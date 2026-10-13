package com.workflow_def.service.parser;

import org.xml.sax.helpers.DefaultHandler;

import com.workflow_def.service.model.NodeDetailsModel;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

@Service
public class BpmnParser extends DefaultHandler {

    private List<NodeDetailsModel> nodes;
    private Map<String, Object> nodeProps;
    private NodeDetailsModel node;
    private StringBuilder data;
    private String nodeName;

    private boolean outgoing;
    private List<String> outgoingNodes;

    private boolean incoming;
    private List<String> incomingNodes;

    public List<NodeDetailsModel> getNodeList() {
        return nodes;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if(qName.equalsIgnoreCase("bpmn:startevent")) {

            node = new NodeDetailsModel();
            node.setNodeId(attributes.getValue("id"));
            node.setNodeType("start event");
            nodeName = attributes.getValue("name");

            if(nodeProps == null)
                nodeProps = new HashMap<>();

            nodeProps.put(node.getNodeId(), nodeName);

        } else if(qName.contains("intermediate")) {

            node = new NodeDetailsModel();
            node.setNodeId(attributes.getValue("id"));
            node.setNodeType("intermediate event");
            nodeName = attributes.getValue("name");

            if(nodeProps == null)
                nodeProps = new HashMap<>();

            nodeProps.put(node.getNodeId(), nodeName);

        } else if(qName.equalsIgnoreCase("bpmn:endevent")) {

            node = new NodeDetailsModel();
            node.setNodeId(attributes.getValue("id"));
            node.setNodeType("end event");
            nodeName = attributes.getValue("name");

            if(nodeProps == null)
                nodeProps = new HashMap<>();

            nodeProps.put(node.getNodeId(), nodeName);

        } else if(qName.equalsIgnoreCase("bpmn:outgoing")) {

            outgoing = true;

        } else if(qName.equalsIgnoreCase("bpmn:incoming")) {

            incoming = true;

        }

        data = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if(qName.equalsIgnoreCase("bpmn:startevent")) { // </bpmn:startEvent>

            node.setOutgoingNodes(outgoingNodes);
            node.setNodeProperties(nodeProps);
            nodes.add(node);
            outgoingNodes = null;
            nodeProps = null;

            return;

        } else if(qName.contains("intermediate")) { // </bpmn:intermediateThrowEvent> for example

            node.setOutgoingNodes(outgoingNodes);
            node.setIncomingNodes(incomingNodes);
            node.setNodeProperties(nodeProps);
            nodes.add(node);
            outgoingNodes = null;
            incomingNodes = null;
            nodeProps = null;

            return;

        } else if(qName.equalsIgnoreCase("bpmn:endevent")) { // </bpmn:endEvent>

            node.setIncomingNodes(incomingNodes);
            node.setNodeProperties(nodeProps);
            nodes.add(node);
            incomingNodes = null;
            nodeProps = null;

            return;

        }


        if(outgoing) {

            if(outgoingNodes == null)
                outgoingNodes = new ArrayList<>();

            outgoingNodes.add(data.toString());

            outgoing = false;

        } else if (incoming) {

            if(incomingNodes == null)
                incomingNodes = new ArrayList<>();

            incomingNodes.add(data.toString());

            incoming = false;

        }

    }

    @Override
    public void characters(char[] ch, int start, int end) throws SAXException {

        data.append(new String(ch, start, end));

    }

}