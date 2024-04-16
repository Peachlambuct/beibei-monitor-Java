package com.example.entity.vo.request;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.entity.BaseData;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("db_develop_subtask")
public class SubtaskAddVO implements BaseData {
    String name;
    String principalName;
    String description;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    Date startTime;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    Date endTime;
}
