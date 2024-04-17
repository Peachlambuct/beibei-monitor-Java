package com.example.task;

import com.example.mapper.ClientWarnMapper;
import com.example.mapper.DevelopTaskMapper;
import com.example.service.DevelopService;
import com.influxdb.client.service.TasksService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class TaskDeleteTask {

    @Resource
    DevelopService developService;

    /**
     * 每天凌晨执行删除过期警告日志的任务
     */
    @Scheduled(cron = "0 0 0 * * ?")  // 每天凌晨执行
    public void deleteExpiredTasks() {
        log.info("执行删除任务已结束30天的任务");
        developService.deleteExpiredTasks();
    }
}
