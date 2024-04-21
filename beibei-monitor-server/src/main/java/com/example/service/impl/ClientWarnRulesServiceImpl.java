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
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.List;

@Service
public class ClientWarnRulesServiceImpl extends ServiceImpl<ClientWarnRulesMapper, ClientWarnRules> implements ClientWarnRulesService {

    @Resource
    ClientWarnRulesMapper clientWarnRulesMapper;

    @Resource
    AccountMapper accountMapper;

    @Override
    public String addWarnRule(ClientWarnRules warnRule,Integer userId){
        if (this.getOne(new QueryWrapper<ClientWarnRules>().eq("client_id",warnRule.getClientId())
                .eq("user_id",userId))!=null)
            return "该客户端规则已存在";
        warnRule.setUserId(userId);
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
    public List<ClientWarnRulesVO> listAllWarnRules(Integer userId) {
        return clientWarnRulesMapper.getAllList(userId);
    }
}
