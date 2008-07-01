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

package org.tzi.use.parser.ocl;

import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.ocl.expr.VarInitializer;
import org.tzi.use.uml.ocl.type.Type;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTVariableInitialization extends AST {
    private Token fName;
    private ASTType fType;
    private ASTExpression fExpr;

    public ASTVariableInitialization(Token name, ASTType type, 
                                     ASTExpression expr) {
        fName = name;
        fType = type;
        fExpr = expr;
    }

    public VarInitializer gen(Context ctx) throws SemanticException {
        Type type = fType.gen(ctx);
        try {
            return new VarInitializer(fName.getText(), type,
                                      fExpr.gen(ctx));
        } catch (ExpInvalidException ex) {
            throw new SemanticException(fName, ex);
        }
    }

    public Token nameToken() {
        return fName;
    }

    public String toString() {
        return "(" + fName + " " + fType + " " + fExpr + ")";
    }
}
