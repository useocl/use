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
package org.tzi.use.parser.use;

import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.al.ALAction;
import org.tzi.use.uml.al.ALActionList;
import org.tzi.use.uml.al.ALIf;
import org.tzi.use.uml.ocl.expr.Expression;

/**
 * @author green
 */
public class ASTALIf extends ASTALAction {

    private ASTExpression fExpr;
    private ASTALActionList fThenList;
    private ASTALActionList fElseList;

    public ASTALIf(ASTExpression expr, ASTALActionList thenList, ASTALActionList elseList) {
        fExpr = expr;
        fThenList = thenList;
        fElseList = elseList;
    }

    /* (non-Javadoc)
     * @see org.tzi.use.parser.use.ASTALAction#gen(org.tzi.use.parser.Context)
     */
    public ALAction gen(Context ctx) throws SemanticException {
        Expression expr = fExpr.gen(ctx);
        try {
            ALActionList thenList = (ALActionList) fThenList.gen(ctx);
            ALActionList elseList = null;
            if (fElseList != null)
                elseList = (ALActionList) fElseList.gen(ctx);
            
            return new ALIf(expr,thenList,elseList);
        } catch (SemanticException e) {
            throw new RuntimeException(e);
        }
    }

}
