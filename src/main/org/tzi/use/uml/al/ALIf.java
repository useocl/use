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
public class ALIf extends ALAction {

    private Expression fExpr;
    private ALAction fThenList;
    private ALAction fElseList;

    public ALIf(Expression expr, ALAction thenList, ALAction elseList) {
        fExpr = expr;
        fThenList = thenList;
        fElseList = elseList;
    }

    public Expression getCondition() {
        return fExpr;
    }
    
    public ALAction getThen() {
        return fThenList;
    }
    
    public ALAction getElse() {
        return fElseList;
    }
    
    public void exec(EvalContext ctx) throws MSystemException {
        Value vcond = fExpr.eval(ctx);
        assert vcond.isBoolean() :  "non-boolean result must be caught by type checker";
        boolean cond = ((BooleanValue)vcond).isTrue();
        if (cond) {
            fThenList.exec(ctx);
        } else if (fElseList != null) {
            fElseList.exec(ctx);
        }
    }

    public String toString() {
        return "if " + fExpr + " then " + fThenList + 
            ((fElseList!=null)?(" else " + fElseList):"") + 
            " end ";
    }

    public void processWithVisitor(MMVisitor v) {
        v.visitALIf(this);
    }

}
