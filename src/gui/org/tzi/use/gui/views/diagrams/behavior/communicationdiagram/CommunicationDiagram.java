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

package org.tzi.use.gui.views.diagrams.behavior.communicationdiagram;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Point2D;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.DiagramViewWithObjectNode;
import org.tzi.use.gui.views.diagrams.ObjectNodeActivity;
import org.tzi.use.gui.views.diagrams.behavior.shared.CmdChooseWindow;
import org.tzi.use.gui.views.diagrams.behavior.shared.MessageSelectionView;
import org.tzi.use.gui.views.diagrams.behavior.shared.VisibleDataManager;
import org.tzi.use.gui.views.diagrams.elements.CommentNode;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.views.diagrams.event.ActionHideCommunicationDiagram;
import org.tzi.use.gui.views.diagrams.event.ActionLoadLayout;
import org.tzi.use.gui.views.diagrams.event.ActionSaveLayout;
import org.tzi.use.gui.views.diagrams.event.DiagramInputHandling;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectNode;
import org.tzi.use.gui.views.selection.objectselection.ObjectSelection;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.events.AttributeAssignedEvent;
import org.tzi.use.uml.sys.events.Event;
import org.tzi.use.uml.sys.events.LinkDeletedEvent;
import org.tzi.use.uml.sys.events.LinkInsertedEvent;
import org.tzi.use.uml.sys.events.ObjectCreatedEvent;
import org.tzi.use.uml.sys.events.ObjectDestroyedEvent;
import org.tzi.use.uml.sys.events.OperationEnteredEvent;
import org.tzi.use.util.collections.CollectionUtil;
import org.w3c.dom.Element;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Sets;
import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;

/**
 * Same as class diagram and object diagram, there are also common things
 * between them and communication diagram. So that, this class is a inheritance
 * of {@link DiagramViewWithObjectNode} which is subclass of {@link DiagramView}
 * .
 * 
 * @author Quang Dung Nguyen
 * @author Thomas Schaefer
 * @author Carsten Schlobohm
 *
 * 
 */
@SuppressWarnings("serial")
public class CommunicationDiagram extends DiagramViewWithObjectNode implements
		MessageSelectionView.MessageSelectionDelegate,
		CmdChooseWindow.CmdChooseWindowDelegate,
		ShowRelatedObjectsWindow.ShowRelatedObjectsDelegate {

	static final Color ACTIVATED_MESSAGE_COLOR = new Color(80, 136, 252);

	private CommunicationDiagramView fParent;
	private ActorNode actorSymbolNode;

	private CommunicationDiagramData dataSource;

	/**
	 * The position of the next object node. This is either set to a random
	 * value or to a specific position when an object is created by drag & drop.
	 */
	private Point2D.Double nextNodePosition = new Point2D.Double();

	private ObjectSelection fSelection;
	private DiagramInputHandling inputHandling;

	private MessagesNavigationDialog naviDialog;
	private CommunicationDiagramEdge activatedEdge;
	private MessagesGroup activatedMessage;

	private VisibleDataManager sharedVisibleManager;

	private boolean resetEnum = false;

	private boolean showAllMessages = true;

	/**
	 * Initialize a CommunicationDiagram and does some initialisation afterwards.
	 * This method was created to prevent the usage of 'this' as a param
	 * for initialisation of an other object in the constructor.
	 * @param parent view
	 * @param log to print log messages
	 * @param options contains property settings and colour settings
	 * @param sharedVisibleManager manages which data is currently visible or should be set hidden
	 * @return a new created CommunicationDiagram
	 */
	public static CommunicationDiagram createCommunicationDiagram(final CommunicationDiagramView parent,
																  final PrintWriter log,
																  final CommunicationDiagramOptions options,
																  final VisibleDataManager sharedVisibleManager) {
		CommunicationDiagramOptions opt = options;
		if (opt == null) {
			opt = new CommunicationDiagramOptions();
		}
		CommunicationDiagram diagram = new CommunicationDiagram(parent, log, opt, sharedVisibleManager);
		diagram.postConstruction();
		return diagram;
	}

	/**
	 * Custom constructor.
	 * Warning: Never pass 'this' to another object in the constructor,
	 * use postConstruction for cases where 'this' as param is needed
	 * @param parent view
	 * @param log to print messages
	 * @param options contains property settings and colour settings
	 * @param sharedVisibleManager manages which data is currently visible
	 */
	private CommunicationDiagram(CommunicationDiagramView parent, PrintWriter log, CommunicationDiagramOptions options, VisibleDataManager sharedVisibleManager) {
		super(options, log);
		fParent = parent;
		this.computeNextRandomPoint();
		parent.setFont(getFont());
		this.sharedVisibleManager = sharedVisibleManager;

		initializeActor(options.getActorDefaultName(),
				options.isActorAlwaysVisible(), options.isActorMovableDefault());
	}

	/**
	 * Do here all initialisation or function calls
	 * which needs 'this' as a param or relies on it in some manner.
	 * Use the constructor for all other initialisation.
	 * Especially register listener only in this method, so
	 * we can prevent multi-threading issues because of a not fully
	 * initialized CommunicationDiagram
	 */
	private void postConstruction() {
		this.dataSource = new CommunicationDiagramData(this);

		fSelection = new ObjectSelection(this, fParent.system());

		fActionSaveLayout = new ActionSaveLayout("USE communication diagram layout", "olt", this);
		fActionLoadLayout = new ActionLoadLayout("USE communications diagram layout", "olt", this);
		inputHandling = new DiagramInputHandling(fNodeSelection, fEdgeSelection, this);

		fParent.addKeyListener(inputHandling);
		addMouseListener(inputHandling);
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				fLayouter = null;
			}
		});
		startLayoutThread();
	}


	@Override
	public void initialize() {
		dataSource.calculateStates();
		super.initialize();
	}

	/**
	 *
	 * @param defaultName name of the actor
	 * @param isAlwaysVisible determines if the actor can be set hidden or not
	 * @param isMovable determines if the actor can be moved
	 */
	private void initializeActor(String defaultName, boolean isAlwaysVisible, boolean isMovable) {
		actorSymbolNode = new ActorNode(new Actor(defaultName), isAlwaysVisible, isMovable);
		actorSymbolNode.setPosition(getWidth() / 2, getHeight() / 2);
		computeNextRandomPoint();
		fGraph.add(actorSymbolNode);
	}

	VisibleDataManager getSharedVisibleManager() {
		return sharedVisibleManager;
	}

	CommunicationDiagramData getComDiaData() {
		return dataSource;
	}

	ActorNode getActorNode() {
		return this.actorSymbolNode;
	}

	void resetLayouter() {
		fLayouter = null;
	}

	/**
	 * Checks if a message is accessible.
	 * A message is not available if the message type is hidden.
	 * @param message to check
	 * @return true when the message type is visible
	 */
	public boolean isMessageAbsentFromGraph(MMessage message) {
		return !sharedVisibleManager.getData().isEventTypeVisible(message.getEvent().getClass());
	}

	/**
	 * Checks if a message is visible.
	 * @param message to check
	 * @return true when the message is in the selected message interval
	 */
	public boolean isMessageVisible(MMessage message) {
		return sharedVisibleManager.getData().isEventVisible(message.getEvent());
	}

	/**
	 * Finds a new position for the next object node.
	 */
	protected void computeNextRandomPoint() {
		nextNodePosition.x = (0.4 + Math.random() * 0.6) * Math.max(200, getWidth() - 50);
		nextNodePosition.y = (0.4 + Math.random() * 0.6) * Math.max(200, getHeight() - 50);
	}

	protected Point2D.Double getNextNodePosition() {
		return nextNodePosition;
	}

	public CommunicationDiagramView getParentDiagram() {
		return fParent;
	}

	@Override
	protected PopupMenuInfo unionOfPopUpMenu() {
		// context menu on right mouse click
		JPopupMenu popupMenu = new JPopupMenu();
		PopupMenuInfo popupInfo = new PopupMenuInfo(popupMenu);

		// position for the popupMenu items
		int pos = 0;

		final Set<PlaceableNode> selectedNodesSet = new HashSet<>();

		// filter selected model elements
		for (PlaceableNode node : fNodeSelection) {
			if (node instanceof ObjectNodeActivity) {
				selectedNodesSet.add(node);
			} else if (node instanceof LinkBoxNode) {
				selectedNodesSet.add(node);
			}
		}

		// Visible text for the menu item
		String selectedObjectsText = null;
		if (selectedNodesSet.size() == 1) {
			selectedObjectsText = Iterators.getOnlyElement(selectedNodesSet.iterator()).getTextForMenu();
		} else if (selectedNodesSet.size() > 1) {
			selectedObjectsText = selectedNodesSet.size() + " Nodes";
		}

		if (selectedNodesSet.size() > 0) {
			final JMenuItem expandInfo = new JMenuItem("Show neighborhood of selection");
			expandInfo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					showReachableObjectsViaOneStep(selectedNodesSet);
					afterSelectionAction();
					invalidateContent(true);
				}
			});
			popupMenu.insert(expandInfo, pos++);
			popupMenu.insert(new JSeparator(), pos++);
		}

		if (!selectedNodesSet.isEmpty()) {
			popupMenu.insert(new ActionHideCommunicationDiagram("Hide " + selectedObjectsText, selectedNodesSet, fNodeSelection, fGraph, this), pos++);
			popupMenu.insert(new ActionHideCommunicationDiagram("Crop " + selectedObjectsText, getNoneSelectedNodes(selectedNodesSet), fNodeSelection,
					fGraph, this), pos++);
			popupMenu.insert(new JSeparator(), pos++);
		}

		final JMenu showHideCrop = new JMenu("Show/hide/crop objects");
		showHideCrop.add(fSelection.getSelectionWithOCLViewAction());
		showHideCrop.add(fSelection.getSelectionObjectView());
		popupMenu.insert(showHideCrop, pos++);

		if (fGraph.size() > 0) {
			popupMenu.insert(fSelection.getSubMenuHideObject(), pos++);
		}

		if (CollectionUtil.exists(fGraph.iterator(), new Predicate<PlaceableNode>() {
			@Override
			public boolean apply(PlaceableNode input) {
				return input.isHidden();
			}
		})) {
			popupMenu.insert(fSelection.getSubMenuShowObject(), pos++);
		}

		final JMenuItem showMissingInfo = new JMenuItem("Show related objects...");
		showMissingInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// open PropertiesWindow
				createShowRelatedObjectsWindow();
				invalidateContent(true);
			}
		});

		popupMenu.insert(showMissingInfo, pos++);

		popupMenu.insert(new JSeparator(), pos++);

		if (!selectedNodesSet.isEmpty()) {
			String displayedText = selectedObjectsText;
			if (selectedNodesSet.size() == 1) {
				PlaceableNode node = Iterators.getOnlyElement(selectedNodesSet.iterator());
				if (node instanceof LinkBoxNode) {
					displayedText = "link";
				} else if (node instanceof ObjectNode) {
					displayedText = "object";
				}
			}
			final JMenuItem setAVEle = new JMenuItem("Keep "+ displayedText +" always visible");
			setAVEle.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					Set<MLink> links = new HashSet<>();
					Set<MObject> objects = new HashSet<>();
					for (PlaceableNode node : selectedNodesSet) {
						if (node instanceof LinkBoxNode) {
							links.add(((LinkBoxNode) node).getLink());
						} else if (node instanceof ObjectBoxNode) {
							objects.add(((ObjectBoxNode) node).object());
						}
					}
					getSharedVisibleManager().getData().addAlwaysVisibleElements(objects, links);
					invalidateContent(true);
				}
			});
			popupMenu.insert(setAVEle, pos++);
			popupMenu.insert(new JSeparator(), pos++);
		} else {
			if (getSharedVisibleManager().getData().existAlwaysVisibleElements()) {
				final JMenuItem resetAVEle = new JMenuItem("Reset selection of always visible");
				resetAVEle.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ev) {
						getSharedVisibleManager().getData().clearAlwaysVisibleElements();
						filterEdges();
						invalidateContent(true);
					}
				});
				popupMenu.insert(resetAVEle, pos++);
				popupMenu.insert(new JSeparator(), pos++);
			}
		}

		// new menu item "Show all life states"
		final JCheckBoxMenuItem objectBoxStatesItem = new JCheckBoxMenuItem("Show all life states");
		objectBoxStatesItem.setState(((CommunicationDiagramOptions) fOpt).isShowLifeStates());
		objectBoxStatesItem.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				((CommunicationDiagramOptions) fOpt).setShowLifeStates(ev.getStateChange() == ItemEvent.SELECTED);
				invalidateContent(true);
			}
		});

		popupMenu.insert(objectBoxStatesItem, pos++);
		popupMenu.insert(new JSeparator(), pos++);

		final JCheckBoxMenuItem messagesItem = new JCheckBoxMenuItem("Show communication messages");
		messagesItem.setState(((CommunicationDiagramOptions) fOpt).isShowCommunicationMessages());
		messagesItem.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ev) {
				((CommunicationDiagramOptions) fOpt).setShowCommunicationMessages(ev.getStateChange() == ItemEvent.SELECTED);
				invalidateContent(true);
			}
		});

		popupMenu.insert(messagesItem, pos++);

		final JMenuItem navigationItem = new JMenuItem("Messages navigation");
		navigationItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				naviDialog = new MessagesNavigationDialog(fParent.getCommunicationDiagram());
				naviDialog.setVisible(true);
			}
		});

		popupMenu.insert(navigationItem, pos++);
		popupMenu.insert(new JSeparator(), pos++);


		final JCheckBoxMenuItem showAllCommandsItem = new JCheckBoxMenuItem("Show all Commands");
		showAllCommandsItem.setState(sharedVisibleManager.getData().allEventTypesVisible());
		showAllCommandsItem.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(showAllCommandsItem.isSelected()){
					getSharedVisibleManager().getData().setAllEventTypesVisible(true);
				}else{
					getSharedVisibleManager().getData().setAllEventTypesVisible(false);
				}
				filterGraphByEvent();

			}
		});

		popupMenu.insert(showAllCommandsItem, pos++); 
		final JMenuItem showSomeCommandsItem = new JMenuItem("Commands to show...");
		showSomeCommandsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				createCmdChooseWindow();
			}
		});

		popupMenu.insert(showSomeCommandsItem, pos++);
		popupMenu.insert(new JSeparator(), pos++);


		final JCheckBoxMenuItem showAllMessagesItem  = new JCheckBoxMenuItem("Show all messages");
		showAllMessagesItem.setState(showAllMessages);
		showAllMessagesItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getSharedVisibleManager().getData().setAllEventsVisible();
				dataSource.calculateStates();
				afterSelectionAction();
				setRelativSequenceNumbers();
				invalidateContent(true);
				showAllMessages = true;
			}
		});
		popupMenu.insert(showAllMessagesItem, pos++);


		// Message selection
		final JMenuItem messageSelection = new JMenuItem("Message selection...");
		ActionListener messageSelectionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createMessageSelectionWindow();
			}
		};
		messageSelection.addActionListener(messageSelectionListener);
		popupMenu.insert(messageSelection, pos++);

		final JCheckBoxMenuItem resetEnumItem = new JCheckBoxMenuItem("Relative numbering");
		resetEnumItem.setState(resetEnum);
		resetEnumItem.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				resetEnum = resetEnumItem.getState();
				resetEnum(resetEnumItem.getState());
			}
		});

		popupMenu.insert(resetEnumItem, pos++);

		popupMenu.insert(new JSeparator(), pos++);

		// new menu item "Properties..."
		final JMenuItem cdProperties = new JMenuItem("Properties...");
		ActionListener propA = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// open PropertiesWindow
				createPropertiesWindow();
				// after closing update diagram
				afterPropertiesClosedAction();

				invalidateContent(true);
			}
		};
		cdProperties.addActionListener(propA);
		popupMenu.add(cdProperties);

		// sequence diagram
		final JMenuItem seqDia = new JMenuItem("Create synchronized sequence diagram");
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fParent.getMainWindow().createSequenceDiagram(sharedVisibleManager);
				invalidateContent(true);
			}
		};
		seqDia.addActionListener(listener);
		popupMenu.add(seqDia);

		popupMenu.addSeparator();

		popupMenu.add(getMenuItemCommentNode(popupInfo));
		if (fNodeSelection.size() == 1) {
			final PlaceableNode selected = fNodeSelection.iterator().next();
			if (selected instanceof CommentNode) {
				final JMenuItem deleteCommandNode = new JMenuItem("Delete comment node");
				deleteCommandNode.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						getGraph().remove(selected);
						fNodeSelection.clear();
						repaint();
					}
				});
				popupMenu.add(deleteCommandNode);
			}
		}
		popupMenu.addSeparator();
		popupMenu.add(getMenuAlign());
		popupMenu.add(getMenuItemAntiAliasing());
		popupMenu.add(getMenuItemShowGrid());

		addLayoutMenuItems(popupMenu);

		return popupInfo;
	}

	/**
	 * Creates a new PropertiesWindow object.
	 */
	private void createPropertiesWindow() {
		PropertiesWindow propWin = new PropertiesWindow(this.getParentDiagram());
		propWin.showWindow();
	}

	/**
	 * Creates a new PropertiesWindow object.
	 */
	private void createShowRelatedObjectsWindow() {
		ShowRelatedObjectsWindow window = new ShowRelatedObjectsWindow(this);
		window.showWindow();
	}

	/**
	 * Creates a new message selection view object.
	 */
	private void createMessageSelectionWindow() {
		MessageSelectionView propWin = new MessageSelectionView(this, true);
		propWin.showWindow();
	}

	/**
	 * Creates a new CmdChooseWindow object.
	 */
	private void createCmdChooseWindow() {
		CmdChooseWindow chooseWin = new CmdChooseWindow(this);
		chooseWin.showWindow();
	}

	/**
	 * Finds all nodes which are not selected.
	 * 
	 * @param selectedNodes Nodes which are selected at this point in the
	 *            diagram.
	 * @return A HashSet of the none selected objects in the diagram.
	 */
	private Set<PlaceableNode> getNoneSelectedNodes(Set<PlaceableNode> selectedNodes) {
		Set<PlaceableNode> noneSelectedNodes = new HashSet<>();

		Iterator<PlaceableNode> it = fGraph.iterator();
		while (it.hasNext()) {
			PlaceableNode o = it.next();
			if (!o.isSelected()) {
				noneSelectedNodes.add(o);
			}
		}
		return noneSelectedNodes;
	}


	public Vector<MMessage> getMessages() {
		return dataSource.getMessages();
	}

	@Override
	public void selectMessageFromToAndDepth(int from, int to, int depth) {
		for (int i = 0; i < dataSource.getMessages().size(); i++) {
			if(i < from || i > to ||
					(dataSource.getMessages().get(i).getSequenceNumber().split("\\.").length > depth &&
					depth != 0)) {
				sharedVisibleManager.getData().setEventHidden(dataSource.getMessages().get(i).getEvent());
			} else {
				sharedVisibleManager.getData().setEventVisible(dataSource.getMessages().get(i).getEvent());
			}
		}

		dataSource.calculateStates();
		afterSelectionAction();
		setRelativSequenceNumbers();
		showAllMessages = sharedVisibleManager.getData().areAllEventsVisible();
		invalidateContent(true);
	}

	@Override
	public List<String> messageLabels() {
		List<String> messagesLabel = new ArrayList<>();
		for (int i = 0; i < this.getMessages().size(); i++) {
			messagesLabel.add(getMessages().get(i).getSequenceNumberMessage());
		}
		return messagesLabel;
	}


	/**
	 * @param nodesToHide set of nodes to hide
	 */
	public void hideElementsInCommunicationDiagram(Set<PlaceableNode> nodesToHide) {
		for (PlaceableNode n : nodesToHide) {
			hideNode(n);
		}
		afterSelectionAction();
	}

	/**
	 * Getter for the communication diagram settings, to manipulate
	 * them over the properties dialog
	 * @return current settings
	 */
	public CommunicationDiagramOptions getSettings() {
		return (CommunicationDiagramOptions) fOpt;
	}

	@Override
	protected void onClosing() {
		super.onClosing();
		fParent.removeKeyListener(inputHandling);
	}

	@Override
	public void resetLayout() {
		fParent.initDiagram(false, (CommunicationDiagramOptions) fOpt);
		fParent.validate();
	}

	@Override
	public void storePlacementInfos(PersistHelper helper, Element rootElement) {
		actorSymbolNode.storePlacementInfo(helper, rootElement, false);

		CommunicationDiagramData data = dataSource;

		for (BaseNode n : data.elementsToNodeMap().values()) {
			n.storePlacementInfo(helper, rootElement, !n.isVisible());
		}

		Iterator<EdgeBase> edgeIter = fGraph.edgeIterator(); 
		while(edgeIter.hasNext()) {
			EdgeBase e = edgeIter.next();
			e.storePlacementInfo(helper, rootElement, !e.isVisible());
		}
	}

	/**
	 * Shows all related nodes which are reachable through an edge
	 * or a link connection
	 * @param nodeSet start nodes
	 */
	private void showReachableObjectsViaOneStep(Set<PlaceableNode> nodeSet) {
		for (PlaceableNode node : nodeSet) {
			Set<MObject> objects = new HashSet<>();
			if (node instanceof LinkBoxNode) {
				MLink link = ((LinkBoxNode) node).getLink();
				objects.addAll(link.linkedObjects());
			} else if (node instanceof ObjectBoxNode) {
				MObject object = ((ObjectBoxNode) node).object();
				objects.add(object);
			}

			Iterator<CommunicationDiagramEdge> iter = Iterators.filter(fGraph.edgeIterator(),
					CommunicationDiagramEdge.class);
			while (iter.hasNext()) {
				CommunicationDiagramEdge edge = iter.next();
				if (edge.source().equals(node) || edge.target().equals(node)) {
					if (edge.source() instanceof LinkBoxNode) {
						objects.addAll(((LinkBoxNode) edge.source()).getLink().linkedObjects());
						showNode(edge.source());
					} else if (edge.source() instanceof ObjectBoxNode) {
						objects.add(((ObjectBoxNode) edge.source()).object());
					}
					if (edge.target() instanceof LinkBoxNode) {
						objects.addAll(((LinkBoxNode) edge.target()).getLink().linkedObjects());
						showNode(edge.target());
					} else if (edge.source() instanceof ObjectBoxNode) {
						objects.add(((ObjectBoxNode) edge.target()).object());
					}
				}
			}
			showObjects(objects);
		}
	}

	/**
	 * @return the activated edge
	 */
	CommunicationDiagramEdge getActivatedEdge() {
		return activatedEdge;
	}

	/**
	 * @param activatedEdge the new activated edge
	 */
	void setActivatedEdge(CommunicationDiagramEdge activatedEdge) {
		this.activatedEdge = activatedEdge;
	}

	/**
	 * @return the activatedMessage
	 */
	MessagesGroup getActivatedMessage() {
		return activatedMessage;
	}

	/**
	 * @param activatedMessage the activatedMessage to set
	 */
	void setActivatedMessage(MessagesGroup activatedMessage) {
		this.activatedMessage = activatedMessage;
	}

	void showOrHideActivatedMessage(boolean on) {
		if (on) {
			if (activatedMessage != null) {
				activatedMessage.setActivatedMessageColor(ACTIVATED_MESSAGE_COLOR);
				repaint();
			}
		} else {
			if (activatedMessage != null) {
				activatedMessage.setActivatedMessageColor(new Color(255, 255, 255, 0));
				repaint();
			}
		}
	}

	CommunicationDiagramEdge getEdge(final MMessage message) {
		Optional<EdgeBase> e = Iterators.tryFind(fGraph.edgeIterator(), 
				new Predicate<EdgeBase>() {
			@Override
			public boolean apply(EdgeBase input) {
				CommunicationDiagramEdge cde = (CommunicationDiagramEdge)input;				

				return cde.getMessages().contains(message);
			}

		});

		return e.isPresent() ? (CommunicationDiagramEdge)e.get() : null;
	}

	/**
	 * resets the numbering from messages
	 * @param enable reset the relative numbering
	 */
	private void resetEnum(boolean enable){
		Iterator<CommunicationDiagramEdge> iter = Iterators.filter(
				fGraph.edgeIterator(),
				CommunicationDiagramEdge.class);

		if (enable) {
			setRelativSequenceNumbers();
		}
		while(iter.hasNext()) {
			iter.next().getMessagesGroup().resetEnum(enable);
		}

		invalidateContent(true);
	}

	/**
	 * Returns the actual states of the shown message events
	 * @return !messageFilter
	 */
	@Override
	public boolean isCreateSelected(){
		return sharedVisibleManager.getData().isEventTypeVisible(ObjectCreatedEvent.class);
	}

	@Override
	public boolean isDestroySelected(){
		return sharedVisibleManager.getData().isEventTypeVisible(ObjectDestroyedEvent.class);
	}

	@Override
	public boolean isInsertSelected(){
		return sharedVisibleManager.getData().isEventTypeVisible(LinkInsertedEvent.class);
	}

	@Override
	public boolean isDeleteSelected(){
		return sharedVisibleManager.getData().isEventTypeVisible(LinkDeletedEvent.class);
	}

	@Override
	public boolean isAssignSelected() {
		return sharedVisibleManager.getData().isEventTypeVisible(
				AttributeAssignedEvent.class);
	}

	@Override
	public void setCreateVisible(boolean selected) {
		sharedVisibleManager.getData().setEventTypeVisible(
				ObjectCreatedEvent.class, selected);
	}

	@Override
	public void setDestroyVisible(boolean selected) {
		sharedVisibleManager.getData().setEventTypeVisible(
				ObjectDestroyedEvent.class, selected);
	}

	@Override
	public void setInsertVisible(boolean selected) {
		sharedVisibleManager.getData().setEventTypeVisible(
				LinkInsertedEvent.class, selected);
	}

	@Override
	public void setDeleteVisible(boolean selected) {
		sharedVisibleManager.getData().setEventTypeVisible(
				LinkDeletedEvent.class, selected);
	}

	@Override
	public void setAssignVisible(boolean selected) {
		sharedVisibleManager.getData().setEventTypeVisible(
				AttributeAssignedEvent.class, selected);
	}

	/**
	 * Filters the Graph
	 */
	@Override
	public void filterGraphByEvent() {
		filterGraph();
		afterSelectionAction();
		setRelativSequenceNumbers();

		invalidateContent(true);
	}

	@Override
	public void showRelatedObjects(boolean showAssociations,
								   boolean showAssociationClasses,
								   boolean showObjectsFromAssociation,
								   boolean showObjectsAsParam,
								   int searchCycles) {
		showRelatedObjectsAction(showAssociations,
				showAssociationClasses,
				showObjectsFromAssociation,
				showObjectsAsParam,
				searchCycles);

		filterEdges();
		fParent.notifyDataManager();
		invalidateContent(true);
	}

	private void showRelatedObjectsAction(boolean showAssociations,
										  boolean showAssociationClasses,
										  boolean showObjectsFromAssociation,
										  boolean showObjectsAsParam,
										  int searchCycles) {
		int count = 0;
		do {
			count++;
			// lists of relevant links and objects
			Collection<MObject> foundMObjects = new ArrayList<>();
			Collection<MLink> foundMLinks = new ArrayList<>();

			foundMLinks = findRelevantLinks(showAssociations,
					showAssociationClasses);
			if (showObjectsFromAssociation) {
				foundMObjects.addAll(hiddenObjectsOfVisibleLinks());
			}
			if (showObjectsAsParam) {
				foundMObjects.addAll(hiddenObjectsUsedAsParameter());
			}
			// set relevant information visible
			for (MLink link : foundMLinks) {
				dataSource.getNodeForObject(link).setHidden(false);
			}
			for (MObject object : foundMObjects) {
				dataSource.getNodeForObject(object).setHidden(false);
			}
		} while (count < searchCycles);
	}

	void filterGraph() {
		dataSource.calculateStates();
	}

	/**
	 * Filters the edges on the basis of visible nodes.
	 */
	void filterEdges() {
		Iterator<CommunicationDiagramEdge> iter = Iterators.filter(fGraph.edgeIterator(),
				CommunicationDiagramEdge.class);
		while(iter.hasNext()) {
			CommunicationDiagramEdge edge = iter.next();


			if(edge.getMessages().size() > 0 && edge.getMessagesGroup().getEdgeMessages().size() == 0){
				edge.setHidden(true);
			}else{
				edge.setHidden(false);
			}
			if(edge.isLink()){
				if(edge.source().willBeDrawn()
						&& edge.target().willBeDrawn()){
					edge.setHidden(false);
				}else{
					edge.setHidden(true);
				}
			}
		}
	}


	/**
	 * Calculates the sequence numbers on the basis of the visible messages
	 */
	private void setRelativSequenceNumbers(){
		ArrayList<Integer> counters = new ArrayList<Integer>();		
		int msgDepth = 0;
		
		for (MMessage msg : dataSource.getMessages()) {
			if(isMessageVisible(msg) && !isMessageAbsentFromGraph(msg) && msg.getOwner().isVisible()){
				msgDepth = msg.getSequenceNumber().split("\\.").length;
				
				if(counters.size() < msgDepth){
					for(int i = counters.size(); i < msgDepth; i++){
						counters.add(1);
					}
					
					msg.setResetedSequenceNumber(buildNewSequenceNumber(counters, msgDepth));
				} else {
					if(msg.getDirection() != MMessage.RETURN){
						counters.set(msgDepth-1, counters.get(msgDepth-1)+1);
					}
					
					for (int i = msgDepth; i < counters.size(); i++) {
						counters.set(i, 0);
					}
					
					msg.setResetedSequenceNumber(buildNewSequenceNumber(counters, msgDepth));
				}
			}
		}	
	}
	
	/**
	 * Build the new sequence number
	 * @param counters contains the sequence number parts,
	 *                    which will separated by a dot
	 * @param breakPoint number of parts which will take from the counters
	 * @return a new sequence number
	 */
	private String buildNewSequenceNumber(ArrayList<Integer> counters,
										  int breakPoint){
		String newSequenceNumber = "";
		
		for (int i = 0; i < breakPoint; i++) {
			newSequenceNumber = newSequenceNumber + counters.get(i);
			
			if(i < breakPoint-1) {
				newSequenceNumber = newSequenceNumber + ".";
			}
		}
		
		return newSequenceNumber;
	}

	/**
	 * returns the maximum depth of the Messages 
	 * f.e 3.2.1: create(Person) is the deepest message and
	 * has the depth 3
	 * @return message depth
	 */
	@Override
	public int getMessageDepth(){
		int depth = 0;
		int var = 0;

		for (MMessage msg : dataSource.getMessages()) {
			var = msg.getSequenceNumber().split("\\.").length;

			if(var > depth){
				depth = var;
			}
		}

		return depth;
	}

	@Override
	public void showObjects(Set<MObject> objects) {
		for (MObject o : objects) {
			this.showNode(dataSource.getNodeForObject(o));
		}
		afterSelectionAction();
	}

	@Override
	public void hideObject(MObject obj) {
		hideNode(dataSource.getNodeForObject(obj));
		afterSelectionAction();
		this.invalidateContent(false);
	}

	private void showNode(PlaceableNode node) {
		node.setHidden(false);
		filterEdges();
	}

	private void hideNode(PlaceableNode node) {
		node.setHidden(true);
	}

	@Override
	public void showObject(MObject obj) {
		this.showNode(dataSource.getNodeForObject(obj));
		afterSelectionAction();
	}

	@Override
	public void showAll() {
		for (PlaceableNode node : fGraph) {
			showNode(node);
		}
		afterSelectionAction();
		this.invalidateContent(true);
	}

	/**
	 * Hides all currently visible elements.
	 */
	@Override
	public void hideAll() {
		for (PlaceableNode node : fGraph) {
			hideNode(node);
		}
		afterSelectionAction();
		this.invalidateContent(true);
	}

	@Override
	protected String getDefaultLayoutFileSuffix() {
		return "_comdia.clt";
	}

	@Override
	public DiagramData getVisibleData() {
		return dataSource;
	}

	@Override
	public DiagramData getHiddenData() {
		return null;
	}

	@Override
	public Set<? extends PlaceableNode> getHiddenNodes() {
		return Sets.newHashSet(fGraph.getHiddenNodesIterator());
	}
	
	@Override
	public void moveObjectNode( MObject obj, int x, int y ) {
		PlaceableNode node = dataSource.getNodeForObject(obj);
		if(node != null){
			node.moveToPosition(x, y);
		}
	}

	/**
	 * Generates a list with all MLinks which occurs during
	 * the events
	 * @return a list of all MLinks
	 */
	private Collection<MLink> getMLinks() {
		Collection<MLink> resultSet = new HashSet<MLink>();
		for (PlaceableNode node : dataSource.getNodes()) {
			if (node instanceof LinkBoxNode) {
				resultSet.add(((LinkBoxNode) node).getLink());
			}
		}
		return resultSet;
	}

	void afterSelectionAction() {
		// applySettings(); FIXME removed for now because it does not work (ticket 192)
		filterEdges();
		fParent.notifyDataManager();
	}

	/**
	 * Updates the diagram with the information from
	 * the selected diagram options.
	 */
	void afterPropertiesClosedAction() {
		afterSelectionAction();
	}

	/**
	 * Apply the settings from the Properties... menu
	 * to the diagram
	 */
	void applySettings() {
		if (!getSettings().isBindVisibleMessagesToMessageSelection()) {
			dataSource.transformMessageInterval(false);
			dataSource.calculateObjectState();
			showAllMessages = true;
		}
		showRelatedObjectsAction(getSettings().isShowMissingLinks(),
				getSettings().isShowMissingObjectLinks(),
				getSettings().isShowHiddenObjectsOfLinkObject(),
				getSettings().isShowHiddenFunctionParameter(),
				getSettings().getSearchCycles());

		if (getSettings().isHideAssociationsWithAHiddenPartner()) {
			filterIncompleteLinks();
		}
	}

	/**
	 * Hide all links where
	 * one or more of its linked objects are not visible.
	 * This option can be activated in the properties.
	 */
	private void filterIncompleteLinks() {
		Collection<MLink> mLinks = getMLinks();
		for (MLink link : mLinks) {
			for (MObject object : link.linkedObjects()) {
				PlaceableNode node = dataSource.getNodeForObject(object);
				if (!node.isVisible()) {
					this.hideNode(dataSource.getNodeForObject(link));
				}
			}
		}
	}

	/**
	 * Finds links which are hidden but the
	 * linked objects to it are visible.
	 * This option can be activated in the properties.
	 *
	 * @param showMissingLinks find simple associations
	 * @param showMissingObjectLinks find association classes
	 * @return a collection of MLinks
	 */
	private Collection<MLink> findRelevantLinks(boolean showMissingLinks,
												boolean showMissingObjectLinks) {
		Collection<MLink> mLinks = getMLinks();
		Collection<MLink> foundMLinks = new ArrayList<>();
		for (MLink link : mLinks) {
			if (link instanceof  MObject) {
				if (showMissingObjectLinks && areLinkedObjectsVisible(link)) {
					PlaceableNode node = dataSource.getNodeForObject(link);
					if (node != null && node.isHidden()) {
						foundMLinks.add(link);
					}
				}
			} else {
				if (showMissingLinks && areLinkedObjectsVisible(link)) {
					PlaceableNode node = dataSource.getNodeForObject(link);
					if (node != null && node.isHidden()) {
						foundMLinks.add(link);
					}
				}
			}
		}
		return foundMLinks;
	}

	/**
	 * Check if all objects linked to a link are currently visible
	 * @param link to check
	 * @return true if all related objects to the link are visible
	 */
	private boolean areLinkedObjectsVisible(MLink link) {
		List<MObject> linked = link.linkedObjects();
		boolean allVisible = true;
		for (MObject object : linked) {
			PlaceableNode node = dataSource.getNodeForObject(object);
			allVisible = allVisible && node.isVisible();
		}
		return allVisible;
	}

	/**
	 * If a link is visible then all objects linked to it
	 * are relevant for the diagram.
	 * This option can be activated in the properties.
	 * @return A collection of hidden objects which are part of visible links
	 */
	private Collection<MObject> hiddenObjectsOfVisibleLinks() {
		Collection<MLink> mLinks = getMLinks();
		Collection<MObject> relevantObjects = new ArrayList<>();
		for (MLink link : mLinks) {
			PlaceableNode node = dataSource.getNodeForObject(link);
			if (node.isVisible()) {
				for (MObject object : link.linkedObjects()) {
					PlaceableNode linkedNode = dataSource.getNodeForObject(object);
					if (linkedNode.isHidden()) {
						relevantObjects.add(object);
					}
				}
			}
		}
		return relevantObjects;
	}

	/**
	 * Finds hidden objects which are used as function parameter in visible messages.
	 * This option can be activated in the properties.
	 * @return A collection of objects which are used as parameter
	 */
	private Collection<MObject> hiddenObjectsUsedAsParameter() {
		Vector<MMessage> messages = dataSource.getMessages();
		Collection<MObject> makeVisibleObjects = new ArrayList<>();
		for (MMessage message : messages) {
			if (getSharedVisibleManager().getData().isEventVisible(message.getEvent())) {
				if (message.getEvent() instanceof OperationEnteredEvent) {
					if (message.getOwner().isVisible()) {
						Value[] functionParam =((OperationEnteredEvent) message.getEvent()).
								getOperationCall().getArguments();
						for (Value param : functionParam) {
							if (param instanceof ObjectValue) {
								MObject object = ((ObjectValue) param).value();
								makeVisibleObjects.add(object);
							}
						}
					}
				} else if (message.getEvent() instanceof AttributeAssignedEvent) {
					if (message.getOwner().isVisible()) {
						Value assignValue = ((AttributeAssignedEvent) message.getEvent()).getValue();
						if (assignValue instanceof ObjectValue) {
							MObject object = ((ObjectValue) assignValue).value();
							makeVisibleObjects.add(object);
						}
					}
				}
			}
		}
		return makeVisibleObjects;
	}

	/**
	 * This is called by the LayoutThread to generate a new layout. The layouter
	 * object can be reused as long as the graph and the size of the drawing
	 * area does not change.
	 */
	@SuppressWarnings("unchecked")
	protected synchronized void autoLayout() {
		if (fLayouter == null) {
			int w = getWidth();
			int h = getHeight();
			if (w == 0 || h == 0)
				return;
			fLayouter = new CommunicationDiagramLayout<PlaceableNode>(fGraph, w, h, 20, 20);
		}
		fLayouter.layout();
		repaint();
	}


	@Override
	public void restorePlacementInfos(PersistHelper helper,
									  int version) {
		if (version < 12)
			return;

		AutoPilot ap = new AutoPilot(helper.getNav());

		restoreUserNode(helper, version, ap);

		restoreCommunicationEdges(helper, version, ap);

		restoreLinkNodes(helper, version, ap);

		restoreObjectNodes(helper, version, ap);
	}

	/**
	 * @param helper to read a xml file
	 * @param version version of the xml
	 * @param ap xml navigator
	 */
	private void restoreObjectNodes(PersistHelper helper,
									int version,
									AutoPilot ap) {
		try {
			// Restore object nodes
			ap.selectXPath("./node[@type='Object Node']");
			try {
				while (ap.evalXPath() != -1) {
					String name = helper.getElementStringValue("name");
					BaseNode obn = null;

					String nameWithoutSequenceNumber = name.split("\\.")[0];
					MObject obj = fParent.system().state().objectByName(nameWithoutSequenceNumber);
					// if you do save the layout and do undo
					// and reload the layout obj will not be in the system
					if (obj != null) {
						obn = dataSource.getNodeForObject(obj);
						if (obn != null) {
							obn.restorePlacementInfo(helper, version);
						}
					}
				}
			} catch (XPathEvalException e) {
				fLog.append(e.getMessage());
			} catch (NavException e) {
				fLog.append(e.getMessage());
			}
		} catch (XPathParseException e) {
			fLog.append(e.getMessage());
		}
	}

	/**
	 * @param helper to read a xml file
	 * @param version version of the xml
	 * @param ap xml navigator
	 */
	private void restoreLinkNodes(PersistHelper helper,
								  int version,
								  AutoPilot ap) {
		// We need a set of all links that have ever existed to build the
		// key with the timestamp
		Map<String,MLink> allLinks = new HashMap<>();

		for (Event e : this.fParent.system().getAllEvents()) {
			if (e instanceof LinkInsertedEvent) {
				LinkInsertedEvent linkE = (LinkInsertedEvent)e;
				allLinks.put(linkE.getLink().toString(), linkE.getLink());
			}
		}

		try {
			ap.selectXPath("./node[@type='Link Node']");
			try {
				while (ap.evalXPath() != -1) {
					String completeName = helper.getElementStringValue("name");
					BaseNode lbn = null;

					String[] nameParts = completeName.split("\\.");
					String name         = nameParts[0];
					if (allLinks.containsKey(name)) {
						lbn = dataSource.getNodeForObject(allLinks.get(name));
						if (lbn != null) {
							lbn.restorePlacementInfo(helper, version);
						}
					}
				}
			} catch (XPathEvalException e) {
				fLog.append(e.getMessage());
			} catch (NavException e) {
				fLog.append(e.getMessage());
			}
		} catch (XPathParseException e) {
			fLog.append(e.getMessage());
		}
	}

	/**
	 * @param helper to read a xml file
	 * @param version version of the xml
	 * @param ap xml navigator
	 */
	private void restoreCommunicationEdges(PersistHelper helper,
										   int version,
										   AutoPilot ap) {
		try {
			// Restore edges
			ap.selectXPath("./edge[@type='Communication Edge']");
			try {
				while (ap.evalXPath() != -1) {
					String edgeName = helper.getElementStringValue("name");
					Iterator<CommunicationDiagramEdge> iter = Iterators.filter(
							fGraph.edgeIterator(),
							CommunicationDiagramEdge.class);

					//FIXME: ueber alle edges laufen und
					// dann XPAth auswerten sollte schneller sein
					while (iter.hasNext()) {
						EdgeBase edge = iter.next();
						if (edge.getName().equals(edgeName)) {
							edge.restorePlacementInfo(helper, version);
							break;
						}
					}
				}
			} catch (XPathEvalException e) {
				fLog.append(e.getMessage());
			} catch (NavException e) {
				fLog.append(e.getMessage());
			}
		} catch (XPathParseException e) {
			fLog.append(e.getMessage());
		}
	}

	/**
	 * @param helper to read a xml file
	 * @param version version of the xml
	 * @param ap xml navigator
	 */
	private void restoreUserNode(PersistHelper helper,
								 int version,
								 AutoPilot ap) {
		try {
			ap.selectXPath("./node[@type='User Node']");
			try {
				while (ap.evalXPath() != -1) {
					String name = helper.getElementStringValue("name");
					ActorNode node = actorSymbolNode;
					if (!name.equals("Actor")) {
						node.getActorData().setUserName(name);
					}
					node.restorePlacementInfo(helper, version);
				}
			} catch (XPathEvalException e) {
				fLog.append(e.getMessage());
			} catch (NavException e) {
				fLog.append(e.getMessage());
			}

		} catch (XPathParseException e) {
			fLog.append(e.getMessage());
		}
	}
}