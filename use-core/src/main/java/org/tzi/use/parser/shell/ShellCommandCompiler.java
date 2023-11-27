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

package org.tzi.use.parser.shell;


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
 * Compiler for shell commands,
 * which are handled by soil.
 * @author Daniel Gent
 *
 */
public class ShellCommandCompiler {
	
	/**
	 * default constructor hidden, since we don't need
	 * instances of this class
	 */
	private ShellCommandCompiler() { }
	
	/**
	 * Builds an {@link ASTStatement abstract syntax tree} which
	 * in turn generates an evaluable {@link MStatement soil statement}.
	 * <p>
	 * All checked exceptions that could happen during this process are
	 * handled. If the input was not a valid soil statement
	 * {@code null} is returned.
	 * </p>
	 * <p>
	 * Encapsulates the string <code>input</code> as an input stream
	 * and delegates to {@link ShellCommandCompiler#compileShellCommand(MModel, MSystemState, VariableEnvironment, InputStream, String, UserOutput, boolean)}
	 * </p>
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
	public static MStatement compileShellCommand(
			MModel model,
            MSystemState state,
            VariableEnvironment variableEnvironment,
            String input, 
            String inputName,
            UserOutput output,
            boolean verbose) {
        
		return ShellCommandCompiler.compileShellCommand(
        		model, 
        		state,
        		variableEnvironment,
        		new ByteArrayInputStream(input.getBytes()), 
        		inputName, 
        		output,
        		verbose);
	}
	
	
	/**
	 * Builds an {@link ASTStatement abstract syntax tree} which
	 * in turn generates an evaluable {@link MStatement soil statement}.
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
	public static MStatement compileShellCommand(MModel model,
			MSystemState state, VariableEnvironment variableEnvironment,
			InputStream input, String inputName, UserOutput output,
			boolean verbose) {
		
		ASTStatement ast = constructAST(input, inputName, output, verbose);
        
        if (ast == null) {
        	return null;
        }
        
		MStatement statement = constructStatement(ast, inputName, output,
				state, model, variableEnvironment, verbose);
		
		return statement;
	 }
	
	/**
	 * Constructs an AST for a shell command represented as 
	 * a soil statement from <code>input</code>.
	 * 
	 * @param input The shell input
	 * @param inputName Name of the in stream for error reporting.
	 * @param output Writer to log errors to.
	 * @param verbose If <code>true</code> additional information is written
	 *        to <code>output</code>.
	 * @return
	 */
	protected static ASTStatement constructAST(InputStream input,
			String inputName, UserOutput output, boolean verbose) {
		
		ANTLRInputStream aInput;
		try {
			aInput = new ANTLRInputStream(input);
			aInput.name = inputName;
		} catch (IOException e) {
			output.printlnError(e.getMessage());
			
			return null;
		}
		
		// set up lexer & parser
		ShellCommandLexer shellLexer = new ShellCommandLexer(aInput);
		CommonTokenStream tokenStream = new CommonTokenStream(shellLexer);
		ShellCommandParser shellParser = new ShellCommandParser(tokenStream);
		ParseErrorHandler errorHandler = new ParseErrorHandler(inputName, output);
		
		shellLexer.init(errorHandler);
		shellParser.init(errorHandler);
		
		// parse statement
		ASTStatement statement = null;
		try {
			statement = shellParser.shellCommand();
		} catch(RecognitionException e) {
			output.printlnError(
		    		shellParser.getSourceName() + ":"  + 
                    e.line                      + ":"  +
                    e.charPositionInLine        + ": " + 
                    e.getMessage());
			
			if (verbose) {
				e.printStackTrace(output.getPrintStream(OutputLevel.NORMAL));
			}

			return null;
		}
			
		if (errorHandler.errorCount() != 0) {
			
			if (verbose) {
				output.println("\nAST so far:");
				statement.printTree(new PrintWriter(output.getPrintStream(OutputLevel.NORMAL)));
			}

			return null;
		}

		return statement;
	}
		
	/**
	 * Constructs a MStatement from the given AST.
	 * If the compilation fails <code>null</code> is returned.
	 * @param statement The AST to construct the statement from.
	 * @param inputName A name for the input used by error messages.
	 * @param output Output for error messages
	 * @param state The system state
	 * @param model The model
	 * @param variableEnvironment
	 * @param verbose if true, detailed messages are printed to stdout
	 * @return The constructed MStatement or <code>null</code> if any error occured.
	 */
	private static MStatement constructStatement(
			ASTStatement statement, 
			String inputName,
			UserOutput output,
			MSystemState state,
			MModel model,
			VariableEnvironment variableEnvironment,
			boolean verbose) {
		
		if (verbose) {	
			output.println()
					.println("------------------")
					.println("COMPILATION REPORT\n")
					.println("RESULTING AST\n");

			statement.printTree(new PrintWriter(output.getPrintStream(OutputLevel.NORMAL)));

			output.println()
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

			output.println(e.getMessage(true));
			
			if (verbose) {
				output.println("-----------\n");
				output.println("STACK TRACE\n");
				e.printStackTrace(output.getPrintStream(OutputLevel.NORMAL));

				output.println("\nCOMPILATION FAILED");
				output.println("------------------\n");
			}

			return null;
		}
		
		if (verbose) {
			output.println("\nCOMPILATION SUCCESSFUL")
					.println("----------------------\n");
		}
	
        return compiledStatement;
	}
}