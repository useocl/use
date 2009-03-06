// $ANTLR 3.1b1 GOCL__.g 2009-03-06 10:35:28

package org.tzi.use.parser.ocl; 

// ------------------------------------
// OCL lexer
// ------------------------------------

import java.io.PrintWriter;
import org.tzi.use.parser.ParseErrorHandler;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class GOCLLexer extends Lexer {
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
    public static final int INT=29;
    public static final int COLON_COLON=40;
    public static final int MINUS=19;
    public static final int Tokens=70;
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
    
        private PrintWriter fErr;
        private ParseErrorHandler fParseErrorHandler;
    
        Stack paraphrase = new Stack();
        
        public String getFilename() {
            return fParseErrorHandler.getFileName();
        }
        
        public void reportError(RecognitionException ex) {
            fParseErrorHandler.reportError(
    			ex.line + ":" + (ex.charPositionInLine + 1) + ": " + ex.getMessage());
        }
     
        public void init(ParseErrorHandler handler) {
            fParseErrorHandler = handler;
    		this.gGOCLLexerRules.init(handler);
        }


    // delegates
    public GOCL_GOCLLexerRules gGOCLLexerRules;
    // delegators

    public GOCLLexer() {;} 
    public GOCLLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public GOCLLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
        gGOCLLexerRules = new GOCL_GOCLLexerRules(input, state, this);
    }
    public String getGrammarFileName() { return "GOCL__.g"; }

    // $ANTLR start LITERAL_oclAsType
    public final void mLITERAL_oclAsType() throws RecognitionException {
        try {
            int _type = LITERAL_oclAsType;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GOCL__.g:34:19: ( 'oclAsType' )
            // GOCL__.g:34:21: 'oclAsType'
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
            // GOCL__.g:35:21: ( 'oclIsKindOf' )
            // GOCL__.g:35:23: 'oclIsKindOf'
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
            // GOCL__.g:36:21: ( 'oclIsTypeOf' )
            // GOCL__.g:36:23: 'oclIsTypeOf'
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
            // GOCL__.g:37:7: ( 'let' )
            // GOCL__.g:37:9: 'let'
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
            // GOCL__.g:38:7: ( 'in' )
            // GOCL__.g:38:9: 'in'
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
            // GOCL__.g:39:7: ( 'implies' )
            // GOCL__.g:39:9: 'implies'
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
            // GOCL__.g:40:7: ( 'or' )
            // GOCL__.g:40:9: 'or'
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
            // GOCL__.g:41:7: ( 'xor' )
            // GOCL__.g:41:9: 'xor'
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
            // GOCL__.g:42:7: ( 'and' )
            // GOCL__.g:42:9: 'and'
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
            // GOCL__.g:43:7: ( 'div' )
            // GOCL__.g:43:9: 'div'
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
            // GOCL__.g:44:7: ( 'not' )
            // GOCL__.g:44:9: 'not'
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
            // GOCL__.g:45:7: ( 'allInstances' )
            // GOCL__.g:45:9: 'allInstances'
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
            // GOCL__.g:46:7: ( 'pre' )
            // GOCL__.g:46:9: 'pre'
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
            // GOCL__.g:47:7: ( 'iterate' )
            // GOCL__.g:47:9: 'iterate'
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
            // GOCL__.g:48:7: ( 'if' )
            // GOCL__.g:48:9: 'if'
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
            // GOCL__.g:49:7: ( 'then' )
            // GOCL__.g:49:9: 'then'
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
            // GOCL__.g:50:7: ( 'else' )
            // GOCL__.g:50:9: 'else'
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
            // GOCL__.g:51:7: ( 'endif' )
            // GOCL__.g:51:9: 'endif'
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
            // GOCL__.g:52:7: ( 'true' )
            // GOCL__.g:52:9: 'true'
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
            // GOCL__.g:53:7: ( 'false' )
            // GOCL__.g:53:9: 'false'
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
            // GOCL__.g:54:7: ( 'Set' )
            // GOCL__.g:54:9: 'Set'
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
            // GOCL__.g:55:7: ( 'Sequence' )
            // GOCL__.g:55:9: 'Sequence'
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
            // GOCL__.g:56:7: ( 'Bag' )
            // GOCL__.g:56:9: 'Bag'
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
            // GOCL__.g:57:7: ( 'oclEmpty' )
            // GOCL__.g:57:9: 'oclEmpty'
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
            // GOCL__.g:58:7: ( 'oclUndefined' )
            // GOCL__.g:58:9: 'oclUndefined'
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
            // GOCL__.g:59:7: ( 'Tuple' )
            // GOCL__.g:59:9: 'Tuple'
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
            // GOCL__.g:60:7: ( 'Collection' )
            // GOCL__.g:60:9: 'Collection'
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

    public void mTokens() throws RecognitionException {
        // GOCL__.g:1:8: ( LITERAL_oclAsType | LITERAL_oclIsKindOf | LITERAL_oclIsTypeOf | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | GOCLLexerRules. Tokens )
        int alt1=28;
        alt1 = dfa1.predict(input);
        switch (alt1) {
            case 1 :
                // GOCL__.g:1:10: LITERAL_oclAsType
                {
                mLITERAL_oclAsType(); 

                }
                break;
            case 2 :
                // GOCL__.g:1:28: LITERAL_oclIsKindOf
                {
                mLITERAL_oclIsKindOf(); 

                }
                break;
            case 3 :
                // GOCL__.g:1:48: LITERAL_oclIsTypeOf
                {
                mLITERAL_oclIsTypeOf(); 

                }
                break;
            case 4 :
                // GOCL__.g:1:68: T__46
                {
                mT__46(); 

                }
                break;
            case 5 :
                // GOCL__.g:1:74: T__47
                {
                mT__47(); 

                }
                break;
            case 6 :
                // GOCL__.g:1:80: T__48
                {
                mT__48(); 

                }
                break;
            case 7 :
                // GOCL__.g:1:86: T__49
                {
                mT__49(); 

                }
                break;
            case 8 :
                // GOCL__.g:1:92: T__50
                {
                mT__50(); 

                }
                break;
            case 9 :
                // GOCL__.g:1:98: T__51
                {
                mT__51(); 

                }
                break;
            case 10 :
                // GOCL__.g:1:104: T__52
                {
                mT__52(); 

                }
                break;
            case 11 :
                // GOCL__.g:1:110: T__53
                {
                mT__53(); 

                }
                break;
            case 12 :
                // GOCL__.g:1:116: T__54
                {
                mT__54(); 

                }
                break;
            case 13 :
                // GOCL__.g:1:122: T__55
                {
                mT__55(); 

                }
                break;
            case 14 :
                // GOCL__.g:1:128: T__56
                {
                mT__56(); 

                }
                break;
            case 15 :
                // GOCL__.g:1:134: T__57
                {
                mT__57(); 

                }
                break;
            case 16 :
                // GOCL__.g:1:140: T__58
                {
                mT__58(); 

                }
                break;
            case 17 :
                // GOCL__.g:1:146: T__59
                {
                mT__59(); 

                }
                break;
            case 18 :
                // GOCL__.g:1:152: T__60
                {
                mT__60(); 

                }
                break;
            case 19 :
                // GOCL__.g:1:158: T__61
                {
                mT__61(); 

                }
                break;
            case 20 :
                // GOCL__.g:1:164: T__62
                {
                mT__62(); 

                }
                break;
            case 21 :
                // GOCL__.g:1:170: T__63
                {
                mT__63(); 

                }
                break;
            case 22 :
                // GOCL__.g:1:176: T__64
                {
                mT__64(); 

                }
                break;
            case 23 :
                // GOCL__.g:1:182: T__65
                {
                mT__65(); 

                }
                break;
            case 24 :
                // GOCL__.g:1:188: T__66
                {
                mT__66(); 

                }
                break;
            case 25 :
                // GOCL__.g:1:194: T__67
                {
                mT__67(); 

                }
                break;
            case 26 :
                // GOCL__.g:1:200: T__68
                {
                mT__68(); 

                }
                break;
            case 27 :
                // GOCL__.g:1:206: T__69
                {
                mT__69(); 

                }
                break;
            case 28 :
                // GOCL__.g:1:212: GOCLLexerRules. Tokens
                {
                gGOCLLexerRules.mTokens(); 

                }
                break;

        }

    }


    protected DFA1 dfa1 = new DFA1(this);
    static final String DFA1_eotS =
        "\1\uffff\17\20\1\uffff\1\20\1\50\1\20\1\52\2\20\1\55\20\20\1\uffff"+
        "\1\102\1\uffff\2\20\1\uffff\1\105\1\106\1\20\1\110\1\111\1\112\5"+
        "\20\1\120\1\20\1\122\6\20\1\uffff\2\20\2\uffff\1\20\3\uffff\1\134"+
        "\1\135\1\136\2\20\1\uffff\1\20\1\uffff\11\20\3\uffff\1\154\1\155"+
        "\1\20\1\157\11\20\2\uffff\1\20\1\uffff\6\20\1\u0080\1\u0081\6\20"+
        "\1\u0088\1\20\2\uffff\1\20\1\u008b\1\20\1\u008d\2\20\1\uffff\2\20"+
        "\1\uffff\1\20\1\uffff\4\20\1\u0097\1\u0098\1\u0099\2\20\3\uffff"+
        "\1\u009c\1\u009d\2\uffff";
    static final String DFA1_eofS =
        "\u009e\uffff";
    static final String DFA1_minS =
        "\1\11\1\143\1\145\1\146\1\157\1\154\1\151\1\157\1\162\1\150\1\154"+
        "\1\141\1\145\1\141\1\165\1\157\1\uffff\1\154\1\60\1\164\1\60\1\160"+
        "\1\145\1\60\1\162\1\144\1\154\1\166\1\164\2\145\1\165\1\163\1\144"+
        "\1\154\1\161\1\147\1\160\1\154\1\101\1\uffff\1\60\1\uffff\1\154"+
        "\1\162\1\uffff\2\60\1\111\3\60\1\156\2\145\1\151\1\163\1\60\1\165"+
        "\1\60\2\154\2\163\1\155\1\156\1\uffff\1\151\1\141\2\uffff\1\156"+
        "\3\uffff\3\60\1\146\1\145\1\uffff\1\145\1\uffff\2\145\1\124\1\113"+
        "\1\160\1\144\1\145\1\164\1\163\3\uffff\2\60\1\156\1\60\1\143\1\171"+
        "\1\151\1\171\1\164\1\145\1\163\1\145\1\164\2\uffff\1\143\1\uffff"+
        "\1\164\1\160\1\156\1\160\1\171\1\146\2\60\1\141\1\145\1\151\1\145"+
        "\1\144\1\145\1\60\1\151\2\uffff\1\156\1\60\1\157\1\60\2\117\1\uffff"+
        "\1\156\1\143\1\uffff\1\156\1\uffff\2\146\2\145\3\60\1\144\1\163"+
        "\3\uffff\2\60\2\uffff";
    static final String DFA1_maxS =
        "\1\175\1\162\1\145\1\164\1\157\1\156\1\151\1\157\2\162\1\156\1\141"+
        "\1\145\1\141\1\165\1\157\1\uffff\1\154\1\172\1\164\1\172\1\160\1"+
        "\145\1\172\1\162\1\144\1\154\1\166\1\164\2\145\1\165\1\163\1\144"+
        "\1\154\1\164\1\147\1\160\1\154\1\125\1\uffff\1\172\1\uffff\1\154"+
        "\1\162\1\uffff\2\172\1\111\3\172\1\156\2\145\1\151\1\163\1\172\1"+
        "\165\1\172\2\154\2\163\1\155\1\156\1\uffff\1\151\1\141\2\uffff\1"+
        "\156\3\uffff\3\172\1\146\1\145\1\uffff\1\145\1\uffff\2\145\2\124"+
        "\1\160\1\144\1\145\1\164\1\163\3\uffff\2\172\1\156\1\172\1\143\1"+
        "\171\1\151\1\171\1\164\1\145\1\163\1\145\1\164\2\uffff\1\143\1\uffff"+
        "\1\164\1\160\1\156\1\160\1\171\1\146\2\172\1\141\1\145\1\151\1\145"+
        "\1\144\1\145\1\172\1\151\2\uffff\1\156\1\172\1\157\1\172\2\117\1"+
        "\uffff\1\156\1\143\1\uffff\1\156\1\uffff\2\146\2\145\3\172\1\144"+
        "\1\163\3\uffff\2\172\2\uffff";
    static final String DFA1_acceptS =
        "\20\uffff\1\34\27\uffff\1\7\1\uffff\1\5\2\uffff\1\17\24\uffff\1"+
        "\4\2\uffff\1\10\1\11\1\uffff\1\12\1\13\1\15\5\uffff\1\25\1\uffff"+
        "\1\27\11\uffff\1\20\1\23\1\21\15\uffff\1\22\1\24\1\uffff\1\32\20"+
        "\uffff\1\6\1\16\6\uffff\1\30\2\uffff\1\26\1\uffff\1\1\11\uffff\1"+
        "\33\1\2\1\3\2\uffff\1\31\1\14";
    static final String DFA1_specialS =
        "\u009e\uffff}>";
    static final String[] DFA1_transitionS = {
            "\2\20\1\uffff\2\20\22\uffff\1\20\2\uffff\2\20\2\uffff\30\20"+
            "\1\uffff\2\20\1\15\1\17\17\20\1\14\1\16\7\20\1\uffff\1\20\1"+
            "\uffff\1\20\1\uffff\1\5\2\20\1\6\1\12\1\13\2\20\1\3\2\20\1\2"+
            "\1\20\1\7\1\1\1\10\3\20\1\11\3\20\1\4\5\20",
            "\1\21\16\uffff\1\22",
            "\1\23",
            "\1\27\6\uffff\1\25\1\24\5\uffff\1\26",
            "\1\30",
            "\1\32\1\uffff\1\31",
            "\1\33",
            "\1\34",
            "\1\35",
            "\1\36\11\uffff\1\37",
            "\1\40\1\uffff\1\41",
            "\1\42",
            "\1\43",
            "\1\44",
            "\1\45",
            "\1\46",
            "",
            "\1\47",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\51",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\53",
            "\1\54",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\56",
            "\1\57",
            "\1\60",
            "\1\61",
            "\1\62",
            "\1\63",
            "\1\64",
            "\1\65",
            "\1\66",
            "\1\67",
            "\1\70",
            "\1\72\2\uffff\1\71",
            "\1\73",
            "\1\74",
            "\1\75",
            "\1\76\3\uffff\1\100\3\uffff\1\77\13\uffff\1\101",
            "",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "",
            "\1\103",
            "\1\104",
            "",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\107",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\113",
            "\1\114",
            "\1\115",
            "\1\116",
            "\1\117",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\121",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\123",
            "\1\124",
            "\1\125",
            "\1\126",
            "\1\127",
            "\1\130",
            "",
            "\1\131",
            "\1\132",
            "",
            "",
            "\1\133",
            "",
            "",
            "",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\137",
            "\1\140",
            "",
            "\1\141",
            "",
            "\1\142",
            "\1\143",
            "\1\144",
            "\1\145\10\uffff\1\146",
            "\1\147",
            "\1\150",
            "\1\151",
            "\1\152",
            "\1\153",
            "",
            "",
            "",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\156",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\160",
            "\1\161",
            "\1\162",
            "\1\163",
            "\1\164",
            "\1\165",
            "\1\166",
            "\1\167",
            "\1\170",
            "",
            "",
            "\1\171",
            "",
            "\1\172",
            "\1\173",
            "\1\174",
            "\1\175",
            "\1\176",
            "\1\177",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\u0082",
            "\1\u0083",
            "\1\u0084",
            "\1\u0085",
            "\1\u0086",
            "\1\u0087",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\u0089",
            "",
            "",
            "\1\u008a",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\u008c",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\u008e",
            "\1\u008f",
            "",
            "\1\u0090",
            "\1\u0091",
            "",
            "\1\u0092",
            "",
            "\1\u0093",
            "\1\u0094",
            "\1\u0095",
            "\1\u0096",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\1\u009a",
            "\1\u009b",
            "",
            "",
            "",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
            "\12\20\7\uffff\32\20\4\uffff\1\20\1\uffff\32\20",
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
            return "1:1: Tokens : ( LITERAL_oclAsType | LITERAL_oclIsKindOf | LITERAL_oclIsTypeOf | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | GOCLLexerRules. Tokens );";
        }
    }
 

}