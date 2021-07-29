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

package org.tzi.use.uml.sys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.statemachines.MProtocolStateMachine;
import org.tzi.use.uml.mm.statemachines.MState;
import org.tzi.use.uml.mm.statemachines.MStateMachine;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.statemachines.MProtocolStateMachineInstance;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

/**
 * An object state is the state of an object in a given system state.
 *
 * @author Mark Richters
 * @author Lars Hamann 
 */
public final class MObjectState {
    /**
     * Slots holding a value for each attribute.
     */
    private Map<MAttribute, Value> fAttrSlots;

    /**
     * Instances of the owned psm.
     */
    private Set<MProtocolStateMachineInstance> protocolStateMachines;

	/**
     * owner object
     */
    private MObject fObject;

    /**
     * Constructs a new object state. 
     */
	public MObjectState(MObject obj) {
        fObject = obj;

        List<MAttribute> atts = obj.cls().allAttributes();
        // initialize attribute slots with undefined values
        fAttrSlots = new IdentityHashMap<MAttribute, Value>(atts.size());
        
        for (MAttribute attr : atts) {
            fAttrSlots.put(attr, UndefinedValue.instance);
        }
        
        Set<MProtocolStateMachine> psms = obj.cls().getAllOwnedProtocolStateMachines(); 
        if (psms.isEmpty()) {
        	this.protocolStateMachines = Collections.emptySet();
        } else {
        	this.protocolStateMachines = new HashSet<MProtocolStateMachineInstance>();
        	// Initialize possible protocol state machines
	        for (MProtocolStateMachine psm : psms) {
	        	this.protocolStateMachines.add(psm.createInstance(this.fObject));
	        }
        }
    }

    /**
     * Copy constructor.
     */
    MObjectState(MObjectState x) {
        fObject = x.fObject;
        fAttrSlots = new IdentityHashMap<MAttribute, Value>(x.fAttrSlots);
        
        if (x.protocolStateMachines.isEmpty()) {
        	this.protocolStateMachines = Collections.emptySet();
        } else {
        	this.protocolStateMachines = new HashSet<MProtocolStateMachineInstance>(x.protocolStateMachines.size());
        	for (MProtocolStateMachineInstance i : x.protocolStateMachines) {        		
        		this.protocolStateMachines.add(new MProtocolStateMachineInstance(i));
        	}
        }
    }

    /**
     * Returns the object linked to this state.
     */
    public MObject object() {
        return fObject;
    }

    /**
     * Used for a new object to initialize the values
     * for attributes that have an initialize expression.
     */
    public void initialize(MSystemState state) {

    	Collection<MAttribute> initAttr = Collections2.filter(fAttrSlots.keySet(), new Predicate<MAttribute>() {
			@Override
			public boolean apply(MAttribute input) {
				return input.getInitExpression().isPresent();
			}});
    	    	
    	if (initAttr.isEmpty()) {
    		return;
    	}
    	
    	List<MAttribute> sortedAttributes = new ArrayList<>(initAttr);
    	
    	// Definition order is important for init expressions.
    	Collections.sort(sortedAttributes, new Comparator<MAttribute>() {
			@Override
			public int compare(MAttribute attr1, MAttribute attr2) {
				int position1 = attr1.getPositionInModel();
				int position2 = attr2.getPositionInModel();
				return Integer.compare(position1, position2);
			}
		});
    	
    	for (MAttribute attr : sortedAttributes) {
			Value v = state.evaluateInitExpression(this.fObject, attr.getInitExpression().get());
			setAttributeValue(attr, v);
    	}
    }
    
    /**
     * Returns the value of the specified attribute.
     *
	 * @exception IllegalArgumentException
	 *                attr is not part of this object.
     */
    public Value attributeValue(MAttribute attr) {
        Value val = fAttrSlots.get(attr);
        
        if (val == null )
			throw new IllegalArgumentException("Attribute `" + attr
					+ "' does not exist in object `" + fObject.name() + "'.");
        
        return val;
    }
    
    /**
     * Returns the value of the specified attribute.
     *
	 * @exception IllegalArgumentException
	 *                attributeName is not a name of an attribute of this object.
     */
    public Value attributeValue(String attributeName) {
    	MAttribute attribute = fObject.cls().attribute(attributeName, true);
    	
    	return attributeValue(attribute);
    }

    /**
     * Assigns a new value to the specified attribute.
     *
	 * @exception IllegalArgumentException
	 *                attr is not part of this object or types don't match.
     */
    public void setAttributeValue(MAttribute attr, Value newVal) {
        Value oldVal = fAttrSlots.get(attr);
        
        if (oldVal == null )
			throw new IllegalArgumentException("Attribute `" + attr
					+ "' does not exist in object `" + fObject.name() + "'.");
        
        if (! newVal.type().conformsTo(attr.type()) )
			throw new IllegalArgumentException("Expected type `" + attr.type()
					+ "' for attribute `" + attr.name() + "', found type `"
					+ newVal.type() + "'.");
        
        fAttrSlots.put(attr, newVal);
    }

    /**
	 * Returns a map with attribute/value pairs.
	 * The returned map is unmodifiable.
     *
     * @return Map(MAttribute, Value) 
     */
    public Map<MAttribute, Value> attributeValueMap() {
        return Collections.unmodifiableMap(fAttrSlots);
    }
    
    /**
     * Returns all state machine instances this object state has.
	 * @return the protocolStateMachines
	 */
	public Set<MProtocolStateMachineInstance> getProtocolStateMachinesInstances() {
		return protocolStateMachines;
	}

	/**
	 * Returns <code>true</code>, if any of the state machines
	 * instances in this state is in the state <code>stateToCheck</code>. 
	 * @param stateToCheck
	 * @return
	 */
	public boolean isInState(MState stateToCheck) {
		for (MProtocolStateMachineInstance i : this.protocolStateMachines) {
			if (i.getProtocolStateMachine().equals(stateToCheck.getContainer().getStateMachine())) {
				if (i.getCurrentState(stateToCheck.getContainer()).equals(stateToCheck))
						return true;
			}
		}
		return false;
	}

	/**
	 * Returns the protocol state machine instance of the given state machine
	 * or <code>null</code> if no such instance is present.
	 * @param stateMachine
	 * @return
	 */
	public MProtocolStateMachineInstance getProtocolStateMachineInstance (MStateMachine stateMachine) {
		for (MProtocolStateMachineInstance i : this.protocolStateMachines) {
			if (i.getProtocolStateMachine().equals(stateMachine))
				return i;
		}
		
		return null;
	}
}
