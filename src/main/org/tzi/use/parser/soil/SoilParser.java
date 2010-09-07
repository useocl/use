// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 Soil.g 2010-09-07 17:10:47

/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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
 
package org.tzi.use.parser.soil;

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
@SuppressWarnings("all") public class SoilParser extends BaseParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "SEMI", "IDENT", "COLON_EQUAL", "LPAREN", "RPAREN", "DOT", "COMMA", "COLON", "EQUAL", "NOT_EQUAL", "LESS", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "PLUS", "MINUS", "STAR", "SLASH", "ARROW", "AT", "BAR", "LBRACK", "RBRACK", "INT", "REAL", "STRING", "HASH", "LBRACE", "RBRACE", "DOTDOT", "NEWLINE", "WS", "SL_COMMENT", "ML_COMMENT", "COLON_COLON", "SCRIPTBODY", "RANGE_OR_INT", "ESC", "HEX_DIGIT", "VOCAB", "'new'", "'between'", "'destroy'", "'insert'", "'into'", "'delete'", "'from'", "'if'", "'then'", "'else'", "'end'", "'for'", "'in'", "'do'", "'let'", "'implies'", "'or'", "'xor'", "'and'", "'div'", "'not'", "'allInstances'", "'pre'", "'iterate'", "'oclAsType'", "'oclIsKindOf'", "'oclIsTypeOf'", "'endif'", "'true'", "'false'", "'Set'", "'Sequence'", "'Bag'", "'OrderedSet'", "'oclEmpty'", "'oclUndefined'", "'Undefined'", "'null'", "'Tuple'", "'Date'", "'Collection'"
    };
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int LBRACK=25;
    public static final int STAR=20;
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
    public static final int LPAREN=7;
    public static final int AT=23;
    public static final int T__55=55;
    public static final int ML_COMMENT=37;
    public static final int T__56=56;
    public static final int RPAREN=8;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int SLASH=21;
    public static final int GREATER=15;
    public static final int COLON_EQUAL=6;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int NOT_EQUAL=13;
    public static final int COMMA=10;
    public static final int T__59=59;
    public static final int EQUAL=12;
    public static final int LESS=14;
    public static final int IDENT=5;
    public static final int PLUS=18;
    public static final int RANGE_OR_INT=40;
    public static final int DOT=9;
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
    public static final int MINUS=19;
    public static final int T__84=84;
    public static final int SEMI=4;
    public static final int COLON=11;
    public static final int REAL=28;
    public static final int WS=35;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int NEWLINE=34;
    public static final int T__70=70;
    public static final int SL_COMMENT=36;
    public static final int VOCAB=43;
    public static final int ARROW=22;
    public static final int LESS_EQUAL=16;
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int T__74=74;
    public static final int GREATER_EQUAL=17;
    public static final int T__73=73;
    public static final int BAR=24;
    public static final int T__79=79;
    public static final int STRING=29;
    public static final int T__78=78;
    public static final int T__77=77;

    // delegates
    // delegators


        public SoilParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public SoilParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[79+1];
             
             
        }
        

    public String[] getTokenNames() { return SoilParser.tokenNames; }
    public String getGrammarFileName() { return "Soil.g"; }



    // $ANTLR start "statOnly"
    // Soil.g:109:1: statOnly returns [ASTStatement n] : s= stat EOF ;
    public final ASTStatement statOnly() throws RecognitionException {
        ASTStatement n = null;

        SoilParser.stat_return s = null;


        try {
            // Soil.g:110:1: (s= stat EOF )
            // Soil.g:111:3: s= stat EOF
            {
            pushFollow(FOLLOW_stat_in_statOnly76);
            s=stat();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_statOnly80); if (state.failed) return n;
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
    // Soil.g:121:1: stat returns [ASTStatement n] : nextStat[seq] ( SEMI nextStat[seq] )* ;
    public final SoilParser.stat_return stat() throws RecognitionException {
        SoilParser.stat_return retval = new SoilParser.stat_return();
        retval.start = input.LT(1);


          ASTSequenceStatement seq = new ASTSequenceStatement();

        try {
            // Soil.g:125:1: ( nextStat[seq] ( SEMI nextStat[seq] )* )
            // Soil.g:126:3: nextStat[seq] ( SEMI nextStat[seq] )*
            {
            pushFollow(FOLLOW_nextStat_in_stat111);
            nextStat(seq);

            state._fsp--;
            if (state.failed) return retval;
            // Soil.g:127:3: ( SEMI nextStat[seq] )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==SEMI) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Soil.g:128:5: SEMI nextStat[seq]
            	    {
            	    match(input,SEMI,FOLLOW_SEMI_in_stat122); if (state.failed) return retval;
            	    pushFollow(FOLLOW_nextStat_in_stat128);
            	    nextStat(seq);

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop1;
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
    // Soil.g:145:1: nextStat[ASTSequenceStatement seq] : s= singleStat ;
    public final SoilParser.nextStat_return nextStat(ASTSequenceStatement seq) throws RecognitionException {
        SoilParser.nextStat_return retval = new SoilParser.nextStat_return();
        retval.start = input.LT(1);

        ASTStatement s = null;


        try {
            // Soil.g:146:1: (s= singleStat )
            // Soil.g:147:3: s= singleStat
            {
            pushFollow(FOLLOW_singleStat_in_nextStat162);
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
    // Soil.g:159:1: singleStat returns [ASTStatement n] options {backtrack=true; memoize=true; } : (emp= emptyStat | vas= varAssignStat | aas= attAssignStat | lcs= lobjCreateStat | ocs= objCreateStat | ods= objDestroyStat | lis= lnkInsStat | lds= lnkDelStat | ces= condExStat | its= iterStat | ops= callStat );
    public final ASTStatement singleStat() throws RecognitionException {
        ASTStatement n = null;
        int singleStat_StartIndex = input.index();
        ASTEmptyStatement emp = null;

        SoilParser.varAssignStat_return vas = null;

        ASTAttributeAssignmentStatement aas = null;

        ASTNewLinkObjectStatement lcs = null;

        SoilParser.objCreateStat_return ocs = null;

        SoilParser.objDestroyStat_return ods = null;

        ASTLinkInsertionStatement lis = null;

        ASTLinkDeletionStatement lds = null;

        ASTConditionalExecutionStatement ces = null;

        ASTIterationStatement its = null;

        ASTOperationCallStatement ops = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return n; }
            // Soil.g:164:1: (emp= emptyStat | vas= varAssignStat | aas= attAssignStat | lcs= lobjCreateStat | ocs= objCreateStat | ods= objDestroyStat | lis= lnkInsStat | lds= lnkDelStat | ces= condExStat | its= iterStat | ops= callStat )
            int alt2=11;
            alt2 = dfa2.predict(input);
            switch (alt2) {
                case 1 :
                    // Soil.g:165:5: emp= emptyStat
                    {
                    pushFollow(FOLLOW_emptyStat_in_singleStat217);
                    emp=emptyStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = emp; 
                    }

                    }
                    break;
                case 2 :
                    // Soil.g:167:5: vas= varAssignStat
                    {
                    pushFollow(FOLLOW_varAssignStat_in_singleStat238);
                    vas=varAssignStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (vas!=null?vas.n:null); 
                    }

                    }
                    break;
                case 3 :
                    // Soil.g:168:5: aas= attAssignStat
                    {
                    pushFollow(FOLLOW_attAssignStat_in_singleStat252);
                    aas=attAssignStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = aas; 
                    }

                    }
                    break;
                case 4 :
                    // Soil.g:169:5: lcs= lobjCreateStat
                    {
                    pushFollow(FOLLOW_lobjCreateStat_in_singleStat266);
                    lcs=lobjCreateStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = lcs; 
                    }

                    }
                    break;
                case 5 :
                    // Soil.g:170:5: ocs= objCreateStat
                    {
                    pushFollow(FOLLOW_objCreateStat_in_singleStat279);
                    ocs=objCreateStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (ocs!=null?ocs.n:null); 
                    }

                    }
                    break;
                case 6 :
                    // Soil.g:171:5: ods= objDestroyStat
                    {
                    pushFollow(FOLLOW_objDestroyStat_in_singleStat293);
                    ods=objDestroyStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (ods!=null?ods.n:null); 
                    }

                    }
                    break;
                case 7 :
                    // Soil.g:172:5: lis= lnkInsStat
                    {
                    pushFollow(FOLLOW_lnkInsStat_in_singleStat306);
                    lis=lnkInsStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = lis; 
                    }

                    }
                    break;
                case 8 :
                    // Soil.g:173:5: lds= lnkDelStat
                    {
                    pushFollow(FOLLOW_lnkDelStat_in_singleStat323);
                    lds=lnkDelStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = lds; 
                    }

                    }
                    break;
                case 9 :
                    // Soil.g:174:5: ces= condExStat
                    {
                    pushFollow(FOLLOW_condExStat_in_singleStat340);
                    ces=condExStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = ces; 
                    }

                    }
                    break;
                case 10 :
                    // Soil.g:175:5: its= iterStat
                    {
                    pushFollow(FOLLOW_iterStat_in_singleStat357);
                    its=iterStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = its; 
                    }

                    }
                    break;
                case 11 :
                    // Soil.g:176:5: ops= callStat
                    {
                    pushFollow(FOLLOW_callStat_in_singleStat376);
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
            if ( state.backtracking>0 ) { memoize(input, 4, singleStat_StartIndex); }
        }
        return n;
    }
    // $ANTLR end "singleStat"


    // $ANTLR start "emptyStat"
    // Soil.g:184:1: emptyStat returns [ASTEmptyStatement n] : nothing ;
    public final ASTEmptyStatement emptyStat() throws RecognitionException {
        ASTEmptyStatement n = null;

        try {
            // Soil.g:185:1: ( nothing )
            // Soil.g:186:3: nothing
            {
            pushFollow(FOLLOW_nothing_in_emptyStat407);
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
    // Soil.g:195:1: varAssignStat returns [ASTStatement n] options {backtrack=true; memoize=true; } : (varName= IDENT COLON_EQUAL rVal= rValue | vNames= identListMin1 COLON_EQUAL 'new' oType= simpleType ( LPAREN oNames= exprList RPAREN )? );
    public final SoilParser.varAssignStat_return varAssignStat() throws RecognitionException {
        SoilParser.varAssignStat_return retval = new SoilParser.varAssignStat_return();
        retval.start = input.LT(1);
        int varAssignStat_StartIndex = input.index();
        Token varName=null;
        SoilParser.rValue_return rVal = null;

        List<String> vNames = null;

        ASTSimpleType oType = null;

        List<ASTExpression> oNames = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // Soil.g:200:1: (varName= IDENT COLON_EQUAL rVal= rValue | vNames= identListMin1 COLON_EQUAL 'new' oType= simpleType ( LPAREN oNames= exprList RPAREN )? )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==IDENT) ) {
                int LA4_1 = input.LA(2);

                if ( (LA4_1==COLON_EQUAL) ) {
                    int LA4_2 = input.LA(3);

                    if ( (LA4_2==44) ) {
                        int LA4_4 = input.LA(4);

                        if ( (LA4_4==IDENT) ) {
                            int LA4_6 = input.LA(5);

                            if ( (synpred11_Soil()) ) {
                                alt4=1;
                            }
                            else if ( (true) ) {
                                alt4=2;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 4, 6, input);

                                throw nvae;
                            }
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 4, 4, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA4_2==IDENT||LA4_2==LPAREN||(LA4_2>=PLUS && LA4_2<=MINUS)||LA4_2==AT||(LA4_2>=INT && LA4_2<=HASH)||LA4_2==51||LA4_2==58||LA4_2==64||(LA4_2>=67 && LA4_2<=70)||(LA4_2>=72 && LA4_2<=83)) ) {
                        alt4=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 4, 2, input);

                        throw nvae;
                    }
                }
                else if ( (LA4_1==COMMA) ) {
                    alt4=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // Soil.g:201:3: varName= IDENT COLON_EQUAL rVal= rValue
                    {
                    varName=(Token)match(input,IDENT,FOLLOW_IDENT_in_varAssignStat460); if (state.failed) return retval;
                    match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_varAssignStat464); if (state.failed) return retval;
                    pushFollow(FOLLOW_rValue_in_varAssignStat472);
                    rVal=rValue();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n = new ASTVariableAssignmentStatement((varName!=null?varName.getText():null), (rVal!=null?rVal.n:null)); 
                    }

                    }
                    break;
                case 2 :
                    // Soil.g:207:3: vNames= identListMin1 COLON_EQUAL 'new' oType= simpleType ( LPAREN oNames= exprList RPAREN )?
                    {
                    pushFollow(FOLLOW_identListMin1_in_varAssignStat491);
                    vNames=identListMin1();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_varAssignStat495); if (state.failed) return retval;
                    match(input,44,FOLLOW_44_in_varAssignStat499); if (state.failed) return retval;
                    pushFollow(FOLLOW_simpleType_in_varAssignStat507);
                    oType=simpleType();

                    state._fsp--;
                    if (state.failed) return retval;
                    // Soil.g:211:3: ( LPAREN oNames= exprList RPAREN )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0==LPAREN) ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // Soil.g:212:5: LPAREN oNames= exprList RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_varAssignStat517); if (state.failed) return retval;
                            pushFollow(FOLLOW_exprList_in_varAssignStat529);
                            oNames=exprList();

                            state._fsp--;
                            if (state.failed) return retval;
                            match(input,RPAREN,FOLLOW_RPAREN_in_varAssignStat535); if (state.failed) return retval;

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
            if ( state.backtracking>0 ) { memoize(input, 6, varAssignStat_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "varAssignStat"


    // $ANTLR start "attAssignStat"
    // Soil.g:270:1: attAssignStat returns [ASTAttributeAssignmentStatement n] : obj= inSoilExpression DOT attName= IDENT COLON_EQUAL r= rValue ;
    public final ASTAttributeAssignmentStatement attAssignStat() throws RecognitionException {
        ASTAttributeAssignmentStatement n = null;

        Token attName=null;
        ASTExpression obj = null;

        SoilParser.rValue_return r = null;


        try {
            // Soil.g:271:1: (obj= inSoilExpression DOT attName= IDENT COLON_EQUAL r= rValue )
            // Soil.g:272:3: obj= inSoilExpression DOT attName= IDENT COLON_EQUAL r= rValue
            {
            pushFollow(FOLLOW_inSoilExpression_in_attAssignStat570);
            obj=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            match(input,DOT,FOLLOW_DOT_in_attAssignStat575); if (state.failed) return n;
            attName=(Token)match(input,IDENT,FOLLOW_IDENT_in_attAssignStat584); if (state.failed) return n;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_attAssignStat588); if (state.failed) return n;
            pushFollow(FOLLOW_rValue_in_attAssignStat596);
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
    // Soil.g:285:1: objCreateStat returns [ASTStatement n] : 'new' objType= simpleType ( LPAREN objNames= exprListMin1 RPAREN )? ;
    public final SoilParser.objCreateStat_return objCreateStat() throws RecognitionException {
        SoilParser.objCreateStat_return retval = new SoilParser.objCreateStat_return();
        retval.start = input.LT(1);

        ASTSimpleType objType = null;

        List<ASTExpression> objNames = null;


        try {
            // Soil.g:286:1: ( 'new' objType= simpleType ( LPAREN objNames= exprListMin1 RPAREN )? )
            // Soil.g:287:3: 'new' objType= simpleType ( LPAREN objNames= exprListMin1 RPAREN )?
            {
            match(input,44,FOLLOW_44_in_objCreateStat622); if (state.failed) return retval;
            pushFollow(FOLLOW_simpleType_in_objCreateStat630);
            objType=simpleType();

            state._fsp--;
            if (state.failed) return retval;
            // Soil.g:289:3: ( LPAREN objNames= exprListMin1 RPAREN )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==LPAREN) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // Soil.g:290:5: LPAREN objNames= exprListMin1 RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_objCreateStat640); if (state.failed) return retval;
                    pushFollow(FOLLOW_exprListMin1_in_objCreateStat652);
                    objNames=exprListMin1();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,RPAREN,FOLLOW_RPAREN_in_objCreateStat658); if (state.failed) return retval;

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
    // Soil.g:317:1: lobjCreateStat returns [ASTNewLinkObjectStatement n] : 'new' assoc= IDENT ( LPAREN name= inSoilExpression RPAREN )? 'between' LPAREN p= rValListMin2 RPAREN ;
    public final ASTNewLinkObjectStatement lobjCreateStat() throws RecognitionException {
        ASTNewLinkObjectStatement n = null;

        Token assoc=null;
        ASTExpression name = null;

        List<ASTRValue> p = null;


        try {
            // Soil.g:318:1: ( 'new' assoc= IDENT ( LPAREN name= inSoilExpression RPAREN )? 'between' LPAREN p= rValListMin2 RPAREN )
            // Soil.g:319:3: 'new' assoc= IDENT ( LPAREN name= inSoilExpression RPAREN )? 'between' LPAREN p= rValListMin2 RPAREN
            {
            match(input,44,FOLLOW_44_in_lobjCreateStat689); if (state.failed) return n;
            assoc=(Token)match(input,IDENT,FOLLOW_IDENT_in_lobjCreateStat697); if (state.failed) return n;
            // Soil.g:321:3: ( LPAREN name= inSoilExpression RPAREN )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==LPAREN) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // Soil.g:322:5: LPAREN name= inSoilExpression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_lobjCreateStat707); if (state.failed) return n;
                    pushFollow(FOLLOW_inSoilExpression_in_lobjCreateStat719);
                    name=inSoilExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_lobjCreateStat725); if (state.failed) return n;

                    }
                    break;

            }

            match(input,45,FOLLOW_45_in_lobjCreateStat734); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_lobjCreateStat738); if (state.failed) return n;
            pushFollow(FOLLOW_rValListMin2_in_lobjCreateStat748);
            p=rValListMin2();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_lobjCreateStat752); if (state.failed) return n;
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
    // Soil.g:342:1: objDestroyStat returns [ASTStatement n] : 'destroy' el= exprListMin1 ;
    public final SoilParser.objDestroyStat_return objDestroyStat() throws RecognitionException {
        SoilParser.objDestroyStat_return retval = new SoilParser.objDestroyStat_return();
        retval.start = input.LT(1);

        List<ASTExpression> el = null;


        try {
            // Soil.g:343:1: ( 'destroy' el= exprListMin1 )
            // Soil.g:344:3: 'destroy' el= exprListMin1
            {
            match(input,46,FOLLOW_46_in_objDestroyStat778); if (state.failed) return retval;
            pushFollow(FOLLOW_exprListMin1_in_objDestroyStat786);
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
    // Soil.g:365:1: lnkInsStat returns [ASTLinkInsertionStatement n] : 'insert' LPAREN p= rValListMin2 RPAREN 'into' ass= IDENT ;
    public final ASTLinkInsertionStatement lnkInsStat() throws RecognitionException {
        ASTLinkInsertionStatement n = null;

        Token ass=null;
        List<ASTRValue> p = null;


        try {
            // Soil.g:366:1: ( 'insert' LPAREN p= rValListMin2 RPAREN 'into' ass= IDENT )
            // Soil.g:367:3: 'insert' LPAREN p= rValListMin2 RPAREN 'into' ass= IDENT
            {
            match(input,47,FOLLOW_47_in_lnkInsStat812); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_lnkInsStat816); if (state.failed) return n;
            pushFollow(FOLLOW_rValListMin2_in_lnkInsStat826);
            p=rValListMin2();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_lnkInsStat830); if (state.failed) return n;
            match(input,48,FOLLOW_48_in_lnkInsStat834); if (state.failed) return n;
            ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_lnkInsStat842); if (state.failed) return n;
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
    // Soil.g:381:1: lnkDelStat returns [ASTLinkDeletionStatement n] : 'delete' LPAREN p= rValListMin2 RPAREN 'from' ass= IDENT ;
    public final ASTLinkDeletionStatement lnkDelStat() throws RecognitionException {
        ASTLinkDeletionStatement n = null;

        Token ass=null;
        List<ASTRValue> p = null;


        try {
            // Soil.g:382:1: ( 'delete' LPAREN p= rValListMin2 RPAREN 'from' ass= IDENT )
            // Soil.g:383:3: 'delete' LPAREN p= rValListMin2 RPAREN 'from' ass= IDENT
            {
            match(input,49,FOLLOW_49_in_lnkDelStat866); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_lnkDelStat870); if (state.failed) return n;
            pushFollow(FOLLOW_rValListMin2_in_lnkDelStat880);
            p=rValListMin2();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_lnkDelStat884); if (state.failed) return n;
            match(input,50,FOLLOW_50_in_lnkDelStat888); if (state.failed) return n;
            ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_lnkDelStat897); if (state.failed) return n;
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
    // Soil.g:397:1: condExStat returns [ASTConditionalExecutionStatement n] : 'if' con= inSoilExpression 'then' ts= stat ( 'else' es= stat )? 'end' ;
    public final ASTConditionalExecutionStatement condExStat() throws RecognitionException {
        ASTConditionalExecutionStatement n = null;

        ASTExpression con = null;

        SoilParser.stat_return ts = null;

        SoilParser.stat_return es = null;



          ASTStatement elseStat = new ASTEmptyStatement();

        try {
            // Soil.g:401:1: ( 'if' con= inSoilExpression 'then' ts= stat ( 'else' es= stat )? 'end' )
            // Soil.g:402:3: 'if' con= inSoilExpression 'then' ts= stat ( 'else' es= stat )? 'end'
            {
            match(input,51,FOLLOW_51_in_condExStat928); if (state.failed) return n;
            pushFollow(FOLLOW_inSoilExpression_in_condExStat937);
            con=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            match(input,52,FOLLOW_52_in_condExStat941); if (state.failed) return n;
            pushFollow(FOLLOW_stat_in_condExStat950);
            ts=stat();

            state._fsp--;
            if (state.failed) return n;
            // Soil.g:406:3: ( 'else' es= stat )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==53) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // Soil.g:407:5: 'else' es= stat
                    {
                    match(input,53,FOLLOW_53_in_condExStat961); if (state.failed) return n;
                    pushFollow(FOLLOW_stat_in_condExStat973);
                    es=stat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       elseStat = (es!=null?es.n:null); 
                    }

                    }
                    break;

            }

            match(input,54,FOLLOW_54_in_condExStat984); if (state.failed) return n;
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
    // Soil.g:418:1: iterStat returns [ASTIterationStatement n] : 'for' var= IDENT 'in' set= inSoilExpression 'do' s= stat 'end' ;
    public final ASTIterationStatement iterStat() throws RecognitionException {
        ASTIterationStatement n = null;

        Token var=null;
        ASTExpression set = null;

        SoilParser.stat_return s = null;


        try {
            // Soil.g:419:1: ( 'for' var= IDENT 'in' set= inSoilExpression 'do' s= stat 'end' )
            // Soil.g:420:3: 'for' var= IDENT 'in' set= inSoilExpression 'do' s= stat 'end'
            {
            match(input,55,FOLLOW_55_in_iterStat1009); if (state.failed) return n;
            var=(Token)match(input,IDENT,FOLLOW_IDENT_in_iterStat1017); if (state.failed) return n;
            match(input,56,FOLLOW_56_in_iterStat1021); if (state.failed) return n;
            pushFollow(FOLLOW_inSoilExpression_in_iterStat1029);
            set=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            match(input,57,FOLLOW_57_in_iterStat1033); if (state.failed) return n;
            pushFollow(FOLLOW_stat_in_iterStat1041);
            s=stat();

            state._fsp--;
            if (state.failed) return n;
            match(input,54,FOLLOW_54_in_iterStat1045); if (state.failed) return n;
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
    // Soil.g:435:1: callStat returns [ASTOperationCallStatement n] : e= inSoilExpression ;
    public final ASTOperationCallStatement callStat() throws RecognitionException {
        ASTOperationCallStatement n = null;

        ASTExpression e = null;


        try {
            // Soil.g:436:1: (e= inSoilExpression )
            // Soil.g:437:3: e= inSoilExpression
            {
            pushFollow(FOLLOW_inSoilExpression_in_callStat1075);
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
    // Soil.g:455:1: nothing : ;
    public final void nothing() throws RecognitionException {
        try {
            // Soil.g:456:1: ()
            // Soil.g:457:1: 
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
    // Soil.g:463:1: rValue returns [ASTRValue n] options {backtrack=true; memoize=true; } : (e= inSoilExpression | loc= lobjCreateStat | 'new' objType= simpleType ( LPAREN objName= inSoilExpression RPAREN )? );
    public final SoilParser.rValue_return rValue() throws RecognitionException {
        SoilParser.rValue_return retval = new SoilParser.rValue_return();
        retval.start = input.LT(1);
        int rValue_StartIndex = input.index();
        ASTExpression e = null;

        ASTNewLinkObjectStatement loc = null;

        ASTSimpleType objType = null;

        ASTExpression objName = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }
            // Soil.g:468:1: (e= inSoilExpression | loc= lobjCreateStat | 'new' objType= simpleType ( LPAREN objName= inSoilExpression RPAREN )? )
            int alt9=3;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // Soil.g:469:3: e= inSoilExpression
                    {
                    pushFollow(FOLLOW_inSoilExpression_in_rValue1147);
                    e=inSoilExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n = new ASTRValueExpressionOrOpCall(e); 
                    }

                    }
                    break;
                case 2 :
                    // Soil.g:472:3: loc= lobjCreateStat
                    {
                    pushFollow(FOLLOW_lobjCreateStat_in_rValue1163);
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
                    // Soil.g:479:3: 'new' objType= simpleType ( LPAREN objName= inSoilExpression RPAREN )?
                    {
                    match(input,44,FOLLOW_44_in_rValue1175); if (state.failed) return retval;
                    pushFollow(FOLLOW_simpleType_in_rValue1184);
                    objType=simpleType();

                    state._fsp--;
                    if (state.failed) return retval;
                    // Soil.g:481:3: ( LPAREN objName= inSoilExpression RPAREN )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0==LPAREN) ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // Soil.g:482:5: LPAREN objName= inSoilExpression RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_rValue1194); if (state.failed) return retval;
                            pushFollow(FOLLOW_inSoilExpression_in_rValue1206);
                            objName=inSoilExpression();

                            state._fsp--;
                            if (state.failed) return retval;
                            match(input,RPAREN,FOLLOW_RPAREN_in_rValue1212); if (state.failed) return retval;

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
            if ( state.backtracking>0 ) { memoize(input, 17, rValue_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "rValue"


    // $ANTLR start "rValList"
    // Soil.g:501:1: rValList returns [List<ASTRValue> n] : ( nothing | rl= rValListMin1 );
    public final List<ASTRValue> rValList() throws RecognitionException {
        List<ASTRValue> n = null;

        List<ASTRValue> rl = null;


        try {
            // Soil.g:502:1: ( nothing | rl= rValListMin1 )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==EOF) ) {
                alt10=1;
            }
            else if ( (LA10_0==IDENT||LA10_0==LPAREN||(LA10_0>=PLUS && LA10_0<=MINUS)||LA10_0==AT||(LA10_0>=INT && LA10_0<=HASH)||LA10_0==44||LA10_0==51||LA10_0==58||LA10_0==64||(LA10_0>=67 && LA10_0<=70)||(LA10_0>=72 && LA10_0<=83)) ) {
                alt10=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // Soil.g:503:3: nothing
                    {
                    pushFollow(FOLLOW_nothing_in_rValList1240);
                    nothing();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ArrayList<ASTRValue>(); 
                    }

                    }
                    break;
                case 2 :
                    // Soil.g:506:3: rl= rValListMin1
                    {
                    pushFollow(FOLLOW_rValListMin1_in_rValList1267);
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
    // Soil.g:514:1: rValListMin1 returns [List<ASTRValue> n] : r= rValue ( COMMA r= rValue )* ;
    public final List<ASTRValue> rValListMin1() throws RecognitionException {
        List<ASTRValue> n = null;

        SoilParser.rValue_return r = null;



          n = new ArrayList<ASTRValue>();

        try {
            // Soil.g:518:1: (r= rValue ( COMMA r= rValue )* )
            // Soil.g:519:3: r= rValue ( COMMA r= rValue )*
            {
            pushFollow(FOLLOW_rValue_in_rValListMin11300);
            r=rValue();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.add((r!=null?r.n:null)); 
            }
            // Soil.g:521:3: ( COMMA r= rValue )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==COMMA) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // Soil.g:522:5: COMMA r= rValue
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_rValListMin11314); if (state.failed) return n;
            	    pushFollow(FOLLOW_rValue_in_rValListMin11324);
            	    r=rValue();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.add((r!=null?r.n:null)); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop11;
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
    // Soil.g:532:1: rValListMin2 returns [List<ASTRValue> n] : r= rValue COMMA r= rValue ( COMMA r= rValue )* ;
    public final List<ASTRValue> rValListMin2() throws RecognitionException {
        List<ASTRValue> n = null;

        SoilParser.rValue_return r = null;



          n = new ArrayList<ASTRValue>();

        try {
            // Soil.g:536:1: (r= rValue COMMA r= rValue ( COMMA r= rValue )* )
            // Soil.g:537:3: r= rValue COMMA r= rValue ( COMMA r= rValue )*
            {
            pushFollow(FOLLOW_rValue_in_rValListMin21363);
            r=rValue();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.add((r!=null?r.n:null)); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_rValListMin21371); if (state.failed) return n;
            pushFollow(FOLLOW_rValue_in_rValListMin21379);
            r=rValue();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.add((r!=null?r.n:null)); 
            }
            // Soil.g:542:3: ( COMMA r= rValue )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==COMMA) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // Soil.g:543:5: COMMA r= rValue
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_rValListMin21393); if (state.failed) return n;
            	    pushFollow(FOLLOW_rValue_in_rValListMin21403);
            	    r=rValue();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.add((r!=null?r.n:null)); 
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
        return n;
    }
    // $ANTLR end "rValListMin2"


    // $ANTLR start "inSoilExpression"
    // Soil.g:553:1: inSoilExpression returns [ASTExpression n] : e= expression ;
    public final ASTExpression inSoilExpression() throws RecognitionException {
        ASTExpression n = null;

        SoilParser.expression_return e = null;


        try {
            // Soil.g:554:1: (e= expression )
            // Soil.g:555:3: e= expression
            {
            pushFollow(FOLLOW_expression_in_inSoilExpression1437);
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
    // Soil.g:564:1: exprList returns [List<ASTExpression> n] : ( nothing | el= exprListMin1 );
    public final List<ASTExpression> exprList() throws RecognitionException {
        List<ASTExpression> n = null;

        List<ASTExpression> el = null;


        try {
            // Soil.g:565:1: ( nothing | el= exprListMin1 )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==RPAREN) ) {
                alt13=1;
            }
            else if ( (LA13_0==IDENT||LA13_0==LPAREN||(LA13_0>=PLUS && LA13_0<=MINUS)||LA13_0==AT||(LA13_0>=INT && LA13_0<=HASH)||LA13_0==51||LA13_0==58||LA13_0==64||(LA13_0>=67 && LA13_0<=70)||(LA13_0>=72 && LA13_0<=83)) ) {
                alt13=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // Soil.g:566:3: nothing
                    {
                    pushFollow(FOLLOW_nothing_in_exprList1466);
                    nothing();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ArrayList<ASTExpression>(); 
                    }

                    }
                    break;
                case 2 :
                    // Soil.g:569:3: el= exprListMin1
                    {
                    pushFollow(FOLLOW_exprListMin1_in_exprList1484);
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
    // Soil.g:577:1: exprListMin1 returns [List<ASTExpression> n] : e= inSoilExpression ( COMMA e= inSoilExpression )* ;
    public final List<ASTExpression> exprListMin1() throws RecognitionException {
        List<ASTExpression> n = null;

        ASTExpression e = null;



          n = new ArrayList<ASTExpression>();

        try {
            // Soil.g:581:1: (e= inSoilExpression ( COMMA e= inSoilExpression )* )
            // Soil.g:582:3: e= inSoilExpression ( COMMA e= inSoilExpression )*
            {
            pushFollow(FOLLOW_inSoilExpression_in_exprListMin11517);
            e=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               if (e != null) n.add(e); 
            }
            // Soil.g:584:3: ( COMMA e= inSoilExpression )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==COMMA) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // Soil.g:585:5: COMMA e= inSoilExpression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_exprListMin11532); if (state.failed) return n;
            	    pushFollow(FOLLOW_inSoilExpression_in_exprListMin11542);
            	    e=inSoilExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       if (e != null) n.add(e); 
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
    // $ANTLR end "exprListMin1"


    // $ANTLR start "exprListMin2"
    // Soil.g:595:1: exprListMin2 returns [List<ASTExpression> n] : e= inSoilExpression COMMA e= inSoilExpression ( COMMA e= inSoilExpression )* ;
    public final List<ASTExpression> exprListMin2() throws RecognitionException {
        List<ASTExpression> n = null;

        ASTExpression e = null;



          n = new ArrayList<ASTExpression>();

        try {
            // Soil.g:599:1: (e= inSoilExpression COMMA e= inSoilExpression ( COMMA e= inSoilExpression )* )
            // Soil.g:600:3: e= inSoilExpression COMMA e= inSoilExpression ( COMMA e= inSoilExpression )*
            {
            pushFollow(FOLLOW_inSoilExpression_in_exprListMin21582);
            e=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               if (e != null) n.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_exprListMin21590); if (state.failed) return n;
            pushFollow(FOLLOW_inSoilExpression_in_exprListMin21598);
            e=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               if (e != null) n.add(e); 
            }
            // Soil.g:605:3: ( COMMA e= inSoilExpression )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==COMMA) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // Soil.g:606:5: COMMA e= inSoilExpression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_exprListMin21612); if (state.failed) return n;
            	    pushFollow(FOLLOW_inSoilExpression_in_exprListMin21622);
            	    e=inSoilExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       if (e != null) n.add(e); 
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
    // $ANTLR end "exprListMin2"


    // $ANTLR start "identList"
    // Soil.g:616:1: identList returns [List<String> n] : ( nothing | il= identListMin1 );
    public final List<String> identList() throws RecognitionException {
        List<String> n = null;

        List<String> il = null;


        try {
            // Soil.g:617:1: ( nothing | il= identListMin1 )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==EOF||LA16_0==SEMI||LA16_0==RPAREN||(LA16_0>=53 && LA16_0<=54)) ) {
                alt16=1;
            }
            else if ( (LA16_0==IDENT) ) {
                alt16=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // Soil.g:618:3: nothing
                    {
                    pushFollow(FOLLOW_nothing_in_identList1652);
                    nothing();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ArrayList<String>(); 
                    }

                    }
                    break;
                case 2 :
                    // Soil.g:621:3: il= identListMin1
                    {
                    pushFollow(FOLLOW_identListMin1_in_identList1669);
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
    // Soil.g:629:1: identListMin1 returns [List<String> n] : id= IDENT ( COMMA id= IDENT )* ;
    public final List<String> identListMin1() throws RecognitionException {
        List<String> n = null;

        Token id=null;


          n = new ArrayList<String>();

        try {
            // Soil.g:633:1: (id= IDENT ( COMMA id= IDENT )* )
            // Soil.g:634:3: id= IDENT ( COMMA id= IDENT )*
            {
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_identListMin11703); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.add((id!=null?id.getText():null)); 
            }
            // Soil.g:636:3: ( COMMA id= IDENT )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==COMMA) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // Soil.g:637:5: COMMA id= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_identListMin11717); if (state.failed) return n;
            	    id=(Token)match(input,IDENT,FOLLOW_IDENT_in_identListMin11727); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {

            	          n.add((id!=null?id.getText():null)); 
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
    // $ANTLR end "identListMin1"


    // $ANTLR start "expressionOnly"
    // Soil.g:678:1: expressionOnly returns [ASTExpression n] : nExp= expression EOF ;
    public final ASTExpression expressionOnly() throws RecognitionException {
        ASTExpression n = null;

        SoilParser.expression_return nExp = null;


        try {
            // Soil.g:679:1: (nExp= expression EOF )
            // Soil.g:680:5: nExp= expression EOF
            {
            pushFollow(FOLLOW_expression_in_expressionOnly1770);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_expressionOnly1772); if (state.failed) return n;
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
    // Soil.g:687:1: expression returns [ASTExpression n] : ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression ;
    public final SoilParser.expression_return expression() throws RecognitionException {
        SoilParser.expression_return retval = new SoilParser.expression_return();
        retval.start = input.LT(1);

        Token name=null;
        ASTType t = null;

        SoilParser.expression_return e1 = null;

        ASTExpression nCndImplies = null;


         
          ASTLetExpression prevLet = null, firstLet = null;
          ASTExpression e2;
          Token tok = null;

        try {
            // Soil.g:693:1: ( ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression )
            // Soil.g:694:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of expression */ 
            }
            // Soil.g:695:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==58) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // Soil.g:696:7: 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in'
            	    {
            	    match(input,58,FOLLOW_58_in_expression1820); if (state.failed) return retval;
            	    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_expression1824); if (state.failed) return retval;
            	    // Soil.g:696:24: ( COLON t= type )?
            	    int alt18=2;
            	    int LA18_0 = input.LA(1);

            	    if ( (LA18_0==COLON) ) {
            	        alt18=1;
            	    }
            	    switch (alt18) {
            	        case 1 :
            	            // Soil.g:696:26: COLON t= type
            	            {
            	            match(input,COLON,FOLLOW_COLON_in_expression1828); if (state.failed) return retval;
            	            pushFollow(FOLLOW_type_in_expression1832);
            	            t=type();

            	            state._fsp--;
            	            if (state.failed) return retval;

            	            }
            	            break;

            	    }

            	    match(input,EQUAL,FOLLOW_EQUAL_in_expression1837); if (state.failed) return retval;
            	    pushFollow(FOLLOW_expression_in_expression1841);
            	    e1=expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    match(input,56,FOLLOW_56_in_expression1843); if (state.failed) return retval;
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
            	    break loop19;
                }
            } while (true);

            pushFollow(FOLLOW_conditionalImpliesExpression_in_expression1868);
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
    // Soil.g:724:1: paramList returns [List paramList] : LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN ;
    public final List paramList() throws RecognitionException {
        List paramList = null;

        ASTVariableDeclaration v = null;


         paramList = new ArrayList(); 
        try {
            // Soil.g:726:1: ( LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN )
            // Soil.g:727:5: LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN
            {
            match(input,LPAREN,FOLLOW_LPAREN_in_paramList1901); if (state.failed) return paramList;
            // Soil.g:728:5: (v= variableDeclaration ( COMMA v= variableDeclaration )* )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==IDENT) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // Soil.g:729:7: v= variableDeclaration ( COMMA v= variableDeclaration )*
                    {
                    pushFollow(FOLLOW_variableDeclaration_in_paramList1918);
                    v=variableDeclaration();

                    state._fsp--;
                    if (state.failed) return paramList;
                    if ( state.backtracking==0 ) {
                       paramList.add(v); 
                    }
                    // Soil.g:730:7: ( COMMA v= variableDeclaration )*
                    loop20:
                    do {
                        int alt20=2;
                        int LA20_0 = input.LA(1);

                        if ( (LA20_0==COMMA) ) {
                            alt20=1;
                        }


                        switch (alt20) {
                    	case 1 :
                    	    // Soil.g:730:9: COMMA v= variableDeclaration
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_paramList1930); if (state.failed) return paramList;
                    	    pushFollow(FOLLOW_variableDeclaration_in_paramList1934);
                    	    v=variableDeclaration();

                    	    state._fsp--;
                    	    if (state.failed) return paramList;
                    	    if ( state.backtracking==0 ) {
                    	       paramList.add(v); 
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

            match(input,RPAREN,FOLLOW_RPAREN_in_paramList1954); if (state.failed) return paramList;

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
    // Soil.g:738:1: idList returns [List idList] : id0= IDENT ( COMMA idn= IDENT )* ;
    public final List idList() throws RecognitionException {
        List idList = null;

        Token id0=null;
        Token idn=null;

         idList = new ArrayList(); 
        try {
            // Soil.g:740:1: (id0= IDENT ( COMMA idn= IDENT )* )
            // Soil.g:741:5: id0= IDENT ( COMMA idn= IDENT )*
            {
            id0=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList1983); if (state.failed) return idList;
            if ( state.backtracking==0 ) {
               idList.add(id0); 
            }
            // Soil.g:742:5: ( COMMA idn= IDENT )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==COMMA) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // Soil.g:742:7: COMMA idn= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_idList1993); if (state.failed) return idList;
            	    idn=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList1997); if (state.failed) return idList;
            	    if ( state.backtracking==0 ) {
            	       idList.add(idn); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop22;
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
    // Soil.g:750:1: variableDeclaration returns [ASTVariableDeclaration n] : name= IDENT COLON t= type ;
    public final ASTVariableDeclaration variableDeclaration() throws RecognitionException {
        ASTVariableDeclaration n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Soil.g:751:1: (name= IDENT COLON t= type )
            // Soil.g:752:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableDeclaration2028); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableDeclaration2030); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableDeclaration2034);
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
    // Soil.g:760:1: conditionalImpliesExpression returns [ASTExpression n] : nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* ;
    public final ASTExpression conditionalImpliesExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndOrExp = null;

        ASTExpression n1 = null;


        try {
            // Soil.g:761:1: (nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* )
            // Soil.g:762:5: nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )*
            {
            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2070);
            nCndOrExp=conditionalOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndOrExp;
            }
            // Soil.g:763:5: (op= 'implies' n1= conditionalOrExpression )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==59) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // Soil.g:763:7: op= 'implies' n1= conditionalOrExpression
            	    {
            	    op=(Token)match(input,59,FOLLOW_59_in_conditionalImpliesExpression2083); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2087);
            	    n1=conditionalOrExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
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
    // $ANTLR end "conditionalImpliesExpression"


    // $ANTLR start "conditionalOrExpression"
    // Soil.g:772:1: conditionalOrExpression returns [ASTExpression n] : nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* ;
    public final ASTExpression conditionalOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndXorExp = null;

        ASTExpression n1 = null;


        try {
            // Soil.g:773:1: (nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* )
            // Soil.g:774:5: nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )*
            {
            pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2132);
            nCndXorExp=conditionalXOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndXorExp;
            }
            // Soil.g:775:5: (op= 'or' n1= conditionalXOrExpression )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==60) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // Soil.g:775:7: op= 'or' n1= conditionalXOrExpression
            	    {
            	    op=(Token)match(input,60,FOLLOW_60_in_conditionalOrExpression2145); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2149);
            	    n1=conditionalXOrExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop24;
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
    // Soil.g:784:1: conditionalXOrExpression returns [ASTExpression n] : nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* ;
    public final ASTExpression conditionalXOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndAndExp = null;

        ASTExpression n1 = null;


        try {
            // Soil.g:785:1: (nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* )
            // Soil.g:786:5: nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )*
            {
            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2193);
            nCndAndExp=conditionalAndExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndAndExp;
            }
            // Soil.g:787:5: (op= 'xor' n1= conditionalAndExpression )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==61) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // Soil.g:787:7: op= 'xor' n1= conditionalAndExpression
            	    {
            	    op=(Token)match(input,61,FOLLOW_61_in_conditionalXOrExpression2206); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2210);
            	    n1=conditionalAndExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop25;
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
    // Soil.g:796:1: conditionalAndExpression returns [ASTExpression n] : nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* ;
    public final ASTExpression conditionalAndExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nEqExp = null;

        ASTExpression n1 = null;


        try {
            // Soil.g:797:1: (nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* )
            // Soil.g:798:5: nEqExp= equalityExpression (op= 'and' n1= equalityExpression )*
            {
            pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression2254);
            nEqExp=equalityExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nEqExp;
            }
            // Soil.g:799:5: (op= 'and' n1= equalityExpression )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==62) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // Soil.g:799:7: op= 'and' n1= equalityExpression
            	    {
            	    op=(Token)match(input,62,FOLLOW_62_in_conditionalAndExpression2267); if (state.failed) return n;
            	    pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression2271);
            	    n1=equalityExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop26;
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
    // Soil.g:808:1: equalityExpression returns [ASTExpression n] : nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* ;
    public final ASTExpression equalityExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nRelExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Soil.g:810:1: (nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* )
            // Soil.g:811:5: nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            {
            pushFollow(FOLLOW_relationalExpression_in_equalityExpression2319);
            nRelExp=relationalExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nRelExp;
            }
            // Soil.g:812:5: ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( ((LA27_0>=EQUAL && LA27_0<=NOT_EQUAL)) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // Soil.g:812:7: ( EQUAL | NOT_EQUAL ) n1= relationalExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       op = input.LT(1); 
            	    }
            	    if ( (input.LA(1)>=EQUAL && input.LA(1)<=NOT_EQUAL) ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression2348);
            	    n1=relationalExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop27;
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
    // Soil.g:822:1: relationalExpression returns [ASTExpression n] : nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* ;
    public final ASTExpression relationalExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nAddiExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Soil.g:824:1: (nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* )
            // Soil.g:825:5: nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            {
            pushFollow(FOLLOW_additiveExpression_in_relationalExpression2397);
            nAddiExp=additiveExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nAddiExp;
            }
            // Soil.g:826:5: ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( ((LA28_0>=LESS && LA28_0<=GREATER_EQUAL)) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // Soil.g:826:7: ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression
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

            	    pushFollow(FOLLOW_additiveExpression_in_relationalExpression2433);
            	    n1=additiveExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop28;
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
    // Soil.g:836:1: additiveExpression returns [ASTExpression n] : nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* ;
    public final ASTExpression additiveExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nMulExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Soil.g:838:1: (nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* )
            // Soil.g:839:5: nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            {
            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression2483);
            nMulExp=multiplicativeExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nMulExp;
            }
            // Soil.g:840:5: ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( ((LA29_0>=PLUS && LA29_0<=MINUS)) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // Soil.g:840:7: ( PLUS | MINUS ) n1= multiplicativeExpression
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

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression2511);
            	    n1=multiplicativeExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop29;
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
    // Soil.g:851:1: multiplicativeExpression returns [ASTExpression n] : nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* ;
    public final ASTExpression multiplicativeExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // Soil.g:853:1: (nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* )
            // Soil.g:854:5: nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            {
            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression2561);
            nUnExp=unaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nUnExp;
            }
            // Soil.g:855:5: ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( ((LA30_0>=STAR && LA30_0<=SLASH)||LA30_0==63) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // Soil.g:855:7: ( STAR | SLASH | 'div' ) n1= unaryExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       op = input.LT(1); 
            	    }
            	    if ( (input.LA(1)>=STAR && input.LA(1)<=SLASH)||input.LA(1)==63 ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression2593);
            	    n1=unaryExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop30;
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
    // Soil.g:867:1: unaryExpression returns [ASTExpression n] : ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression );
    public final ASTExpression unaryExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression nPosExp = null;


         Token op = null; 
        try {
            // Soil.g:869:1: ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression )
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( ((LA31_0>=PLUS && LA31_0<=MINUS)||LA31_0==64) ) {
                alt31=1;
            }
            else if ( (LA31_0==IDENT||LA31_0==LPAREN||LA31_0==AT||(LA31_0>=INT && LA31_0<=HASH)||LA31_0==51||(LA31_0>=67 && LA31_0<=70)||(LA31_0>=72 && LA31_0<=83)) ) {
                alt31=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;
            }
            switch (alt31) {
                case 1 :
                    // Soil.g:870:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    {
                    // Soil.g:870:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    // Soil.g:870:9: ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression
                    {
                    if ( state.backtracking==0 ) {
                       op = input.LT(1); 
                    }
                    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS)||input.LA(1)==64 ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression2679);
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
                    // Soil.g:874:7: nPosExp= postfixExpression
                    {
                    pushFollow(FOLLOW_postfixExpression_in_unaryExpression2699);
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
    // Soil.g:882:1: postfixExpression returns [ASTExpression n] : nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* ;
    public final ASTExpression postfixExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nPrimExp = null;

        ASTExpression nPc = null;


         boolean arrow = false; 
        try {
            // Soil.g:884:1: (nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* )
            // Soil.g:885:5: nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            {
            pushFollow(FOLLOW_primaryExpression_in_postfixExpression2732);
            nPrimExp=primaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nPrimExp; 
            }
            // Soil.g:886:5: ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==DOT) ) {
                    int LA33_2 = input.LA(2);

                    if ( (LA33_2==IDENT) ) {
                        int LA33_4 = input.LA(3);

                        if ( (LA33_4==EOF||LA33_4==SEMI||(LA33_4>=LPAREN && LA33_4<=COMMA)||(LA33_4>=EQUAL && LA33_4<=LBRACK)||(LA33_4>=RBRACE && LA33_4<=DOTDOT)||(LA33_4>=52 && LA33_4<=54)||(LA33_4>=56 && LA33_4<=57)||(LA33_4>=59 && LA33_4<=63)||LA33_4==71) ) {
                            alt33=1;
                        }


                    }
                    else if ( ((LA33_2>=67 && LA33_2<=70)) ) {
                        alt33=1;
                    }


                }
                else if ( (LA33_0==ARROW) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // Soil.g:887:6: ( ARROW | DOT ) nPc= propertyCall[$n, arrow]
            	    {
            	    // Soil.g:887:6: ( ARROW | DOT )
            	    int alt32=2;
            	    int LA32_0 = input.LA(1);

            	    if ( (LA32_0==ARROW) ) {
            	        alt32=1;
            	    }
            	    else if ( (LA32_0==DOT) ) {
            	        alt32=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 32, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt32) {
            	        case 1 :
            	            // Soil.g:887:8: ARROW
            	            {
            	            match(input,ARROW,FOLLOW_ARROW_in_postfixExpression2750); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = true; 
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // Soil.g:887:34: DOT
            	            {
            	            match(input,DOT,FOLLOW_DOT_in_postfixExpression2756); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = false; 
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_propertyCall_in_postfixExpression2767);
            	    nPc=propertyCall(n, arrow);

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = nPc; 
            	    }

            	    }
            	    break;

            	default :
            	    break loop33;
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
    // Soil.g:903:1: primaryExpression returns [ASTExpression n] : (nLit= literal | nOr= objectReference | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? );
    public final ASTExpression primaryExpression() throws RecognitionException {
        ASTExpression n = null;

        Token id1=null;
        ASTExpression nLit = null;

        ASTExpression nOr = null;

        ASTExpression nPc = null;

        SoilParser.expression_return nExp = null;

        ASTExpression nIfExp = null;


        try {
            // Soil.g:904:1: (nLit= literal | nOr= objectReference | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? )
            int alt36=6;
            switch ( input.LA(1) ) {
            case INT:
            case REAL:
            case STRING:
            case HASH:
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
            case 83:
                {
                alt36=1;
                }
                break;
            case IDENT:
                {
                switch ( input.LA(2) ) {
                case COLON_COLON:
                    {
                    alt36=1;
                    }
                    break;
                case EOF:
                case SEMI:
                case LPAREN:
                case RPAREN:
                case COMMA:
                case EQUAL:
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
                case 52:
                case 53:
                case 54:
                case 56:
                case 57:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 71:
                    {
                    alt36=3;
                    }
                    break;
                case DOT:
                    {
                    int LA36_7 = input.LA(3);

                    if ( (LA36_7==65) ) {
                        alt36=6;
                    }
                    else if ( (LA36_7==IDENT||(LA36_7>=67 && LA36_7<=70)) ) {
                        alt36=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 36, 7, input);

                        throw nvae;
                    }
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 36, 2, input);

                    throw nvae;
                }

                }
                break;
            case AT:
                {
                alt36=2;
                }
                break;
            case 67:
            case 68:
            case 69:
            case 70:
                {
                alt36=3;
                }
                break;
            case LPAREN:
                {
                alt36=4;
                }
                break;
            case 51:
                {
                alt36=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;
            }

            switch (alt36) {
                case 1 :
                    // Soil.g:905:7: nLit= literal
                    {
                    pushFollow(FOLLOW_literal_in_primaryExpression2807);
                    nLit=literal();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nLit; 
                    }

                    }
                    break;
                case 2 :
                    // Soil.g:906:7: nOr= objectReference
                    {
                    pushFollow(FOLLOW_objectReference_in_primaryExpression2821);
                    nOr=objectReference();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nOr; 
                    }

                    }
                    break;
                case 3 :
                    // Soil.g:907:7: nPc= propertyCall[null, false]
                    {
                    pushFollow(FOLLOW_propertyCall_in_primaryExpression2833);
                    nPc=propertyCall(null, false);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nPc; 
                    }

                    }
                    break;
                case 4 :
                    // Soil.g:908:7: LPAREN nExp= expression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression2844); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_primaryExpression2848);
                    nExp=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression2850); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (nExp!=null?nExp.n:null); 
                    }

                    }
                    break;
                case 5 :
                    // Soil.g:909:7: nIfExp= ifExpression
                    {
                    pushFollow(FOLLOW_ifExpression_in_primaryExpression2862);
                    nIfExp=ifExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nIfExp; 
                    }

                    }
                    break;
                case 6 :
                    // Soil.g:911:7: id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )?
                    {
                    id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_primaryExpression2879); if (state.failed) return n;
                    match(input,DOT,FOLLOW_DOT_in_primaryExpression2881); if (state.failed) return n;
                    match(input,65,FOLLOW_65_in_primaryExpression2883); if (state.failed) return n;
                    // Soil.g:911:36: ( LPAREN RPAREN )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==LPAREN) ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // Soil.g:911:38: LPAREN RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression2887); if (state.failed) return n;
                            match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression2889); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTAllInstancesExpression(id1); 
                    }
                    // Soil.g:913:7: ( AT 'pre' )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==AT) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // Soil.g:913:9: AT 'pre'
                            {
                            match(input,AT,FOLLOW_AT_in_primaryExpression2910); if (state.failed) return n;
                            match(input,66,FOLLOW_66_in_primaryExpression2912); if (state.failed) return n;
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
    // Soil.g:917:1: objectReference returns [ASTExpression n] : AT objectName= IDENT ;
    public final ASTExpression objectReference() throws RecognitionException {
        ASTExpression n = null;

        Token objectName=null;

        try {
            // Soil.g:918:1: ( AT objectName= IDENT )
            // Soil.g:919:3: AT objectName= IDENT
            {
            match(input,AT,FOLLOW_AT_in_objectReference2939); if (state.failed) return n;
            objectName=(Token)match(input,IDENT,FOLLOW_IDENT_in_objectReference2947); if (state.failed) return n;
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
    // Soil.g:934:1: propertyCall[ASTExpression source, boolean followsArrow] returns [ASTExpression n] : ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] );
    public final ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExpQuery = null;

        ASTExpression nExpIterate = null;

        SoilParser.operationExpression_return nExpOperation = null;

        ASTTypeArgExpression nExpType = null;


        try {
            // Soil.g:935:1: ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] )
            int alt37=4;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA37_1 = input.LA(2);

                if ( ((( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )&&( input.LA(2) == LPAREN ))) ) {
                    alt37=1;
                }
                else if ( (true) ) {
                    alt37=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 37, 1, input);

                    throw nvae;
                }
                }
                break;
            case 67:
                {
                alt37=2;
                }
                break;
            case 68:
            case 69:
            case 70:
                {
                alt37=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 37, 0, input);

                throw nvae;
            }

            switch (alt37) {
                case 1 :
                    // Soil.g:939:7: {...}?{...}?nExpQuery= queryExpression[source]
                    {
                    if ( !(( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) ");
                    }
                    if ( !(( input.LA(2) == LPAREN )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " input.LA(2) == LPAREN ");
                    }
                    pushFollow(FOLLOW_queryExpression_in_propertyCall3015);
                    nExpQuery=queryExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpQuery; 
                    }

                    }
                    break;
                case 2 :
                    // Soil.g:942:7: nExpIterate= iterateExpression[source]
                    {
                    pushFollow(FOLLOW_iterateExpression_in_propertyCall3028);
                    nExpIterate=iterateExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpIterate; 
                    }

                    }
                    break;
                case 3 :
                    // Soil.g:943:7: nExpOperation= operationExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_operationExpression_in_propertyCall3041);
                    nExpOperation=operationExpression(source, followsArrow);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (nExpOperation!=null?nExpOperation.n:null); 
                    }

                    }
                    break;
                case 4 :
                    // Soil.g:944:7: nExpType= typeExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_typeExpression_in_propertyCall3054);
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
    // Soil.g:953:1: queryExpression[ASTExpression range] returns [ASTExpression n] : op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN ;
    public final ASTExpression queryExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTElemVarsDeclaration decls = null;

        SoilParser.expression_return nExp = null;


        ASTElemVarsDeclaration decl = new ASTElemVarsDeclaration(); 
        try {
            // Soil.g:954:69: (op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN )
            // Soil.g:955:5: op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN
            {
            op=(Token)match(input,IDENT,FOLLOW_IDENT_in_queryExpression3089); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_queryExpression3096); if (state.failed) return n;
            // Soil.g:957:5: (decls= elemVarsDeclaration BAR )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==IDENT) ) {
                int LA38_1 = input.LA(2);

                if ( ((LA38_1>=COMMA && LA38_1<=COLON)||LA38_1==BAR) ) {
                    alt38=1;
                }
            }
            switch (alt38) {
                case 1 :
                    // Soil.g:957:7: decls= elemVarsDeclaration BAR
                    {
                    pushFollow(FOLLOW_elemVarsDeclaration_in_queryExpression3107);
                    decls=elemVarsDeclaration();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      decl = decls;
                    }
                    match(input,BAR,FOLLOW_BAR_in_queryExpression3111); if (state.failed) return n;

                    }
                    break;

            }

            pushFollow(FOLLOW_expression_in_queryExpression3122);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_queryExpression3128); if (state.failed) return n;
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
    // Soil.g:971:1: iterateExpression[ASTExpression range] returns [ASTExpression n] : i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN ;
    public final ASTExpression iterateExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTElemVarsDeclaration decls = null;

        ASTVariableInitialization init = null;

        SoilParser.expression_return nExp = null;


        try {
            // Soil.g:971:65: (i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN )
            // Soil.g:972:5: i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN
            {
            i=(Token)match(input,67,FOLLOW_67_in_iterateExpression3160); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_iterateExpression3166); if (state.failed) return n;
            pushFollow(FOLLOW_elemVarsDeclaration_in_iterateExpression3174);
            decls=elemVarsDeclaration();

            state._fsp--;
            if (state.failed) return n;
            match(input,SEMI,FOLLOW_SEMI_in_iterateExpression3176); if (state.failed) return n;
            pushFollow(FOLLOW_variableInitialization_in_iterateExpression3184);
            init=variableInitialization();

            state._fsp--;
            if (state.failed) return n;
            match(input,BAR,FOLLOW_BAR_in_iterateExpression3186); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_iterateExpression3194);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_iterateExpression3200); if (state.failed) return n;
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
    // Soil.g:993:1: operationExpression[ASTExpression source, boolean followsArrow] returns [ASTOperationExpression n] : name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? ;
    public final SoilParser.operationExpression_return operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        SoilParser.operationExpression_return retval = new SoilParser.operationExpression_return();
        retval.start = input.LT(1);

        Token name=null;
        Token rolename=null;
        SoilParser.expression_return e = null;


        try {
            // Soil.g:995:1: (name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? )
            // Soil.g:996:5: name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression3244); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
               retval.n = new ASTOperationExpression(name, source, followsArrow); 
            }
            // Soil.g:999:5: ( LBRACK rolename= IDENT RBRACK )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==LBRACK) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // Soil.g:999:7: LBRACK rolename= IDENT RBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_operationExpression3260); if (state.failed) return retval;
                    rolename=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression3264); if (state.failed) return retval;
                    match(input,RBRACK,FOLLOW_RBRACK_in_operationExpression3266); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n.setExplicitRolename(rolename); 
                    }

                    }
                    break;

            }

            // Soil.g:1001:5: ( AT 'pre' )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==AT) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // Soil.g:1001:7: AT 'pre'
                    {
                    match(input,AT,FOLLOW_AT_in_operationExpression3279); if (state.failed) return retval;
                    match(input,66,FOLLOW_66_in_operationExpression3281); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n.setIsPre(); 
                    }

                    }
                    break;

            }

            // Soil.g:1002:5: ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==LPAREN) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // Soil.g:1003:7: LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_operationExpression3302); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n.hasParentheses(); 
                    }
                    // Soil.g:1004:7: (e= expression ( COMMA e= expression )* )?
                    int alt42=2;
                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==IDENT||LA42_0==LPAREN||(LA42_0>=PLUS && LA42_0<=MINUS)||LA42_0==AT||(LA42_0>=INT && LA42_0<=HASH)||LA42_0==51||LA42_0==58||LA42_0==64||(LA42_0>=67 && LA42_0<=70)||(LA42_0>=72 && LA42_0<=83)) ) {
                        alt42=1;
                    }
                    switch (alt42) {
                        case 1 :
                            // Soil.g:1005:7: e= expression ( COMMA e= expression )*
                            {
                            pushFollow(FOLLOW_expression_in_operationExpression3323);
                            e=expression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                               retval.n.addArg((e!=null?e.n:null)); 
                            }
                            // Soil.g:1006:7: ( COMMA e= expression )*
                            loop41:
                            do {
                                int alt41=2;
                                int LA41_0 = input.LA(1);

                                if ( (LA41_0==COMMA) ) {
                                    alt41=1;
                                }


                                switch (alt41) {
                            	case 1 :
                            	    // Soil.g:1006:9: COMMA e= expression
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_operationExpression3335); if (state.failed) return retval;
                            	    pushFollow(FOLLOW_expression_in_operationExpression3339);
                            	    e=expression();

                            	    state._fsp--;
                            	    if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) {
                            	       retval.n.addArg((e!=null?e.n:null)); 
                            	    }

                            	    }
                            	    break;

                            	default :
                            	    break loop41;
                                }
                            } while (true);


                            }
                            break;

                    }

                    match(input,RPAREN,FOLLOW_RPAREN_in_operationExpression3359); if (state.failed) return retval;

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
    // Soil.g:1019:1: typeExpression[ASTExpression source, boolean followsArrow] returns [ASTTypeArgExpression n] : ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN ;
    public final ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTTypeArgExpression n = null;

        ASTType t = null;


         Token opToken = null; 
        try {
            // Soil.g:1022:1: ( ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN )
            // Soil.g:1023:2: ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN
            {
            if ( state.backtracking==0 ) {
               opToken = input.LT(1); 
            }
            if ( (input.LA(1)>=68 && input.LA(1)<=70) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_typeExpression3424); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_typeExpression3428);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_typeExpression3430); if (state.failed) return n;
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
    // Soil.g:1034:1: elemVarsDeclaration returns [ASTElemVarsDeclaration n] : idListRes= idList ( COLON t= type )? ;
    public final ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException {
        ASTElemVarsDeclaration n = null;

        List idListRes = null;

        ASTType t = null;


         List idList; 
        try {
            // Soil.g:1036:1: (idListRes= idList ( COLON t= type )? )
            // Soil.g:1037:5: idListRes= idList ( COLON t= type )?
            {
            pushFollow(FOLLOW_idList_in_elemVarsDeclaration3469);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            // Soil.g:1038:5: ( COLON t= type )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==COLON) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // Soil.g:1038:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_elemVarsDeclaration3477); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_elemVarsDeclaration3481);
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
    // Soil.g:1047:1: variableInitialization returns [ASTVariableInitialization n] : name= IDENT COLON t= type EQUAL e= expression ;
    public final ASTVariableInitialization variableInitialization() throws RecognitionException {
        ASTVariableInitialization n = null;

        Token name=null;
        ASTType t = null;

        SoilParser.expression_return e = null;


        try {
            // Soil.g:1048:1: (name= IDENT COLON t= type EQUAL e= expression )
            // Soil.g:1049:5: name= IDENT COLON t= type EQUAL e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableInitialization3516); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableInitialization3518); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableInitialization3522);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EQUAL,FOLLOW_EQUAL_in_variableInitialization3524); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_variableInitialization3528);
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
    // Soil.g:1058:1: ifExpression returns [ASTExpression n] : i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' ;
    public final ASTExpression ifExpression() throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        SoilParser.expression_return cond = null;

        SoilParser.expression_return t = null;

        SoilParser.expression_return e = null;


        try {
            // Soil.g:1059:1: (i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' )
            // Soil.g:1060:5: i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif'
            {
            i=(Token)match(input,51,FOLLOW_51_in_ifExpression3560); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression3564);
            cond=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,52,FOLLOW_52_in_ifExpression3566); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression3570);
            t=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,53,FOLLOW_53_in_ifExpression3572); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression3576);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,71,FOLLOW_71_in_ifExpression3578); if (state.failed) return n;
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
    // Soil.g:1080:1: literal returns [ASTExpression n] : (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral );
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
            // Soil.g:1081:1: (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral )
            int alt45=12;
            switch ( input.LA(1) ) {
            case 72:
                {
                alt45=1;
                }
                break;
            case 73:
                {
                alt45=2;
                }
                break;
            case INT:
                {
                alt45=3;
                }
                break;
            case REAL:
                {
                alt45=4;
                }
                break;
            case STRING:
                {
                alt45=5;
                }
                break;
            case HASH:
                {
                alt45=6;
                }
                break;
            case IDENT:
                {
                alt45=7;
                }
                break;
            case 74:
            case 75:
            case 76:
            case 77:
                {
                alt45=8;
                }
                break;
            case 78:
                {
                alt45=9;
                }
                break;
            case 79:
            case 80:
            case 81:
                {
                alt45=10;
                }
                break;
            case 82:
                {
                alt45=11;
                }
                break;
            case 83:
                {
                alt45=12;
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
                    // Soil.g:1082:7: t= 'true'
                    {
                    t=(Token)match(input,72,FOLLOW_72_in_literal3617); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(true); 
                    }

                    }
                    break;
                case 2 :
                    // Soil.g:1083:7: f= 'false'
                    {
                    f=(Token)match(input,73,FOLLOW_73_in_literal3631); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(false); 
                    }

                    }
                    break;
                case 3 :
                    // Soil.g:1084:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_literal3644); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTIntegerLiteral(i); 
                    }

                    }
                    break;
                case 4 :
                    // Soil.g:1085:7: r= REAL
                    {
                    r=(Token)match(input,REAL,FOLLOW_REAL_in_literal3659); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTRealLiteral(r); 
                    }

                    }
                    break;
                case 5 :
                    // Soil.g:1086:7: s= STRING
                    {
                    s=(Token)match(input,STRING,FOLLOW_STRING_in_literal3673); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTStringLiteral(s); 
                    }

                    }
                    break;
                case 6 :
                    // Soil.g:1087:7: HASH enumLit= IDENT
                    {
                    match(input,HASH,FOLLOW_HASH_in_literal3683); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal3687); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumLit);
                    }

                    }
                    break;
                case 7 :
                    // Soil.g:1088:7: enumName= IDENT '::' enumLit= IDENT
                    {
                    enumName=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal3699); if (state.failed) return n;
                    match(input,COLON_COLON,FOLLOW_COLON_COLON_in_literal3701); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal3705); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumName, enumLit); 
                    }

                    }
                    break;
                case 8 :
                    // Soil.g:1089:7: nColIt= collectionLiteral
                    {
                    pushFollow(FOLLOW_collectionLiteral_in_literal3717);
                    nColIt=collectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nColIt; 
                    }

                    }
                    break;
                case 9 :
                    // Soil.g:1090:7: nEColIt= emptyCollectionLiteral
                    {
                    pushFollow(FOLLOW_emptyCollectionLiteral_in_literal3729);
                    nEColIt=emptyCollectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nEColIt; 
                    }

                    }
                    break;
                case 10 :
                    // Soil.g:1091:7: nUndLit= undefinedLiteral
                    {
                    pushFollow(FOLLOW_undefinedLiteral_in_literal3741);
                    nUndLit=undefinedLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nUndLit; 
                    }

                    }
                    break;
                case 11 :
                    // Soil.g:1092:7: nTupleLit= tupleLiteral
                    {
                    pushFollow(FOLLOW_tupleLiteral_in_literal3753);
                    nTupleLit=tupleLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nTupleLit; 
                    }

                    }
                    break;
                case 12 :
                    // Soil.g:1093:7: nDateLit= dateLiteral
                    {
                    pushFollow(FOLLOW_dateLiteral_in_literal3765);
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
    // Soil.g:1101:1: collectionLiteral returns [ASTCollectionLiteral n] : ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE ;
    public final ASTCollectionLiteral collectionLiteral() throws RecognitionException {
        ASTCollectionLiteral n = null;

        ASTCollectionItem ci = null;


         Token op = null; 
        try {
            // Soil.g:1103:1: ( ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE )
            // Soil.g:1104:5: ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE
            {
            if ( state.backtracking==0 ) {
               op = input.LT(1); 
            }
            if ( (input.LA(1)>=74 && input.LA(1)<=77) ) {
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
            match(input,LBRACE,FOLLOW_LBRACE_in_collectionLiteral3832); if (state.failed) return n;
            // Soil.g:1108:5: (ci= collectionItem ( COMMA ci= collectionItem )* )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==IDENT||LA47_0==LPAREN||(LA47_0>=PLUS && LA47_0<=MINUS)||LA47_0==AT||(LA47_0>=INT && LA47_0<=HASH)||LA47_0==51||LA47_0==58||LA47_0==64||(LA47_0>=67 && LA47_0<=70)||(LA47_0>=72 && LA47_0<=83)) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // Soil.g:1109:7: ci= collectionItem ( COMMA ci= collectionItem )*
                    {
                    pushFollow(FOLLOW_collectionItem_in_collectionLiteral3849);
                    ci=collectionItem();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addItem(ci); 
                    }
                    // Soil.g:1110:7: ( COMMA ci= collectionItem )*
                    loop46:
                    do {
                        int alt46=2;
                        int LA46_0 = input.LA(1);

                        if ( (LA46_0==COMMA) ) {
                            alt46=1;
                        }


                        switch (alt46) {
                    	case 1 :
                    	    // Soil.g:1110:9: COMMA ci= collectionItem
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_collectionLiteral3862); if (state.failed) return n;
                    	    pushFollow(FOLLOW_collectionItem_in_collectionLiteral3866);
                    	    ci=collectionItem();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addItem(ci); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop46;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RBRACE,FOLLOW_RBRACE_in_collectionLiteral3885); if (state.failed) return n;

            }

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
    // Soil.g:1119:1: collectionItem returns [ASTCollectionItem n] : e= expression ( DOTDOT e= expression )? ;
    public final ASTCollectionItem collectionItem() throws RecognitionException {
        ASTCollectionItem n = null;

        SoilParser.expression_return e = null;


         n = new ASTCollectionItem(); 
        try {
            // Soil.g:1121:1: (e= expression ( DOTDOT e= expression )? )
            // Soil.g:1122:5: e= expression ( DOTDOT e= expression )?
            {
            pushFollow(FOLLOW_expression_in_collectionItem3914);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setFirst((e!=null?e.n:null)); 
            }
            // Soil.g:1123:5: ( DOTDOT e= expression )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==DOTDOT) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // Soil.g:1123:7: DOTDOT e= expression
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_collectionItem3925); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_collectionItem3929);
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
    // Soil.g:1133:1: emptyCollectionLiteral returns [ASTEmptyCollectionLiteral n] : 'oclEmpty' LPAREN t= collectionType RPAREN ;
    public final ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException {
        ASTEmptyCollectionLiteral n = null;

        ASTCollectionType t = null;


        try {
            // Soil.g:1134:1: ( 'oclEmpty' LPAREN t= collectionType RPAREN )
            // Soil.g:1135:5: 'oclEmpty' LPAREN t= collectionType RPAREN
            {
            match(input,78,FOLLOW_78_in_emptyCollectionLiteral3958); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_emptyCollectionLiteral3960); if (state.failed) return n;
            pushFollow(FOLLOW_collectionType_in_emptyCollectionLiteral3964);
            t=collectionType();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_emptyCollectionLiteral3966); if (state.failed) return n;
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
    // Soil.g:1146:1: undefinedLiteral returns [ASTUndefinedLiteral n] : ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' );
    public final ASTUndefinedLiteral undefinedLiteral() throws RecognitionException {
        ASTUndefinedLiteral n = null;

        ASTType t = null;


        try {
            // Soil.g:1147:1: ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' )
            int alt49=3;
            switch ( input.LA(1) ) {
            case 79:
                {
                alt49=1;
                }
                break;
            case 80:
                {
                alt49=2;
                }
                break;
            case 81:
                {
                alt49=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;
            }

            switch (alt49) {
                case 1 :
                    // Soil.g:1148:5: 'oclUndefined' LPAREN t= type RPAREN
                    {
                    match(input,79,FOLLOW_79_in_undefinedLiteral3996); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_undefinedLiteral3998); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_undefinedLiteral4002);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_undefinedLiteral4004); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(t); 
                    }

                    }
                    break;
                case 2 :
                    // Soil.g:1151:5: 'Undefined'
                    {
                    match(input,80,FOLLOW_80_in_undefinedLiteral4018); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(); 
                    }

                    }
                    break;
                case 3 :
                    // Soil.g:1154:5: 'null'
                    {
                    match(input,81,FOLLOW_81_in_undefinedLiteral4032); if (state.failed) return n;
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
    // Soil.g:1163:1: tupleLiteral returns [ASTTupleLiteral n] : 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE ;
    public final ASTTupleLiteral tupleLiteral() throws RecognitionException {
        ASTTupleLiteral n = null;

        ASTTupleItem ti = null;


         List tiList = new ArrayList(); 
        try {
            // Soil.g:1165:1: ( 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE )
            // Soil.g:1166:5: 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE
            {
            match(input,82,FOLLOW_82_in_tupleLiteral4066); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_tupleLiteral4072); if (state.failed) return n;
            pushFollow(FOLLOW_tupleItem_in_tupleLiteral4080);
            ti=tupleItem();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tiList.add(ti); 
            }
            // Soil.g:1169:5: ( COMMA ti= tupleItem )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==COMMA) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // Soil.g:1169:7: COMMA ti= tupleItem
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleLiteral4091); if (state.failed) return n;
            	    pushFollow(FOLLOW_tupleItem_in_tupleLiteral4095);
            	    ti=tupleItem();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tiList.add(ti); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop50;
                }
            } while (true);

            match(input,RBRACE,FOLLOW_RBRACE_in_tupleLiteral4106); if (state.failed) return n;
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
    // Soil.g:1177:1: tupleItem returns [ASTTupleItem n] : name= IDENT ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) ;
    public final ASTTupleItem tupleItem() throws RecognitionException {
        ASTTupleItem n = null;

        Token name=null;
        ASTType t = null;

        SoilParser.expression_return e = null;


        try {
            // Soil.g:1178:1: (name= IDENT ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) )
            // Soil.g:1179:5: name= IDENT ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tupleItem4137); if (state.failed) return n;
            // Soil.g:1180:5: ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==COLON) ) {
                int LA51_1 = input.LA(2);

                if ( (synpred14_Soil()) ) {
                    alt51=1;
                }
                else if ( (true) ) {
                    alt51=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 51, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA51_0==EQUAL) ) {
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
                    // Soil.g:1183:7: ( COLON type EQUAL )=> COLON t= type EQUAL e= expression
                    {
                    match(input,COLON,FOLLOW_COLON_in_tupleItem4176); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_tupleItem4180);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,EQUAL,FOLLOW_EQUAL_in_tupleItem4182); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_tupleItem4186);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTTupleItem(name, t, (e!=null?e.n:null)); 
                    }

                    }
                    break;
                case 2 :
                    // Soil.g:1186:7: ( COLON | EQUAL ) e= expression
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

                    pushFollow(FOLLOW_expression_in_tupleItem4218);
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
    // Soil.g:1195:1: dateLiteral returns [ASTDateLiteral n] : 'Date' LBRACE v= STRING RBRACE ;
    public final ASTDateLiteral dateLiteral() throws RecognitionException {
        ASTDateLiteral n = null;

        Token v=null;

        try {
            // Soil.g:1196:1: ( 'Date' LBRACE v= STRING RBRACE )
            // Soil.g:1197:5: 'Date' LBRACE v= STRING RBRACE
            {
            match(input,83,FOLLOW_83_in_dateLiteral4263); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_dateLiteral4265); if (state.failed) return n;
            v=(Token)match(input,STRING,FOLLOW_STRING_in_dateLiteral4269); if (state.failed) return n;
            match(input,RBRACE,FOLLOW_RBRACE_in_dateLiteral4271); if (state.failed) return n;
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
    // Soil.g:1207:1: type returns [ASTType n] : (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) ;
    public final ASTType type() throws RecognitionException {
        ASTType n = null;

        ASTSimpleType nTSimple = null;

        ASTCollectionType nTCollection = null;

        ASTTupleType nTTuple = null;


         Token tok = null; 
        try {
            // Soil.g:1209:1: ( (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) )
            // Soil.g:1210:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of type */ 
            }
            // Soil.g:1211:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            int alt52=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt52=1;
                }
                break;
            case 74:
            case 75:
            case 76:
            case 77:
            case 84:
                {
                alt52=2;
                }
                break;
            case 82:
                {
                alt52=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 52, 0, input);

                throw nvae;
            }

            switch (alt52) {
                case 1 :
                    // Soil.g:1212:7: nTSimple= simpleType
                    {
                    pushFollow(FOLLOW_simpleType_in_type4321);
                    nTSimple=simpleType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTSimple; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 2 :
                    // Soil.g:1213:7: nTCollection= collectionType
                    {
                    pushFollow(FOLLOW_collectionType_in_type4333);
                    nTCollection=collectionType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTCollection; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 3 :
                    // Soil.g:1214:7: nTTuple= tupleType
                    {
                    pushFollow(FOLLOW_tupleType_in_type4345);
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
    // Soil.g:1219:1: typeOnly returns [ASTType n] : nT= type EOF ;
    public final ASTType typeOnly() throws RecognitionException {
        ASTType n = null;

        ASTType nT = null;


        try {
            // Soil.g:1220:1: (nT= type EOF )
            // Soil.g:1221:5: nT= type EOF
            {
            pushFollow(FOLLOW_type_in_typeOnly4377);
            nT=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_typeOnly4379); if (state.failed) return n;
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
    // Soil.g:1231:1: simpleType returns [ASTSimpleType n] : name= IDENT ;
    public final ASTSimpleType simpleType() throws RecognitionException {
        ASTSimpleType n = null;

        Token name=null;

        try {
            // Soil.g:1232:1: (name= IDENT )
            // Soil.g:1233:5: name= IDENT
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_simpleType4407); if (state.failed) return n;
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
    // Soil.g:1241:1: collectionType returns [ASTCollectionType n] : ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN ;
    public final ASTCollectionType collectionType() throws RecognitionException {
        ASTCollectionType n = null;

        ASTType elemType = null;


         Token op = null; 
        try {
            // Soil.g:1243:1: ( ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN )
            // Soil.g:1244:5: ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN
            {
            if ( state.backtracking==0 ) {
               op = input.LT(1); 
            }
            if ( (input.LA(1)>=74 && input.LA(1)<=77)||input.LA(1)==84 ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_collectionType4472); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_collectionType4476);
            elemType=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_collectionType4478); if (state.failed) return n;
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
    // Soil.g:1254:1: tupleType returns [ASTTupleType n] : 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN ;
    public final ASTTupleType tupleType() throws RecognitionException {
        ASTTupleType n = null;

        ASTTuplePart tp = null;


         List tpList = new ArrayList(); 
        try {
            // Soil.g:1256:1: ( 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN )
            // Soil.g:1257:5: 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN
            {
            match(input,82,FOLLOW_82_in_tupleType4512); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_tupleType4514); if (state.failed) return n;
            pushFollow(FOLLOW_tuplePart_in_tupleType4523);
            tp=tuplePart();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tpList.add(tp); 
            }
            // Soil.g:1259:5: ( COMMA tp= tuplePart )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==COMMA) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // Soil.g:1259:7: COMMA tp= tuplePart
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleType4534); if (state.failed) return n;
            	    pushFollow(FOLLOW_tuplePart_in_tupleType4538);
            	    tp=tuplePart();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tpList.add(tp); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop53;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_tupleType4550); if (state.failed) return n;
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
    // Soil.g:1268:1: tuplePart returns [ASTTuplePart n] : name= IDENT COLON t= type ;
    public final ASTTuplePart tuplePart() throws RecognitionException {
        ASTTuplePart n = null;

        Token name=null;
        ASTType t = null;


        try {
            // Soil.g:1269:1: (name= IDENT COLON t= type )
            // Soil.g:1270:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tuplePart4582); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_tuplePart4584); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_tuplePart4588);
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

    // $ANTLR start synpred2_Soil
    public final void synpred2_Soil_fragment() throws RecognitionException {   
        SoilParser.varAssignStat_return vas = null;


        // Soil.g:167:5: (vas= varAssignStat )
        // Soil.g:167:5: vas= varAssignStat
        {
        pushFollow(FOLLOW_varAssignStat_in_synpred2_Soil238);
        vas=varAssignStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_Soil

    // $ANTLR start synpred3_Soil
    public final void synpred3_Soil_fragment() throws RecognitionException {   
        ASTAttributeAssignmentStatement aas = null;


        // Soil.g:168:5: (aas= attAssignStat )
        // Soil.g:168:5: aas= attAssignStat
        {
        pushFollow(FOLLOW_attAssignStat_in_synpred3_Soil252);
        aas=attAssignStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_Soil

    // $ANTLR start synpred4_Soil
    public final void synpred4_Soil_fragment() throws RecognitionException {   
        ASTNewLinkObjectStatement lcs = null;


        // Soil.g:169:5: (lcs= lobjCreateStat )
        // Soil.g:169:5: lcs= lobjCreateStat
        {
        pushFollow(FOLLOW_lobjCreateStat_in_synpred4_Soil266);
        lcs=lobjCreateStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_Soil

    // $ANTLR start synpred5_Soil
    public final void synpred5_Soil_fragment() throws RecognitionException {   
        SoilParser.objCreateStat_return ocs = null;


        // Soil.g:170:5: (ocs= objCreateStat )
        // Soil.g:170:5: ocs= objCreateStat
        {
        pushFollow(FOLLOW_objCreateStat_in_synpred5_Soil279);
        ocs=objCreateStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_Soil

    // $ANTLR start synpred9_Soil
    public final void synpred9_Soil_fragment() throws RecognitionException {   
        ASTConditionalExecutionStatement ces = null;


        // Soil.g:174:5: (ces= condExStat )
        // Soil.g:174:5: ces= condExStat
        {
        pushFollow(FOLLOW_condExStat_in_synpred9_Soil340);
        ces=condExStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_Soil

    // $ANTLR start synpred11_Soil
    public final void synpred11_Soil_fragment() throws RecognitionException {   
        Token varName=null;
        SoilParser.rValue_return rVal = null;


        // Soil.g:201:3: (varName= IDENT COLON_EQUAL rVal= rValue )
        // Soil.g:201:3: varName= IDENT COLON_EQUAL rVal= rValue
        {
        varName=(Token)match(input,IDENT,FOLLOW_IDENT_in_synpred11_Soil460); if (state.failed) return ;
        match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_synpred11_Soil464); if (state.failed) return ;
        pushFollow(FOLLOW_rValue_in_synpred11_Soil472);
        rVal=rValue();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred11_Soil

    // $ANTLR start synpred13_Soil
    public final void synpred13_Soil_fragment() throws RecognitionException {   
        ASTNewLinkObjectStatement loc = null;


        // Soil.g:472:3: (loc= lobjCreateStat )
        // Soil.g:472:3: loc= lobjCreateStat
        {
        pushFollow(FOLLOW_lobjCreateStat_in_synpred13_Soil1163);
        loc=lobjCreateStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred13_Soil

    // $ANTLR start synpred14_Soil
    public final void synpred14_Soil_fragment() throws RecognitionException {   
        // Soil.g:1183:7: ( COLON type EQUAL )
        // Soil.g:1183:8: COLON type EQUAL
        {
        match(input,COLON,FOLLOW_COLON_in_synpred14_Soil4167); if (state.failed) return ;
        pushFollow(FOLLOW_type_in_synpred14_Soil4169);
        type();

        state._fsp--;
        if (state.failed) return ;
        match(input,EQUAL,FOLLOW_EQUAL_in_synpred14_Soil4171); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred14_Soil

    // Delegated rules

    public final boolean synpred14_Soil() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_Soil_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_Soil() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_Soil_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_Soil() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_Soil_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_Soil() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_Soil_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred13_Soil() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred13_Soil_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_Soil() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_Soil_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_Soil() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_Soil_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_Soil() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_Soil_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA2 dfa2 = new DFA2(this);
    protected DFA9 dfa9 = new DFA9(this);
    static final String DFA2_eotS =
        "\45\uffff";
    static final String DFA2_eofS =
        "\1\1\44\uffff";
    static final String DFA2_minS =
        "\1\4\4\uffff\26\0\12\uffff";
    static final String DFA2_maxS =
        "\1\123\4\uffff\26\0\12\uffff";
    static final String DFA2_acceptS =
        "\1\uffff\1\1\31\uffff\1\6\1\7\1\10\1\12\1\2\1\3\1\13\1\11\1\4\1"+
        "\5";
    static final String DFA2_specialS =
        "\5\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1"+
        "\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\12\uffff}>";
    static final String[] DFA2_transitionS = {
            "\1\1\1\5\1\uffff\1\30\12\uffff\2\7\3\uffff\1\25\3\uffff\1\12"+
            "\1\13\1\14\1\15\15\uffff\1\32\1\uffff\1\33\1\34\1\uffff\1\35"+
            "\1\uffff\1\31\1\uffff\2\1\1\36\2\uffff\1\6\5\uffff\1\7\2\uffff"+
            "\1\26\3\27\1\uffff\1\10\1\11\4\16\1\17\1\20\1\21\1\22\1\23\1"+
            "\24",
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

    static final short[] DFA2_eot = DFA.unpackEncodedString(DFA2_eotS);
    static final short[] DFA2_eof = DFA.unpackEncodedString(DFA2_eofS);
    static final char[] DFA2_min = DFA.unpackEncodedStringToUnsignedChars(DFA2_minS);
    static final char[] DFA2_max = DFA.unpackEncodedStringToUnsignedChars(DFA2_maxS);
    static final short[] DFA2_accept = DFA.unpackEncodedString(DFA2_acceptS);
    static final short[] DFA2_special = DFA.unpackEncodedString(DFA2_specialS);
    static final short[][] DFA2_transition;

    static {
        int numStates = DFA2_transitionS.length;
        DFA2_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA2_transition[i] = DFA.unpackEncodedString(DFA2_transitionS[i]);
        }
    }

    class DFA2 extends DFA {

        public DFA2(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 2;
            this.eot = DFA2_eot;
            this.eof = DFA2_eof;
            this.min = DFA2_min;
            this.max = DFA2_max;
            this.accept = DFA2_accept;
            this.special = DFA2_special;
            this.transition = DFA2_transition;
        }
        public String getDescription() {
            return "159:1: singleStat returns [ASTStatement n] options {backtrack=true; memoize=true; } : (emp= emptyStat | vas= varAssignStat | aas= attAssignStat | lcs= lobjCreateStat | ocs= objCreateStat | ods= objDestroyStat | lis= lnkInsStat | lds= lnkDelStat | ces= condExStat | its= iterStat | ops= callStat );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA2_5 = input.LA(1);

                         
                        int index2_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Soil()) ) {s = 31;}

                        else if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_5);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA2_6 = input.LA(1);

                         
                        int index2_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_6);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA2_7 = input.LA(1);

                         
                        int index2_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_7);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA2_8 = input.LA(1);

                         
                        int index2_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_8);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA2_9 = input.LA(1);

                         
                        int index2_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_9);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA2_10 = input.LA(1);

                         
                        int index2_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_10);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA2_11 = input.LA(1);

                         
                        int index2_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_11);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA2_12 = input.LA(1);

                         
                        int index2_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_12);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA2_13 = input.LA(1);

                         
                        int index2_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_13);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA2_14 = input.LA(1);

                         
                        int index2_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_14);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA2_15 = input.LA(1);

                         
                        int index2_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_15);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA2_16 = input.LA(1);

                         
                        int index2_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_16);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA2_17 = input.LA(1);

                         
                        int index2_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_17);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA2_18 = input.LA(1);

                         
                        int index2_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_18);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA2_19 = input.LA(1);

                         
                        int index2_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_19);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA2_20 = input.LA(1);

                         
                        int index2_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_20);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA2_21 = input.LA(1);

                         
                        int index2_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_21);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA2_22 = input.LA(1);

                         
                        int index2_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_22);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA2_23 = input.LA(1);

                         
                        int index2_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_23);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA2_24 = input.LA(1);

                         
                        int index2_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_24);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA2_25 = input.LA(1);

                         
                        int index2_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_Soil()) ) {s = 32;}

                        else if ( (synpred9_Soil()) ) {s = 34;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index2_25);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA2_26 = input.LA(1);

                         
                        int index2_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Soil()) ) {s = 35;}

                        else if ( (synpred5_Soil()) ) {s = 36;}

                         
                        input.seek(index2_26);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 2, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA9_eotS =
        "\31\uffff";
    static final String DFA9_eofS =
        "\31\uffff";
    static final String DFA9_minS =
        "\1\5\25\uffff\1\0\2\uffff";
    static final String DFA9_maxS =
        "\1\123\25\uffff\1\0\2\uffff";
    static final String DFA9_acceptS =
        "\1\uffff\1\1\25\uffff\1\2\1\3";
    static final String DFA9_specialS =
        "\26\uffff\1\0\2\uffff}>";
    static final String[] DFA9_transitionS = {
            "\1\1\1\uffff\1\1\12\uffff\2\1\3\uffff\1\1\3\uffff\4\1\15\uffff"+
            "\1\26\6\uffff\1\1\6\uffff\1\1\5\uffff\1\1\2\uffff\4\1\1\uffff"+
            "\14\1",
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
            return "463:1: rValue returns [ASTRValue n] options {backtrack=true; memoize=true; } : (e= inSoilExpression | loc= lobjCreateStat | 'new' objType= simpleType ( LPAREN objName= inSoilExpression RPAREN )? );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA9_22 = input.LA(1);

                         
                        int index9_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_Soil()) ) {s = 23;}

                        else if ( (true) ) {s = 24;}

                         
                        input.seek(index9_22);
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
 

    public static final BitSet FOLLOW_stat_in_statOnly76 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_statOnly80 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nextStat_in_stat111 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_SEMI_in_stat122 = new BitSet(new long[]{0x048AD000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_nextStat_in_stat128 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_singleStat_in_nextStat162 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyStat_in_singleStat217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varAssignStat_in_singleStat238 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attAssignStat_in_singleStat252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lobjCreateStat_in_singleStat266 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objCreateStat_in_singleStat279 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objDestroyStat_in_singleStat293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lnkInsStat_in_singleStat306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lnkDelStat_in_singleStat323 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_condExStat_in_singleStat340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterStat_in_singleStat357 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_callStat_in_singleStat376 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nothing_in_emptyStat407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_varAssignStat460 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_varAssignStat464 = new BitSet(new long[]{0x04081000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_rValue_in_varAssignStat472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identListMin1_in_varAssignStat491 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_varAssignStat495 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_varAssignStat499 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_simpleType_in_varAssignStat507 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_LPAREN_in_varAssignStat517 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_exprList_in_varAssignStat529 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RPAREN_in_varAssignStat535 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_attAssignStat570 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_DOT_in_attAssignStat575 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_attAssignStat584 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_attAssignStat588 = new BitSet(new long[]{0x04081000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_rValue_in_attAssignStat596 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_objCreateStat622 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_simpleType_in_objCreateStat630 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_LPAREN_in_objCreateStat640 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_exprListMin1_in_objCreateStat652 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RPAREN_in_objCreateStat658 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_lobjCreateStat689 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_lobjCreateStat697 = new BitSet(new long[]{0x0000200000000080L});
    public static final BitSet FOLLOW_LPAREN_in_lobjCreateStat707 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_inSoilExpression_in_lobjCreateStat719 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RPAREN_in_lobjCreateStat725 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_45_in_lobjCreateStat734 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_lobjCreateStat738 = new BitSet(new long[]{0x04081000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_rValListMin2_in_lobjCreateStat748 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RPAREN_in_lobjCreateStat752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_objDestroyStat778 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_exprListMin1_in_objDestroyStat786 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_lnkInsStat812 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_lnkInsStat816 = new BitSet(new long[]{0x04081000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_rValListMin2_in_lnkInsStat826 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RPAREN_in_lnkInsStat830 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_48_in_lnkInsStat834 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_lnkInsStat842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_49_in_lnkDelStat866 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_lnkDelStat870 = new BitSet(new long[]{0x04081000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_rValListMin2_in_lnkDelStat880 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RPAREN_in_lnkDelStat884 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_lnkDelStat888 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_lnkDelStat897 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_51_in_condExStat928 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_inSoilExpression_in_condExStat937 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_condExStat941 = new BitSet(new long[]{0x048AD000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_stat_in_condExStat950 = new BitSet(new long[]{0x0060000000000000L});
    public static final BitSet FOLLOW_53_in_condExStat961 = new BitSet(new long[]{0x048AD000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_stat_in_condExStat973 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_condExStat984 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_55_in_iterStat1009 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_iterStat1017 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_iterStat1021 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_inSoilExpression_in_iterStat1029 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_57_in_iterStat1033 = new BitSet(new long[]{0x048AD000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_stat_in_iterStat1041 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_iterStat1045 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_callStat1075 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_rValue1147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lobjCreateStat_in_rValue1163 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_rValue1175 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_simpleType_in_rValue1184 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_LPAREN_in_rValue1194 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_inSoilExpression_in_rValue1206 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RPAREN_in_rValue1212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nothing_in_rValList1240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rValListMin1_in_rValList1267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rValue_in_rValListMin11300 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COMMA_in_rValListMin11314 = new BitSet(new long[]{0x04081000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_rValue_in_rValListMin11324 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_rValue_in_rValListMin21363 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_COMMA_in_rValListMin21371 = new BitSet(new long[]{0x04081000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_rValue_in_rValListMin21379 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COMMA_in_rValListMin21393 = new BitSet(new long[]{0x04081000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_rValue_in_rValListMin21403 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_expression_in_inSoilExpression1437 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nothing_in_exprList1466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exprListMin1_in_exprList1484 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin11517 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COMMA_in_exprListMin11532 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin11542 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin21582 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_COMMA_in_exprListMin21590 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin21598 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COMMA_in_exprListMin21612 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin21622 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_nothing_in_identList1652 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identListMin1_in_identList1669 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_identListMin11703 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COMMA_in_identListMin11717 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_identListMin11727 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_expression_in_expressionOnly1770 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_expressionOnly1772 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_expression1820 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_expression1824 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_COLON_in_expression1828 = new BitSet(new long[]{0x0000000000000020L,0x0000000000143C00L});
    public static final BitSet FOLLOW_type_in_expression1832 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_EQUAL_in_expression1837 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_expression_in_expression1841 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_56_in_expression1843 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_conditionalImpliesExpression_in_expression1868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_paramList1901 = new BitSet(new long[]{0x0000000000000120L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList1918 = new BitSet(new long[]{0x0000000000000500L});
    public static final BitSet FOLLOW_COMMA_in_paramList1930 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList1934 = new BitSet(new long[]{0x0000000000000500L});
    public static final BitSet FOLLOW_RPAREN_in_paramList1954 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_idList1983 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COMMA_in_idList1993 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_idList1997 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_IDENT_in_variableDeclaration2028 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_variableDeclaration2030 = new BitSet(new long[]{0x0000000000000020L,0x0000000000143C00L});
    public static final BitSet FOLLOW_type_in_variableDeclaration2034 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2070 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_59_in_conditionalImpliesExpression2083 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2087 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2132 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_60_in_conditionalOrExpression2145 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2149 = new BitSet(new long[]{0x1000000000000002L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2193 = new BitSet(new long[]{0x2000000000000002L});
    public static final BitSet FOLLOW_61_in_conditionalXOrExpression2206 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2210 = new BitSet(new long[]{0x2000000000000002L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression2254 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_62_in_conditionalAndExpression2267 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression2271 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression2319 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_set_in_equalityExpression2338 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression2348 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression2397 = new BitSet(new long[]{0x000000000003C002L});
    public static final BitSet FOLLOW_set_in_relationalExpression2415 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression2433 = new BitSet(new long[]{0x000000000003C002L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression2483 = new BitSet(new long[]{0x00000000000C0002L});
    public static final BitSet FOLLOW_set_in_additiveExpression2501 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression2511 = new BitSet(new long[]{0x00000000000C0002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression2561 = new BitSet(new long[]{0x8000000000300002L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression2579 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression2593 = new BitSet(new long[]{0x8000000000300002L});
    public static final BitSet FOLLOW_set_in_unaryExpression2655 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression2679 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpression_in_unaryExpression2699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_postfixExpression2732 = new BitSet(new long[]{0x0000000000400202L});
    public static final BitSet FOLLOW_ARROW_in_postfixExpression2750 = new BitSet(new long[]{0x0000000000000020L,0x0000000000000078L});
    public static final BitSet FOLLOW_DOT_in_postfixExpression2756 = new BitSet(new long[]{0x0000000000000020L,0x0000000000000078L});
    public static final BitSet FOLLOW_propertyCall_in_postfixExpression2767 = new BitSet(new long[]{0x0000000000400202L});
    public static final BitSet FOLLOW_literal_in_primaryExpression2807 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objectReference_in_primaryExpression2821 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyCall_in_primaryExpression2833 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression2844 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_expression_in_primaryExpression2848 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression2850 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifExpression_in_primaryExpression2862 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_primaryExpression2879 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_DOT_in_primaryExpression2881 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_primaryExpression2883 = new BitSet(new long[]{0x0000000000800082L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression2887 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression2889 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_AT_in_primaryExpression2910 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_primaryExpression2912 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_in_objectReference2939 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_objectReference2947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_queryExpression_in_propertyCall3015 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterateExpression_in_propertyCall3028 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationExpression_in_propertyCall3041 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeExpression_in_propertyCall3054 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_queryExpression3089 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_queryExpression3096 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_queryExpression3107 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_BAR_in_queryExpression3111 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_expression_in_queryExpression3122 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RPAREN_in_queryExpression3128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_67_in_iterateExpression3160 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_iterateExpression3166 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_iterateExpression3174 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_SEMI_in_iterateExpression3176 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_variableInitialization_in_iterateExpression3184 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_BAR_in_iterateExpression3186 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_expression_in_iterateExpression3194 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RPAREN_in_iterateExpression3200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression3244 = new BitSet(new long[]{0x0000000002800082L});
    public static final BitSet FOLLOW_LBRACK_in_operationExpression3260 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression3264 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_RBRACK_in_operationExpression3266 = new BitSet(new long[]{0x0000000000800082L});
    public static final BitSet FOLLOW_AT_in_operationExpression3279 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_66_in_operationExpression3281 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_LPAREN_in_operationExpression3302 = new BitSet(new long[]{0x04080000788C01A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_expression_in_operationExpression3323 = new BitSet(new long[]{0x0000000000000500L});
    public static final BitSet FOLLOW_COMMA_in_operationExpression3335 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_expression_in_operationExpression3339 = new BitSet(new long[]{0x0000000000000500L});
    public static final BitSet FOLLOW_RPAREN_in_operationExpression3359 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_typeExpression3408 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_typeExpression3424 = new BitSet(new long[]{0x0000000000000020L,0x0000000000143C00L});
    public static final BitSet FOLLOW_type_in_typeExpression3428 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RPAREN_in_typeExpression3430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_elemVarsDeclaration3469 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_COLON_in_elemVarsDeclaration3477 = new BitSet(new long[]{0x0000000000000020L,0x0000000000143C00L});
    public static final BitSet FOLLOW_type_in_elemVarsDeclaration3481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableInitialization3516 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_variableInitialization3518 = new BitSet(new long[]{0x0000000000000020L,0x0000000000143C00L});
    public static final BitSet FOLLOW_type_in_variableInitialization3522 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_EQUAL_in_variableInitialization3524 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_expression_in_variableInitialization3528 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_51_in_ifExpression3560 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_expression_in_ifExpression3564 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_ifExpression3566 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_expression_in_ifExpression3570 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_ifExpression3572 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_expression_in_ifExpression3576 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_ifExpression3578 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_literal3617 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_literal3631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_literal3644 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REAL_in_literal3659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal3673 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_literal3683 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_literal3687 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_literal3699 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_COLON_COLON_in_literal3701 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_literal3705 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionLiteral_in_literal3717 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyCollectionLiteral_in_literal3729 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedLiteral_in_literal3741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleLiteral_in_literal3753 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dateLiteral_in_literal3765 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionLiteral3803 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_LBRACE_in_collectionLiteral3832 = new BitSet(new long[]{0x04080001788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral3849 = new BitSet(new long[]{0x0000000100000400L});
    public static final BitSet FOLLOW_COMMA_in_collectionLiteral3862 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral3866 = new BitSet(new long[]{0x0000000100000400L});
    public static final BitSet FOLLOW_RBRACE_in_collectionLiteral3885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_collectionItem3914 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_DOTDOT_in_collectionItem3925 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_expression_in_collectionItem3929 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_emptyCollectionLiteral3958 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_emptyCollectionLiteral3960 = new BitSet(new long[]{0x0000000000000000L,0x0000000000103C00L});
    public static final BitSet FOLLOW_collectionType_in_emptyCollectionLiteral3964 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RPAREN_in_emptyCollectionLiteral3966 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_undefinedLiteral3996 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_undefinedLiteral3998 = new BitSet(new long[]{0x0000000000000020L,0x0000000000143C00L});
    public static final BitSet FOLLOW_type_in_undefinedLiteral4002 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RPAREN_in_undefinedLiteral4004 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_undefinedLiteral4018 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_undefinedLiteral4032 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_tupleLiteral4066 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_LBRACE_in_tupleLiteral4072 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral4080 = new BitSet(new long[]{0x0000000100000400L});
    public static final BitSet FOLLOW_COMMA_in_tupleLiteral4091 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral4095 = new BitSet(new long[]{0x0000000100000400L});
    public static final BitSet FOLLOW_RBRACE_in_tupleLiteral4106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tupleItem4137 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_COLON_in_tupleItem4176 = new BitSet(new long[]{0x0000000000000020L,0x0000000000143C00L});
    public static final BitSet FOLLOW_type_in_tupleItem4180 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_EQUAL_in_tupleItem4182 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_expression_in_tupleItem4186 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_tupleItem4208 = new BitSet(new long[]{0x04080000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_expression_in_tupleItem4218 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_dateLiteral4263 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_LBRACE_in_dateLiteral4265 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_STRING_in_dateLiteral4269 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_RBRACE_in_dateLiteral4271 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleType_in_type4321 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_type4333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleType_in_type4345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeOnly4377 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_typeOnly4379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_simpleType4407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionType4445 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_collectionType4472 = new BitSet(new long[]{0x0000000000000020L,0x0000000000143C00L});
    public static final BitSet FOLLOW_type_in_collectionType4476 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_RPAREN_in_collectionType4478 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_tupleType4512 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_tupleType4514 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType4523 = new BitSet(new long[]{0x0000000000000500L});
    public static final BitSet FOLLOW_COMMA_in_tupleType4534 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType4538 = new BitSet(new long[]{0x0000000000000500L});
    public static final BitSet FOLLOW_RPAREN_in_tupleType4550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tuplePart4582 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_tuplePart4584 = new BitSet(new long[]{0x0000000000000020L,0x0000000000143C00L});
    public static final BitSet FOLLOW_type_in_tuplePart4588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varAssignStat_in_synpred2_Soil238 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attAssignStat_in_synpred3_Soil252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lobjCreateStat_in_synpred4_Soil266 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objCreateStat_in_synpred5_Soil279 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_condExStat_in_synpred9_Soil340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_synpred11_Soil460 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_synpred11_Soil464 = new BitSet(new long[]{0x04081000788C00A0L,0x00000000000FFF79L});
    public static final BitSet FOLLOW_rValue_in_synpred11_Soil472 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lobjCreateStat_in_synpred13_Soil1163 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_synpred14_Soil4167 = new BitSet(new long[]{0x0000000000000020L,0x0000000000143C00L});
    public static final BitSet FOLLOW_type_in_synpred14_Soil4169 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_EQUAL_in_synpred14_Soil4171 = new BitSet(new long[]{0x0000000000000002L});

}