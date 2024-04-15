package com.example.entity.vo.response;

import com.example.entity.BaseData;
import lombok.Data;

@Data
public class ClientWarnRulesVO implements BaseData {
    Integer id;
    Integer clientId;
    String clientName;
    String name;
    Double cpuWarn;
    Double memoryWarn;
    String description;
}
