package com.example.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.dto.*;
import com.example.entity.vo.request.*;
import com.example.entity.vo.response.*;
import com.example.mapper.*;
import com.example.service.ClientService;
import com.example.utils.Const;
import com.example.utils.InfluxDbUtils;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client> implements ClientService {

    private String currentIp;

    private String registerToken = this.generateNewToken();

    final Map<Integer, Client> clientIdCache = new ConcurrentHashMap<>();
    private final Map<String, Client> clientTokenCache = new ConcurrentHashMap<>();

    @Resource
    ClientDetailMapper detailMapper;

    @Resource
    AccountMapper accountMapper;

    @Resource
    InfluxDbUtils influx;

    @Resource
    DevelopTaskMapper taskMapper;

    @Resource
    ClientWarnRulesMapper clientWarnRulesMapper;

    @Resource
    ClientSshMapper clientSshMapper;

    public final Map<Integer, RuntimeDetailVO> currentRuntime = new ConcurrentHashMap<>();

    @PostConstruct
    public void initClientCache() {
        try {
            URL url = new URL("http://checkip.amazonaws.com");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            currentIp = br.readLine();
            System.out.println("获取的公网IP" + currentIp);
        } catch (Exception e) {
            log.error("获取IP地址失败");
        }
        clientTokenCache.clear();
        clientIdCache.clear();
        this.list().forEach(this::addClientCache);
    }

    @Override
    public String registerToken() {
        return registerToken;
    }

    @Override
    public Client findClientById(int id) {
        return clientIdCache.get(id);
    }

    @Override
    public Client findClientByToken(String token) {
        return clientTokenCache.get(token);
    }

    @Override
    public boolean verifyAndRegister(String token) {
        if (this.registerToken.equals(token)) {
            int id = this.randomClientId();
            Client client = new Client(id, "未命名主机", token, "cn", "未命名节点", new Date());
            if (this.save(client)) {
                registerToken = this.generateNewToken();
                this.addClientCache(client);
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateClientDetail(ClientDetailVO vo, Client client) {
        ClientDetail detail = new ClientDetail();
        BeanUtils.copyProperties(vo, detail);
        detail.setId(client.getId());
        if (Objects.nonNull(detailMapper.selectById(client.getId()))) {
            detailMapper.updateById(detail);
        } else {
            detailMapper.insert(detail);
        }
    }

    @Override
    public void updateRuntimeDetail(RuntimeDetailVO vo, Client client) {
        currentRuntime.put(client.getId(), vo);
        influx.writeRuntimeData(client.getId(), vo);
    }

    @Override
    public List<ClientPreviewVO> listClients() {
        return clientIdCache.values().stream().map(client -> {
            ClientPreviewVO vo = client.asViewObject(ClientPreviewVO.class);
            BeanUtils.copyProperties(detailMapper.selectById(vo.getId()), vo);
            RuntimeDetailVO runtime = currentRuntime.get(client.getId());
            if (this.isOnline(runtime)) {
                BeanUtils.copyProperties(runtime, vo);
                vo.setOnline(true);
            }
            return vo;
        }).toList();
    }

    @Override
    public List<ClientSimpleVO> listSimpleList() {
        return clientIdCache.values().stream().map(client -> {
            ClientSimpleVO vo = client.asViewObject(ClientSimpleVO.class);
            BeanUtils.copyProperties(detailMapper.selectById(vo.getId()), vo);
            return vo;
        }).toList();
    }

    @Override
    public void renameClient(RenameClientVO vo) {
        this.update(Wrappers.<Client>update().eq("id", vo.getId()).set("name", vo.getName()));
        this.initClientCache();
    }

    @Override
    public void renameNode(RenameNodeVO vo) {
        this.update(Wrappers.<Client>update().eq("id", vo.getId())
                .set("node", vo.getNode()).set("location", vo.getLocation()));
        this.initClientCache();
    }

    @Override
    public ClientDetailsVO clientDetails(int clientId) {
        ClientDetailsVO vo = this.clientIdCache.get(clientId).asViewObject(ClientDetailsVO.class);
        BeanUtils.copyProperties(detailMapper.selectById(clientId), vo);
        vo.setOnline(this.isOnline(currentRuntime.get(clientId)));
        return vo;
    }

    @Override
    public RuntimeHistoryVO clientRuntimeDetailsHistory(int clientId) {
        RuntimeHistoryVO vo = influx.readRuntimeData(clientId);
        ClientDetail detail = detailMapper.selectById(clientId);
        BeanUtils.copyProperties(detail, vo);
        return vo;
    }

    @Override
    public RuntimeDetailVO clientRuntimeDetailsNow(int clientId) {
        return currentRuntime.get(clientId);
    }

    @Override
    @Transactional
    public String deleteClient(int clientId) {
        List<SimpleTaskVO> tasks = taskMapper.selectTasksByClientId(String.valueOf(clientId));
        if (!tasks.isEmpty()) {
            return "该客户端有任务，不允许删除";
        }
        this.removeById(clientId);
        detailMapper.deleteById(clientId);
        clientSshMapper.delete(Wrappers.<ClientSsh>query().eq("client_id", clientId));
        clientWarnRulesMapper.delete(Wrappers.<ClientWarnRules>query().eq("client_id", clientId));
        detailMapper.deleteById(clientId);
        List<Account> accountList = accountMapper.getByClientId(String.valueOf(clientId));
        accountList.forEach(account -> {
            List<Integer> list = account.getClientList().stream().filter(it -> !it.equals(clientId)).toList();
            account.setClients(JSON.toJSONString(list));
            accountMapper.updateById(account);
        });
        this.initClientCache();
        currentRuntime.remove(clientId);
        return null;
    }

    @Override
    public List<ClientNameVO> getClientNameList(Integer userId, String role) {
        List<ClientNameVO> clientNameVOS = baseMapper.getByClientName();
        if (!Const.ROLE_ADMIN.equals(role.substring(5))) {
            String clients = accountMapper.selectById(userId).getClients();
            clientNameVOS = clientNameVOS.stream().filter(vo
                    -> JSON.parseArray(clients, Integer.class).contains(vo.getClientId())).toList();
        }
        return clientNameVOS;
    }

    @Override
    public RuntimeDetailVO getCurrentClientDetails(String role) {
        if (!Const.ROLE_ADMIN.equals(role.substring(5)))
            return null;
        Integer clientId = detailMapper.getIdByIp(currentIp);
        if (clientId == null)
            return null;
        if (isOnline(currentRuntime.get(clientId))) {
            return clientRuntimeDetailsNow(clientId);
        }
        return null;
    }

    public boolean getCurrentClientRunStatus(Integer clientId) {
        return isOnline(currentRuntime.get(clientId));
    }

    private boolean isOnline(RuntimeDetailVO runtime) {
        return runtime != null && System.currentTimeMillis() - runtime.getTimestamp() < 60 * 1000;
    }

    private void addClientCache(Client client) {
        clientIdCache.put(client.getId(), client);
        clientTokenCache.put(client.getToken(), client);
    }

    private int randomClientId() {
        return new Random().nextInt(90000000) + 10000000;
    }

    private String generateNewToken() {
        String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(24);
        for (int i = 0; i < 24; i++)
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        return sb.toString();
    }
}