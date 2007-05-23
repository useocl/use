header { 
package org.tzi.use.parser.generator;
}

{
import org.tzi.use.parser.*;
import org.tzi.use.parser.ocl.*;
import org.tzi.use.parser.use.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
}
class GGeneratorParser extends GUSEParser;
options {
    exportVocab = GGenerator;
    
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
{ invariantList = new ArrayList(); ASTConstraintDefinition def; } 
:
    ( def=invariant { invariantList.add(def); } )*
    EOF
    ;


// ++++++++++++++++++++++++++++++++++++++++++
// grammar for compiling generator procedures

/*
procedureListOnly ::= (procedure)*

procedure ::= "procedure" id "(" variableDeclarationList ")"
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
{ procedureList = new ArrayList(); }
:
    ( { ASTGProcedure proc; }
      proc=procedure { procedureList.add(proc); }
    )*
    EOF
    ;


/* ------------------------------------
procedure ::= "procedure" id "(" variableDeclarationList ")"
              ("var" variableDeclarationList ";")?
              "begin" instructionList "end" ";"
*/
procedure returns [ASTGProcedure proc]
{ List parameterDecls; List localDecls; List instructions; 
  localDecls = new ArrayList(); proc = null; }
:
    "procedure" name:IDENT LPAREN parameterDecls=variableDeclarationList RPAREN
    ( "var" localDecls=variableDeclarationList SEMI )?
    "begin" instructions=instructionList "end" SEMI
    { proc = new ASTGProcedure(
               (MyToken) name, parameterDecls, localDecls, instructions ); }
    ;


/* ------------------------------------
variableDeclarationList ::= (variableDeclaration ("," variableDeclaration)* )?
*/
variableDeclarationList returns [List varDecls]
{ ASTVariableDeclaration decl; varDecls = new ArrayList(); }
:
    ( decl=variableDeclaration {varDecls.add(decl);}
      (COMMA decl=variableDeclaration {varDecls.add(decl);} )*
    )?
    ;


/* ------------------------------------
instructionList ::= ( instruction ";" )*
*/
instructionList returns [List instructions]
{ ASTGInstruction instr; instructions = new ArrayList(); }
:
    ( instr=instruction SEMI {instructions.add(instr);} )*
    ;


/* ------------------------------------
instruction ::= variableAssignment
                | attributeAssignment
                | loop
                | atomicInstruction
                | ifThenElse
*/
instruction returns [ASTGInstruction instr]
{ instr=null; }
:
    instr=variableAssignment
    | instr=attributeAssignment
    | instr=loop
    | instr=atomicInstruction
    | instr=ifThenElse
    ;


/* ------------------------------------
variableAssignment ::= id ":=" valueInstruction
*/
variableAssignment returns [ASTGVariableAssignment assignment]
{ ASTGValueInstruction source; assignment=null; }
:
    target:IDENT COLON_EQUAL source=valueInstruction
    { assignment = new ASTGVariableAssignment( (MyToken) target, source ); }
    ;


/* ------------------------------------
attributeAssignment ::= oclExpression DOT id ":=" valueInstruction
*/
attributeAssignment returns [ASTGAttributeAssignment assignment]
{ ASTGValueInstruction source; ASTGocl targetObject;
  assignment=null; }
:
    targetObject=oclExpression DOT attributeName:IDENT
      COLON_EQUAL source=valueInstruction
    { assignment = new ASTGAttributeAssignment(
			 targetObject, (MyToken) attributeName, source ); }
    ;


/* ------------------------------------
loop ::= "for" variableDeclaration "in" oclExpression
	 "begin" instructionList "end" */
loop returns [ASTGLoop loop]
{ ASTVariableDeclaration decl; ASTGocl sequence; List instructions;
  loop=null; }
:
    t:"for" decl=variableDeclaration "in" sequence=oclExpression "begin"
       instructions=instructionList "end"
    { loop= new ASTGLoop( decl, sequence, instructions, (MyToken)t ); }
    ;


/* ------------------------------------
ifThenElse ::= "if" oclExpression
     "then" "begin" instructionList "end"
	 ("else" "begin" instructionList "end")?
     ";"  */
ifThenElse returns [ASTGIfThenElse ifThenElse]
{ ASTGocl sequence; List thenInstructions; List elseInstructions;
  elseInstructions = new ArrayList(); ifThenElse=null; }
:
    token:"if" sequence=oclExpression 
        "then" "begin" thenInstructions=instructionList "end"
        ("else" "begin" elseInstructions=instructionList "end")?
    { ifThenElse= new ASTGIfThenElse( sequence, thenInstructions,
                elseInstructions, (MyToken)token ); }
    ;


/* ------------------------------------
valueInstruction ::= atomicInstruction | oclExpression
*/
valueInstruction returns [ASTGValueInstruction valueinstr]
{ valueinstr = null; }
:
    valueinstr=atomicInstruction
    | valueinstr=oclExpression
    ;


/* ------------------------------------
atomicInstruction ::=
    id "(" ( instructionParameter (","instructionParameter)* )? ")"
*/
atomicInstruction returns [ASTGAtomicInstruction instr]
{ instr=null; ASTGInstructionParameterInterface parameter;}
:
    name:IDENT { instr= new ASTGAtomicInstruction((MyToken) name); } LPAREN
        ( parameter=instructionParameter { instr.addParameter(parameter); }
            ( COMMA parameter=instructionParameter
              { instr.addParameter(parameter); }
            )*
        )?
    RPAREN
    ;


/* ------------------------------------
instructionParameter ::= oclExpression | instrParameterIdent
*/
instructionParameter returns [ASTGInstructionParameterInterface parameter]
{ parameter=null; }
:
    parameter=oclExpression
    | parameter=instrParameterIdent
    ;


/* ------------------------------------
instructionParameter ::= oclExpression | instrParameterIdent
*/
instrParameterIdent returns [MyToken t]
{ t=null; }
:
    i:IDENT { t = (MyToken) i; }
    ;


/* ------------------------------------
oclExpression ::= "[" expression "]" 
*/
oclExpression returns [ASTGocl encapOcl]
{ ASTExpression ocl; encapOcl=null; }
:
    i:LBRACK ocl=expression RBRACK
    { encapOcl = new ASTGocl(ocl, (MyToken)i); }
    ;


// ++++++++++++++++++++++++++++++++++++++++++
// Grammar for calling a procedure

/*
procedureCallOnly ::= id "(" 
			     ( expression ( "," expression )* )?
		         ")"
*/
procedureCallOnly returns [ASTGProcedureCall call]
{ call = null; ASTExpression ocl; }
:
    name:IDENT {call = new ASTGProcedureCall((MyToken)name);}
    LPAREN ( 
	   ocl=expression {call.addParameter(ocl);}
	   ( COMMA ocl=expression {call.addParameter(ocl);} )*
    )? RPAREN
    EOF
    ;

    
{
	import org.tzi.use.parser.MyToken;
	import org.tzi.use.parser.ParseErrorHandler;
	import java.io.PrintWriter;
	import org.tzi.use.util.Log;
	import org.tzi.use.util.StringUtil;
}
class GGeneratorLexer extends GUSELexer;
options {
    importVocab = GGenerator;
}

protected
VOCAB:	
    '\3'..'\377'
    ;

