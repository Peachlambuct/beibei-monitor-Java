package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.ClientWarn;
import com.example.entity.vo.request.WarnVO;
import com.example.entity.vo.response.ClientWarnVO;
import com.example.entity.vo.response.YesterdayWarnVO;

import java.util.List;

public interface ClientWarnService extends IService<ClientWarn> {
    void processWarn(WarnVO warnVO, int clientId);

    List<ClientWarnVO> listAllWarn();

    List<YesterdayWarnVO> listYesterdayWarn(String role,Integer userId);
}
