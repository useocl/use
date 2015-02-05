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

import java.util.Map;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.events.StatementExecutedEvent;

import com.google.common.eventbus.Subscribe;

/** 
 * A StateBrowser provides a tree view of objects and links in the
 * current system state.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
@SuppressWarnings("serial")
public class StateTreeView extends JTree implements View {
    private MSystem fSystem;

    public StateTreeView(MSystem system) {
        fSystem = system;

        // create the nodes.
        setTreeModel();

        // create a tree that allows one selection at a time.
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        putClientProperty("JTree.lineStyle", "Angled");
        fSystem.getEventBus().register(this);
    }

    private void setTreeModel() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(fSystem.model().name());

        MSystemState systemState = fSystem.state();
        DefaultMutableTreeNode category = null;
        category = new DefaultMutableTreeNode("Objects");
        top.add(category);
        
        for (MClass cls : fSystem.model().classes()) {
            DefaultMutableTreeNode classNode = new DefaultMutableTreeNode(cls);
            category.add(classNode);
            
            for (MObject obj : systemState.objectsOfClass(cls)) {
                DefaultMutableTreeNode objNode = new DefaultMutableTreeNode(obj);
                classNode.add(objNode);

                MObjectState objState = obj.state(fSystem.state());
                Map<MAttribute, Value> attributeValueMap = objState.attributeValueMap();

                for (Map.Entry<MAttribute, Value> entry : attributeValueMap.entrySet()) {
                    MAttribute attr = entry.getKey();
                    Value v = entry.getValue();
                    DefaultMutableTreeNode attrNode = 
                        new DefaultMutableTreeNode(attr + " = " + v);
                    objNode.add(attrNode);
                }
            }
        }
        
        DefaultTreeModel treeModel = new DefaultTreeModel(top);
        setModel(treeModel);
    }

    @Subscribe
    public void onStatementExecuted(StatementExecutedEvent e) {
    	setTreeModel();
    }

    /**
     * Detaches the view from its model.
     */
    public void detachModel() {
        fSystem.getEventBus().unregister(this);
    }

}
