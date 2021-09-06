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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.eclipse.jdt.annotation.Nullable;
import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.DiagramGraph;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.views.diagrams.event.ActionLoadLayout;
import org.tzi.use.gui.views.diagrams.event.ActionSaveLayout;
import org.tzi.use.gui.views.diagrams.event.DiagramInputHandling;
import org.tzi.use.gui.views.diagrams.util.Direction;
import org.tzi.use.uml.mm.statemachines.MFinalState;
import org.tzi.use.uml.mm.statemachines.MProtocolTransition;
import org.tzi.use.uml.mm.statemachines.MPseudoState;
import org.tzi.use.uml.mm.statemachines.MRegion;
import org.tzi.use.uml.mm.statemachines.MState;
import org.tzi.use.uml.mm.statemachines.MStateMachine;
import org.tzi.use.uml.mm.statemachines.MTransition;
import org.tzi.use.uml.mm.statemachines.MVertex;
import org.w3c.dom.Element;

import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

/**
 * @author Lars Hamann
 *
 */
@SuppressWarnings("serial")
public class StateMachineDiagram extends DiagramView {

	public class StateChartData implements DiagramData {
		@Override
		public Set<PlaceableNode> getNodes() {
			return new HashSet<>(fGraph.getNodes());
		}

		@Override
		public Set<EdgeBase> getEdges() {
			return Collections.unmodifiableSet(fGraph.getEdges());
		}

		@Override
		public boolean hasNodes() {
			return !fGraph.isEmpty();
		}
				
	}
	protected final StateMachineDiagramView parentView;
	
	private final DiagramInputHandling inputHandling;
	
	private final Map<MVertex, VertexNode> vertexMappings = new HashMap<MVertex, VertexNode>();
	
	private final Map<MTransition, TransitionEdge> transitionMappings = new HashMap<MTransition, TransitionEdge>();
	
	private Map<MVertex, Integer> reflexiveTransitionCount = new HashMap<MVertex, Integer>();
	
	private String caption;
	
	private Polygon captionPolygon = null;
	
	private int minStateNodeHeight;
	private int minStateNodeWidth;
	
	public StateMachineDiagram(StateMachineDiagramView parent, PrintWriter log) {
		this(parent, log, new StateMachineDiagramOptions(Paths.get(parent.getSystem().model().filename())));
	}
	
	/**
	 * @param opt
	 * @param log
	 */
	public StateMachineDiagram(StateMachineDiagramView parent, PrintWriter log, StateMachineDiagramOptions opt) {
		super(opt, log);
		this.parentView = parent;
		
		minStateNodeHeight = Integer.parseInt(System.getProperty("use.gui.view.statemachinediagram.state.minheight"));
        minStateNodeWidth = Integer.parseInt(System.getProperty("use.gui.view.statemachinediagram.state.minwidth"));

		inputHandling = new DiagramInputHandling( fNodeSelection, fEdgeSelection, this );
		
		fActionSaveLayout = new ActionSaveLayout( "USE state machine diagram layout", "slt", this );

		fActionLoadLayout = new ActionLoadLayout( "USE state machine diagram layout", "slt", this );
		
		addMouseListener( inputHandling );
        parent.addKeyListener( inputHandling );
	}

	/**
     * Returns the options of a specific diagram.
     */
	@Override
    public StateMachineDiagramOptions getOptions() {
        return (StateMachineDiagramOptions)fOpt;
    }

	@Override
	public synchronized void drawDiagram(Graphics g) {
		// Draw diagram caption
		super.drawDiagram(g);
		drawCaption(g);
	}
	
	/**
	 * Draws the diagram caption at the left top corner
	 * @param g
	 */
	private void drawCaption(final Graphics g) {
		Graphics2D g2 = (Graphics2D)g.create();
		final int indentLeft = 5;
		final int indentTop = 13;
		final int captionHeight = 18;
		final int cornerWidth = 7;
		
		String completeCaption = caption + " {protocol}";
		if (this.captionPolygon == null) {
			this.captionPolygon = new Polygon();
			
			int dynamicCaptionWidth = g2.getFontMetrics().stringWidth(completeCaption);
			
			this.captionPolygon.addPoint(-1, -1);
			this.captionPolygon.addPoint(-1, captionHeight);
			this.captionPolygon.addPoint(indentLeft + dynamicCaptionWidth, captionHeight);
			this.captionPolygon.addPoint(indentLeft + dynamicCaptionWidth + cornerWidth, captionHeight - cornerWidth);
			this.captionPolygon.addPoint(indentLeft + dynamicCaptionWidth + cornerWidth, -1);
		}
		
		g2.setColor(Color.WHITE);
		g2.fill(captionPolygon);
		g2.setColor(Color.BLACK);
		g2.draw(captionPolygon);
		
		g2.drawString(completeCaption, indentLeft, indentTop);
	}

	@Override
	protected PopupMenuInfo unionOfPopUpMenu() {
		// context menu on right mouse click
        JPopupMenu popupMenu = new JPopupMenu();
        PopupMenuInfo popupInfo = new PopupMenuInfo(popupMenu);
        
        if (!fNodeSelection.isEmpty()) {
        	boolean somethingHidden = false;
        	boolean somethingShown = false;
        	        	
        	for (PlaceableNode n : fNodeSelection) {
        		if (n instanceof StateNode) {
        			StateNode sn = (StateNode)n;
        			if (sn.isShowInvariant()) {
        				somethingShown = true;
        			} else {
        				somethingHidden = true;
        			}
        		}
        	}
        	
        	JMenuItem showStateInv = new JMenuItem(new AbstractAction("Show state invariant") {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (PlaceableNode n : fNodeSelection) {
						if (n instanceof StateNode) {
							StateNode sn = (StateNode)n;
							sn.setShowInvariant(true);
							invalidateNode(sn);
						}
					}
					repaint();
				}
			});
        	showStateInv.setEnabled(somethingHidden);
        	
        	JMenuItem hideStateInv = new JMenuItem(new AbstractAction("Hide state invariant") {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (PlaceableNode n : fNodeSelection) {
						if (n instanceof StateNode) {
							StateNode sn = (StateNode)n;
							sn.setShowInvariant(false);
							invalidateNode(sn);
						}
					}
					repaint();
				}
			});
        	hideStateInv.setEnabled(somethingShown);
        	
        	popupMenu.add(showStateInv);
        	popupMenu.add(hideStateInv);
        }
        
        JMenu mergeMenu = new JMenu("Merge transitions");
               
        final JMenuItem mergeTransitions = new JMenuItem("Merge all possible transitions");
        mergeTransitions.addActionListener(new ActionListener() {
            @Override
        	public void actionPerformed(ActionEvent ev) {
				mergeTransitions(transitionMappings.values());
				invalidateContent(true);
			}
		});
    
        final JMenuItem demergeTransitions = new JMenuItem("Demerge all possible transitions");
        demergeTransitions.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent ev) {
				demergeTransitions(transitionMappings.values());
				invalidateContent(true);
			}
		});
        
        mergeMenu.add(mergeTransitions);
        mergeMenu.add(demergeTransitions);
        mergeMenu.addSeparator();
        
        final JMenuItem mergeSelectedTransitions = new JMenuItem("Merge selected transitions");
        mergeSelectedTransitions.setEnabled(fEdgeSelection.size() > 1);
        mergeSelectedTransitions.addActionListener(new ActionListener() {
            @Override
        	public void actionPerformed(ActionEvent ev) {
				List<TransitionEdge> edges = new ArrayList<TransitionEdge>();
            	for (EdgeBase e : fEdgeSelection) {
            		if (e instanceof TransitionEdge) {
            			edges.add((TransitionEdge)e);
            		}
            	}
            	mergeTransitions(edges);
				invalidateContent(true);
			}
		});
    
        final JMenuItem demergeSelectedTransitions = new JMenuItem("Demerge selected transitions");
        demergeSelectedTransitions.setEnabled(fEdgeSelection.size() > 0);
        demergeSelectedTransitions.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent ev) {
        		List<TransitionEdge> edges = new ArrayList<TransitionEdge>();
            	for (EdgeBase e : fEdgeSelection) {
            		if (e instanceof TransitionEdge) {
            			edges.add((TransitionEdge)e);
            		}
            	}
            	            	
				demergeTransitions(edges);
				invalidateContent(true);
			}
		});
        
        mergeMenu.add(mergeSelectedTransitions);
        mergeMenu.add(demergeSelectedTransitions);
        
        popupMenu.add(mergeMenu);
        
        popupMenu.addSeparator();
        popupMenu.add(getMenuItemCommentNode(popupInfo));
        popupMenu.addSeparator();
        popupMenu.add(getMenuAlign());
        popupMenu.add(getMenuItemAntiAliasing());
        popupMenu.add(getMenuItemShowGrid());
        
        addLayoutMenuItems(popupMenu);
        
        return popupInfo;
	}

	private void mergeTransitions(Collection<TransitionEdge> transitionsToMerge) {
		List<TransitionEdge> toDo = new ArrayList<TransitionEdge>(transitionsToMerge);
		// More natural for a caller, that the first element is used as a merge target
		Collections.reverse(toDo);
		
		while (!toDo.isEmpty()) {
			TransitionEdge mergeTarget = toDo.get(toDo.size() - 1);
			toDo.remove(toDo.size() - 1);
			
			for (int i = toDo.size() - 1; i >= 0; --i) {
				TransitionEdge toMerge = toDo.get(i);
				
				if (mergeTarget.source() == toMerge.source() && 
					mergeTarget.target() == toMerge.target()) {
					// Can be merged. Copy all handled transitions to mergeTarget
					for (MTransition t : toMerge.getTransitions()) {
						transitionMappings.put(t, mergeTarget);
					}
					mergeTarget.mergeTransitionEdge(toMerge);
					fGraph.removeEdge(toMerge);
					fEdgeSelection.remove(toMerge);
					toDo.remove(i);
					
					if (mergeTarget.isReflexive()) {
						VertexNode vNode = (VertexNode)mergeTarget.source();
						Integer count = reflexiveTransitionCount.get(vNode.getVertex());
						reflexiveTransitionCount.put(vNode.getVertex(), count - 1);
					}
				}
			}
		}
	}

	private void demergeTransitions(Collection<TransitionEdge> transitionsToDemerge) {
		/**
		 * We don't want to remove edges which are not covered, to
		 * keep their positions and way points.
		 */
		Set<MTransition> transitionsToAdd = new HashSet<MTransition>();
		
		// Remove all merged transitions from the graph and
		// add them as a single edge
		for (TransitionEdge edge : transitionsToDemerge) {
			transitionsToAdd.addAll(edge.flatten());
		}
				
		ElementFactory f = new ElementFactory();
		for (MTransition t : transitionsToAdd) {
			f.createTransitionEdge(t, false);
		}	
	}

	@Override
	public void resetLayout() {
		parentView.initDiagram(false, (StateMachineDiagramOptions)fOpt);
		parentView.validate();
	}
	
	@Override
	public Set<? extends PlaceableNode> getHiddenNodes() {
		return Collections.emptySet();
	}
	
	@Override
	public void storePlacementInfos(PersistHelper helper, Element rootElement) {
		for (VertexNode n : vertexMappings.values()) {
			n.storePlacementInfo(helper, rootElement, false);
		}
		
		for (TransitionEdge t : transitionMappings.values()) {
			t.storePlacementInfo(helper, rootElement, false);
		}
	}

	@Override
	protected boolean beforeRestorePlacementInfos(int version) {
		return version >= 6;
	}

	@Override
	public void restorePlacementInfos(PersistHelper helper, int version) {
				
		// Restore vertex nodes
		helper.getNav().push();
		
		AutoPilot ap = new AutoPilot(helper.getNav());
		
		try {
			ap.selectXPath("./node");
		
			try {
				while (ap.evalXPath() != -1) {
					String name = helper.getElementStringValue("name");
					MVertex v = parentView.stateMachine.getDefaultRegion().getSubvertex(name);
					
					// Could be deleted
					if (v != null) {
						VertexNode node = vertexMappings.get(v);
						node.restorePlacementInfo(helper, version);
					}
				}
			} catch (XPathEvalException ex) {
				fLog.append(ex.getMessage());
			} catch (NavException ex) {
				fLog.append(ex.getMessage());
			}
		} catch (XPathParseException ex) {
			fLog.append(ex.getMessage());
		}
		
		ap.resetXPath();
		helper.getNav().pop();
		
		// Restore edges
		Set<MTransition> unhandledTransitions = new HashSet<MTransition>(this.transitionMappings.keySet());
		
		try {
			ap.selectXPath("./edge");
			
			try {
				while (ap.evalXPath() != -1) {
					// Remember xpath position.
					helper.getNav().push();
					
					TransitionEdge firstEdge = null;
					
					String source = helper.getElementStringValue("source");
					String target = helper.getElementStringValue("target");
					
					helper.toFirstChild("Transitions");
					helper.toFirstChild("Transition");
					
					do {
						String trigger = helper.getElementStringValue("trigger");
						String guard = helper.getElementStringValue("guard");
						String post = helper.getElementStringValue("post");
						
						MTransition t = findTransition(unhandledTransitions, source, target, trigger, guard, post);
						
						if (t != null) {
							TransitionEdge edge = transitionMappings.get(t);
							
							if (firstEdge == null) {
								// Restore transition way points
								helper.getNav().push();
								helper.toParent();
								helper.toParent();
								edge.restorePlacementInfo(helper, version);
								helper.getNav().pop();
								firstEdge = edge;
							} else {
								// Merged to another edge or stand alone edge
								if (edge != firstEdge) {
									if (edge.getTransitions().size() == 1) {
										fGraph.removeEdge(edge);
									} else {
										edge.removeTransition(t);
									}
									
									firstEdge.addTransition(t);
									transitionMappings.put(t, firstEdge);
								}
							}
							
							// We handled t 
							unhandledTransitions.remove(t);
							
							if (helper.toFirstChild("edgeproperty")) {
								if (helper.getAttributeValue("type").equals("TransitionLabel")) {
									firstEdge.restoreLabel(t, helper, version);
								}
								helper.toParent();
							}
						}
					} while (helper.toNextSibling("Transition"));
					
					// Move pointer to xpath position.
					helper.getNav().pop();
				}
			} catch (XPathEvalException ex) {
				fLog.append(ex.getMessage());
			} catch (NavException ex) {
				fLog.append(ex.getMessage());
			}
		} catch (XPathParseException ex) {
			fLog.append(ex.getMessage());
		}
		
		ElementFactory f = new ElementFactory();
		
		// Add all unhandled transitions as new transitions 
		for (MTransition t : unhandledTransitions) {
			TransitionEdge e = this.transitionMappings.get(t);
			if (e.getTransitions().size() == 1) {
				fGraph.removeEdge(e);
			} else {
				e.removeTransition(t);
			}
			this.transitionMappings.remove(t);
			f.createTransitionEdge(t, false);
		}
	}

	@Nullable
	private MTransition findTransition(Set<MTransition> collection, String source, String target, String trigger, String guard, String post) {
		MTransition found = null;
		// Compare all values
		for (MTransition t : collection) {
			String tGuard = t.getGuard() == null ? "" : t.getGuard().toString();
			String tPost = (t instanceof MProtocolTransition && ((MProtocolTransition) t)
					.getPostCondition() != null) ? ((MProtocolTransition) t)
					.getPostCondition().toString() : "";
			
			if (source.equals(t.getSource().name()) &&
				target.equals(t.getTarget().name()) &&
				trigger.equals((t.getTrigger() == null ? "" : t.getTrigger().toString())) &&
				guard.equals(tGuard) &&
				post.equals(tPost)) {
				
				if (found != null)
					return null;
				found = t;
			}
		}
		
		// Single transition found
		if (found != null) return found;
				
		// Compare, ignore post
		for (MTransition t : collection) {
			if (source.equals(t.getSource().name()) &&
				target.equals(t.getTarget().name()) &&
				trigger.equals(t.getTrigger().toString()) &&
				guard.equals(t.getGuard() == null ? "" : t.getGuard().toString())) {
				if (found != null)
					return null;
				found = t;
			}
		}
		// Single transition found
		if (found != null) return found;
		
		// Compare, ignore post and guard
		for (MTransition t : collection) {
			if (source.equals(t.getSource().name()) &&
				target.equals(t.getTarget().name()) &&
				trigger.equals(t.getTrigger().toString())) {
				if (found != null)
					return null;
				found = t;
			}
		}
		// Single transition found
		if (found != null) return found;
		
		// Compare, ignore post, guard and trigger
		for (MTransition t : collection) {
			if (source.equals(t.getSource().name()) &&
				target.equals(t.getTarget().name())) {
				if (found != null)
					return null;
				found = t;
			}
		}
		
		// Single transition found or null
		return found;
	}

	@Override
	public void showAll() {
		// Ignore call (used by load layout)		
	}

	@Override
	public void hideAll() {
		throw new UnsupportedOperationException("The state machine diagram does not support hinding!");
	}

	@Override
	public DiagramData getVisibleData() {
		// State machines are completely displayed
		return new StateChartData();
	}

	@Override
	public DiagramData getHiddenData() {
		// State machines are completely displayed
		return null;
	}

	/**
	 * Sets the state machine to show and initializes the diagram, i. e., adds
	 * the nodes and edges.
	 * @param stateMachine
	 */
	public void setStateMachine(MStateMachine stateMachine) {
		this.transitionMappings.clear();
		this.vertexMappings.clear();
		this.fGraph = new DiagramGraph();
		
		// We currently support only one region
		MRegion reg = stateMachine.getRegions().get(0);
		ElementFactory f = new ElementFactory();
		
		for (MVertex v : reg.getSubvertices()) {
			VertexNode n = f.createVertexNode(v);
			
			setRandomPosition(n);
			fGraph.add(n);
			vertexMappings.put(v, n);
		}
		
		for (MTransition t : reg.getTransitions()) {
			f.createTransitionEdge(t, false);
		}
	}

	/**
	 * @param n
	 */
	private void setRandomPosition(PlaceableNode n) {
		n.setX( Math.random() * Math.max( 100, parentView.getWidth() * 0.8 ) );
        n.setY( Math.max(20, Math.random() * Math.max( 100, parentView.getHeight() * 0.8 )));
	}

	/**
	 * <p>Factory class which encapsulates the node and edge creation
	 * for the different kinds of nodes and edges.</p>
	 * 
	 * <p>New nodes for other {@link MVertex} subclasses can be added
	 * by putting a new map entry into {@link #nodeCreators}.</p>
	 * 
	 * <p>New edges for other {@link MTransition} subclasses can be added
	 * by putting a new map entry into {@link #edgeCreators}.</p>
	 * 
	 * <p>The factory also handles the position of reflexive edges (see {@link TransitionEdge#getReflexivePosition()}),
	 * by counting the number of reflexive edes per vertex.</p>
	 * 
	 * @author Lars Hamann
	 *
	 */
	private class ElementFactory {
		
		private Map<Class<?>, VertexNodeCreator> nodeCreators = new HashMap<Class<?>, VertexNodeCreator>();
		private Map<Class<?>, TransitionEdgeCreator> edgeCreators = new HashMap<Class<?>, TransitionEdgeCreator>();

		public ElementFactory() {
			nodeCreators.put(MPseudoState.class, new VertexNodeCreator() {
				public VertexNode create(MVertex v) {
					VertexNode n = new PseudoStateNode((MPseudoState)v);
					n.setBackColorSelected(getOptions().getColor(DiagramOptions.NODE_SELECTED_COLOR));
					return n;
				}
			} );
			
			nodeCreators.put(MState.class, new VertexNodeCreator() {
				public VertexNode create(MVertex v) {
					StateNode n = new StateNode((MState)v);
					n.setBackColor(getOptions().getNODE_COLOR());
					n.setBackColorSelected(getOptions().getColor(DiagramOptions.NODE_SELECTED_COLOR));
					n.setTextColor(getOptions().getNODE_LABEL_COLOR());
					n.setFrameColor(getOptions().getNODE_FRAME_COLOR());
					n.setFont(Font.getFont( "use.gui.view.statemachine.font", getFont()));
					n.setInvFont(Font.getFont( "use.gui.view.statemachine.invariantfont", n.getFont().deriveFont(n.getFont().getSize2D() - 2)));
					n.setMinHeight(minStateNodeHeight);
					n.setMinWidth(minStateNodeWidth);
					return n;
				}
			} );
			
			nodeCreators.put(MFinalState.class, new VertexNodeCreator() {
				public VertexNode create(MVertex v) {
					VertexNode n = new FinalStateNode((MState)v);
					n.setBackColorSelected(getOptions().getNODE_SELECTED_COLOR());
					return n; 
				}
			} );
			
			edgeCreators.put(MTransition.class, new TransitionEdgeCreator() {
				@Override
				public TransitionEdge create(MTransition t, int reflexiveCount) {
					VertexNode sNode = vertexMappings.get(t.getSource());
					VertexNode tNode = vertexMappings.get(t.getTarget());
					Direction reflexivePosition = Direction.getDirection(reflexiveCount * 3 % 12);
					return TransitionEdge.create(t, sNode, tNode, t.name(), getOptions(), reflexivePosition);
				}
			});
			
			edgeCreators.put(MProtocolTransition.class, new TransitionEdgeCreator() {
				@Override
				public TransitionEdge create(MTransition t, int reflexiveCount) {
					VertexNode sNode = vertexMappings.get(t.getSource());
					VertexNode tNode = vertexMappings.get(t.getTarget());
					Direction reflexivePosition = Direction.getDirection(reflexiveCount * 3 % 12);
					return ProtocolTransitionEdge.create(t, sNode, tNode, t.name(), getOptions(), reflexivePosition);
				}
			});
		}
		
		public VertexNode createVertexNode(MVertex v) {
			VertexNodeCreator c = nodeCreators.get(v.getClass());
			reflexiveTransitionCount.put(v, 0);
			return c.create(v);
		}
		
		public TransitionEdge createTransitionEdge(MTransition t, boolean mergeIfPossible) {
			TransitionEdge result = null;
			
			if (mergeIfPossible) {
				Set<EdgeBase> edges = fGraph.edgesBetween(vertexMappings.get(t.getSource()), vertexMappings.get(t.getTarget()), !t.isReflexive());
				if (!edges.isEmpty()) {
					result = (TransitionEdge)edges.iterator().next();
					result.addTransition(t);
				}
			}
			
			if (result == null) {
				TransitionEdgeCreator c = edgeCreators.get(t.getClass());
				int reflexiveCount = reflexiveTransitionCount.get(t.getSource());
			
				result = c.create(t, reflexiveCount);
			
				if (t.isReflexive()) {
					reflexiveTransitionCount.put(t.getSource(), reflexiveCount + 1);
				}
				fGraph.addEdge(result);
			}

			transitionMappings.put(t, result);
			return result;
		}
	}
	
	protected interface VertexNodeCreator {
		VertexNode create(MVertex v);
	}
	
	protected interface TransitionEdgeCreator {
		TransitionEdge create(MTransition v, int reflexiveCount);
	}

	/**
	 * @param currentState
	 */
	public void setActiveState(MState currentState) {
		for (VertexNode n : vertexMappings.values()) {
			n.setActive(currentState != null && currentState.equals(n.vertex));
		}
	}

	@Override
	protected String getDefaultLayoutFileSuffix() {
		return "_" + this.parentView.stateMachine.getContext().name() + "_" + this.parentView.stateMachine.name() + "_default.slt";
	}

	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
		this.captionPolygon = null;
	}
}
