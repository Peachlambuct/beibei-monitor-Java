pub mod logger {
    use std::fs::File;
    use chrono::Local;

    pub fn setup_logger() -> Result<(), fern::InitError> {
        if let Err(_) = File::open("log") {
            std::fs::create_dir_all("log").expect("创建文件夹失败!");
        }

        let current_date = Local::now().format("%Y-%m-%d").to_string();
        let log_file_name = format!("log/{}.log", current_date);

        fern::Dispatch::new()
            .format(|out, message, record| {
                out.finish(format_args!(
                    "{}[{}][{}] {}",
                    Local::now().format("[%Y-%m-%d][%H:%M:%S]"),
                    record.target(),
                    record.level(),
                    message
                ))
            })
            .level(log::LevelFilter::Info)
            .chain(std::io::stdout())
            .chain(fern::log_file(log_file_name)?)
            .apply()?;
        Ok(())
    }
}