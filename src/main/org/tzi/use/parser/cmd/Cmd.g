grammar Cmd;
options {
  superClass = BaseParser;
}

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

import org.tzi.use.parser.base.BaseParser;
import org.tzi.use.parser.use.*;
import org.tzi.use.parser.ocl.*;
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
    private ParseErrorHandler fParseErrorHandler;

    public String getFilename() {
        return fParseErrorHandler.getFileName();
    }
    
    public void emitErrorMessage(String msg) {
       	fParseErrorHandler.reportError(msg);
	}
 
    public void init(ParseErrorHandler handler) {
        fParseErrorHandler = handler;
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


/*
--------- Start of file USEBase.gpart -------------------- 
*/

// grammar start

/* ------------------------------------
  model ::= 
    "model" id
    { enumTypeDefinition } 
    {   classDefinition 
      | associationDefinition 
      | "constraints" { invariant | prePost }
    } 
*/
model returns [ASTModel n]
:
    'model' modelName=IDENT { $n = new ASTModel($modelName); }
    ( e=enumTypeDefinition { $n.addEnumTypeDef($e.n); } )*
    (   ( generalClassDefinition[$n] )
      | ( a=associationDefinition { $n.addAssociation($a.n); } )
      | ( 'constraints'
          (   cons=invariant { $n.addConstraint($cons.n); } 
            | ppc=prePost { $n.addPrePost($ppc.n); } 
          )*  
        )
    )*
    EOF
    ;
    
/* ------------------------------------
  enumTypeDefinition ::= 
    "enum" id "{" idList "}" [ ";" ]
*/
enumTypeDefinition returns [ASTEnumTypeDefinition n]
:
    'enum' name=IDENT LBRACE idListRes=idList RBRACE ( SEMI )?
        { $n = new ASTEnumTypeDefinition($name, $idListRes.idList); }
    ;

/* ------------------------------------
  generalClassDefinition ::=
    [ "abstract" ]
   	 { classDefinition | associationClassDefinition }
*/
generalClassDefinition[ASTModel n]
@init{ 
  boolean isAbstract = false;
}
:
    ( 'abstract' { isAbstract = true; } )? 
    ( ( c=classDefinition[isAbstract] { $n.addClass($c.n); } ) | 
      ( ac=associationClassDefinition[isAbstract] { $n.addAssociationClass($ac.n); } ) )
       
;

/* ------------------------------------
  classDefinition ::= 
    [ "abstract" ] "class" id [ specialization ] 
    [ attributes ] 
    [ operations ] 
    [ constraints ]
    "end"

  specialization ::= "<" idList
  attributes ::= "attributes" { attributeDefinition }
  operations ::= "operations" { operationDefinition }
  constraints ::= "constraints" { invariantClause }
*/
classDefinition[boolean isAbstract] returns [ASTClass n]
@init{ List idList; }
:
    'class' name=IDENT { $n = new ASTClass($name, $isAbstract); }
    ( LESS idListRes=idList { $n.addSuperClasses($idListRes.idList); } )?
    ( 'attributes' 
      ( a=attributeDefinition { $n.addAttribute($a.n); } )* 
    )?
    ( 'operations' 
      ( op=operationDefinition { $n.addOperation($op.n); } )* 
    )?
    ( 'constraints'
      (
        inv=invariantClause { $n.addInvariantClause($inv.n); }
      )*
    )?
    'end'
    ;

/* ------------------------------------
  associationClassDefinition ::= 
    [ "abstract" ] "associationClass" id [ specialization ] 
    [ attributes ]
    [ operations ] 
    [ constraints ]
    [( "aggregation" | "composition" )] "between"
    associationEnd { associationEnd }+
    "end"

  specialization ::= "<" idList
  attributes ::= "attributes" { attributeDefinition }
  operations ::= "operations" { operationDefinition }
  constraints ::= "constraints" { invariantClause }
*/
associationClassDefinition[boolean isAbstract] returns [ASTAssociationClass n]
@init {List idList; Token t = null;}
:
    classKW = ('associationClass' | 'associationclass') 
    { 
    	if ($classKW.text.equals("associationClass")) {
               reportWarning("the 'associationClass' keyword is deprecated and will " +
                             "not be supported in the future, use 'associationclass' instead");
            }  
    }
    
    name=IDENT { $n = new ASTAssociationClass($name, isAbstract); }
    ( LESS idListRes=idList { $n.addSuperClasses($idListRes.idList); } )?
    'between'
    ae=associationEnd { $n.addEnd($ae.n); }
    ( ae=associationEnd { $n.addEnd($ae.n); } )+
    ( 'attributes' 
      ( a=attributeDefinition { $n.addAttribute($a.n); } )* 
    )?
    ( 'operations' 
      ( op=operationDefinition { $n.addOperation($op.n); } )* 
    )?
    ( 'constraints'
      (
        inv=invariantClause { $n.addInvariantClause($inv.n); }
      )*
    )?
    ( { t = input.LT(1); }
      ( 'aggregation' | 'composition' )
      { $n.setKind(t); }
    )?
    'end'
    ;
    
/* ------------------------------------
  attributeDefinition ::= 
    id ":" type [ ";" ]
*/
attributeDefinition returns [ASTAttribute n]
:
    name=IDENT COLON t=type ( SEMI )?
        { $n = new ASTAttribute($name, $t.n); }
    ;

/* ------------------------------------
  operationDefinition ::= 
    id paramList [ ":" type [ "=" expression ] ] 
    { prePostClause } [ ";" ]
*/
operationDefinition returns [ASTOperation n]
:
    name=IDENT
    pl=paramList
    ( COLON t=type )?
    (EQUAL e=expression )? 
	('begin' al=alActionList 'end' )?
    { $n = new ASTOperation($name, $pl.paramList, $t.n, $e.n, $al.al); }
    ( ppc=prePostClause { $n.addPrePostClause($ppc.n); } )*
    ( SEMI )?
    ;

/* ------------------------------------
  associationDefinition ::= 
    ( "association" | "aggregation" | "composition" ) 
    id "between"
    associationEnd associationEnd { associationEnd }
    "end"
*/
associationDefinition returns [ASTAssociation n]
@init{ Token t = null; }
:
    { t = input.LT(1); }
    ( 'association' | 'aggregation' | 'composition' )
    //    ( classDefinition | (name:IDENT { n = new ASTAssociation(t, $name); }) )
    name=IDENT { $n = new ASTAssociation(t, $name); }
    'between'
    ae=associationEnd { $n.addEnd($ae.n); }
    ( ae=associationEnd { $n.addEnd($ae.n); } )+
    'end'
    ;


/* ------------------------------------
  associationEnd ::= 
    id "[" multiplicity "]" [ "role" id ] [ "ordered" ] [ ";" ]
*/
associationEnd returns [ASTAssociationEnd n]
:
    name=IDENT LBRACK m=multiplicity RBRACK 
    { $n = new ASTAssociationEnd($name, $m.n); } 
    ( 'role' rn=IDENT { $n.setRolename($rn); } )?
    ( 'ordered' { $n.setOrdered(); } )?
    ( SEMI )?
    ;


/* ------------------------------------
  multiplicity ::= 
    multiplicityRange { "," multiplicityRange }

  multiplicityRange ::=
    multiplicitySpec [ ".." multiplicitySpec ]

  multiplicitySpec ::=
    "*" | INT
*/
multiplicity returns [ASTMultiplicity n]
:
    { 
	Token t = input.LT(1); // remember start position of expression
	$n = new ASTMultiplicity(t);
    }
    mr=multiplicityRange { $n.addRange($mr.n); }
    ( COMMA mr=multiplicityRange  { $n.addRange($mr.n); } )*
    ;

multiplicityRange returns [ASTMultiplicityRange n]
:
    ms1=multiplicitySpec { $n = new ASTMultiplicityRange($ms1.m); }
    ( DOTDOT ms2=multiplicitySpec { $n.setHigh($ms2.m); } )?
    ;

multiplicitySpec returns [int m]
@init{ $m = -1; }
:
      i=INT { $m = Integer.parseInt($i.text); }
    | STAR  { $m = -1; }
    ;


/* ------------------------------------
  constraintDefinition ::= 
    invariant | prePost
*/
//  constraintDefinition returns [ASTConstraintDefinition n]
//  { n = null; }
//  :
//      n=invariant // | prePost
//      ;

/* ------------------------------------
  invariant ::= 
    invariantContext invariantClause { invariantClause }

  invariantContext := 
    "context" [ id ":" ] simpleType
*/
invariant returns [ASTConstraintDefinition n]
:
    { $n = new ASTConstraintDefinition(); }
    'context'
    ( v=IDENT { $n.addVarName($v); } 
       (',' v=IDENT  { $n.addVarName($v); } )* COLON )? 
    t=simpleType { $n.setType($t.n); }
    ( inv=invariantClause { $n.addInvariantClause($inv.n); } )* //+
    ;

/* ------------------------------------
  invariantClause ::= 
    "inv" [ id ] ":" expression
*/
invariantClause returns [ASTInvariantClause n]
:
      'inv' ( name=IDENT )? COLON e=expression { $n = new ASTInvariantClause($name, $e.n); }
    | 'existential' 'inv' ( name=IDENT )? COLON e=expression { $n = new ASTExistentialInvariantClause($name, $e.n); }
    ;


/* ------------------------------------
  prePost ::=
    prePostContext prePostClause { prePostClause }

  prePostContext := 
    "context" id "::" id paramList [ ":" type ]
*/
prePost returns [ASTPrePost n]
:
    'context' classname=IDENT COLON_COLON opname=IDENT pl=paramList ( COLON rt=type )?
    { $n = new ASTPrePost($classname, $opname, $pl.paramList, $rt.n); }
    ( ppc=prePostClause { n.addPrePostClause(ppc); } )+
    ;

/* ------------------------------------
  prePostClause :=
    ("pre" | "post") [ id ] ":" expression
*/
prePostClause returns [ASTPrePostClause n]
@init { Token t = null; }
:
    { t = input.LT(1); }
    ( 'pre' | 'post' )  ( name=IDENT )? COLON e=expression
    { $n = new ASTPrePostClause(t, $name, $e.n); }
    ;

alActionList returns [ASTALActionList al] 
@init{
	$al = new ASTALActionList();
}
:
	( action=alAction {$al.add($action.action);})*
;

alAction returns [ASTALAction action] 
:
    aCV = alCreateVar { $action = $aCV.var; }
|   aDl = alDelete { $action = $aDl.n; }
|   aSe = alSet { $action = $aSe.set; }
|   aSC = alSetCreate { $action = $aSC.setcreate; }
|   aIn = alInsert { $action = $aIn.insert; }
|   aDe = alDestroy { $action = $aDe.n; }
|   aIf = alIf { $action = $aIf.i; }
|   aWh = alWhile { $action = $aWh.w; }
|   aFo = alFor { $action = $aFo.f; }
|   aEx = alExec { $action = $aEx.c; }
;



alCreateVar returns [ASTALCreateVar var] 
:
	('var'|'declare') name=IDENT COLON t=type 
	{ $var = new ASTALCreateVar($name, $type.n); }
;

alSet returns [ASTALSet set]
:

    'set' lval=expression COLON_EQUAL rval=expression
    { $set = new ASTALSet($lval.n, $rval.n); }
; 

alSetCreate returns [ASTALSetCreate setcreate]
:
    'create' lval=expression COLON_EQUAL { input.LT(1).getText().equals("new") }? new_=IDENT cls=IDENT 
    ( 'namehint' nameExpr=expression )?
    { setcreate = new ASTALSetCreate($lval.n, $cls, $nameExpr.n);}
;


alInsert returns [ASTALInsert insert]
@init{List exprList = new ArrayList(); }
:
    'insert' LPAREN 
    e=expression { exprList.add($e.n); } COMMA
    e=expression { exprList.add($e.n); } ( COMMA e=expression { exprList.add($e.n); } )* 
    RPAREN 'into' id=IDENT
    { $insert = new ASTALInsert(exprList, $id); }
    ;


alDelete returns [ASTALDelete n]
@init{ List exprList = new ArrayList(); }
:
    'delete' LPAREN
    e=expression { exprList.add($e.n); } COMMA
    e=expression { exprList.add($e.n); } ( COMMA e=expression { exprList.add($e.n); } )*
    RPAREN 'from' id=IDENT
    { $n = new ASTALDelete(exprList, $id); }
    ;


alDestroy returns [ASTALDestroy n]
:
     'destroy' e=expression
    { $n = new ASTALDestroy($e.n); }
    ;

alIf returns [ASTALIf i]
:
	'if' ifexpr=expression 
	'then' thenlist=alActionList
	('else' elselist=alActionList)?
	'endif' 
	{ $i = new ASTALIf($ifexpr.n, $thenlist.al, $elselist.al); }
;

alWhile returns [ASTALWhile w]
: 
	'while' expr=expression 
	'do' 
	body=alActionList
	'wend'
	{ $w = new ASTALWhile($expr.n, $body.al); }
;


alFor returns [ASTALFor f]
: 
	'for' var=IDENT COLON t=type 'in' expr=expression 
	'do' 
	body=alActionList
	{ input.LT(1).getText().equals("next") }? next=IDENT
	{ $f = new ASTALFor($var, $t.n, $expr.n, $body.al); }
;

alExec returns [ASTALExecute c]
:
    'execute' op=expression
    { $c = new ASTALExecute($op.n); }
;
/*
--------- Start of file OCLBase.gpart -------------------- 
*/

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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

/* ------------------------------------
  expressionOnly ::= 
    conditionalImpliesExpression
*/
expressionOnly returns [ASTExpression n]
:
    nExp=expression EOF {$n = $nExp.n;}
    ;
    
/* ------------------------------------
  expression ::= 
    { "let" id [ : type ] "=" expression "in" } conditionalImpliesExpression
*/
expression returns [ASTExpression n]
@init{ 
  ASTLetExpression prevLet = null, firstLet = null;
  ASTExpression e2;
  Token tok = null;
}
:
    { tok = input.LT(1); /* remember start of expression */ }
    ( 
      'let' name=IDENT ( COLON t=type )? EQUAL e1=expression 'in'
       { ASTLetExpression nextLet = new ASTLetExpression($name, $t.n, $e1.n);
         if ( firstLet == null ) 
             firstLet = nextLet;
         if ( prevLet != null ) 
             prevLet.setInExpr(nextLet);
         prevLet = nextLet;
       }
    )*

    nCndImplies=conditionalImpliesExpression
    { if ( $nCndImplies.n != null ) {
    	 $n = $nCndImplies.n;
         $n.setStartToken(tok);
      }
      
      if ( prevLet != null ) { 
         prevLet.setInExpr($n);
         $n = firstLet;
         $n.setStartToken(tok);
      }
    }
    ;

/* ------------------------------------
  paramList ::= 
    "(" [ variableDeclaration { "," variableDeclaration } ] ")"
*/
paramList returns [List paramList]
@init{ $paramList = new ArrayList(); }
:
    LPAREN
    ( 
      v=variableDeclaration { paramList.add($v.n); }
      ( COMMA v=variableDeclaration  { paramList.add($v.n); } )* 
    )?
    RPAREN
    ;

/* ------------------------------------
  idList ::= id { "," id }
*/
idList returns [List idList]
@init{ $idList = new ArrayList(); }
:
    id0=IDENT { $idList.add($id0); }
    ( COMMA idn=IDENT { $idList.add($idn); } )*
    ;


/* ------------------------------------
  variableDeclaration ::= 
    id ":" type
*/
variableDeclaration returns [ASTVariableDeclaration n]
:
    name=IDENT COLON t=type
    { n = new ASTVariableDeclaration($name, $t.n); }
    ;
    
/* ------------------------------------
  conditionalImpliesExpression ::= 
    conditionalOrExpression { "implies" conditionalOrExpression }
*/
conditionalImpliesExpression returns [ASTExpression n]
: 
    nCndOrExp=conditionalOrExpression {$n = $nCndOrExp.n;} 
    ( op='implies' n1=conditionalOrExpression 
        { $n = new ASTBinaryExpression($op, $n, $n1.n); } 
    )*
    ;

/* ------------------------------------
  conditionalOrExpression ::= 
    conditionalXOrExpression { "or" conditionalXOrExpression }
*/
conditionalOrExpression returns [ASTExpression n]
: 
    nCndXorExp=conditionalXOrExpression {$n = $nCndXorExp.n;} 
    ( op='or' n1=conditionalXOrExpression
        { $n = new ASTBinaryExpression($op, $n, $n1.n); } 
    )*
    ;

/* ------------------------------------
  conditionalXOrExpression ::= 
    conditionalAndExpression { "xor" conditionalAndExpression }
*/
conditionalXOrExpression returns [ASTExpression n]
: 
    nCndAndExp=conditionalAndExpression {$n = $nCndAndExp.n;} 
    ( op='xor' n1=conditionalAndExpression
        { $n = new ASTBinaryExpression($op, $n, $n1.n); } 
    )*
    ;

/* ------------------------------------
  conditionalAndExpression ::= 
    equalityExpression { "and" equalityExpression }
*/
conditionalAndExpression returns [ASTExpression n]
: 
    nEqExp=equalityExpression {$n = $nEqExp.n;} 
    ( op='and' n1=equalityExpression
        { $n = new ASTBinaryExpression($op, $n, $n1.n); }
    )*
    ;

/* ------------------------------------
  equalityExpression ::= 
    relationalExpression { ("=" | "<>") relationalExpression }
*/
equalityExpression returns [ASTExpression n]
@init { Token op = null; }
: 
    nRelExp=relationalExpression {$n = $nRelExp.n;} 
    ( { op = input.LT(1); }
      (EQUAL | NOT_EQUAL) n1=relationalExpression
        { $n = new ASTBinaryExpression(op, $n, $n1.n); } 
    )*
    ;

/* ------------------------------------
  relationalExpression ::= 
    additiveExpression { ("<" | ">" | "<=" | ">=") additiveExpression }
*/
relationalExpression returns [ASTExpression n]
@init { Token op = null; }
: 
    nAddiExp=additiveExpression {$n = $nAddiExp.n;}
    ( { op = input.LT(1); }
      (LESS | GREATER | LESS_EQUAL | GREATER_EQUAL) n1=additiveExpression 
        { $n = new ASTBinaryExpression(op, $n, $n1.n); } 
    )*
    ;

/* ------------------------------------
  additiveExpression ::= 
    multiplicativeExpression { ("+" | "-") multiplicativeExpression }
*/
additiveExpression returns [ASTExpression n]
@init { Token op = null; }
: 
    nMulExp=multiplicativeExpression {$n = $nMulExp.n;}
    ( { op = input.LT(1); }
      (PLUS | MINUS) n1=multiplicativeExpression
        { $n = new ASTBinaryExpression(op, $n, $n1.n); } 
    )*
    ;


/* ------------------------------------
  multiplicativeExpression ::= 
    unaryExpression { ("*" | "/" | "div") unaryExpression }
*/
multiplicativeExpression returns [ASTExpression n]
@init { Token op = null; }
: 
    nUnExp=unaryExpression { $n = $nUnExp.n;}
    ( { op = input.LT(1); }
      (STAR | SLASH | 'div') n1=unaryExpression
        { $n = new ASTBinaryExpression(op, $n, $n1.n); } 
    )*
    ;


/* ------------------------------------
  unaryExpression ::= 
      ( "not" | "-" | "+" ) unaryExpression
    | postfixExpression
*/
unaryExpression returns [ASTExpression n]
@init { Token op = null; }
: 
      ( { op = input.LT(1); }
        ('not' | MINUS | PLUS ) 
        nUnExp=unaryExpression { $n = new ASTUnaryExpression(op, $nUnExp.n); }
      )
    | nPosExp=postfixExpression { $n = $nPosExp.n; }
    ;


/* ------------------------------------
  postfixExpression ::= 
      primaryExpression { ( "." | "->" ) propertyCall }
*/
postfixExpression returns [ASTExpression n]
@init{ boolean arrow = false; }
: 
    nPrimExp=primaryExpression { $n = $nPrimExp.n; }
    ( 
     ( ARROW { arrow = true; } | DOT { arrow = false; } ) 
		nPc=propertyCall[$n, arrow] { $n = $nPc.n; }
    )*
    ;


/* ------------------------------------
  primaryExpression ::= 
      literal
    | propertyCall
    | "(" expression ")"
    | ifExpression

  Note: propertyCall includes variables
*/

primaryExpression returns [ASTExpression n]
: 
      nLit=literal { $n = $nLit.n; }
    | nPc=propertyCall[null, false] { $n = $nPc.n; }
    | LPAREN nExp=expression RPAREN { $n = $nExp.n; }
    | nIfExp=ifExpression { $n = $nIfExp.n; }
    // HACK: the following requires k=3
    | id1=IDENT DOT 'allInstances' ( LPAREN RPAREN )?
      { $n = new ASTAllInstancesExpression($id1); }
      ( AT 'pre' { $n.setIsPre(); } ) ? 
    ;


/* ------------------------------------
  propertyCall ::= 
      queryId   "(" [ elemVarsDeclaration "|" ] expression ")"
    | "iterate" "(" elemVarsDeclaration ";" variableInitialization "|" expression ")"
    | id [ "(" actualParameterList ")" ]


  Note: source may be null (see primaryExpression).
*/
propertyCall[ASTExpression source, boolean followsArrow] returns [ASTExpression n]
:
      // this semantic predicate disambiguates operations from
      // iterate-based expressions which have a different syntax (the
      // OCL grammar is very loose here).
      { org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) }? 
      nExpQuery=queryExpression[source] { $n = $nExpQuery.n; }
    | nExpIterate=iterateExpression[source] { $n = $nExpIterate.n; }
    | nExpOperation=operationExpression[source, followsArrow] { $n = $nExpOperation.n; }
    | nExpType=typeExpression[source, followsArrow] { $n = $nExpType.n; }
    ;


/* ------------------------------------
  queryExpression ::= 
    ("select" | "reject" | "collect" | "exists" | "forAll" | "isUnique" | "sortedBy" ) 
    "(" [ elemVarsDeclaration "|" ] expression ")"
*/
queryExpression[ASTExpression range] returns [ASTExpression n]	
@init {ASTElemVarsDeclaration decl = new ASTElemVarsDeclaration(); }:
    op=IDENT 
    LPAREN 
    ( decls=elemVarsDeclaration {decl = $decls.n;} BAR )?
    nExp=expression
    RPAREN
    { $n = new ASTQueryExpression($op, $range, decl, $nExp.n); }
    ;


/* ------------------------------------
  iterateExpression ::= 
    "iterate" "(" 
    elemVarsDeclaration ";" 
    variableInitialization "|"
    expression ")"
*/
iterateExpression[ASTExpression range] returns [ASTExpression n]:
    i='iterate'
    LPAREN
    decls=elemVarsDeclaration SEMI
    init=variableInitialization BAR
    nExp=expression
    RPAREN
    { $n = new ASTIterateExpression($i, $range, $decls.n, $init.n, $nExp.n); }
    ;


/* ------------------------------------
  operationExpression ::= 
    id ( ("[" id "]") 
       | ( [ "@" "pre" ] [ "(" [ expression { "," expression } ] ")" ] ) )
*/

// Operations always require parentheses even if no arguments are
// required. This makes it easier, for example, to distinguish a
// class-defined operation from an attribute access operation where
// both operations may have the same name.

operationExpression[ASTExpression source, boolean followsArrow] 
    returns [ASTOperationExpression n]
:
    name=IDENT 
    { $n = new ASTOperationExpression($name, $source, $followsArrow); }

    ( LBRACK rolename=IDENT RBRACK { $n.setExplicitRolename($rolename); })?

    ( AT 'pre' { $n.setIsPre(); } ) ? 
    (
      LPAREN { $n.hasParentheses(); }
      ( 
	     e=expression { $n.addArg($e.n); }
	     ( COMMA e=expression { $n.addArg($e.n); } )* 
	  )?
      RPAREN
    )?
    ;


/* ------------------------------------
  typeExpression ::= 
    ("oclAsType" | "oclIsKindOf" | "oclIsTypeOf") LPAREN type RPAREN
*/

typeExpression[ASTExpression source, boolean followsArrow] 
    returns [ASTTypeArgExpression n]
@init { Token opToken = null; }
:
	{ opToken = input.LT(1); }
	( 'oclAsType' | 'oclIsKindOf' |  'oclIsTypeOf' )
	LPAREN t=type RPAREN 
      { $n = new ASTTypeArgExpression(opToken, $source, $t.n, $followsArrow); }
    ;


/* ------------------------------------
  elemVarsDeclaration ::= 
    idList [ ":" type ]
*/
elemVarsDeclaration returns [ASTElemVarsDeclaration n]
@init{ List idList; }
:
    idListRes=idList
    ( COLON t=type )?
    { $n = new ASTElemVarsDeclaration($idListRes.idList, $t.n); }
    ;


/* ------------------------------------
  variableInitialization ::= 
    id ":" type "=" expression
*/
variableInitialization returns [ASTVariableInitialization n]
:
    name=IDENT COLON t=type EQUAL e=expression
    { $n = new ASTVariableInitialization($name, $t.n, $e.n); }
    ;


/* ------------------------------------
  ifExpression ::= 
    "if" expression "then" expression "else" expression "endif"
*/
ifExpression returns [ASTExpression n]
:
    i='if' cond=expression 'then' t=expression 'else' e=expression 'endif'
        { $n = new ASTIfExpression($i, $cond.n, $t.n, $e.n); } 
    ;


/* ------------------------------------
  literal ::= 
      "true"
    | "false"
    | INT
    | REAL
    | STRING
    | "#" id
    | collectionLiteral
    | emptyCollectionLiteral
    | undefinedLiteral
    | tupleLiteral
*/
literal returns [ASTExpression n]
:
      t='true'   { $n = new ASTBooleanLiteral(true); }
    | f='false'  { $n = new ASTBooleanLiteral(false); }
    | i=INT    { $n = new ASTIntegerLiteral($i); }
    | r=REAL   { $n = new ASTRealLiteral($r); }
    | s=STRING { $n = new ASTStringLiteral($s); }
    | HASH enumLit=IDENT { $n = new ASTEnumLiteral($enumLit); }
    | nColIt=collectionLiteral { $n = $nColIt.n; }
    | nEColIt=emptyCollectionLiteral { $n = $nEColIt.n; }
    | nUndLit=undefinedLiteral {$n = $nUndLit.n; }
    | nTupleLit=tupleLiteral {$n = $nTupleLit.n; }
    ;


/* ------------------------------------
  collectionLiteral ::= 
    ( "Set" | "Sequence" | "Bag" | "OrderedSet" ) "{" collectionItem { "," collectionItem } "}"
*/
collectionLiteral returns [ASTCollectionLiteral n]
@init { Token op = null; }
:
    { op = input.LT(1); } 
    ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) 
    { $n = new ASTCollectionLiteral(op); }
    LBRACE 
    (
      ci=collectionItem { $n.addItem($ci.n); } 
      ( COMMA ci=collectionItem { $n.addItem($ci.n); } )*
    )? 
    RBRACE
    ;

/* ------------------------------------
  collectionItem ::=
    expression [ ".." expression ]
*/
collectionItem returns [ASTCollectionItem n]
@init{ $n = new ASTCollectionItem(); }
:
    e=expression { $n.setFirst($e.n); } 
    ( DOTDOT e=expression { $n.setSecond($e.n); } )?
    ;


/* ------------------------------------
  emptyCollectionLiteral ::= 
    "oclEmpty" "(" collectionType ")"

  Hack for avoiding typing problems with e.g. Set{}
*/
emptyCollectionLiteral returns [ASTEmptyCollectionLiteral n]
:
    'oclEmpty' LPAREN t=collectionType RPAREN
    { $n = new ASTEmptyCollectionLiteral($t.n); }
    ;


/* ------------------------------------
  undefinedLiteral ::= 
    "oclUndefined" "(" type ")"

  OCL extension
*/
undefinedLiteral returns [ASTUndefinedLiteral n]
:
    'oclUndefined' LPAREN t=type RPAREN
    { $n = new ASTUndefinedLiteral($t.n); }
|
    'Undefined'
    { $n = new ASTUndefinedLiteral(); }
|
    'null'
    { $n = new ASTUndefinedLiteral(); }
    ;


/* ------------------------------------
  tupleLiteral ::= 
    "Tuple" "{" tupleItem { "," tupleItem } "}"
*/
tupleLiteral returns [ASTTupleLiteral n]
@init{ List tiList = new ArrayList(); }
:
    'Tuple'
    LBRACE
    ti=tupleItem { tiList.add($ti.n); } 
    ( COMMA ti=tupleItem { tiList.add($ti.n); } )*
    RBRACE
    { $n = new ASTTupleLiteral(tiList); }
    ;

/* ------------------------------------
  tupleItem ::= id ":" expression
*/
tupleItem returns [ASTTupleItem n]
:
    name=IDENT (COLON|EQUAL) e=expression 
    { $n = new ASTTupleItem($name, $e.n); } 
    ;


/* ------------------------------------
  type ::= 
      simpleType 
    | collectionType
    | tupleType
*/
type returns [ASTType n]
@init { Token tok = null; }
:
    { tok = input.LT(1); /* remember start of type */ }
    (
      nTSimple=simpleType { $n = $nTSimple.n; $n.setStartToken(tok); }
    | nTCollection=collectionType { $n = $nTCollection.n; $n.setStartToken(tok); }
    | nTTuple=tupleType { $n = $nTTuple.n; $n.setStartToken(tok); }
    )
    ;


typeOnly returns [ASTType n]
:
    nT=type EOF { $n = $nT.n; }
    ;


/* ------------------------------------
  simpleType ::= id 

  A simple type may be a basic type (Integer, Real, Boolean, String),
  an enumeration type, an object type, or OclAny.
*/
simpleType returns [ASTSimpleType n]
:
    name=IDENT { $n = new ASTSimpleType($name); }
    ;


/* ------------------------------------
  collectionType ::= 
    ( "Collection" | "Set" | "Sequence" | "Bag" | "OrderedSet" ) "(" type ")"
*/
collectionType returns [ASTCollectionType n]
@init { Token op = null; }
:
    { op = input.LT(1); } 
    ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) 
    LPAREN elemType=type RPAREN
    { $n = new ASTCollectionType(op, $elemType.n); $n.setStartToken(op);}
    ;


/* ------------------------------------
  tupleType ::= "Tuple" "(" tuplePart { "," tuplePart } ")"
*/
tupleType returns [ASTTupleType n]
@init{ List tpList = new ArrayList(); }
:
    'Tuple' LPAREN 
    tp=tuplePart { tpList.add($tp.n); } 
    ( COMMA tp=tuplePart { tpList.add($tp.n); } )* 
    RPAREN
    { $n = new ASTTupleType(tpList); }
    ;


/* ------------------------------------
  tuplePart ::= id ":" type
*/
tuplePart returns [ASTTuplePart n]
:
    name=IDENT COLON t=type
    { $n = new ASTTuplePart($name, $t.n); }
    ;
/*
--------- Start of file OCLLexerRules.gpart -------------------- 
*/

// Whitespace -- ignored
WS:
    ( ' '
    | '\t'
    | '\f'
    | NEWLINE
    )
    { $channel=HIDDEN; }
    ;

// Single-line comments
SL_COMMENT:
    ('//' | '--')
    (~('\n'|'\r'))* NEWLINE
    { $channel=HIDDEN; }
    ;

// multiple-line comments
ML_COMMENT:
    '/*' ( options {greedy=false;} : . )* '*/' { $channel=HIDDEN; };

fragment
NEWLINE	:	
    '\r\n' | '\r' | '\n';
    
// Use paraphrases for nice error messages
ARROW 		 : '->';
AT     		 : '@';
BAR 		 : '|';
COLON 		 : ':';
COLON_COLON	 : '::';
COLON_EQUAL	 : ':=';
COMMA 		 : ',';
DOT 		 : '.';
DOTDOT 		 : '..';
EQUAL 		 : '=';
GREATER 	 : '>';
GREATER_EQUAL : '>=';
HASH 		 : '#';
LBRACE 		 : '{';
LBRACK 		 : '[';
LESS 		 : '<';
LESS_EQUAL 	 : '<=';
LPAREN 		 : '(';
MINUS 		 : '-';
NOT_EQUAL 	 : '<>';
PLUS 		 : '+';
RBRACE 		 : '}';
RBRACK 		 : ']';
RPAREN		 : ')';
SEMI		 : ';';
SLASH 		 : '/';
STAR 		 : '*';

fragment
INT:
    ('0'..'9')+
    ;

fragment
REAL:
    INT ('.' INT (('e' | 'E') ('+' | '-')? INT)? | ('e' | 'E') ('+' | '-')? INT)
    ;

RANGE_OR_INT:
      ( INT '..' )      => INT    { $type=INT; }
    | ( REAL )          => REAL   { $type=REAL; }
    |   INT                       { $type=INT; }
    ;

// String literals

STRING:	
    '\'' ( ~('\''|'\\') | ESC)* '\'';

// escape sequence -- note that this is protected; it can only be called
//   from another lexer rule -- it will not ever directly return a token to
//   the parser
// There are various ambiguities hushed in this rule.  The optional
// '0'...'7' digit matches should be matched here rather than letting
// them go back to STRING_LITERAL to be matched.  ANTLR does the
// right thing by matching immediately; hence, it's ok to shut off
// the FOLLOW ambig warnings.
fragment
ESC
:
    '\\'
     ( 'n'
     | 'r'
     | 't'
     | 'b'
     | 'f'
     | '"'
     | '\''
     | '\\'
     | 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
     | '0'..'3' ('0'..'7' ('0'..'7')? )?  | '4'..'7' ('0'..'7')?
     )
     ;

// hexadecimal digit (again, note it's protected!)
fragment
HEX_DIGIT:
    ( '0'..'9' | 'A'..'F' | 'a'..'f' );


// An identifier.  Note that testLiterals is set to true!  This means
// that after we match the rule, we look in the literals table to see
// if it's a literal or really an identifer.

IDENT:
    ('$' | 'a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*
    ;

// A dummy rule to force vocabulary to be all characters (except
// special ones that ANTLR uses internally (0 to 2)

fragment
VOCAB:	
    '\U0003'..'\U0377'
    ;
