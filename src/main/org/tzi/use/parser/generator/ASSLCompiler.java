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

import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.tzi.use.gen.assl.statics.GProcedure;
import org.tzi.use.gen.tool.GProcedureCall;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.ParseErrorHandler;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.use.ASTConstraintDefinition;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
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
    public static List compileProcedures(MModel model,
                                         Reader in,
                                         String inName,
                                         PrintWriter err) {
        List astgProcList = new ArrayList();
        List procedures = new ArrayList();  // GProcedure
        ParseErrorHandler errHandler = new ParseErrorHandler(inName, err);
        GGeneratorLexer lexer = new GGeneratorLexer(in);
        GGeneratorParser parser = new GGeneratorParser(lexer);
        lexer.init(errHandler);
        parser.init(errHandler);
        boolean error = false;
    
        try {
            astgProcList = parser.procedureListOnly();
            if (errHandler.errorCount() != 0 )
                error = true;
            else {
                Iterator astgproc = astgProcList.iterator();
                while (astgproc.hasNext() && !error ) {
                    Context ctx = new Context(inName, err, null, null);
                    ctx.setModel(model);
                    ASTGProcedure astgProc = (ASTGProcedure) astgproc.next();
                    try {
                        GProcedure proc = astgProc.gen(ctx);
                        if (ctx.errorCount() != 0 )
                            error = true;
                        else {
                            boolean ignore = false;
                            Iterator it = procedures.iterator();
                            while (it.hasNext())
                                if (((GProcedure) it.next()).signature().equals(proc.signature() ) ) {
                                    err.println("Warning: Ignoring redefinition of " + proc);
                                    ignore = true;
                                }
                            if (!ignore)
                                procedures.add( proc );
                        }
                    } catch (SemanticException ex) {
                        ctx.reportError(ex);
                        error = true;
                    }
                }
            }
        } catch (antlr.RecognitionException e) {
            error = true;
            err.println(parser.getFilename() +":" + 
                        e.getLine() + ":" + e.getColumn() + ": " + 
                        e.getMessage());
        } catch (antlr.TokenStreamRecognitionException e) {
            error = true;
            err.println(parser.getFilename() +":" + 
                        e.recog.getLine() + ":" + e.recog.getColumn() + ": " + 
                        e.recog.getMessage());
        } catch (antlr.TokenStreamException ex) {
            error = true;
            err.println(parser.getFilename() +":" + ex.getMessage());
        }
        err.flush();
        if (error)
            return null;
        else
            return procedures;
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
                                                      Reader in, 
                                                      String inName,
                                                      PrintWriter err) {
        GProcedureCall procCall = null;
        ParseErrorHandler errHandler = new ParseErrorHandler(inName, err);
        try {
            GGeneratorLexer lexer = new GGeneratorLexer(in);
            GGeneratorParser parser = new GGeneratorParser(lexer);
            parser.init(errHandler);
            lexer.init(errHandler);
    
            // Parse the command
            ASTGProcedureCall astgProcCall = parser.procedureCallOnly();
            if (errHandler.errorCount() == 0 ) {
    
                Context ctx=new Context(inName,
                                        err,
                                        systemState.system().topLevelBindings(),
                                        null);
                ctx.setModel(model);
                ctx.setSystemState(systemState);
                procCall = astgProcCall.gen(ctx);
    
                // check for semantic errors
                if (ctx.errorCount() > 0 )
                    procCall = null;
            }
        } catch (antlr.RecognitionException e) {
            errHandler.reportError(e.getLine() + ":" + e.getColumn() + ": " +e.getMessage());
        } catch (antlr.TokenStreamRecognitionException e) {
            errHandler.reportError(e.recog.getLine() + ":" + e.recog.getColumn() 
                    + ": " + e.recog.getMessage());
        } catch (antlr.TokenStreamException ex) {
            errHandler.reportError(ex.getMessage());
        } catch (SemanticException e) {
            errHandler.reportError(e.getMessage());
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
    public static Collection compileAndAddInvariants(MModel model,
                                                     Reader in,
                                                     String inName,
                                                     PrintWriter err) {
        ParseErrorHandler errHandler = new ParseErrorHandler(inName, err);
        Collection addedInvs = null;
        try {
            GGeneratorLexer lexer = new GGeneratorLexer(in);
            GGeneratorParser parser = new GGeneratorParser(lexer);
            lexer.init(errHandler);
            parser.init(errHandler);
    
            // Parse the specification
            //Log.verbose("Parsing...");
            List consDefList = parser.invariantListOnly();
            if (errHandler.errorCount() == 0 ) {
                Context ctx = new Context(inName,
                                          err,
                                          null,
                                          new ModelFactory());
                Collection existingInvs = model.classInvariants();
                ctx.setModel(model);
                Iterator it = consDefList.iterator();
                while (it.hasNext())
                    // adds the class invariants to the given model
                    ((ASTConstraintDefinition) it.next()).gen(ctx);
                addedInvs = new ArrayList(model.classInvariants());
                addedInvs.removeAll(existingInvs);
            }
        } catch (antlr.RecognitionException e) {
            errHandler.reportError(e.getLine() + ":" + e.getColumn() + ": " +e.getMessage());
        } catch (antlr.TokenStreamRecognitionException e) {
            errHandler.reportError(e.recog.getLine() + ":" + e.recog.getColumn() 
                    + ": " + e.recog.getMessage());
        } catch (antlr.TokenStreamException ex) {
            errHandler.reportError(ex.getMessage());
        }
        err.flush();
        return addedInvs;
    }

}
