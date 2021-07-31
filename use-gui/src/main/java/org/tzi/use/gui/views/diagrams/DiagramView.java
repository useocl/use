/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

// $Id: DiagramView.java 6552 2019-08-11 13:03:32Z  $

package org.tzi.use.gui.views.diagrams;

import com.ximpleware.ParseException;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.tzi.use.config.Options;
import org.tzi.use.gui.graphlayout.AllLayoutTypes;
import org.tzi.use.gui.graphlayout.SpringLayout;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.util.PersistHelper;
import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.util.StatusBar;
import org.tzi.use.gui.views.diagrams.elements.CommentNode;
import org.tzi.use.gui.views.diagrams.elements.EdgeProperty;
import org.tzi.use.gui.views.diagrams.elements.PlaceableNode;
import org.tzi.use.gui.views.diagrams.elements.Rolename;
import org.tzi.use.gui.views.diagrams.elements.edges.EdgeBase;
import org.tzi.use.gui.views.diagrams.event.ActionLoadLayout;
import org.tzi.use.gui.views.diagrams.event.ActionSaveLayout;
import org.tzi.use.gui.views.diagrams.util.Direction;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;
import org.tzi.use.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.*;

/**
 * Combines everything that the class and object diagram have in common.
 *
 * @author Fabian Gutsche
 * @author Lars Hamann
 * @author (Modified for Java 9 by Andreas Kaestner)
 */
@SuppressWarnings("serial")
public abstract class DiagramView extends JPanel
        implements Printable {

    /**
     * This graph contains all visible elements of a diagram
     */
    protected DiagramGraph fGraph;

    protected PrintWriter fLog;

    protected Selection<PlaceableNode> fNodeSelection;

    protected Selection<EdgeBase> fEdgeSelection;

    // needed for auto layout
    protected LayoutThread fLayoutThread;

    protected volatile org.tzi.use.gui.graphlayout.SpringLayout<PlaceableNode> fLayouter;

    protected volatile AllLayoutTypes<PlaceableNode> fAllLayoutTypes;

    protected ActionLoadLayout fActionLoadLayout;
    protected ActionSaveLayout fActionSaveLayout;
    protected Action fActionSelectAllNodes;
    protected Action fActionSelectAllEdges;

    protected DiagramOptions fOpt;

    /**
     * This value is read from the system properties file.
     * It determines the minimum width of a class node
     */
    protected int minClassNodeWidth;

    /**
     * This value is read from the system properties file.
     * It determines the minimum height of a class node
     */
    protected int minClassNodeHeight;

    protected StatusBar statusBar = null;

    /**
     * True if a user loads or saves a layout
     */
    private boolean hasUserDefinedLayout = false;

    public DiagramView(DiagramOptions opt, PrintWriter log) {
        fOpt = opt;
        fGraph = new DiagramGraph();
        fLog = log;

        fNodeSelection = new Selection<PlaceableNode>();
        fEdgeSelection = new Selection<EdgeBase>();

        minClassNodeHeight = Integer.parseInt(System.getProperty("use.gui.view.classdiagram.class.minheight"));
        minClassNodeWidth = Integer.parseInt(System.getProperty("use.gui.view.classdiagram.class.minwidth"));
        setFont(Font.getFont("use.gui.view.objectdiagram", getFont()));

        setLayout(null);
        setBackground(Color.white);
        setPreferredSize(Options.fDiagramDimension);

        fActionSelectAllNodes = new AbstractAction("Select all nodes") {
            @Override
            public void actionPerformed(ActionEvent e) {
                fNodeSelection.addAll(getVisibleData().getNodes());
                invalidate();
                repaint();
            }
        };

        fActionSelectAllEdges = new AbstractAction("Select all edges") {
            @Override
            public void actionPerformed(ActionEvent e) {
                fEdgeSelection.addAll(getVisibleData().getEdges());
                invalidate();
                repaint();
            }
        };

        // Add a dummy tool tip text to enable the tool tip functionality
        this.setToolTipText("");
    }

    /**
     * @param statusBar
     */
    public void setStatusBar(StatusBar statusBar) {
        this.statusBar = statusBar;
    }

    public StatusBar getStatusBar() {
        return this.statusBar;
    }

    /**
     * Determines if the auto layout of the diagram is on or off.
     *
     * @return <code>true</code> if the auto layout is on, otherwise
     * <code>false</code>
     */
    public boolean isDoAutoLayout() {
        return fOpt.isDoAutoLayout();
    }

    /**
     * Draws the diagram.
     */
    @Override
    public void paintComponent(Graphics g) {
        drawDiagram(g);
    }

    @Override
    public String getToolTipText(MouseEvent event) {
        PlaceableNode n = this.findNode(event.getX(), event.getY());

        if (n != null && n instanceof ToolTipProvider) {
            String text = ((ToolTipProvider) n).getToolTip(event);
            return text;
        }

        return null;
    }

    /**
     * Initializes all nodes and edges in the diagram which were
     * added since the last call to initialize.
     */
    public void initialize() {
        fGraph.initialize();
    }

    /**
     * Determines if the popup menu of this diagram should be shown. To create
     * the popup menu, use the operation {@link DiagramView#unionOfPopUpMenu()}.
     *
     * @param e MouseEvent.
     * @return <code>true</code> if the popup menu should be shown, otherwise
     * <code>false</code>.
     */
    public final boolean maybeShowPopup(MouseEvent e) {
        if (!e.isPopupTrigger()) {
            return false;
        }

        PopupMenuInfo popupInfo = unionOfPopUpMenu();

        popupInfo.position.setLocation(e.getX(), e.getY());
        popupInfo.popupMenu.show(e.getComponent(), e.getX(), e.getY());
        return true;
    }

    /**
     * When the content of the diagram changes,e.g., after hiding or showing
     * a node, additional calculations are needed, which are not required
     * when "just" redrawing.
     */
    public void invalidateContent(boolean repaint) {
        fGraph.invalidate();
        if (repaint) this.repaint();
    }

    public void invalidateNode(PlaceableNode n) {
        fGraph.invalidateNode(n);
        for (EdgeBase e : fGraph.allEdges(n)) {
            fGraph.invalidateEdge(e);
        }
    }

    /**
     * Draws the diagram.
     *
     * @param g The diagram is drawn into this Graphics object.
     */
    public void drawDiagram(Graphics g) {
        synchronized (fGraph) {

            Graphics2D g2d = (Graphics2D) g;

            if (fOpt.isDoAntiAliasing()) {
                Map<?, ?> hints = (Map<?, ?>) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
                if (hints == null) {
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
                } else {
                    g2d.setRenderingHints(hints);
                }

            }

            Dimension d = getSize();
            g2d.setColor(getBackground());
            g2d.fillRect(0, 0, d.width, d.height);

            if (this.fOpt.showGrid()) {
                drawGrid(g2d);
            }

            fGraph.initialize();

            for (EdgeBase e : fGraph.getInvalidatedEdges()) {
                e.calculateSize(g2d);
            }

            for (PlaceableNode n : fGraph.getInvalidatedNodes()) {
                // set rectangle size for nodes
                // It is important to do this first, otherwise role names will
                // not be dynamically moved after displaying attributes! And
                // after hiding an object which is participating in a two link
                // association, the two links won't be displayed in the right
                // way.
                // Set min height wrt. displayed qualifiers
                int minHeight = 0;
                Set<EdgeBase> myEdges = fGraph.allEdges(n);

                for (EdgeBase e : myEdges) {
                    int heightHint = 0;
                    if (e.source().equals(n))
                        heightHint += e.getSourceHeightHint();

                    if (!e.isReflexive() && e.target().equals(n))
                        heightHint += e.getTargetHeightHint();

                    if (heightHint > 0)
                        minHeight += heightHint + 4;
                }
                n.setRequiredHeight("BY_EDGES", minHeight + 4);
                n.calculateSize(g2d);
            }

            for (EdgeBase e : fGraph.getInvalidatedEdges()) {
                e.updatePosition();
            }
            fGraph.clearInvalidated();

            // draw edges
            // they need to be drawn first otherwise the association will
            // be drawn above the nodes
            Iterator<EdgeBase> edgeIterator = fGraph.edgeIterator();
            while (edgeIterator.hasNext()) {
                EdgeBase e = edgeIterator.next();
                e.draw(g2d);
            }

            double maxX = 0;
            double maxY = 0;

            // draw nodes
            Iterator<PlaceableNode> nodeIterator = fGraph.iterator();
            while (nodeIterator.hasNext()) {
                PlaceableNode n = nodeIterator.next();
                n.draw(g2d);
                maxX = Math.max(maxX, n.getX() + n.getWidth());
                maxY = Math.max(maxY, n.getY() + n.getHeight());
            }

            edgeIterator = fGraph.edgeIterator();
            while (edgeIterator.hasNext()) {
                EdgeBase e = edgeIterator.next();
                PlaceableNode eastNode = e.getWayPointMostTo(Direction.EAST);
                maxX = Math.max(maxX, eastNode.getX() + eastNode.getWidth());
                PlaceableNode southNode = e.getWayPointMostTo(Direction.SOUTH);
                maxY = Math.max(maxY, southNode.getY() + southNode.getHeight());
                e.drawProperties(g2d);

                for (EdgeProperty ep : e.getProperties()) {
                    //TODO refactor hidden/visible management on PlaceableNode (EdgeProperty)
                    if (!ep.isVisible()) {
                        continue;
                    }
                    maxX = Math.max(maxX, ep.getX() + ep.getWidth());
                    maxY = Math.max(maxY, ep.getY() + ep.getHeight());
                }
            }

            Dimension newDimension = new Dimension((int) maxX + 5, (int) maxY + 5);
            if (!newDimension.equals(this.getPreferredSize())) {
                this.setPreferredSize(newDimension);
                this.revalidate();
            }
        }
    }

    /**
     * @param g
     */
    private void drawGrid(Graphics2D g) {
        Color old = g.getColor();
        g.setColor(Color.LIGHT_GRAY);

        Stroke oldStroke = g.getStroke();
        BasicStroke newStroke = new BasicStroke(1.0F, BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_MITER, 10.0F, new float[]{5.0F, 5.0F}, 0.0F);

        g.setStroke(newStroke);

        // Vertical lines
        for (int x = 50; x < getSize().getWidth(); x += 50) {
            g.drawLine(x, 0, x, (int) getSize().getHeight());
            if (Log.isDebug())
                g.drawString(String.valueOf(x), x + 4, 10);
        }

        // Horizontal lines
        for (int y = 50; y < getSize().getHeight(); y += 50) {
            g.drawLine(0, y, (int) getSize().getWidth(), y);
            if (Log.isDebug())
                g.drawString(String.valueOf(y), 2, y + 12);
        }

        g.setColor(old);
        g.setStroke(oldStroke);
    }

    /**
     * Returns the options of a specific diagram.
     */
    public DiagramOptions getOptions() {
        return fOpt;
    }

    /**
     * Returns the log panel.
     */
    public PrintWriter getLog() {
        return fLog;
    }

    /**
     * All currently hidden nodes in this view
     *
     * @return
     */
    public abstract Set<? extends PlaceableNode> getHiddenNodes();

    /**
     * The graph of the diagram
     *
     * @return
     */
    public DiagramGraph getGraph() {
        return this.fGraph;
    }

    /**
     * Prints the diagram. Implementation of Printable interface.
     */
    @Override
    public int print(Graphics g, PageFormat pf, int pi) throws PrinterException {
        if (pi >= 1)
            return Printable.NO_SUCH_PAGE;

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        g2d.translate(pf.getImageableWidth() / 2, pf.getImageableHeight() / 2);

        Dimension d = getSize();
        double scale = Math.min(pf.getImageableWidth() / d.width, pf
                .getImageableHeight()
                / d.height);
        // fit to page
        if (scale < 1.0)
            g2d.scale(scale, scale);
        g2d.translate(-d.width / 2.0, -d.height / 2.0);

        Font f = Font.getFont("use.gui.print.diagramFont", getFont());
        g2d.setFont(f);

        drawDiagram(g2d);
        return Printable.PAGE_EXISTS;
    }

    /**
     * Prints the diagram.
     *
     * @param pf
     * @param jobname
     */
    public void printDiagram(PageFormat pf, String jobname) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName(jobname);
        job.setPrintable(this, pf);

        if (job.printDialog()) {
            // Print the job if the user didn't cancel printing
            try {
                job.print();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Tries to find an edge on the given coordinates.
     *
     * @param x X-coordinate.
     * @param y Y-coordinate.
     */
    public EdgeBase findEdge(int x, int y) {
        Iterator<EdgeBase> it = fGraph.getVisibleEdgesIterator();

        while (it.hasNext()) {
            EdgeBase e = it.next();
            if (e.occupies(x, y)) {
                return e;
            }
        }

        return null;
    }

    /**
     * Adds a way point to the edge at position <code>(x, y)</code>.
     * If no edge is at this position, nothing is donde.
     *
     * @param x
     * @param y
     * @return
     */
    public EdgeProperty addWayPoint(double x, double y) {
        Iterator<EdgeBase> it = fGraph.edgeIterator();

        while (it.hasNext()) {
            EdgeBase e = it.next();
            EdgeProperty wp = e.addWayPoint(x, y);
            if (wp != null) {
                repaint();
                return wp;
            }
        }

        return null;
    }

    /**
     * Finds node occupying the given position.
     *
     * @return null if no such node could be found.
     */
    public PlaceableNode findNode(int x, int y) {
        PlaceableNode res = null;

        synchronized (fGraph) {
            // Find possible edge property that occupies the clicked position
            Iterator<EdgeBase> eIter = fGraph.getVisibleEdgesIterator();

            while (eIter.hasNext()) {
                EdgeBase e = eIter.next();

                res = e.findNode(x, y);

                if (res != null) {
                    return res;
                }
            }

            Iterator<PlaceableNode> nIter = fGraph.getVisibleNodesIterator();

            while (nIter.hasNext()) {
                PlaceableNode n = nIter.next();
                if (!n.isInitialized()) continue;

                if (n.occupies(x, y)) {
                    // Do not break here. We search in the same order
                    // which is used for drawing. There may be another
                    // node drawn on top of this node. That node should be
                    // picked.
                    res = n;
                } else if (n.getRelatedNode(x, y) != null) {
                    res = n.getRelatedNode(x, y);
                }
            }
        }

        return res;
    }

    public Set<PlaceableNode> findNodesInArea(Rectangle r) {
        Set<PlaceableNode> res = new HashSet<PlaceableNode>();

        synchronized (fGraph) {
            Iterator<PlaceableNode> nIter = fGraph.getVisibleNodesIterator();
            while (nIter.hasNext()) {
                PlaceableNode n = nIter.next();
                if (!n.isInitialized()) continue;

                if (r.contains(n.getBounds())) {
                    res.add(n);
                }
            }
        }

        return res;
    }

    public SelectionBox createSelectionBox(Point p) {
        return new SelectionBox(p);
    }

    protected class PopupMenuInfo {

        public JPopupMenu popupMenu = null;

        public int generalShowHideStart = 0;
        public int generalShowHideLength = 0;

        public Point position = new Point();

        public PopupMenuInfo(JPopupMenu menu) {
            this.popupMenu = menu;
        }
    }

    /**
     * Returns a JPopUpMenu with elements used in multiple diagrams.
     */
    protected PopupMenuInfo unionOfPopUpMenu() {
        // context menu on right mouse click
        final JPopupMenu popupMenu = new JPopupMenu();
        PopupMenuInfo info = new PopupMenuInfo(popupMenu);

        final JMenuItem cbCommentNode = getMenuItemCommentNode(info);
        popupMenu.add(cbCommentNode);

        boolean selectionCanBeHidden = !fNodeSelection.isEmpty();
        for (PlaceableNode node : fNodeSelection) {
            if (!(node instanceof EdgeProperty)) {
                selectionCanBeHidden = false;
                break;
            }
        }

        if (selectionCanBeHidden) {
            popupMenu.add(new AbstractAction("Hide selected properties") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (PlaceableNode n : fNodeSelection) {
                        EdgeProperty ep = (EdgeProperty) n;
                        ep.setVisible(false);
                        DiagramView.this.repaint();
                    }
                }
            });
        }

        if (!this.fEdgeSelection.isEmpty()) {
            popupMenu.add(getMenuItemEdgePropertiesVivibility());
        }

        popupMenu.add(new JSeparator());


        final JCheckBoxMenuItem cbAttrValues = new JCheckBoxMenuItem("Show attributes"); // values");
        cbAttrValues.setState(fOpt.isShowAttributes());
        cbAttrValues.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ev) {
                fOpt.setShowAttributes(ev.getStateChange() == ItemEvent.SELECTED);
                invalidateContent(true);
            }
        });

        final JCheckBoxMenuItem cbAssocNames = new JCheckBoxMenuItem("Show association names");
        cbAssocNames.setState(fOpt.isShowAssocNames());
        cbAssocNames.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ev) {
                fOpt.setShowAssocNames(ev.getStateChange() == ItemEvent.SELECTED);
                invalidateContent(true);
            }
        });

        final JCheckBoxMenuItem cbRolenames = new JCheckBoxMenuItem("Show role names");
        cbRolenames.setState(fOpt.isShowRolenames());
        cbRolenames.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ev) {
                fOpt.setShowRolenames(ev.getStateChange() == ItemEvent.SELECTED);
                invalidateContent(true);
            }
        });

        final JCheckBoxMenuItem cbAntiAliasing = getMenuItemAntiAliasing();
        final JCheckBoxMenuItem cbShowGrid = getMenuItemShowGrid();
        final JCheckBoxMenuItem cbGrayscale = getMenuItemGrayscale();

        // This is the start of the general section to show or hide elements
        info.generalShowHideStart = popupMenu.getComponentCount();
        popupMenu.add(cbAttrValues);

        popupMenu.add(cbAssocNames);
        popupMenu.add(cbRolenames);

        info.generalShowHideLength = popupMenu.getComponentCount() - info.generalShowHideStart;

        popupMenu.addSeparator();
        popupMenu.add(getMenuAlign());
        popupMenu.add(cbAntiAliasing);
        popupMenu.add(cbShowGrid);
        popupMenu.add(cbGrayscale);

        addLayoutMenuItems(popupMenu);

        if (fNodeSelection.size() == 1) {
            final PlaceableNode selected = fNodeSelection.iterator().next();
            if (selected instanceof Rolename) {
                popupMenu.add(new AbstractAction("Edit text...") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JTextArea msg = new JTextArea(selected.name());
                        msg.setColumns(68);
                        msg.setRows(4);
                        msg.setLineWrap(true);
                        msg.setWrapStyleWord(true);

                        JScrollPane scrollPane = new JScrollPane(msg);

                        JOptionPane.showMessageDialog(DiagramView.this, scrollPane, "Edit rolename", JOptionPane.PLAIN_MESSAGE);
                        String newText = msg.getText();
                        Rolename r = (Rolename) selected;
                        r.setText(newText);
                        repaint();
                    }
                });
            }
        }

        return info;
    }

    /**
     * Stops the auto layout thread.
     */
    public void stopLayoutThread() {
        if (fLayoutThread != null) {
            fLayoutThread.doLayout = false;
            fLayoutThread.interrupt();
            fLayoutThread = null;
        }
    }

    /**
     * Starts the auto layout thread.
     */
    public void startLayoutThread() {
        if (fOpt.isDoAutoLayout()) {
            fLayoutThread = new LayoutThread();
            fLayoutThread.doLayout = true;
            fLayoutThread.start();
        }
    }

    public enum LayoutType {
        Hierarchical,
        Horizontal,
        HierarchicalUpsideDown,
        HorizontalRightToLeft,
        LandscapeSwimlane,
        PortraitSwimlane
    }

    public void startLayoutFormatThread(LayoutType layoutType, int horizontalSpacing, int verticalSpacing, boolean isPutAssociationsOnRelationsEnabled) {
        fLayoutThread = new LayoutThread();

        fLayoutThread.horizontalSpacing = horizontalSpacing;
        fLayoutThread.verticalSpacing = verticalSpacing;
        fLayoutThread.isPutAssociationsOnRelationsEnabled = isPutAssociationsOnRelationsEnabled;

        switch (layoutType) {
            case Hierarchical:
                fLayoutThread.doHierarchicalLayout = true;
                break;
            case HierarchicalUpsideDown:
                fLayoutThread.doHierarchicalUpsideDownLayout = true;
                break;
            case Horizontal:
                fLayoutThread.doHorizontalLayout = true;
                break;
            case HorizontalRightToLeft:
                fLayoutThread.doHorizontalRightToLeftLayout = true;
                break;
            case LandscapeSwimlane:
                fLayoutThread.doLandscapeSwimlaneLayout = true;
                break;
            case PortraitSwimlane:
                fLayoutThread.doPortraitSwimlaneLayout = true;
                break;
        }
        fLayoutThread.start();
    }

    /**
     * This is called by the LayoutThread to generate a new layout. The layouter
     * object can be reused as long as the graph and the size of the drawing
     * area does not change.
     */
    protected synchronized void autoLayout() {
        if (fLayouter == null) {
            int w = getWidth();
            int h = getHeight();
            if (w == 0 || h == 0)
                return;
            fLayouter = new SpringLayout<PlaceableNode>(fGraph, w, h, 20, 20);
            fLayouter.setEdgeLen(150);
        }
        fLayouter.layout();
        repaint();
    }


    //Manage Diffrent Type Of Layouts
    protected synchronized void HierarchicalLayout(int HorizontalSpacing, int VerticalSpacing, boolean IsPutAssociationsOnRelationsEnabled) {
        if (fLayouter == null) {
            int w = getWidth();
            int h = getHeight();
            if (w == 0 || h == 0)
                return;
            if(DiagramView.this.getHiddenData() != null)
                fAllLayoutTypes = new AllLayoutTypes<PlaceableNode>(fGraph,DiagramView.this.getHiddenData().getNodes(), w, h, 20, 20);
            else
                fAllLayoutTypes = new AllLayoutTypes<PlaceableNode>(fGraph,new HashSet<>(), w, h, 20, 20);

            fAllLayoutTypes.setEdgeLen(150);
        }
        fAllLayoutTypes.HierarchicalLayout(HorizontalSpacing, VerticalSpacing, IsPutAssociationsOnRelationsEnabled);
        repaint();
    }

    protected synchronized void HierarchicalUpsideDownLayout(int HorizontalSpacing, int VerticalSpacing, boolean IsPutAssociationsOnRelationsEnabled) {
        if (fLayouter == null) {
            int w = getWidth();
            int h = getHeight();
            if (w == 0 || h == 0)
                return;
            if(DiagramView.this.getHiddenData() != null)
                fAllLayoutTypes = new AllLayoutTypes<PlaceableNode>(fGraph,DiagramView.this.getHiddenData().getNodes(), w, h, 20, 20);
            else
                fAllLayoutTypes = new AllLayoutTypes<PlaceableNode>(fGraph,new HashSet<>(), w, h, 20, 20);
            fAllLayoutTypes.setEdgeLen(150);
        }
        fAllLayoutTypes.HierarchicalUpsideDownLayout(HorizontalSpacing, VerticalSpacing, IsPutAssociationsOnRelationsEnabled);
        repaint();
    }

    protected synchronized void HorizontalLayout(int HorizontalSpacing, int VerticalSpacing, boolean IsPutAssociationsOnRelationsEnabled) {
        if (fLayouter == null) {
            int w = getWidth();
            int h = getHeight();
            if (w == 0 || h == 0)
                return;
            if(DiagramView.this.getHiddenData() != null)
                fAllLayoutTypes = new AllLayoutTypes<PlaceableNode>(fGraph,DiagramView.this.getHiddenData().getNodes(), w, h, 20, 20);
            else
                fAllLayoutTypes = new AllLayoutTypes<PlaceableNode>(fGraph,new HashSet<>(), w, h, 20, 20);
            fAllLayoutTypes.setEdgeLen(150);
        }
        fAllLayoutTypes.HorizontalLayout(HorizontalSpacing, VerticalSpacing, IsPutAssociationsOnRelationsEnabled);
        repaint();
    }

    protected synchronized void HorizontalRightToLeftLayout(int HorizontalSpacing, int VerticalSpacing, boolean IsPutAssociationsOnRelationsEnabled) {
        if (fLayouter == null) {
            int w = getWidth();
            int h = getHeight();
            if (w == 0 || h == 0)
                return;
            if(DiagramView.this.getHiddenData() != null)
                fAllLayoutTypes = new AllLayoutTypes<PlaceableNode>(fGraph,DiagramView.this.getHiddenData().getNodes(), w, h, 20, 20);
            else
                fAllLayoutTypes = new AllLayoutTypes<PlaceableNode>(fGraph,new HashSet<>(), w, h, 20, 20);
            fAllLayoutTypes.setEdgeLen(150);
        }
        fAllLayoutTypes.HorizontalRightToLeftLayout(HorizontalSpacing, VerticalSpacing, IsPutAssociationsOnRelationsEnabled);
        repaint();
    }

    protected synchronized void LandscapeSwimlaneLayout(int HorizontalSpacing, int VerticalSpacing, boolean IsPutAssociationsOnRelationsEnabled) {
        if (fLayouter == null) {
            int w = getWidth();
            int h = getHeight();
            if (w == 0 || h == 0)
                return;
            if(DiagramView.this.getHiddenData() != null)
                fAllLayoutTypes = new AllLayoutTypes<PlaceableNode>(fGraph,DiagramView.this.getHiddenData().getNodes(), w, h, 20, 20);
            else
                fAllLayoutTypes = new AllLayoutTypes<PlaceableNode>(fGraph,new HashSet<>(), w, h, 20, 20);
            fAllLayoutTypes.setEdgeLen(150);
        }
        fAllLayoutTypes.LandscapeSwimlaneLayout(HorizontalSpacing, VerticalSpacing, IsPutAssociationsOnRelationsEnabled);
        repaint();
    }

    protected synchronized void PortraitSwimlaneLayout(int HorizontalSpacing, int VerticalSpacing, boolean IsPutAssociationsOnRelationsEnabled) {
        if (fLayouter == null) {
            int w = getWidth();
            int h = getHeight();
            if (w == 0 || h == 0)
                return;
            if(DiagramView.this.getHiddenData() != null)
                fAllLayoutTypes = new AllLayoutTypes<PlaceableNode>(fGraph,DiagramView.this.getHiddenData().getNodes(), w, h, 20, 20);
            else
                fAllLayoutTypes = new AllLayoutTypes<PlaceableNode>(fGraph,new HashSet<>(), w, h, 20, 20);
            fAllLayoutTypes.setEdgeLen(150);
        }
        fAllLayoutTypes.PortraitSwimlaneLayout(HorizontalSpacing, VerticalSpacing, IsPutAssociationsOnRelationsEnabled);
        repaint();
    }

    class LayoutThread extends Thread {
        public boolean doLayout = true;
        public boolean doHierarchicalLayout = false;
        public boolean doHierarchicalUpsideDownLayout = false;
        public boolean doHorizontalLayout = false;
        public boolean doHorizontalRightToLeftLayout = false;
        public boolean doLandscapeSwimlaneLayout = false;
        public boolean doPortraitSwimlaneLayout = false;
        public int horizontalSpacing = 120;
        public int verticalSpacing = 120;
        public boolean isPutAssociationsOnRelationsEnabled = true;

        @Override
        public void run() {
            while (doLayout) {
                if (isDoAutoLayout()) {
                    autoLayout();
                } else if (doHierarchicalLayout) {
                    HierarchicalLayout(horizontalSpacing, verticalSpacing, isPutAssociationsOnRelationsEnabled);
                    doHierarchicalLayout = false;
                } else if (doHorizontalLayout) {
                    HorizontalLayout(horizontalSpacing, verticalSpacing, isPutAssociationsOnRelationsEnabled);
                    doHorizontalLayout = false;
                } else if (doHierarchicalUpsideDownLayout) {
                    HierarchicalUpsideDownLayout(horizontalSpacing, verticalSpacing, isPutAssociationsOnRelationsEnabled);
                    doHierarchicalUpsideDownLayout = false;
                } else if (doHorizontalRightToLeftLayout) {
                    HorizontalRightToLeftLayout(horizontalSpacing, verticalSpacing, isPutAssociationsOnRelationsEnabled);
                    doHorizontalRightToLeftLayout = false;
                } else if (doLandscapeSwimlaneLayout) {
                    LandscapeSwimlaneLayout(horizontalSpacing, verticalSpacing, isPutAssociationsOnRelationsEnabled);
                    doLandscapeSwimlaneLayout = false;
                }else if (doPortraitSwimlaneLayout) {
                    PortraitSwimlaneLayout(horizontalSpacing, verticalSpacing, isPutAssociationsOnRelationsEnabled);
                    doPortraitSwimlaneLayout = false;
                }
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    /**
     * Stores the placement info inside of the given element
     *
     * @param rootElement
     */
    public abstract void storePlacementInfos(PersistHelper helper, Element rootElement);

    /**
     * @param rootElement
     */
    public abstract void restorePlacementInfos(PersistHelper helper, int version);

    /**
     * Show all hidden elements again
     */
    public abstract void showAll();

    /**
     * Hide all elements
     */
    public abstract void hideAll();

    protected void beforeLoadLayout(Path layoutFile) {
    }

    protected void beforeSaveLayout(Path layoutFile) {
    }

    public void loadLayoutFromString(String layoutString) {
        PersistHelper helper = null;
        try {
            helper = new PersistHelper(layoutString.getBytes(), fLog);
        } catch (ParseException e) {
            return;
        }


        this.showAll();

        int version = 1;

        if (helper.hasAttribute("version"))
            version = Integer.valueOf(helper.getAttributeValue("version"));

        if (beforeRestorePlacementInfos(version)) {
            helper.toFirstChild("diagramOptions");
            this.getOptions().loadOptions(helper, version);
            helper.toParent();

            helper.setAllNodes(this.collectAllNodes());
            this.restorePlacementInfos(helper, version);
            this.invalidateContent(false);
        }

        this.repaint();
    }

    public void loadLayout(Path layoutFile) {
        beforeLoadLayout(layoutFile);

        this.showAll();

        PersistHelper helper = new PersistHelper(layoutFile, fLog);

        int version = 1;

        if (helper.hasAttribute("version"))
            version = Integer.valueOf(helper.getAttributeValue("version"));

        if (beforeRestorePlacementInfos(version)) {
            helper.toFirstChild("diagramOptions");
            this.getOptions().loadOptions(helper, version);
            helper.toParent();

            helper.setAllNodes(this.collectAllNodes());
            this.restorePlacementInfos(helper, version);
            this.invalidateContent(false);

            afterLoadLayout(layoutFile);
        }

        this.repaint();
    }

    /**
     * Called before the layout information is restored.
     * Can be used to suppress loading if version is incompatible.
     *
     * @param options
     * @param version
     * @return
     */
    protected boolean beforeRestorePlacementInfos(int version) {
        return true;
    }

    protected Map<String, PlaceableNode> collectAllNodes() {
        Map<String, PlaceableNode> allNodes = new HashMap<String, PlaceableNode>(fGraph.size() + fGraph.numEdges() * 4);
        for (PlaceableNode node : fGraph) {
            allNodes.put(node.getId(), node);
            node.collectChildNodes(allNodes);
        }

        for (EdgeBase edge : fGraph.getEdges()) {
            edge.collectChildNodes(allNodes);
        }

        return allNodes;
    }

    private Document saveLayout() throws ParserConfigurationException {
        DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        Document doc;

        docBuilder = fact.newDocumentBuilder();
        doc = docBuilder.newDocument();

        PersistHelper helper = new PersistHelper(fLog);
        Element rootElement = doc.createElement("diagram_Layout");
        rootElement.setAttribute("version", String.valueOf(DiagramOptions.XML_LAYOUT_VERSION));
        doc.appendChild(rootElement);

        Element optionsElement = doc.createElement("diagramOptions");
        rootElement.appendChild(optionsElement);
        this.getOptions().saveOptions(helper, optionsElement);
        this.storePlacementInfos(helper, rootElement);
        return doc;
    }

    public String saveLayoutAsString() {
        Document doc = null;
        try {
            doc = saveLayout();
        } catch (ParserConfigurationException e1) {
            // layout can not be saved
            return null;
        }
        // use specific Xerces class to write DOM-data to a file:

        OutputFormat format = new OutputFormat();
        format.setLineWidth(65);
        format.setIndenting(true);
        format.setIndent(2);
        StringWriter stringOut = new StringWriter();
        XMLSerializer serializer = new XMLSerializer(stringOut, format);
        try {
            serializer.serialize(doc);
        } catch (IOException e1) {
            return "";
        }
        return stringOut.toString();
    }

    public void saveLayout(Path layoutFile) {
        beforeSaveLayout(layoutFile);

        DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        Document doc;

        try {
            docBuilder = fact.newDocumentBuilder();
            doc = docBuilder.newDocument();
        } catch (ParserConfigurationException e1) {
            JOptionPane.showMessageDialog(this, e1.getMessage());
            return;
        }

        PersistHelper helper = new PersistHelper(fLog);
        Element rootElement = doc.createElement("diagram_Layout");
        rootElement.setAttribute("version", String.valueOf(DiagramOptions.XML_LAYOUT_VERSION));
        doc.appendChild(rootElement);

        Element optionsElement = doc.createElement("diagramOptions");
        rootElement.appendChild(optionsElement);
        this.getOptions().saveOptions(helper, optionsElement);
        this.storePlacementInfos(helper, rootElement);

        DOMImplementationLS impl;
        try {
            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e1) {
            JOptionPane.showMessageDialog(this, e1.getMessage());
            return;
        }
        try (FileOutputStream outStream = new FileOutputStream(layoutFile.toFile())) {
            LSSerializer serializer = impl.createLSSerializer();
            serializer.getDomConfig().setParameter("format-pretty-print", true);
            LSOutput output = impl.createLSOutput();
            output.setEncoding(Charset.defaultCharset().name());
            output.setByteStream(outStream);
            serializer.write(doc, output);
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(this, e1.getMessage());
        }

        afterSaveLayout(layoutFile);
    }

    protected Path getDefaultLayoutFile() {
        String suffix = getDefaultLayoutFileSuffix();

        if (this.getOptions().getDirectory() == null ||
                suffix == null)
            return null;

        Path modelFile = this.getOptions().getModelFileName();
        String fileNameOnly = modelFile.getFileName().toString();

        if (fileNameOnly.contains(".")) {
            fileNameOnly = fileNameOnly.substring(0, fileNameOnly.lastIndexOf('.'));
        }

        Path defaultLayoutFile = this.getOptions().getDirectory().resolve(fileNameOnly + suffix);
        return defaultLayoutFile;
    }

    /**
     * @return
     */
    protected abstract String getDefaultLayoutFileSuffix();

    /**
     * Tries to load the default layout from the file "default.clt" in the same
     * directory as the model file.
     * If no such file is present, nothing is done.
     */
    public void loadDefaultLayout() {

        try {
            Path defaultLayoutFile = getDefaultLayoutFile();

            if (defaultLayoutFile == null || !Files.isReadable(defaultLayoutFile))
                return;

            loadLayout(defaultLayoutFile);
            hasUserDefinedLayout = false;
        } catch (Exception e) {
            fLog.println("Error loading default Layout. Using random layout. Cause: " + e.getMessage());
            resetLayout();
        }
    }

    protected void afterLoadLayout(Path layoutFile) {
        hasUserDefinedLayout = true;
    }

    protected void afterSaveLayout(Path layoutFile) {
        hasUserDefinedLayout = true;
    }

    /**
     * Saves the current layout to the file "default.clt" if no other
     * layout was loaded or saved.
     */
    private void saveDefaultLayout() {

        if (this.hasUserDefinedLayout) return;

        Path defaultLayoutFile = getDefaultLayoutFile();
        if (defaultLayoutFile == null)
            return;

        saveLayout(defaultLayoutFile);

        hasUserDefinedLayout = false;
    }

    /**
     * Resets the diagram to a random layout.
     */
    public void resetLayout() {

    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        this.stopLayoutThread();
        this.onClosing();
    }

    /**
     * Can be used to remove listeners etc. to allow the garbage collector to remove
     * the diagram.
     */
    protected void onClosing() {
        this.saveDefaultLayout();
    }

    /**
     * Returns information about currently visible nodes and edges in the diagram.
     *
     * @return
     */
    public abstract DiagramData getVisibleData();

    /**
     * Returns information about currently hidden nodes and edges in the diagram.
     *
     * @return
     */
    public abstract DiagramData getHiddenData();

    public static interface DiagramData {
        Set<PlaceableNode> getNodes();

        Set<EdgeBase> getEdges();

        boolean hasNodes();
    }

    // Menu items for the popup menu

    protected JCheckBoxMenuItem getMenuItemAntiAliasing() {
        final JCheckBoxMenuItem cbAntiAliasing = new JCheckBoxMenuItem(
                "Anti-aliasing");
        cbAntiAliasing.setState(fOpt.isDoAntiAliasing());
        cbAntiAliasing.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ev) {
                fOpt.setDoAntiAliasing(ev.getStateChange() == ItemEvent.SELECTED);
                repaint();
            }
        });
        return cbAntiAliasing;
    }

    protected JCheckBoxMenuItem getMenuItemShowGrid() {
        final JCheckBoxMenuItem cbShowGrid =
                new JCheckBoxMenuItem("Show grid");
        cbShowGrid.setState(fOpt.showGrid());
        cbShowGrid.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ev) {
                fOpt.setShowGrid(ev.getStateChange() == ItemEvent.SELECTED);
                repaint();
            }
        });
        return cbShowGrid;
    }

    protected JCheckBoxMenuItem getMenuItemGrayscale() {
        final JCheckBoxMenuItem cbGrayscale = new JCheckBoxMenuItem("Grayscale view");
        cbGrayscale.setSelected(fOpt.grayscale());
        cbGrayscale.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                fOpt.setGrayscale(e.getStateChange() == ItemEvent.SELECTED);

                setWayPointColor(DiagramView.this.getVisibleData().getEdges());
                setWayPointColor(DiagramView.this.getHiddenData().getEdges());

                repaint();
            }

            protected void setWayPointColor(Set<EdgeBase> edges) {
                for (EdgeBase edge : edges) {
                    for (WayPoint wp : edge.getWayPoints()) {
                        wp.setBackColorSelected(fOpt.getColor(DiagramOptions.NODE_SELECTED_COLOR));
                    }
                }
            }
        });
        return cbGrayscale;
    }

    protected JMenuItem getMenuItemCommentNode(final PopupMenuInfo popupMenuInfo) {
        final JMenuItem commentNode = new JMenuItem("Add comment node");

        commentNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                CommentNode cn = new CommentNode(popupMenuInfo.position.x, popupMenuInfo.position.y, DiagramView.this);
                getGraph().add(cn);

                repaint();
            }
        });
        return commentNode;
    }

    private abstract class AlignAction extends AbstractAction {
        public AlignAction(String text, String icon) {
            super(text, new ImageIcon(Options.getIconPath(icon).toString()));
        }

        @Override
        public final void actionPerformed(ActionEvent e) {
            boolean first = true;
            double newValue = 0;

            for (PlaceableNode n : fNodeSelection) {
                if (first) {
                    newValue = getNewValue(n);
                    first = false;
                } else {
                    setNewValue(n, newValue);
                }
            }
            invalidateContent(true);
        }

        protected abstract double getNewValue(PlaceableNode n);

        protected abstract void setNewValue(PlaceableNode n, double newValue);
    }

    protected JMenuItem getMenuItemEdgePropertiesVivibility() {
        JMenu menuVisible = new JMenu("Show or hide edge properties");

        JMenuItem allVisible = new JMenuItem(new AbstractAction("Show all properties") {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (EdgeBase edge : fEdgeSelection) {
                    for (EdgeProperty p : edge.getProperties()) {
                        p.setVisible(true);
                        DiagramView.this.repaint();
                    }
                }
            }
        });

        JMenuItem allHidden = new JMenuItem(new AbstractAction("Hide all properties") {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (EdgeBase edge : fEdgeSelection) {
                    for (EdgeProperty p : edge.getProperties()) {
                        p.setVisible(false);
                        DiagramView.this.repaint();
                    }
                }
            }
        });

        menuVisible.add(allVisible);
        menuVisible.add(allHidden);

        if (fEdgeSelection.size() == 1) {
            final EdgeBase edge = fEdgeSelection.iterator().next();

            menuVisible.addSeparator();

            for (EdgeBase.PropertyOwner owner : edge.getPropertiesGrouped().keySet()) {
                Collection<EdgeProperty> props = edge.getPropertiesGrouped().get(owner);
                if (!props.isEmpty()) {
                    JMenu menuGroup = new JMenu(getPropertyOwnerCaption(owner));
                    menuVisible.add(menuGroup);

                    for (final EdgeProperty p : props) {

                        final JCheckBoxMenuItem propertyItem = new JCheckBoxMenuItem(p.toString());
                        propertyItem.setState(p.isVisible());
                        propertyItem.addItemListener(new ItemListener() {
                            @Override
                            public void itemStateChanged(ItemEvent ev) {
                                p.setVisible(ev.getStateChange() == ItemEvent.SELECTED);
                                DiagramView.this.repaint();
                            }
                        });

                        menuGroup.add(propertyItem);
                    }
                }
            }
        }

        return menuVisible;
    }

    private String getPropertyOwnerCaption(EdgeBase.PropertyOwner owner) {
        switch (owner) {
            case SOURCE:
                return "Source end properties";
            case TARGET:
                return "Target end properties";
            case EDGE:
                return "Edge properties";
            default:
                return owner.toString();
        }
    }

    protected JMenuItem getMenuAlign() {
        JMenu menuAlign = new JMenu("Align elements");
        menuAlign.add(new JMenuItem(new AlignAction("Left", "shape-align-left-icon.png") {
            @Override
            protected double getNewValue(PlaceableNode n) {
                return n.getX();
            }

            @Override
            protected void setNewValue(PlaceableNode n, double newValue) {
                n.moveToX(newValue);
            }
        }));
        menuAlign.add(new JMenuItem(new AlignAction("Center", "shape-align-center-icon.png") {
            @Override
            protected double getNewValue(PlaceableNode n) {
                return n.getBounds().getCenterX();
            }

            @Override
            protected void setNewValue(PlaceableNode n, double newValue) {
                n.moveToX(newValue - n.getWidth() / 2);
            }
        }));
        menuAlign.add(new JMenuItem(new AlignAction("Right", "shape-align-right-icon.png") {
            @Override
            protected double getNewValue(PlaceableNode n) {
                return n.getBounds().getMaxX();
            }

            @Override
            protected void setNewValue(PlaceableNode n, double newValue) {
                n.moveToX(newValue - n.getWidth());
            }
        }));

        menuAlign.addSeparator();

        menuAlign.add(new JMenuItem(new AlignAction("Top", "shape-align-top-icon.png") {
            @Override
            protected double getNewValue(PlaceableNode n) {
                return n.getY();
            }

            @Override
            protected void setNewValue(PlaceableNode n, double newValue) {
                n.moveToY(newValue);
            }
        }));
        menuAlign.add(new JMenuItem(new AlignAction("Middle", "shape-align-middle-icon.png") {
            @Override
            protected double getNewValue(PlaceableNode n) {
                return n.getBounds().getCenterY();
            }

            @Override
            protected void setNewValue(PlaceableNode n, double newValue) {
                n.moveToY(newValue - n.getHeight() / 2);
            }
        }));
        menuAlign.add(new JMenuItem(new AlignAction("Bottom", "shape-align-bottom-icon.png") {
            @Override
            protected double getNewValue(PlaceableNode n) {
                return n.getBounds().getMaxY();
            }

            @Override
            protected void setNewValue(PlaceableNode n, double newValue) {
                n.moveToY(newValue - n.getHeight());
            }
        }));

        menuAlign.setEnabled(this.fNodeSelection.size() > 1);
        return menuAlign;
    }

    class LayoutsOptionDialog extends JDialog {
        public LayoutsOptionDialog(JFrame parent, final LayoutType layoutType) {
            super(parent, "Layouts Settings");
            JPanel contentPane = new JPanel(new GridBagLayout());

            GridBagConstraints constraints = new GridBagConstraints();

            NumberFormat format = NumberFormat.getInstance();
            NumberFormatter numberFormatter = new NumberFormatter(format);
            numberFormatter.setValueClass(Integer.class);
            numberFormatter.setMinimum(0);
            numberFormatter.setMaximum(Integer.MAX_VALUE);
            numberFormatter.setAllowsInvalid(false);
            numberFormatter.setCommitsOnValidEdit(true);

            JLabel labelVertical = new JLabel("Vertical spacing:");
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = 1;
            contentPane.add(labelVertical, constraints);

            final JFormattedTextField verticalSpacing = new JFormattedTextField(numberFormatter);
            verticalSpacing.setValue(60);
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 1;
            constraints.gridy = 1;
            contentPane.add(verticalSpacing, constraints);

            JLabel labelHorizontal = new JLabel("Horizontal spacing:");
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = 2;
            contentPane.add(labelHorizontal, constraints);

            final JFormattedTextField horizontalSpacing = new JFormattedTextField(numberFormatter);
            horizontalSpacing.setValue(60);
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 1;
            constraints.gridy = 2;
            contentPane.add(horizontalSpacing, constraints);

            final JCheckBox putAssociationsOnRelation = new JCheckBox("Put associations on relation");
            putAssociationsOnRelation.setSelected(true);
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = 3;
            constraints.gridwidth = 2;
            contentPane.add(putAssociationsOnRelation, constraints);

            JButton button = new JButton("OK");
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = 4;
            constraints.ipadx = 10;
            constraints.gridwidth = 2;
            constraints.gridheight = 2;
            contentPane.add(button, constraints);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startLayoutFormatThread(layoutType, Integer.parseInt(horizontalSpacing.getText()), Integer.parseInt(verticalSpacing.getText()), putAssociationsOnRelation.isSelected());
                    dispose();
                }
            });

            setContentPane(contentPane);
            setResizable(false);

            Image emptyIcon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
            setIconImage(emptyIcon);

            setSize(200, 160);
            setLocation(parent.getX() + parent.getWidth() / 2 - getWidth() / 2, parent.getY() + parent.getHeight() / 2 - getHeight() / 2);
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            getRootPane().setDefaultButton(button);
        }
    }

    /**
     * Adds the layout related items like <i>Save layout</i> and <i>Load layout</i> to the menu <code>popupMenu</code>.
     *
     * @param popupMenu The menu to add the items to.
     */
    protected void addLayoutMenuItems(JPopupMenu popupMenu) {
        final JCheckBoxMenuItem cbAutoLayout = new JCheckBoxMenuItem(
                "Auto-Layout");
        cbAutoLayout.setState(fOpt.isDoAutoLayout());
        cbAutoLayout.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ev) {
                fOpt.setDoAutoLayout(ev.getStateChange() == ItemEvent.SELECTED);
                startLayoutThread();
            }
        });
        final Action resetLayout = new AbstractAction("Reset layout") {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetLayout();
            }
        };

        // Different Layouts
        JMenu layouts = new JMenu("Layouts");
        final JMenuItem hierarchicalLayout = new JMenuItem("Hierarchical layout");
        hierarchicalLayout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LayoutsOptionDialog layoutsOptionDialog = new LayoutsOptionDialog(MainWindow.instance(), LayoutType.Hierarchical);
                layoutsOptionDialog.setModal(true);
                layoutsOptionDialog.setVisible(true);
            }
        });
        final JMenuItem hierarchicalUpsideDownLayout = new JMenuItem("Hierarchical Upside Down layout");
        hierarchicalUpsideDownLayout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LayoutsOptionDialog layoutsOptionDialog = new LayoutsOptionDialog(MainWindow.instance(), LayoutType.HierarchicalUpsideDown);
                layoutsOptionDialog.setModal(true);
                layoutsOptionDialog.setVisible(true);
            }
        });
        final JMenuItem horizontalLayout = new JMenuItem("Horizontal layout");
        horizontalLayout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LayoutsOptionDialog layoutsOptionDialog = new LayoutsOptionDialog(MainWindow.instance(), LayoutType.Horizontal);
                layoutsOptionDialog.setModal(true);
                layoutsOptionDialog.setVisible(true);
            }
        });
        final JMenuItem horizontalRightToLeftLayout = new JMenuItem("Horizontal Rigth to Left layout");
        horizontalRightToLeftLayout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LayoutsOptionDialog layoutsOptionDialog = new LayoutsOptionDialog(MainWindow.instance(), LayoutType.HorizontalRightToLeft);
                layoutsOptionDialog.setModal(true);
                layoutsOptionDialog.setVisible(true);
            }
        });
        final JMenuItem landscapeSwimlaneLayout = new JMenuItem("Swimlane(Based on Filmstrip) Landscape layout");
        landscapeSwimlaneLayout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LayoutsOptionDialog layoutsOptionDialog = new LayoutsOptionDialog(MainWindow.instance(), LayoutType.LandscapeSwimlane);
                layoutsOptionDialog.setModal(true);
                layoutsOptionDialog.setVisible(true);
            }
        });

        final JMenuItem portraitSwimlaneLayout = new JMenuItem("Swimlane(Based on Filmstrip) Portrait layout");
        portraitSwimlaneLayout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LayoutsOptionDialog layoutsOptionDialog = new LayoutsOptionDialog(MainWindow.instance(), LayoutType.PortraitSwimlane);
                layoutsOptionDialog.setModal(true);
                layoutsOptionDialog.setVisible(true);
            }
        });

        layouts.add(hierarchicalLayout);
        layouts.add(hierarchicalUpsideDownLayout);
        layouts.addSeparator();
        layouts.add(horizontalLayout);
        layouts.add(horizontalRightToLeftLayout);
        layouts.addSeparator();
        layouts.add(landscapeSwimlaneLayout);
        layouts.add(portraitSwimlaneLayout);

        popupMenu.addSeparator();
        popupMenu.add(cbAutoLayout);
        popupMenu.add(layouts);
        popupMenu.add(resetLayout);
        popupMenu.add(fActionLoadLayout);
        popupMenu.add(fActionSaveLayout);
        fActionSaveLayout.setEnabled(!fGraph.isEmpty());

        if (fGraph.size() > 0) {
            popupMenu.addSeparator();
            popupMenu.add(fActionSelectAllNodes);
            popupMenu.add(fActionSelectAllEdges);
        }
    }
}
