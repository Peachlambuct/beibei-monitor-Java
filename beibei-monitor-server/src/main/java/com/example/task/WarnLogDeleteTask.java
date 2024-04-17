package com.example.task;

import com.example.mapper.ClientWarnMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class WarnLogDeleteTask {

    @Resource
    ClientWarnMapper clientWarnMapper;

    /**
     * 每天凌晨执行删除过期警告日志的任务
     */
    @Scheduled(cron = "0 0 0 * * ?")  // 每天凌晨执行
    public void deleteExpiredWarnLogs() {
        log.info("执行删除过期警告日志的任务");
        clientWarnMapper.deleteOldWarnLogs();
    }
}
