package com.example.entity.vo.response;
import com.example.entity.BaseData;
import com.example.entity.dto.WarnProcessInfo;
import lombok.Data;

import java.util.List;


@Data
public class ClientWarnVO implements BaseData {
    Integer id;
    Integer clientId;
    List<WarnProcessInfo> detail;
    String time;
    String description;
}
