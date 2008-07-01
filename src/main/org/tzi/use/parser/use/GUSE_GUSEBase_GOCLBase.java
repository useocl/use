// $ANTLR 3.1b1 GOCLBase.g 2008-07-01 13:26:35
 
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

package org.tzi.use.parser.use; 

// ------------------------------------
//  USE parser
// ------------------------------------

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.PrintWriter;
import org.tzi.use.parser.ParseErrorHandler;
import org.tzi.use.parser.ocl.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class GUSE_GUSEBase_GOCLBase extends Parser {
    public static final int LITERAL_oclAsType=4;
    public static final int STAR=20;
    public static final int EOF=-1;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__91=91;
    public static final int RPAREN=9;
    public static final int T__92=92;
    public static final int GREATER=15;
    public static final int T__90=90;
    public static final int NOT_EQUAL=13;
    public static final int LITERAL_oclIsTypeOf=6;
    public static final int LESS=14;
    public static final int T__99=99;
    public static final int T__98=98;
    public static final int T__97=97;
    public static final int T__96=96;
    public static final int T__95=95;
    public static final int RBRACK=28;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int RBRACE=34;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int LITERAL_oclIsKindOf=5;
    public static final int INT=29;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int REAL=30;
    public static final int WS=39;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__70=70;
    public static final int SL_COMMENT=40;
    public static final int LESS_EQUAL=16;
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
    public static final int LBRACK=27;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int ESC=43;
    public static final int LBRACE=33;
    public static final int DOTDOT=35;
    public static final int T__61=61;
    public static final int T__60=60;
    public static final int LPAREN=7;
    public static final int T__55=55;
    public static final int AT=24;
    public static final int ML_COMMENT=41;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int COLON_EQUAL=37;
    public static final int T__51=51;
    public static final int SLASH=21;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int COMMA=8;
    public static final int T__59=59;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int EQUAL=12;
    public static final int IDENT=10;
    public static final int PLUS=18;
    public static final int RANGE_OR_INT=42;
    public static final int DOT=23;
    public static final int T__50=50;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int HASH=32;
    public static final int HEX_DIGIT=44;
    public static final int COLON_COLON=36;
    public static final int T__102=102;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int MINUS=19;
    public static final int SEMI=26;
    public static final int COLON=11;
    public static final int NEWLINE=38;
    public static final int VOCAB=45;
    public static final int ARROW=22;
    public static final int GREATER_EQUAL=17;
    public static final int BAR=25;
    public static final int STRING=31;

    // delegates
    // delegators
    public GUSEParser gGUSE;
    public GUSE_GUSEBase gGUSEBase;


        public GUSE_GUSEBase_GOCLBase(TokenStream input, GUSEParser gGUSE, GUSE_GUSEBase gGUSEBase) {
            this(input, new RecognizerSharedState(), gGUSE, gGUSEBase);
        }
        public GUSE_GUSEBase_GOCLBase(TokenStream input, RecognizerSharedState state, GUSEParser gGUSE, GUSE_GUSEBase gGUSEBase) {
            super(input, state);
            this.gGUSE = gGUSE;
            this.gGUSEBase = gGUSEBase;
        }
        

    public String[] getTokenNames() { return GUSEParser.tokenNames; }
    public String getGrammarFileName() { return "GOCLBase.g"; }

      
        final static String Q_COLLECT  = "collect";
        final static String Q_SELECT   = "select";
        final static String Q_REJECT   = "reject";
        final static String Q_FORALL   = "forAll";
        final static String Q_EXISTS   = "exists";
        final static String Q_ISUNIQUE = "isUnique";
        final static String Q_SORTEDBY = "sortedBy";
        final static String Q_ANY      = "any";
        final static String Q_ONE      = "one";
    
        final static int Q_COLLECT_ID  = 1;
        final static int Q_SELECT_ID   = 2;
        final static int Q_REJECT_ID   = 3;
        final static int Q_FORALL_ID   = 4;
        final static int Q_EXISTS_ID   = 5;
        final static int Q_ISUNIQUE_ID = 6;
        final static int Q_SORTEDBY_ID = 7;
        final static int Q_ANY_ID      = 8;
        final static int Q_ONE_ID      = 9;
    
        final static HashMap queryIdentMap = new HashMap();
    
        static {
            queryIdentMap.put(Q_COLLECT,  new Integer(Q_COLLECT_ID));
            queryIdentMap.put(Q_SELECT,   new Integer(Q_SELECT_ID));
            queryIdentMap.put(Q_REJECT,   new Integer(Q_REJECT_ID));
            queryIdentMap.put(Q_FORALL,   new Integer(Q_FORALL_ID));
            queryIdentMap.put(Q_EXISTS,   new Integer(Q_EXISTS_ID));
            queryIdentMap.put(Q_ISUNIQUE, new Integer(Q_ISUNIQUE_ID));
            queryIdentMap.put(Q_SORTEDBY, new Integer(Q_SORTEDBY_ID));
            queryIdentMap.put(Q_ANY,      new Integer(Q_ANY_ID));
            queryIdentMap.put(Q_ONE,      new Integer(Q_ONE_ID));
        }
    
        protected boolean isQueryIdent(Token t) {
            return queryIdentMap.containsKey(t.getText());
        }
        
        public void init(ParseErrorHandler handler) {
            fParseErrorHandler = handler;
        }
    
        /* Overridden methods. */
    	private ParseErrorHandler fParseErrorHandler;
        
        public void emitErrorMessage(String msg) {
           	fParseErrorHandler.reportError(msg);
    	}



    // $ANTLR start paramList
    // GOCLBase.g:122:1: paramList returns [List paramList] : LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN ;
    public final List paramList() throws RecognitionException {
        List paramList = null;

        ASTVariableDeclaration v = null;


         paramList = new ArrayList(); 
        try {
            // GOCLBase.g:124:1: ( LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN )
            // GOCLBase.g:125:5: LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN
            {
            match(input,LPAREN,FOLLOW_LPAREN_in_paramList76); 
            // GOCLBase.g:126:5: (v= variableDeclaration ( COMMA v= variableDeclaration )* )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==IDENT) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // GOCLBase.g:127:7: v= variableDeclaration ( COMMA v= variableDeclaration )*
                    {
                    pushFollow(FOLLOW_variableDeclaration_in_paramList93);
                    v=variableDeclaration();

                    state._fsp--;

                     paramList.add(v); 
                    // GOCLBase.g:128:7: ( COMMA v= variableDeclaration )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0==COMMA) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // GOCLBase.g:128:9: COMMA v= variableDeclaration
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_paramList105); 
                    	    pushFollow(FOLLOW_variableDeclaration_in_paramList109);
                    	    v=variableDeclaration();

                    	    state._fsp--;

                    	     paramList.add(v); 

                    	    }
                    	    break;

                    	default :
                    	    break loop1;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_paramList129); 

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
    // $ANTLR end paramList


    // $ANTLR start idList
    // GOCLBase.g:136:1: idList returns [List idList] : id0= IDENT ( COMMA idn= IDENT )* ;
    public final List idList() throws RecognitionException {
        List idList = null;

        Token id0=null;
        Token idn=null;

         idList = new ArrayList(); 
        try {
            // GOCLBase.g:138:1: (id0= IDENT ( COMMA idn= IDENT )* )
            // GOCLBase.g:139:5: id0= IDENT ( COMMA idn= IDENT )*
            {
            id0=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList158); 
             idList.add(id0); 
            // GOCLBase.g:140:5: ( COMMA idn= IDENT )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==COMMA) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // GOCLBase.g:140:7: COMMA idn= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_idList168); 
            	    idn=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList172); 
            	     idList.add(idn); 

            	    }
            	    break;

            	default :
            	    break loop3;
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
    // $ANTLR end idList


    // $ANTLR start variableDeclaration
    // GOCLBase.g:148:1: variableDeclaration returns [ASTVariableDeclaration n] : name= IDENT COLON t= type ;
    public final ASTVariableDeclaration variableDeclaration() throws RecognitionException {
        ASTVariableDeclaration n = null;

        Token name=null;
        ASTType t = null;


        try {
            // GOCLBase.g:149:1: (name= IDENT COLON t= type )
            // GOCLBase.g:150:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableDeclaration203); 
            match(input,COLON,FOLLOW_COLON_in_variableDeclaration205); 
            pushFollow(FOLLOW_type_in_variableDeclaration209);
            t=type();

            state._fsp--;

             n = new ASTVariableDeclaration(name, t); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end variableDeclaration


    // $ANTLR start expression
    // GOCLBase.g:158:1: expression returns [ASTExpression n] : ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression ;
    public final ASTExpression expression() throws RecognitionException {
        ASTExpression n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e1 = null;

        ASTExpression nCndImplies = null;


         
          ASTLetExpression prevLet = null, firstLet = null;
          ASTExpression e2;

        try {
            // GOCLBase.g:163:1: ( ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression )
            // GOCLBase.g:164:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression
            {
             Token tok = input.LT(1); /* remember start of expression */ 
            // GOCLBase.g:165:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==46) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // GOCLBase.g:166:7: 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in'
            	    {
            	    match(input,46,FOLLOW_46_in_expression257); 
            	    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_expression261); 
            	    // GOCLBase.g:166:24: ( COLON t= type )?
            	    int alt4=2;
            	    int LA4_0 = input.LA(1);

            	    if ( (LA4_0==COLON) ) {
            	        alt4=1;
            	    }
            	    switch (alt4) {
            	        case 1 :
            	            // GOCLBase.g:166:26: COLON t= type
            	            {
            	            match(input,COLON,FOLLOW_COLON_in_expression265); 
            	            pushFollow(FOLLOW_type_in_expression269);
            	            t=type();

            	            state._fsp--;


            	            }
            	            break;

            	    }

            	    match(input,EQUAL,FOLLOW_EQUAL_in_expression274); 
            	    pushFollow(FOLLOW_expression_in_expression278);
            	    e1=expression();

            	    state._fsp--;

            	    match(input,47,FOLLOW_47_in_expression280); 
            	     ASTLetExpression nextLet = new ASTLetExpression(name, t, e1);
            	             if ( firstLet == null ) 
            	                 firstLet = nextLet;
            	             if ( prevLet != null ) 
            	                 prevLet.setInExpr(nextLet);
            	             prevLet = nextLet;
            	           

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            pushFollow(FOLLOW_conditionalImpliesExpression_in_expression305);
            nCndImplies=conditionalImpliesExpression();

            state._fsp--;

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
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end expression


    // $ANTLR start conditionalImpliesExpression
    // GOCLBase.g:195:1: conditionalImpliesExpression returns [ASTExpression n] : nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* ;
    public final ASTExpression conditionalImpliesExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndOrExp = null;

        ASTExpression n1 = null;


        try {
            // GOCLBase.g:196:1: (nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* )
            // GOCLBase.g:197:5: nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )*
            {
            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression338);
            nCndOrExp=conditionalOrExpression();

            state._fsp--;

            n = nCndOrExp;
            // GOCLBase.g:198:5: (op= 'implies' n1= conditionalOrExpression )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==48) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // GOCLBase.g:198:7: op= 'implies' n1= conditionalOrExpression
            	    {
            	    op=(Token)match(input,48,FOLLOW_48_in_conditionalImpliesExpression351); 
            	    pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression355);
            	    n1=conditionalOrExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

            	    }
            	    break;

            	default :
            	    break loop6;
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
    // $ANTLR end conditionalImpliesExpression


    // $ANTLR start conditionalOrExpression
    // GOCLBase.g:208:1: conditionalOrExpression returns [ASTExpression n] : nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* ;
    public final ASTExpression conditionalOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndXorExp = null;

        ASTExpression n1 = null;


        try {
            // GOCLBase.g:209:1: (nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* )
            // GOCLBase.g:210:5: nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )*
            {
            pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression401);
            nCndXorExp=conditionalXOrExpression();

            state._fsp--;

            n = nCndXorExp;
            // GOCLBase.g:211:5: (op= 'or' n1= conditionalXOrExpression )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==49) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // GOCLBase.g:211:7: op= 'or' n1= conditionalXOrExpression
            	    {
            	    op=(Token)match(input,49,FOLLOW_49_in_conditionalOrExpression414); 
            	    pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression418);
            	    n1=conditionalXOrExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

            	    }
            	    break;

            	default :
            	    break loop7;
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
    // $ANTLR end conditionalOrExpression


    // $ANTLR start conditionalXOrExpression
    // GOCLBase.g:221:1: conditionalXOrExpression returns [ASTExpression n] : nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* ;
    public final ASTExpression conditionalXOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndAndExp = null;

        ASTExpression n1 = null;


        try {
            // GOCLBase.g:222:1: (nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* )
            // GOCLBase.g:223:5: nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )*
            {
            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression463);
            nCndAndExp=conditionalAndExpression();

            state._fsp--;

            n = nCndAndExp;
            // GOCLBase.g:224:5: (op= 'xor' n1= conditionalAndExpression )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==50) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // GOCLBase.g:224:7: op= 'xor' n1= conditionalAndExpression
            	    {
            	    op=(Token)match(input,50,FOLLOW_50_in_conditionalXOrExpression476); 
            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression480);
            	    n1=conditionalAndExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

            	    }
            	    break;

            	default :
            	    break loop8;
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
    // $ANTLR end conditionalXOrExpression


    // $ANTLR start conditionalAndExpression
    // GOCLBase.g:234:1: conditionalAndExpression returns [ASTExpression n] : nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* ;
    public final ASTExpression conditionalAndExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nEqExp = null;

        ASTExpression n1 = null;


        try {
            // GOCLBase.g:235:1: (nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* )
            // GOCLBase.g:236:5: nEqExp= equalityExpression (op= 'and' n1= equalityExpression )*
            {
            pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression525);
            nEqExp=equalityExpression();

            state._fsp--;

            n = nEqExp;
            // GOCLBase.g:237:5: (op= 'and' n1= equalityExpression )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==51) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // GOCLBase.g:237:7: op= 'and' n1= equalityExpression
            	    {
            	    op=(Token)match(input,51,FOLLOW_51_in_conditionalAndExpression538); 
            	    pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression542);
            	    n1=equalityExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

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
        return n;
    }
    // $ANTLR end conditionalAndExpression


    // $ANTLR start equalityExpression
    // GOCLBase.g:247:1: equalityExpression returns [ASTExpression n] : nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* ;
    public final ASTExpression equalityExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nRelExp = null;

        ASTExpression n1 = null;


        try {
            // GOCLBase.g:248:1: (nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* )
            // GOCLBase.g:249:5: nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            {
            pushFollow(FOLLOW_relationalExpression_in_equalityExpression586);
            nRelExp=relationalExpression();

            state._fsp--;

            n = nRelExp;
            // GOCLBase.g:250:5: ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( ((LA10_0>=EQUAL && LA10_0<=NOT_EQUAL)) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // GOCLBase.g:250:7: ( EQUAL | NOT_EQUAL ) n1= relationalExpression
            	    {
            	     Token op = input.LT(1); 
            	    if ( (input.LA(1)>=EQUAL && input.LA(1)<=NOT_EQUAL) ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression615);
            	    n1=relationalExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

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
    // $ANTLR end equalityExpression


    // $ANTLR start relationalExpression
    // GOCLBase.g:261:1: relationalExpression returns [ASTExpression n] : nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* ;
    public final ASTExpression relationalExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nAddiExp = null;

        ASTExpression n1 = null;


        try {
            // GOCLBase.g:262:1: (nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* )
            // GOCLBase.g:263:5: nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            {
            pushFollow(FOLLOW_additiveExpression_in_relationalExpression660);
            nAddiExp=additiveExpression();

            state._fsp--;

            n = nAddiExp;
            // GOCLBase.g:264:5: ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>=LESS && LA11_0<=GREATER_EQUAL)) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // GOCLBase.g:264:7: ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression
            	    {
            	     Token op = input.LT(1); 
            	    if ( (input.LA(1)>=LESS && input.LA(1)<=GREATER_EQUAL) ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_additiveExpression_in_relationalExpression696);
            	    n1=additiveExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

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
    // $ANTLR end relationalExpression


    // $ANTLR start additiveExpression
    // GOCLBase.g:275:1: additiveExpression returns [ASTExpression n] : nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* ;
    public final ASTExpression additiveExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nMulExp = null;

        ASTExpression n1 = null;


        try {
            // GOCLBase.g:276:1: (nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* )
            // GOCLBase.g:277:5: nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            {
            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression742);
            nMulExp=multiplicativeExpression();

            state._fsp--;

            n = nMulExp;
            // GOCLBase.g:278:5: ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0>=PLUS && LA12_0<=MINUS)) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // GOCLBase.g:278:7: ( PLUS | MINUS ) n1= multiplicativeExpression
            	    {
            	     Token op = input.LT(1); 
            	    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS) ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression770);
            	    n1=multiplicativeExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

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
    // $ANTLR end additiveExpression


    // $ANTLR start multiplicativeExpression
    // GOCLBase.g:289:1: multiplicativeExpression returns [ASTExpression n] : nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* ;
    public final ASTExpression multiplicativeExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression n1 = null;


        try {
            // GOCLBase.g:290:1: (nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* )
            // GOCLBase.g:291:5: nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            {
            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression815);
            nUnExp=unaryExpression();

            state._fsp--;

             n = nUnExp;
            // GOCLBase.g:292:5: ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0>=STAR && LA13_0<=SLASH)||LA13_0==52) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // GOCLBase.g:292:7: ( STAR | SLASH | 'div' ) n1= unaryExpression
            	    {
            	     Token op = input.LT(1); 
            	    if ( (input.LA(1)>=STAR && input.LA(1)<=SLASH)||input.LA(1)==52 ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression847);
            	    n1=unaryExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

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
    // $ANTLR end multiplicativeExpression


    // $ANTLR start unaryExpression
    // GOCLBase.g:304:1: unaryExpression returns [ASTExpression n] : ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression );
    public final ASTExpression unaryExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression nPosExp = null;


        try {
            // GOCLBase.g:305:1: ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0>=PLUS && LA14_0<=MINUS)||LA14_0==53) ) {
                alt14=1;
            }
            else if ( ((LA14_0>=LITERAL_oclAsType && LA14_0<=LPAREN)||LA14_0==IDENT||(LA14_0>=INT && LA14_0<=HASH)||(LA14_0>=56 && LA14_0<=57)||(LA14_0>=61 && LA14_0<=68)) ) {
                alt14=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // GOCLBase.g:306:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    {
                    // GOCLBase.g:306:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    // GOCLBase.g:306:9: ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression
                    {
                     Token op = input.LT(1); 
                    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS)||input.LA(1)==53 ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression928);
                    nUnExp=unaryExpression();

                    state._fsp--;

                     n = new ASTUnaryExpression(op, nUnExp); 

                    }


                    }
                    break;
                case 2 :
                    // GOCLBase.g:310:7: nPosExp= postfixExpression
                    {
                    pushFollow(FOLLOW_postfixExpression_in_unaryExpression948);
                    nPosExp=postfixExpression();

                    state._fsp--;

                     n = nPosExp; 

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
    // $ANTLR end unaryExpression


    // $ANTLR start postfixExpression
    // GOCLBase.g:318:1: postfixExpression returns [ASTExpression n] : nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* ;
    public final ASTExpression postfixExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nPrimExp = null;

        ASTExpression nPc = null;


         boolean arrow = false; 
        try {
            // GOCLBase.g:320:1: (nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* )
            // GOCLBase.g:321:5: nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            {
            pushFollow(FOLLOW_primaryExpression_in_postfixExpression981);
            nPrimExp=primaryExpression();

            state._fsp--;

             n = nPrimExp; 
            // GOCLBase.g:322:5: ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>=ARROW && LA16_0<=DOT)) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // GOCLBase.g:323:6: ( ARROW | DOT ) nPc= propertyCall[$n, arrow]
            	    {
            	    // GOCLBase.g:323:6: ( ARROW | DOT )
            	    int alt15=2;
            	    int LA15_0 = input.LA(1);

            	    if ( (LA15_0==ARROW) ) {
            	        alt15=1;
            	    }
            	    else if ( (LA15_0==DOT) ) {
            	        alt15=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 15, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt15) {
            	        case 1 :
            	            // GOCLBase.g:323:8: ARROW
            	            {
            	            match(input,ARROW,FOLLOW_ARROW_in_postfixExpression999); 
            	             arrow = true; 

            	            }
            	            break;
            	        case 2 :
            	            // GOCLBase.g:323:34: DOT
            	            {
            	            match(input,DOT,FOLLOW_DOT_in_postfixExpression1005); 
            	             arrow = false; 

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_propertyCall_in_postfixExpression1016);
            	    nPc=propertyCall(n, arrow);

            	    state._fsp--;

            	     n = nPc; 

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
    // $ANTLR end postfixExpression


    // $ANTLR start primaryExpression
    // GOCLBase.g:339:1: primaryExpression returns [ASTExpression n] : (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? );
    public final ASTExpression primaryExpression() throws RecognitionException {
        ASTExpression n = null;

        Token id1=null;
        ASTExpression nLit = null;

        ASTExpression nPc = null;

        ASTExpression nExp = null;

        ASTExpression nIfExp = null;


        try {
            // GOCLBase.g:340:1: (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? )
            int alt19=5;
            switch ( input.LA(1) ) {
            case INT:
            case REAL:
            case STRING:
            case HASH:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
                {
                alt19=1;
                }
                break;
            case IDENT:
                {
                int LA19_2 = input.LA(2);

                if ( (LA19_2==EOF||(LA19_2>=LPAREN && LA19_2<=IDENT)||(LA19_2>=EQUAL && LA19_2<=ARROW)||(LA19_2>=AT && LA19_2<=LBRACK)||(LA19_2>=RBRACE && LA19_2<=DOTDOT)||LA19_2==COLON_EQUAL||(LA19_2>=47 && LA19_2<=52)||LA19_2==55||(LA19_2>=57 && LA19_2<=60)||(LA19_2>=71 && LA19_2<=72)||(LA19_2>=75 && LA19_2<=78)||(LA19_2>=80 && LA19_2<=83)||(LA19_2>=86 && LA19_2<=92)||LA19_2==94||LA19_2==96||(LA19_2>=98 && LA19_2<=103)) ) {
                    alt19=2;
                }
                else if ( (LA19_2==DOT) ) {
                    int LA19_6 = input.LA(3);

                    if ( (LA19_6==54) ) {
                        alt19=5;
                    }
                    else if ( ((LA19_6>=LITERAL_oclAsType && LA19_6<=LITERAL_oclIsTypeOf)||LA19_6==IDENT||LA19_6==56) ) {
                        alt19=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 19, 6, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 19, 2, input);

                    throw nvae;
                }
                }
                break;
            case LITERAL_oclAsType:
            case LITERAL_oclIsKindOf:
            case LITERAL_oclIsTypeOf:
            case 56:
                {
                alt19=2;
                }
                break;
            case LPAREN:
                {
                alt19=3;
                }
                break;
            case 57:
                {
                alt19=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }

            switch (alt19) {
                case 1 :
                    // GOCLBase.g:341:7: nLit= literal
                    {
                    pushFollow(FOLLOW_literal_in_primaryExpression1056);
                    nLit=literal();

                    state._fsp--;

                     n = nLit; 

                    }
                    break;
                case 2 :
                    // GOCLBase.g:342:7: nPc= propertyCall[null, false]
                    {
                    pushFollow(FOLLOW_propertyCall_in_primaryExpression1068);
                    nPc=propertyCall(null, false);

                    state._fsp--;

                     n = nPc; 

                    }
                    break;
                case 3 :
                    // GOCLBase.g:343:7: LPAREN nExp= expression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression1079); 
                    pushFollow(FOLLOW_expression_in_primaryExpression1083);
                    nExp=expression();

                    state._fsp--;

                    match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression1085); 
                     n = nExp; 

                    }
                    break;
                case 4 :
                    // GOCLBase.g:344:7: nIfExp= ifExpression
                    {
                    pushFollow(FOLLOW_ifExpression_in_primaryExpression1097);
                    nIfExp=ifExpression();

                    state._fsp--;

                     n = nIfExp; 

                    }
                    break;
                case 5 :
                    // GOCLBase.g:346:7: id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )?
                    {
                    id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_primaryExpression1114); 
                    match(input,DOT,FOLLOW_DOT_in_primaryExpression1116); 
                    match(input,54,FOLLOW_54_in_primaryExpression1118); 
                    // GOCLBase.g:346:36: ( LPAREN RPAREN )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==LPAREN) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // GOCLBase.g:346:38: LPAREN RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression1122); 
                            match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression1124); 

                            }
                            break;

                    }

                     n = new ASTAllInstancesExpression(id1); 
                    // GOCLBase.g:348:7: ( AT 'pre' )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==AT) ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // GOCLBase.g:348:9: AT 'pre'
                            {
                            match(input,AT,FOLLOW_AT_in_primaryExpression1145); 
                            match(input,55,FOLLOW_55_in_primaryExpression1147); 
                             n.setIsPre(); 

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
    // $ANTLR end primaryExpression


    // $ANTLR start propertyCall
    // GOCLBase.g:361:1: propertyCall[ASTExpression source, boolean followsArrow] returns [ASTExpression n] : ({...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] );
    public final ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExpQuery = null;

        ASTExpression nExpIterate = null;

        ASTOperationExpression nExpOperation = null;

        ASTTypeArgExpression nExpType = null;


        try {
            // GOCLBase.g:362:1: ({...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] )
            int alt20=4;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA20_1 = input.LA(2);

                if ( ( isQueryIdent(input.LT(1)) ) ) {
                    alt20=1;
                }
                else if ( (true) ) {
                    alt20=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 20, 1, input);

                    throw nvae;
                }
                }
                break;
            case 56:
                {
                alt20=2;
                }
                break;
            case LITERAL_oclAsType:
            case LITERAL_oclIsKindOf:
            case LITERAL_oclIsTypeOf:
                {
                alt20=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }

            switch (alt20) {
                case 1 :
                    // GOCLBase.g:366:7: {...}?nExpQuery= queryExpression[source]
                    {
                    if ( !( isQueryIdent(input.LT(1)) ) ) {
                        throw new FailedPredicateException(input, "propertyCall", " isQueryIdent(input.LT(1)) ");
                    }
                    pushFollow(FOLLOW_queryExpression_in_propertyCall1213);
                    nExpQuery=queryExpression(source);

                    state._fsp--;

                     n = nExpQuery; 

                    }
                    break;
                case 2 :
                    // GOCLBase.g:368:7: nExpIterate= iterateExpression[source]
                    {
                    pushFollow(FOLLOW_iterateExpression_in_propertyCall1226);
                    nExpIterate=iterateExpression(source);

                    state._fsp--;

                     n = nExpIterate; 

                    }
                    break;
                case 3 :
                    // GOCLBase.g:369:7: nExpOperation= operationExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_operationExpression_in_propertyCall1239);
                    nExpOperation=operationExpression(source, followsArrow);

                    state._fsp--;

                     n = nExpOperation; 

                    }
                    break;
                case 4 :
                    // GOCLBase.g:370:7: nExpType= typeExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_typeExpression_in_propertyCall1252);
                    nExpType=typeExpression(source, followsArrow);

                    state._fsp--;

                     n = nExpType; 

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
    // $ANTLR end propertyCall


    // $ANTLR start queryExpression
    // GOCLBase.g:386:1: queryExpression[ASTExpression range] returns [ASTExpression n] : op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN ;
    public final ASTExpression queryExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTElemVarsDeclaration decls = null;

        ASTExpression nExp = null;


        ASTElemVarsDeclaration decl = new ASTElemVarsDeclaration(); 
        try {
            // GOCLBase.g:387:69: (op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN )
            // GOCLBase.g:388:5: op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN
            {
            op=(Token)match(input,IDENT,FOLLOW_IDENT_in_queryExpression1294); 
            match(input,LPAREN,FOLLOW_LPAREN_in_queryExpression1301); 
            // GOCLBase.g:390:5: (decls= elemVarsDeclaration BAR )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==IDENT) ) {
                int LA21_1 = input.LA(2);

                if ( (LA21_1==COMMA||LA21_1==COLON||LA21_1==BAR) ) {
                    alt21=1;
                }
            }
            switch (alt21) {
                case 1 :
                    // GOCLBase.g:390:7: decls= elemVarsDeclaration BAR
                    {
                    pushFollow(FOLLOW_elemVarsDeclaration_in_queryExpression1312);
                    decls=elemVarsDeclaration();

                    state._fsp--;

                    decl = decls;
                    match(input,BAR,FOLLOW_BAR_in_queryExpression1316); 

                    }
                    break;

            }

            pushFollow(FOLLOW_expression_in_queryExpression1328);
            nExp=expression();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_queryExpression1334); 
             n = new ASTQueryExpression(op, range, decl, nExp); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end queryExpression


    // $ANTLR start iterateExpression
    // GOCLBase.g:404:1: iterateExpression[ASTExpression range] returns [ASTExpression n] : i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN ;
    public final ASTExpression iterateExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTElemVarsDeclaration decls = null;

        ASTVariableInitialization init = null;

        ASTExpression nExp = null;


        try {
            // GOCLBase.g:404:65: (i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN )
            // GOCLBase.g:405:5: i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN
            {
            i=(Token)match(input,56,FOLLOW_56_in_iterateExpression1366); 
            match(input,LPAREN,FOLLOW_LPAREN_in_iterateExpression1372); 
            pushFollow(FOLLOW_elemVarsDeclaration_in_iterateExpression1380);
            decls=elemVarsDeclaration();

            state._fsp--;

            match(input,SEMI,FOLLOW_SEMI_in_iterateExpression1382); 
            pushFollow(FOLLOW_variableInitialization_in_iterateExpression1390);
            init=variableInitialization();

            state._fsp--;

            match(input,BAR,FOLLOW_BAR_in_iterateExpression1392); 
            pushFollow(FOLLOW_expression_in_iterateExpression1400);
            nExp=expression();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_iterateExpression1406); 
             n = new ASTIterateExpression(i, range, decls, init, nExp); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end iterateExpression


    // $ANTLR start operationExpression
    // GOCLBase.g:426:1: operationExpression[ASTExpression source, boolean followsArrow] returns [ASTOperationExpression n] : name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? ;
    public final ASTOperationExpression operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTOperationExpression n = null;

        Token name=null;
        Token rolename=null;
        ASTExpression e = null;


        try {
            // GOCLBase.g:428:1: (name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? )
            // GOCLBase.g:429:5: name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression1450); 
             n = new ASTOperationExpression(name, source, followsArrow); 
            // GOCLBase.g:432:5: ( LBRACK rolename= IDENT RBRACK )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==LBRACK) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // GOCLBase.g:432:7: LBRACK rolename= IDENT RBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_operationExpression1466); 
                    rolename=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression1470); 
                    match(input,RBRACK,FOLLOW_RBRACK_in_operationExpression1472); 
                     n.setExplicitRolename(rolename); 

                    }
                    break;

            }

            // GOCLBase.g:434:5: ( AT 'pre' )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==AT) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // GOCLBase.g:434:7: AT 'pre'
                    {
                    match(input,AT,FOLLOW_AT_in_operationExpression1485); 
                    match(input,55,FOLLOW_55_in_operationExpression1487); 
                     n.setIsPre(); 

                    }
                    break;

            }

            // GOCLBase.g:435:5: ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==LPAREN) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // GOCLBase.g:436:7: LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_operationExpression1508); 
                     n.hasParentheses(); 
                    // GOCLBase.g:437:7: (e= expression ( COMMA e= expression )* )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( ((LA25_0>=LITERAL_oclAsType && LA25_0<=LPAREN)||LA25_0==IDENT||(LA25_0>=PLUS && LA25_0<=MINUS)||(LA25_0>=INT && LA25_0<=HASH)||LA25_0==46||LA25_0==53||(LA25_0>=56 && LA25_0<=57)||(LA25_0>=61 && LA25_0<=68)) ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // GOCLBase.g:438:7: e= expression ( COMMA e= expression )*
                            {
                            pushFollow(FOLLOW_expression_in_operationExpression1529);
                            e=expression();

                            state._fsp--;

                             n.addArg(e); 
                            // GOCLBase.g:439:7: ( COMMA e= expression )*
                            loop24:
                            do {
                                int alt24=2;
                                int LA24_0 = input.LA(1);

                                if ( (LA24_0==COMMA) ) {
                                    alt24=1;
                                }


                                switch (alt24) {
                            	case 1 :
                            	    // GOCLBase.g:439:9: COMMA e= expression
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_operationExpression1541); 
                            	    pushFollow(FOLLOW_expression_in_operationExpression1545);
                            	    e=expression();

                            	    state._fsp--;

                            	     n.addArg(e); 

                            	    }
                            	    break;

                            	default :
                            	    break loop24;
                                }
                            } while (true);


                            }
                            break;

                    }

                    match(input,RPAREN,FOLLOW_RPAREN_in_operationExpression1565); 

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
    // $ANTLR end operationExpression


    // $ANTLR start typeExpression
    // GOCLBase.g:451:1: typeExpression[ASTExpression source, boolean followsArrow] returns [ASTTypeArgExpression n] : ( LITERAL_oclAsType | LITERAL_oclIsKindOf | LITERAL_oclIsTypeOf ) LPAREN t= type RPAREN ;
    public final ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTTypeArgExpression n = null;

        ASTType t = null;


        try {
            // GOCLBase.g:453:1: ( ( LITERAL_oclAsType | LITERAL_oclIsKindOf | LITERAL_oclIsTypeOf ) LPAREN t= type RPAREN )
            // GOCLBase.g:454:2: ( LITERAL_oclAsType | LITERAL_oclIsKindOf | LITERAL_oclIsTypeOf ) LPAREN t= type RPAREN
            {
             Token opToken = input.LT(1); 
            if ( (input.LA(1)>=LITERAL_oclAsType && input.LA(1)<=LITERAL_oclIsTypeOf) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_typeExpression1619); 
            pushFollow(FOLLOW_type_in_typeExpression1623);
            t=type();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_typeExpression1625); 
             n = new ASTTypeArgExpression(opToken, source, t, followsArrow); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end typeExpression


    // $ANTLR start elemVarsDeclaration
    // GOCLBase.g:465:1: elemVarsDeclaration returns [ASTElemVarsDeclaration n] : idListRes= idList ( COLON t= type )? ;
    public final ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException {
        ASTElemVarsDeclaration n = null;

        List idListRes = null;

        ASTType t = null;


         List idList; 
        try {
            // GOCLBase.g:467:1: (idListRes= idList ( COLON t= type )? )
            // GOCLBase.g:468:5: idListRes= idList ( COLON t= type )?
            {
            pushFollow(FOLLOW_idList_in_elemVarsDeclaration1664);
            idListRes=idList();

            state._fsp--;

            // GOCLBase.g:469:5: ( COLON t= type )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==COLON) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // GOCLBase.g:469:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_elemVarsDeclaration1672); 
                    pushFollow(FOLLOW_type_in_elemVarsDeclaration1676);
                    t=type();

                    state._fsp--;


                    }
                    break;

            }

             n = new ASTElemVarsDeclaration(idListRes, t); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end elemVarsDeclaration


    // $ANTLR start variableInitialization
    // GOCLBase.g:478:1: variableInitialization returns [ASTVariableInitialization n] : name= IDENT COLON t= type EQUAL e= expression ;
    public final ASTVariableInitialization variableInitialization() throws RecognitionException {
        ASTVariableInitialization n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // GOCLBase.g:479:1: (name= IDENT COLON t= type EQUAL e= expression )
            // GOCLBase.g:480:5: name= IDENT COLON t= type EQUAL e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableInitialization1711); 
            match(input,COLON,FOLLOW_COLON_in_variableInitialization1713); 
            pushFollow(FOLLOW_type_in_variableInitialization1717);
            t=type();

            state._fsp--;

            match(input,EQUAL,FOLLOW_EQUAL_in_variableInitialization1719); 
            pushFollow(FOLLOW_expression_in_variableInitialization1723);
            e=expression();

            state._fsp--;

             n = new ASTVariableInitialization(name, t, e); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end variableInitialization


    // $ANTLR start ifExpression
    // GOCLBase.g:489:1: ifExpression returns [ASTExpression n] : i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' ;
    public final ASTExpression ifExpression() throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTExpression cond = null;

        ASTExpression t = null;

        ASTExpression e = null;


        try {
            // GOCLBase.g:490:1: (i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' )
            // GOCLBase.g:491:5: i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif'
            {
            i=(Token)match(input,57,FOLLOW_57_in_ifExpression1755); 
            pushFollow(FOLLOW_expression_in_ifExpression1759);
            cond=expression();

            state._fsp--;

            match(input,58,FOLLOW_58_in_ifExpression1761); 
            pushFollow(FOLLOW_expression_in_ifExpression1765);
            t=expression();

            state._fsp--;

            match(input,59,FOLLOW_59_in_ifExpression1767); 
            pushFollow(FOLLOW_expression_in_ifExpression1771);
            e=expression();

            state._fsp--;

            match(input,60,FOLLOW_60_in_ifExpression1773); 
             n = new ASTIfExpression(i, cond, t, e); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end ifExpression


    // $ANTLR start literal
    // GOCLBase.g:509:1: literal returns [ASTExpression n] : (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral );
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
            // GOCLBase.g:510:1: (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral )
            int alt28=10;
            switch ( input.LA(1) ) {
            case 61:
                {
                alt28=1;
                }
                break;
            case 62:
                {
                alt28=2;
                }
                break;
            case INT:
                {
                alt28=3;
                }
                break;
            case REAL:
                {
                alt28=4;
                }
                break;
            case STRING:
                {
                alt28=5;
                }
                break;
            case HASH:
                {
                alt28=6;
                }
                break;
            case 63:
            case 64:
            case 65:
                {
                alt28=7;
                }
                break;
            case 66:
                {
                alt28=8;
                }
                break;
            case 67:
                {
                alt28=9;
                }
                break;
            case 68:
                {
                alt28=10;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }

            switch (alt28) {
                case 1 :
                    // GOCLBase.g:511:7: t= 'true'
                    {
                    t=(Token)match(input,61,FOLLOW_61_in_literal1812); 
                     n = new ASTBooleanLiteral(true); 

                    }
                    break;
                case 2 :
                    // GOCLBase.g:512:7: f= 'false'
                    {
                    f=(Token)match(input,62,FOLLOW_62_in_literal1826); 
                     n = new ASTBooleanLiteral(false); 

                    }
                    break;
                case 3 :
                    // GOCLBase.g:513:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_literal1839); 
                     n = new ASTIntegerLiteral(i); 

                    }
                    break;
                case 4 :
                    // GOCLBase.g:514:7: r= REAL
                    {
                    r=(Token)match(input,REAL,FOLLOW_REAL_in_literal1854); 
                     n = new ASTRealLiteral(r); 

                    }
                    break;
                case 5 :
                    // GOCLBase.g:515:7: s= STRING
                    {
                    s=(Token)match(input,STRING,FOLLOW_STRING_in_literal1868); 
                     n = new ASTStringLiteral(s); 

                    }
                    break;
                case 6 :
                    // GOCLBase.g:516:7: HASH enumLit= IDENT
                    {
                    match(input,HASH,FOLLOW_HASH_in_literal1878); 
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal1882); 
                     n = new ASTEnumLiteral(enumLit); 

                    }
                    break;
                case 7 :
                    // GOCLBase.g:517:7: nColIt= collectionLiteral
                    {
                    pushFollow(FOLLOW_collectionLiteral_in_literal1894);
                    nColIt=collectionLiteral();

                    state._fsp--;

                     n = nColIt; 

                    }
                    break;
                case 8 :
                    // GOCLBase.g:518:7: nEColIt= emptyCollectionLiteral
                    {
                    pushFollow(FOLLOW_emptyCollectionLiteral_in_literal1906);
                    nEColIt=emptyCollectionLiteral();

                    state._fsp--;

                     n = nEColIt; 

                    }
                    break;
                case 9 :
                    // GOCLBase.g:519:7: nUndLit= undefinedLiteral
                    {
                    pushFollow(FOLLOW_undefinedLiteral_in_literal1918);
                    nUndLit=undefinedLiteral();

                    state._fsp--;

                    n = nUndLit; 

                    }
                    break;
                case 10 :
                    // GOCLBase.g:520:7: nTupleLit= tupleLiteral
                    {
                    pushFollow(FOLLOW_tupleLiteral_in_literal1930);
                    nTupleLit=tupleLiteral();

                    state._fsp--;

                    n = nTupleLit; 

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
    // $ANTLR end literal


    // $ANTLR start collectionLiteral
    // GOCLBase.g:528:1: collectionLiteral returns [ASTCollectionLiteral n] : ( 'Set' | 'Sequence' | 'Bag' ) LBRACE ci= collectionItem ( COMMA ci= collectionItem )* RBRACE ;
    public final ASTCollectionLiteral collectionLiteral() throws RecognitionException {
        ASTCollectionLiteral n = null;

        ASTCollectionItem ci = null;


        try {
            // GOCLBase.g:529:1: ( ( 'Set' | 'Sequence' | 'Bag' ) LBRACE ci= collectionItem ( COMMA ci= collectionItem )* RBRACE )
            // GOCLBase.g:530:5: ( 'Set' | 'Sequence' | 'Bag' ) LBRACE ci= collectionItem ( COMMA ci= collectionItem )* RBRACE
            {
             Token op = input.LT(1); 
            if ( (input.LA(1)>=63 && input.LA(1)<=65) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

             n = new ASTCollectionLiteral(op); 
            match(input,LBRACE,FOLLOW_LBRACE_in_collectionLiteral1988); 
            pushFollow(FOLLOW_collectionItem_in_collectionLiteral1997);
            ci=collectionItem();

            state._fsp--;

             n.addItem(ci); 
            // GOCLBase.g:535:5: ( COMMA ci= collectionItem )*
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==COMMA) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // GOCLBase.g:535:7: COMMA ci= collectionItem
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_collectionLiteral2008); 
            	    pushFollow(FOLLOW_collectionItem_in_collectionLiteral2012);
            	    ci=collectionItem();

            	    state._fsp--;

            	     n.addItem(ci); 

            	    }
            	    break;

            	default :
            	    break loop29;
                }
            } while (true);

            match(input,RBRACE,FOLLOW_RBRACE_in_collectionLiteral2024); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end collectionLiteral


    // $ANTLR start collectionItem
    // GOCLBase.g:543:1: collectionItem returns [ASTCollectionItem n] : e= expression ( DOTDOT e= expression )? ;
    public final ASTCollectionItem collectionItem() throws RecognitionException {
        ASTCollectionItem n = null;

        ASTExpression e = null;


         n = new ASTCollectionItem(); 
        try {
            // GOCLBase.g:545:1: (e= expression ( DOTDOT e= expression )? )
            // GOCLBase.g:546:5: e= expression ( DOTDOT e= expression )?
            {
            pushFollow(FOLLOW_expression_in_collectionItem2053);
            e=expression();

            state._fsp--;

             n.setFirst(e); 
            // GOCLBase.g:547:5: ( DOTDOT e= expression )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==DOTDOT) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // GOCLBase.g:547:7: DOTDOT e= expression
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_collectionItem2064); 
                    pushFollow(FOLLOW_expression_in_collectionItem2068);
                    e=expression();

                    state._fsp--;

                     n.setSecond(e); 

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
    // $ANTLR end collectionItem


    // $ANTLR start emptyCollectionLiteral
    // GOCLBase.g:557:1: emptyCollectionLiteral returns [ASTEmptyCollectionLiteral n] : 'oclEmpty' LPAREN t= collectionType RPAREN ;
    public final ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException {
        ASTEmptyCollectionLiteral n = null;

        ASTCollectionType t = null;


        try {
            // GOCLBase.g:558:1: ( 'oclEmpty' LPAREN t= collectionType RPAREN )
            // GOCLBase.g:559:5: 'oclEmpty' LPAREN t= collectionType RPAREN
            {
            match(input,66,FOLLOW_66_in_emptyCollectionLiteral2097); 
            match(input,LPAREN,FOLLOW_LPAREN_in_emptyCollectionLiteral2099); 
            pushFollow(FOLLOW_collectionType_in_emptyCollectionLiteral2103);
            t=collectionType();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_emptyCollectionLiteral2105); 
             n = new ASTEmptyCollectionLiteral(t); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end emptyCollectionLiteral


    // $ANTLR start undefinedLiteral
    // GOCLBase.g:570:1: undefinedLiteral returns [ASTUndefinedLiteral n] : 'oclUndefined' LPAREN t= type RPAREN ;
    public final ASTUndefinedLiteral undefinedLiteral() throws RecognitionException {
        ASTUndefinedLiteral n = null;

        ASTType t = null;


        try {
            // GOCLBase.g:571:1: ( 'oclUndefined' LPAREN t= type RPAREN )
            // GOCLBase.g:572:5: 'oclUndefined' LPAREN t= type RPAREN
            {
            match(input,67,FOLLOW_67_in_undefinedLiteral2135); 
            match(input,LPAREN,FOLLOW_LPAREN_in_undefinedLiteral2137); 
            pushFollow(FOLLOW_type_in_undefinedLiteral2141);
            t=type();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_undefinedLiteral2143); 
             n = new ASTUndefinedLiteral(t); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end undefinedLiteral


    // $ANTLR start tupleLiteral
    // GOCLBase.g:581:1: tupleLiteral returns [ASTTupleLiteral n] : 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE ;
    public final ASTTupleLiteral tupleLiteral() throws RecognitionException {
        ASTTupleLiteral n = null;

        ASTTupleItem ti = null;


         List tiList = new ArrayList(); 
        try {
            // GOCLBase.g:583:1: ( 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE )
            // GOCLBase.g:584:5: 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE
            {
            match(input,68,FOLLOW_68_in_tupleLiteral2177); 
            match(input,LBRACE,FOLLOW_LBRACE_in_tupleLiteral2183); 
            pushFollow(FOLLOW_tupleItem_in_tupleLiteral2191);
            ti=tupleItem();

            state._fsp--;

             tiList.add(ti); 
            // GOCLBase.g:587:5: ( COMMA ti= tupleItem )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==COMMA) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // GOCLBase.g:587:7: COMMA ti= tupleItem
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleLiteral2202); 
            	    pushFollow(FOLLOW_tupleItem_in_tupleLiteral2206);
            	    ti=tupleItem();

            	    state._fsp--;

            	     tiList.add(ti); 

            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);

            match(input,RBRACE,FOLLOW_RBRACE_in_tupleLiteral2217); 
             n = new ASTTupleLiteral(tiList); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end tupleLiteral


    // $ANTLR start tupleItem
    // GOCLBase.g:595:1: tupleItem returns [ASTTupleItem n] : name= IDENT ( COLON | EQUAL ) e= expression ;
    public final ASTTupleItem tupleItem() throws RecognitionException {
        ASTTupleItem n = null;

        Token name=null;
        ASTExpression e = null;


        try {
            // GOCLBase.g:596:1: (name= IDENT ( COLON | EQUAL ) e= expression )
            // GOCLBase.g:597:5: name= IDENT ( COLON | EQUAL ) e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tupleItem2248); 
            if ( (input.LA(1)>=COLON && input.LA(1)<=EQUAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            pushFollow(FOLLOW_expression_in_tupleItem2258);
            e=expression();

            state._fsp--;

             n = new ASTTupleItem(name, e); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end tupleItem


    // $ANTLR start type
    // GOCLBase.g:608:1: type returns [ASTType n] : (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) ;
    public final ASTType type() throws RecognitionException {
        ASTType n = null;

        ASTSimpleType nTSimple = null;

        ASTCollectionType nTCollection = null;

        ASTTupleType nTTuple = null;


        try {
            // GOCLBase.g:609:1: ( (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) )
            // GOCLBase.g:610:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            {
             Token tok = input.LT(1); /* remember start of type */ 
            // GOCLBase.g:611:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            int alt32=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt32=1;
                }
                break;
            case 63:
            case 64:
            case 65:
            case 69:
                {
                alt32=2;
                }
                break;
            case 68:
                {
                alt32=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;
            }

            switch (alt32) {
                case 1 :
                    // GOCLBase.g:612:7: nTSimple= simpleType
                    {
                    pushFollow(FOLLOW_simpleType_in_type2306);
                    nTSimple=simpleType();

                    state._fsp--;

                     n = nTSimple; n.setStartToken(tok); 

                    }
                    break;
                case 2 :
                    // GOCLBase.g:613:7: nTCollection= collectionType
                    {
                    pushFollow(FOLLOW_collectionType_in_type2318);
                    nTCollection=collectionType();

                    state._fsp--;

                     n = nTCollection; n.setStartToken(tok); 

                    }
                    break;
                case 3 :
                    // GOCLBase.g:614:7: nTTuple= tupleType
                    {
                    pushFollow(FOLLOW_tupleType_in_type2330);
                    nTTuple=tupleType();

                    state._fsp--;

                     n = nTTuple; n.setStartToken(tok); 

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
    // $ANTLR end type


    // $ANTLR start typeOnly
    // GOCLBase.g:619:1: typeOnly returns [ASTType n] : nT= type EOF ;
    public final ASTType typeOnly() throws RecognitionException {
        ASTType n = null;

        ASTType nT = null;


        try {
            // GOCLBase.g:620:1: (nT= type EOF )
            // GOCLBase.g:621:5: nT= type EOF
            {
            pushFollow(FOLLOW_type_in_typeOnly2362);
            nT=type();

            state._fsp--;

            match(input,EOF,FOLLOW_EOF_in_typeOnly2364); 
             n = nT; 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end typeOnly


    // $ANTLR start simpleType
    // GOCLBase.g:631:1: simpleType returns [ASTSimpleType n] : name= IDENT ;
    public final ASTSimpleType simpleType() throws RecognitionException {
        ASTSimpleType n = null;

        Token name=null;

        try {
            // GOCLBase.g:632:1: (name= IDENT )
            // GOCLBase.g:633:5: name= IDENT
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_simpleType2392); 
             n = new ASTSimpleType(name); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end simpleType


    // $ANTLR start collectionType
    // GOCLBase.g:641:1: collectionType returns [ASTCollectionType n] : ( 'Collection' | 'Set' | 'Sequence' | 'Bag' ) LPAREN elemType= type RPAREN ;
    public final ASTCollectionType collectionType() throws RecognitionException {
        ASTCollectionType n = null;

        ASTType elemType = null;


        try {
            // GOCLBase.g:642:1: ( ( 'Collection' | 'Set' | 'Sequence' | 'Bag' ) LPAREN elemType= type RPAREN )
            // GOCLBase.g:643:5: ( 'Collection' | 'Set' | 'Sequence' | 'Bag' ) LPAREN elemType= type RPAREN
            {
             Token op = input.LT(1); 
            if ( (input.LA(1)>=63 && input.LA(1)<=65)||input.LA(1)==69 ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_collectionType2448); 
            pushFollow(FOLLOW_type_in_collectionType2452);
            elemType=type();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_collectionType2454); 
             n = new ASTCollectionType(op, elemType); n.setStartToken(op);

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end collectionType


    // $ANTLR start tupleType
    // GOCLBase.g:653:1: tupleType returns [ASTTupleType n] : 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN ;
    public final ASTTupleType tupleType() throws RecognitionException {
        ASTTupleType n = null;

        ASTTuplePart tp = null;


         List tpList = new ArrayList(); 
        try {
            // GOCLBase.g:655:1: ( 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN )
            // GOCLBase.g:656:5: 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN
            {
            match(input,68,FOLLOW_68_in_tupleType2488); 
            match(input,LPAREN,FOLLOW_LPAREN_in_tupleType2490); 
            pushFollow(FOLLOW_tuplePart_in_tupleType2499);
            tp=tuplePart();

            state._fsp--;

             tpList.add(tp); 
            // GOCLBase.g:658:5: ( COMMA tp= tuplePart )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==COMMA) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // GOCLBase.g:658:7: COMMA tp= tuplePart
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleType2510); 
            	    pushFollow(FOLLOW_tuplePart_in_tupleType2514);
            	    tp=tuplePart();

            	    state._fsp--;

            	     tpList.add(tp); 

            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_tupleType2526); 
             n = new ASTTupleType(tpList); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end tupleType


    // $ANTLR start tuplePart
    // GOCLBase.g:667:1: tuplePart returns [ASTTuplePart n] : name= IDENT COLON t= type ;
    public final ASTTuplePart tuplePart() throws RecognitionException {
        ASTTuplePart n = null;

        Token name=null;
        ASTType t = null;


        try {
            // GOCLBase.g:668:1: (name= IDENT COLON t= type )
            // GOCLBase.g:669:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tuplePart2558); 
            match(input,COLON,FOLLOW_COLON_in_tuplePart2560); 
            pushFollow(FOLLOW_type_in_tuplePart2564);
            t=type();

            state._fsp--;

             n = new ASTTuplePart(name, t); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return n;
    }
    // $ANTLR end tuplePart

    // Delegated rules


 

    public static final BitSet FOLLOW_LPAREN_in_paramList76 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList93 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_COMMA_in_paramList105 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList109 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_RPAREN_in_paramList129 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_idList158 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_idList168 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_idList172 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_IDENT_in_variableDeclaration203 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_variableDeclaration205 = new BitSet(new long[]{0x8000000000000400L,0x0000000000000033L});
    public static final BitSet FOLLOW_type_in_variableDeclaration209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_expression257 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_expression261 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_COLON_in_expression265 = new BitSet(new long[]{0x8000000000000400L,0x0000000000000033L});
    public static final BitSet FOLLOW_type_in_expression269 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_EQUAL_in_expression274 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_expression278 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_expression280 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_conditionalImpliesExpression_in_expression305 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression338 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_48_in_conditionalImpliesExpression351 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression355 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression401 = new BitSet(new long[]{0x0002000000000002L});
    public static final BitSet FOLLOW_49_in_conditionalOrExpression414 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression418 = new BitSet(new long[]{0x0002000000000002L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression463 = new BitSet(new long[]{0x0004000000000002L});
    public static final BitSet FOLLOW_50_in_conditionalXOrExpression476 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression480 = new BitSet(new long[]{0x0004000000000002L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression525 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_51_in_conditionalAndExpression538 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression542 = new BitSet(new long[]{0x0008000000000002L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression586 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_set_in_equalityExpression605 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression615 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression660 = new BitSet(new long[]{0x000000000003C002L});
    public static final BitSet FOLLOW_set_in_relationalExpression678 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression696 = new BitSet(new long[]{0x000000000003C002L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression742 = new BitSet(new long[]{0x00000000000C0002L});
    public static final BitSet FOLLOW_set_in_additiveExpression760 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression770 = new BitSet(new long[]{0x00000000000C0002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression815 = new BitSet(new long[]{0x0010000000300002L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression833 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression847 = new BitSet(new long[]{0x0010000000300002L});
    public static final BitSet FOLLOW_set_in_unaryExpression904 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression928 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpression_in_unaryExpression948 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_postfixExpression981 = new BitSet(new long[]{0x0000000000C00002L});
    public static final BitSet FOLLOW_ARROW_in_postfixExpression999 = new BitSet(new long[]{0x0100000000000470L});
    public static final BitSet FOLLOW_DOT_in_postfixExpression1005 = new BitSet(new long[]{0x0100000000000470L});
    public static final BitSet FOLLOW_propertyCall_in_postfixExpression1016 = new BitSet(new long[]{0x0000000000C00002L});
    public static final BitSet FOLLOW_literal_in_primaryExpression1056 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyCall_in_primaryExpression1068 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression1079 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_primaryExpression1083 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression1085 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifExpression_in_primaryExpression1097 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_primaryExpression1114 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_DOT_in_primaryExpression1116 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_primaryExpression1118 = new BitSet(new long[]{0x0000000001000082L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression1122 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression1124 = new BitSet(new long[]{0x0000000001000002L});
    public static final BitSet FOLLOW_AT_in_primaryExpression1145 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_primaryExpression1147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_queryExpression_in_propertyCall1213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterateExpression_in_propertyCall1226 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationExpression_in_propertyCall1239 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeExpression_in_propertyCall1252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_queryExpression1294 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_queryExpression1301 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_queryExpression1312 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_BAR_in_queryExpression1316 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_queryExpression1328 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_queryExpression1334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_56_in_iterateExpression1366 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_iterateExpression1372 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_iterateExpression1380 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_SEMI_in_iterateExpression1382 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_variableInitialization_in_iterateExpression1390 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_BAR_in_iterateExpression1392 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_iterateExpression1400 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_iterateExpression1406 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression1450 = new BitSet(new long[]{0x0000000009000082L});
    public static final BitSet FOLLOW_LBRACK_in_operationExpression1466 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression1470 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_RBRACK_in_operationExpression1472 = new BitSet(new long[]{0x0000000001000082L});
    public static final BitSet FOLLOW_AT_in_operationExpression1485 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_55_in_operationExpression1487 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_LPAREN_in_operationExpression1508 = new BitSet(new long[]{0xE3204001E00C06F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_operationExpression1529 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_COMMA_in_operationExpression1541 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_operationExpression1545 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_RPAREN_in_operationExpression1565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_typeExpression1603 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_typeExpression1619 = new BitSet(new long[]{0x8000000000000400L,0x0000000000000033L});
    public static final BitSet FOLLOW_type_in_typeExpression1623 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_typeExpression1625 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_elemVarsDeclaration1664 = new BitSet(new long[]{0x0000000000000802L});
    public static final BitSet FOLLOW_COLON_in_elemVarsDeclaration1672 = new BitSet(new long[]{0x8000000000000400L,0x0000000000000033L});
    public static final BitSet FOLLOW_type_in_elemVarsDeclaration1676 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableInitialization1711 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_variableInitialization1713 = new BitSet(new long[]{0x8000000000000400L,0x0000000000000033L});
    public static final BitSet FOLLOW_type_in_variableInitialization1717 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_EQUAL_in_variableInitialization1719 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_variableInitialization1723 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_ifExpression1755 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_ifExpression1759 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_ifExpression1761 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_ifExpression1765 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_ifExpression1767 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_ifExpression1771 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_ifExpression1773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_literal1812 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_literal1826 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_literal1839 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REAL_in_literal1854 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal1868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_literal1878 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_literal1882 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionLiteral_in_literal1894 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyCollectionLiteral_in_literal1906 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedLiteral_in_literal1918 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleLiteral_in_literal1930 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionLiteral1963 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_LBRACE_in_collectionLiteral1988 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral1997 = new BitSet(new long[]{0x0000000400000100L});
    public static final BitSet FOLLOW_COMMA_in_collectionLiteral2008 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral2012 = new BitSet(new long[]{0x0000000400000100L});
    public static final BitSet FOLLOW_RBRACE_in_collectionLiteral2024 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_collectionItem2053 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_DOTDOT_in_collectionItem2064 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_collectionItem2068 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_emptyCollectionLiteral2097 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_emptyCollectionLiteral2099 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000023L});
    public static final BitSet FOLLOW_collectionType_in_emptyCollectionLiteral2103 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_emptyCollectionLiteral2105 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_67_in_undefinedLiteral2135 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_undefinedLiteral2137 = new BitSet(new long[]{0x8000000000000400L,0x0000000000000033L});
    public static final BitSet FOLLOW_type_in_undefinedLiteral2141 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_undefinedLiteral2143 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_tupleLiteral2177 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_LBRACE_in_tupleLiteral2183 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral2191 = new BitSet(new long[]{0x0000000400000100L});
    public static final BitSet FOLLOW_COMMA_in_tupleLiteral2202 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral2206 = new BitSet(new long[]{0x0000000400000100L});
    public static final BitSet FOLLOW_RBRACE_in_tupleLiteral2217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tupleItem2248 = new BitSet(new long[]{0x0000000000001800L});
    public static final BitSet FOLLOW_set_in_tupleItem2250 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_tupleItem2258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleType_in_type2306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_type2318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleType_in_type2330 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeOnly2362 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_typeOnly2364 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_simpleType2392 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionType2425 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_collectionType2448 = new BitSet(new long[]{0x8000000000000400L,0x0000000000000033L});
    public static final BitSet FOLLOW_type_in_collectionType2452 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_collectionType2454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_tupleType2488 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_tupleType2490 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType2499 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_COMMA_in_tupleType2510 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType2514 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_RPAREN_in_tupleType2526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tuplePart2558 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_tuplePart2560 = new BitSet(new long[]{0x8000000000000400L,0x0000000000000033L});
    public static final BitSet FOLLOW_type_in_tuplePart2564 = new BitSet(new long[]{0x0000000000000002L});

}