package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.DevelopTask;
import com.example.entity.vo.request.TaskAddVO;
import com.example.entity.vo.request.TaskListVO;

import java.util.List;

public interface DevelopService extends IService<DevelopTask> {
    void addTask(TaskAddVO task);

    List<TaskListVO> getAllList(Integer userId, String role);
}
