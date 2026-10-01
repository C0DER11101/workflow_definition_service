package com.workflow_def.service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "wf_node")
public class NodeDetailsModel {

    @Id
    @Column(name = "alt_key")
    private BigInteger altKey;

    @Column(name = "node_id")
    private String nodeId;

    @Column(name = "node_type")
    private String nodeType;

    @Column(name = "workflow_id")
    private String workflowId;

    @Column(name = "incoming_nodes")
    private List<String> incomingNodes;

    @Column(name = "outgoing_nodes")
    private List<String> outgoingNodes;

    @Column(name = "node_props")
    private Map<String, String> nodeProperties;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "modified_by")
    private String modifiedBy;

}