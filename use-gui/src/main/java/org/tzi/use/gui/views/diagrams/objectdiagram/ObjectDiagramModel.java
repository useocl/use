package org.tzi.use.gui.views.diagrams.objectdiagram;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;

/**
 * Skeleton for the object diagram domain model. Holds visible/hidden graph data.
 * This is intentionally minimal and not yet wired; follow-up steps will migrate
 * state from {@link NewObjectDiagram} into this model.
 */
public class ObjectDiagramModel {

    /** listeners for future change events */
    public interface Listener {
        default void onNodeAdded(MObject obj, PlaceableNode node) {}
        default void onNodeRemoved(MObject obj) {}
        default void onLinkAdded(MLink link, EdgeBase edge) {}
        default void onLinkRemoved(MLink link) {}
        default void onStateChanged() {}
    }

    private final Set<Listener> listeners = new HashSet<>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    // Visible/hidden state mirrors existing ObjectDiagramData; to be populated incrementally
    private final Map<MObject, PlaceableNode> visibleNodes = new HashMap<>();
    private final Map<MLink, EdgeBase> visibleBinaryEdges = new HashMap<>();
    private final Map<MLink, EdgeBase> visibleNaryEdges = new HashMap<>();

    private final Map<MObject, PlaceableNode> hiddenNodes = new HashMap<>();
    private final Map<MLink, EdgeBase> hiddenBinaryEdges = new HashMap<>();
    private final Map<MLink, EdgeBase> hiddenNaryEdges = new HashMap<>();

    // Accessors (read-only views for now)
    public Map<MObject, PlaceableNode> getVisibleNodes() {
        return visibleNodes;
    }

    public Map<MLink, EdgeBase> getVisibleBinaryEdges() {
        return visibleBinaryEdges;
    }

    public Map<MLink, EdgeBase> getVisibleNaryEdges() {
        return visibleNaryEdges;
    }

    public Map<MObject, PlaceableNode> getHiddenNodes() {
        return hiddenNodes;
    }

    public Map<MLink, EdgeBase> getHiddenBinaryEdges() {
        return hiddenBinaryEdges;
    }

    public Map<MLink, EdgeBase> getHiddenNaryEdges() {
        return hiddenNaryEdges;
    }

    /**
     * Placeholder query for later migration: map links to kind of association.
     */
    public Map<String, List<MLink>> mapLinksToKindOfAssociation(List<MAssociation> associations, Map<MAssociation, Set<MLink>> linksByAssoc) {
        return Map.of();
    }

    /**
     * Placeholder for later migration: resolve a link by value/class combination.
     */
    public MLink getLinkByValue(MLink candidate, String value) {
        return candidate;
    }

    /**
     * Placeholder for later migration: check hidden state of a link.
     */
    public boolean isHidden(MLink link) {
        return hiddenBinaryEdges.containsKey(link)
                || hiddenNaryEdges.containsKey(link)
                || (link instanceof MLinkObject ml) && hiddenNodes.containsKey(ml);
    }

    /**
     * Placeholder for later migration: check hidden state of a node.
     */
    public boolean isHidden(MObject obj) {
        return hiddenNodes.containsKey(obj);
    }
}

