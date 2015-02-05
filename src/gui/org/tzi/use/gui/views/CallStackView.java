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

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.Deque;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

import org.tzi.use.gui.util.PopupListener;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.events.OperationEnteredEvent;
import org.tzi.use.uml.sys.events.OperationExitedEvent;
import org.tzi.use.util.StringUtil;

import com.google.common.eventbus.Subscribe;

/** 
 * A CallStackView shows the stack of currently active operations.
 *
 * @author      Mark Richters 
 */
@SuppressWarnings("serial")
public class CallStackView extends JPanel implements View {
    private MSystem fSystem;
    private JList<String> fList;
    private DefaultListModel<String> fListModel;
    private JPopupMenu fPopupMenu; // context menu on right mouse click
    private boolean fShowCall = false;

    public CallStackView(MSystem system) {
        fSystem = system;

        setFont(Font.getFont("use.gui.userFont", getFont()));
        setLayout(new BorderLayout());
        fListModel = new DefaultListModel<String>();
        fList = new JList<String>(fListModel);
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

        fSystem.getEventBus().register(this);
        update();
    }

    private void update() {
        fListModel.clear();
        
        Deque<MOperationCall> callStack = fSystem.getCallStack();
        
        if (callStack.isEmpty()) {
        	fListModel.addElement("<empty>");
        } else {
        	
        	int current = 1;
        	for (MOperationCall oc : callStack) {
        		MOperation op = oc.getOperation();
        		
        		StringBuilder line = new StringBuilder();
        		line.append(current++);
        		line.append(". ");
        		if (fShowCall) {
        			line.append(oc.getSelf().name());
        			line.append(".");
        		} else {
        			line.append(op.cls().name());
        			line.append("::");
        		}
        		line.append(op.name());
        		line.append("(");
        		if (fShowCall) {
        			StringUtil.fmtSeq(
        					line, 
        					oc.getArgumentsAsNamesAndValues().values(),
        					",");
        		}
        		line.append(")");
        		fListModel.addElement(line.toString());
        	}	
        }
        
        repaint();
    }

    @Subscribe
    public void operationEntered(OperationEnteredEvent e) {
        update();
    }

    @Subscribe
    public void operationExited(OperationExitedEvent e) {
        update();
    }
    
    /**
     * Detaches the view from its model.
     */
    public void detachModel() {
        fSystem.getEventBus().unregister(this);
    }

}