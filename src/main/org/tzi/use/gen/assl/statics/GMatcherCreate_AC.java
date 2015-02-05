/*
 * This is source code of the Snapshot Generator, an extension for USE
 * to generate (valid) system states of UML models.
 * Copyright (C) 2001 Joern Bohling, University of Bremen
 *
 * About USE:
 *   USE - UML based specification environment
 *   Copyright (C) 1999,2000,2001 Mark Richters, University of Bremen
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

package org.tzi.use.gen.assl.statics;

import java.util.LinkedList;
import java.util.List;

import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MModel;

/**
 * Matcher for a Create(AssociationClass, Class...) instruction
 * 
 * @see org.tzi.use.gen.assl.statics
 * @author  Lars Hamann
 */
class GMatcherCreate_AC implements IGInstructionMatcher {
    public String name() {
        return "Create";
    }

    public GInstruction createIfMatches( List<Object> param, MModel model ) {
        if (!matches(param, model))
            return null;
        
        List<GValueInstruction> exp = new LinkedList<GValueInstruction>();
        for (int index = 1; index < param.size(); ++index) {
        	exp.add((GValueInstruction)param.get(index));
        }
        
        return new GInstrCreate_AC((MAssociationClass)model.getClass((String) param.get(0)), exp );
    }

    private boolean matches( List<Object> param, MModel model ) {
    	// Minimum number of arguments
    	if (param.size() < 3) return false;
    	
    	// First param must be a name of an association class and not abstract
    	if (!(param.get(0) instanceof String)) return false;
    	String associationClassName = (String)param.get(0);
    	MClass cls = model.getClass(associationClassName);
    	
    	if (cls == null || cls.isAbstract() || !(cls instanceof MAssociationClass))
    		return false;
    	
    	MAssociationClass associationClass = (MAssociationClass)cls;
    	
    	// All association ends must be provided
    	if (associationClass.associationEnds().size() != param.size() - 1)
    		return false;
    	
    	// All parameter need to be objects of connected classes
    	for (int index = 1; index < param.size(); ++index) {
    		if (!(param.get(index) instanceof GValueInstruction)) return false;
    		
    		GValueInstruction instr = (GValueInstruction)param.get(index);
    		if (!instr.type().conformsTo(associationClass.associationEnds().get(index - 1).cls())) {
    			return false;
    		}
    	}
    	
    	return true;    	
    }
}
