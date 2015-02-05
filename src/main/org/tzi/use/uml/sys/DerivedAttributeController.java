/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

package org.tzi.use.uml.sys;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.tzi.use.analysis.coverage.CoverageCalculationVisitor;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.events.AttributeAssignedEvent;
import org.tzi.use.util.soil.StateDifference;

/**
 * Calculates values of derived attributes after the system
 * state has changed.
 * @author Lars Hamann
 *
 */
public class DerivedAttributeController implements DerivedValueController {

	private MSystemState state;
	
	private Map<MObject, MObjectState> objectStates;
	
	/**
	 * The set of all defined derived attributes in the system.
	 * Eases access to them.
	 */
	private Set<MAttribute> derivedAttributes = new HashSet<MAttribute>();
	
	public DerivedAttributeController(MSystemState state, Map<MObject, MObjectState> objectStates) {
		this.state = state;
		this.objectStates = objectStates;
	}
	
	/**
	 * Copy constructor
	 * @param derivedLinkController
	 */
	public DerivedAttributeController(MSystemState state, Map<MObject, MObjectState> objectStates, DerivedAttributeController derivedAttributeController) {
		this.state = state;
		this.objectStates = objectStates;
		this.derivedAttributes = new HashSet<MAttribute>(derivedAttributeController.derivedAttributes);
	}
	
	@Override
	public void initState() {
		determineDerivedAttributes();
		calculateDerivedValues(false);
	}

	@Override
	public void updateState() {
		calculateDerivedValues(false);
	}
	
	@Override
	public void updateState(StateDifference diff) {
		Set<MObject> modifiedObjects = calculateDerivedValues(true);
		diff.addModifiedObjects(modifiedObjects);
	}
	
	private void determineDerivedAttributes() {
		for (MClass cls : state.system().model().classes()) {
			for (MAttribute att : cls.attributes()) {
				if (att.isDerived()) {
					derivedAttributes.add(att);
				
					CoverageCalculationVisitor visitor = new CoverageCalculationVisitor(true);
					att.getDeriveExpression().processWithVisitor(visitor);
					
					visitor.getCoverageData();
				}
			}
		}
	}
	
	private Set<MObject> calculateDerivedValues(final boolean returnChangeset) {
		Set<MObject> modifiedObjects;
		
		if (returnChangeset) {
			modifiedObjects = new HashSet<MObject>();
		} else {
			modifiedObjects = Collections.emptySet();
		}
		
		for (MAttribute attr : derivedAttributes) {
			MClass definingClass = attr.owner();
			Set<MObject> objects = state.objectsOfClassAndSubClasses(definingClass);
			
			for (MObject obj : objects) {
				MObjectState objState = objectStates.get(obj);
				
				Value derivedValue = state.evaluateDeriveExpression(obj, attr);
				Value currentValue = objState.attributeValue(attr);
				
				// Nothing changed
				if (derivedValue.equals(currentValue))
					continue;
				
				objState.setAttributeValue(attr, derivedValue);
				
				if (returnChangeset) {
					modifiedObjects.add(obj);
					AttributeAssignedEvent e = new AttributeAssignedEvent(state
							.system().getExecutionContext(), obj, attr,
							derivedValue);
					state.system().getEventBus().post(e);
				}
			}
		}
		
		return modifiedObjects;
	}
}
