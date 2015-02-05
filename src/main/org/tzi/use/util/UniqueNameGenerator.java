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

package org.tzi.use.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * A generator for creating unique names. Given a name a it produces
 * a1,a2,... each time a new name is requested.
 *
 * @author      Mark Richters 
 * @author      Lars Hamann
 */
public class UniqueNameGenerator {
	private class StackEntry {
		private Map<String, Integer> nameCounters;
		
		private int numPushes = 1;
				
		public StackEntry() {
			nameCounters = new HashMap<String, Integer>();
		}
		
		public StackEntry(Map<String, Integer> source) {
			nameCounters = new HashMap<String, Integer>(source);
		}
		
		public void push() {
			++numPushes;
		}
		
		public void pop() {
			if (--numPushes <= 0) {
				fStack.pop();
			}
		}
		
		public String generate(String name) {
			// Push was called,
			// we need to copy ourselves and add a new entry to the stack.
			if (numPushes > 1) {
				--numPushes;
				StackEntry newEntry = new StackEntry(this.nameCounters); 
				fStack.push(newEntry);
				return newEntry.generate(name);
			} else {
				Integer i = nameCounters.get(name);
	            
		        if (i == null ) {
		        	i = 1;
		        } else {
		        	i = i + 1;
		        }
		        
		        nameCounters.put(name, i);
		        
		        return name + i;
			}
		}
	}
	
	private Stack<StackEntry> fStack;
        
    public UniqueNameGenerator() {
        fStack = new Stack<StackEntry>();
        fStack.push(new StackEntry());
    }
    
    /**
     * Creates a new name by appending <code>name</code> with a
     * number. Numbering starts with 1. Subsequent calls will
     * increment this number.
     */
    public String generate(String name) {
        return fStack.peek().generate(name);
    }
    
    public void pushState() {
    	// Not all statements require a generated name,
    	// but when using the generator there are many calls
    	// to this operation.
    	fStack.peek().push();
    }
    
    public void popState() {
    	fStack.peek().pop();
    }
    
    public String toString() {
    	return fStack.toString();
    }
}
