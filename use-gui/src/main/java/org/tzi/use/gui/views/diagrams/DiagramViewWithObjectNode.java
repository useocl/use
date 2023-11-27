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

package org.tzi.use.gui.views.diagrams;

import org.tzi.use.gui.views.diagrams.event.ActionHideObjectDiagram;
import org.tzi.use.gui.views.selection.objectselection.DataHolder;
import org.tzi.use.output.UserOutput;
import org.tzi.use.uml.sys.MObject;

import java.util.Set;

/**
 * @author Quang Dung Nguyen
 */
public abstract class DiagramViewWithObjectNode extends DiagramView implements DataHolder {

    public DiagramViewWithObjectNode(DiagramOptions opt, UserOutput output) {
	    super(opt, output);
    }

    /**
     * Show all objects contained in <code>objects</code>
	 */
    public void showObjects(Set<MObject> objects) {
        for (MObject o : objects) {
            showObject(o);
        }
    }
    
    /**
	 * Hides all objects included in <code>objects</code>
	 */
    public void hideObjects(Set<MObject> objects) {
        for (MObject o : objects) {
            hideObject(o);
        }
    }

    public abstract void hideObject(MObject obj);
    public abstract void showObject(MObject obj);
    public abstract void moveObjectNode(MObject obj, int x, int y);

    /**
     * Hides all elements included in <code>objectsToHide</code> in this diagram.
     * @param objectsToHide A set of {@link MObject}s to hide.
     */
    public void hideElementsInDiagram( Set<MObject> objectsToHide ) {
        for (MObject obj : objectsToHide) {
            hideObject(obj);
        }
    }
    
    public ActionHideObjectDiagram getAction( String text, Set<MObject> selectedNodes ) {
        return new ActionHideObjectDiagram( text, selectedNodes, fNodeSelection, fGraph, this );
    }
}
