package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.DevelopTask;
import com.example.entity.vo.request.SubtaskStatusVO;
import com.example.entity.vo.request.TaskSaveVO;
import com.example.entity.vo.response.SimpleTaskVO;
import com.example.entity.vo.response.SubtaskVO;
import com.example.entity.vo.response.TaskListVO;

import java.util.List;

public interface DevelopService extends IService<DevelopTask> {
    List<TaskListVO> getMainTask(Integer userId, String role);

    void deleteTask(Integer taskId);

    void deleteSubtask(Integer subtaskId);

    void updateSubtaskStatus(SubtaskStatusVO subtaskStatusVO,Integer userId);

    List<SubtaskVO> getAllSubtask(Integer userId, String role);

    void deleteExpiredTasks();

    List<SimpleTaskVO> getTaskByClientId(Integer clientId);

    String saveTask(TaskSaveVO task, String role);
}
