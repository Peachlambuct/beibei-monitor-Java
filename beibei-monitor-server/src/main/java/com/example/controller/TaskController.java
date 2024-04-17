package com.example.controller;


import com.example.entity.RestBean;
import com.example.entity.vo.request.SubtaskStatusVO;
import com.example.entity.vo.request.TaskSaveVO;
import com.example.entity.vo.response.TaskListVO;
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
        return RestBean.success(developService.getMainTask(userId,role));
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

    @PostMapping("/saveTask")
    public RestBean<Void> saveTask(@RequestBody TaskSaveVO task){
        // 如果任务ID存在，更新任务。如果任务ID不存在，添加新任务
        if (task.getId() != null) {
            developService.updateTask(task);
        } else {
            developService.addTask(task);
        }
        return RestBean.success();
    }

    @PostMapping("updateSubtask")
    public RestBean<Void> updateSubtask(@RequestAttribute(Const.ATTR_USER_ID) Integer userId,
                                        @RequestBody SubtaskStatusVO subtaskStatusVO){
        developService.updateSubtaskStatus(subtaskStatusVO,userId);
        return RestBean.success();
    }
}
