/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2012 Mark Richters, University of Bremen
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
package org.tzi.use.gui.views.diagrams;

import java.awt.event.MouseEvent;

/**
 * Nodes inside diagrams can implement this interface
 * to provide additional information as a tool tip.
 * @author Lars Hamann
 *
 */
public interface ToolTipProvider {

	/**
	 * The tool tip text to be displayed.
	 * @param event
	 * @return
	 */
	String getToolTip(MouseEvent event);

}
