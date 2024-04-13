package com.example.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class WarnProcessInfo implements Serializable {
    String processName;
    Double cpuUsage;
    Double memoryUsage;
    String user;
    Long usageTime;
}
