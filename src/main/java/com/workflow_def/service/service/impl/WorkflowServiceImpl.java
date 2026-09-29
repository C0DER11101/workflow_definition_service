package com.workflow_def.service.service.impl;

import com.workflow_def.service.dto.AppResponseDTO;
import com.workflow_def.service.dto.WorkflowDTO;
import com.workflow_def.service.model.WorkflowModel;
import com.workflow_def.service.repository.WorkflowRepository;
import com.workflow_def.service.service.WorkflowService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WorkflowServiceImpl implements WorkflowService {

    WorkflowRepository workflowRepository;

    @Override
    public AppResponseDTO processUpdateData(WorkflowDTO workflowDTO) {


        AppResponseDTO appDto = null;

        try {

            WorkflowModel model = workflowRepository.findWorkflowByWorkflowCodeAndTenantId(workflowDTO.getWorkflowCode(), workflowDTO.getTenantId());
            WorkflowModel model1 = null;

            if(model.getStatusFlag().equals("DRAFT")) {

                model.setEntityName(workflowDTO.getEntityName());
                model.setUniqueField(workflowDTO.getUniqueField());
                model.setBpmnXML(workflowDTO.getBpmnXML());

                workflowRepository.save(model);

            } else {
                model1 = new WorkflowModel();
                model1.setWorkflowName("DEMO");
                model1.setBpmnXML(workflowDTO.getBpmnXML());
                model1.setUniqueField(workflowDTO.getUniqueField());
                model1.setWorkflowTypeCode(workflowDTO.getWorkflowTypeCode());
                model1.setWorkflowDescription(workflowDTO.getWorkflowDescription());
                model1.setWorkflowCode(workflowDTO.getWorkflowCode());
                model1.setTenantId(workflowDTO.getTenantId());
                model1.setEntityName(workflowDTO.getEntityName());
                model1.setStatusFlag("DRAFT");
                model1.setWorkflowVersion(0);
                model1.setWorkflowId(workflowDTO.getWorkflowCode() + "_" + 0);
                workflowRepository.save(model1);
            }

            appDto = new AppResponseDTO("SUCCESS", "200", model1, null);

        } catch(Exception e) {

            appDto = new AppResponseDTO("FAILURE", "500", null, e.getMessage());

        }

        return appDto;

    }

    @Override
    public AppResponseDTO processActivate(String wfCode, String wfId) {

        AppResponseDTO appDto = null;

        WorkflowModel model = null;
        WorkflowModel model1 = null;

        try {

            model = workflowRepository.findWorkflowByWorkflowCodeAndWorkflowId(wfCode, wfId);

            List<WorkflowModel> modelList = workflowRepository.findWorkflowByWorkflowCode(wfCode);

            if(model.getStatusFlag().equals("DRAFT")) {

                for (WorkflowModel m : modelList) {

                    if (m.getStatusFlag().equals("ACTIVE")) {

                        m.setStatusFlag("INACTIVE");
                        workflowRepository.save(m);
                        break;

                    }
                }

                int maxVersion = workflowRepository.findMaximumWorkflowVersionByWorkflowCode(wfCode);

                model.setStatusFlag("ACTIVE");
                model.setWorkflowVersion(maxVersion + 1);
                model.setWorkflowId(model.getWorkflowCode() + "_" + model.getWorkflowVersion());

                workflowRepository.save(model);


            } else if(model.getStatusFlag().equals("INACTIVE")) {

                for (WorkflowModel m : modelList) {

                    if (m.getStatusFlag().equals("ACTIVE")) {

                        m.setStatusFlag("INACTIVE");
                        workflowRepository.save(m);
                        break;

                    }
                }

                model.setStatusFlag("ACTIVE");

            }

            appDto = new AppResponseDTO("SUCCESS", "200", model1, null);

        } catch(Exception e) {

            appDto = new AppResponseDTO("FAILURE", "500", null, e.getMessage());

        }

        return appDto;

    }

}