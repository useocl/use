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

package org.tzi.use.uml.sys.ppcHandling;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.util.Log;
import org.tzi.use.util.StringUtil;

/**
 * TODO
 * @author Daniel Gent
 *
 */
public class OpEnterOpExitPPCHandler implements PPCHandler {
	/** TODO */
	private PrintWriter fOutput;
	
	
	/**
	 * TODO
	 */
	public OpEnterOpExitPPCHandler() {
		fOutput = new PrintWriter(Log.out(), true);
	}
	
	
	/**
	 * TODO
	 * @param output
	 */
	public OpEnterOpExitPPCHandler(PrintWriter output) {
		fOutput = output;
	}
	

	@Override
	public void handlePreConditions(
			MSystem system, 
			MOperationCall operationCall) throws PreConditionCheckFailedException {
		
		Map<MPrePostCondition, Boolean> evaluationResults = 
			operationCall.getPreConditionEvaluationResults();
		
		for (Entry<MPrePostCondition, Boolean> entry : evaluationResults.entrySet()) {
			MPrePostCondition preCondition = entry.getKey();
			fOutput.println(
					"precondition " + 
					StringUtil.inQuotes(preCondition.name()) + 
					" is " +
					entry.getValue());	
		}
		
		if (evaluationResults.values().contains(Boolean.FALSE)) {
			throw new PreConditionCheckFailedException(operationCall);
		}
	}

	
	@Override
	public void handlePostConditions(
			MSystem system,
			MOperationCall operationCall) throws PostConditionCheckFailedException {

		Map<MPrePostCondition, Boolean> evaluationResults = 
			operationCall.getPostConditionEvaluationResults();
		
		for (Entry<MPrePostCondition, Boolean> entry : evaluationResults.entrySet()) {
			MPrePostCondition postCondition = entry.getKey();
			fOutput.println(
					"postcondition " + 
					StringUtil.inQuotes(postCondition.name()) + 
					" is " +
					entry.getValue());	
			
			if (!entry.getValue()) {
				Evaluator oclEvaluator = new Evaluator();
				oclEvaluator.eval(
						postCondition.expression(),
						operationCall.getPreState(),
						system.state(), 
						system.getVariableEnvironment().constructVarBindings(), 
						fOutput);
			}
		}
		
		if (evaluationResults.values().contains(Boolean.FALSE)) {
			throw new PostConditionCheckFailedException(operationCall);
		}
	}
}
