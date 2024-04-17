package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.dto.ClientSsh;
import com.example.entity.vo.response.SshListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClientSshMapper extends BaseMapper<ClientSsh> {

    @Select("select  ssh.*,clt.location,clt.name as clientName " +
            "from db_client_ssh as ssh,db_client as clt " +
            "where ssh.client_id = clt.id " +
            "and user_id = #{userId}")
    List<SshListVO> getListByUserId(Integer userId);
}
