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

package org.tzi.use.gen.assl.dynamics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.tzi.use.gen.assl.statics.GInstrTry_Attribute;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.soil.MAttributeAssignmentStatement;
import org.tzi.use.uml.sys.soil.MSequenceStatement;
import org.tzi.use.uml.sys.soil.MStatement;

import combinatorics.CombinatoricsVector;
import combinatorics.Iterator;
import combinatorics.permutations.PermutationWithRepetitionGenerator;

/**
 * Dynamic part of the attribute try
 * @author Lars Hamann
 *
 */
public class GEvalInstrTry_Attribute extends GEvalInstrTry {

	GInstrTry_Attribute instr;
	
	/**
	 * @param instr
	 */
	public GEvalInstrTry_Attribute(GInstrTry_Attribute instr, boolean first ) {
    	super(first);
		this.instr = instr;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.dynamics.GEvalInstruction#eval(org.tzi.use.gen.assl.dynamics.GConfiguration, org.tzi.use.gen.assl.dynamics.IGCaller, org.tzi.use.gen.assl.dynamics.IGCollector)
	 */
	@Override
	public void eval(GConfiguration conf, IGCaller caller, IGCollector collector)
			throws GEvaluationException {
		
		if (collector.doDetailPrinting())
			collector.detailPrintWriter().println(
					new StringBuilder("evaluating `").append(instr)
							.append("'").toString());
		
		Value rangeResult = conf.evalExpression(instr.getObjects().expression());
		
		if (rangeResult.isUndefined()) {
			 collector.invalid( "Can't execute `" + instr +
                     "', because the result of `" + 
                     instr.getObjects() +
                     "' returned the undefined value." );
			 return;
		}
		
		Value valuesResult = conf.evalExpression(instr.getValues().expression());
		
		if (valuesResult.isUndefined()) {
			 collector.invalid( "Can't execute `" + instr +
                     "', because the result of `" + 
                     instr.getValues() +
                     "' returned the undefined value." );
			 return;
		}
		
		CollectionValue range = (CollectionValue)rangeResult;
		CollectionValue values = (CollectionValue)valuesResult;
		
		List<Value> valuesList = new ArrayList<Value>(values.collection()); 
		List<MObject> rangeObjects = new LinkedList<MObject>();
		for (Value object : range.collection()) {
			if (object.isUndefined()) {
				collector.invalid( "Can't execute `" + instr +
	                     "', because the result of `" + 
	                     instr.getObjects() +
	                     "' contained the undefined value." );
				return;
			}
			rangeObjects.add(((ObjectValue)object).value() );
		}
		
		CombinatoricsVector<Value> vector = new CombinatoricsVector<Value>(valuesList);
		PermutationWithRepetitionGenerator<Value> gen = new PermutationWithRepetitionGenerator<Value>(
				vector, range.size());
		Iterator<CombinatoricsVector<Value>> iter = gen.createIterator();
		MSequenceStatement assignStatements = new MSequenceStatement();
		this.initProgress(gen.getNumberOfGeneratedObjects());
		
		MAttribute attr = instr.getAttribute();
		int iValue = 0;
		CombinatoricsVector<Value> currentCombination;
		MStatement assignStmt;
		MSystem system = conf.systemState().system();
		long cmb = 0;
		
		while (!collector.canStop() && iter.hasNext()) {
			assignStatements.clear();
			currentCombination = iter.next();
			iValue = 0;
			
			for (MObject source : rangeObjects) {
				assignStmt = new MAttributeAssignmentStatement(source, attr, currentCombination.getValue(iValue));
				if (collector.doBasicPrinting())
	                collector.basicPrintWriter().println(assignStmt.getShellCommand());
				
				assignStatements.appendStatement(assignStmt);
				++iValue;
			}
		
			try {	
	    		system.execute(assignStatements, true, false, false);
			} catch (MSystemException e) {
				throw new GEvaluationException(e);
			}
			
			this.outPutProgress(++cmb);
			
			caller.feedback(conf, null, collector);
		
			// Remove unique name state, because no undo statements are executed
        	system.getUniqueNameGenerator().popState();
        	
			if (collector.expectSubsequentReporting()) {
            	for (MStatement s : assignStatements.getStatements()) {
            		if (!s.isEmptyStatement()) {
            			collector.subsequentlyPrependStatement(s);
            		}
            	}
            }
		}
		
		this.endProgress();
	}

}
