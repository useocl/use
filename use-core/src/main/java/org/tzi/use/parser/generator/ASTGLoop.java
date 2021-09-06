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

import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.gen.assl.statics.GInstruction;
import org.tzi.use.gen.assl.statics.GLoop;
import org.tzi.use.gen.assl.statics.GValueInstruction;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTVariableDeclaration;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.type.SequenceType;

public class ASTGLoop extends ASTGInstruction {
	Token fName;
    ASTVariableDeclaration fDecl;
    ASTGocl fSequence;
    List<ASTGInstruction> fInstructions;
    
    public ASTGLoop( ASTVariableDeclaration decl,
                     ASTGocl sequence,
                     List<ASTGInstruction> instructions,
                     Token t) {
        fDecl = decl;
        fSequence = sequence;
        fInstructions = instructions;
        fName = t;
    }

    public GInstruction gen(Context ctx) throws SemanticException {
        GLoop loop = new GLoop();
        ctx.varTable().enterScope();

        try {
            VarDecl vardecl = fDecl.gen(ctx);
            if (ctx.varTable().lookup(fDecl.name().getText()) != null) {
                String err = "Variable `" + fDecl.name().getText() + "' was " +
                    "previously declared.";
                throw new SemanticException(fDecl.name(), err);
            }

            ctx.varTable().add( fDecl.name(), vardecl.type() );
            // does NOT throw an exception when variable is redefined
            // because the context scope has changed!!
            ctx.loopVarNames().add( fDecl.name().getText() );
            loop.setDecl( vardecl );

            GInstruction sequence = fSequence.gen(ctx);
            if (! (sequence instanceof GValueInstruction) ||
                ! ((GValueInstruction) sequence).type().isTypeOfSequence() ) {
                String err = "`" + sequence + "' must be of type Sequence.";
                // Fixme: The position of the error must be the
                // position of fSequence.
                throw new SemanticException( fName, err );
            }

            if (! ((SequenceType)((GValueInstruction) sequence).type())
                .elemType().conformsTo(vardecl.type()) ) {
                String err = "The elements of `"+sequence+"' must be of " +
                    "type " + vardecl.type() + " (or a subtype)";
                throw new SemanticException( fDecl.name(), err );
            }

            loop.setSequenceInstr( (GValueInstruction) sequence );
        
            for (ASTGInstruction ins : fInstructions) {
                loop.addInstruction( ins.gen(ctx) );
            }
        } catch (SemanticException ex ) {
            ctx.reportError(ex);
        }
        ctx.loopVarNames().remove( fDecl.name().getText() );
        ctx.varTable().exitScope();
        return loop;
    }
}
        
