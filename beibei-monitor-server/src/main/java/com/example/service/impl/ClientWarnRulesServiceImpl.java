package com.example.service.impl;


import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.ClientWarnRules;
import com.example.entity.vo.response.ClientWarnRulesVO;
import com.example.mapper.AccountMapper;
import com.example.mapper.ClientWarnRulesMapper;
import com.example.service.ClientWarnRulesService;
import com.example.utils.Const;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientWarnRulesServiceImpl extends ServiceImpl<ClientWarnRulesMapper, ClientWarnRules> implements ClientWarnRulesService {

    @Resource
    ClientWarnRulesMapper clientWarnRulesMapper;

    @Resource
    AccountMapper accountMapper;

    @Override
    public String addWarnRule(ClientWarnRules warnRule) {
        if (this.baseMapper.selectList(
                new QueryWrapper<ClientWarnRules>().eq("client_id",warnRule.getClientId())).size() > 0)
            return "该客户端规则已存在";
        this.save(warnRule);
        return "添加成功";
    }

    @Override
    public void updateWarnRule(ClientWarnRules warnRule) {
        this.update().eq("id",warnRule.getId())
                .set("client_id",warnRule.getClientId())
                .set("name",warnRule.getName())
                .set("cpu_warn",warnRule.getCpuWarn())
                .set("memory_warn",warnRule.getMemoryWarn())
                .set("description",warnRule.getDescription()).update();
    }

    @Override
    public void deleteWarnRule(Integer id) {
        this.removeById(id);
    }

    @Override
    public List<ClientWarnRulesVO> listAllWarnRules(Integer userId,String role) {
        List<ClientWarnRulesVO> clientWarnRulesVOS = clientWarnRulesMapper.getAllList();
        if (!Const.ROLE_ADMIN.equals(role.substring(5))){
            List<Integer> clientIds = JSON.parseArray(accountMapper.getClientsById(userId), Integer.class);
            clientWarnRulesVOS.removeIf(clientWarnRulesVO -> !clientIds.contains(clientWarnRulesVO.getClientId()));
        }
        return clientWarnRulesVOS;
    }
}
