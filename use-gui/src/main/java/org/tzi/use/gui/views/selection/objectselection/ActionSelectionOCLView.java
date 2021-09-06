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

package org.tzi.use.gui.views.selection.objectselection;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ViewFrame;
import org.tzi.use.uml.sys.MSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Jun Zhang
 * @author Jie Xu
 * @author Lars Hamann
 * @author Carsten Schlobohm
 */
@SuppressWarnings("serial")
public class ActionSelectionOCLView extends AbstractAction {

    private final DataHolder dataHolder;
    private final MSystem system;

    public ActionSelectionOCLView(final DataHolder dataHolder, final MSystem system) {
        super("With OCL...");
        this.dataHolder = dataHolder;
        this.system = system;
    }

    public void actionPerformed(ActionEvent e) {
        SelectionOCLView opv = new SelectionOCLView(MainWindow.instance(), system, dataHolder);
        ViewFrame f = new ViewFrame("Selection by OCL expression", opv, "ObjectProperties.gif");
        JComponent c = (JComponent) f.getContentPane();

        c.setLayout(new BorderLayout());
        c.add(opv, BorderLayout.CENTER);
        MainWindow.instance().addNewViewFrame(f);
        f.setSize(370, 250);
    }
}
