package com.workflow_def.service.service.impl;

import com.workflow_def.service.dto.AppResponseDTO;
import com.workflow_def.service.dto.WorkflowDTO;
import com.workflow_def.service.model.WorkflowModel;
import com.workflow_def.service.repository.WorkflowRepository;
import com.workflow_def.service.service.WorkflowService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

            // check if there is any workflow with status 'DRAFT' for the given workflow code and tenant id
            for(WorkflowModel model : models) {
                if(model.getStatusFlag().equals("DRAFT")) {
                    draftModel = model;
                    break;
                }
            }

            // if a workflow exists for the given parameters
            if(draftModel != null && draftModel.getStatusFlag().equals("DRAFT")) {

                // then set the entity name, unique field and bpmn xml fields
                draftModel.setEntityName(workflowDTO.getEntityName());
                draftModel.setUniqueField(workflowDTO.getUniqueField());
                draftModel.setBpmnXML(workflowDTO.getBpmnXML());

                workflowRepository.save(draftModel);

            } else {

                // if the workflow doesn't exist, then create a new workflow which will be in 'DRAFT' state
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

    /** Activate a workflow based on the given workflow code and workflow id */
    @Override
    public AppResponseDTO processActivate(String wfCode, String wfId) {

        AppResponseDTO appDto = null;

        WorkflowModel model = null;

        try {

            // retrieve the target workflow
            model = workflowRepository.findWorkflowByWorkflowCodeAndWorkflowId(wfCode, wfId);

            // there can be multiple workflows with the same workflow code
            List<WorkflowModel> modelList = workflowRepository.findWorkflowByWorkflowCode(wfCode);

            // only one of the type of workflows can be activated: ones that are 'DRAFT'  or ones that are 'INACTIVE'
            if(model.getStatusFlag().equals("DRAFT")) {

                // before activating the target workflow, it's important that we de-activate any other workflows (with that workflow code)
                for (WorkflowModel m : modelList) {

                    if (m.getStatusFlag().equals("ACTIVE")) {

                        m.setStatusFlag("INACTIVE");
                        workflowRepository.save(m);
                        break;

                    }
                }

                // retrieving the maximum version among these workflows
                int maxVersion = workflowRepository.findMaximumWorkflowVersionByWorkflowCode(wfCode);

                model.setStatusFlag("ACTIVE");
                model.setWorkflowVersion(maxVersion + 1); // the newly activated workflow has a version one more than the maximum version
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


            // ========================== Node creation phase ==========================
            // create the node for this model
            // first check if node exists for this model
            List<String> workFlowIds = workflowRepository.findAllNodes();

            if(workFlowIds == null) {
                // add the node into the table
            } else {

                // filtering out the node of the current workflow code
                List<String> nodesOfThisWorkflowCode =
                        workFlowIds.stream()
                                .filter(id -> id.equals(wfCode.split("_")[0]))
                                .collect(Collectors.toList());
                if(nodesOfThisWorkflowCode == null) {
                    // do something
                } else {
                    // do something
                }
            }

            appDto = new AppResponseDTO("SUCCESS", "200", model, null);

        } catch(Exception e) {

            appDto = new AppResponseDTO("FAILURE", "500", null, e.getMessage());

        }

        return appDto;

    }

    @Override
    public String processGetBpmnXML(int id) {
        return workflowRepository.findBpmnXMLById(id);
    }

}