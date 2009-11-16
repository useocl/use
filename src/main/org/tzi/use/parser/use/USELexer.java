// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 USE.g 2009-11-16 16:45:54

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
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all") public class USELexer extends Lexer {
    public static final int STAR=16;
    public static final int EOF=-1;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__91=91;
    public static final int RPAREN=20;
    public static final int T__92=92;
    public static final int GREATER=22;
    public static final int T__90=90;
    public static final int NOT_EQUAL=21;
    public static final int LESS=8;
    public static final int T__99=99;
    public static final int T__98=98;
    public static final int T__97=97;
    public static final int T__96=96;
    public static final int T__95=95;
    public static final int RBRACK=12;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int RBRACE=6;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int INT=15;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int REAL=32;
    public static final int T__71=71;
    public static final int WS=36;
    public static final int T__72=72;
    public static final int T__70=70;
    public static final int SL_COMMENT=37;
    public static final int LESS_EQUAL=23;
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
    public static final int LBRACK=11;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int ESC=40;
    public static final int LBRACE=5;
    public static final int DOTDOT=14;
    public static final int T__61=61;
    public static final int T__60=60;
    public static final int LPAREN=19;
    public static final int AT=30;
    public static final int T__55=55;
    public static final int ML_COMMENT=38;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int SLASH=27;
    public static final int T__51=51;
    public static final int COLON_EQUAL=18;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int T__107=107;
    public static final int T__108=108;
    public static final int COMMA=13;
    public static final int T__109=109;
    public static final int T__59=59;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int EQUAL=10;
    public static final int T__105=105;
    public static final int T__106=106;
    public static final int T__111=111;
    public static final int T__110=110;
    public static final int IDENT=4;
    public static final int PLUS=25;
    public static final int RANGE_OR_INT=39;
    public static final int DOT=29;
    public static final int T__50=50;
    public static final int T__43=43;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int HASH=34;
    public static final int HEX_DIGIT=41;
    public static final int COLON_COLON=17;
    public static final int T__102=102;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int MINUS=26;
    public static final int SEMI=7;
    public static final int COLON=9;
    public static final int NEWLINE=35;
    public static final int VOCAB=42;
    public static final int ARROW=28;
    public static final int GREATER_EQUAL=24;
    public static final int BAR=31;
    public static final int STRING=33;

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

    public USELexer() {;} 
    public USELexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public USELexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "USE.g"; }

    // $ANTLR start "T__43"
    public final void mT__43() throws RecognitionException {
        try {
            int _type = T__43;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:46:7: ( 'model' )
            // USE.g:46:9: 'model'
            {
            match("model"); if (state.failed) return ;


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
            // USE.g:47:7: ( 'constraints' )
            // USE.g:47:9: 'constraints'
            {
            match("constraints"); if (state.failed) return ;


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
            // USE.g:48:7: ( 'enum' )
            // USE.g:48:9: 'enum'
            {
            match("enum"); if (state.failed) return ;


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
            // USE.g:49:7: ( 'abstract' )
            // USE.g:49:9: 'abstract'
            {
            match("abstract"); if (state.failed) return ;


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
            // USE.g:50:7: ( 'class' )
            // USE.g:50:9: 'class'
            {
            match("class"); if (state.failed) return ;


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
            // USE.g:51:7: ( 'attributes' )
            // USE.g:51:9: 'attributes'
            {
            match("attributes"); if (state.failed) return ;


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
            // USE.g:52:7: ( 'operations' )
            // USE.g:52:9: 'operations'
            {
            match("operations"); if (state.failed) return ;


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
            // USE.g:53:7: ( 'end' )
            // USE.g:53:9: 'end'
            {
            match("end"); if (state.failed) return ;


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
            // USE.g:54:7: ( 'associationClass' )
            // USE.g:54:9: 'associationClass'
            {
            match("associationClass"); if (state.failed) return ;


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
            // USE.g:55:7: ( 'associationclass' )
            // USE.g:55:9: 'associationclass'
            {
            match("associationclass"); if (state.failed) return ;


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
            // USE.g:56:7: ( 'between' )
            // USE.g:56:9: 'between'
            {
            match("between"); if (state.failed) return ;


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
            // USE.g:57:7: ( 'aggregation' )
            // USE.g:57:9: 'aggregation'
            {
            match("aggregation"); if (state.failed) return ;


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
            // USE.g:58:7: ( 'composition' )
            // USE.g:58:9: 'composition'
            {
            match("composition"); if (state.failed) return ;


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
            // USE.g:59:7: ( 'begin' )
            // USE.g:59:9: 'begin'
            {
            match("begin"); if (state.failed) return ;


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
            // USE.g:60:7: ( 'association' )
            // USE.g:60:9: 'association'
            {
            match("association"); if (state.failed) return ;


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
            // USE.g:61:7: ( 'role' )
            // USE.g:61:9: 'role'
            {
            match("role"); if (state.failed) return ;


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
            // USE.g:62:7: ( 'ordered' )
            // USE.g:62:9: 'ordered'
            {
            match("ordered"); if (state.failed) return ;


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
            // USE.g:63:7: ( 'subsets' )
            // USE.g:63:9: 'subsets'
            {
            match("subsets"); if (state.failed) return ;


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
            // USE.g:64:7: ( 'redefines' )
            // USE.g:64:9: 'redefines'
            {
            match("redefines"); if (state.failed) return ;


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
            // USE.g:65:7: ( 'context' )
            // USE.g:65:9: 'context'
            {
            match("context"); if (state.failed) return ;


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
            // USE.g:66:7: ( 'inv' )
            // USE.g:66:9: 'inv'
            {
            match("inv"); if (state.failed) return ;


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
            // USE.g:67:7: ( 'existential' )
            // USE.g:67:9: 'existential'
            {
            match("existential"); if (state.failed) return ;


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
            // USE.g:68:7: ( 'pre' )
            // USE.g:68:9: 'pre'
            {
            match("pre"); if (state.failed) return ;


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
            // USE.g:69:7: ( 'post' )
            // USE.g:69:9: 'post'
            {
            match("post"); if (state.failed) return ;


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
            // USE.g:70:7: ( 'var' )
            // USE.g:70:9: 'var'
            {
            match("var"); if (state.failed) return ;


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
            // USE.g:71:7: ( 'declare' )
            // USE.g:71:9: 'declare'
            {
            match("declare"); if (state.failed) return ;


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
            // USE.g:72:7: ( 'set' )
            // USE.g:72:9: 'set'
            {
            match("set"); if (state.failed) return ;


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
            // USE.g:73:7: ( 'create' )
            // USE.g:73:9: 'create'
            {
            match("create"); if (state.failed) return ;


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
            // USE.g:74:7: ( 'namehint' )
            // USE.g:74:9: 'namehint'
            {
            match("namehint"); if (state.failed) return ;


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
            // USE.g:75:7: ( 'insert' )
            // USE.g:75:9: 'insert'
            {
            match("insert"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__72"

    // $ANTLR start "T__73"
    public final void mT__73() throws RecognitionException {
        try {
            int _type = T__73;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:76:7: ( 'into' )
            // USE.g:76:9: 'into'
            {
            match("into"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__73"

    // $ANTLR start "T__74"
    public final void mT__74() throws RecognitionException {
        try {
            int _type = T__74;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:77:7: ( 'delete' )
            // USE.g:77:9: 'delete'
            {
            match("delete"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__74"

    // $ANTLR start "T__75"
    public final void mT__75() throws RecognitionException {
        try {
            int _type = T__75;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:78:7: ( 'from' )
            // USE.g:78:9: 'from'
            {
            match("from"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__75"

    // $ANTLR start "T__76"
    public final void mT__76() throws RecognitionException {
        try {
            int _type = T__76;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:79:7: ( 'destroy' )
            // USE.g:79:9: 'destroy'
            {
            match("destroy"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__76"

    // $ANTLR start "T__77"
    public final void mT__77() throws RecognitionException {
        try {
            int _type = T__77;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:80:7: ( 'if' )
            // USE.g:80:9: 'if'
            {
            match("if"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__77"

    // $ANTLR start "T__78"
    public final void mT__78() throws RecognitionException {
        try {
            int _type = T__78;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:81:7: ( 'then' )
            // USE.g:81:9: 'then'
            {
            match("then"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__78"

    // $ANTLR start "T__79"
    public final void mT__79() throws RecognitionException {
        try {
            int _type = T__79;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:82:7: ( 'else' )
            // USE.g:82:9: 'else'
            {
            match("else"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__79"

    // $ANTLR start "T__80"
    public final void mT__80() throws RecognitionException {
        try {
            int _type = T__80;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:83:7: ( 'endif' )
            // USE.g:83:9: 'endif'
            {
            match("endif"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__80"

    // $ANTLR start "T__81"
    public final void mT__81() throws RecognitionException {
        try {
            int _type = T__81;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:84:7: ( 'while' )
            // USE.g:84:9: 'while'
            {
            match("while"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__81"

    // $ANTLR start "T__82"
    public final void mT__82() throws RecognitionException {
        try {
            int _type = T__82;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:85:7: ( 'do' )
            // USE.g:85:9: 'do'
            {
            match("do"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__82"

    // $ANTLR start "T__83"
    public final void mT__83() throws RecognitionException {
        try {
            int _type = T__83;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:86:7: ( 'wend' )
            // USE.g:86:9: 'wend'
            {
            match("wend"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__83"

    // $ANTLR start "T__84"
    public final void mT__84() throws RecognitionException {
        try {
            int _type = T__84;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:87:7: ( 'for' )
            // USE.g:87:9: 'for'
            {
            match("for"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__84"

    // $ANTLR start "T__85"
    public final void mT__85() throws RecognitionException {
        try {
            int _type = T__85;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:88:7: ( 'in' )
            // USE.g:88:9: 'in'
            {
            match("in"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__85"

    // $ANTLR start "T__86"
    public final void mT__86() throws RecognitionException {
        try {
            int _type = T__86;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:89:7: ( 'execute' )
            // USE.g:89:9: 'execute'
            {
            match("execute"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__86"

    // $ANTLR start "T__87"
    public final void mT__87() throws RecognitionException {
        try {
            int _type = T__87;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:90:7: ( 'let' )
            // USE.g:90:9: 'let'
            {
            match("let"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__87"

    // $ANTLR start "T__88"
    public final void mT__88() throws RecognitionException {
        try {
            int _type = T__88;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:91:7: ( 'implies' )
            // USE.g:91:9: 'implies'
            {
            match("implies"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__88"

    // $ANTLR start "T__89"
    public final void mT__89() throws RecognitionException {
        try {
            int _type = T__89;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:92:7: ( 'or' )
            // USE.g:92:9: 'or'
            {
            match("or"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__89"

    // $ANTLR start "T__90"
    public final void mT__90() throws RecognitionException {
        try {
            int _type = T__90;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:93:7: ( 'xor' )
            // USE.g:93:9: 'xor'
            {
            match("xor"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__90"

    // $ANTLR start "T__91"
    public final void mT__91() throws RecognitionException {
        try {
            int _type = T__91;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:94:7: ( 'and' )
            // USE.g:94:9: 'and'
            {
            match("and"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__91"

    // $ANTLR start "T__92"
    public final void mT__92() throws RecognitionException {
        try {
            int _type = T__92;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:95:7: ( 'div' )
            // USE.g:95:9: 'div'
            {
            match("div"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__92"

    // $ANTLR start "T__93"
    public final void mT__93() throws RecognitionException {
        try {
            int _type = T__93;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:96:7: ( 'not' )
            // USE.g:96:9: 'not'
            {
            match("not"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__93"

    // $ANTLR start "T__94"
    public final void mT__94() throws RecognitionException {
        try {
            int _type = T__94;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:97:7: ( 'allInstances' )
            // USE.g:97:9: 'allInstances'
            {
            match("allInstances"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__94"

    // $ANTLR start "T__95"
    public final void mT__95() throws RecognitionException {
        try {
            int _type = T__95;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:98:7: ( 'iterate' )
            // USE.g:98:9: 'iterate'
            {
            match("iterate"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__95"

    // $ANTLR start "T__96"
    public final void mT__96() throws RecognitionException {
        try {
            int _type = T__96;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:99:7: ( 'oclAsType' )
            // USE.g:99:9: 'oclAsType'
            {
            match("oclAsType"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__96"

    // $ANTLR start "T__97"
    public final void mT__97() throws RecognitionException {
        try {
            int _type = T__97;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:100:7: ( 'oclIsKindOf' )
            // USE.g:100:9: 'oclIsKindOf'
            {
            match("oclIsKindOf"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__97"

    // $ANTLR start "T__98"
    public final void mT__98() throws RecognitionException {
        try {
            int _type = T__98;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:101:7: ( 'oclIsTypeOf' )
            // USE.g:101:9: 'oclIsTypeOf'
            {
            match("oclIsTypeOf"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__98"

    // $ANTLR start "T__99"
    public final void mT__99() throws RecognitionException {
        try {
            int _type = T__99;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:102:7: ( 'true' )
            // USE.g:102:9: 'true'
            {
            match("true"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__99"

    // $ANTLR start "T__100"
    public final void mT__100() throws RecognitionException {
        try {
            int _type = T__100;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:103:8: ( 'false' )
            // USE.g:103:10: 'false'
            {
            match("false"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__100"

    // $ANTLR start "T__101"
    public final void mT__101() throws RecognitionException {
        try {
            int _type = T__101;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:104:8: ( 'Set' )
            // USE.g:104:10: 'Set'
            {
            match("Set"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__101"

    // $ANTLR start "T__102"
    public final void mT__102() throws RecognitionException {
        try {
            int _type = T__102;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:105:8: ( 'Sequence' )
            // USE.g:105:10: 'Sequence'
            {
            match("Sequence"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__102"

    // $ANTLR start "T__103"
    public final void mT__103() throws RecognitionException {
        try {
            int _type = T__103;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:106:8: ( 'Bag' )
            // USE.g:106:10: 'Bag'
            {
            match("Bag"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__103"

    // $ANTLR start "T__104"
    public final void mT__104() throws RecognitionException {
        try {
            int _type = T__104;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:107:8: ( 'OrderedSet' )
            // USE.g:107:10: 'OrderedSet'
            {
            match("OrderedSet"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__104"

    // $ANTLR start "T__105"
    public final void mT__105() throws RecognitionException {
        try {
            int _type = T__105;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:108:8: ( 'oclEmpty' )
            // USE.g:108:10: 'oclEmpty'
            {
            match("oclEmpty"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__105"

    // $ANTLR start "T__106"
    public final void mT__106() throws RecognitionException {
        try {
            int _type = T__106;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:109:8: ( 'oclUndefined' )
            // USE.g:109:10: 'oclUndefined'
            {
            match("oclUndefined"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__106"

    // $ANTLR start "T__107"
    public final void mT__107() throws RecognitionException {
        try {
            int _type = T__107;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:110:8: ( 'Undefined' )
            // USE.g:110:10: 'Undefined'
            {
            match("Undefined"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__107"

    // $ANTLR start "T__108"
    public final void mT__108() throws RecognitionException {
        try {
            int _type = T__108;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:111:8: ( 'null' )
            // USE.g:111:10: 'null'
            {
            match("null"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__108"

    // $ANTLR start "T__109"
    public final void mT__109() throws RecognitionException {
        try {
            int _type = T__109;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:112:8: ( 'Tuple' )
            // USE.g:112:10: 'Tuple'
            {
            match("Tuple"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__109"

    // $ANTLR start "T__110"
    public final void mT__110() throws RecognitionException {
        try {
            int _type = T__110;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:113:8: ( 'Date' )
            // USE.g:113:10: 'Date'
            {
            match("Date"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__110"

    // $ANTLR start "T__111"
    public final void mT__111() throws RecognitionException {
        try {
            int _type = T__111;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:114:8: ( 'Collection' )
            // USE.g:114:10: 'Collection'
            {
            match("Collection"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__111"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // USE.g:1127:3: ( ( ' ' | '\\t' | '\\f' | NEWLINE ) )
            // USE.g:1128:5: ( ' ' | '\\t' | '\\f' | NEWLINE )
            {
            // USE.g:1128:5: ( ' ' | '\\t' | '\\f' | NEWLINE )
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
                    // USE.g:1128:7: ' '
                    {
                    match(' '); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // USE.g:1129:7: '\\t'
                    {
                    match('\t'); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // USE.g:1130:7: '\\f'
                    {
                    match('\f'); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // USE.g:1131:7: NEWLINE
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
            // USE.g:1137:11: ( ( '//' | '--' ) (~ ( '\\n' | '\\r' ) )* NEWLINE )
            // USE.g:1138:5: ( '//' | '--' ) (~ ( '\\n' | '\\r' ) )* NEWLINE
            {
            // USE.g:1138:5: ( '//' | '--' )
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
                    // USE.g:1138:6: '//'
                    {
                    match("//"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // USE.g:1138:13: '--'
                    {
                    match("--"); if (state.failed) return ;


                    }
                    break;

            }

            // USE.g:1139:5: (~ ( '\\n' | '\\r' ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='\u0000' && LA3_0<='\t')||(LA3_0>='\u000B' && LA3_0<='\f')||(LA3_0>='\u000E' && LA3_0<='\uFFFF')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // USE.g:1139:6: ~ ( '\\n' | '\\r' )
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
            // USE.g:1144:11: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // USE.g:1145:5: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); if (state.failed) return ;

            // USE.g:1145:10: ( options {greedy=false; } : . )*
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
            	    // USE.g:1145:38: .
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
            // USE.g:1148:9: ( '\\r\\n' | '\\r' | '\\n' )
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
                    // USE.g:1149:5: '\\r\\n'
                    {
                    match("\r\n"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // USE.g:1149:14: '\\r'
                    {
                    match('\r'); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // USE.g:1149:21: '\\n'
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
            // USE.g:1152:10: ( '->' )
            // USE.g:1152:12: '->'
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
            // USE.g:1153:11: ( '@' )
            // USE.g:1153:13: '@'
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
            // USE.g:1154:8: ( '|' )
            // USE.g:1154:10: '|'
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
            // USE.g:1155:10: ( ':' )
            // USE.g:1155:12: ':'
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
            // USE.g:1156:14: ( '::' )
            // USE.g:1156:16: '::'
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
            // USE.g:1157:14: ( ':=' )
            // USE.g:1157:16: ':='
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
            // USE.g:1158:10: ( ',' )
            // USE.g:1158:12: ','
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
            // USE.g:1159:8: ( '.' )
            // USE.g:1159:10: '.'
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
            // USE.g:1160:11: ( '..' )
            // USE.g:1160:13: '..'
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
            // USE.g:1161:10: ( '=' )
            // USE.g:1161:12: '='
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
            // USE.g:1162:11: ( '>' )
            // USE.g:1162:13: '>'
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
            // USE.g:1163:15: ( '>=' )
            // USE.g:1163:17: '>='
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
            // USE.g:1164:9: ( '#' )
            // USE.g:1164:11: '#'
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
            // USE.g:1165:11: ( '{' )
            // USE.g:1165:13: '{'
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
            // USE.g:1166:11: ( '[' )
            // USE.g:1166:13: '['
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
            // USE.g:1167:9: ( '<' )
            // USE.g:1167:11: '<'
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
            // USE.g:1168:14: ( '<=' )
            // USE.g:1168:16: '<='
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
            // USE.g:1169:11: ( '(' )
            // USE.g:1169:13: '('
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
            // USE.g:1170:10: ( '-' )
            // USE.g:1170:12: '-'
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
            // USE.g:1171:13: ( '<>' )
            // USE.g:1171:15: '<>'
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
            // USE.g:1172:9: ( '+' )
            // USE.g:1172:11: '+'
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
            // USE.g:1173:11: ( '}' )
            // USE.g:1173:13: '}'
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
            // USE.g:1174:11: ( ']' )
            // USE.g:1174:13: ']'
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
            // USE.g:1175:10: ( ')' )
            // USE.g:1175:12: ')'
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
            // USE.g:1176:8: ( ';' )
            // USE.g:1176:10: ';'
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
            // USE.g:1177:10: ( '/' )
            // USE.g:1177:12: '/'
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
            // USE.g:1178:9: ( '*' )
            // USE.g:1178:11: '*'
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
            // USE.g:1181:4: ( ( '0' .. '9' )+ )
            // USE.g:1182:5: ( '0' .. '9' )+
            {
            // USE.g:1182:5: ( '0' .. '9' )+
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
            	    // USE.g:1182:6: '0' .. '9'
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
            // USE.g:1186:5: ( INT ( '.' INT ( ( 'e' | 'E' ) ( '+' | '-' )? INT )? | ( 'e' | 'E' ) ( '+' | '-' )? INT ) )
            // USE.g:1187:5: INT ( '.' INT ( ( 'e' | 'E' ) ( '+' | '-' )? INT )? | ( 'e' | 'E' ) ( '+' | '-' )? INT )
            {
            mINT(); if (state.failed) return ;
            // USE.g:1187:9: ( '.' INT ( ( 'e' | 'E' ) ( '+' | '-' )? INT )? | ( 'e' | 'E' ) ( '+' | '-' )? INT )
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
                    // USE.g:1187:10: '.' INT ( ( 'e' | 'E' ) ( '+' | '-' )? INT )?
                    {
                    match('.'); if (state.failed) return ;
                    mINT(); if (state.failed) return ;
                    // USE.g:1187:18: ( ( 'e' | 'E' ) ( '+' | '-' )? INT )?
                    int alt8=2;
                    int LA8_0 = input.LA(1);

                    if ( (LA8_0=='E'||LA8_0=='e') ) {
                        alt8=1;
                    }
                    switch (alt8) {
                        case 1 :
                            // USE.g:1187:19: ( 'e' | 'E' ) ( '+' | '-' )? INT
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

                            // USE.g:1187:31: ( '+' | '-' )?
                            int alt7=2;
                            int LA7_0 = input.LA(1);

                            if ( (LA7_0=='+'||LA7_0=='-') ) {
                                alt7=1;
                            }
                            switch (alt7) {
                                case 1 :
                                    // USE.g:
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
                    // USE.g:1187:52: ( 'e' | 'E' ) ( '+' | '-' )? INT
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

                    // USE.g:1187:64: ( '+' | '-' )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0=='+'||LA9_0=='-') ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // USE.g:
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
            // USE.g:1190:13: ( ( INT '..' )=> INT | ( REAL )=> REAL | INT )
            int alt11=3;
            int LA11_0 = input.LA(1);

            if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                int LA11_1 = input.LA(2);

                if ( ((LA11_1>='0' && LA11_1<='9')) && (synpred2_USE())) {
                    alt11=2;
                }
                else if ( (LA11_1=='.') && (synpred2_USE())) {
                    alt11=2;
                }
                else if ( (LA11_1=='E'||LA11_1=='e') && (synpred2_USE())) {
                    alt11=2;
                }
                else if ( (synpred1_USE()) ) {
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
                    // USE.g:1191:7: ( INT '..' )=> INT
                    {
                    mINT(); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       _type=INT; 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:1192:7: ( REAL )=> REAL
                    {
                    mREAL(); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       _type=REAL; 
                    }

                    }
                    break;
                case 3 :
                    // USE.g:1193:9: INT
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
            // USE.g:1198:7: ( '\\'' (~ ( '\\'' | '\\\\' ) | ESC )* '\\'' )
            // USE.g:1199:5: '\\'' (~ ( '\\'' | '\\\\' ) | ESC )* '\\''
            {
            match('\''); if (state.failed) return ;
            // USE.g:1199:10: (~ ( '\\'' | '\\\\' ) | ESC )*
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
            	    // USE.g:1199:12: ~ ( '\\'' | '\\\\' )
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
            	    // USE.g:1199:27: ESC
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
            // USE.g:1211:1: ( '\\\\' ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT | '0' .. '3' ( '0' .. '7' ( '0' .. '7' )? )? | '4' .. '7' ( '0' .. '7' )? ) )
            // USE.g:1212:5: '\\\\' ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT | '0' .. '3' ( '0' .. '7' ( '0' .. '7' )? )? | '4' .. '7' ( '0' .. '7' )? )
            {
            match('\\'); if (state.failed) return ;
            // USE.g:1213:6: ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT | '0' .. '3' ( '0' .. '7' ( '0' .. '7' )? )? | '4' .. '7' ( '0' .. '7' )? )
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
                    // USE.g:1213:8: 'n'
                    {
                    match('n'); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // USE.g:1214:8: 'r'
                    {
                    match('r'); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // USE.g:1215:8: 't'
                    {
                    match('t'); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // USE.g:1216:8: 'b'
                    {
                    match('b'); if (state.failed) return ;

                    }
                    break;
                case 5 :
                    // USE.g:1217:8: 'f'
                    {
                    match('f'); if (state.failed) return ;

                    }
                    break;
                case 6 :
                    // USE.g:1218:8: '\"'
                    {
                    match('\"'); if (state.failed) return ;

                    }
                    break;
                case 7 :
                    // USE.g:1219:8: '\\''
                    {
                    match('\''); if (state.failed) return ;

                    }
                    break;
                case 8 :
                    // USE.g:1220:8: '\\\\'
                    {
                    match('\\'); if (state.failed) return ;

                    }
                    break;
                case 9 :
                    // USE.g:1221:8: 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
                    {
                    match('u'); if (state.failed) return ;
                    mHEX_DIGIT(); if (state.failed) return ;
                    mHEX_DIGIT(); if (state.failed) return ;
                    mHEX_DIGIT(); if (state.failed) return ;
                    mHEX_DIGIT(); if (state.failed) return ;

                    }
                    break;
                case 10 :
                    // USE.g:1222:8: '0' .. '3' ( '0' .. '7' ( '0' .. '7' )? )?
                    {
                    matchRange('0','3'); if (state.failed) return ;
                    // USE.g:1222:17: ( '0' .. '7' ( '0' .. '7' )? )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( ((LA14_0>='0' && LA14_0<='7')) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // USE.g:1222:18: '0' .. '7' ( '0' .. '7' )?
                            {
                            matchRange('0','7'); if (state.failed) return ;
                            // USE.g:1222:27: ( '0' .. '7' )?
                            int alt13=2;
                            int LA13_0 = input.LA(1);

                            if ( ((LA13_0>='0' && LA13_0<='7')) ) {
                                alt13=1;
                            }
                            switch (alt13) {
                                case 1 :
                                    // USE.g:1222:28: '0' .. '7'
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
                    // USE.g:1222:45: '4' .. '7' ( '0' .. '7' )?
                    {
                    matchRange('4','7'); if (state.failed) return ;
                    // USE.g:1222:54: ( '0' .. '7' )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( ((LA15_0>='0' && LA15_0<='7')) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // USE.g:1222:55: '0' .. '7'
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
            // USE.g:1228:10: ( ( '0' .. '9' | 'A' .. 'F' | 'a' .. 'f' ) )
            // USE.g:1229:5: ( '0' .. '9' | 'A' .. 'F' | 'a' .. 'f' )
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
            // USE.g:1236:6: ( ( '$' | 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // USE.g:1237:5: ( '$' | 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
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

            // USE.g:1237:39: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0>='0' && LA17_0<='9')||(LA17_0>='A' && LA17_0<='Z')||LA17_0=='_'||(LA17_0>='a' && LA17_0<='z')) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // USE.g:
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
            // USE.g:1244:6: ( '\\U0003' .. '\\U0377' )
            // USE.g:1245:5: '\\U0003' .. '\\U0377'
            {
            matchRange('\u0003','\u0377'); if (state.failed) return ;

            }

        }
        finally {
        }
    }
    // $ANTLR end "VOCAB"

    public void mTokens() throws RecognitionException {
        // USE.g:1:8: ( T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | T__105 | T__106 | T__107 | T__108 | T__109 | T__110 | T__111 | WS | SL_COMMENT | ML_COMMENT | ARROW | AT | BAR | COLON | COLON_COLON | COLON_EQUAL | COMMA | DOT | DOTDOT | EQUAL | GREATER | GREATER_EQUAL | HASH | LBRACE | LBRACK | LESS | LESS_EQUAL | LPAREN | MINUS | NOT_EQUAL | PLUS | RBRACE | RBRACK | RPAREN | SEMI | SLASH | STAR | RANGE_OR_INT | STRING | IDENT )
        int alt18=102;
        alt18 = dfa18.predict(input);
        switch (alt18) {
            case 1 :
                // USE.g:1:10: T__43
                {
                mT__43(); if (state.failed) return ;

                }
                break;
            case 2 :
                // USE.g:1:16: T__44
                {
                mT__44(); if (state.failed) return ;

                }
                break;
            case 3 :
                // USE.g:1:22: T__45
                {
                mT__45(); if (state.failed) return ;

                }
                break;
            case 4 :
                // USE.g:1:28: T__46
                {
                mT__46(); if (state.failed) return ;

                }
                break;
            case 5 :
                // USE.g:1:34: T__47
                {
                mT__47(); if (state.failed) return ;

                }
                break;
            case 6 :
                // USE.g:1:40: T__48
                {
                mT__48(); if (state.failed) return ;

                }
                break;
            case 7 :
                // USE.g:1:46: T__49
                {
                mT__49(); if (state.failed) return ;

                }
                break;
            case 8 :
                // USE.g:1:52: T__50
                {
                mT__50(); if (state.failed) return ;

                }
                break;
            case 9 :
                // USE.g:1:58: T__51
                {
                mT__51(); if (state.failed) return ;

                }
                break;
            case 10 :
                // USE.g:1:64: T__52
                {
                mT__52(); if (state.failed) return ;

                }
                break;
            case 11 :
                // USE.g:1:70: T__53
                {
                mT__53(); if (state.failed) return ;

                }
                break;
            case 12 :
                // USE.g:1:76: T__54
                {
                mT__54(); if (state.failed) return ;

                }
                break;
            case 13 :
                // USE.g:1:82: T__55
                {
                mT__55(); if (state.failed) return ;

                }
                break;
            case 14 :
                // USE.g:1:88: T__56
                {
                mT__56(); if (state.failed) return ;

                }
                break;
            case 15 :
                // USE.g:1:94: T__57
                {
                mT__57(); if (state.failed) return ;

                }
                break;
            case 16 :
                // USE.g:1:100: T__58
                {
                mT__58(); if (state.failed) return ;

                }
                break;
            case 17 :
                // USE.g:1:106: T__59
                {
                mT__59(); if (state.failed) return ;

                }
                break;
            case 18 :
                // USE.g:1:112: T__60
                {
                mT__60(); if (state.failed) return ;

                }
                break;
            case 19 :
                // USE.g:1:118: T__61
                {
                mT__61(); if (state.failed) return ;

                }
                break;
            case 20 :
                // USE.g:1:124: T__62
                {
                mT__62(); if (state.failed) return ;

                }
                break;
            case 21 :
                // USE.g:1:130: T__63
                {
                mT__63(); if (state.failed) return ;

                }
                break;
            case 22 :
                // USE.g:1:136: T__64
                {
                mT__64(); if (state.failed) return ;

                }
                break;
            case 23 :
                // USE.g:1:142: T__65
                {
                mT__65(); if (state.failed) return ;

                }
                break;
            case 24 :
                // USE.g:1:148: T__66
                {
                mT__66(); if (state.failed) return ;

                }
                break;
            case 25 :
                // USE.g:1:154: T__67
                {
                mT__67(); if (state.failed) return ;

                }
                break;
            case 26 :
                // USE.g:1:160: T__68
                {
                mT__68(); if (state.failed) return ;

                }
                break;
            case 27 :
                // USE.g:1:166: T__69
                {
                mT__69(); if (state.failed) return ;

                }
                break;
            case 28 :
                // USE.g:1:172: T__70
                {
                mT__70(); if (state.failed) return ;

                }
                break;
            case 29 :
                // USE.g:1:178: T__71
                {
                mT__71(); if (state.failed) return ;

                }
                break;
            case 30 :
                // USE.g:1:184: T__72
                {
                mT__72(); if (state.failed) return ;

                }
                break;
            case 31 :
                // USE.g:1:190: T__73
                {
                mT__73(); if (state.failed) return ;

                }
                break;
            case 32 :
                // USE.g:1:196: T__74
                {
                mT__74(); if (state.failed) return ;

                }
                break;
            case 33 :
                // USE.g:1:202: T__75
                {
                mT__75(); if (state.failed) return ;

                }
                break;
            case 34 :
                // USE.g:1:208: T__76
                {
                mT__76(); if (state.failed) return ;

                }
                break;
            case 35 :
                // USE.g:1:214: T__77
                {
                mT__77(); if (state.failed) return ;

                }
                break;
            case 36 :
                // USE.g:1:220: T__78
                {
                mT__78(); if (state.failed) return ;

                }
                break;
            case 37 :
                // USE.g:1:226: T__79
                {
                mT__79(); if (state.failed) return ;

                }
                break;
            case 38 :
                // USE.g:1:232: T__80
                {
                mT__80(); if (state.failed) return ;

                }
                break;
            case 39 :
                // USE.g:1:238: T__81
                {
                mT__81(); if (state.failed) return ;

                }
                break;
            case 40 :
                // USE.g:1:244: T__82
                {
                mT__82(); if (state.failed) return ;

                }
                break;
            case 41 :
                // USE.g:1:250: T__83
                {
                mT__83(); if (state.failed) return ;

                }
                break;
            case 42 :
                // USE.g:1:256: T__84
                {
                mT__84(); if (state.failed) return ;

                }
                break;
            case 43 :
                // USE.g:1:262: T__85
                {
                mT__85(); if (state.failed) return ;

                }
                break;
            case 44 :
                // USE.g:1:268: T__86
                {
                mT__86(); if (state.failed) return ;

                }
                break;
            case 45 :
                // USE.g:1:274: T__87
                {
                mT__87(); if (state.failed) return ;

                }
                break;
            case 46 :
                // USE.g:1:280: T__88
                {
                mT__88(); if (state.failed) return ;

                }
                break;
            case 47 :
                // USE.g:1:286: T__89
                {
                mT__89(); if (state.failed) return ;

                }
                break;
            case 48 :
                // USE.g:1:292: T__90
                {
                mT__90(); if (state.failed) return ;

                }
                break;
            case 49 :
                // USE.g:1:298: T__91
                {
                mT__91(); if (state.failed) return ;

                }
                break;
            case 50 :
                // USE.g:1:304: T__92
                {
                mT__92(); if (state.failed) return ;

                }
                break;
            case 51 :
                // USE.g:1:310: T__93
                {
                mT__93(); if (state.failed) return ;

                }
                break;
            case 52 :
                // USE.g:1:316: T__94
                {
                mT__94(); if (state.failed) return ;

                }
                break;
            case 53 :
                // USE.g:1:322: T__95
                {
                mT__95(); if (state.failed) return ;

                }
                break;
            case 54 :
                // USE.g:1:328: T__96
                {
                mT__96(); if (state.failed) return ;

                }
                break;
            case 55 :
                // USE.g:1:334: T__97
                {
                mT__97(); if (state.failed) return ;

                }
                break;
            case 56 :
                // USE.g:1:340: T__98
                {
                mT__98(); if (state.failed) return ;

                }
                break;
            case 57 :
                // USE.g:1:346: T__99
                {
                mT__99(); if (state.failed) return ;

                }
                break;
            case 58 :
                // USE.g:1:352: T__100
                {
                mT__100(); if (state.failed) return ;

                }
                break;
            case 59 :
                // USE.g:1:359: T__101
                {
                mT__101(); if (state.failed) return ;

                }
                break;
            case 60 :
                // USE.g:1:366: T__102
                {
                mT__102(); if (state.failed) return ;

                }
                break;
            case 61 :
                // USE.g:1:373: T__103
                {
                mT__103(); if (state.failed) return ;

                }
                break;
            case 62 :
                // USE.g:1:380: T__104
                {
                mT__104(); if (state.failed) return ;

                }
                break;
            case 63 :
                // USE.g:1:387: T__105
                {
                mT__105(); if (state.failed) return ;

                }
                break;
            case 64 :
                // USE.g:1:394: T__106
                {
                mT__106(); if (state.failed) return ;

                }
                break;
            case 65 :
                // USE.g:1:401: T__107
                {
                mT__107(); if (state.failed) return ;

                }
                break;
            case 66 :
                // USE.g:1:408: T__108
                {
                mT__108(); if (state.failed) return ;

                }
                break;
            case 67 :
                // USE.g:1:415: T__109
                {
                mT__109(); if (state.failed) return ;

                }
                break;
            case 68 :
                // USE.g:1:422: T__110
                {
                mT__110(); if (state.failed) return ;

                }
                break;
            case 69 :
                // USE.g:1:429: T__111
                {
                mT__111(); if (state.failed) return ;

                }
                break;
            case 70 :
                // USE.g:1:436: WS
                {
                mWS(); if (state.failed) return ;

                }
                break;
            case 71 :
                // USE.g:1:439: SL_COMMENT
                {
                mSL_COMMENT(); if (state.failed) return ;

                }
                break;
            case 72 :
                // USE.g:1:450: ML_COMMENT
                {
                mML_COMMENT(); if (state.failed) return ;

                }
                break;
            case 73 :
                // USE.g:1:461: ARROW
                {
                mARROW(); if (state.failed) return ;

                }
                break;
            case 74 :
                // USE.g:1:467: AT
                {
                mAT(); if (state.failed) return ;

                }
                break;
            case 75 :
                // USE.g:1:470: BAR
                {
                mBAR(); if (state.failed) return ;

                }
                break;
            case 76 :
                // USE.g:1:474: COLON
                {
                mCOLON(); if (state.failed) return ;

                }
                break;
            case 77 :
                // USE.g:1:480: COLON_COLON
                {
                mCOLON_COLON(); if (state.failed) return ;

                }
                break;
            case 78 :
                // USE.g:1:492: COLON_EQUAL
                {
                mCOLON_EQUAL(); if (state.failed) return ;

                }
                break;
            case 79 :
                // USE.g:1:504: COMMA
                {
                mCOMMA(); if (state.failed) return ;

                }
                break;
            case 80 :
                // USE.g:1:510: DOT
                {
                mDOT(); if (state.failed) return ;

                }
                break;
            case 81 :
                // USE.g:1:514: DOTDOT
                {
                mDOTDOT(); if (state.failed) return ;

                }
                break;
            case 82 :
                // USE.g:1:521: EQUAL
                {
                mEQUAL(); if (state.failed) return ;

                }
                break;
            case 83 :
                // USE.g:1:527: GREATER
                {
                mGREATER(); if (state.failed) return ;

                }
                break;
            case 84 :
                // USE.g:1:535: GREATER_EQUAL
                {
                mGREATER_EQUAL(); if (state.failed) return ;

                }
                break;
            case 85 :
                // USE.g:1:549: HASH
                {
                mHASH(); if (state.failed) return ;

                }
                break;
            case 86 :
                // USE.g:1:554: LBRACE
                {
                mLBRACE(); if (state.failed) return ;

                }
                break;
            case 87 :
                // USE.g:1:561: LBRACK
                {
                mLBRACK(); if (state.failed) return ;

                }
                break;
            case 88 :
                // USE.g:1:568: LESS
                {
                mLESS(); if (state.failed) return ;

                }
                break;
            case 89 :
                // USE.g:1:573: LESS_EQUAL
                {
                mLESS_EQUAL(); if (state.failed) return ;

                }
                break;
            case 90 :
                // USE.g:1:584: LPAREN
                {
                mLPAREN(); if (state.failed) return ;

                }
                break;
            case 91 :
                // USE.g:1:591: MINUS
                {
                mMINUS(); if (state.failed) return ;

                }
                break;
            case 92 :
                // USE.g:1:597: NOT_EQUAL
                {
                mNOT_EQUAL(); if (state.failed) return ;

                }
                break;
            case 93 :
                // USE.g:1:607: PLUS
                {
                mPLUS(); if (state.failed) return ;

                }
                break;
            case 94 :
                // USE.g:1:612: RBRACE
                {
                mRBRACE(); if (state.failed) return ;

                }
                break;
            case 95 :
                // USE.g:1:619: RBRACK
                {
                mRBRACK(); if (state.failed) return ;

                }
                break;
            case 96 :
                // USE.g:1:626: RPAREN
                {
                mRPAREN(); if (state.failed) return ;

                }
                break;
            case 97 :
                // USE.g:1:633: SEMI
                {
                mSEMI(); if (state.failed) return ;

                }
                break;
            case 98 :
                // USE.g:1:638: SLASH
                {
                mSLASH(); if (state.failed) return ;

                }
                break;
            case 99 :
                // USE.g:1:644: STAR
                {
                mSTAR(); if (state.failed) return ;

                }
                break;
            case 100 :
                // USE.g:1:649: RANGE_OR_INT
                {
                mRANGE_OR_INT(); if (state.failed) return ;

                }
                break;
            case 101 :
                // USE.g:1:662: STRING
                {
                mSTRING(); if (state.failed) return ;

                }
                break;
            case 102 :
                // USE.g:1:669: IDENT
                {
                mIDENT(); if (state.failed) return ;

                }
                break;

        }

    }

    // $ANTLR start synpred1_USE
    public final void synpred1_USE_fragment() throws RecognitionException {   
        // USE.g:1191:7: ( INT '..' )
        // USE.g:1191:9: INT '..'
        {
        mINT(); if (state.failed) return ;
        match(".."); if (state.failed) return ;


        }
    }
    // $ANTLR end synpred1_USE

    // $ANTLR start synpred2_USE
    public final void synpred2_USE_fragment() throws RecognitionException {   
        // USE.g:1192:7: ( REAL )
        // USE.g:1192:9: REAL
        {
        mREAL(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_USE

    public final boolean synpred1_USE() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_USE_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_USE() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_USE_fragment(); // can never throw exception
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
        "\1\uffff\31\61\1\uffff\1\146\1\150\2\uffff\1\153\1\uffff\1\155"+
        "\1\uffff\1\157\3\uffff\1\162\12\uffff\16\61\1\u0085\6\61\1\u0090"+
        "\1\u0091\6\61\1\u009a\24\61\17\uffff\6\61\1\u00b8\7\61\1\u00c0\3"+
        "\61\1\uffff\6\61\1\u00cd\1\u00ce\2\61\2\uffff\2\61\1\u00d3\1\61"+
        "\1\u00d5\3\61\1\uffff\1\u00d9\1\61\1\u00db\2\61\1\u00de\5\61\1\u00e4"+
        "\1\u00e5\1\u00e6\1\61\1\u00e8\13\61\1\u00f4\1\61\1\uffff\2\61\1"+
        "\u00f8\4\61\1\uffff\11\61\1\u0106\2\61\2\uffff\1\61\1\u010a\2\61"+
        "\1\uffff\1\u010d\1\uffff\3\61\1\uffff\1\61\1\uffff\1\u0112\1\u0113"+
        "\1\uffff\1\61\1\u0115\1\u0116\1\61\1\u0118\3\uffff\1\61\1\uffff"+
        "\3\61\1\u011d\1\61\1\u011f\3\61\1\u0123\1\61\1\uffff\1\u0125\2\61"+
        "\1\uffff\14\61\1\u0135\1\uffff\3\61\1\uffff\2\61\1\uffff\4\61\2"+
        "\uffff\1\u013f\2\uffff\1\u0140\1\uffff\3\61\1\u0144\1\uffff\1\61"+
        "\1\uffff\3\61\1\uffff\1\u0149\1\uffff\17\61\1\uffff\2\61\1\u015b"+
        "\3\61\1\u015f\2\61\2\uffff\3\61\1\uffff\2\61\1\u0167\1\61\1\uffff"+
        "\1\61\1\u016a\6\61\1\u0171\5\61\1\u0177\1\61\1\u0179\1\uffff\1\u017a"+
        "\1\u017b\1\u017c\1\uffff\1\u017d\6\61\1\uffff\2\61\1\uffff\1\u0186"+
        "\5\61\1\uffff\3\61\1\u018f\1\61\1\uffff\1\61\5\uffff\1\u0192\1\u0193"+
        "\6\61\1\uffff\5\61\1\u019f\2\61\1\uffff\1\61\1\u01a3\2\uffff\1\61"+
        "\1\u01a5\4\61\1\u01aa\3\61\1\u01ae\1\uffff\3\61\1\uffff\1\u01b2"+
        "\1\uffff\1\u01b3\1\u01b4\1\u01b5\1\u01b6\1\uffff\1\u01b9\1\u01ba"+
        "\1\61\1\uffff\1\u01bc\1\u01bd\1\61\5\uffff\2\61\2\uffff\1\u01c1"+
        "\2\uffff\1\u01c2\2\61\2\uffff\4\61\1\u01c9\1\u01ca\2\uffff";
    static final String DFA18_eofS =
        "\u01cb\uffff";
    static final String DFA18_minS =
        "\1\11\1\157\2\154\1\142\1\143\3\145\1\146\1\157\1\141\1\145\2\141"+
        "\1\150\2\145\1\157\1\145\1\141\1\162\1\156\1\165\1\141\1\157\1\uffff"+
        "\1\52\1\55\2\uffff\1\72\1\uffff\1\56\1\uffff\1\75\3\uffff\1\75\12"+
        "\uffff\1\144\1\155\1\141\1\145\1\144\1\145\2\163\1\164\1\163\1\147"+
        "\1\144\1\154\1\145\1\60\1\154\1\147\1\154\1\144\1\142\1\164\2\60"+
        "\1\160\2\145\1\163\1\162\1\143\1\60\1\166\1\155\1\164\1\154\1\157"+
        "\1\162\1\154\1\145\1\165\1\151\1\156\1\164\1\162\1\161\1\147\2\144"+
        "\1\160\1\164\1\154\17\uffff\1\145\1\163\1\160\1\163\1\141\1\155"+
        "\1\60\1\163\1\143\1\145\1\164\1\162\1\157\1\162\1\60\1\111\1\162"+
        "\1\145\1\uffff\1\101\1\167\1\151\2\145\1\163\2\60\1\145\1\157\2"+
        "\uffff\1\154\1\162\1\60\1\164\1\60\1\154\1\145\1\164\1\uffff\1\60"+
        "\1\145\1\60\1\154\1\155\1\60\1\163\1\156\1\145\1\154\1\144\3\60"+
        "\1\165\1\60\2\145\1\154\1\145\2\154\1\164\1\145\1\157\1\163\1\164"+
        "\1\60\1\146\1\uffff\1\164\1\165\1\60\1\162\1\151\1\143\1\145\1\uffff"+
        "\1\156\1\141\1\162\2\163\1\155\1\156\1\145\1\156\1\60\1\146\1\145"+
        "\2\uffff\1\162\1\60\1\151\1\141\1\uffff\1\60\1\uffff\1\141\1\164"+
        "\1\162\1\uffff\1\150\1\uffff\2\60\1\uffff\1\145\2\60\1\145\1\60"+
        "\3\uffff\1\145\1\uffff\1\162\1\146\1\145\1\60\1\145\1\60\1\162\1"+
        "\170\1\163\1\60\1\145\1\uffff\1\60\1\145\1\164\1\uffff\1\141\1\142"+
        "\1\151\1\147\1\163\1\164\1\145\1\124\1\113\1\160\1\144\1\145\1\60"+
        "\1\uffff\1\151\2\164\1\uffff\1\145\1\164\1\uffff\1\162\1\145\1\157"+
        "\1\151\2\uffff\1\60\2\uffff\1\60\1\uffff\1\156\1\145\1\151\1\60"+
        "\1\uffff\1\143\1\uffff\1\141\1\164\1\151\1\uffff\1\60\1\uffff\1"+
        "\156\1\145\1\143\1\165\2\141\1\164\1\151\1\144\1\171\1\151\1\171"+
        "\1\164\1\145\1\156\1\uffff\1\156\1\163\1\60\1\163\2\145\1\60\1\171"+
        "\1\156\2\uffff\1\143\1\144\1\156\1\uffff\1\164\1\151\1\60\1\164"+
        "\1\uffff\1\164\1\60\4\164\1\141\1\157\1\60\1\160\1\156\1\160\1\171"+
        "\1\146\1\60\1\145\1\60\1\uffff\3\60\1\uffff\1\60\1\164\1\145\1\123"+
        "\1\145\1\151\1\156\1\uffff\2\151\1\uffff\1\60\1\145\2\151\2\156"+
        "\1\uffff\1\145\1\144\1\145\1\60\1\151\1\uffff\1\163\5\uffff\2\60"+
        "\1\145\1\144\1\157\1\164\1\157\1\141\1\uffff\1\163\2\157\1\143\1"+
        "\163\1\60\2\117\1\uffff\1\156\1\60\2\uffff\1\164\1\60\1\156\1\163"+
        "\1\156\1\154\1\60\2\156\1\145\1\60\1\uffff\2\146\1\145\1\uffff\1"+
        "\60\1\uffff\4\60\1\uffff\2\60\1\163\1\uffff\2\60\1\144\5\uffff\2"+
        "\154\2\uffff\1\60\2\uffff\1\60\2\141\2\uffff\4\163\2\60\2\uffff";
    static final String DFA18_maxS =
        "\1\175\1\157\1\162\1\170\1\164\1\162\1\145\1\157\1\165\1\164\1"+
        "\162\1\141\1\157\1\165\2\162\1\150\1\145\1\157\1\145\1\141\1\162"+
        "\1\156\1\165\1\141\1\157\1\uffff\1\57\1\76\2\uffff\1\75\1\uffff"+
        "\1\56\1\uffff\1\75\3\uffff\1\76\12\uffff\1\144\1\156\1\141\1\145"+
        "\1\165\1\151\2\163\1\164\1\163\1\147\1\144\1\154\1\145\1\172\1\154"+
        "\1\164\1\154\1\144\1\142\1\164\2\172\1\160\2\145\1\163\1\162\1\163"+
        "\1\172\1\166\1\155\1\164\1\154\1\157\1\162\1\154\1\145\1\165\1\151"+
        "\1\156\1\164\1\162\1\164\1\147\2\144\1\160\1\164\1\154\17\uffff"+
        "\1\145\1\164\1\160\1\163\1\141\1\155\1\172\1\163\1\143\1\145\1\164"+
        "\1\162\1\157\1\162\1\172\1\111\1\162\1\145\1\uffff\1\125\1\167\1"+
        "\151\2\145\1\163\2\172\1\145\1\157\2\uffff\1\154\1\162\1\172\1\164"+
        "\1\172\1\154\1\145\1\164\1\uffff\1\172\1\145\1\172\1\154\1\155\1"+
        "\172\1\163\1\156\1\145\1\154\1\144\3\172\1\165\1\172\2\145\1\154"+
        "\1\145\2\154\1\164\1\145\1\157\1\163\1\164\1\172\1\146\1\uffff\1"+
        "\164\1\165\1\172\1\162\1\151\1\143\1\145\1\uffff\1\156\1\141\1\162"+
        "\2\163\1\155\1\156\1\145\1\156\1\172\1\146\1\145\2\uffff\1\162\1"+
        "\172\1\151\1\141\1\uffff\1\172\1\uffff\1\141\1\164\1\162\1\uffff"+
        "\1\150\1\uffff\2\172\1\uffff\1\145\2\172\1\145\1\172\3\uffff\1\145"+
        "\1\uffff\1\162\1\146\1\145\1\172\1\145\1\172\1\162\1\170\1\163\1"+
        "\172\1\145\1\uffff\1\172\1\145\1\164\1\uffff\1\141\1\142\1\151\1"+
        "\147\1\163\1\164\1\145\2\124\1\160\1\144\1\145\1\172\1\uffff\1\151"+
        "\2\164\1\uffff\1\145\1\164\1\uffff\1\162\1\145\1\157\1\151\2\uffff"+
        "\1\172\2\uffff\1\172\1\uffff\1\156\1\145\1\151\1\172\1\uffff\1\143"+
        "\1\uffff\1\141\1\164\1\151\1\uffff\1\172\1\uffff\1\156\1\145\1\143"+
        "\1\165\2\141\1\164\1\151\1\144\1\171\1\151\1\171\1\164\1\145\1\156"+
        "\1\uffff\1\156\1\163\1\172\1\163\2\145\1\172\1\171\1\156\2\uffff"+
        "\1\143\1\144\1\156\1\uffff\1\164\1\151\1\172\1\164\1\uffff\1\164"+
        "\1\172\4\164\1\141\1\157\1\172\1\160\1\156\1\160\1\171\1\146\1\172"+
        "\1\145\1\172\1\uffff\3\172\1\uffff\1\172\1\164\1\145\1\123\1\145"+
        "\1\151\1\156\1\uffff\2\151\1\uffff\1\172\1\145\2\151\2\156\1\uffff"+
        "\1\145\1\144\1\145\1\172\1\151\1\uffff\1\163\5\uffff\2\172\1\145"+
        "\1\144\1\157\1\164\1\157\1\141\1\uffff\1\163\2\157\1\143\1\163\1"+
        "\172\2\117\1\uffff\1\156\1\172\2\uffff\1\164\1\172\1\156\1\163\1"+
        "\156\1\154\1\172\2\156\1\145\1\172\1\uffff\2\146\1\145\1\uffff\1"+
        "\172\1\uffff\4\172\1\uffff\2\172\1\163\1\uffff\2\172\1\144\5\uffff"+
        "\2\154\2\uffff\1\172\2\uffff\1\172\2\141\2\uffff\4\163\2\172\2\uffff";
    static final String DFA18_acceptS =
        "\32\uffff\1\106\2\uffff\1\112\1\113\1\uffff\1\117\1\uffff\1\122"+
        "\1\uffff\1\125\1\126\1\127\1\uffff\1\132\1\135\1\136\1\137\1\140"+
        "\1\141\1\143\1\144\1\145\1\146\62\uffff\1\107\1\110\1\142\1\111"+
        "\1\133\1\115\1\116\1\114\1\121\1\120\1\124\1\123\1\131\1\134\1\130"+
        "\22\uffff\1\57\12\uffff\1\53\1\43\10\uffff\1\50\35\uffff\1\10\7"+
        "\uffff\1\61\14\uffff\1\33\1\25\4\uffff\1\27\1\uffff\1\31\3\uffff"+
        "\1\62\1\uffff\1\63\2\uffff\1\52\5\uffff\1\55\1\60\1\73\1\uffff\1"+
        "\75\13\uffff\1\3\3\uffff\1\45\15\uffff\1\20\3\uffff\1\37\2\uffff"+
        "\1\30\4\uffff\1\102\1\41\1\uffff\1\44\1\71\1\uffff\1\51\4\uffff"+
        "\1\104\1\uffff\1\1\3\uffff\1\5\1\uffff\1\46\17\uffff\1\16\11\uffff"+
        "\1\72\1\47\3\uffff\1\103\4\uffff\1\34\21\uffff\1\36\3\uffff\1\40"+
        "\7\uffff\1\24\2\uffff\1\54\6\uffff\1\21\5\uffff\1\13\1\uffff\1\22"+
        "\1\56\1\65\1\32\1\42\10\uffff\1\4\10\uffff\1\77\2\uffff\1\35\1\74"+
        "\13\uffff\1\66\3\uffff\1\23\1\uffff\1\101\4\uffff\1\6\3\uffff\1"+
        "\7\3\uffff\1\76\1\105\1\2\1\15\1\26\2\uffff\1\17\1\14\1\uffff\1"+
        "\67\1\70\3\uffff\1\64\1\100\6\uffff\1\11\1\12";
    static final String DFA18_specialS =
        "\u01cb\uffff}>";
    static final String[] DFA18_transitionS = {
            "\2\32\1\uffff\2\32\22\uffff\1\32\2\uffff\1\44\1\61\2\uffff"+
            "\1\60\1\50\1\54\1\56\1\51\1\40\1\34\1\41\1\33\12\57\1\37\1\55"+
            "\1\47\1\42\1\43\1\uffff\1\35\1\61\1\24\1\31\1\30\12\61\1\25"+
            "\3\61\1\23\1\27\1\26\5\61\1\46\1\uffff\1\53\1\uffff\1\61\1\uffff"+
            "\1\4\1\6\1\2\1\14\1\3\1\16\2\61\1\11\2\61\1\21\1\1\1\15\1\5"+
            "\1\12\1\61\1\7\1\10\1\17\1\61\1\13\1\20\1\22\2\61\1\45\1\36"+
            "\1\52",
            "\1\62",
            "\1\64\2\uffff\1\63\2\uffff\1\65",
            "\1\70\1\uffff\1\66\11\uffff\1\67",
            "\1\71\4\uffff\1\74\4\uffff\1\76\1\uffff\1\75\4\uffff\1\73"+
            "\1\72",
            "\1\101\14\uffff\1\77\1\uffff\1\100",
            "\1\102",
            "\1\104\11\uffff\1\103",
            "\1\106\17\uffff\1\105",
            "\1\110\6\uffff\1\111\1\107\5\uffff\1\112",
            "\1\114\2\uffff\1\113",
            "\1\115",
            "\1\116\3\uffff\1\120\5\uffff\1\117",
            "\1\121\15\uffff\1\122\5\uffff\1\123",
            "\1\126\15\uffff\1\125\2\uffff\1\124",
            "\1\127\11\uffff\1\130",
            "\1\132\2\uffff\1\131",
            "\1\133",
            "\1\134",
            "\1\135",
            "\1\136",
            "\1\137",
            "\1\140",
            "\1\141",
            "\1\142",
            "\1\143",
            "",
            "\1\145\4\uffff\1\144",
            "\1\144\20\uffff\1\147",
            "",
            "",
            "\1\151\2\uffff\1\152",
            "",
            "\1\154",
            "",
            "\1\156",
            "",
            "",
            "",
            "\1\160\1\161",
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
            "\1\163",
            "\1\165\1\164",
            "\1\166",
            "\1\167",
            "\1\171\20\uffff\1\170",
            "\1\173\3\uffff\1\172",
            "\1\174",
            "\1\175",
            "\1\176",
            "\1\177",
            "\1\u0080",
            "\1\u0081",
            "\1\u0082",
            "\1\u0083",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\3\61\1\u0084\26"+
            "\61",
            "\1\u0086",
            "\1\u0088\14\uffff\1\u0087",
            "\1\u0089",
            "\1\u008a",
            "\1\u008b",
            "\1\u008c",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\22\61\1\u008e\1"+
            "\u008f\1\61\1\u008d\4\61",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u0092",
            "\1\u0093",
            "\1\u0094",
            "\1\u0095",
            "\1\u0096",
            "\1\u0097\10\uffff\1\u0098\6\uffff\1\u0099",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u009b",
            "\1\u009c",
            "\1\u009d",
            "\1\u009e",
            "\1\u009f",
            "\1\u00a0",
            "\1\u00a1",
            "\1\u00a2",
            "\1\u00a3",
            "\1\u00a4",
            "\1\u00a5",
            "\1\u00a6",
            "\1\u00a7",
            "\1\u00a9\2\uffff\1\u00a8",
            "\1\u00aa",
            "\1\u00ab",
            "\1\u00ac",
            "\1\u00ad",
            "\1\u00ae",
            "\1\u00af",
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
            "\1\u00b0",
            "\1\u00b1\1\u00b2",
            "\1\u00b3",
            "\1\u00b4",
            "\1\u00b5",
            "\1\u00b6",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\10\61\1\u00b7\21"+
            "\61",
            "\1\u00b9",
            "\1\u00ba",
            "\1\u00bb",
            "\1\u00bc",
            "\1\u00bd",
            "\1\u00be",
            "\1\u00bf",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u00c1",
            "\1\u00c2",
            "\1\u00c3",
            "",
            "\1\u00c4\3\uffff\1\u00c6\3\uffff\1\u00c5\13\uffff\1\u00c7",
            "\1\u00c8",
            "\1\u00c9",
            "\1\u00ca",
            "\1\u00cb",
            "\1\u00cc",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u00cf",
            "\1\u00d0",
            "",
            "",
            "\1\u00d1",
            "\1\u00d2",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u00d4",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u00d6",
            "\1\u00d7",
            "\1\u00d8",
            "",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u00da",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u00dc",
            "\1\u00dd",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u00df",
            "\1\u00e0",
            "\1\u00e1",
            "\1\u00e2",
            "\1\u00e3",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u00e7",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u00e9",
            "\1\u00ea",
            "\1\u00eb",
            "\1\u00ec",
            "\1\u00ed",
            "\1\u00ee",
            "\1\u00ef",
            "\1\u00f0",
            "\1\u00f1",
            "\1\u00f2",
            "\1\u00f3",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u00f5",
            "",
            "\1\u00f6",
            "\1\u00f7",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u00f9",
            "\1\u00fa",
            "\1\u00fb",
            "\1\u00fc",
            "",
            "\1\u00fd",
            "\1\u00fe",
            "\1\u00ff",
            "\1\u0100",
            "\1\u0101",
            "\1\u0102",
            "\1\u0103",
            "\1\u0104",
            "\1\u0105",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u0107",
            "\1\u0108",
            "",
            "",
            "\1\u0109",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u010b",
            "\1\u010c",
            "",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "",
            "\1\u010e",
            "\1\u010f",
            "\1\u0110",
            "",
            "\1\u0111",
            "",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "",
            "\1\u0114",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u0117",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "",
            "",
            "",
            "\1\u0119",
            "",
            "\1\u011a",
            "\1\u011b",
            "\1\u011c",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u011e",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u0120",
            "\1\u0121",
            "\1\u0122",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u0124",
            "",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u0126",
            "\1\u0127",
            "",
            "\1\u0128",
            "\1\u0129",
            "\1\u012a",
            "\1\u012b",
            "\1\u012c",
            "\1\u012d",
            "\1\u012e",
            "\1\u012f",
            "\1\u0130\10\uffff\1\u0131",
            "\1\u0132",
            "\1\u0133",
            "\1\u0134",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "",
            "\1\u0136",
            "\1\u0137",
            "\1\u0138",
            "",
            "\1\u0139",
            "\1\u013a",
            "",
            "\1\u013b",
            "\1\u013c",
            "\1\u013d",
            "\1\u013e",
            "",
            "",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "",
            "",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "",
            "\1\u0141",
            "\1\u0142",
            "\1\u0143",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "",
            "\1\u0145",
            "",
            "\1\u0146",
            "\1\u0147",
            "\1\u0148",
            "",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "",
            "\1\u014a",
            "\1\u014b",
            "\1\u014c",
            "\1\u014d",
            "\1\u014e",
            "\1\u014f",
            "\1\u0150",
            "\1\u0151",
            "\1\u0152",
            "\1\u0153",
            "\1\u0154",
            "\1\u0155",
            "\1\u0156",
            "\1\u0157",
            "\1\u0158",
            "",
            "\1\u0159",
            "\1\u015a",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u015c",
            "\1\u015d",
            "\1\u015e",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u0160",
            "\1\u0161",
            "",
            "",
            "\1\u0162",
            "\1\u0163",
            "\1\u0164",
            "",
            "\1\u0165",
            "\1\u0166",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u0168",
            "",
            "\1\u0169",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u016b",
            "\1\u016c",
            "\1\u016d",
            "\1\u016e",
            "\1\u016f",
            "\1\u0170",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u0172",
            "\1\u0173",
            "\1\u0174",
            "\1\u0175",
            "\1\u0176",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u0178",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u017e",
            "\1\u017f",
            "\1\u0180",
            "\1\u0181",
            "\1\u0182",
            "\1\u0183",
            "",
            "\1\u0184",
            "\1\u0185",
            "",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u0187",
            "\1\u0188",
            "\1\u0189",
            "\1\u018a",
            "\1\u018b",
            "",
            "\1\u018c",
            "\1\u018d",
            "\1\u018e",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u0190",
            "",
            "\1\u0191",
            "",
            "",
            "",
            "",
            "",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u0194",
            "\1\u0195",
            "\1\u0196",
            "\1\u0197",
            "\1\u0198",
            "\1\u0199",
            "",
            "\1\u019a",
            "\1\u019b",
            "\1\u019c",
            "\1\u019d",
            "\1\u019e",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u01a0",
            "\1\u01a1",
            "",
            "\1\u01a2",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "",
            "",
            "\1\u01a4",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u01a6",
            "\1\u01a7",
            "\1\u01a8",
            "\1\u01a9",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u01ab",
            "\1\u01ac",
            "\1\u01ad",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "",
            "\1\u01af",
            "\1\u01b0",
            "\1\u01b1",
            "",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "",
            "\12\61\7\uffff\2\61\1\u01b7\27\61\4\uffff\1\61\1\uffff\2\61"+
            "\1\u01b8\27\61",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u01bb",
            "",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u01be",
            "",
            "",
            "",
            "",
            "",
            "\1\u01bf",
            "\1\u01c0",
            "",
            "",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "",
            "",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\1\u01c3",
            "\1\u01c4",
            "",
            "",
            "\1\u01c5",
            "\1\u01c6",
            "\1\u01c7",
            "\1\u01c8",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
            "\12\61\7\uffff\32\61\4\uffff\1\61\1\uffff\32\61",
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
            return "1:1: Tokens : ( T__43 | T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | T__105 | T__106 | T__107 | T__108 | T__109 | T__110 | T__111 | WS | SL_COMMENT | ML_COMMENT | ARROW | AT | BAR | COLON | COLON_COLON | COLON_EQUAL | COMMA | DOT | DOTDOT | EQUAL | GREATER | GREATER_EQUAL | HASH | LBRACE | LBRACK | LESS | LESS_EQUAL | LPAREN | MINUS | NOT_EQUAL | PLUS | RBRACE | RBRACK | RPAREN | SEMI | SLASH | STAR | RANGE_OR_INT | STRING | IDENT );";
        }
    }
 

}