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

package org.tzi.use.uml.sys.ppcHandling;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.mm.statemachines.MProtocolStateMachine;
import org.tzi.use.uml.mm.statemachines.MProtocolTransition;
import org.tzi.use.uml.mm.statemachines.MRegion;
import org.tzi.use.uml.mm.statemachines.MState;
import org.tzi.use.uml.mm.statemachines.MTransition;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.sys.MInstanceState;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.statemachines.MProtocolStateMachineInstance;
import org.tzi.use.util.Log;
import org.tzi.use.util.StringUtil;

/**
 * PPC handler for legacy openter/opexit commands.
 * @author Daniel Gent
 * @author Lars Hamann
 *
 */
public class OpEnterOpExitPPCHandler implements PPCHandler {
	
	private static OpEnterOpExitPPCHandler defaultHandlerToLog;
	
	/**
	 * Singleton instance of the default handler which outputs to {@link Log#out}.
	 * @return Default handler which outputs to {@link Log#out}.
	 */
	public static OpEnterOpExitPPCHandler getDefaultOutputHandler() {
		if (defaultHandlerToLog == null) {
			defaultHandlerToLog = new OpEnterOpExitPPCHandler();
		}
		
		return defaultHandlerToLog;
	}
	
	private PrintWriter fOutput;
	
	/**
	 * Constructs a handler which outputs to {@link Log#out}.
	 */
	private OpEnterOpExitPPCHandler() {
		fOutput = new PrintWriter(Log.out(), true);
	}
	
	
	/**
	 * Constructs a handler which outputs to <code>output</code>.
	 * @param output
	 */
	public OpEnterOpExitPPCHandler(PrintWriter output) {
		fOutput = output;
	}
	

	@Override
	public void handlePreConditions(MSystem system, MOperationCall operationCall) throws PreConditionCheckFailedException {
		
		Map<MPrePostCondition, Boolean> evaluationResults = 
			operationCall.getPreConditionEvaluationResults();
		
		boolean allValid = true;
		
		for (Entry<MPrePostCondition, Boolean> entry : evaluationResults.entrySet()) {
			MPrePostCondition preCondition = entry.getKey();
			fOutput.println(
					"precondition " + 
					StringUtil.inQuotes(preCondition.name()) + 
					" is " +
					entry.getValue());
			allValid &= entry.getValue().booleanValue();
		}
		
		if (!allValid) {
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
                        (Expression) postCondition.expression(),
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


	@Override
	public void handleTransitionsPre(MSystem system, MOperationCall operationCall)
			throws PreConditionCheckFailedException {

		MInstanceState selfState = operationCall.getSelf().state(system.state());
		Set<MProtocolStateMachineInstance> machinesSet = selfState.getProtocolStateMachinesInstances();
		List<MProtocolStateMachineInstance> machines = new ArrayList<>(machinesSet);
		
		Collections.sort(machines, new Comparator<MProtocolStateMachineInstance>() {
			@Override
			public int compare(MProtocolStateMachineInstance o1, MProtocolStateMachineInstance o2) {
				return o1.getProtocolStateMachine().name().compareTo(o2.getProtocolStateMachine().name());
			}
		});
		
		for (MProtocolStateMachineInstance psmInstance : machines) {
			MProtocolStateMachine psm = psmInstance.getProtocolStateMachine(); 
			if (psm.handlesOperation(operationCall.getOperation())) {				
				Map<MRegion, Set<MTransition>> possibleTransitions = operationCall.getPossibleTransitions(psmInstance);
				
				for (MRegion r : psm.getRegions()) {
					MState s = psmInstance.getCurrentState(r);
					Set<MTransition> possibleInRegion = possibleTransitions.get(r);
					
					for (MTransition t : s.getOutgoing()) {
						MProtocolTransition pt = (MProtocolTransition)t;
						// May refer to create, etc.
						if (pt.getReferred() == null) continue;
						
						if (operationCall.getOperation().equals(pt.getReferred()) ||
							operationCall.getOperation().isValidOverrideOf(pt.getReferred())) {
							boolean possible = possibleInRegion.contains(t);
							fOutput.println("Transition " + t.toString() + " is " + (possible ? "" : "not ") + "possible.");
						}
					}
				}
			}
		}
	}


	@Override
	public void handleTransitionsPost(MSystem system,
			MOperationCall operationCall)
			throws PostConditionCheckFailedException {
		// TODO Auto-generated method stub
		
	}
}
