package com.example.entity.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TaskAddVO {
    @NotNull
    String name;
    String principalName;
    String type;
    String description;
    String aboutClientId;
     @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    Date startTime;
     @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    Date endTime;
    List<SubtaskAddVO> subtasks;
}
