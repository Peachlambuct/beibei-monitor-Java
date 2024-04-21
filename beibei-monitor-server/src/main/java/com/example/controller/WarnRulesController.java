package com.example.controller;


import com.example.entity.RestBean;
import com.example.entity.dto.ClientWarnRules;
import com.example.entity.vo.response.ClientWarnRulesVO;
import com.example.service.ClientWarnRulesService;
import com.example.utils.Const;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warnRules")
public class WarnRulesController {

    @Resource
    ClientWarnRulesService clientWarnRulesService;

    @PostMapping("/addWarnRule")
    public RestBean<String> addWarnRule(@RequestBody ClientWarnRules warnRule,
                                        @RequestAttribute(Const.ATTR_USER_ID) Integer userId) {
        String res = clientWarnRulesService.addWarnRule(warnRule,userId);
        if ("添加成功".equals(res)){
            return RestBean.success(res);
        }else {
            return RestBean.failure(400,res);
        }
    }

    @PostMapping("/updateWarnRule")
    public RestBean<Void> updateWarnRule(@RequestBody ClientWarnRules warnRule) {
        clientWarnRulesService.updateWarnRule(warnRule);
        return RestBean.success();
    }

    @GetMapping("/deleteWarnRule")
    public RestBean<Void> deleteWarnRule(@RequestParam("id") Integer id) {
        clientWarnRulesService.deleteWarnRule(id);
        return RestBean.success();
    }

    @GetMapping("/list")
    public RestBean<List<ClientWarnRulesVO>> listAllWarnRules(@RequestAttribute(Const.ATTR_USER_ID) Integer userId) {
        return RestBean.success(clientWarnRulesService.listAllWarnRules(userId));
    }
}
