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

// set package for all generated classes
header { 
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

package org.tzi.use.parser.use; 
}

// ------------------------------------
//  USE parser
// ------------------------------------

{
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.PrintWriter;
import org.tzi.use.parser.MyToken;
import org.tzi.use.parser.ParseErrorHandler;

import org.tzi.use.parser.ocl.*;
}
class GUSEParser extends GOCLParser;
options {
    exportVocab = GUSE;
    defaultErrorHandler = true;
    buildAST = false;
    k = 5;
    //codeGenMakeSwitchThreshold = 2;
    //codeGenBitsetTestThreshold = 3;
}
 

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
{ 
    ASTEnumTypeDefinition e = null;
    ASTAssociation a = null;
    ASTConstraintDefinition cons = null;
    ASTPrePost ppc = null;
    n = null;
}
:
    "model" name:IDENT { n = new ASTModel((MyToken) name); }
    ( e=enumTypeDefinition { n.addEnumTypeDef(e); } )*
    (   ( generalClassDefinition[n] )
      | ( a=associationDefinition { n.addAssociation(a); } )
      | ( "constraints"
          (   cons=invariant { n.addConstraint(cons); } 
            | ppc=prePost { n.addPrePost(ppc); } 
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
{ List idList; n = null; }
:
    "enum" name:IDENT LBRACE idList=idList RBRACE ( SEMI )?
        { n = new ASTEnumTypeDefinition((MyToken) name, idList); }
    ;



/* ------------------------------------
  generalClassDefinition ::=
    [ "abstract" ]
   	 { classDefinition | associationClassDefinition }
*/
generalClassDefinition[ASTModel n]
{ boolean isAbstract = false;
  ASTClass c = null;
  ASTAssociationClass ac = null;}
:
    ( "abstract" { isAbstract = true; } )? 
    ( ( c=classDefinition[isAbstract] { n.addClass(c); } ) | 
      ( ac=associationClassDefinition[isAbstract] { n.addAssociationClass(ac); } ) )
       
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
{ List idList; n = null; }
:
    "class" name:IDENT { n = new ASTClass((MyToken) name, isAbstract); }
    ( LESS idList=idList { n.addSuperClasses(idList); } )?
    ( "attributes" 
      { ASTAttribute a; }
      ( a=attributeDefinition { n.addAttribute(a); } )* 
    )?
    ( "operations" 
      { ASTOperation op; }
      ( op=operationDefinition { n.addOperation(op); } )* 
    )?
    ( "constraints"
      ( { ASTInvariantClause inv; } 
        inv=invariantClause { n.addInvariantClause(inv); }
      )*
    )?
    "end"
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
{List idList; n = null; ASTAssociationEnd ae; }
:
    { MyToken t1 = (MyToken) LT(1); } ("associationClass"|"associationclass") 
        { 
            if (t1.getText().equals("associationClass")) {
               reportWarning("the 'associationClass' keyword is deprecated and will " +
                             "not be supported in the future, use 'associationclass' instead");
            }  
        }
    name:IDENT { n = new ASTAssociationClass((MyToken) name, isAbstract); }
    ( LESS idList=idList { n.addSuperClasses(idList); } )?
    "between"
    ae=associationEnd { n.addEnd(ae); }
    ( ae=associationEnd { n.addEnd(ae); } )+
    ( "attributes" 
      { ASTAttribute a; }
      ( a=attributeDefinition { n.addAttribute(a); } )* 
    )?
    ( "operations" 
      { ASTOperation op; }
      ( op=operationDefinition { n.addOperation(op); } )* 
    )?
    ( "constraints"
      ( { ASTInvariantClause inv; } 
        inv=invariantClause { n.addInvariantClause(inv); }
      )*
    )?
    ( { MyToken t = (MyToken) LT(1); }
      ( "aggregation" | "composition" )
      { n.setKind(t); }
    )?
    "end"
    ;








/* ------------------------------------
  attributeDefinition ::= 
    id ":" type [ ";" ]
*/
attributeDefinition returns [ASTAttribute n]
{ ASTType t; n = null; }
:
    name:IDENT COLON t=type ( SEMI )?
        { n = new ASTAttribute((MyToken) name, t); }
    ;


/* ------------------------------------
  operationDefinition ::= 
    id paramList [ ":" type [ "=" expression ] ] 
    { prePostClause } [ ";" ]
*/
operationDefinition returns [ASTOperation n]
{ List pl; ASTType t = null; ASTExpression e = null; 
  ASTPrePostClause ppc = null; n = null; 
  ASTALActionList al = null;
}
:
    name:IDENT
    pl=paramList
    ( COLON t=type )?
    (EQUAL e=expression )? 
	("begin" al=alActionList "end" )?
    { n = new ASTOperation((MyToken) name, pl, t, e,al); }
    ( ppc=prePostClause { n.addPrePostClause(ppc); } )*
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
{ ASTAssociationEnd ae; n = null; }
:
    { MyToken t = (MyToken) LT(1); }
    ( "association" | "aggregation" | "composition" )
    //    ( classDefinition | (name:IDENT { n = new ASTAssociation(t, (MyToken) name); }) )
    name:IDENT { n = new ASTAssociation(t, (MyToken) name); }
    "between"
    ae=associationEnd { n.addEnd(ae); }
    ( ae=associationEnd { n.addEnd(ae); } )+
    "end"
    ;


/* ------------------------------------
  associationEnd ::= 
    id "[" multiplicity "]" [ "role" id ] [ "ordered" ] [ ";" ]
*/
associationEnd returns [ASTAssociationEnd n]
{ ASTMultiplicity m; n = null; }
:
    name:IDENT LBRACK m=multiplicity RBRACK 
    { n = new ASTAssociationEnd((MyToken) name, m); } 
    ( "role" rn:IDENT { n.setRolename((MyToken) rn); } )?
    ( "ordered" { n.setOrdered(); } )?
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
{ ASTMultiplicityRange mr; n = null; }
:
    { 
	MyToken t = (MyToken) LT(1); // remember start position of expression
	n = new ASTMultiplicity(t); 
    }
    mr=multiplicityRange { n.addRange(mr); }
    ( COMMA mr=multiplicityRange  { n.addRange(mr); } )*
    ;

multiplicityRange returns [ASTMultiplicityRange n]
{ int ms1, ms2; n = null; }
:
    ms1=multiplicitySpec { n = new ASTMultiplicityRange(ms1); }
    ( DOTDOT ms2=multiplicitySpec { n.setHigh(ms2); } )?
    ;

multiplicitySpec returns [int m]
{ m = -1; }
:
      i:INT { m = Integer.parseInt(i.getText()); }
    | STAR  { m = -1; }
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
{ n = null; ASTType t = null; ASTInvariantClause inv = null; }
:
    { n = new ASTConstraintDefinition(); }
    "context"
    ( v:IDENT COLON { n.setVarName((MyToken) v); } )? // requires k = 2

    t=simpleType { n.setType(t); }
    ( inv=invariantClause { n.addInvariantClause(inv); } )* //+
    ;


/* ------------------------------------
  invariantClause ::= 
    "inv" [ id ] ":" expression
*/
invariantClause returns [ASTInvariantClause n]
{ ASTExpression e; n = null; }
:
    "inv" ( name:IDENT )? COLON e=expression
    { n = new ASTInvariantClause((MyToken) name, e); }
    ;


/* ------------------------------------
  prePost ::=
    prePostContext prePostClause { prePostClause }

  prePostContext := 
    "context" id "::" id paramList [ ":" type ]
*/
prePost returns [ASTPrePost n]
{ n = null; List pl = null; ASTType rt = null; ASTPrePostClause ppc = null; }
:
    "context" classname:IDENT COLON_COLON opname:IDENT pl=paramList ( COLON rt=type )?
    { n = new ASTPrePost((MyToken) classname, (MyToken) opname, pl, rt); }
    ( ppc=prePostClause { n.addPrePostClause(ppc); } )+
    ;

/* ------------------------------------
  prePostClause :=
    ("pre" | "post") [ id ] ":" expression
*/
prePostClause returns [ASTPrePostClause n]
{ ASTExpression e; n = null; }
:
    { MyToken t = (MyToken) LT(1); }
    ( "pre" | "post" )  ( name:IDENT )? COLON e=expression
    { n = new ASTPrePostClause(t, (MyToken) name, e); }
    ;



alActionList returns [ASTALActionList al] 
{
	al = null;
	ASTALAction action = null;
	al = new ASTALActionList();
}
:
	( action=alAction {al.add(action);})*
;

alAction returns [ASTALAction action] 
{
	action = null;
}
:
    action = alCreateVar
|	action = alDelete
|	action = alSet
|	action = alSetCreate
|   action = alInsert
|	action = alDestroy
|	action = alIf
|	action = alWhile
|   action = alFor
|   action = alExec
;



alCreateVar returns [ASTALCreateVar var] 
{
	var = null;
	ASTType type = null;
}
:
	("var"|"declare") name:IDENT COLON type=type 
	{ var = new ASTALCreateVar(name,type); }
;

alSet returns [ASTALSet set]
{ 
    set = null;
    ASTExpression lval = null;
    ASTExpression rval = null;
}
:

    "set" lval=expression COLON_EQUAL rval=expression
    { set = new ASTALSet(lval, rval); }
; 

alSetCreate returns [ASTALSetCreate setcreate]
{
    setcreate = null;
    ASTExpression lval = null;
    ASTExpression nameExpr = null;
}
:
    "create" lval=expression COLON_EQUAL { LT(1).getText().equals("new") }? new_:IDENT cls:IDENT 
    ( "namehint" nameExpr=expression )?
    { setcreate = new ASTALSetCreate(lval, (MyToken)cls, nameExpr);}
;


alInsert returns [ASTALInsert insert]
{ ASTExpression e; List exprList = new ArrayList(); insert = null; }
:
    "insert" LPAREN 
    e=expression { exprList.add(e); } COMMA
    e=expression { exprList.add(e); } ( COMMA e=expression { exprList.add(e); } )* 
    RPAREN "into" id:IDENT
    { insert = new ASTALInsert(exprList, (MyToken) id); }
    ;


alDelete returns [ASTALDelete n]
{ ASTExpression e; List exprList = new ArrayList(); n = null; }
:
    "delete" LPAREN
    e=expression { exprList.add(e); } COMMA
    e=expression { exprList.add(e); } ( COMMA e=expression { exprList.add(e); } )*
    RPAREN "from" id:IDENT
    { n = new ASTALDelete(exprList, (MyToken) id); }
    ;


alDestroy returns [ASTALDestroy n]
{ ASTExpression e = null;  n = null; }
:
     "destroy" e=expression
    { n = new ASTALDestroy(e); }
    ;

alIf returns [ASTALIf i]
{
	i = null;
	ASTExpression ifexpr;
	ASTALActionList thenlist;
	ASTALActionList elselist=null;
}
:
	"if" ifexpr=expression 
	"then" thenlist=alActionList
	("else" elselist=alActionList)?
	"endif" 
	{ i = new ASTALIf(ifexpr,thenlist,elselist); }
;

alWhile returns [ASTALWhile w]
{
	w = null;
	ASTExpression expr;
	ASTALActionList body;
}
: 
	"while" expr=expression 
	"do" 
	body=alActionList
	"wend"
	{ w = new ASTALWhile(expr,body); }
;


alFor returns [ASTALFor f]
{
	f = null;
	ASTExpression expr;
	ASTALActionList body;
	ASTType type;
}
: 
	"for" var:IDENT COLON type=type "in" expr=expression 
	"do" 
	body=alActionList
	{ LT(1).getText().equals("next") }? next:IDENT
	{ f = new ASTALFor(var,type,expr,body); }
;

alExec returns [ASTALExecute c]
{ 
    ASTExpression op;
    c=null;
}
:
    "execute" op=expression
    { c = new ASTALExecute(op); }
;


// ------------------------------------
// USE lexer
// ------------------------------------
{
	import java.io.PrintWriter;
	import org.tzi.use.util.Log;
	import org.tzi.use.util.StringUtil;
	import org.tzi.use.parser.ParseErrorHandler;
	import org.tzi.use.parser.MyToken;
}
class GUSELexer extends GOCLLexer;
options {
    importVocab=GUSE;

	// MyLexer.reportError depends on defaultErrorHandler == true for
	// providing correct column number information
    defaultErrorHandler = true;	

    // don't automatically test all tokens for literals
    testLiterals = false;    

    k = 2;
}

{
	protected int fTokColumn = 1;
    private PrintWriter fErr;
    private ParseErrorHandler fParseErrorHandler;
	
	    public void consume() throws CharStreamException {
        if (inputState.guessing == 0 ) {
            if (text.length() == 0 ) {
                // remember token start column
                fTokColumn = getColumn();
            }
        }
        super.consume();
    }

    public String getFilename() {
        return fParseErrorHandler.getFileName();
    }
    
    protected Token makeToken(int t) {
        MyToken token = 
            new MyToken(getFilename(), getLine(), fTokColumn);
        token.setType(t);
        if (t == EOF )
            token.setText("end of file or input");
        return token;
    }

    public void reportError(RecognitionException ex) {
        fParseErrorHandler.reportError(
                ex.getLine() + ":" + ex.getColumn() + ": " + ex.getMessage());
    }
 

    /**
     * Returns true if word is a reserved keyword. 
     */
    public boolean isKeyword(String word) {
        ANTLRHashString s = new ANTLRHashString(word, this);
        boolean res = literals.get(s) != null;
        Log.trace(this, "keyword " + word + ": " + res);
        return res;
    }

    public void traceIn(String rname) throws CharStreamException {
        traceIndent();
        traceDepth += 1;
        System.out.println("> lexer " + rname + ": c == '" + 
                           StringUtil.escapeChar(LA(1), '\'') + "'");
    }

    public void traceOut(String rname) throws CharStreamException {
        traceDepth -= 1;
        traceIndent();
        System.out.println("< lexer " + rname + ": c == '" +
                           StringUtil.escapeChar(LA(1), '\'') + "'");
    }
    
    public void init(ParseErrorHandler handler) {
        fParseErrorHandler = handler;
    }
}

// Whitespace -- ignored
WS:
    ( ' '
    | '\t'
    | '\f'
    | (   "\r\n"
    | '\r'
	| '\n'
      )
      { newline(); }
    )
    { $setType(Token.SKIP); }
    ;

// Single-line comments
SL_COMMENT:
    ("//" | "--")
    (~('\n'|'\r'))* ('\n'|'\r'('\n')?)
    { $setType(Token.SKIP); newline(); }
    ;

// multiple-line comments
ML_COMMENT:
    "/*"
    ( options { generateAmbigWarnings = false; } : { LA(2)!='/' }? '*'
    | '\r' '\n' { newline(); }
    | '\r'	{ newline(); }
    | '\n'	{ newline(); }
    | ~('*'|'\n'|'\r')
    )*
    "*/"
    { $setType(Token.SKIP); }
    ;

// Use paraphrases for nice error messages
//EOF 		options { paraphrase = "\"end of file\""; } : EOF;
ARROW 		options { paraphrase = "'->'"; }        : "->";
AT     		options { paraphrase = "'@'"; }         : '@';
BAR 		options { paraphrase = "'|'"; }         : '|';
COLON 		options { paraphrase = "':'"; }         : ':';
COLON_COLON	options { paraphrase = "'::'"; }        : "::";
COLON_EQUAL	options { paraphrase = "':='"; }        : ":=";
COMMA 		options { paraphrase = "','"; }         : ',';
DOT 		options { paraphrase = "'.'"; }         : '.';
DOTDOT 		options { paraphrase = "'..'"; }        : "..";
EQUAL 		options { paraphrase = "'='"; }         : '=';
GREATER 	options { paraphrase = "'>'"; }         : '>';
GREATER_EQUAL 	options { paraphrase = "'>='"; }        : ">=";
HASH 		options { paraphrase = "'#'"; }         : '#';
LBRACE 		options { paraphrase = "'{'"; }         : '{';
LBRACK 		options { paraphrase = "'['"; }         : '[';
LESS 		options { paraphrase = "'<'"; }         : '<';
LESS_EQUAL 	options { paraphrase = "'<='"; }        : "<=";
LPAREN 		options { paraphrase = "'('"; }         : '(';
MINUS 		options { paraphrase = "'-'"; }         : '-';
NOT_EQUAL 	options { paraphrase = "'<>'"; }        : "<>";
PLUS 		options { paraphrase = "'+'"; }         : '+';
RBRACE 		options { paraphrase = "'}'"; }         : '}';
RBRACK 		options { paraphrase = "']'"; }         : ']';
RPAREN		options { paraphrase = "')'"; }         : ')';
SEMI		options { paraphrase = "';'"; }         : ';';
SLASH 		options { paraphrase = "'/'"; }         : '/';
STAR 		options { paraphrase = "'*'"; }         : '*';

protected
INT:
    ('0'..'9')+
    ;

protected
REAL:
    INT ( '.' INT )? 
    ( ('e'|'E') ('+'|'-')? INT )?
    ;

RANGE_OR_INT:
      ( INT ".." )    => INT    { $setType(INT); }
    | ( INT '.' INT)  => REAL   { $setType(REAL); }
    | ( INT ('e'|'E'))  => REAL { $setType(REAL); }
    |   INT                     { $setType(INT); }
    ;

// String literals

STRING
{ char c1; StringBuffer s = new StringBuffer(); }
:	
    '\'' { s.append('\''); } 
    (   c1=ESC { s.append(c1); } 
      | 
        c2:~('\''|'\\') { s.append(c2); } 
    )* 
    '\'' { s.append('\''); } 
    { $setText(s.toString()); }
    ;

// escape sequence -- note that this is protected; it can only be called
//   from another lexer rule -- it will not ever directly return a token to
//   the parser
// There are various ambiguities hushed in this rule.  The optional
// '0'...'7' digit matches should be matched here rather than letting
// them go back to STRING_LITERAL to be matched.  ANTLR does the
// right thing by matching immediately; hence, it's ok to shut off
// the FOLLOW ambig warnings.
protected
ESC returns [char c]
{ c = 0; int h0,h1,h2,h3; }
:
    '\\'
     (  'n' { c = '\n'; }
     | 'r' { c = '\r'; }
     | 't' { c = '\t'; }
     | 'b' { c = '\b'; }
     | 'f' { c = '\f'; }
     | '"' { c = '"'; }
     | '\'' { c = '\''; }
     | '\\' { c = '\\'; }
     | ('u')+ 
       h3=HEX_DIGIT h2=HEX_DIGIT h1=HEX_DIGIT h0=HEX_DIGIT 
	 { c = (char) (h0 + h1 * 16 + h2 * 16 * 16 + h3 * 16 * 16 * 16); }
     | o1:'0'..'3' { c = (char) Character.digit(o1, 8); } 
        ( options { warnWhenFollowAmbig = false; } : o2:'0'..'7' 
          { c = (char) (c * 8 + Character.digit(o2, 8)); } 
          ( options { warnWhenFollowAmbig = false; } : o3:'0'..'7' 
            { c = (char) (c * 8 + Character.digit(o3, 8)); } 
          )? 
        )?
     | d1:'4'..'7' { c = (char) Character.digit(d1, 8); } 
       ( options { warnWhenFollowAmbig = false; } : d2:'0'..'7' 
         { c = (char) (c * 8 + Character.digit(d2, 8)); } 
       )?
     )
     ;

// hexadecimal digit (again, note it's protected!)
protected
HEX_DIGIT returns [int n]
{ n = 0; }
:
    (   c1:'0'..'9' { n = Character.digit(c1, 16); }
      | c2:'A'..'F' { n = Character.digit(c2, 16); }
      | c3:'a'..'f' { n = Character.digit(c3, 16); }
    )
    ;


// An identifier.  Note that testLiterals is set to true!  This means
// that after we match the rule, we look in the literals table to see
// if it's a literal or really an identifer.

IDENT
    options { 
        testLiterals = true; 
	paraphrase = "an identifier";
    } :	
    ('$' |'a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*
    ;

// A dummy rule to force vocabulary to be all characters (except
// special ones that ANTLR uses internally (0 to 2)

protected
VOCAB:	
    '\3'..'\377'
    ;
