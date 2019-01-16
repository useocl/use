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


package org.tzi.use.gui.views.diagrams.behavior.sequencediagram;

import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.events.*;
import org.tzi.use.util.StringUtil;

import java.util.List;

/**
 * @author Carsten Schlobohm
 */
public class EventMessageCreator {
    /**
     * Creates the message which should be sent to the goal lifeline.
     *
     * @return the message
     */
    public static String createMessage(Event event, boolean showValues) {
        String msgLabel = "";
        if (event instanceof OperationEnteredEvent) {
            msgLabel = createOperationEnterMessage((OperationEnteredEvent) event, showValues);
        } else if (event instanceof ObjectCreatedEvent) {
            msgLabel = createCreateMassage();
        } else if (event instanceof ObjectDestroyedEvent) {
            msgLabel = createDestroyMassage();
        } else if (event instanceof AttributeAssignedEvent) {
            msgLabel = createAttributeAssignMessage((AttributeAssignedEvent) event, showValues);
        } else if (event instanceof LinkInsertedEvent) {
            msgLabel = createLinkInsertMessage((LinkInsertedEvent) event, showValues);
        } else if (event instanceof LinkDeletedEvent) {
            msgLabel = createLinkDeleteMassage((LinkDeletedEvent) event, showValues);
        }
        return msgLabel;
    }

    private static String createOperationEnterMessage(OperationEnteredEvent event, boolean showValues) {
        String msgLabel = "";
        MOperationCall fOpCall = event.getOperationCall();
        msgLabel = fOpCall.getOperation().name();
        if (showValues) {
            StringBuilder argMsg = new StringBuilder();
            argMsg.append("(");
            StringUtil.fmtSeq(argMsg, fOpCall.getArgumentsAsNamesAndValues().values(), ",");
            argMsg.append(")");
            msgLabel += argMsg;
        }
        return msgLabel;
    }

    private static String createCreateMassage() {
        return "create";
    }

    private static String createDestroyMassage() {
        return "destroy";
    }

    private static String createAttributeAssignMessage(AttributeAssignedEvent event, boolean showValues) {
        String msgLabel = "";
        String attribute = event.getAttribute().name();
        String value = event.getValue().toString();
        msgLabel = "set " + attribute;
        if (showValues)
            msgLabel = msgLabel + " := " + value;
        return msgLabel;
    }

    private static String createLinkInsertMessage(LinkInsertedEvent event, boolean showValues) {
        String msgLabel = "";
        boolean isLinkObject = (event.getAssociation() instanceof MAssociationClass);

        if (isLinkObject) {
            msgLabel = "create";
        } else {
            msgLabel = "insert";
        }

        if (showValues) {
            if (isLinkObject) {
                msgLabel = msgLabel + " between ";
            }

            List<MObject> objects = event.getParticipants();
            msgLabel = msgLabel + linkAttribute(objects);
        }
        return msgLabel;
    }

    private static String createLinkDeleteMassage(LinkDeletedEvent event, boolean showValues) {
        String msgLabel = "delete";
        if (showValues) {
            List<MObject> objects = event.getParticipants();
            msgLabel = msgLabel + linkAttribute(objects);
        }
        return msgLabel;
    }

    private static String linkAttribute(List<MObject> objects) {
        String attributeText = "(@";
        for (int i = 0; i < objects.size(); i++) {
            MObject object = objects.get(i);
            attributeText = attributeText + object.toString();
            if (i < (objects.size() - 1))
                attributeText = attributeText + ",@";
            else
                attributeText = attributeText + ")";
        }
        return attributeText;
    }
}
