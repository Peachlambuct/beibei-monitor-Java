
pub mod arch {
    use std::net::IpAddr;
    use std::process::Command;
    use std::time::{Duration, SystemTime, UNIX_EPOCH};
    use log::{warn};
    use reqwest::Client;
    use reqwest::header::HeaderMap;
    use serde_json::json;
    use sysinfo::{Disks, Networks, System};
    use crate::arch::runtime_detail::runtime_detail::{BaseDetails, ProcessInfo, RuntimeDetail, WarnProcessInfo, CFG};

    pub fn get_arch_info() -> BaseDetails {
        let mut sys = System::new_all();
        sys.refresh_all();
        let sys_name = System::name().unwrap();
        let cpu_name = if sys_name.eq("Windows") {
            let output = Command::new("cmd")
                .args(&["/C", "wmic cpu get name"])
                .output()
                .expect("Failed to execute command");

            let output_str = String::from_utf8_lossy(&output.stdout);
            output_str.lines().nth(1).unwrap_or("").trim().to_string()
        } else {
            let output = Command::new("sh")
                .args(&["-c", "cat /proc/cpuinfo | grep 'model name' | uniq"])
                .output()
                .expect("Failed to execute command");

            let output_str = String::from_utf8_lossy(&output.stdout);
            output_str.lines().nth(0).unwrap_or("").trim().split(": ").nth(1).unwrap_or("").to_string()
        };

        let sys_name = System::name().unwrap();
        let sys_os_version = System::os_version().unwrap();
        let sys_arch = std::env::consts::ARCH;
        let os_bit = get_platform_size();
        let cpu_core = sys.cpus().len();
        let memory = sys.total_memory() as f64 / 1024.0 / 1024.0 / 1024.0;
        let ip = get_public_ip().unwrap();

        let mut disk_total: u64 = 0;
        let disks = Disks::new_with_refreshed_list();
        for disk in &disks {
            disk_total += disk.total_space();
        }
        disk_total = disk_total / 1024 / 1024 / 1024;

        BaseDetails {
            os_arch: sys_arch.parse().unwrap(),
            os_name: sys_name,
            os_version: sys_os_version,
            os_bit: os_bit as u8,
            cpu_name: cpu_name.parse().unwrap(),
            cpu_core: cpu_core as u8,
            memory,
            disk: disk_total as u32,
            ip: ip.to_string(),
        }
    }

    fn get_platform_size() -> usize {
        32 << ((!0 as usize) >> 63)
    }

    fn get_public_ip() -> Result<IpAddr, Box<dyn std::error::Error>> {
        let output = Command::new("curl")
            .arg("ifconfig.co")
            .output()?;

        let output_str = std::str::from_utf8(&output.stdout)?.trim();
        let public_ip: IpAddr = output_str.parse()?;

        Ok(public_ip)
    }

    pub async fn get_runtime_details() -> RuntimeDetail {
        let mut sys = System::new_all();
        sys.refresh_all();
        tokio::time::sleep(sysinfo::MINIMUM_CPU_UPDATE_INTERVAL).await;
        sys.refresh_cpu();
        let cpu_usage = sys.global_cpu_info().cpu_usage();
        let memory_usage = sys.used_memory() as f32 / 1024f32 / 1024f32 / 1024f32;
        let memory_usage_percentage = sys.used_memory() as f32 / sys.total_memory() as f32 * 100.0;

        check_warn(cpu_usage, memory_usage_percentage,&sys).await;

        let mut disk_usage = 0;
        for disk in Disks::new_with_refreshed_list().list() {
            disk_usage += disk.total_space() - disk.available_space();
        }
        disk_usage = disk_usage / 1024 / 1024 / 1024;
        let mut networks = Networks::new_with_refreshed_list();
        tokio::time::sleep(Duration::from_millis(10)).await;
        networks.refresh();
        let mut network_upload = 0;
        let mut network_download = 0;

        for (interface_name, data) in &networks {
            if !interface_name.contains("VMware") && !interface_name.contains("vEthernet") {
                network_upload += data.received() as usize;
                network_download += data.transmitted() as usize;
            }
        }
        let mut disk_read = 0;
        let mut disk_write = 0;
        let now = SystemTime::now();
        let since_the_epoch = now.duration_since(UNIX_EPOCH)
            .expect("Time went backwards");
        let timestamp = since_the_epoch.as_millis() as usize;
        for (_, process) in sys.processes() {
            let disk_usage = process.disk_usage();
            disk_read += disk_usage.read_bytes;
            disk_write += disk_usage.written_bytes;
        }
        RuntimeDetail {
            timestamp,
            cpu_usage,
            memory_usage,
            disk_usage,
            network_upload: network_upload as f64 / 1024f64,
            network_download: network_download as f64 / 1024f64,
            disk_read: disk_read as f64 / 1024f64 / 1024f64,
            disk_write: disk_write as f64 / 1024f64 / 1024f64,
        }
    }


    async fn check_warn(cpu_usage: f32,memory_usage: f32,sys: &System) {
        if cpu_usage > 80.0 || memory_usage > 80.0 {
            let mut processes: Vec<_> = sys.processes().values().collect();
            let top_5: Vec<_> = if cpu_usage > 80.0 {
                processes.sort_by(|a, b| b.cpu_usage().partial_cmp(&a.cpu_usage()).unwrap());
                processes.iter().take(5).collect()
            } else {
                processes.sort_by(|a, b| b.memory().partial_cmp(&a.memory()).unwrap());
                processes.iter().take(5).collect()
            };
            let mut process_top = Vec::new();
            for i in top_5 {
                process_top.push(ProcessInfo::new(i.name().to_string(), i.cpu_usage() / sys.cpus().len() as f32, i.memory() as f32 / 1024f32 / 1024.0, i.start_time() / 60u64));
            }
            let mut headers = HeaderMap::new();
            headers.insert("Authorization", CFG.get_token().parse().unwrap());
            headers.insert("Content-Type", "application/json".parse().unwrap());
            let client = Client::new();
            let warn_process_info = WarnProcessInfo::new(cpu_usage, memory_usage, process_top);

            let _ = client.post(CFG.get_address().to_string() + "/monitor/processWarn")
                .headers(headers)
                .json(&json!(warn_process_info))
                .send().await;
            warn!("CPU or memory usage is too high, top 5 processes: Process: {:?}", json!(warn_process_info));
        }
    }
}