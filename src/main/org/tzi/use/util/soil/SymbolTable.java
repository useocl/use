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

package org.tzi.use.util.soil;


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.tzi.use.parser.soil.ast.ASTStatement;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState;


/**
 * A symbol table containing mappings from a variable name to a 
 * {@link Type type}.
 * <p>
 * Additionally to storing a variable's type, a dirty-bit is maintained, which
 * indicates whether a variable's type is guaranteed to be of the declared type.
 * <p>
 * A variable can become dirty if its type changes to an incompatible type
 * in code which is not guaranteed to be executed, such as branches of a 
 * conditional statement or the body of an iteration statement.
 * <p>
 * To keep track of those changes, the state of the symbol table can be 
 * {@link #storeState() stored} before entering such code, and 
 * {@link #restoreState(ASTStatement) restored} afterwards. 
 * After restoring it is checked, whether an assignment of an incompatible 
 * type was made.
 * <p>
 * Storing and restoring states uses a stack, which allows arbitrarily nested
 * statements.
 * 
 * @author Daniel Gent
 */
public class SymbolTable {
	/**
	 * Entry for the symbol table.
	 * 
	 * @author Daniel Gent
	 */
	private class Entry {
		/** the variable's type */
		Type type;
		/** dirty bit */
		boolean isDirty = false;
		/** soil statement causing this entry to be dirty */
		ASTStatement cause = null;
		
		
		/**
		 * constructs a new Entry
		 * 
		 * @param type the variable's type
		 */
		public Entry(Type type) {
			this.type = type;
		}
		
		@Override
		public String toString() {
			if (isDirty) {
				return type + " (DIRTY due to " + ((cause == null) ? "UNKNOWN" : cause) + ")";
			} else {
				return type.toString();
			}
		}
	}
	
	/** the entries */
	private Map<String, Entry> fEntries;
	/** the stack of states */
	private Deque<Map<String, Entry>> fStates = 
		new ArrayDeque<Map<String, Entry>>();
	
	private MSystemState fVisibleState ;
	
	/**
	 * constructs an empty symbol table
	 */
	public SymbolTable() {
		fStates.push(new LinkedHashMap<String, Entry>());
		fEntries = fStates.peek();
	}
	
	/**
	 * constructs an empty symbol table with the visible
	 * objects from state
	 */
	public SymbolTable(MSystemState visibleState) {
		this();
		fVisibleState = visibleState;
	}
	
	/**
	 * construct a symbol table from a {@code VariableEnvironment}
	 * 
	 * @param variableEnvironment a variable environment
	 */
	public SymbolTable(VariableEnvironment variableEnvironment) {
		fStates = variableEnvironment.constructSymbolTable().fStates;
	}
	
	
	/**
	 * removes all states and mappings
	 */
	public void clear() {
		fStates.clear();
		fStates.push(new LinkedHashMap<String, Entry>());
		fEntries = fStates.peek();
	}
	
	
	/**
	 * copies the current state and pushes it to a stack
	 * 
	 * @see #restoreState()
	 * @see #restoreState(ASTStatement)
	 */
	public void storeState() {
		fStates.push(new LinkedHashMap<String, Entry>(fEntries));
		fEntries = fStates.peek();
	}
	
	
	/**
	 * the current state is destroyed and the most recently stored state becomes
	 * the new current state
	 * <p>
	 * additionally the current and the stored state are compared. if the type
	 * of a variable has changed from the stored state to the current state, 
	 * and the type is incompatible (i.e. the new type is not a sub-type of the
	 * stored type), the variable is flagged as being dirty, and the supplied
	 * soil statement gets blamed.
	 * 
	 * @param cause the soil statement to blame for a variable becoming dirty
	 */
	public void restoreState(ASTStatement cause) {
		if (fStates.size() <= 1) {
			return;
		}
		
		Map<String, Entry> poppedState = fStates.pop();
		fEntries = fStates.peek();
			
		Set<String> sharedNames = new HashSet<String>(fEntries.keySet());
		sharedNames.retainAll(poppedState.keySet());
		
		for (String name : sharedNames) {
			Entry thisEntry = fEntries.get(name);
			Entry thatEntry = poppedState.get(name);
			
			if (!thatEntry.type.isSubtypeOf(thisEntry.type)) {
				thisEntry.isDirty = true;
				thisEntry.cause = cause;
			} else if (thatEntry.isDirty) {
				thisEntry.isDirty = true;
				thisEntry.cause = thatEntry.cause;
			}
		}
	}
	
	
	/**
	 * sets the type of a variable
	 * 
	 * @param name name of the variable
	 * @param type type of the variable
	 */
	public void setType(String name, Type type) {
		fEntries.put(name, new Entry(type));
	}
	
	/**
	 * Returns an entry by looking into the
	 * entries constructed by adding elements
	 * to the SymTable or if nothing found by
	 * looking into the objects of the system sate. 
	 * @param name
	 * @return An entry for <code>name</code> or <code>null</code>.
	 */
	private Entry getEntry(String name) {
		Entry entry = fEntries.get(name);
		
		if (entry == null && fVisibleState != null) {
			MObject obj = fVisibleState.objectByName(name);
			if (obj != null)
				return new Entry(obj.type());
		}
		
		return entry;
	}
	/**
	 * returns true if this contains a variable with the specified name
	 * 
	 * @param name the name specifying the variable
	 * @return true if this contains a variable with the specified name
	 */
	public boolean contains(String name) {
		return getEntry(name) != null;
	}
	
	
	/**
	 * returns true if the dirty bit for the specified variable is set
	 * 
	 * @param name the name specifying the variable
	 * @return true if the dirty bit for the specified variable is set
	 */
	public boolean isDirty(String name) {
		Entry entry = getEntry(name);
		
		if (entry != null) {
			return entry.isDirty;
		}
		
		return false;
	}
	
	
	/**
	 * returns the soil statement causing the specified variable to be dirty
	 * 
	 * @param name the name specifying the variable
	 * @return the soil statement causing this variable to be dirty or 
	 *         {@code null} if there isn't a variable with the specified name, 
	 *         or the variable is not flagged as dirty
	 */
	public ASTStatement getCause(String name) {
		Entry entry = getEntry(name);
		
		if ((entry != null) && (entry.isDirty)) {
			return entry.cause;
		} else {
			return null;
		}
	}
	
	
	/**
	 * returns the type of the specified variable
	 * 
	 * @param name the name specifying the variable
	 * @return the variable's type or {@code null} if there is no such variable
	 */
	public Type getType(String name) {
		Entry entry = getEntry(name);
		
		if (entry != null) {
			return entry.type;
		} else {
			return null;
		}
	}
	
	
	@Override
	public String toString() {
		int numStates = fStates.size();
		
		final String STPRE = "[state ";
		final String STPST = "]\n";
		final String COLON = " : ";
		final String NEWLN = "\n";
		final String EMPTY = "empty\n";
		final String PRELUDE = (numStates > 1) ? "  " : "";
		
		StringBuilder sb = new StringBuilder();	
		
		int currentState = numStates;
		for (Map<String, Entry> state : fStates) {
			if (numStates > 1) {
				sb.append(STPRE);
				sb.append(--currentState);
				sb.append(STPST);
			}
			
			if (state.isEmpty()) {
				sb.append(PRELUDE);
				sb.append(EMPTY);
			}
			
			for (Map.Entry<String, Entry> entry : state.entrySet()) {
				String variableName = entry.getKey();
				Entry variableEntry = entry.getValue();
				
				sb.append(PRELUDE);
				sb.append(variableName);
				sb.append(COLON);
				sb.append(variableEntry);
				sb.append(NEWLN);
			}
		}
		
		return sb.toString();
	}
}
