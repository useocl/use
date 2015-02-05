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

package org.tzi.use.uml.sys.soil.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.soil.SoilEvaluationContext;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * Class to handle SOIL library operations, like IO operations.
 * 
 * @author Lars Hamann
 *
 */
public class SoilLibrary {
	/** The single instance. This class and all operations are stateless **/
	private static SoilLibrary instance = null;

	public static synchronized SoilLibrary getInstance() {
		if (instance == null) {
			instance = new SoilLibrary();
			instance.initialize();
		}
		
		return instance;
	}
	
	/** Operation can have different signatures, therefore a multimap is used. **/ 
	private Multimap<String, LibraryOperation> operations = ArrayListMultimap.create();
	
	private SoilLibrary() {}
	
	/**
	 * All operations are registered here.
	 */
	private void initialize() {
		registerOperation(new WriteOp());
		registerOperation(new WriteLineOp());
		registerOperation(new ReadLineOp());
		registerOperation(new ReadIntegerOp());
	}
	
	/**
	 * Just a helper to ease registration.
	 * @param op The operation to register
	 */
	private void registerOperation(LibraryOperation op) {
		operations.put(op.getName(), op);
	}
	
	/**
	 * Searches for an operation with the provided <code>name</code>
	 * that matches the types of the <code>arguments</code>.
	 * @param name The name of the operation to search.
	 * @param arguments The argument expressions. Required to retrieve the types.  
	 * @return The matching operation or <code>null</code>. 
	 */
	public LibraryOperation findOperation(String name, Expression[] arguments) {
		Collection<LibraryOperation> ops = operations.get(name);
		if (ops.isEmpty()) {
			return null;
		}
		
		Type[] argTypes = new Type[arguments.length];
		for (int i = 0; i < arguments.length; ++i) {
			argTypes[i] = arguments[i].type();
		}

		for (LibraryOperation op : ops) {
			if (op.matches(argTypes)) {
				return op;
			}
		}
		
		return null;
	}
	
	private class ReadLineOp implements LibraryOperation {
		@Override
		public boolean matches(Type[] argTypes) {
			return argTypes.length == 0;
		}

		@Override
		public String getName() {
			return "ReadLine";
		}

		@Override
		public Type getReturnType() {
			return TypeFactory.mkString();
		}

		@Override
		public Value execute(SoilEvaluationContext context, Value[] arguments) {
			Value res = UndefinedValue.instance;
			
			try {
				String val = new BufferedReader(new InputStreamReader(System.in)).readLine();
				res = new StringValue(val);
			} catch (IOException e) {
				
			}
			
			return res;
		}
	}
	
	private class ReadIntegerOp implements LibraryOperation {
		@Override
		public boolean matches(Type[] argTypes) {
			return argTypes.length == 0;
		}

		@Override
		public String getName() {
			return "ReadInteger";
		}

		@Override
		public Type getReturnType() {
			return TypeFactory.mkInteger();
		}

		@Override
		public Value execute(SoilEvaluationContext context, Value[] arguments) {
			Value res = UndefinedValue.instance;
			
			try {
				String val = new BufferedReader(new InputStreamReader(System.in)).readLine();
				int integer = Integer.parseInt(val);
				res = IntegerValue.valueOf(integer);
			} catch (IOException|NumberFormatException e) { }
			
			return res;
		}
	}
	
	private class WriteLineOp implements LibraryOperation {
		@Override
		public String getName() {
			return "WriteLine";
		}
		
		@Override
		public boolean matches(Type[] argTypes) {
			if (argTypes.length != 1) return false;
			
			return argTypes[0].isKindOfString(VoidHandling.INCLUDE_VOID);
		}

		@Override
		public Type getReturnType() {
			return TypeFactory.mkVoidType();
		}

		@Override
		public Value execute(SoilEvaluationContext context, Value[] arguments) {
			
			Value v = arguments[0];
			
			if (v.isUndefined()) return null;
			
			String value = ((StringValue)v).value();
			System.out.println(value);
			
			return null;
		}
		
	}
	
	private class WriteOp implements LibraryOperation {
		@Override
		public String getName() {
			return "Write";
		}
		
		@Override
		public boolean matches(Type[] argTypes) {
			if (argTypes.length != 1) return false;
			return argTypes[0].isKindOfString(VoidHandling.INCLUDE_VOID);
		}

		@Override
		public Type getReturnType() {
			return TypeFactory.mkVoidType();
		}

		@Override
		public Value execute(SoilEvaluationContext context, Value[] arguments) {
			
			Value v = arguments[0];
			if (v.isUndefined()) return null;
			
			String value = ((StringValue)v).value();
			System.out.print(value);
			
			return null;
		}
		
	}
}
