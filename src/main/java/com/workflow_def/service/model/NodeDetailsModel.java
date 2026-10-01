package com.workflow_def.service.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "wf_node")
public class NodeDetailsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alt_key")
    private int altKey;

    @Column(name = "node_id")
    private String nodeId;

    @Column(name = "node_type")
    private String nodeType;

    @Column(name = "workflow_id")
    private String workflowId;

    @Column(name = "incoming_nodes")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> incomingNodes;

    @Column(name = "outgoing_nodes")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> outgoingNodes;

    @Column(name = "node_props")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> nodeProperties;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "modified_by")
    private String modifiedBy;

}