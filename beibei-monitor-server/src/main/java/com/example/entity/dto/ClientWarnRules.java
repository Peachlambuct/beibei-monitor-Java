package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.entity.BaseData;
import lombok.Data;

@Data
@TableName("db_warn_rules")
public class ClientWarnRules implements BaseData {
    Integer id; //规则id
    Integer userId; //用户id
    Integer clientId;   //客户端id
    Double cpuWarn; //cpu告警阈值
    Double memoryWarn;  //内存告警阈值
    Integer status; //是否开启告警(0否,1是)
}
