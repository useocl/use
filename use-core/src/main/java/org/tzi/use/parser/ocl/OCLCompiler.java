/*
 * OCL - UML based specification environment
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

package org.tzi.use.parser.ocl;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.ParseErrorHandler;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.Symtable;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MSystemState;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class OCLCompiler {

    private OCLCompiler() {} // no instances
    
    
    /**
	 * Compiles an expression.
	 * @param model the model
	 * @param in the source to compile
	 * @param inName name of the source stream
	 * @param err output stream for error messages
	 * @param globalBindings the variable bindings
	 * @return <code>null</code> if there were any errors
	 */
    public static Expression compileExpression(MModel model,
	                                           String in, 
	                                           String inName, 
	                                           PrintWriter err,
	                                           VarBindings globalBindings) {
		
		return compileExpression(
				model,
				new ByteArrayInputStream(in.getBytes()), 
				inName, 
				err, 
				globalBindings);
	}
    
    /**
	 * Compiles an expression.
	 * @param model the model
	 * @param in the source to compile
	 * @param inName name of the source stream
	 * @param err output stream for error messages
	 * @param varTable the variable bindings
	 * @return <code>null</code> if there were any errors
	 */
    public static Expression compileExpression(MModel model,
	                                           String in, 
	                                           String inName, 
	                                           PrintWriter err,
	                                           Symtable varTable) {
		
		return compileExpression(
				model, 
				null,
				new ByteArrayInputStream(in.getBytes()), 
				inName, 
				err, 
				null,
				varTable);
	}
    
    /**
	 * Compiles an expression.
	 * @param model the model
	 * @param in the source to compile
	 * @param inName name of the source stream
	 * @param err output stream for error messages
	 * @param globalBindings the variable bindings
	 * @return null if there were any errors
	 */
    public static Expression compileExpression(MModel model,
	                                           InputStream in, 
	                                           String inName, 
	                                           PrintWriter err,
	                                           VarBindings globalBindings) {
		
		return compileExpression(
				model, 
				null, 
				in, 
				inName, 
				err, 
				globalBindings);
	}
    

	/**
	 * Compiles an expression.
	 * @param model the model
	 * @param state the system state
	 * @param input the source to compile
	 * @param inName name of the source stream
	 * @param err output stream for error messages
	 * @param globalBindings the variable bindings
	 * @return null if there were any errors
	 */
    public static Expression compileExpression(MModel model,
    		                                   MSystemState state,
            								   String input, 
            								   String inName, 
            								   PrintWriter err,
            								   VarBindings globalBindings)
    {
    	return OCLCompiler.compileExpression(
    			model, 
    			state, 
    			new ByteArrayInputStream(input.getBytes()), 
    			inName, 
    			err, 
    			globalBindings);
    }
    
    /**
     * Compiles an expression.
     *
     * @param  in the source to be compiled
     * @param  inName name of the source stream
     * @param  err output stream for error messages
     * @return Expression null if there were any errors
     */
    public static Expression compileExpression(MModel model,
                                               MSystemState state,
                                               InputStream in, 
                                               String inName, 
                                               PrintWriter err,
                                               VarBindings globalBindings) {
    	return compileExpression(model, state, in, inName, err, globalBindings, null);
    }
    
    /**
     * Compiles an expression.
     *
     * @param  in the source to be compiled
     * @param  inName name of the source stream
     * @param  err output stream for error messages
     * @return Expression null if there were any errors
     */
    private static Expression compileExpression(MModel model,
                                               MSystemState state,
                                               InputStream in, 
                                               String inName, 
                                               PrintWriter err,
                                               VarBindings globalBindings,
                                               Symtable varTable) {
        Expression expr = null;
        ParseErrorHandler errHandler = new ParseErrorHandler(inName, err);
        
        ANTLRInputStream aInput;
		try {
			aInput = new ANTLRInputStream(in);
			aInput.name = inName;
		} catch (IOException e1) {
			err.println(e1.getMessage());
			return expr;
		}
		
        OCLLexer lexer = new OCLLexer(aInput);
        
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        OCLParser parser = new OCLParser(tokenStream);
        
        lexer.init(errHandler);
        parser.init(errHandler);
        
        
        
        try {
            // Parse the input expression
            ASTExpression astExpr = parser.expressionOnly();

            if (errHandler.errorCount() == 0 ) {
    
                // Generate code
                Context ctx = new Context(inName, err, globalBindings, null);
                ctx.setModel(model);
                ctx.setSystemState(state);
                if (varTable != null)
                	ctx.setVarTable(varTable);
                
                expr = astExpr.gen(ctx);
    
                // check for semantic errors
                if (ctx.errorCount() > 0 )
                    expr = null;
            }
        } catch (RecognitionException e) {
            err.println(parser.getSourceName() +":" + 
                        e.line + ":" +
                        e.charPositionInLine + ": " + 
                        e.getMessage());
        } catch (SemanticException e) {
            err.println(e.getMessage());
        } catch (NullPointerException e) {
        	// Only throw if not handled before
        	if (errHandler.errorCount() == 0)
        		throw e;
        }
        
        err.flush();
        return expr;
    }

    /**
     * Compiles a type.
     *
     * @param  in the source to be compiled
     * @param  inName name of the source stream
     * @param  err output stream for error messages
     * @return Type null if there were any errors
     */
    public static Type compileType(MModel model, 
    							   String in, 
                                   String inName, 
                                   PrintWriter err) {
    	
    	InputStream inStream = new ByteArrayInputStream(in.getBytes());
    	return OCLCompiler.compileType(model, inStream, inName, err);
    }
    
    /**
     * Compiles a type.
     *
     * @param  in the source to be compiled
     * @param  inName name of the source stream
     * @param  err output stream for error messages
     * @return Type null if there were any errors
     */
    public static Type compileType(MModel model, 
    							   InputStream in, 
                                   String inName, 
                                   PrintWriter err) {
        Type type = null;
        ParseErrorHandler errHandler = new ParseErrorHandler(inName, err);
        
        ANTLRInputStream aInput;
		try {
			aInput = new ANTLRInputStream(in);
		} catch (IOException e1) {
			err.println(e1.getMessage());
			return type;
		}
		
        OCLLexer lexer = new OCLLexer(aInput);
        
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        OCLParser parser = new OCLParser(tokenStream);
        
        lexer.init(errHandler);
        parser.init(errHandler);
        
        try {
            // Parse the input expression
            ASTType astType = parser.typeOnly();
            //System.err.println("***" + astExpr.toString());
            if (errHandler.errorCount() == 0 ) {
    
                // Generate code
                Context ctx = new Context(inName, err, null, null);
                ctx.setModel(model);
                type = astType.gen(ctx);
    
                // check for semantic errors
                if (ctx.errorCount() > 0 )
                    type  = null;
            }
        } catch (RecognitionException e) {
            err.println(parser.getSourceName() +":" + 
                        e.line + ":" +
                        e.charPositionInLine + ": " + 
                        e.getMessage());
        } catch (SemanticException e) {
            err.println(e.getMessage());
        }
        
        err.flush();
        return type;
    }
}
