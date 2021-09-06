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

package org.tzi.use.gui.views.diagrams.behavior.communicationdiagram;

import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.ObjectNodeActivity;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;

/**
 * Association classes will be represented as LinkBoxNode
 *
 * @author Carsten Schlobohm
 *
 */
public class LinkObjectBoxNode extends LinkBoxNode implements ObjectNodeActivity {
    private MObject object;

    /**
     * Custom constructor
     * @param linkObject presented object
     * @param sequenceNumber its sequence number
     * @param parent owner of the node
     * @param opt option
     */
    public LinkObjectBoxNode(MLinkObject linkObject,
                             String sequenceNumber,
                             CommunicationDiagramView parent,
                             DiagramOptions opt) {
        super(linkObject, sequenceNumber, parent, opt);
        this.object = linkObject;
    }

    @Override
    public MObject object() {
        return object;
    }

    @Override
    public MClass cls() {
        return object.cls();
    }
}
