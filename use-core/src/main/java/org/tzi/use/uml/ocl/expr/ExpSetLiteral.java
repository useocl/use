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

import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.SetValue;
import org.tzi.use.uml.ocl.value.Value;

/**
 * Constant set literal.
 *
 * @author  Mark Richters
 */
public final class ExpSetLiteral extends ExpCollectionLiteral {

    public ExpSetLiteral(Expression[] elemExpr) 
        throws ExpInvalidException
    {
        super("Set", elemExpr);
        Type elemType = inferElementType();
        // Debug: print inferred static element type in constructor
        try {
            System.err.println("ExpSetLiteral.<init>: inferred elemType=" + (elemType == null ? "<null>" : elemType.qualifiedName()));
        } catch (Throwable t) {}
        // If inferElementType returned a generic Collection(T) but the element expressions
        // are concrete collection literals of same kind, prefer that concrete type.
        if (elemType instanceof org.tzi.use.uml.ocl.type.CollectionType) {
            boolean allSeq = true;
            for (Expression e : elemExpr) {
                if (!(e instanceof ExpSequenceLiteral)) { allSeq = false; break; }
            }
            if (allSeq) {
                // element type should be Sequence(inner)
                Type inner = ((org.tzi.use.uml.ocl.type.CollectionType)elemType).elemType();
                elemType = org.tzi.use.uml.ocl.type.TypeFactory.mkSequence(inner);
            }
        }
        setResultType(TypeFactory.mkSet(elemType));
    }

    /**
     * Evaluates expression and returns result value.
     */
    public Value eval(EvalContext ctx) {
        ctx.enter(this);
        // Evaluate element arguments first
        Value[] argValues = evalArgs(ctx);

        // Debug: print runtime classes and runtime types of the evaluated elements
        try {
            StringBuilder dbg = new StringBuilder();
            dbg.append("ExpSetLiteral.eval: argCount=").append(argValues.length).append("\n");
            for (Value v : argValues) {
                dbg.append("  valClass=").append(v.getClass().getName());
                try { dbg.append(", runtimeType=").append(v.getRuntimeType().qualifiedName()); } catch (Throwable t) { dbg.append(", runtimeType=<err>"); }
                dbg.append("\n");
            }
            System.err.println(dbg.toString());
        } catch (Throwable t) {}

         // Determine element type for SetValue. Prefer runtime-detected concrete
         // collection element types (e.g., Sequence(inner)) when elements are
         // themselves collections of a concrete kind.
         org.tzi.use.uml.ocl.type.Type elemType = ((org.tzi.use.uml.ocl.type.CollectionType) type()).elemType();

        boolean allCollectionValues = true;
        boolean allSequence = true;
        java.util.Set<org.tzi.use.uml.ocl.type.Type> innerTypes = new java.util.HashSet<>();
        for (Value v : argValues) {
            if (!(v instanceof org.tzi.use.uml.ocl.value.CollectionValue)) {
                allCollectionValues = false;
                break;
            }
            org.tzi.use.uml.ocl.value.CollectionValue cv = (org.tzi.use.uml.ocl.value.CollectionValue) v;
            org.tzi.use.uml.ocl.type.Type rt = cv.getRuntimeType();
            if (!(rt instanceof org.tzi.use.uml.ocl.type.SequenceType)) {
                allSequence = false;
            } else {
                innerTypes.add(((org.tzi.use.uml.ocl.type.CollectionType) rt).elemType());
            }
        }

        if (allCollectionValues && allSequence && !innerTypes.isEmpty()) {
            org.tzi.use.uml.ocl.type.Type innerLcs = new org.tzi.use.uml.ocl.type.UniqueLeastCommonSupertypeDeterminator().calculateFor(innerTypes);
            if (innerLcs != null) {
                elemType = org.tzi.use.uml.ocl.type.TypeFactory.mkSequence(innerLcs);
            }
        }

        Value res = new SetValue(elemType, argValues);
        ctx.exit(this, res);
        return res;
    }

	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitSetLiteral(this);		
	}
}
