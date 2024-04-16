package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.DevelopSubtask;
import com.example.entity.dto.DevelopTask;
import com.example.entity.vo.request.SubtaskAddVO;
import com.example.entity.vo.request.TaskAddVO;
import com.example.entity.vo.request.TaskListVO;
import com.example.mapper.DevelopSubtaskMapper;
import com.example.mapper.DevelopTaskMapper;
import com.example.service.DevelopService;
import com.example.utils.Const;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DevelopServiceImpl extends ServiceImpl<DevelopTaskMapper, DevelopTask> implements DevelopService {

    @Resource
    DevelopTaskMapper developTaskMapper;

    @Resource
    DevelopSubtaskMapper subtaskMapper;

    @Override
    @Transactional
    public void addTask(TaskAddVO task) {
        DevelopTask developTask = new DevelopTask();
        List<SubtaskAddVO> subtasks = task.getSubtasks();
        BeanUtils.copyProperties(task, developTask);
        this.save(developTask);

        for (SubtaskAddVO subtask : subtasks) {
            DevelopSubtask developSubtask = new DevelopSubtask();
            BeanUtils.copyProperties(subtask, developSubtask);
            developSubtask.setTaskId(developTask.getId());
            subtaskMapper.insert(developSubtask);
        }
    }

    @Override
    public List<TaskListVO> getAllList(Integer userId, String role) {
        List<TaskListVO> taskListVOS = new ArrayList<>();
        List<DevelopTask> tasks;
        if (Const.ROLE_ADMIN.equals(role.substring(5))) {
             tasks = this.list();
        } else {
            tasks = developTaskMapper.getAllByUserId(userId);
        }
        for (DevelopTask task : tasks) {
            TaskListVO taskListVO = new TaskListVO();
            BeanUtils.copyProperties(task, taskListVO);
            taskListVO.setSubtasks(
                    subtaskMapper.selectList(new QueryWrapper<DevelopSubtask>()
                            .eq("task_id", taskListVO.getId()))
            );
            taskListVOS.add(taskListVO);
        }
        return taskListVOS;
    }
}
