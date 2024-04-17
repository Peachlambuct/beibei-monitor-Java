package com.example.entity.vo.request;

import com.example.entity.BaseData;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SubtaskSaveVO implements BaseData {
    Integer id;
    String name;
    String description;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    Date startTime;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    Date endTime;
}
