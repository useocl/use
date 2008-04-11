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

package org.tzi.use.gui.views;

import java.awt.*;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;

import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.StateChangeEvent;

/** 
 * A CommandView shows the sequence of executed state manipulation
 * commands.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public class CommandView extends JPanel implements View {
    private MSystem fSystem;
    private JList fList;
    private DefaultListModel fListModel;

    public CommandView(MSystem system) {
        fSystem = system;

        setFont(Font.getFont("use.gui.userFont", getFont()));
        setLayout(new BorderLayout());
        fListModel = new DefaultListModel();
        fList = new JList(fListModel);
        add(fList, BorderLayout.CENTER);

        fSystem.addChangeListener(this);
        update();
    }

    private void update() {
        fListModel.clear();
        List cmds = fSystem.commands();
        if (cmds.isEmpty() ) {
            fListModel.addElement("<empty>");
        } else {
            int j = 1;
            Iterator cmdIter = cmds.iterator();
            while (cmdIter.hasNext() ) {
                MCmd cmd = (MCmd) cmdIter.next();
                String s = j++ + ". " + cmd.getUSEcmd();
                fListModel.addElement(s);
            }
        }
        fList.ensureIndexIsVisible(fListModel.size() - 1);
        repaint();
    }

    public void stateChanged(StateChangeEvent e) {
        update();
    }

    /**
     * Detaches the view from its model.
     */
    public void detachModel() {
        fSystem.removeChangeListener(this);
    }

}
