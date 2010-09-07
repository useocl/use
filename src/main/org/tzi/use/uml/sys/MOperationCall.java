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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.ppcHandling.DoNothingPPCHandler;
import org.tzi.use.uml.sys.ppcHandling.ExpressionPPCHandler;
import org.tzi.use.uml.sys.ppcHandling.OpEnterOpExitPPCHandler;
import org.tzi.use.uml.sys.ppcHandling.PPCHandler;
import org.tzi.use.uml.sys.ppcHandling.SoilPPCHandler;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.StringUtil;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class MOperationCall {
	/** TODO */
	private SrcPos fCallerSourcePos;
	/** TODO */
	private String fCallerString;
	/** TODO */
	private MObject fSelf;
	/** TODO */
	private MOperation fOperation;
	/** TODO */
	private Value[] fArguments;
	/** TODO */
	private MSystemState fPreState;
	/** TODO */
	private PPCHandler fPreferredPPCHandler;
	/** TODO */
	private LinkedHashMap<MPrePostCondition, Boolean> fPreConditionCheckResults;
	/** TODO */
	private LinkedHashMap<MPrePostCondition, Boolean> fPostConditionCheckResults;
	/** TODO */
	private boolean fEnteredSuccessfully = false;
	/** TODO */
	private boolean fExited = false;
	/** TODO */
	private boolean fExitedSuccessfully = false;
	/** TODO */
	private boolean fExecutionFailed = false;
	/** TODO */
	private Value fResultValue;
	
	/**
	 * The var bindings used by the operation call
	 */
	private VarBindings varBindings;
	
	/**
	 * TODO
	 * @param self
	 * @param operation
	 * @param arguments
	 */
	private MOperationCall(
			MObject self, 
			MOperation operation, 
			Value[] arguments) {
		
		fSelf = self;
		fOperation = operation;
		fArguments = arguments;		
	}
	
	
	/**
	 * TODO
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
		
		this(self, operation, arguments);
		fCallerSourcePos = caller.getSourcePosition();
		fCallerString = caller.toString();
	}
	
	
	/**
	 * TODO
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
		
		this(self, operation, arguments);
		fCallerSourcePos = 
			new SrcPos(caller.getSourceExpression().getStartToken());
		fCallerString = caller.toString();
	}
	
	/**
	 * TODO
	 * @return
	 */
	public MObject getSelf() {
		return fSelf;
	}
	
	
    public MSystemState getPreState() {
    	return fPreState;
    }
    
	/**
	 * TODO
	 * @return
	 */
	public MOperation getOperation() {
		return fOperation;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public Value[] getArguments() {
		return fArguments;
	}
	
	
	
	
	/**
	 * TODO
	 * @return
	 */
	public boolean executionHasFailed() {
		return fExecutionFailed;
	}
	
	
	/**
	 * TODO
	 * @param executionFailed
	 */
	public void setExecutionFailed(boolean executionFailed) {
		fExecutionFailed = executionFailed;
	}
	
	/**
	 * TODO
	 * @param preState
	 */
	public void setPreState(MSystemState preState) {
		fPreState = preState;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public Value getResultValue() {
		return fResultValue;
	}
	
	
	/**
	 * TODO
	 * @param resultValue
	 */
	public void setResultValue(Value resultValue) {
		fResultValue = resultValue;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public boolean enteredSuccessfully() {
		return fEnteredSuccessfully;
	}
	
	
	/**
	 * TODO
	 * @param enteredSuccessfully
	 */
	public void setEnteredSuccessfully(boolean enteredSuccessfully) {
		fEnteredSuccessfully = enteredSuccessfully;
	}
	
	
	/**
	 * TODO
	 * @param exitedSuccessfully
	 */
	public void setExitedSuccessfully(boolean exitedSuccessfully) {
		fExitedSuccessfully = exitedSuccessfully;
		fExited = true;
	}
	
	
	/**
	 * TODO
	 * @param exited
	 */
	public void setExited(boolean exited) {
		fExited = exited;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public boolean exitedSuccessfully() {
		return fExitedSuccessfully;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public boolean exited() {
		return fExited;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public boolean hasPreConditions() {
		return !fOperation.preConditions().isEmpty();
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public boolean hasPostConditions() {
		return !fOperation.postConditions().isEmpty();
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public LinkedHashMap<MPrePostCondition, Boolean> getPreConditionEvaluationResults() {
		return fPreConditionCheckResults;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public List<MPrePostCondition> getFailedPreConditions() {
		return getFailedPPCs(fPreConditionCheckResults);
	}
	
	


	/**
	 * TODO
	 * @param results
	 */
	public void setPreConditionsCheckResult(
			LinkedHashMap<MPrePostCondition, Boolean> results) {
		
		fPreConditionCheckResults = results;	
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public LinkedHashMap<MPrePostCondition, Boolean> getPostConditionEvaluationResults() {
		return fPostConditionCheckResults;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public List<MPrePostCondition> getFailedPostConditions() {
		return getFailedPPCs(fPostConditionCheckResults);
	}
	
	
	/**
	 * TODO
	 * @param results
	 */
	public void setPostConditionsCheckResult(
			LinkedHashMap<MPrePostCondition, Boolean> results) {
		
		fPostConditionCheckResults = results;	
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public PPCHandler getDefaultPPCHandler() {
		if (fOperation.hasExpression()) {
			return new ExpressionPPCHandler();
		} else if (fOperation.hasStatement()) {
			return new SoilPPCHandler();
		} else if (!fOperation.hasBody()) {
			return new OpEnterOpExitPPCHandler();
		} else {
			return DoNothingPPCHandler.getInstance();
		}
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public PPCHandler getPreferredPPCHandler() {
		return fPreferredPPCHandler;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public boolean hasPreferredPPCHandler() {
		return fPreferredPPCHandler != null;
	}
	
	
	/**
	 * TODO
	 * @param preferredPPCHandler
	 */
	public void setPreferredPPCHandler(PPCHandler preferredPPCHandler) {
		fPreferredPPCHandler = preferredPPCHandler;
	}
	
	
	/**
	 * TODO
	 * @param evaluationResults
	 * @return
	 */
	private List<MPrePostCondition> getFailedPPCs(
			Map<MPrePostCondition, Boolean> evaluationResults) {
		
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
	 * TODO
	 * @return
	 */
	public String getCallerString() {
		StringBuilder result = new StringBuilder();
		
		result.append("[caller: ");
		result.append(fCallerString == null ? "<unknown>" : fCallerString);
		result.append("@");
		if (fCallerSourcePos == null) {
			result.append("<unknown>");
		} else {
			result.append(fCallerSourcePos.srcName());
			result.append(":");
			result.append(fCallerSourcePos.line());
			result.append(":");
			result.append(fCallerSourcePos.column());
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
		StringUtil.addToStringBuilder(result, getArgumentsAsNamesAndValues().values());
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
		StringUtil.addToStringBuilder(result, argStrings);
		result.append(")");
		
		return result.toString();
	}
}
