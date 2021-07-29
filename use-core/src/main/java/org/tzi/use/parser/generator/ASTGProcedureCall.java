/*
 * This is source code of the Snapshot Generator, an extension for USE
 * to generate (valid) system states of UML models.
 * Copyright (C) 2001 Joern Bohling, University of Bremen
 *
 * About USE:
 *   USE - UML based specification environment
 *   Copyright (C) 1999,2000,2001 Mark Richters, University of Bremen
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

/**
 * March 22th 2001 
 * @author  Joern Bohling
 */

package org.tzi.use.parser.generator;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.gen.assl.statics.GProcedure;
import org.tzi.use.gen.tool.GProcedureCall;
import org.tzi.use.gen.tool.GSignature;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type;

public class ASTGProcedureCall extends AST {
    private Token fName;
    private List<ASTExpression> fParameter;

    public ASTGProcedureCall(Token name) {
        fName = name;
        fParameter = new ArrayList<ASTExpression>();
    }

    public void addParameter( ASTExpression expr ) {
        fParameter.add(expr);
    }

    public GProcedureCall gen(Context ctx) throws SemanticException {
        List<Expression> params = new ArrayList<Expression>();
        List<Type> paramTypes = new ArrayList<Type>();
        
        for (ASTExpression astExp : fParameter) {
        	Expression exp = astExp.gen(ctx); 
        	params.add(exp);
            paramTypes.add(exp.type());
        }
        
        GSignature sig = new GSignature(fName.getText(), paramTypes);
        GProcedure proc = sig.findMatching(ctx.getProcedures());
        
        if (proc == null) return null;
        
        return new GProcedureCall( proc, params );
    }    
}
