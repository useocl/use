// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 TestSuite.g 2010-09-07 17:10:52
 
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
 
package org.tzi.use.parser.testsuite;

import org.tzi.use.parser.base.BaseParser;
import org.tzi.use.parser.soil.*;
import org.tzi.use.parser.soil.ast.*;
import org.tzi.use.parser.ocl.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all") public class TestSuiteParser extends BaseParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "IDENT", "COLON_COLON", "COMMA", "STRING", "LPAREN", "RPAREN", "COLON", "EQUAL", "NOT_EQUAL", "LESS", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "PLUS", "MINUS", "STAR", "SLASH", "ARROW", "DOT", "AT", "BAR", "SEMI", "LBRACK", "RBRACK", "INT", "REAL", "HASH", "LBRACE", "RBRACE", "DOTDOT", "COLON_EQUAL", "NEWLINE", "WS", "SL_COMMENT", "ML_COMMENT", "SCRIPTBODY", "RANGE_OR_INT", "ESC", "HEX_DIGIT", "VOCAB", "'testsuite'", "'for'", "'model'", "'setup'", "'!'", "'end'", "'testcase'", "'beginVariation'", "'endVariation'", "'assert'", "'valid'", "'invalid'", "'invs'", "'inv'", "'pre'", "'post'", "'let'", "'in'", "'implies'", "'or'", "'xor'", "'and'", "'div'", "'not'", "'allInstances'", "'iterate'", "'oclAsType'", "'oclIsKindOf'", "'oclIsTypeOf'", "'if'", "'then'", "'else'", "'endif'", "'true'", "'false'", "'Set'", "'Sequence'", "'Bag'", "'OrderedSet'", "'oclEmpty'", "'oclUndefined'", "'Undefined'", "'null'", "'Tuple'", "'Date'", "'Collection'", "'new'", "'between'", "'destroy'", "'insert'", "'into'", "'delete'", "'from'", "'do'"
    };
    public static final int STAR=19;
    public static final int EOF=-1;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__91=91;
    public static final int RPAREN=9;
    public static final int T__92=92;
    public static final int GREATER=14;
    public static final int T__90=90;
    public static final int NOT_EQUAL=12;
    public static final int LESS=13;
    public static final int T__97=97;
    public static final int T__96=96;
    public static final int T__95=95;
    public static final int RBRACK=27;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int RBRACE=32;
    public static final int T__83=83;
    public static final int INT=28;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int REAL=29;
    public static final int T__71=71;
    public static final int WS=36;
    public static final int T__72=72;
    public static final int T__70=70;
    public static final int SL_COMMENT=37;
    public static final int LESS_EQUAL=15;
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
    public static final int LBRACK=26;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int ESC=41;
    public static final int LBRACE=31;
    public static final int DOTDOT=33;
    public static final int T__61=61;
    public static final int T__60=60;
    public static final int LPAREN=8;
    public static final int T__55=55;
    public static final int AT=23;
    public static final int ML_COMMENT=38;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int SLASH=20;
    public static final int COLON_EQUAL=34;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int COMMA=6;
    public static final int T__59=59;
    public static final int EQUAL=11;
    public static final int IDENT=4;
    public static final int PLUS=17;
    public static final int RANGE_OR_INT=40;
    public static final int DOT=22;
    public static final int SCRIPTBODY=39;
    public static final int T__50=50;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int HASH=30;
    public static final int HEX_DIGIT=42;
    public static final int COLON_COLON=5;
    public static final int MINUS=18;
    public static final int SEMI=25;
    public static final int COLON=10;
    public static final int NEWLINE=35;
    public static final int VOCAB=43;
    public static final int ARROW=21;
    public static final int GREATER_EQUAL=16;
    public static final int BAR=24;
    public static final int STRING=7;

    // delegates
    // delegators


        public TestSuiteParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public TestSuiteParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[86+1];
             
             
        }
        

    public String[] getTokenNames() { return TestSuiteParser.tokenNames; }
    public String getGrammarFileName() { return "TestSuite.g"; }



    // $ANTLR start "testSuite"
    // TestSuite.g:83:1: testSuite returns [ASTTestSuite suite] : 'testsuite' suiteName= IDENT 'for' 'model' modelFile= filename ( 'setup' ( '!' c= stat )* 'end' )? tests= testCases EOF ;
    public final ASTTestSuite testSuite() throws RecognitionException {
        ASTTestSuite suite = null;

        Token suiteName=null;
        String modelFile = null;

        TestSuiteParser.stat_return c = null;

        List tests = null;



          List setupStatements = new ArrayList();

        try {
            // TestSuite.g:87:1: ( 'testsuite' suiteName= IDENT 'for' 'model' modelFile= filename ( 'setup' ( '!' c= stat )* 'end' )? tests= testCases EOF )
            // TestSuite.g:88:3: 'testsuite' suiteName= IDENT 'for' 'model' modelFile= filename ( 'setup' ( '!' c= stat )* 'end' )? tests= testCases EOF
            {
            match(input,44,FOLLOW_44_in_testSuite61); if (state.failed) return suite;
            suiteName=(Token)match(input,IDENT,FOLLOW_IDENT_in_testSuite71); if (state.failed) return suite;
            if ( state.backtracking==0 ) {
               suite = new ASTTestSuite(suiteName); 
            }
            match(input,45,FOLLOW_45_in_testSuite82); if (state.failed) return suite;
            match(input,46,FOLLOW_46_in_testSuite84); if (state.failed) return suite;
            pushFollow(FOLLOW_filename_in_testSuite93);
            modelFile=filename();

            state._fsp--;
            if (state.failed) return suite;
            if ( state.backtracking==0 ) {
               suite.setModelFile(suiteName); 
            }
            // TestSuite.g:94:3: ( 'setup' ( '!' c= stat )* 'end' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==47) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // TestSuite.g:94:4: 'setup' ( '!' c= stat )* 'end'
                    {
                    match(input,47,FOLLOW_47_in_testSuite105); if (state.failed) return suite;
                    // TestSuite.g:95:4: ( '!' c= stat )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0==48) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // TestSuite.g:95:5: '!' c= stat
                    	    {
                    	    match(input,48,FOLLOW_48_in_testSuite112); if (state.failed) return suite;
                    	    pushFollow(FOLLOW_stat_in_testSuite118);
                    	    c=stat();

                    	    state._fsp--;
                    	    if (state.failed) return suite;
                    	    if ( state.backtracking==0 ) {
                    	       setupStatements.add((c!=null?c.n:null)); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop1;
                        }
                    } while (true);

                    match(input,49,FOLLOW_49_in_testSuite124); if (state.failed) return suite;
                    if ( state.backtracking==0 ) {
                       suite.setSetupStatements(setupStatements); 
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_testCases_in_testSuite145);
            tests=testCases();

            state._fsp--;
            if (state.failed) return suite;
            if ( state.backtracking==0 ) {
               suite.setTestCases(tests); 
            }
            match(input,EOF,FOLLOW_EOF_in_testSuite154); if (state.failed) return suite;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return suite;
    }
    // $ANTLR end "testSuite"


    // $ANTLR start "filename"
    // TestSuite.g:103:1: filename returns [String filename] : name= IDENT '.' suffix= IDENT ;
    public final String filename() throws RecognitionException {
        String filename = null;

        Token name=null;
        Token suffix=null;

        try {
            // TestSuite.g:104:1: (name= IDENT '.' suffix= IDENT )
            // TestSuite.g:105:4: name= IDENT '.' suffix= IDENT
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_filename172); if (state.failed) return filename;
            match(input,DOT,FOLLOW_DOT_in_filename174); if (state.failed) return filename;
            suffix=(Token)match(input,IDENT,FOLLOW_IDENT_in_filename178); if (state.failed) return filename;
            if ( state.backtracking==0 ) {
              filename = (name!=null?name.getText():null) + "." + (suffix!=null?suffix.getText():null);
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return filename;
    }
    // $ANTLR end "filename"


    // $ANTLR start "testCases"
    // TestSuite.g:108:1: testCases returns [List testCases] : (test= testCase )+ ;
    public final List testCases() throws RecognitionException {
        List testCases = null;

        ASTTestCase test = null;


         testCases = new ArrayList(); 
        try {
            // TestSuite.g:110:1: ( (test= testCase )+ )
            // TestSuite.g:111:3: (test= testCase )+
            {
            // TestSuite.g:111:3: (test= testCase )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==50) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // TestSuite.g:111:4: test= testCase
            	    {
            	    pushFollow(FOLLOW_testCase_in_testCases205);
            	    test=testCase();

            	    state._fsp--;
            	    if (state.failed) return testCases;
            	    if ( state.backtracking==0 ) {
            	       testCases.add(test); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
            	    if (state.backtracking>0) {state.failed=true; return testCases;}
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return testCases;
    }
    // $ANTLR end "testCases"


    // $ANTLR start "testCase"
    // TestSuite.g:114:1: testCase returns [ASTTestCase n] : 'testcase' name= IDENT ( '!' c= stat | a= assertStatement | b= 'beginVariation' | e= 'endVariation' )* 'end' ;
    public final ASTTestCase testCase() throws RecognitionException {
        ASTTestCase n = null;

        Token name=null;
        Token b=null;
        Token e=null;
        TestSuiteParser.stat_return c = null;

        ASTAssert a = null;


        try {
            // TestSuite.g:115:1: ( 'testcase' name= IDENT ( '!' c= stat | a= assertStatement | b= 'beginVariation' | e= 'endVariation' )* 'end' )
            // TestSuite.g:116:3: 'testcase' name= IDENT ( '!' c= stat | a= assertStatement | b= 'beginVariation' | e= 'endVariation' )* 'end'
            {
            match(input,50,FOLLOW_50_in_testCase224); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_testCase228); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTTestCase(name); 
            }
            // TestSuite.g:117:3: ( '!' c= stat | a= assertStatement | b= 'beginVariation' | e= 'endVariation' )*
            loop4:
            do {
                int alt4=5;
                switch ( input.LA(1) ) {
                case 48:
                    {
                    alt4=1;
                    }
                    break;
                case 53:
                    {
                    alt4=2;
                    }
                    break;
                case 51:
                    {
                    alt4=3;
                    }
                    break;
                case 52:
                    {
                    alt4=4;
                    }
                    break;

                }

                switch (alt4) {
            	case 1 :
            	    // TestSuite.g:118:7: '!' c= stat
            	    {
            	    match(input,48,FOLLOW_48_in_testCase242); if (state.failed) return n;
            	    pushFollow(FOLLOW_stat_in_testCase248);
            	    c=stat();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addStatement((c!=null?c.n:null)); 
            	    }

            	    }
            	    break;
            	case 2 :
            	    // TestSuite.g:120:7: a= assertStatement
            	    {
            	    pushFollow(FOLLOW_assertStatement_in_testCase267);
            	    a=assertStatement();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addStatement(a); 
            	    }

            	    }
            	    break;
            	case 3 :
            	    // TestSuite.g:122:7: b= 'beginVariation'
            	    {
            	    b=(Token)match(input,51,FOLLOW_51_in_testCase285); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addStatement(new ASTVariationStart(b)); 
            	    }

            	    }
            	    break;
            	case 4 :
            	    // TestSuite.g:124:7: e= 'endVariation'
            	    {
            	    e=(Token)match(input,52,FOLLOW_52_in_testCase303); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addStatement(new ASTVariationEnd(e)); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            match(input,49,FOLLOW_49_in_testCase315); if (state.failed) return n;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "testCase"


    // $ANTLR start "assertStatement"
    // TestSuite.g:129:1: assertStatement returns [ASTAssert n] : s= 'assert' ( 'valid' | 'invalid' ) (exp= expression | 'invs' | 'invs' classname= IDENT | 'inv' classname= IDENT COLON_COLON invname= IDENT | pre= assertionStatementPre[s=$s, valid=valid] | post= assertionStatementPost[s=$s, valid=valid] ) ( COMMA msg= STRING )? ;
    public final ASTAssert assertStatement() throws RecognitionException {
        ASTAssert n = null;

        Token s=null;
        Token classname=null;
        Token invname=null;
        Token msg=null;
        TestSuiteParser.expression_return exp = null;

        ASTAssertPre pre = null;

        ASTAssertPost post = null;


         boolean valid = true; 
        try {
            // TestSuite.g:131:1: (s= 'assert' ( 'valid' | 'invalid' ) (exp= expression | 'invs' | 'invs' classname= IDENT | 'inv' classname= IDENT COLON_COLON invname= IDENT | pre= assertionStatementPre[s=$s, valid=valid] | post= assertionStatementPost[s=$s, valid=valid] ) ( COMMA msg= STRING )? )
            // TestSuite.g:132:3: s= 'assert' ( 'valid' | 'invalid' ) (exp= expression | 'invs' | 'invs' classname= IDENT | 'inv' classname= IDENT COLON_COLON invname= IDENT | pre= assertionStatementPre[s=$s, valid=valid] | post= assertionStatementPost[s=$s, valid=valid] ) ( COMMA msg= STRING )?
            {
            s=(Token)match(input,53,FOLLOW_53_in_assertStatement336); if (state.failed) return n;
            // TestSuite.g:133:3: ( 'valid' | 'invalid' )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==54) ) {
                alt5=1;
            }
            else if ( (LA5_0==55) ) {
                alt5=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // TestSuite.g:133:4: 'valid'
                    {
                    match(input,54,FOLLOW_54_in_assertStatement341); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       valid = true; 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:133:32: 'invalid'
                    {
                    match(input,55,FOLLOW_55_in_assertStatement347); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      valid = false; 
                    }

                    }
                    break;

            }

            // TestSuite.g:134:3: (exp= expression | 'invs' | 'invs' classname= IDENT | 'inv' classname= IDENT COLON_COLON invname= IDENT | pre= assertionStatementPre[s=$s, valid=valid] | post= assertionStatementPost[s=$s, valid=valid] )
            int alt6=6;
            switch ( input.LA(1) ) {
            case IDENT:
            case STRING:
            case LPAREN:
            case PLUS:
            case MINUS:
            case AT:
            case INT:
            case REAL:
            case HASH:
            case 60:
            case 67:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
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
            case 88:
                {
                alt6=1;
                }
                break;
            case 56:
                {
                int LA6_2 = input.LA(2);

                if ( (LA6_2==IDENT) ) {
                    alt6=3;
                }
                else if ( (LA6_2==COMMA||(LA6_2>=48 && LA6_2<=49)||(LA6_2>=51 && LA6_2<=53)) ) {
                    alt6=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 2, input);

                    throw nvae;
                }
                }
                break;
            case 57:
                {
                alt6=4;
                }
                break;
            case 58:
                {
                alt6=5;
                }
                break;
            case 59:
                {
                alt6=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // TestSuite.g:135:7: exp= expression
                    {
                    pushFollow(FOLLOW_expression_in_assertStatement366);
                    exp=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTAssertOclExpression((exp!=null?exp.n:null).getStartToken(), input.LT(-1), valid, (exp!=null?exp.n:null)); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:137:7: 'invs'
                    {
                    match(input,56,FOLLOW_56_in_assertStatement382); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTAssertGlobalInvariants(s, input.LT(-1), valid); 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:139:7: 'invs' classname= IDENT
                    {
                    match(input,56,FOLLOW_56_in_assertStatement398); if (state.failed) return n;
                    classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_assertStatement402); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTAssertClassInvariants(s, input.LT(-1), valid, classname); 
                    }

                    }
                    break;
                case 4 :
                    // TestSuite.g:141:7: 'inv' classname= IDENT COLON_COLON invname= IDENT
                    {
                    match(input,57,FOLLOW_57_in_assertStatement418); if (state.failed) return n;
                    classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_assertStatement422); if (state.failed) return n;
                    match(input,COLON_COLON,FOLLOW_COLON_COLON_in_assertStatement424); if (state.failed) return n;
                    invname=(Token)match(input,IDENT,FOLLOW_IDENT_in_assertStatement428); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTAssertSingleInvariant(s, input.LT(-1), valid, classname, invname); 
                    }

                    }
                    break;
                case 5 :
                    // TestSuite.g:143:7: pre= assertionStatementPre[s=$s, valid=valid]
                    {
                    pushFollow(FOLLOW_assertionStatementPre_in_assertStatement448);
                    pre=assertionStatementPre(s=s, valid=valid);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = pre; 
                    }

                    }
                    break;
                case 6 :
                    // TestSuite.g:145:7: post= assertionStatementPost[s=$s, valid=valid]
                    {
                    pushFollow(FOLLOW_assertionStatementPost_in_assertStatement469);
                    post=assertionStatementPost(s=s, valid=valid);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = post; 
                    }

                    }
                    break;

            }

            // TestSuite.g:147:3: ( COMMA msg= STRING )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==COMMA) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // TestSuite.g:148:5: COMMA msg= STRING
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_assertStatement486); if (state.failed) return n;
                    msg=(Token)match(input,STRING,FOLLOW_STRING_in_assertStatement490); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setMessage(msg); 
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
    // $ANTLR end "assertStatement"


    // $ANTLR start "assertionStatementPre"
    // TestSuite.g:152:1: assertionStatementPre[Token s, boolean valid] returns [ASTAssertPre n] : 'pre' objExp= expression opName= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN ( COLON_COLON name= IDENT )? ;
    public final ASTAssertPre assertionStatementPre(Token s, boolean valid) throws RecognitionException {
        ASTAssertPre n = null;

        Token opName=null;
        Token name=null;
        TestSuiteParser.expression_return objExp = null;

        TestSuiteParser.expression_return e = null;


        try {
            // TestSuite.g:153:1: ( 'pre' objExp= expression opName= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN ( COLON_COLON name= IDENT )? )
            // TestSuite.g:154:3: 'pre' objExp= expression opName= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN ( COLON_COLON name= IDENT )?
            {
            match(input,58,FOLLOW_58_in_assertionStatementPre513); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_assertionStatementPre517);
            objExp=expression();

            state._fsp--;
            if (state.failed) return n;
            opName=(Token)match(input,IDENT,FOLLOW_IDENT_in_assertionStatementPre521); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssertPre(s, null, valid, (objExp!=null?objExp.n:null), opName); 
            }
            match(input,LPAREN,FOLLOW_LPAREN_in_assertionStatementPre527); if (state.failed) return n;
            // TestSuite.g:156:5: (e= expression ( COMMA e= expression )* )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==IDENT||(LA9_0>=STRING && LA9_0<=LPAREN)||(LA9_0>=PLUS && LA9_0<=MINUS)||LA9_0==AT||(LA9_0>=INT && LA9_0<=HASH)||LA9_0==60||LA9_0==67||(LA9_0>=69 && LA9_0<=73)||(LA9_0>=77 && LA9_0<=88)) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // TestSuite.g:156:7: e= expression ( COMMA e= expression )*
                    {
                    pushFollow(FOLLOW_expression_in_assertionStatementPre538);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addArg((e!=null?e.n:null)); 
                    }
                    // TestSuite.g:156:41: ( COMMA e= expression )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0==COMMA) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // TestSuite.g:156:43: COMMA e= expression
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_assertionStatementPre544); if (state.failed) return n;
                    	    pushFollow(FOLLOW_expression_in_assertionStatementPre548);
                    	    e=expression();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addArg((e!=null?e.n:null)); 
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

            match(input,RPAREN,FOLLOW_RPAREN_in_assertionStatementPre561); if (state.failed) return n;
            // TestSuite.g:157:10: ( COLON_COLON name= IDENT )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==COLON_COLON) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // TestSuite.g:157:11: COLON_COLON name= IDENT
                    {
                    match(input,COLON_COLON,FOLLOW_COLON_COLON_in_assertionStatementPre564); if (state.failed) return n;
                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_assertionStatementPre568); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setConditionName(name); 
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n.setEnd(input.LT(-1)); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "assertionStatementPre"


    // $ANTLR start "assertionStatementPost"
    // TestSuite.g:161:1: assertionStatementPost[Token s, boolean valid] returns [ASTAssertPost n] : 'post' (name= IDENT )? ;
    public final ASTAssertPost assertionStatementPost(Token s, boolean valid) throws RecognitionException {
        ASTAssertPost n = null;

        Token name=null;

        try {
            // TestSuite.g:162:1: ( 'post' (name= IDENT )? )
            // TestSuite.g:163:3: 'post' (name= IDENT )?
            {
            match(input,59,FOLLOW_59_in_assertionStatementPost593); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssertPost(s, null, valid); 
            }
            // TestSuite.g:164:3: (name= IDENT )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==IDENT) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // TestSuite.g:164:4: name= IDENT
                    {
                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_assertionStatementPost602); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setConditionName(name); 
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n.setEnd(input.LT(-1)); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end "assertionStatementPost"


    // $ANTLR start "expressionOnly"
    // TestSuite.g:196:1: expressionOnly returns [ASTExpression n] : nExp= expression EOF ;
    public final ASTExpression expressionOnly() throws RecognitionException {
        ASTExpression n = null;

        TestSuiteParser.expression_return nExp = null;


        try {
            // TestSuite.g:197:1: (nExp= expression EOF )
            // TestSuite.g:198:5: nExp= expression EOF
            {
            pushFollow(FOLLOW_expression_in_expressionOnly641);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_expressionOnly643); if (state.failed) return n;
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
    // TestSuite.g:205:1: expression returns [ASTExpression n] : ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression ;
    public final TestSuiteParser.expression_return expression() throws RecognitionException {
        TestSuiteParser.expression_return retval = new TestSuiteParser.expression_return();
        retval.start = input.LT(1);

        Token name=null;
        ASTType t = null;

        TestSuiteParser.expression_return e1 = null;

        ASTExpression nCndImplies = null;


         
          ASTLetExpression prevLet = null, firstLet = null;
          ASTExpression e2;
          Token tok = null;

        try {
            // TestSuite.g:211:1: ( ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression )
            // TestSuite.g:212:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of expression */ 
            }
            // TestSuite.g:213:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==60) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // TestSuite.g:214:7: 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in'
            	    {
            	    match(input,60,FOLLOW_60_in_expression691); if (state.failed) return retval;
            	    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_expression695); if (state.failed) return retval;
            	    // TestSuite.g:214:24: ( COLON t= type )?
            	    int alt12=2;
            	    int LA12_0 = input.LA(1);

            	    if ( (LA12_0==COLON) ) {
            	        alt12=1;
            	    }
            	    switch (alt12) {
            	        case 1 :
            	            // TestSuite.g:214:26: COLON t= type
            	            {
            	            match(input,COLON,FOLLOW_COLON_in_expression699); if (state.failed) return retval;
            	            pushFollow(FOLLOW_type_in_expression703);
            	            t=type();

            	            state._fsp--;
            	            if (state.failed) return retval;

            	            }
            	            break;

            	    }

            	    match(input,EQUAL,FOLLOW_EQUAL_in_expression708); if (state.failed) return retval;
            	    pushFollow(FOLLOW_expression_in_expression712);
            	    e1=expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    match(input,61,FOLLOW_61_in_expression714); if (state.failed) return retval;
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
            	    break loop13;
                }
            } while (true);

            pushFollow(FOLLOW_conditionalImpliesExpression_in_expression739);
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
    // TestSuite.g:242:1: paramList returns [List paramList] : LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN ;
    public final List paramList() throws RecognitionException {
        List paramList = null;

        ASTVariableDeclaration v = null;


         paramList = new ArrayList(); 
        try {
            // TestSuite.g:244:1: ( LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN )
            // TestSuite.g:245:5: LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN
            {
            match(input,LPAREN,FOLLOW_LPAREN_in_paramList772); if (state.failed) return paramList;
            // TestSuite.g:246:5: (v= variableDeclaration ( COMMA v= variableDeclaration )* )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==IDENT) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // TestSuite.g:247:7: v= variableDeclaration ( COMMA v= variableDeclaration )*
                    {
                    pushFollow(FOLLOW_variableDeclaration_in_paramList789);
                    v=variableDeclaration();

                    state._fsp--;
                    if (state.failed) return paramList;
                    if ( state.backtracking==0 ) {
                       paramList.add(v); 
                    }
                    // TestSuite.g:248:7: ( COMMA v= variableDeclaration )*
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( (LA14_0==COMMA) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // TestSuite.g:248:9: COMMA v= variableDeclaration
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_paramList801); if (state.failed) return paramList;
                    	    pushFollow(FOLLOW_variableDeclaration_in_paramList805);
                    	    v=variableDeclaration();

                    	    state._fsp--;
                    	    if (state.failed) return paramList;
                    	    if ( state.backtracking==0 ) {
                    	       paramList.add(v); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop14;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_paramList825); if (state.failed) return paramList;

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
    // TestSuite.g:256:1: idList returns [List idList] : id0= IDENT ( COMMA idn= IDENT )* ;
    public final List idList() throws RecognitionException {
        List idList = null;

        Token id0=null;
        Token idn=null;

         idList = new ArrayList(); 
        try {
            // TestSuite.g:258:1: (id0= IDENT ( COMMA idn= IDENT )* )
            // TestSuite.g:259:5: id0= IDENT ( COMMA idn= IDENT )*
            {
            id0=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList854); if (state.failed) return idList;
            if ( state.backtracking==0 ) {
               idList.add(id0); 
            }
            // TestSuite.g:260:5: ( COMMA idn= IDENT )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==COMMA) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // TestSuite.g:260:7: COMMA idn= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_idList864); if (state.failed) return idList;
            	    idn=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList868); if (state.failed) return idList;
            	    if ( state.backtracking==0 ) {
            	       idList.add(idn); 
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
        return idList;
    }
    // $ANTLR end "idList"


    // $ANTLR start "variableDeclaration"
    // TestSuite.g:268:1: variableDeclaration returns [ASTVariableDeclaration n] : name= IDENT COLON t= type ;
    public final ASTVariableDeclaration variableDeclaration() throws RecognitionException {
        ASTVariableDeclaration n = null;

        Token name=null;
        ASTType t = null;


        try {
            // TestSuite.g:269:1: (name= IDENT COLON t= type )
            // TestSuite.g:270:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableDeclaration899); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableDeclaration901); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableDeclaration905);
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
    // TestSuite.g:278:1: conditionalImpliesExpression returns [ASTExpression n] : nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* ;
    public final ASTExpression conditionalImpliesExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndOrExp = null;

        ASTExpression n1 = null;


        try {
            // TestSuite.g:279:1: (nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* )
            // TestSuite.g:280:5: nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )*
            {
            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression941);
            nCndOrExp=conditionalOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndOrExp;
            }
            // TestSuite.g:281:5: (op= 'implies' n1= conditionalOrExpression )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==62) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // TestSuite.g:281:7: op= 'implies' n1= conditionalOrExpression
            	    {
            	    op=(Token)match(input,62,FOLLOW_62_in_conditionalImpliesExpression954); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression958);
            	    n1=conditionalOrExpression();

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
    // $ANTLR end "conditionalImpliesExpression"


    // $ANTLR start "conditionalOrExpression"
    // TestSuite.g:290:1: conditionalOrExpression returns [ASTExpression n] : nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* ;
    public final ASTExpression conditionalOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndXorExp = null;

        ASTExpression n1 = null;


        try {
            // TestSuite.g:291:1: (nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* )
            // TestSuite.g:292:5: nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )*
            {
            pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression1003);
            nCndXorExp=conditionalXOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndXorExp;
            }
            // TestSuite.g:293:5: (op= 'or' n1= conditionalXOrExpression )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==63) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // TestSuite.g:293:7: op= 'or' n1= conditionalXOrExpression
            	    {
            	    op=(Token)match(input,63,FOLLOW_63_in_conditionalOrExpression1016); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression1020);
            	    n1=conditionalXOrExpression();

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
    // $ANTLR end "conditionalOrExpression"


    // $ANTLR start "conditionalXOrExpression"
    // TestSuite.g:302:1: conditionalXOrExpression returns [ASTExpression n] : nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* ;
    public final ASTExpression conditionalXOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndAndExp = null;

        ASTExpression n1 = null;


        try {
            // TestSuite.g:303:1: (nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* )
            // TestSuite.g:304:5: nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )*
            {
            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression1064);
            nCndAndExp=conditionalAndExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndAndExp;
            }
            // TestSuite.g:305:5: (op= 'xor' n1= conditionalAndExpression )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==64) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // TestSuite.g:305:7: op= 'xor' n1= conditionalAndExpression
            	    {
            	    op=(Token)match(input,64,FOLLOW_64_in_conditionalXOrExpression1077); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression1081);
            	    n1=conditionalAndExpression();

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
    // $ANTLR end "conditionalXOrExpression"


    // $ANTLR start "conditionalAndExpression"
    // TestSuite.g:314:1: conditionalAndExpression returns [ASTExpression n] : nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* ;
    public final ASTExpression conditionalAndExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nEqExp = null;

        ASTExpression n1 = null;


        try {
            // TestSuite.g:315:1: (nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* )
            // TestSuite.g:316:5: nEqExp= equalityExpression (op= 'and' n1= equalityExpression )*
            {
            pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression1125);
            nEqExp=equalityExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nEqExp;
            }
            // TestSuite.g:317:5: (op= 'and' n1= equalityExpression )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==65) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // TestSuite.g:317:7: op= 'and' n1= equalityExpression
            	    {
            	    op=(Token)match(input,65,FOLLOW_65_in_conditionalAndExpression1138); if (state.failed) return n;
            	    pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression1142);
            	    n1=equalityExpression();

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
    // $ANTLR end "conditionalAndExpression"


    // $ANTLR start "equalityExpression"
    // TestSuite.g:326:1: equalityExpression returns [ASTExpression n] : nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* ;
    public final ASTExpression equalityExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nRelExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // TestSuite.g:328:1: (nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* )
            // TestSuite.g:329:5: nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            {
            pushFollow(FOLLOW_relationalExpression_in_equalityExpression1190);
            nRelExp=relationalExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nRelExp;
            }
            // TestSuite.g:330:5: ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( ((LA21_0>=EQUAL && LA21_0<=NOT_EQUAL)) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // TestSuite.g:330:7: ( EQUAL | NOT_EQUAL ) n1= relationalExpression
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

            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression1219);
            	    n1=relationalExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop21;
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
    // TestSuite.g:340:1: relationalExpression returns [ASTExpression n] : nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* ;
    public final ASTExpression relationalExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nAddiExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // TestSuite.g:342:1: (nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* )
            // TestSuite.g:343:5: nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            {
            pushFollow(FOLLOW_additiveExpression_in_relationalExpression1268);
            nAddiExp=additiveExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nAddiExp;
            }
            // TestSuite.g:344:5: ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( ((LA22_0>=LESS && LA22_0<=GREATER_EQUAL)) ) {
                    alt22=1;
                }


                switch (alt22) {
            	case 1 :
            	    // TestSuite.g:344:7: ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression
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

            	    pushFollow(FOLLOW_additiveExpression_in_relationalExpression1304);
            	    n1=additiveExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
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
        return n;
    }
    // $ANTLR end "relationalExpression"


    // $ANTLR start "additiveExpression"
    // TestSuite.g:354:1: additiveExpression returns [ASTExpression n] : nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* ;
    public final ASTExpression additiveExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nMulExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // TestSuite.g:356:1: (nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* )
            // TestSuite.g:357:5: nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            {
            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1354);
            nMulExp=multiplicativeExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nMulExp;
            }
            // TestSuite.g:358:5: ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( ((LA23_0>=PLUS && LA23_0<=MINUS)) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // TestSuite.g:358:7: ( PLUS | MINUS ) n1= multiplicativeExpression
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

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1382);
            	    n1=multiplicativeExpression();

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
    // $ANTLR end "additiveExpression"


    // $ANTLR start "multiplicativeExpression"
    // TestSuite.g:369:1: multiplicativeExpression returns [ASTExpression n] : nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* ;
    public final ASTExpression multiplicativeExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // TestSuite.g:371:1: (nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* )
            // TestSuite.g:372:5: nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            {
            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1432);
            nUnExp=unaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nUnExp;
            }
            // TestSuite.g:373:5: ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( ((LA24_0>=STAR && LA24_0<=SLASH)||LA24_0==66) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // TestSuite.g:373:7: ( STAR | SLASH | 'div' ) n1= unaryExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       op = input.LT(1); 
            	    }
            	    if ( (input.LA(1)>=STAR && input.LA(1)<=SLASH)||input.LA(1)==66 ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1464);
            	    n1=unaryExpression();

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
    // $ANTLR end "multiplicativeExpression"


    // $ANTLR start "unaryExpression"
    // TestSuite.g:385:1: unaryExpression returns [ASTExpression n] : ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression );
    public final ASTExpression unaryExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression nPosExp = null;


         Token op = null; 
        try {
            // TestSuite.g:387:1: ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression )
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( ((LA25_0>=PLUS && LA25_0<=MINUS)||LA25_0==67) ) {
                alt25=1;
            }
            else if ( (LA25_0==IDENT||(LA25_0>=STRING && LA25_0<=LPAREN)||LA25_0==AT||(LA25_0>=INT && LA25_0<=HASH)||(LA25_0>=69 && LA25_0<=73)||(LA25_0>=77 && LA25_0<=88)) ) {
                alt25=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }
            switch (alt25) {
                case 1 :
                    // TestSuite.g:388:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    {
                    // TestSuite.g:388:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    // TestSuite.g:388:9: ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression
                    {
                    if ( state.backtracking==0 ) {
                       op = input.LT(1); 
                    }
                    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS)||input.LA(1)==67 ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression1550);
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
                    // TestSuite.g:392:7: nPosExp= postfixExpression
                    {
                    pushFollow(FOLLOW_postfixExpression_in_unaryExpression1570);
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
    // TestSuite.g:400:1: postfixExpression returns [ASTExpression n] : nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* ;
    public final ASTExpression postfixExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nPrimExp = null;

        ASTExpression nPc = null;


         boolean arrow = false; 
        try {
            // TestSuite.g:402:1: (nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* )
            // TestSuite.g:403:5: nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            {
            pushFollow(FOLLOW_primaryExpression_in_postfixExpression1603);
            nPrimExp=primaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nPrimExp; 
            }
            // TestSuite.g:404:5: ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==DOT) ) {
                    int LA27_2 = input.LA(2);

                    if ( (LA27_2==IDENT) ) {
                        int LA27_4 = input.LA(3);

                        if ( (LA27_4==EOF||LA27_4==IDENT||LA27_4==COMMA||(LA27_4>=LPAREN && LA27_4<=RPAREN)||(LA27_4>=EQUAL && LA27_4<=LBRACK)||(LA27_4>=RBRACE && LA27_4<=DOTDOT)||(LA27_4>=48 && LA27_4<=49)||(LA27_4>=51 && LA27_4<=53)||(LA27_4>=61 && LA27_4<=66)||(LA27_4>=74 && LA27_4<=76)||LA27_4==97) ) {
                            alt27=1;
                        }


                    }
                    else if ( ((LA27_2>=69 && LA27_2<=72)) ) {
                        alt27=1;
                    }


                }
                else if ( (LA27_0==ARROW) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // TestSuite.g:405:6: ( ARROW | DOT ) nPc= propertyCall[$n, arrow]
            	    {
            	    // TestSuite.g:405:6: ( ARROW | DOT )
            	    int alt26=2;
            	    int LA26_0 = input.LA(1);

            	    if ( (LA26_0==ARROW) ) {
            	        alt26=1;
            	    }
            	    else if ( (LA26_0==DOT) ) {
            	        alt26=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 26, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt26) {
            	        case 1 :
            	            // TestSuite.g:405:8: ARROW
            	            {
            	            match(input,ARROW,FOLLOW_ARROW_in_postfixExpression1621); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = true; 
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // TestSuite.g:405:34: DOT
            	            {
            	            match(input,DOT,FOLLOW_DOT_in_postfixExpression1627); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = false; 
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_propertyCall_in_postfixExpression1638);
            	    nPc=propertyCall(n, arrow);

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = nPc; 
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
    // $ANTLR end "postfixExpression"


    // $ANTLR start "primaryExpression"
    // TestSuite.g:421:1: primaryExpression returns [ASTExpression n] : (nLit= literal | nOr= objectReference | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? );
    public final ASTExpression primaryExpression() throws RecognitionException {
        ASTExpression n = null;

        Token id1=null;
        ASTExpression nLit = null;

        ASTExpression nOr = null;

        ASTExpression nPc = null;

        TestSuiteParser.expression_return nExp = null;

        ASTExpression nIfExp = null;


        try {
            // TestSuite.g:422:1: (nLit= literal | nOr= objectReference | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? )
            int alt30=6;
            switch ( input.LA(1) ) {
            case STRING:
            case INT:
            case REAL:
            case HASH:
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
            case 88:
                {
                alt30=1;
                }
                break;
            case IDENT:
                {
                switch ( input.LA(2) ) {
                case COLON_COLON:
                    {
                    alt30=1;
                    }
                    break;
                case EOF:
                case IDENT:
                case COMMA:
                case LPAREN:
                case RPAREN:
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
                case SEMI:
                case LBRACK:
                case RBRACE:
                case DOTDOT:
                case 48:
                case 49:
                case 51:
                case 52:
                case 53:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 74:
                case 75:
                case 76:
                case 97:
                    {
                    alt30=3;
                    }
                    break;
                case DOT:
                    {
                    int LA30_7 = input.LA(3);

                    if ( (LA30_7==68) ) {
                        alt30=6;
                    }
                    else if ( (LA30_7==IDENT||(LA30_7>=69 && LA30_7<=72)) ) {
                        alt30=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 30, 7, input);

                        throw nvae;
                    }
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 30, 2, input);

                    throw nvae;
                }

                }
                break;
            case AT:
                {
                alt30=2;
                }
                break;
            case 69:
            case 70:
            case 71:
            case 72:
                {
                alt30=3;
                }
                break;
            case LPAREN:
                {
                alt30=4;
                }
                break;
            case 73:
                {
                alt30=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }

            switch (alt30) {
                case 1 :
                    // TestSuite.g:423:7: nLit= literal
                    {
                    pushFollow(FOLLOW_literal_in_primaryExpression1678);
                    nLit=literal();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nLit; 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:424:7: nOr= objectReference
                    {
                    pushFollow(FOLLOW_objectReference_in_primaryExpression1692);
                    nOr=objectReference();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nOr; 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:425:7: nPc= propertyCall[null, false]
                    {
                    pushFollow(FOLLOW_propertyCall_in_primaryExpression1704);
                    nPc=propertyCall(null, false);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nPc; 
                    }

                    }
                    break;
                case 4 :
                    // TestSuite.g:426:7: LPAREN nExp= expression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression1715); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_primaryExpression1719);
                    nExp=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression1721); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (nExp!=null?nExp.n:null); 
                    }

                    }
                    break;
                case 5 :
                    // TestSuite.g:427:7: nIfExp= ifExpression
                    {
                    pushFollow(FOLLOW_ifExpression_in_primaryExpression1733);
                    nIfExp=ifExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nIfExp; 
                    }

                    }
                    break;
                case 6 :
                    // TestSuite.g:429:7: id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )?
                    {
                    id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_primaryExpression1750); if (state.failed) return n;
                    match(input,DOT,FOLLOW_DOT_in_primaryExpression1752); if (state.failed) return n;
                    match(input,68,FOLLOW_68_in_primaryExpression1754); if (state.failed) return n;
                    // TestSuite.g:429:36: ( LPAREN RPAREN )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==LPAREN) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // TestSuite.g:429:38: LPAREN RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression1758); if (state.failed) return n;
                            match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression1760); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTAllInstancesExpression(id1); 
                    }
                    // TestSuite.g:431:7: ( AT 'pre' )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==AT) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // TestSuite.g:431:9: AT 'pre'
                            {
                            match(input,AT,FOLLOW_AT_in_primaryExpression1781); if (state.failed) return n;
                            match(input,58,FOLLOW_58_in_primaryExpression1783); if (state.failed) return n;
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
    // TestSuite.g:435:1: objectReference returns [ASTExpression n] : AT objectName= IDENT ;
    public final ASTExpression objectReference() throws RecognitionException {
        ASTExpression n = null;

        Token objectName=null;

        try {
            // TestSuite.g:436:1: ( AT objectName= IDENT )
            // TestSuite.g:437:3: AT objectName= IDENT
            {
            match(input,AT,FOLLOW_AT_in_objectReference1810); if (state.failed) return n;
            objectName=(Token)match(input,IDENT,FOLLOW_IDENT_in_objectReference1818); if (state.failed) return n;
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
    // TestSuite.g:452:1: propertyCall[ASTExpression source, boolean followsArrow] returns [ASTExpression n] : ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] );
    public final ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExpQuery = null;

        ASTExpression nExpIterate = null;

        TestSuiteParser.operationExpression_return nExpOperation = null;

        ASTTypeArgExpression nExpType = null;


        try {
            // TestSuite.g:453:1: ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] )
            int alt31=4;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA31_1 = input.LA(2);

                if ( ((( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )&&( input.LA(2) == LPAREN ))) ) {
                    alt31=1;
                }
                else if ( (true) ) {
                    alt31=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 31, 1, input);

                    throw nvae;
                }
                }
                break;
            case 69:
                {
                alt31=2;
                }
                break;
            case 70:
            case 71:
            case 72:
                {
                alt31=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;
            }

            switch (alt31) {
                case 1 :
                    // TestSuite.g:457:7: {...}?{...}?nExpQuery= queryExpression[source]
                    {
                    if ( !(( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) ");
                    }
                    if ( !(( input.LA(2) == LPAREN )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " input.LA(2) == LPAREN ");
                    }
                    pushFollow(FOLLOW_queryExpression_in_propertyCall1886);
                    nExpQuery=queryExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpQuery; 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:460:7: nExpIterate= iterateExpression[source]
                    {
                    pushFollow(FOLLOW_iterateExpression_in_propertyCall1899);
                    nExpIterate=iterateExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpIterate; 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:461:7: nExpOperation= operationExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_operationExpression_in_propertyCall1912);
                    nExpOperation=operationExpression(source, followsArrow);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (nExpOperation!=null?nExpOperation.n:null); 
                    }

                    }
                    break;
                case 4 :
                    // TestSuite.g:462:7: nExpType= typeExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_typeExpression_in_propertyCall1925);
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
    // TestSuite.g:471:1: queryExpression[ASTExpression range] returns [ASTExpression n] : op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN ;
    public final ASTExpression queryExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTElemVarsDeclaration decls = null;

        TestSuiteParser.expression_return nExp = null;


        ASTElemVarsDeclaration decl = new ASTElemVarsDeclaration(); 
        try {
            // TestSuite.g:472:69: (op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN )
            // TestSuite.g:473:5: op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN
            {
            op=(Token)match(input,IDENT,FOLLOW_IDENT_in_queryExpression1960); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_queryExpression1967); if (state.failed) return n;
            // TestSuite.g:475:5: (decls= elemVarsDeclaration BAR )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==IDENT) ) {
                int LA32_1 = input.LA(2);

                if ( (LA32_1==COMMA||LA32_1==COLON||LA32_1==BAR) ) {
                    alt32=1;
                }
            }
            switch (alt32) {
                case 1 :
                    // TestSuite.g:475:7: decls= elemVarsDeclaration BAR
                    {
                    pushFollow(FOLLOW_elemVarsDeclaration_in_queryExpression1978);
                    decls=elemVarsDeclaration();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      decl = decls;
                    }
                    match(input,BAR,FOLLOW_BAR_in_queryExpression1982); if (state.failed) return n;

                    }
                    break;

            }

            pushFollow(FOLLOW_expression_in_queryExpression1993);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_queryExpression1999); if (state.failed) return n;
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
    // TestSuite.g:489:1: iterateExpression[ASTExpression range] returns [ASTExpression n] : i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN ;
    public final ASTExpression iterateExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTElemVarsDeclaration decls = null;

        ASTVariableInitialization init = null;

        TestSuiteParser.expression_return nExp = null;


        try {
            // TestSuite.g:489:65: (i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN )
            // TestSuite.g:490:5: i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN
            {
            i=(Token)match(input,69,FOLLOW_69_in_iterateExpression2031); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_iterateExpression2037); if (state.failed) return n;
            pushFollow(FOLLOW_elemVarsDeclaration_in_iterateExpression2045);
            decls=elemVarsDeclaration();

            state._fsp--;
            if (state.failed) return n;
            match(input,SEMI,FOLLOW_SEMI_in_iterateExpression2047); if (state.failed) return n;
            pushFollow(FOLLOW_variableInitialization_in_iterateExpression2055);
            init=variableInitialization();

            state._fsp--;
            if (state.failed) return n;
            match(input,BAR,FOLLOW_BAR_in_iterateExpression2057); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_iterateExpression2065);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_iterateExpression2071); if (state.failed) return n;
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
    // TestSuite.g:511:1: operationExpression[ASTExpression source, boolean followsArrow] returns [ASTOperationExpression n] : name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? ;
    public final TestSuiteParser.operationExpression_return operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        TestSuiteParser.operationExpression_return retval = new TestSuiteParser.operationExpression_return();
        retval.start = input.LT(1);

        Token name=null;
        Token rolename=null;
        TestSuiteParser.expression_return e = null;


        try {
            // TestSuite.g:513:1: (name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? )
            // TestSuite.g:514:5: name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression2115); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
               retval.n = new ASTOperationExpression(name, source, followsArrow); 
            }
            // TestSuite.g:517:5: ( LBRACK rolename= IDENT RBRACK )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==LBRACK) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // TestSuite.g:517:7: LBRACK rolename= IDENT RBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_operationExpression2131); if (state.failed) return retval;
                    rolename=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression2135); if (state.failed) return retval;
                    match(input,RBRACK,FOLLOW_RBRACK_in_operationExpression2137); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n.setExplicitRolename(rolename); 
                    }

                    }
                    break;

            }

            // TestSuite.g:519:5: ( AT 'pre' )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==AT) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // TestSuite.g:519:7: AT 'pre'
                    {
                    match(input,AT,FOLLOW_AT_in_operationExpression2150); if (state.failed) return retval;
                    match(input,58,FOLLOW_58_in_operationExpression2152); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n.setIsPre(); 
                    }

                    }
                    break;

            }

            // TestSuite.g:520:5: ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==LPAREN) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // TestSuite.g:521:7: LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_operationExpression2173); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n.hasParentheses(); 
                    }
                    // TestSuite.g:522:7: (e= expression ( COMMA e= expression )* )?
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==IDENT||(LA36_0>=STRING && LA36_0<=LPAREN)||(LA36_0>=PLUS && LA36_0<=MINUS)||LA36_0==AT||(LA36_0>=INT && LA36_0<=HASH)||LA36_0==60||LA36_0==67||(LA36_0>=69 && LA36_0<=73)||(LA36_0>=77 && LA36_0<=88)) ) {
                        alt36=1;
                    }
                    switch (alt36) {
                        case 1 :
                            // TestSuite.g:523:7: e= expression ( COMMA e= expression )*
                            {
                            pushFollow(FOLLOW_expression_in_operationExpression2194);
                            e=expression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                               retval.n.addArg((e!=null?e.n:null)); 
                            }
                            // TestSuite.g:524:7: ( COMMA e= expression )*
                            loop35:
                            do {
                                int alt35=2;
                                int LA35_0 = input.LA(1);

                                if ( (LA35_0==COMMA) ) {
                                    alt35=1;
                                }


                                switch (alt35) {
                            	case 1 :
                            	    // TestSuite.g:524:9: COMMA e= expression
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_operationExpression2206); if (state.failed) return retval;
                            	    pushFollow(FOLLOW_expression_in_operationExpression2210);
                            	    e=expression();

                            	    state._fsp--;
                            	    if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) {
                            	       retval.n.addArg((e!=null?e.n:null)); 
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

                    match(input,RPAREN,FOLLOW_RPAREN_in_operationExpression2230); if (state.failed) return retval;

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
    // TestSuite.g:537:1: typeExpression[ASTExpression source, boolean followsArrow] returns [ASTTypeArgExpression n] : ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN ;
    public final ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTTypeArgExpression n = null;

        ASTType t = null;


         Token opToken = null; 
        try {
            // TestSuite.g:540:1: ( ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN )
            // TestSuite.g:541:2: ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN
            {
            if ( state.backtracking==0 ) {
               opToken = input.LT(1); 
            }
            if ( (input.LA(1)>=70 && input.LA(1)<=72) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_typeExpression2295); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_typeExpression2299);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_typeExpression2301); if (state.failed) return n;
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
    // TestSuite.g:552:1: elemVarsDeclaration returns [ASTElemVarsDeclaration n] : idListRes= idList ( COLON t= type )? ;
    public final ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException {
        ASTElemVarsDeclaration n = null;

        List idListRes = null;

        ASTType t = null;


         List idList; 
        try {
            // TestSuite.g:554:1: (idListRes= idList ( COLON t= type )? )
            // TestSuite.g:555:5: idListRes= idList ( COLON t= type )?
            {
            pushFollow(FOLLOW_idList_in_elemVarsDeclaration2340);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            // TestSuite.g:556:5: ( COLON t= type )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==COLON) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // TestSuite.g:556:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_elemVarsDeclaration2348); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_elemVarsDeclaration2352);
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
    // TestSuite.g:565:1: variableInitialization returns [ASTVariableInitialization n] : name= IDENT COLON t= type EQUAL e= expression ;
    public final ASTVariableInitialization variableInitialization() throws RecognitionException {
        ASTVariableInitialization n = null;

        Token name=null;
        ASTType t = null;

        TestSuiteParser.expression_return e = null;


        try {
            // TestSuite.g:566:1: (name= IDENT COLON t= type EQUAL e= expression )
            // TestSuite.g:567:5: name= IDENT COLON t= type EQUAL e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableInitialization2387); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableInitialization2389); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableInitialization2393);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EQUAL,FOLLOW_EQUAL_in_variableInitialization2395); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_variableInitialization2399);
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
    // TestSuite.g:576:1: ifExpression returns [ASTExpression n] : i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' ;
    public final ASTExpression ifExpression() throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        TestSuiteParser.expression_return cond = null;

        TestSuiteParser.expression_return t = null;

        TestSuiteParser.expression_return e = null;


        try {
            // TestSuite.g:577:1: (i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' )
            // TestSuite.g:578:5: i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif'
            {
            i=(Token)match(input,73,FOLLOW_73_in_ifExpression2431); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression2435);
            cond=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,74,FOLLOW_74_in_ifExpression2437); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression2441);
            t=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,75,FOLLOW_75_in_ifExpression2443); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression2447);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,76,FOLLOW_76_in_ifExpression2449); if (state.failed) return n;
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
    // TestSuite.g:598:1: literal returns [ASTExpression n] : (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral );
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
            // TestSuite.g:599:1: (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral )
            int alt39=12;
            switch ( input.LA(1) ) {
            case 77:
                {
                alt39=1;
                }
                break;
            case 78:
                {
                alt39=2;
                }
                break;
            case INT:
                {
                alt39=3;
                }
                break;
            case REAL:
                {
                alt39=4;
                }
                break;
            case STRING:
                {
                alt39=5;
                }
                break;
            case HASH:
                {
                alt39=6;
                }
                break;
            case IDENT:
                {
                alt39=7;
                }
                break;
            case 79:
            case 80:
            case 81:
            case 82:
                {
                alt39=8;
                }
                break;
            case 83:
                {
                alt39=9;
                }
                break;
            case 84:
            case 85:
            case 86:
                {
                alt39=10;
                }
                break;
            case 87:
                {
                alt39=11;
                }
                break;
            case 88:
                {
                alt39=12;
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
                    // TestSuite.g:600:7: t= 'true'
                    {
                    t=(Token)match(input,77,FOLLOW_77_in_literal2488); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(true); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:601:7: f= 'false'
                    {
                    f=(Token)match(input,78,FOLLOW_78_in_literal2502); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(false); 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:602:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_literal2515); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTIntegerLiteral(i); 
                    }

                    }
                    break;
                case 4 :
                    // TestSuite.g:603:7: r= REAL
                    {
                    r=(Token)match(input,REAL,FOLLOW_REAL_in_literal2530); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTRealLiteral(r); 
                    }

                    }
                    break;
                case 5 :
                    // TestSuite.g:604:7: s= STRING
                    {
                    s=(Token)match(input,STRING,FOLLOW_STRING_in_literal2544); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTStringLiteral(s); 
                    }

                    }
                    break;
                case 6 :
                    // TestSuite.g:605:7: HASH enumLit= IDENT
                    {
                    match(input,HASH,FOLLOW_HASH_in_literal2554); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal2558); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumLit);
                    }

                    }
                    break;
                case 7 :
                    // TestSuite.g:606:7: enumName= IDENT '::' enumLit= IDENT
                    {
                    enumName=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal2570); if (state.failed) return n;
                    match(input,COLON_COLON,FOLLOW_COLON_COLON_in_literal2572); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal2576); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumName, enumLit); 
                    }

                    }
                    break;
                case 8 :
                    // TestSuite.g:607:7: nColIt= collectionLiteral
                    {
                    pushFollow(FOLLOW_collectionLiteral_in_literal2588);
                    nColIt=collectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nColIt; 
                    }

                    }
                    break;
                case 9 :
                    // TestSuite.g:608:7: nEColIt= emptyCollectionLiteral
                    {
                    pushFollow(FOLLOW_emptyCollectionLiteral_in_literal2600);
                    nEColIt=emptyCollectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nEColIt; 
                    }

                    }
                    break;
                case 10 :
                    // TestSuite.g:609:7: nUndLit= undefinedLiteral
                    {
                    pushFollow(FOLLOW_undefinedLiteral_in_literal2612);
                    nUndLit=undefinedLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nUndLit; 
                    }

                    }
                    break;
                case 11 :
                    // TestSuite.g:610:7: nTupleLit= tupleLiteral
                    {
                    pushFollow(FOLLOW_tupleLiteral_in_literal2624);
                    nTupleLit=tupleLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nTupleLit; 
                    }

                    }
                    break;
                case 12 :
                    // TestSuite.g:611:7: nDateLit= dateLiteral
                    {
                    pushFollow(FOLLOW_dateLiteral_in_literal2636);
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
    // TestSuite.g:619:1: collectionLiteral returns [ASTCollectionLiteral n] : ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE ;
    public final ASTCollectionLiteral collectionLiteral() throws RecognitionException {
        ASTCollectionLiteral n = null;

        ASTCollectionItem ci = null;


         Token op = null; 
        try {
            // TestSuite.g:621:1: ( ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE )
            // TestSuite.g:622:5: ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE
            {
            if ( state.backtracking==0 ) {
               op = input.LT(1); 
            }
            if ( (input.LA(1)>=79 && input.LA(1)<=82) ) {
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
            match(input,LBRACE,FOLLOW_LBRACE_in_collectionLiteral2703); if (state.failed) return n;
            // TestSuite.g:626:5: (ci= collectionItem ( COMMA ci= collectionItem )* )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==IDENT||(LA41_0>=STRING && LA41_0<=LPAREN)||(LA41_0>=PLUS && LA41_0<=MINUS)||LA41_0==AT||(LA41_0>=INT && LA41_0<=HASH)||LA41_0==60||LA41_0==67||(LA41_0>=69 && LA41_0<=73)||(LA41_0>=77 && LA41_0<=88)) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // TestSuite.g:627:7: ci= collectionItem ( COMMA ci= collectionItem )*
                    {
                    pushFollow(FOLLOW_collectionItem_in_collectionLiteral2720);
                    ci=collectionItem();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addItem(ci); 
                    }
                    // TestSuite.g:628:7: ( COMMA ci= collectionItem )*
                    loop40:
                    do {
                        int alt40=2;
                        int LA40_0 = input.LA(1);

                        if ( (LA40_0==COMMA) ) {
                            alt40=1;
                        }


                        switch (alt40) {
                    	case 1 :
                    	    // TestSuite.g:628:9: COMMA ci= collectionItem
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_collectionLiteral2733); if (state.failed) return n;
                    	    pushFollow(FOLLOW_collectionItem_in_collectionLiteral2737);
                    	    ci=collectionItem();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addItem(ci); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop40;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RBRACE,FOLLOW_RBRACE_in_collectionLiteral2756); if (state.failed) return n;

            }

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
    // TestSuite.g:637:1: collectionItem returns [ASTCollectionItem n] : e= expression ( DOTDOT e= expression )? ;
    public final ASTCollectionItem collectionItem() throws RecognitionException {
        ASTCollectionItem n = null;

        TestSuiteParser.expression_return e = null;


         n = new ASTCollectionItem(); 
        try {
            // TestSuite.g:639:1: (e= expression ( DOTDOT e= expression )? )
            // TestSuite.g:640:5: e= expression ( DOTDOT e= expression )?
            {
            pushFollow(FOLLOW_expression_in_collectionItem2785);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setFirst((e!=null?e.n:null)); 
            }
            // TestSuite.g:641:5: ( DOTDOT e= expression )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==DOTDOT) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // TestSuite.g:641:7: DOTDOT e= expression
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_collectionItem2796); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_collectionItem2800);
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
    // TestSuite.g:651:1: emptyCollectionLiteral returns [ASTEmptyCollectionLiteral n] : 'oclEmpty' LPAREN t= collectionType RPAREN ;
    public final ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException {
        ASTEmptyCollectionLiteral n = null;

        ASTCollectionType t = null;


        try {
            // TestSuite.g:652:1: ( 'oclEmpty' LPAREN t= collectionType RPAREN )
            // TestSuite.g:653:5: 'oclEmpty' LPAREN t= collectionType RPAREN
            {
            match(input,83,FOLLOW_83_in_emptyCollectionLiteral2829); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_emptyCollectionLiteral2831); if (state.failed) return n;
            pushFollow(FOLLOW_collectionType_in_emptyCollectionLiteral2835);
            t=collectionType();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_emptyCollectionLiteral2837); if (state.failed) return n;
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
    // TestSuite.g:664:1: undefinedLiteral returns [ASTUndefinedLiteral n] : ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' );
    public final ASTUndefinedLiteral undefinedLiteral() throws RecognitionException {
        ASTUndefinedLiteral n = null;

        ASTType t = null;


        try {
            // TestSuite.g:665:1: ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' )
            int alt43=3;
            switch ( input.LA(1) ) {
            case 84:
                {
                alt43=1;
                }
                break;
            case 85:
                {
                alt43=2;
                }
                break;
            case 86:
                {
                alt43=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 43, 0, input);

                throw nvae;
            }

            switch (alt43) {
                case 1 :
                    // TestSuite.g:666:5: 'oclUndefined' LPAREN t= type RPAREN
                    {
                    match(input,84,FOLLOW_84_in_undefinedLiteral2867); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_undefinedLiteral2869); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_undefinedLiteral2873);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_undefinedLiteral2875); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(t); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:669:5: 'Undefined'
                    {
                    match(input,85,FOLLOW_85_in_undefinedLiteral2889); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(); 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:672:5: 'null'
                    {
                    match(input,86,FOLLOW_86_in_undefinedLiteral2903); if (state.failed) return n;
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
    // TestSuite.g:681:1: tupleLiteral returns [ASTTupleLiteral n] : 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE ;
    public final ASTTupleLiteral tupleLiteral() throws RecognitionException {
        ASTTupleLiteral n = null;

        ASTTupleItem ti = null;


         List tiList = new ArrayList(); 
        try {
            // TestSuite.g:683:1: ( 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE )
            // TestSuite.g:684:5: 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE
            {
            match(input,87,FOLLOW_87_in_tupleLiteral2937); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_tupleLiteral2943); if (state.failed) return n;
            pushFollow(FOLLOW_tupleItem_in_tupleLiteral2951);
            ti=tupleItem();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tiList.add(ti); 
            }
            // TestSuite.g:687:5: ( COMMA ti= tupleItem )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==COMMA) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // TestSuite.g:687:7: COMMA ti= tupleItem
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleLiteral2962); if (state.failed) return n;
            	    pushFollow(FOLLOW_tupleItem_in_tupleLiteral2966);
            	    ti=tupleItem();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tiList.add(ti); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop44;
                }
            } while (true);

            match(input,RBRACE,FOLLOW_RBRACE_in_tupleLiteral2977); if (state.failed) return n;
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
    // TestSuite.g:695:1: tupleItem returns [ASTTupleItem n] : name= IDENT ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) ;
    public final ASTTupleItem tupleItem() throws RecognitionException {
        ASTTupleItem n = null;

        Token name=null;
        ASTType t = null;

        TestSuiteParser.expression_return e = null;


        try {
            // TestSuite.g:696:1: (name= IDENT ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) )
            // TestSuite.g:697:5: name= IDENT ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tupleItem3008); if (state.failed) return n;
            // TestSuite.g:698:5: ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==COLON) ) {
                int LA45_1 = input.LA(2);

                if ( (synpred1_TestSuite()) ) {
                    alt45=1;
                }
                else if ( (true) ) {
                    alt45=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 45, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA45_0==EQUAL) ) {
                alt45=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;
            }
            switch (alt45) {
                case 1 :
                    // TestSuite.g:701:7: ( COLON type EQUAL )=> COLON t= type EQUAL e= expression
                    {
                    match(input,COLON,FOLLOW_COLON_in_tupleItem3047); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_tupleItem3051);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,EQUAL,FOLLOW_EQUAL_in_tupleItem3053); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_tupleItem3057);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTTupleItem(name, t, (e!=null?e.n:null)); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:704:7: ( COLON | EQUAL ) e= expression
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

                    pushFollow(FOLLOW_expression_in_tupleItem3089);
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
    // TestSuite.g:713:1: dateLiteral returns [ASTDateLiteral n] : 'Date' LBRACE v= STRING RBRACE ;
    public final ASTDateLiteral dateLiteral() throws RecognitionException {
        ASTDateLiteral n = null;

        Token v=null;

        try {
            // TestSuite.g:714:1: ( 'Date' LBRACE v= STRING RBRACE )
            // TestSuite.g:715:5: 'Date' LBRACE v= STRING RBRACE
            {
            match(input,88,FOLLOW_88_in_dateLiteral3134); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_dateLiteral3136); if (state.failed) return n;
            v=(Token)match(input,STRING,FOLLOW_STRING_in_dateLiteral3140); if (state.failed) return n;
            match(input,RBRACE,FOLLOW_RBRACE_in_dateLiteral3142); if (state.failed) return n;
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
    // TestSuite.g:725:1: type returns [ASTType n] : (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) ;
    public final ASTType type() throws RecognitionException {
        ASTType n = null;

        ASTSimpleType nTSimple = null;

        ASTCollectionType nTCollection = null;

        ASTTupleType nTTuple = null;


         Token tok = null; 
        try {
            // TestSuite.g:727:1: ( (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) )
            // TestSuite.g:728:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of type */ 
            }
            // TestSuite.g:729:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            int alt46=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt46=1;
                }
                break;
            case 79:
            case 80:
            case 81:
            case 82:
            case 89:
                {
                alt46=2;
                }
                break;
            case 87:
                {
                alt46=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 46, 0, input);

                throw nvae;
            }

            switch (alt46) {
                case 1 :
                    // TestSuite.g:730:7: nTSimple= simpleType
                    {
                    pushFollow(FOLLOW_simpleType_in_type3192);
                    nTSimple=simpleType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTSimple; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:731:7: nTCollection= collectionType
                    {
                    pushFollow(FOLLOW_collectionType_in_type3204);
                    nTCollection=collectionType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTCollection; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:732:7: nTTuple= tupleType
                    {
                    pushFollow(FOLLOW_tupleType_in_type3216);
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
    // TestSuite.g:737:1: typeOnly returns [ASTType n] : nT= type EOF ;
    public final ASTType typeOnly() throws RecognitionException {
        ASTType n = null;

        ASTType nT = null;


        try {
            // TestSuite.g:738:1: (nT= type EOF )
            // TestSuite.g:739:5: nT= type EOF
            {
            pushFollow(FOLLOW_type_in_typeOnly3248);
            nT=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_typeOnly3250); if (state.failed) return n;
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
    // TestSuite.g:749:1: simpleType returns [ASTSimpleType n] : name= IDENT ;
    public final ASTSimpleType simpleType() throws RecognitionException {
        ASTSimpleType n = null;

        Token name=null;

        try {
            // TestSuite.g:750:1: (name= IDENT )
            // TestSuite.g:751:5: name= IDENT
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_simpleType3278); if (state.failed) return n;
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
    // TestSuite.g:759:1: collectionType returns [ASTCollectionType n] : ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN ;
    public final ASTCollectionType collectionType() throws RecognitionException {
        ASTCollectionType n = null;

        ASTType elemType = null;


         Token op = null; 
        try {
            // TestSuite.g:761:1: ( ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN )
            // TestSuite.g:762:5: ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN
            {
            if ( state.backtracking==0 ) {
               op = input.LT(1); 
            }
            if ( (input.LA(1)>=79 && input.LA(1)<=82)||input.LA(1)==89 ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_collectionType3343); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_collectionType3347);
            elemType=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_collectionType3349); if (state.failed) return n;
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
    // TestSuite.g:772:1: tupleType returns [ASTTupleType n] : 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN ;
    public final ASTTupleType tupleType() throws RecognitionException {
        ASTTupleType n = null;

        ASTTuplePart tp = null;


         List tpList = new ArrayList(); 
        try {
            // TestSuite.g:774:1: ( 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN )
            // TestSuite.g:775:5: 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN
            {
            match(input,87,FOLLOW_87_in_tupleType3383); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_tupleType3385); if (state.failed) return n;
            pushFollow(FOLLOW_tuplePart_in_tupleType3394);
            tp=tuplePart();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tpList.add(tp); 
            }
            // TestSuite.g:777:5: ( COMMA tp= tuplePart )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==COMMA) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // TestSuite.g:777:7: COMMA tp= tuplePart
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleType3405); if (state.failed) return n;
            	    pushFollow(FOLLOW_tuplePart_in_tupleType3409);
            	    tp=tuplePart();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tpList.add(tp); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop47;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_tupleType3421); if (state.failed) return n;
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
    // TestSuite.g:786:1: tuplePart returns [ASTTuplePart n] : name= IDENT COLON t= type ;
    public final ASTTuplePart tuplePart() throws RecognitionException {
        ASTTuplePart n = null;

        Token name=null;
        ASTType t = null;


        try {
            // TestSuite.g:787:1: (name= IDENT COLON t= type )
            // TestSuite.g:788:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tuplePart3453); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_tuplePart3455); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_tuplePart3459);
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
    // TestSuite.g:827:1: statOnly returns [ASTStatement n] : s= stat EOF ;
    public final ASTStatement statOnly() throws RecognitionException {
        ASTStatement n = null;

        TestSuiteParser.stat_return s = null;


        try {
            // TestSuite.g:828:1: (s= stat EOF )
            // TestSuite.g:829:3: s= stat EOF
            {
            pushFollow(FOLLOW_stat_in_statOnly3508);
            s=stat();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_statOnly3512); if (state.failed) return n;
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
    // TestSuite.g:839:1: stat returns [ASTStatement n] : nextStat[seq] ( SEMI nextStat[seq] )* ;
    public final TestSuiteParser.stat_return stat() throws RecognitionException {
        TestSuiteParser.stat_return retval = new TestSuiteParser.stat_return();
        retval.start = input.LT(1);


          ASTSequenceStatement seq = new ASTSequenceStatement();

        try {
            // TestSuite.g:843:1: ( nextStat[seq] ( SEMI nextStat[seq] )* )
            // TestSuite.g:844:3: nextStat[seq] ( SEMI nextStat[seq] )*
            {
            pushFollow(FOLLOW_nextStat_in_stat3543);
            nextStat(seq);

            state._fsp--;
            if (state.failed) return retval;
            // TestSuite.g:845:3: ( SEMI nextStat[seq] )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==SEMI) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // TestSuite.g:846:5: SEMI nextStat[seq]
            	    {
            	    match(input,SEMI,FOLLOW_SEMI_in_stat3554); if (state.failed) return retval;
            	    pushFollow(FOLLOW_nextStat_in_stat3560);
            	    nextStat(seq);

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop48;
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
    // TestSuite.g:863:1: nextStat[ASTSequenceStatement seq] : s= singleStat ;
    public final TestSuiteParser.nextStat_return nextStat(ASTSequenceStatement seq) throws RecognitionException {
        TestSuiteParser.nextStat_return retval = new TestSuiteParser.nextStat_return();
        retval.start = input.LT(1);

        ASTStatement s = null;


        try {
            // TestSuite.g:864:1: (s= singleStat )
            // TestSuite.g:865:3: s= singleStat
            {
            pushFollow(FOLLOW_singleStat_in_nextStat3594);
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
    // TestSuite.g:877:1: singleStat returns [ASTStatement n] options {backtrack=true; memoize=true; } : (emp= emptyStat | vas= varAssignStat | aas= attAssignStat | lcs= lobjCreateStat | ocs= objCreateStat | ods= objDestroyStat | lis= lnkInsStat | lds= lnkDelStat | ces= condExStat | its= iterStat | ops= callStat );
    public final ASTStatement singleStat() throws RecognitionException {
        ASTStatement n = null;
        int singleStat_StartIndex = input.index();
        ASTEmptyStatement emp = null;

        TestSuiteParser.varAssignStat_return vas = null;

        ASTAttributeAssignmentStatement aas = null;

        ASTNewLinkObjectStatement lcs = null;

        TestSuiteParser.objCreateStat_return ocs = null;

        TestSuiteParser.objDestroyStat_return ods = null;

        ASTLinkInsertionStatement lis = null;

        ASTLinkDeletionStatement lds = null;

        ASTConditionalExecutionStatement ces = null;

        ASTIterationStatement its = null;

        ASTOperationCallStatement ops = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 50) ) { return n; }
            // TestSuite.g:882:1: (emp= emptyStat | vas= varAssignStat | aas= attAssignStat | lcs= lobjCreateStat | ocs= objCreateStat | ods= objDestroyStat | lis= lnkInsStat | lds= lnkDelStat | ces= condExStat | its= iterStat | ops= callStat )
            int alt49=11;
            alt49 = dfa49.predict(input);
            switch (alt49) {
                case 1 :
                    // TestSuite.g:883:5: emp= emptyStat
                    {
                    pushFollow(FOLLOW_emptyStat_in_singleStat3649);
                    emp=emptyStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = emp; 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:885:5: vas= varAssignStat
                    {
                    pushFollow(FOLLOW_varAssignStat_in_singleStat3670);
                    vas=varAssignStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (vas!=null?vas.n:null); 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:886:5: aas= attAssignStat
                    {
                    pushFollow(FOLLOW_attAssignStat_in_singleStat3684);
                    aas=attAssignStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = aas; 
                    }

                    }
                    break;
                case 4 :
                    // TestSuite.g:887:5: lcs= lobjCreateStat
                    {
                    pushFollow(FOLLOW_lobjCreateStat_in_singleStat3698);
                    lcs=lobjCreateStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = lcs; 
                    }

                    }
                    break;
                case 5 :
                    // TestSuite.g:888:5: ocs= objCreateStat
                    {
                    pushFollow(FOLLOW_objCreateStat_in_singleStat3711);
                    ocs=objCreateStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (ocs!=null?ocs.n:null); 
                    }

                    }
                    break;
                case 6 :
                    // TestSuite.g:889:5: ods= objDestroyStat
                    {
                    pushFollow(FOLLOW_objDestroyStat_in_singleStat3725);
                    ods=objDestroyStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (ods!=null?ods.n:null); 
                    }

                    }
                    break;
                case 7 :
                    // TestSuite.g:890:5: lis= lnkInsStat
                    {
                    pushFollow(FOLLOW_lnkInsStat_in_singleStat3738);
                    lis=lnkInsStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = lis; 
                    }

                    }
                    break;
                case 8 :
                    // TestSuite.g:891:5: lds= lnkDelStat
                    {
                    pushFollow(FOLLOW_lnkDelStat_in_singleStat3755);
                    lds=lnkDelStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = lds; 
                    }

                    }
                    break;
                case 9 :
                    // TestSuite.g:892:5: ces= condExStat
                    {
                    pushFollow(FOLLOW_condExStat_in_singleStat3772);
                    ces=condExStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = ces; 
                    }

                    }
                    break;
                case 10 :
                    // TestSuite.g:893:5: its= iterStat
                    {
                    pushFollow(FOLLOW_iterStat_in_singleStat3789);
                    its=iterStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = its; 
                    }

                    }
                    break;
                case 11 :
                    // TestSuite.g:894:5: ops= callStat
                    {
                    pushFollow(FOLLOW_callStat_in_singleStat3808);
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
            if ( state.backtracking>0 ) { memoize(input, 50, singleStat_StartIndex); }
        }
        return n;
    }
    // $ANTLR end "singleStat"


    // $ANTLR start "emptyStat"
    // TestSuite.g:902:1: emptyStat returns [ASTEmptyStatement n] : nothing ;
    public final ASTEmptyStatement emptyStat() throws RecognitionException {
        ASTEmptyStatement n = null;

        try {
            // TestSuite.g:903:1: ( nothing )
            // TestSuite.g:904:3: nothing
            {
            pushFollow(FOLLOW_nothing_in_emptyStat3839);
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
    // TestSuite.g:913:1: varAssignStat returns [ASTStatement n] options {backtrack=true; memoize=true; } : (varName= IDENT COLON_EQUAL rVal= rValue | vNames= identListMin1 COLON_EQUAL 'new' oType= simpleType ( LPAREN oNames= exprList RPAREN )? );
    public final TestSuiteParser.varAssignStat_return varAssignStat() throws RecognitionException {
        TestSuiteParser.varAssignStat_return retval = new TestSuiteParser.varAssignStat_return();
        retval.start = input.LT(1);
        int varAssignStat_StartIndex = input.index();
        Token varName=null;
        TestSuiteParser.rValue_return rVal = null;

        List<String> vNames = null;

        ASTSimpleType oType = null;

        List<ASTExpression> oNames = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 52) ) { return retval; }
            // TestSuite.g:918:1: (varName= IDENT COLON_EQUAL rVal= rValue | vNames= identListMin1 COLON_EQUAL 'new' oType= simpleType ( LPAREN oNames= exprList RPAREN )? )
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==IDENT) ) {
                int LA51_1 = input.LA(2);

                if ( (LA51_1==COLON_EQUAL) ) {
                    int LA51_2 = input.LA(3);

                    if ( (LA51_2==90) ) {
                        int LA51_4 = input.LA(4);

                        if ( (LA51_4==IDENT) ) {
                            int LA51_6 = input.LA(5);

                            if ( (synpred12_TestSuite()) ) {
                                alt51=1;
                            }
                            else if ( (true) ) {
                                alt51=2;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 51, 6, input);

                                throw nvae;
                            }
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 51, 4, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA51_2==IDENT||(LA51_2>=STRING && LA51_2<=LPAREN)||(LA51_2>=PLUS && LA51_2<=MINUS)||LA51_2==AT||(LA51_2>=INT && LA51_2<=HASH)||LA51_2==60||LA51_2==67||(LA51_2>=69 && LA51_2<=73)||(LA51_2>=77 && LA51_2<=88)) ) {
                        alt51=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 51, 2, input);

                        throw nvae;
                    }
                }
                else if ( (LA51_1==COMMA) ) {
                    alt51=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 51, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 51, 0, input);

                throw nvae;
            }
            switch (alt51) {
                case 1 :
                    // TestSuite.g:919:3: varName= IDENT COLON_EQUAL rVal= rValue
                    {
                    varName=(Token)match(input,IDENT,FOLLOW_IDENT_in_varAssignStat3892); if (state.failed) return retval;
                    match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_varAssignStat3896); if (state.failed) return retval;
                    pushFollow(FOLLOW_rValue_in_varAssignStat3904);
                    rVal=rValue();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n = new ASTVariableAssignmentStatement((varName!=null?varName.getText():null), (rVal!=null?rVal.n:null)); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:925:3: vNames= identListMin1 COLON_EQUAL 'new' oType= simpleType ( LPAREN oNames= exprList RPAREN )?
                    {
                    pushFollow(FOLLOW_identListMin1_in_varAssignStat3923);
                    vNames=identListMin1();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_varAssignStat3927); if (state.failed) return retval;
                    match(input,90,FOLLOW_90_in_varAssignStat3931); if (state.failed) return retval;
                    pushFollow(FOLLOW_simpleType_in_varAssignStat3939);
                    oType=simpleType();

                    state._fsp--;
                    if (state.failed) return retval;
                    // TestSuite.g:929:3: ( LPAREN oNames= exprList RPAREN )?
                    int alt50=2;
                    int LA50_0 = input.LA(1);

                    if ( (LA50_0==LPAREN) ) {
                        alt50=1;
                    }
                    switch (alt50) {
                        case 1 :
                            // TestSuite.g:930:5: LPAREN oNames= exprList RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_varAssignStat3949); if (state.failed) return retval;
                            pushFollow(FOLLOW_exprList_in_varAssignStat3961);
                            oNames=exprList();

                            state._fsp--;
                            if (state.failed) return retval;
                            match(input,RPAREN,FOLLOW_RPAREN_in_varAssignStat3967); if (state.failed) return retval;

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
            if ( state.backtracking>0 ) { memoize(input, 52, varAssignStat_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "varAssignStat"


    // $ANTLR start "attAssignStat"
    // TestSuite.g:988:1: attAssignStat returns [ASTAttributeAssignmentStatement n] : obj= inSoilExpression DOT attName= IDENT COLON_EQUAL r= rValue ;
    public final ASTAttributeAssignmentStatement attAssignStat() throws RecognitionException {
        ASTAttributeAssignmentStatement n = null;

        Token attName=null;
        ASTExpression obj = null;

        TestSuiteParser.rValue_return r = null;


        try {
            // TestSuite.g:989:1: (obj= inSoilExpression DOT attName= IDENT COLON_EQUAL r= rValue )
            // TestSuite.g:990:3: obj= inSoilExpression DOT attName= IDENT COLON_EQUAL r= rValue
            {
            pushFollow(FOLLOW_inSoilExpression_in_attAssignStat4002);
            obj=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            match(input,DOT,FOLLOW_DOT_in_attAssignStat4007); if (state.failed) return n;
            attName=(Token)match(input,IDENT,FOLLOW_IDENT_in_attAssignStat4016); if (state.failed) return n;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_attAssignStat4020); if (state.failed) return n;
            pushFollow(FOLLOW_rValue_in_attAssignStat4028);
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
    // TestSuite.g:1003:1: objCreateStat returns [ASTStatement n] : 'new' objType= simpleType ( LPAREN objNames= exprListMin1 RPAREN )? ;
    public final TestSuiteParser.objCreateStat_return objCreateStat() throws RecognitionException {
        TestSuiteParser.objCreateStat_return retval = new TestSuiteParser.objCreateStat_return();
        retval.start = input.LT(1);

        ASTSimpleType objType = null;

        List<ASTExpression> objNames = null;


        try {
            // TestSuite.g:1004:1: ( 'new' objType= simpleType ( LPAREN objNames= exprListMin1 RPAREN )? )
            // TestSuite.g:1005:3: 'new' objType= simpleType ( LPAREN objNames= exprListMin1 RPAREN )?
            {
            match(input,90,FOLLOW_90_in_objCreateStat4054); if (state.failed) return retval;
            pushFollow(FOLLOW_simpleType_in_objCreateStat4062);
            objType=simpleType();

            state._fsp--;
            if (state.failed) return retval;
            // TestSuite.g:1007:3: ( LPAREN objNames= exprListMin1 RPAREN )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==LPAREN) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // TestSuite.g:1008:5: LPAREN objNames= exprListMin1 RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_objCreateStat4072); if (state.failed) return retval;
                    pushFollow(FOLLOW_exprListMin1_in_objCreateStat4084);
                    objNames=exprListMin1();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,RPAREN,FOLLOW_RPAREN_in_objCreateStat4090); if (state.failed) return retval;

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
    // TestSuite.g:1035:1: lobjCreateStat returns [ASTNewLinkObjectStatement n] : 'new' assoc= IDENT ( LPAREN name= inSoilExpression RPAREN )? 'between' LPAREN p= rValListMin2 RPAREN ;
    public final ASTNewLinkObjectStatement lobjCreateStat() throws RecognitionException {
        ASTNewLinkObjectStatement n = null;

        Token assoc=null;
        ASTExpression name = null;

        List<ASTRValue> p = null;


        try {
            // TestSuite.g:1036:1: ( 'new' assoc= IDENT ( LPAREN name= inSoilExpression RPAREN )? 'between' LPAREN p= rValListMin2 RPAREN )
            // TestSuite.g:1037:3: 'new' assoc= IDENT ( LPAREN name= inSoilExpression RPAREN )? 'between' LPAREN p= rValListMin2 RPAREN
            {
            match(input,90,FOLLOW_90_in_lobjCreateStat4121); if (state.failed) return n;
            assoc=(Token)match(input,IDENT,FOLLOW_IDENT_in_lobjCreateStat4129); if (state.failed) return n;
            // TestSuite.g:1039:3: ( LPAREN name= inSoilExpression RPAREN )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==LPAREN) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // TestSuite.g:1040:5: LPAREN name= inSoilExpression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_lobjCreateStat4139); if (state.failed) return n;
                    pushFollow(FOLLOW_inSoilExpression_in_lobjCreateStat4151);
                    name=inSoilExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_lobjCreateStat4157); if (state.failed) return n;

                    }
                    break;

            }

            match(input,91,FOLLOW_91_in_lobjCreateStat4166); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_lobjCreateStat4170); if (state.failed) return n;
            pushFollow(FOLLOW_rValListMin2_in_lobjCreateStat4180);
            p=rValListMin2();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_lobjCreateStat4184); if (state.failed) return n;
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
    // TestSuite.g:1060:1: objDestroyStat returns [ASTStatement n] : 'destroy' el= exprListMin1 ;
    public final TestSuiteParser.objDestroyStat_return objDestroyStat() throws RecognitionException {
        TestSuiteParser.objDestroyStat_return retval = new TestSuiteParser.objDestroyStat_return();
        retval.start = input.LT(1);

        List<ASTExpression> el = null;


        try {
            // TestSuite.g:1061:1: ( 'destroy' el= exprListMin1 )
            // TestSuite.g:1062:3: 'destroy' el= exprListMin1
            {
            match(input,92,FOLLOW_92_in_objDestroyStat4210); if (state.failed) return retval;
            pushFollow(FOLLOW_exprListMin1_in_objDestroyStat4218);
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
    // TestSuite.g:1083:1: lnkInsStat returns [ASTLinkInsertionStatement n] : 'insert' LPAREN p= rValListMin2 RPAREN 'into' ass= IDENT ;
    public final ASTLinkInsertionStatement lnkInsStat() throws RecognitionException {
        ASTLinkInsertionStatement n = null;

        Token ass=null;
        List<ASTRValue> p = null;


        try {
            // TestSuite.g:1084:1: ( 'insert' LPAREN p= rValListMin2 RPAREN 'into' ass= IDENT )
            // TestSuite.g:1085:3: 'insert' LPAREN p= rValListMin2 RPAREN 'into' ass= IDENT
            {
            match(input,93,FOLLOW_93_in_lnkInsStat4244); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_lnkInsStat4248); if (state.failed) return n;
            pushFollow(FOLLOW_rValListMin2_in_lnkInsStat4258);
            p=rValListMin2();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_lnkInsStat4262); if (state.failed) return n;
            match(input,94,FOLLOW_94_in_lnkInsStat4266); if (state.failed) return n;
            ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_lnkInsStat4274); if (state.failed) return n;
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
    // TestSuite.g:1099:1: lnkDelStat returns [ASTLinkDeletionStatement n] : 'delete' LPAREN p= rValListMin2 RPAREN 'from' ass= IDENT ;
    public final ASTLinkDeletionStatement lnkDelStat() throws RecognitionException {
        ASTLinkDeletionStatement n = null;

        Token ass=null;
        List<ASTRValue> p = null;


        try {
            // TestSuite.g:1100:1: ( 'delete' LPAREN p= rValListMin2 RPAREN 'from' ass= IDENT )
            // TestSuite.g:1101:3: 'delete' LPAREN p= rValListMin2 RPAREN 'from' ass= IDENT
            {
            match(input,95,FOLLOW_95_in_lnkDelStat4298); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_lnkDelStat4302); if (state.failed) return n;
            pushFollow(FOLLOW_rValListMin2_in_lnkDelStat4312);
            p=rValListMin2();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_lnkDelStat4316); if (state.failed) return n;
            match(input,96,FOLLOW_96_in_lnkDelStat4320); if (state.failed) return n;
            ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_lnkDelStat4329); if (state.failed) return n;
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
    // TestSuite.g:1115:1: condExStat returns [ASTConditionalExecutionStatement n] : 'if' con= inSoilExpression 'then' ts= stat ( 'else' es= stat )? 'end' ;
    public final ASTConditionalExecutionStatement condExStat() throws RecognitionException {
        ASTConditionalExecutionStatement n = null;

        ASTExpression con = null;

        TestSuiteParser.stat_return ts = null;

        TestSuiteParser.stat_return es = null;



          ASTStatement elseStat = new ASTEmptyStatement();

        try {
            // TestSuite.g:1119:1: ( 'if' con= inSoilExpression 'then' ts= stat ( 'else' es= stat )? 'end' )
            // TestSuite.g:1120:3: 'if' con= inSoilExpression 'then' ts= stat ( 'else' es= stat )? 'end'
            {
            match(input,73,FOLLOW_73_in_condExStat4360); if (state.failed) return n;
            pushFollow(FOLLOW_inSoilExpression_in_condExStat4369);
            con=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            match(input,74,FOLLOW_74_in_condExStat4373); if (state.failed) return n;
            pushFollow(FOLLOW_stat_in_condExStat4382);
            ts=stat();

            state._fsp--;
            if (state.failed) return n;
            // TestSuite.g:1124:3: ( 'else' es= stat )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==75) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // TestSuite.g:1125:5: 'else' es= stat
                    {
                    match(input,75,FOLLOW_75_in_condExStat4393); if (state.failed) return n;
                    pushFollow(FOLLOW_stat_in_condExStat4405);
                    es=stat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       elseStat = (es!=null?es.n:null); 
                    }

                    }
                    break;

            }

            match(input,49,FOLLOW_49_in_condExStat4416); if (state.failed) return n;
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
    // TestSuite.g:1136:1: iterStat returns [ASTIterationStatement n] : 'for' var= IDENT 'in' set= inSoilExpression 'do' s= stat 'end' ;
    public final ASTIterationStatement iterStat() throws RecognitionException {
        ASTIterationStatement n = null;

        Token var=null;
        ASTExpression set = null;

        TestSuiteParser.stat_return s = null;


        try {
            // TestSuite.g:1137:1: ( 'for' var= IDENT 'in' set= inSoilExpression 'do' s= stat 'end' )
            // TestSuite.g:1138:3: 'for' var= IDENT 'in' set= inSoilExpression 'do' s= stat 'end'
            {
            match(input,45,FOLLOW_45_in_iterStat4441); if (state.failed) return n;
            var=(Token)match(input,IDENT,FOLLOW_IDENT_in_iterStat4449); if (state.failed) return n;
            match(input,61,FOLLOW_61_in_iterStat4453); if (state.failed) return n;
            pushFollow(FOLLOW_inSoilExpression_in_iterStat4461);
            set=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            match(input,97,FOLLOW_97_in_iterStat4465); if (state.failed) return n;
            pushFollow(FOLLOW_stat_in_iterStat4473);
            s=stat();

            state._fsp--;
            if (state.failed) return n;
            match(input,49,FOLLOW_49_in_iterStat4477); if (state.failed) return n;
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
    // TestSuite.g:1153:1: callStat returns [ASTOperationCallStatement n] : e= inSoilExpression ;
    public final ASTOperationCallStatement callStat() throws RecognitionException {
        ASTOperationCallStatement n = null;

        ASTExpression e = null;


        try {
            // TestSuite.g:1154:1: (e= inSoilExpression )
            // TestSuite.g:1155:3: e= inSoilExpression
            {
            pushFollow(FOLLOW_inSoilExpression_in_callStat4507);
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
    // TestSuite.g:1173:1: nothing : ;
    public final void nothing() throws RecognitionException {
        try {
            // TestSuite.g:1174:1: ()
            // TestSuite.g:1175:1: 
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
    // TestSuite.g:1181:1: rValue returns [ASTRValue n] options {backtrack=true; memoize=true; } : (e= inSoilExpression | loc= lobjCreateStat | 'new' objType= simpleType ( LPAREN objName= inSoilExpression RPAREN )? );
    public final TestSuiteParser.rValue_return rValue() throws RecognitionException {
        TestSuiteParser.rValue_return retval = new TestSuiteParser.rValue_return();
        retval.start = input.LT(1);
        int rValue_StartIndex = input.index();
        ASTExpression e = null;

        ASTNewLinkObjectStatement loc = null;

        ASTSimpleType objType = null;

        ASTExpression objName = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 63) ) { return retval; }
            // TestSuite.g:1186:1: (e= inSoilExpression | loc= lobjCreateStat | 'new' objType= simpleType ( LPAREN objName= inSoilExpression RPAREN )? )
            int alt56=3;
            alt56 = dfa56.predict(input);
            switch (alt56) {
                case 1 :
                    // TestSuite.g:1187:3: e= inSoilExpression
                    {
                    pushFollow(FOLLOW_inSoilExpression_in_rValue4579);
                    e=inSoilExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n = new ASTRValueExpressionOrOpCall(e); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:1190:3: loc= lobjCreateStat
                    {
                    pushFollow(FOLLOW_lobjCreateStat_in_rValue4595);
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
                    // TestSuite.g:1197:3: 'new' objType= simpleType ( LPAREN objName= inSoilExpression RPAREN )?
                    {
                    match(input,90,FOLLOW_90_in_rValue4607); if (state.failed) return retval;
                    pushFollow(FOLLOW_simpleType_in_rValue4616);
                    objType=simpleType();

                    state._fsp--;
                    if (state.failed) return retval;
                    // TestSuite.g:1199:3: ( LPAREN objName= inSoilExpression RPAREN )?
                    int alt55=2;
                    int LA55_0 = input.LA(1);

                    if ( (LA55_0==LPAREN) ) {
                        alt55=1;
                    }
                    switch (alt55) {
                        case 1 :
                            // TestSuite.g:1200:5: LPAREN objName= inSoilExpression RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_rValue4626); if (state.failed) return retval;
                            pushFollow(FOLLOW_inSoilExpression_in_rValue4638);
                            objName=inSoilExpression();

                            state._fsp--;
                            if (state.failed) return retval;
                            match(input,RPAREN,FOLLOW_RPAREN_in_rValue4644); if (state.failed) return retval;

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
            if ( state.backtracking>0 ) { memoize(input, 63, rValue_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "rValue"


    // $ANTLR start "rValList"
    // TestSuite.g:1219:1: rValList returns [List<ASTRValue> n] : ( nothing | rl= rValListMin1 );
    public final List<ASTRValue> rValList() throws RecognitionException {
        List<ASTRValue> n = null;

        List<ASTRValue> rl = null;


        try {
            // TestSuite.g:1220:1: ( nothing | rl= rValListMin1 )
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==EOF) ) {
                alt57=1;
            }
            else if ( (LA57_0==IDENT||(LA57_0>=STRING && LA57_0<=LPAREN)||(LA57_0>=PLUS && LA57_0<=MINUS)||LA57_0==AT||(LA57_0>=INT && LA57_0<=HASH)||LA57_0==60||LA57_0==67||(LA57_0>=69 && LA57_0<=73)||(LA57_0>=77 && LA57_0<=88)||LA57_0==90) ) {
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
                    // TestSuite.g:1221:3: nothing
                    {
                    pushFollow(FOLLOW_nothing_in_rValList4672);
                    nothing();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ArrayList<ASTRValue>(); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:1224:3: rl= rValListMin1
                    {
                    pushFollow(FOLLOW_rValListMin1_in_rValList4699);
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
    // TestSuite.g:1232:1: rValListMin1 returns [List<ASTRValue> n] : r= rValue ( COMMA r= rValue )* ;
    public final List<ASTRValue> rValListMin1() throws RecognitionException {
        List<ASTRValue> n = null;

        TestSuiteParser.rValue_return r = null;



          n = new ArrayList<ASTRValue>();

        try {
            // TestSuite.g:1236:1: (r= rValue ( COMMA r= rValue )* )
            // TestSuite.g:1237:3: r= rValue ( COMMA r= rValue )*
            {
            pushFollow(FOLLOW_rValue_in_rValListMin14732);
            r=rValue();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.add((r!=null?r.n:null)); 
            }
            // TestSuite.g:1239:3: ( COMMA r= rValue )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==COMMA) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // TestSuite.g:1240:5: COMMA r= rValue
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_rValListMin14746); if (state.failed) return n;
            	    pushFollow(FOLLOW_rValue_in_rValListMin14756);
            	    r=rValue();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.add((r!=null?r.n:null)); 
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
    // $ANTLR end "rValListMin1"


    // $ANTLR start "rValListMin2"
    // TestSuite.g:1250:1: rValListMin2 returns [List<ASTRValue> n] : r= rValue COMMA r= rValue ( COMMA r= rValue )* ;
    public final List<ASTRValue> rValListMin2() throws RecognitionException {
        List<ASTRValue> n = null;

        TestSuiteParser.rValue_return r = null;



          n = new ArrayList<ASTRValue>();

        try {
            // TestSuite.g:1254:1: (r= rValue COMMA r= rValue ( COMMA r= rValue )* )
            // TestSuite.g:1255:3: r= rValue COMMA r= rValue ( COMMA r= rValue )*
            {
            pushFollow(FOLLOW_rValue_in_rValListMin24795);
            r=rValue();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.add((r!=null?r.n:null)); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_rValListMin24803); if (state.failed) return n;
            pushFollow(FOLLOW_rValue_in_rValListMin24811);
            r=rValue();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.add((r!=null?r.n:null)); 
            }
            // TestSuite.g:1260:3: ( COMMA r= rValue )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==COMMA) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // TestSuite.g:1261:5: COMMA r= rValue
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_rValListMin24825); if (state.failed) return n;
            	    pushFollow(FOLLOW_rValue_in_rValListMin24835);
            	    r=rValue();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.add((r!=null?r.n:null)); 
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
        return n;
    }
    // $ANTLR end "rValListMin2"


    // $ANTLR start "inSoilExpression"
    // TestSuite.g:1271:1: inSoilExpression returns [ASTExpression n] : e= expression ;
    public final ASTExpression inSoilExpression() throws RecognitionException {
        ASTExpression n = null;

        TestSuiteParser.expression_return e = null;


        try {
            // TestSuite.g:1272:1: (e= expression )
            // TestSuite.g:1273:3: e= expression
            {
            pushFollow(FOLLOW_expression_in_inSoilExpression4869);
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
    // TestSuite.g:1282:1: exprList returns [List<ASTExpression> n] : ( nothing | el= exprListMin1 );
    public final List<ASTExpression> exprList() throws RecognitionException {
        List<ASTExpression> n = null;

        List<ASTExpression> el = null;


        try {
            // TestSuite.g:1283:1: ( nothing | el= exprListMin1 )
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==RPAREN) ) {
                alt60=1;
            }
            else if ( (LA60_0==IDENT||(LA60_0>=STRING && LA60_0<=LPAREN)||(LA60_0>=PLUS && LA60_0<=MINUS)||LA60_0==AT||(LA60_0>=INT && LA60_0<=HASH)||LA60_0==60||LA60_0==67||(LA60_0>=69 && LA60_0<=73)||(LA60_0>=77 && LA60_0<=88)) ) {
                alt60=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 60, 0, input);

                throw nvae;
            }
            switch (alt60) {
                case 1 :
                    // TestSuite.g:1284:3: nothing
                    {
                    pushFollow(FOLLOW_nothing_in_exprList4898);
                    nothing();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ArrayList<ASTExpression>(); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:1287:3: el= exprListMin1
                    {
                    pushFollow(FOLLOW_exprListMin1_in_exprList4916);
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
    // TestSuite.g:1295:1: exprListMin1 returns [List<ASTExpression> n] : e= inSoilExpression ( COMMA e= inSoilExpression )* ;
    public final List<ASTExpression> exprListMin1() throws RecognitionException {
        List<ASTExpression> n = null;

        ASTExpression e = null;



          n = new ArrayList<ASTExpression>();

        try {
            // TestSuite.g:1299:1: (e= inSoilExpression ( COMMA e= inSoilExpression )* )
            // TestSuite.g:1300:3: e= inSoilExpression ( COMMA e= inSoilExpression )*
            {
            pushFollow(FOLLOW_inSoilExpression_in_exprListMin14949);
            e=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               if (e != null) n.add(e); 
            }
            // TestSuite.g:1302:3: ( COMMA e= inSoilExpression )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==COMMA) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // TestSuite.g:1303:5: COMMA e= inSoilExpression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_exprListMin14964); if (state.failed) return n;
            	    pushFollow(FOLLOW_inSoilExpression_in_exprListMin14974);
            	    e=inSoilExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       if (e != null) n.add(e); 
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
    // $ANTLR end "exprListMin1"


    // $ANTLR start "exprListMin2"
    // TestSuite.g:1313:1: exprListMin2 returns [List<ASTExpression> n] : e= inSoilExpression COMMA e= inSoilExpression ( COMMA e= inSoilExpression )* ;
    public final List<ASTExpression> exprListMin2() throws RecognitionException {
        List<ASTExpression> n = null;

        ASTExpression e = null;



          n = new ArrayList<ASTExpression>();

        try {
            // TestSuite.g:1317:1: (e= inSoilExpression COMMA e= inSoilExpression ( COMMA e= inSoilExpression )* )
            // TestSuite.g:1318:3: e= inSoilExpression COMMA e= inSoilExpression ( COMMA e= inSoilExpression )*
            {
            pushFollow(FOLLOW_inSoilExpression_in_exprListMin25014);
            e=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               if (e != null) n.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_exprListMin25022); if (state.failed) return n;
            pushFollow(FOLLOW_inSoilExpression_in_exprListMin25030);
            e=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               if (e != null) n.add(e); 
            }
            // TestSuite.g:1323:3: ( COMMA e= inSoilExpression )*
            loop62:
            do {
                int alt62=2;
                int LA62_0 = input.LA(1);

                if ( (LA62_0==COMMA) ) {
                    alt62=1;
                }


                switch (alt62) {
            	case 1 :
            	    // TestSuite.g:1324:5: COMMA e= inSoilExpression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_exprListMin25044); if (state.failed) return n;
            	    pushFollow(FOLLOW_inSoilExpression_in_exprListMin25054);
            	    e=inSoilExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       if (e != null) n.add(e); 
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
    // $ANTLR end "exprListMin2"


    // $ANTLR start "identList"
    // TestSuite.g:1334:1: identList returns [List<String> n] : ( nothing | il= identListMin1 );
    public final List<String> identList() throws RecognitionException {
        List<String> n = null;

        List<String> il = null;


        try {
            // TestSuite.g:1335:1: ( nothing | il= identListMin1 )
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==EOF||LA63_0==RPAREN||LA63_0==SEMI||(LA63_0>=48 && LA63_0<=49)||(LA63_0>=51 && LA63_0<=53)||LA63_0==75) ) {
                alt63=1;
            }
            else if ( (LA63_0==IDENT) ) {
                alt63=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;
            }
            switch (alt63) {
                case 1 :
                    // TestSuite.g:1336:3: nothing
                    {
                    pushFollow(FOLLOW_nothing_in_identList5084);
                    nothing();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ArrayList<String>(); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:1339:3: il= identListMin1
                    {
                    pushFollow(FOLLOW_identListMin1_in_identList5101);
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
    // TestSuite.g:1347:1: identListMin1 returns [List<String> n] : id= IDENT ( COMMA id= IDENT )* ;
    public final List<String> identListMin1() throws RecognitionException {
        List<String> n = null;

        Token id=null;


          n = new ArrayList<String>();

        try {
            // TestSuite.g:1351:1: (id= IDENT ( COMMA id= IDENT )* )
            // TestSuite.g:1352:3: id= IDENT ( COMMA id= IDENT )*
            {
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_identListMin15135); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.add((id!=null?id.getText():null)); 
            }
            // TestSuite.g:1354:3: ( COMMA id= IDENT )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==COMMA) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // TestSuite.g:1355:5: COMMA id= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_identListMin15149); if (state.failed) return n;
            	    id=(Token)match(input,IDENT,FOLLOW_IDENT_in_identListMin15159); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {

            	          n.add((id!=null?id.getText():null)); 
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
    // $ANTLR end "identListMin1"

    // $ANTLR start synpred1_TestSuite
    public final void synpred1_TestSuite_fragment() throws RecognitionException {   
        // TestSuite.g:701:7: ( COLON type EQUAL )
        // TestSuite.g:701:8: COLON type EQUAL
        {
        match(input,COLON,FOLLOW_COLON_in_synpred1_TestSuite3038); if (state.failed) return ;
        pushFollow(FOLLOW_type_in_synpred1_TestSuite3040);
        type();

        state._fsp--;
        if (state.failed) return ;
        match(input,EQUAL,FOLLOW_EQUAL_in_synpred1_TestSuite3042); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_TestSuite

    // $ANTLR start synpred3_TestSuite
    public final void synpred3_TestSuite_fragment() throws RecognitionException {   
        TestSuiteParser.varAssignStat_return vas = null;


        // TestSuite.g:885:5: (vas= varAssignStat )
        // TestSuite.g:885:5: vas= varAssignStat
        {
        pushFollow(FOLLOW_varAssignStat_in_synpred3_TestSuite3670);
        vas=varAssignStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_TestSuite

    // $ANTLR start synpred4_TestSuite
    public final void synpred4_TestSuite_fragment() throws RecognitionException {   
        ASTAttributeAssignmentStatement aas = null;


        // TestSuite.g:886:5: (aas= attAssignStat )
        // TestSuite.g:886:5: aas= attAssignStat
        {
        pushFollow(FOLLOW_attAssignStat_in_synpred4_TestSuite3684);
        aas=attAssignStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_TestSuite

    // $ANTLR start synpred5_TestSuite
    public final void synpred5_TestSuite_fragment() throws RecognitionException {   
        ASTNewLinkObjectStatement lcs = null;


        // TestSuite.g:887:5: (lcs= lobjCreateStat )
        // TestSuite.g:887:5: lcs= lobjCreateStat
        {
        pushFollow(FOLLOW_lobjCreateStat_in_synpred5_TestSuite3698);
        lcs=lobjCreateStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_TestSuite

    // $ANTLR start synpred6_TestSuite
    public final void synpred6_TestSuite_fragment() throws RecognitionException {   
        TestSuiteParser.objCreateStat_return ocs = null;


        // TestSuite.g:888:5: (ocs= objCreateStat )
        // TestSuite.g:888:5: ocs= objCreateStat
        {
        pushFollow(FOLLOW_objCreateStat_in_synpred6_TestSuite3711);
        ocs=objCreateStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_TestSuite

    // $ANTLR start synpred10_TestSuite
    public final void synpred10_TestSuite_fragment() throws RecognitionException {   
        ASTConditionalExecutionStatement ces = null;


        // TestSuite.g:892:5: (ces= condExStat )
        // TestSuite.g:892:5: ces= condExStat
        {
        pushFollow(FOLLOW_condExStat_in_synpred10_TestSuite3772);
        ces=condExStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred10_TestSuite

    // $ANTLR start synpred12_TestSuite
    public final void synpred12_TestSuite_fragment() throws RecognitionException {   
        Token varName=null;
        TestSuiteParser.rValue_return rVal = null;


        // TestSuite.g:919:3: (varName= IDENT COLON_EQUAL rVal= rValue )
        // TestSuite.g:919:3: varName= IDENT COLON_EQUAL rVal= rValue
        {
        varName=(Token)match(input,IDENT,FOLLOW_IDENT_in_synpred12_TestSuite3892); if (state.failed) return ;
        match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_synpred12_TestSuite3896); if (state.failed) return ;
        pushFollow(FOLLOW_rValue_in_synpred12_TestSuite3904);
        rVal=rValue();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred12_TestSuite

    // $ANTLR start synpred14_TestSuite
    public final void synpred14_TestSuite_fragment() throws RecognitionException {   
        ASTNewLinkObjectStatement loc = null;


        // TestSuite.g:1190:3: (loc= lobjCreateStat )
        // TestSuite.g:1190:3: loc= lobjCreateStat
        {
        pushFollow(FOLLOW_lobjCreateStat_in_synpred14_TestSuite4595);
        loc=lobjCreateStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred14_TestSuite

    // Delegated rules

    public final boolean synpred10_TestSuite() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_TestSuite_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_TestSuite() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_TestSuite_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_TestSuite() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_TestSuite_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_TestSuite() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_TestSuite_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_TestSuite() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_TestSuite_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred12_TestSuite() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred12_TestSuite_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_TestSuite() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_TestSuite_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_TestSuite() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_TestSuite_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA49 dfa49 = new DFA49(this);
    protected DFA56 dfa56 = new DFA56(this);
    static final String DFA49_eotS =
        "\51\uffff";
    static final String DFA49_eofS =
        "\1\1\50\uffff";
    static final String DFA49_minS =
        "\1\4\10\uffff\26\0\12\uffff";
    static final String DFA49_maxS =
        "\1\137\10\uffff\26\0\12\uffff";
    static final String DFA49_acceptS =
        "\1\uffff\1\1\35\uffff\1\6\1\7\1\10\1\12\1\2\1\3\1\13\1\11\1\4\1"+
        "\5";
    static final String DFA49_specialS =
        "\11\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1"+
        "\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\12\uffff}>";
    static final String[] DFA49_transitionS = {
            "\1\11\2\uffff\1\20\1\34\10\uffff\2\13\4\uffff\1\31\1\uffff"+
            "\1\1\2\uffff\1\16\1\17\1\21\16\uffff\1\42\2\uffff\2\1\1\uffff"+
            "\3\1\6\uffff\1\12\6\uffff\1\13\1\uffff\1\32\3\33\1\35\1\uffff"+
            "\1\1\1\uffff\1\14\1\15\4\22\1\23\1\24\1\25\1\26\1\27\1\30\1"+
            "\uffff\1\36\1\uffff\1\37\1\40\1\uffff\1\41",
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

    static final short[] DFA49_eot = DFA.unpackEncodedString(DFA49_eotS);
    static final short[] DFA49_eof = DFA.unpackEncodedString(DFA49_eofS);
    static final char[] DFA49_min = DFA.unpackEncodedStringToUnsignedChars(DFA49_minS);
    static final char[] DFA49_max = DFA.unpackEncodedStringToUnsignedChars(DFA49_maxS);
    static final short[] DFA49_accept = DFA.unpackEncodedString(DFA49_acceptS);
    static final short[] DFA49_special = DFA.unpackEncodedString(DFA49_specialS);
    static final short[][] DFA49_transition;

    static {
        int numStates = DFA49_transitionS.length;
        DFA49_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA49_transition[i] = DFA.unpackEncodedString(DFA49_transitionS[i]);
        }
    }

    class DFA49 extends DFA {

        public DFA49(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 49;
            this.eot = DFA49_eot;
            this.eof = DFA49_eof;
            this.min = DFA49_min;
            this.max = DFA49_max;
            this.accept = DFA49_accept;
            this.special = DFA49_special;
            this.transition = DFA49_transition;
        }
        public String getDescription() {
            return "877:1: singleStat returns [ASTStatement n] options {backtrack=true; memoize=true; } : (emp= emptyStat | vas= varAssignStat | aas= attAssignStat | lcs= lobjCreateStat | ocs= objCreateStat | ods= objDestroyStat | lis= lnkInsStat | lds= lnkDelStat | ces= condExStat | its= iterStat | ops= callStat );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA49_9 = input.LA(1);

                         
                        int index49_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_TestSuite()) ) {s = 35;}

                        else if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_9);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA49_10 = input.LA(1);

                         
                        int index49_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_10);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA49_11 = input.LA(1);

                         
                        int index49_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_11);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA49_12 = input.LA(1);

                         
                        int index49_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_12);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA49_13 = input.LA(1);

                         
                        int index49_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_13);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA49_14 = input.LA(1);

                         
                        int index49_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_14);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA49_15 = input.LA(1);

                         
                        int index49_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_15);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA49_16 = input.LA(1);

                         
                        int index49_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_16);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA49_17 = input.LA(1);

                         
                        int index49_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_17);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA49_18 = input.LA(1);

                         
                        int index49_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_18);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA49_19 = input.LA(1);

                         
                        int index49_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_19);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA49_20 = input.LA(1);

                         
                        int index49_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_20);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA49_21 = input.LA(1);

                         
                        int index49_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_21);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA49_22 = input.LA(1);

                         
                        int index49_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_22);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA49_23 = input.LA(1);

                         
                        int index49_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_23);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA49_24 = input.LA(1);

                         
                        int index49_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_24);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA49_25 = input.LA(1);

                         
                        int index49_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_25);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA49_26 = input.LA(1);

                         
                        int index49_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_26);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA49_27 = input.LA(1);

                         
                        int index49_27 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_27);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA49_28 = input.LA(1);

                         
                        int index49_28 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_28);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA49_29 = input.LA(1);

                         
                        int index49_29 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_TestSuite()) ) {s = 36;}

                        else if ( (synpred10_TestSuite()) ) {s = 38;}

                        else if ( (true) ) {s = 37;}

                         
                        input.seek(index49_29);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA49_30 = input.LA(1);

                         
                        int index49_30 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_TestSuite()) ) {s = 39;}

                        else if ( (synpred6_TestSuite()) ) {s = 40;}

                         
                        input.seek(index49_30);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 49, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA56_eotS =
        "\31\uffff";
    static final String DFA56_eofS =
        "\31\uffff";
    static final String DFA56_minS =
        "\1\4\25\uffff\1\0\2\uffff";
    static final String DFA56_maxS =
        "\1\132\25\uffff\1\0\2\uffff";
    static final String DFA56_acceptS =
        "\1\uffff\1\1\25\uffff\1\2\1\3";
    static final String DFA56_specialS =
        "\26\uffff\1\0\2\uffff}>";
    static final String[] DFA56_transitionS = {
            "\1\1\2\uffff\2\1\10\uffff\2\1\4\uffff\1\1\4\uffff\3\1\35\uffff"+
            "\1\1\6\uffff\1\1\1\uffff\5\1\3\uffff\14\1\1\uffff\1\26",
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

    static final short[] DFA56_eot = DFA.unpackEncodedString(DFA56_eotS);
    static final short[] DFA56_eof = DFA.unpackEncodedString(DFA56_eofS);
    static final char[] DFA56_min = DFA.unpackEncodedStringToUnsignedChars(DFA56_minS);
    static final char[] DFA56_max = DFA.unpackEncodedStringToUnsignedChars(DFA56_maxS);
    static final short[] DFA56_accept = DFA.unpackEncodedString(DFA56_acceptS);
    static final short[] DFA56_special = DFA.unpackEncodedString(DFA56_specialS);
    static final short[][] DFA56_transition;

    static {
        int numStates = DFA56_transitionS.length;
        DFA56_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA56_transition[i] = DFA.unpackEncodedString(DFA56_transitionS[i]);
        }
    }

    class DFA56 extends DFA {

        public DFA56(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 56;
            this.eot = DFA56_eot;
            this.eof = DFA56_eof;
            this.min = DFA56_min;
            this.max = DFA56_max;
            this.accept = DFA56_accept;
            this.special = DFA56_special;
            this.transition = DFA56_transition;
        }
        public String getDescription() {
            return "1181:1: rValue returns [ASTRValue n] options {backtrack=true; memoize=true; } : (e= inSoilExpression | loc= lobjCreateStat | 'new' objType= simpleType ( LPAREN objName= inSoilExpression RPAREN )? );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA56_22 = input.LA(1);

                         
                        int index56_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred14_TestSuite()) ) {s = 23;}

                        else if ( (true) ) {s = 24;}

                         
                        input.seek(index56_22);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 56, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_44_in_testSuite61 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_testSuite71 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_45_in_testSuite82 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_46_in_testSuite84 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_filename_in_testSuite93 = new BitSet(new long[]{0x0004800000000000L});
    public static final BitSet FOLLOW_47_in_testSuite105 = new BitSet(new long[]{0x0003000000000000L});
    public static final BitSet FOLLOW_48_in_testSuite112 = new BitSet(new long[]{0x1000200070860190L,0x00000000B5FFE3E8L});
    public static final BitSet FOLLOW_stat_in_testSuite118 = new BitSet(new long[]{0x0003000000000000L});
    public static final BitSet FOLLOW_49_in_testSuite124 = new BitSet(new long[]{0x0004800000000000L});
    public static final BitSet FOLLOW_testCases_in_testSuite145 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_testSuite154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_filename172 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_DOT_in_filename174 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_filename178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_testCase_in_testCases205 = new BitSet(new long[]{0x0004800000000002L});
    public static final BitSet FOLLOW_50_in_testCase224 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_testCase228 = new BitSet(new long[]{0x003B000000000000L});
    public static final BitSet FOLLOW_48_in_testCase242 = new BitSet(new long[]{0x1000200070860190L,0x00000000B5FFE3E8L});
    public static final BitSet FOLLOW_stat_in_testCase248 = new BitSet(new long[]{0x003B000000000000L});
    public static final BitSet FOLLOW_assertStatement_in_testCase267 = new BitSet(new long[]{0x003B000000000000L});
    public static final BitSet FOLLOW_51_in_testCase285 = new BitSet(new long[]{0x003B000000000000L});
    public static final BitSet FOLLOW_52_in_testCase303 = new BitSet(new long[]{0x003B000000000000L});
    public static final BitSet FOLLOW_49_in_testCase315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_assertStatement336 = new BitSet(new long[]{0x00C0000000000000L});
    public static final BitSet FOLLOW_54_in_assertStatement341 = new BitSet(new long[]{0x1F00000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_55_in_assertStatement347 = new BitSet(new long[]{0x1F00000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_assertStatement366 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_56_in_assertStatement382 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_56_in_assertStatement398 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_assertStatement402 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_57_in_assertStatement418 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_assertStatement422 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_COLON_in_assertStatement424 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_assertStatement428 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_assertionStatementPre_in_assertStatement448 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_assertionStatementPost_in_assertStatement469 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_assertStatement486 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_STRING_in_assertStatement490 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_58_in_assertionStatementPre513 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_assertionStatementPre517 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_assertionStatementPre521 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_assertionStatementPre527 = new BitSet(new long[]{0x1000000070860390L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_assertionStatementPre538 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_COMMA_in_assertionStatementPre544 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_assertionStatementPre548 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_RPAREN_in_assertionStatementPre561 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_COLON_COLON_in_assertionStatementPre564 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_assertionStatementPre568 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_59_in_assertionStatementPost593 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_IDENT_in_assertionStatementPost602 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_expressionOnly641 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_expressionOnly643 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_expression691 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_expression695 = new BitSet(new long[]{0x0000000000000C00L});
    public static final BitSet FOLLOW_COLON_in_expression699 = new BitSet(new long[]{0x0000000000000010L,0x0000000002878000L});
    public static final BitSet FOLLOW_type_in_expression703 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_expression708 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_expression712 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_expression714 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_conditionalImpliesExpression_in_expression739 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_paramList772 = new BitSet(new long[]{0x0000000000000210L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList789 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_COMMA_in_paramList801 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList805 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_RPAREN_in_paramList825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_idList854 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_idList864 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_idList868 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_IDENT_in_variableDeclaration899 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_COLON_in_variableDeclaration901 = new BitSet(new long[]{0x0000000000000010L,0x0000000002878000L});
    public static final BitSet FOLLOW_type_in_variableDeclaration905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression941 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_62_in_conditionalImpliesExpression954 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression958 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression1003 = new BitSet(new long[]{0x8000000000000002L});
    public static final BitSet FOLLOW_63_in_conditionalOrExpression1016 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression1020 = new BitSet(new long[]{0x8000000000000002L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression1064 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_conditionalXOrExpression1077 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression1081 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression1125 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_conditionalAndExpression1138 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression1142 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression1190 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_set_in_equalityExpression1209 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression1219 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression1268 = new BitSet(new long[]{0x000000000001E002L});
    public static final BitSet FOLLOW_set_in_relationalExpression1286 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression1304 = new BitSet(new long[]{0x000000000001E002L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1354 = new BitSet(new long[]{0x0000000000060002L});
    public static final BitSet FOLLOW_set_in_additiveExpression1372 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1382 = new BitSet(new long[]{0x0000000000060002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1432 = new BitSet(new long[]{0x0000000000180002L,0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression1450 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1464 = new BitSet(new long[]{0x0000000000180002L,0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_unaryExpression1526 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression1550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpression_in_unaryExpression1570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_postfixExpression1603 = new BitSet(new long[]{0x0000000000600002L});
    public static final BitSet FOLLOW_ARROW_in_postfixExpression1621 = new BitSet(new long[]{0x0000000000000010L,0x00000000000001E0L});
    public static final BitSet FOLLOW_DOT_in_postfixExpression1627 = new BitSet(new long[]{0x0000000000000010L,0x00000000000001E0L});
    public static final BitSet FOLLOW_propertyCall_in_postfixExpression1638 = new BitSet(new long[]{0x0000000000600002L});
    public static final BitSet FOLLOW_literal_in_primaryExpression1678 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objectReference_in_primaryExpression1692 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyCall_in_primaryExpression1704 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression1715 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_primaryExpression1719 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression1721 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifExpression_in_primaryExpression1733 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_primaryExpression1750 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_DOT_in_primaryExpression1752 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_primaryExpression1754 = new BitSet(new long[]{0x0000000000800102L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression1758 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression1760 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_AT_in_primaryExpression1781 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_primaryExpression1783 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_in_objectReference1810 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_objectReference1818 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_queryExpression_in_propertyCall1886 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterateExpression_in_propertyCall1899 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationExpression_in_propertyCall1912 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeExpression_in_propertyCall1925 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_queryExpression1960 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_queryExpression1967 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_queryExpression1978 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_BAR_in_queryExpression1982 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_queryExpression1993 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_queryExpression1999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_iterateExpression2031 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_iterateExpression2037 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_iterateExpression2045 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_SEMI_in_iterateExpression2047 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableInitialization_in_iterateExpression2055 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_BAR_in_iterateExpression2057 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_iterateExpression2065 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_iterateExpression2071 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression2115 = new BitSet(new long[]{0x0000000004800102L});
    public static final BitSet FOLLOW_LBRACK_in_operationExpression2131 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression2135 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_RBRACK_in_operationExpression2137 = new BitSet(new long[]{0x0000000000800102L});
    public static final BitSet FOLLOW_AT_in_operationExpression2150 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_operationExpression2152 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_LPAREN_in_operationExpression2173 = new BitSet(new long[]{0x1000000070860390L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_operationExpression2194 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_COMMA_in_operationExpression2206 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_operationExpression2210 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_RPAREN_in_operationExpression2230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_typeExpression2279 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_typeExpression2295 = new BitSet(new long[]{0x0000000000000010L,0x0000000002878000L});
    public static final BitSet FOLLOW_type_in_typeExpression2299 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_typeExpression2301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_elemVarsDeclaration2340 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COLON_in_elemVarsDeclaration2348 = new BitSet(new long[]{0x0000000000000010L,0x0000000002878000L});
    public static final BitSet FOLLOW_type_in_elemVarsDeclaration2352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableInitialization2387 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_COLON_in_variableInitialization2389 = new BitSet(new long[]{0x0000000000000010L,0x0000000002878000L});
    public static final BitSet FOLLOW_type_in_variableInitialization2393 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_variableInitialization2395 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_variableInitialization2399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_ifExpression2431 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_ifExpression2435 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_ifExpression2437 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_ifExpression2441 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_ifExpression2443 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_ifExpression2447 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_ifExpression2449 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_literal2488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_literal2502 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_literal2515 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REAL_in_literal2530 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal2544 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_literal2554 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_literal2558 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_literal2570 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_COLON_in_literal2572 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_literal2576 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionLiteral_in_literal2588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyCollectionLiteral_in_literal2600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedLiteral_in_literal2612 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleLiteral_in_literal2624 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dateLiteral_in_literal2636 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionLiteral2674 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_LBRACE_in_collectionLiteral2703 = new BitSet(new long[]{0x1000000170860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral2720 = new BitSet(new long[]{0x0000000100000040L});
    public static final BitSet FOLLOW_COMMA_in_collectionLiteral2733 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral2737 = new BitSet(new long[]{0x0000000100000040L});
    public static final BitSet FOLLOW_RBRACE_in_collectionLiteral2756 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_collectionItem2785 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_DOTDOT_in_collectionItem2796 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_collectionItem2800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_emptyCollectionLiteral2829 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_emptyCollectionLiteral2831 = new BitSet(new long[]{0x0000000000000000L,0x0000000002078000L});
    public static final BitSet FOLLOW_collectionType_in_emptyCollectionLiteral2835 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_emptyCollectionLiteral2837 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_undefinedLiteral2867 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_undefinedLiteral2869 = new BitSet(new long[]{0x0000000000000010L,0x0000000002878000L});
    public static final BitSet FOLLOW_type_in_undefinedLiteral2873 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_undefinedLiteral2875 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_85_in_undefinedLiteral2889 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_86_in_undefinedLiteral2903 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_87_in_tupleLiteral2937 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_LBRACE_in_tupleLiteral2943 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral2951 = new BitSet(new long[]{0x0000000100000040L});
    public static final BitSet FOLLOW_COMMA_in_tupleLiteral2962 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral2966 = new BitSet(new long[]{0x0000000100000040L});
    public static final BitSet FOLLOW_RBRACE_in_tupleLiteral2977 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tupleItem3008 = new BitSet(new long[]{0x0000000000000C00L});
    public static final BitSet FOLLOW_COLON_in_tupleItem3047 = new BitSet(new long[]{0x0000000000000010L,0x0000000002878000L});
    public static final BitSet FOLLOW_type_in_tupleItem3051 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_tupleItem3053 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_tupleItem3057 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_tupleItem3079 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_tupleItem3089 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_dateLiteral3134 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_LBRACE_in_dateLiteral3136 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_STRING_in_dateLiteral3140 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_RBRACE_in_dateLiteral3142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleType_in_type3192 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_type3204 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleType_in_type3216 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeOnly3248 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_typeOnly3250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_simpleType3278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionType3316 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_collectionType3343 = new BitSet(new long[]{0x0000000000000010L,0x0000000002878000L});
    public static final BitSet FOLLOW_type_in_collectionType3347 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_collectionType3349 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_87_in_tupleType3383 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_tupleType3385 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType3394 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_COMMA_in_tupleType3405 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType3409 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_RPAREN_in_tupleType3421 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tuplePart3453 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_COLON_in_tuplePart3455 = new BitSet(new long[]{0x0000000000000010L,0x0000000002878000L});
    public static final BitSet FOLLOW_type_in_tuplePart3459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stat_in_statOnly3508 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_statOnly3512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nextStat_in_stat3543 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_SEMI_in_stat3554 = new BitSet(new long[]{0x1000200070860190L,0x00000000B5FFE3E8L});
    public static final BitSet FOLLOW_nextStat_in_stat3560 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_singleStat_in_nextStat3594 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyStat_in_singleStat3649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varAssignStat_in_singleStat3670 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attAssignStat_in_singleStat3684 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lobjCreateStat_in_singleStat3698 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objCreateStat_in_singleStat3711 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objDestroyStat_in_singleStat3725 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lnkInsStat_in_singleStat3738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lnkDelStat_in_singleStat3755 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_condExStat_in_singleStat3772 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterStat_in_singleStat3789 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_callStat_in_singleStat3808 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nothing_in_emptyStat3839 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_varAssignStat3892 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_varAssignStat3896 = new BitSet(new long[]{0x1000000070860190L,0x0000000005FFE3E8L});
    public static final BitSet FOLLOW_rValue_in_varAssignStat3904 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identListMin1_in_varAssignStat3923 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_varAssignStat3927 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_varAssignStat3931 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_simpleType_in_varAssignStat3939 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_LPAREN_in_varAssignStat3949 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_exprList_in_varAssignStat3961 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_varAssignStat3967 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_attAssignStat4002 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_DOT_in_attAssignStat4007 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_attAssignStat4016 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_attAssignStat4020 = new BitSet(new long[]{0x1000000070860190L,0x0000000005FFE3E8L});
    public static final BitSet FOLLOW_rValue_in_attAssignStat4028 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_objCreateStat4054 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_simpleType_in_objCreateStat4062 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_LPAREN_in_objCreateStat4072 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_exprListMin1_in_objCreateStat4084 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_objCreateStat4090 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_lobjCreateStat4121 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_lobjCreateStat4129 = new BitSet(new long[]{0x0000000000000100L,0x0000000008000000L});
    public static final BitSet FOLLOW_LPAREN_in_lobjCreateStat4139 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_inSoilExpression_in_lobjCreateStat4151 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_lobjCreateStat4157 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_lobjCreateStat4166 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_lobjCreateStat4170 = new BitSet(new long[]{0x1000000070860190L,0x0000000005FFE3E8L});
    public static final BitSet FOLLOW_rValListMin2_in_lobjCreateStat4180 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_lobjCreateStat4184 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_objDestroyStat4210 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_exprListMin1_in_objDestroyStat4218 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_lnkInsStat4244 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_lnkInsStat4248 = new BitSet(new long[]{0x1000000070860190L,0x0000000005FFE3E8L});
    public static final BitSet FOLLOW_rValListMin2_in_lnkInsStat4258 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_lnkInsStat4262 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_94_in_lnkInsStat4266 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_lnkInsStat4274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_95_in_lnkDelStat4298 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_lnkDelStat4302 = new BitSet(new long[]{0x1000000070860190L,0x0000000005FFE3E8L});
    public static final BitSet FOLLOW_rValListMin2_in_lnkDelStat4312 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_lnkDelStat4316 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_96_in_lnkDelStat4320 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_lnkDelStat4329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_condExStat4360 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_inSoilExpression_in_condExStat4369 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_condExStat4373 = new BitSet(new long[]{0x1000200070860190L,0x00000000B5FFE3E8L});
    public static final BitSet FOLLOW_stat_in_condExStat4382 = new BitSet(new long[]{0x0002000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_condExStat4393 = new BitSet(new long[]{0x1000200070860190L,0x00000000B5FFE3E8L});
    public static final BitSet FOLLOW_stat_in_condExStat4405 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_condExStat4416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_iterStat4441 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_iterStat4449 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_iterStat4453 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_inSoilExpression_in_iterStat4461 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_97_in_iterStat4465 = new BitSet(new long[]{0x1000200070860190L,0x00000000B5FFE3E8L});
    public static final BitSet FOLLOW_stat_in_iterStat4473 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_49_in_iterStat4477 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_callStat4507 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_rValue4579 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lobjCreateStat_in_rValue4595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_rValue4607 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_simpleType_in_rValue4616 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_LPAREN_in_rValue4626 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_inSoilExpression_in_rValue4638 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_rValue4644 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nothing_in_rValList4672 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rValListMin1_in_rValList4699 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rValue_in_rValListMin14732 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_rValListMin14746 = new BitSet(new long[]{0x1000000070860190L,0x0000000005FFE3E8L});
    public static final BitSet FOLLOW_rValue_in_rValListMin14756 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_rValue_in_rValListMin24795 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_rValListMin24803 = new BitSet(new long[]{0x1000000070860190L,0x0000000005FFE3E8L});
    public static final BitSet FOLLOW_rValue_in_rValListMin24811 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_rValListMin24825 = new BitSet(new long[]{0x1000000070860190L,0x0000000005FFE3E8L});
    public static final BitSet FOLLOW_rValue_in_rValListMin24835 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_expression_in_inSoilExpression4869 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nothing_in_exprList4898 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exprListMin1_in_exprList4916 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin14949 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_exprListMin14964 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin14974 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin25014 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_exprListMin25022 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin25030 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_exprListMin25044 = new BitSet(new long[]{0x1000000070860190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin25054 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_nothing_in_identList5084 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identListMin1_in_identList5101 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_identListMin15135 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_identListMin15149 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_identListMin15159 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COLON_in_synpred1_TestSuite3038 = new BitSet(new long[]{0x0000000000000010L,0x0000000002878000L});
    public static final BitSet FOLLOW_type_in_synpred1_TestSuite3040 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_synpred1_TestSuite3042 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varAssignStat_in_synpred3_TestSuite3670 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attAssignStat_in_synpred4_TestSuite3684 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lobjCreateStat_in_synpred5_TestSuite3698 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objCreateStat_in_synpred6_TestSuite3711 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_condExStat_in_synpred10_TestSuite3772 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_synpred12_TestSuite3892 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_synpred12_TestSuite3896 = new BitSet(new long[]{0x1000000070860190L,0x0000000005FFE3E8L});
    public static final BitSet FOLLOW_rValue_in_synpred12_TestSuite3904 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lobjCreateStat_in_synpred14_TestSuite4595 = new BitSet(new long[]{0x0000000000000002L});

}