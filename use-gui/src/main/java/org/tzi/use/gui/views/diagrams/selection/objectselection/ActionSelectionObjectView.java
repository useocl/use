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

package org.tzi.use.gui.views.diagrams.selection.objectselection;
import org.tzi.use.gui.views.diagrams.framework.DataHolder;

import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import org.tzi.use.gui.views.diagrams.MainWindow;
import org.tzi.use.gui.views.diagrams.framework.ViewFrame;
import org.tzi.use.gui.views.diagrams.framework.DiagramType;
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
public class ActionSelectionObjectView extends AbstractAction {

    private final DataHolder dataHolder;
    private final MSystem system;

    public ActionSelectionObjectView(final DataHolder dataHolder, final MSystem system) {
        super("With view...");
        this.dataHolder = dataHolder;
        this.system = system;
    }

    public void actionPerformed(ActionEvent e) {
        SelectionObjectView opv = new SelectionObjectView(org.tzi.use.gui.views.diagrams.framework.IMainWindowServices.INSTANCE.get(), system, dataHolder);
        ViewFrame f = new ViewFrame("Select objects", opv, "ObjectProperties.gif");
        JComponent c = (JComponent) f.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(opv, BorderLayout.CENTER);

        if (org.tzi.use.gui.views.diagrams.framework.IMainWindowServices.JAVA_FX_MODE.get()){
            Platform.runLater(() -> {
                SwingNode swingNode = new SwingNode();
                swingNode.setContent(opv);
                swingNode.setCache(false);

                org.tzi.use.gui.views.diagrams.IFXWindowHost.INSTANCE.get().createNewWindow("Select objects",swingNode, DiagramType.SELECTED_OBJECT_VIEW);
            });
        } else {
            org.tzi.use.gui.views.diagrams.framework.IMainWindowServices.INSTANCE.get().addNewViewFrame(f);
            f.setSize(530, 230);
        }
    }
}
