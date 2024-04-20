package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.dto.DevelopSubtask;
import com.example.entity.vo.request.SubtaskStatusVO;
import com.example.entity.vo.response.SubtaskVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DevelopSubtaskMapper extends BaseMapper<DevelopSubtask> {

    @Select("select s.*, t.about_client_id as aboutClients, t.type as taskType, t.principal_ids as principals, u.username as updateUserName " +
            "from db_develop_subtask s " +
            "join db_develop_task t on s.task_id = t.id " +
            "left join db_account u on s.update_user_id = u.id")
    List<SubtaskVO> getSubtask();
}
