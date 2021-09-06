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
package org.tzi.use.api;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.tzi.use.api.impl.UseSystemApiNative;
import org.tzi.use.api.impl.UseSystemApiUndoable;
import org.tzi.use.config.Options;
import org.tzi.use.config.Options.WarningType;
import org.tzi.use.main.Session;
import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.MultiplicityViolationException;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.util.NullPrintWriter;
import org.tzi.use.util.StringUtil;

/**
 * This class eases the access to a USE session.
 * A USE session stores information about instances of a model, i. e., a system state. 
 * 
 * @author Lars Hamann
 */
public abstract class UseSystemApi {
	
	/**
	 * Creates a new system API for the given session.
	 * The returned API implementation is designed to be used
	 * inside a running USE application session. 
	 * @param session The session to create a new system API for. 
	 * @return A new UseSystemApi instance with an empty system state to manipulate.
	 */
	public static UseSystemApi create(Session session) {
		return new UseSystemApiUndoable(session);
	}
	
	/**
	 * Creates a new system API for the given system.
	 * @param system The system to encapsulate with the API.
	 * @param enableUndo Whether the API should generate undo statements
	 * @return A new UseSystemApi instance with the system state encapsulated to manipulate it.
	 */
	public static UseSystemApi create(MSystem system, boolean enableUndo) {
		if (enableUndo)
			return new UseSystemApiUndoable(system);
		else 
			return new UseSystemApiNative(system);
	}

	/**
	 * Creates a new system API for the given model.
	 * @param model The model to create a new system API for. 
	 * @param enableUndo Whether the API should generate undo statements
	 * @return A new UseSystemApi instance with an empty system state to manipulate.
	 */
	public static UseSystemApi create(MModel model, boolean enableUndo) {
		if (enableUndo)
			return new UseSystemApiUndoable(model);
		else 
			return new UseSystemApiNative(model);
	}
	
	protected final MSystem system;
	
	protected final UseModelApi modelApi;

	protected UseSystemApi(MModel model) {
		this(new MSystem(model));
	}
	
	protected UseSystemApi(MSystem system) {
		this.system = system;
		this.modelApi = new UseModelApi(system.model());
	}
	
	/**
	 * Provides access to the encapsulated {@link MSystem} instance.
	 * @return The encapsulated {@link MSystem}.
	 */
	public final MSystem getSystem() {
		return system;
	}
	
	/**
	 * Returns the object (the instance) identified by <code>objectName</code>
	 * or <code>null</code> if no such object exists.
	 * @param objectName The name of the object to retrieve
	 * @return The object instance with the specified name or <code>null</code> if no such object exists.
	 */
	public final MObject getObject(String objectName) {
		return system.state().objectByName(objectName);
	}

	/**
	 * Returns the object (the instance) identified by <code>objectName</code>.
	 * @param objectName The name of the object to retrieve
	 * @return The object with the specified name.
	 * @throws UseApiException If no object with the name exists.
	 */
	public final MObject getObjectSafe(String objectName) throws UseApiException {
		MObject obj = getObject(objectName);
		if (obj == null) {
			throw new UseApiException("No existing object " + StringUtil.inQuotes(objectName) + ".");
		}
		
		return obj;
	}
	
	/**
	 * Returns the USE objects identified by the names provided by
	 * <code>objectNames</code>.
	 * 
	 * @param objectNames
	 *            The object names to retrieve the objects for.
	 * @return The <code>MObject</code> instances with the given names. 
	 * @throws UseApiException
	 *             If an object with a given name is not present in the current
	 *             system state.
	 */
	public final MObject[] getObjectsSafe(String[] objectNames) throws UseApiException {
		MObject[] useObjects = new MObject[objectNames.length];

		for (int i = 0; i < objectNames.length; ++i) {
			MObject useObject = getObjectSafe(objectNames[i]);
			useObjects[i] = useObject;
		}
		
		return useObjects;
	}

	/**
	 * Objects can be created by specifying an <code>objectName</code> and
	 * a class name <code>objectClass</code>. The concrete class
	 * <code>objectClass</code> cannot be abstract. The created object
	 * is an instance of the class and the object states can be changed over
	 * time.
	 * 
	 * @param className The name of the class for which a new object should be created.
	 * @param objectName The name of the new object.
	 * 
	 * @return The created {@link MObject}.
	 * @throws ApiException
	 *             If the object class name is empty or unknown.
	 */
	public final MObject createObject(String className, String objectName)
			throws UseApiException {

		return createObjects(className, new String[]{objectName})[0];
	}
	
	/**
	 * Creates a new {@link MObject} of for the class <code>objectClass</code>.
	 * 
	 * @param objectClass The class for which a new object should be created.
	 * @param objectName The name of the new object. If <code>null</code>, a unique name is generated.
	 * 
	 * @return The created {@link MObject}.
	 * @throws ApiException
	 *             If the object class name is empty or unknown.
	 */
	public abstract MObject createObjectEx(MClass objectClass, String objectName) throws UseApiException;
	
	/**
	 * Creates new objects for the class named <code>className</code>.
	 * The object names are provided by the argument <code>objectNames</code>.
	 * @param className The name of the class for which a new object should be created.
	 * @param objectNames The names of the new objects.
	 * @throws UseApiException
	 */
	public final MObject[] createObjects(
			String className,
			String... objectNames) throws UseApiException {
		
		MClass cls = modelApi.getClassSafe(className);

		return createObjectsEx(cls, objectNames);
	}
	
	/**
	 * <p>Creates new objects for the class given by <code>objectClass</code>.
	 * The object names are provided by the argument <code>objectNames</code>.</p>
	 * 
	 * <p>This operation can be used if the {@link MClass} instance is known to 
	 * fasten the process (no query for name is required).
	 * If only the class name is known the operation {@link #createObjects(String, String...)}
	 * can be used.</p>
	 * @param objectClass The class for which a new object should be created.
	 * @param objectNames The names of the new objects.
	 * @throws UseApiException
	 */
	public final MObject[] createObjectsEx(MClass objectClass,
			String... objectNames) throws UseApiException {
		MObject[] result = new MObject[objectNames.length];
		
		for (int i = 0; i < objectNames.length; ++i) {
			result[i] = createObjectEx(objectClass, objectNames[i]);
		}
		
		return result;
	}
	
	/**
     * <p>Sets the value of the attribute named <code>attributeName</code> for the
     * object identified by <code>objectName</code> to the value expressed by
     * the OCL expression <code>valueExpression</code>.</p>
     * 
     * @param objectName The name of the object to set the value for.
     * @param attributeName The name of the attribute to set the value for.
     * @param valueExpression An OCL expression evaluating to a value of a valid type of the attribute.
     * @return The USE representation of the result value of the OCL expression given by <code>valueExpression</code>.
	 * @throws UseApiException 
	 *         If no object with the given name exists.
	 *         If the class of the object does not define or inherit an attribute named <code>attributeName</code>. 
     */
    public final Value setAttributeValue(
    		String objectName, 
    		String attributeName, 
    		String valueExpression) throws UseApiException {
    	
    	MObject object = getObjectSafe(objectName);
    	
    	MAttribute attribute = object.cls().attribute(attributeName, true);
    	if (attribute == null) {
    		throw new UseApiException("Unknown attribute named " + StringUtil.inQuotes(attributeName));
    	}
    	
    	Value value = evaluate(valueExpression);
    	
    	setAttributeValueEx(object, attribute, value);
    	
    	return value;
    }
    
    /**
     * <p>Sets the value of the attribute <code>attribute</code> for the
     * object <code>object</code> to the value given by
     * <code>value</code>.</p>
     * 
     * @param object The object to set the value for.
     * @param attribute The attribute to set the value for.
     * @param value A value of a valid type of the attribute.
     * 
	 * @throws UseApiException 
	 *         If the assignment is invalid.
     */
	public abstract void setAttributeValueEx(MObject object,
			MAttribute attribute, Value value) throws UseApiException;
    
    /**
     * Creates a new link for the association named <code>associationName</code>. 
     * The participating objects are identified by their names which
     * are provided by the parameter <code>connectedObjectNames</code>.
     * The order of the participating objects must correspond to the order of the
     * association ends. 
     * @param associationName The name of the association to create the link for.
     * @param connectedObjectNames The names (identifier) of the connected objects in valid order w.r.t. the association ends.
     * @return The new {@link MLink} object. 
     * @throws UseApiException
     */
	public final MLink createLink(String associationName, String... connectedObjectNames) throws UseApiException {
    	MAssociation association = modelApi.getAssociationSafe(associationName);
    	
    	MObject[] objects = getObjectsSafe(connectedObjectNames);
    	
    	return createLinkEx(association, objects, new Value[0][]);
    }
	
	/**
     * Creates a new link for the association named <code>associationName</code>. 
     * The participating objects are identified by their names which
     * are provided by the parameter <code>connectedObjectNames</code>.
     * The order of the participating objects must correspond to the order of the
     * association ends.
     * <p>The values of the qualifiers for the different association ends are provided as a two dimensional array:
     * <ul>
     *   <li>The first dimension specifies the association end the qualifier values are provided for (in the same order as they are defined).</li>
     *   <li>The second dimension specifies the value expressions (a valid OCL expression as a <code>String</code>) of the qualifiers at this end (in the same order as they are defined).</li>
     * </ul>
     * </p>
     * @param associationName The name of the association to create the link for.
     * @param connectedObjectNames The names (identifier) of the connected objects in valid order w.r.t. the association ends.
     * @param qualifierValueExpressions The value expressions of the qualifiers at each end (if any).
     * @return The new {@link MLink} object. 
     * @throws UseApiException
     */
	public final MLink createLink(String associationName, String[] connectedObjectNames, String[][] qualifierValueExpressions) throws UseApiException {

		MAssociation mAssoc = modelApi.getAssociationSafe(associationName);
		
		MObject[] useObjects = getObjectsSafe(connectedObjectNames);
		
		Value[][] qualifierValues = getQualiferValuesFromExpression(
				qualifierValueExpressions, mAssoc);
		
		return createLinkEx(mAssoc, useObjects, qualifierValues);
	}

	/**
     * Creates a new link for the association <code>association</code>. 
     * The participating objects are provided by the array <code>connectedObjects</code>.
     * The order of the participating objects must correspond to the order of the
     * association ends.
     * @param association The association to create the link for.
     * @param connectedObjects The connected objects in valid order w.r.t. the association ends.
     * @return The new {@link MLink} object. 
     * @throws UseApiException
     */
	public final MLink createLinkEx(MAssociation association, MObject[] connectedObjects) throws UseApiException {
		Value[][] qualifierValues = new Value[0][];
		return createLinkEx(association, connectedObjects, qualifierValues);
	}
	
	/**
     * <p>Creates a new link for the association <code>association</code>. 
     * The participating objects are provided by the array <code>connectedObjects</code>.
     * The order of the participating objects must correspond to the order of the
     * association ends.</p>
     * <p>The values of the qualifiers for the different association ends are provided as a 
     * two dimensional array:
     * <ul>
     *   <li>The first dimension specifies the association end the qualifier values are provided for (in the same order as they are defined).</li>
     *   <li>The second dimension provides the values of the qualifiers at this end (in the same order as they are defined).</li>
     * </ul>
     * </p>
     * @param association The association to create the link for.
     * @param connectedObjects The connected objects in valid order w.r.t. the association ends.
     * @param qualifierValues The values of the qualifiers at each end (if any).
     * @return The new {@link MLink} object. 
     * @throws UseApiException
     */
	public abstract MLink createLinkEx(MAssociation association,
			MObject[] connectedObjects, Value[][] qualifierValues)
			throws UseApiException;

	/**
	 * This method creates an instance of an association class.
	 *  
	 * @param associationClassName The name of the association class to create a new instance for.
	 * @param newObjectName The name of the newly created object.
	 * @param connectedObjectNames The names (identifier) of the connected objects in valid order w.r.t. the association ends.
	 * @throws UseApiException
	 *         If the association class is empty or unknown.
	 */
	public final MLinkObject createLinkObject(
			String associationClassName, 
			String newObjectName, 
			String... connectedObjectNames) throws UseApiException {
	
		return createLinkObject(associationClassName, newObjectName, connectedObjectNames, new String[0][]);
	}

	/**
	 * This method creates an instance of an association class.
	 *  
	 * @param associationClassName The name of the association class to create a new instance for.
	 * @param newObjectName The name of the newly created object.
	 * @param connectedObjectsNames The names (identifier) of the connected objects in valid order w.r.t. the association ends.
	 * @throws UseApiException
	 *         If the association class is empty or unknown.
	 */
	public final MLinkObject createLinkObject(
			String associationClassName, 
			String newObjectName, 
			String[] connectedObjectsNames,
			String[][] qualifierValueExpressions) throws UseApiException {

		MAssociationClass assocClass = modelApi.getAssociationClassSafe(associationClassName);
	
		MObject[] connectedObjects = getObjectsSafe(connectedObjectsNames);
		
		Value[][] qualifierValues = getQualiferValuesFromExpression(
				qualifierValueExpressions, assocClass);
		
		return createLinkObjectEx(assocClass, newObjectName, connectedObjects, qualifierValues);
	}

	/**
	 * This method creates a new instance of an association class.
	 *  
	 * @param associationClass The association class to create a new instance for.
	 * @param newObjectName The name of the newly created object.
	 * @param connectedObjects The participating objects.
	 * @throws UseApiException
	 *         
	 */
	public final MLinkObject createLinkObjectEx(
			MAssociationClass associationClass,
			String newObjectName,
			MObject[] connectedObjects) throws UseApiException {
		
		return createLinkObjectEx(associationClass, newObjectName, connectedObjects, new Value[0][]);
	}
	
	/**
	 * This method creates a new instance of an association class.
	 *  
	 * @param associationClass The association class to create a new instance for.
	 * @param newObjectName The name of the newly created object.
	 * @param connectedObjects The participating objects.
	 * @param qualifierValues The values for the qualifiers.
	 * @throws UseApiException
	 *         
	 */
	public abstract MLinkObject createLinkObjectEx(
			MAssociationClass associationClass,
			String newObjectName,
			MObject[] connectedObjects,
			Value[][] qualifierValues) throws UseApiException;
	
	/**
     * Deletes the objects with the names provided by <code>objectNames</code> from the system state.
     * @param objectNames The names of the objects to delete.
     * @throws UseApiException 
     */
    public final void deleteObjects(String... objectNames) throws UseApiException {
    	MObject[] objects = getObjectsSafe(objectNames);    	
    	deleteObjectsEx(objects);
    }
    
    /**
     * Deletes the objects provided by <code>objects</code> from the system state.
     * @param objects The objects to delete.
     * @throws UseApiException 
     */
    public final void deleteObjectsEx(MObject... objects) throws UseApiException {
    	for (MObject obj : objects) {
    		deleteObjectEx(obj);
    	}
    }
    
    /**
     * Deletes the object with the name <code>objectName</code> from the system state.
     * @param objectName
     * @throws UseApiException 
     */
    public final void deleteObject(String objectName) throws UseApiException {
    	MObject object = getObjectSafe(objectName);
    	deleteObjectEx(object);
    }
    
    /**
     * Deletes the object <code>object</code> from the system state.
     * @param object
     * @throws UseApiException 
     */
    public abstract void deleteObjectEx(MObject object) throws UseApiException;

    /**
     * Deletes the {@link MLink} connecting the objects identified by <code>connectedObjectNames</code>
     * from the set of links for the association named <code>associationName</code>. 
     * @param associationName The name of the association containing the link.
     * @param connectedObjectNames The names of the objects participating in the link.
     * @throws UseApiException
     */
    public final void deleteLink(
    		String associationName,
    		String[] connectedObjectNames) throws UseApiException {
    	
    	deleteLink(associationName, connectedObjectNames, new String[0][]);
    }
    
    /**
     * Deletes the {@link MLink} connecting the objects identified by <code>connectedObjectNames</code>
     * using the qualifier value expressions provided by <code>qualifierValueExpressions</code> 
     * from from the set of links for the association named <code>associationName</code>.
     * <p>The values of the qualifiers for the different association ends are provided as a two dimensional array:
     * <ul>
     *   <li>The first dimension specifies the association end the qualifier values are provided for (in the same order as they are defined).</li>
     *   <li>The second dimension specifies the value expressions (a valid OCL expression as a <code>String</code>) of the qualifiers at this end (in the same order as they are defined).</li>
     * </ul>
     * </p>  
     * @param associationName The name of the association containing the link.
     * @param connectedObjectNames The names (identifier) of the connected objects in valid order w.r.t. the association ends.
     * @param qualifierValueExpressions The value expressions of the qualifiers at each end (if any). 
     * @throws UseApiException
     */
    public final void deleteLink(
    		String associationName,
    		String[] connectedObjectNames,
    		String[][] qualifierValueExpressions) throws UseApiException {
    	
    	MAssociation association = modelApi.getAssociationSafe(associationName);
    	MObject[] connectedObjects = getObjectsSafe(connectedObjectNames);
    	Value[][] values = getQualiferValuesFromExpression(qualifierValueExpressions, association);
    	
    	deleteLinkEx(association, connectedObjects, values);
    }
    
    /**
     * Deletes the {@link MLink} connecting the objects <code>connectedObjects</code>
     * from the set of links for the association <code>association</code>. 
     * @param association The association containing the link.
     * @param connectedObjects The objects participating in the link.
     * @throws UseApiException
     */
    public final void deleteLinkEx(
    		MAssociation association, 
    		MObject[] connectedObjects) throws UseApiException {
    	
    	deleteLinkEx(association, connectedObjects, new Value[0][]);
    }
        
    /**
     * Deletes the {@link MLink} connecting the objects <code>connectedObjects</code>
     * using the qualifier values provided by <code>qualifierValues</code> 
     * from from the set of links for the association <code>association</code>.
     * <p>The values of the qualifiers for the different association ends are provided as a two dimensional array:
     * <ul>
     *   <li>The first dimension specifies the association end the qualifier values are provided for (in the same order as they are defined).</li>
     *   <li>The second dimension specifies the value of the qualifiers at this end (in the same order as they are defined).</li>
     * </ul>
     * </p>  
     * @param association The association containing the link.
     * @param connectedObjects The connected objects in valid order w.r.t. the association ends.
     * @param qualifierValues The values of the qualifiers at each end (if any). 
     * @throws UseApiException
     */
    public abstract void deleteLinkEx(
    		MAssociation association,
    		MObject[] connectedObjects,
    		Value[][] qualifierValues) throws UseApiException;
    
    /**
     * Deletes the {@link MLink} <code>link</code> from the system state.
     * @param link The link to be deleted.
     */
    public abstract void deleteLinkEx(MLink link) throws UseApiException;

    /**
	 * Undoes the last executed statement.
	 * @throws UseApiException If no statement is present or undone went wrong.
	 * @throws OperationNotSupportedException If this api instance is not undoable. 
	 */
	public abstract void undo() throws UseApiException, OperationNotSupportedException;
	
	/**
	 * Redoes the last undone statement.
	 * @throws UseApiException If no statement is present or redo went wrong.
	 * @throws OperationNotSupportedException If this api instance is not undoable. 
	 */
	public abstract void redo() throws UseApiException, OperationNotSupportedException;
	
    /**
	 * <p>This method validates the current state of
	 * the encapsulated system and returns the result of the 
	 * validation (<code>true</code> if valid or <code>false</code> if invalid).</p>
	 * 
	 * <p>The validation includes static checks (like multiplicyties) and
	 * the evaluation of all defined invariants.</p> 
	 * 
	 * <p>This method does not provide any details about validation errors.
	 * To retrieve a string representation of the errors use {@link #checkState(PrintWriter)}.</p>
	 * @return <code>true</code> if the state of the encapsulated system is valid.
	 */
    public boolean checkState() {
    	return checkState(NullPrintWriter.getInstance());
    }
    
    /**
	 * <p>This method validates the current state of
	 * the encapsulated system and returns the result of the 
	 * validation (<code>true</code> if valid or <code>false</code> if invalid).</p>
	 * 
	 * <p>The validation includes static checks (like multiplicyties) and
	 * the evaluation of all defined invariants.</p> 
	 * 
	 * @param error A <code>PrintWriter</code> used to report validation errors to.
	 * @return <code>true</code> if the state of the encapsulated system is valid.
	 */
	public boolean checkState(PrintWriter error) {
		boolean isValid;
		// Check structure
		isValid = system.state().checkStructure(error);
		// Check Invariants
		isValid = isValid && system.state().check(error, false, false, true, Collections.<String>emptyList());
		
		return isValid;
	}

	/**
	 * Evaluates the OCL expression <code>expression</code>
	 * on the current system state and returns the result as
	 * a USE value.
	 * @param expression The OCL expression to evaluate.
	 * @return The evaluated USE value.
	 */
	public Value evaluate(String expression) throws UseApiException {
		StringWriter errBuffer = new StringWriter();
		PrintWriter errorPrinter = new PrintWriter(errBuffer, true);
		
		WarningType orgWarningOclAny = Options.checkWarningsOclAnyInCollections();
		WarningType orgWarningUnrelated = Options.checkWarningsUnrelatedTypes();
		
		Options.setCheckWarningsOclAnyInCollections(WarningType.IGNORE);
		Options.setCheckWarningsUnrelatedTypes(WarningType.IGNORE);
		
		Expression expr = OCLCompiler.compileExpression(modelApi.getModel(), expression,
				"USE Api", errorPrinter, system.varBindings());
		
		Options.setCheckWarningsOclAnyInCollections(orgWarningOclAny);
		Options.setCheckWarningsUnrelatedTypes(orgWarningUnrelated);
		
		if (expr == null) {
			throw new UseApiException("Invalid expression "
					+ StringUtil.inQuotes(expression) + "!\n"
					+ errBuffer.toString());
		}
		
		Evaluator evaluator = new Evaluator(false);
		Value val;
		
		try {
			val = evaluator.eval(expr, system.state(), system.varBindings(),
					NullPrintWriter.getInstance());
		} catch (MultiplicityViolationException e) {
			throw new UseApiException("Evaluation failed due to a multiplicity violation!", e);
		}
		
		return val;
	}
	
	/**
	 * Helper method to validate and convert qualifier value expressions to
	 * values.
	 * @param qualifierValueExpressions
	 * @param mAssoc
	 * @return
	 * @throws UseApiException
	 */
	private Value[][] getQualiferValuesFromExpression(
			String[][] qualifierValueExpressions, MAssociation mAssoc)
			throws UseApiException {
		Value[][] qualifierValues;
		
		if (qualifierValueExpressions != null && qualifierValueExpressions.length > 0) {
			int numEnds = mAssoc.associationEnds().size();
			qualifierValues = new Value[numEnds][];
			for (int iEnd = 0; iEnd < numEnds; ++iEnd) {
				if (qualifierValueExpressions[iEnd] == null || qualifierValueExpressions[iEnd].length == 0) {
					qualifierValues[iEnd] = new Value[0];
				} else {
					int numValues = qualifierValueExpressions[iEnd].length;
					qualifierValues[iEnd] = new Value[numValues];
					for (int iValue = 0; iValue < numValues; ++numValues) {
						qualifierValues[iEnd][iValue] = evaluate(qualifierValueExpressions[iEnd][iValue]);
					}
				}
			}
		} else {
			qualifierValues = new Value[0][0];
		}
		return qualifierValues;
	}
	
	protected List<List<Value>> getQualifierValuesAsList(Value[][] qualifierValues) {
		List<List<Value>> qualifierValuesList;
		
		if (qualifierValues == null || qualifierValues.length == 0) {
			qualifierValuesList = Collections.emptyList();
		} else {
			qualifierValuesList = new ArrayList<List<Value>>(qualifierValues.length);
			for (int iEnd = 0; iEnd < qualifierValues.length; ++iEnd) {
				if (qualifierValues[iEnd] == null || qualifierValues[iEnd].length == 0) {
					qualifierValuesList.add(Collections.<Value>emptyList());
				} else {
					Value[] values = qualifierValues[iEnd];
					qualifierValuesList.add(Arrays.asList(values));
				}
			}
		}
		return qualifierValuesList;
	}
}
