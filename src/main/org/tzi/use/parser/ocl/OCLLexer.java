// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 OCL.g 2009-06-04 18:03:24

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
import java.util.Map;
import java.util.HashMap;
public class OCLLexer extends Lexer {
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__64=64;
    public static final int STAR=17;
    public static final int LBRACK=24;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int ESC=40;
    public static final int LBRACE=30;
    public static final int DOTDOT=32;
    public static final int T__61=61;
    public static final int T__60=60;
    public static final int EOF=-1;
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
    public static final int T__44=44;
    public static final int RBRACE=31;
    public static final int T__45=45;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int HASH=29;
    public static final int HEX_DIGIT=41;
    public static final int INT=26;
    public static final int COLON_COLON=37;
    public static final int MINUS=16;
    public static final int SEMI=23;
    public static final int COLON=5;
    public static final int REAL=27;
    public static final int T__71=71;
    public static final int WS=34;
    public static final int T__72=72;
    public static final int NEWLINE=33;
    public static final int T__70=70;
    public static final int SL_COMMENT=35;
    public static final int VOCAB=42;
    public static final int ARROW=19;
    public static final int LESS_EQUAL=13;
    public static final int GREATER_EQUAL=14;
    public static final int BAR=22;
    public static final int STRING=28;

        private ParseErrorHandler fParseErrorHandler;

        public String getFilename() {
            return fParseErrorHandler.getFileName();
        }
        
        public void emitErrorMessage(String msg) {
           	fParseErrorHandler.reportError(msg);
    	}
     
        public void init(ParseErrorHandler handler) {
            fParseErrorHandler = handler;
        }


    // delegates
    // delegators

    public OCLLexer() {;} 
    public OCLLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public OCLLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "OCL.g"; }

    // $ANTLR start "T__43"
    public final void mT__43() throws RecognitionException {
        try {
            int _type = T__43;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:29:7: ( 'let' )
            // OCL.g:29:9: 'let'
            {
            match("let"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__43"

    // $ANTLR start "T__44"
    public final void mT__44() throws RecognitionException {
        try {
            int _type = T__44;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:30:7: ( 'in' )
            // OCL.g:30:9: 'in'
            {
            match("in"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__44"

    // $ANTLR start "T__45"
    public final void mT__45() throws RecognitionException {
        try {
            int _type = T__45;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:31:7: ( 'implies' )
            // OCL.g:31:9: 'implies'
            {
            match("implies"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__45"

    // $ANTLR start "T__46"
    public final void mT__46() throws RecognitionException {
        try {
            int _type = T__46;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:32:7: ( 'or' )
            // OCL.g:32:9: 'or'
            {
            match("or"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__46"

    // $ANTLR start "T__47"
    public final void mT__47() throws RecognitionException {
        try {
            int _type = T__47;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:33:7: ( 'xor' )
            // OCL.g:33:9: 'xor'
            {
            match("xor"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__47"

    // $ANTLR start "T__48"
    public final void mT__48() throws RecognitionException {
        try {
            int _type = T__48;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:34:7: ( 'and' )
            // OCL.g:34:9: 'and'
            {
            match("and"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__48"

    // $ANTLR start "T__49"
    public final void mT__49() throws RecognitionException {
        try {
            int _type = T__49;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:35:7: ( 'div' )
            // OCL.g:35:9: 'div'
            {
            match("div"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__49"

    // $ANTLR start "T__50"
    public final void mT__50() throws RecognitionException {
        try {
            int _type = T__50;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:36:7: ( 'not' )
            // OCL.g:36:9: 'not'
            {
            match("not"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__50"

    // $ANTLR start "T__51"
    public final void mT__51() throws RecognitionException {
        try {
            int _type = T__51;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:37:7: ( 'allInstances' )
            // OCL.g:37:9: 'allInstances'
            {
            match("allInstances"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__51"

    // $ANTLR start "T__52"
    public final void mT__52() throws RecognitionException {
        try {
            int _type = T__52;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:38:7: ( 'pre' )
            // OCL.g:38:9: 'pre'
            {
            match("pre"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__52"

    // $ANTLR start "T__53"
    public final void mT__53() throws RecognitionException {
        try {
            int _type = T__53;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:39:7: ( 'iterate' )
            // OCL.g:39:9: 'iterate'
            {
            match("iterate"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__53"

    // $ANTLR start "T__54"
    public final void mT__54() throws RecognitionException {
        try {
            int _type = T__54;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:40:7: ( 'oclAsType' )
            // OCL.g:40:9: 'oclAsType'
            {
            match("oclAsType"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__54"

    // $ANTLR start "T__55"
    public final void mT__55() throws RecognitionException {
        try {
            int _type = T__55;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:41:7: ( 'oclIsKindOf' )
            // OCL.g:41:9: 'oclIsKindOf'
            {
            match("oclIsKindOf"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__55"

    // $ANTLR start "T__56"
    public final void mT__56() throws RecognitionException {
        try {
            int _type = T__56;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:42:7: ( 'oclIsTypeOf' )
            // OCL.g:42:9: 'oclIsTypeOf'
            {
            match("oclIsTypeOf"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__56"

    // $ANTLR start "T__57"
    public final void mT__57() throws RecognitionException {
        try {
            int _type = T__57;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:43:7: ( 'if' )
            // OCL.g:43:9: 'if'
            {
            match("if"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__57"

    // $ANTLR start "T__58"
    public final void mT__58() throws RecognitionException {
        try {
            int _type = T__58;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:44:7: ( 'then' )
            // OCL.g:44:9: 'then'
            {
            match("then"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__58"

    // $ANTLR start "T__59"
    public final void mT__59() throws RecognitionException {
        try {
            int _type = T__59;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:45:7: ( 'else' )
            // OCL.g:45:9: 'else'
            {
            match("else"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__59"

    // $ANTLR start "T__60"
    public final void mT__60() throws RecognitionException {
        try {
            int _type = T__60;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:46:7: ( 'endif' )
            // OCL.g:46:9: 'endif'
            {
            match("endif"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__60"

    // $ANTLR start "T__61"
    public final void mT__61() throws RecognitionException {
        try {
            int _type = T__61;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:47:7: ( 'true' )
            // OCL.g:47:9: 'true'
            {
            match("true"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__61"

    // $ANTLR start "T__62"
    public final void mT__62() throws RecognitionException {
        try {
            int _type = T__62;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:48:7: ( 'false' )
            // OCL.g:48:9: 'false'
            {
            match("false"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__62"

    // $ANTLR start "T__63"
    public final void mT__63() throws RecognitionException {
        try {
            int _type = T__63;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:49:7: ( 'Set' )
            // OCL.g:49:9: 'Set'
            {
            match("Set"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__63"

    // $ANTLR start "T__64"
    public final void mT__64() throws RecognitionException {
        try {
            int _type = T__64;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:50:7: ( 'Sequence' )
            // OCL.g:50:9: 'Sequence'
            {
            match("Sequence"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__64"

    // $ANTLR start "T__65"
    public final void mT__65() throws RecognitionException {
        try {
            int _type = T__65;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:51:7: ( 'Bag' )
            // OCL.g:51:9: 'Bag'
            {
            match("Bag"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__65"

    // $ANTLR start "T__66"
    public final void mT__66() throws RecognitionException {
        try {
            int _type = T__66;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:52:7: ( 'OrderedSet' )
            // OCL.g:52:9: 'OrderedSet'
            {
            match("OrderedSet"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__66"

    // $ANTLR start "T__67"
    public final void mT__67() throws RecognitionException {
        try {
            int _type = T__67;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:53:7: ( 'oclEmpty' )
            // OCL.g:53:9: 'oclEmpty'
            {
            match("oclEmpty"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__67"

    // $ANTLR start "T__68"
    public final void mT__68() throws RecognitionException {
        try {
            int _type = T__68;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:54:7: ( 'oclUndefined' )
            // OCL.g:54:9: 'oclUndefined'
            {
            match("oclUndefined"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__68"

    // $ANTLR start "T__69"
    public final void mT__69() throws RecognitionException {
        try {
            int _type = T__69;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:55:7: ( 'Undefined' )
            // OCL.g:55:9: 'Undefined'
            {
            match("Undefined"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__69"

    // $ANTLR start "T__70"
    public final void mT__70() throws RecognitionException {
        try {
            int _type = T__70;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:56:7: ( 'null' )
            // OCL.g:56:9: 'null'
            {
            match("null"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__70"

    // $ANTLR start "T__71"
    public final void mT__71() throws RecognitionException {
        try {
            int _type = T__71;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:57:7: ( 'Tuple' )
            // OCL.g:57:9: 'Tuple'
            {
            match("Tuple"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__71"

    // $ANTLR start "T__72"
    public final void mT__72() throws RecognitionException {
        try {
            int _type = T__72;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:58:7: ( 'Collection' )
            // OCL.g:58:9: 'Collection'
            {
            match("Collection"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__72"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:659:3: ( ( ' ' | '\\t' | '\\f' | NEWLINE ) )
            // OCL.g:660:5: ( ' ' | '\\t' | '\\f' | NEWLINE )
            {
            // OCL.g:660:5: ( ' ' | '\\t' | '\\f' | NEWLINE )
            int alt1=4;
            switch ( input.LA(1) ) {
            case ' ':
                {
                alt1=1;
                }
                break;
            case '\t':
                {
                alt1=2;
                }
                break;
            case '\f':
                {
                alt1=3;
                }
                break;
            case '\n':
            case '\r':
                {
                alt1=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // OCL.g:660:7: ' '
                    {
                    match(' '); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // OCL.g:661:7: '\\t'
                    {
                    match('\t'); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // OCL.g:662:7: '\\f'
                    {
                    match('\f'); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // OCL.g:663:7: NEWLINE
                    {
                    mNEWLINE(); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               _channel=HIDDEN; 
            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "SL_COMMENT"
    public final void mSL_COMMENT() throws RecognitionException {
        try {
            int _type = SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:669:11: ( ( '//' | '--' ) (~ ( '\\n' | '\\r' ) )* NEWLINE )
            // OCL.g:670:5: ( '//' | '--' ) (~ ( '\\n' | '\\r' ) )* NEWLINE
            {
            // OCL.g:670:5: ( '//' | '--' )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='/') ) {
                alt2=1;
            }
            else if ( (LA2_0=='-') ) {
                alt2=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // OCL.g:670:6: '//'
                    {
                    match("//"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // OCL.g:670:13: '--'
                    {
                    match("--"); if (state.failed) return ;


                    }
                    break;

            }

            // OCL.g:671:5: (~ ( '\\n' | '\\r' ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='\u0000' && LA3_0<='\t')||(LA3_0>='\u000B' && LA3_0<='\f')||(LA3_0>='\u000E' && LA3_0<='\uFFFF')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // OCL.g:671:6: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();
            	    state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            mNEWLINE(); if (state.failed) return ;
            if ( state.backtracking==0 ) {
               _channel=HIDDEN; 
            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SL_COMMENT"

    // $ANTLR start "ML_COMMENT"
    public final void mML_COMMENT() throws RecognitionException {
        try {
            int _type = ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:676:11: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // OCL.g:677:5: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); if (state.failed) return ;

            // OCL.g:677:10: ( options {greedy=false; } : . )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0=='*') ) {
                    int LA4_1 = input.LA(2);

                    if ( (LA4_1=='/') ) {
                        alt4=2;
                    }
                    else if ( ((LA4_1>='\u0000' && LA4_1<='.')||(LA4_1>='0' && LA4_1<='\uFFFF')) ) {
                        alt4=1;
                    }


                }
                else if ( ((LA4_0>='\u0000' && LA4_0<=')')||(LA4_0>='+' && LA4_0<='\uFFFF')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // OCL.g:677:38: .
            	    {
            	    matchAny(); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            match("*/"); if (state.failed) return ;

            if ( state.backtracking==0 ) {
               _channel=HIDDEN; 
            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ML_COMMENT"

    // $ANTLR start "NEWLINE"
    public final void mNEWLINE() throws RecognitionException {
        try {
            // OCL.g:680:9: ( '\\r\\n' | '\\r' | '\\n' )
            int alt5=3;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='\r') ) {
                int LA5_1 = input.LA(2);

                if ( (LA5_1=='\n') ) {
                    alt5=1;
                }
                else {
                    alt5=2;}
            }
            else if ( (LA5_0=='\n') ) {
                alt5=3;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // OCL.g:681:5: '\\r\\n'
                    {
                    match("\r\n"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // OCL.g:681:14: '\\r'
                    {
                    match('\r'); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // OCL.g:681:21: '\\n'
                    {
                    match('\n'); if (state.failed) return ;

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "NEWLINE"

    // $ANTLR start "ARROW"
    public final void mARROW() throws RecognitionException {
        try {
            int _type = ARROW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:684:10: ( '->' )
            // OCL.g:684:12: '->'
            {
            match("->"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ARROW"

    // $ANTLR start "AT"
    public final void mAT() throws RecognitionException {
        try {
            int _type = AT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:685:11: ( '@' )
            // OCL.g:685:13: '@'
            {
            match('@'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AT"

    // $ANTLR start "BAR"
    public final void mBAR() throws RecognitionException {
        try {
            int _type = BAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:686:8: ( '|' )
            // OCL.g:686:10: '|'
            {
            match('|'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BAR"

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:687:10: ( ':' )
            // OCL.g:687:12: ':'
            {
            match(':'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COLON"

    // $ANTLR start "COLON_COLON"
    public final void mCOLON_COLON() throws RecognitionException {
        try {
            int _type = COLON_COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:688:14: ( '::' )
            // OCL.g:688:16: '::'
            {
            match("::"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COLON_COLON"

    // $ANTLR start "COLON_EQUAL"
    public final void mCOLON_EQUAL() throws RecognitionException {
        try {
            int _type = COLON_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:689:14: ( ':=' )
            // OCL.g:689:16: ':='
            {
            match(":="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COLON_EQUAL"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:690:10: ( ',' )
            // OCL.g:690:12: ','
            {
            match(','); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:691:8: ( '.' )
            // OCL.g:691:10: '.'
            {
            match('.'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "DOTDOT"
    public final void mDOTDOT() throws RecognitionException {
        try {
            int _type = DOTDOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:692:11: ( '..' )
            // OCL.g:692:13: '..'
            {
            match(".."); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOTDOT"

    // $ANTLR start "EQUAL"
    public final void mEQUAL() throws RecognitionException {
        try {
            int _type = EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:693:10: ( '=' )
            // OCL.g:693:12: '='
            {
            match('='); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQUAL"

    // $ANTLR start "GREATER"
    public final void mGREATER() throws RecognitionException {
        try {
            int _type = GREATER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:694:11: ( '>' )
            // OCL.g:694:13: '>'
            {
            match('>'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GREATER"

    // $ANTLR start "GREATER_EQUAL"
    public final void mGREATER_EQUAL() throws RecognitionException {
        try {
            int _type = GREATER_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:695:15: ( '>=' )
            // OCL.g:695:17: '>='
            {
            match(">="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GREATER_EQUAL"

    // $ANTLR start "HASH"
    public final void mHASH() throws RecognitionException {
        try {
            int _type = HASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:696:9: ( '#' )
            // OCL.g:696:11: '#'
            {
            match('#'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "HASH"

    // $ANTLR start "LBRACE"
    public final void mLBRACE() throws RecognitionException {
        try {
            int _type = LBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:697:11: ( '{' )
            // OCL.g:697:13: '{'
            {
            match('{'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LBRACE"

    // $ANTLR start "LBRACK"
    public final void mLBRACK() throws RecognitionException {
        try {
            int _type = LBRACK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:698:11: ( '[' )
            // OCL.g:698:13: '['
            {
            match('['); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LBRACK"

    // $ANTLR start "LESS"
    public final void mLESS() throws RecognitionException {
        try {
            int _type = LESS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:699:9: ( '<' )
            // OCL.g:699:11: '<'
            {
            match('<'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LESS"

    // $ANTLR start "LESS_EQUAL"
    public final void mLESS_EQUAL() throws RecognitionException {
        try {
            int _type = LESS_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:700:14: ( '<=' )
            // OCL.g:700:16: '<='
            {
            match("<="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LESS_EQUAL"

    // $ANTLR start "LPAREN"
    public final void mLPAREN() throws RecognitionException {
        try {
            int _type = LPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:701:11: ( '(' )
            // OCL.g:701:13: '('
            {
            match('('); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LPAREN"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:702:10: ( '-' )
            // OCL.g:702:12: '-'
            {
            match('-'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "NOT_EQUAL"
    public final void mNOT_EQUAL() throws RecognitionException {
        try {
            int _type = NOT_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:703:13: ( '<>' )
            // OCL.g:703:15: '<>'
            {
            match("<>"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOT_EQUAL"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:704:9: ( '+' )
            // OCL.g:704:11: '+'
            {
            match('+'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "RBRACE"
    public final void mRBRACE() throws RecognitionException {
        try {
            int _type = RBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:705:11: ( '}' )
            // OCL.g:705:13: '}'
            {
            match('}'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RBRACE"

    // $ANTLR start "RBRACK"
    public final void mRBRACK() throws RecognitionException {
        try {
            int _type = RBRACK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:706:11: ( ']' )
            // OCL.g:706:13: ']'
            {
            match(']'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RBRACK"

    // $ANTLR start "RPAREN"
    public final void mRPAREN() throws RecognitionException {
        try {
            int _type = RPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:707:10: ( ')' )
            // OCL.g:707:12: ')'
            {
            match(')'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RPAREN"

    // $ANTLR start "SEMI"
    public final void mSEMI() throws RecognitionException {
        try {
            int _type = SEMI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:708:8: ( ';' )
            // OCL.g:708:10: ';'
            {
            match(';'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SEMI"

    // $ANTLR start "SLASH"
    public final void mSLASH() throws RecognitionException {
        try {
            int _type = SLASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:709:10: ( '/' )
            // OCL.g:709:12: '/'
            {
            match('/'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SLASH"

    // $ANTLR start "STAR"
    public final void mSTAR() throws RecognitionException {
        try {
            int _type = STAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:710:9: ( '*' )
            // OCL.g:710:11: '*'
            {
            match('*'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STAR"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            // OCL.g:713:4: ( ( '0' .. '9' )+ )
            // OCL.g:714:5: ( '0' .. '9' )+
            {
            // OCL.g:714:5: ( '0' .. '9' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( ((LA6_0>='0' && LA6_0<='9')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // OCL.g:714:6: '0' .. '9'
            	    {
            	    matchRange('0','9'); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "REAL"
    public final void mREAL() throws RecognitionException {
        try {
            // OCL.g:718:5: ( INT ( '.' INT ( ( 'e' | 'E' ) ( '+' | '-' )? INT )? | ( 'e' | 'E' ) ( '+' | '-' )? INT ) )
            // OCL.g:719:5: INT ( '.' INT ( ( 'e' | 'E' ) ( '+' | '-' )? INT )? | ( 'e' | 'E' ) ( '+' | '-' )? INT )
            {
            mINT(); if (state.failed) return ;
            // OCL.g:719:9: ( '.' INT ( ( 'e' | 'E' ) ( '+' | '-' )? INT )? | ( 'e' | 'E' ) ( '+' | '-' )? INT )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='.') ) {
                alt10=1;
            }
            else if ( (LA10_0=='E'||LA10_0=='e') ) {
                alt10=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // OCL.g:719:10: '.' INT ( ( 'e' | 'E' ) ( '+' | '-' )? INT )?
                    {
                    match('.'); if (state.failed) return ;
                    mINT(); if (state.failed) return ;
                    // OCL.g:719:18: ( ( 'e' | 'E' ) ( '+' | '-' )? INT )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='E'||LA8_0=='e') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // OCL.g:719:19: ( 'e' | 'E' ) ( '+' | '-' )? INT
                            {
                            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                                input.consume();
                            state.failed=false;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return ;}
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;}

                            // OCL.g:719:31: ( '+' | '-' )?
                            int alt7=2;
                            int LA7_0 = input.LA(1);

                            if ( (LA7_0=='+'||LA7_0=='-') ) {
                                alt7=1;
                            }
                            switch (alt7) {
                                case 1 :
                                    // OCL.g:
                                    {
                                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                                        input.consume();
                                    state.failed=false;
                                    }
                                    else {
                                        if (state.backtracking>0) {state.failed=true; return ;}
                                        MismatchedSetException mse = new MismatchedSetException(null,input);
                                        recover(mse);
                                        throw mse;}


                                    }
                                    break;

                            }

                            mINT(); if (state.failed) return ;

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // OCL.g:719:52: ( 'e' | 'E' ) ( '+' | '-' )? INT
                    {
                    if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                        input.consume();
                    state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    // OCL.g:719:64: ( '+' | '-' )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0=='+'||LA9_0=='-') ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // OCL.g:
                            {
                            if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                                input.consume();
                            state.failed=false;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return ;}
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;}


                            }
                            break;

                    }

                    mINT(); if (state.failed) return ;

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "REAL"

    // $ANTLR start "RANGE_OR_INT"
    public final void mRANGE_OR_INT() throws RecognitionException {
        try {
            int _type = RANGE_OR_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:722:13: ( ( INT '..' )=> INT | ( REAL )=> REAL | INT )
            int alt11=3;
            int LA11_0 = input.LA(1);

            if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                int LA11_1 = input.LA(2);

                if ( ((LA11_1>='0' && LA11_1<='9')) && (synpred2_OCL())) {
                    alt11=2;
                }
                else if ( (LA11_1=='.') && (synpred2_OCL())) {
                    alt11=2;
                }
                else if ( (LA11_1=='E'||LA11_1=='e') && (synpred2_OCL())) {
                    alt11=2;
                }
                else if ( (synpred1_OCL()) ) {
                    alt11=1;
                }
                else if ( (true) ) {
                    alt11=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // OCL.g:723:7: ( INT '..' )=> INT
                    {
                    mINT(); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       _type=INT; 
                    }

                    }
                    break;
                case 2 :
                    // OCL.g:724:7: ( REAL )=> REAL
                    {
                    mREAL(); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       _type=REAL; 
                    }

                    }
                    break;
                case 3 :
                    // OCL.g:725:9: INT
                    {
                    mINT(); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       _type=INT; 
                    }

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RANGE_OR_INT"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:730:7: ( '\\'' (~ ( '\\'' | '\\\\' ) | ESC )* '\\'' )
            // OCL.g:731:5: '\\'' (~ ( '\\'' | '\\\\' ) | ESC )* '\\''
            {
            match('\''); if (state.failed) return ;
            // OCL.g:731:10: (~ ( '\\'' | '\\\\' ) | ESC )*
            loop12:
            do {
                int alt12=3;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0>='\u0000' && LA12_0<='&')||(LA12_0>='(' && LA12_0<='[')||(LA12_0>=']' && LA12_0<='\uFFFF')) ) {
                    alt12=1;
                }
                else if ( (LA12_0=='\\') ) {
                    alt12=2;
                }


                switch (alt12) {
            	case 1 :
            	    // OCL.g:731:12: ~ ( '\\'' | '\\\\' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();
            	    state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;
            	case 2 :
            	    // OCL.g:731:27: ESC
            	    {
            	    mESC(); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

            match('\''); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "ESC"
    public final void mESC() throws RecognitionException {
        try {
            // OCL.g:743:1: ( '\\\\' ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT | '0' .. '3' ( '0' .. '7' ( '0' .. '7' )? )? | '4' .. '7' ( '0' .. '7' )? ) )
            // OCL.g:744:5: '\\\\' ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT | '0' .. '3' ( '0' .. '7' ( '0' .. '7' )? )? | '4' .. '7' ( '0' .. '7' )? )
            {
            match('\\'); if (state.failed) return ;
            // OCL.g:745:6: ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT | '0' .. '3' ( '0' .. '7' ( '0' .. '7' )? )? | '4' .. '7' ( '0' .. '7' )? )
            int alt16=11;
            switch ( input.LA(1) ) {
            case 'n':
                {
                alt16=1;
                }
                break;
            case 'r':
                {
                alt16=2;
                }
                break;
            case 't':
                {
                alt16=3;
                }
                break;
            case 'b':
                {
                alt16=4;
                }
                break;
            case 'f':
                {
                alt16=5;
                }
                break;
            case '\"':
                {
                alt16=6;
                }
                break;
            case '\'':
                {
                alt16=7;
                }
                break;
            case '\\':
                {
                alt16=8;
                }
                break;
            case 'u':
                {
                alt16=9;
                }
                break;
            case '0':
            case '1':
            case '2':
            case '3':
                {
                alt16=10;
                }
                break;
            case '4':
            case '5':
            case '6':
            case '7':
                {
                alt16=11;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }

            switch (alt16) {
                case 1 :
                    // OCL.g:745:8: 'n'
                    {
                    match('n'); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // OCL.g:746:8: 'r'
                    {
                    match('r'); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // OCL.g:747:8: 't'
                    {
                    match('t'); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // OCL.g:748:8: 'b'
                    {
                    match('b'); if (state.failed) return ;

                    }
                    break;
                case 5 :
                    // OCL.g:749:8: 'f'
                    {
                    match('f'); if (state.failed) return ;

                    }
                    break;
                case 6 :
                    // OCL.g:750:8: '\"'
                    {
                    match('\"'); if (state.failed) return ;

                    }
                    break;
                case 7 :
                    // OCL.g:751:8: '\\''
                    {
                    match('\''); if (state.failed) return ;

                    }
                    break;
                case 8 :
                    // OCL.g:752:8: '\\\\'
                    {
                    match('\\'); if (state.failed) return ;

                    }
                    break;
                case 9 :
                    // OCL.g:753:8: 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
                    {
                    match('u'); if (state.failed) return ;
                    mHEX_DIGIT(); if (state.failed) return ;
                    mHEX_DIGIT(); if (state.failed) return ;
                    mHEX_DIGIT(); if (state.failed) return ;
                    mHEX_DIGIT(); if (state.failed) return ;

                    }
                    break;
                case 10 :
                    // OCL.g:754:8: '0' .. '3' ( '0' .. '7' ( '0' .. '7' )? )?
                    {
                    matchRange('0','3'); if (state.failed) return ;
                    // OCL.g:754:17: ( '0' .. '7' ( '0' .. '7' )? )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( ((LA14_0>='0' && LA14_0<='7')) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // OCL.g:754:18: '0' .. '7' ( '0' .. '7' )?
                            {
                            matchRange('0','7'); if (state.failed) return ;
                            // OCL.g:754:27: ( '0' .. '7' )?
                            int alt13=2;
                            int LA13_0 = input.LA(1);

                            if ( ((LA13_0>='0' && LA13_0<='7')) ) {
                                alt13=1;
                            }
                            switch (alt13) {
                                case 1 :
                                    // OCL.g:754:28: '0' .. '7'
                                    {
                                    matchRange('0','7'); if (state.failed) return ;

                                    }
                                    break;

                            }


                            }
                            break;

                    }


                    }
                    break;
                case 11 :
                    // OCL.g:754:45: '4' .. '7' ( '0' .. '7' )?
                    {
                    matchRange('4','7'); if (state.failed) return ;
                    // OCL.g:754:54: ( '0' .. '7' )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( ((LA15_0>='0' && LA15_0<='7')) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // OCL.g:754:55: '0' .. '7'
                            {
                            matchRange('0','7'); if (state.failed) return ;

                            }
                            break;

                    }


                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "ESC"

    // $ANTLR start "HEX_DIGIT"
    public final void mHEX_DIGIT() throws RecognitionException {
        try {
            // OCL.g:760:10: ( ( '0' .. '9' | 'A' .. 'F' | 'a' .. 'f' ) )
            // OCL.g:761:5: ( '0' .. '9' | 'A' .. 'F' | 'a' .. 'f' )
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "HEX_DIGIT"

    // $ANTLR start "IDENT"
    public final void mIDENT() throws RecognitionException {
        try {
            int _type = IDENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // OCL.g:768:6: ( ( '$' | 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // OCL.g:769:5: ( '$' | 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            if ( input.LA(1)=='$'||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // OCL.g:769:39: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0>='0' && LA17_0<='9')||(LA17_0>='A' && LA17_0<='Z')||LA17_0=='_'||(LA17_0>='a' && LA17_0<='z')) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // OCL.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();
            	    state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IDENT"

    // $ANTLR start "VOCAB"
    public final void mVOCAB() throws RecognitionException {
        try {
            // OCL.g:776:6: ( '\\U0003' .. '\\U0377' )
            // OCL.g:777:5: '\\U0003' .. '\\U0377'
            {
            matchRange('\u0003','\u0377'); if (state.failed) return ;

            }

        }
        finally {
        }
    }
    // $ANTLR end "VOCAB"

    public void mTokens() throws RecognitionException {
        // OCL.g:1:8: ( T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | WS | SL_COMMENT | ML_COMMENT | ARROW | AT | BAR | COLON | COLON_COLON | COLON_EQUAL | COMMA | DOT | DOTDOT | EQUAL | GREATER | GREATER_EQUAL | HASH | LBRACE | LBRACK | LESS | LESS_EQUAL | LPAREN | MINUS | NOT_EQUAL | PLUS | RBRACE | RBRACK | RPAREN | SEMI | SLASH | STAR | RANGE_OR_INT | STRING | IDENT )
        int alt18=63;
        alt18 = dfa18.predict(input);
        switch (alt18) {
            case 1 :
                // OCL.g:1:10: T__43
                {
                mT__43(); if (state.failed) return ;

                }
                break;
            case 2 :
                // OCL.g:1:16: T__44
                {
                mT__44(); if (state.failed) return ;

                }
                break;
            case 3 :
                // OCL.g:1:22: T__45
                {
                mT__45(); if (state.failed) return ;

                }
                break;
            case 4 :
                // OCL.g:1:28: T__46
                {
                mT__46(); if (state.failed) return ;

                }
                break;
            case 5 :
                // OCL.g:1:34: T__47
                {
                mT__47(); if (state.failed) return ;

                }
                break;
            case 6 :
                // OCL.g:1:40: T__48
                {
                mT__48(); if (state.failed) return ;

                }
                break;
            case 7 :
                // OCL.g:1:46: T__49
                {
                mT__49(); if (state.failed) return ;

                }
                break;
            case 8 :
                // OCL.g:1:52: T__50
                {
                mT__50(); if (state.failed) return ;

                }
                break;
            case 9 :
                // OCL.g:1:58: T__51
                {
                mT__51(); if (state.failed) return ;

                }
                break;
            case 10 :
                // OCL.g:1:64: T__52
                {
                mT__52(); if (state.failed) return ;

                }
                break;
            case 11 :
                // OCL.g:1:70: T__53
                {
                mT__53(); if (state.failed) return ;

                }
                break;
            case 12 :
                // OCL.g:1:76: T__54
                {
                mT__54(); if (state.failed) return ;

                }
                break;
            case 13 :
                // OCL.g:1:82: T__55
                {
                mT__55(); if (state.failed) return ;

                }
                break;
            case 14 :
                // OCL.g:1:88: T__56
                {
                mT__56(); if (state.failed) return ;

                }
                break;
            case 15 :
                // OCL.g:1:94: T__57
                {
                mT__57(); if (state.failed) return ;

                }
                break;
            case 16 :
                // OCL.g:1:100: T__58
                {
                mT__58(); if (state.failed) return ;

                }
                break;
            case 17 :
                // OCL.g:1:106: T__59
                {
                mT__59(); if (state.failed) return ;

                }
                break;
            case 18 :
                // OCL.g:1:112: T__60
                {
                mT__60(); if (state.failed) return ;

                }
                break;
            case 19 :
                // OCL.g:1:118: T__61
                {
                mT__61(); if (state.failed) return ;

                }
                break;
            case 20 :
                // OCL.g:1:124: T__62
                {
                mT__62(); if (state.failed) return ;

                }
                break;
            case 21 :
                // OCL.g:1:130: T__63
                {
                mT__63(); if (state.failed) return ;

                }
                break;
            case 22 :
                // OCL.g:1:136: T__64
                {
                mT__64(); if (state.failed) return ;

                }
                break;
            case 23 :
                // OCL.g:1:142: T__65
                {
                mT__65(); if (state.failed) return ;

                }
                break;
            case 24 :
                // OCL.g:1:148: T__66
                {
                mT__66(); if (state.failed) return ;

                }
                break;
            case 25 :
                // OCL.g:1:154: T__67
                {
                mT__67(); if (state.failed) return ;

                }
                break;
            case 26 :
                // OCL.g:1:160: T__68
                {
                mT__68(); if (state.failed) return ;

                }
                break;
            case 27 :
                // OCL.g:1:166: T__69
                {
                mT__69(); if (state.failed) return ;

                }
                break;
            case 28 :
                // OCL.g:1:172: T__70
                {
                mT__70(); if (state.failed) return ;

                }
                break;
            case 29 :
                // OCL.g:1:178: T__71
                {
                mT__71(); if (state.failed) return ;

                }
                break;
            case 30 :
                // OCL.g:1:184: T__72
                {
                mT__72(); if (state.failed) return ;

                }
                break;
            case 31 :
                // OCL.g:1:190: WS
                {
                mWS(); if (state.failed) return ;

                }
                break;
            case 32 :
                // OCL.g:1:193: SL_COMMENT
                {
                mSL_COMMENT(); if (state.failed) return ;

                }
                break;
            case 33 :
                // OCL.g:1:204: ML_COMMENT
                {
                mML_COMMENT(); if (state.failed) return ;

                }
                break;
            case 34 :
                // OCL.g:1:215: ARROW
                {
                mARROW(); if (state.failed) return ;

                }
                break;
            case 35 :
                // OCL.g:1:221: AT
                {
                mAT(); if (state.failed) return ;

                }
                break;
            case 36 :
                // OCL.g:1:224: BAR
                {
                mBAR(); if (state.failed) return ;

                }
                break;
            case 37 :
                // OCL.g:1:228: COLON
                {
                mCOLON(); if (state.failed) return ;

                }
                break;
            case 38 :
                // OCL.g:1:234: COLON_COLON
                {
                mCOLON_COLON(); if (state.failed) return ;

                }
                break;
            case 39 :
                // OCL.g:1:246: COLON_EQUAL
                {
                mCOLON_EQUAL(); if (state.failed) return ;

                }
                break;
            case 40 :
                // OCL.g:1:258: COMMA
                {
                mCOMMA(); if (state.failed) return ;

                }
                break;
            case 41 :
                // OCL.g:1:264: DOT
                {
                mDOT(); if (state.failed) return ;

                }
                break;
            case 42 :
                // OCL.g:1:268: DOTDOT
                {
                mDOTDOT(); if (state.failed) return ;

                }
                break;
            case 43 :
                // OCL.g:1:275: EQUAL
                {
                mEQUAL(); if (state.failed) return ;

                }
                break;
            case 44 :
                // OCL.g:1:281: GREATER
                {
                mGREATER(); if (state.failed) return ;

                }
                break;
            case 45 :
                // OCL.g:1:289: GREATER_EQUAL
                {
                mGREATER_EQUAL(); if (state.failed) return ;

                }
                break;
            case 46 :
                // OCL.g:1:303: HASH
                {
                mHASH(); if (state.failed) return ;

                }
                break;
            case 47 :
                // OCL.g:1:308: LBRACE
                {
                mLBRACE(); if (state.failed) return ;

                }
                break;
            case 48 :
                // OCL.g:1:315: LBRACK
                {
                mLBRACK(); if (state.failed) return ;

                }
                break;
            case 49 :
                // OCL.g:1:322: LESS
                {
                mLESS(); if (state.failed) return ;

                }
                break;
            case 50 :
                // OCL.g:1:327: LESS_EQUAL
                {
                mLESS_EQUAL(); if (state.failed) return ;

                }
                break;
            case 51 :
                // OCL.g:1:338: LPAREN
                {
                mLPAREN(); if (state.failed) return ;

                }
                break;
            case 52 :
                // OCL.g:1:345: MINUS
                {
                mMINUS(); if (state.failed) return ;

                }
                break;
            case 53 :
                // OCL.g:1:351: NOT_EQUAL
                {
                mNOT_EQUAL(); if (state.failed) return ;

                }
                break;
            case 54 :
                // OCL.g:1:361: PLUS
                {
                mPLUS(); if (state.failed) return ;

                }
                break;
            case 55 :
                // OCL.g:1:366: RBRACE
                {
                mRBRACE(); if (state.failed) return ;

                }
                break;
            case 56 :
                // OCL.g:1:373: RBRACK
                {
                mRBRACK(); if (state.failed) return ;

                }
                break;
            case 57 :
                // OCL.g:1:380: RPAREN
                {
                mRPAREN(); if (state.failed) return ;

                }
                break;
            case 58 :
                // OCL.g:1:387: SEMI
                {
                mSEMI(); if (state.failed) return ;

                }
                break;
            case 59 :
                // OCL.g:1:392: SLASH
                {
                mSLASH(); if (state.failed) return ;

                }
                break;
            case 60 :
                // OCL.g:1:398: STAR
                {
                mSTAR(); if (state.failed) return ;

                }
                break;
            case 61 :
                // OCL.g:1:403: RANGE_OR_INT
                {
                mRANGE_OR_INT(); if (state.failed) return ;

                }
                break;
            case 62 :
                // OCL.g:1:416: STRING
                {
                mSTRING(); if (state.failed) return ;

                }
                break;
            case 63 :
                // OCL.g:1:423: IDENT
                {
                mIDENT(); if (state.failed) return ;

                }
                break;

        }

    }

    // $ANTLR start synpred1_OCL
    public final void synpred1_OCL_fragment() throws RecognitionException {   
        // OCL.g:723:7: ( INT '..' )
        // OCL.g:723:9: INT '..'
        {
        mINT(); if (state.failed) return ;
        match(".."); if (state.failed) return ;


        }
    }
    // $ANTLR end synpred1_OCL

    // $ANTLR start synpred2_OCL
    public final void synpred2_OCL_fragment() throws RecognitionException {   
        // OCL.g:724:7: ( REAL )
        // OCL.g:724:9: REAL
        {
        mREAL(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_OCL

    public final boolean synpred2_OCL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_OCL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred1_OCL() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_OCL_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA18 dfa18 = new DFA18(this);
    static final String DFA18_eotS =
        "\1\uffff\21\51\1\uffff\1\105\1\107\2\uffff\1\112\1\uffff\1\114"+
        "\1\uffff\1\116\3\uffff\1\121\12\uffff\1\51\1\123\2\51\1\126\1\127"+
        "\23\51\17\uffff\1\154\1\uffff\2\51\2\uffff\1\51\1\163\1\164\1\51"+
        "\1\166\1\167\1\51\1\171\5\51\1\177\1\51\1\u0081\4\51\1\uffff\6\51"+
        "\2\uffff\1\51\2\uffff\1\u008d\1\uffff\1\u008e\1\u008f\1\u0090\2"+
        "\51\1\uffff\1\51\1\uffff\13\51\4\uffff\1\u00a0\1\u00a1\3\51\1\u00a5"+
        "\11\51\2\uffff\3\51\1\uffff\1\51\1\u00b3\1\u00b4\12\51\2\uffff\3"+
        "\51\1\u00c2\2\51\1\u00c5\3\51\1\u00c9\2\51\1\uffff\2\51\1\uffff"+
        "\1\51\1\u00cf\1\51\1\uffff\4\51\1\u00d5\1\uffff\1\u00d6\1\u00d7"+
        "\1\u00d8\2\51\4\uffff\1\u00db\1\u00dc\2\uffff";
    static final String DFA18_eofS =
        "\u00dd\uffff";
    static final String DFA18_minS =
        "\1\11\1\145\1\146\1\143\1\157\1\154\1\151\1\157\1\162\1\150\1\154"+
        "\1\141\1\145\1\141\1\162\1\156\1\165\1\157\1\uffff\1\52\1\55\2\uffff"+
        "\1\72\1\uffff\1\56\1\uffff\1\75\3\uffff\1\75\12\uffff\1\164\1\60"+
        "\1\160\1\145\2\60\1\154\1\162\1\144\1\154\1\166\1\164\1\154\2\145"+
        "\1\165\1\163\1\144\1\154\1\161\1\147\2\144\1\160\1\154\17\uffff"+
        "\1\60\1\uffff\1\154\1\162\2\uffff\1\101\2\60\1\111\2\60\1\154\1"+
        "\60\1\156\2\145\1\151\1\163\1\60\1\165\1\60\2\145\2\154\1\uffff"+
        "\1\151\1\141\2\163\1\155\1\156\2\uffff\1\156\2\uffff\1\60\1\uffff"+
        "\3\60\1\146\1\145\1\uffff\1\145\1\uffff\1\162\1\146\3\145\1\164"+
        "\1\124\1\113\1\160\1\144\1\163\4\uffff\2\60\1\156\1\145\1\151\1"+
        "\60\1\143\1\163\1\145\1\171\1\151\1\171\1\164\1\145\1\164\2\uffff"+
        "\1\143\1\144\1\156\1\uffff\1\164\2\60\1\160\1\156\1\160\1\171\1"+
        "\146\1\141\1\145\1\123\1\145\1\151\2\uffff\1\145\1\144\1\145\1\60"+
        "\1\151\1\156\1\60\1\145\1\144\1\157\1\60\2\117\1\uffff\1\156\1\143"+
        "\1\uffff\1\164\1\60\1\156\1\uffff\2\146\2\145\1\60\1\uffff\3\60"+
        "\1\144\1\163\4\uffff\2\60\2\uffff";
    static final String DFA18_maxS =
        "\1\175\1\145\1\164\1\162\1\157\1\156\1\151\1\165\2\162\1\156\1"+
        "\141\1\145\1\141\1\162\1\156\1\165\1\157\1\uffff\1\57\1\76\2\uffff"+
        "\1\75\1\uffff\1\56\1\uffff\1\75\3\uffff\1\76\12\uffff\1\164\1\172"+
        "\1\160\1\145\2\172\1\154\1\162\1\144\1\154\1\166\1\164\1\154\2\145"+
        "\1\165\1\163\1\144\1\154\1\164\1\147\2\144\1\160\1\154\17\uffff"+
        "\1\172\1\uffff\1\154\1\162\2\uffff\1\125\2\172\1\111\2\172\1\154"+
        "\1\172\1\156\2\145\1\151\1\163\1\172\1\165\1\172\2\145\2\154\1\uffff"+
        "\1\151\1\141\2\163\1\155\1\156\2\uffff\1\156\2\uffff\1\172\1\uffff"+
        "\3\172\1\146\1\145\1\uffff\1\145\1\uffff\1\162\1\146\3\145\1\164"+
        "\2\124\1\160\1\144\1\163\4\uffff\2\172\1\156\1\145\1\151\1\172\1"+
        "\143\1\163\1\145\1\171\1\151\1\171\1\164\1\145\1\164\2\uffff\1\143"+
        "\1\144\1\156\1\uffff\1\164\2\172\1\160\1\156\1\160\1\171\1\146\1"+
        "\141\1\145\1\123\1\145\1\151\2\uffff\1\145\1\144\1\145\1\172\1\151"+
        "\1\156\1\172\1\145\1\144\1\157\1\172\2\117\1\uffff\1\156\1\143\1"+
        "\uffff\1\164\1\172\1\156\1\uffff\2\146\2\145\1\172\1\uffff\3\172"+
        "\1\144\1\163\4\uffff\2\172\2\uffff";
    static final String DFA18_acceptS =
        "\22\uffff\1\37\2\uffff\1\43\1\44\1\uffff\1\50\1\uffff\1\53\1\uffff"+
        "\1\56\1\57\1\60\1\uffff\1\63\1\66\1\67\1\70\1\71\1\72\1\74\1\75"+
        "\1\76\1\77\31\uffff\1\40\1\41\1\73\1\42\1\64\1\46\1\47\1\45\1\52"+
        "\1\51\1\55\1\54\1\62\1\65\1\61\1\uffff\1\2\2\uffff\1\17\1\4\24\uffff"+
        "\1\1\6\uffff\1\5\1\6\1\uffff\1\7\1\10\1\uffff\1\12\5\uffff\1\25"+
        "\1\uffff\1\27\13\uffff\1\34\1\20\1\23\1\21\17\uffff\1\22\1\24\3"+
        "\uffff\1\35\15\uffff\1\3\1\13\15\uffff\1\31\2\uffff\1\26\3\uffff"+
        "\1\14\5\uffff\1\33\5\uffff\1\30\1\36\1\15\1\16\2\uffff\1\32\1\11";
    static final String DFA18_specialS =
        "\u00dd\uffff}>";
    static final String[] DFA18_transitionS = {
            "\2\22\1\uffff\2\22\22\uffff\1\22\2\uffff\1\34\1\51\2\uffff"+
            "\1\50\1\40\1\44\1\46\1\41\1\30\1\24\1\31\1\23\12\47\1\27\1\45"+
            "\1\37\1\32\1\33\1\uffff\1\25\1\51\1\15\1\21\13\51\1\16\3\51"+
            "\1\14\1\20\1\17\5\51\1\36\1\uffff\1\43\1\uffff\1\51\1\uffff"+
            "\1\5\2\51\1\6\1\12\1\13\2\51\1\2\2\51\1\1\1\51\1\7\1\3\1\10"+
            "\3\51\1\11\3\51\1\4\2\51\1\35\1\26\1\42",
            "\1\52",
            "\1\56\6\uffff\1\54\1\53\5\uffff\1\55",
            "\1\60\16\uffff\1\57",
            "\1\61",
            "\1\63\1\uffff\1\62",
            "\1\64",
            "\1\65\5\uffff\1\66",
            "\1\67",
            "\1\70\11\uffff\1\71",
            "\1\72\1\uffff\1\73",
            "\1\74",
            "\1\75",
            "\1\76",
            "\1\77",
            "\1\100",
            "\1\101",
            "\1\102",
            "",
            "\1\104\4\uffff\1\103",
            "\1\103\20\uffff\1\106",
            "",
            "",
            "\1\110\2\uffff\1\111",
            "",
            "\1\113",
            "",
            "\1\115",
            "",
            "",
            "",
            "\1\117\1\120",
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
            "\1\122",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\1\124",
            "\1\125",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\1\130",
            "\1\131",
            "\1\132",
            "\1\133",
            "\1\134",
            "\1\135",
            "\1\136",
            "\1\137",
            "\1\140",
            "\1\141",
            "\1\142",
            "\1\143",
            "\1\144",
            "\1\146\2\uffff\1\145",
            "\1\147",
            "\1\150",
            "\1\151",
            "\1\152",
            "\1\153",
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
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "",
            "\1\155",
            "\1\156",
            "",
            "",
            "\1\157\3\uffff\1\161\3\uffff\1\160\13\uffff\1\162",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\1\165",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\1\170",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\1\172",
            "\1\173",
            "\1\174",
            "\1\175",
            "\1\176",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\1\u0080",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\1\u0082",
            "\1\u0083",
            "\1\u0084",
            "\1\u0085",
            "",
            "\1\u0086",
            "\1\u0087",
            "\1\u0088",
            "\1\u0089",
            "\1\u008a",
            "\1\u008b",
            "",
            "",
            "\1\u008c",
            "",
            "",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\1\u0091",
            "\1\u0092",
            "",
            "\1\u0093",
            "",
            "\1\u0094",
            "\1\u0095",
            "\1\u0096",
            "\1\u0097",
            "\1\u0098",
            "\1\u0099",
            "\1\u009a",
            "\1\u009b\10\uffff\1\u009c",
            "\1\u009d",
            "\1\u009e",
            "\1\u009f",
            "",
            "",
            "",
            "",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\1\u00a2",
            "\1\u00a3",
            "\1\u00a4",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\1\u00a6",
            "\1\u00a7",
            "\1\u00a8",
            "\1\u00a9",
            "\1\u00aa",
            "\1\u00ab",
            "\1\u00ac",
            "\1\u00ad",
            "\1\u00ae",
            "",
            "",
            "\1\u00af",
            "\1\u00b0",
            "\1\u00b1",
            "",
            "\1\u00b2",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\1\u00b5",
            "\1\u00b6",
            "\1\u00b7",
            "\1\u00b8",
            "\1\u00b9",
            "\1\u00ba",
            "\1\u00bb",
            "\1\u00bc",
            "\1\u00bd",
            "\1\u00be",
            "",
            "",
            "\1\u00bf",
            "\1\u00c0",
            "\1\u00c1",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\1\u00c3",
            "\1\u00c4",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\1\u00c6",
            "\1\u00c7",
            "\1\u00c8",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\1\u00ca",
            "\1\u00cb",
            "",
            "\1\u00cc",
            "\1\u00cd",
            "",
            "\1\u00ce",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\1\u00d0",
            "",
            "\1\u00d1",
            "\1\u00d2",
            "\1\u00d3",
            "\1\u00d4",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\1\u00d9",
            "\1\u00da",
            "",
            "",
            "",
            "",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "\12\51\7\uffff\32\51\4\uffff\1\51\1\uffff\32\51",
            "",
            ""
    };

    static final short[] DFA18_eot = DFA.unpackEncodedString(DFA18_eotS);
    static final short[] DFA18_eof = DFA.unpackEncodedString(DFA18_eofS);
    static final char[] DFA18_min = DFA.unpackEncodedStringToUnsignedChars(DFA18_minS);
    static final char[] DFA18_max = DFA.unpackEncodedStringToUnsignedChars(DFA18_maxS);
    static final short[] DFA18_accept = DFA.unpackEncodedString(DFA18_acceptS);
    static final short[] DFA18_special = DFA.unpackEncodedString(DFA18_specialS);
    static final short[][] DFA18_transition;

    static {
        int numStates = DFA18_transitionS.length;
        DFA18_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA18_transition[i] = DFA.unpackEncodedString(DFA18_transitionS[i]);
        }
    }

    class DFA18 extends DFA {

        public DFA18(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 18;
            this.eot = DFA18_eot;
            this.eof = DFA18_eof;
            this.min = DFA18_min;
            this.max = DFA18_max;
            this.accept = DFA18_accept;
            this.special = DFA18_special;
            this.transition = DFA18_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | WS | SL_COMMENT | ML_COMMENT | ARROW | AT | BAR | COLON | COLON_COLON | COLON_EQUAL | COMMA | DOT | DOTDOT | EQUAL | GREATER | GREATER_EQUAL | HASH | LBRACE | LBRACK | LESS | LESS_EQUAL | LPAREN | MINUS | NOT_EQUAL | PLUS | RBRACE | RBRACK | RPAREN | SEMI | SLASH | STAR | RANGE_OR_INT | STRING | IDENT );";
        }
    }
 

}