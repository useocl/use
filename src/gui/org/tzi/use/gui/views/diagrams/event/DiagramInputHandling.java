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

// $Id$

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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.tzi.use.gui.util.Selection;
import org.tzi.use.gui.views.diagrams.DiagramView;
import org.tzi.use.gui.views.diagrams.EdgeBase;
import org.tzi.use.gui.views.diagrams.EdgeProperty;
import org.tzi.use.gui.views.diagrams.PlaceableNode;
import org.tzi.use.gui.views.diagrams.Selectable;
import org.tzi.use.gui.views.diagrams.objectdiagram.NewObjectDiagram;
import org.tzi.use.gui.views.diagrams.waypoints.WayPoint;
import org.tzi.use.gui.views.selection.classselection.SelectionClassTableModel;
import org.tzi.use.gui.views.selection.classselection.SelectionClassView;

/**
 * Handles the mouse movements and keyboard inputs 
 * of the class and object diagram.
 *  
 * @version $ProjectVersion: 0.393 $
 * @author Fabian Gutsche
 */
public final class DiagramInputHandling implements MouseListener,
                                                   MouseMotionListener, 
                                                   DropTargetListener,
                                                   KeyListener {
    
    private Selection<PlaceableNode> fNodeSelection;
    private Selection<EdgeBase> fEdgeSelection;
    
    private DiagramView fDiagram;
    
    // needed for mouse handling
    private static final int DRAG_NONE = 0;
    private static final int DRAG_ITEMS = 1; // drag selected items
    private Point fDragStart;
    private int fDragMode;
    private boolean fIsDragging = false;
    private Cursor fCursor;
    private SelectionClassView opv ;
    
    public DiagramInputHandling( Selection<PlaceableNode> nodeSelection, Selection<EdgeBase> edgeSelection, 
                                 DiagramView diagram ) {
        
        fNodeSelection = nodeSelection;
        fEdgeSelection = edgeSelection;
        fDiagram = diagram;
        
        new DropTarget(fDiagram, this);
    }
    
    public void setSelectionClassView(SelectionClassView opv){//jj
    	this.opv = opv;
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
        
        if ( e.getClickCount() == 1  && modifiers == InputEvent.BUTTON1_MASK ) {
               fDiagram.findEdge( e.getX(), e.getY(), 1 );
        }
        
        // mouse over node?
        PlaceableNode pickedObjectNode = fDiagram.findNode( e.getX(), e.getY());
        
        // double click on EdgeProperty than reposition.
        if ( e.getClickCount() == 2 
             && modifiers == InputEvent.BUTTON1_MASK ) {
           if ( pickedObjectNode instanceof EdgeProperty ) {
                ((EdgeProperty) pickedObjectNode).reposition();
                fDiagram.repaint();
                return;
           }
           // is there an edge place a node, which can be moved.
           fDiagram.findEdge( e.getX(), e.getY(), 2 );
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
                if ( fNodeSelection.clear() || fEdgeSelection.clear() ) {
                	if(opv!= null)
                	 ((SelectionClassTableModel)(opv.fTableModel)).clearSelection(); // jj
                    fDiagram.repaint();
                }
            }
        break;
        case InputEvent.SHIFT_MASK + InputEvent.BUTTON1_MASK:
            fDragMode = DRAG_NONE;
		    if (pickedObjectNode != null) {
		        // add this component to the selection
		        fNodeSelection.add( pickedObjectNode );
		        if(opv != null)
		        	((SelectionClassTableModel)(opv.fTableModel)).addSelected(pickedObjectNode);
		
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

            for (Selectable sel : fNodeSelection) {
                sel.setDragged( false );
            }
        }
        fDiagram.maybeShowPopup(e);
    }
    
    
    
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
    }
    
    public synchronized void mouseDragged(MouseEvent e) {
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
            Point p = e.getPoint();
            int dx = p.x - fDragStart.x;
            int dy = p.y - fDragStart.y;
            
            moveSelectedObjects(dx, dy);
            
            fDragStart = p;
        }
        fDiagram.repaint();
    }

	/**
	 * @param dx
	 * @param dy
	 */
	public void moveSelectedObjects(int dx, int dy) {
		for (PlaceableNode sel : fNodeSelection) {
		    sel.setDragged( true );
		}
		
		// move all selected components to new position.
		for (PlaceableNode sel : fNodeSelection) {
		    sel.setDraggedPosition(dx, dy);
		    
		    if ( sel instanceof WayPoint ) {
		        ((WayPoint) sel).setWasMoved( true );
		    }
		}
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

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) { }

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (!e.isActionKey()) return;
		int dX = 0;
		int dY = 0;
		int slowDown = 1;
		
		if (e.isAltDown())
			slowDown = 10;
		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				dX = -10;
				break;
			case KeyEvent.VK_UP:
				dY = -10;
				break;
			case KeyEvent.VK_RIGHT:
				dX = 10;
				break;
			case KeyEvent.VK_DOWN:
				dY = 10;
				break;
		}
		
		if (dX != 0 || dY != 0) {
			moveSelectedObjects(dX / slowDown, dY / slowDown);
			fDiagram.repaint();
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) { }
}
