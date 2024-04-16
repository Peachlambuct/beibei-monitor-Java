package com.example.controller;


import com.example.entity.RestBean;
import com.example.entity.vo.request.TaskAddVO;
import com.example.entity.vo.request.TaskListVO;
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
}
