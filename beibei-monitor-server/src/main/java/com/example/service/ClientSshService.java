package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.ClientSsh;
import com.example.entity.vo.request.SshSaveVO;
import com.example.entity.vo.response.SshConnectionVO;
import com.example.entity.vo.request.SshTestVO;
import com.example.entity.vo.response.SshListVO;
import com.example.entity.vo.response.SshSettingsVO;

import java.util.List;

public interface ClientSshService extends IService<ClientSsh> {
    List<SshListVO> getAlllist(Integer userId);
    SshConnectionVO saveClientSshConnection(SshSaveVO vo, Integer userId);
    SshSettingsVO sshSettings(Integer clientId);
    String testConnection(SshTestVO sshTestVO);

    String deleteClientSsh(Integer id, Integer userId, String role);

    List<SshConnectionVO> getSshByClient(Integer clientId,Integer userId);
}
