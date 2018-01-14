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
import java.awt.print.PageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ModelBrowser;
import org.tzi.use.gui.main.ModelBrowserSorting;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeEvent;
import org.tzi.use.gui.main.ModelBrowserSorting.SortChangeListener;
import org.tzi.use.gui.views.PrintableView;
import org.tzi.use.gui.views.View;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.events.AttributeAssignedEvent;
import org.tzi.use.uml.sys.events.LinkDeletedEvent;
import org.tzi.use.uml.sys.events.LinkInsertedEvent;
import org.tzi.use.uml.sys.events.ObjectCreatedEvent;
import org.tzi.use.uml.sys.events.ObjectDestroyedEvent;
import org.tzi.use.uml.sys.events.TransitionEvent;
import org.tzi.use.uml.sys.soil.MLinkDeletionStatement;
import org.tzi.use.uml.sys.soil.MLinkInsertionStatement;
import org.tzi.use.uml.sys.soil.MObjectDestructionStatement;
import org.tzi.use.uml.sys.soil.MSequenceStatement;

import com.google.common.eventbus.Subscribe;

/** 
 * A graph showing an object diagram with objects and links in the
 * current system state.
 * 
 * @author      Mark Richters 
 */
@SuppressWarnings("serial")
public class NewObjectDiagramView extends JPanel 
  implements View, PrintableView, SortChangeListener {

    protected final MSystem fSystem;
    protected final MainWindow fMainWindow;

    protected NewObjectDiagram fObjectDiagram;
    public static int viewcount = 0;
    
    public NewObjectDiagramView(MainWindow mainWindow, MSystem system) {
        fMainWindow = mainWindow;
        fSystem = system;
        
        fSystem.registerRequiresAllDerivedValues();
        ModelBrowserSorting.getInstance().addSortChangeListener( this );
        fSystem.getEventBus().register(this);

        setLayout(new BorderLayout());

        this.setFocusable(true);
        
        initDiagram(false, null);
    }

    public void initDiagram(boolean loadDefaultLayout, ObjDiagramOptions opt) {
    	if (opt == null)
			fObjectDiagram = new NewObjectDiagram( this, fMainWindow.logWriter());
		else
			fObjectDiagram = new NewObjectDiagram( this, fMainWindow.logWriter(), new ObjDiagramOptions(opt));
		
		fObjectDiagram.setStatusBar(fMainWindow.statusBar());
		this.removeAll();
        add( new JScrollPane(fObjectDiagram) );
        initState();
	}
    
    /**
     * Returns the model browser.
     */
    public ModelBrowser getModelBrowser() {
        return fMainWindow.getModelBrowser();
    }
    
    /**
     * The managed diagram
     * @return
     */
    public NewObjectDiagram getDiagram() {
    	return this.fObjectDiagram;
    }
    
    /**
     * Determines if this is the selected view.
     * @return <code>true</code> if it is the selected view, otherwise
     * <code>false</false>
     */
    public boolean isSelectedView() {
        if ( fMainWindow.getSelectedView() != null ) {
            return fMainWindow.getSelectedView().equals( this );
        } 
        return false;
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
        viewcount++;
    }
    
    @Subscribe
    public void onTransition(TransitionEvent e) {
    	fObjectDiagram.updateObject(e.getSource());
    	fObjectDiagram.invalidateContent(true);
    }
    
    @Subscribe
    public void onObjectCeated(ObjectCreatedEvent e) {
    	if (e.getCreatedObject() instanceof MLink) {
    		return;
    	}

    	fObjectDiagram.addObject(e.getCreatedObject());
    	fObjectDiagram.invalidateContent(true);
    }
    
    @Subscribe
    public void onObjectDestroyed(ObjectDestroyedEvent e) {
    	if (e.getDestroyedObject() instanceof MLink) {
    		return;
    	}
    	
    	fObjectDiagram.deleteObject(e.getDestroyedObject());
    	fObjectDiagram.invalidateContent(true);
    }
    
    @Subscribe
    public void onAttributeAssigned(AttributeAssignedEvent e) {
    	fObjectDiagram.updateObject(e.getObject());
    	fObjectDiagram.invalidateContent(true);
    }
    
    @Subscribe
    public void onLinkCeated(LinkInsertedEvent e) {
    	if (e.getAssociation() instanceof MAssociationClass) {
    		fObjectDiagram.addObject((MLinkObject)e.getLink());	
    	}
    	fObjectDiagram.addLink(e.getLink());
    	fObjectDiagram.invalidateContent(true);
    }
    
    @Subscribe
    public void onLinkDeleted(LinkDeletedEvent e) {
    	if (e.getAssociation() instanceof MAssociationClass) {
    		fObjectDiagram.deleteObject((MLinkObject)e.getLink());
    	}
    	
    	fObjectDiagram.deleteLink(e.getLink());
    	fObjectDiagram.invalidateContent(true);
    }
    
    /**
     * After the occurence of an event the view is updated.
     */
    @Override
	public void stateChanged( SortChangeEvent e ) {
        fObjectDiagram.invalidateContent(true);
    }

    /**
     * Returns the set of all associations that exist between the
     * specified classes. 
     *
     * @return Set(MAssociation) 
     */
    Set<MAssociation> getAllAssociationsBetweenClasses(Set<MClass> classes) {
        return fSystem.model().getAllAssociationsBetweenClasses(classes);
    }

    /**
     * Returns true if there is a link of the specified association
     * connecting the given set of objects.
     */
    Set<MLink> linksBetweenObjects(MAssociation assoc, MObject[] objects) {
        return fSystem.state().linkBetweenObjects(assoc, Arrays.asList(objects));
    }

    /**
     * Executes a command for deleting a link between selected 
     * objects.
     */
    void deleteLink(MLink link) {
    	try {
			fSystem.execute(
					new MLinkDeletionStatement(link));
		} catch (MSystemException e) {
			JOptionPane.showMessageDialog(
					fMainWindow, 
					e.getMessage(), 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
    }
    
    /**
     * Executes a command for inserting a new link.
     */
    void insertLink(MAssociation association, MObject[] objects) {
    	if (association.hasQualifiedEnds()) {
    		QualifierInputView input = new QualifierInputView(fMainWindow, fSystem, association, objects);
    		input.setLocationRelativeTo(this);
    		input.setVisible(true);
    	} else {
	    	try {
				fSystem.execute(new MLinkInsertionStatement(association, objects, Collections.<List<Value>>emptyList()));
			} catch (MSystemException e) {
				JOptionPane.showMessageDialog(
						fMainWindow, 
						e.getMessage(), 
						"Error", 
						JOptionPane.ERROR_MESSAGE);
			}
    	}
    }

    
    /**
     * Executes a command for deleting selected objects.
     */
    void deleteObjects(Set<MObject> objects) {
        MSequenceStatement sequence = new MSequenceStatement();
        
        for (MObject obj : objects) {
        	sequence.appendStatement(
        			new MObjectDestructionStatement(obj.value()));
        }
        
        if (sequence.isEmpty()) {
        	return;
        }
        try {
			fSystem.execute(sequence.simplify());
		} catch (MSystemException e) {
			JOptionPane.showMessageDialog(
					fMainWindow, 
					e.getMessage(), 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
    }

    public MSystem system() {
        return fSystem;
    }

    /**
     * Detaches the view from its model.
     */
    @Override
	public void detachModel() {
        fSystem.getEventBus().unregister(this);
        ModelBrowserSorting.getInstance().removeSortChangeListener( this );
        fSystem.unregisterRequiresAllDerivedValues();
        viewcount--;
    }

    void createObject(String clsName) {
        fMainWindow.createObject(clsName);
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
}
