// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 Cmd.g 2010-03-01 11:56:05
 
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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all") public class CmdParser extends BaseParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "SEMI", "COLON", "COLON_EQUAL", "IDENT", "LPAREN", "RPAREN", "COMMA", "EQUAL", "LBRACE", "RBRACE", "LESS", "SCRIPTBODY", "LBRACK", "RBRACK", "DOTDOT", "INT", "STAR", "COLON_COLON", "NOT_EQUAL", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "PLUS", "MINUS", "SLASH", "ARROW", "DOT", "AT", "BAR", "REAL", "STRING", "HASH", "NEWLINE", "WS", "SL_COMMENT", "ML_COMMENT", "RANGE_OR_INT", "ESC", "HEX_DIGIT", "VOCAB", "'create'", "'assign'", "'between'", "'destroy'", "'insert'", "'into'", "'delete'", "'from'", "'set'", "'openter'", "'opexit'", "'let'", "'model'", "'constraints'", "'enum'", "'abstract'", "'class'", "'attributes'", "'operations'", "'end'", "'associationClass'", "'associationclass'", "'aggregation'", "'composition'", "'script'", "'ordered'", "'subsets'", "'redefines'", "'context'", "'inv'", "'existential'", "'pre'", "'post'", "'in'", "'implies'", "'or'", "'xor'", "'and'", "'div'", "'not'", "'allInstances'", "'iterate'", "'oclAsType'", "'oclIsKindOf'", "'oclIsTypeOf'", "'if'", "'then'", "'else'", "'endif'", "'true'", "'false'", "'Set'", "'Sequence'", "'Bag'", "'OrderedSet'", "'oclEmpty'", "'oclUndefined'", "'Undefined'", "'null'", "'Tuple'", "'Date'", "'Collection'"
    };
    public static final int STAR=20;
    public static final int EOF=-1;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__91=91;
    public static final int RPAREN=9;
    public static final int T__92=92;
    public static final int GREATER=23;
    public static final int T__90=90;
    public static final int NOT_EQUAL=22;
    public static final int LESS=14;
    public static final int T__99=99;
    public static final int T__98=98;
    public static final int T__97=97;
    public static final int T__96=96;
    public static final int T__95=95;
    public static final int RBRACK=17;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int RBRACE=13;
    public static final int T__83=83;
    public static final int INT=19;
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
    public static final int LBRACK=16;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int ESC=41;
    public static final int LBRACE=12;
    public static final int DOTDOT=18;
    public static final int T__61=61;
    public static final int T__60=60;
    public static final int LPAREN=8;
    public static final int AT=31;
    public static final int T__55=55;
    public static final int ML_COMMENT=39;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int COLON_EQUAL=6;
    public static final int SLASH=28;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int COMMA=10;
    public static final int T__59=59;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int EQUAL=11;
    public static final int T__105=105;
    public static final int IDENT=7;
    public static final int PLUS=26;
    public static final int RANGE_OR_INT=40;
    public static final int DOT=30;
    public static final int T__50=50;
    public static final int SCRIPTBODY=15;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int HASH=35;
    public static final int HEX_DIGIT=42;
    public static final int T__102=102;
    public static final int COLON_COLON=21;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int MINUS=27;
    public static final int SEMI=4;
    public static final int COLON=5;
    public static final int NEWLINE=36;
    public static final int VOCAB=43;
    public static final int ARROW=29;
    public static final int GREATER_EQUAL=25;
    public static final int BAR=32;
    public static final int STRING=34;

    // delegates
    // delegators


        public CmdParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public CmdParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return CmdParser.tokenNames; }
    public String getGrammarFileName() { return "Cmd.g"; }



    // $ANTLR start "cmdList"
    // Cmd.g:79:1: cmdList returns [ASTCmdList cmdList] : c= cmd (c= cmd )* EOF ;
    public final ASTCmdList cmdList() throws RecognitionException {
        ASTCmdList cmdList = null;

        ASTCmd c = null;


         cmdList = new ASTCmdList(); 
        try {
            // Cmd.g:81:1: (c= cmd (c= cmd )* EOF )
            // Cmd.g:82:5: c= cmd (c= cmd )* EOF
            {
            pushFollow(FOLLOW_cmd_in_cmdList65);
            c=cmd();

            state._fsp--;
            if (state.failed) return cmdList;
            if ( state.backtracking==0 ) {
               cmdList.add(c); 
            }
            // Cmd.g:83:5: (c= cmd )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=44 && LA1_0<=45)||(LA1_0>=47 && LA1_0<=48)||LA1_0==50||(LA1_0>=52 && LA1_0<=55)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Cmd.g:83:7: c= cmd
            	    {
            	    pushFollow(FOLLOW_cmd_in_cmdList77);
            	    c=cmd();

            	    state._fsp--;
            	    if (state.failed) return cmdList;
            	    if ( state.backtracking==0 ) {
            	       cmdList.add(c); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            match(input,EOF,FOLLOW_EOF_in_cmdList88); if (state.failed) return cmdList;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return cmdList;
    }
    // $ANTLR end "cmdList"


    // $ANTLR start "cmd"
    // Cmd.g:91:1: cmd returns [ASTCmd n] : stmt= cmdStmt ( SEMI )? ;
    public final ASTCmd cmd() throws RecognitionException {
        ASTCmd n = null;

        ASTCmd stmt = null;


        try {
            // Cmd.g:92:1: (stmt= cmdStmt ( SEMI )? )
            // Cmd.g:93:5: stmt= cmdStmt ( SEMI )?
            {
            pushFollow(FOLLOW_cmdStmt_in_cmd114);
            stmt=cmdStmt();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = stmt; 
            }
            // Cmd.g:93:35: ( SEMI )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==SEMI) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // Cmd.g:93:37: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_cmd119); if (state.failed) return n;

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
    // $ANTLR end "cmd"


    // $ANTLR start "cmdStmt"
    // Cmd.g:109:1: cmdStmt returns [ASTCmd n] : (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd ) ;
    public final ASTCmd cmdStmt() throws RecognitionException {
        ASTCmd n = null;

        ASTCmd nC = null;


        try {
            // Cmd.g:110:1: ( (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd ) )
            // Cmd.g:111:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd )
            {
            // Cmd.g:111:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd )
            int alt3=10;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // Cmd.g:112:7: nC= createCmd
                    {
                    pushFollow(FOLLOW_createCmd_in_cmdStmt150);
                    nC=createCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 2 :
                    // Cmd.g:113:7: nC= createAssignCmd
                    {
                    pushFollow(FOLLOW_createAssignCmd_in_cmdStmt162);
                    nC=createAssignCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 3 :
                    // Cmd.g:114:7: nC= createInsertCmd
                    {
                    pushFollow(FOLLOW_createInsertCmd_in_cmdStmt175);
                    nC=createInsertCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 4 :
                    // Cmd.g:115:7: nC= destroyCmd
                    {
                    pushFollow(FOLLOW_destroyCmd_in_cmdStmt187);
                    nC=destroyCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 5 :
                    // Cmd.g:116:7: nC= insertCmd
                    {
                    pushFollow(FOLLOW_insertCmd_in_cmdStmt199);
                    nC=insertCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 6 :
                    // Cmd.g:117:7: nC= deleteCmd
                    {
                    pushFollow(FOLLOW_deleteCmd_in_cmdStmt211);
                    nC=deleteCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 7 :
                    // Cmd.g:118:7: nC= setCmd
                    {
                    pushFollow(FOLLOW_setCmd_in_cmdStmt223);
                    nC=setCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 8 :
                    // Cmd.g:119:7: nC= opEnterCmd
                    {
                    pushFollow(FOLLOW_opEnterCmd_in_cmdStmt235);
                    nC=opEnterCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 9 :
                    // Cmd.g:120:7: nC= opExitCmd
                    {
                    pushFollow(FOLLOW_opExitCmd_in_cmdStmt247);
                    nC=opExitCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 10 :
                    // Cmd.g:121:7: nC= letCmd
                    {
                    pushFollow(FOLLOW_letCmd_in_cmdStmt259);
                    nC=letCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = nC; 
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
    // $ANTLR end "cmdStmt"


    // $ANTLR start "createCmd"
    // Cmd.g:131:1: createCmd returns [ASTCmd n] : 'create' nIdList= idList COLON t= simpleType ;
    public final ASTCmd createCmd() throws RecognitionException {
        ASTCmd n = null;

        List nIdList = null;

        ASTSimpleType t = null;


        try {
            // Cmd.g:132:1: ( 'create' nIdList= idList COLON t= simpleType )
            // Cmd.g:133:5: 'create' nIdList= idList COLON t= simpleType
            {
            match(input,44,FOLLOW_44_in_createCmd288); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createCmd292);
            nIdList=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_createCmd299); if (state.failed) return n;
            pushFollow(FOLLOW_simpleType_in_createCmd303);
            t=simpleType();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTCreateCmd(nIdList, t); 
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
    // $ANTLR end "createCmd"


    // $ANTLR start "createAssignCmd"
    // Cmd.g:143:1: createAssignCmd returns [ASTCmd n] : 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType ;
    public final ASTCmd createAssignCmd() throws RecognitionException {
        ASTCmd n = null;

        List nIdList = null;

        ASTSimpleType t = null;


        try {
            // Cmd.g:144:1: ( 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType )
            // Cmd.g:145:5: 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType
            {
            match(input,45,FOLLOW_45_in_createAssignCmd332); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createAssignCmd336);
            nIdList=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_createAssignCmd338); if (state.failed) return n;
            match(input,44,FOLLOW_44_in_createAssignCmd340); if (state.failed) return n;
            pushFollow(FOLLOW_simpleType_in_createAssignCmd344);
            t=simpleType();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTCreateAssignCmd(nIdList, t); 
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
    // $ANTLR end "createAssignCmd"


    // $ANTLR start "createInsertCmd"
    // Cmd.g:153:1: createInsertCmd returns [ASTCmd n] : 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN ;
    public final ASTCmd createInsertCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        Token idAssoc=null;
        List idListInsert = null;


        try {
            // Cmd.g:154:1: ( 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN )
            // Cmd.g:155:5: 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN
            {
            match(input,44,FOLLOW_44_in_createInsertCmd364); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_createInsertCmd368); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_createInsertCmd370); if (state.failed) return n;
            idAssoc=(Token)match(input,IDENT,FOLLOW_IDENT_in_createInsertCmd374); if (state.failed) return n;
            match(input,46,FOLLOW_46_in_createInsertCmd380); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_createInsertCmd382); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createInsertCmd386);
            idListInsert=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_createInsertCmd388); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTCreateInsertCmd( id, idAssoc, idListInsert); 
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
    // $ANTLR end "createInsertCmd"


    // $ANTLR start "destroyCmd"
    // Cmd.g:166:1: destroyCmd returns [ASTCmd n] : 'destroy' e= expression ( COMMA e= expression )* ;
    public final ASTCmd destroyCmd() throws RecognitionException {
        ASTCmd n = null;

        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // Cmd.g:168:1: ( 'destroy' e= expression ( COMMA e= expression )* )
            // Cmd.g:169:6: 'destroy' e= expression ( COMMA e= expression )*
            {
            match(input,47,FOLLOW_47_in_destroyCmd424); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_destroyCmd428);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // Cmd.g:170:16: ( COMMA e= expression )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==COMMA) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // Cmd.g:170:18: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_destroyCmd450); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_destroyCmd454);
            	    e=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       exprList.add(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               n = new ASTDestroyCmd(exprList); 
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
    // $ANTLR end "destroyCmd"


    // $ANTLR start "insertCmd"
    // Cmd.g:180:1: insertCmd returns [ASTCmd n] : 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT ;
    public final ASTCmd insertCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // Cmd.g:182:1: ( 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT )
            // Cmd.g:183:5: 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT
            {
            match(input,48,FOLLOW_48_in_insertCmd493); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_insertCmd495); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_insertCmd504);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_insertCmd508); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_insertCmd516);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // Cmd.g:185:42: ( COMMA e= expression )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==COMMA) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // Cmd.g:185:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_insertCmd522); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_insertCmd526);
            	    e=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       exprList.add(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_insertCmd538); if (state.failed) return n;
            match(input,49,FOLLOW_49_in_insertCmd540); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_insertCmd544); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTInsertCmd(exprList, id); 
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
    // $ANTLR end "insertCmd"


    // $ANTLR start "deleteCmd"
    // Cmd.g:196:1: deleteCmd returns [ASTCmd n] : 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT ;
    public final ASTCmd deleteCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // Cmd.g:198:1: ( 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT )
            // Cmd.g:199:5: 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT
            {
            match(input,50,FOLLOW_50_in_deleteCmd579); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_deleteCmd581); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_deleteCmd589);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_deleteCmd593); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_deleteCmd601);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // Cmd.g:201:42: ( COMMA e= expression )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==COMMA) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // Cmd.g:201:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_deleteCmd607); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_deleteCmd611);
            	    e=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       exprList.add(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_deleteCmd622); if (state.failed) return n;
            match(input,51,FOLLOW_51_in_deleteCmd624); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_deleteCmd628); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTDeleteCmd(exprList, id); 
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
    // $ANTLR end "deleteCmd"


    // $ANTLR start "setCmd"
    // Cmd.g:215:1: setCmd returns [ASTCmd n] : 'set' e1= expression COLON_EQUAL e2= expression ;
    public final ASTCmd setCmd() throws RecognitionException {
        ASTCmd n = null;

        ASTExpression e1 = null;

        ASTExpression e2 = null;


        try {
            // Cmd.g:216:1: ( 'set' e1= expression COLON_EQUAL e2= expression )
            // Cmd.g:217:5: 'set' e1= expression COLON_EQUAL e2= expression
            {
            match(input,52,FOLLOW_52_in_setCmd658); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_setCmd662);
            e1=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_setCmd664); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_setCmd668);
            e2=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTSetCmd(e1, e2); 
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
    // $ANTLR end "setCmd"


    // $ANTLR start "opEnterCmd"
    // Cmd.g:229:1: opEnterCmd returns [ASTCmd n] : 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN ;
    public final ASTCmd opEnterCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        ASTExpression e = null;


        ASTOpEnterCmd nOpEnter = null;
        try {
            // Cmd.g:231:1: ( 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )
            // Cmd.g:232:5: 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
            {
            match(input,53,FOLLOW_53_in_opEnterCmd702); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_opEnterCmd711);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_opEnterCmd715); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               nOpEnter = new ASTOpEnterCmd(e, id); n = nOpEnter;
            }
            match(input,LPAREN,FOLLOW_LPAREN_in_opEnterCmd723); if (state.failed) return n;
            // Cmd.g:235:5: (e= expression ( COMMA e= expression )* )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( ((LA8_0>=IDENT && LA8_0<=LPAREN)||LA8_0==INT||(LA8_0>=PLUS && LA8_0<=MINUS)||(LA8_0>=REAL && LA8_0<=HASH)||LA8_0==55||LA8_0==83||(LA8_0>=85 && LA8_0<=89)||(LA8_0>=93 && LA8_0<=104)) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // Cmd.g:235:7: e= expression ( COMMA e= expression )*
                    {
                    pushFollow(FOLLOW_expression_in_opEnterCmd733);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       nOpEnter.addArg(e); 
                    }
                    // Cmd.g:235:47: ( COMMA e= expression )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==COMMA) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // Cmd.g:235:49: COMMA e= expression
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_opEnterCmd739); if (state.failed) return n;
                    	    pushFollow(FOLLOW_expression_in_opEnterCmd743);
                    	    e=expression();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       nOpEnter.addArg(e); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_opEnterCmd757); if (state.failed) return n;

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
    // $ANTLR end "opEnterCmd"


    // $ANTLR start "opExitCmd"
    // Cmd.g:245:1: opExitCmd returns [ASTCmd n] : 'opexit' ( ( expression )=>e= expression | ) ;
    public final ASTCmd opExitCmd() throws RecognitionException {
        ASTCmd n = null;

        ASTExpression e = null;


        try {
            // Cmd.g:246:1: ( 'opexit' ( ( expression )=>e= expression | ) )
            // Cmd.g:247:5: 'opexit' ( ( expression )=>e= expression | )
            {
            match(input,54,FOLLOW_54_in_opExitCmd781); if (state.failed) return n;
            // Cmd.g:247:14: ( ( expression )=>e= expression | )
            int alt9=2;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // Cmd.g:247:15: ( expression )=>e= expression
                    {
                    pushFollow(FOLLOW_expression_in_opExitCmd791);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 2 :
                    // Cmd.g:247:45: 
                    {
                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTOpExitCmd(e); 
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
    // $ANTLR end "opExitCmd"


    // $ANTLR start "letCmd"
    // Cmd.g:256:1: letCmd returns [ASTCmd n] : 'let' name= IDENT ( COLON t= type )? EQUAL e= expression ;
    public final ASTCmd letCmd() throws RecognitionException {
        ASTCmd n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // Cmd.g:257:1: ( 'let' name= IDENT ( COLON t= type )? EQUAL e= expression )
            // Cmd.g:258:5: 'let' name= IDENT ( COLON t= type )? EQUAL e= expression
            {
            match(input,55,FOLLOW_55_in_letCmd824); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_letCmd828); if (state.failed) return n;
            // Cmd.g:258:22: ( COLON t= type )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==COLON) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // Cmd.g:258:24: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_letCmd832); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_letCmd836);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            match(input,EQUAL,FOLLOW_EQUAL_in_letCmd841); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_letCmd845);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTLetCmd(name, t, e); 
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
    // $ANTLR end "letCmd"


    // $ANTLR start "model"
    // Cmd.g:278:1: model returns [ASTModel n] : 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF ;
    public final ASTModel model() throws RecognitionException {
        ASTModel n = null;

        Token modelName=null;
        ASTEnumTypeDefinition e = null;

        ASTAssociation a = null;

        ASTConstraintDefinition cons = null;

        ASTPrePost ppc = null;


        try {
            // Cmd.g:279:1: ( 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF )
            // Cmd.g:280:5: 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF
            {
            match(input,56,FOLLOW_56_in_model881); if (state.failed) return n;
            modelName=(Token)match(input,IDENT,FOLLOW_IDENT_in_model885); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTModel(modelName); 
            }
            // Cmd.g:281:5: (e= enumTypeDefinition )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==58) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // Cmd.g:281:7: e= enumTypeDefinition
            	    {
            	    pushFollow(FOLLOW_enumTypeDefinition_in_model897);
            	    e=enumTypeDefinition();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addEnumTypeDef(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            // Cmd.g:282:5: ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )*
            loop13:
            do {
                int alt13=4;
                switch ( input.LA(1) ) {
                case 59:
                case 60:
                case 64:
                case 65:
                    {
                    alt13=1;
                    }
                    break;
                case IDENT:
                case 66:
                case 67:
                    {
                    alt13=2;
                    }
                    break;
                case 57:
                    {
                    alt13=3;
                    }
                    break;

                }

                switch (alt13) {
            	case 1 :
            	    // Cmd.g:282:9: ( generalClassDefinition[$n] )
            	    {
            	    // Cmd.g:282:9: ( generalClassDefinition[$n] )
            	    // Cmd.g:282:11: generalClassDefinition[$n]
            	    {
            	    pushFollow(FOLLOW_generalClassDefinition_in_model914);
            	    generalClassDefinition(n);

            	    state._fsp--;
            	    if (state.failed) return n;

            	    }


            	    }
            	    break;
            	case 2 :
            	    // Cmd.g:283:9: (a= associationDefinition )
            	    {
            	    // Cmd.g:283:9: (a= associationDefinition )
            	    // Cmd.g:283:11: a= associationDefinition
            	    {
            	    pushFollow(FOLLOW_associationDefinition_in_model931);
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
            	    // Cmd.g:284:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    {
            	    // Cmd.g:284:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    // Cmd.g:284:11: 'constraints' (cons= invariant | ppc= prePost )*
            	    {
            	    match(input,57,FOLLOW_57_in_model947); if (state.failed) return n;
            	    // Cmd.g:285:11: (cons= invariant | ppc= prePost )*
            	    loop12:
            	    do {
            	        int alt12=3;
            	        int LA12_0 = input.LA(1);

            	        if ( (LA12_0==72) ) {
            	            int LA12_2 = input.LA(2);

            	            if ( (LA12_2==IDENT) ) {
            	                int LA12_3 = input.LA(3);

            	                if ( (LA12_3==COLON_COLON) ) {
            	                    alt12=2;
            	                }
            	                else if ( (LA12_3==EOF||LA12_3==COLON||LA12_3==IDENT||LA12_3==COMMA||LA12_3==57||(LA12_3>=59 && LA12_3<=60)||(LA12_3>=64 && LA12_3<=67)||(LA12_3>=72 && LA12_3<=74)) ) {
            	                    alt12=1;
            	                }


            	            }


            	        }


            	        switch (alt12) {
            	    	case 1 :
            	    	    // Cmd.g:285:15: cons= invariant
            	    	    {
            	    	    pushFollow(FOLLOW_invariant_in_model965);
            	    	    cons=invariant();

            	    	    state._fsp--;
            	    	    if (state.failed) return n;
            	    	    if ( state.backtracking==0 ) {
            	    	       n.addConstraint(cons); 
            	    	    }

            	    	    }
            	    	    break;
            	    	case 2 :
            	    	    // Cmd.g:286:15: ppc= prePost
            	    	    {
            	    	    pushFollow(FOLLOW_prePost_in_model986);
            	    	    ppc=prePost();

            	    	    state._fsp--;
            	    	    if (state.failed) return n;
            	    	    if ( state.backtracking==0 ) {
            	    	       n.addPrePost(ppc); 
            	    	    }

            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop12;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

            match(input,EOF,FOLLOW_EOF_in_model1027); if (state.failed) return n;

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
    // Cmd.g:297:1: enumTypeDefinition returns [ASTEnumTypeDefinition n] : 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )? ;
    public final ASTEnumTypeDefinition enumTypeDefinition() throws RecognitionException {
        ASTEnumTypeDefinition n = null;

        Token name=null;
        List idListRes = null;


        try {
            // Cmd.g:298:1: ( 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )? )
            // Cmd.g:299:5: 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )?
            {
            match(input,58,FOLLOW_58_in_enumTypeDefinition1054); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_enumTypeDefinition1058); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_enumTypeDefinition1060); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_enumTypeDefinition1064);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,RBRACE,FOLLOW_RBRACE_in_enumTypeDefinition1066); if (state.failed) return n;
            // Cmd.g:299:54: ( SEMI )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==SEMI) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // Cmd.g:299:56: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_enumTypeDefinition1070); if (state.failed) return n;

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
    // Cmd.g:308:1: generalClassDefinition[ASTModel n] : ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) ) ;
    public final void generalClassDefinition(ASTModel n) throws RecognitionException {
        ASTClass c = null;

        ASTAssociationClass ac = null;


         
          boolean isAbstract = false;

        try {
            // Cmd.g:312:1: ( ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) ) )
            // Cmd.g:313:5: ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) )
            {
            // Cmd.g:313:5: ( 'abstract' )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==59) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // Cmd.g:313:7: 'abstract'
                    {
                    match(input,59,FOLLOW_59_in_generalClassDefinition1109); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       isAbstract = true; 
                    }

                    }
                    break;

            }

            // Cmd.g:314:5: ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==60) ) {
                alt16=1;
            }
            else if ( ((LA16_0>=64 && LA16_0<=65)) ) {
                alt16=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // Cmd.g:314:7: (c= classDefinition[isAbstract] )
                    {
                    // Cmd.g:314:7: (c= classDefinition[isAbstract] )
                    // Cmd.g:314:9: c= classDefinition[isAbstract]
                    {
                    pushFollow(FOLLOW_classDefinition_in_generalClassDefinition1127);
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
                    // Cmd.g:315:7: (ac= associationClassDefinition[isAbstract] )
                    {
                    // Cmd.g:315:7: (ac= associationClassDefinition[isAbstract] )
                    // Cmd.g:315:9: ac= associationClassDefinition[isAbstract]
                    {
                    pushFollow(FOLLOW_associationClassDefinition_in_generalClassDefinition1147);
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
    // Cmd.g:332:1: classDefinition[boolean isAbstract] returns [ASTClass n] : 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end' ;
    public final ASTClass classDefinition(boolean isAbstract) throws RecognitionException {
        ASTClass n = null;

        Token name=null;
        List idListRes = null;

        ASTAttribute a = null;

        ASTOperation op = null;

        ASTInvariantClause inv = null;


         List idList; 
        try {
            // Cmd.g:334:1: ( 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end' )
            // Cmd.g:335:5: 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end'
            {
            match(input,60,FOLLOW_60_in_classDefinition1186); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_classDefinition1190); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTClass(name, isAbstract); 
            }
            // Cmd.g:336:5: ( LESS idListRes= idList )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==LESS) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // Cmd.g:336:7: LESS idListRes= idList
                    {
                    match(input,LESS,FOLLOW_LESS_in_classDefinition1200); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_classDefinition1204);
                    idListRes=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addSuperClasses(idListRes); 
                    }

                    }
                    break;

            }

            // Cmd.g:337:5: ( 'attributes' (a= attributeDefinition )* )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==61) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // Cmd.g:337:7: 'attributes' (a= attributeDefinition )*
                    {
                    match(input,61,FOLLOW_61_in_classDefinition1217); if (state.failed) return n;
                    // Cmd.g:338:7: (a= attributeDefinition )*
                    loop18:
                    do {
                        int alt18=2;
                        int LA18_0 = input.LA(1);

                        if ( (LA18_0==IDENT) ) {
                            alt18=1;
                        }


                        switch (alt18) {
                    	case 1 :
                    	    // Cmd.g:338:9: a= attributeDefinition
                    	    {
                    	    pushFollow(FOLLOW_attributeDefinition_in_classDefinition1230);
                    	    a=attributeDefinition();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addAttribute(a); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop18;
                        }
                    } while (true);


                    }
                    break;

            }

            // Cmd.g:340:5: ( 'operations' (op= operationDefinition )* )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==62) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // Cmd.g:340:7: 'operations' (op= operationDefinition )*
                    {
                    match(input,62,FOLLOW_62_in_classDefinition1251); if (state.failed) return n;
                    // Cmd.g:341:7: (op= operationDefinition )*
                    loop20:
                    do {
                        int alt20=2;
                        int LA20_0 = input.LA(1);

                        if ( (LA20_0==IDENT) ) {
                            alt20=1;
                        }


                        switch (alt20) {
                    	case 1 :
                    	    // Cmd.g:341:9: op= operationDefinition
                    	    {
                    	    pushFollow(FOLLOW_operationDefinition_in_classDefinition1264);
                    	    op=operationDefinition();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addOperation(op); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop20;
                        }
                    } while (true);


                    }
                    break;

            }

            // Cmd.g:343:5: ( 'constraints' (inv= invariantClause )* )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==57) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // Cmd.g:343:7: 'constraints' (inv= invariantClause )*
                    {
                    match(input,57,FOLLOW_57_in_classDefinition1285); if (state.failed) return n;
                    // Cmd.g:344:7: (inv= invariantClause )*
                    loop22:
                    do {
                        int alt22=2;
                        int LA22_0 = input.LA(1);

                        if ( ((LA22_0>=73 && LA22_0<=74)) ) {
                            alt22=1;
                        }


                        switch (alt22) {
                    	case 1 :
                    	    // Cmd.g:345:9: inv= invariantClause
                    	    {
                    	    pushFollow(FOLLOW_invariantClause_in_classDefinition1305);
                    	    inv=invariantClause();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addInvariantClause(inv); 
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

            match(input,63,FOLLOW_63_in_classDefinition1329); if (state.failed) return n;

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
    // Cmd.g:366:1: associationClassDefinition[boolean isAbstract] returns [ASTAssociationClass n] : classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end' ;
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
            // Cmd.g:368:1: (classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end' )
            // Cmd.g:369:5: classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end'
            {
            classKW=(Token)input.LT(1);
            if ( (input.LA(1)>=64 && input.LA(1)<=65) ) {
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
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationClassDefinition1388); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociationClass(name, isAbstract); 
            }
            // Cmd.g:378:5: ( LESS idListRes= idList )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==LESS) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // Cmd.g:378:7: LESS idListRes= idList
                    {
                    match(input,LESS,FOLLOW_LESS_in_associationClassDefinition1398); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_associationClassDefinition1402);
                    idListRes=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addSuperClasses(idListRes); 
                    }

                    }
                    break;

            }

            match(input,46,FOLLOW_46_in_associationClassDefinition1413); if (state.failed) return n;
            pushFollow(FOLLOW_associationEnd_in_associationClassDefinition1421);
            ae=associationEnd();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addEnd(ae); 
            }
            // Cmd.g:381:5: (ae= associationEnd )+
            int cnt25=0;
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==IDENT) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // Cmd.g:381:7: ae= associationEnd
            	    {
            	    pushFollow(FOLLOW_associationEnd_in_associationClassDefinition1433);
            	    ae=associationEnd();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addEnd(ae); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt25 >= 1 ) break loop25;
            	    if (state.backtracking>0) {state.failed=true; return n;}
                        EarlyExitException eee =
                            new EarlyExitException(25, input);
                        throw eee;
                }
                cnt25++;
            } while (true);

            // Cmd.g:382:5: ( 'attributes' (a= attributeDefinition )* )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==61) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // Cmd.g:382:7: 'attributes' (a= attributeDefinition )*
                    {
                    match(input,61,FOLLOW_61_in_associationClassDefinition1446); if (state.failed) return n;
                    // Cmd.g:383:7: (a= attributeDefinition )*
                    loop26:
                    do {
                        int alt26=2;
                        int LA26_0 = input.LA(1);

                        if ( (LA26_0==IDENT) ) {
                            alt26=1;
                        }


                        switch (alt26) {
                    	case 1 :
                    	    // Cmd.g:383:9: a= attributeDefinition
                    	    {
                    	    pushFollow(FOLLOW_attributeDefinition_in_associationClassDefinition1459);
                    	    a=attributeDefinition();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addAttribute(a); 
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

            // Cmd.g:385:5: ( 'operations' (op= operationDefinition )* )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==62) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // Cmd.g:385:7: 'operations' (op= operationDefinition )*
                    {
                    match(input,62,FOLLOW_62_in_associationClassDefinition1480); if (state.failed) return n;
                    // Cmd.g:386:7: (op= operationDefinition )*
                    loop28:
                    do {
                        int alt28=2;
                        int LA28_0 = input.LA(1);

                        if ( (LA28_0==IDENT) ) {
                            alt28=1;
                        }


                        switch (alt28) {
                    	case 1 :
                    	    // Cmd.g:386:9: op= operationDefinition
                    	    {
                    	    pushFollow(FOLLOW_operationDefinition_in_associationClassDefinition1493);
                    	    op=operationDefinition();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addOperation(op); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop28;
                        }
                    } while (true);


                    }
                    break;

            }

            // Cmd.g:388:5: ( 'constraints' (inv= invariantClause )* )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==57) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // Cmd.g:388:7: 'constraints' (inv= invariantClause )*
                    {
                    match(input,57,FOLLOW_57_in_associationClassDefinition1514); if (state.failed) return n;
                    // Cmd.g:389:7: (inv= invariantClause )*
                    loop30:
                    do {
                        int alt30=2;
                        int LA30_0 = input.LA(1);

                        if ( ((LA30_0>=73 && LA30_0<=74)) ) {
                            alt30=1;
                        }


                        switch (alt30) {
                    	case 1 :
                    	    // Cmd.g:390:9: inv= invariantClause
                    	    {
                    	    pushFollow(FOLLOW_invariantClause_in_associationClassDefinition1534);
                    	    inv=invariantClause();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addInvariantClause(inv); 
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

            // Cmd.g:393:5: ( ( 'aggregation' | 'composition' ) )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( ((LA32_0>=66 && LA32_0<=67)) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // Cmd.g:393:7: ( 'aggregation' | 'composition' )
                    {
                    if ( state.backtracking==0 ) {
                       t = input.LT(1); 
                    }
                    if ( (input.LA(1)>=66 && input.LA(1)<=67) ) {
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

            match(input,63,FOLLOW_63_in_associationClassDefinition1597); if (state.failed) return n;

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
    // Cmd.g:404:1: attributeDefinition returns [ASTAttribute n] : name= IDENT COLON t= type ( SEMI )? ;
    public final ASTAttribute attributeDefinition() throws RecognitionException {
        ASTAttribute n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Cmd.g:405:1: (name= IDENT COLON t= type ( SEMI )? )
            // Cmd.g:406:5: name= IDENT COLON t= type ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_attributeDefinition1626); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_attributeDefinition1628); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_attributeDefinition1632);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            // Cmd.g:406:29: ( SEMI )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==SEMI) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // Cmd.g:406:31: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_attributeDefinition1636); if (state.failed) return n;

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
    // Cmd.g:415:1: operationDefinition returns [ASTOperation n] : name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression | EQUAL 'script' body= SCRIPTBODY )? (ppc= prePostClause )* ( SEMI )? ;
    public final ASTOperation operationDefinition() throws RecognitionException {
        ASTOperation n = null;

        Token name=null;
        Token body=null;
        List pl = null;

        ASTType t = null;

        ASTExpression e = null;

        ASTPrePostClause ppc = null;


        try {
            // Cmd.g:416:1: (name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression | EQUAL 'script' body= SCRIPTBODY )? (ppc= prePostClause )* ( SEMI )? )
            // Cmd.g:417:5: name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression | EQUAL 'script' body= SCRIPTBODY )? (ppc= prePostClause )* ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationDefinition1674); if (state.failed) return n;
            pushFollow(FOLLOW_paramList_in_operationDefinition1682);
            pl=paramList();

            state._fsp--;
            if (state.failed) return n;
            // Cmd.g:419:5: ( COLON t= type )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==COLON) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // Cmd.g:419:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_operationDefinition1690); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_operationDefinition1694);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTOperation(name, pl, t); 
            }
            // Cmd.g:422:5: ( EQUAL e= expression | EQUAL 'script' body= SCRIPTBODY )?
            int alt35=3;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==EQUAL) ) {
                int LA35_1 = input.LA(2);

                if ( (LA35_1==68) ) {
                    alt35=2;
                }
                else if ( ((LA35_1>=IDENT && LA35_1<=LPAREN)||LA35_1==INT||(LA35_1>=PLUS && LA35_1<=MINUS)||(LA35_1>=REAL && LA35_1<=HASH)||LA35_1==55||LA35_1==83||(LA35_1>=85 && LA35_1<=89)||(LA35_1>=93 && LA35_1<=104)) ) {
                    alt35=1;
                }
            }
            switch (alt35) {
                case 1 :
                    // Cmd.g:423:6: EQUAL e= expression
                    {
                    match(input,EQUAL,FOLLOW_EQUAL_in_operationDefinition1722); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_operationDefinition1726);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setExpression(e); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:426:6: EQUAL 'script' body= SCRIPTBODY
                    {
                    match(input,EQUAL,FOLLOW_EQUAL_in_operationDefinition1748); if (state.failed) return n;
                    match(input,68,FOLLOW_68_in_operationDefinition1750); if (state.failed) return n;
                    body=(Token)match(input,SCRIPTBODY,FOLLOW_SCRIPTBODY_in_operationDefinition1754); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setScript(body); 
                    }

                    }
                    break;

            }

            // Cmd.g:430:5: (ppc= prePostClause )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( ((LA36_0>=75 && LA36_0<=76)) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // Cmd.g:430:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_operationDefinition1785);
            	    ppc=prePostClause();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addPrePostClause(ppc); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop36;
                }
            } while (true);

            // Cmd.g:431:5: ( SEMI )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==SEMI) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // Cmd.g:431:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_operationDefinition1798); if (state.failed) return n;

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
    // Cmd.g:441:1: associationDefinition returns [ASTAssociation n] : ( keyAssociation | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end' ;
    public final ASTAssociation associationDefinition() throws RecognitionException {
        ASTAssociation n = null;

        Token name=null;
        ASTAssociationEnd ae = null;


         Token t = null; 
        try {
            // Cmd.g:443:1: ( ( keyAssociation | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end' )
            // Cmd.g:444:5: ( keyAssociation | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end'
            {
            if ( state.backtracking==0 ) {
               t = input.LT(1); 
            }
            // Cmd.g:445:5: ( keyAssociation | 'aggregation' | 'composition' )
            int alt38=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt38=1;
                }
                break;
            case 66:
                {
                alt38=2;
                }
                break;
            case 67:
                {
                alt38=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 38, 0, input);

                throw nvae;
            }

            switch (alt38) {
                case 1 :
                    // Cmd.g:445:7: keyAssociation
                    {
                    pushFollow(FOLLOW_keyAssociation_in_associationDefinition1836);
                    keyAssociation();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 2 :
                    // Cmd.g:445:24: 'aggregation'
                    {
                    match(input,66,FOLLOW_66_in_associationDefinition1840); if (state.failed) return n;

                    }
                    break;
                case 3 :
                    // Cmd.g:445:40: 'composition'
                    {
                    match(input,67,FOLLOW_67_in_associationDefinition1844); if (state.failed) return n;

                    }
                    break;

            }

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationDefinition1859); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociation(t, name); 
            }
            match(input,46,FOLLOW_46_in_associationDefinition1867); if (state.failed) return n;
            pushFollow(FOLLOW_associationEnd_in_associationDefinition1875);
            ae=associationEnd();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addEnd(ae); 
            }
            // Cmd.g:450:5: (ae= associationEnd )+
            int cnt39=0;
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==IDENT) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // Cmd.g:450:7: ae= associationEnd
            	    {
            	    pushFollow(FOLLOW_associationEnd_in_associationDefinition1887);
            	    ae=associationEnd();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addEnd(ae); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt39 >= 1 ) break loop39;
            	    if (state.backtracking>0) {state.failed=true; return n;}
                        EarlyExitException eee =
                            new EarlyExitException(39, input);
                        throw eee;
                }
                cnt39++;
            } while (true);

            match(input,63,FOLLOW_63_in_associationDefinition1898); if (state.failed) return n;

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
    // Cmd.g:459:1: associationEnd returns [ASTAssociationEnd n] : name= IDENT LBRACK m= multiplicity RBRACK ( keyRole rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )? ;
    public final ASTAssociationEnd associationEnd() throws RecognitionException {
        ASTAssociationEnd n = null;

        Token name=null;
        Token rn=null;
        Token sr=null;
        Token rd=null;
        ASTMultiplicity m = null;


        try {
            // Cmd.g:460:1: (name= IDENT LBRACK m= multiplicity RBRACK ( keyRole rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )? )
            // Cmd.g:461:5: name= IDENT LBRACK m= multiplicity RBRACK ( keyRole rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd1924); if (state.failed) return n;
            match(input,LBRACK,FOLLOW_LBRACK_in_associationEnd1926); if (state.failed) return n;
            pushFollow(FOLLOW_multiplicity_in_associationEnd1930);
            m=multiplicity();

            state._fsp--;
            if (state.failed) return n;
            match(input,RBRACK,FOLLOW_RBRACK_in_associationEnd1932); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociationEnd(name, m); 
            }
            // Cmd.g:462:5: ( keyRole rn= IDENT )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==IDENT) ) {
                int LA40_1 = input.LA(2);

                if ( (LA40_1==IDENT) ) {
                    int LA40_3 = input.LA(3);

                    if ( ((input.LT(1).getText().equals("role"))) ) {
                        alt40=1;
                    }
                }
            }
            switch (alt40) {
                case 1 :
                    // Cmd.g:462:7: keyRole rn= IDENT
                    {
                    pushFollow(FOLLOW_keyRole_in_associationEnd1943);
                    keyRole();

                    state._fsp--;
                    if (state.failed) return n;
                    rn=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd1947); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setRolename(rn); 
                    }

                    }
                    break;

            }

            // Cmd.g:463:5: ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )*
            loop41:
            do {
                int alt41=5;
                switch ( input.LA(1) ) {
                case IDENT:
                    {
                    int LA41_2 = input.LA(2);

                    if ( (LA41_2==SEMI||LA41_2==IDENT||LA41_2==57||(LA41_2>=61 && LA41_2<=63)||(LA41_2>=66 && LA41_2<=67)||(LA41_2>=69 && LA41_2<=71)) ) {
                        alt41=3;
                    }


                    }
                    break;
                case 69:
                    {
                    alt41=1;
                    }
                    break;
                case 70:
                    {
                    alt41=2;
                    }
                    break;
                case 71:
                    {
                    alt41=4;
                    }
                    break;

                }

                switch (alt41) {
            	case 1 :
            	    // Cmd.g:464:9: 'ordered'
            	    {
            	    match(input,69,FOLLOW_69_in_associationEnd1968); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.setOrdered(); 
            	    }

            	    }
            	    break;
            	case 2 :
            	    // Cmd.g:465:9: 'subsets' sr= IDENT
            	    {
            	    match(input,70,FOLLOW_70_in_associationEnd1980); if (state.failed) return n;
            	    sr=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd1984); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addSubsetsRolename(sr); 
            	    }

            	    }
            	    break;
            	case 3 :
            	    // Cmd.g:466:9: keyUnion
            	    {
            	    pushFollow(FOLLOW_keyUnion_in_associationEnd1996);
            	    keyUnion();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.setUnion(true); 
            	    }

            	    }
            	    break;
            	case 4 :
            	    // Cmd.g:467:9: 'redefines' rd= IDENT
            	    {
            	    match(input,71,FOLLOW_71_in_associationEnd2008); if (state.failed) return n;
            	    rd=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd2012); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addRedefinesRolename(rd); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop41;
                }
            } while (true);

            // Cmd.g:469:5: ( SEMI )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==SEMI) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // Cmd.g:469:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_associationEnd2029); if (state.failed) return n;

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
    // Cmd.g:483:1: multiplicity returns [ASTMultiplicity n] : mr= multiplicityRange ( COMMA mr= multiplicityRange )* ;
    public final ASTMultiplicity multiplicity() throws RecognitionException {
        ASTMultiplicity n = null;

        ASTMultiplicityRange mr = null;


        try {
            // Cmd.g:484:1: (mr= multiplicityRange ( COMMA mr= multiplicityRange )* )
            // Cmd.g:485:5: mr= multiplicityRange ( COMMA mr= multiplicityRange )*
            {
            if ( state.backtracking==0 ) {
               
              	Token t = input.LT(1); // remember start position of expression
              	n = new ASTMultiplicity(t);
                  
            }
            pushFollow(FOLLOW_multiplicityRange_in_multiplicity2064);
            mr=multiplicityRange();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addRange(mr); 
            }
            // Cmd.g:490:5: ( COMMA mr= multiplicityRange )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==COMMA) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // Cmd.g:490:7: COMMA mr= multiplicityRange
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_multiplicity2074); if (state.failed) return n;
            	    pushFollow(FOLLOW_multiplicityRange_in_multiplicity2078);
            	    mr=multiplicityRange();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addRange(mr); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop43;
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
    // Cmd.g:493:1: multiplicityRange returns [ASTMultiplicityRange n] : ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? ;
    public final ASTMultiplicityRange multiplicityRange() throws RecognitionException {
        ASTMultiplicityRange n = null;

        int ms1 = 0;

        int ms2 = 0;


        try {
            // Cmd.g:494:1: (ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? )
            // Cmd.g:495:5: ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )?
            {
            pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange2107);
            ms1=multiplicitySpec();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTMultiplicityRange(ms1); 
            }
            // Cmd.g:496:5: ( DOTDOT ms2= multiplicitySpec )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==DOTDOT) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // Cmd.g:496:7: DOTDOT ms2= multiplicitySpec
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_multiplicityRange2117); if (state.failed) return n;
                    pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange2121);
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
    // Cmd.g:499:1: multiplicitySpec returns [int m] : (i= INT | STAR );
    public final int multiplicitySpec() throws RecognitionException {
        int m = 0;

        Token i=null;

         m = -1; 
        try {
            // Cmd.g:501:1: (i= INT | STAR )
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==INT) ) {
                alt45=1;
            }
            else if ( (LA45_0==STAR) ) {
                alt45=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return m;}
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;
            }
            switch (alt45) {
                case 1 :
                    // Cmd.g:502:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_multiplicitySpec2155); if (state.failed) return m;
                    if ( state.backtracking==0 ) {
                       m = Integer.parseInt((i!=null?i.getText():null)); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:503:7: STAR
                    {
                    match(input,STAR,FOLLOW_STAR_in_multiplicitySpec2165); if (state.failed) return m;
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
    // Cmd.g:524:1: invariant returns [ASTConstraintDefinition n] : 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )* ;
    public final ASTConstraintDefinition invariant() throws RecognitionException {
        ASTConstraintDefinition n = null;

        Token v=null;
        ASTSimpleType t = null;

        ASTInvariantClause inv = null;


        try {
            // Cmd.g:525:1: ( 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )* )
            // Cmd.g:526:5: 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )*
            {
            if ( state.backtracking==0 ) {
               n = new ASTConstraintDefinition(); 
            }
            match(input,72,FOLLOW_72_in_invariant2206); if (state.failed) return n;
            // Cmd.g:528:5: (v= IDENT ( ',' v= IDENT )* COLON )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==IDENT) ) {
                int LA47_1 = input.LA(2);

                if ( (LA47_1==COLON||LA47_1==COMMA) ) {
                    alt47=1;
                }
            }
            switch (alt47) {
                case 1 :
                    // Cmd.g:528:7: v= IDENT ( ',' v= IDENT )* COLON
                    {
                    v=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariant2216); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addVarName(v); 
                    }
                    // Cmd.g:529:8: ( ',' v= IDENT )*
                    loop46:
                    do {
                        int alt46=2;
                        int LA46_0 = input.LA(1);

                        if ( (LA46_0==COMMA) ) {
                            alt46=1;
                        }


                        switch (alt46) {
                    	case 1 :
                    	    // Cmd.g:529:9: ',' v= IDENT
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_invariant2229); if (state.failed) return n;
                    	    v=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariant2233); if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addVarName(v); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop46;
                        }
                    } while (true);

                    match(input,COLON,FOLLOW_COLON_in_invariant2241); if (state.failed) return n;

                    }
                    break;

            }

            pushFollow(FOLLOW_simpleType_in_invariant2253);
            t=simpleType();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setType(t); 
            }
            // Cmd.g:531:5: (inv= invariantClause )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( ((LA48_0>=73 && LA48_0<=74)) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // Cmd.g:531:7: inv= invariantClause
            	    {
            	    pushFollow(FOLLOW_invariantClause_in_invariant2265);
            	    inv=invariantClause();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addInvariantClause(inv); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop48;
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
    // Cmd.g:538:1: invariantClause returns [ASTInvariantClause n] : ( 'inv' (name= IDENT )? COLON e= expression | 'existential' 'inv' (name= IDENT )? COLON e= expression );
    public final ASTInvariantClause invariantClause() throws RecognitionException {
        ASTInvariantClause n = null;

        Token name=null;
        ASTExpression e = null;


        try {
            // Cmd.g:539:1: ( 'inv' (name= IDENT )? COLON e= expression | 'existential' 'inv' (name= IDENT )? COLON e= expression )
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==73) ) {
                alt51=1;
            }
            else if ( (LA51_0==74) ) {
                alt51=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;
            }
            switch (alt51) {
                case 1 :
                    // Cmd.g:540:7: 'inv' (name= IDENT )? COLON e= expression
                    {
                    match(input,73,FOLLOW_73_in_invariantClause2296); if (state.failed) return n;
                    // Cmd.g:540:13: (name= IDENT )?
                    int alt49=2;
                    int LA49_0 = input.LA(1);

                    if ( (LA49_0==IDENT) ) {
                        alt49=1;
                    }
                    switch (alt49) {
                        case 1 :
                            // Cmd.g:540:15: name= IDENT
                            {
                            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantClause2302); if (state.failed) return n;

                            }
                            break;

                    }

                    match(input,COLON,FOLLOW_COLON_in_invariantClause2307); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_invariantClause2311);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTInvariantClause(name, e); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:541:7: 'existential' 'inv' (name= IDENT )? COLON e= expression
                    {
                    match(input,74,FOLLOW_74_in_invariantClause2321); if (state.failed) return n;
                    match(input,73,FOLLOW_73_in_invariantClause2323); if (state.failed) return n;
                    // Cmd.g:541:27: (name= IDENT )?
                    int alt50=2;
                    int LA50_0 = input.LA(1);

                    if ( (LA50_0==IDENT) ) {
                        alt50=1;
                    }
                    switch (alt50) {
                        case 1 :
                            // Cmd.g:541:29: name= IDENT
                            {
                            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantClause2329); if (state.failed) return n;

                            }
                            break;

                    }

                    match(input,COLON,FOLLOW_COLON_in_invariantClause2334); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_invariantClause2338);
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
    // Cmd.g:552:1: prePost returns [ASTPrePost n] : 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ ;
    public final ASTPrePost prePost() throws RecognitionException {
        ASTPrePost n = null;

        Token classname=null;
        Token opname=null;
        List pl = null;

        ASTType rt = null;

        ASTPrePostClause ppc = null;


        try {
            // Cmd.g:553:1: ( 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ )
            // Cmd.g:554:5: 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+
            {
            match(input,72,FOLLOW_72_in_prePost2364); if (state.failed) return n;
            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost2368); if (state.failed) return n;
            match(input,COLON_COLON,FOLLOW_COLON_COLON_in_prePost2370); if (state.failed) return n;
            opname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost2374); if (state.failed) return n;
            pushFollow(FOLLOW_paramList_in_prePost2378);
            pl=paramList();

            state._fsp--;
            if (state.failed) return n;
            // Cmd.g:554:69: ( COLON rt= type )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==COLON) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // Cmd.g:554:71: COLON rt= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_prePost2382); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_prePost2386);
                    rt=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTPrePost(classname, opname, pl, rt); 
            }
            // Cmd.g:556:5: (ppc= prePostClause )+
            int cnt53=0;
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( ((LA53_0>=75 && LA53_0<=76)) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // Cmd.g:556:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_prePost2405);
            	    ppc=prePostClause();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addPrePostClause(ppc); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt53 >= 1 ) break loop53;
            	    if (state.backtracking>0) {state.failed=true; return n;}
                        EarlyExitException eee =
                            new EarlyExitException(53, input);
                        throw eee;
                }
                cnt53++;
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
    // Cmd.g:563:1: prePostClause returns [ASTPrePostClause n] : ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression ;
    public final ASTPrePostClause prePostClause() throws RecognitionException {
        ASTPrePostClause n = null;

        Token name=null;
        ASTExpression e = null;


         Token t = null; 
        try {
            // Cmd.g:565:1: ( ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression )
            // Cmd.g:566:5: ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression
            {
            if ( state.backtracking==0 ) {
               t = input.LT(1); 
            }
            if ( (input.LA(1)>=75 && input.LA(1)<=76) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // Cmd.g:567:25: (name= IDENT )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==IDENT) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // Cmd.g:567:27: name= IDENT
                    {
                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePostClause2459); if (state.failed) return n;

                    }
                    break;

            }

            match(input,COLON,FOLLOW_COLON_in_prePostClause2464); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_prePostClause2468);
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
    // Cmd.g:571:1: keyUnion : {...}? IDENT ;
    public final void keyUnion() throws RecognitionException {
        try {
            // Cmd.g:571:9: ({...}? IDENT )
            // Cmd.g:572:3: {...}? IDENT
            {
            if ( !((input.LT(1).getText().equals("union"))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "keyUnion", "input.LT(1).getText().equals(\"union\")");
            }
            match(input,IDENT,FOLLOW_IDENT_in_keyUnion2490); if (state.failed) return ;

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
    // Cmd.g:574:1: keyAssociation : {...}? IDENT ;
    public final void keyAssociation() throws RecognitionException {
        try {
            // Cmd.g:574:15: ({...}? IDENT )
            // Cmd.g:575:3: {...}? IDENT
            {
            if ( !((input.LT(1).getText().equals("association"))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "keyAssociation", "input.LT(1).getText().equals(\"association\")");
            }
            match(input,IDENT,FOLLOW_IDENT_in_keyAssociation2504); if (state.failed) return ;

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
    // Cmd.g:577:1: keyRole : {...}? IDENT ;
    public final void keyRole() throws RecognitionException {
        try {
            // Cmd.g:577:8: ({...}? IDENT )
            // Cmd.g:578:3: {...}? IDENT
            {
            if ( !((input.LT(1).getText().equals("role"))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "keyRole", "input.LT(1).getText().equals(\"role\")");
            }
            match(input,IDENT,FOLLOW_IDENT_in_keyRole2518); if (state.failed) return ;

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
    // Cmd.g:608:1: expressionOnly returns [ASTExpression n] : nExp= expression EOF ;
    public final ASTExpression expressionOnly() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExp = null;


        try {
            // Cmd.g:609:1: (nExp= expression EOF )
            // Cmd.g:610:5: nExp= expression EOF
            {
            pushFollow(FOLLOW_expression_in_expressionOnly2548);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_expressionOnly2550); if (state.failed) return n;
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
    // Cmd.g:617:1: expression returns [ASTExpression n] : ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression ;
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
            // Cmd.g:623:1: ( ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression )
            // Cmd.g:624:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of expression */ 
            }
            // Cmd.g:625:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==55) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // Cmd.g:626:7: 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in'
            	    {
            	    match(input,55,FOLLOW_55_in_expression2598); if (state.failed) return n;
            	    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_expression2602); if (state.failed) return n;
            	    // Cmd.g:626:24: ( COLON t= type )?
            	    int alt55=2;
            	    int LA55_0 = input.LA(1);

            	    if ( (LA55_0==COLON) ) {
            	        alt55=1;
            	    }
            	    switch (alt55) {
            	        case 1 :
            	            // Cmd.g:626:26: COLON t= type
            	            {
            	            match(input,COLON,FOLLOW_COLON_in_expression2606); if (state.failed) return n;
            	            pushFollow(FOLLOW_type_in_expression2610);
            	            t=type();

            	            state._fsp--;
            	            if (state.failed) return n;

            	            }
            	            break;

            	    }

            	    match(input,EQUAL,FOLLOW_EQUAL_in_expression2615); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_expression2619);
            	    e1=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    match(input,77,FOLLOW_77_in_expression2621); if (state.failed) return n;
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
            	    break loop56;
                }
            } while (true);

            pushFollow(FOLLOW_conditionalImpliesExpression_in_expression2646);
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
    // Cmd.g:654:1: paramList returns [List paramList] : LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN ;
    public final List paramList() throws RecognitionException {
        List paramList = null;

        ASTVariableDeclaration v = null;


         paramList = new ArrayList(); 
        try {
            // Cmd.g:656:1: ( LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN )
            // Cmd.g:657:5: LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN
            {
            match(input,LPAREN,FOLLOW_LPAREN_in_paramList2679); if (state.failed) return paramList;
            // Cmd.g:658:5: (v= variableDeclaration ( COMMA v= variableDeclaration )* )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==IDENT) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // Cmd.g:659:7: v= variableDeclaration ( COMMA v= variableDeclaration )*
                    {
                    pushFollow(FOLLOW_variableDeclaration_in_paramList2696);
                    v=variableDeclaration();

                    state._fsp--;
                    if (state.failed) return paramList;
                    if ( state.backtracking==0 ) {
                       paramList.add(v); 
                    }
                    // Cmd.g:660:7: ( COMMA v= variableDeclaration )*
                    loop57:
                    do {
                        int alt57=2;
                        int LA57_0 = input.LA(1);

                        if ( (LA57_0==COMMA) ) {
                            alt57=1;
                        }


                        switch (alt57) {
                    	case 1 :
                    	    // Cmd.g:660:9: COMMA v= variableDeclaration
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_paramList2708); if (state.failed) return paramList;
                    	    pushFollow(FOLLOW_variableDeclaration_in_paramList2712);
                    	    v=variableDeclaration();

                    	    state._fsp--;
                    	    if (state.failed) return paramList;
                    	    if ( state.backtracking==0 ) {
                    	       paramList.add(v); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop57;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_paramList2732); if (state.failed) return paramList;

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
    // Cmd.g:668:1: idList returns [List idList] : id0= IDENT ( COMMA idn= IDENT )* ;
    public final List idList() throws RecognitionException {
        List idList = null;

        Token id0=null;
        Token idn=null;

         idList = new ArrayList(); 
        try {
            // Cmd.g:670:1: (id0= IDENT ( COMMA idn= IDENT )* )
            // Cmd.g:671:5: id0= IDENT ( COMMA idn= IDENT )*
            {
            id0=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList2761); if (state.failed) return idList;
            if ( state.backtracking==0 ) {
               idList.add(id0); 
            }
            // Cmd.g:672:5: ( COMMA idn= IDENT )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==COMMA) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // Cmd.g:672:7: COMMA idn= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_idList2771); if (state.failed) return idList;
            	    idn=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList2775); if (state.failed) return idList;
            	    if ( state.backtracking==0 ) {
            	       idList.add(idn); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop59;
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
    // Cmd.g:680:1: variableDeclaration returns [ASTVariableDeclaration n] : name= IDENT COLON t= type ;
    public final ASTVariableDeclaration variableDeclaration() throws RecognitionException {
        ASTVariableDeclaration n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Cmd.g:681:1: (name= IDENT COLON t= type )
            // Cmd.g:682:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableDeclaration2806); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableDeclaration2808); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableDeclaration2812);
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
    // Cmd.g:690:1: conditionalImpliesExpression returns [ASTExpression n] : nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* ;
    public final ASTExpression conditionalImpliesExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndOrExp = null;

        ASTExpression n1 = null;


        try {
            // Cmd.g:691:1: (nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* )
            // Cmd.g:692:5: nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )*
            {
            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2848);
            nCndOrExp=conditionalOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndOrExp;
            }
            // Cmd.g:693:5: (op= 'implies' n1= conditionalOrExpression )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==78) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // Cmd.g:693:7: op= 'implies' n1= conditionalOrExpression
            	    {
            	    op=(Token)match(input,78,FOLLOW_78_in_conditionalImpliesExpression2861); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2865);
            	    n1=conditionalOrExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop60;
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
    // Cmd.g:702:1: conditionalOrExpression returns [ASTExpression n] : nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* ;
    public final ASTExpression conditionalOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndXorExp = null;

        ASTExpression n1 = null;


        try {
            // Cmd.g:703:1: (nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* )
            // Cmd.g:704:5: nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )*
            {
            pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2910);
            nCndXorExp=conditionalXOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndXorExp;
            }
            // Cmd.g:705:5: (op= 'or' n1= conditionalXOrExpression )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==79) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // Cmd.g:705:7: op= 'or' n1= conditionalXOrExpression
            	    {
            	    op=(Token)match(input,79,FOLLOW_79_in_conditionalOrExpression2923); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2927);
            	    n1=conditionalXOrExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop61;
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
    // Cmd.g:714:1: conditionalXOrExpression returns [ASTExpression n] : nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* ;
    public final ASTExpression conditionalXOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndAndExp = null;

        ASTExpression n1 = null;


        try {
            // Cmd.g:715:1: (nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* )
            // Cmd.g:716:5: nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )*
            {
            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2971);
            nCndAndExp=conditionalAndExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndAndExp;
            }
            // Cmd.g:717:5: (op= 'xor' n1= conditionalAndExpression )*
            loop62:
            do {
                int alt62=2;
                int LA62_0 = input.LA(1);

                if ( (LA62_0==80) ) {
                    alt62=1;
                }


                switch (alt62) {
            	case 1 :
            	    // Cmd.g:717:7: op= 'xor' n1= conditionalAndExpression
            	    {
            	    op=(Token)match(input,80,FOLLOW_80_in_conditionalXOrExpression2984); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2988);
            	    n1=conditionalAndExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop62;
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
    // Cmd.g:726:1: conditionalAndExpression returns [ASTExpression n] : nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* ;
    public final ASTExpression conditionalAndExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nEqExp = null;

        ASTExpression n1 = null;


        try {
            // Cmd.g:727:1: (nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* )
            // Cmd.g:728:5: nEqExp= equalityExpression (op= 'and' n1= equalityExpression )*
            {
            pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression3032);
            nEqExp=equalityExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nEqExp;
            }
            // Cmd.g:729:5: (op= 'and' n1= equalityExpression )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==81) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // Cmd.g:729:7: op= 'and' n1= equalityExpression
            	    {
            	    op=(Token)match(input,81,FOLLOW_81_in_conditionalAndExpression3045); if (state.failed) return n;
            	    pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression3049);
            	    n1=equalityExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
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
        return n;
    }
    // $ANTLR end "conditionalAndExpression"


    // $ANTLR start "equalityExpression"
    // Cmd.g:738:1: equalityExpression returns [ASTExpression n] : nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* ;
    public final ASTExpression equalityExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nRelExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Cmd.g:740:1: (nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* )
            // Cmd.g:741:5: nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            {
            pushFollow(FOLLOW_relationalExpression_in_equalityExpression3097);
            nRelExp=relationalExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nRelExp;
            }
            // Cmd.g:742:5: ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==EQUAL||LA64_0==NOT_EQUAL) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // Cmd.g:742:7: ( EQUAL | NOT_EQUAL ) n1= relationalExpression
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

            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression3126);
            	    n1=relationalExpression();

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
    // $ANTLR end "equalityExpression"


    // $ANTLR start "relationalExpression"
    // Cmd.g:752:1: relationalExpression returns [ASTExpression n] : nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* ;
    public final ASTExpression relationalExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nAddiExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Cmd.g:754:1: (nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* )
            // Cmd.g:755:5: nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            {
            pushFollow(FOLLOW_additiveExpression_in_relationalExpression3175);
            nAddiExp=additiveExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nAddiExp;
            }
            // Cmd.g:756:5: ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( (LA65_0==LESS||(LA65_0>=GREATER && LA65_0<=GREATER_EQUAL)) ) {
                    alt65=1;
                }


                switch (alt65) {
            	case 1 :
            	    // Cmd.g:756:7: ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression
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

            	    pushFollow(FOLLOW_additiveExpression_in_relationalExpression3211);
            	    n1=additiveExpression();

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
    // $ANTLR end "relationalExpression"


    // $ANTLR start "additiveExpression"
    // Cmd.g:766:1: additiveExpression returns [ASTExpression n] : nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* ;
    public final ASTExpression additiveExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nMulExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Cmd.g:768:1: (nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* )
            // Cmd.g:769:5: nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            {
            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3261);
            nMulExp=multiplicativeExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nMulExp;
            }
            // Cmd.g:770:5: ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( ((LA66_0>=PLUS && LA66_0<=MINUS)) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // Cmd.g:770:7: ( PLUS | MINUS ) n1= multiplicativeExpression
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

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3289);
            	    n1=multiplicativeExpression();

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
    // $ANTLR end "additiveExpression"


    // $ANTLR start "multiplicativeExpression"
    // Cmd.g:781:1: multiplicativeExpression returns [ASTExpression n] : nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* ;
    public final ASTExpression multiplicativeExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Cmd.g:783:1: (nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* )
            // Cmd.g:784:5: nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            {
            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3339);
            nUnExp=unaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nUnExp;
            }
            // Cmd.g:785:5: ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==STAR||LA67_0==SLASH||LA67_0==82) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // Cmd.g:785:7: ( STAR | SLASH | 'div' ) n1= unaryExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       op = input.LT(1); 
            	    }
            	    if ( input.LA(1)==STAR||input.LA(1)==SLASH||input.LA(1)==82 ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3371);
            	    n1=unaryExpression();

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
    // $ANTLR end "multiplicativeExpression"


    // $ANTLR start "unaryExpression"
    // Cmd.g:797:1: unaryExpression returns [ASTExpression n] : ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression );
    public final ASTExpression unaryExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression nPosExp = null;


         Token op = null; 
        try {
            // Cmd.g:799:1: ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression )
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( ((LA68_0>=PLUS && LA68_0<=MINUS)||LA68_0==83) ) {
                alt68=1;
            }
            else if ( ((LA68_0>=IDENT && LA68_0<=LPAREN)||LA68_0==INT||(LA68_0>=REAL && LA68_0<=HASH)||(LA68_0>=85 && LA68_0<=89)||(LA68_0>=93 && LA68_0<=104)) ) {
                alt68=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 68, 0, input);

                throw nvae;
            }
            switch (alt68) {
                case 1 :
                    // Cmd.g:800:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    {
                    // Cmd.g:800:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    // Cmd.g:800:9: ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression
                    {
                    if ( state.backtracking==0 ) {
                       op = input.LT(1); 
                    }
                    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS)||input.LA(1)==83 ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression3457);
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
                    // Cmd.g:804:7: nPosExp= postfixExpression
                    {
                    pushFollow(FOLLOW_postfixExpression_in_unaryExpression3477);
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
    // Cmd.g:812:1: postfixExpression returns [ASTExpression n] : nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* ;
    public final ASTExpression postfixExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nPrimExp = null;

        ASTExpression nPc = null;


         boolean arrow = false; 
        try {
            // Cmd.g:814:1: (nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* )
            // Cmd.g:815:5: nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            {
            pushFollow(FOLLOW_primaryExpression_in_postfixExpression3510);
            nPrimExp=primaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nPrimExp; 
            }
            // Cmd.g:816:5: ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            loop70:
            do {
                int alt70=2;
                int LA70_0 = input.LA(1);

                if ( ((LA70_0>=ARROW && LA70_0<=DOT)) ) {
                    alt70=1;
                }


                switch (alt70) {
            	case 1 :
            	    // Cmd.g:817:6: ( ARROW | DOT ) nPc= propertyCall[$n, arrow]
            	    {
            	    // Cmd.g:817:6: ( ARROW | DOT )
            	    int alt69=2;
            	    int LA69_0 = input.LA(1);

            	    if ( (LA69_0==ARROW) ) {
            	        alt69=1;
            	    }
            	    else if ( (LA69_0==DOT) ) {
            	        alt69=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 69, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt69) {
            	        case 1 :
            	            // Cmd.g:817:8: ARROW
            	            {
            	            match(input,ARROW,FOLLOW_ARROW_in_postfixExpression3528); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = true; 
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // Cmd.g:817:34: DOT
            	            {
            	            match(input,DOT,FOLLOW_DOT_in_postfixExpression3534); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = false; 
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_propertyCall_in_postfixExpression3545);
            	    nPc=propertyCall(n, arrow);

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = nPc; 
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
    // $ANTLR end "postfixExpression"


    // $ANTLR start "primaryExpression"
    // Cmd.g:833:1: primaryExpression returns [ASTExpression n] : (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? );
    public final ASTExpression primaryExpression() throws RecognitionException {
        ASTExpression n = null;

        Token id1=null;
        ASTExpression nLit = null;

        ASTExpression nPc = null;

        ASTExpression nExp = null;

        ASTExpression nIfExp = null;


        try {
            // Cmd.g:834:1: (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? )
            int alt73=5;
            switch ( input.LA(1) ) {
            case INT:
            case REAL:
            case STRING:
            case HASH:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
                {
                alt73=1;
                }
                break;
            case IDENT:
                {
                switch ( input.LA(2) ) {
                case COLON_COLON:
                    {
                    alt73=1;
                    }
                    break;
                case EOF:
                case SEMI:
                case COLON_EQUAL:
                case IDENT:
                case LPAREN:
                case RPAREN:
                case COMMA:
                case EQUAL:
                case RBRACE:
                case LESS:
                case LBRACK:
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
                case 44:
                case 45:
                case 47:
                case 48:
                case 50:
                case 52:
                case 53:
                case 54:
                case 55:
                case 57:
                case 59:
                case 60:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 72:
                case 73:
                case 74:
                case 75:
                case 76:
                case 77:
                case 78:
                case 79:
                case 80:
                case 81:
                case 82:
                case 90:
                case 91:
                case 92:
                    {
                    alt73=2;
                    }
                    break;
                case DOT:
                    {
                    int LA73_6 = input.LA(3);

                    if ( (LA73_6==84) ) {
                        alt73=5;
                    }
                    else if ( (LA73_6==IDENT||(LA73_6>=85 && LA73_6<=88)) ) {
                        alt73=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 73, 6, input);

                        throw nvae;
                    }
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 73, 2, input);

                    throw nvae;
                }

                }
                break;
            case 85:
            case 86:
            case 87:
            case 88:
                {
                alt73=2;
                }
                break;
            case LPAREN:
                {
                alt73=3;
                }
                break;
            case 89:
                {
                alt73=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 73, 0, input);

                throw nvae;
            }

            switch (alt73) {
                case 1 :
                    // Cmd.g:835:7: nLit= literal
                    {
                    pushFollow(FOLLOW_literal_in_primaryExpression3585);
                    nLit=literal();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nLit; 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:836:7: nPc= propertyCall[null, false]
                    {
                    pushFollow(FOLLOW_propertyCall_in_primaryExpression3597);
                    nPc=propertyCall(null, false);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nPc; 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:837:7: LPAREN nExp= expression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression3608); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_primaryExpression3612);
                    nExp=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression3614); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExp; 
                    }

                    }
                    break;
                case 4 :
                    // Cmd.g:838:7: nIfExp= ifExpression
                    {
                    pushFollow(FOLLOW_ifExpression_in_primaryExpression3626);
                    nIfExp=ifExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nIfExp; 
                    }

                    }
                    break;
                case 5 :
                    // Cmd.g:840:7: id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )?
                    {
                    id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_primaryExpression3643); if (state.failed) return n;
                    match(input,DOT,FOLLOW_DOT_in_primaryExpression3645); if (state.failed) return n;
                    match(input,84,FOLLOW_84_in_primaryExpression3647); if (state.failed) return n;
                    // Cmd.g:840:36: ( LPAREN RPAREN )?
                    int alt71=2;
                    int LA71_0 = input.LA(1);

                    if ( (LA71_0==LPAREN) ) {
                        alt71=1;
                    }
                    switch (alt71) {
                        case 1 :
                            // Cmd.g:840:38: LPAREN RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression3651); if (state.failed) return n;
                            match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression3653); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTAllInstancesExpression(id1); 
                    }
                    // Cmd.g:842:7: ( AT 'pre' )?
                    int alt72=2;
                    int LA72_0 = input.LA(1);

                    if ( (LA72_0==AT) ) {
                        alt72=1;
                    }
                    switch (alt72) {
                        case 1 :
                            // Cmd.g:842:9: AT 'pre'
                            {
                            match(input,AT,FOLLOW_AT_in_primaryExpression3674); if (state.failed) return n;
                            match(input,75,FOLLOW_75_in_primaryExpression3676); if (state.failed) return n;
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
    // Cmd.g:855:1: propertyCall[ASTExpression source, boolean followsArrow] returns [ASTExpression n] : ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] );
    public final ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExpQuery = null;

        ASTExpression nExpIterate = null;

        ASTOperationExpression nExpOperation = null;

        ASTTypeArgExpression nExpType = null;


        try {
            // Cmd.g:856:1: ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] )
            int alt74=4;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA74_1 = input.LA(2);

                if ( ((( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )&&( input.LA(2) == LPAREN ))) ) {
                    alt74=1;
                }
                else if ( (true) ) {
                    alt74=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 74, 1, input);

                    throw nvae;
                }
                }
                break;
            case 85:
                {
                alt74=2;
                }
                break;
            case 86:
            case 87:
            case 88:
                {
                alt74=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 74, 0, input);

                throw nvae;
            }

            switch (alt74) {
                case 1 :
                    // Cmd.g:860:7: {...}?{...}?nExpQuery= queryExpression[source]
                    {
                    if ( !(( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) ");
                    }
                    if ( !(( input.LA(2) == LPAREN )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " input.LA(2) == LPAREN ");
                    }
                    pushFollow(FOLLOW_queryExpression_in_propertyCall3749);
                    nExpQuery=queryExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpQuery; 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:863:7: nExpIterate= iterateExpression[source]
                    {
                    pushFollow(FOLLOW_iterateExpression_in_propertyCall3762);
                    nExpIterate=iterateExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpIterate; 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:864:7: nExpOperation= operationExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_operationExpression_in_propertyCall3775);
                    nExpOperation=operationExpression(source, followsArrow);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpOperation; 
                    }

                    }
                    break;
                case 4 :
                    // Cmd.g:865:7: nExpType= typeExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_typeExpression_in_propertyCall3788);
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
    // Cmd.g:874:1: queryExpression[ASTExpression range] returns [ASTExpression n] : op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN ;
    public final ASTExpression queryExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTElemVarsDeclaration decls = null;

        ASTExpression nExp = null;


        ASTElemVarsDeclaration decl = new ASTElemVarsDeclaration(); 
        try {
            // Cmd.g:875:69: (op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN )
            // Cmd.g:876:5: op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN
            {
            op=(Token)match(input,IDENT,FOLLOW_IDENT_in_queryExpression3823); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_queryExpression3830); if (state.failed) return n;
            // Cmd.g:878:5: (decls= elemVarsDeclaration BAR )?
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==IDENT) ) {
                int LA75_1 = input.LA(2);

                if ( (LA75_1==COLON||LA75_1==COMMA||LA75_1==BAR) ) {
                    alt75=1;
                }
            }
            switch (alt75) {
                case 1 :
                    // Cmd.g:878:7: decls= elemVarsDeclaration BAR
                    {
                    pushFollow(FOLLOW_elemVarsDeclaration_in_queryExpression3841);
                    decls=elemVarsDeclaration();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      decl = decls;
                    }
                    match(input,BAR,FOLLOW_BAR_in_queryExpression3845); if (state.failed) return n;

                    }
                    break;

            }

            pushFollow(FOLLOW_expression_in_queryExpression3856);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_queryExpression3862); if (state.failed) return n;
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
    // Cmd.g:892:1: iterateExpression[ASTExpression range] returns [ASTExpression n] : i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN ;
    public final ASTExpression iterateExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTElemVarsDeclaration decls = null;

        ASTVariableInitialization init = null;

        ASTExpression nExp = null;


        try {
            // Cmd.g:892:65: (i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN )
            // Cmd.g:893:5: i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN
            {
            i=(Token)match(input,85,FOLLOW_85_in_iterateExpression3894); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_iterateExpression3900); if (state.failed) return n;
            pushFollow(FOLLOW_elemVarsDeclaration_in_iterateExpression3908);
            decls=elemVarsDeclaration();

            state._fsp--;
            if (state.failed) return n;
            match(input,SEMI,FOLLOW_SEMI_in_iterateExpression3910); if (state.failed) return n;
            pushFollow(FOLLOW_variableInitialization_in_iterateExpression3918);
            init=variableInitialization();

            state._fsp--;
            if (state.failed) return n;
            match(input,BAR,FOLLOW_BAR_in_iterateExpression3920); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_iterateExpression3928);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_iterateExpression3934); if (state.failed) return n;
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
    // Cmd.g:914:1: operationExpression[ASTExpression source, boolean followsArrow] returns [ASTOperationExpression n] : name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? ;
    public final ASTOperationExpression operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTOperationExpression n = null;

        Token name=null;
        Token rolename=null;
        ASTExpression e = null;


        try {
            // Cmd.g:916:1: (name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? )
            // Cmd.g:917:5: name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression3978); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTOperationExpression(name, source, followsArrow); 
            }
            // Cmd.g:920:5: ( LBRACK rolename= IDENT RBRACK )?
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==LBRACK) ) {
                alt76=1;
            }
            switch (alt76) {
                case 1 :
                    // Cmd.g:920:7: LBRACK rolename= IDENT RBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_operationExpression3994); if (state.failed) return n;
                    rolename=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression3998); if (state.failed) return n;
                    match(input,RBRACK,FOLLOW_RBRACK_in_operationExpression4000); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setExplicitRolename(rolename); 
                    }

                    }
                    break;

            }

            // Cmd.g:922:5: ( AT 'pre' )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==AT) ) {
                alt77=1;
            }
            switch (alt77) {
                case 1 :
                    // Cmd.g:922:7: AT 'pre'
                    {
                    match(input,AT,FOLLOW_AT_in_operationExpression4013); if (state.failed) return n;
                    match(input,75,FOLLOW_75_in_operationExpression4015); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setIsPre(); 
                    }

                    }
                    break;

            }

            // Cmd.g:923:5: ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==LPAREN) ) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // Cmd.g:924:7: LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_operationExpression4036); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.hasParentheses(); 
                    }
                    // Cmd.g:925:7: (e= expression ( COMMA e= expression )* )?
                    int alt79=2;
                    int LA79_0 = input.LA(1);

                    if ( ((LA79_0>=IDENT && LA79_0<=LPAREN)||LA79_0==INT||(LA79_0>=PLUS && LA79_0<=MINUS)||(LA79_0>=REAL && LA79_0<=HASH)||LA79_0==55||LA79_0==83||(LA79_0>=85 && LA79_0<=89)||(LA79_0>=93 && LA79_0<=104)) ) {
                        alt79=1;
                    }
                    switch (alt79) {
                        case 1 :
                            // Cmd.g:926:7: e= expression ( COMMA e= expression )*
                            {
                            pushFollow(FOLLOW_expression_in_operationExpression4057);
                            e=expression();

                            state._fsp--;
                            if (state.failed) return n;
                            if ( state.backtracking==0 ) {
                               n.addArg(e); 
                            }
                            // Cmd.g:927:7: ( COMMA e= expression )*
                            loop78:
                            do {
                                int alt78=2;
                                int LA78_0 = input.LA(1);

                                if ( (LA78_0==COMMA) ) {
                                    alt78=1;
                                }


                                switch (alt78) {
                            	case 1 :
                            	    // Cmd.g:927:9: COMMA e= expression
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_operationExpression4069); if (state.failed) return n;
                            	    pushFollow(FOLLOW_expression_in_operationExpression4073);
                            	    e=expression();

                            	    state._fsp--;
                            	    if (state.failed) return n;
                            	    if ( state.backtracking==0 ) {
                            	       n.addArg(e); 
                            	    }

                            	    }
                            	    break;

                            	default :
                            	    break loop78;
                                }
                            } while (true);


                            }
                            break;

                    }

                    match(input,RPAREN,FOLLOW_RPAREN_in_operationExpression4093); if (state.failed) return n;

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
    // Cmd.g:939:1: typeExpression[ASTExpression source, boolean followsArrow] returns [ASTTypeArgExpression n] : ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN ;
    public final ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTTypeArgExpression n = null;

        ASTType t = null;


         Token opToken = null; 
        try {
            // Cmd.g:942:1: ( ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN )
            // Cmd.g:943:2: ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN
            {
            if ( state.backtracking==0 ) {
               opToken = input.LT(1); 
            }
            if ( (input.LA(1)>=86 && input.LA(1)<=88) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_typeExpression4152); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_typeExpression4156);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_typeExpression4158); if (state.failed) return n;
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
    // Cmd.g:954:1: elemVarsDeclaration returns [ASTElemVarsDeclaration n] : idListRes= idList ( COLON t= type )? ;
    public final ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException {
        ASTElemVarsDeclaration n = null;

        List idListRes = null;

        ASTType t = null;


         List idList; 
        try {
            // Cmd.g:956:1: (idListRes= idList ( COLON t= type )? )
            // Cmd.g:957:5: idListRes= idList ( COLON t= type )?
            {
            pushFollow(FOLLOW_idList_in_elemVarsDeclaration4197);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            // Cmd.g:958:5: ( COLON t= type )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==COLON) ) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // Cmd.g:958:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_elemVarsDeclaration4205); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_elemVarsDeclaration4209);
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
    // Cmd.g:967:1: variableInitialization returns [ASTVariableInitialization n] : name= IDENT COLON t= type EQUAL e= expression ;
    public final ASTVariableInitialization variableInitialization() throws RecognitionException {
        ASTVariableInitialization n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // Cmd.g:968:1: (name= IDENT COLON t= type EQUAL e= expression )
            // Cmd.g:969:5: name= IDENT COLON t= type EQUAL e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableInitialization4244); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableInitialization4246); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableInitialization4250);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EQUAL,FOLLOW_EQUAL_in_variableInitialization4252); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_variableInitialization4256);
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
    // Cmd.g:978:1: ifExpression returns [ASTExpression n] : i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' ;
    public final ASTExpression ifExpression() throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTExpression cond = null;

        ASTExpression t = null;

        ASTExpression e = null;


        try {
            // Cmd.g:979:1: (i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' )
            // Cmd.g:980:5: i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif'
            {
            i=(Token)match(input,89,FOLLOW_89_in_ifExpression4288); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4292);
            cond=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,90,FOLLOW_90_in_ifExpression4294); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4298);
            t=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,91,FOLLOW_91_in_ifExpression4300); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4304);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,92,FOLLOW_92_in_ifExpression4306); if (state.failed) return n;
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
    // Cmd.g:1000:1: literal returns [ASTExpression n] : (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral );
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
            // Cmd.g:1001:1: (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral )
            int alt82=12;
            switch ( input.LA(1) ) {
            case 93:
                {
                alt82=1;
                }
                break;
            case 94:
                {
                alt82=2;
                }
                break;
            case INT:
                {
                alt82=3;
                }
                break;
            case REAL:
                {
                alt82=4;
                }
                break;
            case STRING:
                {
                alt82=5;
                }
                break;
            case HASH:
                {
                alt82=6;
                }
                break;
            case IDENT:
                {
                alt82=7;
                }
                break;
            case 95:
            case 96:
            case 97:
            case 98:
                {
                alt82=8;
                }
                break;
            case 99:
                {
                alt82=9;
                }
                break;
            case 100:
            case 101:
            case 102:
                {
                alt82=10;
                }
                break;
            case 103:
                {
                alt82=11;
                }
                break;
            case 104:
                {
                alt82=12;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 82, 0, input);

                throw nvae;
            }

            switch (alt82) {
                case 1 :
                    // Cmd.g:1002:7: t= 'true'
                    {
                    t=(Token)match(input,93,FOLLOW_93_in_literal4345); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(true); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:1003:7: f= 'false'
                    {
                    f=(Token)match(input,94,FOLLOW_94_in_literal4359); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(false); 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:1004:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_literal4372); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTIntegerLiteral(i); 
                    }

                    }
                    break;
                case 4 :
                    // Cmd.g:1005:7: r= REAL
                    {
                    r=(Token)match(input,REAL,FOLLOW_REAL_in_literal4387); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTRealLiteral(r); 
                    }

                    }
                    break;
                case 5 :
                    // Cmd.g:1006:7: s= STRING
                    {
                    s=(Token)match(input,STRING,FOLLOW_STRING_in_literal4401); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTStringLiteral(s); 
                    }

                    }
                    break;
                case 6 :
                    // Cmd.g:1007:7: HASH enumLit= IDENT
                    {
                    match(input,HASH,FOLLOW_HASH_in_literal4411); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal4415); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumLit);  reportWarning("the usage of #enumerationLiteral is deprecated and will not be supported in the future, use 'Enumeration::Literal' instead");
                    }

                    }
                    break;
                case 7 :
                    // Cmd.g:1008:7: enumName= IDENT '::' enumLit= IDENT
                    {
                    enumName=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal4427); if (state.failed) return n;
                    match(input,COLON_COLON,FOLLOW_COLON_COLON_in_literal4429); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal4433); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumName, enumLit); 
                    }

                    }
                    break;
                case 8 :
                    // Cmd.g:1009:7: nColIt= collectionLiteral
                    {
                    pushFollow(FOLLOW_collectionLiteral_in_literal4445);
                    nColIt=collectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nColIt; 
                    }

                    }
                    break;
                case 9 :
                    // Cmd.g:1010:7: nEColIt= emptyCollectionLiteral
                    {
                    pushFollow(FOLLOW_emptyCollectionLiteral_in_literal4457);
                    nEColIt=emptyCollectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nEColIt; 
                    }

                    }
                    break;
                case 10 :
                    // Cmd.g:1011:7: nUndLit= undefinedLiteral
                    {
                    pushFollow(FOLLOW_undefinedLiteral_in_literal4469);
                    nUndLit=undefinedLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nUndLit; 
                    }

                    }
                    break;
                case 11 :
                    // Cmd.g:1012:7: nTupleLit= tupleLiteral
                    {
                    pushFollow(FOLLOW_tupleLiteral_in_literal4481);
                    nTupleLit=tupleLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nTupleLit; 
                    }

                    }
                    break;
                case 12 :
                    // Cmd.g:1013:7: nDateLit= dateLiteral
                    {
                    pushFollow(FOLLOW_dateLiteral_in_literal4493);
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
    // Cmd.g:1021:1: collectionLiteral returns [ASTCollectionLiteral n] : ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE ;
    public final ASTCollectionLiteral collectionLiteral() throws RecognitionException {
        ASTCollectionLiteral n = null;

        ASTCollectionItem ci = null;


         Token op = null; 
        try {
            // Cmd.g:1023:1: ( ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE )
            // Cmd.g:1024:5: ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE
            {
            if ( state.backtracking==0 ) {
               op = input.LT(1); 
            }
            if ( (input.LA(1)>=95 && input.LA(1)<=98) ) {
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
            match(input,LBRACE,FOLLOW_LBRACE_in_collectionLiteral4560); if (state.failed) return n;
            // Cmd.g:1028:5: (ci= collectionItem ( COMMA ci= collectionItem )* )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( ((LA84_0>=IDENT && LA84_0<=LPAREN)||LA84_0==INT||(LA84_0>=PLUS && LA84_0<=MINUS)||(LA84_0>=REAL && LA84_0<=HASH)||LA84_0==55||LA84_0==83||(LA84_0>=85 && LA84_0<=89)||(LA84_0>=93 && LA84_0<=104)) ) {
                alt84=1;
            }
            switch (alt84) {
                case 1 :
                    // Cmd.g:1029:7: ci= collectionItem ( COMMA ci= collectionItem )*
                    {
                    pushFollow(FOLLOW_collectionItem_in_collectionLiteral4577);
                    ci=collectionItem();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addItem(ci); 
                    }
                    // Cmd.g:1030:7: ( COMMA ci= collectionItem )*
                    loop83:
                    do {
                        int alt83=2;
                        int LA83_0 = input.LA(1);

                        if ( (LA83_0==COMMA) ) {
                            alt83=1;
                        }


                        switch (alt83) {
                    	case 1 :
                    	    // Cmd.g:1030:9: COMMA ci= collectionItem
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_collectionLiteral4590); if (state.failed) return n;
                    	    pushFollow(FOLLOW_collectionItem_in_collectionLiteral4594);
                    	    ci=collectionItem();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addItem(ci); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop83;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RBRACE,FOLLOW_RBRACE_in_collectionLiteral4613); if (state.failed) return n;

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
    // Cmd.g:1039:1: collectionItem returns [ASTCollectionItem n] : e= expression ( DOTDOT e= expression )? ;
    public final ASTCollectionItem collectionItem() throws RecognitionException {
        ASTCollectionItem n = null;

        ASTExpression e = null;


         n = new ASTCollectionItem(); 
        try {
            // Cmd.g:1041:1: (e= expression ( DOTDOT e= expression )? )
            // Cmd.g:1042:5: e= expression ( DOTDOT e= expression )?
            {
            pushFollow(FOLLOW_expression_in_collectionItem4642);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setFirst(e); 
            }
            // Cmd.g:1043:5: ( DOTDOT e= expression )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==DOTDOT) ) {
                alt85=1;
            }
            switch (alt85) {
                case 1 :
                    // Cmd.g:1043:7: DOTDOT e= expression
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_collectionItem4653); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_collectionItem4657);
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
    // Cmd.g:1053:1: emptyCollectionLiteral returns [ASTEmptyCollectionLiteral n] : 'oclEmpty' LPAREN t= collectionType RPAREN ;
    public final ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException {
        ASTEmptyCollectionLiteral n = null;

        ASTCollectionType t = null;


        try {
            // Cmd.g:1054:1: ( 'oclEmpty' LPAREN t= collectionType RPAREN )
            // Cmd.g:1055:5: 'oclEmpty' LPAREN t= collectionType RPAREN
            {
            match(input,99,FOLLOW_99_in_emptyCollectionLiteral4686); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_emptyCollectionLiteral4688); if (state.failed) return n;
            pushFollow(FOLLOW_collectionType_in_emptyCollectionLiteral4692);
            t=collectionType();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_emptyCollectionLiteral4694); if (state.failed) return n;
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
    // Cmd.g:1066:1: undefinedLiteral returns [ASTUndefinedLiteral n] : ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' );
    public final ASTUndefinedLiteral undefinedLiteral() throws RecognitionException {
        ASTUndefinedLiteral n = null;

        ASTType t = null;


        try {
            // Cmd.g:1067:1: ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' )
            int alt86=3;
            switch ( input.LA(1) ) {
            case 100:
                {
                alt86=1;
                }
                break;
            case 101:
                {
                alt86=2;
                }
                break;
            case 102:
                {
                alt86=3;
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
                    // Cmd.g:1068:5: 'oclUndefined' LPAREN t= type RPAREN
                    {
                    match(input,100,FOLLOW_100_in_undefinedLiteral4724); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_undefinedLiteral4726); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_undefinedLiteral4730);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_undefinedLiteral4732); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(t); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:1071:5: 'Undefined'
                    {
                    match(input,101,FOLLOW_101_in_undefinedLiteral4746); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(); 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:1074:5: 'null'
                    {
                    match(input,102,FOLLOW_102_in_undefinedLiteral4760); if (state.failed) return n;
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
    // Cmd.g:1083:1: tupleLiteral returns [ASTTupleLiteral n] : 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE ;
    public final ASTTupleLiteral tupleLiteral() throws RecognitionException {
        ASTTupleLiteral n = null;

        ASTTupleItem ti = null;


         List tiList = new ArrayList(); 
        try {
            // Cmd.g:1085:1: ( 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE )
            // Cmd.g:1086:5: 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE
            {
            match(input,103,FOLLOW_103_in_tupleLiteral4794); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_tupleLiteral4800); if (state.failed) return n;
            pushFollow(FOLLOW_tupleItem_in_tupleLiteral4808);
            ti=tupleItem();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tiList.add(ti); 
            }
            // Cmd.g:1089:5: ( COMMA ti= tupleItem )*
            loop87:
            do {
                int alt87=2;
                int LA87_0 = input.LA(1);

                if ( (LA87_0==COMMA) ) {
                    alt87=1;
                }


                switch (alt87) {
            	case 1 :
            	    // Cmd.g:1089:7: COMMA ti= tupleItem
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleLiteral4819); if (state.failed) return n;
            	    pushFollow(FOLLOW_tupleItem_in_tupleLiteral4823);
            	    ti=tupleItem();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tiList.add(ti); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop87;
                }
            } while (true);

            match(input,RBRACE,FOLLOW_RBRACE_in_tupleLiteral4834); if (state.failed) return n;
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
    // Cmd.g:1097:1: tupleItem returns [ASTTupleItem n] : name= IDENT ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) ;
    public final ASTTupleItem tupleItem() throws RecognitionException {
        ASTTupleItem n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // Cmd.g:1098:1: (name= IDENT ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) )
            // Cmd.g:1099:5: name= IDENT ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tupleItem4865); if (state.failed) return n;
            // Cmd.g:1100:5: ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==COLON) ) {
                int LA88_1 = input.LA(2);

                if ( (synpred2_Cmd()) ) {
                    alt88=1;
                }
                else if ( (true) ) {
                    alt88=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 88, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA88_0==EQUAL) ) {
                alt88=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 88, 0, input);

                throw nvae;
            }
            switch (alt88) {
                case 1 :
                    // Cmd.g:1103:7: ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression
                    {
                    match(input,COLON,FOLLOW_COLON_in_tupleItem4904); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_tupleItem4908);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,EQUAL,FOLLOW_EQUAL_in_tupleItem4910); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_tupleItem4914);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTTupleItem(name, t, e); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:1106:7: ( COLON | EQUAL ) e= expression
                    {
                    if ( input.LA(1)==COLON||input.LA(1)==EQUAL ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_expression_in_tupleItem4946);
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
    // Cmd.g:1115:1: dateLiteral returns [ASTDateLiteral n] : 'Date' LBRACE v= STRING RBRACE ;
    public final ASTDateLiteral dateLiteral() throws RecognitionException {
        ASTDateLiteral n = null;

        Token v=null;

        try {
            // Cmd.g:1116:1: ( 'Date' LBRACE v= STRING RBRACE )
            // Cmd.g:1117:5: 'Date' LBRACE v= STRING RBRACE
            {
            match(input,104,FOLLOW_104_in_dateLiteral4991); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_dateLiteral4993); if (state.failed) return n;
            v=(Token)match(input,STRING,FOLLOW_STRING_in_dateLiteral4997); if (state.failed) return n;
            match(input,RBRACE,FOLLOW_RBRACE_in_dateLiteral4999); if (state.failed) return n;
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
    // Cmd.g:1127:1: type returns [ASTType n] : (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) ;
    public final ASTType type() throws RecognitionException {
        ASTType n = null;

        ASTSimpleType nTSimple = null;

        ASTCollectionType nTCollection = null;

        ASTTupleType nTTuple = null;


         Token tok = null; 
        try {
            // Cmd.g:1129:1: ( (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) )
            // Cmd.g:1130:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of type */ 
            }
            // Cmd.g:1131:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            int alt89=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt89=1;
                }
                break;
            case 95:
            case 96:
            case 97:
            case 98:
            case 105:
                {
                alt89=2;
                }
                break;
            case 103:
                {
                alt89=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 89, 0, input);

                throw nvae;
            }

            switch (alt89) {
                case 1 :
                    // Cmd.g:1132:7: nTSimple= simpleType
                    {
                    pushFollow(FOLLOW_simpleType_in_type5049);
                    nTSimple=simpleType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTSimple; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:1133:7: nTCollection= collectionType
                    {
                    pushFollow(FOLLOW_collectionType_in_type5061);
                    nTCollection=collectionType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTCollection; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:1134:7: nTTuple= tupleType
                    {
                    pushFollow(FOLLOW_tupleType_in_type5073);
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
    // Cmd.g:1139:1: typeOnly returns [ASTType n] : nT= type EOF ;
    public final ASTType typeOnly() throws RecognitionException {
        ASTType n = null;

        ASTType nT = null;


        try {
            // Cmd.g:1140:1: (nT= type EOF )
            // Cmd.g:1141:5: nT= type EOF
            {
            pushFollow(FOLLOW_type_in_typeOnly5105);
            nT=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_typeOnly5107); if (state.failed) return n;
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
    // Cmd.g:1151:1: simpleType returns [ASTSimpleType n] : name= IDENT ;
    public final ASTSimpleType simpleType() throws RecognitionException {
        ASTSimpleType n = null;

        Token name=null;

        try {
            // Cmd.g:1152:1: (name= IDENT )
            // Cmd.g:1153:5: name= IDENT
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_simpleType5135); if (state.failed) return n;
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
    // Cmd.g:1161:1: collectionType returns [ASTCollectionType n] : ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN ;
    public final ASTCollectionType collectionType() throws RecognitionException {
        ASTCollectionType n = null;

        ASTType elemType = null;


         Token op = null; 
        try {
            // Cmd.g:1163:1: ( ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN )
            // Cmd.g:1164:5: ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN
            {
            if ( state.backtracking==0 ) {
               op = input.LT(1); 
            }
            if ( (input.LA(1)>=95 && input.LA(1)<=98)||input.LA(1)==105 ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_collectionType5200); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_collectionType5204);
            elemType=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_collectionType5206); if (state.failed) return n;
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
    // Cmd.g:1174:1: tupleType returns [ASTTupleType n] : 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN ;
    public final ASTTupleType tupleType() throws RecognitionException {
        ASTTupleType n = null;

        ASTTuplePart tp = null;


         List tpList = new ArrayList(); 
        try {
            // Cmd.g:1176:1: ( 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN )
            // Cmd.g:1177:5: 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN
            {
            match(input,103,FOLLOW_103_in_tupleType5240); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_tupleType5242); if (state.failed) return n;
            pushFollow(FOLLOW_tuplePart_in_tupleType5251);
            tp=tuplePart();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tpList.add(tp); 
            }
            // Cmd.g:1179:5: ( COMMA tp= tuplePart )*
            loop90:
            do {
                int alt90=2;
                int LA90_0 = input.LA(1);

                if ( (LA90_0==COMMA) ) {
                    alt90=1;
                }


                switch (alt90) {
            	case 1 :
            	    // Cmd.g:1179:7: COMMA tp= tuplePart
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleType5262); if (state.failed) return n;
            	    pushFollow(FOLLOW_tuplePart_in_tupleType5266);
            	    tp=tuplePart();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tpList.add(tp); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop90;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_tupleType5278); if (state.failed) return n;
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
    // Cmd.g:1188:1: tuplePart returns [ASTTuplePart n] : name= IDENT COLON t= type ;
    public final ASTTuplePart tuplePart() throws RecognitionException {
        ASTTuplePart n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Cmd.g:1189:1: (name= IDENT COLON t= type )
            // Cmd.g:1190:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tuplePart5310); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_tuplePart5312); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_tuplePart5316);
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

    // $ANTLR start synpred1_Cmd
    public final void synpred1_Cmd_fragment() throws RecognitionException {   
        // Cmd.g:247:15: ( expression )
        // Cmd.g:247:16: expression
        {
        pushFollow(FOLLOW_expression_in_synpred1_Cmd785);
        expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_Cmd

    // $ANTLR start synpred2_Cmd
    public final void synpred2_Cmd_fragment() throws RecognitionException {   
        // Cmd.g:1103:7: ( COLON IDENT EQUAL )
        // Cmd.g:1103:8: COLON IDENT EQUAL
        {
        match(input,COLON,FOLLOW_COLON_in_synpred2_Cmd4895); if (state.failed) return ;
        match(input,IDENT,FOLLOW_IDENT_in_synpred2_Cmd4897); if (state.failed) return ;
        match(input,EQUAL,FOLLOW_EQUAL_in_synpred2_Cmd4899); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_Cmd

    // Delegated rules

    public final boolean synpred2_Cmd() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_Cmd_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_Cmd() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_Cmd_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA3 dfa3 = new DFA3(this);
    protected DFA9 dfa9 = new DFA9(this);
    static final String DFA3_eotS =
        "\17\uffff";
    static final String DFA3_eofS =
        "\15\uffff\1\14\1\uffff";
    static final String DFA3_minS =
        "\1\54\1\7\10\uffff\1\5\1\7\1\uffff\1\4\1\uffff";
    static final String DFA3_maxS =
        "\1\67\1\7\10\uffff\1\12\1\7\1\uffff\1\67\1\uffff";
    static final String DFA3_acceptS =
        "\2\uffff\1\2\1\4\1\5\1\6\1\7\1\10\1\11\1\12\2\uffff\1\1\1\uffff"+
        "\1\3";
    static final String DFA3_specialS =
        "\17\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\1\1\2\1\uffff\1\3\1\4\1\uffff\1\5\1\uffff\1\6\1\7\1\10\1"+
            "\11",
            "\1\12",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\13\4\uffff\1\14",
            "\1\15",
            "",
            "\1\14\47\uffff\2\14\1\16\2\14\1\uffff\1\14\1\uffff\4\14",
            ""
    };

    static final short[] DFA3_eot = DFA.unpackEncodedString(DFA3_eotS);
    static final short[] DFA3_eof = DFA.unpackEncodedString(DFA3_eofS);
    static final char[] DFA3_min = DFA.unpackEncodedStringToUnsignedChars(DFA3_minS);
    static final char[] DFA3_max = DFA.unpackEncodedStringToUnsignedChars(DFA3_maxS);
    static final short[] DFA3_accept = DFA.unpackEncodedString(DFA3_acceptS);
    static final short[] DFA3_special = DFA.unpackEncodedString(DFA3_specialS);
    static final short[][] DFA3_transition;

    static {
        int numStates = DFA3_transitionS.length;
        DFA3_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA3_transition[i] = DFA.unpackEncodedString(DFA3_transitionS[i]);
        }
    }

    class DFA3 extends DFA {

        public DFA3(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 3;
            this.eot = DFA3_eot;
            this.eof = DFA3_eof;
            this.min = DFA3_min;
            this.max = DFA3_max;
            this.accept = DFA3_accept;
            this.special = DFA3_special;
            this.transition = DFA3_transition;
        }
        public String getDescription() {
            return "111:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd )";
        }
    }
    static final String DFA9_eotS =
        "\37\uffff";
    static final String DFA9_eofS =
        "\1\25\36\uffff";
    static final String DFA9_minS =
        "\1\4\1\0\35\uffff";
    static final String DFA9_maxS =
        "\1\150\1\0\35\uffff";
    static final String DFA9_acceptS =
        "\2\uffff\23\1\1\2\11\uffff";
    static final String DFA9_specialS =
        "\1\0\1\1\35\uffff}>";
    static final String[] DFA9_transitionS = {
            "\1\25\2\uffff\1\11\1\23\12\uffff\1\5\6\uffff\2\2\5\uffff\1"+
            "\6\1\7\1\10\10\uffff\2\25\1\uffff\2\25\1\uffff\1\25\1\uffff"+
            "\3\25\1\1\33\uffff\1\2\1\uffff\1\21\3\22\1\24\3\uffff\1\3\1"+
            "\4\4\12\1\13\1\14\1\15\1\16\1\17\1\20",
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
            ""
    };

    static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
    static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
    static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
    static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
    static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
    static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
    static final short[][] DFA9_transition;

    static {
        int numStates = DFA9_transitionS.length;
        DFA9_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
        }
    }

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = DFA9_eot;
            this.eof = DFA9_eof;
            this.min = DFA9_min;
            this.max = DFA9_max;
            this.accept = DFA9_accept;
            this.special = DFA9_special;
            this.transition = DFA9_transition;
        }
        public String getDescription() {
            return "247:14: ( ( expression )=>e= expression | )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA9_0 = input.LA(1);

                         
                        int index9_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA9_0==55) ) {s = 1;}

                        else if ( ((LA9_0>=PLUS && LA9_0<=MINUS)||LA9_0==83) && (synpred1_Cmd())) {s = 2;}

                        else if ( (LA9_0==93) && (synpred1_Cmd())) {s = 3;}

                        else if ( (LA9_0==94) && (synpred1_Cmd())) {s = 4;}

                        else if ( (LA9_0==INT) && (synpred1_Cmd())) {s = 5;}

                        else if ( (LA9_0==REAL) && (synpred1_Cmd())) {s = 6;}

                        else if ( (LA9_0==STRING) && (synpred1_Cmd())) {s = 7;}

                        else if ( (LA9_0==HASH) && (synpred1_Cmd())) {s = 8;}

                        else if ( (LA9_0==IDENT) && (synpred1_Cmd())) {s = 9;}

                        else if ( ((LA9_0>=95 && LA9_0<=98)) && (synpred1_Cmd())) {s = 10;}

                        else if ( (LA9_0==99) && (synpred1_Cmd())) {s = 11;}

                        else if ( (LA9_0==100) && (synpred1_Cmd())) {s = 12;}

                        else if ( (LA9_0==101) && (synpred1_Cmd())) {s = 13;}

                        else if ( (LA9_0==102) && (synpred1_Cmd())) {s = 14;}

                        else if ( (LA9_0==103) && (synpred1_Cmd())) {s = 15;}

                        else if ( (LA9_0==104) && (synpred1_Cmd())) {s = 16;}

                        else if ( (LA9_0==85) && (synpred1_Cmd())) {s = 17;}

                        else if ( ((LA9_0>=86 && LA9_0<=88)) && (synpred1_Cmd())) {s = 18;}

                        else if ( (LA9_0==LPAREN) && (synpred1_Cmd())) {s = 19;}

                        else if ( (LA9_0==89) && (synpred1_Cmd())) {s = 20;}

                        else if ( (LA9_0==EOF||LA9_0==SEMI||(LA9_0>=44 && LA9_0<=45)||(LA9_0>=47 && LA9_0<=48)||LA9_0==50||(LA9_0>=52 && LA9_0<=54)) ) {s = 21;}

                         
                        input.seek(index9_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA9_1 = input.LA(1);

                         
                        int index9_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_Cmd()) ) {s = 20;}

                        else if ( (true) ) {s = 21;}

                         
                        input.seek(index9_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 9, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_cmd_in_cmdList65 = new BitSet(new long[]{0x00F5B00000000000L});
    public static final BitSet FOLLOW_cmd_in_cmdList77 = new BitSet(new long[]{0x00F5B00000000000L});
    public static final BitSet FOLLOW_EOF_in_cmdList88 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cmdStmt_in_cmd114 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_SEMI_in_cmd119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createCmd_in_cmdStmt150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createAssignCmd_in_cmdStmt162 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createInsertCmd_in_cmdStmt175 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_destroyCmd_in_cmdStmt187 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_insertCmd_in_cmdStmt199 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_deleteCmd_in_cmdStmt211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_setCmd_in_cmdStmt223 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_opEnterCmd_in_cmdStmt235 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_opExitCmd_in_cmdStmt247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_letCmd_in_cmdStmt259 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_createCmd288 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_createCmd292 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_createCmd299 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_simpleType_in_createCmd303 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_createAssignCmd332 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_createAssignCmd336 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_createAssignCmd338 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_createAssignCmd340 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_simpleType_in_createAssignCmd344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_createInsertCmd364 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_createInsertCmd368 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_createInsertCmd370 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_createInsertCmd374 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_createInsertCmd380 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_createInsertCmd382 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_createInsertCmd386 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_createInsertCmd388 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_destroyCmd424 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_destroyCmd428 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COMMA_in_destroyCmd450 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_destroyCmd454 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_48_in_insertCmd493 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_insertCmd495 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_insertCmd504 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_COMMA_in_insertCmd508 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_insertCmd516 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_insertCmd522 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_insertCmd526 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_insertCmd538 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_insertCmd540 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_insertCmd544 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_deleteCmd579 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_deleteCmd581 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_deleteCmd589 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_COMMA_in_deleteCmd593 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_deleteCmd601 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_deleteCmd607 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_deleteCmd611 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_deleteCmd622 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_deleteCmd624 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_deleteCmd628 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_setCmd658 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_setCmd662 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_setCmd664 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_setCmd668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_opEnterCmd702 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_opEnterCmd711 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_opEnterCmd715 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_opEnterCmd723 = new BitSet(new long[]{0x0080000E0C080380L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_opEnterCmd733 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_opEnterCmd739 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_opEnterCmd743 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_opEnterCmd757 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_opExitCmd781 = new BitSet(new long[]{0x0080000E0C080182L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_opExitCmd791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_letCmd824 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_letCmd828 = new BitSet(new long[]{0x0000000000000820L});
    public static final BitSet FOLLOW_COLON_in_letCmd832 = new BitSet(new long[]{0x0000000000000080L,0x0000028780000000L});
    public static final BitSet FOLLOW_type_in_letCmd836 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_letCmd841 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_letCmd845 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_model881 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_model885 = new BitSet(new long[]{0x1E00000000000080L,0x000000000000000FL});
    public static final BitSet FOLLOW_enumTypeDefinition_in_model897 = new BitSet(new long[]{0x1E00000000000080L,0x000000000000000FL});
    public static final BitSet FOLLOW_generalClassDefinition_in_model914 = new BitSet(new long[]{0x1A00000000000080L,0x000000000000000FL});
    public static final BitSet FOLLOW_associationDefinition_in_model931 = new BitSet(new long[]{0x1A00000000000080L,0x000000000000000FL});
    public static final BitSet FOLLOW_57_in_model947 = new BitSet(new long[]{0x1A00000000000080L,0x000000000000010FL});
    public static final BitSet FOLLOW_invariant_in_model965 = new BitSet(new long[]{0x1A00000000000080L,0x000000000000010FL});
    public static final BitSet FOLLOW_prePost_in_model986 = new BitSet(new long[]{0x1A00000000000080L,0x000000000000010FL});
    public static final BitSet FOLLOW_EOF_in_model1027 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_enumTypeDefinition1054 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_enumTypeDefinition1058 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LBRACE_in_enumTypeDefinition1060 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_enumTypeDefinition1064 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RBRACE_in_enumTypeDefinition1066 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_SEMI_in_enumTypeDefinition1070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_generalClassDefinition1109 = new BitSet(new long[]{0x1800000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_classDefinition_in_generalClassDefinition1127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_associationClassDefinition_in_generalClassDefinition1147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_classDefinition1186 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_classDefinition1190 = new BitSet(new long[]{0xE200000000004000L});
    public static final BitSet FOLLOW_LESS_in_classDefinition1200 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_classDefinition1204 = new BitSet(new long[]{0xE200000000000000L});
    public static final BitSet FOLLOW_61_in_classDefinition1217 = new BitSet(new long[]{0xC200000000000080L});
    public static final BitSet FOLLOW_attributeDefinition_in_classDefinition1230 = new BitSet(new long[]{0xC200000000000080L});
    public static final BitSet FOLLOW_62_in_classDefinition1251 = new BitSet(new long[]{0x8200000000000080L});
    public static final BitSet FOLLOW_operationDefinition_in_classDefinition1264 = new BitSet(new long[]{0x8200000000000080L});
    public static final BitSet FOLLOW_57_in_classDefinition1285 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_invariantClause_in_classDefinition1305 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_63_in_classDefinition1329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_associationClassDefinition1362 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_associationClassDefinition1388 = new BitSet(new long[]{0x0000400000004000L});
    public static final BitSet FOLLOW_LESS_in_associationClassDefinition1398 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_associationClassDefinition1402 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_associationClassDefinition1413 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_associationEnd_in_associationClassDefinition1421 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_associationEnd_in_associationClassDefinition1433 = new BitSet(new long[]{0xE200000000000080L,0x000000000000000CL});
    public static final BitSet FOLLOW_61_in_associationClassDefinition1446 = new BitSet(new long[]{0xC200000000000080L,0x000000000000000CL});
    public static final BitSet FOLLOW_attributeDefinition_in_associationClassDefinition1459 = new BitSet(new long[]{0xC200000000000080L,0x000000000000000CL});
    public static final BitSet FOLLOW_62_in_associationClassDefinition1480 = new BitSet(new long[]{0x8200000000000080L,0x000000000000000CL});
    public static final BitSet FOLLOW_operationDefinition_in_associationClassDefinition1493 = new BitSet(new long[]{0x8200000000000080L,0x000000000000000CL});
    public static final BitSet FOLLOW_57_in_associationClassDefinition1514 = new BitSet(new long[]{0x8000000000000000L,0x000000000000060CL});
    public static final BitSet FOLLOW_invariantClause_in_associationClassDefinition1534 = new BitSet(new long[]{0x8000000000000000L,0x000000000000060CL});
    public static final BitSet FOLLOW_set_in_associationClassDefinition1568 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_63_in_associationClassDefinition1597 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_attributeDefinition1626 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_attributeDefinition1628 = new BitSet(new long[]{0x0000000000000080L,0x0000028780000000L});
    public static final BitSet FOLLOW_type_in_attributeDefinition1632 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_SEMI_in_attributeDefinition1636 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationDefinition1674 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_paramList_in_operationDefinition1682 = new BitSet(new long[]{0x0000000000000832L,0x0000000000001800L});
    public static final BitSet FOLLOW_COLON_in_operationDefinition1690 = new BitSet(new long[]{0x0000000000000080L,0x0000028780000000L});
    public static final BitSet FOLLOW_type_in_operationDefinition1694 = new BitSet(new long[]{0x0000000000000812L,0x0000000000001800L});
    public static final BitSet FOLLOW_EQUAL_in_operationDefinition1722 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_operationDefinition1726 = new BitSet(new long[]{0x0000000000000012L,0x0000000000001800L});
    public static final BitSet FOLLOW_EQUAL_in_operationDefinition1748 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_operationDefinition1750 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_SCRIPTBODY_in_operationDefinition1754 = new BitSet(new long[]{0x0000000000000012L,0x0000000000001800L});
    public static final BitSet FOLLOW_prePostClause_in_operationDefinition1785 = new BitSet(new long[]{0x0000000000000012L,0x0000000000001800L});
    public static final BitSet FOLLOW_SEMI_in_operationDefinition1798 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_keyAssociation_in_associationDefinition1836 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_66_in_associationDefinition1840 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_67_in_associationDefinition1844 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_associationDefinition1859 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_associationDefinition1867 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_associationEnd_in_associationDefinition1875 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_associationEnd_in_associationDefinition1887 = new BitSet(new long[]{0x8000000000000080L});
    public static final BitSet FOLLOW_63_in_associationDefinition1898 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd1924 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_LBRACK_in_associationEnd1926 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_multiplicity_in_associationEnd1930 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RBRACK_in_associationEnd1932 = new BitSet(new long[]{0x0000000000000092L,0x00000000000000E0L});
    public static final BitSet FOLLOW_keyRole_in_associationEnd1943 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd1947 = new BitSet(new long[]{0x0000000000000092L,0x00000000000000E0L});
    public static final BitSet FOLLOW_69_in_associationEnd1968 = new BitSet(new long[]{0x0000000000000092L,0x00000000000000E0L});
    public static final BitSet FOLLOW_70_in_associationEnd1980 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd1984 = new BitSet(new long[]{0x0000000000000092L,0x00000000000000E0L});
    public static final BitSet FOLLOW_keyUnion_in_associationEnd1996 = new BitSet(new long[]{0x0000000000000092L,0x00000000000000E0L});
    public static final BitSet FOLLOW_71_in_associationEnd2008 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd2012 = new BitSet(new long[]{0x0000000000000092L,0x00000000000000E0L});
    public static final BitSet FOLLOW_SEMI_in_associationEnd2029 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity2064 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COMMA_in_multiplicity2074 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity2078 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange2107 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_DOTDOT_in_multiplicityRange2117 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange2121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_multiplicitySpec2155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_multiplicitySpec2165 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_invariant2206 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_invariant2216 = new BitSet(new long[]{0x0000000000000420L});
    public static final BitSet FOLLOW_COMMA_in_invariant2229 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_invariant2233 = new BitSet(new long[]{0x0000000000000420L});
    public static final BitSet FOLLOW_COLON_in_invariant2241 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_simpleType_in_invariant2253 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000600L});
    public static final BitSet FOLLOW_invariantClause_in_invariant2265 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000600L});
    public static final BitSet FOLLOW_73_in_invariantClause2296 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_IDENT_in_invariantClause2302 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_invariantClause2307 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_invariantClause2311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_invariantClause2321 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_invariantClause2323 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_IDENT_in_invariantClause2329 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_invariantClause2334 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_invariantClause2338 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_prePost2364 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_prePost2368 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_COLON_COLON_in_prePost2370 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_prePost2374 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_paramList_in_prePost2378 = new BitSet(new long[]{0x0000000000000020L,0x0000000000001800L});
    public static final BitSet FOLLOW_COLON_in_prePost2382 = new BitSet(new long[]{0x0000000000000080L,0x0000028780000000L});
    public static final BitSet FOLLOW_type_in_prePost2386 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001800L});
    public static final BitSet FOLLOW_prePostClause_in_prePost2405 = new BitSet(new long[]{0x0000000000000002L,0x0000000000001800L});
    public static final BitSet FOLLOW_set_in_prePostClause2444 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_IDENT_in_prePostClause2459 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_prePostClause2464 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_prePostClause2468 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_keyUnion2490 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_keyAssociation2504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_keyRole2518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_expressionOnly2548 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_expressionOnly2550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_expression2598 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_expression2602 = new BitSet(new long[]{0x0000000000000820L});
    public static final BitSet FOLLOW_COLON_in_expression2606 = new BitSet(new long[]{0x0000000000000080L,0x0000028780000000L});
    public static final BitSet FOLLOW_type_in_expression2610 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_expression2615 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_expression2619 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_expression2621 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_conditionalImpliesExpression_in_expression2646 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_paramList2679 = new BitSet(new long[]{0x0000000000000280L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList2696 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_paramList2708 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList2712 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_paramList2732 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_idList2761 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COMMA_in_idList2771 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_idList2775 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_IDENT_in_variableDeclaration2806 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_variableDeclaration2808 = new BitSet(new long[]{0x0000000000000080L,0x0000028780000000L});
    public static final BitSet FOLLOW_type_in_variableDeclaration2812 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2848 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_conditionalImpliesExpression2861 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2865 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2910 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_conditionalOrExpression2923 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2927 = new BitSet(new long[]{0x0000000000000002L,0x0000000000008000L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2971 = new BitSet(new long[]{0x0000000000000002L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_conditionalXOrExpression2984 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2988 = new BitSet(new long[]{0x0000000000000002L,0x0000000000010000L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression3032 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_conditionalAndExpression3045 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression3049 = new BitSet(new long[]{0x0000000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression3097 = new BitSet(new long[]{0x0000000000400802L});
    public static final BitSet FOLLOW_set_in_equalityExpression3116 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression3126 = new BitSet(new long[]{0x0000000000400802L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression3175 = new BitSet(new long[]{0x0000000003804002L});
    public static final BitSet FOLLOW_set_in_relationalExpression3193 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression3211 = new BitSet(new long[]{0x0000000003804002L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3261 = new BitSet(new long[]{0x000000000C000002L});
    public static final BitSet FOLLOW_set_in_additiveExpression3279 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3289 = new BitSet(new long[]{0x000000000C000002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3339 = new BitSet(new long[]{0x0000000010100002L,0x0000000000040000L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression3357 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3371 = new BitSet(new long[]{0x0000000010100002L,0x0000000000040000L});
    public static final BitSet FOLLOW_set_in_unaryExpression3433 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression3457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpression_in_unaryExpression3477 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_postfixExpression3510 = new BitSet(new long[]{0x0000000060000002L});
    public static final BitSet FOLLOW_ARROW_in_postfixExpression3528 = new BitSet(new long[]{0x0000000000000080L,0x0000000001E00000L});
    public static final BitSet FOLLOW_DOT_in_postfixExpression3534 = new BitSet(new long[]{0x0000000000000080L,0x0000000001E00000L});
    public static final BitSet FOLLOW_propertyCall_in_postfixExpression3545 = new BitSet(new long[]{0x0000000060000002L});
    public static final BitSet FOLLOW_literal_in_primaryExpression3585 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyCall_in_primaryExpression3597 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression3608 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_primaryExpression3612 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression3614 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifExpression_in_primaryExpression3626 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_primaryExpression3643 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_DOT_in_primaryExpression3645 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_primaryExpression3647 = new BitSet(new long[]{0x0000000080000102L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression3651 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression3653 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_AT_in_primaryExpression3674 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_primaryExpression3676 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_queryExpression_in_propertyCall3749 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterateExpression_in_propertyCall3762 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationExpression_in_propertyCall3775 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeExpression_in_propertyCall3788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_queryExpression3823 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_queryExpression3830 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_queryExpression3841 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_BAR_in_queryExpression3845 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_queryExpression3856 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_queryExpression3862 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_85_in_iterateExpression3894 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_iterateExpression3900 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_iterateExpression3908 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_iterateExpression3910 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_variableInitialization_in_iterateExpression3918 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_BAR_in_iterateExpression3920 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_iterateExpression3928 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_iterateExpression3934 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression3978 = new BitSet(new long[]{0x0000000080010102L});
    public static final BitSet FOLLOW_LBRACK_in_operationExpression3994 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression3998 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RBRACK_in_operationExpression4000 = new BitSet(new long[]{0x0000000080000102L});
    public static final BitSet FOLLOW_AT_in_operationExpression4013 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_operationExpression4015 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_LPAREN_in_operationExpression4036 = new BitSet(new long[]{0x0080000E0C080380L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_operationExpression4057 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_operationExpression4069 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_operationExpression4073 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_operationExpression4093 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_typeExpression4136 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_typeExpression4152 = new BitSet(new long[]{0x0000000000000080L,0x0000028780000000L});
    public static final BitSet FOLLOW_type_in_typeExpression4156 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_typeExpression4158 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_elemVarsDeclaration4197 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_COLON_in_elemVarsDeclaration4205 = new BitSet(new long[]{0x0000000000000080L,0x0000028780000000L});
    public static final BitSet FOLLOW_type_in_elemVarsDeclaration4209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableInitialization4244 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_variableInitialization4246 = new BitSet(new long[]{0x0000000000000080L,0x0000028780000000L});
    public static final BitSet FOLLOW_type_in_variableInitialization4250 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_variableInitialization4252 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_variableInitialization4256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_ifExpression4288 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_ifExpression4292 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_ifExpression4294 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_ifExpression4298 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_ifExpression4300 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_ifExpression4304 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_ifExpression4306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_literal4345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_literal4359 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_literal4372 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REAL_in_literal4387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal4401 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_literal4411 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_literal4415 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_literal4427 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_COLON_COLON_in_literal4429 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_literal4433 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionLiteral_in_literal4445 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyCollectionLiteral_in_literal4457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedLiteral_in_literal4469 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleLiteral_in_literal4481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dateLiteral_in_literal4493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionLiteral4531 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LBRACE_in_collectionLiteral4560 = new BitSet(new long[]{0x0080000E0C082180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral4577 = new BitSet(new long[]{0x0000000000002400L});
    public static final BitSet FOLLOW_COMMA_in_collectionLiteral4590 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral4594 = new BitSet(new long[]{0x0000000000002400L});
    public static final BitSet FOLLOW_RBRACE_in_collectionLiteral4613 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_collectionItem4642 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_DOTDOT_in_collectionItem4653 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_collectionItem4657 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_emptyCollectionLiteral4686 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_emptyCollectionLiteral4688 = new BitSet(new long[]{0x0000000000000000L,0x0000020780000000L});
    public static final BitSet FOLLOW_collectionType_in_emptyCollectionLiteral4692 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_emptyCollectionLiteral4694 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_100_in_undefinedLiteral4724 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_undefinedLiteral4726 = new BitSet(new long[]{0x0000000000000080L,0x0000028780000000L});
    public static final BitSet FOLLOW_type_in_undefinedLiteral4730 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_undefinedLiteral4732 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_101_in_undefinedLiteral4746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_102_in_undefinedLiteral4760 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_tupleLiteral4794 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LBRACE_in_tupleLiteral4800 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral4808 = new BitSet(new long[]{0x0000000000002400L});
    public static final BitSet FOLLOW_COMMA_in_tupleLiteral4819 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral4823 = new BitSet(new long[]{0x0000000000002400L});
    public static final BitSet FOLLOW_RBRACE_in_tupleLiteral4834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tupleItem4865 = new BitSet(new long[]{0x0000000000000820L});
    public static final BitSet FOLLOW_COLON_in_tupleItem4904 = new BitSet(new long[]{0x0000000000000080L,0x0000028780000000L});
    public static final BitSet FOLLOW_type_in_tupleItem4908 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_tupleItem4910 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_tupleItem4914 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_tupleItem4936 = new BitSet(new long[]{0x0080000E0C080180L,0x000001FFE3E80000L});
    public static final BitSet FOLLOW_expression_in_tupleItem4946 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_104_in_dateLiteral4991 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LBRACE_in_dateLiteral4993 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRING_in_dateLiteral4997 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RBRACE_in_dateLiteral4999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleType_in_type5049 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_type5061 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleType_in_type5073 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeOnly5105 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_typeOnly5107 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_simpleType5135 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionType5173 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_collectionType5200 = new BitSet(new long[]{0x0000000000000080L,0x0000028780000000L});
    public static final BitSet FOLLOW_type_in_collectionType5204 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_collectionType5206 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_tupleType5240 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_tupleType5242 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType5251 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_tupleType5262 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType5266 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_tupleType5278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tuplePart5310 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_tuplePart5312 = new BitSet(new long[]{0x0000000000000080L,0x0000028780000000L});
    public static final BitSet FOLLOW_type_in_tuplePart5316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_synpred1_Cmd785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_synpred2_Cmd4895 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_synpred2_Cmd4897 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_synpred2_Cmd4899 = new BitSet(new long[]{0x0000000000000002L});

}