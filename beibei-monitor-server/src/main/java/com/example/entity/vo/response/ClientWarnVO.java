package com.example.entity.vo.response;
import com.example.entity.BaseData;
import lombok.Data;


@Data
public class ClientWarnVO implements BaseData {
    Integer id;
    Integer clientId;
    String detail;
    String time;
    String description;
}
