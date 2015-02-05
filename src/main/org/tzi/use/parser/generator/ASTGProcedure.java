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
import org.tzi.use.gen.assl.statics.GInstrTry;
import org.tzi.use.gen.assl.statics.GProcedure;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTVariableDeclaration;
import org.tzi.use.uml.ocl.expr.VarDecl;

public class ASTGProcedure extends AST {
    private Token fName;
    private List<ASTVariableDeclaration> fParameterDecls;
    private List<ASTVariableDeclaration> fLocalDecls;
    private List<ASTGInstruction> fInstructions;
    private GProcedure proc;
    
    public ASTGProcedure (
    		Token name, 
    		List<ASTVariableDeclaration> parameterDecls, 
    		List<ASTVariableDeclaration> localDecls, 
    		List<ASTGInstruction> instructions ) {
        fName = name;
        fParameterDecls = parameterDecls;
        fLocalDecls = localDecls;
        fInstructions = instructions;
    }

	/**
	 * @param ctx
	 * @return
	 */
	public GProcedure genSignature(Context ctx) {
		proc = new GProcedure(fName.getText());		
        ctx.varTable().enterScope();
        
        try {
            for (ASTVariableDeclaration astvardecl : fParameterDecls) {
                VarDecl vardecl = astvardecl.gen(ctx);
                ctx.varTable().add( astvardecl.name(), vardecl.type() );
                // throws an exception when variable is redefined
                proc.addParameterDecl( vardecl );
            }

            for (ASTVariableDeclaration astvardecl : fLocalDecls) {
                VarDecl vardecl = astvardecl.gen(ctx);
                ctx.varTable().add( astvardecl.name(), vardecl.type() );
                // throws an exception when variable is redefined
                proc.addLocalDecl( vardecl );
            }
        } catch (SemanticException ex ) {
            ctx.reportError(ex);
        }
        
        ctx.varTable().exitScope();

        return proc;
	}

	/**
	 * @param ctx
	 * @return
	 */
	public void genBody(Context ctx) {
		GInstrTry.numTries = 0;
        ctx.varTable().enterScope();

        try {
        	for (int i = 0; i < fParameterDecls.size(); ++i) {
        		ASTVariableDeclaration astvardecl = fParameterDecls.get(i);
                VarDecl vardecl = proc.parameterDecls().get(i);
                ctx.varTable().add( astvardecl.name(), vardecl.type() );
            }

        	for (int i = 0; i < fLocalDecls.size(); ++i) {
        		ASTVariableDeclaration astvardecl = fLocalDecls.get(i);
                VarDecl vardecl = proc.localDecls().get(i);
                ctx.varTable().add( astvardecl.name(), vardecl.type() );
            }
            
        	for (ASTGInstruction ins : fInstructions) {
                proc.addInstruction( ins.gen(ctx) );
            }
        } catch (SemanticException ex ) {
            ctx.reportError(ex);
        }
        
        ctx.varTable().exitScope();
	}
}
