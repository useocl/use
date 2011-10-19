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
import org.tzi.use.gen.assl.statics.GOCLExpression;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTExpression;

public class ASTGocl extends ASTGValueInstruction {
  
    private ASTExpression fExpression;

    public ASTGocl(ASTExpression expression, Token t) {
        fExpression = expression;
    }

    public GInstruction gen(Context ctx) throws SemanticException {
        return new GOCLExpression( fExpression.gen(ctx) );
    }
    
    public ASTExpression getExpression() {
    	return fExpression;
    }
}
