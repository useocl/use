package org.tzi.use.gui.views.diagrams.objectdiagram;

import org.tzi.use.gui.views.diagrams.ObjectNodeActivity;
import org.tzi.use.gui.views.diagrams.elements.CommentNode;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.views.diagrams.util.MenuScroller;
import org.tzi.use.gui.views.selection.objectselection.ObjectSelection;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MAssociationClassImpl;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MNamedElementComparator;
import org.tzi.use.uml.mm.statemachines.MProtocolStateMachine;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.util.collections.CollectionUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DefaultContextMenuProvider implements ContextMenuProvider {
    // common UI label constants to avoid duplicated string literals (Sonar S1192)
    private static final String LABEL_HIDE = "Hide ";
    private static final String LABEL_SHOW = "Show ";
    private static final String LABEL_HIDE_ALL = "Hide all";
    private static final String LABEL_SHOW_ALL = "Show all";
    private static final String LABEL_CLASS_PREFIX = "Class ";
    private static final String LABEL_LINKS_SUFFIX = " links";
    // link kind labels
    private static final String KIND_DERIVED = "Derived links";
    private static final String KIND_ASSOCIATION_CLASS = "Association class links";
    private static final String KIND_NARY = "N-ary links";
    private static final String KIND_REFLEXIVE = "Reflexive links";
    private static final String KIND_BINARY = "Binary links";
    private static final String KIND_AGGREGATION = "Aggregation links";
    private static final String KIND_COMPOSITION = "Composition links";

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

        boolean blankCanvas = !hasSelectedObjects && !hasSelectedLinks && safeAssocObjects.isEmpty();

        List<Component> ordered = new ArrayList<>();

        if (diagram instanceof NewObjectDiagram newDiagram

                && hasHiddenElements(newDiagram)

                && (blankCanvas || hasSelectedLinks)) {

            addComponent(ordered, buildMenuItem("Show Hidden Elements", ev -> presenter.onShowHiddenElements()));

        }

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

            addComponent(ordered, buildGreyOutItem(diagram, presenter, safeObjects));

            addSeparator(ordered);

        }

        if (hasSelectedLinks) {

            addComponent(ordered, buildHideSelectedLinksItem(presenter, safeLinks));

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

        if (hasSelectedObjects && safeObjects.size() == 1 && !hasSelectedLinks) {

            addComponent(ordered, buildProtocolStateMachineMenu(presenter, safeObjects));

            addSeparator(ordered);

        }

        addComponent(ordered, buildCommentNode(diagram, presenter, popupPosition));

        addSeparator(ordered);

        buildViewAndLayoutOptions(ordered, baseItems, diagram, presenter);

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

        List<MAssociation> associations = prepareAssociations(presenter);
        List<MObject> selList = prepareSelectedList(selectedObjects);

        if (selList.isEmpty() || associations.isEmpty()) {
            return items;
        }

        for (MAssociation assoc : associations) {
            addInsertItemsForAssociation(assoc, selList, presenter, items);
        }

        return items;

    }

    private List<MAssociation> prepareAssociations(NewObjectDiagramPresenter presenter) {
        Collection<MAssociation> assocCollection = presenter.fetchAllAssociations();
        List<MAssociation> associations = assocCollection == null ? new ArrayList<>() : new ArrayList<>(assocCollection);
        associations.removeIf(Objects::isNull);
        associations.sort(Comparator.comparing(
                MAssociation::name,
                Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)));
        return associations;
    }

    private List<MObject> prepareSelectedList(Set<MObject> selectedObjects) {
        if (selectedObjects == null || selectedObjects.isEmpty()) {
            return List.of();
        }
        List<MObject> selList = new ArrayList<>(selectedObjects);
        selList.removeIf(Objects::isNull);
        selList.sort(Comparator.comparing(this::safeName, String.CASE_INSENSITIVE_ORDER));
        return selList;
    }

    private JMenuItem buildHideSelectedItem(NewObjectDiagramPresenter presenter, Set<MObject> selectedObjects) {

        return buildMenuItem(LABEL_HIDE + describeSelection(selectedObjects),
                ev -> hideOrWarn(presenter, selectedObjects, presenter::onHideObjects));

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

    private JMenuItem buildGreyOutItem(ObjectDiagramInteractor diagram,

                                       NewObjectDiagramPresenter presenter,

                                       Set<MObject> selectedObjects) {

        boolean allGreyed = areAllSelectedObjectsGreyed(diagram, selectedObjects);

        String label = (allGreyed ? "Grey in " : "Grey out ") + describeSelection(selectedObjects);

        return buildMenuItem(label, ev -> hideOrWarn(presenter, selectedObjects,

                objs -> {

                    if (areAllSelectedObjectsGreyed(diagram, objs)) {

                        presenter.onGrayIn(objs);

                    } else {

                        presenter.onGrayOut(objs);

                    }

                }));

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

            JMenu classMenu = new JMenu(LABEL_CLASS_PREFIX + cls.name());

            MenuScroller.setScrollerFor(classMenu, 15, 125, 0, 0);

            List<MObject> classObjects = new ArrayList<>(objectsByClass.getOrDefault(cls, List.of()));

            classObjects.sort(Comparator.comparing(this::safeName, String.CASE_INSENSITIVE_ORDER));

            JMenuItem hideClassAll = new JMenuItem(LABEL_HIDE_ALL);

            hideClassAll.addActionListener(ev -> presenter.onHideObjects(new LinkedHashSet<>(classObjects)));

            classMenu.add(hideClassAll);

            classMenu.addSeparator();

            for (MObject obj : classObjects) {

                classMenu.add(buildMenuItem(LABEL_HIDE + obj.name(), ev -> presenter.onHideObjects(Set.of(obj))));

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

                JMenuItem hideAllLinks = new JMenuItem(LABEL_HIDE_ALL + LABEL_LINKS_SUFFIX);

        hideAllLinks.setEnabled(!newDiagram.getVisibleData().getEdges().isEmpty());

        hideAllLinks.addActionListener(ev -> presenter.onHideAllLinks());

        menu.add(hideAllLinks);

        menu.addSeparator();

        Map<String, List<MLink>> grouped = groupLinksByAssociation(links);

        for (Map.Entry<String, List<MLink>> entry : grouped.entrySet()) {

            JMenu assocMenu = new JMenu(entry.getKey());

            MenuScroller.setScrollerFor(assocMenu, 20, 125, 0, 0);

            List<MLink> assocLinks = entry.getValue();

            assocMenu.add(buildMenuItem(LABEL_HIDE_ALL, ev -> presenter.onHideLinks(assocLinks)));

            assocMenu.addSeparator();

            assocLinks.sort(linkComparator());

            for (MLink link : assocLinks) {
                assocMenu.add(buildMenuItem(LABEL_HIDE + formatLinkName(link), ev -> presenter.onHideLinks(List.of(link))));
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

        JMenuItem showAllLinks = new JMenuItem(LABEL_SHOW_ALL + LABEL_LINKS_SUFFIX);

        showAllLinks.setEnabled(!newDiagram.getHiddenData().getEdges().isEmpty());

        showAllLinks.addActionListener(ev -> presenter.onShowAllLinks());

        menu.add(showAllLinks);

        menu.addSeparator();

        Map<String, List<MLink>> grouped = groupLinksByAssociation(links);

        for (Map.Entry<String, List<MLink>> entry : grouped.entrySet()) {

            JMenu assocMenu = new JMenu(entry.getKey());

            MenuScroller.setScrollerFor(assocMenu, 20, 125, 0, 0);

            List<MLink> assocLinks = entry.getValue();

            assocMenu.add(buildMenuItem(LABEL_SHOW_ALL, ev -> presenter.onShowLinks(assocLinks)));

            assocMenu.addSeparator();

            assocLinks.sort(linkComparator());

            for (MLink link : assocLinks) {
                assocMenu.add(buildMenuItem(LABEL_SHOW + formatLinkName(link), ev -> presenter.onShowLinks(List.of(link))));
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

        JMenuItem showAllObjects = new JMenuItem(LABEL_SHOW_ALL + " hidden objects");

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

            JMenu parent = classMenus.computeIfAbsent(cls, k -> {
                JMenu subm = new JMenu(LABEL_CLASS_PREFIX + k.name());
                MenuScroller.setScrollerFor(subm, 15, 125, 0, 0);
                JMenuItem showAll = new JMenuItem(LABEL_SHOW_ALL);
                showAll.addActionListener(e -> presenter.onShowHiddenElements());
                subm.add(showAll);
                subm.addSeparator();
                return subm;
            });

            parent.add(buildMenuItem(LABEL_SHOW + obj.name(), ev -> presenter.onShowHiddenElements()));

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

    private JMenuItem buildHideSelectedLinksItem(NewObjectDiagramPresenter presenter,

                                                 Set<MLink> selectedLinks) {

        List<MLink> links = sortedSelectedLinks(selectedLinks);

        if (presenter == null || links.isEmpty()) {

            return null;

        }

        String label = links.size() == 1 ? formatLinkName(links.getFirst()) : links.size() + LABEL_LINKS_SUFFIX;

        return buildMenuItem(LABEL_HIDE + label, ev -> presenter.onHideLinks(links));

    }

    private JMenuItem buildCropSelectedLinksItem(ObjectDiagramInteractor diagram,

                                                 NewObjectDiagramPresenter presenter,

                                                 Set<MLink> selectedLinks,

                                                 Set<MObject> selectedAssocObjects) {

        List<MLink> links = sortedSelectedLinks(selectedLinks);

        if (presenter == null || links.isEmpty() || !(diagram instanceof NewObjectDiagram newDiagram)) {

            return null;

        }

        String label = links.size() == 1 ? formatLinkName(links.getFirst()) : links.size() + LABEL_LINKS_SUFFIX;

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
        addGlobalLinksShowHide(menu, newDiagram, presenter, allLinks);
        menu.addSeparator();
        for (Map.Entry<String, List<MLink>> entry : grouped.entrySet()) {
            addLinksByKindAssocMenu(menu, entry.getKey(), entry.getValue(), presenter, newDiagram);
        }

        return menu;

    }

    private TreeMap<String, List<MLink>> mapLinksToKindOfAssociation(NewObjectDiagramPresenter presenter) {

        TreeMap<String, List<MLink>> assocs = new TreeMap<>();

        List<MAssociation> associations = prepareAssociations(presenter);
        if (associations.isEmpty()) {
            return assocs;
        }
        for (MAssociation assoc : associations) {
            Set<MLink> links = presenter.fetchLinksOfAssociation(assoc);
            if (links == null || links.isEmpty()) {
                continue;
            }
            for (MLink link : links) {
                String kind = computeLinkKind(assoc, link);
                if (kind != null) {
                    assocs.computeIfAbsent(kind, k -> new ArrayList<>()).add(link);
                }
            }
        }

        return assocs;

    }

    // formatLinkLabel removed: unused helper removed to reduce noise

    /**
     * Helper to add insert items for a single association. Extracted from buildInsertItems to reduce complexity.
     */
    private void addInsertItemsForAssociation(MAssociation assoc,
                                              List<MObject> selList,
                                              NewObjectDiagramPresenter presenter,
                                              List<JMenuItem> items) {
        if (assoc == null || assoc.isReadOnly()) {
            return;
        }

        List<MAssociationEnd> ends = assoc.associationEnds();
        int n = ends == null ? 0 : ends.size();
        if (n == 0) {
            return;
        }

        Collection<MLink> associationLinks = presenter.fetchLinksOfAssociation(assoc);
        if (associationLinks == null) {
            associationLinks = List.of();
        }

        int m = selList.size();

        List<int[]> tuples = computeValidTuples(m, n);
        for (int[] digits : tuples) {
            MObject[] tuple = new MObject[n];
            MClass[] types = new MClass[n];
            for (int idx = 0; idx < n; idx++) {
                tuple[idx] = selList.get(digits[idx]);
                types[idx] = tuple[idx].cls();
            }
            if (!assoc.isAssignableFrom(types)) {
                continue;
            }
            processTupleForAssociation(assoc, tuple, associationLinks, presenter, items);
        }
    }
    private List<int[]> computeValidTuples(int m, int n) {
        List<int[]> result = new ArrayList<>();
        if (m <= 0 || n <= 0) {
            return result;
        }
        int pow = 1;
        for (int i = 0; i < n; i++) {
            pow *= m;
        }
        for (int value = 0; value < pow; value++) {
            int[] digits = radixConversion(value, m, n);
            if (isCompleteObjectCombination(digits, m)) {
                result.add(digits);
            }
        }
        return result;
    }

    private void processTupleForAssociation(MAssociation assoc,
                                            MObject[] tuple,
                                            Collection<MLink> associationLinks,
                                            NewObjectDiagramPresenter presenter,
                                            List<JMenuItem> items) {
        StringBuilder joined = new StringBuilder();
        for (int idx = 0; idx < tuple.length; idx++) {
            if (idx > 0) {
                joined.append(',');
            }
            joined.append(safeName(tuple[idx]));
        }

        List<MLink> matchingLinks = findMatchingLinks(tuple, associationLinks);

        if (matchingLinks.isEmpty()) {
            List<MObject> tupleList = Arrays.asList(tuple.clone());
            items.add(buildMenuItem("insert (" + joined + ") into " + assoc.name(), ev -> presenter.onInsertLink(assoc, tupleList)));
        } else {
            for (MLink link : matchingLinks) {
                String deleteLabel = buildDeleteLabel(tuple, castQualifiers(link.getQualifier()), assoc.name());
                items.add(buildMenuItem(deleteLabel, ev -> presenter.onDeleteLink(link)));
            }
        }
    }

    private List<MLink> findMatchingLinks(MObject[] tuple, Collection<MLink> associationLinks) {
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
        return matchingLinks;
    }

    private String buildDeleteLabel(MObject[] tuple, List<List<?>> qualifiers, String assocName) {
        StringBuilder deleteLabel = new StringBuilder("delete (");
        for (int idx = 0; idx < tuple.length; idx++) {
            if (idx > 0) {
                deleteLabel.append(',');
            }
            deleteLabel.append(safeName(tuple[idx]));
            if (idx < qualifiers.size()) {
                deleteLabel.append(formatQualifierList(qualifiers.get(idx)));
            }
        }
        deleteLabel.append(") from ").append(assocName);
        return deleteLabel.toString();
    }

    private String formatQualifierList(List<?> qualifierForEnd) {
        if (qualifierForEnd == null || qualifierForEnd.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (int q = 0; q < qualifierForEnd.size(); q++) {
            if (q > 0) {
                sb.append(',');
            }
            sb.append(qualifierForEnd.get(q));
        }
        sb.append('}');
        return sb.toString();
    }

    /**
     * Helper to build and add the submenu for a grouped association entry in the links-by-kind menu.
     */
    private void addLinksByKindAssocMenu(JMenu parentMenu, String key, List<MLink> links, NewObjectDiagramPresenter presenter, NewObjectDiagram newDiagram) {
        if (links == null || links.isEmpty()) {
            return;
        }
        JMenu assocMenu = new JMenu(key);
        MenuScroller.setScrollerFor(assocMenu, 20, 125, 0, 0);
        List<MLink> assocLinks = new ArrayList<>(links);
        int assocLinkState = newDiagram.isHidden(assocLinks);
        if (assocLinkState == 1 || assocLinkState == 2) {
            assocMenu.add(buildMenuItem(LABEL_SHOW_ALL, ev -> presenter.onShowLinks(assocLinks)));
        }
        if (assocLinkState == 0 || assocLinkState == 2) {
            assocMenu.add(buildMenuItem(LABEL_HIDE_ALL, ev -> presenter.onHideLinks(assocLinks)));
        }
        assocMenu.addSeparator();
        List<MLink> sortedLinks = new ArrayList<>(links);
        sortedLinks.sort(linkComparator());
        for (MLink link : sortedLinks) {
            if (newDiagram.isHidden(link)) {
                assocMenu.add(buildMenuItem(LABEL_SHOW + formatLinkName(link), ev -> presenter.onShowLinks(List.of(link))));
            } else {
                assocMenu.add(buildMenuItem(LABEL_HIDE + formatLinkName(link), ev -> presenter.onHideLinks(List.of(link))));
            }
        }
        parentMenu.add(assocMenu);
    }

    private void addGlobalLinksShowHide(JMenu menu, NewObjectDiagram newDiagram, NewObjectDiagramPresenter presenter, List<MLink> allLinks) {
        int linkState = newDiagram.isHidden(allLinks);
        if (linkState == 1 || linkState == 2) {
            menu.add(buildMenuItem(LABEL_SHOW_ALL + LABEL_LINKS_SUFFIX, ev -> presenter.onShowAllLinks()));
        }
        if (linkState == 0 || linkState == 2) {
            menu.add(buildMenuItem(LABEL_HIDE_ALL + LABEL_LINKS_SUFFIX, ev -> presenter.onHideAllLinks()));
        }
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

    private void buildViewAndLayoutOptions(List<Component> ordered, List<Component> baseItems, ObjectDiagramInteractor diagram, NewObjectDiagramPresenter presenter) {
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
        if (diagram instanceof NewObjectDiagram newDiagram && newDiagram.getVisibleData() != null && newDiagram.getVisibleData().hasNodes()) {
            addSeparator(ordered);
            addComponent(ordered, extractOrFallback(baseItems, "Select all nodes", () -> buildStatusItem("Select all nodes", presenter)));
            addComponent(ordered, extractOrFallback(baseItems, "Select all edges", () -> buildStatusItem("Select all edges", presenter)));
        }
    }

    private String computeLinkKind(MAssociation assoc, MLink link) {
        if (assoc == null || link == null) {
            return null;
        }

        switch (assoc.aggregationKind()) {
            case 0:
                if (link.association().isDerived() || link.association().isUnion()) {
                    return KIND_DERIVED;
                }
                if (link.association() instanceof MAssociationClassImpl) {
                    return KIND_ASSOCIATION_CLASS;
                }
                if (link.linkEnds().size() > 2) {
                    return KIND_NARY;
                }
                if (link.association().associatedClasses().size() == 1) {
                    return KIND_REFLEXIVE;
                }
                if (link.linkedObjects().size() == 2 && !link.linkedObjects().get(0).equals(link.linkedObjects().get(1))) {
                    return KIND_BINARY;
                }
                break;
            case 1:
                return KIND_AGGREGATION;
            case 2:
                return KIND_COMPOSITION;
            default:
                break;
        }

        return null;
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

    private boolean hasHiddenElements(NewObjectDiagram diagram) {

        return diagram != null

                && (diagram.getHiddenNodes() != null && !diagram.getHiddenNodes().isEmpty()

                || diagram.getHiddenData() != null && !diagram.getHiddenData().getEdges().isEmpty());

    }

    private boolean areAllSelectedObjectsGreyed(ObjectDiagramInteractor diagram, Set<MObject> selectedObjects) {

        if (diagram == null || selectedObjects == null || selectedObjects.isEmpty()) {

            return false;

        }

        for (MObject obj : selectedObjects) {

            if (!diagram.isGreyed(obj)) {

                return false;

            }

        }

        return true;

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
            // return empty list instead of null to simplify callers
            return List.of();
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