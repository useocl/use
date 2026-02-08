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

package org.tzi.use.util.soil;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.tzi.use.parser.soil.ast.ASTEmptyStatement;
import org.tzi.use.parser.soil.ast.ASTStatement;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;


/**
 * Test cases for various methods of {@code SymbolTable}s
 * 
 * @author Daniel Gent
 * @see SymbolTable
 */
public class SymbolTableTest extends TestCase {
	/** the test subject */
	private SymbolTable fST;
	/** arbitrary name */
	private String fVariableName;
	/** the ocl Real type */
	private Type fRealType;
	/** the ocl Integer type */
	private Type fIntegerType;
	/** the ocl String type */
	private Type fStringType;
	/** arbitrary statement */
	private ASTStatement fStatement;
	
	
	
	/**
	 * constructs the fixture
	 */
	@Override
	@Before
	public void setUp() {
		fST = new SymbolTable();
		fVariableName = "name";
		fRealType = TypeFactory.mkReal();
		fIntegerType = TypeFactory.mkInteger();
		fStringType = TypeFactory.mkString();
		fStatement = new ASTEmptyStatement();
		
		assertTrue(fIntegerType.conformsTo(fRealType));
	}
	
	
	/**
	 * tests type setting and getting
	 * 
	 * @see SymbolTable#getType(String)
	 * @see SymbolTable#setType(String, Type)
	 */
	@Test
	public void testGetSet() {
		assertNull(fST.getType(fVariableName));
		fST.setType(fVariableName, fIntegerType);
		assertEquals(fST.getType(fVariableName), fIntegerType);
		fST.setType(fVariableName, fRealType);
		assertEquals(fST.getType(fVariableName), fRealType);
	}
	
	
	/**
	 * tests state storing and restoring
	 * 
	 * @see SymbolTable#storeState()
	 * @see SymbolTable#restoreState(ASTStatement)
	 */
	@Test
	public void testStoreRestore() {
		// check if the stack works correctly
		fST.setType(fVariableName, fIntegerType);
		assertEquals(fST.getType(fVariableName), fIntegerType);
		fST.storeState();
		fST.setType(fVariableName, fRealType);
		assertEquals(fST.getType(fVariableName), fRealType);
		fST.storeState();
		fST.setType(fVariableName, fStringType);
		assertEquals(fST.getType(fVariableName), fStringType);
		fST.restoreState(fStatement);
		assertEquals(fST.getType(fVariableName), fRealType);
		fST.restoreState(fStatement);
		assertEquals(fST.getType(fVariableName), fIntegerType);
		fST.clear();
		
		// test dirty-bit
		fST.setType(fVariableName, fIntegerType);
		fST.storeState();
		fST.setType(fVariableName, fStringType);
		fST.restoreState(fStatement);
		// the type does not change
		assertEquals(fST.getType(fVariableName), fIntegerType);
		// but the variable is now dirty
		assertTrue(fST.isDirty(fVariableName));
		assertEquals(fST.getCause(fVariableName), fStatement);
		fST.clear();
		
		fST.setType(fVariableName, fRealType);
		fST.storeState();
		fST.setType(fVariableName, fIntegerType);
		fST.restoreState(fStatement);
		assertEquals(fST.getType(fVariableName), fRealType);
		// not dirty, since Integer is a sub-type of Real
		assertFalse(fST.isDirty(fVariableName));	
	}
}
