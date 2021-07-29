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

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MModelElement;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.MPrePostCondition;

/**
 * This class provides operations to analyze the model coverage of OCl
 * expressions.
 * 
 * @author Lars Hamann
 *
 */
public class CoverageAnalyzer {

	public static Map<MModelElement, CoverageData> calculateInvariantCoverage(
			MModel model, boolean expandOperations) {

		Map<MModelElement, CoverageData> result = new HashMap<MModelElement, CoverageData>();

		CoverageCalculationVisitor globalVisitor = new CoverageCalculationVisitor(
				expandOperations);
		CoverageCalculationVisitor localVisitor;

		for (MClassInvariant invariant : model.classInvariants()) {
			localVisitor = new CoverageCalculationVisitor(expandOperations);
			invariant.expandedExpression().processWithVisitor(localVisitor);

			invariant.expandedExpression().processWithVisitor(globalVisitor);

			result.put(invariant, localVisitor.getCoverageData());
		}

		globalVisitor.getCoverageData().addUncoveredClasses(model);
		result.put(model, globalVisitor.getCoverageData());

		return result;
	}

	public static Map<MModelElement, CoverageData> calculatePreConditionCoverage(
			MModel model, boolean expandOperations) {

		Map<MModelElement, CoverageData> result = new HashMap<MModelElement, CoverageData>();

		CoverageCalculationVisitor globalVisitor = new CoverageCalculationVisitor(
				expandOperations);
		CoverageCalculationVisitor localVisitor;

		for (MPrePostCondition preCondition : model.preConditions()) {

			localVisitor = new CoverageCalculationVisitor(expandOperations);
			preCondition.expression().processWithVisitor(localVisitor);

			preCondition.expression().processWithVisitor(globalVisitor);

			result.put(preCondition, localVisitor.getCoverageData());
		}

		globalVisitor.getCoverageData().addUncoveredClasses(model);
		result.put(model, globalVisitor.getCoverageData());

		return result;
	}

	public static Map<MModelElement, CoverageData> calculatePostConditionCoverage(
			MModel model, boolean expandOperations) {

		Map<MModelElement, CoverageData> result = new HashMap<MModelElement, CoverageData>();

		CoverageCalculationVisitor globalVisitor = new CoverageCalculationVisitor(
				expandOperations);
		CoverageCalculationVisitor localVisitor;

		for (MPrePostCondition postCondition : model.postConditions()) {

			localVisitor = new CoverageCalculationVisitor(expandOperations);
			postCondition.expression().processWithVisitor(localVisitor);

			postCondition.expression().processWithVisitor(globalVisitor);

			result.put(postCondition, localVisitor.getCoverageData());
		}

		globalVisitor.getCoverageData().addUncoveredClasses(model);
		result.put(model, globalVisitor.getCoverageData());

		return result;
	}

	public static Map<MModelElement, CoverageData> calculateContractCoverage(
			MModel model, boolean expandOperations) {

		Map<MModelElement, CoverageData> result = new HashMap<MModelElement, CoverageData>();

		CoverageCalculationVisitor globalVisitor = new CoverageCalculationVisitor(
				expandOperations);
		CoverageCalculationVisitor localVisitor;

		for (MPrePostCondition ppc : model.prePostConditions()) {

			localVisitor = new CoverageCalculationVisitor(expandOperations);
			ppc.expression().processWithVisitor(localVisitor);

			ppc.expression().processWithVisitor(globalVisitor);

			result.put(ppc, localVisitor.getCoverageData());
		}

		globalVisitor.getCoverageData().addUncoveredClasses(model);
		result.put(model, globalVisitor.getCoverageData());

		return result;
	}

	/**
	 * Calculates the model coverage for the complete model and for each
	 * invariant.
	 * 
	 * @param model
	 *            The {@link MModel} to calculate the coverage
	 * @param expandOperations
	 *            If <code>true</code>, operation expressions will also be
	 *            considered. Otherwise, only the operation itself is marked as
	 *            covered.
	 * @return A {@link Map} which contains the data for each
	 *         {@link MClassInvariant} and {@link MPrePostCondition} and for the
	 *         complete {@link MModel}.
	 */
	public static Map<MModelElement, CoverageData> calculateTotalCoverage(
			MModel model, boolean expandOperations) {
		
		Map<MModelElement, CoverageData> result = new HashMap<MModelElement, CoverageData>();

		CoverageCalculationVisitor globalVisitor = new CoverageCalculationVisitor(
				expandOperations);
		CoverageCalculationVisitor localVisitor;

		for (MClassInvariant invariant : model.classInvariants()) {
			localVisitor = new CoverageCalculationVisitor(expandOperations);
			invariant.expandedExpression().processWithVisitor(localVisitor);

			invariant.expandedExpression().processWithVisitor(globalVisitor);

			result.put(invariant, localVisitor.getCoverageData());
		}

		for (MPrePostCondition ppc : model.prePostConditions()) {

			localVisitor = new CoverageCalculationVisitor(expandOperations);
			ppc.expression().processWithVisitor(localVisitor);

			ppc.expression().processWithVisitor(globalVisitor);

			result.put(ppc, localVisitor.getCoverageData());
		}
		
		for (MClass mClass : model.classes()) {
			for (MOperation mOperation : mClass.operations()) {
				if(!mOperation.hasExpression()){
					continue;
				}
				
				localVisitor = new CoverageCalculationVisitor(expandOperations);
				
				mOperation.expression().processWithVisitor(localVisitor);
				
				result.put(mOperation, localVisitor.getCoverageData());
			}
		}

		globalVisitor.getCoverageData().addUncoveredClasses(model);
		result.put(model, globalVisitor.getCoverageData());

		return result;
	}
}
