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

import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.Value;

/**
 * Variable expression.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public final class ExpVariable extends Expression  {
    private String fVarname;

    public ExpVariable(String varname, Type t) {
        super(t);
        fVarname = varname;
    }

    /**
     * Evaluates expression and returns result value.
     */
    public Value eval(EvalContext ctx) {
        ctx.enter(this);
        Value res = ctx.getVarValue(fVarname);
        if (res == null ) 
            throw new RuntimeException("unbound variable `" + 
                                       fVarname + "'.");
        ctx.exit(this, res);
        return res;
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        return sb.append(fVarname).append(atPre());
    }
    
    public String name(){
        return "var";
    }
    
    public String getVarname() {
        return fVarname;
    }

	@Override
	public void processWithVisitor(ExpressionVisitor visitor) {
		visitor.visitVariable(this);
	}

	@Override
	protected boolean childExpressionRequiresPreState() {
		return false;
	}
}

