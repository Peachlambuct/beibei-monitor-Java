pub mod runtime_detail {
    use std::fs::File;
    use std::io::Read;
    use once_cell::sync::Lazy;
    use serde::{Deserialize, Serialize};
    const CONFIG_FILE: &str = "config/server.json";
    pub static CFG: Lazy<ServerConfig> = Lazy::new(ServerConfig::init);

    // 能被发送的信息识别
    pub trait ServerMessage {
    }
    #[derive(Serialize, Deserialize, Debug)]
    pub struct RuntimeDetail {
        pub timestamp: usize,
        #[serde(rename = "cpuUsage")]
        pub cpu_usage: f32,
        #[serde(rename = "memoryUsage")]
        pub memory_usage: f32,
        #[serde(rename = "diskUsage")]
        pub disk_usage: u64,
        #[serde(rename = "networkUpload")]
        pub network_upload: f64,
        #[serde(rename = "networkDownload")]
        pub network_download: f64,
        #[serde(rename = "diskRead")]
        pub disk_read: f64,
        #[serde(rename = "diskWrite")]
        pub disk_write: f64,
    }

    #[derive(Serialize, Deserialize, Debug)]
    pub struct ProcessInfo {
        #[serde(rename = "processName")]
        pub(crate) process_name: String,
        #[serde(rename = "cpuUsage")]
        pub(crate) cpu_usage: f32,
        #[serde(rename = "memoryUsage")]
        pub(crate) memory_usage: f32,
        #[serde(rename = "usageTime")]
        pub(crate) usage_time: u64,
    }
    #[derive(Serialize, Deserialize, Debug)]
    pub struct WarnProcessInfo {
        #[serde(rename = "cpuUsage")]
        pub(crate) cpu_usage: f32,
        #[serde(rename = "memoryUsage")]
        pub(crate) memory_usage: f32,
        #[serde(rename = "warnProcessInfos")]
        pub(crate) process_list: Vec<ProcessInfo>,
    }
    #[derive(Serialize, Deserialize, Debug)]
    pub struct ServerConfig {
        address: String,
        token: String,
    }
    #[derive(Serialize, Deserialize, Debug)]
    pub struct BaseDetails {
        #[serde(rename = "osArch")]
        pub os_arch: String,
        #[serde(rename = "osName")]
        pub os_name: String,
        #[serde(rename = "osVersion")]
        pub os_version: String,
        #[serde(rename = "osBit")]
        pub os_bit: u8,
        #[serde(rename = "cpuName")]
        pub cpu_name: String,
        #[serde(rename = "cpuCore")]
        pub cpu_core: u8,
        pub memory: f64,
        pub disk: u32,
        pub ip: String,
    }
    impl ServerMessage for RuntimeDetail {
    }

    impl ServerMessage for BaseDetails {
    }

    impl ServerMessage for ProcessInfo {
    }
    impl WarnProcessInfo {
        pub fn new(cpu_usage: f32, memory_usage: f32, process_list: Vec<ProcessInfo>) -> Self {
            WarnProcessInfo {
                cpu_usage,
                memory_usage,
                process_list,
            }
        }
    }

    impl ProcessInfo {
        pub fn new(process_name: String, cpu_usage: f32, memory_usage: f32, usage_time: u64) -> Self {
            ProcessInfo {
                process_name,
                cpu_usage,
                memory_usage,
                usage_time,
            }
        }
    }
    impl ServerConfig {
        pub fn get_address(&self) -> &String {
            &self.address
        }
        pub fn get_token(&self) -> &String {
            &self.token
        }
        pub fn new(address: String, token: String) -> ServerConfig {
            ServerConfig {
                address,
                token,
            }
        }
        pub fn init() -> ServerConfig {
            let mut file = match File::open(CONFIG_FILE) {
                Ok(file) => file,
                Err(e) => {
                    panic!("没有发现服务器配置文件{},错误信息{}",CONFIG_FILE,e);
                }
            };
            let mut cfg_contents = String::new();
            match file.read_to_string(&mut cfg_contents) {
                Ok(_) => {},
                Err(e) => {
                    panic!("读取服务器配置文件失败,错误信息{}",e);
                }
            };
            match serde_json::from_str(&cfg_contents) {
                Ok(server_config) =>{
                    server_config
                },
                Err(e) => {
                    panic!("解析服务器配置文件失败,错误信息{}",e);
                }
            }
        }
    }
}
