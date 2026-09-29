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
        WorkflowModel newModel = null;

        try {

            List<WorkflowModel> models = workflowRepository.findWorkflowByWorkflowCodeAndTenantId(workflowDTO.getWorkflowCode(), workflowDTO.getTenantId());
            WorkflowModel draftModel = null;

            for(WorkflowModel model : models) {
                if(model.getStatusFlag().equals("DRAFT")) {
                    draftModel = model;
                    break;
                }
            }

            if(draftModel != null && draftModel.getStatusFlag().equals("DRAFT")) {


                draftModel.setEntityName(workflowDTO.getEntityName());
                draftModel.setUniqueField(workflowDTO.getUniqueField());
                draftModel.setBpmnXML(workflowDTO.getBpmnXML());

                workflowRepository.save(draftModel);

            } else {

                newModel = new WorkflowModel();

                newModel.setWorkflowName("DEMO");
                newModel.setBpmnXML(workflowDTO.getBpmnXML());
                newModel.setUniqueField(workflowDTO.getUniqueField());
                newModel.setWorkflowTypeCode(workflowDTO.getWorkflowTypeCode());
                newModel.setWorkflowDescription(workflowDTO.getWorkflowDescription());
                newModel.setWorkflowCode(workflowDTO.getWorkflowCode());
                newModel.setTenantId(workflowDTO.getTenantId());
                newModel.setEntityName(workflowDTO.getEntityName());
                newModel.setStatusFlag("DRAFT");
                newModel.setWorkflowVersion(0);
                newModel.setWorkflowId(workflowDTO.getWorkflowCode() + "_" + 0);
                workflowRepository.save(newModel);

            }

            appDto = new AppResponseDTO("SUCCESS", "200", newModel == null ? draftModel : newModel, null);

        } catch(Exception e) {

            appDto = new AppResponseDTO("FAILURE", "500", null, e.getMessage());

        }

        return appDto;

    }

    @Override
    public AppResponseDTO processActivate(String wfCode, String wfId) {

        AppResponseDTO appDto = null;

        WorkflowModel model = null;

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

            appDto = new AppResponseDTO("SUCCESS", "200", model, null);

        } catch(Exception e) {

            appDto = new AppResponseDTO("FAILURE", "500", null, e.getMessage());

        }

        return appDto;

    }

}