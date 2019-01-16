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

package org.tzi.use.gui.views.diagrams.behavior.sequencediagram;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.print.PageFormat;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.PrintableView;
import org.tzi.use.gui.views.View;
import org.tzi.use.gui.views.diagrams.behavior.shared.VisibleDataManager;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.events.Event;
import org.tzi.use.uml.sys.events.StatementExecutedEvent;

/**
 * A SequenceDiagramView shows a UML sequence diagramm of events.
 * 
 * @author Mark Richters
 * @author Antje Werner
 * @author Carsten Schlobohm
 */

@SuppressWarnings("serial")
public class SequenceDiagramView extends JPanel implements View, PrintableView,
		VisibleDataManager.VisibleDataObserver {

	private SequenceDiagram fSeqDia;

	private VisibleDataManager visibleDataManager;

	/**
	 * Creates a SequenceDiagramView
	 * @param system USE system which stores all information about the current state
	 * @param mainWindow parent view
	 * @param manager manages which data is currently visible or should be set hidden
	 * @return a new created SequenceDiagramView
	 */
	public static SequenceDiagramView createSequenceDiagramView(MSystem system,
												 MainWindow mainWindow,
												 VisibleDataManager manager) {
		SequenceDiagramView sequenceDiagram = new SequenceDiagramView(system, manager);
		sequenceDiagram.postConstruction(system, mainWindow);
		return sequenceDiagram;
	}

	/**
	 * Custom constructor
	 * @param system USE system which stores all information about the current state
	 * @param pVisibleDataManager manages which data is currently visible or should be set hidden
	 */
	private SequenceDiagramView(final MSystem system,
							   final VisibleDataManager pVisibleDataManager) {
		setDoubleBuffered(true);
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder());
		if (pVisibleDataManager == null) {
			this.visibleDataManager = VisibleDataManager.createVisibleDataManager(system);
			initVisibleDataManager();
		} else {
			this.visibleDataManager = pVisibleDataManager;
		}
	}

	/**
	 * Do here all initialisation or function calls
	 * which needs 'this' as param or relies on it in some manner.
	 * Use the constructor for all other initialisation.
	 * Especially register listener only in this method, so
	 * we can prevent multi-threading issues because of a not fully
	 * initialed SequenceDiagramView
	 */
	private void postConstruction(final MSystem system,
								  final MainWindow mainWindow) {
		fSeqDia = new SequenceDiagram(system, mainWindow, this, this.visibleDataManager);
		this.visibleDataManager.registerObserver(this);
		add(fSeqDia, BorderLayout.CENTER);
	}

	/**
	 * Sets the view bounds of the visible view of the SequenceDiagram which are
	 * saved in the fView-Attribute of the SequenceDiagram class.
	 * 
	 * @param bounds the new view bounds
	 */
	void setViewBounds(Rectangle bounds) {
		fSeqDia.setViewBounds(bounds);
	}

	/**
	 * Updates the current sequence diagram.
	 */
	public void update() {
		fSeqDia.update();
	}

	private void initVisibleDataManager() {
		visibleDataManager.getData().setAllEventTypesVisible(false);
	}

	/**
	 * Detaches the view from its model.
	 */
	public void detachModel() {
		visibleDataManager.unregisterObserver(this);
		visibleDataManager.secureTermination();
	}

	/**
	 * Prints the whole diagram.
	 * 
	 * @param pf The PageFormat-Object for the printing.
	 */
	public void printView(PageFormat pf) {
		fSeqDia.printDiagram(pf);
	}

	@Override
	public void export(Graphics2D g) {
		JComponent toExport = fSeqDia;

		boolean oldDb = toExport.isDoubleBuffered();
		toExport.setDoubleBuffered(false);
		toExport.paint(g);
		toExport.setDoubleBuffered(oldDb);
	}

	/**
	 * Prints the visible view of the sequence diagram.
	 * 
	 * @param pf The PageFormat-Object for the printing.
	 */
	public void printOnlyView(PageFormat pf) {
		fSeqDia.printView(pf);
	}

	@Override
	public float getContentHeight() {
		return fSeqDia.getPreferredSize().height;
	}

	@Override
	public float getContentWidth() {
		return fSeqDia.getPreferredSize().width;
	}

	@Override
	public void onObservableChanged() {
		fSeqDia.update();
		fSeqDia.repaint();
	}

	@Override
	public void onStatement(Event event) {}

	@Override
	public void onEventExecuted(StatementExecutedEvent event) {
		fSeqDia.update();
		fSeqDia.repaint();
	}

	void notifyDataManager() {
		visibleDataManager.notifyObservers(this);
	}
}
