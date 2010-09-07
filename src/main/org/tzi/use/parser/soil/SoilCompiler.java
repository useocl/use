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

package org.tzi.use.parser.soil;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.ParseErrorHandler;
import org.tzi.use.parser.soil.ast.ASTStatement;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.soil.VariableEnvironment;
import org.tzi.use.util.soil.exceptions.compilation.CompilationFailedException;


/**
 * Provides methods to generate an evaluable 
 * {@link MStatement Soil statement} from some
 * text input.
 * <p>
 * This is accomplished with the an ANTLR generated
 * parser and lexer, which build an
 * {@link ASTStatement abstract syntax tree} as an
 * intermediate data structure.
 * 
 * @author Daniel Gent
 *
 */
public class SoilCompiler {
	
	/**
	 * default constructor hidden, since we don't need
	 * instances of this class
	 */
	private SoilCompiler() {
		
	}
	
	
	/**
	 * builds an {@link ASTStatement abstract syntax tree} which
	 * in turn generates a evaluable {@link MStatement soil statement}.
	 * <p>
	 * All checked exceptions that could happen during this process are
	 * handled. If the input was not a valid soil statement
	 * {@code null} is returned.
	 * 
	 * @param model the model
	 * @param state the system state
	 * @param variableEnvironment holds the existing variables
	 * @param input the string which holds the statement
	 * @param inputName the name of the source
	 * @param errorOutput target for error messages
	 * @param verbose if true, additional information is produced in case of errors
	 * @return
	 *   if the input was a valid soil statement, an evaluable soil statement, null else
	 */
	public static MStatement compileStatement(
			MModel model,
            MSystemState state,
            VariableEnvironment variableEnvironment,
            String input, 
            String inputName,
            PrintWriter errorOutput,
            boolean verbose) {
        
		return SoilCompiler.compileStatement(
        		model, 
        		state,
        		variableEnvironment,
        		new ByteArrayInputStream(input.getBytes()), 
        		inputName, 
        		errorOutput,
        		verbose);
	}
	
	
	/**
	 * builds an {@link ASTStatement abstract syntax tree} which
	 * in turn generates a evaluable {@link MStatement soil statement}.
	 * <p>
	 * All checked exceptions that could happen during this process are
	 * handled. If the input was not a valid soil statement
	 * {@code null} is returned.
	 * 
	 * @param model the model
	 * @param state the system state
	 * @param variableEnvironment holds the existing variables
	 * @param input the input source
	 * @param inputName the name of the source
	 * @param errorOutput target for error messages
	 * @param verbose if true, additional information is produced in case of errors
	 * @return
	 *   if the input was a valid soil statement, an evaluable soil statement, null else
	 */
	public static MStatement compileStatement(
			MModel model,
            MSystemState state,
            VariableEnvironment variableEnvironment,
            InputStream input, 
            String inputName,
            PrintWriter errorOutput,
            boolean verbose) {
		
        ASTStatement ast = 
        	constructAST(
        		input, 
        		inputName, 
        		errorOutput, 
        		verbose);
        
        if (ast == null) {
        	return null;
        }
        
        MStatement statement = 
        	constructStatement(
        			ast, 
        			inputName, 
        			errorOutput, 
        			state, 
        			model, 
        			variableEnvironment, 
        			verbose);
		
		return statement;
	 }
	
	
	/**
	 * 
	 * @param model
	 * @param state
	 * @param variableEnvironment
	 * @param input
	 * @param inputName
	 * @param errorOutput
	 * @param verbose
	 * @return
	 */
	public static ASTStatement constructAST(
            InputStream input, 
            String inputName,
            PrintWriter errorOutput,
            boolean verbose) {
		
		ANTLRInputStream aInput;
		try {
			aInput = new ANTLRInputStream(input);
			aInput.name = inputName;
		} catch (IOException e) {
			errorOutput.println(e.getMessage());
			errorOutput.flush();
			
			return null;
		}
		
		// set up lexer & parser
		SoilLexer soilLexer = 
			new SoilLexer(aInput);
		CommonTokenStream tokenStream = 
			new CommonTokenStream(soilLexer);
		SoilParser soilParser = 
			new SoilParser(tokenStream);
		ParseErrorHandler errorHandler = 
			new ParseErrorHandler(inputName, errorOutput);
		soilLexer.init(errorHandler);
		soilParser.init(errorHandler);
		
		// parse statement
		ASTStatement statement = null;
		try {
			statement = soilParser.statOnly();
		} catch(RecognitionException e) {
			errorOutput.println(
		    		soilParser.getSourceName() + ":" + 
                    e.line                     + ":" +
                    e.charPositionInLine       + ": " + 
                    e.getMessage());
			
			if (verbose) {
				e.printStackTrace(errorOutput);
			}
			
			errorOutput.flush();
			
			return null;
		}
			
		if (errorHandler.errorCount() != 0) {
			errorOutput.println("Compilation failed.");
			
			if (verbose) {
				errorOutput.println("\nAST so far:");
				statement.printTree(errorOutput);
			}
			
			errorOutput.flush();
			
			return null;
		}
		
		statement.flatten();
		return statement;
	}
	
	
	/**
	 * 
	 * @param statement
	 * @param inputName
	 * @param errorOutput
	 * @param state
	 * @param model
	 * @param variableEnvironment
	 * @param verbose
	 * @return
	 */
	public static MStatement constructStatement(
			ASTStatement statement, 
			String inputName,
			PrintWriter errorOutput,
			MSystemState state,
			MModel model,
			VariableEnvironment variableEnvironment,
			boolean verbose) {
	
		// TODO
		PrintWriter verboseOutput = new PrintWriter(System.out);
		
		if (verbose) {	
			verboseOutput.println();
			verboseOutput.println("------------------");
			verboseOutput.println("COMPILATION REPORT\n");
			verboseOutput.println("RESULTING AST\n");
			statement.printTree(verboseOutput);
			verboseOutput.println();
			verboseOutput.println("-------------\n");
			verboseOutput.flush();
		}
			
		// create context
		Context context = new Context(
				inputName, 
                errorOutput, 
                state.system().varBindings(), 
                null);
		context.setModel(model);
        context.setSystemState(state);
    
        // compile statement
        MStatement compiledStatement;
		try {
			ASTStatement.setVerbose(verbose);
			compiledStatement = 
				statement.generateStatement(
						context,
						variableEnvironment.constructSymbolTable());
				
		} catch (CompilationFailedException e) {
			errorOutput.print("Error: ");
			errorOutput.println(e.getMessage(statement));
			
			if (verbose) {
				errorOutput.println("-----------\n");
				errorOutput.println("STACK TRACE\n");
				e.printStackTrace(errorOutput);
			}
			
			errorOutput.flush();
			
			if (verbose) {
				verboseOutput.println("\nCOMPILATION FAILED");
				verboseOutput.println("------------------\n");
				verboseOutput.flush();
			}
			
			
			return null;
		}
		
		if (verbose) {
			verboseOutput.println("\nCOMPILATION SUCCESSFUL");
			verboseOutput.println("----------------------\n");
			verboseOutput.flush();
		}
	
        return compiledStatement;
	}
}