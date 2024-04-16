package com.example.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.Account;
import com.example.entity.dto.Client;
import com.example.entity.dto.DevelopSubtask;
import com.example.entity.dto.DevelopTask;
import com.example.entity.vo.request.*;
import com.example.entity.vo.response.TaskListVO;
import com.example.mapper.AccountMapper;
import com.example.mapper.ClientMapper;
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
import java.util.stream.Collectors;

@Service
public class DevelopServiceImpl extends ServiceImpl<DevelopTaskMapper, DevelopTask> implements DevelopService {

    @Resource
    DevelopTaskMapper developTaskMapper;

    @Resource
    AccountMapper accountMapper;

    @Resource
    ClientMapper clientMapper;

    @Resource
    DevelopSubtaskMapper subtaskMapper;

    @Override
    @Transactional
    public void addTask(TaskAddVO task) {
        DevelopTask developTask = new DevelopTask(null, task.getName(), JSONArray.copyOf(task.getPrincipalIds()).toJSONString(), task.getType(),
                task.getDescription(), JSONArray.copyOf(task.getAboutClientId()).toJSONString(), task.getStartTime(), task.getEndTime(), null);
        List<SubtaskAddVO> subtasks = task.getSubtasks();
        this.save(developTask);

        for (SubtaskAddVO subtask : subtasks) {
            DevelopSubtask developSubtask = new DevelopSubtask();
            BeanUtils.copyProperties(subtask, developSubtask);
            developSubtask.setTaskId(developTask.getId());
            subtaskMapper.insert(developSubtask);
        }
    }

    @Override
    @Transactional
    public List<TaskListVO> getMainTask(Integer userId, String role) {
        List<TaskListVO> taskListVOS = new ArrayList<>();
        List<DevelopTask> tasks;
        // 获取主任务
        tasks = Const.ROLE_ADMIN.equals(role.substring(5))?this.list():developTaskMapper.getAllByUserId(userId);
        // 遍历主任务，分别获取子任务，任务相关用户id和姓名，任务相关客户端id和名称
        for (DevelopTask task : tasks) {
            // 获取任务相关用户id列表
            List<Integer> principalIdList = JSONArray.parseArray(task.getPrincipalIds(), Integer.class);
            // 获取任务相关客户端id列表
            List<Integer> aboutClientIdList = JSONArray.parseArray(task.getAboutClientId(), Integer.class);
            // 获取任务相关用户姓名列表
            QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("id", principalIdList);
            List<Account> accounts = accountMapper.selectList(queryWrapper);
            ArrayList<String> principalNames = new ArrayList<>();
            accounts.forEach(account -> principalNames.add(account.getUsername()));
            // 获取任务相关客户端名称列表
            QueryWrapper<Client> clientQueryWrapper = new QueryWrapper<>();
            clientQueryWrapper.in("id", aboutClientIdList);
            List<Client> clients = clientMapper.selectList(clientQueryWrapper);
            ArrayList<String> aboutClientNames = new ArrayList<>();
            clients.forEach(client -> aboutClientNames.add(client.getName()));
            // 生成任务VO对象
            TaskListVO taskListVO = new TaskListVO();
            BeanUtils.copyProperties(task, taskListVO);
            taskListVO.setAboutClientIds(aboutClientIdList);
            taskListVO.setPrincipalIds(principalIdList);
            taskListVO.setAboutClientNames(aboutClientNames);
            taskListVO.setPrincipalNames(principalNames);
            // 获取任务子任务列表
            List<DevelopSubtask> subtasks = subtaskMapper.selectList(new QueryWrapper<DevelopSubtask>()
                    .eq("task_id", taskListVO.getId()));
            taskListVO.setSubtasks(subtasks);
            // 计算进度
            int total = subtasks.size();
            int finished = 0;
            for (DevelopSubtask subtask : subtasks) {
                if (subtask.getStatus() == 2) finished++;
            }
            taskListVO.setProcess(finished *  1.0 / total);
            taskListVOS.add(taskListVO);
        }
        return taskListVOS;
    }

    @Override
    @Transactional
    public void deleteTask(Integer taskId) {
        this.removeById(taskId);
        subtaskMapper.delete(new QueryWrapper<DevelopSubtask>().eq("task_id", taskId));
    }

    @Override
    public void updateTask(TaskUpdateVO task) {
        DevelopTask developTask = new DevelopTask(null, task.getName(), JSONArray.copyOf(task.getPrincipalIds()).toJSONString(), task.getType(),
                task.getDescription(), JSONArray.copyOf(task.getAboutClientId()).toJSONString(), task.getStartTime(), task.getEndTime(), null);
        this.updateById(developTask);
        task.getSubtasks().forEach(subtask -> {
            DevelopSubtask developSubtask = new DevelopSubtask();
            BeanUtils.copyProperties(subtask, developSubtask);
            subtaskMapper.update(developSubtask, new QueryWrapper<DevelopSubtask>().eq("id", subtask.getId()));
        });
    }

    @Override
    public void deleteSubtask(Integer subtaskId) {
        subtaskMapper.deleteById(subtaskId);
    }

    @Override
    public void updateSubtaskStatus(SubtaskStatusVO subtaskStatusVO) {
        subtaskMapper.updateStatus(subtaskStatusVO);
    }

}
