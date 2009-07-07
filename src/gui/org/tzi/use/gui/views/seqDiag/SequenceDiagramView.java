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

// $Id$

package org.tzi.use.gui.views.seqDiag;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.print.PageFormat;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.views.PrintableView;
import org.tzi.use.gui.views.View;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.StateChangeEvent;
import org.tzi.use.util.cmd.CommandFailedException;

/**
 * A SequenceDiagramView shows a UML sequence diagramm of events.
 * 
 * @version $ProjectVersion: 0.393 $
 * @author Mark Richters, Antje Werner
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

        fSystem.addChangeListener(this);
    }

    /**
     * Sets the view bounds of the visible view of the SequenceDiagram which are
     * saved in the fView-Attribute of the SequenceDiagram class.
     * 
     * @param bounds
     *            the new view bounds
     */
    public void setViewBounds(Rectangle bounds) {
        fSeqDiag.setViewBounds(bounds);
    }

    /**
     * Updates the current sequence diagram.
     * 
     * @throws CommandFailedException
     */
    public void update() throws CommandFailedException {
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

    public void stateChanged(StateChangeEvent e) {
        try {
            fSeqDiag.update();
        } catch (CommandFailedException cnfe) {
            System.out.println(cnfe);
        }
    }

    /**
     * Detaches the view from its model.
     */
    public void detachModel() {
        fSystem.removeChangeListener(this);
    }

    /**
     * Prints the whole diagram.
     * 
     * @param pf
     *            The PageFormat-Object for the printing.
     */
    public void printView(PageFormat pf) {
        fSeqDiag.printDiagram(pf);
    }

    /**
     * Prints the visible view of the sequence diagram.
     * 
     * @param pf
     *            The PageFormat-Object for the printing.
     */
    public void printOnlyView(PageFormat pf) {
        fSeqDiag.printView(pf);
    }

}
