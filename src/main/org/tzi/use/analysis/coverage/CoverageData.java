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
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MModel;

/**
 * Container class for coverage data.
 * @author lhamann
 *
 */
public class CoverageData {
	
	protected Map<MClassInvariant, Set<MClass>> coveredClassesByInvariant = new HashMap<MClassInvariant, Set<MClass>>();
	
	protected Map<MClassInvariant, Set<MClass>> completeCoveredClassesByInvariant = new HashMap<MClassInvariant, Set<MClass>>();
	
	protected Map<MClassInvariant, Set<MAssociation>> coveredAssocsByInvariant = new HashMap<MClassInvariant, Set<MAssociation>>();
	
	protected Map<MClassInvariant, Set<AttributeAccessInfo>> coveredAttributesByInvariant = new HashMap<MClassInvariant, Set<AttributeAccessInfo>>();

	protected Map<MClass, Integer> classCoverage;
	
	protected Map<MClass, Integer> completeClassCoverage;
	
	protected Map<AttributeAccessInfo, Integer> attributeAccessCoverage;
	
	protected Map<MAttribute, Integer> attributeCoverage;
	
	protected Map<MAssociation, Integer> associationCoverage;
	
	/**
	 * @return the coveredClassesByInvariant
	 */
	public Map<MClassInvariant, Set<MClass>> getCoveredClassesByInvariant() {
		return coveredClassesByInvariant;
	}

	/**
	 * @return the complete covered classes by invariants
	 */
	public Map<MClassInvariant, Set<MClass>> getCompleteCoveredClassesByInvariant() {
		return completeCoveredClassesByInvariant;
	}
	
	/**
	 * @return the coveredAssocsByInvariant
	 */
	public Map<MClassInvariant, Set<MAssociation>> getCoveredAssocsByInvariant() {
		return coveredAssocsByInvariant;
	}

	/**
	 * @return the coveredAttributesByInvariant
	 */
	public Map<MClassInvariant, Set<AttributeAccessInfo>> getCoveredAttributesByInvariant() {
		return coveredAttributesByInvariant;
	}

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
	 * @return the associationCoverage
	 */
	public Map<MAssociation, Integer> getAssociationCoverage() {
		return associationCoverage;
	}
	
	protected void setResult(MModel model, CoverageCalculationVisitor v) {
		this.associationCoverage = v.getAssociationCoverage();
		this.attributeAccessCoverage = v.getAttributeAccessCoverage();
		this.attributeCoverage = v.getAttributeCoverage();
		this.classCoverage = v.getClassCoverage();
		this.completeClassCoverage = v.getCompleteClassCoverage();
		
		for (MClass cls : model.classes()) {
			if (!this.classCoverage.containsKey(cls)) {
				this.classCoverage.put(cls, Integer.valueOf(0));
			}
			if (!this.completeClassCoverage.containsKey(cls)) {
				this.completeClassCoverage.put(cls, Integer.valueOf(0));
			}
		}
	}
	
	/**
	 * Calculates the highest class coverage value
	 * @return
	 */
	public int calcHighestClassCoverage() {
		int res = 0;
		for (Integer i : this.classCoverage.values()) {
			res = Math.max(res, i.intValue());
		}
		return res;
	}
	
	/**
	 * Calculates the highest complete class coverage value
	 * @return
	 */
	public int calcHighestCompleteClassCoverage() {
		int res = 0;
		for (Integer i : this.completeClassCoverage.values()) {
			res = Math.max(res, i.intValue());
		}
		return res;
	}
	
	public int calcLowestClassCoverage() {		
		int res = Integer.MAX_VALUE;
		for (Integer i : this.classCoverage.values()) {
			res = Math.min(res, i.intValue());
		}
		
		return res;
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
		int res = 0;
		for (Integer i : this.attributeCoverage.values()) {
			res = Math.max(res, i.intValue());
		}
		return res;
	}
}
