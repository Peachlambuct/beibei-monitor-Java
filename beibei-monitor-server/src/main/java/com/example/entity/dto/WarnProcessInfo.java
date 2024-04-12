package com.example.entity.dto;

import lombok.Data;

@Data
public class WarnProcessInfo {
    String ProcessName;
    Double CpuUsage;
    Double MemoryUsage;
    String User;
    Long UsageTime;
}
