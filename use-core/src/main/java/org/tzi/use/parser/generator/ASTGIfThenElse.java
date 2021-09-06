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
 * May 8th 2003 
 * @author  <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author  <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */

package org.tzi.use.parser.generator;

import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.gen.assl.statics.GIfThenElse;
import org.tzi.use.gen.assl.statics.GInstruction;
import org.tzi.use.gen.assl.statics.GValueInstruction;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;

public class ASTGIfThenElse extends ASTGInstruction {
    private Token fname;
    ASTGocl fCondition;
    List<ASTGInstruction> fThenInstructions;
    List<ASTGInstruction> fElseInstructions;
  
  
    public ASTGIfThenElse(ASTGocl condition,
                          List<ASTGInstruction> thenInstructions,
                          List<ASTGInstruction> elseInstructions,
                          Token t) {
        fCondition = condition;
        fThenInstructions = thenInstructions;
        fElseInstructions = elseInstructions;
        fname = t;
    }
  
    public GInstruction gen(Context ctx) throws SemanticException {
        GIfThenElse ifThenElse = new GIfThenElse();
        ctx.varTable().enterScope();
    
        try {
            GInstruction condition = fCondition.gen(ctx);
            if (! (condition instanceof GValueInstruction) ||
                ! ((GValueInstruction) condition).type().isTypeOfBoolean() ) {
                String err = "`" + condition + "' must be of type Boolean.";
                throw new SemanticException( fname, err );
            }
      
            ifThenElse.setConditionInstr( (GValueInstruction) condition );
      
            for (ASTGInstruction ins : fThenInstructions) {
                ifThenElse.addThenInstruction( ins.gen(ctx) );
            }

            for (ASTGInstruction ins : fElseInstructions) {
            	ifThenElse.addElseInstruction( ins.gen(ctx) );
            }

        } catch (SemanticException ex ) {
            ctx.reportError(ex);
        }
        //    ctx.loopVarNames().remove( fDecl.name().getText() );
        ctx.varTable().exitScope();
        return ifThenElse;
    }
}
