package com.example.entity.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.ibatis.javassist.runtime.Inner;

import java.util.Date;
import java.util.List;

@Data
public class TaskUpdateVO {
    @NotNull
    Integer id;
    @NotNull
    String name;
    List<Integer> principalIds;
    String type;
    String description;
    List<Integer> aboutClientId;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    Date startTime;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    Date endTime;
    List<SubtaskUpdateVO> subtasks;
}
