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
import java.util.List;
import java.util.Stack;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.events.StatementExecutedEvent;
import org.tzi.use.uml.sys.soil.MEnterOperationStatement;
import org.tzi.use.uml.sys.soil.MExitOperationStatement;
import org.tzi.use.uml.sys.soil.MStatement;

import com.google.common.eventbus.Subscribe;

/** 
 * A CommandView shows the sequence of executed state manipulation
 * commands.
 *  
 * @author      Mark Richters 
 */
@SuppressWarnings("serial")
public class CommandView extends JPanel implements View {
    private MSystem fSystem;
    private JList<String> fList;
    private DefaultListModel<String> fListModel;

    public CommandView(MSystem system) {
        fSystem = system;

        setFont(Font.getFont("use.gui.userFont", getFont()));
        setLayout(new BorderLayout());
        fListModel = new DefaultListModel<String>();
        fList = new JList<String>(fListModel);
        add(fList, BorderLayout.CENTER);

        update();
        
        fSystem.getEventBus().register(this);
    }

    private void update() {
        fListModel.clear();
        
        List<MStatement> evaluatedStatements = fSystem.getEvaluatedStatements();
        int numEvaluatedStatements = evaluatedStatements.size();
        
        if (numEvaluatedStatements == 0) {
        	fListModel.addElement("<empty>");
        } else {
        	Stack<Integer> numbering = new Stack<>();
        	Stack<String> prefixes = new Stack<>();
        	
        	numbering.push(Integer.valueOf(0));
        	prefixes.push("");
        	
        	String entry;
        	String prefix;
        	MStatement statement;
        	
        	for (int i = 0; i < numEvaluatedStatements; ++i) {
        		statement = evaluatedStatements.get(i);
        		int number = numbering.pop();
        		numbering.push(++number);
        		
        		prefix = prefixes.peek() + number + ".";
        		entry = prefix + " " + statement.getShellCommand();
        		
        		if (statement instanceof MEnterOperationStatement) {
        			numbering.push(0);
        			prefixes.push(prefix);
        		} else if (statement instanceof MExitOperationStatement) {
        			numbering.pop();
        			prefixes.pop();
        		}
        		
        		fListModel.addElement(entry);
        	}
        }

        fList.ensureIndexIsVisible(fListModel.size() - 1);
        repaint();
    }

    @Subscribe
    public void onStatementExecuted(StatementExecutedEvent e) {
        update();
    }

    /**
     * Detaches the view from its model.
     */
    public void detachModel() {
        fSystem.getEventBus().unregister(this);
    }

}
