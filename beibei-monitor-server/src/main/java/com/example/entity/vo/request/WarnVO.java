package com.example.entity.vo.request;

import com.example.entity.dto.WarnProcessInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class WarnVO {
    @NotNull
    Double cpuUsage;
    @NotNull
    Double memoryUsage;
    @NotNull
    List<WarnProcessInfo> warnProcessInfos;
}
