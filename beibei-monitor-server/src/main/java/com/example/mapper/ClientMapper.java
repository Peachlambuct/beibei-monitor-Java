package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.dto.Client;
import com.example.entity.vo.response.ClientNameVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClientMapper extends BaseMapper<Client> {
    @Select("SELECT email FROM db_account WHERE JSON_CONTAINS(clients, CONCAT('\"', #{cid}, '\"'))")
    List<String> findEmailsByClientId(@Param("cid") int clientId);

    @Select("SELECT db_client.id as clientId, db_client.name as clientName, ip " +
            "FROM db_client,db_client_detail " +
            "WHERE db_client.id = db_client_detail.id")
    List<ClientNameVO> getByClientName();
}
