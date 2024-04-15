package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.ClientWarn;
import com.example.entity.vo.request.WarnVO;

public interface ClientWarnService extends IService<ClientWarn> {
    void processWarn(WarnVO warnVO, int clientId);
}
