// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 Cmd.g 2010-03-09 12:39:02
 
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
import org.tzi.use.uml.sys.MShowHideCropCmd.Mode;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all") public class CmdParser extends BaseParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "SEMI", "COLON", "COLON_EQUAL", "IDENT", "LPAREN", "RPAREN", "COMMA", "EQUAL", "LBRACE", "RBRACE", "LESS", "SCRIPTBODY", "LBRACK", "RBRACK", "DOTDOT", "INT", "STAR", "COLON_COLON", "NOT_EQUAL", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "PLUS", "MINUS", "SLASH", "ARROW", "DOT", "AT", "BAR", "REAL", "STRING", "HASH", "NEWLINE", "WS", "SL_COMMENT", "ML_COMMENT", "RANGE_OR_INT", "ESC", "HEX_DIGIT", "VOCAB", "'create'", "'assign'", "'between'", "'destroy'", "'insert'", "'into'", "'delete'", "'from'", "'set'", "'openter'", "'opexit'", "'let'", "'hide'", "'all'", "'link'", "'show'", "'crop'", "'model'", "'constraints'", "'enum'", "'abstract'", "'class'", "'attributes'", "'operations'", "'end'", "'associationClass'", "'associationclass'", "'aggregation'", "'composition'", "'script'", "'ordered'", "'subsets'", "'redefines'", "'context'", "'inv'", "'existential'", "'pre'", "'post'", "'in'", "'implies'", "'or'", "'xor'", "'and'", "'div'", "'not'", "'allInstances'", "'iterate'", "'oclAsType'", "'oclIsKindOf'", "'oclIsTypeOf'", "'if'", "'then'", "'else'", "'endif'", "'true'", "'false'", "'Set'", "'Sequence'", "'Bag'", "'OrderedSet'", "'oclEmpty'", "'oclUndefined'", "'Undefined'", "'null'", "'Tuple'", "'Date'", "'Collection'"
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
    public static final int SLASH=28;
    public static final int T__51=51;
    public static final int COLON_EQUAL=6;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__107=107;
    public static final int T__108=108;
    public static final int COMMA=10;
    public static final int T__109=109;
    public static final int T__59=59;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int EQUAL=11;
    public static final int T__105=105;
    public static final int T__106=106;
    public static final int T__110=110;
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
    // Cmd.g:80:1: cmdList returns [ASTCmdList cmdList] : c= cmd (c= cmd )* EOF ;
    public final ASTCmdList cmdList() throws RecognitionException {
        ASTCmdList cmdList = null;

        ASTCmd c = null;


         cmdList = new ASTCmdList(); 
        try {
            // Cmd.g:82:1: (c= cmd (c= cmd )* EOF )
            // Cmd.g:83:5: c= cmd (c= cmd )* EOF
            {
            pushFollow(FOLLOW_cmd_in_cmdList65);
            c=cmd();

            state._fsp--;
            if (state.failed) return cmdList;
            if ( state.backtracking==0 ) {
               cmdList.add(c); 
            }
            // Cmd.g:84:5: (c= cmd )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=44 && LA1_0<=45)||(LA1_0>=47 && LA1_0<=48)||LA1_0==50||(LA1_0>=52 && LA1_0<=56)||(LA1_0>=59 && LA1_0<=60)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Cmd.g:84:7: c= cmd
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
    // Cmd.g:92:1: cmd returns [ASTCmd n] : stmt= cmdStmt ( SEMI )? ;
    public final ASTCmd cmd() throws RecognitionException {
        ASTCmd n = null;

        ASTCmd stmt = null;


        try {
            // Cmd.g:93:1: (stmt= cmdStmt ( SEMI )? )
            // Cmd.g:94:5: stmt= cmdStmt ( SEMI )?
            {
            pushFollow(FOLLOW_cmdStmt_in_cmd114);
            stmt=cmdStmt();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = stmt; 
            }
            // Cmd.g:94:35: ( SEMI )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==SEMI) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // Cmd.g:94:37: SEMI
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
    // Cmd.g:110:1: cmdStmt returns [ASTCmd n] : (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= showCmd | nC= hideCmd | nC= cropCmd ) ;
    public final ASTCmd cmdStmt() throws RecognitionException {
        ASTCmd n = null;

        ASTCmd nC = null;


        try {
            // Cmd.g:111:1: ( (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= showCmd | nC= hideCmd | nC= cropCmd ) )
            // Cmd.g:112:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= showCmd | nC= hideCmd | nC= cropCmd )
            {
            // Cmd.g:112:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= showCmd | nC= hideCmd | nC= cropCmd )
            int alt3=13;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // Cmd.g:113:7: nC= createCmd
                    {
                    pushFollow(FOLLOW_createCmd_in_cmdStmt150);
                    nC=createCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 2 :
                    // Cmd.g:114:7: nC= createAssignCmd
                    {
                    pushFollow(FOLLOW_createAssignCmd_in_cmdStmt162);
                    nC=createAssignCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 3 :
                    // Cmd.g:115:7: nC= createInsertCmd
                    {
                    pushFollow(FOLLOW_createInsertCmd_in_cmdStmt175);
                    nC=createInsertCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 4 :
                    // Cmd.g:116:7: nC= destroyCmd
                    {
                    pushFollow(FOLLOW_destroyCmd_in_cmdStmt187);
                    nC=destroyCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 5 :
                    // Cmd.g:117:7: nC= insertCmd
                    {
                    pushFollow(FOLLOW_insertCmd_in_cmdStmt199);
                    nC=insertCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 6 :
                    // Cmd.g:118:7: nC= deleteCmd
                    {
                    pushFollow(FOLLOW_deleteCmd_in_cmdStmt211);
                    nC=deleteCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 7 :
                    // Cmd.g:119:7: nC= setCmd
                    {
                    pushFollow(FOLLOW_setCmd_in_cmdStmt223);
                    nC=setCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 8 :
                    // Cmd.g:120:7: nC= opEnterCmd
                    {
                    pushFollow(FOLLOW_opEnterCmd_in_cmdStmt235);
                    nC=opEnterCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 9 :
                    // Cmd.g:121:7: nC= opExitCmd
                    {
                    pushFollow(FOLLOW_opExitCmd_in_cmdStmt247);
                    nC=opExitCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 10 :
                    // Cmd.g:122:7: nC= letCmd
                    {
                    pushFollow(FOLLOW_letCmd_in_cmdStmt259);
                    nC=letCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 11 :
                    // Cmd.g:123:7: nC= showCmd
                    {
                    pushFollow(FOLLOW_showCmd_in_cmdStmt271);
                    nC=showCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 12 :
                    // Cmd.g:124:7: nC= hideCmd
                    {
                    pushFollow(FOLLOW_hideCmd_in_cmdStmt283);
                    nC=hideCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 13 :
                    // Cmd.g:125:7: nC= cropCmd
                    {
                    pushFollow(FOLLOW_cropCmd_in_cmdStmt295);
                    nC=cropCmd();

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
    // Cmd.g:135:1: createCmd returns [ASTCmd n] : 'create' nIdList= idList COLON t= simpleType ;
    public final ASTCmd createCmd() throws RecognitionException {
        ASTCmd n = null;

        List nIdList = null;

        ASTSimpleType t = null;


        try {
            // Cmd.g:136:1: ( 'create' nIdList= idList COLON t= simpleType )
            // Cmd.g:137:5: 'create' nIdList= idList COLON t= simpleType
            {
            match(input,44,FOLLOW_44_in_createCmd324); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createCmd328);
            nIdList=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_createCmd335); if (state.failed) return n;
            pushFollow(FOLLOW_simpleType_in_createCmd339);
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
    // Cmd.g:147:1: createAssignCmd returns [ASTCmd n] : 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType ;
    public final ASTCmd createAssignCmd() throws RecognitionException {
        ASTCmd n = null;

        List nIdList = null;

        ASTSimpleType t = null;


        try {
            // Cmd.g:148:1: ( 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType )
            // Cmd.g:149:5: 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType
            {
            match(input,45,FOLLOW_45_in_createAssignCmd368); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createAssignCmd372);
            nIdList=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_createAssignCmd374); if (state.failed) return n;
            match(input,44,FOLLOW_44_in_createAssignCmd376); if (state.failed) return n;
            pushFollow(FOLLOW_simpleType_in_createAssignCmd380);
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
    // Cmd.g:157:1: createInsertCmd returns [ASTCmd n] : 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN ;
    public final ASTCmd createInsertCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        Token idAssoc=null;
        List idListInsert = null;


        try {
            // Cmd.g:158:1: ( 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN )
            // Cmd.g:159:5: 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN
            {
            match(input,44,FOLLOW_44_in_createInsertCmd400); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_createInsertCmd404); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_createInsertCmd406); if (state.failed) return n;
            idAssoc=(Token)match(input,IDENT,FOLLOW_IDENT_in_createInsertCmd410); if (state.failed) return n;
            match(input,46,FOLLOW_46_in_createInsertCmd416); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_createInsertCmd418); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createInsertCmd422);
            idListInsert=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_createInsertCmd424); if (state.failed) return n;
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
    // Cmd.g:170:1: destroyCmd returns [ASTCmd n] : 'destroy' e= expression ( COMMA e= expression )* ;
    public final ASTCmd destroyCmd() throws RecognitionException {
        ASTCmd n = null;

        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // Cmd.g:172:1: ( 'destroy' e= expression ( COMMA e= expression )* )
            // Cmd.g:173:6: 'destroy' e= expression ( COMMA e= expression )*
            {
            match(input,47,FOLLOW_47_in_destroyCmd460); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_destroyCmd464);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // Cmd.g:174:16: ( COMMA e= expression )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==COMMA) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // Cmd.g:174:18: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_destroyCmd486); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_destroyCmd490);
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
    // Cmd.g:184:1: insertCmd returns [ASTCmd n] : 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT ;
    public final ASTCmd insertCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // Cmd.g:186:1: ( 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT )
            // Cmd.g:187:5: 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT
            {
            match(input,48,FOLLOW_48_in_insertCmd529); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_insertCmd531); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_insertCmd540);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_insertCmd544); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_insertCmd552);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // Cmd.g:189:42: ( COMMA e= expression )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==COMMA) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // Cmd.g:189:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_insertCmd558); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_insertCmd562);
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

            match(input,RPAREN,FOLLOW_RPAREN_in_insertCmd574); if (state.failed) return n;
            match(input,49,FOLLOW_49_in_insertCmd576); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_insertCmd580); if (state.failed) return n;
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
    // Cmd.g:200:1: deleteCmd returns [ASTCmd n] : 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT ;
    public final ASTCmd deleteCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // Cmd.g:202:1: ( 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT )
            // Cmd.g:203:5: 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT
            {
            match(input,50,FOLLOW_50_in_deleteCmd615); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_deleteCmd617); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_deleteCmd625);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_deleteCmd629); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_deleteCmd637);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // Cmd.g:205:42: ( COMMA e= expression )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==COMMA) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // Cmd.g:205:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_deleteCmd643); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_deleteCmd647);
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

            match(input,RPAREN,FOLLOW_RPAREN_in_deleteCmd658); if (state.failed) return n;
            match(input,51,FOLLOW_51_in_deleteCmd660); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_deleteCmd664); if (state.failed) return n;
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
    // Cmd.g:219:1: setCmd returns [ASTCmd n] : 'set' e1= expression COLON_EQUAL e2= expression ;
    public final ASTCmd setCmd() throws RecognitionException {
        ASTCmd n = null;

        ASTExpression e1 = null;

        ASTExpression e2 = null;


        try {
            // Cmd.g:220:1: ( 'set' e1= expression COLON_EQUAL e2= expression )
            // Cmd.g:221:5: 'set' e1= expression COLON_EQUAL e2= expression
            {
            match(input,52,FOLLOW_52_in_setCmd694); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_setCmd698);
            e1=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_setCmd700); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_setCmd704);
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
    // Cmd.g:233:1: opEnterCmd returns [ASTCmd n] : 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN ;
    public final ASTCmd opEnterCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        ASTExpression e = null;


        ASTOpEnterCmd nOpEnter = null;
        try {
            // Cmd.g:235:1: ( 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )
            // Cmd.g:236:5: 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
            {
            match(input,53,FOLLOW_53_in_opEnterCmd738); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_opEnterCmd747);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_opEnterCmd751); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               nOpEnter = new ASTOpEnterCmd(e, id); n = nOpEnter;
            }
            match(input,LPAREN,FOLLOW_LPAREN_in_opEnterCmd759); if (state.failed) return n;
            // Cmd.g:239:5: (e= expression ( COMMA e= expression )* )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( ((LA8_0>=IDENT && LA8_0<=LPAREN)||LA8_0==INT||(LA8_0>=PLUS && LA8_0<=MINUS)||(LA8_0>=REAL && LA8_0<=HASH)||LA8_0==55||LA8_0==88||(LA8_0>=90 && LA8_0<=94)||(LA8_0>=98 && LA8_0<=109)) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // Cmd.g:239:7: e= expression ( COMMA e= expression )*
                    {
                    pushFollow(FOLLOW_expression_in_opEnterCmd769);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       nOpEnter.addArg(e); 
                    }
                    // Cmd.g:239:47: ( COMMA e= expression )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==COMMA) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // Cmd.g:239:49: COMMA e= expression
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_opEnterCmd775); if (state.failed) return n;
                    	    pushFollow(FOLLOW_expression_in_opEnterCmd779);
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

            match(input,RPAREN,FOLLOW_RPAREN_in_opEnterCmd793); if (state.failed) return n;

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
    // Cmd.g:249:1: opExitCmd returns [ASTCmd n] : 'opexit' ( ( expression )=>e= expression | ) ;
    public final ASTCmd opExitCmd() throws RecognitionException {
        ASTCmd n = null;

        ASTExpression e = null;


        try {
            // Cmd.g:250:1: ( 'opexit' ( ( expression )=>e= expression | ) )
            // Cmd.g:251:5: 'opexit' ( ( expression )=>e= expression | )
            {
            match(input,54,FOLLOW_54_in_opExitCmd817); if (state.failed) return n;
            // Cmd.g:251:14: ( ( expression )=>e= expression | )
            int alt9=2;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // Cmd.g:251:15: ( expression )=>e= expression
                    {
                    pushFollow(FOLLOW_expression_in_opExitCmd827);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 2 :
                    // Cmd.g:251:45: 
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
    // Cmd.g:260:1: letCmd returns [ASTCmd n] : 'let' name= IDENT ( COLON t= type )? EQUAL e= expression ;
    public final ASTCmd letCmd() throws RecognitionException {
        ASTCmd n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // Cmd.g:261:1: ( 'let' name= IDENT ( COLON t= type )? EQUAL e= expression )
            // Cmd.g:262:5: 'let' name= IDENT ( COLON t= type )? EQUAL e= expression
            {
            match(input,55,FOLLOW_55_in_letCmd860); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_letCmd864); if (state.failed) return n;
            // Cmd.g:262:22: ( COLON t= type )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==COLON) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // Cmd.g:262:24: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_letCmd868); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_letCmd872);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            match(input,EQUAL,FOLLOW_EQUAL_in_letCmd877); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_letCmd881);
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


    // $ANTLR start "hideCmd"
    // Cmd.g:269:1: hideCmd returns [ASTCmd n] : 'hide' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) ;
    public final ASTCmd hideCmd() throws RecognitionException {
        ASTCmd n = null;

        Token classname=null;
        Token ass=null;
        List objList = null;


        try {
            // Cmd.g:270:1: ( 'hide' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) )
            // Cmd.g:271:2: 'hide' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            {
            match(input,56,FOLLOW_56_in_hideCmd908); if (state.failed) return n;
            // Cmd.g:271:9: ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            int alt12=3;
            switch ( input.LA(1) ) {
            case 57:
                {
                alt12=1;
                }
                break;
            case IDENT:
                {
                alt12=2;
                }
                break;
            case 58:
                {
                alt12=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // Cmd.g:272:6: 'all'
                    {
                    match(input,57,FOLLOW_57_in_hideCmd917); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideAllCmd(Mode.HIDE); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:273:6: objList= idList ( COLON classname= IDENT )?
                    {
                    pushFollow(FOLLOW_idList_in_hideCmd930);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    // Cmd.g:273:23: ( COLON classname= IDENT )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==COLON) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // Cmd.g:273:24: COLON classname= IDENT
                            {
                            match(input,COLON,FOLLOW_COLON_in_hideCmd933); if (state.failed) return n;
                            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_hideCmd939); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideCropObjectsCmd(Mode.HIDE, objList, classname); 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:274:6: 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT
                    {
                    match(input,58,FOLLOW_58_in_hideCmd950); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_hideCmd952); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_hideCmd958);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_hideCmd960); if (state.failed) return n;
                    match(input,51,FOLLOW_51_in_hideCmd962); if (state.failed) return n;
                    ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_hideCmd966); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideCropLinkObjectsCmd(Mode.HIDE, ass, objList); 
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
    // $ANTLR end "hideCmd"


    // $ANTLR start "showCmd"
    // Cmd.g:280:1: showCmd returns [ASTCmd n] : 'show' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) ;
    public final ASTCmd showCmd() throws RecognitionException {
        ASTCmd n = null;

        Token classname=null;
        Token ass=null;
        List objList = null;


        try {
            // Cmd.g:281:1: ( 'show' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) )
            // Cmd.g:282:2: 'show' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            {
            match(input,59,FOLLOW_59_in_showCmd991); if (state.failed) return n;
            // Cmd.g:282:9: ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            int alt14=3;
            switch ( input.LA(1) ) {
            case 57:
                {
                alt14=1;
                }
                break;
            case IDENT:
                {
                alt14=2;
                }
                break;
            case 58:
                {
                alt14=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // Cmd.g:283:6: 'all'
                    {
                    match(input,57,FOLLOW_57_in_showCmd1000); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideAllCmd(Mode.SHOW); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:284:6: objList= idList ( COLON classname= IDENT )?
                    {
                    pushFollow(FOLLOW_idList_in_showCmd1013);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    // Cmd.g:284:23: ( COLON classname= IDENT )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( (LA13_0==COLON) ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // Cmd.g:284:24: COLON classname= IDENT
                            {
                            match(input,COLON,FOLLOW_COLON_in_showCmd1016); if (state.failed) return n;
                            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_showCmd1022); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideCropObjectsCmd(Mode.SHOW, objList, classname); 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:285:6: 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT
                    {
                    match(input,58,FOLLOW_58_in_showCmd1033); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_showCmd1035); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_showCmd1041);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_showCmd1043); if (state.failed) return n;
                    match(input,51,FOLLOW_51_in_showCmd1045); if (state.failed) return n;
                    ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_showCmd1049); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideCropLinkObjectsCmd(Mode.SHOW, ass, objList); 
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
    // $ANTLR end "showCmd"


    // $ANTLR start "cropCmd"
    // Cmd.g:291:1: cropCmd returns [ASTCmd n] : 'crop' ( | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) ;
    public final ASTCmd cropCmd() throws RecognitionException {
        ASTCmd n = null;

        Token classname=null;
        Token ass=null;
        List objList = null;


        try {
            // Cmd.g:292:1: ( 'crop' ( | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) )
            // Cmd.g:293:2: 'crop' ( | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            {
            match(input,60,FOLLOW_60_in_cropCmd1074); if (state.failed) return n;
            // Cmd.g:293:9: ( | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            int alt16=3;
            switch ( input.LA(1) ) {
            case EOF:
            case SEMI:
            case 44:
            case 45:
            case 47:
            case 48:
            case 50:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 59:
            case 60:
                {
                alt16=1;
                }
                break;
            case IDENT:
                {
                alt16=2;
                }
                break;
            case 58:
                {
                alt16=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }

            switch (alt16) {
                case 1 :
                    // Cmd.g:294:4: 
                    {
                    }
                    break;
                case 2 :
                    // Cmd.g:294:6: objList= idList ( COLON classname= IDENT )?
                    {
                    pushFollow(FOLLOW_idList_in_cropCmd1087);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    // Cmd.g:294:23: ( COLON classname= IDENT )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==COLON) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // Cmd.g:294:24: COLON classname= IDENT
                            {
                            match(input,COLON,FOLLOW_COLON_in_cropCmd1090); if (state.failed) return n;
                            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_cropCmd1096); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideCropObjectsCmd(Mode.CROP, objList, classname); 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:295:6: 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT
                    {
                    match(input,58,FOLLOW_58_in_cropCmd1107); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_cropCmd1109); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_cropCmd1115);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_cropCmd1117); if (state.failed) return n;
                    match(input,51,FOLLOW_51_in_cropCmd1119); if (state.failed) return n;
                    ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_cropCmd1123); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideCropLinkObjectsCmd(Mode.CROP, ass, objList); 
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
    // $ANTLR end "cropCmd"


    // $ANTLR start "model"
    // Cmd.g:312:1: model returns [ASTModel n] : 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF ;
    public final ASTModel model() throws RecognitionException {
        ASTModel n = null;

        Token modelName=null;
        ASTEnumTypeDefinition e = null;

        ASTAssociation a = null;

        ASTConstraintDefinition cons = null;

        ASTPrePost ppc = null;


        try {
            // Cmd.g:313:1: ( 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF )
            // Cmd.g:314:5: 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF
            {
            match(input,61,FOLLOW_61_in_model1152); if (state.failed) return n;
            modelName=(Token)match(input,IDENT,FOLLOW_IDENT_in_model1156); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTModel(modelName); 
            }
            // Cmd.g:315:5: (e= enumTypeDefinition )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==63) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // Cmd.g:315:7: e= enumTypeDefinition
            	    {
            	    pushFollow(FOLLOW_enumTypeDefinition_in_model1168);
            	    e=enumTypeDefinition();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addEnumTypeDef(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);

            // Cmd.g:316:5: ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )*
            loop19:
            do {
                int alt19=4;
                switch ( input.LA(1) ) {
                case 64:
                case 65:
                case 69:
                case 70:
                    {
                    alt19=1;
                    }
                    break;
                case IDENT:
                case 71:
                case 72:
                    {
                    alt19=2;
                    }
                    break;
                case 62:
                    {
                    alt19=3;
                    }
                    break;

                }

                switch (alt19) {
            	case 1 :
            	    // Cmd.g:316:9: ( generalClassDefinition[$n] )
            	    {
            	    // Cmd.g:316:9: ( generalClassDefinition[$n] )
            	    // Cmd.g:316:11: generalClassDefinition[$n]
            	    {
            	    pushFollow(FOLLOW_generalClassDefinition_in_model1185);
            	    generalClassDefinition(n);

            	    state._fsp--;
            	    if (state.failed) return n;

            	    }


            	    }
            	    break;
            	case 2 :
            	    // Cmd.g:317:9: (a= associationDefinition )
            	    {
            	    // Cmd.g:317:9: (a= associationDefinition )
            	    // Cmd.g:317:11: a= associationDefinition
            	    {
            	    pushFollow(FOLLOW_associationDefinition_in_model1202);
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
            	    // Cmd.g:318:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    {
            	    // Cmd.g:318:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    // Cmd.g:318:11: 'constraints' (cons= invariant | ppc= prePost )*
            	    {
            	    match(input,62,FOLLOW_62_in_model1218); if (state.failed) return n;
            	    // Cmd.g:319:11: (cons= invariant | ppc= prePost )*
            	    loop18:
            	    do {
            	        int alt18=3;
            	        int LA18_0 = input.LA(1);

            	        if ( (LA18_0==77) ) {
            	            int LA18_2 = input.LA(2);

            	            if ( (LA18_2==IDENT) ) {
            	                int LA18_3 = input.LA(3);

            	                if ( (LA18_3==COLON_COLON) ) {
            	                    alt18=2;
            	                }
            	                else if ( (LA18_3==EOF||LA18_3==COLON||LA18_3==IDENT||LA18_3==COMMA||LA18_3==62||(LA18_3>=64 && LA18_3<=65)||(LA18_3>=69 && LA18_3<=72)||(LA18_3>=77 && LA18_3<=79)) ) {
            	                    alt18=1;
            	                }


            	            }


            	        }


            	        switch (alt18) {
            	    	case 1 :
            	    	    // Cmd.g:319:15: cons= invariant
            	    	    {
            	    	    pushFollow(FOLLOW_invariant_in_model1236);
            	    	    cons=invariant();

            	    	    state._fsp--;
            	    	    if (state.failed) return n;
            	    	    if ( state.backtracking==0 ) {
            	    	       n.addConstraint(cons); 
            	    	    }

            	    	    }
            	    	    break;
            	    	case 2 :
            	    	    // Cmd.g:320:15: ppc= prePost
            	    	    {
            	    	    pushFollow(FOLLOW_prePost_in_model1257);
            	    	    ppc=prePost();

            	    	    state._fsp--;
            	    	    if (state.failed) return n;
            	    	    if ( state.backtracking==0 ) {
            	    	       n.addPrePost(ppc); 
            	    	    }

            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop18;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);

            match(input,EOF,FOLLOW_EOF_in_model1298); if (state.failed) return n;

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
    // Cmd.g:331:1: enumTypeDefinition returns [ASTEnumTypeDefinition n] : 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )? ;
    public final ASTEnumTypeDefinition enumTypeDefinition() throws RecognitionException {
        ASTEnumTypeDefinition n = null;

        Token name=null;
        List idListRes = null;


        try {
            // Cmd.g:332:1: ( 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )? )
            // Cmd.g:333:5: 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )?
            {
            match(input,63,FOLLOW_63_in_enumTypeDefinition1325); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_enumTypeDefinition1329); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_enumTypeDefinition1331); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_enumTypeDefinition1335);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,RBRACE,FOLLOW_RBRACE_in_enumTypeDefinition1337); if (state.failed) return n;
            // Cmd.g:333:54: ( SEMI )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==SEMI) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // Cmd.g:333:56: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_enumTypeDefinition1341); if (state.failed) return n;

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
    // Cmd.g:342:1: generalClassDefinition[ASTModel n] : ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) ) ;
    public final void generalClassDefinition(ASTModel n) throws RecognitionException {
        ASTClass c = null;

        ASTAssociationClass ac = null;


         
          boolean isAbstract = false;

        try {
            // Cmd.g:346:1: ( ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) ) )
            // Cmd.g:347:5: ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) )
            {
            // Cmd.g:347:5: ( 'abstract' )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==64) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // Cmd.g:347:7: 'abstract'
                    {
                    match(input,64,FOLLOW_64_in_generalClassDefinition1380); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       isAbstract = true; 
                    }

                    }
                    break;

            }

            // Cmd.g:348:5: ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==65) ) {
                alt22=1;
            }
            else if ( ((LA22_0>=69 && LA22_0<=70)) ) {
                alt22=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // Cmd.g:348:7: (c= classDefinition[isAbstract] )
                    {
                    // Cmd.g:348:7: (c= classDefinition[isAbstract] )
                    // Cmd.g:348:9: c= classDefinition[isAbstract]
                    {
                    pushFollow(FOLLOW_classDefinition_in_generalClassDefinition1398);
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
                    // Cmd.g:349:7: (ac= associationClassDefinition[isAbstract] )
                    {
                    // Cmd.g:349:7: (ac= associationClassDefinition[isAbstract] )
                    // Cmd.g:349:9: ac= associationClassDefinition[isAbstract]
                    {
                    pushFollow(FOLLOW_associationClassDefinition_in_generalClassDefinition1418);
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
    // Cmd.g:366:1: classDefinition[boolean isAbstract] returns [ASTClass n] : 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end' ;
    public final ASTClass classDefinition(boolean isAbstract) throws RecognitionException {
        ASTClass n = null;

        Token name=null;
        List idListRes = null;

        ASTAttribute a = null;

        ASTOperation op = null;

        ASTInvariantClause inv = null;


         List idList; 
        try {
            // Cmd.g:368:1: ( 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end' )
            // Cmd.g:369:5: 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end'
            {
            match(input,65,FOLLOW_65_in_classDefinition1457); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_classDefinition1461); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTClass(name, isAbstract); 
            }
            // Cmd.g:370:5: ( LESS idListRes= idList )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==LESS) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // Cmd.g:370:7: LESS idListRes= idList
                    {
                    match(input,LESS,FOLLOW_LESS_in_classDefinition1471); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_classDefinition1475);
                    idListRes=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addSuperClasses(idListRes); 
                    }

                    }
                    break;

            }

            // Cmd.g:371:5: ( 'attributes' (a= attributeDefinition )* )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==66) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // Cmd.g:371:7: 'attributes' (a= attributeDefinition )*
                    {
                    match(input,66,FOLLOW_66_in_classDefinition1488); if (state.failed) return n;
                    // Cmd.g:372:7: (a= attributeDefinition )*
                    loop24:
                    do {
                        int alt24=2;
                        int LA24_0 = input.LA(1);

                        if ( (LA24_0==IDENT) ) {
                            alt24=1;
                        }


                        switch (alt24) {
                    	case 1 :
                    	    // Cmd.g:372:9: a= attributeDefinition
                    	    {
                    	    pushFollow(FOLLOW_attributeDefinition_in_classDefinition1501);
                    	    a=attributeDefinition();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addAttribute(a); 
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

            // Cmd.g:374:5: ( 'operations' (op= operationDefinition )* )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==67) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // Cmd.g:374:7: 'operations' (op= operationDefinition )*
                    {
                    match(input,67,FOLLOW_67_in_classDefinition1522); if (state.failed) return n;
                    // Cmd.g:375:7: (op= operationDefinition )*
                    loop26:
                    do {
                        int alt26=2;
                        int LA26_0 = input.LA(1);

                        if ( (LA26_0==IDENT) ) {
                            alt26=1;
                        }


                        switch (alt26) {
                    	case 1 :
                    	    // Cmd.g:375:9: op= operationDefinition
                    	    {
                    	    pushFollow(FOLLOW_operationDefinition_in_classDefinition1535);
                    	    op=operationDefinition();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addOperation(op); 
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

            // Cmd.g:377:5: ( 'constraints' (inv= invariantClause )* )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==62) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // Cmd.g:377:7: 'constraints' (inv= invariantClause )*
                    {
                    match(input,62,FOLLOW_62_in_classDefinition1556); if (state.failed) return n;
                    // Cmd.g:378:7: (inv= invariantClause )*
                    loop28:
                    do {
                        int alt28=2;
                        int LA28_0 = input.LA(1);

                        if ( ((LA28_0>=78 && LA28_0<=79)) ) {
                            alt28=1;
                        }


                        switch (alt28) {
                    	case 1 :
                    	    // Cmd.g:379:9: inv= invariantClause
                    	    {
                    	    pushFollow(FOLLOW_invariantClause_in_classDefinition1576);
                    	    inv=invariantClause();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addInvariantClause(inv); 
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

            match(input,68,FOLLOW_68_in_classDefinition1600); if (state.failed) return n;

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
    // Cmd.g:400:1: associationClassDefinition[boolean isAbstract] returns [ASTAssociationClass n] : classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end' ;
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
            // Cmd.g:402:1: (classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end' )
            // Cmd.g:403:5: classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end'
            {
            classKW=(Token)input.LT(1);
            if ( (input.LA(1)>=69 && input.LA(1)<=70) ) {
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
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationClassDefinition1659); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociationClass(name, isAbstract); 
            }
            // Cmd.g:412:5: ( LESS idListRes= idList )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==LESS) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // Cmd.g:412:7: LESS idListRes= idList
                    {
                    match(input,LESS,FOLLOW_LESS_in_associationClassDefinition1669); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_associationClassDefinition1673);
                    idListRes=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addSuperClasses(idListRes); 
                    }

                    }
                    break;

            }

            match(input,46,FOLLOW_46_in_associationClassDefinition1684); if (state.failed) return n;
            pushFollow(FOLLOW_associationEnd_in_associationClassDefinition1692);
            ae=associationEnd();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addEnd(ae); 
            }
            // Cmd.g:415:5: (ae= associationEnd )+
            int cnt31=0;
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==IDENT) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // Cmd.g:415:7: ae= associationEnd
            	    {
            	    pushFollow(FOLLOW_associationEnd_in_associationClassDefinition1704);
            	    ae=associationEnd();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addEnd(ae); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt31 >= 1 ) break loop31;
            	    if (state.backtracking>0) {state.failed=true; return n;}
                        EarlyExitException eee =
                            new EarlyExitException(31, input);
                        throw eee;
                }
                cnt31++;
            } while (true);

            // Cmd.g:416:5: ( 'attributes' (a= attributeDefinition )* )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==66) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // Cmd.g:416:7: 'attributes' (a= attributeDefinition )*
                    {
                    match(input,66,FOLLOW_66_in_associationClassDefinition1717); if (state.failed) return n;
                    // Cmd.g:417:7: (a= attributeDefinition )*
                    loop32:
                    do {
                        int alt32=2;
                        int LA32_0 = input.LA(1);

                        if ( (LA32_0==IDENT) ) {
                            alt32=1;
                        }


                        switch (alt32) {
                    	case 1 :
                    	    // Cmd.g:417:9: a= attributeDefinition
                    	    {
                    	    pushFollow(FOLLOW_attributeDefinition_in_associationClassDefinition1730);
                    	    a=attributeDefinition();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addAttribute(a); 
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

            // Cmd.g:419:5: ( 'operations' (op= operationDefinition )* )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==67) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // Cmd.g:419:7: 'operations' (op= operationDefinition )*
                    {
                    match(input,67,FOLLOW_67_in_associationClassDefinition1751); if (state.failed) return n;
                    // Cmd.g:420:7: (op= operationDefinition )*
                    loop34:
                    do {
                        int alt34=2;
                        int LA34_0 = input.LA(1);

                        if ( (LA34_0==IDENT) ) {
                            alt34=1;
                        }


                        switch (alt34) {
                    	case 1 :
                    	    // Cmd.g:420:9: op= operationDefinition
                    	    {
                    	    pushFollow(FOLLOW_operationDefinition_in_associationClassDefinition1764);
                    	    op=operationDefinition();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addOperation(op); 
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

            // Cmd.g:422:5: ( 'constraints' (inv= invariantClause )* )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==62) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // Cmd.g:422:7: 'constraints' (inv= invariantClause )*
                    {
                    match(input,62,FOLLOW_62_in_associationClassDefinition1785); if (state.failed) return n;
                    // Cmd.g:423:7: (inv= invariantClause )*
                    loop36:
                    do {
                        int alt36=2;
                        int LA36_0 = input.LA(1);

                        if ( ((LA36_0>=78 && LA36_0<=79)) ) {
                            alt36=1;
                        }


                        switch (alt36) {
                    	case 1 :
                    	    // Cmd.g:424:9: inv= invariantClause
                    	    {
                    	    pushFollow(FOLLOW_invariantClause_in_associationClassDefinition1805);
                    	    inv=invariantClause();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addInvariantClause(inv); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop36;
                        }
                    } while (true);


                    }
                    break;

            }

            // Cmd.g:427:5: ( ( 'aggregation' | 'composition' ) )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( ((LA38_0>=71 && LA38_0<=72)) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // Cmd.g:427:7: ( 'aggregation' | 'composition' )
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

                    if ( state.backtracking==0 ) {
                       n.setKind(t); 
                    }

                    }
                    break;

            }

            match(input,68,FOLLOW_68_in_associationClassDefinition1868); if (state.failed) return n;

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
    // Cmd.g:438:1: attributeDefinition returns [ASTAttribute n] : name= IDENT COLON t= type ( SEMI )? ;
    public final ASTAttribute attributeDefinition() throws RecognitionException {
        ASTAttribute n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Cmd.g:439:1: (name= IDENT COLON t= type ( SEMI )? )
            // Cmd.g:440:5: name= IDENT COLON t= type ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_attributeDefinition1897); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_attributeDefinition1899); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_attributeDefinition1903);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            // Cmd.g:440:29: ( SEMI )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==SEMI) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // Cmd.g:440:31: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_attributeDefinition1907); if (state.failed) return n;

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
    // Cmd.g:449:1: operationDefinition returns [ASTOperation n] : name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression | EQUAL 'script' body= SCRIPTBODY )? (ppc= prePostClause )* ( SEMI )? ;
    public final ASTOperation operationDefinition() throws RecognitionException {
        ASTOperation n = null;

        Token name=null;
        Token body=null;
        List pl = null;

        ASTType t = null;

        ASTExpression e = null;

        ASTPrePostClause ppc = null;


        try {
            // Cmd.g:450:1: (name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression | EQUAL 'script' body= SCRIPTBODY )? (ppc= prePostClause )* ( SEMI )? )
            // Cmd.g:451:5: name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression | EQUAL 'script' body= SCRIPTBODY )? (ppc= prePostClause )* ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationDefinition1945); if (state.failed) return n;
            pushFollow(FOLLOW_paramList_in_operationDefinition1953);
            pl=paramList();

            state._fsp--;
            if (state.failed) return n;
            // Cmd.g:453:5: ( COLON t= type )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==COLON) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // Cmd.g:453:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_operationDefinition1961); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_operationDefinition1965);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTOperation(name, pl, t); 
            }
            // Cmd.g:456:5: ( EQUAL e= expression | EQUAL 'script' body= SCRIPTBODY )?
            int alt41=3;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==EQUAL) ) {
                int LA41_1 = input.LA(2);

                if ( (LA41_1==73) ) {
                    alt41=2;
                }
                else if ( ((LA41_1>=IDENT && LA41_1<=LPAREN)||LA41_1==INT||(LA41_1>=PLUS && LA41_1<=MINUS)||(LA41_1>=REAL && LA41_1<=HASH)||LA41_1==55||LA41_1==88||(LA41_1>=90 && LA41_1<=94)||(LA41_1>=98 && LA41_1<=109)) ) {
                    alt41=1;
                }
            }
            switch (alt41) {
                case 1 :
                    // Cmd.g:457:6: EQUAL e= expression
                    {
                    match(input,EQUAL,FOLLOW_EQUAL_in_operationDefinition1993); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_operationDefinition1997);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setExpression(e); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:460:6: EQUAL 'script' body= SCRIPTBODY
                    {
                    match(input,EQUAL,FOLLOW_EQUAL_in_operationDefinition2019); if (state.failed) return n;
                    match(input,73,FOLLOW_73_in_operationDefinition2021); if (state.failed) return n;
                    body=(Token)match(input,SCRIPTBODY,FOLLOW_SCRIPTBODY_in_operationDefinition2025); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setScript(body); 
                    }

                    }
                    break;

            }

            // Cmd.g:464:5: (ppc= prePostClause )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( ((LA42_0>=80 && LA42_0<=81)) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // Cmd.g:464:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_operationDefinition2056);
            	    ppc=prePostClause();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addPrePostClause(ppc); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop42;
                }
            } while (true);

            // Cmd.g:465:5: ( SEMI )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==SEMI) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // Cmd.g:465:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_operationDefinition2069); if (state.failed) return n;

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
    // Cmd.g:475:1: associationDefinition returns [ASTAssociation n] : ( keyAssociation | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end' ;
    public final ASTAssociation associationDefinition() throws RecognitionException {
        ASTAssociation n = null;

        Token name=null;
        ASTAssociationEnd ae = null;


         Token t = null; 
        try {
            // Cmd.g:477:1: ( ( keyAssociation | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end' )
            // Cmd.g:478:5: ( keyAssociation | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end'
            {
            if ( state.backtracking==0 ) {
               t = input.LT(1); 
            }
            // Cmd.g:479:5: ( keyAssociation | 'aggregation' | 'composition' )
            int alt44=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt44=1;
                }
                break;
            case 71:
                {
                alt44=2;
                }
                break;
            case 72:
                {
                alt44=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 44, 0, input);

                throw nvae;
            }

            switch (alt44) {
                case 1 :
                    // Cmd.g:479:7: keyAssociation
                    {
                    pushFollow(FOLLOW_keyAssociation_in_associationDefinition2107);
                    keyAssociation();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 2 :
                    // Cmd.g:479:24: 'aggregation'
                    {
                    match(input,71,FOLLOW_71_in_associationDefinition2111); if (state.failed) return n;

                    }
                    break;
                case 3 :
                    // Cmd.g:479:40: 'composition'
                    {
                    match(input,72,FOLLOW_72_in_associationDefinition2115); if (state.failed) return n;

                    }
                    break;

            }

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationDefinition2130); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociation(t, name); 
            }
            match(input,46,FOLLOW_46_in_associationDefinition2138); if (state.failed) return n;
            pushFollow(FOLLOW_associationEnd_in_associationDefinition2146);
            ae=associationEnd();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addEnd(ae); 
            }
            // Cmd.g:484:5: (ae= associationEnd )+
            int cnt45=0;
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==IDENT) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // Cmd.g:484:7: ae= associationEnd
            	    {
            	    pushFollow(FOLLOW_associationEnd_in_associationDefinition2158);
            	    ae=associationEnd();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addEnd(ae); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt45 >= 1 ) break loop45;
            	    if (state.backtracking>0) {state.failed=true; return n;}
                        EarlyExitException eee =
                            new EarlyExitException(45, input);
                        throw eee;
                }
                cnt45++;
            } while (true);

            match(input,68,FOLLOW_68_in_associationDefinition2169); if (state.failed) return n;

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
    // Cmd.g:493:1: associationEnd returns [ASTAssociationEnd n] : name= IDENT LBRACK m= multiplicity RBRACK ( keyRole rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )? ;
    public final ASTAssociationEnd associationEnd() throws RecognitionException {
        ASTAssociationEnd n = null;

        Token name=null;
        Token rn=null;
        Token sr=null;
        Token rd=null;
        ASTMultiplicity m = null;


        try {
            // Cmd.g:494:1: (name= IDENT LBRACK m= multiplicity RBRACK ( keyRole rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )? )
            // Cmd.g:495:5: name= IDENT LBRACK m= multiplicity RBRACK ( keyRole rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd2195); if (state.failed) return n;
            match(input,LBRACK,FOLLOW_LBRACK_in_associationEnd2197); if (state.failed) return n;
            pushFollow(FOLLOW_multiplicity_in_associationEnd2201);
            m=multiplicity();

            state._fsp--;
            if (state.failed) return n;
            match(input,RBRACK,FOLLOW_RBRACK_in_associationEnd2203); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociationEnd(name, m); 
            }
            // Cmd.g:496:5: ( keyRole rn= IDENT )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==IDENT) ) {
                int LA46_1 = input.LA(2);

                if ( (LA46_1==IDENT) ) {
                    int LA46_3 = input.LA(3);

                    if ( ((input.LT(1).getText().equals("role"))) ) {
                        alt46=1;
                    }
                }
            }
            switch (alt46) {
                case 1 :
                    // Cmd.g:496:7: keyRole rn= IDENT
                    {
                    pushFollow(FOLLOW_keyRole_in_associationEnd2214);
                    keyRole();

                    state._fsp--;
                    if (state.failed) return n;
                    rn=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd2218); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setRolename(rn); 
                    }

                    }
                    break;

            }

            // Cmd.g:497:5: ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )*
            loop47:
            do {
                int alt47=5;
                switch ( input.LA(1) ) {
                case IDENT:
                    {
                    int LA47_2 = input.LA(2);

                    if ( (LA47_2==SEMI||LA47_2==IDENT||LA47_2==62||(LA47_2>=66 && LA47_2<=68)||(LA47_2>=71 && LA47_2<=72)||(LA47_2>=74 && LA47_2<=76)) ) {
                        alt47=3;
                    }


                    }
                    break;
                case 74:
                    {
                    alt47=1;
                    }
                    break;
                case 75:
                    {
                    alt47=2;
                    }
                    break;
                case 76:
                    {
                    alt47=4;
                    }
                    break;

                }

                switch (alt47) {
            	case 1 :
            	    // Cmd.g:498:9: 'ordered'
            	    {
            	    match(input,74,FOLLOW_74_in_associationEnd2239); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.setOrdered(); 
            	    }

            	    }
            	    break;
            	case 2 :
            	    // Cmd.g:499:9: 'subsets' sr= IDENT
            	    {
            	    match(input,75,FOLLOW_75_in_associationEnd2251); if (state.failed) return n;
            	    sr=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd2255); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addSubsetsRolename(sr); 
            	    }

            	    }
            	    break;
            	case 3 :
            	    // Cmd.g:500:9: keyUnion
            	    {
            	    pushFollow(FOLLOW_keyUnion_in_associationEnd2267);
            	    keyUnion();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.setUnion(true); 
            	    }

            	    }
            	    break;
            	case 4 :
            	    // Cmd.g:501:9: 'redefines' rd= IDENT
            	    {
            	    match(input,76,FOLLOW_76_in_associationEnd2279); if (state.failed) return n;
            	    rd=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd2283); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addRedefinesRolename(rd); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop47;
                }
            } while (true);

            // Cmd.g:503:5: ( SEMI )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==SEMI) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // Cmd.g:503:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_associationEnd2300); if (state.failed) return n;

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
    // Cmd.g:517:1: multiplicity returns [ASTMultiplicity n] : mr= multiplicityRange ( COMMA mr= multiplicityRange )* ;
    public final ASTMultiplicity multiplicity() throws RecognitionException {
        ASTMultiplicity n = null;

        ASTMultiplicityRange mr = null;


        try {
            // Cmd.g:518:1: (mr= multiplicityRange ( COMMA mr= multiplicityRange )* )
            // Cmd.g:519:5: mr= multiplicityRange ( COMMA mr= multiplicityRange )*
            {
            if ( state.backtracking==0 ) {
               
              	Token t = input.LT(1); // remember start position of expression
              	n = new ASTMultiplicity(t);
                  
            }
            pushFollow(FOLLOW_multiplicityRange_in_multiplicity2335);
            mr=multiplicityRange();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addRange(mr); 
            }
            // Cmd.g:524:5: ( COMMA mr= multiplicityRange )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==COMMA) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // Cmd.g:524:7: COMMA mr= multiplicityRange
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_multiplicity2345); if (state.failed) return n;
            	    pushFollow(FOLLOW_multiplicityRange_in_multiplicity2349);
            	    mr=multiplicityRange();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addRange(mr); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop49;
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
    // Cmd.g:527:1: multiplicityRange returns [ASTMultiplicityRange n] : ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? ;
    public final ASTMultiplicityRange multiplicityRange() throws RecognitionException {
        ASTMultiplicityRange n = null;

        int ms1 = 0;

        int ms2 = 0;


        try {
            // Cmd.g:528:1: (ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? )
            // Cmd.g:529:5: ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )?
            {
            pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange2378);
            ms1=multiplicitySpec();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTMultiplicityRange(ms1); 
            }
            // Cmd.g:530:5: ( DOTDOT ms2= multiplicitySpec )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==DOTDOT) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // Cmd.g:530:7: DOTDOT ms2= multiplicitySpec
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_multiplicityRange2388); if (state.failed) return n;
                    pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange2392);
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
    // Cmd.g:533:1: multiplicitySpec returns [int m] : (i= INT | STAR );
    public final int multiplicitySpec() throws RecognitionException {
        int m = 0;

        Token i=null;

         m = -1; 
        try {
            // Cmd.g:535:1: (i= INT | STAR )
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==INT) ) {
                alt51=1;
            }
            else if ( (LA51_0==STAR) ) {
                alt51=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return m;}
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;
            }
            switch (alt51) {
                case 1 :
                    // Cmd.g:536:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_multiplicitySpec2426); if (state.failed) return m;
                    if ( state.backtracking==0 ) {
                       m = Integer.parseInt((i!=null?i.getText():null)); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:537:7: STAR
                    {
                    match(input,STAR,FOLLOW_STAR_in_multiplicitySpec2436); if (state.failed) return m;
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
    // Cmd.g:558:1: invariant returns [ASTConstraintDefinition n] : 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )* ;
    public final ASTConstraintDefinition invariant() throws RecognitionException {
        ASTConstraintDefinition n = null;

        Token v=null;
        ASTSimpleType t = null;

        ASTInvariantClause inv = null;


        try {
            // Cmd.g:559:1: ( 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )* )
            // Cmd.g:560:5: 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )*
            {
            if ( state.backtracking==0 ) {
               n = new ASTConstraintDefinition(); 
            }
            match(input,77,FOLLOW_77_in_invariant2477); if (state.failed) return n;
            // Cmd.g:562:5: (v= IDENT ( ',' v= IDENT )* COLON )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==IDENT) ) {
                int LA53_1 = input.LA(2);

                if ( (LA53_1==COLON||LA53_1==COMMA) ) {
                    alt53=1;
                }
            }
            switch (alt53) {
                case 1 :
                    // Cmd.g:562:7: v= IDENT ( ',' v= IDENT )* COLON
                    {
                    v=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariant2487); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addVarName(v); 
                    }
                    // Cmd.g:563:8: ( ',' v= IDENT )*
                    loop52:
                    do {
                        int alt52=2;
                        int LA52_0 = input.LA(1);

                        if ( (LA52_0==COMMA) ) {
                            alt52=1;
                        }


                        switch (alt52) {
                    	case 1 :
                    	    // Cmd.g:563:9: ',' v= IDENT
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_invariant2500); if (state.failed) return n;
                    	    v=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariant2504); if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addVarName(v); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop52;
                        }
                    } while (true);

                    match(input,COLON,FOLLOW_COLON_in_invariant2512); if (state.failed) return n;

                    }
                    break;

            }

            pushFollow(FOLLOW_simpleType_in_invariant2524);
            t=simpleType();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setType(t); 
            }
            // Cmd.g:565:5: (inv= invariantClause )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( ((LA54_0>=78 && LA54_0<=79)) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // Cmd.g:565:7: inv= invariantClause
            	    {
            	    pushFollow(FOLLOW_invariantClause_in_invariant2536);
            	    inv=invariantClause();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addInvariantClause(inv); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop54;
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
    // Cmd.g:572:1: invariantClause returns [ASTInvariantClause n] : ( 'inv' (name= IDENT )? COLON e= expression | 'existential' 'inv' (name= IDENT )? COLON e= expression );
    public final ASTInvariantClause invariantClause() throws RecognitionException {
        ASTInvariantClause n = null;

        Token name=null;
        ASTExpression e = null;


        try {
            // Cmd.g:573:1: ( 'inv' (name= IDENT )? COLON e= expression | 'existential' 'inv' (name= IDENT )? COLON e= expression )
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==78) ) {
                alt57=1;
            }
            else if ( (LA57_0==79) ) {
                alt57=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 57, 0, input);

                throw nvae;
            }
            switch (alt57) {
                case 1 :
                    // Cmd.g:574:7: 'inv' (name= IDENT )? COLON e= expression
                    {
                    match(input,78,FOLLOW_78_in_invariantClause2567); if (state.failed) return n;
                    // Cmd.g:574:13: (name= IDENT )?
                    int alt55=2;
                    int LA55_0 = input.LA(1);

                    if ( (LA55_0==IDENT) ) {
                        alt55=1;
                    }
                    switch (alt55) {
                        case 1 :
                            // Cmd.g:574:15: name= IDENT
                            {
                            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantClause2573); if (state.failed) return n;

                            }
                            break;

                    }

                    match(input,COLON,FOLLOW_COLON_in_invariantClause2578); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_invariantClause2582);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTInvariantClause(name, e); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:575:7: 'existential' 'inv' (name= IDENT )? COLON e= expression
                    {
                    match(input,79,FOLLOW_79_in_invariantClause2592); if (state.failed) return n;
                    match(input,78,FOLLOW_78_in_invariantClause2594); if (state.failed) return n;
                    // Cmd.g:575:27: (name= IDENT )?
                    int alt56=2;
                    int LA56_0 = input.LA(1);

                    if ( (LA56_0==IDENT) ) {
                        alt56=1;
                    }
                    switch (alt56) {
                        case 1 :
                            // Cmd.g:575:29: name= IDENT
                            {
                            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantClause2600); if (state.failed) return n;

                            }
                            break;

                    }

                    match(input,COLON,FOLLOW_COLON_in_invariantClause2605); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_invariantClause2609);
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
    // Cmd.g:586:1: prePost returns [ASTPrePost n] : 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ ;
    public final ASTPrePost prePost() throws RecognitionException {
        ASTPrePost n = null;

        Token classname=null;
        Token opname=null;
        List pl = null;

        ASTType rt = null;

        ASTPrePostClause ppc = null;


        try {
            // Cmd.g:587:1: ( 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ )
            // Cmd.g:588:5: 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+
            {
            match(input,77,FOLLOW_77_in_prePost2635); if (state.failed) return n;
            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost2639); if (state.failed) return n;
            match(input,COLON_COLON,FOLLOW_COLON_COLON_in_prePost2641); if (state.failed) return n;
            opname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost2645); if (state.failed) return n;
            pushFollow(FOLLOW_paramList_in_prePost2649);
            pl=paramList();

            state._fsp--;
            if (state.failed) return n;
            // Cmd.g:588:69: ( COLON rt= type )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==COLON) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // Cmd.g:588:71: COLON rt= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_prePost2653); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_prePost2657);
                    rt=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTPrePost(classname, opname, pl, rt); 
            }
            // Cmd.g:590:5: (ppc= prePostClause )+
            int cnt59=0;
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( ((LA59_0>=80 && LA59_0<=81)) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // Cmd.g:590:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_prePost2676);
            	    ppc=prePostClause();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addPrePostClause(ppc); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt59 >= 1 ) break loop59;
            	    if (state.backtracking>0) {state.failed=true; return n;}
                        EarlyExitException eee =
                            new EarlyExitException(59, input);
                        throw eee;
                }
                cnt59++;
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
    // Cmd.g:597:1: prePostClause returns [ASTPrePostClause n] : ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression ;
    public final ASTPrePostClause prePostClause() throws RecognitionException {
        ASTPrePostClause n = null;

        Token name=null;
        ASTExpression e = null;


         Token t = null; 
        try {
            // Cmd.g:599:1: ( ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression )
            // Cmd.g:600:5: ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression
            {
            if ( state.backtracking==0 ) {
               t = input.LT(1); 
            }
            if ( (input.LA(1)>=80 && input.LA(1)<=81) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // Cmd.g:601:25: (name= IDENT )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==IDENT) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // Cmd.g:601:27: name= IDENT
                    {
                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePostClause2730); if (state.failed) return n;

                    }
                    break;

            }

            match(input,COLON,FOLLOW_COLON_in_prePostClause2735); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_prePostClause2739);
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
    // Cmd.g:605:1: keyUnion : {...}? IDENT ;
    public final void keyUnion() throws RecognitionException {
        try {
            // Cmd.g:605:9: ({...}? IDENT )
            // Cmd.g:606:3: {...}? IDENT
            {
            if ( !((input.LT(1).getText().equals("union"))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "keyUnion", "input.LT(1).getText().equals(\"union\")");
            }
            match(input,IDENT,FOLLOW_IDENT_in_keyUnion2761); if (state.failed) return ;

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
    // Cmd.g:608:1: keyAssociation : {...}? IDENT ;
    public final void keyAssociation() throws RecognitionException {
        try {
            // Cmd.g:608:15: ({...}? IDENT )
            // Cmd.g:609:3: {...}? IDENT
            {
            if ( !((input.LT(1).getText().equals("association"))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "keyAssociation", "input.LT(1).getText().equals(\"association\")");
            }
            match(input,IDENT,FOLLOW_IDENT_in_keyAssociation2775); if (state.failed) return ;

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
    // Cmd.g:611:1: keyRole : {...}? IDENT ;
    public final void keyRole() throws RecognitionException {
        try {
            // Cmd.g:611:8: ({...}? IDENT )
            // Cmd.g:612:3: {...}? IDENT
            {
            if ( !((input.LT(1).getText().equals("role"))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "keyRole", "input.LT(1).getText().equals(\"role\")");
            }
            match(input,IDENT,FOLLOW_IDENT_in_keyRole2789); if (state.failed) return ;

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
    // Cmd.g:642:1: expressionOnly returns [ASTExpression n] : nExp= expression EOF ;
    public final ASTExpression expressionOnly() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExp = null;


        try {
            // Cmd.g:643:1: (nExp= expression EOF )
            // Cmd.g:644:5: nExp= expression EOF
            {
            pushFollow(FOLLOW_expression_in_expressionOnly2819);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_expressionOnly2821); if (state.failed) return n;
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
    // Cmd.g:651:1: expression returns [ASTExpression n] : ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression ;
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
            // Cmd.g:657:1: ( ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression )
            // Cmd.g:658:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of expression */ 
            }
            // Cmd.g:659:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )*
            loop62:
            do {
                int alt62=2;
                int LA62_0 = input.LA(1);

                if ( (LA62_0==55) ) {
                    alt62=1;
                }


                switch (alt62) {
            	case 1 :
            	    // Cmd.g:660:7: 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in'
            	    {
            	    match(input,55,FOLLOW_55_in_expression2869); if (state.failed) return n;
            	    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_expression2873); if (state.failed) return n;
            	    // Cmd.g:660:24: ( COLON t= type )?
            	    int alt61=2;
            	    int LA61_0 = input.LA(1);

            	    if ( (LA61_0==COLON) ) {
            	        alt61=1;
            	    }
            	    switch (alt61) {
            	        case 1 :
            	            // Cmd.g:660:26: COLON t= type
            	            {
            	            match(input,COLON,FOLLOW_COLON_in_expression2877); if (state.failed) return n;
            	            pushFollow(FOLLOW_type_in_expression2881);
            	            t=type();

            	            state._fsp--;
            	            if (state.failed) return n;

            	            }
            	            break;

            	    }

            	    match(input,EQUAL,FOLLOW_EQUAL_in_expression2886); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_expression2890);
            	    e1=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    match(input,82,FOLLOW_82_in_expression2892); if (state.failed) return n;
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
            	    break loop62;
                }
            } while (true);

            pushFollow(FOLLOW_conditionalImpliesExpression_in_expression2917);
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
    // Cmd.g:688:1: paramList returns [List paramList] : LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN ;
    public final List paramList() throws RecognitionException {
        List paramList = null;

        ASTVariableDeclaration v = null;


         paramList = new ArrayList(); 
        try {
            // Cmd.g:690:1: ( LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN )
            // Cmd.g:691:5: LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN
            {
            match(input,LPAREN,FOLLOW_LPAREN_in_paramList2950); if (state.failed) return paramList;
            // Cmd.g:692:5: (v= variableDeclaration ( COMMA v= variableDeclaration )* )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==IDENT) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // Cmd.g:693:7: v= variableDeclaration ( COMMA v= variableDeclaration )*
                    {
                    pushFollow(FOLLOW_variableDeclaration_in_paramList2967);
                    v=variableDeclaration();

                    state._fsp--;
                    if (state.failed) return paramList;
                    if ( state.backtracking==0 ) {
                       paramList.add(v); 
                    }
                    // Cmd.g:694:7: ( COMMA v= variableDeclaration )*
                    loop63:
                    do {
                        int alt63=2;
                        int LA63_0 = input.LA(1);

                        if ( (LA63_0==COMMA) ) {
                            alt63=1;
                        }


                        switch (alt63) {
                    	case 1 :
                    	    // Cmd.g:694:9: COMMA v= variableDeclaration
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_paramList2979); if (state.failed) return paramList;
                    	    pushFollow(FOLLOW_variableDeclaration_in_paramList2983);
                    	    v=variableDeclaration();

                    	    state._fsp--;
                    	    if (state.failed) return paramList;
                    	    if ( state.backtracking==0 ) {
                    	       paramList.add(v); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop63;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_paramList3003); if (state.failed) return paramList;

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
    // Cmd.g:702:1: idList returns [List idList] : id0= IDENT ( COMMA idn= IDENT )* ;
    public final List idList() throws RecognitionException {
        List idList = null;

        Token id0=null;
        Token idn=null;

         idList = new ArrayList(); 
        try {
            // Cmd.g:704:1: (id0= IDENT ( COMMA idn= IDENT )* )
            // Cmd.g:705:5: id0= IDENT ( COMMA idn= IDENT )*
            {
            id0=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList3032); if (state.failed) return idList;
            if ( state.backtracking==0 ) {
               idList.add(id0); 
            }
            // Cmd.g:706:5: ( COMMA idn= IDENT )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( (LA65_0==COMMA) ) {
                    alt65=1;
                }


                switch (alt65) {
            	case 1 :
            	    // Cmd.g:706:7: COMMA idn= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_idList3042); if (state.failed) return idList;
            	    idn=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList3046); if (state.failed) return idList;
            	    if ( state.backtracking==0 ) {
            	       idList.add(idn); 
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
        return idList;
    }
    // $ANTLR end "idList"


    // $ANTLR start "variableDeclaration"
    // Cmd.g:714:1: variableDeclaration returns [ASTVariableDeclaration n] : name= IDENT COLON t= type ;
    public final ASTVariableDeclaration variableDeclaration() throws RecognitionException {
        ASTVariableDeclaration n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Cmd.g:715:1: (name= IDENT COLON t= type )
            // Cmd.g:716:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableDeclaration3077); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableDeclaration3079); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableDeclaration3083);
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
    // Cmd.g:724:1: conditionalImpliesExpression returns [ASTExpression n] : nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* ;
    public final ASTExpression conditionalImpliesExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndOrExp = null;

        ASTExpression n1 = null;


        try {
            // Cmd.g:725:1: (nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* )
            // Cmd.g:726:5: nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )*
            {
            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3119);
            nCndOrExp=conditionalOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndOrExp;
            }
            // Cmd.g:727:5: (op= 'implies' n1= conditionalOrExpression )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( (LA66_0==83) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // Cmd.g:727:7: op= 'implies' n1= conditionalOrExpression
            	    {
            	    op=(Token)match(input,83,FOLLOW_83_in_conditionalImpliesExpression3132); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3136);
            	    n1=conditionalOrExpression();

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
    // $ANTLR end "conditionalImpliesExpression"


    // $ANTLR start "conditionalOrExpression"
    // Cmd.g:736:1: conditionalOrExpression returns [ASTExpression n] : nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* ;
    public final ASTExpression conditionalOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndXorExp = null;

        ASTExpression n1 = null;


        try {
            // Cmd.g:737:1: (nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* )
            // Cmd.g:738:5: nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )*
            {
            pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3181);
            nCndXorExp=conditionalXOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndXorExp;
            }
            // Cmd.g:739:5: (op= 'or' n1= conditionalXOrExpression )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==84) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // Cmd.g:739:7: op= 'or' n1= conditionalXOrExpression
            	    {
            	    op=(Token)match(input,84,FOLLOW_84_in_conditionalOrExpression3194); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3198);
            	    n1=conditionalXOrExpression();

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
    // $ANTLR end "conditionalOrExpression"


    // $ANTLR start "conditionalXOrExpression"
    // Cmd.g:748:1: conditionalXOrExpression returns [ASTExpression n] : nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* ;
    public final ASTExpression conditionalXOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndAndExp = null;

        ASTExpression n1 = null;


        try {
            // Cmd.g:749:1: (nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* )
            // Cmd.g:750:5: nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )*
            {
            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3242);
            nCndAndExp=conditionalAndExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndAndExp;
            }
            // Cmd.g:751:5: (op= 'xor' n1= conditionalAndExpression )*
            loop68:
            do {
                int alt68=2;
                int LA68_0 = input.LA(1);

                if ( (LA68_0==85) ) {
                    alt68=1;
                }


                switch (alt68) {
            	case 1 :
            	    // Cmd.g:751:7: op= 'xor' n1= conditionalAndExpression
            	    {
            	    op=(Token)match(input,85,FOLLOW_85_in_conditionalXOrExpression3255); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3259);
            	    n1=conditionalAndExpression();

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
    // $ANTLR end "conditionalXOrExpression"


    // $ANTLR start "conditionalAndExpression"
    // Cmd.g:760:1: conditionalAndExpression returns [ASTExpression n] : nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* ;
    public final ASTExpression conditionalAndExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nEqExp = null;

        ASTExpression n1 = null;


        try {
            // Cmd.g:761:1: (nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* )
            // Cmd.g:762:5: nEqExp= equalityExpression (op= 'and' n1= equalityExpression )*
            {
            pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression3303);
            nEqExp=equalityExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nEqExp;
            }
            // Cmd.g:763:5: (op= 'and' n1= equalityExpression )*
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==86) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // Cmd.g:763:7: op= 'and' n1= equalityExpression
            	    {
            	    op=(Token)match(input,86,FOLLOW_86_in_conditionalAndExpression3316); if (state.failed) return n;
            	    pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression3320);
            	    n1=equalityExpression();

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
    // $ANTLR end "conditionalAndExpression"


    // $ANTLR start "equalityExpression"
    // Cmd.g:772:1: equalityExpression returns [ASTExpression n] : nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* ;
    public final ASTExpression equalityExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nRelExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Cmd.g:774:1: (nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* )
            // Cmd.g:775:5: nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            {
            pushFollow(FOLLOW_relationalExpression_in_equalityExpression3368);
            nRelExp=relationalExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nRelExp;
            }
            // Cmd.g:776:5: ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            loop70:
            do {
                int alt70=2;
                int LA70_0 = input.LA(1);

                if ( (LA70_0==EQUAL||LA70_0==NOT_EQUAL) ) {
                    alt70=1;
                }


                switch (alt70) {
            	case 1 :
            	    // Cmd.g:776:7: ( EQUAL | NOT_EQUAL ) n1= relationalExpression
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

            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression3397);
            	    n1=relationalExpression();

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
    // $ANTLR end "equalityExpression"


    // $ANTLR start "relationalExpression"
    // Cmd.g:786:1: relationalExpression returns [ASTExpression n] : nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* ;
    public final ASTExpression relationalExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nAddiExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Cmd.g:788:1: (nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* )
            // Cmd.g:789:5: nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            {
            pushFollow(FOLLOW_additiveExpression_in_relationalExpression3446);
            nAddiExp=additiveExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nAddiExp;
            }
            // Cmd.g:790:5: ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            loop71:
            do {
                int alt71=2;
                int LA71_0 = input.LA(1);

                if ( (LA71_0==LESS||(LA71_0>=GREATER && LA71_0<=GREATER_EQUAL)) ) {
                    alt71=1;
                }


                switch (alt71) {
            	case 1 :
            	    // Cmd.g:790:7: ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression
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

            	    pushFollow(FOLLOW_additiveExpression_in_relationalExpression3482);
            	    n1=additiveExpression();

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
    // $ANTLR end "relationalExpression"


    // $ANTLR start "additiveExpression"
    // Cmd.g:800:1: additiveExpression returns [ASTExpression n] : nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* ;
    public final ASTExpression additiveExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nMulExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Cmd.g:802:1: (nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* )
            // Cmd.g:803:5: nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            {
            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3532);
            nMulExp=multiplicativeExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nMulExp;
            }
            // Cmd.g:804:5: ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            loop72:
            do {
                int alt72=2;
                int LA72_0 = input.LA(1);

                if ( ((LA72_0>=PLUS && LA72_0<=MINUS)) ) {
                    alt72=1;
                }


                switch (alt72) {
            	case 1 :
            	    // Cmd.g:804:7: ( PLUS | MINUS ) n1= multiplicativeExpression
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

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3560);
            	    n1=multiplicativeExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

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
    // $ANTLR end "additiveExpression"


    // $ANTLR start "multiplicativeExpression"
    // Cmd.g:815:1: multiplicativeExpression returns [ASTExpression n] : nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* ;
    public final ASTExpression multiplicativeExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Cmd.g:817:1: (nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* )
            // Cmd.g:818:5: nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            {
            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3610);
            nUnExp=unaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nUnExp;
            }
            // Cmd.g:819:5: ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            loop73:
            do {
                int alt73=2;
                int LA73_0 = input.LA(1);

                if ( (LA73_0==STAR||LA73_0==SLASH||LA73_0==87) ) {
                    alt73=1;
                }


                switch (alt73) {
            	case 1 :
            	    // Cmd.g:819:7: ( STAR | SLASH | 'div' ) n1= unaryExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       op = input.LT(1); 
            	    }
            	    if ( input.LA(1)==STAR||input.LA(1)==SLASH||input.LA(1)==87 ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3642);
            	    n1=unaryExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

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
    // $ANTLR end "multiplicativeExpression"


    // $ANTLR start "unaryExpression"
    // Cmd.g:831:1: unaryExpression returns [ASTExpression n] : ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression );
    public final ASTExpression unaryExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression nPosExp = null;


         Token op = null; 
        try {
            // Cmd.g:833:1: ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression )
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( ((LA74_0>=PLUS && LA74_0<=MINUS)||LA74_0==88) ) {
                alt74=1;
            }
            else if ( ((LA74_0>=IDENT && LA74_0<=LPAREN)||LA74_0==INT||(LA74_0>=REAL && LA74_0<=HASH)||(LA74_0>=90 && LA74_0<=94)||(LA74_0>=98 && LA74_0<=109)) ) {
                alt74=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 74, 0, input);

                throw nvae;
            }
            switch (alt74) {
                case 1 :
                    // Cmd.g:834:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    {
                    // Cmd.g:834:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    // Cmd.g:834:9: ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression
                    {
                    if ( state.backtracking==0 ) {
                       op = input.LT(1); 
                    }
                    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS)||input.LA(1)==88 ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression3728);
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
                    // Cmd.g:838:7: nPosExp= postfixExpression
                    {
                    pushFollow(FOLLOW_postfixExpression_in_unaryExpression3748);
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
    // Cmd.g:846:1: postfixExpression returns [ASTExpression n] : nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* ;
    public final ASTExpression postfixExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nPrimExp = null;

        ASTExpression nPc = null;


         boolean arrow = false; 
        try {
            // Cmd.g:848:1: (nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* )
            // Cmd.g:849:5: nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            {
            pushFollow(FOLLOW_primaryExpression_in_postfixExpression3781);
            nPrimExp=primaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nPrimExp; 
            }
            // Cmd.g:850:5: ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            loop76:
            do {
                int alt76=2;
                int LA76_0 = input.LA(1);

                if ( ((LA76_0>=ARROW && LA76_0<=DOT)) ) {
                    alt76=1;
                }


                switch (alt76) {
            	case 1 :
            	    // Cmd.g:851:6: ( ARROW | DOT ) nPc= propertyCall[$n, arrow]
            	    {
            	    // Cmd.g:851:6: ( ARROW | DOT )
            	    int alt75=2;
            	    int LA75_0 = input.LA(1);

            	    if ( (LA75_0==ARROW) ) {
            	        alt75=1;
            	    }
            	    else if ( (LA75_0==DOT) ) {
            	        alt75=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 75, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt75) {
            	        case 1 :
            	            // Cmd.g:851:8: ARROW
            	            {
            	            match(input,ARROW,FOLLOW_ARROW_in_postfixExpression3799); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = true; 
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // Cmd.g:851:34: DOT
            	            {
            	            match(input,DOT,FOLLOW_DOT_in_postfixExpression3805); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = false; 
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_propertyCall_in_postfixExpression3816);
            	    nPc=propertyCall(n, arrow);

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = nPc; 
            	    }

            	    }
            	    break;

            	default :
            	    break loop76;
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
    // Cmd.g:867:1: primaryExpression returns [ASTExpression n] : (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? );
    public final ASTExpression primaryExpression() throws RecognitionException {
        ASTExpression n = null;

        Token id1=null;
        ASTExpression nLit = null;

        ASTExpression nPc = null;

        ASTExpression nExp = null;

        ASTExpression nIfExp = null;


        try {
            // Cmd.g:868:1: (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? )
            int alt79=5;
            switch ( input.LA(1) ) {
            case INT:
            case REAL:
            case STRING:
            case HASH:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
                {
                alt79=1;
                }
                break;
            case IDENT:
                {
                switch ( input.LA(2) ) {
                case COLON_COLON:
                    {
                    alt79=1;
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
                case 56:
                case 59:
                case 60:
                case 62:
                case 64:
                case 65:
                case 68:
                case 69:
                case 70:
                case 71:
                case 72:
                case 77:
                case 78:
                case 79:
                case 80:
                case 81:
                case 82:
                case 83:
                case 84:
                case 85:
                case 86:
                case 87:
                case 95:
                case 96:
                case 97:
                    {
                    alt79=2;
                    }
                    break;
                case DOT:
                    {
                    int LA79_6 = input.LA(3);

                    if ( (LA79_6==89) ) {
                        alt79=5;
                    }
                    else if ( (LA79_6==IDENT||(LA79_6>=90 && LA79_6<=93)) ) {
                        alt79=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 79, 6, input);

                        throw nvae;
                    }
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 79, 2, input);

                    throw nvae;
                }

                }
                break;
            case 90:
            case 91:
            case 92:
            case 93:
                {
                alt79=2;
                }
                break;
            case LPAREN:
                {
                alt79=3;
                }
                break;
            case 94:
                {
                alt79=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 79, 0, input);

                throw nvae;
            }

            switch (alt79) {
                case 1 :
                    // Cmd.g:869:7: nLit= literal
                    {
                    pushFollow(FOLLOW_literal_in_primaryExpression3856);
                    nLit=literal();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nLit; 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:870:7: nPc= propertyCall[null, false]
                    {
                    pushFollow(FOLLOW_propertyCall_in_primaryExpression3868);
                    nPc=propertyCall(null, false);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nPc; 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:871:7: LPAREN nExp= expression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression3879); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_primaryExpression3883);
                    nExp=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression3885); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExp; 
                    }

                    }
                    break;
                case 4 :
                    // Cmd.g:872:7: nIfExp= ifExpression
                    {
                    pushFollow(FOLLOW_ifExpression_in_primaryExpression3897);
                    nIfExp=ifExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nIfExp; 
                    }

                    }
                    break;
                case 5 :
                    // Cmd.g:874:7: id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )?
                    {
                    id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_primaryExpression3914); if (state.failed) return n;
                    match(input,DOT,FOLLOW_DOT_in_primaryExpression3916); if (state.failed) return n;
                    match(input,89,FOLLOW_89_in_primaryExpression3918); if (state.failed) return n;
                    // Cmd.g:874:36: ( LPAREN RPAREN )?
                    int alt77=2;
                    int LA77_0 = input.LA(1);

                    if ( (LA77_0==LPAREN) ) {
                        alt77=1;
                    }
                    switch (alt77) {
                        case 1 :
                            // Cmd.g:874:38: LPAREN RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression3922); if (state.failed) return n;
                            match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression3924); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTAllInstancesExpression(id1); 
                    }
                    // Cmd.g:876:7: ( AT 'pre' )?
                    int alt78=2;
                    int LA78_0 = input.LA(1);

                    if ( (LA78_0==AT) ) {
                        alt78=1;
                    }
                    switch (alt78) {
                        case 1 :
                            // Cmd.g:876:9: AT 'pre'
                            {
                            match(input,AT,FOLLOW_AT_in_primaryExpression3945); if (state.failed) return n;
                            match(input,80,FOLLOW_80_in_primaryExpression3947); if (state.failed) return n;
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
    // Cmd.g:889:1: propertyCall[ASTExpression source, boolean followsArrow] returns [ASTExpression n] : ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] );
    public final ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExpQuery = null;

        ASTExpression nExpIterate = null;

        ASTOperationExpression nExpOperation = null;

        ASTTypeArgExpression nExpType = null;


        try {
            // Cmd.g:890:1: ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] )
            int alt80=4;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA80_1 = input.LA(2);

                if ( ((( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )&&( input.LA(2) == LPAREN ))) ) {
                    alt80=1;
                }
                else if ( (true) ) {
                    alt80=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 80, 1, input);

                    throw nvae;
                }
                }
                break;
            case 90:
                {
                alt80=2;
                }
                break;
            case 91:
            case 92:
            case 93:
                {
                alt80=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 80, 0, input);

                throw nvae;
            }

            switch (alt80) {
                case 1 :
                    // Cmd.g:894:7: {...}?{...}?nExpQuery= queryExpression[source]
                    {
                    if ( !(( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) ");
                    }
                    if ( !(( input.LA(2) == LPAREN )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " input.LA(2) == LPAREN ");
                    }
                    pushFollow(FOLLOW_queryExpression_in_propertyCall4020);
                    nExpQuery=queryExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpQuery; 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:897:7: nExpIterate= iterateExpression[source]
                    {
                    pushFollow(FOLLOW_iterateExpression_in_propertyCall4033);
                    nExpIterate=iterateExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpIterate; 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:898:7: nExpOperation= operationExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_operationExpression_in_propertyCall4046);
                    nExpOperation=operationExpression(source, followsArrow);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpOperation; 
                    }

                    }
                    break;
                case 4 :
                    // Cmd.g:899:7: nExpType= typeExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_typeExpression_in_propertyCall4059);
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
    // Cmd.g:908:1: queryExpression[ASTExpression range] returns [ASTExpression n] : op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN ;
    public final ASTExpression queryExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTElemVarsDeclaration decls = null;

        ASTExpression nExp = null;


        ASTElemVarsDeclaration decl = new ASTElemVarsDeclaration(); 
        try {
            // Cmd.g:909:69: (op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN )
            // Cmd.g:910:5: op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN
            {
            op=(Token)match(input,IDENT,FOLLOW_IDENT_in_queryExpression4094); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_queryExpression4101); if (state.failed) return n;
            // Cmd.g:912:5: (decls= elemVarsDeclaration BAR )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==IDENT) ) {
                int LA81_1 = input.LA(2);

                if ( (LA81_1==COLON||LA81_1==COMMA||LA81_1==BAR) ) {
                    alt81=1;
                }
            }
            switch (alt81) {
                case 1 :
                    // Cmd.g:912:7: decls= elemVarsDeclaration BAR
                    {
                    pushFollow(FOLLOW_elemVarsDeclaration_in_queryExpression4112);
                    decls=elemVarsDeclaration();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      decl = decls;
                    }
                    match(input,BAR,FOLLOW_BAR_in_queryExpression4116); if (state.failed) return n;

                    }
                    break;

            }

            pushFollow(FOLLOW_expression_in_queryExpression4127);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_queryExpression4133); if (state.failed) return n;
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
    // Cmd.g:926:1: iterateExpression[ASTExpression range] returns [ASTExpression n] : i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN ;
    public final ASTExpression iterateExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTElemVarsDeclaration decls = null;

        ASTVariableInitialization init = null;

        ASTExpression nExp = null;


        try {
            // Cmd.g:926:65: (i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN )
            // Cmd.g:927:5: i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN
            {
            i=(Token)match(input,90,FOLLOW_90_in_iterateExpression4165); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_iterateExpression4171); if (state.failed) return n;
            pushFollow(FOLLOW_elemVarsDeclaration_in_iterateExpression4179);
            decls=elemVarsDeclaration();

            state._fsp--;
            if (state.failed) return n;
            match(input,SEMI,FOLLOW_SEMI_in_iterateExpression4181); if (state.failed) return n;
            pushFollow(FOLLOW_variableInitialization_in_iterateExpression4189);
            init=variableInitialization();

            state._fsp--;
            if (state.failed) return n;
            match(input,BAR,FOLLOW_BAR_in_iterateExpression4191); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_iterateExpression4199);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_iterateExpression4205); if (state.failed) return n;
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
    // Cmd.g:948:1: operationExpression[ASTExpression source, boolean followsArrow] returns [ASTOperationExpression n] : name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? ;
    public final ASTOperationExpression operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTOperationExpression n = null;

        Token name=null;
        Token rolename=null;
        ASTExpression e = null;


        try {
            // Cmd.g:950:1: (name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? )
            // Cmd.g:951:5: name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression4249); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTOperationExpression(name, source, followsArrow); 
            }
            // Cmd.g:954:5: ( LBRACK rolename= IDENT RBRACK )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==LBRACK) ) {
                alt82=1;
            }
            switch (alt82) {
                case 1 :
                    // Cmd.g:954:7: LBRACK rolename= IDENT RBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_operationExpression4265); if (state.failed) return n;
                    rolename=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression4269); if (state.failed) return n;
                    match(input,RBRACK,FOLLOW_RBRACK_in_operationExpression4271); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setExplicitRolename(rolename); 
                    }

                    }
                    break;

            }

            // Cmd.g:956:5: ( AT 'pre' )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==AT) ) {
                alt83=1;
            }
            switch (alt83) {
                case 1 :
                    // Cmd.g:956:7: AT 'pre'
                    {
                    match(input,AT,FOLLOW_AT_in_operationExpression4284); if (state.failed) return n;
                    match(input,80,FOLLOW_80_in_operationExpression4286); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setIsPre(); 
                    }

                    }
                    break;

            }

            // Cmd.g:957:5: ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==LPAREN) ) {
                alt86=1;
            }
            switch (alt86) {
                case 1 :
                    // Cmd.g:958:7: LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_operationExpression4307); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.hasParentheses(); 
                    }
                    // Cmd.g:959:7: (e= expression ( COMMA e= expression )* )?
                    int alt85=2;
                    int LA85_0 = input.LA(1);

                    if ( ((LA85_0>=IDENT && LA85_0<=LPAREN)||LA85_0==INT||(LA85_0>=PLUS && LA85_0<=MINUS)||(LA85_0>=REAL && LA85_0<=HASH)||LA85_0==55||LA85_0==88||(LA85_0>=90 && LA85_0<=94)||(LA85_0>=98 && LA85_0<=109)) ) {
                        alt85=1;
                    }
                    switch (alt85) {
                        case 1 :
                            // Cmd.g:960:7: e= expression ( COMMA e= expression )*
                            {
                            pushFollow(FOLLOW_expression_in_operationExpression4328);
                            e=expression();

                            state._fsp--;
                            if (state.failed) return n;
                            if ( state.backtracking==0 ) {
                               n.addArg(e); 
                            }
                            // Cmd.g:961:7: ( COMMA e= expression )*
                            loop84:
                            do {
                                int alt84=2;
                                int LA84_0 = input.LA(1);

                                if ( (LA84_0==COMMA) ) {
                                    alt84=1;
                                }


                                switch (alt84) {
                            	case 1 :
                            	    // Cmd.g:961:9: COMMA e= expression
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_operationExpression4340); if (state.failed) return n;
                            	    pushFollow(FOLLOW_expression_in_operationExpression4344);
                            	    e=expression();

                            	    state._fsp--;
                            	    if (state.failed) return n;
                            	    if ( state.backtracking==0 ) {
                            	       n.addArg(e); 
                            	    }

                            	    }
                            	    break;

                            	default :
                            	    break loop84;
                                }
                            } while (true);


                            }
                            break;

                    }

                    match(input,RPAREN,FOLLOW_RPAREN_in_operationExpression4364); if (state.failed) return n;

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
    // Cmd.g:973:1: typeExpression[ASTExpression source, boolean followsArrow] returns [ASTTypeArgExpression n] : ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN ;
    public final ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTTypeArgExpression n = null;

        ASTType t = null;


         Token opToken = null; 
        try {
            // Cmd.g:976:1: ( ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN )
            // Cmd.g:977:2: ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN
            {
            if ( state.backtracking==0 ) {
               opToken = input.LT(1); 
            }
            if ( (input.LA(1)>=91 && input.LA(1)<=93) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_typeExpression4423); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_typeExpression4427);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_typeExpression4429); if (state.failed) return n;
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
    // Cmd.g:988:1: elemVarsDeclaration returns [ASTElemVarsDeclaration n] : idListRes= idList ( COLON t= type )? ;
    public final ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException {
        ASTElemVarsDeclaration n = null;

        List idListRes = null;

        ASTType t = null;


         List idList; 
        try {
            // Cmd.g:990:1: (idListRes= idList ( COLON t= type )? )
            // Cmd.g:991:5: idListRes= idList ( COLON t= type )?
            {
            pushFollow(FOLLOW_idList_in_elemVarsDeclaration4468);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            // Cmd.g:992:5: ( COLON t= type )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==COLON) ) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    // Cmd.g:992:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_elemVarsDeclaration4476); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_elemVarsDeclaration4480);
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
    // Cmd.g:1001:1: variableInitialization returns [ASTVariableInitialization n] : name= IDENT COLON t= type EQUAL e= expression ;
    public final ASTVariableInitialization variableInitialization() throws RecognitionException {
        ASTVariableInitialization n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // Cmd.g:1002:1: (name= IDENT COLON t= type EQUAL e= expression )
            // Cmd.g:1003:5: name= IDENT COLON t= type EQUAL e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableInitialization4515); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableInitialization4517); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableInitialization4521);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EQUAL,FOLLOW_EQUAL_in_variableInitialization4523); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_variableInitialization4527);
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
    // Cmd.g:1012:1: ifExpression returns [ASTExpression n] : i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' ;
    public final ASTExpression ifExpression() throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTExpression cond = null;

        ASTExpression t = null;

        ASTExpression e = null;


        try {
            // Cmd.g:1013:1: (i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' )
            // Cmd.g:1014:5: i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif'
            {
            i=(Token)match(input,94,FOLLOW_94_in_ifExpression4559); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4563);
            cond=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,95,FOLLOW_95_in_ifExpression4565); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4569);
            t=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,96,FOLLOW_96_in_ifExpression4571); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4575);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,97,FOLLOW_97_in_ifExpression4577); if (state.failed) return n;
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
    // Cmd.g:1034:1: literal returns [ASTExpression n] : (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral );
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
            // Cmd.g:1035:1: (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral )
            int alt88=12;
            switch ( input.LA(1) ) {
            case 98:
                {
                alt88=1;
                }
                break;
            case 99:
                {
                alt88=2;
                }
                break;
            case INT:
                {
                alt88=3;
                }
                break;
            case REAL:
                {
                alt88=4;
                }
                break;
            case STRING:
                {
                alt88=5;
                }
                break;
            case HASH:
                {
                alt88=6;
                }
                break;
            case IDENT:
                {
                alt88=7;
                }
                break;
            case 100:
            case 101:
            case 102:
            case 103:
                {
                alt88=8;
                }
                break;
            case 104:
                {
                alt88=9;
                }
                break;
            case 105:
            case 106:
            case 107:
                {
                alt88=10;
                }
                break;
            case 108:
                {
                alt88=11;
                }
                break;
            case 109:
                {
                alt88=12;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 88, 0, input);

                throw nvae;
            }

            switch (alt88) {
                case 1 :
                    // Cmd.g:1036:7: t= 'true'
                    {
                    t=(Token)match(input,98,FOLLOW_98_in_literal4616); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(true); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:1037:7: f= 'false'
                    {
                    f=(Token)match(input,99,FOLLOW_99_in_literal4630); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(false); 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:1038:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_literal4643); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTIntegerLiteral(i); 
                    }

                    }
                    break;
                case 4 :
                    // Cmd.g:1039:7: r= REAL
                    {
                    r=(Token)match(input,REAL,FOLLOW_REAL_in_literal4658); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTRealLiteral(r); 
                    }

                    }
                    break;
                case 5 :
                    // Cmd.g:1040:7: s= STRING
                    {
                    s=(Token)match(input,STRING,FOLLOW_STRING_in_literal4672); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTStringLiteral(s); 
                    }

                    }
                    break;
                case 6 :
                    // Cmd.g:1041:7: HASH enumLit= IDENT
                    {
                    match(input,HASH,FOLLOW_HASH_in_literal4682); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal4686); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumLit);  reportWarning("the usage of #enumerationLiteral is deprecated and will not be supported in the future, use 'Enumeration::Literal' instead");
                    }

                    }
                    break;
                case 7 :
                    // Cmd.g:1042:7: enumName= IDENT '::' enumLit= IDENT
                    {
                    enumName=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal4698); if (state.failed) return n;
                    match(input,COLON_COLON,FOLLOW_COLON_COLON_in_literal4700); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal4704); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumName, enumLit); 
                    }

                    }
                    break;
                case 8 :
                    // Cmd.g:1043:7: nColIt= collectionLiteral
                    {
                    pushFollow(FOLLOW_collectionLiteral_in_literal4716);
                    nColIt=collectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nColIt; 
                    }

                    }
                    break;
                case 9 :
                    // Cmd.g:1044:7: nEColIt= emptyCollectionLiteral
                    {
                    pushFollow(FOLLOW_emptyCollectionLiteral_in_literal4728);
                    nEColIt=emptyCollectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nEColIt; 
                    }

                    }
                    break;
                case 10 :
                    // Cmd.g:1045:7: nUndLit= undefinedLiteral
                    {
                    pushFollow(FOLLOW_undefinedLiteral_in_literal4740);
                    nUndLit=undefinedLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nUndLit; 
                    }

                    }
                    break;
                case 11 :
                    // Cmd.g:1046:7: nTupleLit= tupleLiteral
                    {
                    pushFollow(FOLLOW_tupleLiteral_in_literal4752);
                    nTupleLit=tupleLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nTupleLit; 
                    }

                    }
                    break;
                case 12 :
                    // Cmd.g:1047:7: nDateLit= dateLiteral
                    {
                    pushFollow(FOLLOW_dateLiteral_in_literal4764);
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
    // Cmd.g:1055:1: collectionLiteral returns [ASTCollectionLiteral n] : ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE ;
    public final ASTCollectionLiteral collectionLiteral() throws RecognitionException {
        ASTCollectionLiteral n = null;

        ASTCollectionItem ci = null;


         Token op = null; 
        try {
            // Cmd.g:1057:1: ( ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE )
            // Cmd.g:1058:5: ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE
            {
            if ( state.backtracking==0 ) {
               op = input.LT(1); 
            }
            if ( (input.LA(1)>=100 && input.LA(1)<=103) ) {
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
            match(input,LBRACE,FOLLOW_LBRACE_in_collectionLiteral4831); if (state.failed) return n;
            // Cmd.g:1062:5: (ci= collectionItem ( COMMA ci= collectionItem )* )?
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( ((LA90_0>=IDENT && LA90_0<=LPAREN)||LA90_0==INT||(LA90_0>=PLUS && LA90_0<=MINUS)||(LA90_0>=REAL && LA90_0<=HASH)||LA90_0==55||LA90_0==88||(LA90_0>=90 && LA90_0<=94)||(LA90_0>=98 && LA90_0<=109)) ) {
                alt90=1;
            }
            switch (alt90) {
                case 1 :
                    // Cmd.g:1063:7: ci= collectionItem ( COMMA ci= collectionItem )*
                    {
                    pushFollow(FOLLOW_collectionItem_in_collectionLiteral4848);
                    ci=collectionItem();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addItem(ci); 
                    }
                    // Cmd.g:1064:7: ( COMMA ci= collectionItem )*
                    loop89:
                    do {
                        int alt89=2;
                        int LA89_0 = input.LA(1);

                        if ( (LA89_0==COMMA) ) {
                            alt89=1;
                        }


                        switch (alt89) {
                    	case 1 :
                    	    // Cmd.g:1064:9: COMMA ci= collectionItem
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_collectionLiteral4861); if (state.failed) return n;
                    	    pushFollow(FOLLOW_collectionItem_in_collectionLiteral4865);
                    	    ci=collectionItem();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addItem(ci); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop89;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RBRACE,FOLLOW_RBRACE_in_collectionLiteral4884); if (state.failed) return n;

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
    // Cmd.g:1073:1: collectionItem returns [ASTCollectionItem n] : e= expression ( DOTDOT e= expression )? ;
    public final ASTCollectionItem collectionItem() throws RecognitionException {
        ASTCollectionItem n = null;

        ASTExpression e = null;


         n = new ASTCollectionItem(); 
        try {
            // Cmd.g:1075:1: (e= expression ( DOTDOT e= expression )? )
            // Cmd.g:1076:5: e= expression ( DOTDOT e= expression )?
            {
            pushFollow(FOLLOW_expression_in_collectionItem4913);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setFirst(e); 
            }
            // Cmd.g:1077:5: ( DOTDOT e= expression )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==DOTDOT) ) {
                alt91=1;
            }
            switch (alt91) {
                case 1 :
                    // Cmd.g:1077:7: DOTDOT e= expression
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_collectionItem4924); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_collectionItem4928);
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
    // Cmd.g:1087:1: emptyCollectionLiteral returns [ASTEmptyCollectionLiteral n] : 'oclEmpty' LPAREN t= collectionType RPAREN ;
    public final ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException {
        ASTEmptyCollectionLiteral n = null;

        ASTCollectionType t = null;


        try {
            // Cmd.g:1088:1: ( 'oclEmpty' LPAREN t= collectionType RPAREN )
            // Cmd.g:1089:5: 'oclEmpty' LPAREN t= collectionType RPAREN
            {
            match(input,104,FOLLOW_104_in_emptyCollectionLiteral4957); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_emptyCollectionLiteral4959); if (state.failed) return n;
            pushFollow(FOLLOW_collectionType_in_emptyCollectionLiteral4963);
            t=collectionType();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_emptyCollectionLiteral4965); if (state.failed) return n;
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
    // Cmd.g:1100:1: undefinedLiteral returns [ASTUndefinedLiteral n] : ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' );
    public final ASTUndefinedLiteral undefinedLiteral() throws RecognitionException {
        ASTUndefinedLiteral n = null;

        ASTType t = null;


        try {
            // Cmd.g:1101:1: ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' )
            int alt92=3;
            switch ( input.LA(1) ) {
            case 105:
                {
                alt92=1;
                }
                break;
            case 106:
                {
                alt92=2;
                }
                break;
            case 107:
                {
                alt92=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 92, 0, input);

                throw nvae;
            }

            switch (alt92) {
                case 1 :
                    // Cmd.g:1102:5: 'oclUndefined' LPAREN t= type RPAREN
                    {
                    match(input,105,FOLLOW_105_in_undefinedLiteral4995); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_undefinedLiteral4997); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_undefinedLiteral5001);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_undefinedLiteral5003); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(t); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:1105:5: 'Undefined'
                    {
                    match(input,106,FOLLOW_106_in_undefinedLiteral5017); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(); 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:1108:5: 'null'
                    {
                    match(input,107,FOLLOW_107_in_undefinedLiteral5031); if (state.failed) return n;
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
    // Cmd.g:1117:1: tupleLiteral returns [ASTTupleLiteral n] : 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE ;
    public final ASTTupleLiteral tupleLiteral() throws RecognitionException {
        ASTTupleLiteral n = null;

        ASTTupleItem ti = null;


         List tiList = new ArrayList(); 
        try {
            // Cmd.g:1119:1: ( 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE )
            // Cmd.g:1120:5: 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE
            {
            match(input,108,FOLLOW_108_in_tupleLiteral5065); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_tupleLiteral5071); if (state.failed) return n;
            pushFollow(FOLLOW_tupleItem_in_tupleLiteral5079);
            ti=tupleItem();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tiList.add(ti); 
            }
            // Cmd.g:1123:5: ( COMMA ti= tupleItem )*
            loop93:
            do {
                int alt93=2;
                int LA93_0 = input.LA(1);

                if ( (LA93_0==COMMA) ) {
                    alt93=1;
                }


                switch (alt93) {
            	case 1 :
            	    // Cmd.g:1123:7: COMMA ti= tupleItem
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleLiteral5090); if (state.failed) return n;
            	    pushFollow(FOLLOW_tupleItem_in_tupleLiteral5094);
            	    ti=tupleItem();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tiList.add(ti); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop93;
                }
            } while (true);

            match(input,RBRACE,FOLLOW_RBRACE_in_tupleLiteral5105); if (state.failed) return n;
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
    // Cmd.g:1131:1: tupleItem returns [ASTTupleItem n] : name= IDENT ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) ;
    public final ASTTupleItem tupleItem() throws RecognitionException {
        ASTTupleItem n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // Cmd.g:1132:1: (name= IDENT ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) )
            // Cmd.g:1133:5: name= IDENT ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tupleItem5136); if (state.failed) return n;
            // Cmd.g:1134:5: ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( (LA94_0==COLON) ) {
                int LA94_1 = input.LA(2);

                if ( (synpred2_Cmd()) ) {
                    alt94=1;
                }
                else if ( (true) ) {
                    alt94=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 94, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA94_0==EQUAL) ) {
                alt94=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 94, 0, input);

                throw nvae;
            }
            switch (alt94) {
                case 1 :
                    // Cmd.g:1137:7: ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression
                    {
                    match(input,COLON,FOLLOW_COLON_in_tupleItem5175); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_tupleItem5179);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,EQUAL,FOLLOW_EQUAL_in_tupleItem5181); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_tupleItem5185);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTTupleItem(name, t, e); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:1140:7: ( COLON | EQUAL ) e= expression
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

                    pushFollow(FOLLOW_expression_in_tupleItem5217);
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
    // Cmd.g:1149:1: dateLiteral returns [ASTDateLiteral n] : 'Date' LBRACE v= STRING RBRACE ;
    public final ASTDateLiteral dateLiteral() throws RecognitionException {
        ASTDateLiteral n = null;

        Token v=null;

        try {
            // Cmd.g:1150:1: ( 'Date' LBRACE v= STRING RBRACE )
            // Cmd.g:1151:5: 'Date' LBRACE v= STRING RBRACE
            {
            match(input,109,FOLLOW_109_in_dateLiteral5262); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_dateLiteral5264); if (state.failed) return n;
            v=(Token)match(input,STRING,FOLLOW_STRING_in_dateLiteral5268); if (state.failed) return n;
            match(input,RBRACE,FOLLOW_RBRACE_in_dateLiteral5270); if (state.failed) return n;
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
    // Cmd.g:1161:1: type returns [ASTType n] : (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) ;
    public final ASTType type() throws RecognitionException {
        ASTType n = null;

        ASTSimpleType nTSimple = null;

        ASTCollectionType nTCollection = null;

        ASTTupleType nTTuple = null;


         Token tok = null; 
        try {
            // Cmd.g:1163:1: ( (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) )
            // Cmd.g:1164:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of type */ 
            }
            // Cmd.g:1165:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            int alt95=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt95=1;
                }
                break;
            case 100:
            case 101:
            case 102:
            case 103:
            case 110:
                {
                alt95=2;
                }
                break;
            case 108:
                {
                alt95=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 95, 0, input);

                throw nvae;
            }

            switch (alt95) {
                case 1 :
                    // Cmd.g:1166:7: nTSimple= simpleType
                    {
                    pushFollow(FOLLOW_simpleType_in_type5320);
                    nTSimple=simpleType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTSimple; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:1167:7: nTCollection= collectionType
                    {
                    pushFollow(FOLLOW_collectionType_in_type5332);
                    nTCollection=collectionType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTCollection; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:1168:7: nTTuple= tupleType
                    {
                    pushFollow(FOLLOW_tupleType_in_type5344);
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
    // Cmd.g:1173:1: typeOnly returns [ASTType n] : nT= type EOF ;
    public final ASTType typeOnly() throws RecognitionException {
        ASTType n = null;

        ASTType nT = null;


        try {
            // Cmd.g:1174:1: (nT= type EOF )
            // Cmd.g:1175:5: nT= type EOF
            {
            pushFollow(FOLLOW_type_in_typeOnly5376);
            nT=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_typeOnly5378); if (state.failed) return n;
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
    // Cmd.g:1185:1: simpleType returns [ASTSimpleType n] : name= IDENT ;
    public final ASTSimpleType simpleType() throws RecognitionException {
        ASTSimpleType n = null;

        Token name=null;

        try {
            // Cmd.g:1186:1: (name= IDENT )
            // Cmd.g:1187:5: name= IDENT
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_simpleType5406); if (state.failed) return n;
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
    // Cmd.g:1195:1: collectionType returns [ASTCollectionType n] : ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN ;
    public final ASTCollectionType collectionType() throws RecognitionException {
        ASTCollectionType n = null;

        ASTType elemType = null;


         Token op = null; 
        try {
            // Cmd.g:1197:1: ( ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN )
            // Cmd.g:1198:5: ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN
            {
            if ( state.backtracking==0 ) {
               op = input.LT(1); 
            }
            if ( (input.LA(1)>=100 && input.LA(1)<=103)||input.LA(1)==110 ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_collectionType5471); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_collectionType5475);
            elemType=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_collectionType5477); if (state.failed) return n;
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
    // Cmd.g:1208:1: tupleType returns [ASTTupleType n] : 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN ;
    public final ASTTupleType tupleType() throws RecognitionException {
        ASTTupleType n = null;

        ASTTuplePart tp = null;


         List tpList = new ArrayList(); 
        try {
            // Cmd.g:1210:1: ( 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN )
            // Cmd.g:1211:5: 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN
            {
            match(input,108,FOLLOW_108_in_tupleType5511); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_tupleType5513); if (state.failed) return n;
            pushFollow(FOLLOW_tuplePart_in_tupleType5522);
            tp=tuplePart();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tpList.add(tp); 
            }
            // Cmd.g:1213:5: ( COMMA tp= tuplePart )*
            loop96:
            do {
                int alt96=2;
                int LA96_0 = input.LA(1);

                if ( (LA96_0==COMMA) ) {
                    alt96=1;
                }


                switch (alt96) {
            	case 1 :
            	    // Cmd.g:1213:7: COMMA tp= tuplePart
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleType5533); if (state.failed) return n;
            	    pushFollow(FOLLOW_tuplePart_in_tupleType5537);
            	    tp=tuplePart();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tpList.add(tp); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop96;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_tupleType5549); if (state.failed) return n;
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
    // Cmd.g:1222:1: tuplePart returns [ASTTuplePart n] : name= IDENT COLON t= type ;
    public final ASTTuplePart tuplePart() throws RecognitionException {
        ASTTuplePart n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Cmd.g:1223:1: (name= IDENT COLON t= type )
            // Cmd.g:1224:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tuplePart5581); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_tuplePart5583); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_tuplePart5587);
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
        // Cmd.g:251:15: ( expression )
        // Cmd.g:251:16: expression
        {
        pushFollow(FOLLOW_expression_in_synpred1_Cmd821);
        expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_Cmd

    // $ANTLR start synpred2_Cmd
    public final void synpred2_Cmd_fragment() throws RecognitionException {   
        // Cmd.g:1137:7: ( COLON IDENT EQUAL )
        // Cmd.g:1137:8: COLON IDENT EQUAL
        {
        match(input,COLON,FOLLOW_COLON_in_synpred2_Cmd5166); if (state.failed) return ;
        match(input,IDENT,FOLLOW_IDENT_in_synpred2_Cmd5168); if (state.failed) return ;
        match(input,EQUAL,FOLLOW_EQUAL_in_synpred2_Cmd5170); if (state.failed) return ;

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
        "\22\uffff";
    static final String DFA3_eofS =
        "\20\uffff\1\17\1\uffff";
    static final String DFA3_minS =
        "\1\54\1\7\13\uffff\1\5\1\7\1\uffff\1\4\1\uffff";
    static final String DFA3_maxS =
        "\1\74\1\7\13\uffff\1\12\1\7\1\uffff\1\74\1\uffff";
    static final String DFA3_acceptS =
        "\2\uffff\1\2\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\2\uffff"+
        "\1\1\1\uffff\1\3";
    static final String DFA3_specialS =
        "\22\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\1\1\2\1\uffff\1\3\1\4\1\uffff\1\5\1\uffff\1\6\1\7\1\10\1"+
            "\11\1\13\2\uffff\1\12\1\14",
            "\1\15",
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
            "\1\16\4\uffff\1\17",
            "\1\20",
            "",
            "\1\17\47\uffff\2\17\1\21\2\17\1\uffff\1\17\1\uffff\5\17\2"+
            "\uffff\2\17",
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
            return "112:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= showCmd | nC= hideCmd | nC= cropCmd )";
        }
    }
    static final String DFA9_eotS =
        "\42\uffff";
    static final String DFA9_eofS =
        "\1\25\41\uffff";
    static final String DFA9_minS =
        "\1\4\1\0\40\uffff";
    static final String DFA9_maxS =
        "\1\155\1\0\40\uffff";
    static final String DFA9_acceptS =
        "\2\uffff\23\1\1\2\14\uffff";
    static final String DFA9_specialS =
        "\1\0\1\1\40\uffff}>";
    static final String[] DFA9_transitionS = {
            "\1\25\2\uffff\1\11\1\23\12\uffff\1\5\6\uffff\2\2\5\uffff\1"+
            "\6\1\7\1\10\10\uffff\2\25\1\uffff\2\25\1\uffff\1\25\1\uffff"+
            "\3\25\1\1\1\25\2\uffff\2\25\33\uffff\1\2\1\uffff\1\21\3\22\1"+
            "\24\3\uffff\1\3\1\4\4\12\1\13\1\14\1\15\1\16\1\17\1\20",
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
            return "251:14: ( ( expression )=>e= expression | )";
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

                        else if ( ((LA9_0>=PLUS && LA9_0<=MINUS)||LA9_0==88) && (synpred1_Cmd())) {s = 2;}

                        else if ( (LA9_0==98) && (synpred1_Cmd())) {s = 3;}

                        else if ( (LA9_0==99) && (synpred1_Cmd())) {s = 4;}

                        else if ( (LA9_0==INT) && (synpred1_Cmd())) {s = 5;}

                        else if ( (LA9_0==REAL) && (synpred1_Cmd())) {s = 6;}

                        else if ( (LA9_0==STRING) && (synpred1_Cmd())) {s = 7;}

                        else if ( (LA9_0==HASH) && (synpred1_Cmd())) {s = 8;}

                        else if ( (LA9_0==IDENT) && (synpred1_Cmd())) {s = 9;}

                        else if ( ((LA9_0>=100 && LA9_0<=103)) && (synpred1_Cmd())) {s = 10;}

                        else if ( (LA9_0==104) && (synpred1_Cmd())) {s = 11;}

                        else if ( (LA9_0==105) && (synpred1_Cmd())) {s = 12;}

                        else if ( (LA9_0==106) && (synpred1_Cmd())) {s = 13;}

                        else if ( (LA9_0==107) && (synpred1_Cmd())) {s = 14;}

                        else if ( (LA9_0==108) && (synpred1_Cmd())) {s = 15;}

                        else if ( (LA9_0==109) && (synpred1_Cmd())) {s = 16;}

                        else if ( (LA9_0==90) && (synpred1_Cmd())) {s = 17;}

                        else if ( ((LA9_0>=91 && LA9_0<=93)) && (synpred1_Cmd())) {s = 18;}

                        else if ( (LA9_0==LPAREN) && (synpred1_Cmd())) {s = 19;}

                        else if ( (LA9_0==94) && (synpred1_Cmd())) {s = 20;}

                        else if ( (LA9_0==EOF||LA9_0==SEMI||(LA9_0>=44 && LA9_0<=45)||(LA9_0>=47 && LA9_0<=48)||LA9_0==50||(LA9_0>=52 && LA9_0<=54)||LA9_0==56||(LA9_0>=59 && LA9_0<=60)) ) {s = 21;}

                         
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
 

    public static final BitSet FOLLOW_cmd_in_cmdList65 = new BitSet(new long[]{0x19F5B00000000000L});
    public static final BitSet FOLLOW_cmd_in_cmdList77 = new BitSet(new long[]{0x19F5B00000000000L});
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
    public static final BitSet FOLLOW_showCmd_in_cmdStmt271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hideCmd_in_cmdStmt283 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cropCmd_in_cmdStmt295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_createCmd324 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_createCmd328 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_createCmd335 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_simpleType_in_createCmd339 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_createAssignCmd368 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_createAssignCmd372 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_createAssignCmd374 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_createAssignCmd376 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_simpleType_in_createAssignCmd380 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_createInsertCmd400 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_createInsertCmd404 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_createInsertCmd406 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_createInsertCmd410 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_createInsertCmd416 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_createInsertCmd418 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_createInsertCmd422 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_createInsertCmd424 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_destroyCmd460 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_destroyCmd464 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COMMA_in_destroyCmd486 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_destroyCmd490 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_48_in_insertCmd529 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_insertCmd531 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_insertCmd540 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_COMMA_in_insertCmd544 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_insertCmd552 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_insertCmd558 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_insertCmd562 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_insertCmd574 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_insertCmd576 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_insertCmd580 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_deleteCmd615 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_deleteCmd617 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_deleteCmd625 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_COMMA_in_deleteCmd629 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_deleteCmd637 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_deleteCmd643 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_deleteCmd647 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_deleteCmd658 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_deleteCmd660 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_deleteCmd664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_setCmd694 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_setCmd698 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_setCmd700 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_setCmd704 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_opEnterCmd738 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_opEnterCmd747 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_opEnterCmd751 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_opEnterCmd759 = new BitSet(new long[]{0x0080000E0C080380L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_opEnterCmd769 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_opEnterCmd775 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_opEnterCmd779 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_opEnterCmd793 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_opExitCmd817 = new BitSet(new long[]{0x0080000E0C080182L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_opExitCmd827 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_letCmd860 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_letCmd864 = new BitSet(new long[]{0x0000000000000820L});
    public static final BitSet FOLLOW_COLON_in_letCmd868 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_letCmd872 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_letCmd877 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_letCmd881 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_hideCmd908 = new BitSet(new long[]{0x0600000000000080L});
    public static final BitSet FOLLOW_57_in_hideCmd917 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_hideCmd930 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_COLON_in_hideCmd933 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_hideCmd939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_hideCmd950 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_hideCmd952 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_hideCmd958 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_hideCmd960 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_hideCmd962 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_hideCmd966 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_showCmd991 = new BitSet(new long[]{0x0600000000000080L});
    public static final BitSet FOLLOW_57_in_showCmd1000 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_showCmd1013 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_COLON_in_showCmd1016 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_showCmd1022 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_showCmd1033 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_showCmd1035 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_showCmd1041 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_showCmd1043 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_showCmd1045 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_showCmd1049 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_cropCmd1074 = new BitSet(new long[]{0x0400000000000082L});
    public static final BitSet FOLLOW_idList_in_cropCmd1087 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_COLON_in_cropCmd1090 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_cropCmd1096 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_cropCmd1107 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_cropCmd1109 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_cropCmd1115 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_cropCmd1117 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_cropCmd1119 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_cropCmd1123 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_model1152 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_model1156 = new BitSet(new long[]{0xC000000000000080L,0x00000000000001E3L});
    public static final BitSet FOLLOW_enumTypeDefinition_in_model1168 = new BitSet(new long[]{0xC000000000000080L,0x00000000000001E3L});
    public static final BitSet FOLLOW_generalClassDefinition_in_model1185 = new BitSet(new long[]{0x4000000000000080L,0x00000000000001E3L});
    public static final BitSet FOLLOW_associationDefinition_in_model1202 = new BitSet(new long[]{0x4000000000000080L,0x00000000000001E3L});
    public static final BitSet FOLLOW_62_in_model1218 = new BitSet(new long[]{0x4000000000000080L,0x00000000000021E3L});
    public static final BitSet FOLLOW_invariant_in_model1236 = new BitSet(new long[]{0x4000000000000080L,0x00000000000021E3L});
    public static final BitSet FOLLOW_prePost_in_model1257 = new BitSet(new long[]{0x4000000000000080L,0x00000000000021E3L});
    public static final BitSet FOLLOW_EOF_in_model1298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_enumTypeDefinition1325 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_enumTypeDefinition1329 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LBRACE_in_enumTypeDefinition1331 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_enumTypeDefinition1335 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RBRACE_in_enumTypeDefinition1337 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_SEMI_in_enumTypeDefinition1341 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_64_in_generalClassDefinition1380 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000063L});
    public static final BitSet FOLLOW_classDefinition_in_generalClassDefinition1398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_associationClassDefinition_in_generalClassDefinition1418 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_classDefinition1457 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_classDefinition1461 = new BitSet(new long[]{0x4000000000004000L,0x000000000000001CL});
    public static final BitSet FOLLOW_LESS_in_classDefinition1471 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_classDefinition1475 = new BitSet(new long[]{0x4000000000000000L,0x000000000000001CL});
    public static final BitSet FOLLOW_66_in_classDefinition1488 = new BitSet(new long[]{0x4000000000000080L,0x0000000000000018L});
    public static final BitSet FOLLOW_attributeDefinition_in_classDefinition1501 = new BitSet(new long[]{0x4000000000000080L,0x0000000000000018L});
    public static final BitSet FOLLOW_67_in_classDefinition1522 = new BitSet(new long[]{0x4000000000000080L,0x0000000000000010L});
    public static final BitSet FOLLOW_operationDefinition_in_classDefinition1535 = new BitSet(new long[]{0x4000000000000080L,0x0000000000000010L});
    public static final BitSet FOLLOW_62_in_classDefinition1556 = new BitSet(new long[]{0x0000000000000000L,0x000000000000C010L});
    public static final BitSet FOLLOW_invariantClause_in_classDefinition1576 = new BitSet(new long[]{0x0000000000000000L,0x000000000000C010L});
    public static final BitSet FOLLOW_68_in_classDefinition1600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_associationClassDefinition1633 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_associationClassDefinition1659 = new BitSet(new long[]{0x0000400000004000L});
    public static final BitSet FOLLOW_LESS_in_associationClassDefinition1669 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_associationClassDefinition1673 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_associationClassDefinition1684 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_associationEnd_in_associationClassDefinition1692 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_associationEnd_in_associationClassDefinition1704 = new BitSet(new long[]{0x4000000000000080L,0x000000000000019CL});
    public static final BitSet FOLLOW_66_in_associationClassDefinition1717 = new BitSet(new long[]{0x4000000000000080L,0x0000000000000198L});
    public static final BitSet FOLLOW_attributeDefinition_in_associationClassDefinition1730 = new BitSet(new long[]{0x4000000000000080L,0x0000000000000198L});
    public static final BitSet FOLLOW_67_in_associationClassDefinition1751 = new BitSet(new long[]{0x4000000000000080L,0x0000000000000190L});
    public static final BitSet FOLLOW_operationDefinition_in_associationClassDefinition1764 = new BitSet(new long[]{0x4000000000000080L,0x0000000000000190L});
    public static final BitSet FOLLOW_62_in_associationClassDefinition1785 = new BitSet(new long[]{0x0000000000000000L,0x000000000000C190L});
    public static final BitSet FOLLOW_invariantClause_in_associationClassDefinition1805 = new BitSet(new long[]{0x0000000000000000L,0x000000000000C190L});
    public static final BitSet FOLLOW_set_in_associationClassDefinition1839 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_associationClassDefinition1868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_attributeDefinition1897 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_attributeDefinition1899 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_attributeDefinition1903 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_SEMI_in_attributeDefinition1907 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationDefinition1945 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_paramList_in_operationDefinition1953 = new BitSet(new long[]{0x0000000000000832L,0x0000000000030000L});
    public static final BitSet FOLLOW_COLON_in_operationDefinition1961 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_operationDefinition1965 = new BitSet(new long[]{0x0000000000000812L,0x0000000000030000L});
    public static final BitSet FOLLOW_EQUAL_in_operationDefinition1993 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_operationDefinition1997 = new BitSet(new long[]{0x0000000000000012L,0x0000000000030000L});
    public static final BitSet FOLLOW_EQUAL_in_operationDefinition2019 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_operationDefinition2021 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_SCRIPTBODY_in_operationDefinition2025 = new BitSet(new long[]{0x0000000000000012L,0x0000000000030000L});
    public static final BitSet FOLLOW_prePostClause_in_operationDefinition2056 = new BitSet(new long[]{0x0000000000000012L,0x0000000000030000L});
    public static final BitSet FOLLOW_SEMI_in_operationDefinition2069 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_keyAssociation_in_associationDefinition2107 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_associationDefinition2111 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_72_in_associationDefinition2115 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_associationDefinition2130 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_associationDefinition2138 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_associationEnd_in_associationDefinition2146 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_associationEnd_in_associationDefinition2158 = new BitSet(new long[]{0x0000000000000080L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_associationDefinition2169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd2195 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_LBRACK_in_associationEnd2197 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_multiplicity_in_associationEnd2201 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RBRACK_in_associationEnd2203 = new BitSet(new long[]{0x0000000000000092L,0x0000000000001C00L});
    public static final BitSet FOLLOW_keyRole_in_associationEnd2214 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd2218 = new BitSet(new long[]{0x0000000000000092L,0x0000000000001C00L});
    public static final BitSet FOLLOW_74_in_associationEnd2239 = new BitSet(new long[]{0x0000000000000092L,0x0000000000001C00L});
    public static final BitSet FOLLOW_75_in_associationEnd2251 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd2255 = new BitSet(new long[]{0x0000000000000092L,0x0000000000001C00L});
    public static final BitSet FOLLOW_keyUnion_in_associationEnd2267 = new BitSet(new long[]{0x0000000000000092L,0x0000000000001C00L});
    public static final BitSet FOLLOW_76_in_associationEnd2279 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd2283 = new BitSet(new long[]{0x0000000000000092L,0x0000000000001C00L});
    public static final BitSet FOLLOW_SEMI_in_associationEnd2300 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity2335 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COMMA_in_multiplicity2345 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity2349 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange2378 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_DOTDOT_in_multiplicityRange2388 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange2392 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_multiplicitySpec2426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_multiplicitySpec2436 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_invariant2477 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_invariant2487 = new BitSet(new long[]{0x0000000000000420L});
    public static final BitSet FOLLOW_COMMA_in_invariant2500 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_invariant2504 = new BitSet(new long[]{0x0000000000000420L});
    public static final BitSet FOLLOW_COLON_in_invariant2512 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_simpleType_in_invariant2524 = new BitSet(new long[]{0x0000000000000002L,0x000000000000C000L});
    public static final BitSet FOLLOW_invariantClause_in_invariant2536 = new BitSet(new long[]{0x0000000000000002L,0x000000000000C000L});
    public static final BitSet FOLLOW_78_in_invariantClause2567 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_IDENT_in_invariantClause2573 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_invariantClause2578 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_invariantClause2582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_invariantClause2592 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_invariantClause2594 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_IDENT_in_invariantClause2600 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_invariantClause2605 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_invariantClause2609 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_prePost2635 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_prePost2639 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_COLON_COLON_in_prePost2641 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_prePost2645 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_paramList_in_prePost2649 = new BitSet(new long[]{0x0000000000000020L,0x0000000000030000L});
    public static final BitSet FOLLOW_COLON_in_prePost2653 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_prePost2657 = new BitSet(new long[]{0x0000000000000000L,0x0000000000030000L});
    public static final BitSet FOLLOW_prePostClause_in_prePost2676 = new BitSet(new long[]{0x0000000000000002L,0x0000000000030000L});
    public static final BitSet FOLLOW_set_in_prePostClause2715 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_IDENT_in_prePostClause2730 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_prePostClause2735 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_prePostClause2739 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_keyUnion2761 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_keyAssociation2775 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_keyRole2789 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_expressionOnly2819 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_expressionOnly2821 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_expression2869 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_expression2873 = new BitSet(new long[]{0x0000000000000820L});
    public static final BitSet FOLLOW_COLON_in_expression2877 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_expression2881 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_expression2886 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_expression2890 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_expression2892 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_conditionalImpliesExpression_in_expression2917 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_paramList2950 = new BitSet(new long[]{0x0000000000000280L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList2967 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_paramList2979 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList2983 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_paramList3003 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_idList3032 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COMMA_in_idList3042 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_idList3046 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_IDENT_in_variableDeclaration3077 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_variableDeclaration3079 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_variableDeclaration3083 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3119 = new BitSet(new long[]{0x0000000000000002L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_conditionalImpliesExpression3132 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3136 = new BitSet(new long[]{0x0000000000000002L,0x0000000000080000L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3181 = new BitSet(new long[]{0x0000000000000002L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_conditionalOrExpression3194 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3198 = new BitSet(new long[]{0x0000000000000002L,0x0000000000100000L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3242 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_conditionalXOrExpression3255 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3259 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression3303 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_conditionalAndExpression3316 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression3320 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression3368 = new BitSet(new long[]{0x0000000000400802L});
    public static final BitSet FOLLOW_set_in_equalityExpression3387 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression3397 = new BitSet(new long[]{0x0000000000400802L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression3446 = new BitSet(new long[]{0x0000000003804002L});
    public static final BitSet FOLLOW_set_in_relationalExpression3464 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression3482 = new BitSet(new long[]{0x0000000003804002L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3532 = new BitSet(new long[]{0x000000000C000002L});
    public static final BitSet FOLLOW_set_in_additiveExpression3550 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3560 = new BitSet(new long[]{0x000000000C000002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3610 = new BitSet(new long[]{0x0000000010100002L,0x0000000000800000L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression3628 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3642 = new BitSet(new long[]{0x0000000010100002L,0x0000000000800000L});
    public static final BitSet FOLLOW_set_in_unaryExpression3704 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression3728 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpression_in_unaryExpression3748 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_postfixExpression3781 = new BitSet(new long[]{0x0000000060000002L});
    public static final BitSet FOLLOW_ARROW_in_postfixExpression3799 = new BitSet(new long[]{0x0000000000000080L,0x000000003C000000L});
    public static final BitSet FOLLOW_DOT_in_postfixExpression3805 = new BitSet(new long[]{0x0000000000000080L,0x000000003C000000L});
    public static final BitSet FOLLOW_propertyCall_in_postfixExpression3816 = new BitSet(new long[]{0x0000000060000002L});
    public static final BitSet FOLLOW_literal_in_primaryExpression3856 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyCall_in_primaryExpression3868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression3879 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_primaryExpression3883 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression3885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifExpression_in_primaryExpression3897 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_primaryExpression3914 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_DOT_in_primaryExpression3916 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_primaryExpression3918 = new BitSet(new long[]{0x0000000080000102L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression3922 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression3924 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_AT_in_primaryExpression3945 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_primaryExpression3947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_queryExpression_in_propertyCall4020 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterateExpression_in_propertyCall4033 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationExpression_in_propertyCall4046 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeExpression_in_propertyCall4059 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_queryExpression4094 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_queryExpression4101 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_queryExpression4112 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_BAR_in_queryExpression4116 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_queryExpression4127 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_queryExpression4133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_iterateExpression4165 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_iterateExpression4171 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_iterateExpression4179 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_iterateExpression4181 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_variableInitialization_in_iterateExpression4189 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_BAR_in_iterateExpression4191 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_iterateExpression4199 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_iterateExpression4205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression4249 = new BitSet(new long[]{0x0000000080010102L});
    public static final BitSet FOLLOW_LBRACK_in_operationExpression4265 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression4269 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RBRACK_in_operationExpression4271 = new BitSet(new long[]{0x0000000080000102L});
    public static final BitSet FOLLOW_AT_in_operationExpression4284 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_operationExpression4286 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_LPAREN_in_operationExpression4307 = new BitSet(new long[]{0x0080000E0C080380L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_operationExpression4328 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_operationExpression4340 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_operationExpression4344 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_operationExpression4364 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_typeExpression4407 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_typeExpression4423 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_typeExpression4427 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_typeExpression4429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_elemVarsDeclaration4468 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_COLON_in_elemVarsDeclaration4476 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_elemVarsDeclaration4480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableInitialization4515 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_variableInitialization4517 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_variableInitialization4521 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_variableInitialization4523 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_variableInitialization4527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_ifExpression4559 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_ifExpression4563 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_95_in_ifExpression4565 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_ifExpression4569 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_96_in_ifExpression4571 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_ifExpression4575 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_97_in_ifExpression4577 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_literal4616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_literal4630 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_literal4643 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REAL_in_literal4658 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal4672 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_literal4682 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_literal4686 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_literal4698 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_COLON_COLON_in_literal4700 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_literal4704 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionLiteral_in_literal4716 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyCollectionLiteral_in_literal4728 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedLiteral_in_literal4740 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleLiteral_in_literal4752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dateLiteral_in_literal4764 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionLiteral4802 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LBRACE_in_collectionLiteral4831 = new BitSet(new long[]{0x0080000E0C082180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral4848 = new BitSet(new long[]{0x0000000000002400L});
    public static final BitSet FOLLOW_COMMA_in_collectionLiteral4861 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral4865 = new BitSet(new long[]{0x0000000000002400L});
    public static final BitSet FOLLOW_RBRACE_in_collectionLiteral4884 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_collectionItem4913 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_DOTDOT_in_collectionItem4924 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_collectionItem4928 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_104_in_emptyCollectionLiteral4957 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_emptyCollectionLiteral4959 = new BitSet(new long[]{0x0000000000000000L,0x000040F000000000L});
    public static final BitSet FOLLOW_collectionType_in_emptyCollectionLiteral4963 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_emptyCollectionLiteral4965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_105_in_undefinedLiteral4995 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_undefinedLiteral4997 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_undefinedLiteral5001 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_undefinedLiteral5003 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_106_in_undefinedLiteral5017 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_107_in_undefinedLiteral5031 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_108_in_tupleLiteral5065 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LBRACE_in_tupleLiteral5071 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral5079 = new BitSet(new long[]{0x0000000000002400L});
    public static final BitSet FOLLOW_COMMA_in_tupleLiteral5090 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral5094 = new BitSet(new long[]{0x0000000000002400L});
    public static final BitSet FOLLOW_RBRACE_in_tupleLiteral5105 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tupleItem5136 = new BitSet(new long[]{0x0000000000000820L});
    public static final BitSet FOLLOW_COLON_in_tupleItem5175 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_tupleItem5179 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_tupleItem5181 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_tupleItem5185 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_tupleItem5207 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_tupleItem5217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_109_in_dateLiteral5262 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LBRACE_in_dateLiteral5264 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRING_in_dateLiteral5268 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RBRACE_in_dateLiteral5270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleType_in_type5320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_type5332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleType_in_type5344 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeOnly5376 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_typeOnly5378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_simpleType5406 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionType5444 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_collectionType5471 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_collectionType5475 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_collectionType5477 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_108_in_tupleType5511 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_tupleType5513 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType5522 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_tupleType5533 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType5537 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_tupleType5549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tuplePart5581 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_tuplePart5583 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_tuplePart5587 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_synpred1_Cmd821 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_synpred2_Cmd5166 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_synpred2_Cmd5168 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_synpred2_Cmd5170 = new BitSet(new long[]{0x0000000000000002L});

}