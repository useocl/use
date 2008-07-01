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

parser grammar GUSEBase;

import GOCLBase;

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

package @package; 

// ------------------------------------
//  USE parser
// ------------------------------------

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.PrintWriter;
import org.tzi.use.parser.ParseErrorHandler;

import @base_packageimport.*;
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
	
	/** Parser warning-reporting function */
    public void reportWarning(String s) {
        if (getSourceName() == null) {
            System.err.println("warning: " + s);
        }
        else {
            System.err.println(getSourceName() + ": warning: " + s);
        }
    }
}

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
@init {List idList; }
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
    ( { Token t = input.LT(1); }
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
:
    { Token t = input.LT(1); }
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
    ( v=IDENT COLON { $n.setVarName($v); } )? // requires k = 2

    t=simpleType { $n.setType($t.n); }
    ( inv=invariantClause { $n.addInvariantClause($inv.n); } )* //+
    ;


/* ------------------------------------
  invariantClause ::= 
    "inv" [ id ] ":" expression
*/
invariantClause returns [ASTInvariantClause n]
:
    'inv' ( name=IDENT )? COLON e=expression
    { $n = new ASTInvariantClause($name, $e.n); }
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
:
    { Token t = input.LT(1); }
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