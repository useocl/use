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

package org.tzi.use.parser.use;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.ParseErrorHandler;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MMultiplicity;
import org.tzi.use.uml.mm.ModelFactory;

/** 
 * Compiler for USE specifications, expressions, and commands. The
 * class has a set of static methods providing a simple interface to
 * the lexer, parser and code generation process.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public class USECompiler {

    // utility class
    private USECompiler() {}
    

    /**
     * Compiles a specification.
     *
     * @param  in the source to be compiled
     * @param  inName name of the source stream
     * @param  err output stream for error messages
     * @return MModel null if there were any errors
     */
    public static MModel compileSpecification(String in, 
                                              String inName,
                                              PrintWriter err,
                                              ModelFactory factory) {
    	
    	InputStream inStream = new ByteArrayInputStream(in.getBytes());
    	return USECompiler.compileSpecification(inStream, inName, err, factory);
    }
    /**
     * Compiles a specification.
     *
     * @param  in the source to be compiled
     * @param  inName name of the source stream
     * @param  err output stream for error messages
     * @return MModel null if there were any errors
     */
    public static MModel compileSpecification(InputStream in, 
                                              String inName,
                                              PrintWriter err,
                                              ModelFactory factory) {
        MModel model = null;
        ParseErrorHandler errHandler = new ParseErrorHandler(inName, err);
        
        ANTLRInputStream aInput;
		try {
			aInput = new ANTLRInputStream(in);
			aInput.name = inName;
		} catch (IOException e1) {
			err.println(e1.getMessage());
			return model;
		}
		
        USELexer lexer = new USELexer(aInput);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        USEParser parser = new USEParser(tokenStream);
        
        lexer.init(errHandler);
        parser.init(errHandler);
        
        try {
            // Parse the specification
            ASTModel astModel = parser.model();
            if (errHandler.errorCount() == 0 ) {

                // Generate code
                Context ctx = new Context(inName, err, null, factory);
                model = astModel.gen(ctx);
                if (ctx.errorCount() > 0 )
                    model = null;
            }
        } catch (RecognitionException e) {
            err.println(parser.getSourceName() +":" + 
                        e.line + ":" +
                        e.charPositionInLine + ": " + 
                        e.getMessage());
        }
        
        err.flush();
        return model;
    }
    
    /**
     * Compiles a specification.
     *
     * @param  in the source to be compiled
     * @param  inName name of the source stream
     * @param  err output stream for error messages
     * @return MModel null if there were any errors
     */
    public static MMultiplicity compileMultiplicity(String in, 
                                             String inName,
                                             PrintWriter err,
                                             ModelFactory factory) {
    	
    	InputStream inStream = new ByteArrayInputStream(in.getBytes());
    	return USECompiler.compileMultiplicity(inStream, inName, err, factory);
    }
    /**
     * Compiles a specification.
     *
     * @param  in the source to be compiled
     * @param  inName name of the source stream
     * @param  err output stream for error messages
     * @return MModel null if there were any errors
     */
    public static MMultiplicity compileMultiplicity(InputStream in, 
                                              String inName,
                                              PrintWriter err,
                                              ModelFactory factory) {
        MMultiplicity mul = null;
        ParseErrorHandler errHandler = new ParseErrorHandler(inName, err);
        
        ANTLRInputStream aInput;
		try {
			aInput = new ANTLRInputStream(in);
			aInput.name = inName;
		} catch (IOException e1) {
			err.println(e1.getMessage());
			return mul;
		}
		
        USELexer lexer = new USELexer(aInput);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        USEParser parser = new USEParser(tokenStream);
        
        lexer.init(errHandler);
        parser.init(errHandler);
        
        try {
            // Parse the specification
            ASTMultiplicity astMul = parser.multiplicity();
            if (errHandler.errorCount() == 0 ) {

                // Generate code
                Context ctx = new Context(inName, err, null, factory);
                mul = astMul.gen(ctx);
                if (ctx.errorCount() > 0 )
                	mul = null;
            }
        } catch (RecognitionException e) {
            err.println(parser.getSourceName() +":" + 
                        e.line + ":" +
                        e.charPositionInLine + ": " + 
                        e.getMessage());
        }
        
        err.flush();
        return mul;
    }
}
