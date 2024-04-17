package com.example.controller;

import com.example.entity.RestBean;
import com.example.entity.dto.Client;
import com.example.entity.dto.ClientWarnRules;
import com.example.entity.vo.request.ClientDetailVO;
import com.example.entity.vo.request.RuntimeDetailVO;
import com.example.entity.vo.request.WarnVO;
import com.example.entity.vo.response.ClientNameVO;
import com.example.service.ClientService;
import com.example.service.ClientWarnRulesService;
import com.example.service.ClientWarnService;
import com.example.utils.Const;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.GET;

import java.util.List;

@RestController
@RequestMapping("/monitor")
public class ClientController {

    @Resource
    ClientService clientService;

    @Resource
    ClientWarnService clientWarnService;


    @GetMapping("/register")
    public RestBean<Void> registerClient(@RequestHeader("Authorization") String token) {
        return clientService.verifyAndRegister(token) ?
                RestBean.success() : RestBean.failure(401, "客户端注册失败，请检查Token是否正确");
    }

    @PostMapping("/detail")
    public RestBean<Void> updateClientDetails(@RequestAttribute(Const.ATTR_CLIENT) Client client,
                                              @RequestBody @Valid ClientDetailVO vo) {
        clientService.updateClientDetail(vo, client);
        return RestBean.success();
    }

    @PostMapping("/runtime")
    public RestBean<Void> updateRuntimeDetails(@RequestAttribute(Const.ATTR_CLIENT) Client client,
                                               @RequestBody @Valid RuntimeDetailVO vo) {
        clientService.updateRuntimeDetail(vo, client);
        return RestBean.success();
    }

    @PostMapping("/processWarn")
    public RestBean<Void> processWarn(@RequestBody WarnVO warnVO,
                                      @RequestAttribute(Const.ATTR_CLIENT) Client client) {
        clientWarnService.processWarn(warnVO,client.getId());
        return RestBean.success();
    }
}
