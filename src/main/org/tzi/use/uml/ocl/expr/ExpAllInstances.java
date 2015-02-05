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

package org.tzi.use.uml.ocl.expr;

import java.util.Set;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassifier;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.LinkValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.SetValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkSet;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemState;

/**
 * Type.allInstances
 *
 * @author Mark Richters
 * @author Lars Hamann
 */
public final class ExpAllInstances extends Expression {
    private MClassifier fSourceType;
    
    public ExpAllInstances(Type sourceType)
        throws ExpInvalidException
    {
        // result type is a set of sourceType
        super(TypeFactory.mkSet(sourceType));

        if (! sourceType.isKindOfClassifier(VoidHandling.EXCLUDE_VOID) )
            throw new ExpInvalidException("Expected an object type, found `" + sourceType + "'.");
        
        fSourceType = (MClassifier)sourceType;
    }

    /**
     * The type allInstances() is applied to. 
     * @return
     */
    public MClassifier getSourceType() {
    	return this.fSourceType;
    }
    
	@Override
	protected boolean childExpressionRequiresPreState() {
		return false;
	}
	
    /**
     * Evaluates expression and returns result value. 
     */
    public Value eval(EvalContext ctx) {
        ctx.enter(this);
        MSystemState systemState = isPre() ? ctx.preState() : ctx.postState();

        // the result set will contain all instances of the specified
        // class plus all instances of subclasses

        // get set of objects 
        SetValue res;
        
        if(fSourceType.isTypeOfClass()) {
	        Set<MObject> objSet = systemState.objectsOfClassAndSubClasses((MClass)fSourceType);
	        Value[] objValues = new Value[objSet.size()];
	
	        int i = 0;
	        for (MObject obj : objSet) {
	            objValues[i++] = new ObjectValue(obj.cls(), obj);
	        }
	
	        // create result set with object references
	        res = new SetValue(fSourceType, objValues);
        } else if (fSourceType.isTypeOfAssociation()) {
        	MLinkSet links = systemState.linksOfAssociation((MAssociation)fSourceType);
        	Value[] linkValues = new Value[links.size()];
        	
        	int i = 0;
        	for (MLink link : links.links()) {
        		linkValues[i++] = new LinkValue(link.association(), link);
        	}
        	
        	res = new SetValue(fSourceType, linkValues);
        } else {
        	throw new IllegalArgumentException("allInstances() is only supported on classes and associations.");
        }
        
        ctx.exit(this, res);
        return res;
    }

    @Override
    public String name() {
    	return "allInstances";
    }
    
	@Override
    public StringBuilder toString(StringBuilder sb) {
		fSourceType.toString(sb);
		sb.append(".").append(name());
		return sb.append(atPre());
    }
	
	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitAllInstances(this);
	}
}
