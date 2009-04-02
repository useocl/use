// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 Generator.g 2009-04-02 13:53:47
 
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

package org.tzi.use.parser.generator;

import org.tzi.use.parser.base.BaseParser;
import org.tzi.use.parser.use.*;
import org.tzi.use.parser.ocl.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class GeneratorParser extends BaseParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "IDENT", "LPAREN", "RPAREN", "SEMI", "COMMA", "COLON_EQUAL", "DOT", "LBRACK", "RBRACK", "LBRACE", "RBRACE", "LESS", "COLON", "EQUAL", "DOTDOT", "INT", "STAR", "COLON_COLON", "NOT_EQUAL", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "PLUS", "MINUS", "SLASH", "ARROW", "AT", "BAR", "REAL", "STRING", "HASH", "NEWLINE", "WS", "SL_COMMENT", "ML_COMMENT", "RANGE_OR_INT", "ESC", "HEX_DIGIT", "VOCAB", "'procedure'", "'var'", "'begin'", "'end'", "'for'", "'in'", "'if'", "'then'", "'else'", "'model'", "'constraints'", "'enum'", "'abstract'", "'class'", "'attributes'", "'operations'", "'associationClass'", "'associationclass'", "'between'", "'aggregation'", "'composition'", "'association'", "'role'", "'ordered'", "'context'", "'inv'", "'pre'", "'post'", "'declare'", "'set'", "'create'", "'namehint'", "'insert'", "'into'", "'delete'", "'from'", "'destroy'", "'endif'", "'while'", "'do'", "'wend'", "'execute'", "'let'", "'implies'", "'or'", "'xor'", "'and'", "'div'", "'not'", "'allInstances'", "'iterate'", "'oclAsType'", "'oclIsKindOf'", "'oclIsTypeOf'", "'true'", "'false'", "'Set'", "'Sequence'", "'Bag'", "'oclEmpty'", "'oclUndefined'", "'Undefined'", "'null'", "'Tuple'", "'Collection'"
    };
    public static final int STAR=20;
    public static final int EOF=-1;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__91=91;
    public static final int RPAREN=6;
    public static final int T__92=92;
    public static final int GREATER=23;
    public static final int T__90=90;
    public static final int NOT_EQUAL=22;
    public static final int LESS=15;
    public static final int T__99=99;
    public static final int T__98=98;
    public static final int T__97=97;
    public static final int T__96=96;
    public static final int T__95=95;
    public static final int RBRACK=12;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int RBRACE=14;
    public static final int T__83=83;
    public static final int INT=19;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int REAL=32;
    public static final int WS=36;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__70=70;
    public static final int SL_COMMENT=37;
    public static final int LESS_EQUAL=24;
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int T__74=74;
    public static final int T__73=73;
    public static final int T__79=79;
    public static final int T__78=78;
    public static final int T__77=77;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__64=64;
    public static final int LBRACK=11;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int ESC=40;
    public static final int LBRACE=13;
    public static final int DOTDOT=18;
    public static final int T__61=61;
    public static final int T__60=60;
    public static final int LPAREN=5;
    public static final int AT=30;
    public static final int T__55=55;
    public static final int ML_COMMENT=38;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int SLASH=28;
    public static final int T__51=51;
    public static final int COLON_EQUAL=9;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__107=107;
    public static final int COMMA=8;
    public static final int T__59=59;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int EQUAL=17;
    public static final int T__105=105;
    public static final int T__106=106;
    public static final int IDENT=4;
    public static final int PLUS=26;
    public static final int RANGE_OR_INT=39;
    public static final int DOT=10;
    public static final int T__50=50;
    public static final int T__43=43;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int HASH=34;
    public static final int HEX_DIGIT=41;
    public static final int T__102=102;
    public static final int COLON_COLON=21;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int MINUS=27;
    public static final int SEMI=7;
    public static final int COLON=16;
    public static final int NEWLINE=35;
    public static final int VOCAB=42;
    public static final int ARROW=29;
    public static final int GREATER_EQUAL=25;
    public static final int BAR=31;
    public static final int STRING=33;

    // delegates
    // delegators


        public GeneratorParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public GeneratorParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return GeneratorParser.tokenNames; }
    public String getGrammarFileName() { return "Generator.g"; }



    // $ANTLR start "invariantListOnly"
    // Generator.g:67:1: invariantListOnly returns [List invariantList] : (def= invariant )* EOF ;
    public final List invariantListOnly() throws RecognitionException {
        List invariantList = null;

        ASTConstraintDefinition def = null;


         invariantList = new ArrayList(); 
        try {
            // Generator.g:69:1: ( (def= invariant )* EOF )
            // Generator.g:70:5: (def= invariant )* EOF
            {
            // Generator.g:70:5: (def= invariant )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==67) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Generator.g:70:7: def= invariant
            	    {
            	    pushFollow(FOLLOW_invariant_in_invariantListOnly79);
            	    def=invariant();

            	    state._fsp--;

            	     invariantList.add(def); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            match(input,EOF,FOLLOW_EOF_in_invariantListOnly90); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return invariantList;
    }
    // $ANTLR end "invariantListOnly"


    // $ANTLR start "procedureListOnly"
    // Generator.g:119:1: procedureListOnly returns [List procedureList] : (proc= procedure )* EOF ;
    public final List procedureListOnly() throws RecognitionException {
        List procedureList = null;

        ASTGProcedure proc = null;


         procedureList = new ArrayList(); 
        try {
            // Generator.g:121:1: ( (proc= procedure )* EOF )
            // Generator.g:122:5: (proc= procedure )* EOF
            {
            // Generator.g:122:5: (proc= procedure )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==43) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // Generator.g:123:7: proc= procedure
            	    {
            	    pushFollow(FOLLOW_procedure_in_procedureListOnly135);
            	    proc=procedure();

            	    state._fsp--;

            	     procedureList.add(proc); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            match(input,EOF,FOLLOW_EOF_in_procedureListOnly150); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return procedureList;
    }
    // $ANTLR end "procedureListOnly"


    // $ANTLR start "procedure"
    // Generator.g:134:1: procedure returns [ASTGProcedure proc] : 'procedure' name= IDENT LPAREN parameterDecls= variableDeclarationList RPAREN ( 'var' localDecls= variableDeclarationList SEMI )? 'begin' instructions= instructionList 'end' SEMI ;
    public final ASTGProcedure procedure() throws RecognitionException {
        ASTGProcedure proc = null;

        Token name=null;
        List parameterDecls = null;

        List localDecls = null;

        List instructions = null;


         localDecls = new ArrayList(); 
        try {
            // Generator.g:136:1: ( 'procedure' name= IDENT LPAREN parameterDecls= variableDeclarationList RPAREN ( 'var' localDecls= variableDeclarationList SEMI )? 'begin' instructions= instructionList 'end' SEMI )
            // Generator.g:137:5: 'procedure' name= IDENT LPAREN parameterDecls= variableDeclarationList RPAREN ( 'var' localDecls= variableDeclarationList SEMI )? 'begin' instructions= instructionList 'end' SEMI
            {
            match(input,43,FOLLOW_43_in_procedure178); 
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_procedure182); 
            match(input,LPAREN,FOLLOW_LPAREN_in_procedure184); 
            pushFollow(FOLLOW_variableDeclarationList_in_procedure188);
            parameterDecls=variableDeclarationList();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_procedure190); 
            // Generator.g:138:5: ( 'var' localDecls= variableDeclarationList SEMI )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==44) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // Generator.g:138:7: 'var' localDecls= variableDeclarationList SEMI
                    {
                    match(input,44,FOLLOW_44_in_procedure198); 
                    pushFollow(FOLLOW_variableDeclarationList_in_procedure202);
                    localDecls=variableDeclarationList();

                    state._fsp--;

                    match(input,SEMI,FOLLOW_SEMI_in_procedure204); 

                    }
                    break;

            }

            match(input,45,FOLLOW_45_in_procedure213); 
            pushFollow(FOLLOW_instructionList_in_procedure217);
            instructions=instructionList();

            state._fsp--;

            match(input,46,FOLLOW_46_in_procedure219); 
            match(input,SEMI,FOLLOW_SEMI_in_procedure221); 
             proc = new ASTGProcedure(name, parameterDecls, localDecls, instructions ); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return proc;
    }
    // $ANTLR end "procedure"


    // $ANTLR start "variableDeclarationList"
    // Generator.g:147:1: variableDeclarationList returns [List varDecls] : (decl= variableDeclaration ( COMMA decl= variableDeclaration )* )? ;
    public final List variableDeclarationList() throws RecognitionException {
        List varDecls = null;

        ASTVariableDeclaration decl = null;


         varDecls = new ArrayList(); 
        try {
            // Generator.g:149:1: ( (decl= variableDeclaration ( COMMA decl= variableDeclaration )* )? )
            // Generator.g:150:5: (decl= variableDeclaration ( COMMA decl= variableDeclaration )* )?
            {
            // Generator.g:150:5: (decl= variableDeclaration ( COMMA decl= variableDeclaration )* )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==IDENT) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // Generator.g:150:7: decl= variableDeclaration ( COMMA decl= variableDeclaration )*
                    {
                    pushFollow(FOLLOW_variableDeclaration_in_variableDeclarationList259);
                    decl=variableDeclaration();

                    state._fsp--;

                    varDecls.add(decl);
                    // Generator.g:151:7: ( COMMA decl= variableDeclaration )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0==COMMA) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // Generator.g:151:8: COMMA decl= variableDeclaration
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_variableDeclarationList270); 
                    	    pushFollow(FOLLOW_variableDeclaration_in_variableDeclarationList274);
                    	    decl=variableDeclaration();

                    	    state._fsp--;

                    	    varDecls.add(decl);

                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return varDecls;
    }
    // $ANTLR end "variableDeclarationList"


    // $ANTLR start "instructionList"
    // Generator.g:159:1: instructionList returns [List instructions] : (instr= instruction SEMI )* ;
    public final List instructionList() throws RecognitionException {
        List instructions = null;

        ASTGInstruction instr = null;


         instructions = new ArrayList(); 
        try {
            // Generator.g:161:1: ( (instr= instruction SEMI )* )
            // Generator.g:162:5: (instr= instruction SEMI )*
            {
            // Generator.g:162:5: (instr= instruction SEMI )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==IDENT||LA6_0==LBRACK||LA6_0==47||LA6_0==49) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // Generator.g:162:7: instr= instruction SEMI
            	    {
            	    pushFollow(FOLLOW_instruction_in_instructionList318);
            	    instr=instruction();

            	    state._fsp--;

            	    match(input,SEMI,FOLLOW_SEMI_in_instructionList320); 
            	    instructions.add(instr);

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return instructions;
    }
    // $ANTLR end "instructionList"


    // $ANTLR start "instruction"
    // Generator.g:173:1: instruction returns [ASTGInstruction instr] : (instrVA= variableAssignment | instrAA= attributeAssignment | instrLO= loop | instrAI= atomicInstruction | instrIT= ifThenElse );
    public final ASTGInstruction instruction() throws RecognitionException {
        ASTGInstruction instr = null;

        ASTGVariableAssignment instrVA = null;

        ASTGAttributeAssignment instrAA = null;

        ASTGLoop instrLO = null;

        ASTGAtomicInstruction instrAI = null;

        ASTGIfThenElse instrIT = null;


        try {
            // Generator.g:174:1: (instrVA= variableAssignment | instrAA= attributeAssignment | instrLO= loop | instrAI= atomicInstruction | instrIT= ifThenElse )
            int alt7=5;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA7_1 = input.LA(2);

                if ( (LA7_1==COLON_EQUAL) ) {
                    alt7=1;
                }
                else if ( (LA7_1==LPAREN) ) {
                    alt7=4;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 1, input);

                    throw nvae;
                }
                }
                break;
            case LBRACK:
                {
                alt7=2;
                }
                break;
            case 47:
                {
                alt7=3;
                }
                break;
            case 49:
                {
                alt7=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // Generator.g:175:7: instrVA= variableAssignment
                    {
                    pushFollow(FOLLOW_variableAssignment_in_instruction355);
                    instrVA=variableAssignment();

                    state._fsp--;

                    instr = instrVA;

                    }
                    break;
                case 2 :
                    // Generator.g:176:7: instrAA= attributeAssignment
                    {
                    pushFollow(FOLLOW_attributeAssignment_in_instruction370);
                    instrAA=attributeAssignment();

                    state._fsp--;

                    instr = instrAA;

                    }
                    break;
                case 3 :
                    // Generator.g:177:7: instrLO= loop
                    {
                    pushFollow(FOLLOW_loop_in_instruction384);
                    instrLO=loop();

                    state._fsp--;

                    instr = instrLO;

                    }
                    break;
                case 4 :
                    // Generator.g:178:7: instrAI= atomicInstruction
                    {
                    pushFollow(FOLLOW_atomicInstruction_in_instruction401);
                    instrAI=atomicInstruction();

                    state._fsp--;

                    instr = instrAI;

                    }
                    break;
                case 5 :
                    // Generator.g:179:7: instrIT= ifThenElse
                    {
                    pushFollow(FOLLOW_ifThenElse_in_instruction415);
                    instrIT=ifThenElse();

                    state._fsp--;

                    instr = instrIT;

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return instr;
    }
    // $ANTLR end "instruction"


    // $ANTLR start "variableAssignment"
    // Generator.g:186:1: variableAssignment returns [ASTGVariableAssignment assignment] : target= IDENT COLON_EQUAL source= valueInstruction ;
    public final ASTGVariableAssignment variableAssignment() throws RecognitionException {
        ASTGVariableAssignment assignment = null;

        Token target=null;
        ASTGValueInstruction source = null;


        try {
            // Generator.g:187:1: (target= IDENT COLON_EQUAL source= valueInstruction )
            // Generator.g:188:5: target= IDENT COLON_EQUAL source= valueInstruction
            {
            target=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableAssignment445); 
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_variableAssignment447); 
            pushFollow(FOLLOW_valueInstruction_in_variableAssignment451);
            source=valueInstruction();

            state._fsp--;

             assignment = new ASTGVariableAssignment( target, source ); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return assignment;
    }
    // $ANTLR end "variableAssignment"


    // $ANTLR start "attributeAssignment"
    // Generator.g:196:1: attributeAssignment returns [ASTGAttributeAssignment assignment] : targetObject= oclExpression DOT attributeName= IDENT COLON_EQUAL source= valueInstruction ;
    public final ASTGAttributeAssignment attributeAssignment() throws RecognitionException {
        ASTGAttributeAssignment assignment = null;

        Token attributeName=null;
        ASTGocl targetObject = null;

        ASTGValueInstruction source = null;


        try {
            // Generator.g:197:1: (targetObject= oclExpression DOT attributeName= IDENT COLON_EQUAL source= valueInstruction )
            // Generator.g:198:5: targetObject= oclExpression DOT attributeName= IDENT COLON_EQUAL source= valueInstruction
            {
            pushFollow(FOLLOW_oclExpression_in_attributeAssignment483);
            targetObject=oclExpression();

            state._fsp--;

            match(input,DOT,FOLLOW_DOT_in_attributeAssignment485); 
            attributeName=(Token)match(input,IDENT,FOLLOW_IDENT_in_attributeAssignment489); 
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_attributeAssignment497); 
            pushFollow(FOLLOW_valueInstruction_in_attributeAssignment501);
            source=valueInstruction();

            state._fsp--;

             assignment = new ASTGAttributeAssignment(
            			 targetObject, attributeName, source ); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return assignment;
    }
    // $ANTLR end "attributeAssignment"


    // $ANTLR start "loop"
    // Generator.g:208:1: loop returns [ASTGLoop loop] : t= 'for' decl= variableDeclaration 'in' sequence= oclExpression 'begin' instructions= instructionList 'end' ;
    public final ASTGLoop loop() throws RecognitionException {
        ASTGLoop loop = null;

        Token t=null;
        ASTVariableDeclaration decl = null;

        ASTGocl sequence = null;

        List instructions = null;


        try {
            // Generator.g:209:1: (t= 'for' decl= variableDeclaration 'in' sequence= oclExpression 'begin' instructions= instructionList 'end' )
            // Generator.g:210:5: t= 'for' decl= variableDeclaration 'in' sequence= oclExpression 'begin' instructions= instructionList 'end'
            {
            t=(Token)match(input,47,FOLLOW_47_in_loop533); 
            pushFollow(FOLLOW_variableDeclaration_in_loop537);
            decl=variableDeclaration();

            state._fsp--;

            match(input,48,FOLLOW_48_in_loop539); 
            pushFollow(FOLLOW_oclExpression_in_loop543);
            sequence=oclExpression();

            state._fsp--;

            match(input,45,FOLLOW_45_in_loop545); 
            pushFollow(FOLLOW_instructionList_in_loop556);
            instructions=instructionList();

            state._fsp--;

            match(input,46,FOLLOW_46_in_loop558); 
             loop = new ASTGLoop( decl, sequence, instructions, t ); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return loop;
    }
    // $ANTLR end "loop"


    // $ANTLR start "ifThenElse"
    // Generator.g:221:1: ifThenElse returns [ASTGIfThenElse ifThenElse] : token= 'if' sequence= oclExpression 'then' 'begin' thenInstructions= instructionList 'end' ( 'else' 'begin' elseInstructions= instructionList 'end' )? ;
    public final ASTGIfThenElse ifThenElse() throws RecognitionException {
        ASTGIfThenElse ifThenElse = null;

        Token token=null;
        ASTGocl sequence = null;

        List thenInstructions = null;

        List elseInstructions = null;


         List elseInstructionsList = new ArrayList(); 
        try {
            // Generator.g:223:1: (token= 'if' sequence= oclExpression 'then' 'begin' thenInstructions= instructionList 'end' ( 'else' 'begin' elseInstructions= instructionList 'end' )? )
            // Generator.g:224:5: token= 'if' sequence= oclExpression 'then' 'begin' thenInstructions= instructionList 'end' ( 'else' 'begin' elseInstructions= instructionList 'end' )?
            {
            token=(Token)match(input,49,FOLLOW_49_in_ifThenElse594); 
            pushFollow(FOLLOW_oclExpression_in_ifThenElse598);
            sequence=oclExpression();

            state._fsp--;

            match(input,50,FOLLOW_50_in_ifThenElse609); 
            match(input,45,FOLLOW_45_in_ifThenElse611); 
            pushFollow(FOLLOW_instructionList_in_ifThenElse615);
            thenInstructions=instructionList();

            state._fsp--;

            match(input,46,FOLLOW_46_in_ifThenElse617); 
            // Generator.g:226:9: ( 'else' 'begin' elseInstructions= instructionList 'end' )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==51) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // Generator.g:226:10: 'else' 'begin' elseInstructions= instructionList 'end'
                    {
                    match(input,51,FOLLOW_51_in_ifThenElse628); 
                    match(input,45,FOLLOW_45_in_ifThenElse630); 
                    pushFollow(FOLLOW_instructionList_in_ifThenElse634);
                    elseInstructions=instructionList();

                    state._fsp--;

                    match(input,46,FOLLOW_46_in_ifThenElse636); 
                     elseInstructionsList=elseInstructions; 

                    }
                    break;

            }

             ifThenElse = new ASTGIfThenElse( sequence, thenInstructions,
                            elseInstructionsList, token ); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ifThenElse;
    }
    // $ANTLR end "ifThenElse"


    // $ANTLR start "valueInstruction"
    // Generator.g:235:1: valueInstruction returns [ASTGValueInstruction valueinstr] : (atmoicInstr= atomicInstruction | oclExpr= oclExpression );
    public final ASTGValueInstruction valueInstruction() throws RecognitionException {
        ASTGValueInstruction valueinstr = null;

        ASTGAtomicInstruction atmoicInstr = null;

        ASTGocl oclExpr = null;


        try {
            // Generator.g:236:1: (atmoicInstr= atomicInstruction | oclExpr= oclExpression )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==IDENT) ) {
                alt9=1;
            }
            else if ( (LA9_0==LBRACK) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // Generator.g:237:7: atmoicInstr= atomicInstruction
                    {
                    pushFollow(FOLLOW_atomicInstruction_in_valueInstruction676);
                    atmoicInstr=atomicInstruction();

                    state._fsp--;

                    valueinstr = atmoicInstr; 

                    }
                    break;
                case 2 :
                    // Generator.g:238:7: oclExpr= oclExpression
                    {
                    pushFollow(FOLLOW_oclExpression_in_valueInstruction690);
                    oclExpr=oclExpression();

                    state._fsp--;

                    valueinstr = oclExpr; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return valueinstr;
    }
    // $ANTLR end "valueInstruction"


    // $ANTLR start "atomicInstruction"
    // Generator.g:246:1: atomicInstruction returns [ASTGAtomicInstruction instr] : name= IDENT LPAREN (parameter= instructionParameter ( COMMA parameter= instructionParameter )* )? RPAREN ;
    public final ASTGAtomicInstruction atomicInstruction() throws RecognitionException {
        ASTGAtomicInstruction instr = null;

        Token name=null;
        Object parameter = null;


        try {
            // Generator.g:247:1: (name= IDENT LPAREN (parameter= instructionParameter ( COMMA parameter= instructionParameter )* )? RPAREN )
            // Generator.g:248:5: name= IDENT LPAREN (parameter= instructionParameter ( COMMA parameter= instructionParameter )* )? RPAREN
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_atomicInstruction720); 
             instr = new ASTGAtomicInstruction(name); 
            match(input,LPAREN,FOLLOW_LPAREN_in_atomicInstruction724); 
            // Generator.g:249:9: (parameter= instructionParameter ( COMMA parameter= instructionParameter )* )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==IDENT||LA11_0==LBRACK) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // Generator.g:249:11: parameter= instructionParameter ( COMMA parameter= instructionParameter )*
                    {
                    pushFollow(FOLLOW_instructionParameter_in_atomicInstruction738);
                    parameter=instructionParameter();

                    state._fsp--;

                     instr.addParameter(parameter); 
                    // Generator.g:250:13: ( COMMA parameter= instructionParameter )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0==COMMA) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // Generator.g:250:15: COMMA parameter= instructionParameter
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_atomicInstruction756); 
                    	    pushFollow(FOLLOW_instructionParameter_in_atomicInstruction760);
                    	    parameter=instructionParameter();

                    	    state._fsp--;

                    	     instr.addParameter(parameter); 

                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_atomicInstruction808); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return instr;
    }
    // $ANTLR end "atomicInstruction"


    // $ANTLR start "instructionParameter"
    // Generator.g:261:1: instructionParameter returns [Object parameter] : (parameterOcl= oclExpression | parameterIdent= instrParameterIdent );
    public final Object instructionParameter() throws RecognitionException {
        Object parameter = null;

        ASTGocl parameterOcl = null;

        Token parameterIdent = null;


        try {
            // Generator.g:262:1: (parameterOcl= oclExpression | parameterIdent= instrParameterIdent )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==LBRACK) ) {
                alt12=1;
            }
            else if ( (LA12_0==IDENT) ) {
                alt12=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // Generator.g:263:7: parameterOcl= oclExpression
                    {
                    pushFollow(FOLLOW_oclExpression_in_instructionParameter836);
                    parameterOcl=oclExpression();

                    state._fsp--;

                    parameter = parameterOcl; 

                    }
                    break;
                case 2 :
                    // Generator.g:264:7: parameterIdent= instrParameterIdent
                    {
                    pushFollow(FOLLOW_instrParameterIdent_in_instructionParameter848);
                    parameterIdent=instrParameterIdent();

                    state._fsp--;

                    parameter = parameterIdent; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return parameter;
    }
    // $ANTLR end "instructionParameter"


    // $ANTLR start "instrParameterIdent"
    // Generator.g:271:1: instrParameterIdent returns [Token t] : i= IDENT ;
    public final Token instrParameterIdent() throws RecognitionException {
        Token t = null;

        Token i=null;

        try {
            // Generator.g:272:1: (i= IDENT )
            // Generator.g:273:5: i= IDENT
            {
            i=(Token)match(input,IDENT,FOLLOW_IDENT_in_instrParameterIdent876); 
             t = i; 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return t;
    }
    // $ANTLR end "instrParameterIdent"


    // $ANTLR start "oclExpression"
    // Generator.g:280:1: oclExpression returns [ASTGocl encapOcl] : i= LBRACK ocl= expression RBRACK ;
    public final ASTGocl oclExpression() throws RecognitionException {
        ASTGocl encapOcl = null;

        Token i=null;
        ASTExpression ocl = null;


        try {
            // Generator.g:281:1: (i= LBRACK ocl= expression RBRACK )
            // Generator.g:282:5: i= LBRACK ocl= expression RBRACK
            {
            i=(Token)match(input,LBRACK,FOLLOW_LBRACK_in_oclExpression904); 
            pushFollow(FOLLOW_expression_in_oclExpression908);
            ocl=expression();

            state._fsp--;

            match(input,RBRACK,FOLLOW_RBRACK_in_oclExpression910); 
             encapOcl = new ASTGocl(ocl, i); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return encapOcl;
    }
    // $ANTLR end "oclExpression"


    // $ANTLR start "procedureCallOnly"
    // Generator.g:295:1: procedureCallOnly returns [ASTGProcedureCall call] : name= IDENT LPAREN (ocl= expression ( COMMA ocl= expression )* )? RPAREN EOF ;
    public final ASTGProcedureCall procedureCallOnly() throws RecognitionException {
        ASTGProcedureCall call = null;

        Token name=null;
        ASTExpression ocl = null;


        try {
            // Generator.g:296:1: (name= IDENT LPAREN (ocl= expression ( COMMA ocl= expression )* )? RPAREN EOF )
            // Generator.g:297:5: name= IDENT LPAREN (ocl= expression ( COMMA ocl= expression )* )? RPAREN EOF
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_procedureCallOnly945); 
            call = new ASTGProcedureCall(name);
            match(input,LPAREN,FOLLOW_LPAREN_in_procedureCallOnly953); 
            // Generator.g:298:12: (ocl= expression ( COMMA ocl= expression )* )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0>=IDENT && LA14_0<=LPAREN)||LA14_0==INT||(LA14_0>=PLUS && LA14_0<=MINUS)||(LA14_0>=REAL && LA14_0<=HASH)||LA14_0==49||LA14_0==85||LA14_0==91||(LA14_0>=93 && LA14_0<=106)) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // Generator.g:299:5: ocl= expression ( COMMA ocl= expression )*
                    {
                    pushFollow(FOLLOW_expression_in_procedureCallOnly964);
                    ocl=expression();

                    state._fsp--;

                    call.addParameter(ocl);
                    // Generator.g:300:5: ( COMMA ocl= expression )*
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0==COMMA) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // Generator.g:300:7: COMMA ocl= expression
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_procedureCallOnly974); 
                    	    pushFollow(FOLLOW_expression_in_procedureCallOnly978);
                    	    ocl=expression();

                    	    state._fsp--;

                    	    call.addParameter(ocl);

                    	    }
                    	    break;

                    	default :
                    	    break loop13;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_procedureCallOnly992); 
            match(input,EOF,FOLLOW_EOF_in_procedureCallOnly998); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return call;
    }
    // $ANTLR end "procedureCallOnly"


    // $ANTLR start "model"
    // Generator.g:319:1: model returns [ASTModel n] : 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF ;
    public final ASTModel model() throws RecognitionException {
        ASTModel n = null;

        Token modelName=null;
        ASTEnumTypeDefinition e = null;

        ASTAssociation a = null;

        ASTConstraintDefinition cons = null;

        ASTPrePost ppc = null;


        try {
            // Generator.g:320:1: ( 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF )
            // Generator.g:321:5: 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF
            {
            match(input,52,FOLLOW_52_in_model1025); 
            modelName=(Token)match(input,IDENT,FOLLOW_IDENT_in_model1029); 
             n = new ASTModel(modelName); 
            // Generator.g:322:5: (e= enumTypeDefinition )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==54) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // Generator.g:322:7: e= enumTypeDefinition
            	    {
            	    pushFollow(FOLLOW_enumTypeDefinition_in_model1041);
            	    e=enumTypeDefinition();

            	    state._fsp--;

            	     n.addEnumTypeDef(e); 

            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);

            // Generator.g:323:5: ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )*
            loop17:
            do {
                int alt17=4;
                switch ( input.LA(1) ) {
                case 55:
                case 56:
                case 59:
                case 60:
                    {
                    alt17=1;
                    }
                    break;
                case 62:
                case 63:
                case 64:
                    {
                    alt17=2;
                    }
                    break;
                case 53:
                    {
                    alt17=3;
                    }
                    break;

                }

                switch (alt17) {
            	case 1 :
            	    // Generator.g:323:9: ( generalClassDefinition[$n] )
            	    {
            	    // Generator.g:323:9: ( generalClassDefinition[$n] )
            	    // Generator.g:323:11: generalClassDefinition[$n]
            	    {
            	    pushFollow(FOLLOW_generalClassDefinition_in_model1058);
            	    generalClassDefinition(n);

            	    state._fsp--;


            	    }


            	    }
            	    break;
            	case 2 :
            	    // Generator.g:324:9: (a= associationDefinition )
            	    {
            	    // Generator.g:324:9: (a= associationDefinition )
            	    // Generator.g:324:11: a= associationDefinition
            	    {
            	    pushFollow(FOLLOW_associationDefinition_in_model1075);
            	    a=associationDefinition();

            	    state._fsp--;

            	     n.addAssociation(a); 

            	    }


            	    }
            	    break;
            	case 3 :
            	    // Generator.g:325:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    {
            	    // Generator.g:325:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    // Generator.g:325:11: 'constraints' (cons= invariant | ppc= prePost )*
            	    {
            	    match(input,53,FOLLOW_53_in_model1091); 
            	    // Generator.g:326:11: (cons= invariant | ppc= prePost )*
            	    loop16:
            	    do {
            	        int alt16=3;
            	        int LA16_0 = input.LA(1);

            	        if ( (LA16_0==67) ) {
            	            int LA16_2 = input.LA(2);

            	            if ( (LA16_2==IDENT) ) {
            	                int LA16_3 = input.LA(3);

            	                if ( (LA16_3==COLON_COLON) ) {
            	                    alt16=2;
            	                }
            	                else if ( (LA16_3==EOF||LA16_3==COLON||LA16_3==53||(LA16_3>=55 && LA16_3<=56)||(LA16_3>=59 && LA16_3<=60)||(LA16_3>=62 && LA16_3<=64)||(LA16_3>=67 && LA16_3<=68)) ) {
            	                    alt16=1;
            	                }


            	            }


            	        }


            	        switch (alt16) {
            	    	case 1 :
            	    	    // Generator.g:326:15: cons= invariant
            	    	    {
            	    	    pushFollow(FOLLOW_invariant_in_model1109);
            	    	    cons=invariant();

            	    	    state._fsp--;

            	    	     n.addConstraint(cons); 

            	    	    }
            	    	    break;
            	    	case 2 :
            	    	    // Generator.g:327:15: ppc= prePost
            	    	    {
            	    	    pushFollow(FOLLOW_prePost_in_model1130);
            	    	    ppc=prePost();

            	    	    state._fsp--;

            	    	     n.addPrePost(ppc); 

            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop16;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

            match(input,EOF,FOLLOW_EOF_in_model1171); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "model"


    // $ANTLR start "enumTypeDefinition"
    // Generator.g:338:1: enumTypeDefinition returns [ASTEnumTypeDefinition n] : 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )? ;
    public final ASTEnumTypeDefinition enumTypeDefinition() throws RecognitionException {
        ASTEnumTypeDefinition n = null;

        Token name=null;
        List idListRes = null;


        try {
            // Generator.g:339:1: ( 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )? )
            // Generator.g:340:5: 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )?
            {
            match(input,54,FOLLOW_54_in_enumTypeDefinition1198); 
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_enumTypeDefinition1202); 
            match(input,LBRACE,FOLLOW_LBRACE_in_enumTypeDefinition1204); 
            pushFollow(FOLLOW_idList_in_enumTypeDefinition1208);
            idListRes=idList();

            state._fsp--;

            match(input,RBRACE,FOLLOW_RBRACE_in_enumTypeDefinition1210); 
            // Generator.g:340:54: ( SEMI )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==SEMI) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // Generator.g:340:56: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_enumTypeDefinition1214); 

                    }
                    break;

            }

             n = new ASTEnumTypeDefinition(name, idListRes); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "enumTypeDefinition"


    // $ANTLR start "generalClassDefinition"
    // Generator.g:349:1: generalClassDefinition[ASTModel n] : ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) ) ;
    public final void generalClassDefinition(ASTModel n) throws RecognitionException {
        ASTClass c = null;

        ASTAssociationClass ac = null;


         
          boolean isAbstract = false;

        try {
            // Generator.g:353:1: ( ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) ) )
            // Generator.g:354:5: ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) )
            {
            // Generator.g:354:5: ( 'abstract' )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==55) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // Generator.g:354:7: 'abstract'
                    {
                    match(input,55,FOLLOW_55_in_generalClassDefinition1253); 
                     isAbstract = true; 

                    }
                    break;

            }

            // Generator.g:355:5: ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==56) ) {
                alt20=1;
            }
            else if ( ((LA20_0>=59 && LA20_0<=60)) ) {
                alt20=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // Generator.g:355:7: (c= classDefinition[isAbstract] )
                    {
                    // Generator.g:355:7: (c= classDefinition[isAbstract] )
                    // Generator.g:355:9: c= classDefinition[isAbstract]
                    {
                    pushFollow(FOLLOW_classDefinition_in_generalClassDefinition1271);
                    c=classDefinition(isAbstract);

                    state._fsp--;

                     n.addClass(c); 

                    }


                    }
                    break;
                case 2 :
                    // Generator.g:356:7: (ac= associationClassDefinition[isAbstract] )
                    {
                    // Generator.g:356:7: (ac= associationClassDefinition[isAbstract] )
                    // Generator.g:356:9: ac= associationClassDefinition[isAbstract]
                    {
                    pushFollow(FOLLOW_associationClassDefinition_in_generalClassDefinition1291);
                    ac=associationClassDefinition(isAbstract);

                    state._fsp--;

                     n.addAssociationClass(ac); 

                    }


                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "generalClassDefinition"


    // $ANTLR start "classDefinition"
    // Generator.g:373:1: classDefinition[boolean isAbstract] returns [ASTClass n] : 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end' ;
    public final ASTClass classDefinition(boolean isAbstract) throws RecognitionException {
        ASTClass n = null;

        Token name=null;
        List idListRes = null;

        ASTAttribute a = null;

        ASTOperation op = null;

        ASTInvariantClause inv = null;


         List idList; 
        try {
            // Generator.g:375:1: ( 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end' )
            // Generator.g:376:5: 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end'
            {
            match(input,56,FOLLOW_56_in_classDefinition1330); 
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_classDefinition1334); 
             n = new ASTClass(name, isAbstract); 
            // Generator.g:377:5: ( LESS idListRes= idList )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==LESS) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // Generator.g:377:7: LESS idListRes= idList
                    {
                    match(input,LESS,FOLLOW_LESS_in_classDefinition1344); 
                    pushFollow(FOLLOW_idList_in_classDefinition1348);
                    idListRes=idList();

                    state._fsp--;

                     n.addSuperClasses(idListRes); 

                    }
                    break;

            }

            // Generator.g:378:5: ( 'attributes' (a= attributeDefinition )* )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==57) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // Generator.g:378:7: 'attributes' (a= attributeDefinition )*
                    {
                    match(input,57,FOLLOW_57_in_classDefinition1361); 
                    // Generator.g:379:7: (a= attributeDefinition )*
                    loop22:
                    do {
                        int alt22=2;
                        int LA22_0 = input.LA(1);

                        if ( (LA22_0==IDENT) ) {
                            alt22=1;
                        }


                        switch (alt22) {
                    	case 1 :
                    	    // Generator.g:379:9: a= attributeDefinition
                    	    {
                    	    pushFollow(FOLLOW_attributeDefinition_in_classDefinition1374);
                    	    a=attributeDefinition();

                    	    state._fsp--;

                    	     n.addAttribute(a); 

                    	    }
                    	    break;

                    	default :
                    	    break loop22;
                        }
                    } while (true);


                    }
                    break;

            }

            // Generator.g:381:5: ( 'operations' (op= operationDefinition )* )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==58) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // Generator.g:381:7: 'operations' (op= operationDefinition )*
                    {
                    match(input,58,FOLLOW_58_in_classDefinition1395); 
                    // Generator.g:382:7: (op= operationDefinition )*
                    loop24:
                    do {
                        int alt24=2;
                        int LA24_0 = input.LA(1);

                        if ( (LA24_0==IDENT) ) {
                            alt24=1;
                        }


                        switch (alt24) {
                    	case 1 :
                    	    // Generator.g:382:9: op= operationDefinition
                    	    {
                    	    pushFollow(FOLLOW_operationDefinition_in_classDefinition1408);
                    	    op=operationDefinition();

                    	    state._fsp--;

                    	     n.addOperation(op); 

                    	    }
                    	    break;

                    	default :
                    	    break loop24;
                        }
                    } while (true);


                    }
                    break;

            }

            // Generator.g:384:5: ( 'constraints' (inv= invariantClause )* )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==53) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // Generator.g:384:7: 'constraints' (inv= invariantClause )*
                    {
                    match(input,53,FOLLOW_53_in_classDefinition1429); 
                    // Generator.g:385:7: (inv= invariantClause )*
                    loop26:
                    do {
                        int alt26=2;
                        int LA26_0 = input.LA(1);

                        if ( (LA26_0==68) ) {
                            alt26=1;
                        }


                        switch (alt26) {
                    	case 1 :
                    	    // Generator.g:386:9: inv= invariantClause
                    	    {
                    	    pushFollow(FOLLOW_invariantClause_in_classDefinition1449);
                    	    inv=invariantClause();

                    	    state._fsp--;

                    	     n.addInvariantClause(inv); 

                    	    }
                    	    break;

                    	default :
                    	    break loop26;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,46,FOLLOW_46_in_classDefinition1473); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "classDefinition"


    // $ANTLR start "associationClassDefinition"
    // Generator.g:407:1: associationClassDefinition[boolean isAbstract] returns [ASTAssociationClass n] : classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end' ;
    public final ASTAssociationClass associationClassDefinition(boolean isAbstract) throws RecognitionException {
        ASTAssociationClass n = null;

        Token classKW=null;
        Token name=null;
        List idListRes = null;

        ASTAssociationEnd ae = null;

        ASTAttribute a = null;

        ASTOperation op = null;

        ASTInvariantClause inv = null;


        List idList; Token t = null;
        try {
            // Generator.g:409:1: (classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end' )
            // Generator.g:410:5: classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end'
            {
            classKW=(Token)input.LT(1);
            if ( (input.LA(1)>=59 && input.LA(1)<=60) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

             
                	if ((classKW!=null?classKW.getText():null).equals("associationClass")) {
                           reportWarning("the 'associationClass' keyword is deprecated and will " +
                                         "not be supported in the future, use 'associationclass' instead");
                        }  
                
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationClassDefinition1532); 
             n = new ASTAssociationClass(name, isAbstract); 
            // Generator.g:419:5: ( LESS idListRes= idList )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==LESS) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // Generator.g:419:7: LESS idListRes= idList
                    {
                    match(input,LESS,FOLLOW_LESS_in_associationClassDefinition1542); 
                    pushFollow(FOLLOW_idList_in_associationClassDefinition1546);
                    idListRes=idList();

                    state._fsp--;

                     n.addSuperClasses(idListRes); 

                    }
                    break;

            }

            match(input,61,FOLLOW_61_in_associationClassDefinition1557); 
            pushFollow(FOLLOW_associationEnd_in_associationClassDefinition1565);
            ae=associationEnd();

            state._fsp--;

             n.addEnd(ae); 
            // Generator.g:422:5: (ae= associationEnd )+
            int cnt29=0;
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==IDENT) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // Generator.g:422:7: ae= associationEnd
            	    {
            	    pushFollow(FOLLOW_associationEnd_in_associationClassDefinition1577);
            	    ae=associationEnd();

            	    state._fsp--;

            	     n.addEnd(ae); 

            	    }
            	    break;

            	default :
            	    if ( cnt29 >= 1 ) break loop29;
                        EarlyExitException eee =
                            new EarlyExitException(29, input);
                        throw eee;
                }
                cnt29++;
            } while (true);

            // Generator.g:423:5: ( 'attributes' (a= attributeDefinition )* )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==57) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // Generator.g:423:7: 'attributes' (a= attributeDefinition )*
                    {
                    match(input,57,FOLLOW_57_in_associationClassDefinition1590); 
                    // Generator.g:424:7: (a= attributeDefinition )*
                    loop30:
                    do {
                        int alt30=2;
                        int LA30_0 = input.LA(1);

                        if ( (LA30_0==IDENT) ) {
                            alt30=1;
                        }


                        switch (alt30) {
                    	case 1 :
                    	    // Generator.g:424:9: a= attributeDefinition
                    	    {
                    	    pushFollow(FOLLOW_attributeDefinition_in_associationClassDefinition1603);
                    	    a=attributeDefinition();

                    	    state._fsp--;

                    	     n.addAttribute(a); 

                    	    }
                    	    break;

                    	default :
                    	    break loop30;
                        }
                    } while (true);


                    }
                    break;

            }

            // Generator.g:426:5: ( 'operations' (op= operationDefinition )* )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==58) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // Generator.g:426:7: 'operations' (op= operationDefinition )*
                    {
                    match(input,58,FOLLOW_58_in_associationClassDefinition1624); 
                    // Generator.g:427:7: (op= operationDefinition )*
                    loop32:
                    do {
                        int alt32=2;
                        int LA32_0 = input.LA(1);

                        if ( (LA32_0==IDENT) ) {
                            alt32=1;
                        }


                        switch (alt32) {
                    	case 1 :
                    	    // Generator.g:427:9: op= operationDefinition
                    	    {
                    	    pushFollow(FOLLOW_operationDefinition_in_associationClassDefinition1637);
                    	    op=operationDefinition();

                    	    state._fsp--;

                    	     n.addOperation(op); 

                    	    }
                    	    break;

                    	default :
                    	    break loop32;
                        }
                    } while (true);


                    }
                    break;

            }

            // Generator.g:429:5: ( 'constraints' (inv= invariantClause )* )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==53) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // Generator.g:429:7: 'constraints' (inv= invariantClause )*
                    {
                    match(input,53,FOLLOW_53_in_associationClassDefinition1658); 
                    // Generator.g:430:7: (inv= invariantClause )*
                    loop34:
                    do {
                        int alt34=2;
                        int LA34_0 = input.LA(1);

                        if ( (LA34_0==68) ) {
                            alt34=1;
                        }


                        switch (alt34) {
                    	case 1 :
                    	    // Generator.g:431:9: inv= invariantClause
                    	    {
                    	    pushFollow(FOLLOW_invariantClause_in_associationClassDefinition1678);
                    	    inv=invariantClause();

                    	    state._fsp--;

                    	     n.addInvariantClause(inv); 

                    	    }
                    	    break;

                    	default :
                    	    break loop34;
                        }
                    } while (true);


                    }
                    break;

            }

            // Generator.g:434:5: ( ( 'aggregation' | 'composition' ) )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( ((LA36_0>=62 && LA36_0<=63)) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // Generator.g:434:7: ( 'aggregation' | 'composition' )
                    {
                     t = input.LT(1); 
                    if ( (input.LA(1)>=62 && input.LA(1)<=63) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                     n.setKind(t); 

                    }
                    break;

            }

            match(input,46,FOLLOW_46_in_associationClassDefinition1741); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "associationClassDefinition"


    // $ANTLR start "attributeDefinition"
    // Generator.g:445:1: attributeDefinition returns [ASTAttribute n] : name= IDENT COLON t= type ( SEMI )? ;
    public final ASTAttribute attributeDefinition() throws RecognitionException {
        ASTAttribute n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Generator.g:446:1: (name= IDENT COLON t= type ( SEMI )? )
            // Generator.g:447:5: name= IDENT COLON t= type ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_attributeDefinition1770); 
            match(input,COLON,FOLLOW_COLON_in_attributeDefinition1772); 
            pushFollow(FOLLOW_type_in_attributeDefinition1776);
            t=type();

            state._fsp--;

            // Generator.g:447:29: ( SEMI )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==SEMI) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // Generator.g:447:31: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_attributeDefinition1780); 

                    }
                    break;

            }

             n = new ASTAttribute(name, t); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "attributeDefinition"


    // $ANTLR start "operationDefinition"
    // Generator.g:456:1: operationDefinition returns [ASTOperation n] : name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression )? ( 'begin' al= alActionList 'end' )? (ppc= prePostClause )* ( SEMI )? ;
    public final ASTOperation operationDefinition() throws RecognitionException {
        ASTOperation n = null;

        Token name=null;
        List pl = null;

        ASTType t = null;

        ASTExpression e = null;

        ASTALActionList al = null;

        ASTPrePostClause ppc = null;


        try {
            // Generator.g:457:1: (name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression )? ( 'begin' al= alActionList 'end' )? (ppc= prePostClause )* ( SEMI )? )
            // Generator.g:458:5: name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression )? ( 'begin' al= alActionList 'end' )? (ppc= prePostClause )* ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationDefinition1818); 
            pushFollow(FOLLOW_paramList_in_operationDefinition1826);
            pl=paramList();

            state._fsp--;

            // Generator.g:460:5: ( COLON t= type )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==COLON) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // Generator.g:460:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_operationDefinition1834); 
                    pushFollow(FOLLOW_type_in_operationDefinition1838);
                    t=type();

                    state._fsp--;


                    }
                    break;

            }

            // Generator.g:461:5: ( EQUAL e= expression )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==EQUAL) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // Generator.g:461:6: EQUAL e= expression
                    {
                    match(input,EQUAL,FOLLOW_EQUAL_in_operationDefinition1848); 
                    pushFollow(FOLLOW_expression_in_operationDefinition1852);
                    e=expression();

                    state._fsp--;


                    }
                    break;

            }

            // Generator.g:462:2: ( 'begin' al= alActionList 'end' )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==45) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // Generator.g:462:3: 'begin' al= alActionList 'end'
                    {
                    match(input,45,FOLLOW_45_in_operationDefinition1860); 
                    pushFollow(FOLLOW_alActionList_in_operationDefinition1864);
                    al=alActionList();

                    state._fsp--;

                    match(input,46,FOLLOW_46_in_operationDefinition1866); 

                    }
                    break;

            }

             n = new ASTOperation(name, pl, t, e, al); 
            // Generator.g:464:5: (ppc= prePostClause )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( ((LA41_0>=69 && LA41_0<=70)) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // Generator.g:464:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_operationDefinition1885);
            	    ppc=prePostClause();

            	    state._fsp--;

            	     n.addPrePostClause(ppc); 

            	    }
            	    break;

            	default :
            	    break loop41;
                }
            } while (true);

            // Generator.g:465:5: ( SEMI )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==SEMI) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // Generator.g:465:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_operationDefinition1898); 

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "operationDefinition"


    // $ANTLR start "associationDefinition"
    // Generator.g:475:1: associationDefinition returns [ASTAssociation n] : ( 'association' | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end' ;
    public final ASTAssociation associationDefinition() throws RecognitionException {
        ASTAssociation n = null;

        Token name=null;
        ASTAssociationEnd ae = null;


         Token t = null; 
        try {
            // Generator.g:477:1: ( ( 'association' | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end' )
            // Generator.g:478:5: ( 'association' | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end'
            {
             t = input.LT(1); 
            if ( (input.LA(1)>=62 && input.LA(1)<=64) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationDefinition1959); 
             n = new ASTAssociation(t, name); 
            match(input,61,FOLLOW_61_in_associationDefinition1967); 
            pushFollow(FOLLOW_associationEnd_in_associationDefinition1975);
            ae=associationEnd();

            state._fsp--;

             n.addEnd(ae); 
            // Generator.g:484:5: (ae= associationEnd )+
            int cnt43=0;
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==IDENT) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // Generator.g:484:7: ae= associationEnd
            	    {
            	    pushFollow(FOLLOW_associationEnd_in_associationDefinition1987);
            	    ae=associationEnd();

            	    state._fsp--;

            	     n.addEnd(ae); 

            	    }
            	    break;

            	default :
            	    if ( cnt43 >= 1 ) break loop43;
                        EarlyExitException eee =
                            new EarlyExitException(43, input);
                        throw eee;
                }
                cnt43++;
            } while (true);

            match(input,46,FOLLOW_46_in_associationDefinition1998); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "associationDefinition"


    // $ANTLR start "associationEnd"
    // Generator.g:493:1: associationEnd returns [ASTAssociationEnd n] : name= IDENT LBRACK m= multiplicity RBRACK ( 'role' rn= IDENT )? ( 'ordered' )? ( SEMI )? ;
    public final ASTAssociationEnd associationEnd() throws RecognitionException {
        ASTAssociationEnd n = null;

        Token name=null;
        Token rn=null;
        ASTMultiplicity m = null;


        try {
            // Generator.g:494:1: (name= IDENT LBRACK m= multiplicity RBRACK ( 'role' rn= IDENT )? ( 'ordered' )? ( SEMI )? )
            // Generator.g:495:5: name= IDENT LBRACK m= multiplicity RBRACK ( 'role' rn= IDENT )? ( 'ordered' )? ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd2024); 
            match(input,LBRACK,FOLLOW_LBRACK_in_associationEnd2026); 
            pushFollow(FOLLOW_multiplicity_in_associationEnd2030);
            m=multiplicity();

            state._fsp--;

            match(input,RBRACK,FOLLOW_RBRACK_in_associationEnd2032); 
             n = new ASTAssociationEnd(name, m); 
            // Generator.g:497:5: ( 'role' rn= IDENT )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==65) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // Generator.g:497:7: 'role' rn= IDENT
                    {
                    match(input,65,FOLLOW_65_in_associationEnd2048); 
                    rn=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd2052); 
                     n.setRolename(rn); 

                    }
                    break;

            }

            // Generator.g:498:5: ( 'ordered' )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==66) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // Generator.g:498:7: 'ordered'
                    {
                    match(input,66,FOLLOW_66_in_associationEnd2065); 
                     n.setOrdered(); 

                    }
                    break;

            }

            // Generator.g:499:5: ( SEMI )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==SEMI) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // Generator.g:499:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_associationEnd2078); 

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "associationEnd"


    // $ANTLR start "multiplicity"
    // Generator.g:513:1: multiplicity returns [ASTMultiplicity n] : mr= multiplicityRange ( COMMA mr= multiplicityRange )* ;
    public final ASTMultiplicity multiplicity() throws RecognitionException {
        ASTMultiplicity n = null;

        ASTMultiplicityRange mr = null;


        try {
            // Generator.g:514:1: (mr= multiplicityRange ( COMMA mr= multiplicityRange )* )
            // Generator.g:515:5: mr= multiplicityRange ( COMMA mr= multiplicityRange )*
            {
             
            	Token t = input.LT(1); // remember start position of expression
            	n = new ASTMultiplicity(t);
                
            pushFollow(FOLLOW_multiplicityRange_in_multiplicity2113);
            mr=multiplicityRange();

            state._fsp--;

             n.addRange(mr); 
            // Generator.g:520:5: ( COMMA mr= multiplicityRange )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==COMMA) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // Generator.g:520:7: COMMA mr= multiplicityRange
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_multiplicity2123); 
            	    pushFollow(FOLLOW_multiplicityRange_in_multiplicity2127);
            	    mr=multiplicityRange();

            	    state._fsp--;

            	     n.addRange(mr); 

            	    }
            	    break;

            	default :
            	    break loop47;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "multiplicity"


    // $ANTLR start "multiplicityRange"
    // Generator.g:523:1: multiplicityRange returns [ASTMultiplicityRange n] : ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? ;
    public final ASTMultiplicityRange multiplicityRange() throws RecognitionException {
        ASTMultiplicityRange n = null;

        int ms1 = 0;

        int ms2 = 0;


        try {
            // Generator.g:524:1: (ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? )
            // Generator.g:525:5: ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )?
            {
            pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange2156);
            ms1=multiplicitySpec();

            state._fsp--;

             n = new ASTMultiplicityRange(ms1); 
            // Generator.g:526:5: ( DOTDOT ms2= multiplicitySpec )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==DOTDOT) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // Generator.g:526:7: DOTDOT ms2= multiplicitySpec
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_multiplicityRange2166); 
                    pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange2170);
                    ms2=multiplicitySpec();

                    state._fsp--;

                     n.setHigh(ms2); 

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "multiplicityRange"


    // $ANTLR start "multiplicitySpec"
    // Generator.g:529:1: multiplicitySpec returns [int m] : (i= INT | STAR );
    public final int multiplicitySpec() throws RecognitionException {
        int m = 0;

        Token i=null;

         m = -1; 
        try {
            // Generator.g:531:1: (i= INT | STAR )
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==INT) ) {
                alt49=1;
            }
            else if ( (LA49_0==STAR) ) {
                alt49=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;
            }
            switch (alt49) {
                case 1 :
                    // Generator.g:532:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_multiplicitySpec2204); 
                     m = Integer.parseInt((i!=null?i.getText():null)); 

                    }
                    break;
                case 2 :
                    // Generator.g:533:7: STAR
                    {
                    match(input,STAR,FOLLOW_STAR_in_multiplicitySpec2214); 
                     m = -1; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return m;
    }
    // $ANTLR end "multiplicitySpec"


    // $ANTLR start "invariant"
    // Generator.g:554:1: invariant returns [ASTConstraintDefinition n] : 'context' (v= IDENT COLON )? t= simpleType (inv= invariantClause )* ;
    public final ASTConstraintDefinition invariant() throws RecognitionException {
        ASTConstraintDefinition n = null;

        Token v=null;
        ASTSimpleType t = null;

        ASTInvariantClause inv = null;


        try {
            // Generator.g:555:1: ( 'context' (v= IDENT COLON )? t= simpleType (inv= invariantClause )* )
            // Generator.g:556:5: 'context' (v= IDENT COLON )? t= simpleType (inv= invariantClause )*
            {
             n = new ASTConstraintDefinition(); 
            match(input,67,FOLLOW_67_in_invariant2255); 
            // Generator.g:558:5: (v= IDENT COLON )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==IDENT) ) {
                int LA50_1 = input.LA(2);

                if ( (LA50_1==COLON) ) {
                    alt50=1;
                }
            }
            switch (alt50) {
                case 1 :
                    // Generator.g:558:7: v= IDENT COLON
                    {
                    v=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariant2265); 
                    match(input,COLON,FOLLOW_COLON_in_invariant2267); 
                     n.setVarName(v); 

                    }
                    break;

            }

            pushFollow(FOLLOW_simpleType_in_invariant2282);
            t=simpleType();

            state._fsp--;

             n.setType(t); 
            // Generator.g:561:5: (inv= invariantClause )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==68) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // Generator.g:561:7: inv= invariantClause
            	    {
            	    pushFollow(FOLLOW_invariantClause_in_invariant2294);
            	    inv=invariantClause();

            	    state._fsp--;

            	     n.addInvariantClause(inv); 

            	    }
            	    break;

            	default :
            	    break loop51;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "invariant"


    // $ANTLR start "invariantClause"
    // Generator.g:569:1: invariantClause returns [ASTInvariantClause n] : 'inv' (name= IDENT )? COLON e= expression ;
    public final ASTInvariantClause invariantClause() throws RecognitionException {
        ASTInvariantClause n = null;

        Token name=null;
        ASTExpression e = null;


        try {
            // Generator.g:570:1: ( 'inv' (name= IDENT )? COLON e= expression )
            // Generator.g:571:5: 'inv' (name= IDENT )? COLON e= expression
            {
            match(input,68,FOLLOW_68_in_invariantClause2324); 
            // Generator.g:571:11: (name= IDENT )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==IDENT) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // Generator.g:571:13: name= IDENT
                    {
                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantClause2330); 

                    }
                    break;

            }

            match(input,COLON,FOLLOW_COLON_in_invariantClause2335); 
            pushFollow(FOLLOW_expression_in_invariantClause2339);
            e=expression();

            state._fsp--;

             n = new ASTInvariantClause(name, e); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "invariantClause"


    // $ANTLR start "prePost"
    // Generator.g:583:1: prePost returns [ASTPrePost n] : 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ ;
    public final ASTPrePost prePost() throws RecognitionException {
        ASTPrePost n = null;

        Token classname=null;
        Token opname=null;
        List pl = null;

        ASTType rt = null;

        ASTPrePostClause ppc = null;


        try {
            // Generator.g:584:1: ( 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ )
            // Generator.g:585:5: 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+
            {
            match(input,67,FOLLOW_67_in_prePost2369); 
            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost2373); 
            match(input,COLON_COLON,FOLLOW_COLON_COLON_in_prePost2375); 
            opname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost2379); 
            pushFollow(FOLLOW_paramList_in_prePost2383);
            pl=paramList();

            state._fsp--;

            // Generator.g:585:69: ( COLON rt= type )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==COLON) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // Generator.g:585:71: COLON rt= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_prePost2387); 
                    pushFollow(FOLLOW_type_in_prePost2391);
                    rt=type();

                    state._fsp--;


                    }
                    break;

            }

             n = new ASTPrePost(classname, opname, pl, rt); 
            // Generator.g:587:5: (ppc= prePostClause )+
            int cnt54=0;
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( ((LA54_0>=69 && LA54_0<=70)) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // Generator.g:587:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_prePost2410);
            	    ppc=prePostClause();

            	    state._fsp--;

            	     n.addPrePostClause(ppc); 

            	    }
            	    break;

            	default :
            	    if ( cnt54 >= 1 ) break loop54;
                        EarlyExitException eee =
                            new EarlyExitException(54, input);
                        throw eee;
                }
                cnt54++;
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "prePost"


    // $ANTLR start "prePostClause"
    // Generator.g:594:1: prePostClause returns [ASTPrePostClause n] : ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression ;
    public final ASTPrePostClause prePostClause() throws RecognitionException {
        ASTPrePostClause n = null;

        Token name=null;
        ASTExpression e = null;


         Token t = null; 
        try {
            // Generator.g:596:1: ( ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression )
            // Generator.g:597:5: ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression
            {
             t = input.LT(1); 
            if ( (input.LA(1)>=69 && input.LA(1)<=70) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // Generator.g:598:25: (name= IDENT )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==IDENT) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // Generator.g:598:27: name= IDENT
                    {
                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePostClause2464); 

                    }
                    break;

            }

            match(input,COLON,FOLLOW_COLON_in_prePostClause2469); 
            pushFollow(FOLLOW_expression_in_prePostClause2473);
            e=expression();

            state._fsp--;

             n = new ASTPrePostClause(t, name, e); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "prePostClause"


    // $ANTLR start "alActionList"
    // Generator.g:602:1: alActionList returns [ASTALActionList al] : (action= alAction )* ;
    public final ASTALActionList alActionList() throws RecognitionException {
        ASTALActionList al = null;

        ASTALAction action = null;



        	al = new ASTALActionList();

        try {
            // Generator.g:606:1: ( (action= alAction )* )
            // Generator.g:607:2: (action= alAction )*
            {
            // Generator.g:607:2: (action= alAction )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==44||LA56_0==47||LA56_0==49||(LA56_0>=71 && LA56_0<=73)||LA56_0==75||LA56_0==77||LA56_0==79||LA56_0==81||LA56_0==84) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // Generator.g:607:4: action= alAction
            	    {
            	    pushFollow(FOLLOW_alAction_in_alActionList2506);
            	    action=alAction();

            	    state._fsp--;

            	    al.add(action);

            	    }
            	    break;

            	default :
            	    break loop56;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return al;
    }
    // $ANTLR end "alActionList"


    // $ANTLR start "alAction"
    // Generator.g:610:1: alAction returns [ASTALAction action] : (aCV= alCreateVar | aDl= alDelete | aSe= alSet | aSC= alSetCreate | aIn= alInsert | aDe= alDestroy | aIf= alIf | aWh= alWhile | aFo= alFor | aEx= alExec );
    public final ASTALAction alAction() throws RecognitionException {
        ASTALAction action = null;

        ASTALCreateVar aCV = null;

        ASTALDelete aDl = null;

        ASTALSet aSe = null;

        ASTALSetCreate aSC = null;

        ASTALInsert aIn = null;

        ASTALDestroy aDe = null;

        ASTALIf aIf = null;

        ASTALWhile aWh = null;

        ASTALFor aFo = null;

        ASTALExecute aEx = null;


        try {
            // Generator.g:611:1: (aCV= alCreateVar | aDl= alDelete | aSe= alSet | aSC= alSetCreate | aIn= alInsert | aDe= alDestroy | aIf= alIf | aWh= alWhile | aFo= alFor | aEx= alExec )
            int alt57=10;
            switch ( input.LA(1) ) {
            case 44:
            case 71:
                {
                alt57=1;
                }
                break;
            case 77:
                {
                alt57=2;
                }
                break;
            case 72:
                {
                alt57=3;
                }
                break;
            case 73:
                {
                alt57=4;
                }
                break;
            case 75:
                {
                alt57=5;
                }
                break;
            case 79:
                {
                alt57=6;
                }
                break;
            case 49:
                {
                alt57=7;
                }
                break;
            case 81:
                {
                alt57=8;
                }
                break;
            case 47:
                {
                alt57=9;
                }
                break;
            case 84:
                {
                alt57=10;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 57, 0, input);

                throw nvae;
            }

            switch (alt57) {
                case 1 :
                    // Generator.g:612:5: aCV= alCreateVar
                    {
                    pushFollow(FOLLOW_alCreateVar_in_alAction2532);
                    aCV=alCreateVar();

                    state._fsp--;

                     action = aCV; 

                    }
                    break;
                case 2 :
                    // Generator.g:613:5: aDl= alDelete
                    {
                    pushFollow(FOLLOW_alDelete_in_alAction2544);
                    aDl=alDelete();

                    state._fsp--;

                     action = aDl; 

                    }
                    break;
                case 3 :
                    // Generator.g:614:5: aSe= alSet
                    {
                    pushFollow(FOLLOW_alSet_in_alAction2556);
                    aSe=alSet();

                    state._fsp--;

                     action = aSe; 

                    }
                    break;
                case 4 :
                    // Generator.g:615:5: aSC= alSetCreate
                    {
                    pushFollow(FOLLOW_alSetCreate_in_alAction2568);
                    aSC=alSetCreate();

                    state._fsp--;

                     action = aSC; 

                    }
                    break;
                case 5 :
                    // Generator.g:616:5: aIn= alInsert
                    {
                    pushFollow(FOLLOW_alInsert_in_alAction2580);
                    aIn=alInsert();

                    state._fsp--;

                     action = aIn; 

                    }
                    break;
                case 6 :
                    // Generator.g:617:5: aDe= alDestroy
                    {
                    pushFollow(FOLLOW_alDestroy_in_alAction2592);
                    aDe=alDestroy();

                    state._fsp--;

                     action = aDe; 

                    }
                    break;
                case 7 :
                    // Generator.g:618:5: aIf= alIf
                    {
                    pushFollow(FOLLOW_alIf_in_alAction2604);
                    aIf=alIf();

                    state._fsp--;

                     action = aIf; 

                    }
                    break;
                case 8 :
                    // Generator.g:619:5: aWh= alWhile
                    {
                    pushFollow(FOLLOW_alWhile_in_alAction2616);
                    aWh=alWhile();

                    state._fsp--;

                     action = aWh; 

                    }
                    break;
                case 9 :
                    // Generator.g:620:5: aFo= alFor
                    {
                    pushFollow(FOLLOW_alFor_in_alAction2628);
                    aFo=alFor();

                    state._fsp--;

                     action = aFo; 

                    }
                    break;
                case 10 :
                    // Generator.g:621:5: aEx= alExec
                    {
                    pushFollow(FOLLOW_alExec_in_alAction2640);
                    aEx=alExec();

                    state._fsp--;

                     action = aEx; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return action;
    }
    // $ANTLR end "alAction"


    // $ANTLR start "alCreateVar"
    // Generator.g:626:1: alCreateVar returns [ASTALCreateVar var] : ( 'var' | 'declare' ) name= IDENT COLON t= type ;
    public final ASTALCreateVar alCreateVar() throws RecognitionException {
        ASTALCreateVar var = null;

        Token name=null;
        ASTType t = null;


        try {
            // Generator.g:627:1: ( ( 'var' | 'declare' ) name= IDENT COLON t= type )
            // Generator.g:628:2: ( 'var' | 'declare' ) name= IDENT COLON t= type
            {
            if ( input.LA(1)==44||input.LA(1)==71 ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_alCreateVar2667); 
            match(input,COLON,FOLLOW_COLON_in_alCreateVar2669); 
            pushFollow(FOLLOW_type_in_alCreateVar2673);
            t=type();

            state._fsp--;

             var = new ASTALCreateVar(name, t); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return var;
    }
    // $ANTLR end "alCreateVar"


    // $ANTLR start "alSet"
    // Generator.g:632:1: alSet returns [ASTALSet set] : 'set' lval= expression COLON_EQUAL rval= expression ;
    public final ASTALSet alSet() throws RecognitionException {
        ASTALSet set = null;

        ASTExpression lval = null;

        ASTExpression rval = null;


        try {
            // Generator.g:633:1: ( 'set' lval= expression COLON_EQUAL rval= expression )
            // Generator.g:635:5: 'set' lval= expression COLON_EQUAL rval= expression
            {
            match(input,72,FOLLOW_72_in_alSet2695); 
            pushFollow(FOLLOW_expression_in_alSet2699);
            lval=expression();

            state._fsp--;

            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_alSet2701); 
            pushFollow(FOLLOW_expression_in_alSet2705);
            rval=expression();

            state._fsp--;

             set = new ASTALSet(lval, rval); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return set;
    }
    // $ANTLR end "alSet"


    // $ANTLR start "alSetCreate"
    // Generator.g:639:1: alSetCreate returns [ASTALSetCreate setcreate] : 'create' lval= expression COLON_EQUAL {...}?new_= IDENT cls= IDENT ( 'namehint' nameExpr= expression )? ;
    public final ASTALSetCreate alSetCreate() throws RecognitionException {
        ASTALSetCreate setcreate = null;

        Token new_=null;
        Token cls=null;
        ASTExpression lval = null;

        ASTExpression nameExpr = null;


        try {
            // Generator.g:640:1: ( 'create' lval= expression COLON_EQUAL {...}?new_= IDENT cls= IDENT ( 'namehint' nameExpr= expression )? )
            // Generator.g:641:5: 'create' lval= expression COLON_EQUAL {...}?new_= IDENT cls= IDENT ( 'namehint' nameExpr= expression )?
            {
            match(input,73,FOLLOW_73_in_alSetCreate2729); 
            pushFollow(FOLLOW_expression_in_alSetCreate2733);
            lval=expression();

            state._fsp--;

            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_alSetCreate2735); 
            if ( !(( input.LT(1).getText().equals("new") )) ) {
                throw new FailedPredicateException(input, "alSetCreate", " input.LT(1).getText().equals(\"new\") ");
            }
            new_=(Token)match(input,IDENT,FOLLOW_IDENT_in_alSetCreate2741); 
            cls=(Token)match(input,IDENT,FOLLOW_IDENT_in_alSetCreate2745); 
            // Generator.g:642:5: ( 'namehint' nameExpr= expression )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==74) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // Generator.g:642:7: 'namehint' nameExpr= expression
                    {
                    match(input,74,FOLLOW_74_in_alSetCreate2754); 
                    pushFollow(FOLLOW_expression_in_alSetCreate2758);
                    nameExpr=expression();

                    state._fsp--;


                    }
                    break;

            }

             setcreate = new ASTALSetCreate(lval, cls, nameExpr);

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return setcreate;
    }
    // $ANTLR end "alSetCreate"


    // $ANTLR start "alInsert"
    // Generator.g:647:1: alInsert returns [ASTALInsert insert] : 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT ;
    public final ASTALInsert alInsert() throws RecognitionException {
        ASTALInsert insert = null;

        Token id=null;
        ASTExpression e = null;


        List exprList = new ArrayList(); 
        try {
            // Generator.g:649:1: ( 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT )
            // Generator.g:650:5: 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT
            {
            match(input,75,FOLLOW_75_in_alInsert2789); 
            match(input,LPAREN,FOLLOW_LPAREN_in_alInsert2791); 
            pushFollow(FOLLOW_expression_in_alInsert2800);
            e=expression();

            state._fsp--;

             exprList.add(e); 
            match(input,COMMA,FOLLOW_COMMA_in_alInsert2804); 
            pushFollow(FOLLOW_expression_in_alInsert2812);
            e=expression();

            state._fsp--;

             exprList.add(e); 
            // Generator.g:652:42: ( COMMA e= expression )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==COMMA) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // Generator.g:652:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_alInsert2818); 
            	    pushFollow(FOLLOW_expression_in_alInsert2822);
            	    e=expression();

            	    state._fsp--;

            	     exprList.add(e); 

            	    }
            	    break;

            	default :
            	    break loop59;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_alInsert2834); 
            match(input,76,FOLLOW_76_in_alInsert2836); 
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_alInsert2840); 
             insert = new ASTALInsert(exprList, id); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return insert;
    }
    // $ANTLR end "alInsert"


    // $ANTLR start "alDelete"
    // Generator.g:658:1: alDelete returns [ASTALDelete n] : 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT ;
    public final ASTALDelete alDelete() throws RecognitionException {
        ASTALDelete n = null;

        Token id=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // Generator.g:660:1: ( 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT )
            // Generator.g:661:5: 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT
            {
            match(input,77,FOLLOW_77_in_alDelete2872); 
            match(input,LPAREN,FOLLOW_LPAREN_in_alDelete2874); 
            pushFollow(FOLLOW_expression_in_alDelete2882);
            e=expression();

            state._fsp--;

             exprList.add(e); 
            match(input,COMMA,FOLLOW_COMMA_in_alDelete2886); 
            pushFollow(FOLLOW_expression_in_alDelete2894);
            e=expression();

            state._fsp--;

             exprList.add(e); 
            // Generator.g:663:42: ( COMMA e= expression )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==COMMA) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // Generator.g:663:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_alDelete2900); 
            	    pushFollow(FOLLOW_expression_in_alDelete2904);
            	    e=expression();

            	    state._fsp--;

            	     exprList.add(e); 

            	    }
            	    break;

            	default :
            	    break loop60;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_alDelete2915); 
            match(input,78,FOLLOW_78_in_alDelete2917); 
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_alDelete2921); 
             n = new ASTALDelete(exprList, id); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "alDelete"


    // $ANTLR start "alDestroy"
    // Generator.g:669:1: alDestroy returns [ASTALDestroy n] : 'destroy' e= expression ;
    public final ASTALDestroy alDestroy() throws RecognitionException {
        ASTALDestroy n = null;

        ASTExpression e = null;


        try {
            // Generator.g:670:1: ( 'destroy' e= expression )
            // Generator.g:671:6: 'destroy' e= expression
            {
            match(input,79,FOLLOW_79_in_alDestroy2950); 
            pushFollow(FOLLOW_expression_in_alDestroy2954);
            e=expression();

            state._fsp--;

             n = new ASTALDestroy(e); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "alDestroy"


    // $ANTLR start "alIf"
    // Generator.g:675:1: alIf returns [ASTALIf i] : 'if' ifexpr= expression 'then' thenlist= alActionList ( 'else' elselist= alActionList )? 'endif' ;
    public final ASTALIf alIf() throws RecognitionException {
        ASTALIf i = null;

        ASTExpression ifexpr = null;

        ASTALActionList thenlist = null;

        ASTALActionList elselist = null;


        try {
            // Generator.g:676:1: ( 'if' ifexpr= expression 'then' thenlist= alActionList ( 'else' elselist= alActionList )? 'endif' )
            // Generator.g:677:2: 'if' ifexpr= expression 'then' thenlist= alActionList ( 'else' elselist= alActionList )? 'endif'
            {
            match(input,49,FOLLOW_49_in_alIf2978); 
            pushFollow(FOLLOW_expression_in_alIf2982);
            ifexpr=expression();

            state._fsp--;

            match(input,50,FOLLOW_50_in_alIf2986); 
            pushFollow(FOLLOW_alActionList_in_alIf2990);
            thenlist=alActionList();

            state._fsp--;

            // Generator.g:679:2: ( 'else' elselist= alActionList )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==51) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // Generator.g:679:3: 'else' elselist= alActionList
                    {
                    match(input,51,FOLLOW_51_in_alIf2994); 
                    pushFollow(FOLLOW_alActionList_in_alIf2998);
                    elselist=alActionList();

                    state._fsp--;


                    }
                    break;

            }

            match(input,80,FOLLOW_80_in_alIf3003); 
             i = new ASTALIf(ifexpr, thenlist, elselist); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return i;
    }
    // $ANTLR end "alIf"


    // $ANTLR start "alWhile"
    // Generator.g:684:1: alWhile returns [ASTALWhile w] : 'while' expr= expression 'do' body= alActionList 'wend' ;
    public final ASTALWhile alWhile() throws RecognitionException {
        ASTALWhile w = null;

        ASTExpression expr = null;

        ASTALActionList body = null;


        try {
            // Generator.g:685:1: ( 'while' expr= expression 'do' body= alActionList 'wend' )
            // Generator.g:686:2: 'while' expr= expression 'do' body= alActionList 'wend'
            {
            match(input,81,FOLLOW_81_in_alWhile3022); 
            pushFollow(FOLLOW_expression_in_alWhile3026);
            expr=expression();

            state._fsp--;

            match(input,82,FOLLOW_82_in_alWhile3030); 
            pushFollow(FOLLOW_alActionList_in_alWhile3036);
            body=alActionList();

            state._fsp--;

            match(input,83,FOLLOW_83_in_alWhile3039); 
             w = new ASTALWhile(expr, body); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return w;
    }
    // $ANTLR end "alWhile"


    // $ANTLR start "alFor"
    // Generator.g:694:1: alFor returns [ASTALFor f] : 'for' var= IDENT COLON t= type 'in' expr= expression 'do' body= alActionList {...}?next= IDENT ;
    public final ASTALFor alFor() throws RecognitionException {
        ASTALFor f = null;

        Token var=null;
        Token next=null;
        ASTType t = null;

        ASTExpression expr = null;

        ASTALActionList body = null;


        try {
            // Generator.g:695:1: ( 'for' var= IDENT COLON t= type 'in' expr= expression 'do' body= alActionList {...}?next= IDENT )
            // Generator.g:696:2: 'for' var= IDENT COLON t= type 'in' expr= expression 'do' body= alActionList {...}?next= IDENT
            {
            match(input,47,FOLLOW_47_in_alFor3058); 
            var=(Token)match(input,IDENT,FOLLOW_IDENT_in_alFor3062); 
            match(input,COLON,FOLLOW_COLON_in_alFor3064); 
            pushFollow(FOLLOW_type_in_alFor3068);
            t=type();

            state._fsp--;

            match(input,48,FOLLOW_48_in_alFor3070); 
            pushFollow(FOLLOW_expression_in_alFor3074);
            expr=expression();

            state._fsp--;

            match(input,82,FOLLOW_82_in_alFor3078); 
            pushFollow(FOLLOW_alActionList_in_alFor3084);
            body=alActionList();

            state._fsp--;

            if ( !(( input.LT(1).getText().equals("next") )) ) {
                throw new FailedPredicateException(input, "alFor", " input.LT(1).getText().equals(\"next\") ");
            }
            next=(Token)match(input,IDENT,FOLLOW_IDENT_in_alFor3091); 
             f = new ASTALFor(var, t, expr, body); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return f;
    }
    // $ANTLR end "alFor"


    // $ANTLR start "alExec"
    // Generator.g:703:1: alExec returns [ASTALExecute c] : 'execute' op= expression ;
    public final ASTALExecute alExec() throws RecognitionException {
        ASTALExecute c = null;

        ASTExpression op = null;


        try {
            // Generator.g:704:1: ( 'execute' op= expression )
            // Generator.g:705:5: 'execute' op= expression
            {
            match(input,84,FOLLOW_84_in_alExec3111); 
            pushFollow(FOLLOW_expression_in_alExec3115);
            op=expression();

            state._fsp--;

             c = new ASTALExecute(op); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return c;
    }
    // $ANTLR end "alExec"


    // $ANTLR start "expressionOnly"
    // Generator.g:737:1: expressionOnly returns [ASTExpression n] : nExp= expression EOF ;
    public final ASTExpression expressionOnly() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExp = null;


        try {
            // Generator.g:738:1: (nExp= expression EOF )
            // Generator.g:739:5: nExp= expression EOF
            {
            pushFollow(FOLLOW_expression_in_expressionOnly3150);
            nExp=expression();

            state._fsp--;

            match(input,EOF,FOLLOW_EOF_in_expressionOnly3152); 
            n = nExp;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "expressionOnly"


    // $ANTLR start "expression"
    // Generator.g:746:1: expression returns [ASTExpression n] : ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression ;
    public final ASTExpression expression() throws RecognitionException {
        ASTExpression n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e1 = null;

        ASTExpression nCndImplies = null;


         
          ASTLetExpression prevLet = null, firstLet = null;
          ASTExpression e2;
          Token tok = null;

        try {
            // Generator.g:752:1: ( ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression )
            // Generator.g:753:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression
            {
             tok = input.LT(1); /* remember start of expression */ 
            // Generator.g:754:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==85) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // Generator.g:755:7: 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in'
            	    {
            	    match(input,85,FOLLOW_85_in_expression3200); 
            	    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_expression3204); 
            	    // Generator.g:755:24: ( COLON t= type )?
            	    int alt62=2;
            	    int LA62_0 = input.LA(1);

            	    if ( (LA62_0==COLON) ) {
            	        alt62=1;
            	    }
            	    switch (alt62) {
            	        case 1 :
            	            // Generator.g:755:26: COLON t= type
            	            {
            	            match(input,COLON,FOLLOW_COLON_in_expression3208); 
            	            pushFollow(FOLLOW_type_in_expression3212);
            	            t=type();

            	            state._fsp--;


            	            }
            	            break;

            	    }

            	    match(input,EQUAL,FOLLOW_EQUAL_in_expression3217); 
            	    pushFollow(FOLLOW_expression_in_expression3221);
            	    e1=expression();

            	    state._fsp--;

            	    match(input,48,FOLLOW_48_in_expression3223); 
            	     ASTLetExpression nextLet = new ASTLetExpression(name, t, e1);
            	             if ( firstLet == null ) 
            	                 firstLet = nextLet;
            	             if ( prevLet != null ) 
            	                 prevLet.setInExpr(nextLet);
            	             prevLet = nextLet;
            	           

            	    }
            	    break;

            	default :
            	    break loop63;
                }
            } while (true);

            pushFollow(FOLLOW_conditionalImpliesExpression_in_expression3248);
            nCndImplies=conditionalImpliesExpression();

            state._fsp--;

             if ( nCndImplies != null ) {
                	 n = nCndImplies;
                     n.setStartToken(tok);
                  }
                  
                  if ( prevLet != null ) { 
                     prevLet.setInExpr(n);
                     n = firstLet;
                     n.setStartToken(tok);
                  }
                

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "expression"


    // $ANTLR start "paramList"
    // Generator.g:783:1: paramList returns [List paramList] : LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN ;
    public final List paramList() throws RecognitionException {
        List paramList = null;

        ASTVariableDeclaration v = null;


         paramList = new ArrayList(); 
        try {
            // Generator.g:785:1: ( LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN )
            // Generator.g:786:5: LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN
            {
            match(input,LPAREN,FOLLOW_LPAREN_in_paramList3281); 
            // Generator.g:787:5: (v= variableDeclaration ( COMMA v= variableDeclaration )* )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==IDENT) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // Generator.g:788:7: v= variableDeclaration ( COMMA v= variableDeclaration )*
                    {
                    pushFollow(FOLLOW_variableDeclaration_in_paramList3298);
                    v=variableDeclaration();

                    state._fsp--;

                     paramList.add(v); 
                    // Generator.g:789:7: ( COMMA v= variableDeclaration )*
                    loop64:
                    do {
                        int alt64=2;
                        int LA64_0 = input.LA(1);

                        if ( (LA64_0==COMMA) ) {
                            alt64=1;
                        }


                        switch (alt64) {
                    	case 1 :
                    	    // Generator.g:789:9: COMMA v= variableDeclaration
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_paramList3310); 
                    	    pushFollow(FOLLOW_variableDeclaration_in_paramList3314);
                    	    v=variableDeclaration();

                    	    state._fsp--;

                    	     paramList.add(v); 

                    	    }
                    	    break;

                    	default :
                    	    break loop64;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_paramList3334); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return paramList;
    }
    // $ANTLR end "paramList"


    // $ANTLR start "idList"
    // Generator.g:797:1: idList returns [List idList] : id0= IDENT ( COMMA idn= IDENT )* ;
    public final List idList() throws RecognitionException {
        List idList = null;

        Token id0=null;
        Token idn=null;

         idList = new ArrayList(); 
        try {
            // Generator.g:799:1: (id0= IDENT ( COMMA idn= IDENT )* )
            // Generator.g:800:5: id0= IDENT ( COMMA idn= IDENT )*
            {
            id0=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList3363); 
             idList.add(id0); 
            // Generator.g:801:5: ( COMMA idn= IDENT )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( (LA66_0==COMMA) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // Generator.g:801:7: COMMA idn= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_idList3373); 
            	    idn=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList3377); 
            	     idList.add(idn); 

            	    }
            	    break;

            	default :
            	    break loop66;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return idList;
    }
    // $ANTLR end "idList"


    // $ANTLR start "variableDeclaration"
    // Generator.g:809:1: variableDeclaration returns [ASTVariableDeclaration n] : name= IDENT COLON t= type ;
    public final ASTVariableDeclaration variableDeclaration() throws RecognitionException {
        ASTVariableDeclaration n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Generator.g:810:1: (name= IDENT COLON t= type )
            // Generator.g:811:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableDeclaration3408); 
            match(input,COLON,FOLLOW_COLON_in_variableDeclaration3410); 
            pushFollow(FOLLOW_type_in_variableDeclaration3414);
            t=type();

            state._fsp--;

             n = new ASTVariableDeclaration(name, t); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "variableDeclaration"


    // $ANTLR start "conditionalImpliesExpression"
    // Generator.g:819:1: conditionalImpliesExpression returns [ASTExpression n] : nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* ;
    public final ASTExpression conditionalImpliesExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndOrExp = null;

        ASTExpression n1 = null;


        try {
            // Generator.g:820:1: (nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* )
            // Generator.g:821:5: nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )*
            {
            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3450);
            nCndOrExp=conditionalOrExpression();

            state._fsp--;

            n = nCndOrExp;
            // Generator.g:822:5: (op= 'implies' n1= conditionalOrExpression )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==86) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // Generator.g:822:7: op= 'implies' n1= conditionalOrExpression
            	    {
            	    op=(Token)match(input,86,FOLLOW_86_in_conditionalImpliesExpression3463); 
            	    pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3467);
            	    n1=conditionalOrExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

            	    }
            	    break;

            	default :
            	    break loop67;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "conditionalImpliesExpression"


    // $ANTLR start "conditionalOrExpression"
    // Generator.g:831:1: conditionalOrExpression returns [ASTExpression n] : nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* ;
    public final ASTExpression conditionalOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndXorExp = null;

        ASTExpression n1 = null;


        try {
            // Generator.g:832:1: (nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* )
            // Generator.g:833:5: nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )*
            {
            pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3512);
            nCndXorExp=conditionalXOrExpression();

            state._fsp--;

            n = nCndXorExp;
            // Generator.g:834:5: (op= 'or' n1= conditionalXOrExpression )*
            loop68:
            do {
                int alt68=2;
                int LA68_0 = input.LA(1);

                if ( (LA68_0==87) ) {
                    alt68=1;
                }


                switch (alt68) {
            	case 1 :
            	    // Generator.g:834:7: op= 'or' n1= conditionalXOrExpression
            	    {
            	    op=(Token)match(input,87,FOLLOW_87_in_conditionalOrExpression3525); 
            	    pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3529);
            	    n1=conditionalXOrExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

            	    }
            	    break;

            	default :
            	    break loop68;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "conditionalOrExpression"


    // $ANTLR start "conditionalXOrExpression"
    // Generator.g:843:1: conditionalXOrExpression returns [ASTExpression n] : nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* ;
    public final ASTExpression conditionalXOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndAndExp = null;

        ASTExpression n1 = null;


        try {
            // Generator.g:844:1: (nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* )
            // Generator.g:845:5: nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )*
            {
            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3573);
            nCndAndExp=conditionalAndExpression();

            state._fsp--;

            n = nCndAndExp;
            // Generator.g:846:5: (op= 'xor' n1= conditionalAndExpression )*
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==88) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // Generator.g:846:7: op= 'xor' n1= conditionalAndExpression
            	    {
            	    op=(Token)match(input,88,FOLLOW_88_in_conditionalXOrExpression3586); 
            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3590);
            	    n1=conditionalAndExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

            	    }
            	    break;

            	default :
            	    break loop69;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "conditionalXOrExpression"


    // $ANTLR start "conditionalAndExpression"
    // Generator.g:855:1: conditionalAndExpression returns [ASTExpression n] : nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* ;
    public final ASTExpression conditionalAndExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nEqExp = null;

        ASTExpression n1 = null;


        try {
            // Generator.g:856:1: (nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* )
            // Generator.g:857:5: nEqExp= equalityExpression (op= 'and' n1= equalityExpression )*
            {
            pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression3634);
            nEqExp=equalityExpression();

            state._fsp--;

            n = nEqExp;
            // Generator.g:858:5: (op= 'and' n1= equalityExpression )*
            loop70:
            do {
                int alt70=2;
                int LA70_0 = input.LA(1);

                if ( (LA70_0==89) ) {
                    alt70=1;
                }


                switch (alt70) {
            	case 1 :
            	    // Generator.g:858:7: op= 'and' n1= equalityExpression
            	    {
            	    op=(Token)match(input,89,FOLLOW_89_in_conditionalAndExpression3647); 
            	    pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression3651);
            	    n1=equalityExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

            	    }
            	    break;

            	default :
            	    break loop70;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "conditionalAndExpression"


    // $ANTLR start "equalityExpression"
    // Generator.g:867:1: equalityExpression returns [ASTExpression n] : nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* ;
    public final ASTExpression equalityExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nRelExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Generator.g:869:1: (nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* )
            // Generator.g:870:5: nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            {
            pushFollow(FOLLOW_relationalExpression_in_equalityExpression3699);
            nRelExp=relationalExpression();

            state._fsp--;

            n = nRelExp;
            // Generator.g:871:5: ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            loop71:
            do {
                int alt71=2;
                int LA71_0 = input.LA(1);

                if ( (LA71_0==EQUAL||LA71_0==NOT_EQUAL) ) {
                    alt71=1;
                }


                switch (alt71) {
            	case 1 :
            	    // Generator.g:871:7: ( EQUAL | NOT_EQUAL ) n1= relationalExpression
            	    {
            	     op = input.LT(1); 
            	    if ( input.LA(1)==EQUAL||input.LA(1)==NOT_EQUAL ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression3728);
            	    n1=relationalExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

            	    }
            	    break;

            	default :
            	    break loop71;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "equalityExpression"


    // $ANTLR start "relationalExpression"
    // Generator.g:881:1: relationalExpression returns [ASTExpression n] : nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* ;
    public final ASTExpression relationalExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nAddiExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Generator.g:883:1: (nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* )
            // Generator.g:884:5: nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            {
            pushFollow(FOLLOW_additiveExpression_in_relationalExpression3777);
            nAddiExp=additiveExpression();

            state._fsp--;

            n = nAddiExp;
            // Generator.g:885:5: ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            loop72:
            do {
                int alt72=2;
                int LA72_0 = input.LA(1);

                if ( (LA72_0==LESS||(LA72_0>=GREATER && LA72_0<=GREATER_EQUAL)) ) {
                    alt72=1;
                }


                switch (alt72) {
            	case 1 :
            	    // Generator.g:885:7: ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression
            	    {
            	     op = input.LT(1); 
            	    if ( input.LA(1)==LESS||(input.LA(1)>=GREATER && input.LA(1)<=GREATER_EQUAL) ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_additiveExpression_in_relationalExpression3813);
            	    n1=additiveExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

            	    }
            	    break;

            	default :
            	    break loop72;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "relationalExpression"


    // $ANTLR start "additiveExpression"
    // Generator.g:895:1: additiveExpression returns [ASTExpression n] : nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* ;
    public final ASTExpression additiveExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nMulExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Generator.g:897:1: (nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* )
            // Generator.g:898:5: nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            {
            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3863);
            nMulExp=multiplicativeExpression();

            state._fsp--;

            n = nMulExp;
            // Generator.g:899:5: ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            loop73:
            do {
                int alt73=2;
                int LA73_0 = input.LA(1);

                if ( ((LA73_0>=PLUS && LA73_0<=MINUS)) ) {
                    alt73=1;
                }


                switch (alt73) {
            	case 1 :
            	    // Generator.g:899:7: ( PLUS | MINUS ) n1= multiplicativeExpression
            	    {
            	     op = input.LT(1); 
            	    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS) ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3891);
            	    n1=multiplicativeExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

            	    }
            	    break;

            	default :
            	    break loop73;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "additiveExpression"


    // $ANTLR start "multiplicativeExpression"
    // Generator.g:910:1: multiplicativeExpression returns [ASTExpression n] : nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* ;
    public final ASTExpression multiplicativeExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Generator.g:912:1: (nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* )
            // Generator.g:913:5: nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            {
            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3941);
            nUnExp=unaryExpression();

            state._fsp--;

             n = nUnExp;
            // Generator.g:914:5: ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            loop74:
            do {
                int alt74=2;
                int LA74_0 = input.LA(1);

                if ( (LA74_0==STAR||LA74_0==SLASH||LA74_0==90) ) {
                    alt74=1;
                }


                switch (alt74) {
            	case 1 :
            	    // Generator.g:914:7: ( STAR | SLASH | 'div' ) n1= unaryExpression
            	    {
            	     op = input.LT(1); 
            	    if ( input.LA(1)==STAR||input.LA(1)==SLASH||input.LA(1)==90 ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3973);
            	    n1=unaryExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

            	    }
            	    break;

            	default :
            	    break loop74;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "multiplicativeExpression"


    // $ANTLR start "unaryExpression"
    // Generator.g:926:1: unaryExpression returns [ASTExpression n] : ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression );
    public final ASTExpression unaryExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression nPosExp = null;


         Token op = null; 
        try {
            // Generator.g:928:1: ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression )
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( ((LA75_0>=PLUS && LA75_0<=MINUS)||LA75_0==91) ) {
                alt75=1;
            }
            else if ( ((LA75_0>=IDENT && LA75_0<=LPAREN)||LA75_0==INT||(LA75_0>=REAL && LA75_0<=HASH)||LA75_0==49||(LA75_0>=93 && LA75_0<=106)) ) {
                alt75=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 75, 0, input);

                throw nvae;
            }
            switch (alt75) {
                case 1 :
                    // Generator.g:929:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    {
                    // Generator.g:929:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    // Generator.g:929:9: ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression
                    {
                     op = input.LT(1); 
                    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS)||input.LA(1)==91 ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression4059);
                    nUnExp=unaryExpression();

                    state._fsp--;

                     n = new ASTUnaryExpression(op, nUnExp); 

                    }


                    }
                    break;
                case 2 :
                    // Generator.g:933:7: nPosExp= postfixExpression
                    {
                    pushFollow(FOLLOW_postfixExpression_in_unaryExpression4079);
                    nPosExp=postfixExpression();

                    state._fsp--;

                     n = nPosExp; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "unaryExpression"


    // $ANTLR start "postfixExpression"
    // Generator.g:941:1: postfixExpression returns [ASTExpression n] : nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* ;
    public final ASTExpression postfixExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nPrimExp = null;

        ASTExpression nPc = null;


         boolean arrow = false; 
        try {
            // Generator.g:943:1: (nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* )
            // Generator.g:944:5: nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            {
            pushFollow(FOLLOW_primaryExpression_in_postfixExpression4112);
            nPrimExp=primaryExpression();

            state._fsp--;

             n = nPrimExp; 
            // Generator.g:945:5: ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            loop77:
            do {
                int alt77=2;
                int LA77_0 = input.LA(1);

                if ( (LA77_0==DOT||LA77_0==ARROW) ) {
                    alt77=1;
                }


                switch (alt77) {
            	case 1 :
            	    // Generator.g:946:6: ( ARROW | DOT ) nPc= propertyCall[$n, arrow]
            	    {
            	    // Generator.g:946:6: ( ARROW | DOT )
            	    int alt76=2;
            	    int LA76_0 = input.LA(1);

            	    if ( (LA76_0==ARROW) ) {
            	        alt76=1;
            	    }
            	    else if ( (LA76_0==DOT) ) {
            	        alt76=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 76, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt76) {
            	        case 1 :
            	            // Generator.g:946:8: ARROW
            	            {
            	            match(input,ARROW,FOLLOW_ARROW_in_postfixExpression4130); 
            	             arrow = true; 

            	            }
            	            break;
            	        case 2 :
            	            // Generator.g:946:34: DOT
            	            {
            	            match(input,DOT,FOLLOW_DOT_in_postfixExpression4136); 
            	             arrow = false; 

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_propertyCall_in_postfixExpression4147);
            	    nPc=propertyCall(n, arrow);

            	    state._fsp--;

            	     n = nPc; 

            	    }
            	    break;

            	default :
            	    break loop77;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "postfixExpression"


    // $ANTLR start "primaryExpression"
    // Generator.g:962:1: primaryExpression returns [ASTExpression n] : (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? );
    public final ASTExpression primaryExpression() throws RecognitionException {
        ASTExpression n = null;

        Token id1=null;
        ASTExpression nLit = null;

        ASTExpression nPc = null;

        ASTExpression nExp = null;

        ASTExpression nIfExp = null;


        try {
            // Generator.g:963:1: (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? )
            int alt80=5;
            switch ( input.LA(1) ) {
            case INT:
            case REAL:
            case STRING:
            case HASH:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
                {
                alt80=1;
                }
                break;
            case IDENT:
                {
                int LA80_2 = input.LA(2);

                if ( (LA80_2==EOF||(LA80_2>=IDENT && LA80_2<=COLON_EQUAL)||(LA80_2>=LBRACK && LA80_2<=RBRACK)||(LA80_2>=RBRACE && LA80_2<=LESS)||(LA80_2>=EQUAL && LA80_2<=DOTDOT)||LA80_2==STAR||(LA80_2>=NOT_EQUAL && LA80_2<=BAR)||(LA80_2>=44 && LA80_2<=51)||LA80_2==53||(LA80_2>=55 && LA80_2<=56)||(LA80_2>=59 && LA80_2<=60)||(LA80_2>=62 && LA80_2<=64)||(LA80_2>=67 && LA80_2<=73)||LA80_2==75||LA80_2==77||(LA80_2>=79 && LA80_2<=84)||(LA80_2>=86 && LA80_2<=90)) ) {
                    alt80=2;
                }
                else if ( (LA80_2==DOT) ) {
                    int LA80_6 = input.LA(3);

                    if ( (LA80_6==92) ) {
                        alt80=5;
                    }
                    else if ( (LA80_6==IDENT||(LA80_6>=93 && LA80_6<=96)) ) {
                        alt80=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 80, 6, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 80, 2, input);

                    throw nvae;
                }
                }
                break;
            case 93:
            case 94:
            case 95:
            case 96:
                {
                alt80=2;
                }
                break;
            case LPAREN:
                {
                alt80=3;
                }
                break;
            case 49:
                {
                alt80=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 80, 0, input);

                throw nvae;
            }

            switch (alt80) {
                case 1 :
                    // Generator.g:964:7: nLit= literal
                    {
                    pushFollow(FOLLOW_literal_in_primaryExpression4187);
                    nLit=literal();

                    state._fsp--;

                     n = nLit; 

                    }
                    break;
                case 2 :
                    // Generator.g:965:7: nPc= propertyCall[null, false]
                    {
                    pushFollow(FOLLOW_propertyCall_in_primaryExpression4199);
                    nPc=propertyCall(null, false);

                    state._fsp--;

                     n = nPc; 

                    }
                    break;
                case 3 :
                    // Generator.g:966:7: LPAREN nExp= expression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression4210); 
                    pushFollow(FOLLOW_expression_in_primaryExpression4214);
                    nExp=expression();

                    state._fsp--;

                    match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression4216); 
                     n = nExp; 

                    }
                    break;
                case 4 :
                    // Generator.g:967:7: nIfExp= ifExpression
                    {
                    pushFollow(FOLLOW_ifExpression_in_primaryExpression4228);
                    nIfExp=ifExpression();

                    state._fsp--;

                     n = nIfExp; 

                    }
                    break;
                case 5 :
                    // Generator.g:969:7: id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )?
                    {
                    id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_primaryExpression4245); 
                    match(input,DOT,FOLLOW_DOT_in_primaryExpression4247); 
                    match(input,92,FOLLOW_92_in_primaryExpression4249); 
                    // Generator.g:969:36: ( LPAREN RPAREN )?
                    int alt78=2;
                    int LA78_0 = input.LA(1);

                    if ( (LA78_0==LPAREN) ) {
                        alt78=1;
                    }
                    switch (alt78) {
                        case 1 :
                            // Generator.g:969:38: LPAREN RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression4253); 
                            match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression4255); 

                            }
                            break;

                    }

                     n = new ASTAllInstancesExpression(id1); 
                    // Generator.g:971:7: ( AT 'pre' )?
                    int alt79=2;
                    int LA79_0 = input.LA(1);

                    if ( (LA79_0==AT) ) {
                        alt79=1;
                    }
                    switch (alt79) {
                        case 1 :
                            // Generator.g:971:9: AT 'pre'
                            {
                            match(input,AT,FOLLOW_AT_in_primaryExpression4276); 
                            match(input,69,FOLLOW_69_in_primaryExpression4278); 
                             n.setIsPre(); 

                            }
                            break;

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "primaryExpression"


    // $ANTLR start "propertyCall"
    // Generator.g:984:1: propertyCall[ASTExpression source, boolean followsArrow] returns [ASTExpression n] : ({...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] );
    public final ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExpQuery = null;

        ASTExpression nExpIterate = null;

        ASTOperationExpression nExpOperation = null;

        ASTTypeArgExpression nExpType = null;


        try {
            // Generator.g:985:1: ({...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] )
            int alt81=4;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA81_1 = input.LA(2);

                if ( (( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                    alt81=1;
                }
                else if ( (true) ) {
                    alt81=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 81, 1, input);

                    throw nvae;
                }
                }
                break;
            case 93:
                {
                alt81=2;
                }
                break;
            case 94:
            case 95:
            case 96:
                {
                alt81=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 81, 0, input);

                throw nvae;
            }

            switch (alt81) {
                case 1 :
                    // Generator.g:989:7: {...}?nExpQuery= queryExpression[source]
                    {
                    if ( !(( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                        throw new FailedPredicateException(input, "propertyCall", " org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) ");
                    }
                    pushFollow(FOLLOW_queryExpression_in_propertyCall4344);
                    nExpQuery=queryExpression(source);

                    state._fsp--;

                     n = nExpQuery; 

                    }
                    break;
                case 2 :
                    // Generator.g:991:7: nExpIterate= iterateExpression[source]
                    {
                    pushFollow(FOLLOW_iterateExpression_in_propertyCall4357);
                    nExpIterate=iterateExpression(source);

                    state._fsp--;

                     n = nExpIterate; 

                    }
                    break;
                case 3 :
                    // Generator.g:992:7: nExpOperation= operationExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_operationExpression_in_propertyCall4370);
                    nExpOperation=operationExpression(source, followsArrow);

                    state._fsp--;

                     n = nExpOperation; 

                    }
                    break;
                case 4 :
                    // Generator.g:993:7: nExpType= typeExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_typeExpression_in_propertyCall4383);
                    nExpType=typeExpression(source, followsArrow);

                    state._fsp--;

                     n = nExpType; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "propertyCall"


    // $ANTLR start "queryExpression"
    // Generator.g:1002:1: queryExpression[ASTExpression range] returns [ASTExpression n] : op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN ;
    public final ASTExpression queryExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTElemVarsDeclaration decls = null;

        ASTExpression nExp = null;


        ASTElemVarsDeclaration decl = new ASTElemVarsDeclaration(); 
        try {
            // Generator.g:1003:69: (op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN )
            // Generator.g:1004:5: op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN
            {
            op=(Token)match(input,IDENT,FOLLOW_IDENT_in_queryExpression4418); 
            match(input,LPAREN,FOLLOW_LPAREN_in_queryExpression4425); 
            // Generator.g:1006:5: (decls= elemVarsDeclaration BAR )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==IDENT) ) {
                int LA82_1 = input.LA(2);

                if ( (LA82_1==COMMA||LA82_1==COLON||LA82_1==BAR) ) {
                    alt82=1;
                }
            }
            switch (alt82) {
                case 1 :
                    // Generator.g:1006:7: decls= elemVarsDeclaration BAR
                    {
                    pushFollow(FOLLOW_elemVarsDeclaration_in_queryExpression4436);
                    decls=elemVarsDeclaration();

                    state._fsp--;

                    decl = decls;
                    match(input,BAR,FOLLOW_BAR_in_queryExpression4440); 

                    }
                    break;

            }

            pushFollow(FOLLOW_expression_in_queryExpression4451);
            nExp=expression();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_queryExpression4457); 
             n = new ASTQueryExpression(op, range, decl, nExp); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "queryExpression"


    // $ANTLR start "iterateExpression"
    // Generator.g:1020:1: iterateExpression[ASTExpression range] returns [ASTExpression n] : i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN ;
    public final ASTExpression iterateExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTElemVarsDeclaration decls = null;

        ASTVariableInitialization init = null;

        ASTExpression nExp = null;


        try {
            // Generator.g:1020:65: (i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN )
            // Generator.g:1021:5: i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN
            {
            i=(Token)match(input,93,FOLLOW_93_in_iterateExpression4489); 
            match(input,LPAREN,FOLLOW_LPAREN_in_iterateExpression4495); 
            pushFollow(FOLLOW_elemVarsDeclaration_in_iterateExpression4503);
            decls=elemVarsDeclaration();

            state._fsp--;

            match(input,SEMI,FOLLOW_SEMI_in_iterateExpression4505); 
            pushFollow(FOLLOW_variableInitialization_in_iterateExpression4513);
            init=variableInitialization();

            state._fsp--;

            match(input,BAR,FOLLOW_BAR_in_iterateExpression4515); 
            pushFollow(FOLLOW_expression_in_iterateExpression4523);
            nExp=expression();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_iterateExpression4529); 
             n = new ASTIterateExpression(i, range, decls, init, nExp); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "iterateExpression"


    // $ANTLR start "operationExpression"
    // Generator.g:1042:1: operationExpression[ASTExpression source, boolean followsArrow] returns [ASTOperationExpression n] : name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? ;
    public final ASTOperationExpression operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTOperationExpression n = null;

        Token name=null;
        Token rolename=null;
        ASTExpression e = null;


        try {
            // Generator.g:1044:1: (name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? )
            // Generator.g:1045:5: name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression4573); 
             n = new ASTOperationExpression(name, source, followsArrow); 
            // Generator.g:1048:5: ( LBRACK rolename= IDENT RBRACK )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==LBRACK) ) {
                alt83=1;
            }
            switch (alt83) {
                case 1 :
                    // Generator.g:1048:7: LBRACK rolename= IDENT RBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_operationExpression4589); 
                    rolename=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression4593); 
                    match(input,RBRACK,FOLLOW_RBRACK_in_operationExpression4595); 
                     n.setExplicitRolename(rolename); 

                    }
                    break;

            }

            // Generator.g:1050:5: ( AT 'pre' )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==AT) ) {
                alt84=1;
            }
            switch (alt84) {
                case 1 :
                    // Generator.g:1050:7: AT 'pre'
                    {
                    match(input,AT,FOLLOW_AT_in_operationExpression4608); 
                    match(input,69,FOLLOW_69_in_operationExpression4610); 
                     n.setIsPre(); 

                    }
                    break;

            }

            // Generator.g:1051:5: ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==LPAREN) ) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    // Generator.g:1052:7: LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_operationExpression4631); 
                     n.hasParentheses(); 
                    // Generator.g:1053:7: (e= expression ( COMMA e= expression )* )?
                    int alt86=2;
                    int LA86_0 = input.LA(1);

                    if ( ((LA86_0>=IDENT && LA86_0<=LPAREN)||LA86_0==INT||(LA86_0>=PLUS && LA86_0<=MINUS)||(LA86_0>=REAL && LA86_0<=HASH)||LA86_0==49||LA86_0==85||LA86_0==91||(LA86_0>=93 && LA86_0<=106)) ) {
                        alt86=1;
                    }
                    switch (alt86) {
                        case 1 :
                            // Generator.g:1054:7: e= expression ( COMMA e= expression )*
                            {
                            pushFollow(FOLLOW_expression_in_operationExpression4652);
                            e=expression();

                            state._fsp--;

                             n.addArg(e); 
                            // Generator.g:1055:7: ( COMMA e= expression )*
                            loop85:
                            do {
                                int alt85=2;
                                int LA85_0 = input.LA(1);

                                if ( (LA85_0==COMMA) ) {
                                    alt85=1;
                                }


                                switch (alt85) {
                            	case 1 :
                            	    // Generator.g:1055:9: COMMA e= expression
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_operationExpression4664); 
                            	    pushFollow(FOLLOW_expression_in_operationExpression4668);
                            	    e=expression();

                            	    state._fsp--;

                            	     n.addArg(e); 

                            	    }
                            	    break;

                            	default :
                            	    break loop85;
                                }
                            } while (true);


                            }
                            break;

                    }

                    match(input,RPAREN,FOLLOW_RPAREN_in_operationExpression4688); 

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "operationExpression"


    // $ANTLR start "typeExpression"
    // Generator.g:1067:1: typeExpression[ASTExpression source, boolean followsArrow] returns [ASTTypeArgExpression n] : ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN ;
    public final ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTTypeArgExpression n = null;

        ASTType t = null;


         Token opToken = null; 
        try {
            // Generator.g:1070:1: ( ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN )
            // Generator.g:1071:2: ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN
            {
             opToken = input.LT(1); 
            if ( (input.LA(1)>=94 && input.LA(1)<=96) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_typeExpression4747); 
            pushFollow(FOLLOW_type_in_typeExpression4751);
            t=type();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_typeExpression4753); 
             n = new ASTTypeArgExpression(opToken, source, t, followsArrow); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "typeExpression"


    // $ANTLR start "elemVarsDeclaration"
    // Generator.g:1082:1: elemVarsDeclaration returns [ASTElemVarsDeclaration n] : idListRes= idList ( COLON t= type )? ;
    public final ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException {
        ASTElemVarsDeclaration n = null;

        List idListRes = null;

        ASTType t = null;


         List idList; 
        try {
            // Generator.g:1084:1: (idListRes= idList ( COLON t= type )? )
            // Generator.g:1085:5: idListRes= idList ( COLON t= type )?
            {
            pushFollow(FOLLOW_idList_in_elemVarsDeclaration4792);
            idListRes=idList();

            state._fsp--;

            // Generator.g:1086:5: ( COLON t= type )?
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==COLON) ) {
                alt88=1;
            }
            switch (alt88) {
                case 1 :
                    // Generator.g:1086:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_elemVarsDeclaration4800); 
                    pushFollow(FOLLOW_type_in_elemVarsDeclaration4804);
                    t=type();

                    state._fsp--;


                    }
                    break;

            }

             n = new ASTElemVarsDeclaration(idListRes, t); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "elemVarsDeclaration"


    // $ANTLR start "variableInitialization"
    // Generator.g:1095:1: variableInitialization returns [ASTVariableInitialization n] : name= IDENT COLON t= type EQUAL e= expression ;
    public final ASTVariableInitialization variableInitialization() throws RecognitionException {
        ASTVariableInitialization n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // Generator.g:1096:1: (name= IDENT COLON t= type EQUAL e= expression )
            // Generator.g:1097:5: name= IDENT COLON t= type EQUAL e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableInitialization4839); 
            match(input,COLON,FOLLOW_COLON_in_variableInitialization4841); 
            pushFollow(FOLLOW_type_in_variableInitialization4845);
            t=type();

            state._fsp--;

            match(input,EQUAL,FOLLOW_EQUAL_in_variableInitialization4847); 
            pushFollow(FOLLOW_expression_in_variableInitialization4851);
            e=expression();

            state._fsp--;

             n = new ASTVariableInitialization(name, t, e); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "variableInitialization"


    // $ANTLR start "ifExpression"
    // Generator.g:1106:1: ifExpression returns [ASTExpression n] : i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' ;
    public final ASTExpression ifExpression() throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTExpression cond = null;

        ASTExpression t = null;

        ASTExpression e = null;


        try {
            // Generator.g:1107:1: (i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' )
            // Generator.g:1108:5: i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif'
            {
            i=(Token)match(input,49,FOLLOW_49_in_ifExpression4883); 
            pushFollow(FOLLOW_expression_in_ifExpression4887);
            cond=expression();

            state._fsp--;

            match(input,50,FOLLOW_50_in_ifExpression4889); 
            pushFollow(FOLLOW_expression_in_ifExpression4893);
            t=expression();

            state._fsp--;

            match(input,51,FOLLOW_51_in_ifExpression4895); 
            pushFollow(FOLLOW_expression_in_ifExpression4899);
            e=expression();

            state._fsp--;

            match(input,80,FOLLOW_80_in_ifExpression4901); 
             n = new ASTIfExpression(i, cond, t, e); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "ifExpression"


    // $ANTLR start "literal"
    // Generator.g:1126:1: literal returns [ASTExpression n] : (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral );
    public final ASTExpression literal() throws RecognitionException {
        ASTExpression n = null;

        Token t=null;
        Token f=null;
        Token i=null;
        Token r=null;
        Token s=null;
        Token enumLit=null;
        ASTCollectionLiteral nColIt = null;

        ASTEmptyCollectionLiteral nEColIt = null;

        ASTUndefinedLiteral nUndLit = null;

        ASTTupleLiteral nTupleLit = null;


        try {
            // Generator.g:1127:1: (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral )
            int alt89=10;
            switch ( input.LA(1) ) {
            case 97:
                {
                alt89=1;
                }
                break;
            case 98:
                {
                alt89=2;
                }
                break;
            case INT:
                {
                alt89=3;
                }
                break;
            case REAL:
                {
                alt89=4;
                }
                break;
            case STRING:
                {
                alt89=5;
                }
                break;
            case HASH:
                {
                alt89=6;
                }
                break;
            case 99:
            case 100:
            case 101:
                {
                alt89=7;
                }
                break;
            case 102:
                {
                alt89=8;
                }
                break;
            case 103:
            case 104:
            case 105:
                {
                alt89=9;
                }
                break;
            case 106:
                {
                alt89=10;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 89, 0, input);

                throw nvae;
            }

            switch (alt89) {
                case 1 :
                    // Generator.g:1128:7: t= 'true'
                    {
                    t=(Token)match(input,97,FOLLOW_97_in_literal4940); 
                     n = new ASTBooleanLiteral(true); 

                    }
                    break;
                case 2 :
                    // Generator.g:1129:7: f= 'false'
                    {
                    f=(Token)match(input,98,FOLLOW_98_in_literal4954); 
                     n = new ASTBooleanLiteral(false); 

                    }
                    break;
                case 3 :
                    // Generator.g:1130:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_literal4967); 
                     n = new ASTIntegerLiteral(i); 

                    }
                    break;
                case 4 :
                    // Generator.g:1131:7: r= REAL
                    {
                    r=(Token)match(input,REAL,FOLLOW_REAL_in_literal4982); 
                     n = new ASTRealLiteral(r); 

                    }
                    break;
                case 5 :
                    // Generator.g:1132:7: s= STRING
                    {
                    s=(Token)match(input,STRING,FOLLOW_STRING_in_literal4996); 
                     n = new ASTStringLiteral(s); 

                    }
                    break;
                case 6 :
                    // Generator.g:1133:7: HASH enumLit= IDENT
                    {
                    match(input,HASH,FOLLOW_HASH_in_literal5006); 
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal5010); 
                     n = new ASTEnumLiteral(enumLit); 

                    }
                    break;
                case 7 :
                    // Generator.g:1134:7: nColIt= collectionLiteral
                    {
                    pushFollow(FOLLOW_collectionLiteral_in_literal5022);
                    nColIt=collectionLiteral();

                    state._fsp--;

                     n = nColIt; 

                    }
                    break;
                case 8 :
                    // Generator.g:1135:7: nEColIt= emptyCollectionLiteral
                    {
                    pushFollow(FOLLOW_emptyCollectionLiteral_in_literal5034);
                    nEColIt=emptyCollectionLiteral();

                    state._fsp--;

                     n = nEColIt; 

                    }
                    break;
                case 9 :
                    // Generator.g:1136:7: nUndLit= undefinedLiteral
                    {
                    pushFollow(FOLLOW_undefinedLiteral_in_literal5046);
                    nUndLit=undefinedLiteral();

                    state._fsp--;

                    n = nUndLit; 

                    }
                    break;
                case 10 :
                    // Generator.g:1137:7: nTupleLit= tupleLiteral
                    {
                    pushFollow(FOLLOW_tupleLiteral_in_literal5058);
                    nTupleLit=tupleLiteral();

                    state._fsp--;

                    n = nTupleLit; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "literal"


    // $ANTLR start "collectionLiteral"
    // Generator.g:1145:1: collectionLiteral returns [ASTCollectionLiteral n] : ( 'Set' | 'Sequence' | 'Bag' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE ;
    public final ASTCollectionLiteral collectionLiteral() throws RecognitionException {
        ASTCollectionLiteral n = null;

        ASTCollectionItem ci = null;


         Token op = null; 
        try {
            // Generator.g:1147:1: ( ( 'Set' | 'Sequence' | 'Bag' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE )
            // Generator.g:1148:5: ( 'Set' | 'Sequence' | 'Bag' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE
            {
             op = input.LT(1); 
            if ( (input.LA(1)>=99 && input.LA(1)<=101) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

             n = new ASTCollectionLiteral(op); 
            match(input,LBRACE,FOLLOW_LBRACE_in_collectionLiteral5121); 
            // Generator.g:1152:5: (ci= collectionItem ( COMMA ci= collectionItem )* )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( ((LA91_0>=IDENT && LA91_0<=LPAREN)||LA91_0==INT||(LA91_0>=PLUS && LA91_0<=MINUS)||(LA91_0>=REAL && LA91_0<=HASH)||LA91_0==49||LA91_0==85||LA91_0==91||(LA91_0>=93 && LA91_0<=106)) ) {
                alt91=1;
            }
            switch (alt91) {
                case 1 :
                    // Generator.g:1153:7: ci= collectionItem ( COMMA ci= collectionItem )*
                    {
                    pushFollow(FOLLOW_collectionItem_in_collectionLiteral5138);
                    ci=collectionItem();

                    state._fsp--;

                     n.addItem(ci); 
                    // Generator.g:1154:7: ( COMMA ci= collectionItem )*
                    loop90:
                    do {
                        int alt90=2;
                        int LA90_0 = input.LA(1);

                        if ( (LA90_0==COMMA) ) {
                            alt90=1;
                        }


                        switch (alt90) {
                    	case 1 :
                    	    // Generator.g:1154:9: COMMA ci= collectionItem
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_collectionLiteral5151); 
                    	    pushFollow(FOLLOW_collectionItem_in_collectionLiteral5155);
                    	    ci=collectionItem();

                    	    state._fsp--;

                    	     n.addItem(ci); 

                    	    }
                    	    break;

                    	default :
                    	    break loop90;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RBRACE,FOLLOW_RBRACE_in_collectionLiteral5174); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "collectionLiteral"


    // $ANTLR start "collectionItem"
    // Generator.g:1163:1: collectionItem returns [ASTCollectionItem n] : e= expression ( DOTDOT e= expression )? ;
    public final ASTCollectionItem collectionItem() throws RecognitionException {
        ASTCollectionItem n = null;

        ASTExpression e = null;


         n = new ASTCollectionItem(); 
        try {
            // Generator.g:1165:1: (e= expression ( DOTDOT e= expression )? )
            // Generator.g:1166:5: e= expression ( DOTDOT e= expression )?
            {
            pushFollow(FOLLOW_expression_in_collectionItem5203);
            e=expression();

            state._fsp--;

             n.setFirst(e); 
            // Generator.g:1167:5: ( DOTDOT e= expression )?
            int alt92=2;
            int LA92_0 = input.LA(1);

            if ( (LA92_0==DOTDOT) ) {
                alt92=1;
            }
            switch (alt92) {
                case 1 :
                    // Generator.g:1167:7: DOTDOT e= expression
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_collectionItem5214); 
                    pushFollow(FOLLOW_expression_in_collectionItem5218);
                    e=expression();

                    state._fsp--;

                     n.setSecond(e); 

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "collectionItem"


    // $ANTLR start "emptyCollectionLiteral"
    // Generator.g:1177:1: emptyCollectionLiteral returns [ASTEmptyCollectionLiteral n] : 'oclEmpty' LPAREN t= collectionType RPAREN ;
    public final ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException {
        ASTEmptyCollectionLiteral n = null;

        ASTCollectionType t = null;


        try {
            // Generator.g:1178:1: ( 'oclEmpty' LPAREN t= collectionType RPAREN )
            // Generator.g:1179:5: 'oclEmpty' LPAREN t= collectionType RPAREN
            {
            match(input,102,FOLLOW_102_in_emptyCollectionLiteral5247); 
            match(input,LPAREN,FOLLOW_LPAREN_in_emptyCollectionLiteral5249); 
            pushFollow(FOLLOW_collectionType_in_emptyCollectionLiteral5253);
            t=collectionType();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_emptyCollectionLiteral5255); 
             n = new ASTEmptyCollectionLiteral(t); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "emptyCollectionLiteral"


    // $ANTLR start "undefinedLiteral"
    // Generator.g:1190:1: undefinedLiteral returns [ASTUndefinedLiteral n] : ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' );
    public final ASTUndefinedLiteral undefinedLiteral() throws RecognitionException {
        ASTUndefinedLiteral n = null;

        ASTType t = null;


        try {
            // Generator.g:1191:1: ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' )
            int alt93=3;
            switch ( input.LA(1) ) {
            case 103:
                {
                alt93=1;
                }
                break;
            case 104:
                {
                alt93=2;
                }
                break;
            case 105:
                {
                alt93=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 93, 0, input);

                throw nvae;
            }

            switch (alt93) {
                case 1 :
                    // Generator.g:1192:5: 'oclUndefined' LPAREN t= type RPAREN
                    {
                    match(input,103,FOLLOW_103_in_undefinedLiteral5285); 
                    match(input,LPAREN,FOLLOW_LPAREN_in_undefinedLiteral5287); 
                    pushFollow(FOLLOW_type_in_undefinedLiteral5291);
                    t=type();

                    state._fsp--;

                    match(input,RPAREN,FOLLOW_RPAREN_in_undefinedLiteral5293); 
                     n = new ASTUndefinedLiteral(t); 

                    }
                    break;
                case 2 :
                    // Generator.g:1195:5: 'Undefined'
                    {
                    match(input,104,FOLLOW_104_in_undefinedLiteral5307); 
                     n = new ASTUndefinedLiteral(); 

                    }
                    break;
                case 3 :
                    // Generator.g:1198:5: 'null'
                    {
                    match(input,105,FOLLOW_105_in_undefinedLiteral5321); 
                     n = new ASTUndefinedLiteral(); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "undefinedLiteral"


    // $ANTLR start "tupleLiteral"
    // Generator.g:1207:1: tupleLiteral returns [ASTTupleLiteral n] : 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE ;
    public final ASTTupleLiteral tupleLiteral() throws RecognitionException {
        ASTTupleLiteral n = null;

        ASTTupleItem ti = null;


         List tiList = new ArrayList(); 
        try {
            // Generator.g:1209:1: ( 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE )
            // Generator.g:1210:5: 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE
            {
            match(input,106,FOLLOW_106_in_tupleLiteral5355); 
            match(input,LBRACE,FOLLOW_LBRACE_in_tupleLiteral5361); 
            pushFollow(FOLLOW_tupleItem_in_tupleLiteral5369);
            ti=tupleItem();

            state._fsp--;

             tiList.add(ti); 
            // Generator.g:1213:5: ( COMMA ti= tupleItem )*
            loop94:
            do {
                int alt94=2;
                int LA94_0 = input.LA(1);

                if ( (LA94_0==COMMA) ) {
                    alt94=1;
                }


                switch (alt94) {
            	case 1 :
            	    // Generator.g:1213:7: COMMA ti= tupleItem
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleLiteral5380); 
            	    pushFollow(FOLLOW_tupleItem_in_tupleLiteral5384);
            	    ti=tupleItem();

            	    state._fsp--;

            	     tiList.add(ti); 

            	    }
            	    break;

            	default :
            	    break loop94;
                }
            } while (true);

            match(input,RBRACE,FOLLOW_RBRACE_in_tupleLiteral5395); 
             n = new ASTTupleLiteral(tiList); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "tupleLiteral"


    // $ANTLR start "tupleItem"
    // Generator.g:1221:1: tupleItem returns [ASTTupleItem n] : name= IDENT ( COLON | EQUAL ) e= expression ;
    public final ASTTupleItem tupleItem() throws RecognitionException {
        ASTTupleItem n = null;

        Token name=null;
        ASTExpression e = null;


        try {
            // Generator.g:1222:1: (name= IDENT ( COLON | EQUAL ) e= expression )
            // Generator.g:1223:5: name= IDENT ( COLON | EQUAL ) e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tupleItem5426); 
            if ( (input.LA(1)>=COLON && input.LA(1)<=EQUAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            pushFollow(FOLLOW_expression_in_tupleItem5436);
            e=expression();

            state._fsp--;

             n = new ASTTupleItem(name, e); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "tupleItem"


    // $ANTLR start "type"
    // Generator.g:1234:1: type returns [ASTType n] : (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) ;
    public final ASTType type() throws RecognitionException {
        ASTType n = null;

        ASTSimpleType nTSimple = null;

        ASTCollectionType nTCollection = null;

        ASTTupleType nTTuple = null;


         Token tok = null; 
        try {
            // Generator.g:1236:1: ( (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) )
            // Generator.g:1237:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            {
             tok = input.LT(1); /* remember start of type */ 
            // Generator.g:1238:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            int alt95=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt95=1;
                }
                break;
            case 99:
            case 100:
            case 101:
            case 107:
                {
                alt95=2;
                }
                break;
            case 106:
                {
                alt95=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 95, 0, input);

                throw nvae;
            }

            switch (alt95) {
                case 1 :
                    // Generator.g:1239:7: nTSimple= simpleType
                    {
                    pushFollow(FOLLOW_simpleType_in_type5489);
                    nTSimple=simpleType();

                    state._fsp--;

                     n = nTSimple; n.setStartToken(tok); 

                    }
                    break;
                case 2 :
                    // Generator.g:1240:7: nTCollection= collectionType
                    {
                    pushFollow(FOLLOW_collectionType_in_type5501);
                    nTCollection=collectionType();

                    state._fsp--;

                     n = nTCollection; n.setStartToken(tok); 

                    }
                    break;
                case 3 :
                    // Generator.g:1241:7: nTTuple= tupleType
                    {
                    pushFollow(FOLLOW_tupleType_in_type5513);
                    nTTuple=tupleType();

                    state._fsp--;

                     n = nTTuple; n.setStartToken(tok); 

                    }
                    break;

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "type"


    // $ANTLR start "typeOnly"
    // Generator.g:1246:1: typeOnly returns [ASTType n] : nT= type EOF ;
    public final ASTType typeOnly() throws RecognitionException {
        ASTType n = null;

        ASTType nT = null;


        try {
            // Generator.g:1247:1: (nT= type EOF )
            // Generator.g:1248:5: nT= type EOF
            {
            pushFollow(FOLLOW_type_in_typeOnly5545);
            nT=type();

            state._fsp--;

            match(input,EOF,FOLLOW_EOF_in_typeOnly5547); 
             n = nT; 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "typeOnly"


    // $ANTLR start "simpleType"
    // Generator.g:1258:1: simpleType returns [ASTSimpleType n] : name= IDENT ;
    public final ASTSimpleType simpleType() throws RecognitionException {
        ASTSimpleType n = null;

        Token name=null;

        try {
            // Generator.g:1259:1: (name= IDENT )
            // Generator.g:1260:5: name= IDENT
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_simpleType5575); 
             n = new ASTSimpleType(name); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "simpleType"


    // $ANTLR start "collectionType"
    // Generator.g:1268:1: collectionType returns [ASTCollectionType n] : ( 'Collection' | 'Set' | 'Sequence' | 'Bag' ) LPAREN elemType= type RPAREN ;
    public final ASTCollectionType collectionType() throws RecognitionException {
        ASTCollectionType n = null;

        ASTType elemType = null;


         Token op = null; 
        try {
            // Generator.g:1270:1: ( ( 'Collection' | 'Set' | 'Sequence' | 'Bag' ) LPAREN elemType= type RPAREN )
            // Generator.g:1271:5: ( 'Collection' | 'Set' | 'Sequence' | 'Bag' ) LPAREN elemType= type RPAREN
            {
             op = input.LT(1); 
            if ( (input.LA(1)>=99 && input.LA(1)<=101)||input.LA(1)==107 ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_collectionType5636); 
            pushFollow(FOLLOW_type_in_collectionType5640);
            elemType=type();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_collectionType5642); 
             n = new ASTCollectionType(op, elemType); n.setStartToken(op);

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "collectionType"


    // $ANTLR start "tupleType"
    // Generator.g:1281:1: tupleType returns [ASTTupleType n] : 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN ;
    public final ASTTupleType tupleType() throws RecognitionException {
        ASTTupleType n = null;

        ASTTuplePart tp = null;


         List tpList = new ArrayList(); 
        try {
            // Generator.g:1283:1: ( 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN )
            // Generator.g:1284:5: 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN
            {
            match(input,106,FOLLOW_106_in_tupleType5676); 
            match(input,LPAREN,FOLLOW_LPAREN_in_tupleType5678); 
            pushFollow(FOLLOW_tuplePart_in_tupleType5687);
            tp=tuplePart();

            state._fsp--;

             tpList.add(tp); 
            // Generator.g:1286:5: ( COMMA tp= tuplePart )*
            loop96:
            do {
                int alt96=2;
                int LA96_0 = input.LA(1);

                if ( (LA96_0==COMMA) ) {
                    alt96=1;
                }


                switch (alt96) {
            	case 1 :
            	    // Generator.g:1286:7: COMMA tp= tuplePart
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleType5698); 
            	    pushFollow(FOLLOW_tuplePart_in_tupleType5702);
            	    tp=tuplePart();

            	    state._fsp--;

            	     tpList.add(tp); 

            	    }
            	    break;

            	default :
            	    break loop96;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_tupleType5714); 
             n = new ASTTupleType(tpList); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "tupleType"


    // $ANTLR start "tuplePart"
    // Generator.g:1295:1: tuplePart returns [ASTTuplePart n] : name= IDENT COLON t= type ;
    public final ASTTuplePart tuplePart() throws RecognitionException {
        ASTTuplePart n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Generator.g:1296:1: (name= IDENT COLON t= type )
            // Generator.g:1297:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tuplePart5746); 
            match(input,COLON,FOLLOW_COLON_in_tuplePart5748); 
            pushFollow(FOLLOW_type_in_tuplePart5752);
            t=type();

            state._fsp--;

             n = new ASTTuplePart(name, t); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "tuplePart"

    // Delegated rules


 

    public static final BitSet FOLLOW_invariant_in_invariantListOnly79 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_EOF_in_invariantListOnly90 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_procedure_in_procedureListOnly135 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_EOF_in_procedureListOnly150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_procedure178 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_procedure182 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_procedure184 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_variableDeclarationList_in_procedure188 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_procedure190 = new BitSet(new long[]{0x0000300000000000L});
    public static final BitSet FOLLOW_44_in_procedure198 = new BitSet(new long[]{0x0000000000000090L});
    public static final BitSet FOLLOW_variableDeclarationList_in_procedure202 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_in_procedure204 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_45_in_procedure213 = new BitSet(new long[]{0x0002C00000000810L});
    public static final BitSet FOLLOW_instructionList_in_procedure217 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_procedure219 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_in_procedure221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableDeclaration_in_variableDeclarationList259 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_variableDeclarationList270 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclaration_in_variableDeclarationList274 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_instruction_in_instructionList318 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_in_instructionList320 = new BitSet(new long[]{0x0002800000000812L});
    public static final BitSet FOLLOW_variableAssignment_in_instruction355 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attributeAssignment_in_instruction370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_loop_in_instruction384 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicInstruction_in_instruction401 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifThenElse_in_instruction415 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableAssignment445 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_variableAssignment447 = new BitSet(new long[]{0x0000000000000810L});
    public static final BitSet FOLLOW_valueInstruction_in_variableAssignment451 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oclExpression_in_attributeAssignment483 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_DOT_in_attributeAssignment485 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_attributeAssignment489 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_attributeAssignment497 = new BitSet(new long[]{0x0000000000000810L});
    public static final BitSet FOLLOW_valueInstruction_in_attributeAssignment501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_loop533 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclaration_in_loop537 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_loop539 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_oclExpression_in_loop543 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_45_in_loop545 = new BitSet(new long[]{0x0002C00000000810L});
    public static final BitSet FOLLOW_instructionList_in_loop556 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_loop558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_ifThenElse594 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_oclExpression_in_ifThenElse598 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_ifThenElse609 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_45_in_ifThenElse611 = new BitSet(new long[]{0x0002C00000000810L});
    public static final BitSet FOLLOW_instructionList_in_ifThenElse615 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_ifThenElse617 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_51_in_ifThenElse628 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_45_in_ifThenElse630 = new BitSet(new long[]{0x0002C00000000810L});
    public static final BitSet FOLLOW_instructionList_in_ifThenElse634 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_ifThenElse636 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicInstruction_in_valueInstruction676 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oclExpression_in_valueInstruction690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_atomicInstruction720 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_atomicInstruction724 = new BitSet(new long[]{0x0000000000000850L});
    public static final BitSet FOLLOW_instructionParameter_in_atomicInstruction738 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_COMMA_in_atomicInstruction756 = new BitSet(new long[]{0x0000000000000810L});
    public static final BitSet FOLLOW_instructionParameter_in_atomicInstruction760 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RPAREN_in_atomicInstruction808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oclExpression_in_instructionParameter836 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_instrParameterIdent_in_instructionParameter848 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_instrParameterIdent876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACK_in_oclExpression904 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_oclExpression908 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RBRACK_in_oclExpression910 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_procedureCallOnly945 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_procedureCallOnly953 = new BitSet(new long[]{0x000200070C080070L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_procedureCallOnly964 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_COMMA_in_procedureCallOnly974 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_procedureCallOnly978 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RPAREN_in_procedureCallOnly992 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_procedureCallOnly998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_model1025 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_model1029 = new BitSet(new long[]{0xD9E0000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_enumTypeDefinition_in_model1041 = new BitSet(new long[]{0xD9E0000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_generalClassDefinition_in_model1058 = new BitSet(new long[]{0xD9A0000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_associationDefinition_in_model1075 = new BitSet(new long[]{0xD9A0000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_53_in_model1091 = new BitSet(new long[]{0xD9A0000000000000L,0x0000000000000009L});
    public static final BitSet FOLLOW_invariant_in_model1109 = new BitSet(new long[]{0xD9A0000000000000L,0x0000000000000009L});
    public static final BitSet FOLLOW_prePost_in_model1130 = new BitSet(new long[]{0xD9A0000000000000L,0x0000000000000009L});
    public static final BitSet FOLLOW_EOF_in_model1171 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_enumTypeDefinition1198 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_enumTypeDefinition1202 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_enumTypeDefinition1204 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_enumTypeDefinition1208 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RBRACE_in_enumTypeDefinition1210 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_enumTypeDefinition1214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_generalClassDefinition1253 = new BitSet(new long[]{0x1980000000000000L});
    public static final BitSet FOLLOW_classDefinition_in_generalClassDefinition1271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_associationClassDefinition_in_generalClassDefinition1291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_classDefinition1330 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_classDefinition1334 = new BitSet(new long[]{0x0620400000008000L});
    public static final BitSet FOLLOW_LESS_in_classDefinition1344 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_classDefinition1348 = new BitSet(new long[]{0x0620400000000000L});
    public static final BitSet FOLLOW_57_in_classDefinition1361 = new BitSet(new long[]{0x0420400000000010L});
    public static final BitSet FOLLOW_attributeDefinition_in_classDefinition1374 = new BitSet(new long[]{0x0420400000000010L});
    public static final BitSet FOLLOW_58_in_classDefinition1395 = new BitSet(new long[]{0x0020400000000010L});
    public static final BitSet FOLLOW_operationDefinition_in_classDefinition1408 = new BitSet(new long[]{0x0020400000000010L});
    public static final BitSet FOLLOW_53_in_classDefinition1429 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_invariantClause_in_classDefinition1449 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_46_in_classDefinition1473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_associationClassDefinition1506 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationClassDefinition1532 = new BitSet(new long[]{0x2000000000008000L});
    public static final BitSet FOLLOW_LESS_in_associationClassDefinition1542 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_associationClassDefinition1546 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_associationClassDefinition1557 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_associationEnd_in_associationClassDefinition1565 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_associationEnd_in_associationClassDefinition1577 = new BitSet(new long[]{0xC620400000000010L});
    public static final BitSet FOLLOW_57_in_associationClassDefinition1590 = new BitSet(new long[]{0xC420400000000010L});
    public static final BitSet FOLLOW_attributeDefinition_in_associationClassDefinition1603 = new BitSet(new long[]{0xC420400000000010L});
    public static final BitSet FOLLOW_58_in_associationClassDefinition1624 = new BitSet(new long[]{0xC020400000000010L});
    public static final BitSet FOLLOW_operationDefinition_in_associationClassDefinition1637 = new BitSet(new long[]{0xC020400000000010L});
    public static final BitSet FOLLOW_53_in_associationClassDefinition1658 = new BitSet(new long[]{0xC000400000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_invariantClause_in_associationClassDefinition1678 = new BitSet(new long[]{0xC000400000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_set_in_associationClassDefinition1712 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_associationClassDefinition1741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_attributeDefinition1770 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_attributeDefinition1772 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_attributeDefinition1776 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_attributeDefinition1780 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationDefinition1818 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_paramList_in_operationDefinition1826 = new BitSet(new long[]{0x0000200000030082L,0x0000000000000060L});
    public static final BitSet FOLLOW_COLON_in_operationDefinition1834 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_operationDefinition1838 = new BitSet(new long[]{0x0000200000020082L,0x0000000000000060L});
    public static final BitSet FOLLOW_EQUAL_in_operationDefinition1848 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_operationDefinition1852 = new BitSet(new long[]{0x0000200000000082L,0x0000000000000060L});
    public static final BitSet FOLLOW_45_in_operationDefinition1860 = new BitSet(new long[]{0x0002D00000000000L,0x000000000012AB80L});
    public static final BitSet FOLLOW_alActionList_in_operationDefinition1864 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_operationDefinition1866 = new BitSet(new long[]{0x0000000000000082L,0x0000000000000060L});
    public static final BitSet FOLLOW_prePostClause_in_operationDefinition1885 = new BitSet(new long[]{0x0000000000000082L,0x0000000000000060L});
    public static final BitSet FOLLOW_SEMI_in_operationDefinition1898 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_associationDefinition1934 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationDefinition1959 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_associationDefinition1967 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_associationEnd_in_associationDefinition1975 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_associationEnd_in_associationDefinition1987 = new BitSet(new long[]{0x0000400000000010L});
    public static final BitSet FOLLOW_46_in_associationDefinition1998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd2024 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LBRACK_in_associationEnd2026 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_multiplicity_in_associationEnd2030 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RBRACK_in_associationEnd2032 = new BitSet(new long[]{0x0000000000000082L,0x0000000000000006L});
    public static final BitSet FOLLOW_65_in_associationEnd2048 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd2052 = new BitSet(new long[]{0x0000000000000082L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_associationEnd2065 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_associationEnd2078 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity2113 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_multiplicity2123 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity2127 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange2156 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_DOTDOT_in_multiplicityRange2166 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange2170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_multiplicitySpec2204 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_multiplicitySpec2214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_67_in_invariant2255 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_invariant2265 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_invariant2267 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_simpleType_in_invariant2282 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_invariantClause_in_invariant2294 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_invariantClause2324 = new BitSet(new long[]{0x0000000000010010L});
    public static final BitSet FOLLOW_IDENT_in_invariantClause2330 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_invariantClause2335 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_invariantClause2339 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_67_in_prePost2369 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_prePost2373 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_COLON_COLON_in_prePost2375 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_prePost2379 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_paramList_in_prePost2383 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000060L});
    public static final BitSet FOLLOW_COLON_in_prePost2387 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_prePost2391 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000060L});
    public static final BitSet FOLLOW_prePostClause_in_prePost2410 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000060L});
    public static final BitSet FOLLOW_set_in_prePostClause2449 = new BitSet(new long[]{0x0000000000010010L});
    public static final BitSet FOLLOW_IDENT_in_prePostClause2464 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_prePostClause2469 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_prePostClause2473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alAction_in_alActionList2506 = new BitSet(new long[]{0x0002900000000002L,0x000000000012AB80L});
    public static final BitSet FOLLOW_alCreateVar_in_alAction2532 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alDelete_in_alAction2544 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alSet_in_alAction2556 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alSetCreate_in_alAction2568 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alInsert_in_alAction2580 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alDestroy_in_alAction2592 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alIf_in_alAction2604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alWhile_in_alAction2616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alFor_in_alAction2628 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alExec_in_alAction2640 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_alCreateVar2659 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alCreateVar2667 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_alCreateVar2669 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_alCreateVar2673 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_alSet2695 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_alSet2699 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_alSet2701 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_alSet2705 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_alSetCreate2729 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_alSetCreate2733 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_alSetCreate2735 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alSetCreate2741 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alSetCreate2745 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_alSetCreate2754 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_alSetCreate2758 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_alInsert2789 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_alInsert2791 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_alInsert2800 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_alInsert2804 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_alInsert2812 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_COMMA_in_alInsert2818 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_alInsert2822 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RPAREN_in_alInsert2834 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_alInsert2836 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alInsert2840 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_alDelete2872 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_alDelete2874 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_alDelete2882 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_alDelete2886 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_alDelete2894 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_COMMA_in_alDelete2900 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_alDelete2904 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RPAREN_in_alDelete2915 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_alDelete2917 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alDelete2921 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_alDestroy2950 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_alDestroy2954 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_alIf2978 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_alIf2982 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_alIf2986 = new BitSet(new long[]{0x000A900000000000L,0x000000000013AB80L});
    public static final BitSet FOLLOW_alActionList_in_alIf2990 = new BitSet(new long[]{0x0008000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_51_in_alIf2994 = new BitSet(new long[]{0x0002900000000000L,0x000000000013AB80L});
    public static final BitSet FOLLOW_alActionList_in_alIf2998 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_alIf3003 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_alWhile3022 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_alWhile3026 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_alWhile3030 = new BitSet(new long[]{0x0002900000000000L,0x00000000001AAB80L});
    public static final BitSet FOLLOW_alActionList_in_alWhile3036 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_alWhile3039 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_alFor3058 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alFor3062 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_alFor3064 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_alFor3068 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_alFor3070 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_alFor3074 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_alFor3078 = new BitSet(new long[]{0x0002900000000010L,0x000000000012AB80L});
    public static final BitSet FOLLOW_alActionList_in_alFor3084 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alFor3091 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_alExec3111 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_alExec3115 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_expressionOnly3150 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_expressionOnly3152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_85_in_expression3200 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_expression3204 = new BitSet(new long[]{0x0000000000030000L});
    public static final BitSet FOLLOW_COLON_in_expression3208 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_expression3212 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_EQUAL_in_expression3217 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_expression3221 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_expression3223 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_conditionalImpliesExpression_in_expression3248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_paramList3281 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList3298 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_COMMA_in_paramList3310 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList3314 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RPAREN_in_paramList3334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_idList3363 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_idList3373 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_idList3377 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_IDENT_in_variableDeclaration3408 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_variableDeclaration3410 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_variableDeclaration3414 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3450 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_conditionalImpliesExpression3463 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3467 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3512 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_conditionalOrExpression3525 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3529 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3573 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_88_in_conditionalXOrExpression3586 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3590 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression3634 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_conditionalAndExpression3647 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression3651 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression3699 = new BitSet(new long[]{0x0000000000420002L});
    public static final BitSet FOLLOW_set_in_equalityExpression3718 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression3728 = new BitSet(new long[]{0x0000000000420002L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression3777 = new BitSet(new long[]{0x0000000003808002L});
    public static final BitSet FOLLOW_set_in_relationalExpression3795 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression3813 = new BitSet(new long[]{0x0000000003808002L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3863 = new BitSet(new long[]{0x000000000C000002L});
    public static final BitSet FOLLOW_set_in_additiveExpression3881 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3891 = new BitSet(new long[]{0x000000000C000002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3941 = new BitSet(new long[]{0x0000000010100002L,0x0000000004000000L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression3959 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3973 = new BitSet(new long[]{0x0000000010100002L,0x0000000004000000L});
    public static final BitSet FOLLOW_set_in_unaryExpression4035 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression4059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpression_in_unaryExpression4079 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_postfixExpression4112 = new BitSet(new long[]{0x0000000020000402L});
    public static final BitSet FOLLOW_ARROW_in_postfixExpression4130 = new BitSet(new long[]{0x0000000000000010L,0x00000001E0000000L});
    public static final BitSet FOLLOW_DOT_in_postfixExpression4136 = new BitSet(new long[]{0x0000000000000010L,0x00000001E0000000L});
    public static final BitSet FOLLOW_propertyCall_in_postfixExpression4147 = new BitSet(new long[]{0x0000000020000402L});
    public static final BitSet FOLLOW_literal_in_primaryExpression4187 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyCall_in_primaryExpression4199 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression4210 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_primaryExpression4214 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression4216 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifExpression_in_primaryExpression4228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_primaryExpression4245 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_DOT_in_primaryExpression4247 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_primaryExpression4249 = new BitSet(new long[]{0x0000000040000022L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression4253 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression4255 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_AT_in_primaryExpression4276 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_primaryExpression4278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_queryExpression_in_propertyCall4344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterateExpression_in_propertyCall4357 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationExpression_in_propertyCall4370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeExpression_in_propertyCall4383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_queryExpression4418 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_queryExpression4425 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_queryExpression4436 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_BAR_in_queryExpression4440 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_queryExpression4451 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_queryExpression4457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_iterateExpression4489 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_iterateExpression4495 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_iterateExpression4503 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_in_iterateExpression4505 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableInitialization_in_iterateExpression4513 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_BAR_in_iterateExpression4515 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_iterateExpression4523 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_iterateExpression4529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression4573 = new BitSet(new long[]{0x0000000040000822L});
    public static final BitSet FOLLOW_LBRACK_in_operationExpression4589 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression4593 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RBRACK_in_operationExpression4595 = new BitSet(new long[]{0x0000000040000022L});
    public static final BitSet FOLLOW_AT_in_operationExpression4608 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_operationExpression4610 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_LPAREN_in_operationExpression4631 = new BitSet(new long[]{0x000200070C080070L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_operationExpression4652 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_COMMA_in_operationExpression4664 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_operationExpression4668 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RPAREN_in_operationExpression4688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_typeExpression4731 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_typeExpression4747 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_typeExpression4751 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_typeExpression4753 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_elemVarsDeclaration4792 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_COLON_in_elemVarsDeclaration4800 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_elemVarsDeclaration4804 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableInitialization4839 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_variableInitialization4841 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_variableInitialization4845 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_EQUAL_in_variableInitialization4847 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_variableInitialization4851 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_ifExpression4883 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_ifExpression4887 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_ifExpression4889 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_ifExpression4893 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_ifExpression4895 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_ifExpression4899 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_ifExpression4901 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_97_in_literal4940 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_literal4954 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_literal4967 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REAL_in_literal4982 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal4996 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_literal5006 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_literal5010 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionLiteral_in_literal5022 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyCollectionLiteral_in_literal5034 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedLiteral_in_literal5046 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleLiteral_in_literal5058 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionLiteral5096 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_collectionLiteral5121 = new BitSet(new long[]{0x000200070C084030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral5138 = new BitSet(new long[]{0x0000000000004100L});
    public static final BitSet FOLLOW_COMMA_in_collectionLiteral5151 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral5155 = new BitSet(new long[]{0x0000000000004100L});
    public static final BitSet FOLLOW_RBRACE_in_collectionLiteral5174 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_collectionItem5203 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_DOTDOT_in_collectionItem5214 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_collectionItem5218 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_102_in_emptyCollectionLiteral5247 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_emptyCollectionLiteral5249 = new BitSet(new long[]{0x0000000000000000L,0x0000083800000000L});
    public static final BitSet FOLLOW_collectionType_in_emptyCollectionLiteral5253 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_emptyCollectionLiteral5255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_undefinedLiteral5285 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_undefinedLiteral5287 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_undefinedLiteral5291 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_undefinedLiteral5293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_104_in_undefinedLiteral5307 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_105_in_undefinedLiteral5321 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_106_in_tupleLiteral5355 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_tupleLiteral5361 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral5369 = new BitSet(new long[]{0x0000000000004100L});
    public static final BitSet FOLLOW_COMMA_in_tupleLiteral5380 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral5384 = new BitSet(new long[]{0x0000000000004100L});
    public static final BitSet FOLLOW_RBRACE_in_tupleLiteral5395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tupleItem5426 = new BitSet(new long[]{0x0000000000030000L});
    public static final BitSet FOLLOW_set_in_tupleItem5428 = new BitSet(new long[]{0x000200070C080030L,0x000007FFE8200000L});
    public static final BitSet FOLLOW_expression_in_tupleItem5436 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleType_in_type5489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_type5501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleType_in_type5513 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeOnly5545 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_typeOnly5547 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_simpleType5575 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionType5613 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_collectionType5636 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_collectionType5640 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_collectionType5642 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_106_in_tupleType5676 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_tupleType5678 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType5687 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_COMMA_in_tupleType5698 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType5702 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RPAREN_in_tupleType5714 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tuplePart5746 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_tuplePart5748 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_tuplePart5752 = new BitSet(new long[]{0x0000000000000002L});

}