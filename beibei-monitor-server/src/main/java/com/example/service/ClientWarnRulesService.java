package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.ClientWarnRules;
import com.example.entity.vo.request.WarnVO;

public interface ClientWarnRulesService extends IService<ClientWarnRules> {
    void addWarnRule(ClientWarnRules warnRule);
    void updateWarnRule(ClientWarnRules warnRule);
    void deleteWarnRule(Integer id);
}
