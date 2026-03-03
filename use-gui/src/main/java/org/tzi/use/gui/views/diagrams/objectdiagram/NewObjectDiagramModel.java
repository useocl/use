package org.tzi.use.gui.views.diagrams.objectdiagram;

import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.List;
import java.util.ArrayList;

import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.views.diagrams.elements.positioning.PositionStrategy;
import org.tzi.use.gui.views.diagrams.elements.edges.BinaryAssociationOrLinkEdge;
import org.tzi.use.gui.views.diagrams.elements.edges.NAryAssociationClassOrObjectEdge;
import org.tzi.use.gui.views.diagrams.elements.DiamondNode;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram.ObjectDiagramData;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;

/**
 * Skeleton for the object diagram domain model. Holds visible/hidden graph data.
 * This is intentionally minimal and not yet wired; follow-up steps will migrate
 * state from {@link NewObjectDiagram} into this model.
 */
public class NewObjectDiagramModel {

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

    private final ObjectDiagramData visibleData = new ObjectDiagramData();
    private final ObjectDiagramData hiddenData = new ObjectDiagramData();

    // caches for last known positions, moved from diagram into model
    private final Map<MObject, Point2D> lastKnownNodePositions = new WeakHashMap<>();
    private final Map<MLink, PositionStrategy> lastKnownLinkPositions = new WeakHashMap<>();

    /** tracked selection/highlight state to centralize UI state */
    private final Set<MObject> selectedObjects = new HashSet<>();
    private final Set<MLink> selectedLinks = new HashSet<>();
    private final Map<MObject, Boolean> highlightedObjects = new HashMap<>();
    private final Map<MLink, Boolean> highlightedLinks = new HashMap<>();

    public ObjectDiagramData getVisibleData() {
        return visibleData;
    }

    public ObjectDiagramData getHiddenData() {
        return hiddenData;
    }

    public Map<MObject, Point2D> getLastKnownNodePositions() {
        return lastKnownNodePositions;
    }

    public Map<MLink, PositionStrategy> getLastKnownLinkPositions() {
        return lastKnownLinkPositions;
    }

    public Set<MObject> getSelectedObjects() {
        return Collections.unmodifiableSet(selectedObjects);
    }

    public Set<MLink> getSelectedLinks() {
        return Collections.unmodifiableSet(selectedLinks);
    }

    /** Clear all stored state (visible/hidden and caches). */
    public void reset() {
        visibleData.clear();
        hiddenData.clear();
        lastKnownNodePositions.clear();
        lastKnownLinkPositions.clear();
        selectedObjects.clear();
        selectedLinks.clear();
        highlightedObjects.clear();
        highlightedLinks.clear();
    }

    public boolean isHidden(MLink link) {
        return hiddenData.containsLink(link)
                || (link instanceof MLinkObject ml && hiddenData.getObjectToNodeMap().containsKey(ml));
    }

    public boolean isHidden(MObject obj) {
        return hiddenData.getObjectToNodeMap().containsKey(obj);
    }

    /** Move object node from hidden to visible or vice versa. */
    public void moveObject(MObject obj, boolean toVisible) {
        ObjectDiagramData source = toVisible ? hiddenData : visibleData;
        ObjectDiagramData target = toVisible ? visibleData : hiddenData;
        var node = source.getObjectToNodeMap().remove(obj);
        if (node != null) {
            target.getObjectToNodeMap().put(obj, node);
        }
    }

    public BinaryAssociationOrLinkEdge moveBinaryLink(MLink link, boolean toVisible) {
        ObjectDiagramData source = toVisible ? hiddenData : visibleData;
        ObjectDiagramData target = toVisible ? visibleData : hiddenData;
        BinaryAssociationOrLinkEdge edge = source.getBinaryLinkToEdgeMap().remove(link);
        if (edge != null) {
            target.getBinaryLinkToEdgeMap().put(link, edge);
        }
        return edge;
    }

    public EdgeBase moveLinkObjectEdge(MLinkObject link, boolean toVisible) {
        ObjectDiagramData source = toVisible ? hiddenData : visibleData;
        ObjectDiagramData target = toVisible ? visibleData : hiddenData;
        EdgeBase edge = source.getLinkObjectToNodeEdge().remove(link);
        if (edge != null) {
            target.getLinkObjectToNodeEdge().put(link, edge);
        }
        return edge;
    }

    public DiamondNode moveNaryLinkNode(MLink link, boolean toVisible) {
        ObjectDiagramData source = toVisible ? hiddenData : visibleData;
        ObjectDiagramData target = toVisible ? visibleData : hiddenData;
        DiamondNode node = source.getNaryLinkToDiamondNodeMap().remove(link);
        if (node != null) {
            target.getNaryLinkToDiamondNodeMap().put(link, node);
        }
        return node;
    }

    public List<EdgeBase> moveHalfEdges(MLink link, boolean toVisible) {
        ObjectDiagramData source = toVisible ? hiddenData : visibleData;
        ObjectDiagramData target = toVisible ? visibleData : hiddenData;
        List<EdgeBase> edges = source.getHalfLinkToEdgeMap().remove(link);
        if (edges != null) {
            target.getHalfLinkToEdgeMap().put(link, edges);
        }
        return edges == null ? List.of() : edges;
    }

    public BinaryAssociationOrLinkEdge popBinaryLink(MLink link) {
        BinaryAssociationOrLinkEdge edge = visibleData.getBinaryLinkToEdgeMap().remove(link);
        if (edge == null) {
            edge = hiddenData.getBinaryLinkToEdgeMap().remove(link);
        }
        return edge;
    }

    public EdgeBase popLinkObjectEdge(MLinkObject link) {
        EdgeBase edge = visibleData.getLinkObjectToNodeEdge().remove(link);
        if (edge == null) {
            edge = hiddenData.getLinkObjectToNodeEdge().remove(link);
        }
        return edge;
    }

    public DiamondNode popNaryLinkNode(MLink link) {
        DiamondNode node = visibleData.getNaryLinkToDiamondNodeMap().remove(link);
        if (node == null) {
            node = hiddenData.getNaryLinkToDiamondNodeMap().remove(link);
        }
        return node;
    }

    public List<EdgeBase> popHalfEdges(MLink link) {
        List<EdgeBase> edges = visibleData.getHalfLinkToEdgeMap().remove(link);
        if (edges == null) {
            edges = hiddenData.getHalfLinkToEdgeMap().remove(link);
        }
        return edges == null ? new ArrayList<>() : edges;
    }

    public ObjectNode popObject(MObject obj) {
        ObjectNode node = visibleData.getObjectToNodeMap().remove(obj);
        if (node == null) {
            node = hiddenData.getObjectToNodeMap().remove(obj);
        }
        return node;
    }

    public void replaceSelection(Set<MObject> objects, Set<MLink> links) {
        selectedObjects.clear();
        selectedLinks.clear();
        if (objects != null) {
            selectedObjects.addAll(objects);
        }
        if (links != null) {
            selectedLinks.addAll(links);
        }
        notifyStateChanged();
    }

    public boolean isHighlighted(MObject obj) {
        return highlightedObjects.getOrDefault(obj, Boolean.FALSE);
    }

    public boolean isHighlighted(MLink link) {
        return highlightedLinks.getOrDefault(link, Boolean.FALSE);
    }

    public void setHighlighted(MObject obj, boolean value) {
        highlightedObjects.put(obj, value);
        notifyStateChanged();
    }

    public void setHighlighted(MLink link, boolean value) {
        highlightedLinks.put(link, value);
        notifyStateChanged();
    }

    private void notifyStateChanged() {
        listeners.forEach(Listener::onStateChanged);
    }
}
