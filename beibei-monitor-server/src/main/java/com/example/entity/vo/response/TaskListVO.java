package com.example.entity.vo.response;

import com.example.entity.dto.DevelopSubtask;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TaskListVO {
    Integer id;
    String name;
    List<Integer> principalIds;
    List<String> principalNames;
    List<Integer> aboutClientIds;
    List<String> aboutClientNames;
    Double process;
    String type;
    String description;
    Date startTime;
    Date endTime;
    Integer status;
    List<DevelopSubtask> subtasks;
}
