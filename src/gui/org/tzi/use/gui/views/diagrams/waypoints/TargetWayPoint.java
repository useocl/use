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

import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.NodeBase;

/**
 * Way point attached to the target node
 * @author Lars Hamann
 *
 */
public class TargetWayPoint extends AttachedWayPoint {
	
	public TargetWayPoint(NodeBase source, NodeBase target, EdgeBase edge,
			int id, String edgeName, DiagramOptions opt) {
		super(source, target, edge, id, WayPointType.TARGET, edgeName, opt);
	}
	
	/* (non-Javadoc)
	 * @see org.tzi.use.gui.views.diagrams.waypoints.AttachedWayPoint#getAttachedNode()
	 */
	@Override
	protected NodeBase getAttachedNode() {
		return this.fEdge.getDrawingTarget();
	}
}
