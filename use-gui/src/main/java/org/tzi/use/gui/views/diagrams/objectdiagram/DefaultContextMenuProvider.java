package org.tzi.use.gui.views.diagrams.objectdiagram;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
// ...existing imports...
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
/**
 * Default context menu provider used when no plugin provides a specialized menu.
 */
public class DefaultContextMenuProvider implements ContextMenuProvider {
    @Override
    public void enhanceMenu(JPopupMenu popupMenu,
                            ObjectDiagramInteractor diagram,
                            NewObjectDiagramPresenter presenter,
                            Set<MObject> selectedObjects,
                            Set<MLink> selectedLinks,
                            Set<MObject> selectedAssocObjects) {
        if (popupMenu == null || presenter == null) {
            return;
        }
        // Build menu in a strictly linear fashion. Helpers only build items; this method
        // inserts them at incrementing positions and controls all separators centrally.
        int pos = 0;

        // 1) Object-specific actions (edit/destroy)
        List<JMenuItem> objectActions = buildObjectActions(presenter, selectedObjects);
        for (JMenuItem it : objectActions) {
            popupMenu.insert(it, pos++);
        }

        // 2) Separator
        popupMenu.insert(new JSeparator(), pos++);

        // 3) Insert/actions placeholders
        List<JMenuItem> insertItems = buildInsertPlaceholders(presenter, selectedObjects, selectedLinks);
        for (JMenuItem it : insertItems) {
            popupMenu.insert(it, pos++);
        }
        // separator after insert block
        popupMenu.insert(new JSeparator(), pos++);

        // 4) Show hidden elements (single item, may be null)
        JMenuItem showHidden = buildShowHiddenElementsIfNeeded(diagram, presenter);
        if (showHidden != null) {
            popupMenu.insert(showHidden, pos++);
        }

        // 5) Top-level hide/crop/grey display items
        HideCropGroup hideCrop = buildHideCropGreyTopLevel(presenter, diagram, selectedObjects, selectedLinks);
        for (JMenuItem it : hideCrop.topItems) {
            popupMenu.insert(it, pos++);
        }
        // separator after top-level hide/crop/grey
        popupMenu.insert(new JSeparator(), pos++);

        // 6) Submenus and other fixed entries in requested order
        // Two identical "Show/hide/crop objects" entries are preserved intentionally
        JMenu showHideCrop1 = buildShowHideCropSubmenu(presenter, diagram, selectedObjects, hideCrop);
        popupMenu.insert(showHideCrop1, pos++);
        JMenu showHideCrop2 = buildShowHideCropSubmenu(presenter, diagram, selectedObjects, hideCrop);
        popupMenu.insert(showHideCrop2, pos++);

        JMenu hideObjectsMenu = buildHideObjectsSubmenu(presenter, selectedObjects);
        popupMenu.insert(hideObjectsMenu, pos++);

        JMenu hideLinksMenu = buildHideLinksSubmenu(presenter, selectedLinks);
        popupMenu.insert(hideLinksMenu, pos++);

        popupMenu.insert(new JSeparator(), pos++);

        JMenuItem linksByKind = buildLinksByKindPlaceholder();
        popupMenu.insert(linksByKind, pos++);

        popupMenu.insert(new JSeparator(), pos++);

        JMenuItem protocol = buildProtocolStatePlaceholder();
        popupMenu.insert(protocol, pos++);

        popupMenu.insert(new JSeparator(), pos++);

        JMenuItem commentNode = buildCommentNode(presenter);
        popupMenu.insert(commentNode, pos++);

        popupMenu.insert(new JSeparator(), pos++);

        // 7) Checkboxes and general items: these are typically created in DiagramView.unionOfPopUpMenu,
        // but we need to ensure the "Show states" checkbox is present and wired to the diagram/presenter.
        try {
            boolean init = diagram != null && diagram.isShowStates();
            JCheckBoxMenuItem showStates = new JCheckBoxMenuItem("Show states", init);
            showStates.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent ev) {
                    boolean sel = ev.getStateChange() == ItemEvent.SELECTED;
                    try {
                        presenter.onToggleShowStates(sel);
                    } catch (Throwable ignored) {}
                    if (diagram != null) {
                        diagram.setShowStates(sel);
                        diagram.invalidateContent(true);
                    }
                }
            });
            popupMenu.insert(showStates, pos++);
        } catch (Throwable ignored) {
            // safe fallback: if diagram API not present, skip
        }
        // Ensure separators are normalized after deterministic insertion sequence
        normalizeSeparators(popupMenu);
    }
    private int addObjectActions(JPopupMenu popupMenu,
                                 NewObjectDiagramPresenter presenter,
                                 Set<MObject> selectedObjects,
                                 int pos) {
        if (selectedObjects.size() == 1) {
            MObject single = selectedObjects.iterator().next();
            String name = safeName(single);
            popupMenu.insert(buildMenuItem("Edit properties of '" + name + "'", ev -> presenter.onShowObjectProperties(single)), pos++);
            popupMenu.insert(buildMenuItem("Destroy '" + name + "'", ev -> presenter.onDeleteObjects(selectedObjects)), pos++);
        } else if (selectedObjects.size() > 1) {
            popupMenu.insert(buildMenuItem("Destroy " + selectedObjects.size() + " objects", ev -> presenter.onDeleteObjects(selectedObjects)), pos++);
        }
        return pos;
    }
    private int addHideObjectsSubmenu(JPopupMenu popupMenu,
                                      NewObjectDiagramPresenter presenter,
                                      Set<MObject> selectedObjects,
                                      int pos) {
        if (!selectedObjects.isEmpty()) {
            JMenu hideObjectsMenu = new JMenu("Hide objects");
            hideObjectsMenu.add(buildMenuItem("Hide selected objects", ev -> presenter.onHideObjects(selectedObjects)));
            popupMenu.insert(hideObjectsMenu, pos++);
        }
        return pos;
    }
    private int addLinksByKindPlaceholder(JPopupMenu popupMenu, int pos) {
        JMenuItem placeholder = new JMenuItem("Show/hide links-by-kind");
        placeholder.setEnabled(false);
        popupMenu.insert(placeholder, pos++);
        return pos;
    }
    private int addProtocolStatePlaceholder(JPopupMenu popupMenu, int pos) {
        JMenuItem placeholder = new JMenuItem("Show protocol state machine...");
        placeholder.setEnabled(false);
        popupMenu.insert(placeholder, pos++);
        return pos;
    }
    private int addCommentNode(JPopupMenu popupMenu,
                                NewObjectDiagramPresenter presenter,
                                int pos) {
        // Always insert a separator immediately before the comment node.
        // normalizeSeparators() will later remove duplicates/leading separators.
        popupMenu.insert(new JSeparator(), pos++);
        popupMenu.insert(buildMenuItem("Add comment node", ev -> presenter.onStatusMessage("Add comment node not implemented in presenter menu")), pos++);
        return pos;
    }
    private int insertSeparator(JPopupMenu popupMenu, int pos) {
        // Only insert a separator if we have at least one component already
        // and the last component is not a separator. This avoids stray small
        // gaps when a conditional block (like inserts) is omitted.
        int count = popupMenu.getComponentCount();
        if (count == 0) {
            return pos;
        }
        java.awt.Component last = popupMenu.getComponent(count - 1);
        if (last instanceof JSeparator) {
            return pos;
        }
        popupMenu.insert(new JSeparator(), pos++);
        return pos;
    }
    private HideCropGroup addHideCropGreyTopLevel(JPopupMenu popupMenu,
                                                  NewObjectDiagramPresenter presenter,
                                                  ObjectDiagramInteractor diagram,
                                                  Set<MObject> selectedObjects,
                                                  Set<MLink> selectedLinks,
                                                  int pos) {
        // Build labels according to selection
        int size = selectedObjects.size() + selectedLinks.size();
        String hideLabel;
        String cropLabel;
        if (size > 1) {
            hideLabel = "Hide " + size + " elements";
            cropLabel = "Crop " + size + " elements";
        } else if (selectedObjects.size() == 1) {
            String name = safeName(selectedObjects.iterator().next());
            hideLabel = "Hide " + name;
            cropLabel = "Crop " + name;
        } else {
            // single link or nothing selected -> generic labels
            hideLabel = "Hide selected";
            cropLabel = "Crop selected";
        }

        // Create display menu items directly (MenuElements) so the popup does not close
        // when hovering. Add a decorative matte border around the group to emulate the
        // boxed appearance from the previous implementation.
        List<JMenuItem> displayItems = new ArrayList<>();

        JMenuItem displayHide = buildMenuItem(hideLabel, ev -> {
            presenter.onHideLinks(selectedLinks);
            presenter.onHideObjects(selectedObjects);
            presenter.onStatusMessage("Selected elements hidden");
        });
        displayItems.add(displayHide);

        JMenuItem displayCrop = buildMenuItem(cropLabel, ev -> {
            Set<MObject> objectsToHide = new HashSet<>(selectedObjects);
            selectedLinks.forEach(link -> {
                if (link instanceof MLinkObject linkObject) {
                    objectsToHide.add(linkObject);
                } else {
                    objectsToHide.addAll(link.linkedObjects());
                }
            });
            presenter.onCropSelection(objectsToHide);
            presenter.onStatusMessage("Cropped selection");
        });
        displayItems.add(displayCrop);

        // Grey in / out
        int greyed = 0;
        int visible = 0;
        for (MObject obj : selectedObjects) {
            if (diagram != null && diagram.isGreyed(obj)) {
                greyed++;
            } else {
                visible++;
            }
        }
        if (greyed > 0) {
            String labelGreyIn;
            if (selectedObjects.size() == 1) {
                labelGreyIn = "Grey in " + safeName(selectedObjects.iterator().next());
            } else {
                labelGreyIn = "Grey in selected";
                if (greyed > 1) {
                    labelGreyIn += " (" + greyed + ")";
                }
            }
            displayItems.add(buildMenuItem(labelGreyIn, ev -> presenter.onGrayIn(selectedObjects)));
        }
        if (visible > 0) {
            String labelGreyOut;
            if (selectedObjects.size() == 1) {
                labelGreyOut = "Grey out " + safeName(selectedObjects.iterator().next());
            } else {
                labelGreyOut = "Grey out selected";
                if (visible > 1) {
                    labelGreyOut += " (" + visible + ")";
                }
            }
            displayItems.add(buildMenuItem(labelGreyOut, ev -> presenter.onGrayOut(selectedObjects)));
        }

        // Insert the items as regular JMenuItems between separators (like insert entries)
        if (!displayItems.isEmpty()) {
            for (JMenuItem it : displayItems) {
                // ensure consistent look & behavior
                it.setOpaque(true);
                it.setBackground(UIManager.getColor("Menu.background"));
                popupMenu.insert(it, pos++);
            }
            // insert a separator immediately after the top-level hide/crop/grey items
            popupMenu.insert(new JSeparator(), pos++);
        }

        // Create separate JMenuItem instances to be used in the Show/hide/crop submenu later.
        JMenuItem hideItem = new JMenuItem(hideLabel);
        hideItem.addActionListener(ev -> {
            presenter.onHideLinks(selectedLinks);
            presenter.onHideObjects(selectedObjects);
            presenter.onStatusMessage("Selected elements hidden");
        });
        JMenuItem cropItem = new JMenuItem(cropLabel);
        cropItem.addActionListener(ev -> {
            Set<MObject> objectsToHide = new HashSet<>(selectedObjects);
            selectedLinks.forEach(link -> {
                if (link instanceof MLinkObject linkObject) {
                    objectsToHide.add(linkObject);
                } else {
                    objectsToHide.addAll(link.linkedObjects());
                }
            });
            presenter.onCropSelection(objectsToHide);
            presenter.onStatusMessage("Cropped selection");
        });

        int nextPos = popupMenu.getComponentCount();
        return new HideCropGroup(hideItem, cropItem, nextPos);
    }
    private int addShowHideCropSubmenu(JPopupMenu popupMenu,
                                       NewObjectDiagramPresenter presenter,
                                       ObjectDiagramInteractor diagram,
                                       Set<MObject> selectedObjects,
                                       HideCropGroup hideCrop,
                                       int pos) {
        JMenu showHideCrop = new JMenu("Show/hide/crop objects");
        showHideCrop.add(hideCrop.hideItem);
        showHideCrop.add(hideCrop.cropItem);
        addGreyItems(showHideCrop, diagram, presenter, selectedObjects);
        popupMenu.insert(showHideCrop, pos++);
        return pos;
    }
    private int addHideLinksSubmenu(JPopupMenu popupMenu,
                                     NewObjectDiagramPresenter presenter,
                                     Set<MLink> selectedLinks,
                                     int pos) {
        if (!selectedLinks.isEmpty()) {
            JMenu hideLinksMenu = new JMenu("Hide links");
            hideLinksMenu.add(buildMenuItem("Hide selected links", ev -> presenter.onHideLinks(selectedLinks)));
            popupMenu.insert(hideLinksMenu, pos++);
        }
        return pos;
    }
    /**
     * Adds a group of placeholder "insert ..." menu items as seen in the reference Kontextmenü.
     * Keeps them non-interactive or routes them to a status message via presenter.
     * This implementation restores the full combinatorial generation and per-link delete items
     * similar to the original legacy `unionOfPopUpMenu()`.
     */
    private int addInsertPlaceholders(JPopupMenu popupMenu,
                                      NewObjectDiagramPresenter presenter,
                                      Set<MObject> selectedObjects,
                                      Set<MLink> selectedLinks,
                                      int pos) {
        // If there are selected objects, generate all assignments of selected objects
        // to association ends (radix-based enumeration) and offer insert/delete actions
        // per combination where applicable.
        if (selectedObjects != null && !selectedObjects.isEmpty()) {
            List<MObject> selList = new ArrayList<>(selectedObjects);
            int m = selList.size();
            boolean addedInsertLinkAction = false;
            Collection<MAssociation> associations = presenter.fetchAllAssociations();
            if (associations == null) {
                return pos;
            }
            for (MAssociation assoc : associations) {
                if (assoc.isReadOnly()) continue;
                List<MAssociationEnd> ends = assoc.associationEnds();
                int n = ends == null ? 0 : ends.size();
                if (n == 0) continue;
                if (m > n) continue; // more selected objects than ends
                int pow = 1;
                for (int i = 0; i < n; ++i) pow *= m;
                for (int i = 0; i < pow; ++i) {
                    int[] digits = radixConversion(i, m, n);
                    if (!isCompleteObjectCombination(digits, m)) continue;
                    MObject[] l = new MObject[n];
                    MClass[] c = new MClass[n];
                    for (int j = 0; j < n; ++j) {
                        l[j] = selList.get(digits[j]);
                        c[j] = l[j].cls();
                    }
                    if (!assoc.isAssignableFrom(c)) continue;
                    // find existing links between these objects for this association
                    Set<MLink> links = presenter.fetchLinksOfAssociation(assoc).stream()
                            .filter(link -> matchesLink(link, l))
                            .collect(Collectors.toSet());
                    if (links.isEmpty() || assoc.hasQualifiedEnds()) {
                        // build label like: "insert (Obj1,Obj2,...) into AssocName"
                        String joinedNames = Arrays.stream(l)
                                .map(this::safeName)
                                .collect(Collectors.joining(","));
                        String insLabel = "insert (" + joinedNames + ") into " + assoc.name();
                        JMenuItem ins = buildMenuItem(insLabel, ev -> presenter.onInsertLink(assoc, Arrays.asList(l)));
                        popupMenu.insert(ins, pos++);
                        addedInsertLinkAction = true;
                    }
                    if (!links.isEmpty()) {
                        for (MLink link : links) {
                            JMenuItem del = buildMenuItem("delete " + link.toString(), ev -> presenter.onDeleteLink(link));
                            popupMenu.insert(del, pos++);
                            addedInsertLinkAction = true;
                        }
                    }
                }
            }
            if (addedInsertLinkAction) {
                pos = insertSeparator(popupMenu, pos);
            }
            return pos;
        }
        // Fallback: no selection -> show generic insert items based on available objects of classes
        Collection<MAssociation> associations = presenter.fetchAllAssociations();
        if (associations == null || associations.isEmpty()) {
            return pos;
        }
        for (MAssociation assoc : associations) {
            List<MAssociationEnd> ends = assoc.associationEnds();
            if (ends == null || ends.isEmpty()) {
                continue;
            }
            boolean possible = true;
            for (MAssociationEnd end : ends) {
                // fetch objects of the required class from the presenter (diagram-level)
                Set<MObject> candidates = presenter.fetchObjectsOfClass(end.cls());
                if (candidates == null || candidates.isEmpty()) {
                    possible = false;
                    break;
                }
            }
            if (!possible) {
                // skip associations that cannot be realized from the current diagram
                continue;
            }
            int required = ends.size();
            boolean selectionEmpty = selectedObjects == null || selectedObjects.isEmpty();
            boolean selectionMatches = selectedObjects != null && selectedObjects.size() == required;
            String label;
            if (!selectionEmpty && selectedObjects.size() == 1 && required == 2) {
                String name = safeName(selectedObjects.iterator().next());
                // match formatting: no space after comma
                label = "insert (" + name + "," + name + ") into " + assoc.name();
            } else {
                label = "insert into " + assoc.name();
            }
            JMenuItem mi = buildMenuItem(label, ev -> {
                if (selectionMatches) {
                    presenter.onInsertLink(assoc, new ArrayList<>(selectedObjects));
                } else {
                    // interactive insertion: presenter will choose participants from available objects
                    presenter.onInsertLinkInteractive(selectedObjects);
                }
            });
            // only enabled when selection matches required participants or interaction is possible
            mi.setEnabled(selectionMatches || selectionEmpty);
            popupMenu.insert(mi, pos++);
        }
        return pos;
    }
    /**
     * Returns the number of associations that can be realized given the
     * objects currently available in the diagram. This is used to decide
     * whether to render the insert block at all (avoids small empty gaps).
     */
    private int countPossibleInserts(NewObjectDiagramPresenter presenter, Set<MObject> selectedObjects) {
        Collection<MAssociation> associations = presenter.fetchAllAssociations();
        if (associations == null || associations.isEmpty()) {
            return 0;
        }
        int count = 0;
        for (MAssociation assoc : associations) {
            List<MAssociationEnd> ends = assoc.associationEnds();
            if (ends == null || ends.isEmpty()) {
                continue;
            }
            boolean possible = true;
            for (MAssociationEnd end : ends) {
                Set<MObject> candidates = presenter.fetchObjectsOfClass(end.cls());
                if (candidates == null || candidates.isEmpty()) {
                    possible = false;
                    break;
                }
            }
            if (possible) {
                count++;
            }
        }
        return count;
    }
    // removed unused addShowHidden to match the reference menu ordering
    private static class HideCropGroup {
        final JMenuItem hideItem;
        final JMenuItem cropItem;
        final int nextPos;
        final List<JMenuItem> topItems;

        HideCropGroup(JMenuItem hideItem, JMenuItem cropItem, int nextPos) {
            this.hideItem = hideItem;
            this.cropItem = cropItem;
            this.nextPos = nextPos;
            this.topItems = List.of();
        }

        HideCropGroup(List<JMenuItem> topItems, JMenuItem hideItem, JMenuItem cropItem) {
            this.hideItem = hideItem;
            this.cropItem = cropItem;
            this.nextPos = -1;
            this.topItems = topItems == null ? List.of() : topItems;
        }
    }
    private JMenuItem buildMenuItem(String label, ActionListener l) {
        JMenuItem item = new JMenuItem(label);
        item.addActionListener(l);
        return item;
    }

    // New builders that only create items/menus and do NOT insert into a JPopupMenu
    private List<JMenuItem> buildObjectActions(NewObjectDiagramPresenter presenter, Set<MObject> selectedObjects) {
        List<JMenuItem> res = new ArrayList<>();
        if (selectedObjects == null) {
            return res;
        }
        if (selectedObjects.size() == 1) {
            MObject single = selectedObjects.iterator().next();
            String name = safeName(single);
            res.add(buildMenuItem("Edit properties of '" + name + "'", ev -> presenter.onShowObjectProperties(single)));
            res.add(buildMenuItem("Destroy '" + name + "'", ev -> presenter.onDeleteObjects(selectedObjects)));
        } else if (selectedObjects.size() > 1) {
            res.add(buildMenuItem("Destroy " + selectedObjects.size() + " objects", ev -> presenter.onDeleteObjects(selectedObjects)));
        }
        return res;
    }

    private List<JMenuItem> buildInsertPlaceholders(NewObjectDiagramPresenter presenter, Set<MObject> selectedObjects, Set<MLink> selectedLinks) {
        List<JMenuItem> items = new ArrayList<>();
        if (presenter == null) return items;

        if (selectedObjects != null && !selectedObjects.isEmpty()) {
            List<MObject> selList = new ArrayList<>(selectedObjects);
            int m = selList.size();
            boolean addedInsertLinkAction = false;
            Collection<MAssociation> associations = presenter.fetchAllAssociations();
            if (associations == null) {
                return items;
            }
            for (MAssociation assoc : associations) {
                if (assoc.isReadOnly()) continue;
                List<MAssociationEnd> ends = assoc.associationEnds();
                int n = ends == null ? 0 : ends.size();
                if (n == 0) continue;
                if (m > n) continue; // more selected objects than ends
                int pow = 1;
                for (int i = 0; i < n; ++i) pow *= m;
                for (int i = 0; i < pow; ++i) {
                    int[] digits = radixConversion(i, m, n);
                    if (!isCompleteObjectCombination(digits, m)) continue;
                    MObject[] l = new MObject[n];
                    MClass[] c = new MClass[n];
                    for (int j = 0; j < n; ++j) {
                        l[j] = selList.get(digits[j]);
                        c[j] = l[j].cls();
                    }
                    if (!assoc.isAssignableFrom(c)) continue;
                    Set<MLink> links = presenter.fetchLinksOfAssociation(assoc).stream()
                            .filter(link -> matchesLink(link, l))
                            .collect(Collectors.toSet());
                    if (links.isEmpty() || assoc.hasQualifiedEnds()) {
                        String joinedNames = Arrays.stream(l).map(this::safeName).collect(Collectors.joining(","));
                        String insLabel = "insert (" + joinedNames + ") into " + assoc.name();
                        JMenuItem ins = buildMenuItem(insLabel, ev -> presenter.onInsertLink(assoc, Arrays.asList(l)));
                        items.add(ins);
                        addedInsertLinkAction = true;
                    }
                    if (!links.isEmpty()) {
                        for (MLink link : links) {
                            JMenuItem del = buildMenuItem("delete " + link.toString(), ev -> presenter.onDeleteLink(link));
                            items.add(del);
                            addedInsertLinkAction = true;
                        }
                    }
                }
            }
            return items;
        }

        Collection<MAssociation> associations = presenter.fetchAllAssociations();
        if (associations == null || associations.isEmpty()) {
            return items;
        }
        for (MAssociation assoc : associations) {
            List<MAssociationEnd> ends = assoc.associationEnds();
            if (ends == null || ends.isEmpty()) {
                continue;
            }
            boolean possible = true;
            for (MAssociationEnd end : ends) {
                Set<MObject> candidates = presenter.fetchObjectsOfClass(end.cls());
                if (candidates == null || candidates.isEmpty()) {
                    possible = false;
                    break;
                }
            }
            if (!possible) continue;
            int required = ends.size();
            boolean selectionEmpty = selectedObjects == null || selectedObjects.isEmpty();
            boolean selectionMatches = selectedObjects != null && selectedObjects.size() == required;
            String label;
            if (!selectionEmpty && selectedObjects.size() == 1 && required == 2) {
                String name = safeName(selectedObjects.iterator().next());
                label = "insert (" + name + "," + name + ") into " + assoc.name();
            } else {
                label = "insert into " + assoc.name();
            }
            JMenuItem mi = buildMenuItem(label, ev -> {
                if (selectionMatches) {
                    presenter.onInsertLink(assoc, new ArrayList<>(selectedObjects));
                } else {
                    presenter.onInsertLinkInteractive(selectedObjects);
                }
            });
            mi.setEnabled(selectionMatches || selectionEmpty);
            items.add(mi);
        }
        return items;
    }

    private JMenuItem buildShowHiddenElementsIfNeeded(ObjectDiagramInteractor diagram, NewObjectDiagramPresenter presenter) {
        if (diagram == null || presenter == null) return null;
        try {
            Method m = diagram.getClass().getMethod("getHiddenData");
            Object hidden = m.invoke(diagram);
            if (hidden == null) return null;
            Method getEdges = null;
            Method getNodes = null;
            try { getEdges = hidden.getClass().getMethod("getEdges"); } catch (NoSuchMethodException ignored) {}
            try { getNodes = hidden.getClass().getMethod("getNodes"); } catch (NoSuchMethodException ignored) {}
            boolean hasHidden = false;
            if (getEdges != null) {
                Object edges = getEdges.invoke(hidden);
                if (edges instanceof Collection) {
                    hasHidden = !((Collection) edges).isEmpty();
                }
            }
            if (!hasHidden && getNodes != null) {
                Object nodes = getNodes.invoke(hidden);
                if (nodes instanceof Collection) {
                    hasHidden = !((Collection) nodes).isEmpty();
                }
            }
            if (hasHidden) {
                JMenuItem showAllObjects = new JMenuItem("Show hidden elements");
                showAllObjects.addActionListener(ev -> {
                    try { presenter.onShowHiddenElements(); } catch (Throwable ignored) {}
                });
                return showAllObjects;
            }
        } catch (Throwable ignored) {}
        return null;
    }

    private HideCropGroup buildHideCropGreyTopLevel(NewObjectDiagramPresenter presenter,
                                                    ObjectDiagramInteractor diagram,
                                                    Set<MObject> selectedObjects,
                                                    Set<MLink> selectedLinks) {
        // Build labels according to selection
        int size = (selectedObjects == null ? 0 : selectedObjects.size()) + (selectedLinks == null ? 0 : selectedLinks.size());
        String hideLabel;
        String cropLabel;
        if (size > 1) {
            hideLabel = "Hide " + size + " elements";
            cropLabel = "Crop " + size + " elements";
        } else if (selectedObjects != null && selectedObjects.size() == 1) {
            String name = safeName(selectedObjects.iterator().next());
            hideLabel = "Hide " + name;
            cropLabel = "Crop " + name;
        } else {
            hideLabel = "Hide selected";
            cropLabel = "Crop selected";
        }

        List<JMenuItem> topItems = new ArrayList<>();
        JMenuItem displayHide = buildMenuItem(hideLabel, ev -> {
            presenter.onHideLinks(selectedLinks);
            presenter.onHideObjects(selectedObjects);
            presenter.onStatusMessage("Selected elements hidden");
        });
        topItems.add(displayHide);

        JMenuItem displayCrop = buildMenuItem(cropLabel, ev -> {
            Set<MObject> objectsToHide = new HashSet<>(selectedObjects == null ? Set.of() : selectedObjects);
            if (selectedLinks != null) {
                selectedLinks.forEach(link -> {
                    if (link instanceof MLinkObject linkObject) {
                        objectsToHide.add(linkObject);
                    } else {
                        objectsToHide.addAll(link.linkedObjects());
                    }
                });
            }
            presenter.onCropSelection(objectsToHide);
            presenter.onStatusMessage("Cropped selection");
        });
        topItems.add(displayCrop);

        int greyed = 0;
        int visible = 0;
        if (selectedObjects != null) {
            for (MObject obj : selectedObjects) {
                if (diagram != null && diagram.isGreyed(obj)) {
                    greyed++;
                } else {
                    visible++;
                }
            }
        }
        if (greyed > 0) {
            String labelGreyIn;
            if (selectedObjects != null && selectedObjects.size() == 1) {
                labelGreyIn = "Grey in " + safeName(selectedObjects.iterator().next());
            } else {
                labelGreyIn = "Grey in selected";
                if (greyed > 1) labelGreyIn += " (" + greyed + ")";
            }
            topItems.add(buildMenuItem(labelGreyIn, ev -> presenter.onGrayIn(selectedObjects)));
        }
        if (visible > 0) {
            String labelGreyOut;
            if (selectedObjects != null && selectedObjects.size() == 1) {
                labelGreyOut = "Grey out " + safeName(selectedObjects.iterator().next());
            } else {
                labelGreyOut = "Grey out selected";
                if (visible > 1) labelGreyOut += " (" + visible + ")";
            }
            topItems.add(buildMenuItem(labelGreyOut, ev -> presenter.onGrayOut(selectedObjects)));
        }

        JMenuItem hideItem = new JMenuItem(hideLabel);
        hideItem.addActionListener(ev -> {
            presenter.onHideLinks(selectedLinks);
            presenter.onHideObjects(selectedObjects);
            presenter.onStatusMessage("Selected elements hidden");
        });
        JMenuItem cropItem = new JMenuItem(cropLabel);
        cropItem.addActionListener(ev -> {
            Set<MObject> objectsToHide = new HashSet<>(selectedObjects == null ? Set.of() : selectedObjects);
            if (selectedLinks != null) {
                selectedLinks.forEach(link -> {
                    if (link instanceof MLinkObject linkObject) {
                        objectsToHide.add(linkObject);
                    } else {
                        objectsToHide.addAll(link.linkedObjects());
                    }
                });
            }
            presenter.onCropSelection(objectsToHide);
            presenter.onStatusMessage("Cropped selection");
        });

        return new HideCropGroup(topItems, hideItem, cropItem);
    }

    private JMenu buildShowHideCropSubmenu(NewObjectDiagramPresenter presenter, ObjectDiagramInteractor diagram, Set<MObject> selectedObjects, HideCropGroup hideCrop) {
        JMenu showHideCrop = new JMenu("Show/hide/crop objects");
        if (hideCrop != null) {
            showHideCrop.add(hideCrop.hideItem);
            showHideCrop.add(hideCrop.cropItem);
            addGreyItems(showHideCrop, diagram, presenter, selectedObjects == null ? Set.of() : selectedObjects);
        }
        return showHideCrop;
    }

    private JMenu buildHideObjectsSubmenu(NewObjectDiagramPresenter presenter, Set<MObject> selectedObjects) {
        JMenu hideObjectsMenu = new JMenu("Hide objects");
        JMenuItem mi = buildMenuItem("Hide selected objects", ev -> presenter.onHideObjects(selectedObjects == null ? Set.of() : selectedObjects));
        mi.setEnabled(selectedObjects != null && !selectedObjects.isEmpty());
        hideObjectsMenu.add(mi);
        return hideObjectsMenu;
    }

    private JMenu buildHideLinksSubmenu(NewObjectDiagramPresenter presenter, Set<MLink> selectedLinks) {
        JMenu hideLinksMenu = new JMenu("Hide links");
        JMenuItem mi = buildMenuItem("Hide selected links", ev -> presenter.onHideLinks(selectedLinks == null ? Set.of() : selectedLinks));
        mi.setEnabled(selectedLinks != null && !selectedLinks.isEmpty());
        hideLinksMenu.add(mi);
        return hideLinksMenu;
    }

    private JMenuItem buildLinksByKindPlaceholder() {
        JMenuItem placeholder = new JMenuItem("Show/hide links-by-kind");
        placeholder.setEnabled(false);
        return placeholder;
    }

    private JMenuItem buildProtocolStatePlaceholder() {
        JMenuItem placeholder = new JMenuItem("Show protocol state machine...");
        placeholder.setEnabled(false);
        return placeholder;
    }

    private JMenuItem buildCommentNode(NewObjectDiagramPresenter presenter) {
        JMenuItem mi = buildMenuItem("Add comment node", ev -> presenter.onStatusMessage("Add comment node not implemented in presenter menu"));
        return mi;
    }
    private void addGreyItems(JComponent menu,
                              ObjectDiagramInteractor diagram,
                              NewObjectDiagramPresenter presenter,
                              Set<MObject> selectedObjects) {
        int greyed = 0;
        int visible = 0;
        for (MObject obj : selectedObjects) {
            if (diagram != null && diagram.isGreyed(obj)) {
                greyed++;
            } else {
                visible++;
            }
        }
        if (greyed > 0) {
            String labelGreyIn;
            if (selectedObjects.size() == 1) {
                labelGreyIn = "Grey in " + safeName(selectedObjects.iterator().next());
            } else {
                labelGreyIn = "Grey in selected";
                if (greyed > 1) {
                    labelGreyIn += " (" + greyed + ")";
                }
            }
            menu.add(buildMenuItem(labelGreyIn, ev -> presenter.onGrayIn(selectedObjects)));
        }
        if (visible > 0) {
            String labelGreyOut;
            if (selectedObjects.size() == 1) {
                labelGreyOut = "Grey out " + safeName(selectedObjects.iterator().next());
            } else {
                labelGreyOut = "Grey out selected";
                if (visible > 1) {
                    labelGreyOut += " (" + visible + ")";
                }
            }
            menu.add(buildMenuItem(labelGreyOut, ev -> presenter.onGrayOut(selectedObjects)));
        }
    }
    private String safeName(MObject obj) {
        try { return obj == null ? "" : obj.name(); } catch (Exception e) { return ""; }
    }
    /**
     * Remove leading/trailing separators and collapse consecutive separators.
     */
    private void normalizeSeparators(JPopupMenu popupMenu) {
        if (popupMenu == null) {
            return;
        }
        // Remove leading separators
        while (popupMenu.getComponentCount() > 0 && popupMenu.getComponent(0) instanceof JSeparator) {
            popupMenu.remove(0);
        }
        // Remove trailing separators
        while (popupMenu.getComponentCount() > 0 && popupMenu.getComponent(popupMenu.getComponentCount() - 1) instanceof JSeparator) {
            popupMenu.remove(popupMenu.getComponentCount() - 1);
        }
        // Collapse consecutive separators
        for (int i = 1; i < popupMenu.getComponentCount(); i++) {
            if (popupMenu.getComponent(i) instanceof JSeparator && popupMenu.getComponent(i - 1) instanceof JSeparator) {
                popupMenu.remove(i);
                i--; // stay at same index to check for further duplicates
            }
        }
    }
    /**
     * Adds a "Show hidden elements" menu entry if the diagram reports hidden nodes/edges.
     * Uses reflection to avoid requiring a direct API on ObjectDiagramInteractor.
     */
    private int addShowHiddenElementsIfNeeded(JPopupMenu popupMenu,
                                              ObjectDiagramInteractor diagram,
                                              NewObjectDiagramPresenter presenter,
                                              int pos) {
        if (diagram == null || presenter == null) {
            return pos;
        }
        try {
            Method m = diagram.getClass().getMethod("getHiddenData");
            Object hidden = m.invoke(diagram);
            if (hidden == null) {
                return pos;
            }
            Method getEdges = null;
            Method getNodes = null;
            try { getEdges = hidden.getClass().getMethod("getEdges"); } catch (NoSuchMethodException ignored) {}
            try { getNodes = hidden.getClass().getMethod("getNodes"); } catch (NoSuchMethodException ignored) {}
            boolean hasHidden = false;
            if (getEdges != null) {
                Object edges = getEdges.invoke(hidden);
                if (edges instanceof Collection) {
                    hasHidden = !((Collection) edges).isEmpty();
                }
            }
            if (!hasHidden && getNodes != null) {
                Object nodes = getNodes.invoke(hidden);
                if (nodes instanceof Collection) {
                    hasHidden = !((Collection) nodes).isEmpty();
                }
            }
            if (hasHidden) {
                JMenuItem showAllObjects = new JMenuItem("Show hidden elements");
                showAllObjects.addActionListener(ev -> {
                    try { presenter.onShowHiddenElements(); } catch (Throwable ignored) {}
                });
                popupMenu.insert(showAllObjects, pos++);
            }
        } catch (Throwable ignored) {
            // reflection failed -> silently skip
        }
        return pos;
    }
    // helper: radix conversion for enumerating assignments
    private int[] radixConversion(int value, int radix, int length) {
        int[] digits = new int[length];
        for (int i = 0; i < length; i++) {
            digits[i] = value % radix;
            value /= radix;
        }
        return digits;
    }
    // helper: ensure that every selected object index [0..m-1] appears at least once in digits
    private boolean isCompleteObjectCombination(int[] digits, int m) {
        boolean[] used = new boolean[m];
        int usedCount = 0;
        for (int d : digits) {
            if (!used[d]) {
                used[d] = true;
                usedCount++;
            }
        }
        return usedCount == m;
    }
    // helper: check whether a link connects exactly the objects in l (multiset match)
    private boolean matchesLink(MLink link, MObject[] l) {
        if (link == null) return false;
        java.util.List<MObject> linked = new ArrayList<>();
        link.linkedObjects().forEach(linked::add);
        if (linked.size() != l.length) return false;
        for (MObject o : l) {
            boolean removed = linked.remove(o);
            if (!removed) return false;
        }
        return linked.isEmpty();
    }

    // BoxMenuElement removed: use plain JMenuItems so hide/crop/grey appear like insert entries
}
