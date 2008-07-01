grammar GCmd;

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
 
package org.tzi.use.parser.cmd;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import org.tzi.use.parser.ParseErrorHandler;
import org.tzi.use.parser.ocl.*;
import org.tzi.use.parser.use.*;
}

@members {
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

@lexer::header {
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
 
package org.tzi.use.parser.cmd;

import org.tzi.use.parser.ParseErrorHandler;
}

@lexer::members {
	public void init(ParseErrorHandler handler) {
        fParseErrorHandler = handler;
		this.gGOCLLexerRules.init(handler);
    }

    /* Overridden methods. */
	private ParseErrorHandler fParseErrorHandler;
    
    public void emitErrorMessage(String msg) {
       	fParseErrorHandler.reportError(msg);
	}
}

// grammar for commands

/* ------------------------------------
  cmdList ::= cmd { cmd }
*/
cmdList returns [ASTCmdList cmdList]
@init{ $cmdList = new ASTCmdList(); }
:
    c=cmd { cmdList.add($c.n); }
    ( c=cmd { cmdList.add($c.n); } )*
    EOF
    ;


/* ------------------------------------
  cmd ::= cmdStmt [ ";" ]
*/
cmd returns [ASTCmd n]
:
    stmt=cmdStmt { $n = $stmt.n; }( SEMI )?;


/* ------------------------------------
  cmdStmt ::= 
      createCmd
    | createAssignCmd 
    | createInsertCmd
    | destroyCmd 
    | insertCmd 
    | deleteCmd 
    | setCmd 
    | opEnterCmd
    | opExitCmd
    | letCmd
*/
cmdStmt returns [ASTCmd n]
:
	(
      nC = createCmd
    | nC = createAssignCmd 
    | nC = createInsertCmd
    | nC = destroyCmd
    | nC = insertCmd
    | nC = deleteCmd
    | nC = setCmd
    | nC = opEnterCmd
    | nC = opExitCmd
    | nC = letCmd
    | nC = executeCmd
	) { $n = $nC.n; }
    ;


/* ------------------------------------
  Creates one or more objects and binds variables to them.

  createCmd ::= "create" idList ":" simpleType
*/
createCmd returns [ASTCmd n]
:
    'create' nIdList=idList 
    COLON t=simpleType
    { $n = new ASTCreateCmd($nIdList.idList, $t.n); }
    ;

/* ------------------------------------
  Creates an anonymous object and assigns it to a variable.

  createAssignCmd ::= "assign" idList ":=" "create" simpleType
*/
createAssignCmd returns [ASTCmd n]
:
    'assign' nIdList=idList COLON_EQUAL 'create' t=simpleType{ $n = new ASTCreateAssignCmd($nIdList.idList, $t.n); };


/* ------------------------------------
  Creates one or more objects and binds variables to them.

  create ::= "create" id ":" simpleType "between" "(" idList ")"
*/
createInsertCmd returns [ASTCmd n]
:
    'create' id=IDENT COLON idAssoc=IDENT
    'between' LPAREN idListInsert=idList RPAREN
    { $n = new ASTCreateInsertCmd( $id, $idAssoc, $idListInsert.idList); }
    ;


/* ------------------------------------
  Destroys one or more objects (expression may be a collection)

  destroyCmd ::= "destroy" expression { "," expression }
*/
destroyCmd returns [ASTCmd n]
@init { List exprList = new ArrayList(); }
:
     'destroy' e=expression { exprList.add($e.n); } 
               ( COMMA e=expression { exprList.add($e.n); } )*
    { $n = new ASTDestroyCmd(exprList); }
    ;


/* ------------------------------------
  Inserts a link (tuple of objects) into an association.

  insertCmd ::= "insert" "(" expression "," expression { "," expression } ")" "into" id
*/
insertCmd returns [ASTCmd n]
@init{ List exprList = new ArrayList(); }
:
    'insert' LPAREN 
    e=expression { exprList.add($e.n); } COMMA
    e=expression { exprList.add($e.n); } ( COMMA e=expression { exprList.add($e.n); } )* 
    RPAREN 'into' id=IDENT
    { $n = new ASTInsertCmd(exprList, $id); }
    ;


/* ------------------------------------
  Deletes a link (tuple of objects) from an association.

  deleteCmd ::= "delete" "(" expression "," expression { "," expression } ")" "from" id
*/
deleteCmd returns [ASTCmd n]
@init { List exprList = new ArrayList(); }
:
    'delete' LPAREN
    e=expression { exprList.add($e.n); } COMMA
    e=expression { exprList.add($e.n); } ( COMMA e=expression { exprList.add($e.n); } )*
    RPAREN 'from' id=IDENT
    { $n = new ASTDeleteCmd(exprList, $id); }
    ;


/* ------------------------------------

  Assigns a value to an attribute of an object. The first "expression"
  must be an attribute access expression giving an "l-value" for an
  attribute.

  setCmd ::= "set" expression ":=" expression 
*/
setCmd returns [ASTCmd n]
:
    'set' e1=expression COLON_EQUAL e2=expression
    { $n = new ASTSetCmd($e1.n, $e2.n); }
    ;


/* ------------------------------------
  A call of an operation which may have side-effects. The first
  expression must have an object type.

  opEnterCmd ::= 
    "openter" expression id "(" [ expression { "," expression } ] ")" 
*/
opEnterCmd returns [ASTCmd n]
@init{ASTOpEnterCmd nOpEnter = null;}
:
    'openter' 
    e=expression id=IDENT { nOpEnter = new ASTOpEnterCmd($e.n, $id); $n = nOpEnter;}
    LPAREN
    ( e=expression { nOpEnter.addArg($e.n); } ( COMMA e=expression { nOpEnter.addArg($e.n); } )* )?
    RPAREN 
    ;

/* ------------------------------------
  Command to exit an operation. A result expression is required if the
  operation to be exited declared a result type.

  opExitCmd ::= "opexit" [ expression ]
*/
opExitCmd returns [ASTCmd n]
:
    'opexit' ((expression)=> e=expression | )
    { $n = new ASTOpExitCmd($e.n); }
    ;

/* ------------------------------------
  Command to bind a toplevel variable.

  letCmd ::= "let" id [ ":" type ] "=" expression
*/
letCmd returns [ASTCmd n]
:
    'let' name=IDENT ( COLON t=type )? EQUAL e=expression
     { $n = new ASTLetCmd($name, $t.n, $e.n); }
    ;

executeCmd returns [ASTCmd n]
:
	'execute' 
    e=expression 
    { $n = new ASTExecuteCmd($e.n); }
;

