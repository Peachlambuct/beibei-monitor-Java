use log::{error, info};
use std::fs::File;
use std::io::{stdin, Write};
use tokio::task;
mod arch;
mod logger;
mod net;

use crate::arch::arch::arch::{get_arch_info, get_runtime_details};
use crate::arch::runtime_detail::runtime_detail::ServerConfig;
use crate::logger::logger::logger::setup_logger;
use crate::net::net::net::{register_to_server, update_base_details, update_runtime_details};

#[tokio::main]
async fn main() {
    setup_logger().expect("初始化日志失败!");
    task::spawn(async {
        match File::open("config/server.json") {
            Ok(_) => {
                info!("正在向服务器同步数据...");
                let sys_info = get_arch_info();
                update_base_details(sys_info).await.expect("同步数据失败:");
                info!("数据同步完成...");
                update_date().await;
            }
            Err(_) => {
                info!("正在准备向服务器注册中...");
                println!("请输入服务器的IP地址：");
                let mut address = String::new();
                stdin().read_line(&mut address).ok().expect("读取输入错误!");
                let address: String = match address.trim().parse() {
                    Ok(f) => f,
                    Err(_) => {
                        println!("请正确输入IP地址!");
                        return;
                    }
                };
                println!("请输入token密钥：");
                let mut token = String::new();
                stdin().read_line(&mut token).ok().expect("读取输入错误");
                let token: String = match token.trim().parse() {
                    Ok(f) => f,
                    Err(_) => {
                        println!("请正确输入");
                        return;
                    }
                };
                let config = ServerConfig::new(address.clone(), token.clone());
                let address = address + "/monitor/register";

                if let Err(_) = File::open("config") {
                    std::fs::create_dir("config").expect("创建文件夹失败!");
                }
                if let Ok(mut file) = File::create("config/server.json") {
                    let data = serde_json::to_string(&config);
                    if data.is_err() {
                        error!("序列化错误!");
                        std::process::exit(1);
                    }
                    let data = data.unwrap();
                    file.write_all(data.as_bytes())
                        .expect("服务器数据保存出错!");
                }
                info!("服务器信息保存完成...");
                register_to_server(&address, &token)
                    .await
                    .expect("服务器同步数据失败!请检查服务器是否开启或者网络是否正常!");
                info!("正在向服务器同步数据...");
                let sys_info = get_arch_info();
                update_base_details(sys_info)
                    .await
                    .expect("服务器同步数据失败!请检查服务器是否开启或者网络是否正常!");
                info!("数据同步完成...");
                update_date().await;
            }
        }
    })
    .await
    .expect("任务执行失败!");
}

async fn update_date() {
    let mut err_num = 0;
    loop {
        let runtime_detail = get_runtime_details().await;
        match update_runtime_details(runtime_detail).await {
            Ok(_) => {}
            Err(_) => {
                err_num += 1;
                if err_num >= 3 {
                    error!("请检查服务器是否开启或者网络是否正常!");
                    std::process::exit(1);
                }
            }
        }
        tokio::time::sleep(std::time::Duration::from_secs(9)).await;
    }
}