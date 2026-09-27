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

    /*
    @PostMapping(value = "/insert")
    public void putData(@RequestBody WorkflowDTO wfDTO) {
    }
     */

    //@PostMapping(value = "/update/{wfcode}/{tenantId}")
    @GetMapping(value = "/update/{wfcode}/{tenantId}")
    public @ResponseBody AppResponseDTO updateData(@PathVariable("wfcode") String wfCode, @PathVariable("tenantId") String tenantId) {

        return workflowService.processUpdateData(wfCode, tenantId);

    }

}