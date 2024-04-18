package com.example.entity.vo.response;

import com.example.entity.BaseData;
import lombok.Data;

import java.util.Date;

@Data
public class SubtaskVO implements BaseData {
    Integer id;
    String name;
    Integer taskId;
    String description;
    String aboutClients;
    String principals;
    String taskType;
    Date startTime;
    Date endTime;
    Date updateTime;
    String updateUserName;
    Integer status;
}
