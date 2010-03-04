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

import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.BagValue;
import org.tzi.use.uml.ocl.value.SequenceValue;
import org.tzi.use.uml.ocl.value.SetValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;

/** 
 * OCL collect expression.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ExpCollect extends ExpQuery {
    
    /**
     * Constructs a collect expression. <code>elemVarDecl</code> may be null.
     */
    public ExpCollect(VarDecl elemVarDecl,
                      Expression rangeExp, 
                      Expression queryExp) 
        throws ExpInvalidException
    {
        // result type is bag or sequence of query expression type
        super( rangeExp.type().isSequence() || rangeExp.type().isOrderedSet()
               ? (Type) TypeFactory.mkSequence(
            		   (queryExp.type().isCollection(true) ? ((CollectionType)queryExp.type()).elemType() : queryExp.type()))
               : (Type) TypeFactory.mkBag(
            		   (queryExp.type().isCollection(true) ? ((CollectionType)queryExp.type()).elemType() : queryExp.type())), 
               ( elemVarDecl != null ) 
               ? new VarDeclList(elemVarDecl) 
               : new VarDeclList(true),
               rangeExp, queryExp);
    }

    /** 
     * Return name of query expression.
     */
    public String name() {
        return "collect";
    }

    /**
     * Evaluates expression and returns result value.
     */
    public Value eval(EvalContext ctx) {
        ctx.enter(this);
        Value res = evalCollectNested(ctx);
        
        if (res.isUndefined()) 
        	res = UndefinedValue.instance;
        else if (res.isBag())           
        	res = ((BagValue) res).flatten();
        else if (res.isSet())      
        	res = ((SetValue) res).flatten();
        else if (res.isSequence()) 
        	res = ((SequenceValue) res).flatten();
        
        ctx.exit(this, res);
        return res;
    }
}

