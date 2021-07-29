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

import org.antlr.runtime.Token;
import org.tzi.use.gen.assl.statics.GInstruction;
import org.tzi.use.gen.assl.statics.GValueInstruction;
import org.tzi.use.gen.assl.statics.GVariableAssignment;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.ocl.type.Type;

public class ASTGVariableAssignment extends ASTGInstruction {
    private Token fTarget;
    private ASTGValueInstruction fSource;

    public ASTGVariableAssignment( Token target,
                                   ASTGValueInstruction source ) {
        fTarget = target;
        fSource = source;
    }

    public GInstruction gen(Context ctx) throws SemanticException {
        // check whether the target variable was declared
        Type type = ctx.varTable().lookup( fTarget.getText() );
        if (type == null ) {
            String err = "Variable "+fTarget.getText()+" must be declared.";
            throw new SemanticException( fTarget, err );
        }

        // the target variable must not be declared within a
        // loop instruction
        if (ctx.loopVarNames().contains(fTarget.getText()) ) {
            String err = "Variable "+fTarget.getText()+" was declared " +
                "within a loop instruction and can't be the target of an " +
                "assignment.";
            throw new SemanticException( fTarget, err );
        }
    
        // fSource must be a GValueInstruction
        GInstruction source = fSource.gen(ctx);
        if (! (source instanceof GValueInstruction) ) {
            String err = "`" + source + "' can't be the source of an " +
                "assignment, because it has no return value.";
            throw new SemanticException( fTarget, err );
        }

        GValueInstruction valuesource = (GValueInstruction) source;
        // The type of the source must be the same type 
        // or a subtype of the target variable.
        if (! valuesource.type().conformsTo(type)) {
            String err = "Invalid assignment: " +
                "`"+fTarget.getText()+"' is of type " + type + "." +
                "`" + valuesource + "' is of type " + valuesource.type() + ".";
            throw new SemanticException( fTarget, err );
        }
    
        GVariableAssignment assignment =
            new GVariableAssignment( fTarget.getText(),
                                     valuesource );

        return assignment;
    }
}
