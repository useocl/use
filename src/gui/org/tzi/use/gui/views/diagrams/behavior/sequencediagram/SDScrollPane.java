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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * A special ScrollPane for the sequence diagram.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Antje Werner
 */
@SuppressWarnings("serial")
public class SDScrollPane extends JScrollPane {

	/**
	 * The SequenceDiagramView which should be scrolled by this ScrollPane.
	 */
	private SequenceDiagramView sequenceDiagramView;

	/**
	 * Constructs a new SDScrollPane object.
	 * 
	 * @param comp the Component that should be scrolled
	 */
	public SDScrollPane(JComponent comp) {
		super(comp);
		// Double buffering prevents glimmering
		setDoubleBuffered(true);
		sequenceDiagramView = (SequenceDiagramView) comp;
		verticalScrollBar.setUnitIncrement(4);
		horizontalScrollBar.setUnitIncrement(4);

		Dimension preferredDimensionOfSDPane = getPreferredSize();

		setPreferredSize(preferredDimensionOfSDPane);

		// inform the SequenceDiagramView object about the new view bounds
		sequenceDiagramView.setViewBounds(new Rectangle(horizontalScrollBar.getValue(), verticalScrollBar.getValue(),
				(int) preferredDimensionOfSDPane.getWidth(), (int) preferredDimensionOfSDPane.getHeight()));

		// update sequence diagram
		// ((SequenceDiagramView) sequenceDiagramView).update();
		// set viewport of the scrollpane
		SDViewport viewport = new SDViewport();
		viewport.setSize(sequenceDiagramView.getSize());
		viewport.setView(sequenceDiagramView);
		setViewport(viewport);

	}

	/**
	 * Calculates the preferred size of the component.
	 * 
	 * @return the calculated preferred size
	 */
	public Dimension getPreferredSize() {
		Rectangle sequenceDiagramViewPortBorderBounds = getViewportBorderBounds();
		Dimension dimensionOfVerticalScrollBar = verticalScrollBar.getPreferredSize();
		Dimension dimensionOfHorizontalScrollBar = horizontalScrollBar.getPreferredSize();
		Dimension dimensionOfSequenceDiagramScrollPane = new Dimension(sequenceDiagramViewPortBorderBounds.width + dimensionOfVerticalScrollBar.width,
				sequenceDiagramViewPortBorderBounds.height + dimensionOfHorizontalScrollBar.height);
		return dimensionOfSequenceDiagramScrollPane;
	}

	/**
	 * A special Viewport for the sequence diagram.
	 * 
	 * @author Antje Werner
	 */
	private class SDViewport extends JViewport {

		/**
		 * Constructs a new SDViewport object.
		 */
		SDViewport() {
			super();
			// //prevents glimmering
			setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		}

		/**
		 * Actualize the SequenceDiagramView and calls the paintChildren-Method
		 * of the super-class JViewport.
		 */
		public void paintChildren(Graphics g) {
			// inform the SequenceDiagramView object about the new view bounds
			sequenceDiagramView.setViewBounds(new Rectangle(horizontalScrollBar.getValue(), verticalScrollBar.getValue(),
					getExtentSize().width, getExtentSize().height));
			// update sequence diagram
			sequenceDiagramView.update();
			super.paintChildren(g);
		}

	}

}
