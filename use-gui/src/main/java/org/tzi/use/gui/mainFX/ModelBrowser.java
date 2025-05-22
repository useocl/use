package org.tzi.use.gui.mainFX;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.web.WebView;
import org.tzi.use.gui.util.MMHTMLPrintVisitor;
import org.tzi.use.main.runtime.IRuntime;
import org.tzi.use.uml.mm.*;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class ModelBrowser {

    private WebView fHtmlPane;
    private MModel fModel;
    private Map<String, Collection<?>> modelCollections = new HashMap<String, Collection<?>>();
    private ModelBrowserSorting fMbs;
    private final ObservableList<SelectionChangedListener> listeners = FXCollections.observableArrayList();

    private static ModelBrowser instance;

    private static TreeView<String> fFolderTreeView;

    private IRuntime fPluginRuntime;

    private Map<String, MModelElement> modelElementMap = new HashMap<>();


    public Map<String, Collection<?>> getModelCollections() {
        return this.modelCollections;
    }


    public ModelBrowser() {
        instance = this;
    }

    /**
     * Creates a browser with no model.
     */
    public ModelBrowser(MModel model, IRuntime pluginRuntime) {
        setPluginRuntime(pluginRuntime);
        setModel(model);
        MainWindow.getInstance().setMode();

        // Apply CSS styling for angled lines (can be defined in an external CSS file)
        MainWindow.getInstance().setStyleSheet();

        // Listen for selection changes

        MainWindow.getInstance().getFolderTreeView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //TreeItem<String> selectedItem = newValue;

            //Object nodeInfo = selectedItem.getValue();
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
        ScrollPane treeViewScrollPane = new ScrollPane(MainWindow.getInstance().getFolderTreeView());
        treeViewScrollPane.setFitToWidth(true);

//        // Create the WebView (equivalent to JEditorPane)
//        MainWindowFX.getInstance().initWebViewPlaceholder();

        // Wrap the WebView in a ScrollPane
        Platform.runLater(() -> {

                    ScrollPane htmlViewScrollPane = new ScrollPane(MainWindow.getInstance().getWebViewFromPlaceholder());
                    htmlViewScrollPane.setFitToWidth(true);

                    // Set minimum sizes for the split panes
                    treeViewScrollPane.setMinHeight(50);
                    htmlViewScrollPane.setMinHeight(50);
                }

        );


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
        MainWindow.getInstance().getWebViewFromPlaceholder().getEngine().loadContent(spec);
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

    void setModel(MModel model) {
        initializeTreeView(model.name());
        // Set the model
        this.fModel = model;

        fMbs = ModelBrowserSorting.getInstance();
        //fMbs.addSortChangeListener(this);

        // Check if the model is null
        if (model == null) {
            TreeItem<String> rootItem = new TreeItem<>("No model available");
            MainWindow.getInstance().updateTreeView(rootItem);

            return;
        }

        // Set root item with the model name
        TreeItem<String> rootItem = new TreeItem<>(fModel.name());

        editDropDownGraphic(rootItem);

        MainWindow.getInstance().updateTreeView(rootItem);

        // Populate the tree with nodes
        createNodes(rootItem);

        treeLeafIconForClassNodes(rootItem);

        activateDragAndDropFeatures();

        // Reset HTML pane
        if (MainWindow.getInstance().getWebViewFromPlaceholder() != null) {
            MainWindow.getInstance().getWebViewFromPlaceholder().getEngine().loadContent(""); // Clear the WebView content
        }
    }

    public void createNodes(final TreeItem<String> root) {
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

    public void treeLeafIconForClassNodes(TreeItem<String> rootItem){

        for (TreeItem<String> classNode : rootItem.getChildren()) {
            for (TreeItem<String> treeLeaf :classNode.getChildren()) {
                // Load an img for the Tree Leafs
                ImageView treeLeafIcon = getIcon("TreeLeaf.gif");
                treeLeaf.setGraphic(treeLeafIcon);
            }
        }
    }

    /**
     * Adding child nodes to a parent
     */
    private void addChildNodes(TreeItem<String> parent, String nodeName, Collection<?> items) {
        // Load an img for the closed folder
        ImageView folderIconView = getIcon("TreeClosed.gif");

        TreeItem<String> categoryNode = new TreeItem<>(nodeName, folderIconView);
        parent.getChildren().add(categoryNode);

        editDropDownGraphic(categoryNode);


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

    /**
     * Sets dynamic icons for a TreeItem based on its expansion state.
     * This method assigns a closed folder icon initially and switches between an open and closed folder icon
     * when the TreeItem is expanded or collapsed, respectively.
     *
     * @param rootItem the TreeItem to which the dynamic icons are applied.
     */
    public void editDropDownGraphic(TreeItem<String> rootItem){
        // Load an img for the closed folder
        ImageView folderClosedIconView = getIcon("TreeClosed.gif");

        // Default Closed Icon for Initialization
        rootItem.setGraphic(folderClosedIconView);

        // Load an img for the Opened folder
        ImageView folderOpenIconView = getIcon("TreeOpen.gif");

        rootItem.expandedProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                rootItem.setGraphic(folderOpenIconView); // Changes the icon, when the node is opened
            } else {
                rootItem.setGraphic(folderClosedIconView); // Changes the icon, when the node is closed
            }
        });
    }

    public void activateDragAndDropFeatures() {
        TreeView<String> folderTreeView = MainWindow.getInstance().getFolderTreeView();

        folderTreeView.setOnDragDetected(event -> {
            TreeItem<String> selectedItem = folderTreeView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }

            MModelElement element = modelElementMap.get(selectedItem.getValue());
            if (element instanceof MClass) {
                Dragboard db = folderTreeView.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putString("CLASS-" + ((MClass) element).name());
                db.setContent(content);

                event.consume();
            }
        });
    }

    /**
     * Initializes the TreeView with a root item and potential child nodes.
     * The method creates a root TreeItem with the provided name if a model is available,
     * or a placeholder if no model exists. It expands the root item and updates the TreeView display.
     *
     * @param name the name to be used for the root TreeItem or for identification in the placeholder.
     */
    private void initializeTreeView(String name) {
        // Create the root item
        TreeItem<String> rootItem;
        if (fModel != null) {
            rootItem = new TreeItem<>(name);
            createNodes(rootItem);  // Add child nodes to root item
        } else {
            rootItem = new TreeItem<>("No model available");
        }

        rootItem.setExpanded(true); // Expands the root item
        MainWindow.getInstance().updateTreeView(rootItem);
        MainWindow.getInstance().getFolderTreeView().refresh(); // Refresh the TreeView to display the changes
    }

    public interface SelectionChangedListener {
        void selectionChanged(MModelElement element);
    }

    /**
     * Retrieves an icon by name and returns it as an ImageView. This method loads the image from the specified resource path
     * constructed using the icon name provided. It ensures the resource is not null before loading to prevent runtime exceptions.
     *
     * @param name the name of the icon to retrieve, which is used to construct the resource path.
     * @return an ImageView containing the loaded image.
     */
    private static ImageView getIcon(String name) {
        // Load the image from the resource path
        Image image = new Image(Objects.requireNonNull(MainWindow.class.getResourceAsStream("/images/" + name)));
        // Create an ImageView to display the image
        return new ImageView(image);
    }

    public static ModelBrowser getInstance(){
        return instance;
    }

}
