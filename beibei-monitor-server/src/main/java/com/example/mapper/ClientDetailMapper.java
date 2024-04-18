package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.dto.ClientDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClientDetailMapper extends BaseMapper<ClientDetail> {
    @Select("select id from db_client_detail where ip = #{currentIp}")
    Integer getIdByIp(String currentIp);
}
