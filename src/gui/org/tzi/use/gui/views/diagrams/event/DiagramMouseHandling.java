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

$Id$

package org.tzi.use.gui.views.diagrams.event;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.Set;

import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.diagrams.DiagramOptions;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.EdgeProperty;
import org.tzi.use.gui.views.diagrams.NodeOnEdge;
import org.tzi.use.gui.views.diagrams.PlaceableNode;
import org.tzi.use.gui.views.diagrams.Selectable;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;

/**
 * Handels the mouse movements of the class and object diagram.
 *  
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public final class DiagramMouseHandling implements MouseListener,
                                                   MouseMotionListener, 
                                                   DropTargetListener {
    
    private Selection fNodeSelection;
    private Selection fEdgeSelection;
    private DropTarget fDropTarget;
    private DirectedGraph fGraph;
    private DiagramView fDiagram;
    
    // needed for mouse handling
    private static final int DRAG_NONE = 0;
    private static final int DRAG_ITEMS = 1; // drag selected items
    private Point fDragStart;
    private int fDragMode;
    private boolean fIsDragging = false;
    private Cursor fCursor;
    
    public DiagramMouseHandling( Selection nodeSelection, Selection edgeSelection,
                                 DirectedGraph graph, HideAdministration hideAdmin,
                                 Set hiddenNodes, DiagramOptions opt, 
                                 DiagramView diagram ) {
        
        fNodeSelection = nodeSelection;
        fEdgeSelection = edgeSelection;
        fGraph = graph;
        fDiagram = diagram;
        
        fDropTarget = new DropTarget(fDiagram, this);
    }
    
    
    
    public void mouseClicked(MouseEvent e) {
                
    }
    
    
    /**
     * event: on item not on item -------------------------------------------
     * b1: select item clear selection deselect others S-b1: extend selection -
     */
    public void mousePressed(MouseEvent e) {
        if (fDiagram.maybeShowPopup(e))
            return;
        
        fDiagram.addMouseMotionListener(this);
        fDiagram.stopLayoutThread();
        int modifiers = e.getModifiers();
        
        if ( e.getClickCount() == 1 
                && modifiers == InputEvent.BUTTON1_MASK ) {
               fDiagram.findEdge( fGraph, e.getX(), e.getY(), 1 );
        }
        
        // mouse over node?
        PlaceableNode pickedObjectNode = fDiagram.findNode( fGraph, e.getX(), 
                                                            e.getY());
        
        // double click on EdgeProperty than reposition.
        if ( e.getClickCount() == 2 
             && modifiers == InputEvent.BUTTON1_MASK ) {
           if ( pickedObjectNode instanceof EdgeProperty ) {
                ((EdgeProperty) pickedObjectNode).reposition();
                fDiagram.repaint();
                return;
           }
           // is there an edge place a node, which can be moved.
           fDiagram.findEdge( fGraph, e.getX(), e.getY(), 2 );
        }
        
        
        
        switch (modifiers) {
        case InputEvent.BUTTON1_MASK:
            if (pickedObjectNode != null) {
                // If this item is not currently selected, remove all
                // other items from the selection and only select this
                // item. If this item is currently selected, do
                // nothing (and also leave all other selected items
                // untouched). In any case this click may be used to
                // start dragging selected items.
                if (!fNodeSelection.isSelected(pickedObjectNode)) {
                    // clear selection
                    fNodeSelection.clear();
                    fEdgeSelection.clear();
                    // add this component as the only selected item
                    fNodeSelection.add(pickedObjectNode);
                    fDiagram.repaint();
                } 
                // subsequent dragging events will move selected items
                fDragMode = DRAG_ITEMS;
                fDragStart = e.getPoint();
            } else {
                // click in background, clear selection
                if ( fNodeSelection.clear() | fEdgeSelection.clear() ) {
                    fDiagram.repaint();
                }
            }
        break;
        case InputEvent.SHIFT_MASK + InputEvent.BUTTON1_MASK:
            fDragMode = DRAG_NONE;
        if (pickedObjectNode != null) {
            // add this component to the selection
            fNodeSelection.add( pickedObjectNode );
            fDiagram.repaint();
        }
        break;
        case InputEvent.BUTTON2_MASK:
            if ( fDiagram instanceof NewObjectDiagram ) {
                ((NewObjectDiagram) fDiagram).mayBeShowObjectInfo( e );
            }
        break;
        default:
            // do nothing
        }
    }
    
    
    
    
    public void mouseReleased(MouseEvent e) {
        fDiagram.removeMouseMotionListener(this);
        fDiagram.startLayoutThread();
        
        if ( fDiagram instanceof NewObjectDiagram ) {
            ((NewObjectDiagram) fDiagram).mayBeDisposeObjectInfo();
        }
        
        if (fIsDragging) {
            e.getComponent().setCursor(fCursor);
            fIsDragging = false;
            fDragMode = DRAG_NONE;
            Iterator it = fNodeSelection.iterator();
            while ( it.hasNext() ) {
                Selectable sel = (Selectable) it.next();
                sel.setDragged( false );
            }
        }
        fDiagram.maybeShowPopup(e);
    }
    
    
    
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
    }
    
    public void mouseDragged(MouseEvent e) {
        // ignore dragging events which we are not interested in
        if (fDragMode == DRAG_NONE)
            return;
        
        // switch cursor at start of dragging
        if (!fIsDragging) {
            fCursor = fDiagram.getCursor();
            fDiagram.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            fIsDragging = true;
        }
        
        if (fDragMode == DRAG_ITEMS) {
            Iterator it = fNodeSelection.iterator();
            while ( it.hasNext() ) {
                Selectable sel = (Selectable) it.next();
                sel.setDragged( true );
            }
            Point p = e.getPoint();
            int dx = p.x - fDragStart.x;
            int dy = p.y - fDragStart.y;
            // move all selected components to new position.
            Iterator nodeIterator = fNodeSelection.iterator();
            while (nodeIterator.hasNext()) {
                PlaceableNode node = (PlaceableNode) nodeIterator.next();
                node.setPosition(node.x() + dx, node.y() + dy);
                if ( node instanceof NodeOnEdge ) {
                    ((NodeOnEdge) node).setWasMoved( true );
                }
            }
            fDragStart = p;
        }
        fDiagram.repaint();
    }
    
    public void mouseMoved(MouseEvent e) {
    }
    
    // implementation of interface DragTargetListener
    public void dragEnter(DropTargetDragEvent dtde) {
        //Log.trace(this, "dragEnter");
        dtde.acceptDrag(DnDConstants.ACTION_MOVE);
    }
    
    public void dragOver(DropTargetDragEvent dtde) {
        //Log.trace(this, "dragOver");
        dtde.acceptDrag(DnDConstants.ACTION_MOVE);
    }
    
    public void dropActionChanged(DropTargetDragEvent dtde) {
        //Log.trace(this, "dropActionChanged");
        dtde.acceptDrag(DnDConstants.ACTION_MOVE);
    }
    
    public void dragExit(DropTargetEvent dte) {
        //Log.trace(this, "dragExit");
    }
    
    /**
     * Accepts a drag of a class from the ModelBrowser. A new object of this
     * class will be created.
     */
    public void drop(DropTargetDropEvent dtde) {
        if ( fDiagram instanceof NewObjectDiagram ) {
            ((NewObjectDiagram) fDiagram).dropObjectFromModelBrowser( dtde );
        }
    }
}
