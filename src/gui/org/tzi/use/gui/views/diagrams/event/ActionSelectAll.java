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

// $Id$

package org.tzi.use.gui.views.diagrams.event;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.AbstractAction;

import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.Selectable;

@SuppressWarnings("serial")
public class ActionSelectAll extends AbstractAction {
    private Selection fNodeSelection;
    private Map<?, ? extends Selectable> fNodes;
    private Map<?, ? extends Selectable> fEnumNodes;
    private DiagramView fDiagram;
    
    public ActionSelectAll( Selection nodeSelection, Map<?, ? extends Selectable> nodes, Map<?, ? extends Selectable> enumNodes,
                            DiagramView diagram ) {
        super( "Select all" );
        fNodeSelection = nodeSelection;
        fNodes = nodes;
        fEnumNodes = nodes;
        fDiagram = diagram;
    }
    
    public void actionPerformed( ActionEvent e ) {
        for (Selectable s : fNodes.values()) {
            fNodeSelection.add( s );
        }

        for (Selectable s : fEnumNodes.values()) {
            fNodeSelection.add( s );    
        }
        
        fDiagram.repaint();
    }
    
}
