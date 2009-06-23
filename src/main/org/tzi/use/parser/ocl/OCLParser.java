// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 OCL.g 2009-06-16 14:38:29
 
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

package org.tzi.use.parser.ocl; 

// ------------------------------------
//  USE OCL parser
// ------------------------------------
import org.tzi.use.parser.base.BaseParser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class OCLParser extends BaseParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "IDENT", "COLON", "EQUAL", "LPAREN", "COMMA", "RPAREN", "NOT_EQUAL", "LESS", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "PLUS", "MINUS", "STAR", "SLASH", "ARROW", "DOT", "AT", "BAR", "SEMI", "LBRACK", "RBRACK", "INT", "REAL", "STRING", "HASH", "LBRACE", "RBRACE", "DOTDOT", "NEWLINE", "WS", "SL_COMMENT", "ML_COMMENT", "COLON_COLON", "COLON_EQUAL", "RANGE_OR_INT", "ESC", "HEX_DIGIT", "VOCAB", "'let'", "'in'", "'implies'", "'or'", "'xor'", "'and'", "'div'", "'not'", "'allInstances'", "'pre'", "'iterate'", "'oclAsType'", "'oclIsKindOf'", "'oclIsTypeOf'", "'if'", "'then'", "'else'", "'endif'", "'true'", "'false'", "'Set'", "'Sequence'", "'Bag'", "'OrderedSet'", "'oclEmpty'", "'oclUndefined'", "'Undefined'", "'null'", "'Tuple'", "'Date'", "'Collection'"
    };
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int LBRACK=24;
    public static final int STAR=17;
    public static final int T__64=64;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int ESC=40;
    public static final int LBRACE=30;
    public static final int DOTDOT=32;
    public static final int T__61=61;
    public static final int EOF=-1;
    public static final int T__60=60;
    public static final int LPAREN=7;
    public static final int AT=21;
    public static final int T__55=55;
    public static final int ML_COMMENT=36;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int RPAREN=9;
    public static final int T__58=58;
    public static final int COLON_EQUAL=38;
    public static final int SLASH=18;
    public static final int T__51=51;
    public static final int GREATER=12;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int COMMA=8;
    public static final int NOT_EQUAL=10;
    public static final int T__59=59;
    public static final int EQUAL=6;
    public static final int LESS=11;
    public static final int IDENT=4;
    public static final int PLUS=15;
    public static final int RANGE_OR_INT=39;
    public static final int DOT=20;
    public static final int T__50=50;
    public static final int RBRACK=25;
    public static final int T__43=43;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int RBRACE=31;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int HASH=29;
    public static final int HEX_DIGIT=41;
    public static final int COLON_COLON=37;
    public static final int INT=26;
    public static final int MINUS=16;
    public static final int SEMI=23;
    public static final int COLON=5;
    public static final int REAL=27;
    public static final int WS=34;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int NEWLINE=33;
    public static final int T__70=70;
    public static final int SL_COMMENT=35;
    public static final int VOCAB=42;
    public static final int ARROW=19;
    public static final int LESS_EQUAL=13;
    public static final int GREATER_EQUAL=14;
    public static final int T__73=73;
    public static final int BAR=22;
    public static final int STRING=28;

    // delegates
    // delegators


        public OCLParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public OCLParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return OCLParser.tokenNames; }
    public String getGrammarFileName() { return "OCL.g"; }



    // $ANTLR start "expressionOnly"
    // OCL.g:91:1: expressionOnly returns [ASTExpression n] : nExp= expression EOF ;
    public final ASTExpression expressionOnly() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExp = null;


        try {
            // OCL.g:92:1: (nExp= expression EOF )
            // OCL.g:93:5: nExp= expression EOF
            {
            pushFollow(FOLLOW_expression_in_expressionOnly68);
            nExp=expression();

            state._fsp--;

            match(input,EOF,FOLLOW_EOF_in_expressionOnly70); 
            n = nExp;

            }

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
    // OCL.g:100:1: expression returns [ASTExpression n] : ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression ;
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
            // OCL.g:106:1: ( ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression )
            // OCL.g:107:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression
            {
             tok = input.LT(1); /* remember start of expression */ 
            // OCL.g:108:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==43) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // OCL.g:109:7: 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in'
            	    {
            	    match(input,43,FOLLOW_43_in_expression118); 
            	    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_expression122); 
            	    // OCL.g:109:24: ( COLON t= type )?
            	    int alt1=2;
            	    int LA1_0 = input.LA(1);

            	    if ( (LA1_0==COLON) ) {
            	        alt1=1;
            	    }
            	    switch (alt1) {
            	        case 1 :
            	            // OCL.g:109:26: COLON t= type
            	            {
            	            match(input,COLON,FOLLOW_COLON_in_expression126); 
            	            pushFollow(FOLLOW_type_in_expression130);
            	            t=type();

            	            state._fsp--;


            	            }
            	            break;

            	    }

            	    match(input,EQUAL,FOLLOW_EQUAL_in_expression135); 
            	    pushFollow(FOLLOW_expression_in_expression139);
            	    e1=expression();

            	    state._fsp--;

            	    match(input,44,FOLLOW_44_in_expression141); 
            	     ASTLetExpression nextLet = new ASTLetExpression(name, t, e1);
            	             if ( firstLet == null ) 
            	                 firstLet = nextLet;
            	             if ( prevLet != null ) 
            	                 prevLet.setInExpr(nextLet);
            	             prevLet = nextLet;
            	           

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            pushFollow(FOLLOW_conditionalImpliesExpression_in_expression166);
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
    // $ANTLR end "expression"


    // $ANTLR start "paramList"
    // OCL.g:137:1: paramList returns [List paramList] : LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN ;
    public final List paramList() throws RecognitionException {
        List paramList = null;

        ASTVariableDeclaration v = null;


         paramList = new ArrayList(); 
        try {
            // OCL.g:139:1: ( LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN )
            // OCL.g:140:5: LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN
            {
            match(input,LPAREN,FOLLOW_LPAREN_in_paramList199); 
            // OCL.g:141:5: (v= variableDeclaration ( COMMA v= variableDeclaration )* )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==IDENT) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // OCL.g:142:7: v= variableDeclaration ( COMMA v= variableDeclaration )*
                    {
                    pushFollow(FOLLOW_variableDeclaration_in_paramList216);
                    v=variableDeclaration();

                    state._fsp--;

                     paramList.add(v); 
                    // OCL.g:143:7: ( COMMA v= variableDeclaration )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==COMMA) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // OCL.g:143:9: COMMA v= variableDeclaration
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_paramList228); 
                    	    pushFollow(FOLLOW_variableDeclaration_in_paramList232);
                    	    v=variableDeclaration();

                    	    state._fsp--;

                    	     paramList.add(v); 

                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_paramList252); 

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
    // OCL.g:151:1: idList returns [List idList] : id0= IDENT ( COMMA idn= IDENT )* ;
    public final List idList() throws RecognitionException {
        List idList = null;

        Token id0=null;
        Token idn=null;

         idList = new ArrayList(); 
        try {
            // OCL.g:153:1: (id0= IDENT ( COMMA idn= IDENT )* )
            // OCL.g:154:5: id0= IDENT ( COMMA idn= IDENT )*
            {
            id0=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList281); 
             idList.add(id0); 
            // OCL.g:155:5: ( COMMA idn= IDENT )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==COMMA) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // OCL.g:155:7: COMMA idn= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_idList291); 
            	    idn=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList295); 
            	     idList.add(idn); 

            	    }
            	    break;

            	default :
            	    break loop5;
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
    // OCL.g:163:1: variableDeclaration returns [ASTVariableDeclaration n] : name= IDENT COLON t= type ;
    public final ASTVariableDeclaration variableDeclaration() throws RecognitionException {
        ASTVariableDeclaration n = null;

        Token name=null;
        ASTType t = null;


        try {
            // OCL.g:164:1: (name= IDENT COLON t= type )
            // OCL.g:165:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableDeclaration326); 
            match(input,COLON,FOLLOW_COLON_in_variableDeclaration328); 
            pushFollow(FOLLOW_type_in_variableDeclaration332);
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
    // $ANTLR end "variableDeclaration"


    // $ANTLR start "conditionalImpliesExpression"
    // OCL.g:173:1: conditionalImpliesExpression returns [ASTExpression n] : nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* ;
    public final ASTExpression conditionalImpliesExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndOrExp = null;

        ASTExpression n1 = null;


        try {
            // OCL.g:174:1: (nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* )
            // OCL.g:175:5: nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )*
            {
            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression368);
            nCndOrExp=conditionalOrExpression();

            state._fsp--;

            n = nCndOrExp;
            // OCL.g:176:5: (op= 'implies' n1= conditionalOrExpression )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==45) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // OCL.g:176:7: op= 'implies' n1= conditionalOrExpression
            	    {
            	    op=(Token)match(input,45,FOLLOW_45_in_conditionalImpliesExpression381); 
            	    pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression385);
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
    // $ANTLR end "conditionalImpliesExpression"


    // $ANTLR start "conditionalOrExpression"
    // OCL.g:185:1: conditionalOrExpression returns [ASTExpression n] : nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* ;
    public final ASTExpression conditionalOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndXorExp = null;

        ASTExpression n1 = null;


        try {
            // OCL.g:186:1: (nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* )
            // OCL.g:187:5: nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )*
            {
            pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression430);
            nCndXorExp=conditionalXOrExpression();

            state._fsp--;

            n = nCndXorExp;
            // OCL.g:188:5: (op= 'or' n1= conditionalXOrExpression )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==46) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // OCL.g:188:7: op= 'or' n1= conditionalXOrExpression
            	    {
            	    op=(Token)match(input,46,FOLLOW_46_in_conditionalOrExpression443); 
            	    pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression447);
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
    // $ANTLR end "conditionalOrExpression"


    // $ANTLR start "conditionalXOrExpression"
    // OCL.g:197:1: conditionalXOrExpression returns [ASTExpression n] : nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* ;
    public final ASTExpression conditionalXOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndAndExp = null;

        ASTExpression n1 = null;


        try {
            // OCL.g:198:1: (nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* )
            // OCL.g:199:5: nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )*
            {
            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression491);
            nCndAndExp=conditionalAndExpression();

            state._fsp--;

            n = nCndAndExp;
            // OCL.g:200:5: (op= 'xor' n1= conditionalAndExpression )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==47) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // OCL.g:200:7: op= 'xor' n1= conditionalAndExpression
            	    {
            	    op=(Token)match(input,47,FOLLOW_47_in_conditionalXOrExpression504); 
            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression508);
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
    // $ANTLR end "conditionalXOrExpression"


    // $ANTLR start "conditionalAndExpression"
    // OCL.g:209:1: conditionalAndExpression returns [ASTExpression n] : nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* ;
    public final ASTExpression conditionalAndExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nEqExp = null;

        ASTExpression n1 = null;


        try {
            // OCL.g:210:1: (nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* )
            // OCL.g:211:5: nEqExp= equalityExpression (op= 'and' n1= equalityExpression )*
            {
            pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression552);
            nEqExp=equalityExpression();

            state._fsp--;

            n = nEqExp;
            // OCL.g:212:5: (op= 'and' n1= equalityExpression )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==48) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // OCL.g:212:7: op= 'and' n1= equalityExpression
            	    {
            	    op=(Token)match(input,48,FOLLOW_48_in_conditionalAndExpression565); 
            	    pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression569);
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
    // $ANTLR end "conditionalAndExpression"


    // $ANTLR start "equalityExpression"
    // OCL.g:221:1: equalityExpression returns [ASTExpression n] : nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* ;
    public final ASTExpression equalityExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nRelExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // OCL.g:223:1: (nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* )
            // OCL.g:224:5: nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            {
            pushFollow(FOLLOW_relationalExpression_in_equalityExpression617);
            nRelExp=relationalExpression();

            state._fsp--;

            n = nRelExp;
            // OCL.g:225:5: ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==EQUAL||LA10_0==NOT_EQUAL) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // OCL.g:225:7: ( EQUAL | NOT_EQUAL ) n1= relationalExpression
            	    {
            	     op = input.LT(1); 
            	    if ( input.LA(1)==EQUAL||input.LA(1)==NOT_EQUAL ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression646);
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
    // $ANTLR end "equalityExpression"


    // $ANTLR start "relationalExpression"
    // OCL.g:235:1: relationalExpression returns [ASTExpression n] : nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* ;
    public final ASTExpression relationalExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nAddiExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // OCL.g:237:1: (nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* )
            // OCL.g:238:5: nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            {
            pushFollow(FOLLOW_additiveExpression_in_relationalExpression695);
            nAddiExp=additiveExpression();

            state._fsp--;

            n = nAddiExp;
            // OCL.g:239:5: ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>=LESS && LA11_0<=GREATER_EQUAL)) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // OCL.g:239:7: ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression
            	    {
            	     op = input.LT(1); 
            	    if ( (input.LA(1)>=LESS && input.LA(1)<=GREATER_EQUAL) ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_additiveExpression_in_relationalExpression731);
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
    // $ANTLR end "relationalExpression"


    // $ANTLR start "additiveExpression"
    // OCL.g:249:1: additiveExpression returns [ASTExpression n] : nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* ;
    public final ASTExpression additiveExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nMulExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // OCL.g:251:1: (nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* )
            // OCL.g:252:5: nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            {
            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression781);
            nMulExp=multiplicativeExpression();

            state._fsp--;

            n = nMulExp;
            // OCL.g:253:5: ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0>=PLUS && LA12_0<=MINUS)) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // OCL.g:253:7: ( PLUS | MINUS ) n1= multiplicativeExpression
            	    {
            	     op = input.LT(1); 
            	    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS) ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression809);
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
    // $ANTLR end "additiveExpression"


    // $ANTLR start "multiplicativeExpression"
    // OCL.g:264:1: multiplicativeExpression returns [ASTExpression n] : nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* ;
    public final ASTExpression multiplicativeExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // OCL.g:266:1: (nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* )
            // OCL.g:267:5: nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            {
            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression859);
            nUnExp=unaryExpression();

            state._fsp--;

             n = nUnExp;
            // OCL.g:268:5: ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0>=STAR && LA13_0<=SLASH)||LA13_0==49) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // OCL.g:268:7: ( STAR | SLASH | 'div' ) n1= unaryExpression
            	    {
            	     op = input.LT(1); 
            	    if ( (input.LA(1)>=STAR && input.LA(1)<=SLASH)||input.LA(1)==49 ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression891);
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
    // $ANTLR end "multiplicativeExpression"


    // $ANTLR start "unaryExpression"
    // OCL.g:280:1: unaryExpression returns [ASTExpression n] : ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression );
    public final ASTExpression unaryExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression nPosExp = null;


         Token op = null; 
        try {
            // OCL.g:282:1: ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0>=PLUS && LA14_0<=MINUS)||LA14_0==50) ) {
                alt14=1;
            }
            else if ( (LA14_0==IDENT||LA14_0==LPAREN||(LA14_0>=INT && LA14_0<=HASH)||(LA14_0>=53 && LA14_0<=57)||(LA14_0>=61 && LA14_0<=72)) ) {
                alt14=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // OCL.g:283:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    {
                    // OCL.g:283:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    // OCL.g:283:9: ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression
                    {
                     op = input.LT(1); 
                    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS)||input.LA(1)==50 ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression977);
                    nUnExp=unaryExpression();

                    state._fsp--;

                     n = new ASTUnaryExpression(op, nUnExp); 

                    }


                    }
                    break;
                case 2 :
                    // OCL.g:287:7: nPosExp= postfixExpression
                    {
                    pushFollow(FOLLOW_postfixExpression_in_unaryExpression997);
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
    // $ANTLR end "unaryExpression"


    // $ANTLR start "postfixExpression"
    // OCL.g:295:1: postfixExpression returns [ASTExpression n] : nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* ;
    public final ASTExpression postfixExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nPrimExp = null;

        ASTExpression nPc = null;


         boolean arrow = false; 
        try {
            // OCL.g:297:1: (nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* )
            // OCL.g:298:5: nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            {
            pushFollow(FOLLOW_primaryExpression_in_postfixExpression1030);
            nPrimExp=primaryExpression();

            state._fsp--;

             n = nPrimExp; 
            // OCL.g:299:5: ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>=ARROW && LA16_0<=DOT)) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // OCL.g:300:6: ( ARROW | DOT ) nPc= propertyCall[$n, arrow]
            	    {
            	    // OCL.g:300:6: ( ARROW | DOT )
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
            	            // OCL.g:300:8: ARROW
            	            {
            	            match(input,ARROW,FOLLOW_ARROW_in_postfixExpression1048); 
            	             arrow = true; 

            	            }
            	            break;
            	        case 2 :
            	            // OCL.g:300:34: DOT
            	            {
            	            match(input,DOT,FOLLOW_DOT_in_postfixExpression1054); 
            	             arrow = false; 

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_propertyCall_in_postfixExpression1065);
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
    // $ANTLR end "postfixExpression"


    // $ANTLR start "primaryExpression"
    // OCL.g:316:1: primaryExpression returns [ASTExpression n] : (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? );
    public final ASTExpression primaryExpression() throws RecognitionException {
        ASTExpression n = null;

        Token id1=null;
        ASTExpression nLit = null;

        ASTExpression nPc = null;

        ASTExpression nExp = null;

        ASTExpression nIfExp = null;


        try {
            // OCL.g:317:1: (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? )
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
            case 69:
            case 70:
            case 71:
            case 72:
                {
                alt19=1;
                }
                break;
            case IDENT:
                {
                int LA19_2 = input.LA(2);

                if ( (LA19_2==EOF||(LA19_2>=EQUAL && LA19_2<=ARROW)||(LA19_2>=AT && LA19_2<=BAR)||LA19_2==LBRACK||(LA19_2>=RBRACE && LA19_2<=DOTDOT)||(LA19_2>=44 && LA19_2<=49)||(LA19_2>=58 && LA19_2<=60)) ) {
                    alt19=2;
                }
                else if ( (LA19_2==DOT) ) {
                    int LA19_6 = input.LA(3);

                    if ( (LA19_6==51) ) {
                        alt19=5;
                    }
                    else if ( (LA19_6==IDENT||(LA19_6>=53 && LA19_6<=56)) ) {
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
            case 53:
            case 54:
            case 55:
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
                    // OCL.g:318:7: nLit= literal
                    {
                    pushFollow(FOLLOW_literal_in_primaryExpression1105);
                    nLit=literal();

                    state._fsp--;

                     n = nLit; 

                    }
                    break;
                case 2 :
                    // OCL.g:319:7: nPc= propertyCall[null, false]
                    {
                    pushFollow(FOLLOW_propertyCall_in_primaryExpression1117);
                    nPc=propertyCall(null, false);

                    state._fsp--;

                     n = nPc; 

                    }
                    break;
                case 3 :
                    // OCL.g:320:7: LPAREN nExp= expression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression1128); 
                    pushFollow(FOLLOW_expression_in_primaryExpression1132);
                    nExp=expression();

                    state._fsp--;

                    match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression1134); 
                     n = nExp; 

                    }
                    break;
                case 4 :
                    // OCL.g:321:7: nIfExp= ifExpression
                    {
                    pushFollow(FOLLOW_ifExpression_in_primaryExpression1146);
                    nIfExp=ifExpression();

                    state._fsp--;

                     n = nIfExp; 

                    }
                    break;
                case 5 :
                    // OCL.g:323:7: id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )?
                    {
                    id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_primaryExpression1163); 
                    match(input,DOT,FOLLOW_DOT_in_primaryExpression1165); 
                    match(input,51,FOLLOW_51_in_primaryExpression1167); 
                    // OCL.g:323:36: ( LPAREN RPAREN )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==LPAREN) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // OCL.g:323:38: LPAREN RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression1171); 
                            match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression1173); 

                            }
                            break;

                    }

                     n = new ASTAllInstancesExpression(id1); 
                    // OCL.g:325:7: ( AT 'pre' )?
                    int alt18=2;
                    int LA18_0 = input.LA(1);

                    if ( (LA18_0==AT) ) {
                        alt18=1;
                    }
                    switch (alt18) {
                        case 1 :
                            // OCL.g:325:9: AT 'pre'
                            {
                            match(input,AT,FOLLOW_AT_in_primaryExpression1194); 
                            match(input,52,FOLLOW_52_in_primaryExpression1196); 
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
    // $ANTLR end "primaryExpression"


    // $ANTLR start "propertyCall"
    // OCL.g:338:1: propertyCall[ASTExpression source, boolean followsArrow] returns [ASTExpression n] : ({...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] );
    public final ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExpQuery = null;

        ASTExpression nExpIterate = null;

        ASTOperationExpression nExpOperation = null;

        ASTTypeArgExpression nExpType = null;


        try {
            // OCL.g:339:1: ({...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] )
            int alt20=4;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA20_1 = input.LA(2);

                if ( (( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
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
            case 53:
                {
                alt20=2;
                }
                break;
            case 54:
            case 55:
            case 56:
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
                    // OCL.g:343:7: {...}?nExpQuery= queryExpression[source]
                    {
                    if ( !(( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                        throw new FailedPredicateException(input, "propertyCall", " org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) ");
                    }
                    pushFollow(FOLLOW_queryExpression_in_propertyCall1262);
                    nExpQuery=queryExpression(source);

                    state._fsp--;

                     n = nExpQuery; 

                    }
                    break;
                case 2 :
                    // OCL.g:345:7: nExpIterate= iterateExpression[source]
                    {
                    pushFollow(FOLLOW_iterateExpression_in_propertyCall1275);
                    nExpIterate=iterateExpression(source);

                    state._fsp--;

                     n = nExpIterate; 

                    }
                    break;
                case 3 :
                    // OCL.g:346:7: nExpOperation= operationExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_operationExpression_in_propertyCall1288);
                    nExpOperation=operationExpression(source, followsArrow);

                    state._fsp--;

                     n = nExpOperation; 

                    }
                    break;
                case 4 :
                    // OCL.g:347:7: nExpType= typeExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_typeExpression_in_propertyCall1301);
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
    // $ANTLR end "propertyCall"


    // $ANTLR start "queryExpression"
    // OCL.g:356:1: queryExpression[ASTExpression range] returns [ASTExpression n] : op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN ;
    public final ASTExpression queryExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTElemVarsDeclaration decls = null;

        ASTExpression nExp = null;


        ASTElemVarsDeclaration decl = new ASTElemVarsDeclaration(); 
        try {
            // OCL.g:357:69: (op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN )
            // OCL.g:358:5: op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN
            {
            op=(Token)match(input,IDENT,FOLLOW_IDENT_in_queryExpression1336); 
            match(input,LPAREN,FOLLOW_LPAREN_in_queryExpression1343); 
            // OCL.g:360:5: (decls= elemVarsDeclaration BAR )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==IDENT) ) {
                int LA21_1 = input.LA(2);

                if ( (LA21_1==COLON||LA21_1==COMMA||LA21_1==BAR) ) {
                    alt21=1;
                }
            }
            switch (alt21) {
                case 1 :
                    // OCL.g:360:7: decls= elemVarsDeclaration BAR
                    {
                    pushFollow(FOLLOW_elemVarsDeclaration_in_queryExpression1354);
                    decls=elemVarsDeclaration();

                    state._fsp--;

                    decl = decls;
                    match(input,BAR,FOLLOW_BAR_in_queryExpression1358); 

                    }
                    break;

            }

            pushFollow(FOLLOW_expression_in_queryExpression1369);
            nExp=expression();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_queryExpression1375); 
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
    // $ANTLR end "queryExpression"


    // $ANTLR start "iterateExpression"
    // OCL.g:374:1: iterateExpression[ASTExpression range] returns [ASTExpression n] : i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN ;
    public final ASTExpression iterateExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTElemVarsDeclaration decls = null;

        ASTVariableInitialization init = null;

        ASTExpression nExp = null;


        try {
            // OCL.g:374:65: (i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN )
            // OCL.g:375:5: i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN
            {
            i=(Token)match(input,53,FOLLOW_53_in_iterateExpression1407); 
            match(input,LPAREN,FOLLOW_LPAREN_in_iterateExpression1413); 
            pushFollow(FOLLOW_elemVarsDeclaration_in_iterateExpression1421);
            decls=elemVarsDeclaration();

            state._fsp--;

            match(input,SEMI,FOLLOW_SEMI_in_iterateExpression1423); 
            pushFollow(FOLLOW_variableInitialization_in_iterateExpression1431);
            init=variableInitialization();

            state._fsp--;

            match(input,BAR,FOLLOW_BAR_in_iterateExpression1433); 
            pushFollow(FOLLOW_expression_in_iterateExpression1441);
            nExp=expression();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_iterateExpression1447); 
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
    // $ANTLR end "iterateExpression"


    // $ANTLR start "operationExpression"
    // OCL.g:396:1: operationExpression[ASTExpression source, boolean followsArrow] returns [ASTOperationExpression n] : name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? ;
    public final ASTOperationExpression operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTOperationExpression n = null;

        Token name=null;
        Token rolename=null;
        ASTExpression e = null;


        try {
            // OCL.g:398:1: (name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? )
            // OCL.g:399:5: name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression1491); 
             n = new ASTOperationExpression(name, source, followsArrow); 
            // OCL.g:402:5: ( LBRACK rolename= IDENT RBRACK )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==LBRACK) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // OCL.g:402:7: LBRACK rolename= IDENT RBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_operationExpression1507); 
                    rolename=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression1511); 
                    match(input,RBRACK,FOLLOW_RBRACK_in_operationExpression1513); 
                     n.setExplicitRolename(rolename); 

                    }
                    break;

            }

            // OCL.g:404:5: ( AT 'pre' )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==AT) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // OCL.g:404:7: AT 'pre'
                    {
                    match(input,AT,FOLLOW_AT_in_operationExpression1526); 
                    match(input,52,FOLLOW_52_in_operationExpression1528); 
                     n.setIsPre(); 

                    }
                    break;

            }

            // OCL.g:405:5: ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==LPAREN) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // OCL.g:406:7: LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_operationExpression1549); 
                     n.hasParentheses(); 
                    // OCL.g:407:7: (e= expression ( COMMA e= expression )* )?
                    int alt25=2;
                    int LA25_0 = input.LA(1);

                    if ( (LA25_0==IDENT||LA25_0==LPAREN||(LA25_0>=PLUS && LA25_0<=MINUS)||(LA25_0>=INT && LA25_0<=HASH)||LA25_0==43||LA25_0==50||(LA25_0>=53 && LA25_0<=57)||(LA25_0>=61 && LA25_0<=72)) ) {
                        alt25=1;
                    }
                    switch (alt25) {
                        case 1 :
                            // OCL.g:408:7: e= expression ( COMMA e= expression )*
                            {
                            pushFollow(FOLLOW_expression_in_operationExpression1570);
                            e=expression();

                            state._fsp--;

                             n.addArg(e); 
                            // OCL.g:409:7: ( COMMA e= expression )*
                            loop24:
                            do {
                                int alt24=2;
                                int LA24_0 = input.LA(1);

                                if ( (LA24_0==COMMA) ) {
                                    alt24=1;
                                }


                                switch (alt24) {
                            	case 1 :
                            	    // OCL.g:409:9: COMMA e= expression
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_operationExpression1582); 
                            	    pushFollow(FOLLOW_expression_in_operationExpression1586);
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

                    match(input,RPAREN,FOLLOW_RPAREN_in_operationExpression1606); 

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
    // OCL.g:421:1: typeExpression[ASTExpression source, boolean followsArrow] returns [ASTTypeArgExpression n] : ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN ;
    public final ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTTypeArgExpression n = null;

        ASTType t = null;


         Token opToken = null; 
        try {
            // OCL.g:424:1: ( ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN )
            // OCL.g:425:2: ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN
            {
             opToken = input.LT(1); 
            if ( (input.LA(1)>=54 && input.LA(1)<=56) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_typeExpression1665); 
            pushFollow(FOLLOW_type_in_typeExpression1669);
            t=type();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_typeExpression1671); 
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
    // $ANTLR end "typeExpression"


    // $ANTLR start "elemVarsDeclaration"
    // OCL.g:436:1: elemVarsDeclaration returns [ASTElemVarsDeclaration n] : idListRes= idList ( COLON t= type )? ;
    public final ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException {
        ASTElemVarsDeclaration n = null;

        List idListRes = null;

        ASTType t = null;


         List idList; 
        try {
            // OCL.g:438:1: (idListRes= idList ( COLON t= type )? )
            // OCL.g:439:5: idListRes= idList ( COLON t= type )?
            {
            pushFollow(FOLLOW_idList_in_elemVarsDeclaration1710);
            idListRes=idList();

            state._fsp--;

            // OCL.g:440:5: ( COLON t= type )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==COLON) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // OCL.g:440:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_elemVarsDeclaration1718); 
                    pushFollow(FOLLOW_type_in_elemVarsDeclaration1722);
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
    // $ANTLR end "elemVarsDeclaration"


    // $ANTLR start "variableInitialization"
    // OCL.g:449:1: variableInitialization returns [ASTVariableInitialization n] : name= IDENT COLON t= type EQUAL e= expression ;
    public final ASTVariableInitialization variableInitialization() throws RecognitionException {
        ASTVariableInitialization n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // OCL.g:450:1: (name= IDENT COLON t= type EQUAL e= expression )
            // OCL.g:451:5: name= IDENT COLON t= type EQUAL e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableInitialization1757); 
            match(input,COLON,FOLLOW_COLON_in_variableInitialization1759); 
            pushFollow(FOLLOW_type_in_variableInitialization1763);
            t=type();

            state._fsp--;

            match(input,EQUAL,FOLLOW_EQUAL_in_variableInitialization1765); 
            pushFollow(FOLLOW_expression_in_variableInitialization1769);
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
    // $ANTLR end "variableInitialization"


    // $ANTLR start "ifExpression"
    // OCL.g:460:1: ifExpression returns [ASTExpression n] : i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' ;
    public final ASTExpression ifExpression() throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTExpression cond = null;

        ASTExpression t = null;

        ASTExpression e = null;


        try {
            // OCL.g:461:1: (i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' )
            // OCL.g:462:5: i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif'
            {
            i=(Token)match(input,57,FOLLOW_57_in_ifExpression1801); 
            pushFollow(FOLLOW_expression_in_ifExpression1805);
            cond=expression();

            state._fsp--;

            match(input,58,FOLLOW_58_in_ifExpression1807); 
            pushFollow(FOLLOW_expression_in_ifExpression1811);
            t=expression();

            state._fsp--;

            match(input,59,FOLLOW_59_in_ifExpression1813); 
            pushFollow(FOLLOW_expression_in_ifExpression1817);
            e=expression();

            state._fsp--;

            match(input,60,FOLLOW_60_in_ifExpression1819); 
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
    // $ANTLR end "ifExpression"


    // $ANTLR start "literal"
    // OCL.g:481:1: literal returns [ASTExpression n] : (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral );
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

        ASTDateLiteral nDateLit = null;


        try {
            // OCL.g:482:1: (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral )
            int alt28=11;
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
            case 66:
                {
                alt28=7;
                }
                break;
            case 67:
                {
                alt28=8;
                }
                break;
            case 68:
            case 69:
            case 70:
                {
                alt28=9;
                }
                break;
            case 71:
                {
                alt28=10;
                }
                break;
            case 72:
                {
                alt28=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }

            switch (alt28) {
                case 1 :
                    // OCL.g:483:7: t= 'true'
                    {
                    t=(Token)match(input,61,FOLLOW_61_in_literal1858); 
                     n = new ASTBooleanLiteral(true); 

                    }
                    break;
                case 2 :
                    // OCL.g:484:7: f= 'false'
                    {
                    f=(Token)match(input,62,FOLLOW_62_in_literal1872); 
                     n = new ASTBooleanLiteral(false); 

                    }
                    break;
                case 3 :
                    // OCL.g:485:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_literal1885); 
                     n = new ASTIntegerLiteral(i); 

                    }
                    break;
                case 4 :
                    // OCL.g:486:7: r= REAL
                    {
                    r=(Token)match(input,REAL,FOLLOW_REAL_in_literal1900); 
                     n = new ASTRealLiteral(r); 

                    }
                    break;
                case 5 :
                    // OCL.g:487:7: s= STRING
                    {
                    s=(Token)match(input,STRING,FOLLOW_STRING_in_literal1914); 
                     n = new ASTStringLiteral(s); 

                    }
                    break;
                case 6 :
                    // OCL.g:488:7: HASH enumLit= IDENT
                    {
                    match(input,HASH,FOLLOW_HASH_in_literal1924); 
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal1928); 
                     n = new ASTEnumLiteral(enumLit); 

                    }
                    break;
                case 7 :
                    // OCL.g:489:7: nColIt= collectionLiteral
                    {
                    pushFollow(FOLLOW_collectionLiteral_in_literal1940);
                    nColIt=collectionLiteral();

                    state._fsp--;

                     n = nColIt; 

                    }
                    break;
                case 8 :
                    // OCL.g:490:7: nEColIt= emptyCollectionLiteral
                    {
                    pushFollow(FOLLOW_emptyCollectionLiteral_in_literal1952);
                    nEColIt=emptyCollectionLiteral();

                    state._fsp--;

                     n = nEColIt; 

                    }
                    break;
                case 9 :
                    // OCL.g:491:7: nUndLit= undefinedLiteral
                    {
                    pushFollow(FOLLOW_undefinedLiteral_in_literal1964);
                    nUndLit=undefinedLiteral();

                    state._fsp--;

                    n = nUndLit; 

                    }
                    break;
                case 10 :
                    // OCL.g:492:7: nTupleLit= tupleLiteral
                    {
                    pushFollow(FOLLOW_tupleLiteral_in_literal1976);
                    nTupleLit=tupleLiteral();

                    state._fsp--;

                    n = nTupleLit; 

                    }
                    break;
                case 11 :
                    // OCL.g:493:7: nDateLit= dateLiteral
                    {
                    pushFollow(FOLLOW_dateLiteral_in_literal1988);
                    nDateLit=dateLiteral();

                    state._fsp--;

                    n = nDateLit; 

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
    // OCL.g:501:1: collectionLiteral returns [ASTCollectionLiteral n] : ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE ;
    public final ASTCollectionLiteral collectionLiteral() throws RecognitionException {
        ASTCollectionLiteral n = null;

        ASTCollectionItem ci = null;


         Token op = null; 
        try {
            // OCL.g:503:1: ( ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE )
            // OCL.g:504:5: ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE
            {
             op = input.LT(1); 
            if ( (input.LA(1)>=63 && input.LA(1)<=66) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

             n = new ASTCollectionLiteral(op); 
            match(input,LBRACE,FOLLOW_LBRACE_in_collectionLiteral2055); 
            // OCL.g:508:5: (ci= collectionItem ( COMMA ci= collectionItem )* )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==IDENT||LA30_0==LPAREN||(LA30_0>=PLUS && LA30_0<=MINUS)||(LA30_0>=INT && LA30_0<=HASH)||LA30_0==43||LA30_0==50||(LA30_0>=53 && LA30_0<=57)||(LA30_0>=61 && LA30_0<=72)) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // OCL.g:509:7: ci= collectionItem ( COMMA ci= collectionItem )*
                    {
                    pushFollow(FOLLOW_collectionItem_in_collectionLiteral2072);
                    ci=collectionItem();

                    state._fsp--;

                     n.addItem(ci); 
                    // OCL.g:510:7: ( COMMA ci= collectionItem )*
                    loop29:
                    do {
                        int alt29=2;
                        int LA29_0 = input.LA(1);

                        if ( (LA29_0==COMMA) ) {
                            alt29=1;
                        }


                        switch (alt29) {
                    	case 1 :
                    	    // OCL.g:510:9: COMMA ci= collectionItem
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_collectionLiteral2085); 
                    	    pushFollow(FOLLOW_collectionItem_in_collectionLiteral2089);
                    	    ci=collectionItem();

                    	    state._fsp--;

                    	     n.addItem(ci); 

                    	    }
                    	    break;

                    	default :
                    	    break loop29;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RBRACE,FOLLOW_RBRACE_in_collectionLiteral2108); 

            }

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
    // OCL.g:519:1: collectionItem returns [ASTCollectionItem n] : e= expression ( DOTDOT e= expression )? ;
    public final ASTCollectionItem collectionItem() throws RecognitionException {
        ASTCollectionItem n = null;

        ASTExpression e = null;


         n = new ASTCollectionItem(); 
        try {
            // OCL.g:521:1: (e= expression ( DOTDOT e= expression )? )
            // OCL.g:522:5: e= expression ( DOTDOT e= expression )?
            {
            pushFollow(FOLLOW_expression_in_collectionItem2137);
            e=expression();

            state._fsp--;

             n.setFirst(e); 
            // OCL.g:523:5: ( DOTDOT e= expression )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==DOTDOT) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // OCL.g:523:7: DOTDOT e= expression
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_collectionItem2148); 
                    pushFollow(FOLLOW_expression_in_collectionItem2152);
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
    // $ANTLR end "collectionItem"


    // $ANTLR start "emptyCollectionLiteral"
    // OCL.g:533:1: emptyCollectionLiteral returns [ASTEmptyCollectionLiteral n] : 'oclEmpty' LPAREN t= collectionType RPAREN ;
    public final ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException {
        ASTEmptyCollectionLiteral n = null;

        ASTCollectionType t = null;


        try {
            // OCL.g:534:1: ( 'oclEmpty' LPAREN t= collectionType RPAREN )
            // OCL.g:535:5: 'oclEmpty' LPAREN t= collectionType RPAREN
            {
            match(input,67,FOLLOW_67_in_emptyCollectionLiteral2181); 
            match(input,LPAREN,FOLLOW_LPAREN_in_emptyCollectionLiteral2183); 
            pushFollow(FOLLOW_collectionType_in_emptyCollectionLiteral2187);
            t=collectionType();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_emptyCollectionLiteral2189); 
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
    // $ANTLR end "emptyCollectionLiteral"


    // $ANTLR start "undefinedLiteral"
    // OCL.g:546:1: undefinedLiteral returns [ASTUndefinedLiteral n] : ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' );
    public final ASTUndefinedLiteral undefinedLiteral() throws RecognitionException {
        ASTUndefinedLiteral n = null;

        ASTType t = null;


        try {
            // OCL.g:547:1: ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' )
            int alt32=3;
            switch ( input.LA(1) ) {
            case 68:
                {
                alt32=1;
                }
                break;
            case 69:
                {
                alt32=2;
                }
                break;
            case 70:
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
                    // OCL.g:548:5: 'oclUndefined' LPAREN t= type RPAREN
                    {
                    match(input,68,FOLLOW_68_in_undefinedLiteral2219); 
                    match(input,LPAREN,FOLLOW_LPAREN_in_undefinedLiteral2221); 
                    pushFollow(FOLLOW_type_in_undefinedLiteral2225);
                    t=type();

                    state._fsp--;

                    match(input,RPAREN,FOLLOW_RPAREN_in_undefinedLiteral2227); 
                     n = new ASTUndefinedLiteral(t); 

                    }
                    break;
                case 2 :
                    // OCL.g:551:5: 'Undefined'
                    {
                    match(input,69,FOLLOW_69_in_undefinedLiteral2241); 
                     n = new ASTUndefinedLiteral(); 

                    }
                    break;
                case 3 :
                    // OCL.g:554:5: 'null'
                    {
                    match(input,70,FOLLOW_70_in_undefinedLiteral2255); 
                     n = new ASTUndefinedLiteral(); 

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
    // OCL.g:563:1: tupleLiteral returns [ASTTupleLiteral n] : 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE ;
    public final ASTTupleLiteral tupleLiteral() throws RecognitionException {
        ASTTupleLiteral n = null;

        ASTTupleItem ti = null;


         List tiList = new ArrayList(); 
        try {
            // OCL.g:565:1: ( 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE )
            // OCL.g:566:5: 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE
            {
            match(input,71,FOLLOW_71_in_tupleLiteral2289); 
            match(input,LBRACE,FOLLOW_LBRACE_in_tupleLiteral2295); 
            pushFollow(FOLLOW_tupleItem_in_tupleLiteral2303);
            ti=tupleItem();

            state._fsp--;

             tiList.add(ti); 
            // OCL.g:569:5: ( COMMA ti= tupleItem )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==COMMA) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // OCL.g:569:7: COMMA ti= tupleItem
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleLiteral2314); 
            	    pushFollow(FOLLOW_tupleItem_in_tupleLiteral2318);
            	    ti=tupleItem();

            	    state._fsp--;

            	     tiList.add(ti); 

            	    }
            	    break;

            	default :
            	    break loop33;
                }
            } while (true);

            match(input,RBRACE,FOLLOW_RBRACE_in_tupleLiteral2329); 
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
    // $ANTLR end "tupleLiteral"


    // $ANTLR start "tupleItem"
    // OCL.g:577:1: tupleItem returns [ASTTupleItem n] : name= IDENT ( COLON | EQUAL ) e= expression ;
    public final ASTTupleItem tupleItem() throws RecognitionException {
        ASTTupleItem n = null;

        Token name=null;
        ASTExpression e = null;


        try {
            // OCL.g:578:1: (name= IDENT ( COLON | EQUAL ) e= expression )
            // OCL.g:579:5: name= IDENT ( COLON | EQUAL ) e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tupleItem2360); 
            if ( (input.LA(1)>=COLON && input.LA(1)<=EQUAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            pushFollow(FOLLOW_expression_in_tupleItem2370);
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
    // $ANTLR end "tupleItem"


    // $ANTLR start "dateLiteral"
    // OCL.g:587:1: dateLiteral returns [ASTDateLiteral n] : 'Date' LBRACE v= STRING RBRACE ;
    public final ASTDateLiteral dateLiteral() throws RecognitionException {
        ASTDateLiteral n = null;

        Token v=null;

        try {
            // OCL.g:588:1: ( 'Date' LBRACE v= STRING RBRACE )
            // OCL.g:589:5: 'Date' LBRACE v= STRING RBRACE
            {
            match(input,72,FOLLOW_72_in_dateLiteral2401); 
            match(input,LBRACE,FOLLOW_LBRACE_in_dateLiteral2403); 
            v=(Token)match(input,STRING,FOLLOW_STRING_in_dateLiteral2407); 
            match(input,RBRACE,FOLLOW_RBRACE_in_dateLiteral2409); 
             n = new ASTDateLiteral( v ); 

            }

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
    // OCL.g:599:1: type returns [ASTType n] : (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) ;
    public final ASTType type() throws RecognitionException {
        ASTType n = null;

        ASTSimpleType nTSimple = null;

        ASTCollectionType nTCollection = null;

        ASTTupleType nTTuple = null;


         Token tok = null; 
        try {
            // OCL.g:601:1: ( (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) )
            // OCL.g:602:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            {
             tok = input.LT(1); /* remember start of type */ 
            // OCL.g:603:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            int alt34=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt34=1;
                }
                break;
            case 63:
            case 64:
            case 65:
            case 66:
            case 73:
                {
                alt34=2;
                }
                break;
            case 71:
                {
                alt34=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 34, 0, input);

                throw nvae;
            }

            switch (alt34) {
                case 1 :
                    // OCL.g:604:7: nTSimple= simpleType
                    {
                    pushFollow(FOLLOW_simpleType_in_type2459);
                    nTSimple=simpleType();

                    state._fsp--;

                     n = nTSimple; n.setStartToken(tok); 

                    }
                    break;
                case 2 :
                    // OCL.g:605:7: nTCollection= collectionType
                    {
                    pushFollow(FOLLOW_collectionType_in_type2471);
                    nTCollection=collectionType();

                    state._fsp--;

                     n = nTCollection; n.setStartToken(tok); 

                    }
                    break;
                case 3 :
                    // OCL.g:606:7: nTTuple= tupleType
                    {
                    pushFollow(FOLLOW_tupleType_in_type2483);
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
    // $ANTLR end "type"


    // $ANTLR start "typeOnly"
    // OCL.g:611:1: typeOnly returns [ASTType n] : nT= type EOF ;
    public final ASTType typeOnly() throws RecognitionException {
        ASTType n = null;

        ASTType nT = null;


        try {
            // OCL.g:612:1: (nT= type EOF )
            // OCL.g:613:5: nT= type EOF
            {
            pushFollow(FOLLOW_type_in_typeOnly2515);
            nT=type();

            state._fsp--;

            match(input,EOF,FOLLOW_EOF_in_typeOnly2517); 
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
    // $ANTLR end "typeOnly"


    // $ANTLR start "simpleType"
    // OCL.g:623:1: simpleType returns [ASTSimpleType n] : name= IDENT ;
    public final ASTSimpleType simpleType() throws RecognitionException {
        ASTSimpleType n = null;

        Token name=null;

        try {
            // OCL.g:624:1: (name= IDENT )
            // OCL.g:625:5: name= IDENT
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_simpleType2545); 
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
    // $ANTLR end "simpleType"


    // $ANTLR start "collectionType"
    // OCL.g:633:1: collectionType returns [ASTCollectionType n] : ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN ;
    public final ASTCollectionType collectionType() throws RecognitionException {
        ASTCollectionType n = null;

        ASTType elemType = null;


         Token op = null; 
        try {
            // OCL.g:635:1: ( ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN )
            // OCL.g:636:5: ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN
            {
             op = input.LT(1); 
            if ( (input.LA(1)>=63 && input.LA(1)<=66)||input.LA(1)==73 ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_collectionType2610); 
            pushFollow(FOLLOW_type_in_collectionType2614);
            elemType=type();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_collectionType2616); 
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
    // $ANTLR end "collectionType"


    // $ANTLR start "tupleType"
    // OCL.g:646:1: tupleType returns [ASTTupleType n] : 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN ;
    public final ASTTupleType tupleType() throws RecognitionException {
        ASTTupleType n = null;

        ASTTuplePart tp = null;


         List tpList = new ArrayList(); 
        try {
            // OCL.g:648:1: ( 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN )
            // OCL.g:649:5: 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN
            {
            match(input,71,FOLLOW_71_in_tupleType2650); 
            match(input,LPAREN,FOLLOW_LPAREN_in_tupleType2652); 
            pushFollow(FOLLOW_tuplePart_in_tupleType2661);
            tp=tuplePart();

            state._fsp--;

             tpList.add(tp); 
            // OCL.g:651:5: ( COMMA tp= tuplePart )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==COMMA) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // OCL.g:651:7: COMMA tp= tuplePart
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleType2672); 
            	    pushFollow(FOLLOW_tuplePart_in_tupleType2676);
            	    tp=tuplePart();

            	    state._fsp--;

            	     tpList.add(tp); 

            	    }
            	    break;

            	default :
            	    break loop35;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_tupleType2688); 
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
    // $ANTLR end "tupleType"


    // $ANTLR start "tuplePart"
    // OCL.g:660:1: tuplePart returns [ASTTuplePart n] : name= IDENT COLON t= type ;
    public final ASTTuplePart tuplePart() throws RecognitionException {
        ASTTuplePart n = null;

        Token name=null;
        ASTType t = null;


        try {
            // OCL.g:661:1: (name= IDENT COLON t= type )
            // OCL.g:662:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tuplePart2720); 
            match(input,COLON,FOLLOW_COLON_in_tuplePart2722); 
            pushFollow(FOLLOW_type_in_tuplePart2726);
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
    // $ANTLR end "tuplePart"

    // Delegated rules


 

    public static final BitSet FOLLOW_expression_in_expressionOnly68 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_expressionOnly70 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_43_in_expression118 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_expression122 = new BitSet(new long[]{0x0000000000000060L});
    public static final BitSet FOLLOW_COLON_in_expression126 = new BitSet(new long[]{0x8000000000000010L,0x0000000000000287L});
    public static final BitSet FOLLOW_type_in_expression130 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_EQUAL_in_expression135 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_expression_in_expression139 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_44_in_expression141 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_conditionalImpliesExpression_in_expression166 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_paramList199 = new BitSet(new long[]{0x0000000000000210L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList216 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_COMMA_in_paramList228 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList232 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_RPAREN_in_paramList252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_idList281 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_idList291 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_idList295 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_IDENT_in_variableDeclaration326 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_variableDeclaration328 = new BitSet(new long[]{0x8000000000000010L,0x0000000000000287L});
    public static final BitSet FOLLOW_type_in_variableDeclaration332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression368 = new BitSet(new long[]{0x0000200000000002L});
    public static final BitSet FOLLOW_45_in_conditionalImpliesExpression381 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression385 = new BitSet(new long[]{0x0000200000000002L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression430 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_46_in_conditionalOrExpression443 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression447 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression491 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_47_in_conditionalXOrExpression504 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression508 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression552 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_48_in_conditionalAndExpression565 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression569 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression617 = new BitSet(new long[]{0x0000000000000442L});
    public static final BitSet FOLLOW_set_in_equalityExpression636 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression646 = new BitSet(new long[]{0x0000000000000442L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression695 = new BitSet(new long[]{0x0000000000007802L});
    public static final BitSet FOLLOW_set_in_relationalExpression713 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression731 = new BitSet(new long[]{0x0000000000007802L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression781 = new BitSet(new long[]{0x0000000000018002L});
    public static final BitSet FOLLOW_set_in_additiveExpression799 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression809 = new BitSet(new long[]{0x0000000000018002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression859 = new BitSet(new long[]{0x0002000000060002L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression877 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression891 = new BitSet(new long[]{0x0002000000060002L});
    public static final BitSet FOLLOW_set_in_unaryExpression953 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression977 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpression_in_unaryExpression997 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_postfixExpression1030 = new BitSet(new long[]{0x0000000000180002L});
    public static final BitSet FOLLOW_ARROW_in_postfixExpression1048 = new BitSet(new long[]{0x01E0000000000010L});
    public static final BitSet FOLLOW_DOT_in_postfixExpression1054 = new BitSet(new long[]{0x01E0000000000010L});
    public static final BitSet FOLLOW_propertyCall_in_postfixExpression1065 = new BitSet(new long[]{0x0000000000180002L});
    public static final BitSet FOLLOW_literal_in_primaryExpression1105 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyCall_in_primaryExpression1117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression1128 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_expression_in_primaryExpression1132 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression1134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifExpression_in_primaryExpression1146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_primaryExpression1163 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_DOT_in_primaryExpression1165 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_primaryExpression1167 = new BitSet(new long[]{0x0000000000200082L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression1171 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression1173 = new BitSet(new long[]{0x0000000000200002L});
    public static final BitSet FOLLOW_AT_in_primaryExpression1194 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_primaryExpression1196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_queryExpression_in_propertyCall1262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterateExpression_in_propertyCall1275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationExpression_in_propertyCall1288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeExpression_in_propertyCall1301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_queryExpression1336 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_queryExpression1343 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_queryExpression1354 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_BAR_in_queryExpression1358 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_expression_in_queryExpression1369 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_queryExpression1375 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_53_in_iterateExpression1407 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_iterateExpression1413 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_iterateExpression1421 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_SEMI_in_iterateExpression1423 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableInitialization_in_iterateExpression1431 = new BitSet(new long[]{0x0000000000400000L});
    public static final BitSet FOLLOW_BAR_in_iterateExpression1433 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_expression_in_iterateExpression1441 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_iterateExpression1447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression1491 = new BitSet(new long[]{0x0000000001200082L});
    public static final BitSet FOLLOW_LBRACK_in_operationExpression1507 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression1511 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_RBRACK_in_operationExpression1513 = new BitSet(new long[]{0x0000000000200082L});
    public static final BitSet FOLLOW_AT_in_operationExpression1526 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_52_in_operationExpression1528 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_LPAREN_in_operationExpression1549 = new BitSet(new long[]{0xE3E408003C018290L,0x00000000000001FFL});
    public static final BitSet FOLLOW_expression_in_operationExpression1570 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_COMMA_in_operationExpression1582 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_expression_in_operationExpression1586 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_RPAREN_in_operationExpression1606 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_typeExpression1649 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_typeExpression1665 = new BitSet(new long[]{0x8000000000000010L,0x0000000000000287L});
    public static final BitSet FOLLOW_type_in_typeExpression1669 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_typeExpression1671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_elemVarsDeclaration1710 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_COLON_in_elemVarsDeclaration1718 = new BitSet(new long[]{0x8000000000000010L,0x0000000000000287L});
    public static final BitSet FOLLOW_type_in_elemVarsDeclaration1722 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableInitialization1757 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_variableInitialization1759 = new BitSet(new long[]{0x8000000000000010L,0x0000000000000287L});
    public static final BitSet FOLLOW_type_in_variableInitialization1763 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_EQUAL_in_variableInitialization1765 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_expression_in_variableInitialization1769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_ifExpression1801 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_expression_in_ifExpression1805 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_ifExpression1807 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_expression_in_ifExpression1811 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_59_in_ifExpression1813 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_expression_in_ifExpression1817 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_ifExpression1819 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_literal1858 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_literal1872 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_literal1885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REAL_in_literal1900 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal1914 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_literal1924 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_literal1928 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionLiteral_in_literal1940 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyCollectionLiteral_in_literal1952 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedLiteral_in_literal1964 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleLiteral_in_literal1976 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dateLiteral_in_literal1988 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionLiteral2026 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_LBRACE_in_collectionLiteral2055 = new BitSet(new long[]{0xE3E40800BC018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral2072 = new BitSet(new long[]{0x0000000080000100L});
    public static final BitSet FOLLOW_COMMA_in_collectionLiteral2085 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral2089 = new BitSet(new long[]{0x0000000080000100L});
    public static final BitSet FOLLOW_RBRACE_in_collectionLiteral2108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_collectionItem2137 = new BitSet(new long[]{0x0000000100000002L});
    public static final BitSet FOLLOW_DOTDOT_in_collectionItem2148 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_expression_in_collectionItem2152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_67_in_emptyCollectionLiteral2181 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_emptyCollectionLiteral2183 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000207L});
    public static final BitSet FOLLOW_collectionType_in_emptyCollectionLiteral2187 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_emptyCollectionLiteral2189 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_undefinedLiteral2219 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_undefinedLiteral2221 = new BitSet(new long[]{0x8000000000000010L,0x0000000000000287L});
    public static final BitSet FOLLOW_type_in_undefinedLiteral2225 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_undefinedLiteral2227 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_undefinedLiteral2241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_70_in_undefinedLiteral2255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_tupleLiteral2289 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_LBRACE_in_tupleLiteral2295 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral2303 = new BitSet(new long[]{0x0000000080000100L});
    public static final BitSet FOLLOW_COMMA_in_tupleLiteral2314 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral2318 = new BitSet(new long[]{0x0000000080000100L});
    public static final BitSet FOLLOW_RBRACE_in_tupleLiteral2329 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tupleItem2360 = new BitSet(new long[]{0x0000000000000060L});
    public static final BitSet FOLLOW_set_in_tupleItem2362 = new BitSet(new long[]{0xE3E408003C018090L,0x00000000000001FFL});
    public static final BitSet FOLLOW_expression_in_tupleItem2370 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_dateLiteral2401 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_LBRACE_in_dateLiteral2403 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_STRING_in_dateLiteral2407 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_RBRACE_in_dateLiteral2409 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleType_in_type2459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_type2471 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleType_in_type2483 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeOnly2515 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_typeOnly2517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_simpleType2545 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionType2583 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_collectionType2610 = new BitSet(new long[]{0x8000000000000010L,0x0000000000000287L});
    public static final BitSet FOLLOW_type_in_collectionType2614 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_collectionType2616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_tupleType2650 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_tupleType2652 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType2661 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_COMMA_in_tupleType2672 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType2676 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_RPAREN_in_tupleType2688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tuplePart2720 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_COLON_in_tuplePart2722 = new BitSet(new long[]{0x8000000000000010L,0x0000000000000287L});
    public static final BitSet FOLLOW_type_in_tuplePart2726 = new BitSet(new long[]{0x0000000000000002L});

}