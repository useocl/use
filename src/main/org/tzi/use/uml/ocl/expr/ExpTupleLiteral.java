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

import java.util.HashMap;
import java.util.Map;

import org.tzi.use.uml.ocl.type.TupleType;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.TupleValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.StringUtil;

/**
 * Constant tuple literal.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public final class ExpTupleLiteral extends Expression {
    private Part[] fParts;

    public static class Part {
        private String fName;
        private Expression fExpr;

        public Part(String name, Expression expr) {
            fName = name;
            fExpr = expr;
        }

        public String toString() {
            return fName + ":" + fExpr;
        }
    }

    public ExpTupleLiteral(Part[] parts) {
        // set result type later
        super(null);

        // determine tuple type
        fParts = parts;
        TupleType.Part[] typeParts = new TupleType.Part[fParts.length];
        
        for (int i = 0; i < fParts.length; i++)
            typeParts[i] = new TupleType.Part(fParts[i].fName, fParts[i].fExpr.type());
        
        setResultType(TypeFactory.mkTuple(typeParts));
    }

    /**
     * Evaluates expression and returns result value.
     */
    public Value eval(EvalContext ctx) {
        ctx.enter(this);
        Value res = null;
        Map parts = new HashMap(fParts.length);
        
        for (int i = 0; i < fParts.length; i++) {
            parts.put(fParts[i].fName, fParts[i].fExpr.eval(ctx));
        }
        
        res = new TupleValue((TupleType) type(), parts);
        ctx.exit(this, res);
        return res;
    }

    public String toString() {
        return "Tuple {" + StringUtil.fmtSeq(fParts, ",") + "}";
    }
}