// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 TestSuite.g 2010-04-22 16:12:32
 
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
import org.tzi.use.parser.cmd.*;
import org.tzi.use.parser.ocl.*;
import org.tzi.use.uml.sys.MCmdShowHideCrop.Mode;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all") public class TestSuiteParser extends BaseParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "IDENT", "COLON_COLON", "COMMA", "STRING", "LPAREN", "RPAREN", "COLON", "EQUAL", "NOT_EQUAL", "LESS", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "PLUS", "MINUS", "STAR", "SLASH", "ARROW", "DOT", "AT", "BAR", "SEMI", "LBRACK", "RBRACK", "INT", "REAL", "HASH", "LBRACE", "RBRACE", "DOTDOT", "COLON_EQUAL", "NEWLINE", "WS", "SL_COMMENT", "ML_COMMENT", "SCRIPTBODY", "RANGE_OR_INT", "ESC", "HEX_DIGIT", "VOCAB", "'testsuite'", "'for'", "'model'", "'setup'", "'!'", "'end'", "'testcase'", "'beginVariation'", "'endVariation'", "'assert'", "'valid'", "'invalid'", "'invs'", "'inv'", "'pre'", "'post'", "'let'", "'in'", "'implies'", "'or'", "'xor'", "'and'", "'div'", "'not'", "'allInstances'", "'iterate'", "'oclAsType'", "'oclIsKindOf'", "'oclIsTypeOf'", "'if'", "'then'", "'else'", "'endif'", "'true'", "'false'", "'Set'", "'Sequence'", "'Bag'", "'OrderedSet'", "'oclEmpty'", "'oclUndefined'", "'Undefined'", "'null'", "'Tuple'", "'Date'", "'Collection'", "'create'", "'assign'", "'between'", "'destroy'", "'insert'", "'into'", "'delete'", "'from'", "'set'", "'openter'", "'opexit'", "'hide'", "'all'", "'link'", "'show'", "'crop'"
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
    public static final int T__99=99;
    public static final int T__98=98;
    public static final int T__97=97;
    public static final int T__96=96;
    public static final int T__95=95;
    public static final int RBRACK=27;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int RBRACE=32;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int INT=28;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int REAL=29;
    public static final int WS=36;
    public static final int T__71=71;
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
    public static final int COLON_EQUAL=34;
    public static final int T__51=51;
    public static final int SLASH=20;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int COMMA=6;
    public static final int T__59=59;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int EQUAL=11;
    public static final int T__105=105;
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
    public static final int T__102=102;
    public static final int COLON_COLON=5;
    public static final int T__101=101;
    public static final int T__100=100;
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
             
        }
        

    public String[] getTokenNames() { return TestSuiteParser.tokenNames; }
    public String getGrammarFileName() { return "TestSuite.g"; }



    // $ANTLR start "testSuite"
    // TestSuite.g:83:1: testSuite returns [ASTTestSuite suite] : 'testsuite' suiteName= IDENT 'for' 'model' modelFile= filename ( 'setup' ( '!' c= cmd )* 'end' )? tests= testCases EOF ;
    public final ASTTestSuite testSuite() throws RecognitionException {
        ASTTestSuite suite = null;

        Token suiteName=null;
        String modelFile = null;

        ASTCmd c = null;

        List tests = null;



          List setupStatements = new ArrayList();

        try {
            // TestSuite.g:87:1: ( 'testsuite' suiteName= IDENT 'for' 'model' modelFile= filename ( 'setup' ( '!' c= cmd )* 'end' )? tests= testCases EOF )
            // TestSuite.g:88:3: 'testsuite' suiteName= IDENT 'for' 'model' modelFile= filename ( 'setup' ( '!' c= cmd )* 'end' )? tests= testCases EOF
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
            // TestSuite.g:94:3: ( 'setup' ( '!' c= cmd )* 'end' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==47) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // TestSuite.g:94:4: 'setup' ( '!' c= cmd )* 'end'
                    {
                    match(input,47,FOLLOW_47_in_testSuite105); if (state.failed) return suite;
                    // TestSuite.g:95:4: ( '!' c= cmd )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0==48) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // TestSuite.g:95:5: '!' c= cmd
                    	    {
                    	    match(input,48,FOLLOW_48_in_testSuite112); if (state.failed) return suite;
                    	    pushFollow(FOLLOW_cmd_in_testSuite118);
                    	    c=cmd();

                    	    state._fsp--;
                    	    if (state.failed) return suite;
                    	    if ( state.backtracking==0 ) {
                    	       setupStatements.add(c); 
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
    // TestSuite.g:114:1: testCase returns [ASTTestCase n] : 'testcase' name= IDENT ( '!' c= cmd | a= assertStatement | b= 'beginVariation' | e= 'endVariation' )* 'end' ;
    public final ASTTestCase testCase() throws RecognitionException {
        ASTTestCase n = null;

        Token name=null;
        Token b=null;
        Token e=null;
        ASTCmd c = null;

        ASTAssert a = null;


        try {
            // TestSuite.g:115:1: ( 'testcase' name= IDENT ( '!' c= cmd | a= assertStatement | b= 'beginVariation' | e= 'endVariation' )* 'end' )
            // TestSuite.g:116:3: 'testcase' name= IDENT ( '!' c= cmd | a= assertStatement | b= 'beginVariation' | e= 'endVariation' )* 'end'
            {
            match(input,50,FOLLOW_50_in_testCase224); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_testCase228); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTTestCase(name); 
            }
            // TestSuite.g:117:3: ( '!' c= cmd | a= assertStatement | b= 'beginVariation' | e= 'endVariation' )*
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
            	    // TestSuite.g:118:7: '!' c= cmd
            	    {
            	    match(input,48,FOLLOW_48_in_testCase242); if (state.failed) return n;
            	    pushFollow(FOLLOW_cmd_in_testCase248);
            	    c=cmd();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addStatement(c); 
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
        ASTExpression exp = null;

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
                       n = new ASTAssertOclExpression(exp.getStartToken(), input.LT(-1), valid, exp); 
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
        ASTExpression objExp = null;

        ASTExpression e = null;


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
               n = new ASTAssertPre(s, null, valid, objExp, opName); 
            }
            match(input,LPAREN,FOLLOW_LPAREN_in_assertionStatementPre527); if (state.failed) return n;
            // TestSuite.g:156:5: (e= expression ( COMMA e= expression )* )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==IDENT||(LA9_0>=STRING && LA9_0<=LPAREN)||(LA9_0>=PLUS && LA9_0<=MINUS)||(LA9_0>=INT && LA9_0<=HASH)||LA9_0==60||LA9_0==67||(LA9_0>=69 && LA9_0<=73)||(LA9_0>=77 && LA9_0<=88)) ) {
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
                       n.addArg(e); 
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
                    	       n.addArg(e); 
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

        ASTExpression nExp = null;


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
    // TestSuite.g:205:1: expression returns [ASTExpression n] : ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression ;
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
            	    match(input,60,FOLLOW_60_in_expression691); if (state.failed) return n;
            	    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_expression695); if (state.failed) return n;
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
            	            match(input,COLON,FOLLOW_COLON_in_expression699); if (state.failed) return n;
            	            pushFollow(FOLLOW_type_in_expression703);
            	            t=type();

            	            state._fsp--;
            	            if (state.failed) return n;

            	            }
            	            break;

            	    }

            	    match(input,EQUAL,FOLLOW_EQUAL_in_expression708); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_expression712);
            	    e1=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    match(input,61,FOLLOW_61_in_expression714); if (state.failed) return n;
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
            	    break loop13;
                }
            } while (true);

            pushFollow(FOLLOW_conditionalImpliesExpression_in_expression739);
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
            else if ( (LA25_0==IDENT||(LA25_0>=STRING && LA25_0<=LPAREN)||(LA25_0>=INT && LA25_0<=HASH)||(LA25_0>=69 && LA25_0<=73)||(LA25_0>=77 && LA25_0<=88)) ) {
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

                if ( ((LA27_0>=ARROW && LA27_0<=DOT)) ) {
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
    // TestSuite.g:421:1: primaryExpression returns [ASTExpression n] : (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? );
    public final ASTExpression primaryExpression() throws RecognitionException {
        ASTExpression n = null;

        Token id1=null;
        ASTExpression nLit = null;

        ASTExpression nPc = null;

        ASTExpression nExp = null;

        ASTExpression nIfExp = null;


        try {
            // TestSuite.g:422:1: (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? )
            int alt30=5;
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
                case COLON_EQUAL:
                case 48:
                case 49:
                case 51:
                case 52:
                case 53:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 74:
                case 75:
                case 76:
                case 90:
                case 91:
                case 93:
                case 94:
                case 96:
                case 98:
                case 99:
                case 100:
                case 101:
                case 104:
                case 105:
                    {
                    alt30=2;
                    }
                    break;
                case DOT:
                    {
                    int LA30_6 = input.LA(3);

                    if ( (LA30_6==68) ) {
                        alt30=5;
                    }
                    else if ( (LA30_6==IDENT||(LA30_6>=69 && LA30_6<=72)) ) {
                        alt30=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 30, 6, input);

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
            case 69:
            case 70:
            case 71:
            case 72:
                {
                alt30=2;
                }
                break;
            case LPAREN:
                {
                alt30=3;
                }
                break;
            case 73:
                {
                alt30=4;
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
                    // TestSuite.g:424:7: nPc= propertyCall[null, false]
                    {
                    pushFollow(FOLLOW_propertyCall_in_primaryExpression1690);
                    nPc=propertyCall(null, false);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nPc; 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:425:7: LPAREN nExp= expression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression1701); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_primaryExpression1705);
                    nExp=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression1707); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExp; 
                    }

                    }
                    break;
                case 4 :
                    // TestSuite.g:426:7: nIfExp= ifExpression
                    {
                    pushFollow(FOLLOW_ifExpression_in_primaryExpression1719);
                    nIfExp=ifExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nIfExp; 
                    }

                    }
                    break;
                case 5 :
                    // TestSuite.g:428:7: id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )?
                    {
                    id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_primaryExpression1736); if (state.failed) return n;
                    match(input,DOT,FOLLOW_DOT_in_primaryExpression1738); if (state.failed) return n;
                    match(input,68,FOLLOW_68_in_primaryExpression1740); if (state.failed) return n;
                    // TestSuite.g:428:36: ( LPAREN RPAREN )?
                    int alt28=2;
                    int LA28_0 = input.LA(1);

                    if ( (LA28_0==LPAREN) ) {
                        alt28=1;
                    }
                    switch (alt28) {
                        case 1 :
                            // TestSuite.g:428:38: LPAREN RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression1744); if (state.failed) return n;
                            match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression1746); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTAllInstancesExpression(id1); 
                    }
                    // TestSuite.g:430:7: ( AT 'pre' )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==AT) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // TestSuite.g:430:9: AT 'pre'
                            {
                            match(input,AT,FOLLOW_AT_in_primaryExpression1767); if (state.failed) return n;
                            match(input,58,FOLLOW_58_in_primaryExpression1769); if (state.failed) return n;
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
    // TestSuite.g:443:1: propertyCall[ASTExpression source, boolean followsArrow] returns [ASTExpression n] : ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] );
    public final ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExpQuery = null;

        ASTExpression nExpIterate = null;

        ASTOperationExpression nExpOperation = null;

        ASTTypeArgExpression nExpType = null;


        try {
            // TestSuite.g:444:1: ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] )
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
                    // TestSuite.g:448:7: {...}?{...}?nExpQuery= queryExpression[source]
                    {
                    if ( !(( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) ");
                    }
                    if ( !(( input.LA(2) == LPAREN )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " input.LA(2) == LPAREN ");
                    }
                    pushFollow(FOLLOW_queryExpression_in_propertyCall1842);
                    nExpQuery=queryExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpQuery; 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:451:7: nExpIterate= iterateExpression[source]
                    {
                    pushFollow(FOLLOW_iterateExpression_in_propertyCall1855);
                    nExpIterate=iterateExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpIterate; 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:452:7: nExpOperation= operationExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_operationExpression_in_propertyCall1868);
                    nExpOperation=operationExpression(source, followsArrow);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpOperation; 
                    }

                    }
                    break;
                case 4 :
                    // TestSuite.g:453:7: nExpType= typeExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_typeExpression_in_propertyCall1881);
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
    // TestSuite.g:462:1: queryExpression[ASTExpression range] returns [ASTExpression n] : op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN ;
    public final ASTExpression queryExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTElemVarsDeclaration decls = null;

        ASTExpression nExp = null;


        ASTElemVarsDeclaration decl = new ASTElemVarsDeclaration(); 
        try {
            // TestSuite.g:463:69: (op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN )
            // TestSuite.g:464:5: op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN
            {
            op=(Token)match(input,IDENT,FOLLOW_IDENT_in_queryExpression1916); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_queryExpression1923); if (state.failed) return n;
            // TestSuite.g:466:5: (decls= elemVarsDeclaration BAR )?
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
                    // TestSuite.g:466:7: decls= elemVarsDeclaration BAR
                    {
                    pushFollow(FOLLOW_elemVarsDeclaration_in_queryExpression1934);
                    decls=elemVarsDeclaration();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      decl = decls;
                    }
                    match(input,BAR,FOLLOW_BAR_in_queryExpression1938); if (state.failed) return n;

                    }
                    break;

            }

            pushFollow(FOLLOW_expression_in_queryExpression1949);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_queryExpression1955); if (state.failed) return n;
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
    // TestSuite.g:480:1: iterateExpression[ASTExpression range] returns [ASTExpression n] : i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN ;
    public final ASTExpression iterateExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTElemVarsDeclaration decls = null;

        ASTVariableInitialization init = null;

        ASTExpression nExp = null;


        try {
            // TestSuite.g:480:65: (i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN )
            // TestSuite.g:481:5: i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN
            {
            i=(Token)match(input,69,FOLLOW_69_in_iterateExpression1987); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_iterateExpression1993); if (state.failed) return n;
            pushFollow(FOLLOW_elemVarsDeclaration_in_iterateExpression2001);
            decls=elemVarsDeclaration();

            state._fsp--;
            if (state.failed) return n;
            match(input,SEMI,FOLLOW_SEMI_in_iterateExpression2003); if (state.failed) return n;
            pushFollow(FOLLOW_variableInitialization_in_iterateExpression2011);
            init=variableInitialization();

            state._fsp--;
            if (state.failed) return n;
            match(input,BAR,FOLLOW_BAR_in_iterateExpression2013); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_iterateExpression2021);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_iterateExpression2027); if (state.failed) return n;
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
    // TestSuite.g:502:1: operationExpression[ASTExpression source, boolean followsArrow] returns [ASTOperationExpression n] : name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? ;
    public final ASTOperationExpression operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTOperationExpression n = null;

        Token name=null;
        Token rolename=null;
        ASTExpression e = null;


        try {
            // TestSuite.g:504:1: (name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? )
            // TestSuite.g:505:5: name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression2071); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTOperationExpression(name, source, followsArrow); 
            }
            // TestSuite.g:508:5: ( LBRACK rolename= IDENT RBRACK )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==LBRACK) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // TestSuite.g:508:7: LBRACK rolename= IDENT RBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_operationExpression2087); if (state.failed) return n;
                    rolename=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression2091); if (state.failed) return n;
                    match(input,RBRACK,FOLLOW_RBRACK_in_operationExpression2093); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setExplicitRolename(rolename); 
                    }

                    }
                    break;

            }

            // TestSuite.g:510:5: ( AT 'pre' )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==AT) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // TestSuite.g:510:7: AT 'pre'
                    {
                    match(input,AT,FOLLOW_AT_in_operationExpression2106); if (state.failed) return n;
                    match(input,58,FOLLOW_58_in_operationExpression2108); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setIsPre(); 
                    }

                    }
                    break;

            }

            // TestSuite.g:511:5: ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==LPAREN) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // TestSuite.g:512:7: LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_operationExpression2129); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.hasParentheses(); 
                    }
                    // TestSuite.g:513:7: (e= expression ( COMMA e= expression )* )?
                    int alt36=2;
                    int LA36_0 = input.LA(1);

                    if ( (LA36_0==IDENT||(LA36_0>=STRING && LA36_0<=LPAREN)||(LA36_0>=PLUS && LA36_0<=MINUS)||(LA36_0>=INT && LA36_0<=HASH)||LA36_0==60||LA36_0==67||(LA36_0>=69 && LA36_0<=73)||(LA36_0>=77 && LA36_0<=88)) ) {
                        alt36=1;
                    }
                    switch (alt36) {
                        case 1 :
                            // TestSuite.g:514:7: e= expression ( COMMA e= expression )*
                            {
                            pushFollow(FOLLOW_expression_in_operationExpression2150);
                            e=expression();

                            state._fsp--;
                            if (state.failed) return n;
                            if ( state.backtracking==0 ) {
                               n.addArg(e); 
                            }
                            // TestSuite.g:515:7: ( COMMA e= expression )*
                            loop35:
                            do {
                                int alt35=2;
                                int LA35_0 = input.LA(1);

                                if ( (LA35_0==COMMA) ) {
                                    alt35=1;
                                }


                                switch (alt35) {
                            	case 1 :
                            	    // TestSuite.g:515:9: COMMA e= expression
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_operationExpression2162); if (state.failed) return n;
                            	    pushFollow(FOLLOW_expression_in_operationExpression2166);
                            	    e=expression();

                            	    state._fsp--;
                            	    if (state.failed) return n;
                            	    if ( state.backtracking==0 ) {
                            	       n.addArg(e); 
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

                    match(input,RPAREN,FOLLOW_RPAREN_in_operationExpression2186); if (state.failed) return n;

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
    // TestSuite.g:527:1: typeExpression[ASTExpression source, boolean followsArrow] returns [ASTTypeArgExpression n] : ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN ;
    public final ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTTypeArgExpression n = null;

        ASTType t = null;


         Token opToken = null; 
        try {
            // TestSuite.g:530:1: ( ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN )
            // TestSuite.g:531:2: ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN
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

            match(input,LPAREN,FOLLOW_LPAREN_in_typeExpression2245); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_typeExpression2249);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_typeExpression2251); if (state.failed) return n;
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
    // TestSuite.g:542:1: elemVarsDeclaration returns [ASTElemVarsDeclaration n] : idListRes= idList ( COLON t= type )? ;
    public final ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException {
        ASTElemVarsDeclaration n = null;

        List idListRes = null;

        ASTType t = null;


         List idList; 
        try {
            // TestSuite.g:544:1: (idListRes= idList ( COLON t= type )? )
            // TestSuite.g:545:5: idListRes= idList ( COLON t= type )?
            {
            pushFollow(FOLLOW_idList_in_elemVarsDeclaration2290);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            // TestSuite.g:546:5: ( COLON t= type )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==COLON) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // TestSuite.g:546:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_elemVarsDeclaration2298); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_elemVarsDeclaration2302);
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
    // TestSuite.g:555:1: variableInitialization returns [ASTVariableInitialization n] : name= IDENT COLON t= type EQUAL e= expression ;
    public final ASTVariableInitialization variableInitialization() throws RecognitionException {
        ASTVariableInitialization n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // TestSuite.g:556:1: (name= IDENT COLON t= type EQUAL e= expression )
            // TestSuite.g:557:5: name= IDENT COLON t= type EQUAL e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableInitialization2337); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableInitialization2339); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableInitialization2343);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EQUAL,FOLLOW_EQUAL_in_variableInitialization2345); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_variableInitialization2349);
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
    // TestSuite.g:566:1: ifExpression returns [ASTExpression n] : i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' ;
    public final ASTExpression ifExpression() throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTExpression cond = null;

        ASTExpression t = null;

        ASTExpression e = null;


        try {
            // TestSuite.g:567:1: (i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' )
            // TestSuite.g:568:5: i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif'
            {
            i=(Token)match(input,73,FOLLOW_73_in_ifExpression2381); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression2385);
            cond=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,74,FOLLOW_74_in_ifExpression2387); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression2391);
            t=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,75,FOLLOW_75_in_ifExpression2393); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression2397);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,76,FOLLOW_76_in_ifExpression2399); if (state.failed) return n;
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
    // TestSuite.g:588:1: literal returns [ASTExpression n] : (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral );
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
            // TestSuite.g:589:1: (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral )
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
                    // TestSuite.g:590:7: t= 'true'
                    {
                    t=(Token)match(input,77,FOLLOW_77_in_literal2438); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(true); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:591:7: f= 'false'
                    {
                    f=(Token)match(input,78,FOLLOW_78_in_literal2452); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(false); 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:592:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_literal2465); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTIntegerLiteral(i); 
                    }

                    }
                    break;
                case 4 :
                    // TestSuite.g:593:7: r= REAL
                    {
                    r=(Token)match(input,REAL,FOLLOW_REAL_in_literal2480); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTRealLiteral(r); 
                    }

                    }
                    break;
                case 5 :
                    // TestSuite.g:594:7: s= STRING
                    {
                    s=(Token)match(input,STRING,FOLLOW_STRING_in_literal2494); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTStringLiteral(s); 
                    }

                    }
                    break;
                case 6 :
                    // TestSuite.g:595:7: HASH enumLit= IDENT
                    {
                    match(input,HASH,FOLLOW_HASH_in_literal2504); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal2508); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumLit);  reportWarning(enumLit, "the usage of #enumerationLiteral is deprecated and will not be supported in the future, use 'Enumeration::Literal' instead");
                    }

                    }
                    break;
                case 7 :
                    // TestSuite.g:596:7: enumName= IDENT '::' enumLit= IDENT
                    {
                    enumName=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal2520); if (state.failed) return n;
                    match(input,COLON_COLON,FOLLOW_COLON_COLON_in_literal2522); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal2526); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumName, enumLit); 
                    }

                    }
                    break;
                case 8 :
                    // TestSuite.g:597:7: nColIt= collectionLiteral
                    {
                    pushFollow(FOLLOW_collectionLiteral_in_literal2538);
                    nColIt=collectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nColIt; 
                    }

                    }
                    break;
                case 9 :
                    // TestSuite.g:598:7: nEColIt= emptyCollectionLiteral
                    {
                    pushFollow(FOLLOW_emptyCollectionLiteral_in_literal2550);
                    nEColIt=emptyCollectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nEColIt; 
                    }

                    }
                    break;
                case 10 :
                    // TestSuite.g:599:7: nUndLit= undefinedLiteral
                    {
                    pushFollow(FOLLOW_undefinedLiteral_in_literal2562);
                    nUndLit=undefinedLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nUndLit; 
                    }

                    }
                    break;
                case 11 :
                    // TestSuite.g:600:7: nTupleLit= tupleLiteral
                    {
                    pushFollow(FOLLOW_tupleLiteral_in_literal2574);
                    nTupleLit=tupleLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nTupleLit; 
                    }

                    }
                    break;
                case 12 :
                    // TestSuite.g:601:7: nDateLit= dateLiteral
                    {
                    pushFollow(FOLLOW_dateLiteral_in_literal2586);
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
    // TestSuite.g:609:1: collectionLiteral returns [ASTCollectionLiteral n] : ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE ;
    public final ASTCollectionLiteral collectionLiteral() throws RecognitionException {
        ASTCollectionLiteral n = null;

        ASTCollectionItem ci = null;


         Token op = null; 
        try {
            // TestSuite.g:611:1: ( ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE )
            // TestSuite.g:612:5: ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE
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
            match(input,LBRACE,FOLLOW_LBRACE_in_collectionLiteral2653); if (state.failed) return n;
            // TestSuite.g:616:5: (ci= collectionItem ( COMMA ci= collectionItem )* )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==IDENT||(LA41_0>=STRING && LA41_0<=LPAREN)||(LA41_0>=PLUS && LA41_0<=MINUS)||(LA41_0>=INT && LA41_0<=HASH)||LA41_0==60||LA41_0==67||(LA41_0>=69 && LA41_0<=73)||(LA41_0>=77 && LA41_0<=88)) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // TestSuite.g:617:7: ci= collectionItem ( COMMA ci= collectionItem )*
                    {
                    pushFollow(FOLLOW_collectionItem_in_collectionLiteral2670);
                    ci=collectionItem();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addItem(ci); 
                    }
                    // TestSuite.g:618:7: ( COMMA ci= collectionItem )*
                    loop40:
                    do {
                        int alt40=2;
                        int LA40_0 = input.LA(1);

                        if ( (LA40_0==COMMA) ) {
                            alt40=1;
                        }


                        switch (alt40) {
                    	case 1 :
                    	    // TestSuite.g:618:9: COMMA ci= collectionItem
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_collectionLiteral2683); if (state.failed) return n;
                    	    pushFollow(FOLLOW_collectionItem_in_collectionLiteral2687);
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

            match(input,RBRACE,FOLLOW_RBRACE_in_collectionLiteral2706); if (state.failed) return n;

            }

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
    // TestSuite.g:627:1: collectionItem returns [ASTCollectionItem n] : e= expression ( DOTDOT e= expression )? ;
    public final ASTCollectionItem collectionItem() throws RecognitionException {
        ASTCollectionItem n = null;

        ASTExpression e = null;


         n = new ASTCollectionItem(); 
        try {
            // TestSuite.g:629:1: (e= expression ( DOTDOT e= expression )? )
            // TestSuite.g:630:5: e= expression ( DOTDOT e= expression )?
            {
            pushFollow(FOLLOW_expression_in_collectionItem2735);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setFirst(e); 
            }
            // TestSuite.g:631:5: ( DOTDOT e= expression )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==DOTDOT) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // TestSuite.g:631:7: DOTDOT e= expression
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_collectionItem2746); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_collectionItem2750);
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
    // TestSuite.g:641:1: emptyCollectionLiteral returns [ASTEmptyCollectionLiteral n] : 'oclEmpty' LPAREN t= collectionType RPAREN ;
    public final ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException {
        ASTEmptyCollectionLiteral n = null;

        ASTCollectionType t = null;


        try {
            // TestSuite.g:642:1: ( 'oclEmpty' LPAREN t= collectionType RPAREN )
            // TestSuite.g:643:5: 'oclEmpty' LPAREN t= collectionType RPAREN
            {
            match(input,83,FOLLOW_83_in_emptyCollectionLiteral2779); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_emptyCollectionLiteral2781); if (state.failed) return n;
            pushFollow(FOLLOW_collectionType_in_emptyCollectionLiteral2785);
            t=collectionType();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_emptyCollectionLiteral2787); if (state.failed) return n;
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
    // TestSuite.g:654:1: undefinedLiteral returns [ASTUndefinedLiteral n] : ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' );
    public final ASTUndefinedLiteral undefinedLiteral() throws RecognitionException {
        ASTUndefinedLiteral n = null;

        ASTType t = null;


        try {
            // TestSuite.g:655:1: ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' )
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
                    // TestSuite.g:656:5: 'oclUndefined' LPAREN t= type RPAREN
                    {
                    match(input,84,FOLLOW_84_in_undefinedLiteral2817); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_undefinedLiteral2819); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_undefinedLiteral2823);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_undefinedLiteral2825); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(t); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:659:5: 'Undefined'
                    {
                    match(input,85,FOLLOW_85_in_undefinedLiteral2839); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(); 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:662:5: 'null'
                    {
                    match(input,86,FOLLOW_86_in_undefinedLiteral2853); if (state.failed) return n;
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
    // TestSuite.g:671:1: tupleLiteral returns [ASTTupleLiteral n] : 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE ;
    public final ASTTupleLiteral tupleLiteral() throws RecognitionException {
        ASTTupleLiteral n = null;

        ASTTupleItem ti = null;


         List tiList = new ArrayList(); 
        try {
            // TestSuite.g:673:1: ( 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE )
            // TestSuite.g:674:5: 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE
            {
            match(input,87,FOLLOW_87_in_tupleLiteral2887); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_tupleLiteral2893); if (state.failed) return n;
            pushFollow(FOLLOW_tupleItem_in_tupleLiteral2901);
            ti=tupleItem();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tiList.add(ti); 
            }
            // TestSuite.g:677:5: ( COMMA ti= tupleItem )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==COMMA) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // TestSuite.g:677:7: COMMA ti= tupleItem
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleLiteral2912); if (state.failed) return n;
            	    pushFollow(FOLLOW_tupleItem_in_tupleLiteral2916);
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

            match(input,RBRACE,FOLLOW_RBRACE_in_tupleLiteral2927); if (state.failed) return n;
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
    // TestSuite.g:685:1: tupleItem returns [ASTTupleItem n] : name= IDENT ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) ;
    public final ASTTupleItem tupleItem() throws RecognitionException {
        ASTTupleItem n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // TestSuite.g:686:1: (name= IDENT ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) )
            // TestSuite.g:687:5: name= IDENT ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tupleItem2958); if (state.failed) return n;
            // TestSuite.g:688:5: ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
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
                    // TestSuite.g:691:7: ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression
                    {
                    match(input,COLON,FOLLOW_COLON_in_tupleItem2997); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_tupleItem3001);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,EQUAL,FOLLOW_EQUAL_in_tupleItem3003); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_tupleItem3007);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTTupleItem(name, t, e); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:694:7: ( COLON | EQUAL ) e= expression
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

                    pushFollow(FOLLOW_expression_in_tupleItem3039);
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
    // TestSuite.g:703:1: dateLiteral returns [ASTDateLiteral n] : 'Date' LBRACE v= STRING RBRACE ;
    public final ASTDateLiteral dateLiteral() throws RecognitionException {
        ASTDateLiteral n = null;

        Token v=null;

        try {
            // TestSuite.g:704:1: ( 'Date' LBRACE v= STRING RBRACE )
            // TestSuite.g:705:5: 'Date' LBRACE v= STRING RBRACE
            {
            match(input,88,FOLLOW_88_in_dateLiteral3084); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_dateLiteral3086); if (state.failed) return n;
            v=(Token)match(input,STRING,FOLLOW_STRING_in_dateLiteral3090); if (state.failed) return n;
            match(input,RBRACE,FOLLOW_RBRACE_in_dateLiteral3092); if (state.failed) return n;
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
    // TestSuite.g:715:1: type returns [ASTType n] : (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) ;
    public final ASTType type() throws RecognitionException {
        ASTType n = null;

        ASTSimpleType nTSimple = null;

        ASTCollectionType nTCollection = null;

        ASTTupleType nTTuple = null;


         Token tok = null; 
        try {
            // TestSuite.g:717:1: ( (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) )
            // TestSuite.g:718:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of type */ 
            }
            // TestSuite.g:719:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
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
                    // TestSuite.g:720:7: nTSimple= simpleType
                    {
                    pushFollow(FOLLOW_simpleType_in_type3142);
                    nTSimple=simpleType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTSimple; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:721:7: nTCollection= collectionType
                    {
                    pushFollow(FOLLOW_collectionType_in_type3154);
                    nTCollection=collectionType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTCollection; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:722:7: nTTuple= tupleType
                    {
                    pushFollow(FOLLOW_tupleType_in_type3166);
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
    // TestSuite.g:727:1: typeOnly returns [ASTType n] : nT= type EOF ;
    public final ASTType typeOnly() throws RecognitionException {
        ASTType n = null;

        ASTType nT = null;


        try {
            // TestSuite.g:728:1: (nT= type EOF )
            // TestSuite.g:729:5: nT= type EOF
            {
            pushFollow(FOLLOW_type_in_typeOnly3198);
            nT=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_typeOnly3200); if (state.failed) return n;
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
    // TestSuite.g:739:1: simpleType returns [ASTSimpleType n] : name= IDENT ;
    public final ASTSimpleType simpleType() throws RecognitionException {
        ASTSimpleType n = null;

        Token name=null;

        try {
            // TestSuite.g:740:1: (name= IDENT )
            // TestSuite.g:741:5: name= IDENT
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_simpleType3228); if (state.failed) return n;
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
    // TestSuite.g:749:1: collectionType returns [ASTCollectionType n] : ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN ;
    public final ASTCollectionType collectionType() throws RecognitionException {
        ASTCollectionType n = null;

        ASTType elemType = null;


         Token op = null; 
        try {
            // TestSuite.g:751:1: ( ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN )
            // TestSuite.g:752:5: ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN
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

            match(input,LPAREN,FOLLOW_LPAREN_in_collectionType3293); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_collectionType3297);
            elemType=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_collectionType3299); if (state.failed) return n;
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
    // TestSuite.g:762:1: tupleType returns [ASTTupleType n] : 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN ;
    public final ASTTupleType tupleType() throws RecognitionException {
        ASTTupleType n = null;

        ASTTuplePart tp = null;


         List tpList = new ArrayList(); 
        try {
            // TestSuite.g:764:1: ( 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN )
            // TestSuite.g:765:5: 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN
            {
            match(input,87,FOLLOW_87_in_tupleType3333); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_tupleType3335); if (state.failed) return n;
            pushFollow(FOLLOW_tuplePart_in_tupleType3344);
            tp=tuplePart();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tpList.add(tp); 
            }
            // TestSuite.g:767:5: ( COMMA tp= tuplePart )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==COMMA) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // TestSuite.g:767:7: COMMA tp= tuplePart
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleType3355); if (state.failed) return n;
            	    pushFollow(FOLLOW_tuplePart_in_tupleType3359);
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

            match(input,RPAREN,FOLLOW_RPAREN_in_tupleType3371); if (state.failed) return n;
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
    // TestSuite.g:776:1: tuplePart returns [ASTTuplePart n] : name= IDENT COLON t= type ;
    public final ASTTuplePart tuplePart() throws RecognitionException {
        ASTTuplePart n = null;

        Token name=null;
        ASTType t = null;


        try {
            // TestSuite.g:777:1: (name= IDENT COLON t= type )
            // TestSuite.g:778:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tuplePart3403); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_tuplePart3405); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_tuplePart3409);
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


    // $ANTLR start "cmdList"
    // TestSuite.g:786:1: cmdList returns [ASTCmdList cmdList] : c= cmd (c= cmd )* EOF ;
    public final ASTCmdList cmdList() throws RecognitionException {
        ASTCmdList cmdList = null;

        ASTCmd c = null;


         cmdList = new ASTCmdList(); 
        try {
            // TestSuite.g:788:1: (c= cmd (c= cmd )* EOF )
            // TestSuite.g:789:5: c= cmd (c= cmd )* EOF
            {
            pushFollow(FOLLOW_cmd_in_cmdList3445);
            c=cmd();

            state._fsp--;
            if (state.failed) return cmdList;
            if ( state.backtracking==0 ) {
               cmdList.add(c); 
            }
            // TestSuite.g:790:5: (c= cmd )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==60||(LA48_0>=90 && LA48_0<=91)||(LA48_0>=93 && LA48_0<=94)||LA48_0==96||(LA48_0>=98 && LA48_0<=101)||(LA48_0>=104 && LA48_0<=105)) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // TestSuite.g:790:7: c= cmd
            	    {
            	    pushFollow(FOLLOW_cmd_in_cmdList3457);
            	    c=cmd();

            	    state._fsp--;
            	    if (state.failed) return cmdList;
            	    if ( state.backtracking==0 ) {
            	       cmdList.add(c); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop48;
                }
            } while (true);

            match(input,EOF,FOLLOW_EOF_in_cmdList3468); if (state.failed) return cmdList;

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
    // TestSuite.g:797:1: cmd returns [ASTCmd n] : stmt= cmdStmt ( SEMI )? ;
    public final ASTCmd cmd() throws RecognitionException {
        ASTCmd n = null;

        ASTCmd stmt = null;


        try {
            // TestSuite.g:798:1: (stmt= cmdStmt ( SEMI )? )
            // TestSuite.g:799:5: stmt= cmdStmt ( SEMI )?
            {
            pushFollow(FOLLOW_cmdStmt_in_cmd3501);
            stmt=cmdStmt();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = stmt; 
            }
            // TestSuite.g:799:35: ( SEMI )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==SEMI) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // TestSuite.g:799:37: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_cmd3506); if (state.failed) return n;

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
    // TestSuite.g:815:1: cmdStmt returns [ASTCmd n] : (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= showCmd | nC= hideCmd | nC= cropCmd ) ;
    public final ASTCmd cmdStmt() throws RecognitionException {
        ASTCmd n = null;

        ASTCmd nC = null;


        try {
            // TestSuite.g:816:1: ( (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= showCmd | nC= hideCmd | nC= cropCmd ) )
            // TestSuite.g:817:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= showCmd | nC= hideCmd | nC= cropCmd )
            {
            // TestSuite.g:817:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= showCmd | nC= hideCmd | nC= cropCmd )
            int alt50=13;
            alt50 = dfa50.predict(input);
            switch (alt50) {
                case 1 :
                    // TestSuite.g:818:7: nC= createCmd
                    {
                    pushFollow(FOLLOW_createCmd_in_cmdStmt3537);
                    nC=createCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 2 :
                    // TestSuite.g:819:7: nC= createAssignCmd
                    {
                    pushFollow(FOLLOW_createAssignCmd_in_cmdStmt3549);
                    nC=createAssignCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 3 :
                    // TestSuite.g:820:7: nC= createInsertCmd
                    {
                    pushFollow(FOLLOW_createInsertCmd_in_cmdStmt3562);
                    nC=createInsertCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 4 :
                    // TestSuite.g:821:7: nC= destroyCmd
                    {
                    pushFollow(FOLLOW_destroyCmd_in_cmdStmt3574);
                    nC=destroyCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 5 :
                    // TestSuite.g:822:7: nC= insertCmd
                    {
                    pushFollow(FOLLOW_insertCmd_in_cmdStmt3586);
                    nC=insertCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 6 :
                    // TestSuite.g:823:7: nC= deleteCmd
                    {
                    pushFollow(FOLLOW_deleteCmd_in_cmdStmt3598);
                    nC=deleteCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 7 :
                    // TestSuite.g:824:7: nC= setCmd
                    {
                    pushFollow(FOLLOW_setCmd_in_cmdStmt3610);
                    nC=setCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 8 :
                    // TestSuite.g:825:7: nC= opEnterCmd
                    {
                    pushFollow(FOLLOW_opEnterCmd_in_cmdStmt3622);
                    nC=opEnterCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 9 :
                    // TestSuite.g:826:7: nC= opExitCmd
                    {
                    pushFollow(FOLLOW_opExitCmd_in_cmdStmt3634);
                    nC=opExitCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 10 :
                    // TestSuite.g:827:7: nC= letCmd
                    {
                    pushFollow(FOLLOW_letCmd_in_cmdStmt3646);
                    nC=letCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 11 :
                    // TestSuite.g:828:7: nC= showCmd
                    {
                    pushFollow(FOLLOW_showCmd_in_cmdStmt3658);
                    nC=showCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 12 :
                    // TestSuite.g:829:7: nC= hideCmd
                    {
                    pushFollow(FOLLOW_hideCmd_in_cmdStmt3670);
                    nC=hideCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 13 :
                    // TestSuite.g:830:7: nC= cropCmd
                    {
                    pushFollow(FOLLOW_cropCmd_in_cmdStmt3682);
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
    // TestSuite.g:840:1: createCmd returns [ASTCmd n] : s= 'create' nIdList= idList COLON t= simpleType ;
    public final ASTCmd createCmd() throws RecognitionException {
        ASTCmd n = null;

        Token s=null;
        List nIdList = null;

        ASTSimpleType t = null;


        try {
            // TestSuite.g:841:1: (s= 'create' nIdList= idList COLON t= simpleType )
            // TestSuite.g:842:5: s= 'create' nIdList= idList COLON t= simpleType
            {
            s=(Token)match(input,90,FOLLOW_90_in_createCmd3713); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createCmd3717);
            nIdList=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_createCmd3724); if (state.failed) return n;
            pushFollow(FOLLOW_simpleType_in_createCmd3728);
            t=simpleType();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTCreateCmd(s, nIdList, t); 
            }

            }

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
    // TestSuite.g:852:1: createAssignCmd returns [ASTCmd n] : s= 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType ;
    public final ASTCmd createAssignCmd() throws RecognitionException {
        ASTCmd n = null;

        Token s=null;
        List nIdList = null;

        ASTSimpleType t = null;


        try {
            // TestSuite.g:853:1: (s= 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType )
            // TestSuite.g:854:5: s= 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType
            {
            s=(Token)match(input,91,FOLLOW_91_in_createAssignCmd3759); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createAssignCmd3763);
            nIdList=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_createAssignCmd3765); if (state.failed) return n;
            match(input,90,FOLLOW_90_in_createAssignCmd3767); if (state.failed) return n;
            pushFollow(FOLLOW_simpleType_in_createAssignCmd3771);
            t=simpleType();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTCreateAssignCmd(s, nIdList, t); 
            }

            }

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
    // TestSuite.g:862:1: createInsertCmd returns [ASTCmd n] : s= 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN ;
    public final ASTCmd createInsertCmd() throws RecognitionException {
        ASTCmd n = null;

        Token s=null;
        Token id=null;
        Token idAssoc=null;
        List idListInsert = null;


        try {
            // TestSuite.g:863:1: (s= 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN )
            // TestSuite.g:864:5: s= 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN
            {
            s=(Token)match(input,90,FOLLOW_90_in_createInsertCmd3793); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_createInsertCmd3797); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_createInsertCmd3799); if (state.failed) return n;
            idAssoc=(Token)match(input,IDENT,FOLLOW_IDENT_in_createInsertCmd3803); if (state.failed) return n;
            match(input,92,FOLLOW_92_in_createInsertCmd3809); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_createInsertCmd3811); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createInsertCmd3815);
            idListInsert=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_createInsertCmd3817); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTCreateInsertCmd( s, id, idAssoc, idListInsert); 
            }

            }

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
    // TestSuite.g:875:1: destroyCmd returns [ASTCmd n] : s= 'destroy' e= expression ( COMMA e= expression )* ;
    public final ASTCmd destroyCmd() throws RecognitionException {
        ASTCmd n = null;

        Token s=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // TestSuite.g:877:1: (s= 'destroy' e= expression ( COMMA e= expression )* )
            // TestSuite.g:878:6: s= 'destroy' e= expression ( COMMA e= expression )*
            {
            s=(Token)match(input,93,FOLLOW_93_in_destroyCmd3855); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_destroyCmd3859);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // TestSuite.g:879:16: ( COMMA e= expression )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==COMMA) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // TestSuite.g:879:18: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_destroyCmd3881); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_destroyCmd3885);
            	    e=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       exprList.add(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop51;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               n = new ASTDestroyCmd(s, exprList); 
            }

            }

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
    // TestSuite.g:889:1: insertCmd returns [ASTCmd n] : s= 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT ;
    public final ASTCmd insertCmd() throws RecognitionException {
        ASTCmd n = null;

        Token s=null;
        Token id=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // TestSuite.g:891:1: (s= 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT )
            // TestSuite.g:892:5: s= 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT
            {
            s=(Token)match(input,94,FOLLOW_94_in_insertCmd3926); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_insertCmd3928); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_insertCmd3937);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_insertCmd3941); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_insertCmd3949);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // TestSuite.g:894:42: ( COMMA e= expression )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==COMMA) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // TestSuite.g:894:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_insertCmd3955); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_insertCmd3959);
            	    e=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       exprList.add(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop52;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_insertCmd3971); if (state.failed) return n;
            match(input,95,FOLLOW_95_in_insertCmd3973); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_insertCmd3977); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTInsertCmd(s, exprList, id); 
            }

            }

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
    // TestSuite.g:905:1: deleteCmd returns [ASTCmd n] : s= 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT ;
    public final ASTCmd deleteCmd() throws RecognitionException {
        ASTCmd n = null;

        Token s=null;
        Token id=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // TestSuite.g:907:1: (s= 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT )
            // TestSuite.g:908:5: s= 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT
            {
            s=(Token)match(input,96,FOLLOW_96_in_deleteCmd4014); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_deleteCmd4016); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_deleteCmd4024);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_deleteCmd4028); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_deleteCmd4036);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // TestSuite.g:910:42: ( COMMA e= expression )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==COMMA) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // TestSuite.g:910:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_deleteCmd4042); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_deleteCmd4046);
            	    e=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       exprList.add(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop53;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_deleteCmd4057); if (state.failed) return n;
            match(input,97,FOLLOW_97_in_deleteCmd4059); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_deleteCmd4063); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTDeleteCmd(s, exprList, id); 
            }

            }

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
    // TestSuite.g:924:1: setCmd returns [ASTCmd n] : s= 'set' e1= expression COLON_EQUAL e2= expression ;
    public final ASTCmd setCmd() throws RecognitionException {
        ASTCmd n = null;

        Token s=null;
        ASTExpression e1 = null;

        ASTExpression e2 = null;


        try {
            // TestSuite.g:925:1: (s= 'set' e1= expression COLON_EQUAL e2= expression )
            // TestSuite.g:926:5: s= 'set' e1= expression COLON_EQUAL e2= expression
            {
            s=(Token)match(input,98,FOLLOW_98_in_setCmd4095); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_setCmd4099);
            e1=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_setCmd4101); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_setCmd4105);
            e2=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTSetCmd(s, e1, e2); 
            }

            }

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
    // TestSuite.g:938:1: opEnterCmd returns [ASTCmd n] : s= 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN ;
    public final ASTCmd opEnterCmd() throws RecognitionException {
        ASTCmd n = null;

        Token s=null;
        Token id=null;
        ASTExpression e = null;


        ASTOpEnterCmd nOpEnter = null;
        try {
            // TestSuite.g:940:1: (s= 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )
            // TestSuite.g:941:5: s= 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
            {
            s=(Token)match(input,99,FOLLOW_99_in_opEnterCmd4141); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_opEnterCmd4150);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_opEnterCmd4154); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               nOpEnter = new ASTOpEnterCmd(s, e, id); n = nOpEnter;
            }
            match(input,LPAREN,FOLLOW_LPAREN_in_opEnterCmd4162); if (state.failed) return n;
            // TestSuite.g:944:5: (e= expression ( COMMA e= expression )* )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==IDENT||(LA55_0>=STRING && LA55_0<=LPAREN)||(LA55_0>=PLUS && LA55_0<=MINUS)||(LA55_0>=INT && LA55_0<=HASH)||LA55_0==60||LA55_0==67||(LA55_0>=69 && LA55_0<=73)||(LA55_0>=77 && LA55_0<=88)) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // TestSuite.g:944:7: e= expression ( COMMA e= expression )*
                    {
                    pushFollow(FOLLOW_expression_in_opEnterCmd4172);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       nOpEnter.addArg(e); 
                    }
                    // TestSuite.g:944:47: ( COMMA e= expression )*
                    loop54:
                    do {
                        int alt54=2;
                        int LA54_0 = input.LA(1);

                        if ( (LA54_0==COMMA) ) {
                            alt54=1;
                        }


                        switch (alt54) {
                    	case 1 :
                    	    // TestSuite.g:944:49: COMMA e= expression
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_opEnterCmd4178); if (state.failed) return n;
                    	    pushFollow(FOLLOW_expression_in_opEnterCmd4182);
                    	    e=expression();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       nOpEnter.addArg(e); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop54;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_opEnterCmd4196); if (state.failed) return n;

            }

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
    // TestSuite.g:954:1: opExitCmd returns [ASTCmd n] : s= 'opexit' ( ( expression )=>e= expression | ) ;
    public final ASTCmd opExitCmd() throws RecognitionException {
        ASTCmd n = null;

        Token s=null;
        ASTExpression e = null;


        try {
            // TestSuite.g:955:1: (s= 'opexit' ( ( expression )=>e= expression | ) )
            // TestSuite.g:956:5: s= 'opexit' ( ( expression )=>e= expression | )
            {
            s=(Token)match(input,100,FOLLOW_100_in_opExitCmd4222); if (state.failed) return n;
            // TestSuite.g:956:16: ( ( expression )=>e= expression | )
            int alt56=2;
            alt56 = dfa56.predict(input);
            switch (alt56) {
                case 1 :
                    // TestSuite.g:956:17: ( expression )=>e= expression
                    {
                    pushFollow(FOLLOW_expression_in_opExitCmd4232);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 2 :
                    // TestSuite.g:956:47: 
                    {
                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTOpExitCmd(s, e); 
            }

            }

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
    // TestSuite.g:965:1: letCmd returns [ASTCmd n] : s= 'let' name= IDENT ( COLON t= type )? EQUAL e= expression ;
    public final ASTCmd letCmd() throws RecognitionException {
        ASTCmd n = null;

        Token s=null;
        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // TestSuite.g:966:1: (s= 'let' name= IDENT ( COLON t= type )? EQUAL e= expression )
            // TestSuite.g:967:5: s= 'let' name= IDENT ( COLON t= type )? EQUAL e= expression
            {
            s=(Token)match(input,60,FOLLOW_60_in_letCmd4267); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_letCmd4271); if (state.failed) return n;
            // TestSuite.g:967:24: ( COLON t= type )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==COLON) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // TestSuite.g:967:26: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_letCmd4275); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_letCmd4279);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            match(input,EQUAL,FOLLOW_EQUAL_in_letCmd4284); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_letCmd4288);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTLetCmd(s, name, t, e); 
            }

            }

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
    // TestSuite.g:974:1: hideCmd returns [ASTCmd n] : s= 'hide' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) ;
    public final ASTCmd hideCmd() throws RecognitionException {
        ASTCmd n = null;

        Token s=null;
        Token classname=null;
        Token ass=null;
        List objList = null;


        try {
            // TestSuite.g:975:1: (s= 'hide' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) )
            // TestSuite.g:976:2: s= 'hide' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            {
            s=(Token)match(input,101,FOLLOW_101_in_hideCmd4317); if (state.failed) return n;
            // TestSuite.g:976:11: ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            int alt59=3;
            switch ( input.LA(1) ) {
            case 102:
                {
                alt59=1;
                }
                break;
            case IDENT:
                {
                alt59=2;
                }
                break;
            case 103:
                {
                alt59=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;
            }

            switch (alt59) {
                case 1 :
                    // TestSuite.g:977:6: 'all'
                    {
                    match(input,102,FOLLOW_102_in_hideCmd4326); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideAllCmd(s, Mode.HIDE); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:978:6: objList= idList ( COLON classname= IDENT )?
                    {
                    pushFollow(FOLLOW_idList_in_hideCmd4339);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    // TestSuite.g:978:23: ( COLON classname= IDENT )?
                    int alt58=2;
                    int LA58_0 = input.LA(1);

                    if ( (LA58_0==COLON) ) {
                        alt58=1;
                    }
                    switch (alt58) {
                        case 1 :
                            // TestSuite.g:978:24: COLON classname= IDENT
                            {
                            match(input,COLON,FOLLOW_COLON_in_hideCmd4342); if (state.failed) return n;
                            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_hideCmd4348); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideCropObjectsCmd(s, Mode.HIDE, objList, classname); 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:979:6: 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT
                    {
                    match(input,103,FOLLOW_103_in_hideCmd4359); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_hideCmd4361); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_hideCmd4367);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_hideCmd4369); if (state.failed) return n;
                    match(input,97,FOLLOW_97_in_hideCmd4371); if (state.failed) return n;
                    ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_hideCmd4375); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideCropLinkObjectsCmd(s, Mode.HIDE, ass, objList); 
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
    // TestSuite.g:985:1: showCmd returns [ASTCmd n] : s= 'show' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) ;
    public final ASTCmd showCmd() throws RecognitionException {
        ASTCmd n = null;

        Token s=null;
        Token classname=null;
        Token ass=null;
        List objList = null;


        try {
            // TestSuite.g:986:1: (s= 'show' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) )
            // TestSuite.g:987:2: s= 'show' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            {
            s=(Token)match(input,104,FOLLOW_104_in_showCmd4402); if (state.failed) return n;
            // TestSuite.g:987:11: ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            int alt61=3;
            switch ( input.LA(1) ) {
            case 102:
                {
                alt61=1;
                }
                break;
            case IDENT:
                {
                alt61=2;
                }
                break;
            case 103:
                {
                alt61=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 61, 0, input);

                throw nvae;
            }

            switch (alt61) {
                case 1 :
                    // TestSuite.g:988:6: 'all'
                    {
                    match(input,102,FOLLOW_102_in_showCmd4411); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideAllCmd(s, Mode.SHOW); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:989:6: objList= idList ( COLON classname= IDENT )?
                    {
                    pushFollow(FOLLOW_idList_in_showCmd4424);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    // TestSuite.g:989:23: ( COLON classname= IDENT )?
                    int alt60=2;
                    int LA60_0 = input.LA(1);

                    if ( (LA60_0==COLON) ) {
                        alt60=1;
                    }
                    switch (alt60) {
                        case 1 :
                            // TestSuite.g:989:24: COLON classname= IDENT
                            {
                            match(input,COLON,FOLLOW_COLON_in_showCmd4427); if (state.failed) return n;
                            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_showCmd4433); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideCropObjectsCmd(s, Mode.SHOW, objList, classname); 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:990:6: 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT
                    {
                    match(input,103,FOLLOW_103_in_showCmd4444); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_showCmd4446); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_showCmd4452);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_showCmd4454); if (state.failed) return n;
                    match(input,97,FOLLOW_97_in_showCmd4456); if (state.failed) return n;
                    ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_showCmd4460); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideCropLinkObjectsCmd(s, Mode.SHOW, ass, objList); 
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
    // TestSuite.g:996:1: cropCmd returns [ASTCmd n] : s= 'crop' ( | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) ;
    public final ASTCmd cropCmd() throws RecognitionException {
        ASTCmd n = null;

        Token s=null;
        Token classname=null;
        Token ass=null;
        List objList = null;


        try {
            // TestSuite.g:997:1: (s= 'crop' ( | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) )
            // TestSuite.g:998:2: s= 'crop' ( | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            {
            s=(Token)match(input,105,FOLLOW_105_in_cropCmd4487); if (state.failed) return n;
            // TestSuite.g:998:11: ( | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            int alt63=3;
            switch ( input.LA(1) ) {
            case EOF:
            case SEMI:
            case 48:
            case 49:
            case 51:
            case 52:
            case 53:
            case 60:
            case 90:
            case 91:
            case 93:
            case 94:
            case 96:
            case 98:
            case 99:
            case 100:
            case 101:
            case 104:
            case 105:
                {
                alt63=1;
                }
                break;
            case IDENT:
                {
                alt63=2;
                }
                break;
            case 103:
                {
                alt63=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;
            }

            switch (alt63) {
                case 1 :
                    // TestSuite.g:999:4: 
                    {
                    }
                    break;
                case 2 :
                    // TestSuite.g:999:6: objList= idList ( COLON classname= IDENT )?
                    {
                    pushFollow(FOLLOW_idList_in_cropCmd4500);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    // TestSuite.g:999:23: ( COLON classname= IDENT )?
                    int alt62=2;
                    int LA62_0 = input.LA(1);

                    if ( (LA62_0==COLON) ) {
                        alt62=1;
                    }
                    switch (alt62) {
                        case 1 :
                            // TestSuite.g:999:24: COLON classname= IDENT
                            {
                            match(input,COLON,FOLLOW_COLON_in_cropCmd4503); if (state.failed) return n;
                            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_cropCmd4509); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideCropObjectsCmd(s, Mode.CROP, objList, classname); 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:1000:6: 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT
                    {
                    match(input,103,FOLLOW_103_in_cropCmd4520); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_cropCmd4522); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_cropCmd4528);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_cropCmd4530); if (state.failed) return n;
                    match(input,97,FOLLOW_97_in_cropCmd4532); if (state.failed) return n;
                    ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_cropCmd4536); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideCropLinkObjectsCmd(s, Mode.CROP, ass, objList); 
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

    // $ANTLR start synpred1_TestSuite
    public final void synpred1_TestSuite_fragment() throws RecognitionException {   
        // TestSuite.g:691:7: ( COLON IDENT EQUAL )
        // TestSuite.g:691:8: COLON IDENT EQUAL
        {
        match(input,COLON,FOLLOW_COLON_in_synpred1_TestSuite2988); if (state.failed) return ;
        match(input,IDENT,FOLLOW_IDENT_in_synpred1_TestSuite2990); if (state.failed) return ;
        match(input,EQUAL,FOLLOW_EQUAL_in_synpred1_TestSuite2992); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_TestSuite

    // $ANTLR start synpred2_TestSuite
    public final void synpred2_TestSuite_fragment() throws RecognitionException {   
        // TestSuite.g:956:17: ( expression )
        // TestSuite.g:956:18: expression
        {
        pushFollow(FOLLOW_expression_in_synpred2_TestSuite4226);
        expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_TestSuite

    // Delegated rules

    public final boolean synpred2_TestSuite() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_TestSuite_fragment(); // can never throw exception
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


    protected DFA50 dfa50 = new DFA50(this);
    protected DFA56 dfa56 = new DFA56(this);
    static final String DFA50_eotS =
        "\22\uffff";
    static final String DFA50_eofS =
        "\20\uffff\1\17\1\uffff";
    static final String DFA50_minS =
        "\1\74\1\4\13\uffff\1\6\1\4\1\uffff\1\31\1\uffff";
    static final String DFA50_maxS =
        "\1\151\1\4\13\uffff\1\12\1\4\1\uffff\1\151\1\uffff";
    static final String DFA50_acceptS =
        "\2\uffff\1\2\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\2\uffff"+
        "\1\1\1\uffff\1\3";
    static final String DFA50_specialS =
        "\22\uffff}>";
    static final String[] DFA50_transitionS = {
            "\1\11\35\uffff\1\1\1\2\1\uffff\1\3\1\4\1\uffff\1\5\1\uffff"+
            "\1\6\1\7\1\10\1\13\2\uffff\1\12\1\14",
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
            "\1\17\3\uffff\1\16",
            "\1\20",
            "",
            "\1\17\26\uffff\2\17\1\uffff\3\17\6\uffff\1\17\35\uffff\2\17"+
            "\1\21\2\17\1\uffff\1\17\1\uffff\4\17\2\uffff\2\17",
            ""
    };

    static final short[] DFA50_eot = DFA.unpackEncodedString(DFA50_eotS);
    static final short[] DFA50_eof = DFA.unpackEncodedString(DFA50_eofS);
    static final char[] DFA50_min = DFA.unpackEncodedStringToUnsignedChars(DFA50_minS);
    static final char[] DFA50_max = DFA.unpackEncodedStringToUnsignedChars(DFA50_maxS);
    static final short[] DFA50_accept = DFA.unpackEncodedString(DFA50_acceptS);
    static final short[] DFA50_special = DFA.unpackEncodedString(DFA50_specialS);
    static final short[][] DFA50_transition;

    static {
        int numStates = DFA50_transitionS.length;
        DFA50_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA50_transition[i] = DFA.unpackEncodedString(DFA50_transitionS[i]);
        }
    }

    class DFA50 extends DFA {

        public DFA50(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 50;
            this.eot = DFA50_eot;
            this.eof = DFA50_eof;
            this.min = DFA50_min;
            this.max = DFA50_max;
            this.accept = DFA50_accept;
            this.special = DFA50_special;
            this.transition = DFA50_transition;
        }
        public String getDescription() {
            return "817:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= showCmd | nC= hideCmd | nC= cropCmd )";
        }
    }
    static final String DFA56_eotS =
        "\47\uffff";
    static final String DFA56_eofS =
        "\1\25\46\uffff";
    static final String DFA56_minS =
        "\1\4\1\0\45\uffff";
    static final String DFA56_maxS =
        "\1\151\1\0\45\uffff";
    static final String DFA56_acceptS =
        "\2\uffff\23\1\1\2\21\uffff";
    static final String DFA56_specialS =
        "\1\0\1\1\45\uffff}>";
    static final String[] DFA56_transitionS = {
            "\1\11\2\uffff\1\7\1\23\10\uffff\2\2\6\uffff\1\25\2\uffff\1"+
            "\5\1\6\1\10\21\uffff\2\25\1\uffff\3\25\6\uffff\1\1\6\uffff\1"+
            "\2\1\uffff\1\21\3\22\1\24\3\uffff\1\3\1\4\4\12\1\13\1\14\1\15"+
            "\1\16\1\17\1\20\1\uffff\2\25\1\uffff\2\25\1\uffff\1\25\1\uffff"+
            "\4\25\2\uffff\2\25",
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
            "",
            "",
            "",
            "",
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
            return "956:16: ( ( expression )=>e= expression | )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA56_0 = input.LA(1);

                         
                        int index56_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA56_0==60) ) {s = 1;}

                        else if ( ((LA56_0>=PLUS && LA56_0<=MINUS)||LA56_0==67) && (synpred2_TestSuite())) {s = 2;}

                        else if ( (LA56_0==77) && (synpred2_TestSuite())) {s = 3;}

                        else if ( (LA56_0==78) && (synpred2_TestSuite())) {s = 4;}

                        else if ( (LA56_0==INT) && (synpred2_TestSuite())) {s = 5;}

                        else if ( (LA56_0==REAL) && (synpred2_TestSuite())) {s = 6;}

                        else if ( (LA56_0==STRING) && (synpred2_TestSuite())) {s = 7;}

                        else if ( (LA56_0==HASH) && (synpred2_TestSuite())) {s = 8;}

                        else if ( (LA56_0==IDENT) && (synpred2_TestSuite())) {s = 9;}

                        else if ( ((LA56_0>=79 && LA56_0<=82)) && (synpred2_TestSuite())) {s = 10;}

                        else if ( (LA56_0==83) && (synpred2_TestSuite())) {s = 11;}

                        else if ( (LA56_0==84) && (synpred2_TestSuite())) {s = 12;}

                        else if ( (LA56_0==85) && (synpred2_TestSuite())) {s = 13;}

                        else if ( (LA56_0==86) && (synpred2_TestSuite())) {s = 14;}

                        else if ( (LA56_0==87) && (synpred2_TestSuite())) {s = 15;}

                        else if ( (LA56_0==88) && (synpred2_TestSuite())) {s = 16;}

                        else if ( (LA56_0==69) && (synpred2_TestSuite())) {s = 17;}

                        else if ( ((LA56_0>=70 && LA56_0<=72)) && (synpred2_TestSuite())) {s = 18;}

                        else if ( (LA56_0==LPAREN) && (synpred2_TestSuite())) {s = 19;}

                        else if ( (LA56_0==73) && (synpred2_TestSuite())) {s = 20;}

                        else if ( (LA56_0==EOF||LA56_0==SEMI||(LA56_0>=48 && LA56_0<=49)||(LA56_0>=51 && LA56_0<=53)||(LA56_0>=90 && LA56_0<=91)||(LA56_0>=93 && LA56_0<=94)||LA56_0==96||(LA56_0>=98 && LA56_0<=101)||(LA56_0>=104 && LA56_0<=105)) ) {s = 21;}

                         
                        input.seek(index56_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA56_1 = input.LA(1);

                         
                        int index56_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_TestSuite()) ) {s = 20;}

                        else if ( (true) ) {s = 21;}

                         
                        input.seek(index56_1);
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
    public static final BitSet FOLLOW_48_in_testSuite112 = new BitSet(new long[]{0x1000000000000000L,0x0000033D6C000000L});
    public static final BitSet FOLLOW_cmd_in_testSuite118 = new BitSet(new long[]{0x0003000000000000L});
    public static final BitSet FOLLOW_49_in_testSuite124 = new BitSet(new long[]{0x0004800000000000L});
    public static final BitSet FOLLOW_testCases_in_testSuite145 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_testSuite154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_filename172 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_DOT_in_filename174 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_filename178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_testCase_in_testCases205 = new BitSet(new long[]{0x0004800000000002L});
    public static final BitSet FOLLOW_50_in_testCase224 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_testCase228 = new BitSet(new long[]{0x003B000000000000L});
    public static final BitSet FOLLOW_48_in_testCase242 = new BitSet(new long[]{0x1000000000000000L,0x0000033D6C000000L});
    public static final BitSet FOLLOW_cmd_in_testCase248 = new BitSet(new long[]{0x003B000000000000L});
    public static final BitSet FOLLOW_assertStatement_in_testCase267 = new BitSet(new long[]{0x003B000000000000L});
    public static final BitSet FOLLOW_51_in_testCase285 = new BitSet(new long[]{0x003B000000000000L});
    public static final BitSet FOLLOW_52_in_testCase303 = new BitSet(new long[]{0x003B000000000000L});
    public static final BitSet FOLLOW_49_in_testCase315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_assertStatement336 = new BitSet(new long[]{0x00C0000000000000L});
    public static final BitSet FOLLOW_54_in_assertStatement341 = new BitSet(new long[]{0x1F00000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_55_in_assertStatement347 = new BitSet(new long[]{0x1F00000070060190L,0x0000000001FFE3E8L});
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
    public static final BitSet FOLLOW_58_in_assertionStatementPre513 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_assertionStatementPre517 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_assertionStatementPre521 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_assertionStatementPre527 = new BitSet(new long[]{0x1000000070060390L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_assertionStatementPre538 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_COMMA_in_assertionStatementPre544 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
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
    public static final BitSet FOLLOW_EQUAL_in_expression708 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_expression712 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_expression714 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
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
    public static final BitSet FOLLOW_62_in_conditionalImpliesExpression954 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression958 = new BitSet(new long[]{0x4000000000000002L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression1003 = new BitSet(new long[]{0x8000000000000002L});
    public static final BitSet FOLLOW_63_in_conditionalOrExpression1016 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression1020 = new BitSet(new long[]{0x8000000000000002L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression1064 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_conditionalXOrExpression1077 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression1081 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression1125 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_conditionalAndExpression1138 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression1142 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000002L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression1190 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_set_in_equalityExpression1209 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression1219 = new BitSet(new long[]{0x0000000000001802L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression1268 = new BitSet(new long[]{0x000000000001E002L});
    public static final BitSet FOLLOW_set_in_relationalExpression1286 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression1304 = new BitSet(new long[]{0x000000000001E002L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1354 = new BitSet(new long[]{0x0000000000060002L});
    public static final BitSet FOLLOW_set_in_additiveExpression1372 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1382 = new BitSet(new long[]{0x0000000000060002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1432 = new BitSet(new long[]{0x0000000000180002L,0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression1450 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1464 = new BitSet(new long[]{0x0000000000180002L,0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_unaryExpression1526 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression1550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpression_in_unaryExpression1570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_postfixExpression1603 = new BitSet(new long[]{0x0000000000600002L});
    public static final BitSet FOLLOW_ARROW_in_postfixExpression1621 = new BitSet(new long[]{0x0000000000000010L,0x00000000000001E0L});
    public static final BitSet FOLLOW_DOT_in_postfixExpression1627 = new BitSet(new long[]{0x0000000000000010L,0x00000000000001E0L});
    public static final BitSet FOLLOW_propertyCall_in_postfixExpression1638 = new BitSet(new long[]{0x0000000000600002L});
    public static final BitSet FOLLOW_literal_in_primaryExpression1678 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyCall_in_primaryExpression1690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression1701 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_primaryExpression1705 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression1707 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifExpression_in_primaryExpression1719 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_primaryExpression1736 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_DOT_in_primaryExpression1738 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_primaryExpression1740 = new BitSet(new long[]{0x0000000000800102L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression1744 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression1746 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_AT_in_primaryExpression1767 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_primaryExpression1769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_queryExpression_in_propertyCall1842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterateExpression_in_propertyCall1855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationExpression_in_propertyCall1868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeExpression_in_propertyCall1881 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_queryExpression1916 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_queryExpression1923 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_queryExpression1934 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_BAR_in_queryExpression1938 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_queryExpression1949 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_queryExpression1955 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_iterateExpression1987 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_iterateExpression1993 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_iterateExpression2001 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_SEMI_in_iterateExpression2003 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableInitialization_in_iterateExpression2011 = new BitSet(new long[]{0x0000000001000000L});
    public static final BitSet FOLLOW_BAR_in_iterateExpression2013 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_iterateExpression2021 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_iterateExpression2027 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression2071 = new BitSet(new long[]{0x0000000004800102L});
    public static final BitSet FOLLOW_LBRACK_in_operationExpression2087 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression2091 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_RBRACK_in_operationExpression2093 = new BitSet(new long[]{0x0000000000800102L});
    public static final BitSet FOLLOW_AT_in_operationExpression2106 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_operationExpression2108 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_LPAREN_in_operationExpression2129 = new BitSet(new long[]{0x1000000070060390L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_operationExpression2150 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_COMMA_in_operationExpression2162 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_operationExpression2166 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_RPAREN_in_operationExpression2186 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_typeExpression2229 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_typeExpression2245 = new BitSet(new long[]{0x0000000000000010L,0x0000000002878000L});
    public static final BitSet FOLLOW_type_in_typeExpression2249 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_typeExpression2251 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_elemVarsDeclaration2290 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COLON_in_elemVarsDeclaration2298 = new BitSet(new long[]{0x0000000000000010L,0x0000000002878000L});
    public static final BitSet FOLLOW_type_in_elemVarsDeclaration2302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableInitialization2337 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_COLON_in_variableInitialization2339 = new BitSet(new long[]{0x0000000000000010L,0x0000000002878000L});
    public static final BitSet FOLLOW_type_in_variableInitialization2343 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_variableInitialization2345 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_variableInitialization2349 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_ifExpression2381 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_ifExpression2385 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_ifExpression2387 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_ifExpression2391 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_ifExpression2393 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_ifExpression2397 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_ifExpression2399 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_literal2438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_literal2452 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_literal2465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REAL_in_literal2480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal2494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_literal2504 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_literal2508 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_literal2520 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_COLON_in_literal2522 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_literal2526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionLiteral_in_literal2538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyCollectionLiteral_in_literal2550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedLiteral_in_literal2562 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleLiteral_in_literal2574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dateLiteral_in_literal2586 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionLiteral2624 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_LBRACE_in_collectionLiteral2653 = new BitSet(new long[]{0x1000000170060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral2670 = new BitSet(new long[]{0x0000000100000040L});
    public static final BitSet FOLLOW_COMMA_in_collectionLiteral2683 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral2687 = new BitSet(new long[]{0x0000000100000040L});
    public static final BitSet FOLLOW_RBRACE_in_collectionLiteral2706 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_collectionItem2735 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_DOTDOT_in_collectionItem2746 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_collectionItem2750 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_emptyCollectionLiteral2779 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_emptyCollectionLiteral2781 = new BitSet(new long[]{0x0000000000000000L,0x0000000002078000L});
    public static final BitSet FOLLOW_collectionType_in_emptyCollectionLiteral2785 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_emptyCollectionLiteral2787 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_undefinedLiteral2817 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_undefinedLiteral2819 = new BitSet(new long[]{0x0000000000000010L,0x0000000002878000L});
    public static final BitSet FOLLOW_type_in_undefinedLiteral2823 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_undefinedLiteral2825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_85_in_undefinedLiteral2839 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_86_in_undefinedLiteral2853 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_87_in_tupleLiteral2887 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_LBRACE_in_tupleLiteral2893 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral2901 = new BitSet(new long[]{0x0000000100000040L});
    public static final BitSet FOLLOW_COMMA_in_tupleLiteral2912 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral2916 = new BitSet(new long[]{0x0000000100000040L});
    public static final BitSet FOLLOW_RBRACE_in_tupleLiteral2927 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tupleItem2958 = new BitSet(new long[]{0x0000000000000C00L});
    public static final BitSet FOLLOW_COLON_in_tupleItem2997 = new BitSet(new long[]{0x0000000000000010L,0x0000000002878000L});
    public static final BitSet FOLLOW_type_in_tupleItem3001 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_tupleItem3003 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_tupleItem3007 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_tupleItem3029 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_tupleItem3039 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_88_in_dateLiteral3084 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_LBRACE_in_dateLiteral3086 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_STRING_in_dateLiteral3090 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_RBRACE_in_dateLiteral3092 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleType_in_type3142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_type3154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleType_in_type3166 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeOnly3198 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_typeOnly3200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_simpleType3228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionType3266 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_collectionType3293 = new BitSet(new long[]{0x0000000000000010L,0x0000000002878000L});
    public static final BitSet FOLLOW_type_in_collectionType3297 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_collectionType3299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_87_in_tupleType3333 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_tupleType3335 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType3344 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_COMMA_in_tupleType3355 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType3359 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_RPAREN_in_tupleType3371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tuplePart3403 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_COLON_in_tuplePart3405 = new BitSet(new long[]{0x0000000000000010L,0x0000000002878000L});
    public static final BitSet FOLLOW_type_in_tuplePart3409 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cmd_in_cmdList3445 = new BitSet(new long[]{0x1000000000000000L,0x0000033D6C000000L});
    public static final BitSet FOLLOW_cmd_in_cmdList3457 = new BitSet(new long[]{0x1000000000000000L,0x0000033D6C000000L});
    public static final BitSet FOLLOW_EOF_in_cmdList3468 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cmdStmt_in_cmd3501 = new BitSet(new long[]{0x0000000002000002L});
    public static final BitSet FOLLOW_SEMI_in_cmd3506 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createCmd_in_cmdStmt3537 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createAssignCmd_in_cmdStmt3549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createInsertCmd_in_cmdStmt3562 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_destroyCmd_in_cmdStmt3574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_insertCmd_in_cmdStmt3586 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_deleteCmd_in_cmdStmt3598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_setCmd_in_cmdStmt3610 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_opEnterCmd_in_cmdStmt3622 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_opExitCmd_in_cmdStmt3634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_letCmd_in_cmdStmt3646 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_showCmd_in_cmdStmt3658 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hideCmd_in_cmdStmt3670 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cropCmd_in_cmdStmt3682 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_createCmd3713 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_createCmd3717 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_COLON_in_createCmd3724 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_simpleType_in_createCmd3728 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_91_in_createAssignCmd3759 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_createAssignCmd3763 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_createAssignCmd3765 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_createAssignCmd3767 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_simpleType_in_createAssignCmd3771 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_createInsertCmd3793 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_createInsertCmd3797 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_COLON_in_createInsertCmd3799 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_createInsertCmd3803 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_createInsertCmd3809 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_createInsertCmd3811 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_createInsertCmd3815 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_createInsertCmd3817 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_destroyCmd3855 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_destroyCmd3859 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_COMMA_in_destroyCmd3881 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_destroyCmd3885 = new BitSet(new long[]{0x0000000000000042L});
    public static final BitSet FOLLOW_94_in_insertCmd3926 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_insertCmd3928 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_insertCmd3937 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_insertCmd3941 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_insertCmd3949 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_COMMA_in_insertCmd3955 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_insertCmd3959 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_RPAREN_in_insertCmd3971 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_95_in_insertCmd3973 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_insertCmd3977 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_deleteCmd4014 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_deleteCmd4016 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_deleteCmd4024 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_COMMA_in_deleteCmd4028 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_deleteCmd4036 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_COMMA_in_deleteCmd4042 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_deleteCmd4046 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_RPAREN_in_deleteCmd4057 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_97_in_deleteCmd4059 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_deleteCmd4063 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_setCmd4095 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_setCmd4099 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_setCmd4101 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_setCmd4105 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_opEnterCmd4141 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_opEnterCmd4150 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_opEnterCmd4154 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_opEnterCmd4162 = new BitSet(new long[]{0x1000000070060390L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_opEnterCmd4172 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_COMMA_in_opEnterCmd4178 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_opEnterCmd4182 = new BitSet(new long[]{0x0000000000000240L});
    public static final BitSet FOLLOW_RPAREN_in_opEnterCmd4196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_100_in_opExitCmd4222 = new BitSet(new long[]{0x1000000070060192L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_opExitCmd4232 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_letCmd4267 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_letCmd4271 = new BitSet(new long[]{0x0000000000000C00L});
    public static final BitSet FOLLOW_COLON_in_letCmd4275 = new BitSet(new long[]{0x0000000000000010L,0x0000000002878000L});
    public static final BitSet FOLLOW_type_in_letCmd4279 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_letCmd4284 = new BitSet(new long[]{0x1000000070060190L,0x0000000001FFE3E8L});
    public static final BitSet FOLLOW_expression_in_letCmd4288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_101_in_hideCmd4317 = new BitSet(new long[]{0x0000000000000010L,0x000000C000000000L});
    public static final BitSet FOLLOW_102_in_hideCmd4326 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_hideCmd4339 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COLON_in_hideCmd4342 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_hideCmd4348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_hideCmd4359 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_hideCmd4361 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_hideCmd4367 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_hideCmd4369 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_97_in_hideCmd4371 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_hideCmd4375 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_104_in_showCmd4402 = new BitSet(new long[]{0x0000000000000010L,0x000000C000000000L});
    public static final BitSet FOLLOW_102_in_showCmd4411 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_showCmd4424 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COLON_in_showCmd4427 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_showCmd4433 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_showCmd4444 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_showCmd4446 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_showCmd4452 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_showCmd4454 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_97_in_showCmd4456 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_showCmd4460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_105_in_cropCmd4487 = new BitSet(new long[]{0x0000000000000012L,0x0000008000000000L});
    public static final BitSet FOLLOW_idList_in_cropCmd4500 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_COLON_in_cropCmd4503 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_cropCmd4509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_cropCmd4520 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_LPAREN_in_cropCmd4522 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_cropCmd4528 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_cropCmd4530 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_97_in_cropCmd4532 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_cropCmd4536 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_synpred1_TestSuite2988 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_synpred1_TestSuite2990 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EQUAL_in_synpred1_TestSuite2992 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_synpred2_TestSuite4226 = new BitSet(new long[]{0x0000000000000002L});

}