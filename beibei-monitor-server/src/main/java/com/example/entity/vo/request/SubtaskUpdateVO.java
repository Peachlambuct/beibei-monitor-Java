package com.example.entity.vo.request;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.entity.BaseData;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SubtaskUpdateVO implements BaseData {
    Integer id;
    String name;
    String principalName;
    String description;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    Date startTime;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd  HH:mm:ss")
    Date endTime;
}
