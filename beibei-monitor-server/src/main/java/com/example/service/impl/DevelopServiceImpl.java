package com.example.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.Account;
import com.example.entity.dto.Client;
import com.example.entity.dto.DevelopSubtask;
import com.example.entity.dto.DevelopTask;
import com.example.entity.vo.request.*;
import com.example.entity.vo.response.SimpleTaskVO;
import com.example.entity.vo.response.SubtaskVO;
import com.example.entity.vo.response.TaskListVO;
import com.example.mapper.AccountMapper;
import com.example.mapper.ClientMapper;
import com.example.mapper.DevelopSubtaskMapper;
import com.example.mapper.DevelopTaskMapper;
import com.example.service.DevelopService;
import com.example.utils.Const;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public List<TaskListVO> getMainTask(Integer userId, String role) {
        List<TaskListVO> taskListVOS = new ArrayList<>();
        List<DevelopTask> tasks;
        // 获取主任务
        tasks = Const.ROLE_ADMIN.equals(role.substring(5)) ? this.list() : developTaskMapper.getAllByUserId(userId);
        // 遍历主任务，分别获取子任务，任务相关用户id和姓名，任务相关客户端id和名称
        for (DevelopTask task : tasks) {
            // 获取任务相关用户id列表
            List<Integer> principalIdList = JSONArray.parseArray(task.getPrincipalIds(), Integer.class);
            // 获取任务相关客户端id列表
            List<Integer> aboutClientIdList = JSONArray.parseArray(task.getAboutClientId(), Integer.class);
            // 获取任务相关用户姓名列表
            ArrayList<String> principalNames, aboutClientNames;
            if (!principalIdList.isEmpty()) {
                QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
                queryWrapper.in("id", principalIdList);
                List<Account> accounts = accountMapper.selectList(queryWrapper);
                principalNames = new ArrayList<>();
                accounts.forEach(account -> principalNames.add(account.getUsername()));
            } else {
                principalNames = null;
            }
            // 获取任务相关客户端名称列表

            if (!aboutClientIdList.isEmpty()) {
                QueryWrapper<Client> clientQueryWrapper = new QueryWrapper<>();
                clientQueryWrapper.in("id", aboutClientIdList);
                List<Client> clients = clientMapper.selectList(clientQueryWrapper);
                aboutClientNames = new ArrayList<>();
                clients.forEach(client -> aboutClientNames.add(client.getName()));
            } else {
                aboutClientNames = null;
            }

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
            taskListVO.setProcess(finished * 1.0 / total);
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
    @Transactional
    public void saveTask(TaskSaveVO task, String role) {
        if (!Const.ROLE_ADMIN.equals(role.substring(5)))
            return;
        DevelopTask developTask = new DevelopTask(null, task.getName(),
                JSONArray.copyOf(task.getPrincipalIds()).toJSONString(),
                task.getType(), task.getDescription(),
                JSONArray.copyOf(task.getAboutClientId()).toJSONString(), task.getStartTime(),
                task.getEndTime(), null);
        List<SubtaskSaveVO> subtasks = task.getSubtasks();
        if (task.getId() != null) {
            developTask.setId(task.getId());
            this.updateById(developTask);
        } else {
            this.save(developTask);
        }
        List<DevelopSubtask> subtaskList = subtaskMapper.selectList(
                new QueryWrapper<DevelopSubtask>().eq("task_id", developTask.getId()));
        for (SubtaskSaveVO subtaskVo : subtasks) {
            DevelopSubtask subtask = new DevelopSubtask();
            BeanUtils.copyProperties(subtaskVo, subtask);
            subtaskList.removeIf(subtask1 -> subtask1.getId().equals(subtask.getId()));
            subtask.setTaskId(task.getId());
            if (subtask.getId() != null) {
                subtaskMapper.updateById(subtask);
            } else {
                subtaskMapper.insert(subtask);
            }
        }
        subtaskList.forEach(subtask -> subtaskMapper.deleteById(subtask.getId()));
    }

    @Override
    public void deleteSubtask(Integer subtaskId) {
        subtaskMapper.deleteById(subtaskId);
    }

    @Override
    @Transactional
    public void updateSubtaskStatus(SubtaskStatusVO subtaskStatusVO, Integer userId) {
        // 创建一个UpdateWrapper实例
        UpdateWrapper<DevelopSubtask> updateWrapper = new UpdateWrapper<>();
        // 设置要更新的字段
        updateWrapper.set("status", subtaskStatusVO.getStatus());
        updateWrapper.set("update_time", new Date());
        updateWrapper.set("update_user_id", userId);
        // 设置更新条件
        updateWrapper.eq("id", subtaskStatusVO.getSubtaskId());
        // 使用Mapper接口的update方法来更新数据
        subtaskMapper.update(null, updateWrapper);
    }

    @Override
    public List<SubtaskVO> getAllSubtask(Integer userId, String role) {
        List<SubtaskVO> subtaskVOS = subtaskMapper.getSubtask();
        if (!Const.ROLE_ADMIN.equals(role.substring(5))) {
            List<Integer> taskIdList = developTaskMapper.getTaskIdsByUserId(userId);
            subtaskVOS.removeIf(subtaskVO -> !taskIdList.contains(subtaskVO.getTaskId()));
            subtaskVOS.forEach(subtaskVO -> {
                clientMapper.selectList(new QueryWrapper<Client>()
                                .in("id", JSONArray.parseArray(subtaskVO.getAboutClients(), Integer.class)))
                        .stream().map(Client::getName).reduce((s1, s2) -> s1 + ", " + s2)
                        .ifPresent(subtaskVO::setAboutClients);
                accountMapper.selectList(new QueryWrapper<Account>()
                                .in("id", JSONArray.parseArray(subtaskVO.getPrincipals(), Integer.class)))
                        .stream().map(Account::getUsername).reduce((s1, s2) -> s1 + ", " + s2)
                        .ifPresent(subtaskVO::setPrincipals);
            });
        }
        return subtaskVOS;
    }

    @Override
    @Transactional
    public void deleteExpiredTasks() {
        List<Integer> ids = developTaskMapper.getExpiredTaskIds();
        if (!ids.isEmpty()) {
            developTaskMapper.deleteBatchIds(ids);
            subtaskMapper.delete(new QueryWrapper<DevelopSubtask>().in("task_id", ids));
        }
    }

    @Override
    public List<SimpleTaskVO> getTaskByClientId(Integer clientId) {
        return developTaskMapper.selectTasksByClientId(String.valueOf(clientId));
    }
}
