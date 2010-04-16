// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 TestSuite.g 2010-04-16 10:49:32
 
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
import org.tzi.use.uml.sys.MShowHideCropCmd.Mode;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all") public class TestSuiteParser extends BaseParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "IDENT", "COLON", "EQUAL", "LPAREN", "COMMA", "RPAREN", "NOT_EQUAL", "LESS", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "PLUS", "MINUS", "STAR", "SLASH", "ARROW", "DOT", "AT", "BAR", "SEMI", "LBRACK", "RBRACK", "INT", "REAL", "STRING", "HASH", "LBRACE", "RBRACE", "DOTDOT", "COLON_EQUAL", "NEWLINE", "WS", "SL_COMMENT", "ML_COMMENT", "COLON_COLON", "SCRIPTBODY", "RANGE_OR_INT", "ESC", "HEX_DIGIT", "VOCAB", "'testsuite'", "'for'", "'model'", "'setup'", "'!'", "'end'", "'testcase'", "'assert'", "'let'", "'in'", "'implies'", "'or'", "'xor'", "'and'", "'div'", "'not'", "'allInstances'", "'pre'", "'iterate'", "'oclAsType'", "'oclIsKindOf'", "'oclIsTypeOf'", "'if'", "'then'", "'else'", "'endif'", "'true'", "'false'", "'Set'", "'Sequence'", "'Bag'", "'OrderedSet'", "'oclEmpty'", "'oclUndefined'", "'Undefined'", "'null'", "'Tuple'", "'Date'", "'Collection'", "'create'", "'assign'", "'between'", "'destroy'", "'insert'", "'into'", "'delete'", "'from'", "'set'", "'openter'", "'opexit'", "'hide'", "'all'", "'link'", "'show'", "'crop'"
    };
    public static final int STAR=17;
    public static final int EOF=-1;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__91=91;
    public static final int RPAREN=9;
    public static final int T__92=92;
    public static final int GREATER=12;
    public static final int T__90=90;
    public static final int NOT_EQUAL=10;
    public static final int LESS=11;
    public static final int T__98=98;
    public static final int T__97=97;
    public static final int T__96=96;
    public static final int T__95=95;
    public static final int RBRACK=25;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int T__82=82;
    public static final int RBRACE=31;
    public static final int T__83=83;
    public static final int INT=26;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int REAL=27;
    public static final int T__71=71;
    public static final int WS=35;
    public static final int T__72=72;
    public static final int T__70=70;
    public static final int SL_COMMENT=36;
    public static final int LESS_EQUAL=13;
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
    public static final int LBRACK=24;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int ESC=41;
    public static final int LBRACE=30;
    public static final int DOTDOT=32;
    public static final int T__61=61;
    public static final int T__60=60;
    public static final int LPAREN=7;
    public static final int T__55=55;
    public static final int AT=21;
    public static final int ML_COMMENT=37;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int SLASH=18;
    public static final int COLON_EQUAL=33;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int COMMA=8;
    public static final int T__59=59;
    public static final int EQUAL=6;
    public static final int IDENT=4;
    public static final int PLUS=15;
    public static final int RANGE_OR_INT=40;
    public static final int DOT=20;
    public static final int SCRIPTBODY=39;
    public static final int T__50=50;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int HASH=29;
    public static final int HEX_DIGIT=42;
    public static final int COLON_COLON=38;
    public static final int MINUS=16;
    public static final int SEMI=23;
    public static final int COLON=5;
    public static final int NEWLINE=34;
    public static final int VOCAB=43;
    public static final int ARROW=19;
    public static final int GREATER_EQUAL=14;
    public static final int BAR=22;
    public static final int STRING=28;

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
    // TestSuite.g:114:1: testCase returns [ASTTestCase n] : 'testcase' name= IDENT ( '!' c= cmd | 'assert' exp= expression )* 'end' ;
    public final ASTTestCase testCase() throws RecognitionException {
        ASTTestCase n = null;

        Token name=null;
        ASTCmd c = null;

        ASTExpression exp = null;


        try {
            // TestSuite.g:115:1: ( 'testcase' name= IDENT ( '!' c= cmd | 'assert' exp= expression )* 'end' )
            // TestSuite.g:116:3: 'testcase' name= IDENT ( '!' c= cmd | 'assert' exp= expression )* 'end'
            {
            match(input,50,FOLLOW_50_in_testCase224); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_testCase228); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTTestCase(name); 
            }
            // TestSuite.g:117:3: ( '!' c= cmd | 'assert' exp= expression )*
            loop4:
            do {
                int alt4=3;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==48) ) {
                    alt4=1;
                }
                else if ( (LA4_0==51) ) {
                    alt4=2;
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
            	    // TestSuite.g:120:7: 'assert' exp= expression
            	    {
            	    match(input,51,FOLLOW_51_in_testCase265); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_testCase269);
            	    exp=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addStatement(new ASTAssert(exp, input.LT(-1))); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            match(input,49,FOLLOW_49_in_testCase281); if (state.failed) return n;

            }

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


    // $ANTLR start "expressionOnly"
    // TestSuite.g:153:1: expressionOnly returns [ASTExpression n] : nExp= expression EOF ;
    public final ASTExpression expressionOnly() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExp = null;


        try {
            // TestSuite.g:154:1: (nExp= expression EOF )
            // TestSuite.g:155:5: nExp= expression EOF
            {
            pushFollow(FOLLOW_expression_in_expressionOnly311);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_expressionOnly313); if (state.failed) return n;
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
    // TestSuite.g:162:1: expression returns [ASTExpression n] : ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression ;
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
            // TestSuite.g:168:1: ( ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression )
            // TestSuite.g:169:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of expression */ 
            }
            // TestSuite.g:170:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==52) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // TestSuite.g:171:7: 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in'
            	    {
            	    match(input,52,FOLLOW_52_in_expression361); if (state.failed) return n;
            	    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_expression365); if (state.failed) return n;
            	    // TestSuite.g:171:24: ( COLON t= type )?
            	    int alt5=2;
            	    int LA5_0 = input.LA(1);

            	    if ( (LA5_0==COLON) ) {
            	        alt5=1;
            	    }
            	    switch (alt5) {
            	        case 1 :
            	            // TestSuite.g:171:26: COLON t= type
            	            {
            	            match(input,COLON,FOLLOW_COLON_in_expression369); if (state.failed) return n;
            	            pushFollow(FOLLOW_type_in_expression373);
            	            t=type();

            	            state._fsp--;
            	            if (state.failed) return n;

            	            }
            	            break;

            	    }

            	    match(input,EQUAL,FOLLOW_EQUAL_in_expression378); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_expression382);
            	    e1=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    match(input,53,FOLLOW_53_in_expression384); if (state.failed) return n;
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
            	    break loop6;
                }
            } while (true);

            pushFollow(FOLLOW_conditionalImpliesExpression_in_expression409);
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
    // TestSuite.g:199:1: paramList returns [List paramList] : LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN ;
    public final List paramList() throws RecognitionException {
        List paramList = null;

        ASTVariableDeclaration v = null;


         paramList = new ArrayList(); 
        try {
            // TestSuite.g:201:1: ( LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN )
            // TestSuite.g:202:5: LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN
            {
            match(input,LPAREN,FOLLOW_LPAREN_in_paramList442); if (state.failed) return paramList;
            // TestSuite.g:203:5: (v= variableDeclaration ( COMMA v= variableDeclaration )* )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==IDENT) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // TestSuite.g:204:7: v= variableDeclaration ( COMMA v= variableDeclaration )*
                    {
                    pushFollow(FOLLOW_variableDeclaration_in_paramList459);
                    v=variableDeclaration();

                    state._fsp--;
                    if (state.failed) return paramList;
                    if ( state.backtracking==0 ) {
                       paramList.add(v); 
                    }
                    // TestSuite.g:205:7: ( COMMA v= variableDeclaration )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==COMMA) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // TestSuite.g:205:9: COMMA v= variableDeclaration
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_paramList471); if (state.failed) return paramList;
                    	    pushFollow(FOLLOW_variableDeclaration_in_paramList475);
                    	    v=variableDeclaration();

                    	    state._fsp--;
                    	    if (state.failed) return paramList;
                    	    if ( state.backtracking==0 ) {
                    	       paramList.add(v); 
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

            match(input,RPAREN,FOLLOW_RPAREN_in_paramList495); if (state.failed) return paramList;

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
    // TestSuite.g:213:1: idList returns [List idList] : id0= IDENT ( COMMA idn= IDENT )* ;
    public final List idList() throws RecognitionException {
        List idList = null;

        Token id0=null;
        Token idn=null;

         idList = new ArrayList(); 
        try {
            // TestSuite.g:215:1: (id0= IDENT ( COMMA idn= IDENT )* )
            // TestSuite.g:216:5: id0= IDENT ( COMMA idn= IDENT )*
            {
            id0=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList524); if (state.failed) return idList;
            if ( state.backtracking==0 ) {
               idList.add(id0); 
            }
            // TestSuite.g:217:5: ( COMMA idn= IDENT )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==COMMA) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // TestSuite.g:217:7: COMMA idn= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_idList534); if (state.failed) return idList;
            	    idn=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList538); if (state.failed) return idList;
            	    if ( state.backtracking==0 ) {
            	       idList.add(idn); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop9;
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
    // TestSuite.g:225:1: variableDeclaration returns [ASTVariableDeclaration n] : name= IDENT COLON t= type ;
    public final ASTVariableDeclaration variableDeclaration() throws RecognitionException {
        ASTVariableDeclaration n = null;

        Token name=null;
        ASTType t = null;


        try {
            // TestSuite.g:226:1: (name= IDENT COLON t= type )
            // TestSuite.g:227:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableDeclaration569); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableDeclaration571); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableDeclaration575);
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
    // TestSuite.g:235:1: conditionalImpliesExpression returns [ASTExpression n] : nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* ;
    public final ASTExpression conditionalImpliesExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndOrExp = null;

        ASTExpression n1 = null;


        try {
            // TestSuite.g:236:1: (nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* )
            // TestSuite.g:237:5: nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )*
            {
            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression611);
            nCndOrExp=conditionalOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndOrExp;
            }
            // TestSuite.g:238:5: (op= 'implies' n1= conditionalOrExpression )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==54) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // TestSuite.g:238:7: op= 'implies' n1= conditionalOrExpression
            	    {
            	    op=(Token)match(input,54,FOLLOW_54_in_conditionalImpliesExpression624); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression628);
            	    n1=conditionalOrExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop10;
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
    // TestSuite.g:247:1: conditionalOrExpression returns [ASTExpression n] : nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* ;
    public final ASTExpression conditionalOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndXorExp = null;

        ASTExpression n1 = null;


        try {
            // TestSuite.g:248:1: (nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* )
            // TestSuite.g:249:5: nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )*
            {
            pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression673);
            nCndXorExp=conditionalXOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndXorExp;
            }
            // TestSuite.g:250:5: (op= 'or' n1= conditionalXOrExpression )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==55) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // TestSuite.g:250:7: op= 'or' n1= conditionalXOrExpression
            	    {
            	    op=(Token)match(input,55,FOLLOW_55_in_conditionalOrExpression686); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression690);
            	    n1=conditionalXOrExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
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
    // $ANTLR end "conditionalOrExpression"


    // $ANTLR start "conditionalXOrExpression"
    // TestSuite.g:259:1: conditionalXOrExpression returns [ASTExpression n] : nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* ;
    public final ASTExpression conditionalXOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndAndExp = null;

        ASTExpression n1 = null;


        try {
            // TestSuite.g:260:1: (nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* )
            // TestSuite.g:261:5: nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )*
            {
            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression734);
            nCndAndExp=conditionalAndExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndAndExp;
            }
            // TestSuite.g:262:5: (op= 'xor' n1= conditionalAndExpression )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==56) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // TestSuite.g:262:7: op= 'xor' n1= conditionalAndExpression
            	    {
            	    op=(Token)match(input,56,FOLLOW_56_in_conditionalXOrExpression747); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression751);
            	    n1=conditionalAndExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
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
    // $ANTLR end "conditionalXOrExpression"


    // $ANTLR start "conditionalAndExpression"
    // TestSuite.g:271:1: conditionalAndExpression returns [ASTExpression n] : nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* ;
    public final ASTExpression conditionalAndExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nEqExp = null;

        ASTExpression n1 = null;


        try {
            // TestSuite.g:272:1: (nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* )
            // TestSuite.g:273:5: nEqExp= equalityExpression (op= 'and' n1= equalityExpression )*
            {
            pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression795);
            nEqExp=equalityExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nEqExp;
            }
            // TestSuite.g:274:5: (op= 'and' n1= equalityExpression )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==57) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // TestSuite.g:274:7: op= 'and' n1= equalityExpression
            	    {
            	    op=(Token)match(input,57,FOLLOW_57_in_conditionalAndExpression808); if (state.failed) return n;
            	    pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression812);
            	    n1=equalityExpression();

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
    // $ANTLR end "conditionalAndExpression"


    // $ANTLR start "equalityExpression"
    // TestSuite.g:283:1: equalityExpression returns [ASTExpression n] : nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* ;
    public final ASTExpression equalityExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nRelExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // TestSuite.g:285:1: (nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* )
            // TestSuite.g:286:5: nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            {
            pushFollow(FOLLOW_relationalExpression_in_equalityExpression860);
            nRelExp=relationalExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nRelExp;
            }
            // TestSuite.g:287:5: ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==EQUAL||LA14_0==NOT_EQUAL) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // TestSuite.g:287:7: ( EQUAL | NOT_EQUAL ) n1= relationalExpression
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

            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression889);
            	    n1=relationalExpression();

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
    // $ANTLR end "equalityExpression"


    // $ANTLR start "relationalExpression"
    // TestSuite.g:297:1: relationalExpression returns [ASTExpression n] : nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* ;
    public final ASTExpression relationalExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nAddiExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // TestSuite.g:299:1: (nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* )
            // TestSuite.g:300:5: nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            {
            pushFollow(FOLLOW_additiveExpression_in_relationalExpression938);
            nAddiExp=additiveExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nAddiExp;
            }
            // TestSuite.g:301:5: ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( ((LA15_0>=LESS && LA15_0<=GREATER_EQUAL)) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // TestSuite.g:301:7: ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression
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

            	    pushFollow(FOLLOW_additiveExpression_in_relationalExpression974);
            	    n1=additiveExpression();

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
    // $ANTLR end "relationalExpression"


    // $ANTLR start "additiveExpression"
    // TestSuite.g:311:1: additiveExpression returns [ASTExpression n] : nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* ;
    public final ASTExpression additiveExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nMulExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // TestSuite.g:313:1: (nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* )
            // TestSuite.g:314:5: nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            {
            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1024);
            nMulExp=multiplicativeExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nMulExp;
            }
            // TestSuite.g:315:5: ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>=PLUS && LA16_0<=MINUS)) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // TestSuite.g:315:7: ( PLUS | MINUS ) n1= multiplicativeExpression
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

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1052);
            	    n1=multiplicativeExpression();

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
    // $ANTLR end "additiveExpression"


    // $ANTLR start "multiplicativeExpression"
    // TestSuite.g:326:1: multiplicativeExpression returns [ASTExpression n] : nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* ;
    public final ASTExpression multiplicativeExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // TestSuite.g:328:1: (nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* )
            // TestSuite.g:329:5: nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            {
            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1102);
            nUnExp=unaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nUnExp;
            }
            // TestSuite.g:330:5: ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0>=STAR && LA17_0<=SLASH)||LA17_0==58) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // TestSuite.g:330:7: ( STAR | SLASH | 'div' ) n1= unaryExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       op = input.LT(1); 
            	    }
            	    if ( (input.LA(1)>=STAR && input.LA(1)<=SLASH)||input.LA(1)==58 ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1134);
            	    n1=unaryExpression();

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
    // $ANTLR end "multiplicativeExpression"


    // $ANTLR start "unaryExpression"
    // TestSuite.g:342:1: unaryExpression returns [ASTExpression n] : ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression );
    public final ASTExpression unaryExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression nPosExp = null;


         Token op = null; 
        try {
            // TestSuite.g:344:1: ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( ((LA18_0>=PLUS && LA18_0<=MINUS)||LA18_0==59) ) {
                alt18=1;
            }
            else if ( (LA18_0==IDENT||LA18_0==LPAREN||(LA18_0>=INT && LA18_0<=HASH)||(LA18_0>=62 && LA18_0<=66)||(LA18_0>=70 && LA18_0<=81)) ) {
                alt18=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // TestSuite.g:345:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    {
                    // TestSuite.g:345:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    // TestSuite.g:345:9: ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression
                    {
                    if ( state.backtracking==0 ) {
                       op = input.LT(1); 
                    }
                    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS)||input.LA(1)==59 ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression1220);
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
                    // TestSuite.g:349:7: nPosExp= postfixExpression
                    {
                    pushFollow(FOLLOW_postfixExpression_in_unaryExpression1240);
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
    // TestSuite.g:357:1: postfixExpression returns [ASTExpression n] : nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* ;
    public final ASTExpression postfixExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nPrimExp = null;

        ASTExpression nPc = null;


         boolean arrow = false; 
        try {
            // TestSuite.g:359:1: (nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* )
            // TestSuite.g:360:5: nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            {
            pushFollow(FOLLOW_primaryExpression_in_postfixExpression1273);
            nPrimExp=primaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nPrimExp; 
            }
            // TestSuite.g:361:5: ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>=ARROW && LA20_0<=DOT)) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // TestSuite.g:362:6: ( ARROW | DOT ) nPc= propertyCall[$n, arrow]
            	    {
            	    // TestSuite.g:362:6: ( ARROW | DOT )
            	    int alt19=2;
            	    int LA19_0 = input.LA(1);

            	    if ( (LA19_0==ARROW) ) {
            	        alt19=1;
            	    }
            	    else if ( (LA19_0==DOT) ) {
            	        alt19=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 19, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt19) {
            	        case 1 :
            	            // TestSuite.g:362:8: ARROW
            	            {
            	            match(input,ARROW,FOLLOW_ARROW_in_postfixExpression1291); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = true; 
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // TestSuite.g:362:34: DOT
            	            {
            	            match(input,DOT,FOLLOW_DOT_in_postfixExpression1297); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = false; 
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_propertyCall_in_postfixExpression1308);
            	    nPc=propertyCall(n, arrow);

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = nPc; 
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
    // $ANTLR end "postfixExpression"


    // $ANTLR start "primaryExpression"
    // TestSuite.g:378:1: primaryExpression returns [ASTExpression n] : (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? );
    public final ASTExpression primaryExpression() throws RecognitionException {
        ASTExpression n = null;

        Token id1=null;
        ASTExpression nLit = null;

        ASTExpression nPc = null;

        ASTExpression nExp = null;

        ASTExpression nIfExp = null;


        try {
            // TestSuite.g:379:1: (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? )
            int alt23=5;
            switch ( input.LA(1) ) {
            case INT:
            case REAL:
            case STRING:
            case HASH:
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
            case 81:
                {
                alt23=1;
                }
                break;
            case IDENT:
                {
                switch ( input.LA(2) ) {
                case COLON_COLON:
                    {
                    alt23=1;
                    }
                    break;
                case EOF:
                case IDENT:
                case EQUAL:
                case LPAREN:
                case COMMA:
                case RPAREN:
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
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 67:
                case 68:
                case 69:
                case 83:
                case 84:
                case 86:
                case 87:
                case 89:
                case 91:
                case 92:
                case 93:
                case 94:
                case 97:
                case 98:
                    {
                    alt23=2;
                    }
                    break;
                case DOT:
                    {
                    int LA23_6 = input.LA(3);

                    if ( (LA23_6==60) ) {
                        alt23=5;
                    }
                    else if ( (LA23_6==IDENT||(LA23_6>=62 && LA23_6<=65)) ) {
                        alt23=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 23, 6, input);

                        throw nvae;
                    }
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 23, 2, input);

                    throw nvae;
                }

                }
                break;
            case 62:
            case 63:
            case 64:
            case 65:
                {
                alt23=2;
                }
                break;
            case LPAREN:
                {
                alt23=3;
                }
                break;
            case 66:
                {
                alt23=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }

            switch (alt23) {
                case 1 :
                    // TestSuite.g:380:7: nLit= literal
                    {
                    pushFollow(FOLLOW_literal_in_primaryExpression1348);
                    nLit=literal();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nLit; 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:381:7: nPc= propertyCall[null, false]
                    {
                    pushFollow(FOLLOW_propertyCall_in_primaryExpression1360);
                    nPc=propertyCall(null, false);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nPc; 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:382:7: LPAREN nExp= expression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression1371); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_primaryExpression1375);
                    nExp=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression1377); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExp; 
                    }

                    }
                    break;
                case 4 :
                    // TestSuite.g:383:7: nIfExp= ifExpression
                    {
                    pushFollow(FOLLOW_ifExpression_in_primaryExpression1389);
                    nIfExp=ifExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nIfExp; 
                    }

                    }
                    break;
                case 5 :
                    // TestSuite.g:385:7: id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )?
                    {
                    id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_primaryExpression1406); if (state.failed) return n;
                    match(input,DOT,FOLLOW_DOT_in_primaryExpression1408); if (state.failed) return n;
                    match(input,60,FOLLOW_60_in_primaryExpression1410); if (state.failed) return n;
                    // TestSuite.g:385:36: ( LPAREN RPAREN )?
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0==LPAREN) ) {
                        alt21=1;
                    }
                    switch (alt21) {
                        case 1 :
                            // TestSuite.g:385:38: LPAREN RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression1414); if (state.failed) return n;
                            match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression1416); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTAllInstancesExpression(id1); 
                    }
                    // TestSuite.g:387:7: ( AT 'pre' )?
                    int alt22=2;
                    int LA22_0 = input.LA(1);

                    if ( (LA22_0==AT) ) {
                        alt22=1;
                    }
                    switch (alt22) {
                        case 1 :
                            // TestSuite.g:387:9: AT 'pre'
                            {
                            match(input,AT,FOLLOW_AT_in_primaryExpression1437); if (state.failed) return n;
                            match(input,61,FOLLOW_61_in_primaryExpression1439); if (state.failed) return n;
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
    // TestSuite.g:400:1: propertyCall[ASTExpression source, boolean followsArrow] returns [ASTExpression n] : ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] );
    public final ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExpQuery = null;

        ASTExpression nExpIterate = null;

        ASTOperationExpression nExpOperation = null;

        ASTTypeArgExpression nExpType = null;


        try {
            // TestSuite.g:401:1: ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] )
            int alt24=4;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA24_1 = input.LA(2);

                if ( ((( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )&&( input.LA(2) == LPAREN ))) ) {
                    alt24=1;
                }
                else if ( (true) ) {
                    alt24=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 24, 1, input);

                    throw nvae;
                }
                }
                break;
            case 62:
                {
                alt24=2;
                }
                break;
            case 63:
            case 64:
            case 65:
                {
                alt24=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }

            switch (alt24) {
                case 1 :
                    // TestSuite.g:405:7: {...}?{...}?nExpQuery= queryExpression[source]
                    {
                    if ( !(( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) ");
                    }
                    if ( !(( input.LA(2) == LPAREN )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " input.LA(2) == LPAREN ");
                    }
                    pushFollow(FOLLOW_queryExpression_in_propertyCall1512);
                    nExpQuery=queryExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpQuery; 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:408:7: nExpIterate= iterateExpression[source]
                    {
                    pushFollow(FOLLOW_iterateExpression_in_propertyCall1525);
                    nExpIterate=iterateExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpIterate; 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:409:7: nExpOperation= operationExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_operationExpression_in_propertyCall1538);
                    nExpOperation=operationExpression(source, followsArrow);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpOperation; 
                    }

                    }
                    break;
                case 4 :
                    // TestSuite.g:410:7: nExpType= typeExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_typeExpression_in_propertyCall1551);
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
    // TestSuite.g:419:1: queryExpression[ASTExpression range] returns [ASTExpression n] : op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN ;
    public final ASTExpression queryExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTElemVarsDeclaration decls = null;

        ASTExpression nExp = null;


        ASTElemVarsDeclaration decl = new ASTElemVarsDeclaration(); 
        try {
            // TestSuite.g:420:69: (op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN )
            // TestSuite.g:421:5: op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN
            {
            op=(Token)match(input,IDENT,FOLLOW_IDENT_in_queryExpression1586); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_queryExpression1593); if (state.failed) return n;
            // TestSuite.g:423:5: (decls= elemVarsDeclaration BAR )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==IDENT) ) {
                int LA25_1 = input.LA(2);

                if ( (LA25_1==COLON||LA25_1==COMMA||LA25_1==BAR) ) {
                    alt25=1;
                }
            }
            switch (alt25) {
                case 1 :
                    // TestSuite.g:423:7: decls= elemVarsDeclaration BAR
                    {
                    pushFollow(FOLLOW_elemVarsDeclaration_in_queryExpression1604);
                    decls=elemVarsDeclaration();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      decl = decls;
                    }
                    match(input,BAR,FOLLOW_BAR_in_queryExpression1608); if (state.failed) return n;

                    }
                    break;

            }

            pushFollow(FOLLOW_expression_in_queryExpression1619);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_queryExpression1625); if (state.failed) return n;
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
    // TestSuite.g:437:1: iterateExpression[ASTExpression range] returns [ASTExpression n] : i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN ;
    public final ASTExpression iterateExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTElemVarsDeclaration decls = null;

        ASTVariableInitialization init = null;

        ASTExpression nExp = null;


        try {
            // TestSuite.g:437:65: (i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN )
            // TestSuite.g:438:5: i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN
            {
            i=(Token)match(input,62,FOLLOW_62_in_iterateExpression1657); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_iterateExpression1663); if (state.failed) return n;
            pushFollow(FOLLOW_elemVarsDeclaration_in_iterateExpression1671);
            decls=elemVarsDeclaration();

            state._fsp--;
            if (state.failed) return n;
            match(input,SEMI,FOLLOW_SEMI_in_iterateExpression1673); if (state.failed) return n;
            pushFollow(FOLLOW_variableInitialization_in_iterateExpression1681);
            init=variableInitialization();

            state._fsp--;
            if (state.failed) return n;
            match(input,BAR,FOLLOW_BAR_in_iterateExpression1683); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_iterateExpression1691);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_iterateExpression1697); if (state.failed) return n;
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
    // TestSuite.g:459:1: operationExpression[ASTExpression source, boolean followsArrow] returns [ASTOperationExpression n] : name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? ;
    public final ASTOperationExpression operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTOperationExpression n = null;

        Token name=null;
        Token rolename=null;
        ASTExpression e = null;


        try {
            // TestSuite.g:461:1: (name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? )
            // TestSuite.g:462:5: name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression1741); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTOperationExpression(name, source, followsArrow); 
            }
            // TestSuite.g:465:5: ( LBRACK rolename= IDENT RBRACK )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==LBRACK) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // TestSuite.g:465:7: LBRACK rolename= IDENT RBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_operationExpression1757); if (state.failed) return n;
                    rolename=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression1761); if (state.failed) return n;
                    match(input,RBRACK,FOLLOW_RBRACK_in_operationExpression1763); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setExplicitRolename(rolename); 
                    }

                    }
                    break;

            }

            // TestSuite.g:467:5: ( AT 'pre' )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==AT) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // TestSuite.g:467:7: AT 'pre'
                    {
                    match(input,AT,FOLLOW_AT_in_operationExpression1776); if (state.failed) return n;
                    match(input,61,FOLLOW_61_in_operationExpression1778); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setIsPre(); 
                    }

                    }
                    break;

            }

            // TestSuite.g:468:5: ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==LPAREN) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // TestSuite.g:469:7: LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_operationExpression1799); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.hasParentheses(); 
                    }
                    // TestSuite.g:470:7: (e= expression ( COMMA e= expression )* )?
                    int alt29=2;
                    int LA29_0 = input.LA(1);

                    if ( (LA29_0==IDENT||LA29_0==LPAREN||(LA29_0>=PLUS && LA29_0<=MINUS)||(LA29_0>=INT && LA29_0<=HASH)||LA29_0==52||LA29_0==59||(LA29_0>=62 && LA29_0<=66)||(LA29_0>=70 && LA29_0<=81)) ) {
                        alt29=1;
                    }
                    switch (alt29) {
                        case 1 :
                            // TestSuite.g:471:7: e= expression ( COMMA e= expression )*
                            {
                            pushFollow(FOLLOW_expression_in_operationExpression1820);
                            e=expression();

                            state._fsp--;
                            if (state.failed) return n;
                            if ( state.backtracking==0 ) {
                               n.addArg(e); 
                            }
                            // TestSuite.g:472:7: ( COMMA e= expression )*
                            loop28:
                            do {
                                int alt28=2;
                                int LA28_0 = input.LA(1);

                                if ( (LA28_0==COMMA) ) {
                                    alt28=1;
                                }


                                switch (alt28) {
                            	case 1 :
                            	    // TestSuite.g:472:9: COMMA e= expression
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_operationExpression1832); if (state.failed) return n;
                            	    pushFollow(FOLLOW_expression_in_operationExpression1836);
                            	    e=expression();

                            	    state._fsp--;
                            	    if (state.failed) return n;
                            	    if ( state.backtracking==0 ) {
                            	       n.addArg(e); 
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

                    match(input,RPAREN,FOLLOW_RPAREN_in_operationExpression1856); if (state.failed) return n;

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
    // TestSuite.g:484:1: typeExpression[ASTExpression source, boolean followsArrow] returns [ASTTypeArgExpression n] : ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN ;
    public final ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTTypeArgExpression n = null;

        ASTType t = null;


         Token opToken = null; 
        try {
            // TestSuite.g:487:1: ( ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN )
            // TestSuite.g:488:2: ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN
            {
            if ( state.backtracking==0 ) {
               opToken = input.LT(1); 
            }
            if ( (input.LA(1)>=63 && input.LA(1)<=65) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_typeExpression1915); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_typeExpression1919);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_typeExpression1921); if (state.failed) return n;
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
    // TestSuite.g:499:1: elemVarsDeclaration returns [ASTElemVarsDeclaration n] : idListRes= idList ( COLON t= type )? ;
    public final ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException {
        ASTElemVarsDeclaration n = null;

        List idListRes = null;

        ASTType t = null;


         List idList; 
        try {
            // TestSuite.g:501:1: (idListRes= idList ( COLON t= type )? )
            // TestSuite.g:502:5: idListRes= idList ( COLON t= type )?
            {
            pushFollow(FOLLOW_idList_in_elemVarsDeclaration1960);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            // TestSuite.g:503:5: ( COLON t= type )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==COLON) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // TestSuite.g:503:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_elemVarsDeclaration1968); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_elemVarsDeclaration1972);
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
    // TestSuite.g:512:1: variableInitialization returns [ASTVariableInitialization n] : name= IDENT COLON t= type EQUAL e= expression ;
    public final ASTVariableInitialization variableInitialization() throws RecognitionException {
        ASTVariableInitialization n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // TestSuite.g:513:1: (name= IDENT COLON t= type EQUAL e= expression )
            // TestSuite.g:514:5: name= IDENT COLON t= type EQUAL e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableInitialization2007); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableInitialization2009); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableInitialization2013);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EQUAL,FOLLOW_EQUAL_in_variableInitialization2015); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_variableInitialization2019);
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
    // TestSuite.g:523:1: ifExpression returns [ASTExpression n] : i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' ;
    public final ASTExpression ifExpression() throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTExpression cond = null;

        ASTExpression t = null;

        ASTExpression e = null;


        try {
            // TestSuite.g:524:1: (i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' )
            // TestSuite.g:525:5: i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif'
            {
            i=(Token)match(input,66,FOLLOW_66_in_ifExpression2051); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression2055);
            cond=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,67,FOLLOW_67_in_ifExpression2057); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression2061);
            t=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,68,FOLLOW_68_in_ifExpression2063); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression2067);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,69,FOLLOW_69_in_ifExpression2069); if (state.failed) return n;
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
    // TestSuite.g:545:1: literal returns [ASTExpression n] : (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral );
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
            // TestSuite.g:546:1: (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral )
            int alt32=12;
            switch ( input.LA(1) ) {
            case 70:
                {
                alt32=1;
                }
                break;
            case 71:
                {
                alt32=2;
                }
                break;
            case INT:
                {
                alt32=3;
                }
                break;
            case REAL:
                {
                alt32=4;
                }
                break;
            case STRING:
                {
                alt32=5;
                }
                break;
            case HASH:
                {
                alt32=6;
                }
                break;
            case IDENT:
                {
                alt32=7;
                }
                break;
            case 72:
            case 73:
            case 74:
            case 75:
                {
                alt32=8;
                }
                break;
            case 76:
                {
                alt32=9;
                }
                break;
            case 77:
            case 78:
            case 79:
                {
                alt32=10;
                }
                break;
            case 80:
                {
                alt32=11;
                }
                break;
            case 81:
                {
                alt32=12;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;
            }

            switch (alt32) {
                case 1 :
                    // TestSuite.g:547:7: t= 'true'
                    {
                    t=(Token)match(input,70,FOLLOW_70_in_literal2108); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(true); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:548:7: f= 'false'
                    {
                    f=(Token)match(input,71,FOLLOW_71_in_literal2122); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(false); 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:549:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_literal2135); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTIntegerLiteral(i); 
                    }

                    }
                    break;
                case 4 :
                    // TestSuite.g:550:7: r= REAL
                    {
                    r=(Token)match(input,REAL,FOLLOW_REAL_in_literal2150); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTRealLiteral(r); 
                    }

                    }
                    break;
                case 5 :
                    // TestSuite.g:551:7: s= STRING
                    {
                    s=(Token)match(input,STRING,FOLLOW_STRING_in_literal2164); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTStringLiteral(s); 
                    }

                    }
                    break;
                case 6 :
                    // TestSuite.g:552:7: HASH enumLit= IDENT
                    {
                    match(input,HASH,FOLLOW_HASH_in_literal2174); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal2178); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumLit);  reportWarning(enumLit, "the usage of #enumerationLiteral is deprecated and will not be supported in the future, use 'Enumeration::Literal' instead");
                    }

                    }
                    break;
                case 7 :
                    // TestSuite.g:553:7: enumName= IDENT '::' enumLit= IDENT
                    {
                    enumName=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal2190); if (state.failed) return n;
                    match(input,COLON_COLON,FOLLOW_COLON_COLON_in_literal2192); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal2196); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumName, enumLit); 
                    }

                    }
                    break;
                case 8 :
                    // TestSuite.g:554:7: nColIt= collectionLiteral
                    {
                    pushFollow(FOLLOW_collectionLiteral_in_literal2208);
                    nColIt=collectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nColIt; 
                    }

                    }
                    break;
                case 9 :
                    // TestSuite.g:555:7: nEColIt= emptyCollectionLiteral
                    {
                    pushFollow(FOLLOW_emptyCollectionLiteral_in_literal2220);
                    nEColIt=emptyCollectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nEColIt; 
                    }

                    }
                    break;
                case 10 :
                    // TestSuite.g:556:7: nUndLit= undefinedLiteral
                    {
                    pushFollow(FOLLOW_undefinedLiteral_in_literal2232);
                    nUndLit=undefinedLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nUndLit; 
                    }

                    }
                    break;
                case 11 :
                    // TestSuite.g:557:7: nTupleLit= tupleLiteral
                    {
                    pushFollow(FOLLOW_tupleLiteral_in_literal2244);
                    nTupleLit=tupleLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nTupleLit; 
                    }

                    }
                    break;
                case 12 :
                    // TestSuite.g:558:7: nDateLit= dateLiteral
                    {
                    pushFollow(FOLLOW_dateLiteral_in_literal2256);
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
    // TestSuite.g:566:1: collectionLiteral returns [ASTCollectionLiteral n] : ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE ;
    public final ASTCollectionLiteral collectionLiteral() throws RecognitionException {
        ASTCollectionLiteral n = null;

        ASTCollectionItem ci = null;


         Token op = null; 
        try {
            // TestSuite.g:568:1: ( ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE )
            // TestSuite.g:569:5: ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE
            {
            if ( state.backtracking==0 ) {
               op = input.LT(1); 
            }
            if ( (input.LA(1)>=72 && input.LA(1)<=75) ) {
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
            match(input,LBRACE,FOLLOW_LBRACE_in_collectionLiteral2323); if (state.failed) return n;
            // TestSuite.g:573:5: (ci= collectionItem ( COMMA ci= collectionItem )* )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==IDENT||LA34_0==LPAREN||(LA34_0>=PLUS && LA34_0<=MINUS)||(LA34_0>=INT && LA34_0<=HASH)||LA34_0==52||LA34_0==59||(LA34_0>=62 && LA34_0<=66)||(LA34_0>=70 && LA34_0<=81)) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // TestSuite.g:574:7: ci= collectionItem ( COMMA ci= collectionItem )*
                    {
                    pushFollow(FOLLOW_collectionItem_in_collectionLiteral2340);
                    ci=collectionItem();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addItem(ci); 
                    }
                    // TestSuite.g:575:7: ( COMMA ci= collectionItem )*
                    loop33:
                    do {
                        int alt33=2;
                        int LA33_0 = input.LA(1);

                        if ( (LA33_0==COMMA) ) {
                            alt33=1;
                        }


                        switch (alt33) {
                    	case 1 :
                    	    // TestSuite.g:575:9: COMMA ci= collectionItem
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_collectionLiteral2353); if (state.failed) return n;
                    	    pushFollow(FOLLOW_collectionItem_in_collectionLiteral2357);
                    	    ci=collectionItem();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addItem(ci); 
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

            match(input,RBRACE,FOLLOW_RBRACE_in_collectionLiteral2376); if (state.failed) return n;

            }

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
    // TestSuite.g:584:1: collectionItem returns [ASTCollectionItem n] : e= expression ( DOTDOT e= expression )? ;
    public final ASTCollectionItem collectionItem() throws RecognitionException {
        ASTCollectionItem n = null;

        ASTExpression e = null;


         n = new ASTCollectionItem(); 
        try {
            // TestSuite.g:586:1: (e= expression ( DOTDOT e= expression )? )
            // TestSuite.g:587:5: e= expression ( DOTDOT e= expression )?
            {
            pushFollow(FOLLOW_expression_in_collectionItem2405);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setFirst(e); 
            }
            // TestSuite.g:588:5: ( DOTDOT e= expression )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==DOTDOT) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // TestSuite.g:588:7: DOTDOT e= expression
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_collectionItem2416); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_collectionItem2420);
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
    // TestSuite.g:598:1: emptyCollectionLiteral returns [ASTEmptyCollectionLiteral n] : 'oclEmpty' LPAREN t= collectionType RPAREN ;
    public final ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException {
        ASTEmptyCollectionLiteral n = null;

        ASTCollectionType t = null;


        try {
            // TestSuite.g:599:1: ( 'oclEmpty' LPAREN t= collectionType RPAREN )
            // TestSuite.g:600:5: 'oclEmpty' LPAREN t= collectionType RPAREN
            {
            match(input,76,FOLLOW_76_in_emptyCollectionLiteral2449); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_emptyCollectionLiteral2451); if (state.failed) return n;
            pushFollow(FOLLOW_collectionType_in_emptyCollectionLiteral2455);
            t=collectionType();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_emptyCollectionLiteral2457); if (state.failed) return n;
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
    // TestSuite.g:611:1: undefinedLiteral returns [ASTUndefinedLiteral n] : ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' );
    public final ASTUndefinedLiteral undefinedLiteral() throws RecognitionException {
        ASTUndefinedLiteral n = null;

        ASTType t = null;


        try {
            // TestSuite.g:612:1: ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' )
            int alt36=3;
            switch ( input.LA(1) ) {
            case 77:
                {
                alt36=1;
                }
                break;
            case 78:
                {
                alt36=2;
                }
                break;
            case 79:
                {
                alt36=3;
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
                    // TestSuite.g:613:5: 'oclUndefined' LPAREN t= type RPAREN
                    {
                    match(input,77,FOLLOW_77_in_undefinedLiteral2487); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_undefinedLiteral2489); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_undefinedLiteral2493);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_undefinedLiteral2495); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(t); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:616:5: 'Undefined'
                    {
                    match(input,78,FOLLOW_78_in_undefinedLiteral2509); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(); 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:619:5: 'null'
                    {
                    match(input,79,FOLLOW_79_in_undefinedLiteral2523); if (state.failed) return n;
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
    // TestSuite.g:628:1: tupleLiteral returns [ASTTupleLiteral n] : 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE ;
    public final ASTTupleLiteral tupleLiteral() throws RecognitionException {
        ASTTupleLiteral n = null;

        ASTTupleItem ti = null;


         List tiList = new ArrayList(); 
        try {
            // TestSuite.g:630:1: ( 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE )
            // TestSuite.g:631:5: 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE
            {
            match(input,80,FOLLOW_80_in_tupleLiteral2557); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_tupleLiteral2563); if (state.failed) return n;
            pushFollow(FOLLOW_tupleItem_in_tupleLiteral2571);
            ti=tupleItem();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tiList.add(ti); 
            }
            // TestSuite.g:634:5: ( COMMA ti= tupleItem )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==COMMA) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // TestSuite.g:634:7: COMMA ti= tupleItem
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleLiteral2582); if (state.failed) return n;
            	    pushFollow(FOLLOW_tupleItem_in_tupleLiteral2586);
            	    ti=tupleItem();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tiList.add(ti); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop37;
                }
            } while (true);

            match(input,RBRACE,FOLLOW_RBRACE_in_tupleLiteral2597); if (state.failed) return n;
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
    // TestSuite.g:642:1: tupleItem returns [ASTTupleItem n] : name= IDENT ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) ;
    public final ASTTupleItem tupleItem() throws RecognitionException {
        ASTTupleItem n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // TestSuite.g:643:1: (name= IDENT ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) )
            // TestSuite.g:644:5: name= IDENT ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tupleItem2628); if (state.failed) return n;
            // TestSuite.g:645:5: ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==COLON) ) {
                int LA38_1 = input.LA(2);

                if ( (synpred1_TestSuite()) ) {
                    alt38=1;
                }
                else if ( (true) ) {
                    alt38=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 38, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA38_0==EQUAL) ) {
                alt38=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 38, 0, input);

                throw nvae;
            }
            switch (alt38) {
                case 1 :
                    // TestSuite.g:648:7: ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression
                    {
                    match(input,COLON,FOLLOW_COLON_in_tupleItem2667); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_tupleItem2671);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,EQUAL,FOLLOW_EQUAL_in_tupleItem2673); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_tupleItem2677);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTTupleItem(name, t, e); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:651:7: ( COLON | EQUAL ) e= expression
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

                    pushFollow(FOLLOW_expression_in_tupleItem2709);
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
    // TestSuite.g:660:1: dateLiteral returns [ASTDateLiteral n] : 'Date' LBRACE v= STRING RBRACE ;
    public final ASTDateLiteral dateLiteral() throws RecognitionException {
        ASTDateLiteral n = null;

        Token v=null;

        try {
            // TestSuite.g:661:1: ( 'Date' LBRACE v= STRING RBRACE )
            // TestSuite.g:662:5: 'Date' LBRACE v= STRING RBRACE
            {
            match(input,81,FOLLOW_81_in_dateLiteral2754); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_dateLiteral2756); if (state.failed) return n;
            v=(Token)match(input,STRING,FOLLOW_STRING_in_dateLiteral2760); if (state.failed) return n;
            match(input,RBRACE,FOLLOW_RBRACE_in_dateLiteral2762); if (state.failed) return n;
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
    // TestSuite.g:672:1: type returns [ASTType n] : (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) ;
    public final ASTType type() throws RecognitionException {
        ASTType n = null;

        ASTSimpleType nTSimple = null;

        ASTCollectionType nTCollection = null;

        ASTTupleType nTTuple = null;


         Token tok = null; 
        try {
            // TestSuite.g:674:1: ( (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) )
            // TestSuite.g:675:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of type */ 
            }
            // TestSuite.g:676:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            int alt39=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt39=1;
                }
                break;
            case 72:
            case 73:
            case 74:
            case 75:
            case 82:
                {
                alt39=2;
                }
                break;
            case 80:
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
                    // TestSuite.g:677:7: nTSimple= simpleType
                    {
                    pushFollow(FOLLOW_simpleType_in_type2812);
                    nTSimple=simpleType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTSimple; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:678:7: nTCollection= collectionType
                    {
                    pushFollow(FOLLOW_collectionType_in_type2824);
                    nTCollection=collectionType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTCollection; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:679:7: nTTuple= tupleType
                    {
                    pushFollow(FOLLOW_tupleType_in_type2836);
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
    // TestSuite.g:684:1: typeOnly returns [ASTType n] : nT= type EOF ;
    public final ASTType typeOnly() throws RecognitionException {
        ASTType n = null;

        ASTType nT = null;


        try {
            // TestSuite.g:685:1: (nT= type EOF )
            // TestSuite.g:686:5: nT= type EOF
            {
            pushFollow(FOLLOW_type_in_typeOnly2868);
            nT=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_typeOnly2870); if (state.failed) return n;
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
    // TestSuite.g:696:1: simpleType returns [ASTSimpleType n] : name= IDENT ;
    public final ASTSimpleType simpleType() throws RecognitionException {
        ASTSimpleType n = null;

        Token name=null;

        try {
            // TestSuite.g:697:1: (name= IDENT )
            // TestSuite.g:698:5: name= IDENT
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_simpleType2898); if (state.failed) return n;
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
    // TestSuite.g:706:1: collectionType returns [ASTCollectionType n] : ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN ;
    public final ASTCollectionType collectionType() throws RecognitionException {
        ASTCollectionType n = null;

        ASTType elemType = null;


         Token op = null; 
        try {
            // TestSuite.g:708:1: ( ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN )
            // TestSuite.g:709:5: ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN
            {
            if ( state.backtracking==0 ) {
               op = input.LT(1); 
            }
            if ( (input.LA(1)>=72 && input.LA(1)<=75)||input.LA(1)==82 ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_collectionType2963); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_collectionType2967);
            elemType=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_collectionType2969); if (state.failed) return n;
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
    // TestSuite.g:719:1: tupleType returns [ASTTupleType n] : 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN ;
    public final ASTTupleType tupleType() throws RecognitionException {
        ASTTupleType n = null;

        ASTTuplePart tp = null;


         List tpList = new ArrayList(); 
        try {
            // TestSuite.g:721:1: ( 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN )
            // TestSuite.g:722:5: 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN
            {
            match(input,80,FOLLOW_80_in_tupleType3003); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_tupleType3005); if (state.failed) return n;
            pushFollow(FOLLOW_tuplePart_in_tupleType3014);
            tp=tuplePart();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tpList.add(tp); 
            }
            // TestSuite.g:724:5: ( COMMA tp= tuplePart )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==COMMA) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // TestSuite.g:724:7: COMMA tp= tuplePart
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleType3025); if (state.failed) return n;
            	    pushFollow(FOLLOW_tuplePart_in_tupleType3029);
            	    tp=tuplePart();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tpList.add(tp); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop40;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_tupleType3041); if (state.failed) return n;
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
    // TestSuite.g:733:1: tuplePart returns [ASTTuplePart n] : name= IDENT COLON t= type ;
    public final ASTTuplePart tuplePart() throws RecognitionException {
        ASTTuplePart n = null;

        Token name=null;
        ASTType t = null;


        try {
            // TestSuite.g:734:1: (name= IDENT COLON t= type )
            // TestSuite.g:735:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tuplePart3073); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_tuplePart3075); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_tuplePart3079);
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
    // TestSuite.g:743:1: cmdList returns [ASTCmdList cmdList] : c= cmd (c= cmd )* EOF ;
    public final ASTCmdList cmdList() throws RecognitionException {
        ASTCmdList cmdList = null;

        ASTCmd c = null;


         cmdList = new ASTCmdList(); 
        try {
            // TestSuite.g:745:1: (c= cmd (c= cmd )* EOF )
            // TestSuite.g:746:5: c= cmd (c= cmd )* EOF
            {
            pushFollow(FOLLOW_cmd_in_cmdList3115);
            c=cmd();

            state._fsp--;
            if (state.failed) return cmdList;
            if ( state.backtracking==0 ) {
               cmdList.add(c); 
            }
            // TestSuite.g:747:5: (c= cmd )*
            loop41:
            do {
                int alt41=2;
                int LA41_0 = input.LA(1);

                if ( (LA41_0==52||(LA41_0>=83 && LA41_0<=84)||(LA41_0>=86 && LA41_0<=87)||LA41_0==89||(LA41_0>=91 && LA41_0<=94)||(LA41_0>=97 && LA41_0<=98)) ) {
                    alt41=1;
                }


                switch (alt41) {
            	case 1 :
            	    // TestSuite.g:747:7: c= cmd
            	    {
            	    pushFollow(FOLLOW_cmd_in_cmdList3127);
            	    c=cmd();

            	    state._fsp--;
            	    if (state.failed) return cmdList;
            	    if ( state.backtracking==0 ) {
            	       cmdList.add(c); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop41;
                }
            } while (true);

            match(input,EOF,FOLLOW_EOF_in_cmdList3138); if (state.failed) return cmdList;

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
    // TestSuite.g:751:1: embeddedCmdList returns [ASTCmdList cmdList] : (c= cmd )+ ;
    public final ASTCmdList embeddedCmdList() throws RecognitionException {
        ASTCmdList cmdList = null;

        ASTCmd c = null;


         cmdList = new ASTCmdList(); 
        try {
            // TestSuite.g:753:1: ( (c= cmd )+ )
            // TestSuite.g:754:5: (c= cmd )+
            {
            // TestSuite.g:754:5: (c= cmd )+
            int cnt42=0;
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==52||(LA42_0>=83 && LA42_0<=84)||(LA42_0>=86 && LA42_0<=87)||LA42_0==89||(LA42_0>=91 && LA42_0<=94)||(LA42_0>=97 && LA42_0<=98)) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // TestSuite.g:754:7: c= cmd
            	    {
            	    pushFollow(FOLLOW_cmd_in_embeddedCmdList3167);
            	    c=cmd();

            	    state._fsp--;
            	    if (state.failed) return cmdList;
            	    if ( state.backtracking==0 ) {
            	       cmdList.add(c); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt42 >= 1 ) break loop42;
            	    if (state.backtracking>0) {state.failed=true; return cmdList;}
                        EarlyExitException eee =
                            new EarlyExitException(42, input);
                        throw eee;
                }
                cnt42++;
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
    // TestSuite.g:760:1: cmd returns [ASTCmd n] : stmt= cmdStmt ( SEMI )? ;
    public final ASTCmd cmd() throws RecognitionException {
        ASTCmd n = null;

        ASTCmd stmt = null;


        try {
            // TestSuite.g:761:1: (stmt= cmdStmt ( SEMI )? )
            // TestSuite.g:762:5: stmt= cmdStmt ( SEMI )?
            {
            pushFollow(FOLLOW_cmdStmt_in_cmd3200);
            stmt=cmdStmt();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = stmt; 
            }
            // TestSuite.g:762:35: ( SEMI )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==SEMI) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // TestSuite.g:762:37: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_cmd3205); if (state.failed) return n;

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
    // TestSuite.g:778:1: cmdStmt returns [ASTCmd n] : (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= showCmd | nC= hideCmd | nC= cropCmd ) ;
    public final ASTCmd cmdStmt() throws RecognitionException {
        ASTCmd n = null;

        ASTCmd nC = null;


        try {
            // TestSuite.g:779:1: ( (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= showCmd | nC= hideCmd | nC= cropCmd ) )
            // TestSuite.g:780:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= showCmd | nC= hideCmd | nC= cropCmd )
            {
            // TestSuite.g:780:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= showCmd | nC= hideCmd | nC= cropCmd )
            int alt44=13;
            alt44 = dfa44.predict(input);
            switch (alt44) {
                case 1 :
                    // TestSuite.g:781:7: nC= createCmd
                    {
                    pushFollow(FOLLOW_createCmd_in_cmdStmt3236);
                    nC=createCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 2 :
                    // TestSuite.g:782:7: nC= createAssignCmd
                    {
                    pushFollow(FOLLOW_createAssignCmd_in_cmdStmt3248);
                    nC=createAssignCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 3 :
                    // TestSuite.g:783:7: nC= createInsertCmd
                    {
                    pushFollow(FOLLOW_createInsertCmd_in_cmdStmt3261);
                    nC=createInsertCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 4 :
                    // TestSuite.g:784:7: nC= destroyCmd
                    {
                    pushFollow(FOLLOW_destroyCmd_in_cmdStmt3273);
                    nC=destroyCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 5 :
                    // TestSuite.g:785:7: nC= insertCmd
                    {
                    pushFollow(FOLLOW_insertCmd_in_cmdStmt3285);
                    nC=insertCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 6 :
                    // TestSuite.g:786:7: nC= deleteCmd
                    {
                    pushFollow(FOLLOW_deleteCmd_in_cmdStmt3297);
                    nC=deleteCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 7 :
                    // TestSuite.g:787:7: nC= setCmd
                    {
                    pushFollow(FOLLOW_setCmd_in_cmdStmt3309);
                    nC=setCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 8 :
                    // TestSuite.g:788:7: nC= opEnterCmd
                    {
                    pushFollow(FOLLOW_opEnterCmd_in_cmdStmt3321);
                    nC=opEnterCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 9 :
                    // TestSuite.g:789:7: nC= opExitCmd
                    {
                    pushFollow(FOLLOW_opExitCmd_in_cmdStmt3333);
                    nC=opExitCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 10 :
                    // TestSuite.g:790:7: nC= letCmd
                    {
                    pushFollow(FOLLOW_letCmd_in_cmdStmt3345);
                    nC=letCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 11 :
                    // TestSuite.g:791:7: nC= showCmd
                    {
                    pushFollow(FOLLOW_showCmd_in_cmdStmt3357);
                    nC=showCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 12 :
                    // TestSuite.g:792:7: nC= hideCmd
                    {
                    pushFollow(FOLLOW_hideCmd_in_cmdStmt3369);
                    nC=hideCmd();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 13 :
                    // TestSuite.g:793:7: nC= cropCmd
                    {
                    pushFollow(FOLLOW_cropCmd_in_cmdStmt3381);
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
    // TestSuite.g:803:1: createCmd returns [ASTCmd n] : 'create' nIdList= idList COLON t= simpleType ;
    public final ASTCmd createCmd() throws RecognitionException {
        ASTCmd n = null;

        List nIdList = null;

        ASTSimpleType t = null;


        try {
            // TestSuite.g:804:1: ( 'create' nIdList= idList COLON t= simpleType )
            // TestSuite.g:805:5: 'create' nIdList= idList COLON t= simpleType
            {
            match(input,83,FOLLOW_83_in_createCmd3410); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createCmd3414);
            nIdList=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_createCmd3421); if (state.failed) return n;
            pushFollow(FOLLOW_simpleType_in_createCmd3425);
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
    // TestSuite.g:815:1: createAssignCmd returns [ASTCmd n] : 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType ;
    public final ASTCmd createAssignCmd() throws RecognitionException {
        ASTCmd n = null;

        List nIdList = null;

        ASTSimpleType t = null;


        try {
            // TestSuite.g:816:1: ( 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType )
            // TestSuite.g:817:5: 'assign' nIdList= idList COLON_EQUAL 'create' t= simpleType
            {
            match(input,84,FOLLOW_84_in_createAssignCmd3454); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createAssignCmd3458);
            nIdList=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_createAssignCmd3460); if (state.failed) return n;
            match(input,83,FOLLOW_83_in_createAssignCmd3462); if (state.failed) return n;
            pushFollow(FOLLOW_simpleType_in_createAssignCmd3466);
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
    // TestSuite.g:825:1: createInsertCmd returns [ASTCmd n] : 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN ;
    public final ASTCmd createInsertCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        Token idAssoc=null;
        List idListInsert = null;


        try {
            // TestSuite.g:826:1: ( 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN )
            // TestSuite.g:827:5: 'create' id= IDENT COLON idAssoc= IDENT 'between' LPAREN idListInsert= idList RPAREN
            {
            match(input,83,FOLLOW_83_in_createInsertCmd3486); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_createInsertCmd3490); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_createInsertCmd3492); if (state.failed) return n;
            idAssoc=(Token)match(input,IDENT,FOLLOW_IDENT_in_createInsertCmd3496); if (state.failed) return n;
            match(input,85,FOLLOW_85_in_createInsertCmd3502); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_createInsertCmd3504); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_createInsertCmd3508);
            idListInsert=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_createInsertCmd3510); if (state.failed) return n;
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
    // TestSuite.g:838:1: destroyCmd returns [ASTCmd n] : 'destroy' e= expression ( COMMA e= expression )* ;
    public final ASTCmd destroyCmd() throws RecognitionException {
        ASTCmd n = null;

        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // TestSuite.g:840:1: ( 'destroy' e= expression ( COMMA e= expression )* )
            // TestSuite.g:841:6: 'destroy' e= expression ( COMMA e= expression )*
            {
            match(input,86,FOLLOW_86_in_destroyCmd3546); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_destroyCmd3550);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // TestSuite.g:842:16: ( COMMA e= expression )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==COMMA) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // TestSuite.g:842:18: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_destroyCmd3572); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_destroyCmd3576);
            	    e=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       exprList.add(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop45;
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
    // TestSuite.g:852:1: insertCmd returns [ASTCmd n] : 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT ;
    public final ASTCmd insertCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // TestSuite.g:854:1: ( 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT )
            // TestSuite.g:855:5: 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT
            {
            match(input,87,FOLLOW_87_in_insertCmd3615); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_insertCmd3617); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_insertCmd3626);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_insertCmd3630); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_insertCmd3638);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // TestSuite.g:857:42: ( COMMA e= expression )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==COMMA) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // TestSuite.g:857:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_insertCmd3644); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_insertCmd3648);
            	    e=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       exprList.add(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop46;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_insertCmd3660); if (state.failed) return n;
            match(input,88,FOLLOW_88_in_insertCmd3662); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_insertCmd3666); if (state.failed) return n;
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
    // TestSuite.g:868:1: deleteCmd returns [ASTCmd n] : 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT ;
    public final ASTCmd deleteCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // TestSuite.g:870:1: ( 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT )
            // TestSuite.g:871:5: 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT
            {
            match(input,89,FOLLOW_89_in_deleteCmd3701); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_deleteCmd3703); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_deleteCmd3711);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_deleteCmd3715); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_deleteCmd3723);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // TestSuite.g:873:42: ( COMMA e= expression )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==COMMA) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // TestSuite.g:873:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_deleteCmd3729); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_deleteCmd3733);
            	    e=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       exprList.add(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop47;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_deleteCmd3744); if (state.failed) return n;
            match(input,90,FOLLOW_90_in_deleteCmd3746); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_deleteCmd3750); if (state.failed) return n;
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
    // TestSuite.g:887:1: setCmd returns [ASTCmd n] : 'set' e1= expression COLON_EQUAL e2= expression ;
    public final ASTCmd setCmd() throws RecognitionException {
        ASTCmd n = null;

        ASTExpression e1 = null;

        ASTExpression e2 = null;


        try {
            // TestSuite.g:888:1: ( 'set' e1= expression COLON_EQUAL e2= expression )
            // TestSuite.g:889:5: 'set' e1= expression COLON_EQUAL e2= expression
            {
            match(input,91,FOLLOW_91_in_setCmd3780); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_setCmd3784);
            e1=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_setCmd3786); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_setCmd3790);
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
    // TestSuite.g:901:1: opEnterCmd returns [ASTCmd n] : 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN ;
    public final ASTCmd opEnterCmd() throws RecognitionException {
        ASTCmd n = null;

        Token id=null;
        ASTExpression e = null;


        ASTOpEnterCmd nOpEnter = null;
        try {
            // TestSuite.g:903:1: ( 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )
            // TestSuite.g:904:5: 'openter' e= expression id= IDENT LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
            {
            match(input,92,FOLLOW_92_in_opEnterCmd3824); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_opEnterCmd3833);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_opEnterCmd3837); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               nOpEnter = new ASTOpEnterCmd(e, id); n = nOpEnter;
            }
            match(input,LPAREN,FOLLOW_LPAREN_in_opEnterCmd3845); if (state.failed) return n;
            // TestSuite.g:907:5: (e= expression ( COMMA e= expression )* )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==IDENT||LA49_0==LPAREN||(LA49_0>=PLUS && LA49_0<=MINUS)||(LA49_0>=INT && LA49_0<=HASH)||LA49_0==52||LA49_0==59||(LA49_0>=62 && LA49_0<=66)||(LA49_0>=70 && LA49_0<=81)) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // TestSuite.g:907:7: e= expression ( COMMA e= expression )*
                    {
                    pushFollow(FOLLOW_expression_in_opEnterCmd3855);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       nOpEnter.addArg(e); 
                    }
                    // TestSuite.g:907:47: ( COMMA e= expression )*
                    loop48:
                    do {
                        int alt48=2;
                        int LA48_0 = input.LA(1);

                        if ( (LA48_0==COMMA) ) {
                            alt48=1;
                        }


                        switch (alt48) {
                    	case 1 :
                    	    // TestSuite.g:907:49: COMMA e= expression
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_opEnterCmd3861); if (state.failed) return n;
                    	    pushFollow(FOLLOW_expression_in_opEnterCmd3865);
                    	    e=expression();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       nOpEnter.addArg(e); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop48;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_opEnterCmd3879); if (state.failed) return n;

            }

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
    // TestSuite.g:917:1: opExitCmd returns [ASTCmd n] : 'opexit' ( ( expression )=>e= expression | ) ;
    public final ASTCmd opExitCmd() throws RecognitionException {
        ASTCmd n = null;

        ASTExpression e = null;


        try {
            // TestSuite.g:918:1: ( 'opexit' ( ( expression )=>e= expression | ) )
            // TestSuite.g:919:5: 'opexit' ( ( expression )=>e= expression | )
            {
            match(input,93,FOLLOW_93_in_opExitCmd3903); if (state.failed) return n;
            // TestSuite.g:919:14: ( ( expression )=>e= expression | )
            int alt50=2;
            alt50 = dfa50.predict(input);
            switch (alt50) {
                case 1 :
                    // TestSuite.g:919:15: ( expression )=>e= expression
                    {
                    pushFollow(FOLLOW_expression_in_opExitCmd3913);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 2 :
                    // TestSuite.g:919:45: 
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
    // TestSuite.g:928:1: letCmd returns [ASTCmd n] : 'let' name= IDENT ( COLON t= type )? EQUAL e= expression ;
    public final ASTCmd letCmd() throws RecognitionException {
        ASTCmd n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // TestSuite.g:929:1: ( 'let' name= IDENT ( COLON t= type )? EQUAL e= expression )
            // TestSuite.g:930:5: 'let' name= IDENT ( COLON t= type )? EQUAL e= expression
            {
            match(input,52,FOLLOW_52_in_letCmd3946); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_letCmd3950); if (state.failed) return n;
            // TestSuite.g:930:22: ( COLON t= type )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==COLON) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // TestSuite.g:930:24: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_letCmd3954); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_letCmd3958);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            match(input,EQUAL,FOLLOW_EQUAL_in_letCmd3963); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_letCmd3967);
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
    // TestSuite.g:937:1: hideCmd returns [ASTCmd n] : 'hide' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) ;
    public final ASTCmd hideCmd() throws RecognitionException {
        ASTCmd n = null;

        Token classname=null;
        Token ass=null;
        List objList = null;


        try {
            // TestSuite.g:938:1: ( 'hide' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) )
            // TestSuite.g:939:2: 'hide' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            {
            match(input,94,FOLLOW_94_in_hideCmd3994); if (state.failed) return n;
            // TestSuite.g:939:9: ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            int alt53=3;
            switch ( input.LA(1) ) {
            case 95:
                {
                alt53=1;
                }
                break;
            case IDENT:
                {
                alt53=2;
                }
                break;
            case 96:
                {
                alt53=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 53, 0, input);

                throw nvae;
            }

            switch (alt53) {
                case 1 :
                    // TestSuite.g:940:6: 'all'
                    {
                    match(input,95,FOLLOW_95_in_hideCmd4003); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideAllCmd(Mode.HIDE); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:941:6: objList= idList ( COLON classname= IDENT )?
                    {
                    pushFollow(FOLLOW_idList_in_hideCmd4016);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    // TestSuite.g:941:23: ( COLON classname= IDENT )?
                    int alt52=2;
                    int LA52_0 = input.LA(1);

                    if ( (LA52_0==COLON) ) {
                        alt52=1;
                    }
                    switch (alt52) {
                        case 1 :
                            // TestSuite.g:941:24: COLON classname= IDENT
                            {
                            match(input,COLON,FOLLOW_COLON_in_hideCmd4019); if (state.failed) return n;
                            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_hideCmd4025); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideCropObjectsCmd(Mode.HIDE, objList, classname); 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:942:6: 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT
                    {
                    match(input,96,FOLLOW_96_in_hideCmd4036); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_hideCmd4038); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_hideCmd4044);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_hideCmd4046); if (state.failed) return n;
                    match(input,90,FOLLOW_90_in_hideCmd4048); if (state.failed) return n;
                    ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_hideCmd4052); if (state.failed) return n;
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
    // TestSuite.g:948:1: showCmd returns [ASTCmd n] : 'show' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) ;
    public final ASTCmd showCmd() throws RecognitionException {
        ASTCmd n = null;

        Token classname=null;
        Token ass=null;
        List objList = null;


        try {
            // TestSuite.g:949:1: ( 'show' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) )
            // TestSuite.g:950:2: 'show' ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            {
            match(input,97,FOLLOW_97_in_showCmd4077); if (state.failed) return n;
            // TestSuite.g:950:9: ( 'all' | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            int alt55=3;
            switch ( input.LA(1) ) {
            case 95:
                {
                alt55=1;
                }
                break;
            case IDENT:
                {
                alt55=2;
                }
                break;
            case 96:
                {
                alt55=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 55, 0, input);

                throw nvae;
            }

            switch (alt55) {
                case 1 :
                    // TestSuite.g:951:6: 'all'
                    {
                    match(input,95,FOLLOW_95_in_showCmd4086); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideAllCmd(Mode.SHOW); 
                    }

                    }
                    break;
                case 2 :
                    // TestSuite.g:952:6: objList= idList ( COLON classname= IDENT )?
                    {
                    pushFollow(FOLLOW_idList_in_showCmd4099);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    // TestSuite.g:952:23: ( COLON classname= IDENT )?
                    int alt54=2;
                    int LA54_0 = input.LA(1);

                    if ( (LA54_0==COLON) ) {
                        alt54=1;
                    }
                    switch (alt54) {
                        case 1 :
                            // TestSuite.g:952:24: COLON classname= IDENT
                            {
                            match(input,COLON,FOLLOW_COLON_in_showCmd4102); if (state.failed) return n;
                            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_showCmd4108); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideCropObjectsCmd(Mode.SHOW, objList, classname); 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:953:6: 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT
                    {
                    match(input,96,FOLLOW_96_in_showCmd4119); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_showCmd4121); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_showCmd4127);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_showCmd4129); if (state.failed) return n;
                    match(input,90,FOLLOW_90_in_showCmd4131); if (state.failed) return n;
                    ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_showCmd4135); if (state.failed) return n;
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
    // TestSuite.g:959:1: cropCmd returns [ASTCmd n] : 'crop' ( | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) ;
    public final ASTCmd cropCmd() throws RecognitionException {
        ASTCmd n = null;

        Token classname=null;
        Token ass=null;
        List objList = null;


        try {
            // TestSuite.g:960:1: ( 'crop' ( | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT ) )
            // TestSuite.g:961:2: 'crop' ( | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            {
            match(input,98,FOLLOW_98_in_cropCmd4160); if (state.failed) return n;
            // TestSuite.g:961:9: ( | objList= idList ( COLON classname= IDENT )? | 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT )
            int alt57=3;
            switch ( input.LA(1) ) {
            case EOF:
            case SEMI:
            case 48:
            case 49:
            case 51:
            case 52:
            case 83:
            case 84:
            case 86:
            case 87:
            case 89:
            case 91:
            case 92:
            case 93:
            case 94:
            case 97:
            case 98:
                {
                alt57=1;
                }
                break;
            case IDENT:
                {
                alt57=2;
                }
                break;
            case 96:
                {
                alt57=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 57, 0, input);

                throw nvae;
            }

            switch (alt57) {
                case 1 :
                    // TestSuite.g:962:4: 
                    {
                    }
                    break;
                case 2 :
                    // TestSuite.g:962:6: objList= idList ( COLON classname= IDENT )?
                    {
                    pushFollow(FOLLOW_idList_in_cropCmd4173);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    // TestSuite.g:962:23: ( COLON classname= IDENT )?
                    int alt56=2;
                    int LA56_0 = input.LA(1);

                    if ( (LA56_0==COLON) ) {
                        alt56=1;
                    }
                    switch (alt56) {
                        case 1 :
                            // TestSuite.g:962:24: COLON classname= IDENT
                            {
                            match(input,COLON,FOLLOW_COLON_in_cropCmd4176); if (state.failed) return n;
                            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_cropCmd4182); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTShowHideCropObjectsCmd(Mode.CROP, objList, classname); 
                    }

                    }
                    break;
                case 3 :
                    // TestSuite.g:963:6: 'link' LPAREN objList= idList RPAREN 'from' ass= IDENT
                    {
                    match(input,96,FOLLOW_96_in_cropCmd4193); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_cropCmd4195); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_cropCmd4201);
                    objList=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_cropCmd4203); if (state.failed) return n;
                    match(input,90,FOLLOW_90_in_cropCmd4205); if (state.failed) return n;
                    ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_cropCmd4209); if (state.failed) return n;
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

    // $ANTLR start synpred1_TestSuite
    public final void synpred1_TestSuite_fragment() throws RecognitionException {   
        // TestSuite.g:648:7: ( COLON IDENT EQUAL )
        // TestSuite.g:648:8: COLON IDENT EQUAL
        {
        match(input,COLON,FOLLOW_COLON_in_synpred1_TestSuite2658); if (state.failed) return ;
        match(input,IDENT,FOLLOW_IDENT_in_synpred1_TestSuite2660); if (state.failed) return ;
        match(input,EQUAL,FOLLOW_EQUAL_in_synpred1_TestSuite2662); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_TestSuite

    // $ANTLR start synpred2_TestSuite
    public final void synpred2_TestSuite_fragment() throws RecognitionException {   
        // TestSuite.g:919:15: ( expression )
        // TestSuite.g:919:16: expression
        {
        pushFollow(FOLLOW_expression_in_synpred2_TestSuite3907);
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


    protected DFA44 dfa44 = new DFA44(this);
    protected DFA50 dfa50 = new DFA50(this);
    static final String DFA44_eotS =
        "\22\uffff";
    static final String DFA44_eofS =
        "\20\uffff\1\17\1\uffff";
    static final String DFA44_minS =
        "\1\64\1\4\13\uffff\1\5\1\4\1\uffff\1\27\1\uffff";
    static final String DFA44_maxS =
        "\1\142\1\4\13\uffff\1\10\1\4\1\uffff\1\142\1\uffff";
    static final String DFA44_acceptS =
        "\2\uffff\1\2\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\2\uffff"+
        "\1\1\1\uffff\1\3";
    static final String DFA44_specialS =
        "\22\uffff}>";
    static final String[] DFA44_transitionS = {
            "\1\11\36\uffff\1\1\1\2\1\uffff\1\3\1\4\1\uffff\1\5\1\uffff"+
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
            "\1\16\2\uffff\1\17",
            "\1\20",
            "",
            "\1\17\30\uffff\2\17\1\uffff\2\17\36\uffff\2\17\1\21\2\17\1"+
            "\uffff\1\17\1\uffff\4\17\2\uffff\2\17",
            ""
    };

    static final short[] DFA44_eot = DFA.unpackEncodedString(DFA44_eotS);
    static final short[] DFA44_eof = DFA.unpackEncodedString(DFA44_eofS);
    static final char[] DFA44_min = DFA.unpackEncodedStringToUnsignedChars(DFA44_minS);
    static final char[] DFA44_max = DFA.unpackEncodedStringToUnsignedChars(DFA44_maxS);
    static final short[] DFA44_accept = DFA.unpackEncodedString(DFA44_acceptS);
    static final short[] DFA44_special = DFA.unpackEncodedString(DFA44_specialS);
    static final short[][] DFA44_transition;

    static {
        int numStates = DFA44_transitionS.length;
        DFA44_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA44_transition[i] = DFA.unpackEncodedString(DFA44_transitionS[i]);
        }
    }

    class DFA44 extends DFA {

        public DFA44(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 44;
            this.eot = DFA44_eot;
            this.eof = DFA44_eof;
            this.min = DFA44_min;
            this.max = DFA44_max;
            this.accept = DFA44_accept;
            this.special = DFA44_special;
            this.transition = DFA44_transition;
        }
        public String getDescription() {
            return "780:2: (nC= createCmd | nC= createAssignCmd | nC= createInsertCmd | nC= destroyCmd | nC= insertCmd | nC= deleteCmd | nC= setCmd | nC= opEnterCmd | nC= opExitCmd | nC= letCmd | nC= showCmd | nC= hideCmd | nC= cropCmd )";
        }
    }
    static final String DFA50_eotS =
        "\45\uffff";
    static final String DFA50_eofS =
        "\1\25\44\uffff";
    static final String DFA50_minS =
        "\1\4\1\0\43\uffff";
    static final String DFA50_maxS =
        "\1\142\1\0\43\uffff";
    static final String DFA50_acceptS =
        "\2\uffff\23\1\1\2\17\uffff";
    static final String DFA50_specialS =
        "\1\0\1\1\43\uffff}>";
    static final String[] DFA50_transitionS = {
            "\1\11\2\uffff\1\23\7\uffff\2\2\6\uffff\1\25\2\uffff\1\5\1\6"+
            "\1\7\1\10\22\uffff\2\25\1\uffff\1\25\1\1\6\uffff\1\2\2\uffff"+
            "\1\21\3\22\1\24\3\uffff\1\3\1\4\4\12\1\13\1\14\1\15\1\16\1\17"+
            "\1\20\1\uffff\2\25\1\uffff\2\25\1\uffff\1\25\1\uffff\4\25\2"+
            "\uffff\2\25",
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
            return "919:14: ( ( expression )=>e= expression | )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA50_0 = input.LA(1);

                         
                        int index50_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA50_0==52) ) {s = 1;}

                        else if ( ((LA50_0>=PLUS && LA50_0<=MINUS)||LA50_0==59) && (synpred2_TestSuite())) {s = 2;}

                        else if ( (LA50_0==70) && (synpred2_TestSuite())) {s = 3;}

                        else if ( (LA50_0==71) && (synpred2_TestSuite())) {s = 4;}

                        else if ( (LA50_0==INT) && (synpred2_TestSuite())) {s = 5;}

                        else if ( (LA50_0==REAL) && (synpred2_TestSuite())) {s = 6;}

                        else if ( (LA50_0==STRING) && (synpred2_TestSuite())) {s = 7;}

                        else if ( (LA50_0==HASH) && (synpred2_TestSuite())) {s = 8;}

                        else if ( (LA50_0==IDENT) && (synpred2_TestSuite())) {s = 9;}

                        else if ( ((LA50_0>=72 && LA50_0<=75)) && (synpred2_TestSuite())) {s = 10;}

                        else if ( (LA50_0==76) && (synpred2_TestSuite())) {s = 11;}

                        else if ( (LA50_0==77) && (synpred2_TestSuite())) {s = 12;}

                        else if ( (LA50_0==78) && (synpred2_TestSuite())) {s = 13;}

                        else if ( (LA50_0==79) && (synpred2_TestSuite())) {s = 14;}

                        else if ( (LA50_0==80) && (synpred2_TestSuite())) {s = 15;}

                        else if ( (LA50_0==81) && (synpred2_TestSuite())) {s = 16;}

                        else if ( (LA50_0==62) && (synpred2_TestSuite())) {s = 17;}

                        else if ( ((LA50_0>=63 && LA50_0<=65)) && (synpred2_TestSuite())) {s = 18;}

                        else if ( (LA50_0==LPAREN) && (synpred2_TestSuite())) {s = 19;}

                        else if ( (LA50_0==66) && (synpred2_TestSuite())) {s = 20;}

                        else if ( (LA50_0==EOF||LA50_0==SEMI||(LA50_0>=48 && LA50_0<=49)||LA50_0==51||(LA50_0>=83 && LA50_0<=84)||(LA50_0>=86 && LA50_0<=87)||LA50_0==89||(LA50_0>=91 && LA50_0<=94)||(LA50_0>=97 && LA50_0<=98)) ) {s = 21;}

                         
                        input.seek(index50_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA50_1 = input.LA(1);

                         
                        int index50_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_TestSuite()) ) {s = 20;}

                        else if ( (true) ) {s = 21;}

                         
                        input.seek(index50_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 50, _s, input);
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
    public static final BitSet FOLLOW_48_in_testSuite112 = new BitSet(new long[]{0x0010000000000000L,0x000000067AD80000L});
    public static final BitSet FOLLOW_cmd_in_testSuite118 = new BitSet(new long[]{0x0003000000000000L});
    public static final BitSet FOLLOW_49_in_testSuite124 = new BitSet(new long[]{0x0004800000000000L});
    public static final BitSet FOLLOW_testCases_in_testSuite145 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_testSuite154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_filename172 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_DOT_in_filename174 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_filename178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_testCase_in_testCases205 = new BitSet(new long[]{0x0004800000000002L});
    public static final BitSet FOLLOW_50_in_testCase224 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_testCase228 = new BitSet(new long[]{0x000B000000000000L});
    public static final BitSet FOLLOW_48_in_testCase242 = new BitSet(new long[]{0x0010000000000000L,0x000000067AD80000L});
    public static final BitSet FOLLOW_cmd_in_testCase248 = new BitSet(new long[]{0x000B000000000000L});
    public static final BitSet FOLLOW_51_in_testCase265 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_testCase269 = new BitSet(new long[]{0x000B000000000000L});
    public static final BitSet FOLLOW_49_in_testCase281 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_expressionOnly311 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_expressionOnly313 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_expression361 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_expression365 = new BitSet(new long[]{0x0000000000000060L});
    public static final BitSet FOLLOW_COLON_in_expression369 = new BitSet(new long[]{0x0000000000000010L,0x0000000000050F00L});
    public static final BitSet FOLLOW_type_in_expression373 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_EQUAL_in_expression378 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_expression382 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_expression384 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_conditionalImpliesExpression_in_expression409 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_paramList442 = new BitSet(new long[]{0x0000000000000210L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList459 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_COMMA_in_paramList471 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList475 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_RPAREN_in_paramList495 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_idList524 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_idList534 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_idList538 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_IDENT_in_variableDeclaration569 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_variableDeclaration571 = new BitSet(new long[]{0x0000000000000010L,0x0000000000050F00L});
    public static final BitSet FOLLOW_type_in_variableDeclaration575 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression611 = new BitSet(new long[]{0x0040000000000002L});
    public static final BitSet FOLLOW_54_in_conditionalImpliesExpression624 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression628 = new BitSet(new long[]{0x0040000000000002L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression673 = new BitSet(new long[]{0x0080000000000002L});
    public static final BitSet FOLLOW_55_in_conditionalOrExpression686 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression690 = new BitSet(new long[]{0x0080000000000002L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression734 = new BitSet(new long[]{0x0100000000000002L});
    public static final BitSet FOLLOW_56_in_conditionalXOrExpression747 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression751 = new BitSet(new long[]{0x0100000000000002L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression795 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_57_in_conditionalAndExpression808 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression812 = new BitSet(new long[]{0x0200000000000002L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression860 = new BitSet(new long[]{0x0000000000000442L});
    public static final BitSet FOLLOW_set_in_equalityExpression879 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression889 = new BitSet(new long[]{0x0000000000000442L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression938 = new BitSet(new long[]{0x0000000000007802L});
    public static final BitSet FOLLOW_set_in_relationalExpression956 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression974 = new BitSet(new long[]{0x0000000000007802L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1024 = new BitSet(new long[]{0x0000000000018002L});
    public static final BitSet FOLLOW_set_in_additiveExpression1042 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1052 = new BitSet(new long[]{0x0000000000018002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1102 = new BitSet(new long[]{0x0400000000060002L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression1120 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1134 = new BitSet(new long[]{0x0400000000060002L});
    public static final BitSet FOLLOW_set_in_unaryExpression1196 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression1220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpression_in_unaryExpression1240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_postfixExpression1273 = new BitSet(new long[]{0x0000000000180002L});
    public static final BitSet FOLLOW_ARROW_in_postfixExpression1291 = new BitSet(new long[]{0xC000000000000010L,0x0000000000000003L});
    public static final BitSet FOLLOW_DOT_in_postfixExpression1297 = new BitSet(new long[]{0xC000000000000010L,0x0000000000000003L});
    public static final BitSet FOLLOW_propertyCall_in_postfixExpression1308 = new BitSet(new long[]{0x0000000000180002L});
    public static final BitSet FOLLOW_literal_in_primaryExpression1348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyCall_in_primaryExpression1360 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression1371 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_primaryExpression1375 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression1377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifExpression_in_primaryExpression1389 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_primaryExpression1406 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_DOT_in_primaryExpression1408 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_primaryExpression1410 = new BitSet(new long[]{0x0000000000200082L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression1414 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression1416 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_AT_in_primaryExpression1437 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_primaryExpression1439 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_queryExpression_in_propertyCall1512 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterateExpression_in_propertyCall1525 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationExpression_in_propertyCall1538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeExpression_in_propertyCall1551 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_queryExpression1586 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_queryExpression1593 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_queryExpression1604 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_BAR_in_queryExpression1608 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_queryExpression1619 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_queryExpression1625 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_iterateExpression1657 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_iterateExpression1663 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_iterateExpression1671 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_SEMI_in_iterateExpression1673 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableInitialization_in_iterateExpression1681 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_BAR_in_iterateExpression1683 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_iterateExpression1691 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_iterateExpression1697 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression1741 = new BitSet(new long[]{0x0000000001200082L});
    public static final BitSet FOLLOW_LBRACK_in_operationExpression1757 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression1761 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_RBRACK_in_operationExpression1763 = new BitSet(new long[]{0x0000000000200082L});
    public static final BitSet FOLLOW_AT_in_operationExpression1776 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_operationExpression1778 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_LPAREN_in_operationExpression1799 = new BitSet(new long[]{0xC81000003C018290L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_operationExpression1820 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_COMMA_in_operationExpression1832 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_operationExpression1836 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_RPAREN_in_operationExpression1856 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_typeExpression1899 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_typeExpression1915 = new BitSet(new long[]{0x0000000000000010L,0x0000000000050F00L});
    public static final BitSet FOLLOW_type_in_typeExpression1919 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_typeExpression1921 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_elemVarsDeclaration1960 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_COLON_in_elemVarsDeclaration1968 = new BitSet(new long[]{0x0000000000000010L,0x0000000000050F00L});
    public static final BitSet FOLLOW_type_in_elemVarsDeclaration1972 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableInitialization2007 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_variableInitialization2009 = new BitSet(new long[]{0x0000000000000010L,0x0000000000050F00L});
    public static final BitSet FOLLOW_type_in_variableInitialization2013 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_EQUAL_in_variableInitialization2015 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_variableInitialization2019 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_ifExpression2051 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_ifExpression2055 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_ifExpression2057 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_ifExpression2061 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_ifExpression2063 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_ifExpression2067 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_ifExpression2069 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_70_in_literal2108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_literal2122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_literal2135 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REAL_in_literal2150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal2164 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_literal2174 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_literal2178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_literal2190 = new BitSet(new long[]{0x0000004000000000L});
    public static final BitSet FOLLOW_COLON_COLON_in_literal2192 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_literal2196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionLiteral_in_literal2208 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyCollectionLiteral_in_literal2220 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedLiteral_in_literal2232 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleLiteral_in_literal2244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dateLiteral_in_literal2256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionLiteral2294 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_LBRACE_in_collectionLiteral2323 = new BitSet(new long[]{0xC8100000BC018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral2340 = new BitSet(new long[]{0x0000000080000100L});
    public static final BitSet FOLLOW_COMMA_in_collectionLiteral2353 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral2357 = new BitSet(new long[]{0x0000000080000100L});
    public static final BitSet FOLLOW_RBRACE_in_collectionLiteral2376 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_collectionItem2405 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_DOTDOT_in_collectionItem2416 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_collectionItem2420 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_emptyCollectionLiteral2449 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_emptyCollectionLiteral2451 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040F00L});
    public static final BitSet FOLLOW_collectionType_in_emptyCollectionLiteral2455 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_emptyCollectionLiteral2457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_undefinedLiteral2487 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_undefinedLiteral2489 = new BitSet(new long[]{0x0000000000000010L,0x0000000000050F00L});
    public static final BitSet FOLLOW_type_in_undefinedLiteral2493 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_undefinedLiteral2495 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_undefinedLiteral2509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_undefinedLiteral2523 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_tupleLiteral2557 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_LBRACE_in_tupleLiteral2563 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral2571 = new BitSet(new long[]{0x0000000080000100L});
    public static final BitSet FOLLOW_COMMA_in_tupleLiteral2582 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral2586 = new BitSet(new long[]{0x0000000080000100L});
    public static final BitSet FOLLOW_RBRACE_in_tupleLiteral2597 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tupleItem2628 = new BitSet(new long[]{0x0000000000000060L});
    public static final BitSet FOLLOW_COLON_in_tupleItem2667 = new BitSet(new long[]{0x0000000000000010L,0x0000000000050F00L});
    public static final BitSet FOLLOW_type_in_tupleItem2671 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_EQUAL_in_tupleItem2673 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_tupleItem2677 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_tupleItem2699 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_tupleItem2709 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_dateLiteral2754 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_LBRACE_in_dateLiteral2756 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_STRING_in_dateLiteral2760 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_RBRACE_in_dateLiteral2762 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleType_in_type2812 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_type2824 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleType_in_type2836 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeOnly2868 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_typeOnly2870 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_simpleType2898 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionType2936 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_collectionType2963 = new BitSet(new long[]{0x0000000000000010L,0x0000000000050F00L});
    public static final BitSet FOLLOW_type_in_collectionType2967 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_collectionType2969 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_80_in_tupleType3003 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_tupleType3005 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType3014 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_COMMA_in_tupleType3025 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType3029 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_RPAREN_in_tupleType3041 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tuplePart3073 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_tuplePart3075 = new BitSet(new long[]{0x0000000000000010L,0x0000000000050F00L});
    public static final BitSet FOLLOW_type_in_tuplePart3079 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cmd_in_cmdList3115 = new BitSet(new long[]{0x0010000000000000L,0x000000067AD80000L});
    public static final BitSet FOLLOW_cmd_in_cmdList3127 = new BitSet(new long[]{0x0010000000000000L,0x000000067AD80000L});
    public static final BitSet FOLLOW_EOF_in_cmdList3138 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cmd_in_embeddedCmdList3167 = new BitSet(new long[]{0x0010000000000002L,0x000000067AD80000L});
    public static final BitSet FOLLOW_cmdStmt_in_cmd3200 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_SEMI_in_cmd3205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createCmd_in_cmdStmt3236 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createAssignCmd_in_cmdStmt3248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createInsertCmd_in_cmdStmt3261 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_destroyCmd_in_cmdStmt3273 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_insertCmd_in_cmdStmt3285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_deleteCmd_in_cmdStmt3297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_setCmd_in_cmdStmt3309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_opEnterCmd_in_cmdStmt3321 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_opExitCmd_in_cmdStmt3333 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_letCmd_in_cmdStmt3345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_showCmd_in_cmdStmt3357 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hideCmd_in_cmdStmt3369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cropCmd_in_cmdStmt3381 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_createCmd3410 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_createCmd3414 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_createCmd3421 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_simpleType_in_createCmd3425 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_createAssignCmd3454 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_createAssignCmd3458 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_createAssignCmd3460 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_createAssignCmd3462 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_simpleType_in_createAssignCmd3466 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_createInsertCmd3486 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_createInsertCmd3490 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_createInsertCmd3492 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_createInsertCmd3496 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_createInsertCmd3502 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_createInsertCmd3504 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_createInsertCmd3508 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_createInsertCmd3510 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_86_in_destroyCmd3546 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_destroyCmd3550 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_destroyCmd3572 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_destroyCmd3576 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_87_in_insertCmd3615 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_insertCmd3617 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_insertCmd3626 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_insertCmd3630 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_insertCmd3638 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_COMMA_in_insertCmd3644 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_insertCmd3648 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_RPAREN_in_insertCmd3660 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_88_in_insertCmd3662 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_insertCmd3666 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_deleteCmd3701 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_deleteCmd3703 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_deleteCmd3711 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_deleteCmd3715 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_deleteCmd3723 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_COMMA_in_deleteCmd3729 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_deleteCmd3733 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_RPAREN_in_deleteCmd3744 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_deleteCmd3746 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_deleteCmd3750 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_91_in_setCmd3780 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_setCmd3784 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_setCmd3786 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_setCmd3790 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_opEnterCmd3824 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_opEnterCmd3833 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_opEnterCmd3837 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_opEnterCmd3845 = new BitSet(new long[]{0xC81000003C018290L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_opEnterCmd3855 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_COMMA_in_opEnterCmd3861 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_opEnterCmd3865 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_RPAREN_in_opEnterCmd3879 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_opExitCmd3903 = new BitSet(new long[]{0xC81000003C018092L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_opExitCmd3913 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_52_in_letCmd3946 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_letCmd3950 = new BitSet(new long[]{0x0000000000000060L});
    public static final BitSet FOLLOW_COLON_in_letCmd3954 = new BitSet(new long[]{0x0000000000000010L,0x0000000000050F00L});
    public static final BitSet FOLLOW_type_in_letCmd3958 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_EQUAL_in_letCmd3963 = new BitSet(new long[]{0xC81000003C018090L,0x000000000003FFC7L});
    public static final BitSet FOLLOW_expression_in_letCmd3967 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_hideCmd3994 = new BitSet(new long[]{0x0000000000000010L,0x0000000180000000L});
    public static final BitSet FOLLOW_95_in_hideCmd4003 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_hideCmd4016 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_COLON_in_hideCmd4019 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_hideCmd4025 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_hideCmd4036 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_hideCmd4038 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_hideCmd4044 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_hideCmd4046 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_hideCmd4048 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_hideCmd4052 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_97_in_showCmd4077 = new BitSet(new long[]{0x0000000000000010L,0x0000000180000000L});
    public static final BitSet FOLLOW_95_in_showCmd4086 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_showCmd4099 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_COLON_in_showCmd4102 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_showCmd4108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_showCmd4119 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_showCmd4121 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_showCmd4127 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_showCmd4129 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_showCmd4131 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_showCmd4135 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_cropCmd4160 = new BitSet(new long[]{0x0000000000000012L,0x0000000100000000L});
    public static final BitSet FOLLOW_idList_in_cropCmd4173 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_COLON_in_cropCmd4176 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_cropCmd4182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_cropCmd4193 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_cropCmd4195 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_cropCmd4201 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_cropCmd4203 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_cropCmd4205 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_cropCmd4209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_synpred1_TestSuite2658 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_synpred1_TestSuite2660 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_EQUAL_in_synpred1_TestSuite2662 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_synpred2_TestSuite3907 = new BitSet(new long[]{0x0000000000000002L});

}