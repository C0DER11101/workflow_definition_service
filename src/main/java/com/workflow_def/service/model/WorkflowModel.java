package com.workflow_def.service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "wf_workflow")
public class WorkflowModel {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "wf_id")
    private String workflowId;

    @Column(name = "wf_code")
    private String workflowCode;

    @Column(name = "workflow_name")
    private String workflowName;

    @Column(name = "workflow_version")
    private int workflowVersion;

    @Column(name = "status_flag")
    private String statusFlag;

    @Column(name = "wf_description")
    private String workflowDescription;

    @Column(name = "wf_type_code")
    private String workflowTypeCode;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "unique_field")
    private int uniqueField;

    @Column(name = "bpmn_xml")
    private String bpmnXML;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_date")
    @Temporal(TemporalType.DATE)
    private Date modifiedDate;

    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;

}