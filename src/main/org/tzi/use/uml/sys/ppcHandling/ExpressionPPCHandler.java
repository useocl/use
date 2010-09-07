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

import static org.tzi.use.util.StringUtil.inQuotes;

import java.io.PrintWriter;
import java.util.Deque;
import java.util.List;

import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.util.Log;

/**
 * TODO
 * @author Daniel Gent
 *
 */
public class ExpressionPPCHandler implements PPCHandler {
	/** TODO */
	private PrintWriter fOutput;

	
	/**
	 * TODO
	 * @param output
	 */
	public ExpressionPPCHandler(PrintWriter output) {
		fOutput = output;
	}
	
	
	/**
	 * TODO
	 */
	public ExpressionPPCHandler() {
		fOutput = new PrintWriter(Log.out(), true);
	}

	
	
	@Override
	public void handlePreConditions(
			MSystem system,
			MOperationCall operationCall) throws PreConditionCheckFailedException {
		
		List<MPrePostCondition> failedPreConditions = 
			operationCall.getFailedPreConditions();
		
		int numFailedPreConditions = failedPreConditions.size();
		
		if (numFailedPreConditions > 0) {
			fOutput.println(
					"\n[Warning] " +
					numFailedPreConditions +
					" precondition" +
					(numFailedPreConditions > 1 ? "s " : " ") +
					"in operation call " +
					inQuotes(operationCall) +
					" do" +
					(numFailedPreConditions > 1 ? " " : "es ") +
					"not hold:");
		}
		
		for (MPrePostCondition preCondition : failedPreConditions) {
			fOutput.println(
					"  " + 
					preCondition.name() + 
					": " + 
					preCondition.expression());
			
			printDetailedPPC(
					system, 
					operationCall.getPreState(), 
					preCondition.expression());
			
			fOutput.println();
		}
		
		if (numFailedPreConditions > 0) {
			fOutput.println("  call stack at the time of evaluation:");
			Deque<MOperationCall> callStack = system.getCallStack();
			int index = callStack.size();
			for (MOperationCall opCall : callStack) {
				fOutput.print("    " + index-- + ". ");
				fOutput.println(opCall + " " + opCall.getCallerString());
			}
			
			//throw new PreConditionCheckFailedException(operationCall);
		}
	}

	@Override
	public void handlePostConditions(
			MSystem system,
			MOperationCall operationCall) throws PostConditionCheckFailedException {
		
		
		List<MPrePostCondition> failedPostConditions = 
			operationCall.getFailedPostConditions();
		
		int numFailedPostConditions = failedPostConditions.size();
		
		if (numFailedPostConditions > 0) {
			fOutput.println(
					"\n[Warning] " +
					numFailedPostConditions +
					" postcondition" +
					(numFailedPostConditions > 1 ? "s " : " ") +
					"in operation call " +
					inQuotes(operationCall) +
					" do" +
					(numFailedPostConditions > 1 ? " " : "es ") +
					"not hold:");
		}
		
		for (MPrePostCondition postCondition : failedPostConditions) {
			fOutput.println(
					"  " + 
					postCondition.name() + 
					": " + 
					postCondition.expression());
			
			printDetailedPPC(
					system, 
					operationCall.getPreState(), 
					postCondition.expression());
			
			fOutput.println();
		}
				
		if (numFailedPostConditions > 0) {
			fOutput.println("  call stack at the time of evaluation:");
			Deque<MOperationCall> callStack = system.getCallStack();
			int index = callStack.size();
			for (MOperationCall opCall : callStack) {
				fOutput.print("    " + index-- + ". ");
				fOutput.println(opCall + " " + opCall.getCallerString());
			}
			
			//throw new PostConditionCheckFailedException(operationCall);
		}
	}
	
	
	/**
	 * TODO
	 * @param system
	 * @param preState
	 * @param ppc
	 */
	private void printDetailedPPC(
			MSystem system, 
			MSystemState preState, 
			Expression ppc) {
		
		Evaluator oclEvaluator = new Evaluator();
		oclEvaluator.eval(
				ppc,
				preState,
				system.state(), 
				system.getVariableEnvironment().constructVarBindings(), 
				fOutput,
				"    ");
	}
}
