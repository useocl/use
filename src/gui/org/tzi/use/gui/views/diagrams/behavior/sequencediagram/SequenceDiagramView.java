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
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.events.tags.SystemStateChangedEvent;

import com.google.common.eventbus.Subscribe;

/**
 * A SequenceDiagramView shows a UML sequence diagramm of events.
 * 
 * @author Mark Richters
 * @author Antje Werner
 */

@SuppressWarnings("serial")
public class SequenceDiagramView extends JPanel implements View, PrintableView {

	private MSystem fSystem;

	private SequenceDiagram fSeqDiag;

	public SequenceDiagramView(MSystem system, MainWindow mainW) {
		setDoubleBuffered(true);
		fSystem = system;
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder());
		fSeqDiag = new SequenceDiagram(system, mainW);
		add(fSeqDiag, BorderLayout.CENTER);

		fSystem.getEventBus().register(this);
	}

	/**
	 * Sets the view bounds of the visible view of the SequenceDiagram which are
	 * saved in the fView-Attribute of the SequenceDiagram class.
	 * 
	 * @param bounds the new view bounds
	 */
	public void setViewBounds(Rectangle bounds) {
		fSeqDiag.setViewBounds(bounds);
	}

	/**
	 * Updates the current sequence diagram.
	 */
	public void update() {
		fSeqDiag.update();
	}

	/**
	 * Returns the SequenceDiagram-Object which is saved in fSeqDiag.
	 * 
	 * @return the SequenceDiagram-Object
	 */
	public SequenceDiagram getSeqDiag() {
		return fSeqDiag;
	}

	@Subscribe
	public void onStateChanged(SystemStateChangedEvent event) {
		// TODO: Handle event types to be able to update the diagram stepwise
		fSeqDiag.update();
	}

	/**
	 * Detaches the view from its model.
	 */
	public void detachModel() {
		fSystem.getEventBus().unregister(this);
	}

	/**
	 * Prints the whole diagram.
	 * 
	 * @param pf The PageFormat-Object for the printing.
	 */
	public void printView(PageFormat pf) {
		fSeqDiag.printDiagram(pf);
	}

	@Override
	public void export(Graphics2D g) {
		JComponent toExport = fSeqDiag;

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
		fSeqDiag.printView(pf);
	}

	@Override
	public float getContentHeight() {
		return fSeqDiag.getPreferredSize().height;
	}

	@Override
	public float getContentWidth() {
		return fSeqDiag.getPreferredSize().width;
	}

}
