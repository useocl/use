grammar GOCL;

import GOCLBase, GOCLLexerRules;

@header { 
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

package org.tzi.use.parser.ocl; 

// ------------------------------------
//  USE parser
// ------------------------------------

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.PrintWriter;
import org.tzi.use.parser.ParseErrorHandler;
import org.tzi.use.parser.use.*;
}

@members{  
    final static String Q_COLLECT  = "collect";
    final static String Q_COLLECTNESTED  = "collectNested";
    final static String Q_SELECT   = "select";
    final static String Q_REJECT   = "reject";
    final static String Q_FORALL   = "forAll";
    final static String Q_EXISTS   = "exists";
    final static String Q_ISUNIQUE = "isUnique";
    final static String Q_SORTEDBY = "sortedBy";
    final static String Q_ANY      = "any";
    final static String Q_ONE      = "one";

    final static int Q_COLLECT_ID  = 1;
    final static int Q_SELECT_ID   = 2;
    final static int Q_REJECT_ID   = 3;
    final static int Q_FORALL_ID   = 4;
    final static int Q_EXISTS_ID   = 5;
    final static int Q_ISUNIQUE_ID = 6;
    final static int Q_SORTEDBY_ID = 7;
    final static int Q_ANY_ID      = 8;
    final static int Q_ONE_ID      = 9;
    final static int Q_COLLECTNESTED_ID  = 10;

    final static HashMap queryIdentMap = new HashMap();

    static {
        queryIdentMap.put(Q_COLLECT,  new Integer(Q_COLLECT_ID));
        queryIdentMap.put(Q_SELECT,   new Integer(Q_SELECT_ID));
        queryIdentMap.put(Q_REJECT,   new Integer(Q_REJECT_ID));
        queryIdentMap.put(Q_FORALL,   new Integer(Q_FORALL_ID));
        queryIdentMap.put(Q_EXISTS,   new Integer(Q_EXISTS_ID));
        queryIdentMap.put(Q_ISUNIQUE, new Integer(Q_ISUNIQUE_ID));
        queryIdentMap.put(Q_SORTEDBY, new Integer(Q_SORTEDBY_ID));
        queryIdentMap.put(Q_ANY,      new Integer(Q_ANY_ID));
        queryIdentMap.put(Q_ONE,      new Integer(Q_ONE_ID));
        queryIdentMap.put(Q_COLLECTNESTED, new Integer(Q_COLLECTNESTED_ID));
    }

    protected boolean isQueryIdent(Token t) {
        return queryIdentMap.containsKey(t.getText());
    }
    
    public void init(ParseErrorHandler handler) {
        fParseErrorHandler = handler;
		this.gGOCLBase.init(handler);
    }

    /* Overridden methods. */
	private ParseErrorHandler fParseErrorHandler;
    
    public void emitErrorMessage(String msg) {
       	fParseErrorHandler.reportError(msg);
	}
}

@lexer::header
{
package org.tzi.use.parser.ocl; 

// ------------------------------------
// OCL lexer
// ------------------------------------

import java.io.PrintWriter;
import org.tzi.use.parser.ParseErrorHandler;
}

@lexer::members {
    private PrintWriter fErr;
    private ParseErrorHandler fParseErrorHandler;

    Stack paraphrase = new Stack();
    
    public String getFilename() {
        return fParseErrorHandler.getFileName();
    }
    
    public void reportError(RecognitionException ex) {
        fParseErrorHandler.reportError(
			ex.line + ":" + (ex.charPositionInLine + 1) + ": " + ex.getMessage());
    }
 
    public void init(ParseErrorHandler handler) {
        fParseErrorHandler = handler;
		this.gGOCLLexerRules.init(handler);
    }
}

/* ------------------------------------
  expressionOnly ::= 
    conditionalImpliesExpression
*/
expressionOnly returns [ASTExpression n]
:
    nExp=expression EOF {$n = $nExp.n;}
    ;