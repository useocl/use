// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 Generator.g 2010-06-21 12:49:37
 
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
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all") public class GeneratorParser extends BaseParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "IDENT", "LPAREN", "RPAREN", "SEMI", "COMMA", "COLON_EQUAL", "DOT", "LBRACK", "RBRACK", "LBRACE", "RBRACE", "LESS", "COLON", "EQUAL", "SCRIPTBODY", "DOTDOT", "INT", "STAR", "COLON_COLON", "NOT_EQUAL", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "PLUS", "MINUS", "SLASH", "ARROW", "AT", "BAR", "REAL", "STRING", "HASH", "NEWLINE", "WS", "SL_COMMENT", "ML_COMMENT", "RANGE_OR_INT", "ESC", "HEX_DIGIT", "VOCAB", "'procedure'", "'var'", "'begin'", "'end'", "'for'", "'in'", "'if'", "'then'", "'else'", "'model'", "'constraints'", "'enum'", "'abstract'", "'class'", "'attributes'", "'operations'", "'associationClass'", "'associationclass'", "'between'", "'aggregation'", "'composition'", "'ordered'", "'subsets'", "'redefines'", "'context'", "'inv'", "'existential'", "'pre'", "'post'", "'let'", "'implies'", "'or'", "'xor'", "'and'", "'div'", "'not'", "'allInstances'", "'iterate'", "'oclAsType'", "'oclIsKindOf'", "'oclIsTypeOf'", "'endif'", "'true'", "'false'", "'Set'", "'Sequence'", "'Bag'", "'OrderedSet'", "'oclEmpty'", "'oclUndefined'", "'Undefined'", "'null'", "'Tuple'", "'Date'", "'Collection'"
    };
    public static final int STAR=21;
    public static final int EOF=-1;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__91=91;
    public static final int RPAREN=6;
    public static final int T__92=92;
    public static final int GREATER=24;
    public static final int T__90=90;
    public static final int NOT_EQUAL=23;
    public static final int LESS=15;
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
    public static final int INT=20;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int REAL=33;
    public static final int WS=37;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__70=70;
    public static final int SL_COMMENT=38;
    public static final int LESS_EQUAL=25;
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
    public static final int ESC=41;
    public static final int LBRACE=13;
    public static final int DOTDOT=19;
    public static final int T__61=61;
    public static final int T__60=60;
    public static final int LPAREN=5;
    public static final int T__55=55;
    public static final int AT=31;
    public static final int ML_COMMENT=39;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int COLON_EQUAL=9;
    public static final int SLASH=29;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int COMMA=8;
    public static final int T__59=59;
    public static final int EQUAL=17;
    public static final int IDENT=4;
    public static final int PLUS=27;
    public static final int RANGE_OR_INT=40;
    public static final int DOT=10;
    public static final int T__50=50;
    public static final int SCRIPTBODY=18;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int HASH=35;
    public static final int HEX_DIGIT=42;
    public static final int COLON_COLON=22;
    public static final int MINUS=28;
    public static final int SEMI=7;
    public static final int COLON=16;
    public static final int NEWLINE=36;
    public static final int VOCAB=43;
    public static final int ARROW=30;
    public static final int GREATER_EQUAL=26;
    public static final int BAR=32;
    public static final int STRING=34;

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
    // Generator.g:67:1: invariantListOnly returns [List<ASTConstraintDefinition> invariantList] : (def= invariant )* EOF ;
    public final List<ASTConstraintDefinition> invariantListOnly() throws RecognitionException {
        List<ASTConstraintDefinition> invariantList = null;

        ASTConstraintDefinition def = null;


         invariantList = new ArrayList<ASTConstraintDefinition>(); 
        try {
            // Generator.g:69:1: ( (def= invariant )* EOF )
            // Generator.g:70:5: (def= invariant )* EOF
            {
            // Generator.g:70:5: (def= invariant )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==68) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Generator.g:70:7: def= invariant
            	    {
            	    pushFollow(FOLLOW_invariant_in_invariantListOnly79);
            	    def=invariant();

            	    state._fsp--;
            	    if (state.failed) return invariantList;
            	    if ( state.backtracking==0 ) {
            	       invariantList.add(def); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            match(input,EOF,FOLLOW_EOF_in_invariantListOnly90); if (state.failed) return invariantList;

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
    // Generator.g:119:1: procedureListOnly returns [List<ASTGProcedure> procedureList] : (proc= procedure )* EOF ;
    public final List<ASTGProcedure> procedureListOnly() throws RecognitionException {
        List<ASTGProcedure> procedureList = null;

        ASTGProcedure proc = null;


         procedureList = new ArrayList<ASTGProcedure>(); 
        try {
            // Generator.g:121:1: ( (proc= procedure )* EOF )
            // Generator.g:122:5: (proc= procedure )* EOF
            {
            // Generator.g:122:5: (proc= procedure )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==44) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // Generator.g:123:7: proc= procedure
            	    {
            	    pushFollow(FOLLOW_procedure_in_procedureListOnly135);
            	    proc=procedure();

            	    state._fsp--;
            	    if (state.failed) return procedureList;
            	    if ( state.backtracking==0 ) {
            	       procedureList.add(proc); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            match(input,EOF,FOLLOW_EOF_in_procedureListOnly150); if (state.failed) return procedureList;

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
            match(input,44,FOLLOW_44_in_procedure178); if (state.failed) return proc;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_procedure182); if (state.failed) return proc;
            match(input,LPAREN,FOLLOW_LPAREN_in_procedure184); if (state.failed) return proc;
            pushFollow(FOLLOW_variableDeclarationList_in_procedure188);
            parameterDecls=variableDeclarationList();

            state._fsp--;
            if (state.failed) return proc;
            match(input,RPAREN,FOLLOW_RPAREN_in_procedure190); if (state.failed) return proc;
            // Generator.g:138:5: ( 'var' localDecls= variableDeclarationList SEMI )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==45) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // Generator.g:138:7: 'var' localDecls= variableDeclarationList SEMI
                    {
                    match(input,45,FOLLOW_45_in_procedure198); if (state.failed) return proc;
                    pushFollow(FOLLOW_variableDeclarationList_in_procedure202);
                    localDecls=variableDeclarationList();

                    state._fsp--;
                    if (state.failed) return proc;
                    match(input,SEMI,FOLLOW_SEMI_in_procedure204); if (state.failed) return proc;

                    }
                    break;

            }

            match(input,46,FOLLOW_46_in_procedure213); if (state.failed) return proc;
            pushFollow(FOLLOW_instructionList_in_procedure217);
            instructions=instructionList();

            state._fsp--;
            if (state.failed) return proc;
            match(input,47,FOLLOW_47_in_procedure219); if (state.failed) return proc;
            match(input,SEMI,FOLLOW_SEMI_in_procedure221); if (state.failed) return proc;
            if ( state.backtracking==0 ) {
               proc = new ASTGProcedure(name, parameterDecls, localDecls, instructions ); 
            }

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
                    if (state.failed) return varDecls;
                    if ( state.backtracking==0 ) {
                      varDecls.add(decl);
                    }
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
                    	    match(input,COMMA,FOLLOW_COMMA_in_variableDeclarationList270); if (state.failed) return varDecls;
                    	    pushFollow(FOLLOW_variableDeclaration_in_variableDeclarationList274);
                    	    decl=variableDeclaration();

                    	    state._fsp--;
                    	    if (state.failed) return varDecls;
                    	    if ( state.backtracking==0 ) {
                    	      varDecls.add(decl);
                    	    }

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

                if ( (LA6_0==IDENT||LA6_0==LBRACK||LA6_0==48||LA6_0==50) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // Generator.g:162:7: instr= instruction SEMI
            	    {
            	    pushFollow(FOLLOW_instruction_in_instructionList318);
            	    instr=instruction();

            	    state._fsp--;
            	    if (state.failed) return instructions;
            	    match(input,SEMI,FOLLOW_SEMI_in_instructionList320); if (state.failed) return instructions;
            	    if ( state.backtracking==0 ) {
            	      instructions.add(instr);
            	    }

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
                    if (state.backtracking>0) {state.failed=true; return instr;}
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
            case 48:
                {
                alt7=3;
                }
                break;
            case 50:
                {
                alt7=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return instr;}
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
                    if (state.failed) return instr;
                    if ( state.backtracking==0 ) {
                      instr = instrVA;
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:176:7: instrAA= attributeAssignment
                    {
                    pushFollow(FOLLOW_attributeAssignment_in_instruction370);
                    instrAA=attributeAssignment();

                    state._fsp--;
                    if (state.failed) return instr;
                    if ( state.backtracking==0 ) {
                      instr = instrAA;
                    }

                    }
                    break;
                case 3 :
                    // Generator.g:177:7: instrLO= loop
                    {
                    pushFollow(FOLLOW_loop_in_instruction384);
                    instrLO=loop();

                    state._fsp--;
                    if (state.failed) return instr;
                    if ( state.backtracking==0 ) {
                      instr = instrLO;
                    }

                    }
                    break;
                case 4 :
                    // Generator.g:178:7: instrAI= atomicInstruction
                    {
                    pushFollow(FOLLOW_atomicInstruction_in_instruction401);
                    instrAI=atomicInstruction();

                    state._fsp--;
                    if (state.failed) return instr;
                    if ( state.backtracking==0 ) {
                      instr = instrAI;
                    }

                    }
                    break;
                case 5 :
                    // Generator.g:179:7: instrIT= ifThenElse
                    {
                    pushFollow(FOLLOW_ifThenElse_in_instruction415);
                    instrIT=ifThenElse();

                    state._fsp--;
                    if (state.failed) return instr;
                    if ( state.backtracking==0 ) {
                      instr = instrIT;
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
            target=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableAssignment445); if (state.failed) return assignment;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_variableAssignment447); if (state.failed) return assignment;
            pushFollow(FOLLOW_valueInstruction_in_variableAssignment451);
            source=valueInstruction();

            state._fsp--;
            if (state.failed) return assignment;
            if ( state.backtracking==0 ) {
               assignment = new ASTGVariableAssignment( target, source ); 
            }

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
            if (state.failed) return assignment;
            match(input,DOT,FOLLOW_DOT_in_attributeAssignment485); if (state.failed) return assignment;
            attributeName=(Token)match(input,IDENT,FOLLOW_IDENT_in_attributeAssignment489); if (state.failed) return assignment;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_attributeAssignment497); if (state.failed) return assignment;
            pushFollow(FOLLOW_valueInstruction_in_attributeAssignment501);
            source=valueInstruction();

            state._fsp--;
            if (state.failed) return assignment;
            if ( state.backtracking==0 ) {
               assignment = new ASTGAttributeAssignment(
              			 targetObject, attributeName, source ); 
            }

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
            t=(Token)match(input,48,FOLLOW_48_in_loop533); if (state.failed) return loop;
            pushFollow(FOLLOW_variableDeclaration_in_loop537);
            decl=variableDeclaration();

            state._fsp--;
            if (state.failed) return loop;
            match(input,49,FOLLOW_49_in_loop539); if (state.failed) return loop;
            pushFollow(FOLLOW_oclExpression_in_loop543);
            sequence=oclExpression();

            state._fsp--;
            if (state.failed) return loop;
            match(input,46,FOLLOW_46_in_loop545); if (state.failed) return loop;
            pushFollow(FOLLOW_instructionList_in_loop556);
            instructions=instructionList();

            state._fsp--;
            if (state.failed) return loop;
            match(input,47,FOLLOW_47_in_loop558); if (state.failed) return loop;
            if ( state.backtracking==0 ) {
               loop = new ASTGLoop( decl, sequence, instructions, t ); 
            }

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
            token=(Token)match(input,50,FOLLOW_50_in_ifThenElse594); if (state.failed) return ifThenElse;
            pushFollow(FOLLOW_oclExpression_in_ifThenElse598);
            sequence=oclExpression();

            state._fsp--;
            if (state.failed) return ifThenElse;
            match(input,51,FOLLOW_51_in_ifThenElse609); if (state.failed) return ifThenElse;
            match(input,46,FOLLOW_46_in_ifThenElse611); if (state.failed) return ifThenElse;
            pushFollow(FOLLOW_instructionList_in_ifThenElse615);
            thenInstructions=instructionList();

            state._fsp--;
            if (state.failed) return ifThenElse;
            match(input,47,FOLLOW_47_in_ifThenElse617); if (state.failed) return ifThenElse;
            // Generator.g:226:9: ( 'else' 'begin' elseInstructions= instructionList 'end' )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==52) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // Generator.g:226:10: 'else' 'begin' elseInstructions= instructionList 'end'
                    {
                    match(input,52,FOLLOW_52_in_ifThenElse628); if (state.failed) return ifThenElse;
                    match(input,46,FOLLOW_46_in_ifThenElse630); if (state.failed) return ifThenElse;
                    pushFollow(FOLLOW_instructionList_in_ifThenElse634);
                    elseInstructions=instructionList();

                    state._fsp--;
                    if (state.failed) return ifThenElse;
                    match(input,47,FOLLOW_47_in_ifThenElse636); if (state.failed) return ifThenElse;
                    if ( state.backtracking==0 ) {
                       elseInstructionsList=elseInstructions; 
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               ifThenElse = new ASTGIfThenElse( sequence, thenInstructions,
                              elseInstructionsList, token ); 
            }

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
                if (state.backtracking>0) {state.failed=true; return valueinstr;}
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
                    if (state.failed) return valueinstr;
                    if ( state.backtracking==0 ) {
                      valueinstr = atmoicInstr; 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:238:7: oclExpr= oclExpression
                    {
                    pushFollow(FOLLOW_oclExpression_in_valueInstruction690);
                    oclExpr=oclExpression();

                    state._fsp--;
                    if (state.failed) return valueinstr;
                    if ( state.backtracking==0 ) {
                      valueinstr = oclExpr; 
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
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_atomicInstruction720); if (state.failed) return instr;
            if ( state.backtracking==0 ) {
               instr = new ASTGAtomicInstruction(name); 
            }
            match(input,LPAREN,FOLLOW_LPAREN_in_atomicInstruction724); if (state.failed) return instr;
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
                    if (state.failed) return instr;
                    if ( state.backtracking==0 ) {
                       instr.addParameter(parameter); 
                    }
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
                    	    match(input,COMMA,FOLLOW_COMMA_in_atomicInstruction756); if (state.failed) return instr;
                    	    pushFollow(FOLLOW_instructionParameter_in_atomicInstruction760);
                    	    parameter=instructionParameter();

                    	    state._fsp--;
                    	    if (state.failed) return instr;
                    	    if ( state.backtracking==0 ) {
                    	       instr.addParameter(parameter); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_atomicInstruction808); if (state.failed) return instr;

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
                if (state.backtracking>0) {state.failed=true; return parameter;}
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
                    if (state.failed) return parameter;
                    if ( state.backtracking==0 ) {
                      parameter = parameterOcl; 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:264:7: parameterIdent= instrParameterIdent
                    {
                    pushFollow(FOLLOW_instrParameterIdent_in_instructionParameter848);
                    parameterIdent=instrParameterIdent();

                    state._fsp--;
                    if (state.failed) return parameter;
                    if ( state.backtracking==0 ) {
                      parameter = parameterIdent; 
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
            i=(Token)match(input,IDENT,FOLLOW_IDENT_in_instrParameterIdent876); if (state.failed) return t;
            if ( state.backtracking==0 ) {
               t = i; 
            }

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
            i=(Token)match(input,LBRACK,FOLLOW_LBRACK_in_oclExpression904); if (state.failed) return encapOcl;
            pushFollow(FOLLOW_expression_in_oclExpression908);
            ocl=expression();

            state._fsp--;
            if (state.failed) return encapOcl;
            match(input,RBRACK,FOLLOW_RBRACK_in_oclExpression910); if (state.failed) return encapOcl;
            if ( state.backtracking==0 ) {
               encapOcl = new ASTGocl(ocl, i); 
            }

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
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_procedureCallOnly945); if (state.failed) return call;
            if ( state.backtracking==0 ) {
              call = new ASTGProcedureCall(name);
            }
            match(input,LPAREN,FOLLOW_LPAREN_in_procedureCallOnly953); if (state.failed) return call;
            // Generator.g:298:12: (ocl= expression ( COMMA ocl= expression )* )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0>=IDENT && LA14_0<=LPAREN)||LA14_0==INT||(LA14_0>=PLUS && LA14_0<=MINUS)||(LA14_0>=REAL && LA14_0<=HASH)||LA14_0==50||LA14_0==73||LA14_0==79||(LA14_0>=81 && LA14_0<=84)||(LA14_0>=86 && LA14_0<=97)) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // Generator.g:299:5: ocl= expression ( COMMA ocl= expression )*
                    {
                    pushFollow(FOLLOW_expression_in_procedureCallOnly964);
                    ocl=expression();

                    state._fsp--;
                    if (state.failed) return call;
                    if ( state.backtracking==0 ) {
                      call.addParameter(ocl);
                    }
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
                    	    match(input,COMMA,FOLLOW_COMMA_in_procedureCallOnly974); if (state.failed) return call;
                    	    pushFollow(FOLLOW_expression_in_procedureCallOnly978);
                    	    ocl=expression();

                    	    state._fsp--;
                    	    if (state.failed) return call;
                    	    if ( state.backtracking==0 ) {
                    	      call.addParameter(ocl);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop13;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_procedureCallOnly992); if (state.failed) return call;
            match(input,EOF,FOLLOW_EOF_in_procedureCallOnly998); if (state.failed) return call;

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
            match(input,53,FOLLOW_53_in_model1025); if (state.failed) return n;
            modelName=(Token)match(input,IDENT,FOLLOW_IDENT_in_model1029); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTModel(modelName); 
            }
            // Generator.g:322:5: (e= enumTypeDefinition )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==55) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // Generator.g:322:7: e= enumTypeDefinition
            	    {
            	    pushFollow(FOLLOW_enumTypeDefinition_in_model1041);
            	    e=enumTypeDefinition();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addEnumTypeDef(e); 
            	    }

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
                case 56:
                case 57:
                case 60:
                case 61:
                    {
                    alt17=1;
                    }
                    break;
                case IDENT:
                case 63:
                case 64:
                    {
                    alt17=2;
                    }
                    break;
                case 54:
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
            	    if (state.failed) return n;

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
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addAssociation(a); 
            	    }

            	    }


            	    }
            	    break;
            	case 3 :
            	    // Generator.g:325:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    {
            	    // Generator.g:325:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    // Generator.g:325:11: 'constraints' (cons= invariant | ppc= prePost )*
            	    {
            	    match(input,54,FOLLOW_54_in_model1091); if (state.failed) return n;
            	    // Generator.g:326:11: (cons= invariant | ppc= prePost )*
            	    loop16:
            	    do {
            	        int alt16=3;
            	        int LA16_0 = input.LA(1);

            	        if ( (LA16_0==68) ) {
            	            int LA16_2 = input.LA(2);

            	            if ( (LA16_2==IDENT) ) {
            	                int LA16_3 = input.LA(3);

            	                if ( (LA16_3==COLON_COLON) ) {
            	                    alt16=2;
            	                }
            	                else if ( (LA16_3==EOF||LA16_3==IDENT||LA16_3==COMMA||LA16_3==COLON||LA16_3==54||(LA16_3>=56 && LA16_3<=57)||(LA16_3>=60 && LA16_3<=61)||(LA16_3>=63 && LA16_3<=64)||(LA16_3>=68 && LA16_3<=70)) ) {
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
            	    	    if (state.failed) return n;
            	    	    if ( state.backtracking==0 ) {
            	    	       n.addConstraint(cons); 
            	    	    }

            	    	    }
            	    	    break;
            	    	case 2 :
            	    	    // Generator.g:327:15: ppc= prePost
            	    	    {
            	    	    pushFollow(FOLLOW_prePost_in_model1130);
            	    	    ppc=prePost();

            	    	    state._fsp--;
            	    	    if (state.failed) return n;
            	    	    if ( state.backtracking==0 ) {
            	    	       n.addPrePost(ppc); 
            	    	    }

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

            match(input,EOF,FOLLOW_EOF_in_model1171); if (state.failed) return n;

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
            match(input,55,FOLLOW_55_in_enumTypeDefinition1198); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_enumTypeDefinition1202); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_enumTypeDefinition1204); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_enumTypeDefinition1208);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,RBRACE,FOLLOW_RBRACE_in_enumTypeDefinition1210); if (state.failed) return n;
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
                    match(input,SEMI,FOLLOW_SEMI_in_enumTypeDefinition1214); if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTEnumTypeDefinition(name, idListRes); 
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

            if ( (LA19_0==56) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // Generator.g:354:7: 'abstract'
                    {
                    match(input,56,FOLLOW_56_in_generalClassDefinition1253); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       isAbstract = true; 
                    }

                    }
                    break;

            }

            // Generator.g:355:5: ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==57) ) {
                alt20=1;
            }
            else if ( ((LA20_0>=60 && LA20_0<=61)) ) {
                alt20=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
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
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       n.addClass(c); 
                    }

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
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       n.addAssociationClass(ac); 
                    }

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
            match(input,57,FOLLOW_57_in_classDefinition1330); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_classDefinition1334); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTClass(name, isAbstract); 
            }
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
                    match(input,LESS,FOLLOW_LESS_in_classDefinition1344); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_classDefinition1348);
                    idListRes=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addSuperClasses(idListRes); 
                    }

                    }
                    break;

            }

            // Generator.g:378:5: ( 'attributes' (a= attributeDefinition )* )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==58) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // Generator.g:378:7: 'attributes' (a= attributeDefinition )*
                    {
                    match(input,58,FOLLOW_58_in_classDefinition1361); if (state.failed) return n;
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
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addAttribute(a); 
                    	    }

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

            if ( (LA25_0==59) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // Generator.g:381:7: 'operations' (op= operationDefinition )*
                    {
                    match(input,59,FOLLOW_59_in_classDefinition1395); if (state.failed) return n;
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
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addOperation(op); 
                    	    }

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

            if ( (LA27_0==54) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // Generator.g:384:7: 'constraints' (inv= invariantClause )*
                    {
                    match(input,54,FOLLOW_54_in_classDefinition1429); if (state.failed) return n;
                    // Generator.g:385:7: (inv= invariantClause )*
                    loop26:
                    do {
                        int alt26=2;
                        int LA26_0 = input.LA(1);

                        if ( ((LA26_0>=69 && LA26_0<=70)) ) {
                            alt26=1;
                        }


                        switch (alt26) {
                    	case 1 :
                    	    // Generator.g:386:9: inv= invariantClause
                    	    {
                    	    pushFollow(FOLLOW_invariantClause_in_classDefinition1449);
                    	    inv=invariantClause();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addInvariantClause(inv); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop26;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,47,FOLLOW_47_in_classDefinition1473); if (state.failed) return n;

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
            if ( (input.LA(1)>=60 && input.LA(1)<=61) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            if ( state.backtracking==0 ) {
               
                  	if ((classKW!=null?classKW.getText():null).equals("associationClass")) {
                             reportWarning("the 'associationClass' keyword is deprecated and will " +
                                           "not be supported in the future, use 'associationclass' instead");
                          }  
                  
            }
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationClassDefinition1532); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociationClass(name, isAbstract); 
            }
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
                    match(input,LESS,FOLLOW_LESS_in_associationClassDefinition1542); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_associationClassDefinition1546);
                    idListRes=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addSuperClasses(idListRes); 
                    }

                    }
                    break;

            }

            match(input,62,FOLLOW_62_in_associationClassDefinition1557); if (state.failed) return n;
            pushFollow(FOLLOW_associationEnd_in_associationClassDefinition1565);
            ae=associationEnd();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addEnd(ae); 
            }
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
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addEnd(ae); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt29 >= 1 ) break loop29;
            	    if (state.backtracking>0) {state.failed=true; return n;}
                        EarlyExitException eee =
                            new EarlyExitException(29, input);
                        throw eee;
                }
                cnt29++;
            } while (true);

            // Generator.g:423:5: ( 'attributes' (a= attributeDefinition )* )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==58) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // Generator.g:423:7: 'attributes' (a= attributeDefinition )*
                    {
                    match(input,58,FOLLOW_58_in_associationClassDefinition1590); if (state.failed) return n;
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
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addAttribute(a); 
                    	    }

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

            if ( (LA33_0==59) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // Generator.g:426:7: 'operations' (op= operationDefinition )*
                    {
                    match(input,59,FOLLOW_59_in_associationClassDefinition1624); if (state.failed) return n;
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
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addOperation(op); 
                    	    }

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

            if ( (LA35_0==54) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // Generator.g:429:7: 'constraints' (inv= invariantClause )*
                    {
                    match(input,54,FOLLOW_54_in_associationClassDefinition1658); if (state.failed) return n;
                    // Generator.g:430:7: (inv= invariantClause )*
                    loop34:
                    do {
                        int alt34=2;
                        int LA34_0 = input.LA(1);

                        if ( ((LA34_0>=69 && LA34_0<=70)) ) {
                            alt34=1;
                        }


                        switch (alt34) {
                    	case 1 :
                    	    // Generator.g:431:9: inv= invariantClause
                    	    {
                    	    pushFollow(FOLLOW_invariantClause_in_associationClassDefinition1678);
                    	    inv=invariantClause();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addInvariantClause(inv); 
                    	    }

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

            if ( ((LA36_0>=63 && LA36_0<=64)) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // Generator.g:434:7: ( 'aggregation' | 'composition' )
                    {
                    if ( state.backtracking==0 ) {
                       t = input.LT(1); 
                    }
                    if ( (input.LA(1)>=63 && input.LA(1)<=64) ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    if ( state.backtracking==0 ) {
                       n.setKind(t); 
                    }

                    }
                    break;

            }

            match(input,47,FOLLOW_47_in_associationClassDefinition1741); if (state.failed) return n;

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
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_attributeDefinition1770); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_attributeDefinition1772); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_attributeDefinition1776);
            t=type();

            state._fsp--;
            if (state.failed) return n;
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
                    match(input,SEMI,FOLLOW_SEMI_in_attributeDefinition1780); if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTAttribute(name, t); 
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
    // $ANTLR end "attributeDefinition"


    // $ANTLR start "operationDefinition"
    // Generator.g:456:1: operationDefinition returns [ASTOperation n] : name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression | EQUAL keyScript body= SCRIPTBODY )? (ppc= prePostClause )* ( SEMI )? ;
    public final ASTOperation operationDefinition() throws RecognitionException {
        ASTOperation n = null;

        Token name=null;
        Token body=null;
        List pl = null;

        ASTType t = null;

        ASTExpression e = null;

        ASTPrePostClause ppc = null;


        try {
            // Generator.g:457:1: (name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression | EQUAL keyScript body= SCRIPTBODY )? (ppc= prePostClause )* ( SEMI )? )
            // Generator.g:458:5: name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression | EQUAL keyScript body= SCRIPTBODY )? (ppc= prePostClause )* ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationDefinition1818); if (state.failed) return n;
            pushFollow(FOLLOW_paramList_in_operationDefinition1826);
            pl=paramList();

            state._fsp--;
            if (state.failed) return n;
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
                    match(input,COLON,FOLLOW_COLON_in_operationDefinition1834); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_operationDefinition1838);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTOperation(name, pl, t); 
            }
            // Generator.g:463:5: ( EQUAL e= expression | EQUAL keyScript body= SCRIPTBODY )?
            int alt39=3;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==EQUAL) ) {
                int LA39_1 = input.LA(2);

                if ( (LA39_1==IDENT) ) {
                    int LA39_3 = input.LA(3);

                    if ( ((LA39_3>=IDENT && LA39_3<=LPAREN)||LA39_3==SEMI||(LA39_3>=DOT && LA39_3<=LBRACK)||LA39_3==LESS||LA39_3==EQUAL||(LA39_3>=STAR && LA39_3<=AT)||LA39_3==47||LA39_3==54||(LA39_3>=63 && LA39_3<=64)||(LA39_3>=71 && LA39_3<=72)||(LA39_3>=74 && LA39_3<=78)) ) {
                        alt39=1;
                    }
                    else if ( (LA39_3==SCRIPTBODY) ) {
                        alt39=2;
                    }
                }
                else if ( (LA39_1==LPAREN||LA39_1==INT||(LA39_1>=PLUS && LA39_1<=MINUS)||(LA39_1>=REAL && LA39_1<=HASH)||LA39_1==50||LA39_1==73||LA39_1==79||(LA39_1>=81 && LA39_1<=84)||(LA39_1>=86 && LA39_1<=97)) ) {
                    alt39=1;
                }
            }
            switch (alt39) {
                case 1 :
                    // Generator.g:464:6: EQUAL e= expression
                    {
                    match(input,EQUAL,FOLLOW_EQUAL_in_operationDefinition1866); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_operationDefinition1870);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setExpression(e); 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:467:6: EQUAL keyScript body= SCRIPTBODY
                    {
                    match(input,EQUAL,FOLLOW_EQUAL_in_operationDefinition1892); if (state.failed) return n;
                    pushFollow(FOLLOW_keyScript_in_operationDefinition1894);
                    keyScript();

                    state._fsp--;
                    if (state.failed) return n;
                    body=(Token)match(input,SCRIPTBODY,FOLLOW_SCRIPTBODY_in_operationDefinition1898); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setScript(body); 
                    }

                    }
                    break;

            }

            // Generator.g:471:5: (ppc= prePostClause )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( ((LA40_0>=71 && LA40_0<=72)) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // Generator.g:471:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_operationDefinition1929);
            	    ppc=prePostClause();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addPrePostClause(ppc); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop40;
                }
            } while (true);

            // Generator.g:472:5: ( SEMI )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==SEMI) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // Generator.g:472:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_operationDefinition1942); if (state.failed) return n;

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
    // Generator.g:482:1: associationDefinition returns [ASTAssociation n] : ( keyAssociation | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end' ;
    public final ASTAssociation associationDefinition() throws RecognitionException {
        ASTAssociation n = null;

        Token name=null;
        ASTAssociationEnd ae = null;


         Token t = null; 
        try {
            // Generator.g:484:1: ( ( keyAssociation | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end' )
            // Generator.g:485:5: ( keyAssociation | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end'
            {
            if ( state.backtracking==0 ) {
               t = input.LT(1); 
            }
            // Generator.g:486:5: ( keyAssociation | 'aggregation' | 'composition' )
            int alt42=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt42=1;
                }
                break;
            case 63:
                {
                alt42=2;
                }
                break;
            case 64:
                {
                alt42=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 42, 0, input);

                throw nvae;
            }

            switch (alt42) {
                case 1 :
                    // Generator.g:486:7: keyAssociation
                    {
                    pushFollow(FOLLOW_keyAssociation_in_associationDefinition1980);
                    keyAssociation();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 2 :
                    // Generator.g:486:24: 'aggregation'
                    {
                    match(input,63,FOLLOW_63_in_associationDefinition1984); if (state.failed) return n;

                    }
                    break;
                case 3 :
                    // Generator.g:486:40: 'composition'
                    {
                    match(input,64,FOLLOW_64_in_associationDefinition1988); if (state.failed) return n;

                    }
                    break;

            }

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationDefinition2003); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociation(t, name); 
            }
            match(input,62,FOLLOW_62_in_associationDefinition2011); if (state.failed) return n;
            pushFollow(FOLLOW_associationEnd_in_associationDefinition2019);
            ae=associationEnd();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addEnd(ae); 
            }
            // Generator.g:491:5: (ae= associationEnd )+
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
            	    // Generator.g:491:7: ae= associationEnd
            	    {
            	    pushFollow(FOLLOW_associationEnd_in_associationDefinition2031);
            	    ae=associationEnd();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addEnd(ae); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt43 >= 1 ) break loop43;
            	    if (state.backtracking>0) {state.failed=true; return n;}
                        EarlyExitException eee =
                            new EarlyExitException(43, input);
                        throw eee;
                }
                cnt43++;
            } while (true);

            match(input,47,FOLLOW_47_in_associationDefinition2042); if (state.failed) return n;

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
    // Generator.g:500:1: associationEnd returns [ASTAssociationEnd n] : name= IDENT LBRACK m= multiplicity RBRACK ( keyRole rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )? ;
    public final ASTAssociationEnd associationEnd() throws RecognitionException {
        ASTAssociationEnd n = null;

        Token name=null;
        Token rn=null;
        Token sr=null;
        Token rd=null;
        ASTMultiplicity m = null;


        try {
            // Generator.g:501:1: (name= IDENT LBRACK m= multiplicity RBRACK ( keyRole rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )? )
            // Generator.g:502:5: name= IDENT LBRACK m= multiplicity RBRACK ( keyRole rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd2068); if (state.failed) return n;
            match(input,LBRACK,FOLLOW_LBRACK_in_associationEnd2070); if (state.failed) return n;
            pushFollow(FOLLOW_multiplicity_in_associationEnd2074);
            m=multiplicity();

            state._fsp--;
            if (state.failed) return n;
            match(input,RBRACK,FOLLOW_RBRACK_in_associationEnd2076); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociationEnd(name, m); 
            }
            // Generator.g:503:5: ( keyRole rn= IDENT )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==IDENT) ) {
                int LA44_1 = input.LA(2);

                if ( (LA44_1==IDENT) ) {
                    int LA44_3 = input.LA(3);

                    if ( ((input.LT(1).getText().equals("role"))) ) {
                        alt44=1;
                    }
                }
            }
            switch (alt44) {
                case 1 :
                    // Generator.g:503:7: keyRole rn= IDENT
                    {
                    pushFollow(FOLLOW_keyRole_in_associationEnd2087);
                    keyRole();

                    state._fsp--;
                    if (state.failed) return n;
                    rn=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd2091); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setRolename(rn); 
                    }

                    }
                    break;

            }

            // Generator.g:504:5: ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )*
            loop45:
            do {
                int alt45=5;
                switch ( input.LA(1) ) {
                case IDENT:
                    {
                    int LA45_2 = input.LA(2);

                    if ( (LA45_2==IDENT||LA45_2==SEMI||LA45_2==47||LA45_2==54||(LA45_2>=58 && LA45_2<=59)||(LA45_2>=63 && LA45_2<=67)) ) {
                        alt45=3;
                    }


                    }
                    break;
                case 65:
                    {
                    alt45=1;
                    }
                    break;
                case 66:
                    {
                    alt45=2;
                    }
                    break;
                case 67:
                    {
                    alt45=4;
                    }
                    break;

                }

                switch (alt45) {
            	case 1 :
            	    // Generator.g:505:9: 'ordered'
            	    {
            	    match(input,65,FOLLOW_65_in_associationEnd2112); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.setOrdered(); 
            	    }

            	    }
            	    break;
            	case 2 :
            	    // Generator.g:506:9: 'subsets' sr= IDENT
            	    {
            	    match(input,66,FOLLOW_66_in_associationEnd2124); if (state.failed) return n;
            	    sr=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd2128); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addSubsetsRolename(sr); 
            	    }

            	    }
            	    break;
            	case 3 :
            	    // Generator.g:507:9: keyUnion
            	    {
            	    pushFollow(FOLLOW_keyUnion_in_associationEnd2140);
            	    keyUnion();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.setUnion(true); 
            	    }

            	    }
            	    break;
            	case 4 :
            	    // Generator.g:508:9: 'redefines' rd= IDENT
            	    {
            	    match(input,67,FOLLOW_67_in_associationEnd2152); if (state.failed) return n;
            	    rd=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd2156); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addRedefinesRolename(rd); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop45;
                }
            } while (true);

            // Generator.g:510:5: ( SEMI )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==SEMI) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // Generator.g:510:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_associationEnd2173); if (state.failed) return n;

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
    // Generator.g:524:1: multiplicity returns [ASTMultiplicity n] : mr= multiplicityRange ( COMMA mr= multiplicityRange )* ;
    public final ASTMultiplicity multiplicity() throws RecognitionException {
        ASTMultiplicity n = null;

        ASTMultiplicityRange mr = null;


        try {
            // Generator.g:525:1: (mr= multiplicityRange ( COMMA mr= multiplicityRange )* )
            // Generator.g:526:5: mr= multiplicityRange ( COMMA mr= multiplicityRange )*
            {
            if ( state.backtracking==0 ) {
               
              	Token t = input.LT(1); // remember start position of expression
              	n = new ASTMultiplicity(t);
                  
            }
            pushFollow(FOLLOW_multiplicityRange_in_multiplicity2208);
            mr=multiplicityRange();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addRange(mr); 
            }
            // Generator.g:531:5: ( COMMA mr= multiplicityRange )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==COMMA) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // Generator.g:531:7: COMMA mr= multiplicityRange
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_multiplicity2218); if (state.failed) return n;
            	    pushFollow(FOLLOW_multiplicityRange_in_multiplicity2222);
            	    mr=multiplicityRange();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addRange(mr); 
            	    }

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
    // Generator.g:534:1: multiplicityRange returns [ASTMultiplicityRange n] : ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? ;
    public final ASTMultiplicityRange multiplicityRange() throws RecognitionException {
        ASTMultiplicityRange n = null;

        int ms1 = 0;

        int ms2 = 0;


        try {
            // Generator.g:535:1: (ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? )
            // Generator.g:536:5: ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )?
            {
            pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange2251);
            ms1=multiplicitySpec();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTMultiplicityRange(ms1); 
            }
            // Generator.g:537:5: ( DOTDOT ms2= multiplicitySpec )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==DOTDOT) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // Generator.g:537:7: DOTDOT ms2= multiplicitySpec
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_multiplicityRange2261); if (state.failed) return n;
                    pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange2265);
                    ms2=multiplicitySpec();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setHigh(ms2); 
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
        return n;
    }
    // $ANTLR end "multiplicityRange"


    // $ANTLR start "multiplicitySpec"
    // Generator.g:540:1: multiplicitySpec returns [int m] : (i= INT | STAR );
    public final int multiplicitySpec() throws RecognitionException {
        int m = 0;

        Token i=null;

         m = -1; 
        try {
            // Generator.g:542:1: (i= INT | STAR )
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==INT) ) {
                alt49=1;
            }
            else if ( (LA49_0==STAR) ) {
                alt49=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return m;}
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;
            }
            switch (alt49) {
                case 1 :
                    // Generator.g:543:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_multiplicitySpec2299); if (state.failed) return m;
                    if ( state.backtracking==0 ) {
                       m = Integer.parseInt((i!=null?i.getText():null)); 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:544:7: STAR
                    {
                    match(input,STAR,FOLLOW_STAR_in_multiplicitySpec2309); if (state.failed) return m;
                    if ( state.backtracking==0 ) {
                       m = -1; 
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
        return m;
    }
    // $ANTLR end "multiplicitySpec"


    // $ANTLR start "invariant"
    // Generator.g:565:1: invariant returns [ASTConstraintDefinition n] : 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )* ;
    public final ASTConstraintDefinition invariant() throws RecognitionException {
        ASTConstraintDefinition n = null;

        Token v=null;
        ASTSimpleType t = null;

        ASTInvariantClause inv = null;


        try {
            // Generator.g:566:1: ( 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )* )
            // Generator.g:567:5: 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )*
            {
            if ( state.backtracking==0 ) {
               n = new ASTConstraintDefinition(); 
            }
            match(input,68,FOLLOW_68_in_invariant2350); if (state.failed) return n;
            // Generator.g:569:5: (v= IDENT ( ',' v= IDENT )* COLON )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==IDENT) ) {
                int LA51_1 = input.LA(2);

                if ( (LA51_1==COMMA||LA51_1==COLON) ) {
                    alt51=1;
                }
            }
            switch (alt51) {
                case 1 :
                    // Generator.g:569:7: v= IDENT ( ',' v= IDENT )* COLON
                    {
                    v=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariant2360); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addVarName(v); 
                    }
                    // Generator.g:570:8: ( ',' v= IDENT )*
                    loop50:
                    do {
                        int alt50=2;
                        int LA50_0 = input.LA(1);

                        if ( (LA50_0==COMMA) ) {
                            alt50=1;
                        }


                        switch (alt50) {
                    	case 1 :
                    	    // Generator.g:570:9: ',' v= IDENT
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_invariant2373); if (state.failed) return n;
                    	    v=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariant2377); if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addVarName(v); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop50;
                        }
                    } while (true);

                    match(input,COLON,FOLLOW_COLON_in_invariant2385); if (state.failed) return n;

                    }
                    break;

            }

            pushFollow(FOLLOW_simpleType_in_invariant2397);
            t=simpleType();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setType(t); 
            }
            // Generator.g:572:5: (inv= invariantClause )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( ((LA52_0>=69 && LA52_0<=70)) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // Generator.g:572:7: inv= invariantClause
            	    {
            	    pushFollow(FOLLOW_invariantClause_in_invariant2409);
            	    inv=invariantClause();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addInvariantClause(inv); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop52;
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
    // Generator.g:579:1: invariantClause returns [ASTInvariantClause n] : ( 'inv' (name= IDENT )? COLON e= expression | 'existential' 'inv' (name= IDENT )? COLON e= expression );
    public final ASTInvariantClause invariantClause() throws RecognitionException {
        ASTInvariantClause n = null;

        Token name=null;
        ASTExpression e = null;


        try {
            // Generator.g:580:1: ( 'inv' (name= IDENT )? COLON e= expression | 'existential' 'inv' (name= IDENT )? COLON e= expression )
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==69) ) {
                alt55=1;
            }
            else if ( (LA55_0==70) ) {
                alt55=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 55, 0, input);

                throw nvae;
            }
            switch (alt55) {
                case 1 :
                    // Generator.g:581:7: 'inv' (name= IDENT )? COLON e= expression
                    {
                    match(input,69,FOLLOW_69_in_invariantClause2440); if (state.failed) return n;
                    // Generator.g:581:13: (name= IDENT )?
                    int alt53=2;
                    int LA53_0 = input.LA(1);

                    if ( (LA53_0==IDENT) ) {
                        alt53=1;
                    }
                    switch (alt53) {
                        case 1 :
                            // Generator.g:581:15: name= IDENT
                            {
                            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantClause2446); if (state.failed) return n;

                            }
                            break;

                    }

                    match(input,COLON,FOLLOW_COLON_in_invariantClause2451); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_invariantClause2455);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTInvariantClause(name, e); 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:582:7: 'existential' 'inv' (name= IDENT )? COLON e= expression
                    {
                    match(input,70,FOLLOW_70_in_invariantClause2465); if (state.failed) return n;
                    match(input,69,FOLLOW_69_in_invariantClause2467); if (state.failed) return n;
                    // Generator.g:582:27: (name= IDENT )?
                    int alt54=2;
                    int LA54_0 = input.LA(1);

                    if ( (LA54_0==IDENT) ) {
                        alt54=1;
                    }
                    switch (alt54) {
                        case 1 :
                            // Generator.g:582:29: name= IDENT
                            {
                            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantClause2473); if (state.failed) return n;

                            }
                            break;

                    }

                    match(input,COLON,FOLLOW_COLON_in_invariantClause2478); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_invariantClause2482);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTExistentialInvariantClause(name, e); 
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
    // $ANTLR end "invariantClause"


    // $ANTLR start "prePost"
    // Generator.g:593:1: prePost returns [ASTPrePost n] : 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ ;
    public final ASTPrePost prePost() throws RecognitionException {
        ASTPrePost n = null;

        Token classname=null;
        Token opname=null;
        List pl = null;

        ASTType rt = null;

        ASTPrePostClause ppc = null;


        try {
            // Generator.g:594:1: ( 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ )
            // Generator.g:595:5: 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+
            {
            match(input,68,FOLLOW_68_in_prePost2508); if (state.failed) return n;
            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost2512); if (state.failed) return n;
            match(input,COLON_COLON,FOLLOW_COLON_COLON_in_prePost2514); if (state.failed) return n;
            opname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost2518); if (state.failed) return n;
            pushFollow(FOLLOW_paramList_in_prePost2522);
            pl=paramList();

            state._fsp--;
            if (state.failed) return n;
            // Generator.g:595:69: ( COLON rt= type )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==COLON) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // Generator.g:595:71: COLON rt= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_prePost2526); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_prePost2530);
                    rt=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTPrePost(classname, opname, pl, rt); 
            }
            // Generator.g:597:5: (ppc= prePostClause )+
            int cnt57=0;
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( ((LA57_0>=71 && LA57_0<=72)) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // Generator.g:597:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_prePost2549);
            	    ppc=prePostClause();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addPrePostClause(ppc); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt57 >= 1 ) break loop57;
            	    if (state.backtracking>0) {state.failed=true; return n;}
                        EarlyExitException eee =
                            new EarlyExitException(57, input);
                        throw eee;
                }
                cnt57++;
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
    // Generator.g:604:1: prePostClause returns [ASTPrePostClause n] : ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression ;
    public final ASTPrePostClause prePostClause() throws RecognitionException {
        ASTPrePostClause n = null;

        Token name=null;
        ASTExpression e = null;


         Token t = null; 
        try {
            // Generator.g:606:1: ( ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression )
            // Generator.g:607:5: ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression
            {
            if ( state.backtracking==0 ) {
               t = input.LT(1); 
            }
            if ( (input.LA(1)>=71 && input.LA(1)<=72) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // Generator.g:608:25: (name= IDENT )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==IDENT) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // Generator.g:608:27: name= IDENT
                    {
                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePostClause2603); if (state.failed) return n;

                    }
                    break;

            }

            match(input,COLON,FOLLOW_COLON_in_prePostClause2608); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_prePostClause2612);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTPrePostClause(t, name, e); 
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
    // $ANTLR end "prePostClause"


    // $ANTLR start "keyUnion"
    // Generator.g:612:1: keyUnion : {...}? IDENT ;
    public final void keyUnion() throws RecognitionException {
        try {
            // Generator.g:612:9: ({...}? IDENT )
            // Generator.g:613:3: {...}? IDENT
            {
            if ( !((input.LT(1).getText().equals("union"))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "keyUnion", "input.LT(1).getText().equals(\"union\")");
            }
            match(input,IDENT,FOLLOW_IDENT_in_keyUnion2634); if (state.failed) return ;

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
    // $ANTLR end "keyUnion"


    // $ANTLR start "keyAssociation"
    // Generator.g:615:1: keyAssociation : {...}? IDENT ;
    public final void keyAssociation() throws RecognitionException {
        try {
            // Generator.g:615:15: ({...}? IDENT )
            // Generator.g:616:3: {...}? IDENT
            {
            if ( !((input.LT(1).getText().equals("association"))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "keyAssociation", "input.LT(1).getText().equals(\"association\")");
            }
            match(input,IDENT,FOLLOW_IDENT_in_keyAssociation2648); if (state.failed) return ;

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
    // $ANTLR end "keyAssociation"


    // $ANTLR start "keyRole"
    // Generator.g:618:1: keyRole : {...}? IDENT ;
    public final void keyRole() throws RecognitionException {
        try {
            // Generator.g:618:8: ({...}? IDENT )
            // Generator.g:619:3: {...}? IDENT
            {
            if ( !((input.LT(1).getText().equals("role"))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "keyRole", "input.LT(1).getText().equals(\"role\")");
            }
            match(input,IDENT,FOLLOW_IDENT_in_keyRole2662); if (state.failed) return ;

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
    // $ANTLR end "keyRole"


    // $ANTLR start "keyScript"
    // Generator.g:621:1: keyScript : {...}? IDENT ;
    public final void keyScript() throws RecognitionException {
        try {
            // Generator.g:621:10: ({...}? IDENT )
            // Generator.g:622:3: {...}? IDENT
            {
            if ( !((input.LT(1).getText().equals("script"))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "keyScript", "input.LT(1).getText().equals(\"script\")");
            }
            match(input,IDENT,FOLLOW_IDENT_in_keyScript2676); if (state.failed) return ;

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
    // $ANTLR end "keyScript"


    // $ANTLR start "expressionOnly"
    // Generator.g:653:1: expressionOnly returns [ASTExpression n] : nExp= expression EOF ;
    public final ASTExpression expressionOnly() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExp = null;


        try {
            // Generator.g:654:1: (nExp= expression EOF )
            // Generator.g:655:5: nExp= expression EOF
            {
            pushFollow(FOLLOW_expression_in_expressionOnly2709);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_expressionOnly2711); if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nExp;
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
    // $ANTLR end "expressionOnly"


    // $ANTLR start "expression"
    // Generator.g:662:1: expression returns [ASTExpression n] : ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression ;
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
            // Generator.g:668:1: ( ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression )
            // Generator.g:669:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of expression */ 
            }
            // Generator.g:670:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==73) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // Generator.g:671:7: 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in'
            	    {
            	    match(input,73,FOLLOW_73_in_expression2759); if (state.failed) return n;
            	    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_expression2763); if (state.failed) return n;
            	    // Generator.g:671:24: ( COLON t= type )?
            	    int alt59=2;
            	    int LA59_0 = input.LA(1);

            	    if ( (LA59_0==COLON) ) {
            	        alt59=1;
            	    }
            	    switch (alt59) {
            	        case 1 :
            	            // Generator.g:671:26: COLON t= type
            	            {
            	            match(input,COLON,FOLLOW_COLON_in_expression2767); if (state.failed) return n;
            	            pushFollow(FOLLOW_type_in_expression2771);
            	            t=type();

            	            state._fsp--;
            	            if (state.failed) return n;

            	            }
            	            break;

            	    }

            	    match(input,EQUAL,FOLLOW_EQUAL_in_expression2776); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_expression2780);
            	    e1=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    match(input,49,FOLLOW_49_in_expression2782); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       ASTLetExpression nextLet = new ASTLetExpression(name, t, e1);
            	               if ( firstLet == null ) 
            	                   firstLet = nextLet;
            	               if ( prevLet != null ) 
            	                   prevLet.setInExpr(nextLet);
            	               prevLet = nextLet;
            	             
            	    }

            	    }
            	    break;

            	default :
            	    break loop60;
                }
            } while (true);

            pushFollow(FOLLOW_conditionalImpliesExpression_in_expression2807);
            nCndImplies=conditionalImpliesExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
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
    // Generator.g:699:1: paramList returns [List paramList] : LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN ;
    public final List paramList() throws RecognitionException {
        List paramList = null;

        ASTVariableDeclaration v = null;


         paramList = new ArrayList(); 
        try {
            // Generator.g:701:1: ( LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN )
            // Generator.g:702:5: LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN
            {
            match(input,LPAREN,FOLLOW_LPAREN_in_paramList2840); if (state.failed) return paramList;
            // Generator.g:703:5: (v= variableDeclaration ( COMMA v= variableDeclaration )* )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==IDENT) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // Generator.g:704:7: v= variableDeclaration ( COMMA v= variableDeclaration )*
                    {
                    pushFollow(FOLLOW_variableDeclaration_in_paramList2857);
                    v=variableDeclaration();

                    state._fsp--;
                    if (state.failed) return paramList;
                    if ( state.backtracking==0 ) {
                       paramList.add(v); 
                    }
                    // Generator.g:705:7: ( COMMA v= variableDeclaration )*
                    loop61:
                    do {
                        int alt61=2;
                        int LA61_0 = input.LA(1);

                        if ( (LA61_0==COMMA) ) {
                            alt61=1;
                        }


                        switch (alt61) {
                    	case 1 :
                    	    // Generator.g:705:9: COMMA v= variableDeclaration
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_paramList2869); if (state.failed) return paramList;
                    	    pushFollow(FOLLOW_variableDeclaration_in_paramList2873);
                    	    v=variableDeclaration();

                    	    state._fsp--;
                    	    if (state.failed) return paramList;
                    	    if ( state.backtracking==0 ) {
                    	       paramList.add(v); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop61;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_paramList2893); if (state.failed) return paramList;

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
    // Generator.g:713:1: idList returns [List idList] : id0= IDENT ( COMMA idn= IDENT )* ;
    public final List idList() throws RecognitionException {
        List idList = null;

        Token id0=null;
        Token idn=null;

         idList = new ArrayList(); 
        try {
            // Generator.g:715:1: (id0= IDENT ( COMMA idn= IDENT )* )
            // Generator.g:716:5: id0= IDENT ( COMMA idn= IDENT )*
            {
            id0=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList2922); if (state.failed) return idList;
            if ( state.backtracking==0 ) {
               idList.add(id0); 
            }
            // Generator.g:717:5: ( COMMA idn= IDENT )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==COMMA) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // Generator.g:717:7: COMMA idn= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_idList2932); if (state.failed) return idList;
            	    idn=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList2936); if (state.failed) return idList;
            	    if ( state.backtracking==0 ) {
            	       idList.add(idn); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop63;
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
    // Generator.g:725:1: variableDeclaration returns [ASTVariableDeclaration n] : name= IDENT COLON t= type ;
    public final ASTVariableDeclaration variableDeclaration() throws RecognitionException {
        ASTVariableDeclaration n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Generator.g:726:1: (name= IDENT COLON t= type )
            // Generator.g:727:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableDeclaration2967); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableDeclaration2969); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableDeclaration2973);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTVariableDeclaration(name, t); 
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
    // $ANTLR end "variableDeclaration"


    // $ANTLR start "conditionalImpliesExpression"
    // Generator.g:735:1: conditionalImpliesExpression returns [ASTExpression n] : nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* ;
    public final ASTExpression conditionalImpliesExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndOrExp = null;

        ASTExpression n1 = null;


        try {
            // Generator.g:736:1: (nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* )
            // Generator.g:737:5: nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )*
            {
            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3009);
            nCndOrExp=conditionalOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndOrExp;
            }
            // Generator.g:738:5: (op= 'implies' n1= conditionalOrExpression )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==74) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // Generator.g:738:7: op= 'implies' n1= conditionalOrExpression
            	    {
            	    op=(Token)match(input,74,FOLLOW_74_in_conditionalImpliesExpression3022); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3026);
            	    n1=conditionalOrExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop64;
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
    // Generator.g:747:1: conditionalOrExpression returns [ASTExpression n] : nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* ;
    public final ASTExpression conditionalOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndXorExp = null;

        ASTExpression n1 = null;


        try {
            // Generator.g:748:1: (nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* )
            // Generator.g:749:5: nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )*
            {
            pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3071);
            nCndXorExp=conditionalXOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndXorExp;
            }
            // Generator.g:750:5: (op= 'or' n1= conditionalXOrExpression )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( (LA65_0==75) ) {
                    alt65=1;
                }


                switch (alt65) {
            	case 1 :
            	    // Generator.g:750:7: op= 'or' n1= conditionalXOrExpression
            	    {
            	    op=(Token)match(input,75,FOLLOW_75_in_conditionalOrExpression3084); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3088);
            	    n1=conditionalXOrExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop65;
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
    // Generator.g:759:1: conditionalXOrExpression returns [ASTExpression n] : nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* ;
    public final ASTExpression conditionalXOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndAndExp = null;

        ASTExpression n1 = null;


        try {
            // Generator.g:760:1: (nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* )
            // Generator.g:761:5: nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )*
            {
            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3132);
            nCndAndExp=conditionalAndExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndAndExp;
            }
            // Generator.g:762:5: (op= 'xor' n1= conditionalAndExpression )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( (LA66_0==76) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // Generator.g:762:7: op= 'xor' n1= conditionalAndExpression
            	    {
            	    op=(Token)match(input,76,FOLLOW_76_in_conditionalXOrExpression3145); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3149);
            	    n1=conditionalAndExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

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
        return n;
    }
    // $ANTLR end "conditionalXOrExpression"


    // $ANTLR start "conditionalAndExpression"
    // Generator.g:771:1: conditionalAndExpression returns [ASTExpression n] : nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* ;
    public final ASTExpression conditionalAndExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nEqExp = null;

        ASTExpression n1 = null;


        try {
            // Generator.g:772:1: (nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* )
            // Generator.g:773:5: nEqExp= equalityExpression (op= 'and' n1= equalityExpression )*
            {
            pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression3193);
            nEqExp=equalityExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nEqExp;
            }
            // Generator.g:774:5: (op= 'and' n1= equalityExpression )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==77) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // Generator.g:774:7: op= 'and' n1= equalityExpression
            	    {
            	    op=(Token)match(input,77,FOLLOW_77_in_conditionalAndExpression3206); if (state.failed) return n;
            	    pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression3210);
            	    n1=equalityExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

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
    // $ANTLR end "conditionalAndExpression"


    // $ANTLR start "equalityExpression"
    // Generator.g:783:1: equalityExpression returns [ASTExpression n] : nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* ;
    public final ASTExpression equalityExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nRelExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Generator.g:785:1: (nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* )
            // Generator.g:786:5: nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            {
            pushFollow(FOLLOW_relationalExpression_in_equalityExpression3258);
            nRelExp=relationalExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nRelExp;
            }
            // Generator.g:787:5: ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            loop68:
            do {
                int alt68=2;
                int LA68_0 = input.LA(1);

                if ( (LA68_0==EQUAL||LA68_0==NOT_EQUAL) ) {
                    alt68=1;
                }


                switch (alt68) {
            	case 1 :
            	    // Generator.g:787:7: ( EQUAL | NOT_EQUAL ) n1= relationalExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       op = input.LT(1); 
            	    }
            	    if ( input.LA(1)==EQUAL||input.LA(1)==NOT_EQUAL ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression3287);
            	    n1=relationalExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

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
    // $ANTLR end "equalityExpression"


    // $ANTLR start "relationalExpression"
    // Generator.g:797:1: relationalExpression returns [ASTExpression n] : nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* ;
    public final ASTExpression relationalExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nAddiExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Generator.g:799:1: (nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* )
            // Generator.g:800:5: nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            {
            pushFollow(FOLLOW_additiveExpression_in_relationalExpression3336);
            nAddiExp=additiveExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nAddiExp;
            }
            // Generator.g:801:5: ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==LESS||(LA69_0>=GREATER && LA69_0<=GREATER_EQUAL)) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // Generator.g:801:7: ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       op = input.LT(1); 
            	    }
            	    if ( input.LA(1)==LESS||(input.LA(1)>=GREATER && input.LA(1)<=GREATER_EQUAL) ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_additiveExpression_in_relationalExpression3372);
            	    n1=additiveExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

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
    // $ANTLR end "relationalExpression"


    // $ANTLR start "additiveExpression"
    // Generator.g:811:1: additiveExpression returns [ASTExpression n] : nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* ;
    public final ASTExpression additiveExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nMulExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Generator.g:813:1: (nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* )
            // Generator.g:814:5: nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            {
            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3422);
            nMulExp=multiplicativeExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nMulExp;
            }
            // Generator.g:815:5: ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            loop70:
            do {
                int alt70=2;
                int LA70_0 = input.LA(1);

                if ( ((LA70_0>=PLUS && LA70_0<=MINUS)) ) {
                    alt70=1;
                }


                switch (alt70) {
            	case 1 :
            	    // Generator.g:815:7: ( PLUS | MINUS ) n1= multiplicativeExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       op = input.LT(1); 
            	    }
            	    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS) ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3450);
            	    n1=multiplicativeExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

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
    // $ANTLR end "additiveExpression"


    // $ANTLR start "multiplicativeExpression"
    // Generator.g:826:1: multiplicativeExpression returns [ASTExpression n] : nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* ;
    public final ASTExpression multiplicativeExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Generator.g:828:1: (nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* )
            // Generator.g:829:5: nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            {
            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3500);
            nUnExp=unaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nUnExp;
            }
            // Generator.g:830:5: ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            loop71:
            do {
                int alt71=2;
                int LA71_0 = input.LA(1);

                if ( (LA71_0==STAR||LA71_0==SLASH||LA71_0==78) ) {
                    alt71=1;
                }


                switch (alt71) {
            	case 1 :
            	    // Generator.g:830:7: ( STAR | SLASH | 'div' ) n1= unaryExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       op = input.LT(1); 
            	    }
            	    if ( input.LA(1)==STAR||input.LA(1)==SLASH||input.LA(1)==78 ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3532);
            	    n1=unaryExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

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
    // $ANTLR end "multiplicativeExpression"


    // $ANTLR start "unaryExpression"
    // Generator.g:842:1: unaryExpression returns [ASTExpression n] : ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression );
    public final ASTExpression unaryExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression nPosExp = null;


         Token op = null; 
        try {
            // Generator.g:844:1: ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression )
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( ((LA72_0>=PLUS && LA72_0<=MINUS)||LA72_0==79) ) {
                alt72=1;
            }
            else if ( ((LA72_0>=IDENT && LA72_0<=LPAREN)||LA72_0==INT||(LA72_0>=REAL && LA72_0<=HASH)||LA72_0==50||(LA72_0>=81 && LA72_0<=84)||(LA72_0>=86 && LA72_0<=97)) ) {
                alt72=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 72, 0, input);

                throw nvae;
            }
            switch (alt72) {
                case 1 :
                    // Generator.g:845:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    {
                    // Generator.g:845:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    // Generator.g:845:9: ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression
                    {
                    if ( state.backtracking==0 ) {
                       op = input.LT(1); 
                    }
                    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS)||input.LA(1)==79 ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression3618);
                    nUnExp=unaryExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUnaryExpression(op, nUnExp); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // Generator.g:849:7: nPosExp= postfixExpression
                    {
                    pushFollow(FOLLOW_postfixExpression_in_unaryExpression3638);
                    nPosExp=postfixExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nPosExp; 
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
    // $ANTLR end "unaryExpression"


    // $ANTLR start "postfixExpression"
    // Generator.g:857:1: postfixExpression returns [ASTExpression n] : nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* ;
    public final ASTExpression postfixExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nPrimExp = null;

        ASTExpression nPc = null;


         boolean arrow = false; 
        try {
            // Generator.g:859:1: (nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* )
            // Generator.g:860:5: nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            {
            pushFollow(FOLLOW_primaryExpression_in_postfixExpression3671);
            nPrimExp=primaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nPrimExp; 
            }
            // Generator.g:861:5: ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            loop74:
            do {
                int alt74=2;
                int LA74_0 = input.LA(1);

                if ( (LA74_0==DOT||LA74_0==ARROW) ) {
                    alt74=1;
                }


                switch (alt74) {
            	case 1 :
            	    // Generator.g:862:6: ( ARROW | DOT ) nPc= propertyCall[$n, arrow]
            	    {
            	    // Generator.g:862:6: ( ARROW | DOT )
            	    int alt73=2;
            	    int LA73_0 = input.LA(1);

            	    if ( (LA73_0==ARROW) ) {
            	        alt73=1;
            	    }
            	    else if ( (LA73_0==DOT) ) {
            	        alt73=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 73, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt73) {
            	        case 1 :
            	            // Generator.g:862:8: ARROW
            	            {
            	            match(input,ARROW,FOLLOW_ARROW_in_postfixExpression3689); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = true; 
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // Generator.g:862:34: DOT
            	            {
            	            match(input,DOT,FOLLOW_DOT_in_postfixExpression3695); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = false; 
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_propertyCall_in_postfixExpression3706);
            	    nPc=propertyCall(n, arrow);

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = nPc; 
            	    }

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
    // $ANTLR end "postfixExpression"


    // $ANTLR start "primaryExpression"
    // Generator.g:878:1: primaryExpression returns [ASTExpression n] : (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? );
    public final ASTExpression primaryExpression() throws RecognitionException {
        ASTExpression n = null;

        Token id1=null;
        ASTExpression nLit = null;

        ASTExpression nPc = null;

        ASTExpression nExp = null;

        ASTExpression nIfExp = null;


        try {
            // Generator.g:879:1: (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? )
            int alt77=5;
            switch ( input.LA(1) ) {
            case INT:
            case REAL:
            case STRING:
            case HASH:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
                {
                alt77=1;
                }
                break;
            case IDENT:
                {
                switch ( input.LA(2) ) {
                case COLON_COLON:
                    {
                    alt77=1;
                    }
                    break;
                case EOF:
                case IDENT:
                case LPAREN:
                case RPAREN:
                case SEMI:
                case COMMA:
                case LBRACK:
                case RBRACK:
                case RBRACE:
                case LESS:
                case EQUAL:
                case DOTDOT:
                case STAR:
                case NOT_EQUAL:
                case GREATER:
                case LESS_EQUAL:
                case GREATER_EQUAL:
                case PLUS:
                case MINUS:
                case SLASH:
                case ARROW:
                case AT:
                case BAR:
                case 47:
                case 49:
                case 51:
                case 52:
                case 54:
                case 56:
                case 57:
                case 60:
                case 61:
                case 63:
                case 64:
                case 68:
                case 69:
                case 70:
                case 71:
                case 72:
                case 74:
                case 75:
                case 76:
                case 77:
                case 78:
                case 85:
                    {
                    alt77=2;
                    }
                    break;
                case DOT:
                    {
                    int LA77_6 = input.LA(3);

                    if ( (LA77_6==80) ) {
                        alt77=5;
                    }
                    else if ( (LA77_6==IDENT||(LA77_6>=81 && LA77_6<=84)) ) {
                        alt77=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 77, 6, input);

                        throw nvae;
                    }
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 77, 2, input);

                    throw nvae;
                }

                }
                break;
            case 81:
            case 82:
            case 83:
            case 84:
                {
                alt77=2;
                }
                break;
            case LPAREN:
                {
                alt77=3;
                }
                break;
            case 50:
                {
                alt77=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 77, 0, input);

                throw nvae;
            }

            switch (alt77) {
                case 1 :
                    // Generator.g:880:7: nLit= literal
                    {
                    pushFollow(FOLLOW_literal_in_primaryExpression3746);
                    nLit=literal();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nLit; 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:881:7: nPc= propertyCall[null, false]
                    {
                    pushFollow(FOLLOW_propertyCall_in_primaryExpression3758);
                    nPc=propertyCall(null, false);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nPc; 
                    }

                    }
                    break;
                case 3 :
                    // Generator.g:882:7: LPAREN nExp= expression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression3769); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_primaryExpression3773);
                    nExp=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression3775); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExp; 
                    }

                    }
                    break;
                case 4 :
                    // Generator.g:883:7: nIfExp= ifExpression
                    {
                    pushFollow(FOLLOW_ifExpression_in_primaryExpression3787);
                    nIfExp=ifExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nIfExp; 
                    }

                    }
                    break;
                case 5 :
                    // Generator.g:885:7: id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )?
                    {
                    id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_primaryExpression3804); if (state.failed) return n;
                    match(input,DOT,FOLLOW_DOT_in_primaryExpression3806); if (state.failed) return n;
                    match(input,80,FOLLOW_80_in_primaryExpression3808); if (state.failed) return n;
                    // Generator.g:885:36: ( LPAREN RPAREN )?
                    int alt75=2;
                    int LA75_0 = input.LA(1);

                    if ( (LA75_0==LPAREN) ) {
                        alt75=1;
                    }
                    switch (alt75) {
                        case 1 :
                            // Generator.g:885:38: LPAREN RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression3812); if (state.failed) return n;
                            match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression3814); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTAllInstancesExpression(id1); 
                    }
                    // Generator.g:887:7: ( AT 'pre' )?
                    int alt76=2;
                    int LA76_0 = input.LA(1);

                    if ( (LA76_0==AT) ) {
                        alt76=1;
                    }
                    switch (alt76) {
                        case 1 :
                            // Generator.g:887:9: AT 'pre'
                            {
                            match(input,AT,FOLLOW_AT_in_primaryExpression3835); if (state.failed) return n;
                            match(input,71,FOLLOW_71_in_primaryExpression3837); if (state.failed) return n;
                            if ( state.backtracking==0 ) {
                               n.setIsPre(); 
                            }

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
    // Generator.g:900:1: propertyCall[ASTExpression source, boolean followsArrow] returns [ASTExpression n] : ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] );
    public final ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExpQuery = null;

        ASTExpression nExpIterate = null;

        ASTOperationExpression nExpOperation = null;

        ASTTypeArgExpression nExpType = null;


        try {
            // Generator.g:901:1: ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] )
            int alt78=4;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA78_1 = input.LA(2);

                if ( ((( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )&&( input.LA(2) == LPAREN ))) ) {
                    alt78=1;
                }
                else if ( (true) ) {
                    alt78=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 78, 1, input);

                    throw nvae;
                }
                }
                break;
            case 81:
                {
                alt78=2;
                }
                break;
            case 82:
            case 83:
            case 84:
                {
                alt78=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 78, 0, input);

                throw nvae;
            }

            switch (alt78) {
                case 1 :
                    // Generator.g:905:7: {...}?{...}?nExpQuery= queryExpression[source]
                    {
                    if ( !(( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) ");
                    }
                    if ( !(( input.LA(2) == LPAREN )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " input.LA(2) == LPAREN ");
                    }
                    pushFollow(FOLLOW_queryExpression_in_propertyCall3910);
                    nExpQuery=queryExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpQuery; 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:908:7: nExpIterate= iterateExpression[source]
                    {
                    pushFollow(FOLLOW_iterateExpression_in_propertyCall3923);
                    nExpIterate=iterateExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpIterate; 
                    }

                    }
                    break;
                case 3 :
                    // Generator.g:909:7: nExpOperation= operationExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_operationExpression_in_propertyCall3936);
                    nExpOperation=operationExpression(source, followsArrow);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpOperation; 
                    }

                    }
                    break;
                case 4 :
                    // Generator.g:910:7: nExpType= typeExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_typeExpression_in_propertyCall3949);
                    nExpType=typeExpression(source, followsArrow);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpType; 
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
    // $ANTLR end "propertyCall"


    // $ANTLR start "queryExpression"
    // Generator.g:919:1: queryExpression[ASTExpression range] returns [ASTExpression n] : op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN ;
    public final ASTExpression queryExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTElemVarsDeclaration decls = null;

        ASTExpression nExp = null;


        ASTElemVarsDeclaration decl = new ASTElemVarsDeclaration(); 
        try {
            // Generator.g:920:69: (op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN )
            // Generator.g:921:5: op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN
            {
            op=(Token)match(input,IDENT,FOLLOW_IDENT_in_queryExpression3984); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_queryExpression3991); if (state.failed) return n;
            // Generator.g:923:5: (decls= elemVarsDeclaration BAR )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==IDENT) ) {
                int LA79_1 = input.LA(2);

                if ( (LA79_1==COMMA||LA79_1==COLON||LA79_1==BAR) ) {
                    alt79=1;
                }
            }
            switch (alt79) {
                case 1 :
                    // Generator.g:923:7: decls= elemVarsDeclaration BAR
                    {
                    pushFollow(FOLLOW_elemVarsDeclaration_in_queryExpression4002);
                    decls=elemVarsDeclaration();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      decl = decls;
                    }
                    match(input,BAR,FOLLOW_BAR_in_queryExpression4006); if (state.failed) return n;

                    }
                    break;

            }

            pushFollow(FOLLOW_expression_in_queryExpression4017);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_queryExpression4023); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTQueryExpression(op, range, decl, nExp); 
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
    // $ANTLR end "queryExpression"


    // $ANTLR start "iterateExpression"
    // Generator.g:937:1: iterateExpression[ASTExpression range] returns [ASTExpression n] : i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN ;
    public final ASTExpression iterateExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTElemVarsDeclaration decls = null;

        ASTVariableInitialization init = null;

        ASTExpression nExp = null;


        try {
            // Generator.g:937:65: (i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN )
            // Generator.g:938:5: i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN
            {
            i=(Token)match(input,81,FOLLOW_81_in_iterateExpression4055); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_iterateExpression4061); if (state.failed) return n;
            pushFollow(FOLLOW_elemVarsDeclaration_in_iterateExpression4069);
            decls=elemVarsDeclaration();

            state._fsp--;
            if (state.failed) return n;
            match(input,SEMI,FOLLOW_SEMI_in_iterateExpression4071); if (state.failed) return n;
            pushFollow(FOLLOW_variableInitialization_in_iterateExpression4079);
            init=variableInitialization();

            state._fsp--;
            if (state.failed) return n;
            match(input,BAR,FOLLOW_BAR_in_iterateExpression4081); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_iterateExpression4089);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_iterateExpression4095); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTIterateExpression(i, range, decls, init, nExp); 
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
    // $ANTLR end "iterateExpression"


    // $ANTLR start "operationExpression"
    // Generator.g:959:1: operationExpression[ASTExpression source, boolean followsArrow] returns [ASTOperationExpression n] : name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? ;
    public final ASTOperationExpression operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTOperationExpression n = null;

        Token name=null;
        Token rolename=null;
        ASTExpression e = null;


        try {
            // Generator.g:961:1: (name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? )
            // Generator.g:962:5: name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression4139); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTOperationExpression(name, source, followsArrow); 
            }
            // Generator.g:965:5: ( LBRACK rolename= IDENT RBRACK )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==LBRACK) ) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // Generator.g:965:7: LBRACK rolename= IDENT RBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_operationExpression4155); if (state.failed) return n;
                    rolename=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression4159); if (state.failed) return n;
                    match(input,RBRACK,FOLLOW_RBRACK_in_operationExpression4161); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setExplicitRolename(rolename); 
                    }

                    }
                    break;

            }

            // Generator.g:967:5: ( AT 'pre' )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==AT) ) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // Generator.g:967:7: AT 'pre'
                    {
                    match(input,AT,FOLLOW_AT_in_operationExpression4174); if (state.failed) return n;
                    match(input,71,FOLLOW_71_in_operationExpression4176); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setIsPre(); 
                    }

                    }
                    break;

            }

            // Generator.g:968:5: ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==LPAREN) ) {
                alt84=1;
            }
            switch (alt84) {
                case 1 :
                    // Generator.g:969:7: LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_operationExpression4197); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.hasParentheses(); 
                    }
                    // Generator.g:970:7: (e= expression ( COMMA e= expression )* )?
                    int alt83=2;
                    int LA83_0 = input.LA(1);

                    if ( ((LA83_0>=IDENT && LA83_0<=LPAREN)||LA83_0==INT||(LA83_0>=PLUS && LA83_0<=MINUS)||(LA83_0>=REAL && LA83_0<=HASH)||LA83_0==50||LA83_0==73||LA83_0==79||(LA83_0>=81 && LA83_0<=84)||(LA83_0>=86 && LA83_0<=97)) ) {
                        alt83=1;
                    }
                    switch (alt83) {
                        case 1 :
                            // Generator.g:971:7: e= expression ( COMMA e= expression )*
                            {
                            pushFollow(FOLLOW_expression_in_operationExpression4218);
                            e=expression();

                            state._fsp--;
                            if (state.failed) return n;
                            if ( state.backtracking==0 ) {
                               n.addArg(e); 
                            }
                            // Generator.g:972:7: ( COMMA e= expression )*
                            loop82:
                            do {
                                int alt82=2;
                                int LA82_0 = input.LA(1);

                                if ( (LA82_0==COMMA) ) {
                                    alt82=1;
                                }


                                switch (alt82) {
                            	case 1 :
                            	    // Generator.g:972:9: COMMA e= expression
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_operationExpression4230); if (state.failed) return n;
                            	    pushFollow(FOLLOW_expression_in_operationExpression4234);
                            	    e=expression();

                            	    state._fsp--;
                            	    if (state.failed) return n;
                            	    if ( state.backtracking==0 ) {
                            	       n.addArg(e); 
                            	    }

                            	    }
                            	    break;

                            	default :
                            	    break loop82;
                                }
                            } while (true);


                            }
                            break;

                    }

                    match(input,RPAREN,FOLLOW_RPAREN_in_operationExpression4254); if (state.failed) return n;

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
    // Generator.g:984:1: typeExpression[ASTExpression source, boolean followsArrow] returns [ASTTypeArgExpression n] : ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN ;
    public final ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTTypeArgExpression n = null;

        ASTType t = null;


         Token opToken = null; 
        try {
            // Generator.g:987:1: ( ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN )
            // Generator.g:988:2: ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN
            {
            if ( state.backtracking==0 ) {
               opToken = input.LT(1); 
            }
            if ( (input.LA(1)>=82 && input.LA(1)<=84) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_typeExpression4313); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_typeExpression4317);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_typeExpression4319); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTTypeArgExpression(opToken, source, t, followsArrow); 
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
    // $ANTLR end "typeExpression"


    // $ANTLR start "elemVarsDeclaration"
    // Generator.g:999:1: elemVarsDeclaration returns [ASTElemVarsDeclaration n] : idListRes= idList ( COLON t= type )? ;
    public final ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException {
        ASTElemVarsDeclaration n = null;

        List idListRes = null;

        ASTType t = null;


         List idList; 
        try {
            // Generator.g:1001:1: (idListRes= idList ( COLON t= type )? )
            // Generator.g:1002:5: idListRes= idList ( COLON t= type )?
            {
            pushFollow(FOLLOW_idList_in_elemVarsDeclaration4358);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            // Generator.g:1003:5: ( COLON t= type )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==COLON) ) {
                alt85=1;
            }
            switch (alt85) {
                case 1 :
                    // Generator.g:1003:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_elemVarsDeclaration4366); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_elemVarsDeclaration4370);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTElemVarsDeclaration(idListRes, t); 
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
    // $ANTLR end "elemVarsDeclaration"


    // $ANTLR start "variableInitialization"
    // Generator.g:1012:1: variableInitialization returns [ASTVariableInitialization n] : name= IDENT COLON t= type EQUAL e= expression ;
    public final ASTVariableInitialization variableInitialization() throws RecognitionException {
        ASTVariableInitialization n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // Generator.g:1013:1: (name= IDENT COLON t= type EQUAL e= expression )
            // Generator.g:1014:5: name= IDENT COLON t= type EQUAL e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableInitialization4405); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableInitialization4407); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableInitialization4411);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EQUAL,FOLLOW_EQUAL_in_variableInitialization4413); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_variableInitialization4417);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTVariableInitialization(name, t, e); 
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
    // $ANTLR end "variableInitialization"


    // $ANTLR start "ifExpression"
    // Generator.g:1023:1: ifExpression returns [ASTExpression n] : i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' ;
    public final ASTExpression ifExpression() throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTExpression cond = null;

        ASTExpression t = null;

        ASTExpression e = null;


        try {
            // Generator.g:1024:1: (i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' )
            // Generator.g:1025:5: i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif'
            {
            i=(Token)match(input,50,FOLLOW_50_in_ifExpression4449); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4453);
            cond=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,51,FOLLOW_51_in_ifExpression4455); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4459);
            t=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,52,FOLLOW_52_in_ifExpression4461); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4465);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,85,FOLLOW_85_in_ifExpression4467); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTIfExpression(i, cond, t, e); 
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
    // $ANTLR end "ifExpression"


    // $ANTLR start "literal"
    // Generator.g:1045:1: literal returns [ASTExpression n] : (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral );
    public final ASTExpression literal() throws RecognitionException {
        ASTExpression n = null;

        Token t=null;
        Token f=null;
        Token i=null;
        Token r=null;
        Token s=null;
        Token enumLit=null;
        Token enumName=null;
        ASTCollectionLiteral nColIt = null;

        ASTEmptyCollectionLiteral nEColIt = null;

        ASTUndefinedLiteral nUndLit = null;

        ASTTupleLiteral nTupleLit = null;

        ASTDateLiteral nDateLit = null;


        try {
            // Generator.g:1046:1: (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral )
            int alt86=12;
            switch ( input.LA(1) ) {
            case 86:
                {
                alt86=1;
                }
                break;
            case 87:
                {
                alt86=2;
                }
                break;
            case INT:
                {
                alt86=3;
                }
                break;
            case REAL:
                {
                alt86=4;
                }
                break;
            case STRING:
                {
                alt86=5;
                }
                break;
            case HASH:
                {
                alt86=6;
                }
                break;
            case IDENT:
                {
                alt86=7;
                }
                break;
            case 88:
            case 89:
            case 90:
            case 91:
                {
                alt86=8;
                }
                break;
            case 92:
                {
                alt86=9;
                }
                break;
            case 93:
            case 94:
            case 95:
                {
                alt86=10;
                }
                break;
            case 96:
                {
                alt86=11;
                }
                break;
            case 97:
                {
                alt86=12;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 86, 0, input);

                throw nvae;
            }

            switch (alt86) {
                case 1 :
                    // Generator.g:1047:7: t= 'true'
                    {
                    t=(Token)match(input,86,FOLLOW_86_in_literal4506); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(true); 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:1048:7: f= 'false'
                    {
                    f=(Token)match(input,87,FOLLOW_87_in_literal4520); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(false); 
                    }

                    }
                    break;
                case 3 :
                    // Generator.g:1049:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_literal4533); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTIntegerLiteral(i); 
                    }

                    }
                    break;
                case 4 :
                    // Generator.g:1050:7: r= REAL
                    {
                    r=(Token)match(input,REAL,FOLLOW_REAL_in_literal4548); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTRealLiteral(r); 
                    }

                    }
                    break;
                case 5 :
                    // Generator.g:1051:7: s= STRING
                    {
                    s=(Token)match(input,STRING,FOLLOW_STRING_in_literal4562); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTStringLiteral(s); 
                    }

                    }
                    break;
                case 6 :
                    // Generator.g:1052:7: HASH enumLit= IDENT
                    {
                    match(input,HASH,FOLLOW_HASH_in_literal4572); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal4576); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumLit);
                    }

                    }
                    break;
                case 7 :
                    // Generator.g:1053:7: enumName= IDENT '::' enumLit= IDENT
                    {
                    enumName=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal4588); if (state.failed) return n;
                    match(input,COLON_COLON,FOLLOW_COLON_COLON_in_literal4590); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal4594); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumName, enumLit); 
                    }

                    }
                    break;
                case 8 :
                    // Generator.g:1054:7: nColIt= collectionLiteral
                    {
                    pushFollow(FOLLOW_collectionLiteral_in_literal4606);
                    nColIt=collectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nColIt; 
                    }

                    }
                    break;
                case 9 :
                    // Generator.g:1055:7: nEColIt= emptyCollectionLiteral
                    {
                    pushFollow(FOLLOW_emptyCollectionLiteral_in_literal4618);
                    nEColIt=emptyCollectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nEColIt; 
                    }

                    }
                    break;
                case 10 :
                    // Generator.g:1056:7: nUndLit= undefinedLiteral
                    {
                    pushFollow(FOLLOW_undefinedLiteral_in_literal4630);
                    nUndLit=undefinedLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nUndLit; 
                    }

                    }
                    break;
                case 11 :
                    // Generator.g:1057:7: nTupleLit= tupleLiteral
                    {
                    pushFollow(FOLLOW_tupleLiteral_in_literal4642);
                    nTupleLit=tupleLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nTupleLit; 
                    }

                    }
                    break;
                case 12 :
                    // Generator.g:1058:7: nDateLit= dateLiteral
                    {
                    pushFollow(FOLLOW_dateLiteral_in_literal4654);
                    nDateLit=dateLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nDateLit; 
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
    // $ANTLR end "literal"


    // $ANTLR start "collectionLiteral"
    // Generator.g:1066:1: collectionLiteral returns [ASTCollectionLiteral n] : ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE ;
    public final ASTCollectionLiteral collectionLiteral() throws RecognitionException {
        ASTCollectionLiteral n = null;

        ASTCollectionItem ci = null;


         Token op = null; 
        try {
            // Generator.g:1068:1: ( ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE )
            // Generator.g:1069:5: ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE
            {
            if ( state.backtracking==0 ) {
               op = input.LT(1); 
            }
            if ( (input.LA(1)>=88 && input.LA(1)<=91) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            if ( state.backtracking==0 ) {
               n = new ASTCollectionLiteral(op); 
            }
            match(input,LBRACE,FOLLOW_LBRACE_in_collectionLiteral4721); if (state.failed) return n;
            // Generator.g:1073:5: (ci= collectionItem ( COMMA ci= collectionItem )* )?
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( ((LA88_0>=IDENT && LA88_0<=LPAREN)||LA88_0==INT||(LA88_0>=PLUS && LA88_0<=MINUS)||(LA88_0>=REAL && LA88_0<=HASH)||LA88_0==50||LA88_0==73||LA88_0==79||(LA88_0>=81 && LA88_0<=84)||(LA88_0>=86 && LA88_0<=97)) ) {
                alt88=1;
            }
            switch (alt88) {
                case 1 :
                    // Generator.g:1074:7: ci= collectionItem ( COMMA ci= collectionItem )*
                    {
                    pushFollow(FOLLOW_collectionItem_in_collectionLiteral4738);
                    ci=collectionItem();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addItem(ci); 
                    }
                    // Generator.g:1075:7: ( COMMA ci= collectionItem )*
                    loop87:
                    do {
                        int alt87=2;
                        int LA87_0 = input.LA(1);

                        if ( (LA87_0==COMMA) ) {
                            alt87=1;
                        }


                        switch (alt87) {
                    	case 1 :
                    	    // Generator.g:1075:9: COMMA ci= collectionItem
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_collectionLiteral4751); if (state.failed) return n;
                    	    pushFollow(FOLLOW_collectionItem_in_collectionLiteral4755);
                    	    ci=collectionItem();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addItem(ci); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop87;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RBRACE,FOLLOW_RBRACE_in_collectionLiteral4774); if (state.failed) return n;

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
    // Generator.g:1084:1: collectionItem returns [ASTCollectionItem n] : e= expression ( DOTDOT e= expression )? ;
    public final ASTCollectionItem collectionItem() throws RecognitionException {
        ASTCollectionItem n = null;

        ASTExpression e = null;


         n = new ASTCollectionItem(); 
        try {
            // Generator.g:1086:1: (e= expression ( DOTDOT e= expression )? )
            // Generator.g:1087:5: e= expression ( DOTDOT e= expression )?
            {
            pushFollow(FOLLOW_expression_in_collectionItem4803);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setFirst(e); 
            }
            // Generator.g:1088:5: ( DOTDOT e= expression )?
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==DOTDOT) ) {
                alt89=1;
            }
            switch (alt89) {
                case 1 :
                    // Generator.g:1088:7: DOTDOT e= expression
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_collectionItem4814); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_collectionItem4818);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setSecond(e); 
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
        return n;
    }
    // $ANTLR end "collectionItem"


    // $ANTLR start "emptyCollectionLiteral"
    // Generator.g:1098:1: emptyCollectionLiteral returns [ASTEmptyCollectionLiteral n] : 'oclEmpty' LPAREN t= collectionType RPAREN ;
    public final ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException {
        ASTEmptyCollectionLiteral n = null;

        ASTCollectionType t = null;


        try {
            // Generator.g:1099:1: ( 'oclEmpty' LPAREN t= collectionType RPAREN )
            // Generator.g:1100:5: 'oclEmpty' LPAREN t= collectionType RPAREN
            {
            match(input,92,FOLLOW_92_in_emptyCollectionLiteral4847); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_emptyCollectionLiteral4849); if (state.failed) return n;
            pushFollow(FOLLOW_collectionType_in_emptyCollectionLiteral4853);
            t=collectionType();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_emptyCollectionLiteral4855); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTEmptyCollectionLiteral(t); 
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
    // $ANTLR end "emptyCollectionLiteral"


    // $ANTLR start "undefinedLiteral"
    // Generator.g:1111:1: undefinedLiteral returns [ASTUndefinedLiteral n] : ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' );
    public final ASTUndefinedLiteral undefinedLiteral() throws RecognitionException {
        ASTUndefinedLiteral n = null;

        ASTType t = null;


        try {
            // Generator.g:1112:1: ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' )
            int alt90=3;
            switch ( input.LA(1) ) {
            case 93:
                {
                alt90=1;
                }
                break;
            case 94:
                {
                alt90=2;
                }
                break;
            case 95:
                {
                alt90=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 90, 0, input);

                throw nvae;
            }

            switch (alt90) {
                case 1 :
                    // Generator.g:1113:5: 'oclUndefined' LPAREN t= type RPAREN
                    {
                    match(input,93,FOLLOW_93_in_undefinedLiteral4885); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_undefinedLiteral4887); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_undefinedLiteral4891);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_undefinedLiteral4893); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(t); 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:1116:5: 'Undefined'
                    {
                    match(input,94,FOLLOW_94_in_undefinedLiteral4907); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(); 
                    }

                    }
                    break;
                case 3 :
                    // Generator.g:1119:5: 'null'
                    {
                    match(input,95,FOLLOW_95_in_undefinedLiteral4921); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(); 
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
    // $ANTLR end "undefinedLiteral"


    // $ANTLR start "tupleLiteral"
    // Generator.g:1128:1: tupleLiteral returns [ASTTupleLiteral n] : 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE ;
    public final ASTTupleLiteral tupleLiteral() throws RecognitionException {
        ASTTupleLiteral n = null;

        ASTTupleItem ti = null;


         List tiList = new ArrayList(); 
        try {
            // Generator.g:1130:1: ( 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE )
            // Generator.g:1131:5: 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE
            {
            match(input,96,FOLLOW_96_in_tupleLiteral4955); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_tupleLiteral4961); if (state.failed) return n;
            pushFollow(FOLLOW_tupleItem_in_tupleLiteral4969);
            ti=tupleItem();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tiList.add(ti); 
            }
            // Generator.g:1134:5: ( COMMA ti= tupleItem )*
            loop91:
            do {
                int alt91=2;
                int LA91_0 = input.LA(1);

                if ( (LA91_0==COMMA) ) {
                    alt91=1;
                }


                switch (alt91) {
            	case 1 :
            	    // Generator.g:1134:7: COMMA ti= tupleItem
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleLiteral4980); if (state.failed) return n;
            	    pushFollow(FOLLOW_tupleItem_in_tupleLiteral4984);
            	    ti=tupleItem();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tiList.add(ti); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop91;
                }
            } while (true);

            match(input,RBRACE,FOLLOW_RBRACE_in_tupleLiteral4995); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTTupleLiteral(tiList); 
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
    // $ANTLR end "tupleLiteral"


    // $ANTLR start "tupleItem"
    // Generator.g:1142:1: tupleItem returns [ASTTupleItem n] : name= IDENT ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) ;
    public final ASTTupleItem tupleItem() throws RecognitionException {
        ASTTupleItem n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // Generator.g:1143:1: (name= IDENT ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) )
            // Generator.g:1144:5: name= IDENT ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tupleItem5026); if (state.failed) return n;
            // Generator.g:1145:5: ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            int alt92=2;
            int LA92_0 = input.LA(1);

            if ( (LA92_0==COLON) ) {
                int LA92_1 = input.LA(2);

                if ( (synpred1_Generator()) ) {
                    alt92=1;
                }
                else if ( (true) ) {
                    alt92=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 92, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA92_0==EQUAL) ) {
                alt92=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 92, 0, input);

                throw nvae;
            }
            switch (alt92) {
                case 1 :
                    // Generator.g:1148:7: ( COLON type EQUAL )=> COLON t= type EQUAL e= expression
                    {
                    match(input,COLON,FOLLOW_COLON_in_tupleItem5065); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_tupleItem5069);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,EQUAL,FOLLOW_EQUAL_in_tupleItem5071); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_tupleItem5075);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTTupleItem(name, t, e); 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:1151:7: ( COLON | EQUAL ) e= expression
                    {
                    if ( (input.LA(1)>=COLON && input.LA(1)<=EQUAL) ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_expression_in_tupleItem5107);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTTupleItem(name, e); 
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
        return n;
    }
    // $ANTLR end "tupleItem"


    // $ANTLR start "dateLiteral"
    // Generator.g:1160:1: dateLiteral returns [ASTDateLiteral n] : 'Date' LBRACE v= STRING RBRACE ;
    public final ASTDateLiteral dateLiteral() throws RecognitionException {
        ASTDateLiteral n = null;

        Token v=null;

        try {
            // Generator.g:1161:1: ( 'Date' LBRACE v= STRING RBRACE )
            // Generator.g:1162:5: 'Date' LBRACE v= STRING RBRACE
            {
            match(input,97,FOLLOW_97_in_dateLiteral5152); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_dateLiteral5154); if (state.failed) return n;
            v=(Token)match(input,STRING,FOLLOW_STRING_in_dateLiteral5158); if (state.failed) return n;
            match(input,RBRACE,FOLLOW_RBRACE_in_dateLiteral5160); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTDateLiteral( v ); 
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
    // $ANTLR end "dateLiteral"


    // $ANTLR start "type"
    // Generator.g:1172:1: type returns [ASTType n] : (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) ;
    public final ASTType type() throws RecognitionException {
        ASTType n = null;

        ASTSimpleType nTSimple = null;

        ASTCollectionType nTCollection = null;

        ASTTupleType nTTuple = null;


         Token tok = null; 
        try {
            // Generator.g:1174:1: ( (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) )
            // Generator.g:1175:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of type */ 
            }
            // Generator.g:1176:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            int alt93=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt93=1;
                }
                break;
            case 88:
            case 89:
            case 90:
            case 91:
            case 98:
                {
                alt93=2;
                }
                break;
            case 96:
                {
                alt93=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 93, 0, input);

                throw nvae;
            }

            switch (alt93) {
                case 1 :
                    // Generator.g:1177:7: nTSimple= simpleType
                    {
                    pushFollow(FOLLOW_simpleType_in_type5210);
                    nTSimple=simpleType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTSimple; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:1178:7: nTCollection= collectionType
                    {
                    pushFollow(FOLLOW_collectionType_in_type5222);
                    nTCollection=collectionType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTCollection; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 3 :
                    // Generator.g:1179:7: nTTuple= tupleType
                    {
                    pushFollow(FOLLOW_tupleType_in_type5234);
                    nTTuple=tupleType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTTuple; if (n != null) n.setStartToken(tok); 
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
        return n;
    }
    // $ANTLR end "type"


    // $ANTLR start "typeOnly"
    // Generator.g:1184:1: typeOnly returns [ASTType n] : nT= type EOF ;
    public final ASTType typeOnly() throws RecognitionException {
        ASTType n = null;

        ASTType nT = null;


        try {
            // Generator.g:1185:1: (nT= type EOF )
            // Generator.g:1186:5: nT= type EOF
            {
            pushFollow(FOLLOW_type_in_typeOnly5266);
            nT=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_typeOnly5268); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nT; 
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
    // $ANTLR end "typeOnly"


    // $ANTLR start "simpleType"
    // Generator.g:1196:1: simpleType returns [ASTSimpleType n] : name= IDENT ;
    public final ASTSimpleType simpleType() throws RecognitionException {
        ASTSimpleType n = null;

        Token name=null;

        try {
            // Generator.g:1197:1: (name= IDENT )
            // Generator.g:1198:5: name= IDENT
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_simpleType5296); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTSimpleType(name); 
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
    // $ANTLR end "simpleType"


    // $ANTLR start "collectionType"
    // Generator.g:1206:1: collectionType returns [ASTCollectionType n] : ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN ;
    public final ASTCollectionType collectionType() throws RecognitionException {
        ASTCollectionType n = null;

        ASTType elemType = null;


         Token op = null; 
        try {
            // Generator.g:1208:1: ( ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN )
            // Generator.g:1209:5: ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN
            {
            if ( state.backtracking==0 ) {
               op = input.LT(1); 
            }
            if ( (input.LA(1)>=88 && input.LA(1)<=91)||input.LA(1)==98 ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_collectionType5361); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_collectionType5365);
            elemType=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_collectionType5367); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTCollectionType(op, elemType); if (n != null) n.setStartToken(op);
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
    // $ANTLR end "collectionType"


    // $ANTLR start "tupleType"
    // Generator.g:1219:1: tupleType returns [ASTTupleType n] : 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN ;
    public final ASTTupleType tupleType() throws RecognitionException {
        ASTTupleType n = null;

        ASTTuplePart tp = null;


         List tpList = new ArrayList(); 
        try {
            // Generator.g:1221:1: ( 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN )
            // Generator.g:1222:5: 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN
            {
            match(input,96,FOLLOW_96_in_tupleType5401); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_tupleType5403); if (state.failed) return n;
            pushFollow(FOLLOW_tuplePart_in_tupleType5412);
            tp=tuplePart();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tpList.add(tp); 
            }
            // Generator.g:1224:5: ( COMMA tp= tuplePart )*
            loop94:
            do {
                int alt94=2;
                int LA94_0 = input.LA(1);

                if ( (LA94_0==COMMA) ) {
                    alt94=1;
                }


                switch (alt94) {
            	case 1 :
            	    // Generator.g:1224:7: COMMA tp= tuplePart
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleType5423); if (state.failed) return n;
            	    pushFollow(FOLLOW_tuplePart_in_tupleType5427);
            	    tp=tuplePart();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tpList.add(tp); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop94;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_tupleType5439); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTTupleType(tpList); 
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
    // $ANTLR end "tupleType"


    // $ANTLR start "tuplePart"
    // Generator.g:1233:1: tuplePart returns [ASTTuplePart n] : name= IDENT COLON t= type ;
    public final ASTTuplePart tuplePart() throws RecognitionException {
        ASTTuplePart n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Generator.g:1234:1: (name= IDENT COLON t= type )
            // Generator.g:1235:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tuplePart5471); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_tuplePart5473); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_tuplePart5477);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTTuplePart(name, t); 
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
    // $ANTLR end "tuplePart"

    // $ANTLR start synpred1_Generator
    public final void synpred1_Generator_fragment() throws RecognitionException {   
        // Generator.g:1148:7: ( COLON type EQUAL )
        // Generator.g:1148:8: COLON type EQUAL
        {
        match(input,COLON,FOLLOW_COLON_in_synpred1_Generator5056); if (state.failed) return ;
        pushFollow(FOLLOW_type_in_synpred1_Generator5058);
        type();

        state._fsp--;
        if (state.failed) return ;
        match(input,EQUAL,FOLLOW_EQUAL_in_synpred1_Generator5060); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_Generator

    // Delegated rules

    public final boolean synpred1_Generator() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_Generator_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


 

    public static final BitSet FOLLOW_invariant_in_invariantListOnly79 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_EOF_in_invariantListOnly90 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_procedure_in_procedureListOnly135 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_EOF_in_procedureListOnly150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_procedure178 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_procedure182 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_procedure184 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_variableDeclarationList_in_procedure188 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_procedure190 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_45_in_procedure198 = new BitSet(new long[]{0x0000000000000090L});
    public static final BitSet FOLLOW_variableDeclarationList_in_procedure202 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_in_procedure204 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_procedure213 = new BitSet(new long[]{0x0005800000000810L});
    public static final BitSet FOLLOW_instructionList_in_procedure217 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_procedure219 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_in_procedure221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableDeclaration_in_variableDeclarationList259 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_variableDeclarationList270 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclaration_in_variableDeclarationList274 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_instruction_in_instructionList318 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_in_instructionList320 = new BitSet(new long[]{0x0005000000000812L});
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
    public static final BitSet FOLLOW_48_in_loop533 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclaration_in_loop537 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_loop539 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_oclExpression_in_loop543 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_loop545 = new BitSet(new long[]{0x0005800000000810L});
    public static final BitSet FOLLOW_instructionList_in_loop556 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_loop558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_ifThenElse594 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_oclExpression_in_ifThenElse598 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_ifThenElse609 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_ifThenElse611 = new BitSet(new long[]{0x0005800000000810L});
    public static final BitSet FOLLOW_instructionList_in_ifThenElse615 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_ifThenElse617 = new BitSet(new long[]{0x0010000000000002L});
    public static final BitSet FOLLOW_52_in_ifThenElse628 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_ifThenElse630 = new BitSet(new long[]{0x0005800000000810L});
    public static final BitSet FOLLOW_instructionList_in_ifThenElse634 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_ifThenElse636 = new BitSet(new long[]{0x0000000000000002L});
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
    public static final BitSet FOLLOW_LBRACK_in_oclExpression904 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_oclExpression908 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RBRACK_in_oclExpression910 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_procedureCallOnly945 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_procedureCallOnly953 = new BitSet(new long[]{0x0004000E18100070L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_procedureCallOnly964 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_COMMA_in_procedureCallOnly974 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_procedureCallOnly978 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RPAREN_in_procedureCallOnly992 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_procedureCallOnly998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_model1025 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_model1029 = new BitSet(new long[]{0xB3C0000000000010L,0x0000000000000001L});
    public static final BitSet FOLLOW_enumTypeDefinition_in_model1041 = new BitSet(new long[]{0xB3C0000000000010L,0x0000000000000001L});
    public static final BitSet FOLLOW_generalClassDefinition_in_model1058 = new BitSet(new long[]{0xB340000000000010L,0x0000000000000001L});
    public static final BitSet FOLLOW_associationDefinition_in_model1075 = new BitSet(new long[]{0xB340000000000010L,0x0000000000000001L});
    public static final BitSet FOLLOW_54_in_model1091 = new BitSet(new long[]{0xB340000000000010L,0x0000000000000011L});
    public static final BitSet FOLLOW_invariant_in_model1109 = new BitSet(new long[]{0xB340000000000010L,0x0000000000000011L});
    public static final BitSet FOLLOW_prePost_in_model1130 = new BitSet(new long[]{0xB340000000000010L,0x0000000000000011L});
    public static final BitSet FOLLOW_EOF_in_model1171 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_enumTypeDefinition1198 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_enumTypeDefinition1202 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_enumTypeDefinition1204 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_enumTypeDefinition1208 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RBRACE_in_enumTypeDefinition1210 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_enumTypeDefinition1214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_generalClassDefinition1253 = new BitSet(new long[]{0x3300000000000000L});
    public static final BitSet FOLLOW_classDefinition_in_generalClassDefinition1271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_associationClassDefinition_in_generalClassDefinition1291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_classDefinition1330 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_classDefinition1334 = new BitSet(new long[]{0x0C40800000008000L});
    public static final BitSet FOLLOW_LESS_in_classDefinition1344 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_classDefinition1348 = new BitSet(new long[]{0x0C40800000000000L});
    public static final BitSet FOLLOW_58_in_classDefinition1361 = new BitSet(new long[]{0x0840800000000010L});
    public static final BitSet FOLLOW_attributeDefinition_in_classDefinition1374 = new BitSet(new long[]{0x0840800000000010L});
    public static final BitSet FOLLOW_59_in_classDefinition1395 = new BitSet(new long[]{0x0040800000000010L});
    public static final BitSet FOLLOW_operationDefinition_in_classDefinition1408 = new BitSet(new long[]{0x0040800000000010L});
    public static final BitSet FOLLOW_54_in_classDefinition1429 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000060L});
    public static final BitSet FOLLOW_invariantClause_in_classDefinition1449 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000060L});
    public static final BitSet FOLLOW_47_in_classDefinition1473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_associationClassDefinition1506 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationClassDefinition1532 = new BitSet(new long[]{0x4000000000008000L});
    public static final BitSet FOLLOW_LESS_in_associationClassDefinition1542 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_associationClassDefinition1546 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_62_in_associationClassDefinition1557 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_associationEnd_in_associationClassDefinition1565 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_associationEnd_in_associationClassDefinition1577 = new BitSet(new long[]{0x8C40800000000010L,0x0000000000000001L});
    public static final BitSet FOLLOW_58_in_associationClassDefinition1590 = new BitSet(new long[]{0x8840800000000010L,0x0000000000000001L});
    public static final BitSet FOLLOW_attributeDefinition_in_associationClassDefinition1603 = new BitSet(new long[]{0x8840800000000010L,0x0000000000000001L});
    public static final BitSet FOLLOW_59_in_associationClassDefinition1624 = new BitSet(new long[]{0x8040800000000010L,0x0000000000000001L});
    public static final BitSet FOLLOW_operationDefinition_in_associationClassDefinition1637 = new BitSet(new long[]{0x8040800000000010L,0x0000000000000001L});
    public static final BitSet FOLLOW_54_in_associationClassDefinition1658 = new BitSet(new long[]{0x8000800000000000L,0x0000000000000061L});
    public static final BitSet FOLLOW_invariantClause_in_associationClassDefinition1678 = new BitSet(new long[]{0x8000800000000000L,0x0000000000000061L});
    public static final BitSet FOLLOW_set_in_associationClassDefinition1712 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_associationClassDefinition1741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_attributeDefinition1770 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_attributeDefinition1772 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_attributeDefinition1776 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_attributeDefinition1780 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationDefinition1818 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_paramList_in_operationDefinition1826 = new BitSet(new long[]{0x0000000000030082L,0x0000000000000180L});
    public static final BitSet FOLLOW_COLON_in_operationDefinition1834 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_operationDefinition1838 = new BitSet(new long[]{0x0000000000020082L,0x0000000000000180L});
    public static final BitSet FOLLOW_EQUAL_in_operationDefinition1866 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_operationDefinition1870 = new BitSet(new long[]{0x0000000000000082L,0x0000000000000180L});
    public static final BitSet FOLLOW_EQUAL_in_operationDefinition1892 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_keyScript_in_operationDefinition1894 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_SCRIPTBODY_in_operationDefinition1898 = new BitSet(new long[]{0x0000000000000082L,0x0000000000000180L});
    public static final BitSet FOLLOW_prePostClause_in_operationDefinition1929 = new BitSet(new long[]{0x0000000000000082L,0x0000000000000180L});
    public static final BitSet FOLLOW_SEMI_in_operationDefinition1942 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_keyAssociation_in_associationDefinition1980 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_63_in_associationDefinition1984 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_64_in_associationDefinition1988 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationDefinition2003 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_62_in_associationDefinition2011 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_associationEnd_in_associationDefinition2019 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_associationEnd_in_associationDefinition2031 = new BitSet(new long[]{0x0000800000000010L});
    public static final BitSet FOLLOW_47_in_associationDefinition2042 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd2068 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LBRACK_in_associationEnd2070 = new BitSet(new long[]{0x0000000000300000L});
    public static final BitSet FOLLOW_multiplicity_in_associationEnd2074 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RBRACK_in_associationEnd2076 = new BitSet(new long[]{0x0000000000000092L,0x000000000000000EL});
    public static final BitSet FOLLOW_keyRole_in_associationEnd2087 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd2091 = new BitSet(new long[]{0x0000000000000092L,0x000000000000000EL});
    public static final BitSet FOLLOW_65_in_associationEnd2112 = new BitSet(new long[]{0x0000000000000092L,0x000000000000000EL});
    public static final BitSet FOLLOW_66_in_associationEnd2124 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd2128 = new BitSet(new long[]{0x0000000000000092L,0x000000000000000EL});
    public static final BitSet FOLLOW_keyUnion_in_associationEnd2140 = new BitSet(new long[]{0x0000000000000092L,0x000000000000000EL});
    public static final BitSet FOLLOW_67_in_associationEnd2152 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd2156 = new BitSet(new long[]{0x0000000000000092L,0x000000000000000EL});
    public static final BitSet FOLLOW_SEMI_in_associationEnd2173 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity2208 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_multiplicity2218 = new BitSet(new long[]{0x0000000000300000L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity2222 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange2251 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_DOTDOT_in_multiplicityRange2261 = new BitSet(new long[]{0x0000000000300000L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange2265 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_multiplicitySpec2299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_multiplicitySpec2309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_invariant2350 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_invariant2360 = new BitSet(new long[]{0x0000000000010100L});
    public static final BitSet FOLLOW_COMMA_in_invariant2373 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_invariant2377 = new BitSet(new long[]{0x0000000000010100L});
    public static final BitSet FOLLOW_COLON_in_invariant2385 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_simpleType_in_invariant2397 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000060L});
    public static final BitSet FOLLOW_invariantClause_in_invariant2409 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000060L});
    public static final BitSet FOLLOW_69_in_invariantClause2440 = new BitSet(new long[]{0x0000000000010010L});
    public static final BitSet FOLLOW_IDENT_in_invariantClause2446 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_invariantClause2451 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_invariantClause2455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_70_in_invariantClause2465 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_invariantClause2467 = new BitSet(new long[]{0x0000000000010010L});
    public static final BitSet FOLLOW_IDENT_in_invariantClause2473 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_invariantClause2478 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_invariantClause2482 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_prePost2508 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_prePost2512 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_COLON_COLON_in_prePost2514 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_prePost2518 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_paramList_in_prePost2522 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000180L});
    public static final BitSet FOLLOW_COLON_in_prePost2526 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_prePost2530 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_prePostClause_in_prePost2549 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000180L});
    public static final BitSet FOLLOW_set_in_prePostClause2588 = new BitSet(new long[]{0x0000000000010010L});
    public static final BitSet FOLLOW_IDENT_in_prePostClause2603 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_prePostClause2608 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_prePostClause2612 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_keyUnion2634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_keyAssociation2648 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_keyRole2662 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_keyScript2676 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_expressionOnly2709 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_expressionOnly2711 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_expression2759 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_expression2763 = new BitSet(new long[]{0x0000000000030000L});
    public static final BitSet FOLLOW_COLON_in_expression2767 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_expression2771 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_EQUAL_in_expression2776 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_expression2780 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_expression2782 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_conditionalImpliesExpression_in_expression2807 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_paramList2840 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList2857 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_COMMA_in_paramList2869 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList2873 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RPAREN_in_paramList2893 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_idList2922 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_idList2932 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_idList2936 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_IDENT_in_variableDeclaration2967 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_variableDeclaration2969 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_variableDeclaration2973 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3009 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_conditionalImpliesExpression3022 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3026 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3071 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_conditionalOrExpression3084 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3088 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3132 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_conditionalXOrExpression3145 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3149 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression3193 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_conditionalAndExpression3206 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression3210 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression3258 = new BitSet(new long[]{0x0000000000820002L});
    public static final BitSet FOLLOW_set_in_equalityExpression3277 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression3287 = new BitSet(new long[]{0x0000000000820002L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression3336 = new BitSet(new long[]{0x0000000007008002L});
    public static final BitSet FOLLOW_set_in_relationalExpression3354 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression3372 = new BitSet(new long[]{0x0000000007008002L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3422 = new BitSet(new long[]{0x0000000018000002L});
    public static final BitSet FOLLOW_set_in_additiveExpression3440 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3450 = new BitSet(new long[]{0x0000000018000002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3500 = new BitSet(new long[]{0x0000000020200002L,0x0000000000004000L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression3518 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3532 = new BitSet(new long[]{0x0000000020200002L,0x0000000000004000L});
    public static final BitSet FOLLOW_set_in_unaryExpression3594 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression3618 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpression_in_unaryExpression3638 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_postfixExpression3671 = new BitSet(new long[]{0x0000000040000402L});
    public static final BitSet FOLLOW_ARROW_in_postfixExpression3689 = new BitSet(new long[]{0x0000000000000010L,0x00000000001E0000L});
    public static final BitSet FOLLOW_DOT_in_postfixExpression3695 = new BitSet(new long[]{0x0000000000000010L,0x00000000001E0000L});
    public static final BitSet FOLLOW_propertyCall_in_postfixExpression3706 = new BitSet(new long[]{0x0000000040000402L});
    public static final BitSet FOLLOW_literal_in_primaryExpression3746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyCall_in_primaryExpression3758 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression3769 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_primaryExpression3773 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression3775 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifExpression_in_primaryExpression3787 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_primaryExpression3804 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_DOT_in_primaryExpression3806 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_primaryExpression3808 = new BitSet(new long[]{0x0000000080000022L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression3812 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression3814 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_AT_in_primaryExpression3835 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_primaryExpression3837 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_queryExpression_in_propertyCall3910 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterateExpression_in_propertyCall3923 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationExpression_in_propertyCall3936 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeExpression_in_propertyCall3949 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_queryExpression3984 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_queryExpression3991 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_queryExpression4002 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_BAR_in_queryExpression4006 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_queryExpression4017 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_queryExpression4023 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_iterateExpression4055 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_iterateExpression4061 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_iterateExpression4069 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_in_iterateExpression4071 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableInitialization_in_iterateExpression4079 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_BAR_in_iterateExpression4081 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_iterateExpression4089 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_iterateExpression4095 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression4139 = new BitSet(new long[]{0x0000000080000822L});
    public static final BitSet FOLLOW_LBRACK_in_operationExpression4155 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression4159 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RBRACK_in_operationExpression4161 = new BitSet(new long[]{0x0000000080000022L});
    public static final BitSet FOLLOW_AT_in_operationExpression4174 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_operationExpression4176 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_LPAREN_in_operationExpression4197 = new BitSet(new long[]{0x0004000E18100070L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_operationExpression4218 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_COMMA_in_operationExpression4230 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_operationExpression4234 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RPAREN_in_operationExpression4254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_typeExpression4297 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_typeExpression4313 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_typeExpression4317 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_typeExpression4319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_elemVarsDeclaration4358 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_COLON_in_elemVarsDeclaration4366 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_elemVarsDeclaration4370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableInitialization4405 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_variableInitialization4407 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_variableInitialization4411 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_EQUAL_in_variableInitialization4413 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_variableInitialization4417 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_ifExpression4449 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_ifExpression4453 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_ifExpression4455 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_ifExpression4459 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_ifExpression4461 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_ifExpression4465 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_ifExpression4467 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_86_in_literal4506 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_87_in_literal4520 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_literal4533 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REAL_in_literal4548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal4562 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_literal4572 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_literal4576 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_literal4588 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_COLON_COLON_in_literal4590 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_literal4594 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionLiteral_in_literal4606 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyCollectionLiteral_in_literal4618 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedLiteral_in_literal4630 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleLiteral_in_literal4642 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dateLiteral_in_literal4654 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionLiteral4692 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_collectionLiteral4721 = new BitSet(new long[]{0x0004000E18104030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral4738 = new BitSet(new long[]{0x0000000000004100L});
    public static final BitSet FOLLOW_COMMA_in_collectionLiteral4751 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral4755 = new BitSet(new long[]{0x0000000000004100L});
    public static final BitSet FOLLOW_RBRACE_in_collectionLiteral4774 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_collectionItem4803 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_DOTDOT_in_collectionItem4814 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_collectionItem4818 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_emptyCollectionLiteral4847 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_emptyCollectionLiteral4849 = new BitSet(new long[]{0x0000000000000000L,0x000000040F000000L});
    public static final BitSet FOLLOW_collectionType_in_emptyCollectionLiteral4853 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_emptyCollectionLiteral4855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_undefinedLiteral4885 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_undefinedLiteral4887 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_undefinedLiteral4891 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_undefinedLiteral4893 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_undefinedLiteral4907 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_95_in_undefinedLiteral4921 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_tupleLiteral4955 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_tupleLiteral4961 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral4969 = new BitSet(new long[]{0x0000000000004100L});
    public static final BitSet FOLLOW_COMMA_in_tupleLiteral4980 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral4984 = new BitSet(new long[]{0x0000000000004100L});
    public static final BitSet FOLLOW_RBRACE_in_tupleLiteral4995 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tupleItem5026 = new BitSet(new long[]{0x0000000000030000L});
    public static final BitSet FOLLOW_COLON_in_tupleItem5065 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_tupleItem5069 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_EQUAL_in_tupleItem5071 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_tupleItem5075 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_tupleItem5097 = new BitSet(new long[]{0x0004000E18100030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_tupleItem5107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_97_in_dateLiteral5152 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_dateLiteral5154 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRING_in_dateLiteral5158 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RBRACE_in_dateLiteral5160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleType_in_type5210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_type5222 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleType_in_type5234 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeOnly5266 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_typeOnly5268 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_simpleType5296 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionType5334 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_collectionType5361 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_collectionType5365 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_collectionType5367 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_tupleType5401 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_tupleType5403 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType5412 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_COMMA_in_tupleType5423 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType5427 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RPAREN_in_tupleType5439 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tuplePart5471 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_tuplePart5473 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_tuplePart5477 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_synpred1_Generator5056 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_synpred1_Generator5058 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_EQUAL_in_synpred1_Generator5060 = new BitSet(new long[]{0x0000000000000002L});

}