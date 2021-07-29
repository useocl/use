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

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.ExpConstUnlimitedNatural;

/**
 * This coverage visitor counts for each covered element
 * the number of occurrences.
 * <p>This visitor can be used to check how often an element is used.</p>
 * <p>Covered elements:
 * <ul>
 *  <li>Classes</li>
 *  <li>Associations</li>
 *  <li>Association ends</li>
 *  <li>Attributes</li>
 * </ul>
 * @author Lars Hamann
 */
public class CoverageCalculationVisitor extends AbstractCoverageVisitor {

	private final CoverageData coverageData = new CoverageData();
		
	public CoverageCalculationVisitor(boolean expandOperations) {
		super(expandOperations);
	}
	
	/**
	 * @param cls
	 */
	@Override
	protected void addClassCoverage(MClass cls) {
		if (!coverageData.getClassCoverage().containsKey(cls)) {
			coverageData.getClassCoverage().put(cls, 1);
		} else {
			coverageData.getClassCoverage().put(cls, coverageData.getClassCoverage().get(cls) + 1);
		}
		addCompleteClassCoverage(cls);
	}
	
	/**
	 * @param cls
	 */
	protected void addCompleteClassCoverage(MClass cls) {
		if (!coverageData.getCompleteClassCoverage().containsKey(cls)) {
			coverageData.getCompleteClassCoverage().put(cls, 1);
		} else {
			coverageData.getCompleteClassCoverage().put(cls, coverageData.getCompleteClassCoverage().get(cls) + 1);
		}
	}
	
	/**
	 * @param sourceType
	 */
	@Override
	protected void addAttributeCoverage(MClass sourceClass, MAttribute att) {
		AttributeAccessInfo info = new AttributeAccessInfo(sourceClass, att);
		if (!coverageData.getAttributeAccessCoverage().containsKey(info)) {
			coverageData.getAttributeAccessCoverage().put(info, 1);
		} else {
			coverageData.getAttributeAccessCoverage().put(info, coverageData.getAttributeAccessCoverage().get(info) + 1);
		}
		if (!coverageData.getAttributeCoverage().containsKey(att)) {
			coverageData.getAttributeCoverage().put(att, 1);
		} else {
			coverageData.getAttributeCoverage().put(att, coverageData.getAttributeCoverage().get(att) + 1);
		}
		addCompleteClassCoverage(sourceClass);
	}
	
	/**
	 * @param sourceType
	 */
	@Override
	protected void addOperationCoverage(MClass sourceClass, MOperation op) {
		if (!coverageData.getOperationCoverage().containsKey(op)) {
			coverageData.getOperationCoverage().put(op, 1);
		} else {
			coverageData.getOperationCoverage().put(op, coverageData.getOperationCoverage().get(op) + 1);
		}
		addCompleteClassCoverage(sourceClass);
	}
	
	/**
	 * @param sourceType
	 */
	@Override
	protected void addAssociationCoverage(MAssociation assoc) {
		if (!coverageData.getAssociationCoverage().containsKey(assoc)) {
			coverageData.getAssociationCoverage().put(assoc, 1);
		} else {
			coverageData.getAssociationCoverage().put(assoc, coverageData.getAssociationCoverage().get(assoc) + 1);
		}
	}
	
	/**
	 * @param sourceType
	 */
	@Override
	protected void addAssociationEndCoverage(MNavigableElement dst) {
		//FIXME: How to handle association class?
		if (!(dst instanceof MAssociationEnd)) return;
		MAssociationEnd end = (MAssociationEnd)dst;
		
		if (!coverageData.getAssociationEndCoverage().containsKey(end)) {
			coverageData.getAssociationEndCoverage().put(end, 1);
		} else {
			coverageData.getAssociationEndCoverage().put(end, coverageData.getAssociationEndCoverage().get(end) + 1);
		}
		
		addCompleteClassCoverage(end.cls());
	}

	/**
	 * Returns the collected information about the coverage
	 * @return The collected coverage data
	 */
	public CoverageData getCoverageData() {
		return coverageData;
	}

	@Override
	public void visitConstUnlimitedNatural(
			ExpConstUnlimitedNatural expressionConstUnlimitedNatural) {
		
	}
}
