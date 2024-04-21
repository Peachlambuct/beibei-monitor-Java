package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.ClientWarnRules;
import com.example.entity.vo.response.ClientWarnRulesVO;

import java.util.List;

public interface ClientWarnRulesService extends IService<ClientWarnRules> {
    String addWarnRule(ClientWarnRules warnRule,Integer userId);
    void updateWarnRule(ClientWarnRules warnRule);
    void deleteWarnRule(Integer id);
    List<ClientWarnRulesVO> listAllWarnRules(Integer userId);
}
