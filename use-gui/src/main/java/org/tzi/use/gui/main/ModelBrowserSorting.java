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

/* $ProjectHeader: use 2-3-1-release.3 Wed, 02 Aug 2006 17:53:29 +0200 green $ */

package org.tzi.use.gui.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.mm.statemachines.MStateMachine;
import org.tzi.use.util.uml.sorting.AlphabeticalConditionByNameComparator;
import org.tzi.use.util.uml.sorting.AlphabeticalConditionComparator;
import org.tzi.use.util.uml.sorting.AlphabeticalInvariantComparator;
import org.tzi.use.util.uml.sorting.AlphabeticalNamedElementComparator;
import org.tzi.use.util.uml.sorting.AlphabeticalOperationComparator;
import org.tzi.use.util.uml.sorting.UseFileOrderComparator;


/**
 * ModelBrowserSorting is used to put the tree nodes of the
 * ModelBrowser in a specific order. It provides all the
 * sorting algorithem.
 *
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 * @version $ProjectVersion: 2-3-1-release.3 $
 */
public class ModelBrowserSorting  {
    
    /**
     * A SortChangeEvent is used to notify interested listeners that the
     * sorting order has changed.  
     */
    @SuppressWarnings("serial")
	public class SortChangeEvent extends EventObject {
        public SortChangeEvent(Object source) {
            super(source);
        }
    }

    public interface SortChangeListener extends EventListener {
        void stateChanged(ModelBrowserSorting.SortChangeEvent e);
    }

    
    private static ModelBrowserSorting fModelBrowserSorting = null;
    private EventListenerList fListenerList;
    
    /**
     * Starting classes order.
     */
    public int clsOrder = 2;

    /**
     * Starting attribute order.
     */
    public int attrOrder = 2;

    /**
     * Starting operation order.
     */
    public int oprOrder = 2;

    /**
     * Starting associations order.
     */
    public int assocOrder = 2;

    /**
     * Starting invariant order.
     */
    public int invOrder = 2;

    /**
     * Starting state machine order.
     */
    public StateMachineOrder stateMachineOrder = StateMachineOrder.USE;
    
    public enum StateMachineOrder {
    	USE,
    	ALPHABETIC
    }
    
    /**
     * Signals that the classes will be sorted in alphabetic order.
     */
    public static final int CLS_ALPHABETIC = 1;

    /**
     * Signals that the classes will be sorted in the
     * way they were written in the USE-File.
     */
    public static final int CLS_USE_ORDER = 2;

    /**
     * Signals that the attributes will be sorted in alphabetic order.
     */
    public static final int ATTR_ALPHABETIC = 1;

    /**
     * Signals that the attributes will be sorted in the
     * way they were written in the USE-File.
     */
    public static final int ATTR_USE_ORDER = 2;

   /**
     * Signals that the operations will be sorted in alphabetic order.
     */
    public static final int OPR_ALPHABETIC = 1;

    /**
     * Signals that the operations will be sorted in the
     * way they were written in the USE-File.
     */
    public static final int OPR_USE_ORDER = 2;

    /**
     * Signals that the associations will be sorted in alphabetic order.
     */
    public static final int ASSOC_ALPHABETIC = 1;
    /**
     * Signals that the associations will be sorted in the
     * way they were written in the USE-File.
     */
    public static final int ASSOC_USE_ORDER = 2;

    /**
     * Signals that all invarants will be sorted in alphabetic order 
     * by class name first
     */
    public static final int INV_ALPHABETIC_BY_CLASS = 1;

    /**
     * Signals that the invariants will be sorted in the
     * way they were written in the USE-File.
     */
    public static final int INV_USE_ORDER = 2;

    /**
     * Signals that all invarants will be sorted in alphabetic order 
     * by invariant name first
     */
    public static final int INV_ALPHABETIC_INV_NAME = 5;
    public int condOrder = 10;
    public static final int COND_ALPHABETIC_BY_OPERATION = 7;
    public static final int COND_ALPHABETIC_BY_NAME = 8;
    public static final int COND_ALPHABETIC_BY_PRE = 9;
    public static final int COND_USE_ORDER = 10;


    private ModelBrowserSorting() {
        fListenerList = new EventListenerList();
    }
 
    public static ModelBrowserSorting getInstance() {
        if ( fModelBrowserSorting == null ) {
            fModelBrowserSorting = new ModelBrowserSorting();
        }
        return fModelBrowserSorting;
    }
    
    
    /**
     * Adds Listeners who are interrested on a change event of sorting.
     * @param l The listener who is interrested
     */
    public void addSortChangeListener( SortChangeListener l ) {
        fListenerList.add( SortChangeListener.class, l );
    }
    
    /**
     * Calls the specific algorithem in which way the tree will be sorted.
     *
     * @return The correct sorted <code>ArrayList</code>.
     */
    public Collection<MClass> sortClasses( Collection<MClass> items) {
        ArrayList<MClass> classes = new ArrayList<MClass>( items );
        
        if (classes.size() > 0) {
            switch (clsOrder) {
                case CLS_ALPHABETIC:
                    Collections.sort(classes, new AlphabeticalNamedElementComparator());
                    break;
                case CLS_USE_ORDER:
                    Collections.sort(classes, new UseFileOrderComparator());
                    break;
                default:
                    break;
            }
        }
        return classes;
    }
    
    /**
     * Calls the specific algorithm in which way the tree will be sorted.
     *
     * @return A new list containing the attributes in the currently set sort order.
     */
    public List<MAttribute> sortAttributes(Collection<MAttribute> items) {
        ArrayList<MAttribute> attributes = new ArrayList<MAttribute>( items );
                
        if ( attributes.size() > 0 ) {
            switch ( attrOrder ) {
                case ATTR_ALPHABETIC:
                    Collections.sort(attributes, new AlphabeticalNamedElementComparator());
                    break;
                case ATTR_USE_ORDER:
                    Collections.sort(attributes, new UseFileOrderComparator());
                    break;
                default:
                    break;
            }
        }
        
        return attributes;
    }
    
    /**
     * Calls the specific algorithem in which way the tree will be sorted.
     *
     * @return The correct sorted <code>ArrayList</code>.
     */
    public List<MOperation> sortOperations(Collection<MOperation> items) {
        ArrayList<MOperation> operations = new ArrayList<MOperation>( items );
                
        if ( operations.size() > 0 ) {
            switch ( oprOrder ) {
                case OPR_ALPHABETIC:
                    Collections.sort(operations, new AlphabeticalOperationComparator());
                    break;
                case OPR_USE_ORDER:
                    Collections.sort(operations, new UseFileOrderComparator());
                    break;
                default:
                    break;
            }
        }
        return operations;
    }
    
    /**
     * Calls the specific algorithem in which way the tree will be sorted.
     *
     * @return The correct sorted <code>ArrayList</code>.
     */
    ArrayList<MAssociation> sortAssociations(final ArrayList<MAssociation> associations) {
        ArrayList<MAssociation> onlyAssocs = new ArrayList<MAssociation>();
        if (associations.size() > 0) {
            for (MAssociation assoc : associations) {
                if ( assoc instanceof MAssociationClass ) {
                    continue;
                }
                onlyAssocs.add( assoc );
            }
            
            switch (assocOrder) {
                case ASSOC_ALPHABETIC:
                    Collections.sort(onlyAssocs, new AlphabeticalNamedElementComparator());
                    break;
                case ASSOC_USE_ORDER:
                    Collections.sort(onlyAssocs, new UseFileOrderComparator());
                    break;
                default:
                    break;
            }
        }
        
        return onlyAssocs;
    }

    /**
     * Calls the specific algorithem in which way the tree will be sorted.
     *
     * @return The correct sorted <code>ArrayList</code>.
     */
    public ArrayList<MClassInvariant> sortInvariants(final Collection<MClassInvariant> items) {
        ArrayList<MClassInvariant> sortedInvs = new ArrayList<MClassInvariant>(items);

        if (sortedInvs.size() > 0) {
            switch (invOrder) {
            case INV_ALPHABETIC_BY_CLASS:
                Collections.sort(sortedInvs, new AlphabeticalInvariantComparator(true));
                break;
            case INV_USE_ORDER:
                Collections.sort(sortedInvs, new UseFileOrderComparator());
                break;
            case INV_ALPHABETIC_INV_NAME:
                Collections.sort(sortedInvs, new AlphabeticalInvariantComparator(false));
                break;
            default:
                break;
            }
        }

        return sortedInvs;
    }

    /**
     * Calls the specific algorithem in which way the tree will be sorted.
     *
     * @return The correct sorted <code>ArrayList</code>.
     */
    Collection<MPrePostCondition> sortPrePostConditions(final Collection<MPrePostCondition> items) {
        final ArrayList<MPrePostCondition> sortedConds = new ArrayList<MPrePostCondition>(items);

        if (sortedConds.size() > 0) {
            switch (condOrder) {
                case COND_ALPHABETIC_BY_OPERATION:
                    Collections.sort(sortedConds, new AlphabeticalConditionComparator(false));
                    break;
                case COND_ALPHABETIC_BY_NAME:
                    Collections.sort(sortedConds, new AlphabeticalConditionByNameComparator());
                    break;
                case COND_ALPHABETIC_BY_PRE:
                    Collections.sort(sortedConds, new AlphabeticalConditionComparator(true));
                    break;
                case COND_USE_ORDER:
                    Collections.sort(sortedConds, new UseFileOrderComparator());
                    break;
                default:
                    break;
            }
        }

        return sortedConds;
    }

	Collection<?> sortPluginCollection(Collection<?> value) {
		return value;
	}

    
    /*
     * Notify all listeners that have registered interest for
     * notification on this event type.  The event instance 
     * is lazily created using the parameters passed into 
     * the fire method.
     */
    public void fireStateChanged() {
        // Guaranteed to return a non-null array
        Object[] listeners = fListenerList.getListenerList();
        SortChangeEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i >= 0; i -= 2) {
            if (listeners[i] == SortChangeListener.class ) {
                // Lazily create the event:
                if (e == null) {
                    e = new SortChangeEvent(this);
                }
                ((SortChangeListener) listeners[i+1]).stateChanged(e);
            }          
        }
    }

	/**
	 * @param newObjectDiagramView
	 */
	public void removeSortChangeListener(SortChangeListener l) {
		fListenerList.remove( SortChangeListener.class, l );
	}

	/**
	 * @param allOwnedProtocolStateMachines
	 * @return
	 */
	public List<MStateMachine> sortStateMachines(
			Collection<? extends MStateMachine> stateMachines) {
		
		ArrayList<MStateMachine> sms = new ArrayList<MStateMachine>(stateMachines);
        if (!sms.isEmpty()) {
            switch (stateMachineOrder) {
                case ALPHABETIC:
                    Collections.sort(sms, new AlphabeticalNamedElementComparator());
                    break;
                case USE:
                    Collections.sort(sms, new UseFileOrderComparator());
                    break;
                default:
                    break;
            }
        }
        
        return sms;
	}
}
