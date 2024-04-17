package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.dto.DevelopSubtask;
import com.example.entity.vo.request.SubtaskStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface DevelopSubtaskMapper extends BaseMapper<DevelopSubtask> {
}
