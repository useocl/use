package org.tzi.use.gui.views.diagrams.objectdiagram;

import org.tzi.use.gui.views.diagrams.util.MenuScroller;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;

import javax.swing.*;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DefaultContextMenuProvider implements ContextMenuProvider {

    private static final int INSERT_SLOT_COUNT = 3;

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

        List<Component> baseItems = new ArrayList<>(Arrays.asList(popupMenu.getComponents()));
        popupMenu.removeAll();

        Set<MObject> safeObjects = selectedObjects == null ? Set.of() : selectedObjects;
        Set<MLink> safeLinks = selectedLinks == null ? Set.of() : selectedLinks;

        List<Component> ordered = new ArrayList<>();

        addComponent(ordered, buildEditPropertiesItem(presenter, safeObjects));
        addComponent(ordered, buildDestroyItem(presenter, safeObjects));
        addSeparator(ordered);

        addComponents(ordered, buildInsertItems(presenter, safeObjects));
        addSeparator(ordered);

        addComponent(ordered, buildHideSelectedItem(presenter, safeObjects));
        addComponent(ordered, buildCropSelectedItem(presenter, safeObjects));
        addComponent(ordered, buildGreyOutItem(presenter, safeObjects));
        addSeparator(ordered);

        addComponent(ordered, buildShowHideCropMenu(presenter, diagram, safeObjects, safeLinks));
        addComponent(ordered, buildHideObjectsMenu(presenter, safeObjects));
        addComponent(ordered, buildHideLinksMenu(presenter, safeLinks));
        addSeparator(ordered);
        addComponent(ordered, buildLinksByKindMenu(diagram));
        addSeparator(ordered);

        addComponent(ordered, buildProtocolStateMachineItem(presenter));
        addSeparator(ordered);

        addComponent(ordered, extractOrFallback(baseItems, "Add comment node", () -> buildCommentNode(presenter)));
        addSeparator(ordered);

        addComponent(ordered, extractOrFallback(baseItems, "Show attributes", () -> buildStatusItem("Show attributes", presenter)));
        addComponent(ordered, buildShowStatesToggle(diagram, presenter));
        addComponent(ordered, extractOrFallback(baseItems, "Show association names", () -> buildStatusItem("Show association names", presenter)));
        addComponent(ordered, extractOrFallback(baseItems, "Show role names", () -> buildStatusItem("Show role names", presenter)));
        addSeparator(ordered);
        addComponent(ordered, extractOrFallback(baseItems, "Align elements", () -> new JMenu("Align elements")));
        addComponent(ordered, extractOrFallback(baseItems, "Anti-aliasing", () -> buildStatusItem("Anti-aliasing", presenter)));
        addComponent(ordered, extractOrFallback(baseItems, "Show grid", () -> buildStatusItem("Show grid", presenter)));
        addComponent(ordered, extractOrFallback(baseItems, "Grayscale view", () -> buildStatusItem("Grayscale view", presenter)));
        addSeparator(ordered);
        addComponent(ordered, extractOrFallback(baseItems, "Auto-Layout", () -> buildStatusItem("Auto-Layout", presenter)));
        addComponent(ordered, extractOrFallback(baseItems, "Layouts", () -> new JMenu("Layouts")));
        addComponent(ordered, extractOrFallback(baseItems, "Reset layout", () -> buildStatusItem("Reset layout", presenter)));
        addComponent(ordered, extractOrFallback(baseItems, "Load layout...", () -> buildStatusItem("Load layout...", presenter)));
        addComponent(ordered, extractOrFallback(baseItems, "Save layout...", () -> buildStatusItem("Save layout...", presenter)));
        addSeparator(ordered);
        addComponent(ordered, extractOrFallback(baseItems, "Select all nodes", () -> buildStatusItem("Select all nodes", presenter)));
        addComponent(ordered, extractOrFallback(baseItems, "Select all edges", () -> buildStatusItem("Select all edges", presenter)));

        ordered.stream().filter(Objects::nonNull).forEach(popupMenu::add);
    }

    private void addComponent(List<Component> target, Component component) {
        if (component != null) {
            target.add(component);
        }
    }

    private void addComponents(List<Component> target, List<? extends Component> components) {
        components.stream().filter(Objects::nonNull).forEach(target::add);
    }

    private void addSeparator(List<Component> target) {
        if (target.isEmpty() || target.get(target.size() - 1) instanceof JSeparator) {
            return;
        }
        target.add(new JSeparator());
    }

    private Component extractOrFallback(List<Component> baseItems, String text, Supplier<Component> fallback) {
        Component extracted = takeAndRemove(baseItems, text);
        if (extracted != null) {
            return extracted;
        }
        return fallback == null ? null : fallback.get();
    }

    private Component takeAndRemove(List<Component> components, String text) {
        for (Iterator<Component> it = components.iterator(); it.hasNext(); ) {
            Component candidate = it.next();
            if (candidate instanceof JMenuItem item && text.equals(item.getText())) {
                it.remove();
                return item;
            }
            if (candidate instanceof JMenu menu && text.equals(menu.getText())) {
                it.remove();
                return menu;
            }
        }
        return null;
    }

    private JMenuItem buildEditPropertiesItem(NewObjectDiagramPresenter presenter, Set<MObject> selectedObjects) {
        return buildMenuItem("Edit properties of '" + safeName(selectedObjects.iterator().next()) + "'" , ev -> {
            if (selectedObjects.isEmpty()) {
                presenter.onStatusMessage("Select an object to edit its properties.");
            } else {
                presenter.onShowObjectProperties(selectedObjects.iterator().next());
            }
        });
    }

    private JMenuItem buildDestroyItem(NewObjectDiagramPresenter presenter, Set<MObject> selectedObjects) {
        return buildMenuItem("Destroy '" + safeName(selectedObjects.iterator().next()) + "'", ev -> {
            if (selectedObjects.isEmpty()) {
                presenter.onStatusMessage("Select at least one object to destroy.");
            } else {
                presenter.onDeleteObjects(selectedObjects);
            }
        });
    }

    private List<JMenuItem> buildInsertItems(NewObjectDiagramPresenter presenter, Set<MObject> selectedObjects) {
        List<JMenuItem> items = new ArrayList<>();
        if (presenter == null) {
            return items;
        }

        if (!selectedObjects.isEmpty()) {
            List<MObject> selList = new ArrayList<>(selectedObjects);
            int m = selList.size();
            Collection<MAssociation> associations = presenter.fetchAllAssociations();
            if (associations == null) {
                return items;
            }
            for (MAssociation assoc : associations) {
                if (assoc.isReadOnly()) {
                    continue;
                }
                List<MAssociationEnd> ends = assoc.associationEnds();
                int n = ends == null ? 0 : ends.size();
                if (n == 0 || m > n) {
                    continue;
                }
                int pow = 1;
                for (int i = 0; i < n; i++) {
                    pow *= m;
                }
                for (int value = 0; value < pow; value++) {
                    int[] digits = radixConversion(value, m, n);
                    if (!isCompleteObjectCombination(digits, m)) {
                        continue;
                    }
                    MObject[] tuple = new MObject[n];
                    MClass[] types = new MClass[n];
                    for (int idx = 0; idx < n; idx++) {
                        tuple[idx] = selList.get(digits[idx]);
                        types[idx] = tuple[idx].cls();
                    }
                    if (!assoc.isAssignableFrom(types)) {
                        continue;
                    }
                    String joined = Arrays.stream(tuple).map(this::safeName).reduce((a, b) -> a + "," + b).orElse("...");
                    items.add(buildMenuItem("insert (" + joined + ") into " + assoc.name(),
                            ev -> presenter.onInsertLink(assoc, Arrays.asList(tuple))));
                    if (items.size() == INSERT_SLOT_COUNT) {
                        return items;
                    }
                }
            }
        }

        Collection<MAssociation> associations = presenter.fetchAllAssociations();
        if (associations != null) {
            for (MAssociation assoc : associations) {
                items.add(buildMenuItem("insert (...) into " + assoc.name(),
                        ev -> presenter.onInsertLinkInteractive(selectedObjects)));
                if (items.size() == INSERT_SLOT_COUNT) {
                    return items;
                }
            }
        }

        while (items.size() < INSERT_SLOT_COUNT) {
            items.add(buildMenuItem("insert (...) into ...",
                    ev -> presenter.onInsertLinkInteractive(selectedObjects)));
        }
        return items;
    }

    private JMenuItem buildHideSelectedItem(NewObjectDiagramPresenter presenter, Set<MObject> selectedObjects) {
        return buildMenuItem("Hide " + describeSelection(selectedObjects),
                ev -> hideOrWarn(presenter, selectedObjects, objs -> presenter.onHideObjects(objs)));
    }

    private JMenuItem buildCropSelectedItem(NewObjectDiagramPresenter presenter, Set<MObject> selectedObjects) {
        return buildMenuItem("Crop " + describeSelection(selectedObjects), ev -> {
            if (selectedObjects.isEmpty()) {
                presenter.onStatusMessage("Select objects before cropping.");
            } else {
                presenter.onCropSelection(new HashSet<>(selectedObjects));
            }
        });
    }

    private JMenuItem buildGreyOutItem(NewObjectDiagramPresenter presenter, Set<MObject> selectedObjects) {
        return buildMenuItem("Grey out " + describeSelection(selectedObjects),
                ev -> hideOrWarn(presenter, selectedObjects, objs -> presenter.onGrayOut(objs)));
    }

    private JMenu buildShowHideCropMenu(NewObjectDiagramPresenter presenter,
                                        ObjectDiagramInteractor diagram,
                                        Set<MObject> selectedObjects,
                                        Set<MLink> selectedLinks) {
        JMenu menu = new JMenu("Show/hide/crop objects");
        menu.add(buildMenuItem("Show hidden elements", ev -> presenter.onShowHiddenElements()));
        menu.add(buildMenuItem("Hide selected objects", ev -> hideOrWarn(presenter, selectedObjects, objs -> presenter.onHideObjects(objs))));
        menu.add(buildMenuItem("Crop selection", ev -> {
            if (selectedObjects.isEmpty() && selectedLinks.isEmpty()) {
                presenter.onStatusMessage("Select objects or links to crop.");
                return;
            }
            Set<MObject> targets = new HashSet<>(selectedObjects);
            selectedLinks.forEach(link -> targets.addAll(link.linkedObjects()));
            presenter.onCropSelection(targets);
        }));
        menu.add(buildMenuItem("Grey out selection", ev -> hideOrWarn(presenter, selectedObjects, objs -> presenter.onGrayOut(objs))));
        menu.add(buildMenuItem("Grey in selection", ev -> hideOrWarn(presenter, selectedObjects, objs -> presenter.onGrayIn(objs))));
        if (diagram instanceof NewObjectDiagram newDiagram) {
            menu.addSeparator();
            menu.add(buildMenuItem("Show all objects", ev -> newDiagram.showAll()));
            menu.add(buildMenuItem("Hide all objects", ev -> newDiagram.hideAll()));
        }
        return menu;
    }

    private JMenu buildHideObjectsMenu(NewObjectDiagramPresenter presenter, Set<MObject> selectedObjects) {
        JMenu menu = new JMenu("Hide objects");
        menu.add(buildMenuItem("Hide selected objects", ev -> hideOrWarn(presenter, selectedObjects, objs -> presenter.onHideObjects(objs))));
        menu.add(buildMenuItem("Show hidden objects", ev -> presenter.onShowHiddenElements()));
        return menu;
    }

    private JMenu buildHideLinksMenu(NewObjectDiagramPresenter presenter, Set<MLink> selectedLinks) {
        JMenu menu = new JMenu("Hide links");
        menu.add(buildMenuItem("Hide selected links", ev -> {
            if (selectedLinks.isEmpty()) {
                presenter.onStatusMessage("Select links to hide.");
            } else {
                presenter.onHideLinks(selectedLinks);
            }
        }));
        menu.add(buildMenuItem("Show all links", ev -> presenter.onShowAllLinks()));
        menu.add(buildMenuItem("Hide all links", ev -> presenter.onHideAllLinks()));
        return menu;
    }

    private JMenu buildLinksByKindMenu(ObjectDiagramInteractor diagram) {
        JMenu menu = new JMenu("Show/hide links-by-kind");
        if (!(diagram instanceof NewObjectDiagram newDiagram)) {
            menu.add(buildMenuItem("Not available", ev -> {}));
            return menu;
        }

        TreeMap<String, List<MLink>> grouped = newDiagram.mapLinksToKindOfAssociation();
        if (grouped.isEmpty()) {
            menu.add(buildMenuItem("No associations", ev -> {}));
            return menu;
        }

        MenuScroller.setScrollerFor(menu, 15, 125, 0, 0);
        menu.add(buildMenuItem("Show all links", ev -> {
            newDiagram.showAllLinks();
            newDiagram.repaint();
        }));
        menu.add(buildMenuItem("Hide all links", ev -> {
            newDiagram.hideAllLinks();
            newDiagram.repaint();
        }));
        menu.addSeparator();

        for (Map.Entry<String, List<MLink>> entry : grouped.entrySet()) {
            JMenu assocMenu = new JMenu(entry.getKey());
            List<MLink> links = entry.getValue();
            assocMenu.add(buildMenuItem("Show all", ev -> {
                newDiagram.showLink(links);
                newDiagram.repaint();
            }));
            assocMenu.add(buildMenuItem("Hide all", ev -> {
                newDiagram.hideLink(links);
                newDiagram.repaint();
            }));
            assocMenu.addSeparator();
            for (MLink link : links) {
                assocMenu.add(buildMenuItem(formatLinkLabel("Show", link), ev -> {
                    newDiagram.showLink(link);
                    newDiagram.repaint();
                }));
                assocMenu.add(buildMenuItem(formatLinkLabel("Hide", link), ev -> {
                    newDiagram.hideLink(link);
                    newDiagram.repaint();
                }));
            }
            menu.add(assocMenu);
        }
        return menu;
    }

    private String formatLinkLabel(String prefix, MLink link) {
        return prefix + " " + link.toString();
    }

    private JMenuItem buildProtocolStateMachineItem(NewObjectDiagramPresenter presenter) {
        return buildMenuItem("Show protocol state machine...", ev -> presenter.onStatusMessage("Protocol state machine view not implemented yet."));
    }

    private JMenuItem buildCommentNode(NewObjectDiagramPresenter presenter) {
        return buildMenuItem("Add comment node", ev -> presenter.onStatusMessage("Add comment node not implemented in presenter menu"));
    }

    private JMenuItem buildShowStatesToggle(ObjectDiagramInteractor diagram, NewObjectDiagramPresenter presenter) {
        boolean initial = diagram != null && diagram.isShowStates();
        JCheckBoxMenuItem item = new JCheckBoxMenuItem("Show states", initial);
        item.addItemListener(ev -> {
            boolean selected = ev.getStateChange() == ItemEvent.SELECTED;
            presenter.onToggleShowStates(selected);
            if (diagram != null) {
                diagram.setShowStates(selected);
                diagram.invalidateContent(true);
            }
        });
        return item;
    }

    private JMenuItem buildStatusItem(String label, NewObjectDiagramPresenter presenter) {
        return buildMenuItem(label, ev -> presenter.onStatusMessage(label + " is managed by the base view."));
    }

    private JMenuItem buildMenuItem(String label, ActionListener listener) {
        JMenuItem item = new JMenuItem(label);
        item.addActionListener(listener);
        return item;
    }

    private String safeName(MObject obj) {
        try {
            return obj == null ? "" : obj.name();
        } catch (Exception e) {
            return "";
        }
    }

    private String describeSelection(Set<MObject> selectedObjects) {
        if (selectedObjects.isEmpty()) {
            return "<object>";
        }
        if (selectedObjects.size() == 1) {
            return safeName(selectedObjects.iterator().next());
        }
        return selectedObjects.size() + " objects";
    }

    private void hideOrWarn(NewObjectDiagramPresenter presenter,
                             Set<MObject> selectedObjects,
                             Consumer<Set<MObject>> action) {
        if (selectedObjects.isEmpty()) {
            presenter.onStatusMessage("Select at least one object to perform this action.");
            return;
        }
        action.accept(selectedObjects);
    }

    private int[] radixConversion(int value, int radix, int length) {
        int[] digits = new int[length];
        for (int i = 0; i < length; i++) {
            digits[i] = value % radix;
            value /= radix;
        }
        return digits;
    }

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
}