/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
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

package org.tzi.use.gui.views.diagrams.framework;

import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseEvent;

import org.tzi.use.uml.mm.instance.MLink;
import org.tzi.use.uml.mm.instance.MObject;

/**
 * The view an object-diagram element needs of the object diagram that owns it.
 * <p>
 * Extends {@link IDiagram} with the two object-diagram queries that generic
 * edge elements (e.g. association/link edges shared by class and object
 * diagrams) need, so those elements depend on this foundation interface instead
 * of the concrete {@code NewObjectDiagram}. {@code NewObjectDiagram} implements
 * this interface.
 */
public interface IObjectDiagram extends IDiagram {

    /** Whether the object diagram currently shows the given link. */
    boolean containsLink(MLink link);

    /** Whether the node for the given object is currently greyed-out. */
    boolean isObjectNodeGreyed(MObject obj);

    /** Object-diagram-specific input handling (delegated from the generic input handler). */
    void mayBeShowObjectInfo(MouseEvent e);

    void mayBeDisposeObjectInfo();

    void dropObjectFromModelBrowser(DropTargetDropEvent dtde);

}
