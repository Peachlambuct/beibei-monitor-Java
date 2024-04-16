package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.dto.DevelopTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DevelopTaskMapper extends BaseMapper<DevelopTask> {
    @Select("SELECT db_develop_task.* " +
            "FROM db_develop_task " +
            "JOIN db_account ON JSON_CONTAINS(db_account.clients, CAST(db_develop_task.about_client_id AS JSON)) " +
            "WHERE db_account.id = #{userId};")
    List<DevelopTask> getAllByUserId(Integer userId);
}
