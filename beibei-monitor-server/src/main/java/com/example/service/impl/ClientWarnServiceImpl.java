package com.example.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.Account;
import com.example.entity.dto.ClientWarn;
import com.example.entity.dto.ClientWarnRules;
import com.example.entity.dto.WarnProcessInfo;
import com.example.entity.vo.request.WarnVO;
import com.example.entity.vo.response.ClientWarnVO;
import com.example.entity.vo.response.YesterdayWarnVO;
import com.example.mapper.AccountMapper;
import com.example.mapper.ClientMapper;
import com.example.mapper.ClientWarnMapper;
import com.example.mapper.ClientWarnRulesMapper;
import com.example.service.ClientWarnService;
import com.example.utils.Const;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class ClientWarnServiceImpl extends ServiceImpl<ClientWarnMapper, ClientWarn> implements ClientWarnService {

    @Resource
    ClientMapper clientMapper;

    @Resource
    AccountMapper accountMapper;

    @Resource
    ClientWarnRulesMapper clientWarnRulesMapper;

    @Resource
    AmqpTemplate rabbitTemplate;

    @Resource
    StringRedisTemplate stringRedisTemplate;


    @Override
    public List<ClientWarnVO> listAllWarn() {
        List<ClientWarn> clientWarns = this.list();
        List<ClientWarnVO> clientWarnVOS = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        clientWarns.forEach(clientWarn -> {
            ClientWarnVO clientWarnVO = new ClientWarnVO();
            BeanUtils.copyProperties(clientWarn, clientWarnVO);
            clientWarnVO.setTime(format.format(clientWarn.getTime()));
            clientWarnVOS.add(clientWarnVO);
        });
        return clientWarnVOS;
    }

    @Override
    public List<YesterdayWarnVO> listYesterdayWarn(String role,Integer userId) {
        List<YesterdayWarnVO> yesterdayWarn = baseMapper.getYesterdayWarn();
        if (!Const.ROLE_ADMIN.equals(role.substring(5))){
            List<Integer> clients = JSON.parseArray(accountMapper.selectById(userId).getClients(),Integer.class);
            yesterdayWarn.removeIf(warn -> !clients.contains(warn.getClientId()));
        }
        return yesterdayWarn;
    }

    /**
     * 处理告警信息
     * @param warnVO top5进程
     * @param clientId 服务器id
     */
    @Override
    @Transactional
    public void processWarn(WarnVO warnVO, int clientId) {
        // 根据客户端id获取阈值
        ClientWarnRules warnRule = clientWarnRulesMapper.getInfoByClientId(clientId);
        if (Objects.isNull(warnRule) ||
                (warnRule.getCpuWarn() > warnVO.getCpuUsage() && warnRule.getMemoryWarn() > warnVO.getMemoryUsage()))
            return;
        String description;
        if (warnVO.getCpuUsage() >= warnRule.getCpuWarn() && warnVO.getMemoryUsage() >= warnRule.getMemoryWarn()){
            description = "CPU、内存使用率超过阈值";
        } else if (warnVO.getCpuUsage() >= warnRule.getCpuWarn()){
            description = "CPU使用率超过阈值";
        } else {
            description = "内存使用率超过阈值";
        }
        ClientWarn warn = new ClientWarn();
        warn.setClientId(clientId);
        warn.setTime(new Date());
        warn.setDescription(description);
        warn.setDetail(JSON.toJSONString(warnVO.getWarnProcessInfos()));
        this.save(warn);

        // 这里应该是发送到rabbitmq
        // 根据clientId获取负责节点人的email
        if (stringRedisTemplate.opsForValue().get(Const.MQ_WARN + clientId) != null) return;
        clientMapper.findEmailsByClientId(clientId).forEach(email -> {
            Map<String, Object> data = Map.of("id",clientId,
                    "data", warnVO.getWarnProcessInfos(),
                    "email", email,
                    "description", description);
            rabbitTemplate.convertAndSend(Const.MQ_WARN, data);
        });

        //TODO 测试用
        Map<String, Object> data = Map.of("id",clientId,
                "data", warnVO.getWarnProcessInfos(),
                "email", "2727402548@qq.com",
                "description", description);
        rabbitTemplate.convertAndSend(Const.MQ_WARN, data);

        //放入redis防止10分钟内重复发送
        stringRedisTemplate.opsForValue().set(Const.MQ_WARN + clientId, "1", 10, TimeUnit.MINUTES);
    }
}
