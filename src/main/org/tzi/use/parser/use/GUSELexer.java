// $ANTLR 3.1b1 GUSE__.g 2009-03-27 14:16:39

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

import org.tzi.use.parser.ParseErrorHandler;
// ------------------------------------
//  USE parser
// ------------------------------------


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class GUSELexer extends Lexer {
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
    public static final int T__82=82;
    public static final int RBRACE=34;
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
    public static final int T__71=71;
    public static final int WS=39;
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
    public static final int T__102=102;
    public static final int COLON_COLON=36;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int MINUS=19;
    public static final int Tokens=105;
    public static final int SEMI=26;
    public static final int COLON=11;
    public static final int NEWLINE=38;
    public static final int VOCAB=45;
    public static final int ARROW=22;
    public static final int GREATER_EQUAL=17;
    public static final int BAR=25;
    public static final int STRING=31;
    
    	public void init(ParseErrorHandler handler) {
    		fParseErrorHandler = handler;
    	}
    	
    	public String getFilename() {
            return fParseErrorHandler.getFileName();
        }
        
    	/* Overridden methods. */
    	private ParseErrorHandler fParseErrorHandler;
        
    	public void emitErrorMessage(String msg) {
           	fParseErrorHandler.reportError(msg);
    	}


    // delegates
    public GUSE_GOCLLexerRules gGOCLLexerRules;
    // delegators

    public GUSELexer() {;} 
    public GUSELexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public GUSELexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
        gGOCLLexerRules = new GUSE_GOCLLexerRules(input, state, this);
    }
    public String getGrammarFileName() { return "GUSE__.g"; }

    // $ANTLR start LITERAL_oclAsType
    public final void mLITERAL_oclAsType() throws RecognitionException {
        try {
            int _type = LITERAL_oclAsType;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:47:19: ( 'oclAsType' )
            // GUSE__.g:47:21: 'oclAsType'
            {
            match("oclAsType"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end LITERAL_oclAsType

    // $ANTLR start LITERAL_oclIsKindOf
    public final void mLITERAL_oclIsKindOf() throws RecognitionException {
        try {
            int _type = LITERAL_oclIsKindOf;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:48:21: ( 'oclIsKindOf' )
            // GUSE__.g:48:23: 'oclIsKindOf'
            {
            match("oclIsKindOf"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end LITERAL_oclIsKindOf

    // $ANTLR start LITERAL_oclIsTypeOf
    public final void mLITERAL_oclIsTypeOf() throws RecognitionException {
        try {
            int _type = LITERAL_oclIsTypeOf;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:49:21: ( 'oclIsTypeOf' )
            // GUSE__.g:49:23: 'oclIsTypeOf'
            {
            match("oclIsTypeOf"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end LITERAL_oclIsTypeOf

    // $ANTLR start T__46
    public final void mT__46() throws RecognitionException {
        try {
            int _type = T__46;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:50:7: ( 'let' )
            // GUSE__.g:50:9: 'let'
            {
            match("let"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__46

    // $ANTLR start T__47
    public final void mT__47() throws RecognitionException {
        try {
            int _type = T__47;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:51:7: ( 'in' )
            // GUSE__.g:51:9: 'in'
            {
            match("in"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__47

    // $ANTLR start T__48
    public final void mT__48() throws RecognitionException {
        try {
            int _type = T__48;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:52:7: ( 'implies' )
            // GUSE__.g:52:9: 'implies'
            {
            match("implies"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__48

    // $ANTLR start T__49
    public final void mT__49() throws RecognitionException {
        try {
            int _type = T__49;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:53:7: ( 'or' )
            // GUSE__.g:53:9: 'or'
            {
            match("or"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__49

    // $ANTLR start T__50
    public final void mT__50() throws RecognitionException {
        try {
            int _type = T__50;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:54:7: ( 'xor' )
            // GUSE__.g:54:9: 'xor'
            {
            match("xor"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__50

    // $ANTLR start T__51
    public final void mT__51() throws RecognitionException {
        try {
            int _type = T__51;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:55:7: ( 'and' )
            // GUSE__.g:55:9: 'and'
            {
            match("and"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__51

    // $ANTLR start T__52
    public final void mT__52() throws RecognitionException {
        try {
            int _type = T__52;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:56:7: ( 'div' )
            // GUSE__.g:56:9: 'div'
            {
            match("div"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__52

    // $ANTLR start T__53
    public final void mT__53() throws RecognitionException {
        try {
            int _type = T__53;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:57:7: ( 'not' )
            // GUSE__.g:57:9: 'not'
            {
            match("not"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__53

    // $ANTLR start T__54
    public final void mT__54() throws RecognitionException {
        try {
            int _type = T__54;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:58:7: ( 'allInstances' )
            // GUSE__.g:58:9: 'allInstances'
            {
            match("allInstances"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__54

    // $ANTLR start T__55
    public final void mT__55() throws RecognitionException {
        try {
            int _type = T__55;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:59:7: ( 'pre' )
            // GUSE__.g:59:9: 'pre'
            {
            match("pre"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__55

    // $ANTLR start T__56
    public final void mT__56() throws RecognitionException {
        try {
            int _type = T__56;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:60:7: ( 'iterate' )
            // GUSE__.g:60:9: 'iterate'
            {
            match("iterate"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__56

    // $ANTLR start T__57
    public final void mT__57() throws RecognitionException {
        try {
            int _type = T__57;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:61:7: ( 'if' )
            // GUSE__.g:61:9: 'if'
            {
            match("if"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__57

    // $ANTLR start T__58
    public final void mT__58() throws RecognitionException {
        try {
            int _type = T__58;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:62:7: ( 'then' )
            // GUSE__.g:62:9: 'then'
            {
            match("then"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__58

    // $ANTLR start T__59
    public final void mT__59() throws RecognitionException {
        try {
            int _type = T__59;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:63:7: ( 'else' )
            // GUSE__.g:63:9: 'else'
            {
            match("else"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__59

    // $ANTLR start T__60
    public final void mT__60() throws RecognitionException {
        try {
            int _type = T__60;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:64:7: ( 'endif' )
            // GUSE__.g:64:9: 'endif'
            {
            match("endif"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__60

    // $ANTLR start T__61
    public final void mT__61() throws RecognitionException {
        try {
            int _type = T__61;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:65:7: ( 'true' )
            // GUSE__.g:65:9: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__61

    // $ANTLR start T__62
    public final void mT__62() throws RecognitionException {
        try {
            int _type = T__62;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:66:7: ( 'false' )
            // GUSE__.g:66:9: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__62

    // $ANTLR start T__63
    public final void mT__63() throws RecognitionException {
        try {
            int _type = T__63;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:67:7: ( 'Set' )
            // GUSE__.g:67:9: 'Set'
            {
            match("Set"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__63

    // $ANTLR start T__64
    public final void mT__64() throws RecognitionException {
        try {
            int _type = T__64;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:68:7: ( 'Sequence' )
            // GUSE__.g:68:9: 'Sequence'
            {
            match("Sequence"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__64

    // $ANTLR start T__65
    public final void mT__65() throws RecognitionException {
        try {
            int _type = T__65;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:69:7: ( 'Bag' )
            // GUSE__.g:69:9: 'Bag'
            {
            match("Bag"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__65

    // $ANTLR start T__66
    public final void mT__66() throws RecognitionException {
        try {
            int _type = T__66;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:70:7: ( 'oclEmpty' )
            // GUSE__.g:70:9: 'oclEmpty'
            {
            match("oclEmpty"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__66

    // $ANTLR start T__67
    public final void mT__67() throws RecognitionException {
        try {
            int _type = T__67;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:71:7: ( 'oclUndefined' )
            // GUSE__.g:71:9: 'oclUndefined'
            {
            match("oclUndefined"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__67

    // $ANTLR start T__68
    public final void mT__68() throws RecognitionException {
        try {
            int _type = T__68;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:72:7: ( 'Tuple' )
            // GUSE__.g:72:9: 'Tuple'
            {
            match("Tuple"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__68

    // $ANTLR start T__69
    public final void mT__69() throws RecognitionException {
        try {
            int _type = T__69;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:73:7: ( 'Collection' )
            // GUSE__.g:73:9: 'Collection'
            {
            match("Collection"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__69

    // $ANTLR start T__70
    public final void mT__70() throws RecognitionException {
        try {
            int _type = T__70;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:74:7: ( 'enum' )
            // GUSE__.g:74:9: 'enum'
            {
            match("enum"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__70

    // $ANTLR start T__71
    public final void mT__71() throws RecognitionException {
        try {
            int _type = T__71;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:75:7: ( 'abstract' )
            // GUSE__.g:75:9: 'abstract'
            {
            match("abstract"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__71

    // $ANTLR start T__72
    public final void mT__72() throws RecognitionException {
        try {
            int _type = T__72;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:76:7: ( 'class' )
            // GUSE__.g:76:9: 'class'
            {
            match("class"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__72

    // $ANTLR start T__73
    public final void mT__73() throws RecognitionException {
        try {
            int _type = T__73;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:77:7: ( 'attributes' )
            // GUSE__.g:77:9: 'attributes'
            {
            match("attributes"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__73

    // $ANTLR start T__74
    public final void mT__74() throws RecognitionException {
        try {
            int _type = T__74;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:78:7: ( 'operations' )
            // GUSE__.g:78:9: 'operations'
            {
            match("operations"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__74

    // $ANTLR start T__75
    public final void mT__75() throws RecognitionException {
        try {
            int _type = T__75;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:79:7: ( 'constraints' )
            // GUSE__.g:79:9: 'constraints'
            {
            match("constraints"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__75

    // $ANTLR start T__76
    public final void mT__76() throws RecognitionException {
        try {
            int _type = T__76;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:80:7: ( 'end' )
            // GUSE__.g:80:9: 'end'
            {
            match("end"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__76

    // $ANTLR start T__77
    public final void mT__77() throws RecognitionException {
        try {
            int _type = T__77;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:81:7: ( 'associationClass' )
            // GUSE__.g:81:9: 'associationClass'
            {
            match("associationClass"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__77

    // $ANTLR start T__78
    public final void mT__78() throws RecognitionException {
        try {
            int _type = T__78;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:82:7: ( 'associationclass' )
            // GUSE__.g:82:9: 'associationclass'
            {
            match("associationclass"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__78

    // $ANTLR start T__79
    public final void mT__79() throws RecognitionException {
        try {
            int _type = T__79;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:83:7: ( 'between' )
            // GUSE__.g:83:9: 'between'
            {
            match("between"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__79

    // $ANTLR start T__80
    public final void mT__80() throws RecognitionException {
        try {
            int _type = T__80;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:84:7: ( 'aggregation' )
            // GUSE__.g:84:9: 'aggregation'
            {
            match("aggregation"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__80

    // $ANTLR start T__81
    public final void mT__81() throws RecognitionException {
        try {
            int _type = T__81;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:85:7: ( 'composition' )
            // GUSE__.g:85:9: 'composition'
            {
            match("composition"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__81

    // $ANTLR start T__82
    public final void mT__82() throws RecognitionException {
        try {
            int _type = T__82;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:86:7: ( 'begin' )
            // GUSE__.g:86:9: 'begin'
            {
            match("begin"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__82

    // $ANTLR start T__83
    public final void mT__83() throws RecognitionException {
        try {
            int _type = T__83;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:87:7: ( 'association' )
            // GUSE__.g:87:9: 'association'
            {
            match("association"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__83

    // $ANTLR start T__84
    public final void mT__84() throws RecognitionException {
        try {
            int _type = T__84;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:88:7: ( 'role' )
            // GUSE__.g:88:9: 'role'
            {
            match("role"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__84

    // $ANTLR start T__85
    public final void mT__85() throws RecognitionException {
        try {
            int _type = T__85;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:89:7: ( 'ordered' )
            // GUSE__.g:89:9: 'ordered'
            {
            match("ordered"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__85

    // $ANTLR start T__86
    public final void mT__86() throws RecognitionException {
        try {
            int _type = T__86;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:90:7: ( 'context' )
            // GUSE__.g:90:9: 'context'
            {
            match("context"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__86

    // $ANTLR start T__87
    public final void mT__87() throws RecognitionException {
        try {
            int _type = T__87;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:91:7: ( 'inv' )
            // GUSE__.g:91:9: 'inv'
            {
            match("inv"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__87

    // $ANTLR start T__88
    public final void mT__88() throws RecognitionException {
        try {
            int _type = T__88;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:92:7: ( 'post' )
            // GUSE__.g:92:9: 'post'
            {
            match("post"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__88

    // $ANTLR start T__89
    public final void mT__89() throws RecognitionException {
        try {
            int _type = T__89;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:93:7: ( 'var' )
            // GUSE__.g:93:9: 'var'
            {
            match("var"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__89

    // $ANTLR start T__90
    public final void mT__90() throws RecognitionException {
        try {
            int _type = T__90;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:94:7: ( 'declare' )
            // GUSE__.g:94:9: 'declare'
            {
            match("declare"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__90

    // $ANTLR start T__91
    public final void mT__91() throws RecognitionException {
        try {
            int _type = T__91;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:95:7: ( 'set' )
            // GUSE__.g:95:9: 'set'
            {
            match("set"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__91

    // $ANTLR start T__92
    public final void mT__92() throws RecognitionException {
        try {
            int _type = T__92;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:96:7: ( 'create' )
            // GUSE__.g:96:9: 'create'
            {
            match("create"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__92

    // $ANTLR start T__93
    public final void mT__93() throws RecognitionException {
        try {
            int _type = T__93;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:97:7: ( 'namehint' )
            // GUSE__.g:97:9: 'namehint'
            {
            match("namehint"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__93

    // $ANTLR start T__94
    public final void mT__94() throws RecognitionException {
        try {
            int _type = T__94;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:98:7: ( 'insert' )
            // GUSE__.g:98:9: 'insert'
            {
            match("insert"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__94

    // $ANTLR start T__95
    public final void mT__95() throws RecognitionException {
        try {
            int _type = T__95;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:99:7: ( 'into' )
            // GUSE__.g:99:9: 'into'
            {
            match("into"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__95

    // $ANTLR start T__96
    public final void mT__96() throws RecognitionException {
        try {
            int _type = T__96;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:100:7: ( 'delete' )
            // GUSE__.g:100:9: 'delete'
            {
            match("delete"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__96

    // $ANTLR start T__97
    public final void mT__97() throws RecognitionException {
        try {
            int _type = T__97;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:101:7: ( 'from' )
            // GUSE__.g:101:9: 'from'
            {
            match("from"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__97

    // $ANTLR start T__98
    public final void mT__98() throws RecognitionException {
        try {
            int _type = T__98;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:102:7: ( 'destroy' )
            // GUSE__.g:102:9: 'destroy'
            {
            match("destroy"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__98

    // $ANTLR start T__99
    public final void mT__99() throws RecognitionException {
        try {
            int _type = T__99;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:103:7: ( 'while' )
            // GUSE__.g:103:9: 'while'
            {
            match("while"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__99

    // $ANTLR start T__100
    public final void mT__100() throws RecognitionException {
        try {
            int _type = T__100;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:104:8: ( 'do' )
            // GUSE__.g:104:10: 'do'
            {
            match("do"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__100

    // $ANTLR start T__101
    public final void mT__101() throws RecognitionException {
        try {
            int _type = T__101;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:105:8: ( 'wend' )
            // GUSE__.g:105:10: 'wend'
            {
            match("wend"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__101

    // $ANTLR start T__102
    public final void mT__102() throws RecognitionException {
        try {
            int _type = T__102;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:106:8: ( 'for' )
            // GUSE__.g:106:10: 'for'
            {
            match("for"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__102

    // $ANTLR start T__103
    public final void mT__103() throws RecognitionException {
        try {
            int _type = T__103;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:107:8: ( 'execute' )
            // GUSE__.g:107:10: 'execute'
            {
            match("execute"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__103

    // $ANTLR start T__104
    public final void mT__104() throws RecognitionException {
        try {
            int _type = T__104;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GUSE__.g:108:8: ( 'model' )
            // GUSE__.g:108:10: 'model'
            {
            match("model"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__104

    public void mTokens() throws RecognitionException {
        // GUSE__.g:1:8: ( LITERAL_oclAsType | LITERAL_oclIsKindOf | LITERAL_oclIsTypeOf | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | GOCLLexerRules. Tokens )
        int alt1=63;
        alt1 = dfa1.predict(input);
        switch (alt1) {
            case 1 :
                // GUSE__.g:1:10: LITERAL_oclAsType
                {
                mLITERAL_oclAsType(); 

                }
                break;
            case 2 :
                // GUSE__.g:1:28: LITERAL_oclIsKindOf
                {
                mLITERAL_oclIsKindOf(); 

                }
                break;
            case 3 :
                // GUSE__.g:1:48: LITERAL_oclIsTypeOf
                {
                mLITERAL_oclIsTypeOf(); 

                }
                break;
            case 4 :
                // GUSE__.g:1:68: T__46
                {
                mT__46(); 

                }
                break;
            case 5 :
                // GUSE__.g:1:74: T__47
                {
                mT__47(); 

                }
                break;
            case 6 :
                // GUSE__.g:1:80: T__48
                {
                mT__48(); 

                }
                break;
            case 7 :
                // GUSE__.g:1:86: T__49
                {
                mT__49(); 

                }
                break;
            case 8 :
                // GUSE__.g:1:92: T__50
                {
                mT__50(); 

                }
                break;
            case 9 :
                // GUSE__.g:1:98: T__51
                {
                mT__51(); 

                }
                break;
            case 10 :
                // GUSE__.g:1:104: T__52
                {
                mT__52(); 

                }
                break;
            case 11 :
                // GUSE__.g:1:110: T__53
                {
                mT__53(); 

                }
                break;
            case 12 :
                // GUSE__.g:1:116: T__54
                {
                mT__54(); 

                }
                break;
            case 13 :
                // GUSE__.g:1:122: T__55
                {
                mT__55(); 

                }
                break;
            case 14 :
                // GUSE__.g:1:128: T__56
                {
                mT__56(); 

                }
                break;
            case 15 :
                // GUSE__.g:1:134: T__57
                {
                mT__57(); 

                }
                break;
            case 16 :
                // GUSE__.g:1:140: T__58
                {
                mT__58(); 

                }
                break;
            case 17 :
                // GUSE__.g:1:146: T__59
                {
                mT__59(); 

                }
                break;
            case 18 :
                // GUSE__.g:1:152: T__60
                {
                mT__60(); 

                }
                break;
            case 19 :
                // GUSE__.g:1:158: T__61
                {
                mT__61(); 

                }
                break;
            case 20 :
                // GUSE__.g:1:164: T__62
                {
                mT__62(); 

                }
                break;
            case 21 :
                // GUSE__.g:1:170: T__63
                {
                mT__63(); 

                }
                break;
            case 22 :
                // GUSE__.g:1:176: T__64
                {
                mT__64(); 

                }
                break;
            case 23 :
                // GUSE__.g:1:182: T__65
                {
                mT__65(); 

                }
                break;
            case 24 :
                // GUSE__.g:1:188: T__66
                {
                mT__66(); 

                }
                break;
            case 25 :
                // GUSE__.g:1:194: T__67
                {
                mT__67(); 

                }
                break;
            case 26 :
                // GUSE__.g:1:200: T__68
                {
                mT__68(); 

                }
                break;
            case 27 :
                // GUSE__.g:1:206: T__69
                {
                mT__69(); 

                }
                break;
            case 28 :
                // GUSE__.g:1:212: T__70
                {
                mT__70(); 

                }
                break;
            case 29 :
                // GUSE__.g:1:218: T__71
                {
                mT__71(); 

                }
                break;
            case 30 :
                // GUSE__.g:1:224: T__72
                {
                mT__72(); 

                }
                break;
            case 31 :
                // GUSE__.g:1:230: T__73
                {
                mT__73(); 

                }
                break;
            case 32 :
                // GUSE__.g:1:236: T__74
                {
                mT__74(); 

                }
                break;
            case 33 :
                // GUSE__.g:1:242: T__75
                {
                mT__75(); 

                }
                break;
            case 34 :
                // GUSE__.g:1:248: T__76
                {
                mT__76(); 

                }
                break;
            case 35 :
                // GUSE__.g:1:254: T__77
                {
                mT__77(); 

                }
                break;
            case 36 :
                // GUSE__.g:1:260: T__78
                {
                mT__78(); 

                }
                break;
            case 37 :
                // GUSE__.g:1:266: T__79
                {
                mT__79(); 

                }
                break;
            case 38 :
                // GUSE__.g:1:272: T__80
                {
                mT__80(); 

                }
                break;
            case 39 :
                // GUSE__.g:1:278: T__81
                {
                mT__81(); 

                }
                break;
            case 40 :
                // GUSE__.g:1:284: T__82
                {
                mT__82(); 

                }
                break;
            case 41 :
                // GUSE__.g:1:290: T__83
                {
                mT__83(); 

                }
                break;
            case 42 :
                // GUSE__.g:1:296: T__84
                {
                mT__84(); 

                }
                break;
            case 43 :
                // GUSE__.g:1:302: T__85
                {
                mT__85(); 

                }
                break;
            case 44 :
                // GUSE__.g:1:308: T__86
                {
                mT__86(); 

                }
                break;
            case 45 :
                // GUSE__.g:1:314: T__87
                {
                mT__87(); 

                }
                break;
            case 46 :
                // GUSE__.g:1:320: T__88
                {
                mT__88(); 

                }
                break;
            case 47 :
                // GUSE__.g:1:326: T__89
                {
                mT__89(); 

                }
                break;
            case 48 :
                // GUSE__.g:1:332: T__90
                {
                mT__90(); 

                }
                break;
            case 49 :
                // GUSE__.g:1:338: T__91
                {
                mT__91(); 

                }
                break;
            case 50 :
                // GUSE__.g:1:344: T__92
                {
                mT__92(); 

                }
                break;
            case 51 :
                // GUSE__.g:1:350: T__93
                {
                mT__93(); 

                }
                break;
            case 52 :
                // GUSE__.g:1:356: T__94
                {
                mT__94(); 

                }
                break;
            case 53 :
                // GUSE__.g:1:362: T__95
                {
                mT__95(); 

                }
                break;
            case 54 :
                // GUSE__.g:1:368: T__96
                {
                mT__96(); 

                }
                break;
            case 55 :
                // GUSE__.g:1:374: T__97
                {
                mT__97(); 

                }
                break;
            case 56 :
                // GUSE__.g:1:380: T__98
                {
                mT__98(); 

                }
                break;
            case 57 :
                // GUSE__.g:1:386: T__99
                {
                mT__99(); 

                }
                break;
            case 58 :
                // GUSE__.g:1:392: T__100
                {
                mT__100(); 

                }
                break;
            case 59 :
                // GUSE__.g:1:399: T__101
                {
                mT__101(); 

                }
                break;
            case 60 :
                // GUSE__.g:1:406: T__102
                {
                mT__102(); 

                }
                break;
            case 61 :
                // GUSE__.g:1:413: T__103
                {
                mT__103(); 

                }
                break;
            case 62 :
                // GUSE__.g:1:420: T__104
                {
                mT__104(); 

                }
                break;
            case 63 :
                // GUSE__.g:1:427: GOCLLexerRules. Tokens
                {
                gGOCLLexerRules.mTokens(); 

                }
                break;

        }

    }


    protected DFA1 dfa1 = new DFA1(this);
    static final String DFA1_eotS =
        "\1\uffff\26\27\1\uffff\1\27\1\106\2\27\1\114\2\27\1\117\11\27\1"+
        "\133\34\27\1\uffff\1\27\1\u0080\1\u0081\2\27\1\uffff\2\27\1\uffff"+
        "\1\u0086\1\u0087\5\27\1\u008d\3\27\1\uffff\1\u0091\1\27\1\u0093"+
        "\4\27\1\u0099\4\27\1\u009e\1\u009f\1\27\1\u00a1\11\27\1\u00ac\1"+
        "\u00ad\11\27\2\uffff\1\27\1\u00b8\2\27\2\uffff\5\27\1\uffff\3\27"+
        "\1\uffff\1\27\1\uffff\1\u00c4\1\u00c5\1\u00c6\1\u00c7\1\27\1\uffff"+
        "\1\u00c9\2\27\1\u00cc\2\uffff\1\27\1\uffff\11\27\1\u00d7\2\uffff"+
        "\1\27\1\u00d9\10\27\1\uffff\13\27\4\uffff\1\u00ee\1\uffff\1\27\1"+
        "\u00f0\1\uffff\1\27\1\u00f2\1\27\1\u00f4\5\27\1\u00fa\1\uffff\1"+
        "\u00fb\1\uffff\1\u00fc\7\27\1\u0104\10\27\1\u010d\2\27\1\uffff\1"+
        "\27\1\uffff\1\27\1\uffff\1\27\1\uffff\3\27\1\u0116\1\27\3\uffff"+
        "\5\27\1\u011d\1\27\1\uffff\1\u011f\1\u0120\5\27\1\u0126\1\uffff"+
        "\1\u0127\1\27\1\u0129\3\27\1\u012d\1\27\1\uffff\1\u012f\3\27\1\u0133"+
        "\1\27\1\uffff\1\27\2\uffff\1\27\1\u0137\3\27\2\uffff\1\u013b\1\uffff"+
        "\1\u013c\2\27\1\uffff\1\27\1\uffff\1\u0140\2\27\1\uffff\3\27\1\uffff"+
        "\3\27\2\uffff\3\27\1\uffff\3\27\1\u014f\1\27\1\u0151\2\27\1\u0154"+
        "\2\27\1\u0157\1\u0158\1\27\1\uffff\1\27\1\uffff\1\u015d\1\u015e"+
        "\1\uffff\1\u015f\1\u0160\2\uffff\1\u0161\1\u0162\2\27\6\uffff\6"+
        "\27\1\u016b\1\u016c\2\uffff";
    static final String DFA1_eofS =
        "\u016d\uffff";
    static final String DFA1_minS =
        "\1\11\1\143\1\145\1\146\1\157\1\142\1\145\1\141\1\157\1\150\1\154"+
        "\1\141\1\145\1\141\1\165\1\157\1\154\1\145\1\157\1\141\2\145\1\157"+
        "\1\uffff\1\154\1\60\1\145\1\164\1\60\1\160\1\145\1\60\1\162\1\144"+
        "\1\154\1\163\1\164\1\163\1\147\1\166\1\143\1\60\1\164\1\155\1\145"+
        "\1\163\1\145\1\165\1\163\1\144\1\145\1\154\1\157\1\162\1\161\1\147"+
        "\1\160\1\154\1\141\1\155\1\145\1\147\1\154\1\162\1\164\1\151\1\156"+
        "\1\144\1\101\1\145\1\uffff\1\162\2\60\1\145\1\157\1\uffff\1\154"+
        "\1\162\1\uffff\2\60\1\111\1\164\1\162\1\157\1\162\1\60\1\154\1\145"+
        "\1\164\1\uffff\1\60\1\145\1\60\1\164\1\156\2\145\1\60\1\155\1\143"+
        "\1\163\1\155\2\60\1\165\1\60\2\154\2\163\1\160\1\141\1\167\1\151"+
        "\1\145\2\60\1\154\1\144\1\145\2\163\1\155\1\156\1\162\1\141\2\uffff"+
        "\1\162\1\60\1\151\1\141\2\uffff\1\156\1\162\1\151\1\143\1\145\1"+
        "\uffff\1\141\1\164\1\162\1\uffff\1\150\1\uffff\4\60\1\146\1\uffff"+
        "\1\60\1\165\1\145\1\60\2\uffff\1\145\1\uffff\2\145\1\163\1\164\1"+
        "\145\1\157\1\164\1\145\1\156\1\60\2\uffff\1\145\1\60\1\154\1\124"+
        "\1\113\1\160\1\144\1\145\2\164\1\uffff\1\145\1\164\1\163\1\141\1"+
        "\142\1\151\1\147\1\162\1\145\1\157\1\151\4\uffff\1\60\1\uffff\1"+
        "\164\1\60\1\uffff\1\156\1\60\1\143\1\60\1\162\1\170\1\163\2\145"+
        "\1\60\1\uffff\1\60\1\uffff\1\60\1\171\1\151\1\171\1\164\1\145\1"+
        "\144\1\151\1\60\1\163\1\145\1\164\1\143\1\165\2\141\1\145\1\60\1"+
        "\171\1\156\1\uffff\1\145\1\uffff\1\143\1\uffff\1\164\1\uffff\1\141"+
        "\1\164\1\151\1\60\1\156\3\uffff\1\160\1\156\1\160\1\171\1\146\1"+
        "\60\1\157\1\uffff\2\60\1\141\4\164\1\60\1\uffff\1\60\1\164\1\60"+
        "\1\145\2\151\1\60\1\164\1\uffff\1\60\1\145\1\144\1\145\1\60\1\151"+
        "\1\uffff\1\156\2\uffff\1\156\1\60\1\145\2\151\2\uffff\1\60\1\uffff"+
        "\1\60\1\157\1\156\1\uffff\1\151\1\uffff\1\60\2\117\1\uffff\1\156"+
        "\1\163\1\143\1\uffff\1\163\2\157\2\uffff\1\156\1\164\1\157\1\uffff"+
        "\2\146\1\145\1\60\1\145\1\60\2\156\1\60\1\163\1\156\2\60\1\144\1"+
        "\uffff\1\163\1\uffff\2\60\1\uffff\2\60\2\uffff\2\60\2\154\6\uffff"+
        "\2\141\4\163\2\60\2\uffff";
    static final String DFA1_maxS =
        "\1\175\1\162\1\145\1\164\1\157\1\164\2\157\2\162\1\170\1\162\1\145"+
        "\1\141\1\165\1\157\1\162\1\145\1\157\1\141\1\145\1\150\1\157\1\uffff"+
        "\1\154\1\172\1\145\1\164\1\172\1\160\1\145\1\172\1\162\1\144\1\154"+
        "\1\163\1\164\1\163\1\147\1\166\1\163\1\172\1\164\1\155\1\145\1\163"+
        "\1\145\1\165\1\163\1\165\1\145\1\154\1\157\1\162\1\164\1\147\1\160"+
        "\1\154\1\141\1\156\1\145\1\164\1\154\1\162\1\164\1\151\1\156\1\144"+
        "\1\125\1\145\1\uffff\1\162\2\172\1\145\1\157\1\uffff\1\154\1\162"+
        "\1\uffff\2\172\1\111\1\164\1\162\1\157\1\162\1\172\1\154\1\145\1"+
        "\164\1\uffff\1\172\1\145\1\172\1\164\1\156\2\145\1\172\1\155\1\143"+
        "\1\163\1\155\2\172\1\165\1\172\2\154\1\163\1\164\1\160\1\141\1\167"+
        "\1\151\1\145\2\172\1\154\1\144\1\145\2\163\1\155\1\156\1\162\1\141"+
        "\2\uffff\1\162\1\172\1\151\1\141\2\uffff\1\156\1\162\1\151\1\143"+
        "\1\145\1\uffff\1\141\1\164\1\162\1\uffff\1\150\1\uffff\4\172\1\146"+
        "\1\uffff\1\172\1\165\1\145\1\172\2\uffff\1\145\1\uffff\2\145\1\163"+
        "\1\164\1\145\1\157\1\164\1\145\1\156\1\172\2\uffff\1\145\1\172\1"+
        "\154\2\124\1\160\1\144\1\145\2\164\1\uffff\1\145\1\164\1\163\1\141"+
        "\1\142\1\151\1\147\1\162\1\145\1\157\1\151\4\uffff\1\172\1\uffff"+
        "\1\164\1\172\1\uffff\1\156\1\172\1\143\1\172\1\162\1\170\1\163\2"+
        "\145\1\172\1\uffff\1\172\1\uffff\1\172\1\171\1\151\1\171\1\164\1"+
        "\145\1\144\1\151\1\172\1\163\1\145\1\164\1\143\1\165\2\141\1\145"+
        "\1\172\1\171\1\156\1\uffff\1\145\1\uffff\1\143\1\uffff\1\164\1\uffff"+
        "\1\141\1\164\1\151\1\172\1\156\3\uffff\1\160\1\156\1\160\1\171\1"+
        "\146\1\172\1\157\1\uffff\2\172\1\141\4\164\1\172\1\uffff\1\172\1"+
        "\164\1\172\1\145\2\151\1\172\1\164\1\uffff\1\172\1\145\1\144\1\145"+
        "\1\172\1\151\1\uffff\1\156\2\uffff\1\156\1\172\1\145\2\151\2\uffff"+
        "\1\172\1\uffff\1\172\1\157\1\156\1\uffff\1\151\1\uffff\1\172\2\117"+
        "\1\uffff\1\156\1\163\1\143\1\uffff\1\163\2\157\2\uffff\1\156\1\164"+
        "\1\157\1\uffff\2\146\1\145\1\172\1\145\1\172\2\156\1\172\1\163\1"+
        "\156\2\172\1\144\1\uffff\1\163\1\uffff\2\172\1\uffff\2\172\2\uffff"+
        "\2\172\2\154\6\uffff\2\141\4\163\2\172\2\uffff";
    static final String DFA1_acceptS =
        "\27\uffff\1\77\56\uffff\1\7\5\uffff\1\5\2\uffff\1\17\13\uffff\1"+
        "\72\44\uffff\1\4\1\55\4\uffff\1\10\1\11\5\uffff\1\12\3\uffff\1\13"+
        "\1\uffff\1\15\5\uffff\1\42\4\uffff\1\74\1\25\1\uffff\1\27\12\uffff"+
        "\1\57\1\61\12\uffff\1\65\13\uffff\1\56\1\20\1\23\1\21\1\uffff\1"+
        "\34\2\uffff\1\67\12\uffff\1\52\1\uffff\1\73\24\uffff\1\22\1\uffff"+
        "\1\24\1\uffff\1\32\1\uffff\1\36\5\uffff\1\50\1\71\1\76\7\uffff\1"+
        "\64\10\uffff\1\66\10\uffff\1\62\6\uffff\1\53\1\uffff\1\6\1\16\5"+
        "\uffff\1\60\1\70\1\uffff\1\75\3\uffff\1\54\1\uffff\1\45\3\uffff"+
        "\1\30\3\uffff\1\35\3\uffff\1\63\1\26\3\uffff\1\1\16\uffff\1\40\1"+
        "\uffff\1\37\2\uffff\1\33\2\uffff\1\2\1\3\4\uffff\1\51\1\46\1\41"+
        "\1\47\1\31\1\14\10\uffff\1\43\1\44";
    static final String DFA1_specialS =
        "\u016d\uffff}>";
    static final String[] DFA1_transitionS = {
            "\2\27\1\uffff\2\27\22\uffff\1\27\2\uffff\2\27\2\uffff\30\27"+
            "\1\uffff\2\27\1\15\1\17\17\27\1\14\1\16\7\27\1\uffff\1\27\1"+
            "\uffff\1\27\1\uffff\1\5\1\21\1\20\1\6\1\12\1\13\2\27\1\3\2\27"+
            "\1\2\1\26\1\7\1\1\1\10\1\27\1\22\1\24\1\11\1\27\1\23\1\25\1"+
            "\4\5\27",
            "\1\30\14\uffff\1\32\1\uffff\1\31",
            "\1\33",
            "\1\37\6\uffff\1\35\1\34\5\uffff\1\36",
            "\1\40",
            "\1\43\4\uffff\1\46\4\uffff\1\42\1\uffff\1\41\4\uffff\1\45\1"+
            "\44",
            "\1\50\3\uffff\1\47\5\uffff\1\51",
            "\1\53\15\uffff\1\52",
            "\1\55\2\uffff\1\54",
            "\1\56\11\uffff\1\57",
            "\1\60\1\uffff\1\61\11\uffff\1\62",
            "\1\63\15\uffff\1\65\2\uffff\1\64",
            "\1\66",
            "\1\67",
            "\1\70",
            "\1\71",
            "\1\72\2\uffff\1\73\2\uffff\1\74",
            "\1\75",
            "\1\76",
            "\1\77",
            "\1\100",
            "\1\102\2\uffff\1\101",
            "\1\103",
            "",
            "\1\104",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\3\27\1\105\26\27",
            "\1\107",
            "\1\110",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\22\27\1\112\1\113"+
            "\1\27\1\111\4\27",
            "\1\115",
            "\1\116",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\120",
            "\1\121",
            "\1\122",
            "\1\123",
            "\1\124",
            "\1\125",
            "\1\126",
            "\1\127",
            "\1\130\10\uffff\1\131\6\uffff\1\132",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\134",
            "\1\135",
            "\1\136",
            "\1\137",
            "\1\140",
            "\1\141",
            "\1\142",
            "\1\143\20\uffff\1\144",
            "\1\145",
            "\1\146",
            "\1\147",
            "\1\150",
            "\1\152\2\uffff\1\151",
            "\1\153",
            "\1\154",
            "\1\155",
            "\1\156",
            "\1\160\1\157",
            "\1\161",
            "\1\163\14\uffff\1\162",
            "\1\164",
            "\1\165",
            "\1\166",
            "\1\167",
            "\1\170",
            "\1\171",
            "\1\172\3\uffff\1\174\3\uffff\1\173\13\uffff\1\175",
            "\1\176",
            "",
            "\1\177",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u0082",
            "\1\u0083",
            "",
            "\1\u0084",
            "\1\u0085",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u0088",
            "\1\u0089",
            "\1\u008a",
            "\1\u008b",
            "\1\u008c",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u008e",
            "\1\u008f",
            "\1\u0090",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u0092",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u0094",
            "\1\u0095",
            "\1\u0096",
            "\1\u0097",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\10\27\1\u0098\21\27",
            "\1\u009a",
            "\1\u009b",
            "\1\u009c",
            "\1\u009d",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u00a0",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u00a2",
            "\1\u00a3",
            "\1\u00a4",
            "\1\u00a5\1\u00a6",
            "\1\u00a7",
            "\1\u00a8",
            "\1\u00a9",
            "\1\u00aa",
            "\1\u00ab",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u00ae",
            "\1\u00af",
            "\1\u00b0",
            "\1\u00b1",
            "\1\u00b2",
            "\1\u00b3",
            "\1\u00b4",
            "\1\u00b5",
            "\1\u00b6",
            "",
            "",
            "\1\u00b7",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u00b9",
            "\1\u00ba",
            "",
            "",
            "\1\u00bb",
            "\1\u00bc",
            "\1\u00bd",
            "\1\u00be",
            "\1\u00bf",
            "",
            "\1\u00c0",
            "\1\u00c1",
            "\1\u00c2",
            "",
            "\1\u00c3",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u00c8",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u00ca",
            "\1\u00cb",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "",
            "",
            "\1\u00cd",
            "",
            "\1\u00ce",
            "\1\u00cf",
            "\1\u00d0",
            "\1\u00d1",
            "\1\u00d2",
            "\1\u00d3",
            "\1\u00d4",
            "\1\u00d5",
            "\1\u00d6",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "",
            "",
            "\1\u00d8",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u00da",
            "\1\u00db",
            "\1\u00dc\10\uffff\1\u00dd",
            "\1\u00de",
            "\1\u00df",
            "\1\u00e0",
            "\1\u00e1",
            "\1\u00e2",
            "",
            "\1\u00e3",
            "\1\u00e4",
            "\1\u00e5",
            "\1\u00e6",
            "\1\u00e7",
            "\1\u00e8",
            "\1\u00e9",
            "\1\u00ea",
            "\1\u00eb",
            "\1\u00ec",
            "\1\u00ed",
            "",
            "",
            "",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "",
            "\1\u00ef",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "",
            "\1\u00f1",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u00f3",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u00f5",
            "\1\u00f6",
            "\1\u00f7",
            "\1\u00f8",
            "\1\u00f9",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u00fd",
            "\1\u00fe",
            "\1\u00ff",
            "\1\u0100",
            "\1\u0101",
            "\1\u0102",
            "\1\u0103",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u0105",
            "\1\u0106",
            "\1\u0107",
            "\1\u0108",
            "\1\u0109",
            "\1\u010a",
            "\1\u010b",
            "\1\u010c",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u010e",
            "\1\u010f",
            "",
            "\1\u0110",
            "",
            "\1\u0111",
            "",
            "\1\u0112",
            "",
            "\1\u0113",
            "\1\u0114",
            "\1\u0115",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u0117",
            "",
            "",
            "",
            "\1\u0118",
            "\1\u0119",
            "\1\u011a",
            "\1\u011b",
            "\1\u011c",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u011e",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u0121",
            "\1\u0122",
            "\1\u0123",
            "\1\u0124",
            "\1\u0125",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u0128",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u012a",
            "\1\u012b",
            "\1\u012c",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u012e",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u0130",
            "\1\u0131",
            "\1\u0132",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u0134",
            "",
            "\1\u0135",
            "",
            "",
            "\1\u0136",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u0138",
            "\1\u0139",
            "\1\u013a",
            "",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u013d",
            "\1\u013e",
            "",
            "\1\u013f",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u0141",
            "\1\u0142",
            "",
            "\1\u0143",
            "\1\u0144",
            "\1\u0145",
            "",
            "\1\u0146",
            "\1\u0147",
            "\1\u0148",
            "",
            "",
            "\1\u0149",
            "\1\u014a",
            "\1\u014b",
            "",
            "\1\u014c",
            "\1\u014d",
            "\1\u014e",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u0150",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u0152",
            "\1\u0153",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u0155",
            "\1\u0156",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u0159",
            "",
            "\1\u015a",
            "",
            "\12\27\7\uffff\2\27\1\u015b\27\27\4\uffff\1\27\1\uffff\2\27"+
            "\1\u015c\27\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "",
            "",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\1\u0163",
            "\1\u0164",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u0165",
            "\1\u0166",
            "\1\u0167",
            "\1\u0168",
            "\1\u0169",
            "\1\u016a",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
            "\12\27\7\uffff\32\27\4\uffff\1\27\1\uffff\32\27",
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
            return "1:1: Tokens : ( LITERAL_oclAsType | LITERAL_oclIsKindOf | LITERAL_oclIsTypeOf | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | GOCLLexerRules. Tokens );";
        }
    }
 

}