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

// $Id$

package org.tzi.use.analysis.coverage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.uml.mm.MOperation;

/**
 * Container class for coverage data.
 * @author Lars Hamann
 *
 */
public class CoverageData {
	
	/**
	 * Only expressions which access directly a class (allInstances()) are counted
	 */
	protected Map<MClass, Integer> classCoverage = new HashMap<MClass, Integer>();
	
	/**
	 * All expressions are counted which cover the class, associations or attributes of the class
	 */
	protected Map<MClass, Integer> completeClassCoverage = new HashMap<MClass, Integer>();
	
	protected Map<AttributeAccessInfo, Integer> attributeAccessCoverage = new HashMap<AttributeAccessInfo, Integer>();
	
	protected Map<MAttribute, Integer> attributeCoverage = new HashMap<MAttribute, Integer>();
	
	protected Map<MAssociation, Integer> associationCoverage = new HashMap<MAssociation, Integer>();
	
	protected Map<MOperation, Integer> operationCoverage = new HashMap<MOperation, Integer>();
	
	/**
	 * Saves coverage information about association end coverage
	 */
	protected Map<MAssociationEnd, Integer> associationEndCoverage = new HashMap<MAssociationEnd, Integer>();

	public CoverageData() {	}
	
	/**
	 * @return the classCoverage
	 */
	public Map<MClass, Integer> getClassCoverage() {
		return classCoverage;
	}

	/**
	 * @return the classCoverage
	 */
	public Map<MClass, Integer> getCompleteClassCoverage() {
		return completeClassCoverage;
	}
	
	/**
	 * @return the attributeCoverage
	 */
	public Map<AttributeAccessInfo, Integer> getAttributeAccessCoverage() {
		return attributeAccessCoverage;
	}

	/**
	 * @return the attributeCoverage
	 */
	public Map<MAttribute, Integer> getAttributeCoverage() {
		return attributeCoverage;
	}
	
	/**
	 * @return the attributeCoverage
	 */
	public Map<MOperation, Integer> getOperationCoverage() {
		return operationCoverage;
	}
	
	/**
	 * @return the associationCoverage
	 */
	public Map<MAssociation, Integer> getAssociationCoverage() {
		return associationCoverage;
	}
	
	/**
	 * @return the associationEndCoverage
	 */
	public Map<MAssociationEnd, Integer> getAssociationEndCoverage() {
		return associationEndCoverage;
	}
	
	/**
	 * @return the propertyCoverage
	 */
	public Map<MModelElement, Integer> getPropertyCoverage() {
		HashMap<MModelElement, Integer> res = new HashMap<MModelElement, Integer>();
		res.putAll(this.associationEndCoverage);
		res.putAll(this.attributeCoverage);
		res.putAll(this.operationCoverage);
		return res;
	}
	
	/**
	 * Calculates the highest class coverage value
	 * @return
	 */
	public int calcHighestClassCoverage() {
		return highestInt(this.classCoverage);
	}
	
	/**
	 * Calculates the highest complete class coverage value
	 * @return
	 */
	public int calcHighestCompleteClassCoverage() {
		return highestInt(this.completeClassCoverage);
	}
	
	public int calcLowestClassCoverage() {		
		return lowestInt(this.classCoverage);
	}
	
	public Set<MClass> getCoveredClasses() {
		Set<MClass> result = new TreeSet<MClass>();
		
		for (Map.Entry<MClass, Integer> entry : getClassCoverage().entrySet()) {
			if (entry.getValue().intValue() > 0) {
				result.add(entry.getKey());
			}
		}
		
		return result;
	}
	
	public Set<MClass> getCompleteCoveredClasses() {
		Set<MClass> result = new TreeSet<MClass>();
		
		for (Map.Entry<MClass, Integer> entry : getCompleteClassCoverage().entrySet()) {
			if (entry.getValue().intValue() > 0) {
				result.add(entry.getKey());
			}
		}
		
		return result;
	}

	/**
	 * @return
	 */
	public int calcHighestAttributeCoverage() {
		return highestInt(this.attributeCoverage);
	}
	
	public int highestInt(Map<?, Integer> map) {
		int res = 0;
		for (Integer i : map.values()) {
			res = Math.max(res, i.intValue());
		}
		return res;
	}
	
	public int lowestInt(Map<?, Integer> map) {
		int res = Integer.MAX_VALUE;
		for (Integer i : map.values()) {
			res = Math.min(res, i.intValue());
		}
		return res;
	}
	
	/**
	 * Adds all uncovered classes to the corresponding maps with a value of 0.
	 * @param model
	 */
	public void addUncoveredClasses(MModel model) {
		for (MClass cls : model.classes()) {
			if (!this.classCoverage.containsKey(cls)) {
				this.classCoverage.put(cls, Integer.valueOf(0));
			}
			if (!this.completeClassCoverage.containsKey(cls)) {
				this.completeClassCoverage.put(cls, Integer.valueOf(0));
			}
		}
	}
}
