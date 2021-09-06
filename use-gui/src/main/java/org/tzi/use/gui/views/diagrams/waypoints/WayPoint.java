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

package org.tzi.use.gui.views.diagrams.waypoints;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.tzi.use.config.Options;
import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.elements.EdgeProperty;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.edges.BinaryAssociationClassOrObject;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.xmlparser.LayoutTags;
import org.w3c.dom.Element;

/**
 * Represents a way point on an edge. 
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public class WayPoint extends EdgeProperty {

	private int fID = -1;
        
    protected WayPointType fType;
   
	protected WayPoint previousWayPoint;
	
	protected WayPoint nextWayPoint;
	
	public WayPoint( EdgeBase edge, WayPointType type, String edgeName, DiagramOptions opt ) {
		super(opt);
        fEdge = edge;
        fType = type;
        setBackColorSelected(opt.getNODE_SELECTED_COLOR());
    }

	@Override
	public void onInitialize() {
		setCalculatedBounds(8, 8);
	}
	
	/**
	 * Returns the ID of the way point.
	 */
	@Override
	public String name() {
        return String.valueOf( getWayPointID() );
    }
    
	@Override
    public String getId() {
    	return new StringBuilder(fEdge.getId())
    							 .append("::WayPoint::")
    							 .append(getWayPointID()).toString(); 
    }
	
    /**
	 * @return the previousWayPoint
	 */
	public WayPoint getPreviousWayPoint() {
		return previousWayPoint;
	}

	/**
	 * @param previousWayPoint the previousWayPoint to set
	 */
	public void setPreviousWayPoint(WayPoint previousWayPoint) {
		this.previousWayPoint = previousWayPoint;
	}

	/**
	 * @return the nextWayPoint
	 */
	public WayPoint getNextWayPoint() {
		return nextWayPoint;
	}

	/**
	 * @param nextWayPoint the nextWayPoint to set
	 */
	public void setNextWayPoint(WayPoint nextWayPoint) {
		this.nextWayPoint = nextWayPoint;
	}

	/**
	 * @return
	 */
	public WayPoint getStrategyWayPoint() {		
		return this;
	}
	
	/**
     * Returns the id of this node.
     */
    public int getWayPointID() {
        return fID;
    }
    /**
     * Sets the id of this node.
     * @param id New id.
     */
    public void setID( int id ) {
        fID = id;
    }
    
    /**
     * Returns the id of this node.
     */
    public WayPointType getSpecialID() {
        return fType;
    }
	
    public boolean isSpecial()  {
        return fType.isSpecial();
    }
    
    /**
     * Returns the point that the drawing engine uses during
     * calculation of the path of an edge.
     * For user defined way points (<code>type == {@link WayPointType#USER}</code>) this is 
     * the center of the way point. 
     * For special ones this differs     
     * @return The point the drawing engine uses to calculate pathes
     */
    public PlaceableNode getCalculationNode() {
    	return this;
    }
    
    @Override
    public void doCalculateSize(Graphics2D g) { }
    	
    @Override
	protected void firePositionChanged(double deltaX, double deltaY) {
    	super.firePositionChanged(deltaX, deltaY);
		if (this.previousWayPoint != null) this.previousWayPoint.updatePosition();
		if (this.nextWayPoint != null) this.nextWayPoint.updatePosition();
	}

	@Override
	protected void fireBoundsChanged(Rectangle2D oldBounds,
			Rectangle2D newBounds) {
		super.fireBoundsChanged(oldBounds, newBounds);
		if (this.previousWayPoint != null) this.previousWayPoint.updatePosition();
		if (this.nextWayPoint != null) this.nextWayPoint.updatePosition();
	}

	@Override
    public boolean isVisible() {
    	if (getSpecialID() == WayPointType.ASSOC_CLASS             // associactioclass node
            || ( getSpecialID() == WayPointType.ASSOC_CLASS_CON       // associationclass
                 && fEdge.getWayPoints().size() <= 3 )
            || ( fEdge.isReflexive()                              // reflexive edge
                 && fEdge.getWayPoints().size() <= 5 ) 
            || ( fEdge.isReflexive() && fEdge instanceof BinaryAssociationClassOrObject // selfreflexive associationclass 
                 && fEdge.getWayPoints().size() <= 6 ) ) {
           return false;
       } else {
    	   return true;
       }
    }
    
    /**
	 * @return
	 */
	public Point2D getLinePoint() {
		return getCenter();
	}
	
	/**
     * Draws a rectangle around the position of this node.
     */
    @Override
    protected void onDraw( Graphics2D g ) {
    	// draw the node in selected color
        if ( fEdge.isSelected() || isSelected() ) {
        	Color c = g.getColor();
            g.setColor( getBackColorSelected() );
            g.draw(getBounds());
            if (Options.getDebug()) {
            	g.drawString(String.valueOf(getWayPointID()), (float)getBounds().getMaxX() + 5, (float)getBounds().getCenterY());
            	g.drawString(getStrategy().toString(), (float)getBounds().getMaxX() + 5, (float)getBounds().getCenterY() + 10);
            }
            g.setColor( c );
        }
    }
    
    /**
     * Either removes this node from the list of all way points of the edge,
     * if the way point type allows deletion or sets the
     * position to the automatically computed position. 
     */
    @Override
	public void setToAutoPosition() {
    	if ( getSpecialID().allowsDeletion()) {
            fEdge.removeWayPoint( this );
        } else {
        	super.setToAutoPosition();
        }
    }

    @Override
    public String getStoreType() {
    	// In 1.0: NodeOnEdge
    	return "WayPoint";
    }
    
    @Override
    protected void storeAdditionalInfo(PersistHelper helper, Element nodeElement, boolean hidden ) {
    	super.storeAdditionalInfo(helper, nodeElement, hidden);
    	helper.appendChild(nodeElement, LayoutTags.ID, String.valueOf(getWayPointID()));
    	helper.appendChild(nodeElement, LayoutTags.SPECIALID, String.valueOf(getSpecialID().getId()));
    }
    
    @Override
    protected void restoreAdditionalInfo( PersistHelper helper, int version ) {
    	if (version > 1) {
			this.setIsUserDefined(Boolean.valueOf(helper.getAttributeValue("userDefined")));
		}
    }
    
	public double getMyOffset() { return 0.0; }
    
    @Override
    public String toString() {
    	return this.getClass().getSimpleName() + " " + this.getBounds();
    }
    }