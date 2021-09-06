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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.edges.DirectedEdgeFactory;
import org.tzi.use.gui.views.diagrams.edges.DirectedEdgeFactory.ArrowStyle;
import org.tzi.use.gui.views.diagrams.elements.EdgeProperty;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.views.diagrams.util.Direction;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;
import org.tzi.use.gui.views.diagrams.waypoints.WayPointType;
import org.tzi.use.uml.mm.statemachines.MProtocolTransition;
import org.tzi.use.uml.mm.statemachines.MTransition;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.collect.Multimap;

/**
 * @author Lars Hamann
 * 
 */
public class TransitionEdge extends EdgeBase {

	/** 
	 *  Transitions with same source and target can be displayed as "merged".
	 *  Therefore, we use a set of transitions. 
	 **/
	private final List<MTransition> transitions;

	/** 
	 *  Transitions with same source and target can be displayed as "merged".
	 *  Therefore, we use a set of transition labels. 
	 **/
	private List<TransitionLabel> labels;

	/**
	 * If this edge is a reflexive edge it can be located at four different
	 * positions: NORT_EAST SOUTH_EAST SOUTH_WEST NORTH_WEST
	 */
	Direction fReflexivePosition = Direction.NORTH_EAST;

	/**
	 * @param source
	 * @param target
	 * @param edgeName
	 * @param diagram
	 */
	protected TransitionEdge(MTransition transition, VertexNode source,
			VertexNode target, String edgeName, DiagramOptions diagramOptions,
			Direction reflexivePosition) {
		super(source, target, edgeName, diagramOptions, false);
		
		this.transitions = new ArrayList<MTransition>();
		this.transitions.add(transition);
		this.fReflexivePosition = reflexivePosition;
	}

	@Override
	protected void initializeProperties(Multimap<PropertyOwner, EdgeProperty> properties) {
		super.initializeProperties(properties);
		MTransition t = transitions.get(0);
		this.labels = new ArrayList<TransitionLabel>();
		this.labels.add(new TransitionLabel(t.toString(), t.toString(), this,
				(VertexNode) fSource, getSourceWayPoint(),
				(VertexNode) fTarget, getTargetWayPoint(), fOpt));
		properties.putAll(PropertyOwner.EDGE, this.labels);
	}

	protected String getIdInternal() {
		//FIXME: Make unique
		return "transition::" + fSource.getId() + "::" + fTarget.getId() + "::"; 
	}

	private void removeLabel(int index) {
		TransitionLabel label = this.labels.get(index); 
		label.dispose();
		
		this.labels.remove(index);
		this.edgeProperties.remove(PropertyOwner.EDGE, label);
	}
	
	private void addLabel(TransitionLabel label) {
		this.labels.add(label);
		this.firstDraw = true;
		this.edgeProperties.put(PropertyOwner.EDGE, label);
	}
	
	public void addTransition(MTransition transition) {
		if (this.transitions.add(transition)) {
			TransitionLabel label = new TransitionLabel(transition.toString() + "::label::" + this.labels.size() + 1, transition.toString(), this, 
					(VertexNode)fSource, getSourceWayPoint(), 
					(VertexNode)fTarget, getTargetWayPoint(),
					this.fOpt); 
			addLabel(label);
		}
	}
	
	/**
	 * @param t
	 */
	public void removeTransition(MTransition t) {
		int index = this.transitions.indexOf(t);
		if (index > -1) {
			this.transitions.remove(index);
			removeLabel(index);
			// TODO: Re id labels
		}
	}
	
	public void mergeTransitionEdge(TransitionEdge transitionEdge) {
		for (int i = 0; i < transitionEdge.transitions.size(); ++i) {
			MTransition transition = transitionEdge.transitions.get(i); 
			if (this.transitions.add(transition)) {
				TransitionLabel label = transitionEdge.labels.get(i);
				label.mergeTo(this);
				addLabel(label);
			}
		}
	}

	@Override
	protected void onDraw(Graphics2D g) {
		
		if (this.isSelected()) {
			g.setColor(fOpt.getEDGE_SELECTED_COLOR());
		} else {
			g.setColor(Color.BLACK);
		}
		
		EdgeProperty n1 = null;
		Point2D p1 = null;

		WayPoint n2 = null;
		Point2D p2 = null;

		// draw all line segments
		if (!fWayPoints.isEmpty()) {
			Iterator<WayPoint> it = fWayPoints.iterator();
			n1 = it.next();

			n1.draw(g);

			while (it.hasNext()) {
				n2 = it.next();
				p1 = n1.getCenter();
				p2 = n2.getCenter();

				// draw nodeOnEdge
				n2.draw(g);

				try {
					if (n2.getSpecialID() == WayPointType.TARGET) {
						DirectedEdgeFactory.drawArrow(g,
								(int) Math.round(p1.getX()),
								(int) Math.round(p1.getY()),
								(int) Math.round(p2.getX()),
								(int) Math.round(p2.getY()),
								ArrowStyle.OPEN);
					} else {
						g.drawLine((int) Math.round(p1.getX()),
								   (int) Math.round(p1.getY()),
								   (int) Math.round(p2.getX()),
								   (int) Math.round(p2.getY()));
					}
					n1 = n2;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean isLink() {
		return false;
	}

	/**
	 * @return the transition
	 */
	public Collection<MTransition> getTransitions() {
		return Collections.unmodifiableCollection(transitions);
	}

	@Override
	protected String getStoreType() {
		return "Transition";
	}

	/**
	 * @return the fReflexivePosition
	 */
	@Override
	public Direction getReflexivePosition() {
		return fReflexivePosition;
	}

	/**
	 * @param fReflexivePosition
	 *            the fReflexivePosition to set
	 */
	@Override
	public void setReflexivePosition(Direction fReflexivePosition) {
		this.fReflexivePosition = fReflexivePosition;
	}

	@Override
	public PlaceableNode findNode(double x, double y) {
		for (TransitionLabel label : this.labels) {
			if (label.occupies(x, y))
				return label;
		}
		
		return super.findNode(x, y);
	}

	@Override
	protected void storeProperties(PersistHelper helper, boolean hidden,
			Element edgeElement) {
		// Properties are stored in additional info
	}
	
	@Override
	protected void storeAdditionalInfo(PersistHelper helper, Element element,
			boolean hidden) {
		super.storeAdditionalInfo(helper, element, hidden);

		if (isReflexive())
			helper.appendChild(element, "reflexivePosition",
					fReflexivePosition.toString());

		Document doc = element.getOwnerDocument();
        Element transitionsElement = doc.createElement("Transitions");
        element.appendChild(transitionsElement);
        
		for (int i = 0; i < this.transitions.size(); ++i) {
			MTransition transition = this.transitions.get(i);
			Element transitionElement = doc.createElement("Transition");
			transitionsElement.appendChild(transitionElement);
	        
			helper.appendChild(transitionElement, "trigger",
					(transition.getTrigger() == null ? "" : transition.getTrigger()
							.toString()));
			helper.appendChild(transitionElement, "guard",
					(transition.getGuard() == null ? "" : transition.getGuard()
							.toString()));
	
			if (transition instanceof MProtocolTransition) {
				MProtocolTransition pt = (MProtocolTransition)transition;
				helper.appendChild(transitionElement, "post",
						(pt.getPostCondition() == null ? "" : pt.getPostCondition()
								.toString()));
			}
			
			this.labels.get(i).storePlacementInfo(helper, transitionElement, hidden);
		}
	}

	@Override
	protected void restoreAdditionalInfo(PersistHelper helper, int version) {
		if (isReflexive()) {
			String pos = helper.getElementStringValue("reflexivePosition");
			try {
				fReflexivePosition = Direction.valueOf(Direction.class, pos);
			} catch (NullPointerException e) {
			}
		}

		super.restoreAdditionalInfo(helper, version);
	}

	/**
	 * @param t
	 * @param helper
	 */
	public void restoreLabel(MTransition t, PersistHelper helper, int version) {
		TransitionLabel l = this.labels.get(this.transitions.indexOf(t));
		l.restorePlacementInfo(helper, version);
	}

	/**
	 * Removes all additional (merged) transitions from this edge. 
	 * @return The set of removed transitions.
	 */
	public Set<MTransition> flatten() {
		if (transitions.size() == 1) return Collections.emptySet();
		
		Set<MTransition> removed = new HashSet<MTransition>();
		while (transitions.size() > 1) {
			int lastIndex = transitions.size() - 1;
			MTransition t = transitions.get(lastIndex);
			transitions.remove(lastIndex);
			TransitionLabel removedLabel = labels.remove(lastIndex);
			removedLabel.dispose();
			removed.add(t);
			this.edgeProperties.remove(PropertyOwner.EDGE, removedLabel);
		}
		
		return removed;
	}
	
	/**
	 * @param source
	 * @param target
	 * @param edgeName
	 * @param diagram
	 */
	public static TransitionEdge create(MTransition transition, VertexNode source,
			VertexNode target, String edgeName, DiagramOptions diagramOptions,
			Direction reflexivePosition) {
		TransitionEdge edge = new TransitionEdge(transition, source, target, edgeName, diagramOptions, reflexivePosition);
		edge.initialize();
		return edge;
	}
}
