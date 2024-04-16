package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.DevelopTask;
import com.example.entity.vo.request.SubtaskStatusVO;
import com.example.entity.vo.request.TaskAddVO;
import com.example.entity.vo.response.TaskListVO;
import com.example.entity.vo.request.TaskUpdateVO;

import java.util.List;

public interface DevelopService extends IService<DevelopTask> {
    void addTask(TaskAddVO task);

    List<TaskListVO> getMainTask(Integer userId, String role);

    void deleteTask(Integer taskId);

    void updateTask(TaskUpdateVO task);

    void deleteSubtask(Integer subtaskId);

    void updateSubtaskStatus(SubtaskStatusVO subtaskStatusVO);
}
