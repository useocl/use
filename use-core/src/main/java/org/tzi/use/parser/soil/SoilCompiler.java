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

package org.tzi.use.parser.soil;


import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.tzi.use.output.OutputLevel;
import org.tzi.use.output.UserOutput;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.ParseErrorHandler;
import org.tzi.use.parser.soil.ast.ASTStatement;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.soil.VariableEnvironment;
import org.tzi.use.util.soil.exceptions.CompilationFailedException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;


/**
 * Provides methods to generate an evaluable 
 * {@link MStatement Soil statement} from some
 * text input.
 * <p>
 * This is accomplished with the ANTLR generated
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
	private SoilCompiler() {}
	
	
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
	 * @param output target for error messages
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
            UserOutput output,
            boolean verbose) {
        
		return SoilCompiler.compileStatement(
        		model, 
        		state,
        		variableEnvironment,
        		new ByteArrayInputStream(input.getBytes()), 
        		inputName, 
        		output,
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
	 * @param output target for error messages
	 * @param verbose if true, additional information is produced in case of errors
	 * @return
	 *   if the input was a valid soil statement, an evaluable soil statement, null else
	 */
	public static MStatement compileStatement(MModel model, MSystemState state,
			VariableEnvironment variableEnvironment, InputStream input,
			String inputName, UserOutput output, boolean verbose) {
		
		ASTStatement ast = constructAST(input, inputName, output, verbose);
        
        if (ast == null) {
        	return null;
        }

        return constructStatement(ast, inputName, output,
				state, model, variableEnvironment, verbose);
	 }
	
	
	/**
	 * Parses <code>input</code> and returns an AST node
	 * if the input could be parsed successful.
	 * If not, information about errors is written to <code>errorOuput</code>
	 * and <code>null</code> is returned.
	 * @param input The input stream to be parsed.
	 * @param inputName A logical name for the input (used for error reporting). 
	 * @param output An information sink for error messages.
	 * @param verbose If <code>true</code>, additional information is written to <code>output</code>
	 * in case of an error. 
	 * @return The AST of the parsed statement.
	 */
	public static ASTStatement constructAST(
            InputStream input, 
            String inputName,
            UserOutput output,
            boolean verbose) {
		
		ANTLRInputStream aInput;
		try {
			aInput = new ANTLRInputStream(input);
			aInput.name = inputName;
		} catch (IOException e) {
			output.println(e.getMessage());
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
			new ParseErrorHandler(inputName, output);
		soilLexer.init(errorHandler);
		soilParser.init(errorHandler);
		
		// parse statement
		ASTStatement statement;
		try {
			statement = soilParser.statOnly();
		} catch(RecognitionException e) {
			output.println(
		    		soilParser.getSourceName() + ":" + 
                    e.line                     + ":" +
                    e.charPositionInLine       + ": " + 
                    e.getMessage());
			
			if (verbose) {
				e.printStackTrace(output.getPrintStream(OutputLevel.ERROR));
			}

			return null;
		}
			
		if (errorHandler.errorCount() != 0) {
			output.println("Compilation failed.");

			if (output.getOutputLevel().covers(OutputLevel.TRACE)) {
				output.printlnVerbose("\nAST so far:");
				statement.printTree(new PrintWriter(output.getPrintStream(OutputLevel.TRACE)));
			}

			return null;
		}
		
		statement.flatten();
		return statement;
	}
	
	
	/**
	 * 
	 * @param statement
	 * @param inputName
	 * @param output
	 * @param state
	 * @param model
	 * @param variableEnvironment
	 * @param verbose
	 * @return
	 */
	public static MStatement constructStatement(
			ASTStatement statement, 
			String inputName,
			UserOutput output,
			MSystemState state,
			MModel model,
			VariableEnvironment variableEnvironment,
			boolean verbose) {

		if (output.getOutputLevel().covers(OutputLevel.TRACE)) {
			output.printlnVerbose()
					.printlnVerbose("------------------")
					.printlnVerbose("COMPILATION REPORT\n")
					.printlnVerbose("RESULTING AST\n");

			statement.printTree(new PrintWriter(output.getPrintStream(OutputLevel.TRACE)));

			output.printlnVerbose()
					.println("-------------\n");
		}
			
		// create context
		Context context = new Context(
				inputName, 
                output,
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
			output.printError("Error: ");
			output.printlnError(e.getMessage(true));
			
			if (verbose) {
				output.printlnTrace("-----------\n");
				output.printlnTrace("STACK TRACE\n");
				e.printStackTrace(output.getPrintStream(OutputLevel.TRACE));

				output.printlnVerbose("\nCOMPILATION FAILED");
				output.printlnVerbose("------------------\n");
			}

			return null;
		}
		
		if (verbose) {
			output.printlnVerbose("\nCOMPILATION SUCCESSFUL")
					.printlnVerbose("----------------------\n");
		}
	
        return compiledStatement;
	}
}