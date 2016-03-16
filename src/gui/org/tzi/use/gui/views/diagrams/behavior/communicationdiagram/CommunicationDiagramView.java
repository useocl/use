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

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.PrintableView;
import org.tzi.use.gui.views.View;
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

import com.google.common.eventbus.Subscribe;

/**
 * A Panel for displaying the communication diagrams
 * 
 * @author Quang Dung Nguyen
 * 
 */
@SuppressWarnings("serial")
public class CommunicationDiagramView extends JPanel implements View, PrintableView {

	private final MSystem system;
	private final MainWindow mainWindow;

	private CommunicationDiagram comDia;

	public CommunicationDiagramView(MainWindow mainWindow, MSystem system) {
		this.mainWindow = mainWindow;
		this.system = system;

		system.getEventBus().register(this);

		setLayout(new BorderLayout());
		this.setFocusable(true);
		initDiagram(false, null);
	}

	void initDiagram(boolean loadDefaultLayout, CommunicationDiagramOptions opt) {
		if (opt == null) {
			comDia = new CommunicationDiagram(this, mainWindow.logWriter());
		} else {
			comDia = new CommunicationDiagram(this, mainWindow.logWriter(), opt);
		}

		comDia.setStatusBar(mainWindow.statusBar());
		this.removeAll();
		add(new JScrollPane(comDia));
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
			if (event instanceof ObjectCreatedEvent) {
				comDia.addObjectCreatedEvent((ObjectCreatedEvent) event);
			}

			if (event instanceof ObjectDestroyedEvent) {
				comDia.addObjectDestroyedEvent((ObjectDestroyedEvent) event);
			}

			if (event instanceof LinkInsertedEvent) {
				comDia.addLinkInsertedEvent((LinkInsertedEvent) event);
			}

			if (event instanceof LinkDeletedEvent) {
				comDia.addLinkDeletedEvent((LinkDeletedEvent) event);
			}

			if (event instanceof AttributeAssignedEvent) {
				comDia.addAttributeAssignedEvent((AttributeAssignedEvent) event);
			}

			if (event instanceof OperationEnteredEvent) {
				comDia.addOperationEnteredEvent((OperationEnteredEvent) event);
			}

			if (event instanceof OperationExitedEvent) {
				comDia.addOperationExitedEvent((OperationExitedEvent) event);
			}
		}

		comDia.initialize();
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
		system.getEventBus().unregister(this);
	}

	@Subscribe
	public void onObjectCreated(ObjectCreatedEvent e) {
		comDia.addObjectCreatedEvent(e);
		comDia.invalidateContent(true);
	}

	@Subscribe
	public void onObjectDeleted(ObjectDestroyedEvent e) {
		comDia.addObjectDestroyedEvent(e);
		comDia.invalidateContent(true);
	}

	@Subscribe
	public void onLinkCreated(LinkInsertedEvent e) {
		comDia.addLinkInsertedEvent(e);
		comDia.invalidateContent(true);
	}

	@Subscribe
	public void onLinkDeleted(LinkDeletedEvent e) {
		comDia.addLinkDeletedEvent(e);
		comDia.invalidateContent(true);
	}

	@Subscribe
	public void onAttributeAssigned(AttributeAssignedEvent e) {
		comDia.addAttributeAssignedEvent(e);
		comDia.invalidateContent(true);
	}

	@Subscribe
	public void onOperationEntered(OperationEnteredEvent e) {
		comDia.addOperationEnteredEvent(e);
		comDia.invalidateContent(true);
	}

	@Subscribe
	public void onOperationExited(OperationExitedEvent e) {
		comDia.addOperationExitedEvent(e);
		comDia.invalidateContent(true);
	}

	public MSystem system() {
		return system;
	}

	@Subscribe
	public void undone(StatementExecutedEvent e) {
		if (e.getContext() == EventContext.UNDO) {
			comDia.onClosing();
			initDiagram(true, null);
		}
	}
}
