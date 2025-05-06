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

package org.tzi.use.gui.mainFX;

import org.tzi.use.uml.mm.*;
import org.tzi.use.uml.mm.statemachines.MStateMachine;
import org.tzi.use.util.uml.sorting.*;

import javax.swing.event.EventListenerList;
import java.util.*;


/**
 * ModelBrowserSorting is used to put the tree nodes of the
 * ModelBrowser in a specific order. It provides all the
 * sorting algorithem.
 *
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */
public class ModelBrowserSorting {


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


    ModelBrowserSorting() {
        fListenerList = new EventListenerList();
    }
 
    public static ModelBrowserSorting getInstance() {
        if ( fModelBrowserSorting == null ) {
            fModelBrowserSorting = new ModelBrowserSorting();
        }
        return fModelBrowserSorting;
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


    public int getClsOrder() {
        return clsOrder;
    }

    public void setClsOrder(int clsOrder) {
        this.clsOrder = clsOrder;
    }

    public int getAttrOrder() {
        return attrOrder;
    }

    public void setAttrOrder(int attrOrder) {
        this.attrOrder = attrOrder;
    }

    public int getOprOrder() {
        return oprOrder;
    }

    public void setOprOrder(int oprOrder) {
        this.oprOrder = oprOrder;
    }

    public int getAssocOrder() {
        return assocOrder;
    }

    public void setAssocOrder(int assocOrder) {
        this.assocOrder = assocOrder;
    }

    public int getInvOrder() {
        return invOrder;
    }

    public void setInvOrder(int invOrder) {
        this.invOrder = invOrder;
    }

    public StateMachineOrder getStateMachineOrder() {
        return stateMachineOrder;
    }

    public void setStateMachineOrder(StateMachineOrder stateMachineOrder) {
        this.stateMachineOrder = stateMachineOrder;
    }

    public int getCondOrder() {
        return condOrder;
    }

    public void setCondOrder(int condOrder) {
        this.condOrder = condOrder;
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
