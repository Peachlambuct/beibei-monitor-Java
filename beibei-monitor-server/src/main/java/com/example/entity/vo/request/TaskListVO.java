package com.example.entity.vo.request;

import com.example.entity.dto.DevelopSubtask;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TaskListVO {
    Integer id;
    String name;
    String principalName;
    String type;
    String description;
    Date startTime;
    Date endTime;
    Integer status;
    List<DevelopSubtask> subtasks;
}
