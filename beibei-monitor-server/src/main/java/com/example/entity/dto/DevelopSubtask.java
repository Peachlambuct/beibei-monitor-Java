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
    String principalName;   //负责人姓名
    String description; //任务描述
    Date startTime; //任务开始时间
    Date endTime;   //任务结束时间
    Integer status; //状态(0未开始,1进行中,2已完成)
}
