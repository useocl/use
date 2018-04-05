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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
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
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyFixed;
import org.tzi.use.gui.views.diagrams.elements.positioning.StrategyUnmovable;
import org.tzi.use.gui.views.diagrams.event.ActionHideCommunicationDiagram;
import org.tzi.use.gui.views.diagrams.event.ActionLoadLayout;
import org.tzi.use.gui.views.diagrams.event.ActionSaveLayout;
import org.tzi.use.gui.views.diagrams.event.DiagramInputHandling;
import org.tzi.use.gui.views.selection.objectselection.ObjectSelection;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.events.AttributeAssignedEvent;
import org.tzi.use.uml.sys.events.Event;
import org.tzi.use.uml.sys.events.LinkDeletedEvent;
import org.tzi.use.uml.sys.events.LinkInsertedEvent;
import org.tzi.use.uml.sys.events.ObjectCreatedEvent;
import org.tzi.use.uml.sys.events.ObjectDestroyedEvent;
import org.tzi.use.uml.sys.events.OperationEnteredEvent;
import org.tzi.use.uml.sys.events.OperationExitedEvent;
import org.tzi.use.util.StringUtil;
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
 * @param <E>
 *
 * 
 */
@SuppressWarnings("serial")
public class CommunicationDiagram extends DiagramViewWithObjectNode {

	/**
	 * Because use objects and links don't consider the
	 * creation time we need an additional class for the
	 * model element keys. 
	 * 
	 * @author Lars Hamann
	 *
	 */
	private class ElementKey {
		private final Object element;

		private final int timestamp;

		public ElementKey(Object element, int timestamp) {
			if (!(element instanceof MLink) && !(element instanceof MObject))
				throw new IllegalArgumentException("Key element must be an MLink or MObject");

			this.element = element;
			this.timestamp = timestamp;
		}

		@Override
		public int hashCode() {
			return element.hashCode() * timestamp;
		}

		@Override
		public boolean equals(Object obj) {
			ElementKey key = (ElementKey)obj;
			return this.timestamp == key.timestamp
					&& this.element.equals(key.element);
		}
	}

	public class CommunicationDiagramData implements DiagramData {

		/**
		 * Since MLink and MObject don't have a common supertype or
		 * interface (needs to be fixed) we must use a map with type object
		 * as key.
		 * However, only MLink or MObject instances should be contained
		 */
		private Map<ElementKey, BaseNode> elementsToNodeMap;



		public CommunicationDiagramData() {
			elementsToNodeMap = new HashMap<>();
		}

		@Override
		public Set<PlaceableNode> getNodes() {
			return new HashSet<PlaceableNode>(elementsToNodeMap.values());
		}

		@Override
		public Set<EdgeBase> getEdges() {
			return Sets.newHashSet(getGraph().edgeIterator());
		}

		@Override
		public boolean hasNodes() {
			return !elementsToNodeMap.isEmpty();
		}

		/**
		 * Copies all data to the target object
		 * 
		 * @param hiddenData
		 */
		public void copyTo(CommunicationDiagramData target) {
			target.elementsToNodeMap.putAll(this.elementsToNodeMap);
		}

		/**
		 * Removes all data
		 */
		public void clear() {
			this.elementsToNodeMap.clear();
		}
	}

	private CreationTimeRecorder messageRecorder = new CreationTimeRecorder();

	public static final Color ACTIVATED_MESSAGE_COLOR = new Color(80, 136, 252);

	private CommunicationDiagramView fParent;
	private ActorNode actorSymbolNode;
	private ActorChangeNameDialog actorNameDialog;

	private CommunicationDiagramData visibleData = new CommunicationDiagramData();

	/**
	 * The position of the next object node. This is either set to a random
	 * value or to a specific position when an object is created by drag & drop.
	 */
	private Point2D.Double nextNodePosition = new Point2D.Double();

	private ObjectSelection fSelection;
	private DiagramInputHandling inputHandling;

	private Stack<MOperationCall> operationsStack;
	private List<MObject> operationsCaller = new ArrayList<MObject>();

	private List<Integer> sequenceNumbers;

	private MessagesNavigationDialog naviDialog;
	private CommunicationDiagramEdge activatedEdge;
	private MessagesGroup activatedMessage;

	private boolean[]  messageFilter = new boolean[5];
	private Class<?>[] messageFilterTypes = new Class<?>[] {
			ObjectCreatedEvent.class,
			ObjectDestroyedEvent.class,
			LinkInsertedEvent.class,
			LinkDeletedEvent.class,
			AttributeAssignedEvent.class
	};

	private boolean resetEnum = false;

	private int startMessage = -1;
	private int endMessage = -1;

	private boolean showAllMessages = true;

	/**
	 * Creates a new empty diagram.
	 */
	CommunicationDiagram(CommunicationDiagramView parent, PrintWriter log) {
		this(parent, log, new CommunicationDiagramOptions());
	}

	CommunicationDiagram(CommunicationDiagramView parent, PrintWriter log, CommunicationDiagramOptions options) {
		super(options, log);
		this.computeNextRandomPoint();

		operationsStack = new Stack<MOperationCall>();
		sequenceNumbers = new ArrayList<Integer>();
		sequenceNumbers.add(1);

		for (int i = 0; i < messageFilter.length; i++) {
			messageFilter[i] = false;
		}

		fParent = parent;
		parent.setFont(getFont());

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

		initializeActor();
		startLayoutThread();
	}


	@Override
	public void initialize() {
		calculateStates();
		super.initialize();
	}

	private void initializeActor() {
		actorSymbolNode = new ActorNode(new Actor());
		actorSymbolNode.setPosition(getWidth() / 2, getHeight() / 2);
		computeNextRandomPoint();
		fGraph.add(actorSymbolNode);
	}

	/**
	 * Finds a new position for the next object node.
	 */
	private void computeNextRandomPoint() {
		nextNodePosition.x = (0.4 + Math.random() * 0.6) * Math.max(200, getWidth() - 50);
		nextNodePosition.y = (0.4 + Math.random() * 0.6) * Math.max(200, getHeight() - 50);
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

		// Split selected nodes into model elements
		for (PlaceableNode node : fNodeSelection) {
			if (node instanceof ObjectNodeActivity) {
				selectedNodesSet.add(node);
			} else if (node instanceof LinkBoxNode) {
				selectedNodesSet.add(node);
			}
		}

		// This text is reused often
		String selectedObjectsText = null;
		if (selectedNodesSet.size() == 1) {
			selectedObjectsText = Iterators.getOnlyElement(selectedNodesSet.iterator()).getTextForMenu();
		} else if (selectedNodesSet.size() > 1) {
			selectedObjectsText = selectedNodesSet.size() + " Nodes";
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

		if (CollectionUtil.exists(fGraph.iterator(), new Predicate<PlaceableNode>() {
			@Override
			public boolean apply(PlaceableNode input) {
				return !input.isVisible();
			}
		})) {
			final JMenuItem showAllObjects = new JMenuItem("Show hidden objects");
			showAllObjects.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					showAll();
					invalidateContent(true);
				}
			});
			popupMenu.insert(showAllObjects, pos++);
		}

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

		popupMenu.insert(new JSeparator(), pos++);

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
		showAllCommandsItem.setState(!messageFilter[0]&&!messageFilter[1]&&!messageFilter[2]
				&&!messageFilter[3]&&!messageFilter[4]);
		showAllCommandsItem.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(showAllCommandsItem.isSelected()){
					for (int i = 0; i < messageFilter.length; i++) {
						messageFilter[i] = false;
					}
					filterGraphByEvent(messageFilter);
				}else{
					for (int i = 0; i < messageFilter.length; i++) {
						messageFilter[i] = true;
					}
					filterGraphByEvent(messageFilter);
				}

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

		final JMenuItem showSomeMessagesItem = new JMenuItem("Select message intervall...");
		showSomeMessagesItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createMessageChooseWindow();
			}
		});

		popupMenu.insert(showSomeMessagesItem, pos++);

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

		final JMenuItem messageDepthItem  = new JMenuItem("Select message depth...");
		messageDepthItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createMessageDepthWindow();
			}
		});

		popupMenu.insert(messageDepthItem, pos++);

		final JCheckBoxMenuItem showAllMessagesItem  = new JCheckBoxMenuItem("Show all messages");
		showAllMessagesItem.setState(showAllMessages);
		showAllMessagesItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				filterGraphByMessageDepth(0);
			}
		});
		popupMenu.insert(showAllMessagesItem, pos++);
		popupMenu.insert(new JSeparator(), pos++);

		final JMenuItem fixActorItem;

		if (actorSymbolNode.isUnmovable()) {
			// new menu item "Set actor movable"
			fixActorItem = new JMenuItem("Set actor movable");
			fixActorItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					actorSymbolNode.setStrategy(StrategyFixed.instance);
					actorSymbolNode.setUnmovable(false);
					getStatusBar().showTmpMessage("Actor was set to movable");
				}
			});
		} else {
			// new menu item "Set actor unmovable"
			fixActorItem = new JMenuItem("Set actor unmovable");
			fixActorItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					actorSymbolNode.setStrategy(StrategyUnmovable.instance);
					actorSymbolNode.setUnmovable(true);
					getStatusBar().showTmpMessage("Actor was set to unmovable");
				}
			});
		}

		popupMenu.insert(fixActorItem, pos++);		

		final JMenuItem actorNameItem = new JMenuItem("Change actor name...");
		actorNameItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				actorNameDialog = new ActorChangeNameDialog(fParent.getCommunicationDiagram());
				actorNameDialog.setVisible(true);
			}
		});

		popupMenu.insert(actorNameItem, pos++);
		popupMenu.insert(new JSeparator(), pos++);

		popupMenu.add(getMenuItemCommentNode(popupInfo));
		popupMenu.addSeparator();
		popupMenu.add(getMenuAlign());
		popupMenu.add(getMenuItemAntiAliasing());
		popupMenu.add(getMenuItemShowGrid());

		addLayoutMenuItems(popupMenu);

		return popupInfo;
	}

	/**
	 * Creates a new CmdChooseWindow object.
	 */
	private void createCmdChooseWindow() {
		CmdChooseWindowComDiag chooseWin = new CmdChooseWindowComDiag(this);
		chooseWin.showWindow();
	}

	/**
	 * Creates a new MessageChooseWindow.
	 */
	private void createMessageChooseWindow(){
		MessageChooseWindow msgWindow = new MessageChooseWindow(this);
		msgWindow.showWindow();
	}

	/**
	 * Creates a new MessageDepthWindow.
	 */

	private void createMessageDepthWindow(){
		MessageDepthWindow msgDepthwindow = new MessageDepthWindow(this);
		msgDepthwindow.setSize(200, 120);
		msgDepthwindow.setLocationRelativeTo(this);
		msgDepthwindow.setVisible(true);
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
	/**
	 * filters the messages that are in visible range
	 * @param showedMessages
	 */

	public void showSomeMessages(int startMessage, int endMessage){
		this.startMessage = startMessage;
		this.endMessage = endMessage;

		for (int i = 0; i < getMessages().size(); i++) {
			if(i >= startMessage && i <= endMessage ){
				getMessages().get(i).setVisible(true);
			}else
			{
				getMessages().get(i).setVisible(false);
			}
		}

		calculateStates();
		filterEdges();
		setRelativSequenceNumbers();
		invalidateContent(true);
	}

	/**
	 * @param fNodesToHide
	 */
	public void hideElementsInCommunicationDiagram(Set<PlaceableNode> nodesToHide) {
		for (PlaceableNode n : nodesToHide) {
			hideNode(n);
		}
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
		storePlacementInfos(helper, rootElement, true);
		storePlacementInfos(helper, rootElement, false);
	}

	/**
	 * @param helper
	 * @param rootElement
	 * @param visible
	 */
	private void storePlacementInfos(PersistHelper helper, Element rootElement, boolean visible) {
		if (!visible) return;

		CommunicationDiagramData data = visibleData;

		for (BaseNode n : data.elementsToNodeMap.values()) {
			n.storePlacementInfo(helper, rootElement, !n.isVisible());
		}

		Iterator<EdgeBase> edgeIter = fGraph.edgeIterator(); 
		while(edgeIter.hasNext()) {
			EdgeBase e = edgeIter.next();
			e.storePlacementInfo(helper, rootElement, !e.isVisible());
		}
	}

	@Override
	public void restorePlacementInfos(PersistHelper helper, int version) {
		if (version < 12)
			return;

		AutoPilot ap = new AutoPilot(helper.getNav());

		restoreUserNode(helper, version, ap);

		restoreCommunicationEdges(helper, version, ap);

		restoreLinkNodes(helper, version, ap);

		restoreObjectNodes(helper, version, ap);
	}

	/**
	 * @param helper
	 * @param version
	 * @param ap
	 */
	private void restoreObjectNodes(PersistHelper helper, int version, AutoPilot ap) {
		try {
			// Restore object nodes
			ap.selectXPath("./node[@type='Object Node']");
			try {
				while (ap.evalXPath() != -1) {
					String name = helper.getElementStringValue("name");
					BaseNode obn = null;

					String nameWithoutSequenceNumber = name.split("\\.")[0];
					MObject obj = fParent.system().state().objectByName(nameWithoutSequenceNumber);
					obn = getNodeForObject(obj);
					if (obn != null) {
						obn.restorePlacementInfo(helper, version);
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
	 * @param helper
	 * @param version
	 * @param ap
	 */
	private void restoreLinkNodes(PersistHelper helper, int version, AutoPilot ap) {
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
					int timeStamp       = Integer.valueOf(nameParts[1]);

					if (allLinks.containsKey(name)) {
						ElementKey key = new ElementKey(allLinks.get(name), timeStamp);
						if (visibleData.elementsToNodeMap.containsKey(key)) {
							lbn = visibleData.elementsToNodeMap.get(key);
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
	 * @param helper
	 * @param version
	 * @param ap
	 */
	private void restoreCommunicationEdges(PersistHelper helper, int version, AutoPilot ap) {
		try {
			// Restore edges
			ap.selectXPath("./edge[@type='Communication Edge']");
			try {
				while (ap.evalXPath() != -1) {
					String edgeName = helper.getElementStringValue("name");
					Iterator<CommunicationDiagramEdge> iter = Iterators.filter(
							fGraph.edgeIterator(),
							CommunicationDiagramEdge.class);

					//FIXME: ueber alle edges laufen und dann XPAth auswerten sollte schneller sein
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
	 * @param helper
	 * @param version
	 * @param ap
	 */
	private void restoreUserNode(PersistHelper helper, int version, AutoPilot ap) {
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

	/**
	 * @return the activatedEdge
	 */
	public CommunicationDiagramEdge getActivatedEdge() {
		return activatedEdge;
	}

	/**
	 * @param activatedEdge the activatedEdge to set
	 */
	public void setActivatedEdge(CommunicationDiagramEdge activatedEdge) {
		this.activatedEdge = activatedEdge;
	}

	/**
	 * @return the activatedMessage
	 */
	public MessagesGroup getActivatedMessage() {
		return activatedMessage;
	}

	/**
	 * @param activatedMessage the activatedMessage to set
	 */
	public void setActivatedMessage(MessagesGroup activatedMessage) {
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

	public Vector<MMessage> getMessages() {
		return messageRecorder.getMessages();
	}

	void changeActorName(String newName) {
		actorSymbolNode.getActorData().setUserName(newName);
	}

	public String getActorName() {
		return actorSymbolNode.getActorData().getUserName();
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

	/**
	 * resets the numbering from messages
	 * @param enable
	 */

	private void resetEnum(boolean enable){
		Iterator<CommunicationDiagramEdge> iter = Iterators.filter(
				fGraph.edgeIterator(),
				CommunicationDiagramEdge.class);

		if (enable) {
			setRelativSequenceNumbers();
			while(iter.hasNext()) {
				iter.next().getMessagesGroup().resetEnum(enable);
			}

		} else {
			while(iter.hasNext()) {
				iter.next().getMessagesGroup().resetEnum(enable);
			}
		}

		invalidateContent(true);
	}

	/**
	 * Returns the actual states of the shown message events
	 * @return !messageFilter
	 */
	public boolean isCreateSelected(){
		return !messageFilter[0];
	}

	public boolean isDestroySelected(){
		return !messageFilter[1];
	}

	public boolean isInsertSelected(){
		return !messageFilter[2];
	}

	public boolean isDeleteSelected(){
		return !messageFilter[3];
	}

	public boolean isSetSelected(){
		return !messageFilter[4];
	}

	/**
	 * Filters the Graph by the actual Events  
	 * @param filter: contains the selected event filters
	 */
	public void filterGraphByEvent(boolean[] filter) {		
		messageFilter = filter;
		boolean visible;

		for (MMessage msg : this.getMessages()) {
			visible = true;
			msg.setAbsentFromGraphByEvent(!visible);

			for (int i = 0; i < this.messageFilter.length; ++i) {
				visible = this.messageFilter[i]
						&& this.messageFilterTypes[i].equals(msg.getEvent()
								.getClass());
				if(visible){
					msg.setAbsentFromGraphByEvent(true);
				}
			}			
		}

		calculateStates();
		filterEdges();
		setRelativSequenceNumbers();

		invalidateContent(true);
	}

	/**
	 * filter the graph by selected message depth
	 * @param depth
	 */
	public void filterGraphByMessageDepth(int depth){

		if (depth != 0 ) {
			showAllMessages = false;
		} else {
			showAllMessages = true;
		}


		for (MMessage msg : getMessages()) {
			msg.setAbsentFromGraphByDepth(false);

			if(depth != 0){
				if(msg.getSequenceNumber().split("\\.").length > depth){
					msg.setAbsentFromGraphByDepth(true);
				}
			}
		}

		calculateStates();
		filterEdges();

		setRelativSequenceNumbers();
		invalidateContent(true);
	}

	/**
	 * Filters the edges on the basis of visible nodes.
	 */
	private void filterEdges() {
		Iterator<CommunicationDiagramEdge> iter = Iterators.filter(fGraph.edgeIterator(), CommunicationDiagramEdge.class);
		while(iter.hasNext()) {
			CommunicationDiagramEdge edge = iter.next();

			if(edge.getMessages().size() > 0 && edge.getMessagesGroup().getEdgeMessages().size() == 0){
				edge.setAbsentInCurrentView(true);
			}else{
				if(edge instanceof LinkCommunicationDiagramEdge){

					if(!edge.source().isAbsentFromGraph() && edge.source().isVisible()
							&& !edge.target().isAbsentFromGraph() && edge.target().isVisible()){
						edge.setAbsentInCurrentView(false);
					}else{
						edge.setAbsentInCurrentView(true);
					}
				}else {
					edge.setAbsentInCurrentView(false);
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
		
		for (MMessage msg : getMessages()) {
			if(msg.isVisible() && !msg.isAbsentFromGraph() && msg.getOwner().isVisible()){
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
	 * @param counters: contains the sequence number parts, which are separated by a dot
	 * @param breakPoint
	 * @return
	 */
	private String buildNewSequenceNumber(ArrayList<Integer> counters, int breakPoint){
		String newSequenceNumber = "";
		
		for (int i = 0; i < breakPoint; i++) {
			newSequenceNumber = newSequenceNumber + counters.get(i);
			
			if(i < breakPoint-1){
				newSequenceNumber = newSequenceNumber + ".";
			}
		}
		
		return newSequenceNumber;
	}

	/**
	 * Calculates the actual state of all object and changes them when it is necessary
	 * Further it manages on basis of states the visibility of all objects
	 */
	private void calculateStates() {

		Map<ElementKey, BaseNode> nodeMap = this.visibleData.elementsToNodeMap;
		// Reset to persistent
		for (BaseNode n : nodeMap.values()) {
			n.setState(ObjectState.PERSISTENT);
			n.setAbsentFromGraph(true);
		}

		BaseNode node;
		// We might not consider all events here, therefore we
		// need to keep "our clock" in synch. 
		int start = (startMessage == -1 ? 0 : startMessage);
		int end =   (endMessage == -1 ? this.getMessages().size() - 1 : endMessage);

		for (int i = start; i <= end; ++i) {
			if(getMessages().get(i).isVisible() && !getMessages().get(i).isAbsentFromGraph()){

				Event event = this.getMessages().get(i).getEvent();
				CommunicationDiagramEdge edge = getMessages().get(i).getOwner();

				edge.source().setAbsentFromGraph(false);
				edge.target().setAbsentFromGraph(false);

				if (event instanceof ObjectCreatedEvent) {
					MObject obj = ((ObjectCreatedEvent)event).getCreatedObject();
					nodeMap.get(createElementKey(obj, i)).setState(ObjectState.NEW);

				} else if (event instanceof ObjectDestroyedEvent) {
					MObject obj = ((ObjectDestroyedEvent)event).getDestroyedObject();
					node = nodeMap.get(createElementKey(obj, i));

					if (node.getState() == ObjectState.NEW) {
						node.setState(ObjectState.TRANSIENT);
					} else {
						node.setState(ObjectState.DELETED);
					}

				} else if (event instanceof LinkInsertedEvent) {
					MLink link = ((LinkInsertedEvent)event).getLink();
					nodeMap.get(createElementKey(link, i)).setState(ObjectState.NEW);

				} else if (event instanceof LinkDeletedEvent) {
					MLink link = ((LinkDeletedEvent)event).getLink();
					node = nodeMap.get(createElementKey(link, i));

					if (node.getState() == ObjectState.NEW) {
						node.setState(ObjectState.TRANSIENT);
					} else {
						node.setState(ObjectState.DELETED);
					}
				}
			}
		}
	}

	/**
	 * returns the maximum depth of the Messages 
	 * f.e 3.2.1: create(Person) is the deepest message, also the depth is 3
	 * @return
	 */

	public int getMessageDepth(){
		int depth = 0;
		int var = 0;

		for (MMessage msg : getMessages()) {
			var = msg.getSequenceNumber().split("\\.").length;

			if(var > depth){
				depth = var;
			}
		}

		return depth;
	}

	private ElementKey createElementKey(Object element, int currentTime) {
		return new ElementKey(element, messageRecorder.getLastCreationTime(element, currentTime));
	}

	@Override
	public void hideObject(MObject obj) {
		hideNode(getNodeForObject(obj));
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
		this.showNode(getNodeForObject(obj));
	}

	@Override
	public void showAll() {
		for (PlaceableNode node : fGraph) {
			showNode(node);
		}
		this.filterEdges();
		this.invalidateContent(true);
	}

	/**
	 * Hides all currently visible elements. The diagram is not repainted!
	 */
	@Override
	public void hideAll() {
		for (PlaceableNode node : fGraph) {
			hideNode(node);
		}
		this.invalidateContent(true);
	}

	@Override
	protected String getDefaultLayoutFileSuffix() {
		return "_comdia.clt";
	}

	@Override
	public DiagramData getVisibleData() {
		return visibleData;
	}

	@Override
	public DiagramData getHiddenData() {
		return null;
	}

	public String getSequenceNumber() {
		String sequenceNumber = sequenceNumbers.get(0).toString();
		for (int i = 1; i < sequenceNumbers.size(); i++) {
			sequenceNumber += "." + sequenceNumbers.get(i);
		}
		return sequenceNumber;
	}

	private void raiseSequenceNumber() {
		if (sequenceNumbers.size() > 0) {
			int lastNumber = sequenceNumbers.get(sequenceNumbers.size() - 1);
			sequenceNumbers.set(sequenceNumbers.size() - 1, lastNumber + 1);
		}
	}

	protected void addObjectCreatedEvent(ObjectCreatedEvent e) {
		PlaceableNode callOpNode;
		MObject obj = e.getCreatedObject();

		if (operationsStack.isEmpty()) {
			callOpNode = actorSymbolNode;
		} else {
			MObject callOpObject = operationsStack.peek().getSelf();
			callOpNode = getNodeForObject(callOpObject);
		}

		ObjectBoxNode newObjectBoxNode = new ObjectBoxNode(obj, getSequenceNumber(), fParent, fOpt);
		newObjectBoxNode.calculateBounds();
		newObjectBoxNode.setPosition(nextNodePosition);
		computeNextRandomPoint();
		fGraph.add(newObjectBoxNode);
		visibleData.elementsToNodeMap.put(new ElementKey(obj, messageRecorder.getTime()), newObjectBoxNode);

		MMessage mess = new MMessage(e, getSequenceNumber(), "create");
		messageRecorder.addMessage(mess);
		raiseSequenceNumber();

		CommunicationDiagramEdge cde = CommunicationDiagramEdge.create(callOpNode, newObjectBoxNode, this, false);
		cde.addNewMessage(mess);

		fGraph.addEdge(cde);
		fLayouter = null;
	}

	/**
	 * @param obj
	 */
	protected void addObjectDestroyedEvent(ObjectDestroyedEvent event) {
		PlaceableNode callOpNode;
		BaseNode objectNodeToDestroy;
		MObject obj = event.getDestroyedObject();

		if (operationsStack.isEmpty()) {
			callOpNode = actorSymbolNode;
		} else {
			MObject callOpObject = operationsStack.peek().getSelf();
			callOpNode = getNodeForObject(callOpObject);
		}

		objectNodeToDestroy = getNodeForObject(obj);

		// Case of redo
		if (objectNodeToDestroy == null)
			return;

		MMessage mess = new MMessage(event, getSequenceNumber(), "destroy");
		messageRecorder.addMessage(mess);
		raiseSequenceNumber();

		CommunicationDiagramEdge edge = getSingleEdge(objectNodeToDestroy, callOpNode);
		if (edge == null) {
			edge = new CommunicationDiagramEdge(callOpNode, objectNodeToDestroy, this, false);
			fGraph.invalidateEdge(edge);
		}
		edge.addNewMessage(mess);

		fLayouter = null;
	}

	/**
	 * @param link
	 */
	protected void addLinkInsertedEvent(LinkInsertedEvent event) {
		PlaceableNode callOpNode;

		MLink link = event.getLink();

		if (operationsStack.isEmpty()) {
			callOpNode = actorSymbolNode;
		} else {
			MObject callOpObject = operationsStack.peek().getSelf();
			callOpNode = getNodeForObject(callOpObject);
		}


		LinkBoxNode newLinkNode = new LinkBoxNode(link, getSequenceNumber(), fParent, fOpt);
		newLinkNode.setPosition(nextNodePosition);
		fGraph.add(newLinkNode);
		visibleData.elementsToNodeMap.put(new ElementKey(link, messageRecorder.getTime()), newLinkNode);

		CommunicationDiagramEdge cde;
		cde = CommunicationDiagramEdge.create(callOpNode, newLinkNode, this, false);
		cde.setDashed(true);
		String messageLabel = "insert(";

		List<CommunicationDiagramEdge> edges = new ArrayList<CommunicationDiagramEdge>();

		for (MObject obj : link.linkedObjects()) {
			BaseNode linkedNode = getNodeForObject(obj);

			messageLabel += String.format("@%s,", obj.name());
			//			if (linkedNode.equals(callOpNode)) {
			//				continue;
			//			}
			LinkCommunicationDiagramEdge linkEdge = LinkCommunicationDiagramEdge.create(newLinkNode, linkedNode, this, false);

			fGraph.addEdge(linkEdge);
			edges.add(linkEdge);
		}

		messageLabel = messageLabel.substring(0, messageLabel.length() - 1) + ")";

		MMessage mess = new MMessage(event, getSequenceNumber(), messageLabel);
		messageRecorder.addMessage(mess);
		cde.addNewMessage(mess);
		fGraph.addEdge(cde);
		fLayouter = null;

		raiseSequenceNumber();
	}

	/**
	 * Removes a link from the diagram.
	 */
	protected void addLinkDeletedEvent(LinkDeletedEvent event) {
		PlaceableNode callOpNode;
		BaseNode linkNodeToDelete;
		MLink link = event.getLink();

		if (operationsStack.isEmpty()) {
			callOpNode = actorSymbolNode;
		} else {
			MObject callOpObject = operationsStack.peek().getSelf();
			callOpNode = getNodeForObject(callOpObject);
		}

		linkNodeToDelete = visibleData.elementsToNodeMap.get(createElementKey(link, messageRecorder.getTime()));

		// Case of redo
		if (linkNodeToDelete == null)
			return;

		MMessage mess = new MMessage(event, getSequenceNumber(), "delete");
		messageRecorder.addMessage(mess);
		raiseSequenceNumber();

		CommunicationDiagramEdge edge = null;
		Set<EdgeBase> existingEdges = fGraph.edgesBetween(callOpNode, linkNodeToDelete, false);

		if (existingEdges.isEmpty()) {
			edge = new CommunicationDiagramEdge(callOpNode, linkNodeToDelete, this, false);
			edge.addNewMessage(mess);
			fGraph.addEdge(edge);
		} else {
			edge = (CommunicationDiagramEdge)existingEdges.iterator().next();
			edge.addNewMessage(mess);
		}

		fLayouter = null;
	}

	protected void addAttributeAssignedEvent(AttributeAssignedEvent event) {
		PlaceableNode callOpNode;
		BaseNode objectNodeToAssign;
		MObject object = event.getObject();
		MAttribute attribute = event.getAttribute();
		Value value = event.getValue();

		if (operationsStack.isEmpty()) {
			callOpNode = actorSymbolNode;
		} else {
			MObject callOpObject = operationsStack.peek().getSelf();
			callOpNode = getNodeForObject(callOpObject);
		}

		objectNodeToAssign = getNodeForObject(object);

		MMessage mess = new MMessage(event, getSequenceNumber(), String.format("set %s := %s", attribute.name(), value.toString()));
		messageRecorder.addMessage(mess);
		raiseSequenceNumber();
		CommunicationDiagramEdge edge = getSingleEdge(callOpNode, objectNodeToAssign);

		if (edge == null) {
			edge = new CommunicationDiagramEdge(callOpNode, objectNodeToAssign, this, false);
			fGraph.addEdge(edge);
		}

		edge.addNewMessage(mess);
		fLayouter = null;
	}

	/**
	 * Returns the node for the given object.
	 * If obj is a link object, it is a LinkBoxNode, otherise
	 * it is a ObjectBoxNode. 
	 * @param obj
	 * @return
	 */
	private BaseNode getNodeForObject(Object obj) {
		BaseNode node = this.visibleData.elementsToNodeMap.get(createElementKey(obj, messageRecorder.getLastCreationTime(obj)));
		return node;
	}

	void addOperationEnteredEvent(OperationEnteredEvent event) {
		MOperationCall operationCall = event.getOperationCall();
		operationsStack.add(operationCall);
		MOperation operation = operationCall.getOperation();

		StringBuilder msgLabel = new StringBuilder();
		msgLabel.append(operation.name());
		msgLabel.append("(");
		StringUtil.fmtSeq(msgLabel, operationCall.getArgumentsAsNamesAndValues().values(), ",");
		msgLabel.append(")");

		MMessage mess = new MMessage(event, getSequenceNumber(), msgLabel.toString());
		messageRecorder.addMessage(mess);
		MObject enterOpObject = operationCall.getSelf();

		BaseNode enterOpNode;
		enterOpNode = getNodeForObject(enterOpObject);

		CommunicationDiagramEdge edge = null;

		if (!operationsCaller.isEmpty()) {
			MObject callOpObject = operationsCaller.get(operationsCaller.size() - 1);
			BaseNode callOpNode;

			callOpNode = getNodeForObject(callOpObject);

			edge = getSingleEdge(callOpNode, enterOpNode);

			if (edge == null) {
				edge = new CommunicationDiagramEdge(callOpNode, enterOpNode, this, false);
				fGraph.addEdge(edge);
			}
		} else {
			// Actor is calling
			edge = getSingleEdge(this.actorSymbolNode, enterOpNode);
		}

		operationsCaller.add(enterOpObject);
		edge.addNewMessage(mess);
		fLayouter = null;
		sequenceNumbers.add(1);
	}

	/**
	 * @param operationCall
	 */
	void addOperationExitedEvent(OperationExitedEvent event) {
		MOperationCall operationCall = event.getOperationCall();
		MObject obj = operationCall.getSelf();
		BaseNode obn = getNodeForObject(obj);
		
		if (!operationsCaller.isEmpty()) {
			operationsCaller.remove(operationsCaller.size() - 1);
		}
		sequenceNumbers.remove(sequenceNumbers.size() - 1);

		if (operationCall.getResultValue() != null) {
			MMessage mess = new MMessage(event, getSequenceNumber(), operationCall.getResultValue().toString());
			mess.setDirection(MMessage.RETURN);
			if (!operationCall.exitedSuccessfully()) {
				mess.setFailedReturn(true);
			}
			messageRecorder.addMessage(mess);

			PlaceableNode sourceNode;
			if (!operationsCaller.isEmpty()) {
				MObject sourceObject = operationsCaller.get(operationsCaller.size() - 1);
				sourceNode = getNodeForObject(sourceObject);
			} else {
				sourceNode = actorSymbolNode;
			}
				
			CommunicationDiagramEdge edge = getSingleEdge(sourceNode, obn);
			if (edge == null) {
				edge = new CommunicationDiagramEdge(sourceNode, obn, this, false);
				fGraph.addEdge(edge);
			}

			edge.addNewMessage(mess);
			
			fLayouter = null;
		}

		if (!operationsStack.isEmpty()) {
			operationsStack.pop();
		}

		raiseSequenceNumber();
	}

	/**
	 * Returns the edge between <code>n1</code> and <code>n2</code>
	 * if there exists exactly one edge between both nodes.
	 * Otherwise <code>null</code> is returned.
	 * @param sourceNode
	 * @param obn
	 * @return
	 */
	private CommunicationDiagramEdge getSingleEdge(PlaceableNode n1, PlaceableNode n2) {
		Set<EdgeBase> existingEdges = fGraph.edgesBetween(n1, n2, false);

		if (existingEdges.size() == 1) {
			return (CommunicationDiagramEdge)existingEdges.iterator().next();
		}

		return null;
	}

	@Override
	public Set<? extends PlaceableNode> getHiddenNodes() {
		return Sets.newHashSet(fGraph.getHiddenNodesIterator());
	}
	
	@Override
	public void moveObjectNode( MObject obj, int x, int y ) {
		PlaceableNode node = getNodeForObject(obj);
		if(node != null){
			node.moveToPosition(x, y);
		}
	}
}