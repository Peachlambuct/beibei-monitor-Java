package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.entity.BaseData;
import lombok.Data;

import java.util.Date;

@Data
@TableName("db_client_warn")
public class ClientWarn implements BaseData {
    @TableId(type = IdType.AUTO)
    Integer id; //警告id
    Integer clientId;   //客户端id
    String detail;  //警告详细信息
    Date time;  //警告时间
    String description; //警告描述
}
