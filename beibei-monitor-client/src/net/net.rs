pub mod net {
    use log::{info, warn};
    use reqwest::header::HeaderMap;
    use reqwest::Error;
    use serde::Serialize;
    use serde_json::Value;
    use std::collections::HashMap;
    use std::fs;
    use crate::arch::runtime_detail::runtime_detail::{BaseDetails, RuntimeDetail, ServerMessage, CFG};

    async fn get(url: &str, header: HeaderMap) -> Result<HashMap<String, Value>, Error> {
        let client = reqwest::Client::new();
        Ok(client.get(url)
            .headers(header)
            .send()
            .await?.json::<HashMap<String, Value>>().await?)
    }

    async fn post<T: ServerMessage + Serialize>(data: T, url: &str)
                                                -> Result<HashMap<String, Value>, Error> {
        let data = serde_json::to_string(&data).unwrap();
        let client = reqwest::Client::new();
        let mut headers = HeaderMap::new();
        headers.insert("Authorization", CFG.get_token().parse().unwrap());
        headers.insert("Content-Type", "application/json".parse().unwrap());
        Ok(client.post(CFG.get_address().to_string() + "/monitor/" + url)
            .headers(headers)
            .body(data)
            .send()
            .await?.json::<HashMap<String, Value>>().await?)
    }

    pub async fn update_runtime_details(data: RuntimeDetail) -> Result<(), Box<dyn std::error::Error>> {
        handle_response(post(data, "runtime").await).await
    }

    pub async fn register_to_server(address: &str, token: &str) -> Result<(), Box<dyn std::error::Error>> {
        let mut headers = HeaderMap::new();
        headers.insert("Authorization", token.parse().unwrap());
        handle_response(get(address, headers).await).await
    }

    pub async fn update_base_details(data: BaseDetails) -> Result<(), Box<dyn std::error::Error>> {
        handle_response(post(data, "detail").await).await
    }

    async fn handle_response(res: Result<HashMap<String, Value>, Error>) -> Result<(), Box<dyn std::error::Error>> {
        match res {
            Ok(res) => {
                match res.get("code") {
                    None => {
                        warn!("数据上传失败!");
                        Err("数据上传失败!".into())
                    }
                    Some(code) => {
                        if code.as_u64().unwrap() == 200 {
                            info!("数据上传成功!");
                            Ok(())
                        }
                        else if code.as_u64().unwrap() == 401 {
                            warn!("Token验证失败!,请检查服务器配置文件中的token是否正确!，请重新注册");
                            fs::remove_dir_all("config").expect("删除文件夹失败!");
                            Err("Token验证失败!".into())
                        }
                        else {
                            warn!("数据上传失败!");
                            Err("数据上传失败!".into())
                        }
                    }
                }
            }
            Err(e) => Err(e.into()),
        }
    }
}