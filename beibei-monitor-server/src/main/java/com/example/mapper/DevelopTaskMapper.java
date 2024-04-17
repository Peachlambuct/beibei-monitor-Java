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
            "WHERE JSON_CONTAINS(principal_ids, CAST(#{userId} AS JSON));")
    List<DevelopTask> getAllByUserId(Integer userId);

    @Select("select id from db_develop_task where end_time < DATE_SUB(NOW(), INTERVAL -30 DAY) and status = 2;")
    List<Integer> getExpiredTaskIds();
}
