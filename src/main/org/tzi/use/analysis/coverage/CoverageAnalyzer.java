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

import java.util.TreeSet;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MModel;

/**
 * This class provides operations to analyze 
 * the model coverage of OCl expressions.
 * @author lhamann
 *
 */
public class CoverageAnalyzer {
	public static CoverageData calculateModelCoverage(MModel model) {
		CoverageData result = new CoverageData();
		
		CoverageCalculationVisitor visitorOverall = new CoverageCalculationVisitor();
		CoverageCalculationVisitor visitorSingleInv;
		
		for (MClassInvariant inv : model.classInvariants()) {
			visitorSingleInv = new CoverageCalculationVisitor();
			inv.expandedExpression().processWithVisitor(visitorOverall);
			inv.expandedExpression().processWithVisitor(visitorSingleInv);
			
			result.getCoveredClassesByInvariant().put(inv, new TreeSet<MClass>(visitorSingleInv.getClassCoverage().keySet()));
			result.getCoveredAssocsByInvariant().put(inv, new TreeSet<MAssociation>(visitorSingleInv.getAssociationCoverage().keySet()));
			result.getCoveredAttributesByInvariant().put(inv, new TreeSet<AttributeAccessInfo>(visitorSingleInv.getAttributeAccessCoverage().keySet()));
			result.getCompleteCoveredClassesByInvariant().put(inv, new TreeSet<MClass>(visitorSingleInv.getCompleteClassCoverage().keySet()));
		}
		
		result.setResult(model, visitorOverall);
		return result;
	}
}
