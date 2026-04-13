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

package org.tzi.use.gui.views.diagrams.objectdiagram;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.print.PageFormat;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ModelBrowser;
import org.tzi.use.gui.main.ModelBrowserSorting;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeEvent;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeListener;
import org.tzi.use.gui.views.PrintableView;
import org.tzi.use.gui.views.View;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;

/**
 * A graph showing an object diagram with objects and links in the
 * current system state.
 * 
 * @author      Mark Richters 
 */
public class NewObjectDiagramView extends JPanel
  implements View, PrintableView, SortChangeListener, NewObjectDiagramUI, DiagramContext {

    private static final long serialVersionUID = 1L;

    /** The system shown in this view. Marked transient because MSystem is not serializable. */
    protected final transient MSystem fSystem;
    /** Reference to main window; transient because MainWindow is not serializable. */
    protected final transient MainWindow fMainWindow;
    /** Controller used for commands; transient because controllers are not serializable. */
    private transient ApplicationController appController;

    /** The underlying diagram component. transient because Swing components are not serializable. */
    protected transient NewObjectDiagram fObjectDiagram;

    /** Number of active views. Use AtomicInteger to avoid instance methods writing plain static fields. */
    private static final AtomicInteger VIEW_COUNT = new AtomicInteger(0);

    public NewObjectDiagramView(MainWindow mainWindow, MSystem system) {
        fMainWindow = mainWindow;
        fSystem = system;
        
        fSystem.registerRequiresAllDerivedValues();
        ModelBrowserSorting.getInstance().addSortChangeListener( this );

        setLayout(new BorderLayout());

        this.setFocusable(true);
        
        initDiagram(false, null);
    }

    @SuppressWarnings("unused")
    public void initDiagram(boolean loadDefaultLayout, ObjDiagramOptions opt) {
        if (opt == null)
            fObjectDiagram = new NewObjectDiagram( this, fMainWindow.logWriter());
        else
            fObjectDiagram = new NewObjectDiagram( this, fMainWindow.logWriter(), new ObjDiagramOptions(opt));

        // wire MVP skeleton with basic placement persistence
        NewObjectDiagramModel model = new NewObjectDiagramModel();
        PlacementRepository placementRepository = new DefaultPlacementRepository();

        // avoid assignment inside method invocation (Sonar S1121)
        appController = new DefaultApplicationController(fMainWindow, fSystem, this);
        NewObjectDiagramPresenterImpl presenter = new NewObjectDiagramPresenterImpl(
                this,
                fObjectDiagram,
                model,
                appController,
                new DefaultContextMenuProvider(),
                placementRepository,
                fSystem);
         fObjectDiagram.setPresenter(presenter);

          fObjectDiagram.setStatusBar(fMainWindow.statusBar());
         this.removeAll();
         add( new JScrollPane(fObjectDiagram) );
          initState();
  	}

    /** The managed diagram. */
    public NewObjectDiagram getDiagram() {
     	return this.fObjectDiagram;
     }

    /**
     * Does a full update of the view.
     */
    private void initState() {        
        for (MObject obj : fSystem.state().allObjects()) {
            fObjectDiagram.addObject(obj);
        }

        for (MLink link : fSystem.state().allLinks()) {
            fObjectDiagram.addLink(link);
        }
        
        fObjectDiagram.initialize();
        VIEW_COUNT.incrementAndGet();
    }
    
    // legacy helpers removed; use public DiagramContext methods below

    @Override public MSystem system() { return fSystem; }
    @Override public ModelBrowser getModelBrowser() { return fMainWindow.getModelBrowser(); }
    @Override public boolean isSelectedView() { return fMainWindow.getSelectedView() != null && fMainWindow.getSelectedView().equals(this); }
    /**
     * Adds a key listener and ensures the component is focusable so it can receive key events.
     */
    @Override
    public synchronized void addKeyListener(KeyListener l) {
        super.addKeyListener(l);
        // make sure the component can receive focus when a key listener is added
        setFocusable(true);
    }

    /**
     * Returns the font of this component. If no font is set, fall back to the main window font.
     */
    @Override
    public Font getFont() {
        Font f = super.getFont();
        if (f == null && fMainWindow != null) {
            return fMainWindow.getFont();
        }
        return f;
    }

    /**
     * Sets the font and triggers a revalidation/repaint to reflect the change.
     */
    @Override
    public void setFont(Font font) {
        super.setFont(font);
        revalidate();
        repaint();
    }
    @Override public void createObject(String className) {
        if (fObjectDiagram != null && fObjectDiagram.getPresenter() != null) {
            fObjectDiagram.getPresenter().onCreateObject(className);
        } else {
            MainWindow.instance().createObject(className);
        }
    }
    @Override public void insertLink(MAssociation association, MObject[] objects) {
        if (fObjectDiagram != null && fObjectDiagram.getPresenter() != null) {
            fObjectDiagram.getPresenter().onInsertLink(association, Arrays.asList(objects));
        } else if (appController != null) {
            appController.insertLink(association, Arrays.asList(objects));
        }
    }
    @Override public void deleteLink(MLink link) {
        if (fObjectDiagram != null && fObjectDiagram.getPresenter() != null) {
            fObjectDiagram.getPresenter().onDeleteLink(link);
        } else if (appController != null) {
            appController.deleteLink(link);
        }
    }
    @Override public void deleteObjects(Set<MObject> objects) {
        if (fObjectDiagram != null && fObjectDiagram.getPresenter() != null) {
            fObjectDiagram.getPresenter().onDeleteObjects(objects);
        } else if (appController != null) {
            appController.deleteObjects(objects);
        }
    }
    @Override public void resetDiagram(ObjDiagramOptions options) {
        if (fObjectDiagram != null && fObjectDiagram.getPresenter() != null) {
            fObjectDiagram.getPresenter().onResetDiagram(options);
        } else {
            initDiagram(false, options);
        }
    }

    /**
     * Detaches the view from its model.
     */
    @Override
	public void detachModel() {
        if (fObjectDiagram.getPresenter() != null) {
            fObjectDiagram.getPresenter().detach();
        }
        ModelBrowserSorting.getInstance().removeSortChangeListener( this );
        fSystem.unregisterRequiresAllDerivedValues();
        VIEW_COUNT.decrementAndGet();
    }

    /**
     * Returns the current number of active views.
     *
     * @return the view count
     */
    public static int getViewcount() {
        return VIEW_COUNT.get();
    }

    @Override
	public void printView(PageFormat pf) {
        fObjectDiagram.printDiagram(pf, "Object diagram" );
    }

    @Override
	public void export( Graphics2D g ) {
    	JComponent toExport = fObjectDiagram;
        
    	boolean oldDb = toExport.isDoubleBuffered();
    	toExport.setDoubleBuffered(false);
    	toExport.paint(g);
    	toExport.setDoubleBuffered(oldDb);
    }

	@Override
	public float getContentHeight() {
		return fObjectDiagram.getPreferredSize().height;
	}

	@Override
	public float getContentWidth() {
		return fObjectDiagram.getPreferredSize().width;
	}

    @Override
    public void showPopup(JPopupMenu popup) {
        if (fObjectDiagram != null) {
            popup.show(fObjectDiagram, fObjectDiagram.getWidth() / 2, fObjectDiagram.getHeight() / 2);
        } else {
            popup.show(this, getWidth() / 2, getHeight() / 2);
        }
    }

    @Override
    public void refresh() {
        if (fObjectDiagram != null) {
            fObjectDiagram.invalidateContent(true);
            fObjectDiagram.repaint();
        } else {
            revalidate();
            repaint();
        }
    }

    @Override
    public void setStatus(String status) {
        if (fMainWindow != null && fMainWindow.statusBar() != null) {
            fMainWindow.statusBar().showTmpMessage(status);
        }
    }

	 @Override
     public void stateChanged(SortChangeEvent e) {
         if (fObjectDiagram != null && fObjectDiagram.getPresenter() != null) {
             fObjectDiagram.getPresenter().onRefreshRequested();
         } else if (fObjectDiagram != null) {
             fObjectDiagram.invalidateContent(true);
         }
     }
}
