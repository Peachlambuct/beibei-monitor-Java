package com.example.controller;

import com.example.entity.RestBean;
import com.example.entity.dto.Account;
import com.example.entity.vo.request.SshConnectionVO;
import com.example.entity.vo.request.SshTestVO;
import com.example.entity.vo.response.SshListVO;
import com.example.entity.vo.response.SshSettingsVO;
import com.example.service.AccountService;
import com.example.service.ClientSshService;
import com.example.utils.Const;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/ssh")
public class SshController {
    @Resource
    ClientSshService clientSshService;

    @Resource
    AccountService accountService;

    @GetMapping("/list")
    public RestBean<List<SshListVO>> list(@RequestAttribute(Const.ATTR_USER_ID) Integer userId) {
        return RestBean.success(clientSshService.getAlllist(userId));
    }

    @PostMapping("/ssh-test")
    public RestBean<String> sshTestConnection(@RequestBody @Valid SshTestVO sshTestVO){
        String result = clientSshService.testConnection(sshTestVO);
        if ("连接成功".equals(result)){
            return RestBean.success(result);
        }else
            return RestBean.failure(403,result);
    }

    @PostMapping("/ssh-save")
    public RestBean<Void> saveSshConnection(@RequestBody @Valid SshConnectionVO vo,
                                            @RequestAttribute(Const.ATTR_USER_ID) Integer userId,
                                            @RequestAttribute(Const.ATTR_USER_ROLE) String userRole) {
        clientSshService.saveClientSshConnection(vo, userId);
        return RestBean.success();
    }

    @GetMapping("/ssh")
    public RestBean<SshSettingsVO> sshSettings(Integer clientId,
                                               @RequestAttribute(Const.ATTR_USER_ID) Integer userId,
                                               @RequestAttribute(Const.ATTR_USER_ROLE) String userRole) {
        if (this.permissionCheck(userId, userRole, clientId)) {
            return RestBean.success(clientSshService.sshSettings(clientId));
        } else {
            return RestBean.noPermission();
        }
    }

    private List<Integer> accountAccessClients(int uid) {
        Account account = accountService.getById(uid);
        return account.getClientList();
    }

    private boolean isAdminAccount(String role) {
        role = role.substring(5);
        return Const.ROLE_ADMIN.equals(role);
    }

    private boolean permissionCheck(int uid, String role, int clientId) {
        if (this.isAdminAccount(role)) return true;
        return this.accountAccessClients(uid).contains(clientId);
    }
}
