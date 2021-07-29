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

// $Id$

package org.tzi.use.gui.views.diagrams.waypoints;

/**
 * Different types of way points.
 * @author Lars Hamann
 *
 */
public enum WayPointType {
	/**
	 * A user defined way point 
	 */
	USER(0),
	/**
     * Special identification for the source node on this edge.
     */
    SOURCE(1),
    /**
     * Special identification for the target node on this edge.
     */
    TARGET(2),
    /**
     * Special identification for the first reflexive node on this edge.
     */
    REFLEXIVE_1(3),
    /**
     * Special identification for the second reflexive node on this edge.
     */
    REFLEXIVE_2(4),
    /**
     * Special identification for the third reflexive node on this edge.
     */
    REFLEXIVE_3(5),
    /**
     * Special identification for the associationclass/objectlink node 
     * on this edge.
     */
    ASSOC_CLASS(6),
    /**
     * Special identification for the third reflexive node on this edge.
     */
    REFLEXIVE_4(8),
    /**
     * Special identification for the connection point from the dashed to 
     * the solid line of an associationclass/objectlink.
     */
    ASSOC_CLASS_CON(7);
    
    private int id;
    
    private WayPointType(int id) {
    	this.id = id; 
    }
    
    public int getId() {
    	return this.id;
    }
    
    /**
     * Returns true, if this way point was not created by the user, e. g., it 
     * is a way point of a reflexive edge.
     * @return
     */
    public boolean isSpecial() {
    	return id != 0;
    }
    
    public static WayPointType getById(int id) {
    	return WayPointType.values()[id];
    }

	/**
	 * @return
	 */
	public boolean allowsDeletion() {
		return this == WayPointType.USER;
	}
}
