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
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Antje Werner
 * 
 * A window for choosing the commands to show in the sequence diagram. By
 * default only operation calls are diagramed,but it is also possible to diagram
 * create-, destroy-, insert-, delete- and/or set-commands.
 */
@SuppressWarnings("serial")
public class CmdChooseWindow extends JDialog implements ActionListener {
    /**
     * A checkbox which contains the possible constructions (create, destroy,
     * set, insert, delete).
     */
    JCheckBox fCb[] = new JCheckBox[5];

    /**
     * The container for this Window.
     */
    Container fC;

    /**
     * The sequence diagram from which this window is called.
     */
    SequenceDiagram fSeqDiag;

    /**
     * Constructs an new CmdChooseWindow.
     * 
     */
    public CmdChooseWindow(SequenceDiagram seqDiag) {
        super(seqDiag.getMainWindow(), true);
        setTitle("Choose Commands...");
        fSeqDiag = seqDiag;
        setModal(true);
        fC = getContentPane();
        fC.setLayout(new BorderLayout(20, 10));

        JLabel label1 = new JLabel("");
        JLabel label2 = new JLabel("");
        JLabel label3 = new JLabel("");
        JPanel checkBox = new JPanel();
        checkBox.setLayout(new GridLayout(3, 2, 20, 0));

        fCb[0] = new JCheckBox("Create", fSeqDiag.getProperties().showCreate());
        fCb[1] = new JCheckBox("Destroy", fSeqDiag.getProperties()
                .showDestroy());
        fCb[2] = new JCheckBox("Insert", fSeqDiag.getProperties().showInsert());
        fCb[3] = new JCheckBox("Delete", fSeqDiag.getProperties().showDelete());
        fCb[4] = new JCheckBox("Set", fSeqDiag.getProperties().showSet());

        for (int i = 0; i < fCb.length; i++) {
            checkBox.add(fCb[i]);
        }

        JPanel buttons = new JPanel();
        JButton ok = new OKButton("Ok");
        ok.addActionListener(this);
        buttons.add(ok);
        JButton cancel = new CancelButton("Cancel");
        cancel.addActionListener(this);
        buttons.add(cancel);
        fC.add("East", label1);
        fC.add("West", label2);
        fC.add("North", label3);
        fC.add("Center", checkBox);
        fC.add("South", buttons);
        pack();
    }

    /**
     * Shows the Window.
     */
    public void showWindow() {
        CmdChooseWindow cmdChooseW = new CmdChooseWindow(fSeqDiag);
        cmdChooseW.setSize(200, 150);
        cmdChooseW.setLocation(300, 200);
        cmdChooseW.setVisible(true);
    }

    /**
     * Analyses the entries made in the window.
     */
    public void actionPerformed(ActionEvent e) {
        if ("Ok".equals(e.getActionCommand())) {
            fSeqDiag.getProperties().showCreate(fCb[0].isSelected());
            fSeqDiag.getProperties().showDestroy(fCb[1].isSelected());
            fSeqDiag.getProperties().showInsert(fCb[2].isSelected());
            fSeqDiag.getProperties().showDelete(fCb[3].isSelected());
            fSeqDiag.getProperties().showSet(fCb[4].isSelected());
            fSeqDiag.restoreAllValues();
            dispose();
        }
        if ("Cancel".equals(e.getActionCommand())) {
            dispose();
        }

    }

}
