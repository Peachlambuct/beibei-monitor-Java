package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.dto.ClientWarn;
import com.example.entity.dto.ClientWarnRules;
import com.example.entity.vo.response.ClientWarnRulesVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import scala.Int;

import java.util.List;

@Mapper
public interface ClientWarnRulesMapper extends BaseMapper<ClientWarnRules> {
    @Select("select * from db_client_warn_rules where client_id = #{clientId}")
    ClientWarnRules getInfoByClientId(Integer clientId);

    @Select("select dcwr.*,dc.name as clientName " +
            "from db_client as dc, db_client_warn_rules as dcwr " +
            "where dc.id = dcwr.client_id and dcwr.user_id = #{userId}")
    List<ClientWarnRulesVO> getAllList(Integer userId);
}