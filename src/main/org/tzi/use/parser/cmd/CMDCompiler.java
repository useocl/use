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

package org.tzi.use.parser.cmd;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.ParseErrorHandler;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MSystemState;

public class CMDCompiler {

    private CMDCompiler() {} // no instances
    
    /**
     * Compiles a list of object manipulation commands.
     *
     * @param  in the source to be compiled
     * @param  inName name of the source stream
     * @param  err output stream for error messages
     * @return List(Cmd) or null if there were any errors
     */
    public static List<MCmd> compileCmdList(MModel model,
                                      MSystemState systemState,
                                      String in, 
                                      String inName,
                                      PrintWriter err) {
    	
    	InputStream stream = new ByteArrayInputStream(in.getBytes());
    	return CMDCompiler.compileCmdList(model, systemState, stream, inName, err);
    }
    /**
     * Compiles a list of object manipulation commands.
     *
     * @param  in the source to be compiled
     * @param  inName name of the source stream
     * @param  err output stream for error messages
     * @return List(Cmd) or null if there were any errors
     */
    public static List<MCmd> compileCmdList(MModel model,
                                      MSystemState systemState,
                                      InputStream in, 
                                      String inName,
                                      PrintWriter err) {
        List<MCmd> cmdList = null;
        ParseErrorHandler errHandler = new ParseErrorHandler(inName, err);
        
        ANTLRInputStream aInput;
		try {
			aInput = new ANTLRInputStream(in);
		} catch (IOException e1) {
			err.println(e1.getMessage());
			return cmdList;
		}
		
        CmdLexer lexer = new CmdLexer(aInput);
        CommonTokenStream tStream = new CommonTokenStream(lexer);
        
        CmdParser parser = new CmdParser(tStream);
        
        lexer.init(errHandler);
        parser.init(errHandler);
        
        try {
            // Parse the command
            ASTCmdList astCmdList = parser.cmdList();
            if (errHandler.errorCount() == 0 ) {
                // Generate code
                Context ctx = new Context(inName, err, 
                                          systemState.system().topLevelBindings(), 
                                          null);
                ctx.setModel(model);
                ctx.setSystemState(systemState);
                cmdList = astCmdList.gen(ctx);
    
                // check for semantic errors
                if (ctx.errorCount() > 0 )
                    cmdList = null;
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
        return cmdList;
    }
}
