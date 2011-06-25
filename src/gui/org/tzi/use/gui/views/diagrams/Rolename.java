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
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.tzi.use.gui.views.diagrams.util.Direction;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.util.StringUtil;

/**
 * Represents a role name node in a diagram. 
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public final class Rolename extends EdgeProperty {
    MAssociationEnd fAssocEnd;
    
    /**
     * 
     * @param assocEnd The <code>MAassociationEnd</code> this role name belongs to.
     * @param source The <code>NodeBase</code> which represents the class or object node this role name is attached to. 
     * @param target The <code>NodeBase</code> which represents the class, object or diamond node of the opposite end.
     * @param sourceWayPoint
     * @param targetWayPoint
     * @param opt
     * @param side
     * @param edge
     */
    Rolename( MAssociationEnd assocEnd, NodeBase source, NodeBase target, 
              WayPoint sourceWayPoint, WayPoint targetWayPoint, DiagramOptions opt, int side, EdgeBase edge ) {
    	super(source, sourceWayPoint, target, targetWayPoint, false);
    	
        fAssocEnd = assocEnd;
        setName();
        fAssoc = fAssocEnd.association();
        fEdge = edge;
        fOpt = opt;
        fSide = side;
        
        sourceWayPoint.addPositionChangedListener(new PositionChangedListener<PlaceableNode>() {
			@Override
			public void positionChanged(PlaceableNode source, Point2D newPosition, double deltaX, double deltaY) {
				Rolename.this.calculatePosition();				
			}
		});
    }
    
    private void setName() {
    	List<String> constraints = new ArrayList<String>();
    	
    	if (fAssocEnd.isOrdered()) {
    		constraints.add("ordered");
    	}
    	
    	if (fAssocEnd.isUnion()) {
    		constraints.add("union");
    	}
    	
		for (MAssociationEnd subsettedEnd : fAssocEnd.getSubsettedEnds()) {
			constraints.add("subsets " + subsettedEnd.nameAsRolename());    			
		}
    	    	
		for (MAssociationEnd redefinedEnd : fAssocEnd.getRedefinedEnds()) {
			constraints.add("redefines " + redefinedEnd.nameAsRolename());    			
		}
		
    	fName = fAssocEnd.nameAsRolename();
    	
    	if (constraints.size() > 0) {
    		fName = fName + " {" + StringUtil.fmtSeq(constraints.iterator(), ", ") + "}";
    	}
    }
    
    /**
     * Draws a role name on a binary edge.
     */
    @Override
    protected void onDraw( Graphics2D g ) {
        setColor( g );
                    
        if ( fAssocEnd != null && fAssocEnd.isNavigable() ) {
        	if ( isSelected() ) {
            	drawSelected(g);
            }
        	
            drawTextCentered(g);
        }

        resetColor( g );
    }

	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.EdgeProperty#getDefaultPosition()
	 */
	@Override
	protected Point2D getDefaultPosition() {
		// simple approximation of role name placement
		Point2D.Double result = new Point2D.Double();
		
        int fn1H = (int)fSource.getHeight() / 2;
        Direction targetLocation = Direction.getDirection(sourceWayPoint.getCenter(), targetWayPoint.getCenter());
        
        if ( targetLocation.isLocatedSouth() ) {
            result.y = sourceWayPoint.getY() + fn1H + 15;
        } else {
            result.y = sourceWayPoint.getY() - fn1H - 10;
        }
        
        if ( targetLocation.isLocatedWest() ) {
            result.x = sourceWayPoint.getX() - getBounds().getWidth() - 2;
        } else {
            result.x = sourceWayPoint.getX() + 2;
        }
        
        return result;
	}

	@Override
    public String getStoreType() {
    	return "rolename";
    }
	
	@Override
	protected String getStoreKind() {
		return fSide == SOURCE_SIDE ? "source" : "target";
	}
}
