package com.example.controller;


import com.example.entity.RestBean;
import com.example.entity.dto.ClientWarn;
import com.example.entity.vo.response.ClientWarnVO;
import com.example.entity.vo.response.YesterdayWarnVO;
import com.example.service.ClientWarnService;
import com.example.utils.Const;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warn")
public class WarnController {

    @Resource
    ClientWarnService clientWarnService;

    @GetMapping("/list")
    public RestBean<List<ClientWarnVO>> listAllWarn(){
        return RestBean.success(clientWarnService.listAllWarn());
    }

    @GetMapping("/yesterday")
    public RestBean<List<YesterdayWarnVO>> listYesterdayWarn(@RequestAttribute(Const.ATTR_USER_ROLE) String role,
                                                             @RequestAttribute(Const.ATTR_USER_ID) Integer userId){
        return RestBean.success(clientWarnService.listYesterdayWarn(role, userId));
    }
}
