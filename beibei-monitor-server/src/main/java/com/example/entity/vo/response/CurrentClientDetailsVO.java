package com.example.entity.vo.response;

import com.example.entity.vo.request.RuntimeDetailVO;
import lombok.Data;

@Data
public class CurrentClientDetailsVO {
    ClientDetailsVO clientDetailsVO;
    RuntimeDetailVO runtimeDetailVO;
    RuntimeHistoryVO runtimeHistoryVO;
}
