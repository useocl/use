// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 Cmd.g 2010-04-16 10:49:30
 
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
    // Cmd.g:78:1: cmdList returns [ASTCmdList cmdList] : c= cmd (c= cmd )* EOF ;
    public final ASTCmdList cmdList() throws RecognitionException {
        ASTCmdList cmdList = null;

        ASTCmd c = null;


         cmdList = new ASTCmdList(); 
        try {
            // Cmd.g:80:1: (c= cmd (c= cmd )* EOF )
            // Cmd.g:81:5: c= cmd (c= cmd )* EOF
            {
            pushFollow(FOLLOW_cmd_in_cmdList63);
            c=cmd();

            state._fsp--;
            if (state.failed) return cmdList;
            if ( state.backtracking==0 ) {
               cmdList.add(c); 
            }
            // Cmd.g:82:5: (c= cmd )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>=44 && LA1_0<=45)||(LA1_0>=47 && LA1_0<=48)||LA1_0==50||(LA1_0>=52 && LA1_0<=56)||(LA1_0>=59 && LA1_0<=60)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Cmd.g:82:7: c= cmd
            	    {
            	    pushFollow(FOLLOW_cmd_in_cmdList75);
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

            match(input,EOF,FOLLOW_EOF_in_cmdList86); if (state.failed) return cmdList;

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


    // $ANTLR start "embeddedCmdList"
    // Cmd.g:86:1: embeddedCmdList returns [ASTCmdList cmdList] : (c= cmd )+ ;
    public final ASTCmdList embeddedCmdList() throws RecognitionException {
        ASTCmdList cmdList = null;

        ASTCmd c = null;


         cmdList = new ASTCmdList(); 
        try {
            // Cmd.g:88:1: ( (c= cmd )+ )
            // Cmd.g:89:5: (c= cmd )+
            {
            // Cmd.g:89:5: (c= cmd )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>=44 && LA2_0<=45)||(LA2_0>=47 && LA2_0<=48)||LA2_0==50||(LA2_0>=52 && LA2_0<=56)||(LA2_0>=59 && LA2_0<=60)) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // Cmd.g:89:7: c= cmd
            	    {
            	    pushFollow(FOLLOW_cmd_in_embeddedCmdList115);
            	    c=cmd();

            	    state._fsp--;
            	    if (state.failed) return cmdList;
            	    if ( state.backtracking==0 ) {
            	       cmdList.add(c); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
            	    if (state.backtracking>0) {state.failed=true; return cmdList;}
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


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
    // $ANTLR end "embeddedCmdList"


    // $ANTLR start "cmd"
    // Cmd.g:95:1: cmd returns [ASTCmd n] : stmt= cmdStmt ( SEMI )? ;
    public final ASTCmd cmd() throws RecognitionException {
        ASTCmd n = null;

        ASTCmd stmt = null;


        try {
            // Cmd.g:96:1: (stmt= cmdStmt ( SEMI )? )
            // Cmd.g:97:5: stmt= cmdStmt ( SEMI )?
            {
            pushFollow(FOLLOW_cmdStmt_in_cmd148);
            stmt=cmdStmt();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = stmt; 
            }
            // Cmd.g:97:35: ( SEMI )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==SEMI) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // Cmd.g:97:37: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_cmd153); if (state.failed) return n;

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
    // Cmd.g:113:1: cmdStmt returns [ASTCmd n] : (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= showCmd | nC= hideCmd | nC= cropCmd ) ;
    public final ASTCmd cmdStmt() throws RecognitionException {
        ASTCmd n = null;

        ASTCmd nC = null;


        try {
            // Cmd.g:114:1: ( (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= showCmd | nC= hideCmd | nC= cropCmd ) )
            // Cmd.g:115:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= showCmd | nC= hideCmd | nC= cropCmd )
            {
            // Cmd.g:115:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= showCmd | nC= hideCmd | nC= cropCmd )
            int alt4=13;
            alt4 = dfa4.predict(input);
            switch (alt4) {
                case 1 :
                    // Cmd.g:116:7: nC= createCmd
                    {
                    pushFollow(FOLLOW_createCmd_in_cmdStmt184);
                    nC=createCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 2 :
                    // Cmd.g:117:7: nC= createAssignCmd
                    {
                    pushFollow(FOLLOW_createAssignCmd_in_cmdStmt196);
                    nC=createAssignCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 3 :
                    // Cmd.g:118:7: nC= createInsertCmd
                    {
                    pushFollow(FOLLOW_createInsertCmd_in_cmdStmt209);
                    nC=createInsertCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 4 :
                    // Cmd.g:119:7: nC= destroyCmd
                    {
                    pushFollow(FOLLOW_destroyCmd_in_cmdStmt221);
                    nC=destroyCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 5 :
                    // Cmd.g:120:7: nC= insertCmd
                    {
                    pushFollow(FOLLOW_insertCmd_in_cmdStmt233);
                    nC=insertCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 6 :
                    // Cmd.g:121:7: nC= deleteCmd
                    {
                    pushFollow(FOLLOW_deleteCmd_in_cmdStmt245);
                    nC=deleteCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 7 :
                    // Cmd.g:122:7: nC= setCmd
                    {
                    pushFollow(FOLLOW_setCmd_in_cmdStmt257);
                    nC=setCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 8 :
                    // Cmd.g:123:7: nC= opEnterCmd
                    {
                    pushFollow(FOLLOW_opEnterCmd_in_cmdStmt269);
                    nC=opEnterCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 9 :
                    // Cmd.g:124:7: nC= opExitCmd
                    {
                    pushFollow(FOLLOW_opExitCmd_in_cmdStmt281);
                    nC=opExitCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 10 :
                    // Cmd.g:125:7: nC= letCmd
                    {
                    pushFollow(FOLLOW_letCmd_in_cmdStmt293);
                    nC=letCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 11 :
                    // Cmd.g:126:7: nC= showCmd
                    {
                    pushFollow(FOLLOW_showCmd_in_cmdStmt305);
                    nC=showCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 12 :
                    // Cmd.g:127:7: nC= hideCmd
                    {
                    pushFollow(FOLLOW_hideCmd_in_cmdStmt317);
                    nC=hideCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 13 :
                    // Cmd.g:128:7: nC= cropCmd
                    {
                    pushFollow(FOLLOW_cropCmd_in_cmdStmt329);
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
    // Cmd.g:138:1: createCmd returns [ASTCmd n] : 'create' nIdList= idList COLON t= simpleType ;
    public final ASTCmd createCmd() throws RecognitionException {
        ASTCmd n = null;

        List nIdList = null;

        ASTSimpleType t = null;


        try {
            // Cmd.g:139:1: ( 'create' nIdList= idList COLON t= simpleType )
            // Cmd.g:140:5: 'create' nIdList= idList COLON t= simpleType
            {
            match(input,44,FOLLOW_44_in_createCmd358); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createCmd362);
            nIdList=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_createCmd369); if (state.failed) return n;
            pushFollow(FOLLOW_simpleType_in_createCmd373);
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
    // Cmd.g:150:1: createAssignCmd returns [ASTCmd n] : 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType ;
    public final ASTCmd createAssignCmd() throws RecognitionException {
        ASTCmd n = null;

        List nIdList = null;

        ASTSimpleType t = null;


        try {
            // Cmd.g:151:1: ( 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType )
            // Cmd.g:152:5: 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType
            {
            match(input,45,FOLLOW_45_in_createAssignCmd402); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createAssignCmd406);
            nIdList=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_createAssignCmd408); if (state.failed) return n;
            match(input,44,FOLLOW_44_in_createAssignCmd410); if (state.failed) return n;
            pushFollow(FOLLOW_simpleType_in_createAssignCmd414);
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
    // Cmd.g:160:1: createInsertCmd returns [ASTCmd n] : 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN ;
    public final ASTCmd createInsertCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        Token idAssoc=null;
        List idListInsert = null;


        try {
            // Cmd.g:161:1: ( 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN )
            // Cmd.g:162:5: 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN
            {
            match(input,44,FOLLOW_44_in_createInsertCmd434); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_createInsertCmd438); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_createInsertCmd440); if (state.failed) return n;
            idAssoc=(Token)match(input,IDENT,FOLLOW_IDENT_in_createInsertCmd444); if (state.failed) return n;
            match(input,46,FOLLOW_46_in_createInsertCmd450); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_createInsertCmd452); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createInsertCmd456);
            idListInsert=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_createInsertCmd458); if (state.failed) return n;
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
    // Cmd.g:173:1: destroyCmd returns [ASTCmd n] : 'destroy' e= expression ( COMMA e= expression )* ;
    public final ASTCmd destroyCmd() throws RecognitionException {
        ASTCmd n = null;

        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // Cmd.g:175:1: ( 'destroy' e= expression ( COMMA e= expression )* )
            // Cmd.g:176:6: 'destroy' e= expression ( COMMA e= expression )*
            {
            match(input,47,FOLLOW_47_in_destroyCmd494); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_destroyCmd498);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // Cmd.g:177:16: ( COMMA e= expression )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==COMMA) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // Cmd.g:177:18: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_destroyCmd520); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_destroyCmd524);
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
    // Cmd.g:187:1: insertCmd returns [ASTCmd n] : 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT ;
    public final ASTCmd insertCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // Cmd.g:189:1: ( 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT )
            // Cmd.g:190:5: 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT
            {
            match(input,48,FOLLOW_48_in_insertCmd563); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_insertCmd565); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_insertCmd574);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_insertCmd578); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_insertCmd586);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // Cmd.g:192:42: ( COMMA e= expression )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==COMMA) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // Cmd.g:192:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_insertCmd592); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_insertCmd596);
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

            match(input,RPAREN,FOLLOW_RPAREN_in_insertCmd608); if (state.failed) return n;
            match(input,49,FOLLOW_49_in_insertCmd610); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_insertCmd614); if (state.failed) return n;
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
    // Cmd.g:203:1: deleteCmd returns [ASTCmd n] : 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT ;
    public final ASTCmd deleteCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // Cmd.g:205:1: ( 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT )
            // Cmd.g:206:5: 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT
            {
            match(input,50,FOLLOW_50_in_deleteCmd649); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_deleteCmd651); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_deleteCmd659);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_deleteCmd663); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_deleteCmd671);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // Cmd.g:208:42: ( COMMA e= expression )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==COMMA) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // Cmd.g:208:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_deleteCmd677); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_deleteCmd681);
            	    e=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       exprList.add(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_deleteCmd692); if (state.failed) return n;
            match(input,51,FOLLOW_51_in_deleteCmd694); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_deleteCmd698); if (state.failed) return n;
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
    // Cmd.g:222:1: setCmd returns [ASTCmd n] : 'set' e1= expression COLON_EQUAL e2= expression ;
    public final ASTCmd setCmd() throws RecognitionException {
        ASTCmd n = null;

        ASTExpression e1 = null;

        ASTExpression e2 = null;


        try {
            // Cmd.g:223:1: ( 'set' e1= expression COLON_EQUAL e2= expression )
            // Cmd.g:224:5: 'set' e1= expression COLON_EQUAL e2= expression
            {
            match(input,52,FOLLOW_52_in_setCmd728); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_setCmd732);
            e1=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_setCmd734); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_setCmd738);
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
    // Cmd.g:236:1: opEnterCmd returns [ASTCmd n] : 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN ;
    public final ASTCmd opEnterCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        ASTExpression e = null;


        ASTOpEnterCmd nOpEnter = null;
        try {
            // Cmd.g:238:1: ( 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )
            // Cmd.g:239:5: 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
            {
            match(input,53,FOLLOW_53_in_opEnterCmd772); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_opEnterCmd781);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_opEnterCmd785); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               nOpEnter = new ASTOpEnterCmd(e, id); n = nOpEnter;
            }
            match(input,LPAREN,FOLLOW_LPAREN_in_opEnterCmd793); if (state.failed) return n;
            // Cmd.g:242:5: (e= expression ( COMMA e= expression )* )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( ((LA9_0>=IDENT && LA9_0<=LPAREN)||LA9_0==INT||(LA9_0>=PLUS && LA9_0<=MINUS)||(LA9_0>=REAL && LA9_0<=HASH)||LA9_0==55||LA9_0==88||(LA9_0>=90 && LA9_0<=94)||(LA9_0>=98 && LA9_0<=109)) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // Cmd.g:242:7: e= expression ( COMMA e= expression )*
                    {
                    pushFollow(FOLLOW_expression_in_opEnterCmd803);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       nOpEnter.addArg(e); 
                    }
                    // Cmd.g:242:47: ( COMMA e= expression )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0==COMMA) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // Cmd.g:242:49: COMMA e= expression
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_opEnterCmd809); if (state.failed) return n;
                    	    pushFollow(FOLLOW_expression_in_opEnterCmd813);
                    	    e=expression();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       nOpEnter.addArg(e); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop8;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_opEnterCmd827); if (state.failed) return n;

            }

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
    // Cmd.g:252:1: opExitCmd returns [ASTCmd n] : 'opexit' ( ( expression )=>e= expression | ) ;
    public final ASTCmd opExitCmd() throws RecognitionException {
        ASTCmd n = null;

        ASTExpression e = null;


        try {
            // Cmd.g:253:1: ( 'opexit' ( ( expression )=>e= expression | ) )
            // Cmd.g:254:5: 'opexit' ( ( expression )=>e= expression | )
            {
            match(input,54,FOLLOW_54_in_opExitCmd851); if (state.failed) return n;
            // Cmd.g:254:14: ( ( expression )=>e= expression | )
            int alt10=2;
            alt10 = dfa10.predict(input);
            switch (alt10) {
                case 1 :
                    // Cmd.g:254:15: ( expression )=>e= expression
                    {
                    pushFollow(FOLLOW_expression_in_opExitCmd861);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 2 :
                    // Cmd.g:254:45: 
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
    // Cmd.g:263:1: letCmd returns [ASTCmd n] : 'let' name= IDENT ( COLON t= type )? EQUAL e= expression ;
    public final ASTCmd letCmd() throws RecognitionException {
        ASTCmd n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // Cmd.g:264:1: ( 'let' name= IDENT ( COLON t= type )? EQUAL e= expression )
            // Cmd.g:265:5: 'let' name= IDENT ( COLON t= type )? EQUAL e= expression
            {
            match(input,55,FOLLOW_55_in_letCmd894); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_letCmd898); if (state.failed) return n;
            // Cmd.g:265:22: ( COLON t= type )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==COLON) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // Cmd.g:265:24: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_letCmd902); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_letCmd906);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            match(input,EQUAL,FOLLOW_EQUAL_in_letCmd911); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_letCmd915);
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
    // Cmd.g:272:1: hideCmd returns [ASTCmd n] : 'hide' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) ;
    public final ASTCmd hideCmd() throws RecognitionException {
        ASTCmd n = null;

        Token classname=null;
        Token ass=null;
        List objList = null;


        try {
            // Cmd.g:273:1: ( 'hide' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) )
            // Cmd.g:274:2: 'hide' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            {
            match(input,56,FOLLOW_56_in_hideCmd942); if (state.failed) return n;
            // Cmd.g:274:9: ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            int alt13=3;
            switch ( input.LA(1) ) {
            case 57:
                {
                alt13=1;
                }
                break;
            case IDENT:
                {
                alt13=2;
                }
                break;
            case 58:
                {
                alt13=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }

            switch (alt13) {
                case 1 :
                    // Cmd.g:275:6: 'all'
                    {
                    match(input,57,FOLLOW_57_in_hideCmd951); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideAllCmd(Mode.HIDE); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:276:6: objList= idList ( COLON classname= IDENT )?
                    {
                    pushFollow(FOLLOW_idList_in_hideCmd964);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    // Cmd.g:276:23: ( COLON classname= IDENT )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==COLON) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // Cmd.g:276:24: COLON classname= IDENT
                            {
                            match(input,COLON,FOLLOW_COLON_in_hideCmd967); if (state.failed) return n;
                            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_hideCmd973); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideCropObjectsCmd(Mode.HIDE, objList, classname); 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:277:6: 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT
                    {
                    match(input,58,FOLLOW_58_in_hideCmd984); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_hideCmd986); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_hideCmd992);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_hideCmd994); if (state.failed) return n;
                    match(input,51,FOLLOW_51_in_hideCmd996); if (state.failed) return n;
                    ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_hideCmd1000); if (state.failed) return n;
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
    // Cmd.g:283:1: showCmd returns [ASTCmd n] : 'show' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) ;
    public final ASTCmd showCmd() throws RecognitionException {
        ASTCmd n = null;

        Token classname=null;
        Token ass=null;
        List objList = null;


        try {
            // Cmd.g:284:1: ( 'show' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) )
            // Cmd.g:285:2: 'show' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            {
            match(input,59,FOLLOW_59_in_showCmd1025); if (state.failed) return n;
            // Cmd.g:285:9: ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            int alt15=3;
            switch ( input.LA(1) ) {
            case 57:
                {
                alt15=1;
                }
                break;
            case IDENT:
                {
                alt15=2;
                }
                break;
            case 58:
                {
                alt15=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // Cmd.g:286:6: 'all'
                    {
                    match(input,57,FOLLOW_57_in_showCmd1034); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideAllCmd(Mode.SHOW); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:287:6: objList= idList ( COLON classname= IDENT )?
                    {
                    pushFollow(FOLLOW_idList_in_showCmd1047);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    // Cmd.g:287:23: ( COLON classname= IDENT )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==COLON) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // Cmd.g:287:24: COLON classname= IDENT
                            {
                            match(input,COLON,FOLLOW_COLON_in_showCmd1050); if (state.failed) return n;
                            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_showCmd1056); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideCropObjectsCmd(Mode.SHOW, objList, classname); 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:288:6: 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT
                    {
                    match(input,58,FOLLOW_58_in_showCmd1067); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_showCmd1069); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_showCmd1075);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_showCmd1077); if (state.failed) return n;
                    match(input,51,FOLLOW_51_in_showCmd1079); if (state.failed) return n;
                    ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_showCmd1083); if (state.failed) return n;
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
    // Cmd.g:294:1: cropCmd returns [ASTCmd n] : 'crop' ( | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) ;
    public final ASTCmd cropCmd() throws RecognitionException {
        ASTCmd n = null;

        Token classname=null;
        Token ass=null;
        List objList = null;


        try {
            // Cmd.g:295:1: ( 'crop' ( | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) )
            // Cmd.g:296:2: 'crop' ( | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            {
            match(input,60,FOLLOW_60_in_cropCmd1108); if (state.failed) return n;
            // Cmd.g:296:9: ( | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            int alt17=3;
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
                alt17=1;
                }
                break;
            case IDENT:
                {
                alt17=2;
                }
                break;
            case 58:
                {
                alt17=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }

            switch (alt17) {
                case 1 :
                    // Cmd.g:297:4: 
                    {
                    }
                    break;
                case 2 :
                    // Cmd.g:297:6: objList= idList ( COLON classname= IDENT )?
                    {
                    pushFollow(FOLLOW_idList_in_cropCmd1121);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    // Cmd.g:297:23: ( COLON classname= IDENT )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==COLON) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // Cmd.g:297:24: COLON classname= IDENT
                            {
                            match(input,COLON,FOLLOW_COLON_in_cropCmd1124); if (state.failed) return n;
                            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_cropCmd1130); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideCropObjectsCmd(Mode.CROP, objList, classname); 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:298:6: 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT
                    {
                    match(input,58,FOLLOW_58_in_cropCmd1141); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_cropCmd1143); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_cropCmd1149);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_cropCmd1151); if (state.failed) return n;
                    match(input,51,FOLLOW_51_in_cropCmd1153); if (state.failed) return n;
                    ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_cropCmd1157); if (state.failed) return n;
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
    // Cmd.g:315:1: model returns [ASTModel n] : 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF ;
    public final ASTModel model() throws RecognitionException {
        ASTModel n = null;

        Token modelName=null;
        ASTEnumTypeDefinition e = null;

        ASTAssociation a = null;

        ASTConstraintDefinition cons = null;

        ASTPrePost ppc = null;


        try {
            // Cmd.g:316:1: ( 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF )
            // Cmd.g:317:5: 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF
            {
            match(input,61,FOLLOW_61_in_model1186); if (state.failed) return n;
            modelName=(Token)match(input,IDENT,FOLLOW_IDENT_in_model1190); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTModel(modelName); 
            }
            // Cmd.g:318:5: (e= enumTypeDefinition )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==63) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // Cmd.g:318:7: e= enumTypeDefinition
            	    {
            	    pushFollow(FOLLOW_enumTypeDefinition_in_model1202);
            	    e=enumTypeDefinition();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addEnumTypeDef(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

            // Cmd.g:319:5: ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )*
            loop20:
            do {
                int alt20=4;
                switch ( input.LA(1) ) {
                case 64:
                case 65:
                case 69:
                case 70:
                    {
                    alt20=1;
                    }
                    break;
                case IDENT:
                case 71:
                case 72:
                    {
                    alt20=2;
                    }
                    break;
                case 62:
                    {
                    alt20=3;
                    }
                    break;

                }

                switch (alt20) {
            	case 1 :
            	    // Cmd.g:319:9: ( generalClassDefinition[$n] )
            	    {
            	    // Cmd.g:319:9: ( generalClassDefinition[$n] )
            	    // Cmd.g:319:11: generalClassDefinition[$n]
            	    {
            	    pushFollow(FOLLOW_generalClassDefinition_in_model1219);
            	    generalClassDefinition(n);

            	    state._fsp--;
            	    if (state.failed) return n;

            	    }


            	    }
            	    break;
            	case 2 :
            	    // Cmd.g:320:9: (a= associationDefinition )
            	    {
            	    // Cmd.g:320:9: (a= associationDefinition )
            	    // Cmd.g:320:11: a= associationDefinition
            	    {
            	    pushFollow(FOLLOW_associationDefinition_in_model1236);
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
            	    // Cmd.g:321:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    {
            	    // Cmd.g:321:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    // Cmd.g:321:11: 'constraints' (cons= invariant | ppc= prePost )*
            	    {
            	    match(input,62,FOLLOW_62_in_model1252); if (state.failed) return n;
            	    // Cmd.g:322:11: (cons= invariant | ppc= prePost )*
            	    loop19:
            	    do {
            	        int alt19=3;
            	        int LA19_0 = input.LA(1);

            	        if ( (LA19_0==77) ) {
            	            int LA19_2 = input.LA(2);

            	            if ( (LA19_2==IDENT) ) {
            	                int LA19_3 = input.LA(3);

            	                if ( (LA19_3==COLON_COLON) ) {
            	                    alt19=2;
            	                }
            	                else if ( (LA19_3==EOF||LA19_3==COLON||LA19_3==IDENT||LA19_3==COMMA||LA19_3==62||(LA19_3>=64 && LA19_3<=65)||(LA19_3>=69 && LA19_3<=72)||(LA19_3>=77 && LA19_3<=79)) ) {
            	                    alt19=1;
            	                }


            	            }


            	        }


            	        switch (alt19) {
            	    	case 1 :
            	    	    // Cmd.g:322:15: cons= invariant
            	    	    {
            	    	    pushFollow(FOLLOW_invariant_in_model1270);
            	    	    cons=invariant();

            	    	    state._fsp--;
            	    	    if (state.failed) return n;
            	    	    if ( state.backtracking==0 ) {
            	    	       n.addConstraint(cons); 
            	    	    }

            	    	    }
            	    	    break;
            	    	case 2 :
            	    	    // Cmd.g:323:15: ppc= prePost
            	    	    {
            	    	    pushFollow(FOLLOW_prePost_in_model1291);
            	    	    ppc=prePost();

            	    	    state._fsp--;
            	    	    if (state.failed) return n;
            	    	    if ( state.backtracking==0 ) {
            	    	       n.addPrePost(ppc); 
            	    	    }

            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop19;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);

            match(input,EOF,FOLLOW_EOF_in_model1332); if (state.failed) return n;

            }

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
    // Cmd.g:334:1: enumTypeDefinition returns [ASTEnumTypeDefinition n] : 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )? ;
    public final ASTEnumTypeDefinition enumTypeDefinition() throws RecognitionException {
        ASTEnumTypeDefinition n = null;

        Token name=null;
        List idListRes = null;


        try {
            // Cmd.g:335:1: ( 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )? )
            // Cmd.g:336:5: 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )?
            {
            match(input,63,FOLLOW_63_in_enumTypeDefinition1359); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_enumTypeDefinition1363); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_enumTypeDefinition1365); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_enumTypeDefinition1369);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,RBRACE,FOLLOW_RBRACE_in_enumTypeDefinition1371); if (state.failed) return n;
            // Cmd.g:336:54: ( SEMI )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==SEMI) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // Cmd.g:336:56: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_enumTypeDefinition1375); if (state.failed) return n;

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
    // Cmd.g:345:1: generalClassDefinition[ASTModel n] : ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) ) ;
    public final void generalClassDefinition(ASTModel n) throws RecognitionException {
        ASTClass c = null;

        ASTAssociationClass ac = null;


         
          boolean isAbstract = false;

        try {
            // Cmd.g:349:1: ( ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) ) )
            // Cmd.g:350:5: ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) )
            {
            // Cmd.g:350:5: ( 'abstract' )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==64) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // Cmd.g:350:7: 'abstract'
                    {
                    match(input,64,FOLLOW_64_in_generalClassDefinition1414); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       isAbstract = true; 
                    }

                    }
                    break;

            }

            // Cmd.g:351:5: ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) )
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==65) ) {
                alt23=1;
            }
            else if ( ((LA23_0>=69 && LA23_0<=70)) ) {
                alt23=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }
            switch (alt23) {
                case 1 :
                    // Cmd.g:351:7: (c= classDefinition[isAbstract] )
                    {
                    // Cmd.g:351:7: (c= classDefinition[isAbstract] )
                    // Cmd.g:351:9: c= classDefinition[isAbstract]
                    {
                    pushFollow(FOLLOW_classDefinition_in_generalClassDefinition1432);
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
                    // Cmd.g:352:7: (ac= associationClassDefinition[isAbstract] )
                    {
                    // Cmd.g:352:7: (ac= associationClassDefinition[isAbstract] )
                    // Cmd.g:352:9: ac= associationClassDefinition[isAbstract]
                    {
                    pushFollow(FOLLOW_associationClassDefinition_in_generalClassDefinition1452);
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
    // Cmd.g:369:1: classDefinition[boolean isAbstract] returns [ASTClass n] : 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end' ;
    public final ASTClass classDefinition(boolean isAbstract) throws RecognitionException {
        ASTClass n = null;

        Token name=null;
        List idListRes = null;

        ASTAttribute a = null;

        ASTOperation op = null;

        ASTInvariantClause inv = null;


         List idList; 
        try {
            // Cmd.g:371:1: ( 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end' )
            // Cmd.g:372:5: 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end'
            {
            match(input,65,FOLLOW_65_in_classDefinition1491); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_classDefinition1495); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTClass(name, isAbstract); 
            }
            // Cmd.g:373:5: ( LESS idListRes= idList )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==LESS) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // Cmd.g:373:7: LESS idListRes= idList
                    {
                    match(input,LESS,FOLLOW_LESS_in_classDefinition1505); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_classDefinition1509);
                    idListRes=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addSuperClasses(idListRes); 
                    }

                    }
                    break;

            }

            // Cmd.g:374:5: ( 'attributes' (a= attributeDefinition )* )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==66) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // Cmd.g:374:7: 'attributes' (a= attributeDefinition )*
                    {
                    match(input,66,FOLLOW_66_in_classDefinition1522); if (state.failed) return n;
                    // Cmd.g:375:7: (a= attributeDefinition )*
                    loop25:
                    do {
                        int alt25=2;
                        int LA25_0 = input.LA(1);

                        if ( (LA25_0==IDENT) ) {
                            alt25=1;
                        }


                        switch (alt25) {
                    	case 1 :
                    	    // Cmd.g:375:9: a= attributeDefinition
                    	    {
                    	    pushFollow(FOLLOW_attributeDefinition_in_classDefinition1535);
                    	    a=attributeDefinition();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addAttribute(a); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop25;
                        }
                    } while (true);


                    }
                    break;

            }

            // Cmd.g:377:5: ( 'operations' (op= operationDefinition )* )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==67) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // Cmd.g:377:7: 'operations' (op= operationDefinition )*
                    {
                    match(input,67,FOLLOW_67_in_classDefinition1556); if (state.failed) return n;
                    // Cmd.g:378:7: (op= operationDefinition )*
                    loop27:
                    do {
                        int alt27=2;
                        int LA27_0 = input.LA(1);

                        if ( (LA27_0==IDENT) ) {
                            alt27=1;
                        }


                        switch (alt27) {
                    	case 1 :
                    	    // Cmd.g:378:9: op= operationDefinition
                    	    {
                    	    pushFollow(FOLLOW_operationDefinition_in_classDefinition1569);
                    	    op=operationDefinition();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addOperation(op); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop27;
                        }
                    } while (true);


                    }
                    break;

            }

            // Cmd.g:380:5: ( 'constraints' (inv= invariantClause )* )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==62) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // Cmd.g:380:7: 'constraints' (inv= invariantClause )*
                    {
                    match(input,62,FOLLOW_62_in_classDefinition1590); if (state.failed) return n;
                    // Cmd.g:381:7: (inv= invariantClause )*
                    loop29:
                    do {
                        int alt29=2;
                        int LA29_0 = input.LA(1);

                        if ( ((LA29_0>=78 && LA29_0<=79)) ) {
                            alt29=1;
                        }


                        switch (alt29) {
                    	case 1 :
                    	    // Cmd.g:382:9: inv= invariantClause
                    	    {
                    	    pushFollow(FOLLOW_invariantClause_in_classDefinition1610);
                    	    inv=invariantClause();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addInvariantClause(inv); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop29;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,68,FOLLOW_68_in_classDefinition1634); if (state.failed) return n;

            }

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
    // Cmd.g:403:1: associationClassDefinition[boolean isAbstract] returns [ASTAssociationClass n] : classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end' ;
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
            // Cmd.g:405:1: (classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end' )
            // Cmd.g:406:5: classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end'
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
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationClassDefinition1693); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociationClass(name, isAbstract); 
            }
            // Cmd.g:415:5: ( LESS idListRes= idList )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==LESS) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // Cmd.g:415:7: LESS idListRes= idList
                    {
                    match(input,LESS,FOLLOW_LESS_in_associationClassDefinition1703); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_associationClassDefinition1707);
                    idListRes=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addSuperClasses(idListRes); 
                    }

                    }
                    break;

            }

            match(input,46,FOLLOW_46_in_associationClassDefinition1718); if (state.failed) return n;
            pushFollow(FOLLOW_associationEnd_in_associationClassDefinition1726);
            ae=associationEnd();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addEnd(ae); 
            }
            // Cmd.g:418:5: (ae= associationEnd )+
            int cnt32=0;
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==IDENT) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // Cmd.g:418:7: ae= associationEnd
            	    {
            	    pushFollow(FOLLOW_associationEnd_in_associationClassDefinition1738);
            	    ae=associationEnd();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addEnd(ae); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt32 >= 1 ) break loop32;
            	    if (state.backtracking>0) {state.failed=true; return n;}
                        EarlyExitException eee =
                            new EarlyExitException(32, input);
                        throw eee;
                }
                cnt32++;
            } while (true);

            // Cmd.g:419:5: ( 'attributes' (a= attributeDefinition )* )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==66) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // Cmd.g:419:7: 'attributes' (a= attributeDefinition )*
                    {
                    match(input,66,FOLLOW_66_in_associationClassDefinition1751); if (state.failed) return n;
                    // Cmd.g:420:7: (a= attributeDefinition )*
                    loop33:
                    do {
                        int alt33=2;
                        int LA33_0 = input.LA(1);

                        if ( (LA33_0==IDENT) ) {
                            alt33=1;
                        }


                        switch (alt33) {
                    	case 1 :
                    	    // Cmd.g:420:9: a= attributeDefinition
                    	    {
                    	    pushFollow(FOLLOW_attributeDefinition_in_associationClassDefinition1764);
                    	    a=attributeDefinition();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addAttribute(a); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop33;
                        }
                    } while (true);


                    }
                    break;

            }

            // Cmd.g:422:5: ( 'operations' (op= operationDefinition )* )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==67) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // Cmd.g:422:7: 'operations' (op= operationDefinition )*
                    {
                    match(input,67,FOLLOW_67_in_associationClassDefinition1785); if (state.failed) return n;
                    // Cmd.g:423:7: (op= operationDefinition )*
                    loop35:
                    do {
                        int alt35=2;
                        int LA35_0 = input.LA(1);

                        if ( (LA35_0==IDENT) ) {
                            alt35=1;
                        }


                        switch (alt35) {
                    	case 1 :
                    	    // Cmd.g:423:9: op= operationDefinition
                    	    {
                    	    pushFollow(FOLLOW_operationDefinition_in_associationClassDefinition1798);
                    	    op=operationDefinition();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addOperation(op); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop35;
                        }
                    } while (true);


                    }
                    break;

            }

            // Cmd.g:425:5: ( 'constraints' (inv= invariantClause )* )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==62) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // Cmd.g:425:7: 'constraints' (inv= invariantClause )*
                    {
                    match(input,62,FOLLOW_62_in_associationClassDefinition1819); if (state.failed) return n;
                    // Cmd.g:426:7: (inv= invariantClause )*
                    loop37:
                    do {
                        int alt37=2;
                        int LA37_0 = input.LA(1);

                        if ( ((LA37_0>=78 && LA37_0<=79)) ) {
                            alt37=1;
                        }


                        switch (alt37) {
                    	case 1 :
                    	    // Cmd.g:427:9: inv= invariantClause
                    	    {
                    	    pushFollow(FOLLOW_invariantClause_in_associationClassDefinition1839);
                    	    inv=invariantClause();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addInvariantClause(inv); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop37;
                        }
                    } while (true);


                    }
                    break;

            }

            // Cmd.g:430:5: ( ( 'aggregation' | 'composition' ) )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( ((LA39_0>=71 && LA39_0<=72)) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // Cmd.g:430:7: ( 'aggregation' | 'composition' )
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

            match(input,68,FOLLOW_68_in_associationClassDefinition1902); if (state.failed) return n;

            }

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
    // Cmd.g:441:1: attributeDefinition returns [ASTAttribute n] : name= IDENT COLON t= type ( SEMI )? ;
    public final ASTAttribute attributeDefinition() throws RecognitionException {
        ASTAttribute n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Cmd.g:442:1: (name= IDENT COLON t= type ( SEMI )? )
            // Cmd.g:443:5: name= IDENT COLON t= type ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_attributeDefinition1931); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_attributeDefinition1933); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_attributeDefinition1937);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            // Cmd.g:443:29: ( SEMI )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==SEMI) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // Cmd.g:443:31: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_attributeDefinition1941); if (state.failed) return n;

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
    // Cmd.g:452:1: operationDefinition returns [ASTOperation n] : name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression | EQUAL 'script' body= SCRIPTBODY )? (ppc= prePostClause )* ( SEMI )? ;
    public final ASTOperation operationDefinition() throws RecognitionException {
        ASTOperation n = null;

        Token name=null;
        Token body=null;
        List pl = null;

        ASTType t = null;

        ASTExpression e = null;

        ASTPrePostClause ppc = null;


        try {
            // Cmd.g:453:1: (name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression | EQUAL 'script' body= SCRIPTBODY )? (ppc= prePostClause )* ( SEMI )? )
            // Cmd.g:454:5: name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression | EQUAL 'script' body= SCRIPTBODY )? (ppc= prePostClause )* ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationDefinition1979); if (state.failed) return n;
            pushFollow(FOLLOW_paramList_in_operationDefinition1987);
            pl=paramList();

            state._fsp--;
            if (state.failed) return n;
            // Cmd.g:456:5: ( COLON t= type )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==COLON) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // Cmd.g:456:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_operationDefinition1995); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_operationDefinition1999);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTOperation(name, pl, t); 
            }
            // Cmd.g:459:5: ( EQUAL e= expression | EQUAL 'script' body= SCRIPTBODY )?
            int alt42=3;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==EQUAL) ) {
                int LA42_1 = input.LA(2);

                if ( (LA42_1==73) ) {
                    alt42=2;
                }
                else if ( ((LA42_1>=IDENT && LA42_1<=LPAREN)||LA42_1==INT||(LA42_1>=PLUS && LA42_1<=MINUS)||(LA42_1>=REAL && LA42_1<=HASH)||LA42_1==55||LA42_1==88||(LA42_1>=90 && LA42_1<=94)||(LA42_1>=98 && LA42_1<=109)) ) {
                    alt42=1;
                }
            }
            switch (alt42) {
                case 1 :
                    // Cmd.g:460:6: EQUAL e= expression
                    {
                    match(input,EQUAL,FOLLOW_EQUAL_in_operationDefinition2027); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_operationDefinition2031);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setExpression(e); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:463:6: EQUAL 'script' body= SCRIPTBODY
                    {
                    match(input,EQUAL,FOLLOW_EQUAL_in_operationDefinition2053); if (state.failed) return n;
                    match(input,73,FOLLOW_73_in_operationDefinition2055); if (state.failed) return n;
                    body=(Token)match(input,SCRIPTBODY,FOLLOW_SCRIPTBODY_in_operationDefinition2059); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setScript(body); 
                    }

                    }
                    break;

            }

            // Cmd.g:467:5: (ppc= prePostClause )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( ((LA43_0>=80 && LA43_0<=81)) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // Cmd.g:467:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_operationDefinition2090);
            	    ppc=prePostClause();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addPrePostClause(ppc); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop43;
                }
            } while (true);

            // Cmd.g:468:5: ( SEMI )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==SEMI) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // Cmd.g:468:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_operationDefinition2103); if (state.failed) return n;

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
    // Cmd.g:478:1: associationDefinition returns [ASTAssociation n] : ( keyAssociation | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end' ;
    public final ASTAssociation associationDefinition() throws RecognitionException {
        ASTAssociation n = null;

        Token name=null;
        ASTAssociationEnd ae = null;


         Token t = null; 
        try {
            // Cmd.g:480:1: ( ( keyAssociation | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end' )
            // Cmd.g:481:5: ( keyAssociation | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end'
            {
            if ( state.backtracking==0 ) {
               t = input.LT(1); 
            }
            // Cmd.g:482:5: ( keyAssociation | 'aggregation' | 'composition' )
            int alt45=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt45=1;
                }
                break;
            case 71:
                {
                alt45=2;
                }
                break;
            case 72:
                {
                alt45=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;
            }

            switch (alt45) {
                case 1 :
                    // Cmd.g:482:7: keyAssociation
                    {
                    pushFollow(FOLLOW_keyAssociation_in_associationDefinition2141);
                    keyAssociation();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 2 :
                    // Cmd.g:482:24: 'aggregation'
                    {
                    match(input,71,FOLLOW_71_in_associationDefinition2145); if (state.failed) return n;

                    }
                    break;
                case 3 :
                    // Cmd.g:482:40: 'composition'
                    {
                    match(input,72,FOLLOW_72_in_associationDefinition2149); if (state.failed) return n;

                    }
                    break;

            }

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationDefinition2164); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociation(t, name); 
            }
            match(input,46,FOLLOW_46_in_associationDefinition2172); if (state.failed) return n;
            pushFollow(FOLLOW_associationEnd_in_associationDefinition2180);
            ae=associationEnd();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addEnd(ae); 
            }
            // Cmd.g:487:5: (ae= associationEnd )+
            int cnt46=0;
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==IDENT) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // Cmd.g:487:7: ae= associationEnd
            	    {
            	    pushFollow(FOLLOW_associationEnd_in_associationDefinition2192);
            	    ae=associationEnd();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addEnd(ae); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt46 >= 1 ) break loop46;
            	    if (state.backtracking>0) {state.failed=true; return n;}
                        EarlyExitException eee =
                            new EarlyExitException(46, input);
                        throw eee;
                }
                cnt46++;
            } while (true);

            match(input,68,FOLLOW_68_in_associationDefinition2203); if (state.failed) return n;

            }

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
    // Cmd.g:496:1: associationEnd returns [ASTAssociationEnd n] : name= IDENT LBRACK m= multiplicity RBRACK ( keyRole rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )? ;
    public final ASTAssociationEnd associationEnd() throws RecognitionException {
        ASTAssociationEnd n = null;

        Token name=null;
        Token rn=null;
        Token sr=null;
        Token rd=null;
        ASTMultiplicity m = null;


        try {
            // Cmd.g:497:1: (name= IDENT LBRACK m= multiplicity RBRACK ( keyRole rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )? )
            // Cmd.g:498:5: name= IDENT LBRACK m= multiplicity RBRACK ( keyRole rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd2229); if (state.failed) return n;
            match(input,LBRACK,FOLLOW_LBRACK_in_associationEnd2231); if (state.failed) return n;
            pushFollow(FOLLOW_multiplicity_in_associationEnd2235);
            m=multiplicity();

            state._fsp--;
            if (state.failed) return n;
            match(input,RBRACK,FOLLOW_RBRACK_in_associationEnd2237); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociationEnd(name, m); 
            }
            // Cmd.g:499:5: ( keyRole rn= IDENT )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==IDENT) ) {
                int LA47_1 = input.LA(2);

                if ( (LA47_1==IDENT) ) {
                    int LA47_3 = input.LA(3);

                    if ( ((input.LT(1).getText().equals("role"))) ) {
                        alt47=1;
                    }
                }
            }
            switch (alt47) {
                case 1 :
                    // Cmd.g:499:7: keyRole rn= IDENT
                    {
                    pushFollow(FOLLOW_keyRole_in_associationEnd2248);
                    keyRole();

                    state._fsp--;
                    if (state.failed) return n;
                    rn=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd2252); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setRolename(rn); 
                    }

                    }
                    break;

            }

            // Cmd.g:500:5: ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )*
            loop48:
            do {
                int alt48=5;
                switch ( input.LA(1) ) {
                case IDENT:
                    {
                    int LA48_2 = input.LA(2);

                    if ( (LA48_2==SEMI||LA48_2==IDENT||LA48_2==62||(LA48_2>=66 && LA48_2<=68)||(LA48_2>=71 && LA48_2<=72)||(LA48_2>=74 && LA48_2<=76)) ) {
                        alt48=3;
                    }


                    }
                    break;
                case 74:
                    {
                    alt48=1;
                    }
                    break;
                case 75:
                    {
                    alt48=2;
                    }
                    break;
                case 76:
                    {
                    alt48=4;
                    }
                    break;

                }

                switch (alt48) {
            	case 1 :
            	    // Cmd.g:501:9: 'ordered'
            	    {
            	    match(input,74,FOLLOW_74_in_associationEnd2273); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.setOrdered(); 
            	    }

            	    }
            	    break;
            	case 2 :
            	    // Cmd.g:502:9: 'subsets' sr= IDENT
            	    {
            	    match(input,75,FOLLOW_75_in_associationEnd2285); if (state.failed) return n;
            	    sr=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd2289); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addSubsetsRolename(sr); 
            	    }

            	    }
            	    break;
            	case 3 :
            	    // Cmd.g:503:9: keyUnion
            	    {
            	    pushFollow(FOLLOW_keyUnion_in_associationEnd2301);
            	    keyUnion();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.setUnion(true); 
            	    }

            	    }
            	    break;
            	case 4 :
            	    // Cmd.g:504:9: 'redefines' rd= IDENT
            	    {
            	    match(input,76,FOLLOW_76_in_associationEnd2313); if (state.failed) return n;
            	    rd=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd2317); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addRedefinesRolename(rd); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop48;
                }
            } while (true);

            // Cmd.g:506:5: ( SEMI )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==SEMI) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // Cmd.g:506:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_associationEnd2334); if (state.failed) return n;

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
    // Cmd.g:520:1: multiplicity returns [ASTMultiplicity n] : mr= multiplicityRange ( COMMA mr= multiplicityRange )* ;
    public final ASTMultiplicity multiplicity() throws RecognitionException {
        ASTMultiplicity n = null;

        ASTMultiplicityRange mr = null;


        try {
            // Cmd.g:521:1: (mr= multiplicityRange ( COMMA mr= multiplicityRange )* )
            // Cmd.g:522:5: mr= multiplicityRange ( COMMA mr= multiplicityRange )*
            {
            if ( state.backtracking==0 ) {
               
              	Token t = input.LT(1); // remember start position of expression
              	n = new ASTMultiplicity(t);
                  
            }
            pushFollow(FOLLOW_multiplicityRange_in_multiplicity2369);
            mr=multiplicityRange();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addRange(mr); 
            }
            // Cmd.g:527:5: ( COMMA mr= multiplicityRange )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==COMMA) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // Cmd.g:527:7: COMMA mr= multiplicityRange
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_multiplicity2379); if (state.failed) return n;
            	    pushFollow(FOLLOW_multiplicityRange_in_multiplicity2383);
            	    mr=multiplicityRange();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addRange(mr); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop50;
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
    // Cmd.g:530:1: multiplicityRange returns [ASTMultiplicityRange n] : ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? ;
    public final ASTMultiplicityRange multiplicityRange() throws RecognitionException {
        ASTMultiplicityRange n = null;

        int ms1 = 0;

        int ms2 = 0;


        try {
            // Cmd.g:531:1: (ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? )
            // Cmd.g:532:5: ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )?
            {
            pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange2412);
            ms1=multiplicitySpec();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTMultiplicityRange(ms1); 
            }
            // Cmd.g:533:5: ( DOTDOT ms2= multiplicitySpec )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==DOTDOT) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // Cmd.g:533:7: DOTDOT ms2= multiplicitySpec
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_multiplicityRange2422); if (state.failed) return n;
                    pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange2426);
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
    // Cmd.g:536:1: multiplicitySpec returns [int m] : (i= INT | STAR );
    public final int multiplicitySpec() throws RecognitionException {
        int m = 0;

        Token i=null;

         m = -1; 
        try {
            // Cmd.g:538:1: (i= INT | STAR )
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==INT) ) {
                alt52=1;
            }
            else if ( (LA52_0==STAR) ) {
                alt52=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return m;}
                NoViableAltException nvae =
                    new NoViableAltException("", 52, 0, input);

                throw nvae;
            }
            switch (alt52) {
                case 1 :
                    // Cmd.g:539:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_multiplicitySpec2460); if (state.failed) return m;
                    if ( state.backtracking==0 ) {
                       m = Integer.parseInt((i!=null?i.getText():null)); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:540:7: STAR
                    {
                    match(input,STAR,FOLLOW_STAR_in_multiplicitySpec2470); if (state.failed) return m;
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
    // Cmd.g:561:1: invariant returns [ASTConstraintDefinition n] : 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )* ;
    public final ASTConstraintDefinition invariant() throws RecognitionException {
        ASTConstraintDefinition n = null;

        Token v=null;
        ASTSimpleType t = null;

        ASTInvariantClause inv = null;


        try {
            // Cmd.g:562:1: ( 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )* )
            // Cmd.g:563:5: 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )*
            {
            if ( state.backtracking==0 ) {
               n = new ASTConstraintDefinition(); 
            }
            match(input,77,FOLLOW_77_in_invariant2511); if (state.failed) return n;
            // Cmd.g:565:5: (v= IDENT ( ',' v= IDENT )* COLON )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==IDENT) ) {
                int LA54_1 = input.LA(2);

                if ( (LA54_1==COLON||LA54_1==COMMA) ) {
                    alt54=1;
                }
            }
            switch (alt54) {
                case 1 :
                    // Cmd.g:565:7: v= IDENT ( ',' v= IDENT )* COLON
                    {
                    v=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariant2521); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addVarName(v); 
                    }
                    // Cmd.g:566:8: ( ',' v= IDENT )*
                    loop53:
                    do {
                        int alt53=2;
                        int LA53_0 = input.LA(1);

                        if ( (LA53_0==COMMA) ) {
                            alt53=1;
                        }


                        switch (alt53) {
                    	case 1 :
                    	    // Cmd.g:566:9: ',' v= IDENT
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_invariant2534); if (state.failed) return n;
                    	    v=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariant2538); if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addVarName(v); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop53;
                        }
                    } while (true);

                    match(input,COLON,FOLLOW_COLON_in_invariant2546); if (state.failed) return n;

                    }
                    break;

            }

            pushFollow(FOLLOW_simpleType_in_invariant2558);
            t=simpleType();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setType(t); 
            }
            // Cmd.g:568:5: (inv= invariantClause )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( ((LA55_0>=78 && LA55_0<=79)) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // Cmd.g:568:7: inv= invariantClause
            	    {
            	    pushFollow(FOLLOW_invariantClause_in_invariant2570);
            	    inv=invariantClause();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addInvariantClause(inv); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop55;
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
    // Cmd.g:575:1: invariantClause returns [ASTInvariantClause n] : ( 'inv' (name= IDENT )? COLON e= expression | 'existential' 'inv' (name= IDENT )? COLON e= expression );
    public final ASTInvariantClause invariantClause() throws RecognitionException {
        ASTInvariantClause n = null;

        Token name=null;
        ASTExpression e = null;


        try {
            // Cmd.g:576:1: ( 'inv' (name= IDENT )? COLON e= expression | 'existential' 'inv' (name= IDENT )? COLON e= expression )
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==78) ) {
                alt58=1;
            }
            else if ( (LA58_0==79) ) {
                alt58=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 58, 0, input);

                throw nvae;
            }
            switch (alt58) {
                case 1 :
                    // Cmd.g:577:7: 'inv' (name= IDENT )? COLON e= expression
                    {
                    match(input,78,FOLLOW_78_in_invariantClause2601); if (state.failed) return n;
                    // Cmd.g:577:13: (name= IDENT )?
                    int alt56=2;
                    int LA56_0 = input.LA(1);

                    if ( (LA56_0==IDENT) ) {
                        alt56=1;
                    }
                    switch (alt56) {
                        case 1 :
                            // Cmd.g:577:15: name= IDENT
                            {
                            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantClause2607); if (state.failed) return n;

                            }
                            break;

                    }

                    match(input,COLON,FOLLOW_COLON_in_invariantClause2612); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_invariantClause2616);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTInvariantClause(name, e); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:578:7: 'existential' 'inv' (name= IDENT )? COLON e= expression
                    {
                    match(input,79,FOLLOW_79_in_invariantClause2626); if (state.failed) return n;
                    match(input,78,FOLLOW_78_in_invariantClause2628); if (state.failed) return n;
                    // Cmd.g:578:27: (name= IDENT )?
                    int alt57=2;
                    int LA57_0 = input.LA(1);

                    if ( (LA57_0==IDENT) ) {
                        alt57=1;
                    }
                    switch (alt57) {
                        case 1 :
                            // Cmd.g:578:29: name= IDENT
                            {
                            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantClause2634); if (state.failed) return n;

                            }
                            break;

                    }

                    match(input,COLON,FOLLOW_COLON_in_invariantClause2639); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_invariantClause2643);
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
    // Cmd.g:589:1: prePost returns [ASTPrePost n] : 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ ;
    public final ASTPrePost prePost() throws RecognitionException {
        ASTPrePost n = null;

        Token classname=null;
        Token opname=null;
        List pl = null;

        ASTType rt = null;

        ASTPrePostClause ppc = null;


        try {
            // Cmd.g:590:1: ( 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ )
            // Cmd.g:591:5: 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+
            {
            match(input,77,FOLLOW_77_in_prePost2669); if (state.failed) return n;
            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost2673); if (state.failed) return n;
            match(input,COLON_COLON,FOLLOW_COLON_COLON_in_prePost2675); if (state.failed) return n;
            opname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost2679); if (state.failed) return n;
            pushFollow(FOLLOW_paramList_in_prePost2683);
            pl=paramList();

            state._fsp--;
            if (state.failed) return n;
            // Cmd.g:591:69: ( COLON rt= type )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==COLON) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // Cmd.g:591:71: COLON rt= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_prePost2687); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_prePost2691);
                    rt=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTPrePost(classname, opname, pl, rt); 
            }
            // Cmd.g:593:5: (ppc= prePostClause )+
            int cnt60=0;
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( ((LA60_0>=80 && LA60_0<=81)) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // Cmd.g:593:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_prePost2710);
            	    ppc=prePostClause();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addPrePostClause(ppc); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt60 >= 1 ) break loop60;
            	    if (state.backtracking>0) {state.failed=true; return n;}
                        EarlyExitException eee =
                            new EarlyExitException(60, input);
                        throw eee;
                }
                cnt60++;
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
    // Cmd.g:600:1: prePostClause returns [ASTPrePostClause n] : ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression ;
    public final ASTPrePostClause prePostClause() throws RecognitionException {
        ASTPrePostClause n = null;

        Token name=null;
        ASTExpression e = null;


         Token t = null; 
        try {
            // Cmd.g:602:1: ( ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression )
            // Cmd.g:603:5: ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression
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

            // Cmd.g:604:25: (name= IDENT )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==IDENT) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // Cmd.g:604:27: name= IDENT
                    {
                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePostClause2764); if (state.failed) return n;

                    }
                    break;

            }

            match(input,COLON,FOLLOW_COLON_in_prePostClause2769); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_prePostClause2773);
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
    // Cmd.g:608:1: keyUnion : {...}? IDENT ;
    public final void keyUnion() throws RecognitionException {
        try {
            // Cmd.g:608:9: ({...}? IDENT )
            // Cmd.g:609:3: {...}? IDENT
            {
            if ( !((input.LT(1).getText().equals("union"))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "keyUnion", "input.LT(1).getText().equals(\"union\")");
            }
            match(input,IDENT,FOLLOW_IDENT_in_keyUnion2795); if (state.failed) return ;

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
    // Cmd.g:611:1: keyAssociation : {...}? IDENT ;
    public final void keyAssociation() throws RecognitionException {
        try {
            // Cmd.g:611:15: ({...}? IDENT )
            // Cmd.g:612:3: {...}? IDENT
            {
            if ( !((input.LT(1).getText().equals("association"))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "keyAssociation", "input.LT(1).getText().equals(\"association\")");
            }
            match(input,IDENT,FOLLOW_IDENT_in_keyAssociation2809); if (state.failed) return ;

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
    // Cmd.g:614:1: keyRole : {...}? IDENT ;
    public final void keyRole() throws RecognitionException {
        try {
            // Cmd.g:614:8: ({...}? IDENT )
            // Cmd.g:615:3: {...}? IDENT
            {
            if ( !((input.LT(1).getText().equals("role"))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "keyRole", "input.LT(1).getText().equals(\"role\")");
            }
            match(input,IDENT,FOLLOW_IDENT_in_keyRole2823); if (state.failed) return ;

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
    // Cmd.g:645:1: expressionOnly returns [ASTExpression n] : nExp= expression EOF ;
    public final ASTExpression expressionOnly() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExp = null;


        try {
            // Cmd.g:646:1: (nExp= expression EOF )
            // Cmd.g:647:5: nExp= expression EOF
            {
            pushFollow(FOLLOW_expression_in_expressionOnly2853);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_expressionOnly2855); if (state.failed) return n;
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
    // Cmd.g:654:1: expression returns [ASTExpression n] : ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression ;
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
            // Cmd.g:660:1: ( ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression )
            // Cmd.g:661:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of expression */ 
            }
            // Cmd.g:662:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==55) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // Cmd.g:663:7: 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in'
            	    {
            	    match(input,55,FOLLOW_55_in_expression2903); if (state.failed) return n;
            	    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_expression2907); if (state.failed) return n;
            	    // Cmd.g:663:24: ( COLON t= type )?
            	    int alt62=2;
            	    int LA62_0 = input.LA(1);

            	    if ( (LA62_0==COLON) ) {
            	        alt62=1;
            	    }
            	    switch (alt62) {
            	        case 1 :
            	            // Cmd.g:663:26: COLON t= type
            	            {
            	            match(input,COLON,FOLLOW_COLON_in_expression2911); if (state.failed) return n;
            	            pushFollow(FOLLOW_type_in_expression2915);
            	            t=type();

            	            state._fsp--;
            	            if (state.failed) return n;

            	            }
            	            break;

            	    }

            	    match(input,EQUAL,FOLLOW_EQUAL_in_expression2920); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_expression2924);
            	    e1=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    match(input,82,FOLLOW_82_in_expression2926); if (state.failed) return n;
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
            	    break loop63;
                }
            } while (true);

            pushFollow(FOLLOW_conditionalImpliesExpression_in_expression2951);
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
    // Cmd.g:691:1: paramList returns [List paramList] : LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN ;
    public final List paramList() throws RecognitionException {
        List paramList = null;

        ASTVariableDeclaration v = null;


         paramList = new ArrayList(); 
        try {
            // Cmd.g:693:1: ( LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN )
            // Cmd.g:694:5: LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN
            {
            match(input,LPAREN,FOLLOW_LPAREN_in_paramList2984); if (state.failed) return paramList;
            // Cmd.g:695:5: (v= variableDeclaration ( COMMA v= variableDeclaration )* )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==IDENT) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // Cmd.g:696:7: v= variableDeclaration ( COMMA v= variableDeclaration )*
                    {
                    pushFollow(FOLLOW_variableDeclaration_in_paramList3001);
                    v=variableDeclaration();

                    state._fsp--;
                    if (state.failed) return paramList;
                    if ( state.backtracking==0 ) {
                       paramList.add(v); 
                    }
                    // Cmd.g:697:7: ( COMMA v= variableDeclaration )*
                    loop64:
                    do {
                        int alt64=2;
                        int LA64_0 = input.LA(1);

                        if ( (LA64_0==COMMA) ) {
                            alt64=1;
                        }


                        switch (alt64) {
                    	case 1 :
                    	    // Cmd.g:697:9: COMMA v= variableDeclaration
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_paramList3013); if (state.failed) return paramList;
                    	    pushFollow(FOLLOW_variableDeclaration_in_paramList3017);
                    	    v=variableDeclaration();

                    	    state._fsp--;
                    	    if (state.failed) return paramList;
                    	    if ( state.backtracking==0 ) {
                    	       paramList.add(v); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop64;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_paramList3037); if (state.failed) return paramList;

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
    // Cmd.g:705:1: idList returns [List idList] : id0= IDENT ( COMMA idn= IDENT )* ;
    public final List idList() throws RecognitionException {
        List idList = null;

        Token id0=null;
        Token idn=null;

         idList = new ArrayList(); 
        try {
            // Cmd.g:707:1: (id0= IDENT ( COMMA idn= IDENT )* )
            // Cmd.g:708:5: id0= IDENT ( COMMA idn= IDENT )*
            {
            id0=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList3066); if (state.failed) return idList;
            if ( state.backtracking==0 ) {
               idList.add(id0); 
            }
            // Cmd.g:709:5: ( COMMA idn= IDENT )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( (LA66_0==COMMA) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // Cmd.g:709:7: COMMA idn= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_idList3076); if (state.failed) return idList;
            	    idn=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList3080); if (state.failed) return idList;
            	    if ( state.backtracking==0 ) {
            	       idList.add(idn); 
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
        return idList;
    }
    // $ANTLR end "idList"


    // $ANTLR start "variableDeclaration"
    // Cmd.g:717:1: variableDeclaration returns [ASTVariableDeclaration n] : name= IDENT COLON t= type ;
    public final ASTVariableDeclaration variableDeclaration() throws RecognitionException {
        ASTVariableDeclaration n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Cmd.g:718:1: (name= IDENT COLON t= type )
            // Cmd.g:719:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableDeclaration3111); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableDeclaration3113); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableDeclaration3117);
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
    // Cmd.g:727:1: conditionalImpliesExpression returns [ASTExpression n] : nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* ;
    public final ASTExpression conditionalImpliesExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndOrExp = null;

        ASTExpression n1 = null;


        try {
            // Cmd.g:728:1: (nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* )
            // Cmd.g:729:5: nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )*
            {
            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3153);
            nCndOrExp=conditionalOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndOrExp;
            }
            // Cmd.g:730:5: (op= 'implies' n1= conditionalOrExpression )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( (LA67_0==83) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // Cmd.g:730:7: op= 'implies' n1= conditionalOrExpression
            	    {
            	    op=(Token)match(input,83,FOLLOW_83_in_conditionalImpliesExpression3166); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3170);
            	    n1=conditionalOrExpression();

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
    // $ANTLR end "conditionalImpliesExpression"


    // $ANTLR start "conditionalOrExpression"
    // Cmd.g:739:1: conditionalOrExpression returns [ASTExpression n] : nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* ;
    public final ASTExpression conditionalOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndXorExp = null;

        ASTExpression n1 = null;


        try {
            // Cmd.g:740:1: (nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* )
            // Cmd.g:741:5: nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )*
            {
            pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3215);
            nCndXorExp=conditionalXOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndXorExp;
            }
            // Cmd.g:742:5: (op= 'or' n1= conditionalXOrExpression )*
            loop68:
            do {
                int alt68=2;
                int LA68_0 = input.LA(1);

                if ( (LA68_0==84) ) {
                    alt68=1;
                }


                switch (alt68) {
            	case 1 :
            	    // Cmd.g:742:7: op= 'or' n1= conditionalXOrExpression
            	    {
            	    op=(Token)match(input,84,FOLLOW_84_in_conditionalOrExpression3228); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3232);
            	    n1=conditionalXOrExpression();

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
    // $ANTLR end "conditionalOrExpression"


    // $ANTLR start "conditionalXOrExpression"
    // Cmd.g:751:1: conditionalXOrExpression returns [ASTExpression n] : nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* ;
    public final ASTExpression conditionalXOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndAndExp = null;

        ASTExpression n1 = null;


        try {
            // Cmd.g:752:1: (nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* )
            // Cmd.g:753:5: nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )*
            {
            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3276);
            nCndAndExp=conditionalAndExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndAndExp;
            }
            // Cmd.g:754:5: (op= 'xor' n1= conditionalAndExpression )*
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==85) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // Cmd.g:754:7: op= 'xor' n1= conditionalAndExpression
            	    {
            	    op=(Token)match(input,85,FOLLOW_85_in_conditionalXOrExpression3289); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3293);
            	    n1=conditionalAndExpression();

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
    // $ANTLR end "conditionalXOrExpression"


    // $ANTLR start "conditionalAndExpression"
    // Cmd.g:763:1: conditionalAndExpression returns [ASTExpression n] : nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* ;
    public final ASTExpression conditionalAndExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nEqExp = null;

        ASTExpression n1 = null;


        try {
            // Cmd.g:764:1: (nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* )
            // Cmd.g:765:5: nEqExp= equalityExpression (op= 'and' n1= equalityExpression )*
            {
            pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression3337);
            nEqExp=equalityExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nEqExp;
            }
            // Cmd.g:766:5: (op= 'and' n1= equalityExpression )*
            loop70:
            do {
                int alt70=2;
                int LA70_0 = input.LA(1);

                if ( (LA70_0==86) ) {
                    alt70=1;
                }


                switch (alt70) {
            	case 1 :
            	    // Cmd.g:766:7: op= 'and' n1= equalityExpression
            	    {
            	    op=(Token)match(input,86,FOLLOW_86_in_conditionalAndExpression3350); if (state.failed) return n;
            	    pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression3354);
            	    n1=equalityExpression();

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
    // $ANTLR end "conditionalAndExpression"


    // $ANTLR start "equalityExpression"
    // Cmd.g:775:1: equalityExpression returns [ASTExpression n] : nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* ;
    public final ASTExpression equalityExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nRelExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Cmd.g:777:1: (nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* )
            // Cmd.g:778:5: nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            {
            pushFollow(FOLLOW_relationalExpression_in_equalityExpression3402);
            nRelExp=relationalExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nRelExp;
            }
            // Cmd.g:779:5: ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            loop71:
            do {
                int alt71=2;
                int LA71_0 = input.LA(1);

                if ( (LA71_0==EQUAL||LA71_0==NOT_EQUAL) ) {
                    alt71=1;
                }


                switch (alt71) {
            	case 1 :
            	    // Cmd.g:779:7: ( EQUAL | NOT_EQUAL ) n1= relationalExpression
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

            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression3431);
            	    n1=relationalExpression();

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
    // $ANTLR end "equalityExpression"


    // $ANTLR start "relationalExpression"
    // Cmd.g:789:1: relationalExpression returns [ASTExpression n] : nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* ;
    public final ASTExpression relationalExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nAddiExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Cmd.g:791:1: (nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* )
            // Cmd.g:792:5: nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            {
            pushFollow(FOLLOW_additiveExpression_in_relationalExpression3480);
            nAddiExp=additiveExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nAddiExp;
            }
            // Cmd.g:793:5: ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            loop72:
            do {
                int alt72=2;
                int LA72_0 = input.LA(1);

                if ( (LA72_0==LESS||(LA72_0>=GREATER && LA72_0<=GREATER_EQUAL)) ) {
                    alt72=1;
                }


                switch (alt72) {
            	case 1 :
            	    // Cmd.g:793:7: ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression
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

            	    pushFollow(FOLLOW_additiveExpression_in_relationalExpression3516);
            	    n1=additiveExpression();

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
    // $ANTLR end "relationalExpression"


    // $ANTLR start "additiveExpression"
    // Cmd.g:803:1: additiveExpression returns [ASTExpression n] : nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* ;
    public final ASTExpression additiveExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nMulExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Cmd.g:805:1: (nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* )
            // Cmd.g:806:5: nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            {
            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3566);
            nMulExp=multiplicativeExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nMulExp;
            }
            // Cmd.g:807:5: ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            loop73:
            do {
                int alt73=2;
                int LA73_0 = input.LA(1);

                if ( ((LA73_0>=PLUS && LA73_0<=MINUS)) ) {
                    alt73=1;
                }


                switch (alt73) {
            	case 1 :
            	    // Cmd.g:807:7: ( PLUS | MINUS ) n1= multiplicativeExpression
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

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3594);
            	    n1=multiplicativeExpression();

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
    // $ANTLR end "additiveExpression"


    // $ANTLR start "multiplicativeExpression"
    // Cmd.g:818:1: multiplicativeExpression returns [ASTExpression n] : nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* ;
    public final ASTExpression multiplicativeExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Cmd.g:820:1: (nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* )
            // Cmd.g:821:5: nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            {
            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3644);
            nUnExp=unaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nUnExp;
            }
            // Cmd.g:822:5: ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            loop74:
            do {
                int alt74=2;
                int LA74_0 = input.LA(1);

                if ( (LA74_0==STAR||LA74_0==SLASH||LA74_0==87) ) {
                    alt74=1;
                }


                switch (alt74) {
            	case 1 :
            	    // Cmd.g:822:7: ( STAR | SLASH | 'div' ) n1= unaryExpression
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

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3676);
            	    n1=unaryExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
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
    // $ANTLR end "multiplicativeExpression"


    // $ANTLR start "unaryExpression"
    // Cmd.g:834:1: unaryExpression returns [ASTExpression n] : ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression );
    public final ASTExpression unaryExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression nPosExp = null;


         Token op = null; 
        try {
            // Cmd.g:836:1: ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression )
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( ((LA75_0>=PLUS && LA75_0<=MINUS)||LA75_0==88) ) {
                alt75=1;
            }
            else if ( ((LA75_0>=IDENT && LA75_0<=LPAREN)||LA75_0==INT||(LA75_0>=REAL && LA75_0<=HASH)||(LA75_0>=90 && LA75_0<=94)||(LA75_0>=98 && LA75_0<=109)) ) {
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
                    // Cmd.g:837:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    {
                    // Cmd.g:837:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    // Cmd.g:837:9: ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression
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

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression3762);
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
                    // Cmd.g:841:7: nPosExp= postfixExpression
                    {
                    pushFollow(FOLLOW_postfixExpression_in_unaryExpression3782);
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
    // Cmd.g:849:1: postfixExpression returns [ASTExpression n] : nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* ;
    public final ASTExpression postfixExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nPrimExp = null;

        ASTExpression nPc = null;


         boolean arrow = false; 
        try {
            // Cmd.g:851:1: (nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* )
            // Cmd.g:852:5: nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            {
            pushFollow(FOLLOW_primaryExpression_in_postfixExpression3815);
            nPrimExp=primaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nPrimExp; 
            }
            // Cmd.g:853:5: ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            loop77:
            do {
                int alt77=2;
                int LA77_0 = input.LA(1);

                if ( ((LA77_0>=ARROW && LA77_0<=DOT)) ) {
                    alt77=1;
                }


                switch (alt77) {
            	case 1 :
            	    // Cmd.g:854:6: ( ARROW | DOT ) nPc= propertyCall[$n, arrow]
            	    {
            	    // Cmd.g:854:6: ( ARROW | DOT )
            	    int alt76=2;
            	    int LA76_0 = input.LA(1);

            	    if ( (LA76_0==ARROW) ) {
            	        alt76=1;
            	    }
            	    else if ( (LA76_0==DOT) ) {
            	        alt76=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 76, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt76) {
            	        case 1 :
            	            // Cmd.g:854:8: ARROW
            	            {
            	            match(input,ARROW,FOLLOW_ARROW_in_postfixExpression3833); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = true; 
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // Cmd.g:854:34: DOT
            	            {
            	            match(input,DOT,FOLLOW_DOT_in_postfixExpression3839); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = false; 
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_propertyCall_in_postfixExpression3850);
            	    nPc=propertyCall(n, arrow);

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = nPc; 
            	    }

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
    // Cmd.g:870:1: primaryExpression returns [ASTExpression n] : (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? );
    public final ASTExpression primaryExpression() throws RecognitionException {
        ASTExpression n = null;

        Token id1=null;
        ASTExpression nLit = null;

        ASTExpression nPc = null;

        ASTExpression nExp = null;

        ASTExpression nIfExp = null;


        try {
            // Cmd.g:871:1: (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? )
            int alt80=5;
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
                alt80=1;
                }
                break;
            case IDENT:
                {
                switch ( input.LA(2) ) {
                case COLON_COLON:
                    {
                    alt80=1;
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
                    alt80=2;
                    }
                    break;
                case DOT:
                    {
                    int LA80_6 = input.LA(3);

                    if ( (LA80_6==89) ) {
                        alt80=5;
                    }
                    else if ( (LA80_6==IDENT||(LA80_6>=90 && LA80_6<=93)) ) {
                        alt80=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 80, 6, input);

                        throw nvae;
                    }
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 80, 2, input);

                    throw nvae;
                }

                }
                break;
            case 90:
            case 91:
            case 92:
            case 93:
                {
                alt80=2;
                }
                break;
            case LPAREN:
                {
                alt80=3;
                }
                break;
            case 94:
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
                    // Cmd.g:872:7: nLit= literal
                    {
                    pushFollow(FOLLOW_literal_in_primaryExpression3890);
                    nLit=literal();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nLit; 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:873:7: nPc= propertyCall[null, false]
                    {
                    pushFollow(FOLLOW_propertyCall_in_primaryExpression3902);
                    nPc=propertyCall(null, false);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nPc; 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:874:7: LPAREN nExp= expression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression3913); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_primaryExpression3917);
                    nExp=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression3919); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExp; 
                    }

                    }
                    break;
                case 4 :
                    // Cmd.g:875:7: nIfExp= ifExpression
                    {
                    pushFollow(FOLLOW_ifExpression_in_primaryExpression3931);
                    nIfExp=ifExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nIfExp; 
                    }

                    }
                    break;
                case 5 :
                    // Cmd.g:877:7: id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )?
                    {
                    id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_primaryExpression3948); if (state.failed) return n;
                    match(input,DOT,FOLLOW_DOT_in_primaryExpression3950); if (state.failed) return n;
                    match(input,89,FOLLOW_89_in_primaryExpression3952); if (state.failed) return n;
                    // Cmd.g:877:36: ( LPAREN RPAREN )?
                    int alt78=2;
                    int LA78_0 = input.LA(1);

                    if ( (LA78_0==LPAREN) ) {
                        alt78=1;
                    }
                    switch (alt78) {
                        case 1 :
                            // Cmd.g:877:38: LPAREN RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression3956); if (state.failed) return n;
                            match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression3958); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTAllInstancesExpression(id1); 
                    }
                    // Cmd.g:879:7: ( AT 'pre' )?
                    int alt79=2;
                    int LA79_0 = input.LA(1);

                    if ( (LA79_0==AT) ) {
                        alt79=1;
                    }
                    switch (alt79) {
                        case 1 :
                            // Cmd.g:879:9: AT 'pre'
                            {
                            match(input,AT,FOLLOW_AT_in_primaryExpression3979); if (state.failed) return n;
                            match(input,80,FOLLOW_80_in_primaryExpression3981); if (state.failed) return n;
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
    // Cmd.g:892:1: propertyCall[ASTExpression source, boolean followsArrow] returns [ASTExpression n] : ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] );
    public final ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExpQuery = null;

        ASTExpression nExpIterate = null;

        ASTOperationExpression nExpOperation = null;

        ASTTypeArgExpression nExpType = null;


        try {
            // Cmd.g:893:1: ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] )
            int alt81=4;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA81_1 = input.LA(2);

                if ( ((( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )&&( input.LA(2) == LPAREN ))) ) {
                    alt81=1;
                }
                else if ( (true) ) {
                    alt81=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 81, 1, input);

                    throw nvae;
                }
                }
                break;
            case 90:
                {
                alt81=2;
                }
                break;
            case 91:
            case 92:
            case 93:
                {
                alt81=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 81, 0, input);

                throw nvae;
            }

            switch (alt81) {
                case 1 :
                    // Cmd.g:897:7: {...}?{...}?nExpQuery= queryExpression[source]
                    {
                    if ( !(( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) ");
                    }
                    if ( !(( input.LA(2) == LPAREN )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " input.LA(2) == LPAREN ");
                    }
                    pushFollow(FOLLOW_queryExpression_in_propertyCall4054);
                    nExpQuery=queryExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpQuery; 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:900:7: nExpIterate= iterateExpression[source]
                    {
                    pushFollow(FOLLOW_iterateExpression_in_propertyCall4067);
                    nExpIterate=iterateExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpIterate; 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:901:7: nExpOperation= operationExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_operationExpression_in_propertyCall4080);
                    nExpOperation=operationExpression(source, followsArrow);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpOperation; 
                    }

                    }
                    break;
                case 4 :
                    // Cmd.g:902:7: nExpType= typeExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_typeExpression_in_propertyCall4093);
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
    // Cmd.g:911:1: queryExpression[ASTExpression range] returns [ASTExpression n] : op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN ;
    public final ASTExpression queryExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTElemVarsDeclaration decls = null;

        ASTExpression nExp = null;


        ASTElemVarsDeclaration decl = new ASTElemVarsDeclaration(); 
        try {
            // Cmd.g:912:69: (op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN )
            // Cmd.g:913:5: op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN
            {
            op=(Token)match(input,IDENT,FOLLOW_IDENT_in_queryExpression4128); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_queryExpression4135); if (state.failed) return n;
            // Cmd.g:915:5: (decls= elemVarsDeclaration BAR )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==IDENT) ) {
                int LA82_1 = input.LA(2);

                if ( (LA82_1==COLON||LA82_1==COMMA||LA82_1==BAR) ) {
                    alt82=1;
                }
            }
            switch (alt82) {
                case 1 :
                    // Cmd.g:915:7: decls= elemVarsDeclaration BAR
                    {
                    pushFollow(FOLLOW_elemVarsDeclaration_in_queryExpression4146);
                    decls=elemVarsDeclaration();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      decl = decls;
                    }
                    match(input,BAR,FOLLOW_BAR_in_queryExpression4150); if (state.failed) return n;

                    }
                    break;

            }

            pushFollow(FOLLOW_expression_in_queryExpression4161);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_queryExpression4167); if (state.failed) return n;
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
    // Cmd.g:929:1: iterateExpression[ASTExpression range] returns [ASTExpression n] : i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN ;
    public final ASTExpression iterateExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTElemVarsDeclaration decls = null;

        ASTVariableInitialization init = null;

        ASTExpression nExp = null;


        try {
            // Cmd.g:929:65: (i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN )
            // Cmd.g:930:5: i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN
            {
            i=(Token)match(input,90,FOLLOW_90_in_iterateExpression4199); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_iterateExpression4205); if (state.failed) return n;
            pushFollow(FOLLOW_elemVarsDeclaration_in_iterateExpression4213);
            decls=elemVarsDeclaration();

            state._fsp--;
            if (state.failed) return n;
            match(input,SEMI,FOLLOW_SEMI_in_iterateExpression4215); if (state.failed) return n;
            pushFollow(FOLLOW_variableInitialization_in_iterateExpression4223);
            init=variableInitialization();

            state._fsp--;
            if (state.failed) return n;
            match(input,BAR,FOLLOW_BAR_in_iterateExpression4225); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_iterateExpression4233);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_iterateExpression4239); if (state.failed) return n;
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
    // Cmd.g:951:1: operationExpression[ASTExpression source, boolean followsArrow] returns [ASTOperationExpression n] : name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? ;
    public final ASTOperationExpression operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTOperationExpression n = null;

        Token name=null;
        Token rolename=null;
        ASTExpression e = null;


        try {
            // Cmd.g:953:1: (name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? )
            // Cmd.g:954:5: name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression4283); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTOperationExpression(name, source, followsArrow); 
            }
            // Cmd.g:957:5: ( LBRACK rolename= IDENT RBRACK )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==LBRACK) ) {
                alt83=1;
            }
            switch (alt83) {
                case 1 :
                    // Cmd.g:957:7: LBRACK rolename= IDENT RBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_operationExpression4299); if (state.failed) return n;
                    rolename=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression4303); if (state.failed) return n;
                    match(input,RBRACK,FOLLOW_RBRACK_in_operationExpression4305); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setExplicitRolename(rolename); 
                    }

                    }
                    break;

            }

            // Cmd.g:959:5: ( AT 'pre' )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==AT) ) {
                alt84=1;
            }
            switch (alt84) {
                case 1 :
                    // Cmd.g:959:7: AT 'pre'
                    {
                    match(input,AT,FOLLOW_AT_in_operationExpression4318); if (state.failed) return n;
                    match(input,80,FOLLOW_80_in_operationExpression4320); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setIsPre(); 
                    }

                    }
                    break;

            }

            // Cmd.g:960:5: ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==LPAREN) ) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    // Cmd.g:961:7: LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_operationExpression4341); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.hasParentheses(); 
                    }
                    // Cmd.g:962:7: (e= expression ( COMMA e= expression )* )?
                    int alt86=2;
                    int LA86_0 = input.LA(1);

                    if ( ((LA86_0>=IDENT && LA86_0<=LPAREN)||LA86_0==INT||(LA86_0>=PLUS && LA86_0<=MINUS)||(LA86_0>=REAL && LA86_0<=HASH)||LA86_0==55||LA86_0==88||(LA86_0>=90 && LA86_0<=94)||(LA86_0>=98 && LA86_0<=109)) ) {
                        alt86=1;
                    }
                    switch (alt86) {
                        case 1 :
                            // Cmd.g:963:7: e= expression ( COMMA e= expression )*
                            {
                            pushFollow(FOLLOW_expression_in_operationExpression4362);
                            e=expression();

                            state._fsp--;
                            if (state.failed) return n;
                            if ( state.backtracking==0 ) {
                               n.addArg(e); 
                            }
                            // Cmd.g:964:7: ( COMMA e= expression )*
                            loop85:
                            do {
                                int alt85=2;
                                int LA85_0 = input.LA(1);

                                if ( (LA85_0==COMMA) ) {
                                    alt85=1;
                                }


                                switch (alt85) {
                            	case 1 :
                            	    // Cmd.g:964:9: COMMA e= expression
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_operationExpression4374); if (state.failed) return n;
                            	    pushFollow(FOLLOW_expression_in_operationExpression4378);
                            	    e=expression();

                            	    state._fsp--;
                            	    if (state.failed) return n;
                            	    if ( state.backtracking==0 ) {
                            	       n.addArg(e); 
                            	    }

                            	    }
                            	    break;

                            	default :
                            	    break loop85;
                                }
                            } while (true);


                            }
                            break;

                    }

                    match(input,RPAREN,FOLLOW_RPAREN_in_operationExpression4398); if (state.failed) return n;

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
    // Cmd.g:976:1: typeExpression[ASTExpression source, boolean followsArrow] returns [ASTTypeArgExpression n] : ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN ;
    public final ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTTypeArgExpression n = null;

        ASTType t = null;


         Token opToken = null; 
        try {
            // Cmd.g:979:1: ( ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN )
            // Cmd.g:980:2: ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN
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

            match(input,LPAREN,FOLLOW_LPAREN_in_typeExpression4457); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_typeExpression4461);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_typeExpression4463); if (state.failed) return n;
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
    // Cmd.g:991:1: elemVarsDeclaration returns [ASTElemVarsDeclaration n] : idListRes= idList ( COLON t= type )? ;
    public final ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException {
        ASTElemVarsDeclaration n = null;

        List idListRes = null;

        ASTType t = null;


         List idList; 
        try {
            // Cmd.g:993:1: (idListRes= idList ( COLON t= type )? )
            // Cmd.g:994:5: idListRes= idList ( COLON t= type )?
            {
            pushFollow(FOLLOW_idList_in_elemVarsDeclaration4502);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            // Cmd.g:995:5: ( COLON t= type )?
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==COLON) ) {
                alt88=1;
            }
            switch (alt88) {
                case 1 :
                    // Cmd.g:995:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_elemVarsDeclaration4510); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_elemVarsDeclaration4514);
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
    // Cmd.g:1004:1: variableInitialization returns [ASTVariableInitialization n] : name= IDENT COLON t= type EQUAL e= expression ;
    public final ASTVariableInitialization variableInitialization() throws RecognitionException {
        ASTVariableInitialization n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // Cmd.g:1005:1: (name= IDENT COLON t= type EQUAL e= expression )
            // Cmd.g:1006:5: name= IDENT COLON t= type EQUAL e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableInitialization4549); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableInitialization4551); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableInitialization4555);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EQUAL,FOLLOW_EQUAL_in_variableInitialization4557); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_variableInitialization4561);
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
    // Cmd.g:1015:1: ifExpression returns [ASTExpression n] : i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' ;
    public final ASTExpression ifExpression() throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTExpression cond = null;

        ASTExpression t = null;

        ASTExpression e = null;


        try {
            // Cmd.g:1016:1: (i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' )
            // Cmd.g:1017:5: i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif'
            {
            i=(Token)match(input,94,FOLLOW_94_in_ifExpression4593); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4597);
            cond=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,95,FOLLOW_95_in_ifExpression4599); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4603);
            t=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,96,FOLLOW_96_in_ifExpression4605); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4609);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,97,FOLLOW_97_in_ifExpression4611); if (state.failed) return n;
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
    // Cmd.g:1037:1: literal returns [ASTExpression n] : (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral );
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
            // Cmd.g:1038:1: (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral )
            int alt89=12;
            switch ( input.LA(1) ) {
            case 98:
                {
                alt89=1;
                }
                break;
            case 99:
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
            case IDENT:
                {
                alt89=7;
                }
                break;
            case 100:
            case 101:
            case 102:
            case 103:
                {
                alt89=8;
                }
                break;
            case 104:
                {
                alt89=9;
                }
                break;
            case 105:
            case 106:
            case 107:
                {
                alt89=10;
                }
                break;
            case 108:
                {
                alt89=11;
                }
                break;
            case 109:
                {
                alt89=12;
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
                    // Cmd.g:1039:7: t= 'true'
                    {
                    t=(Token)match(input,98,FOLLOW_98_in_literal4650); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(true); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:1040:7: f= 'false'
                    {
                    f=(Token)match(input,99,FOLLOW_99_in_literal4664); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(false); 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:1041:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_literal4677); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTIntegerLiteral(i); 
                    }

                    }
                    break;
                case 4 :
                    // Cmd.g:1042:7: r= REAL
                    {
                    r=(Token)match(input,REAL,FOLLOW_REAL_in_literal4692); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTRealLiteral(r); 
                    }

                    }
                    break;
                case 5 :
                    // Cmd.g:1043:7: s= STRING
                    {
                    s=(Token)match(input,STRING,FOLLOW_STRING_in_literal4706); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTStringLiteral(s); 
                    }

                    }
                    break;
                case 6 :
                    // Cmd.g:1044:7: HASH enumLit= IDENT
                    {
                    match(input,HASH,FOLLOW_HASH_in_literal4716); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal4720); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumLit);  reportWarning(enumLit, "the usage of #enumerationLiteral is deprecated and will not be supported in the future, use 'Enumeration::Literal' instead");
                    }

                    }
                    break;
                case 7 :
                    // Cmd.g:1045:7: enumName= IDENT '::' enumLit= IDENT
                    {
                    enumName=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal4732); if (state.failed) return n;
                    match(input,COLON_COLON,FOLLOW_COLON_COLON_in_literal4734); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal4738); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumName, enumLit); 
                    }

                    }
                    break;
                case 8 :
                    // Cmd.g:1046:7: nColIt= collectionLiteral
                    {
                    pushFollow(FOLLOW_collectionLiteral_in_literal4750);
                    nColIt=collectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nColIt; 
                    }

                    }
                    break;
                case 9 :
                    // Cmd.g:1047:7: nEColIt= emptyCollectionLiteral
                    {
                    pushFollow(FOLLOW_emptyCollectionLiteral_in_literal4762);
                    nEColIt=emptyCollectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nEColIt; 
                    }

                    }
                    break;
                case 10 :
                    // Cmd.g:1048:7: nUndLit= undefinedLiteral
                    {
                    pushFollow(FOLLOW_undefinedLiteral_in_literal4774);
                    nUndLit=undefinedLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nUndLit; 
                    }

                    }
                    break;
                case 11 :
                    // Cmd.g:1049:7: nTupleLit= tupleLiteral
                    {
                    pushFollow(FOLLOW_tupleLiteral_in_literal4786);
                    nTupleLit=tupleLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nTupleLit; 
                    }

                    }
                    break;
                case 12 :
                    // Cmd.g:1050:7: nDateLit= dateLiteral
                    {
                    pushFollow(FOLLOW_dateLiteral_in_literal4798);
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
    // Cmd.g:1058:1: collectionLiteral returns [ASTCollectionLiteral n] : ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE ;
    public final ASTCollectionLiteral collectionLiteral() throws RecognitionException {
        ASTCollectionLiteral n = null;

        ASTCollectionItem ci = null;


         Token op = null; 
        try {
            // Cmd.g:1060:1: ( ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE )
            // Cmd.g:1061:5: ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE
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
            match(input,LBRACE,FOLLOW_LBRACE_in_collectionLiteral4865); if (state.failed) return n;
            // Cmd.g:1065:5: (ci= collectionItem ( COMMA ci= collectionItem )* )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( ((LA91_0>=IDENT && LA91_0<=LPAREN)||LA91_0==INT||(LA91_0>=PLUS && LA91_0<=MINUS)||(LA91_0>=REAL && LA91_0<=HASH)||LA91_0==55||LA91_0==88||(LA91_0>=90 && LA91_0<=94)||(LA91_0>=98 && LA91_0<=109)) ) {
                alt91=1;
            }
            switch (alt91) {
                case 1 :
                    // Cmd.g:1066:7: ci= collectionItem ( COMMA ci= collectionItem )*
                    {
                    pushFollow(FOLLOW_collectionItem_in_collectionLiteral4882);
                    ci=collectionItem();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addItem(ci); 
                    }
                    // Cmd.g:1067:7: ( COMMA ci= collectionItem )*
                    loop90:
                    do {
                        int alt90=2;
                        int LA90_0 = input.LA(1);

                        if ( (LA90_0==COMMA) ) {
                            alt90=1;
                        }


                        switch (alt90) {
                    	case 1 :
                    	    // Cmd.g:1067:9: COMMA ci= collectionItem
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_collectionLiteral4895); if (state.failed) return n;
                    	    pushFollow(FOLLOW_collectionItem_in_collectionLiteral4899);
                    	    ci=collectionItem();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addItem(ci); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop90;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RBRACE,FOLLOW_RBRACE_in_collectionLiteral4918); if (state.failed) return n;

            }

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
    // Cmd.g:1076:1: collectionItem returns [ASTCollectionItem n] : e= expression ( DOTDOT e= expression )? ;
    public final ASTCollectionItem collectionItem() throws RecognitionException {
        ASTCollectionItem n = null;

        ASTExpression e = null;


         n = new ASTCollectionItem(); 
        try {
            // Cmd.g:1078:1: (e= expression ( DOTDOT e= expression )? )
            // Cmd.g:1079:5: e= expression ( DOTDOT e= expression )?
            {
            pushFollow(FOLLOW_expression_in_collectionItem4947);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setFirst(e); 
            }
            // Cmd.g:1080:5: ( DOTDOT e= expression )?
            int alt92=2;
            int LA92_0 = input.LA(1);

            if ( (LA92_0==DOTDOT) ) {
                alt92=1;
            }
            switch (alt92) {
                case 1 :
                    // Cmd.g:1080:7: DOTDOT e= expression
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_collectionItem4958); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_collectionItem4962);
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
    // Cmd.g:1090:1: emptyCollectionLiteral returns [ASTEmptyCollectionLiteral n] : 'oclEmpty' LPAREN t= collectionType RPAREN ;
    public final ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException {
        ASTEmptyCollectionLiteral n = null;

        ASTCollectionType t = null;


        try {
            // Cmd.g:1091:1: ( 'oclEmpty' LPAREN t= collectionType RPAREN )
            // Cmd.g:1092:5: 'oclEmpty' LPAREN t= collectionType RPAREN
            {
            match(input,104,FOLLOW_104_in_emptyCollectionLiteral4991); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_emptyCollectionLiteral4993); if (state.failed) return n;
            pushFollow(FOLLOW_collectionType_in_emptyCollectionLiteral4997);
            t=collectionType();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_emptyCollectionLiteral4999); if (state.failed) return n;
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
    // Cmd.g:1103:1: undefinedLiteral returns [ASTUndefinedLiteral n] : ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' );
    public final ASTUndefinedLiteral undefinedLiteral() throws RecognitionException {
        ASTUndefinedLiteral n = null;

        ASTType t = null;


        try {
            // Cmd.g:1104:1: ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' )
            int alt93=3;
            switch ( input.LA(1) ) {
            case 105:
                {
                alt93=1;
                }
                break;
            case 106:
                {
                alt93=2;
                }
                break;
            case 107:
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
                    // Cmd.g:1105:5: 'oclUndefined' LPAREN t= type RPAREN
                    {
                    match(input,105,FOLLOW_105_in_undefinedLiteral5029); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_undefinedLiteral5031); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_undefinedLiteral5035);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_undefinedLiteral5037); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(t); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:1108:5: 'Undefined'
                    {
                    match(input,106,FOLLOW_106_in_undefinedLiteral5051); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(); 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:1111:5: 'null'
                    {
                    match(input,107,FOLLOW_107_in_undefinedLiteral5065); if (state.failed) return n;
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
    // Cmd.g:1120:1: tupleLiteral returns [ASTTupleLiteral n] : 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE ;
    public final ASTTupleLiteral tupleLiteral() throws RecognitionException {
        ASTTupleLiteral n = null;

        ASTTupleItem ti = null;


         List tiList = new ArrayList(); 
        try {
            // Cmd.g:1122:1: ( 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE )
            // Cmd.g:1123:5: 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE
            {
            match(input,108,FOLLOW_108_in_tupleLiteral5099); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_tupleLiteral5105); if (state.failed) return n;
            pushFollow(FOLLOW_tupleItem_in_tupleLiteral5113);
            ti=tupleItem();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tiList.add(ti); 
            }
            // Cmd.g:1126:5: ( COMMA ti= tupleItem )*
            loop94:
            do {
                int alt94=2;
                int LA94_0 = input.LA(1);

                if ( (LA94_0==COMMA) ) {
                    alt94=1;
                }


                switch (alt94) {
            	case 1 :
            	    // Cmd.g:1126:7: COMMA ti= tupleItem
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleLiteral5124); if (state.failed) return n;
            	    pushFollow(FOLLOW_tupleItem_in_tupleLiteral5128);
            	    ti=tupleItem();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tiList.add(ti); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop94;
                }
            } while (true);

            match(input,RBRACE,FOLLOW_RBRACE_in_tupleLiteral5139); if (state.failed) return n;
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
    // Cmd.g:1134:1: tupleItem returns [ASTTupleItem n] : name= IDENT ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) ;
    public final ASTTupleItem tupleItem() throws RecognitionException {
        ASTTupleItem n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // Cmd.g:1135:1: (name= IDENT ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) )
            // Cmd.g:1136:5: name= IDENT ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tupleItem5170); if (state.failed) return n;
            // Cmd.g:1137:5: ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==COLON) ) {
                int LA95_1 = input.LA(2);

                if ( (synpred2_Cmd()) ) {
                    alt95=1;
                }
                else if ( (true) ) {
                    alt95=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 95, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA95_0==EQUAL) ) {
                alt95=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 95, 0, input);

                throw nvae;
            }
            switch (alt95) {
                case 1 :
                    // Cmd.g:1140:7: ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression
                    {
                    match(input,COLON,FOLLOW_COLON_in_tupleItem5209); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_tupleItem5213);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,EQUAL,FOLLOW_EQUAL_in_tupleItem5215); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_tupleItem5219);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTTupleItem(name, t, e); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:1143:7: ( COLON | EQUAL ) e= expression
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

                    pushFollow(FOLLOW_expression_in_tupleItem5251);
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
    // Cmd.g:1152:1: dateLiteral returns [ASTDateLiteral n] : 'Date' LBRACE v= STRING RBRACE ;
    public final ASTDateLiteral dateLiteral() throws RecognitionException {
        ASTDateLiteral n = null;

        Token v=null;

        try {
            // Cmd.g:1153:1: ( 'Date' LBRACE v= STRING RBRACE )
            // Cmd.g:1154:5: 'Date' LBRACE v= STRING RBRACE
            {
            match(input,109,FOLLOW_109_in_dateLiteral5296); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_dateLiteral5298); if (state.failed) return n;
            v=(Token)match(input,STRING,FOLLOW_STRING_in_dateLiteral5302); if (state.failed) return n;
            match(input,RBRACE,FOLLOW_RBRACE_in_dateLiteral5304); if (state.failed) return n;
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
    // Cmd.g:1164:1: type returns [ASTType n] : (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) ;
    public final ASTType type() throws RecognitionException {
        ASTType n = null;

        ASTSimpleType nTSimple = null;

        ASTCollectionType nTCollection = null;

        ASTTupleType nTTuple = null;


         Token tok = null; 
        try {
            // Cmd.g:1166:1: ( (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) )
            // Cmd.g:1167:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of type */ 
            }
            // Cmd.g:1168:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            int alt96=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt96=1;
                }
                break;
            case 100:
            case 101:
            case 102:
            case 103:
            case 110:
                {
                alt96=2;
                }
                break;
            case 108:
                {
                alt96=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 96, 0, input);

                throw nvae;
            }

            switch (alt96) {
                case 1 :
                    // Cmd.g:1169:7: nTSimple= simpleType
                    {
                    pushFollow(FOLLOW_simpleType_in_type5354);
                    nTSimple=simpleType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTSimple; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 2 :
                    // Cmd.g:1170:7: nTCollection= collectionType
                    {
                    pushFollow(FOLLOW_collectionType_in_type5366);
                    nTCollection=collectionType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTCollection; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 3 :
                    // Cmd.g:1171:7: nTTuple= tupleType
                    {
                    pushFollow(FOLLOW_tupleType_in_type5378);
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
    // Cmd.g:1176:1: typeOnly returns [ASTType n] : nT= type EOF ;
    public final ASTType typeOnly() throws RecognitionException {
        ASTType n = null;

        ASTType nT = null;


        try {
            // Cmd.g:1177:1: (nT= type EOF )
            // Cmd.g:1178:5: nT= type EOF
            {
            pushFollow(FOLLOW_type_in_typeOnly5410);
            nT=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_typeOnly5412); if (state.failed) return n;
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
    // Cmd.g:1188:1: simpleType returns [ASTSimpleType n] : name= IDENT ;
    public final ASTSimpleType simpleType() throws RecognitionException {
        ASTSimpleType n = null;

        Token name=null;

        try {
            // Cmd.g:1189:1: (name= IDENT )
            // Cmd.g:1190:5: name= IDENT
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_simpleType5440); if (state.failed) return n;
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
    // Cmd.g:1198:1: collectionType returns [ASTCollectionType n] : ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN ;
    public final ASTCollectionType collectionType() throws RecognitionException {
        ASTCollectionType n = null;

        ASTType elemType = null;


         Token op = null; 
        try {
            // Cmd.g:1200:1: ( ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN )
            // Cmd.g:1201:5: ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN
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

            match(input,LPAREN,FOLLOW_LPAREN_in_collectionType5505); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_collectionType5509);
            elemType=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_collectionType5511); if (state.failed) return n;
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
    // Cmd.g:1211:1: tupleType returns [ASTTupleType n] : 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN ;
    public final ASTTupleType tupleType() throws RecognitionException {
        ASTTupleType n = null;

        ASTTuplePart tp = null;


         List tpList = new ArrayList(); 
        try {
            // Cmd.g:1213:1: ( 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN )
            // Cmd.g:1214:5: 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN
            {
            match(input,108,FOLLOW_108_in_tupleType5545); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_tupleType5547); if (state.failed) return n;
            pushFollow(FOLLOW_tuplePart_in_tupleType5556);
            tp=tuplePart();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tpList.add(tp); 
            }
            // Cmd.g:1216:5: ( COMMA tp= tuplePart )*
            loop97:
            do {
                int alt97=2;
                int LA97_0 = input.LA(1);

                if ( (LA97_0==COMMA) ) {
                    alt97=1;
                }


                switch (alt97) {
            	case 1 :
            	    // Cmd.g:1216:7: COMMA tp= tuplePart
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleType5567); if (state.failed) return n;
            	    pushFollow(FOLLOW_tuplePart_in_tupleType5571);
            	    tp=tuplePart();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tpList.add(tp); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop97;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_tupleType5583); if (state.failed) return n;
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
    // Cmd.g:1225:1: tuplePart returns [ASTTuplePart n] : name= IDENT COLON t= type ;
    public final ASTTuplePart tuplePart() throws RecognitionException {
        ASTTuplePart n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Cmd.g:1226:1: (name= IDENT COLON t= type )
            // Cmd.g:1227:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tuplePart5615); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_tuplePart5617); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_tuplePart5621);
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
        // Cmd.g:254:15: ( expression )
        // Cmd.g:254:16: expression
        {
        pushFollow(FOLLOW_expression_in_synpred1_Cmd855);
        expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_Cmd

    // $ANTLR start synpred2_Cmd
    public final void synpred2_Cmd_fragment() throws RecognitionException {   
        // Cmd.g:1140:7: ( COLON IDENT EQUAL )
        // Cmd.g:1140:8: COLON IDENT EQUAL
        {
        match(input,COLON,FOLLOW_COLON_in_synpred2_Cmd5200); if (state.failed) return ;
        match(input,IDENT,FOLLOW_IDENT_in_synpred2_Cmd5202); if (state.failed) return ;
        match(input,EQUAL,FOLLOW_EQUAL_in_synpred2_Cmd5204); if (state.failed) return ;

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


    protected DFA4 dfa4 = new DFA4(this);
    protected DFA10 dfa10 = new DFA10(this);
    static final String DFA4_eotS =
        "\22\uffff";
    static final String DFA4_eofS =
        "\20\uffff\1\17\1\uffff";
    static final String DFA4_minS =
        "\1\54\1\7\13\uffff\1\5\1\7\1\uffff\1\4\1\uffff";
    static final String DFA4_maxS =
        "\1\74\1\7\13\uffff\1\12\1\7\1\uffff\1\74\1\uffff";
    static final String DFA4_acceptS =
        "\2\uffff\1\2\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\2\uffff"+
        "\1\1\1\uffff\1\3";
    static final String DFA4_specialS =
        "\22\uffff}>";
    static final String[] DFA4_transitionS = {
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

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "115:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= showCmd | nC= hideCmd | nC= cropCmd )";
        }
    }
    static final String DFA10_eotS =
        "\42\uffff";
    static final String DFA10_eofS =
        "\1\25\41\uffff";
    static final String DFA10_minS =
        "\1\4\1\0\40\uffff";
    static final String DFA10_maxS =
        "\1\155\1\0\40\uffff";
    static final String DFA10_acceptS =
        "\2\uffff\23\1\1\2\14\uffff";
    static final String DFA10_specialS =
        "\1\0\1\1\40\uffff}>";
    static final String[] DFA10_transitionS = {
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

    static final short[] DFA10_eot = DFA.unpackEncodedString(DFA10_eotS);
    static final short[] DFA10_eof = DFA.unpackEncodedString(DFA10_eofS);
    static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars(DFA10_minS);
    static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars(DFA10_maxS);
    static final short[] DFA10_accept = DFA.unpackEncodedString(DFA10_acceptS);
    static final short[] DFA10_special = DFA.unpackEncodedString(DFA10_specialS);
    static final short[][] DFA10_transition;

    static {
        int numStates = DFA10_transitionS.length;
        DFA10_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA10_transition[i] = DFA.unpackEncodedString(DFA10_transitionS[i]);
        }
    }

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = DFA10_eot;
            this.eof = DFA10_eof;
            this.min = DFA10_min;
            this.max = DFA10_max;
            this.accept = DFA10_accept;
            this.special = DFA10_special;
            this.transition = DFA10_transition;
        }
        public String getDescription() {
            return "254:14: ( ( expression )=>e= expression | )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA10_0 = input.LA(1);

                         
                        int index10_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA10_0==55) ) {s = 1;}

                        else if ( ((LA10_0>=PLUS && LA10_0<=MINUS)||LA10_0==88) && (synpred1_Cmd())) {s = 2;}

                        else if ( (LA10_0==98) && (synpred1_Cmd())) {s = 3;}

                        else if ( (LA10_0==99) && (synpred1_Cmd())) {s = 4;}

                        else if ( (LA10_0==INT) && (synpred1_Cmd())) {s = 5;}

                        else if ( (LA10_0==REAL) && (synpred1_Cmd())) {s = 6;}

                        else if ( (LA10_0==STRING) && (synpred1_Cmd())) {s = 7;}

                        else if ( (LA10_0==HASH) && (synpred1_Cmd())) {s = 8;}

                        else if ( (LA10_0==IDENT) && (synpred1_Cmd())) {s = 9;}

                        else if ( ((LA10_0>=100 && LA10_0<=103)) && (synpred1_Cmd())) {s = 10;}

                        else if ( (LA10_0==104) && (synpred1_Cmd())) {s = 11;}

                        else if ( (LA10_0==105) && (synpred1_Cmd())) {s = 12;}

                        else if ( (LA10_0==106) && (synpred1_Cmd())) {s = 13;}

                        else if ( (LA10_0==107) && (synpred1_Cmd())) {s = 14;}

                        else if ( (LA10_0==108) && (synpred1_Cmd())) {s = 15;}

                        else if ( (LA10_0==109) && (synpred1_Cmd())) {s = 16;}

                        else if ( (LA10_0==90) && (synpred1_Cmd())) {s = 17;}

                        else if ( ((LA10_0>=91 && LA10_0<=93)) && (synpred1_Cmd())) {s = 18;}

                        else if ( (LA10_0==LPAREN) && (synpred1_Cmd())) {s = 19;}

                        else if ( (LA10_0==94) && (synpred1_Cmd())) {s = 20;}

                        else if ( (LA10_0==EOF||LA10_0==SEMI||(LA10_0>=44 && LA10_0<=45)||(LA10_0>=47 && LA10_0<=48)||LA10_0==50||(LA10_0>=52 && LA10_0<=54)||LA10_0==56||(LA10_0>=59 && LA10_0<=60)) ) {s = 21;}

                         
                        input.seek(index10_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA10_1 = input.LA(1);

                         
                        int index10_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_Cmd()) ) {s = 20;}

                        else if ( (true) ) {s = 21;}

                         
                        input.seek(index10_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 10, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_cmd_in_cmdList63 = new BitSet(new long[]{0x19F5B00000000000L});
    public static final BitSet FOLLOW_cmd_in_cmdList75 = new BitSet(new long[]{0x19F5B00000000000L});
    public static final BitSet FOLLOW_EOF_in_cmdList86 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cmd_in_embeddedCmdList115 = new BitSet(new long[]{0x19F5B00000000002L});
    public static final BitSet FOLLOW_cmdStmt_in_cmd148 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_SEMI_in_cmd153 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createCmd_in_cmdStmt184 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createAssignCmd_in_cmdStmt196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createInsertCmd_in_cmdStmt209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_destroyCmd_in_cmdStmt221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_insertCmd_in_cmdStmt233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_deleteCmd_in_cmdStmt245 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_setCmd_in_cmdStmt257 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_opEnterCmd_in_cmdStmt269 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_opExitCmd_in_cmdStmt281 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_letCmd_in_cmdStmt293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_showCmd_in_cmdStmt305 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hideCmd_in_cmdStmt317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cropCmd_in_cmdStmt329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_createCmd358 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_createCmd362 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_createCmd369 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_simpleType_in_createCmd373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_createAssignCmd402 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_createAssignCmd406 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_createAssignCmd408 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_createAssignCmd410 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_simpleType_in_createAssignCmd414 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_createInsertCmd434 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_createInsertCmd438 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_createInsertCmd440 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_createInsertCmd444 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_createInsertCmd450 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_createInsertCmd452 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_createInsertCmd456 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_createInsertCmd458 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_destroyCmd494 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_destroyCmd498 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COMMA_in_destroyCmd520 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_destroyCmd524 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_48_in_insertCmd563 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_insertCmd565 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_insertCmd574 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_COMMA_in_insertCmd578 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_insertCmd586 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_insertCmd592 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_insertCmd596 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_insertCmd608 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_insertCmd610 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_insertCmd614 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_deleteCmd649 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_deleteCmd651 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_deleteCmd659 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_COMMA_in_deleteCmd663 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_deleteCmd671 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_deleteCmd677 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_deleteCmd681 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_deleteCmd692 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_deleteCmd694 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_deleteCmd698 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_setCmd728 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_setCmd732 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_setCmd734 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_setCmd738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_opEnterCmd772 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_opEnterCmd781 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_opEnterCmd785 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_opEnterCmd793 = new BitSet(new long[]{0x0080000E0C080380L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_opEnterCmd803 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_opEnterCmd809 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_opEnterCmd813 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_opEnterCmd827 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_54_in_opExitCmd851 = new BitSet(new long[]{0x0080000E0C080182L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_opExitCmd861 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_letCmd894 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_letCmd898 = new BitSet(new long[]{0x0000000000000820L});
    public static final BitSet FOLLOW_COLON_in_letCmd902 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_letCmd906 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_letCmd911 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_letCmd915 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_hideCmd942 = new BitSet(new long[]{0x0600000000000080L});
    public static final BitSet FOLLOW_57_in_hideCmd951 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_hideCmd964 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_COLON_in_hideCmd967 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_hideCmd973 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_hideCmd984 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_hideCmd986 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_hideCmd992 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_hideCmd994 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_hideCmd996 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_hideCmd1000 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_showCmd1025 = new BitSet(new long[]{0x0600000000000080L});
    public static final BitSet FOLLOW_57_in_showCmd1034 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_showCmd1047 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_COLON_in_showCmd1050 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_showCmd1056 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_showCmd1067 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_showCmd1069 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_showCmd1075 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_showCmd1077 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_showCmd1079 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_showCmd1083 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_cropCmd1108 = new BitSet(new long[]{0x0400000000000082L});
    public static final BitSet FOLLOW_idList_in_cropCmd1121 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_COLON_in_cropCmd1124 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_cropCmd1130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_cropCmd1141 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_cropCmd1143 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_cropCmd1149 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_cropCmd1151 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_cropCmd1153 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_cropCmd1157 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_model1186 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_model1190 = new BitSet(new long[]{0xC000000000000080L,0x00000000000001E3L});
    public static final BitSet FOLLOW_enumTypeDefinition_in_model1202 = new BitSet(new long[]{0xC000000000000080L,0x00000000000001E3L});
    public static final BitSet FOLLOW_generalClassDefinition_in_model1219 = new BitSet(new long[]{0x4000000000000080L,0x00000000000001E3L});
    public static final BitSet FOLLOW_associationDefinition_in_model1236 = new BitSet(new long[]{0x4000000000000080L,0x00000000000001E3L});
    public static final BitSet FOLLOW_62_in_model1252 = new BitSet(new long[]{0x4000000000000080L,0x00000000000021E3L});
    public static final BitSet FOLLOW_invariant_in_model1270 = new BitSet(new long[]{0x4000000000000080L,0x00000000000021E3L});
    public static final BitSet FOLLOW_prePost_in_model1291 = new BitSet(new long[]{0x4000000000000080L,0x00000000000021E3L});
    public static final BitSet FOLLOW_EOF_in_model1332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_enumTypeDefinition1359 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_enumTypeDefinition1363 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LBRACE_in_enumTypeDefinition1365 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_enumTypeDefinition1369 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RBRACE_in_enumTypeDefinition1371 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_SEMI_in_enumTypeDefinition1375 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_64_in_generalClassDefinition1414 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000063L});
    public static final BitSet FOLLOW_classDefinition_in_generalClassDefinition1432 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_associationClassDefinition_in_generalClassDefinition1452 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_classDefinition1491 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_classDefinition1495 = new BitSet(new long[]{0x4000000000004000L,0x000000000000001CL});
    public static final BitSet FOLLOW_LESS_in_classDefinition1505 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_classDefinition1509 = new BitSet(new long[]{0x4000000000000000L,0x000000000000001CL});
    public static final BitSet FOLLOW_66_in_classDefinition1522 = new BitSet(new long[]{0x4000000000000080L,0x0000000000000018L});
    public static final BitSet FOLLOW_attributeDefinition_in_classDefinition1535 = new BitSet(new long[]{0x4000000000000080L,0x0000000000000018L});
    public static final BitSet FOLLOW_67_in_classDefinition1556 = new BitSet(new long[]{0x4000000000000080L,0x0000000000000010L});
    public static final BitSet FOLLOW_operationDefinition_in_classDefinition1569 = new BitSet(new long[]{0x4000000000000080L,0x0000000000000010L});
    public static final BitSet FOLLOW_62_in_classDefinition1590 = new BitSet(new long[]{0x0000000000000000L,0x000000000000C010L});
    public static final BitSet FOLLOW_invariantClause_in_classDefinition1610 = new BitSet(new long[]{0x0000000000000000L,0x000000000000C010L});
    public static final BitSet FOLLOW_68_in_classDefinition1634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_associationClassDefinition1667 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_associationClassDefinition1693 = new BitSet(new long[]{0x0000400000004000L});
    public static final BitSet FOLLOW_LESS_in_associationClassDefinition1703 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_idList_in_associationClassDefinition1707 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_associationClassDefinition1718 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_associationEnd_in_associationClassDefinition1726 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_associationEnd_in_associationClassDefinition1738 = new BitSet(new long[]{0x4000000000000080L,0x000000000000019CL});
    public static final BitSet FOLLOW_66_in_associationClassDefinition1751 = new BitSet(new long[]{0x4000000000000080L,0x0000000000000198L});
    public static final BitSet FOLLOW_attributeDefinition_in_associationClassDefinition1764 = new BitSet(new long[]{0x4000000000000080L,0x0000000000000198L});
    public static final BitSet FOLLOW_67_in_associationClassDefinition1785 = new BitSet(new long[]{0x4000000000000080L,0x0000000000000190L});
    public static final BitSet FOLLOW_operationDefinition_in_associationClassDefinition1798 = new BitSet(new long[]{0x4000000000000080L,0x0000000000000190L});
    public static final BitSet FOLLOW_62_in_associationClassDefinition1819 = new BitSet(new long[]{0x0000000000000000L,0x000000000000C190L});
    public static final BitSet FOLLOW_invariantClause_in_associationClassDefinition1839 = new BitSet(new long[]{0x0000000000000000L,0x000000000000C190L});
    public static final BitSet FOLLOW_set_in_associationClassDefinition1873 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_associationClassDefinition1902 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_attributeDefinition1931 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_attributeDefinition1933 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_attributeDefinition1937 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_SEMI_in_attributeDefinition1941 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationDefinition1979 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_paramList_in_operationDefinition1987 = new BitSet(new long[]{0x0000000000000832L,0x0000000000030000L});
    public static final BitSet FOLLOW_COLON_in_operationDefinition1995 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_operationDefinition1999 = new BitSet(new long[]{0x0000000000000812L,0x0000000000030000L});
    public static final BitSet FOLLOW_EQUAL_in_operationDefinition2027 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_operationDefinition2031 = new BitSet(new long[]{0x0000000000000012L,0x0000000000030000L});
    public static final BitSet FOLLOW_EQUAL_in_operationDefinition2053 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_operationDefinition2055 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_SCRIPTBODY_in_operationDefinition2059 = new BitSet(new long[]{0x0000000000000012L,0x0000000000030000L});
    public static final BitSet FOLLOW_prePostClause_in_operationDefinition2090 = new BitSet(new long[]{0x0000000000000012L,0x0000000000030000L});
    public static final BitSet FOLLOW_SEMI_in_operationDefinition2103 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_keyAssociation_in_associationDefinition2141 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_associationDefinition2145 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_72_in_associationDefinition2149 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_associationDefinition2164 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_associationDefinition2172 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_associationEnd_in_associationDefinition2180 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_associationEnd_in_associationDefinition2192 = new BitSet(new long[]{0x0000000000000080L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_associationDefinition2203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd2229 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_LBRACK_in_associationEnd2231 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_multiplicity_in_associationEnd2235 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RBRACK_in_associationEnd2237 = new BitSet(new long[]{0x0000000000000092L,0x0000000000001C00L});
    public static final BitSet FOLLOW_keyRole_in_associationEnd2248 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd2252 = new BitSet(new long[]{0x0000000000000092L,0x0000000000001C00L});
    public static final BitSet FOLLOW_74_in_associationEnd2273 = new BitSet(new long[]{0x0000000000000092L,0x0000000000001C00L});
    public static final BitSet FOLLOW_75_in_associationEnd2285 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd2289 = new BitSet(new long[]{0x0000000000000092L,0x0000000000001C00L});
    public static final BitSet FOLLOW_keyUnion_in_associationEnd2301 = new BitSet(new long[]{0x0000000000000092L,0x0000000000001C00L});
    public static final BitSet FOLLOW_76_in_associationEnd2313 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd2317 = new BitSet(new long[]{0x0000000000000092L,0x0000000000001C00L});
    public static final BitSet FOLLOW_SEMI_in_associationEnd2334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity2369 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COMMA_in_multiplicity2379 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity2383 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange2412 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_DOTDOT_in_multiplicityRange2422 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange2426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_multiplicitySpec2460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_multiplicitySpec2470 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_invariant2511 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_invariant2521 = new BitSet(new long[]{0x0000000000000420L});
    public static final BitSet FOLLOW_COMMA_in_invariant2534 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_invariant2538 = new BitSet(new long[]{0x0000000000000420L});
    public static final BitSet FOLLOW_COLON_in_invariant2546 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_simpleType_in_invariant2558 = new BitSet(new long[]{0x0000000000000002L,0x000000000000C000L});
    public static final BitSet FOLLOW_invariantClause_in_invariant2570 = new BitSet(new long[]{0x0000000000000002L,0x000000000000C000L});
    public static final BitSet FOLLOW_78_in_invariantClause2601 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_IDENT_in_invariantClause2607 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_invariantClause2612 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_invariantClause2616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_invariantClause2626 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_invariantClause2628 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_IDENT_in_invariantClause2634 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_invariantClause2639 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_invariantClause2643 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_prePost2669 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_prePost2673 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_COLON_COLON_in_prePost2675 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_prePost2679 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_paramList_in_prePost2683 = new BitSet(new long[]{0x0000000000000020L,0x0000000000030000L});
    public static final BitSet FOLLOW_COLON_in_prePost2687 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_prePost2691 = new BitSet(new long[]{0x0000000000000000L,0x0000000000030000L});
    public static final BitSet FOLLOW_prePostClause_in_prePost2710 = new BitSet(new long[]{0x0000000000000002L,0x0000000000030000L});
    public static final BitSet FOLLOW_set_in_prePostClause2749 = new BitSet(new long[]{0x00000000000000A0L});
    public static final BitSet FOLLOW_IDENT_in_prePostClause2764 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_prePostClause2769 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_prePostClause2773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_keyUnion2795 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_keyAssociation2809 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_keyRole2823 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_expressionOnly2853 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_expressionOnly2855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_expression2903 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_expression2907 = new BitSet(new long[]{0x0000000000000820L});
    public static final BitSet FOLLOW_COLON_in_expression2911 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_expression2915 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_expression2920 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_expression2924 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_expression2926 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_conditionalImpliesExpression_in_expression2951 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_paramList2984 = new BitSet(new long[]{0x0000000000000280L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList3001 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_paramList3013 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList3017 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_paramList3037 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_idList3066 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COMMA_in_idList3076 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_idList3080 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_IDENT_in_variableDeclaration3111 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_variableDeclaration3113 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_variableDeclaration3117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3153 = new BitSet(new long[]{0x0000000000000002L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_conditionalImpliesExpression3166 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression3170 = new BitSet(new long[]{0x0000000000000002L,0x0000000000080000L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3215 = new BitSet(new long[]{0x0000000000000002L,0x0000000000100000L});
    public static final BitSet FOLLOW_84_in_conditionalOrExpression3228 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression3232 = new BitSet(new long[]{0x0000000000000002L,0x0000000000100000L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3276 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_conditionalXOrExpression3289 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression3293 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression3337 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_conditionalAndExpression3350 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression3354 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression3402 = new BitSet(new long[]{0x0000000000400802L});
    public static final BitSet FOLLOW_set_in_equalityExpression3421 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression3431 = new BitSet(new long[]{0x0000000000400802L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression3480 = new BitSet(new long[]{0x0000000003804002L});
    public static final BitSet FOLLOW_set_in_relationalExpression3498 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression3516 = new BitSet(new long[]{0x0000000003804002L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3566 = new BitSet(new long[]{0x000000000C000002L});
    public static final BitSet FOLLOW_set_in_additiveExpression3584 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3594 = new BitSet(new long[]{0x000000000C000002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3644 = new BitSet(new long[]{0x0000000010100002L,0x0000000000800000L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression3662 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3676 = new BitSet(new long[]{0x0000000010100002L,0x0000000000800000L});
    public static final BitSet FOLLOW_set_in_unaryExpression3738 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression3762 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpression_in_unaryExpression3782 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_postfixExpression3815 = new BitSet(new long[]{0x0000000060000002L});
    public static final BitSet FOLLOW_ARROW_in_postfixExpression3833 = new BitSet(new long[]{0x0000000000000080L,0x000000003C000000L});
    public static final BitSet FOLLOW_DOT_in_postfixExpression3839 = new BitSet(new long[]{0x0000000000000080L,0x000000003C000000L});
    public static final BitSet FOLLOW_propertyCall_in_postfixExpression3850 = new BitSet(new long[]{0x0000000060000002L});
    public static final BitSet FOLLOW_literal_in_primaryExpression3890 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyCall_in_primaryExpression3902 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression3913 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_primaryExpression3917 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression3919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifExpression_in_primaryExpression3931 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_primaryExpression3948 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_DOT_in_primaryExpression3950 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_primaryExpression3952 = new BitSet(new long[]{0x0000000080000102L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression3956 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression3958 = new BitSet(new long[]{0x0000000080000002L});
    public static final BitSet FOLLOW_AT_in_primaryExpression3979 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_primaryExpression3981 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_queryExpression_in_propertyCall4054 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterateExpression_in_propertyCall4067 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationExpression_in_propertyCall4080 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeExpression_in_propertyCall4093 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_queryExpression4128 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_queryExpression4135 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_queryExpression4146 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_BAR_in_queryExpression4150 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_queryExpression4161 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_queryExpression4167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_iterateExpression4199 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_iterateExpression4205 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_iterateExpression4213 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_iterateExpression4215 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_variableInitialization_in_iterateExpression4223 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_BAR_in_iterateExpression4225 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_iterateExpression4233 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_iterateExpression4239 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression4283 = new BitSet(new long[]{0x0000000080010102L});
    public static final BitSet FOLLOW_LBRACK_in_operationExpression4299 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression4303 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_RBRACK_in_operationExpression4305 = new BitSet(new long[]{0x0000000080000102L});
    public static final BitSet FOLLOW_AT_in_operationExpression4318 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_operationExpression4320 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_LPAREN_in_operationExpression4341 = new BitSet(new long[]{0x0080000E0C080380L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_operationExpression4362 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_operationExpression4374 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_operationExpression4378 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_operationExpression4398 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_typeExpression4441 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_typeExpression4457 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_typeExpression4461 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_typeExpression4463 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_elemVarsDeclaration4502 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_COLON_in_elemVarsDeclaration4510 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_elemVarsDeclaration4514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableInitialization4549 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_variableInitialization4551 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_variableInitialization4555 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_variableInitialization4557 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_variableInitialization4561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_ifExpression4593 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_ifExpression4597 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_95_in_ifExpression4599 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_ifExpression4603 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_96_in_ifExpression4605 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_ifExpression4609 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_97_in_ifExpression4611 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_literal4650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_literal4664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_literal4677 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REAL_in_literal4692 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal4706 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_literal4716 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_literal4720 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_literal4732 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_COLON_COLON_in_literal4734 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_literal4738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionLiteral_in_literal4750 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyCollectionLiteral_in_literal4762 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedLiteral_in_literal4774 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleLiteral_in_literal4786 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dateLiteral_in_literal4798 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionLiteral4836 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LBRACE_in_collectionLiteral4865 = new BitSet(new long[]{0x0080000E0C082180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral4882 = new BitSet(new long[]{0x0000000000002400L});
    public static final BitSet FOLLOW_COMMA_in_collectionLiteral4895 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral4899 = new BitSet(new long[]{0x0000000000002400L});
    public static final BitSet FOLLOW_RBRACE_in_collectionLiteral4918 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_collectionItem4947 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_DOTDOT_in_collectionItem4958 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_collectionItem4962 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_104_in_emptyCollectionLiteral4991 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_emptyCollectionLiteral4993 = new BitSet(new long[]{0x0000000000000000L,0x000040F000000000L});
    public static final BitSet FOLLOW_collectionType_in_emptyCollectionLiteral4997 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_emptyCollectionLiteral4999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_105_in_undefinedLiteral5029 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_undefinedLiteral5031 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_undefinedLiteral5035 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_undefinedLiteral5037 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_106_in_undefinedLiteral5051 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_107_in_undefinedLiteral5065 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_108_in_tupleLiteral5099 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LBRACE_in_tupleLiteral5105 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral5113 = new BitSet(new long[]{0x0000000000002400L});
    public static final BitSet FOLLOW_COMMA_in_tupleLiteral5124 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral5128 = new BitSet(new long[]{0x0000000000002400L});
    public static final BitSet FOLLOW_RBRACE_in_tupleLiteral5139 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tupleItem5170 = new BitSet(new long[]{0x0000000000000820L});
    public static final BitSet FOLLOW_COLON_in_tupleItem5209 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_tupleItem5213 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_tupleItem5215 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_tupleItem5219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_tupleItem5241 = new BitSet(new long[]{0x0080000E0C080180L,0x00003FFC7D000000L});
    public static final BitSet FOLLOW_expression_in_tupleItem5251 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_109_in_dateLiteral5296 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_LBRACE_in_dateLiteral5298 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_STRING_in_dateLiteral5302 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_RBRACE_in_dateLiteral5304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleType_in_type5354 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_type5366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleType_in_type5378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeOnly5410 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_typeOnly5412 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_simpleType5440 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionType5478 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_collectionType5505 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_collectionType5509 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_collectionType5511 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_108_in_tupleType5545 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_tupleType5547 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType5556 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COMMA_in_tupleType5567 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType5571 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_RPAREN_in_tupleType5583 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tuplePart5615 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_tuplePart5617 = new BitSet(new long[]{0x0000000000000080L,0x000050F000000000L});
    public static final BitSet FOLLOW_type_in_tuplePart5621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_synpred1_Cmd855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_synpred2_Cmd5200 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_synpred2_Cmd5202 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_synpred2_Cmd5204 = new BitSet(new long[]{0x0000000000000002L});

}