/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
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

import static org.tzi.use.util.StringUtil.inQuotes;

import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.swing.event.EventListenerList;

import org.tzi.use.gen.tool.GGenerator;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.events.Event;
import org.tzi.use.uml.sys.ppcHandling.PPCHandler;
import org.tzi.use.uml.sys.ppcHandling.PostConditionCheckFailedException;
import org.tzi.use.uml.sys.ppcHandling.PreConditionCheckFailedException;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.uml.sys.soil.SoilEvaluationContext;
import org.tzi.use.util.Log;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.UniqueNameGenerator;
import org.tzi.use.util.soil.StateDifference;
import org.tzi.use.util.soil.VariableEnvironment;
import org.tzi.use.util.soil.exceptions.evaluation.EvaluationFailedException;

/**
 * A system maintains a system state and provides functionality for
 * doing state transitions.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public final class MSystem {
    private MModel fModel;  // The model of this system. 
    private MSystemState fCurrentState; // The current system state. 
    private Map<String, MObject> fObjects;   // The set of all objects
    private UniqueNameGenerator fUniqueNameGenerator; // creation of object names
    protected EventListenerList fListenerList = new EventListenerList();
    private MOperationCall lastOperationCall; // last called operation (used by test suite)
    private GGenerator fGenerator;    // snapshot generator
    /** the variables of this system */
    private VariableEnvironment fVariableEnvironment;
    /** TODO */
    private PPCHandler fPPCHandlerOverride;
    /** the stack of evaluation results of statements */
    private Deque<StatementEvaluationResult> fStatementEvaluationResults;
    /** the operation-call stack */
    private Deque<MOperationCall> fCallStack;
    /** TODO */
    private Deque<MStatement> fRedoStack;
    /** TODO */
    private int fStateLock = 0;
    /** TODO */
    private Deque<MStatement> fCurrentlyEvaluatedStatements;

    /**
     * True, if this system is used inside a test suite.
     * Some operations need this information, e.g., the pre state
     * has to be saved when there is no post condition  
     */
    private boolean isRunningTestSuite;
    
    private Stack<MSystemState> variationPointsStates = new Stack<MSystemState>();
    
    private Stack<VariableEnvironment> variationPointsVars = new Stack<VariableEnvironment>();
    
    /**
     * constructs a new MSystem
     * @param model the model of this system
     */
    public MSystem(MModel model) {
        fModel = model;
        init();
    }

    private void init() {
        fObjects = new HashMap<String, MObject>();
        fUniqueNameGenerator = new UniqueNameGenerator();
        fCurrentState = new MSystemState(fUniqueNameGenerator.generate("state#"), this);
        fGenerator = new GGenerator(this);
        fVariableEnvironment = new VariableEnvironment(fCurrentState);
        fStatementEvaluationResults = new ArrayDeque<StatementEvaluationResult>();
        fCallStack = new ArrayDeque<MOperationCall>();
        fRedoStack = new ArrayDeque<MStatement>();
        fCurrentlyEvaluatedStatements = new ArrayDeque<MStatement>();
    }

    /**
	 * Resets the system to its initial state.
	 */
	public void reset() {
	    init();
	}

	/**
     * Returns the current system state.
     */
    public MSystemState state() {
        return fCurrentState;
    }

    /**
     * Returns the system's model.
     */
    public MModel model() {
        return fModel;
    }

    /**
     * Returns the system's instance generator.
     */
    public GGenerator generator() {
        return fGenerator;
    }
    
    public VarBindings varBindings() {
		return fVariableEnvironment.constructVarBindings();
	}

	public VariableEnvironment getVariableEnvironment() {
    	return fVariableEnvironment;
    }
    
    
    public void addChangeListener(StateChangeListener l) {
        fListenerList.add(StateChangeListener.class, l);
    }
    
    public void removeChangeListener(StateChangeListener l) {
        fListenerList.remove(StateChangeListener.class, l);
    }
    
    public void registerPPCHandlerOverride(PPCHandler ppcHandlerOverride) {
    	fPPCHandlerOverride = ppcHandlerOverride;
    }
    
    /**
     * Creates and adds a new object to the system. The name of the
     * object may be null in which case a unique name is automatically
     * generated.
     *
     * @return the created object.  
     */
    MObject createObject(MClass cls, String name) throws MSystemException {
        if (cls.isAbstract() )
            throw new MSystemException("The abstract class `" + cls.name() + 
                                       "' cannot be instantiated.");
    
        // create new object and initial state
        if (name == null ) {
            name = uniqueObjectNameForClass(cls.name());
        } else if (fObjects.containsKey(name) )
            throw new MSystemException("An object with name `" + name + 
                                       "' already exists.");
        
        MObject obj = new MObjectImpl(cls, name);
        addObject(obj);
        return obj;
    }

    void addObject(MObject obj) {
        fObjects.put(obj.name(), obj);
    }

    void deleteObject(MObject obj) {
        fObjects.remove(obj.name());
    }
    
    /**
     * TODO
     * @param operationCall
     */
    private void evaluatePreConditions(EvalContext ctx, MOperationCall operationCall) {
    	
    	List<MPrePostCondition> preConditions = 
			operationCall.getOperation().preConditions();
    	
    	LinkedHashMap<MPrePostCondition, Boolean> results = 
    		new LinkedHashMap<MPrePostCondition, Boolean>(preConditions.size());
    	
    	for (MPrePostCondition preCondition : preConditions) {
			Evaluator oclEvaluator = new Evaluator();
			
			VarBindings b = (operationCall.requiresVariableFrameInEnvironment() ? fVariableEnvironment
					.constructVarBindings() : ctx.varBindings());
			
			Value evalResult = 
				oclEvaluator.eval(
						preCondition.expression(), 
						fCurrentState,
						b);
			
			boolean conditionPassed = 
				(evalResult.isDefined() && 
						evalResult.type().isBoolean() &&
						((BooleanValue)evalResult).isTrue());
			
			results.put(preCondition, conditionPassed);
    	}
    	
    	operationCall.setPreConditionsCheckResult(results);
    }
    
    
    /**
     * TODO
     * @param operationCall
     */
    private void evaluatePostConditions(EvalContext ctx, MOperationCall operationCall) {
    	
    	List<MPrePostCondition> postConditions = 
			operationCall.getOperation().postConditions();
    	
    	LinkedHashMap<MPrePostCondition, Boolean> results = 
    		new LinkedHashMap<MPrePostCondition, Boolean>(postConditions.size());
    	
		VarBindings b = (operationCall.requiresVariableFrameInEnvironment() ? fVariableEnvironment
				.constructVarBindings() : ctx.varBindings());
		
		if (!operationCall.requiresVariableFrameInEnvironment() && operationCall.getResultValue() != null) {
			b.push("result", operationCall.getResultValue());
		}
		
    	operationCall.setVarBindings(b);
    	boolean conditionPassed;
    	
    	for (MPrePostCondition postCondition : postConditions) {
			Evaluator oclEvaluator = new Evaluator();
			
			try {
				Value evalResult = 
					oclEvaluator.eval(
							postCondition.expression(), 
							operationCall.getPreState(),
							fCurrentState,
							operationCall.getVarBindings());
				
				conditionPassed = 
					(evalResult.isDefined() && 
							evalResult.type().isBoolean() &&
							((BooleanValue)evalResult).isTrue());
			} catch (MultiplicityViolationException e) {
				conditionPassed = false;
			}
			results.put(postCondition, conditionPassed);
    	}
    	
    	if (!operationCall.requiresVariableFrameInEnvironment() && operationCall.getResultValue() != null) {
			b.pop();
		}
    	
    	operationCall.setPostConditionsCheckResult(results);
    }
    
    
    /**
     * TODO
     * @return
     */
    public MOperationCall getCurrentOperation() {
    	return fCallStack.peek();
    }
    

    /**
	 * TODO
	 * @param self
	 * @param operation
	 * @param arguments
	 * @param ppcHandler
	 * @param output
	 * @throws MSystemException 
	 */
	public void enterOperation(EvalContext ctx, MOperationCall operationCall, boolean isOpenter) throws MSystemException {
						
		assertParametersValid(operationCall);
		
		if (operationCall.requiresVariableFrameInEnvironment()) {
			pushParametersOnVariableEnvironment(operationCall, isOpenter);
		
			if (getCurrentStatement() != null) {
				if (!stateIsLocked()) {
					getCurrentStatement().enteredOperationDuringEvaluation(operationCall);
				}
			}
		}
	
		fCallStack.push(operationCall);
		
		assertPreConditions(ctx, operationCall);
		
		copyPreStateIfNeccessary(operationCall);
		operationCall.setEnteredSuccessfully(true);
	}

	public MOperationCall exitOperation(EvalContext ctx, Value resultValue) throws MSystemException {
		
		MOperationCall operationCall = getCurrentOperation();
		if (operationCall == null) {
			throw new MSystemException("Call stack is empty.");
		}
		
		if (operationCall.executionHasFailed()) {
			exitCurrentOperation();
			return operationCall;
		}
		
		try {
			assertResultValueValid(resultValue, operationCall.getOperation());
			if (resultValue != null) operationCall.setResultValue(resultValue);	
		
			assertPostConditions(ctx, operationCall);
		
			operationCall.setExitedSuccessfully(true);
		
			return operationCall;
		} finally {
			exitCurrentOperation();
		}
	}

	
	/**
	 * Evaluate and check all preconditions of an operation call. 
	 * If any is not fulfilled, an exception is raised.
	 * Before the exception is raised, the PPC handler (if any) is invoked.
	 * @param operationCall
	 * @throws MSystemException
	 */
	private void assertPreConditions(EvalContext ctx, MOperationCall operationCall)
			throws MSystemException {
		evaluatePreConditions(ctx, operationCall);
		lockState();
		PPCHandler ppcHandler = determinePPCHandler(operationCall);
		try {
			ppcHandler.handlePreConditions(this, operationCall);
		} catch (PreConditionCheckFailedException e) {
			fCallStack.pop();
			if (operationCall.requiresVariableFrameInEnvironment()) {
				fVariableEnvironment.popFrame();
			}
			throw new MSystemException(e.getMessage(), e);
		} finally {
			unlockState();
		}
	}

	private PPCHandler determinePPCHandler(MOperationCall operationCall) {
		// make sure we have a ppc handler
		PPCHandler ppcHandler;
		if (fPPCHandlerOverride != null) {
			ppcHandler = fPPCHandlerOverride;
		} else if (operationCall.hasPreferredPPCHandler()) {
			ppcHandler = operationCall.getPreferredPPCHandler();
		} else {
			ppcHandler = operationCall.getDefaultPPCHandler();
		}
		return ppcHandler;
	}

	/**
	 * Push a new frame on the variable environment and assign all 
	 * parameters of this operation call, including "self".
	 */
	private void pushParametersOnVariableEnvironment(
			MOperationCall operationCall, boolean isOpenter) {
		// set up variable environment
		fVariableEnvironment.pushFrame(isOpenter);
		fVariableEnvironment.assign("self", operationCall.getSelf().value());
		for (int i = 0; i < operationCall.getOperation().paramList().size();++i) {
			fVariableEnvironment.assign(operationCall.getOperation().paramList().varDecl(i).name(), operationCall.getArguments()[i]);
		}
	}

	/**
	 * Creates a copy of the current system state, if there are any postconditions for this operation or
	 * if currently a test suite is executed.
	 * @param operationCall
	 */
	private void copyPreStateIfNeccessary(MOperationCall operationCall) {
		// if the post conditions of this operations require a pre state 
		// require a state copy, create it
		if (isRunningTestSuite 
			|| operationCall.hasPostConditions()
			&& operationCall.getOperation().postConditionsRequirePreState()) {
			
			operationCall.setPreState(
					new MSystemState(
							fUniqueNameGenerator.generate("state#"), 
							fCurrentState));
		} else {
			operationCall.setPreState(fCurrentState);
		}
	}

	
	/**
	 * Assert that the actual parameter values in this opcall are consistent with the operation.
	 * @param operationCall
	 * @throws MSystemException
	 */
	private void assertParametersValid(MOperationCall operationCall)
			throws MSystemException {
		if (operationCall.getArguments().length != operationCall.getOperation().paramList().size()) {
			throw new MSystemException(
					"Number of arguments does not match declaration of " +
					" operation " +
					StringUtil.inQuotes(operationCall.getOperation().name()) +
					" in class " +
					StringUtil.inQuotes(operationCall.getSelf().cls().name()) +
					". Expected " +
					operationCall.getOperation().paramList().size() +
					" arguments" + ((operationCall.getArguments().length == 1) ? "" : "s") +
					", found " +
					operationCall.getArguments().length +
					".");
		}
		
		for (int i = 0; i < operationCall.getOperation().paramList().size(); ++i) {
			
			VarDecl parameter = operationCall.getOperation().paramList().varDecl(i);
			Value argument = operationCall.getArguments()[i];
			
			Type expectedType = parameter.type();
			Type foundType = argument.type();
			
			if (!foundType.isSubtypeOf(expectedType)) {
	
				throw new MSystemException(
						"Type mismatch in argument " +
						i +
						". Expected type " +
						StringUtil.inQuotes(expectedType) +
						", found " +
						StringUtil.inQuotes(foundType) +
						".");
			}				
		}
	}


	/** 
	 * Assert that the result value of this operation call is consistent with the operation.
	 * @param resultValue
	 * @param forceExit
	 * @param operation
	 * @throws MSystemException
	 */
	private void assertResultValueValid(Value resultValue, MOperation operation) throws MSystemException {
		if (operation.hasResultType()) {
			if (resultValue == null) {
				throw new MSystemException(
	            		"Result value of type " +
	            		inQuotes(operation.resultType()) +
	            		" required on exit of operation " +
	            		inQuotes(operation) +
	            		"." );
				
			// result value has incompatible type
	        } else if (!resultValue.type().isSubtypeOf(operation.resultType())) {
	        	throw new MSystemException(
	        			"Result value type " +
	        			inQuotes(resultValue.type()) +
	        			" does not match operation result type " +
	        			inQuotes(operation.resultType()) +
	        			"." );
	        // result value is of correct type
	        } 
		// operation has no return value
	    } else {
	    	// redundant result value, just give a warning
	    	if (resultValue != null) {
	    		Log.out().println(
	    				"Warning: Result value " + 
	    				inQuotes(resultValue) + 
	    				" is ignored, since operation " + 
	    				inQuotes(operation) +
	    				" is not defined to return a value.");
	    	}
	    }
	}

	/**
	 * Evaluate and check all postconditions of an operation call. 
	 * If any is not fulfilled, an exception is raised.
	 * Before the exception is raised, the PPC handler (if any) is invoked.
	 * @param operationCall
	 * @throws MSystemException
	 */
	private void assertPostConditions(EvalContext ctx, MOperationCall operationCall)
			throws MSystemException {
		evaluatePostConditions(ctx, operationCall);
		PPCHandler ppcHandler = determinePPCHandler(operationCall);
		try {
			ppcHandler.handlePostConditions(this, operationCall);
		} catch (PostConditionCheckFailedException e) {
			throw(new MSystemException(e.getMessage()));
		}
	}
	
	
	/**
	 * TODO
	 */
	private void exitCurrentOperation() {
		MOperationCall currentOperation = getCurrentOperation();
		if (currentOperation == null) throw new RuntimeException("Cannot exit without a current operation!");
		currentOperation.setExited(true);
		fCallStack.pop();
		MStatement currentStatement = getCurrentStatement();
		if (currentOperation.requiresVariableFrameInEnvironment()) {
			if (currentStatement != null && !stateIsLocked()) {
				currentStatement.exitedOperationDuringEvaluation(
						currentOperation);
			}
			fVariableEnvironment.popFrame();
		}
	}
	
	
	/**
     * Returns a unique name that can be used for a new object of the
     * given class.  
     */
    public String uniqueObjectNameForClass(String clsName) {
        return fUniqueNameGenerator.generate(clsName);
    }
    
    
    /**
     * TODO
     * @param statement
     * @param undoOnFailure
     * @param storeResult
     * @return
     * @throws MSystemException
     */
    private StatementEvaluationResult evaluate(
			MStatement statement,
			boolean undoOnFailure,
			boolean storeResult,
			boolean notifyUpdateStateListeners) throws MSystemException {
    	
    	return evaluate(
    			statement, 
    			new SoilEvaluationContext(this),
    			undoOnFailure, 
    			storeResult,
    			notifyUpdateStateListeners);
    }
    
   
    /**
	 * TODO
	 * @param statement
	 * @throws EvaluationFailedException
	 */
	private StatementEvaluationResult evaluate(
			MStatement statement,
			SoilEvaluationContext context,
			boolean undoOnFailure,
			boolean storeResult,
			boolean notifyUpdateStateListeners) throws MSystemException {
		
		if (stateIsLocked()) {
			throw new MSystemException(
					"The system currently cannot be modified.");
		}
		
		fCurrentlyEvaluatedStatements.push(statement);
		
		if (context.isUndo()) {
			fUniqueNameGenerator.popState();
		} else {
			fUniqueNameGenerator.pushState();
		}
		
		StatementEvaluationResult result = statement.evaluate(context);
				
		fCurrentlyEvaluatedStatements.pop();
		
		if (storeResult) {
			fStatementEvaluationResults.push(result);
		}
		
		if (result.wasSuccessfull() && notifyUpdateStateListeners)
			fireStateChanged(result.getStateDifference());
		
		if (!result.wasSuccessfull()) {
			if (undoOnFailure) {
				if (storeResult) fStatementEvaluationResults.pop();
				evaluate(result.getInverseStatement(), false, false, notifyUpdateStateListeners);
			}
			
			throw new MSystemException(result.getException().getMessage(), result.getException());
		}
		
		return result;
	}

	/**
	 * TODO
	 * @param differences
	 */
	private void fireStateChanged(StateDifference differences) {
		Object[] listeners = fListenerList.getListenerList();
		StateChangeEvent sce = null;
		
		for (int i = listeners.length-2; i >= 0; i -= 2) {
	        if (listeners[i] == StateChangeListener.class) {
	        	try {
	        		if (sce == null) {
	        			sce = new StateChangeEvent(this);
	        			differences.fillStateChangeEvent(sce);
	        		}
	        		
	        		((StateChangeListener)listeners[i+1]).stateChanged(sce);
	        	} catch (Exception ex) { }
	        }          
	    }
		
		differences.clear();
	}

	/**
     * TODO
     * @param statement
     * @throws EvaluationFailedException
     */
    public StatementEvaluationResult evaluateStatement(
    		MStatement statement) throws MSystemException {
    	    	   	
    	return evaluateStatement(statement, true, true, true);
    }
    
    /**
     * TODO
     * @param statement
     * @throws EvaluationFailedException
     */
    public StatementEvaluationResult evaluateStatement(
    		MStatement statement, boolean notifyUpdateStateListeners) throws MSystemException {
    	    	   	
    	return evaluateStatement(statement, true, true, notifyUpdateStateListeners);
    }
    
    public StatementEvaluationResult evaluateStatement(
    		MStatement statement,
    		boolean undoOnFailure,
    		boolean storeResult) throws MSystemException {
    	return evaluateStatement(statement, undoOnFailure, storeResult, true);
    }
    
    /**
     * TODO
     * @param statement
     * @param undoOnFailure
     * @param storeResult
     * @return
     * @throws EvaluationFailedException
     */
    public StatementEvaluationResult evaluateStatement(
    		MStatement statement,
    		boolean undoOnFailure,
    		boolean storeResult,
    		boolean notifyUpdateStateListeners) throws MSystemException {
    	
    	fRedoStack.clear();
    	
    	StatementEvaluationResult result = 
    		evaluate(statement, undoOnFailure, storeResult, notifyUpdateStateListeners);
    	
    	return result;
    }
    
    
    public Value evaluateStatementInExpression(
    		MStatement statement) throws MSystemException {
    	
    	MStatement currentStatement = getCurrentStatement();
    	
    	if (currentStatement == null) {
    		evaluate(statement, false, false, false);
    	} else {
    		try {
    			currentStatement.evaluateSubStatement(statement);
    		} catch (EvaluationFailedException e) {
    			throw new MSystemException(e.getMessage(), e);
    		}
    	}
    	
		return fVariableEnvironment.lookUp("result");
    }
    
    
    /**
     * TODO
     * @return
     * @throws MSystemException
     * @throws EvaluationFailedException
     */
    public StatementEvaluationResult undoLastStatement() 
    throws MSystemException {
    	
    	if (fStatementEvaluationResults.isEmpty()) {
    		throw new MSystemException("nothing to undo");
    	}
    	
    	StatementEvaluationResult lastResult = 
    		fStatementEvaluationResults.pop();
    	
    	MStatement lastStatement = lastResult.getEvaluatedStatement();
    	MStatement inverseStatement = lastResult.getInverseStatement();
    	
    	fRedoStack.push(lastStatement);
    	
    	if (Log.isTracing())
    		Log.trace(this, "undoing a statement");
    	
    	SoilEvaluationContext context = new SoilEvaluationContext(this);
    	context.setIsUndo(true);
    	
    	return evaluate(inverseStatement, context, false, false, true);
    }
    
    
    /**
	 * TODO
	 * @throws MSystemException
	 * @throws EvaluationFailedException
	 */
	public StatementEvaluationResult redoStatement() throws MSystemException {
		
		if (fRedoStack.isEmpty()) {
			throw new MSystemException("nothing to redo");
		}
		
		MStatement redoStatement = fRedoStack.pop();
		
		if (Log.isTracing())
			Log.trace(this, "redoing a statement");
		
		SoilEvaluationContext context = new SoilEvaluationContext(this);
		context.setIsRedo(true);
		
		StatementEvaluationResult result = 
			evaluate(
					redoStatement, 
					context, 
					false, 
					true,
					true);
		
		return result;
	}

//        lastOperationCall = opcall;
	/**
     * TODO
     * @return
     */
    public String getUndoDescription() {
    	if (fStatementEvaluationResults.isEmpty()) {
    		return null;
    	} else {
    		StatementEvaluationResult lastResult = 
    			fStatementEvaluationResults.peek();
    		
    		MStatement lastEvaluatedStatement = 
    			lastResult.getEvaluatedStatement();
    		
    		return lastEvaluatedStatement.getShellCommand();
    	}
    }
    
    
    public MStatement nextToRedo() {
    	return fRedoStack.peek();
    }
    
    public void setLastOperationCall(MOperationCall lastCall) {
    	this.lastOperationCall = lastCall;
    }
    
    public MOperationCall getLastOperationCall() {
    	return lastOperationCall;
    }
    
    public boolean isRunningTestSuite() {
		return isRunningTestSuite;
	}

	public void setRunningTestSuite(boolean isRunningTestSuite) {
		this.isRunningTestSuite = isRunningTestSuite;
	}

	/**
     * TODO
     * @return
     */
    public String getRedoDescription() {
    	
    	if (fRedoStack.isEmpty()) {
    		return null;
    	}
    	
    	return fRedoStack.peek().getShellCommand();
    }

	
	/**
     * TODO
     * @param out
     */
    public void writeSoilStatements(PrintWriter out) {
    	for (MStatement statement : getEvaluatedStatements()) {
    		out.println(statement.getShellCommand());
    	}
    }

    /**
     * TODO
     * @return
     */
    public int numEvaluatedStatements() {
    	return fStatementEvaluationResults.size();
    }
    
    /**
     * TODO
     * @return
     */
    public List<MStatement> getEvaluatedStatements() {
    	List<MStatement> evaluatedStatements = 
    		new ArrayList<MStatement>(fStatementEvaluationResults.size());
    	
    	for (StatementEvaluationResult result : fStatementEvaluationResults) {
    		evaluatedStatements.add(0, result.getEvaluatedStatement());
    	}
    	
    	return evaluatedStatements;
    }
    
    
    /**
     * TODO
     * @return
     */
    private MStatement getCurrentStatement() {
    	return fCurrentlyEvaluatedStatements.peek();
    }
    
    
    /**
	 * TODO
	 * @return
	 */
	public Deque<MOperationCall> getCallStack() {
		return fCallStack;
	}
	
	
	/**
	 * TODO
	 * @param object
	 * @return
	 */
	public boolean hasActiveOperation(MObject object) {
		
		for (MOperationCall operationCAll : fCallStack) {
			if (operationCAll.getSelf() == object) {
				return true;
			}
		}
		
		return false;
	}
	

	/**
     * TODO
     * @return
     */
    public List<Event> getAllEvents() {
    	List<Event> result = new ArrayList<Event>();
    	
    	Iterator<StatementEvaluationResult> it = 
    		fStatementEvaluationResults.descendingIterator();
    	while (it.hasNext()) {
    		result.addAll(it.next().getEvents());
    	}
    	
    	MStatement currentStatement = fCurrentlyEvaluatedStatements.peek();
    	if (currentStatement != null) {
    		result.addAll(currentStatement.getResult().getEvents());
    	}
    	
    	return result;
    }
    
    private void lockState() {
    	++fStateLock;
    }
    
    private void unlockState() {
    	--fStateLock;
    }
    
    private boolean stateIsLocked() {
    	return fStateLock > 0;
    }
    
    public void updateListeners() {
    	MStatement currentStatement = fCurrentlyEvaluatedStatements.peek();
    	
    	if (currentStatement != null) {
    		fireStateChanged(
					currentStatement.getResult().getStateDifference());
		}
    }
    
    /**
     * Starts a new variation in a test case
     */
    public void beginVariation() {
		
    	// Store current system state on stack
		variationPointsStates.push(this.fCurrentState);
		variationPointsVars.push(this.fVariableEnvironment);
		
		this.fCurrentState = new MSystemState(new UniqueNameGenerator().generate("variation#"), this.fCurrentState);
		this.fVariableEnvironment = new VariableEnvironment(this.fVariableEnvironment, this.fCurrentState);
	}
	
    /**
     * Ends a variation in a test case
     */
	public void endVariation() throws MSystemException {
		if (variationPointsStates.isEmpty()) {
			throw new MSystemException("No Variation to end!");
		}
		
		this.fCurrentState = variationPointsStates.pop();
		this.fVariableEnvironment = variationPointsVars.pop();
	}
}
