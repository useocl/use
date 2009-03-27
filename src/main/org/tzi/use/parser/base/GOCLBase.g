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
parser grammar GOCLBase;

tokens
{
	LITERAL_oclAsType = 'oclAsType';
	LITERAL_oclIsKindOf = 'oclIsKindOf';
	LITERAL_oclIsTypeOf = 'oclIsTypeOf';
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
    }

    /* Overridden methods. */
	private ParseErrorHandler fParseErrorHandler;
    
    public void emitErrorMessage(String msg) {
       	fParseErrorHandler.reportError(msg);
	}
}

// grammar start


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
  expression ::= 
    { "let" id [ : type ] "=" expression "in" } conditionalImpliesExpression
*/
expression returns [ASTExpression n]
@init{ 
  ASTLetExpression prevLet = null, firstLet = null;
  ASTExpression e2;
}
:
    { Token tok = input.LT(1); /* remember start of expression */ }
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
: 
    nRelExp=relationalExpression {$n = $nRelExp.n;} 
    ( { Token op = input.LT(1); }
      (EQUAL | NOT_EQUAL) n1=relationalExpression
        { $n = new ASTBinaryExpression(op, $n, $n1.n); } 
    )*
    ;


/* ------------------------------------
  relationalExpression ::= 
    additiveExpression { ("<" | ">" | "<=" | ">=") additiveExpression }
*/
relationalExpression returns [ASTExpression n]
: 
    nAddiExp=additiveExpression {$n = $nAddiExp.n;}
    ( { Token op = input.LT(1); }
      (LESS | GREATER | LESS_EQUAL | GREATER_EQUAL) n1=additiveExpression 
        { $n = new ASTBinaryExpression(op, $n, $n1.n); } 
    )*
    ;


/* ------------------------------------
  additiveExpression ::= 
    multiplicativeExpression { ("+" | "-") multiplicativeExpression }
*/
additiveExpression returns [ASTExpression n]
: 
    nMulExp=multiplicativeExpression {$n = $nMulExp.n;}
    ( { Token op = input.LT(1); }
      (PLUS | MINUS) n1=multiplicativeExpression
        { $n = new ASTBinaryExpression(op, $n, $n1.n); } 
    )*
    ;


/* ------------------------------------
  multiplicativeExpression ::= 
    unaryExpression { ("*" | "/" | "div") unaryExpression }
*/
multiplicativeExpression returns [ASTExpression n]
: 
    nUnExp=unaryExpression { $n = $nUnExp.n;}
    ( { Token op = input.LT(1); }
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
: 
      ( { Token op = input.LT(1); }
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
      { isQueryIdent(input.LT(1)) }? 
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

// Using lookahead k=2 instead of a syntactic predicate for
// disambiguating between variableDeclaration and expression (both may
// start with an IDENT) seems to be the better alternative in
// queryOperation. It avoids guessing (k=1) which turns off actions in
// almost all rules and blows up the generated parser.

queryExpression[ASTExpression range] returns [ASTExpression n]	
@init {ASTElemVarsDeclaration decl = new ASTElemVarsDeclaration(); }:
    op=IDENT 
    LPAREN 
    ( decls=elemVarsDeclaration {decl = $decls.n;} BAR )? // requires k=2 
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
:
	{ Token opToken = input.LT(1); }
	( LITERAL_oclAsType | LITERAL_oclIsKindOf |  LITERAL_oclIsTypeOf )
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
    ( "Set" | "Sequence" | "Bag" ) "{" collectionItem { "," collectionItem } "}"
*/
collectionLiteral returns [ASTCollectionLiteral n]
:
    { Token op = input.LT(1); } 
    ( 'Set' | 'Sequence' | 'Bag' ) 
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
:
    { Token tok = input.LT(1); /* remember start of type */ }
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
    ( "Collection" | "Set" | "Sequence" | "Bag" ) "(" type ")"
*/
collectionType returns [ASTCollectionType n]
:
    { Token op = input.LT(1); } 
    ( 'Collection' | 'Set' | 'Sequence' | 'Bag' ) 
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
