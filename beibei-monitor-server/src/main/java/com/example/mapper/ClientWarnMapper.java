package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.dto.ClientWarn;
import com.example.entity.dto.ClientWarnRules;
import com.example.entity.vo.request.WarnVO;
import com.example.entity.vo.response.YesterdayWarnVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClientWarnMapper extends BaseMapper<ClientWarn> {

    @Delete("DELETE FROM db_client_warn WHERE time < DATE_SUB(NOW(), INTERVAL 3 DAY);")
    void deleteOldWarnLogs();

    @Select("SELECT cw.*,c.name AS client_name " +
            "FROM db_client_warn AS cw " +
            "JOIN db_client AS c ON cw.client_id = c.id " +
            "WHERE DATE(cw.time) = DATE_SUB(CURDATE(), INTERVAL 1 DAY);")
    List<YesterdayWarnVO> getYesterdayWarn();
}
