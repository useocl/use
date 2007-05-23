/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.gui.views.seqDiag;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import org.tzi.use.util.cmd.CommandFailedException;

/**
 * A special ScrollPane for the sequence diagram.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Antje Werner
 */
public class SDScrollPane extends JScrollPane {

    /**
     * The horizontal ScrollBar.
     */
    JScrollBar fHorizSB;

    /**
     * The vertical ScrollBar.
     */
    JScrollBar fVertSB;

    /**
     * The SequenceDiagramView which should be scrolled by this ScrollPane.
     */
    SequenceDiagramView fComp;

    /**
     * Constructs a new SDScrollPane object.
     * 
     * @param comp
     *            the Component that should be scrolled
     */
    public SDScrollPane(JComponent comp) {
        super(comp);
        // Double buffering prevents glimmering
        setDoubleBuffered(true);
        fComp = (SequenceDiagramView) comp;
        fHorizSB = getHorizontalScrollBar();
        fVertSB = getVerticalScrollBar();
        fVertSB.setUnitIncrement(4);
        fHorizSB.setUnitIncrement(4);

        Dimension vDim = getPreferredSize();

        int x = fHorizSB.getValue();
        int y = fVertSB.getValue();
        setPreferredSize(vDim);
        // inform the SequenceDiagramView object about the new view bounds
        ((SequenceDiagramView) fComp).setViewBounds(new Rectangle(fHorizSB
                .getValue(), fVertSB.getValue(), (int) vDim.getWidth(),
                (int) vDim.getHeight()));
        // update sequence diagram
        try {
            ((SequenceDiagramView) fComp).update();
        } catch (CommandFailedException cfe) {
            // ignored
        }
        // set viewport of the scrollpane
        SDViewport viewport = new SDViewport();
        viewport.setSize(fComp.getSize());
        viewport.setView(fComp);
        setViewport(viewport);
    }

    /**
     * Calculates the preferred size of the component.
     * 
     * @return the calculated preferred size
     */
    public Dimension getPreferredSize() {
        Rectangle compDim = getViewportBorderBounds();
        Dimension vertSBDim = fVertSB.getPreferredSize();
        Dimension horizSBDim = fHorizSB.getPreferredSize();
        Dimension dim = new Dimension(compDim.width + vertSBDim.width,
                compDim.height + horizSBDim.height);
        return dim;
    }

    /**
     * A special Viewport for the sequence diagram.
     * 
     * @author Antje Werner
     */
    class SDViewport extends JViewport {

        /**
         * The view panel.
         */
        JPanel fView;

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
            ((SequenceDiagramView) fComp).setViewBounds(new Rectangle(
                    (int) fHorizSB.getValue(), (int) fVertSB.getValue(),
                    getExtentSize().width, getExtentSize().height));
            // update sequence diagram
            try {
                ((SequenceDiagramView) fComp).update();
            } catch (CommandFailedException cfe) {
                // ignored
            }
            super.paintChildren(g);
        }

    }

}