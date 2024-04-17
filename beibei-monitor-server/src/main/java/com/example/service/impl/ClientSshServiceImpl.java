package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.ClientDetail;
import com.example.entity.dto.ClientSsh;
import com.example.entity.vo.request.RuntimeDetailVO;
import com.example.entity.vo.request.SshSaveVO;
import com.example.entity.vo.response.SshConnectionVO;
import com.example.entity.vo.request.SshTestVO;
import com.example.entity.vo.response.SshListVO;
import com.example.entity.vo.response.SshSettingsVO;
import com.example.mapper.ClientDetailMapper;
import com.example.mapper.ClientSshMapper;
import com.example.service.ClientSshService;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.Objects;

@Service
public class ClientSshServiceImpl extends ServiceImpl<ClientSshMapper, ClientSsh> implements ClientSshService {
    @Resource
    ClientServiceImpl clientServiceImpl;

    @Resource
    ClientDetailMapper detailMapper;

    private boolean isOnline(RuntimeDetailVO runtime) {
        return runtime != null && System.currentTimeMillis() - runtime.getTimestamp() < 60 * 1000;
    }
    @Override
    public List<SshListVO> getAlllist(Integer userId) {
        List<SshListVO> sshListVOS = this.baseMapper.getListByUserId(userId);
        for (SshListVO sshListVO : sshListVOS){
            sshListVO.setIsOnLine(false);
            if (Objects.nonNull(clientServiceImpl.currentRuntime)){
                RuntimeDetailVO runtimeDetailVO = clientServiceImpl.currentRuntime.get(sshListVO.getClientId());
                if (runtimeDetailVO != null)
                    sshListVO.setIsOnLine(isOnline(runtimeDetailVO));
            }
        }
        return sshListVOS;
    }


    @Override
    public SshConnectionVO saveClientSshConnection(SshSaveVO vo, Integer userId) {
        // 获取客户端ip地址
        ClientDetail detail = detailMapper.selectById(vo.getClientId());
        if (detail == null)
            return null;
        String ip = detail.getIp();
        ClientSsh clientSsh;
        // 判断是否已经存在连接
        if (Objects.nonNull(vo.getId())) {
            clientSsh = new ClientSsh();
            BeanUtils.copyProperties(vo, clientSsh);
            clientSsh.setIp(ip);
            this.updateById(clientSsh);
        }else {
            clientSsh = new ClientSsh();
            BeanUtils.copyProperties(vo,clientSsh);
            clientSsh.setIp(ip);
            clientSsh.setUserId(userId);
            this.save(clientSsh);
        }
        SshConnectionVO sshConnectionVO = new SshConnectionVO();
        BeanUtils.copyProperties(clientSsh,sshConnectionVO);
        return sshConnectionVO;
    }

    //TODO 不知道干嘛用的
    @Override
    public SshSettingsVO sshSettings(Integer clientId) {
        ClientDetail detail = detailMapper.selectById(clientId);
        ClientSsh ssh = this.getBaseMapper().selectById(clientId);
        SshSettingsVO vo;
        if (ssh == null) {
            vo = new SshSettingsVO();
        } else {
            vo = ssh.asViewObject(SshSettingsVO.class);
        }
        vo.setIp(detail.getIp());
        return vo;
    }

    @Override
    public String testConnection(SshTestVO sshTestVO) {
        ClientDetail clientDetail = detailMapper.selectById(sshTestVO.getClientId());
        if (clientDetail == null)
            return "服务器并未在系统注册";
        sshTestVO.setIp(clientDetail.getIp());
        return testSsh(sshTestVO);
    }

    private  String testSsh(SshTestVO ssh) {
        try {
            JSch jSch = new JSch();
            com.jcraft.jsch.Session js = jSch.getSession(ssh.getUsername(), ssh.getIp(), ssh.getPort());
            js.setPassword(ssh.getPassword());
            //设置首次登录跳过主机检查
            js.setConfig("StrictHostKeyChecking", "no");
            js.setTimeout(3000);
            js.connect();
            js.disconnect();
            return "连接成功";
        } catch (JSchException e) {
            String message = e.getMessage();
            if (message.equals("Auth fail")) {
                log.error("连接SSH失败，用户名或密码错误，登录失败");
                return "登录SSH失败，用户名或密码错误";
            } else if (message.contains("Connection refused")) {
                log.error("连接SSH失败，连接被拒绝，可能是没有启动SSH服务或是放开端口");
                return "连接被拒绝，可能是没有启动SSH服务或是放开端口";
            } else {
                log.error("连接SSH时出现错误", e);
                return "无法连接，异常信息" + e.getMessage();
            }
        }
    }
}
