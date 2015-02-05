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

import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.uml.mm.MPrePostCondition;

/**
 * This class provides operations to analyze 
 * the model coverage of OCl expressions.
 * @author Lars Hamann
 *
 */
public class CoverageAnalyzer {
	
	/**
	 * Calculates the model coverage for the complete model and for each invariant. 
	 * @param model The {@link MModel} to calculate the coverage
	 * @param expandOprations If <code>true</code>, operation expressions will also be considered. Otherwise, only the operation itself is marked as covered.  
	 * @return A {@link Map} which contains the data for each {@link MClassInvariant} and {@link MPrePostCondition} and for the complete {@link MModel}.
	 */
	public static Map<MModelElement, CoverageData> calculateModelCoverage(MModel model, boolean expandOprations) {
		Map<MModelElement, CoverageData> result = new HashMap<MModelElement, CoverageData>(
				model.classInvariants().size() + 1);
		
		CoverageCalculationVisitor visitorOverall = new CoverageCalculationVisitor(expandOprations);
		CoverageCalculationVisitor visitorSingleElement;
		
		for (MClassInvariant inv : model.classInvariants()) {
			visitorSingleElement = new CoverageCalculationVisitor(expandOprations);
			inv.expandedExpression().processWithVisitor(visitorOverall);
			inv.expandedExpression().processWithVisitor(visitorSingleElement);
						
			result.put(inv, visitorSingleElement.getCoverageData());
		}
		
		for (MPrePostCondition ppc : model.prePostConditions()) {
			visitorSingleElement = new CoverageCalculationVisitor(expandOprations);
			ppc.expression().processWithVisitor(visitorOverall);
			ppc.expression().processWithVisitor(visitorSingleElement);
						
			result.put(ppc, visitorSingleElement.getCoverageData());
		}
		
		visitorOverall.getCoverageData().addUncoveredClasses(model);
		result.put(model, visitorOverall.getCoverageData());
		return result;
	}
}
