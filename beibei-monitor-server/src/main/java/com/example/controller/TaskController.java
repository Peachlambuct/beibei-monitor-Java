package com.example.controller;


import com.example.entity.RestBean;
import com.example.entity.vo.request.TaskAddVO;
import com.example.entity.vo.request.TaskListVO;
import com.example.entity.vo.request.TaskUpdateVO;
import com.example.service.DevelopService;
import com.example.utils.Const;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Resource
    DevelopService developService;

    @GetMapping("/list")
    public RestBean<List<TaskListVO>> list(@RequestAttribute(Const.ATTR_USER_ID) Integer userId,
                                           @RequestAttribute(Const.ATTR_USER_ROLE) String role){
        return RestBean.success(developService.getAllList(userId,role));
    }
    @PostMapping("/addTask")
    public RestBean<Void> addTask(@RequestBody TaskAddVO task){
        developService.addTask(task);
        return RestBean.success();
    }
    @GetMapping("/deleteTask")
    public RestBean<Void> deleteTask(@RequestParam Integer taskId){
        developService.deleteTask(taskId);
        return RestBean.success();
    }

    @GetMapping("/deleteSubtask")
    public RestBean<Void> deleteSubtask(@RequestParam Integer subtaskId){
        developService.deleteSubtask(subtaskId);
        return RestBean.success();
    }

    @PostMapping("/updateTask")
    public RestBean<Void> updateTask(@RequestBody TaskUpdateVO task){
        developService.updateTask(task);
        return RestBean.success();
    }
}
