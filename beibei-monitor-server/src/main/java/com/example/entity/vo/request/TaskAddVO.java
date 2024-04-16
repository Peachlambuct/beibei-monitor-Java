package com.example.entity.vo.request;

import com.alibaba.fastjson2.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class TaskAddVO {
    @NotNull
    String name;
    String principalName;
    String type;
    String description;
    List<Integer> aboutClientId;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    Date startTime;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    Date endTime;
    List<SubtaskAddVO> subtasks;
}
