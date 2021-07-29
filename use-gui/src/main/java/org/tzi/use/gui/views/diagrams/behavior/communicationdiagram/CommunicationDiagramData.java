/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

// $Id$


package org.tzi.use.gui.views.diagrams.behavior.communicationdiagram;

import com.google.common.collect.Sets;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.events.*;
import org.tzi.use.util.StringUtil;

import java.util.*;

/**
 * A class which handle all creation and destruction of
 * nodes from the CommunicationDiagram.
 *
 * @author Quang Dung Nguyen
 * @author Thomas Schaefer
 * @author Carsten Schlobohm
 */
public class CommunicationDiagramData implements DiagramView.DiagramData {

    /**
     * Since MLink and MObject don't have a common supertype or
     * interface (needs to be fixed) we must use a map with type object
     * as key.
     * However, only MLink or MObject instances should be contained
     */
    private Map<ElementKey, BaseNode> elementsToNodeMap;

    private CommunicationDiagram diagram;

    private CreationTimeRecorder messageRecorder = new CreationTimeRecorder();

    private Stack<MOperationCall> operationsStack;
    private List<MObject> operationsCaller = new ArrayList<MObject>();
    private List<Integer> sequenceNumbers;


    public CommunicationDiagramData(CommunicationDiagram diagram) {
        this.diagram = diagram;
        elementsToNodeMap = new HashMap<>();
        operationsStack = new Stack<MOperationCall>();

        sequenceNumbers = new ArrayList<Integer>();
        sequenceNumbers.add(1);
    }

    @Override
    public Set<PlaceableNode> getNodes() {
        return new HashSet<PlaceableNode>(elementsToNodeMap.values());
    }

    @Override
    public Set<EdgeBase> getEdges() {
        return Sets.newHashSet(diagram.getGraph().edgeIterator());
    }

    @Override
    public boolean hasNodes() {
        return !elementsToNodeMap.isEmpty();
    }

    /**
     * Copies all data to the target object
     *
     * @param target another CommunicationDiagramData
     */
    public void copyTo(CommunicationDiagramData target) {
        target.elementsToNodeMap.putAll(this.elementsToNodeMap);
    }

    /**
     * Removes all data
     */
    public void clear() {
        this.elementsToNodeMap.clear();
    }

    public Map<ElementKey, BaseNode> elementsToNodeMap() {
        return elementsToNodeMap;
    }

    private ElementKey createElementKey(Object element, int currentTime) {
        return new ElementKey(element, messageRecorder.getLastCreationTime(element, currentTime));
    }

    public Vector<MMessage> getMessages() {
        return messageRecorder.getMessages();
    }

    /**
     * @param e creates a new object
     */
    protected void addObjectCreatedEvent(ObjectCreatedEvent e) {
        PlaceableNode callOpNode;
        MObject obj = e.getCreatedObject();

        if (operationsStack.isEmpty()) {
            callOpNode = diagram.getActorNode();
        } else {
            MObject callOpObject = operationsStack.peek().getSelf();
            callOpNode = getNodeForObject(callOpObject);
        }

        ObjectBoxNode newObjectBoxNode = new ObjectBoxNode(obj, getSequenceNumber(), diagram.getParentDiagram(), diagram.getOptions());
        newObjectBoxNode.calculateBounds();
        newObjectBoxNode.setPosition(diagram.getNextNodePosition());
        diagram.computeNextRandomPoint();
        diagram.getGraph().add(newObjectBoxNode);
        elementsToNodeMap().put(new ElementKey(obj, messageRecorder.getTime()), newObjectBoxNode);

        MMessage mess = new MMessage(e, getSequenceNumber(), "create");
        messageRecorder.addMessage(mess);
        raiseSequenceNumber();

        CommunicationDiagramEdge cde = CommunicationDiagramEdge.create(callOpNode, newObjectBoxNode, diagram, false);
        cde.addNewMessage(mess);

        diagram.getGraph().addEdge(cde);
        diagram.resetLayouter();
    }

    /**
     * @param event destroys an object
     */
    protected void addObjectDestroyedEvent(ObjectDestroyedEvent event) {
        PlaceableNode callOpNode;
        BaseNode objectNodeToDestroy;
        MObject obj = event.getDestroyedObject();

        if (operationsStack.isEmpty()) {
            callOpNode = diagram.getActorNode();
        } else {
            MObject callOpObject = operationsStack.peek().getSelf();
            callOpNode = getNodeForObject(callOpObject);
        }

        objectNodeToDestroy = getNodeForObject(obj);

        // Case of redo
        if (objectNodeToDestroy == null)
            return;

        MMessage mess = new MMessage(event, getSequenceNumber(), "destroy");
        messageRecorder.addMessage(mess);
        raiseSequenceNumber();

        CommunicationDiagramEdge edge = getSingleEdge(objectNodeToDestroy, callOpNode);
        if (edge == null) {
            edge = new CommunicationDiagramEdge(callOpNode, objectNodeToDestroy, diagram, false);
            diagram.getGraph().invalidateEdge(edge);
        }
        edge.addNewMessage(mess);

        diagram.resetLayouter();
    }

    /**
     * @param event adds a new link to the diagram
     */
    protected void addLinkInsertedEvent(LinkInsertedEvent event) {
        PlaceableNode callOpNode;

        MLink link = event.getLink();

        if (operationsStack.isEmpty()) {
            callOpNode = diagram.getActorNode();
        } else {
            MObject callOpObject = operationsStack.peek().getSelf();
            callOpNode = getNodeForObject(callOpObject);
        }

        LinkBoxNode newLinkNode = null;
        if (link instanceof MLinkObject) {
            newLinkNode = new LinkObjectBoxNode((MLinkObject) link, getSequenceNumber(), diagram.getParentDiagram(), diagram.getOptions());
        } else {
            newLinkNode = new LinkBoxNode(link, getSequenceNumber(), diagram.getParentDiagram(), diagram.getOptions());
        }
        newLinkNode.setPosition(diagram.getNextNodePosition());
        diagram.getGraph().add(newLinkNode);
        elementsToNodeMap.put(new ElementKey(link, messageRecorder.getTime()), newLinkNode);

        CommunicationDiagramEdge cde;
        cde = CommunicationDiagramEdge.create(callOpNode, newLinkNode, diagram, false);
        cde.setDashed(true);
        String messageLabel = "insert(";

        for (MObject obj : link.linkedObjects()) {
            BaseNode linkedNode = getNodeForObject(obj);

            messageLabel += String.format("@%s,", obj.name());
            CommunicationDiagramEdge linkEdge = CommunicationDiagramEdge.createLink(newLinkNode, linkedNode, diagram, false);

            diagram.getGraph().addEdge(linkEdge);
        }

        messageLabel = messageLabel.substring(0, messageLabel.length() - 1) + ")";

        MMessage mess = new MMessage(event, getSequenceNumber(), messageLabel);
        messageRecorder.addMessage(mess);
        cde.addNewMessage(mess);
        diagram.getGraph().addEdge(cde);
        diagram.resetLayouter();

        raiseSequenceNumber();
    }

    /**
     *
     * @param event removes a link from the diagram
     */
    protected void addLinkDeletedEvent(LinkDeletedEvent event) {
        PlaceableNode callOpNode;
        BaseNode linkNodeToDelete;
        MLink link = event.getLink();

        if (operationsStack.isEmpty()) {
            callOpNode = diagram.getActorNode();
        } else {
            MObject callOpObject = operationsStack.peek().getSelf();
            callOpNode = getNodeForObject(callOpObject);
        }

        linkNodeToDelete = elementsToNodeMap.get(createElementKey(link, messageRecorder.getTime()));

        // Case of redo
        if (linkNodeToDelete == null)
            return;

        MMessage mess = new MMessage(event, getSequenceNumber(), "delete");
        messageRecorder.addMessage(mess);
        raiseSequenceNumber();

        CommunicationDiagramEdge edge = null;
        Set<EdgeBase> existingEdges = diagram.getGraph().edgesBetween(callOpNode, linkNodeToDelete, false);

        if (existingEdges.isEmpty()) {
            edge = new CommunicationDiagramEdge(callOpNode, linkNodeToDelete, diagram, false);
            edge.addNewMessage(mess);
            diagram.getGraph().addEdge(edge);
        } else {
            edge = (CommunicationDiagramEdge)existingEdges.iterator().next();
            edge.addNewMessage(mess);
        }

        diagram.resetLayouter();
    }

    /**
     *
     * @param event set a parameter to a variable
     */
    protected void addAttributeAssignedEvent(AttributeAssignedEvent event) {
        PlaceableNode callOpNode;
        BaseNode objectNodeToAssign;
        MObject object = event.getObject();
        MAttribute attribute = event.getAttribute();
        Value value = event.getValue();

        if (operationsStack.isEmpty()) {
            callOpNode = diagram.getActorNode();
        } else {
            MObject callOpObject = operationsStack.peek().getSelf();
            callOpNode = getNodeForObject(callOpObject);
        }

        objectNodeToAssign = getNodeForObject(object);

        MMessage mess = new MMessage(event, getSequenceNumber(), String.format("set %s := %s", attribute.name(), value.toString()));
        messageRecorder.addMessage(mess);
        raiseSequenceNumber();
        CommunicationDiagramEdge edge = getSingleEdge(callOpNode, objectNodeToAssign);

        if (edge == null) {
            edge = new CommunicationDiagramEdge(callOpNode, objectNodeToAssign, diagram, false);
            diagram.getGraph().addEdge(edge);
        } else if (edge.target().equals(callOpNode) && !edge.source().equals(callOpNode)) {
            mess.setDirection(MMessage.BACKWARD);
        }

        edge.addNewMessage(mess);
        diagram.resetLayouter();
    }

    /**
     * @param event new function call
     */
    void addOperationEnteredEvent(OperationEnteredEvent event) {
        MOperationCall operationCall = event.getOperationCall();
        operationsStack.add(operationCall);
        MOperation operation = operationCall.getOperation();

        StringBuilder msgLabel = new StringBuilder();
        msgLabel.append(operation.name());
        msgLabel.append("(");
        StringUtil.fmtSeq(msgLabel, operationCall.getArgumentsAsNamesAndValues().values(), ",");
        msgLabel.append(")");

        MMessage mess = new MMessage(event, getSequenceNumber(), msgLabel.toString());
        messageRecorder.addMessage(mess);
        MObject enterOpObject = operationCall.getSelf();

        BaseNode enterOpNode;
        enterOpNode = getNodeForObject(enterOpObject);

        CommunicationDiagramEdge edge = null;

        if (!operationsCaller.isEmpty()) {
            MObject callOpObject = operationsCaller.get(operationsCaller.size() - 1);
            BaseNode callOpNode;

            callOpNode = getNodeForObject(callOpObject);

            edge = getSingleEdge(callOpNode, enterOpNode);

            if (edge == null) {
                edge = new CommunicationDiagramEdge(callOpNode, enterOpNode, diagram, false);
                diagram.getGraph().addEdge(edge);
            } else {
                // an edge show always in one direction,
                // if the edge show to the operation caller, the arrow of the message must be flipped
                if (edge.target().equals(callOpNode) && !edge.source().equals(callOpNode)) {
                    mess.setDirection(MMessage.BACKWARD);
                }
            }
        } else {
            // Actor is calling
            edge = getSingleEdge(diagram.getActorNode(), enterOpNode);
        }

        operationsCaller.add(enterOpObject);
        edge.addNewMessage(mess);

        diagram.resetLayouter();
        sequenceNumbers.add(1);
    }

    /**
     * @param event function call ended
     */
    void addOperationExitedEvent(OperationExitedEvent event) {
        MOperationCall operationCall = event.getOperationCall();
        MObject obj = operationCall.getSelf();
        BaseNode obn = getNodeForObject(obj);

        if (!operationsCaller.isEmpty()) {
            operationsCaller.remove(operationsCaller.size() - 1);
        }
        sequenceNumbers.remove(sequenceNumbers.size() - 1);

        if (operationCall.getResultValue() != null) {
            MMessage mess = new MMessage(event, getSequenceNumber(), operationCall.getResultValue().toString());
            mess.setDirection(MMessage.RETURN);
            if (!operationCall.exitedSuccessfully()) {
                mess.setFailedReturn(true);
            }
            messageRecorder.addMessage(mess);

            PlaceableNode sourceNode;
            if (!operationsCaller.isEmpty()) {
                MObject sourceObject = operationsCaller.get(operationsCaller.size() - 1);
                sourceNode = getNodeForObject(sourceObject);
            } else {
                sourceNode = diagram.getActorNode();
            }

            CommunicationDiagramEdge edge = getSingleEdge(sourceNode, obn);
            if (edge == null) {
                edge = new CommunicationDiagramEdge(sourceNode, obn, diagram, false);
                diagram.getGraph().addEdge(edge);
            }

            edge.addNewMessage(mess);

            diagram.resetLayouter();
        }

        if (!operationsStack.isEmpty()) {
            operationsStack.pop();
        }

        raiseSequenceNumber();
    }

    /**
     * Returns the node for the given object.
     * If obj is a link object, it is a LinkBoxNode, otherwise
     * it is a ObjectBoxNode.
     * @param obj to find the right node
     * @return node if object exist in the diagram or null
     */
    protected BaseNode getNodeForObject(Object obj) {
        return elementsToNodeMap.get(createElementKey(obj, messageRecorder.getLastCreationTime(obj)));
    }

    /**
     * @return current sequence number
     */
    private String getSequenceNumber() {
        String sequenceNumber = sequenceNumbers.get(0).toString();
        for (int i = 1; i < sequenceNumbers.size(); i++) {
            sequenceNumber += "." + sequenceNumbers.get(i);
        }
        return sequenceNumber;
    }

    /**
     * Raises the sequence number
     */
    private void raiseSequenceNumber() {
        if (sequenceNumbers.size() > 0) {
            int lastNumber = sequenceNumbers.get(sequenceNumbers.size() - 1);
            sequenceNumbers.set(sequenceNumbers.size() - 1, lastNumber + 1);
        }
    }

    /**
     * Calculates the actual state of all objects and changes them when it is necessary
     * Further it manages on basis of states the visibility of all objects
     */
    protected void calculateStates() {
        calculateVisibleNodes();
        calculateObjectState();
    }

    /**
     * Reset the binding between messages, nodes and selected messages.
     * After this function all messages and nodes will be selectable.
     * Nodes which were absent because of the message selection, will be set hidden
     */
    protected void transformMessageInterval(boolean makeMessageIntervalVisible) {
        Set<PlaceableNode> absentNodes = new HashSet<>();
        Set<PlaceableNode> shownNodes = new HashSet<>();
        for (int i = 0; i < this.getMessages().size(); ++i) {
            if (!diagram.isMessageAbsentFromGraph(getMessages().get(i))) {
                if (diagram.isMessageVisible(getMessages().get(i))) {
                    CommunicationDiagramEdge edge = getMessages().get(i).getOwner();
                    shownNodes.add(edge.source());
                    shownNodes.add(edge.target());
                } else {
                    CommunicationDiagramEdge edge = getMessages().get(i).getOwner();
                    absentNodes.add(edge.source());
                    absentNodes.add(edge.target());
                }
            }
        }
        absentNodes.removeAll(shownNodes);
        for (PlaceableNode node : absentNodes) {
            node.setAbsentFromGraph(false);
            node.setHidden(true);
        }
        if (makeMessageIntervalVisible) {
            for (PlaceableNode node : shownNodes) {
                node.setHidden(false);
            }
        }
        diagram.getSharedVisibleManager().getData().setAllEventsVisible();
    }

    /**
     * Calculates the current visible nodes depending on which
     * events are visible
     */
    private void calculateVisibleNodes() {
        Map<ElementKey, BaseNode> nodeMap = elementsToNodeMap();
        for (BaseNode n : nodeMap.values()) {
            n.setAbsentFromGraph(true);
        }

        for (int i = 0; i < this.getMessages().size(); ++i) {
            if(diagram.isMessageVisible(getMessages().get(i)) && !diagram.isMessageAbsentFromGraph(getMessages().get(i))) {
                CommunicationDiagramEdge edge = getMessages().get(i).getOwner();
                edge.source().setAbsentFromGraph(false);
                edge.target().setAbsentFromGraph(false);
            }
        }
    }

    /**
     * Calculates the current state of all objects depending on which
     * events are visible
     */
    protected void calculateObjectState() {
        Map<ElementKey, BaseNode> nodeMap = elementsToNodeMap();
        // Reset to persistent
        for (BaseNode n : nodeMap.values()) {
            n.setState(ObjectState.PERSISTENT);
        }
        BaseNode node;

        int start = 0;
        int end = this.getMessages().size() - 1;

        for (int i = start; i <= end; ++i) {
            if(diagram.isMessageVisible(getMessages().get(i)) &&
                    !diagram.isMessageAbsentFromGraph(getMessages().get(i))){

                Event event = this.getMessages().get(i).getEvent();
                if (event instanceof ObjectCreatedEvent) {
                    MObject obj = ((ObjectCreatedEvent)event).getCreatedObject();
                    nodeMap.get(createElementKey(obj, i)).setState(ObjectState.NEW);

                } else if (event instanceof ObjectDestroyedEvent) {
                    MObject obj = ((ObjectDestroyedEvent)event).getDestroyedObject();
                    node = nodeMap.get(createElementKey(obj, i));

                    if (node.getState() == ObjectState.NEW) {
                        node.setState(ObjectState.TRANSIENT);
                    } else {
                        node.setState(ObjectState.DELETED);
                    }

                } else if (event instanceof LinkInsertedEvent) {
                    MLink link = ((LinkInsertedEvent)event).getLink();
                    nodeMap.get(createElementKey(link, i)).setState(ObjectState.NEW);

                } else if (event instanceof LinkDeletedEvent) {
                    MLink link = ((LinkDeletedEvent)event).getLink();
                    node = nodeMap.get(createElementKey(link, i));

                    if (node.getState() == ObjectState.NEW) {
                        node.setState(ObjectState.TRANSIENT);
                    } else {
                        node.setState(ObjectState.DELETED);
                    }
                }
            }
        }

        for (int i = start; i <= end; ++i) {
            Event event = this.getMessages().get(i).getEvent();
            if (event instanceof ObjectCreatedEvent) {
                MObject obj = ((ObjectCreatedEvent) event).getCreatedObject();
                nodeMap.get(createElementKey(obj, i)).setOriginalLifeState(ObjectState.NEW);

            } else if (event instanceof ObjectDestroyedEvent) {
                MObject obj = ((ObjectDestroyedEvent) event).getDestroyedObject();
                node = nodeMap.get(createElementKey(obj, i));

                if (node.getOriginalLifeState() == ObjectState.NEW) {
                    node.setOriginalLifeState(ObjectState.TRANSIENT);
                } else {
                    node.setOriginalLifeState(ObjectState.DELETED);
                }

            } else if (event instanceof LinkInsertedEvent) {
                MLink link = ((LinkInsertedEvent) event).getLink();
                nodeMap.get(createElementKey(link, i)).setOriginalLifeState(ObjectState.NEW);

            } else if (event instanceof LinkDeletedEvent) {
                MLink link = ((LinkDeletedEvent) event).getLink();
                node = nodeMap.get(createElementKey(link, i));

                if (node.getOriginalLifeState() == ObjectState.NEW) {
                    node.setOriginalLifeState(ObjectState.TRANSIENT);
                } else {
                    node.setOriginalLifeState(ObjectState.DELETED);
                }
            }
        }
    }

    /**
     * Returns the edge between <code>node1</code> and <code>node2</code>
     * if there exists one edge between both nodes.
     * Otherwise <code>null</code> is returned.
     * @param node1 one node of the edge
     * @param node2 other node of the edge
     * @return null or the edge between the two nodes
     */
    private CommunicationDiagramEdge getSingleEdge(PlaceableNode node1, PlaceableNode node2) {
        Set<EdgeBase> existingEdges = diagram.getGraph().edgesBetween(node1, node2, false);
        if (existingEdges.size() == 1) {
            return (CommunicationDiagramEdge)existingEdges.iterator().next();
        }
        return null;
    }
}
