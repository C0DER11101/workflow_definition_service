package com.workflow_def.service.repository;

import com.workflow_def.service.model.WorkflowModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkflowRepository extends JpaRepository<WorkflowModel, Integer> {

    @Query(value = "from WorkflowModel where workflowCode=:wfCode and tenantId=:tenantId")
    WorkflowModel getData(String wfCode, String tenantId);
}