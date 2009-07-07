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
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

import org.tzi.use.gui.util.PopupListener;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.StateChangeEvent;
import org.tzi.use.util.StringUtil;

/** 
 * A CallStackView shows the stack of currently active operations.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
@SuppressWarnings("serial")
public class CallStackView extends JPanel implements View {
    private MSystem fSystem;
    private JList fList;
    private DefaultListModel fListModel;
    private JPopupMenu fPopupMenu; // context menu on right mouse click
    private boolean fShowCall = false;

    public CallStackView(MSystem system) {
        fSystem = system;

        setFont(Font.getFont("use.gui.userFont", getFont()));
        setLayout(new BorderLayout());
        fListModel = new DefaultListModel();
        fList = new JList(fListModel);
        add(fList, BorderLayout.CENTER);

        // create the popup menu for options
        fPopupMenu = new JPopupMenu();
        ButtonGroup group = new ButtonGroup();

        final JRadioButtonMenuItem rbShowSignature =
            new JRadioButtonMenuItem("Show operation signature");
        rbShowSignature.setSelected(! fShowCall);
        rbShowSignature.setMnemonic(KeyEvent.VK_S);
        rbShowSignature.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent ev) {
                    fShowCall = ev.getStateChange() != ItemEvent.SELECTED;
                    update();
                }
            });
        group.add(rbShowSignature);
        fPopupMenu.add(rbShowSignature);
        final JRadioButtonMenuItem rbShowCall =
            new JRadioButtonMenuItem("Show operation call");
        rbShowSignature.setSelected(fShowCall);
        rbShowCall.setMnemonic(KeyEvent.VK_C);
        rbShowCall.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent ev) {
                    fShowCall = ev.getStateChange() == ItemEvent.SELECTED;
                    update();
                }
            });
        group.add(rbShowCall);
        fPopupMenu.add(rbShowCall);
        PopupListener pl = new PopupListener(fPopupMenu);
        fList.addMouseListener(pl);
        addMouseListener(pl);

        fSystem.addChangeListener(this);
        update();
    }

    private void update() {
        fListModel.clear();
        List<MOperationCall> callStack = fSystem.callStack();
        
        if (callStack.isEmpty() ) {
            fListModel.addElement("<empty>");
        } else {
            int j = 1;
            for (int i = callStack.size() - 1; i >= 0; i--) {
                MOperationCall opcall = callStack.get(i);
                MOperation op = opcall.operation();
                String s = j++ + ". " + 
                    ( fShowCall ? 
                      opcall.targetObject().name() + "." : 
                      op.cls().name() + "::" ) + 
                    op.name() + "(" + 
                    ( fShowCall ? StringUtil.fmtSeq(opcall.argValues(), ",") : "" ) +
                    ")";
                fListModel.addElement(s);
            }
        }
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