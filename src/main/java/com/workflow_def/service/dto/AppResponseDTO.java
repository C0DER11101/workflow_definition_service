package com.workflow_def.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppResponseDTO {

    private String status;
    private String statusCode;
    private Object data;
    private String errMsg;

}