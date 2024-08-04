package org.tzi.use.gui.main;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.tzi.use.gui.views.diagrams.event.ModelBrowserMouseHandling;
import org.tzi.use.uml.mm.MModel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.List;

public class ModelBrowserTest {

    private MModel fModel;

    private TreeItem fTop;
    private TreeView<String> folderTreeView;

    /**
     * Creates a browser with no model.
     */
    public ModelBrowserTest(TreeView<String> folderTreeView) {
        this.folderTreeView = folderTreeView;
    }

    public void updateFolderTree(String name) {
        // Clear existing items
        folderTreeView.setRoot(null);

        // Create the root item
        TreeItem<String> rootItem = new TreeItem<>(name);
        rootItem.setExpanded(true); //expands the TreeItem!

        // Create a list to hold the items
        List<TreeItem<String>> items = new ArrayList<>();
        items.add(new TreeItem<>("Classes"));
        items.add(new TreeItem<>("Associations"));
        items.add(new TreeItem<>("Invariants"));
        items.add(new TreeItem<>("Pre-/Postconditions"));
        items.add(new TreeItem<>("Query Operations"));

        // Add all items from the list to the root item
        rootItem.getChildren().addAll(items);

        // Set the root item
        folderTreeView.setRoot(rootItem);
    }

}
