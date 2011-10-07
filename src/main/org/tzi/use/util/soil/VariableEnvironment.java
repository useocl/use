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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState;


/**
 * Holds variables and their values
 * <p>
 * Data is organized as follows:<br>
 * Variables and their respective values are hold as a 
 * mapping {@code String} -> {@code Value}. Mappings for one or more variables
 * are contained in a frame. One or more frames are contained in a level.
 * <p>
 * example:
 * <p>
 * l(n+1) is more recent than l(n), f(n+1) is more recent than f(n)
 * <p>
 * l2[ f0[x->17]                       ]<br>
 * l1[ f0[a->'a', x->'x'], f1[a->'b']  ]<br>
 * l0[ f0[x->12,  y->'y'], f1[z->3]    ]<br>
 * <p>
 * Looking up values only considers the most recent level, and searches 
 * backwards from the most recent frame in that level until the value is found 
 * or the least recent frame has been searched.<br>
 * When assigning a value to a variable, a pre-existing mapping is searched in 
 * the same way a looking up values work. If no such mapping exists, a new 
 * mapping is added to the most recent frame in the most recent level.
 * 
 * @author Daniel Gent
 */
public class VariableEnvironment {
	/** the stack of frames */
	private Deque<Map<String,Value>> fFrames;
	/** TODO */
	private Deque<Boolean> fObjectVisibility;
	/** reference to the current frame */
	private Map<String, Value> fCurrentFrame;

	/** TODO */
	private MSystemState fSystemState;
	

	/**
	 * Constructs an empty variable environment
	 */
	public VariableEnvironment(MSystemState systemState) {
		clear();
		fSystemState = systemState;
	}
	
	
	/**
	 * copy constructor
	 * <p>
	 * creates a quasi real copy (note that {@code String} and {@code Value} 
	 * objects aren't copied, since they are considered immutable)
	 *  
	 * @param other the {@code VariableEnvironment} to copy
	 */
	public VariableEnvironment(VariableEnvironment other) {
		fSystemState = other.fSystemState;
		fFrames = new ArrayDeque<Map<String, Value>>();
		for (Map<String,Value> b : other.fFrames) {
			Map<String, Value> b1 = new HashMap<String,Value>();
			b1.putAll(b);
			fFrames.add(b1);
		}
		fCurrentFrame = fFrames.peek();
		fObjectVisibility = new ArrayDeque<Boolean>(other.fObjectVisibility);
	}

	public VariableEnvironment(VariableEnvironment other, MSystemState systemState) {
		fSystemState = systemState;
		fFrames = new ArrayDeque<Map<String, Value>>(other.fFrames);
		fCurrentFrame = fFrames.peek();
		fObjectVisibility = new ArrayDeque<Boolean>(other.fObjectVisibility);
	}
	
	/**
	 * Restores the initial state, which consists of one level, with one
	 * frame containing no variable mappings
	 */
	public void clear() {
		
		fFrames = new ArrayDeque<Map<String, Value>>();
		fObjectVisibility = new ArrayDeque<Boolean>();
		
		pushFrame(true);
	}
	
	
	/**
	 * Returns true if this variable environment is empty, empty being 
	 * defined as consisting of one level with one frame containing no 
	 * variable mappings
	 * 
	 * @return {@code true} if this variable environment is empty, 
	 * {@code false} else
	 */
	public boolean isEmpty() {
		return ((fFrames.size() == 1) && fFrames.peek().isEmpty());
	}
	
	
	/**
	 * adds a new level to this variable environment, which initially has
	 * one empty frame. all consecutive assignments and lookups will be
	 * performed on this level until {@code pushLevel} or {@code popLevel} are
	 * called
	 * 
	 * @see #popFrame()
	 */
	public void pushFrame(boolean objectsVisible) {
		fFrames.push(new HashMap<String,Value>());
		fCurrentFrame = fFrames.peek();
		fObjectVisibility.push(objectsVisible);
	}
	
	
	/**
	 * removes the most recent level pushed with {@code pushLevel}. if only one
	 * level exists, this does nothing
	 * 
	 * @see #pushFrame()
	 */
	public void popFrame() {
		fFrames.pop();
		fCurrentFrame = fFrames.peek();
		fObjectVisibility.pop();
	}
	

	/**
	 * assigns a value to a variable
	 * <p>
	 * Note: only the current, most recent level of this variable environment 
	 * can be accessed with this method.
	 * <p>
	 * If the current level already contains a mapping for this
	 * variable, it is updated in the most recent frame containing it, else
	 * it a new mapping is added to the most recent frame.
	 * 
	 * @param name name of the variable
	 * @param value value of the variable
	 * @return if there was a previous mapping, the old {@code Value} is 
	 * returned, {@code null} else
	 */
	public Value assign(String name, Value value) {
		
		Value oldValue = fCurrentFrame.get(name);
		fCurrentFrame.put(name, value);
		
		return oldValue;
	}
	
	
	/**
	 * Assigns multiple variables. This is a wrapper
	 * for the single variable version.
	 * 
	 * @param variables the variables and their values
	 * @see #assign(String, Value)
	 */
	public void assign(Map<String, Value> variables) {
		for (Entry<String, Value> entry : variables.entrySet()) {
			assign(entry.getKey(), entry.getValue());
		}
	}
	
	
	/**
	 * returns a variable's value
	 * <p>
	 * Note: only the current, most recent level of this variable environment 
	 * can be accessed with this method.
	 * <p>
	 * If the current level contains a mapping for this name,
	 * the value of the mapping in the most recent frame is returned.
	 * {@code null} is returned else.
	 * 
	 * @param name the name of the variable
	 * @return if the variable is known it's {@code Value}, {@code null} else
	 */
	public Value lookUp(String name)
	{	
		Value result = fCurrentFrame.get(name);
		
		if ((result == null) 
				&& (fObjectVisibility.peek()) 
				&& (fSystemState.hasObjectWithName(name))) {
			
			result = fSystemState.objectByName(name).value();
		}
		
		return result;
	}
	
	
	/**
	 * returns all currently available variable mappings, i.e. all mappings
	 * in the current level. if a variable name is mapped in different frames,
	 * the mapping in the most recent frame is used. (the one that would be 
	 * accessed by a {@code lookUp}) 
	 * @return all current variable mappings
	 */
	public Map<String, Value> getCurrentMappings() {
		return fCurrentFrame;
	}
	
	
	/**
	 * removes the most recent mapping of this variable name on the current
	 * level of this variable environment
	 * 
	 * @param name the variable's name
	 */
	public void remove(String name) {
		fFrames.peek().remove(name);
	}
	
	
	/**
	 * updates the references to a deleted object
	 * <p>
	 * All variables on all levels referencing to the supplied object reference
	 * to the corresponding undefined value afterwards.
	 * 
	 * @param object the object that was deleted
	 */
	public void undefineReferencesTo(MObject object) {
		
		for (Map<String, Value> frame : fFrames) {
			for (Entry<String, Value> entry : frame.entrySet()) {
				Value value = entry.getValue();	
				if ((value.isObject()) &&
						((ObjectValue)value).value() == object) {
					
					// use the type of this value to find the correct 
					// 'undefined' value; the object type might be more 
					// specific, and we want to preserve type of this 
					// variable
					entry.setValue(UndefinedValue.instance);
				}
			}
		}	
	}
	
	
	/**
	 * returns the names of all variables in the top level frame (the first
	 * frame of the first level) referencing the supplied object
	 *
	 * @param object the referenced object
	 * @return the names of top-level variables referencing the object
	 */
	public List<String> getTopLevelReferencesTo(MObject object) {
		
		List<String> result = new ArrayList<String>();
		
		for (Entry<String, Value> entry : fFrames.peekFirst().entrySet()) {
			Value value = entry.getValue();
			if ((value.isObject()) &&
					((ObjectValue)value).value() == object) {
				
				result.add(entry.getKey());
			}
		}
		
		return result;
	}
	
	
	/**
	 * constructs a symbol table based on the mappings in the current level of 
	 * this variable environment
	 * <p>
	 * This is basically a transformation of a Name->Value mapping 
	 * to a Name->Type mapping. Note that if the current level contains 
	 * more than one mapping for a variable, only the most recent is added
	 * to the symbol table.
	 * 
	 * @see SymbolTable
	 * @return the constructed symbol table
	 */
	public SymbolTable constructSymbolTable() {
		SymbolTable result = new SymbolTable();
		
		if (fObjectVisibility.peek()) {
			for (MObject obj : fSystemState.allObjects()) {
				result.setType(obj.name(), obj.type());
			}
		}
		
		for (Entry<String, Value> entry : fCurrentFrame.entrySet()) {
			String varName = entry.getKey();			
			result.setType(varName, entry.getValue().type());
		}
		
		return result;
	}
	
	
	/**
	 * constructs a variable bindings in {@code VarBindings} 
	 * format from this {@code VariableEnvironment}
	 * <p>
	 * Note that the level/frame structure is not duplicated, i.e. if this 
	 * variable environment contains more than one mapping for a 
	 * variable, only the most recent is added to the variable 
	 * bindings.
	 *   
	 * @see VarBindings
	 * @return the constructed object
	 */
	public VarBindings constructVarBindings() {
		VarBindings result = null;
		
		if (fObjectVisibility.peek()) {
			result = new VarBindings(fSystemState);
		} else {
			result = new VarBindings();
		}
		
		for (Entry<String, Value> entry : fCurrentFrame.entrySet()) {
			String name = entry.getKey();
			Value value = entry.getValue();
			
			result.push(name, value);
		}
		
		return result;
	}
	
	
	@Override
	public String toString() {
		
		final String COLON = " : ";
		final String EQUAL = " = ";
		final String NEWLN = System.getProperty("line.separator");
		final String FRPRE = "[frame ";
		final String FRPST = "]" + NEWLN;
		final String EMPTY  = "empty" + NEWLN;
		String VAR_INDENT;
		
		int numFrames = fFrames.size();
		int numObjects = fSystemState.numObjects();
		boolean printFrameNum = ((numFrames > 1) || (numObjects > 0));
		
		VAR_INDENT = printFrameNum ? "  " : "";
		
		StringBuilder sb = new StringBuilder();

		int currentFrame = (numFrames - 1);
		
		for (Map<String, Value> frame : fFrames) {
			if (printFrameNum) {
				sb.append(FRPRE);
				sb.append(currentFrame--);
				sb.append(FRPST);
			}
			
			if (frame.isEmpty()) {
				sb.append(VAR_INDENT);
				sb.append(EMPTY);
			}
			
			for (Entry<String, Value> entry : frame.entrySet()) {
				sb.append(VAR_INDENT);
				sb.append(entry.getKey());
				sb.append(COLON);
				sb.append(entry.getValue().type());
				sb.append(EQUAL);
				sb.append(entry.getValue());
				sb.append(NEWLN);
			}
		}
		
		if (numObjects > 0) {
			
			sb.append("[object variables]");
			sb.append(NEWLN);
			
			List<String> objectNames = 
				new LinkedList<String>(fSystemState.allObjectNames());
			Collections.sort(objectNames);
			
			for (String objectName : objectNames) {
				MObject object = fSystemState.objectByName(objectName);
				
				sb.append(VAR_INDENT);
				sb.append(objectName);
				sb.append(COLON);
				sb.append(object.type());
				sb.append(EQUAL);
				sb.append(object.value());
				sb.append(NEWLN);
			}
		}
		
		return sb.toString();
	}
}