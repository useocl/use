package org.tzi.use.gui.views.diagrams.objectdiagram;

import org.tzi.use.gui.views.diagrams.elements.CommentNode;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.util.MenuScroller;
import org.tzi.use.gui.views.diagrams.ObjectNodeActivity;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MAssociationClassImpl;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MNamedElementComparator;
import org.tzi.use.uml.mm.statemachines.MProtocolStateMachine;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.util.collections.CollectionUtil;
import org.tzi.use.gui.views.selection.objectselection.ObjectSelection;

import javax.swing.*;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DefaultContextMenuProvider implements ContextMenuProvider {

    @Override
    public void enhanceMenu(JPopupMenu popupMenu,
                            ObjectDiagramInteractor diagram,
                            NewObjectDiagramPresenter presenter,
                            Point popupPosition,
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
        Set<MObject> safeAssocObjects = selectedAssocObjects == null ? Set.of() : selectedAssocObjects;
        boolean hasSelectedObjects = !safeObjects.isEmpty();
        boolean hasSelectedLinks = !safeLinks.isEmpty();

        List<Component> ordered = new ArrayList<>();

        if (hasSelectedObjects) {
            addComponent(ordered, buildEditPropertiesItem(presenter, safeObjects));
            addComponent(ordered, buildDestroyItem(presenter, safeObjects));
            addSeparator(ordered);
        }

        if (hasSelectedObjects) {
            addComponents(ordered, buildInsertItems(presenter, safeObjects));
            addSeparator(ordered);
        }

        if (hasSelectedObjects) {
            addComponent(ordered, buildHideSelectedItem(presenter, safeObjects));
            addComponent(ordered, buildCropSelectedItem(presenter, safeObjects));
            addComponent(ordered, buildGreyOutItem(presenter, safeObjects));
            addSeparator(ordered);
        }

        if (hasSelectedLinks) {
            addComponent(ordered, buildHideSelectedLinksItem(diagram, presenter, safeLinks));
            addComponent(ordered, buildCropSelectedLinksItem(diagram, presenter, safeLinks, safeAssocObjects));
            addSeparator(ordered);
        }

        addComponent(ordered, buildShowHideCropMenu(diagram, presenter, safeObjects));
        addComponent(ordered, buildHideObjectsMenu(diagram, presenter));
        addComponent(ordered, buildHideLinksMenu(diagram, presenter));
        addComponent(ordered, buildShowObjectsMenu(diagram, presenter));
        addComponent(ordered, buildShowLinksMenu(diagram, presenter));
        addSeparator(ordered);
        addComponent(ordered, buildLinksByKindMenu(diagram, presenter));
        addSeparator(ordered);

        addComponent(ordered, buildProtocolStateMachineMenu(presenter, safeObjects));
        addSeparator(ordered);

        addComponent(ordered, buildCommentNode(diagram, presenter, popupPosition));
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
        if (target.isEmpty() || target.getLast() instanceof JSeparator) {
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
        MObject first = selectedObjects == null || selectedObjects.isEmpty() ? null : selectedObjects.iterator().next();
        return buildMenuItem("Edit properties of '" + safeName(first) + "'", ev -> {
            if (first == null) {
                presenter.onStatusMessage("Select an object to edit its properties.");
            } else {
                presenter.onShowObjectProperties(first);
            }
        });
    }

    private JMenuItem buildDestroyItem(NewObjectDiagramPresenter presenter, Set<MObject> selectedObjects) {
        MObject first = selectedObjects == null || selectedObjects.isEmpty() ? null : selectedObjects.iterator().next();
        return buildMenuItem("Destroy '" + safeName(first) + "'", ev -> {
            if (selectedObjects == null || selectedObjects.isEmpty()) {
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

        Collection<MAssociation> assocCollection = presenter.fetchAllAssociations();
        List<MAssociation> associations = assocCollection == null ? new ArrayList<>() : new ArrayList<>(assocCollection);
        associations.removeIf(Objects::isNull);
        associations.sort(Comparator.comparing(
                MAssociation::name,
                Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)));

        if (selectedObjects == null || selectedObjects.isEmpty() || associations.isEmpty()) {
            return items;
        }

        List<MObject> selList = new ArrayList<>(selectedObjects);
        selList.removeIf(Objects::isNull);
        selList.sort(Comparator.comparing(this::safeName, String.CASE_INSENSITIVE_ORDER));
        int m = selList.size();
        if (m == 0) {
            return items;
        }

        for (MAssociation assoc : associations) {
            if (assoc == null || assoc.isReadOnly()) {
                continue;
            }

            List<MAssociationEnd> ends = assoc.associationEnds();
            int n = ends == null ? 0 : ends.size();
            if (n == 0) {
                continue;
            }

            Collection<MLink> associationLinks = presenter.fetchLinksOfAssociation(assoc);
            if (associationLinks == null) {
                associationLinks = List.of();
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

                StringBuilder joined = new StringBuilder();
                for (int idx = 0; idx < tuple.length; idx++) {
                    if (idx > 0) {
                        joined.append(',');
                    }
                    joined.append(safeName(tuple[idx]));
                }

                List<MLink> matchingLinks = new ArrayList<>();
                for (MLink link : associationLinks) {
                    List<MObject> linkObjects = link.linkedObjects();
                    if (linkObjects == null || linkObjects.size() != tuple.length) {
                        continue;
                    }

                    boolean sameTuple = true;
                    for (int idx = 0; idx < tuple.length; idx++) {
                        if (linkObjects.get(idx) != tuple[idx]) {
                            sameTuple = false;
                            break;
                        }
                    }

                    if (sameTuple) {
                        matchingLinks.add(link);
                    }
                }

                if (matchingLinks.isEmpty()) {
                    List<MObject> tupleList = Arrays.asList(tuple.clone());
                    items.add(buildMenuItem("insert (" + joined + ") into " + assoc.name(),
                            ev -> presenter.onInsertLink(assoc, tupleList)));
                } else {
                    for (MLink link : matchingLinks) {
                        StringBuilder deleteLabel = new StringBuilder("delete (");
                        List<List<?>> qualifiers = castQualifiers(link.getQualifier());
                        for (int idx = 0; idx < tuple.length; idx++) {
                            if (idx > 0) {
                                deleteLabel.append(',');
                            }
                            deleteLabel.append(safeName(tuple[idx]));
                            if (qualifiers != null && idx < qualifiers.size()) {
                                List<?> qualifierForEnd = qualifiers.get(idx);
                                if (qualifierForEnd != null && !qualifierForEnd.isEmpty()) {
                                    deleteLabel.append('{');
                                    for (int q = 0; q < qualifierForEnd.size(); q++) {
                                        if (q > 0) {
                                            deleteLabel.append(',');
                                        }
                                        deleteLabel.append(qualifierForEnd.get(q));
                                    }
                                    deleteLabel.append('}');
                                }
                            }
                        }
                        deleteLabel.append(") from ").append(assoc.name());
                        items.add(buildMenuItem(deleteLabel.toString(), ev -> presenter.onDeleteLink(link)));
                    }
                }
            }
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

    private JMenu buildHideObjectsMenu(ObjectDiagramInteractor diagram, NewObjectDiagramPresenter presenter) {
        JMenu menu = new JMenu("Hide objects");
        MenuScroller.setScrollerFor(menu, 15, 125, 0, 0);

        if (!(diagram instanceof NewObjectDiagram newDiagram)) {
            return menu;
        }

        Set<MObject> visibleObjects = new LinkedHashSet<>(newDiagram.getVisibleData().getObjectToNodeMap().keySet());
        if (visibleObjects.isEmpty()) {
            return null;
        }

        JMenuItem hideAllObjects = new JMenuItem("Hide all objects");
        hideAllObjects.setEnabled(!visibleObjects.isEmpty());
        hideAllObjects.addActionListener(ev -> presenter.onHideObjects(new LinkedHashSet<>(visibleObjects)));
        menu.add(hideAllObjects);
        menu.addSeparator();

        Map<MClass, List<MObject>> objectsByClass = new HashMap<>();
        for (MObject obj : visibleObjects) {
            objectsByClass.computeIfAbsent(obj.cls(), key -> new ArrayList<>()).add(obj);
        }

        List<MClass> classes = new ArrayList<>(objectsByClass.keySet());
        classes.sort(new MNamedElementComparator());

        for (MClass cls : classes) {
            JMenu classMenu = new JMenu("Class " + cls.name());
            MenuScroller.setScrollerFor(classMenu, 15, 125, 0, 0);

            List<MObject> classObjects = new ArrayList<>(objectsByClass.getOrDefault(cls, List.of()));
            classObjects.sort(Comparator.comparing(this::safeName, String.CASE_INSENSITIVE_ORDER));

            JMenuItem hideClassAll = new JMenuItem("Hide all");
            hideClassAll.addActionListener(ev -> presenter.onHideObjects(new LinkedHashSet<>(classObjects)));
            classMenu.add(hideClassAll);
            classMenu.addSeparator();

            for (MObject obj : classObjects) {
                classMenu.add(buildMenuItem("Hide " + obj.name(), ev -> presenter.onHideObjects(Set.of(obj))));
            }

            menu.add(classMenu);
        }

        return menu;
    }

    private JMenu buildHideLinksMenu(ObjectDiagramInteractor diagram, NewObjectDiagramPresenter presenter) {
        JMenu menu = new JMenu("Hide links");
        MenuScroller.setScrollerFor(menu, 20, 125, 0, 0);

        if (!(diagram instanceof NewObjectDiagram newDiagram)) {
            return null;
        }

        List<MLink> links = collectUniqueLinks(new LinkedHashSet<>(newDiagram.getVisibleData().getEdges()));
        if (links.isEmpty()) {
            return null;
        }

        JMenuItem hideAllLinks = new JMenuItem("Hide all links");
        hideAllLinks.setEnabled(!newDiagram.getVisibleData().getEdges().isEmpty());
        hideAllLinks.addActionListener(ev -> presenter.onHideAllLinks());
        menu.add(hideAllLinks);
        menu.addSeparator();

        Map<String, List<MLink>> grouped = groupLinksByAssociation(links);
        for (Map.Entry<String, List<MLink>> entry : grouped.entrySet()) {
            JMenu assocMenu = new JMenu(entry.getKey());
            MenuScroller.setScrollerFor(assocMenu, 20, 125, 0, 0);
            List<MLink> assocLinks = entry.getValue();
            assocMenu.add(buildMenuItem("Hide all", ev -> presenter.onHideLinks(assocLinks)));
            assocMenu.addSeparator();
            assocLinks.sort(linkComparator());
            for (MLink link : assocLinks) {
                assocMenu.add(buildMenuItem("Hide " + formatLinkName(link), ev -> presenter.onHideLinks(List.of(link))));
            }
            menu.add(assocMenu);
        }
        return menu;
    }

    private JMenu buildShowLinksMenu(ObjectDiagramInteractor diagram, NewObjectDiagramPresenter presenter) {
        JMenu menu = new JMenu("Show links");
        MenuScroller.setScrollerFor(menu, 20, 125, 0, 0);

        if (!(diagram instanceof NewObjectDiagram newDiagram)) {
            return null;
        }

        List<MLink> links = collectUniqueLinks(new LinkedHashSet<>(newDiagram.getHiddenData().getEdges()));
        if (links.isEmpty()) {
            return null;
        }

        JMenuItem showAllLinks = new JMenuItem("Show all links");
        showAllLinks.setEnabled(!newDiagram.getHiddenData().getEdges().isEmpty());
        showAllLinks.addActionListener(ev -> presenter.onShowAllLinks());
        menu.add(showAllLinks);
        menu.addSeparator();

        Map<String, List<MLink>> grouped = groupLinksByAssociation(links);
        for (Map.Entry<String, List<MLink>> entry : grouped.entrySet()) {
            JMenu assocMenu = new JMenu(entry.getKey());
            MenuScroller.setScrollerFor(assocMenu, 20, 125, 0, 0);
            List<MLink> assocLinks = entry.getValue();
            assocMenu.add(buildMenuItem("Show all", ev -> presenter.onShowLinks(assocLinks)));
            assocMenu.addSeparator();
            assocLinks.sort(linkComparator());
            for (MLink link : assocLinks) {
                assocMenu.add(buildMenuItem("Show " + formatLinkName(link), ev -> presenter.onShowLinks(List.of(link))));
            }
            menu.add(assocMenu);
        }

        return menu;
    }

    private JMenu buildShowObjectsMenu(ObjectDiagramInteractor diagram, NewObjectDiagramPresenter presenter) {
        JMenu menu = new JMenu("Show objects");
        MenuScroller.setScrollerFor(menu, 15, 125, 0, 0);

        if (!(diagram instanceof NewObjectDiagram newDiagram)) {
            return null;
        }

        Set<? extends PlaceableNode> hiddenNodes = newDiagram.getHiddenNodes();
        Set<ObjectNodeActivity> hiddenObjectNodes = CollectionUtil.filterByType(hiddenNodes, ObjectNodeActivity.class);
        if (hiddenObjectNodes.isEmpty()) {
            return null;
        }

        JMenuItem showAllObjects = new JMenuItem("Show all hidden objects");
        showAllObjects.setEnabled(true);
        showAllObjects.addActionListener(ev -> presenter.onShowHiddenElements());
        menu.add(showAllObjects);
        menu.addSeparator();

        TreeSet<ObjectNodeActivity> sortedNodes = new TreeSet<>(Comparator.comparing((ObjectNodeActivity n) -> n.object().name(), new org.tzi.use.gui.util.AlphanumComparator()));
        sortedNodes.addAll(hiddenObjectNodes);

        Map<MClass, JMenu> classMenus = new HashMap<>();
        for (ObjectNodeActivity node : sortedNodes) {
            MClass cls = node.cls();
            MObject obj = node.object();

            if (!classMenus.containsKey(cls)) {
                JMenu subm = new JMenu("Class " + cls.name());
                MenuScroller.setScrollerFor(subm, 15, 125, 0, 0);
                classMenus.put(cls, subm);

                JMenuItem showAll = new JMenuItem("Show all");
                showAll.addActionListener(e -> presenter.onShowHiddenElements());
                subm.add(showAll);
                subm.addSeparator();
            }

            JMenu parent = classMenus.get(cls);
            parent.add(buildMenuItem("Show " + obj.name(), ev -> presenter.onShowHiddenElements()));
        }

        List<MClass> classes = new ArrayList<>(classMenus.keySet());
        classes.sort(new MNamedElementComparator());
        for (MClass cls : classes) {
            menu.add(classMenus.get(cls));
        }

        return menu;
    }

    private JMenu buildShowHideCropMenu(ObjectDiagramInteractor diagram,
                                        NewObjectDiagramPresenter presenter,
                                        Set<MObject> selectedObjects) {
        JMenu menu = new JMenu("Show/hide/crop objects");
        if (presenter == null) {
            return menu;
        }

        ObjectSelection selection = diagram != null ? diagram.getObjectSelection() : null;
        presenter.onBuildShowHideCropMenu(new ShowHideCropMenuBuilder() {
            @Override
            public void addSelectedObjectPathView(Set<MObject> selectedObjects) {
                if (selection != null && selectedObjects != null && !selectedObjects.isEmpty()) {
                    menu.add(selection.getSelectedObjectPathView("By path length...", selectedObjects));
                }
            }

            @Override
            public void addSelectionWithOCLViewAction() {
                if (selection != null) {
                    menu.add(selection.getSelectionWithOCLViewAction());
                }
            }

            @Override
            public void addSelectionObjectView() {
                if (selection != null) {
                    menu.add(selection.getSelectionObjectView());
                }
            }
        }, selectedObjects);
        return menu;
    }

    private JMenuItem buildHideSelectedLinksItem(ObjectDiagramInteractor diagram,
                                                 NewObjectDiagramPresenter presenter,
                                                 Set<MLink> selectedLinks) {
        List<MLink> links = sortedSelectedLinks(selectedLinks);
        if (presenter == null || links.isEmpty()) {
            return null;
        }
        String label = links.size() == 1 ? formatLinkName(links.getFirst()) : links.size() + " links";
        return buildMenuItem("Hide " + label, ev -> presenter.onHideLinks(links));
    }

    private JMenuItem buildCropSelectedLinksItem(ObjectDiagramInteractor diagram,
                                                 NewObjectDiagramPresenter presenter,
                                                 Set<MLink> selectedLinks,
                                                 Set<MObject> selectedAssocObjects) {
        List<MLink> links = sortedSelectedLinks(selectedLinks);
        if (presenter == null || links.isEmpty() || !(diagram instanceof NewObjectDiagram newDiagram)) {
            return null;
        }
        String label = links.size() == 1 ? formatLinkName(links.getFirst()) : links.size() + " links";
        Set<MObject> keepObjects = selectedAssocObjects == null ? Set.of() : new LinkedHashSet<>(selectedAssocObjects);
        return buildMenuItem("Crop " + label, ev -> cropSelectedLinks(presenter, newDiagram, links, keepObjects));
    }

    private void cropSelectedLinks(NewObjectDiagramPresenter presenter,
                                   NewObjectDiagram newDiagram,
                                   Collection<MLink> selectedLinks,
                                   Set<MObject> keepObjects) {
        Set<MLink> selected = new LinkedHashSet<>(selectedLinks);
        Set<MObject> visibleObjects = new LinkedHashSet<>(newDiagram.getVisibleData().getObjectToNodeMap().keySet());
        visibleObjects.removeAll(keepObjects);

        List<MLink> visibleLinks = collectUniqueLinks(new LinkedHashSet<>(newDiagram.getVisibleData().getEdges()));
        visibleLinks.removeIf(selected::contains);

        if (!visibleObjects.isEmpty()) {
            presenter.onHideObjects(visibleObjects);
        }
        if (!visibleLinks.isEmpty()) {
            presenter.onHideLinks(visibleLinks);
        }
    }

    private List<MLink> sortedSelectedLinks(Set<MLink> selectedLinks) {
        List<MLink> links = new ArrayList<>(selectedLinks == null ? Set.of() : selectedLinks);
        links.removeIf(Objects::isNull);
        links.sort(linkComparator());
        return links;
    }

    private JMenu buildLinksByKindMenu(ObjectDiagramInteractor diagram, NewObjectDiagramPresenter presenter) {
        JMenu menu = new JMenu("Show/hide links-by-kind");
        if (!(diagram instanceof NewObjectDiagram newDiagram) || presenter == null) {
            return null;
        }

        TreeMap<String, List<MLink>> grouped = mapLinksToKindOfAssociation(presenter);
        if (grouped.isEmpty()) {
            return null;
        }

        MenuScroller.setScrollerFor(menu, 15, 125, 0, 0);
        List<MLink> allLinks = grouped.values().stream().flatMap(Collection::stream).distinct().toList();
        int linkState = newDiagram.isHidden(allLinks);
        if (linkState == 1 || linkState == 2) {
            menu.add(buildMenuItem("Show all links", ev -> presenter.onShowAllLinks()));
        }
        if (linkState == 0 || linkState == 2) {
            menu.add(buildMenuItem("Hide all links", ev -> presenter.onHideAllLinks()));
        }

        menu.addSeparator();

        for (Map.Entry<String, List<MLink>> entry : grouped.entrySet()) {
            List<MLink> links = entry.getValue();
            if (links == null || links.isEmpty()) {
                continue;
            }

            JMenu assocMenu = new JMenu(entry.getKey());
            List<MLink> assocLinks = new ArrayList<>(links);
            int assocLinkState = newDiagram.isHidden(assocLinks);
            if (assocLinkState == 1 || assocLinkState == 2) {
                assocMenu.add(buildMenuItem("Show all", ev -> presenter.onShowLinks(assocLinks)));
            }
            if (assocLinkState == 0 || assocLinkState == 2) {
                assocMenu.add(buildMenuItem("Hide all", ev -> presenter.onHideLinks(assocLinks)));
            }
            assocMenu.addSeparator();
            List<MLink> sortedLinks = new ArrayList<>(links);
            sortedLinks.sort(linkComparator());
            for (MLink link : sortedLinks) {
                if (newDiagram.isHidden(link)) {
                    assocMenu.add(buildMenuItem("Show " + formatLinkName(link), ev -> presenter.onShowLinks(List.of(link))));
                } else {
                    assocMenu.add(buildMenuItem("Hide " + formatLinkName(link), ev -> presenter.onHideLinks(List.of(link))));
                }
            }
            menu.add(assocMenu);
        }
        return menu;
    }

    private TreeMap<String, List<MLink>> mapLinksToKindOfAssociation(NewObjectDiagramPresenter presenter) {
        TreeMap<String, List<MLink>> assocs = new TreeMap<>();
        Collection<MAssociation> associations = presenter.fetchAllAssociations();
        if (associations == null) {
            return assocs;
        }

        final String derrivedLinks = "Derived links";
        final String associationClass = "Association class links";
        final String nAryLinks = "N-ary links";
        final String reflexivLinks = "Reflexive links";
        final String binaryLinks = "Binary links";
        final String aggregation = "Aggregation links";
        final String compositon = "Composition links";

        for (MAssociation assoc : associations) {
            if (assoc == null) {
                continue;
            }
            Set<MLink> links = presenter.fetchLinksOfAssociation(assoc);
            if (links == null || links.isEmpty()) {
                continue;
            }

            switch (assoc.aggregationKind()) {
            case 0:
                for (MLink link : links) {
                    if (link.association().isDerived() || link.association().isUnion()) {
                        assocs.computeIfAbsent(derrivedLinks, k -> new ArrayList<>()).add(link);
                    } else if (link.association() instanceof MAssociationClassImpl) {
                        assocs.computeIfAbsent(associationClass, k -> new ArrayList<>()).add(link);
                    } else if (link.linkEnds().size() > 2) {
                        assocs.computeIfAbsent(nAryLinks, k -> new ArrayList<>()).add(link);
                    } else if (link.association().associatedClasses().size() == 1) {
                        assocs.computeIfAbsent(reflexivLinks, k -> new ArrayList<>()).add(link);
                    } else if (link.linkedObjects().size() == 2
                            && !link.linkedObjects().get(0).equals(link.linkedObjects().get(1))) {
                        assocs.computeIfAbsent(binaryLinks, k -> new ArrayList<>()).add(link);
                    }
                }
                break;
            case 1:
                for (MLink link : links) {
                    assocs.computeIfAbsent(aggregation, k -> new ArrayList<>()).add(link);
                }
                break;
            case 2:
                for (MLink link : links) {
                    assocs.computeIfAbsent(compositon, k -> new ArrayList<>()).add(link);
                }
                break;
            default:
                break;
            }
        }
        return assocs;
    }

    private String formatLinkLabel(String prefix, MLink link) {
        return prefix + " " + link.toString();
    }

    private String formatLinkName(MLink link) {
        if (link == null) {
            return "()";
        }
        StringBuilder label = new StringBuilder("(");
        if (link.association().associatedClasses().size() == 1) {
            label.append(link.getLinkEnd(0).associationEnd().nameAsRolename()).append(":");
        }
        label.append(link.linkedObjects().get(0).toString());
        if (link.getLinkEnd(0).hasQualifiers()) {
            label.append(",").append(link.getLinkEnd(0).getQualifierValues());
        }
        label.append(", ");
        if (link.association().associatedClasses().size() == 1) {
            label.append(link.getLinkEnd(1).associationEnd().nameAsRolename()).append(":");
        }
        label.append(link.linkedObjects().get(1).toString());
        if (link.getLinkEnd(1).hasQualifiers()) {
            label.append(",").append(link.getLinkEnd(1).getQualifierValues());
        }
        label.append(")");
        return label.toString();
    }

    private List<MLink> collectUniqueLinks(Set<EdgeBase> edges) {
        List<MLink> links = new ArrayList<>();
        for (EdgeBase edge : edges) {
            if (edge instanceof org.tzi.use.gui.views.diagrams.elements.edges.LinkEdge aEdge) {
                MLink link = aEdge.getLink();
                if (!links.contains(link)) {
                    links.add(link);
                }
            }
        }
        return links;
    }

    private Map<String, List<MLink>> groupLinksByAssociation(List<MLink> links) {
        Map<String, List<MLink>> grouped = new TreeMap<>();
        for (MLink link : links) {
            grouped.computeIfAbsent(link.association().toString(), key -> new ArrayList<>()).add(link);
        }
        return grouped;
    }

    private Comparator<MLink> linkComparator() {
        return Comparator
                .comparing((MLink link) -> link.linkedObjects().get(0).toString(), new org.tzi.use.gui.util.AlphanumComparator())
                .thenComparing(link -> link.linkedObjects().get(1).toString(), new org.tzi.use.gui.util.AlphanumComparator());
    }

    private JMenu buildProtocolStateMachineMenu(NewObjectDiagramPresenter presenter, Set<MObject> selectedObjects) {
        JMenu menu = new JMenu("Show protocol state machine...");
        menu.setEnabled(false);

        if (presenter == null || selectedObjects == null || selectedObjects.size() != 1) {
            return menu;
        }

        MObject obj = selectedObjects.iterator().next();
        List<MProtocolStateMachine> sortedPSMs = new LinkedList<>(presenter.fetchProtocolStateMachines(obj));
        sortedPSMs.removeIf(Objects::isNull);
        sortedPSMs.sort(new MNamedElementComparator());

        for (MProtocolStateMachine psm : sortedPSMs) {
            menu.setEnabled(true);
            final JMenuItem showGivenPSM = new JMenuItem(psm.name());
            showGivenPSM.addActionListener(ev -> presenter.onShowProtocolStateMachine(psm, obj));
            menu.add(showGivenPSM);
        }

        return menu;
    }

    private JMenuItem buildCommentNode(ObjectDiagramInteractor diagram,
                                       NewObjectDiagramPresenter presenter,
                                       Point popupPosition) {
        return buildMenuItem("Add comment node", ev -> {
            if (!(diagram instanceof NewObjectDiagram newDiagram)) {
                if (presenter != null) {
                    presenter.onStatusMessage("Add comment node requires the object diagram.");
                }
                return;
            }

            Point position = popupPosition != null ? popupPosition : new Point();
            CommentNode commentNode = new CommentNode(position.x, position.y, newDiagram);
            newDiagram.getGraph().add(commentNode);
            newDiagram.invalidateContent(true);
        });
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

    private List<List<?>> castQualifiers(List<?> qualifiers) {
        if (qualifiers == null) {
            return null;
        }
        List<List<?>> casted = new ArrayList<>(qualifiers.size());
        for (Object qualifier : qualifiers) {
            if (qualifier instanceof List<?> list) {
                casted.add(list);
            } else {
                casted.add(List.of());
            }
        }
        return casted;
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

