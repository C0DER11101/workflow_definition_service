package com.workflow_def.service.controller;

import com.workflow_def.service.dto.AppResponseDTO;
import com.workflow_def.service.dto.WorkflowDTO;
import com.workflow_def.service.service.WorkflowService;
import com.workflow_def.service.util.DecodeXMLUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class WorkflowController {

    WorkflowService workflowService;

    @PostMapping(value = "/activate/{wfCode}/{wfId}")
    public @ResponseBody AppResponseDTO activate(@PathVariable("wfCode") String wfCode, @PathVariable("wfId") String wfId) {
        return workflowService.processActivate(wfCode, wfId);
    }

    @PostMapping(value = "/update")
    public @ResponseBody AppResponseDTO update(@RequestBody WorkflowDTO wfDTO) {
        return workflowService.processUpdateData(wfDTO);
    }

    @GetMapping(value = "/getxml/{id}")
    public @ResponseBody String getBpmnXML(@PathVariable("id") int id) {
        StringBuilder xml = new StringBuilder();
        byte[] xmlContent = DecodeXMLUtil.decodeXML(workflowService.processGetBpmnXML(id));

        for(byte b : xmlContent)
            xml.append((char)b);

        return xml.toString();
    }

}