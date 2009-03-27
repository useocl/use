// $ANTLR 3.1b1 GCmd.g 2009-03-27 15:04:21
 
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

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import org.tzi.use.parser.ParseErrorHandler;
import org.tzi.use.parser.ocl.*;
import org.tzi.use.parser.use.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class GCmdParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LITERAL_oclAsType", "LITERAL_oclIsKindOf", "LITERAL_oclIsTypeOf", "LPAREN", "COMMA", "RPAREN", "IDENT", "COLON", "EQUAL", "NOT_EQUAL", "LESS", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "PLUS", "MINUS", "STAR", "SLASH", "ARROW", "DOT", "AT", "BAR", "SEMI", "LBRACK", "RBRACK", "INT", "REAL", "STRING", "HASH", "LBRACE", "RBRACE", "DOTDOT", "NEWLINE", "WS", "SL_COMMENT", "ML_COMMENT", "COLON_COLON", "COLON_EQUAL", "RANGE_OR_INT", "ESC", "HEX_DIGIT", "VOCAB", "'let'", "'in'", "'implies'", "'or'", "'xor'", "'and'", "'div'", "'not'", "'allInstances'", "'pre'", "'iterate'", "'if'", "'then'", "'else'", "'endif'", "'true'", "'false'", "'Set'", "'Sequence'", "'Bag'", "'oclEmpty'", "'oclUndefined'", "'Undefined'", "'null'", "'Tuple'", "'Collection'", "'create'", "'assign'", "'between'", "'destroy'", "'insert'", "'into'", "'delete'", "'from'", "'set'", "'openter'", "'opexit'", "'execute'"
    };
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int LITERAL_oclAsType=4;
    public static final int LBRACK=27;
    public static final int STAR=20;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int ESC=43;
    public static final int LBRACE=33;
    public static final int DOTDOT=35;
    public static final int T__61=61;
    public static final int T__60=60;
    public static final int EOF=-1;
    public static final int LPAREN=7;
    public static final int AT=24;
    public static final int T__55=55;
    public static final int ML_COMMENT=39;
    public static final int T__56=56;
    public static final int RPAREN=9;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int COLON_EQUAL=41;
    public static final int SLASH=21;
    public static final int GREATER=15;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int NOT_EQUAL=13;
    public static final int COMMA=8;
    public static final int LITERAL_oclIsTypeOf=6;
    public static final int T__59=59;
    public static final int EQUAL=12;
    public static final int LESS=14;
    public static final int IDENT=10;
    public static final int PLUS=18;
    public static final int RANGE_OR_INT=42;
    public static final int DOT=23;
    public static final int T__50=50;
    public static final int RBRACK=28;
    public static final int T__46=46;
    public static final int T__80=80;
    public static final int T__47=47;
    public static final int T__81=81;
    public static final int RBRACE=34;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int LITERAL_oclIsKindOf=5;
    public static final int HASH=32;
    public static final int HEX_DIGIT=44;
    public static final int COLON_COLON=40;
    public static final int INT=29;
    public static final int MINUS=19;
    public static final int SEMI=26;
    public static final int COLON=11;
    public static final int REAL=30;
    public static final int WS=37;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int NEWLINE=36;
    public static final int T__70=70;
    public static final int SL_COMMENT=38;
    public static final int VOCAB=45;
    public static final int ARROW=22;
    public static final int LESS_EQUAL=16;
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int T__74=74;
    public static final int GREATER_EQUAL=17;
    public static final int T__73=73;
    public static final int BAR=25;
    public static final int T__79=79;
    public static final int STRING=31;
    public static final int T__78=78;
    public static final int T__77=77;

    // delegates
    public GCmd_GOCLBase gGOCLBase;
    // delegators


        public GCmdParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public GCmdParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            gGOCLBase = new GCmd_GOCLBase(input, state, this);
        }
        

    public String[] getTokenNames() { return GCmdParser.tokenNames; }
    public String getGrammarFileName() { return "GCmd.g"; }

    
    	public void init(ParseErrorHandler handler) {
            fParseErrorHandler = handler;
    		this.gGOCLBase.init(handler);
        }
    
        /* Overridden methods. */
    	private ParseErrorHandler fParseErrorHandler;
        
        public void emitErrorMessage(String msg) {
           	fParseErrorHandler.reportError(msg);
    	}



    // $ANTLR start cmdList
    // GCmd.g:93:1: cmdList returns [ASTCmdList cmdList] : c= cmd (c= cmd )* EOF ;
    public final ASTCmdList cmdList() throws RecognitionException {
        ASTCmdList cmdList = null;

        ASTCmd c = null;


         cmdList = new ASTCmdList(); 
        try {
            // GCmd.g:95:1: (c= cmd (c= cmd )* EOF )
            // GCmd.g:96:5: c= cmd (c= cmd )* EOF
            {
            pushFollow(FOLLOW_cmd_in_cmdList67);
            c=cmd();

            state._fsp--;
            if (state.failed) return cmdList;
            if ( state.backtracking==0 ) {
               cmdList.add(c); 
            }
            // GCmd.g:97:5: (c= cmd )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==46||(LA1_0>=72 && LA1_0<=73)||(LA1_0>=75 && LA1_0<=76)||LA1_0==78||(LA1_0>=80 && LA1_0<=83)) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // GCmd.g:97:7: c= cmd
            	    {
            	    pushFollow(FOLLOW_cmd_in_cmdList79);
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

            match(input,EOF,FOLLOW_EOF_in_cmdList90); if (state.failed) return cmdList;

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
    // $ANTLR end cmdList


    // $ANTLR start cmd
    // GCmd.g:105:1: cmd returns [ASTCmd n] : stmt= cmdStmt ( SEMI )? ;
    public final ASTCmd cmd() throws RecognitionException {
        ASTCmd n = null;

        ASTCmd stmt = null;


        try {
            // GCmd.g:106:1: (stmt= cmdStmt ( SEMI )? )
            // GCmd.g:107:5: stmt= cmdStmt ( SEMI )?
            {
            pushFollow(FOLLOW_cmdStmt_in_cmd116);
            stmt=cmdStmt();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = stmt; 
            }
            // GCmd.g:107:35: ( SEMI )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==SEMI) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // GCmd.g:107:37: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_cmd121); if (state.failed) return n;

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
    // $ANTLR end cmd


    // $ANTLR start cmdStmt
    // GCmd.g:123:1: cmdStmt returns [ASTCmd n] : (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= executeCmd ) ;
    public final ASTCmd cmdStmt() throws RecognitionException {
        ASTCmd n = null;

        ASTCmd nC = null;


        try {
            // GCmd.g:124:1: ( (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= executeCmd ) )
            // GCmd.g:125:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= executeCmd )
            {
            // GCmd.g:125:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= executeCmd )
            int alt3=11;
            alt3 = dfa3.predict(input);
            switch (alt3) {
                case 1 :
                    // GCmd.g:126:7: nC= createCmd
                    {
                    pushFollow(FOLLOW_createCmd_in_cmdStmt152);
                    nC=createCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 2 :
                    // GCmd.g:127:7: nC= createAssignCmd
                    {
                    pushFollow(FOLLOW_createAssignCmd_in_cmdStmt164);
                    nC=createAssignCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 3 :
                    // GCmd.g:128:7: nC= createInsertCmd
                    {
                    pushFollow(FOLLOW_createInsertCmd_in_cmdStmt177);
                    nC=createInsertCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 4 :
                    // GCmd.g:129:7: nC= destroyCmd
                    {
                    pushFollow(FOLLOW_destroyCmd_in_cmdStmt189);
                    nC=destroyCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 5 :
                    // GCmd.g:130:7: nC= insertCmd
                    {
                    pushFollow(FOLLOW_insertCmd_in_cmdStmt201);
                    nC=insertCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 6 :
                    // GCmd.g:131:7: nC= deleteCmd
                    {
                    pushFollow(FOLLOW_deleteCmd_in_cmdStmt213);
                    nC=deleteCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 7 :
                    // GCmd.g:132:7: nC= setCmd
                    {
                    pushFollow(FOLLOW_setCmd_in_cmdStmt225);
                    nC=setCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 8 :
                    // GCmd.g:133:7: nC= opEnterCmd
                    {
                    pushFollow(FOLLOW_opEnterCmd_in_cmdStmt237);
                    nC=opEnterCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 9 :
                    // GCmd.g:134:7: nC= opExitCmd
                    {
                    pushFollow(FOLLOW_opExitCmd_in_cmdStmt249);
                    nC=opExitCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 10 :
                    // GCmd.g:135:7: nC= letCmd
                    {
                    pushFollow(FOLLOW_letCmd_in_cmdStmt261);
                    nC=letCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 11 :
                    // GCmd.g:136:7: nC= executeCmd
                    {
                    pushFollow(FOLLOW_executeCmd_in_cmdStmt273);
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
    // $ANTLR end cmdStmt


    // $ANTLR start createCmd
    // GCmd.g:146:1: createCmd returns [ASTCmd n] : 'create' nIdList= idList COLON t= simpleType ;
    public final ASTCmd createCmd() throws RecognitionException {
        ASTCmd n = null;

        List nIdList = null;

        ASTSimpleType t = null;


        try {
            // GCmd.g:147:1: ( 'create' nIdList= idList COLON t= simpleType )
            // GCmd.g:148:5: 'create' nIdList= idList COLON t= simpleType
            {
            match(input,72,FOLLOW_72_in_createCmd302); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createCmd306);
            nIdList=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_createCmd313); if (state.failed) return n;
            pushFollow(FOLLOW_simpleType_in_createCmd317);
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
    // $ANTLR end createCmd


    // $ANTLR start createAssignCmd
    // GCmd.g:158:1: createAssignCmd returns [ASTCmd n] : 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType ;
    public final ASTCmd createAssignCmd() throws RecognitionException {
        ASTCmd n = null;

        List nIdList = null;

        ASTSimpleType t = null;


        try {
            // GCmd.g:159:1: ( 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType )
            // GCmd.g:160:5: 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType
            {
            match(input,73,FOLLOW_73_in_createAssignCmd346); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createAssignCmd350);
            nIdList=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_createAssignCmd352); if (state.failed) return n;
            match(input,72,FOLLOW_72_in_createAssignCmd354); if (state.failed) return n;
            pushFollow(FOLLOW_simpleType_in_createAssignCmd358);
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
    // $ANTLR end createAssignCmd


    // $ANTLR start createInsertCmd
    // GCmd.g:168:1: createInsertCmd returns [ASTCmd n] : 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN ;
    public final ASTCmd createInsertCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        Token idAssoc=null;
        List idListInsert = null;


        try {
            // GCmd.g:169:1: ( 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN )
            // GCmd.g:170:5: 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN
            {
            match(input,72,FOLLOW_72_in_createInsertCmd378); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_createInsertCmd382); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_createInsertCmd384); if (state.failed) return n;
            idAssoc=(Token)match(input,IDENT,FOLLOW_IDENT_in_createInsertCmd388); if (state.failed) return n;
            match(input,74,FOLLOW_74_in_createInsertCmd394); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_createInsertCmd396); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createInsertCmd400);
            idListInsert=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_createInsertCmd402); if (state.failed) return n;
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
    // $ANTLR end createInsertCmd


    // $ANTLR start destroyCmd
    // GCmd.g:181:1: destroyCmd returns [ASTCmd n] : 'destroy' e= expression ( COMMA e= expression )* ;
    public final ASTCmd destroyCmd() throws RecognitionException {
        ASTCmd n = null;

        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // GCmd.g:183:1: ( 'destroy' e= expression ( COMMA e= expression )* )
            // GCmd.g:184:6: 'destroy' e= expression ( COMMA e= expression )*
            {
            match(input,75,FOLLOW_75_in_destroyCmd438); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_destroyCmd442);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // GCmd.g:185:16: ( COMMA e= expression )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==COMMA) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // GCmd.g:185:18: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_destroyCmd464); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_destroyCmd468);
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
    // $ANTLR end destroyCmd


    // $ANTLR start insertCmd
    // GCmd.g:195:1: insertCmd returns [ASTCmd n] : 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT ;
    public final ASTCmd insertCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // GCmd.g:197:1: ( 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT )
            // GCmd.g:198:5: 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT
            {
            match(input,76,FOLLOW_76_in_insertCmd507); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_insertCmd509); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_insertCmd518);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_insertCmd522); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_insertCmd530);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // GCmd.g:200:42: ( COMMA e= expression )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==COMMA) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // GCmd.g:200:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_insertCmd536); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_insertCmd540);
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

            match(input,RPAREN,FOLLOW_RPAREN_in_insertCmd552); if (state.failed) return n;
            match(input,77,FOLLOW_77_in_insertCmd554); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_insertCmd558); if (state.failed) return n;
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
    // $ANTLR end insertCmd


    // $ANTLR start deleteCmd
    // GCmd.g:211:1: deleteCmd returns [ASTCmd n] : 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT ;
    public final ASTCmd deleteCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // GCmd.g:213:1: ( 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT )
            // GCmd.g:214:5: 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT
            {
            match(input,78,FOLLOW_78_in_deleteCmd593); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_deleteCmd595); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_deleteCmd603);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_deleteCmd607); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_deleteCmd615);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // GCmd.g:216:42: ( COMMA e= expression )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==COMMA) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // GCmd.g:216:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_deleteCmd621); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_deleteCmd625);
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

            match(input,RPAREN,FOLLOW_RPAREN_in_deleteCmd636); if (state.failed) return n;
            match(input,79,FOLLOW_79_in_deleteCmd638); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_deleteCmd642); if (state.failed) return n;
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
    // $ANTLR end deleteCmd


    // $ANTLR start setCmd
    // GCmd.g:230:1: setCmd returns [ASTCmd n] : 'set' e1= expression COLON_EQUAL e2= expression ;
    public final ASTCmd setCmd() throws RecognitionException {
        ASTCmd n = null;

        ASTExpression e1 = null;

        ASTExpression e2 = null;


        try {
            // GCmd.g:231:1: ( 'set' e1= expression COLON_EQUAL e2= expression )
            // GCmd.g:232:5: 'set' e1= expression COLON_EQUAL e2= expression
            {
            match(input,80,FOLLOW_80_in_setCmd672); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_setCmd676);
            e1=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_setCmd678); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_setCmd682);
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
    // $ANTLR end setCmd


    // $ANTLR start opEnterCmd
    // GCmd.g:244:1: opEnterCmd returns [ASTCmd n] : 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN ;
    public final ASTCmd opEnterCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        ASTExpression e = null;


        ASTOpEnterCmd nOpEnter = null;
        try {
            // GCmd.g:246:1: ( 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )
            // GCmd.g:247:5: 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
            {
            match(input,81,FOLLOW_81_in_opEnterCmd716); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_opEnterCmd725);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_opEnterCmd729); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               nOpEnter = new ASTOpEnterCmd(e, id); n = nOpEnter;
            }
            match(input,LPAREN,FOLLOW_LPAREN_in_opEnterCmd737); if (state.failed) return n;
            // GCmd.g:250:5: (e= expression ( COMMA e= expression )* )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( ((LA8_0>=LITERAL_oclAsType && LA8_0<=LPAREN)||LA8_0==IDENT||(LA8_0>=PLUS && LA8_0<=MINUS)||(LA8_0>=INT && LA8_0<=HASH)||LA8_0==46||LA8_0==53||(LA8_0>=56 && LA8_0<=57)||(LA8_0>=61 && LA8_0<=70)) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // GCmd.g:250:7: e= expression ( COMMA e= expression )*
                    {
                    pushFollow(FOLLOW_expression_in_opEnterCmd747);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       nOpEnter.addArg(e); 
                    }
                    // GCmd.g:250:47: ( COMMA e= expression )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==COMMA) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // GCmd.g:250:49: COMMA e= expression
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_opEnterCmd753); if (state.failed) return n;
                    	    pushFollow(FOLLOW_expression_in_opEnterCmd757);
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

            match(input,RPAREN,FOLLOW_RPAREN_in_opEnterCmd771); if (state.failed) return n;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end opEnterCmd


    // $ANTLR start opExitCmd
    // GCmd.g:260:1: opExitCmd returns [ASTCmd n] : 'opexit' ( ( expression )=>e= expression | ) ;
    public final ASTCmd opExitCmd() throws RecognitionException {
        ASTCmd n = null;

        ASTExpression e = null;


        try {
            // GCmd.g:261:1: ( 'opexit' ( ( expression )=>e= expression | ) )
            // GCmd.g:262:5: 'opexit' ( ( expression )=>e= expression | )
            {
            match(input,82,FOLLOW_82_in_opExitCmd795); if (state.failed) return n;
            // GCmd.g:262:14: ( ( expression )=>e= expression | )
            int alt9=2;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // GCmd.g:262:15: ( expression )=>e= expression
                    {
                    pushFollow(FOLLOW_expression_in_opExitCmd805);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 2 :
                    // GCmd.g:262:45: 
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
    // $ANTLR end opExitCmd


    // $ANTLR start letCmd
    // GCmd.g:271:1: letCmd returns [ASTCmd n] : 'let' name= IDENT ( COLON t= type )? EQUAL e= expression ;
    public final ASTCmd letCmd() throws RecognitionException {
        ASTCmd n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // GCmd.g:272:1: ( 'let' name= IDENT ( COLON t= type )? EQUAL e= expression )
            // GCmd.g:273:5: 'let' name= IDENT ( COLON t= type )? EQUAL e= expression
            {
            match(input,46,FOLLOW_46_in_letCmd838); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_letCmd842); if (state.failed) return n;
            // GCmd.g:273:22: ( COLON t= type )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==COLON) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // GCmd.g:273:24: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_letCmd846); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_letCmd850);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            match(input,EQUAL,FOLLOW_EQUAL_in_letCmd855); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_letCmd859);
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
    // $ANTLR end letCmd


    // $ANTLR start executeCmd
    // GCmd.g:277:1: executeCmd returns [ASTCmd n] : 'execute' e= expression ;
    public final ASTCmd executeCmd() throws RecognitionException {
        ASTCmd n = null;

        ASTExpression e = null;


        try {
            // GCmd.g:278:1: ( 'execute' e= expression )
            // GCmd.g:279:2: 'execute' e= expression
            {
            match(input,83,FOLLOW_83_in_executeCmd884); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_executeCmd893);
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
    // $ANTLR end executeCmd

    // $ANTLR start synpred1_GCmd
    public final void synpred1_GCmd_fragment() throws RecognitionException {   
        // GCmd.g:262:15: ( expression )
        // GCmd.g:262:16: expression
        {
        pushFollow(FOLLOW_expression_in_synpred1_GCmd799);
        expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_GCmd

    // Delegated rules
    public ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException { return gGOCLBase.elemVarsDeclaration(); }
    public ASTVariableInitialization variableInitialization() throws RecognitionException { return gGOCLBase.variableInitialization(); }
    public ASTExpression literal() throws RecognitionException { return gGOCLBase.literal(); }
    public ASTVariableDeclaration variableDeclaration() throws RecognitionException { return gGOCLBase.variableDeclaration(); }
    public ASTExpression queryExpression(ASTExpression range) throws RecognitionException { return gGOCLBase.queryExpression(range); }
    public ASTCollectionLiteral collectionLiteral() throws RecognitionException { return gGOCLBase.collectionLiteral(); }
    public ASTType typeOnly() throws RecognitionException { return gGOCLBase.typeOnly(); }
    public ASTExpression relationalExpression() throws RecognitionException { return gGOCLBase.relationalExpression(); }
    public ASTExpression primaryExpression() throws RecognitionException { return gGOCLBase.primaryExpression(); }
    public ASTTupleType tupleType() throws RecognitionException { return gGOCLBase.tupleType(); }
    public ASTExpression ifExpression() throws RecognitionException { return gGOCLBase.ifExpression(); }
    public ASTExpression conditionalAndExpression() throws RecognitionException { return gGOCLBase.conditionalAndExpression(); }
    public ASTCollectionType collectionType() throws RecognitionException { return gGOCLBase.collectionType(); }
    public ASTUndefinedLiteral undefinedLiteral() throws RecognitionException { return gGOCLBase.undefinedLiteral(); }
    public ASTExpression unaryExpression() throws RecognitionException { return gGOCLBase.unaryExpression(); }
    public ASTType type() throws RecognitionException { return gGOCLBase.type(); }
    public ASTExpression conditionalXOrExpression() throws RecognitionException { return gGOCLBase.conditionalXOrExpression(); }
    public ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException { return gGOCLBase.emptyCollectionLiteral(); }
    public ASTSimpleType simpleType() throws RecognitionException { return gGOCLBase.simpleType(); }
    public List idList() throws RecognitionException { return gGOCLBase.idList(); }
    public ASTExpression conditionalImpliesExpression() throws RecognitionException { return gGOCLBase.conditionalImpliesExpression(); }
    public ASTExpression multiplicativeExpression() throws RecognitionException { return gGOCLBase.multiplicativeExpression(); }
    public ASTTupleLiteral tupleLiteral() throws RecognitionException { return gGOCLBase.tupleLiteral(); }
    public ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException { return gGOCLBase.propertyCall(source, followsArrow); }
    public ASTTupleItem tupleItem() throws RecognitionException { return gGOCLBase.tupleItem(); }
    public ASTTuplePart tuplePart() throws RecognitionException { return gGOCLBase.tuplePart(); }
    public ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException { return gGOCLBase.typeExpression(source, followsArrow); }
    public ASTExpression equalityExpression() throws RecognitionException { return gGOCLBase.equalityExpression(); }
    public ASTCollectionItem collectionItem() throws RecognitionException { return gGOCLBase.collectionItem(); }
    public ASTExpression iterateExpression(ASTExpression range) throws RecognitionException { return gGOCLBase.iterateExpression(range); }
    public ASTExpression conditionalOrExpression() throws RecognitionException { return gGOCLBase.conditionalOrExpression(); }
    public List paramList() throws RecognitionException { return gGOCLBase.paramList(); }
    public ASTExpression expression() throws RecognitionException { return gGOCLBase.expression(); }
    public ASTExpression postfixExpression() throws RecognitionException { return gGOCLBase.postfixExpression(); }
    public ASTExpression additiveExpression() throws RecognitionException { return gGOCLBase.additiveExpression(); }
    public ASTOperationExpression operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException { return gGOCLBase.operationExpression(source, followsArrow); }

    public final boolean synpred1_GCmd() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_GCmd_fragment(); // can never throw exception
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
        "\1\56\1\12\11\uffff\1\10\1\12\1\uffff\1\32\1\uffff";
    static final String DFA3_maxS =
        "\1\123\1\12\11\uffff\1\13\1\12\1\uffff\1\123\1\uffff";
    static final String DFA3_acceptS =
        "\2\uffff\1\2\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\2\uffff\1\1\1\uffff"+
        "\1\3";
    static final String DFA3_specialS =
        "\20\uffff}>";
    static final String[] DFA3_transitionS = {
            "\1\11\31\uffff\1\1\1\2\1\uffff\1\3\1\4\1\uffff\1\5\1\uffff\1"+
            "\6\1\7\1\10\1\12",
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
            "\1\15\2\uffff\1\14",
            "\1\16",
            "",
            "\1\15\23\uffff\1\15\31\uffff\2\15\1\17\2\15\1\uffff\1\15\1\uffff"+
            "\4\15",
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
            return "125:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= executeCmd )";
        }
    }
    static final String DFA9_eotS =
        "\37\uffff";
    static final String DFA9_eofS =
        "\1\24\36\uffff";
    static final String DFA9_minS =
        "\1\4\1\0\35\uffff";
    static final String DFA9_maxS =
        "\1\123\1\0\35\uffff";
    static final String DFA9_acceptS =
        "\2\uffff\22\1\1\2\12\uffff";
    static final String DFA9_specialS =
        "\1\0\1\1\35\uffff}>";
    static final String[] DFA9_transitionS = {
            "\3\21\1\22\2\uffff\1\17\7\uffff\2\2\6\uffff\1\24\2\uffff\1\5"+
            "\1\6\1\7\1\10\15\uffff\1\1\6\uffff\1\2\2\uffff\1\20\1\23\3\uffff"+
            "\1\3\1\4\3\11\1\12\1\13\1\14\1\15\1\16\1\uffff\2\24\1\uffff"+
            "\2\24\1\uffff\1\24\1\uffff\4\24",
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
            return "262:14: ( ( expression )=>e= expression | )";
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
                        if ( (LA9_0==46) ) {s = 1;}

                        else if ( ((LA9_0>=PLUS && LA9_0<=MINUS)||LA9_0==53) && (synpred1_GCmd())) {s = 2;}

                        else if ( (LA9_0==61) && (synpred1_GCmd())) {s = 3;}

                        else if ( (LA9_0==62) && (synpred1_GCmd())) {s = 4;}

                        else if ( (LA9_0==INT) && (synpred1_GCmd())) {s = 5;}

                        else if ( (LA9_0==REAL) && (synpred1_GCmd())) {s = 6;}

                        else if ( (LA9_0==STRING) && (synpred1_GCmd())) {s = 7;}

                        else if ( (LA9_0==HASH) && (synpred1_GCmd())) {s = 8;}

                        else if ( ((LA9_0>=63 && LA9_0<=65)) && (synpred1_GCmd())) {s = 9;}

                        else if ( (LA9_0==66) && (synpred1_GCmd())) {s = 10;}

                        else if ( (LA9_0==67) && (synpred1_GCmd())) {s = 11;}

                        else if ( (LA9_0==68) && (synpred1_GCmd())) {s = 12;}

                        else if ( (LA9_0==69) && (synpred1_GCmd())) {s = 13;}

                        else if ( (LA9_0==70) && (synpred1_GCmd())) {s = 14;}

                        else if ( (LA9_0==IDENT) && (synpred1_GCmd())) {s = 15;}

                        else if ( (LA9_0==56) && (synpred1_GCmd())) {s = 16;}

                        else if ( ((LA9_0>=LITERAL_oclAsType && LA9_0<=LITERAL_oclIsTypeOf)) && (synpred1_GCmd())) {s = 17;}

                        else if ( (LA9_0==LPAREN) && (synpred1_GCmd())) {s = 18;}

                        else if ( (LA9_0==57) && (synpred1_GCmd())) {s = 19;}

                        else if ( (LA9_0==EOF||LA9_0==SEMI||(LA9_0>=72 && LA9_0<=73)||(LA9_0>=75 && LA9_0<=76)||LA9_0==78||(LA9_0>=80 && LA9_0<=83)) ) {s = 20;}

                         
                        input.seek(index9_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA9_1 = input.LA(1);

                         
                        int index9_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_GCmd()) ) {s = 19;}

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
 

    public static final BitSet FOLLOW_cmd_in_cmdList67 = new BitSet(new long[]{0x0000400000000000L,0x00000000000F5B00L});
    public static final BitSet FOLLOW_cmd_in_cmdList79 = new BitSet(new long[]{0x0000400000000000L,0x00000000000F5B00L});
    public static final BitSet FOLLOW_EOF_in_cmdList90 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cmdStmt_in_cmd116 = new BitSet(new long[]{0x0000000004000002L});
    public static final BitSet FOLLOW_SEMI_in_cmd121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createCmd_in_cmdStmt152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createAssignCmd_in_cmdStmt164 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createInsertCmd_in_cmdStmt177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_destroyCmd_in_cmdStmt189 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_insertCmd_in_cmdStmt201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_deleteCmd_in_cmdStmt213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_setCmd_in_cmdStmt225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_opEnterCmd_in_cmdStmt237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_opExitCmd_in_cmdStmt249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_letCmd_in_cmdStmt261 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_executeCmd_in_cmdStmt273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_createCmd302 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_idList_in_createCmd306 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_createCmd313 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_simpleType_in_createCmd317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_createAssignCmd346 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_idList_in_createAssignCmd350 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_createAssignCmd352 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_createAssignCmd354 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_simpleType_in_createAssignCmd358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_createInsertCmd378 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_createInsertCmd382 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_createInsertCmd384 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_createInsertCmd388 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_createInsertCmd394 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_createInsertCmd396 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_idList_in_createInsertCmd400 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_createInsertCmd402 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_destroyCmd438 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000007FL});
    public static final BitSet FOLLOW_expression_in_destroyCmd442 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_destroyCmd464 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000007FL});
    public static final BitSet FOLLOW_expression_in_destroyCmd468 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_76_in_insertCmd507 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_insertCmd509 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000007FL});
    public static final BitSet FOLLOW_expression_in_insertCmd518 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_insertCmd522 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000007FL});
    public static final BitSet FOLLOW_expression_in_insertCmd530 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_COMMA_in_insertCmd536 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000007FL});
    public static final BitSet FOLLOW_expression_in_insertCmd540 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_RPAREN_in_insertCmd552 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_insertCmd554 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_insertCmd558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_deleteCmd593 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_deleteCmd595 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000007FL});
    public static final BitSet FOLLOW_expression_in_deleteCmd603 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_deleteCmd607 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000007FL});
    public static final BitSet FOLLOW_expression_in_deleteCmd615 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_COMMA_in_deleteCmd621 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000007FL});
    public static final BitSet FOLLOW_expression_in_deleteCmd625 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_RPAREN_in_deleteCmd636 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_deleteCmd638 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_deleteCmd642 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_setCmd672 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000007FL});
    public static final BitSet FOLLOW_expression_in_setCmd676 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_setCmd678 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000007FL});
    public static final BitSet FOLLOW_expression_in_setCmd682 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_opEnterCmd716 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000007FL});
    public static final BitSet FOLLOW_expression_in_opEnterCmd725 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_opEnterCmd729 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_opEnterCmd737 = new BitSet(new long[]{0xE3204001E00C06F0L,0x000000000000007FL});
    public static final BitSet FOLLOW_expression_in_opEnterCmd747 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_COMMA_in_opEnterCmd753 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000007FL});
    public static final BitSet FOLLOW_expression_in_opEnterCmd757 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_RPAREN_in_opEnterCmd771 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_opExitCmd795 = new BitSet(new long[]{0xE3204001E00C04F2L,0x000000000000007FL});
    public static final BitSet FOLLOW_expression_in_opExitCmd805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_letCmd838 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_letCmd842 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_COLON_in_letCmd846 = new BitSet(new long[]{0x8000000000000400L,0x00000000000000C3L});
    public static final BitSet FOLLOW_type_in_letCmd850 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_EQUAL_in_letCmd855 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000007FL});
    public static final BitSet FOLLOW_expression_in_letCmd859 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_executeCmd884 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000007FL});
    public static final BitSet FOLLOW_expression_in_executeCmd893 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_synpred1_GCmd799 = new BitSet(new long[]{0x0000000000000002L});

}