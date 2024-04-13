package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.dto.Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClientMapper extends BaseMapper<Client> {
    @Select("SELECT email FROM db_account WHERE JSON_CONTAINS(clients, CONCAT('\"', #{cid}, '\"'))")
    List<String> findEmailsByClientId(@Param("cid") int clientId);
}
