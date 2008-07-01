grammar GGenerator;

import GUSEBase, GOCLLexerRules;

@header { 
package org.tzi.use.parser.generator;

import org.tzi.use.parser.*;
import org.tzi.use.parser.ocl.*;
import org.tzi.use.parser.use.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
}
 
@members {
	private ParseErrorHandler fParseErrorHandler;
    
    public void init(ParseErrorHandler handler) {
        fParseErrorHandler = handler;
		this.gGUSEBase.init(handler);
    }
    
    /* Overridden methods. */
    public void emitErrorMessage(String msg) {
       	fParseErrorHandler.reportError(msg);
	}
 }
 
@lexer::header {
package org.tzi.use.parser.generator;

import org.tzi.use.parser.ParseErrorHandler;
}
 
@lexer::members {
	private ParseErrorHandler fParseErrorHandler;
    
    public void init(ParseErrorHandler handler) {
        fParseErrorHandler = handler;
		this.gGOCLLexerRules.init(handler);
    }
    
    /* Overridden methods. */
    public void reportError(RecognitionException ex) {
        fParseErrorHandler.reportError(
	        ex.line + ":" + (ex.charPositionInLine + 1) + ": " + ex.getMessage());
    }
 }
 
// ------------------------------------
// Generator grammar (extends USE grammar)
// ------------------------------------


// ++++++++++++++++++++++++++++++++++++++++++
// grammar for compiling class invariants

/* ------------------------------------
  invariantListOnly ::= { invariant }
*/
invariantListOnly returns [List invariantList] 
@init { $invariantList = new ArrayList(); } 
:
    ( def=invariant { $invariantList.add($def.n); } )*
    EOF
    ;


// ++++++++++++++++++++++++++++++++++++++++++
// grammar for compiling generator procedures

/*
procedureListOnly ::= (procedure)*

procedure ::= 'procedure' id '(' variableDeclarationList ')'
              ("var" variableDeclarationList ";")?
              "begin" instructionList "end" ";"

variableDeclarationList ::= (variableDeclaration ("," variableDeclaration)* )?

instructionList ::= ( instruction ";" )*

instruction ::= variableAssignment
                | attributeAssignment
                | loop
                | atomicInstruction
                | ifThenElse
// Not yet implemented: cut

variableAssignment ::= id ":=" valueInstruction

attributeAssignment ::= oclExpression DOT id ":=" valueInstruction

loop ::= "for" variableDeclaration "in" oclExpression
	 "begin" instructionList "end"

valueInstruction ::= atomicInstruction | oclExpression

atomicInstruction ::=
   id "(" ( instructionParameter (","instructionParameter)* )? ")"

instructionParameter ::= oclExpression | instrParameterIdent

instrParameterIdent ::= id

oclExpression ::= "[" expression "]"
*/


/* ------------------------------------
procedureListOnly ::= (procedure)*
*/
procedureListOnly returns [List procedureList]
@init{ $procedureList = new ArrayList(); }
:
    (
      proc=procedure { $procedureList.add($proc.proc); }
    )*
    EOF
    ;


/* ------------------------------------
procedure ::= "procedure" id "(" variableDeclarationList ")"
              ("var" variableDeclarationList ";")?
              "begin" instructionList "end" ";"
*/
procedure returns [ASTGProcedure proc]
@init{ localDecls = new ArrayList(); }
:
    'procedure' name=IDENT LPAREN parameterDecls=variableDeclarationList RPAREN
    ( 'var' localDecls=variableDeclarationList SEMI )?
    'begin' instructions=instructionList 'end' SEMI
    { proc = new ASTGProcedure($name, $parameterDecls.varDecls, $localDecls.varDecls, $instructions.instructions ); }
    ;


/* ------------------------------------
variableDeclarationList ::= (variableDeclaration ("," variableDeclaration)* )?
*/
variableDeclarationList returns [List varDecls]
@init{ $varDecls = new ArrayList(); }
:
    ( decl=variableDeclaration {$varDecls.add($decl.n);}
      (COMMA decl=variableDeclaration {$varDecls.add($decl.n);} )*
    )?
    ;


/* ------------------------------------
instructionList ::= ( instruction ";" )*
*/
instructionList returns [List instructions]
@init{ $instructions = new ArrayList(); }
:
    ( instr=instruction SEMI {$instructions.add($instr.instr);} )*
    ;


/* ------------------------------------
instruction ::= variableAssignment
                | attributeAssignment
                | loop
                | atomicInstruction
                | ifThenElse
*/
instruction returns [ASTGInstruction instr]
:
      instrVA = variableAssignment  {$instr = $instrVA.assignment;}
    | instrAA = attributeAssignment {$instr = $instrAA.assignment;}
    | instrLO = loop				{$instr = $instrLO.loop;}
    | instrAI = atomicInstruction	{$instr = $instrAI.instr;}
    | instrIT = ifThenElse			{$instr = $instrIT.ifThenElse;}
    ;


/* ------------------------------------
variableAssignment ::= id ":=" valueInstruction
*/
variableAssignment returns [ASTGVariableAssignment assignment]
:
    target=IDENT COLON_EQUAL source=valueInstruction
    { $assignment = new ASTGVariableAssignment( $target, $source.valueinstr ); }
    ;


/* ------------------------------------
attributeAssignment ::= oclExpression DOT id ":=" valueInstruction
*/
attributeAssignment returns [ASTGAttributeAssignment assignment]
:
    targetObject=oclExpression DOT attributeName=IDENT
      COLON_EQUAL source=valueInstruction
    { $assignment = new ASTGAttributeAssignment(
			 $targetObject.encapOcl, $attributeName, $source.valueinstr ); }
    ;


/* ------------------------------------
loop ::= "for" variableDeclaration "in" oclExpression
	 "begin" instructionList "end" */
loop returns [ASTGLoop loop]
:
    t='for' decl=variableDeclaration 'in' sequence=oclExpression 'begin'
       instructions=instructionList 'end'
    { $loop = new ASTGLoop( $decl.n, $sequence.encapOcl, $instructions.instructions, $t ); }
    ;


/* ------------------------------------
ifThenElse ::= "if" oclExpression
     "then" "begin" instructionList "end"
	 ("else" "begin" instructionList "end")?
     ";"  */
ifThenElse returns [ASTGIfThenElse ifThenElse]
@init{ List elseInstructionsList = new ArrayList(); }
:
    token='if' sequence=oclExpression 
        'then' 'begin' thenInstructions=instructionList 'end'
        ('else' 'begin' elseInstructions=instructionList 'end' { elseInstructionsList=$elseInstructions.instructions; })?
    { $ifThenElse = new ASTGIfThenElse( $sequence.encapOcl, $thenInstructions.instructions,
                elseInstructionsList, $token ); }
    ;


/* ------------------------------------
valueInstruction ::= atomicInstruction | oclExpression
*/
valueInstruction returns [ASTGValueInstruction valueinstr]
:
      atmoicInstr = atomicInstruction	{$valueinstr = $atmoicInstr.instr; }
    | oclExpr = oclExpression			{$valueinstr = $oclExpr.encapOcl; }
    ;


/* ------------------------------------
atomicInstruction ::=
    id "(" ( instructionParameter (","instructionParameter)* )? ")"
*/
atomicInstruction returns [ASTGAtomicInstruction instr]
:
    name=IDENT { $instr = new ASTGAtomicInstruction($name); } LPAREN
        ( parameter=instructionParameter { $instr.addParameter($parameter.parameter); }
            ( COMMA parameter=instructionParameter
              { $instr.addParameter($parameter.parameter); }
            )*
        )?
    RPAREN
    ;


/* ------------------------------------
instructionParameter ::= oclExpression | instrParameterIdent
*/
instructionParameter returns [Object parameter]
:
      parameterOcl=oclExpression {$parameter = $parameterOcl.encapOcl; }
    | parameterIdent=instrParameterIdent {$parameter = $parameterIdent.t; }
    ;


/* ------------------------------------
instructionParameter ::= oclExpression | instrParameterIdent
*/
instrParameterIdent returns [Token t]
:
    i=IDENT { $t = $i; }
    ;


/* ------------------------------------
oclExpression ::= "[" expression "]" 
*/
oclExpression returns [ASTGocl encapOcl]
:
    i=LBRACK ocl=expression RBRACK
    { $encapOcl = new ASTGocl($ocl.n, $i); }
    ;


// ++++++++++++++++++++++++++++++++++++++++++
// Grammar for calling a procedure

/*
procedureCallOnly ::= id "(" 
			     ( expression ( "," expression )* )?
		         ")"
*/
procedureCallOnly returns [ASTGProcedureCall call]
:
    name=IDENT {$call = new ASTGProcedureCall($name);}
    LPAREN ( 
	   ocl=expression {$call.addParameter($ocl.n);}
	   ( COMMA ocl=expression {$call.addParameter($ocl.n);} )*
    )? RPAREN
    EOF
    ;