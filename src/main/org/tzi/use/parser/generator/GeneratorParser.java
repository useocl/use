// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 Generator.g 2010-09-07 16:42:54
 
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
import org.tzi.use.parser.soil.ast.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all") public class GeneratorParser extends BaseParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "IDENT", "LPAREN", "RPAREN", "SEMI", "COMMA", "COLON_EQUAL", "DOT", "LBRACK", "RBRACK", "LBRACE", "RBRACE", "LESS", "COLON", "EQUAL", "DOTDOT", "INT", "STAR", "COLON_COLON", "NOT_EQUAL", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "PLUS", "MINUS", "SLASH", "ARROW", "AT", "BAR", "REAL", "STRING", "HASH", "NEWLINE", "WS", "SL_COMMENT", "ML_COMMENT", "SCRIPTBODY", "RANGE_OR_INT", "ESC", "HEX_DIGIT", "VOCAB", "'procedure'", "'var'", "'begin'", "'end'", "'for'", "'in'", "'if'", "'then'", "'else'", "'model'", "'constraints'", "'enum'", "'abstract'", "'class'", "'attributes'", "'operations'", "'associationClass'", "'associationclass'", "'between'", "'aggregation'", "'composition'", "'ordered'", "'subsets'", "'redefines'", "'context'", "'inv'", "'existential'", "'pre'", "'post'", "'let'", "'implies'", "'or'", "'xor'", "'and'", "'div'", "'not'", "'allInstances'", "'iterate'", "'oclAsType'", "'oclIsKindOf'", "'oclIsTypeOf'", "'endif'", "'true'", "'false'", "'Set'", "'Sequence'", "'Bag'", "'OrderedSet'", "'oclEmpty'", "'oclUndefined'", "'Undefined'", "'null'", "'Tuple'", "'Date'", "'Collection'", "'new'", "'destroy'", "'insert'", "'into'", "'delete'", "'from'", "'do'"
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
    public static final int ESC=41;
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
    public static final int T__51=51;
    public static final int COLON_EQUAL=9;
    public static final int SLASH=28;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int COMMA=8;
    public static final int T__59=59;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int EQUAL=17;
    public static final int T__105=105;
    public static final int IDENT=4;
    public static final int PLUS=26;
    public static final int RANGE_OR_INT=40;
    public static final int DOT=10;
    public static final int SCRIPTBODY=39;
    public static final int T__50=50;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int HASH=34;
    public static final int HEX_DIGIT=42;
    public static final int T__102=102;
    public static final int COLON_COLON=21;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int MINUS=27;
    public static final int SEMI=7;
    public static final int COLON=16;
    public static final int NEWLINE=35;
    public static final int VOCAB=43;
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
            this.state.ruleMemo = new HashMap[114+1];
             
             
        }
        

    public String[] getTokenNames() { return GeneratorParser.tokenNames; }
    public String getGrammarFileName() { return "Generator.g"; }



    // $ANTLR start "invariantListOnly"
    // Generator.g:69:1: invariantListOnly returns [List<ASTConstraintDefinition> invariantList] : (def= invariant )* EOF ;
    public final List<ASTConstraintDefinition> invariantListOnly() throws RecognitionException {
        List<ASTConstraintDefinition> invariantList = null;

        ASTConstraintDefinition def = null;


         invariantList = new ArrayList<ASTConstraintDefinition>(); 
        try {
            // Generator.g:71:1: ( (def= invariant )* EOF )
            // Generator.g:72:5: (def= invariant )* EOF
            {
            // Generator.g:72:5: (def= invariant )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==68) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Generator.g:72:7: def= invariant
            	    {
            	    pushFollow(FOLLOW_invariant_in_invariantListOnly80);
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

            match(input,EOF,FOLLOW_EOF_in_invariantListOnly91); if (state.failed) return invariantList;

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
    // Generator.g:121:1: procedureListOnly returns [List<ASTGProcedure> procedureList] : (proc= procedure )* EOF ;
    public final List<ASTGProcedure> procedureListOnly() throws RecognitionException {
        List<ASTGProcedure> procedureList = null;

        ASTGProcedure proc = null;


         procedureList = new ArrayList<ASTGProcedure>(); 
        try {
            // Generator.g:123:1: ( (proc= procedure )* EOF )
            // Generator.g:124:5: (proc= procedure )* EOF
            {
            // Generator.g:124:5: (proc= procedure )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==44) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // Generator.g:125:7: proc= procedure
            	    {
            	    pushFollow(FOLLOW_procedure_in_procedureListOnly136);
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

            match(input,EOF,FOLLOW_EOF_in_procedureListOnly151); if (state.failed) return procedureList;

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
    // Generator.g:136:1: procedure returns [ASTGProcedure proc] : 'procedure' name= IDENT LPAREN parameterDecls= variableDeclarationList RPAREN ( 'var' localDecls= variableDeclarationList SEMI )? 'begin' instructions= instructionList 'end' SEMI ;
    public final ASTGProcedure procedure() throws RecognitionException {
        ASTGProcedure proc = null;

        Token name=null;
        List parameterDecls = null;

        List localDecls = null;

        List instructions = null;


         localDecls = new ArrayList(); 
        try {
            // Generator.g:138:1: ( 'procedure' name= IDENT LPAREN parameterDecls= variableDeclarationList RPAREN ( 'var' localDecls= variableDeclarationList SEMI )? 'begin' instructions= instructionList 'end' SEMI )
            // Generator.g:139:5: 'procedure' name= IDENT LPAREN parameterDecls= variableDeclarationList RPAREN ( 'var' localDecls= variableDeclarationList SEMI )? 'begin' instructions= instructionList 'end' SEMI
            {
            match(input,44,FOLLOW_44_in_procedure179); if (state.failed) return proc;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_procedure183); if (state.failed) return proc;
            match(input,LPAREN,FOLLOW_LPAREN_in_procedure185); if (state.failed) return proc;
            pushFollow(FOLLOW_variableDeclarationList_in_procedure189);
            parameterDecls=variableDeclarationList();

            state._fsp--;
            if (state.failed) return proc;
            match(input,RPAREN,FOLLOW_RPAREN_in_procedure191); if (state.failed) return proc;
            // Generator.g:140:5: ( 'var' localDecls= variableDeclarationList SEMI )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==45) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // Generator.g:140:7: 'var' localDecls= variableDeclarationList SEMI
                    {
                    match(input,45,FOLLOW_45_in_procedure199); if (state.failed) return proc;
                    pushFollow(FOLLOW_variableDeclarationList_in_procedure203);
                    localDecls=variableDeclarationList();

                    state._fsp--;
                    if (state.failed) return proc;
                    match(input,SEMI,FOLLOW_SEMI_in_procedure205); if (state.failed) return proc;

                    }
                    break;

            }

            match(input,46,FOLLOW_46_in_procedure214); if (state.failed) return proc;
            pushFollow(FOLLOW_instructionList_in_procedure218);
            instructions=instructionList();

            state._fsp--;
            if (state.failed) return proc;
            match(input,47,FOLLOW_47_in_procedure220); if (state.failed) return proc;
            match(input,SEMI,FOLLOW_SEMI_in_procedure222); if (state.failed) return proc;
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
    // Generator.g:149:1: variableDeclarationList returns [List varDecls] : (decl= variableDeclaration ( COMMA decl= variableDeclaration )* )? ;
    public final List variableDeclarationList() throws RecognitionException {
        List varDecls = null;

        ASTVariableDeclaration decl = null;


         varDecls = new ArrayList(); 
        try {
            // Generator.g:151:1: ( (decl= variableDeclaration ( COMMA decl= variableDeclaration )* )? )
            // Generator.g:152:5: (decl= variableDeclaration ( COMMA decl= variableDeclaration )* )?
            {
            // Generator.g:152:5: (decl= variableDeclaration ( COMMA decl= variableDeclaration )* )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==IDENT) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // Generator.g:152:7: decl= variableDeclaration ( COMMA decl= variableDeclaration )*
                    {
                    pushFollow(FOLLOW_variableDeclaration_in_variableDeclarationList260);
                    decl=variableDeclaration();

                    state._fsp--;
                    if (state.failed) return varDecls;
                    if ( state.backtracking==0 ) {
                      varDecls.add(decl);
                    }
                    // Generator.g:153:7: ( COMMA decl= variableDeclaration )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0==COMMA) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // Generator.g:153:8: COMMA decl= variableDeclaration
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_variableDeclarationList271); if (state.failed) return varDecls;
                    	    pushFollow(FOLLOW_variableDeclaration_in_variableDeclarationList275);
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
    // Generator.g:161:1: instructionList returns [List instructions] : (instr= instruction SEMI )* ;
    public final List instructionList() throws RecognitionException {
        List instructions = null;

        ASTGInstruction instr = null;


         instructions = new ArrayList(); 
        try {
            // Generator.g:163:1: ( (instr= instruction SEMI )* )
            // Generator.g:164:5: (instr= instruction SEMI )*
            {
            // Generator.g:164:5: (instr= instruction SEMI )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==IDENT||LA6_0==LBRACK||LA6_0==48||LA6_0==50) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // Generator.g:164:7: instr= instruction SEMI
            	    {
            	    pushFollow(FOLLOW_instruction_in_instructionList319);
            	    instr=instruction();

            	    state._fsp--;
            	    if (state.failed) return instructions;
            	    match(input,SEMI,FOLLOW_SEMI_in_instructionList321); if (state.failed) return instructions;
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
    // Generator.g:175:1: instruction returns [ASTGInstruction instr] : (instrVA= variableAssignment | instrAA= attributeAssignment | instrLO= loop | instrAI= atomicInstruction | instrIT= ifThenElse );
    public final ASTGInstruction instruction() throws RecognitionException {
        ASTGInstruction instr = null;

        ASTGVariableAssignment instrVA = null;

        ASTGAttributeAssignment instrAA = null;

        ASTGLoop instrLO = null;

        ASTGAtomicInstruction instrAI = null;

        ASTGIfThenElse instrIT = null;


        try {
            // Generator.g:176:1: (instrVA= variableAssignment | instrAA= attributeAssignment | instrLO= loop | instrAI= atomicInstruction | instrIT= ifThenElse )
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
                    // Generator.g:177:7: instrVA= variableAssignment
                    {
                    pushFollow(FOLLOW_variableAssignment_in_instruction356);
                    instrVA=variableAssignment();

                    state._fsp--;
                    if (state.failed) return instr;
                    if ( state.backtracking==0 ) {
                      instr = instrVA;
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:178:7: instrAA= attributeAssignment
                    {
                    pushFollow(FOLLOW_attributeAssignment_in_instruction371);
                    instrAA=attributeAssignment();

                    state._fsp--;
                    if (state.failed) return instr;
                    if ( state.backtracking==0 ) {
                      instr = instrAA;
                    }

                    }
                    break;
                case 3 :
                    // Generator.g:179:7: instrLO= loop
                    {
                    pushFollow(FOLLOW_loop_in_instruction385);
                    instrLO=loop();

                    state._fsp--;
                    if (state.failed) return instr;
                    if ( state.backtracking==0 ) {
                      instr = instrLO;
                    }

                    }
                    break;
                case 4 :
                    // Generator.g:180:7: instrAI= atomicInstruction
                    {
                    pushFollow(FOLLOW_atomicInstruction_in_instruction402);
                    instrAI=atomicInstruction();

                    state._fsp--;
                    if (state.failed) return instr;
                    if ( state.backtracking==0 ) {
                      instr = instrAI;
                    }

                    }
                    break;
                case 5 :
                    // Generator.g:181:7: instrIT= ifThenElse
                    {
                    pushFollow(FOLLOW_ifThenElse_in_instruction416);
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
    // Generator.g:188:1: variableAssignment returns [ASTGVariableAssignment assignment] : target= IDENT COLON_EQUAL source= valueInstruction ;
    public final ASTGVariableAssignment variableAssignment() throws RecognitionException {
        ASTGVariableAssignment assignment = null;

        Token target=null;
        ASTGValueInstruction source = null;


        try {
            // Generator.g:189:1: (target= IDENT COLON_EQUAL source= valueInstruction )
            // Generator.g:190:5: target= IDENT COLON_EQUAL source= valueInstruction
            {
            target=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableAssignment446); if (state.failed) return assignment;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_variableAssignment448); if (state.failed) return assignment;
            pushFollow(FOLLOW_valueInstruction_in_variableAssignment452);
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
    // Generator.g:198:1: attributeAssignment returns [ASTGAttributeAssignment assignment] : targetObject= oclExpression DOT attributeName= IDENT COLON_EQUAL source= valueInstruction ;
    public final ASTGAttributeAssignment attributeAssignment() throws RecognitionException {
        ASTGAttributeAssignment assignment = null;

        Token attributeName=null;
        ASTGocl targetObject = null;

        ASTGValueInstruction source = null;


        try {
            // Generator.g:199:1: (targetObject= oclExpression DOT attributeName= IDENT COLON_EQUAL source= valueInstruction )
            // Generator.g:200:5: targetObject= oclExpression DOT attributeName= IDENT COLON_EQUAL source= valueInstruction
            {
            pushFollow(FOLLOW_oclExpression_in_attributeAssignment484);
            targetObject=oclExpression();

            state._fsp--;
            if (state.failed) return assignment;
            match(input,DOT,FOLLOW_DOT_in_attributeAssignment486); if (state.failed) return assignment;
            attributeName=(Token)match(input,IDENT,FOLLOW_IDENT_in_attributeAssignment490); if (state.failed) return assignment;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_attributeAssignment498); if (state.failed) return assignment;
            pushFollow(FOLLOW_valueInstruction_in_attributeAssignment502);
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
    // Generator.g:210:1: loop returns [ASTGLoop loop] : t= 'for' decl= variableDeclaration 'in' sequence= oclExpression 'begin' instructions= instructionList 'end' ;
    public final ASTGLoop loop() throws RecognitionException {
        ASTGLoop loop = null;

        Token t=null;
        ASTVariableDeclaration decl = null;

        ASTGocl sequence = null;

        List instructions = null;


        try {
            // Generator.g:211:1: (t= 'for' decl= variableDeclaration 'in' sequence= oclExpression 'begin' instructions= instructionList 'end' )
            // Generator.g:212:5: t= 'for' decl= variableDeclaration 'in' sequence= oclExpression 'begin' instructions= instructionList 'end'
            {
            t=(Token)match(input,48,FOLLOW_48_in_loop534); if (state.failed) return loop;
            pushFollow(FOLLOW_variableDeclaration_in_loop538);
            decl=variableDeclaration();

            state._fsp--;
            if (state.failed) return loop;
            match(input,49,FOLLOW_49_in_loop540); if (state.failed) return loop;
            pushFollow(FOLLOW_oclExpression_in_loop544);
            sequence=oclExpression();

            state._fsp--;
            if (state.failed) return loop;
            match(input,46,FOLLOW_46_in_loop546); if (state.failed) return loop;
            pushFollow(FOLLOW_instructionList_in_loop557);
            instructions=instructionList();

            state._fsp--;
            if (state.failed) return loop;
            match(input,47,FOLLOW_47_in_loop559); if (state.failed) return loop;
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
    // Generator.g:223:1: ifThenElse returns [ASTGIfThenElse ifThenElse] : token= 'if' sequence= oclExpression 'then' 'begin' thenInstructions= instructionList 'end' ( 'else' 'begin' elseInstructions= instructionList 'end' )? ;
    public final ASTGIfThenElse ifThenElse() throws RecognitionException {
        ASTGIfThenElse ifThenElse = null;

        Token token=null;
        ASTGocl sequence = null;

        List thenInstructions = null;

        List elseInstructions = null;


         List elseInstructionsList = new ArrayList(); 
        try {
            // Generator.g:225:1: (token= 'if' sequence= oclExpression 'then' 'begin' thenInstructions= instructionList 'end' ( 'else' 'begin' elseInstructions= instructionList 'end' )? )
            // Generator.g:226:5: token= 'if' sequence= oclExpression 'then' 'begin' thenInstructions= instructionList 'end' ( 'else' 'begin' elseInstructions= instructionList 'end' )?
            {
            token=(Token)match(input,50,FOLLOW_50_in_ifThenElse595); if (state.failed) return ifThenElse;
            pushFollow(FOLLOW_oclExpression_in_ifThenElse599);
            sequence=oclExpression();

            state._fsp--;
            if (state.failed) return ifThenElse;
            match(input,51,FOLLOW_51_in_ifThenElse610); if (state.failed) return ifThenElse;
            match(input,46,FOLLOW_46_in_ifThenElse612); if (state.failed) return ifThenElse;
            pushFollow(FOLLOW_instructionList_in_ifThenElse616);
            thenInstructions=instructionList();

            state._fsp--;
            if (state.failed) return ifThenElse;
            match(input,47,FOLLOW_47_in_ifThenElse618); if (state.failed) return ifThenElse;
            // Generator.g:228:9: ( 'else' 'begin' elseInstructions= instructionList 'end' )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==52) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // Generator.g:228:10: 'else' 'begin' elseInstructions= instructionList 'end'
                    {
                    match(input,52,FOLLOW_52_in_ifThenElse629); if (state.failed) return ifThenElse;
                    match(input,46,FOLLOW_46_in_ifThenElse631); if (state.failed) return ifThenElse;
                    pushFollow(FOLLOW_instructionList_in_ifThenElse635);
                    elseInstructions=instructionList();

                    state._fsp--;
                    if (state.failed) return ifThenElse;
                    match(input,47,FOLLOW_47_in_ifThenElse637); if (state.failed) return ifThenElse;
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
    // Generator.g:237:1: valueInstruction returns [ASTGValueInstruction valueinstr] : (atmoicInstr= atomicInstruction | oclExpr= oclExpression );
    public final ASTGValueInstruction valueInstruction() throws RecognitionException {
        ASTGValueInstruction valueinstr = null;

        ASTGAtomicInstruction atmoicInstr = null;

        ASTGocl oclExpr = null;


        try {
            // Generator.g:238:1: (atmoicInstr= atomicInstruction | oclExpr= oclExpression )
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
                    // Generator.g:239:7: atmoicInstr= atomicInstruction
                    {
                    pushFollow(FOLLOW_atomicInstruction_in_valueInstruction677);
                    atmoicInstr=atomicInstruction();

                    state._fsp--;
                    if (state.failed) return valueinstr;
                    if ( state.backtracking==0 ) {
                      valueinstr = atmoicInstr; 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:240:7: oclExpr= oclExpression
                    {
                    pushFollow(FOLLOW_oclExpression_in_valueInstruction691);
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
    // Generator.g:248:1: atomicInstruction returns [ASTGAtomicInstruction instr] : name= IDENT LPAREN (parameter= instructionParameter ( COMMA parameter= instructionParameter )* )? RPAREN ;
    public final ASTGAtomicInstruction atomicInstruction() throws RecognitionException {
        ASTGAtomicInstruction instr = null;

        Token name=null;
        Object parameter = null;


        try {
            // Generator.g:249:1: (name= IDENT LPAREN (parameter= instructionParameter ( COMMA parameter= instructionParameter )* )? RPAREN )
            // Generator.g:250:5: name= IDENT LPAREN (parameter= instructionParameter ( COMMA parameter= instructionParameter )* )? RPAREN
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_atomicInstruction721); if (state.failed) return instr;
            if ( state.backtracking==0 ) {
               instr = new ASTGAtomicInstruction(name); 
            }
            match(input,LPAREN,FOLLOW_LPAREN_in_atomicInstruction725); if (state.failed) return instr;
            // Generator.g:251:9: (parameter= instructionParameter ( COMMA parameter= instructionParameter )* )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==IDENT||LA11_0==LBRACK) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // Generator.g:251:11: parameter= instructionParameter ( COMMA parameter= instructionParameter )*
                    {
                    pushFollow(FOLLOW_instructionParameter_in_atomicInstruction739);
                    parameter=instructionParameter();

                    state._fsp--;
                    if (state.failed) return instr;
                    if ( state.backtracking==0 ) {
                       instr.addParameter(parameter); 
                    }
                    // Generator.g:252:13: ( COMMA parameter= instructionParameter )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0==COMMA) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // Generator.g:252:15: COMMA parameter= instructionParameter
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_atomicInstruction757); if (state.failed) return instr;
                    	    pushFollow(FOLLOW_instructionParameter_in_atomicInstruction761);
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

            match(input,RPAREN,FOLLOW_RPAREN_in_atomicInstruction809); if (state.failed) return instr;

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
    // Generator.g:263:1: instructionParameter returns [Object parameter] : (parameterOcl= oclExpression | parameterIdent= instrParameterIdent );
    public final Object instructionParameter() throws RecognitionException {
        Object parameter = null;

        ASTGocl parameterOcl = null;

        Token parameterIdent = null;


        try {
            // Generator.g:264:1: (parameterOcl= oclExpression | parameterIdent= instrParameterIdent )
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
                    // Generator.g:265:7: parameterOcl= oclExpression
                    {
                    pushFollow(FOLLOW_oclExpression_in_instructionParameter837);
                    parameterOcl=oclExpression();

                    state._fsp--;
                    if (state.failed) return parameter;
                    if ( state.backtracking==0 ) {
                      parameter = parameterOcl; 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:266:7: parameterIdent= instrParameterIdent
                    {
                    pushFollow(FOLLOW_instrParameterIdent_in_instructionParameter849);
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
    // Generator.g:273:1: instrParameterIdent returns [Token t] : i= IDENT ;
    public final Token instrParameterIdent() throws RecognitionException {
        Token t = null;

        Token i=null;

        try {
            // Generator.g:274:1: (i= IDENT )
            // Generator.g:275:5: i= IDENT
            {
            i=(Token)match(input,IDENT,FOLLOW_IDENT_in_instrParameterIdent877); if (state.failed) return t;
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
    // Generator.g:282:1: oclExpression returns [ASTGocl encapOcl] : i= LBRACK ocl= expression RBRACK ;
    public final ASTGocl oclExpression() throws RecognitionException {
        ASTGocl encapOcl = null;

        Token i=null;
        GeneratorParser.expression_return ocl = null;


        try {
            // Generator.g:283:1: (i= LBRACK ocl= expression RBRACK )
            // Generator.g:284:5: i= LBRACK ocl= expression RBRACK
            {
            i=(Token)match(input,LBRACK,FOLLOW_LBRACK_in_oclExpression905); if (state.failed) return encapOcl;
            pushFollow(FOLLOW_expression_in_oclExpression909);
            ocl=expression();

            state._fsp--;
            if (state.failed) return encapOcl;
            match(input,RBRACK,FOLLOW_RBRACK_in_oclExpression911); if (state.failed) return encapOcl;
            if ( state.backtracking==0 ) {
               encapOcl = new ASTGocl((ocl!=null?ocl.n:null), i); 
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
    // Generator.g:297:1: procedureCallOnly returns [ASTGProcedureCall call] : name= IDENT LPAREN (ocl= expression ( COMMA ocl= expression )* )? RPAREN EOF ;
    public final ASTGProcedureCall procedureCallOnly() throws RecognitionException {
        ASTGProcedureCall call = null;

        Token name=null;
        GeneratorParser.expression_return ocl = null;


        try {
            // Generator.g:298:1: (name= IDENT LPAREN (ocl= expression ( COMMA ocl= expression )* )? RPAREN EOF )
            // Generator.g:299:5: name= IDENT LPAREN (ocl= expression ( COMMA ocl= expression )* )? RPAREN EOF
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_procedureCallOnly946); if (state.failed) return call;
            if ( state.backtracking==0 ) {
              call = new ASTGProcedureCall(name);
            }
            match(input,LPAREN,FOLLOW_LPAREN_in_procedureCallOnly954); if (state.failed) return call;
            // Generator.g:300:12: (ocl= expression ( COMMA ocl= expression )* )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0>=IDENT && LA14_0<=LPAREN)||LA14_0==INT||(LA14_0>=PLUS && LA14_0<=MINUS)||LA14_0==AT||(LA14_0>=REAL && LA14_0<=HASH)||LA14_0==50||LA14_0==73||LA14_0==79||(LA14_0>=81 && LA14_0<=84)||(LA14_0>=86 && LA14_0<=97)) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // Generator.g:301:5: ocl= expression ( COMMA ocl= expression )*
                    {
                    pushFollow(FOLLOW_expression_in_procedureCallOnly965);
                    ocl=expression();

                    state._fsp--;
                    if (state.failed) return call;
                    if ( state.backtracking==0 ) {
                      call.addParameter((ocl!=null?ocl.n:null));
                    }
                    // Generator.g:302:5: ( COMMA ocl= expression )*
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0==COMMA) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // Generator.g:302:7: COMMA ocl= expression
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_procedureCallOnly975); if (state.failed) return call;
                    	    pushFollow(FOLLOW_expression_in_procedureCallOnly979);
                    	    ocl=expression();

                    	    state._fsp--;
                    	    if (state.failed) return call;
                    	    if ( state.backtracking==0 ) {
                    	      call.addParameter((ocl!=null?ocl.n:null));
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

            match(input,RPAREN,FOLLOW_RPAREN_in_procedureCallOnly993); if (state.failed) return call;
            match(input,EOF,FOLLOW_EOF_in_procedureCallOnly999); if (state.failed) return call;

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
    // Generator.g:321:1: model returns [ASTModel n] : 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF ;
    public final ASTModel model() throws RecognitionException {
        ASTModel n = null;

        Token modelName=null;
        ASTEnumTypeDefinition e = null;

        ASTAssociation a = null;

        ASTConstraintDefinition cons = null;

        ASTPrePost ppc = null;


        try {
            // Generator.g:322:1: ( 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF )
            // Generator.g:323:5: 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF
            {
            match(input,53,FOLLOW_53_in_model1026); if (state.failed) return n;
            modelName=(Token)match(input,IDENT,FOLLOW_IDENT_in_model1030); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTModel(modelName); 
            }
            // Generator.g:324:5: (e= enumTypeDefinition )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==55) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // Generator.g:324:7: e= enumTypeDefinition
            	    {
            	    pushFollow(FOLLOW_enumTypeDefinition_in_model1042);
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

            // Generator.g:325:5: ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )*
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
            	    // Generator.g:325:9: ( generalClassDefinition[$n] )
            	    {
            	    // Generator.g:325:9: ( generalClassDefinition[$n] )
            	    // Generator.g:325:11: generalClassDefinition[$n]
            	    {
            	    pushFollow(FOLLOW_generalClassDefinition_in_model1059);
            	    generalClassDefinition(n);

            	    state._fsp--;
            	    if (state.failed) return n;

            	    }


            	    }
            	    break;
            	case 2 :
            	    // Generator.g:326:9: (a= associationDefinition )
            	    {
            	    // Generator.g:326:9: (a= associationDefinition )
            	    // Generator.g:326:11: a= associationDefinition
            	    {
            	    pushFollow(FOLLOW_associationDefinition_in_model1076);
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
            	    // Generator.g:327:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    {
            	    // Generator.g:327:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    // Generator.g:327:11: 'constraints' (cons= invariant | ppc= prePost )*
            	    {
            	    match(input,54,FOLLOW_54_in_model1092); if (state.failed) return n;
            	    // Generator.g:328:11: (cons= invariant | ppc= prePost )*
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
            	    	    // Generator.g:328:15: cons= invariant
            	    	    {
            	    	    pushFollow(FOLLOW_invariant_in_model1110);
            	    	    cons=invariant();

            	    	    state._fsp--;
            	    	    if (state.failed) return n;
            	    	    if ( state.backtracking==0 ) {
            	    	       n.addConstraint(cons); 
            	    	    }

            	    	    }
            	    	    break;
            	    	case 2 :
            	    	    // Generator.g:329:15: ppc= prePost
            	    	    {
            	    	    pushFollow(FOLLOW_prePost_in_model1131);
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

            match(input,EOF,FOLLOW_EOF_in_model1172); if (state.failed) return n;

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
    // Generator.g:340:1: enumTypeDefinition returns [ASTEnumTypeDefinition n] : 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )? ;
    public final ASTEnumTypeDefinition enumTypeDefinition() throws RecognitionException {
        ASTEnumTypeDefinition n = null;

        Token name=null;
        List idListRes = null;


        try {
            // Generator.g:341:1: ( 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )? )
            // Generator.g:342:5: 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )?
            {
            match(input,55,FOLLOW_55_in_enumTypeDefinition1199); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_enumTypeDefinition1203); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_enumTypeDefinition1205); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_enumTypeDefinition1209);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,RBRACE,FOLLOW_RBRACE_in_enumTypeDefinition1211); if (state.failed) return n;
            // Generator.g:342:54: ( SEMI )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==SEMI) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // Generator.g:342:56: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_enumTypeDefinition1215); if (state.failed) return n;

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
    // Generator.g:351:1: generalClassDefinition[ASTModel n] : ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) ) ;
    public final void generalClassDefinition(ASTModel n) throws RecognitionException {
        ASTClass c = null;

        ASTAssociationClass ac = null;


         
          boolean isAbstract = false;

        try {
            // Generator.g:355:1: ( ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) ) )
            // Generator.g:356:5: ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) )
            {
            // Generator.g:356:5: ( 'abstract' )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==56) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // Generator.g:356:7: 'abstract'
                    {
                    match(input,56,FOLLOW_56_in_generalClassDefinition1254); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       isAbstract = true; 
                    }

                    }
                    break;

            }

            // Generator.g:357:5: ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) )
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
                    // Generator.g:357:7: (c= classDefinition[isAbstract] )
                    {
                    // Generator.g:357:7: (c= classDefinition[isAbstract] )
                    // Generator.g:357:9: c= classDefinition[isAbstract]
                    {
                    pushFollow(FOLLOW_classDefinition_in_generalClassDefinition1272);
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
                    // Generator.g:358:7: (ac= associationClassDefinition[isAbstract] )
                    {
                    // Generator.g:358:7: (ac= associationClassDefinition[isAbstract] )
                    // Generator.g:358:9: ac= associationClassDefinition[isAbstract]
                    {
                    pushFollow(FOLLOW_associationClassDefinition_in_generalClassDefinition1292);
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
    // Generator.g:375:1: classDefinition[boolean isAbstract] returns [ASTClass n] : 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end' ;
    public final ASTClass classDefinition(boolean isAbstract) throws RecognitionException {
        ASTClass n = null;

        Token name=null;
        List idListRes = null;

        ASTAttribute a = null;

        ASTOperation op = null;

        ASTInvariantClause inv = null;


         List idList; 
        try {
            // Generator.g:377:1: ( 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end' )
            // Generator.g:378:5: 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end'
            {
            match(input,57,FOLLOW_57_in_classDefinition1331); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_classDefinition1335); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTClass(name, isAbstract); 
            }
            // Generator.g:379:5: ( LESS idListRes= idList )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==LESS) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // Generator.g:379:7: LESS idListRes= idList
                    {
                    match(input,LESS,FOLLOW_LESS_in_classDefinition1345); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_classDefinition1349);
                    idListRes=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addSuperClasses(idListRes); 
                    }

                    }
                    break;

            }

            // Generator.g:380:5: ( 'attributes' (a= attributeDefinition )* )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==58) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // Generator.g:380:7: 'attributes' (a= attributeDefinition )*
                    {
                    match(input,58,FOLLOW_58_in_classDefinition1362); if (state.failed) return n;
                    // Generator.g:381:7: (a= attributeDefinition )*
                    loop22:
                    do {
                        int alt22=2;
                        int LA22_0 = input.LA(1);

                        if ( (LA22_0==IDENT) ) {
                            alt22=1;
                        }


                        switch (alt22) {
                    	case 1 :
                    	    // Generator.g:381:9: a= attributeDefinition
                    	    {
                    	    pushFollow(FOLLOW_attributeDefinition_in_classDefinition1375);
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

            // Generator.g:383:5: ( 'operations' (op= operationDefinition )* )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==59) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // Generator.g:383:7: 'operations' (op= operationDefinition )*
                    {
                    match(input,59,FOLLOW_59_in_classDefinition1396); if (state.failed) return n;
                    // Generator.g:384:7: (op= operationDefinition )*
                    loop24:
                    do {
                        int alt24=2;
                        int LA24_0 = input.LA(1);

                        if ( (LA24_0==IDENT) ) {
                            alt24=1;
                        }


                        switch (alt24) {
                    	case 1 :
                    	    // Generator.g:384:9: op= operationDefinition
                    	    {
                    	    pushFollow(FOLLOW_operationDefinition_in_classDefinition1409);
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

            // Generator.g:386:5: ( 'constraints' (inv= invariantClause )* )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==54) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // Generator.g:386:7: 'constraints' (inv= invariantClause )*
                    {
                    match(input,54,FOLLOW_54_in_classDefinition1430); if (state.failed) return n;
                    // Generator.g:387:7: (inv= invariantClause )*
                    loop26:
                    do {
                        int alt26=2;
                        int LA26_0 = input.LA(1);

                        if ( ((LA26_0>=69 && LA26_0<=70)) ) {
                            alt26=1;
                        }


                        switch (alt26) {
                    	case 1 :
                    	    // Generator.g:388:9: inv= invariantClause
                    	    {
                    	    pushFollow(FOLLOW_invariantClause_in_classDefinition1450);
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

            match(input,47,FOLLOW_47_in_classDefinition1474); if (state.failed) return n;

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
    // Generator.g:409:1: associationClassDefinition[boolean isAbstract] returns [ASTAssociationClass n] : classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end' ;
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
            // Generator.g:411:1: (classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end' )
            // Generator.g:412:5: classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end'
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
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationClassDefinition1533); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociationClass(name, isAbstract); 
            }
            // Generator.g:421:5: ( LESS idListRes= idList )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==LESS) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // Generator.g:421:7: LESS idListRes= idList
                    {
                    match(input,LESS,FOLLOW_LESS_in_associationClassDefinition1543); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_associationClassDefinition1547);
                    idListRes=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addSuperClasses(idListRes); 
                    }

                    }
                    break;

            }

            match(input,62,FOLLOW_62_in_associationClassDefinition1558); if (state.failed) return n;
            pushFollow(FOLLOW_associationEnd_in_associationClassDefinition1566);
            ae=associationEnd();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addEnd(ae); 
            }
            // Generator.g:424:5: (ae= associationEnd )+
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
            	    // Generator.g:424:7: ae= associationEnd
            	    {
            	    pushFollow(FOLLOW_associationEnd_in_associationClassDefinition1578);
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

            // Generator.g:425:5: ( 'attributes' (a= attributeDefinition )* )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==58) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // Generator.g:425:7: 'attributes' (a= attributeDefinition )*
                    {
                    match(input,58,FOLLOW_58_in_associationClassDefinition1591); if (state.failed) return n;
                    // Generator.g:426:7: (a= attributeDefinition )*
                    loop30:
                    do {
                        int alt30=2;
                        int LA30_0 = input.LA(1);

                        if ( (LA30_0==IDENT) ) {
                            alt30=1;
                        }


                        switch (alt30) {
                    	case 1 :
                    	    // Generator.g:426:9: a= attributeDefinition
                    	    {
                    	    pushFollow(FOLLOW_attributeDefinition_in_associationClassDefinition1604);
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

            // Generator.g:428:5: ( 'operations' (op= operationDefinition )* )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==59) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // Generator.g:428:7: 'operations' (op= operationDefinition )*
                    {
                    match(input,59,FOLLOW_59_in_associationClassDefinition1625); if (state.failed) return n;
                    // Generator.g:429:7: (op= operationDefinition )*
                    loop32:
                    do {
                        int alt32=2;
                        int LA32_0 = input.LA(1);

                        if ( (LA32_0==IDENT) ) {
                            alt32=1;
                        }


                        switch (alt32) {
                    	case 1 :
                    	    // Generator.g:429:9: op= operationDefinition
                    	    {
                    	    pushFollow(FOLLOW_operationDefinition_in_associationClassDefinition1638);
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

            // Generator.g:431:5: ( 'constraints' (inv= invariantClause )* )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==54) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // Generator.g:431:7: 'constraints' (inv= invariantClause )*
                    {
                    match(input,54,FOLLOW_54_in_associationClassDefinition1659); if (state.failed) return n;
                    // Generator.g:432:7: (inv= invariantClause )*
                    loop34:
                    do {
                        int alt34=2;
                        int LA34_0 = input.LA(1);

                        if ( ((LA34_0>=69 && LA34_0<=70)) ) {
                            alt34=1;
                        }


                        switch (alt34) {
                    	case 1 :
                    	    // Generator.g:433:9: inv= invariantClause
                    	    {
                    	    pushFollow(FOLLOW_invariantClause_in_associationClassDefinition1679);
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

            // Generator.g:436:5: ( ( 'aggregation' | 'composition' ) )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( ((LA36_0>=63 && LA36_0<=64)) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // Generator.g:436:7: ( 'aggregation' | 'composition' )
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

            match(input,47,FOLLOW_47_in_associationClassDefinition1742); if (state.failed) return n;

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
    // Generator.g:447:1: attributeDefinition returns [ASTAttribute n] : name= IDENT COLON t= type ( SEMI )? ;
    public final ASTAttribute attributeDefinition() throws RecognitionException {
        ASTAttribute n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Generator.g:448:1: (name= IDENT COLON t= type ( SEMI )? )
            // Generator.g:449:5: name= IDENT COLON t= type ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_attributeDefinition1771); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_attributeDefinition1773); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_attributeDefinition1777);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            // Generator.g:449:29: ( SEMI )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==SEMI) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // Generator.g:449:31: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_attributeDefinition1781); if (state.failed) return n;

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
    // Generator.g:458:1: operationDefinition returns [ASTOperation n] : name= IDENT pl= paramList ( COLON t= type )? ( ( EQUAL e= expression ) | ( 'begin' s= stat 'end' ) )? (ppc= prePostClause )* ( SEMI )? ;
    public final ASTOperation operationDefinition() throws RecognitionException {
        ASTOperation n = null;

        Token name=null;
        List pl = null;

        ASTType t = null;

        GeneratorParser.expression_return e = null;

        GeneratorParser.stat_return s = null;

        ASTPrePostClause ppc = null;


        try {
            // Generator.g:459:1: (name= IDENT pl= paramList ( COLON t= type )? ( ( EQUAL e= expression ) | ( 'begin' s= stat 'end' ) )? (ppc= prePostClause )* ( SEMI )? )
            // Generator.g:460:5: name= IDENT pl= paramList ( COLON t= type )? ( ( EQUAL e= expression ) | ( 'begin' s= stat 'end' ) )? (ppc= prePostClause )* ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationDefinition1821); if (state.failed) return n;
            pushFollow(FOLLOW_paramList_in_operationDefinition1831);
            pl=paramList();

            state._fsp--;
            if (state.failed) return n;
            // Generator.g:462:5: ( COLON t= type )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==COLON) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // Generator.g:462:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_operationDefinition1839); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_operationDefinition1845);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTOperation(name, pl, t); 
            }
            // Generator.g:464:5: ( ( EQUAL e= expression ) | ( 'begin' s= stat 'end' ) )?
            int alt39=3;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==EQUAL) ) {
                alt39=1;
            }
            else if ( (LA39_0==46) ) {
                alt39=2;
            }
            switch (alt39) {
                case 1 :
                    // Generator.g:465:9: ( EQUAL e= expression )
                    {
                    // Generator.g:465:9: ( EQUAL e= expression )
                    // Generator.g:465:11: EQUAL e= expression
                    {
                    match(input,EQUAL,FOLLOW_EQUAL_in_operationDefinition1872); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_operationDefinition1878);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setExpression((e!=null?e.n:null)); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // Generator.g:466:9: ( 'begin' s= stat 'end' )
                    {
                    // Generator.g:466:9: ( 'begin' s= stat 'end' )
                    // Generator.g:466:11: 'begin' s= stat 'end'
                    {
                    match(input,46,FOLLOW_46_in_operationDefinition1896); if (state.failed) return n;
                    pushFollow(FOLLOW_stat_in_operationDefinition1902);
                    s=stat();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,47,FOLLOW_47_in_operationDefinition1904); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setStatement((s!=null?s.n:null));  
                    }

                    }


                    }
                    break;

            }

            // Generator.g:468:5: (ppc= prePostClause )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( ((LA40_0>=71 && LA40_0<=72)) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // Generator.g:468:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_operationDefinition1925);
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

            // Generator.g:469:5: ( SEMI )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==SEMI) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // Generator.g:469:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_operationDefinition1938); if (state.failed) return n;

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
    // Generator.g:479:1: associationDefinition returns [ASTAssociation n] : ( keyAssociation | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end' ;
    public final ASTAssociation associationDefinition() throws RecognitionException {
        ASTAssociation n = null;

        Token name=null;
        ASTAssociationEnd ae = null;


         Token t = null; 
        try {
            // Generator.g:481:1: ( ( keyAssociation | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end' )
            // Generator.g:482:5: ( keyAssociation | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end'
            {
            if ( state.backtracking==0 ) {
               t = input.LT(1); 
            }
            // Generator.g:483:5: ( keyAssociation | 'aggregation' | 'composition' )
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
                    // Generator.g:483:7: keyAssociation
                    {
                    pushFollow(FOLLOW_keyAssociation_in_associationDefinition1976);
                    keyAssociation();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 2 :
                    // Generator.g:483:24: 'aggregation'
                    {
                    match(input,63,FOLLOW_63_in_associationDefinition1980); if (state.failed) return n;

                    }
                    break;
                case 3 :
                    // Generator.g:483:40: 'composition'
                    {
                    match(input,64,FOLLOW_64_in_associationDefinition1984); if (state.failed) return n;

                    }
                    break;

            }

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationDefinition1999); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociation(t, name); 
            }
            match(input,62,FOLLOW_62_in_associationDefinition2007); if (state.failed) return n;
            pushFollow(FOLLOW_associationEnd_in_associationDefinition2015);
            ae=associationEnd();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addEnd(ae); 
            }
            // Generator.g:488:5: (ae= associationEnd )+
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
            	    // Generator.g:488:7: ae= associationEnd
            	    {
            	    pushFollow(FOLLOW_associationEnd_in_associationDefinition2027);
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

            match(input,47,FOLLOW_47_in_associationDefinition2038); if (state.failed) return n;

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
    // Generator.g:497:1: associationEnd returns [ASTAssociationEnd n] : name= IDENT LBRACK m= multiplicity RBRACK ( keyRole rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )? ;
    public final ASTAssociationEnd associationEnd() throws RecognitionException {
        ASTAssociationEnd n = null;

        Token name=null;
        Token rn=null;
        Token sr=null;
        Token rd=null;
        ASTMultiplicity m = null;


        try {
            // Generator.g:498:1: (name= IDENT LBRACK m= multiplicity RBRACK ( keyRole rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )? )
            // Generator.g:499:5: name= IDENT LBRACK m= multiplicity RBRACK ( keyRole rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd2064); if (state.failed) return n;
            match(input,LBRACK,FOLLOW_LBRACK_in_associationEnd2066); if (state.failed) return n;
            pushFollow(FOLLOW_multiplicity_in_associationEnd2070);
            m=multiplicity();

            state._fsp--;
            if (state.failed) return n;
            match(input,RBRACK,FOLLOW_RBRACK_in_associationEnd2072); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociationEnd(name, m); 
            }
            // Generator.g:500:5: ( keyRole rn= IDENT )?
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
                    // Generator.g:500:7: keyRole rn= IDENT
                    {
                    pushFollow(FOLLOW_keyRole_in_associationEnd2083);
                    keyRole();

                    state._fsp--;
                    if (state.failed) return n;
                    rn=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd2087); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setRolename(rn); 
                    }

                    }
                    break;

            }

            // Generator.g:501:5: ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )*
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
            	    // Generator.g:502:9: 'ordered'
            	    {
            	    match(input,65,FOLLOW_65_in_associationEnd2108); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.setOrdered(); 
            	    }

            	    }
            	    break;
            	case 2 :
            	    // Generator.g:503:9: 'subsets' sr= IDENT
            	    {
            	    match(input,66,FOLLOW_66_in_associationEnd2120); if (state.failed) return n;
            	    sr=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd2124); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addSubsetsRolename(sr); 
            	    }

            	    }
            	    break;
            	case 3 :
            	    // Generator.g:504:9: keyUnion
            	    {
            	    pushFollow(FOLLOW_keyUnion_in_associationEnd2136);
            	    keyUnion();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.setUnion(true); 
            	    }

            	    }
            	    break;
            	case 4 :
            	    // Generator.g:505:9: 'redefines' rd= IDENT
            	    {
            	    match(input,67,FOLLOW_67_in_associationEnd2148); if (state.failed) return n;
            	    rd=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd2152); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addRedefinesRolename(rd); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop45;
                }
            } while (true);

            // Generator.g:507:5: ( SEMI )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==SEMI) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // Generator.g:507:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_associationEnd2169); if (state.failed) return n;

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
    // Generator.g:521:1: multiplicity returns [ASTMultiplicity n] : mr= multiplicityRange ( COMMA mr= multiplicityRange )* ;
    public final ASTMultiplicity multiplicity() throws RecognitionException {
        ASTMultiplicity n = null;

        ASTMultiplicityRange mr = null;


        try {
            // Generator.g:522:1: (mr= multiplicityRange ( COMMA mr= multiplicityRange )* )
            // Generator.g:523:5: mr= multiplicityRange ( COMMA mr= multiplicityRange )*
            {
            if ( state.backtracking==0 ) {
               
              	Token t = input.LT(1); // remember start position of expression
              	n = new ASTMultiplicity(t);
                  
            }
            pushFollow(FOLLOW_multiplicityRange_in_multiplicity2204);
            mr=multiplicityRange();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addRange(mr); 
            }
            // Generator.g:528:5: ( COMMA mr= multiplicityRange )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==COMMA) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // Generator.g:528:7: COMMA mr= multiplicityRange
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_multiplicity2214); if (state.failed) return n;
            	    pushFollow(FOLLOW_multiplicityRange_in_multiplicity2218);
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
    // Generator.g:531:1: multiplicityRange returns [ASTMultiplicityRange n] : ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? ;
    public final ASTMultiplicityRange multiplicityRange() throws RecognitionException {
        ASTMultiplicityRange n = null;

        int ms1 = 0;

        int ms2 = 0;


        try {
            // Generator.g:532:1: (ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? )
            // Generator.g:533:5: ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )?
            {
            pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange2247);
            ms1=multiplicitySpec();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTMultiplicityRange(ms1); 
            }
            // Generator.g:534:5: ( DOTDOT ms2= multiplicitySpec )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==DOTDOT) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // Generator.g:534:7: DOTDOT ms2= multiplicitySpec
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_multiplicityRange2257); if (state.failed) return n;
                    pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange2261);
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
    // Generator.g:537:1: multiplicitySpec returns [int m] : (i= INT | STAR );
    public final int multiplicitySpec() throws RecognitionException {
        int m = 0;

        Token i=null;

         m = -1; 
        try {
            // Generator.g:539:1: (i= INT | STAR )
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
                    // Generator.g:540:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_multiplicitySpec2295); if (state.failed) return m;
                    if ( state.backtracking==0 ) {
                       m = Integer.parseInt((i!=null?i.getText():null)); 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:541:7: STAR
                    {
                    match(input,STAR,FOLLOW_STAR_in_multiplicitySpec2305); if (state.failed) return m;
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
    // Generator.g:562:1: invariant returns [ASTConstraintDefinition n] : 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )* ;
    public final ASTConstraintDefinition invariant() throws RecognitionException {
        ASTConstraintDefinition n = null;

        Token v=null;
        ASTSimpleType t = null;

        ASTInvariantClause inv = null;


        try {
            // Generator.g:563:1: ( 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )* )
            // Generator.g:564:5: 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )*
            {
            if ( state.backtracking==0 ) {
               n = new ASTConstraintDefinition(); 
            }
            match(input,68,FOLLOW_68_in_invariant2346); if (state.failed) return n;
            // Generator.g:566:5: (v= IDENT ( ',' v= IDENT )* COLON )?
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
                    // Generator.g:566:7: v= IDENT ( ',' v= IDENT )* COLON
                    {
                    v=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariant2356); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addVarName(v); 
                    }
                    // Generator.g:567:8: ( ',' v= IDENT )*
                    loop50:
                    do {
                        int alt50=2;
                        int LA50_0 = input.LA(1);

                        if ( (LA50_0==COMMA) ) {
                            alt50=1;
                        }


                        switch (alt50) {
                    	case 1 :
                    	    // Generator.g:567:9: ',' v= IDENT
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_invariant2369); if (state.failed) return n;
                    	    v=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariant2373); if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addVarName(v); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop50;
                        }
                    } while (true);

                    match(input,COLON,FOLLOW_COLON_in_invariant2381); if (state.failed) return n;

                    }
                    break;

            }

            pushFollow(FOLLOW_simpleType_in_invariant2393);
            t=simpleType();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setType(t); 
            }
            // Generator.g:569:5: (inv= invariantClause )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( ((LA52_0>=69 && LA52_0<=70)) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // Generator.g:569:7: inv= invariantClause
            	    {
            	    pushFollow(FOLLOW_invariantClause_in_invariant2405);
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
    // Generator.g:576:1: invariantClause returns [ASTInvariantClause n] : ( 'inv' (name= IDENT )? COLON e= expression | 'existential' 'inv' (name= IDENT )? COLON e= expression );
    public final ASTInvariantClause invariantClause() throws RecognitionException {
        ASTInvariantClause n = null;

        Token name=null;
        GeneratorParser.expression_return e = null;


        try {
            // Generator.g:577:1: ( 'inv' (name= IDENT )? COLON e= expression | 'existential' 'inv' (name= IDENT )? COLON e= expression )
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
                    // Generator.g:578:7: 'inv' (name= IDENT )? COLON e= expression
                    {
                    match(input,69,FOLLOW_69_in_invariantClause2436); if (state.failed) return n;
                    // Generator.g:578:13: (name= IDENT )?
                    int alt53=2;
                    int LA53_0 = input.LA(1);

                    if ( (LA53_0==IDENT) ) {
                        alt53=1;
                    }
                    switch (alt53) {
                        case 1 :
                            // Generator.g:578:15: name= IDENT
                            {
                            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantClause2442); if (state.failed) return n;

                            }
                            break;

                    }

                    match(input,COLON,FOLLOW_COLON_in_invariantClause2447); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_invariantClause2451);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTInvariantClause(name, (e!=null?e.n:null)); 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:579:7: 'existential' 'inv' (name= IDENT )? COLON e= expression
                    {
                    match(input,70,FOLLOW_70_in_invariantClause2461); if (state.failed) return n;
                    match(input,69,FOLLOW_69_in_invariantClause2463); if (state.failed) return n;
                    // Generator.g:579:27: (name= IDENT )?
                    int alt54=2;
                    int LA54_0 = input.LA(1);

                    if ( (LA54_0==IDENT) ) {
                        alt54=1;
                    }
                    switch (alt54) {
                        case 1 :
                            // Generator.g:579:29: name= IDENT
                            {
                            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantClause2469); if (state.failed) return n;

                            }
                            break;

                    }

                    match(input,COLON,FOLLOW_COLON_in_invariantClause2474); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_invariantClause2478);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTExistentialInvariantClause(name, (e!=null?e.n:null)); 
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
    // Generator.g:590:1: prePost returns [ASTPrePost n] : 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ ;
    public final ASTPrePost prePost() throws RecognitionException {
        ASTPrePost n = null;

        Token classname=null;
        Token opname=null;
        List pl = null;

        ASTType rt = null;

        ASTPrePostClause ppc = null;


        try {
            // Generator.g:591:1: ( 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ )
            // Generator.g:592:5: 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+
            {
            match(input,68,FOLLOW_68_in_prePost2504); if (state.failed) return n;
            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost2508); if (state.failed) return n;
            match(input,COLON_COLON,FOLLOW_COLON_COLON_in_prePost2510); if (state.failed) return n;
            opname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost2514); if (state.failed) return n;
            pushFollow(FOLLOW_paramList_in_prePost2518);
            pl=paramList();

            state._fsp--;
            if (state.failed) return n;
            // Generator.g:592:69: ( COLON rt= type )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==COLON) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // Generator.g:592:71: COLON rt= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_prePost2522); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_prePost2526);
                    rt=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTPrePost(classname, opname, pl, rt); 
            }
            // Generator.g:594:5: (ppc= prePostClause )+
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
            	    // Generator.g:594:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_prePost2545);
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
    // Generator.g:601:1: prePostClause returns [ASTPrePostClause n] : ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression ;
    public final ASTPrePostClause prePostClause() throws RecognitionException {
        ASTPrePostClause n = null;

        Token name=null;
        GeneratorParser.expression_return e = null;


         Token t = null; 
        try {
            // Generator.g:603:1: ( ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression )
            // Generator.g:604:5: ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression
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

            // Generator.g:605:25: (name= IDENT )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==IDENT) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // Generator.g:605:27: name= IDENT
                    {
                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePostClause2599); if (state.failed) return n;

                    }
                    break;

            }

            match(input,COLON,FOLLOW_COLON_in_prePostClause2604); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_prePostClause2608);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTPrePostClause(t, name, (e!=null?e.n:null)); 
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
    // Generator.g:609:1: keyUnion : {...}? IDENT ;
    public final void keyUnion() throws RecognitionException {
        try {
            // Generator.g:609:9: ({...}? IDENT )
            // Generator.g:610:3: {...}? IDENT
            {
            if ( !((input.LT(1).getText().equals("union"))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "keyUnion", "input.LT(1).getText().equals(\"union\")");
            }
            match(input,IDENT,FOLLOW_IDENT_in_keyUnion2630); if (state.failed) return ;

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
    // Generator.g:612:1: keyAssociation : {...}? IDENT ;
    public final void keyAssociation() throws RecognitionException {
        try {
            // Generator.g:612:15: ({...}? IDENT )
            // Generator.g:613:3: {...}? IDENT
            {
            if ( !((input.LT(1).getText().equals("association"))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "keyAssociation", "input.LT(1).getText().equals(\"association\")");
            }
            match(input,IDENT,FOLLOW_IDENT_in_keyAssociation2644); if (state.failed) return ;

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
    // Generator.g:615:1: keyRole : {...}? IDENT ;
    public final void keyRole() throws RecognitionException {
        try {
            // Generator.g:615:8: ({...}? IDENT )
            // Generator.g:616:3: {...}? IDENT
            {
            if ( !((input.LT(1).getText().equals("role"))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "keyRole", "input.LT(1).getText().equals(\"role\")");
            }
            match(input,IDENT,FOLLOW_IDENT_in_keyRole2658); if (state.failed) return ;

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


    // $ANTLR start "expressionOnly"
    // Generator.g:647:1: expressionOnly returns [ASTExpression n] : nExp= expression EOF ;
    public final ASTExpression expressionOnly() throws RecognitionException {
        ASTExpression n = null;

        GeneratorParser.expression_return nExp = null;


        try {
            // Generator.g:648:1: (nExp= expression EOF )
            // Generator.g:649:5: nExp= expression EOF
            {
            pushFollow(FOLLOW_expression_in_expressionOnly2691);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_expressionOnly2693); if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = (nExp!=null?nExp.n:null);
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

    public static class expression_return extends ParserRuleReturnScope {
        public ASTExpression n;
    };

    // $ANTLR start "expression"
    // Generator.g:656:1: expression returns [ASTExpression n] : ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression ;
    public final GeneratorParser.expression_return expression() throws RecognitionException {
        GeneratorParser.expression_return retval = new GeneratorParser.expression_return();
        retval.start = input.LT(1);

        Token name=null;
        ASTType t = null;

        GeneratorParser.expression_return e1 = null;

        ASTExpression nCndImplies = null;


         
          ASTLetExpression prevLet = null, firstLet = null;
          ASTExpression e2;
          Token tok = null;

        try {
            // Generator.g:662:1: ( ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression )
            // Generator.g:663:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of expression */ 
            }
            // Generator.g:664:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==73) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // Generator.g:665:7: 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in'
            	    {
            	    match(input,73,FOLLOW_73_in_expression2741); if (state.failed) return retval;
            	    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_expression2745); if (state.failed) return retval;
            	    // Generator.g:665:24: ( COLON t= type )?
            	    int alt59=2;
            	    int LA59_0 = input.LA(1);

            	    if ( (LA59_0==COLON) ) {
            	        alt59=1;
            	    }
            	    switch (alt59) {
            	        case 1 :
            	            // Generator.g:665:26: COLON t= type
            	            {
            	            match(input,COLON,FOLLOW_COLON_in_expression2749); if (state.failed) return retval;
            	            pushFollow(FOLLOW_type_in_expression2753);
            	            t=type();

            	            state._fsp--;
            	            if (state.failed) return retval;

            	            }
            	            break;

            	    }

            	    match(input,EQUAL,FOLLOW_EQUAL_in_expression2758); if (state.failed) return retval;
            	    pushFollow(FOLLOW_expression_in_expression2762);
            	    e1=expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    match(input,49,FOLLOW_49_in_expression2764); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	       ASTLetExpression nextLet = new ASTLetExpression(name, t, (e1!=null?e1.n:null));
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

            pushFollow(FOLLOW_conditionalImpliesExpression_in_expression2789);
            nCndImplies=conditionalImpliesExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) {
               if ( nCndImplies != null ) {
                  	 retval.n = nCndImplies;
                       retval.n.setStartToken(tok);
                    }
                    
                    if ( prevLet != null ) { 
                       prevLet.setInExpr(retval.n);
                       retval.n = firstLet;
                       retval.n.setStartToken(tok);
                    }
                  
            }

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expression"


    // $ANTLR start "paramList"
    // Generator.g:693:1: paramList returns [List paramList] : LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN ;
    public final List paramList() throws RecognitionException {
        List paramList = null;

        ASTVariableDeclaration v = null;


         paramList = new ArrayList(); 
        try {
            // Generator.g:695:1: ( LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN )
            // Generator.g:696:5: LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN
            {
            match(input,LPAREN,FOLLOW_LPAREN_in_paramList2822); if (state.failed) return paramList;
            // Generator.g:697:5: (v= variableDeclaration ( COMMA v= variableDeclaration )* )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==IDENT) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // Generator.g:698:7: v= variableDeclaration ( COMMA v= variableDeclaration )*
                    {
                    pushFollow(FOLLOW_variableDeclaration_in_paramList2839);
                    v=variableDeclaration();

                    state._fsp--;
                    if (state.failed) return paramList;
                    if ( state.backtracking==0 ) {
                       paramList.add(v); 
                    }
                    // Generator.g:699:7: ( COMMA v= variableDeclaration )*
                    loop61:
                    do {
                        int alt61=2;
                        int LA61_0 = input.LA(1);

                        if ( (LA61_0==COMMA) ) {
                            alt61=1;
                        }


                        switch (alt61) {
                    	case 1 :
                    	    // Generator.g:699:9: COMMA v= variableDeclaration
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_paramList2851); if (state.failed) return paramList;
                    	    pushFollow(FOLLOW_variableDeclaration_in_paramList2855);
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

            match(input,RPAREN,FOLLOW_RPAREN_in_paramList2875); if (state.failed) return paramList;

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
    // Generator.g:707:1: idList returns [List idList] : id0= IDENT ( COMMA idn= IDENT )* ;
    public final List idList() throws RecognitionException {
        List idList = null;

        Token id0=null;
        Token idn=null;

         idList = new ArrayList(); 
        try {
            // Generator.g:709:1: (id0= IDENT ( COMMA idn= IDENT )* )
            // Generator.g:710:5: id0= IDENT ( COMMA idn= IDENT )*
            {
            id0=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList2904); if (state.failed) return idList;
            if ( state.backtracking==0 ) {
               idList.add(id0); 
            }
            // Generator.g:711:5: ( COMMA idn= IDENT )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==COMMA) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // Generator.g:711:7: COMMA idn= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_idList2914); if (state.failed) return idList;
            	    idn=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList2918); if (state.failed) return idList;
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
    // Generator.g:719:1: variableDeclaration returns [ASTVariableDeclaration n] : name= IDENT COLON t= type ;
    public final ASTVariableDeclaration variableDeclaration() throws RecognitionException {
        ASTVariableDeclaration n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Generator.g:720:1: (name= IDENT COLON t= type )
            // Generator.g:721:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableDeclaration2949); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableDeclaration2951); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableDeclaration2955);
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
    // Generator.g:729:1: conditionalImpliesExpression returns [ASTExpression n] : nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* ;
    public final ASTExpression conditionalImpliesExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndOrExp = null;

        ASTExpression n1 = null;


        try {
            // Generator.g:730:1: (nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* )
            // Generator.g:731:5: nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )*
            {
            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2991);
            nCndOrExp=conditionalOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndOrExp;
            }
            // Generator.g:732:5: (op= 'implies' n1= conditionalOrExpression )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==74) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // Generator.g:732:7: op= 'implies' n1= conditionalOrExpression
            	    {
            	    op=(Token)match(input,74,FOLLOW_74_in_conditionalImpliesExpression3004); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3008);
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
    // Generator.g:741:1: conditionalOrExpression returns [ASTExpression n] : nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* ;
    public final ASTExpression conditionalOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndXorExp = null;

        ASTExpression n1 = null;


        try {
            // Generator.g:742:1: (nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* )
            // Generator.g:743:5: nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )*
            {
            pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3053);
            nCndXorExp=conditionalXOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndXorExp;
            }
            // Generator.g:744:5: (op= 'or' n1= conditionalXOrExpression )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( (LA65_0==75) ) {
                    alt65=1;
                }


                switch (alt65) {
            	case 1 :
            	    // Generator.g:744:7: op= 'or' n1= conditionalXOrExpression
            	    {
            	    op=(Token)match(input,75,FOLLOW_75_in_conditionalOrExpression3066); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3070);
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
    // Generator.g:753:1: conditionalXOrExpression returns [ASTExpression n] : nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* ;
    public final ASTExpression conditionalXOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndAndExp = null;

        ASTExpression n1 = null;


        try {
            // Generator.g:754:1: (nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* )
            // Generator.g:755:5: nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )*
            {
            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3114);
            nCndAndExp=conditionalAndExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndAndExp;
            }
            // Generator.g:756:5: (op= 'xor' n1= conditionalAndExpression )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( (LA66_0==76) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // Generator.g:756:7: op= 'xor' n1= conditionalAndExpression
            	    {
            	    op=(Token)match(input,76,FOLLOW_76_in_conditionalXOrExpression3127); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3131);
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
    // Generator.g:765:1: conditionalAndExpression returns [ASTExpression n] : nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* ;
    public final ASTExpression conditionalAndExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nEqExp = null;

        ASTExpression n1 = null;


        try {
            // Generator.g:766:1: (nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* )
            // Generator.g:767:5: nEqExp= equalityExpression (op= 'and' n1= equalityExpression )*
            {
            pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression3175);
            nEqExp=equalityExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nEqExp;
            }
            // Generator.g:768:5: (op= 'and' n1= equalityExpression )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==77) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // Generator.g:768:7: op= 'and' n1= equalityExpression
            	    {
            	    op=(Token)match(input,77,FOLLOW_77_in_conditionalAndExpression3188); if (state.failed) return n;
            	    pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression3192);
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
    // Generator.g:777:1: equalityExpression returns [ASTExpression n] : nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* ;
    public final ASTExpression equalityExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nRelExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Generator.g:779:1: (nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* )
            // Generator.g:780:5: nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            {
            pushFollow(FOLLOW_relationalExpression_in_equalityExpression3240);
            nRelExp=relationalExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nRelExp;
            }
            // Generator.g:781:5: ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            loop68:
            do {
                int alt68=2;
                int LA68_0 = input.LA(1);

                if ( (LA68_0==EQUAL||LA68_0==NOT_EQUAL) ) {
                    alt68=1;
                }


                switch (alt68) {
            	case 1 :
            	    // Generator.g:781:7: ( EQUAL | NOT_EQUAL ) n1= relationalExpression
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

            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression3269);
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
    // Generator.g:791:1: relationalExpression returns [ASTExpression n] : nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* ;
    public final ASTExpression relationalExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nAddiExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Generator.g:793:1: (nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* )
            // Generator.g:794:5: nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            {
            pushFollow(FOLLOW_additiveExpression_in_relationalExpression3318);
            nAddiExp=additiveExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nAddiExp;
            }
            // Generator.g:795:5: ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==LESS||(LA69_0>=GREATER && LA69_0<=GREATER_EQUAL)) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // Generator.g:795:7: ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression
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

            	    pushFollow(FOLLOW_additiveExpression_in_relationalExpression3354);
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
    // Generator.g:805:1: additiveExpression returns [ASTExpression n] : nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* ;
    public final ASTExpression additiveExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nMulExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Generator.g:807:1: (nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* )
            // Generator.g:808:5: nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            {
            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3404);
            nMulExp=multiplicativeExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nMulExp;
            }
            // Generator.g:809:5: ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            loop70:
            do {
                int alt70=2;
                int LA70_0 = input.LA(1);

                if ( ((LA70_0>=PLUS && LA70_0<=MINUS)) ) {
                    alt70=1;
                }


                switch (alt70) {
            	case 1 :
            	    // Generator.g:809:7: ( PLUS | MINUS ) n1= multiplicativeExpression
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

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3432);
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
    // Generator.g:820:1: multiplicativeExpression returns [ASTExpression n] : nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* ;
    public final ASTExpression multiplicativeExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Generator.g:822:1: (nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* )
            // Generator.g:823:5: nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            {
            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3482);
            nUnExp=unaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nUnExp;
            }
            // Generator.g:824:5: ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            loop71:
            do {
                int alt71=2;
                int LA71_0 = input.LA(1);

                if ( (LA71_0==STAR||LA71_0==SLASH||LA71_0==78) ) {
                    alt71=1;
                }


                switch (alt71) {
            	case 1 :
            	    // Generator.g:824:7: ( STAR | SLASH | 'div' ) n1= unaryExpression
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

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3514);
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
    // Generator.g:836:1: unaryExpression returns [ASTExpression n] : ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression );
    public final ASTExpression unaryExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression nPosExp = null;


         Token op = null; 
        try {
            // Generator.g:838:1: ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression )
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( ((LA72_0>=PLUS && LA72_0<=MINUS)||LA72_0==79) ) {
                alt72=1;
            }
            else if ( ((LA72_0>=IDENT && LA72_0<=LPAREN)||LA72_0==INT||LA72_0==AT||(LA72_0>=REAL && LA72_0<=HASH)||LA72_0==50||(LA72_0>=81 && LA72_0<=84)||(LA72_0>=86 && LA72_0<=97)) ) {
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
                    // Generator.g:839:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    {
                    // Generator.g:839:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    // Generator.g:839:9: ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression
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

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression3600);
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
                    // Generator.g:843:7: nPosExp= postfixExpression
                    {
                    pushFollow(FOLLOW_postfixExpression_in_unaryExpression3620);
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
    // Generator.g:851:1: postfixExpression returns [ASTExpression n] : nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* ;
    public final ASTExpression postfixExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nPrimExp = null;

        ASTExpression nPc = null;


         boolean arrow = false; 
        try {
            // Generator.g:853:1: (nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* )
            // Generator.g:854:5: nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            {
            pushFollow(FOLLOW_primaryExpression_in_postfixExpression3653);
            nPrimExp=primaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nPrimExp; 
            }
            // Generator.g:855:5: ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            loop74:
            do {
                int alt74=2;
                int LA74_0 = input.LA(1);

                if ( (LA74_0==DOT) ) {
                    int LA74_2 = input.LA(2);

                    if ( (LA74_2==IDENT) ) {
                        int LA74_4 = input.LA(3);

                        if ( (LA74_4==EOF||(LA74_4>=IDENT && LA74_4<=COMMA)||(LA74_4>=DOT && LA74_4<=RBRACK)||(LA74_4>=RBRACE && LA74_4<=LESS)||(LA74_4>=EQUAL && LA74_4<=DOTDOT)||LA74_4==STAR||(LA74_4>=NOT_EQUAL && LA74_4<=BAR)||LA74_4==47||LA74_4==49||(LA74_4>=51 && LA74_4<=52)||LA74_4==54||(LA74_4>=56 && LA74_4<=57)||(LA74_4>=60 && LA74_4<=61)||(LA74_4>=63 && LA74_4<=64)||(LA74_4>=68 && LA74_4<=72)||(LA74_4>=74 && LA74_4<=78)||LA74_4==85||LA74_4==105) ) {
                            alt74=1;
                        }


                    }
                    else if ( ((LA74_2>=81 && LA74_2<=84)) ) {
                        alt74=1;
                    }


                }
                else if ( (LA74_0==ARROW) ) {
                    alt74=1;
                }


                switch (alt74) {
            	case 1 :
            	    // Generator.g:856:6: ( ARROW | DOT ) nPc= propertyCall[$n, arrow]
            	    {
            	    // Generator.g:856:6: ( ARROW | DOT )
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
            	            // Generator.g:856:8: ARROW
            	            {
            	            match(input,ARROW,FOLLOW_ARROW_in_postfixExpression3671); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = true; 
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // Generator.g:856:34: DOT
            	            {
            	            match(input,DOT,FOLLOW_DOT_in_postfixExpression3677); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = false; 
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_propertyCall_in_postfixExpression3688);
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
    // Generator.g:872:1: primaryExpression returns [ASTExpression n] : (nLit= literal | nOr= objectReference | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? );
    public final ASTExpression primaryExpression() throws RecognitionException {
        ASTExpression n = null;

        Token id1=null;
        ASTExpression nLit = null;

        ASTExpression nOr = null;

        ASTExpression nPc = null;

        GeneratorParser.expression_return nExp = null;

        ASTExpression nIfExp = null;


        try {
            // Generator.g:873:1: (nLit= literal | nOr= objectReference | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? )
            int alt77=6;
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
                case 105:
                    {
                    alt77=3;
                    }
                    break;
                case DOT:
                    {
                    int LA77_7 = input.LA(3);

                    if ( (LA77_7==80) ) {
                        alt77=6;
                    }
                    else if ( (LA77_7==IDENT||(LA77_7>=81 && LA77_7<=84)) ) {
                        alt77=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 77, 7, input);

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
            case AT:
                {
                alt77=2;
                }
                break;
            case 81:
            case 82:
            case 83:
            case 84:
                {
                alt77=3;
                }
                break;
            case LPAREN:
                {
                alt77=4;
                }
                break;
            case 50:
                {
                alt77=5;
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
                    // Generator.g:874:7: nLit= literal
                    {
                    pushFollow(FOLLOW_literal_in_primaryExpression3728);
                    nLit=literal();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nLit; 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:875:7: nOr= objectReference
                    {
                    pushFollow(FOLLOW_objectReference_in_primaryExpression3742);
                    nOr=objectReference();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nOr; 
                    }

                    }
                    break;
                case 3 :
                    // Generator.g:876:7: nPc= propertyCall[null, false]
                    {
                    pushFollow(FOLLOW_propertyCall_in_primaryExpression3754);
                    nPc=propertyCall(null, false);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nPc; 
                    }

                    }
                    break;
                case 4 :
                    // Generator.g:877:7: LPAREN nExp= expression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression3765); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_primaryExpression3769);
                    nExp=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression3771); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (nExp!=null?nExp.n:null); 
                    }

                    }
                    break;
                case 5 :
                    // Generator.g:878:7: nIfExp= ifExpression
                    {
                    pushFollow(FOLLOW_ifExpression_in_primaryExpression3783);
                    nIfExp=ifExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nIfExp; 
                    }

                    }
                    break;
                case 6 :
                    // Generator.g:880:7: id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )?
                    {
                    id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_primaryExpression3800); if (state.failed) return n;
                    match(input,DOT,FOLLOW_DOT_in_primaryExpression3802); if (state.failed) return n;
                    match(input,80,FOLLOW_80_in_primaryExpression3804); if (state.failed) return n;
                    // Generator.g:880:36: ( LPAREN RPAREN )?
                    int alt75=2;
                    int LA75_0 = input.LA(1);

                    if ( (LA75_0==LPAREN) ) {
                        alt75=1;
                    }
                    switch (alt75) {
                        case 1 :
                            // Generator.g:880:38: LPAREN RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression3808); if (state.failed) return n;
                            match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression3810); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTAllInstancesExpression(id1); 
                    }
                    // Generator.g:882:7: ( AT 'pre' )?
                    int alt76=2;
                    int LA76_0 = input.LA(1);

                    if ( (LA76_0==AT) ) {
                        alt76=1;
                    }
                    switch (alt76) {
                        case 1 :
                            // Generator.g:882:9: AT 'pre'
                            {
                            match(input,AT,FOLLOW_AT_in_primaryExpression3831); if (state.failed) return n;
                            match(input,71,FOLLOW_71_in_primaryExpression3833); if (state.failed) return n;
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


    // $ANTLR start "objectReference"
    // Generator.g:886:1: objectReference returns [ASTExpression n] : AT objectName= IDENT ;
    public final ASTExpression objectReference() throws RecognitionException {
        ASTExpression n = null;

        Token objectName=null;

        try {
            // Generator.g:887:1: ( AT objectName= IDENT )
            // Generator.g:888:3: AT objectName= IDENT
            {
            match(input,AT,FOLLOW_AT_in_objectReference3860); if (state.failed) return n;
            objectName=(Token)match(input,IDENT,FOLLOW_IDENT_in_objectReference3868); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTObjectReferenceExpression(objectName); 
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
    // $ANTLR end "objectReference"


    // $ANTLR start "propertyCall"
    // Generator.g:903:1: propertyCall[ASTExpression source, boolean followsArrow] returns [ASTExpression n] : ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] );
    public final ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExpQuery = null;

        ASTExpression nExpIterate = null;

        GeneratorParser.operationExpression_return nExpOperation = null;

        ASTTypeArgExpression nExpType = null;


        try {
            // Generator.g:904:1: ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] )
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
                    // Generator.g:908:7: {...}?{...}?nExpQuery= queryExpression[source]
                    {
                    if ( !(( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) ");
                    }
                    if ( !(( input.LA(2) == LPAREN )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " input.LA(2) == LPAREN ");
                    }
                    pushFollow(FOLLOW_queryExpression_in_propertyCall3936);
                    nExpQuery=queryExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpQuery; 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:911:7: nExpIterate= iterateExpression[source]
                    {
                    pushFollow(FOLLOW_iterateExpression_in_propertyCall3949);
                    nExpIterate=iterateExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpIterate; 
                    }

                    }
                    break;
                case 3 :
                    // Generator.g:912:7: nExpOperation= operationExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_operationExpression_in_propertyCall3962);
                    nExpOperation=operationExpression(source, followsArrow);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (nExpOperation!=null?nExpOperation.n:null); 
                    }

                    }
                    break;
                case 4 :
                    // Generator.g:913:7: nExpType= typeExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_typeExpression_in_propertyCall3975);
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
    // Generator.g:922:1: queryExpression[ASTExpression range] returns [ASTExpression n] : op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN ;
    public final ASTExpression queryExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTElemVarsDeclaration decls = null;

        GeneratorParser.expression_return nExp = null;


        ASTElemVarsDeclaration decl = new ASTElemVarsDeclaration(); 
        try {
            // Generator.g:923:69: (op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN )
            // Generator.g:924:5: op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN
            {
            op=(Token)match(input,IDENT,FOLLOW_IDENT_in_queryExpression4010); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_queryExpression4017); if (state.failed) return n;
            // Generator.g:926:5: (decls= elemVarsDeclaration BAR )?
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
                    // Generator.g:926:7: decls= elemVarsDeclaration BAR
                    {
                    pushFollow(FOLLOW_elemVarsDeclaration_in_queryExpression4028);
                    decls=elemVarsDeclaration();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      decl = decls;
                    }
                    match(input,BAR,FOLLOW_BAR_in_queryExpression4032); if (state.failed) return n;

                    }
                    break;

            }

            pushFollow(FOLLOW_expression_in_queryExpression4043);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_queryExpression4049); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTQueryExpression(op, range, decl, (nExp!=null?nExp.n:null)); 
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
    // Generator.g:940:1: iterateExpression[ASTExpression range] returns [ASTExpression n] : i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN ;
    public final ASTExpression iterateExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTElemVarsDeclaration decls = null;

        ASTVariableInitialization init = null;

        GeneratorParser.expression_return nExp = null;


        try {
            // Generator.g:940:65: (i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN )
            // Generator.g:941:5: i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN
            {
            i=(Token)match(input,81,FOLLOW_81_in_iterateExpression4081); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_iterateExpression4087); if (state.failed) return n;
            pushFollow(FOLLOW_elemVarsDeclaration_in_iterateExpression4095);
            decls=elemVarsDeclaration();

            state._fsp--;
            if (state.failed) return n;
            match(input,SEMI,FOLLOW_SEMI_in_iterateExpression4097); if (state.failed) return n;
            pushFollow(FOLLOW_variableInitialization_in_iterateExpression4105);
            init=variableInitialization();

            state._fsp--;
            if (state.failed) return n;
            match(input,BAR,FOLLOW_BAR_in_iterateExpression4107); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_iterateExpression4115);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_iterateExpression4121); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTIterateExpression(i, range, decls, init, (nExp!=null?nExp.n:null)); 
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

    public static class operationExpression_return extends ParserRuleReturnScope {
        public ASTOperationExpression n;
    };

    // $ANTLR start "operationExpression"
    // Generator.g:962:1: operationExpression[ASTExpression source, boolean followsArrow] returns [ASTOperationExpression n] : name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? ;
    public final GeneratorParser.operationExpression_return operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        GeneratorParser.operationExpression_return retval = new GeneratorParser.operationExpression_return();
        retval.start = input.LT(1);

        Token name=null;
        Token rolename=null;
        GeneratorParser.expression_return e = null;


        try {
            // Generator.g:964:1: (name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? )
            // Generator.g:965:5: name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression4165); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
               retval.n = new ASTOperationExpression(name, source, followsArrow); 
            }
            // Generator.g:968:5: ( LBRACK rolename= IDENT RBRACK )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==LBRACK) ) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // Generator.g:968:7: LBRACK rolename= IDENT RBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_operationExpression4181); if (state.failed) return retval;
                    rolename=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression4185); if (state.failed) return retval;
                    match(input,RBRACK,FOLLOW_RBRACK_in_operationExpression4187); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n.setExplicitRolename(rolename); 
                    }

                    }
                    break;

            }

            // Generator.g:970:5: ( AT 'pre' )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==AT) ) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // Generator.g:970:7: AT 'pre'
                    {
                    match(input,AT,FOLLOW_AT_in_operationExpression4200); if (state.failed) return retval;
                    match(input,71,FOLLOW_71_in_operationExpression4202); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n.setIsPre(); 
                    }

                    }
                    break;

            }

            // Generator.g:971:5: ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==LPAREN) ) {
                alt84=1;
            }
            switch (alt84) {
                case 1 :
                    // Generator.g:972:7: LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_operationExpression4223); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n.hasParentheses(); 
                    }
                    // Generator.g:973:7: (e= expression ( COMMA e= expression )* )?
                    int alt83=2;
                    int LA83_0 = input.LA(1);

                    if ( ((LA83_0>=IDENT && LA83_0<=LPAREN)||LA83_0==INT||(LA83_0>=PLUS && LA83_0<=MINUS)||LA83_0==AT||(LA83_0>=REAL && LA83_0<=HASH)||LA83_0==50||LA83_0==73||LA83_0==79||(LA83_0>=81 && LA83_0<=84)||(LA83_0>=86 && LA83_0<=97)) ) {
                        alt83=1;
                    }
                    switch (alt83) {
                        case 1 :
                            // Generator.g:974:7: e= expression ( COMMA e= expression )*
                            {
                            pushFollow(FOLLOW_expression_in_operationExpression4244);
                            e=expression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                               retval.n.addArg((e!=null?e.n:null)); 
                            }
                            // Generator.g:975:7: ( COMMA e= expression )*
                            loop82:
                            do {
                                int alt82=2;
                                int LA82_0 = input.LA(1);

                                if ( (LA82_0==COMMA) ) {
                                    alt82=1;
                                }


                                switch (alt82) {
                            	case 1 :
                            	    // Generator.g:975:9: COMMA e= expression
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_operationExpression4256); if (state.failed) return retval;
                            	    pushFollow(FOLLOW_expression_in_operationExpression4260);
                            	    e=expression();

                            	    state._fsp--;
                            	    if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) {
                            	       retval.n.addArg((e!=null?e.n:null)); 
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

                    match(input,RPAREN,FOLLOW_RPAREN_in_operationExpression4280); if (state.failed) return retval;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               retval.n.setStartToken(((Token)retval.start)); 
            }

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "operationExpression"


    // $ANTLR start "typeExpression"
    // Generator.g:988:1: typeExpression[ASTExpression source, boolean followsArrow] returns [ASTTypeArgExpression n] : ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN ;
    public final ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTTypeArgExpression n = null;

        ASTType t = null;


         Token opToken = null; 
        try {
            // Generator.g:991:1: ( ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN )
            // Generator.g:992:2: ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN
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

            match(input,LPAREN,FOLLOW_LPAREN_in_typeExpression4345); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_typeExpression4349);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_typeExpression4351); if (state.failed) return n;
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
    // Generator.g:1003:1: elemVarsDeclaration returns [ASTElemVarsDeclaration n] : idListRes= idList ( COLON t= type )? ;
    public final ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException {
        ASTElemVarsDeclaration n = null;

        List idListRes = null;

        ASTType t = null;


         List idList; 
        try {
            // Generator.g:1005:1: (idListRes= idList ( COLON t= type )? )
            // Generator.g:1006:5: idListRes= idList ( COLON t= type )?
            {
            pushFollow(FOLLOW_idList_in_elemVarsDeclaration4390);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            // Generator.g:1007:5: ( COLON t= type )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==COLON) ) {
                alt85=1;
            }
            switch (alt85) {
                case 1 :
                    // Generator.g:1007:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_elemVarsDeclaration4398); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_elemVarsDeclaration4402);
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
    // Generator.g:1016:1: variableInitialization returns [ASTVariableInitialization n] : name= IDENT COLON t= type EQUAL e= expression ;
    public final ASTVariableInitialization variableInitialization() throws RecognitionException {
        ASTVariableInitialization n = null;

        Token name=null;
        ASTType t = null;

        GeneratorParser.expression_return e = null;


        try {
            // Generator.g:1017:1: (name= IDENT COLON t= type EQUAL e= expression )
            // Generator.g:1018:5: name= IDENT COLON t= type EQUAL e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableInitialization4437); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableInitialization4439); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableInitialization4443);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EQUAL,FOLLOW_EQUAL_in_variableInitialization4445); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_variableInitialization4449);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTVariableInitialization(name, t, (e!=null?e.n:null)); 
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
    // Generator.g:1027:1: ifExpression returns [ASTExpression n] : i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' ;
    public final ASTExpression ifExpression() throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        GeneratorParser.expression_return cond = null;

        GeneratorParser.expression_return t = null;

        GeneratorParser.expression_return e = null;


        try {
            // Generator.g:1028:1: (i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' )
            // Generator.g:1029:5: i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif'
            {
            i=(Token)match(input,50,FOLLOW_50_in_ifExpression4481); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4485);
            cond=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,51,FOLLOW_51_in_ifExpression4487); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4491);
            t=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,52,FOLLOW_52_in_ifExpression4493); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4497);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,85,FOLLOW_85_in_ifExpression4499); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTIfExpression(i, (cond!=null?cond.n:null), (t!=null?t.n:null), (e!=null?e.n:null)); 
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
    // Generator.g:1049:1: literal returns [ASTExpression n] : (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral );
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
            // Generator.g:1050:1: (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral )
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
                    // Generator.g:1051:7: t= 'true'
                    {
                    t=(Token)match(input,86,FOLLOW_86_in_literal4538); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(true); 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:1052:7: f= 'false'
                    {
                    f=(Token)match(input,87,FOLLOW_87_in_literal4552); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(false); 
                    }

                    }
                    break;
                case 3 :
                    // Generator.g:1053:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_literal4565); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTIntegerLiteral(i); 
                    }

                    }
                    break;
                case 4 :
                    // Generator.g:1054:7: r= REAL
                    {
                    r=(Token)match(input,REAL,FOLLOW_REAL_in_literal4580); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTRealLiteral(r); 
                    }

                    }
                    break;
                case 5 :
                    // Generator.g:1055:7: s= STRING
                    {
                    s=(Token)match(input,STRING,FOLLOW_STRING_in_literal4594); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTStringLiteral(s); 
                    }

                    }
                    break;
                case 6 :
                    // Generator.g:1056:7: HASH enumLit= IDENT
                    {
                    match(input,HASH,FOLLOW_HASH_in_literal4604); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal4608); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumLit);
                    }

                    }
                    break;
                case 7 :
                    // Generator.g:1057:7: enumName= IDENT '::' enumLit= IDENT
                    {
                    enumName=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal4620); if (state.failed) return n;
                    match(input,COLON_COLON,FOLLOW_COLON_COLON_in_literal4622); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal4626); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumName, enumLit); 
                    }

                    }
                    break;
                case 8 :
                    // Generator.g:1058:7: nColIt= collectionLiteral
                    {
                    pushFollow(FOLLOW_collectionLiteral_in_literal4638);
                    nColIt=collectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nColIt; 
                    }

                    }
                    break;
                case 9 :
                    // Generator.g:1059:7: nEColIt= emptyCollectionLiteral
                    {
                    pushFollow(FOLLOW_emptyCollectionLiteral_in_literal4650);
                    nEColIt=emptyCollectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nEColIt; 
                    }

                    }
                    break;
                case 10 :
                    // Generator.g:1060:7: nUndLit= undefinedLiteral
                    {
                    pushFollow(FOLLOW_undefinedLiteral_in_literal4662);
                    nUndLit=undefinedLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nUndLit; 
                    }

                    }
                    break;
                case 11 :
                    // Generator.g:1061:7: nTupleLit= tupleLiteral
                    {
                    pushFollow(FOLLOW_tupleLiteral_in_literal4674);
                    nTupleLit=tupleLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nTupleLit; 
                    }

                    }
                    break;
                case 12 :
                    // Generator.g:1062:7: nDateLit= dateLiteral
                    {
                    pushFollow(FOLLOW_dateLiteral_in_literal4686);
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
    // Generator.g:1070:1: collectionLiteral returns [ASTCollectionLiteral n] : ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE ;
    public final ASTCollectionLiteral collectionLiteral() throws RecognitionException {
        ASTCollectionLiteral n = null;

        ASTCollectionItem ci = null;


         Token op = null; 
        try {
            // Generator.g:1072:1: ( ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE )
            // Generator.g:1073:5: ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE
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
            match(input,LBRACE,FOLLOW_LBRACE_in_collectionLiteral4753); if (state.failed) return n;
            // Generator.g:1077:5: (ci= collectionItem ( COMMA ci= collectionItem )* )?
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( ((LA88_0>=IDENT && LA88_0<=LPAREN)||LA88_0==INT||(LA88_0>=PLUS && LA88_0<=MINUS)||LA88_0==AT||(LA88_0>=REAL && LA88_0<=HASH)||LA88_0==50||LA88_0==73||LA88_0==79||(LA88_0>=81 && LA88_0<=84)||(LA88_0>=86 && LA88_0<=97)) ) {
                alt88=1;
            }
            switch (alt88) {
                case 1 :
                    // Generator.g:1078:7: ci= collectionItem ( COMMA ci= collectionItem )*
                    {
                    pushFollow(FOLLOW_collectionItem_in_collectionLiteral4770);
                    ci=collectionItem();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addItem(ci); 
                    }
                    // Generator.g:1079:7: ( COMMA ci= collectionItem )*
                    loop87:
                    do {
                        int alt87=2;
                        int LA87_0 = input.LA(1);

                        if ( (LA87_0==COMMA) ) {
                            alt87=1;
                        }


                        switch (alt87) {
                    	case 1 :
                    	    // Generator.g:1079:9: COMMA ci= collectionItem
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_collectionLiteral4783); if (state.failed) return n;
                    	    pushFollow(FOLLOW_collectionItem_in_collectionLiteral4787);
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

            match(input,RBRACE,FOLLOW_RBRACE_in_collectionLiteral4806); if (state.failed) return n;

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
    // Generator.g:1088:1: collectionItem returns [ASTCollectionItem n] : e= expression ( DOTDOT e= expression )? ;
    public final ASTCollectionItem collectionItem() throws RecognitionException {
        ASTCollectionItem n = null;

        GeneratorParser.expression_return e = null;


         n = new ASTCollectionItem(); 
        try {
            // Generator.g:1090:1: (e= expression ( DOTDOT e= expression )? )
            // Generator.g:1091:5: e= expression ( DOTDOT e= expression )?
            {
            pushFollow(FOLLOW_expression_in_collectionItem4835);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setFirst((e!=null?e.n:null)); 
            }
            // Generator.g:1092:5: ( DOTDOT e= expression )?
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==DOTDOT) ) {
                alt89=1;
            }
            switch (alt89) {
                case 1 :
                    // Generator.g:1092:7: DOTDOT e= expression
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_collectionItem4846); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_collectionItem4850);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setSecond((e!=null?e.n:null)); 
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
    // Generator.g:1102:1: emptyCollectionLiteral returns [ASTEmptyCollectionLiteral n] : 'oclEmpty' LPAREN t= collectionType RPAREN ;
    public final ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException {
        ASTEmptyCollectionLiteral n = null;

        ASTCollectionType t = null;


        try {
            // Generator.g:1103:1: ( 'oclEmpty' LPAREN t= collectionType RPAREN )
            // Generator.g:1104:5: 'oclEmpty' LPAREN t= collectionType RPAREN
            {
            match(input,92,FOLLOW_92_in_emptyCollectionLiteral4879); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_emptyCollectionLiteral4881); if (state.failed) return n;
            pushFollow(FOLLOW_collectionType_in_emptyCollectionLiteral4885);
            t=collectionType();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_emptyCollectionLiteral4887); if (state.failed) return n;
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
    // Generator.g:1115:1: undefinedLiteral returns [ASTUndefinedLiteral n] : ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' );
    public final ASTUndefinedLiteral undefinedLiteral() throws RecognitionException {
        ASTUndefinedLiteral n = null;

        ASTType t = null;


        try {
            // Generator.g:1116:1: ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' )
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
                    // Generator.g:1117:5: 'oclUndefined' LPAREN t= type RPAREN
                    {
                    match(input,93,FOLLOW_93_in_undefinedLiteral4917); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_undefinedLiteral4919); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_undefinedLiteral4923);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_undefinedLiteral4925); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(t); 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:1120:5: 'Undefined'
                    {
                    match(input,94,FOLLOW_94_in_undefinedLiteral4939); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(); 
                    }

                    }
                    break;
                case 3 :
                    // Generator.g:1123:5: 'null'
                    {
                    match(input,95,FOLLOW_95_in_undefinedLiteral4953); if (state.failed) return n;
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
    // Generator.g:1132:1: tupleLiteral returns [ASTTupleLiteral n] : 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE ;
    public final ASTTupleLiteral tupleLiteral() throws RecognitionException {
        ASTTupleLiteral n = null;

        ASTTupleItem ti = null;


         List tiList = new ArrayList(); 
        try {
            // Generator.g:1134:1: ( 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE )
            // Generator.g:1135:5: 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE
            {
            match(input,96,FOLLOW_96_in_tupleLiteral4987); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_tupleLiteral4993); if (state.failed) return n;
            pushFollow(FOLLOW_tupleItem_in_tupleLiteral5001);
            ti=tupleItem();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tiList.add(ti); 
            }
            // Generator.g:1138:5: ( COMMA ti= tupleItem )*
            loop91:
            do {
                int alt91=2;
                int LA91_0 = input.LA(1);

                if ( (LA91_0==COMMA) ) {
                    alt91=1;
                }


                switch (alt91) {
            	case 1 :
            	    // Generator.g:1138:7: COMMA ti= tupleItem
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleLiteral5012); if (state.failed) return n;
            	    pushFollow(FOLLOW_tupleItem_in_tupleLiteral5016);
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

            match(input,RBRACE,FOLLOW_RBRACE_in_tupleLiteral5027); if (state.failed) return n;
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
    // Generator.g:1146:1: tupleItem returns [ASTTupleItem n] : name= IDENT ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) ;
    public final ASTTupleItem tupleItem() throws RecognitionException {
        ASTTupleItem n = null;

        Token name=null;
        ASTType t = null;

        GeneratorParser.expression_return e = null;


        try {
            // Generator.g:1147:1: (name= IDENT ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) )
            // Generator.g:1148:5: name= IDENT ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tupleItem5058); if (state.failed) return n;
            // Generator.g:1149:5: ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
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
                    // Generator.g:1152:7: ( COLON type EQUAL )=> COLON t= type EQUAL e= expression
                    {
                    match(input,COLON,FOLLOW_COLON_in_tupleItem5097); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_tupleItem5101);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,EQUAL,FOLLOW_EQUAL_in_tupleItem5103); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_tupleItem5107);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTTupleItem(name, t, (e!=null?e.n:null)); 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:1155:7: ( COLON | EQUAL ) e= expression
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

                    pushFollow(FOLLOW_expression_in_tupleItem5139);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTTupleItem(name, (e!=null?e.n:null)); 
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
    // Generator.g:1164:1: dateLiteral returns [ASTDateLiteral n] : 'Date' LBRACE v= STRING RBRACE ;
    public final ASTDateLiteral dateLiteral() throws RecognitionException {
        ASTDateLiteral n = null;

        Token v=null;

        try {
            // Generator.g:1165:1: ( 'Date' LBRACE v= STRING RBRACE )
            // Generator.g:1166:5: 'Date' LBRACE v= STRING RBRACE
            {
            match(input,97,FOLLOW_97_in_dateLiteral5184); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_dateLiteral5186); if (state.failed) return n;
            v=(Token)match(input,STRING,FOLLOW_STRING_in_dateLiteral5190); if (state.failed) return n;
            match(input,RBRACE,FOLLOW_RBRACE_in_dateLiteral5192); if (state.failed) return n;
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
    // Generator.g:1176:1: type returns [ASTType n] : (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) ;
    public final ASTType type() throws RecognitionException {
        ASTType n = null;

        ASTSimpleType nTSimple = null;

        ASTCollectionType nTCollection = null;

        ASTTupleType nTTuple = null;


         Token tok = null; 
        try {
            // Generator.g:1178:1: ( (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) )
            // Generator.g:1179:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of type */ 
            }
            // Generator.g:1180:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
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
                    // Generator.g:1181:7: nTSimple= simpleType
                    {
                    pushFollow(FOLLOW_simpleType_in_type5242);
                    nTSimple=simpleType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTSimple; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:1182:7: nTCollection= collectionType
                    {
                    pushFollow(FOLLOW_collectionType_in_type5254);
                    nTCollection=collectionType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTCollection; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 3 :
                    // Generator.g:1183:7: nTTuple= tupleType
                    {
                    pushFollow(FOLLOW_tupleType_in_type5266);
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
    // Generator.g:1188:1: typeOnly returns [ASTType n] : nT= type EOF ;
    public final ASTType typeOnly() throws RecognitionException {
        ASTType n = null;

        ASTType nT = null;


        try {
            // Generator.g:1189:1: (nT= type EOF )
            // Generator.g:1190:5: nT= type EOF
            {
            pushFollow(FOLLOW_type_in_typeOnly5298);
            nT=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_typeOnly5300); if (state.failed) return n;
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
    // Generator.g:1200:1: simpleType returns [ASTSimpleType n] : name= IDENT ;
    public final ASTSimpleType simpleType() throws RecognitionException {
        ASTSimpleType n = null;

        Token name=null;

        try {
            // Generator.g:1201:1: (name= IDENT )
            // Generator.g:1202:5: name= IDENT
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_simpleType5328); if (state.failed) return n;
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
    // Generator.g:1210:1: collectionType returns [ASTCollectionType n] : ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN ;
    public final ASTCollectionType collectionType() throws RecognitionException {
        ASTCollectionType n = null;

        ASTType elemType = null;


         Token op = null; 
        try {
            // Generator.g:1212:1: ( ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN )
            // Generator.g:1213:5: ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN
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

            match(input,LPAREN,FOLLOW_LPAREN_in_collectionType5393); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_collectionType5397);
            elemType=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_collectionType5399); if (state.failed) return n;
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
    // Generator.g:1223:1: tupleType returns [ASTTupleType n] : 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN ;
    public final ASTTupleType tupleType() throws RecognitionException {
        ASTTupleType n = null;

        ASTTuplePart tp = null;


         List tpList = new ArrayList(); 
        try {
            // Generator.g:1225:1: ( 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN )
            // Generator.g:1226:5: 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN
            {
            match(input,96,FOLLOW_96_in_tupleType5433); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_tupleType5435); if (state.failed) return n;
            pushFollow(FOLLOW_tuplePart_in_tupleType5444);
            tp=tuplePart();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tpList.add(tp); 
            }
            // Generator.g:1228:5: ( COMMA tp= tuplePart )*
            loop94:
            do {
                int alt94=2;
                int LA94_0 = input.LA(1);

                if ( (LA94_0==COMMA) ) {
                    alt94=1;
                }


                switch (alt94) {
            	case 1 :
            	    // Generator.g:1228:7: COMMA tp= tuplePart
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleType5455); if (state.failed) return n;
            	    pushFollow(FOLLOW_tuplePart_in_tupleType5459);
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

            match(input,RPAREN,FOLLOW_RPAREN_in_tupleType5471); if (state.failed) return n;
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
    // Generator.g:1237:1: tuplePart returns [ASTTuplePart n] : name= IDENT COLON t= type ;
    public final ASTTuplePart tuplePart() throws RecognitionException {
        ASTTuplePart n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Generator.g:1238:1: (name= IDENT COLON t= type )
            // Generator.g:1239:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tuplePart5503); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_tuplePart5505); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_tuplePart5509);
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


    // $ANTLR start "statOnly"
    // Generator.g:1278:1: statOnly returns [ASTStatement n] : s= stat EOF ;
    public final ASTStatement statOnly() throws RecognitionException {
        ASTStatement n = null;

        GeneratorParser.stat_return s = null;


        try {
            // Generator.g:1279:1: (s= stat EOF )
            // Generator.g:1280:3: s= stat EOF
            {
            pushFollow(FOLLOW_stat_in_statOnly5558);
            s=stat();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_statOnly5562); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = (s!=null?s.n:null); 
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
    // $ANTLR end "statOnly"

    public static class stat_return extends ParserRuleReturnScope {
        public ASTStatement n;
    };

    // $ANTLR start "stat"
    // Generator.g:1290:1: stat returns [ASTStatement n] : nextStat[seq] ( SEMI nextStat[seq] )* ;
    public final GeneratorParser.stat_return stat() throws RecognitionException {
        GeneratorParser.stat_return retval = new GeneratorParser.stat_return();
        retval.start = input.LT(1);


          ASTSequenceStatement seq = new ASTSequenceStatement();

        try {
            // Generator.g:1294:1: ( nextStat[seq] ( SEMI nextStat[seq] )* )
            // Generator.g:1295:3: nextStat[seq] ( SEMI nextStat[seq] )*
            {
            pushFollow(FOLLOW_nextStat_in_stat5593);
            nextStat(seq);

            state._fsp--;
            if (state.failed) return retval;
            // Generator.g:1296:3: ( SEMI nextStat[seq] )*
            loop95:
            do {
                int alt95=2;
                int LA95_0 = input.LA(1);

                if ( (LA95_0==SEMI) ) {
                    alt95=1;
                }


                switch (alt95) {
            	case 1 :
            	    // Generator.g:1297:5: SEMI nextStat[seq]
            	    {
            	    match(input,SEMI,FOLLOW_SEMI_in_stat5604); if (state.failed) return retval;
            	    pushFollow(FOLLOW_nextStat_in_stat5610);
            	    nextStat(seq);

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop95;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               
                  retval.n = seq.simplify();
                  if ((retval.n != null) && (!retval.n.isEmptyStatement())) {
                    retval.n.setSourcePosition(((Token)retval.start));
                    retval.n.setParsedText(input.toString(retval.start,input.LT(-1)));
                  }
                
            }

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "stat"

    public static class nextStat_return extends ParserRuleReturnScope {
    };

    // $ANTLR start "nextStat"
    // Generator.g:1314:1: nextStat[ASTSequenceStatement seq] : s= singleStat ;
    public final GeneratorParser.nextStat_return nextStat(ASTSequenceStatement seq) throws RecognitionException {
        GeneratorParser.nextStat_return retval = new GeneratorParser.nextStat_return();
        retval.start = input.LT(1);

        ASTStatement s = null;


        try {
            // Generator.g:1315:1: (s= singleStat )
            // Generator.g:1316:3: s= singleStat
            {
            pushFollow(FOLLOW_singleStat_in_nextStat5644);
            s=singleStat();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) {

                  if ((s != null) && (!s.isEmptyStatement())) {
                    seq.addStatement(s, ((Token)retval.start), input.toString(retval.start,input.LT(-1)));
                  }
                
            }

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "nextStat"


    // $ANTLR start "singleStat"
    // Generator.g:1328:1: singleStat returns [ASTStatement n] options {backtrack=true; memoize=true; } : (emp= emptyStat | vas= varAssignStat | aas= attAssignStat | lcs= lobjCreateStat | ocs= objCreateStat | ods= objDestroyStat | lis= lnkInsStat | lds= lnkDelStat | ces= condExStat | its= iterStat | ops= callStat );
    public final ASTStatement singleStat() throws RecognitionException {
        ASTStatement n = null;
        int singleStat_StartIndex = input.index();
        ASTEmptyStatement emp = null;

        GeneratorParser.varAssignStat_return vas = null;

        ASTAttributeAssignmentStatement aas = null;

        ASTNewLinkObjectStatement lcs = null;

        GeneratorParser.objCreateStat_return ocs = null;

        GeneratorParser.objDestroyStat_return ods = null;

        ASTLinkInsertionStatement lis = null;

        ASTLinkDeletionStatement lds = null;

        ASTConditionalExecutionStatement ces = null;

        ASTIterationStatement its = null;

        ASTOperationCallStatement ops = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 78) ) { return n; }
            // Generator.g:1333:1: (emp= emptyStat | vas= varAssignStat | aas= attAssignStat | lcs= lobjCreateStat | ocs= objCreateStat | ods= objDestroyStat | lis= lnkInsStat | lds= lnkDelStat | ces= condExStat | its= iterStat | ops= callStat )
            int alt96=11;
            alt96 = dfa96.predict(input);
            switch (alt96) {
                case 1 :
                    // Generator.g:1334:5: emp= emptyStat
                    {
                    pushFollow(FOLLOW_emptyStat_in_singleStat5699);
                    emp=emptyStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = emp; 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:1336:5: vas= varAssignStat
                    {
                    pushFollow(FOLLOW_varAssignStat_in_singleStat5720);
                    vas=varAssignStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (vas!=null?vas.n:null); 
                    }

                    }
                    break;
                case 3 :
                    // Generator.g:1337:5: aas= attAssignStat
                    {
                    pushFollow(FOLLOW_attAssignStat_in_singleStat5734);
                    aas=attAssignStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = aas; 
                    }

                    }
                    break;
                case 4 :
                    // Generator.g:1338:5: lcs= lobjCreateStat
                    {
                    pushFollow(FOLLOW_lobjCreateStat_in_singleStat5748);
                    lcs=lobjCreateStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = lcs; 
                    }

                    }
                    break;
                case 5 :
                    // Generator.g:1339:5: ocs= objCreateStat
                    {
                    pushFollow(FOLLOW_objCreateStat_in_singleStat5761);
                    ocs=objCreateStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (ocs!=null?ocs.n:null); 
                    }

                    }
                    break;
                case 6 :
                    // Generator.g:1340:5: ods= objDestroyStat
                    {
                    pushFollow(FOLLOW_objDestroyStat_in_singleStat5775);
                    ods=objDestroyStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (ods!=null?ods.n:null); 
                    }

                    }
                    break;
                case 7 :
                    // Generator.g:1341:5: lis= lnkInsStat
                    {
                    pushFollow(FOLLOW_lnkInsStat_in_singleStat5788);
                    lis=lnkInsStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = lis; 
                    }

                    }
                    break;
                case 8 :
                    // Generator.g:1342:5: lds= lnkDelStat
                    {
                    pushFollow(FOLLOW_lnkDelStat_in_singleStat5805);
                    lds=lnkDelStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = lds; 
                    }

                    }
                    break;
                case 9 :
                    // Generator.g:1343:5: ces= condExStat
                    {
                    pushFollow(FOLLOW_condExStat_in_singleStat5822);
                    ces=condExStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = ces; 
                    }

                    }
                    break;
                case 10 :
                    // Generator.g:1344:5: its= iterStat
                    {
                    pushFollow(FOLLOW_iterStat_in_singleStat5839);
                    its=iterStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = its; 
                    }

                    }
                    break;
                case 11 :
                    // Generator.g:1345:5: ops= callStat
                    {
                    pushFollow(FOLLOW_callStat_in_singleStat5858);
                    ops=callStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = ops; 
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
            if ( state.backtracking>0 ) { memoize(input, 78, singleStat_StartIndex); }
        }
        return n;
    }
    // $ANTLR end "singleStat"


    // $ANTLR start "emptyStat"
    // Generator.g:1353:1: emptyStat returns [ASTEmptyStatement n] : nothing ;
    public final ASTEmptyStatement emptyStat() throws RecognitionException {
        ASTEmptyStatement n = null;

        try {
            // Generator.g:1354:1: ( nothing )
            // Generator.g:1355:3: nothing
            {
            pushFollow(FOLLOW_nothing_in_emptyStat5889);
            nothing();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTEmptyStatement(); 
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
    // $ANTLR end "emptyStat"

    public static class varAssignStat_return extends ParserRuleReturnScope {
        public ASTStatement n;
    };

    // $ANTLR start "varAssignStat"
    // Generator.g:1364:1: varAssignStat returns [ASTStatement n] options {backtrack=true; memoize=true; } : (varName= IDENT COLON_EQUAL rVal= rValue | vNames= identListMin1 COLON_EQUAL 'new' oType= simpleType ( LPAREN oNames= exprList RPAREN )? );
    public final GeneratorParser.varAssignStat_return varAssignStat() throws RecognitionException {
        GeneratorParser.varAssignStat_return retval = new GeneratorParser.varAssignStat_return();
        retval.start = input.LT(1);
        int varAssignStat_StartIndex = input.index();
        Token varName=null;
        GeneratorParser.rValue_return rVal = null;

        List<String> vNames = null;

        ASTSimpleType oType = null;

        List<ASTExpression> oNames = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 80) ) { return retval; }
            // Generator.g:1369:1: (varName= IDENT COLON_EQUAL rVal= rValue | vNames= identListMin1 COLON_EQUAL 'new' oType= simpleType ( LPAREN oNames= exprList RPAREN )? )
            int alt98=2;
            int LA98_0 = input.LA(1);

            if ( (LA98_0==IDENT) ) {
                int LA98_1 = input.LA(2);

                if ( (LA98_1==COLON_EQUAL) ) {
                    int LA98_2 = input.LA(3);

                    if ( (LA98_2==99) ) {
                        int LA98_4 = input.LA(4);

                        if ( (LA98_4==IDENT) ) {
                            int LA98_6 = input.LA(5);

                            if ( (synpred12_Generator()) ) {
                                alt98=1;
                            }
                            else if ( (true) ) {
                                alt98=2;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 98, 6, input);

                                throw nvae;
                            }
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 98, 4, input);

                            throw nvae;
                        }
                    }
                    else if ( ((LA98_2>=IDENT && LA98_2<=LPAREN)||LA98_2==INT||(LA98_2>=PLUS && LA98_2<=MINUS)||LA98_2==AT||(LA98_2>=REAL && LA98_2<=HASH)||LA98_2==50||LA98_2==73||LA98_2==79||(LA98_2>=81 && LA98_2<=84)||(LA98_2>=86 && LA98_2<=97)) ) {
                        alt98=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 98, 2, input);

                        throw nvae;
                    }
                }
                else if ( (LA98_1==COMMA) ) {
                    alt98=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 98, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 98, 0, input);

                throw nvae;
            }
            switch (alt98) {
                case 1 :
                    // Generator.g:1370:3: varName= IDENT COLON_EQUAL rVal= rValue
                    {
                    varName=(Token)match(input,IDENT,FOLLOW_IDENT_in_varAssignStat5942); if (state.failed) return retval;
                    match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_varAssignStat5946); if (state.failed) return retval;
                    pushFollow(FOLLOW_rValue_in_varAssignStat5954);
                    rVal=rValue();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n = new ASTVariableAssignmentStatement((varName!=null?varName.getText():null), (rVal!=null?rVal.n:null)); 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:1376:3: vNames= identListMin1 COLON_EQUAL 'new' oType= simpleType ( LPAREN oNames= exprList RPAREN )?
                    {
                    pushFollow(FOLLOW_identListMin1_in_varAssignStat5973);
                    vNames=identListMin1();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_varAssignStat5977); if (state.failed) return retval;
                    match(input,99,FOLLOW_99_in_varAssignStat5981); if (state.failed) return retval;
                    pushFollow(FOLLOW_simpleType_in_varAssignStat5989);
                    oType=simpleType();

                    state._fsp--;
                    if (state.failed) return retval;
                    // Generator.g:1380:3: ( LPAREN oNames= exprList RPAREN )?
                    int alt97=2;
                    int LA97_0 = input.LA(1);

                    if ( (LA97_0==LPAREN) ) {
                        alt97=1;
                    }
                    switch (alt97) {
                        case 1 :
                            // Generator.g:1381:5: LPAREN oNames= exprList RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_varAssignStat5999); if (state.failed) return retval;
                            pushFollow(FOLLOW_exprList_in_varAssignStat6011);
                            oNames=exprList();

                            state._fsp--;
                            if (state.failed) return retval;
                            match(input,RPAREN,FOLLOW_RPAREN_in_varAssignStat6017); if (state.failed) return retval;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {

                          ASTSimpleType objType = oType;
                          List<String> varNames = vNames;
                          int numVars = varNames.size();
                          
                          List<ASTExpression> objNames = null;
                          int numNames = 0;
                          
                          if (oNames != null) {
                            objNames = oNames;
                            numNames = objNames.size();
                          }
                          
                          if (numNames > numVars) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("ignoring superfluous object name");
                            if ((numNames - numVars) > 1) {
                              sb.append("s");
                            }
                            sb.append(": ");
                            for (int i = numVars; i < numNames; ) {
                              sb.append(objNames.get(i).getStringRep());
                              if (++i < numNames) {
                                sb.append(", ");
                              }
                            }
                            sb.append(" in statement `");
                            sb.append(input.toString(retval.start,input.LT(-1))); 
                            sb.append("'");
                            
                            reportWarning(sb.toString());
                          }
                          
                          ASTSequenceStatement seq = new ASTSequenceStatement(numVars);
                          for (int i = 0; i < numVars; ++i) {     
                            seq.addStatement(
                              new ASTVariableAssignmentStatement(
                                varNames.get(i),
                                new ASTRValueNewObject(
                                  objType, 
                                  ((i < numNames) ? objNames.get(i) : null))),
                              ((Token)retval.start),
                              input.toString(retval.start,input.LT(-1)));  
                          }
                             
                          retval.n = seq.simplify();
                        
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 80, varAssignStat_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "varAssignStat"


    // $ANTLR start "attAssignStat"
    // Generator.g:1439:1: attAssignStat returns [ASTAttributeAssignmentStatement n] : obj= inSoilExpression DOT attName= IDENT COLON_EQUAL r= rValue ;
    public final ASTAttributeAssignmentStatement attAssignStat() throws RecognitionException {
        ASTAttributeAssignmentStatement n = null;

        Token attName=null;
        ASTExpression obj = null;

        GeneratorParser.rValue_return r = null;


        try {
            // Generator.g:1440:1: (obj= inSoilExpression DOT attName= IDENT COLON_EQUAL r= rValue )
            // Generator.g:1441:3: obj= inSoilExpression DOT attName= IDENT COLON_EQUAL r= rValue
            {
            pushFollow(FOLLOW_inSoilExpression_in_attAssignStat6052);
            obj=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            match(input,DOT,FOLLOW_DOT_in_attAssignStat6057); if (state.failed) return n;
            attName=(Token)match(input,IDENT,FOLLOW_IDENT_in_attAssignStat6066); if (state.failed) return n;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_attAssignStat6070); if (state.failed) return n;
            pushFollow(FOLLOW_rValue_in_attAssignStat6078);
            r=rValue();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAttributeAssignmentStatement(obj, (attName!=null?attName.getText():null), (r!=null?r.n:null)); 
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
    // $ANTLR end "attAssignStat"

    public static class objCreateStat_return extends ParserRuleReturnScope {
        public ASTStatement n;
    };

    // $ANTLR start "objCreateStat"
    // Generator.g:1454:1: objCreateStat returns [ASTStatement n] : 'new' objType= simpleType ( LPAREN objNames= exprListMin1 RPAREN )? ;
    public final GeneratorParser.objCreateStat_return objCreateStat() throws RecognitionException {
        GeneratorParser.objCreateStat_return retval = new GeneratorParser.objCreateStat_return();
        retval.start = input.LT(1);

        ASTSimpleType objType = null;

        List<ASTExpression> objNames = null;


        try {
            // Generator.g:1455:1: ( 'new' objType= simpleType ( LPAREN objNames= exprListMin1 RPAREN )? )
            // Generator.g:1456:3: 'new' objType= simpleType ( LPAREN objNames= exprListMin1 RPAREN )?
            {
            match(input,99,FOLLOW_99_in_objCreateStat6104); if (state.failed) return retval;
            pushFollow(FOLLOW_simpleType_in_objCreateStat6112);
            objType=simpleType();

            state._fsp--;
            if (state.failed) return retval;
            // Generator.g:1458:3: ( LPAREN objNames= exprListMin1 RPAREN )?
            int alt99=2;
            int LA99_0 = input.LA(1);

            if ( (LA99_0==LPAREN) ) {
                alt99=1;
            }
            switch (alt99) {
                case 1 :
                    // Generator.g:1459:5: LPAREN objNames= exprListMin1 RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_objCreateStat6122); if (state.failed) return retval;
                    pushFollow(FOLLOW_exprListMin1_in_objCreateStat6134);
                    objNames=exprListMin1();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,RPAREN,FOLLOW_RPAREN_in_objCreateStat6140); if (state.failed) return retval;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                  if (objNames == null) {
                    retval.n = new ASTNewObjectStatement(objType);
                  } else {
                    ASTSequenceStatement seq = new ASTSequenceStatement();
                    
                    for (ASTExpression objName : objNames){    
                      seq.addStatement(
                        new ASTNewObjectStatement(objType, objName),
                        ((Token)retval.start),
                        input.toString(retval.start,input.LT(-1)));
                    }
                    
                    retval.n = seq.simplify();
                  }
                
            }

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "objCreateStat"


    // $ANTLR start "lobjCreateStat"
    // Generator.g:1486:1: lobjCreateStat returns [ASTNewLinkObjectStatement n] : 'new' assoc= IDENT ( LPAREN name= inSoilExpression RPAREN )? 'between' LPAREN p= rValListMin2 RPAREN ;
    public final ASTNewLinkObjectStatement lobjCreateStat() throws RecognitionException {
        ASTNewLinkObjectStatement n = null;

        Token assoc=null;
        ASTExpression name = null;

        List<ASTRValue> p = null;


        try {
            // Generator.g:1487:1: ( 'new' assoc= IDENT ( LPAREN name= inSoilExpression RPAREN )? 'between' LPAREN p= rValListMin2 RPAREN )
            // Generator.g:1488:3: 'new' assoc= IDENT ( LPAREN name= inSoilExpression RPAREN )? 'between' LPAREN p= rValListMin2 RPAREN
            {
            match(input,99,FOLLOW_99_in_lobjCreateStat6171); if (state.failed) return n;
            assoc=(Token)match(input,IDENT,FOLLOW_IDENT_in_lobjCreateStat6179); if (state.failed) return n;
            // Generator.g:1490:3: ( LPAREN name= inSoilExpression RPAREN )?
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( (LA100_0==LPAREN) ) {
                alt100=1;
            }
            switch (alt100) {
                case 1 :
                    // Generator.g:1491:5: LPAREN name= inSoilExpression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_lobjCreateStat6189); if (state.failed) return n;
                    pushFollow(FOLLOW_inSoilExpression_in_lobjCreateStat6201);
                    name=inSoilExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_lobjCreateStat6207); if (state.failed) return n;

                    }
                    break;

            }

            match(input,62,FOLLOW_62_in_lobjCreateStat6216); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_lobjCreateStat6220); if (state.failed) return n;
            pushFollow(FOLLOW_rValListMin2_in_lobjCreateStat6230);
            p=rValListMin2();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_lobjCreateStat6234); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               
                  n = 
                    new ASTNewLinkObjectStatement(
                      (assoc!=null?assoc.getText():null), p, name); 
                
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
    // $ANTLR end "lobjCreateStat"

    public static class objDestroyStat_return extends ParserRuleReturnScope {
        public ASTStatement n;
    };

    // $ANTLR start "objDestroyStat"
    // Generator.g:1511:1: objDestroyStat returns [ASTStatement n] : 'destroy' el= exprListMin1 ;
    public final GeneratorParser.objDestroyStat_return objDestroyStat() throws RecognitionException {
        GeneratorParser.objDestroyStat_return retval = new GeneratorParser.objDestroyStat_return();
        retval.start = input.LT(1);

        List<ASTExpression> el = null;


        try {
            // Generator.g:1512:1: ( 'destroy' el= exprListMin1 )
            // Generator.g:1513:3: 'destroy' el= exprListMin1
            {
            match(input,100,FOLLOW_100_in_objDestroyStat6260); if (state.failed) return retval;
            pushFollow(FOLLOW_exprListMin1_in_objDestroyStat6268);
            el=exprListMin1();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) {

                  ASTSequenceStatement seq = new ASTSequenceStatement();
                  
                  for (ASTExpression expression : el) {
                    seq.addStatement(
                      new ASTObjectDestructionStatement(expression),
                      ((Token)retval.start),
                      input.toString(retval.start,input.LT(-1)));
                  }
                  
                  retval.n = seq.simplify();
                
            }

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "objDestroyStat"


    // $ANTLR start "lnkInsStat"
    // Generator.g:1534:1: lnkInsStat returns [ASTLinkInsertionStatement n] : 'insert' LPAREN p= rValListMin2 RPAREN 'into' ass= IDENT ;
    public final ASTLinkInsertionStatement lnkInsStat() throws RecognitionException {
        ASTLinkInsertionStatement n = null;

        Token ass=null;
        List<ASTRValue> p = null;


        try {
            // Generator.g:1535:1: ( 'insert' LPAREN p= rValListMin2 RPAREN 'into' ass= IDENT )
            // Generator.g:1536:3: 'insert' LPAREN p= rValListMin2 RPAREN 'into' ass= IDENT
            {
            match(input,101,FOLLOW_101_in_lnkInsStat6294); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_lnkInsStat6298); if (state.failed) return n;
            pushFollow(FOLLOW_rValListMin2_in_lnkInsStat6308);
            p=rValListMin2();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_lnkInsStat6312); if (state.failed) return n;
            match(input,102,FOLLOW_102_in_lnkInsStat6316); if (state.failed) return n;
            ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_lnkInsStat6324); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTLinkInsertionStatement((ass!=null?ass.getText():null), p); 
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
    // $ANTLR end "lnkInsStat"


    // $ANTLR start "lnkDelStat"
    // Generator.g:1550:1: lnkDelStat returns [ASTLinkDeletionStatement n] : 'delete' LPAREN p= rValListMin2 RPAREN 'from' ass= IDENT ;
    public final ASTLinkDeletionStatement lnkDelStat() throws RecognitionException {
        ASTLinkDeletionStatement n = null;

        Token ass=null;
        List<ASTRValue> p = null;


        try {
            // Generator.g:1551:1: ( 'delete' LPAREN p= rValListMin2 RPAREN 'from' ass= IDENT )
            // Generator.g:1552:3: 'delete' LPAREN p= rValListMin2 RPAREN 'from' ass= IDENT
            {
            match(input,103,FOLLOW_103_in_lnkDelStat6348); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_lnkDelStat6352); if (state.failed) return n;
            pushFollow(FOLLOW_rValListMin2_in_lnkDelStat6362);
            p=rValListMin2();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_lnkDelStat6366); if (state.failed) return n;
            match(input,104,FOLLOW_104_in_lnkDelStat6370); if (state.failed) return n;
            ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_lnkDelStat6379); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTLinkDeletionStatement((ass!=null?ass.getText():null), p); 
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
    // $ANTLR end "lnkDelStat"


    // $ANTLR start "condExStat"
    // Generator.g:1566:1: condExStat returns [ASTConditionalExecutionStatement n] : 'if' con= inSoilExpression 'then' ts= stat ( 'else' es= stat )? 'end' ;
    public final ASTConditionalExecutionStatement condExStat() throws RecognitionException {
        ASTConditionalExecutionStatement n = null;

        ASTExpression con = null;

        GeneratorParser.stat_return ts = null;

        GeneratorParser.stat_return es = null;



          ASTStatement elseStat = new ASTEmptyStatement();

        try {
            // Generator.g:1570:1: ( 'if' con= inSoilExpression 'then' ts= stat ( 'else' es= stat )? 'end' )
            // Generator.g:1571:3: 'if' con= inSoilExpression 'then' ts= stat ( 'else' es= stat )? 'end'
            {
            match(input,50,FOLLOW_50_in_condExStat6410); if (state.failed) return n;
            pushFollow(FOLLOW_inSoilExpression_in_condExStat6419);
            con=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            match(input,51,FOLLOW_51_in_condExStat6423); if (state.failed) return n;
            pushFollow(FOLLOW_stat_in_condExStat6432);
            ts=stat();

            state._fsp--;
            if (state.failed) return n;
            // Generator.g:1575:3: ( 'else' es= stat )?
            int alt101=2;
            int LA101_0 = input.LA(1);

            if ( (LA101_0==52) ) {
                alt101=1;
            }
            switch (alt101) {
                case 1 :
                    // Generator.g:1576:5: 'else' es= stat
                    {
                    match(input,52,FOLLOW_52_in_condExStat6443); if (state.failed) return n;
                    pushFollow(FOLLOW_stat_in_condExStat6455);
                    es=stat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       elseStat = (es!=null?es.n:null); 
                    }

                    }
                    break;

            }

            match(input,47,FOLLOW_47_in_condExStat6466); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTConditionalExecutionStatement(con, (ts!=null?ts.n:null), elseStat); 
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
    // $ANTLR end "condExStat"


    // $ANTLR start "iterStat"
    // Generator.g:1587:1: iterStat returns [ASTIterationStatement n] : 'for' var= IDENT 'in' set= inSoilExpression 'do' s= stat 'end' ;
    public final ASTIterationStatement iterStat() throws RecognitionException {
        ASTIterationStatement n = null;

        Token var=null;
        ASTExpression set = null;

        GeneratorParser.stat_return s = null;


        try {
            // Generator.g:1588:1: ( 'for' var= IDENT 'in' set= inSoilExpression 'do' s= stat 'end' )
            // Generator.g:1589:3: 'for' var= IDENT 'in' set= inSoilExpression 'do' s= stat 'end'
            {
            match(input,48,FOLLOW_48_in_iterStat6491); if (state.failed) return n;
            var=(Token)match(input,IDENT,FOLLOW_IDENT_in_iterStat6499); if (state.failed) return n;
            match(input,49,FOLLOW_49_in_iterStat6503); if (state.failed) return n;
            pushFollow(FOLLOW_inSoilExpression_in_iterStat6511);
            set=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            match(input,105,FOLLOW_105_in_iterStat6515); if (state.failed) return n;
            pushFollow(FOLLOW_stat_in_iterStat6523);
            s=stat();

            state._fsp--;
            if (state.failed) return n;
            match(input,47,FOLLOW_47_in_iterStat6527); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTIterationStatement((var!=null?var.getText():null), set, (s!=null?s.n:null)); 
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
    // $ANTLR end "iterStat"


    // $ANTLR start "callStat"
    // Generator.g:1604:1: callStat returns [ASTOperationCallStatement n] : e= inSoilExpression ;
    public final ASTOperationCallStatement callStat() throws RecognitionException {
        ASTOperationCallStatement n = null;

        ASTExpression e = null;


        try {
            // Generator.g:1605:1: (e= inSoilExpression )
            // Generator.g:1606:3: e= inSoilExpression
            {
            pushFollow(FOLLOW_inSoilExpression_in_callStat6557);
            e=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTOperationCallStatement(e); 
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
    // $ANTLR end "callStat"


    // $ANTLR start "nothing"
    // Generator.g:1624:1: nothing : ;
    public final void nothing() throws RecognitionException {
        try {
            // Generator.g:1625:1: ()
            // Generator.g:1626:1: 
            {
            }

        }
        finally {
        }
        return ;
    }
    // $ANTLR end "nothing"

    public static class rValue_return extends ParserRuleReturnScope {
        public ASTRValue n;
    };

    // $ANTLR start "rValue"
    // Generator.g:1632:1: rValue returns [ASTRValue n] options {backtrack=true; memoize=true; } : (e= inSoilExpression | loc= lobjCreateStat | 'new' objType= simpleType ( LPAREN objName= inSoilExpression RPAREN )? );
    public final GeneratorParser.rValue_return rValue() throws RecognitionException {
        GeneratorParser.rValue_return retval = new GeneratorParser.rValue_return();
        retval.start = input.LT(1);
        int rValue_StartIndex = input.index();
        ASTExpression e = null;

        ASTNewLinkObjectStatement loc = null;

        ASTSimpleType objType = null;

        ASTExpression objName = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 91) ) { return retval; }
            // Generator.g:1637:1: (e= inSoilExpression | loc= lobjCreateStat | 'new' objType= simpleType ( LPAREN objName= inSoilExpression RPAREN )? )
            int alt103=3;
            alt103 = dfa103.predict(input);
            switch (alt103) {
                case 1 :
                    // Generator.g:1638:3: e= inSoilExpression
                    {
                    pushFollow(FOLLOW_inSoilExpression_in_rValue6629);
                    e=inSoilExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n = new ASTRValueExpressionOrOpCall(e); 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:1641:3: loc= lobjCreateStat
                    {
                    pushFollow(FOLLOW_lobjCreateStat_in_rValue6645);
                    loc=lobjCreateStat();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       
                          loc.setSourcePosition(((Token)retval.start));
                          loc.setParsedText(input.toString(retval.start,input.LT(-1)));
                          retval.n = new ASTRValueNewLinkObject(loc);
                        
                    }

                    }
                    break;
                case 3 :
                    // Generator.g:1648:3: 'new' objType= simpleType ( LPAREN objName= inSoilExpression RPAREN )?
                    {
                    match(input,99,FOLLOW_99_in_rValue6657); if (state.failed) return retval;
                    pushFollow(FOLLOW_simpleType_in_rValue6666);
                    objType=simpleType();

                    state._fsp--;
                    if (state.failed) return retval;
                    // Generator.g:1650:3: ( LPAREN objName= inSoilExpression RPAREN )?
                    int alt102=2;
                    int LA102_0 = input.LA(1);

                    if ( (LA102_0==LPAREN) ) {
                        alt102=1;
                    }
                    switch (alt102) {
                        case 1 :
                            // Generator.g:1651:5: LPAREN objName= inSoilExpression RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_rValue6676); if (state.failed) return retval;
                            pushFollow(FOLLOW_inSoilExpression_in_rValue6688);
                            objName=inSoilExpression();

                            state._fsp--;
                            if (state.failed) return retval;
                            match(input,RPAREN,FOLLOW_RPAREN_in_rValue6694); if (state.failed) return retval;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       
                          ASTNewObjectStatement nos = 
                            new ASTNewObjectStatement(objType, objName);
                          
                          nos.setSourcePosition(((Token)retval.start));
                          nos.setParsedText(input.toString(retval.start,input.LT(-1)));
                            
                          retval.n = new ASTRValueNewObject(nos); 
                        
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 91, rValue_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "rValue"


    // $ANTLR start "rValList"
    // Generator.g:1670:1: rValList returns [List<ASTRValue> n] : ( nothing | rl= rValListMin1 );
    public final List<ASTRValue> rValList() throws RecognitionException {
        List<ASTRValue> n = null;

        List<ASTRValue> rl = null;


        try {
            // Generator.g:1671:1: ( nothing | rl= rValListMin1 )
            int alt104=2;
            int LA104_0 = input.LA(1);

            if ( (LA104_0==EOF) ) {
                alt104=1;
            }
            else if ( ((LA104_0>=IDENT && LA104_0<=LPAREN)||LA104_0==INT||(LA104_0>=PLUS && LA104_0<=MINUS)||LA104_0==AT||(LA104_0>=REAL && LA104_0<=HASH)||LA104_0==50||LA104_0==73||LA104_0==79||(LA104_0>=81 && LA104_0<=84)||(LA104_0>=86 && LA104_0<=97)||LA104_0==99) ) {
                alt104=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 104, 0, input);

                throw nvae;
            }
            switch (alt104) {
                case 1 :
                    // Generator.g:1672:3: nothing
                    {
                    pushFollow(FOLLOW_nothing_in_rValList6722);
                    nothing();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ArrayList<ASTRValue>(); 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:1675:3: rl= rValListMin1
                    {
                    pushFollow(FOLLOW_rValListMin1_in_rValList6749);
                    rl=rValListMin1();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = rl; 
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
    // $ANTLR end "rValList"


    // $ANTLR start "rValListMin1"
    // Generator.g:1683:1: rValListMin1 returns [List<ASTRValue> n] : r= rValue ( COMMA r= rValue )* ;
    public final List<ASTRValue> rValListMin1() throws RecognitionException {
        List<ASTRValue> n = null;

        GeneratorParser.rValue_return r = null;



          n = new ArrayList<ASTRValue>();

        try {
            // Generator.g:1687:1: (r= rValue ( COMMA r= rValue )* )
            // Generator.g:1688:3: r= rValue ( COMMA r= rValue )*
            {
            pushFollow(FOLLOW_rValue_in_rValListMin16782);
            r=rValue();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.add((r!=null?r.n:null)); 
            }
            // Generator.g:1690:3: ( COMMA r= rValue )*
            loop105:
            do {
                int alt105=2;
                int LA105_0 = input.LA(1);

                if ( (LA105_0==COMMA) ) {
                    alt105=1;
                }


                switch (alt105) {
            	case 1 :
            	    // Generator.g:1691:5: COMMA r= rValue
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_rValListMin16796); if (state.failed) return n;
            	    pushFollow(FOLLOW_rValue_in_rValListMin16806);
            	    r=rValue();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.add((r!=null?r.n:null)); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop105;
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
    // $ANTLR end "rValListMin1"


    // $ANTLR start "rValListMin2"
    // Generator.g:1701:1: rValListMin2 returns [List<ASTRValue> n] : r= rValue COMMA r= rValue ( COMMA r= rValue )* ;
    public final List<ASTRValue> rValListMin2() throws RecognitionException {
        List<ASTRValue> n = null;

        GeneratorParser.rValue_return r = null;



          n = new ArrayList<ASTRValue>();

        try {
            // Generator.g:1705:1: (r= rValue COMMA r= rValue ( COMMA r= rValue )* )
            // Generator.g:1706:3: r= rValue COMMA r= rValue ( COMMA r= rValue )*
            {
            pushFollow(FOLLOW_rValue_in_rValListMin26845);
            r=rValue();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.add((r!=null?r.n:null)); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_rValListMin26853); if (state.failed) return n;
            pushFollow(FOLLOW_rValue_in_rValListMin26861);
            r=rValue();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.add((r!=null?r.n:null)); 
            }
            // Generator.g:1711:3: ( COMMA r= rValue )*
            loop106:
            do {
                int alt106=2;
                int LA106_0 = input.LA(1);

                if ( (LA106_0==COMMA) ) {
                    alt106=1;
                }


                switch (alt106) {
            	case 1 :
            	    // Generator.g:1712:5: COMMA r= rValue
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_rValListMin26875); if (state.failed) return n;
            	    pushFollow(FOLLOW_rValue_in_rValListMin26885);
            	    r=rValue();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.add((r!=null?r.n:null)); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop106;
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
    // $ANTLR end "rValListMin2"


    // $ANTLR start "inSoilExpression"
    // Generator.g:1722:1: inSoilExpression returns [ASTExpression n] : e= expression ;
    public final ASTExpression inSoilExpression() throws RecognitionException {
        ASTExpression n = null;

        GeneratorParser.expression_return e = null;


        try {
            // Generator.g:1723:1: (e= expression )
            // Generator.g:1724:3: e= expression
            {
            pushFollow(FOLLOW_expression_in_inSoilExpression6919);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               if ((e!=null?e.n:null) != null) (e!=null?e.n:null).setStringRep((e!=null?input.toString(e.start,e.stop):null)); 
            }
            if ( state.backtracking==0 ) {
               n = (e!=null?e.n:null); 
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
    // $ANTLR end "inSoilExpression"


    // $ANTLR start "exprList"
    // Generator.g:1733:1: exprList returns [List<ASTExpression> n] : ( nothing | el= exprListMin1 );
    public final List<ASTExpression> exprList() throws RecognitionException {
        List<ASTExpression> n = null;

        List<ASTExpression> el = null;


        try {
            // Generator.g:1734:1: ( nothing | el= exprListMin1 )
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==RPAREN) ) {
                alt107=1;
            }
            else if ( ((LA107_0>=IDENT && LA107_0<=LPAREN)||LA107_0==INT||(LA107_0>=PLUS && LA107_0<=MINUS)||LA107_0==AT||(LA107_0>=REAL && LA107_0<=HASH)||LA107_0==50||LA107_0==73||LA107_0==79||(LA107_0>=81 && LA107_0<=84)||(LA107_0>=86 && LA107_0<=97)) ) {
                alt107=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 107, 0, input);

                throw nvae;
            }
            switch (alt107) {
                case 1 :
                    // Generator.g:1735:3: nothing
                    {
                    pushFollow(FOLLOW_nothing_in_exprList6948);
                    nothing();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ArrayList<ASTExpression>(); 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:1738:3: el= exprListMin1
                    {
                    pushFollow(FOLLOW_exprListMin1_in_exprList6966);
                    el=exprListMin1();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = el; 
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
    // $ANTLR end "exprList"


    // $ANTLR start "exprListMin1"
    // Generator.g:1746:1: exprListMin1 returns [List<ASTExpression> n] : e= inSoilExpression ( COMMA e= inSoilExpression )* ;
    public final List<ASTExpression> exprListMin1() throws RecognitionException {
        List<ASTExpression> n = null;

        ASTExpression e = null;



          n = new ArrayList<ASTExpression>();

        try {
            // Generator.g:1750:1: (e= inSoilExpression ( COMMA e= inSoilExpression )* )
            // Generator.g:1751:3: e= inSoilExpression ( COMMA e= inSoilExpression )*
            {
            pushFollow(FOLLOW_inSoilExpression_in_exprListMin16999);
            e=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               if (e != null) n.add(e); 
            }
            // Generator.g:1753:3: ( COMMA e= inSoilExpression )*
            loop108:
            do {
                int alt108=2;
                int LA108_0 = input.LA(1);

                if ( (LA108_0==COMMA) ) {
                    alt108=1;
                }


                switch (alt108) {
            	case 1 :
            	    // Generator.g:1754:5: COMMA e= inSoilExpression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_exprListMin17014); if (state.failed) return n;
            	    pushFollow(FOLLOW_inSoilExpression_in_exprListMin17024);
            	    e=inSoilExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       if (e != null) n.add(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop108;
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
    // $ANTLR end "exprListMin1"


    // $ANTLR start "exprListMin2"
    // Generator.g:1764:1: exprListMin2 returns [List<ASTExpression> n] : e= inSoilExpression COMMA e= inSoilExpression ( COMMA e= inSoilExpression )* ;
    public final List<ASTExpression> exprListMin2() throws RecognitionException {
        List<ASTExpression> n = null;

        ASTExpression e = null;



          n = new ArrayList<ASTExpression>();

        try {
            // Generator.g:1768:1: (e= inSoilExpression COMMA e= inSoilExpression ( COMMA e= inSoilExpression )* )
            // Generator.g:1769:3: e= inSoilExpression COMMA e= inSoilExpression ( COMMA e= inSoilExpression )*
            {
            pushFollow(FOLLOW_inSoilExpression_in_exprListMin27064);
            e=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               if (e != null) n.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_exprListMin27072); if (state.failed) return n;
            pushFollow(FOLLOW_inSoilExpression_in_exprListMin27080);
            e=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               if (e != null) n.add(e); 
            }
            // Generator.g:1774:3: ( COMMA e= inSoilExpression )*
            loop109:
            do {
                int alt109=2;
                int LA109_0 = input.LA(1);

                if ( (LA109_0==COMMA) ) {
                    alt109=1;
                }


                switch (alt109) {
            	case 1 :
            	    // Generator.g:1775:5: COMMA e= inSoilExpression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_exprListMin27094); if (state.failed) return n;
            	    pushFollow(FOLLOW_inSoilExpression_in_exprListMin27104);
            	    e=inSoilExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       if (e != null) n.add(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop109;
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
    // $ANTLR end "exprListMin2"


    // $ANTLR start "identList"
    // Generator.g:1785:1: identList returns [List<String> n] : ( nothing | il= identListMin1 );
    public final List<String> identList() throws RecognitionException {
        List<String> n = null;

        List<String> il = null;


        try {
            // Generator.g:1786:1: ( nothing | il= identListMin1 )
            int alt110=2;
            int LA110_0 = input.LA(1);

            if ( (LA110_0==EOF||(LA110_0>=RPAREN && LA110_0<=SEMI)||LA110_0==47||LA110_0==52) ) {
                alt110=1;
            }
            else if ( (LA110_0==IDENT) ) {
                alt110=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 110, 0, input);

                throw nvae;
            }
            switch (alt110) {
                case 1 :
                    // Generator.g:1787:3: nothing
                    {
                    pushFollow(FOLLOW_nothing_in_identList7134);
                    nothing();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ArrayList<String>(); 
                    }

                    }
                    break;
                case 2 :
                    // Generator.g:1790:3: il= identListMin1
                    {
                    pushFollow(FOLLOW_identListMin1_in_identList7151);
                    il=identListMin1();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = il; 
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
    // $ANTLR end "identList"


    // $ANTLR start "identListMin1"
    // Generator.g:1798:1: identListMin1 returns [List<String> n] : id= IDENT ( COMMA id= IDENT )* ;
    public final List<String> identListMin1() throws RecognitionException {
        List<String> n = null;

        Token id=null;


          n = new ArrayList<String>();

        try {
            // Generator.g:1802:1: (id= IDENT ( COMMA id= IDENT )* )
            // Generator.g:1803:3: id= IDENT ( COMMA id= IDENT )*
            {
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_identListMin17185); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.add((id!=null?id.getText():null)); 
            }
            // Generator.g:1805:3: ( COMMA id= IDENT )*
            loop111:
            do {
                int alt111=2;
                int LA111_0 = input.LA(1);

                if ( (LA111_0==COMMA) ) {
                    alt111=1;
                }


                switch (alt111) {
            	case 1 :
            	    // Generator.g:1806:5: COMMA id= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_identListMin17199); if (state.failed) return n;
            	    id=(Token)match(input,IDENT,FOLLOW_IDENT_in_identListMin17209); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {

            	          n.add((id!=null?id.getText():null)); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop111;
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
    // $ANTLR end "identListMin1"

    // $ANTLR start synpred1_Generator
    public final void synpred1_Generator_fragment() throws RecognitionException {   
        // Generator.g:1152:7: ( COLON type EQUAL )
        // Generator.g:1152:8: COLON type EQUAL
        {
        match(input,COLON,FOLLOW_COLON_in_synpred1_Generator5088); if (state.failed) return ;
        pushFollow(FOLLOW_type_in_synpred1_Generator5090);
        type();

        state._fsp--;
        if (state.failed) return ;
        match(input,EQUAL,FOLLOW_EQUAL_in_synpred1_Generator5092); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_Generator

    // $ANTLR start synpred3_Generator
    public final void synpred3_Generator_fragment() throws RecognitionException {   
        GeneratorParser.varAssignStat_return vas = null;


        // Generator.g:1336:5: (vas= varAssignStat )
        // Generator.g:1336:5: vas= varAssignStat
        {
        pushFollow(FOLLOW_varAssignStat_in_synpred3_Generator5720);
        vas=varAssignStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_Generator

    // $ANTLR start synpred4_Generator
    public final void synpred4_Generator_fragment() throws RecognitionException {   
        ASTAttributeAssignmentStatement aas = null;


        // Generator.g:1337:5: (aas= attAssignStat )
        // Generator.g:1337:5: aas= attAssignStat
        {
        pushFollow(FOLLOW_attAssignStat_in_synpred4_Generator5734);
        aas=attAssignStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_Generator

    // $ANTLR start synpred5_Generator
    public final void synpred5_Generator_fragment() throws RecognitionException {   
        ASTNewLinkObjectStatement lcs = null;


        // Generator.g:1338:5: (lcs= lobjCreateStat )
        // Generator.g:1338:5: lcs= lobjCreateStat
        {
        pushFollow(FOLLOW_lobjCreateStat_in_synpred5_Generator5748);
        lcs=lobjCreateStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_Generator

    // $ANTLR start synpred6_Generator
    public final void synpred6_Generator_fragment() throws RecognitionException {   
        GeneratorParser.objCreateStat_return ocs = null;


        // Generator.g:1339:5: (ocs= objCreateStat )
        // Generator.g:1339:5: ocs= objCreateStat
        {
        pushFollow(FOLLOW_objCreateStat_in_synpred6_Generator5761);
        ocs=objCreateStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_Generator

    // $ANTLR start synpred10_Generator
    public final void synpred10_Generator_fragment() throws RecognitionException {   
        ASTConditionalExecutionStatement ces = null;


        // Generator.g:1343:5: (ces= condExStat )
        // Generator.g:1343:5: ces= condExStat
        {
        pushFollow(FOLLOW_condExStat_in_synpred10_Generator5822);
        ces=condExStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred10_Generator

    // $ANTLR start synpred12_Generator
    public final void synpred12_Generator_fragment() throws RecognitionException {   
        Token varName=null;
        GeneratorParser.rValue_return rVal = null;


        // Generator.g:1370:3: (varName= IDENT COLON_EQUAL rVal= rValue )
        // Generator.g:1370:3: varName= IDENT COLON_EQUAL rVal= rValue
        {
        varName=(Token)match(input,IDENT,FOLLOW_IDENT_in_synpred12_Generator5942); if (state.failed) return ;
        match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_synpred12_Generator5946); if (state.failed) return ;
        pushFollow(FOLLOW_rValue_in_synpred12_Generator5954);
        rVal=rValue();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred12_Generator

    // $ANTLR start synpred14_Generator
    public final void synpred14_Generator_fragment() throws RecognitionException {   
        ASTNewLinkObjectStatement loc = null;


        // Generator.g:1641:3: (loc= lobjCreateStat )
        // Generator.g:1641:3: loc= lobjCreateStat
        {
        pushFollow(FOLLOW_lobjCreateStat_in_synpred14_Generator6645);
        loc=lobjCreateStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred14_Generator

    // Delegated rules

    public final boolean synpred4_Generator() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_Generator_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
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
    public final boolean synpred12_Generator() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred12_Generator_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_Generator() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_Generator_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_Generator() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_Generator_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_Generator() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_Generator_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_Generator() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_Generator_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_Generator() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_Generator_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA96 dfa96 = new DFA96(this);
    protected DFA103 dfa103 = new DFA103(this);
    static final String DFA96_eotS =
        "\45\uffff";
    static final String DFA96_eofS =
        "\1\1\44\uffff";
    static final String DFA96_minS =
        "\1\4\4\uffff\26\0\12\uffff";
    static final String DFA96_maxS =
        "\1\147\4\uffff\26\0\12\uffff";
    static final String DFA96_acceptS =
        "\1\uffff\1\1\31\uffff\1\6\1\7\1\10\1\12\1\2\1\3\1\13\1\11\1\4\1"+
        "\5";
    static final String DFA96_specialS =
        "\5\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14"+
        "\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\12\uffff}>";
    static final String[] DFA96_transitionS = {
            "\1\5\1\30\1\uffff\1\1\13\uffff\1\12\6\uffff\2\7\2\uffff\1\25"+
            "\1\uffff\1\13\1\14\1\15\14\uffff\1\1\1\36\1\uffff\1\31\1\uffff"+
            "\1\1\24\uffff\1\6\5\uffff\1\7\1\uffff\1\26\3\27\1\uffff\1\10"+
            "\1\11\4\16\1\17\1\20\1\21\1\22\1\23\1\24\1\uffff\1\32\1\33\1"+
            "\34\1\uffff\1\35",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA96_eot = DFA.unpackEncodedString(DFA96_eotS);
    static final short[] DFA96_eof = DFA.unpackEncodedString(DFA96_eofS);
    static final char[] DFA96_min = DFA.unpackEncodedStringToUnsignedChars(DFA96_minS);
    static final char[] DFA96_max = DFA.unpackEncodedStringToUnsignedChars(DFA96_maxS);
    static final short[] DFA96_accept = DFA.unpackEncodedString(DFA96_acceptS);
    static final short[] DFA96_special = DFA.unpackEncodedString(DFA96_specialS);
    static final short[][] DFA96_transition;

    static {
        int numStates = DFA96_transitionS.length;
        DFA96_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA96_transition[i] = DFA.unpackEncodedString(DFA96_transitionS[i]);
        }
    }

    class DFA96 extends DFA {

        public DFA96(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 96;
            this.eot = DFA96_eot;
            this.eof = DFA96_eof;
            this.min = DFA96_min;
            this.max = DFA96_max;
            this.accept = DFA96_accept;
            this.special = DFA96_special;
            this.transition = DFA96_transition;
        }
        public String getDescription() {
            return "1328:1: singleStat returns [ASTStatement n] options {backtrack=true; memoize=true; } : (emp= emptyStat | vas= varAssignStat | aas= attAssignStat | lcs= lobjCreateStat | ocs= objCreateStat | ods= objDestroyStat | lis= lnkInsStat | lds= lnkDelStat | ces= condExStat | its= iterStat | ops= callStat );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA96_5 = input.LA(1);

                         
                        int index96_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Generator()) ) {s = 31;}

                        else if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_5);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA96_6 = input.LA(1);

                         
                        int index96_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_6);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA96_7 = input.LA(1);

                         
                        int index96_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_7);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA96_8 = input.LA(1);

                         
                        int index96_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_8);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA96_9 = input.LA(1);

                         
                        int index96_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_9);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA96_10 = input.LA(1);

                         
                        int index96_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_10);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA96_11 = input.LA(1);

                         
                        int index96_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_11);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA96_12 = input.LA(1);

                         
                        int index96_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_12);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA96_13 = input.LA(1);

                         
                        int index96_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_13);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA96_14 = input.LA(1);

                         
                        int index96_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_14);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA96_15 = input.LA(1);

                         
                        int index96_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_15);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA96_16 = input.LA(1);

                         
                        int index96_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_16);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA96_17 = input.LA(1);

                         
                        int index96_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_17);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA96_18 = input.LA(1);

                         
                        int index96_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_18);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA96_19 = input.LA(1);

                         
                        int index96_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_19);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA96_20 = input.LA(1);

                         
                        int index96_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_20);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA96_21 = input.LA(1);

                         
                        int index96_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_21);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA96_22 = input.LA(1);

                         
                        int index96_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_22);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA96_23 = input.LA(1);

                         
                        int index96_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_23);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA96_24 = input.LA(1);

                         
                        int index96_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_24);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA96_25 = input.LA(1);

                         
                        int index96_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Generator()) ) {s = 32;}

                        else if ( (synpred10_Generator()) ) {s = 34;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index96_25);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA96_26 = input.LA(1);

                         
                        int index96_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Generator()) ) {s = 35;}

                        else if ( (synpred6_Generator()) ) {s = 36;}

                         
                        input.seek(index96_26);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 96, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA103_eotS =
        "\31\uffff";
    static final String DFA103_eofS =
        "\31\uffff";
    static final String DFA103_minS =
        "\1\4\25\uffff\1\0\2\uffff";
    static final String DFA103_maxS =
        "\1\143\25\uffff\1\0\2\uffff";
    static final String DFA103_acceptS =
        "\1\uffff\1\1\25\uffff\1\2\1\3";
    static final String DFA103_specialS =
        "\26\uffff\1\0\2\uffff}>";
    static final String[] DFA103_transitionS = {
            "\2\1\15\uffff\1\1\6\uffff\2\1\2\uffff\1\1\1\uffff\3\1\17\uffff"+
            "\1\1\26\uffff\1\1\5\uffff\1\1\1\uffff\4\1\1\uffff\14\1\1\uffff"+
            "\1\26",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA103_eot = DFA.unpackEncodedString(DFA103_eotS);
    static final short[] DFA103_eof = DFA.unpackEncodedString(DFA103_eofS);
    static final char[] DFA103_min = DFA.unpackEncodedStringToUnsignedChars(DFA103_minS);
    static final char[] DFA103_max = DFA.unpackEncodedStringToUnsignedChars(DFA103_maxS);
    static final short[] DFA103_accept = DFA.unpackEncodedString(DFA103_acceptS);
    static final short[] DFA103_special = DFA.unpackEncodedString(DFA103_specialS);
    static final short[][] DFA103_transition;

    static {
        int numStates = DFA103_transitionS.length;
        DFA103_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA103_transition[i] = DFA.unpackEncodedString(DFA103_transitionS[i]);
        }
    }

    class DFA103 extends DFA {

        public DFA103(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 103;
            this.eot = DFA103_eot;
            this.eof = DFA103_eof;
            this.min = DFA103_min;
            this.max = DFA103_max;
            this.accept = DFA103_accept;
            this.special = DFA103_special;
            this.transition = DFA103_transition;
        }
        public String getDescription() {
            return "1632:1: rValue returns [ASTRValue n] options {backtrack=true; memoize=true; } : (e= inSoilExpression | loc= lobjCreateStat | 'new' objType= simpleType ( LPAREN objName= inSoilExpression RPAREN )? );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA103_22 = input.LA(1);

                         
                        int index103_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred14_Generator()) ) {s = 23;}

                        else if ( (true) ) {s = 24;}

                         
                        input.seek(index103_22);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 103, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_invariant_in_invariantListOnly80 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_EOF_in_invariantListOnly91 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_procedure_in_procedureListOnly136 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_EOF_in_procedureListOnly151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_procedure179 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_procedure183 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_procedure185 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_variableDeclarationList_in_procedure189 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_procedure191 = new BitSet(new long[]{0x0000600000000000L});
    public static final BitSet FOLLOW_45_in_procedure199 = new BitSet(new long[]{0x0000000000000090L});
    public static final BitSet FOLLOW_variableDeclarationList_in_procedure203 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_in_procedure205 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_procedure214 = new BitSet(new long[]{0x0005800000000810L});
    public static final BitSet FOLLOW_instructionList_in_procedure218 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_procedure220 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_in_procedure222 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableDeclaration_in_variableDeclarationList260 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_variableDeclarationList271 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclaration_in_variableDeclarationList275 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_instruction_in_instructionList319 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_in_instructionList321 = new BitSet(new long[]{0x0005000000000812L});
    public static final BitSet FOLLOW_variableAssignment_in_instruction356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attributeAssignment_in_instruction371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_loop_in_instruction385 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicInstruction_in_instruction402 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifThenElse_in_instruction416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableAssignment446 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_variableAssignment448 = new BitSet(new long[]{0x0000000000000810L});
    public static final BitSet FOLLOW_valueInstruction_in_variableAssignment452 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oclExpression_in_attributeAssignment484 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_DOT_in_attributeAssignment486 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_attributeAssignment490 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_attributeAssignment498 = new BitSet(new long[]{0x0000000000000810L});
    public static final BitSet FOLLOW_valueInstruction_in_attributeAssignment502 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_loop534 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclaration_in_loop538 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_loop540 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_oclExpression_in_loop544 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_loop546 = new BitSet(new long[]{0x0005800000000810L});
    public static final BitSet FOLLOW_instructionList_in_loop557 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_loop559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_ifThenElse595 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_oclExpression_in_ifThenElse599 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_ifThenElse610 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_ifThenElse612 = new BitSet(new long[]{0x0005800000000810L});
    public static final BitSet FOLLOW_instructionList_in_ifThenElse616 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_ifThenElse618 = new BitSet(new long[]{0x0010000000000002L});
    public static final BitSet FOLLOW_52_in_ifThenElse629 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_ifThenElse631 = new BitSet(new long[]{0x0005800000000810L});
    public static final BitSet FOLLOW_instructionList_in_ifThenElse635 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_ifThenElse637 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicInstruction_in_valueInstruction677 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oclExpression_in_valueInstruction691 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_atomicInstruction721 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_atomicInstruction725 = new BitSet(new long[]{0x0000000000000850L});
    public static final BitSet FOLLOW_instructionParameter_in_atomicInstruction739 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_COMMA_in_atomicInstruction757 = new BitSet(new long[]{0x0000000000000810L});
    public static final BitSet FOLLOW_instructionParameter_in_atomicInstruction761 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RPAREN_in_atomicInstruction809 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oclExpression_in_instructionParameter837 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_instrParameterIdent_in_instructionParameter849 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_instrParameterIdent877 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACK_in_oclExpression905 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_oclExpression909 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RBRACK_in_oclExpression911 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_procedureCallOnly946 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_procedureCallOnly954 = new BitSet(new long[]{0x000400074C080070L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_procedureCallOnly965 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_COMMA_in_procedureCallOnly975 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_procedureCallOnly979 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RPAREN_in_procedureCallOnly993 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_procedureCallOnly999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_model1026 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_model1030 = new BitSet(new long[]{0xB3C0000000000010L,0x0000000000000001L});
    public static final BitSet FOLLOW_enumTypeDefinition_in_model1042 = new BitSet(new long[]{0xB3C0000000000010L,0x0000000000000001L});
    public static final BitSet FOLLOW_generalClassDefinition_in_model1059 = new BitSet(new long[]{0xB340000000000010L,0x0000000000000001L});
    public static final BitSet FOLLOW_associationDefinition_in_model1076 = new BitSet(new long[]{0xB340000000000010L,0x0000000000000001L});
    public static final BitSet FOLLOW_54_in_model1092 = new BitSet(new long[]{0xB340000000000010L,0x0000000000000011L});
    public static final BitSet FOLLOW_invariant_in_model1110 = new BitSet(new long[]{0xB340000000000010L,0x0000000000000011L});
    public static final BitSet FOLLOW_prePost_in_model1131 = new BitSet(new long[]{0xB340000000000010L,0x0000000000000011L});
    public static final BitSet FOLLOW_EOF_in_model1172 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_enumTypeDefinition1199 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_enumTypeDefinition1203 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_enumTypeDefinition1205 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_enumTypeDefinition1209 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RBRACE_in_enumTypeDefinition1211 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_enumTypeDefinition1215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_generalClassDefinition1254 = new BitSet(new long[]{0x3300000000000000L});
    public static final BitSet FOLLOW_classDefinition_in_generalClassDefinition1272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_associationClassDefinition_in_generalClassDefinition1292 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_classDefinition1331 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_classDefinition1335 = new BitSet(new long[]{0x0C40800000008000L});
    public static final BitSet FOLLOW_LESS_in_classDefinition1345 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_classDefinition1349 = new BitSet(new long[]{0x0C40800000000000L});
    public static final BitSet FOLLOW_58_in_classDefinition1362 = new BitSet(new long[]{0x0840800000000010L});
    public static final BitSet FOLLOW_attributeDefinition_in_classDefinition1375 = new BitSet(new long[]{0x0840800000000010L});
    public static final BitSet FOLLOW_59_in_classDefinition1396 = new BitSet(new long[]{0x0040800000000010L});
    public static final BitSet FOLLOW_operationDefinition_in_classDefinition1409 = new BitSet(new long[]{0x0040800000000010L});
    public static final BitSet FOLLOW_54_in_classDefinition1430 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000060L});
    public static final BitSet FOLLOW_invariantClause_in_classDefinition1450 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000060L});
    public static final BitSet FOLLOW_47_in_classDefinition1474 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_associationClassDefinition1507 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationClassDefinition1533 = new BitSet(new long[]{0x4000000000008000L});
    public static final BitSet FOLLOW_LESS_in_associationClassDefinition1543 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_associationClassDefinition1547 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_62_in_associationClassDefinition1558 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_associationEnd_in_associationClassDefinition1566 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_associationEnd_in_associationClassDefinition1578 = new BitSet(new long[]{0x8C40800000000010L,0x0000000000000001L});
    public static final BitSet FOLLOW_58_in_associationClassDefinition1591 = new BitSet(new long[]{0x8840800000000010L,0x0000000000000001L});
    public static final BitSet FOLLOW_attributeDefinition_in_associationClassDefinition1604 = new BitSet(new long[]{0x8840800000000010L,0x0000000000000001L});
    public static final BitSet FOLLOW_59_in_associationClassDefinition1625 = new BitSet(new long[]{0x8040800000000010L,0x0000000000000001L});
    public static final BitSet FOLLOW_operationDefinition_in_associationClassDefinition1638 = new BitSet(new long[]{0x8040800000000010L,0x0000000000000001L});
    public static final BitSet FOLLOW_54_in_associationClassDefinition1659 = new BitSet(new long[]{0x8000800000000000L,0x0000000000000061L});
    public static final BitSet FOLLOW_invariantClause_in_associationClassDefinition1679 = new BitSet(new long[]{0x8000800000000000L,0x0000000000000061L});
    public static final BitSet FOLLOW_set_in_associationClassDefinition1713 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_associationClassDefinition1742 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_attributeDefinition1771 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_attributeDefinition1773 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_attributeDefinition1777 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_attributeDefinition1781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationDefinition1821 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_paramList_in_operationDefinition1831 = new BitSet(new long[]{0x0000400000030082L,0x0000000000000180L});
    public static final BitSet FOLLOW_COLON_in_operationDefinition1839 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_operationDefinition1845 = new BitSet(new long[]{0x0000400000020082L,0x0000000000000180L});
    public static final BitSet FOLLOW_EQUAL_in_operationDefinition1872 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_operationDefinition1878 = new BitSet(new long[]{0x0000000000000082L,0x0000000000000180L});
    public static final BitSet FOLLOW_46_in_operationDefinition1896 = new BitSet(new long[]{0x000500074C080030L,0x000000BBFFDE8200L});
    public static final BitSet FOLLOW_stat_in_operationDefinition1902 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_operationDefinition1904 = new BitSet(new long[]{0x0000000000000082L,0x0000000000000180L});
    public static final BitSet FOLLOW_prePostClause_in_operationDefinition1925 = new BitSet(new long[]{0x0000000000000082L,0x0000000000000180L});
    public static final BitSet FOLLOW_SEMI_in_operationDefinition1938 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_keyAssociation_in_associationDefinition1976 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_63_in_associationDefinition1980 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_64_in_associationDefinition1984 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationDefinition1999 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_62_in_associationDefinition2007 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_associationEnd_in_associationDefinition2015 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_associationEnd_in_associationDefinition2027 = new BitSet(new long[]{0x0000800000000010L});
    public static final BitSet FOLLOW_47_in_associationDefinition2038 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd2064 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LBRACK_in_associationEnd2066 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_multiplicity_in_associationEnd2070 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RBRACK_in_associationEnd2072 = new BitSet(new long[]{0x0000000000000092L,0x000000000000000EL});
    public static final BitSet FOLLOW_keyRole_in_associationEnd2083 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd2087 = new BitSet(new long[]{0x0000000000000092L,0x000000000000000EL});
    public static final BitSet FOLLOW_65_in_associationEnd2108 = new BitSet(new long[]{0x0000000000000092L,0x000000000000000EL});
    public static final BitSet FOLLOW_66_in_associationEnd2120 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd2124 = new BitSet(new long[]{0x0000000000000092L,0x000000000000000EL});
    public static final BitSet FOLLOW_keyUnion_in_associationEnd2136 = new BitSet(new long[]{0x0000000000000092L,0x000000000000000EL});
    public static final BitSet FOLLOW_67_in_associationEnd2148 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd2152 = new BitSet(new long[]{0x0000000000000092L,0x000000000000000EL});
    public static final BitSet FOLLOW_SEMI_in_associationEnd2169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity2204 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_multiplicity2214 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity2218 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange2247 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_DOTDOT_in_multiplicityRange2257 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange2261 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_multiplicitySpec2295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_multiplicitySpec2305 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_invariant2346 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_invariant2356 = new BitSet(new long[]{0x0000000000010100L});
    public static final BitSet FOLLOW_COMMA_in_invariant2369 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_invariant2373 = new BitSet(new long[]{0x0000000000010100L});
    public static final BitSet FOLLOW_COLON_in_invariant2381 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_simpleType_in_invariant2393 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000060L});
    public static final BitSet FOLLOW_invariantClause_in_invariant2405 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000060L});
    public static final BitSet FOLLOW_69_in_invariantClause2436 = new BitSet(new long[]{0x0000000000010010L});
    public static final BitSet FOLLOW_IDENT_in_invariantClause2442 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_invariantClause2447 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_invariantClause2451 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_70_in_invariantClause2461 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_invariantClause2463 = new BitSet(new long[]{0x0000000000010010L});
    public static final BitSet FOLLOW_IDENT_in_invariantClause2469 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_invariantClause2474 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_invariantClause2478 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_prePost2504 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_prePost2508 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_COLON_COLON_in_prePost2510 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_prePost2514 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_paramList_in_prePost2518 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000180L});
    public static final BitSet FOLLOW_COLON_in_prePost2522 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_prePost2526 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_prePostClause_in_prePost2545 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000180L});
    public static final BitSet FOLLOW_set_in_prePostClause2584 = new BitSet(new long[]{0x0000000000010010L});
    public static final BitSet FOLLOW_IDENT_in_prePostClause2599 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_prePostClause2604 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_prePostClause2608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_keyUnion2630 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_keyAssociation2644 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_keyRole2658 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_expressionOnly2691 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_expressionOnly2693 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_expression2741 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_expression2745 = new BitSet(new long[]{0x0000000000030000L});
    public static final BitSet FOLLOW_COLON_in_expression2749 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_expression2753 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_EQUAL_in_expression2758 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_expression2762 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_expression2764 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_conditionalImpliesExpression_in_expression2789 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_paramList2822 = new BitSet(new long[]{0x0000000000000050L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList2839 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_COMMA_in_paramList2851 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList2855 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RPAREN_in_paramList2875 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_idList2904 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_idList2914 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_idList2918 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_IDENT_in_variableDeclaration2949 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_variableDeclaration2951 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_variableDeclaration2955 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2991 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_conditionalImpliesExpression3004 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3008 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3053 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_conditionalOrExpression3066 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3070 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000800L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3114 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_conditionalXOrExpression3127 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3131 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001000L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression3175 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_conditionalAndExpression3188 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression3192 = new BitSet(new long[]{0x0000000000000002L,0x0000000000002000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression3240 = new BitSet(new long[]{0x0000000000420002L});
    public static final BitSet FOLLOW_set_in_equalityExpression3259 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression3269 = new BitSet(new long[]{0x0000000000420002L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression3318 = new BitSet(new long[]{0x0000000003808002L});
    public static final BitSet FOLLOW_set_in_relationalExpression3336 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression3354 = new BitSet(new long[]{0x0000000003808002L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3404 = new BitSet(new long[]{0x000000000C000002L});
    public static final BitSet FOLLOW_set_in_additiveExpression3422 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3432 = new BitSet(new long[]{0x000000000C000002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3482 = new BitSet(new long[]{0x0000000010100002L,0x0000000000004000L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression3500 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3514 = new BitSet(new long[]{0x0000000010100002L,0x0000000000004000L});
    public static final BitSet FOLLOW_set_in_unaryExpression3576 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression3600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpression_in_unaryExpression3620 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_postfixExpression3653 = new BitSet(new long[]{0x0000000020000402L});
    public static final BitSet FOLLOW_ARROW_in_postfixExpression3671 = new BitSet(new long[]{0x0000000000000010L,0x00000000001E0000L});
    public static final BitSet FOLLOW_DOT_in_postfixExpression3677 = new BitSet(new long[]{0x0000000000000010L,0x00000000001E0000L});
    public static final BitSet FOLLOW_propertyCall_in_postfixExpression3688 = new BitSet(new long[]{0x0000000020000402L});
    public static final BitSet FOLLOW_literal_in_primaryExpression3728 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objectReference_in_primaryExpression3742 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyCall_in_primaryExpression3754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression3765 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_primaryExpression3769 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression3771 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifExpression_in_primaryExpression3783 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_primaryExpression3800 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_DOT_in_primaryExpression3802 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_primaryExpression3804 = new BitSet(new long[]{0x0000000040000022L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression3808 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression3810 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_AT_in_primaryExpression3831 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_primaryExpression3833 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_in_objectReference3860 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_objectReference3868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_queryExpression_in_propertyCall3936 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterateExpression_in_propertyCall3949 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationExpression_in_propertyCall3962 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeExpression_in_propertyCall3975 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_queryExpression4010 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_queryExpression4017 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_queryExpression4028 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_BAR_in_queryExpression4032 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_queryExpression4043 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_queryExpression4049 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_iterateExpression4081 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_iterateExpression4087 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_iterateExpression4095 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_in_iterateExpression4097 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableInitialization_in_iterateExpression4105 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_BAR_in_iterateExpression4107 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_iterateExpression4115 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_iterateExpression4121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression4165 = new BitSet(new long[]{0x0000000040000822L});
    public static final BitSet FOLLOW_LBRACK_in_operationExpression4181 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression4185 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RBRACK_in_operationExpression4187 = new BitSet(new long[]{0x0000000040000022L});
    public static final BitSet FOLLOW_AT_in_operationExpression4200 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_operationExpression4202 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_LPAREN_in_operationExpression4223 = new BitSet(new long[]{0x000400074C080070L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_operationExpression4244 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_COMMA_in_operationExpression4256 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_operationExpression4260 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RPAREN_in_operationExpression4280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_typeExpression4329 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_typeExpression4345 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_typeExpression4349 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_typeExpression4351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_elemVarsDeclaration4390 = new BitSet(new long[]{0x0000000000010002L});
    public static final BitSet FOLLOW_COLON_in_elemVarsDeclaration4398 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_elemVarsDeclaration4402 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableInitialization4437 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_variableInitialization4439 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_variableInitialization4443 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_EQUAL_in_variableInitialization4445 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_variableInitialization4449 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_ifExpression4481 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_ifExpression4485 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_ifExpression4487 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_ifExpression4491 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_ifExpression4493 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_ifExpression4497 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_ifExpression4499 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_86_in_literal4538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_87_in_literal4552 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_literal4565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REAL_in_literal4580 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal4594 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_literal4604 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_literal4608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_literal4620 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_COLON_COLON_in_literal4622 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_literal4626 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionLiteral_in_literal4638 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyCollectionLiteral_in_literal4650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedLiteral_in_literal4662 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleLiteral_in_literal4674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dateLiteral_in_literal4686 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionLiteral4724 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_collectionLiteral4753 = new BitSet(new long[]{0x000400074C084030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral4770 = new BitSet(new long[]{0x0000000000004100L});
    public static final BitSet FOLLOW_COMMA_in_collectionLiteral4783 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral4787 = new BitSet(new long[]{0x0000000000004100L});
    public static final BitSet FOLLOW_RBRACE_in_collectionLiteral4806 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_collectionItem4835 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_DOTDOT_in_collectionItem4846 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_collectionItem4850 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_emptyCollectionLiteral4879 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_emptyCollectionLiteral4881 = new BitSet(new long[]{0x0000000000000000L,0x000000040F000000L});
    public static final BitSet FOLLOW_collectionType_in_emptyCollectionLiteral4885 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_emptyCollectionLiteral4887 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_undefinedLiteral4917 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_undefinedLiteral4919 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_undefinedLiteral4923 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_undefinedLiteral4925 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_undefinedLiteral4939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_95_in_undefinedLiteral4953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_tupleLiteral4987 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_tupleLiteral4993 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral5001 = new BitSet(new long[]{0x0000000000004100L});
    public static final BitSet FOLLOW_COMMA_in_tupleLiteral5012 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral5016 = new BitSet(new long[]{0x0000000000004100L});
    public static final BitSet FOLLOW_RBRACE_in_tupleLiteral5027 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tupleItem5058 = new BitSet(new long[]{0x0000000000030000L});
    public static final BitSet FOLLOW_COLON_in_tupleItem5097 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_tupleItem5101 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_EQUAL_in_tupleItem5103 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_tupleItem5107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_tupleItem5129 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_expression_in_tupleItem5139 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_97_in_dateLiteral5184 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACE_in_dateLiteral5186 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_dateLiteral5190 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_RBRACE_in_dateLiteral5192 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleType_in_type5242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_type5254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleType_in_type5266 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeOnly5298 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_typeOnly5300 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_simpleType5328 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionType5366 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_collectionType5393 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_collectionType5397 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_collectionType5399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_tupleType5433 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_tupleType5435 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType5444 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_COMMA_in_tupleType5455 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType5459 = new BitSet(new long[]{0x0000000000000140L});
    public static final BitSet FOLLOW_RPAREN_in_tupleType5471 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tuplePart5503 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_COLON_in_tuplePart5505 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_tuplePart5509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stat_in_statOnly5558 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_statOnly5562 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nextStat_in_stat5593 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_stat5604 = new BitSet(new long[]{0x000500074C080030L,0x000000BBFFDE8200L});
    public static final BitSet FOLLOW_nextStat_in_stat5610 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_singleStat_in_nextStat5644 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyStat_in_singleStat5699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varAssignStat_in_singleStat5720 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attAssignStat_in_singleStat5734 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lobjCreateStat_in_singleStat5748 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objCreateStat_in_singleStat5761 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objDestroyStat_in_singleStat5775 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lnkInsStat_in_singleStat5788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lnkDelStat_in_singleStat5805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_condExStat_in_singleStat5822 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterStat_in_singleStat5839 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_callStat_in_singleStat5858 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nothing_in_emptyStat5889 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_varAssignStat5942 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_varAssignStat5946 = new BitSet(new long[]{0x000400074C080030L,0x0000000BFFDE8200L});
    public static final BitSet FOLLOW_rValue_in_varAssignStat5954 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identListMin1_in_varAssignStat5973 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_varAssignStat5977 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_99_in_varAssignStat5981 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_simpleType_in_varAssignStat5989 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_LPAREN_in_varAssignStat5999 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_exprList_in_varAssignStat6011 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_varAssignStat6017 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_attAssignStat6052 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_DOT_in_attAssignStat6057 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_attAssignStat6066 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_attAssignStat6070 = new BitSet(new long[]{0x000400074C080030L,0x0000000BFFDE8200L});
    public static final BitSet FOLLOW_rValue_in_attAssignStat6078 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_objCreateStat6104 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_simpleType_in_objCreateStat6112 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_LPAREN_in_objCreateStat6122 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_exprListMin1_in_objCreateStat6134 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_objCreateStat6140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_lobjCreateStat6171 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_lobjCreateStat6179 = new BitSet(new long[]{0x4000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_lobjCreateStat6189 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_inSoilExpression_in_lobjCreateStat6201 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_lobjCreateStat6207 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_62_in_lobjCreateStat6216 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_lobjCreateStat6220 = new BitSet(new long[]{0x000400074C080030L,0x0000000BFFDE8200L});
    public static final BitSet FOLLOW_rValListMin2_in_lobjCreateStat6230 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_lobjCreateStat6234 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_100_in_objDestroyStat6260 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_exprListMin1_in_objDestroyStat6268 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_101_in_lnkInsStat6294 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_lnkInsStat6298 = new BitSet(new long[]{0x000400074C080030L,0x0000000BFFDE8200L});
    public static final BitSet FOLLOW_rValListMin2_in_lnkInsStat6308 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_lnkInsStat6312 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_102_in_lnkInsStat6316 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_lnkInsStat6324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_lnkDelStat6348 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LPAREN_in_lnkDelStat6352 = new BitSet(new long[]{0x000400074C080030L,0x0000000BFFDE8200L});
    public static final BitSet FOLLOW_rValListMin2_in_lnkDelStat6362 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_lnkDelStat6366 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_104_in_lnkDelStat6370 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_lnkDelStat6379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_condExStat6410 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_inSoilExpression_in_condExStat6419 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_condExStat6423 = new BitSet(new long[]{0x000500074C080030L,0x000000BBFFDE8200L});
    public static final BitSet FOLLOW_stat_in_condExStat6432 = new BitSet(new long[]{0x0010800000000000L});
    public static final BitSet FOLLOW_52_in_condExStat6443 = new BitSet(new long[]{0x000500074C080030L,0x000000BBFFDE8200L});
    public static final BitSet FOLLOW_stat_in_condExStat6455 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_condExStat6466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_iterStat6491 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_iterStat6499 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_iterStat6503 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_inSoilExpression_in_iterStat6511 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_105_in_iterStat6515 = new BitSet(new long[]{0x000500074C080030L,0x000000BBFFDE8200L});
    public static final BitSet FOLLOW_stat_in_iterStat6523 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_iterStat6527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_callStat6557 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_rValue6629 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lobjCreateStat_in_rValue6645 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_rValue6657 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_simpleType_in_rValue6666 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_LPAREN_in_rValue6676 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_inSoilExpression_in_rValue6688 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RPAREN_in_rValue6694 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nothing_in_rValList6722 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rValListMin1_in_rValList6749 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rValue_in_rValListMin16782 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_rValListMin16796 = new BitSet(new long[]{0x000400074C080030L,0x0000000BFFDE8200L});
    public static final BitSet FOLLOW_rValue_in_rValListMin16806 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_rValue_in_rValListMin26845 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_rValListMin26853 = new BitSet(new long[]{0x000400074C080030L,0x0000000BFFDE8200L});
    public static final BitSet FOLLOW_rValue_in_rValListMin26861 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_rValListMin26875 = new BitSet(new long[]{0x000400074C080030L,0x0000000BFFDE8200L});
    public static final BitSet FOLLOW_rValue_in_rValListMin26885 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_expression_in_inSoilExpression6919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nothing_in_exprList6948 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exprListMin1_in_exprList6966 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin16999 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_exprListMin17014 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin17024 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin27064 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_exprListMin27072 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin27080 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_exprListMin27094 = new BitSet(new long[]{0x000400074C080030L,0x00000003FFDE8200L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin27104 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_nothing_in_identList7134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identListMin1_in_identList7151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_identListMin17185 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_identListMin17199 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_identListMin17209 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COLON_in_synpred1_Generator5088 = new BitSet(new long[]{0x0000000000000010L,0x000000050F000000L});
    public static final BitSet FOLLOW_type_in_synpred1_Generator5090 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_EQUAL_in_synpred1_Generator5092 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varAssignStat_in_synpred3_Generator5720 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attAssignStat_in_synpred4_Generator5734 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lobjCreateStat_in_synpred5_Generator5748 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objCreateStat_in_synpred6_Generator5761 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_condExStat_in_synpred10_Generator5822 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_synpred12_Generator5942 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_synpred12_Generator5946 = new BitSet(new long[]{0x000400074C080030L,0x0000000BFFDE8200L});
    public static final BitSet FOLLOW_rValue_in_synpred12_Generator5954 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lobjCreateStat_in_synpred14_Generator6645 = new BitSet(new long[]{0x0000000000000002L});

}