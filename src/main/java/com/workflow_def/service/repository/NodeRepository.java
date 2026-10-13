package com.workflow_def.service.repository;

import com.workflow_def.service.model.NodeDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NodeRepository extends JpaRepository<NodeDetailsModel, Integer> {

    /* ============================= For NodeDetailsModel ============================= */
    @Query(value = "select workflowId from NodeDetailsModel")
    List<String> findAllNodes();
    /* ============================= END ============================= */

}