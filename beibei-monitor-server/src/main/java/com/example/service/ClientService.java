package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.dto.Client;
import com.example.entity.dto.ClientWarnRules;
import com.example.entity.vo.request.*;
import com.example.entity.vo.response.*;

import java.util.List;

public interface ClientService extends IService<Client> {
    String registerToken();
    Client findClientById(int id);
    Client findClientByToken(String token);
    boolean verifyAndRegister(String token);
    void updateClientDetail(ClientDetailVO vo, Client client);
    void updateRuntimeDetail(RuntimeDetailVO vo, Client client);
    List<ClientPreviewVO> listClients();
    List<ClientSimpleVO> listSimpleList();
    void renameClient(RenameClientVO vo);
    void renameNode(RenameNodeVO vo);
    ClientDetailsVO clientDetails(int clientId);
    RuntimeHistoryVO clientRuntimeDetailsHistory(int clientId);
    RuntimeDetailVO clientRuntimeDetailsNow(int clientId);
    void deleteClient(int clientId);
    List<ClientNameVO> getClientNameList(Integer userId, String role);
    RuntimeDetailVO getCurrentClientDetails(String role);
}
