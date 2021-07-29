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
package org.tzi.use.api;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.SrcPos;
import org.tzi.use.parser.Symtable;
import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.parser.soil.SoilCompiler;
import org.tzi.use.parser.soil.ast.ASTStatement;
import org.tzi.use.parser.use.USECompiler;
import org.tzi.use.uml.mm.MAggregationKind;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MGeneralization;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MMultiplicity;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.MPrePostCondition;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.mm.commonbehavior.communications.MSignal;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.NullPrintWriter;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.CompilationFailedException;

/**
 * <p>This class encapsulates access to the USE model
 * elements, i. e., access to the UML model elements.
 * All structural modifications of a model can be done
 * through this class which acts as a facade to the
 * overall USE system.</p>
 *
 * <p>For each model manipulation there exists at least one
 * operation which takes only primitive values like <code>String</code> or <code>int</code>.
 * For common modeling patterns like binary associations basic operations
 * are provided, too.</p>
 * <p>The operations which require all information as USE instance values for a
 * given modification are provided for extended and faster access to USE.
 * They are denoted by the suffix <b>Ex</b>.</p>
 *
 * @see UseSystemApi
 * @author Daniela Petrova
 * @author Lars Hamann
 */
public class UseModelApi {

	/**
	 * The instance of the encapsulated model.
	 */
	private MModel mModel;

	/**
	 * Helper object
	 */
	ModelFactory mFactory = new ModelFactory();

	/**
	 * Creates a new UseModelApi instance with an empty model named "unnamed".
	 * The new model instance can be retrieved by {@link #getModel()}.
	 */
	public UseModelApi() {
		mModel = mFactory.createModel("unnamed");
	}

	/**
	 * Creates a new UseModelApi instance with an empty model named <code>modelName</code>.
	 * The new model instance can be retrieved by {@link #getModel()}.
	 * @param modelName The name of the new model.
	 */
	public UseModelApi(String modelName) {
		mModel = mFactory.createModel(modelName);
	}

	/**
	 * Creates a new UseModelApi instance with
	 * the provided <code>model</code> as the model instance.
	 * This is useful if you want to modify an existing model instance.
	 * @param model The model to modify through this API instance.
	 */
	public UseModelApi(MModel model) {
		mModel = model;
	}

	/**
	 * Returns the model modified through this API instance.
	 * @return the model handled by this API instance.
	 */
	public MModel getModel() {
		return mModel;
	}

	/**
	 * Queries the underlying model for the class with the name <code>className</code>
	 * and returns the corresponding meta-class ({@link MClass}) instance.
	 * @param className The name of the class to query for.
	 * @return The meta-class with the given name or <code>null</code> if the class is unknown.
	 */
	public MClass getClass(String className) {
		return mModel.getClass(className);
	}

	/**
	 * Queries the underlying model for the association with the name <code>asssociationName</code>
	 * and returns the corresponding meta-class ({@link MAssociation}) instance.
	 * @param associationName The name of the association to query for.
	 * @return The meta-class instance with the given name or <code>null</code> if the association is unknown.
	 */
	public MAssociation getAssociation(String associationName) {
		return mModel.getAssociation(associationName);
	}

	/**
	 * Queries the underlying model for the association with the name <code>asssociationName</code>
	 * and returns the corresponding meta-class ({@link MAssociation}) instance.
	 * @param associationName The name of the association to query for.
	 * @return The meta-class instance with the given name.
	 * @throws UseApiException If the association is unknown.
	 */
	public MAssociation getAssociationSafe(String associationName) throws UseApiException {
		MAssociation association =  mModel.getAssociation(associationName);

		if (association == null) {
			throw new UseApiException("Unknown association named " + StringUtil.inQuotes(associationName) +".");
		}

		return association;
	}

	/**
	 * Queries the underlying model for the association class with the name <code>asssociationClassName</code>
	 * and returns the corresponding meta-class ({@link MAssociationClass}) instance.
	 * @param associationClassName The name of the association class to query for.
	 * @return The meta-class instance with the given name or <code>null</code> if the association class is unknown.
	 */
	public MAssociationClass getAssociationClass(String associationClassName) {
		return mModel.getAssociationClass(associationClassName);
	}

	/**
	 * Queries the underlying model for the association class with the name <code>asssociationClassName</code>
	 * and returns the corresponding meta-class ({@link MAssociationClass}) instance.
	 * @param associationClassName The name of the association class to query for.
	 * @return The meta-class instance with the given name.
	 * @throws UseApiException If no association class with the given name is defined in the model.
	 */
	public MAssociationClass getAssociationClassSafe(String associationClassName) throws UseApiException {
		MAssociationClass cls = mModel.getAssociationClass(associationClassName);

		if (cls == null) {
			throw new UseApiException("Unknown association class "
					+ StringUtil.inQuotes(associationClassName));
		}

		return cls;
	}

	/**
	 * Creates a new USE model, which contains all other model elements. This
	 * operation is called after the creation of the new session. System and
	 * model may change during a session. At the beginning of building a valid
	 * USE model is needed only an unique model name <code>modelName</code>.
	 *
	 * @param modelName
	 * @return the new created model
	 * @throws ApiException
	 */
	public MModel createModel(String modelName) {
		mModel = mFactory.createModel(modelName);
		return mModel;
	}

	/**
	 * Creates a new class in a USE model. The model is owning this class. First
	 * a shell session is opened and the model has a valid name
	 * <code>modelName</code>. Then the creation of the new class is possible
	 * with a valid <code>className</code> and a classifier if the class is
	 * abstract or not <code>isAbstract</code>.
	 *
	 * @param className The name of the class to create. Must be unique in a model.
	 * @param isAbstract If <code>true</code>, no instances can be created for this class.
	 * @return the newly created class
	 * @throws UseApiException If no class name is given or if the class is invalid.
	 */

	public MClass createClass(String className, boolean isAbstract) throws UseApiException {
		if (className == null || className.equals("")) {
			throw new UseApiException("A class must be named");
		}

		MClass cls = mFactory.createClass(className, isAbstract);

		try {
			mModel.addClass(cls);
		} catch (MInvalidModelException e) {
			throw new UseApiException("Add class failed!", e);
		}

		return cls;
	}

	/**
	 * Creates a new enumeration with the given <code>literals</code> in the current model.
	 * @param enumerationName The name of the enumeration (<i>required</i>).
	 * @param literals The enumeration literals
	 * @return The created enumeration
	 * @throws UseApiException
	 */
	public EnumType createEnumeration(String enumerationName, String... literals) throws UseApiException {
		return createEnumeration(enumerationName, Arrays.asList(literals));
	}

	/**
	 * Creates a new enumeration with the given <code>literals</code> in the current model.
	 * @param enumerationName The name of the enumeration (<i>required</i>).
	 * @param literals The enumeration literals
	 * @return The created enumeration
	 * @throws UseApiException
	 */
	public EnumType createEnumeration(String enumerationName, List<String> literals) throws UseApiException {

		if (enumerationName == null || enumerationName.equals("")) {
			throw new UseApiException("A name is required for an enumeration.");
		}

		EnumType enumType = TypeFactory.mkEnum(enumerationName, literals);

		try {
			mModel.addEnumType(enumType);
		} catch (MInvalidModelException e) {
			throw new UseApiException("Enumeration creation failed!", e);
		}

		return enumType;
	}

	/**
	 * Creates an attribute with the name <code>attributeName</code> for the class
	 * identified by the name <code>owningClassName</code>.
	 * The type of the attribute can be a built-in OCL type or a already created
	 * user defined type (Class or Enumeration).
	 * @param owningClassName The name of the class to create the attribute for.
	 * @param attributeName The name of the attribute to create.
	 * @param attributeType The type of the attribute.
	 * @return The created <code>MAttribute</code> with given name and type.
	 * @throws UseApiException
	 */
	public MAttribute createAttribute(String owningClassName, String attributeName, String attributeType)
			throws UseApiException {

		MClass cls = getClassSafe(owningClassName);

		Type mAttributeType = getType(attributeType);

		if (mAttributeType == null) {
			throw new UseApiException("Unknown type " + StringUtil.inQuotes(attributeType) + " for attribute.");
		}

		return createAttributeEx(cls, attributeName, mAttributeType);
	}

	/**
	 * Creates an attribute with the name <code>attributeName</code> and type <code>attributeType</code> for the class
	 * <code>owningClass</code>.
	 * @param owningClass The class to create the attribute for.
	 * @param attributeName The name of the attribute to create.
	 * @param attributeType The type of the attribute.
	 * @return The created <code>MAttribute</code> with given name and type.
	 * @throws UseApiException
	 */
	public MAttribute createAttributeEx(MClass owningClass, String attributeName, Type attributeType)
			throws UseApiException {
		MAttribute attrib = mFactory.createAttribute(attributeName, attributeType);

		try {
			owningClass.addAttribute(attrib);
		} catch (MInvalidModelException e) {
			throw new UseApiException("Attribute creation failed!", e);
		}

		return attrib;
	}

	/**
	 * <p>Creates an operation signature with the name <code>operationName</code> for the class
	 * identified by <code>ownerName</code>.</p>
	 * <p>The return type of the operation is defined by the parameter <code>returnType</code>.
	 * It can be any built-in or already created user defined type.
	 * </p>
	 * <p>The parameters of the operation to create are specified by a two dimensional array.
	 * The first dimension defines the parameter position. The second dimension has exactly two entries:
	 * <ol>
	 *   <li> At index 0 the name of the parameter</li>
	 *   <li> At index 1 the type of the parameter</li>
	 * </ol>
	 * @param ownerName The class name to create the operation for.
	 * @param operationName The name of the operation to create.
	 * @param parameter The operation parameters
	 * @param returnType The return type of the operation (can be <code>null</code>).
	 * @return The created <code>MOperation</code>.
	 * @throws UseApiException
	 */
	public MOperation createOperation(String ownerName, String operationName,
			String[][] parameter, String returnType) throws UseApiException {

		if (ownerName == null || ownerName.equals("")) {
			throw new UseApiException("Owner name is required!");
		}

		if (operationName == null || operationName.equals("")) {
			throw new UseApiException("Operation name is required!");
		}

		MClass owner = getClassSafe(ownerName);

		VarDeclList vars = new VarDeclList(false);
		for (String[] var : parameter) {
			Type t = getType(var[1]);
			vars.add(new VarDecl(var[0], t));
		}

		Type resultType = null;
		if (returnType != null) {
			resultType = getType(returnType);
		}

		return createOperationEx(owner, operationName, vars, resultType);
	}

	/**
	 * <p>Creates an operation with the name <code>operationName</code> for the class
	 * <code>owner</code>.</p>
	 * <p>The return type of the operation is defined by the parameter <code>returnType</code>.</p>
	 * <p>The parameters of the operation are specified as a variable declaration list.
	 * @param owner The class to create the operation for.
	 * @param operationName The name of the operation to create.
	 * @param parameter The operation parameters
	 * @param returnType The return type of the operation (can be <code>null</code>).
	 * @return The created <code>MOperation</code>.
	 * @throws UseApiException
	 */
	public MOperation createOperationEx(MClass owner, String operationName,
			VarDeclList parameter, Type returnType) throws UseApiException {

		MOperation op = mFactory.createOperation(operationName, parameter, returnType);

		try {
			owner.addOperation(op);
		} catch (MInvalidModelException e) {
			throw new UseApiException("Operation creation failed!", e);
		}

		return op;
	}

	/**
	 * Creates a new query operation named <code>operationName</code>
	 * for the class <code>ownerName</code>.
	 * <p>The return type of the operation is defined by the parameter <code>returnType</code>.
	 * It can be any built-in or already created user defined type.
	 * </p>
	 * <p>The parameters of the operation to create are specified by a two dimensional array.
	 * The first dimension defines the parameter position. The second dimension has exactly two entries:
	 * <ol>
	 *   <li> At index 0 the name of the parameter</li>
	 *   <li> At index 1 the type of the parameter</li>
	 * </ol>
	 * </p>
	 * <p>The <code>body</code> of the operation can be any valid <i>OCL</i>-expression that conforms to the return type.</p>
	 * @param owner The class to create the operation for.
	 * @param operationName The name of the operation to create.
	 * @param parameter The operation parameters
	 * @param returnType The return type of the operation (can be <code>null</code>).
	 * @param body The OCL-expression of the operation.
	 * @return The created <code>MOperation</code>.
	 * @throws UseApiException
	 */
	public MOperation createQueryOperation(String ownerName, String operationName,
			String[][] parameter, String returnType, String body) throws UseApiException {

		MOperation op = createOperation(ownerName, operationName, parameter, returnType);

		StringWriter errBuffer = new StringWriter();
		PrintWriter errorPrinter = new PrintWriter(errBuffer, true);

		Symtable symTable = new Symtable();
		try {
			symTable.add("self", op.cls(), null);
		} catch (SemanticException e) {
			throw new UseApiException("Could not create query operation.", e);
		}

		Expression bodyExp = OCLCompiler.compileExpression(mModel, body, "body", errorPrinter, symTable);

		if (bodyExp == null) {
			throw new UseApiException(
					"Compilation of body expression failed:\n"
							+ errBuffer.toString());
		}

		try {
			op.setExpression(bodyExp);
		} catch (MInvalidModelException e) {
			throw new UseApiException("Could not create query operation.", e);
		}

		return op;
	}

	/**
	 * Creates a new operation with an imperative body named <code>operationName</code>
	 * for the class <code>ownerName</code>.
	 * <p>The return type of the operation is defined by the parameter <code>returnType</code>.
	 * It can be any built-in or already created user defined type.
	 * </p>
	 * <p>The parameters of the operation to create are specified by a two dimensional array.
	 * The first dimension defines the parameter position. The second dimension has exactly two entries:
	 * <ol>
	 *   <li> At index 0 the name of the parameter</li>
	 *   <li> At index 1 the type of the parameter</li>
	 * </ol>
	 * </p>
	 * <p>The <code>body</code> of the operation can be any valid <i>SOIL</i>-operation body that conforms to the return type.</p>
	 * @param owner The class to create the operation for.
	 * @param operationName The name of the operation to create.
	 * @param parameter The operation parameters
	 * @param returnType The return type of the operation (can be <code>null</code>).
	 * @param body The SOIL-body of the operation.
	 * @return The created <code>MOperation</code>.
	 * @throws UseApiException
	 */
	public MOperation createImperativeOperation(String ownerName, String operationName,
			String[][] parameter, String returnType, String body) throws UseApiException {

		MOperation op = createOperation(ownerName, operationName, parameter, returnType);

		InputStream input = new ByteArrayInputStream(body.getBytes());

		StringWriter errBuffer = new StringWriter();
		PrintWriter errorPrinter = new PrintWriter(errBuffer, true);

		ASTStatement statementAst = SoilCompiler.constructAST(input, "USE Api", errorPrinter, false);

		if (statementAst == null) {
			throw new UseApiException("Could not create operation. Syntax error in SOIL body:\n" + errBuffer.toString());
		}

		Context ctx = new Context("USE APi", errorPrinter, new VarBindings(), null);
		ctx.setModel(getModel());
		MStatement statement;

		try {
			statement = statementAst.generateStatement(ctx, op);
		} catch (CompilationFailedException e) {
			throw new UseApiException("Could not create operation:\n" + e.getMessage(), e);
		}

		op.setStatement(statement);
		return op;
	}

	/**
	 * Creates a new pre- or postcondition named {@code name} for the
	 * operation {@code operationName} of the class {@code ownerName}
	 * with the expression {@code condition}. The switch {@code isPre}
	 * is used to control whether a pre- or a postcondition is created.
	 *
	 * @param ownerName The class the operation is assigned to.
	 * @param operationName The name of the operation.
	 * @param name The name of the pre-/postcondition.
	 * @param condition The OCL-Expression of the condition.
	 * @param isPre Switch whether the condition is a precondition or not.
	 * @return The created {@link MPrePostCondition}.
	 * @throws UseApiException
	 */
	public MPrePostCondition createPrePostCondition(String ownerName,
			String operationName, String name, String condition, boolean isPre)
					throws UseApiException {
		MClass cls = getClassSafe(ownerName);
		MOperation op = cls.operation(operationName, false);

		if(op == null){
			throw new UseApiException("Unknown operation "
					+ StringUtil.inQuotes(ownerName + "::" + operationName)
					+ ".");
		}

		StringWriter errBuffer = new StringWriter();
		PrintWriter errorPrinter = new PrintWriter(errBuffer, true);

		Symtable symTable = new Symtable();
		try {
			symTable.add("self", cls, null);
			for(VarDecl var : op.paramList()){
				symTable.add(var.name(), var.type(), null);
			}
			if(op.hasResultType()){
				symTable.add("result", op.resultType(), null);
			}
		}
		catch(SemanticException ex){
			throw new UseApiException("Could not create pre-/postcondition.", ex);
		}

		Expression conditionExp = OCLCompiler.compileExpression(mModel,
				condition, "condition", errorPrinter, symTable);

		if (conditionExp == null) {
			throw new UseApiException(
					"Compilation of condition expression failed:\n"
							+ errBuffer.toString());
		}

		return createPrePostConditionEx(name, op, isPre, conditionExp);
	}

	/**
	 * Creates a new pre- or postcondition with the name {@code name} for
	 * the operation {@code op} with the expression {@code condition}.
	 * The switch {@code isPre} is used to control whether a pre- or a
	 * postcondition is created.
	 *
	 * @param name The name of the pre-/postcondition.
	 * @param op The operation the condition shall be assigned to.
	 * @param isPre Switch whether the condition is a precondition or not.
	 * @param condition The OCL-Expression of the condition.
	 * @return The created {@link MPrePostCondition}.
	 * @throws UseApiException
	 */
	public MPrePostCondition createPrePostConditionEx(String name,
			MOperation op, boolean isPre, Expression condition)
					throws UseApiException {

		MPrePostCondition cond;
		try {
			cond = mFactory.createPrePostCondition(name, op, isPre, condition);
			mModel.addPrePostCondition(cond);
		} catch (ExpInvalidException | MInvalidModelException ex) {
			throw new UseApiException("Could not create pre-/postcondition.", ex);
		}

		return cond;
	}

	/**
	 * This method creates a binary association class. The association class is
	 * a class and an association at once. The association class has a valid
	 * name <code>associationClassName</code> and is mark by the parameter name
	 * <code>isAbstract</code> if its an abstract class or not.
	 */
	public MAssociationClass createAssociationClass(String associationClassName, boolean isAbstract,
			String end1ClassName, String end1RoleName, String end1Multiplicity, int end1Aggregation,
			String end2ClassName, String end2RoleName, String end2Multiplicity, int end2Aggregation)
					throws UseApiException {

		return createAssociationClass(associationClassName, isAbstract,
				new String[] {end1ClassName, end2ClassName},
				new String[] {end1RoleName, end2RoleName},
				new String[] {end1Multiplicity, end2Multiplicity},
				new int[] {end1Aggregation, end2Aggregation});
	}

	/**
	 * This method creates an n-ary association class. The association class is
	 * a class and an association at once. The association class has a valid
	 * name <code>associationClassName</code> and is mark by the parameter name
	 * <code>isAbstract</code> if its an abstract class or not.
	 */
	public MAssociationClass createAssociationClass(String associationClassName, boolean isAbstract,
			String[] classNames, String[] roleNames, String[] multiplicities, int[] aggregationKinds) throws UseApiException {
		boolean[] orderedInfo = new boolean[classNames.length];
		Arrays.fill(orderedInfo, false);
		return createAssociationClass(associationClassName, isAbstract, classNames, roleNames, multiplicities, aggregationKinds, orderedInfo, new String[0][][]);
	}

	/**
	 * This method creates an n-ary association class. The association class is
	 * a class and an association at once. The association class has a valid
	 * name <code>associationClassName</code> and is mark by the parameter name
	 * <code>isAbstract</code> if its an abstract class or not.
	 * <p>
	 * For inheriting association classes you must use
	 * {@link #createAssociationClass(String, boolean, String[], String[], String[], String[], int[], boolean[], String[][][])}.
	 *
	 * @param qualifier A three dimensional array containing for each association end (dimension one)
	 *                  the qualifier information (dimension two) as a string array of length two (dimension three).
	 *                  The first element in the array of the third dimension is the name of the qualifier, the second
	 *                  element is the type.
	 */
	public MAssociationClass createAssociationClass(String associationClassName, boolean isAbstract,
			String[] classNames, String[] roleNames, String[] multiplicities, int[] aggregationKinds,
			boolean[] orderedInfo, String[][][] qualifier) throws UseApiException {
		return createAssociationClass(associationClassName, isAbstract, new String[0], classNames, roleNames, multiplicities, aggregationKinds, orderedInfo, qualifier);
	}
	
	/**
	 * This method creates an n-ary association class. The association class is
	 * a class and an association at once. The association class has a valid
	 * name <code>associationClassName</code> and is mark by the parameter name
	 * <code>isAbstract</code> if its an abstract class or not.
	 *
	 * @param parents   An array containing the name of all parents of this association class. These must be specified
	 *                  for the inheritance to work. Do not add these generalizations manually using
	 *                  {@link #createGeneralization(String, String)} later.
	 * @param qualifier A three dimensional array containing for each association end (dimension one)
	 *                  the qualifier information (dimension two) as a string array of length two (dimension three).
	 *                  The first element in the array of the third dimension is the name of the qualifier, the second
	 *                  element is the type.
	 */
	public MAssociationClass createAssociationClass(String associationClassName, boolean isAbstract, String[] parents,
			String[] classNames, String[] roleNames, String[] multiplicities, int[] aggregationKinds,
			boolean[] orderedInfo, String[][][] qualifier) throws UseApiException {
		int numEnds = classNames.length;

		if ( numEnds != roleNames.length ||
				numEnds != multiplicities.length ||
				numEnds != aggregationKinds.length ||
				numEnds != orderedInfo.length ||
				(qualifier.length > 0 && qualifier.length != numEnds)) {
			throw new UseApiException("The number of class names, role names, multiplicities and aggregation kinds must match.");
		}

		MAssociationClass associationClass = mFactory.createAssociationClass(associationClassName, isAbstract);

		try {
			for (int i = 0; i < numEnds; ++i) {
				associationClass.addAssociationEnd(createAssociationEnd(
						classNames[i], roleNames[i], multiplicities[i],
						aggregationKinds[i], orderedInfo[i], (qualifier.length == 0 ? new String[0][] : qualifier[i])));
			}

			mModel.addClass(associationClass);
			for(String p : parents){
				createGeneralization(associationClassName, p);
			}
			mModel.addAssociation(associationClass);
		} catch (MInvalidModelException e) {
			throw new UseApiException(e.getMessage(), e);
		}

		return associationClass;
	}

	/**
	 * This method creates a class invariant for the class given by <code>contextName</code>.
	 * The body expression <code>invBody</code> needs to be a boolean OCL expression.
	 * "Normal" invariants are validated for all instances of the context class when {@link UseSystemApi#checkState()}
	 * is called. If <code>isExistential</code> is <code>true</code>, the invariant
	 * checks if the body is <code>true</code>, for at least one instance (<code>exists</code> instead of <code>forAll</code>).
	 *
	 * @param invName An optional name for the invariant to create.
	 * @param contextName The name of the class to define the constraint on.
	 * @param invBody The body of the invariant.
	 * @param isExistential Should <code>forAll</code> or <code>exists</code> be used.
	 *
	 * @return MClassInvariant The new invariant added to the current model.
	 *
	 * @throws ApiException
	 *             If the type of the context name is unknown or not a class name,
	 *             the body expression is invalid or the invariant name is already used
	 *             for this class.
	 */
	public MClassInvariant createInvariant(String invName, String contextName,
			String invBody, boolean isExistential) throws UseApiException {

		MClass cls = getClassSafe(contextName);

		Symtable vars = new Symtable();
		try {
			vars.add("self", cls, new SrcPos("self", 1, 1));
		} catch (SemanticException e1) {
			throw new UseApiException("Could not add " + StringUtil.inQuotes("self") + " to symtable.", e1);
		}

		StringWriter errBuffer = new StringWriter();
		PrintWriter errorPrinter = new PrintWriter(errBuffer, true);

		Expression invExp = OCLCompiler.compileExpression(mModel, invBody, "UseApi", errorPrinter, vars);

		if (invExp == null) {
			throw new UseApiException(errBuffer.toString());
		}

		return createInvariantEx(invName, contextName, invExp, isExistential);
	}

	/**
	 * This method creates a class invariant for the class given by <code>contextName</code>.
	 * The body expression <code>invBody</code> needs to be a boolean OCL expression.
	 * "Normal" invariants are validated for all instances of the context class when {@link UseSystemApi#checkState()}
	 * is called. If <code>isExistential</code> is <code>true</code>, the invariant
	 * checks if the body is <code>true</code>, for at least one instance (<code>exists</code> instead of <code>forAll</code>).
	 *
	 * @param invName An optional name for the invariant to create.
	 * @param contextName The name of the class to define the constraint on.
	 * @param invBody The expression of the invariant.
	 * @param isExistential Should <code>forAll</code> or <code>exists</code> be used.
	 *
	 * @return MClassInvariant The new invariant added to the current model.
	 *
	 * @throws ApiException
	 *             If the type of the context name is unknown or not a class name,
	 *             the body expression is invalid or the invariant name is already used
	 *             for this class.
	 */
	public MClassInvariant createInvariantEx(String invName, String contextName,
			Expression invBody, boolean isExistential) throws UseApiException {
		MClass cls = getClassSafe(contextName);

		MClassInvariant mClassInvariant = null;
		try {
			mClassInvariant = mFactory.createClassInvariant(invName, null,
					cls, invBody, isExistential);

			mModel.addClassInvariant(mClassInvariant);
		} catch (ExpInvalidException e) {
			throw new UseApiException("Invalid invariant expression!", e);
		} catch (MInvalidModelException e) {
			throw new UseApiException("Invariant creation failed!", e);
		}

		return mClassInvariant;
	}

	/**
	 * This method creates a generalization relation two classes.
	 * The name of the parent class is provided by <code>parentName</code>.
	 * The name of the subclass is given by <code>childName</code>.
	 * @ocl.pre   Class.allInstances()->exists(name=childName) and  Class.allInstances()->exists(name=parentName)
	 * @param childName The name of the subclass
	 * @param parentName The name of the parent, i. e., general class
	 * @return The generalization instance
	 * @throws ApiException
	 *         If the class names are invalid.
	 */
	public MGeneralization createGeneralization(String childName, String parentName) throws UseApiException {

		MClass mChild = getClass(childName);
		MClass mParent = getClass(parentName);

		return createGeneralizationEx(mChild, mParent);
	}

	/**
	 * This method creates a generalization relation between two classes.
	 * The parent class is provided by <code>parent</code>.
	 * The subclass is given by <code>child</code>.
	 *
	 * @param child The subclass
	 * @param parent The general class
	 * @return The generalization instance
	 * @throws ApiException
	 *         If the class names are invalid.
	 */
	public MGeneralization createGeneralizationEx(MClass child, MClass parent) throws UseApiException {

		if (child.model() != mModel || parent.model() != mModel) {
			throw new UseApiException("The provided model elements must be in the model handled by the API instance!");
		}

		MGeneralization mGeneralization = mFactory.createGeneralization(child, parent);

		try {
			mModel.addGeneralization(mGeneralization);
		} catch (MInvalidModelException e) {
			throw new UseApiException("Creation of generalization failed!", e);
		}

		return mGeneralization;
	}

	/**
	 * Creates a new generalization relationship between the two associations.
	 * Note, that an association class can only inherit from another association class.
	 * @param child The more specific association
	 * @param parent The more general association
	 * @return
	 * @throws UseApiException If the inheritance relation is invalid.
	 */
	public MGeneralization createGeneralizationEx(MAssociation child, MAssociation parent) throws UseApiException {
		MGeneralization mGeneralization = mFactory.createGeneralization(child, parent);

		try {
			mModel.addGeneralization(mGeneralization);
		} catch (MInvalidModelException e) {
			throw new UseApiException("Creation of generalization failed!", e);
		}

		return mGeneralization;
	}


	public MGeneralization createGeneralizationEx(MAssociationClass child, MAssociationClass parent) throws UseApiException {
		MGeneralization mGeneralization = mFactory.createGeneralization(child, parent);

		try {
			mModel.addGeneralization(mGeneralization);
		} catch (MInvalidModelException e) {
			throw new UseApiException("Creation of generalization failed!", e);
		}

		return mGeneralization;
	}

	public MGeneralization createGeneralizationEx(MSignal child, MSignal parent) throws UseApiException {
		MGeneralization mGeneralization = mFactory.createGeneralization(child, parent);

		try {
			mModel.addGeneralization(mGeneralization);
		} catch (MInvalidModelException e) {
			throw new UseApiException("Creation of generalization failed!", e);
		}

		return mGeneralization;
	}

	/**
	 * This operation creates an association with the name
	 * <code>associationName</code>. The association connects two classes defined by
	 * the parameters <code>end1ClassName</code> and <code>end2ClassName</code>.
	 * For each end the following parameters must be provided (# must be replaced by the end number):
	 * <ul>
	 *  <li> end#ClassName: The name of the class at this end.</li>
	 *  <li> end#RoleName: The role name of the class at this end.</li>
	 *  <li> end#Multiplicity: The multiplicity at this end. Can be defined by using a range (1..*) or a single value (2).</li>
	 *  <li> end#Aggregation: The aggregation kind at this end (see {@link MAggregationKind}).</li>
	 * </ul>
	 * @param associationName The name of the association to create. Must be unique inside a single model.
	 * @param end1ClassName The name of the class at the first association end.
	 * @param end1RoleName The role name of the first association end.
	 * @param end1Multiplicity The multiplicity specification (1..*, 0..*, etc.) of the first association end.
	 * @param end1Aggregation The aggregation kind ({@link MAggregationKind}) of the first association end.
	 * @param end2ClassName The name of the class at the second association end.
	 * @param end2RoleName The role name of the second association end.
	 * @param end2Multiplicity The multiplicity specification (1..*, 0..*, etc.) of the second association end.
	 * @param end2Aggregation The aggregation kind ({@link MAggregationKind}) of the second association end.
	 *
	 * @return The new association as an instance of the meta-class {@link MAssociation}.
	 * @throws ApiException
	 *             If the association name is empty or already defined.
	 */
	public MAssociation createAssociation(String associationName,
			String end1ClassName, String end1RoleName, String end1Multiplicity, int end1Aggregation,
			String end2ClassName, String end2RoleName, String end2Multiplicity, int end2Aggregation) throws UseApiException {

		return createAssociation(associationName,
				new String[]  {end1ClassName,  end2ClassName},
				new String[]  {end1RoleName, end2RoleName},
				new String[]  {end1Multiplicity, end2Multiplicity},
				new int[]     {end1Aggregation, end2Aggregation},
				new boolean[] {false, false},
				new String[][][]{});
	}

	/**
	 *
	 * @param associationName
	 * @param classNames
	 * @param roleNames
	 * @param multiplicities
	 * @param aggregationKinds
	 * @param orderedInfo
	 * @param qualifier A three dimensional array containing for each association end (dimension one)
	 *                  the qualifier information (dimension two) as a string array of length two (dimension three).
	 *                  The first element in the array of the third dimension is the name of the qualifier, the second
	 *                  element is the type.
	 * @return The new association
	 * @throws UseApiException
	 */
	public MAssociation createAssociation(String associationName, String[] classNames, String[] roleNames,
			String[] multiplicities, int[] aggregationKinds, boolean[] orderedInfo,
			String[][][] qualifier) throws UseApiException {
		if (associationName == null || associationName.equals("")) {
			throw new UseApiException("Asssociations must be named!");
		}

		int numEnds = classNames.length;

		if (numEnds != roleNames.length ||
				numEnds != multiplicities.length ||
				numEnds != aggregationKinds.length ||
				numEnds != orderedInfo.length ||
				(qualifier.length > 0 && qualifier.length != numEnds)) {
			throw new UseApiException("All association end information must be provided for each association end.");
		}

		MAssociation assoc = mFactory.createAssociation(associationName);
		MAssociationEnd end;

		try {
			for (int i = 0; i < numEnds; ++i) {
				end = createAssociationEnd(classNames[i],
						roleNames[i],
						multiplicities[i],
						aggregationKinds[i],
						orderedInfo[i],
						(qualifier.length == 0 ? new String[0][] : qualifier[i]));

				assoc.addAssociationEnd(end);
			}

			mModel.addAssociation(assoc);
		} catch (MInvalidModelException e) {
			throw new UseApiException("Association creation failed", e);
		}

		return assoc;
	}

	public MAssociation createAssociationEx(String associationName, MClass[] classes, String[] roleNames,
			String[] multiplicities, int[] aggregationKinds, boolean[] orderedInfo,
			String[][][] qualifier) throws UseApiException {

		return null;
	}

	/**
	 * Helper to create associations ends.
	 * @param endClassName
	 * @param endRoleName
	 * @param endMultiplicity
	 * @param endAggregation
	 * @return The MAssociationEnd instance
	 * @throws UseApiException
	 */
	private MAssociationEnd createAssociationEnd(String endClassName,
			String endRoleName, String endMultiplicity, int endAggregation, boolean isOrdered, String[][] qualifier)
					throws UseApiException {
		if (!MAggregationKind.isValid(endAggregation)) {
			throw new UseApiException("Invalid aggregation specified for association end 1.");
		}

		MClass classEnd = getClassSafe(endClassName);

		MMultiplicity m = USECompiler.compileMultiplicity(endMultiplicity,
				"Use Api", NullPrintWriter.getInstance(), mFactory);

		List<VarDecl> qualifierDecl;

		if (qualifier.length > 0) {
			qualifierDecl = new ArrayList<VarDecl>(qualifier.length);
			for (int i = 0; i < qualifier.length; ++i) {
				if (qualifier[i].length != 2) {
					throw new UseApiException("Qualifiers must be defined with a name and a type");
				}

				Type t = getType(qualifier[i][1]);
				qualifierDecl.add(new VarDecl(qualifier[i][0], t));
			}
		} else {
			qualifierDecl = Collections.emptyList();
		}

		MAssociationEnd end = new MAssociationEnd(classEnd, endRoleName, m, endAggregation, isOrdered, qualifierDecl);
		return end;
	}

	public void createRedefineConstraint(String childAssociation, String redefiningEnd, String redefinedEnd) {

	}

	public void createRedefineConstraintEx(MAssociationEnd redefiningEnd, MAssociationEnd redefinedEnd) {

	}

	/**
	 * Helper method to safely retrieve a class.
	 * Safe by the degree, that if no exception is thrown you get a valid class
	 * instance. In contrast to the need to handle <code>null</code> as a return value.
	 * @param className The name of the class to lookup.
	 * @return The {@link MClass} with the name <code>className</code>.
	 * @throws UseApiException If no class with the given name exists in the encapsulated model.
	 */
	public MClass getClassSafe(String className) throws UseApiException {
		MClass cls = mModel.getClass(className);

		if (cls == null) {
			throw new UseApiException("Unknown class " + StringUtil.inQuotes(className));
		}

		return cls;
	}

	/**
	 * Compiles the type expression <code>typeExpr</code> to
	 * a USE type ({@link Type}).
	 *
	 * @param typeExpr The type expression, e. g., <code>Integer</code> or <code>Set(Person)</code>.
	 * @return The internal representation of the type.
	 * @throws UseApiException If an invalid type expression was provided.
	 */
	public Type getType(String typeExpr) throws UseApiException {
		Type type;
		type = OCLCompiler.compileType(mModel, typeExpr, "UseApi", NullPrintWriter.getInstance());

		if (type == null) {
			throw new UseApiException("Invalid type expression "
					+ StringUtil.inQuotes(typeExpr) + ".");
		}

		return type;
	}

	/**
	 * Creates a new instance of the signal meta class.
	 * @param name The name of the signal.
	 * @param isAbstract <code>true</code> if the signal is abstract, i.e., it cannot be instantiated.
	 * @return
	 */
	public MSignal createSignal(String name, boolean isAbstract) throws UseApiException {
		MSignal signal = mFactory.createSignal(name, isAbstract);

		try {
			mModel.addSignal(signal);
			signal.setModel(mModel);
		} catch (MInvalidModelException e) {
			throw new UseApiException("Error during signal creation.", e);
		}

		return signal;
	}

	/**
	 * Creates a new attribute for the given signal <code>s</code>.
	 * @param owningSignal The signal to add the attribute to.
	 * @param attributeName The name of the attribute to create.
	 * @param attributeType The type of the new attribute.
	 * @throws UseApiException If creation fails (see cause).
	 */
	public MAttribute createAttributeEx(MSignal owningSignal, String attributeName, String attributeType) throws UseApiException {

		Type t = getType(attributeType);

		MAttribute attr = mFactory.createAttribute(attributeName, t);

		try {
			owningSignal.addAttribute(attr);
		} catch (MInvalidModelException e) {
			throw new UseApiException("Error during attribute creation.", e);
		}

		return attr;
	}
}
