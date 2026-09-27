package com.workflow_def.service.service;

import com.workflow_def.service.dto.AppResponseDTO;

public interface WorkflowService {

    AppResponseDTO processUpdateData(String wfCode, String tenantId);

}