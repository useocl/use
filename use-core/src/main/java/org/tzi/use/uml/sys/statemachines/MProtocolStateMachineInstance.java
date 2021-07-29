/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2012 Mark Richters, University of Bremen
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
package org.tzi.use.uml.sys.statemachines;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;
import org.tzi.use.uml.mm.statemachines.MFinalState;
import org.tzi.use.uml.mm.statemachines.MProtocolStateMachine;
import org.tzi.use.uml.mm.statemachines.MProtocolTransition;
import org.tzi.use.uml.mm.statemachines.MRegion;
import org.tzi.use.uml.mm.statemachines.MState;
import org.tzi.use.uml.mm.statemachines.MTransition;
import org.tzi.use.uml.mm.statemachines.MVertex;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.util.StringUtil;

/**
 * @author Lars Hamann
 *
 */
public class MProtocolStateMachineInstance {

	protected final MObject contextObject;
	
	protected Map<MRegion, MState> currentRegionsState;
	
	protected final MProtocolStateMachine stateMachine;
	
	/**
	 * <code>true</code> if the state machine instance
	 * is currently executing a transition.
	 * This means, other received events are ignored (run-to-completion).
	 */
	protected boolean executingTransition = false;
	
	/**
	 * @param objectState
	 */
	public MProtocolStateMachineInstance(MProtocolStateMachine machine, MObject object) {
		this.contextObject = object;
		this.stateMachine = machine;
		
		this.currentRegionsState = new HashMap<MRegion, MState>(stateMachine.getRegions().size());
		
		for (MRegion r : stateMachine.getRegions()) {
			this.currentRegionsState.put(r,  r.getInitialState());
		}
	}

	/**
	 * Copy constructor
	 * @param i
	 */
	public MProtocolStateMachineInstance(MProtocolStateMachineInstance i) {
		this.contextObject = i.contextObject;
		this.currentRegionsState = new HashMap<MRegion, MState>(i.currentRegionsState);
		this.stateMachine = i.stateMachine;
	}

	/**
	 * Returns the current state of the given region.
	 * @param region The region to retrieve the current state for.
	 * @return The current state of the specified region.
	 * @throws IllegalArgumentException If the specified region does not belong to this state machine.
	 */
	public MState getCurrentState(MRegion region) {
		if (!this.currentRegionsState.containsKey(region))
			throw new IllegalArgumentException("Invalid region for state machine.");
		
		return this.currentRegionsState.get(region);
	}

	/**
	 * Returns the context object of this state machine instance.
	 * @return
	 */
	public MObject getObject() {
		return this.contextObject;
	}
	
	/**
	 * The state machine this instance represents.
	 * @return
	 */
	public MProtocolStateMachine getProtocolStateMachine() {
		return stateMachine;
	}

	/**
	 * <code>true</code> if at least one transition exists for the
	 * operation call.
	 * @param ctx
	 * @param operationCall
	 * @return
	 */
	public boolean validOperationCall(EvalContext ctx, MOperationCall operationCall, Map<MRegion, Set<MTransition>> possibleTransitions) {
		
		boolean foundValid = false;
		
		for (Map.Entry<MRegion, MState> entry : this.currentRegionsState.entrySet()) {
			if (entry.getValue() instanceof MFinalState) {
				return false;
			}

			Set<MTransition> outgoingTransitions = entry.getValue().getOutgoing();
			possibleTransitions.put(entry.getKey(), new LinkedHashSet<MTransition>());
			
			for (MTransition t : outgoingTransitions) {
				MProtocolTransition pt = (MProtocolTransition)t;
				// some transitions don't have an operation, e. g., create
				if (pt.getReferred() == null) continue;
				
				boolean isValid = false;
				
				if (   operationCall.getOperation().equals(pt.getReferred())
					|| operationCall.getOperation().isValidOverrideOf(pt.getReferred())) {
					// Check the guard
					if (pt.hasGuard()) {
						Evaluator oclEvaluator = new Evaluator();
						
						Value evalResult = 
							oclEvaluator.eval(t.getGuard(), 
											  ctx.postState(), ctx.varBindings());

						if (evalResult.isBoolean() && ((BooleanValue)evalResult).isTrue()) {
							isValid = true;
						}
							
					} else {
						isValid = true;
					}
				}
				
				if (isValid) {
					possibleTransitions.get(entry.getKey()).add(t);
					foundValid = foundValid || isValid;
				}
			}
		}
		
		return foundValid;
	}

	public class TransitionResult {
		private final MTransition transition;
		
		private Value postConditionResult;
		
		private Value stateInvariantResult;

		/**
		 * @param transition
		 */
		public TransitionResult(MTransition transition) {
			super();
			this.transition = transition;
		}

		/**
		 * @return the value of the post condition evaluation 
		 */
		public Value getPostConditionResult() {
			return postConditionResult;
		}

		/**
		 * @param result the value of the postcondition evaluation to set
		 */
		public void setPostConditionResult(Value result) {
			this.postConditionResult = result;
		}

		/**
		 * @return the value of the target state invariant evaluation.
		 */
		public Value getStateInvariantResult() {
			return stateInvariantResult;
		}

		/**
		 * @param result the value of the target state invariant evaluation to set
		 */
		public void setStateInvariantResult(Value result) {
			this.stateInvariantResult = result;
		}

		/**
		 * @return the transition
		 */
		public MTransition getTransition() {
			return transition;
		}
		
		/**
		 * 
		 * @return <code>true</code> if the transition was successful, <code>false</code> otherwise.
		 */
		public boolean wasSuccessfull() {
			return (postConditionResult == null || !postConditionResult.isUndefined() && ((BooleanValue)postConditionResult).isTrue()) &&
				   (stateInvariantResult == null || !stateInvariantResult.isUndefined() && ((BooleanValue)stateInvariantResult).isTrue());
		}
		
		@Override
		public String toString() {
			StringBuilder s = new StringBuilder();
			s.append(transition);
			if (postConditionResult != null)  s.append("\n    Post condition result: ").append(postConditionResult);
			if (stateInvariantResult != null) s.append("\n    Target state invariant result: ").append(stateInvariantResult);
			return s.toString();
		}
	}
	
	/**
	 * Tries to finish the transition <code>t</code>.
	 * @param t
	 * @param ctx
	 * @param operationCall
	 * @param An error printer, can be <code>null</code>.
	 * @return
	 */
	public TransitionResult evaluateTransition(MTransition t, EvalContext ctx, MOperationCall operationCall) {
		
		TransitionResult result = new TransitionResult(t);
		
		if (!t.getSource().equals(this.currentRegionsState.get(t.getContainer()))) {
			throw new IllegalArgumentException(
					"Cannot execute transition "
							+ StringUtil.inQuotes(t.toString())
							+ ", because the protocol state machine is not in the source state of the transition");
		}
		
		MProtocolTransition pt = (MProtocolTransition)t;
		MState targetState = (MState)t.getTarget();
		
		if (pt.hasPostCondition()) {
			Evaluator oclEvaluator = new Evaluator();
			
			Value evalResult = 
				oclEvaluator.eval(pt.getPostCondition(), ctx.preState(), ctx.postState(), ctx.varBindings());
				
			result.setPostConditionResult(evalResult);
		}
		
		if (targetState.getStateInvariant() != null) {
			Evaluator oclEvaluator = new Evaluator();
			VarBindings b = new VarBindings();
			b.push("self", new ObjectValue(stateMachine.getContext(), contextObject));
			Value evalResult = 
				oclEvaluator.eval(targetState.getStateInvariant(), ctx.postState(), ctx.varBindings());

			result.setStateInvariantResult(evalResult);
		}
		
		return result;
	}

	public void doTransition(MTransition t) {
		MState targetState = (MState)t.getTarget();
		this.currentRegionsState.put(t.getContainer(), targetState);
	}
	
	/**
	 * @param t
	 */
	public void revertTransition(MTransition t) {
		this.currentRegionsState.put(t.getContainer(), (MState)t.getSource());
	}
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		
		res.append(this.getProtocolStateMachine().toString());
		res.append(" [self: ");
		res.append(contextObject.name());
		res.append(", current state: ");
		res.append(this.currentRegionsState.get(this.getProtocolStateMachine().getDefaultRegion()).name());
		res.append("]");
		
		return  res.toString();
	}

	/**
	 * Validates the state invariant of all current states
	 * of all regions.  
	 * @param systemState The system state used to validate the state invariants.
	 * @param errOut A PrintWriter for error reporting.
	 * @return <code>true</code> if all state invariants are valid, <code>false</code> otherwise.
	 */
	public boolean checkStateInvariant(MSystemState systemState, @NonNull PrintWriter errOut) {
		boolean valid = true;
		Evaluator evaluator = new Evaluator();
		VarBindings bindings = getVarBindings(systemState);
		MState s;
		
		for (Map.Entry<MRegion,MState> entry : currentRegionsState.entrySet()) {
			s = entry.getValue();
			
			if (s == null || s.getStateInvariant() == null) continue;
			
			Value result = evaluator.eval(s.getStateInvariant(), systemState, bindings);
			
			if (!result.isBoolean() || ((BooleanValue)result).isFalse()) {
				valid = false;
				errOut.print("State invariant violation in state ");
				errOut.print(StringUtil.inQuotes(s.name()));
				errOut.print(" of psm ");
				errOut.print(StringUtil.inQuotes(this.stateMachine.toString()));
				errOut.print(" for object ");
				errOut.println(this.contextObject.toString());
			}
		}
		
		return valid;
	}
	
	/**
	 * Determines the states of all regions of this state machine by
	 * evaluating the state invariants.
	 * If exactly one state invariant in a region is <code>true</code>,
	 * the state is set as the current one in this region. 
	 */
	public void determineState(MSystemState systemState, PrintWriter out) {
		Set<MState> possibleStates = new HashSet<MState>();
		
		Evaluator evaluator = new Evaluator();
		VarBindings bindings = getVarBindings(systemState);
		
		for (MRegion r : stateMachine.getRegions()) {
			possibleStates.clear();
			
			for (MVertex v : r.getSubvertices()) {
				if (v instanceof MState) {
					MState s = (MState)v;
					if (s.getStateInvariant() == null) continue;
					
					Value result = evaluator.eval(s.getStateInvariant(), systemState, bindings);
					
					if (result.isBoolean() && ((BooleanValue)result).isTrue())
						possibleStates.add(s);
				}
			}
			
			if (possibleStates.isEmpty()) {
				out.println("Could not find a valid state for psm "
						+ stateMachine.toString() + " for "
						+ contextObject.name());
				currentRegionsState.put(r, null);
			} else if (possibleStates.size() > 1) {
				out.println("Found multiple valid states for psm "
						+ stateMachine.toString() + " for "
						+ contextObject.name());
				out.println("Valid states:");
				for (MState s : possibleStates) {
					out.print("   ");
					out.println(s.name());
				}
				currentRegionsState.put(r, null);
			} else {
				currentRegionsState.put(r, possibleStates.iterator().next());
			}
		}
	}

	private VarBindings getVarBindings(MSystemState systemState) {
		VarBindings bindings = new VarBindings(systemState);
		bindings.push("self", new ObjectValue(stateMachine.getContext(), contextObject));
		return bindings;
	}

	/**
	 * @return the executingTransition
	 */
	public boolean isExecutingTransition() {
		return executingTransition;
	}

	/**
	 * @param executingTransition the executingTransition to set
	 */
	public void setExecutingTransition(boolean executingTransition) {
		this.executingTransition = executingTransition;
	}
}
