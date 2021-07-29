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

package org.tzi.use.uml.sys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.mm.statemachines.MRegion;
import org.tzi.use.uml.mm.statemachines.MTransition;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.ppcHandling.DoNothingPPCHandler;
import org.tzi.use.uml.sys.ppcHandling.ExpressionPPCHandler;
import org.tzi.use.uml.sys.ppcHandling.OpEnterOpExitPPCHandler;
import org.tzi.use.uml.sys.ppcHandling.PPCHandler;
import org.tzi.use.uml.sys.ppcHandling.SoilPPCHandler;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.uml.sys.statemachines.MProtocolStateMachineInstance;
import org.tzi.use.util.StringUtil;


/**
 * This class handles an operation call
 * inside of soil or with the legacy command "openter".
 * @author Daniel Gent
 *
 */
public class MOperationCall {	
	/** The caller expression of the operation call.
	 *  <p>Used for detailed output.</p>
	 *  <p>If this attribute is <code>null</code>, {@link #fCallerStatement} is set.</p> 
	 */
	private final Expression fCallerExpression;
	/** The caller statement of the operation call.
	 *  <p>Used for detailed output</p>
	 *  <p>If this attribute is <code>null</code>, {@link #fCallerExpression} is set.</p> 
	 */
	private final MStatement fCallerStatement;
	
	/** The "self-pointer" for this operation call, i. e., 
	 *  the receiver of the message
	 */
	private MObject fSelf;
	
	/**
	 * The called operation
	 */
	private MOperation fOperation;
	
	/** 
	 * The arguments of the operation all
	 */
	private Value[] fArguments;
	
	/** 
	 * Reference to the system state before the operation was called.
	 * Needed for using <code>@pre</code> in postconditions and other
	 * functions. 
	 */
	private MSystemState fPreState;
	
	/** The preferred PPC handler. Can be set externally */
	private PPCHandler fPreferredPPCHandler;
	
	/** Stores the evaluation results of the preconditions */
	private Map<MPrePostCondition, Boolean> fPreConditionCheckResults;
	
	/** Stores the evaluation results of the postconditions */
	private Map<MPrePostCondition, Boolean> fPostConditionCheckResults;
	
	/** Information about the success of the operation enter. Is set externally. */
	private boolean fEnteredSuccessfully = false;
	
	/** Information about the current state of the operation call. */
	private boolean fExited = false;
	
	/** Information about the success of the operation exit. Is set externally. */
	private boolean fExitedSuccessfully = false;
	
	/** Information about the success of the overall operation call. Is set externally. */
	private boolean fExecutionFailed = false;
	
	/** 
	 * Saves the result value if this operation call, if any. 
	 */
	private Value fResultValue;
	
	/**
	 * The variable bindings used by the operation call
	 */
	private VarBindings varBindings;
	
	/**
	 * Cache for possible transitions that could be
	 * taken when the operation returns.	
	 */
	private Map<MProtocolStateMachineInstance, Map<MRegion, Set<MTransition>>> possibleTransitions = null;
		
	/**
	 * Saves all executed transitions after the operation was exited.
	 * Needed to be able to revert an operation call.
	 */
	private Map<MProtocolStateMachineInstance, Set<MTransition>> executedTransitions = null;
	
	/**
	 * Private constructor with all required values.
	 * @param self
	 * @param operation
	 * @param arguments
	 */
	private MOperationCall(
			MObject self, 
			MOperation operation, 
			Value[] arguments,
			Expression callerExpression,
			MStatement callerStatement) {
		
		fSelf = self;
		fOperation = operation;
		fArguments = arguments;
		fCallerExpression = callerExpression;
		fCallerStatement = callerStatement;
	}
	
	
	/**
	 * Constructs a new operation call object with the statement <code>caller</code>
	 * as the source of the operation call.
	 * @param caller
	 * @param self
	 * @param operation
	 * @param arguments
	 */
	public MOperationCall(
			MStatement caller,
			MObject self, 
			MOperation operation, 
			Value[] arguments) {
		
		this(self, operation, arguments, null, caller);
	}
	
	
	/**
	 * Constructs a new operation call object with the expression <code>caller</code>
	 * as the source of the operation call.
	 * @param caller
	 * @param self
	 * @param operation
	 * @param arguments
	 */
	public MOperationCall(
			Expression caller,
			MObject self,
			MOperation operation,
			Value[] arguments) {
		
		this(self, operation, arguments, caller, null);
	}
	
	/**
	 * The receiver of the operation call.
	 * @return
	 */
	public MObject getSelf() {
		return fSelf;
	}
	
	
	/**
	 * Returns the pre state of the operation call.
	 * @return
	 */
    public MSystemState getPreState() {
    	return fPreState;
    }
    
	/**
	 * The called operation.
	 * @return
	 */
	public MOperation getOperation() {
		return fOperation;
	}
	
	/**
	 * Returns all argument values.
	 * @return
	 */
	public Value[] getArguments() {
		return fArguments;
	}

	/**
	 * Access to the information of the overall operation call execution.
	 * @return <code>true</code> if the execution failed.
	 */
	public boolean executionHasFailed() {
		return fExecutionFailed;
	}
	
	/**
	 * Sets information about the execution of the operation.
	 * @param executionFailed
	 */
	public void setExecutionFailed(boolean executionFailed) {
		fExecutionFailed = executionFailed;
	}
	
	/**
	 * Sets the pre state of the operation call.
	 * @param preState
	 */
	public void setPreState(MSystemState preState) {
		fPreState = preState;
	}
	
	
	/**
	 * Access to the result value, if any.
	 * @return The result value of the operation. Maybe <code>null</code>.
	 */
	public Value getResultValue() {
		return fResultValue;
	}
	
	/**
	 * Sets the result value of this operation call.
	 * @param resultValue The result value.
	 */
	public void setResultValue(Value resultValue) {
		fResultValue = resultValue;
	}
	
	
	/**
	 * Access to information about the entrance of the operation call.
	 * @return
	 */
	public boolean enteredSuccessfully() {
		return fEnteredSuccessfully;
	}
	
	
	/**
	 * Sets information about the entrance success.
	 * @param enteredSuccessfully The success state of the operation call enter.
	 */
	public void setEnteredSuccessfully(boolean enteredSuccessfully) {
		fEnteredSuccessfully = enteredSuccessfully;
	}
	
	
	/**
	 * Sets information about the exit success.
	 * @param exitedSuccessfully The success state of the exit.
	 */
	public void setExitedSuccessfully(boolean exitedSuccessfully) {
		fExitedSuccessfully = exitedSuccessfully;
		fExited = true;
	}
	
	
	/**
	 * Sets the state of the operation call.
	 * @param exited
	 */
	public void setExited(boolean exited) {
		fExited = exited;
	}
	
	
	/**
	 * Access to the information about the success of the operation exit.
	 * @return
	 */
	public boolean exitedSuccessfully() {
		return fExitedSuccessfully;
	}
	
	
	/**
	 * Access to the information about the current state of the operation call.
	 * @return
	 */
	public boolean exited() {
		return fExited;
	}
	
	
	/**
	 * <code>true</code>, if the called operation defines pre conditions.
	 * @return
	 */
	public boolean hasPreConditions() {
		return !fOperation.preConditions().isEmpty();
	}
	
	
	/**
	 * <code>true</code>, if the called operation defines post conditions.
	 * @return
	 */
	public boolean hasPostConditions() {
		return !fOperation.postConditions().isEmpty();
	}
	
	
	/**
	 * Returns all evaluation results of the pre conditions.
	 * @return
	 */
	public Map<MPrePostCondition, Boolean> getPreConditionEvaluationResults() {
		return fPreConditionCheckResults;
	}
	
	
	/**
	 * Returns all failed pre conditions.
	 * @return
	 */
	public List<MPrePostCondition> getFailedPreConditions() {
		return getFailedPPCs(fPreConditionCheckResults);
	}

	
	/**
	 * Sets the evaluation results of the post conditions.
	 * @param results
	 */
	public void setPreConditionsCheckResult(Map<MPrePostCondition, Boolean> results) {
		fPreConditionCheckResults = results;
	}
	
	
	/**
	 * Returns all evaluation results of the post conditions. 
	 * @return
	 */
	public Map<MPrePostCondition, Boolean> getPostConditionEvaluationResults() {
		return fPostConditionCheckResults;
	}
	
	
	/**
	 * Returns all failed post conditions.
	 * @return
	 */
	public List<MPrePostCondition> getFailedPostConditions() {
		return getFailedPPCs(fPostConditionCheckResults);
	}
	
	
	/**
	 * Sets the evaluation results of the post conditions.
	 * @param results
	 */
	public void setPostConditionsCheckResult(Map<MPrePostCondition, Boolean> results) {
		fPostConditionCheckResults = results;
	}
	
	
	/**
	 * Returns the default PPCHandler of this operation call.
	 * The default values are:
	 * <ul>
	 *   <li>{@link ExpressionPPCHandler#getDefaultOutputHandler()}: <br/>If the called operation has an OCL-body, i. e., it is a OCL query operation.
	 *   <li>{@link SoilPPCHandler#getDefaultOutputHandler()}:</br> If the called operation has a SOIL-body.
	 *   <li>{@link OpEnterOpExitPPCHandler#getDefaultOutputHandler()}:</br> If it has no body (legacy openter / opexit).
	 *   <li>{@link DoNothingPPCHandler#getInstance()}:</br> otherwise.
	 * </ul> 
	 * @return The default PPCHandler for the called operation.
	 */
	public PPCHandler getDefaultPPCHandler() {
		if (fOperation.hasExpression()) {
			return ExpressionPPCHandler.getDefaultOutputHandler();
		} else if (fOperation.hasStatement()) {
			return SoilPPCHandler.getDefaultOutputHandler();
		} else if (!fOperation.hasBody()) {
			return OpEnterOpExitPPCHandler.getDefaultOutputHandler();
		} else {
			return DoNothingPPCHandler.getInstance();
		}
	}
	
	
	/**
	 * Returns a possible defined preferred PPC handler, which overrides the default handler.
	 * @return
	 */
	public PPCHandler getPreferredPPCHandler() {
		return fPreferredPPCHandler;
	}
	
	
	/**
	 * Returns <code>true</code> if a preferred
	 * PPC handler was set.
	 * @return
	 */
	public boolean hasPreferredPPCHandler() {
		return fPreferredPPCHandler != null;
	}
	
	
	/**
	 * Sets the preferred PPC handler.
	 * @param preferredPPCHandler
	 */
	public void setPreferredPPCHandler(PPCHandler preferredPPCHandler) {
		fPreferredPPCHandler = preferredPPCHandler;
	}
		
	/**
	 * Extracts all failed pre- or postconditions from the provided evaluation results.
	 * @param evaluationResults The evaluation results to filter.
	 * @return A list of all failed pre- or postconditions which were included in <code>evaluationResults</code>.
	 */
	private List<MPrePostCondition> getFailedPPCs(Map<MPrePostCondition, Boolean> evaluationResults) {
		
		List<MPrePostCondition> result = new ArrayList<MPrePostCondition>();
		
		for (Entry<MPrePostCondition, Boolean> entry : evaluationResults.entrySet()) {
			if (!entry.getValue()) {
				result.add(entry.getKey());
			}
		}
		
		return result;
	}
	
	
	public VarBindings getVarBindings() {
		return varBindings;
	}


	public void setVarBindings(VarBindings varBindings) {
		this.varBindings = varBindings;
	}
	
	/**
	 * Returns a String with information about the caller.
	 * <p>The String has the following form:<br/>
	 * <b>[caller: </b> <code>(callerExpression|callerStatement|<b>&lt;unknown&gt;</b>)</code><b>@</b><code>(sourcePos|<b>&lt;unknown&gt;)</b></code><b>]</b></p>
	 * @return
	 */
	public String getCallerString() {
		StringBuilder result = new StringBuilder();
		SrcPos sourcePosition;
		
		result.append("[caller: ");
		if (fCallerExpression != null) {
			fCallerExpression.toString(result);
			sourcePosition = fCallerExpression.getSourcePosition();
		} else if (fCallerStatement != null) {
			result.append(fCallerStatement.toString());
			sourcePosition = fCallerStatement.getSourcePosition();
		} else {
			result.append("<unknown>");
			sourcePosition = null;
		}
		
		result.append("@");
		if (sourcePosition == null) {
			result.append("<unknown>");
		} else {
			result.append(sourcePosition.toString(true));
		}
		result.append("]");
		
		return result.toString();
	}
	
	
	public LinkedHashMap<String,Value> getArgumentsAsNamesAndValues() {
		LinkedHashMap<String, Value> res = new LinkedHashMap<String, Value>();
		for(int i = 0; i < fOperation.paramNames().size();++i) {
			res.put(fOperation.paramNames().get(i), fArguments[i]);
			
		}
		return res;
	}
	
	public String toLegacyString() {
		StringBuilder result = new StringBuilder();
		result.append(fSelf.name());
		result.append(".");
		result.append(fOperation.name());
		result.append("(");
		StringUtil.fmtSeq(result, getArgumentsAsNamesAndValues().values(), ", ");
		result.append(")");
		return result.toString();
	}
	
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(fOperation.cls().name());
		result.append("::");
		result.append(fOperation.name());
		result.append("(");
		result.append("self:");
		result.append(fSelf.value());
		if (fArguments.length!=0) {
			result.append(", ");
		}
		List<String> argStrings = new ArrayList<String>(fArguments.length);
		for (Entry<String, Value> entry : getArgumentsAsNamesAndValues().entrySet()) {
			argStrings.add(entry.getKey() + ":" + entry.getValue());
		}
		StringUtil.fmtSeq(result, argStrings, ", ");
		result.append(")");
		
		return result.toString();
	}


    /**
     * Does this operation require a fresh variable frame? 
     * OCL query operations do not require a fresh frame, all other operations do.
     * @return
     */
    public boolean requiresVariableFrameInEnvironment() {
        return fOperation.expression() == null;
    }


	/**
	 * Stores possible transitions of an operation call.
	 * Used to be able to skip the calculation of the
	 * possible transitions after an operation call has been executed.
	 * @param psm
	 * @param possibleTransitions
	 */
	public void putPossibleTransitions(MProtocolStateMachineInstance psm,
			Map<MRegion, Set<MTransition>> possibleTransitions) {
		
		if (this.possibleTransitions == null) {
			this.possibleTransitions = new HashMap<MProtocolStateMachineInstance, Map<MRegion, Set<MTransition>>>();
		}
		
		this.possibleTransitions.put(psm, possibleTransitions);
	}
	
	/**
	 * Stores executed transitions of an operation call.
	 * Used to be able to revert the operation call.
	 * @param psm
	 * @param executedTrans
	 */
	public void putExecutedTransitions(MProtocolStateMachineInstance psm,
			Set<MTransition> executedTrans) {
		
		if (this.executedTransitions == null) {
			this.executedTransitions = new HashMap<MProtocolStateMachineInstance, Set<MTransition>>();
		}
		
		this.executedTransitions.put(psm, executedTrans);
	}
	
	/**
	 * Returns previously set transitions that could be taken
	 * when the operation is returning.
	 * @param psm
	 * @return
	 */
	public Map<MRegion, Set<MTransition>> getPossibleTransitions(MProtocolStateMachineInstance psm) {
		if (this.possibleTransitions == null) 
			return Collections.emptyMap();
		
		return this.possibleTransitions.get(psm);
	}
	
	public Map<MProtocolStateMachineInstance, Map<MRegion, Set<MTransition>>> getAllPossibleTransitions() {
		if (this.possibleTransitions == null) {
			return Collections.emptyMap();
		}
		
		return this.possibleTransitions;
	}
	
	/**
	 * returns <code>true</code> if the operation call could 
	 * execute a transition.
	 * @return
	 */
	public boolean hasPossibleTransitions() {
		return (this.possibleTransitions != null);
	}
	
	/**
	 * Returns previously set transitions that were taken
	 * after the operation executed.
	 * @param psm
	 * @return
	 */
	public Map<MProtocolStateMachineInstance, Set<MTransition>> getExecutedTransitions() {
		return this.executedTransitions;
	}

	/**
	 * <code>true</code> if the operation call executed any transition.
	 * @return
	 */
	public boolean hasExecutedTransitions() {
		return executedTransitions != null && executedTransitions.size() > 0;
	}

	/**
	 * @param t
	 */
	public void addExecutedTransition(MProtocolStateMachineInstance psm, MTransition t) {
		if (executedTransitions == null)
			executedTransitions = new HashMap<MProtocolStateMachineInstance, Set<MTransition>>();
		
		if (!executedTransitions.containsKey(psm)) {
			executedTransitions.put(psm, new HashSet<MTransition>());
		}
		
		executedTransitions.get(psm).add(t);
	}
}
