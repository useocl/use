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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.gui.views.diagrams.event;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Map;

import javax.swing.AbstractAction;

import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.Selectable;

public class ActionSelectAll extends AbstractAction {
    private Selection fNodeSelection;
    private Map fNodes;
    private Map fEnumNodes;
    private DiagramView fDiagram;
    
    public ActionSelectAll( Selection nodeSelection, Map nodes, Map enumNodes,
                            DiagramView diagram ) {
        super( "Select all" );
        fNodeSelection = nodeSelection;
        fNodes = nodes;
        fEnumNodes = nodes;
        fDiagram = diagram;
    }
    
    public void actionPerformed( ActionEvent e ) {
        Iterator it = fNodes.values().iterator();
        while ( it.hasNext() ) {
            fNodeSelection.add( (Selectable) it.next() );    
        }
        it = fEnumNodes.values().iterator();
        while ( it.hasNext() ) {
            fNodeSelection.add( (Selectable) it.next() );    
        }
        fDiagram.repaint();
    }
    
}
