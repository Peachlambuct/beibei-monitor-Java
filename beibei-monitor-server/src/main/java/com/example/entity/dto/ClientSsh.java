package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.entity.BaseData;
import lombok.Data;

@Data
@TableName("db_client_ssh")
public class ClientSsh implements BaseData {
    @TableId
    Integer id; //ssh连接id
    Integer userId; //用户id
    Integer clintId;  //客户端id
    Integer port;   //ssh连接端口
    String username;    //ssh连接的客户端账号名
    String password;    //ssh连接的客户端密码
}
