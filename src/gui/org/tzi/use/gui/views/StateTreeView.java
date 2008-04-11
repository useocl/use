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

import java.util.Iterator;
import java.util.Map;
import javax.swing.*;
import javax.swing.tree.*;

import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.StateChangeEvent;

/** 
 * A StateBrowser provides a tree view of objects and links in the
 * current system state.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public class StateTreeView extends JTree implements View {
    private MSystem fSystem;

    public StateTreeView(MSystem system) {
        fSystem = system;

        // create the nodes.
        setTreeModel();

        // create a tree that allows one selection at a time.
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        putClientProperty("JTree.lineStyle", "Angled");
        fSystem.addChangeListener(this);
    }

//    private void addChildNodes(DefaultMutableTreeNode parent, Iterator it) {
//        DefaultMutableTreeNode child;
//        while (it.hasNext() ) {
//            child = new DefaultMutableTreeNode(it.next());
//            parent.add(child);
//        }
//    }

    private void setTreeModel() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(fSystem.model().name());

        MSystemState systemState = fSystem.state();
        DefaultMutableTreeNode category = null;
        category = new DefaultMutableTreeNode("Objects");
        top.add(category);
        Iterator classIterator = fSystem.model().classes().iterator();
        while (classIterator.hasNext() ) {
            MClass cls = (MClass) classIterator.next();
            DefaultMutableTreeNode classNode = new DefaultMutableTreeNode(cls);
            category.add(classNode);
            Iterator objectIterator = systemState.objectsOfClass(cls).iterator();
            while (objectIterator.hasNext() ) {
                MObject obj = (MObject) objectIterator.next();
                DefaultMutableTreeNode objNode = new DefaultMutableTreeNode(obj);
                classNode.add(objNode);

                MObjectState objState = obj.state(fSystem.state());
                Map attributeValueMap = objState.attributeValueMap();
                Iterator attrIterator = attributeValueMap.entrySet().iterator();
                while (attrIterator.hasNext() ) {
                    Map.Entry entry = (Map.Entry) attrIterator.next();
                    MAttribute attr = (MAttribute) entry.getKey();
                    Value v = (Value) entry.getValue();
                    DefaultMutableTreeNode attrNode = 
                        new DefaultMutableTreeNode(attr + " = " + v);
                    objNode.add(attrNode);
                }
            }
        }






        //          category = new DefaultMutableTreeNode("Associations");
        //          top.add(category);
        //      addChildNodes(category, fModel.associations().iterator());

        //          category = new DefaultMutableTreeNode("Constraints");
        //          top.add(category);
        //      addChildNodes(category, fModel.classInvariants().iterator());
        DefaultTreeModel treeModel = new DefaultTreeModel(top);
        setModel(treeModel);
    }

    public void stateChanged(StateChangeEvent e) {
        setTreeModel();
    }

    /**
     * Detaches the view from its model.
     */
    public void detachModel() {
        fSystem.removeChangeListener(this);
    }

}
