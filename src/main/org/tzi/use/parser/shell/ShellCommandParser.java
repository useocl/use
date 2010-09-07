// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 ShellCommand.g 2010-09-07 17:10:49

/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 University of Bremen
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
 
 
package org.tzi.use.parser.shell;

import org.tzi.use.parser.base.BaseParser;
import org.tzi.use.parser.ocl.*;
import org.tzi.use.parser.soil.ast.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all") public class ShellCommandParser extends BaseParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "SEMI", "COLON", "COLON_EQUAL", "IDENT", "LPAREN", "RPAREN", "EQUAL", "COMMA", "NOT_EQUAL", "LESS", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "PLUS", "MINUS", "STAR", "SLASH", "ARROW", "DOT", "AT", "BAR", "LBRACK", "RBRACK", "INT", "REAL", "STRING", "HASH", "LBRACE", "RBRACE", "DOTDOT", "NEWLINE", "WS", "SL_COMMENT", "ML_COMMENT", "COLON_COLON", "SCRIPTBODY", "RANGE_OR_INT", "ESC", "HEX_DIGIT", "VOCAB", "'set'", "'create'", "'assign'", "'between'", "'let'", "'openter'", "'opexit'", "'execute'", "'in'", "'implies'", "'or'", "'xor'", "'and'", "'div'", "'not'", "'allInstances'", "'pre'", "'iterate'", "'oclAsType'", "'oclIsKindOf'", "'oclIsTypeOf'", "'if'", "'then'", "'else'", "'endif'", "'true'", "'false'", "'Set'", "'Sequence'", "'Bag'", "'OrderedSet'", "'oclEmpty'", "'oclUndefined'", "'Undefined'", "'null'", "'Tuple'", "'Date'", "'Collection'", "'new'", "'destroy'", "'insert'", "'into'", "'delete'", "'from'", "'end'", "'for'", "'do'"
    };
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int LBRACK=25;
    public static final int STAR=19;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int ESC=41;
    public static final int LBRACE=31;
    public static final int DOTDOT=33;
    public static final int T__61=61;
    public static final int EOF=-1;
    public static final int T__60=60;
    public static final int LPAREN=8;
    public static final int AT=23;
    public static final int T__55=55;
    public static final int ML_COMMENT=37;
    public static final int T__56=56;
    public static final int RPAREN=9;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int SLASH=20;
    public static final int GREATER=14;
    public static final int COLON_EQUAL=6;
    public static final int T__51=51;
    public static final int T__90=90;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int NOT_EQUAL=12;
    public static final int COMMA=11;
    public static final int T__59=59;
    public static final int EQUAL=10;
    public static final int LESS=13;
    public static final int IDENT=7;
    public static final int PLUS=17;
    public static final int RANGE_OR_INT=40;
    public static final int DOT=22;
    public static final int SCRIPTBODY=39;
    public static final int T__50=50;
    public static final int RBRACK=26;
    public static final int T__46=46;
    public static final int T__80=80;
    public static final int T__47=47;
    public static final int T__81=81;
    public static final int RBRACE=32;
    public static final int T__44=44;
    public static final int T__82=82;
    public static final int T__45=45;
    public static final int T__83=83;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int HASH=30;
    public static final int HEX_DIGIT=42;
    public static final int COLON_COLON=38;
    public static final int INT=27;
    public static final int MINUS=18;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int SEMI=4;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int COLON=5;
    public static final int REAL=28;
    public static final int WS=35;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int NEWLINE=34;
    public static final int T__70=70;
    public static final int SL_COMMENT=36;
    public static final int VOCAB=43;
    public static final int ARROW=21;
    public static final int LESS_EQUAL=15;
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int T__74=74;
    public static final int GREATER_EQUAL=16;
    public static final int T__73=73;
    public static final int BAR=24;
    public static final int T__79=79;
    public static final int STRING=29;
    public static final int T__78=78;
    public static final int T__77=77;

    // delegates
    // delegators


        public ShellCommandParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public ShellCommandParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[92+1];
             
             
        }
        

    public String[] getTokenNames() { return ShellCommandParser.tokenNames; }
    public String getGrammarFileName() { return "ShellCommand.g"; }



    // $ANTLR start "shellCommand"
    // ShellCommand.g:81:1: shellCommand returns [ASTStatement n] : ( ( stat EOF )=>s= stat EOF | l= legacyStat EOF );
    public final ASTStatement shellCommand() throws RecognitionException {
        ASTStatement n = null;

        ShellCommandParser.stat_return s = null;

        ShellCommandParser.legacyStat_return l = null;


        try {
            // ShellCommand.g:82:1: ( ( stat EOF )=>s= stat EOF | l= legacyStat EOF )
            int alt1=2;
            alt1 = dfa1.predict(input);
            switch (alt1) {
                case 1 :
                    // ShellCommand.g:83:3: ( stat EOF )=>s= stat EOF
                    {
                    pushFollow(FOLLOW_stat_in_shellCommand70);
                    s=stat();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,EOF,FOLLOW_EOF_in_shellCommand72); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (s!=null?s.n:null); 
                    }

                    }
                    break;
                case 2 :
                    // ShellCommand.g:86:3: l= legacyStat EOF
                    {
                    pushFollow(FOLLOW_legacyStat_in_shellCommand88);
                    l=legacyStat();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,EOF,FOLLOW_EOF_in_shellCommand90); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (l!=null?l.n:null); 
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
    // $ANTLR end "shellCommand"

    public static class legacyStat_return extends ParserRuleReturnScope {
        public ASTStatement n;
    };

    // $ANTLR start "legacyStat"
    // ShellCommand.g:96:1: legacyStat returns [ASTStatement n] : (loe= legacyOpEnter | lox= legacyOpExit | nextLegacyStat[seq] ( nextLegacyStat[seq] )* );
    public final ShellCommandParser.legacyStat_return legacyStat() throws RecognitionException {
        ShellCommandParser.legacyStat_return retval = new ShellCommandParser.legacyStat_return();
        retval.start = input.LT(1);

        ASTEnterOperationStatement loe = null;

        ASTStatement lox = null;



          ASTSequenceStatement seq = new ASTSequenceStatement();

        try {
            // ShellCommand.g:100:1: (loe= legacyOpEnter | lox= legacyOpExit | nextLegacyStat[seq] ( nextLegacyStat[seq] )* )
            int alt3=3;
            switch ( input.LA(1) ) {
            case 49:
                {
                alt3=1;
                }
                break;
            case 50:
                {
                alt3=2;
                }
                break;
            case 44:
            case 45:
            case 46:
            case 48:
            case 51:
            case 83:
            case 84:
            case 86:
                {
                alt3=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // ShellCommand.g:101:3: loe= legacyOpEnter
                    {
                    pushFollow(FOLLOW_legacyOpEnter_in_legacyStat124);
                    loe=legacyOpEnter();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       
                          retval.n = loe;
                          if (retval.n != null) {
                            retval.n.setSourcePosition(((Token)retval.start));
                            retval.n.setParsedText(input.toString(retval.start,input.LT(-1)));
                          }
                        
                    }

                    }
                    break;
                case 2 :
                    // ShellCommand.g:110:3: lox= legacyOpExit
                    {
                    pushFollow(FOLLOW_legacyOpExit_in_legacyStat140);
                    lox=legacyOpExit();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       
                          retval.n = lox; 
                          if (retval.n != null) {
                            retval.n.setSourcePosition(((Token)retval.start));
                            retval.n.setParsedText(input.toString(retval.start,input.LT(-1)));
                          }
                        
                    }

                    }
                    break;
                case 3 :
                    // ShellCommand.g:119:3: nextLegacyStat[seq] ( nextLegacyStat[seq] )*
                    {
                    pushFollow(FOLLOW_nextLegacyStat_in_legacyStat153);
                    nextLegacyStat(seq);

                    state._fsp--;
                    if (state.failed) return retval;
                    // ShellCommand.g:120:3: ( nextLegacyStat[seq] )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( ((LA2_0>=44 && LA2_0<=46)||LA2_0==48||LA2_0==51||(LA2_0>=83 && LA2_0<=84)||LA2_0==86) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // ShellCommand.g:121:5: nextLegacyStat[seq]
                    	    {
                    	    pushFollow(FOLLOW_nextLegacyStat_in_legacyStat164);
                    	    nextLegacyStat(seq);

                    	    state._fsp--;
                    	    if (state.failed) return retval;

                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);

                    if ( state.backtracking==0 ) {
                       
                          retval.n = seq.simplify();
                          retval.n.setSourcePosition(((Token)retval.start));
                          retval.n.setParsedText(input.toString(retval.start,input.LT(-1)));
                        
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
        }
        return retval;
    }
    // $ANTLR end "legacyStat"

    public static class nextLegacyStat_return extends ParserRuleReturnScope {
    };

    // $ANTLR start "nextLegacyStat"
    // ShellCommand.g:135:1: nextLegacyStat[ASTSequenceStatement seq] : s= singleLegacyStat ( SEMI )? ;
    public final ShellCommandParser.nextLegacyStat_return nextLegacyStat(ASTSequenceStatement seq) throws RecognitionException {
        ShellCommandParser.nextLegacyStat_return retval = new ShellCommandParser.nextLegacyStat_return();
        retval.start = input.LT(1);

        ASTStatement s = null;


        try {
            // ShellCommand.g:136:1: (s= singleLegacyStat ( SEMI )? )
            // ShellCommand.g:137:3: s= singleLegacyStat ( SEMI )?
            {
            pushFollow(FOLLOW_singleLegacyStat_in_nextLegacyStat198);
            s=singleLegacyStat();

            state._fsp--;
            if (state.failed) return retval;
            // ShellCommand.g:138:3: ( SEMI )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==SEMI) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // ShellCommand.g:138:3: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_nextLegacyStat202); if (state.failed) return retval;

                    }
                    break;

            }

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
    // $ANTLR end "nextLegacyStat"


    // $ANTLR start "singleLegacyStat"
    // ShellCommand.g:151:1: singleLegacyStat returns [ASTStatement n] : (lcr= legacyCreate | lca= legacyCreateAssign | lci= legacyCreateInsert | llt= legacyLet | lex= legacyExecute | ods= objDestroyStat | 'set' aas= attAssignStat | lis= lnkInsStat | lds= lnkDelStat );
    public final ASTStatement singleLegacyStat() throws RecognitionException {
        ASTStatement n = null;

        ShellCommandParser.legacyCreate_return lcr = null;

        ShellCommandParser.legacyCreateAssign_return lca = null;

        ShellCommandParser.legacyCreateInsert_return lci = null;

        ASTVariableAssignmentStatement llt = null;

        ASTStatement lex = null;

        ShellCommandParser.objDestroyStat_return ods = null;

        ASTAttributeAssignmentStatement aas = null;

        ASTLinkInsertionStatement lis = null;

        ASTLinkDeletionStatement lds = null;


        try {
            // ShellCommand.g:152:1: (lcr= legacyCreate | lca= legacyCreateAssign | lci= legacyCreateInsert | llt= legacyLet | lex= legacyExecute | ods= objDestroyStat | 'set' aas= attAssignStat | lis= lnkInsStat | lds= lnkDelStat )
            int alt5=9;
            alt5 = dfa5.predict(input);
            switch (alt5) {
                case 1 :
                    // ShellCommand.g:153:5: lcr= legacyCreate
                    {
                    pushFollow(FOLLOW_legacyCreate_in_singleLegacyStat235);
                    lcr=legacyCreate();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (lcr!=null?lcr.n:null); 
                    }

                    }
                    break;
                case 2 :
                    // ShellCommand.g:154:5: lca= legacyCreateAssign
                    {
                    pushFollow(FOLLOW_legacyCreateAssign_in_singleLegacyStat254);
                    lca=legacyCreateAssign();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (lca!=null?lca.n:null); 
                    }

                    }
                    break;
                case 3 :
                    // ShellCommand.g:155:5: lci= legacyCreateInsert
                    {
                    pushFollow(FOLLOW_legacyCreateInsert_in_singleLegacyStat267);
                    lci=legacyCreateInsert();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (lci!=null?lci.n:null); 
                    }

                    }
                    break;
                case 4 :
                    // ShellCommand.g:156:5: llt= legacyLet
                    {
                    pushFollow(FOLLOW_legacyLet_in_singleLegacyStat280);
                    llt=legacyLet();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = llt; 
                    }

                    }
                    break;
                case 5 :
                    // ShellCommand.g:159:5: lex= legacyExecute
                    {
                    pushFollow(FOLLOW_legacyExecute_in_singleLegacyStat308);
                    lex=legacyExecute();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = lex; 
                    }

                    }
                    break;
                case 6 :
                    // ShellCommand.g:160:5: ods= objDestroyStat
                    {
                    pushFollow(FOLLOW_objDestroyStat_in_singleLegacyStat326);
                    ods=objDestroyStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (ods!=null?ods.n:null); 
                    }

                    }
                    break;
                case 7 :
                    // ShellCommand.g:161:5: 'set' aas= attAssignStat
                    {
                    match(input,44,FOLLOW_44_in_singleLegacyStat339); if (state.failed) return n;
                    pushFollow(FOLLOW_attAssignStat_in_singleLegacyStat345);
                    aas=attAssignStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = aas; 
                    }

                    }
                    break;
                case 8 :
                    // ShellCommand.g:162:5: lis= lnkInsStat
                    {
                    pushFollow(FOLLOW_lnkInsStat_in_singleLegacyStat357);
                    lis=lnkInsStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = lis; 
                    }

                    }
                    break;
                case 9 :
                    // ShellCommand.g:163:5: lds= lnkDelStat
                    {
                    pushFollow(FOLLOW_lnkDelStat_in_singleLegacyStat378);
                    lds=lnkDelStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = lds; 
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
    // $ANTLR end "singleLegacyStat"

    public static class legacyCreate_return extends ParserRuleReturnScope {
        public ASTStatement n;
    };

    // $ANTLR start "legacyCreate"
    // ShellCommand.g:176:1: legacyCreate returns [ASTStatement n] : 'create' objNames= identListMin1 COLON objType= simpleType ;
    public final ShellCommandParser.legacyCreate_return legacyCreate() throws RecognitionException {
        ShellCommandParser.legacyCreate_return retval = new ShellCommandParser.legacyCreate_return();
        retval.start = input.LT(1);

        List<String> objNames = null;

        ASTSimpleType objType = null;


        try {
            // ShellCommand.g:177:1: ( 'create' objNames= identListMin1 COLON objType= simpleType )
            // ShellCommand.g:178:3: 'create' objNames= identListMin1 COLON objType= simpleType
            {
            match(input,45,FOLLOW_45_in_legacyCreate414); if (state.failed) return retval;
            pushFollow(FOLLOW_identListMin1_in_legacyCreate422);
            objNames=identListMin1();

            state._fsp--;
            if (state.failed) return retval;
            match(input,COLON,FOLLOW_COLON_in_legacyCreate426); if (state.failed) return retval;
            pushFollow(FOLLOW_simpleType_in_legacyCreate434);
            objType=simpleType();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) {
               
                  ASTSequenceStatement seq = new ASTSequenceStatement();
                  
                  for (String objName : objNames) {
                    seq.addStatement(
                      new ASTNewObjectStatement(
                        objType, 
                        new ASTStringLiteral(objName)), 
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
    // $ANTLR end "legacyCreate"

    public static class legacyCreateAssign_return extends ParserRuleReturnScope {
        public ASTStatement n;
    };

    // $ANTLR start "legacyCreateAssign"
    // ShellCommand.g:210:1: legacyCreateAssign returns [ASTStatement n] : 'assign' varNames= identListMin1 COLON_EQUAL 'create' objType= simpleType ;
    public final ShellCommandParser.legacyCreateAssign_return legacyCreateAssign() throws RecognitionException {
        ShellCommandParser.legacyCreateAssign_return retval = new ShellCommandParser.legacyCreateAssign_return();
        retval.start = input.LT(1);

        List<String> varNames = null;

        ASTSimpleType objType = null;


        try {
            // ShellCommand.g:211:1: ( 'assign' varNames= identListMin1 COLON_EQUAL 'create' objType= simpleType )
            // ShellCommand.g:212:3: 'assign' varNames= identListMin1 COLON_EQUAL 'create' objType= simpleType
            {
            match(input,46,FOLLOW_46_in_legacyCreateAssign471); if (state.failed) return retval;
            pushFollow(FOLLOW_identListMin1_in_legacyCreateAssign479);
            varNames=identListMin1();

            state._fsp--;
            if (state.failed) return retval;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_legacyCreateAssign483); if (state.failed) return retval;
            match(input,45,FOLLOW_45_in_legacyCreateAssign487); if (state.failed) return retval;
            pushFollow(FOLLOW_simpleType_in_legacyCreateAssign495);
            objType=simpleType();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) {

                  ASTSequenceStatement seq = new ASTSequenceStatement();
                  
                  for (String varName : varNames) {
                    seq.addStatement(
                      new ASTVariableAssignmentStatement(
                        varName, 
                        new ASTRValueNewObject(objType)),
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
    // $ANTLR end "legacyCreateAssign"

    public static class legacyCreateInsert_return extends ParserRuleReturnScope {
        public ASTVariableAssignmentStatement n;
    };

    // $ANTLR start "legacyCreateInsert"
    // ShellCommand.g:246:1: legacyCreateInsert returns [ASTVariableAssignmentStatement n] : 'create' name= IDENT COLON asClassName= IDENT 'between' LPAREN participants= rValListMin2 RPAREN ;
    public final ShellCommandParser.legacyCreateInsert_return legacyCreateInsert() throws RecognitionException {
        ShellCommandParser.legacyCreateInsert_return retval = new ShellCommandParser.legacyCreateInsert_return();
        retval.start = input.LT(1);

        Token name=null;
        Token asClassName=null;
        List<ASTRValue> participants = null;


        try {
            // ShellCommand.g:247:1: ( 'create' name= IDENT COLON asClassName= IDENT 'between' LPAREN participants= rValListMin2 RPAREN )
            // ShellCommand.g:248:3: 'create' name= IDENT COLON asClassName= IDENT 'between' LPAREN participants= rValListMin2 RPAREN
            {
            match(input,45,FOLLOW_45_in_legacyCreateInsert529); if (state.failed) return retval;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_legacyCreateInsert537); if (state.failed) return retval;
            match(input,COLON,FOLLOW_COLON_in_legacyCreateInsert541); if (state.failed) return retval;
            asClassName=(Token)match(input,IDENT,FOLLOW_IDENT_in_legacyCreateInsert549); if (state.failed) return retval;
            match(input,47,FOLLOW_47_in_legacyCreateInsert553); if (state.failed) return retval;
            match(input,LPAREN,FOLLOW_LPAREN_in_legacyCreateInsert557); if (state.failed) return retval;
            pushFollow(FOLLOW_rValListMin2_in_legacyCreateInsert567);
            participants=rValListMin2();

            state._fsp--;
            if (state.failed) return retval;
            match(input,RPAREN,FOLLOW_RPAREN_in_legacyCreateInsert571); if (state.failed) return retval;
            if ( state.backtracking==0 ) {

                  ASTNewLinkObjectStatement nlo = 
                    new ASTNewLinkObjectStatement(
                      (asClassName!=null?asClassName.getText():null), 
                      participants, 
                      new ASTStringLiteral((name!=null?name.getText():null)));
                      
                  nlo.setSourcePosition(((Token)retval.start));
                  nlo.setParsedText(input.toString(retval.start,input.LT(-1)));
                
                  retval.n = new ASTVariableAssignmentStatement(
                    (name!=null?name.getText():null),
                    new ASTRValueNewLinkObject(nlo));
                
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
    // $ANTLR end "legacyCreateInsert"


    // $ANTLR start "legacyLet"
    // ShellCommand.g:284:1: legacyLet returns [ASTVariableAssignmentStatement n] : 'let' varName= IDENT ( COLON varType= type )? EQUAL e= inSoilExpression ;
    public final ASTVariableAssignmentStatement legacyLet() throws RecognitionException {
        ASTVariableAssignmentStatement n = null;

        Token varName=null;
        ASTType varType = null;

        ASTExpression e = null;


        try {
            // ShellCommand.g:285:1: ( 'let' varName= IDENT ( COLON varType= type )? EQUAL e= inSoilExpression )
            // ShellCommand.g:286:3: 'let' varName= IDENT ( COLON varType= type )? EQUAL e= inSoilExpression
            {
            match(input,48,FOLLOW_48_in_legacyLet604); if (state.failed) return n;
            varName=(Token)match(input,IDENT,FOLLOW_IDENT_in_legacyLet612); if (state.failed) return n;
            // ShellCommand.g:288:3: ( COLON varType= type )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==COLON) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // ShellCommand.g:289:5: COLON varType= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_legacyLet622); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_legacyLet632);
                    varType=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            match(input,EQUAL,FOLLOW_EQUAL_in_legacyLet641); if (state.failed) return n;
            pushFollow(FOLLOW_inSoilExpression_in_legacyLet649);
            e=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTVariableAssignmentStatement((varName!=null?varName.getText():null), e, varType); 
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
    // $ANTLR end "legacyLet"


    // $ANTLR start "legacyOpEnter"
    // ShellCommand.g:310:1: legacyOpEnter returns [ASTEnterOperationStatement n] : 'openter' obj= inSoilExpression op= IDENT LPAREN args= exprList RPAREN ;
    public final ASTEnterOperationStatement legacyOpEnter() throws RecognitionException {
        ASTEnterOperationStatement n = null;

        Token op=null;
        ASTExpression obj = null;

        List<ASTExpression> args = null;


        try {
            // ShellCommand.g:311:1: ( 'openter' obj= inSoilExpression op= IDENT LPAREN args= exprList RPAREN )
            // ShellCommand.g:312:3: 'openter' obj= inSoilExpression op= IDENT LPAREN args= exprList RPAREN
            {
            match(input,49,FOLLOW_49_in_legacyOpEnter682); if (state.failed) return n;
            pushFollow(FOLLOW_inSoilExpression_in_legacyOpEnter690);
            obj=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            op=(Token)match(input,IDENT,FOLLOW_IDENT_in_legacyOpEnter698); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_legacyOpEnter702); if (state.failed) return n;
            pushFollow(FOLLOW_exprList_in_legacyOpEnter712);
            args=exprList();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_legacyOpEnter716); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTEnterOperationStatement(obj, (op!=null?op.getText():null), args); 
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
    // $ANTLR end "legacyOpEnter"


    // $ANTLR start "legacyOpExit"
    // ShellCommand.g:335:1: legacyOpExit returns [ASTStatement n] : 'opexit' ( ( inSoilExpression )=>retVal= inSoilExpression | nothing ) ;
    public final ASTStatement legacyOpExit() throws RecognitionException {
        ASTStatement n = null;

        ASTExpression retVal = null;


        try {
            // ShellCommand.g:336:1: ( 'opexit' ( ( inSoilExpression )=>retVal= inSoilExpression | nothing ) )
            // ShellCommand.g:337:3: 'opexit' ( ( inSoilExpression )=>retVal= inSoilExpression | nothing )
            {
            match(input,50,FOLLOW_50_in_legacyOpExit751); if (state.failed) return n;
            // ShellCommand.g:337:12: ( ( inSoilExpression )=>retVal= inSoilExpression | nothing )
            int alt7=2;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // ShellCommand.g:337:13: ( inSoilExpression )=>retVal= inSoilExpression
                    {
                    pushFollow(FOLLOW_inSoilExpression_in_legacyOpExit763);
                    retVal=inSoilExpression();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 2 :
                    // ShellCommand.g:337:62: nothing
                    {
                    pushFollow(FOLLOW_nothing_in_legacyOpExit767);
                    nothing();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTExitOperationStatement(retVal); 
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
    // $ANTLR end "legacyOpExit"


    // $ANTLR start "legacyExecute"
    // ShellCommand.g:352:1: legacyExecute returns [ASTStatement n] : 'execute' expression ;
    public final ASTStatement legacyExecute() throws RecognitionException {
        ASTStatement n = null;

        try {
            // ShellCommand.g:353:1: ( 'execute' expression )
            // ShellCommand.g:354:3: 'execute' expression
            {
            match(input,51,FOLLOW_51_in_legacyExecute800); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_legacyExecute804);
            expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               
                  reportWarning("the execute command is not supported in this version"); 
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
    // $ANTLR end "legacyExecute"


    // $ANTLR start "expressionOnly"
    // ShellCommand.g:392:1: expressionOnly returns [ASTExpression n] : nExp= expression EOF ;
    public final ASTExpression expressionOnly() throws RecognitionException {
        ASTExpression n = null;

        ShellCommandParser.expression_return nExp = null;


        try {
            // ShellCommand.g:393:1: (nExp= expression EOF )
            // ShellCommand.g:394:5: nExp= expression EOF
            {
            pushFollow(FOLLOW_expression_in_expressionOnly842);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_expressionOnly844); if (state.failed) return n;
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
    // ShellCommand.g:401:1: expression returns [ASTExpression n] : ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression ;
    public final ShellCommandParser.expression_return expression() throws RecognitionException {
        ShellCommandParser.expression_return retval = new ShellCommandParser.expression_return();
        retval.start = input.LT(1);

        Token name=null;
        ASTType t = null;

        ShellCommandParser.expression_return e1 = null;

        ASTExpression nCndImplies = null;


         
          ASTLetExpression prevLet = null, firstLet = null;
          ASTExpression e2;
          Token tok = null;

        try {
            // ShellCommand.g:407:1: ( ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression )
            // ShellCommand.g:408:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of expression */ 
            }
            // ShellCommand.g:409:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==48) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // ShellCommand.g:410:7: 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in'
            	    {
            	    match(input,48,FOLLOW_48_in_expression892); if (state.failed) return retval;
            	    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_expression896); if (state.failed) return retval;
            	    // ShellCommand.g:410:24: ( COLON t= type )?
            	    int alt8=2;
            	    int LA8_0 = input.LA(1);

            	    if ( (LA8_0==COLON) ) {
            	        alt8=1;
            	    }
            	    switch (alt8) {
            	        case 1 :
            	            // ShellCommand.g:410:26: COLON t= type
            	            {
            	            match(input,COLON,FOLLOW_COLON_in_expression900); if (state.failed) return retval;
            	            pushFollow(FOLLOW_type_in_expression904);
            	            t=type();

            	            state._fsp--;
            	            if (state.failed) return retval;

            	            }
            	            break;

            	    }

            	    match(input,EQUAL,FOLLOW_EQUAL_in_expression909); if (state.failed) return retval;
            	    pushFollow(FOLLOW_expression_in_expression913);
            	    e1=expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    match(input,52,FOLLOW_52_in_expression915); if (state.failed) return retval;
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
            	    break loop9;
                }
            } while (true);

            pushFollow(FOLLOW_conditionalImpliesExpression_in_expression940);
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
    // ShellCommand.g:438:1: paramList returns [List paramList] : LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN ;
    public final List paramList() throws RecognitionException {
        List paramList = null;

        ASTVariableDeclaration v = null;


         paramList = new ArrayList(); 
        try {
            // ShellCommand.g:440:1: ( LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN )
            // ShellCommand.g:441:5: LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN
            {
            match(input,LPAREN,FOLLOW_LPAREN_in_paramList973); if (state.failed) return paramList;
            // ShellCommand.g:442:5: (v= variableDeclaration ( COMMA v= variableDeclaration )* )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==IDENT) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // ShellCommand.g:443:7: v= variableDeclaration ( COMMA v= variableDeclaration )*
                    {
                    pushFollow(FOLLOW_variableDeclaration_in_paramList990);
                    v=variableDeclaration();

                    state._fsp--;
                    if (state.failed) return paramList;
                    if ( state.backtracking==0 ) {
                       paramList.add(v); 
                    }
                    // ShellCommand.g:444:7: ( COMMA v= variableDeclaration )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0==COMMA) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // ShellCommand.g:444:9: COMMA v= variableDeclaration
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_paramList1002); if (state.failed) return paramList;
                    	    pushFollow(FOLLOW_variableDeclaration_in_paramList1006);
                    	    v=variableDeclaration();

                    	    state._fsp--;
                    	    if (state.failed) return paramList;
                    	    if ( state.backtracking==0 ) {
                    	       paramList.add(v); 
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

            match(input,RPAREN,FOLLOW_RPAREN_in_paramList1026); if (state.failed) return paramList;

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
    // ShellCommand.g:452:1: idList returns [List idList] : id0= IDENT ( COMMA idn= IDENT )* ;
    public final List idList() throws RecognitionException {
        List idList = null;

        Token id0=null;
        Token idn=null;

         idList = new ArrayList(); 
        try {
            // ShellCommand.g:454:1: (id0= IDENT ( COMMA idn= IDENT )* )
            // ShellCommand.g:455:5: id0= IDENT ( COMMA idn= IDENT )*
            {
            id0=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList1055); if (state.failed) return idList;
            if ( state.backtracking==0 ) {
               idList.add(id0); 
            }
            // ShellCommand.g:456:5: ( COMMA idn= IDENT )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==COMMA) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // ShellCommand.g:456:7: COMMA idn= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_idList1065); if (state.failed) return idList;
            	    idn=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList1069); if (state.failed) return idList;
            	    if ( state.backtracking==0 ) {
            	       idList.add(idn); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop12;
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
    // ShellCommand.g:464:1: variableDeclaration returns [ASTVariableDeclaration n] : name= IDENT COLON t= type ;
    public final ASTVariableDeclaration variableDeclaration() throws RecognitionException {
        ASTVariableDeclaration n = null;

        Token name=null;
        ASTType t = null;


        try {
            // ShellCommand.g:465:1: (name= IDENT COLON t= type )
            // ShellCommand.g:466:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableDeclaration1100); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableDeclaration1102); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableDeclaration1106);
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
    // ShellCommand.g:474:1: conditionalImpliesExpression returns [ASTExpression n] : nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* ;
    public final ASTExpression conditionalImpliesExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndOrExp = null;

        ASTExpression n1 = null;


        try {
            // ShellCommand.g:475:1: (nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* )
            // ShellCommand.g:476:5: nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )*
            {
            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression1142);
            nCndOrExp=conditionalOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndOrExp;
            }
            // ShellCommand.g:477:5: (op= 'implies' n1= conditionalOrExpression )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==53) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // ShellCommand.g:477:7: op= 'implies' n1= conditionalOrExpression
            	    {
            	    op=(Token)match(input,53,FOLLOW_53_in_conditionalImpliesExpression1155); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression1159);
            	    n1=conditionalOrExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop13;
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
    // ShellCommand.g:486:1: conditionalOrExpression returns [ASTExpression n] : nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* ;
    public final ASTExpression conditionalOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndXorExp = null;

        ASTExpression n1 = null;


        try {
            // ShellCommand.g:487:1: (nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* )
            // ShellCommand.g:488:5: nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )*
            {
            pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression1204);
            nCndXorExp=conditionalXOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndXorExp;
            }
            // ShellCommand.g:489:5: (op= 'or' n1= conditionalXOrExpression )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==54) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // ShellCommand.g:489:7: op= 'or' n1= conditionalXOrExpression
            	    {
            	    op=(Token)match(input,54,FOLLOW_54_in_conditionalOrExpression1217); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression1221);
            	    n1=conditionalXOrExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop14;
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
    // ShellCommand.g:498:1: conditionalXOrExpression returns [ASTExpression n] : nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* ;
    public final ASTExpression conditionalXOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndAndExp = null;

        ASTExpression n1 = null;


        try {
            // ShellCommand.g:499:1: (nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* )
            // ShellCommand.g:500:5: nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )*
            {
            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression1265);
            nCndAndExp=conditionalAndExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndAndExp;
            }
            // ShellCommand.g:501:5: (op= 'xor' n1= conditionalAndExpression )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==55) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // ShellCommand.g:501:7: op= 'xor' n1= conditionalAndExpression
            	    {
            	    op=(Token)match(input,55,FOLLOW_55_in_conditionalXOrExpression1278); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression1282);
            	    n1=conditionalAndExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop15;
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
    // ShellCommand.g:510:1: conditionalAndExpression returns [ASTExpression n] : nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* ;
    public final ASTExpression conditionalAndExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nEqExp = null;

        ASTExpression n1 = null;


        try {
            // ShellCommand.g:511:1: (nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* )
            // ShellCommand.g:512:5: nEqExp= equalityExpression (op= 'and' n1= equalityExpression )*
            {
            pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression1326);
            nEqExp=equalityExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nEqExp;
            }
            // ShellCommand.g:513:5: (op= 'and' n1= equalityExpression )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==56) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // ShellCommand.g:513:7: op= 'and' n1= equalityExpression
            	    {
            	    op=(Token)match(input,56,FOLLOW_56_in_conditionalAndExpression1339); if (state.failed) return n;
            	    pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression1343);
            	    n1=equalityExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop16;
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
    // ShellCommand.g:522:1: equalityExpression returns [ASTExpression n] : nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* ;
    public final ASTExpression equalityExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nRelExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // ShellCommand.g:524:1: (nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* )
            // ShellCommand.g:525:5: nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            {
            pushFollow(FOLLOW_relationalExpression_in_equalityExpression1391);
            nRelExp=relationalExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nRelExp;
            }
            // ShellCommand.g:526:5: ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==EQUAL||LA17_0==NOT_EQUAL) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // ShellCommand.g:526:7: ( EQUAL | NOT_EQUAL ) n1= relationalExpression
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

            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression1420);
            	    n1=relationalExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop17;
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
    // ShellCommand.g:536:1: relationalExpression returns [ASTExpression n] : nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* ;
    public final ASTExpression relationalExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nAddiExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // ShellCommand.g:538:1: (nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* )
            // ShellCommand.g:539:5: nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            {
            pushFollow(FOLLOW_additiveExpression_in_relationalExpression1469);
            nAddiExp=additiveExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nAddiExp;
            }
            // ShellCommand.g:540:5: ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>=LESS && LA18_0<=GREATER_EQUAL)) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // ShellCommand.g:540:7: ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       op = input.LT(1); 
            	    }
            	    if ( (input.LA(1)>=LESS && input.LA(1)<=GREATER_EQUAL) ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_additiveExpression_in_relationalExpression1505);
            	    n1=additiveExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop18;
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
    // ShellCommand.g:550:1: additiveExpression returns [ASTExpression n] : nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* ;
    public final ASTExpression additiveExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nMulExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // ShellCommand.g:552:1: (nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* )
            // ShellCommand.g:553:5: nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            {
            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1555);
            nMulExp=multiplicativeExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nMulExp;
            }
            // ShellCommand.g:554:5: ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( ((LA19_0>=PLUS && LA19_0<=MINUS)) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // ShellCommand.g:554:7: ( PLUS | MINUS ) n1= multiplicativeExpression
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

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1583);
            	    n1=multiplicativeExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop19;
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
    // ShellCommand.g:565:1: multiplicativeExpression returns [ASTExpression n] : nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* ;
    public final ASTExpression multiplicativeExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // ShellCommand.g:567:1: (nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* )
            // ShellCommand.g:568:5: nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            {
            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1633);
            nUnExp=unaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nUnExp;
            }
            // ShellCommand.g:569:5: ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>=STAR && LA20_0<=SLASH)||LA20_0==57) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // ShellCommand.g:569:7: ( STAR | SLASH | 'div' ) n1= unaryExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       op = input.LT(1); 
            	    }
            	    if ( (input.LA(1)>=STAR && input.LA(1)<=SLASH)||input.LA(1)==57 ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1665);
            	    n1=unaryExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop20;
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
    // ShellCommand.g:581:1: unaryExpression returns [ASTExpression n] : ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression );
    public final ASTExpression unaryExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression nPosExp = null;


         Token op = null; 
        try {
            // ShellCommand.g:583:1: ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( ((LA21_0>=PLUS && LA21_0<=MINUS)||LA21_0==58) ) {
                alt21=1;
            }
            else if ( ((LA21_0>=IDENT && LA21_0<=LPAREN)||LA21_0==AT||(LA21_0>=INT && LA21_0<=HASH)||(LA21_0>=61 && LA21_0<=65)||(LA21_0>=69 && LA21_0<=80)) ) {
                alt21=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // ShellCommand.g:584:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    {
                    // ShellCommand.g:584:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    // ShellCommand.g:584:9: ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression
                    {
                    if ( state.backtracking==0 ) {
                       op = input.LT(1); 
                    }
                    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS)||input.LA(1)==58 ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression1751);
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
                    // ShellCommand.g:588:7: nPosExp= postfixExpression
                    {
                    pushFollow(FOLLOW_postfixExpression_in_unaryExpression1771);
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
    // ShellCommand.g:596:1: postfixExpression returns [ASTExpression n] : nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* ;
    public final ASTExpression postfixExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nPrimExp = null;

        ASTExpression nPc = null;


         boolean arrow = false; 
        try {
            // ShellCommand.g:598:1: (nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* )
            // ShellCommand.g:599:5: nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            {
            pushFollow(FOLLOW_primaryExpression_in_postfixExpression1804);
            nPrimExp=primaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nPrimExp; 
            }
            // ShellCommand.g:600:5: ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==DOT) ) {
                    int LA23_2 = input.LA(2);

                    if ( (LA23_2==IDENT) ) {
                        int LA23_4 = input.LA(3);

                        if ( (LA23_4==EOF||LA23_4==SEMI||(LA23_4>=IDENT && LA23_4<=LBRACK)||(LA23_4>=RBRACE && LA23_4<=DOTDOT)||(LA23_4>=44 && LA23_4<=46)||LA23_4==48||(LA23_4>=51 && LA23_4<=57)||(LA23_4>=66 && LA23_4<=68)||(LA23_4>=83 && LA23_4<=84)||LA23_4==86||LA23_4==88||LA23_4==90) ) {
                            alt23=1;
                        }


                    }
                    else if ( ((LA23_2>=61 && LA23_2<=64)) ) {
                        alt23=1;
                    }


                }
                else if ( (LA23_0==ARROW) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // ShellCommand.g:601:6: ( ARROW | DOT ) nPc= propertyCall[$n, arrow]
            	    {
            	    // ShellCommand.g:601:6: ( ARROW | DOT )
            	    int alt22=2;
            	    int LA22_0 = input.LA(1);

            	    if ( (LA22_0==ARROW) ) {
            	        alt22=1;
            	    }
            	    else if ( (LA22_0==DOT) ) {
            	        alt22=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 22, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt22) {
            	        case 1 :
            	            // ShellCommand.g:601:8: ARROW
            	            {
            	            match(input,ARROW,FOLLOW_ARROW_in_postfixExpression1822); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = true; 
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // ShellCommand.g:601:34: DOT
            	            {
            	            match(input,DOT,FOLLOW_DOT_in_postfixExpression1828); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = false; 
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_propertyCall_in_postfixExpression1839);
            	    nPc=propertyCall(n, arrow);

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = nPc; 
            	    }

            	    }
            	    break;

            	default :
            	    break loop23;
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
    // ShellCommand.g:617:1: primaryExpression returns [ASTExpression n] : (nLit= literal | nOr= objectReference | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? );
    public final ASTExpression primaryExpression() throws RecognitionException {
        ASTExpression n = null;

        Token id1=null;
        ASTExpression nLit = null;

        ASTExpression nOr = null;

        ASTExpression nPc = null;

        ShellCommandParser.expression_return nExp = null;

        ASTExpression nIfExp = null;


        try {
            // ShellCommand.g:618:1: (nLit= literal | nOr= objectReference | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? )
            int alt26=6;
            switch ( input.LA(1) ) {
            case INT:
            case REAL:
            case STRING:
            case HASH:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
                {
                alt26=1;
                }
                break;
            case IDENT:
                {
                switch ( input.LA(2) ) {
                case COLON_COLON:
                    {
                    alt26=1;
                    }
                    break;
                case EOF:
                case SEMI:
                case IDENT:
                case LPAREN:
                case RPAREN:
                case EQUAL:
                case COMMA:
                case NOT_EQUAL:
                case LESS:
                case GREATER:
                case LESS_EQUAL:
                case GREATER_EQUAL:
                case PLUS:
                case MINUS:
                case STAR:
                case SLASH:
                case ARROW:
                case AT:
                case BAR:
                case LBRACK:
                case RBRACE:
                case DOTDOT:
                case 44:
                case 45:
                case 46:
                case 48:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 66:
                case 67:
                case 68:
                case 83:
                case 84:
                case 86:
                case 88:
                case 90:
                    {
                    alt26=3;
                    }
                    break;
                case DOT:
                    {
                    int LA26_7 = input.LA(3);

                    if ( (LA26_7==59) ) {
                        alt26=6;
                    }
                    else if ( (LA26_7==IDENT||(LA26_7>=61 && LA26_7<=64)) ) {
                        alt26=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 26, 7, input);

                        throw nvae;
                    }
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 26, 2, input);

                    throw nvae;
                }

                }
                break;
            case AT:
                {
                alt26=2;
                }
                break;
            case 61:
            case 62:
            case 63:
            case 64:
                {
                alt26=3;
                }
                break;
            case LPAREN:
                {
                alt26=4;
                }
                break;
            case 65:
                {
                alt26=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }

            switch (alt26) {
                case 1 :
                    // ShellCommand.g:619:7: nLit= literal
                    {
                    pushFollow(FOLLOW_literal_in_primaryExpression1879);
                    nLit=literal();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nLit; 
                    }

                    }
                    break;
                case 2 :
                    // ShellCommand.g:620:7: nOr= objectReference
                    {
                    pushFollow(FOLLOW_objectReference_in_primaryExpression1893);
                    nOr=objectReference();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nOr; 
                    }

                    }
                    break;
                case 3 :
                    // ShellCommand.g:621:7: nPc= propertyCall[null, false]
                    {
                    pushFollow(FOLLOW_propertyCall_in_primaryExpression1905);
                    nPc=propertyCall(null, false);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nPc; 
                    }

                    }
                    break;
                case 4 :
                    // ShellCommand.g:622:7: LPAREN nExp= expression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression1916); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_primaryExpression1920);
                    nExp=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression1922); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (nExp!=null?nExp.n:null); 
                    }

                    }
                    break;
                case 5 :
                    // ShellCommand.g:623:7: nIfExp= ifExpression
                    {
                    pushFollow(FOLLOW_ifExpression_in_primaryExpression1934);
                    nIfExp=ifExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nIfExp; 
                    }

                    }
                    break;
                case 6 :
                    // ShellCommand.g:625:7: id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )?
                    {
                    id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_primaryExpression1951); if (state.failed) return n;
                    match(input,DOT,FOLLOW_DOT_in_primaryExpression1953); if (state.failed) return n;
                    match(input,59,FOLLOW_59_in_primaryExpression1955); if (state.failed) return n;
                    // ShellCommand.g:625:36: ( LPAREN RPAREN )?
                    int alt24=2;
                    int LA24_0 = input.LA(1);

                    if ( (LA24_0==LPAREN) ) {
                        alt24=1;
                    }
                    switch (alt24) {
                        case 1 :
                            // ShellCommand.g:625:38: LPAREN RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression1959); if (state.failed) return n;
                            match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression1961); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTAllInstancesExpression(id1); 
                    }
                    // ShellCommand.g:627:7: ( AT 'pre' )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==AT) ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // ShellCommand.g:627:9: AT 'pre'
                            {
                            match(input,AT,FOLLOW_AT_in_primaryExpression1982); if (state.failed) return n;
                            match(input,60,FOLLOW_60_in_primaryExpression1984); if (state.failed) return n;
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
    // ShellCommand.g:631:1: objectReference returns [ASTExpression n] : AT objectName= IDENT ;
    public final ASTExpression objectReference() throws RecognitionException {
        ASTExpression n = null;

        Token objectName=null;

        try {
            // ShellCommand.g:632:1: ( AT objectName= IDENT )
            // ShellCommand.g:633:3: AT objectName= IDENT
            {
            match(input,AT,FOLLOW_AT_in_objectReference2011); if (state.failed) return n;
            objectName=(Token)match(input,IDENT,FOLLOW_IDENT_in_objectReference2019); if (state.failed) return n;
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
    // ShellCommand.g:648:1: propertyCall[ASTExpression source, boolean followsArrow] returns [ASTExpression n] : ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] );
    public final ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExpQuery = null;

        ASTExpression nExpIterate = null;

        ShellCommandParser.operationExpression_return nExpOperation = null;

        ASTTypeArgExpression nExpType = null;


        try {
            // ShellCommand.g:649:1: ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] )
            int alt27=4;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA27_1 = input.LA(2);

                if ( ((( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )&&( input.LA(2) == LPAREN ))) ) {
                    alt27=1;
                }
                else if ( (true) ) {
                    alt27=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 27, 1, input);

                    throw nvae;
                }
                }
                break;
            case 61:
                {
                alt27=2;
                }
                break;
            case 62:
            case 63:
            case 64:
                {
                alt27=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }

            switch (alt27) {
                case 1 :
                    // ShellCommand.g:653:7: {...}?{...}?nExpQuery= queryExpression[source]
                    {
                    if ( !(( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) ");
                    }
                    if ( !(( input.LA(2) == LPAREN )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " input.LA(2) == LPAREN ");
                    }
                    pushFollow(FOLLOW_queryExpression_in_propertyCall2087);
                    nExpQuery=queryExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpQuery; 
                    }

                    }
                    break;
                case 2 :
                    // ShellCommand.g:656:7: nExpIterate= iterateExpression[source]
                    {
                    pushFollow(FOLLOW_iterateExpression_in_propertyCall2100);
                    nExpIterate=iterateExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpIterate; 
                    }

                    }
                    break;
                case 3 :
                    // ShellCommand.g:657:7: nExpOperation= operationExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_operationExpression_in_propertyCall2113);
                    nExpOperation=operationExpression(source, followsArrow);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (nExpOperation!=null?nExpOperation.n:null); 
                    }

                    }
                    break;
                case 4 :
                    // ShellCommand.g:658:7: nExpType= typeExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_typeExpression_in_propertyCall2126);
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
    // ShellCommand.g:667:1: queryExpression[ASTExpression range] returns [ASTExpression n] : op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN ;
    public final ASTExpression queryExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTElemVarsDeclaration decls = null;

        ShellCommandParser.expression_return nExp = null;


        ASTElemVarsDeclaration decl = new ASTElemVarsDeclaration(); 
        try {
            // ShellCommand.g:668:69: (op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN )
            // ShellCommand.g:669:5: op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN
            {
            op=(Token)match(input,IDENT,FOLLOW_IDENT_in_queryExpression2161); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_queryExpression2168); if (state.failed) return n;
            // ShellCommand.g:671:5: (decls= elemVarsDeclaration BAR )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==IDENT) ) {
                int LA28_1 = input.LA(2);

                if ( (LA28_1==COLON||LA28_1==COMMA||LA28_1==BAR) ) {
                    alt28=1;
                }
            }
            switch (alt28) {
                case 1 :
                    // ShellCommand.g:671:7: decls= elemVarsDeclaration BAR
                    {
                    pushFollow(FOLLOW_elemVarsDeclaration_in_queryExpression2179);
                    decls=elemVarsDeclaration();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      decl = decls;
                    }
                    match(input,BAR,FOLLOW_BAR_in_queryExpression2183); if (state.failed) return n;

                    }
                    break;

            }

            pushFollow(FOLLOW_expression_in_queryExpression2194);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_queryExpression2200); if (state.failed) return n;
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
    // ShellCommand.g:685:1: iterateExpression[ASTExpression range] returns [ASTExpression n] : i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN ;
    public final ASTExpression iterateExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTElemVarsDeclaration decls = null;

        ASTVariableInitialization init = null;

        ShellCommandParser.expression_return nExp = null;


        try {
            // ShellCommand.g:685:65: (i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN )
            // ShellCommand.g:686:5: i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN
            {
            i=(Token)match(input,61,FOLLOW_61_in_iterateExpression2232); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_iterateExpression2238); if (state.failed) return n;
            pushFollow(FOLLOW_elemVarsDeclaration_in_iterateExpression2246);
            decls=elemVarsDeclaration();

            state._fsp--;
            if (state.failed) return n;
            match(input,SEMI,FOLLOW_SEMI_in_iterateExpression2248); if (state.failed) return n;
            pushFollow(FOLLOW_variableInitialization_in_iterateExpression2256);
            init=variableInitialization();

            state._fsp--;
            if (state.failed) return n;
            match(input,BAR,FOLLOW_BAR_in_iterateExpression2258); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_iterateExpression2266);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_iterateExpression2272); if (state.failed) return n;
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
    // ShellCommand.g:707:1: operationExpression[ASTExpression source, boolean followsArrow] returns [ASTOperationExpression n] : name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? ;
    public final ShellCommandParser.operationExpression_return operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ShellCommandParser.operationExpression_return retval = new ShellCommandParser.operationExpression_return();
        retval.start = input.LT(1);

        Token name=null;
        Token rolename=null;
        ShellCommandParser.expression_return e = null;


        try {
            // ShellCommand.g:709:1: (name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? )
            // ShellCommand.g:710:5: name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression2316); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
               retval.n = new ASTOperationExpression(name, source, followsArrow); 
            }
            // ShellCommand.g:713:5: ( LBRACK rolename= IDENT RBRACK )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==LBRACK) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // ShellCommand.g:713:7: LBRACK rolename= IDENT RBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_operationExpression2332); if (state.failed) return retval;
                    rolename=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression2336); if (state.failed) return retval;
                    match(input,RBRACK,FOLLOW_RBRACK_in_operationExpression2338); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n.setExplicitRolename(rolename); 
                    }

                    }
                    break;

            }

            // ShellCommand.g:715:5: ( AT 'pre' )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==AT) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // ShellCommand.g:715:7: AT 'pre'
                    {
                    match(input,AT,FOLLOW_AT_in_operationExpression2351); if (state.failed) return retval;
                    match(input,60,FOLLOW_60_in_operationExpression2353); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n.setIsPre(); 
                    }

                    }
                    break;

            }

            // ShellCommand.g:716:5: ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==LPAREN) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // ShellCommand.g:717:7: LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_operationExpression2374); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n.hasParentheses(); 
                    }
                    // ShellCommand.g:718:7: (e= expression ( COMMA e= expression )* )?
                    int alt32=2;
                    int LA32_0 = input.LA(1);

                    if ( ((LA32_0>=IDENT && LA32_0<=LPAREN)||(LA32_0>=PLUS && LA32_0<=MINUS)||LA32_0==AT||(LA32_0>=INT && LA32_0<=HASH)||LA32_0==48||LA32_0==58||(LA32_0>=61 && LA32_0<=65)||(LA32_0>=69 && LA32_0<=80)) ) {
                        alt32=1;
                    }
                    switch (alt32) {
                        case 1 :
                            // ShellCommand.g:719:7: e= expression ( COMMA e= expression )*
                            {
                            pushFollow(FOLLOW_expression_in_operationExpression2395);
                            e=expression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                               retval.n.addArg((e!=null?e.n:null)); 
                            }
                            // ShellCommand.g:720:7: ( COMMA e= expression )*
                            loop31:
                            do {
                                int alt31=2;
                                int LA31_0 = input.LA(1);

                                if ( (LA31_0==COMMA) ) {
                                    alt31=1;
                                }


                                switch (alt31) {
                            	case 1 :
                            	    // ShellCommand.g:720:9: COMMA e= expression
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_operationExpression2407); if (state.failed) return retval;
                            	    pushFollow(FOLLOW_expression_in_operationExpression2411);
                            	    e=expression();

                            	    state._fsp--;
                            	    if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) {
                            	       retval.n.addArg((e!=null?e.n:null)); 
                            	    }

                            	    }
                            	    break;

                            	default :
                            	    break loop31;
                                }
                            } while (true);


                            }
                            break;

                    }

                    match(input,RPAREN,FOLLOW_RPAREN_in_operationExpression2431); if (state.failed) return retval;

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
    // ShellCommand.g:733:1: typeExpression[ASTExpression source, boolean followsArrow] returns [ASTTypeArgExpression n] : ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN ;
    public final ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTTypeArgExpression n = null;

        ASTType t = null;


         Token opToken = null; 
        try {
            // ShellCommand.g:736:1: ( ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN )
            // ShellCommand.g:737:2: ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN
            {
            if ( state.backtracking==0 ) {
               opToken = input.LT(1); 
            }
            if ( (input.LA(1)>=62 && input.LA(1)<=64) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_typeExpression2496); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_typeExpression2500);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_typeExpression2502); if (state.failed) return n;
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
    // ShellCommand.g:748:1: elemVarsDeclaration returns [ASTElemVarsDeclaration n] : idListRes= idList ( COLON t= type )? ;
    public final ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException {
        ASTElemVarsDeclaration n = null;

        List idListRes = null;

        ASTType t = null;


         List idList; 
        try {
            // ShellCommand.g:750:1: (idListRes= idList ( COLON t= type )? )
            // ShellCommand.g:751:5: idListRes= idList ( COLON t= type )?
            {
            pushFollow(FOLLOW_idList_in_elemVarsDeclaration2541);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            // ShellCommand.g:752:5: ( COLON t= type )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==COLON) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // ShellCommand.g:752:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_elemVarsDeclaration2549); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_elemVarsDeclaration2553);
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
    // ShellCommand.g:761:1: variableInitialization returns [ASTVariableInitialization n] : name= IDENT COLON t= type EQUAL e= expression ;
    public final ASTVariableInitialization variableInitialization() throws RecognitionException {
        ASTVariableInitialization n = null;

        Token name=null;
        ASTType t = null;

        ShellCommandParser.expression_return e = null;


        try {
            // ShellCommand.g:762:1: (name= IDENT COLON t= type EQUAL e= expression )
            // ShellCommand.g:763:5: name= IDENT COLON t= type EQUAL e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableInitialization2588); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableInitialization2590); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableInitialization2594);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EQUAL,FOLLOW_EQUAL_in_variableInitialization2596); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_variableInitialization2600);
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
    // ShellCommand.g:772:1: ifExpression returns [ASTExpression n] : i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' ;
    public final ASTExpression ifExpression() throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ShellCommandParser.expression_return cond = null;

        ShellCommandParser.expression_return t = null;

        ShellCommandParser.expression_return e = null;


        try {
            // ShellCommand.g:773:1: (i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' )
            // ShellCommand.g:774:5: i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif'
            {
            i=(Token)match(input,65,FOLLOW_65_in_ifExpression2632); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression2636);
            cond=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,66,FOLLOW_66_in_ifExpression2638); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression2642);
            t=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,67,FOLLOW_67_in_ifExpression2644); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression2648);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,68,FOLLOW_68_in_ifExpression2650); if (state.failed) return n;
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
    // ShellCommand.g:794:1: literal returns [ASTExpression n] : (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral );
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
            // ShellCommand.g:795:1: (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral )
            int alt35=12;
            switch ( input.LA(1) ) {
            case 69:
                {
                alt35=1;
                }
                break;
            case 70:
                {
                alt35=2;
                }
                break;
            case INT:
                {
                alt35=3;
                }
                break;
            case REAL:
                {
                alt35=4;
                }
                break;
            case STRING:
                {
                alt35=5;
                }
                break;
            case HASH:
                {
                alt35=6;
                }
                break;
            case IDENT:
                {
                alt35=7;
                }
                break;
            case 71:
            case 72:
            case 73:
            case 74:
                {
                alt35=8;
                }
                break;
            case 75:
                {
                alt35=9;
                }
                break;
            case 76:
            case 77:
            case 78:
                {
                alt35=10;
                }
                break;
            case 79:
                {
                alt35=11;
                }
                break;
            case 80:
                {
                alt35=12;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;
            }

            switch (alt35) {
                case 1 :
                    // ShellCommand.g:796:7: t= 'true'
                    {
                    t=(Token)match(input,69,FOLLOW_69_in_literal2689); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(true); 
                    }

                    }
                    break;
                case 2 :
                    // ShellCommand.g:797:7: f= 'false'
                    {
                    f=(Token)match(input,70,FOLLOW_70_in_literal2703); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(false); 
                    }

                    }
                    break;
                case 3 :
                    // ShellCommand.g:798:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_literal2716); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTIntegerLiteral(i); 
                    }

                    }
                    break;
                case 4 :
                    // ShellCommand.g:799:7: r= REAL
                    {
                    r=(Token)match(input,REAL,FOLLOW_REAL_in_literal2731); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTRealLiteral(r); 
                    }

                    }
                    break;
                case 5 :
                    // ShellCommand.g:800:7: s= STRING
                    {
                    s=(Token)match(input,STRING,FOLLOW_STRING_in_literal2745); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTStringLiteral(s); 
                    }

                    }
                    break;
                case 6 :
                    // ShellCommand.g:801:7: HASH enumLit= IDENT
                    {
                    match(input,HASH,FOLLOW_HASH_in_literal2755); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal2759); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumLit);
                    }

                    }
                    break;
                case 7 :
                    // ShellCommand.g:802:7: enumName= IDENT '::' enumLit= IDENT
                    {
                    enumName=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal2771); if (state.failed) return n;
                    match(input,COLON_COLON,FOLLOW_COLON_COLON_in_literal2773); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal2777); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumName, enumLit); 
                    }

                    }
                    break;
                case 8 :
                    // ShellCommand.g:803:7: nColIt= collectionLiteral
                    {
                    pushFollow(FOLLOW_collectionLiteral_in_literal2789);
                    nColIt=collectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nColIt; 
                    }

                    }
                    break;
                case 9 :
                    // ShellCommand.g:804:7: nEColIt= emptyCollectionLiteral
                    {
                    pushFollow(FOLLOW_emptyCollectionLiteral_in_literal2801);
                    nEColIt=emptyCollectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nEColIt; 
                    }

                    }
                    break;
                case 10 :
                    // ShellCommand.g:805:7: nUndLit= undefinedLiteral
                    {
                    pushFollow(FOLLOW_undefinedLiteral_in_literal2813);
                    nUndLit=undefinedLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nUndLit; 
                    }

                    }
                    break;
                case 11 :
                    // ShellCommand.g:806:7: nTupleLit= tupleLiteral
                    {
                    pushFollow(FOLLOW_tupleLiteral_in_literal2825);
                    nTupleLit=tupleLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nTupleLit; 
                    }

                    }
                    break;
                case 12 :
                    // ShellCommand.g:807:7: nDateLit= dateLiteral
                    {
                    pushFollow(FOLLOW_dateLiteral_in_literal2837);
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
    // ShellCommand.g:815:1: collectionLiteral returns [ASTCollectionLiteral n] : ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE ;
    public final ASTCollectionLiteral collectionLiteral() throws RecognitionException {
        ASTCollectionLiteral n = null;

        ASTCollectionItem ci = null;


         Token op = null; 
        try {
            // ShellCommand.g:817:1: ( ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE )
            // ShellCommand.g:818:5: ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE
            {
            if ( state.backtracking==0 ) {
               op = input.LT(1); 
            }
            if ( (input.LA(1)>=71 && input.LA(1)<=74) ) {
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
            match(input,LBRACE,FOLLOW_LBRACE_in_collectionLiteral2904); if (state.failed) return n;
            // ShellCommand.g:822:5: (ci= collectionItem ( COMMA ci= collectionItem )* )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( ((LA37_0>=IDENT && LA37_0<=LPAREN)||(LA37_0>=PLUS && LA37_0<=MINUS)||LA37_0==AT||(LA37_0>=INT && LA37_0<=HASH)||LA37_0==48||LA37_0==58||(LA37_0>=61 && LA37_0<=65)||(LA37_0>=69 && LA37_0<=80)) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // ShellCommand.g:823:7: ci= collectionItem ( COMMA ci= collectionItem )*
                    {
                    pushFollow(FOLLOW_collectionItem_in_collectionLiteral2921);
                    ci=collectionItem();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addItem(ci); 
                    }
                    // ShellCommand.g:824:7: ( COMMA ci= collectionItem )*
                    loop36:
                    do {
                        int alt36=2;
                        int LA36_0 = input.LA(1);

                        if ( (LA36_0==COMMA) ) {
                            alt36=1;
                        }


                        switch (alt36) {
                    	case 1 :
                    	    // ShellCommand.g:824:9: COMMA ci= collectionItem
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_collectionLiteral2934); if (state.failed) return n;
                    	    pushFollow(FOLLOW_collectionItem_in_collectionLiteral2938);
                    	    ci=collectionItem();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addItem(ci); 
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

            match(input,RBRACE,FOLLOW_RBRACE_in_collectionLiteral2957); if (state.failed) return n;

            }

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
    // ShellCommand.g:833:1: collectionItem returns [ASTCollectionItem n] : e= expression ( DOTDOT e= expression )? ;
    public final ASTCollectionItem collectionItem() throws RecognitionException {
        ASTCollectionItem n = null;

        ShellCommandParser.expression_return e = null;


         n = new ASTCollectionItem(); 
        try {
            // ShellCommand.g:835:1: (e= expression ( DOTDOT e= expression )? )
            // ShellCommand.g:836:5: e= expression ( DOTDOT e= expression )?
            {
            pushFollow(FOLLOW_expression_in_collectionItem2986);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setFirst((e!=null?e.n:null)); 
            }
            // ShellCommand.g:837:5: ( DOTDOT e= expression )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==DOTDOT) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // ShellCommand.g:837:7: DOTDOT e= expression
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_collectionItem2997); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_collectionItem3001);
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
    // ShellCommand.g:847:1: emptyCollectionLiteral returns [ASTEmptyCollectionLiteral n] : 'oclEmpty' LPAREN t= collectionType RPAREN ;
    public final ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException {
        ASTEmptyCollectionLiteral n = null;

        ASTCollectionType t = null;


        try {
            // ShellCommand.g:848:1: ( 'oclEmpty' LPAREN t= collectionType RPAREN )
            // ShellCommand.g:849:5: 'oclEmpty' LPAREN t= collectionType RPAREN
            {
            match(input,75,FOLLOW_75_in_emptyCollectionLiteral3030); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_emptyCollectionLiteral3032); if (state.failed) return n;
            pushFollow(FOLLOW_collectionType_in_emptyCollectionLiteral3036);
            t=collectionType();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_emptyCollectionLiteral3038); if (state.failed) return n;
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
    // ShellCommand.g:860:1: undefinedLiteral returns [ASTUndefinedLiteral n] : ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' );
    public final ASTUndefinedLiteral undefinedLiteral() throws RecognitionException {
        ASTUndefinedLiteral n = null;

        ASTType t = null;


        try {
            // ShellCommand.g:861:1: ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' )
            int alt39=3;
            switch ( input.LA(1) ) {
            case 76:
                {
                alt39=1;
                }
                break;
            case 77:
                {
                alt39=2;
                }
                break;
            case 78:
                {
                alt39=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 39, 0, input);

                throw nvae;
            }

            switch (alt39) {
                case 1 :
                    // ShellCommand.g:862:5: 'oclUndefined' LPAREN t= type RPAREN
                    {
                    match(input,76,FOLLOW_76_in_undefinedLiteral3068); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_undefinedLiteral3070); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_undefinedLiteral3074);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_undefinedLiteral3076); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(t); 
                    }

                    }
                    break;
                case 2 :
                    // ShellCommand.g:865:5: 'Undefined'
                    {
                    match(input,77,FOLLOW_77_in_undefinedLiteral3090); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(); 
                    }

                    }
                    break;
                case 3 :
                    // ShellCommand.g:868:5: 'null'
                    {
                    match(input,78,FOLLOW_78_in_undefinedLiteral3104); if (state.failed) return n;
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
    // ShellCommand.g:877:1: tupleLiteral returns [ASTTupleLiteral n] : 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE ;
    public final ASTTupleLiteral tupleLiteral() throws RecognitionException {
        ASTTupleLiteral n = null;

        ASTTupleItem ti = null;


         List tiList = new ArrayList(); 
        try {
            // ShellCommand.g:879:1: ( 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE )
            // ShellCommand.g:880:5: 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE
            {
            match(input,79,FOLLOW_79_in_tupleLiteral3138); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_tupleLiteral3144); if (state.failed) return n;
            pushFollow(FOLLOW_tupleItem_in_tupleLiteral3152);
            ti=tupleItem();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tiList.add(ti); 
            }
            // ShellCommand.g:883:5: ( COMMA ti= tupleItem )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==COMMA) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // ShellCommand.g:883:7: COMMA ti= tupleItem
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleLiteral3163); if (state.failed) return n;
            	    pushFollow(FOLLOW_tupleItem_in_tupleLiteral3167);
            	    ti=tupleItem();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tiList.add(ti); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop40;
                }
            } while (true);

            match(input,RBRACE,FOLLOW_RBRACE_in_tupleLiteral3178); if (state.failed) return n;
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
    // ShellCommand.g:891:1: tupleItem returns [ASTTupleItem n] : name= IDENT ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) ;
    public final ASTTupleItem tupleItem() throws RecognitionException {
        ASTTupleItem n = null;

        Token name=null;
        ASTType t = null;

        ShellCommandParser.expression_return e = null;


        try {
            // ShellCommand.g:892:1: (name= IDENT ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) )
            // ShellCommand.g:893:5: name= IDENT ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tupleItem3209); if (state.failed) return n;
            // ShellCommand.g:894:5: ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==COLON) ) {
                int LA41_1 = input.LA(2);

                if ( (synpred3_ShellCommand()) ) {
                    alt41=1;
                }
                else if ( (true) ) {
                    alt41=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 41, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA41_0==EQUAL) ) {
                alt41=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 41, 0, input);

                throw nvae;
            }
            switch (alt41) {
                case 1 :
                    // ShellCommand.g:897:7: ( COLON type EQUAL )=> COLON t= type EQUAL e= expression
                    {
                    match(input,COLON,FOLLOW_COLON_in_tupleItem3248); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_tupleItem3252);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,EQUAL,FOLLOW_EQUAL_in_tupleItem3254); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_tupleItem3258);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTTupleItem(name, t, (e!=null?e.n:null)); 
                    }

                    }
                    break;
                case 2 :
                    // ShellCommand.g:900:7: ( COLON | EQUAL ) e= expression
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

                    pushFollow(FOLLOW_expression_in_tupleItem3290);
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
    // ShellCommand.g:909:1: dateLiteral returns [ASTDateLiteral n] : 'Date' LBRACE v= STRING RBRACE ;
    public final ASTDateLiteral dateLiteral() throws RecognitionException {
        ASTDateLiteral n = null;

        Token v=null;

        try {
            // ShellCommand.g:910:1: ( 'Date' LBRACE v= STRING RBRACE )
            // ShellCommand.g:911:5: 'Date' LBRACE v= STRING RBRACE
            {
            match(input,80,FOLLOW_80_in_dateLiteral3335); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_dateLiteral3337); if (state.failed) return n;
            v=(Token)match(input,STRING,FOLLOW_STRING_in_dateLiteral3341); if (state.failed) return n;
            match(input,RBRACE,FOLLOW_RBRACE_in_dateLiteral3343); if (state.failed) return n;
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
    // ShellCommand.g:921:1: type returns [ASTType n] : (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) ;
    public final ASTType type() throws RecognitionException {
        ASTType n = null;

        ASTSimpleType nTSimple = null;

        ASTCollectionType nTCollection = null;

        ASTTupleType nTTuple = null;


         Token tok = null; 
        try {
            // ShellCommand.g:923:1: ( (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) )
            // ShellCommand.g:924:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of type */ 
            }
            // ShellCommand.g:925:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            int alt42=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt42=1;
                }
                break;
            case 71:
            case 72:
            case 73:
            case 74:
            case 81:
                {
                alt42=2;
                }
                break;
            case 79:
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
                    // ShellCommand.g:926:7: nTSimple= simpleType
                    {
                    pushFollow(FOLLOW_simpleType_in_type3393);
                    nTSimple=simpleType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTSimple; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 2 :
                    // ShellCommand.g:927:7: nTCollection= collectionType
                    {
                    pushFollow(FOLLOW_collectionType_in_type3405);
                    nTCollection=collectionType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTCollection; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 3 :
                    // ShellCommand.g:928:7: nTTuple= tupleType
                    {
                    pushFollow(FOLLOW_tupleType_in_type3417);
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
    // ShellCommand.g:933:1: typeOnly returns [ASTType n] : nT= type EOF ;
    public final ASTType typeOnly() throws RecognitionException {
        ASTType n = null;

        ASTType nT = null;


        try {
            // ShellCommand.g:934:1: (nT= type EOF )
            // ShellCommand.g:935:5: nT= type EOF
            {
            pushFollow(FOLLOW_type_in_typeOnly3449);
            nT=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_typeOnly3451); if (state.failed) return n;
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
    // ShellCommand.g:945:1: simpleType returns [ASTSimpleType n] : name= IDENT ;
    public final ASTSimpleType simpleType() throws RecognitionException {
        ASTSimpleType n = null;

        Token name=null;

        try {
            // ShellCommand.g:946:1: (name= IDENT )
            // ShellCommand.g:947:5: name= IDENT
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_simpleType3479); if (state.failed) return n;
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
    // ShellCommand.g:955:1: collectionType returns [ASTCollectionType n] : ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN ;
    public final ASTCollectionType collectionType() throws RecognitionException {
        ASTCollectionType n = null;

        ASTType elemType = null;


         Token op = null; 
        try {
            // ShellCommand.g:957:1: ( ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN )
            // ShellCommand.g:958:5: ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN
            {
            if ( state.backtracking==0 ) {
               op = input.LT(1); 
            }
            if ( (input.LA(1)>=71 && input.LA(1)<=74)||input.LA(1)==81 ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_collectionType3544); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_collectionType3548);
            elemType=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_collectionType3550); if (state.failed) return n;
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
    // ShellCommand.g:968:1: tupleType returns [ASTTupleType n] : 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN ;
    public final ASTTupleType tupleType() throws RecognitionException {
        ASTTupleType n = null;

        ASTTuplePart tp = null;


         List tpList = new ArrayList(); 
        try {
            // ShellCommand.g:970:1: ( 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN )
            // ShellCommand.g:971:5: 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN
            {
            match(input,79,FOLLOW_79_in_tupleType3584); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_tupleType3586); if (state.failed) return n;
            pushFollow(FOLLOW_tuplePart_in_tupleType3595);
            tp=tuplePart();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tpList.add(tp); 
            }
            // ShellCommand.g:973:5: ( COMMA tp= tuplePart )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==COMMA) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // ShellCommand.g:973:7: COMMA tp= tuplePart
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleType3606); if (state.failed) return n;
            	    pushFollow(FOLLOW_tuplePart_in_tupleType3610);
            	    tp=tuplePart();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tpList.add(tp); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop43;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_tupleType3622); if (state.failed) return n;
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
    // ShellCommand.g:982:1: tuplePart returns [ASTTuplePart n] : name= IDENT COLON t= type ;
    public final ASTTuplePart tuplePart() throws RecognitionException {
        ASTTuplePart n = null;

        Token name=null;
        ASTType t = null;


        try {
            // ShellCommand.g:983:1: (name= IDENT COLON t= type )
            // ShellCommand.g:984:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tuplePart3654); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_tuplePart3656); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_tuplePart3660);
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
    // ShellCommand.g:1023:1: statOnly returns [ASTStatement n] : s= stat EOF ;
    public final ASTStatement statOnly() throws RecognitionException {
        ASTStatement n = null;

        ShellCommandParser.stat_return s = null;


        try {
            // ShellCommand.g:1024:1: (s= stat EOF )
            // ShellCommand.g:1025:3: s= stat EOF
            {
            pushFollow(FOLLOW_stat_in_statOnly3709);
            s=stat();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_statOnly3713); if (state.failed) return n;
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
    // ShellCommand.g:1035:1: stat returns [ASTStatement n] : nextStat[seq] ( SEMI nextStat[seq] )* ;
    public final ShellCommandParser.stat_return stat() throws RecognitionException {
        ShellCommandParser.stat_return retval = new ShellCommandParser.stat_return();
        retval.start = input.LT(1);


          ASTSequenceStatement seq = new ASTSequenceStatement();

        try {
            // ShellCommand.g:1039:1: ( nextStat[seq] ( SEMI nextStat[seq] )* )
            // ShellCommand.g:1040:3: nextStat[seq] ( SEMI nextStat[seq] )*
            {
            pushFollow(FOLLOW_nextStat_in_stat3744);
            nextStat(seq);

            state._fsp--;
            if (state.failed) return retval;
            // ShellCommand.g:1041:3: ( SEMI nextStat[seq] )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==SEMI) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // ShellCommand.g:1042:5: SEMI nextStat[seq]
            	    {
            	    match(input,SEMI,FOLLOW_SEMI_in_stat3755); if (state.failed) return retval;
            	    pushFollow(FOLLOW_nextStat_in_stat3761);
            	    nextStat(seq);

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop44;
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
    // ShellCommand.g:1059:1: nextStat[ASTSequenceStatement seq] : s= singleStat ;
    public final ShellCommandParser.nextStat_return nextStat(ASTSequenceStatement seq) throws RecognitionException {
        ShellCommandParser.nextStat_return retval = new ShellCommandParser.nextStat_return();
        retval.start = input.LT(1);

        ASTStatement s = null;


        try {
            // ShellCommand.g:1060:1: (s= singleStat )
            // ShellCommand.g:1061:3: s= singleStat
            {
            pushFollow(FOLLOW_singleStat_in_nextStat3795);
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
    // ShellCommand.g:1073:1: singleStat returns [ASTStatement n] options {backtrack=true; memoize=true; } : (emp= emptyStat | vas= varAssignStat | aas= attAssignStat | lcs= lobjCreateStat | ocs= objCreateStat | ods= objDestroyStat | lis= lnkInsStat | lds= lnkDelStat | ces= condExStat | its= iterStat | ops= callStat );
    public final ASTStatement singleStat() throws RecognitionException {
        ASTStatement n = null;
        int singleStat_StartIndex = input.index();
        ASTEmptyStatement emp = null;

        ShellCommandParser.varAssignStat_return vas = null;

        ASTAttributeAssignmentStatement aas = null;

        ASTNewLinkObjectStatement lcs = null;

        ShellCommandParser.objCreateStat_return ocs = null;

        ShellCommandParser.objDestroyStat_return ods = null;

        ASTLinkInsertionStatement lis = null;

        ASTLinkDeletionStatement lds = null;

        ASTConditionalExecutionStatement ces = null;

        ASTIterationStatement its = null;

        ASTOperationCallStatement ops = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 54) ) { return n; }
            // ShellCommand.g:1078:1: (emp= emptyStat | vas= varAssignStat | aas= attAssignStat | lcs= lobjCreateStat | ocs= objCreateStat | ods= objDestroyStat | lis= lnkInsStat | lds= lnkDelStat | ces= condExStat | its= iterStat | ops= callStat )
            int alt45=11;
            alt45 = dfa45.predict(input);
            switch (alt45) {
                case 1 :
                    // ShellCommand.g:1079:5: emp= emptyStat
                    {
                    pushFollow(FOLLOW_emptyStat_in_singleStat3850);
                    emp=emptyStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = emp; 
                    }

                    }
                    break;
                case 2 :
                    // ShellCommand.g:1081:5: vas= varAssignStat
                    {
                    pushFollow(FOLLOW_varAssignStat_in_singleStat3871);
                    vas=varAssignStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (vas!=null?vas.n:null); 
                    }

                    }
                    break;
                case 3 :
                    // ShellCommand.g:1082:5: aas= attAssignStat
                    {
                    pushFollow(FOLLOW_attAssignStat_in_singleStat3885);
                    aas=attAssignStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = aas; 
                    }

                    }
                    break;
                case 4 :
                    // ShellCommand.g:1083:5: lcs= lobjCreateStat
                    {
                    pushFollow(FOLLOW_lobjCreateStat_in_singleStat3899);
                    lcs=lobjCreateStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = lcs; 
                    }

                    }
                    break;
                case 5 :
                    // ShellCommand.g:1084:5: ocs= objCreateStat
                    {
                    pushFollow(FOLLOW_objCreateStat_in_singleStat3912);
                    ocs=objCreateStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (ocs!=null?ocs.n:null); 
                    }

                    }
                    break;
                case 6 :
                    // ShellCommand.g:1085:5: ods= objDestroyStat
                    {
                    pushFollow(FOLLOW_objDestroyStat_in_singleStat3926);
                    ods=objDestroyStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (ods!=null?ods.n:null); 
                    }

                    }
                    break;
                case 7 :
                    // ShellCommand.g:1086:5: lis= lnkInsStat
                    {
                    pushFollow(FOLLOW_lnkInsStat_in_singleStat3939);
                    lis=lnkInsStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = lis; 
                    }

                    }
                    break;
                case 8 :
                    // ShellCommand.g:1087:5: lds= lnkDelStat
                    {
                    pushFollow(FOLLOW_lnkDelStat_in_singleStat3956);
                    lds=lnkDelStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = lds; 
                    }

                    }
                    break;
                case 9 :
                    // ShellCommand.g:1088:5: ces= condExStat
                    {
                    pushFollow(FOLLOW_condExStat_in_singleStat3973);
                    ces=condExStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = ces; 
                    }

                    }
                    break;
                case 10 :
                    // ShellCommand.g:1089:5: its= iterStat
                    {
                    pushFollow(FOLLOW_iterStat_in_singleStat3990);
                    its=iterStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = its; 
                    }

                    }
                    break;
                case 11 :
                    // ShellCommand.g:1090:5: ops= callStat
                    {
                    pushFollow(FOLLOW_callStat_in_singleStat4009);
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
            if ( state.backtracking>0 ) { memoize(input, 54, singleStat_StartIndex); }
        }
        return n;
    }
    // $ANTLR end "singleStat"


    // $ANTLR start "emptyStat"
    // ShellCommand.g:1098:1: emptyStat returns [ASTEmptyStatement n] : nothing ;
    public final ASTEmptyStatement emptyStat() throws RecognitionException {
        ASTEmptyStatement n = null;

        try {
            // ShellCommand.g:1099:1: ( nothing )
            // ShellCommand.g:1100:3: nothing
            {
            pushFollow(FOLLOW_nothing_in_emptyStat4040);
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
    // ShellCommand.g:1109:1: varAssignStat returns [ASTStatement n] options {backtrack=true; memoize=true; } : (varName= IDENT COLON_EQUAL rVal= rValue | vNames= identListMin1 COLON_EQUAL 'new' oType= simpleType ( LPAREN oNames= exprList RPAREN )? );
    public final ShellCommandParser.varAssignStat_return varAssignStat() throws RecognitionException {
        ShellCommandParser.varAssignStat_return retval = new ShellCommandParser.varAssignStat_return();
        retval.start = input.LT(1);
        int varAssignStat_StartIndex = input.index();
        Token varName=null;
        ShellCommandParser.rValue_return rVal = null;

        List<String> vNames = null;

        ASTSimpleType oType = null;

        List<ASTExpression> oNames = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 56) ) { return retval; }
            // ShellCommand.g:1114:1: (varName= IDENT COLON_EQUAL rVal= rValue | vNames= identListMin1 COLON_EQUAL 'new' oType= simpleType ( LPAREN oNames= exprList RPAREN )? )
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==IDENT) ) {
                int LA47_1 = input.LA(2);

                if ( (LA47_1==COLON_EQUAL) ) {
                    int LA47_2 = input.LA(3);

                    if ( (LA47_2==82) ) {
                        int LA47_4 = input.LA(4);

                        if ( (LA47_4==IDENT) ) {
                            int LA47_6 = input.LA(5);

                            if ( (synpred14_ShellCommand()) ) {
                                alt47=1;
                            }
                            else if ( (true) ) {
                                alt47=2;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 47, 6, input);

                                throw nvae;
                            }
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 47, 4, input);

                            throw nvae;
                        }
                    }
                    else if ( ((LA47_2>=IDENT && LA47_2<=LPAREN)||(LA47_2>=PLUS && LA47_2<=MINUS)||LA47_2==AT||(LA47_2>=INT && LA47_2<=HASH)||LA47_2==48||LA47_2==58||(LA47_2>=61 && LA47_2<=65)||(LA47_2>=69 && LA47_2<=80)) ) {
                        alt47=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 47, 2, input);

                        throw nvae;
                    }
                }
                else if ( (LA47_1==COMMA) ) {
                    alt47=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 47, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 47, 0, input);

                throw nvae;
            }
            switch (alt47) {
                case 1 :
                    // ShellCommand.g:1115:3: varName= IDENT COLON_EQUAL rVal= rValue
                    {
                    varName=(Token)match(input,IDENT,FOLLOW_IDENT_in_varAssignStat4093); if (state.failed) return retval;
                    match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_varAssignStat4097); if (state.failed) return retval;
                    pushFollow(FOLLOW_rValue_in_varAssignStat4105);
                    rVal=rValue();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n = new ASTVariableAssignmentStatement((varName!=null?varName.getText():null), (rVal!=null?rVal.n:null)); 
                    }

                    }
                    break;
                case 2 :
                    // ShellCommand.g:1121:3: vNames= identListMin1 COLON_EQUAL 'new' oType= simpleType ( LPAREN oNames= exprList RPAREN )?
                    {
                    pushFollow(FOLLOW_identListMin1_in_varAssignStat4124);
                    vNames=identListMin1();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_varAssignStat4128); if (state.failed) return retval;
                    match(input,82,FOLLOW_82_in_varAssignStat4132); if (state.failed) return retval;
                    pushFollow(FOLLOW_simpleType_in_varAssignStat4140);
                    oType=simpleType();

                    state._fsp--;
                    if (state.failed) return retval;
                    // ShellCommand.g:1125:3: ( LPAREN oNames= exprList RPAREN )?
                    int alt46=2;
                    int LA46_0 = input.LA(1);

                    if ( (LA46_0==LPAREN) ) {
                        alt46=1;
                    }
                    switch (alt46) {
                        case 1 :
                            // ShellCommand.g:1126:5: LPAREN oNames= exprList RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_varAssignStat4150); if (state.failed) return retval;
                            pushFollow(FOLLOW_exprList_in_varAssignStat4162);
                            oNames=exprList();

                            state._fsp--;
                            if (state.failed) return retval;
                            match(input,RPAREN,FOLLOW_RPAREN_in_varAssignStat4168); if (state.failed) return retval;

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
            if ( state.backtracking>0 ) { memoize(input, 56, varAssignStat_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "varAssignStat"


    // $ANTLR start "attAssignStat"
    // ShellCommand.g:1184:1: attAssignStat returns [ASTAttributeAssignmentStatement n] : obj= inSoilExpression DOT attName= IDENT COLON_EQUAL r= rValue ;
    public final ASTAttributeAssignmentStatement attAssignStat() throws RecognitionException {
        ASTAttributeAssignmentStatement n = null;

        Token attName=null;
        ASTExpression obj = null;

        ShellCommandParser.rValue_return r = null;


        try {
            // ShellCommand.g:1185:1: (obj= inSoilExpression DOT attName= IDENT COLON_EQUAL r= rValue )
            // ShellCommand.g:1186:3: obj= inSoilExpression DOT attName= IDENT COLON_EQUAL r= rValue
            {
            pushFollow(FOLLOW_inSoilExpression_in_attAssignStat4203);
            obj=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            match(input,DOT,FOLLOW_DOT_in_attAssignStat4208); if (state.failed) return n;
            attName=(Token)match(input,IDENT,FOLLOW_IDENT_in_attAssignStat4217); if (state.failed) return n;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_attAssignStat4221); if (state.failed) return n;
            pushFollow(FOLLOW_rValue_in_attAssignStat4229);
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
    // ShellCommand.g:1199:1: objCreateStat returns [ASTStatement n] : 'new' objType= simpleType ( LPAREN objNames= exprListMin1 RPAREN )? ;
    public final ShellCommandParser.objCreateStat_return objCreateStat() throws RecognitionException {
        ShellCommandParser.objCreateStat_return retval = new ShellCommandParser.objCreateStat_return();
        retval.start = input.LT(1);

        ASTSimpleType objType = null;

        List<ASTExpression> objNames = null;


        try {
            // ShellCommand.g:1200:1: ( 'new' objType= simpleType ( LPAREN objNames= exprListMin1 RPAREN )? )
            // ShellCommand.g:1201:3: 'new' objType= simpleType ( LPAREN objNames= exprListMin1 RPAREN )?
            {
            match(input,82,FOLLOW_82_in_objCreateStat4255); if (state.failed) return retval;
            pushFollow(FOLLOW_simpleType_in_objCreateStat4263);
            objType=simpleType();

            state._fsp--;
            if (state.failed) return retval;
            // ShellCommand.g:1203:3: ( LPAREN objNames= exprListMin1 RPAREN )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==LPAREN) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // ShellCommand.g:1204:5: LPAREN objNames= exprListMin1 RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_objCreateStat4273); if (state.failed) return retval;
                    pushFollow(FOLLOW_exprListMin1_in_objCreateStat4285);
                    objNames=exprListMin1();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,RPAREN,FOLLOW_RPAREN_in_objCreateStat4291); if (state.failed) return retval;

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
    // ShellCommand.g:1231:1: lobjCreateStat returns [ASTNewLinkObjectStatement n] : 'new' assoc= IDENT ( LPAREN name= inSoilExpression RPAREN )? 'between' LPAREN p= rValListMin2 RPAREN ;
    public final ASTNewLinkObjectStatement lobjCreateStat() throws RecognitionException {
        ASTNewLinkObjectStatement n = null;

        Token assoc=null;
        ASTExpression name = null;

        List<ASTRValue> p = null;


        try {
            // ShellCommand.g:1232:1: ( 'new' assoc= IDENT ( LPAREN name= inSoilExpression RPAREN )? 'between' LPAREN p= rValListMin2 RPAREN )
            // ShellCommand.g:1233:3: 'new' assoc= IDENT ( LPAREN name= inSoilExpression RPAREN )? 'between' LPAREN p= rValListMin2 RPAREN
            {
            match(input,82,FOLLOW_82_in_lobjCreateStat4322); if (state.failed) return n;
            assoc=(Token)match(input,IDENT,FOLLOW_IDENT_in_lobjCreateStat4330); if (state.failed) return n;
            // ShellCommand.g:1235:3: ( LPAREN name= inSoilExpression RPAREN )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==LPAREN) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // ShellCommand.g:1236:5: LPAREN name= inSoilExpression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_lobjCreateStat4340); if (state.failed) return n;
                    pushFollow(FOLLOW_inSoilExpression_in_lobjCreateStat4352);
                    name=inSoilExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_lobjCreateStat4358); if (state.failed) return n;

                    }
                    break;

            }

            match(input,47,FOLLOW_47_in_lobjCreateStat4367); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_lobjCreateStat4371); if (state.failed) return n;
            pushFollow(FOLLOW_rValListMin2_in_lobjCreateStat4381);
            p=rValListMin2();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_lobjCreateStat4385); if (state.failed) return n;
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
    // ShellCommand.g:1256:1: objDestroyStat returns [ASTStatement n] : 'destroy' el= exprListMin1 ;
    public final ShellCommandParser.objDestroyStat_return objDestroyStat() throws RecognitionException {
        ShellCommandParser.objDestroyStat_return retval = new ShellCommandParser.objDestroyStat_return();
        retval.start = input.LT(1);

        List<ASTExpression> el = null;


        try {
            // ShellCommand.g:1257:1: ( 'destroy' el= exprListMin1 )
            // ShellCommand.g:1258:3: 'destroy' el= exprListMin1
            {
            match(input,83,FOLLOW_83_in_objDestroyStat4411); if (state.failed) return retval;
            pushFollow(FOLLOW_exprListMin1_in_objDestroyStat4419);
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
    // ShellCommand.g:1279:1: lnkInsStat returns [ASTLinkInsertionStatement n] : 'insert' LPAREN p= rValListMin2 RPAREN 'into' ass= IDENT ;
    public final ASTLinkInsertionStatement lnkInsStat() throws RecognitionException {
        ASTLinkInsertionStatement n = null;

        Token ass=null;
        List<ASTRValue> p = null;


        try {
            // ShellCommand.g:1280:1: ( 'insert' LPAREN p= rValListMin2 RPAREN 'into' ass= IDENT )
            // ShellCommand.g:1281:3: 'insert' LPAREN p= rValListMin2 RPAREN 'into' ass= IDENT
            {
            match(input,84,FOLLOW_84_in_lnkInsStat4445); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_lnkInsStat4449); if (state.failed) return n;
            pushFollow(FOLLOW_rValListMin2_in_lnkInsStat4459);
            p=rValListMin2();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_lnkInsStat4463); if (state.failed) return n;
            match(input,85,FOLLOW_85_in_lnkInsStat4467); if (state.failed) return n;
            ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_lnkInsStat4475); if (state.failed) return n;
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
    // ShellCommand.g:1295:1: lnkDelStat returns [ASTLinkDeletionStatement n] : 'delete' LPAREN p= rValListMin2 RPAREN 'from' ass= IDENT ;
    public final ASTLinkDeletionStatement lnkDelStat() throws RecognitionException {
        ASTLinkDeletionStatement n = null;

        Token ass=null;
        List<ASTRValue> p = null;


        try {
            // ShellCommand.g:1296:1: ( 'delete' LPAREN p= rValListMin2 RPAREN 'from' ass= IDENT )
            // ShellCommand.g:1297:3: 'delete' LPAREN p= rValListMin2 RPAREN 'from' ass= IDENT
            {
            match(input,86,FOLLOW_86_in_lnkDelStat4499); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_lnkDelStat4503); if (state.failed) return n;
            pushFollow(FOLLOW_rValListMin2_in_lnkDelStat4513);
            p=rValListMin2();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_lnkDelStat4517); if (state.failed) return n;
            match(input,87,FOLLOW_87_in_lnkDelStat4521); if (state.failed) return n;
            ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_lnkDelStat4530); if (state.failed) return n;
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
    // ShellCommand.g:1311:1: condExStat returns [ASTConditionalExecutionStatement n] : 'if' con= inSoilExpression 'then' ts= stat ( 'else' es= stat )? 'end' ;
    public final ASTConditionalExecutionStatement condExStat() throws RecognitionException {
        ASTConditionalExecutionStatement n = null;

        ASTExpression con = null;

        ShellCommandParser.stat_return ts = null;

        ShellCommandParser.stat_return es = null;



          ASTStatement elseStat = new ASTEmptyStatement();

        try {
            // ShellCommand.g:1315:1: ( 'if' con= inSoilExpression 'then' ts= stat ( 'else' es= stat )? 'end' )
            // ShellCommand.g:1316:3: 'if' con= inSoilExpression 'then' ts= stat ( 'else' es= stat )? 'end'
            {
            match(input,65,FOLLOW_65_in_condExStat4561); if (state.failed) return n;
            pushFollow(FOLLOW_inSoilExpression_in_condExStat4570);
            con=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            match(input,66,FOLLOW_66_in_condExStat4574); if (state.failed) return n;
            pushFollow(FOLLOW_stat_in_condExStat4583);
            ts=stat();

            state._fsp--;
            if (state.failed) return n;
            // ShellCommand.g:1320:3: ( 'else' es= stat )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==67) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // ShellCommand.g:1321:5: 'else' es= stat
                    {
                    match(input,67,FOLLOW_67_in_condExStat4594); if (state.failed) return n;
                    pushFollow(FOLLOW_stat_in_condExStat4606);
                    es=stat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       elseStat = (es!=null?es.n:null); 
                    }

                    }
                    break;

            }

            match(input,88,FOLLOW_88_in_condExStat4617); if (state.failed) return n;
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
    // ShellCommand.g:1332:1: iterStat returns [ASTIterationStatement n] : 'for' var= IDENT 'in' set= inSoilExpression 'do' s= stat 'end' ;
    public final ASTIterationStatement iterStat() throws RecognitionException {
        ASTIterationStatement n = null;

        Token var=null;
        ASTExpression set = null;

        ShellCommandParser.stat_return s = null;


        try {
            // ShellCommand.g:1333:1: ( 'for' var= IDENT 'in' set= inSoilExpression 'do' s= stat 'end' )
            // ShellCommand.g:1334:3: 'for' var= IDENT 'in' set= inSoilExpression 'do' s= stat 'end'
            {
            match(input,89,FOLLOW_89_in_iterStat4642); if (state.failed) return n;
            var=(Token)match(input,IDENT,FOLLOW_IDENT_in_iterStat4650); if (state.failed) return n;
            match(input,52,FOLLOW_52_in_iterStat4654); if (state.failed) return n;
            pushFollow(FOLLOW_inSoilExpression_in_iterStat4662);
            set=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            match(input,90,FOLLOW_90_in_iterStat4666); if (state.failed) return n;
            pushFollow(FOLLOW_stat_in_iterStat4674);
            s=stat();

            state._fsp--;
            if (state.failed) return n;
            match(input,88,FOLLOW_88_in_iterStat4678); if (state.failed) return n;
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
    // ShellCommand.g:1349:1: callStat returns [ASTOperationCallStatement n] : e= inSoilExpression ;
    public final ASTOperationCallStatement callStat() throws RecognitionException {
        ASTOperationCallStatement n = null;

        ASTExpression e = null;


        try {
            // ShellCommand.g:1350:1: (e= inSoilExpression )
            // ShellCommand.g:1351:3: e= inSoilExpression
            {
            pushFollow(FOLLOW_inSoilExpression_in_callStat4708);
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
    // ShellCommand.g:1369:1: nothing : ;
    public final void nothing() throws RecognitionException {
        try {
            // ShellCommand.g:1370:1: ()
            // ShellCommand.g:1371:1: 
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
    // ShellCommand.g:1377:1: rValue returns [ASTRValue n] options {backtrack=true; memoize=true; } : (e= inSoilExpression | loc= lobjCreateStat | 'new' objType= simpleType ( LPAREN objName= inSoilExpression RPAREN )? );
    public final ShellCommandParser.rValue_return rValue() throws RecognitionException {
        ShellCommandParser.rValue_return retval = new ShellCommandParser.rValue_return();
        retval.start = input.LT(1);
        int rValue_StartIndex = input.index();
        ASTExpression e = null;

        ASTNewLinkObjectStatement loc = null;

        ASTSimpleType objType = null;

        ASTExpression objName = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 67) ) { return retval; }
            // ShellCommand.g:1382:1: (e= inSoilExpression | loc= lobjCreateStat | 'new' objType= simpleType ( LPAREN objName= inSoilExpression RPAREN )? )
            int alt52=3;
            alt52 = dfa52.predict(input);
            switch (alt52) {
                case 1 :
                    // ShellCommand.g:1383:3: e= inSoilExpression
                    {
                    pushFollow(FOLLOW_inSoilExpression_in_rValue4780);
                    e=inSoilExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n = new ASTRValueExpressionOrOpCall(e); 
                    }

                    }
                    break;
                case 2 :
                    // ShellCommand.g:1386:3: loc= lobjCreateStat
                    {
                    pushFollow(FOLLOW_lobjCreateStat_in_rValue4796);
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
                    // ShellCommand.g:1393:3: 'new' objType= simpleType ( LPAREN objName= inSoilExpression RPAREN )?
                    {
                    match(input,82,FOLLOW_82_in_rValue4808); if (state.failed) return retval;
                    pushFollow(FOLLOW_simpleType_in_rValue4817);
                    objType=simpleType();

                    state._fsp--;
                    if (state.failed) return retval;
                    // ShellCommand.g:1395:3: ( LPAREN objName= inSoilExpression RPAREN )?
                    int alt51=2;
                    int LA51_0 = input.LA(1);

                    if ( (LA51_0==LPAREN) ) {
                        alt51=1;
                    }
                    switch (alt51) {
                        case 1 :
                            // ShellCommand.g:1396:5: LPAREN objName= inSoilExpression RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_rValue4827); if (state.failed) return retval;
                            pushFollow(FOLLOW_inSoilExpression_in_rValue4839);
                            objName=inSoilExpression();

                            state._fsp--;
                            if (state.failed) return retval;
                            match(input,RPAREN,FOLLOW_RPAREN_in_rValue4845); if (state.failed) return retval;

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
            if ( state.backtracking>0 ) { memoize(input, 67, rValue_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "rValue"


    // $ANTLR start "rValList"
    // ShellCommand.g:1415:1: rValList returns [List<ASTRValue> n] : ( nothing | rl= rValListMin1 );
    public final List<ASTRValue> rValList() throws RecognitionException {
        List<ASTRValue> n = null;

        List<ASTRValue> rl = null;


        try {
            // ShellCommand.g:1416:1: ( nothing | rl= rValListMin1 )
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==EOF) ) {
                alt53=1;
            }
            else if ( ((LA53_0>=IDENT && LA53_0<=LPAREN)||(LA53_0>=PLUS && LA53_0<=MINUS)||LA53_0==AT||(LA53_0>=INT && LA53_0<=HASH)||LA53_0==48||LA53_0==58||(LA53_0>=61 && LA53_0<=65)||(LA53_0>=69 && LA53_0<=80)||LA53_0==82) ) {
                alt53=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 53, 0, input);

                throw nvae;
            }
            switch (alt53) {
                case 1 :
                    // ShellCommand.g:1417:3: nothing
                    {
                    pushFollow(FOLLOW_nothing_in_rValList4873);
                    nothing();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ArrayList<ASTRValue>(); 
                    }

                    }
                    break;
                case 2 :
                    // ShellCommand.g:1420:3: rl= rValListMin1
                    {
                    pushFollow(FOLLOW_rValListMin1_in_rValList4900);
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
    // ShellCommand.g:1428:1: rValListMin1 returns [List<ASTRValue> n] : r= rValue ( COMMA r= rValue )* ;
    public final List<ASTRValue> rValListMin1() throws RecognitionException {
        List<ASTRValue> n = null;

        ShellCommandParser.rValue_return r = null;



          n = new ArrayList<ASTRValue>();

        try {
            // ShellCommand.g:1432:1: (r= rValue ( COMMA r= rValue )* )
            // ShellCommand.g:1433:3: r= rValue ( COMMA r= rValue )*
            {
            pushFollow(FOLLOW_rValue_in_rValListMin14933);
            r=rValue();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.add((r!=null?r.n:null)); 
            }
            // ShellCommand.g:1435:3: ( COMMA r= rValue )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==COMMA) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // ShellCommand.g:1436:5: COMMA r= rValue
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_rValListMin14947); if (state.failed) return n;
            	    pushFollow(FOLLOW_rValue_in_rValListMin14957);
            	    r=rValue();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.add((r!=null?r.n:null)); 
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
    // $ANTLR end "rValListMin1"


    // $ANTLR start "rValListMin2"
    // ShellCommand.g:1446:1: rValListMin2 returns [List<ASTRValue> n] : r= rValue COMMA r= rValue ( COMMA r= rValue )* ;
    public final List<ASTRValue> rValListMin2() throws RecognitionException {
        List<ASTRValue> n = null;

        ShellCommandParser.rValue_return r = null;



          n = new ArrayList<ASTRValue>();

        try {
            // ShellCommand.g:1450:1: (r= rValue COMMA r= rValue ( COMMA r= rValue )* )
            // ShellCommand.g:1451:3: r= rValue COMMA r= rValue ( COMMA r= rValue )*
            {
            pushFollow(FOLLOW_rValue_in_rValListMin24996);
            r=rValue();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.add((r!=null?r.n:null)); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_rValListMin25004); if (state.failed) return n;
            pushFollow(FOLLOW_rValue_in_rValListMin25012);
            r=rValue();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.add((r!=null?r.n:null)); 
            }
            // ShellCommand.g:1456:3: ( COMMA r= rValue )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==COMMA) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // ShellCommand.g:1457:5: COMMA r= rValue
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_rValListMin25026); if (state.failed) return n;
            	    pushFollow(FOLLOW_rValue_in_rValListMin25036);
            	    r=rValue();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.add((r!=null?r.n:null)); 
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
    // $ANTLR end "rValListMin2"


    // $ANTLR start "inSoilExpression"
    // ShellCommand.g:1467:1: inSoilExpression returns [ASTExpression n] : e= expression ;
    public final ASTExpression inSoilExpression() throws RecognitionException {
        ASTExpression n = null;

        ShellCommandParser.expression_return e = null;


        try {
            // ShellCommand.g:1468:1: (e= expression )
            // ShellCommand.g:1469:3: e= expression
            {
            pushFollow(FOLLOW_expression_in_inSoilExpression5070);
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
    // ShellCommand.g:1478:1: exprList returns [List<ASTExpression> n] : ( nothing | el= exprListMin1 );
    public final List<ASTExpression> exprList() throws RecognitionException {
        List<ASTExpression> n = null;

        List<ASTExpression> el = null;


        try {
            // ShellCommand.g:1479:1: ( nothing | el= exprListMin1 )
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==RPAREN) ) {
                alt56=1;
            }
            else if ( ((LA56_0>=IDENT && LA56_0<=LPAREN)||(LA56_0>=PLUS && LA56_0<=MINUS)||LA56_0==AT||(LA56_0>=INT && LA56_0<=HASH)||LA56_0==48||LA56_0==58||(LA56_0>=61 && LA56_0<=65)||(LA56_0>=69 && LA56_0<=80)) ) {
                alt56=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 56, 0, input);

                throw nvae;
            }
            switch (alt56) {
                case 1 :
                    // ShellCommand.g:1480:3: nothing
                    {
                    pushFollow(FOLLOW_nothing_in_exprList5099);
                    nothing();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ArrayList<ASTExpression>(); 
                    }

                    }
                    break;
                case 2 :
                    // ShellCommand.g:1483:3: el= exprListMin1
                    {
                    pushFollow(FOLLOW_exprListMin1_in_exprList5117);
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
    // ShellCommand.g:1491:1: exprListMin1 returns [List<ASTExpression> n] : e= inSoilExpression ( COMMA e= inSoilExpression )* ;
    public final List<ASTExpression> exprListMin1() throws RecognitionException {
        List<ASTExpression> n = null;

        ASTExpression e = null;



          n = new ArrayList<ASTExpression>();

        try {
            // ShellCommand.g:1495:1: (e= inSoilExpression ( COMMA e= inSoilExpression )* )
            // ShellCommand.g:1496:3: e= inSoilExpression ( COMMA e= inSoilExpression )*
            {
            pushFollow(FOLLOW_inSoilExpression_in_exprListMin15150);
            e=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               if (e != null) n.add(e); 
            }
            // ShellCommand.g:1498:3: ( COMMA e= inSoilExpression )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==COMMA) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // ShellCommand.g:1499:5: COMMA e= inSoilExpression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_exprListMin15165); if (state.failed) return n;
            	    pushFollow(FOLLOW_inSoilExpression_in_exprListMin15175);
            	    e=inSoilExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       if (e != null) n.add(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop57;
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
    // ShellCommand.g:1509:1: exprListMin2 returns [List<ASTExpression> n] : e= inSoilExpression COMMA e= inSoilExpression ( COMMA e= inSoilExpression )* ;
    public final List<ASTExpression> exprListMin2() throws RecognitionException {
        List<ASTExpression> n = null;

        ASTExpression e = null;



          n = new ArrayList<ASTExpression>();

        try {
            // ShellCommand.g:1513:1: (e= inSoilExpression COMMA e= inSoilExpression ( COMMA e= inSoilExpression )* )
            // ShellCommand.g:1514:3: e= inSoilExpression COMMA e= inSoilExpression ( COMMA e= inSoilExpression )*
            {
            pushFollow(FOLLOW_inSoilExpression_in_exprListMin25215);
            e=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               if (e != null) n.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_exprListMin25223); if (state.failed) return n;
            pushFollow(FOLLOW_inSoilExpression_in_exprListMin25231);
            e=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               if (e != null) n.add(e); 
            }
            // ShellCommand.g:1519:3: ( COMMA e= inSoilExpression )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==COMMA) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // ShellCommand.g:1520:5: COMMA e= inSoilExpression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_exprListMin25245); if (state.failed) return n;
            	    pushFollow(FOLLOW_inSoilExpression_in_exprListMin25255);
            	    e=inSoilExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       if (e != null) n.add(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop58;
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
    // ShellCommand.g:1530:1: identList returns [List<String> n] : ( nothing | il= identListMin1 );
    public final List<String> identList() throws RecognitionException {
        List<String> n = null;

        List<String> il = null;


        try {
            // ShellCommand.g:1531:1: ( nothing | il= identListMin1 )
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==EOF||LA59_0==SEMI||LA59_0==RPAREN||LA59_0==67||LA59_0==88) ) {
                alt59=1;
            }
            else if ( (LA59_0==IDENT) ) {
                alt59=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;
            }
            switch (alt59) {
                case 1 :
                    // ShellCommand.g:1532:3: nothing
                    {
                    pushFollow(FOLLOW_nothing_in_identList5285);
                    nothing();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ArrayList<String>(); 
                    }

                    }
                    break;
                case 2 :
                    // ShellCommand.g:1535:3: il= identListMin1
                    {
                    pushFollow(FOLLOW_identListMin1_in_identList5302);
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
    // ShellCommand.g:1543:1: identListMin1 returns [List<String> n] : id= IDENT ( COMMA id= IDENT )* ;
    public final List<String> identListMin1() throws RecognitionException {
        List<String> n = null;

        Token id=null;


          n = new ArrayList<String>();

        try {
            // ShellCommand.g:1547:1: (id= IDENT ( COMMA id= IDENT )* )
            // ShellCommand.g:1548:3: id= IDENT ( COMMA id= IDENT )*
            {
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_identListMin15336); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.add((id!=null?id.getText():null)); 
            }
            // ShellCommand.g:1550:3: ( COMMA id= IDENT )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==COMMA) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // ShellCommand.g:1551:5: COMMA id= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_identListMin15350); if (state.failed) return n;
            	    id=(Token)match(input,IDENT,FOLLOW_IDENT_in_identListMin15360); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {

            	          n.add((id!=null?id.getText():null)); 
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
    // $ANTLR end "identListMin1"

    // $ANTLR start synpred1_ShellCommand
    public final void synpred1_ShellCommand_fragment() throws RecognitionException {   
        // ShellCommand.g:83:3: ( stat EOF )
        // ShellCommand.g:83:4: stat EOF
        {
        pushFollow(FOLLOW_stat_in_synpred1_ShellCommand60);
        stat();

        state._fsp--;
        if (state.failed) return ;
        match(input,EOF,FOLLOW_EOF_in_synpred1_ShellCommand62); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_ShellCommand

    // $ANTLR start synpred2_ShellCommand
    public final void synpred2_ShellCommand_fragment() throws RecognitionException {   
        // ShellCommand.g:337:13: ( inSoilExpression )
        // ShellCommand.g:337:14: inSoilExpression
        {
        pushFollow(FOLLOW_inSoilExpression_in_synpred2_ShellCommand755);
        inSoilExpression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_ShellCommand

    // $ANTLR start synpred3_ShellCommand
    public final void synpred3_ShellCommand_fragment() throws RecognitionException {   
        // ShellCommand.g:897:7: ( COLON type EQUAL )
        // ShellCommand.g:897:8: COLON type EQUAL
        {
        match(input,COLON,FOLLOW_COLON_in_synpred3_ShellCommand3239); if (state.failed) return ;
        pushFollow(FOLLOW_type_in_synpred3_ShellCommand3241);
        type();

        state._fsp--;
        if (state.failed) return ;
        match(input,EQUAL,FOLLOW_EQUAL_in_synpred3_ShellCommand3243); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_ShellCommand

    // $ANTLR start synpred5_ShellCommand
    public final void synpred5_ShellCommand_fragment() throws RecognitionException {   
        ShellCommandParser.varAssignStat_return vas = null;


        // ShellCommand.g:1081:5: (vas= varAssignStat )
        // ShellCommand.g:1081:5: vas= varAssignStat
        {
        pushFollow(FOLLOW_varAssignStat_in_synpred5_ShellCommand3871);
        vas=varAssignStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_ShellCommand

    // $ANTLR start synpred6_ShellCommand
    public final void synpred6_ShellCommand_fragment() throws RecognitionException {   
        ASTAttributeAssignmentStatement aas = null;


        // ShellCommand.g:1082:5: (aas= attAssignStat )
        // ShellCommand.g:1082:5: aas= attAssignStat
        {
        pushFollow(FOLLOW_attAssignStat_in_synpred6_ShellCommand3885);
        aas=attAssignStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_ShellCommand

    // $ANTLR start synpred7_ShellCommand
    public final void synpred7_ShellCommand_fragment() throws RecognitionException {   
        ASTNewLinkObjectStatement lcs = null;


        // ShellCommand.g:1083:5: (lcs= lobjCreateStat )
        // ShellCommand.g:1083:5: lcs= lobjCreateStat
        {
        pushFollow(FOLLOW_lobjCreateStat_in_synpred7_ShellCommand3899);
        lcs=lobjCreateStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred7_ShellCommand

    // $ANTLR start synpred8_ShellCommand
    public final void synpred8_ShellCommand_fragment() throws RecognitionException {   
        ShellCommandParser.objCreateStat_return ocs = null;


        // ShellCommand.g:1084:5: (ocs= objCreateStat )
        // ShellCommand.g:1084:5: ocs= objCreateStat
        {
        pushFollow(FOLLOW_objCreateStat_in_synpred8_ShellCommand3912);
        ocs=objCreateStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred8_ShellCommand

    // $ANTLR start synpred12_ShellCommand
    public final void synpred12_ShellCommand_fragment() throws RecognitionException {   
        ASTConditionalExecutionStatement ces = null;


        // ShellCommand.g:1088:5: (ces= condExStat )
        // ShellCommand.g:1088:5: ces= condExStat
        {
        pushFollow(FOLLOW_condExStat_in_synpred12_ShellCommand3973);
        ces=condExStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred12_ShellCommand

    // $ANTLR start synpred14_ShellCommand
    public final void synpred14_ShellCommand_fragment() throws RecognitionException {   
        Token varName=null;
        ShellCommandParser.rValue_return rVal = null;


        // ShellCommand.g:1115:3: (varName= IDENT COLON_EQUAL rVal= rValue )
        // ShellCommand.g:1115:3: varName= IDENT COLON_EQUAL rVal= rValue
        {
        varName=(Token)match(input,IDENT,FOLLOW_IDENT_in_synpred14_ShellCommand4093); if (state.failed) return ;
        match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_synpred14_ShellCommand4097); if (state.failed) return ;
        pushFollow(FOLLOW_rValue_in_synpred14_ShellCommand4105);
        rVal=rValue();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred14_ShellCommand

    // $ANTLR start synpred16_ShellCommand
    public final void synpred16_ShellCommand_fragment() throws RecognitionException {   
        ASTNewLinkObjectStatement loc = null;


        // ShellCommand.g:1386:3: (loc= lobjCreateStat )
        // ShellCommand.g:1386:3: loc= lobjCreateStat
        {
        pushFollow(FOLLOW_lobjCreateStat_in_synpred16_ShellCommand4796);
        loc=lobjCreateStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred16_ShellCommand

    // Delegated rules

    public final boolean synpred7_ShellCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_ShellCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_ShellCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_ShellCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_ShellCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_ShellCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_ShellCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_ShellCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred12_ShellCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred12_ShellCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_ShellCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_ShellCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_ShellCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_ShellCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_ShellCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_ShellCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_ShellCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_ShellCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred16_ShellCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred16_ShellCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA1 dfa1 = new DFA1(this);
    protected DFA5 dfa5 = new DFA5(this);
    protected DFA7 dfa7 = new DFA7(this);
    protected DFA45 dfa45 = new DFA45(this);
    protected DFA52 dfa52 = new DFA52(this);
    static final String DFA1_eotS =
        "\43\uffff";
    static final String DFA1_eofS =
        "\1\2\42\uffff";
    static final String DFA1_minS =
        "\1\4\3\uffff\1\0\24\uffff\3\0\7\uffff";
    static final String DFA1_maxS =
        "\1\131\3\uffff\1\0\24\uffff\3\0\7\uffff";
    static final String DFA1_acceptS =
        "\1\uffff\3\1\1\uffff\24\1\3\uffff\1\1\1\2\5\uffff";
    static final String DFA1_specialS =
        "\1\0\3\uffff\1\1\24\uffff\1\2\1\3\1\4\7\uffff}>";
    static final String[] DFA1_transitionS = {
            "\1\1\2\uffff\1\3\1\26\10\uffff\2\5\4\uffff\1\23\3\uffff\1\10"+
            "\1\11\1\12\1\13\15\uffff\3\35\1\uffff\1\4\3\35\6\uffff\1\5\2"+
            "\uffff\1\24\3\25\1\27\3\uffff\1\6\1\7\4\14\1\15\1\16\1\17\1"+
            "\20\1\21\1\22\1\uffff\1\30\1\31\1\32\1\uffff\1\33\2\uffff\1"+
            "\34",
            "",
            "",
            "",
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
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA1_eot = DFA.unpackEncodedString(DFA1_eotS);
    static final short[] DFA1_eof = DFA.unpackEncodedString(DFA1_eofS);
    static final char[] DFA1_min = DFA.unpackEncodedStringToUnsignedChars(DFA1_minS);
    static final char[] DFA1_max = DFA.unpackEncodedStringToUnsignedChars(DFA1_maxS);
    static final short[] DFA1_accept = DFA.unpackEncodedString(DFA1_acceptS);
    static final short[] DFA1_special = DFA.unpackEncodedString(DFA1_specialS);
    static final short[][] DFA1_transition;

    static {
        int numStates = DFA1_transitionS.length;
        DFA1_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA1_transition[i] = DFA.unpackEncodedString(DFA1_transitionS[i]);
        }
    }

    class DFA1 extends DFA {

        public DFA1(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 1;
            this.eot = DFA1_eot;
            this.eof = DFA1_eof;
            this.min = DFA1_min;
            this.max = DFA1_max;
            this.accept = DFA1_accept;
            this.special = DFA1_special;
            this.transition = DFA1_transition;
        }
        public String getDescription() {
            return "81:1: shellCommand returns [ASTStatement n] : ( ( stat EOF )=>s= stat EOF | l= legacyStat EOF );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA1_0 = input.LA(1);

                         
                        int index1_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA1_0==SEMI) && (synpred1_ShellCommand())) {s = 1;}

                        else if ( (LA1_0==EOF) && (synpred1_ShellCommand())) {s = 2;}

                        else if ( (LA1_0==IDENT) && (synpred1_ShellCommand())) {s = 3;}

                        else if ( (LA1_0==48) ) {s = 4;}

                        else if ( ((LA1_0>=PLUS && LA1_0<=MINUS)||LA1_0==58) && (synpred1_ShellCommand())) {s = 5;}

                        else if ( (LA1_0==69) && (synpred1_ShellCommand())) {s = 6;}

                        else if ( (LA1_0==70) && (synpred1_ShellCommand())) {s = 7;}

                        else if ( (LA1_0==INT) && (synpred1_ShellCommand())) {s = 8;}

                        else if ( (LA1_0==REAL) && (synpred1_ShellCommand())) {s = 9;}

                        else if ( (LA1_0==STRING) && (synpred1_ShellCommand())) {s = 10;}

                        else if ( (LA1_0==HASH) && (synpred1_ShellCommand())) {s = 11;}

                        else if ( ((LA1_0>=71 && LA1_0<=74)) && (synpred1_ShellCommand())) {s = 12;}

                        else if ( (LA1_0==75) && (synpred1_ShellCommand())) {s = 13;}

                        else if ( (LA1_0==76) && (synpred1_ShellCommand())) {s = 14;}

                        else if ( (LA1_0==77) && (synpred1_ShellCommand())) {s = 15;}

                        else if ( (LA1_0==78) && (synpred1_ShellCommand())) {s = 16;}

                        else if ( (LA1_0==79) && (synpred1_ShellCommand())) {s = 17;}

                        else if ( (LA1_0==80) && (synpred1_ShellCommand())) {s = 18;}

                        else if ( (LA1_0==AT) && (synpred1_ShellCommand())) {s = 19;}

                        else if ( (LA1_0==61) && (synpred1_ShellCommand())) {s = 20;}

                        else if ( ((LA1_0>=62 && LA1_0<=64)) && (synpred1_ShellCommand())) {s = 21;}

                        else if ( (LA1_0==LPAREN) && (synpred1_ShellCommand())) {s = 22;}

                        else if ( (LA1_0==65) && (synpred1_ShellCommand())) {s = 23;}

                        else if ( (LA1_0==82) && (synpred1_ShellCommand())) {s = 24;}

                        else if ( (LA1_0==83) ) {s = 25;}

                        else if ( (LA1_0==84) ) {s = 26;}

                        else if ( (LA1_0==86) ) {s = 27;}

                        else if ( (LA1_0==89) && (synpred1_ShellCommand())) {s = 28;}

                        else if ( ((LA1_0>=44 && LA1_0<=46)||(LA1_0>=49 && LA1_0<=51)) ) {s = 29;}

                         
                        input.seek(index1_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA1_4 = input.LA(1);

                         
                        int index1_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_ShellCommand()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index1_4);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA1_25 = input.LA(1);

                         
                        int index1_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_ShellCommand()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index1_25);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA1_26 = input.LA(1);

                         
                        int index1_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_ShellCommand()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index1_26);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA1_27 = input.LA(1);

                         
                        int index1_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred1_ShellCommand()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index1_27);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 1, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA5_eotS =
        "\16\uffff";
    static final String DFA5_eofS =
        "\14\uffff\1\13\1\uffff";
    static final String DFA5_minS =
        "\1\54\1\7\7\uffff\1\5\1\7\1\uffff\1\4\1\uffff";
    static final String DFA5_maxS =
        "\1\126\1\7\7\uffff\1\13\1\7\1\uffff\1\126\1\uffff";
    static final String DFA5_acceptS =
        "\2\uffff\1\2\1\4\1\5\1\6\1\7\1\10\1\11\2\uffff\1\1\1\uffff\1\3";
    static final String DFA5_specialS =
        "\16\uffff}>";
    static final String[] DFA5_transitionS = {
            "\1\6\1\1\1\2\1\uffff\1\3\2\uffff\1\4\37\uffff\1\5\1\7\1\uffff"+
            "\1\10",
            "\1\11",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\12\5\uffff\1\13",
            "\1\14",
            "",
            "\1\13\47\uffff\3\13\1\15\1\13\2\uffff\1\13\37\uffff\2\13\1"+
            "\uffff\1\13",
            ""
    };

    static final short[] DFA5_eot = DFA.unpackEncodedString(DFA5_eotS);
    static final short[] DFA5_eof = DFA.unpackEncodedString(DFA5_eofS);
    static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars(DFA5_minS);
    static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars(DFA5_maxS);
    static final short[] DFA5_accept = DFA.unpackEncodedString(DFA5_acceptS);
    static final short[] DFA5_special = DFA.unpackEncodedString(DFA5_specialS);
    static final short[][] DFA5_transition;

    static {
        int numStates = DFA5_transitionS.length;
        DFA5_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA5_transition[i] = DFA.unpackEncodedString(DFA5_transitionS[i]);
        }
    }

    class DFA5 extends DFA {

        public DFA5(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 5;
            this.eot = DFA5_eot;
            this.eof = DFA5_eof;
            this.min = DFA5_min;
            this.max = DFA5_max;
            this.accept = DFA5_accept;
            this.special = DFA5_special;
            this.transition = DFA5_transition;
        }
        public String getDescription() {
            return "151:1: singleLegacyStat returns [ASTStatement n] : (lcr= legacyCreate | lca= legacyCreateAssign | lci= legacyCreateInsert | llt= legacyLet | lex= legacyExecute | ods= objDestroyStat | 'set' aas= attAssignStat | lis= lnkInsStat | lds= lnkDelStat );";
        }
    }
    static final String DFA7_eotS =
        "\27\uffff";
    static final String DFA7_eofS =
        "\1\26\26\uffff";
    static final String DFA7_minS =
        "\1\7\26\uffff";
    static final String DFA7_maxS =
        "\1\120\26\uffff";
    static final String DFA7_acceptS =
        "\1\uffff\25\1\1\2";
    static final String DFA7_specialS =
        "\1\0\26\uffff}>";
    static final String[] DFA7_transitionS = {
            "\1\11\1\24\10\uffff\2\2\4\uffff\1\21\3\uffff\1\5\1\6\1\7\1"+
            "\10\21\uffff\1\1\11\uffff\1\2\2\uffff\1\22\3\23\1\25\3\uffff"+
            "\1\3\1\4\4\12\1\13\1\14\1\15\1\16\1\17\1\20",
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

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "337:12: ( ( inSoilExpression )=>retVal= inSoilExpression | nothing )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA7_0 = input.LA(1);

                         
                        int index7_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA7_0==48) && (synpred2_ShellCommand())) {s = 1;}

                        else if ( ((LA7_0>=PLUS && LA7_0<=MINUS)||LA7_0==58) && (synpred2_ShellCommand())) {s = 2;}

                        else if ( (LA7_0==69) && (synpred2_ShellCommand())) {s = 3;}

                        else if ( (LA7_0==70) && (synpred2_ShellCommand())) {s = 4;}

                        else if ( (LA7_0==INT) && (synpred2_ShellCommand())) {s = 5;}

                        else if ( (LA7_0==REAL) && (synpred2_ShellCommand())) {s = 6;}

                        else if ( (LA7_0==STRING) && (synpred2_ShellCommand())) {s = 7;}

                        else if ( (LA7_0==HASH) && (synpred2_ShellCommand())) {s = 8;}

                        else if ( (LA7_0==IDENT) && (synpred2_ShellCommand())) {s = 9;}

                        else if ( ((LA7_0>=71 && LA7_0<=74)) && (synpred2_ShellCommand())) {s = 10;}

                        else if ( (LA7_0==75) && (synpred2_ShellCommand())) {s = 11;}

                        else if ( (LA7_0==76) && (synpred2_ShellCommand())) {s = 12;}

                        else if ( (LA7_0==77) && (synpred2_ShellCommand())) {s = 13;}

                        else if ( (LA7_0==78) && (synpred2_ShellCommand())) {s = 14;}

                        else if ( (LA7_0==79) && (synpred2_ShellCommand())) {s = 15;}

                        else if ( (LA7_0==80) && (synpred2_ShellCommand())) {s = 16;}

                        else if ( (LA7_0==AT) && (synpred2_ShellCommand())) {s = 17;}

                        else if ( (LA7_0==61) && (synpred2_ShellCommand())) {s = 18;}

                        else if ( ((LA7_0>=62 && LA7_0<=64)) && (synpred2_ShellCommand())) {s = 19;}

                        else if ( (LA7_0==LPAREN) && (synpred2_ShellCommand())) {s = 20;}

                        else if ( (LA7_0==65) && (synpred2_ShellCommand())) {s = 21;}

                        else if ( (LA7_0==EOF) ) {s = 22;}

                         
                        input.seek(index7_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 7, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA45_eotS =
        "\45\uffff";
    static final String DFA45_eofS =
        "\1\1\44\uffff";
    static final String DFA45_minS =
        "\1\4\4\uffff\26\0\12\uffff";
    static final String DFA45_maxS =
        "\1\131\4\uffff\26\0\12\uffff";
    static final String DFA45_acceptS =
        "\1\uffff\1\1\31\uffff\1\6\1\7\1\10\1\12\1\2\1\3\1\13\1\11\1\4\1"+
        "\5";
    static final String DFA45_specialS =
        "\5\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1"+
        "\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\12\uffff}>";
    static final String[] DFA45_transitionS = {
            "\1\1\2\uffff\1\5\1\30\10\uffff\2\7\4\uffff\1\25\3\uffff\1\12"+
            "\1\13\1\14\1\15\21\uffff\1\6\11\uffff\1\7\2\uffff\1\26\3\27"+
            "\1\31\1\uffff\1\1\1\uffff\1\10\1\11\4\16\1\17\1\20\1\21\1\22"+
            "\1\23\1\24\1\uffff\1\32\1\33\1\34\1\uffff\1\35\1\uffff\1\1\1"+
            "\36",
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

    static final short[] DFA45_eot = DFA.unpackEncodedString(DFA45_eotS);
    static final short[] DFA45_eof = DFA.unpackEncodedString(DFA45_eofS);
    static final char[] DFA45_min = DFA.unpackEncodedStringToUnsignedChars(DFA45_minS);
    static final char[] DFA45_max = DFA.unpackEncodedStringToUnsignedChars(DFA45_maxS);
    static final short[] DFA45_accept = DFA.unpackEncodedString(DFA45_acceptS);
    static final short[] DFA45_special = DFA.unpackEncodedString(DFA45_specialS);
    static final short[][] DFA45_transition;

    static {
        int numStates = DFA45_transitionS.length;
        DFA45_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA45_transition[i] = DFA.unpackEncodedString(DFA45_transitionS[i]);
        }
    }

    class DFA45 extends DFA {

        public DFA45(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 45;
            this.eot = DFA45_eot;
            this.eof = DFA45_eof;
            this.min = DFA45_min;
            this.max = DFA45_max;
            this.accept = DFA45_accept;
            this.special = DFA45_special;
            this.transition = DFA45_transition;
        }
        public String getDescription() {
            return "1073:1: singleStat returns [ASTStatement n] options {backtrack=true; memoize=true; } : (emp= emptyStat | vas= varAssignStat | aas= attAssignStat | lcs= lobjCreateStat | ocs= objCreateStat | ods= objDestroyStat | lis= lnkInsStat | lds= lnkDelStat | ces= condExStat | its= iterStat | ops= callStat );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA45_5 = input.LA(1);

                         
                        int index45_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_ShellCommand()) ) {s = 31;}

                        else if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_5);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA45_6 = input.LA(1);

                         
                        int index45_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_6);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA45_7 = input.LA(1);

                         
                        int index45_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_7);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA45_8 = input.LA(1);

                         
                        int index45_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_8);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA45_9 = input.LA(1);

                         
                        int index45_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_9);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA45_10 = input.LA(1);

                         
                        int index45_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_10);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA45_11 = input.LA(1);

                         
                        int index45_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_11);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA45_12 = input.LA(1);

                         
                        int index45_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_12);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA45_13 = input.LA(1);

                         
                        int index45_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_13);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA45_14 = input.LA(1);

                         
                        int index45_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_14);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA45_15 = input.LA(1);

                         
                        int index45_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_15);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA45_16 = input.LA(1);

                         
                        int index45_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_16);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA45_17 = input.LA(1);

                         
                        int index45_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_17);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA45_18 = input.LA(1);

                         
                        int index45_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_18);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA45_19 = input.LA(1);

                         
                        int index45_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_19);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA45_20 = input.LA(1);

                         
                        int index45_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_20);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA45_21 = input.LA(1);

                         
                        int index45_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_21);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA45_22 = input.LA(1);

                         
                        int index45_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_22);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA45_23 = input.LA(1);

                         
                        int index45_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_23);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA45_24 = input.LA(1);

                         
                        int index45_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_24);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA45_25 = input.LA(1);

                         
                        int index45_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_ShellCommand()) ) {s = 32;}

                        else if ( (synpred12_ShellCommand()) ) {s = 34;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index45_25);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA45_26 = input.LA(1);

                         
                        int index45_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_ShellCommand()) ) {s = 35;}

                        else if ( (synpred8_ShellCommand()) ) {s = 36;}

                         
                        input.seek(index45_26);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 45, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA52_eotS =
        "\31\uffff";
    static final String DFA52_eofS =
        "\31\uffff";
    static final String DFA52_minS =
        "\1\7\25\uffff\1\0\2\uffff";
    static final String DFA52_maxS =
        "\1\122\25\uffff\1\0\2\uffff";
    static final String DFA52_acceptS =
        "\1\uffff\1\1\25\uffff\1\2\1\3";
    static final String DFA52_specialS =
        "\26\uffff\1\0\2\uffff}>";
    static final String[] DFA52_transitionS = {
            "\2\1\10\uffff\2\1\4\uffff\1\1\3\uffff\4\1\21\uffff\1\1\11\uffff"+
            "\1\1\2\uffff\5\1\3\uffff\14\1\1\uffff\1\26",
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

    static final short[] DFA52_eot = DFA.unpackEncodedString(DFA52_eotS);
    static final short[] DFA52_eof = DFA.unpackEncodedString(DFA52_eofS);
    static final char[] DFA52_min = DFA.unpackEncodedStringToUnsignedChars(DFA52_minS);
    static final char[] DFA52_max = DFA.unpackEncodedStringToUnsignedChars(DFA52_maxS);
    static final short[] DFA52_accept = DFA.unpackEncodedString(DFA52_acceptS);
    static final short[] DFA52_special = DFA.unpackEncodedString(DFA52_specialS);
    static final short[][] DFA52_transition;

    static {
        int numStates = DFA52_transitionS.length;
        DFA52_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA52_transition[i] = DFA.unpackEncodedString(DFA52_transitionS[i]);
        }
    }

    class DFA52 extends DFA {

        public DFA52(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 52;
            this.eot = DFA52_eot;
            this.eof = DFA52_eof;
            this.min = DFA52_min;
            this.max = DFA52_max;
            this.accept = DFA52_accept;
            this.special = DFA52_special;
            this.transition = DFA52_transition;
        }
        public String getDescription() {
            return "1377:1: rValue returns [ASTRValue n] options {backtrack=true; memoize=true; } : (e= inSoilExpression | loc= lobjCreateStat | 'new' objType= simpleType ( LPAREN objName= inSoilExpression RPAREN )? );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA52_22 = input.LA(1);

                         
                        int index52_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_ShellCommand()) ) {s = 23;}

                        else if ( (true) ) {s = 24;}

                         
                        input.seek(index52_22);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 52, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_stat_in_shellCommand70 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_shellCommand72 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_legacyStat_in_shellCommand88 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_shellCommand90 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_legacyOpEnter_in_legacyStat124 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_legacyOpExit_in_legacyStat140 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nextLegacyStat_in_legacyStat153 = new BitSet(new long[]{0x0009700000000002L,0x0000000000580000L});
    public static final BitSet FOLLOW_nextLegacyStat_in_legacyStat164 = new BitSet(new long[]{0x0009700000000002L,0x0000000000580000L});
    public static final BitSet FOLLOW_singleLegacyStat_in_nextLegacyStat198 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_SEMI_in_nextLegacyStat202 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_legacyCreate_in_singleLegacyStat235 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_legacyCreateAssign_in_singleLegacyStat254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_legacyCreateInsert_in_singleLegacyStat267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_legacyLet_in_singleLegacyStat280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_legacyExecute_in_singleLegacyStat308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objDestroyStat_in_singleLegacyStat326 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_singleLegacyStat339 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_attAssignStat_in_singleLegacyStat345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lnkInsStat_in_singleLegacyStat357 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lnkDelStat_in_singleLegacyStat378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_legacyCreate414 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_identListMin1_in_legacyCreate422 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_legacyCreate426 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_simpleType_in_legacyCreate434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_legacyCreateAssign471 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_identListMin1_in_legacyCreateAssign479 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_legacyCreateAssign483 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_45_in_legacyCreateAssign487 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_simpleType_in_legacyCreateAssign495 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_legacyCreateInsert529 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_legacyCreateInsert537 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_legacyCreateInsert541 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_legacyCreateInsert549 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_legacyCreateInsert553 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_legacyCreateInsert557 = new BitSet(new long[]{0xE401000078860180L,0x000000000005FFE3L});
    public static final BitSet FOLLOW_rValListMin2_in_legacyCreateInsert567 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_legacyCreateInsert571 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_legacyLet604 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_legacyLet612 = new BitSet(new long[]{0x0000000000000420L});
    public static final BitSet FOLLOW_COLON_in_legacyLet622 = new BitSet(new long[]{0x0000000000000080L,0x0000000000028780L});
    public static final BitSet FOLLOW_type_in_legacyLet632 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_legacyLet641 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_inSoilExpression_in_legacyLet649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_legacyOpEnter682 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_inSoilExpression_in_legacyOpEnter690 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_legacyOpEnter698 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_legacyOpEnter702 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_exprList_in_legacyOpEnter712 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_legacyOpEnter716 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_50_in_legacyOpExit751 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_inSoilExpression_in_legacyOpExit763 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nothing_in_legacyOpExit767 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_51_in_legacyExecute800 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_expression_in_legacyExecute804 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_expressionOnly842 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_expressionOnly844 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_expression892 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_expression896 = new BitSet(new long[]{0x0000000000000420L});
    public static final BitSet FOLLOW_COLON_in_expression900 = new BitSet(new long[]{0x0000000000000080L,0x0000000000028780L});
    public static final BitSet FOLLOW_type_in_expression904 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_expression909 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_expression_in_expression913 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_expression915 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_conditionalImpliesExpression_in_expression940 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_paramList973 = new BitSet(new long[]{0x0000000000000280L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList990 = new BitSet(new long[]{0x0000000000000A00L});
    public static final BitSet FOLLOW_COMMA_in_paramList1002 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList1006 = new BitSet(new long[]{0x0000000000000A00L});
    public static final BitSet FOLLOW_RPAREN_in_paramList1026 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_idList1055 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_COMMA_in_idList1065 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_idList1069 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_IDENT_in_variableDeclaration1100 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_variableDeclaration1102 = new BitSet(new long[]{0x0000000000000080L,0x0000000000028780L});
    public static final BitSet FOLLOW_type_in_variableDeclaration1106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression1142 = new BitSet(new long[]{0x0020000000000002L});
    public static final BitSet FOLLOW_53_in_conditionalImpliesExpression1155 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression1159 = new BitSet(new long[]{0x0020000000000002L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression1204 = new BitSet(new long[]{0x0040000000000002L});
    public static final BitSet FOLLOW_54_in_conditionalOrExpression1217 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression1221 = new BitSet(new long[]{0x0040000000000002L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression1265 = new BitSet(new long[]{0x0080000000000002L});
    public static final BitSet FOLLOW_55_in_conditionalXOrExpression1278 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression1282 = new BitSet(new long[]{0x0080000000000002L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression1326 = new BitSet(new long[]{0x0100000000000002L});
    public static final BitSet FOLLOW_56_in_conditionalAndExpression1339 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression1343 = new BitSet(new long[]{0x0100000000000002L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression1391 = new BitSet(new long[]{0x0000000000001402L});
    public static final BitSet FOLLOW_set_in_equalityExpression1410 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression1420 = new BitSet(new long[]{0x0000000000001402L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression1469 = new BitSet(new long[]{0x000000000001E002L});
    public static final BitSet FOLLOW_set_in_relationalExpression1487 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression1505 = new BitSet(new long[]{0x000000000001E002L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1555 = new BitSet(new long[]{0x0000000000060002L});
    public static final BitSet FOLLOW_set_in_additiveExpression1573 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1583 = new BitSet(new long[]{0x0000000000060002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1633 = new BitSet(new long[]{0x0200000000180002L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression1651 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1665 = new BitSet(new long[]{0x0200000000180002L});
    public static final BitSet FOLLOW_set_in_unaryExpression1727 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression1751 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpression_in_unaryExpression1771 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_postfixExpression1804 = new BitSet(new long[]{0x0000000000600002L});
    public static final BitSet FOLLOW_ARROW_in_postfixExpression1822 = new BitSet(new long[]{0xE000000000000080L,0x0000000000000001L});
    public static final BitSet FOLLOW_DOT_in_postfixExpression1828 = new BitSet(new long[]{0xE000000000000080L,0x0000000000000001L});
    public static final BitSet FOLLOW_propertyCall_in_postfixExpression1839 = new BitSet(new long[]{0x0000000000600002L});
    public static final BitSet FOLLOW_literal_in_primaryExpression1879 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objectReference_in_primaryExpression1893 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyCall_in_primaryExpression1905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression1916 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_expression_in_primaryExpression1920 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression1922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifExpression_in_primaryExpression1934 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_primaryExpression1951 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_DOT_in_primaryExpression1953 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_primaryExpression1955 = new BitSet(new long[]{0x0000000000800102L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression1959 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression1961 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_AT_in_primaryExpression1982 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_primaryExpression1984 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_in_objectReference2011 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_objectReference2019 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_queryExpression_in_propertyCall2087 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterateExpression_in_propertyCall2100 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationExpression_in_propertyCall2113 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeExpression_in_propertyCall2126 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_queryExpression2161 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_queryExpression2168 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_queryExpression2179 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_BAR_in_queryExpression2183 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_expression_in_queryExpression2194 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_queryExpression2200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_iterateExpression2232 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_iterateExpression2238 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_iterateExpression2246 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_iterateExpression2248 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_variableInitialization_in_iterateExpression2256 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_BAR_in_iterateExpression2258 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_expression_in_iterateExpression2266 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_iterateExpression2272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression2316 = new BitSet(new long[]{0x0000000002800102L});
    public static final BitSet FOLLOW_LBRACK_in_operationExpression2332 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression2336 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_RBRACK_in_operationExpression2338 = new BitSet(new long[]{0x0000000000800102L});
    public static final BitSet FOLLOW_AT_in_operationExpression2351 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_operationExpression2353 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_LPAREN_in_operationExpression2374 = new BitSet(new long[]{0xE401000078860380L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_expression_in_operationExpression2395 = new BitSet(new long[]{0x0000000000000A00L});
    public static final BitSet FOLLOW_COMMA_in_operationExpression2407 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_expression_in_operationExpression2411 = new BitSet(new long[]{0x0000000000000A00L});
    public static final BitSet FOLLOW_RPAREN_in_operationExpression2431 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_typeExpression2480 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_typeExpression2496 = new BitSet(new long[]{0x0000000000000080L,0x0000000000028780L});
    public static final BitSet FOLLOW_type_in_typeExpression2500 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_typeExpression2502 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_elemVarsDeclaration2541 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_COLON_in_elemVarsDeclaration2549 = new BitSet(new long[]{0x0000000000000080L,0x0000000000028780L});
    public static final BitSet FOLLOW_type_in_elemVarsDeclaration2553 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableInitialization2588 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_variableInitialization2590 = new BitSet(new long[]{0x0000000000000080L,0x0000000000028780L});
    public static final BitSet FOLLOW_type_in_variableInitialization2594 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_variableInitialization2596 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_expression_in_variableInitialization2600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_ifExpression2632 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_expression_in_ifExpression2636 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_ifExpression2638 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_expression_in_ifExpression2642 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_ifExpression2644 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_expression_in_ifExpression2648 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_ifExpression2650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_literal2689 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_70_in_literal2703 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_literal2716 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REAL_in_literal2731 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal2745 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_literal2755 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_literal2759 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_literal2771 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_COLON_COLON_in_literal2773 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_literal2777 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionLiteral_in_literal2789 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyCollectionLiteral_in_literal2801 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedLiteral_in_literal2813 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleLiteral_in_literal2825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dateLiteral_in_literal2837 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionLiteral2875 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_LBRACE_in_collectionLiteral2904 = new BitSet(new long[]{0xE401000178860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral2921 = new BitSet(new long[]{0x0000000100000800L});
    public static final BitSet FOLLOW_COMMA_in_collectionLiteral2934 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral2938 = new BitSet(new long[]{0x0000000100000800L});
    public static final BitSet FOLLOW_RBRACE_in_collectionLiteral2957 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_collectionItem2986 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_DOTDOT_in_collectionItem2997 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_expression_in_collectionItem3001 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_emptyCollectionLiteral3030 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_emptyCollectionLiteral3032 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020780L});
    public static final BitSet FOLLOW_collectionType_in_emptyCollectionLiteral3036 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_emptyCollectionLiteral3038 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_undefinedLiteral3068 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_undefinedLiteral3070 = new BitSet(new long[]{0x0000000000000080L,0x0000000000028780L});
    public static final BitSet FOLLOW_type_in_undefinedLiteral3074 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_undefinedLiteral3076 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_undefinedLiteral3090 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_undefinedLiteral3104 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_tupleLiteral3138 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_LBRACE_in_tupleLiteral3144 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral3152 = new BitSet(new long[]{0x0000000100000800L});
    public static final BitSet FOLLOW_COMMA_in_tupleLiteral3163 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral3167 = new BitSet(new long[]{0x0000000100000800L});
    public static final BitSet FOLLOW_RBRACE_in_tupleLiteral3178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tupleItem3209 = new BitSet(new long[]{0x0000000000000420L});
    public static final BitSet FOLLOW_COLON_in_tupleItem3248 = new BitSet(new long[]{0x0000000000000080L,0x0000000000028780L});
    public static final BitSet FOLLOW_type_in_tupleItem3252 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_tupleItem3254 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_expression_in_tupleItem3258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_tupleItem3280 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_expression_in_tupleItem3290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_dateLiteral3335 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_LBRACE_in_dateLiteral3337 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_STRING_in_dateLiteral3341 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_RBRACE_in_dateLiteral3343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleType_in_type3393 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_type3405 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleType_in_type3417 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeOnly3449 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_typeOnly3451 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_simpleType3479 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionType3517 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_collectionType3544 = new BitSet(new long[]{0x0000000000000080L,0x0000000000028780L});
    public static final BitSet FOLLOW_type_in_collectionType3548 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_collectionType3550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_tupleType3584 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_tupleType3586 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType3595 = new BitSet(new long[]{0x0000000000000A00L});
    public static final BitSet FOLLOW_COMMA_in_tupleType3606 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType3610 = new BitSet(new long[]{0x0000000000000A00L});
    public static final BitSet FOLLOW_RPAREN_in_tupleType3622 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tuplePart3654 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_tuplePart3656 = new BitSet(new long[]{0x0000000000000080L,0x0000000000028780L});
    public static final BitSet FOLLOW_type_in_tuplePart3660 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stat_in_statOnly3709 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_statOnly3713 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nextStat_in_stat3744 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_SEMI_in_stat3755 = new BitSet(new long[]{0xE409700078860180L,0x00000000025DFFE3L});
    public static final BitSet FOLLOW_nextStat_in_stat3761 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_singleStat_in_nextStat3795 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyStat_in_singleStat3850 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varAssignStat_in_singleStat3871 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attAssignStat_in_singleStat3885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lobjCreateStat_in_singleStat3899 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objCreateStat_in_singleStat3912 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objDestroyStat_in_singleStat3926 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lnkInsStat_in_singleStat3939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lnkDelStat_in_singleStat3956 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_condExStat_in_singleStat3973 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterStat_in_singleStat3990 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_callStat_in_singleStat4009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nothing_in_emptyStat4040 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_varAssignStat4093 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_varAssignStat4097 = new BitSet(new long[]{0xE401000078860180L,0x000000000005FFE3L});
    public static final BitSet FOLLOW_rValue_in_varAssignStat4105 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identListMin1_in_varAssignStat4124 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_varAssignStat4128 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_varAssignStat4132 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_simpleType_in_varAssignStat4140 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_LPAREN_in_varAssignStat4150 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_exprList_in_varAssignStat4162 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_varAssignStat4168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_attAssignStat4203 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_DOT_in_attAssignStat4208 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_attAssignStat4217 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_attAssignStat4221 = new BitSet(new long[]{0xE401000078860180L,0x000000000005FFE3L});
    public static final BitSet FOLLOW_rValue_in_attAssignStat4229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_objCreateStat4255 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_simpleType_in_objCreateStat4263 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_LPAREN_in_objCreateStat4273 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_exprListMin1_in_objCreateStat4285 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_objCreateStat4291 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_lobjCreateStat4322 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_lobjCreateStat4330 = new BitSet(new long[]{0x0000800000000100L});
    public static final BitSet FOLLOW_LPAREN_in_lobjCreateStat4340 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_inSoilExpression_in_lobjCreateStat4352 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_lobjCreateStat4358 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_lobjCreateStat4367 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_lobjCreateStat4371 = new BitSet(new long[]{0xE401000078860180L,0x000000000005FFE3L});
    public static final BitSet FOLLOW_rValListMin2_in_lobjCreateStat4381 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_lobjCreateStat4385 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_objDestroyStat4411 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_exprListMin1_in_objDestroyStat4419 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_lnkInsStat4445 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_lnkInsStat4449 = new BitSet(new long[]{0xE401000078860180L,0x000000000005FFE3L});
    public static final BitSet FOLLOW_rValListMin2_in_lnkInsStat4459 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_lnkInsStat4463 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_lnkInsStat4467 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_lnkInsStat4475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_86_in_lnkDelStat4499 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_lnkDelStat4503 = new BitSet(new long[]{0xE401000078860180L,0x000000000005FFE3L});
    public static final BitSet FOLLOW_rValListMin2_in_lnkDelStat4513 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_lnkDelStat4517 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_lnkDelStat4521 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_lnkDelStat4530 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_condExStat4561 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_inSoilExpression_in_condExStat4570 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_condExStat4574 = new BitSet(new long[]{0xE409700078860180L,0x00000000025DFFE3L});
    public static final BitSet FOLLOW_stat_in_condExStat4583 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000008L});
    public static final BitSet FOLLOW_67_in_condExStat4594 = new BitSet(new long[]{0xE409700078860180L,0x00000000025DFFE3L});
    public static final BitSet FOLLOW_stat_in_condExStat4606 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_88_in_condExStat4617 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_iterStat4642 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_iterStat4650 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_iterStat4654 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_inSoilExpression_in_iterStat4662 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_iterStat4666 = new BitSet(new long[]{0xE409700078860180L,0x00000000025DFFE3L});
    public static final BitSet FOLLOW_stat_in_iterStat4674 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_88_in_iterStat4678 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_callStat4708 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_rValue4780 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lobjCreateStat_in_rValue4796 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_rValue4808 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_simpleType_in_rValue4817 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_LPAREN_in_rValue4827 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_inSoilExpression_in_rValue4839 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_rValue4845 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nothing_in_rValList4873 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rValListMin1_in_rValList4900 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rValue_in_rValListMin14933 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_COMMA_in_rValListMin14947 = new BitSet(new long[]{0xE401000078860180L,0x000000000005FFE3L});
    public static final BitSet FOLLOW_rValue_in_rValListMin14957 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_rValue_in_rValListMin24996 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COMMA_in_rValListMin25004 = new BitSet(new long[]{0xE401000078860180L,0x000000000005FFE3L});
    public static final BitSet FOLLOW_rValue_in_rValListMin25012 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_COMMA_in_rValListMin25026 = new BitSet(new long[]{0xE401000078860180L,0x000000000005FFE3L});
    public static final BitSet FOLLOW_rValue_in_rValListMin25036 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_expression_in_inSoilExpression5070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nothing_in_exprList5099 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exprListMin1_in_exprList5117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin15150 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_COMMA_in_exprListMin15165 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin15175 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin25215 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COMMA_in_exprListMin25223 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin25231 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_COMMA_in_exprListMin25245 = new BitSet(new long[]{0xE401000078860180L,0x000000000001FFE3L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin25255 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_nothing_in_identList5285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identListMin1_in_identList5302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_identListMin15336 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_COMMA_in_identListMin15350 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_IDENT_in_identListMin15360 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_stat_in_synpred1_ShellCommand60 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_synpred1_ShellCommand62 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_synpred2_ShellCommand755 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_synpred3_ShellCommand3239 = new BitSet(new long[]{0x0000000000000080L,0x0000000000028780L});
    public static final BitSet FOLLOW_type_in_synpred3_ShellCommand3241 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_synpred3_ShellCommand3243 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varAssignStat_in_synpred5_ShellCommand3871 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attAssignStat_in_synpred6_ShellCommand3885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lobjCreateStat_in_synpred7_ShellCommand3899 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objCreateStat_in_synpred8_ShellCommand3912 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_condExStat_in_synpred12_ShellCommand3973 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_synpred14_ShellCommand4093 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_synpred14_ShellCommand4097 = new BitSet(new long[]{0xE401000078860180L,0x000000000005FFE3L});
    public static final BitSet FOLLOW_rValue_in_synpred14_ShellCommand4105 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lobjCreateStat_in_synpred16_ShellCommand4796 = new BitSet(new long[]{0x0000000000000002L});

}