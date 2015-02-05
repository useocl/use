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
package org.tzi.use.gui.views.diagrams.statemachine;

import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.elements.EdgeProperty;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyInBetween;

/**
 * @author Lars Hamann
 *
 */
public class TransitionLabel extends EdgeProperty {

	public TransitionLabel(String id, String label, TransitionEdge edge, VertexNode source, EdgeProperty sourceWayPoint, VertexNode target,
			EdgeProperty targetWayPoint, DiagramOptions opt) {
		super(id, new PlaceableNode[] {sourceWayPoint, targetWayPoint}, false, opt); 
		fName = label;
		this.fEdge = edge;
		this.strategy = new StrategyInBetween(this, new PlaceableNode[] {sourceWayPoint, targetWayPoint}, 0, 10);
	}

	@Override
	protected String getStoreType() {
		return "TransitionLabel";
	}

	@Override
	public String toString() {
		return this.fName; 
	}

	public boolean isResizable() {
    	return true;
    }
	
	/**
	 * @param transitionEdge
	 */
	public void mergeTo(TransitionEdge transitionEdge) {
		PlaceableNode[] related = new PlaceableNode[]{transitionEdge.getSourceWayPoint(), transitionEdge.getTargetWayPoint()};
		this.setStrategy(new StrategyInBetween(this, related, 0, 10));
		this.sourceWayPoint = transitionEdge.getSourceWayPoint();
		this.targetWayPoint = transitionEdge.getTargetWayPoint();
		this.relatedNodes = related;
		this.fEdge = transitionEdge;
	}
}
