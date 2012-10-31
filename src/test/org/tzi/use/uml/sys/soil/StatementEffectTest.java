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
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.tzi.use.TestSystem;
import org.tzi.use.api.UseSystemApi;
import org.tzi.use.parser.shell.ShellCommandCompiler;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.ppcHandling.SoilPPCHandler;
import org.tzi.use.util.NullPrintWriter;
import org.tzi.use.util.soil.VariableEnvironment;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class StatementEffectTest extends TestCase {
	/** TODO */
	private TestSystem fTestSystem;
	/** TODO */
	private UseSystemApi systemApi;
	/** TODO */
	private MSystemState fState;
	/** TODO */
	private MSystemState fOldState;
	/** TODO */
	private VariableEnvironment fOldVarEnv;
	
	
	/**
	 * TODO
	 * @throws Exception
	 */
	@Before
	@Override
	public void setUp() throws MInvalidModelException, MSystemException, ExpInvalidException {
		fTestSystem = new TestSystem();
		systemApi = UseSystemApi.create(fTestSystem.getSystem());
		fState = fTestSystem.getState();
		fOldState = new MSystemState("oldState", fState);
		fOldVarEnv = new VariableEnvironment(fTestSystem.getVarEnv());
	}
	
	
	/**
	 * TODO
	 * @throws Exception 
	 */
	private void reset() throws MSystemException {
		fTestSystem.reset();
		fState = fTestSystem.getState();
		fOldState = new MSystemState("oldState", fState);
		fOldVarEnv = new VariableEnvironment(fTestSystem.getVarEnv());
	}
	
	
	/**
	 * TODO
	 * @throws MSystemException 
	 */
	@Test
	public void testVariableAssignment() throws MSystemException {
		
		////////////////////////////////
		// simple variable assignment //
		////////////////////////////////
		
		String statement;
		IntegerValue val1 = IntegerValue.valueOf(42);
		String varName = "x";
		
		// variable not existent before
		assertNull(lookUpVar(varName));
		
		statement = varName + " := " + val1;
		evaluateStatement(statement);
		
		// 1 new variable
		List<String> newVars = getNewVars();
		assertEquals(1, newVars.size());
		assertTrue(getNewVars().contains(varName));
		// with the desired value
		assertEquals(lookUpVar(varName), val1);
		
		//////////
		// undo //
		//////////
		
		IntegerValue val2 = IntegerValue.valueOf(43);
		
		// overwrite variable
		statement = varName + " := " + val2;
		evaluateStatement(statement);
		
		// variable has new value
		assertEquals(lookUpVar(varName), val2);
		
		undo();
		
		// old value should be restored
		assertEquals(lookUpVar(varName), val1);
		
		//////////
		// redo //
		//////////
		
		redo();
		
		// new value should be restored
		assertEquals(lookUpVar(varName), val2);
		
		//////////////////////////////
		// assignment of new object //
		//////////////////////////////
		
		String className = "C1";
		MObject newObject;
		Value varVal;
		
		reset();
		// variable not existent
		assertNull(lookUpVar(varName));
		
		statement = varName + " := new " + className;
		evaluateStatement(statement);
		
		// 2 new variables
		assertEquals(1, getNewVars().size());
		// explicit variable refers to object
		assertNotNull(lookUpVar(varName));
		varVal = lookUpVar(varName);
		assertTrue(varVal instanceof ObjectValue);
		newObject = ((ObjectValue)varVal).value();
		// new object did not exist before
		assertFalse(fOldState.hasObjectWithName(newObject.name()));
		// new object is of desired class
		assertEquals(newObject.cls().name(), className);
		
		///////////////////////////////////
		// assignment of new link object //
		///////////////////////////////////
		
		String assocClassName = "AC1";
		MLinkObject newLinkObject;
		String objName1 = "o1";
		String objName2 = "o2";
		
		reset();
		// variable not existent
		assertNull(lookUpVar(varName));
		
		statement = 
			varName +
			" := new " +
			assocClassName +
			" between (" +
			objName1 +
			", " +
			objName2 +
			")";
		
		evaluateStatement(statement);
		// 1 new variable
		assertEquals(1, getNewVars().size());
		// explicit variable referring to the new link object
		varVal = lookUpVar(varName);
		assertNotNull(varVal);
		assertTrue(varVal instanceof ObjectValue);
		assertTrue(((ObjectValue)varVal).value() instanceof MLinkObject);
		newLinkObject = (MLinkObject)((ObjectValue)varVal).value();
		assertEquals(newLinkObject.cls().name(), assocClassName);
		// object did not exist before
		assertFalse(fOldState.hasObjectWithName(newLinkObject.name()));
		
		////////////////////////////////
		// assignment of return value //
		////////////////////////////////
		
		String opCallObjectName = "o1";
		String opName = "s1";
		String paramString = "0";
		int expectedResultValue = 42;
		
		reset();
		assertNull(lookUpVar(varName));
		statement = 
			varName +
			" := " +
			opCallObjectName +
			"." +
			opName +
			"(" +
			paramString +
			")";
		
		evaluateStatement(statement);
		// 1 new variable with the expected value
		assertEquals(getNewVars().size(), 1);
		assertNotNull(lookUpVar(varName));
		varVal = lookUpVar(varName);
		assertTrue(varVal instanceof IntegerValue);
		assertEquals(((IntegerValue)varVal).value(), expectedResultValue);

	}
	
	
	/**
	 * TODO
	 * @throws MSystemException 
	 */
	@Test
	public void testAttributeAssignment() throws MSystemException {
		
		////////////////////////////////
		// basic attribute assignment //
		////////////////////////////////
		
		String objVarName = "o1";
		String attName = "int";
		int attVal = 42;
		
		String statement =
			objVarName + 
			"." +
			attName +
			" := " +
			attVal;
		
		MObject object = getObjectByVarName(objVarName);
		assertNotNull(object);
		
		evaluateStatement(statement);
		
		Value newAttVal = getAttVal(object, attName);
		assertEquals(newAttVal, IntegerValue.valueOf(attVal));
		
		int attVal2 = 43;
		
		statement =
			objVarName + 
			"." +
			attName +
			" := " +
			attVal2;
		
		evaluateStatement(statement);
		
		newAttVal = getAttVal(object, attName);
		assertEquals(newAttVal, IntegerValue.valueOf(attVal2));
		
		//////////
		// undo //
		//////////
		
		undo();
		
		newAttVal = getAttVal(object, attName);
		assertEquals(newAttVal, IntegerValue.valueOf(attVal));
		
		//////////
		// redo //
		//////////
		
		redo();
		
		newAttVal = getAttVal(object, attName);
		assertEquals(newAttVal, IntegerValue.valueOf(attVal2));
	}


	/**
	 * TODO
	 * @throws MSystemException 
	 */
	@Test
	public void testObjectCreation() throws MSystemException {
		
		///////////////////////////
		// basic object creation //
		///////////////////////////
		
		String statement;
		String className = "C1";
		
		statement = "new " + className;
		evaluateStatement(statement);
		
		// 1 new object of expected class
		List<MObject> newObjects = getNewObjects();
		assertEquals(newObjects.size(), 1);
		MObject newObject = newObjects.get(0);
		assertEquals(newObject.cls().name(), className);
		// there is a variable with the same name as the object name
		assertEquals(0, getNewVars().size());
		String objectName = newObject.name();
		Value varVal = lookUpVar(objectName);
		assertNotNull(varVal);
		// and it points to the new object
		assertEquals(varVal, newObject.value());		
		
		//////////
		// undo //
		//////////
		
		undo();
		
		// new object is gone
		assertTrue(getNewObjects().isEmpty());
		// variable is gone
		assertNull(lookUpVar(objectName));
		
		//////////
		// redo //
		//////////
		
		redo();
		
		assertEquals(newObjects.size(), 1);
		newObject = newObjects.get(0);
		// object is of class C1
		assertEquals(newObject.cls().name(), className);
		// and has the same name as in the previous run
		assertEquals(newObject.name(), objectName);
		// the variable should be back
		varVal = lookUpVar(objectName);
		assertNotNull(varVal);
		// and it points to the new object
		assertEquals(varVal, newObject.value());

		////////////////////////////////////////////////
		// object creation with mandatory object name //
		////////////////////////////////////////////////
		
		String name1 = "a";
		
		reset();
		
		statement = "new " + className + "('" + name1 + "')";
		evaluateStatement(statement);
		
		// 1 new variable
		assertEquals(0, getNewVars().size());
		newObjects = getNewObjects();
		// 1 new object
		assertEquals(newObjects.size(), 1);
		newObject = newObjects.get(0);
		// object is of class C1
		assertEquals(newObject.cls().name(), className);
		// objects name is the mandatory name
		assertEquals(newObject.name(), name1);
		// new variable with that name exists
		varVal = lookUpVar(name1);
		assertNotNull(varVal);
		// and it points to the new object
		assertEquals(varVal, newObject.value());

		////////////////////////
		// variable shadowing //
		////////////////////////
		
		int intVal = 42;
		
		reset();
		
		// 2 consecutive statements
		statement = 
			"new " + 
			className + 
			"('" + 
			name1 + 
			"')" +
			"; " +
			name1 + 
			" := " + 
			intVal;
		
		evaluateStatement(statement);
		
		// 1 new variable
		assertEquals(getNewVars().size(), 1);
		newObjects = getNewObjects();
		// 1 new object
		assertEquals(newObjects.size(), 1);
		newObject = newObjects.get(0);
		// of the declared class
		assertEquals(newObject.cls().name(), className);
		// with the mandatory name
		assertEquals(newObject.name(), name1);
		// but its variable is shadowed
		varVal = lookUpVar(name1);
		assertEquals(((IntegerValue)varVal).value(), intVal);
		
		// order of the sequence should not matter
		
		reset();
		
		// 2 consecutive statements
		statement = 
			name1 + 
			" := " + 
			intVal + 
			"; " +
			"new " + 
			className + 
			"('" + 
			name1 + 
			"')";
		
		evaluateStatement(statement);
		
		// 1 new variable
		assertEquals(getNewVars().size(), 1);
		newObjects = getNewObjects();
		// 1 new object
		assertEquals(newObjects.size(), 1);
		newObject = newObjects.get(0);
		// of the declared class
		assertEquals(newObject.cls().name(), className);
		// with the mandatory name
		assertEquals(newObject.name(), name1);
		// but its variable is shadowed
		varVal = lookUpVar(name1);
		assertEquals(((IntegerValue)varVal).value(), 42);
	}
	
	
	/**
	 * TODO
	 * @throws MSystemException 
	 */
	@Test
	public void testLinkObjectCreation() throws MSystemException {
		
		////////////////////////////////
		// basic link object creation //
		////////////////////////////////
		
		String statement;
		
		String assClassName = "AC1";
		String pName1 = "o1";
		String pName2 = "o2";
		
		// the objects we're about to link should exist...
		assertNotNull(lookUpVar(pName1));
		assertTrue(lookUpVar(pName1) instanceof ObjectValue);
		MObject linkedObject1 = ((ObjectValue)lookUpVar(pName1)).value();
		assertNotNull(lookUpVar(pName2));
		assertTrue(lookUpVar(pName2) instanceof ObjectValue);
		MObject linkedObject2 = ((ObjectValue)lookUpVar(pName2)).value();
		
		statement = 
			"new " + 
			assClassName + 
			" between " +
			"(" + 
			pName1 + "," + 
			pName2 + 
			")";
		
		evaluateStatement(statement);
		
		assertEquals(0, getNewVars().size());
		List<MObject> newObjects = getNewObjects();
		// 1 new object
		assertEquals(1, newObjects.size());
		MObject newObject = newObjects.get(0);
		// must be a link object
		assertTrue(newObject instanceof MLinkObject);
		MLinkObject newLinkObject = (MLinkObject)newObject;
		// association must be AC1
		assertEquals(newLinkObject.association().name(), assClassName);
		// 1 new link
		List<MLink> newLinks = getNewLinks();
		assertEquals(newLinks.size(), 1);
		MLink newLink = newLinks.get(0);
		// must be the link object
		assertSame(newLink, newObject);
		// o1 and o2 must be the linked object
		assertEquals(newLinkObject.linkedObjects().size(), 2);
		assertTrue(newLinkObject.linkedObjects().contains(linkedObject1));
		assertTrue(newLinkObject.linkedObjects().contains(linkedObject2));
		String linkObjectName = newLinkObject.name();
		// there must be a variable with the same name as the link object
		Value varVal = lookUpVar(linkObjectName);
		assertNotNull(varVal);
		// which refers to the new link object
		assertEquals(varVal, newLinkObject.value());
		
		undo();
		
		// should remove the new object, the new link and the variable
		assertTrue(getNewObjects().isEmpty());
		assertTrue(getNewLinks().isEmpty());
		assertNull(lookUpVar(linkObjectName));
		
		//////////
		// redo //
		//////////
		
		redo();
		
		assertEquals(0, getNewVars().size());
		newObjects = getNewObjects();
		assertEquals(1, newObjects.size());
		newObject = newObjects.get(0);
		// must have the same name again
		assertEquals(newObject.name(), linkObjectName);
		// must be a link object
		assertTrue(newObject instanceof MLinkObject);
		newLinkObject = (MLinkObject)newObject;
		// association must be AC1
		assertEquals(newLinkObject.association().name(), assClassName);
		// 1 new link
		newLinks = getNewLinks();
		assertEquals(newLinks.size(), 1);
		newLink = newLinks.get(0);
		// must be the link object
		assertSame(newLink, newObject);
		// o1 and o2 must be the linked object
		assertEquals(newLinkObject.linkedObjects().size(), 2);
		assertTrue(newLinkObject.linkedObjects().contains(linkedObject1));
		assertTrue(newLinkObject.linkedObjects().contains(linkedObject2));
		// there must be a variable with the same name as the link object
		varVal = lookUpVar(linkObjectName);
		assertNotNull(varVal);
		// which refers to the new link object
		assertEquals(varVal, newLinkObject.value());
		
		//////////////////////////////////////////////////////////
		// link object creation with mandatory link object name //
		//////////////////////////////////////////////////////////
		
		reset();
		
		String mandatoryName = "a";
		
		statement = 
			"new " + assClassName + "('" + mandatoryName + "') " + 
			"between (" + pName1 + "," + pName2 + ")";
		
		evaluateStatement(statement);
		
		assertEquals(0, getNewVars().size());
		newObjects = getNewObjects();
		assertEquals(newObjects.size(), 1);
		newObject = newObjects.get(0);	
		// must be a link object
		assertTrue(newObject instanceof MLinkObject);
		newLinkObject = (MLinkObject)newObject;
		// name must be the mandatory name
		assertEquals(newLinkObject.name(), mandatoryName);
		// association must be AC1
		assertEquals(newLinkObject.association().name(), assClassName);
		// 1 new link
		newLinks = getNewLinks();
		assertEquals(newLinks.size(), 1);
		newLink = newLinks.get(0);
		// must be the link object
		assertSame(newLink, newObject);
		// o1 and o2 must be the linked object
		assertEquals(newLinkObject.linkedObjects().size(), 2);
		assertTrue(newLinkObject.linkedObjects().contains(linkedObject1));
		assertTrue(newLinkObject.linkedObjects().contains(linkedObject2));
		// there must be a variable with the mandatory name
		varVal = lookUpVar(mandatoryName);
		assertNotNull(varVal);
		// which refers to the new link object
		assertEquals(varVal, newLinkObject.value());
		
		//////////////////////////////////////////////////
		// link object creation between two new objects //
		//////////////////////////////////////////////////
		
		reset();
		
		String pClass1 = "C1";
		String pClass2 = "C2";
		String pMName1 = "b";
		String pMName2 = "c";
		
		statement = 
				"new " + assClassName + "('" + mandatoryName + "') " +
				"between ("  +
				"new " + pClass1 + "('" + pMName1 + "'), " +
				"new " + pClass2 + "('" + pMName2 + "')" +
				")";
		
		
		// variable names should be free
		assertNull(lookUpVar(pMName1));
		assertNull(lookUpVar(pMName2));
		
		evaluateStatement(statement);
		
		// no new variables
		List<String> newVars = getNewVars();
		assertEquals(0, newVars.size());
		assertFalse(newVars.contains(mandatoryName));
		assertFalse(newVars.contains(pMName1));
		assertFalse(newVars.contains(pMName2));		
		// 3 new objects
		newObjects = getNewObjects();
		assertEquals(3, newObjects.size());
		linkedObject1 = getObject(pMName1);
		assertTrue(newObjects.contains(linkedObject1));
		assertEquals(linkedObject1.cls().name(), pClass1);
		linkedObject2 = getObject(pMName2);
		assertTrue(newObjects.contains(linkedObject2));
		assertEquals(linkedObject2.cls().name(), pClass2);
		newObject = getObject(mandatoryName);
		assertTrue(newObject instanceof MLinkObject);
		newLinkObject = (MLinkObject)newObject;
		assertEquals(newLinkObject.association().name(), assClassName);
		assertEquals(newLinkObject.linkedObjects().size(), 2);
		assertTrue(newLinkObject.linkedObjects().contains(linkedObject1));
		assertTrue(newLinkObject.linkedObjects().contains(linkedObject2));
		// 1 new link
		newLinks = getNewLinks();
		assertEquals(newLinks.size(), 1);
		newLinks = getNewLinks();
		assertEquals(newLinks.size(), 1);
		newLink = newLinks.get(0);
		// must be the link object
		assertSame(newLink, newObject);
		// variables are assigned correctly
		assertEquals(linkedObject1.value(), lookUpVar(pMName1));
		assertEquals(linkedObject2.value(), lookUpVar(pMName2));
		assertEquals(newLinkObject.value(), lookUpVar(mandatoryName));
		
		//////////
		// undo //
		//////////
		
		undo();
		
		// no new variables
		assertTrue(getNewVars().isEmpty());
		// no new objects
		assertTrue(getNewObjects().isEmpty());
		// no new links
		assertTrue(getNewLinks().isEmpty());
		
		//////////
		// redo //
		//////////
		
		redo();
		
		// everything should be the same again
		
		// 3 new variables
		newVars = getNewVars();
		assertEquals(0, newVars.size());
		assertFalse(newVars.contains(mandatoryName));
		assertFalse(newVars.contains(pMName1));
		assertFalse(newVars.contains(pMName2));		
		// 3 new objects
		newObjects = getNewObjects();
		assertEquals(newObjects.size(), 3);
		linkedObject1 = getObject(pMName1);
		assertTrue(newObjects.contains(linkedObject1));
		assertEquals(linkedObject1.cls().name(), pClass1);
		linkedObject2 = getObject(pMName2);
		assertTrue(newObjects.contains(linkedObject2));
		assertEquals(linkedObject2.cls().name(), pClass2);
		newObject = getObject(mandatoryName);
		assertTrue(newObject instanceof MLinkObject);
		newLinkObject = (MLinkObject)newObject;
		assertEquals(newLinkObject.association().name(), assClassName);
		assertEquals(newLinkObject.linkedObjects().size(), 2);
		assertTrue(newLinkObject.linkedObjects().contains(linkedObject1));
		assertTrue(newLinkObject.linkedObjects().contains(linkedObject2));
		// 1 new link
		newLinks = getNewLinks();
		assertEquals(newLinks.size(), 1);
		newLinks = getNewLinks();
		assertEquals(newLinks.size(), 1);
		newLink = newLinks.get(0);
		// must be the link object
		assertSame(newLink, newObject);
		// variables are assigned correctly
		assertEquals(linkedObject1.value(), lookUpVar(pMName1));
		assertEquals(linkedObject2.value(), lookUpVar(pMName2));
		assertEquals(newLinkObject.value(), lookUpVar(mandatoryName));
	}
	
	
	/**
	 * TODO
	 * @throws MSystemException 
	 */
	@Test
	public void testObjectDestruction() throws MSystemException {
		
		//////////////////////////////
		// basic object destruction //
		//////////////////////////////
		
		String vNameObj1 = "o1";
		
		assertNotNull(lookUpVar(vNameObj1));
		MObject object1 = getObjectByVarName(vNameObj1);
		String oNameObj1 = object1.name();
		assertNotNull(object1);
		
		String statement = "destroy " + vNameObj1;
		
		evaluateStatement(statement);
		
		// object should be gone
		assertNull(getObject(oNameObj1));
		
		// variable should be undefined, but still exist
		Value varVal1 = lookUpVar(vNameObj1);
		assertNotNull(varVal1);
		assertTrue(varVal1.type().isSubtypeOf(fOldVarEnv.lookUp(vNameObj1).type()));
		assertTrue(varVal1.isUndefined());
		
		//////////
		// undo //
		//////////
		
		undo();
		
		// object should be back
		MObject restoredObject = getObjectByVarName(vNameObj1);
		assertNotNull(restoredObject);
		assertSame(object1, restoredObject);
		
		// variable should be restored
		varVal1 = lookUpVar(vNameObj1);
		assertNotNull(varVal1);
		assertEquals(varVal1, restoredObject.value());
		
		//////////
		// redo //
		//////////
		
		redo();
		
		// object should be gone
		assertNull(getObjectByVarName(vNameObj1));
		
		// variable should be undefined, but still exist
		varVal1 = lookUpVar(vNameObj1);
		assertNotNull(varVal1);
		assertTrue(varVal1.type().isSubtypeOf(fOldVarEnv.lookUp(vNameObj1).type()));
		assertTrue(varVal1.isUndefined());
		
		/////////////////////////////////////
		// destruction of multiple objects //
		/////////////////////////////////////
		
		reset();
		
		String vNameObj2 = "o2";
		
		assertNotNull(lookUpVar(vNameObj1));
		assertNotNull(lookUpVar(vNameObj2));
		object1 = getObjectByVarName(vNameObj1);
		oNameObj1 = object1.name();
		assertNotNull(object1);
		MObject object2 = getObjectByVarName(vNameObj2);
		String oNameObj2 = object2.name();
		assertNotNull(object2);
		
		statement = 
			"destroy " + 
			vNameObj1 + ", " +
			vNameObj2;
		
		evaluateStatement(statement);
		
		// both objects should be gone
		assertNull(getObject(oNameObj1));
		assertNull(getObject(oNameObj2));
		// variables should undefined
		varVal1 = lookUpVar(vNameObj1);
		assertNotNull(varVal1);
		assertTrue(varVal1.type().isSubtypeOf(fOldVarEnv.lookUp(vNameObj1).type()));
		assertTrue(varVal1.isUndefined());
		Value varVal2 = lookUpVar(vNameObj2);
		assertNotNull(varVal2);
		assertTrue(varVal2.type().isSubtypeOf(fOldVarEnv.lookUp(vNameObj2).type()));
		assertTrue(varVal2.isUndefined());
		
		///////////////////////////////////////////////
		// destruction of a static object collection //
		///////////////////////////////////////////////
		
		reset();
		
		statement = 
			"destroy " +
			"Set{" +
			vNameObj1 + ", " +
			vNameObj2 +
			"}";
		
		evaluateStatement(statement);
		
		// both objects should be gone
		assertNull(getObject(oNameObj1));
		assertNull(getObject(oNameObj2));
		// variables should undefined
		varVal1 = lookUpVar(vNameObj1);
		assertNotNull(varVal1);
		assertTrue(varVal1.type().isSubtypeOf(fOldVarEnv.lookUp(vNameObj1).type()));
		assertTrue(varVal1.isUndefined());
		varVal2 = lookUpVar(vNameObj2);
		assertNotNull(varVal2);
		assertTrue(varVal2.type().isSubtypeOf(fOldVarEnv.lookUp(vNameObj2).type()));
		assertTrue(varVal2.isUndefined());
		
		//////////////////////////////////////////////
		// destruction of dynamic object collection //
		//////////////////////////////////////////////
		
		reset();
		
		String objClassName = "C1";
		
		List<MObject> objectsToDestroy = new ArrayList<MObject>();
		
		for (MObject o : fState.allObjects()) {
			if (o.cls().name().equals(objClassName)) {
				objectsToDestroy.add(o);
			}
		}
		
		assertFalse(objectsToDestroy.isEmpty());
		
		statement = 
			"destroy " +
			objClassName + 
			".allInstances";
		
		evaluateStatement(statement);
		
		// all objects of chosen class destroyed
		for (MObject o : objectsToDestroy) {
			assertFalse(fState.hasObjectWithName(o.name()));
		}
		
		//////////
		// undo //
		//////////
		
		undo();
		
		// all destroyed objects restored
		for (MObject o : objectsToDestroy) {
			assertTrue(fState.hasObjectWithName(o.name()));
		}
		
		
		
		// TODO
		// link object destruction?
	}
	
	
	/**
	 * TODO
	 * @throws MSystemException 
	 */
	@Test
	public void testLinkInsertion() throws MSystemException {
		
		//////////////////////////
		// basic link insertion //
		//////////////////////////
		
		String statement;
		String assocName = "A1";
		String pName1 = "o1";
		String pName2 = "o2";
		
		statement =
			"insert (" +
			pName1 + ", " +
			pName2 +
			") into " +
			assocName;
		
		// objects we want to link should exist...
		MObject linkedObject1 = getObjectByVarName(pName1);
		assertNotNull(linkedObject1);
		MObject linkedObject2 = getObjectByVarName(pName2);
		assertNotNull(linkedObject2);
		assertFalse(fState.hasLinkBetweenObjects(
				fTestSystem.getSystem().model().getAssociation(assocName),
				linkedObject1,
				linkedObject2));
		
		evaluateStatement(statement);
		
		// 1 new link
		List<MLink> newLinks = getNewLinks();
		assertEquals(newLinks.size(), 1);
		MLink newLink = newLinks.get(0);
		// of the correct association
		assertEquals(newLink.association().name(), assocName);
		// linking the correct objects
		List<MObject> linkedObjects = newLink.linkedObjects();
		assertEquals(linkedObjects.size(), 2);
		assertTrue(linkedObjects.contains(linkedObject1));
		assertTrue(linkedObjects.contains(linkedObject2));
		
		//////////
		// undo //
		//////////
		
		undo();
		
		// link is gone
		newLinks = getNewLinks();
		assertTrue(newLinks.isEmpty());
		assertFalse(fState.allLinks().contains(newLink));
		
		//////////
		// redo //
		//////////
		
		redo();
		
		// 1 new link
		newLinks = getNewLinks();
		assertEquals(newLinks.size(), 1);
		newLink = newLinks.get(0);
		// of the correct association
		assertEquals(newLink.association().name(), assocName);
		// linking the correct objects
		linkedObjects = newLink.linkedObjects();
		assertEquals(linkedObjects.size(), 2);
		assertTrue(linkedObjects.contains(linkedObject1));
		assertTrue(linkedObjects.contains(linkedObject2));
		
		/////////////////////////////////////////////
		// link object creation via link insertion //
		/////////////////////////////////////////////
		
		reset();
		
		String assocClassName = "AC1";
		
		statement =
			"insert (" +
			pName1 + ", " +
			pName2 +
			") into " +
			assocClassName;
		
		// objects we want to link should exist...
		linkedObject1 = getObjectByVarName(pName1);
		assertNotNull(linkedObject1);
		linkedObject2 = getObjectByVarName(pName2);
		assertNotNull(linkedObject2);
		assertFalse(fState.hasLinkBetweenObjects(
				fTestSystem.getSystem().model().getAssociation(assocClassName),
				linkedObject1,
				linkedObject2));
		
		evaluateStatement(statement);
		
		// 1 new link
		newLinks = getNewLinks();
		assertEquals(newLinks.size(), 1);
		newLink = newLinks.get(0);
		// link object!
		assertTrue(newLink instanceof MLinkObject);
		assertEquals(getNewObjects().size(), 1);
		// of the correct association
		assertEquals(newLink.association().name(), assocClassName);
		// linking the correct objects
		linkedObjects = newLink.linkedObjects();
		assertEquals(linkedObjects.size(), 2);
		assertTrue(linkedObjects.contains(linkedObject1));
		assertTrue(linkedObjects.contains(linkedObject2));
	}
	
	
	/**
	 * TODO
	 * @throws MSystemException 
	 */
	@Test
	public void testLinkDeletion() throws MSystemException {
		
		/////////////////////////
		// basic link deletion //
		/////////////////////////
		
		String statement;
		String assocName = "A1";
		String pName1 = "o3";
		String pName2 = "o4";
		
		statement =
			"delete (" +
			pName1 + ", " +
			pName2 +
			") from " +
			assocName;
		
		// objects we want to link should exist...
		MObject linkedObject1 = getObjectByVarName(pName1);
		assertNotNull(linkedObject1);
		MObject linkedObject2 = getObjectByVarName(pName2);
		assertNotNull(linkedObject2);
		// link should exist
		assertNotNull(getLink(assocName, linkedObject1, linkedObject2));
		
		evaluateStatement(statement);
		
		// 1 less link
		assertEquals(fState.allLinks().size() + 1, fOldState.allLinks().size());
		assertNull(getLink(assocName, linkedObject1, linkedObject2));
		
		//////////
		// undo //
		//////////
		
		undo();
		
		// link is back
		assertEquals(fState.allLinks().size(), fOldState.allLinks().size());
		assertNotNull(getLink(assocName, linkedObject1, linkedObject2));
		
		
		//////////
		// redo //
		//////////
		
		redo();
		
		// and gone again
		assertEquals(fState.allLinks().size() + 1, fOldState.allLinks().size());
		assertNull(getLink(assocName, linkedObject1, linkedObject2));
	
		///////////////////////////////////////////////
		// link object destruction via link deletion //
		///////////////////////////////////////////////
		
		reset();
		
		String assocClassName = "AC1";
		pName1 = "o5";
		pName2 = "o6";
		
		linkedObject1 = getObjectByVarName(pName1);
		assertNotNull(linkedObject1);
		linkedObject2 = getObjectByVarName(pName2);
		assertNotNull(linkedObject1);
		MObject linkObject = getObjectByVarName("lo1");
		assertNotNull(linkObject);
		MLink link = getLink(assocClassName, linkedObject1, linkedObject2);
		assertSame(linkObject, link);
		
		statement = 
			"delete " +
			"(" +
			pName1 + ", " +
			pName2 +
			") from " +
			assocClassName;
		
		evaluateStatement(statement);
		
		// link + link object gone
		assertFalse(fState.allLinks().contains(link));
		assertFalse(fState.allObjects().contains(linkObject));
	}
	
	
	/**
	 * TODO
	 * @throws MSystemException 
	 */
	@Test
	public void testConditional() throws MSystemException {
			
		//////////////////////
		// condition = true //
		//////////////////////
		
		String varName = "v";
		int thenVal = 41;
		int elseVal = 43;
		
		String condition = "true";
		
		String statement = 
			"if " + condition + 
			" then " + varName + " := " + thenVal + 
			" else " + varName + " := " + elseVal + 
			" end";
		
		// there is a variable "v" of type integer with value 42
		Value varVal = lookUpVar(varName);
		assertNotNull(varVal);
		assertTrue(varVal instanceof IntegerValue);
		assertEquals(((IntegerValue)varVal).value(), 42);
		
		evaluateStatement(statement);
		
		// value should now be the then value
		varVal = lookUpVar(varName);
		assertNotNull(varVal);
		assertTrue(varVal instanceof IntegerValue);
		assertEquals(((IntegerValue)varVal).value(), thenVal);
		
		//////////
		// undo //
		//////////
		
		undo();
				
		varVal = lookUpVar(varName);
		assertNotNull(varVal);
		assertTrue(varVal instanceof IntegerValue);
		assertEquals(((IntegerValue)varVal).value(), 42);
		
		//////////
		// redo //
		//////////
		
		redo();
		
		varVal = lookUpVar(varName);
		assertNotNull(varVal);
		assertTrue(varVal instanceof IntegerValue);
		assertEquals(((IntegerValue)varVal).value(), thenVal);
				
		///////////////////////
		// condition = false //
		///////////////////////
		
		reset();
		
		condition = "false";
		
		statement = 
			"if " + condition + 
			" then " + varName + " := " + thenVal + 
			" else " + varName + " := " + elseVal + 
			" end";
		
		// there is a variable "v" of type integer with value 42
		varVal = lookUpVar(varName);
		assertNotNull(varVal);
		assertTrue(varVal instanceof IntegerValue);
		assertEquals(((IntegerValue)varVal).value(), 42);
		
		evaluateStatement(statement);
		
		// value should now be the else value
		varVal = lookUpVar(varName);
		assertNotNull(varVal);
		assertTrue(varVal instanceof IntegerValue);
		assertEquals(((IntegerValue)varVal).value(), elseVal);
		
		///////////////////////////////////////
		// condition = oclUndefined(Boolean) //
		///////////////////////////////////////
		
		reset();
		
		condition = "oclUndefined(Boolean)";
		
		statement = 
			"if " + condition + 
			" then " + varName + " := " + thenVal + 
			" else " + varName + " := " + elseVal + 
			" end";
		
		// there is a variable "v" of type integer with value 42
		varVal = lookUpVar(varName);
		assertNotNull(varVal);
		assertTrue(varVal instanceof IntegerValue);
		assertEquals(((IntegerValue)varVal).value(), 42);
		
		evaluateStatement(statement);
		
		// value should now be the else value
		varVal = lookUpVar(varName);
		assertNotNull(varVal);
		assertTrue(varVal instanceof IntegerValue);
		assertEquals(((IntegerValue)varVal).value(), elseVal);
		
		//////////////////////////////////////////////////
		// variables created inside conditionals vanish //
		//////////////////////////////////////////////////
		
		reset();
		
		varName = "asdf";
		statement = "if true then " + varName + " := 12 end";
		
		assertNull(lookUpVar(varName));
		evaluateStatement(statement);
		assertNotNull(lookUpVar(varName));
	}
	
	
	/**
	 * TODO
	 * @throws MSystemException 
	 */
	@Test
	public void testIteration() throws MSystemException {
		
		///////////////////
		// silly example //
		///////////////////
		
		String statement =
			"sum := 0; " +
			"for x in Sequence{1..10} do sum := sum + x end;";
		
		evaluateStatement(statement);
		
		// java equivalent of soil code
		int sum = 0;
		for (int x = 1; x <= 10; ++x) {
			sum += x;
		}
		
		assertEquals(lookUpVar("sum"), IntegerValue.valueOf(sum));
		
		///////////////////////////////////////////////////
		// iteration variable shadows existing variables //
		///////////////////////////////////////////////////
		
		reset();
		
		statement = "x := 12; y := 13; for x in Set{0} do y := x end";
		
		evaluateStatement(statement);
		
		assertEquals(lookUpVar("y"), IntegerValue.valueOf(0));
		
	
		////////////////////////////////////////////////
		// variables created inside iterations vanish //
		////////////////////////////////////////////////
		
		reset();
		
		statement = "for x in Set{0} do y := x end";
		
		assertNull(lookUpVar("y"));
		evaluateStatement(statement);
		assertNotNull(lookUpVar("y"));
	}
	
	
	/**
	 * TODO
	 * @throws MSystemException 
	 */
	@Test
	public void testOperationCall() throws MSystemException {
		
		/////////////////////////
		// failed precondition //
		/////////////////////////
		
		String objVarName = "o1";
		
		// this operation fails to fulfill the pre conditions
		
		String opName = "failEnter";
		
		String statement = 
			objVarName +
			"." +
			opName +
			"()";
		
		// hide output
		fTestSystem.getSystem().registerPPCHandlerOverride(
				new SoilPPCHandler(
						NullPrintWriter.getInstance()));
		
		boolean caughtException = false;
		
		try {
			evaluateStatement(statement);
		} catch (MSystemException e) {
			caughtException = true;
		}
		
		assertTrue(caughtException);
		// call stack should be empty
		assertTrue(fTestSystem.getSystem().getCallStack().isEmpty());
		
		//////////////////////////
		// failed postcondition //
		//////////////////////////
		
		// this operation fails to fulfill the post conditions
		
		opName = "failExit";
		
		statement = 
			objVarName +
			"." +
			opName +
			"()";
		
		caughtException = false;
		
		try {
			evaluateStatement(statement);
		} catch (MSystemException e) {
			caughtException = true;
		}
		
		assertTrue(caughtException);
		// call stack should be empty
		assertTrue(fTestSystem.getSystem().getCallStack().isEmpty());
		
		////////////////
		// proxy fail //
		////////////////
		
		// this operation does not violate pre- or post conditions itself,
		// but calls another operation which does
		
		opName = "proxyFail";
		
		statement = 
			objVarName +
			"." +
			opName +
			"()";
		
		caughtException = false;
		
		try {
			evaluateStatement(statement);
		} catch (MSystemException e) {
			caughtException = true;
		}
		
		assertTrue(caughtException);
		// call stack should be empty
		assertTrue(fTestSystem.getSystem().getCallStack().isEmpty());
	}
		
	
	/**
	 * TODO
	 * @throws MSystemException 
	 */
	@Test
	public void testOpEnterOpExit() throws MSystemException {
		
		/////////////
		// openter //
		/////////////
		
		String objVarName = "o1";
		String opName = "u1";
		
		String statement = 
			"openter " +
			objVarName +
			" " +
			opName +
			"()";
		
		// object exists
		MObject object = getObjectByVarName(objVarName);
		assertNotNull(object);
		// call stack is empty
		assertTrue(fTestSystem.getSystem().getCallStack().isEmpty());
		
		evaluateStatement(statement);
		
		// one item on the call stack now
		assertEquals(fTestSystem.getSystem().getCallStack().size(), 1);
		// which happens to be the called operation
		MOperationCall opCall = fTestSystem.getSystem().getCurrentOperation();
		MObject self = opCall.getSelf();
		assertSame(self, object);
		assertEquals(opCall.getOperation().name(), opName);
		
		//////////
		// undo //
		//////////
		
		undo();
		
		// undo should result in the call stack being empty
		assertTrue(fTestSystem.getSystem().getCallStack().isEmpty());
		
		//////////
		// redo //
		//////////
		
		redo();
		
		// redo should have the original effect again
		assertEquals(fTestSystem.getSystem().getCallStack().size(), 1);
		opCall = fTestSystem.getSystem().getCurrentOperation();
		self = opCall.getSelf();
		assertSame(self, object);
		assertEquals(opCall.getOperation().name(), opName);
		
		//////////////////////////////////////
		// opexit with missing result value //
		//////////////////////////////////////
		
		statement = "opexit";
		
		// opexit with improper result value should result in the operation
		// still being on the stack
		
		boolean exceptionCaught = false;
		
		try {
			evaluateStatement(statement);
		} catch (MSystemException e) {
			exceptionCaught = true;
		}
		
		assertTrue(exceptionCaught);
		
		assertEquals(fTestSystem.getSystem().getCallStack().size(), 1);
		opCall = fTestSystem.getSystem().getCurrentOperation();
		self = opCall.getSelf();
		assertSame(self, object);
		assertEquals(opCall.getOperation().name(), opName);
		
		//////////////////////////////////////
		// opexit with correct result value //
		//////////////////////////////////////
		
		statement = "opexit 'asdf'";
		
		evaluateStatement(statement);
		
		// a proper opexit should result in an empty call stack
		assertTrue(fTestSystem.getSystem().getCallStack().isEmpty());
		
		//////////
		// undo //
		//////////
		
		undo();
		
		assertEquals(fTestSystem.getSystem().getCallStack().size(), 1);
		opCall = fTestSystem.getSystem().getCurrentOperation();
		self = opCall.getSelf();
		assertSame(self, object);
		assertEquals(opCall.getOperation().name(), opName);
		
		//////////
		// redo //
		//////////
		
		redo();
		
		assertTrue(fTestSystem.getSystem().getCallStack().isEmpty());
	}
	
	
	/**
	 * TODO
	 * @param varName
	 * @param varEnv
	 * @return
	 */
	private Value lookUpVar(
			String varName, 
			VariableEnvironment varEnv) {
		
		return varEnv.lookUp(varName);
	}
	
	
	/**
	 * TODO
	 * @param varName
	 * @return
	 */
	private Value lookUpVar(String varName) {
		return lookUpVar(varName, fTestSystem.getVarEnv());
	}
	
	
	/**
	 * TODO
	 * @param objectName
	 * @param state
	 * @return
	 */
	private MObject getObject(String objectName, MSystemState state) {
		return state.objectByName(objectName);
	}
	
	
	/**
	 * TODO
	 * @param objectName
	 * @return
	 */
	private MObject getObject(String objectName) {
		return getObject(objectName, fTestSystem.getState());
	}
	
	
	/**
	 * TODO
	 * @param varName
	 * @param varEnv
	 * @return
	 */
	private MObject getObjectByVarName(String varName, VariableEnvironment varEnv) {
		
		Value varVal = lookUpVar(varName, varEnv);
		
		if (varVal == null) {
			return null;
		}
		
		if (varVal instanceof ObjectValue) {
			return ((ObjectValue)varVal).value();
		}
		
		return null;
	}
	
	
	/**
	 * TODO
	 * @param varName
	 * @return
	 */
	private MObject getObjectByVarName(String varName) {
		return getObjectByVarName(varName, fTestSystem.getVarEnv());
	}
	
	
	/**
	 * TODO
	 * @param object
	 * @param attName
	 * @return
	 */
	private Value getAttVal(MObject object, String attName) {
		
		if (fState.allObjects().contains(object)) {
			return object.state(fState).attributeValue(attName);
		}
		
		return null;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	private List<String> getNewVars() {
		List<String> result = 
			new ArrayList<String>(fTestSystem.getVarEnv().getCurrentMappings().keySet());
		
		result.removeAll(fOldVarEnv.getCurrentMappings().keySet());
		
		return result;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	private List<MObject> getNewObjects() {
		List<MObject> result = 
			new ArrayList<MObject>(fTestSystem.getState().allObjects());
		result.removeAll(fOldState.allObjects());
		
		return result;
	}
	

	/**
	 * TODO
	 * @return
	 */
	private List<MLink> getNewLinks() {
		List<MLink> result = 
			new ArrayList<MLink>(fTestSystem.getState().allLinks());
		
		result.removeAll(fOldState.allLinks());
		
		return result;
	}
	
	
	/**
	 * TODO
	 * @param assocName
	 * @param participants
	 * @return
	 */
	private MLink getLink(String assocName, MObject... participants) {
		
		List<MObject> partList = Arrays.asList(participants);
		
		for (MLink link : fTestSystem.getState().allLinks()) {
			if ((link.association().name().equals(assocName)) &&
					(link.linkedObjects().containsAll(partList)) &&
					(partList.containsAll(link.linkedObjects()))) {
					
				return link;
			}
		}
		
		return null;
	}
	
	
	/**
	 * TODO
	 * @throws MSystemException
	 */
	private void undo() throws MSystemException {
		fTestSystem.getSystem().undoLastStatement();
	}
	
	
	/**
	 * TODO
	 * @throws MSystemException
	 */
	private void redo() throws MSystemException {
		fTestSystem.getSystem().redoStatement();
	}
	
	
	/**
     * TODO
     * @param statement
     * @throws MSystemException
     */
    public void evaluateStatement(
    		String statement) throws MSystemException {
    	
    	systemApi.getSystem().execute(generateStatement(statement));
    }
    
    
    /**
	 * TODO
	 * @param input
	 * @return
	 */
	private MStatement generateStatement(String input) {
		
		return ShellCommandCompiler.compileShellCommand(
				systemApi.getSystem().model(), 
				systemApi.getSystem().state(), 
				systemApi.getSystem().getVariableEnvironment(), 
				input, 
				"<input>", 
				NullPrintWriter.getInstance(), 
				false);
	}
}
