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

$Id$

package org.tzi.use.parser.ocl;

import org.tzi.use.parser.Context;
import org.tzi.use.parser.MyToken;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.expr.ExpAllInstances;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.TypeFactory;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTAllInstancesExpression extends ASTExpression {
    private MyToken fToken;

    public ASTAllInstancesExpression(MyToken token) {
        fToken = token;
    }

    public Expression gen(Context ctx) throws SemanticException {
        Expression res = null;
        String name = fToken.getText();

        // check for object type
        MClass cls = ctx.model().getClass(name);
        if (cls == null )
            throw new SemanticException(fToken,
                                        "Expected object type, found `" + name + "'.");

        try {
            res = new ExpAllInstances(TypeFactory.mkObjectType(cls));
            if (isPre()) res.setIsPre();
        } catch (ExpInvalidException ex) {
            throw new SemanticException(fToken, ex);
        }
        return res;
    }
}
