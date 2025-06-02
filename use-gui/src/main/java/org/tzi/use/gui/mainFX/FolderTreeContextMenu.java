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

package org.tzi.use.gui.mainFX;


import javafx.scene.control.*;
import org.tzi.use.main.Session;
import org.tzi.use.main.runtime.IRuntime;

import java.util.ArrayList;

/**
 * Creates a context menu for sorting elements in the model browser tree (JavaFX version).
 * Supports sorting of classes, associations, invariants, and conditions.
 * Also preserves expanded tree nodes after sorting.
 *
 * @author Akif Aydin
 */
@SuppressWarnings("serial")
public class FolderTreeContextMenu {

    private final Session session;
    private final IRuntime pluginRuntime;

    /**
     * Constructor initializes the context menu with session and runtime info.
     */
    public FolderTreeContextMenu(Session session, IRuntime pluginRuntime) {
        this.session = session;
        this.pluginRuntime = pluginRuntime;
    }

    /**
     * Builds and returns the right-click context menu with sorting options.
     * Sorting can be switched between alphabetical and USE file order.
     * Also refreshes the model browser and restores the expanded tree state.
     */
    public ContextMenu createContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        Menu sortClasses = new Menu("sort classes");
        Menu sortAssociations = new Menu("sort associations");
        Menu sortInvariant = new Menu("sort invariants");
        Menu sortPrePostconditions = new Menu("sort pre-/postconditions");

        CheckMenuItem inAlphabeticOrderClasses = new CheckMenuItem("in alphabetic order");
        CheckMenuItem inUseFileOrderClasses = new CheckMenuItem("in USE file order");
        inUseFileOrderClasses.setSelected(true);

        inAlphabeticOrderClasses.setOnAction(event -> {
            if (inAlphabeticOrderClasses.isSelected()) {
                inUseFileOrderClasses.setSelected(false); //Unchecks other CheckMenuItem
                ArrayList<String> lstofExpanded = saveCurrentState();
                ModelBrowserSorting.getInstance().setClsOrder(1);
                new ModelBrowser(session.system().model(), pluginRuntime);
                stateChanged(lstofExpanded);
            }
        });

        inUseFileOrderClasses.setOnAction(event -> {
            if (inUseFileOrderClasses.isSelected()) {
                inAlphabeticOrderClasses.setSelected(false); //Unchecks other CheckMenuItem
                ArrayList<String> lstofExpanded = saveCurrentState();
                ModelBrowserSorting.getInstance().setClsOrder(2);
                new ModelBrowser(session.system().model(), pluginRuntime);
                stateChanged(lstofExpanded);
            }
        });

        Menu sortClassAttributes = new Menu("sort attributes");
        Menu sortClassOperations = new Menu("sort operations");
        Menu sortClassStateMachines = new Menu("sort state machines");

        CheckMenuItem srtClsSrtAttrAlph = new CheckMenuItem("in alphabetic order");
        CheckMenuItem srtClsSrtAttrUse = new CheckMenuItem("in USE file order");
        srtClsSrtAttrUse.setSelected(true);

        srtClsSrtAttrAlph.setOnAction(event -> {
            if (srtClsSrtAttrAlph.isSelected()) {
                srtClsSrtAttrUse.setSelected(false); //Unchecks other CheckMenuItem
                ArrayList<String> lstofExpanded = saveCurrentState();
                ModelBrowserSorting.getInstance().setAttrOrder(1);
                new ModelBrowser(session.system().model(), pluginRuntime);
                stateChanged(lstofExpanded);
            }
        });

        srtClsSrtAttrUse.setOnAction(event -> {
            if (srtClsSrtAttrUse.isSelected()) {
                srtClsSrtAttrAlph.setSelected(false); //Unchecks other CheckMenuItem
                ArrayList<String> lstofExpanded = saveCurrentState();
                ModelBrowserSorting.getInstance().setAttrOrder(2);
                new ModelBrowser(session.system().model(), pluginRuntime);
                stateChanged(lstofExpanded);
            }
        });

        CheckMenuItem srtClsSrtOperAlph = new CheckMenuItem("in alphabetic order");
        CheckMenuItem srtClsSrtOperUse = new CheckMenuItem("in USE file order");
        srtClsSrtOperUse.setSelected(true);

        srtClsSrtOperAlph.setOnAction(event -> {
            if (srtClsSrtOperAlph.isSelected()) {
                srtClsSrtOperUse.setSelected(false); //Unchecks other CheckMenuItem
                ArrayList<String> lstofExpanded = saveCurrentState();
                ModelBrowserSorting.getInstance().setOprOrder(1);
                new ModelBrowser(session.system().model(), pluginRuntime);
                stateChanged(lstofExpanded);
            }
        });

        srtClsSrtOperUse.setOnAction(event -> {
            if (srtClsSrtOperUse.isSelected()) {
                srtClsSrtOperAlph.setSelected(false); //Unchecks other CheckMenuItem
                ArrayList<String> lstofExpanded = saveCurrentState();
                ModelBrowserSorting.getInstance().setOprOrder(2);
                new ModelBrowser(session.system().model(), pluginRuntime);
                stateChanged(lstofExpanded);
            }
        });

        CheckMenuItem srtClsSrtSmAlph = new CheckMenuItem("in alphabetic order");
        CheckMenuItem srtClsSrtSmUse = new CheckMenuItem("in USE file order");
        srtClsSrtSmUse.setSelected(true);

        srtClsSrtSmAlph.setOnAction(event -> {
            if (srtClsSrtSmAlph.isSelected()) {
                srtClsSrtSmUse.setSelected(false); //Unchecks other CheckMenuItem
                ArrayList<String> lstofExpanded = saveCurrentState();
                ModelBrowserSorting.getInstance().setStateMachineOrder(ModelBrowserSorting.StateMachineOrder.ALPHABETIC);
                new ModelBrowser(session.system().model(), pluginRuntime);
                stateChanged(lstofExpanded);
            }
        });

        srtClsSrtSmUse.setOnAction(event -> {
            if (srtClsSrtSmUse.isSelected()) {
                srtClsSrtSmAlph.setSelected(false); //Unchecks other CheckMenuItem
                ArrayList<String> lstofExpanded = saveCurrentState();
                ModelBrowserSorting.getInstance().setStateMachineOrder(ModelBrowserSorting.StateMachineOrder.USE);
                new ModelBrowser(session.system().model(), pluginRuntime);
                stateChanged(lstofExpanded);
            }
        });

        sortClassAttributes.getItems().addAll(srtClsSrtAttrAlph, srtClsSrtAttrUse);
        sortClassOperations.getItems().addAll(srtClsSrtOperAlph, srtClsSrtOperUse);
        sortClassStateMachines.getItems().addAll(srtClsSrtSmAlph, srtClsSrtSmUse);

        sortClasses.getItems().addAll(inAlphabeticOrderClasses, inUseFileOrderClasses, sortClassAttributes, sortClassOperations, sortClassStateMachines);

        CheckMenuItem srtAsoAlph = new CheckMenuItem("in alphabetic order");
        CheckMenuItem srtAsoUse = new CheckMenuItem("in USE file order");
        srtAsoUse.setSelected(true);

        srtAsoAlph.setOnAction(event -> {
            if (srtAsoAlph.isSelected()) {
                srtAsoUse.setSelected(false); //Unchecks other CheckMenuItem
                ArrayList<String> lstofExpanded = saveCurrentState();
                ModelBrowserSorting.getInstance().setAssocOrder(1);
                new ModelBrowser(session.system().model(), pluginRuntime);
                stateChanged(lstofExpanded);
            }
        });

        srtAsoUse.setOnAction(event -> {
            if (srtAsoUse.isSelected()) {
                srtAsoAlph.setSelected(false); //Unchecks other CheckMenuItem
                ArrayList<String> lstofExpanded = saveCurrentState();
                ModelBrowserSorting.getInstance().setAssocOrder(2);
                new ModelBrowser(session.system().model(), pluginRuntime);
                stateChanged(lstofExpanded);
            }
        });

        sortAssociations.getItems().addAll(srtAsoAlph, srtAsoUse);

        CheckMenuItem srtInvAlphClsNameFirst = new CheckMenuItem("in alphabetic order - class name first");
        CheckMenuItem srtInvAlphInvNameFirst = new CheckMenuItem("in alphabetic order - invariant name first");
        CheckMenuItem srtInvUse = new CheckMenuItem("in USE file order");
        srtInvUse.setSelected(true);

        srtInvAlphClsNameFirst.setOnAction(event -> {
            if (srtInvAlphClsNameFirst.isSelected()) {
                srtInvAlphInvNameFirst.setSelected(false); //Unchecks other CheckMenuItem
                srtInvUse.setSelected(false); //Unchecks other CheckMenuItem
                ArrayList<String> lstofExpanded = saveCurrentState();
                ModelBrowserSorting.getInstance().setInvOrder(1);
                new ModelBrowser(session.system().model(), pluginRuntime);
                stateChanged(lstofExpanded);
            }
        });

        srtInvAlphInvNameFirst.setOnAction(event -> {
            if (srtInvAlphInvNameFirst.isSelected()) {
                srtInvAlphClsNameFirst.setSelected(false); //Unchecks other CheckMenuItem
                srtInvUse.setSelected(false); //Unchecks other CheckMenuItem
                ArrayList<String> lstofExpanded = saveCurrentState();
                ModelBrowserSorting.getInstance().setInvOrder(5);
                new ModelBrowser(session.system().model(), pluginRuntime);
                stateChanged(lstofExpanded);
            }
        });

        srtInvUse.setOnAction(event -> {
            if (srtInvUse.isSelected()) {
                srtInvAlphClsNameFirst.setSelected(false); //Unchecks other CheckMenuItem
                srtInvAlphInvNameFirst.setSelected(false); //Unchecks other CheckMenuItem
                ArrayList<String> lstofExpanded = saveCurrentState();
                ModelBrowserSorting.getInstance().setInvOrder(2);
                new ModelBrowser(session.system().model(), pluginRuntime);
                stateChanged(lstofExpanded);
            }
        });

        sortInvariant.getItems().addAll(srtInvAlphClsNameFirst, srtInvAlphInvNameFirst, srtInvUse);

        CheckMenuItem srtPPCAlphOpNameFirst = new CheckMenuItem("in alphabetic order - class name first");
        CheckMenuItem srtPPCAlphCondNameFirst = new CheckMenuItem("in alphabetic order - invariant name first");
        CheckMenuItem srtPPCAlphPreCondFirst = new CheckMenuItem("in alphabetic order - invariant name first");
        CheckMenuItem srtPPCUse = new CheckMenuItem("in USE file order");
        srtPPCUse.setSelected(true);

        srtPPCAlphOpNameFirst.setOnAction(event -> {
            if (srtPPCAlphOpNameFirst.isSelected()) {
                srtPPCAlphCondNameFirst.setSelected(false); //Unchecks other CheckMenuItem
                srtPPCAlphPreCondFirst.setSelected(false); //Unchecks other CheckMenuItem
                srtPPCUse.setSelected(false);
                ArrayList<String> lstofExpanded = saveCurrentState();
                ModelBrowserSorting.getInstance().setCondOrder(7);
                new ModelBrowser(session.system().model(), pluginRuntime);
                stateChanged(lstofExpanded);
            }
        });

        srtPPCAlphCondNameFirst.setOnAction(event -> {
            if (srtPPCAlphCondNameFirst.isSelected()) {
                srtPPCAlphOpNameFirst.setSelected(false); //Unchecks other CheckMenuItem
                srtPPCAlphPreCondFirst.setSelected(false); //Unchecks other CheckMenuItem
                srtPPCUse.setSelected(false);
                ArrayList<String> lstofExpanded = saveCurrentState();
                ModelBrowserSorting.getInstance().setCondOrder(8);
                new ModelBrowser(session.system().model(), pluginRuntime);
                stateChanged(lstofExpanded);
            }
        });

        srtPPCAlphPreCondFirst.setOnAction(event -> {
            if (srtPPCAlphPreCondFirst.isSelected()) {
                srtPPCAlphCondNameFirst.setSelected(false); //Unchecks other CheckMenuItem
                srtPPCAlphOpNameFirst.setSelected(false); //Unchecks other CheckMenuItem
                srtPPCUse.setSelected(false);
                ArrayList<String> lstofExpanded = saveCurrentState();
                ModelBrowserSorting.getInstance().setCondOrder(9);
                new ModelBrowser(session.system().model(), pluginRuntime);
                stateChanged(lstofExpanded);
            }
        });

        srtPPCUse.setOnAction(event -> {
            if (srtPPCUse.isSelected()) {
                srtPPCAlphCondNameFirst.setSelected(false); //Unchecks other CheckMenuItem
                srtPPCAlphPreCondFirst.setSelected(false); //Unchecks other CheckMenuItem
                srtPPCAlphOpNameFirst.setSelected(false);
                ArrayList<String> lstofExpanded = saveCurrentState();
                ModelBrowserSorting.getInstance().setCondOrder(10);
                new ModelBrowser(session.system().model(), pluginRuntime);
                stateChanged(lstofExpanded);
            }
        });

        sortPrePostconditions.getItems().addAll(srtPPCAlphOpNameFirst, srtPPCAlphCondNameFirst, srtPPCAlphPreCondFirst, srtPPCUse);

        contextMenu.getItems().addAll(sortClasses, sortAssociations, sortInvariant, sortPrePostconditions);

        return contextMenu;
    }

    /**
     * Saves which items in the TreeView are currently expanded.
     * This helps preserve user view when reloading the tree.
     */
    public ArrayList<String> saveCurrentState() {
        ArrayList<String> lstofExpanded = new ArrayList<>();

        if (MainWindow.getInstance().getFolderTreeView().getRoot().isExpanded()) {
            lstofExpanded.add(MainWindow.getInstance().getFolderTreeView().getRoot().getValue());
        }
        for (TreeItem<String> child : MainWindow.getInstance().getFolderTreeView().getRoot().getChildren()) {
            if (child.isExpanded()) {
                lstofExpanded.add(child.getValue());
            }
        }

        return lstofExpanded;
    }

    /**
     * If an event occurs the trees state will be restored
     */
    public void stateChanged(ArrayList<String> lstofExpanded) {

        // check if the root was Expanded, then set Expanded True
        if (lstofExpanded.get(0).equals(MainWindow.getInstance().getFolderTreeView().getRoot().getValue())){
            MainWindow.getInstance().getFolderTreeView().getRoot().setExpanded(true);
        }

        // check if the TreeItems were Expanded, If a TreeItem was Expanded, set Expand True
        for (TreeItem<String> child : MainWindow.getInstance().getFolderTreeView().getRoot().getChildren()) {
            if (lstofExpanded.contains(child.getValue())) {
                child.setExpanded(true);
            }
        }
    }


}
