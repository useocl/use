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

package org.tzi.use.uml.sys;

import static org.tzi.use.util.StringUtil.inQuotes;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.tzi.use.config.Options;
import org.tzi.use.gen.tool.GGenerator;
import org.tzi.use.parser.generator.ASSLCompiler;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.mm.statemachines.MRegion;
import org.tzi.use.uml.mm.statemachines.MStateMachine;
import org.tzi.use.uml.mm.statemachines.MTransition;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MSystemState.DeleteObjectResult;
import org.tzi.use.uml.sys.events.AttributeAssignedEvent;
import org.tzi.use.uml.sys.events.ClassInvariantChangedEvent;
import org.tzi.use.uml.sys.events.ClassInvariantChangedEvent.InvariantStateChange;
import org.tzi.use.uml.sys.events.ClassInvariantsLoadedEvent;
import org.tzi.use.uml.sys.events.ClassInvariantsUnloadedEvent;
import org.tzi.use.uml.sys.events.Event;
import org.tzi.use.uml.sys.events.LinkDeletedEvent;
import org.tzi.use.uml.sys.events.LinkInsertedEvent;
import org.tzi.use.uml.sys.events.ObjectCreatedEvent;
import org.tzi.use.uml.sys.events.ObjectDestroyedEvent;
import org.tzi.use.uml.sys.events.OperationEnteredEvent;
import org.tzi.use.uml.sys.events.OperationExitedEvent;
import org.tzi.use.uml.sys.events.StatementExecutedEvent;
import org.tzi.use.uml.sys.events.TransitionEvent;
import org.tzi.use.uml.sys.events.tags.EventContext;
import org.tzi.use.uml.sys.ppcHandling.PPCHandler;
import org.tzi.use.uml.sys.ppcHandling.PostConditionCheckFailedException;
import org.tzi.use.uml.sys.ppcHandling.PreConditionCheckFailedException;
import org.tzi.use.uml.sys.soil.MAttributeAssignmentStatement;
import org.tzi.use.uml.sys.soil.MLinkDeletionStatement;
import org.tzi.use.uml.sys.soil.MLinkInsertionStatement;
import org.tzi.use.uml.sys.soil.MObjectDestructionStatement;
import org.tzi.use.uml.sys.soil.MObjectRestorationStatement;
import org.tzi.use.uml.sys.soil.MRValue;
import org.tzi.use.uml.sys.soil.MRValueExpression;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.uml.sys.soil.MVariableAssignmentStatement;
import org.tzi.use.uml.sys.soil.MVariableDestructionStatement;
import org.tzi.use.uml.sys.soil.SoilEvaluationContext;
import org.tzi.use.uml.sys.statemachines.MProtocolStateMachineInstance;
import org.tzi.use.uml.sys.statemachines.MProtocolStateMachineInstance.TransitionResult;
import org.tzi.use.util.Log;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.UniqueNameGenerator;
import org.tzi.use.util.soil.VariableEnvironment;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;

import com.google.common.eventbus.EventBus;

/**
 * A system maintains a system state and provides functionality for doing state
 * transitions.
 * 
 * @author Fabian Buettner
 * @author Lars Hamann
 * @author Frank Hilken
 * @author Mark Richters
 */
public final class MSystem {
	/** The model of this system. */
	private MModel fModel;

	/** The current system state. */
	private MSystemState fCurrentState;

	/** The set of all objects */
	private Map<String, MObject> fObjects;

	/** Handles creation of numbered object names considering undo */
	private UniqueNameGenerator fUniqueNameGenerator;

	/** Event bus for detailed events during execution **/
	private EventBus eventBus = new EventBus("System change");

	/** Last called operation (used by test suite) */
	private MOperationCall lastOperationCall;

	/** Snapshot generator */
	private GGenerator fGenerator;

	/** The variables of this system */
	private VariableEnvironment fVariableEnvironment;

	/** Current override of pre- and postcondition handlers */
	private PPCHandler fPPCHandlerOverride;

	/** The stack of evaluation results of statements */
	private Deque<StatementEvaluationResult> fStatementEvaluationResults;

	/** The operation-call stack */
	private Deque<MOperationCall> fCallStack;

	/** Stack which is filled after an undo */
	private Deque<MStatement> fRedoStack;

	/**
	 * SOIL call stack with information about result of a statement and how to
	 * revert it.
	 */
	private Deque<StatementEvaluationResult> fCurrentlyEvaluatedStatements;

	/**
	 * True, if this system is used inside a test suite. Some operations need
	 * this information, e.g., the pre state has to be saved when there is no
	 * post condition
	 */
	private boolean isRunningTestSuite;
	/**
	 * Stack of variation points (system state backups) for the test suite.
	 */
	private Stack<MSystemState> variationPointsStates = new Stack<MSystemState>();
	/**
	 * Stack of variation points (variable backups) for the test suite.
	 */
	private Stack<VariableEnvironment> variationPointsVars = new Stack<VariableEnvironment>();

	/**
	 * Count of open GUI elements that require updates on changes with derived
	 * elements.
	 */
	private int deriveUpdatableElementCount = 0;

	/**
	 * The current state of execution, to be able
	 * to identify undo operations. 
	 */
	private EventContext executionContext = EventContext.NORMAL_EXECUTION;

	/**
	 * constructs a new MSystem
	 * 
	 * @param model the model of this system
	 */
	public MSystem(MModel model) {
		fModel = model;
		init();
	}

	/**
	 * Initializes a system (used for new system instances and for
	 * {@link #reset()})
	 */
	private void init() {
		fObjects = new HashMap<String, MObject>();
		fUniqueNameGenerator = new UniqueNameGenerator();
		fCurrentState = new MSystemState(fUniqueNameGenerator.generate("state#"), this);
		fGenerator = new GGenerator(this);
		fVariableEnvironment = new VariableEnvironment(fCurrentState);
		fStatementEvaluationResults = new ArrayDeque<StatementEvaluationResult>();
		fCallStack = new ArrayDeque<MOperationCall>();
		fRedoStack = new ArrayDeque<MStatement>();
		fCurrentlyEvaluatedStatements = new ArrayDeque<StatementEvaluationResult>();
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

	/**
	 * @return
	 */
	public boolean isExecutingStatement() {
		return !this.fCurrentlyEvaluatedStatements.isEmpty();
	}

	/**
	 * The event bus of the system. 
	 * It is used to provide fine grained event 
	 * notifications during execution.
	 * 
	 * All raised events are in the
	 * package {@link org.tzi.use.uml.sys.events}.
	 * 
	 * The documentation of the used event bus can 
	 * be found <a href="http://code.google.com/p/guava-libraries/wiki/EventBusExplained">here</a>.   
	 * @return
	 */
	public EventBus getEventBus() {
		return this.eventBus;
	}

	/**
	 * The current execution context, e. g., UNDO
	 * @return the executionContext
	 */
	public EventContext getExecutionContext() {
		return executionContext;
	}
	
	public void registerPPCHandlerOverride(PPCHandler ppcHandlerOverride) {
		fPPCHandlerOverride = ppcHandlerOverride;
	}

	/**
	 * Registers a new element, that needs to be updated on changes of derived
	 * elements. If this is the first element, the derived elements of the
	 * system are recalculated.
	 */
	public synchronized void registerRequiresAllDerivedValues() {
		deriveUpdatableElementCount++;
		if (deriveUpdatableElementCount == 1) {
			fCurrentState.updateDerivedValues();
		}
	}

	/**
	 * Unregisters an element, that needs to be updated on changed of derived
	 * elements.
	 */
	public synchronized void unregisterRequiresAllDerivedValues() {
		deriveUpdatableElementCount--;
	}

	/**
	 * @return true, if elements exists, that need to be updated, false
	 *         otherwise
	 */
	public synchronized boolean isImmediatlyCalculateDerivedValues() {
		return deriveUpdatableElementCount > 0;
	}

	public UniqueNameGenerator getUniqueNameGenerator() {
		return this.fUniqueNameGenerator;
	}

	/**
	 * Creates and adds a new object to the system. The name of the object may
	 * be null in which case a unique name is automatically generated.
	 * 
	 * @return the created object.
	 */
	MObject createObject(MClass cls, String name) throws MSystemException {
		if (cls.isAbstract())
			throw new MSystemException("The abstract class `" + cls.name() + "' cannot be instantiated.");

		// create new object and initial state
		if (name == null) {
			name = uniqueObjectNameForClass(cls.name());
		} else if (fObjects.containsKey(name))
			throw new MSystemException("An object with name `" + name + "' already exists.");

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

	public void loadInvariants(InputStream in, String inputName, boolean doEcho, PrintWriter out) {
		Collection<MClassInvariant> invs = ASSLCompiler
				.compileInvariants(fModel, in, inputName,
						out);

		if(invs != null){
			for(Iterator<MClassInvariant> it = invs.iterator(); it.hasNext();){
				MClassInvariant inv = it.next();
				try {
					fModel.addClassInvariant(inv);
				} catch (MInvalidModelException e) {
					it.remove();
					out.println(e.getMessage());
				}
			}
			
			if(invs.size() > 0){
				fireClassInvariantsLoadedEvent(invs);
			}
			
			if (doEcho) {
				out.println("Added invariants:");
				
				if (invs.isEmpty()) {
					out.println("(none)");
				} else {
					for (MClassInvariant inv : invs) {
						out.println(inv.toString());
					}
				}
			}
		}
	}
	
	public void unloadInvariants(Set<String> invNames, PrintWriter out) {
		Collection<MClassInvariant> invs = new LinkedList<MClassInvariant>();
		for(String name : invNames){
			MClassInvariant inv = fModel.removeClassInvariant(name);
			if(inv == null){
				out.println("Invariant " + StringUtil.inQuotes(name)
						+ " does not exist or is an invariant of the original model. Ignoring.");
			} else {
				invs.add(inv);
			}
		}

		if(invs.size() > 0){
			fireClassInvariantsUnloadedEvent(invs);
		}
	}
	
	/**
	 * @see #setClassInvariantFlags(MClassInvariant, Boolean, Boolean)
	 * @param disabled May be {@code null} for no change.
	 * @param negated May be {@code null} for no change.
	 */
	public void setClassInvariantFlags(Collection<MClassInvariant> invs, Boolean disabled, Boolean negated) {
		for (MClassInvariant inv : invs) {
			setClassInvariantFlags(inv, disabled, negated);
		}
	}

	/**
	 * @param inv invariant to set flags
	 * @param active State if invariant is active. May be {@code null} for no change.
	 * @param negated State if invariant is negated. May be {@code null} for no change.
	 */
	public void setClassInvariantFlags(MClassInvariant inv, Boolean active, Boolean negated) {
		if (active != null){
			inv.setActive(active.booleanValue());
			fireClassInvariantChangeEvent(inv, active.booleanValue() ? InvariantStateChange.ACTIVATED : InvariantStateChange.DEACTIVATED);
		}
		if (negated != null){
			inv.setNegated(negated.booleanValue());
			fireClassInvariantChangeEvent(inv, negated.booleanValue() ? InvariantStateChange.NEGATED : InvariantStateChange.DENEGATED);
		}
	}
	
	/**
	 * Evaluates all pre conditions of a called operation.
	 * 
	 * @param operationCall The operation call to check the pre conditions for.
	 */
	private void evaluatePreConditions(EvalContext ctx, MOperationCall operationCall) {

		List<MPrePostCondition> preConditions = operationCall.getOperation().preConditions();

		if (preConditions.isEmpty()) {
			// Performance improvement...
			operationCall.setPreConditionsCheckResult(Collections.<MPrePostCondition, Boolean> emptyMap());
			return;
		}
		
		Map<MPrePostCondition, Boolean> results = new LinkedHashMap<MPrePostCondition, Boolean>(preConditions.size());
		operationCall.setPreConditionsCheckResult(results);

		for (MPrePostCondition preCondition : preConditions) {
			Evaluator oclEvaluator = new Evaluator();

			VarBindings b = (operationCall.requiresVariableFrameInEnvironment() ? fVariableEnvironment.constructVarBindings() : ctx.varBindings());

			Value evalResult = oclEvaluator.eval(preCondition.expression(), fCurrentState, b);

			boolean conditionPassed = (evalResult.isDefined()
					&& evalResult.type().isTypeOfBoolean() && ((BooleanValue) evalResult)
					.isTrue());

			results.put(preCondition, conditionPassed);
		}
	}

	/**
	 * Evaluates all post conditions of the given operation call and stores
	 * their result inside of the operation call object.
	 * 
	 * @param operationCall The operation call to validate the post conditions
	 *            for. Also used to store the post condition results.
	 */
	private void evaluatePostConditions(EvalContext ctx, MOperationCall operationCall) {

		List<MPrePostCondition> postConditions = operationCall.getOperation().postConditions();

		if (postConditions.isEmpty()) {
			// Performance improvement...
			operationCall.setPostConditionsCheckResult(Collections.<MPrePostCondition, Boolean> emptyMap());
			return;
		}
		
		LinkedHashMap<MPrePostCondition, Boolean> results = new LinkedHashMap<MPrePostCondition, Boolean>(postConditions.size());

		VarBindings b = (operationCall.requiresVariableFrameInEnvironment() ? fVariableEnvironment.constructVarBindings() : ctx.varBindings());

		if (!operationCall.requiresVariableFrameInEnvironment() && operationCall.getResultValue() != null) {
			b.push("result", operationCall.getResultValue());
		}

		operationCall.setVarBindings(b);
		boolean conditionPassed;

		for (MPrePostCondition postCondition : postConditions) {
			Evaluator oclEvaluator = new Evaluator();

			try {
				Value evalResult = oclEvaluator.eval(postCondition.expression(), operationCall.getPreState(), fCurrentState, operationCall.getVarBindings());

				conditionPassed = (evalResult.isDefined()
						&& evalResult.type().isTypeOfBoolean() && ((BooleanValue) evalResult)
						.isTrue());
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
	 * Returns the current operation call, if any.
	 * 
	 * @return The current operation call oder <code>null</code> if no operation
	 *         is called.
	 */
	public MOperationCall getCurrentOperation() {
		return fCallStack.peek();
	}

	/**
	 * Enters a query operation. The following checks a performed:
	 * <ul>
	 * <li>Parameter validity</li>
	 * <li>Validity of pre conditions</li>
	 * </ul>
	 * The operation call is pushed onto the call stack and the current state is
	 * copied if required.
	 * 
	 * @param ctx
	 * @param operationCall
	 * @param isOpenter
	 * @throws MSystemException
	 */
	public void enterQueryOperation(EvalContext ctx, MOperationCall operationCall, boolean isOpenter) throws MSystemException {

		assertParametersValid(operationCall);

		fCallStack.push(operationCall);

		assertPreConditions(ctx, operationCall);

		operationCall.setEnteredSuccessfully(true);
	}

	/**
	 * Enters a non query operation. The following checks are done:
	 * <ul>
	 * <li>Validity of parameters</li>
	 * <li>Preconditions</li>
	 * <li>Transitions</li>
	 * </ul>
	 * .
	 * 
	 * @param context
	 * @param result
	 * @param operationCall
	 * @param isOpenter
	 * @throws MSystemException
	 */
	public void enterNonQueryOperation(SoilEvaluationContext context, StatementEvaluationResult result, MOperationCall operationCall, boolean isOpenter)
			throws MSystemException {

		assertParametersValid(operationCall);

		// set up variable environment
		fVariableEnvironment.pushFrame(isOpenter);
		fVariableEnvironment.assign("self", operationCall.getSelf().value());
		for (int i = 0; i < operationCall.getOperation().paramList().size(); ++i) {
			fVariableEnvironment.assign(operationCall.getOperation().paramList().varDecl(i).name(), operationCall.getArguments()[i]);
		}

		if (getCurrentStatement() != null) {
			result.appendEvent(fireOperationEntered(operationCall));
		}

		// Push call stack in case of error to allow
		// it to be shown as invalid in diagrams.
		fCallStack.push(operationCall);

		EvalContext ctx = new EvalContext(fCurrentState, fCurrentState, fVariableEnvironment.constructVarBindings(), null, "");

		try {
			assertPreConditions(ctx, operationCall);

			if (Options.getCheckTransitions())
				calculatePossibleTransition(ctx, operationCall);
		} catch (MSystemException e) {
			fCallStack.pop();
			if (operationCall.requiresVariableFrameInEnvironment()) {
				fVariableEnvironment.popFrame();
			}
			throw e;
		}

		copyPreStateIfNeccessary(operationCall);
		operationCall.setEnteredSuccessfully(true);
	}

	/**
	 * Exits a query operation, i. e., a query without side effects. Validates
	 * the post conditions of the query expression.
	 * 
	 * In any case, the operation is removed from the call stack.
	 * 
	 * @param ctx
	 * @param resultValue
	 * @return
	 * @throws MSystemException
	 */
	public MOperationCall exitQueryOperation(EvalContext ctx, Value resultValue) throws MSystemException {

		MOperationCall operationCall = getCurrentOperation();

		if (operationCall == null) {
			throw new MSystemException("Call stack is empty.");
		}

		if (operationCall.executionHasFailed()) {
			operationCall.setExited(true);
			fCallStack.pop();
			return operationCall;
		}

		try {
			if (!operationCall.getOperation().postConditions().isEmpty())
				assertPostConditions(ctx, operationCall);

			operationCall.setExitedSuccessfully(true);

			return operationCall;
		} finally {
			operationCall.setExited(true);
			fCallStack.pop();
		}
	}

	/**
	 * Calculates the possible transitions for all state machines of the
	 * receiving instance.
	 * 
	 * @param ctx The current evaluation context.
	 * @param operationCall The operation call to calculate the possible
	 *            transitions for.
	 * @throws MSystemException If no valid transition is found for a state
	 *             machine.
	 */
	private void calculatePossibleTransition(EvalContext ctx, MOperationCall operationCall) throws MSystemException {

		if (ctx == null) {
			VarBindings b = fVariableEnvironment.constructVarBindings();
			ctx = new EvalContext(null, fCurrentState, b, null, "");
		}

		MObjectState objState = operationCall.getSelf().state(fCurrentState);

		for (MProtocolStateMachineInstance psm : objState.getProtocolStateMachinesInstances()) {
			// Operation is not covered by the state machine
			if (!psm.getProtocolStateMachine().handlesOperation(operationCall.getOperation()))
				continue;
			
			Map<MRegion, Set<MTransition>> possibleTransitions = new LinkedHashMap<MRegion, Set<MTransition>>();
			boolean validOperationCall = psm.validOperationCall(ctx, operationCall, possibleTransitions);

			if (!psm.isExecutingTransition() && !validOperationCall) {
				throw new MSystemException("No valid transition available in protocol state machine " + inQuotes(psm) + " for operation call "
						+ operationCall.toString() + ".");
			}

			if (psm.isExecutingTransition() && validOperationCall) {
				// TODO: Put output to context
				Log.warn("Warning: The state machine instance " + inQuotes(psm.toString()));
				Log.warn("ignores the possible valid operation call " + inQuotes(operationCall.toString()) + ",");
				Log.warn("because it already executes a transition.");
			}

			if (!psm.isExecutingTransition() && !possibleTransitions.isEmpty()) {
				operationCall.putPossibleTransitions(psm, possibleTransitions);
				psm.setExecutingTransition(true);
			}
		}

		PPCHandler ppcHandler = determinePPCHandler(operationCall);

		try {
			ppcHandler.handleTransitionsPre(this, operationCall);
		} catch (PreConditionCheckFailedException e) {
			throw new MSystemException(e.getMessage(), e);
		}
	}

	/**
	 * Exits an operation with side effects.
	 * 
	 * @param context
	 * @param result
	 * @param resultValue
	 * @return
	 * @throws MSystemException
	 */
	public MOperationCall exitNonQueryOperation(SoilEvaluationContext context, StatementEvaluationResult result, Value resultValue) throws MSystemException {

		MOperationCall operationCall = getCurrentOperation();
		if (operationCall == null) {
			throw new MSystemException("Call stack is empty.");
		}

		if (operationCall.executionHasFailed()) {
			exitCurrentNonQueryOperation(context, result);
			return operationCall;
		}

		try {
			assertResultValueValid(resultValue, operationCall.getOperation());
			if (resultValue != null)
				operationCall.setResultValue(resultValue);

			EvalContext ctx = new EvalContext(operationCall.getPreState(), fCurrentState, fVariableEnvironment.constructVarBindings(), null, "");
			assertPostConditions(ctx, operationCall);

			if (Options.getCheckTransitions())
				doTransition(result, ctx, operationCall);

			operationCall.setExitedSuccessfully(true);

			return operationCall;
		} finally {
			exitCurrentNonQueryOperation(context, result);
		}
	}

	/**
	 * Executes a transition for all state machines if possible.
	 * 
	 * @throws MSystemException If no valid transition exits in a state machine.
	 */
	private void doTransition(StatementEvaluationResult result, EvalContext ctx, MOperationCall operationCall) throws MSystemException {
		if (!operationCall.hasPossibleTransitions())
			return;

		MObjectState objState = operationCall.getSelf().state(fCurrentState);

		if (ctx == null) {
			VarBindings b = fVariableEnvironment.constructVarBindings();
			ctx = new EvalContext(operationCall.getPreState(), fCurrentState, b, null, "");
		}

		List<TransitionResult> toExecute = new LinkedList<>();

		for (MProtocolStateMachineInstance psm : objState.getProtocolStateMachinesInstances()) {
			Map<MRegion, Set<MTransition>> possibleTransitions = operationCall.getPossibleTransitions(psm);

			// This state machine might not be related to the call
			if (possibleTransitions == null)
				continue;

			Map<MTransition, TransitionResult> results = new HashMap<>();

			boolean allRegionsValid = true;

			for (Map.Entry<MRegion, Set<MTransition>> entry : possibleTransitions.entrySet()) {
				// No transition in this region
				// FIXME: Check semantics of regions
				if (entry.getValue().isEmpty())
					continue;

				int numValidTransitions = 0;
				for (MTransition t : entry.getValue()) {
					TransitionResult r = psm.evaluateTransition(t, ctx, operationCall);
					if (r.wasSuccessfull()) {
						numValidTransitions++;
						toExecute.add(r);
					}
					results.put(t, r);
				}

				allRegionsValid = allRegionsValid && (numValidTransitions == 1);
			}

			if (allRegionsValid) {
				for (TransitionResult r : toExecute) {
					psm.doTransition(r.getTransition());
					operationCall.addExecutedTransition(psm, r.getTransition());
					result.appendEvent(fireTransition(operationCall.getSelf(), psm.getProtocolStateMachine(), r.getTransition()));
					state().updateDerivedValues(result.getStateDifference());
				}
				psm.setExecutingTransition(false);
			} else {
				throw new MSystemException("No valid transition after operation call " + inQuotes(operationCall) + " for protocol state machine "
						+ inQuotes(psm) + ". Possible transitions:\n" + StringUtil.fmtSeq(results.values(), "\n"));
			}
		}
	}

	/**
	 * @param result 
	 * @param key
	 * @param t
	 */
	public void revertTransition(StatementEvaluationResult result, MProtocolStateMachineInstance psmI, MTransition t) {
		psmI.revertTransition(t);
		result.appendEvent(fireTransition(psmI.getObject(), psmI.getProtocolStateMachine(), t));
		state().updateDerivedValues(result.getStateDifference());
	}
	
	/**
	 * Tries to determine the states of the state machines
	 * for the defined objects by evaluating the state invariants
	 * of each object and the corresponding state machines.
	 */
	public void determineStates(PrintWriter out) {
		for (MObject o : this.state().allObjects()) {
			if (o.cls().getAllOwnedProtocolStateMachines().isEmpty()) continue;
			
			for (MProtocolStateMachineInstance psmI : o.state(this.state()).getProtocolStateMachinesInstances()) {
				psmI.determineState(this.state(), out);
				fireTransition(o, psmI.getProtocolStateMachine(), null);
				state().updateDerivedValues();
			}
		}
		
		out.println("State determination finished.");
	}
	
	/**
	 * Evaluate and check all preconditions of an operation call. If any is not
	 * fulfilled, an exception is raised. Before the exception is raised, the
	 * PPC handler (if any) is invoked.
	 * 
	 * @param operationCall
	 * @throws MSystemException
	 */
	private void assertPreConditions(EvalContext ctx, MOperationCall operationCall) throws MSystemException {

		evaluatePreConditions(ctx, operationCall);

		if (operationCall.getPreConditionEvaluationResults().isEmpty())
			return;

		PPCHandler ppcHandler = determinePPCHandler(operationCall);

		try {
			ppcHandler.handlePreConditions(this, operationCall);
		} catch (PreConditionCheckFailedException e) {
			throw new MSystemException(e.getMessage(), e);
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
	 * Creates a copy of the current system state, if there are any
	 * postconditions for this operation or if currently a test suite is
	 * executed.
	 * 
	 * @param operationCall
	 */
	private void copyPreStateIfNeccessary(MOperationCall operationCall) {
		// if the post conditions of this operations require a pre state
		// require a state copy, create it
		if (isRunningTestSuite || operationCall.hasPostConditions() && operationCall.getOperation().postConditionsRequirePreState()
				|| operationCall.getSelf().cls().hasStateMachineWhichHandles(operationCall)) {

			operationCall.setPreState(new MSystemState(fUniqueNameGenerator.generate("state#"), fCurrentState));
		} else {
			operationCall.setPreState(fCurrentState);
		}
	}

	/**
	 * Assert that the actual parameter values in this opcall are consistent
	 * with the operation.
	 * 
	 * @param operationCall
	 * @throws MSystemException
	 */
	private void assertParametersValid(MOperationCall operationCall) throws MSystemException {
		if (operationCall.getArguments().length != operationCall.getOperation().paramList().size()) {
			throw new MSystemException("Number of arguments does not match declaration of " + " operation "
					+ StringUtil.inQuotes(operationCall.getOperation().name()) + " in class " + StringUtil.inQuotes(operationCall.getSelf().cls().name())
					+ ". Expected " + operationCall.getOperation().paramList().size() + " arguments" + ((operationCall.getArguments().length == 1) ? "" : "s")
					+ ", found " + operationCall.getArguments().length + ".");
		}

		for (int i = 0; i < operationCall.getOperation().paramList().size(); ++i) {

			VarDecl parameter = operationCall.getOperation().paramList().varDecl(i);
			Value argument = operationCall.getArguments()[i];

			Type expectedType = parameter.type();
			Type foundType = argument.type();

			if (!foundType.conformsTo(expectedType)) {

				throw new MSystemException("Type mismatch in argument " + i + ". Expected type " + StringUtil.inQuotes(expectedType) + ", found "
						+ StringUtil.inQuotes(foundType) + ".");
			}
		}
	}

	/**
	 * Assert that the result value of this operation call is consistent with
	 * the operation.
	 * 
	 * @param resultValue
	 * @param forceExit
	 * @param operation
	 * @throws MSystemException
	 */
	private void assertResultValueValid(Value resultValue, MOperation operation) throws MSystemException {
		if (operation.hasResultType()) {
			if (resultValue == null) {
				throw new MSystemException("Result value of type " + inQuotes(operation.resultType()) + " required on exit of operation " + inQuotes(operation)
						+ ".");

				// result value has incompatible type
			} else if (!resultValue.type().conformsTo(operation.resultType())) {
				throw new MSystemException("Result value type " + inQuotes(resultValue.type()) + " does not match operation result type "
						+ inQuotes(operation.resultType()) + ".");
				// result value is of correct type
			}
			// operation has no return value
		} else {
			// redundant result value, just give a warning
			if (resultValue != null) {
				Log.out().println(
						"Warning: Result value " + inQuotes(resultValue) + " is ignored, since operation " + inQuotes(operation)
								+ " is not defined to return a value.");
			}
		}
	}

	/**
	 * Evaluate and check all postconditions of an operation call. If any is not
	 * fulfilled, an exception is raised. Before the exception is raised, the
	 * PPC handler (if any) is invoked.
	 * 
	 * @param operationCall
	 * @throws MSystemException
	 */
	private void assertPostConditions(EvalContext ctx, MOperationCall operationCall) throws MSystemException {
		evaluatePostConditions(ctx, operationCall);

		if (operationCall.getPostConditionEvaluationResults().isEmpty())
			return;

		PPCHandler ppcHandler = determinePPCHandler(operationCall);
		try {
			ppcHandler.handlePostConditions(this, operationCall);
		} catch (PostConditionCheckFailedException e) {
			throw (new MSystemException(e.getMessage()));
		}
	}

	/**
	 * Exits the current non query operation, by setting the current operation
	 * call to exited, removing the operation call from the call stack and
	 * popping the current frame. Further, an operation exit event is added to
	 * the events of <code>result</code>.
	 */
	private void exitCurrentNonQueryOperation(SoilEvaluationContext context, StatementEvaluationResult result) {
		MOperationCall currentOperation = getCurrentOperation();
		if (currentOperation == null)
			throw new RuntimeException("Cannot exit without a current operation!");
		currentOperation.setExited(true);
		fCallStack.pop();
		MStatement currentStatement = getCurrentStatement();
		if (currentStatement != null) {
			result.appendEvent(fireOperationExited(currentOperation));
		}
		fVariableEnvironment.popFrame();
	}

	/**
	 * Returns a unique name that can be used for a new object of the given
	 * class.
	 */
	public String uniqueObjectNameForClass(String clsName) {
		return fUniqueNameGenerator.generate(clsName);
	}

	/**
	 * Creates an object in the current system state, captures additional
	 * information about the execution.
	 * 
	 * @param objectClass
	 * @param objectName
	 * @throws MSystemException
	 */
	public MObject createObject(StatementEvaluationResult result, MClass objectClass, String objectName) throws MSystemException {

		MObject newObject = fCurrentState.createObject(objectClass, objectName);
		result.getStateDifference().addNewObject(newObject);
		result.prependToInverseStatement(new MObjectDestructionStatement(newObject.value()));

		result.appendEvent(fireObjectCreated(newObject));
		
		state().updateDerivedValues(result.getStateDifference());

		return newObject;
	}

	/**
	 * Destroys an object in the current system state and keeps track of the
	 * changes.
	 * 
	 * @throws MSystemException
	 */
	public void destroyObject(StatementEvaluationResult result, MObject object) throws MSystemException {

		// we cannot destroy an object with an active operation. we need to
		// check whether this object, or any of the link objects possibly
		// connected to this object have an active operation
		Set<MObject> objectsAffectedByDeletion = fCurrentState.getObjectsAffectedByDestruction(object);

		for (MObject affectedObject : objectsAffectedByDeletion) {
			if (hasActiveOperation(affectedObject)) {
				throw new MSystemException("Object " + StringUtil.inQuotes(affectedObject) + " has an active operation and thus cannot be deleted.");
			}
		}

		// .deleteObject() also takes care of the links this
		// object has been participating in
		DeleteObjectResult deleteResult = fCurrentState.deleteObject(object);
		result.getStateDifference().addDeleteResult(deleteResult);

		Map<MObject, List<String>> undefinedTopLevelReferences = new HashMap<MObject, List<String>>();

		for (MObject destroyedObject : deleteResult.getRemovedObjects()) {
			List<String> topLevelReferences = fVariableEnvironment.getTopLevelReferencesTo(destroyedObject);

			if (!topLevelReferences.isEmpty()) {
				undefinedTopLevelReferences.put(destroyedObject, topLevelReferences);
			}

			fVariableEnvironment.undefineReferencesTo(destroyedObject);
		}

		result.prependToInverseStatement(new MObjectRestorationStatement(deleteResult, undefinedTopLevelReferences));

		result.appendEvent(fireObjectDestroyed(object));
		if (object instanceof MLink) {
			MLink link = (MLink) object;
			result.appendEvent(fireLinkDeleted(link));
		}

		Set<MLink> deletedLinks = new HashSet<MLink>(deleteResult.getRemovedLinks());
		Set<MObject> deletedObjects = new HashSet<MObject>(deleteResult.getRemovedObjects());

		deletedLinks.remove(object);
		deletedObjects.remove(object);

		for (MObject o : deletedObjects) {
			if (o instanceof MLink) {
				deletedLinks.add((MLink) o);
			}
		}

		deletedObjects.removeAll(deletedLinks);

		for (MLink l : deletedLinks) {
			result.appendEvent(fireLinkDeleted(l));
		}

		for (MObject o : deletedObjects) {
			result.appendEvent(fireObjectDestroyed(o));
		}

		state().updateDerivedValues(result.getStateDifference());
	}

	/**
	 * Inserts a link between objects to the current state and keeps track of
	 * the changes.
	 * 
	 * @param association
	 * @param participants
	 * @param qualifierValues
	 * @throws EvaluationFailedException
	 */
	public MLink createLink(StatementEvaluationResult result, MAssociation association, List<MObject> participants, List<List<Value>> qualifierValues)
			throws MSystemException {

		MLink newLink = fCurrentState.createLink(association, participants, qualifierValues);

		result.getStateDifference().addNewLink(newLink);

		List<MRValue> wrappedParticipants = new ArrayList<MRValue>(participants.size());

		for (MObject participant : participants) {
			wrappedParticipants.add(new MRValueExpression(participant));
		}

		List<List<MRValue>> wrappedQualifier = new LinkedList<List<MRValue>>();

		for (List<Value> qValues : qualifierValues) {
			List<MRValue> wrappedQValues;
			if (qValues.size() == 0) {
				wrappedQValues = Collections.emptyList();
			} else {
				wrappedQValues = new LinkedList<MRValue>();
				for (Value qValue : qValues) {
					wrappedQValues.add(new MRValueExpression(qValue));
				}
			}
			wrappedQualifier.add(wrappedQValues);
		}

		result.prependToInverseStatement(new MLinkDeletionStatement(association, wrappedParticipants, wrappedQualifier));
		result.appendEvent(fireLinkInserted(newLink));

		state().updateDerivedValues(result.getStateDifference());

		return newLink;
	}

	/**
	 * Deletes a link in the current system state and keeps track of the
	 * changes.
	 * 
	 * @param association
	 * @param participants
	 * @throws MSystemException
	 * @throws EvaluationFailedException
	 */
	public void deleteLink(StatementEvaluationResult result, MAssociation association, List<MObject> participants, List<List<Value>> qualifierValues)
			throws MSystemException {

		// we need to find out if this is actually a link object, since we need
		// to call destroyObject in that case to get the correct undo
		// statement
		MLink link = fCurrentState.linkBetweenObjects(association, participants, qualifierValues);

		if (link == null) {
			throw new MSystemException("Link `" + association.name() + "' between ("
					+ StringUtil.fmtSeqWithSubSeq(participants, ",", qualifierValues, ",", "{", "}") + ") does not exist.");
		}

		if (link instanceof MLinkObject) {
			destroyObject(result, (MLinkObject) link);
			return;
		}

		result.getStateDifference().addDeleteResult(fCurrentState.deleteLink(link));

		List<MRValue> wrappedParticipants = new ArrayList<MRValue>(participants.size());

		for (MObject participant : participants) {
			wrappedParticipants.add(new MRValueExpression(participant));
		}

		List<List<MRValue>> wrappedQualifier;
		if (qualifierValues == null || qualifierValues.isEmpty()) {
			wrappedQualifier = Collections.emptyList();
		} else {
			wrappedQualifier = new ArrayList<List<MRValue>>(qualifierValues.size());

			for (List<Value> endQualifier : qualifierValues) {
				List<MRValue> endQualifierValues;

				if (endQualifier == null || endQualifier.isEmpty()) {
					endQualifierValues = Collections.emptyList();
				} else {
					endQualifierValues = new ArrayList<MRValue>();
					for (Value v : endQualifier) {
						endQualifierValues.add(new MRValueExpression(v));
					}
				}

				wrappedQualifier.add(endQualifierValues);
			}
		}
		result.prependToInverseStatement(new MLinkInsertionStatement(association, wrappedParticipants, wrappedQualifier));

		state().updateDerivedValues(result.getStateDifference());
		
		LinkDeletedEvent e = new LinkDeletedEvent(executionContext, link);
		result.appendEvent(e);
		
		getEventBus().post(e);
	}

	/**
	 * Creates a link object in the current system state and keeps track of the
	 * changes.
	 * 
	 * @param associationClass
	 * @param linkObjectName
	 * @param participants
	 * @return
	 * @throws MSystemException
	 * @throws EvaluationFailedException
	 */
	public MLinkObject createLinkObject(StatementEvaluationResult result, MAssociationClass associationClass, String linkObjectName,
			List<MObject> participants, List<List<Value>> qualifierValues) throws MSystemException {

		MLinkObject newLinkObject;
		newLinkObject = fCurrentState.createLinkObject(associationClass, linkObjectName, participants, qualifierValues);

		result.getStateDifference().addNewLinkObject(newLinkObject);
		result.prependToInverseStatement(new MObjectDestructionStatement(newLinkObject.value()));

		result.appendEvent(fireObjectCreated(newLinkObject));
		result.appendEvent(fireLinkInserted(newLinkObject));
		
		state().updateDerivedValues(result.getStateDifference());

		return newLinkObject;
	}

	/**
	 * Assigns an attribute value of an object in the current system state and
	 * keeps track of the changes.
	 * 
	 * @param object
	 * @param attribute
	 * @param value
	 * @throws MSystemException
	 * @throws EvaluationFailedException
	 */
	public void assignAttribute(StatementEvaluationResult result, MObject object, MAttribute attribute, Value value) throws MSystemException {

		Value oldValue;

		try {
			oldValue = object.state(fCurrentState).attributeValue(attribute);
			object.state(fCurrentState).setAttributeValue(attribute, value);
		} catch (IllegalArgumentException e) {
			throw new MSystemException(e.getMessage());
		}

		result.getStateDifference().addModifiedObject(object);

		result.prependToInverseStatement(new MAttributeAssignmentStatement(object, attribute, oldValue));

		result.appendEvent(fireAttributeAssigned(object, attribute, value));

		state().updateDerivedValues(result.getStateDifference());
	}

	/**
	 * Assigns the <code>value</code> to the variable named
	 * <code>variableName</code> and constructs an inverse statement (
	 * {@link MVariableAssignmentStatement} or
	 * {@link MVariableDestructionStatement}) and prepends it to the provided
	 * <code>result</code>.
	 * 
	 * @param result The statement evaluation result to prepend the inverse
	 *            statements to.
	 * @param variableName The variable name to set the new value for.
	 * @param value The new value of the variable.
	 */
	public void assignVariable(StatementEvaluationResult result, String variableName, Value value) {

		Value oldValue = fVariableEnvironment.lookUp(variableName);

		if (oldValue != null) {
			result.prependToInverseStatement(new MVariableAssignmentStatement(variableName, oldValue));
		} else {
			result.prependToInverseStatement(new MVariableDestructionStatement(variableName));
		}

		fVariableEnvironment.assign(variableName, value);
	}

	/**
	 * Executes a statement using the current system state and stores the result
	 * on the undo stack. If the execution fails, the effects of the statement
	 * are reverted. Update listeners are notified and the result is stored.
	 * 
	 * @param statement The statement to execute.
	 * @throws EvaluationFailedException
	 */
	public StatementEvaluationResult execute(MStatement statement) throws MSystemException {
		return execute(statement, true, true, true);
	}

	/**
	 * Executes a statement using the current system state and stores the result
	 * on the undo stack. If the execution fails, the effects of the statement
	 * are reverted.
	 * 
	 * @param statement The statement to execute.
	 * @param notifyUpdateStateListeners If <code>true</code> the registered
	 *            system state listeners are notified after the successful
	 *            execution.
	 * @throws EvaluationFailedException
	 */
	public StatementEvaluationResult execute(MStatement statement, boolean notifyUpdateStateListeners) throws MSystemException {

		return execute(statement, true, true, notifyUpdateStateListeners);
	}

	/**
	 * Executes a statement using the current system state. The redo stack is
	 * cleared beforehand.
	 * 
	 * @param statement The statement to execute.
	 * @param undoOnFailure If <code>true</code>, a failure during the execution
	 *            leads to an undo of all effects of this statement and its
	 *            substatements.
	 * @param storeResult If <code>true</code> the result is stored on statement
	 *            execution stack. If a result is stored, it can be undone.
	 * @param notifyUpdateStateListeners If <code>true</code> the registered
	 *            system state listeners are notified after the successful
	 *            execution.
	 * @return
	 * @throws EvaluationFailedException
	 */
	public StatementEvaluationResult execute(MStatement statement, boolean undoOnFailure, boolean storeResult, boolean notifyUpdateStateListeners)
			throws MSystemException {

		fRedoStack.clear();

		StatementEvaluationResult result = execute(statement, new SoilEvaluationContext(this), undoOnFailure, storeResult, notifyUpdateStateListeners);

		return result;
	}

	/**
	 * Executes a statement.
	 * 
	 * @param statement The statement to execute
	 * @param context The evaluation context
	 * @param undoOnFailure Undo the statement on failure in sub-statements
	 * @param storeResult If <code>true</code> the result is stored on statement
	 *            execution stack. If a result is stored, it can be undone.
	 * @param notifyUpdateStateListeners
	 * @throws EvaluationFailedException
	 */
	private StatementEvaluationResult execute(MStatement statement, SoilEvaluationContext context, boolean undoOnFailure, boolean storeResult,
			boolean notifyUpdateStateListeners) throws MSystemException {

		StatementEvaluationResult result = new StatementEvaluationResult(statement);

		fCurrentlyEvaluatedStatements.push(result);

		if (context.isUndo()) {
			fUniqueNameGenerator.popState();
			executionContext = EventContext.UNDO;
		} else {
			fUniqueNameGenerator.pushState();
		}

		try {
			statement.execute(context, result);
		} catch (EvaluationFailedException e) {
			result.setException(e);
		}

		fCurrentlyEvaluatedStatements.pop();

		if (storeResult) {
			fStatementEvaluationResults.push(result);
		}

		if (result.wasSuccessfull() && notifyUpdateStateListeners) {
			getEventBus().post(new StatementExecutedEvent(executionContext, statement, result.getStateDifference()));
		}

		if (!result.wasSuccessfull()) {
			if (undoOnFailure) {
				if (storeResult)
					fStatementEvaluationResults.pop();
				SoilEvaluationContext ctx = new SoilEvaluationContext(this);
				ctx.setIsUndo(true);
				execute(result.getInverseStatement(), ctx, false, false, notifyUpdateStateListeners);
			}

			throw new MSystemException(result.getException().getMessage(), result.getException());
		}

		executionContext = EventContext.NORMAL_EXECUTION;
		return result;
	}

	/**
	 * Undoes the last executed statement.
	 * 
	 * @return
	 * @throws MSystemException
	 * @throws EvaluationFailedException
	 */
	public StatementEvaluationResult undoLastStatement() throws MSystemException {

		if (fStatementEvaluationResults.isEmpty()) {
			throw new MSystemException("nothing to undo");
		}

		StatementEvaluationResult lastResult = fStatementEvaluationResults.pop();

		MStatement lastStatement = lastResult.getEvaluatedStatement();
		MStatement inverseStatement = lastResult.getInverseStatement();

		fRedoStack.push(lastStatement);

		if (Log.isTracing())
			Log.trace(this, "undoing a statement");

		SoilEvaluationContext context = new SoilEvaluationContext(this);
		context.setIsUndo(true);

		StatementEvaluationResult result = execute(inverseStatement, context, false, false, true);
		
		return result;
	}

	/**
	 * Redoes the last undone statement.
	 * 
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

		StatementEvaluationResult result = execute(redoStatement, context, false, true, true);

		return result;
	}

	/**
	 * Returns the shell command of the last executed statement.
	 * 
	 * @return
	 */
	public String getUndoDescription() {
		if (fStatementEvaluationResults.isEmpty()) {
			return null;
		} else {
			StatementEvaluationResult lastResult = fStatementEvaluationResults.peek();

			MStatement lastEvaluatedStatement = lastResult.getEvaluatedStatement();

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
	 * Returns the shell command of the last undone statement.
	 * 
	 * @return
	 */
	public String getRedoDescription() {

		if (fRedoStack.isEmpty()) {
			return null;
		}

		return fRedoStack.peek().getShellCommand();
	}

	/**
	 * Writes all shell commands of the executed statements of the current
	 * system to <code>out</code>.
	 * 
	 * @param out The <code>PrintWriter</code> to write to.
	 */
	public void writeSoilStatements(PrintWriter out) {
		for (MStatement statement : getEvaluatedStatements()) {
			out.println(statement.getShellCommand());
		}
	}

	/**
	 * Returns the total number of evaluated statements.
	 * 
	 * @return
	 */
	public int numEvaluatedStatements() {
		return fStatementEvaluationResults.size();
	}

	/**
	 * The list of all executed statements.
	 * 
	 * @return
	 */
	public List<MStatement> getEvaluatedStatements() {
		List<MStatement> evaluatedStatements = new ArrayList<MStatement>(fStatementEvaluationResults.size());

		for (StatementEvaluationResult result : fStatementEvaluationResults) {
			evaluatedStatements.add(0, result.getEvaluatedStatement());
		}

		return evaluatedStatements;
	}

	/**
	 * Returns the currently executed statement.
	 * 
	 * @return The executed statement.
	 */
	private MStatement getCurrentStatement() {
		return fCurrentlyEvaluatedStatements.peek().getEvaluatedStatement();
	}

	/**
	 * The current call stack of the system.
	 * 
	 * @return The current call stack of the system.
	 */
	public Deque<MOperationCall> getCallStack() {
		return fCallStack;
	}

	/**
	 * Queries the current call stack for an operation call event with
	 * <code>object</code> as the receiving instance.
	 * 
	 * @param object The object to check an active operation call for.
	 * @return <code>true</code> if the provided object has an active operation.
	 */
	public boolean hasActiveOperation(MObject object) {

		for (MOperationCall operationCall : fCallStack) {
			if (operationCall.getSelf() == object) {
				return true;
			}
		}

		return false;
	}

	/**
	 * <p>
	 * Calculates the list of all events which occurred in this system instance.
	 * </p>
	 * <p>
	 * The events are derived from the previously executed statements and the
	 * currently executed statement, if any.
	 * </p>
	 * 
	 * @return A <code>List</code> with all occurred events, derived from the
	 *         executed statements.
	 */
	public List<Event> getAllEvents() {
		List<Event> result = new ArrayList<Event>();

		Iterator<StatementEvaluationResult> it = fStatementEvaluationResults.descendingIterator();
		while (it.hasNext()) {
			result.addAll(it.next().getEvents());
		}

		StatementEvaluationResult currentResult = fCurrentlyEvaluatedStatements.peek();
		if (currentResult != null) {
			result.addAll(currentResult.getEvents());
		}

		return result;
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

	/**
	 * @param object
	 */
	ObjectCreatedEvent fireObjectCreated(MObject object) {
		if (object instanceof MLink) return null;
		
		ObjectCreatedEvent objectCreatedEvent = new ObjectCreatedEvent(executionContext, object);
        getEventBus().post(objectCreatedEvent);
        
        return objectCreatedEvent;
	}
	
	/**
	 * @param object
	 * @return
	 */
	ObjectDestroyedEvent fireObjectDestroyed(MObject object) {
		ObjectDestroyedEvent event = new ObjectDestroyedEvent(executionContext, object);
		getEventBus().post(event);
		return event;
	}
		
	LinkDeletedEvent fireLinkDeleted(MLink link) {
		LinkDeletedEvent event = new LinkDeletedEvent(executionContext, link);
		getEventBus().post(event);
		return event;
	}

	/**
	 * @param link
	 */
	LinkInsertedEvent fireLinkInserted(MLink link) {
		LinkInsertedEvent event = new LinkInsertedEvent(executionContext, link);
		getEventBus().post(event);
		return event;
	}
	
	AttributeAssignedEvent fireAttributeAssigned(MObject object, MAttribute attribute,
			Value value) {
		AttributeAssignedEvent e = new AttributeAssignedEvent(executionContext, object, attribute, value);
		getEventBus().post(e);
		return e;
	}

	OperationEnteredEvent fireOperationEntered(MOperationCall operationCall) {
		OperationEnteredEvent e = new OperationEnteredEvent(executionContext, operationCall);
		getEventBus().post(e);
		return e;
	}
	
	OperationExitedEvent fireOperationExited(MOperationCall operationCall) {
		OperationExitedEvent e = new OperationExitedEvent(executionContext, operationCall);
		getEventBus().post(e);
		return e;
	}
	
	TransitionEvent fireTransition(MObject source, MStateMachine stateMachine, MTransition transition) {
		TransitionEvent e = new TransitionEvent(executionContext, source, stateMachine, transition);
		getEventBus().post(e);
		return e;
	}
	
	ClassInvariantsLoadedEvent fireClassInvariantsLoadedEvent(Collection<MClassInvariant> invariants) {
		ClassInvariantsLoadedEvent e = new ClassInvariantsLoadedEvent(executionContext, invariants);
		getEventBus().post(e);
		return e;
	}
	
	ClassInvariantsUnloadedEvent fireClassInvariantsUnloadedEvent(Collection<MClassInvariant> invariants) {
		ClassInvariantsUnloadedEvent e = new ClassInvariantsUnloadedEvent(executionContext, invariants);
		getEventBus().post(e);
		return e;
	}
	
	ClassInvariantChangedEvent fireClassInvariantChangeEvent(MClassInvariant invariant, InvariantStateChange change){
		ClassInvariantChangedEvent e = new ClassInvariantChangedEvent(executionContext, invariant, change);
		getEventBus().post(e);
		return e;
	}
	
	/**
	 * @param objectState
	 * @throws MSystemException 
	 */
	public void restoreObject(MObjectState objectState) throws MSystemException {
		fCurrentState.restoreObject(objectState);
        fireObjectCreated(objectState.object());
	}

	/**
	 * @param link
	 */
	public void restoreLink(MLink link) {
		fCurrentState.insertLink(link);
        fireLinkInserted(link);
	}
}
