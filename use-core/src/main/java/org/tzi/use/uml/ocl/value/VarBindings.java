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

package org.tzi.use.uml.ocl.value;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState;

/**
 * Variable bindings bind names to values. Bindings are kept on a stack and can
 * be retrieved by name. Main use is for expression evaluation.
 * 
 * @author Mark Richters
 */
public class VarBindings implements Iterable<VarBindings.Entry> {

    public class Entry {
        String fVarname;

        Value fValue;

        Entry(String varname, Value value) {
            fVarname = varname;
            fValue = value;
        }

        public String getVarName() {
            return fVarname;
        }

        public String valToString() {
            return fValue.toString();
        }

        public Value getValue() {
            return fValue;
        }

        protected void setValue(Value v) {
        	fValue = v;
        }
        
        @Override
        public boolean equals(Object otherEntry) {
        	if (!(otherEntry instanceof VarBindings.Entry)) {
        		return false;
        	}
        	
        	Entry other = (Entry)otherEntry;
        	return fVarname.equals(other.fVarname);
        }
        
        @Override
        public int hashCode() {
        	return fVarname.hashCode() * fValue.hashCode();
        }
        
        public String toString() {
            return fVarname + " : " + fValue.type() + " = " + fValue;
        }
    }
    
    private List<Entry> fBindings;

    private MSystemState fVisibleState;
    
    /**
     * Creates an empty VarBindings.
     */
    public VarBindings() {
        fBindings = new ArrayList<Entry>();
    }

    /**
     * Creates an empty VarBindings.
     */
    public VarBindings(MSystemState visibleState) {
        fBindings = new ArrayList<Entry>();
        fVisibleState = visibleState;
    }

    /**
     * Creates a VarBindings object and initializes it with the VarBindings
     * object passed as parameter.
     */
    public VarBindings(VarBindings bindings) {
        fBindings = new ArrayList<Entry>(bindings.fBindings);
        fVisibleState = bindings.fVisibleState;
    }

    /**
     * Adds all given bindings.
     */
    public void add(VarBindings bindings) {
        fBindings.addAll(bindings.fBindings);
        
        if (this.fVisibleState == null)
        	this.fVisibleState = bindings.fVisibleState;
    }

    public void push(String varname, Value value) {
        fBindings.add(new Entry(varname, value));
    }

    /**
     * Sets the value of the entry at the peek
     * to <code>v</code>. 
     * @param v
     */
    public void setPeekValue(Value v) {
    	fBindings.get(fBindings.size() - 1).fValue = v;
    }
    
    /**
     * Removes the last varentry
     */
    public void pop() {
        fBindings.remove(fBindings.size() - 1);
    }

    /**
     * Removes the latest added entry with given name.
     */
    public void remove(String varname) {
        // search in reverse order
        for (int i = fBindings.size() - 1; i >= 0; i--) {
            Entry b = fBindings.get(i);
            if (b.fVarname.equals(varname)) {
                fBindings.remove(i);
                break;
            }
        }
    }

    /**
     * Searches current bindings for variable name. Visibility is determined by
     * the order of elements. Variable bindings may thus be hidden by bindings
     * at earlier positions.
     * 
     * @return value for name binding or null if not bound
     */
    public Value getValue(String name) {
        // search in reverse order
        for (int i = fBindings.size() - 1; i >= 0; i--) {
            Entry b = fBindings.get(i);
            if (b.fVarname.equals(name))
                return b.fValue;
        }
        if (fVisibleState != null) {
        	for (MObject o : fVisibleState.allObjects()) {
        		if (o.name().equals(name)) return o.value();
        	}
        }
        return null;
    }

    /**
     * Returns an iterator over VarBindings.Entry objects.
     */
    public Iterator<Entry> iterator() {
    	if (fVisibleState != null) { 
    		ArrayList<Entry> tmp = new ArrayList<Entry>(fVisibleState.numObjects() + fBindings.size());
    		// add all object names which are not shadowed by variable names
    		for( MObject obj : fVisibleState.allObjects()) {
    			boolean shadowed = false;
    			for (int i = fBindings.size() - 1; i >= 0; i--) {
    	            if (fBindings.get(i).fVarname.equals(obj.name())) {
    	            	shadowed = true;
    	            	break;
    	            }
    	        }
    			if (!shadowed) {
    				tmp.add( new Entry(obj.name(),obj.value()) );
    			}
    		}
    		// all variable names
    		tmp.addAll(fBindings);
    		return tmp.iterator();
    	}
        return fBindings.iterator();
    }

    /**
     * Returns string representation of bindings useful for debugging.
     */
    public String toString() {
        return "VarBindings: " + fBindings.toString();
    }
}
