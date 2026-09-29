package com.workflow_def.service.service;

import com.workflow_def.service.dto.AppResponseDTO;
import com.workflow_def.service.dto.WorkflowDTO;

public interface WorkflowService {

    AppResponseDTO processUpdateData(WorkflowDTO workflowDTO);
    AppResponseDTO processActivate(String wfCode, String wfId);

}