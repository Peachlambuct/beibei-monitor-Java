package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.ClientWarnRules;
import com.example.mapper.ClientWarnRulesMapper;
import com.example.service.ClientWarnRulesService;
import org.springframework.stereotype.Service;

@Service
public class ClientWarnRulesServiceImpl extends ServiceImpl<ClientWarnRulesMapper, ClientWarnRules> implements ClientWarnRulesService {

    @Override
    public void addWarnRule(ClientWarnRules warnRule) {
        this.save(warnRule);
    }

    @Override
    public void updateWarnRule(ClientWarnRules warnRule) {
        this.update().eq("id",warnRule.getId())
                .set("clientId",warnRule.getClientId())
                .set("name",warnRule.getName())
                .set("cpuWarn",warnRule.getCpuWarn())
                .set("memoryWarn",warnRule.getMemoryWarn())
                .set("description",warnRule.getDescription());
    }

    @Override
    public void deleteWarnRule(Integer id) {
        this.removeById(id);
    }
}
