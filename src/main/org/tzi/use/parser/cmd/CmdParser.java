// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 Cmd.g 2009-04-02 13:53:43
 
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
public class CmdParser extends BaseParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "SEMI", "COLON", "COLON_EQUAL", "IDENT", "LPAREN", "RPAREN", "COMMA", "EQUAL", "LBRACE", "RBRACE", "LESS", "LBRACK", "RBRACK", "DOTDOT", "INT", "STAR", "COLON_COLON", "NOT_EQUAL", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "PLUS", "MINUS", "SLASH", "ARROW", "DOT", "AT", "BAR", "REAL", "STRING", "HASH", "NEWLINE", "WS", "SL_COMMENT", "ML_COMMENT", "RANGE_OR_INT", "ESC", "HEX_DIGIT", "VOCAB", "'create'", "'assign'", "'between'", "'destroy'", "'insert'", "'into'", "'delete'", "'from'", "'set'", "'openter'", "'opexit'", "'let'", "'execute'", "'model'", "'constraints'", "'enum'", "'abstract'", "'class'", "'attributes'", "'operations'", "'end'", "'associationClass'", "'associationclass'", "'aggregation'", "'composition'", "'begin'", "'association'", "'role'", "'ordered'", "'context'", "'inv'", "'pre'", "'post'", "'var'", "'declare'", "'namehint'", "'if'", "'then'", "'else'", "'endif'", "'while'", "'do'", "'wend'", "'for'", "'in'", "'implies'", "'or'", "'xor'", "'and'", "'div'", "'not'", "'allInstances'", "'iterate'", "'oclAsType'", "'oclIsKindOf'", "'oclIsTypeOf'", "'true'", "'false'", "'Set'", "'Sequence'", "'Bag'", "'oclEmpty'", "'oclUndefined'", "'Undefined'", "'null'", "'Tuple'", "'Collection'"
    };
    public static final int STAR=19;
    public static final int EOF=-1;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__91=91;
    public static final int RPAREN=9;
    public static final int T__92=92;
    public static final int GREATER=22;
    public static final int T__90=90;
    public static final int NOT_EQUAL=21;
    public static final int LESS=14;
    public static final int T__99=99;
    public static final int T__98=98;
    public static final int T__97=97;
    public static final int T__96=96;
    public static final int T__95=95;
    public static final int RBRACK=16;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int RBRACE=13;
    public static final int T__83=83;
    public static final int INT=18;
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
    public static final int LESS_EQUAL=23;
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
    public static final int LBRACK=15;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int ESC=40;
    public static final int LBRACE=12;
    public static final int DOTDOT=17;
    public static final int T__61=61;
    public static final int T__60=60;
    public static final int LPAREN=8;
    public static final int AT=30;
    public static final int T__55=55;
    public static final int ML_COMMENT=38;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int SLASH=27;
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
    public static final int IDENT=7;
    public static final int PLUS=25;
    public static final int RANGE_OR_INT=39;
    public static final int DOT=29;
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
    public static final int COLON_COLON=20;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int MINUS=26;
    public static final int SEMI=4;
    public static final int COLON=5;
    public static final int NEWLINE=35;
    public static final int VOCAB=42;
    public static final int ARROW=28;
    public static final int GREATER_EQUAL=24;
    public static final int BAR=31;
    public static final int STRING=33;

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

                if ( ((LA1_0>=43 && LA1_0<=44)||(LA1_0>=46 && LA1_0<=47)||LA1_0==49||(LA1_0>=51 && LA1_0<=55)) ) {
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
    // Cmd.g:109:1: cmdStmt returns [ASTCmd n] : (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= executeCmd ) ;
    public final ASTCmd cmdStmt() throws RecognitionException {
        ASTCmd n = null;

        ASTCmd nC = null;


        try {
            // Cmd.g:110:1: ( (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= executeCmd ) )
            // Cmd.g:111:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= executeCmd )
            {
            // Cmd.g:111:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= executeCmd )
            int alt3=11;
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
                case 11 :
                    // Cmd.g:122:7: nC= executeCmd
                    {
                    pushFollow(FOLLOW_executeCmd_in_cmdStmt271);
                    nC=executeCmd();

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
    // Cmd.g:132:1: createCmd returns [ASTCmd n] : 'create' nIdList= idList COLON t= simpleType ;
    public final ASTCmd createCmd() throws RecognitionException {
        ASTCmd n = null;

        List nIdList = null;

        ASTSimpleType t = null;


        try {
            // Cmd.g:133:1: ( 'create' nIdList= idList COLON t= simpleType )
            // Cmd.g:134:5: 'create' nIdList= idList COLON t= simpleType
            {
            match(input,43,FOLLOW_43_in_createCmd300); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createCmd304);
            nIdList=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_createCmd311); if (state.failed) return n;
            pushFollow(FOLLOW_simpleType_in_createCmd315);
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
    // Cmd.g:144:1: createAssignCmd returns [ASTCmd n] : 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType ;
    public final ASTCmd createAssignCmd() throws RecognitionException {
        ASTCmd n = null;

        List nIdList = null;

        ASTSimpleType t = null;


        try {
            // Cmd.g:145:1: ( 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType )
            // Cmd.g:146:5: 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType
            {
            match(input,44,FOLLOW_44_in_createAssignCmd344); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createAssignCmd348);
            nIdList=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_createAssignCmd350); if (state.failed) return n;
            match(input,43,FOLLOW_43_in_createAssignCmd352); if (state.failed) return n;
            pushFollow(FOLLOW_simpleType_in_createAssignCmd356);
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
    // Cmd.g:154:1: createInsertCmd returns [ASTCmd n] : 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN ;
    public final ASTCmd createInsertCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        Token idAssoc=null;
        List idListInsert = null;


        try {
            // Cmd.g:155:1: ( 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN )
            // Cmd.g:156:5: 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN
            {
            match(input,43,FOLLOW_43_in_createInsertCmd376); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_createInsertCmd380); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_createInsertCmd382); if (state.failed) return n;
            idAssoc=(Token)match(input,IDENT,FOLLOW_IDENT_in_createInsertCmd386); if (state.failed) return n;
            match(input,45,FOLLOW_45_in_createInsertCmd392); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_createInsertCmd394); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createInsertCmd398);
            idListInsert=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_createInsertCmd400); if (state.failed) return n;
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
    // Cmd.g:167:1: destroyCmd returns [ASTCmd n] : 'destroy' e= expression ( COMMA e= expression )* ;
    public final ASTCmd destroyCmd() throws RecognitionException {
        ASTCmd n = null;

        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // Cmd.g:169:1: ( 'destroy' e= expression ( COMMA e= expression )* )
            // Cmd.g:170:6: 'destroy' e= expression ( COMMA e= expression )*
            {
            match(input,46,FOLLOW_46_in_destroyCmd436); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_destroyCmd440);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // Cmd.g:171:16: ( COMMA e= expression )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==COMMA) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // Cmd.g:171:18: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_destroyCmd462); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_destroyCmd466);
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
    // Cmd.g:181:1: insertCmd returns [ASTCmd n] : 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT ;
    public final ASTCmd insertCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // Cmd.g:183:1: ( 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT )
            // Cmd.g:184:5: 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT
            {
            match(input,47,FOLLOW_47_in_insertCmd505); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_insertCmd507); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_insertCmd516);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_insertCmd520); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_insertCmd528);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // Cmd.g:186:42: ( COMMA e= expression )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==COMMA) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // Cmd.g:186:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_insertCmd534); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_insertCmd538);
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

            match(input,RPAREN,FOLLOW_RPAREN_in_insertCmd550); if (state.failed) return n;
            match(input,48,FOLLOW_48_in_insertCmd552); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_insertCmd556); if (state.failed) return n;
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
    // Cmd.g:197:1: deleteCmd returns [ASTCmd n] : 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT ;
    public final ASTCmd deleteCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // Cmd.g:199:1: ( 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT )
            // Cmd.g:200:5: 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT
            {
            match(input,49,FOLLOW_49_in_deleteCmd591); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_deleteCmd593); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_deleteCmd601);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_deleteCmd605); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_deleteCmd613);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // Cmd.g:202:42: ( COMMA e= expression )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==COMMA) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // Cmd.g:202:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_deleteCmd619); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_deleteCmd623);
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

            match(input,RPAREN,FOLLOW_RPAREN_in_deleteCmd634); if (state.failed) return n;
            match(input,50,FOLLOW_50_in_deleteCmd636); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_deleteCmd640); if (state.failed) return n;
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
    // Cmd.g:216:1: setCmd returns [ASTCmd n] : 'set' e1= expression COLON_EQUAL e2= expression ;
    public final ASTCmd setCmd() throws RecognitionException {
        ASTCmd n = null;

        ASTExpression e1 = null;

        ASTExpression e2 = null;


        try {
            // Cmd.g:217:1: ( 'set' e1= expression COLON_EQUAL e2= expression )
            // Cmd.g:218:5: 'set' e1= expression COLON_EQUAL e2= expression
            {
            match(input,51,FOLLOW_51_in_setCmd670); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_setCmd674);
            e1=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_setCmd676); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_setCmd680);
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
    // Cmd.g:230:1: opEnterCmd returns [ASTCmd n] : 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN ;
    public final ASTCmd opEnterCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        ASTExpression e = null;


        ASTOpEnterCmd nOpEnter = null;
        try {
            // Cmd.g:232:1: ( 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )
            // Cmd.g:233:5: 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
            {
            match(input,52,FOLLOW_52_in_opEnterCmd714); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_opEnterCmd723);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_opEnterCmd727); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               nOpEnter = new ASTOpEnterCmd(e, id); n = nOpEnter;
            }
            match(input,LPAREN,FOLLOW_LPAREN_in_opEnterCmd735); if (state.failed) return n;
            // Cmd.g:236:5: (e= expression ( COMMA e= expression )* )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( ((LA8_0>=IDENT && LA8_0<=LPAREN)||LA8_0==INT||(LA8_0>=PLUS && LA8_0<=MINUS)||(LA8_0>=REAL && LA8_0<=HASH)||LA8_0==54||LA8_0==79||LA8_0==93||(LA8_0>=95 && LA8_0<=108)) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // Cmd.g:236:7: e= expression ( COMMA e= expression )*
                    {
                    pushFollow(FOLLOW_expression_in_opEnterCmd745);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       nOpEnter.addArg(e); 
                    }
                    // Cmd.g:236:47: ( COMMA e= expression )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==COMMA) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // Cmd.g:236:49: COMMA e= expression
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_opEnterCmd751); if (state.failed) return n;
                    	    pushFollow(FOLLOW_expression_in_opEnterCmd755);
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

            match(input,RPAREN,FOLLOW_RPAREN_in_opEnterCmd769); if (state.failed) return n;

            }

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
    // Cmd.g:246:1: opExitCmd returns [ASTCmd n] : 'opexit' ( ( expression )=>e= expression | ) ;
    public final ASTCmd opExitCmd() throws RecognitionException {
        ASTCmd n = null;

        ASTExpression e = null;


        try {
            // Cmd.g:247:1: ( 'opexit' ( ( expression )=>e= expression | ) )
            // Cmd.g:248:5: 'opexit' ( ( expression )=>e= expression | )
            {
            match(input,53,FOLLOW_53_in_opExitCmd793); if (state.failed) return n;
            // Cmd.g:248:14: ( ( expression )=>e= expression | )
            int alt9=2;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // Cmd.g:248:15: ( expression )=>e= expression
                    {
                    pushFollow(FOLLOW_expression_in_opExitCmd803);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 2 :
                    // Cmd.g:248:45: 
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
    // Cmd.g:257:1: letCmd returns [ASTCmd n] : 'let' name= IDENT ( COLON t= type )? EQUAL e= expression ;
    public final ASTCmd letCmd() throws RecognitionException {
        ASTCmd n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // Cmd.g:258:1: ( 'let' name= IDENT ( COLON t= type )? EQUAL e= expression )
            // Cmd.g:259:5: 'let' name= IDENT ( COLON t= type )? EQUAL e= expression
            {
            match(input,54,FOLLOW_54_in_letCmd836); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_letCmd840); if (state.failed) return n;
            // Cmd.g:259:22: ( COLON t= type )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==COLON) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // Cmd.g:259:24: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_letCmd844); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_letCmd848);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            match(input,EQUAL,FOLLOW_EQUAL_in_letCmd853); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_letCmd857);
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


    // $ANTLR start "executeCmd"
    // Cmd.g:263:1: executeCmd returns [ASTCmd n] : 'execute' e= expression ;
    public final ASTCmd executeCmd() throws RecognitionException {
        ASTCmd n = null;

        ASTExpression e = null;


        try {
            // Cmd.g:264:1: ( 'execute' e= expression )
            // Cmd.g:265:2: 'execute' e= expression
            {
            match(input,55,FOLLOW_55_in_executeCmd882); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_executeCmd891);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTExecuteCmd(e); 
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
    // $ANTLR end "executeCmd"


    // $ANTLR start "model"
    // Cmd.g:286:1: model returns [ASTModel n] : 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF ;
    public final ASTModel model() throws RecognitionException {
        ASTModel n = null;

        Token modelName=null;
        ASTEnumTypeDefinition e = null;

        ASTAssociation a = null;

        ASTConstraintDefinition cons = null;

        ASTPrePost ppc = null;


        try {
            // Cmd.g:287:1: ( 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF )
            // Cmd.g:288:5: 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF
            {
            match(input,56,FOLLOW_56_in_model923); if (state.failed) return n;
            modelName=(Token)match(input,IDENT,FOLLOW_IDENT_in_model927); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTModel(modelName); 
            }
            // Cmd.g:289:5: (e= enumTypeDefinition )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==58) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // Cmd.g:289:7: e= enumTypeDefinition
            	    {
            	    pushFollow(FOLLOW_enumTypeDefinition_in_model939);
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

            // Cmd.g:290:5: ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )*
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
                case 66:
                case 67:
                case 69:
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
            	    // Cmd.g:290:9: ( generalClassDefinition[$n] )
            	    {
            	    // Cmd.g:290:9: ( generalClassDefinition[$n] )
            	    // Cmd.g:290:11: generalClassDefinition[$n]
            	    {
            	    pushFollow(FOLLOW_generalClassDefinition_in_model956);
            	    generalClassDefinition(n);

            	    state._fsp--;
            	    if (state.failed) return n;

            	    }


            	    }
            	    break;
            	case 2 :
            	    // Cmd.g:291:9: (a= associationDefinition )
            	    {
            	    // Cmd.g:291:9: (a= associationDefinition )
            	    // Cmd.g:291:11: a= associationDefinition
            	    {
            	    pushFollow(FOLLOW_associationDefinition_in_model973);
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
            	    // Cmd.g:292:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    {
            	    // Cmd.g:292:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    // Cmd.g:292:11: 'constraints' (cons= invariant | ppc= prePost )*
            	    {
            	    match(input,57,FOLLOW_57_in_model989); if (state.failed) return n;
            	    // Cmd.g:293:11: (cons= invariant | ppc= prePost )*
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
            	                else if ( (LA12_3==EOF||LA12_3==COLON||LA12_3==57||(LA12_3>=59 && LA12_3<=60)||(LA12_3>=64 && LA12_3<=67)||LA12_3==69||(LA12_3>=72 && LA12_3<=73)) ) {
            	                    alt12=1;
            	                }


            	            }


            	        }


            	        switch (alt12) {
            	    	case 1 :
            	    	    // Cmd.g:293:15: cons= invariant
            	    	    {
            	    	    pushFollow(FOLLOW_invariant_in_model1007);
            	    	    cons=invariant();

            	    	    state._fsp--;
            	    	    if (state.failed) return n;
            	    	    if ( state.backtracking==0 ) {
            	    	       n.addConstraint(cons); 
            	    	    }

            	    	    }
            	    	    break;
            	    	case 2 :
            	    	    // Cmd.g:294:15: ppc= prePost
            	    	    {
            	    	    pushFollow(FOLLOW_prePost_in_model1028);
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

            match(input,EOF,FOLLOW_EOF_in_model1069); if (state.failed) return n;

            }

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
    // Cmd.g:305:1: enumTypeDefinition returns [ASTEnumTypeDefinition n] : 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )? ;
    public final ASTEnumTypeDefinition enumTypeDefinition() throws RecognitionException {
        ASTEnumTypeDefinition n = null;

        Token name=null;
        List idListRes = null;


        try {
            // Cmd.g:306:1: ( 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )? )
            // Cmd.g:307:5: 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )?
            {
            match(input,58,FOLLOW_58_in_enumTypeDefinition1096); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_enumTypeDefinition1100); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_enumTypeDefinition1102); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_enumTypeDefinition1106);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,RBRACE,FOLLOW_RBRACE_in_enumTypeDefinition1108); if (state.failed) return n;
            // Cmd.g:307:54: ( SEMI )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==SEMI) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // Cmd.g:307:56: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_enumTypeDefinition1112); if (state.failed) return n;

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
    // Cmd.g:316:1: generalClassDefinition[ASTModel n] : ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) ) ;
    public final void generalClassDefinition(ASTModel n) throws RecognitionException {
        ASTClass c = null;

        ASTAssociationClass ac = null;


         
          boolean isAbstract = false;

        try {
            // Cmd.g:320:1: ( ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) ) )
            // Cmd.g:321:5: ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) )
            {
            // Cmd.g:321:5: ( 'abstract' )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==59) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // Cmd.g:321:7: 'abstract'
                    {
                    match(input,59,FOLLOW_59_in_generalClassDefinition1151); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       isAbstract = true; 
                    }

                    }
                    break;

            }

            // Cmd.g:322:5: ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) )
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
                    // Cmd.g:322:7: (c= classDefinition[isAbstract] )
                    {
                    // Cmd.g:322:7: (c= classDefinition[isAbstract] )
                    // Cmd.g:322:9: c= classDefinition[isAbstract]
                    {
                    pushFollow(FOLLOW_classDefinition_in_generalClassDefinition1169);
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
                    // Cmd.g:323:7: (ac= associationClassDefinition[isAbstract] )
                    {
                    // Cmd.g:323:7: (ac= associationClassDefinition[isAbstract] )
                    // Cmd.g:323:9: ac= associationClassDefinition[isAbstract]
                    {
                    pushFollow(FOLLOW_associationClassDefinition_in_generalClassDefinition1189);
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
    // Cmd.g:340:1: classDefinition[boolean isAbstract] returns [ASTClass n] : 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end' ;
    public final ASTClass classDefinition(boolean isAbstract) throws RecognitionException {
        ASTClass n = null;

        Token name=null;
        List idListRes = null;

        ASTAttribute a = null;

        ASTOperation op = null;

        ASTInvariantClause inv = null;


         List idList; 
        try {
            // Cmd.g:342:1: ( 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end' )
            // Cmd.g:343:5: 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end'
            {
            match(input,60,FOLLOW_60_in_classDefinition1228); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_classDefinition1232); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTClass(name, isAbstract); 
            }
            // Cmd.g:344:5: ( LESS idListRes= idList )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==LESS) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // Cmd.g:344:7: LESS idListRes= idList
                    {
                    match(input,LESS,FOLLOW_LESS_in_classDefinition1242); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_classDefinition1246);
                    idListRes=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addSuperClasses(idListRes); 
                    }

                    }
                    break;

            }

            // Cmd.g:345:5: ( 'attributes' (a= attributeDefinition )* )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==61) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // Cmd.g:345:7: 'attributes' (a= attributeDefinition )*
                    {
                    match(input,61,FOLLOW_61_in_classDefinition1259); if (state.failed) return n;
                    // Cmd.g:346:7: (a= attributeDefinition )*
                    loop18:
                    do {
                        int alt18=2;
                        int LA18_0 = input.LA(1);

                        if ( (LA18_0==IDENT) ) {
                            alt18=1;
                        }


                        switch (alt18) {
                    	case 1 :
                    	    // Cmd.g:346:9: a= attributeDefinition
                    	    {
                    	    pushFollow(FOLLOW_attributeDefinition_in_classDefinition1272);
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

            // Cmd.g:348:5: ( 'operations' (op= operationDefinition )* )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==62) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // Cmd.g:348:7: 'operations' (op= operationDefinition )*
                    {
                    match(input,62,FOLLOW_62_in_classDefinition1293); if (state.failed) return n;
                    // Cmd.g:349:7: (op= operationDefinition )*
                    loop20:
                    do {
                        int alt20=2;
                        int LA20_0 = input.LA(1);

                        if ( (LA20_0==IDENT) ) {
                            alt20=1;
                        }


                        switch (alt20) {
                    	case 1 :
                    	    // Cmd.g:349:9: op= operationDefinition
                    	    {
                    	    pushFollow(FOLLOW_operationDefinition_in_classDefinition1306);
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

            // Cmd.g:351:5: ( 'constraints' (inv= invariantClause )* )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==57) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // Cmd.g:351:7: 'constraints' (inv= invariantClause )*
                    {
                    match(input,57,FOLLOW_57_in_classDefinition1327); if (state.failed) return n;
                    // Cmd.g:352:7: (inv= invariantClause )*
                    loop22:
                    do {
                        int alt22=2;
                        int LA22_0 = input.LA(1);

                        if ( (LA22_0==73) ) {
                            alt22=1;
                        }


                        switch (alt22) {
                    	case 1 :
                    	    // Cmd.g:353:9: inv= invariantClause
                    	    {
                    	    pushFollow(FOLLOW_invariantClause_in_classDefinition1347);
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

            match(input,63,FOLLOW_63_in_classDefinition1371); if (state.failed) return n;

            }

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
    // Cmd.g:374:1: associationClassDefinition[boolean isAbstract] returns [ASTAssociationClass n] : classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end' ;
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
            // Cmd.g:376:1: (classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end' )
            // Cmd.g:377:5: classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end'
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
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationClassDefinition1430); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociationClass(name, isAbstract); 
            }
            // Cmd.g:386:5: ( LESS idListRes= idList )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==LESS) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // Cmd.g:386:7: LESS idListRes= idList
                    {
                    match(input,LESS,FOLLOW_LESS_in_associationClassDefinition1440); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_associationClassDefinition1444);
                    idListRes=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addSuperClasses(idListRes); 
                    }

                    }
                    break;

            }

            match(input,45,FOLLOW_45_in_associationClassDefinition1455); if (state.failed) return n;
            pushFollow(FOLLOW_associationEnd_in_associationClassDefinition1463);
            ae=associationEnd();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addEnd(ae); 
            }
            // Cmd.g:389:5: (ae= associationEnd )+
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
            	    // Cmd.g:389:7: ae= associationEnd
            	    {
            	    pushFollow(FOLLOW_associationEnd_in_associationClassDefinition1475);
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

            // Cmd.g:390:5: ( 'attributes' (a= attributeDefinition )* )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==61) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // Cmd.g:390:7: 'attributes' (a= attributeDefinition )*
                    {
                    match(input,61,FOLLOW_61_in_associationClassDefinition1488); if (state.failed) return n;
                    // Cmd.g:391:7: (a= attributeDefinition )*
                    loop26:
                    do {
                        int alt26=2;
                        int LA26_0 = input.LA(1);

                        if ( (LA26_0==IDENT) ) {
                            alt26=1;
                        }


                        switch (alt26) {
                    	case 1 :
                    	    // Cmd.g:391:9: a= attributeDefinition
                    	    {
                    	    pushFollow(FOLLOW_attributeDefinition_in_associationClassDefinition1501);
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

            // Cmd.g:393:5: ( 'operations' (op= operationDefinition )* )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==62) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // Cmd.g:393:7: 'operations' (op= operationDefinition )*
                    {
                    match(input,62,FOLLOW_62_in_associationClassDefinition1522); if (state.failed) return n;
                    // Cmd.g:394:7: (op= operationDefinition )*
                    loop28:
                    do {
                        int alt28=2;
                        int LA28_0 = input.LA(1);

                        if ( (LA28_0==IDENT) ) {
                            alt28=1;
                        }


                        switch (alt28) {
                    	case 1 :
                    	    // Cmd.g:394:9: op= operationDefinition
                    	    {
                    	    pushFollow(FOLLOW_operationDefinition_in_associationClassDefinition1535);
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

            // Cmd.g:396:5: ( 'constraints' (inv= invariantClause )* )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==57) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // Cmd.g:396:7: 'constraints' (inv= invariantClause )*
                    {
                    match(input,57,FOLLOW_57_in_associationClassDefinition1556); if (state.failed) return n;
                    // Cmd.g:397:7: (inv= invariantClause )*
                    loop30:
                    do {
                        int alt30=2;
                        int LA30_0 = input.LA(1);

                        if ( (LA30_0==73) ) {
                            alt30=1;
                        }


                        switch (alt30) {
                    	case 1 :
                    	    // Cmd.g:398:9: inv= invariantClause
                    	    {
                    	    pushFollow(FOLLOW_invariantClause_in_associationClassDefinition1576);
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

            // Cmd.g:401:5: ( ( 'aggregation' | 'composition' ) )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( ((LA32_0>=66 && LA32_0<=67)) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // Cmd.g:401:7: ( 'aggregation' | 'composition' )
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

            match(input,63,FOLLOW_63_in_associationClassDefinition1639); if (state.failed) return n;

            }

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
    // Cmd.g:412:1: attributeDefinition returns [ASTAttribute n] : name= IDENT COLON t= type ( SEMI )? ;
    public final ASTAttribute attributeDefinition() throws RecognitionException {
        ASTAttribute n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Cmd.g:413:1: (name= IDENT COLON t= type ( SEMI )? )
            // Cmd.g:414:5: name= IDENT COLON t= type ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_attributeDefinition1668); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_attributeDefinition1670); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_attributeDefinition1674);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            // Cmd.g:414:29: ( SEMI )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==SEMI) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // Cmd.g:414:31: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_attributeDefinition1678); if (state.failed) return n;

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
    // Cmd.g:423:1: operationDefinition returns [ASTOperation n] : name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression )? ( 'begin' al= alActionList 'end' )? (ppc= prePostClause )* ( SEMI )? ;
    public final ASTOperation operationDefinition() throws RecognitionException {
        ASTOperation n = null;

        Token name=null;
        List pl = null;

        ASTType t = null;

        ASTExpression e = null;

        ASTALActionList al = null;

        ASTPrePostClause ppc = null;


        try {
            // Cmd.g:424:1: (name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression )? ( 'begin' al= alActionList 'end' )? (ppc= prePostClause )* ( SEMI )? )
            // Cmd.g:425:5: name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression )? ( 'begin' al= alActionList 'end' )? (ppc= prePostClause )* ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationDefinition1716); if (state.failed) return n;
            pushFollow(FOLLOW_paramList_in_operationDefinition1724);
            pl=paramList();

            state._fsp--;
            if (state.failed) return n;
            // Cmd.g:427:5: ( COLON t= type )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==COLON) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // Cmd.g:427:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_operationDefinition1732); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_operationDefinition1736);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            // Cmd.g:428:5: ( EQUAL e= expression )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==EQUAL) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // Cmd.g:428:6: EQUAL e= expression
                    {
                    match(input,EQUAL,FOLLOW_EQUAL_in_operationDefinition1746); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_operationDefinition1750);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            // Cmd.g:429:2: ( 'begin' al= alActionList 'end' )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==68) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // Cmd.g:429:3: 'begin' al= alActionList 'end'
                    {
                    match(input,68,FOLLOW_68_in_operationDefinition1758); if (state.failed) return n;
                    pushFollow(FOLLOW_alActionList_in_operationDefinition1762);
                    al=alActionList();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,63,FOLLOW_63_in_operationDefinition1764); if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTOperation(name, pl, t, e, al); 
            }
            // Cmd.g:431:5: (ppc= prePostClause )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( ((LA37_0>=74 && LA37_0<=75)) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // Cmd.g:431:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_operationDefinition1783);
            	    ppc=prePostClause();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addPrePostClause(ppc); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop37;
                }
            } while (true);

            // Cmd.g:432:5: ( SEMI )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==SEMI) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // Cmd.g:432:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_operationDefinition1796); if (state.failed) return n;

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
    // Cmd.g:442:1: associationDefinition returns [ASTAssociation n] : ( 'association' | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end' ;
    public final ASTAssociation associationDefinition() throws RecognitionException {
        ASTAssociation n = null;

        Token name=null;
        ASTAssociationEnd ae = null;


         Token t = null; 
        try {
            // Cmd.g:444:1: ( ( 'association' | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end' )
            // Cmd.g:445:5: ( 'association' | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end'
            {
            if ( state.backtracking==0 ) {
               t = input.LT(1); 
            }
            if ( (input.LA(1)>=66 && input.LA(1)<=67)||input.LA(1)==69 ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationDefinition1857); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociation(t, name); 
            }
            match(input,45,FOLLOW_45_in_associationDefinition1865); if (state.failed) return n;
            pushFollow(FOLLOW_associationEnd_in_associationDefinition1873);
            ae=associationEnd();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addEnd(ae); 
            }
            // Cmd.g:451:5: (ae= associationEnd )+
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
            	    // Cmd.g:451:7: ae= associationEnd
            	    {
            	    pushFollow(FOLLOW_associationEnd_in_associationDefinition1885);
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

            match(input,63,FOLLOW_63_in_associationDefinition1896); if (state.failed) return n;

            }

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
    // Cmd.g:460:1: associationEnd returns [ASTAssociationEnd n] : name= IDENT LBRACK m= multiplicity RBRACK ( 'role' rn= IDENT )? ( 'ordered' )? ( SEMI )? ;
    public final ASTAssociationEnd associationEnd() throws RecognitionException {
        ASTAssociationEnd n = null;

        Token name=null;
        Token rn=null;
        ASTMultiplicity m = null;


        try {
            // Cmd.g:461:1: (name= IDENT LBRACK m= multiplicity RBRACK ( 'role' rn= IDENT )? ( 'ordered' )? ( SEMI )? )
            // Cmd.g:462:5: name= IDENT LBRACK m= multiplicity RBRACK ( 'role' rn= IDENT )? ( 'ordered' )? ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd1922); if (state.failed) return n;
            match(input,LBRACK,FOLLOW_LBRACK_in_associationEnd1924); if (state.failed) return n;
            pushFollow(FOLLOW_multiplicity_in_associationEnd1928);
            m=multiplicity();

            state._fsp--;
            if (state.failed) return n;
            match(input,RBRACK,FOLLOW_RBRACK_in_associationEnd1930); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociationEnd(name, m); 
            }
            // Cmd.g:464:5: ( 'role' rn= IDENT )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==70) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // Cmd.g:464:7: 'role' rn= IDENT
                    {
                    match(input,70,FOLLOW_70_in_associationEnd1946); if (state.failed) return n;
                    rn=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd1950); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setRolename(rn); 
                    }

                    }
                    break;

            }

            // Cmd.g:465:5: ( 'ordered' )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==71) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // Cmd.g:465:7: 'ordered'
                    {
                    match(input,71,FOLLOW_71_in_associationEnd1963); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setOrdered(); 
                    }

                    }
                    break;

            }

            // Cmd.g:466:5: ( SEMI )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==SEMI) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // Cmd.g:466:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_associationEnd1976); if (state.failed) return n;

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
    // Cmd.g:480:1: multiplicity returns [ASTMultiplicity n] : mr= multiplicityRange ( COMMA mr= multiplicityRange )* ;
    public final ASTMultiplicity multiplicity() throws RecognitionException {
        ASTMultiplicity n = null;

        ASTMultiplicityRange mr = null;


        try {
            // Cmd.g:481:1: (mr= multiplicityRange ( COMMA mr= multiplicityRange )* )
            // Cmd.g:482:5: mr= multiplicityRange ( COMMA mr= multiplicityRange )*
            {
            if ( state.backtracking==0 ) {
               
              	Token t = input.LT(1); // remember start position of expression
              	n = new ASTMultiplicity(t);
                  
            }
            pushFollow(FOLLOW_multiplicityRange_in_multiplicity2011);
            mr=multiplicityRange();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addRange(mr); 
            }
            // Cmd.g:487:5: ( COMMA mr= multiplicityRange )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==COMMA) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // Cmd.g:487:7: COMMA mr= multiplicityRange
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_multiplicity2021); if (state.failed) return n;
            	    pushFollow(FOLLOW_multiplicityRange_in_multiplicity2025);
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
    // Cmd.g:490:1: multiplicityRange returns [ASTMultiplicityRange n] : ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? ;
    public final ASTMultiplicityRange multiplicityRange() throws RecognitionException {
        ASTMultiplicityRange n = null;

        int ms1 = 0;

        int ms2 = 0;


        try {
            // Cmd.g:491:1: (ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? )
            // Cmd.g:492:5: ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )?
            {
            pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange2054);
            ms1=multiplicitySpec();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTMultiplicityRange(ms1); 
            }
            // Cmd.g:493:5: ( DOTDOT ms2= multiplicitySpec )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==DOTDOT) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // Cmd.g:493:7: DOTDOT ms2= multiplicitySpec
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_multiplicityRange2064); if (state.failed) return n;
                    pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange2068);
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
    // Cmd.g:496:1: multiplicitySpec returns [int m] : (i= INT | STAR );
    public final int multiplicitySpec() throws RecognitionException {
        int m = 0;

        Token i=null;

         m = -1; 
        try {
            // Cmd.g:498:1: (i= INT | STAR )
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
                    // Cmd.g:499:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_multiplicitySpec2102); if (state.failed) return m;
                    if ( state.backtracking==0 ) {
                       m = Integer.parseInt((i!=null?i.getText():null)); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:500:7: STAR
                    {
                    match(input,STAR,FOLLOW_STAR_in_multiplicitySpec2112); if (state.failed) return m;
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
    // Cmd.g:521:1: invariant returns [ASTConstraintDefinition n] : 'context' (v= IDENT COLON )? t= simpleType (inv= invariantClause )* ;
    public final ASTConstraintDefinition invariant() throws RecognitionException {
        ASTConstraintDefinition n = null;

        Token v=null;
        ASTSimpleType t = null;

        ASTInvariantClause inv = null;


        try {
            // Cmd.g:522:1: ( 'context' (v= IDENT COLON )? t= simpleType (inv= invariantClause )* )
            // Cmd.g:523:5: 'context' (v= IDENT COLON )? t= simpleType (inv= invariantClause )*
            {
            if ( state.backtracking==0 ) {
               n = new ASTConstraintDefinition(); 
            }
            match(input,72,FOLLOW_72_in_invariant2153); if (state.failed) return n;
            // Cmd.g:525:5: (v= IDENT COLON )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==IDENT) ) {
                int LA46_1 = input.LA(2);

                if ( (LA46_1==COLON) ) {
                    alt46=1;
                }
            }
            switch (alt46) {
                case 1 :
                    // Cmd.g:525:7: v= IDENT COLON
                    {
                    v=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariant2163); if (state.failed) return n;
                    match(input,COLON,FOLLOW_COLON_in_invariant2165); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setVarName(v); 
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_simpleType_in_invariant2180);
            t=simpleType();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setType(t); 
            }
            // Cmd.g:528:5: (inv= invariantClause )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==73) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // Cmd.g:528:7: inv= invariantClause
            	    {
            	    pushFollow(FOLLOW_invariantClause_in_invariant2192);
            	    inv=invariantClause();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addInvariantClause(inv); 
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
    // $ANTLR end "invariant"


    // $ANTLR start "invariantClause"
    // Cmd.g:536:1: invariantClause returns [ASTInvariantClause n] : 'inv' (name= IDENT )? COLON e= expression ;
    public final ASTInvariantClause invariantClause() throws RecognitionException {
        ASTInvariantClause n = null;

        Token name=null;
        ASTExpression e = null;


        try {
            // Cmd.g:537:1: ( 'inv' (name= IDENT )? COLON e= expression )
            // Cmd.g:538:5: 'inv' (name= IDENT )? COLON e= expression
            {
            match(input,73,FOLLOW_73_in_invariantClause2222); if (state.failed) return n;
            // Cmd.g:538:11: (name= IDENT )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==IDENT) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // Cmd.g:538:13: name= IDENT
                    {
                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantClause2228); if (state.failed) return n;

                    }
                    break;

            }

            match(input,COLON,FOLLOW_COLON_in_invariantClause2233); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_invariantClause2237);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTInvariantClause(name, e); 
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
    // $ANTLR end "invariantClause"


    // $ANTLR start "prePost"
    // Cmd.g:550:1: prePost returns [ASTPrePost n] : 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ ;
    public final ASTPrePost prePost() throws RecognitionException {
        ASTPrePost n = null;

        Token classname=null;
        Token opname=null;
        List pl = null;

        ASTType rt = null;

        ASTPrePostClause ppc = null;


        try {
            // Cmd.g:551:1: ( 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ )
            // Cmd.g:552:5: 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+
            {
            match(input,72,FOLLOW_72_in_prePost2267); if (state.failed) return n;
            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost2271); if (state.failed) return n;
            match(input,COLON_COLON,FOLLOW_COLON_COLON_in_prePost2273); if (state.failed) return n;
            opname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost2277); if (state.failed) return n;
            pushFollow(FOLLOW_paramList_in_prePost2281);
            pl=paramList();

            state._fsp--;
            if (state.failed) return n;
            // Cmd.g:552:69: ( COLON rt= type )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==COLON) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // Cmd.g:552:71: COLON rt= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_prePost2285); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_prePost2289);
                    rt=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTPrePost(classname, opname, pl, rt); 
            }
            // Cmd.g:554:5: (ppc= prePostClause )+
            int cnt50=0;
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( ((LA50_0>=74 && LA50_0<=75)) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // Cmd.g:554:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_prePost2308);
            	    ppc=prePostClause();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addPrePostClause(ppc); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt50 >= 1 ) break loop50;
            	    if (state.backtracking>0) {state.failed=true; return n;}
                        EarlyExitException eee =
                            new EarlyExitException(50, input);
                        throw eee;
                }
                cnt50++;
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
    // Cmd.g:561:1: prePostClause returns [ASTPrePostClause n] : ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression ;
    public final ASTPrePostClause prePostClause() throws RecognitionException {
        ASTPrePostClause n = null;

        Token name=null;
        ASTExpression e = null;


         Token t = null; 
        try {
            // Cmd.g:563:1: ( ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression )
            // Cmd.g:564:5: ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression
            {
            if ( state.backtracking==0 ) {
               t = input.LT(1); 
            }
            if ( (input.LA(1)>=74 && input.LA(1)<=75) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // Cmd.g:565:25: (name= IDENT )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==IDENT) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // Cmd.g:565:27: name= IDENT
                    {
                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePostClause2362); if (state.failed) return n;

                    }
                    break;

            }

            match(input,COLON,FOLLOW_COLON_in_prePostClause2367); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_prePostClause2371);
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


    // $ANTLR start "alActionList"
    // Cmd.g:569:1: alActionList returns [ASTALActionList al] : (action= alAction )* ;
    public final ASTALActionList alActionList() throws RecognitionException {
        ASTALActionList al = null;

        ASTALAction action = null;



        	al = new ASTALActionList();

        try {
            // Cmd.g:573:1: ( (action= alAction )* )
            // Cmd.g:574:2: (action= alAction )*
            {
            // Cmd.g:574:2: (action= alAction )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==43||(LA52_0>=46 && LA52_0<=47)||LA52_0==49||LA52_0==51||LA52_0==55||(LA52_0>=76 && LA52_0<=77)||LA52_0==79||LA52_0==83||LA52_0==86) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // Cmd.g:574:4: action= alAction
            	    {
            	    pushFollow(FOLLOW_alAction_in_alActionList2404);
            	    action=alAction();

            	    state._fsp--;
            	    if (state.failed) return al;
            	    if ( state.backtracking==0 ) {
            	      al.add(action);
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
        return al;
    }
    // $ANTLR end "alActionList"


    // $ANTLR start "alAction"
    // Cmd.g:577:1: alAction returns [ASTALAction action] : (aCV= alCreateVar | aDl= alDelete | aSe= alSet | aSC= alSetCreate | aIn= alInsert | aDe= alDestroy | aIf= alIf | aWh= alWhile | aFo= alFor | aEx= alExec );
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
            // Cmd.g:578:1: (aCV= alCreateVar | aDl= alDelete | aSe= alSet | aSC= alSetCreate | aIn= alInsert | aDe= alDestroy | aIf= alIf | aWh= alWhile | aFo= alFor | aEx= alExec )
            int alt53=10;
            switch ( input.LA(1) ) {
            case 76:
            case 77:
                {
                alt53=1;
                }
                break;
            case 49:
                {
                alt53=2;
                }
                break;
            case 51:
                {
                alt53=3;
                }
                break;
            case 43:
                {
                alt53=4;
                }
                break;
            case 47:
                {
                alt53=5;
                }
                break;
            case 46:
                {
                alt53=6;
                }
                break;
            case 79:
                {
                alt53=7;
                }
                break;
            case 83:
                {
                alt53=8;
                }
                break;
            case 86:
                {
                alt53=9;
                }
                break;
            case 55:
                {
                alt53=10;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return action;}
                NoViableAltException nvae =
                    new NoViableAltException("", 53, 0, input);

                throw nvae;
            }

            switch (alt53) {
                case 1 :
                    // Cmd.g:579:5: aCV= alCreateVar
                    {
                    pushFollow(FOLLOW_alCreateVar_in_alAction2430);
                    aCV=alCreateVar();

                    state._fsp--;
                    if (state.failed) return action;
                    if ( state.backtracking==0 ) {
                       action = aCV; 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:580:5: aDl= alDelete
                    {
                    pushFollow(FOLLOW_alDelete_in_alAction2442);
                    aDl=alDelete();

                    state._fsp--;
                    if (state.failed) return action;
                    if ( state.backtracking==0 ) {
                       action = aDl; 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:581:5: aSe= alSet
                    {
                    pushFollow(FOLLOW_alSet_in_alAction2454);
                    aSe=alSet();

                    state._fsp--;
                    if (state.failed) return action;
                    if ( state.backtracking==0 ) {
                       action = aSe; 
                    }

                    }
                    break;
                case 4 :
                    // Cmd.g:582:5: aSC= alSetCreate
                    {
                    pushFollow(FOLLOW_alSetCreate_in_alAction2466);
                    aSC=alSetCreate();

                    state._fsp--;
                    if (state.failed) return action;
                    if ( state.backtracking==0 ) {
                       action = aSC; 
                    }

                    }
                    break;
                case 5 :
                    // Cmd.g:583:5: aIn= alInsert
                    {
                    pushFollow(FOLLOW_alInsert_in_alAction2478);
                    aIn=alInsert();

                    state._fsp--;
                    if (state.failed) return action;
                    if ( state.backtracking==0 ) {
                       action = aIn; 
                    }

                    }
                    break;
                case 6 :
                    // Cmd.g:584:5: aDe= alDestroy
                    {
                    pushFollow(FOLLOW_alDestroy_in_alAction2490);
                    aDe=alDestroy();

                    state._fsp--;
                    if (state.failed) return action;
                    if ( state.backtracking==0 ) {
                       action = aDe; 
                    }

                    }
                    break;
                case 7 :
                    // Cmd.g:585:5: aIf= alIf
                    {
                    pushFollow(FOLLOW_alIf_in_alAction2502);
                    aIf=alIf();

                    state._fsp--;
                    if (state.failed) return action;
                    if ( state.backtracking==0 ) {
                       action = aIf; 
                    }

                    }
                    break;
                case 8 :
                    // Cmd.g:586:5: aWh= alWhile
                    {
                    pushFollow(FOLLOW_alWhile_in_alAction2514);
                    aWh=alWhile();

                    state._fsp--;
                    if (state.failed) return action;
                    if ( state.backtracking==0 ) {
                       action = aWh; 
                    }

                    }
                    break;
                case 9 :
                    // Cmd.g:587:5: aFo= alFor
                    {
                    pushFollow(FOLLOW_alFor_in_alAction2526);
                    aFo=alFor();

                    state._fsp--;
                    if (state.failed) return action;
                    if ( state.backtracking==0 ) {
                       action = aFo; 
                    }

                    }
                    break;
                case 10 :
                    // Cmd.g:588:5: aEx= alExec
                    {
                    pushFollow(FOLLOW_alExec_in_alAction2538);
                    aEx=alExec();

                    state._fsp--;
                    if (state.failed) return action;
                    if ( state.backtracking==0 ) {
                       action = aEx; 
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
        return action;
    }
    // $ANTLR end "alAction"


    // $ANTLR start "alCreateVar"
    // Cmd.g:593:1: alCreateVar returns [ASTALCreateVar var] : ( 'var' | 'declare' ) name= IDENT COLON t= type ;
    public final ASTALCreateVar alCreateVar() throws RecognitionException {
        ASTALCreateVar var = null;

        Token name=null;
        ASTType t = null;


        try {
            // Cmd.g:594:1: ( ( 'var' | 'declare' ) name= IDENT COLON t= type )
            // Cmd.g:595:2: ( 'var' | 'declare' ) name= IDENT COLON t= type
            {
            if ( (input.LA(1)>=76 && input.LA(1)<=77) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return var;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_alCreateVar2565); if (state.failed) return var;
            match(input,COLON,FOLLOW_COLON_in_alCreateVar2567); if (state.failed) return var;
            pushFollow(FOLLOW_type_in_alCreateVar2571);
            t=type();

            state._fsp--;
            if (state.failed) return var;
            if ( state.backtracking==0 ) {
               var = new ASTALCreateVar(name, t); 
            }

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
    // Cmd.g:599:1: alSet returns [ASTALSet set] : 'set' lval= expression COLON_EQUAL rval= expression ;
    public final ASTALSet alSet() throws RecognitionException {
        ASTALSet set = null;

        ASTExpression lval = null;

        ASTExpression rval = null;


        try {
            // Cmd.g:600:1: ( 'set' lval= expression COLON_EQUAL rval= expression )
            // Cmd.g:602:5: 'set' lval= expression COLON_EQUAL rval= expression
            {
            match(input,51,FOLLOW_51_in_alSet2593); if (state.failed) return set;
            pushFollow(FOLLOW_expression_in_alSet2597);
            lval=expression();

            state._fsp--;
            if (state.failed) return set;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_alSet2599); if (state.failed) return set;
            pushFollow(FOLLOW_expression_in_alSet2603);
            rval=expression();

            state._fsp--;
            if (state.failed) return set;
            if ( state.backtracking==0 ) {
               set = new ASTALSet(lval, rval); 
            }

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
    // Cmd.g:606:1: alSetCreate returns [ASTALSetCreate setcreate] : 'create' lval= expression COLON_EQUAL {...}?new_= IDENT cls= IDENT ( 'namehint' nameExpr= expression )? ;
    public final ASTALSetCreate alSetCreate() throws RecognitionException {
        ASTALSetCreate setcreate = null;

        Token new_=null;
        Token cls=null;
        ASTExpression lval = null;

        ASTExpression nameExpr = null;


        try {
            // Cmd.g:607:1: ( 'create' lval= expression COLON_EQUAL {...}?new_= IDENT cls= IDENT ( 'namehint' nameExpr= expression )? )
            // Cmd.g:608:5: 'create' lval= expression COLON_EQUAL {...}?new_= IDENT cls= IDENT ( 'namehint' nameExpr= expression )?
            {
            match(input,43,FOLLOW_43_in_alSetCreate2627); if (state.failed) return setcreate;
            pushFollow(FOLLOW_expression_in_alSetCreate2631);
            lval=expression();

            state._fsp--;
            if (state.failed) return setcreate;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_alSetCreate2633); if (state.failed) return setcreate;
            if ( !(( input.LT(1).getText().equals("new") )) ) {
                if (state.backtracking>0) {state.failed=true; return setcreate;}
                throw new FailedPredicateException(input, "alSetCreate", " input.LT(1).getText().equals(\"new\") ");
            }
            new_=(Token)match(input,IDENT,FOLLOW_IDENT_in_alSetCreate2639); if (state.failed) return setcreate;
            cls=(Token)match(input,IDENT,FOLLOW_IDENT_in_alSetCreate2643); if (state.failed) return setcreate;
            // Cmd.g:609:5: ( 'namehint' nameExpr= expression )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==78) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // Cmd.g:609:7: 'namehint' nameExpr= expression
                    {
                    match(input,78,FOLLOW_78_in_alSetCreate2652); if (state.failed) return setcreate;
                    pushFollow(FOLLOW_expression_in_alSetCreate2656);
                    nameExpr=expression();

                    state._fsp--;
                    if (state.failed) return setcreate;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               setcreate = new ASTALSetCreate(lval, cls, nameExpr);
            }

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
    // Cmd.g:614:1: alInsert returns [ASTALInsert insert] : 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT ;
    public final ASTALInsert alInsert() throws RecognitionException {
        ASTALInsert insert = null;

        Token id=null;
        ASTExpression e = null;


        List exprList = new ArrayList(); 
        try {
            // Cmd.g:616:1: ( 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT )
            // Cmd.g:617:5: 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT
            {
            match(input,47,FOLLOW_47_in_alInsert2687); if (state.failed) return insert;
            match(input,LPAREN,FOLLOW_LPAREN_in_alInsert2689); if (state.failed) return insert;
            pushFollow(FOLLOW_expression_in_alInsert2698);
            e=expression();

            state._fsp--;
            if (state.failed) return insert;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_alInsert2702); if (state.failed) return insert;
            pushFollow(FOLLOW_expression_in_alInsert2710);
            e=expression();

            state._fsp--;
            if (state.failed) return insert;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // Cmd.g:619:42: ( COMMA e= expression )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==COMMA) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // Cmd.g:619:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_alInsert2716); if (state.failed) return insert;
            	    pushFollow(FOLLOW_expression_in_alInsert2720);
            	    e=expression();

            	    state._fsp--;
            	    if (state.failed) return insert;
            	    if ( state.backtracking==0 ) {
            	       exprList.add(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop55;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_alInsert2732); if (state.failed) return insert;
            match(input,48,FOLLOW_48_in_alInsert2734); if (state.failed) return insert;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_alInsert2738); if (state.failed) return insert;
            if ( state.backtracking==0 ) {
               insert = new ASTALInsert(exprList, id); 
            }

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
    // Cmd.g:625:1: alDelete returns [ASTALDelete n] : 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT ;
    public final ASTALDelete alDelete() throws RecognitionException {
        ASTALDelete n = null;

        Token id=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // Cmd.g:627:1: ( 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT )
            // Cmd.g:628:5: 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT
            {
            match(input,49,FOLLOW_49_in_alDelete2770); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_alDelete2772); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_alDelete2780);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_alDelete2784); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_alDelete2792);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // Cmd.g:630:42: ( COMMA e= expression )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==COMMA) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // Cmd.g:630:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_alDelete2798); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_alDelete2802);
            	    e=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       exprList.add(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop56;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_alDelete2813); if (state.failed) return n;
            match(input,50,FOLLOW_50_in_alDelete2815); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_alDelete2819); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTALDelete(exprList, id); 
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
    // $ANTLR end "alDelete"


    // $ANTLR start "alDestroy"
    // Cmd.g:636:1: alDestroy returns [ASTALDestroy n] : 'destroy' e= expression ;
    public final ASTALDestroy alDestroy() throws RecognitionException {
        ASTALDestroy n = null;

        ASTExpression e = null;


        try {
            // Cmd.g:637:1: ( 'destroy' e= expression )
            // Cmd.g:638:6: 'destroy' e= expression
            {
            match(input,46,FOLLOW_46_in_alDestroy2848); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_alDestroy2852);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTALDestroy(e); 
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
    // $ANTLR end "alDestroy"


    // $ANTLR start "alIf"
    // Cmd.g:642:1: alIf returns [ASTALIf i] : 'if' ifexpr= expression 'then' thenlist= alActionList ( 'else' elselist= alActionList )? 'endif' ;
    public final ASTALIf alIf() throws RecognitionException {
        ASTALIf i = null;

        ASTExpression ifexpr = null;

        ASTALActionList thenlist = null;

        ASTALActionList elselist = null;


        try {
            // Cmd.g:643:1: ( 'if' ifexpr= expression 'then' thenlist= alActionList ( 'else' elselist= alActionList )? 'endif' )
            // Cmd.g:644:2: 'if' ifexpr= expression 'then' thenlist= alActionList ( 'else' elselist= alActionList )? 'endif'
            {
            match(input,79,FOLLOW_79_in_alIf2876); if (state.failed) return i;
            pushFollow(FOLLOW_expression_in_alIf2880);
            ifexpr=expression();

            state._fsp--;
            if (state.failed) return i;
            match(input,80,FOLLOW_80_in_alIf2884); if (state.failed) return i;
            pushFollow(FOLLOW_alActionList_in_alIf2888);
            thenlist=alActionList();

            state._fsp--;
            if (state.failed) return i;
            // Cmd.g:646:2: ( 'else' elselist= alActionList )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==81) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // Cmd.g:646:3: 'else' elselist= alActionList
                    {
                    match(input,81,FOLLOW_81_in_alIf2892); if (state.failed) return i;
                    pushFollow(FOLLOW_alActionList_in_alIf2896);
                    elselist=alActionList();

                    state._fsp--;
                    if (state.failed) return i;

                    }
                    break;

            }

            match(input,82,FOLLOW_82_in_alIf2901); if (state.failed) return i;
            if ( state.backtracking==0 ) {
               i = new ASTALIf(ifexpr, thenlist, elselist); 
            }

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
    // Cmd.g:651:1: alWhile returns [ASTALWhile w] : 'while' expr= expression 'do' body= alActionList 'wend' ;
    public final ASTALWhile alWhile() throws RecognitionException {
        ASTALWhile w = null;

        ASTExpression expr = null;

        ASTALActionList body = null;


        try {
            // Cmd.g:652:1: ( 'while' expr= expression 'do' body= alActionList 'wend' )
            // Cmd.g:653:2: 'while' expr= expression 'do' body= alActionList 'wend'
            {
            match(input,83,FOLLOW_83_in_alWhile2920); if (state.failed) return w;
            pushFollow(FOLLOW_expression_in_alWhile2924);
            expr=expression();

            state._fsp--;
            if (state.failed) return w;
            match(input,84,FOLLOW_84_in_alWhile2928); if (state.failed) return w;
            pushFollow(FOLLOW_alActionList_in_alWhile2934);
            body=alActionList();

            state._fsp--;
            if (state.failed) return w;
            match(input,85,FOLLOW_85_in_alWhile2937); if (state.failed) return w;
            if ( state.backtracking==0 ) {
               w = new ASTALWhile(expr, body); 
            }

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
    // Cmd.g:661:1: alFor returns [ASTALFor f] : 'for' var= IDENT COLON t= type 'in' expr= expression 'do' body= alActionList {...}?next= IDENT ;
    public final ASTALFor alFor() throws RecognitionException {
        ASTALFor f = null;

        Token var=null;
        Token next=null;
        ASTType t = null;

        ASTExpression expr = null;

        ASTALActionList body = null;


        try {
            // Cmd.g:662:1: ( 'for' var= IDENT COLON t= type 'in' expr= expression 'do' body= alActionList {...}?next= IDENT )
            // Cmd.g:663:2: 'for' var= IDENT COLON t= type 'in' expr= expression 'do' body= alActionList {...}?next= IDENT
            {
            match(input,86,FOLLOW_86_in_alFor2956); if (state.failed) return f;
            var=(Token)match(input,IDENT,FOLLOW_IDENT_in_alFor2960); if (state.failed) return f;
            match(input,COLON,FOLLOW_COLON_in_alFor2962); if (state.failed) return f;
            pushFollow(FOLLOW_type_in_alFor2966);
            t=type();

            state._fsp--;
            if (state.failed) return f;
            match(input,87,FOLLOW_87_in_alFor2968); if (state.failed) return f;
            pushFollow(FOLLOW_expression_in_alFor2972);
            expr=expression();

            state._fsp--;
            if (state.failed) return f;
            match(input,84,FOLLOW_84_in_alFor2976); if (state.failed) return f;
            pushFollow(FOLLOW_alActionList_in_alFor2982);
            body=alActionList();

            state._fsp--;
            if (state.failed) return f;
            if ( !(( input.LT(1).getText().equals("next") )) ) {
                if (state.backtracking>0) {state.failed=true; return f;}
                throw new FailedPredicateException(input, "alFor", " input.LT(1).getText().equals(\"next\") ");
            }
            next=(Token)match(input,IDENT,FOLLOW_IDENT_in_alFor2989); if (state.failed) return f;
            if ( state.backtracking==0 ) {
               f = new ASTALFor(var, t, expr, body); 
            }

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
    // Cmd.g:670:1: alExec returns [ASTALExecute c] : 'execute' op= expression ;
    public final ASTALExecute alExec() throws RecognitionException {
        ASTALExecute c = null;

        ASTExpression op = null;


        try {
            // Cmd.g:671:1: ( 'execute' op= expression )
            // Cmd.g:672:5: 'execute' op= expression
            {
            match(input,55,FOLLOW_55_in_alExec3009); if (state.failed) return c;
            pushFollow(FOLLOW_expression_in_alExec3013);
            op=expression();

            state._fsp--;
            if (state.failed) return c;
            if ( state.backtracking==0 ) {
               c = new ASTALExecute(op); 
            }

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
    // Cmd.g:704:1: expressionOnly returns [ASTExpression n] : nExp= expression EOF ;
    public final ASTExpression expressionOnly() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExp = null;


        try {
            // Cmd.g:705:1: (nExp= expression EOF )
            // Cmd.g:706:5: nExp= expression EOF
            {
            pushFollow(FOLLOW_expression_in_expressionOnly3048);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_expressionOnly3050); if (state.failed) return n;
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
    // Cmd.g:713:1: expression returns [ASTExpression n] : ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression ;
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
            // Cmd.g:719:1: ( ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression )
            // Cmd.g:720:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of expression */ 
            }
            // Cmd.g:721:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==54) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // Cmd.g:722:7: 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in'
            	    {
            	    match(input,54,FOLLOW_54_in_expression3098); if (state.failed) return n;
            	    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_expression3102); if (state.failed) return n;
            	    // Cmd.g:722:24: ( COLON t= type )?
            	    int alt58=2;
            	    int LA58_0 = input.LA(1);

            	    if ( (LA58_0==COLON) ) {
            	        alt58=1;
            	    }
            	    switch (alt58) {
            	        case 1 :
            	            // Cmd.g:722:26: COLON t= type
            	            {
            	            match(input,COLON,FOLLOW_COLON_in_expression3106); if (state.failed) return n;
            	            pushFollow(FOLLOW_type_in_expression3110);
            	            t=type();

            	            state._fsp--;
            	            if (state.failed) return n;

            	            }
            	            break;

            	    }

            	    match(input,EQUAL,FOLLOW_EQUAL_in_expression3115); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_expression3119);
            	    e1=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    match(input,87,FOLLOW_87_in_expression3121); if (state.failed) return n;
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
            	    break loop59;
                }
            } while (true);

            pushFollow(FOLLOW_conditionalImpliesExpression_in_expression3146);
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
    // Cmd.g:750:1: paramList returns [List paramList] : LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN ;
    public final List paramList() throws RecognitionException {
        List paramList = null;

        ASTVariableDeclaration v = null;


         paramList = new ArrayList(); 
        try {
            // Cmd.g:752:1: ( LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN )
            // Cmd.g:753:5: LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN
            {
            match(input,LPAREN,FOLLOW_LPAREN_in_paramList3179); if (state.failed) return paramList;
            // Cmd.g:754:5: (v= variableDeclaration ( COMMA v= variableDeclaration )* )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==IDENT) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // Cmd.g:755:7: v= variableDeclaration ( COMMA v= variableDeclaration )*
                    {
                    pushFollow(FOLLOW_variableDeclaration_in_paramList3196);
                    v=variableDeclaration();

                    state._fsp--;
                    if (state.failed) return paramList;
                    if ( state.backtracking==0 ) {
                       paramList.add(v); 
                    }
                    // Cmd.g:756:7: ( COMMA v= variableDeclaration )*
                    loop60:
                    do {
                        int alt60=2;
                        int LA60_0 = input.LA(1);

                        if ( (LA60_0==COMMA) ) {
                            alt60=1;
                        }


                        switch (alt60) {
                    	case 1 :
                    	    // Cmd.g:756:9: COMMA v= variableDeclaration
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_paramList3208); if (state.failed) return paramList;
                    	    pushFollow(FOLLOW_variableDeclaration_in_paramList3212);
                    	    v=variableDeclaration();

                    	    state._fsp--;
                    	    if (state.failed) return paramList;
                    	    if ( state.backtracking==0 ) {
                    	       paramList.add(v); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop60;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_paramList3232); if (state.failed) return paramList;

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
    // Cmd.g:764:1: idList returns [List idList] : id0= IDENT ( COMMA idn= IDENT )* ;
    public final List idList() throws RecognitionException {
        List idList = null;

        Token id0=null;
        Token idn=null;

         idList = new ArrayList(); 
        try {
            // Cmd.g:766:1: (id0= IDENT ( COMMA idn= IDENT )* )
            // Cmd.g:767:5: id0= IDENT ( COMMA idn= IDENT )*
            {
            id0=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList3261); if (state.failed) return idList;
            if ( state.backtracking==0 ) {
               idList.add(id0); 
            }
            // Cmd.g:768:5: ( COMMA idn= IDENT )*
            loop62:
            do {
                int alt62=2;
                int LA62_0 = input.LA(1);

                if ( (LA62_0==COMMA) ) {
                    alt62=1;
                }


                switch (alt62) {
            	case 1 :
            	    // Cmd.g:768:7: COMMA idn= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_idList3271); if (state.failed) return idList;
            	    idn=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList3275); if (state.failed) return idList;
            	    if ( state.backtracking==0 ) {
            	       idList.add(idn); 
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
        return idList;
    }
    // $ANTLR end "idList"


    // $ANTLR start "variableDeclaration"
    // Cmd.g:776:1: variableDeclaration returns [ASTVariableDeclaration n] : name= IDENT COLON t= type ;
    public final ASTVariableDeclaration variableDeclaration() throws RecognitionException {
        ASTVariableDeclaration n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Cmd.g:777:1: (name= IDENT COLON t= type )
            // Cmd.g:778:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableDeclaration3306); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableDeclaration3308); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableDeclaration3312);
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
    // Cmd.g:786:1: conditionalImpliesExpression returns [ASTExpression n] : nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* ;
    public final ASTExpression conditionalImpliesExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndOrExp = null;

        ASTExpression n1 = null;


        try {
            // Cmd.g:787:1: (nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* )
            // Cmd.g:788:5: nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )*
            {
            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3348);
            nCndOrExp=conditionalOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndOrExp;
            }
            // Cmd.g:789:5: (op= 'implies' n1= conditionalOrExpression )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==88) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // Cmd.g:789:7: op= 'implies' n1= conditionalOrExpression
            	    {
            	    op=(Token)match(input,88,FOLLOW_88_in_conditionalImpliesExpression3361); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3365);
            	    n1=conditionalOrExpression();

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
    // $ANTLR end "conditionalImpliesExpression"


    // $ANTLR start "conditionalOrExpression"
    // Cmd.g:798:1: conditionalOrExpression returns [ASTExpression n] : nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* ;
    public final ASTExpression conditionalOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndXorExp = null;

        ASTExpression n1 = null;


        try {
            // Cmd.g:799:1: (nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* )
            // Cmd.g:800:5: nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )*
            {
            pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3410);
            nCndXorExp=conditionalXOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndXorExp;
            }
            // Cmd.g:801:5: (op= 'or' n1= conditionalXOrExpression )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==89) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // Cmd.g:801:7: op= 'or' n1= conditionalXOrExpression
            	    {
            	    op=(Token)match(input,89,FOLLOW_89_in_conditionalOrExpression3423); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3427);
            	    n1=conditionalXOrExpression();

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
    // $ANTLR end "conditionalOrExpression"


    // $ANTLR start "conditionalXOrExpression"
    // Cmd.g:810:1: conditionalXOrExpression returns [ASTExpression n] : nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* ;
    public final ASTExpression conditionalXOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndAndExp = null;

        ASTExpression n1 = null;


        try {
            // Cmd.g:811:1: (nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* )
            // Cmd.g:812:5: nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )*
            {
            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3471);
            nCndAndExp=conditionalAndExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndAndExp;
            }
            // Cmd.g:813:5: (op= 'xor' n1= conditionalAndExpression )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( (LA65_0==90) ) {
                    alt65=1;
                }


                switch (alt65) {
            	case 1 :
            	    // Cmd.g:813:7: op= 'xor' n1= conditionalAndExpression
            	    {
            	    op=(Token)match(input,90,FOLLOW_90_in_conditionalXOrExpression3484); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3488);
            	    n1=conditionalAndExpression();

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
    // $ANTLR end "conditionalXOrExpression"


    // $ANTLR start "conditionalAndExpression"
    // Cmd.g:822:1: conditionalAndExpression returns [ASTExpression n] : nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* ;
    public final ASTExpression conditionalAndExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nEqExp = null;

        ASTExpression n1 = null;


        try {
            // Cmd.g:823:1: (nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* )
            // Cmd.g:824:5: nEqExp= equalityExpression (op= 'and' n1= equalityExpression )*
            {
            pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression3532);
            nEqExp=equalityExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nEqExp;
            }
            // Cmd.g:825:5: (op= 'and' n1= equalityExpression )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( (LA66_0==91) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // Cmd.g:825:7: op= 'and' n1= equalityExpression
            	    {
            	    op=(Token)match(input,91,FOLLOW_91_in_conditionalAndExpression3545); if (state.failed) return n;
            	    pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression3549);
            	    n1=equalityExpression();

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
    // $ANTLR end "conditionalAndExpression"


    // $ANTLR start "equalityExpression"
    // Cmd.g:834:1: equalityExpression returns [ASTExpression n] : nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* ;
    public final ASTExpression equalityExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nRelExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Cmd.g:836:1: (nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* )
            // Cmd.g:837:5: nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            {
            pushFollow(FOLLOW_relationalExpression_in_equalityExpression3597);
            nRelExp=relationalExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nRelExp;
            }
            // Cmd.g:838:5: ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==EQUAL||LA67_0==NOT_EQUAL) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // Cmd.g:838:7: ( EQUAL | NOT_EQUAL ) n1= relationalExpression
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

            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression3626);
            	    n1=relationalExpression();

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
    // $ANTLR end "equalityExpression"


    // $ANTLR start "relationalExpression"
    // Cmd.g:848:1: relationalExpression returns [ASTExpression n] : nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* ;
    public final ASTExpression relationalExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nAddiExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Cmd.g:850:1: (nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* )
            // Cmd.g:851:5: nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            {
            pushFollow(FOLLOW_additiveExpression_in_relationalExpression3675);
            nAddiExp=additiveExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nAddiExp;
            }
            // Cmd.g:852:5: ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            loop68:
            do {
                int alt68=2;
                int LA68_0 = input.LA(1);

                if ( (LA68_0==LESS||(LA68_0>=GREATER && LA68_0<=GREATER_EQUAL)) ) {
                    alt68=1;
                }


                switch (alt68) {
            	case 1 :
            	    // Cmd.g:852:7: ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression
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

            	    pushFollow(FOLLOW_additiveExpression_in_relationalExpression3711);
            	    n1=additiveExpression();

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
    // $ANTLR end "relationalExpression"


    // $ANTLR start "additiveExpression"
    // Cmd.g:862:1: additiveExpression returns [ASTExpression n] : nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* ;
    public final ASTExpression additiveExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nMulExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Cmd.g:864:1: (nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* )
            // Cmd.g:865:5: nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            {
            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3761);
            nMulExp=multiplicativeExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nMulExp;
            }
            // Cmd.g:866:5: ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( ((LA69_0>=PLUS && LA69_0<=MINUS)) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // Cmd.g:866:7: ( PLUS | MINUS ) n1= multiplicativeExpression
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

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3789);
            	    n1=multiplicativeExpression();

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
    // $ANTLR end "additiveExpression"


    // $ANTLR start "multiplicativeExpression"
    // Cmd.g:877:1: multiplicativeExpression returns [ASTExpression n] : nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* ;
    public final ASTExpression multiplicativeExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Cmd.g:879:1: (nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* )
            // Cmd.g:880:5: nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            {
            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3839);
            nUnExp=unaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nUnExp;
            }
            // Cmd.g:881:5: ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            loop70:
            do {
                int alt70=2;
                int LA70_0 = input.LA(1);

                if ( (LA70_0==STAR||LA70_0==SLASH||LA70_0==92) ) {
                    alt70=1;
                }


                switch (alt70) {
            	case 1 :
            	    // Cmd.g:881:7: ( STAR | SLASH | 'div' ) n1= unaryExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       op = input.LT(1); 
            	    }
            	    if ( input.LA(1)==STAR||input.LA(1)==SLASH||input.LA(1)==92 ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3871);
            	    n1=unaryExpression();

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
    // $ANTLR end "multiplicativeExpression"


    // $ANTLR start "unaryExpression"
    // Cmd.g:893:1: unaryExpression returns [ASTExpression n] : ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression );
    public final ASTExpression unaryExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression nPosExp = null;


         Token op = null; 
        try {
            // Cmd.g:895:1: ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression )
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( ((LA71_0>=PLUS && LA71_0<=MINUS)||LA71_0==93) ) {
                alt71=1;
            }
            else if ( ((LA71_0>=IDENT && LA71_0<=LPAREN)||LA71_0==INT||(LA71_0>=REAL && LA71_0<=HASH)||LA71_0==79||(LA71_0>=95 && LA71_0<=108)) ) {
                alt71=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 71, 0, input);

                throw nvae;
            }
            switch (alt71) {
                case 1 :
                    // Cmd.g:896:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    {
                    // Cmd.g:896:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    // Cmd.g:896:9: ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression
                    {
                    if ( state.backtracking==0 ) {
                       op = input.LT(1); 
                    }
                    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS)||input.LA(1)==93 ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression3957);
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
                    // Cmd.g:900:7: nPosExp= postfixExpression
                    {
                    pushFollow(FOLLOW_postfixExpression_in_unaryExpression3977);
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
    // Cmd.g:908:1: postfixExpression returns [ASTExpression n] : nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* ;
    public final ASTExpression postfixExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nPrimExp = null;

        ASTExpression nPc = null;


         boolean arrow = false; 
        try {
            // Cmd.g:910:1: (nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* )
            // Cmd.g:911:5: nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            {
            pushFollow(FOLLOW_primaryExpression_in_postfixExpression4010);
            nPrimExp=primaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nPrimExp; 
            }
            // Cmd.g:912:5: ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            loop73:
            do {
                int alt73=2;
                int LA73_0 = input.LA(1);

                if ( ((LA73_0>=ARROW && LA73_0<=DOT)) ) {
                    alt73=1;
                }


                switch (alt73) {
            	case 1 :
            	    // Cmd.g:913:6: ( ARROW | DOT ) nPc= propertyCall[$n, arrow]
            	    {
            	    // Cmd.g:913:6: ( ARROW | DOT )
            	    int alt72=2;
            	    int LA72_0 = input.LA(1);

            	    if ( (LA72_0==ARROW) ) {
            	        alt72=1;
            	    }
            	    else if ( (LA72_0==DOT) ) {
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
            	            // Cmd.g:913:8: ARROW
            	            {
            	            match(input,ARROW,FOLLOW_ARROW_in_postfixExpression4028); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = true; 
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // Cmd.g:913:34: DOT
            	            {
            	            match(input,DOT,FOLLOW_DOT_in_postfixExpression4034); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = false; 
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_propertyCall_in_postfixExpression4045);
            	    nPc=propertyCall(n, arrow);

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = nPc; 
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
    // $ANTLR end "postfixExpression"


    // $ANTLR start "primaryExpression"
    // Cmd.g:929:1: primaryExpression returns [ASTExpression n] : (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? );
    public final ASTExpression primaryExpression() throws RecognitionException {
        ASTExpression n = null;

        Token id1=null;
        ASTExpression nLit = null;

        ASTExpression nPc = null;

        ASTExpression nExp = null;

        ASTExpression nIfExp = null;


        try {
            // Cmd.g:930:1: (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? )
            int alt76=5;
            switch ( input.LA(1) ) {
            case INT:
            case REAL:
            case STRING:
            case HASH:
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
                {
                alt76=1;
                }
                break;
            case IDENT:
                {
                int LA76_2 = input.LA(2);

                if ( (LA76_2==EOF||LA76_2==SEMI||(LA76_2>=COLON_EQUAL && LA76_2<=EQUAL)||(LA76_2>=RBRACE && LA76_2<=LBRACK)||LA76_2==DOTDOT||LA76_2==STAR||(LA76_2>=NOT_EQUAL && LA76_2<=ARROW)||(LA76_2>=AT && LA76_2<=BAR)||(LA76_2>=43 && LA76_2<=44)||(LA76_2>=46 && LA76_2<=47)||LA76_2==49||(LA76_2>=51 && LA76_2<=55)||LA76_2==57||(LA76_2>=59 && LA76_2<=60)||(LA76_2>=63 && LA76_2<=69)||(LA76_2>=72 && LA76_2<=77)||(LA76_2>=79 && LA76_2<=92)) ) {
                    alt76=2;
                }
                else if ( (LA76_2==DOT) ) {
                    int LA76_6 = input.LA(3);

                    if ( (LA76_6==94) ) {
                        alt76=5;
                    }
                    else if ( (LA76_6==IDENT||(LA76_6>=95 && LA76_6<=98)) ) {
                        alt76=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 76, 6, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 76, 2, input);

                    throw nvae;
                }
                }
                break;
            case 95:
            case 96:
            case 97:
            case 98:
                {
                alt76=2;
                }
                break;
            case LPAREN:
                {
                alt76=3;
                }
                break;
            case 79:
                {
                alt76=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 76, 0, input);

                throw nvae;
            }

            switch (alt76) {
                case 1 :
                    // Cmd.g:931:7: nLit= literal
                    {
                    pushFollow(FOLLOW_literal_in_primaryExpression4085);
                    nLit=literal();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nLit; 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:932:7: nPc= propertyCall[null, false]
                    {
                    pushFollow(FOLLOW_propertyCall_in_primaryExpression4097);
                    nPc=propertyCall(null, false);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nPc; 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:933:7: LPAREN nExp= expression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression4108); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_primaryExpression4112);
                    nExp=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression4114); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExp; 
                    }

                    }
                    break;
                case 4 :
                    // Cmd.g:934:7: nIfExp= ifExpression
                    {
                    pushFollow(FOLLOW_ifExpression_in_primaryExpression4126);
                    nIfExp=ifExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nIfExp; 
                    }

                    }
                    break;
                case 5 :
                    // Cmd.g:936:7: id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )?
                    {
                    id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_primaryExpression4143); if (state.failed) return n;
                    match(input,DOT,FOLLOW_DOT_in_primaryExpression4145); if (state.failed) return n;
                    match(input,94,FOLLOW_94_in_primaryExpression4147); if (state.failed) return n;
                    // Cmd.g:936:36: ( LPAREN RPAREN )?
                    int alt74=2;
                    int LA74_0 = input.LA(1);

                    if ( (LA74_0==LPAREN) ) {
                        alt74=1;
                    }
                    switch (alt74) {
                        case 1 :
                            // Cmd.g:936:38: LPAREN RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression4151); if (state.failed) return n;
                            match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression4153); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTAllInstancesExpression(id1); 
                    }
                    // Cmd.g:938:7: ( AT 'pre' )?
                    int alt75=2;
                    int LA75_0 = input.LA(1);

                    if ( (LA75_0==AT) ) {
                        alt75=1;
                    }
                    switch (alt75) {
                        case 1 :
                            // Cmd.g:938:9: AT 'pre'
                            {
                            match(input,AT,FOLLOW_AT_in_primaryExpression4174); if (state.failed) return n;
                            match(input,74,FOLLOW_74_in_primaryExpression4176); if (state.failed) return n;
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
    // Cmd.g:951:1: propertyCall[ASTExpression source, boolean followsArrow] returns [ASTExpression n] : ({...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] );
    public final ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExpQuery = null;

        ASTExpression nExpIterate = null;

        ASTOperationExpression nExpOperation = null;

        ASTTypeArgExpression nExpType = null;


        try {
            // Cmd.g:952:1: ({...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] )
            int alt77=4;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA77_1 = input.LA(2);

                if ( (( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                    alt77=1;
                }
                else if ( (true) ) {
                    alt77=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 77, 1, input);

                    throw nvae;
                }
                }
                break;
            case 95:
                {
                alt77=2;
                }
                break;
            case 96:
            case 97:
            case 98:
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
                    // Cmd.g:956:7: {...}?nExpQuery= queryExpression[source]
                    {
                    if ( !(( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) ");
                    }
                    pushFollow(FOLLOW_queryExpression_in_propertyCall4242);
                    nExpQuery=queryExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpQuery; 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:958:7: nExpIterate= iterateExpression[source]
                    {
                    pushFollow(FOLLOW_iterateExpression_in_propertyCall4255);
                    nExpIterate=iterateExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpIterate; 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:959:7: nExpOperation= operationExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_operationExpression_in_propertyCall4268);
                    nExpOperation=operationExpression(source, followsArrow);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpOperation; 
                    }

                    }
                    break;
                case 4 :
                    // Cmd.g:960:7: nExpType= typeExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_typeExpression_in_propertyCall4281);
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
    // Cmd.g:969:1: queryExpression[ASTExpression range] returns [ASTExpression n] : op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN ;
    public final ASTExpression queryExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTElemVarsDeclaration decls = null;

        ASTExpression nExp = null;


        ASTElemVarsDeclaration decl = new ASTElemVarsDeclaration(); 
        try {
            // Cmd.g:970:69: (op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN )
            // Cmd.g:971:5: op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN
            {
            op=(Token)match(input,IDENT,FOLLOW_IDENT_in_queryExpression4316); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_queryExpression4323); if (state.failed) return n;
            // Cmd.g:973:5: (decls= elemVarsDeclaration BAR )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==IDENT) ) {
                int LA78_1 = input.LA(2);

                if ( (LA78_1==COLON||LA78_1==COMMA||LA78_1==BAR) ) {
                    alt78=1;
                }
            }
            switch (alt78) {
                case 1 :
                    // Cmd.g:973:7: decls= elemVarsDeclaration BAR
                    {
                    pushFollow(FOLLOW_elemVarsDeclaration_in_queryExpression4334);
                    decls=elemVarsDeclaration();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      decl = decls;
                    }
                    match(input,BAR,FOLLOW_BAR_in_queryExpression4338); if (state.failed) return n;

                    }
                    break;

            }

            pushFollow(FOLLOW_expression_in_queryExpression4349);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_queryExpression4355); if (state.failed) return n;
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
    // Cmd.g:987:1: iterateExpression[ASTExpression range] returns [ASTExpression n] : i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN ;
    public final ASTExpression iterateExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTElemVarsDeclaration decls = null;

        ASTVariableInitialization init = null;

        ASTExpression nExp = null;


        try {
            // Cmd.g:987:65: (i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN )
            // Cmd.g:988:5: i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN
            {
            i=(Token)match(input,95,FOLLOW_95_in_iterateExpression4387); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_iterateExpression4393); if (state.failed) return n;
            pushFollow(FOLLOW_elemVarsDeclaration_in_iterateExpression4401);
            decls=elemVarsDeclaration();

            state._fsp--;
            if (state.failed) return n;
            match(input,SEMI,FOLLOW_SEMI_in_iterateExpression4403); if (state.failed) return n;
            pushFollow(FOLLOW_variableInitialization_in_iterateExpression4411);
            init=variableInitialization();

            state._fsp--;
            if (state.failed) return n;
            match(input,BAR,FOLLOW_BAR_in_iterateExpression4413); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_iterateExpression4421);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_iterateExpression4427); if (state.failed) return n;
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
    // Cmd.g:1009:1: operationExpression[ASTExpression source, boolean followsArrow] returns [ASTOperationExpression n] : name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? ;
    public final ASTOperationExpression operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTOperationExpression n = null;

        Token name=null;
        Token rolename=null;
        ASTExpression e = null;


        try {
            // Cmd.g:1011:1: (name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? )
            // Cmd.g:1012:5: name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression4471); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTOperationExpression(name, source, followsArrow); 
            }
            // Cmd.g:1015:5: ( LBRACK rolename= IDENT RBRACK )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==LBRACK) ) {
                alt79=1;
            }
            switch (alt79) {
                case 1 :
                    // Cmd.g:1015:7: LBRACK rolename= IDENT RBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_operationExpression4487); if (state.failed) return n;
                    rolename=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression4491); if (state.failed) return n;
                    match(input,RBRACK,FOLLOW_RBRACK_in_operationExpression4493); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setExplicitRolename(rolename); 
                    }

                    }
                    break;

            }

            // Cmd.g:1017:5: ( AT 'pre' )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==AT) ) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // Cmd.g:1017:7: AT 'pre'
                    {
                    match(input,AT,FOLLOW_AT_in_operationExpression4506); if (state.failed) return n;
                    match(input,74,FOLLOW_74_in_operationExpression4508); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setIsPre(); 
                    }

                    }
                    break;

            }

            // Cmd.g:1018:5: ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==LPAREN) ) {
                alt83=1;
            }
            switch (alt83) {
                case 1 :
                    // Cmd.g:1019:7: LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_operationExpression4529); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.hasParentheses(); 
                    }
                    // Cmd.g:1020:7: (e= expression ( COMMA e= expression )* )?
                    int alt82=2;
                    int LA82_0 = input.LA(1);

                    if ( ((LA82_0>=IDENT && LA82_0<=LPAREN)||LA82_0==INT||(LA82_0>=PLUS && LA82_0<=MINUS)||(LA82_0>=REAL && LA82_0<=HASH)||LA82_0==54||LA82_0==79||LA82_0==93||(LA82_0>=95 && LA82_0<=108)) ) {
                        alt82=1;
                    }
                    switch (alt82) {
                        case 1 :
                            // Cmd.g:1021:7: e= expression ( COMMA e= expression )*
                            {
                            pushFollow(FOLLOW_expression_in_operationExpression4550);
                            e=expression();

                            state._fsp--;
                            if (state.failed) return n;
                            if ( state.backtracking==0 ) {
                               n.addArg(e); 
                            }
                            // Cmd.g:1022:7: ( COMMA e= expression )*
                            loop81:
                            do {
                                int alt81=2;
                                int LA81_0 = input.LA(1);

                                if ( (LA81_0==COMMA) ) {
                                    alt81=1;
                                }


                                switch (alt81) {
                            	case 1 :
                            	    // Cmd.g:1022:9: COMMA e= expression
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_operationExpression4562); if (state.failed) return n;
                            	    pushFollow(FOLLOW_expression_in_operationExpression4566);
                            	    e=expression();

                            	    state._fsp--;
                            	    if (state.failed) return n;
                            	    if ( state.backtracking==0 ) {
                            	       n.addArg(e); 
                            	    }

                            	    }
                            	    break;

                            	default :
                            	    break loop81;
                                }
                            } while (true);


                            }
                            break;

                    }

                    match(input,RPAREN,FOLLOW_RPAREN_in_operationExpression4586); if (state.failed) return n;

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
    // Cmd.g:1034:1: typeExpression[ASTExpression source, boolean followsArrow] returns [ASTTypeArgExpression n] : ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN ;
    public final ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTTypeArgExpression n = null;

        ASTType t = null;


         Token opToken = null; 
        try {
            // Cmd.g:1037:1: ( ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN )
            // Cmd.g:1038:2: ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN
            {
            if ( state.backtracking==0 ) {
               opToken = input.LT(1); 
            }
            if ( (input.LA(1)>=96 && input.LA(1)<=98) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_typeExpression4645); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_typeExpression4649);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_typeExpression4651); if (state.failed) return n;
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
    // Cmd.g:1049:1: elemVarsDeclaration returns [ASTElemVarsDeclaration n] : idListRes= idList ( COLON t= type )? ;
    public final ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException {
        ASTElemVarsDeclaration n = null;

        List idListRes = null;

        ASTType t = null;


         List idList; 
        try {
            // Cmd.g:1051:1: (idListRes= idList ( COLON t= type )? )
            // Cmd.g:1052:5: idListRes= idList ( COLON t= type )?
            {
            pushFollow(FOLLOW_idList_in_elemVarsDeclaration4690);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            // Cmd.g:1053:5: ( COLON t= type )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==COLON) ) {
                alt84=1;
            }
            switch (alt84) {
                case 1 :
                    // Cmd.g:1053:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_elemVarsDeclaration4698); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_elemVarsDeclaration4702);
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
    // Cmd.g:1062:1: variableInitialization returns [ASTVariableInitialization n] : name= IDENT COLON t= type EQUAL e= expression ;
    public final ASTVariableInitialization variableInitialization() throws RecognitionException {
        ASTVariableInitialization n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // Cmd.g:1063:1: (name= IDENT COLON t= type EQUAL e= expression )
            // Cmd.g:1064:5: name= IDENT COLON t= type EQUAL e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableInitialization4737); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableInitialization4739); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableInitialization4743);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EQUAL,FOLLOW_EQUAL_in_variableInitialization4745); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_variableInitialization4749);
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
    // Cmd.g:1073:1: ifExpression returns [ASTExpression n] : i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' ;
    public final ASTExpression ifExpression() throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTExpression cond = null;

        ASTExpression t = null;

        ASTExpression e = null;


        try {
            // Cmd.g:1074:1: (i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' )
            // Cmd.g:1075:5: i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif'
            {
            i=(Token)match(input,79,FOLLOW_79_in_ifExpression4781); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4785);
            cond=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,80,FOLLOW_80_in_ifExpression4787); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4791);
            t=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,81,FOLLOW_81_in_ifExpression4793); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4797);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,82,FOLLOW_82_in_ifExpression4799); if (state.failed) return n;
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
    // Cmd.g:1093:1: literal returns [ASTExpression n] : (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral );
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
            // Cmd.g:1094:1: (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral )
            int alt85=10;
            switch ( input.LA(1) ) {
            case 99:
                {
                alt85=1;
                }
                break;
            case 100:
                {
                alt85=2;
                }
                break;
            case INT:
                {
                alt85=3;
                }
                break;
            case REAL:
                {
                alt85=4;
                }
                break;
            case STRING:
                {
                alt85=5;
                }
                break;
            case HASH:
                {
                alt85=6;
                }
                break;
            case 101:
            case 102:
            case 103:
                {
                alt85=7;
                }
                break;
            case 104:
                {
                alt85=8;
                }
                break;
            case 105:
            case 106:
            case 107:
                {
                alt85=9;
                }
                break;
            case 108:
                {
                alt85=10;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 85, 0, input);

                throw nvae;
            }

            switch (alt85) {
                case 1 :
                    // Cmd.g:1095:7: t= 'true'
                    {
                    t=(Token)match(input,99,FOLLOW_99_in_literal4838); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(true); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:1096:7: f= 'false'
                    {
                    f=(Token)match(input,100,FOLLOW_100_in_literal4852); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(false); 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:1097:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_literal4865); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTIntegerLiteral(i); 
                    }

                    }
                    break;
                case 4 :
                    // Cmd.g:1098:7: r= REAL
                    {
                    r=(Token)match(input,REAL,FOLLOW_REAL_in_literal4880); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTRealLiteral(r); 
                    }

                    }
                    break;
                case 5 :
                    // Cmd.g:1099:7: s= STRING
                    {
                    s=(Token)match(input,STRING,FOLLOW_STRING_in_literal4894); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTStringLiteral(s); 
                    }

                    }
                    break;
                case 6 :
                    // Cmd.g:1100:7: HASH enumLit= IDENT
                    {
                    match(input,HASH,FOLLOW_HASH_in_literal4904); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal4908); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumLit); 
                    }

                    }
                    break;
                case 7 :
                    // Cmd.g:1101:7: nColIt= collectionLiteral
                    {
                    pushFollow(FOLLOW_collectionLiteral_in_literal4920);
                    nColIt=collectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nColIt; 
                    }

                    }
                    break;
                case 8 :
                    // Cmd.g:1102:7: nEColIt= emptyCollectionLiteral
                    {
                    pushFollow(FOLLOW_emptyCollectionLiteral_in_literal4932);
                    nEColIt=emptyCollectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nEColIt; 
                    }

                    }
                    break;
                case 9 :
                    // Cmd.g:1103:7: nUndLit= undefinedLiteral
                    {
                    pushFollow(FOLLOW_undefinedLiteral_in_literal4944);
                    nUndLit=undefinedLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nUndLit; 
                    }

                    }
                    break;
                case 10 :
                    // Cmd.g:1104:7: nTupleLit= tupleLiteral
                    {
                    pushFollow(FOLLOW_tupleLiteral_in_literal4956);
                    nTupleLit=tupleLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nTupleLit; 
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
    // Cmd.g:1112:1: collectionLiteral returns [ASTCollectionLiteral n] : ( 'Set' | 'Sequence' | 'Bag' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE ;
    public final ASTCollectionLiteral collectionLiteral() throws RecognitionException {
        ASTCollectionLiteral n = null;

        ASTCollectionItem ci = null;


         Token op = null; 
        try {
            // Cmd.g:1114:1: ( ( 'Set' | 'Sequence' | 'Bag' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE )
            // Cmd.g:1115:5: ( 'Set' | 'Sequence' | 'Bag' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE
            {
            if ( state.backtracking==0 ) {
               op = input.LT(1); 
            }
            if ( (input.LA(1)>=101 && input.LA(1)<=103) ) {
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
            match(input,LBRACE,FOLLOW_LBRACE_in_collectionLiteral5019); if (state.failed) return n;
            // Cmd.g:1119:5: (ci= collectionItem ( COMMA ci= collectionItem )* )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( ((LA87_0>=IDENT && LA87_0<=LPAREN)||LA87_0==INT||(LA87_0>=PLUS && LA87_0<=MINUS)||(LA87_0>=REAL && LA87_0<=HASH)||LA87_0==54||LA87_0==79||LA87_0==93||(LA87_0>=95 && LA87_0<=108)) ) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    // Cmd.g:1120:7: ci= collectionItem ( COMMA ci= collectionItem )*
                    {
                    pushFollow(FOLLOW_collectionItem_in_collectionLiteral5036);
                    ci=collectionItem();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addItem(ci); 
                    }
                    // Cmd.g:1121:7: ( COMMA ci= collectionItem )*
                    loop86:
                    do {
                        int alt86=2;
                        int LA86_0 = input.LA(1);

                        if ( (LA86_0==COMMA) ) {
                            alt86=1;
                        }


                        switch (alt86) {
                    	case 1 :
                    	    // Cmd.g:1121:9: COMMA ci= collectionItem
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_collectionLiteral5049); if (state.failed) return n;
                    	    pushFollow(FOLLOW_collectionItem_in_collectionLiteral5053);
                    	    ci=collectionItem();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addItem(ci); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop86;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RBRACE,FOLLOW_RBRACE_in_collectionLiteral5072); if (state.failed) return n;

            }

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
    // Cmd.g:1130:1: collectionItem returns [ASTCollectionItem n] : e= expression ( DOTDOT e= expression )? ;
    public final ASTCollectionItem collectionItem() throws RecognitionException {
        ASTCollectionItem n = null;

        ASTExpression e = null;


         n = new ASTCollectionItem(); 
        try {
            // Cmd.g:1132:1: (e= expression ( DOTDOT e= expression )? )
            // Cmd.g:1133:5: e= expression ( DOTDOT e= expression )?
            {
            pushFollow(FOLLOW_expression_in_collectionItem5101);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setFirst(e); 
            }
            // Cmd.g:1134:5: ( DOTDOT e= expression )?
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==DOTDOT) ) {
                alt88=1;
            }
            switch (alt88) {
                case 1 :
                    // Cmd.g:1134:7: DOTDOT e= expression
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_collectionItem5112); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_collectionItem5116);
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
    // Cmd.g:1144:1: emptyCollectionLiteral returns [ASTEmptyCollectionLiteral n] : 'oclEmpty' LPAREN t= collectionType RPAREN ;
    public final ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException {
        ASTEmptyCollectionLiteral n = null;

        ASTCollectionType t = null;


        try {
            // Cmd.g:1145:1: ( 'oclEmpty' LPAREN t= collectionType RPAREN )
            // Cmd.g:1146:5: 'oclEmpty' LPAREN t= collectionType RPAREN
            {
            match(input,104,FOLLOW_104_in_emptyCollectionLiteral5145); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_emptyCollectionLiteral5147); if (state.failed) return n;
            pushFollow(FOLLOW_collectionType_in_emptyCollectionLiteral5151);
            t=collectionType();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_emptyCollectionLiteral5153); if (state.failed) return n;
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
    // Cmd.g:1157:1: undefinedLiteral returns [ASTUndefinedLiteral n] : ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' );
    public final ASTUndefinedLiteral undefinedLiteral() throws RecognitionException {
        ASTUndefinedLiteral n = null;

        ASTType t = null;


        try {
            // Cmd.g:1158:1: ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' )
            int alt89=3;
            switch ( input.LA(1) ) {
            case 105:
                {
                alt89=1;
                }
                break;
            case 106:
                {
                alt89=2;
                }
                break;
            case 107:
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
                    // Cmd.g:1159:5: 'oclUndefined' LPAREN t= type RPAREN
                    {
                    match(input,105,FOLLOW_105_in_undefinedLiteral5183); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_undefinedLiteral5185); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_undefinedLiteral5189);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_undefinedLiteral5191); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(t); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:1162:5: 'Undefined'
                    {
                    match(input,106,FOLLOW_106_in_undefinedLiteral5205); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(); 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:1165:5: 'null'
                    {
                    match(input,107,FOLLOW_107_in_undefinedLiteral5219); if (state.failed) return n;
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
    // Cmd.g:1174:1: tupleLiteral returns [ASTTupleLiteral n] : 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE ;
    public final ASTTupleLiteral tupleLiteral() throws RecognitionException {
        ASTTupleLiteral n = null;

        ASTTupleItem ti = null;


         List tiList = new ArrayList(); 
        try {
            // Cmd.g:1176:1: ( 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE )
            // Cmd.g:1177:5: 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE
            {
            match(input,108,FOLLOW_108_in_tupleLiteral5253); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_tupleLiteral5259); if (state.failed) return n;
            pushFollow(FOLLOW_tupleItem_in_tupleLiteral5267);
            ti=tupleItem();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tiList.add(ti); 
            }
            // Cmd.g:1180:5: ( COMMA ti= tupleItem )*
            loop90:
            do {
                int alt90=2;
                int LA90_0 = input.LA(1);

                if ( (LA90_0==COMMA) ) {
                    alt90=1;
                }


                switch (alt90) {
            	case 1 :
            	    // Cmd.g:1180:7: COMMA ti= tupleItem
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleLiteral5278); if (state.failed) return n;
            	    pushFollow(FOLLOW_tupleItem_in_tupleLiteral5282);
            	    ti=tupleItem();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tiList.add(ti); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop90;
                }
            } while (true);

            match(input,RBRACE,FOLLOW_RBRACE_in_tupleLiteral5293); if (state.failed) return n;
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
    // Cmd.g:1188:1: tupleItem returns [ASTTupleItem n] : name= IDENT ( COLON | EQUAL ) e= expression ;
    public final ASTTupleItem tupleItem() throws RecognitionException {
        ASTTupleItem n = null;

        Token name=null;
        ASTExpression e = null;


        try {
            // Cmd.g:1189:1: (name= IDENT ( COLON | EQUAL ) e= expression )
            // Cmd.g:1190:5: name= IDENT ( COLON | EQUAL ) e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tupleItem5324); if (state.failed) return n;
            if ( input.LA(1)==COLON||input.LA(1)==EQUAL ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            pushFollow(FOLLOW_expression_in_tupleItem5334);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTTupleItem(name, e); 
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


    // $ANTLR start "type"
    // Cmd.g:1201:1: type returns [ASTType n] : (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) ;
    public final ASTType type() throws RecognitionException {
        ASTType n = null;

        ASTSimpleType nTSimple = null;

        ASTCollectionType nTCollection = null;

        ASTTupleType nTTuple = null;


         Token tok = null; 
        try {
            // Cmd.g:1203:1: ( (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) )
            // Cmd.g:1204:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of type */ 
            }
            // Cmd.g:1205:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            int alt91=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt91=1;
                }
                break;
            case 101:
            case 102:
            case 103:
            case 109:
                {
                alt91=2;
                }
                break;
            case 108:
                {
                alt91=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 91, 0, input);

                throw nvae;
            }

            switch (alt91) {
                case 1 :
                    // Cmd.g:1206:7: nTSimple= simpleType
                    {
                    pushFollow(FOLLOW_simpleType_in_type5387);
                    nTSimple=simpleType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTSimple; n.setStartToken(tok); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:1207:7: nTCollection= collectionType
                    {
                    pushFollow(FOLLOW_collectionType_in_type5399);
                    nTCollection=collectionType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTCollection; n.setStartToken(tok); 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:1208:7: nTTuple= tupleType
                    {
                    pushFollow(FOLLOW_tupleType_in_type5411);
                    nTTuple=tupleType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTTuple; n.setStartToken(tok); 
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
    // Cmd.g:1213:1: typeOnly returns [ASTType n] : nT= type EOF ;
    public final ASTType typeOnly() throws RecognitionException {
        ASTType n = null;

        ASTType nT = null;


        try {
            // Cmd.g:1214:1: (nT= type EOF )
            // Cmd.g:1215:5: nT= type EOF
            {
            pushFollow(FOLLOW_type_in_typeOnly5443);
            nT=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_typeOnly5445); if (state.failed) return n;
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
    // Cmd.g:1225:1: simpleType returns [ASTSimpleType n] : name= IDENT ;
    public final ASTSimpleType simpleType() throws RecognitionException {
        ASTSimpleType n = null;

        Token name=null;

        try {
            // Cmd.g:1226:1: (name= IDENT )
            // Cmd.g:1227:5: name= IDENT
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_simpleType5473); if (state.failed) return n;
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
    // Cmd.g:1235:1: collectionType returns [ASTCollectionType n] : ( 'Collection' | 'Set' | 'Sequence' | 'Bag' ) LPAREN elemType= type RPAREN ;
    public final ASTCollectionType collectionType() throws RecognitionException {
        ASTCollectionType n = null;

        ASTType elemType = null;


         Token op = null; 
        try {
            // Cmd.g:1237:1: ( ( 'Collection' | 'Set' | 'Sequence' | 'Bag' ) LPAREN elemType= type RPAREN )
            // Cmd.g:1238:5: ( 'Collection' | 'Set' | 'Sequence' | 'Bag' ) LPAREN elemType= type RPAREN
            {
            if ( state.backtracking==0 ) {
               op = input.LT(1); 
            }
            if ( (input.LA(1)>=101 && input.LA(1)<=103)||input.LA(1)==109 ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_collectionType5534); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_collectionType5538);
            elemType=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_collectionType5540); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTCollectionType(op, elemType); n.setStartToken(op);
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
    // Cmd.g:1248:1: tupleType returns [ASTTupleType n] : 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN ;
    public final ASTTupleType tupleType() throws RecognitionException {
        ASTTupleType n = null;

        ASTTuplePart tp = null;


         List tpList = new ArrayList(); 
        try {
            // Cmd.g:1250:1: ( 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN )
            // Cmd.g:1251:5: 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN
            {
            match(input,108,FOLLOW_108_in_tupleType5574); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_tupleType5576); if (state.failed) return n;
            pushFollow(FOLLOW_tuplePart_in_tupleType5585);
            tp=tuplePart();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tpList.add(tp); 
            }
            // Cmd.g:1253:5: ( COMMA tp= tuplePart )*
            loop92:
            do {
                int alt92=2;
                int LA92_0 = input.LA(1);

                if ( (LA92_0==COMMA) ) {
                    alt92=1;
                }


                switch (alt92) {
            	case 1 :
            	    // Cmd.g:1253:7: COMMA tp= tuplePart
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleType5596); if (state.failed) return n;
            	    pushFollow(FOLLOW_tuplePart_in_tupleType5600);
            	    tp=tuplePart();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tpList.add(tp); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop92;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_tupleType5612); if (state.failed) return n;
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
    // Cmd.g:1262:1: tuplePart returns [ASTTuplePart n] : name= IDENT COLON t= type ;
    public final ASTTuplePart tuplePart() throws RecognitionException {
        ASTTuplePart n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Cmd.g:1263:1: (name= IDENT COLON t= type )
            // Cmd.g:1264:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tuplePart5644); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_tuplePart5646); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_tuplePart5650);
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
        // Cmd.g:248:15: ( expression )
        // Cmd.g:248:16: expression
        {
        pushFollow(FOLLOW_expression_in_synpred1_Cmd797);
        expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_Cmd

    // Delegated rules

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
        "\20\uffff";
    static final String DFA3_eofS =
        "\16\uffff\1\15\1\uffff";
    static final String DFA3_minS =
        "\1\53\1\7\11\uffff\1\5\1\7\1\uffff\1\4\1\uffff";
    static final String DFA3_maxS =
        "\1\67\1\7\11\uffff\1\12\1\7\1\uffff\1\67\1\uffff";
    static final String DFA3_acceptS =
        "\2\uffff\1\2\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\2\uffff\1\1\1\uffff"+
        "\1\3";
    static final String DFA3_specialS =
        "\20\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\1\1\2\1\uffff\1\3\1\4\1\uffff\1\5\1\uffff\1\6\1\7\1\10\1"+
            "\11\1\12",
            "\1\13",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\14\4\uffff\1\15",
            "\1\16",
            "",
            "\1\15\46\uffff\2\15\1\17\2\15\1\uffff\1\15\1\uffff\5\15",
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
            return "111:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= executeCmd )";
        }
    }
    static final String DFA9_eotS =
        "\37\uffff";
    static final String DFA9_eofS =
        "\1\24\36\uffff";
    static final String DFA9_minS =
        "\1\4\1\0\35\uffff";
    static final String DFA9_maxS =
        "\1\154\1\0\35\uffff";
    static final String DFA9_acceptS =
        "\2\uffff\22\1\1\2\12\uffff";
    static final String DFA9_specialS =
        "\1\0\1\1\35\uffff}>";
    static final String[] DFA9_transitionS = {
            "\1\24\2\uffff\1\17\1\22\11\uffff\1\5\6\uffff\2\2\5\uffff\1\6"+
            "\1\7\1\10\10\uffff\2\24\1\uffff\2\24\1\uffff\1\24\1\uffff\3"+
            "\24\1\1\1\24\27\uffff\1\23\15\uffff\1\2\1\uffff\1\20\3\21\1"+
            "\3\1\4\3\11\1\12\1\13\1\14\1\15\1\16",
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
            return "248:14: ( ( expression )=>e= expression | )";
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
                        if ( (LA9_0==54) ) {s = 1;}

                        else if ( ((LA9_0>=PLUS && LA9_0<=MINUS)||LA9_0==93) && (synpred1_Cmd())) {s = 2;}

                        else if ( (LA9_0==99) && (synpred1_Cmd())) {s = 3;}

                        else if ( (LA9_0==100) && (synpred1_Cmd())) {s = 4;}

                        else if ( (LA9_0==INT) && (synpred1_Cmd())) {s = 5;}

                        else if ( (LA9_0==REAL) && (synpred1_Cmd())) {s = 6;}

                        else if ( (LA9_0==STRING) && (synpred1_Cmd())) {s = 7;}

                        else if ( (LA9_0==HASH) && (synpred1_Cmd())) {s = 8;}

                        else if ( ((LA9_0>=101 && LA9_0<=103)) && (synpred1_Cmd())) {s = 9;}

                        else if ( (LA9_0==104) && (synpred1_Cmd())) {s = 10;}

                        else if ( (LA9_0==105) && (synpred1_Cmd())) {s = 11;}

                        else if ( (LA9_0==106) && (synpred1_Cmd())) {s = 12;}

                        else if ( (LA9_0==107) && (synpred1_Cmd())) {s = 13;}

                        else if ( (LA9_0==108) && (synpred1_Cmd())) {s = 14;}

                        else if ( (LA9_0==IDENT) && (synpred1_Cmd())) {s = 15;}

                        else if ( (LA9_0==95) && (synpred1_Cmd())) {s = 16;}

                        else if ( ((LA9_0>=96 && LA9_0<=98)) && (synpred1_Cmd())) {s = 17;}

                        else if ( (LA9_0==LPAREN) && (synpred1_Cmd())) {s = 18;}

                        else if ( (LA9_0==79) && (synpred1_Cmd())) {s = 19;}

                        else if ( (LA9_0==EOF||LA9_0==SEMI||(LA9_0>=43 && LA9_0<=44)||(LA9_0>=46 && LA9_0<=47)||LA9_0==49||(LA9_0>=51 && LA9_0<=53)||LA9_0==55) ) {s = 20;}

                         
                        input.seek(index9_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA9_1 = input.LA(1);

                         
                        int index9_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_Cmd()) ) {s = 19;}

                        else if ( (true) ) {s = 20;}

                         
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
 

    public static final BitSet FOLLOW_cmd_in_cmdList65 = new BitSet(new long[]{0x00FAD80000000000L});
    public static final BitSet FOLLOW_cmd_in_cmdList77 = new BitSet(new long[]{0x00FAD80000000000L});
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
    public static final BitSet FOLLOW_executeCmd_in_cmdStmt271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_createCmd300 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_createCmd304 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_createCmd311 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_simpleType_in_createCmd315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_createAssignCmd344 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_createAssignCmd348 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_createAssignCmd350 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_43_in_createAssignCmd352 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_simpleType_in_createAssignCmd356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_createInsertCmd376 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_createInsertCmd380 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_createInsertCmd382 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_createInsertCmd386 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_45_in_createInsertCmd392 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_createInsertCmd394 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_createInsertCmd398 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_createInsertCmd400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_destroyCmd436 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_destroyCmd440 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COMMA_in_destroyCmd462 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_destroyCmd466 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_47_in_insertCmd505 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_insertCmd507 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_insertCmd516 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_COMMA_in_insertCmd520 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_insertCmd528 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_insertCmd534 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_insertCmd538 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_insertCmd550 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_insertCmd552 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_insertCmd556 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_deleteCmd591 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_deleteCmd593 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_deleteCmd601 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_COMMA_in_deleteCmd605 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_deleteCmd613 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_deleteCmd619 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_deleteCmd623 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_deleteCmd634 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_deleteCmd636 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_deleteCmd640 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_51_in_setCmd670 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_setCmd674 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_setCmd676 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_setCmd680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_opEnterCmd714 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_opEnterCmd723 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_opEnterCmd727 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_opEnterCmd735 = new BitSet(new long[]{0x0040000706040380L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_opEnterCmd745 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_opEnterCmd751 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_opEnterCmd755 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_opEnterCmd769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_opExitCmd793 = new BitSet(new long[]{0x0040000706040182L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_opExitCmd803 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_letCmd836 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_letCmd840 = new BitSet(new long[]{0x0000000000000820L});
    public static final BitSet FOLLOW_COLON_in_letCmd844 = new BitSet(new long[]{0x0000000000000080L,0x000030E000000000L});
    public static final BitSet FOLLOW_type_in_letCmd848 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_letCmd853 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_letCmd857 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_executeCmd882 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_executeCmd891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_model923 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_model927 = new BitSet(new long[]{0x1E00000000000000L,0x000000000000002FL});
    public static final BitSet FOLLOW_enumTypeDefinition_in_model939 = new BitSet(new long[]{0x1E00000000000000L,0x000000000000002FL});
    public static final BitSet FOLLOW_generalClassDefinition_in_model956 = new BitSet(new long[]{0x1A00000000000000L,0x000000000000002FL});
    public static final BitSet FOLLOW_associationDefinition_in_model973 = new BitSet(new long[]{0x1A00000000000000L,0x000000000000002FL});
    public static final BitSet FOLLOW_57_in_model989 = new BitSet(new long[]{0x1A00000000000000L,0x000000000000012FL});
    public static final BitSet FOLLOW_invariant_in_model1007 = new BitSet(new long[]{0x1A00000000000000L,0x000000000000012FL});
    public static final BitSet FOLLOW_prePost_in_model1028 = new BitSet(new long[]{0x1A00000000000000L,0x000000000000012FL});
    public static final BitSet FOLLOW_EOF_in_model1069 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_enumTypeDefinition1096 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_enumTypeDefinition1100 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LBRACE_in_enumTypeDefinition1102 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_enumTypeDefinition1106 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RBRACE_in_enumTypeDefinition1108 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_SEMI_in_enumTypeDefinition1112 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_generalClassDefinition1151 = new BitSet(new long[]{0x1800000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_classDefinition_in_generalClassDefinition1169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_associationClassDefinition_in_generalClassDefinition1189 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_classDefinition1228 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_classDefinition1232 = new BitSet(new long[]{0xE200000000004000L});
    public static final BitSet FOLLOW_LESS_in_classDefinition1242 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_classDefinition1246 = new BitSet(new long[]{0xE200000000000000L});
    public static final BitSet FOLLOW_61_in_classDefinition1259 = new BitSet(new long[]{0xC200000000000080L});
    public static final BitSet FOLLOW_attributeDefinition_in_classDefinition1272 = new BitSet(new long[]{0xC200000000000080L});
    public static final BitSet FOLLOW_62_in_classDefinition1293 = new BitSet(new long[]{0x8200000000000080L});
    public static final BitSet FOLLOW_operationDefinition_in_classDefinition1306 = new BitSet(new long[]{0x8200000000000080L});
    public static final BitSet FOLLOW_57_in_classDefinition1327 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_invariantClause_in_classDefinition1347 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_63_in_classDefinition1371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_associationClassDefinition1404 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_associationClassDefinition1430 = new BitSet(new long[]{0x0000200000004000L});
    public static final BitSet FOLLOW_LESS_in_associationClassDefinition1440 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_associationClassDefinition1444 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_45_in_associationClassDefinition1455 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_associationEnd_in_associationClassDefinition1463 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_associationEnd_in_associationClassDefinition1475 = new BitSet(new long[]{0xE200000000000080L,0x000000000000000CL});
    public static final BitSet FOLLOW_61_in_associationClassDefinition1488 = new BitSet(new long[]{0xC200000000000080L,0x000000000000000CL});
    public static final BitSet FOLLOW_attributeDefinition_in_associationClassDefinition1501 = new BitSet(new long[]{0xC200000000000080L,0x000000000000000CL});
    public static final BitSet FOLLOW_62_in_associationClassDefinition1522 = new BitSet(new long[]{0x8200000000000080L,0x000000000000000CL});
    public static final BitSet FOLLOW_operationDefinition_in_associationClassDefinition1535 = new BitSet(new long[]{0x8200000000000080L,0x000000000000000CL});
    public static final BitSet FOLLOW_57_in_associationClassDefinition1556 = new BitSet(new long[]{0x8000000000000000L,0x000000000000020CL});
    public static final BitSet FOLLOW_invariantClause_in_associationClassDefinition1576 = new BitSet(new long[]{0x8000000000000000L,0x000000000000020CL});
    public static final BitSet FOLLOW_set_in_associationClassDefinition1610 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_63_in_associationClassDefinition1639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_attributeDefinition1668 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_attributeDefinition1670 = new BitSet(new long[]{0x0000000000000080L,0x000030E000000000L});
    public static final BitSet FOLLOW_type_in_attributeDefinition1674 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_SEMI_in_attributeDefinition1678 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationDefinition1716 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_paramList_in_operationDefinition1724 = new BitSet(new long[]{0x0000000000000832L,0x0000000000000C10L});
    public static final BitSet FOLLOW_COLON_in_operationDefinition1732 = new BitSet(new long[]{0x0000000000000080L,0x000030E000000000L});
    public static final BitSet FOLLOW_type_in_operationDefinition1736 = new BitSet(new long[]{0x0000000000000812L,0x0000000000000C10L});
    public static final BitSet FOLLOW_EQUAL_in_operationDefinition1746 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_operationDefinition1750 = new BitSet(new long[]{0x0000000000000012L,0x0000000000000C10L});
    public static final BitSet FOLLOW_68_in_operationDefinition1758 = new BitSet(new long[]{0x808AC80000000000L,0x000000000048B000L});
    public static final BitSet FOLLOW_alActionList_in_operationDefinition1762 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_63_in_operationDefinition1764 = new BitSet(new long[]{0x0000000000000012L,0x0000000000000C00L});
    public static final BitSet FOLLOW_prePostClause_in_operationDefinition1783 = new BitSet(new long[]{0x0000000000000012L,0x0000000000000C00L});
    public static final BitSet FOLLOW_SEMI_in_operationDefinition1796 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_associationDefinition1832 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_associationDefinition1857 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_45_in_associationDefinition1865 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_associationEnd_in_associationDefinition1873 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_associationEnd_in_associationDefinition1885 = new BitSet(new long[]{0x8000000000000080L});
    public static final BitSet FOLLOW_63_in_associationDefinition1896 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd1922 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_LBRACK_in_associationEnd1924 = new BitSet(new long[]{0x00000000000C0000L});
    public static final BitSet FOLLOW_multiplicity_in_associationEnd1928 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_RBRACK_in_associationEnd1930 = new BitSet(new long[]{0x0000000000000012L,0x00000000000000C0L});
    public static final BitSet FOLLOW_70_in_associationEnd1946 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd1950 = new BitSet(new long[]{0x0000000000000012L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_associationEnd1963 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_SEMI_in_associationEnd1976 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity2011 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COMMA_in_multiplicity2021 = new BitSet(new long[]{0x00000000000C0000L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity2025 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange2054 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_DOTDOT_in_multiplicityRange2064 = new BitSet(new long[]{0x00000000000C0000L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange2068 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_multiplicitySpec2102 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_multiplicitySpec2112 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_invariant2153 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_invariant2163 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_invariant2165 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_simpleType_in_invariant2180 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_invariantClause_in_invariant2192 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_invariantClause2222 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_IDENT_in_invariantClause2228 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_invariantClause2233 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_invariantClause2237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_prePost2267 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_prePost2271 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_COLON_COLON_in_prePost2273 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_prePost2277 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_paramList_in_prePost2281 = new BitSet(new long[]{0x0000000000000020L,0x0000000000000C00L});
    public static final BitSet FOLLOW_COLON_in_prePost2285 = new BitSet(new long[]{0x0000000000000080L,0x000030E000000000L});
    public static final BitSet FOLLOW_type_in_prePost2289 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000C00L});
    public static final BitSet FOLLOW_prePostClause_in_prePost2308 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000C00L});
    public static final BitSet FOLLOW_set_in_prePostClause2347 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_IDENT_in_prePostClause2362 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_prePostClause2367 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_prePostClause2371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alAction_in_alActionList2404 = new BitSet(new long[]{0x008AC80000000002L,0x000000000048B000L});
    public static final BitSet FOLLOW_alCreateVar_in_alAction2430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alDelete_in_alAction2442 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alSet_in_alAction2454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alSetCreate_in_alAction2466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alInsert_in_alAction2478 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alDestroy_in_alAction2490 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alIf_in_alAction2502 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alWhile_in_alAction2514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alFor_in_alAction2526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alExec_in_alAction2538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_alCreateVar2557 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_alCreateVar2565 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_alCreateVar2567 = new BitSet(new long[]{0x0000000000000080L,0x000030E000000000L});
    public static final BitSet FOLLOW_type_in_alCreateVar2571 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_51_in_alSet2593 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_alSet2597 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_alSet2599 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_alSet2603 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_alSetCreate2627 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_alSetCreate2631 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_alSetCreate2633 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_alSetCreate2639 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_alSetCreate2643 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_alSetCreate2652 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_alSetCreate2656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_alInsert2687 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_alInsert2689 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_alInsert2698 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_COMMA_in_alInsert2702 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_alInsert2710 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_alInsert2716 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_alInsert2720 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_alInsert2732 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_alInsert2734 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_alInsert2738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_alDelete2770 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_alDelete2772 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_alDelete2780 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_COMMA_in_alDelete2784 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_alDelete2792 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_alDelete2798 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_alDelete2802 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_alDelete2813 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_alDelete2815 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_alDelete2819 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_alDestroy2848 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_alDestroy2852 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_alIf2876 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_alIf2880 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_alIf2884 = new BitSet(new long[]{0x008AC80000000000L,0x00000000004EB000L});
    public static final BitSet FOLLOW_alActionList_in_alIf2888 = new BitSet(new long[]{0x0000000000000000L,0x0000000000060000L});
    public static final BitSet FOLLOW_81_in_alIf2892 = new BitSet(new long[]{0x008AC80000000000L,0x00000000004CB000L});
    public static final BitSet FOLLOW_alActionList_in_alIf2896 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_alIf2901 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_alWhile2920 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_alWhile2924 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_alWhile2928 = new BitSet(new long[]{0x008AC80000000000L,0x000000000068B000L});
    public static final BitSet FOLLOW_alActionList_in_alWhile2934 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_alWhile2937 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_86_in_alFor2956 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_alFor2960 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_alFor2962 = new BitSet(new long[]{0x0000000000000080L,0x000030E000000000L});
    public static final BitSet FOLLOW_type_in_alFor2966 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_alFor2968 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_alFor2972 = new BitSet(new long[]{0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_alFor2976 = new BitSet(new long[]{0x008AC80000000080L,0x000000000048B000L});
    public static final BitSet FOLLOW_alActionList_in_alFor2982 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_alFor2989 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_alExec3009 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_alExec3013 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_expressionOnly3048 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_expressionOnly3050 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_expression3098 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_expression3102 = new BitSet(new long[]{0x0000000000000820L});
    public static final BitSet FOLLOW_COLON_in_expression3106 = new BitSet(new long[]{0x0000000000000080L,0x000030E000000000L});
    public static final BitSet FOLLOW_type_in_expression3110 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_expression3115 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_expression3119 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_expression3121 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_conditionalImpliesExpression_in_expression3146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_paramList3179 = new BitSet(new long[]{0x0000000000000280L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList3196 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_paramList3208 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList3212 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_paramList3232 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_idList3261 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COMMA_in_idList3271 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_idList3275 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_IDENT_in_variableDeclaration3306 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_variableDeclaration3308 = new BitSet(new long[]{0x0000000000000080L,0x000030E000000000L});
    public static final BitSet FOLLOW_type_in_variableDeclaration3312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3348 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_88_in_conditionalImpliesExpression3361 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3365 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3410 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_conditionalOrExpression3423 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3427 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3471 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_conditionalXOrExpression3484 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3488 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression3532 = new BitSet(new long[]{0x0000000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_conditionalAndExpression3545 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression3549 = new BitSet(new long[]{0x0000000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression3597 = new BitSet(new long[]{0x0000000000200802L});
    public static final BitSet FOLLOW_set_in_equalityExpression3616 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression3626 = new BitSet(new long[]{0x0000000000200802L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression3675 = new BitSet(new long[]{0x0000000001C04002L});
    public static final BitSet FOLLOW_set_in_relationalExpression3693 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression3711 = new BitSet(new long[]{0x0000000001C04002L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3761 = new BitSet(new long[]{0x0000000006000002L});
    public static final BitSet FOLLOW_set_in_additiveExpression3779 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3789 = new BitSet(new long[]{0x0000000006000002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3839 = new BitSet(new long[]{0x0000000008080002L,0x0000000010000000L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression3857 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3871 = new BitSet(new long[]{0x0000000008080002L,0x0000000010000000L});
    public static final BitSet FOLLOW_set_in_unaryExpression3933 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression3957 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpression_in_unaryExpression3977 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_postfixExpression4010 = new BitSet(new long[]{0x0000000030000002L});
    public static final BitSet FOLLOW_ARROW_in_postfixExpression4028 = new BitSet(new long[]{0x0000000000000080L,0x0000000780000000L});
    public static final BitSet FOLLOW_DOT_in_postfixExpression4034 = new BitSet(new long[]{0x0000000000000080L,0x0000000780000000L});
    public static final BitSet FOLLOW_propertyCall_in_postfixExpression4045 = new BitSet(new long[]{0x0000000030000002L});
    public static final BitSet FOLLOW_literal_in_primaryExpression4085 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyCall_in_primaryExpression4097 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression4108 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_primaryExpression4112 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression4114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifExpression_in_primaryExpression4126 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_primaryExpression4143 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_DOT_in_primaryExpression4145 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_94_in_primaryExpression4147 = new BitSet(new long[]{0x0000000040000102L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression4151 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression4153 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_AT_in_primaryExpression4174 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_primaryExpression4176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_queryExpression_in_propertyCall4242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterateExpression_in_propertyCall4255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationExpression_in_propertyCall4268 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeExpression_in_propertyCall4281 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_queryExpression4316 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_queryExpression4323 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_queryExpression4334 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_BAR_in_queryExpression4338 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_queryExpression4349 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_queryExpression4355 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_95_in_iterateExpression4387 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_iterateExpression4393 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_iterateExpression4401 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_iterateExpression4403 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_variableInitialization_in_iterateExpression4411 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_BAR_in_iterateExpression4413 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_iterateExpression4421 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_iterateExpression4427 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression4471 = new BitSet(new long[]{0x0000000040008102L});
    public static final BitSet FOLLOW_LBRACK_in_operationExpression4487 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression4491 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_RBRACK_in_operationExpression4493 = new BitSet(new long[]{0x0000000040000102L});
    public static final BitSet FOLLOW_AT_in_operationExpression4506 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_operationExpression4508 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_LPAREN_in_operationExpression4529 = new BitSet(new long[]{0x0040000706040380L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_operationExpression4550 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_operationExpression4562 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_operationExpression4566 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_operationExpression4586 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_typeExpression4629 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_typeExpression4645 = new BitSet(new long[]{0x0000000000000080L,0x000030E000000000L});
    public static final BitSet FOLLOW_type_in_typeExpression4649 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_typeExpression4651 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_elemVarsDeclaration4690 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_COLON_in_elemVarsDeclaration4698 = new BitSet(new long[]{0x0000000000000080L,0x000030E000000000L});
    public static final BitSet FOLLOW_type_in_elemVarsDeclaration4702 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableInitialization4737 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_variableInitialization4739 = new BitSet(new long[]{0x0000000000000080L,0x000030E000000000L});
    public static final BitSet FOLLOW_type_in_variableInitialization4743 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_variableInitialization4745 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_variableInitialization4749 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_ifExpression4781 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_ifExpression4785 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_ifExpression4787 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_ifExpression4791 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_ifExpression4793 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_ifExpression4797 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_ifExpression4799 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_literal4838 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_100_in_literal4852 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_literal4865 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REAL_in_literal4880 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal4894 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_literal4904 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_literal4908 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionLiteral_in_literal4920 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyCollectionLiteral_in_literal4932 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedLiteral_in_literal4944 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleLiteral_in_literal4956 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionLiteral4994 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LBRACE_in_collectionLiteral5019 = new BitSet(new long[]{0x0040000706042180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral5036 = new BitSet(new long[]{0x0000000000002400L});
    public static final BitSet FOLLOW_COMMA_in_collectionLiteral5049 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral5053 = new BitSet(new long[]{0x0000000000002400L});
    public static final BitSet FOLLOW_RBRACE_in_collectionLiteral5072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_collectionItem5101 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_DOTDOT_in_collectionItem5112 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_collectionItem5116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_104_in_emptyCollectionLiteral5145 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_emptyCollectionLiteral5147 = new BitSet(new long[]{0x0000000000000000L,0x000020E000000000L});
    public static final BitSet FOLLOW_collectionType_in_emptyCollectionLiteral5151 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_emptyCollectionLiteral5153 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_105_in_undefinedLiteral5183 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_undefinedLiteral5185 = new BitSet(new long[]{0x0000000000000080L,0x000030E000000000L});
    public static final BitSet FOLLOW_type_in_undefinedLiteral5189 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_undefinedLiteral5191 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_106_in_undefinedLiteral5205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_107_in_undefinedLiteral5219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_108_in_tupleLiteral5253 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LBRACE_in_tupleLiteral5259 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral5267 = new BitSet(new long[]{0x0000000000002400L});
    public static final BitSet FOLLOW_COMMA_in_tupleLiteral5278 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral5282 = new BitSet(new long[]{0x0000000000002400L});
    public static final BitSet FOLLOW_RBRACE_in_tupleLiteral5293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tupleItem5324 = new BitSet(new long[]{0x0000000000000820L});
    public static final BitSet FOLLOW_set_in_tupleItem5326 = new BitSet(new long[]{0x0040000706040180L,0x00001FFFA0008000L});
    public static final BitSet FOLLOW_expression_in_tupleItem5334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleType_in_type5387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_type5399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleType_in_type5411 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeOnly5443 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_typeOnly5445 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_simpleType5473 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionType5511 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_collectionType5534 = new BitSet(new long[]{0x0000000000000080L,0x000030E000000000L});
    public static final BitSet FOLLOW_type_in_collectionType5538 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_collectionType5540 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_108_in_tupleType5574 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_tupleType5576 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType5585 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_tupleType5596 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType5600 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_tupleType5612 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tuplePart5644 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_tuplePart5646 = new BitSet(new long[]{0x0000000000000080L,0x000030E000000000L});
    public static final BitSet FOLLOW_type_in_tuplePart5650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_synpred1_Cmd797 = new BitSet(new long[]{0x0000000000000002L});

}