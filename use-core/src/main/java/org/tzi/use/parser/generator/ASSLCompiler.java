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

package org.tzi.use.parser.generator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.tzi.use.gen.assl.statics.GProcedure;
import org.tzi.use.gen.tool.GProcedureCall;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.ParseErrorHandler;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.use.ASTConstraintDefinition;
import org.tzi.use.uml.mm.GeneratorModelFactory;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.sys.MSystemState;

public class ASSLCompiler {

    private ASSLCompiler() {} // no instances
    
    /**
     * Compiles generator procedures
     *
     * @param  model the model, which is the context of the procedure
     * @param  in the source to be compiled
     * @param  inName name of the source stream
     * @param  err output stream for error messages
     * @return List the comiled procedures (GProcedure)
     */
    public static List<GProcedure> compileProcedures(MModel model,
                                         InputStream in,
                                         String inName,
                                         PrintWriter err) {
        
    	List<ASTGProcedure> astgProcList = new ArrayList<ASTGProcedure>();
        List<GProcedure> procedures = new ArrayList<GProcedure>();
        ParseErrorHandler errHandler = new ParseErrorHandler(inName, err);
        
        ANTLRInputStream aInput;
		try {
			aInput = new ANTLRInputStream(in);
			aInput.name = inName;
		} catch (IOException e1) {
			err.println(e1.getMessage());
			return null;
		}
		
        GeneratorLexer lexer = new GeneratorLexer(aInput);
        CommonTokenStream tStream = new CommonTokenStream(lexer);
        
        GeneratorParser parser = new GeneratorParser(tStream);
        lexer.init(errHandler);
        parser.init(errHandler);
        boolean error = false;
    
        try {
            astgProcList = parser.procedureListOnly();
            if (errHandler.errorCount() != 0 )
                error = true;
            else {
                Iterator<ASTGProcedure> astgproc = astgProcList.iterator();
                
                // Generate signatures first, to allow procedure calls
                while (astgproc.hasNext() && !error ) {
                    Context ctx = new Context(inName, err, null, null);
                    ctx.setModel(model);
                    ASTGProcedure astgProc = astgproc.next();
                    GProcedure proc = astgProc.genSignature(ctx);
                    if (ctx.errorCount() != 0 )
                        error = true;
                    else {
                        boolean ignore = false;
                        Iterator<GProcedure> it = procedures.iterator();
                        while (it.hasNext())
                            if ( it.next().getSignature().equals(proc.getSignature() ) ) {
                                err.println("Warning: Ignoring redefinition of " + proc);
                                ignore = true;
                            }
                        if (!ignore)
                            procedures.add( proc );
                    }
                }
                
                astgproc = astgProcList.iterator();
                while (astgproc.hasNext() && !error ) {
                	Context ctx = new Context(inName, err, null, null);
                    ctx.setModel(model);
                    ctx.setProcedures(procedures);
                    ASTGProcedure astgProc = astgproc.next();
                    astgProc.genBody(ctx);
                    if (ctx.errorCount() != 0 )
                        error = true;
                }
                
            }
        } catch (RecognitionException e) {
            err.println(parser.getSourceName() +":" + 
                        e.line + ":" +
                        e.charPositionInLine + ": " + 
                        e.getMessage());
        }
        err.flush();
        if (error)
            return null;
        else
            return procedures;
    }

    /**
     * Compiles a procedure call (call given as String)
     *
     * @param  model the model of the call
     * @param  systemState the context of the call
     * @param  in the input source stream
     * @param  inName the name of the input source stream
     * @param  err output stream for error messages
     * @return GProcedureCall the compiled call or null
     */
    public static GProcedureCall compileProcedureCall(MModel model,
                                                      MSystemState systemState,
                                                      List<GProcedure> procedures,
                                                      String in, 
                                                      String inName,
                                                      PrintWriter err) {
    	InputStream stream = new ByteArrayInputStream(in.getBytes());
    	return ASSLCompiler.compileProcedureCall(model, systemState, procedures, stream, inName, err);
    }
    /**
     * Compiles a procedure call
     *
     * @param  model the model of the call
     * @param  systemState the context of the call
     * @param  in the input source stream
     * @param  inName the name of the input source stream
     * @param  err output stream for error messages
     * @return GProcedureCall the compiled call or null
     */
    public static GProcedureCall compileProcedureCall(MModel model,
                                                      MSystemState systemState,
                                                      List<GProcedure> procedures,
                                                      InputStream in, 
                                                      String inName,
                                                      PrintWriter err) {
        GProcedureCall procCall = null;
        ParseErrorHandler errHandler = new ParseErrorHandler(inName, err);
        try {
        	ANTLRInputStream aInput = new ANTLRInputStream(in);
            GeneratorLexer lexer = new GeneratorLexer(aInput);
            CommonTokenStream tStream = new CommonTokenStream(lexer);
            GeneratorParser parser = new GeneratorParser(tStream);
            
            parser.init(errHandler);
            lexer.init(errHandler);
    
            // Parse the command
            ASTGProcedureCall astgProcCall = parser.procedureCallOnly();
            if (errHandler.errorCount() == 0 ) {
    
                Context ctx = new Context(inName,
                                        err,
                                        systemState.system().varBindings(),
                                        null);
                ctx.setModel(model);
                ctx.setSystemState(systemState);
                ctx.setProcedures(procedures);
                
                procCall = astgProcCall.gen(ctx);
    
                // check for semantic errors
                if (ctx.errorCount() > 0 )
                    procCall = null;
            }
        } catch (RecognitionException e) {
            err.println(e.line + ":" +
                        e.charPositionInLine + ": " + 
                        e.getMessage());
        } catch (SemanticException e) {
            err.println(e.getMessage());
        } catch (IOException e) {
        	err.println(e.getMessage());
		}
        err.flush();
        return procCall;
    }

    /**
     * Compiles and adds invariants
     *
     * @param  model the model the invaraints are added to
     * @param  in the source to be compiled
     * @param  inName name of the source stream
     * @param  err output stream for error messages
     * @return Collection the added invariants (MClassInvariant)
     */
    public static Collection<MClassInvariant> compileInvariants(MModel model,
                                                     InputStream in,
                                                     String inName,
                                                     PrintWriter err) {
        ParseErrorHandler errHandler = new ParseErrorHandler(inName, err);
        Collection<MClassInvariant> addedInvs = null;
        
        try {
        	ANTLRInputStream aInput = new ANTLRInputStream(in);
        	aInput.name = inName;
        	
            GeneratorLexer lexer = new GeneratorLexer(aInput);
            CommonTokenStream tStream = new CommonTokenStream(lexer);
            GeneratorParser parser = new GeneratorParser(tStream);
            
            lexer.init(errHandler);
            parser.init(errHandler);
    
            // Parse the specification
            List<ASTConstraintDefinition> consDefList = parser.invariantListOnly();
            
            if (errHandler.errorCount() == 0 ) {
                Context ctx = new Context(inName,
                                          err,
                                          null,
                                          new GeneratorModelFactory());
                ctx.setModel(model);
                
                addedInvs = new LinkedList<MClassInvariant>();
                for (ASTConstraintDefinition cd : consDefList) {
                    // adds the class invariants to the given model
                    addedInvs.addAll(cd.gen(ctx, false));
                }
            }
        } catch (RecognitionException e) {
            err.println(e.line + ":" +
                        e.charPositionInLine + ": " + 
                        e.getMessage());
        } catch (IOException e) {
        	err.println(e.getMessage());
		}
        err.flush();
        return addedInvs;
    }
}