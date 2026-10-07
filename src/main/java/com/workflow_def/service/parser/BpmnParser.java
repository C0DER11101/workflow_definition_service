package com.workflow_def.service.parser;

import org.xml.sax.helpers.DefaultHandler;

import com.workflow_def.service.model.NodeDetailsModel;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.List;
import java.util.ArrayList;

@Service
public class BpmnParser extends DefaultHandler {

    private List<NodeDetailsModel> nodes;
    private NodeDetailsModel node;
    private StringBuilder data;
    private String prevNode;

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

            // create a new node

            node = new NodeDetailsModel();
            node.setNodeId(attributes.getValue("id"));


            if(nodes == null)
                nodes = new ArrayList<>();

            // set the fields of this node



            prevNode = qName;

        } else if(qName.equalsIgnoreCase("bpmn:outgoing")) {

            outgoing = true;

        } else if(qName.equalsIgnoreCase("bpmn:incoming")) {

            incoming = true;

        }

        data = new StringBuilder();

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if(outgoing) {

            if(outgoingNodes == null)
                outgoingNodes = new ArrayList<>();

            outgoingNodes.add(data.toString());
            outgoing = false;

        } else if(incoming) {

            if(incomingNodes == null)
                incomingNodes = new ArrayList<>();

            incomingNodes.add(data.toString());
            incoming = false;

        }

        if(prevNode.equalsIgnoreCase("bpmn:startevent") || prevNode.substring(0, 17).equalsIgnoreCase("bpmn:intermediate") || prevNode.equalsIgnoreCase("bpmn:endevent"))
            nodes.add(node);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }

}