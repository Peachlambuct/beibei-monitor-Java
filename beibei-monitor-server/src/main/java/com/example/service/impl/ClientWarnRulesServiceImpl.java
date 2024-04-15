package com.example.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.ClientWarnRules;
import com.example.entity.vo.response.ClientWarnRulesVO;
import com.example.mapper.ClientWarnRulesMapper;
import com.example.service.ClientWarnRulesService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientWarnRulesServiceImpl extends ServiceImpl<ClientWarnRulesMapper, ClientWarnRules> implements ClientWarnRulesService {

    @Resource
    ClientWarnRulesMapper clientWarnRulesMapper;

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
                .set("description",warnRule.getDescription()).update();
    }

    @Override
    public void deleteWarnRule(Integer id) {
        this.removeById(id);
    }

    @Override
    public List<ClientWarnRulesVO> listAllWarnRules() {
        return clientWarnRulesMapper.getAllList();
    }
}
