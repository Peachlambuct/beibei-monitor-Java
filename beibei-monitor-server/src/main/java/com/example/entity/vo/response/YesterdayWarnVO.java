package com.example.entity.vo.response;
import com.example.entity.BaseData;
import com.example.entity.dto.WarnProcessInfo;
import lombok.Data;

import java.util.List;


@Data
public class YesterdayWarnVO implements BaseData {
    Integer id;
    String clientName;
    Integer clientId;
    String time;
    String description;
}
