package com.example.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.entity.BaseData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("db_develop_task")
@AllArgsConstructor
public class DevelopTask implements BaseData {
    @TableId(type = IdType.AUTO)
    Integer id;  //任务id
    String name;    //任务名称
    String principalIds;   //项目小组ids
    String type;    //任务类型(例:测试)
    String description; //任务描述
    String aboutClientId;   //任务相关服务器id
    Date startTime; //任务开始时间
    Date endTime;   //任务结束时间
    Integer status; //状态(0未开始,1进行中,2已完成)
}
