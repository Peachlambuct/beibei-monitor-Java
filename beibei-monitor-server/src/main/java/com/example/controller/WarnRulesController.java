package com.example.controller;


import com.example.entity.RestBean;
import com.example.entity.dto.ClientWarnRules;
import com.example.service.ClientWarnRulesService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warnRules")
public class WarnRulesController {

    @Resource
    ClientWarnRulesService clientWarnRulesService;

    @PostMapping("/addWarnRule")
    public RestBean<Void> addWarnRule(@RequestBody ClientWarnRules warnRule) {
        clientWarnRulesService.addWarnRule(warnRule);
        return RestBean.success();
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
    public RestBean<List<ClientWarnRules>> listAllWarnRules() {
        return RestBean.success(clientWarnRulesService.list());
    }
}
