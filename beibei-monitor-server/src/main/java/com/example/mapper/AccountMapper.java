package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.dto.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    @Select("select clients from db_account where id = #{userId}")
    String getClientsById(Integer userId);
}
