package org.tzi.use.gui.views.diagrams.objectdiagram;

import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;
import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.edges.BinaryAssociationClassOrObject;
import org.tzi.use.gui.views.diagrams.elements.edges.BinaryAssociationOrLinkEdge;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.uml.mm.MAssociation;
// removed unused imports
import org.tzi.use.uml.sys.*;
import org.w3c.dom.Element;

import java.util.*;

class DefaultPlacementRepository implements PlacementRepository {

    @Override
    public void storeLayout(NewObjectDiagramModel model,
                             PersistHelper helper,
                             Element root) {
        storePlacementInfos(helper, root, model.getVisibleData(), true);
        storePlacementInfos(helper, root, model.getHiddenData(), false);
    }

    private void storePlacementInfos(PersistHelper helper, Element root, NewObjectDiagram.ObjectDiagramData data, boolean visible) {
        for (ObjectNode n : data.getObjectToNodeMap().values()) {
            n.storePlacementInfo(helper, root, !visible);
        }

        for (PlaceableNode n : data.getNaryLinkToDiamondNodeMap().values()) {
            n.storePlacementInfo(helper, root, !visible);
        }

        for (EdgeBase e : data.getBinaryLinkToEdgeMap().values()) {
            e.storePlacementInfo(helper, root, !visible);
        }

        for (EdgeBase e : data.getLinkObjectToNodeEdge().values()) {
            e.storePlacementInfo(helper, root, !visible);
        }
    }

    @Override
    public Set<MObject> restoreLayout(NewObjectDiagramModel model,
                                      PersistHelper helper,
                                      int version,
                                      MSystem system) {
        Set<MObject> hiddenObjects = new HashSet<>();
        if (version < 12) return hiddenObjects;
        restoreBinaryEdges(helper, version, system, model.getVisibleData());
        restoreNodeEdges(helper, version, system, model.getVisibleData());
        restoreDiamondNodes(helper, version, system, model.getVisibleData(), hiddenObjects);
        return hiddenObjects;
    }

    private void restoreBinaryEdges(PersistHelper helper, int version, MSystem system, NewObjectDiagram.ObjectDiagramData visibleData) {
        AutoPilot ap = new AutoPilot(helper.getNav());
        helper.getNav().push();
        try {
            ap.selectXPath("./edge[@type='BinaryEdge']");
            while (ap.evalXPath() != -1) {
                String name = helper.getElementStringValue("name");
                MAssociation assoc = system.model().getAssociation(name);
                String sourceObjectName = helper.getElementStringValue("source");
                String targetObjectName = helper.getElementStringValue("target");

                MObject sourceObject = system.state().objectByName(sourceObjectName);
                MObject targetObject = system.state().objectByName(targetObjectName);

                if (assoc != null && sourceObject != null && targetObject != null) {
                    MLink link = resolveLink(helper, assoc, sourceObject, targetObject, system);
                    if (link != null) {
                        BinaryAssociationOrLinkEdge edge = visibleData.getBinaryLinkToEdgeMap().get(link);
                        if (edge != null) {
                            edge.restorePlacementInfo(helper, version);
                        }
                    }
                }
            }
        } catch (XPathEvalException | NavException | XPathParseException e) {
            // ignore layout restore issues to keep robust
        } finally {
            ap.resetXPath();
            helper.getNav().pop();
        }
    }

    private void restoreNodeEdges(PersistHelper helper, int version, MSystem system, NewObjectDiagram.ObjectDiagramData visibleData) {
        AutoPilot ap = new AutoPilot(helper.getNav());
        helper.getNav().push();
        try {
            ap.selectXPath("./edge[@type='NodeEdge']");
            while (ap.evalXPath() != -1) {
                String name = helper.getElementStringValue("name");
                MAssociation assoc = system.model().getAssociation(name);
                String sourceObjectName = helper.getElementStringValue("source");
                String targetObjectName = helper.getElementStringValue("target");

                MObject sourceObject = system.state().objectByName(sourceObjectName);
                MObject targetObject = system.state().objectByName(targetObjectName);

                if (assoc != null && sourceObject != null && targetObject != null) {
                    MLink link = resolveLink(helper, assoc, sourceObject, targetObject, system);
                    if (link instanceof MLinkObject linkObj) {
                        EdgeBase tmp = visibleData.getLinkObjectToNodeEdge().get(linkObj);
                        if (tmp instanceof BinaryAssociationClassOrObject edge) {
                            edge.restorePlacementInfo(helper, version);
                        }
                    }
                }
            }
        } catch (XPathEvalException | NavException | XPathParseException e) {
            // ignore layout restore issues to keep robust
        } finally {
            ap.resetXPath();
            helper.getNav().pop();
        }
    }

    private void restoreDiamondNodes(PersistHelper helper, int version, MSystem system,
                                     NewObjectDiagram.ObjectDiagramData visibleData,
                                     Set<MObject> hiddenObjects) {
        restoreObjectNodes(helper, version, system, visibleData, hiddenObjects);
        restoreDiamondNodesForAssociations(helper, version, system, visibleData);
    }

    private void restoreObjectNodes(PersistHelper helper, int version, MSystem system,
                                    NewObjectDiagram.ObjectDiagramData visibleData,
                                    Set<MObject> hiddenObjects) {
        AutoPilot ap = new AutoPilot(helper.getNav());
        helper.getNav().push();
        try {
            ap.selectXPath("./node[@type='Object']");
            while (ap.evalXPath() != -1) {
                String name = helper.getElementStringValue("name");
                MObject obj = system.state().objectByName(name);
                if (obj != null) {
                    ObjectNode node = visibleData.getObjectToNodeMap().get(obj);
                    if (node != null) {
                        node.restorePlacementInfo(helper, version);
                        if (helper.getElementBooleanValue(org.tzi.use.gui.xmlparser.LayoutTags.HIDDEN)) {
                            hiddenObjects.add(obj);
                        }
                    }
                }
            }
        } catch (XPathEvalException | NavException | XPathParseException e) {
            // ignore
        } finally {
            ap.resetXPath();
            helper.getNav().pop();
        }
    }

    private void restoreDiamondNodesForAssociations(PersistHelper helper, int version, MSystem system,
                                                    NewObjectDiagram.ObjectDiagramData visibleData) {
        AutoPilot ap = new AutoPilot(helper.getNav());
        helper.getNav().push();
        try {
            ap.selectXPath("./node[@type='DiamondNode']");
            while (ap.evalXPath() != -1) {
                processDiamondNode(helper, version, system, visibleData);
            }
        } catch (XPathEvalException | NavException | XPathParseException e) {
            // ignore
        } finally {
            ap.resetXPath();
            helper.getNav().pop();
        }
    }

    private void processDiamondNode(PersistHelper helper, int version, MSystem system,
                                    NewObjectDiagram.ObjectDiagramData visibleData) {
        String name = helper.getElementStringValue("name");
        MAssociation assoc = system.model().getAssociation(name);
        // Only proceed if association exists and there is at least one connectedNode child
        if (assoc == null || !helper.toFirstChild("connectedNode")) {
            return;
        }

        List<MObject> connectedObjects = new LinkedList<>();
        // collect all connectedNode children
        do {
            String objectName = helper.getElementStringValue();
            MObject obj = system.state().objectByName(objectName);
            if (obj != null) {
                connectedObjects.add(obj);
            }
        } while (helper.toNextSibling("connectedNode"));

        // only restore if number of connected objects matches association arity
        if (assoc.associationEnds().size() == connectedObjects.size()) {
            MLink link = system.state().linkBetweenObjects(assoc, connectedObjects, Collections.emptyList());
            if (link != null) {
                var node = visibleData.getNaryLinkToDiamondNodeMap().get(link);
                if (node != null) {
                    helper.toParent();
                    node.restorePlacementInfo(helper, version);
                }
            }
        }
    }

    private MLink resolveLink(PersistHelper helper, MAssociation assoc, MObject source, MObject target, MSystem system) {
        if (assoc.hasQualifiedEnds()) {
            String linkValue = helper.getElementStringValue("linkValue");
            return getLinkByValue(assoc, Arrays.asList(source, target), linkValue, system);
        }
        return system.state().linkBetweenObjects(assoc, Arrays.asList(source, target), Collections.emptyList());
    }

    private MLink getLinkByValue(MAssociation assoc, List<MObject> objects, String linkValue, MSystem system) {
        if (assoc == null || objects == null) {
            return null;
        }
        Set<MLink> allLinks;
        try {
            allLinks = system.state().linksOfAssociation(assoc).links();
        } catch (Exception ex) {
            return null;
        }
        if (allLinks == null || allLinks.isEmpty()) {
            return null;
        }
        List<MLink> matchingLinks = allLinks.stream()
                .filter(Objects::nonNull)
                .filter(l -> isMatchingLink(l, objects))
                .collect(java.util.stream.Collectors.toCollection(java.util.LinkedList::new));
        if (matchingLinks.isEmpty()) {
            return null;
        }
        if (matchingLinks.size() == 1) {
            return matchingLinks.getFirst();
        }
        if (linkValue == null) {
            return null;
        }
        for (MLink l : matchingLinks) {
            String s = null;
            try {
                s = l.toString();
            } catch (Exception ignored) {
                // toString may throw for certain link implementations; ignore and continue
            }
            if (Objects.equals(linkValue, s)) {
                return l;
            }
        }
        return null;
    }

    /**
     * Helper: checks whether link's linkedObjects equal given objects (by position).
     */
    private boolean isMatchingLink(MLink l, List<MObject> objects) {
        List<MObject> linked = l.linkedObjects();
        if (linked == null || linked.size() != objects.size()) {
            return false;
        }
        for (int i = 0; i < linked.size(); i++) {
            if (!Objects.equals(linked.get(i), objects.get(i))) {
                return false;
            }
        }
        return true;
    }
}
