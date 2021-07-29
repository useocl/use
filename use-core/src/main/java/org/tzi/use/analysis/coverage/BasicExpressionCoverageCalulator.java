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
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.ExpObjectByUseId;
import org.tzi.use.uml.ocl.expr.ExpRange;
import org.tzi.use.uml.ocl.expr.Expression;

/**
 * This coverage visitor simply adds all covered
 * model elements into the result.
 * <p>This visitor can be used to check whether an element is covered or not.</p>
 * <p>Covered elements:
 * <ul>
 *  <li>Classes</li>
 *  <li>Associations</li>
 *  <li>Association ends</li>
 *  <li>Attributes</li>
 * </ul>
 * @author Lars Hamann
 */
public class BasicExpressionCoverageCalulator extends AbstractCoverageVisitor {

	private BasicCoverageData coverage;
	
	public BasicExpressionCoverageCalulator(boolean expandOperations) {
		super(expandOperations);
	}

	/**
	 * @return
	 */
	public BasicCoverageData calcualteCoverage(Expression expr) {
		this.coverage = new BasicCoverageData();
		expr.processWithVisitor(this);
		return coverage;
	}

	@Override
	protected void addClassCoverage(MClass cls) {
		coverage.getCoveredClasses().add(cls);
	}

	@Override
	protected void addAssociationEndCoverage(MNavigableElement dst) {
		coverage.getCoveredAssociations().add(dst.association());		
	}

	@Override
	protected void addAssociationCoverage(MAssociation assoc) {
		coverage.getCoveredAssociations().add(assoc);
	}

	@Override
	protected void addAttributeCoverage(MClass sourceClass, MAttribute att) {
		coverage.getCoveredAttributes().add(att);		
	}

	@Override
	public void visitObjectByUseId(ExpObjectByUseId expObjectByUseId) {
		coverage.getCoveredClasses().add(expObjectByUseId.getSourceType());
		expObjectByUseId.getIdExpression().processWithVisitor(this);
	}

	@Override
	protected void addOperationCoverage(MClass sourceClass, MOperation op) {
		coverage.getCoveredOperations().add(op);
	}

	@Override
	public void visitRange(ExpRange exp) {
		exp.getStart().processWithVisitor(this);
		exp.getEnd().processWithVisitor(this);
	}
}
