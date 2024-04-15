package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.entity.BaseData;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@TableName("db_client_warn_rules")
public class ClientWarnRules implements BaseData {
    @TableId(type = IdType.AUTO)
    Integer id; //规则id
    Integer clientId;   //客户端id
    String name; // 规则名称
    Double cpuWarn; //cpu告警阈值
    Double memoryWarn;  //内存告警阈值
    String description;  //规则描述
}
