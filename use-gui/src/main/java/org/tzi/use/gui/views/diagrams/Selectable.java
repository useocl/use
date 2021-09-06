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

/**
 * Represents a selectable object in a diagram. 
 * @author Fabian Gutsche
 * @author Lars Hamann
 */
public interface Selectable {
    
    /**
     * Sets if this node is selected or not.
     * @param on The new value of the selection
     */
    public void setSelected( boolean on );
    
    /**
     * Returns if this node is selected. 
     */
    public boolean isSelected();
    
    /**
     * Sets, if a resize of a selected element is
     * currently allowed.
     * For example, it is the only selected element.
     * @param allowed
     */
    public void setResizeAllowed( boolean allowed );
    
    /**
     * <code>true</code>, if an element is allowed to
     * be resized. 
     * @return
     */
    public boolean getResizeAllowed();
}
