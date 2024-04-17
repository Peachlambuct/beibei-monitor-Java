package com.example.entity.vo.response;

import lombok.Data;

@Data
public class SshListVO {
    Integer id;
    String name;
    Integer clientId;
    String clientName;
    String ip;
    Boolean isOnLine;
    Integer port;
    String username;
    String password;
    String location;
}
