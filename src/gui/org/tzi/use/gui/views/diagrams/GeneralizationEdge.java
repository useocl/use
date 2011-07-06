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

package org.tzi.use.gui.views.diagrams;

import java.awt.Graphics2D;
import java.util.Iterator;

import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
import org.tzi.use.gui.views.diagrams.edges.DirectedEdgeFactory;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;

/**
 * An Edge representing the generalisation between to nodes.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public final class GeneralizationEdge extends EdgeBase {

    public GeneralizationEdge( ClassNode child, ClassNode parent, 
                               DiagramView diagram ) {
        super( child, parent, "Inheritance", diagram );
    }

    @Override
    public boolean isLink() { return false; }
    
    /**
     * Draws a straightline edge between source and target node.
     */
    @Override
    protected void onDraw( Graphics2D g ) {
        g.setColor( fOpt.getEDGE_COLOR() );

        // draw all line segments
        if ( !fWayPoints.isEmpty() ) {
        	WayPoint n1 = null;
            WayPoint n2 = null;
            
            Iterator<WayPoint> it = fWayPoints.iterator();
            n1 = it.next();
            int counter = 1;

            while( it.hasNext() ) {
                n2 = it.next();
                counter++;
                n2.draw(g);
                
                try {
                    if ( counter < fWayPoints.size() ) {
                        DirectedEdgeFactory.drawAssociation( g,
                                                             (int) n1.getCenter().getX(),
                                                             (int) n1.getCenter().getY(),
                                                             (int) n2.getCenter().getX(),
                                                             (int) n2.getCenter().getY() );
                        n1 = n2;
                    } else {
                    	// draw the last line segment, as an inheritance
                        DirectedEdgeFactory.drawInheritance( g, (int) n1.getCenter().getX(), 
                                                                (int) n1.getCenter().getY(), 
                                                                (int) n2.getCenter().getX(), 
                                                                (int) n2.getCenter().getY() );
                    }
                } catch ( Exception e ) {
                    //ignore
                }
            }
        }
    }

    @Override
    protected String getStoreType() { return "Generalization"; }
}
