package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.entity.BaseData;
import lombok.Data;

import java.util.Date;

@Data
@TableName("db_develop_subtask")
public class DevelopSubtask implements BaseData {
    @TableId(type = IdType.AUTO)
    Integer id; //子任务id
    String name;    //任务名称
    Integer taskId;    //父任务id
    String description; //任务描述
    Date startTime; //任务开始时间
    Date endTime;   //任务结束时间
    Date updateTime;    //更新时间
    Integer updateUserId;   //更新人id
    Integer status; //状态(0未开始,1进行中,2已完成)
}
