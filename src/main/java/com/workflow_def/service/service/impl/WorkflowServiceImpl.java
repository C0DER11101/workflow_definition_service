package com.workflow_def.service.service.impl;

import com.workflow_def.service.dto.AppResponseDTO;
import com.workflow_def.service.model.WorkflowModel;
import com.workflow_def.service.repository.WorkflowRepository;
import com.workflow_def.service.service.WorkflowService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WorkflowServiceImpl implements WorkflowService {

    WorkflowRepository workflowRepository;

    @Override
    public AppResponseDTO processUpdateData(String wfCode, String tenantId) {


        AppResponseDTO appDto = null;

        try {

            WorkflowModel model = workflowRepository.getData(wfCode, tenantId);

            if(model.getStatusFlag().equals("DRAFT")) {
                model.setEntityName("Priyanuj");
                model.setUniqueField(130);

                workflowRepository.save(model);
            }

            appDto = new AppResponseDTO("SUCCESS", "200", model, null);

        } catch(Exception e) {

            appDto = new AppResponseDTO("FAILURE", "500", null, e.getMessage());

        }

        return appDto;

    }

}