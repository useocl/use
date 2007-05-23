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
package org.tzi.use.uml.al;

import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MSystemException;

/**
 * @author green
 */
public class ALWhile extends ALAction {

    private Expression fExpr;
    private ALActionList fBody;

    public ALWhile(Expression expr, ALActionList body) {
        fExpr = expr;
        fBody = body;
    }

    public Expression getCondition() {
        return fExpr;
    }

    public ALAction getBody() {
        return fBody;
    }
    
    public void exec(EvalContext ctx) throws MSystemException {
        while( evalCondition(ctx) == true ) {
            fBody.exec(ctx);
        }
    }

    private boolean evalCondition(EvalContext ctx) {
        Value vcond = fExpr.eval(ctx);
        assert vcond.isBoolean() :  "non-boolean result must be caught by type checker";
        return ((BooleanValue)vcond).isTrue();
    }

    public String toString() {
        return "while " + fExpr.toString()  +" do " 
            + fBody.toString() +" wend ";
    }

    public void processWithVisitor(MMVisitor v) {
        v.visitALWhile(this);
    }

}
