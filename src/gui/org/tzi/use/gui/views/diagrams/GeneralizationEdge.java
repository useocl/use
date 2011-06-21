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

    public GeneralizationEdge( ClassNode source, ClassNode target, 
                               DiagramView diagram ) {
        super( source, target, "Inheritance", diagram );
    }

    @Override
    public boolean isLink() { return false; }
    
    /**
     * Draws a straightline edge between source and target node.
     */
    @Override
    protected void onDraw( Graphics2D g ) {
        g.setColor( fOpt.getEDGE_COLOR() );
    
        WayPoint n1 = null;
        WayPoint n2 = null;
        
        // draw all line segments
        if ( !fWayPoints.isEmpty() ) {
            Iterator<WayPoint> it = fWayPoints.iterator();
            int counter = 0;
            if ( it.hasNext() ) {
                n1 = (WayPoint) it.next();
                counter++;
            }
            while( it.hasNext() ) {
                n2 = (WayPoint) it.next();
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
                    }
                    
                } catch ( Exception e ) {
                    //ignore
                }
            }
        }
        
        // draw the last line segment, as an inheritance
        try {
            DirectedEdgeFactory.drawInheritance( g, (int) n1.getCenter().getX(), 
                                                    (int) n1.getCenter().getY(), 
                                                    (int) n2.getCenter().getX(), 
                                                    (int) n2.getCenter().getY() );
        } catch ( Exception ex ) {
            //ignore
        }
    }

    @Override
    protected String storeGetType() { return "Generalization"; }
}
