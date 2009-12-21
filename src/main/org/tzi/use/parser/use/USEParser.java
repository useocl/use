// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 USE.g 2009-12-21 11:54:55
 
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
import org.tzi.use.parser.base.BaseParser;
import org.tzi.use.parser.ocl.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all") public class USEParser extends BaseParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "IDENT", "LBRACE", "RBRACE", "SEMI", "LESS", "COLON", "EQUAL", "LBRACK", "RBRACK", "COMMA", "DOTDOT", "INT", "STAR", "COLON_COLON", "COLON_EQUAL", "LPAREN", "RPAREN", "NOT_EQUAL", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "PLUS", "MINUS", "SLASH", "ARROW", "DOT", "AT", "BAR", "REAL", "STRING", "HASH", "NEWLINE", "WS", "SL_COMMENT", "ML_COMMENT", "RANGE_OR_INT", "ESC", "HEX_DIGIT", "VOCAB", "'model'", "'constraints'", "'enum'", "'abstract'", "'class'", "'attributes'", "'operations'", "'end'", "'associationClass'", "'associationclass'", "'between'", "'aggregation'", "'composition'", "'begin'", "'association'", "'role'", "'ordered'", "'subsets'", "'redefines'", "'context'", "'inv'", "'existential'", "'pre'", "'post'", "'var'", "'declare'", "'set'", "'create'", "'namehint'", "'insert'", "'into'", "'delete'", "'from'", "'destroy'", "'if'", "'then'", "'else'", "'endif'", "'while'", "'do'", "'wend'", "'for'", "'in'", "'execute'", "'let'", "'implies'", "'or'", "'xor'", "'and'", "'div'", "'not'", "'allInstances'", "'iterate'", "'oclAsType'", "'oclIsKindOf'", "'oclIsTypeOf'", "'true'", "'false'", "'Set'", "'Sequence'", "'Bag'", "'OrderedSet'", "'oclEmpty'", "'oclUndefined'", "'Undefined'", "'null'", "'Tuple'", "'Date'", "'Collection'"
    };
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
    public static final int T__82=82;
    public static final int RBRACE=6;
    public static final int T__83=83;
    public static final int INT=15;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int REAL=32;
    public static final int WS=36;
    public static final int T__71=71;
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
    public static final int T__102=102;
    public static final int COLON_COLON=17;
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

    // delegates
    // delegators


        public USEParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public USEParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return USEParser.tokenNames; }
    public String getGrammarFileName() { return "USE.g"; }



    // $ANTLR start "model"
    // USE.g:116:1: model returns [ASTModel n] : 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF ;
    public final ASTModel model() throws RecognitionException {
        ASTModel n = null;

        Token modelName=null;
        ASTEnumTypeDefinition e = null;

        ASTAssociation a = null;

        ASTConstraintDefinition cons = null;

        ASTPrePost ppc = null;


        try {
            // USE.g:117:1: ( 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF )
            // USE.g:118:5: 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF
            {
            match(input,43,FOLLOW_43_in_model69); if (state.failed) return n;
            modelName=(Token)match(input,IDENT,FOLLOW_IDENT_in_model73); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTModel(modelName); 
            }
            // USE.g:119:5: (e= enumTypeDefinition )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==45) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // USE.g:119:7: e= enumTypeDefinition
            	    {
            	    pushFollow(FOLLOW_enumTypeDefinition_in_model85);
            	    e=enumTypeDefinition();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addEnumTypeDef(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // USE.g:120:5: ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )*
            loop3:
            do {
                int alt3=4;
                switch ( input.LA(1) ) {
                case 46:
                case 47:
                case 51:
                case 52:
                    {
                    alt3=1;
                    }
                    break;
                case 54:
                case 55:
                case 57:
                    {
                    alt3=2;
                    }
                    break;
                case 44:
                    {
                    alt3=3;
                    }
                    break;

                }

                switch (alt3) {
            	case 1 :
            	    // USE.g:120:9: ( generalClassDefinition[$n] )
            	    {
            	    // USE.g:120:9: ( generalClassDefinition[$n] )
            	    // USE.g:120:11: generalClassDefinition[$n]
            	    {
            	    pushFollow(FOLLOW_generalClassDefinition_in_model102);
            	    generalClassDefinition(n);

            	    state._fsp--;
            	    if (state.failed) return n;

            	    }


            	    }
            	    break;
            	case 2 :
            	    // USE.g:121:9: (a= associationDefinition )
            	    {
            	    // USE.g:121:9: (a= associationDefinition )
            	    // USE.g:121:11: a= associationDefinition
            	    {
            	    pushFollow(FOLLOW_associationDefinition_in_model119);
            	    a=associationDefinition();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addAssociation(a); 
            	    }

            	    }


            	    }
            	    break;
            	case 3 :
            	    // USE.g:122:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    {
            	    // USE.g:122:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    // USE.g:122:11: 'constraints' (cons= invariant | ppc= prePost )*
            	    {
            	    match(input,44,FOLLOW_44_in_model135); if (state.failed) return n;
            	    // USE.g:123:11: (cons= invariant | ppc= prePost )*
            	    loop2:
            	    do {
            	        int alt2=3;
            	        int LA2_0 = input.LA(1);

            	        if ( (LA2_0==62) ) {
            	            int LA2_2 = input.LA(2);

            	            if ( (LA2_2==IDENT) ) {
            	                int LA2_3 = input.LA(3);

            	                if ( (LA2_3==COLON_COLON) ) {
            	                    alt2=2;
            	                }
            	                else if ( (LA2_3==EOF||LA2_3==COLON||LA2_3==COMMA||LA2_3==44||(LA2_3>=46 && LA2_3<=47)||(LA2_3>=51 && LA2_3<=52)||(LA2_3>=54 && LA2_3<=55)||LA2_3==57||(LA2_3>=62 && LA2_3<=64)) ) {
            	                    alt2=1;
            	                }


            	            }


            	        }


            	        switch (alt2) {
            	    	case 1 :
            	    	    // USE.g:123:15: cons= invariant
            	    	    {
            	    	    pushFollow(FOLLOW_invariant_in_model153);
            	    	    cons=invariant();

            	    	    state._fsp--;
            	    	    if (state.failed) return n;
            	    	    if ( state.backtracking==0 ) {
            	    	       n.addConstraint(cons); 
            	    	    }

            	    	    }
            	    	    break;
            	    	case 2 :
            	    	    // USE.g:124:15: ppc= prePost
            	    	    {
            	    	    pushFollow(FOLLOW_prePost_in_model174);
            	    	    ppc=prePost();

            	    	    state._fsp--;
            	    	    if (state.failed) return n;
            	    	    if ( state.backtracking==0 ) {
            	    	       n.addPrePost(ppc); 
            	    	    }

            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop2;
            	        }
            	    } while (true);


            	    }


            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            match(input,EOF,FOLLOW_EOF_in_model215); if (state.failed) return n;

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
    // $ANTLR end "model"


    // $ANTLR start "enumTypeDefinition"
    // USE.g:135:1: enumTypeDefinition returns [ASTEnumTypeDefinition n] : 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )? ;
    public final ASTEnumTypeDefinition enumTypeDefinition() throws RecognitionException {
        ASTEnumTypeDefinition n = null;

        Token name=null;
        List idListRes = null;


        try {
            // USE.g:136:1: ( 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )? )
            // USE.g:137:5: 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )?
            {
            match(input,45,FOLLOW_45_in_enumTypeDefinition242); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_enumTypeDefinition246); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_enumTypeDefinition248); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_enumTypeDefinition252);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,RBRACE,FOLLOW_RBRACE_in_enumTypeDefinition254); if (state.failed) return n;
            // USE.g:137:54: ( SEMI )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==SEMI) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // USE.g:137:56: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_enumTypeDefinition258); if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTEnumTypeDefinition(name, idListRes); 
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
    // $ANTLR end "enumTypeDefinition"


    // $ANTLR start "generalClassDefinition"
    // USE.g:146:1: generalClassDefinition[ASTModel n] : ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) ) ;
    public final void generalClassDefinition(ASTModel n) throws RecognitionException {
        ASTClass c = null;

        ASTAssociationClass ac = null;


         
          boolean isAbstract = false;

        try {
            // USE.g:150:1: ( ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) ) )
            // USE.g:151:5: ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) )
            {
            // USE.g:151:5: ( 'abstract' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==46) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // USE.g:151:7: 'abstract'
                    {
                    match(input,46,FOLLOW_46_in_generalClassDefinition297); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       isAbstract = true; 
                    }

                    }
                    break;

            }

            // USE.g:152:5: ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==47) ) {
                alt6=1;
            }
            else if ( ((LA6_0>=51 && LA6_0<=52)) ) {
                alt6=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // USE.g:152:7: (c= classDefinition[isAbstract] )
                    {
                    // USE.g:152:7: (c= classDefinition[isAbstract] )
                    // USE.g:152:9: c= classDefinition[isAbstract]
                    {
                    pushFollow(FOLLOW_classDefinition_in_generalClassDefinition315);
                    c=classDefinition(isAbstract);

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       n.addClass(c); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // USE.g:153:7: (ac= associationClassDefinition[isAbstract] )
                    {
                    // USE.g:153:7: (ac= associationClassDefinition[isAbstract] )
                    // USE.g:153:9: ac= associationClassDefinition[isAbstract]
                    {
                    pushFollow(FOLLOW_associationClassDefinition_in_generalClassDefinition335);
                    ac=associationClassDefinition(isAbstract);

                    state._fsp--;
                    if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       n.addAssociationClass(ac); 
                    }

                    }


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
        return ;
    }
    // $ANTLR end "generalClassDefinition"


    // $ANTLR start "classDefinition"
    // USE.g:170:1: classDefinition[boolean isAbstract] returns [ASTClass n] : 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end' ;
    public final ASTClass classDefinition(boolean isAbstract) throws RecognitionException {
        ASTClass n = null;

        Token name=null;
        List idListRes = null;

        ASTAttribute a = null;

        ASTOperation op = null;

        ASTInvariantClause inv = null;


         List idList; 
        try {
            // USE.g:172:1: ( 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end' )
            // USE.g:173:5: 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end'
            {
            match(input,47,FOLLOW_47_in_classDefinition374); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_classDefinition378); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTClass(name, isAbstract); 
            }
            // USE.g:174:5: ( LESS idListRes= idList )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==LESS) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // USE.g:174:7: LESS idListRes= idList
                    {
                    match(input,LESS,FOLLOW_LESS_in_classDefinition388); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_classDefinition392);
                    idListRes=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addSuperClasses(idListRes); 
                    }

                    }
                    break;

            }

            // USE.g:175:5: ( 'attributes' (a= attributeDefinition )* )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==48) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // USE.g:175:7: 'attributes' (a= attributeDefinition )*
                    {
                    match(input,48,FOLLOW_48_in_classDefinition405); if (state.failed) return n;
                    // USE.g:176:7: (a= attributeDefinition )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0==IDENT) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // USE.g:176:9: a= attributeDefinition
                    	    {
                    	    pushFollow(FOLLOW_attributeDefinition_in_classDefinition418);
                    	    a=attributeDefinition();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addAttribute(a); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop8;
                        }
                    } while (true);


                    }
                    break;

            }

            // USE.g:178:5: ( 'operations' (op= operationDefinition )* )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==49) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // USE.g:178:7: 'operations' (op= operationDefinition )*
                    {
                    match(input,49,FOLLOW_49_in_classDefinition439); if (state.failed) return n;
                    // USE.g:179:7: (op= operationDefinition )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0==IDENT) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // USE.g:179:9: op= operationDefinition
                    	    {
                    	    pushFollow(FOLLOW_operationDefinition_in_classDefinition452);
                    	    op=operationDefinition();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addOperation(op); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);


                    }
                    break;

            }

            // USE.g:181:5: ( 'constraints' (inv= invariantClause )* )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==44) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // USE.g:181:7: 'constraints' (inv= invariantClause )*
                    {
                    match(input,44,FOLLOW_44_in_classDefinition473); if (state.failed) return n;
                    // USE.g:182:7: (inv= invariantClause )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( ((LA12_0>=63 && LA12_0<=64)) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // USE.g:183:9: inv= invariantClause
                    	    {
                    	    pushFollow(FOLLOW_invariantClause_in_classDefinition493);
                    	    inv=invariantClause();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addInvariantClause(inv); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop12;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,50,FOLLOW_50_in_classDefinition517); if (state.failed) return n;

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
    // $ANTLR end "classDefinition"


    // $ANTLR start "associationClassDefinition"
    // USE.g:204:1: associationClassDefinition[boolean isAbstract] returns [ASTAssociationClass n] : classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end' ;
    public final ASTAssociationClass associationClassDefinition(boolean isAbstract) throws RecognitionException {
        ASTAssociationClass n = null;

        Token classKW=null;
        Token name=null;
        List idListRes = null;

        ASTAssociationEnd ae = null;

        ASTAttribute a = null;

        ASTOperation op = null;

        ASTInvariantClause inv = null;


        List idList; Token t = null;
        try {
            // USE.g:206:1: (classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end' )
            // USE.g:207:5: classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end'
            {
            classKW=(Token)input.LT(1);
            if ( (input.LA(1)>=51 && input.LA(1)<=52) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            if ( state.backtracking==0 ) {
               
                  	if ((classKW!=null?classKW.getText():null).equals("associationClass")) {
                             reportWarning("the 'associationClass' keyword is deprecated and will " +
                                           "not be supported in the future, use 'associationclass' instead");
                          }  
                  
            }
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationClassDefinition576); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociationClass(name, isAbstract); 
            }
            // USE.g:216:5: ( LESS idListRes= idList )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==LESS) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // USE.g:216:7: LESS idListRes= idList
                    {
                    match(input,LESS,FOLLOW_LESS_in_associationClassDefinition586); if (state.failed) return n;
                    pushFollow(FOLLOW_idList_in_associationClassDefinition590);
                    idListRes=idList();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addSuperClasses(idListRes); 
                    }

                    }
                    break;

            }

            match(input,53,FOLLOW_53_in_associationClassDefinition601); if (state.failed) return n;
            pushFollow(FOLLOW_associationEnd_in_associationClassDefinition609);
            ae=associationEnd();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addEnd(ae); 
            }
            // USE.g:219:5: (ae= associationEnd )+
            int cnt15=0;
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==IDENT) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // USE.g:219:7: ae= associationEnd
            	    {
            	    pushFollow(FOLLOW_associationEnd_in_associationClassDefinition621);
            	    ae=associationEnd();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addEnd(ae); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt15 >= 1 ) break loop15;
            	    if (state.backtracking>0) {state.failed=true; return n;}
                        EarlyExitException eee =
                            new EarlyExitException(15, input);
                        throw eee;
                }
                cnt15++;
            } while (true);

            // USE.g:220:5: ( 'attributes' (a= attributeDefinition )* )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==48) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // USE.g:220:7: 'attributes' (a= attributeDefinition )*
                    {
                    match(input,48,FOLLOW_48_in_associationClassDefinition634); if (state.failed) return n;
                    // USE.g:221:7: (a= attributeDefinition )*
                    loop16:
                    do {
                        int alt16=2;
                        int LA16_0 = input.LA(1);

                        if ( (LA16_0==IDENT) ) {
                            alt16=1;
                        }


                        switch (alt16) {
                    	case 1 :
                    	    // USE.g:221:9: a= attributeDefinition
                    	    {
                    	    pushFollow(FOLLOW_attributeDefinition_in_associationClassDefinition647);
                    	    a=attributeDefinition();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addAttribute(a); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop16;
                        }
                    } while (true);


                    }
                    break;

            }

            // USE.g:223:5: ( 'operations' (op= operationDefinition )* )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==49) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // USE.g:223:7: 'operations' (op= operationDefinition )*
                    {
                    match(input,49,FOLLOW_49_in_associationClassDefinition668); if (state.failed) return n;
                    // USE.g:224:7: (op= operationDefinition )*
                    loop18:
                    do {
                        int alt18=2;
                        int LA18_0 = input.LA(1);

                        if ( (LA18_0==IDENT) ) {
                            alt18=1;
                        }


                        switch (alt18) {
                    	case 1 :
                    	    // USE.g:224:9: op= operationDefinition
                    	    {
                    	    pushFollow(FOLLOW_operationDefinition_in_associationClassDefinition681);
                    	    op=operationDefinition();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addOperation(op); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop18;
                        }
                    } while (true);


                    }
                    break;

            }

            // USE.g:226:5: ( 'constraints' (inv= invariantClause )* )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==44) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // USE.g:226:7: 'constraints' (inv= invariantClause )*
                    {
                    match(input,44,FOLLOW_44_in_associationClassDefinition702); if (state.failed) return n;
                    // USE.g:227:7: (inv= invariantClause )*
                    loop20:
                    do {
                        int alt20=2;
                        int LA20_0 = input.LA(1);

                        if ( ((LA20_0>=63 && LA20_0<=64)) ) {
                            alt20=1;
                        }


                        switch (alt20) {
                    	case 1 :
                    	    // USE.g:228:9: inv= invariantClause
                    	    {
                    	    pushFollow(FOLLOW_invariantClause_in_associationClassDefinition722);
                    	    inv=invariantClause();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addInvariantClause(inv); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop20;
                        }
                    } while (true);


                    }
                    break;

            }

            // USE.g:231:5: ( ( 'aggregation' | 'composition' ) )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( ((LA22_0>=54 && LA22_0<=55)) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // USE.g:231:7: ( 'aggregation' | 'composition' )
                    {
                    if ( state.backtracking==0 ) {
                       t = input.LT(1); 
                    }
                    if ( (input.LA(1)>=54 && input.LA(1)<=55) ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    if ( state.backtracking==0 ) {
                       n.setKind(t); 
                    }

                    }
                    break;

            }

            match(input,50,FOLLOW_50_in_associationClassDefinition785); if (state.failed) return n;

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
    // $ANTLR end "associationClassDefinition"


    // $ANTLR start "attributeDefinition"
    // USE.g:242:1: attributeDefinition returns [ASTAttribute n] : name= IDENT COLON t= type ( SEMI )? ;
    public final ASTAttribute attributeDefinition() throws RecognitionException {
        ASTAttribute n = null;

        Token name=null;
        ASTType t = null;


        try {
            // USE.g:243:1: (name= IDENT COLON t= type ( SEMI )? )
            // USE.g:244:5: name= IDENT COLON t= type ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_attributeDefinition814); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_attributeDefinition816); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_attributeDefinition820);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            // USE.g:244:29: ( SEMI )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==SEMI) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // USE.g:244:31: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_attributeDefinition824); if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTAttribute(name, t); 
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
    // $ANTLR end "attributeDefinition"


    // $ANTLR start "operationDefinition"
    // USE.g:253:1: operationDefinition returns [ASTOperation n] : name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression )? ( 'begin' al= alActionList 'end' )? (ppc= prePostClause )* ( SEMI )? ;
    public final ASTOperation operationDefinition() throws RecognitionException {
        ASTOperation n = null;

        Token name=null;
        List pl = null;

        ASTType t = null;

        ASTExpression e = null;

        ASTALActionList al = null;

        ASTPrePostClause ppc = null;


        try {
            // USE.g:254:1: (name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression )? ( 'begin' al= alActionList 'end' )? (ppc= prePostClause )* ( SEMI )? )
            // USE.g:255:5: name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression )? ( 'begin' al= alActionList 'end' )? (ppc= prePostClause )* ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationDefinition862); if (state.failed) return n;
            pushFollow(FOLLOW_paramList_in_operationDefinition870);
            pl=paramList();

            state._fsp--;
            if (state.failed) return n;
            // USE.g:257:5: ( COLON t= type )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==COLON) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // USE.g:257:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_operationDefinition878); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_operationDefinition882);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            // USE.g:258:5: ( EQUAL e= expression )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==EQUAL) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // USE.g:258:6: EQUAL e= expression
                    {
                    match(input,EQUAL,FOLLOW_EQUAL_in_operationDefinition892); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_operationDefinition896);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            // USE.g:259:2: ( 'begin' al= alActionList 'end' )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==56) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // USE.g:259:3: 'begin' al= alActionList 'end'
                    {
                    match(input,56,FOLLOW_56_in_operationDefinition904); if (state.failed) return n;
                    pushFollow(FOLLOW_alActionList_in_operationDefinition908);
                    al=alActionList();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,50,FOLLOW_50_in_operationDefinition910); if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTOperation(name, pl, t, e, al); 
            }
            // USE.g:261:5: (ppc= prePostClause )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( ((LA27_0>=65 && LA27_0<=66)) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // USE.g:261:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_operationDefinition929);
            	    ppc=prePostClause();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addPrePostClause(ppc); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);

            // USE.g:262:5: ( SEMI )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==SEMI) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // USE.g:262:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_operationDefinition942); if (state.failed) return n;

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
    // $ANTLR end "operationDefinition"


    // $ANTLR start "associationDefinition"
    // USE.g:272:1: associationDefinition returns [ASTAssociation n] : ( 'association' | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end' ;
    public final ASTAssociation associationDefinition() throws RecognitionException {
        ASTAssociation n = null;

        Token name=null;
        ASTAssociationEnd ae = null;


         Token t = null; 
        try {
            // USE.g:274:1: ( ( 'association' | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end' )
            // USE.g:275:5: ( 'association' | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end'
            {
            if ( state.backtracking==0 ) {
               t = input.LT(1); 
            }
            if ( (input.LA(1)>=54 && input.LA(1)<=55)||input.LA(1)==57 ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationDefinition1003); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociation(t, name); 
            }
            match(input,53,FOLLOW_53_in_associationDefinition1011); if (state.failed) return n;
            pushFollow(FOLLOW_associationEnd_in_associationDefinition1019);
            ae=associationEnd();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addEnd(ae); 
            }
            // USE.g:281:5: (ae= associationEnd )+
            int cnt29=0;
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==IDENT) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // USE.g:281:7: ae= associationEnd
            	    {
            	    pushFollow(FOLLOW_associationEnd_in_associationDefinition1031);
            	    ae=associationEnd();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addEnd(ae); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt29 >= 1 ) break loop29;
            	    if (state.backtracking>0) {state.failed=true; return n;}
                        EarlyExitException eee =
                            new EarlyExitException(29, input);
                        throw eee;
                }
                cnt29++;
            } while (true);

            match(input,50,FOLLOW_50_in_associationDefinition1042); if (state.failed) return n;

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
    // $ANTLR end "associationDefinition"


    // $ANTLR start "associationEnd"
    // USE.g:290:1: associationEnd returns [ASTAssociationEnd n] : name= IDENT LBRACK m= multiplicity RBRACK ( 'role' rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )? ;
    public final ASTAssociationEnd associationEnd() throws RecognitionException {
        ASTAssociationEnd n = null;

        Token name=null;
        Token rn=null;
        Token sr=null;
        Token rd=null;
        ASTMultiplicity m = null;


        try {
            // USE.g:291:1: (name= IDENT LBRACK m= multiplicity RBRACK ( 'role' rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )? )
            // USE.g:292:5: name= IDENT LBRACK m= multiplicity RBRACK ( 'role' rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd1068); if (state.failed) return n;
            match(input,LBRACK,FOLLOW_LBRACK_in_associationEnd1070); if (state.failed) return n;
            pushFollow(FOLLOW_multiplicity_in_associationEnd1074);
            m=multiplicity();

            state._fsp--;
            if (state.failed) return n;
            match(input,RBRACK,FOLLOW_RBRACK_in_associationEnd1076); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociationEnd(name, m); 
            }
            // USE.g:293:5: ( 'role' rn= IDENT )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==58) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // USE.g:293:7: 'role' rn= IDENT
                    {
                    match(input,58,FOLLOW_58_in_associationEnd1087); if (state.failed) return n;
                    rn=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd1091); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setRolename(rn); 
                    }

                    }
                    break;

            }

            // USE.g:294:5: ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )*
            loop31:
            do {
                int alt31=5;
                switch ( input.LA(1) ) {
                case IDENT:
                    {
                    int LA31_2 = input.LA(2);

                    if ( (LA31_2==IDENT||LA31_2==SEMI||LA31_2==44||(LA31_2>=48 && LA31_2<=50)||(LA31_2>=54 && LA31_2<=55)||(LA31_2>=59 && LA31_2<=61)) ) {
                        alt31=3;
                    }


                    }
                    break;
                case 59:
                    {
                    alt31=1;
                    }
                    break;
                case 60:
                    {
                    alt31=2;
                    }
                    break;
                case 61:
                    {
                    alt31=4;
                    }
                    break;

                }

                switch (alt31) {
            	case 1 :
            	    // USE.g:295:9: 'ordered'
            	    {
            	    match(input,59,FOLLOW_59_in_associationEnd1112); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.setOrdered(); 
            	    }

            	    }
            	    break;
            	case 2 :
            	    // USE.g:296:9: 'subsets' sr= IDENT
            	    {
            	    match(input,60,FOLLOW_60_in_associationEnd1124); if (state.failed) return n;
            	    sr=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd1128); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addSubsetsRolename(sr); 
            	    }

            	    }
            	    break;
            	case 3 :
            	    // USE.g:297:9: keyUnion
            	    {
            	    pushFollow(FOLLOW_keyUnion_in_associationEnd1140);
            	    keyUnion();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.setUnion(true); 
            	    }

            	    }
            	    break;
            	case 4 :
            	    // USE.g:298:9: 'redefines' rd= IDENT
            	    {
            	    match(input,61,FOLLOW_61_in_associationEnd1152); if (state.failed) return n;
            	    rd=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd1156); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addRedefinesRolename(rd); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);

            // USE.g:300:5: ( SEMI )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==SEMI) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // USE.g:300:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_associationEnd1173); if (state.failed) return n;

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
    // $ANTLR end "associationEnd"


    // $ANTLR start "multiplicity"
    // USE.g:314:1: multiplicity returns [ASTMultiplicity n] : mr= multiplicityRange ( COMMA mr= multiplicityRange )* ;
    public final ASTMultiplicity multiplicity() throws RecognitionException {
        ASTMultiplicity n = null;

        ASTMultiplicityRange mr = null;


        try {
            // USE.g:315:1: (mr= multiplicityRange ( COMMA mr= multiplicityRange )* )
            // USE.g:316:5: mr= multiplicityRange ( COMMA mr= multiplicityRange )*
            {
            if ( state.backtracking==0 ) {
               
              	Token t = input.LT(1); // remember start position of expression
              	n = new ASTMultiplicity(t);
                  
            }
            pushFollow(FOLLOW_multiplicityRange_in_multiplicity1208);
            mr=multiplicityRange();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addRange(mr); 
            }
            // USE.g:321:5: ( COMMA mr= multiplicityRange )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==COMMA) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // USE.g:321:7: COMMA mr= multiplicityRange
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_multiplicity1218); if (state.failed) return n;
            	    pushFollow(FOLLOW_multiplicityRange_in_multiplicity1222);
            	    mr=multiplicityRange();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addRange(mr); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop33;
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
    // $ANTLR end "multiplicity"


    // $ANTLR start "multiplicityRange"
    // USE.g:324:1: multiplicityRange returns [ASTMultiplicityRange n] : ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? ;
    public final ASTMultiplicityRange multiplicityRange() throws RecognitionException {
        ASTMultiplicityRange n = null;

        int ms1 = 0;

        int ms2 = 0;


        try {
            // USE.g:325:1: (ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? )
            // USE.g:326:5: ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )?
            {
            pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange1251);
            ms1=multiplicitySpec();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTMultiplicityRange(ms1); 
            }
            // USE.g:327:5: ( DOTDOT ms2= multiplicitySpec )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==DOTDOT) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // USE.g:327:7: DOTDOT ms2= multiplicitySpec
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_multiplicityRange1261); if (state.failed) return n;
                    pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange1265);
                    ms2=multiplicitySpec();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setHigh(ms2); 
                    }

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
    // $ANTLR end "multiplicityRange"


    // $ANTLR start "multiplicitySpec"
    // USE.g:330:1: multiplicitySpec returns [int m] : (i= INT | STAR );
    public final int multiplicitySpec() throws RecognitionException {
        int m = 0;

        Token i=null;

         m = -1; 
        try {
            // USE.g:332:1: (i= INT | STAR )
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==INT) ) {
                alt35=1;
            }
            else if ( (LA35_0==STAR) ) {
                alt35=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return m;}
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;
            }
            switch (alt35) {
                case 1 :
                    // USE.g:333:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_multiplicitySpec1299); if (state.failed) return m;
                    if ( state.backtracking==0 ) {
                       m = Integer.parseInt((i!=null?i.getText():null)); 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:334:7: STAR
                    {
                    match(input,STAR,FOLLOW_STAR_in_multiplicitySpec1309); if (state.failed) return m;
                    if ( state.backtracking==0 ) {
                       m = -1; 
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
        return m;
    }
    // $ANTLR end "multiplicitySpec"


    // $ANTLR start "invariant"
    // USE.g:355:1: invariant returns [ASTConstraintDefinition n] : 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )* ;
    public final ASTConstraintDefinition invariant() throws RecognitionException {
        ASTConstraintDefinition n = null;

        Token v=null;
        ASTSimpleType t = null;

        ASTInvariantClause inv = null;


        try {
            // USE.g:356:1: ( 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )* )
            // USE.g:357:5: 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )*
            {
            if ( state.backtracking==0 ) {
               n = new ASTConstraintDefinition(); 
            }
            match(input,62,FOLLOW_62_in_invariant1350); if (state.failed) return n;
            // USE.g:359:5: (v= IDENT ( ',' v= IDENT )* COLON )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==IDENT) ) {
                int LA37_1 = input.LA(2);

                if ( (LA37_1==COLON||LA37_1==COMMA) ) {
                    alt37=1;
                }
            }
            switch (alt37) {
                case 1 :
                    // USE.g:359:7: v= IDENT ( ',' v= IDENT )* COLON
                    {
                    v=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariant1360); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addVarName(v); 
                    }
                    // USE.g:360:8: ( ',' v= IDENT )*
                    loop36:
                    do {
                        int alt36=2;
                        int LA36_0 = input.LA(1);

                        if ( (LA36_0==COMMA) ) {
                            alt36=1;
                        }


                        switch (alt36) {
                    	case 1 :
                    	    // USE.g:360:9: ',' v= IDENT
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_invariant1373); if (state.failed) return n;
                    	    v=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariant1377); if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addVarName(v); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop36;
                        }
                    } while (true);

                    match(input,COLON,FOLLOW_COLON_in_invariant1385); if (state.failed) return n;

                    }
                    break;

            }

            pushFollow(FOLLOW_simpleType_in_invariant1397);
            t=simpleType();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setType(t); 
            }
            // USE.g:362:5: (inv= invariantClause )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( ((LA38_0>=63 && LA38_0<=64)) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // USE.g:362:7: inv= invariantClause
            	    {
            	    pushFollow(FOLLOW_invariantClause_in_invariant1409);
            	    inv=invariantClause();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addInvariantClause(inv); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop38;
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
    // $ANTLR end "invariant"


    // $ANTLR start "invariantClause"
    // USE.g:369:1: invariantClause returns [ASTInvariantClause n] : ( 'inv' (name= IDENT )? COLON e= expression | 'existential' 'inv' (name= IDENT )? COLON e= expression );
    public final ASTInvariantClause invariantClause() throws RecognitionException {
        ASTInvariantClause n = null;

        Token name=null;
        ASTExpression e = null;


        try {
            // USE.g:370:1: ( 'inv' (name= IDENT )? COLON e= expression | 'existential' 'inv' (name= IDENT )? COLON e= expression )
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==63) ) {
                alt41=1;
            }
            else if ( (LA41_0==64) ) {
                alt41=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 41, 0, input);

                throw nvae;
            }
            switch (alt41) {
                case 1 :
                    // USE.g:371:7: 'inv' (name= IDENT )? COLON e= expression
                    {
                    match(input,63,FOLLOW_63_in_invariantClause1440); if (state.failed) return n;
                    // USE.g:371:13: (name= IDENT )?
                    int alt39=2;
                    int LA39_0 = input.LA(1);

                    if ( (LA39_0==IDENT) ) {
                        alt39=1;
                    }
                    switch (alt39) {
                        case 1 :
                            // USE.g:371:15: name= IDENT
                            {
                            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantClause1446); if (state.failed) return n;

                            }
                            break;

                    }

                    match(input,COLON,FOLLOW_COLON_in_invariantClause1451); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_invariantClause1455);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTInvariantClause(name, e); 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:372:7: 'existential' 'inv' (name= IDENT )? COLON e= expression
                    {
                    match(input,64,FOLLOW_64_in_invariantClause1465); if (state.failed) return n;
                    match(input,63,FOLLOW_63_in_invariantClause1467); if (state.failed) return n;
                    // USE.g:372:27: (name= IDENT )?
                    int alt40=2;
                    int LA40_0 = input.LA(1);

                    if ( (LA40_0==IDENT) ) {
                        alt40=1;
                    }
                    switch (alt40) {
                        case 1 :
                            // USE.g:372:29: name= IDENT
                            {
                            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantClause1473); if (state.failed) return n;

                            }
                            break;

                    }

                    match(input,COLON,FOLLOW_COLON_in_invariantClause1478); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_invariantClause1482);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTExistentialInvariantClause(name, e); 
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
    // $ANTLR end "invariantClause"


    // $ANTLR start "prePost"
    // USE.g:383:1: prePost returns [ASTPrePost n] : 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ ;
    public final ASTPrePost prePost() throws RecognitionException {
        ASTPrePost n = null;

        Token classname=null;
        Token opname=null;
        List pl = null;

        ASTType rt = null;

        ASTPrePostClause ppc = null;


        try {
            // USE.g:384:1: ( 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ )
            // USE.g:385:5: 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+
            {
            match(input,62,FOLLOW_62_in_prePost1508); if (state.failed) return n;
            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost1512); if (state.failed) return n;
            match(input,COLON_COLON,FOLLOW_COLON_COLON_in_prePost1514); if (state.failed) return n;
            opname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost1518); if (state.failed) return n;
            pushFollow(FOLLOW_paramList_in_prePost1522);
            pl=paramList();

            state._fsp--;
            if (state.failed) return n;
            // USE.g:385:69: ( COLON rt= type )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==COLON) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // USE.g:385:71: COLON rt= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_prePost1526); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_prePost1530);
                    rt=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTPrePost(classname, opname, pl, rt); 
            }
            // USE.g:387:5: (ppc= prePostClause )+
            int cnt43=0;
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( ((LA43_0>=65 && LA43_0<=66)) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // USE.g:387:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_prePost1549);
            	    ppc=prePostClause();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addPrePostClause(ppc); 
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt43 >= 1 ) break loop43;
            	    if (state.backtracking>0) {state.failed=true; return n;}
                        EarlyExitException eee =
                            new EarlyExitException(43, input);
                        throw eee;
                }
                cnt43++;
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
    // $ANTLR end "prePost"


    // $ANTLR start "prePostClause"
    // USE.g:394:1: prePostClause returns [ASTPrePostClause n] : ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression ;
    public final ASTPrePostClause prePostClause() throws RecognitionException {
        ASTPrePostClause n = null;

        Token name=null;
        ASTExpression e = null;


         Token t = null; 
        try {
            // USE.g:396:1: ( ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression )
            // USE.g:397:5: ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression
            {
            if ( state.backtracking==0 ) {
               t = input.LT(1); 
            }
            if ( (input.LA(1)>=65 && input.LA(1)<=66) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // USE.g:398:25: (name= IDENT )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==IDENT) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // USE.g:398:27: name= IDENT
                    {
                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePostClause1603); if (state.failed) return n;

                    }
                    break;

            }

            match(input,COLON,FOLLOW_COLON_in_prePostClause1608); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_prePostClause1612);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTPrePostClause(t, name, e); 
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
    // $ANTLR end "prePostClause"


    // $ANTLR start "alActionList"
    // USE.g:402:1: alActionList returns [ASTALActionList al] : (action= alAction )* ;
    public final ASTALActionList alActionList() throws RecognitionException {
        ASTALActionList al = null;

        ASTALAction action = null;



        	al = new ASTALActionList();

        try {
            // USE.g:406:1: ( (action= alAction )* )
            // USE.g:407:2: (action= alAction )*
            {
            // USE.g:407:2: (action= alAction )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( ((LA45_0>=67 && LA45_0<=70)||LA45_0==72||LA45_0==74||(LA45_0>=76 && LA45_0<=77)||LA45_0==81||LA45_0==84||LA45_0==86) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // USE.g:407:4: action= alAction
            	    {
            	    pushFollow(FOLLOW_alAction_in_alActionList1645);
            	    action=alAction();

            	    state._fsp--;
            	    if (state.failed) return al;
            	    if ( state.backtracking==0 ) {
            	      al.add(action);
            	    }

            	    }
            	    break;

            	default :
            	    break loop45;
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
        return al;
    }
    // $ANTLR end "alActionList"


    // $ANTLR start "alAction"
    // USE.g:410:1: alAction returns [ASTALAction action] : (aCV= alCreateVar | aDl= alDelete | aSe= alSet | aSC= alSetCreate | aIn= alInsert | aDe= alDestroy | aIf= alIf | aWh= alWhile | aFo= alFor | aEx= alExec );
    public final ASTALAction alAction() throws RecognitionException {
        ASTALAction action = null;

        ASTALCreateVar aCV = null;

        ASTALDelete aDl = null;

        ASTALSet aSe = null;

        ASTALSetCreate aSC = null;

        ASTALInsert aIn = null;

        ASTALDestroy aDe = null;

        ASTALIf aIf = null;

        ASTALWhile aWh = null;

        ASTALFor aFo = null;

        ASTALExecute aEx = null;


        try {
            // USE.g:411:1: (aCV= alCreateVar | aDl= alDelete | aSe= alSet | aSC= alSetCreate | aIn= alInsert | aDe= alDestroy | aIf= alIf | aWh= alWhile | aFo= alFor | aEx= alExec )
            int alt46=10;
            switch ( input.LA(1) ) {
            case 67:
            case 68:
                {
                alt46=1;
                }
                break;
            case 74:
                {
                alt46=2;
                }
                break;
            case 69:
                {
                alt46=3;
                }
                break;
            case 70:
                {
                alt46=4;
                }
                break;
            case 72:
                {
                alt46=5;
                }
                break;
            case 76:
                {
                alt46=6;
                }
                break;
            case 77:
                {
                alt46=7;
                }
                break;
            case 81:
                {
                alt46=8;
                }
                break;
            case 84:
                {
                alt46=9;
                }
                break;
            case 86:
                {
                alt46=10;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return action;}
                NoViableAltException nvae =
                    new NoViableAltException("", 46, 0, input);

                throw nvae;
            }

            switch (alt46) {
                case 1 :
                    // USE.g:412:5: aCV= alCreateVar
                    {
                    pushFollow(FOLLOW_alCreateVar_in_alAction1671);
                    aCV=alCreateVar();

                    state._fsp--;
                    if (state.failed) return action;
                    if ( state.backtracking==0 ) {
                       action = aCV; 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:413:5: aDl= alDelete
                    {
                    pushFollow(FOLLOW_alDelete_in_alAction1683);
                    aDl=alDelete();

                    state._fsp--;
                    if (state.failed) return action;
                    if ( state.backtracking==0 ) {
                       action = aDl; 
                    }

                    }
                    break;
                case 3 :
                    // USE.g:414:5: aSe= alSet
                    {
                    pushFollow(FOLLOW_alSet_in_alAction1695);
                    aSe=alSet();

                    state._fsp--;
                    if (state.failed) return action;
                    if ( state.backtracking==0 ) {
                       action = aSe; 
                    }

                    }
                    break;
                case 4 :
                    // USE.g:415:5: aSC= alSetCreate
                    {
                    pushFollow(FOLLOW_alSetCreate_in_alAction1707);
                    aSC=alSetCreate();

                    state._fsp--;
                    if (state.failed) return action;
                    if ( state.backtracking==0 ) {
                       action = aSC; 
                    }

                    }
                    break;
                case 5 :
                    // USE.g:416:5: aIn= alInsert
                    {
                    pushFollow(FOLLOW_alInsert_in_alAction1719);
                    aIn=alInsert();

                    state._fsp--;
                    if (state.failed) return action;
                    if ( state.backtracking==0 ) {
                       action = aIn; 
                    }

                    }
                    break;
                case 6 :
                    // USE.g:417:5: aDe= alDestroy
                    {
                    pushFollow(FOLLOW_alDestroy_in_alAction1731);
                    aDe=alDestroy();

                    state._fsp--;
                    if (state.failed) return action;
                    if ( state.backtracking==0 ) {
                       action = aDe; 
                    }

                    }
                    break;
                case 7 :
                    // USE.g:418:5: aIf= alIf
                    {
                    pushFollow(FOLLOW_alIf_in_alAction1743);
                    aIf=alIf();

                    state._fsp--;
                    if (state.failed) return action;
                    if ( state.backtracking==0 ) {
                       action = aIf; 
                    }

                    }
                    break;
                case 8 :
                    // USE.g:419:5: aWh= alWhile
                    {
                    pushFollow(FOLLOW_alWhile_in_alAction1755);
                    aWh=alWhile();

                    state._fsp--;
                    if (state.failed) return action;
                    if ( state.backtracking==0 ) {
                       action = aWh; 
                    }

                    }
                    break;
                case 9 :
                    // USE.g:420:5: aFo= alFor
                    {
                    pushFollow(FOLLOW_alFor_in_alAction1767);
                    aFo=alFor();

                    state._fsp--;
                    if (state.failed) return action;
                    if ( state.backtracking==0 ) {
                       action = aFo; 
                    }

                    }
                    break;
                case 10 :
                    // USE.g:421:5: aEx= alExec
                    {
                    pushFollow(FOLLOW_alExec_in_alAction1779);
                    aEx=alExec();

                    state._fsp--;
                    if (state.failed) return action;
                    if ( state.backtracking==0 ) {
                       action = aEx; 
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
        return action;
    }
    // $ANTLR end "alAction"


    // $ANTLR start "alCreateVar"
    // USE.g:426:1: alCreateVar returns [ASTALCreateVar var] : ( 'var' | 'declare' ) name= IDENT COLON t= type ;
    public final ASTALCreateVar alCreateVar() throws RecognitionException {
        ASTALCreateVar var = null;

        Token name=null;
        ASTType t = null;


        try {
            // USE.g:427:1: ( ( 'var' | 'declare' ) name= IDENT COLON t= type )
            // USE.g:428:2: ( 'var' | 'declare' ) name= IDENT COLON t= type
            {
            if ( (input.LA(1)>=67 && input.LA(1)<=68) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return var;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_alCreateVar1806); if (state.failed) return var;
            match(input,COLON,FOLLOW_COLON_in_alCreateVar1808); if (state.failed) return var;
            pushFollow(FOLLOW_type_in_alCreateVar1812);
            t=type();

            state._fsp--;
            if (state.failed) return var;
            if ( state.backtracking==0 ) {
               var = new ASTALCreateVar(name, t); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return var;
    }
    // $ANTLR end "alCreateVar"


    // $ANTLR start "alSet"
    // USE.g:432:1: alSet returns [ASTALSet set] : 'set' lval= expression COLON_EQUAL rval= expression ;
    public final ASTALSet alSet() throws RecognitionException {
        ASTALSet set = null;

        ASTExpression lval = null;

        ASTExpression rval = null;


        try {
            // USE.g:433:1: ( 'set' lval= expression COLON_EQUAL rval= expression )
            // USE.g:435:5: 'set' lval= expression COLON_EQUAL rval= expression
            {
            match(input,69,FOLLOW_69_in_alSet1834); if (state.failed) return set;
            pushFollow(FOLLOW_expression_in_alSet1838);
            lval=expression();

            state._fsp--;
            if (state.failed) return set;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_alSet1840); if (state.failed) return set;
            pushFollow(FOLLOW_expression_in_alSet1844);
            rval=expression();

            state._fsp--;
            if (state.failed) return set;
            if ( state.backtracking==0 ) {
               set = new ASTALSet(lval, rval); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return set;
    }
    // $ANTLR end "alSet"


    // $ANTLR start "alSetCreate"
    // USE.g:439:1: alSetCreate returns [ASTALSetCreate setcreate] : 'create' lval= expression COLON_EQUAL {...}?new_= IDENT cls= IDENT ( 'namehint' nameExpr= expression )? ;
    public final ASTALSetCreate alSetCreate() throws RecognitionException {
        ASTALSetCreate setcreate = null;

        Token new_=null;
        Token cls=null;
        ASTExpression lval = null;

        ASTExpression nameExpr = null;


        try {
            // USE.g:440:1: ( 'create' lval= expression COLON_EQUAL {...}?new_= IDENT cls= IDENT ( 'namehint' nameExpr= expression )? )
            // USE.g:441:5: 'create' lval= expression COLON_EQUAL {...}?new_= IDENT cls= IDENT ( 'namehint' nameExpr= expression )?
            {
            match(input,70,FOLLOW_70_in_alSetCreate1868); if (state.failed) return setcreate;
            pushFollow(FOLLOW_expression_in_alSetCreate1872);
            lval=expression();

            state._fsp--;
            if (state.failed) return setcreate;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_alSetCreate1874); if (state.failed) return setcreate;
            if ( !(( input.LT(1).getText().equals("new") )) ) {
                if (state.backtracking>0) {state.failed=true; return setcreate;}
                throw new FailedPredicateException(input, "alSetCreate", " input.LT(1).getText().equals(\"new\") ");
            }
            new_=(Token)match(input,IDENT,FOLLOW_IDENT_in_alSetCreate1880); if (state.failed) return setcreate;
            cls=(Token)match(input,IDENT,FOLLOW_IDENT_in_alSetCreate1884); if (state.failed) return setcreate;
            // USE.g:442:5: ( 'namehint' nameExpr= expression )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==71) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // USE.g:442:7: 'namehint' nameExpr= expression
                    {
                    match(input,71,FOLLOW_71_in_alSetCreate1893); if (state.failed) return setcreate;
                    pushFollow(FOLLOW_expression_in_alSetCreate1897);
                    nameExpr=expression();

                    state._fsp--;
                    if (state.failed) return setcreate;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               setcreate = new ASTALSetCreate(lval, cls, nameExpr);
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return setcreate;
    }
    // $ANTLR end "alSetCreate"


    // $ANTLR start "alInsert"
    // USE.g:447:1: alInsert returns [ASTALInsert insert] : 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT ;
    public final ASTALInsert alInsert() throws RecognitionException {
        ASTALInsert insert = null;

        Token id=null;
        ASTExpression e = null;


        List exprList = new ArrayList(); 
        try {
            // USE.g:449:1: ( 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT )
            // USE.g:450:5: 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT
            {
            match(input,72,FOLLOW_72_in_alInsert1928); if (state.failed) return insert;
            match(input,LPAREN,FOLLOW_LPAREN_in_alInsert1930); if (state.failed) return insert;
            pushFollow(FOLLOW_expression_in_alInsert1939);
            e=expression();

            state._fsp--;
            if (state.failed) return insert;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_alInsert1943); if (state.failed) return insert;
            pushFollow(FOLLOW_expression_in_alInsert1951);
            e=expression();

            state._fsp--;
            if (state.failed) return insert;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // USE.g:452:42: ( COMMA e= expression )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==COMMA) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // USE.g:452:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_alInsert1957); if (state.failed) return insert;
            	    pushFollow(FOLLOW_expression_in_alInsert1961);
            	    e=expression();

            	    state._fsp--;
            	    if (state.failed) return insert;
            	    if ( state.backtracking==0 ) {
            	       exprList.add(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop48;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_alInsert1973); if (state.failed) return insert;
            match(input,73,FOLLOW_73_in_alInsert1975); if (state.failed) return insert;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_alInsert1979); if (state.failed) return insert;
            if ( state.backtracking==0 ) {
               insert = new ASTALInsert(exprList, id); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return insert;
    }
    // $ANTLR end "alInsert"


    // $ANTLR start "alDelete"
    // USE.g:458:1: alDelete returns [ASTALDelete n] : 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT ;
    public final ASTALDelete alDelete() throws RecognitionException {
        ASTALDelete n = null;

        Token id=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // USE.g:460:1: ( 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT )
            // USE.g:461:5: 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT
            {
            match(input,74,FOLLOW_74_in_alDelete2011); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_alDelete2013); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_alDelete2021);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_alDelete2025); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_alDelete2033);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               exprList.add(e); 
            }
            // USE.g:463:42: ( COMMA e= expression )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==COMMA) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // USE.g:463:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_alDelete2039); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_alDelete2043);
            	    e=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       exprList.add(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop49;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_alDelete2054); if (state.failed) return n;
            match(input,75,FOLLOW_75_in_alDelete2056); if (state.failed) return n;
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_alDelete2060); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTALDelete(exprList, id); 
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
    // $ANTLR end "alDelete"


    // $ANTLR start "alDestroy"
    // USE.g:469:1: alDestroy returns [ASTALDestroy n] : 'destroy' e= expression ;
    public final ASTALDestroy alDestroy() throws RecognitionException {
        ASTALDestroy n = null;

        ASTExpression e = null;


        try {
            // USE.g:470:1: ( 'destroy' e= expression )
            // USE.g:471:6: 'destroy' e= expression
            {
            match(input,76,FOLLOW_76_in_alDestroy2089); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_alDestroy2093);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTALDestroy(e); 
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
    // $ANTLR end "alDestroy"


    // $ANTLR start "alIf"
    // USE.g:475:1: alIf returns [ASTALIf i] : 'if' ifexpr= expression 'then' thenlist= alActionList ( 'else' elselist= alActionList )? 'endif' ;
    public final ASTALIf alIf() throws RecognitionException {
        ASTALIf i = null;

        ASTExpression ifexpr = null;

        ASTALActionList thenlist = null;

        ASTALActionList elselist = null;


        try {
            // USE.g:476:1: ( 'if' ifexpr= expression 'then' thenlist= alActionList ( 'else' elselist= alActionList )? 'endif' )
            // USE.g:477:2: 'if' ifexpr= expression 'then' thenlist= alActionList ( 'else' elselist= alActionList )? 'endif'
            {
            match(input,77,FOLLOW_77_in_alIf2117); if (state.failed) return i;
            pushFollow(FOLLOW_expression_in_alIf2121);
            ifexpr=expression();

            state._fsp--;
            if (state.failed) return i;
            match(input,78,FOLLOW_78_in_alIf2125); if (state.failed) return i;
            pushFollow(FOLLOW_alActionList_in_alIf2129);
            thenlist=alActionList();

            state._fsp--;
            if (state.failed) return i;
            // USE.g:479:2: ( 'else' elselist= alActionList )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==79) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // USE.g:479:3: 'else' elselist= alActionList
                    {
                    match(input,79,FOLLOW_79_in_alIf2133); if (state.failed) return i;
                    pushFollow(FOLLOW_alActionList_in_alIf2137);
                    elselist=alActionList();

                    state._fsp--;
                    if (state.failed) return i;

                    }
                    break;

            }

            match(input,80,FOLLOW_80_in_alIf2142); if (state.failed) return i;
            if ( state.backtracking==0 ) {
               i = new ASTALIf(ifexpr, thenlist, elselist); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return i;
    }
    // $ANTLR end "alIf"


    // $ANTLR start "alWhile"
    // USE.g:484:1: alWhile returns [ASTALWhile w] : 'while' expr= expression 'do' body= alActionList 'wend' ;
    public final ASTALWhile alWhile() throws RecognitionException {
        ASTALWhile w = null;

        ASTExpression expr = null;

        ASTALActionList body = null;


        try {
            // USE.g:485:1: ( 'while' expr= expression 'do' body= alActionList 'wend' )
            // USE.g:486:2: 'while' expr= expression 'do' body= alActionList 'wend'
            {
            match(input,81,FOLLOW_81_in_alWhile2161); if (state.failed) return w;
            pushFollow(FOLLOW_expression_in_alWhile2165);
            expr=expression();

            state._fsp--;
            if (state.failed) return w;
            match(input,82,FOLLOW_82_in_alWhile2169); if (state.failed) return w;
            pushFollow(FOLLOW_alActionList_in_alWhile2175);
            body=alActionList();

            state._fsp--;
            if (state.failed) return w;
            match(input,83,FOLLOW_83_in_alWhile2178); if (state.failed) return w;
            if ( state.backtracking==0 ) {
               w = new ASTALWhile(expr, body); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return w;
    }
    // $ANTLR end "alWhile"


    // $ANTLR start "alFor"
    // USE.g:494:1: alFor returns [ASTALFor f] : 'for' var= IDENT COLON t= type 'in' expr= expression 'do' body= alActionList {...}?next= IDENT ;
    public final ASTALFor alFor() throws RecognitionException {
        ASTALFor f = null;

        Token var=null;
        Token next=null;
        ASTType t = null;

        ASTExpression expr = null;

        ASTALActionList body = null;


        try {
            // USE.g:495:1: ( 'for' var= IDENT COLON t= type 'in' expr= expression 'do' body= alActionList {...}?next= IDENT )
            // USE.g:496:2: 'for' var= IDENT COLON t= type 'in' expr= expression 'do' body= alActionList {...}?next= IDENT
            {
            match(input,84,FOLLOW_84_in_alFor2197); if (state.failed) return f;
            var=(Token)match(input,IDENT,FOLLOW_IDENT_in_alFor2201); if (state.failed) return f;
            match(input,COLON,FOLLOW_COLON_in_alFor2203); if (state.failed) return f;
            pushFollow(FOLLOW_type_in_alFor2207);
            t=type();

            state._fsp--;
            if (state.failed) return f;
            match(input,85,FOLLOW_85_in_alFor2209); if (state.failed) return f;
            pushFollow(FOLLOW_expression_in_alFor2213);
            expr=expression();

            state._fsp--;
            if (state.failed) return f;
            match(input,82,FOLLOW_82_in_alFor2217); if (state.failed) return f;
            pushFollow(FOLLOW_alActionList_in_alFor2223);
            body=alActionList();

            state._fsp--;
            if (state.failed) return f;
            if ( !(( input.LT(1).getText().equals("next") )) ) {
                if (state.backtracking>0) {state.failed=true; return f;}
                throw new FailedPredicateException(input, "alFor", " input.LT(1).getText().equals(\"next\") ");
            }
            next=(Token)match(input,IDENT,FOLLOW_IDENT_in_alFor2230); if (state.failed) return f;
            if ( state.backtracking==0 ) {
               f = new ASTALFor(var, t, expr, body); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return f;
    }
    // $ANTLR end "alFor"


    // $ANTLR start "alExec"
    // USE.g:503:1: alExec returns [ASTALExecute c] : 'execute' op= expression ;
    public final ASTALExecute alExec() throws RecognitionException {
        ASTALExecute c = null;

        ASTExpression op = null;


        try {
            // USE.g:504:1: ( 'execute' op= expression )
            // USE.g:505:5: 'execute' op= expression
            {
            match(input,86,FOLLOW_86_in_alExec2250); if (state.failed) return c;
            pushFollow(FOLLOW_expression_in_alExec2254);
            op=expression();

            state._fsp--;
            if (state.failed) return c;
            if ( state.backtracking==0 ) {
               c = new ASTALExecute(op); 
            }

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return c;
    }
    // $ANTLR end "alExec"


    // $ANTLR start "keyUnion"
    // USE.g:509:1: keyUnion : {...}? IDENT ;
    public final void keyUnion() throws RecognitionException {
        try {
            // USE.g:509:9: ({...}? IDENT )
            // USE.g:510:3: {...}? IDENT
            {
            if ( !((input.LT(1).getText().equals("union"))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "keyUnion", "input.LT(1).getText().equals(\"union\")");
            }
            match(input,IDENT,FOLLOW_IDENT_in_keyUnion2272); if (state.failed) return ;

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "keyUnion"


    // $ANTLR start "expressionOnly"
    // USE.g:540:1: expressionOnly returns [ASTExpression n] : nExp= expression EOF ;
    public final ASTExpression expressionOnly() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExp = null;


        try {
            // USE.g:541:1: (nExp= expression EOF )
            // USE.g:542:5: nExp= expression EOF
            {
            pushFollow(FOLLOW_expression_in_expressionOnly2302);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_expressionOnly2304); if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nExp;
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
    // $ANTLR end "expressionOnly"


    // $ANTLR start "expression"
    // USE.g:549:1: expression returns [ASTExpression n] : ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression ;
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
            // USE.g:555:1: ( ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression )
            // USE.g:556:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of expression */ 
            }
            // USE.g:557:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==87) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // USE.g:558:7: 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in'
            	    {
            	    match(input,87,FOLLOW_87_in_expression2352); if (state.failed) return n;
            	    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_expression2356); if (state.failed) return n;
            	    // USE.g:558:24: ( COLON t= type )?
            	    int alt51=2;
            	    int LA51_0 = input.LA(1);

            	    if ( (LA51_0==COLON) ) {
            	        alt51=1;
            	    }
            	    switch (alt51) {
            	        case 1 :
            	            // USE.g:558:26: COLON t= type
            	            {
            	            match(input,COLON,FOLLOW_COLON_in_expression2360); if (state.failed) return n;
            	            pushFollow(FOLLOW_type_in_expression2364);
            	            t=type();

            	            state._fsp--;
            	            if (state.failed) return n;

            	            }
            	            break;

            	    }

            	    match(input,EQUAL,FOLLOW_EQUAL_in_expression2369); if (state.failed) return n;
            	    pushFollow(FOLLOW_expression_in_expression2373);
            	    e1=expression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    match(input,85,FOLLOW_85_in_expression2375); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       ASTLetExpression nextLet = new ASTLetExpression(name, t, e1);
            	               if ( firstLet == null ) 
            	                   firstLet = nextLet;
            	               if ( prevLet != null ) 
            	                   prevLet.setInExpr(nextLet);
            	               prevLet = nextLet;
            	             
            	    }

            	    }
            	    break;

            	default :
            	    break loop52;
                }
            } while (true);

            pushFollow(FOLLOW_conditionalImpliesExpression_in_expression2400);
            nCndImplies=conditionalImpliesExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
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
    // USE.g:586:1: paramList returns [List paramList] : LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN ;
    public final List paramList() throws RecognitionException {
        List paramList = null;

        ASTVariableDeclaration v = null;


         paramList = new ArrayList(); 
        try {
            // USE.g:588:1: ( LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN )
            // USE.g:589:5: LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN
            {
            match(input,LPAREN,FOLLOW_LPAREN_in_paramList2433); if (state.failed) return paramList;
            // USE.g:590:5: (v= variableDeclaration ( COMMA v= variableDeclaration )* )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==IDENT) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // USE.g:591:7: v= variableDeclaration ( COMMA v= variableDeclaration )*
                    {
                    pushFollow(FOLLOW_variableDeclaration_in_paramList2450);
                    v=variableDeclaration();

                    state._fsp--;
                    if (state.failed) return paramList;
                    if ( state.backtracking==0 ) {
                       paramList.add(v); 
                    }
                    // USE.g:592:7: ( COMMA v= variableDeclaration )*
                    loop53:
                    do {
                        int alt53=2;
                        int LA53_0 = input.LA(1);

                        if ( (LA53_0==COMMA) ) {
                            alt53=1;
                        }


                        switch (alt53) {
                    	case 1 :
                    	    // USE.g:592:9: COMMA v= variableDeclaration
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_paramList2462); if (state.failed) return paramList;
                    	    pushFollow(FOLLOW_variableDeclaration_in_paramList2466);
                    	    v=variableDeclaration();

                    	    state._fsp--;
                    	    if (state.failed) return paramList;
                    	    if ( state.backtracking==0 ) {
                    	       paramList.add(v); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop53;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_paramList2486); if (state.failed) return paramList;

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
    // USE.g:600:1: idList returns [List idList] : id0= IDENT ( COMMA idn= IDENT )* ;
    public final List idList() throws RecognitionException {
        List idList = null;

        Token id0=null;
        Token idn=null;

         idList = new ArrayList(); 
        try {
            // USE.g:602:1: (id0= IDENT ( COMMA idn= IDENT )* )
            // USE.g:603:5: id0= IDENT ( COMMA idn= IDENT )*
            {
            id0=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList2515); if (state.failed) return idList;
            if ( state.backtracking==0 ) {
               idList.add(id0); 
            }
            // USE.g:604:5: ( COMMA idn= IDENT )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==COMMA) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // USE.g:604:7: COMMA idn= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_idList2525); if (state.failed) return idList;
            	    idn=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList2529); if (state.failed) return idList;
            	    if ( state.backtracking==0 ) {
            	       idList.add(idn); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop55;
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
    // USE.g:612:1: variableDeclaration returns [ASTVariableDeclaration n] : name= IDENT COLON t= type ;
    public final ASTVariableDeclaration variableDeclaration() throws RecognitionException {
        ASTVariableDeclaration n = null;

        Token name=null;
        ASTType t = null;


        try {
            // USE.g:613:1: (name= IDENT COLON t= type )
            // USE.g:614:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableDeclaration2560); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableDeclaration2562); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableDeclaration2566);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTVariableDeclaration(name, t); 
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
    // $ANTLR end "variableDeclaration"


    // $ANTLR start "conditionalImpliesExpression"
    // USE.g:622:1: conditionalImpliesExpression returns [ASTExpression n] : nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* ;
    public final ASTExpression conditionalImpliesExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndOrExp = null;

        ASTExpression n1 = null;


        try {
            // USE.g:623:1: (nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* )
            // USE.g:624:5: nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )*
            {
            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2602);
            nCndOrExp=conditionalOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndOrExp;
            }
            // USE.g:625:5: (op= 'implies' n1= conditionalOrExpression )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==88) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // USE.g:625:7: op= 'implies' n1= conditionalOrExpression
            	    {
            	    op=(Token)match(input,88,FOLLOW_88_in_conditionalImpliesExpression2615); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2619);
            	    n1=conditionalOrExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop56;
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
    // USE.g:634:1: conditionalOrExpression returns [ASTExpression n] : nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* ;
    public final ASTExpression conditionalOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndXorExp = null;

        ASTExpression n1 = null;


        try {
            // USE.g:635:1: (nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* )
            // USE.g:636:5: nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )*
            {
            pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2664);
            nCndXorExp=conditionalXOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndXorExp;
            }
            // USE.g:637:5: (op= 'or' n1= conditionalXOrExpression )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==89) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // USE.g:637:7: op= 'or' n1= conditionalXOrExpression
            	    {
            	    op=(Token)match(input,89,FOLLOW_89_in_conditionalOrExpression2677); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2681);
            	    n1=conditionalXOrExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop57;
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
    // USE.g:646:1: conditionalXOrExpression returns [ASTExpression n] : nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* ;
    public final ASTExpression conditionalXOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndAndExp = null;

        ASTExpression n1 = null;


        try {
            // USE.g:647:1: (nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* )
            // USE.g:648:5: nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )*
            {
            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2725);
            nCndAndExp=conditionalAndExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndAndExp;
            }
            // USE.g:649:5: (op= 'xor' n1= conditionalAndExpression )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==90) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // USE.g:649:7: op= 'xor' n1= conditionalAndExpression
            	    {
            	    op=(Token)match(input,90,FOLLOW_90_in_conditionalXOrExpression2738); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2742);
            	    n1=conditionalAndExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop58;
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
    // USE.g:658:1: conditionalAndExpression returns [ASTExpression n] : nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* ;
    public final ASTExpression conditionalAndExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nEqExp = null;

        ASTExpression n1 = null;


        try {
            // USE.g:659:1: (nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* )
            // USE.g:660:5: nEqExp= equalityExpression (op= 'and' n1= equalityExpression )*
            {
            pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression2786);
            nEqExp=equalityExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nEqExp;
            }
            // USE.g:661:5: (op= 'and' n1= equalityExpression )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==91) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // USE.g:661:7: op= 'and' n1= equalityExpression
            	    {
            	    op=(Token)match(input,91,FOLLOW_91_in_conditionalAndExpression2799); if (state.failed) return n;
            	    pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression2803);
            	    n1=equalityExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop59;
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
    // USE.g:670:1: equalityExpression returns [ASTExpression n] : nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* ;
    public final ASTExpression equalityExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nRelExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // USE.g:672:1: (nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* )
            // USE.g:673:5: nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            {
            pushFollow(FOLLOW_relationalExpression_in_equalityExpression2851);
            nRelExp=relationalExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nRelExp;
            }
            // USE.g:674:5: ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==EQUAL||LA60_0==NOT_EQUAL) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // USE.g:674:7: ( EQUAL | NOT_EQUAL ) n1= relationalExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       op = input.LT(1); 
            	    }
            	    if ( input.LA(1)==EQUAL||input.LA(1)==NOT_EQUAL ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression2880);
            	    n1=relationalExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop60;
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
    // USE.g:684:1: relationalExpression returns [ASTExpression n] : nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* ;
    public final ASTExpression relationalExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nAddiExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // USE.g:686:1: (nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* )
            // USE.g:687:5: nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            {
            pushFollow(FOLLOW_additiveExpression_in_relationalExpression2929);
            nAddiExp=additiveExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nAddiExp;
            }
            // USE.g:688:5: ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==LESS||(LA61_0>=GREATER && LA61_0<=GREATER_EQUAL)) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // USE.g:688:7: ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       op = input.LT(1); 
            	    }
            	    if ( input.LA(1)==LESS||(input.LA(1)>=GREATER && input.LA(1)<=GREATER_EQUAL) ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_additiveExpression_in_relationalExpression2965);
            	    n1=additiveExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop61;
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
    // USE.g:698:1: additiveExpression returns [ASTExpression n] : nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* ;
    public final ASTExpression additiveExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nMulExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // USE.g:700:1: (nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* )
            // USE.g:701:5: nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            {
            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3015);
            nMulExp=multiplicativeExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nMulExp;
            }
            // USE.g:702:5: ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            loop62:
            do {
                int alt62=2;
                int LA62_0 = input.LA(1);

                if ( ((LA62_0>=PLUS && LA62_0<=MINUS)) ) {
                    alt62=1;
                }


                switch (alt62) {
            	case 1 :
            	    // USE.g:702:7: ( PLUS | MINUS ) n1= multiplicativeExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       op = input.LT(1); 
            	    }
            	    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS) ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression3043);
            	    n1=multiplicativeExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop62;
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
    // USE.g:713:1: multiplicativeExpression returns [ASTExpression n] : nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* ;
    public final ASTExpression multiplicativeExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // USE.g:715:1: (nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* )
            // USE.g:716:5: nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            {
            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3093);
            nUnExp=unaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nUnExp;
            }
            // USE.g:717:5: ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==STAR||LA63_0==SLASH||LA63_0==92) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // USE.g:717:7: ( STAR | SLASH | 'div' ) n1= unaryExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       op = input.LT(1); 
            	    }
            	    if ( input.LA(1)==STAR||input.LA(1)==SLASH||input.LA(1)==92 ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3125);
            	    n1=unaryExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop63;
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
    // USE.g:729:1: unaryExpression returns [ASTExpression n] : ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression );
    public final ASTExpression unaryExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression nPosExp = null;


         Token op = null; 
        try {
            // USE.g:731:1: ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression )
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( ((LA64_0>=PLUS && LA64_0<=MINUS)||LA64_0==93) ) {
                alt64=1;
            }
            else if ( (LA64_0==IDENT||LA64_0==INT||LA64_0==LPAREN||(LA64_0>=REAL && LA64_0<=HASH)||LA64_0==77||(LA64_0>=95 && LA64_0<=110)) ) {
                alt64=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 64, 0, input);

                throw nvae;
            }
            switch (alt64) {
                case 1 :
                    // USE.g:732:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    {
                    // USE.g:732:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    // USE.g:732:9: ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression
                    {
                    if ( state.backtracking==0 ) {
                       op = input.LT(1); 
                    }
                    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS)||input.LA(1)==93 ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression3211);
                    nUnExp=unaryExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUnaryExpression(op, nUnExp); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // USE.g:736:7: nPosExp= postfixExpression
                    {
                    pushFollow(FOLLOW_postfixExpression_in_unaryExpression3231);
                    nPosExp=postfixExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nPosExp; 
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
    // $ANTLR end "unaryExpression"


    // $ANTLR start "postfixExpression"
    // USE.g:744:1: postfixExpression returns [ASTExpression n] : nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* ;
    public final ASTExpression postfixExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nPrimExp = null;

        ASTExpression nPc = null;


         boolean arrow = false; 
        try {
            // USE.g:746:1: (nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* )
            // USE.g:747:5: nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            {
            pushFollow(FOLLOW_primaryExpression_in_postfixExpression3264);
            nPrimExp=primaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nPrimExp; 
            }
            // USE.g:748:5: ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( ((LA66_0>=ARROW && LA66_0<=DOT)) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // USE.g:749:6: ( ARROW | DOT ) nPc= propertyCall[$n, arrow]
            	    {
            	    // USE.g:749:6: ( ARROW | DOT )
            	    int alt65=2;
            	    int LA65_0 = input.LA(1);

            	    if ( (LA65_0==ARROW) ) {
            	        alt65=1;
            	    }
            	    else if ( (LA65_0==DOT) ) {
            	        alt65=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 65, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt65) {
            	        case 1 :
            	            // USE.g:749:8: ARROW
            	            {
            	            match(input,ARROW,FOLLOW_ARROW_in_postfixExpression3282); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = true; 
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // USE.g:749:34: DOT
            	            {
            	            match(input,DOT,FOLLOW_DOT_in_postfixExpression3288); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = false; 
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_propertyCall_in_postfixExpression3299);
            	    nPc=propertyCall(n, arrow);

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = nPc; 
            	    }

            	    }
            	    break;

            	default :
            	    break loop66;
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
    // USE.g:765:1: primaryExpression returns [ASTExpression n] : (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? );
    public final ASTExpression primaryExpression() throws RecognitionException {
        ASTExpression n = null;

        Token id1=null;
        ASTExpression nLit = null;

        ASTExpression nPc = null;

        ASTExpression nExp = null;

        ASTExpression nIfExp = null;


        try {
            // USE.g:766:1: (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? )
            int alt69=5;
            switch ( input.LA(1) ) {
            case INT:
            case REAL:
            case STRING:
            case HASH:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
                {
                alt69=1;
                }
                break;
            case IDENT:
                {
                int LA69_2 = input.LA(2);

                if ( (LA69_2==EOF||LA69_2==IDENT||(LA69_2>=RBRACE && LA69_2<=LESS)||(LA69_2>=EQUAL && LA69_2<=LBRACK)||(LA69_2>=COMMA && LA69_2<=DOTDOT)||LA69_2==STAR||(LA69_2>=COLON_EQUAL && LA69_2<=ARROW)||(LA69_2>=AT && LA69_2<=BAR)||LA69_2==44||(LA69_2>=46 && LA69_2<=47)||(LA69_2>=50 && LA69_2<=52)||(LA69_2>=54 && LA69_2<=57)||(LA69_2>=62 && LA69_2<=70)||LA69_2==72||LA69_2==74||(LA69_2>=76 && LA69_2<=86)||(LA69_2>=88 && LA69_2<=92)) ) {
                    alt69=2;
                }
                else if ( (LA69_2==DOT) ) {
                    int LA69_6 = input.LA(3);

                    if ( (LA69_6==94) ) {
                        alt69=5;
                    }
                    else if ( (LA69_6==IDENT||(LA69_6>=95 && LA69_6<=98)) ) {
                        alt69=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 69, 6, input);

                        throw nvae;
                    }
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 69, 2, input);

                    throw nvae;
                }
                }
                break;
            case 95:
            case 96:
            case 97:
            case 98:
                {
                alt69=2;
                }
                break;
            case LPAREN:
                {
                alt69=3;
                }
                break;
            case 77:
                {
                alt69=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 69, 0, input);

                throw nvae;
            }

            switch (alt69) {
                case 1 :
                    // USE.g:767:7: nLit= literal
                    {
                    pushFollow(FOLLOW_literal_in_primaryExpression3339);
                    nLit=literal();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nLit; 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:768:7: nPc= propertyCall[null, false]
                    {
                    pushFollow(FOLLOW_propertyCall_in_primaryExpression3351);
                    nPc=propertyCall(null, false);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nPc; 
                    }

                    }
                    break;
                case 3 :
                    // USE.g:769:7: LPAREN nExp= expression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression3362); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_primaryExpression3366);
                    nExp=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression3368); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExp; 
                    }

                    }
                    break;
                case 4 :
                    // USE.g:770:7: nIfExp= ifExpression
                    {
                    pushFollow(FOLLOW_ifExpression_in_primaryExpression3380);
                    nIfExp=ifExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nIfExp; 
                    }

                    }
                    break;
                case 5 :
                    // USE.g:772:7: id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )?
                    {
                    id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_primaryExpression3397); if (state.failed) return n;
                    match(input,DOT,FOLLOW_DOT_in_primaryExpression3399); if (state.failed) return n;
                    match(input,94,FOLLOW_94_in_primaryExpression3401); if (state.failed) return n;
                    // USE.g:772:36: ( LPAREN RPAREN )?
                    int alt67=2;
                    int LA67_0 = input.LA(1);

                    if ( (LA67_0==LPAREN) ) {
                        alt67=1;
                    }
                    switch (alt67) {
                        case 1 :
                            // USE.g:772:38: LPAREN RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression3405); if (state.failed) return n;
                            match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression3407); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTAllInstancesExpression(id1); 
                    }
                    // USE.g:774:7: ( AT 'pre' )?
                    int alt68=2;
                    int LA68_0 = input.LA(1);

                    if ( (LA68_0==AT) ) {
                        alt68=1;
                    }
                    switch (alt68) {
                        case 1 :
                            // USE.g:774:9: AT 'pre'
                            {
                            match(input,AT,FOLLOW_AT_in_primaryExpression3428); if (state.failed) return n;
                            match(input,65,FOLLOW_65_in_primaryExpression3430); if (state.failed) return n;
                            if ( state.backtracking==0 ) {
                               n.setIsPre(); 
                            }

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
    // USE.g:787:1: propertyCall[ASTExpression source, boolean followsArrow] returns [ASTExpression n] : ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] );
    public final ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExpQuery = null;

        ASTExpression nExpIterate = null;

        ASTOperationExpression nExpOperation = null;

        ASTTypeArgExpression nExpType = null;


        try {
            // USE.g:788:1: ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] )
            int alt70=4;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA70_1 = input.LA(2);

                if ( ((( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )&&( input.LA(2) == LPAREN ))) ) {
                    alt70=1;
                }
                else if ( (true) ) {
                    alt70=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 70, 1, input);

                    throw nvae;
                }
                }
                break;
            case 95:
                {
                alt70=2;
                }
                break;
            case 96:
            case 97:
            case 98:
                {
                alt70=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 70, 0, input);

                throw nvae;
            }

            switch (alt70) {
                case 1 :
                    // USE.g:792:7: {...}?{...}?nExpQuery= queryExpression[source]
                    {
                    if ( !(( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) ");
                    }
                    if ( !(( input.LA(2) == LPAREN )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " input.LA(2) == LPAREN ");
                    }
                    pushFollow(FOLLOW_queryExpression_in_propertyCall3503);
                    nExpQuery=queryExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpQuery; 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:795:7: nExpIterate= iterateExpression[source]
                    {
                    pushFollow(FOLLOW_iterateExpression_in_propertyCall3516);
                    nExpIterate=iterateExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpIterate; 
                    }

                    }
                    break;
                case 3 :
                    // USE.g:796:7: nExpOperation= operationExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_operationExpression_in_propertyCall3529);
                    nExpOperation=operationExpression(source, followsArrow);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpOperation; 
                    }

                    }
                    break;
                case 4 :
                    // USE.g:797:7: nExpType= typeExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_typeExpression_in_propertyCall3542);
                    nExpType=typeExpression(source, followsArrow);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpType; 
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
    // $ANTLR end "propertyCall"


    // $ANTLR start "queryExpression"
    // USE.g:806:1: queryExpression[ASTExpression range] returns [ASTExpression n] : op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN ;
    public final ASTExpression queryExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTElemVarsDeclaration decls = null;

        ASTExpression nExp = null;


        ASTElemVarsDeclaration decl = new ASTElemVarsDeclaration(); 
        try {
            // USE.g:807:69: (op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN )
            // USE.g:808:5: op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN
            {
            op=(Token)match(input,IDENT,FOLLOW_IDENT_in_queryExpression3577); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_queryExpression3584); if (state.failed) return n;
            // USE.g:810:5: (decls= elemVarsDeclaration BAR )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==IDENT) ) {
                int LA71_1 = input.LA(2);

                if ( (LA71_1==COLON||LA71_1==COMMA||LA71_1==BAR) ) {
                    alt71=1;
                }
            }
            switch (alt71) {
                case 1 :
                    // USE.g:810:7: decls= elemVarsDeclaration BAR
                    {
                    pushFollow(FOLLOW_elemVarsDeclaration_in_queryExpression3595);
                    decls=elemVarsDeclaration();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      decl = decls;
                    }
                    match(input,BAR,FOLLOW_BAR_in_queryExpression3599); if (state.failed) return n;

                    }
                    break;

            }

            pushFollow(FOLLOW_expression_in_queryExpression3610);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_queryExpression3616); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTQueryExpression(op, range, decl, nExp); 
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
    // $ANTLR end "queryExpression"


    // $ANTLR start "iterateExpression"
    // USE.g:824:1: iterateExpression[ASTExpression range] returns [ASTExpression n] : i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN ;
    public final ASTExpression iterateExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTElemVarsDeclaration decls = null;

        ASTVariableInitialization init = null;

        ASTExpression nExp = null;


        try {
            // USE.g:824:65: (i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN )
            // USE.g:825:5: i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN
            {
            i=(Token)match(input,95,FOLLOW_95_in_iterateExpression3648); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_iterateExpression3654); if (state.failed) return n;
            pushFollow(FOLLOW_elemVarsDeclaration_in_iterateExpression3662);
            decls=elemVarsDeclaration();

            state._fsp--;
            if (state.failed) return n;
            match(input,SEMI,FOLLOW_SEMI_in_iterateExpression3664); if (state.failed) return n;
            pushFollow(FOLLOW_variableInitialization_in_iterateExpression3672);
            init=variableInitialization();

            state._fsp--;
            if (state.failed) return n;
            match(input,BAR,FOLLOW_BAR_in_iterateExpression3674); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_iterateExpression3682);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_iterateExpression3688); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTIterateExpression(i, range, decls, init, nExp); 
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
    // $ANTLR end "iterateExpression"


    // $ANTLR start "operationExpression"
    // USE.g:846:1: operationExpression[ASTExpression source, boolean followsArrow] returns [ASTOperationExpression n] : name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? ;
    public final ASTOperationExpression operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTOperationExpression n = null;

        Token name=null;
        Token rolename=null;
        ASTExpression e = null;


        try {
            // USE.g:848:1: (name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? )
            // USE.g:849:5: name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression3732); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTOperationExpression(name, source, followsArrow); 
            }
            // USE.g:852:5: ( LBRACK rolename= IDENT RBRACK )?
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==LBRACK) ) {
                alt72=1;
            }
            switch (alt72) {
                case 1 :
                    // USE.g:852:7: LBRACK rolename= IDENT RBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_operationExpression3748); if (state.failed) return n;
                    rolename=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression3752); if (state.failed) return n;
                    match(input,RBRACK,FOLLOW_RBRACK_in_operationExpression3754); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setExplicitRolename(rolename); 
                    }

                    }
                    break;

            }

            // USE.g:854:5: ( AT 'pre' )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==AT) ) {
                alt73=1;
            }
            switch (alt73) {
                case 1 :
                    // USE.g:854:7: AT 'pre'
                    {
                    match(input,AT,FOLLOW_AT_in_operationExpression3767); if (state.failed) return n;
                    match(input,65,FOLLOW_65_in_operationExpression3769); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setIsPre(); 
                    }

                    }
                    break;

            }

            // USE.g:855:5: ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==LPAREN) ) {
                alt76=1;
            }
            switch (alt76) {
                case 1 :
                    // USE.g:856:7: LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_operationExpression3790); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.hasParentheses(); 
                    }
                    // USE.g:857:7: (e= expression ( COMMA e= expression )* )?
                    int alt75=2;
                    int LA75_0 = input.LA(1);

                    if ( (LA75_0==IDENT||LA75_0==INT||LA75_0==LPAREN||(LA75_0>=PLUS && LA75_0<=MINUS)||(LA75_0>=REAL && LA75_0<=HASH)||LA75_0==77||LA75_0==87||LA75_0==93||(LA75_0>=95 && LA75_0<=110)) ) {
                        alt75=1;
                    }
                    switch (alt75) {
                        case 1 :
                            // USE.g:858:7: e= expression ( COMMA e= expression )*
                            {
                            pushFollow(FOLLOW_expression_in_operationExpression3811);
                            e=expression();

                            state._fsp--;
                            if (state.failed) return n;
                            if ( state.backtracking==0 ) {
                               n.addArg(e); 
                            }
                            // USE.g:859:7: ( COMMA e= expression )*
                            loop74:
                            do {
                                int alt74=2;
                                int LA74_0 = input.LA(1);

                                if ( (LA74_0==COMMA) ) {
                                    alt74=1;
                                }


                                switch (alt74) {
                            	case 1 :
                            	    // USE.g:859:9: COMMA e= expression
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_operationExpression3823); if (state.failed) return n;
                            	    pushFollow(FOLLOW_expression_in_operationExpression3827);
                            	    e=expression();

                            	    state._fsp--;
                            	    if (state.failed) return n;
                            	    if ( state.backtracking==0 ) {
                            	       n.addArg(e); 
                            	    }

                            	    }
                            	    break;

                            	default :
                            	    break loop74;
                                }
                            } while (true);


                            }
                            break;

                    }

                    match(input,RPAREN,FOLLOW_RPAREN_in_operationExpression3847); if (state.failed) return n;

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
    // USE.g:871:1: typeExpression[ASTExpression source, boolean followsArrow] returns [ASTTypeArgExpression n] : ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN ;
    public final ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTTypeArgExpression n = null;

        ASTType t = null;


         Token opToken = null; 
        try {
            // USE.g:874:1: ( ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN )
            // USE.g:875:2: ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN
            {
            if ( state.backtracking==0 ) {
               opToken = input.LT(1); 
            }
            if ( (input.LA(1)>=96 && input.LA(1)<=98) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_typeExpression3906); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_typeExpression3910);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_typeExpression3912); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTTypeArgExpression(opToken, source, t, followsArrow); 
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
    // $ANTLR end "typeExpression"


    // $ANTLR start "elemVarsDeclaration"
    // USE.g:886:1: elemVarsDeclaration returns [ASTElemVarsDeclaration n] : idListRes= idList ( COLON t= type )? ;
    public final ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException {
        ASTElemVarsDeclaration n = null;

        List idListRes = null;

        ASTType t = null;


         List idList; 
        try {
            // USE.g:888:1: (idListRes= idList ( COLON t= type )? )
            // USE.g:889:5: idListRes= idList ( COLON t= type )?
            {
            pushFollow(FOLLOW_idList_in_elemVarsDeclaration3951);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            // USE.g:890:5: ( COLON t= type )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==COLON) ) {
                alt77=1;
            }
            switch (alt77) {
                case 1 :
                    // USE.g:890:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_elemVarsDeclaration3959); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_elemVarsDeclaration3963);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTElemVarsDeclaration(idListRes, t); 
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
    // $ANTLR end "elemVarsDeclaration"


    // $ANTLR start "variableInitialization"
    // USE.g:899:1: variableInitialization returns [ASTVariableInitialization n] : name= IDENT COLON t= type EQUAL e= expression ;
    public final ASTVariableInitialization variableInitialization() throws RecognitionException {
        ASTVariableInitialization n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // USE.g:900:1: (name= IDENT COLON t= type EQUAL e= expression )
            // USE.g:901:5: name= IDENT COLON t= type EQUAL e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableInitialization3998); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableInitialization4000); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableInitialization4004);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EQUAL,FOLLOW_EQUAL_in_variableInitialization4006); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_variableInitialization4010);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTVariableInitialization(name, t, e); 
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
    // $ANTLR end "variableInitialization"


    // $ANTLR start "ifExpression"
    // USE.g:910:1: ifExpression returns [ASTExpression n] : i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' ;
    public final ASTExpression ifExpression() throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTExpression cond = null;

        ASTExpression t = null;

        ASTExpression e = null;


        try {
            // USE.g:911:1: (i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' )
            // USE.g:912:5: i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif'
            {
            i=(Token)match(input,77,FOLLOW_77_in_ifExpression4042); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4046);
            cond=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,78,FOLLOW_78_in_ifExpression4048); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4052);
            t=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,79,FOLLOW_79_in_ifExpression4054); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression4058);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,80,FOLLOW_80_in_ifExpression4060); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTIfExpression(i, cond, t, e); 
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
    // $ANTLR end "ifExpression"


    // $ANTLR start "literal"
    // USE.g:931:1: literal returns [ASTExpression n] : (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral );
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
            // USE.g:932:1: (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral )
            int alt78=11;
            switch ( input.LA(1) ) {
            case 99:
                {
                alt78=1;
                }
                break;
            case 100:
                {
                alt78=2;
                }
                break;
            case INT:
                {
                alt78=3;
                }
                break;
            case REAL:
                {
                alt78=4;
                }
                break;
            case STRING:
                {
                alt78=5;
                }
                break;
            case HASH:
                {
                alt78=6;
                }
                break;
            case 101:
            case 102:
            case 103:
            case 104:
                {
                alt78=7;
                }
                break;
            case 105:
                {
                alt78=8;
                }
                break;
            case 106:
            case 107:
            case 108:
                {
                alt78=9;
                }
                break;
            case 109:
                {
                alt78=10;
                }
                break;
            case 110:
                {
                alt78=11;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 78, 0, input);

                throw nvae;
            }

            switch (alt78) {
                case 1 :
                    // USE.g:933:7: t= 'true'
                    {
                    t=(Token)match(input,99,FOLLOW_99_in_literal4099); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(true); 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:934:7: f= 'false'
                    {
                    f=(Token)match(input,100,FOLLOW_100_in_literal4113); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(false); 
                    }

                    }
                    break;
                case 3 :
                    // USE.g:935:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_literal4126); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTIntegerLiteral(i); 
                    }

                    }
                    break;
                case 4 :
                    // USE.g:936:7: r= REAL
                    {
                    r=(Token)match(input,REAL,FOLLOW_REAL_in_literal4141); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTRealLiteral(r); 
                    }

                    }
                    break;
                case 5 :
                    // USE.g:937:7: s= STRING
                    {
                    s=(Token)match(input,STRING,FOLLOW_STRING_in_literal4155); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTStringLiteral(s); 
                    }

                    }
                    break;
                case 6 :
                    // USE.g:938:7: HASH enumLit= IDENT
                    {
                    match(input,HASH,FOLLOW_HASH_in_literal4165); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal4169); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumLit); 
                    }

                    }
                    break;
                case 7 :
                    // USE.g:939:7: nColIt= collectionLiteral
                    {
                    pushFollow(FOLLOW_collectionLiteral_in_literal4181);
                    nColIt=collectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nColIt; 
                    }

                    }
                    break;
                case 8 :
                    // USE.g:940:7: nEColIt= emptyCollectionLiteral
                    {
                    pushFollow(FOLLOW_emptyCollectionLiteral_in_literal4193);
                    nEColIt=emptyCollectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nEColIt; 
                    }

                    }
                    break;
                case 9 :
                    // USE.g:941:7: nUndLit= undefinedLiteral
                    {
                    pushFollow(FOLLOW_undefinedLiteral_in_literal4205);
                    nUndLit=undefinedLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nUndLit; 
                    }

                    }
                    break;
                case 10 :
                    // USE.g:942:7: nTupleLit= tupleLiteral
                    {
                    pushFollow(FOLLOW_tupleLiteral_in_literal4217);
                    nTupleLit=tupleLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nTupleLit; 
                    }

                    }
                    break;
                case 11 :
                    // USE.g:943:7: nDateLit= dateLiteral
                    {
                    pushFollow(FOLLOW_dateLiteral_in_literal4229);
                    nDateLit=dateLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nDateLit; 
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
    // $ANTLR end "literal"


    // $ANTLR start "collectionLiteral"
    // USE.g:951:1: collectionLiteral returns [ASTCollectionLiteral n] : ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE ;
    public final ASTCollectionLiteral collectionLiteral() throws RecognitionException {
        ASTCollectionLiteral n = null;

        ASTCollectionItem ci = null;


         Token op = null; 
        try {
            // USE.g:953:1: ( ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE )
            // USE.g:954:5: ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE
            {
            if ( state.backtracking==0 ) {
               op = input.LT(1); 
            }
            if ( (input.LA(1)>=101 && input.LA(1)<=104) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            if ( state.backtracking==0 ) {
               n = new ASTCollectionLiteral(op); 
            }
            match(input,LBRACE,FOLLOW_LBRACE_in_collectionLiteral4296); if (state.failed) return n;
            // USE.g:958:5: (ci= collectionItem ( COMMA ci= collectionItem )* )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==IDENT||LA80_0==INT||LA80_0==LPAREN||(LA80_0>=PLUS && LA80_0<=MINUS)||(LA80_0>=REAL && LA80_0<=HASH)||LA80_0==77||LA80_0==87||LA80_0==93||(LA80_0>=95 && LA80_0<=110)) ) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // USE.g:959:7: ci= collectionItem ( COMMA ci= collectionItem )*
                    {
                    pushFollow(FOLLOW_collectionItem_in_collectionLiteral4313);
                    ci=collectionItem();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addItem(ci); 
                    }
                    // USE.g:960:7: ( COMMA ci= collectionItem )*
                    loop79:
                    do {
                        int alt79=2;
                        int LA79_0 = input.LA(1);

                        if ( (LA79_0==COMMA) ) {
                            alt79=1;
                        }


                        switch (alt79) {
                    	case 1 :
                    	    // USE.g:960:9: COMMA ci= collectionItem
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_collectionLiteral4326); if (state.failed) return n;
                    	    pushFollow(FOLLOW_collectionItem_in_collectionLiteral4330);
                    	    ci=collectionItem();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addItem(ci); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop79;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RBRACE,FOLLOW_RBRACE_in_collectionLiteral4349); if (state.failed) return n;

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
    // USE.g:969:1: collectionItem returns [ASTCollectionItem n] : e= expression ( DOTDOT e= expression )? ;
    public final ASTCollectionItem collectionItem() throws RecognitionException {
        ASTCollectionItem n = null;

        ASTExpression e = null;


         n = new ASTCollectionItem(); 
        try {
            // USE.g:971:1: (e= expression ( DOTDOT e= expression )? )
            // USE.g:972:5: e= expression ( DOTDOT e= expression )?
            {
            pushFollow(FOLLOW_expression_in_collectionItem4378);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setFirst(e); 
            }
            // USE.g:973:5: ( DOTDOT e= expression )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==DOTDOT) ) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // USE.g:973:7: DOTDOT e= expression
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_collectionItem4389); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_collectionItem4393);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setSecond(e); 
                    }

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
    // USE.g:983:1: emptyCollectionLiteral returns [ASTEmptyCollectionLiteral n] : 'oclEmpty' LPAREN t= collectionType RPAREN ;
    public final ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException {
        ASTEmptyCollectionLiteral n = null;

        ASTCollectionType t = null;


        try {
            // USE.g:984:1: ( 'oclEmpty' LPAREN t= collectionType RPAREN )
            // USE.g:985:5: 'oclEmpty' LPAREN t= collectionType RPAREN
            {
            match(input,105,FOLLOW_105_in_emptyCollectionLiteral4422); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_emptyCollectionLiteral4424); if (state.failed) return n;
            pushFollow(FOLLOW_collectionType_in_emptyCollectionLiteral4428);
            t=collectionType();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_emptyCollectionLiteral4430); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTEmptyCollectionLiteral(t); 
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
    // $ANTLR end "emptyCollectionLiteral"


    // $ANTLR start "undefinedLiteral"
    // USE.g:996:1: undefinedLiteral returns [ASTUndefinedLiteral n] : ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' );
    public final ASTUndefinedLiteral undefinedLiteral() throws RecognitionException {
        ASTUndefinedLiteral n = null;

        ASTType t = null;


        try {
            // USE.g:997:1: ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' )
            int alt82=3;
            switch ( input.LA(1) ) {
            case 106:
                {
                alt82=1;
                }
                break;
            case 107:
                {
                alt82=2;
                }
                break;
            case 108:
                {
                alt82=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 82, 0, input);

                throw nvae;
            }

            switch (alt82) {
                case 1 :
                    // USE.g:998:5: 'oclUndefined' LPAREN t= type RPAREN
                    {
                    match(input,106,FOLLOW_106_in_undefinedLiteral4460); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_undefinedLiteral4462); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_undefinedLiteral4466);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_undefinedLiteral4468); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(t); 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:1001:5: 'Undefined'
                    {
                    match(input,107,FOLLOW_107_in_undefinedLiteral4482); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(); 
                    }

                    }
                    break;
                case 3 :
                    // USE.g:1004:5: 'null'
                    {
                    match(input,108,FOLLOW_108_in_undefinedLiteral4496); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(); 
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
    // $ANTLR end "undefinedLiteral"


    // $ANTLR start "tupleLiteral"
    // USE.g:1013:1: tupleLiteral returns [ASTTupleLiteral n] : 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE ;
    public final ASTTupleLiteral tupleLiteral() throws RecognitionException {
        ASTTupleLiteral n = null;

        ASTTupleItem ti = null;


         List tiList = new ArrayList(); 
        try {
            // USE.g:1015:1: ( 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE )
            // USE.g:1016:5: 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE
            {
            match(input,109,FOLLOW_109_in_tupleLiteral4530); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_tupleLiteral4536); if (state.failed) return n;
            pushFollow(FOLLOW_tupleItem_in_tupleLiteral4544);
            ti=tupleItem();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tiList.add(ti); 
            }
            // USE.g:1019:5: ( COMMA ti= tupleItem )*
            loop83:
            do {
                int alt83=2;
                int LA83_0 = input.LA(1);

                if ( (LA83_0==COMMA) ) {
                    alt83=1;
                }


                switch (alt83) {
            	case 1 :
            	    // USE.g:1019:7: COMMA ti= tupleItem
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleLiteral4555); if (state.failed) return n;
            	    pushFollow(FOLLOW_tupleItem_in_tupleLiteral4559);
            	    ti=tupleItem();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tiList.add(ti); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop83;
                }
            } while (true);

            match(input,RBRACE,FOLLOW_RBRACE_in_tupleLiteral4570); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTTupleLiteral(tiList); 
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
    // $ANTLR end "tupleLiteral"


    // $ANTLR start "tupleItem"
    // USE.g:1027:1: tupleItem returns [ASTTupleItem n] : name= IDENT ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) ;
    public final ASTTupleItem tupleItem() throws RecognitionException {
        ASTTupleItem n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // USE.g:1028:1: (name= IDENT ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) )
            // USE.g:1029:5: name= IDENT ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tupleItem4601); if (state.failed) return n;
            // USE.g:1030:5: ( ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==COLON) ) {
                int LA84_1 = input.LA(2);

                if ( (synpred1_USE()) ) {
                    alt84=1;
                }
                else if ( (true) ) {
                    alt84=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 84, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA84_0==EQUAL) ) {
                alt84=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 84, 0, input);

                throw nvae;
            }
            switch (alt84) {
                case 1 :
                    // USE.g:1033:7: ( COLON IDENT EQUAL )=> COLON t= type EQUAL e= expression
                    {
                    match(input,COLON,FOLLOW_COLON_in_tupleItem4640); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_tupleItem4644);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,EQUAL,FOLLOW_EQUAL_in_tupleItem4646); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_tupleItem4650);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTTupleItem(name, t, e); 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:1036:7: ( COLON | EQUAL ) e= expression
                    {
                    if ( (input.LA(1)>=COLON && input.LA(1)<=EQUAL) ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_expression_in_tupleItem4682);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTTupleItem(name, e); 
                    }

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
    // $ANTLR end "tupleItem"


    // $ANTLR start "dateLiteral"
    // USE.g:1045:1: dateLiteral returns [ASTDateLiteral n] : 'Date' LBRACE v= STRING RBRACE ;
    public final ASTDateLiteral dateLiteral() throws RecognitionException {
        ASTDateLiteral n = null;

        Token v=null;

        try {
            // USE.g:1046:1: ( 'Date' LBRACE v= STRING RBRACE )
            // USE.g:1047:5: 'Date' LBRACE v= STRING RBRACE
            {
            match(input,110,FOLLOW_110_in_dateLiteral4727); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_dateLiteral4729); if (state.failed) return n;
            v=(Token)match(input,STRING,FOLLOW_STRING_in_dateLiteral4733); if (state.failed) return n;
            match(input,RBRACE,FOLLOW_RBRACE_in_dateLiteral4735); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTDateLiteral( v ); 
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
    // $ANTLR end "dateLiteral"


    // $ANTLR start "type"
    // USE.g:1057:1: type returns [ASTType n] : (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) ;
    public final ASTType type() throws RecognitionException {
        ASTType n = null;

        ASTSimpleType nTSimple = null;

        ASTCollectionType nTCollection = null;

        ASTTupleType nTTuple = null;


         Token tok = null; 
        try {
            // USE.g:1059:1: ( (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) )
            // USE.g:1060:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of type */ 
            }
            // USE.g:1061:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            int alt85=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt85=1;
                }
                break;
            case 101:
            case 102:
            case 103:
            case 104:
            case 111:
                {
                alt85=2;
                }
                break;
            case 109:
                {
                alt85=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 85, 0, input);

                throw nvae;
            }

            switch (alt85) {
                case 1 :
                    // USE.g:1062:7: nTSimple= simpleType
                    {
                    pushFollow(FOLLOW_simpleType_in_type4785);
                    nTSimple=simpleType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTSimple; n.setStartToken(tok); 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:1063:7: nTCollection= collectionType
                    {
                    pushFollow(FOLLOW_collectionType_in_type4797);
                    nTCollection=collectionType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTCollection; n.setStartToken(tok); 
                    }

                    }
                    break;
                case 3 :
                    // USE.g:1064:7: nTTuple= tupleType
                    {
                    pushFollow(FOLLOW_tupleType_in_type4809);
                    nTTuple=tupleType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTTuple; n.setStartToken(tok); 
                    }

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
    // USE.g:1069:1: typeOnly returns [ASTType n] : nT= type EOF ;
    public final ASTType typeOnly() throws RecognitionException {
        ASTType n = null;

        ASTType nT = null;


        try {
            // USE.g:1070:1: (nT= type EOF )
            // USE.g:1071:5: nT= type EOF
            {
            pushFollow(FOLLOW_type_in_typeOnly4841);
            nT=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_typeOnly4843); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nT; 
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
    // $ANTLR end "typeOnly"


    // $ANTLR start "simpleType"
    // USE.g:1081:1: simpleType returns [ASTSimpleType n] : name= IDENT ;
    public final ASTSimpleType simpleType() throws RecognitionException {
        ASTSimpleType n = null;

        Token name=null;

        try {
            // USE.g:1082:1: (name= IDENT )
            // USE.g:1083:5: name= IDENT
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_simpleType4871); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTSimpleType(name); 
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
    // $ANTLR end "simpleType"


    // $ANTLR start "collectionType"
    // USE.g:1091:1: collectionType returns [ASTCollectionType n] : ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN ;
    public final ASTCollectionType collectionType() throws RecognitionException {
        ASTCollectionType n = null;

        ASTType elemType = null;


         Token op = null; 
        try {
            // USE.g:1093:1: ( ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN )
            // USE.g:1094:5: ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN
            {
            if ( state.backtracking==0 ) {
               op = input.LT(1); 
            }
            if ( (input.LA(1)>=101 && input.LA(1)<=104)||input.LA(1)==111 ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_collectionType4936); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_collectionType4940);
            elemType=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_collectionType4942); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTCollectionType(op, elemType); n.setStartToken(op);
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
    // $ANTLR end "collectionType"


    // $ANTLR start "tupleType"
    // USE.g:1104:1: tupleType returns [ASTTupleType n] : 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN ;
    public final ASTTupleType tupleType() throws RecognitionException {
        ASTTupleType n = null;

        ASTTuplePart tp = null;


         List tpList = new ArrayList(); 
        try {
            // USE.g:1106:1: ( 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN )
            // USE.g:1107:5: 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN
            {
            match(input,109,FOLLOW_109_in_tupleType4976); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_tupleType4978); if (state.failed) return n;
            pushFollow(FOLLOW_tuplePart_in_tupleType4987);
            tp=tuplePart();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tpList.add(tp); 
            }
            // USE.g:1109:5: ( COMMA tp= tuplePart )*
            loop86:
            do {
                int alt86=2;
                int LA86_0 = input.LA(1);

                if ( (LA86_0==COMMA) ) {
                    alt86=1;
                }


                switch (alt86) {
            	case 1 :
            	    // USE.g:1109:7: COMMA tp= tuplePart
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleType4998); if (state.failed) return n;
            	    pushFollow(FOLLOW_tuplePart_in_tupleType5002);
            	    tp=tuplePart();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tpList.add(tp); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop86;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_tupleType5014); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTTupleType(tpList); 
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
    // $ANTLR end "tupleType"


    // $ANTLR start "tuplePart"
    // USE.g:1118:1: tuplePart returns [ASTTuplePart n] : name= IDENT COLON t= type ;
    public final ASTTuplePart tuplePart() throws RecognitionException {
        ASTTuplePart n = null;

        Token name=null;
        ASTType t = null;


        try {
            // USE.g:1119:1: (name= IDENT COLON t= type )
            // USE.g:1120:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tuplePart5046); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_tuplePart5048); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_tuplePart5052);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTTuplePart(name, t); 
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
    // $ANTLR end "tuplePart"

    // $ANTLR start synpred1_USE
    public final void synpred1_USE_fragment() throws RecognitionException {   
        // USE.g:1033:7: ( COLON IDENT EQUAL )
        // USE.g:1033:8: COLON IDENT EQUAL
        {
        match(input,COLON,FOLLOW_COLON_in_synpred1_USE4631); if (state.failed) return ;
        match(input,IDENT,FOLLOW_IDENT_in_synpred1_USE4633); if (state.failed) return ;
        match(input,EQUAL,FOLLOW_EQUAL_in_synpred1_USE4635); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_USE

    // Delegated rules

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


 

    public static final BitSet FOLLOW_43_in_model69 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_model73 = new BitSet(new long[]{0x02D8F00000000000L});
    public static final BitSet FOLLOW_enumTypeDefinition_in_model85 = new BitSet(new long[]{0x02D8F00000000000L});
    public static final BitSet FOLLOW_generalClassDefinition_in_model102 = new BitSet(new long[]{0x02D8D00000000000L});
    public static final BitSet FOLLOW_associationDefinition_in_model119 = new BitSet(new long[]{0x02D8D00000000000L});
    public static final BitSet FOLLOW_44_in_model135 = new BitSet(new long[]{0x42D8D00000000000L});
    public static final BitSet FOLLOW_invariant_in_model153 = new BitSet(new long[]{0x42D8D00000000000L});
    public static final BitSet FOLLOW_prePost_in_model174 = new BitSet(new long[]{0x42D8D00000000000L});
    public static final BitSet FOLLOW_EOF_in_model215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_45_in_enumTypeDefinition242 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_enumTypeDefinition246 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LBRACE_in_enumTypeDefinition248 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_enumTypeDefinition252 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RBRACE_in_enumTypeDefinition254 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_enumTypeDefinition258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_generalClassDefinition297 = new BitSet(new long[]{0x0018C00000000000L});
    public static final BitSet FOLLOW_classDefinition_in_generalClassDefinition315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_associationClassDefinition_in_generalClassDefinition335 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_classDefinition374 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_classDefinition378 = new BitSet(new long[]{0x0007100000000100L});
    public static final BitSet FOLLOW_LESS_in_classDefinition388 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_classDefinition392 = new BitSet(new long[]{0x0007100000000000L});
    public static final BitSet FOLLOW_48_in_classDefinition405 = new BitSet(new long[]{0x0006100000000010L});
    public static final BitSet FOLLOW_attributeDefinition_in_classDefinition418 = new BitSet(new long[]{0x0006100000000010L});
    public static final BitSet FOLLOW_49_in_classDefinition439 = new BitSet(new long[]{0x0004100000000010L});
    public static final BitSet FOLLOW_operationDefinition_in_classDefinition452 = new BitSet(new long[]{0x0004100000000010L});
    public static final BitSet FOLLOW_44_in_classDefinition473 = new BitSet(new long[]{0x8004000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_invariantClause_in_classDefinition493 = new BitSet(new long[]{0x8004000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_50_in_classDefinition517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_associationClassDefinition550 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationClassDefinition576 = new BitSet(new long[]{0x0020000000000100L});
    public static final BitSet FOLLOW_LESS_in_associationClassDefinition586 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_associationClassDefinition590 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_associationClassDefinition601 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_associationEnd_in_associationClassDefinition609 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_associationEnd_in_associationClassDefinition621 = new BitSet(new long[]{0x00C7100000000010L});
    public static final BitSet FOLLOW_48_in_associationClassDefinition634 = new BitSet(new long[]{0x00C6100000000010L});
    public static final BitSet FOLLOW_attributeDefinition_in_associationClassDefinition647 = new BitSet(new long[]{0x00C6100000000010L});
    public static final BitSet FOLLOW_49_in_associationClassDefinition668 = new BitSet(new long[]{0x00C4100000000010L});
    public static final BitSet FOLLOW_operationDefinition_in_associationClassDefinition681 = new BitSet(new long[]{0x00C4100000000010L});
    public static final BitSet FOLLOW_44_in_associationClassDefinition702 = new BitSet(new long[]{0x80C4000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_invariantClause_in_associationClassDefinition722 = new BitSet(new long[]{0x80C4000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_set_in_associationClassDefinition756 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_associationClassDefinition785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_attributeDefinition814 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_attributeDefinition816 = new BitSet(new long[]{0x0000000000000010L,0x0000A1E000000000L});
    public static final BitSet FOLLOW_type_in_attributeDefinition820 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_attributeDefinition824 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationDefinition862 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_paramList_in_operationDefinition870 = new BitSet(new long[]{0x0100000000000682L,0x0000000000000006L});
    public static final BitSet FOLLOW_COLON_in_operationDefinition878 = new BitSet(new long[]{0x0000000000000010L,0x0000A1E000000000L});
    public static final BitSet FOLLOW_type_in_operationDefinition882 = new BitSet(new long[]{0x0100000000000482L,0x0000000000000006L});
    public static final BitSet FOLLOW_EQUAL_in_operationDefinition892 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_operationDefinition896 = new BitSet(new long[]{0x0100000000000082L,0x0000000000000006L});
    public static final BitSet FOLLOW_56_in_operationDefinition904 = new BitSet(new long[]{0x0004000000000000L,0x0000000000523578L});
    public static final BitSet FOLLOW_alActionList_in_operationDefinition908 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_operationDefinition910 = new BitSet(new long[]{0x0000000000000082L,0x0000000000000006L});
    public static final BitSet FOLLOW_prePostClause_in_operationDefinition929 = new BitSet(new long[]{0x0000000000000082L,0x0000000000000006L});
    public static final BitSet FOLLOW_SEMI_in_operationDefinition942 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_associationDefinition978 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationDefinition1003 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_53_in_associationDefinition1011 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_associationEnd_in_associationDefinition1019 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_associationEnd_in_associationDefinition1031 = new BitSet(new long[]{0x0004000000000010L});
    public static final BitSet FOLLOW_50_in_associationDefinition1042 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd1068 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LBRACK_in_associationEnd1070 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_multiplicity_in_associationEnd1074 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RBRACK_in_associationEnd1076 = new BitSet(new long[]{0x3C00000000000092L});
    public static final BitSet FOLLOW_58_in_associationEnd1087 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd1091 = new BitSet(new long[]{0x3800000000000092L});
    public static final BitSet FOLLOW_59_in_associationEnd1112 = new BitSet(new long[]{0x3800000000000092L});
    public static final BitSet FOLLOW_60_in_associationEnd1124 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd1128 = new BitSet(new long[]{0x3800000000000092L});
    public static final BitSet FOLLOW_keyUnion_in_associationEnd1140 = new BitSet(new long[]{0x3800000000000092L});
    public static final BitSet FOLLOW_61_in_associationEnd1152 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd1156 = new BitSet(new long[]{0x3800000000000092L});
    public static final BitSet FOLLOW_SEMI_in_associationEnd1173 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity1208 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_COMMA_in_multiplicity1218 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity1222 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange1251 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_DOTDOT_in_multiplicityRange1261 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange1265 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_multiplicitySpec1299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_multiplicitySpec1309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_invariant1350 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_invariant1360 = new BitSet(new long[]{0x0000000000002200L});
    public static final BitSet FOLLOW_COMMA_in_invariant1373 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_invariant1377 = new BitSet(new long[]{0x0000000000002200L});
    public static final BitSet FOLLOW_COLON_in_invariant1385 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_simpleType_in_invariant1397 = new BitSet(new long[]{0x8000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_invariantClause_in_invariant1409 = new BitSet(new long[]{0x8000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_63_in_invariantClause1440 = new BitSet(new long[]{0x0000000000000210L});
    public static final BitSet FOLLOW_IDENT_in_invariantClause1446 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_invariantClause1451 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_invariantClause1455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_64_in_invariantClause1465 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_63_in_invariantClause1467 = new BitSet(new long[]{0x0000000000000210L});
    public static final BitSet FOLLOW_IDENT_in_invariantClause1473 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_invariantClause1478 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_invariantClause1482 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_prePost1508 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_prePost1512 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_COLON_COLON_in_prePost1514 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_prePost1518 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_paramList_in_prePost1522 = new BitSet(new long[]{0x0000000000000200L,0x0000000000000006L});
    public static final BitSet FOLLOW_COLON_in_prePost1526 = new BitSet(new long[]{0x0000000000000010L,0x0000A1E000000000L});
    public static final BitSet FOLLOW_type_in_prePost1530 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000006L});
    public static final BitSet FOLLOW_prePostClause_in_prePost1549 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000006L});
    public static final BitSet FOLLOW_set_in_prePostClause1588 = new BitSet(new long[]{0x0000000000000210L});
    public static final BitSet FOLLOW_IDENT_in_prePostClause1603 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_prePostClause1608 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_prePostClause1612 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alAction_in_alActionList1645 = new BitSet(new long[]{0x0000000000000002L,0x0000000000523578L});
    public static final BitSet FOLLOW_alCreateVar_in_alAction1671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alDelete_in_alAction1683 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alSet_in_alAction1695 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alSetCreate_in_alAction1707 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alInsert_in_alAction1719 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alDestroy_in_alAction1731 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alIf_in_alAction1743 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alWhile_in_alAction1755 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alFor_in_alAction1767 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alExec_in_alAction1779 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_alCreateVar1798 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alCreateVar1806 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_alCreateVar1808 = new BitSet(new long[]{0x0000000000000010L,0x0000A1E000000000L});
    public static final BitSet FOLLOW_type_in_alCreateVar1812 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_alSet1834 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_alSet1838 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_alSet1840 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_alSet1844 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_70_in_alSetCreate1868 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_alSetCreate1872 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_alSetCreate1874 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alSetCreate1880 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alSetCreate1884 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_alSetCreate1893 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_alSetCreate1897 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_alInsert1928 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_alInsert1930 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_alInsert1939 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_COMMA_in_alInsert1943 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_alInsert1951 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_COMMA_in_alInsert1957 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_alInsert1961 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_RPAREN_in_alInsert1973 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_alInsert1975 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alInsert1979 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_alDelete2011 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_alDelete2013 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_alDelete2021 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_COMMA_in_alDelete2025 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_alDelete2033 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_COMMA_in_alDelete2039 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_alDelete2043 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_RPAREN_in_alDelete2054 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_alDelete2056 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alDelete2060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_76_in_alDestroy2089 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_alDestroy2093 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_alIf2117 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_alIf2121 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_alIf2125 = new BitSet(new long[]{0x0000000000000000L,0x000000000053B578L});
    public static final BitSet FOLLOW_alActionList_in_alIf2129 = new BitSet(new long[]{0x0000000000000000L,0x0000000000018000L});
    public static final BitSet FOLLOW_79_in_alIf2133 = new BitSet(new long[]{0x0000000000000000L,0x0000000000533578L});
    public static final BitSet FOLLOW_alActionList_in_alIf2137 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_alIf2142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_alWhile2161 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_alWhile2165 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_alWhile2169 = new BitSet(new long[]{0x0000000000000000L,0x00000000005A3578L});
    public static final BitSet FOLLOW_alActionList_in_alWhile2175 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_alWhile2178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_alFor2197 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alFor2201 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_alFor2203 = new BitSet(new long[]{0x0000000000000010L,0x0000A1E000000000L});
    public static final BitSet FOLLOW_type_in_alFor2207 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_alFor2209 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_alFor2213 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_alFor2217 = new BitSet(new long[]{0x0000000000000010L,0x0000000000523578L});
    public static final BitSet FOLLOW_alActionList_in_alFor2223 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alFor2230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_86_in_alExec2250 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_alExec2254 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_keyUnion2272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_expressionOnly2302 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_expressionOnly2304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_87_in_expression2352 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_expression2356 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COLON_in_expression2360 = new BitSet(new long[]{0x0000000000000010L,0x0000A1E000000000L});
    public static final BitSet FOLLOW_type_in_expression2364 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_expression2369 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_expression2373 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_expression2375 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_conditionalImpliesExpression_in_expression2400 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_paramList2433 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList2450 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_COMMA_in_paramList2462 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList2466 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_RPAREN_in_paramList2486 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_idList2515 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_COMMA_in_idList2525 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_idList2529 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_IDENT_in_variableDeclaration2560 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_variableDeclaration2562 = new BitSet(new long[]{0x0000000000000010L,0x0000A1E000000000L});
    public static final BitSet FOLLOW_type_in_variableDeclaration2566 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2602 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_88_in_conditionalImpliesExpression2615 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2619 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2664 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_conditionalOrExpression2677 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2681 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2725 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_90_in_conditionalXOrExpression2738 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2742 = new BitSet(new long[]{0x0000000000000002L,0x0000000004000000L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression2786 = new BitSet(new long[]{0x0000000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_conditionalAndExpression2799 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression2803 = new BitSet(new long[]{0x0000000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression2851 = new BitSet(new long[]{0x0000000000200402L});
    public static final BitSet FOLLOW_set_in_equalityExpression2870 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression2880 = new BitSet(new long[]{0x0000000000200402L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression2929 = new BitSet(new long[]{0x0000000001C00102L});
    public static final BitSet FOLLOW_set_in_relationalExpression2947 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression2965 = new BitSet(new long[]{0x0000000001C00102L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3015 = new BitSet(new long[]{0x0000000006000002L});
    public static final BitSet FOLLOW_set_in_additiveExpression3033 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression3043 = new BitSet(new long[]{0x0000000006000002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3093 = new BitSet(new long[]{0x0000000008010002L,0x0000000010000000L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression3111 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3125 = new BitSet(new long[]{0x0000000008010002L,0x0000000010000000L});
    public static final BitSet FOLLOW_set_in_unaryExpression3187 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression3211 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpression_in_unaryExpression3231 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_postfixExpression3264 = new BitSet(new long[]{0x0000000030000002L});
    public static final BitSet FOLLOW_ARROW_in_postfixExpression3282 = new BitSet(new long[]{0x0000000000000010L,0x0000000780000000L});
    public static final BitSet FOLLOW_DOT_in_postfixExpression3288 = new BitSet(new long[]{0x0000000000000010L,0x0000000780000000L});
    public static final BitSet FOLLOW_propertyCall_in_postfixExpression3299 = new BitSet(new long[]{0x0000000030000002L});
    public static final BitSet FOLLOW_literal_in_primaryExpression3339 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyCall_in_primaryExpression3351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression3362 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_primaryExpression3366 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression3368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifExpression_in_primaryExpression3380 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_primaryExpression3397 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_DOT_in_primaryExpression3399 = new BitSet(new long[]{0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_94_in_primaryExpression3401 = new BitSet(new long[]{0x0000000040080002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression3405 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression3407 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_AT_in_primaryExpression3428 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_primaryExpression3430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_queryExpression_in_propertyCall3503 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterateExpression_in_propertyCall3516 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationExpression_in_propertyCall3529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeExpression_in_propertyCall3542 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_queryExpression3577 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_queryExpression3584 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_queryExpression3595 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_BAR_in_queryExpression3599 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_queryExpression3610 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_queryExpression3616 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_95_in_iterateExpression3648 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_iterateExpression3654 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_iterateExpression3662 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_in_iterateExpression3664 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableInitialization_in_iterateExpression3672 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_BAR_in_iterateExpression3674 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_iterateExpression3682 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_iterateExpression3688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression3732 = new BitSet(new long[]{0x0000000040080802L});
    public static final BitSet FOLLOW_LBRACK_in_operationExpression3748 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression3752 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RBRACK_in_operationExpression3754 = new BitSet(new long[]{0x0000000040080002L});
    public static final BitSet FOLLOW_AT_in_operationExpression3767 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_65_in_operationExpression3769 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_LPAREN_in_operationExpression3790 = new BitSet(new long[]{0x0000000706188010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_operationExpression3811 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_COMMA_in_operationExpression3823 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_operationExpression3827 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_RPAREN_in_operationExpression3847 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_typeExpression3890 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_typeExpression3906 = new BitSet(new long[]{0x0000000000000010L,0x0000A1E000000000L});
    public static final BitSet FOLLOW_type_in_typeExpression3910 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_typeExpression3912 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_elemVarsDeclaration3951 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_COLON_in_elemVarsDeclaration3959 = new BitSet(new long[]{0x0000000000000010L,0x0000A1E000000000L});
    public static final BitSet FOLLOW_type_in_elemVarsDeclaration3963 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableInitialization3998 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_variableInitialization4000 = new BitSet(new long[]{0x0000000000000010L,0x0000A1E000000000L});
    public static final BitSet FOLLOW_type_in_variableInitialization4004 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_variableInitialization4006 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_variableInitialization4010 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_77_in_ifExpression4042 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_ifExpression4046 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_ifExpression4048 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_ifExpression4052 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_ifExpression4054 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_ifExpression4058 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_ifExpression4060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_literal4099 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_100_in_literal4113 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_literal4126 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REAL_in_literal4141 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal4155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_literal4165 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_literal4169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionLiteral_in_literal4181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyCollectionLiteral_in_literal4193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedLiteral_in_literal4205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleLiteral_in_literal4217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dateLiteral_in_literal4229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionLiteral4267 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LBRACE_in_collectionLiteral4296 = new BitSet(new long[]{0x0000000706088050L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral4313 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_COMMA_in_collectionLiteral4326 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral4330 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_RBRACE_in_collectionLiteral4349 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_collectionItem4378 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_DOTDOT_in_collectionItem4389 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_collectionItem4393 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_105_in_emptyCollectionLiteral4422 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_emptyCollectionLiteral4424 = new BitSet(new long[]{0x0000000000000000L,0x000081E000000000L});
    public static final BitSet FOLLOW_collectionType_in_emptyCollectionLiteral4428 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_emptyCollectionLiteral4430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_106_in_undefinedLiteral4460 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_undefinedLiteral4462 = new BitSet(new long[]{0x0000000000000010L,0x0000A1E000000000L});
    public static final BitSet FOLLOW_type_in_undefinedLiteral4466 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_undefinedLiteral4468 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_107_in_undefinedLiteral4482 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_108_in_undefinedLiteral4496 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_109_in_tupleLiteral4530 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LBRACE_in_tupleLiteral4536 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral4544 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_COMMA_in_tupleLiteral4555 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral4559 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_RBRACE_in_tupleLiteral4570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tupleItem4601 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COLON_in_tupleItem4640 = new BitSet(new long[]{0x0000000000000010L,0x0000A1E000000000L});
    public static final BitSet FOLLOW_type_in_tupleItem4644 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_tupleItem4646 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_tupleItem4650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_tupleItem4672 = new BitSet(new long[]{0x0000000706088010L,0x00007FFFA0802000L});
    public static final BitSet FOLLOW_expression_in_tupleItem4682 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_110_in_dateLiteral4727 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LBRACE_in_dateLiteral4729 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_STRING_in_dateLiteral4733 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RBRACE_in_dateLiteral4735 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleType_in_type4785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_type4797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleType_in_type4809 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeOnly4841 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_typeOnly4843 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_simpleType4871 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionType4909 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_collectionType4936 = new BitSet(new long[]{0x0000000000000010L,0x0000A1E000000000L});
    public static final BitSet FOLLOW_type_in_collectionType4940 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_collectionType4942 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_109_in_tupleType4976 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_tupleType4978 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType4987 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_COMMA_in_tupleType4998 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType5002 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_RPAREN_in_tupleType5014 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tuplePart5046 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_tuplePart5048 = new BitSet(new long[]{0x0000000000000010L,0x0000A1E000000000L});
    public static final BitSet FOLLOW_type_in_tuplePart5052 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_synpred1_USE4631 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_synpred1_USE4633 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_synpred1_USE4635 = new BitSet(new long[]{0x0000000000000002L});

}