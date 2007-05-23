header { 
package org.tzi.use.parser.cmd;
}

{
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import org.tzi.use.parser.ParseErrorHandler;
import org.tzi.use.parser.MyToken;

import org.tzi.use.parser.ocl.*;
import org.tzi.use.parser.use.*;
}
class GCmdParser extends GOCLParser;
options {
    exportVocab = GCmd;
    
}

// grammar for commands

/* ------------------------------------
  cmdList ::= cmd { cmd }
*/
cmdList returns [ASTCmdList cmdList]
{ cmdList = new ASTCmdList(); ASTCmd c; }
:
    c=cmd { cmdList.add(c); }
    ( c=cmd { cmdList.add(c); } )*
    EOF
    ;


/* ------------------------------------
  cmd ::= cmdStmt [ ";" ]
*/
cmd returns [ASTCmd n]
{ n = null; }
:
    n=cmdStmt ( SEMI )?
    ;


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
{ n = null; }
:
      n=createCmd
    | n=createAssignCmd 
    | n=createInsertCmd
    | n=destroyCmd
    | n=insertCmd
    | n=deleteCmd
    | n=setCmd
    | n=opEnterCmd
    | n=opExitCmd
    | n=letCmd
    | n=executeCmd
    ;


/* ------------------------------------
  Creates one or more objects and binds variables to them.

  createCmd ::= "create" idList ":" simpleType
*/
createCmd returns [ASTCreateCmd n]
{ List idList; ASTType t; n = null; }
:
    "create" idList=idList 
    COLON t=simpleType
    { n = new ASTCreateCmd(idList, t); }
    ;

/* ------------------------------------
  Creates an anonymous object and assigns it to a variable.

  createAssignCmd ::= "assign" idList ":=" "create" simpleType
*/
createAssignCmd returns [ASTCreateAssignCmd n]
{ List idList; ASTType t; n = null; }
:
    "assign" idList=idList  COLON_EQUAL "create" t=simpleType{ n = new ASTCreateAssignCmd(idList, t); };


/* ------------------------------------
  Creates one or more objects and binds variables to them.

  create ::= "create" id ":" simpleType "between" "(" idList ")"
*/
createInsertCmd returns [ASTCreateInsertCmd n]
{ List idListInsert; ASTType t; n = null; }
:
    "create" id:IDENT COLON idAssoc:IDENT
    "between" LPAREN idListInsert=idList RPAREN
    { n = new ASTCreateInsertCmd( (MyToken) id, (MyToken) idAssoc, idListInsert); }
    ;


/* ------------------------------------
  Destroys one or more objects (expression may be a collection)

  destroyCmd ::= "destroy" expression { "," expression }
*/
destroyCmd returns [ASTDestroyCmd n]
{ ASTExpression e = null; List exprList = new ArrayList(); n = null; }
:
     "destroy" e=expression { exprList.add(e); } 
               ( COMMA e=expression { exprList.add(e); } )*
    { n = new ASTDestroyCmd(exprList); }
    ;


/* ------------------------------------
  Inserts a link (tuple of objects) into an association.

  insertCmd ::= "insert" "(" expression "," expression { "," expression } ")" "into" id
*/
insertCmd returns [ASTInsertCmd n]
{ ASTExpression e; List exprList = new ArrayList(); n = null; }
:
    "insert" LPAREN 
    e=expression { exprList.add(e); } COMMA
    e=expression { exprList.add(e); } ( COMMA e=expression { exprList.add(e); } )* 
    RPAREN "into" id:IDENT
    { n = new ASTInsertCmd(exprList, (MyToken) id); }
    ;


/* ------------------------------------
  Deletes a link (tuple of objects) from an association.

  deleteCmd ::= "delete" "(" expression "," expression { "," expression } ")" "from" id
*/
deleteCmd returns [ASTDeleteCmd n]
{ ASTExpression e; List exprList = new ArrayList(); n = null; }
:
    "delete" LPAREN
    e=expression { exprList.add(e); } COMMA
    e=expression { exprList.add(e); } ( COMMA e=expression { exprList.add(e); } )*
    RPAREN "from" id:IDENT
    { n = new ASTDeleteCmd(exprList, (MyToken) id); }
    ;


/* ------------------------------------

  Assigns a value to an attribute of an object. The first "expression"
  must be an attribute access expression giving an "l-value" for an
  attribute.

  setCmd ::= "set" expression ":=" expression 
*/
setCmd returns [ASTSetCmd n]
{ ASTExpression e1, e2; n = null; }
:
    "set" e1=expression COLON_EQUAL e2=expression
    { n = new ASTSetCmd(e1, e2); }
    ;


/* ------------------------------------
  A call of an operation which may have side-effects. The first
  expression must have an object type.

  opEnterCmd ::= 
    "openter" expression id "(" [ expression { "," expression } ] ")" 
*/
opEnterCmd returns [ASTOpEnterCmd n]
{ ASTExpression e; n = null; }
:
    "openter" 
    e=expression id:IDENT { n = new ASTOpEnterCmd(e, (MyToken) id); }
    LPAREN
    ( e=expression { n.addArg(e); } ( COMMA e=expression { n.addArg(e); } )* )?
    RPAREN 
    ;

/* ------------------------------------
  Command to exit an operation. A result expression is required if the
  operation to be exited declared a result type.

  opExitCmd ::= "opexit" [ expression ]
*/
opExitCmd returns [ASTOpExitCmd n]
{ ASTExpression e = null; n = null; }
:
    "opexit" 
    ( // ambiguity between let-expression and let-cmd. Default is to
      // match expression.
       options { warnWhenFollowAmbig=false; } : e=expression
    )?
    { n = new ASTOpExitCmd(e); }
    ;

/* ------------------------------------
  Command to bind a toplevel variable.

  letCmd ::= "let" id [ ":" type ] "=" expression
*/
letCmd returns [ASTLetCmd n]
{ ASTExpression e = null; ASTType t = null; n = null; }
:
    "let" name:IDENT ( COLON t=type )? EQUAL e=expression
     { n = new ASTLetCmd((MyToken) name, t, e); }
    ;


executeCmd returns [ASTExecuteCmd ec]
{
	ec = null;
	ASTExpression e = null; 
}
:
	"execute" 
    e=expression 
    { ec = new ASTExecuteCmd(e); }
;

{
import java.io.PrintWriter;
import org.tzi.use.util.Log;
import org.tzi.use.util.StringUtil;
import org.tzi.use.parser.ParseErrorHandler;
import org.tzi.use.parser.MyToken;
	
}
class GCmdLexer extends GOCLLexer;
options {
    importVocab = GCmd;
}

protected
VOCAB:	
    '\3'..'\377'
    ;

