package org.tzi.use.gui.graphlayout;

import org.tzi.use.graph.DirectedEdge;
import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.gui.views.diagrams.Layoutable;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassNode;
import org.tzi.use.gui.views.diagrams.elements.DiamondNode;
import org.tzi.use.gui.views.diagrams.elements.edges.AssociationOrLinkPartEdge;
import org.tzi.use.gui.views.diagrams.objectdiagram.ObjectNode;
import org.tzi.use.uml.mm.MAggregationKind;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAssociationClassImpl;
import org.tzi.use.uml.sys.MLinkObject;

import javax.swing.*;
import java.util.*;


public class AllLayoutTypes<N extends Layoutable> {
    protected DirectedGraph<N, ?> fGraph;
    protected double fWidth;
    protected double fHeight;
    protected double fMarginX;
    protected double fMarginY;
    private double fEdgeLen = 120.0;
    protected List<N> fNodes;
    Map<N, Integer> NodesIncomingEdgeCount = new HashMap<N, Integer>();
    Map<N, Integer> NodesOutgoingEdgeCount = new HashMap<N, Integer>();
    Map<N, Integer> LayerLevelNodeCount = new LinkedHashMap<N, Integer>();
    Map<N, Integer> AssociationsLevelNodeCount = new LinkedHashMap<N, Integer>();
    Map<N, N> AssociationsParentNode = new LinkedHashMap<N, N>();
    Map<N, N> AssociationsSourceNode = new LinkedHashMap<N, N>();
    ArrayList<N> RootNodes = new ArrayList<N>();
    Set<N> DiamondNodes = new HashSet<N>();
    Set<N> AssociationNodes = new HashSet<N>();
    Set<N> TracedNodes = new HashSet<N>();
    Set<N> HiddenNodes = new HashSet<N>();
    double maxLayerWidth = 0;
    double maxLayerHeight = 0;
    double startLayerX = 0;
    double startLayerY = 0;
    int HorizontalSpacing;
    int VerticalSpacing;
    boolean IsPutAssociationsOnRelationsEnabled;

    public enum LayoutType {
        Hierarchical,
        Horizontal,
        HierarchicalUpsideDown,
        HorizontalRightToLeft,
        LandscapeSwimlane,
        PortraitSwimlane
    }

    public AllLayoutTypes(DirectedGraph<N, ?> g,
                          Set<N> hiddenNodes,
                          double width, double height,
                          double marginx, double marginy) {
        fGraph = g;
        fWidth = width;
        fHeight = height;
        fMarginX = marginx;
        fMarginY = marginy;
        fNodes = fGraph.getNodes();
        HiddenNodes = hiddenNodes;
    }

    /**
     * Sets a new default length for edges.
     */
    public void setEdgeLen(double len) {
        fEdgeLen = fEdgeLen;
    }

    private void MakeAllParametersEmpty() {
        NodesIncomingEdgeCount = new HashMap<N, Integer>();
        NodesOutgoingEdgeCount = new HashMap<N, Integer>();
        LayerLevelNodeCount = new LinkedHashMap<N, Integer>();
        AssociationsLevelNodeCount = new LinkedHashMap<N, Integer>();
        AssociationsParentNode = new LinkedHashMap<N, N>();
        AssociationsSourceNode = new LinkedHashMap<N, N>();
        RootNodes = new ArrayList<N>();
        DiamondNodes = new HashSet<N>();
        AssociationNodes = new HashSet<N>();
        TracedNodes = new HashSet<N>();
        maxLayerWidth = 0;
        maxLayerHeight = 0;
        startLayerX = 0;
        startLayerY = 0;
        nodeCount = 0;
        Temp = new HashSet<N>();
        AllRootNodesLongestPath = new LinkedHashMap<N, Integer>();
        AllTracedNodes = new ArrayList<N>();
        LongestRootPath = 0;
        CurrentLongestRootPath = 0;
        CurrentRootPathLong = 0;
        sourceLayerLevelNodeCount = new LinkedHashMap<N, Integer>();
        sourceLayerLevelNodeCount = new LinkedHashMap<N, Integer>();
    }

    public void HierarchicalLayout(int horizontalSpacing, int verticalSpacing, boolean isPutAssociationsOnRelationsEnabled) {
        HorizontalSpacing = horizontalSpacing;
        VerticalSpacing = verticalSpacing;
        IsPutAssociationsOnRelationsEnabled = isPutAssociationsOnRelationsEnabled;
        layout(LayoutType.Hierarchical);
    }

    public void HierarchicalUpsideDownLayout(int horizontalSpacing, int verticalSpacing, boolean isPutAssociationsOnRelationsEnabled) {
        HorizontalSpacing = horizontalSpacing;
        VerticalSpacing = verticalSpacing;
        IsPutAssociationsOnRelationsEnabled = isPutAssociationsOnRelationsEnabled;
        layout(LayoutType.HierarchicalUpsideDown);
    }

    public void HorizontalLayout(int horizontalSpacing, int verticalSpacing, boolean isPutAssociationsOnRelationsEnabled) {
        HorizontalSpacing = horizontalSpacing;
        VerticalSpacing = verticalSpacing;
        IsPutAssociationsOnRelationsEnabled = isPutAssociationsOnRelationsEnabled;
        layout(LayoutType.Horizontal);
    }

    public void HorizontalRightToLeftLayout(int horizontalSpacing, int verticalSpacing, boolean isPutAssociationsOnRelationsEnabled) {
        HorizontalSpacing = horizontalSpacing;
        VerticalSpacing = verticalSpacing;
        IsPutAssociationsOnRelationsEnabled = isPutAssociationsOnRelationsEnabled;
        layout(LayoutType.HorizontalRightToLeft);
    }

    public void LandscapeSwimlaneLayout(int horizontalSpacing, int verticalSpacing, boolean isPutAssociationsOnRelationsEnabled) {
        HorizontalSpacing = horizontalSpacing;
        VerticalSpacing = verticalSpacing;
        IsPutAssociationsOnRelationsEnabled = isPutAssociationsOnRelationsEnabled;
        for (N n : fGraph.getNodes()) {
            if (!(n instanceof ObjectNode || n instanceof DiamondNode)) {
                return;
            }
        }
        SwimlaneLayout(LayoutType.LandscapeSwimlane);
    }

    public void PortraitSwimlaneLayout(int horizontalSpacing, int verticalSpacing, boolean isPutAssociationsOnRelationsEnabled) {
        HorizontalSpacing = horizontalSpacing;
        VerticalSpacing = verticalSpacing;
        IsPutAssociationsOnRelationsEnabled = isPutAssociationsOnRelationsEnabled;
        for (N n : fGraph.getNodes()) {
            if (!(n instanceof ObjectNode || n instanceof DiamondNode)) {
                return;
            }
        }
        SwimlaneLayout(LayoutType.PortraitSwimlane);
    }

    private void layout(LayoutType layoutType) {
        MakeAllParametersEmpty();
        SetRootNodes();
        GetLongestRootPath();
        Map<N, Integer> tracedRootNodes = new LinkedHashMap<N, Integer>();
        if (RootNodes.size() == 0) {
            //Show all Nodes - there is not any edge
        } else {
            double maxX = 0;
            double maxY = 0;
            for (N node : RootNodes) {

                for (N tn : LayerLevelNodeCount.keySet()) {
                    if ((tn.getCenter().getX() + (tn.getWidth() / 2)) > maxX) {
                        maxX = tn.getCenter().getX() + (tn.getWidth() / 2);
                    }
                    if ((tn.getCenter().getY() + (tn.getHeight() / 2)) > maxY) {
                        maxY = tn.getCenter().getY() + (tn.getHeight() / 2);
                    }
                }
                if (layoutType == LayoutType.Hierarchical || layoutType == LayoutType.HierarchicalUpsideDown) {
                    if (maxLayerWidth > 0)
                        startLayerX = (maxX + HorizontalSpacing);
                    else
                        startLayerX += fMarginX;
                } else if (layoutType == LayoutType.Horizontal || layoutType == LayoutType.HorizontalRightToLeft) {
                    if (maxLayerHeight > 0)
                        startLayerY = (maxY + VerticalSpacing);
                    else
                        startLayerY += fMarginY;
                }

                for (N rootNode : RootNodes) {
                    if (LayerLevelNodeCount.keySet().contains(rootNode) && !tracedRootNodes.keySet().contains(rootNode)) {
                        tracedRootNodes.put(rootNode, LayerLevelNodeCount.get(rootNode).intValue());
                    }
                }
                LayerLevelNodeCount.clear();
                if (tracedRootNodes.keySet().contains(node)) {
                    LayerLevelNodeCount.put(node, tracedRootNodes.get(node).intValue());
                } else {
                    LayerLevelNodeCount.put(node, 0);
                }
                SetAllLayersLevel(node, node);
                do {
                    sourceLayerLevelNodeCount = new LinkedHashMap<N, Integer>();
                    for (N layerNode : LayerLevelNodeCount.keySet()) {
                        ExpandLayerLevelsToSourceNodes(layerNode, layerNode);
                    }
                    LayerLevelNodeCount.putAll(sourceLayerLevelNodeCount);
                } while (sourceLayerLevelNodeCount.size() > 0);
                AddDiamondNodesToLayers();
                FindAssociations();
                int CurrentLayerCount = 0;
                while (true) {
                    CurrentLayerCount = GetMaxLayerCount();
                    UpdateNodeLayerCountWithMultipleSource();
                    if (CurrentLayerCount == GetMaxLayerCount())
                        break;
                }
                SortLayoutLevels();
                if (layoutType == LayoutType.Hierarchical || layoutType == LayoutType.HierarchicalUpsideDown)
                    DrawHierarchical(node);
                else if (layoutType == LayoutType.Horizontal || layoutType == LayoutType.HorizontalRightToLeft)
                    DrawHorizontal(node);
            }
        }

        if (layoutType == LayoutType.HierarchicalUpsideDown)
            DrawHierarchicalDownUp();
        else if (layoutType == LayoutType.HorizontalRightToLeft)
            DrawHorizontalLeftRight();

        if (layoutType == LayoutType.Hierarchical || layoutType == LayoutType.HierarchicalUpsideDown || layoutType == LayoutType.LandscapeSwimlane || layoutType == LayoutType.PortraitSwimlane)
            MoveHiddenObjectsDown();
        else if (layoutType == LayoutType.Horizontal || layoutType == LayoutType.HorizontalRightToLeft)
            MoveHiddenObjectsRight();
    }

    private void InitializeNodesIncomingOutgoingEdgesCount() {
        int nodeSize = fNodes.size();
        DiamondNodes = new HashSet<N>();
        AssociationNodes = new HashSet<N>();
        //create a Map of all nodes withe the number of in coming and outgoing edges
        for (int i = 0; i < nodeSize; i++) {
            N currentNode = fNodes.get(i);
            NodesIncomingEdgeCount.put(currentNode, fGraph.numIncomingEdges(currentNode));
            NodesOutgoingEdgeCount.put(currentNode, fGraph.numOutgoingEdges(currentNode));
            if (currentNode instanceof DiamondNode) {
                DiamondNodes.add(currentNode);
            } else if (currentNode instanceof ClassNode) {
                if (((ClassNode) currentNode).cls() instanceof MAssociationClass)
                    AssociationNodes.add(currentNode);
            } else if (currentNode instanceof ObjectNode) {
                if (((ObjectNode) currentNode).cls() instanceof MAssociationClass)
                    AssociationNodes.add(currentNode);

            }
        }
    }

    int nodeCount = 0;
    Set<N> Temp = new HashSet<N>();

    private void RootNodeChildCounts(N rootNode, N mainRootNode) {

        if (fGraph.numOutgoingEdges(rootNode) == 0 || (fGraph.targetNodeSet(rootNode).contains(mainRootNode) && rootNode != mainRootNode)) {
            nodeCount = Temp.size();
            return;
        }

        if (!Temp.contains(rootNode))
            Temp.add(rootNode);
        for (N targetNode : fGraph.targetNodeSet(rootNode)) {
            if (!Temp.contains(targetNode)) {
                Temp.add(targetNode);
                RootNodeChildCounts(targetNode, mainRootNode);
            }
        }
        nodeCount = Temp.size();
    }

    private void SetRootNodes() {
        InitializeNodesIncomingOutgoingEdgesCount();

        //Nodes without incoming edge are Root nodes
        for (N node : NodesIncomingEdgeCount.keySet()) {
            if ((NodesIncomingEdgeCount.get(node).equals(0) || (NodesIncomingEdgeCount.get(node).equals(1) && fGraph.targetNodeSet(node).contains(node))) && !(node instanceof DiamondNode) && !(AssociationNodes.contains(node))) {
                RootNodes.add(node);
            }
        }

        if (IsRootNodesFull()) {
            return;
        }

        for (N node : fGraph.getNodes()) {
            nodeCount = 0;
            Temp = new HashSet<N>();
            Temp = GetTempChild();
            RootNodeChildCounts(node, node);
            if (nodeCount == fGraph.getNodes().size() - DiamondNodes.size()) {
                RootNodes.add(node);
                return;
            }
        }

        //If there is a cycle , the Node(s) with more outgoing edges is/are root nodes
        int maxOutgoingCount = 0;
        for (N node : NodesOutgoingEdgeCount.keySet()) {
            if (NodesOutgoingEdgeCount.get(node).intValue() > maxOutgoingCount) {
                maxOutgoingCount = NodesIncomingEdgeCount.get(node).intValue();
            }
        }

        for (N node : NodesOutgoingEdgeCount.keySet()) {
            if (NodesOutgoingEdgeCount.get(node).intValue() == maxOutgoingCount) {
                RootNodes.add(node);
                IsRootNodesFull();
                if (nodeCount < fGraph.getNodes().size() - DiamondNodes.size())
                    RootNodes.remove(node);
                else if (nodeCount == fGraph.size())
                    break;
            }
        }

        if (IsRootNodesFull()) {
            return;
        }

        for (N node : NodesOutgoingEdgeCount.keySet()) {
            if (NodesOutgoingEdgeCount.get(node).intValue() == maxOutgoingCount) {
                RootNodes.add(node);
                IsRootNodesFull();
                if (nodeCount == fGraph.getNodes().size() - DiamondNodes.size())
                    break;
            }
        }
    }

    private Set<N> GetTempChild() {
        boolean isAssociationEndNotRootNode = false;
        Set<N> TempChild = new HashSet<N>();
        for (N dnode : DiamondNodes) {
            for (N targetNode : fGraph.targetNodeSet(dnode)) {
                if (NodesIncomingEdgeCount.get(targetNode).intValue() - 1 > 0) {
                    isAssociationEndNotRootNode = true;
                }
            }
            if (isAssociationEndNotRootNode == false && fGraph.targetNodeSet(dnode).size() > 0) {
                RootNodes.add(fGraph.targetNodeSet(dnode).iterator().next());
            }
            for (N targetNode : fGraph.targetNodeSet(dnode)) {
                if (NodesIncomingEdgeCount.get(targetNode).intValue() - 1 == 0) {
                    TempChild.add(targetNode);
                }
            }
            isAssociationEndNotRootNode = false;
        }

        for (N node : AssociationNodes) {
            TempChild.add(node);
        }
        return TempChild;
    }

    private boolean IsRootNodesFull() {
        nodeCount = 0;
        Temp = new HashSet<N>();
        Temp = GetTempChild();

        for (N n : RootNodes) {
            RootNodeChildCounts(n, n);
        }

        if (nodeCount == fGraph.getNodes().size() - DiamondNodes.size()) {
            return true;
        } else
            return false;
    }

    private Map<N, Integer> sortByValue(Map<N, Integer> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<N, Integer>> list =
                new LinkedList<Map.Entry<N, Integer>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<N, Integer>>() {
            public int compare(Map.Entry<N, Integer> o1,
                               Map.Entry<N, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<N, Integer> sortedMap = new LinkedHashMap<N, Integer>();
        for (Map.Entry<N, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    Map<N, Integer> AllRootNodesLongestPath = new LinkedHashMap<N, Integer>();
    ArrayList<N> AllTracedNodes = new ArrayList<N>();
    int LongestRootPath = 0;

    private void GetLongestRootPath() {
        if (RootNodes.size() == 0) {
            return;
        } else {
            for (N node : RootNodes) {
                FindLongestPath(node, node);
                if (CurrentLongestRootPath > LongestRootPath)
                    LongestRootPath = CurrentLongestRootPath;
                AllRootNodesLongestPath.put(node, CurrentLongestRootPath);
                AllTracedNodes = new ArrayList<N>();
                CurrentRootPathLong = 0;
                CurrentLongestRootPath = 0;
            }

            RootNodes.clear();
            for (N node : sortByValue(AllRootNodesLongestPath).keySet()) {
                RootNodes.add(node);
            }
        }
    }

    int CurrentLongestRootPath = 0;
    int CurrentRootPathLong = 0;

    private void FindLongestPath(N rootNode, N mainRootNode) {
        if (fGraph.targetNodeSet(rootNode).size() == 0) {
            if (CurrentRootPathLong > CurrentLongestRootPath)
                CurrentLongestRootPath = CurrentRootPathLong;
            CurrentRootPathLong--;
            AllTracedNodes.remove(rootNode);
            return;
        }
        if (!AllTracedNodes.contains(rootNode)) {
            AllTracedNodes.add(rootNode);
        }

        for (N targetNode : fGraph.targetNodeSet(rootNode)) {
            if (AllTracedNodes.contains(targetNode)) {
                if (CurrentRootPathLong > CurrentLongestRootPath)
                    CurrentLongestRootPath = CurrentRootPathLong;
                CurrentRootPathLong--;
                AllTracedNodes.remove(rootNode);
                return;
            }

            AllTracedNodes.add(targetNode);
            CurrentRootPathLong++;
            FindLongestPath(targetNode, mainRootNode);
        }
        CurrentRootPathLong--;
        AllTracedNodes.remove(rootNode);
    }

    private void SetAllLayersLevel(N rootNode, N mainRootNode) {
        int layerCount = 0;
        if (fGraph.numOutgoingEdges(rootNode) == 0 || (fGraph.targetNodeSet(rootNode).contains(mainRootNode) && rootNode != mainRootNode)) {
            return;
        }

        for (N targetNode : fGraph.targetNodeSet(rootNode)) {
            for (N n : LayerLevelNodeCount.keySet()) {
                if (n == rootNode) {
                    layerCount = LayerLevelNodeCount.get(n).intValue();
                }
            }
            if (LayerLevelNodeCount.keySet().contains(targetNode) || TracedNodes.contains(targetNode))
                continue;
            layerCount++;
            LayerLevelNodeCount.put(targetNode, layerCount);
            SetAllLayersLevel(targetNode, mainRootNode);
        }
    }

    private void SetTreeLayerCountTarget(N rootNode, N mainRootNode) {
        int layerCount = 0;
        if (fGraph.numOutgoingEdges(rootNode) == 0 || (fGraph.targetNodeSet(rootNode).contains(mainRootNode) && rootNode != mainRootNode))
            return;

        for (N targetNode : fGraph.targetNodeSet(rootNode)) {
            for (N n : LayerLevelNodeCount.keySet()) {
                if (n == rootNode) {
                    layerCount = LayerLevelNodeCount.get(n).intValue();
                }
            }
            for (N n : sourceLayerLevelNodeCount.keySet()) {
                if (n == rootNode) {
                    layerCount = sourceLayerLevelNodeCount.get(n).intValue();
                }
            }
            if (LayerLevelNodeCount.keySet().contains(targetNode) || TracedNodes.contains(targetNode) || sourceLayerLevelNodeCount.keySet().contains(targetNode))
                continue;
            layerCount++;
            sourceLayerLevelNodeCount.put(targetNode, layerCount);
            SetTreeLayerCountTarget(targetNode, mainRootNode);
        }
    }

    Map<N, Integer> sourceLayerLevelNodeCount = new LinkedHashMap<N, Integer>();

    private void ExpandLayerLevelsToSourceNodes(N rootNode, N mainRootNode) {
        int layerSourceCount = 0;
        if (fGraph.numIncomingEdges(rootNode) == 0 || (fGraph.sourceNodeSet(rootNode).contains(mainRootNode) && rootNode != mainRootNode))
            return;

        for (N sourceNode : fGraph.sourceNodeSet(rootNode)) {
            if (LayerLevelNodeCount.keySet().contains(rootNode))
                layerSourceCount = LayerLevelNodeCount.get(rootNode).intValue();
            else if (sourceLayerLevelNodeCount.keySet().contains(rootNode))
                layerSourceCount = sourceLayerLevelNodeCount.get(rootNode).intValue();
            if (LayerLevelNodeCount.keySet().contains(sourceNode) || TracedNodes.contains(sourceNode) || sourceLayerLevelNodeCount.keySet().contains(sourceNode))
                continue;
            if (layerSourceCount - 1 < 0)
                layerSourceCount = 0;
            else
                layerSourceCount--;
            sourceLayerLevelNodeCount.put(sourceNode, layerSourceCount);
            SetTreeLayerCountTarget(sourceNode, sourceNode);
            ExpandLayerLevelsToSourceNodes(sourceNode, mainRootNode);
        }
    }

    private void UpdateNodeLayerCountWithMultipleSource() {
        int max = GetMaxLayerCount();
        Set<N> cyleRelation = new HashSet<N>();
        for (int i = 0; i <= max; i++) {
            for (N node : LayerLevelNodeCount.keySet()) {
                if (LayerLevelNodeCount.get(node).intValue() == i) {
                    int maxParentLayer = 0;
                    int currentParentLayer = 0;

                    for (N sourceNode : fGraph.sourceNodeSet(node)) {
                        if (sourceNode == node || sourceNode instanceof DiamondNode || cyleRelation.contains(sourceNode) || fGraph.existsPath(node, sourceNode))
                            continue;
                        if (fGraph.sourceNodeSet(sourceNode).contains(node)) {
                            cyleRelation.add(node);
                        }
                        for (N layerNode : LayerLevelNodeCount.keySet()) {
                            if (sourceNode == layerNode) {
                                currentParentLayer = LayerLevelNodeCount.get(layerNode).intValue();
                            }
                        }
                        if (currentParentLayer > maxParentLayer) {
                            maxParentLayer = currentParentLayer;
                        }
                    }

                    if (maxParentLayer > 0) {
                        LayerLevelNodeCount.replace(node, LayerLevelNodeCount.get(node).intValue(), maxParentLayer + 1);
                    }
                }
            }
            cyleRelation.clear();
        }

        for (N node : LayerLevelNodeCount.keySet()) {
            int minLevelCount = Integer.MAX_VALUE;
            Set<N> rootDiamond = new HashSet<N>();
            if (node instanceof DiamondNode) {
                for (N targetNode : fGraph.targetNodeSet(node)) {
                    if (LayerLevelNodeCount.keySet().contains(targetNode)) {
                        if (LayerLevelNodeCount.get(targetNode).intValue() < minLevelCount &&
                                (LayerLevelNodeCount.get(targetNode).intValue() != 0 || fGraph.sourceNodeSet(targetNode).size() > 1)) {
                            minLevelCount = LayerLevelNodeCount.get(targetNode).intValue();
                            rootDiamond.clear();
                            rootDiamond.add(targetNode);
                        }
                    }
                }
                if (rootDiamond.size() == 0) {
                    LayerLevelNodeCount.replace(node, LayerLevelNodeCount.get(node).intValue(), 0);
                    for (N targetNode : fGraph.targetNodeSet(node)) {
                        if (LayerLevelNodeCount.keySet().contains(targetNode)) {
                            LayerLevelNodeCount.replace(targetNode, LayerLevelNodeCount.get(targetNode).intValue(), 1);
                        }
                    }
                } else {
                    LayerLevelNodeCount.replace(node, LayerLevelNodeCount.get(node).intValue(), minLevelCount + 1);
                    for (N targetNode : fGraph.targetNodeSet(node)) {
                        if (LayerLevelNodeCount.keySet().contains(targetNode)) {
                            if ((LayerLevelNodeCount.get(targetNode).intValue() <= minLevelCount + 2 && !rootDiamond.contains(targetNode)) || (LayerLevelNodeCount.get(targetNode).intValue() <= minLevelCount + 2 && fGraph.sourceNodeSet(targetNode).size() <= 1)) {
                                LayerLevelNodeCount.replace(targetNode, LayerLevelNodeCount.get(targetNode).intValue(), minLevelCount + 2);
                            }
                        }
                    }
                }
            }
        }

    }

    private void AddDiamondNodesToLayers() {
        for (N dnode : DiamondNodes) {
            boolean isInRoot = true;
            for (N targetNode : fGraph.targetNodeSet(dnode)) {
                if (fGraph.numIncomingEdges(targetNode) > 1 || fGraph.numOutgoingEdges(targetNode) > 0)
                    isInRoot = false;
            }
            if (isInRoot == true) {
                LayerLevelNodeCount.replace(dnode, 0);
                for (N targetNode : fGraph.targetNodeSet(dnode)) {
                    LayerLevelNodeCount.replace(targetNode, 1);
                }
                continue;
            }
            int minLayerCount = Integer.MAX_VALUE;
            boolean minFound = false;
            for (N targetNode : fGraph.targetNodeSet(dnode)) {
                if (fGraph.numIncomingEdges(targetNode) > 1 || fGraph.numOutgoingEdges(targetNode) > 0)
                    if (LayerLevelNodeCount.keySet().contains(targetNode))
                        if (minLayerCount > LayerLevelNodeCount.get(targetNode).intValue()) {
                            minLayerCount = LayerLevelNodeCount.get(targetNode).intValue();
                            minFound = true;
                        }
            }
            if (minFound == false)
                continue;
            LayerLevelNodeCount.replace(dnode, minLayerCount + 1);
            for (N targetNode : fGraph.targetNodeSet(dnode)) {

                if (LayerLevelNodeCount.get(targetNode).intValue() != minLayerCount)
                    LayerLevelNodeCount.replace(targetNode, minLayerCount + 2);
                else if (!(fGraph.numIncomingEdges(targetNode) > 1 || fGraph.numOutgoingEdges(targetNode) > 0))
                    LayerLevelNodeCount.replace(targetNode, minLayerCount + 2);
            }
        }
    }

    private void FindAssociations() {
        AssociationsLevelNodeCount = new LinkedHashMap<N, Integer>();
        AssociationsParentNode = new LinkedHashMap<N, N>();
        AssociationsSourceNode = new LinkedHashMap<N, N>();
        if (GetMaxLayerCount() <= 0)
            return;
        for (N node : AssociationNodes) {
            int firstLevel = 0;
            int SecondLevel = 0;

            if (node instanceof ClassNode) {
                if (((ClassNode) node).cls() instanceof MAssociationClass)
                    for (int i = 0; i < ((MAssociationClassImpl) ((ClassNode) node).getClassifier()).associationEnds().size(); i++) {
                        String parentName = (((MAssociationClassImpl) ((ClassNode) node).getClassifier()).associationEnds().get(i).cls().name().toString());
                        for (N nodeLevel : LayerLevelNodeCount.keySet()) {
                            if (((ClassNode) nodeLevel).name() == parentName) {
                                if (i == 0) {
                                    firstLevel = LayerLevelNodeCount.get(nodeLevel).intValue();
                                    AssociationsLevelNodeCount.put(node, firstLevel);
                                    AssociationsParentNode.put(node, nodeLevel);
                                } else {
                                    SecondLevel = LayerLevelNodeCount.get(nodeLevel).intValue();
                                    if (firstLevel < SecondLevel) {
                                        AssociationsSourceNode.put(node, AssociationsParentNode.get(node));
                                        AssociationsParentNode.remove(node);
                                        AssociationsLevelNodeCount.remove(node);
                                        AssociationsLevelNodeCount.put(node, SecondLevel);
                                        AssociationsParentNode.put(node, nodeLevel);
                                    } else {
                                        AssociationsSourceNode.put(node, nodeLevel);
                                    }
                                }
                            }
                        }
                    }
            }

            if (node instanceof ObjectNode) {
                for (int i = 0; i < ((MLinkObject) ((ObjectNode) node).object()).linkedObjects().size(); i++) {
                    String parentName = (((MLinkObject) ((ObjectNode) node).object()).linkedObjects().get(i).name().toString());
                    for (N nodeLevel : LayerLevelNodeCount.keySet()) {
                        if (((ObjectNode) nodeLevel).name() == parentName) {
                            if (i == 0) {
                                firstLevel = LayerLevelNodeCount.get(nodeLevel).intValue();
                                AssociationsLevelNodeCount.put(node, firstLevel);
                                AssociationsParentNode.put(node, nodeLevel);
                            } else {
                                SecondLevel = LayerLevelNodeCount.get(nodeLevel).intValue();
                                if (firstLevel < SecondLevel) {
                                    AssociationsSourceNode.put(node, AssociationsParentNode.get(node));
                                    AssociationsParentNode.remove(node);
                                    AssociationsLevelNodeCount.remove(node);
                                    AssociationsLevelNodeCount.put(node, SecondLevel);
                                    AssociationsParentNode.put(node, nodeLevel);
                                } else {
                                    AssociationsSourceNode.put(node, nodeLevel);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void SortLayoutLevels() {
        Map<N, Integer> sortedlayerLevelNodeCount = sortByValue(LayerLevelNodeCount);
        Map<N, Integer> tempNodes = new LinkedHashMap<N, Integer>();

        for (N node : sortedlayerLevelNodeCount.keySet()) {
            if (sortedlayerLevelNodeCount.get(node).intValue() == 0) {
                tempNodes.put(node, 0);
            }
        }

        while (tempNodes.size() != sortedlayerLevelNodeCount.size()) {
            for (int i = 0; i <= GetMaxLayerCount(); i++) {
                for (N node : sortedlayerLevelNodeCount.keySet()) {
                    if (sortedlayerLevelNodeCount.get(node).intValue() == i) {
                        if (!tempNodes.containsKey(node)) {
                            tempNodes.put(node, i);
                        }
                        for (N sourceNode : fGraph.sourceNodeSet(node)) {
                            if (sourceNode instanceof DiamondNode) {

                                for (N n : LayerLevelNodeCount.keySet()) {
                                    if (n != sourceNode && fGraph.sourceNodeSet(n).contains(sourceNode)) {
                                        if (LayerLevelNodeCount.get(n).intValue() == i + 1)
                                            tempNodes.put(n, i + 1);
                                    }
                                }
                            }
                        }
                        for (N targetNode : fGraph.targetNodeSet(node)) {
                            if (!tempNodes.containsKey(targetNode) && sortedlayerLevelNodeCount.keySet().contains(targetNode))
                                if (sortedlayerLevelNodeCount.get(targetNode).intValue() == i + 1) {
                                    for (N n : LayerLevelNodeCount.keySet()) {
                                        if (LayerLevelNodeCount.get(n).intValue() == i + 1) {
                                            if (fGraph.sourceNodeSet(n).size() == 0) {
                                                for (N levelTargetNode : fGraph.targetNodeSet(n)) {
                                                    if (fGraph.sourceNodeSet(levelTargetNode).contains(targetNode))
                                                        if (targetNode != levelTargetNode)
                                                            tempNodes.put(n, i + 1);
                                                }
                                            }
                                        }
                                    }
                                    tempNodes.put(targetNode, i + 1);
                                }
                        }
                    }
                }
            }
        }

        LayerLevelNodeCount = new LinkedHashMap<N, Integer>();
        LayerLevelNodeCount.putAll(tempNodes);
    }

    private int GetMaxLayerCount() {
        int maxLayerCount = 0;
        for (int layerCount : LayerLevelNodeCount.values()) {
            if (layerCount > maxLayerCount) {
                maxLayerCount = layerCount;
            }
        }
        return maxLayerCount;
    }

    private void GetMaxLayerWidth() {
        double sumNodeWidth = 0;
        double maxSumNodeWidth = 0;
        int maxCount = 0;
        int maxAssCount = 0;
        maxLayerWidth = 0;

        for (int i = 0; i <= GetMaxLayerCount(); i++) {
            int count = 0;
            int assCount = 0;
            for (N node : LayerLevelNodeCount.keySet()) {
                if (LayerLevelNodeCount.get(node).intValue() == i) {
                    sumNodeWidth += node.getWidth();
                    count++;
                }
            }
            for (N n : AssociationsLevelNodeCount.keySet()) {
                if (AssociationsLevelNodeCount.get(n).intValue() == i) {
                    sumNodeWidth += (2 * n.getWidth());
                    assCount++;
                }
            }

            if (sumNodeWidth > maxSumNodeWidth) {
                maxSumNodeWidth = sumNodeWidth;
                maxCount = count;
            }
            if (assCount > maxAssCount) {
                maxAssCount = assCount;
            }
            sumNodeWidth = 0;
        }
        if (maxAssCount > 0) {
            maxSumNodeWidth += ((maxCount - 1) * HorizontalSpacing) + ((maxAssCount - 1) * fMarginX) + fMarginX;
        } else {
            maxSumNodeWidth += ((maxCount - 1) * HorizontalSpacing) + fMarginX;
        }
        maxLayerWidth = maxSumNodeWidth;
    }

    private void GetMaxLayerHeight() {
        double sumNodeHeight = 0;
        double maxSumNodeHeight = 0;
        int maxCount = 0;
        int maxAssCount = 0;
        maxLayerHeight = 0;

        for (int i = 0; i <= GetMaxLayerCount(); i++) {
            int count = 0;
            int assCount = 0;
            for (N node : LayerLevelNodeCount.keySet()) {
                if (LayerLevelNodeCount.get(node).intValue() == i) {
                    sumNodeHeight += node.getHeight();
                    count++;
                }
            }
            for (N n : AssociationsLevelNodeCount.keySet()) {
                if (AssociationsLevelNodeCount.get(n).intValue() == i) {
                    sumNodeHeight += (2 * n.getHeight());
                    assCount++;
                }
            }

            if (sumNodeHeight > maxSumNodeHeight) {
                maxSumNodeHeight = sumNodeHeight;
                maxCount = count;
            }
            if (assCount > maxAssCount) {
                maxAssCount = assCount;
            }
            sumNodeHeight = 0;
        }
        if (maxAssCount > 0) {
            maxSumNodeHeight += ((maxCount - 1) * VerticalSpacing) + ((maxAssCount - 1) * fMarginY) + fMarginY;
        } else {
            maxSumNodeHeight += ((maxCount - 1) * VerticalSpacing) + fMarginY;
        }
        maxLayerHeight = maxSumNodeHeight;
    }

    private void DrawHierarchical(N node) {
        double maxHeight = 0;
        double maxLevelNodeWidth = 0;
        double levelMaxHeight = 0;
        double associationMaxHeight = 0;
        GetMaxLayerWidth();

        for (int i = 0; i <= GetMaxLayerCount(); i++) {
            int countInLevel = 0;
            int countInAssociation = 0;
            levelMaxHeight = 0;
            associationMaxHeight = 0;
            for (N n : LayerLevelNodeCount.keySet()) {
                if (LayerLevelNodeCount.get(n).intValue() == i) {
                    countInLevel++;
                    if (fGraph.edgesBetween(n, n, false).size() == 1) {
                        if (n.getHeight() + 50 > levelMaxHeight)
                            levelMaxHeight = n.getHeight() + 50;
                    } else if (n.getHeight() > levelMaxHeight)
                        levelMaxHeight = n.getHeight();

                    maxLevelNodeWidth += n.getWidth();
                }
            }

            for (N n : AssociationsLevelNodeCount.keySet()) {
                if (AssociationsLevelNodeCount.get(n).intValue() == i) {
                    countInAssociation++;
                    if (n.getHeight() > associationMaxHeight)
                        associationMaxHeight = n.getHeight();

                    maxLevelNodeWidth += n.getWidth();
                }
            }

            if (i == 0) {
                maxHeight += levelMaxHeight / 2 + fMarginY;
            } else {
                maxHeight += levelMaxHeight / 2 + associationMaxHeight;
            }

            if (countInLevel == 1) {
                for (N n : LayerLevelNodeCount.keySet()) {
                    if (LayerLevelNodeCount.get(n).intValue() == i && !TracedNodes.contains(n)) {
                        if (i == 0) {
                            n.setCenter((maxLayerWidth / 2) + startLayerX, maxHeight);
                            TracedNodes.add(n);
                        } else if (fGraph.sourceNodeSet(n).size() == 0 || fGraph.sourceNodeSet(n).size() > 1) {
                            n.setCenter((maxLayerWidth / 2) + startLayerX, maxHeight);
                            TracedNodes.add(n);
                        } else if (fGraph.sourceNodeSet(n).size() == 1) {
                            n.setCenter(fGraph.sourceNodeSet(n).iterator().next().getCenter().getX(), maxHeight);
                            TracedNodes.add(n);
                        }
                    }
                }
            } else {
                int nodeCounter = 0;
                double currentXPosition = 0;
                for (N n : LayerLevelNodeCount.keySet()) {
                    if (LayerLevelNodeCount.get(n).intValue() == i && !TracedNodes.contains(n)) {
                        if (nodeCounter == 0) {
                            n.setCenter(((maxLayerWidth / (countInLevel + 1)) * ++nodeCounter) + startLayerX - (n.getWidth() / 2), maxHeight);
                            currentXPosition = n.getCenter().getX() + (n.getWidth() / 2) + HorizontalSpacing;
                        } else {
                            n.setCenter(currentXPosition + (n.getWidth() / 2), maxHeight);
                            currentXPosition += HorizontalSpacing + n.getWidth();
                        }
                        TracedNodes.add(n);
                    }
                }
            }
            if (countInAssociation != 0) {
                for (N n : AssociationsLevelNodeCount.keySet()) {
                    if (AssociationsLevelNodeCount.get(n).intValue() == i) {
                        for (N anode : AssociationsParentNode.keySet()) {
                            if (anode == n) {
                                double parentX = 0;
                                double parentY = 0;
                                double sourceX = 0;
                                double sourceY = 0;
                                if (AssociationsParentNode.get(anode) == null || AssociationsSourceNode.get(anode) == null) {
                                    continue;
                                }
                                if (AssociationsParentNode.get(anode) != null) {
                                    parentX = AssociationsParentNode.get(anode).getCenter().getX();
                                    parentY = AssociationsParentNode.get(anode).getCenter().getY();
                                }
                                if (AssociationsSourceNode.get(anode) != null) {
                                    sourceX = AssociationsSourceNode.get(anode).getCenter().getX();
                                    sourceY = AssociationsSourceNode.get(anode).getCenter().getY();
                                }
                                double currentX = 0;
                                double currentY = 0;
                                if (parentX > sourceX) {
                                    currentX = ((parentX - sourceX) / 2) + sourceX;
                                } else if (parentX < sourceX) {
                                    currentX = ((sourceX - parentX) / 2) + parentX;
                                } else if (parentX == sourceX) {
                                    currentX = parentX;
                                }

                                if (parentY > sourceY) {
                                    currentY = ((parentY - sourceY) / 2) + sourceY;
                                } else if (parentY < sourceY) {
                                    currentY = ((sourceY - parentY) / 2) + parentY;
                                } else if (parentY == sourceY) {
                                    currentY = parentY;
                                }
                                if (IsPutAssociationsOnRelationsEnabled == true) {
                                    if (n instanceof ClassNode)
                                        ((ClassNode) n).moveToPosition(currentX - (n.getWidth() / 2), currentY - (n.getHeight() / 2));
                                    else
                                        ((ObjectNode) n).moveToPosition(currentX - (n.getWidth() / 2), currentY - (n.getHeight() / 2));
                                } else {
                                    if (n instanceof ClassNode)
                                        ((ClassNode) n).moveToPosition((currentX - (n.getWidth() / 2)) + n.getWidth(), currentY - (n.getHeight() / 2));
                                    else
                                        ((ObjectNode) n).moveToPosition((currentX - (n.getWidth() / 2)) + n.getWidth(), currentY - (n.getHeight() / 2));
                                }
                            }
                        }
                    }
                }
            }
            maxHeight += levelMaxHeight / 2 + VerticalSpacing;
        }
    }

    private void DrawHorizontal(N node) {
        double maxWidth = 0;
        double maxLevelNodeHeight = 0;
        double levelMaxWidth = 0;
        double associationMaxWidth = 0;
        GetMaxLayerHeight();

        for (int i = 0; i <= GetMaxLayerCount(); i++) {
            int countInLevel = 0;
            int countInAssociation = 0;
            levelMaxWidth = 0;
            associationMaxWidth = 0;
            for (N n : LayerLevelNodeCount.keySet()) {
                if (LayerLevelNodeCount.get(n).intValue() == i) {
                    countInLevel++;
                    if (fGraph.edgesBetween(n, n, false).size() == 1) {
                        if (n.getWidth() + 50 > levelMaxWidth)
                            levelMaxWidth = n.getWidth() + 50;
                    } else if (n.getWidth() > levelMaxWidth)
                        levelMaxWidth = n.getWidth();

                    maxLevelNodeHeight += n.getHeight();
                }
            }

            for (N n : AssociationsLevelNodeCount.keySet()) {
                if (AssociationsLevelNodeCount.get(n).intValue() == i) {
                    countInAssociation++;
                    if (n.getWidth() > associationMaxWidth)
                        associationMaxWidth = n.getWidth();

                    maxLevelNodeHeight += n.getHeight();
                }
            }

            if (i == 0) {
                maxWidth += levelMaxWidth / 2 + fMarginX;
            } else {
                maxWidth += levelMaxWidth / 2 + associationMaxWidth;
            }

            if (countInLevel == 1) {
                for (N n : LayerLevelNodeCount.keySet()) {
                    if (LayerLevelNodeCount.get(n).intValue() == i && !TracedNodes.contains(n)) {
                        if (i == 0) {
                            n.setCenter(maxWidth, (maxLayerHeight / 2) + startLayerY);
                            TracedNodes.add(n);
                        } else if (fGraph.sourceNodeSet(n).size() == 0 || fGraph.sourceNodeSet(n).size() > 1) {
                            n.setCenter(maxWidth, (maxLayerHeight / 2) + startLayerY);
                            TracedNodes.add(n);
                        } else if (fGraph.sourceNodeSet(n).size() == 1) {
                            n.setCenter(maxWidth, fGraph.sourceNodeSet(n).iterator().next().getCenter().getY());
                            TracedNodes.add(n);
                        }
                    }
                }
            } else {
                int nodeCounter = 0;
                double currentYPosition = 0;
                for (N n : LayerLevelNodeCount.keySet()) {
                    if (LayerLevelNodeCount.get(n).intValue() == i && !TracedNodes.contains(n)) {
                        if (nodeCounter == 0) {
                            n.setCenter(maxWidth, ((maxLayerHeight / (countInLevel + 1)) * ++nodeCounter) + startLayerY - (n.getHeight() / 2));
                            currentYPosition = n.getCenter().getY() + (n.getHeight() / 2) + VerticalSpacing;
                        } else {
                            n.setCenter(maxWidth, currentYPosition + (n.getHeight() / 2));
                            currentYPosition += VerticalSpacing + n.getHeight();
                        }
                        TracedNodes.add(n);
                    }
                }
            }

            if (countInAssociation != 0) {
                for (N n : AssociationsLevelNodeCount.keySet()) {
                    if (AssociationsLevelNodeCount.get(n).intValue() == i) {
                        for (N anode : AssociationsParentNode.keySet()) {
                            if (anode == n) {
                                double parentX = 0;
                                double parentY = 0;
                                double sourceX = 0;
                                double sourceY = 0;
                                if (AssociationsParentNode.get(anode) == null || AssociationsSourceNode.get(anode) == null) {
                                    continue;
                                }
                                if (AssociationsParentNode.get(anode) != null) {
                                    parentX = AssociationsParentNode.get(anode).getCenter().getX();
                                    parentY = AssociationsParentNode.get(anode).getCenter().getY();
                                }
                                if (AssociationsSourceNode.get(anode) != null) {
                                    sourceX = AssociationsSourceNode.get(anode).getCenter().getX();
                                    sourceY = AssociationsSourceNode.get(anode).getCenter().getY();
                                }
                                double currentX = 0;
                                double currentY = 0;
                                if (parentX > sourceX) {
                                    currentX = ((parentX - sourceX) / 2) + sourceX;
                                } else if (parentX < sourceX) {
                                    currentX = ((sourceX - parentX) / 2) + parentX;
                                } else if (parentX == sourceX) {
                                    currentX = parentX;
                                }

                                if (parentY > sourceY) {
                                    currentY = ((parentY - sourceY) / 2) + sourceY;
                                } else if (parentY < sourceY) {
                                    currentY = ((sourceY - parentY) / 2) + parentY;
                                } else if (parentY == sourceY) {
                                    currentY = parentY;
                                }
                                if (IsPutAssociationsOnRelationsEnabled == true) {
                                    if (n instanceof ClassNode)
                                        ((ClassNode) n).moveToPosition(currentX - (n.getWidth() / 2), currentY - (n.getHeight() / 2));
                                    else
                                        ((ObjectNode) n).moveToPosition(currentX - (n.getWidth() / 2), currentY - (n.getHeight() / 2));
                                } else {
                                    if (n instanceof ClassNode)
                                        ((ClassNode) n).moveToPosition(currentX - (n.getWidth() / 2) + n.getWidth(), currentY - (n.getHeight() / 2));
                                    else
                                        ((ObjectNode) n).moveToPosition(currentX - (n.getWidth() / 2) + n.getWidth(), currentY - (n.getHeight() / 2));
                                }
                            }
                        }
                    }
                }
            }
            maxWidth += levelMaxWidth / 2 + HorizontalSpacing;
        }
    }

    private void DrawHierarchicalDownUp() {
        double maxY = 0;
        for (N n : fGraph.getNodes()) {
            if (n.getCenter().getY() + (n.getHeight() / 2) > maxY) {
                maxY = n.getCenter().getY() + (n.getHeight() / 2);
            }
        }

        maxY += fMarginY;
        for (N n : fGraph.getNodes()) {
            n.setCenter(n.getCenter().getX(), maxY - n.getCenter().getY());
        }
    }

    private void DrawHorizontalLeftRight() {
        double maxX = 0;
        for (N n : fGraph.getNodes()) {
            if (n.getCenter().getX() + (n.getWidth() / 2) > maxX) {
                maxX = n.getCenter().getX() + (n.getWidth() / 2);
            }
        }

        maxX += fMarginX;
        for (N n : fGraph.getNodes()) {
            n.setCenter(maxX - n.getCenter().getX(), n.getCenter().getY());
        }
    }

    private Map<N, String> sortByStringValue(Map<N, String> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<N, String>> list =
                new LinkedList<Map.Entry<N, String>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<N, String>>() {
            public int compare(Map.Entry<N, String> o1,
                               Map.Entry<N, String> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<N, String> sortedMap = new LinkedHashMap<N, String>();
        for (Map.Entry<N, String> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    private Map<String, Integer> sortStringByIntegerValue(Map<String, Integer> unsortMap) {

        // 1. Convert Map to List of Map
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    private void SwimlaneLayout(LayoutType layoutType) {
        MakeAllParametersEmpty();
        SetRootNodes();

        Map<N, String> SwimlaneGroupping = new LinkedHashMap<N, String>();
        Map<String, Integer> SwimlaneGrouppingCount = new LinkedHashMap<String, Integer>();
        Map<N, Integer> SwimlaneGrouppingNodeCount = new LinkedHashMap<N, Integer>();
        Map<N, Integer> SwimlaneGrouppingRootNodeCount = new LinkedHashMap<N, Integer>();

        for (N n : fGraph.getNodes()) {
            if (n instanceof ObjectNode) {
                SwimlaneGroupping.put(n, ((ObjectNode) n).cls().name());
            }
        }

        Map<N, String> sortedSwimlaneGroupping = sortByStringValue(SwimlaneGroupping);
        SwimlaneGroupping = sortedSwimlaneGroupping;

        for (N node : SwimlaneGroupping.keySet()) {
            if (SwimlaneGrouppingCount.keySet().contains(SwimlaneGroupping.get(node))) {
                SwimlaneGrouppingCount.replace(SwimlaneGroupping.get(node), SwimlaneGrouppingCount.get(SwimlaneGroupping.get(node)) + 1);
            } else {
                SwimlaneGrouppingCount.put(SwimlaneGroupping.get(node), 1);
            }
        }

        SwimlaneGrouppingCount = sortStringByIntegerValue(SwimlaneGrouppingCount);

        if (SwimlaneGrouppingCount.size() <= 1) {
            return;
        }

        for (N n : SwimlaneGroupping.keySet()) {
            SwimlaneGrouppingNodeCount.put(n, SwimlaneGrouppingCount.get(SwimlaneGroupping.get(n)).intValue());
        }

        SwimlaneGrouppingNodeCount = sortByValue(SwimlaneGrouppingNodeCount);

        for (N n : RootNodes) {
            if (n instanceof DiamondNode)
                continue;
            SwimlaneGrouppingRootNodeCount.put(n, SwimlaneGrouppingNodeCount.get(n).intValue());
        }
        SwimlaneGrouppingRootNodeCount = sortByValue(SwimlaneGrouppingRootNodeCount);
        RootNodes.clear();
        for (N rootNode : SwimlaneGrouppingRootNodeCount.keySet()) {
            RootNodes.add(rootNode);
        }

        int count = 0;
        Map<N, String> tracedRootNodes = new LinkedHashMap<N, String>();
        Map<N, Integer> TempLayerLevelNodeCount = new LinkedHashMap<N, Integer>();

        if (RootNodes.size() == 0) {
            //Show all Nodes - there is not any edge
        } else {
            for (N rootNode : RootNodes) {
                if (tracedRootNodes.containsKey(rootNode)) {
                    continue;
                }
                LayerLevelNodeCount.put(rootNode, count);
                tracedRootNodes.put(rootNode, SwimlaneGroupping.get(rootNode));
                for (N n : SwimlaneGroupping.keySet()) {
                    if (SwimlaneGroupping.get(n) == ((ObjectNode) rootNode).cls().name()) {
                        LayerLevelNodeCount.put(n, count);
                        tracedRootNodes.put(n, SwimlaneGroupping.get(n));
                    }
                }

                sortLayerLevelNodeCount();
                SplitRootLayerLevel();
                count = GetMaxLayerCount() + 1;
                sortLayerLevelNodeCount();
            }
        }

        sortLayerLevelNodeCount();
        SplitRootLayerLevel();
        count = GetMaxLayerCount() + 1;
        sortLayerLevelNodeCount();
        TempSortedLayerLevelNodeCount.clear();

        do {
            TempLayerLevelNodeCount.clear();
            for (N node : LayerLevelNodeCount.keySet()) {
                for (N targetNode : fGraph.targetNodeSet(node)) {
                    if (tracedRootNodes.containsKey(targetNode)) {
                        continue;
                    }
                    TempLayerLevelNodeCount.put(targetNode, count);
                    tracedRootNodes.put(targetNode, SwimlaneGroupping.get(targetNode));
                    for (N nf : SwimlaneGroupping.keySet()) {
                        if (SwimlaneGroupping.get(nf) == ((ObjectNode) targetNode).cls().name()) {
                            TempLayerLevelNodeCount.put(nf, count);
                            tracedRootNodes.put(nf, SwimlaneGroupping.get(nf));
                        }
                    }
                    count++;
                }
            }
            LayerLevelNodeCount.putAll(TempLayerLevelNodeCount);
            sortLayerLevelNodeCount();
            SplitRootLayerLevel();
            count = GetMaxLayerCount() + 1;
            sortLayerLevelNodeCount();
        } while (TempLayerLevelNodeCount.size() > 0);

        do {
            TempLayerLevelNodeCount.clear();
            for (N node : LayerLevelNodeCount.keySet()) {
                for (N sourceNode : fGraph.sourceNodeSet(node)) {
                    if (tracedRootNodes.containsKey(sourceNode)) {
                        continue;
                    }
                    if (sourceNode instanceof DiamondNode) {
                        for (N n : fGraph.getNodes()) {
                            if (fGraph.sourceNodeSet(n).contains(sourceNode)) {
                                if (n != node && !tracedRootNodes.containsKey(n)) {

                                    TempLayerLevelNodeCount.put(sourceNode, count);
                                    tracedRootNodes.put(sourceNode, SwimlaneGroupping.get(sourceNode));
                                    count++;

                                    TempLayerLevelNodeCount.put(n, count);
                                    tracedRootNodes.put(n, SwimlaneGroupping.get(n));
                                    for (N nf : SwimlaneGroupping.keySet()) {
                                        if (SwimlaneGroupping.get(nf) == ((ObjectNode) n).cls().name()) {
                                            TempLayerLevelNodeCount.put(nf, count);
                                            tracedRootNodes.put(nf, SwimlaneGroupping.get(nf));
                                        }
                                    }
                                    count++;
                                }
                            }
                        }

                    } else {
                        TempLayerLevelNodeCount.put(sourceNode, count);
                        tracedRootNodes.put(sourceNode, SwimlaneGroupping.get(sourceNode));
                        for (N nf : SwimlaneGroupping.keySet()) {
                            if (SwimlaneGroupping.get(nf) == ((ObjectNode) sourceNode).cls().name()) {
                                TempLayerLevelNodeCount.put(nf, count);
                                tracedRootNodes.put(nf, SwimlaneGroupping.get(nf));
                            }
                        }
                        count++;
                    }
                }
            }
            LayerLevelNodeCount.putAll(TempLayerLevelNodeCount);
            sortLayerLevelNodeCount();
            SplitRootLayerLevel();
            count = GetMaxLayerCount() + 1;
            sortLayerLevelNodeCount();
        } while (TempLayerLevelNodeCount.size() > 0);

        int tempLevelCount1 = 0;
        int tempLevelCount2 = 0;
        do {
            tempLevelCount1 = GetMaxLayerCount();
            sortLayerLevelNodeCount();
            SplitRootLayerLevel();
            sortLayerLevelNodeCount();
            tempLevelCount2 = GetMaxLayerCount();

        } while (tempLevelCount1 != tempLevelCount2);

        SplitNotRelatedLayerLevel();
        if (layoutType == LayoutType.LandscapeSwimlane) {
            DrawLandscapeSwimlane();
        } else if (layoutType == LayoutType.PortraitSwimlane) {
            DrawPortraitSwimlane();
        }
    }

    private void SplitRootLayerLevel() {
        Map<N, Integer> LastLayerNodes = new LinkedHashMap<N, Integer>();
        for (int i = 0; i <= GetMaxLayerCount(); i++) {
            Map<N, Integer> TempSplitedLayerLevelNodeCount = new LinkedHashMap<N, Integer>();
            Map<N, Integer> LayerNodes = new LinkedHashMap<N, Integer>();
            Map<N, Integer> SpecialNodes = new LinkedHashMap<N, Integer>();
            for (N node : LayerLevelNodeCount.keySet()) {
                if (LayerLevelNodeCount.get(node).intValue() == i && !(node instanceof DiamondNode)) {
                    LayerNodes.put(node, LayerLevelNodeCount.get(node).intValue());
                }
                if (node instanceof DiamondNode) {
                    TempSplitedLayerLevelNodeCount.put(node, LayerLevelNodeCount.get(node).intValue());
                    SpecialNodes.put(node, LayerLevelNodeCount.get(node).intValue());
                }
            }
            if (LayerNodes.size() <= 1 || LastLayerNodes.keySet().containsAll(LayerNodes.keySet()))
                continue;

            TempSplitedLayerLevelNodeCount.put(LayerNodes.entrySet().iterator().next().getKey()
                    , LayerLevelNodeCount.get(LayerNodes.entrySet().iterator().next().getKey()).intValue());

            for (N firstNode : LayerNodes.keySet()) {
                for (N secondNode : LayerNodes.keySet()) {
                    if (firstNode == secondNode) {
                        continue;
                    }
                    for (DirectedEdge e : fGraph.edgesBetween(firstNode, secondNode)) {
                        if (((AssociationOrLinkPartEdge) (e)).getAssociation().aggregationKind() == MAggregationKind.AGGREGATION) {
                            if (TempSplitedLayerLevelNodeCount.keySet().contains(firstNode)) {
                                TempSplitedLayerLevelNodeCount.put(secondNode, LayerNodes.get(secondNode).intValue());
                            }
                        }
                    }
                }
            }
            LastLayerNodes.clear();
            LastLayerNodes = LayerNodes;


            Set<N> tempNode = new HashSet<N>();
            if (TempSplitedLayerLevelNodeCount.size() - SpecialNodes.size() == 1 && LayerNodes.size() > 1) {
                Map<N, Integer> sortThisLayer = new LinkedHashMap<N, Integer>();
                LayerLevelNodeCount.keySet().removeAll(LayerNodes.keySet());
                for (N node : LayerLevelNodeCount.keySet()) {
                    if (!LayerLevelNodeCount.keySet().contains(node))
                        continue;
                    N Tnode = TempSplitedLayerLevelNodeCount.keySet().iterator().next();
                    if (!LayerNodes.containsKey(Tnode))
                        continue;
                    if (LayerLevelNodeCount.get(node).intValue() < LayerNodes.get(Tnode).intValue()) {
                        for (N targetNode : fGraph.targetNodeSet(node)) {
                            if (LayerNodes.keySet().contains(targetNode)) {
                                sortThisLayer.put(targetNode, LayerNodes.get(targetNode).intValue());
                            }
                        }
                    }
                }
                LayerLevelNodeCount.putAll(sortThisLayer);
                for (N node : LayerNodes.keySet()) {
                    if (!LayerLevelNodeCount.keySet().contains(node)) {
                        LayerLevelNodeCount.put(node, LayerNodes.get(node).intValue());
                    }
                }
            }
            if (TempSplitedLayerLevelNodeCount.size() - SpecialNodes.size() > 1 && !TempSplitedLayerLevelNodeCount.keySet().containsAll(LayerNodes.keySet())) {
                Integer level = TempSplitedLayerLevelNodeCount.get(TempSplitedLayerLevelNodeCount.keySet().iterator().next()).intValue();
                Integer max = GetMaxLayerCount();
                for (int j = max; j >= level; j--) {
                    for (N n : LayerLevelNodeCount.keySet()) {
                        if (LayerLevelNodeCount.get(n).intValue() == j) {
                            if (j == level) {
                                if (!tempNode.contains(n) && !TempSplitedLayerLevelNodeCount.keySet().contains(n)) {
                                    LayerLevelNodeCount.replace(n, j + 1);
                                    tempNode.add(n);
                                }
                            } else {
                                if (!tempNode.contains(n)) {
                                    LayerLevelNodeCount.replace(n, j + 1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    Map<N, Integer> TempSortedLayerLevelNodeCount = new LinkedHashMap<N, Integer>();

    private void sortLayerLevelNodeCount() {
        TempSortedLayerLevelNodeCount = new LinkedHashMap<N, Integer>();
        for (int i = 0; i <= GetMaxLayerCount(); i++) {
            Map<N, Integer> LayerNodes = new LinkedHashMap<N, Integer>();
            Map<N, Integer> SpecialNodes = new LinkedHashMap<N, Integer>();
            for (N node : LayerLevelNodeCount.keySet()) {
                if (LayerLevelNodeCount.get(node).intValue() == i && !(node instanceof DiamondNode)) {
                    LayerNodes.put(node, LayerLevelNodeCount.get(node).intValue());
                }
                if (node instanceof DiamondNode) {
                    TempSortedLayerLevelNodeCount.put(node, LayerLevelNodeCount.get(node).intValue());
                    SpecialNodes.put(node, LayerLevelNodeCount.get(node).intValue());
                }
            }
            if (LayerNodes.size() <= 1)
                continue;

            for (N node : LayerNodes.keySet()) {
                boolean isInLevel = false;
                for (N sourceNode : fGraph.sourceNodeSet(node)) {
                    if (LayerNodes.keySet().contains(sourceNode)) {
                        isInLevel = true;
                        break;
                    }
                }
                if (isInLevel == false) {
                    TempSortedLayerLevelNodeCount.put(node, LayerLevelNodeCount.get(node).intValue());
                    AddTargetToLayerLevelNodeCount(LayerNodes, node);
                }
            }

            if (TempSortedLayerLevelNodeCount.size() - SpecialNodes.size() == 0) {
                TempSortedLayerLevelNodeCount.put(LayerNodes.entrySet().iterator().next().getKey()
                        , LayerLevelNodeCount.get(LayerNodes.entrySet().iterator().next().getKey()).intValue());
                AddTargetToLayerLevelNodeCount(LayerNodes, LayerNodes.entrySet().iterator().next().getKey());
            }


            if (TempSortedLayerLevelNodeCount.keySet().containsAll(LayerNodes.keySet())) {
                LayerLevelNodeCount.keySet().removeAll(TempSortedLayerLevelNodeCount.keySet());
                LayerLevelNodeCount.putAll(TempSortedLayerLevelNodeCount);
            } else {
                if (i != 0) {
                    TempSortedLayerLevelNodeCount.clear();
                    for (N node : LayerLevelNodeCount.keySet()) {
                        if (node instanceof DiamondNode) {
                            TempSortedLayerLevelNodeCount.put(node, LayerLevelNodeCount.get(node).intValue());
                        }
                    }
                    for (N node : sortByValue(LayerLevelNodeCount).keySet()) {
                        if (LayerLevelNodeCount.get(node).intValue() < i) {
                            for (N layerNode : LayerNodes.keySet()) {
                                if (fGraph.targetNodeSet(node).contains(layerNode)) {
                                    TempSortedLayerLevelNodeCount.put(layerNode, LayerLevelNodeCount.get(layerNode).intValue());
                                }
                            }
                        }
                    }
                    for (N node : LayerNodes.keySet()) {
                        if (!TempSortedLayerLevelNodeCount.containsKey(node)) {
                            TempSortedLayerLevelNodeCount.put(node, LayerLevelNodeCount.get(node).intValue());
                        }
                    }
                    LayerLevelNodeCount.keySet().removeAll(TempSortedLayerLevelNodeCount.keySet());
                    LayerLevelNodeCount.putAll(TempSortedLayerLevelNodeCount);
                }
            }
            TempSortedLayerLevelNodeCount.clear();
        }
    }

    private void AddTargetToLayerLevelNodeCount(Map<N, Integer> LayerNodes, N n) {

        if (fGraph.targetNodeSet(n).size() == 0 || (fGraph.targetNodeSet(n).size() == 1 && fGraph.targetNodeSet(n).contains(n)))
            return;
        boolean isAny = false;
        for (N targetNode : fGraph.targetNodeSet(n)) {
            if (LayerNodes.keySet().contains(targetNode) && LayerNodes.get(targetNode) == LayerNodes.get(n))
                isAny = true;
        }
        if (isAny == false)
            return;

        for (N targetNode : fGraph.targetNodeSet(n)) {
            if (!LayerNodes.keySet().contains(targetNode))
                continue;
            if (targetNode != n && LayerNodes.get(targetNode) == LayerNodes.get(n)) {
                if (!TempSortedLayerLevelNodeCount.keySet().contains(targetNode))
                    TempSortedLayerLevelNodeCount.put(targetNode, LayerNodes.get(targetNode));
            }
        }
        for (N targetNode : fGraph.targetNodeSet(n)) {
            if (!LayerNodes.keySet().contains(targetNode))
                continue;
            if (targetNode != n && LayerNodes.get(targetNode) == LayerNodes.get(n)) {
                AddTargetToLayerLevelNodeCount(LayerNodes, targetNode);
            }
        }
    }

    private void SplitNotRelatedLayerLevel() {
        Map<N, Integer> LastLayerNodes = new LinkedHashMap<N, Integer>();
        for (int i = 0; i <= GetMaxLayerCount(); i++) {
            Map<N, Integer> TempSplitedLayerLevelNodeCount = new LinkedHashMap<N, Integer>();
            Map<N, Integer> LayerNodes = new LinkedHashMap<N, Integer>();
            Map<N, Integer> SpecialNodes = new LinkedHashMap<N, Integer>();
            for (N node : LayerLevelNodeCount.keySet()) {
                if (LayerLevelNodeCount.get(node).intValue() == i && !(node instanceof DiamondNode)) {
                    LayerNodes.put(node, LayerLevelNodeCount.get(node).intValue());
                }
                if (node instanceof DiamondNode) {
                    TempSplitedLayerLevelNodeCount.put(node, LayerLevelNodeCount.get(node).intValue());
                    SpecialNodes.put(node, LayerLevelNodeCount.get(node).intValue());
                }
            }
            if (LayerNodes.size() <= 1)
                continue;

            TempSplitedLayerLevelNodeCount.put(LayerNodes.entrySet().iterator().next().getKey()
                    , LayerLevelNodeCount.get(LayerNodes.entrySet().iterator().next().getKey()).intValue());

            for (N firstNode : LayerNodes.keySet()) {
                for (N secondNode : LayerNodes.keySet()) {
                    if (firstNode == secondNode) {
                        continue;
                    }
                    for (DirectedEdge e : fGraph.edgesBetween(firstNode, secondNode)) {
                        if (((AssociationOrLinkPartEdge) (e)).getAssociation().aggregationKind() == MAggregationKind.AGGREGATION) {
                            if (TempSplitedLayerLevelNodeCount.keySet().contains(firstNode)) {
                                TempSplitedLayerLevelNodeCount.put(secondNode, LayerNodes.get(secondNode).intValue());
                            }
                        }
                    }
                }
            }
            LastLayerNodes.clear();

            Set<N> tempNode = new HashSet<N>();
            if (TempSplitedLayerLevelNodeCount.size() - SpecialNodes.size() == 1 && LayerNodes.size() > 1) {
                Map<N, Integer> sortThisLayer = new LinkedHashMap<N, Integer>();
                LayerLevelNodeCount.keySet().removeAll(LayerNodes.keySet());
                for (N node : LayerLevelNodeCount.keySet()) {
                    if (!LayerLevelNodeCount.keySet().contains(node))
                        continue;
                    N Tnode = TempSplitedLayerLevelNodeCount.keySet().iterator().next();
                    if (!LayerNodes.containsKey(Tnode))
                        continue;
                    if (LayerLevelNodeCount.get(node).intValue() < LayerNodes.get(Tnode).intValue()) {
                        for (N targetNode : fGraph.targetNodeSet(node)) {
                            if (LayerNodes.keySet().contains(targetNode)) {
                                sortThisLayer.put(targetNode, LayerNodes.get(targetNode).intValue());
                            }
                        }
                    }
                }
                LayerLevelNodeCount.putAll(sortThisLayer);
                for (N node : LayerNodes.keySet()) {
                    if (!LayerLevelNodeCount.keySet().contains(node)) {
                        LayerLevelNodeCount.put(node, LayerNodes.get(node).intValue());
                    }
                }
            }
        }
    }

    private void DrawLandscapeSwimlane() {
        double currentX = fMarginX;
        double currentY = fMarginY;
        double maxCurrentHeight = 0;
        double maxCurrentWidth = 0;
        double sourceCurrnetX = 0;
        double sourceWidth = 0;

        for (int i = 0; i <= GetMaxLayerCount(); i++) {
            maxCurrentHeight = 0;
            maxCurrentWidth = 0;
            sourceCurrnetX = 0;
            sourceWidth = 0;
            for (N node : LayerLevelNodeCount.keySet()) {
                if (LayerLevelNodeCount.get(node).intValue() == i) {
                    if (node.getWidth() > maxCurrentWidth) {
                        maxCurrentWidth = node.getWidth();
                    }
                }
            }
            for (N node : LayerLevelNodeCount.keySet()) {
                if (LayerLevelNodeCount.get(node).intValue() == i) {
                    if (LayerLevelNodeCount.get(node).intValue() == i) {
                        if (node.getHeight() > maxCurrentHeight) {
                            maxCurrentHeight = node.getHeight();
                        }
                    }
                }
            }
            currentY += (maxCurrentHeight / 2);
            for (N node : LayerLevelNodeCount.keySet()) {
                if (LayerLevelNodeCount.get(node).intValue() == i) {
                    //First Node in Level
                    if (node instanceof DiamondNode) {
                        for (N parentNode : LayerLevelNodeCount.keySet()) {
                            if (LayerLevelNodeCount.get(parentNode).intValue() < i) {
                                if (fGraph.sourceNodeSet(node).contains(parentNode)) {
                                    sourceCurrnetX = (parentNode.getCenter().getX());
                                    sourceWidth = parentNode.getWidth();
                                    break;
                                }
                            }
                        }
                    } else {
                        boolean hasTarget = false;
                        for (N parentNode : LayerLevelNodeCount.keySet()) {
                            if (LayerLevelNodeCount.get(parentNode).intValue() < i) {
                                if (fGraph.targetNodeSet(node).contains(parentNode)) {
                                    sourceCurrnetX = (parentNode.getCenter().getX());
                                    sourceWidth = parentNode.getWidth();
                                    hasTarget = true;
                                    break;
                                }
                            }
                        }
                        if (hasTarget == false) {
                            for (N parentNode : LayerLevelNodeCount.keySet()) {
                                if (LayerLevelNodeCount.get(parentNode).intValue() < i) {
                                    if (fGraph.sourceNodeSet(node).contains(parentNode)) {
                                        sourceCurrnetX = (parentNode.getCenter().getX());
                                        sourceWidth = parentNode.getWidth();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
            }
            if (sourceCurrnetX > 0) {
                for (N node : LayerLevelNodeCount.keySet()) {
                    if (LayerLevelNodeCount.get(node).intValue() == i) {
                        if (sourceWidth >= node.getWidth()) {
                            currentX = sourceCurrnetX - (node.getWidth() / 2);
                            break;
                        } else {
                            currentX = (sourceCurrnetX - (sourceWidth / 2)) + ((node.getWidth() / 2) - (sourceWidth / 2));
                            break;
                        }
                    }
                }
            }
            for (N node : LayerLevelNodeCount.keySet()) {
                if (LayerLevelNodeCount.get(node).intValue() == i) {
                    currentX += (node.getWidth() / 2);
                    node.setCenter(currentX, currentY);
                    currentX += (node.getWidth() / 2) + HorizontalSpacing;
                }
            }
            currentX = fMarginX;
            currentY += (maxCurrentHeight / 2) + VerticalSpacing;
        }
    }

    private void DrawPortraitSwimlane() {
        double currentX = fMarginX;
        double currentY = fMarginY;
        double maxCurrentHeight = 0;
        double maxCurrentWidth = 0;
        double sourceCurrnetY = 0;
        double sourceHeight = 0;

        for (int i = 0; i <= GetMaxLayerCount(); i++) {
            maxCurrentHeight = 0;
            maxCurrentWidth = 0;
            sourceCurrnetY = 0;
            sourceHeight = 0;
            for (N node : LayerLevelNodeCount.keySet()) {
                if (LayerLevelNodeCount.get(node).intValue() == i) {
                    if (node.getWidth() > maxCurrentWidth) {
                        maxCurrentWidth = node.getWidth();
                    }
                }
            }
            for (N node : LayerLevelNodeCount.keySet()) {
                if (LayerLevelNodeCount.get(node).intValue() == i) {
                    if (LayerLevelNodeCount.get(node).intValue() == i) {
                        if (node.getHeight() > maxCurrentHeight) {
                            maxCurrentHeight = node.getHeight();
                        }
                    }
                }
            }
            currentX += (maxCurrentWidth / 2);
            for (N node : LayerLevelNodeCount.keySet()) {
                if (LayerLevelNodeCount.get(node).intValue() == i) {
                    //First Node in Level
                    if (node instanceof DiamondNode) {
                        for (N parentNode : LayerLevelNodeCount.keySet()) {
                            if (LayerLevelNodeCount.get(parentNode).intValue() < i) {
                                if (fGraph.sourceNodeSet(node).contains(parentNode)) {
                                    sourceCurrnetY = (parentNode.getCenter().getY());
                                    sourceHeight = parentNode.getWidth();
                                    break;
                                }
                            }
                        }
                    } else {
                        boolean hasTarget = false;
                        for (N parentNode : LayerLevelNodeCount.keySet()) {
                            if (LayerLevelNodeCount.get(parentNode).intValue() < i) {
                                if (fGraph.targetNodeSet(node).contains(parentNode)) {
                                    sourceCurrnetY = (parentNode.getCenter().getY());
                                    sourceHeight = parentNode.getWidth();
                                    hasTarget = true;
                                    break;
                                }
                            }
                        }
                        if (hasTarget == false) {
                            for (N parentNode : LayerLevelNodeCount.keySet()) {
                                if (LayerLevelNodeCount.get(parentNode).intValue() < i) {
                                    if (fGraph.sourceNodeSet(node).contains(parentNode)) {
                                        sourceCurrnetY = (parentNode.getCenter().getY());
                                        sourceHeight = parentNode.getWidth();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
            }
            if (sourceCurrnetY > 0) {
                for (N node : LayerLevelNodeCount.keySet()) {
                    if (LayerLevelNodeCount.get(node).intValue() == i) {
                        if (sourceHeight >= node.getHeight()) {
                            currentY = sourceCurrnetY - (node.getHeight() / 2);
                            break;
                        } else {
                            currentY = (sourceCurrnetY - (sourceHeight / 2)) + ((node.getHeight() / 2) - (sourceHeight / 2));
                            break;
                        }
                    }
                }
            }
            for (N node : LayerLevelNodeCount.keySet()) {
                if (LayerLevelNodeCount.get(node).intValue() == i) {
                    currentY += (node.getHeight() / 2);
                    node.setCenter(currentX, currentY);
                    currentY += (node.getHeight() / 2) + VerticalSpacing;
                }
            }
            currentY = fMarginY;
            currentX += (maxCurrentWidth / 2) + HorizontalSpacing;
        }
    }

    private void MoveHiddenObjectsDown() {
        double maxY = 0;
        double maxHiddenWidth = 0;
        for (N node : fGraph.getNodes()) {
            if (node.getCenter().getY() > maxY) {
                maxY = node.getCenter().getY();
            }
        }
        for (N node : HiddenNodes) {
            if (node.getWidth() > maxHiddenWidth) {
                maxHiddenWidth = node.getWidth();
            }
        }
        for (N node : HiddenNodes) {
            node.setCenter(fMarginX + (maxHiddenWidth / 2), maxY + fEdgeLen);
        }
    }

    private void MoveHiddenObjectsRight() {
        double maxX = 0;
        double maxHiddenHeight = 0;
        for (N node : fGraph.getNodes()) {
            if (node.getCenter().getX() > maxX) {
                maxX = node.getCenter().getX();
            }
        }
        for (N node : HiddenNodes) {
            if (node.getHeight() > maxHiddenHeight) {
                maxHiddenHeight = node.getHeight();
            }
        }
        for (N node : HiddenNodes) {
            node.setCenter(maxX + fEdgeLen, fMarginY + (maxHiddenHeight / 2));
        }
    }
}