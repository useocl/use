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

package org.tzi.use.uml.sys.soil;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tzi.use.config.Options;
import org.tzi.use.parser.SrcPos;
import org.tzi.use.parser.soil.ast.ASTStatement;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.CollectionValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.MSystemState.DeleteObjectResult;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.uml.sys.events.AttributeAssignedEvent;
import org.tzi.use.uml.sys.events.LinkDeletedEvent;
import org.tzi.use.uml.sys.events.LinkInsertedEvent;
import org.tzi.use.uml.sys.events.ObjectCreatedEvent;
import org.tzi.use.uml.sys.events.ObjectDestroyedEvent;
import org.tzi.use.uml.sys.events.OperationEnteredEvent;
import org.tzi.use.uml.sys.events.OperationExitedEvent;
import org.tzi.use.uml.sys.ppcHandling.PPCHandler;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.VariableEnvironment;
import org.tzi.use.util.soil.exceptions.evaluation.DestroyObjectWithActiveOperationException;
import org.tzi.use.util.soil.exceptions.evaluation.EvaluationFailedException;
import org.tzi.use.util.soil.exceptions.evaluation.ExceptionOccuredException;
import org.tzi.use.util.soil.exceptions.evaluation.ExpressionEvaluationFailedException;
import org.tzi.use.util.soil.exceptions.evaluation.NotACollectionException;
import org.tzi.use.util.soil.exceptions.evaluation.NotAStringException;
import org.tzi.use.util.soil.exceptions.evaluation.NotAnObjectException;
import org.tzi.use.util.soil.exceptions.evaluation.UndefinedValueException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public abstract class MStatement {
	/** TODO */
	private ASTStatement fSourceStatement;
	/** TODO */
	private SrcPos fSourcePosition;
	/** TODO */
	protected SoilEvaluationContext fContext;
	/** TODO */
	protected StatementEvaluationResult fResult;
	/** TODO */
	protected MSystem fSystem;
	/** TODO */
	protected MSystemState fState;
	/** TODO */
	protected VariableEnvironment fVarEnv;
	/** TODO */
	private boolean fIsOperationBody = false;
	/** TODO */
	private static final String SHELL_PREFIX = "!";
	
	
	/**
	 * TODO
	 * @return
	 */
	public boolean hasSourceStatement() {
		return fSourceStatement != null;
	}
	
	
	/**
	 * TODO
	 * @param sourceStatement
	 */
	public void setSourceStatement(ASTStatement sourceStatement) {
		fSourceStatement = sourceStatement;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public ASTStatement getSourceStatement() {
		return fSourceStatement;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public boolean hasSourcePosition() {
		return fSourcePosition != null;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public SrcPos getSourcePosition() {
		return fSourcePosition;
	}
	
	
	/**
	 * TODO
	 * @param sourcePosition
	 */
	public void setSourcePosition(SrcPos sourcePosition) {
		fSourcePosition = sourcePosition;
	}
	
	
	/**
	 * TODO
	 */
	public StatementEvaluationResult evaluate(SoilEvaluationContext context) {
		
		StatementEvaluationResult result = new StatementEvaluationResult(this);
		
		evaluate(context, result);
		
		return result;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public boolean isEmptyStatement() {
		return this == MEmptyStatement.getInstance();
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public boolean isOperationBody() {
		return fIsOperationBody;
	}


	/**
	 * TODO
	 * @param isOperationBody
	 */
	public void setIsOperationBody(boolean isOperationBody) {
		fIsOperationBody = isOperationBody;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public final String getShellCommand() {
		return SHELL_PREFIX + shellCommand();
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public StatementEvaluationResult getResult() {
		return fResult;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public abstract boolean hasSideEffects();
	
	
	/**
	 * TODO
	 * @return
	 */
	public boolean isCallableInOCL() {
		switch (Options.soilFromOCL) {
		case ALL                  : return true;
		case SIDEEFFECT_FREE_ONLY : return !hasSideEffects();
		case NONE                 :
		default                   : return false;
		}	
	}
	
	
	@Override
	public abstract String toString();


	/**
	 * TODO
	 * @return
	 */
	protected abstract String shellCommand();

	
	/**
	 * TODO
	 * @param indent
	 * @param indentIncr
	 * @return
	 */
	public String toVisitorString(int indent, int indentIncr) {
		
		return toVisitorString(
				new StringBuilder(StringUtil.repeat(" ", indent)), 
				StringUtil.repeat(" ", indentIncr));
		
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	private String toVisitorString(
			StringBuilder indent, 
			String indentIncr) {
		
		StringBuilder result = new StringBuilder();
		
		toVisitorString(indent, indentIncr, result);
		
		return result.toString();
	}
	
	
	/**
	 * TODO
	 * @param indent
	 * @param indentIncrease
	 * @param target
	 */
	protected void toVisitorString(
			StringBuilder indent,
			String indentIncrease,
			StringBuilder target) {
		
		target.append(indent);
		target.append(shellCommand());
	}
	

	/**
	 * TODO
	 * @param context
	 * @param result
	 */
	protected void evaluate(
			SoilEvaluationContext context,
			StatementEvaluationResult result) {
		
		fContext = context;
		fResult = result;
		fSystem = context.getSystem();
		fState = context.getState();
		fVarEnv = context.getVarEnv();
		
		try {
			evaluate();
		} catch (EvaluationFailedException e) {
			result.setException(e);
		}
	}


	/**
	 * TODO
	 * @param hasUndoStatement
	 * @throws EvaluationFailedException
	 */
	protected abstract void evaluate() throws EvaluationFailedException;


	/**
	 * TODO
	 * @param expression
	 * @param mustBeDefined
	 * @return
	 * @throws EvaluationFailedException
	 */
	protected Value evaluateExpression(
			Expression expression, 
			boolean mustBeDefined) throws EvaluationFailedException {
		
		Evaluator evaluator = new Evaluator();
		
		Value value;
		
		fContext.enterExpression(expression);
		try {
			value = evaluator.eval(
					expression, 
					fState, 
					fVarEnv.constructVarBindings());
		} catch(RuntimeException e) {
			throw new ExpressionEvaluationFailedException(
					this, 
					expression, 
					e);
		} finally {
			fContext.exitExpression();
		}
		
		if (mustBeDefined && value.isUndefined()) {
			throw new UndefinedValueException(this, expression);
		}
		
		return value;
	}
	
	
	/**
	 * TODO
	 * @param expression
	 * @return
	 * @throws EvaluationFailedException
	 */
	protected Value evaluateExpression(
			Expression expression) throws EvaluationFailedException {
		
		return evaluateExpression(expression, false);
	}
	

	/**
	 * TODO
	 * @param expression
	 * @return
	 * @throws EvaluationFailedException
	 */
	protected MObject evaluateObjectExpression(
			Expression expression) throws EvaluationFailedException {
		
		Value value = evaluateExpression(expression, true);
		
		if (value instanceof ObjectValue) {
			return ((ObjectValue)value).value();
		} else {
			throw new NotAnObjectException(this, expression);
		}
	}
		
	
	/**
	 * TODO
	 * @param expressions
	 * @return
	 * @throws EvaluationFailedException
	 */
	protected List<MObject> evaluateObjectExpressions(
			List<Expression> expressions) throws EvaluationFailedException {
		
		List<MObject> result = new ArrayList<MObject>(expressions.size());
		
		for (Expression expression : expressions) {
			result.add(evaluateObjectExpression(expression));
		}
		
		return result;
	}
		
	
	/**
	 * TODO
	 * @param expression
	 * @return
	 * @throws EvaluationFailedException
	 */
	protected CollectionValue evaluateCollection(
			Expression expression) throws EvaluationFailedException {
		
		Value value = evaluateExpression(expression, true);
		
		if (value instanceof CollectionValue) {
			return (CollectionValue)value;
		} else {
			throw new NotACollectionException(this, expression);
		}
	}
	
	
	/**
	 * TODO
	 * @param expression
	 * @return
	 * @throws EvaluationFailedException
	 */
	protected String evaluateString(
			Expression expression) throws EvaluationFailedException {
		
		Value value = evaluateExpression(expression, true);
	
		if (value instanceof StringValue) {
			return ((StringValue)value).value();
		} else {
			throw new NotAStringException(this, expression);
		}
	}
	
	
	/**
	 * TODO
	 * @param statement
	 * @throws EvaluationFailedException
	 */
	public void evaluateSubStatement(
			MStatement statement) throws EvaluationFailedException {
		
		statement.evaluate(fContext, fResult);
		
		if (fResult.getException() != null) {
			throw fResult.getException();
		}
	}
	
	
	/**
	 * TODO
	 * @param rValue
	 * @return
	 * @throws EvaluationFailedException 
	 */
	protected Value evaluateRValue(
			MRValue rValue, 
			boolean mustBeDefined) throws EvaluationFailedException {
		
		Value value = rValue.evaluate(this);
		
		if (mustBeDefined && value.isUndefined()) {
			throw new UndefinedValueException(this, rValue);
		}
		
		return value;
	}
	
	
	/**
	 * TODO
	 * @param rValue
	 * @return
	 * @throws EvaluationFailedException 
	 */
	protected Value evaluateRValue(
			MRValue rValue) throws EvaluationFailedException {
		
		return evaluateRValue(rValue, false);
	}
	
	
	/**
	 * TODO	
	 * @param rValue
	 * @return
	 * @throws EvaluationFailedException
	 */
	protected MObject evaluateObjectRValue(
			MRValue rValue) throws EvaluationFailedException {
		
		Value value = evaluateRValue(rValue, true);
		
		if (value instanceof ObjectValue) {
			return ((ObjectValue)value).value();
		} else {
			throw new NotAnObjectException(this, rValue);
		}
	}
	
	
	/**
	 * TODO
	 * @param rValues
	 * @return
	 * @throws EvaluationFailedException
	 */
	protected List<MObject> evaluateObjectRValues(
			List<MRValue> rValues) throws EvaluationFailedException {
		
		List<MObject> result = new ArrayList<MObject>(rValues.size());
		
		for (MRValue rValue : rValues) {
			result.add(evaluateObjectRValue(rValue));
		}
		
		return result;
	}
	
	
	/**
	 * TODO
	 * @param variableName
	 * @param value
	 */
	protected void assignVariable(String variableName, Value value) {
		
		Value oldValue = fVarEnv.lookUp(variableName);
		
		if (oldValue != null) {
			fResult.prependToInverseStatement(
					new MVariableAssignmentStatement(
							variableName, 
							oldValue));
		} else {
			fResult.prependToInverseStatement(
					new MVariableDestructionStatement(variableName));
		}
			
		fVarEnv.assign(variableName, value);	
	}
	
	
	/**
	 * TODO
	 * @param objectClass
	 * @param objectName
	 * @return
	 * @throws EvaluationFailedException
	 */
	protected MObject createObject(
			MClass objectClass, 
			String objectName) throws EvaluationFailedException {
		
		MObject newObject;
		try {
			newObject = fState.createObject(objectClass, objectName);
		} catch (MSystemException e) {
			throw new ExceptionOccuredException(this, e);
		}
		
		fResult.getStateDifference().addNewObject(newObject);
		
		fResult.prependToInverseStatement(
				new MObjectDestructionStatement(newObject.value()));
		
		fResult.appendEvent(new ObjectCreatedEvent(this, newObject));
		
		return newObject;
	}
	
	
	/**
	 * TODO
	 * @param associationClass
	 * @param linkObjectName
	 * @param participants
	 * @return
	 * @throws EvaluationFailedException
	 */
	protected MLinkObject createLinkObject(
			MAssociationClass associationClass, 
			String linkObjectName, 
			List<MObject> participants,
			List<List<Value>> qualifierValues) throws EvaluationFailedException {
		
		MLinkObject newLinkObject;
		try {
			newLinkObject = 
				fState.createLinkObject(
					associationClass, 
					linkObjectName, 
					participants,
					qualifierValues);
			
		} catch (MSystemException e) {
			throw new ExceptionOccuredException(this, e);
		}
		
		fResult.getStateDifference().addNewLinkObject(newLinkObject);
		
		fResult.prependToInverseStatement(
				new MObjectDestructionStatement(newLinkObject.value()));
		
		fResult.appendEvent(
				new LinkInsertedEvent(this, associationClass, participants));
		
		return newLinkObject;
	}
	
	
	/**
	 * TODO
	 * @param object
	 * @throws EvaluationFailedException 
	 */
	protected void destroyObject(MObject object) throws EvaluationFailedException {
		
		// we cannot destroy an object with an active operation. we need to 
		// check whether this object, or any of the link objects possibly
		// connected to this object have an active operation
		Set<MObject> objectsAffectedByDeletion = 
			fState.getObjectsAffectedByDestruction(object);
		
		for (MObject affectedObject : objectsAffectedByDeletion) {
			if (fSystem.hasActiveOperation(affectedObject)) {
				throw new DestroyObjectWithActiveOperationException(
						this, 
						affectedObject);
			}
		}
		
		// .deleteObject() also takes care of the links this
		// object has been participating in
		DeleteObjectResult deleteResult = 
			fState.deleteObject(object);	
		fResult.getStateDifference().addDeleteResult(deleteResult);
		
		Map<MObject, List<String>> undefinedTopLevelReferences = 
			new HashMap<MObject, List<String>>();
			
		for (MObject destroyedObject : deleteResult.getRemovedObjects()) {
			List<String> topLevelReferences = 
				fVarEnv.getTopLevelReferencesTo(destroyedObject);
			
			if (!topLevelReferences.isEmpty()) {
				undefinedTopLevelReferences.put(
						destroyedObject,
						topLevelReferences);
			}
					
			fVarEnv.undefineReferencesTo(destroyedObject);
		}
		
		fResult.prependToInverseStatement(
				new MObjectRestorationStatement(
						deleteResult, 
						undefinedTopLevelReferences));
		
		if (object instanceof MLink) {
			MLink link = (MLink)object;
			fResult.appendEvent(new LinkDeletedEvent(
					this,
					link.association(), 
					Arrays.asList(link.linkedObjectsAsArray())));
		} else {
			fResult.appendEvent(new ObjectDestroyedEvent(this, object));
		}
		
		Set<MLink> deletedLinks = 
			new HashSet<MLink>(deleteResult.getRemovedLinks());
		Set<MObject> deletedObjects = 
			new HashSet<MObject>(deleteResult.getRemovedObjects());
		
		deletedLinks.remove(object);
		deletedObjects.remove(object);
		
		for (MObject o : deletedObjects) {
			if (o instanceof MLink) {
				deletedLinks.add((MLink)o);
			}
		}
		
		deletedObjects.removeAll(deletedLinks);
		
		for (MLink l : deletedLinks) {
			fResult.appendEvent(new LinkDeletedEvent(
					this,
					l.association(), 
					Arrays.asList(l.linkedObjectsAsArray())));
		}
		
		for (MObject o : deletedObjects) {
			fResult.appendEvent(new ObjectDestroyedEvent(this, o));
		}
	}
	
	
	/**
	 * TODO
	 * @param object
	 * @param attribute
	 * @param value
	 * @throws EvaluationFailedException
	 */
	protected void assignAttribute(
			MObject object, 
			MAttribute attribute, 
			Value value) throws EvaluationFailedException {
		
		Value oldValue;
		
		try {
			oldValue = object.state(fState).attributeValue(attribute);
			object.state(fState).setAttributeValue(attribute, value);
		} catch (IllegalArgumentException e) {
			throw new ExceptionOccuredException(this, e);
		}
		
		fResult.getStateDifference().addModifiedObject(object);
		
		fResult.prependToInverseStatement(
				new MAttributeAssignmentStatement(
						object, 
						attribute, 
						oldValue));
		
		fResult.appendEvent(
				new AttributeAssignedEvent(
						this, 
						object, 
						attribute, 
						value));
	}
	
	
	/**
	 * TODO
	 * @param association
	 * @param participants
	 * @param qualifierValues
	 * @throws EvaluationFailedException 
	 */
	protected MLink insertLink(
			MAssociation association, 
			List<MObject> participants,
			List<List<Value>> qualifierValues) throws EvaluationFailedException {
		
		MLink newLink;
		try {
			newLink = fState.createLink(association, participants, qualifierValues);
		} catch (MSystemException e) {
			throw new ExceptionOccuredException(this, e);
		}
		
		fResult.getStateDifference().addNewLink(newLink);
		
		List<MRValue> wrappedParticipants = 
			new ArrayList<MRValue>(participants.size());
		
		for (MObject participant : participants) {
			wrappedParticipants.add(
					new MRValueExpression(participant));
		}
		
		List<List<MRValue>> wrappedQualifier = new LinkedList<List<MRValue>>();
		
		for(List<Value> qValues : qualifierValues) {
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
		
		fResult.prependToInverseStatement(
				new MLinkDeletionStatement(association, wrappedParticipants, wrappedQualifier));
		
		fResult.appendEvent(
				new LinkInsertedEvent(
						this, 
						association, 
						participants));
		
		return newLink;
	}
	
	
	/**
	 * TODO
	 * @param association
	 * @param participants
	 * @throws EvaluationFailedException
	 */
	protected void deleteLink(
			MAssociation association, 
			List<MObject> participants,
			List<List<Value>> qualifierValues) throws EvaluationFailedException {
		
		// we need to find out if this is actually a link object, since we need
		// to call destroyObject in that case to get the correct undo 
		// statement
		MLink link = fState.linkBetweenObjects(association, participants, qualifierValues);
		
		if ((link != null) && (link instanceof MLinkObject)) {
			destroyObject((MLinkObject)link);
			return;
		}
		
		try {
			fResult.getStateDifference().addDeleteResult(
					fState.deleteLink(association, participants, qualifierValues));
		} catch (MSystemException e) {
			throw new ExceptionOccuredException(this, e);
		}
		
		List<MRValue> wrappedParticipants = 
			new ArrayList<MRValue>(participants.size());
		
		for (MObject participant : participants) {
			wrappedParticipants.add(
					new MRValueExpression(participant));
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
		fResult.prependToInverseStatement(
				new MLinkInsertionStatement(association, wrappedParticipants, wrappedQualifier));
		
		fResult.appendEvent(
				new LinkDeletedEvent(
						this, 
						association, 
						participants));
	}
	
	
	/**
	 * TODO
	 * @param operationCall
	 */
	public void enteredOperationDuringEvaluation(
			MOperationCall operationCall) {
		
		fResult.appendEvent(
				new OperationEnteredEvent(
						this, 
						operationCall));
	}
	
	
	/**
	 * TODO
	 * @param operationCall
	 */
	public void exitedOperationDuringEvaluation(
			MOperationCall operationCall) {
		
		fResult.appendEvent(
				new OperationExitedEvent(this, operationCall));
	}
	
	
	/**
	 * TODO
	 * @param self
	 * @param operation
	 * @param arguments
	 * @throws ExceptionOccuredException
	 */
	protected MOperationCall enterOperation(
			MObject self, 
			MOperation operation, 
			Value[] arguments,
			PPCHandler preferredPPCHandler,
			boolean isOpenter) throws ExceptionOccuredException {
	
		MOperationCall operationCall = 
			new MOperationCall(this, self, operation, arguments);
		
		operationCall.setPreferredPPCHandler(preferredPPCHandler);
		
		try {
			fSystem.enterOperation(null, operationCall, isOpenter);
		} catch (MSystemException e) {
			throw new ExceptionOccuredException(this, e);
		}
		
		return operationCall;
	}
	
	
	/**
	 * TODO
	 * @param resultValue
	 * @throws ExceptionOccuredException
	 */
	protected MOperationCall exitOperation(
			Value resultValue,
			PPCHandler preferredPPCHandler) throws ExceptionOccuredException {
		
		MOperationCall currentOperation = fSystem.getCurrentOperation();
		
		if (currentOperation == null) {
			throw new RuntimeException("NO OPERATION");
		}
		
		if (preferredPPCHandler != null) {
			currentOperation.setPreferredPPCHandler(preferredPPCHandler);
		}
		
		try {
			fSystem.exitOperation(null, resultValue);
		} catch (MSystemException e) {
			throw new ExceptionOccuredException(this, e);
		}
		
		return currentOperation;
	}
	
	
	/**
	 * TODO
	 * @param resultValue
	 * @return
	 * @throws ExceptionOccuredException
	 */
	protected MOperationCall exitOperation(
			Value resultValue) throws ExceptionOccuredException {
		
		return exitOperation(resultValue, null);
	}
}