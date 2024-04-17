package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.entity.BaseData;
import lombok.Data;

@Data
@TableName("db_client_ssh")
public class ClientSsh implements BaseData {
    @TableId(type = IdType.AUTO)
    Integer id; //ssh连接id
    String name; //连接名称
    Integer userId; //用户id
    Integer clientId;  //客户端id
    Integer port;   //ssh连接端口
    String username;    //ssh连接的客户端账号名
    String password;    //ssh连接的客户端密码
    String ip;  // 客户端ip
}
