// $ANTLR 3.1b1 GCmd__.g 2008-07-01 13:26:39

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

import org.tzi.use.parser.ParseErrorHandler;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class GCmdLexer extends Lexer {
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__66=66;
    public static final int LITERAL_oclAsType=4;
    public static final int T__67=67;
    public static final int T__64=64;
    public static final int STAR=20;
    public static final int LBRACK=27;
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
    public static final int T__80=80;
    public static final int T__46=46;
    public static final int T__81=81;
    public static final int T__47=47;
    public static final int RBRACE=34;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int LITERAL_oclIsKindOf=5;
    public static final int HASH=32;
    public static final int HEX_DIGIT=44;
    public static final int INT=29;
    public static final int COLON_COLON=40;
    public static final int MINUS=19;
    public static final int Tokens=82;
    public static final int SEMI=26;
    public static final int COLON=11;
    public static final int REAL=30;
    public static final int T__71=71;
    public static final int WS=37;
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
    public static final int T__73=73;
    public static final int GREATER_EQUAL=17;
    public static final int BAR=25;
    public static final int T__79=79;
    public static final int T__78=78;
    public static final int STRING=31;
    public static final int T__77=77;
    
    	public void init(ParseErrorHandler handler) {
            fParseErrorHandler = handler;
    		this.gGOCLLexerRules.init(handler);
        }
    
        /* Overridden methods. */
    	private ParseErrorHandler fParseErrorHandler;
        
        public void emitErrorMessage(String msg) {
           	fParseErrorHandler.reportError(msg);
    	}


    // delegates
    public GCmd_GOCLLexerRules gGOCLLexerRules;
    // delegators

    public GCmdLexer() {;} 
    public GCmdLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public GCmdLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
        gGOCLLexerRules = new GCmd_GOCLLexerRules(input, state, this);
    }
    public String getGrammarFileName() { return "GCmd__.g"; }

    // $ANTLR start LITERAL_oclAsType
    public final void mLITERAL_oclAsType() throws RecognitionException {
        try {
            int _type = LITERAL_oclAsType;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GCmd__.g:41:19: ( 'oclAsType' )
            // GCmd__.g:41:21: 'oclAsType'
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
            // GCmd__.g:42:21: ( 'oclIsKindOf' )
            // GCmd__.g:42:23: 'oclIsKindOf'
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
            // GCmd__.g:43:21: ( 'oclIsTypeOf' )
            // GCmd__.g:43:23: 'oclIsTypeOf'
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
            // GCmd__.g:44:7: ( 'let' )
            // GCmd__.g:44:9: 'let'
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
            // GCmd__.g:45:7: ( 'in' )
            // GCmd__.g:45:9: 'in'
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
            // GCmd__.g:46:7: ( 'implies' )
            // GCmd__.g:46:9: 'implies'
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
            // GCmd__.g:47:7: ( 'or' )
            // GCmd__.g:47:9: 'or'
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
            // GCmd__.g:48:7: ( 'xor' )
            // GCmd__.g:48:9: 'xor'
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
            // GCmd__.g:49:7: ( 'and' )
            // GCmd__.g:49:9: 'and'
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
            // GCmd__.g:50:7: ( 'div' )
            // GCmd__.g:50:9: 'div'
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
            // GCmd__.g:51:7: ( 'not' )
            // GCmd__.g:51:9: 'not'
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
            // GCmd__.g:52:7: ( 'allInstances' )
            // GCmd__.g:52:9: 'allInstances'
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
            // GCmd__.g:53:7: ( 'pre' )
            // GCmd__.g:53:9: 'pre'
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
            // GCmd__.g:54:7: ( 'iterate' )
            // GCmd__.g:54:9: 'iterate'
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
            // GCmd__.g:55:7: ( 'if' )
            // GCmd__.g:55:9: 'if'
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
            // GCmd__.g:56:7: ( 'then' )
            // GCmd__.g:56:9: 'then'
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
            // GCmd__.g:57:7: ( 'else' )
            // GCmd__.g:57:9: 'else'
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
            // GCmd__.g:58:7: ( 'endif' )
            // GCmd__.g:58:9: 'endif'
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
            // GCmd__.g:59:7: ( 'true' )
            // GCmd__.g:59:9: 'true'
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
            // GCmd__.g:60:7: ( 'false' )
            // GCmd__.g:60:9: 'false'
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
            // GCmd__.g:61:7: ( 'Set' )
            // GCmd__.g:61:9: 'Set'
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
            // GCmd__.g:62:7: ( 'Sequence' )
            // GCmd__.g:62:9: 'Sequence'
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
            // GCmd__.g:63:7: ( 'Bag' )
            // GCmd__.g:63:9: 'Bag'
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
            // GCmd__.g:64:7: ( 'oclEmpty' )
            // GCmd__.g:64:9: 'oclEmpty'
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
            // GCmd__.g:65:7: ( 'oclUndefined' )
            // GCmd__.g:65:9: 'oclUndefined'
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
            // GCmd__.g:66:7: ( 'Tuple' )
            // GCmd__.g:66:9: 'Tuple'
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
            // GCmd__.g:67:7: ( 'Collection' )
            // GCmd__.g:67:9: 'Collection'
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
            // GCmd__.g:68:7: ( 'create' )
            // GCmd__.g:68:9: 'create'
            {
            match("create"); 


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
            // GCmd__.g:69:7: ( 'assign' )
            // GCmd__.g:69:9: 'assign'
            {
            match("assign"); 


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
            // GCmd__.g:70:7: ( 'between' )
            // GCmd__.g:70:9: 'between'
            {
            match("between"); 


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
            // GCmd__.g:71:7: ( 'destroy' )
            // GCmd__.g:71:9: 'destroy'
            {
            match("destroy"); 


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
            // GCmd__.g:72:7: ( 'insert' )
            // GCmd__.g:72:9: 'insert'
            {
            match("insert"); 


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
            // GCmd__.g:73:7: ( 'into' )
            // GCmd__.g:73:9: 'into'
            {
            match("into"); 


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
            // GCmd__.g:74:7: ( 'delete' )
            // GCmd__.g:74:9: 'delete'
            {
            match("delete"); 


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
            // GCmd__.g:75:7: ( 'from' )
            // GCmd__.g:75:9: 'from'
            {
            match("from"); 


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
            // GCmd__.g:76:7: ( 'set' )
            // GCmd__.g:76:9: 'set'
            {
            match("set"); 


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
            // GCmd__.g:77:7: ( 'openter' )
            // GCmd__.g:77:9: 'openter'
            {
            match("openter"); 


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
            // GCmd__.g:78:7: ( 'opexit' )
            // GCmd__.g:78:9: 'opexit'
            {
            match("opexit"); 


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
            // GCmd__.g:79:7: ( 'execute' )
            // GCmd__.g:79:9: 'execute'
            {
            match("execute"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__81

    public void mTokens() throws RecognitionException {
        // GCmd__.g:1:8: ( LITERAL_oclAsType | LITERAL_oclIsKindOf | LITERAL_oclIsTypeOf | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | GOCLLexerRules. Tokens )
        int alt1=40;
        alt1 = dfa1.predict(input);
        switch (alt1) {
            case 1 :
                // GCmd__.g:1:10: LITERAL_oclAsType
                {
                mLITERAL_oclAsType(); 

                }
                break;
            case 2 :
                // GCmd__.g:1:28: LITERAL_oclIsKindOf
                {
                mLITERAL_oclIsKindOf(); 

                }
                break;
            case 3 :
                // GCmd__.g:1:48: LITERAL_oclIsTypeOf
                {
                mLITERAL_oclIsTypeOf(); 

                }
                break;
            case 4 :
                // GCmd__.g:1:68: T__46
                {
                mT__46(); 

                }
                break;
            case 5 :
                // GCmd__.g:1:74: T__47
                {
                mT__47(); 

                }
                break;
            case 6 :
                // GCmd__.g:1:80: T__48
                {
                mT__48(); 

                }
                break;
            case 7 :
                // GCmd__.g:1:86: T__49
                {
                mT__49(); 

                }
                break;
            case 8 :
                // GCmd__.g:1:92: T__50
                {
                mT__50(); 

                }
                break;
            case 9 :
                // GCmd__.g:1:98: T__51
                {
                mT__51(); 

                }
                break;
            case 10 :
                // GCmd__.g:1:104: T__52
                {
                mT__52(); 

                }
                break;
            case 11 :
                // GCmd__.g:1:110: T__53
                {
                mT__53(); 

                }
                break;
            case 12 :
                // GCmd__.g:1:116: T__54
                {
                mT__54(); 

                }
                break;
            case 13 :
                // GCmd__.g:1:122: T__55
                {
                mT__55(); 

                }
                break;
            case 14 :
                // GCmd__.g:1:128: T__56
                {
                mT__56(); 

                }
                break;
            case 15 :
                // GCmd__.g:1:134: T__57
                {
                mT__57(); 

                }
                break;
            case 16 :
                // GCmd__.g:1:140: T__58
                {
                mT__58(); 

                }
                break;
            case 17 :
                // GCmd__.g:1:146: T__59
                {
                mT__59(); 

                }
                break;
            case 18 :
                // GCmd__.g:1:152: T__60
                {
                mT__60(); 

                }
                break;
            case 19 :
                // GCmd__.g:1:158: T__61
                {
                mT__61(); 

                }
                break;
            case 20 :
                // GCmd__.g:1:164: T__62
                {
                mT__62(); 

                }
                break;
            case 21 :
                // GCmd__.g:1:170: T__63
                {
                mT__63(); 

                }
                break;
            case 22 :
                // GCmd__.g:1:176: T__64
                {
                mT__64(); 

                }
                break;
            case 23 :
                // GCmd__.g:1:182: T__65
                {
                mT__65(); 

                }
                break;
            case 24 :
                // GCmd__.g:1:188: T__66
                {
                mT__66(); 

                }
                break;
            case 25 :
                // GCmd__.g:1:194: T__67
                {
                mT__67(); 

                }
                break;
            case 26 :
                // GCmd__.g:1:200: T__68
                {
                mT__68(); 

                }
                break;
            case 27 :
                // GCmd__.g:1:206: T__69
                {
                mT__69(); 

                }
                break;
            case 28 :
                // GCmd__.g:1:212: T__70
                {
                mT__70(); 

                }
                break;
            case 29 :
                // GCmd__.g:1:218: T__71
                {
                mT__71(); 

                }
                break;
            case 30 :
                // GCmd__.g:1:224: T__72
                {
                mT__72(); 

                }
                break;
            case 31 :
                // GCmd__.g:1:230: T__73
                {
                mT__73(); 

                }
                break;
            case 32 :
                // GCmd__.g:1:236: T__74
                {
                mT__74(); 

                }
                break;
            case 33 :
                // GCmd__.g:1:242: T__75
                {
                mT__75(); 

                }
                break;
            case 34 :
                // GCmd__.g:1:248: T__76
                {
                mT__76(); 

                }
                break;
            case 35 :
                // GCmd__.g:1:254: T__77
                {
                mT__77(); 

                }
                break;
            case 36 :
                // GCmd__.g:1:260: T__78
                {
                mT__78(); 

                }
                break;
            case 37 :
                // GCmd__.g:1:266: T__79
                {
                mT__79(); 

                }
                break;
            case 38 :
                // GCmd__.g:1:272: T__80
                {
                mT__80(); 

                }
                break;
            case 39 :
                // GCmd__.g:1:278: T__81
                {
                mT__81(); 

                }
                break;
            case 40 :
                // GCmd__.g:1:284: GOCLLexerRules. Tokens
                {
                gGOCLLexerRules.mTokens(); 

                }
                break;

        }

    }


    protected DFA1 dfa1 = new DFA1(this);
    static final String DFA1_eotS =
        "\1\uffff\22\23\1\uffff\1\23\1\63\2\23\1\70\2\23\1\73\27\23\1\uffff"+
        "\1\23\1\132\2\23\1\uffff\2\23\1\uffff\1\137\1\140\2\23\1\143\2\23"+
        "\1\146\1\147\7\23\1\157\1\23\1\161\4\23\1\166\6\23\1\uffff\1\23"+
        "\1\176\2\23\2\uffff\2\23\1\uffff\2\23\2\uffff\1\u0085\1\u0086\1"+
        "\u0087\3\23\1\u008b\1\uffff\1\23\1\uffff\4\23\1\uffff\7\23\1\uffff"+
        "\6\23\3\uffff\1\u009f\1\23\1\u00a1\1\uffff\1\23\1\u00a3\11\23\1"+
        "\u00ad\1\u00ae\3\23\1\u00b2\1\23\1\u00b4\1\uffff\1\23\1\uffff\1"+
        "\23\1\uffff\1\23\1\u00b8\6\23\1\u00bf\2\uffff\1\u00c0\1\u00c1\1"+
        "\23\1\uffff\1\u00c3\1\uffff\1\u00c4\2\23\1\uffff\1\u00c7\3\23\1"+
        "\u00cb\1\23\3\uffff\1\23\2\uffff\1\u00ce\1\23\1\uffff\1\u00d0\2"+
        "\23\1\uffff\2\23\1\uffff\1\23\1\uffff\4\23\1\u00da\1\u00db\1\u00dc"+
        "\2\23\3\uffff\1\u00df\1\u00e0\2\uffff";
    static final String DFA1_eofS =
        "\u00e1\uffff";
    static final String DFA1_minS =
        "\1\11\1\143\1\145\1\146\1\157\1\154\1\145\1\157\1\162\1\150\1\154"+
        "\1\141\1\145\1\141\1\165\1\157\1\162\2\145\1\uffff\1\154\1\60\1"+
        "\145\1\164\1\60\1\160\1\145\1\60\1\162\1\144\1\154\1\163\1\166\1"+
        "\154\1\164\2\145\1\165\1\163\1\144\1\145\1\154\1\157\1\161\1\147"+
        "\1\160\1\154\1\145\2\164\1\101\1\uffff\1\156\1\60\1\145\1\157\1"+
        "\uffff\1\154\1\162\1\uffff\2\60\1\111\1\151\1\60\1\164\1\145\2\60"+
        "\1\156\2\145\1\151\1\143\1\163\1\155\1\60\1\165\1\60\2\154\1\141"+
        "\1\167\1\60\2\163\1\155\1\156\1\164\1\151\1\uffff\1\162\1\60\1\151"+
        "\1\141\2\uffff\1\156\1\147\1\uffff\1\162\1\164\2\uffff\3\60\1\146"+
        "\1\165\1\145\1\60\1\uffff\1\145\1\uffff\2\145\1\164\1\145\1\uffff"+
        "\1\124\1\113\1\160\1\144\1\145\2\164\1\uffff\1\145\1\164\1\163\1"+
        "\156\1\157\1\145\3\uffff\1\60\1\164\1\60\1\uffff\1\156\1\60\1\143"+
        "\2\145\1\171\1\151\1\171\1\164\1\145\1\162\2\60\1\163\1\145\1\164"+
        "\1\60\1\171\1\60\1\uffff\1\145\1\uffff\1\143\1\uffff\1\164\1\60"+
        "\1\156\1\160\1\156\1\160\1\171\1\146\1\60\2\uffff\2\60\1\141\1\uffff"+
        "\1\60\1\uffff\1\60\1\145\1\151\1\uffff\1\60\1\145\1\144\1\145\1"+
        "\60\1\151\3\uffff\1\156\2\uffff\1\60\1\157\1\uffff\1\60\2\117\1"+
        "\uffff\1\156\1\143\1\uffff\1\156\1\uffff\2\146\2\145\3\60\1\144"+
        "\1\163\3\uffff\2\60\2\uffff";
    static final String DFA1_maxS =
        "\1\175\1\162\1\145\1\164\1\157\1\163\1\151\1\157\2\162\1\170\1\162"+
        "\1\145\1\141\1\165\1\157\1\162\2\145\1\uffff\1\154\1\172\1\145\1"+
        "\164\1\172\1\160\1\145\1\172\1\162\1\144\1\154\1\163\1\166\1\163"+
        "\1\164\2\145\1\165\1\163\1\144\1\145\1\154\1\157\1\164\1\147\1\160"+
        "\1\154\1\145\2\164\1\125\1\uffff\1\170\1\172\1\145\1\157\1\uffff"+
        "\1\154\1\162\1\uffff\2\172\1\111\1\151\1\172\1\164\1\145\2\172\1"+
        "\156\2\145\1\151\1\143\1\163\1\155\1\172\1\165\1\172\2\154\1\141"+
        "\1\167\1\172\2\163\1\155\1\156\1\164\1\151\1\uffff\1\162\1\172\1"+
        "\151\1\141\2\uffff\1\156\1\147\1\uffff\1\162\1\164\2\uffff\3\172"+
        "\1\146\1\165\1\145\1\172\1\uffff\1\145\1\uffff\2\145\1\164\1\145"+
        "\1\uffff\2\124\1\160\1\144\1\145\2\164\1\uffff\1\145\1\164\1\163"+
        "\1\156\1\157\1\145\3\uffff\1\172\1\164\1\172\1\uffff\1\156\1\172"+
        "\1\143\2\145\1\171\1\151\1\171\1\164\1\145\1\162\2\172\1\163\1\145"+
        "\1\164\1\172\1\171\1\172\1\uffff\1\145\1\uffff\1\143\1\uffff\1\164"+
        "\1\172\1\156\1\160\1\156\1\160\1\171\1\146\1\172\2\uffff\2\172\1"+
        "\141\1\uffff\1\172\1\uffff\1\172\1\145\1\151\1\uffff\1\172\1\145"+
        "\1\144\1\145\1\172\1\151\3\uffff\1\156\2\uffff\1\172\1\157\1\uffff"+
        "\1\172\2\117\1\uffff\1\156\1\143\1\uffff\1\156\1\uffff\2\146\2\145"+
        "\3\172\1\144\1\163\3\uffff\2\172\2\uffff";
    static final String DFA1_acceptS =
        "\23\uffff\1\50\37\uffff\1\7\4\uffff\1\5\2\uffff\1\17\36\uffff\1"+
        "\4\4\uffff\1\10\1\11\2\uffff\1\12\2\uffff\1\13\1\15\7\uffff\1\25"+
        "\1\uffff\1\27\4\uffff\1\44\7\uffff\1\41\6\uffff\1\20\1\23\1\21\3"+
        "\uffff\1\43\23\uffff\1\22\1\uffff\1\24\1\uffff\1\32\11\uffff\1\46"+
        "\1\40\3\uffff\1\35\1\uffff\1\42\3\uffff\1\34\6\uffff\1\45\1\6\1"+
        "\16\1\uffff\1\37\1\47\2\uffff\1\36\3\uffff\1\30\2\uffff\1\26\1\uffff"+
        "\1\1\11\uffff\1\33\1\2\1\3\2\uffff\1\31\1\14";
    static final String DFA1_specialS =
        "\u00e1\uffff}>";
    static final String[] DFA1_transitionS = {
            "\2\23\1\uffff\2\23\22\uffff\1\23\2\uffff\2\23\2\uffff\30\23"+
            "\1\uffff\2\23\1\15\1\17\17\23\1\14\1\16\7\23\1\uffff\1\23\1"+
            "\uffff\1\23\1\uffff\1\5\1\21\1\20\1\6\1\12\1\13\2\23\1\3\2\23"+
            "\1\2\1\23\1\7\1\1\1\10\2\23\1\22\1\11\3\23\1\4\5\23",
            "\1\24\14\uffff\1\26\1\uffff\1\25",
            "\1\27",
            "\1\33\6\uffff\1\31\1\30\5\uffff\1\32",
            "\1\34",
            "\1\36\1\uffff\1\35\4\uffff\1\37",
            "\1\41\3\uffff\1\40",
            "\1\42",
            "\1\43",
            "\1\44\11\uffff\1\45",
            "\1\46\1\uffff\1\47\11\uffff\1\50",
            "\1\51\20\uffff\1\52",
            "\1\53",
            "\1\54",
            "\1\55",
            "\1\56",
            "\1\57",
            "\1\60",
            "\1\61",
            "",
            "\1\62",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\64",
            "\1\65",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\22\23\1\66\1\67\6"+
            "\23",
            "\1\71",
            "\1\72",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\74",
            "\1\75",
            "\1\76",
            "\1\77",
            "\1\100",
            "\1\102\6\uffff\1\101",
            "\1\103",
            "\1\104",
            "\1\105",
            "\1\106",
            "\1\107",
            "\1\110",
            "\1\111",
            "\1\112",
            "\1\113",
            "\1\115\2\uffff\1\114",
            "\1\116",
            "\1\117",
            "\1\120",
            "\1\121",
            "\1\122",
            "\1\123",
            "\1\124\3\uffff\1\126\3\uffff\1\125\13\uffff\1\127",
            "",
            "\1\130\11\uffff\1\131",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\133",
            "\1\134",
            "",
            "\1\135",
            "\1\136",
            "",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\141",
            "\1\142",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\144",
            "\1\145",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\150",
            "\1\151",
            "\1\152",
            "\1\153",
            "\1\154",
            "\1\155",
            "\1\156",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\160",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\162",
            "\1\163",
            "\1\164",
            "\1\165",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\167",
            "\1\170",
            "\1\171",
            "\1\172",
            "\1\173",
            "\1\174",
            "",
            "\1\175",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\177",
            "\1\u0080",
            "",
            "",
            "\1\u0081",
            "\1\u0082",
            "",
            "\1\u0083",
            "\1\u0084",
            "",
            "",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u0088",
            "\1\u0089",
            "\1\u008a",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "",
            "\1\u008c",
            "",
            "\1\u008d",
            "\1\u008e",
            "\1\u008f",
            "\1\u0090",
            "",
            "\1\u0091",
            "\1\u0092\10\uffff\1\u0093",
            "\1\u0094",
            "\1\u0095",
            "\1\u0096",
            "\1\u0097",
            "\1\u0098",
            "",
            "\1\u0099",
            "\1\u009a",
            "\1\u009b",
            "\1\u009c",
            "\1\u009d",
            "\1\u009e",
            "",
            "",
            "",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u00a0",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "",
            "\1\u00a2",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u00a4",
            "\1\u00a5",
            "\1\u00a6",
            "\1\u00a7",
            "\1\u00a8",
            "\1\u00a9",
            "\1\u00aa",
            "\1\u00ab",
            "\1\u00ac",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u00af",
            "\1\u00b0",
            "\1\u00b1",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u00b3",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "",
            "\1\u00b5",
            "",
            "\1\u00b6",
            "",
            "\1\u00b7",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u00b9",
            "\1\u00ba",
            "\1\u00bb",
            "\1\u00bc",
            "\1\u00bd",
            "\1\u00be",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "",
            "",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u00c2",
            "",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u00c5",
            "\1\u00c6",
            "",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u00c8",
            "\1\u00c9",
            "\1\u00ca",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u00cc",
            "",
            "",
            "",
            "\1\u00cd",
            "",
            "",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u00cf",
            "",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u00d1",
            "\1\u00d2",
            "",
            "\1\u00d3",
            "\1\u00d4",
            "",
            "\1\u00d5",
            "",
            "\1\u00d6",
            "\1\u00d7",
            "\1\u00d8",
            "\1\u00d9",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\1\u00dd",
            "\1\u00de",
            "",
            "",
            "",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
            "\12\23\7\uffff\32\23\4\uffff\1\23\1\uffff\32\23",
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
            return "1:1: Tokens : ( LITERAL_oclAsType | LITERAL_oclIsKindOf | LITERAL_oclIsTypeOf | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | GOCLLexerRules. Tokens );";
        }
    }
 

}