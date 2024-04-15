package com.example.controller;


import com.example.entity.RestBean;
import com.example.entity.dto.ClientWarn;
import com.example.entity.vo.response.ClientWarnVO;
import com.example.service.ClientWarnService;
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
}
