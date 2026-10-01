package com.workflow_def.service.repository;

import com.workflow_def.service.model.WorkflowModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkflowRepository extends JpaRepository<WorkflowModel, Integer> {

    @Query(value = "from WorkflowModel where workflowCode=:wfCode and tenantId=:tenantId")
    List<WorkflowModel> findWorkflowByWorkflowCodeAndTenantId(String wfCode, String tenantId);

    @Query(value = "from WorkflowModel where workflowCode=:wfCode and workflowId=:wfId")
    WorkflowModel findWorkflowByWorkflowCodeAndWorkflowId(String wfCode, String wfId);

    @Query(value = "from WorkflowModel where workflowCode=:wfCode")
    List<WorkflowModel> findWorkflowByWorkflowCode(String wfCode);

    @Query(value = "select max(workflowVersion) from WorkflowModel where workflowCode=:wfCode")
    int findMaximumWorkflowVersionByWorkflowCode(String wfCode);

    @Query(value = "select bpmnXML from WorkflowModel where id=:id")
    String findBpmnXMLById(int id);

}