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

package org.tzi.use.gui.views.diagrams;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Iterator;

import org.tzi.use.gui.views.diagrams.edges.DirectedEdgeFactory;

/**
 * An Edge representing the generalisation between to nodes.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public final class GeneralizationEdge extends EdgeBase {

    public GeneralizationEdge( Object source, Object target, 
                               DiagramView diagram ) {
        super( source, target, "Inheritance", diagram, null );
    }

    /**
     * Draws a straightline edge between source and target node.
     */
    public void draw( Graphics g, FontMetrics fm ) {
        g.setColor( fOpt.getEDGE_COLOR() );
    
        NodeOnEdge n1 = null;
        NodeOnEdge n2 = null;
        
        // draw all line segments
        if ( !fNodesOnEdge.isEmpty() ) {
            Iterator it = fNodesOnEdge.iterator();
            int counter = 0;
            if ( it.hasNext() ) {
                n1 = (NodeOnEdge) it.next();
                counter++;
            }
            while( it.hasNext() ) {
                n2 = (NodeOnEdge) it.next();
                counter++;
                n2.draw( g, g.getFontMetrics() );
                try {
                    if ( counter < fNodesOnEdge.size() ) {
                        DirectedEdgeFactory.drawAssociation( g,
                                                             (int) n1.x(),
                                                             (int) n1.y(),
                                                             (int) n2.x(),
                                                             (int) n2.y() );
                        n1 = n2;
                    }
                    
                } catch ( Exception e ) {
                    //ignore
                }
            }
        }
        
        // draw the last line segment, as an inheritance
        try {
            DirectedEdgeFactory.drawInheritance( g, (int) n1.x(), 
                                                 (int) n1.y(), 
                                                 (int) n2.x(), 
                                                 (int) n2.y() );
        } catch ( Exception ex ) {
            //ignore
        }
    }

}
