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

package org.tzi.use.analysis.coverage;

import org.tzi.use.uml.mm.*;
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
	 * @param dtp
	 */
	@Override
	protected void addDataTypeCoverage(MDataType dtp) {
		if (!coverageData.getClassifierCoverage().containsKey(dtp)) {
			coverageData.getClassifierCoverage().put(dtp, 1);
		} else {
			coverageData.getClassifierCoverage().put(dtp, coverageData.getClassifierCoverage().get(dtp) + 1);
		}
		addCompleteClassCoverage(dtp);
	}

	/**
	 * @param cls
	 */
	@Override
	protected void addClassCoverage(MClass cls) {
		if (!coverageData.getClassifierCoverage().containsKey(cls)) {
			coverageData.getClassifierCoverage().put(cls, 1);
		} else {
			coverageData.getClassifierCoverage().put(cls, coverageData.getClassifierCoverage().get(cls) + 1);
		}
		addCompleteClassCoverage(cls);
	}
	
	/**
	 * @param cf
	 */
	protected void addCompleteClassCoverage(MClassifier cf) {
		if (!coverageData.getCompleteClassifierCoverage().containsKey(cf)) {
			coverageData.getCompleteClassifierCoverage().put(cf, 1);
		} else {
			coverageData.getCompleteClassifierCoverage().put(cf, coverageData.getCompleteClassifierCoverage().get(cf) + 1);
		}
	}
	
	/**
	 * @param sourceClassifier
	 * @param att
	 */
	@Override
	protected void addAttributeCoverage(MClassifier sourceClassifier, MAttribute att) {
		AttributeAccessInfo info = new AttributeAccessInfo(sourceClassifier, att);
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
		addCompleteClassCoverage(sourceClassifier);
	}
	
	/**
	 * @param sourceClassifier
	 * @param op
	 */
	@Override
	protected void addOperationCoverage(MClassifier sourceClassifier, MOperation op) {
		if (!coverageData.getOperationCoverage().containsKey(op)) {
			coverageData.getOperationCoverage().put(op, 1);
		} else {
			coverageData.getOperationCoverage().put(op, coverageData.getOperationCoverage().get(op) + 1);
		}
		addCompleteClassCoverage(sourceClassifier);
	}
	
	/**
	 * @param assoc
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
	 * @param dst
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
