// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 Soil.g 2010-09-07 17:10:47

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
 
package org.tzi.use.parser.soil;

import org.tzi.use.parser.ParseErrorHandler;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all") public class SoilLexer extends Lexer {
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__64=64;
    public static final int STAR=20;
    public static final int LBRACK=25;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int ESC=41;
    public static final int LBRACE=31;
    public static final int DOTDOT=33;
    public static final int T__61=61;
    public static final int T__60=60;
    public static final int EOF=-1;
    public static final int LPAREN=7;
    public static final int AT=23;
    public static final int T__55=55;
    public static final int ML_COMMENT=37;
    public static final int T__56=56;
    public static final int RPAREN=8;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int SLASH=21;
    public static final int GREATER=15;
    public static final int COLON_EQUAL=6;
    public static final int T__51=51;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int NOT_EQUAL=13;
    public static final int COMMA=10;
    public static final int T__59=59;
    public static final int EQUAL=12;
    public static final int LESS=14;
    public static final int IDENT=5;
    public static final int PLUS=18;
    public static final int RANGE_OR_INT=40;
    public static final int DOT=9;
    public static final int T__50=50;
    public static final int SCRIPTBODY=39;
    public static final int RBRACK=26;
    public static final int T__80=80;
    public static final int T__46=46;
    public static final int T__81=81;
    public static final int T__47=47;
    public static final int T__82=82;
    public static final int T__44=44;
    public static final int RBRACE=32;
    public static final int T__83=83;
    public static final int T__45=45;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int HASH=30;
    public static final int HEX_DIGIT=42;
    public static final int INT=27;
    public static final int COLON_COLON=38;
    public static final int MINUS=19;
    public static final int T__84=84;
    public static final int SEMI=4;
    public static final int COLON=11;
    public static final int REAL=28;
    public static final int T__71=71;
    public static final int WS=35;
    public static final int T__72=72;
    public static final int NEWLINE=34;
    public static final int T__70=70;
    public static final int SL_COMMENT=36;
    public static final int VOCAB=43;
    public static final int ARROW=22;
    public static final int LESS_EQUAL=16;
    public static final int T__76=76;
    public static final int T__75=75;
    public static final int T__74=74;
    public static final int T__73=73;
    public static final int GREATER_EQUAL=17;
    public static final int BAR=24;
    public static final int T__79=79;
    public static final int T__78=78;
    public static final int STRING=29;
    public static final int T__77=77;

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

    public SoilLexer() {;} 
    public SoilLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public SoilLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "Soil.g"; }

    // $ANTLR start "T__44"
    public final void mT__44() throws RecognitionException {
        try {
            int _type = T__44;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Soil.g:43:7: ( 'new' )
            // Soil.g:43:9: 'new'
            {
            match("new"); if (state.failed) return ;


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
            // Soil.g:44:7: ( 'between' )
            // Soil.g:44:9: 'between'
            {
            match("between"); if (state.failed) return ;


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
            // Soil.g:45:7: ( 'destroy' )
            // Soil.g:45:9: 'destroy'
            {
            match("destroy"); if (state.failed) return ;


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
            // Soil.g:46:7: ( 'insert' )
            // Soil.g:46:9: 'insert'
            {
            match("insert"); if (state.failed) return ;


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
            // Soil.g:47:7: ( 'into' )
            // Soil.g:47:9: 'into'
            {
            match("into"); if (state.failed) return ;


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
            // Soil.g:48:7: ( 'delete' )
            // Soil.g:48:9: 'delete'
            {
            match("delete"); if (state.failed) return ;


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
            // Soil.g:49:7: ( 'from' )
            // Soil.g:49:9: 'from'
            {
            match("from"); if (state.failed) return ;


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
            // Soil.g:50:7: ( 'if' )
            // Soil.g:50:9: 'if'
            {
            match("if"); if (state.failed) return ;


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
            // Soil.g:51:7: ( 'then' )
            // Soil.g:51:9: 'then'
            {
            match("then"); if (state.failed) return ;


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
            // Soil.g:52:7: ( 'else' )
            // Soil.g:52:9: 'else'
            {
            match("else"); if (state.failed) return ;


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
            // Soil.g:53:7: ( 'end' )
            // Soil.g:53:9: 'end'
            {
            match("end"); if (state.failed) return ;


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
            // Soil.g:54:7: ( 'for' )
            // Soil.g:54:9: 'for'
            {
            match("for"); if (state.failed) return ;


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
            // Soil.g:55:7: ( 'in' )
            // Soil.g:55:9: 'in'
            {
            match("in"); if (state.failed) return ;


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
            // Soil.g:56:7: ( 'do' )
            // Soil.g:56:9: 'do'
            {
            match("do"); if (state.failed) return ;


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
            // Soil.g:57:7: ( 'let' )
            // Soil.g:57:9: 'let'
            {
            match("let"); if (state.failed) return ;


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
            // Soil.g:58:7: ( 'implies' )
            // Soil.g:58:9: 'implies'
            {
            match("implies"); if (state.failed) return ;


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
            // Soil.g:59:7: ( 'or' )
            // Soil.g:59:9: 'or'
            {
            match("or"); if (state.failed) return ;


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
            // Soil.g:60:7: ( 'xor' )
            // Soil.g:60:9: 'xor'
            {
            match("xor"); if (state.failed) return ;


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
            // Soil.g:61:7: ( 'and' )
            // Soil.g:61:9: 'and'
            {
            match("and"); if (state.failed) return ;


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
            // Soil.g:62:7: ( 'div' )
            // Soil.g:62:9: 'div'
            {
            match("div"); if (state.failed) return ;


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
            // Soil.g:63:7: ( 'not' )
            // Soil.g:63:9: 'not'
            {
            match("not"); if (state.failed) return ;


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
            // Soil.g:64:7: ( 'allInstances' )
            // Soil.g:64:9: 'allInstances'
            {
            match("allInstances"); if (state.failed) return ;


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
            // Soil.g:65:7: ( 'pre' )
            // Soil.g:65:9: 'pre'
            {
            match("pre"); if (state.failed) return ;


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
            // Soil.g:66:7: ( 'iterate' )
            // Soil.g:66:9: 'iterate'
            {
            match("iterate"); if (state.failed) return ;


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
            // Soil.g:67:7: ( 'oclAsType' )
            // Soil.g:67:9: 'oclAsType'
            {
            match("oclAsType"); if (state.failed) return ;


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
            // Soil.g:68:7: ( 'oclIsKindOf' )
            // Soil.g:68:9: 'oclIsKindOf'
            {
            match("oclIsKindOf"); if (state.failed) return ;


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
            // Soil.g:69:7: ( 'oclIsTypeOf' )
            // Soil.g:69:9: 'oclIsTypeOf'
            {
            match("oclIsTypeOf"); if (state.failed) return ;


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
            // Soil.g:70:7: ( 'endif' )
            // Soil.g:70:9: 'endif'
            {
            match("endif"); if (state.failed) return ;


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
            // Soil.g:71:7: ( 'true' )
            // Soil.g:71:9: 'true'
            {
            match("true"); if (state.failed) return ;


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
            // Soil.g:72:7: ( 'false' )
            // Soil.g:72:9: 'false'
            {
            match("false"); if (state.failed) return ;


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
            // Soil.g:73:7: ( 'Set' )
            // Soil.g:73:9: 'Set'
            {
            match("Set"); if (state.failed) return ;


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
            // Soil.g:74:7: ( 'Sequence' )
            // Soil.g:74:9: 'Sequence'
            {
            match("Sequence"); if (state.failed) return ;


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
            // Soil.g:75:7: ( 'Bag' )
            // Soil.g:75:9: 'Bag'
            {
            match("Bag"); if (state.failed) return ;


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
            // Soil.g:76:7: ( 'OrderedSet' )
            // Soil.g:76:9: 'OrderedSet'
            {
            match("OrderedSet"); if (state.failed) return ;


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
            // Soil.g:77:7: ( 'oclEmpty' )
            // Soil.g:77:9: 'oclEmpty'
            {
            match("oclEmpty"); if (state.failed) return ;


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
            // Soil.g:78:7: ( 'oclUndefined' )
            // Soil.g:78:9: 'oclUndefined'
            {
            match("oclUndefined"); if (state.failed) return ;


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
            // Soil.g:79:7: ( 'Undefined' )
            // Soil.g:79:9: 'Undefined'
            {
            match("Undefined"); if (state.failed) return ;


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
            // Soil.g:80:7: ( 'null' )
            // Soil.g:80:9: 'null'
            {
            match("null"); if (state.failed) return ;


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
            // Soil.g:81:7: ( 'Tuple' )
            // Soil.g:81:9: 'Tuple'
            {
            match("Tuple"); if (state.failed) return ;


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
            // Soil.g:82:7: ( 'Date' )
            // Soil.g:82:9: 'Date'
            {
            match("Date"); if (state.failed) return ;


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
            // Soil.g:83:7: ( 'Collection' )
            // Soil.g:83:9: 'Collection'
            {
            match("Collection"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__84"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Soil.g:1279:3: ( ( ' ' | '\\t' | '\\f' | NEWLINE ) )
            // Soil.g:1280:5: ( ' ' | '\\t' | '\\f' | NEWLINE )
            {
            // Soil.g:1280:5: ( ' ' | '\\t' | '\\f' | NEWLINE )
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
                    // Soil.g:1280:7: ' '
                    {
                    match(' '); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // Soil.g:1281:7: '\\t'
                    {
                    match('\t'); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // Soil.g:1282:7: '\\f'
                    {
                    match('\f'); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // Soil.g:1283:7: NEWLINE
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
            // Soil.g:1289:11: ( ( '//' | '--' ) (~ ( '\\n' | '\\r' ) )* NEWLINE )
            // Soil.g:1290:5: ( '//' | '--' ) (~ ( '\\n' | '\\r' ) )* NEWLINE
            {
            // Soil.g:1290:5: ( '//' | '--' )
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
                    // Soil.g:1290:6: '//'
                    {
                    match("//"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // Soil.g:1290:13: '--'
                    {
                    match("--"); if (state.failed) return ;


                    }
                    break;

            }

            // Soil.g:1291:5: (~ ( '\\n' | '\\r' ) )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='\u0000' && LA3_0<='\t')||(LA3_0>='\u000B' && LA3_0<='\f')||(LA3_0>='\u000E' && LA3_0<='\uFFFF')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // Soil.g:1291:6: ~ ( '\\n' | '\\r' )
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
            // Soil.g:1296:11: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // Soil.g:1297:5: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); if (state.failed) return ;

            // Soil.g:1297:10: ( options {greedy=false; } : . )*
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
            	    // Soil.g:1297:38: .
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
            // Soil.g:1300:9: ( '\\r\\n' | '\\r' | '\\n' )
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
                    // Soil.g:1301:5: '\\r\\n'
                    {
                    match("\r\n"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // Soil.g:1301:14: '\\r'
                    {
                    match('\r'); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // Soil.g:1301:21: '\\n'
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
            // Soil.g:1304:10: ( '->' )
            // Soil.g:1304:12: '->'
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
            // Soil.g:1305:11: ( '@' )
            // Soil.g:1305:13: '@'
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
            // Soil.g:1306:8: ( '|' )
            // Soil.g:1306:10: '|'
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
            // Soil.g:1307:10: ( ':' )
            // Soil.g:1307:12: ':'
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
            // Soil.g:1308:14: ( '::' )
            // Soil.g:1308:16: '::'
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
            // Soil.g:1309:14: ( ':=' )
            // Soil.g:1309:16: ':='
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
            // Soil.g:1310:10: ( ',' )
            // Soil.g:1310:12: ','
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
            // Soil.g:1311:8: ( '.' )
            // Soil.g:1311:10: '.'
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
            // Soil.g:1312:11: ( '..' )
            // Soil.g:1312:13: '..'
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
            // Soil.g:1313:10: ( '=' )
            // Soil.g:1313:12: '='
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
            // Soil.g:1314:11: ( '>' )
            // Soil.g:1314:13: '>'
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
            // Soil.g:1315:15: ( '>=' )
            // Soil.g:1315:17: '>='
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
            // Soil.g:1316:9: ( '#' )
            // Soil.g:1316:11: '#'
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
            // Soil.g:1317:11: ( '{' )
            // Soil.g:1317:13: '{'
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
            // Soil.g:1318:11: ( '[' )
            // Soil.g:1318:13: '['
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
            // Soil.g:1319:9: ( '<' )
            // Soil.g:1319:11: '<'
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
            // Soil.g:1320:14: ( '<=' )
            // Soil.g:1320:16: '<='
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
            // Soil.g:1321:11: ( '(' )
            // Soil.g:1321:13: '('
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
            // Soil.g:1322:10: ( '-' )
            // Soil.g:1322:12: '-'
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
            // Soil.g:1323:13: ( '<>' )
            // Soil.g:1323:15: '<>'
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
            // Soil.g:1324:9: ( '+' )
            // Soil.g:1324:11: '+'
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
            // Soil.g:1325:11: ( '}' )
            // Soil.g:1325:13: '}'
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
            // Soil.g:1326:11: ( ']' )
            // Soil.g:1326:13: ']'
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
            // Soil.g:1327:10: ( ')' )
            // Soil.g:1327:12: ')'
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
            // Soil.g:1328:8: ( ';' )
            // Soil.g:1328:10: ';'
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
            // Soil.g:1329:10: ( '/' )
            // Soil.g:1329:12: '/'
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
            // Soil.g:1330:9: ( '*' )
            // Soil.g:1330:11: '*'
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

    // $ANTLR start "SCRIPTBODY"
    public final void mSCRIPTBODY() throws RecognitionException {
        try {
            int _type = SCRIPTBODY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Soil.g:1332:11: ( '<<' ( options {greedy=false; } : . )* '>>' )
            // Soil.g:1333:3: '<<' ( options {greedy=false; } : . )* '>>'
            {
            match("<<"); if (state.failed) return ;

            // Soil.g:1333:8: ( options {greedy=false; } : . )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0=='>') ) {
                    int LA6_1 = input.LA(2);

                    if ( (LA6_1=='>') ) {
                        alt6=2;
                    }
                    else if ( ((LA6_1>='\u0000' && LA6_1<='=')||(LA6_1>='?' && LA6_1<='\uFFFF')) ) {
                        alt6=1;
                    }


                }
                else if ( ((LA6_0>='\u0000' && LA6_0<='=')||(LA6_0>='?' && LA6_0<='\uFFFF')) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // Soil.g:1333:36: .
            	    {
            	    matchAny(); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            match(">>"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SCRIPTBODY"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            // Soil.g:1336:4: ( ( '0' .. '9' )+ )
            // Soil.g:1337:5: ( '0' .. '9' )+
            {
            // Soil.g:1337:5: ( '0' .. '9' )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='0' && LA7_0<='9')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // Soil.g:1337:6: '0' .. '9'
            	    {
            	    matchRange('0','9'); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
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
            // Soil.g:1341:5: ( INT ( '.' INT ( ( 'e' | 'E' ) ( '+' | '-' )? INT )? | ( 'e' | 'E' ) ( '+' | '-' )? INT ) )
            // Soil.g:1342:5: INT ( '.' INT ( ( 'e' | 'E' ) ( '+' | '-' )? INT )? | ( 'e' | 'E' ) ( '+' | '-' )? INT )
            {
            mINT(); if (state.failed) return ;
            // Soil.g:1342:9: ( '.' INT ( ( 'e' | 'E' ) ( '+' | '-' )? INT )? | ( 'e' | 'E' ) ( '+' | '-' )? INT )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0=='.') ) {
                alt11=1;
            }
            else if ( (LA11_0=='E'||LA11_0=='e') ) {
                alt11=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // Soil.g:1342:10: '.' INT ( ( 'e' | 'E' ) ( '+' | '-' )? INT )?
                    {
                    match('.'); if (state.failed) return ;
                    mINT(); if (state.failed) return ;
                    // Soil.g:1342:18: ( ( 'e' | 'E' ) ( '+' | '-' )? INT )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0=='E'||LA9_0=='e') ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // Soil.g:1342:19: ( 'e' | 'E' ) ( '+' | '-' )? INT
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

                            // Soil.g:1342:31: ( '+' | '-' )?
                            int alt8=2;
                            int LA8_0 = input.LA(1);

                            if ( (LA8_0=='+'||LA8_0=='-') ) {
                                alt8=1;
                            }
                            switch (alt8) {
                                case 1 :
                                    // Soil.g:
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
                    // Soil.g:1342:52: ( 'e' | 'E' ) ( '+' | '-' )? INT
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

                    // Soil.g:1342:64: ( '+' | '-' )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0=='+'||LA10_0=='-') ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // Soil.g:
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
            // Soil.g:1345:13: ( ( INT '..' )=> INT | ( REAL )=> REAL | INT )
            int alt12=3;
            int LA12_0 = input.LA(1);

            if ( ((LA12_0>='0' && LA12_0<='9')) ) {
                int LA12_1 = input.LA(2);

                if ( (LA12_1=='.') && (synpred2_Soil())) {
                    alt12=2;
                }
                else if ( (LA12_1=='E'||LA12_1=='e') && (synpred2_Soil())) {
                    alt12=2;
                }
                else if ( ((LA12_1>='0' && LA12_1<='9')) && (synpred2_Soil())) {
                    alt12=2;
                }
                else if ( (synpred1_Soil()) ) {
                    alt12=1;
                }
                else if ( (true) ) {
                    alt12=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 12, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // Soil.g:1346:7: ( INT '..' )=> INT
                    {
                    mINT(); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       _type=INT; 
                    }

                    }
                    break;
                case 2 :
                    // Soil.g:1347:7: ( REAL )=> REAL
                    {
                    mREAL(); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       _type=REAL; 
                    }

                    }
                    break;
                case 3 :
                    // Soil.g:1348:9: INT
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
            // Soil.g:1353:7: ( '\\'' (~ ( '\\'' | '\\\\' ) | ESC )* '\\'' )
            // Soil.g:1354:5: '\\'' (~ ( '\\'' | '\\\\' ) | ESC )* '\\''
            {
            match('\''); if (state.failed) return ;
            // Soil.g:1354:10: (~ ( '\\'' | '\\\\' ) | ESC )*
            loop13:
            do {
                int alt13=3;
                int LA13_0 = input.LA(1);

                if ( ((LA13_0>='\u0000' && LA13_0<='&')||(LA13_0>='(' && LA13_0<='[')||(LA13_0>=']' && LA13_0<='\uFFFF')) ) {
                    alt13=1;
                }
                else if ( (LA13_0=='\\') ) {
                    alt13=2;
                }


                switch (alt13) {
            	case 1 :
            	    // Soil.g:1354:12: ~ ( '\\'' | '\\\\' )
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
            	    // Soil.g:1354:27: ESC
            	    {
            	    mESC(); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop13;
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
            // Soil.g:1366:1: ( '\\\\' ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT | '0' .. '3' ( '0' .. '7' ( '0' .. '7' )? )? | '4' .. '7' ( '0' .. '7' )? ) )
            // Soil.g:1367:5: '\\\\' ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT | '0' .. '3' ( '0' .. '7' ( '0' .. '7' )? )? | '4' .. '7' ( '0' .. '7' )? )
            {
            match('\\'); if (state.failed) return ;
            // Soil.g:1368:6: ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT | '0' .. '3' ( '0' .. '7' ( '0' .. '7' )? )? | '4' .. '7' ( '0' .. '7' )? )
            int alt17=11;
            switch ( input.LA(1) ) {
            case 'n':
                {
                alt17=1;
                }
                break;
            case 'r':
                {
                alt17=2;
                }
                break;
            case 't':
                {
                alt17=3;
                }
                break;
            case 'b':
                {
                alt17=4;
                }
                break;
            case 'f':
                {
                alt17=5;
                }
                break;
            case '\"':
                {
                alt17=6;
                }
                break;
            case '\'':
                {
                alt17=7;
                }
                break;
            case '\\':
                {
                alt17=8;
                }
                break;
            case 'u':
                {
                alt17=9;
                }
                break;
            case '0':
            case '1':
            case '2':
            case '3':
                {
                alt17=10;
                }
                break;
            case '4':
            case '5':
            case '6':
            case '7':
                {
                alt17=11;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }

            switch (alt17) {
                case 1 :
                    // Soil.g:1368:8: 'n'
                    {
                    match('n'); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // Soil.g:1369:8: 'r'
                    {
                    match('r'); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // Soil.g:1370:8: 't'
                    {
                    match('t'); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // Soil.g:1371:8: 'b'
                    {
                    match('b'); if (state.failed) return ;

                    }
                    break;
                case 5 :
                    // Soil.g:1372:8: 'f'
                    {
                    match('f'); if (state.failed) return ;

                    }
                    break;
                case 6 :
                    // Soil.g:1373:8: '\"'
                    {
                    match('\"'); if (state.failed) return ;

                    }
                    break;
                case 7 :
                    // Soil.g:1374:8: '\\''
                    {
                    match('\''); if (state.failed) return ;

                    }
                    break;
                case 8 :
                    // Soil.g:1375:8: '\\\\'
                    {
                    match('\\'); if (state.failed) return ;

                    }
                    break;
                case 9 :
                    // Soil.g:1376:8: 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
                    {
                    match('u'); if (state.failed) return ;
                    mHEX_DIGIT(); if (state.failed) return ;
                    mHEX_DIGIT(); if (state.failed) return ;
                    mHEX_DIGIT(); if (state.failed) return ;
                    mHEX_DIGIT(); if (state.failed) return ;

                    }
                    break;
                case 10 :
                    // Soil.g:1377:8: '0' .. '3' ( '0' .. '7' ( '0' .. '7' )? )?
                    {
                    matchRange('0','3'); if (state.failed) return ;
                    // Soil.g:1377:17: ( '0' .. '7' ( '0' .. '7' )? )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( ((LA15_0>='0' && LA15_0<='7')) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // Soil.g:1377:18: '0' .. '7' ( '0' .. '7' )?
                            {
                            matchRange('0','7'); if (state.failed) return ;
                            // Soil.g:1377:27: ( '0' .. '7' )?
                            int alt14=2;
                            int LA14_0 = input.LA(1);

                            if ( ((LA14_0>='0' && LA14_0<='7')) ) {
                                alt14=1;
                            }
                            switch (alt14) {
                                case 1 :
                                    // Soil.g:1377:28: '0' .. '7'
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
                    // Soil.g:1377:45: '4' .. '7' ( '0' .. '7' )?
                    {
                    matchRange('4','7'); if (state.failed) return ;
                    // Soil.g:1377:54: ( '0' .. '7' )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( ((LA16_0>='0' && LA16_0<='7')) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // Soil.g:1377:55: '0' .. '7'
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
            // Soil.g:1383:10: ( ( '0' .. '9' | 'A' .. 'F' | 'a' .. 'f' ) )
            // Soil.g:1384:5: ( '0' .. '9' | 'A' .. 'F' | 'a' .. 'f' )
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
            // Soil.g:1391:6: ( ( '$' | 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // Soil.g:1392:5: ( '$' | 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
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

            // Soil.g:1392:39: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>='0' && LA18_0<='9')||(LA18_0>='A' && LA18_0<='Z')||LA18_0=='_'||(LA18_0>='a' && LA18_0<='z')) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // Soil.g:
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
            	    break loop18;
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
            // Soil.g:1399:6: ( '\\U0003' .. '\\U0377' )
            // Soil.g:1400:5: '\\U0003' .. '\\U0377'
            {
            matchRange('\u0003','\u0377'); if (state.failed) return ;

            }

        }
        finally {
        }
    }
    // $ANTLR end "VOCAB"

    public void mTokens() throws RecognitionException {
        // Soil.g:1:8: ( T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | WS | SL_COMMENT | ML_COMMENT | ARROW | AT | BAR | COLON | COLON_COLON | COLON_EQUAL | COMMA | DOT | DOTDOT | EQUAL | GREATER | GREATER_EQUAL | HASH | LBRACE | LBRACK | LESS | LESS_EQUAL | LPAREN | MINUS | NOT_EQUAL | PLUS | RBRACE | RBRACK | RPAREN | SEMI | SLASH | STAR | SCRIPTBODY | RANGE_OR_INT | STRING | IDENT )
        int alt19=75;
        alt19 = dfa19.predict(input);
        switch (alt19) {
            case 1 :
                // Soil.g:1:10: T__44
                {
                mT__44(); if (state.failed) return ;

                }
                break;
            case 2 :
                // Soil.g:1:16: T__45
                {
                mT__45(); if (state.failed) return ;

                }
                break;
            case 3 :
                // Soil.g:1:22: T__46
                {
                mT__46(); if (state.failed) return ;

                }
                break;
            case 4 :
                // Soil.g:1:28: T__47
                {
                mT__47(); if (state.failed) return ;

                }
                break;
            case 5 :
                // Soil.g:1:34: T__48
                {
                mT__48(); if (state.failed) return ;

                }
                break;
            case 6 :
                // Soil.g:1:40: T__49
                {
                mT__49(); if (state.failed) return ;

                }
                break;
            case 7 :
                // Soil.g:1:46: T__50
                {
                mT__50(); if (state.failed) return ;

                }
                break;
            case 8 :
                // Soil.g:1:52: T__51
                {
                mT__51(); if (state.failed) return ;

                }
                break;
            case 9 :
                // Soil.g:1:58: T__52
                {
                mT__52(); if (state.failed) return ;

                }
                break;
            case 10 :
                // Soil.g:1:64: T__53
                {
                mT__53(); if (state.failed) return ;

                }
                break;
            case 11 :
                // Soil.g:1:70: T__54
                {
                mT__54(); if (state.failed) return ;

                }
                break;
            case 12 :
                // Soil.g:1:76: T__55
                {
                mT__55(); if (state.failed) return ;

                }
                break;
            case 13 :
                // Soil.g:1:82: T__56
                {
                mT__56(); if (state.failed) return ;

                }
                break;
            case 14 :
                // Soil.g:1:88: T__57
                {
                mT__57(); if (state.failed) return ;

                }
                break;
            case 15 :
                // Soil.g:1:94: T__58
                {
                mT__58(); if (state.failed) return ;

                }
                break;
            case 16 :
                // Soil.g:1:100: T__59
                {
                mT__59(); if (state.failed) return ;

                }
                break;
            case 17 :
                // Soil.g:1:106: T__60
                {
                mT__60(); if (state.failed) return ;

                }
                break;
            case 18 :
                // Soil.g:1:112: T__61
                {
                mT__61(); if (state.failed) return ;

                }
                break;
            case 19 :
                // Soil.g:1:118: T__62
                {
                mT__62(); if (state.failed) return ;

                }
                break;
            case 20 :
                // Soil.g:1:124: T__63
                {
                mT__63(); if (state.failed) return ;

                }
                break;
            case 21 :
                // Soil.g:1:130: T__64
                {
                mT__64(); if (state.failed) return ;

                }
                break;
            case 22 :
                // Soil.g:1:136: T__65
                {
                mT__65(); if (state.failed) return ;

                }
                break;
            case 23 :
                // Soil.g:1:142: T__66
                {
                mT__66(); if (state.failed) return ;

                }
                break;
            case 24 :
                // Soil.g:1:148: T__67
                {
                mT__67(); if (state.failed) return ;

                }
                break;
            case 25 :
                // Soil.g:1:154: T__68
                {
                mT__68(); if (state.failed) return ;

                }
                break;
            case 26 :
                // Soil.g:1:160: T__69
                {
                mT__69(); if (state.failed) return ;

                }
                break;
            case 27 :
                // Soil.g:1:166: T__70
                {
                mT__70(); if (state.failed) return ;

                }
                break;
            case 28 :
                // Soil.g:1:172: T__71
                {
                mT__71(); if (state.failed) return ;

                }
                break;
            case 29 :
                // Soil.g:1:178: T__72
                {
                mT__72(); if (state.failed) return ;

                }
                break;
            case 30 :
                // Soil.g:1:184: T__73
                {
                mT__73(); if (state.failed) return ;

                }
                break;
            case 31 :
                // Soil.g:1:190: T__74
                {
                mT__74(); if (state.failed) return ;

                }
                break;
            case 32 :
                // Soil.g:1:196: T__75
                {
                mT__75(); if (state.failed) return ;

                }
                break;
            case 33 :
                // Soil.g:1:202: T__76
                {
                mT__76(); if (state.failed) return ;

                }
                break;
            case 34 :
                // Soil.g:1:208: T__77
                {
                mT__77(); if (state.failed) return ;

                }
                break;
            case 35 :
                // Soil.g:1:214: T__78
                {
                mT__78(); if (state.failed) return ;

                }
                break;
            case 36 :
                // Soil.g:1:220: T__79
                {
                mT__79(); if (state.failed) return ;

                }
                break;
            case 37 :
                // Soil.g:1:226: T__80
                {
                mT__80(); if (state.failed) return ;

                }
                break;
            case 38 :
                // Soil.g:1:232: T__81
                {
                mT__81(); if (state.failed) return ;

                }
                break;
            case 39 :
                // Soil.g:1:238: T__82
                {
                mT__82(); if (state.failed) return ;

                }
                break;
            case 40 :
                // Soil.g:1:244: T__83
                {
                mT__83(); if (state.failed) return ;

                }
                break;
            case 41 :
                // Soil.g:1:250: T__84
                {
                mT__84(); if (state.failed) return ;

                }
                break;
            case 42 :
                // Soil.g:1:256: WS
                {
                mWS(); if (state.failed) return ;

                }
                break;
            case 43 :
                // Soil.g:1:259: SL_COMMENT
                {
                mSL_COMMENT(); if (state.failed) return ;

                }
                break;
            case 44 :
                // Soil.g:1:270: ML_COMMENT
                {
                mML_COMMENT(); if (state.failed) return ;

                }
                break;
            case 45 :
                // Soil.g:1:281: ARROW
                {
                mARROW(); if (state.failed) return ;

                }
                break;
            case 46 :
                // Soil.g:1:287: AT
                {
                mAT(); if (state.failed) return ;

                }
                break;
            case 47 :
                // Soil.g:1:290: BAR
                {
                mBAR(); if (state.failed) return ;

                }
                break;
            case 48 :
                // Soil.g:1:294: COLON
                {
                mCOLON(); if (state.failed) return ;

                }
                break;
            case 49 :
                // Soil.g:1:300: COLON_COLON
                {
                mCOLON_COLON(); if (state.failed) return ;

                }
                break;
            case 50 :
                // Soil.g:1:312: COLON_EQUAL
                {
                mCOLON_EQUAL(); if (state.failed) return ;

                }
                break;
            case 51 :
                // Soil.g:1:324: COMMA
                {
                mCOMMA(); if (state.failed) return ;

                }
                break;
            case 52 :
                // Soil.g:1:330: DOT
                {
                mDOT(); if (state.failed) return ;

                }
                break;
            case 53 :
                // Soil.g:1:334: DOTDOT
                {
                mDOTDOT(); if (state.failed) return ;

                }
                break;
            case 54 :
                // Soil.g:1:341: EQUAL
                {
                mEQUAL(); if (state.failed) return ;

                }
                break;
            case 55 :
                // Soil.g:1:347: GREATER
                {
                mGREATER(); if (state.failed) return ;

                }
                break;
            case 56 :
                // Soil.g:1:355: GREATER_EQUAL
                {
                mGREATER_EQUAL(); if (state.failed) return ;

                }
                break;
            case 57 :
                // Soil.g:1:369: HASH
                {
                mHASH(); if (state.failed) return ;

                }
                break;
            case 58 :
                // Soil.g:1:374: LBRACE
                {
                mLBRACE(); if (state.failed) return ;

                }
                break;
            case 59 :
                // Soil.g:1:381: LBRACK
                {
                mLBRACK(); if (state.failed) return ;

                }
                break;
            case 60 :
                // Soil.g:1:388: LESS
                {
                mLESS(); if (state.failed) return ;

                }
                break;
            case 61 :
                // Soil.g:1:393: LESS_EQUAL
                {
                mLESS_EQUAL(); if (state.failed) return ;

                }
                break;
            case 62 :
                // Soil.g:1:404: LPAREN
                {
                mLPAREN(); if (state.failed) return ;

                }
                break;
            case 63 :
                // Soil.g:1:411: MINUS
                {
                mMINUS(); if (state.failed) return ;

                }
                break;
            case 64 :
                // Soil.g:1:417: NOT_EQUAL
                {
                mNOT_EQUAL(); if (state.failed) return ;

                }
                break;
            case 65 :
                // Soil.g:1:427: PLUS
                {
                mPLUS(); if (state.failed) return ;

                }
                break;
            case 66 :
                // Soil.g:1:432: RBRACE
                {
                mRBRACE(); if (state.failed) return ;

                }
                break;
            case 67 :
                // Soil.g:1:439: RBRACK
                {
                mRBRACK(); if (state.failed) return ;

                }
                break;
            case 68 :
                // Soil.g:1:446: RPAREN
                {
                mRPAREN(); if (state.failed) return ;

                }
                break;
            case 69 :
                // Soil.g:1:453: SEMI
                {
                mSEMI(); if (state.failed) return ;

                }
                break;
            case 70 :
                // Soil.g:1:458: SLASH
                {
                mSLASH(); if (state.failed) return ;

                }
                break;
            case 71 :
                // Soil.g:1:464: STAR
                {
                mSTAR(); if (state.failed) return ;

                }
                break;
            case 72 :
                // Soil.g:1:469: SCRIPTBODY
                {
                mSCRIPTBODY(); if (state.failed) return ;

                }
                break;
            case 73 :
                // Soil.g:1:480: RANGE_OR_INT
                {
                mRANGE_OR_INT(); if (state.failed) return ;

                }
                break;
            case 74 :
                // Soil.g:1:493: STRING
                {
                mSTRING(); if (state.failed) return ;

                }
                break;
            case 75 :
                // Soil.g:1:500: IDENT
                {
                mIDENT(); if (state.failed) return ;

                }
                break;

        }

    }

    // $ANTLR start synpred1_Soil
    public final void synpred1_Soil_fragment() throws RecognitionException {   
        // Soil.g:1346:7: ( INT '..' )
        // Soil.g:1346:9: INT '..'
        {
        mINT(); if (state.failed) return ;
        match(".."); if (state.failed) return ;


        }
    }
    // $ANTLR end synpred1_Soil

    // $ANTLR start synpred2_Soil
    public final void synpred2_Soil_fragment() throws RecognitionException {   
        // Soil.g:1347:7: ( REAL )
        // Soil.g:1347:9: REAL
        {
        mREAL(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_Soil

    public final boolean synpred1_Soil() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_Soil_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_Soil() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_Soil_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA19 dfa19 = new DFA19(this);
    static final String DFA19_eotS =
        "\1\uffff\23\53\1\uffff\1\116\1\120\2\uffff\1\123\1\uffff\1\125"+
        "\1\uffff\1\127\3\uffff\1\133\12\uffff\5\53\1\142\1\53\1\146\1\147"+
        "\12\53\1\162\14\53\20\uffff\1\u0080\1\u0081\4\53\1\uffff\1\u0086"+
        "\2\53\2\uffff\3\53\1\u008c\4\53\1\u0092\1\u0093\1\uffff\1\53\1\u0098"+
        "\1\u0099\1\53\1\u009b\1\u009c\1\53\1\u009e\5\53\2\uffff\1\u00a4"+
        "\3\53\1\uffff\1\53\1\u00a9\2\53\1\u00ac\1\uffff\1\53\1\u00ae\1\u00af"+
        "\1\u00b0\1\53\2\uffff\4\53\2\uffff\1\53\2\uffff\1\53\1\uffff\3\53"+
        "\1\u00bb\1\53\1\uffff\4\53\1\uffff\2\53\1\uffff\1\u00c3\3\uffff"+
        "\1\u00c4\10\53\1\u00ce\1\uffff\3\53\1\u00d2\1\u00d3\2\53\2\uffff"+
        "\11\53\1\uffff\1\53\1\u00e0\1\u00e1\2\uffff\1\u00e2\1\u00e3\12\53"+
        "\4\uffff\3\53\1\u00f1\2\53\1\u00f4\3\53\1\u00f8\2\53\1\uffff\2\53"+
        "\1\uffff\1\53\1\u00fe\1\53\1\uffff\4\53\1\u0104\1\uffff\1\u0105"+
        "\1\u0106\1\u0107\2\53\4\uffff\1\u010a\1\u010b\2\uffff";
    static final String DFA19_eofS =
        "\u010c\uffff";
    static final String DFA19_minS =
        "\1\11\3\145\1\146\1\141\1\150\1\154\1\145\1\143\1\157\1\154\1\162"+
        "\1\145\1\141\1\162\1\156\1\165\1\141\1\157\1\uffff\1\52\1\55\2\uffff"+
        "\1\72\1\uffff\1\56\1\uffff\1\75\3\uffff\1\74\12\uffff\1\167\1\164"+
        "\1\154\1\164\1\154\1\60\1\166\2\60\1\160\1\145\1\157\1\162\1\154"+
        "\1\145\1\165\1\163\1\144\1\164\1\60\1\154\1\162\1\144\1\154\1\145"+
        "\1\161\1\147\2\144\1\160\1\164\1\154\20\uffff\2\60\1\154\1\167\1"+
        "\164\1\145\1\uffff\1\60\1\145\1\157\2\uffff\1\154\1\162\1\155\1"+
        "\60\1\163\1\156\2\145\2\60\1\uffff\1\101\2\60\1\111\2\60\1\165\1"+
        "\60\2\145\1\154\1\145\1\154\2\uffff\1\60\1\145\1\162\1\164\1\uffff"+
        "\1\162\1\60\1\151\1\141\1\60\1\uffff\1\145\3\60\1\146\2\uffff\2"+
        "\163\1\155\1\156\2\uffff\1\156\2\uffff\1\145\1\uffff\1\162\1\146"+
        "\1\145\1\60\1\145\1\uffff\1\145\1\157\1\145\1\164\1\uffff\1\145"+
        "\1\164\1\uffff\1\60\3\uffff\1\60\1\124\1\113\1\160\1\144\1\163\1"+
        "\156\1\145\1\151\1\60\1\uffff\1\143\1\156\1\171\2\60\1\163\1\145"+
        "\2\uffff\1\171\1\151\1\171\1\164\1\145\1\164\1\143\1\144\1\156\1"+
        "\uffff\1\164\2\60\2\uffff\2\60\1\160\1\156\1\160\1\171\1\146\1\141"+
        "\1\145\1\123\1\145\1\151\4\uffff\1\145\1\144\1\145\1\60\1\151\1"+
        "\156\1\60\1\145\1\144\1\157\1\60\2\117\1\uffff\1\156\1\143\1\uffff"+
        "\1\164\1\60\1\156\1\uffff\2\146\2\145\1\60\1\uffff\3\60\1\144\1"+
        "\163\4\uffff\2\60\2\uffff";
    static final String DFA19_maxS =
        "\1\175\1\165\1\145\1\157\1\164\2\162\1\156\1\145\1\162\1\157\1"+
        "\156\1\162\1\145\1\141\1\162\1\156\1\165\1\141\1\157\1\uffff\1\57"+
        "\1\76\2\uffff\1\75\1\uffff\1\56\1\uffff\1\75\3\uffff\1\76\12\uffff"+
        "\1\167\1\164\1\154\1\164\1\163\1\172\1\166\2\172\1\160\1\145\1\157"+
        "\1\162\1\154\1\145\1\165\1\163\1\144\1\164\1\172\1\154\1\162\1\144"+
        "\1\154\1\145\1\164\1\147\2\144\1\160\1\164\1\154\20\uffff\2\172"+
        "\1\154\1\167\1\164\1\145\1\uffff\1\172\1\145\1\157\2\uffff\1\154"+
        "\1\162\1\155\1\172\1\163\1\156\2\145\2\172\1\uffff\1\125\2\172\1"+
        "\111\2\172\1\165\1\172\2\145\1\154\1\145\1\154\2\uffff\1\172\1\145"+
        "\1\162\1\164\1\uffff\1\162\1\172\1\151\1\141\1\172\1\uffff\1\145"+
        "\3\172\1\146\2\uffff\2\163\1\155\1\156\2\uffff\1\156\2\uffff\1\145"+
        "\1\uffff\1\162\1\146\1\145\1\172\1\145\1\uffff\1\145\1\157\1\145"+
        "\1\164\1\uffff\1\145\1\164\1\uffff\1\172\3\uffff\1\172\2\124\1\160"+
        "\1\144\1\163\1\156\1\145\1\151\1\172\1\uffff\1\143\1\156\1\171\2"+
        "\172\1\163\1\145\2\uffff\1\171\1\151\1\171\1\164\1\145\1\164\1\143"+
        "\1\144\1\156\1\uffff\1\164\2\172\2\uffff\2\172\1\160\1\156\1\160"+
        "\1\171\1\146\1\141\1\145\1\123\1\145\1\151\4\uffff\1\145\1\144\1"+
        "\145\1\172\1\151\1\156\1\172\1\145\1\144\1\157\1\172\2\117\1\uffff"+
        "\1\156\1\143\1\uffff\1\164\1\172\1\156\1\uffff\2\146\2\145\1\172"+
        "\1\uffff\3\172\1\144\1\163\4\uffff\2\172\2\uffff";
    static final String DFA19_acceptS =
        "\24\uffff\1\52\2\uffff\1\56\1\57\1\uffff\1\63\1\uffff\1\66\1\uffff"+
        "\1\71\1\72\1\73\1\uffff\1\76\1\101\1\102\1\103\1\104\1\105\1\107"+
        "\1\111\1\112\1\113\40\uffff\1\53\1\54\1\106\1\55\1\77\1\61\1\62"+
        "\1\60\1\65\1\64\1\70\1\67\1\75\1\100\1\110\1\74\6\uffff\1\16\3\uffff"+
        "\1\15\1\10\12\uffff\1\21\15\uffff\1\1\1\25\4\uffff\1\24\5\uffff"+
        "\1\14\5\uffff\1\13\1\17\4\uffff\1\22\1\23\1\uffff\1\27\1\37\1\uffff"+
        "\1\41\5\uffff\1\46\4\uffff\1\5\2\uffff\1\7\1\uffff\1\11\1\35\1\12"+
        "\12\uffff\1\50\7\uffff\1\36\1\34\11\uffff\1\47\3\uffff\1\6\1\4\14"+
        "\uffff\1\2\1\3\1\20\1\30\15\uffff\1\43\2\uffff\1\40\3\uffff\1\31"+
        "\5\uffff\1\45\5\uffff\1\42\1\51\1\32\1\33\2\uffff\1\44\1\26";
    static final String DFA19_specialS =
        "\u010c\uffff}>";
    static final String[] DFA19_transitionS = {
            "\2\24\1\uffff\2\24\22\uffff\1\24\2\uffff\1\36\1\53\2\uffff"+
            "\1\52\1\42\1\46\1\50\1\43\1\32\1\26\1\33\1\25\12\51\1\31\1\47"+
            "\1\41\1\34\1\35\1\uffff\1\27\1\53\1\16\1\23\1\22\12\53\1\17"+
            "\3\53\1\15\1\21\1\20\5\53\1\40\1\uffff\1\45\1\uffff\1\53\1\uffff"+
            "\1\13\1\2\1\53\1\3\1\7\1\5\2\53\1\4\2\53\1\10\1\53\1\1\1\11"+
            "\1\14\3\53\1\6\3\53\1\12\2\53\1\37\1\30\1\44",
            "\1\54\11\uffff\1\55\5\uffff\1\56",
            "\1\57",
            "\1\60\3\uffff\1\62\5\uffff\1\61",
            "\1\64\6\uffff\1\65\1\63\5\uffff\1\66",
            "\1\71\15\uffff\1\70\2\uffff\1\67",
            "\1\72\11\uffff\1\73",
            "\1\74\1\uffff\1\75",
            "\1\76",
            "\1\100\16\uffff\1\77",
            "\1\101",
            "\1\103\1\uffff\1\102",
            "\1\104",
            "\1\105",
            "\1\106",
            "\1\107",
            "\1\110",
            "\1\111",
            "\1\112",
            "\1\113",
            "",
            "\1\115\4\uffff\1\114",
            "\1\114\20\uffff\1\117",
            "",
            "",
            "\1\121\2\uffff\1\122",
            "",
            "\1\124",
            "",
            "\1\126",
            "",
            "",
            "",
            "\1\132\1\130\1\131",
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
            "\1\134",
            "\1\135",
            "\1\136",
            "\1\137",
            "\1\141\6\uffff\1\140",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\143",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\22\53\1\144\1\145"+
            "\6\53",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\150",
            "\1\151",
            "\1\152",
            "\1\153",
            "\1\154",
            "\1\155",
            "\1\156",
            "\1\157",
            "\1\160",
            "\1\161",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\163",
            "\1\164",
            "\1\165",
            "\1\166",
            "\1\167",
            "\1\171\2\uffff\1\170",
            "\1\172",
            "\1\173",
            "\1\174",
            "\1\175",
            "\1\176",
            "\1\177",
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
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\u0082",
            "\1\u0083",
            "\1\u0084",
            "\1\u0085",
            "",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\u0087",
            "\1\u0088",
            "",
            "",
            "\1\u0089",
            "\1\u008a",
            "\1\u008b",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\u008d",
            "\1\u008e",
            "\1\u008f",
            "\1\u0090",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\10\53\1\u0091\21"+
            "\53",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "",
            "\1\u0094\3\uffff\1\u0096\3\uffff\1\u0095\13\uffff\1\u0097",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\u009a",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\u009d",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\u009f",
            "\1\u00a0",
            "\1\u00a1",
            "\1\u00a2",
            "\1\u00a3",
            "",
            "",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\u00a5",
            "\1\u00a6",
            "\1\u00a7",
            "",
            "\1\u00a8",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\u00aa",
            "\1\u00ab",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "",
            "\1\u00ad",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\u00b1",
            "",
            "",
            "\1\u00b2",
            "\1\u00b3",
            "\1\u00b4",
            "\1\u00b5",
            "",
            "",
            "\1\u00b6",
            "",
            "",
            "\1\u00b7",
            "",
            "\1\u00b8",
            "\1\u00b9",
            "\1\u00ba",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\u00bc",
            "",
            "\1\u00bd",
            "\1\u00be",
            "\1\u00bf",
            "\1\u00c0",
            "",
            "\1\u00c1",
            "\1\u00c2",
            "",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "",
            "",
            "",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\u00c5",
            "\1\u00c6\10\uffff\1\u00c7",
            "\1\u00c8",
            "\1\u00c9",
            "\1\u00ca",
            "\1\u00cb",
            "\1\u00cc",
            "\1\u00cd",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "",
            "\1\u00cf",
            "\1\u00d0",
            "\1\u00d1",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\u00d4",
            "\1\u00d5",
            "",
            "",
            "\1\u00d6",
            "\1\u00d7",
            "\1\u00d8",
            "\1\u00d9",
            "\1\u00da",
            "\1\u00db",
            "\1\u00dc",
            "\1\u00dd",
            "\1\u00de",
            "",
            "\1\u00df",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "",
            "",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
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
            "\1\u00ee",
            "\1\u00ef",
            "\1\u00f0",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\u00f2",
            "\1\u00f3",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\u00f5",
            "\1\u00f6",
            "\1\u00f7",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\u00f9",
            "\1\u00fa",
            "",
            "\1\u00fb",
            "\1\u00fc",
            "",
            "\1\u00fd",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\u00ff",
            "",
            "\1\u0100",
            "\1\u0101",
            "\1\u0102",
            "\1\u0103",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\1\u0108",
            "\1\u0109",
            "",
            "",
            "",
            "",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "\12\53\7\uffff\32\53\4\uffff\1\53\1\uffff\32\53",
            "",
            ""
    };

    static final short[] DFA19_eot = DFA.unpackEncodedString(DFA19_eotS);
    static final short[] DFA19_eof = DFA.unpackEncodedString(DFA19_eofS);
    static final char[] DFA19_min = DFA.unpackEncodedStringToUnsignedChars(DFA19_minS);
    static final char[] DFA19_max = DFA.unpackEncodedStringToUnsignedChars(DFA19_maxS);
    static final short[] DFA19_accept = DFA.unpackEncodedString(DFA19_acceptS);
    static final short[] DFA19_special = DFA.unpackEncodedString(DFA19_specialS);
    static final short[][] DFA19_transition;

    static {
        int numStates = DFA19_transitionS.length;
        DFA19_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA19_transition[i] = DFA.unpackEncodedString(DFA19_transitionS[i]);
        }
    }

    class DFA19 extends DFA {

        public DFA19(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 19;
            this.eot = DFA19_eot;
            this.eof = DFA19_eof;
            this.min = DFA19_min;
            this.max = DFA19_max;
            this.accept = DFA19_accept;
            this.special = DFA19_special;
            this.transition = DFA19_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__44 | T__45 | T__46 | T__47 | T__48 | T__49 | T__50 | T__51 | T__52 | T__53 | T__54 | T__55 | T__56 | T__57 | T__58 | T__59 | T__60 | T__61 | T__62 | T__63 | T__64 | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | WS | SL_COMMENT | ML_COMMENT | ARROW | AT | BAR | COLON | COLON_COLON | COLON_EQUAL | COMMA | DOT | DOTDOT | EQUAL | GREATER | GREATER_EQUAL | HASH | LBRACE | LBRACK | LESS | LESS_EQUAL | LPAREN | MINUS | NOT_EQUAL | PLUS | RBRACE | RBRACK | RPAREN | SEMI | SLASH | STAR | SCRIPTBODY | RANGE_OR_INT | STRING | IDENT );";
        }
    }
 

}