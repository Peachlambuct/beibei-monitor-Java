package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.dto.ClientWarn;
import com.example.entity.dto.ClientWarnRules;
import com.example.entity.vo.request.WarnVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClientWarnMapper extends BaseMapper<ClientWarn> {

    @Delete("DELETE FROM db_client_warn WHERE time < DATE_SUB(NOW(), INTERVAL 3 DAY);")
    void deleteOldWarnLogs();
}
