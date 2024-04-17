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
    String aboutClientId;
    String taskType;
    Date startTime;
    Date endTime;
    Date updateTime;
    Integer updateUserId;
    Integer status;
}
