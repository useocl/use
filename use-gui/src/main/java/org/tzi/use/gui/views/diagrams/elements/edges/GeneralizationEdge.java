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

package org.tzi.use.gui.views.diagrams.elements.edges;

import java.awt.Graphics2D;
import java.util.Iterator;

import lombok.Getter;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.edges.DirectedEdgeFactory;
import org.tzi.use.gui.views.diagrams.elements.EdgeProperty;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;
import org.tzi.use.uml.mm.MGeneralization;

/**
 * An Edge representing the generalization between to nodes.
 *
 * @author Fabian Gutsche
 * @author Lars Hamann
 */
@Getter
public final class GeneralizationEdge extends EdgeBase {

    private final MGeneralization mGeneralization;

    protected GeneralizationEdge(MGeneralization mGeneralization, PlaceableNode child, PlaceableNode parent,
                                 DiagramView diagram ) {
        super( child, parent, "Inheritance", diagram.getOptions(), true);
        this.mGeneralization = mGeneralization;
    }

    @Override
    public boolean isLink() { return false; }
    
    @Override
    protected String getIdInternal() {
    	return "Generalization::" + fSource.getId() + "::" + fTarget.getId();
    }
    /**
     * Draws a straightline edge between source and target node.
     */
    @Override
    protected void onDraw( Graphics2D g ) {
    	if ( isSelected() ) {
            g.setColor( fOpt.getEDGE_SELECTED_COLOR() );
        } else {
            g.setColor(getEdgeColor());
        }

        // draw all line segments
        if ( !fWayPoints.isEmpty() ) {
        	EdgeProperty n1 = null;
            EdgeProperty n2 = null;
            
            Iterator<WayPoint> it = fWayPoints.iterator();
            n1 = it.next();
            n1.draw(g);
            
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
                                                             (int) n2.getCenter().getY(),
                                                             false);
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
    
    public static GeneralizationEdge create(MGeneralization mGeneralization, PlaceableNode child, PlaceableNode parent, DiagramView diagram ) {
        return new GeneralizationEdge(mGeneralization, child, parent, diagram);
    }
}
