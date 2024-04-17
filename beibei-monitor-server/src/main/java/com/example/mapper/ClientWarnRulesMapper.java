package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.dto.ClientWarn;
import com.example.entity.dto.ClientWarnRules;
import com.example.entity.vo.response.ClientWarnRulesVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClientWarnRulesMapper extends BaseMapper<ClientWarnRules> {
    @Select("select * from db_client_warn_rules where client_id = #{clientId}")
    ClientWarnRules getInfoByClientId(Integer clientId);

    @Select("select db_client_warn_rules.*,dc.name as clientName " +
            "from db_client as dc, db_client_warn_rules " +
            "where dc.id = db_client_warn_rules.client_id")
    List<ClientWarnRulesVO> getAllList();
}