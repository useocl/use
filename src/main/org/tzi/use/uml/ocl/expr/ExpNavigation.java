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

import java.util.LinkedList;
import java.util.List;

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.Type.VoidHandling;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.util.StringUtil;

/**
 * Navigation expression from one class to another.
 * 
 * @author  Mark Richters
 * @author  Lars Hamann
 */
public final class ExpNavigation extends Expression {
    private final MNavigableElement fSrc;
    private final MNavigableElement fDst;
    private final Expression fObjExp;
    
    private final Expression[] qualifierExpressions;
    
    public ExpNavigation(Expression objExp,
                         MNavigableElement src,
                         MNavigableElement dst,
                         List<Expression> theQualifierExpressions)
        throws ExpInvalidException
    {
        // set result type later
        super(null);
        
        if (theQualifierExpressions == null) {
        	this.qualifierExpressions = new Expression[0];
        } else {
        	this.qualifierExpressions = theQualifierExpressions.toArray(new Expression[theQualifierExpressions.size()]);
        }
                
        if ( !objExp.type().isKindOfClassifier(VoidHandling.EXCLUDE_VOID) )
            throw new ExpInvalidException(
                    "Target expression of navigation operation must have " +
                    "object type, found `" + objExp.type() + "'." );
        
        if (!src.hasQualifiers() && qualifierExpressions.length > 0) {
        	throw new ExpInvalidException("The navigation end " + StringUtil.inQuotes(dst.nameAsRolename()) +
        			" has no defined qualifiers, but qualifer values were provided.");
        }
        
        setResultType( dst.getType( objExp.type(), src, qualifierExpressions.length > 0 ) );

        this.fSrc = src;
        this.fDst = dst;
        this.fObjExp = objExp;
    }

    /**
     * Evaluates expression and returns result value.
     */
    @Override
	public Value eval(EvalContext ctx) {
        ctx.enter(this);
        Value res = UndefinedValue.instance;
        final Value val = fObjExp.eval(ctx);

        // if we don't have an object we can't navigate 
        if (! val.isUndefined() ) {
            // get the object
            final ObjectValue objVal = (ObjectValue) val;
            final MObject obj = objVal.value();
            final MSystemState state = isPre() ? ctx.preState() : ctx.postState();
            final Type resultType = type();
            
              
            // get objects at association end
        	List<Value> qualifierValues = new LinkedList<Value>();
        		
    		for (Expression exp : this.qualifierExpressions) {
    			qualifierValues.add(exp.eval(ctx));
    		}
        	        	
            List<MObject> objList = obj.getNavigableObjects(state, fSrc, fDst, qualifierValues);
            if (resultType.isTypeOfClass() ) {
                if (objList.size() > 1 )
                    throw new MultiplicityViolationException(
                        "expected link set size 1 at " + 
                        "association end `" + fDst + 
                        "', found: " + 
                        objList.size());
                if (objList.size() == 1 ) {
                    res = new ObjectValue((MClass)type(), objList.get(0));
                }
            } else if (resultType.isKindOfCollection(VoidHandling.EXCLUDE_VOID)) {
            	CollectionType ct = (CollectionType)resultType;
            	res = ct.createCollectionValue(oidsToObjectValues(state, objList));
            } else
                throw new RuntimeException("Unexpected association end type `" + 
                                           resultType + "'");
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
                res[i++] = new ObjectValue(obj.cls(), obj);
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
    
    /**
     * Returns an array of defined qualifier expressions for this navigation.
     * If no expressions where defined an empty array is returned. 
     * @return
     */
    public Expression[] getQualifierExpression(){
		return qualifierExpressions;
    }

	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitNavigation(this);
	}

	@Override
	protected boolean childExpressionRequiresPreState() {
		 if (fObjExp.requiresPreState()) return true;
		 
		 for (Expression e : qualifierExpressions) {
		    if (e.requiresPreState()) return true;
		 }
		 
		 return false;
	}

}

