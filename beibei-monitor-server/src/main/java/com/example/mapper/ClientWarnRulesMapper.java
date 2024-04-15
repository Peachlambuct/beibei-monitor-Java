package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.dto.ClientWarn;
import com.example.entity.dto.ClientWarnRules;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClientWarnRulesMapper extends BaseMapper<ClientWarnRules> {
    @Select("select * from db_client_warn_rules where client_id = #{clientId}")
    ClientWarnRules getInfoByClientId(Integer clientId);
}
