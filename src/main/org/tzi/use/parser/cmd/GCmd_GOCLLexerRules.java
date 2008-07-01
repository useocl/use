// $ANTLR 3.1b1 GOCLLexerRules.g 2008-07-01 13:26:39

package org.tzi.use.parser.cmd; 

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
public class GCmd_GOCLLexerRules extends Lexer {
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
    
        private PrintWriter fErr;
        private ParseErrorHandler fParseErrorHandler;
    
        Stack paraphrase = new Stack();
        
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
    public GCmdLexer gGCmd;

    public GCmd_GOCLLexerRules() {;} 
    public GCmd_GOCLLexerRules(CharStream input, GCmdLexer gGCmd) {
        this(input, new RecognizerSharedState(), gGCmd);
    }
    public GCmd_GOCLLexerRules(CharStream input, RecognizerSharedState state, GCmdLexer gGCmd) {
        super(input,state);

        this.gGCmd = gGCmd;
    }
    public String getGrammarFileName() { return "GOCLLexerRules.g"; }

    // $ANTLR start WS
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GOCLLexerRules.g:50:3: ( ( ' ' | '\\t' | '\\f' | NEWLINE ) )
            // GOCLLexerRules.g:51:5: ( ' ' | '\\t' | '\\f' | NEWLINE )
            {
            // GOCLLexerRules.g:51:5: ( ' ' | '\\t' | '\\f' | NEWLINE )
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
                    // GOCLLexerRules.g:51:7: ' '
                    {
                    match(' '); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // GOCLLexerRules.g:52:7: '\\t'
                    {
                    match('\t'); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // GOCLLexerRules.g:53:7: '\\f'
                    {
                    match('\f'); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // GOCLLexerRules.g:54:7: NEWLINE
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
    // $ANTLR end WS

    // $ANTLR start SL_COMMENT
    public final void mSL_COMMENT() throws RecognitionException {
        try {
            int _type = SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GOCLLexerRules.g:60:11: ( ( '//' | '--' ) (~ ( '\\n' | '\\r' ) )* NEWLINE )
            // GOCLLexerRules.g:61:5: ( '//' | '--' ) (~ ( '\\n' | '\\r' ) )* NEWLINE
            {
            // GOCLLexerRules.g:61:5: ( '//' | '--' )
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
                    // GOCLLexerRules.g:61:6: '//'
                    {
                    match("//"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // GOCLLexerRules.g:61:13: '--'
                    {
                    match("--"); if (state.failed) return ;


                    }
                    break;

            }

            // GOCLLexerRules.g:62:5: (~ ( '\\n' | '\\r' ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='\u0000' && LA3_0<='\t')||(LA3_0>='\u000B' && LA3_0<='\f')||(LA3_0>='\u000E' && LA3_0<='\uFFFE')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // GOCLLexerRules.g:62:6: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFE') ) {
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
    // $ANTLR end SL_COMMENT

    // $ANTLR start ML_COMMENT
    public final void mML_COMMENT() throws RecognitionException {
        try {
            int _type = ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GOCLLexerRules.g:67:11: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // GOCLLexerRules.g:68:5: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); if (state.failed) return ;

            // GOCLLexerRules.g:68:10: ( options {greedy=false; } : . )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0=='*') ) {
                    int LA4_1 = input.LA(2);

                    if ( (LA4_1=='/') ) {
                        alt4=2;
                    }
                    else if ( ((LA4_1>='\u0000' && LA4_1<='.')||(LA4_1>='0' && LA4_1<='\uFFFE')) ) {
                        alt4=1;
                    }


                }
                else if ( ((LA4_0>='\u0000' && LA4_0<=')')||(LA4_0>='+' && LA4_0<='\uFFFE')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // GOCLLexerRules.g:68:38: .
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
    // $ANTLR end ML_COMMENT

    // $ANTLR start NEWLINE
    public final void mNEWLINE() throws RecognitionException {
        try {
            // GOCLLexerRules.g:71:9: ( '\\r\\n' | '\\r' | '\\n' )
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
                    // GOCLLexerRules.g:72:5: '\\r\\n'
                    {
                    match("\r\n"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // GOCLLexerRules.g:72:14: '\\r'
                    {
                    match('\r'); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // GOCLLexerRules.g:72:21: '\\n'
                    {
                    match('\n'); if (state.failed) return ;

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end NEWLINE

    // $ANTLR start ARROW
    public final void mARROW() throws RecognitionException {
        try {
            int _type = ARROW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push("->"); 
            // GOCLLexerRules.g:76:76: ( '->' )
            // GOCLLexerRules.g:76:78: '->'
            {
            match("->"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end ARROW

    // $ANTLR start AT
    public final void mAT() throws RecognitionException {
        try {
            int _type = AT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push("@");  
            // GOCLLexerRules.g:77:77: ( '@' )
            // GOCLLexerRules.g:77:79: '@'
            {
            match('@'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end AT

    // $ANTLR start BAR
    public final void mBAR() throws RecognitionException {
        try {
            int _type = BAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push("|");  
            // GOCLLexerRules.g:78:74: ( '|' )
            // GOCLLexerRules.g:78:76: '|'
            {
            match('|'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end BAR

    // $ANTLR start COLON
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push(":");  
            // GOCLLexerRules.g:79:76: ( ':' )
            // GOCLLexerRules.g:79:78: ':'
            {
            match(':'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end COLON

    // $ANTLR start COLON_COLON
    public final void mCOLON_COLON() throws RecognitionException {
        try {
            int _type = COLON_COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push("::"); 
            // GOCLLexerRules.g:80:80: ( '::' )
            // GOCLLexerRules.g:80:82: '::'
            {
            match("::"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end COLON_COLON

    // $ANTLR start COLON_EQUAL
    public final void mCOLON_EQUAL() throws RecognitionException {
        try {
            int _type = COLON_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push(":="); 
            // GOCLLexerRules.g:81:80: ( ':=' )
            // GOCLLexerRules.g:81:82: ':='
            {
            match(":="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end COLON_EQUAL

    // $ANTLR start COMMA
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push(",");  
            // GOCLLexerRules.g:82:76: ( ',' )
            // GOCLLexerRules.g:82:78: ','
            {
            match(','); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end COMMA

    // $ANTLR start DOT
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push(".");  
            // GOCLLexerRules.g:83:74: ( '.' )
            // GOCLLexerRules.g:83:76: '.'
            {
            match('.'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end DOT

    // $ANTLR start DOTDOT
    public final void mDOTDOT() throws RecognitionException {
        try {
            int _type = DOTDOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push(".."); 
            // GOCLLexerRules.g:84:77: ( '..' )
            // GOCLLexerRules.g:84:79: '..'
            {
            match(".."); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end DOTDOT

    // $ANTLR start EQUAL
    public final void mEQUAL() throws RecognitionException {
        try {
            int _type = EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push("=");  
            // GOCLLexerRules.g:85:76: ( '=' )
            // GOCLLexerRules.g:85:78: '='
            {
            match('='); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end EQUAL

    // $ANTLR start GREATER
    public final void mGREATER() throws RecognitionException {
        try {
            int _type = GREATER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push(">");  
            // GOCLLexerRules.g:86:77: ( '>' )
            // GOCLLexerRules.g:86:79: '>'
            {
            match('>'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end GREATER

    // $ANTLR start GREATER_EQUAL
    public final void mGREATER_EQUAL() throws RecognitionException {
        try {
            int _type = GREATER_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push(">="); 
            // GOCLLexerRules.g:87:83: ( '>=' )
            // GOCLLexerRules.g:87:85: '>='
            {
            match(">="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end GREATER_EQUAL

    // $ANTLR start HASH
    public final void mHASH() throws RecognitionException {
        try {
            int _type = HASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push("#");  
            // GOCLLexerRules.g:88:75: ( '#' )
            // GOCLLexerRules.g:88:77: '#'
            {
            match('#'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end HASH

    // $ANTLR start LBRACE
    public final void mLBRACE() throws RecognitionException {
        try {
            int _type = LBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push("{");  
            // GOCLLexerRules.g:89:77: ( '{' )
            // GOCLLexerRules.g:89:79: '{'
            {
            match('{'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end LBRACE

    // $ANTLR start LBRACK
    public final void mLBRACK() throws RecognitionException {
        try {
            int _type = LBRACK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push("[");  
            // GOCLLexerRules.g:90:77: ( '[' )
            // GOCLLexerRules.g:90:79: '['
            {
            match('['); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end LBRACK

    // $ANTLR start LESS
    public final void mLESS() throws RecognitionException {
        try {
            int _type = LESS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push("<");  
            // GOCLLexerRules.g:91:75: ( '<' )
            // GOCLLexerRules.g:91:77: '<'
            {
            match('<'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end LESS

    // $ANTLR start LESS_EQUAL
    public final void mLESS_EQUAL() throws RecognitionException {
        try {
            int _type = LESS_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push("<="); 
            // GOCLLexerRules.g:92:80: ( '<=' )
            // GOCLLexerRules.g:92:82: '<='
            {
            match("<="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end LESS_EQUAL

    // $ANTLR start LPAREN
    public final void mLPAREN() throws RecognitionException {
        try {
            int _type = LPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push("(");  
            // GOCLLexerRules.g:93:77: ( '(' )
            // GOCLLexerRules.g:93:79: '('
            {
            match('('); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end LPAREN

    // $ANTLR start MINUS
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push("-");  
            // GOCLLexerRules.g:94:76: ( '-' )
            // GOCLLexerRules.g:94:78: '-'
            {
            match('-'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end MINUS

    // $ANTLR start NOT_EQUAL
    public final void mNOT_EQUAL() throws RecognitionException {
        try {
            int _type = NOT_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push("<>"); 
            // GOCLLexerRules.g:95:79: ( '<>' )
            // GOCLLexerRules.g:95:81: '<>'
            {
            match("<>"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end NOT_EQUAL

    // $ANTLR start PLUS
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push("+");  
            // GOCLLexerRules.g:96:75: ( '+' )
            // GOCLLexerRules.g:96:77: '+'
            {
            match('+'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end PLUS

    // $ANTLR start RBRACE
    public final void mRBRACE() throws RecognitionException {
        try {
            int _type = RBRACE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push("}");  
            // GOCLLexerRules.g:97:77: ( '}' )
            // GOCLLexerRules.g:97:79: '}'
            {
            match('}'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end RBRACE

    // $ANTLR start RBRACK
    public final void mRBRACK() throws RecognitionException {
        try {
            int _type = RBRACK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push("]");  
            // GOCLLexerRules.g:98:77: ( ']' )
            // GOCLLexerRules.g:98:79: ']'
            {
            match(']'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end RBRACK

    // $ANTLR start RPAREN
    public final void mRPAREN() throws RecognitionException {
        try {
            int _type = RPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push(")");  
            // GOCLLexerRules.g:99:76: ( ')' )
            // GOCLLexerRules.g:99:78: ')'
            {
            match(')'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end RPAREN

    // $ANTLR start SEMI
    public final void mSEMI() throws RecognitionException {
        try {
            int _type = SEMI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push(";");  
            // GOCLLexerRules.g:100:74: ( ';' )
            // GOCLLexerRules.g:100:76: ';'
            {
            match(';'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end SEMI

    // $ANTLR start SLASH
    public final void mSLASH() throws RecognitionException {
        try {
            int _type = SLASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push("/");  
            // GOCLLexerRules.g:101:76: ( '/' )
            // GOCLLexerRules.g:101:78: '/'
            {
            match('/'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end SLASH

    // $ANTLR start STAR
    public final void mSTAR() throws RecognitionException {
        try {
            int _type = STAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
             paraphrase.push("*");  
            // GOCLLexerRules.g:102:75: ( '*' )
            // GOCLLexerRules.g:102:77: '*'
            {
            match('*'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
            if ( state.backtracking==0 ) {
               paraphrase.pop(); 
            }    }
        finally {
        }
    }
    // $ANTLR end STAR

    // $ANTLR start INT
    public final void mINT() throws RecognitionException {
        try {
            // GOCLLexerRules.g:105:4: ( ( '0' .. '9' )+ )
            // GOCLLexerRules.g:106:5: ( '0' .. '9' )+
            {
            // GOCLLexerRules.g:106:5: ( '0' .. '9' )+
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
            	    // GOCLLexerRules.g:106:6: '0' .. '9'
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
    // $ANTLR end INT

    // $ANTLR start REAL
    public final void mREAL() throws RecognitionException {
        try {
            // GOCLLexerRules.g:110:5: ( INT ( '.' INT | ( ( 'e' | 'E' ) ( '+' | '-' )? INT )? ) )
            // GOCLLexerRules.g:111:5: INT ( '.' INT | ( ( 'e' | 'E' ) ( '+' | '-' )? INT )? )
            {
            mINT(); if (state.failed) return ;
            // GOCLLexerRules.g:111:9: ( '.' INT | ( ( 'e' | 'E' ) ( '+' | '-' )? INT )? )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='.') ) {
                alt9=1;
            }
            else {
                alt9=2;}
            switch (alt9) {
                case 1 :
                    // GOCLLexerRules.g:111:11: '.' INT
                    {
                    match('.'); if (state.failed) return ;
                    mINT(); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // GOCLLexerRules.g:111:21: ( ( 'e' | 'E' ) ( '+' | '-' )? INT )?
                    {
                    // GOCLLexerRules.g:111:21: ( ( 'e' | 'E' ) ( '+' | '-' )? INT )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='E'||LA8_0=='e') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // GOCLLexerRules.g:111:23: ( 'e' | 'E' ) ( '+' | '-' )? INT
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

                            // GOCLLexerRules.g:111:33: ( '+' | '-' )?
                            int alt7=2;
                            int LA7_0 = input.LA(1);

                            if ( (LA7_0=='+'||LA7_0=='-') ) {
                                alt7=1;
                            }
                            switch (alt7) {
                                case 1 :
                                    // GOCLLexerRules.g:
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

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end REAL

    // $ANTLR start RANGE_OR_INT
    public final void mRANGE_OR_INT() throws RecognitionException {
        try {
            int _type = RANGE_OR_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GOCLLexerRules.g:114:13: ( ( INT '..' )=> INT | ( INT '.' INT )=> REAL | ( INT ( 'e' | 'E' ) )=> REAL | INT )
            int alt10=4;
            int LA10_0 = input.LA(1);

            if ( ((LA10_0>='0' && LA10_0<='9')) ) {
                int LA10_1 = input.LA(2);

                if ( (synpred1_GOCLLexerRules()) ) {
                    alt10=1;
                }
                else if ( (synpred2_GOCLLexerRules()) ) {
                    alt10=2;
                }
                else if ( (synpred3_GOCLLexerRules()) ) {
                    alt10=3;
                }
                else if ( (true) ) {
                    alt10=4;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // GOCLLexerRules.g:115:7: ( INT '..' )=> INT
                    {
                    mINT(); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       _type=INT; 
                    }

                    }
                    break;
                case 2 :
                    // GOCLLexerRules.g:116:7: ( INT '.' INT )=> REAL
                    {
                    mREAL(); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       _type=REAL; 
                    }

                    }
                    break;
                case 3 :
                    // GOCLLexerRules.g:117:7: ( INT ( 'e' | 'E' ) )=> REAL
                    {
                    mREAL(); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       _type=REAL; 
                    }

                    }
                    break;
                case 4 :
                    // GOCLLexerRules.g:118:9: INT
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
    // $ANTLR end RANGE_OR_INT

    // $ANTLR start STRING
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GOCLLexerRules.g:123:7: ( '\\'' (~ ( '\\'' | '\\\\' ) | ESC )* '\\'' )
            // GOCLLexerRules.g:124:5: '\\'' (~ ( '\\'' | '\\\\' ) | ESC )* '\\''
            {
            match('\''); if (state.failed) return ;
            // GOCLLexerRules.g:124:10: (~ ( '\\'' | '\\\\' ) | ESC )*
            loop11:
            do {
                int alt11=3;
                int LA11_0 = input.LA(1);

                if ( ((LA11_0>='\u0000' && LA11_0<='&')||(LA11_0>='(' && LA11_0<='[')||(LA11_0>=']' && LA11_0<='\uFFFE')) ) {
                    alt11=1;
                }
                else if ( (LA11_0=='\\') ) {
                    alt11=2;
                }


                switch (alt11) {
            	case 1 :
            	    // GOCLLexerRules.g:124:12: ~ ( '\\'' | '\\\\' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFE') ) {
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
            	    // GOCLLexerRules.g:124:27: ESC
            	    {
            	    mESC(); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop11;
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
    // $ANTLR end STRING

    // $ANTLR start ESC
    public final void mESC() throws RecognitionException {
        try {
            // GOCLLexerRules.g:136:1: ( '\\\\' ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT | '0' .. '3' ( '0' .. '7' ( '0' .. '7' )? )? | '4' .. '7' ( '0' .. '7' )? ) )
            // GOCLLexerRules.g:137:5: '\\\\' ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT | '0' .. '3' ( '0' .. '7' ( '0' .. '7' )? )? | '4' .. '7' ( '0' .. '7' )? )
            {
            match('\\'); if (state.failed) return ;
            // GOCLLexerRules.g:138:6: ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT | '0' .. '3' ( '0' .. '7' ( '0' .. '7' )? )? | '4' .. '7' ( '0' .. '7' )? )
            int alt15=11;
            switch ( input.LA(1) ) {
            case 'n':
                {
                alt15=1;
                }
                break;
            case 'r':
                {
                alt15=2;
                }
                break;
            case 't':
                {
                alt15=3;
                }
                break;
            case 'b':
                {
                alt15=4;
                }
                break;
            case 'f':
                {
                alt15=5;
                }
                break;
            case '\"':
                {
                alt15=6;
                }
                break;
            case '\'':
                {
                alt15=7;
                }
                break;
            case '\\':
                {
                alt15=8;
                }
                break;
            case 'u':
                {
                alt15=9;
                }
                break;
            case '0':
            case '1':
            case '2':
            case '3':
                {
                alt15=10;
                }
                break;
            case '4':
            case '5':
            case '6':
            case '7':
                {
                alt15=11;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // GOCLLexerRules.g:138:8: 'n'
                    {
                    match('n'); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // GOCLLexerRules.g:139:8: 'r'
                    {
                    match('r'); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // GOCLLexerRules.g:140:8: 't'
                    {
                    match('t'); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // GOCLLexerRules.g:141:8: 'b'
                    {
                    match('b'); if (state.failed) return ;

                    }
                    break;
                case 5 :
                    // GOCLLexerRules.g:142:8: 'f'
                    {
                    match('f'); if (state.failed) return ;

                    }
                    break;
                case 6 :
                    // GOCLLexerRules.g:143:8: '\"'
                    {
                    match('\"'); if (state.failed) return ;

                    }
                    break;
                case 7 :
                    // GOCLLexerRules.g:144:8: '\\''
                    {
                    match('\''); if (state.failed) return ;

                    }
                    break;
                case 8 :
                    // GOCLLexerRules.g:145:8: '\\\\'
                    {
                    match('\\'); if (state.failed) return ;

                    }
                    break;
                case 9 :
                    // GOCLLexerRules.g:146:8: 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
                    {
                    match('u'); if (state.failed) return ;
                    mHEX_DIGIT(); if (state.failed) return ;
                    mHEX_DIGIT(); if (state.failed) return ;
                    mHEX_DIGIT(); if (state.failed) return ;
                    mHEX_DIGIT(); if (state.failed) return ;

                    }
                    break;
                case 10 :
                    // GOCLLexerRules.g:147:8: '0' .. '3' ( '0' .. '7' ( '0' .. '7' )? )?
                    {
                    matchRange('0','3'); if (state.failed) return ;
                    // GOCLLexerRules.g:147:17: ( '0' .. '7' ( '0' .. '7' )? )?
                    int alt13=2;
                    int LA13_0 = input.LA(1);

                    if ( ((LA13_0>='0' && LA13_0<='7')) ) {
                        alt13=1;
                    }
                    switch (alt13) {
                        case 1 :
                            // GOCLLexerRules.g:147:18: '0' .. '7' ( '0' .. '7' )?
                            {
                            matchRange('0','7'); if (state.failed) return ;
                            // GOCLLexerRules.g:147:27: ( '0' .. '7' )?
                            int alt12=2;
                            int LA12_0 = input.LA(1);

                            if ( ((LA12_0>='0' && LA12_0<='7')) ) {
                                alt12=1;
                            }
                            switch (alt12) {
                                case 1 :
                                    // GOCLLexerRules.g:147:28: '0' .. '7'
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
                    // GOCLLexerRules.g:147:45: '4' .. '7' ( '0' .. '7' )?
                    {
                    matchRange('4','7'); if (state.failed) return ;
                    // GOCLLexerRules.g:147:54: ( '0' .. '7' )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( ((LA14_0>='0' && LA14_0<='7')) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // GOCLLexerRules.g:147:55: '0' .. '7'
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
    // $ANTLR end ESC

    // $ANTLR start HEX_DIGIT
    public final void mHEX_DIGIT() throws RecognitionException {
        try {
            // GOCLLexerRules.g:153:10: ( ( '0' .. '9' | 'A' .. 'F' | 'a' .. 'f' ) )
            // GOCLLexerRules.g:154:5: ( '0' .. '9' | 'A' .. 'F' | 'a' .. 'f' )
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
    // $ANTLR end HEX_DIGIT

    // $ANTLR start IDENT
    public final void mIDENT() throws RecognitionException {
        try {
            int _type = IDENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GOCLLexerRules.g:165:9: ( ( '$' | 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // GOCLLexerRules.g:166:5: ( '$' | 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
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

            // GOCLLexerRules.g:166:39: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0>='0' && LA16_0<='9')||(LA16_0>='A' && LA16_0<='Z')||LA16_0=='_'||(LA16_0>='a' && LA16_0<='z')) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // GOCLLexerRules.g:
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
            	    break loop16;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end IDENT

    // $ANTLR start VOCAB
    public final void mVOCAB() throws RecognitionException {
        try {
            // GOCLLexerRules.g:173:6: ( '\\U0003' .. '\\U0377' )
            // GOCLLexerRules.g:174:5: '\\U0003' .. '\\U0377'
            {
            matchRange('\u0003','\u0377'); if (state.failed) return ;

            }

        }
        finally {
        }
    }
    // $ANTLR end VOCAB

    public void mTokens() throws RecognitionException {
        // GOCLLexerRules.g:1:8: ( WS | SL_COMMENT | ML_COMMENT | ARROW | AT | BAR | COLON | COLON_COLON | COLON_EQUAL | COMMA | DOT | DOTDOT | EQUAL | GREATER | GREATER_EQUAL | HASH | LBRACE | LBRACK | LESS | LESS_EQUAL | LPAREN | MINUS | NOT_EQUAL | PLUS | RBRACE | RBRACK | RPAREN | SEMI | SLASH | STAR | RANGE_OR_INT | STRING | IDENT )
        int alt17=33;
        alt17 = dfa17.predict(input);
        switch (alt17) {
            case 1 :
                // GOCLLexerRules.g:1:10: WS
                {
                mWS(); if (state.failed) return ;

                }
                break;
            case 2 :
                // GOCLLexerRules.g:1:13: SL_COMMENT
                {
                mSL_COMMENT(); if (state.failed) return ;

                }
                break;
            case 3 :
                // GOCLLexerRules.g:1:24: ML_COMMENT
                {
                mML_COMMENT(); if (state.failed) return ;

                }
                break;
            case 4 :
                // GOCLLexerRules.g:1:35: ARROW
                {
                mARROW(); if (state.failed) return ;

                }
                break;
            case 5 :
                // GOCLLexerRules.g:1:41: AT
                {
                mAT(); if (state.failed) return ;

                }
                break;
            case 6 :
                // GOCLLexerRules.g:1:44: BAR
                {
                mBAR(); if (state.failed) return ;

                }
                break;
            case 7 :
                // GOCLLexerRules.g:1:48: COLON
                {
                mCOLON(); if (state.failed) return ;

                }
                break;
            case 8 :
                // GOCLLexerRules.g:1:54: COLON_COLON
                {
                mCOLON_COLON(); if (state.failed) return ;

                }
                break;
            case 9 :
                // GOCLLexerRules.g:1:66: COLON_EQUAL
                {
                mCOLON_EQUAL(); if (state.failed) return ;

                }
                break;
            case 10 :
                // GOCLLexerRules.g:1:78: COMMA
                {
                mCOMMA(); if (state.failed) return ;

                }
                break;
            case 11 :
                // GOCLLexerRules.g:1:84: DOT
                {
                mDOT(); if (state.failed) return ;

                }
                break;
            case 12 :
                // GOCLLexerRules.g:1:88: DOTDOT
                {
                mDOTDOT(); if (state.failed) return ;

                }
                break;
            case 13 :
                // GOCLLexerRules.g:1:95: EQUAL
                {
                mEQUAL(); if (state.failed) return ;

                }
                break;
            case 14 :
                // GOCLLexerRules.g:1:101: GREATER
                {
                mGREATER(); if (state.failed) return ;

                }
                break;
            case 15 :
                // GOCLLexerRules.g:1:109: GREATER_EQUAL
                {
                mGREATER_EQUAL(); if (state.failed) return ;

                }
                break;
            case 16 :
                // GOCLLexerRules.g:1:123: HASH
                {
                mHASH(); if (state.failed) return ;

                }
                break;
            case 17 :
                // GOCLLexerRules.g:1:128: LBRACE
                {
                mLBRACE(); if (state.failed) return ;

                }
                break;
            case 18 :
                // GOCLLexerRules.g:1:135: LBRACK
                {
                mLBRACK(); if (state.failed) return ;

                }
                break;
            case 19 :
                // GOCLLexerRules.g:1:142: LESS
                {
                mLESS(); if (state.failed) return ;

                }
                break;
            case 20 :
                // GOCLLexerRules.g:1:147: LESS_EQUAL
                {
                mLESS_EQUAL(); if (state.failed) return ;

                }
                break;
            case 21 :
                // GOCLLexerRules.g:1:158: LPAREN
                {
                mLPAREN(); if (state.failed) return ;

                }
                break;
            case 22 :
                // GOCLLexerRules.g:1:165: MINUS
                {
                mMINUS(); if (state.failed) return ;

                }
                break;
            case 23 :
                // GOCLLexerRules.g:1:171: NOT_EQUAL
                {
                mNOT_EQUAL(); if (state.failed) return ;

                }
                break;
            case 24 :
                // GOCLLexerRules.g:1:181: PLUS
                {
                mPLUS(); if (state.failed) return ;

                }
                break;
            case 25 :
                // GOCLLexerRules.g:1:186: RBRACE
                {
                mRBRACE(); if (state.failed) return ;

                }
                break;
            case 26 :
                // GOCLLexerRules.g:1:193: RBRACK
                {
                mRBRACK(); if (state.failed) return ;

                }
                break;
            case 27 :
                // GOCLLexerRules.g:1:200: RPAREN
                {
                mRPAREN(); if (state.failed) return ;

                }
                break;
            case 28 :
                // GOCLLexerRules.g:1:207: SEMI
                {
                mSEMI(); if (state.failed) return ;

                }
                break;
            case 29 :
                // GOCLLexerRules.g:1:212: SLASH
                {
                mSLASH(); if (state.failed) return ;

                }
                break;
            case 30 :
                // GOCLLexerRules.g:1:218: STAR
                {
                mSTAR(); if (state.failed) return ;

                }
                break;
            case 31 :
                // GOCLLexerRules.g:1:223: RANGE_OR_INT
                {
                mRANGE_OR_INT(); if (state.failed) return ;

                }
                break;
            case 32 :
                // GOCLLexerRules.g:1:236: STRING
                {
                mSTRING(); if (state.failed) return ;

                }
                break;
            case 33 :
                // GOCLLexerRules.g:1:243: IDENT
                {
                mIDENT(); if (state.failed) return ;

                }
                break;

        }

    }

    // $ANTLR start synpred1_GOCLLexerRules
    public final void synpred1_GOCLLexerRules_fragment() throws RecognitionException {   
        // GOCLLexerRules.g:115:7: ( INT '..' )
        // GOCLLexerRules.g:115:9: INT '..'
        {
        mINT(); if (state.failed) return ;
        match(".."); if (state.failed) return ;


        }
    }
    // $ANTLR end synpred1_GOCLLexerRules

    // $ANTLR start synpred2_GOCLLexerRules
    public final void synpred2_GOCLLexerRules_fragment() throws RecognitionException {   
        // GOCLLexerRules.g:116:7: ( INT '.' INT )
        // GOCLLexerRules.g:116:9: INT '.' INT
        {
        mINT(); if (state.failed) return ;
        match('.'); if (state.failed) return ;
        mINT(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_GOCLLexerRules

    // $ANTLR start synpred3_GOCLLexerRules
    public final void synpred3_GOCLLexerRules_fragment() throws RecognitionException {   
        // GOCLLexerRules.g:117:7: ( INT ( 'e' | 'E' ) )
        // GOCLLexerRules.g:117:9: INT ( 'e' | 'E' )
        {
        mINT(); if (state.failed) return ;
        if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
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
    // $ANTLR end synpred3_GOCLLexerRules

    public final boolean synpred1_GOCLLexerRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_GOCLLexerRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_GOCLLexerRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_GOCLLexerRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_GOCLLexerRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_GOCLLexerRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA17 dfa17 = new DFA17(this);
    static final String DFA17_eotS =
        "\2\uffff\1\33\1\35\2\uffff\1\40\1\uffff\1\42\1\uffff\1\44\3\uffff"+
        "\1\47\31\uffff";
    static final String DFA17_eofS =
        "\50\uffff";
    static final String DFA17_minS =
        "\1\11\1\uffff\1\52\1\55\2\uffff\1\72\1\uffff\1\56\1\uffff\1\75\3"+
        "\uffff\1\75\31\uffff";
    static final String DFA17_maxS =
        "\1\175\1\uffff\1\57\1\76\2\uffff\1\75\1\uffff\1\56\1\uffff\1\75"+
        "\3\uffff\1\76\31\uffff";
    static final String DFA17_acceptS =
        "\1\uffff\1\1\2\uffff\1\5\1\6\1\uffff\1\12\1\uffff\1\15\1\uffff\1"+
        "\20\1\21\1\22\1\uffff\1\25\1\30\1\31\1\32\1\33\1\34\1\36\1\37\1"+
        "\40\1\41\1\2\1\3\1\35\1\4\1\26\1\10\1\11\1\7\1\14\1\13\1\17\1\16"+
        "\1\24\1\27\1\23";
    static final String DFA17_specialS =
        "\50\uffff}>";
    static final String[] DFA17_transitionS = {
            "\2\1\1\uffff\2\1\22\uffff\1\1\2\uffff\1\13\1\30\2\uffff\1\27"+
            "\1\17\1\23\1\25\1\20\1\7\1\3\1\10\1\2\12\26\1\6\1\24\1\16\1"+
            "\11\1\12\1\uffff\1\4\32\30\1\15\1\uffff\1\22\1\uffff\1\30\1"+
            "\uffff\32\30\1\14\1\5\1\21",
            "",
            "\1\32\4\uffff\1\31",
            "\1\31\20\uffff\1\34",
            "",
            "",
            "\1\36\2\uffff\1\37",
            "",
            "\1\41",
            "",
            "\1\43",
            "",
            "",
            "",
            "\1\45\1\46",
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

    static final short[] DFA17_eot = DFA.unpackEncodedString(DFA17_eotS);
    static final short[] DFA17_eof = DFA.unpackEncodedString(DFA17_eofS);
    static final char[] DFA17_min = DFA.unpackEncodedStringToUnsignedChars(DFA17_minS);
    static final char[] DFA17_max = DFA.unpackEncodedStringToUnsignedChars(DFA17_maxS);
    static final short[] DFA17_accept = DFA.unpackEncodedString(DFA17_acceptS);
    static final short[] DFA17_special = DFA.unpackEncodedString(DFA17_specialS);
    static final short[][] DFA17_transition;

    static {
        int numStates = DFA17_transitionS.length;
        DFA17_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA17_transition[i] = DFA.unpackEncodedString(DFA17_transitionS[i]);
        }
    }

    class DFA17 extends DFA {

        public DFA17(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 17;
            this.eot = DFA17_eot;
            this.eof = DFA17_eof;
            this.min = DFA17_min;
            this.max = DFA17_max;
            this.accept = DFA17_accept;
            this.special = DFA17_special;
            this.transition = DFA17_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( WS | SL_COMMENT | ML_COMMENT | ARROW | AT | BAR | COLON | COLON_COLON | COLON_EQUAL | COMMA | DOT | DOTDOT | EQUAL | GREATER | GREATER_EQUAL | HASH | LBRACE | LBRACK | LESS | LESS_EQUAL | LPAREN | MINUS | NOT_EQUAL | PLUS | RBRACE | RBRACK | RPAREN | SEMI | SLASH | STAR | RANGE_OR_INT | STRING | IDENT );";
        }
    }
 

}