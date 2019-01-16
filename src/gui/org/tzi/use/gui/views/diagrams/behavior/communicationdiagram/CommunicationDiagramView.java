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

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.util.List;

import javax.swing.*;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.PrintableView;
import org.tzi.use.gui.views.View;
import org.tzi.use.gui.views.diagrams.behavior.shared.VisibleDataManager;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.events.AttributeAssignedEvent;
import org.tzi.use.uml.sys.events.Event;
import org.tzi.use.uml.sys.events.LinkDeletedEvent;
import org.tzi.use.uml.sys.events.LinkInsertedEvent;
import org.tzi.use.uml.sys.events.ObjectCreatedEvent;
import org.tzi.use.uml.sys.events.ObjectDestroyedEvent;
import org.tzi.use.uml.sys.events.OperationEnteredEvent;
import org.tzi.use.uml.sys.events.OperationExitedEvent;
import org.tzi.use.uml.sys.events.StatementExecutedEvent;
import org.tzi.use.uml.sys.events.tags.EventContext;

/**
 * A Panel for displaying the communication diagrams
 * 
 * @author Quang Dung Nguyen
 * @author Carsten Schlobohm
 * 
 */
@SuppressWarnings("serial")
public class CommunicationDiagramView extends JPanel implements View,
		PrintableView, VisibleDataManager.VisibleDataObserver {

	private final MSystem system;
	private final MainWindow mainWindow;

	private CommunicationDiagram comDia;

	private VisibleDataManager visibleDataManager;

	/**
	 * Custom constructor
	 * @param mainWindow The parent view
	 * @param system USE system which stores all information about the current state
	 */
	public CommunicationDiagramView(MainWindow mainWindow, MSystem system) {
		this.mainWindow = mainWindow;
		this.system = system;

		setLayout(new BorderLayout());
		this.setFocusable(true);
		this.visibleDataManager = VisibleDataManager.createVisibleDataManager(system);
	}

	/**
	 * Do here all initialisation or function calls
	 * which needs 'this' as param or relies on it in some manner.
	 * Use the constructor for all other initialisation.
	 * Especially register listener only in this method, so
	 * we can prevent multi-threading issues because of a not fully
	 * initialed CommunicationDiagramView
	 */
	public void postConstruction() {
		initDiagram(false, null);
		this.visibleDataManager.registerObserver(this);
	}

	public static CommunicationDiagramView createCommunicationDiagramm(
			MainWindow mainWindow,
			MSystem system,
			VisibleDataManager visibleDataManager) {
		CommunicationDiagramView view = new CommunicationDiagramView(mainWindow, system);
		view.visibleDataManager = visibleDataManager;
		view.postConstruction();
		view.comDia.filterEdges();
		view.comDia.invalidateContent(true);
		return view;
	}

	/**
	 * @param loadDefaultLayout decides if a default layout is loaded
	 * @param opt contains diagram and selection settings
	 */
	void initDiagram(boolean loadDefaultLayout,
					 CommunicationDiagramOptions opt) {
		comDia = CommunicationDiagram.createCommunicationDiagram(this, mainWindow.logWriter(), opt, this.visibleDataManager);

		comDia.setStatusBar(mainWindow.statusBar());
		this.removeAll();
		JScrollPane scrollPane = new JScrollPane(comDia);
		scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		add(scrollPane);
		initState();

		if (loadDefaultLayout) {
			comDia.loadDefaultLayout();
		}
	}

	/**
	 * Does a full update of the view.
	 */
	private void initState() {
		List<Event> events = system.getAllEvents();

		for (Event event : events) {
			handleEvent(event);
		}

		comDia.initialize();
	}

	/**
	 * @param event a new message between objects
	 */
	private void handleEvent(Event event) {
		if (event instanceof ObjectCreatedEvent) {
			comDia.getComDiaData().addObjectCreatedEvent((ObjectCreatedEvent) event);
		}

		if (event instanceof ObjectDestroyedEvent) {
			comDia.getComDiaData().addObjectDestroyedEvent((ObjectDestroyedEvent) event);
		}

		if (event instanceof LinkInsertedEvent) {
			comDia.getComDiaData().addLinkInsertedEvent((LinkInsertedEvent) event);
		}

		if (event instanceof LinkDeletedEvent) {
			comDia.getComDiaData().addLinkDeletedEvent((LinkDeletedEvent) event);
		}

		if (event instanceof AttributeAssignedEvent) {
			comDia.getComDiaData().addAttributeAssignedEvent((AttributeAssignedEvent) event);
		}

		if (event instanceof OperationEnteredEvent) {
			comDia.getComDiaData().addOperationEnteredEvent((OperationEnteredEvent) event);
		}

		if (event instanceof OperationExitedEvent) {
			comDia.getComDiaData().addOperationExitedEvent((OperationExitedEvent) event);
		}
	}

	public CommunicationDiagram getCommunicationDiagram() {
		return comDia;
	}

	/**
	 * @return the fMainWindow
	 */
	public MainWindow getMainWindow() {
		return mainWindow;
	}

	@Override
	public void printView(PageFormat pf) {
		comDia.printDiagram(pf, "Communication diagram");
	}

	@Override
	public void export(Graphics2D g) {
		JComponent toExport = comDia;
		boolean oldDb = toExport.isDoubleBuffered();
		toExport.setDoubleBuffered(false);
		toExport.paint(g);
		toExport.setDoubleBuffered(oldDb);
	}

	@Override
	public float getContentHeight() {
		return comDia.getPreferredSize().height;
	}

	@Override
	public float getContentWidth() {
		return comDia.getPreferredSize().width;
	}

	@Override
	public void detachModel() {
		visibleDataManager.unregisterObserver(this);
		visibleDataManager.secureTermination();
	}

	public MSystem system() {
		return system;
	}


	void notifyDataManager() {
		visibleDataManager.notifyObservers(this);
	}

	@Override
	public void onObservableChanged() {
		comDia.filterGraph();
		comDia.filterEdges();
		comDia.invalidateContent(true);
	}

	@Override
	public void onStatement(Event event) {
		if (event.getContext() == EventContext.UNDO) {
			return;
		}
		handleEvent(event);

		comDia.initialize();
		comDia.applySettings();
		comDia.filterEdges();
		comDia.invalidateContent(true);
	}

	@Override
	public void onEventExecuted(StatementExecutedEvent event) {
		if (event.getContext() == EventContext.UNDO) {
			comDia.onClosing();
			CommunicationDiagramOptions settings = comDia.getSettings();
			String backup = comDia.saveLayoutAsString();
			initDiagram(true, settings);
			if (backup != null && !backup.equals("")) {
				comDia.loadLayoutFromString(backup);
			}
			comDia.afterSelectionAction();
		}
	}
}
