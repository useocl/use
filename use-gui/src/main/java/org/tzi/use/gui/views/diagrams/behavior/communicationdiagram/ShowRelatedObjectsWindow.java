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

import org.tzi.use.gui.util.GridBagHelper;
import org.tzi.use.gui.views.diagrams.behavior.shared.CancelButton;
import org.tzi.use.gui.views.diagrams.behavior.shared.OKButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Carsten Schlobohm
 */
public class ShowRelatedObjectsWindow extends JDialog implements ActionListener {

    public interface ShowRelatedObjectsDelegate {
        /**
         *
         * @param showAssociations show hidden links of associations if they are relevant
         * @param showAssociationClasses show hidden links of association classes if they are relevant
         * @param showObjectsFromAssociation show hidden objects of an visible links
         * @param showObjectsAsParam show hidden objects which are used as function param
         * @param searchCycles cycles
         */
        void showRelatedObjects(boolean showAssociations,
                                boolean showAssociationClasses,
                                boolean showObjectsFromAssociation,
                                boolean showObjectsAsParam,
                                int searchCycles);
    }

    private final ShowRelatedObjectsDelegate delegate;

    /**
     * Some panels.
     */
    private JPanel selectionPanel;

    private ShowRelatedObjectsPanel panelContainer;

    private Container container;

    public ShowRelatedObjectsWindow(ShowRelatedObjectsDelegate delegate) {
        setTitle("Show Related Objects...");
        this.delegate = delegate;
        setAlwaysOnTop(true);
        setSize(250, 120);
        setResizable(true);
        setMaximumSize(new Dimension(800, 400));
        setMinimumSize(new Dimension(250, 120));

        panelContainer = new ShowRelatedObjectsPanel(false, false, false, false, 1, "");

        container = getContentPane();
        container.setLayout(new BorderLayout(20, 10));

        selectionPanel = panelContainer.getPanel();

        container.add("Center", selectionPanel);
        // OK-/Cancel-Buttons
        JPanel buttons = WindowHelper.customJPanel(0);
        JButton ok = new OKButton("Ok");
        ok.addActionListener(this);
        buttons.add(ok);
        JButton cancel = new CancelButton("Cancel");
        cancel.addActionListener(this);
        buttons.add(cancel);
        container.add("South", buttons);
        pack();
    }

    /**
     * Shows the Window.
     */
    public void showWindow() {
        this.setLocation(300, 200);
        this.setVisible(true);
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Ok".equals(e.getActionCommand())) {
            delegate.showRelatedObjects(panelContainer.isShowAssociations(),
                    panelContainer.isShowAssociationClasses(),
                    panelContainer.isShowObjectsFromAssociation(),
                    panelContainer.isShowObjectsAsParam(),
                    panelContainer.searchCycles());
            dispose();
        }
        if ("Cancel".equals(e.getActionCommand())) {
            dispose();
        }
    }
}
