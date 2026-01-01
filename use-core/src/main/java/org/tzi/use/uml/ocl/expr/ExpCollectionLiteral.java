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

package org.tzi.use.uml.ocl.expr;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.type.UniqueLeastCommonSupertypeDeterminator;
import org.tzi.use.uml.ocl.value.SequenceValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.StringUtil;

/**
 * Abstract base class for collection literals.
 * 
 * @author  Mark Richters
 */
public abstract class ExpCollectionLiteral extends Expression {
    
	private final String fKind;

	protected final Expression[] fElemExpr;

    protected ExpCollectionLiteral(String kind, Expression[] elemExpr) {
        super(null);
        fKind = kind;
        fElemExpr = elemExpr;
    }

    /**
	 * @return the fKind
	 */
	public String getKind() {
		return fKind;
	}
	
    /**
     * Returns the value for the type parameter of this collection.
     */
    protected Type inferElementType()
        throws ExpInvalidException
    {
     if (this.fElemExpr.length == 0)
       return TypeFactory.mkVoidType();
     else if (this.fElemExpr.length == 1)
       return this.fElemExpr[0].type();

     // If all elements are the same concrete collection literal class
     boolean allSameLiteralKind = true;
     Class<?> litClass = null;
     for (Expression e : fElemExpr) {
         if (!(e instanceof ExpCollectionLiteral)) {
             allSameLiteralKind = false;
             break;
         }
         if (litClass == null) litClass = e.getClass();
         else if (! litClass.equals(e.getClass())) {
             allSameLiteralKind = false;
             break;
         }
     }
     if (allSameLiteralKind && litClass != null) {
         // collect inner element types
         Set<Type> innerTypes = new HashSet<Type>();
         for (Expression e : fElemExpr) {
             Type et = ((CollectionType) e.type()).elemType();
             innerTypes.add(et);
         }
         Type innerResult = new UniqueLeastCommonSupertypeDeterminator().calculateFor(innerTypes);
         if (innerResult != null) {
             if (litClass.equals(ExpSequenceLiteral.class)) {
                 return TypeFactory.mkSequence(innerResult);
             } else if (litClass.equals(ExpSetLiteral.class)) {
                 return TypeFactory.mkSet(innerResult);
             } else if (litClass.equals(ExpBagLiteral.class)) {
                 return TypeFactory.mkBag(innerResult);
             } else if (litClass.equals(ExpOrderedSetLiteral.class)) {
                 return TypeFactory.mkOrderedSet(innerResult);
             }
         }
     }

     Set<Type> types = new HashSet<Type>();
     for (int i = 0; i < fElemExpr.length; i++) {
         Type t = fElemExpr[i].type();
         types.add(t);
     }

     Type result = new UniqueLeastCommonSupertypeDeterminator().calculateFor(types);

     if (result == null)
       throw new ExpInvalidException("No common supertype of the element types");

     // If result is a generic Collection(T) but all element types are concrete collections
     // of the same kind (e.g., all Sequence(T')), prefer the concrete collection kind
     // and compute inner common element type.
     boolean allCollections = true;
     Class<?> kindClass = null;
     for (Type t : types) {
         if (!(t instanceof org.tzi.use.uml.ocl.type.CollectionType)) {
             allCollections = false;
             break;
         }
         if (kindClass == null) kindClass = t.getClass();
         else if (! kindClass.equals(t.getClass())) {
             allCollections = false;
             break;
         }
     }
     if (allCollections && kindClass != null && result instanceof org.tzi.use.uml.ocl.type.CollectionType) {
         java.util.Set<org.tzi.use.uml.ocl.type.Type> innerTypes = new java.util.HashSet<>();
         for (Type t : types) {
             org.tzi.use.uml.ocl.type.CollectionType ct = (org.tzi.use.uml.ocl.type.CollectionType) t;
             innerTypes.add(ct.elemType());
         }
         org.tzi.use.uml.ocl.type.Type innerResult = new org.tzi.use.uml.ocl.type.UniqueLeastCommonSupertypeDeterminator().calculateFor(innerTypes);
         if (innerResult != null) {
             if (kindClass.equals(org.tzi.use.uml.ocl.type.SequenceType.class)) {
                 return org.tzi.use.uml.ocl.type.TypeFactory.mkSequence(innerResult);
             } else if (kindClass.equals(org.tzi.use.uml.ocl.type.SetType.class)) {
                 return org.tzi.use.uml.ocl.type.TypeFactory.mkSet(innerResult);
             } else if (kindClass.equals(org.tzi.use.uml.ocl.type.BagType.class)) {
                 return org.tzi.use.uml.ocl.type.TypeFactory.mkBag(innerResult);
             } else if (kindClass.equals(org.tzi.use.uml.ocl.type.OrderedSetType.class)) {
                 return org.tzi.use.uml.ocl.type.TypeFactory.mkOrderedSet(innerResult);
             }
         }
     }

     return result;
    }

	@Override
	protected boolean childExpressionRequiresPreState() {
		for (Expression elementExpr : fElemExpr) {
			if (elementExpr.requiresPreState())
				return true;
		}
		
		return false;
	}

	/**
     * Evaluates argument expressions.
     */
    protected Value[] evalArgs(EvalContext ctx) {
    	
        List<Value> argValues = new LinkedList<Value>();
        
        for (Expression exp : fElemExpr) {
        	Value eValue = exp.eval(ctx);
            if (exp instanceof ExpRange) {
            	if (eValue.isUndefined()) {
            		argValues.add(eValue);
            	} else {
            		SequenceValue sVal = (SequenceValue)eValue;
            		for (Value v : sVal.collection()) {
            			argValues.add(v);
            		}
            	}
            } else { 
            	argValues.add(eValue);
            }
        }
        
        return argValues.toArray(new Value[argValues.size()]);
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        sb.append(fKind)
          .append(" {");
        StringUtil.fmtSeq(sb, fElemExpr, ",");
        return sb.append("}");
    }

    public Expression[] getElemExpr() {
        return fElemExpr;
    }
}

