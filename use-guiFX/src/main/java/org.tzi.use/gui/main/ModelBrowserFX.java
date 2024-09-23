package org.tzi.use.gui.main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.web.WebView;
import org.tzi.use.gui.main.runtime.IPluginMMVisitor;
import org.tzi.use.gui.main.runtime.IPluginMMVisitorFX;
import org.tzi.use.gui.main.runtime.IPluginMModelExtensionPoint;
import org.tzi.use.gui.main.runtime.IPluginMModelExtensionPointFX;
import org.tzi.use.gui.util.MMHTMLPrintVisitor;
import org.tzi.use.gui.util.MMHTMLPrintVisitorFX;
import org.tzi.use.main.runtime.IRuntime;
import org.tzi.use.uml.mm.*;


import javax.swing.tree.DefaultMutableTreeNode;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class ModelBrowserFX implements ModelBrowserSortingFX.SortChangeListener {

    private WebView fHtmlPane;
    private MModel fModel;
    private Map<String, Collection<?>> modelCollections = new HashMap<String, Collection<?>>();
    private ModelBrowserSortingFX fMbs;
    private final ObservableList<SelectionChangedListener> listeners = FXCollections.observableArrayList();

    private static TreeView<String> fFolderTreeView;

    private IRuntime fPluginRuntime;

    private Map<String, MModelElement> modelElementMap = new HashMap<>();


    public Map<String, Collection<?>> getModelCollections() {
        return this.modelCollections;
    }


//    public MModelElement getSelectedModelElement() {
//        TreeItem<Object> selectedItem = folderTreeView.getSelectionModel().getSelectedItem();
//        if (selectedItem != null && selectedItem.getValue() instanceof MModelElement) {
//            return (MModelElement) selectedItem.getValue();
//        }
//        return null;
//    }

//    public void dragGestureRecognized() {
//        // Set up a drag-and-drop action for the tree
//        folderTreeView.setOnDragDetected(event -> {
//            TreeItem<Object> selectedItem = folderTreeView.getSelectionModel().getSelectedItem();
//
//            if (selectedItem == null || !(selectedItem.getValue() instanceof MClass)) {
//                event.consume();
//                return;
//            }
//
//            // Start drag-and-drop gesture
//            Dragboard db = fFolderTreeView.startDragAndDrop(TransferMode.MOVE);
//
//            // Put a string representation of the MClass into the dragboard
//            ClipboardContent content = new ClipboardContent();
//            MClass cls = (MClass) selectedItem.getValue();
//            content.putString("CLASS-" + cls.name());
//            db.setContent(content);
//
//            event.consume();
//        });
//
//        // Set up event for when the drag is done
//        fFolderTreeView.setOnDragDone(event -> {
//            if (event.getTransferMode() == TransferMode.MOVE) {
//                System.out.println("Drag successful");
//            }
//            event.consume();
//        });
//    }

    /**
     * Creates a browser with no model.
     */
    public ModelBrowserFX(MModel model, IRuntime pluginRuntime) {
        System.out.println("ModelBrowserFX Constructor Called");
        setPluginRuntime(pluginRuntime);
        //this.fFolderTreeView = folderTreeView;
        setModel(model);
        MainWindowFX.getInstance().getWebViewFromPlaceholder().getEngine().loadContent("Wie Gehts?");
        MainWindowFX.getInstance().setMode();


//        // Set custom cell renderer (CellFactory in JavaFX)
//        MainWindowFX.getInstance().getFolderTreeView().setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
//            @Override
//            public CellRenderer<String> call(TreeView<String> param) {
//                return new CellRenderer(); // Use the custom CellRenderer
//            }
//        });

        // Apply CSS styling for angled lines (can be defined in an external CSS file)
        MainWindowFX.getInstance().setStyleSheet();

        // Listen for selection changes

        MainWindowFX.getInstance().getFolderTreeView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //TreeItem<String> selectedItem = newValue;

            //Object nodeInfo = selectedItem.getValue();
            System.out.println("your here?");
            // Check if the node is a leaf and is an instance of MModelElement
            if (newValue != null) {
                String selectedItem = newValue.getValue();  // Der ausgewählte String

                // Hole das entsprechende MModelElement aus der Map
                MModelElement selectedElement = modelElementMap.get(selectedItem);

                if (selectedElement != null) {
                    // displayInfo aufrufen, um die Informationen im WebView anzuzeigen
                    displayInfo(selectedElement);
                }
            }

        });


        // Wrap the TreeView in a ScrollPane
        ScrollPane treeViewScrollPane = new ScrollPane(MainWindowFX.getInstance().getFolderTreeView());
        treeViewScrollPane.setFitToWidth(true);

//        // Create the WebView (equivalent to JEditorPane)
//        MainWindowFX.getInstance().initWebViewPlaceholder();

        // Wrap the WebView in a ScrollPane
        ScrollPane htmlViewScrollPane = new ScrollPane(MainWindowFX.getInstance().getWebViewFromPlaceholder());
        htmlViewScrollPane.setFitToWidth(true);

        // Set minimum sizes for the split panes
        treeViewScrollPane.setMinHeight(50);
        htmlViewScrollPane.setMinHeight(50);

    }

    private void displayInfo(MModelElement element) {

        StringWriter sw = new StringWriter();
        sw.write("<html><head>");
        sw.write("<style> <!-- body { font-family: sansserif; } --> </style>");
        sw.write("</head><body><font size=\"-1\">");

        // Erstelle einen Visitor, um die HTML-Repräsentation zu erstellen
        //PrintWriter pv = new PrintWriter(sw);
        MMHTMLPrintVisitor v = new MMHTMLPrintVisitor(new PrintWriter(sw));

        element.processWithVisitor(v);

        sw.write("</font></body></html>");
        String spec = sw.toString();

        // Set the HTML content in the WebView
        System.out.println("HTML: " + spec);
        MainWindowFX.getInstance().getWebViewFromPlaceholder().getEngine().loadContent(spec);
    }

    public void fireSelectionChanged(MModelElement elem) {
        // Notify all registered listeners
        for (SelectionChangedListener listener : listeners) {
            listener.selectionChanged(elem);
        }
    }

    public void addSelectionChangedListener(SelectionChangedListener listener) {
        listeners.add(listener);
    }

    public void removeSelectionChangedListener(SelectionChangedListener listener) {
        listeners.remove(listener);
    }

    private void setPluginRuntime(IRuntime pluginRuntime) {
        this.fPluginRuntime = pluginRuntime;
    }

    @Override
    public void stateChanged(ModelBrowserSortingFX.SortChangeEvent e) {
        System.out.println("Sort has changed");
    }

    class CellRenderer extends TreeCell<Object> {

        @Override
        protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
                setTooltip(null);
                return;
            }

            TreeItem<Object> treeItem = getTreeItem();
            int level = treeItem.getParent() == null ? 0 : treeItem.getParent().getChildren().indexOf(treeItem) + 1;

            setTooltip(null); // Reset tooltip by default

            // Display root and categories as non-leaf nodes
            if (level == 0) {
                // Root level - Customize icons
                setText(item.toString());
                if (treeItem.isLeaf()) {
                    // Set an icon or style for a closed node (No model available)
                    setGraphic(getClosedIcon());
                } else {
                    // Set an icon for an open node
                    setGraphic(getOpenIcon());
                }
            } else if (level == 1) {
                // Categories level
                setText(item.toString());
                if (treeItem.isExpanded()) {
                    setGraphic(getOpenIcon());
                } else {
                    setGraphic(getClosedIcon());
                }
            } else {
                // Custom level rendering (for MClass and other types)
                setText(item.toString());
                if (item instanceof MClass) {
                    MClass cls = (MClass) item;
                    Tooltip tooltip = new Tooltip("Drag onto object diagram to create a new " + cls.name() + " object.");
                    setTooltip(tooltip);
                }
            }
        }

        private Node getClosedIcon() {
            // Create a custom icon for a closed node (optional)
            return new Label("Closed"); // Replace with an actual icon if needed
        }

        private Node getOpenIcon() {
            // Create a custom icon for an open node (optional)
            return new Label("Open"); // Replace with an actual icon if needed
        }
    }

    void setModel(MModel model) {
        initializeTreeView(model.name());
        // Set the model
        this.fModel = model;

        fMbs = ModelBrowserSortingFX.getInstance();
        fMbs.addSortChangeListener(this);

        // Check if the model is null
        if (model == null) {
            TreeItem<String> rootItem = new TreeItem<>("No model available");
            MainWindowFX.getInstance().updateTreeView(rootItem);

            return;
        }

        // Set root item with the model name
        System.out.println("Model Name: " + fModel.name());
        TreeItem<String> rootItem = new TreeItem<>(fModel.name());
        MainWindowFX.getInstance().updateTreeView(rootItem);

        // Populate the tree with nodes
        createNodes(rootItem);

        //fMbs = ModelBrowserSortingFX.getInstance();
        //fMbs.addSortChangeListener(this);

        // Add sorting and mouse handling
        applySorting(MainWindowFX.getInstance().getFolderTreeView()); // Call sorting method
        setupMouseHandling(MainWindowFX.getInstance().getFolderTreeView()); // Set up mouse event handlers

        // Reset HTML pane
        if (MainWindowFX.getInstance().getWebViewFromPlaceholder() != null) {
            MainWindowFX.getInstance().getWebViewFromPlaceholder().getEngine().loadContent(""); // Clear the WebView content
        }
    }

    private void createNodes(final TreeItem<String> root) {
        // Example: Adding sorted classes
        Collection<MClass> sortedClasses = fMbs.sortClasses(new ArrayList<>(fModel.classes()));
        addChildNodes(root, "Classes", sortedClasses);

        // Example: Adding sorted classes
        Collection<MAssociation> sortedAssociations = fMbs.sortAssociations(new ArrayList<>(fModel.associations()));
        addChildNodes(root, "Associations", sortedAssociations);

        // Example: Adding sorted classes
        Collection<MClassInvariant> sortedInvariants = fMbs.sortInvariants(fModel.classInvariants());
        addChildNodes(root, "Invariants", sortedInvariants);

        // Example: Adding sorted classes
        Collection<MPrePostCondition> sortedConditions = fMbs.sortPrePostConditions(fModel.prePostConditions());
        addChildNodes(root, "Pre-/Postconditions", sortedConditions);

        Set<Map.Entry<String, Collection<?>>> modelCollectionEntrySet = this.modelCollections.entrySet();


        for (Map.Entry<String, Collection<?>> modelCollectionMapEntry : modelCollectionEntrySet) {
            String modelCollectionName = modelCollectionMapEntry.getKey()
                    .toString();
            Collection<?> modelCollection = fMbs
                    .sortPluginCollection(modelCollectionMapEntry.getValue());
            addChildNodes(root, modelCollectionName, modelCollection);
        }

        final Collection<MOperation> queryOperations = new ArrayList<MOperation>();
        for (MClass mClass : fModel.classes()) {
            for (MOperation mOperation : mClass.operations()) {
                if (mOperation.hasExpression()) {
                    queryOperations.add(mOperation);
                }
            }
        }
        addChildNodes(root, "Query Operations", queryOperations);


    }

    // Adding child nodes to a parent
    private void addChildNodes(TreeItem<String> parent, String nodeName, Collection<?> items) {
        TreeItem<String> categoryNode = new TreeItem<>(nodeName);
        parent.getChildren().add(categoryNode);

//        for (MModelElement item : items) {
//            TreeItem<MModelElement> child = new TreeItem<MModelElement>(item);
//            categoryNode.getChildren().add(child.getValue());
//        }

        for (Object item : items) {
            if (item instanceof MClass) {
                MClass mClass = (MClass) item;
                TreeItem<String> childNode = new TreeItem<>(mClass.toString());
                categoryNode.getChildren().add(childNode);
                modelElementMap.put(mClass.toString(), mClass);  // Füge das Mapping hinzu

            } else if (item instanceof MAssociation) {
                MAssociation mAssociation = (MAssociation) item;
                TreeItem<String> childNode = new TreeItem<>(mAssociation.toString());
                categoryNode.getChildren().add(childNode);
                modelElementMap.put(mAssociation.toString(), mAssociation);  // Füge das Mapping hinzu

            } else if (item instanceof MClassInvariant) {
                MClassInvariant mClassInvariant = (MClassInvariant) item;
                TreeItem<String> childNode = new TreeItem<>(mClassInvariant.toString());
                categoryNode.getChildren().add(childNode);
                modelElementMap.put(mClassInvariant.toString(), mClassInvariant);  // Füge das Mapping hinzu

            } else if (item instanceof MPrePostCondition) {
                MPrePostCondition mPrePostCondition = (MPrePostCondition) item;
                TreeItem<String> childNode = new TreeItem<>(mPrePostCondition.toString());
                categoryNode.getChildren().add(childNode);
                modelElementMap.put(mPrePostCondition.toString(), mPrePostCondition);  // Füge das Mapping hinzu


            } else if (item instanceof MOperation) {
                MOperation mOperation = (MOperation) item;
                TreeItem<String> childNode = new TreeItem<>(mOperation.toString());
                categoryNode.getChildren().add(childNode);
                modelElementMap.put(mOperation.toString(), mOperation);  // Füge das Mapping hinzu

            }
        }
    }

    // Sort TreeItem children
    private void sortTreeItems(TreeItem<String> rootItem) {
        ObservableList<TreeItem<String>> children = rootItem.getChildren();
        FXCollections.sort(children, (item1, item2) -> {
            // Implement your sorting logic here
            // For example, if sorting by a string name:
            return item1.getValue().toString().compareTo(item2.getValue().toString());
        });
        for (TreeItem<String> child : children) {
            sortTreeItems(child); // Recursively sort children
        }
    }

    // Call this method to sort the tree view
    public void applySorting(TreeView<String> folderTreeView) {
        TreeItem<String> root = folderTreeView.getRoot();
        if (root != null) {
            sortTreeItems(root);
        }
    }

    // Set up mouse handling
    private void setupMouseHandling(TreeView<String> folderTreeView) {
        folderTreeView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                // Handle double-click event
                TreeItem<String> selectedItem = MainWindowFX.getInstance().getFolderTreeView().getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    // Perform an action with the selected item
                    System.out.println("Double-clicked on: " + selectedItem.getValue() + selectedItem.toString() + selectedItem.getChildren().toString());
                }
            }
        });

        // Example of handling right-click
        folderTreeView.setOnContextMenuRequested(event -> {
            // Show context menu or perform actions
            System.out.println("Right-clicked at: " + event.getScreenX() + ", " + event.getScreenY());
        });
    }

    private void initializeTreeView(String name) {
        // Create the root item
        TreeItem<String> rootItem;
        if (fModel != null) {
            rootItem = new TreeItem<>(name);
            createNodes(rootItem);  // Add child nodes to root item
            System.out.println("TreeView root set to: " + name);
        } else {
            rootItem = new TreeItem<>("No model available");
        }

        rootItem.setExpanded(true); // Expands the root item
        MainWindowFX.getInstance().updateTreeView(rootItem);
        MainWindowFX.getInstance().getFolderTreeView().refresh(); // Refresh the TreeView to display the changes
    }

    public interface SelectionChangedListener {
        void selectionChanged(MModelElement element);
    }

}
