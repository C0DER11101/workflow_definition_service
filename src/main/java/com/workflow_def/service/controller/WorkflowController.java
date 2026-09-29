package com.workflow_def.service.controller;

import com.workflow_def.service.dto.AppResponseDTO;
import com.workflow_def.service.dto.WorkflowDTO;
import com.workflow_def.service.service.WorkflowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class WorkflowController {

    WorkflowService workflowService;

    @PostMapping(value = "/insert")
    public void insert(@RequestBody WorkflowDTO workflowDTO) {
    }

    @PostMapping(value = "/update")
    public void update(@RequestBody WorkflowDTO wfDTO) {
        workflowService.processUpdateData(wfDTO);
    }

}