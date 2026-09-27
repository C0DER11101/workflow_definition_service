package com.workflow_def.service.dto;

import lombok.Data;

@Data
public class WorkflowDTO {

    private String tenantId;
    private String workflowCode;
    private String workflowDescription;
    private String workflowTypeCode;
    private String entityName; // to be updated
    private int uniqueField; // to be updated
    private String bpmnXML; // to be updated

}