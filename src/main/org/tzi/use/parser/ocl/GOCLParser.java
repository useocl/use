// $ANTLR 3.1b1 GOCL.g 2009-03-27 14:16:36
 
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
//  USE parser
// ------------------------------------

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.PrintWriter;
import org.tzi.use.parser.ParseErrorHandler;
import org.tzi.use.parser.use.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class GOCLParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LITERAL_oclAsType", "LITERAL_oclIsKindOf", "LITERAL_oclIsTypeOf", "LPAREN", "COMMA", "RPAREN", "IDENT", "COLON", "EQUAL", "NOT_EQUAL", "LESS", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "PLUS", "MINUS", "STAR", "SLASH", "ARROW", "DOT", "AT", "BAR", "SEMI", "LBRACK", "RBRACK", "INT", "REAL", "STRING", "HASH", "LBRACE", "RBRACE", "DOTDOT", "NEWLINE", "WS", "SL_COMMENT", "ML_COMMENT", "COLON_COLON", "COLON_EQUAL", "RANGE_OR_INT", "ESC", "HEX_DIGIT", "VOCAB", "'let'", "'in'", "'implies'", "'or'", "'xor'", "'and'", "'div'", "'not'", "'allInstances'", "'pre'", "'iterate'", "'if'", "'then'", "'else'", "'endif'", "'true'", "'false'", "'Set'", "'Sequence'", "'Bag'", "'oclEmpty'", "'oclUndefined'", "'Tuple'", "'Collection'"
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
    public static final int T__57=57;
    public static final int RPAREN=9;
    public static final int T__58=58;
    public static final int COLON_EQUAL=41;
    public static final int SLASH=21;
    public static final int T__51=51;
    public static final int GREATER=15;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int COMMA=8;
    public static final int NOT_EQUAL=13;
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
    public static final int T__47=47;
    public static final int RBRACE=34;
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
    public static final int NEWLINE=36;
    public static final int SL_COMMENT=38;
    public static final int VOCAB=45;
    public static final int ARROW=22;
    public static final int LESS_EQUAL=16;
    public static final int GREATER_EQUAL=17;
    public static final int BAR=25;
    public static final int STRING=31;

    // delegates
    public GOCL_GOCLBase gGOCLBase;
    // delegators


        public GOCLParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public GOCLParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            gGOCLBase = new GOCL_GOCLBase(input, state, this);
        }
        

    public String[] getTokenNames() { return GOCLParser.tokenNames; }
    public String getGrammarFileName() { return "GOCL.g"; }

      
        final static String Q_COLLECT  = "collect";
        final static String Q_COLLECTNESTED  = "collectNested";
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
        final static int Q_COLLECTNESTED_ID  = 10;
    
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
            queryIdentMap.put(Q_COLLECTNESTED, new Integer(Q_COLLECTNESTED_ID));
        }
    
        protected boolean isQueryIdent(Token t) {
            return queryIdentMap.containsKey(t.getText());
        }
        
        public void init(ParseErrorHandler handler) {
            fParseErrorHandler = handler;
    		this.gGOCLBase.init(handler);
        }
    
        /* Overridden methods. */
    	private ParseErrorHandler fParseErrorHandler;
        
        public void emitErrorMessage(String msg) {
           	fParseErrorHandler.reportError(msg);
    	}



    // $ANTLR start expressionOnly
    // GOCL.g:131:1: expressionOnly returns [ASTExpression n] : nExp= expression EOF ;
    public final ASTExpression expressionOnly() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExp = null;


        try {
            // GOCL.g:132:1: (nExp= expression EOF )
            // GOCL.g:133:5: nExp= expression EOF
            {
            pushFollow(FOLLOW_expression_in_expressionOnly60);
            nExp=expression();

            state._fsp--;

            match(input,EOF,FOLLOW_EOF_in_expressionOnly62); 
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
    // $ANTLR end expressionOnly

    // Delegated rules
    public ASTExpression multiplicativeExpression() throws RecognitionException { return gGOCLBase.multiplicativeExpression(); }
    public ASTExpression equalityExpression() throws RecognitionException { return gGOCLBase.equalityExpression(); }
    public ASTExpression conditionalAndExpression() throws RecognitionException { return gGOCLBase.conditionalAndExpression(); }
    public ASTVariableDeclaration variableDeclaration() throws RecognitionException { return gGOCLBase.variableDeclaration(); }
    public ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException { return gGOCLBase.elemVarsDeclaration(); }
    public ASTCollectionItem collectionItem() throws RecognitionException { return gGOCLBase.collectionItem(); }
    public ASTExpression relationalExpression() throws RecognitionException { return gGOCLBase.relationalExpression(); }
    public ASTExpression literal() throws RecognitionException { return gGOCLBase.literal(); }
    public ASTCollectionType collectionType() throws RecognitionException { return gGOCLBase.collectionType(); }
    public ASTTupleItem tupleItem() throws RecognitionException { return gGOCLBase.tupleItem(); }
    public ASTExpression queryExpression(ASTExpression range) throws RecognitionException { return gGOCLBase.queryExpression(range); }
    public ASTExpression conditionalOrExpression() throws RecognitionException { return gGOCLBase.conditionalOrExpression(); }
    public List idList() throws RecognitionException { return gGOCLBase.idList(); }
    public ASTUndefinedLiteral undefinedLiteral() throws RecognitionException { return gGOCLBase.undefinedLiteral(); }
    public List paramList() throws RecognitionException { return gGOCLBase.paramList(); }
    public ASTExpression postfixExpression() throws RecognitionException { return gGOCLBase.postfixExpression(); }
    public ASTExpression conditionalImpliesExpression() throws RecognitionException { return gGOCLBase.conditionalImpliesExpression(); }
    public ASTCollectionLiteral collectionLiteral() throws RecognitionException { return gGOCLBase.collectionLiteral(); }
    public ASTVariableInitialization variableInitialization() throws RecognitionException { return gGOCLBase.variableInitialization(); }
    public ASTExpression unaryExpression() throws RecognitionException { return gGOCLBase.unaryExpression(); }
    public ASTExpression conditionalXOrExpression() throws RecognitionException { return gGOCLBase.conditionalXOrExpression(); }
    public ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException { return gGOCLBase.propertyCall(source, followsArrow); }
    public ASTTupleLiteral tupleLiteral() throws RecognitionException { return gGOCLBase.tupleLiteral(); }
    public ASTExpression additiveExpression() throws RecognitionException { return gGOCLBase.additiveExpression(); }
    public ASTExpression primaryExpression() throws RecognitionException { return gGOCLBase.primaryExpression(); }
    public ASTTupleType tupleType() throws RecognitionException { return gGOCLBase.tupleType(); }
    public ASTExpression ifExpression() throws RecognitionException { return gGOCLBase.ifExpression(); }
    public ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException { return gGOCLBase.emptyCollectionLiteral(); }
    public ASTType type() throws RecognitionException { return gGOCLBase.type(); }
    public ASTTuplePart tuplePart() throws RecognitionException { return gGOCLBase.tuplePart(); }
    public ASTType typeOnly() throws RecognitionException { return gGOCLBase.typeOnly(); }
    public ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException { return gGOCLBase.typeExpression(source, followsArrow); }
    public ASTExpression expression() throws RecognitionException { return gGOCLBase.expression(); }
    public ASTOperationExpression operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException { return gGOCLBase.operationExpression(source, followsArrow); }
    public ASTSimpleType simpleType() throws RecognitionException { return gGOCLBase.simpleType(); }
    public ASTExpression iterateExpression(ASTExpression range) throws RecognitionException { return gGOCLBase.iterateExpression(range); }


 

    public static final BitSet FOLLOW_expression_in_expressionOnly60 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_expressionOnly62 = new BitSet(new long[]{0x0000000000000002L});

}