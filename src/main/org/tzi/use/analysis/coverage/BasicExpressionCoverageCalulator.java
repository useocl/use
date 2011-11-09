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
import org.tzi.use.uml.ocl.expr.Expression;

/**
 * TODO
 * @author Lars Hamann
 *
 */
public class BasicExpressionCoverageCalulator extends AbstractCoverageVisitor {

	private BasicCoverageData coverage;
	
	/**
	 * @param inv
	 */
	public BasicExpressionCoverageCalulator() { }

	/**
	 * @return
	 */
	public BasicCoverageData calcualteCoverage(Expression expr) {
		this.coverage = new BasicCoverageData();
		expr.processWithVisitor(this);
		return coverage;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.analysis.coverage.AbstractCoverageVisitor#addClassCoverage(org.tzi.use.uml.mm.MClass)
	 */
	@Override
	protected void addClassCoverage(MClass cls) {
		coverage.getCoveredClasses().add(cls);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.analysis.coverage.AbstractCoverageVisitor#addAssociationEndCoverage(org.tzi.use.uml.mm.MNavigableElement)
	 */
	@Override
	protected void addAssociationEndCoverage(MNavigableElement dst) {
		coverage.getCoveredAssociations().add(dst.association());		
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.analysis.coverage.AbstractCoverageVisitor#addAssociationCoverage(org.tzi.use.uml.mm.MAssociation)
	 */
	@Override
	protected void addAssociationCoverage(MAssociation assoc) {
		coverage.getCoveredAssociations().add(assoc);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.analysis.coverage.AbstractCoverageVisitor#addAttributeCoverage(org.tzi.use.uml.mm.MClass, org.tzi.use.uml.mm.MAttribute)
	 */
	@Override
	protected void addAttributeCoverage(MClass sourceClass, MAttribute att) {
		coverage.getCoveredAttributes().add(att);		
	}

}
