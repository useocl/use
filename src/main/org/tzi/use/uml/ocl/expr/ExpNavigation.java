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

import java.io.PrintWriter;
import java.util.List;

import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.uml.ocl.type.OrderedSetType;
import org.tzi.use.uml.ocl.type.SetType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.OrderedSetValue;
import org.tzi.use.uml.ocl.value.SetValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.util.Log;

/**
 * Navigation expression from one class to another.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public final class ExpNavigation extends Expression {
    private MNavigableElement fSrc;
    private MNavigableElement fDst;
    private Expression fObjExp;
    
    public ExpNavigation(Expression objExp,
                         MNavigableElement src,
                         MNavigableElement dst)
        throws ExpInvalidException
    {
        // set result type later
        super(null, objExp);

        if ( !objExp.type().isTrueObjectType() )
            throw new ExpInvalidException(
                    "Target expression of navigation operation must have " +
                    "object type, found `" + objExp.type() + "'." );
        
        // let c be the class at dstEnd, then the result type is:
        // (1) c if the multiplicity is max. one and this is binary association
        // (2) Set(c) if the multiplicity is greater than 1 
        // (3) OrderedSet(c) if the association end is marked as {ordered}
        setResultType( dst.getType( objExp.type(), src ) );

        fSrc = src;
        fDst = dst;
        fObjExp = objExp;
    }

    /**
     * Evaluates expression and returns result value.
     */
    @Override
	public Value eval(EvalContext ctx) {
        ctx.enter(this);
        Value res = UndefinedValue.instance;
        Value val = fObjExp.eval(ctx);

        // if we don't have an object we can't navigate 
        if (! val.isUndefined() ) {
            // get the object
            ObjectValue objVal = (ObjectValue) val;
            MObject obj = objVal.value();
            MSystemState state = isPre() ? ctx.preState() : ctx.postState();
            Type resultType = type();
            
            // if dst is derived evaluate the derive expression with obj as source
            if (fDst.isDerived()) {
            	ctx.pushVarBinding("self", objVal);
            	res = fDst.getDeriveExpression().eval(ctx);
            	
            	if (res.isUndefined()) {
            		if (resultType.isSet()) {
            			res = new SetValue(((SetType) resultType).elemType());
            		} else if (resultType.isOrderedSet()) {
            			res =  new OrderedSetValue(((OrderedSetType) resultType).elemType());
            		}
            	}
            } else if (fDst.getAllOtherAssociationEnds().size() == 1 && 
            		   fDst.getAllOtherAssociationEnds().get(0).isDerived()) {
            	/* The opposite side of a derived end of a binary association can be calculated:
            	   T = 
            	   T.allInstances()->select(t | t.deriveExpression->includes(self))
            	   
            	*/
            	MClass endClass = fDst.cls();
            	MNavigableElement otherEnd = fDst.getAllOtherAssociationEnds().get(0);
            	StringBuilder query = new StringBuilder();
            	query.append(endClass.name()).append(".allInstances()->select(self | ");
            	otherEnd.getDeriveExpression().toString(query);
            	query.append("->includes(sourceObject)");
            	query.append(")");
            	
            	ctx.pushVarBinding("sourceObject", objVal);
            	
            	Expression linkExpression = OCLCompiler.compileExpression(
            			ctx.postState().system().model(), 
            			query.toString(), 
            			"opposite derived end", 
            			new PrintWriter(Log.out()),
            			ctx.varBindings());
            	
            	if (linkExpression == null) {
            		Log.error("Calculated opposite derive expression had compile errors!");
            		return UndefinedValue.instance;
            	}
            	
            	res = linkExpression.eval(ctx);
            	
            	if (res.isUndefined()) {
            		if (resultType.isSet()) {
            			res = new SetValue(((SetType) resultType).elemType());
            		} else if (resultType.isOrderedSet()) {
            			res =  new OrderedSetValue(((OrderedSetType) resultType).elemType());
            		}
            	}
            } else {  
	            // get objects at association end
	            List<MObject> objList = obj.getNavigableObjects(state, fSrc, fDst);
	            if (resultType.isTrueObjectType() ) {
	                if (objList.size() > 1 )
	                    throw new MultiplicityViolationException(
	                        "expected link set size 1 at " + 
	                        "association end `" + fDst + 
	                        "', found: " + 
	                        objList.size());
	                if (objList.size() == 1 ) {
	                    obj = objList.get(0);
	                    if (obj.exists(state) )
	                        res = new ObjectValue((ObjectType) type(), obj);
	                }
	            } else if (resultType.isSet() ) {
	                res = new SetValue(((SetType) resultType).elemType(), 
	                                   oidsToObjectValues(state, objList));
	            } else if (resultType.isOrderedSet() ) {
	                res = new OrderedSetValue(((OrderedSetType) resultType).elemType(), 
	                                        oidsToObjectValues(state, objList));
	            } else
	                throw new RuntimeException("Unexpected association end type `" + 
	                                           resultType + "'");
            }
        }

        ctx.exit(this, res);
        return res;
    }

    private Value[] oidsToObjectValues(MSystemState state, List<MObject> objList) {
        Value[] res = new ObjectValue[objList.size()];
        int i = 0;
        
        for (MObject obj : objList) {
            MObjectState objState = obj.state(state);
            if (objState != null )
                res[i++] = new ObjectValue(obj.type(), obj);
        }
        return res;
    }


    @Override
    public StringBuilder toString(StringBuilder sb) {
        fObjExp.toString(sb);
        return sb.append(".")
          		 .append(fDst.nameAsRolename())
          		 .append(atPre());
    }

    public MNavigableElement getSource() {
        return fSrc;
    }

    public MNavigableElement getDestination() {
        return fDst;
    }

    public Expression getObjectExpression() {
        return fObjExp;
    }

}

