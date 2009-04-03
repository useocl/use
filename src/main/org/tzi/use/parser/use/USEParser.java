// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 USE.g 2009-04-03 12:29:11
 
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

public class USEParser extends BaseParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "IDENT", "LBRACE", "RBRACE", "SEMI", "LESS", "COLON", "EQUAL", "LBRACK", "RBRACK", "COMMA", "DOTDOT", "INT", "STAR", "COLON_COLON", "COLON_EQUAL", "LPAREN", "RPAREN", "NOT_EQUAL", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "PLUS", "MINUS", "SLASH", "ARROW", "DOT", "AT", "BAR", "REAL", "STRING", "HASH", "NEWLINE", "WS", "SL_COMMENT", "ML_COMMENT", "RANGE_OR_INT", "ESC", "HEX_DIGIT", "VOCAB", "'model'", "'constraints'", "'enum'", "'abstract'", "'class'", "'attributes'", "'operations'", "'end'", "'associationClass'", "'associationclass'", "'between'", "'aggregation'", "'composition'", "'begin'", "'association'", "'role'", "'ordered'", "'context'", "'inv'", "'existential'", "'pre'", "'post'", "'var'", "'declare'", "'set'", "'create'", "'namehint'", "'insert'", "'into'", "'delete'", "'from'", "'destroy'", "'if'", "'then'", "'else'", "'endif'", "'while'", "'do'", "'wend'", "'for'", "'in'", "'execute'", "'let'", "'implies'", "'or'", "'xor'", "'and'", "'div'", "'not'", "'allInstances'", "'iterate'", "'oclAsType'", "'oclIsKindOf'", "'oclIsTypeOf'", "'true'", "'false'", "'Set'", "'Sequence'", "'Bag'", "'oclEmpty'", "'oclUndefined'", "'Undefined'", "'null'", "'Tuple'", "'Collection'"
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
    public static final int COMMA=13;
    public static final int T__59=59;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int EQUAL=10;
    public static final int T__105=105;
    public static final int T__106=106;
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
            match(input,43,FOLLOW_43_in_model69); 
            modelName=(Token)match(input,IDENT,FOLLOW_IDENT_in_model73); 
             n = new ASTModel(modelName); 
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

            	     n.addEnumTypeDef(e); 

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

            	     n.addAssociation(a); 

            	    }


            	    }
            	    break;
            	case 3 :
            	    // USE.g:122:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    {
            	    // USE.g:122:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    // USE.g:122:11: 'constraints' (cons= invariant | ppc= prePost )*
            	    {
            	    match(input,44,FOLLOW_44_in_model135); 
            	    // USE.g:123:11: (cons= invariant | ppc= prePost )*
            	    loop2:
            	    do {
            	        int alt2=3;
            	        int LA2_0 = input.LA(1);

            	        if ( (LA2_0==60) ) {
            	            int LA2_2 = input.LA(2);

            	            if ( (LA2_2==IDENT) ) {
            	                int LA2_3 = input.LA(3);

            	                if ( (LA2_3==COLON_COLON) ) {
            	                    alt2=2;
            	                }
            	                else if ( (LA2_3==EOF||LA2_3==COLON||LA2_3==COMMA||LA2_3==44||(LA2_3>=46 && LA2_3<=47)||(LA2_3>=51 && LA2_3<=52)||(LA2_3>=54 && LA2_3<=55)||LA2_3==57||(LA2_3>=60 && LA2_3<=62)) ) {
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

            	    	     n.addConstraint(cons); 

            	    	    }
            	    	    break;
            	    	case 2 :
            	    	    // USE.g:124:15: ppc= prePost
            	    	    {
            	    	    pushFollow(FOLLOW_prePost_in_model174);
            	    	    ppc=prePost();

            	    	    state._fsp--;

            	    	     n.addPrePost(ppc); 

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

            match(input,EOF,FOLLOW_EOF_in_model215); 

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
            match(input,45,FOLLOW_45_in_enumTypeDefinition242); 
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_enumTypeDefinition246); 
            match(input,LBRACE,FOLLOW_LBRACE_in_enumTypeDefinition248); 
            pushFollow(FOLLOW_idList_in_enumTypeDefinition252);
            idListRes=idList();

            state._fsp--;

            match(input,RBRACE,FOLLOW_RBRACE_in_enumTypeDefinition254); 
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
                    match(input,SEMI,FOLLOW_SEMI_in_enumTypeDefinition258); 

                    }
                    break;

            }

             n = new ASTEnumTypeDefinition(name, idListRes); 

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
                    match(input,46,FOLLOW_46_in_generalClassDefinition297); 
                     isAbstract = true; 

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

                     n.addClass(c); 

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

                     n.addAssociationClass(ac); 

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
            match(input,47,FOLLOW_47_in_classDefinition374); 
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_classDefinition378); 
             n = new ASTClass(name, isAbstract); 
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
                    match(input,LESS,FOLLOW_LESS_in_classDefinition388); 
                    pushFollow(FOLLOW_idList_in_classDefinition392);
                    idListRes=idList();

                    state._fsp--;

                     n.addSuperClasses(idListRes); 

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
                    match(input,48,FOLLOW_48_in_classDefinition405); 
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

                    	     n.addAttribute(a); 

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
                    match(input,49,FOLLOW_49_in_classDefinition439); 
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

                    	     n.addOperation(op); 

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
                    match(input,44,FOLLOW_44_in_classDefinition473); 
                    // USE.g:182:7: (inv= invariantClause )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( ((LA12_0>=61 && LA12_0<=62)) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // USE.g:183:9: inv= invariantClause
                    	    {
                    	    pushFollow(FOLLOW_invariantClause_in_classDefinition493);
                    	    inv=invariantClause();

                    	    state._fsp--;

                    	     n.addInvariantClause(inv); 

                    	    }
                    	    break;

                    	default :
                    	    break loop12;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,50,FOLLOW_50_in_classDefinition517); 

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
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

             
                	if ((classKW!=null?classKW.getText():null).equals("associationClass")) {
                           reportWarning("the 'associationClass' keyword is deprecated and will " +
                                         "not be supported in the future, use 'associationclass' instead");
                        }  
                
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationClassDefinition576); 
             n = new ASTAssociationClass(name, isAbstract); 
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
                    match(input,LESS,FOLLOW_LESS_in_associationClassDefinition586); 
                    pushFollow(FOLLOW_idList_in_associationClassDefinition590);
                    idListRes=idList();

                    state._fsp--;

                     n.addSuperClasses(idListRes); 

                    }
                    break;

            }

            match(input,53,FOLLOW_53_in_associationClassDefinition601); 
            pushFollow(FOLLOW_associationEnd_in_associationClassDefinition609);
            ae=associationEnd();

            state._fsp--;

             n.addEnd(ae); 
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

            	     n.addEnd(ae); 

            	    }
            	    break;

            	default :
            	    if ( cnt15 >= 1 ) break loop15;
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
                    match(input,48,FOLLOW_48_in_associationClassDefinition634); 
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

                    	     n.addAttribute(a); 

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
                    match(input,49,FOLLOW_49_in_associationClassDefinition668); 
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

                    	     n.addOperation(op); 

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
                    match(input,44,FOLLOW_44_in_associationClassDefinition702); 
                    // USE.g:227:7: (inv= invariantClause )*
                    loop20:
                    do {
                        int alt20=2;
                        int LA20_0 = input.LA(1);

                        if ( ((LA20_0>=61 && LA20_0<=62)) ) {
                            alt20=1;
                        }


                        switch (alt20) {
                    	case 1 :
                    	    // USE.g:228:9: inv= invariantClause
                    	    {
                    	    pushFollow(FOLLOW_invariantClause_in_associationClassDefinition722);
                    	    inv=invariantClause();

                    	    state._fsp--;

                    	     n.addInvariantClause(inv); 

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
                     t = input.LT(1); 
                    if ( (input.LA(1)>=54 && input.LA(1)<=55) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                     n.setKind(t); 

                    }
                    break;

            }

            match(input,50,FOLLOW_50_in_associationClassDefinition785); 

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
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_attributeDefinition814); 
            match(input,COLON,FOLLOW_COLON_in_attributeDefinition816); 
            pushFollow(FOLLOW_type_in_attributeDefinition820);
            t=type();

            state._fsp--;

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
                    match(input,SEMI,FOLLOW_SEMI_in_attributeDefinition824); 

                    }
                    break;

            }

             n = new ASTAttribute(name, t); 

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
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationDefinition862); 
            pushFollow(FOLLOW_paramList_in_operationDefinition870);
            pl=paramList();

            state._fsp--;

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
                    match(input,COLON,FOLLOW_COLON_in_operationDefinition878); 
                    pushFollow(FOLLOW_type_in_operationDefinition882);
                    t=type();

                    state._fsp--;


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
                    match(input,EQUAL,FOLLOW_EQUAL_in_operationDefinition892); 
                    pushFollow(FOLLOW_expression_in_operationDefinition896);
                    e=expression();

                    state._fsp--;


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
                    match(input,56,FOLLOW_56_in_operationDefinition904); 
                    pushFollow(FOLLOW_alActionList_in_operationDefinition908);
                    al=alActionList();

                    state._fsp--;

                    match(input,50,FOLLOW_50_in_operationDefinition910); 

                    }
                    break;

            }

             n = new ASTOperation(name, pl, t, e, al); 
            // USE.g:261:5: (ppc= prePostClause )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( ((LA27_0>=63 && LA27_0<=64)) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // USE.g:261:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_operationDefinition929);
            	    ppc=prePostClause();

            	    state._fsp--;

            	     n.addPrePostClause(ppc); 

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
                    match(input,SEMI,FOLLOW_SEMI_in_operationDefinition942); 

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
             t = input.LT(1); 
            if ( (input.LA(1)>=54 && input.LA(1)<=55)||input.LA(1)==57 ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationDefinition1003); 
             n = new ASTAssociation(t, name); 
            match(input,53,FOLLOW_53_in_associationDefinition1011); 
            pushFollow(FOLLOW_associationEnd_in_associationDefinition1019);
            ae=associationEnd();

            state._fsp--;

             n.addEnd(ae); 
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

            	     n.addEnd(ae); 

            	    }
            	    break;

            	default :
            	    if ( cnt29 >= 1 ) break loop29;
                        EarlyExitException eee =
                            new EarlyExitException(29, input);
                        throw eee;
                }
                cnt29++;
            } while (true);

            match(input,50,FOLLOW_50_in_associationDefinition1042); 

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
    // USE.g:290:1: associationEnd returns [ASTAssociationEnd n] : name= IDENT LBRACK m= multiplicity RBRACK ( 'role' rn= IDENT )? ( 'ordered' )? ( SEMI )? ;
    public final ASTAssociationEnd associationEnd() throws RecognitionException {
        ASTAssociationEnd n = null;

        Token name=null;
        Token rn=null;
        ASTMultiplicity m = null;


        try {
            // USE.g:291:1: (name= IDENT LBRACK m= multiplicity RBRACK ( 'role' rn= IDENT )? ( 'ordered' )? ( SEMI )? )
            // USE.g:292:5: name= IDENT LBRACK m= multiplicity RBRACK ( 'role' rn= IDENT )? ( 'ordered' )? ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd1068); 
            match(input,LBRACK,FOLLOW_LBRACK_in_associationEnd1070); 
            pushFollow(FOLLOW_multiplicity_in_associationEnd1074);
            m=multiplicity();

            state._fsp--;

            match(input,RBRACK,FOLLOW_RBRACK_in_associationEnd1076); 
             n = new ASTAssociationEnd(name, m); 
            // USE.g:294:5: ( 'role' rn= IDENT )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==58) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // USE.g:294:7: 'role' rn= IDENT
                    {
                    match(input,58,FOLLOW_58_in_associationEnd1092); 
                    rn=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd1096); 
                     n.setRolename(rn); 

                    }
                    break;

            }

            // USE.g:295:5: ( 'ordered' )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==59) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // USE.g:295:7: 'ordered'
                    {
                    match(input,59,FOLLOW_59_in_associationEnd1109); 
                     n.setOrdered(); 

                    }
                    break;

            }

            // USE.g:296:5: ( SEMI )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==SEMI) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // USE.g:296:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_associationEnd1122); 

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
    // USE.g:310:1: multiplicity returns [ASTMultiplicity n] : mr= multiplicityRange ( COMMA mr= multiplicityRange )* ;
    public final ASTMultiplicity multiplicity() throws RecognitionException {
        ASTMultiplicity n = null;

        ASTMultiplicityRange mr = null;


        try {
            // USE.g:311:1: (mr= multiplicityRange ( COMMA mr= multiplicityRange )* )
            // USE.g:312:5: mr= multiplicityRange ( COMMA mr= multiplicityRange )*
            {
             
            	Token t = input.LT(1); // remember start position of expression
            	n = new ASTMultiplicity(t);
                
            pushFollow(FOLLOW_multiplicityRange_in_multiplicity1157);
            mr=multiplicityRange();

            state._fsp--;

             n.addRange(mr); 
            // USE.g:317:5: ( COMMA mr= multiplicityRange )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==COMMA) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // USE.g:317:7: COMMA mr= multiplicityRange
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_multiplicity1167); 
            	    pushFollow(FOLLOW_multiplicityRange_in_multiplicity1171);
            	    mr=multiplicityRange();

            	    state._fsp--;

            	     n.addRange(mr); 

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
    // USE.g:320:1: multiplicityRange returns [ASTMultiplicityRange n] : ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? ;
    public final ASTMultiplicityRange multiplicityRange() throws RecognitionException {
        ASTMultiplicityRange n = null;

        int ms1 = 0;

        int ms2 = 0;


        try {
            // USE.g:321:1: (ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? )
            // USE.g:322:5: ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )?
            {
            pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange1200);
            ms1=multiplicitySpec();

            state._fsp--;

             n = new ASTMultiplicityRange(ms1); 
            // USE.g:323:5: ( DOTDOT ms2= multiplicitySpec )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==DOTDOT) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // USE.g:323:7: DOTDOT ms2= multiplicitySpec
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_multiplicityRange1210); 
                    pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange1214);
                    ms2=multiplicitySpec();

                    state._fsp--;

                     n.setHigh(ms2); 

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
    // USE.g:326:1: multiplicitySpec returns [int m] : (i= INT | STAR );
    public final int multiplicitySpec() throws RecognitionException {
        int m = 0;

        Token i=null;

         m = -1; 
        try {
            // USE.g:328:1: (i= INT | STAR )
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==INT) ) {
                alt35=1;
            }
            else if ( (LA35_0==STAR) ) {
                alt35=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;
            }
            switch (alt35) {
                case 1 :
                    // USE.g:329:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_multiplicitySpec1248); 
                     m = Integer.parseInt((i!=null?i.getText():null)); 

                    }
                    break;
                case 2 :
                    // USE.g:330:7: STAR
                    {
                    match(input,STAR,FOLLOW_STAR_in_multiplicitySpec1258); 
                     m = -1; 

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
    // USE.g:351:1: invariant returns [ASTConstraintDefinition n] : 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )* ;
    public final ASTConstraintDefinition invariant() throws RecognitionException {
        ASTConstraintDefinition n = null;

        Token v=null;
        ASTSimpleType t = null;

        ASTInvariantClause inv = null;


        try {
            // USE.g:352:1: ( 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )* )
            // USE.g:353:5: 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )*
            {
             n = new ASTConstraintDefinition(); 
            match(input,60,FOLLOW_60_in_invariant1299); 
            // USE.g:355:5: (v= IDENT ( ',' v= IDENT )* COLON )?
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
                    // USE.g:355:7: v= IDENT ( ',' v= IDENT )* COLON
                    {
                    v=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariant1309); 
                     n.addVarName(v); 
                    // USE.g:356:8: ( ',' v= IDENT )*
                    loop36:
                    do {
                        int alt36=2;
                        int LA36_0 = input.LA(1);

                        if ( (LA36_0==COMMA) ) {
                            alt36=1;
                        }


                        switch (alt36) {
                    	case 1 :
                    	    // USE.g:356:9: ',' v= IDENT
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_invariant1322); 
                    	    v=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariant1326); 
                    	     n.addVarName(v); 

                    	    }
                    	    break;

                    	default :
                    	    break loop36;
                        }
                    } while (true);

                    match(input,COLON,FOLLOW_COLON_in_invariant1334); 

                    }
                    break;

            }

            pushFollow(FOLLOW_simpleType_in_invariant1346);
            t=simpleType();

            state._fsp--;

             n.setType(t); 
            // USE.g:358:5: (inv= invariantClause )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( ((LA38_0>=61 && LA38_0<=62)) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // USE.g:358:7: inv= invariantClause
            	    {
            	    pushFollow(FOLLOW_invariantClause_in_invariant1358);
            	    inv=invariantClause();

            	    state._fsp--;

            	     n.addInvariantClause(inv); 

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
    // USE.g:365:1: invariantClause returns [ASTInvariantClause n] : ( 'inv' (name= IDENT )? COLON e= expression | 'existential' 'inv' (name= IDENT )? COLON e= expression );
    public final ASTInvariantClause invariantClause() throws RecognitionException {
        ASTInvariantClause n = null;

        Token name=null;
        ASTExpression e = null;


        try {
            // USE.g:366:1: ( 'inv' (name= IDENT )? COLON e= expression | 'existential' 'inv' (name= IDENT )? COLON e= expression )
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==61) ) {
                alt41=1;
            }
            else if ( (LA41_0==62) ) {
                alt41=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 41, 0, input);

                throw nvae;
            }
            switch (alt41) {
                case 1 :
                    // USE.g:367:7: 'inv' (name= IDENT )? COLON e= expression
                    {
                    match(input,61,FOLLOW_61_in_invariantClause1389); 
                    // USE.g:367:13: (name= IDENT )?
                    int alt39=2;
                    int LA39_0 = input.LA(1);

                    if ( (LA39_0==IDENT) ) {
                        alt39=1;
                    }
                    switch (alt39) {
                        case 1 :
                            // USE.g:367:15: name= IDENT
                            {
                            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantClause1395); 

                            }
                            break;

                    }

                    match(input,COLON,FOLLOW_COLON_in_invariantClause1400); 
                    pushFollow(FOLLOW_expression_in_invariantClause1404);
                    e=expression();

                    state._fsp--;

                     n = new ASTInvariantClause(name, e); 

                    }
                    break;
                case 2 :
                    // USE.g:368:7: 'existential' 'inv' (name= IDENT )? COLON e= expression
                    {
                    match(input,62,FOLLOW_62_in_invariantClause1414); 
                    match(input,61,FOLLOW_61_in_invariantClause1416); 
                    // USE.g:368:27: (name= IDENT )?
                    int alt40=2;
                    int LA40_0 = input.LA(1);

                    if ( (LA40_0==IDENT) ) {
                        alt40=1;
                    }
                    switch (alt40) {
                        case 1 :
                            // USE.g:368:29: name= IDENT
                            {
                            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantClause1422); 

                            }
                            break;

                    }

                    match(input,COLON,FOLLOW_COLON_in_invariantClause1427); 
                    pushFollow(FOLLOW_expression_in_invariantClause1431);
                    e=expression();

                    state._fsp--;

                     n = new ASTExistentialInvariantClause(name, e); 

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
    // USE.g:379:1: prePost returns [ASTPrePost n] : 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ ;
    public final ASTPrePost prePost() throws RecognitionException {
        ASTPrePost n = null;

        Token classname=null;
        Token opname=null;
        List pl = null;

        ASTType rt = null;

        ASTPrePostClause ppc = null;


        try {
            // USE.g:380:1: ( 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ )
            // USE.g:381:5: 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+
            {
            match(input,60,FOLLOW_60_in_prePost1457); 
            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost1461); 
            match(input,COLON_COLON,FOLLOW_COLON_COLON_in_prePost1463); 
            opname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost1467); 
            pushFollow(FOLLOW_paramList_in_prePost1471);
            pl=paramList();

            state._fsp--;

            // USE.g:381:69: ( COLON rt= type )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==COLON) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // USE.g:381:71: COLON rt= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_prePost1475); 
                    pushFollow(FOLLOW_type_in_prePost1479);
                    rt=type();

                    state._fsp--;


                    }
                    break;

            }

             n = new ASTPrePost(classname, opname, pl, rt); 
            // USE.g:383:5: (ppc= prePostClause )+
            int cnt43=0;
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( ((LA43_0>=63 && LA43_0<=64)) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // USE.g:383:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_prePost1498);
            	    ppc=prePostClause();

            	    state._fsp--;

            	     n.addPrePostClause(ppc); 

            	    }
            	    break;

            	default :
            	    if ( cnt43 >= 1 ) break loop43;
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
    // USE.g:390:1: prePostClause returns [ASTPrePostClause n] : ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression ;
    public final ASTPrePostClause prePostClause() throws RecognitionException {
        ASTPrePostClause n = null;

        Token name=null;
        ASTExpression e = null;


         Token t = null; 
        try {
            // USE.g:392:1: ( ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression )
            // USE.g:393:5: ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression
            {
             t = input.LT(1); 
            if ( (input.LA(1)>=63 && input.LA(1)<=64) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // USE.g:394:25: (name= IDENT )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==IDENT) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // USE.g:394:27: name= IDENT
                    {
                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePostClause1552); 

                    }
                    break;

            }

            match(input,COLON,FOLLOW_COLON_in_prePostClause1557); 
            pushFollow(FOLLOW_expression_in_prePostClause1561);
            e=expression();

            state._fsp--;

             n = new ASTPrePostClause(t, name, e); 

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
    // USE.g:398:1: alActionList returns [ASTALActionList al] : (action= alAction )* ;
    public final ASTALActionList alActionList() throws RecognitionException {
        ASTALActionList al = null;

        ASTALAction action = null;



        	al = new ASTALActionList();

        try {
            // USE.g:402:1: ( (action= alAction )* )
            // USE.g:403:2: (action= alAction )*
            {
            // USE.g:403:2: (action= alAction )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( ((LA45_0>=65 && LA45_0<=68)||LA45_0==70||LA45_0==72||(LA45_0>=74 && LA45_0<=75)||LA45_0==79||LA45_0==82||LA45_0==84) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // USE.g:403:4: action= alAction
            	    {
            	    pushFollow(FOLLOW_alAction_in_alActionList1594);
            	    action=alAction();

            	    state._fsp--;

            	    al.add(action);

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
    // USE.g:406:1: alAction returns [ASTALAction action] : (aCV= alCreateVar | aDl= alDelete | aSe= alSet | aSC= alSetCreate | aIn= alInsert | aDe= alDestroy | aIf= alIf | aWh= alWhile | aFo= alFor | aEx= alExec );
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
            // USE.g:407:1: (aCV= alCreateVar | aDl= alDelete | aSe= alSet | aSC= alSetCreate | aIn= alInsert | aDe= alDestroy | aIf= alIf | aWh= alWhile | aFo= alFor | aEx= alExec )
            int alt46=10;
            switch ( input.LA(1) ) {
            case 65:
            case 66:
                {
                alt46=1;
                }
                break;
            case 72:
                {
                alt46=2;
                }
                break;
            case 67:
                {
                alt46=3;
                }
                break;
            case 68:
                {
                alt46=4;
                }
                break;
            case 70:
                {
                alt46=5;
                }
                break;
            case 74:
                {
                alt46=6;
                }
                break;
            case 75:
                {
                alt46=7;
                }
                break;
            case 79:
                {
                alt46=8;
                }
                break;
            case 82:
                {
                alt46=9;
                }
                break;
            case 84:
                {
                alt46=10;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 46, 0, input);

                throw nvae;
            }

            switch (alt46) {
                case 1 :
                    // USE.g:408:5: aCV= alCreateVar
                    {
                    pushFollow(FOLLOW_alCreateVar_in_alAction1620);
                    aCV=alCreateVar();

                    state._fsp--;

                     action = aCV; 

                    }
                    break;
                case 2 :
                    // USE.g:409:5: aDl= alDelete
                    {
                    pushFollow(FOLLOW_alDelete_in_alAction1632);
                    aDl=alDelete();

                    state._fsp--;

                     action = aDl; 

                    }
                    break;
                case 3 :
                    // USE.g:410:5: aSe= alSet
                    {
                    pushFollow(FOLLOW_alSet_in_alAction1644);
                    aSe=alSet();

                    state._fsp--;

                     action = aSe; 

                    }
                    break;
                case 4 :
                    // USE.g:411:5: aSC= alSetCreate
                    {
                    pushFollow(FOLLOW_alSetCreate_in_alAction1656);
                    aSC=alSetCreate();

                    state._fsp--;

                     action = aSC; 

                    }
                    break;
                case 5 :
                    // USE.g:412:5: aIn= alInsert
                    {
                    pushFollow(FOLLOW_alInsert_in_alAction1668);
                    aIn=alInsert();

                    state._fsp--;

                     action = aIn; 

                    }
                    break;
                case 6 :
                    // USE.g:413:5: aDe= alDestroy
                    {
                    pushFollow(FOLLOW_alDestroy_in_alAction1680);
                    aDe=alDestroy();

                    state._fsp--;

                     action = aDe; 

                    }
                    break;
                case 7 :
                    // USE.g:414:5: aIf= alIf
                    {
                    pushFollow(FOLLOW_alIf_in_alAction1692);
                    aIf=alIf();

                    state._fsp--;

                     action = aIf; 

                    }
                    break;
                case 8 :
                    // USE.g:415:5: aWh= alWhile
                    {
                    pushFollow(FOLLOW_alWhile_in_alAction1704);
                    aWh=alWhile();

                    state._fsp--;

                     action = aWh; 

                    }
                    break;
                case 9 :
                    // USE.g:416:5: aFo= alFor
                    {
                    pushFollow(FOLLOW_alFor_in_alAction1716);
                    aFo=alFor();

                    state._fsp--;

                     action = aFo; 

                    }
                    break;
                case 10 :
                    // USE.g:417:5: aEx= alExec
                    {
                    pushFollow(FOLLOW_alExec_in_alAction1728);
                    aEx=alExec();

                    state._fsp--;

                     action = aEx; 

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
    // USE.g:422:1: alCreateVar returns [ASTALCreateVar var] : ( 'var' | 'declare' ) name= IDENT COLON t= type ;
    public final ASTALCreateVar alCreateVar() throws RecognitionException {
        ASTALCreateVar var = null;

        Token name=null;
        ASTType t = null;


        try {
            // USE.g:423:1: ( ( 'var' | 'declare' ) name= IDENT COLON t= type )
            // USE.g:424:2: ( 'var' | 'declare' ) name= IDENT COLON t= type
            {
            if ( (input.LA(1)>=65 && input.LA(1)<=66) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_alCreateVar1755); 
            match(input,COLON,FOLLOW_COLON_in_alCreateVar1757); 
            pushFollow(FOLLOW_type_in_alCreateVar1761);
            t=type();

            state._fsp--;

             var = new ASTALCreateVar(name, t); 

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
    // USE.g:428:1: alSet returns [ASTALSet set] : 'set' lval= expression COLON_EQUAL rval= expression ;
    public final ASTALSet alSet() throws RecognitionException {
        ASTALSet set = null;

        ASTExpression lval = null;

        ASTExpression rval = null;


        try {
            // USE.g:429:1: ( 'set' lval= expression COLON_EQUAL rval= expression )
            // USE.g:431:5: 'set' lval= expression COLON_EQUAL rval= expression
            {
            match(input,67,FOLLOW_67_in_alSet1783); 
            pushFollow(FOLLOW_expression_in_alSet1787);
            lval=expression();

            state._fsp--;

            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_alSet1789); 
            pushFollow(FOLLOW_expression_in_alSet1793);
            rval=expression();

            state._fsp--;

             set = new ASTALSet(lval, rval); 

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
    // USE.g:435:1: alSetCreate returns [ASTALSetCreate setcreate] : 'create' lval= expression COLON_EQUAL {...}?new_= IDENT cls= IDENT ( 'namehint' nameExpr= expression )? ;
    public final ASTALSetCreate alSetCreate() throws RecognitionException {
        ASTALSetCreate setcreate = null;

        Token new_=null;
        Token cls=null;
        ASTExpression lval = null;

        ASTExpression nameExpr = null;


        try {
            // USE.g:436:1: ( 'create' lval= expression COLON_EQUAL {...}?new_= IDENT cls= IDENT ( 'namehint' nameExpr= expression )? )
            // USE.g:437:5: 'create' lval= expression COLON_EQUAL {...}?new_= IDENT cls= IDENT ( 'namehint' nameExpr= expression )?
            {
            match(input,68,FOLLOW_68_in_alSetCreate1817); 
            pushFollow(FOLLOW_expression_in_alSetCreate1821);
            lval=expression();

            state._fsp--;

            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_alSetCreate1823); 
            if ( !(( input.LT(1).getText().equals("new") )) ) {
                throw new FailedPredicateException(input, "alSetCreate", " input.LT(1).getText().equals(\"new\") ");
            }
            new_=(Token)match(input,IDENT,FOLLOW_IDENT_in_alSetCreate1829); 
            cls=(Token)match(input,IDENT,FOLLOW_IDENT_in_alSetCreate1833); 
            // USE.g:438:5: ( 'namehint' nameExpr= expression )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==69) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // USE.g:438:7: 'namehint' nameExpr= expression
                    {
                    match(input,69,FOLLOW_69_in_alSetCreate1842); 
                    pushFollow(FOLLOW_expression_in_alSetCreate1846);
                    nameExpr=expression();

                    state._fsp--;


                    }
                    break;

            }

             setcreate = new ASTALSetCreate(lval, cls, nameExpr);

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
    // USE.g:443:1: alInsert returns [ASTALInsert insert] : 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT ;
    public final ASTALInsert alInsert() throws RecognitionException {
        ASTALInsert insert = null;

        Token id=null;
        ASTExpression e = null;


        List exprList = new ArrayList(); 
        try {
            // USE.g:445:1: ( 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT )
            // USE.g:446:5: 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT
            {
            match(input,70,FOLLOW_70_in_alInsert1877); 
            match(input,LPAREN,FOLLOW_LPAREN_in_alInsert1879); 
            pushFollow(FOLLOW_expression_in_alInsert1888);
            e=expression();

            state._fsp--;

             exprList.add(e); 
            match(input,COMMA,FOLLOW_COMMA_in_alInsert1892); 
            pushFollow(FOLLOW_expression_in_alInsert1900);
            e=expression();

            state._fsp--;

             exprList.add(e); 
            // USE.g:448:42: ( COMMA e= expression )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==COMMA) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // USE.g:448:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_alInsert1906); 
            	    pushFollow(FOLLOW_expression_in_alInsert1910);
            	    e=expression();

            	    state._fsp--;

            	     exprList.add(e); 

            	    }
            	    break;

            	default :
            	    break loop48;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_alInsert1922); 
            match(input,71,FOLLOW_71_in_alInsert1924); 
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_alInsert1928); 
             insert = new ASTALInsert(exprList, id); 

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
    // USE.g:454:1: alDelete returns [ASTALDelete n] : 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT ;
    public final ASTALDelete alDelete() throws RecognitionException {
        ASTALDelete n = null;

        Token id=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // USE.g:456:1: ( 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT )
            // USE.g:457:5: 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT
            {
            match(input,72,FOLLOW_72_in_alDelete1960); 
            match(input,LPAREN,FOLLOW_LPAREN_in_alDelete1962); 
            pushFollow(FOLLOW_expression_in_alDelete1970);
            e=expression();

            state._fsp--;

             exprList.add(e); 
            match(input,COMMA,FOLLOW_COMMA_in_alDelete1974); 
            pushFollow(FOLLOW_expression_in_alDelete1982);
            e=expression();

            state._fsp--;

             exprList.add(e); 
            // USE.g:459:42: ( COMMA e= expression )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==COMMA) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // USE.g:459:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_alDelete1988); 
            	    pushFollow(FOLLOW_expression_in_alDelete1992);
            	    e=expression();

            	    state._fsp--;

            	     exprList.add(e); 

            	    }
            	    break;

            	default :
            	    break loop49;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_alDelete2003); 
            match(input,73,FOLLOW_73_in_alDelete2005); 
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_alDelete2009); 
             n = new ASTALDelete(exprList, id); 

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
    // USE.g:465:1: alDestroy returns [ASTALDestroy n] : 'destroy' e= expression ;
    public final ASTALDestroy alDestroy() throws RecognitionException {
        ASTALDestroy n = null;

        ASTExpression e = null;


        try {
            // USE.g:466:1: ( 'destroy' e= expression )
            // USE.g:467:6: 'destroy' e= expression
            {
            match(input,74,FOLLOW_74_in_alDestroy2038); 
            pushFollow(FOLLOW_expression_in_alDestroy2042);
            e=expression();

            state._fsp--;

             n = new ASTALDestroy(e); 

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
    // USE.g:471:1: alIf returns [ASTALIf i] : 'if' ifexpr= expression 'then' thenlist= alActionList ( 'else' elselist= alActionList )? 'endif' ;
    public final ASTALIf alIf() throws RecognitionException {
        ASTALIf i = null;

        ASTExpression ifexpr = null;

        ASTALActionList thenlist = null;

        ASTALActionList elselist = null;


        try {
            // USE.g:472:1: ( 'if' ifexpr= expression 'then' thenlist= alActionList ( 'else' elselist= alActionList )? 'endif' )
            // USE.g:473:2: 'if' ifexpr= expression 'then' thenlist= alActionList ( 'else' elselist= alActionList )? 'endif'
            {
            match(input,75,FOLLOW_75_in_alIf2066); 
            pushFollow(FOLLOW_expression_in_alIf2070);
            ifexpr=expression();

            state._fsp--;

            match(input,76,FOLLOW_76_in_alIf2074); 
            pushFollow(FOLLOW_alActionList_in_alIf2078);
            thenlist=alActionList();

            state._fsp--;

            // USE.g:475:2: ( 'else' elselist= alActionList )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==77) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // USE.g:475:3: 'else' elselist= alActionList
                    {
                    match(input,77,FOLLOW_77_in_alIf2082); 
                    pushFollow(FOLLOW_alActionList_in_alIf2086);
                    elselist=alActionList();

                    state._fsp--;


                    }
                    break;

            }

            match(input,78,FOLLOW_78_in_alIf2091); 
             i = new ASTALIf(ifexpr, thenlist, elselist); 

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
    // USE.g:480:1: alWhile returns [ASTALWhile w] : 'while' expr= expression 'do' body= alActionList 'wend' ;
    public final ASTALWhile alWhile() throws RecognitionException {
        ASTALWhile w = null;

        ASTExpression expr = null;

        ASTALActionList body = null;


        try {
            // USE.g:481:1: ( 'while' expr= expression 'do' body= alActionList 'wend' )
            // USE.g:482:2: 'while' expr= expression 'do' body= alActionList 'wend'
            {
            match(input,79,FOLLOW_79_in_alWhile2110); 
            pushFollow(FOLLOW_expression_in_alWhile2114);
            expr=expression();

            state._fsp--;

            match(input,80,FOLLOW_80_in_alWhile2118); 
            pushFollow(FOLLOW_alActionList_in_alWhile2124);
            body=alActionList();

            state._fsp--;

            match(input,81,FOLLOW_81_in_alWhile2127); 
             w = new ASTALWhile(expr, body); 

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
    // USE.g:490:1: alFor returns [ASTALFor f] : 'for' var= IDENT COLON t= type 'in' expr= expression 'do' body= alActionList {...}?next= IDENT ;
    public final ASTALFor alFor() throws RecognitionException {
        ASTALFor f = null;

        Token var=null;
        Token next=null;
        ASTType t = null;

        ASTExpression expr = null;

        ASTALActionList body = null;


        try {
            // USE.g:491:1: ( 'for' var= IDENT COLON t= type 'in' expr= expression 'do' body= alActionList {...}?next= IDENT )
            // USE.g:492:2: 'for' var= IDENT COLON t= type 'in' expr= expression 'do' body= alActionList {...}?next= IDENT
            {
            match(input,82,FOLLOW_82_in_alFor2146); 
            var=(Token)match(input,IDENT,FOLLOW_IDENT_in_alFor2150); 
            match(input,COLON,FOLLOW_COLON_in_alFor2152); 
            pushFollow(FOLLOW_type_in_alFor2156);
            t=type();

            state._fsp--;

            match(input,83,FOLLOW_83_in_alFor2158); 
            pushFollow(FOLLOW_expression_in_alFor2162);
            expr=expression();

            state._fsp--;

            match(input,80,FOLLOW_80_in_alFor2166); 
            pushFollow(FOLLOW_alActionList_in_alFor2172);
            body=alActionList();

            state._fsp--;

            if ( !(( input.LT(1).getText().equals("next") )) ) {
                throw new FailedPredicateException(input, "alFor", " input.LT(1).getText().equals(\"next\") ");
            }
            next=(Token)match(input,IDENT,FOLLOW_IDENT_in_alFor2179); 
             f = new ASTALFor(var, t, expr, body); 

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
    // USE.g:499:1: alExec returns [ASTALExecute c] : 'execute' op= expression ;
    public final ASTALExecute alExec() throws RecognitionException {
        ASTALExecute c = null;

        ASTExpression op = null;


        try {
            // USE.g:500:1: ( 'execute' op= expression )
            // USE.g:501:5: 'execute' op= expression
            {
            match(input,84,FOLLOW_84_in_alExec2199); 
            pushFollow(FOLLOW_expression_in_alExec2203);
            op=expression();

            state._fsp--;

             c = new ASTALExecute(op); 

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


    // $ANTLR start "expressionOnly"
    // USE.g:533:1: expressionOnly returns [ASTExpression n] : nExp= expression EOF ;
    public final ASTExpression expressionOnly() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExp = null;


        try {
            // USE.g:534:1: (nExp= expression EOF )
            // USE.g:535:5: nExp= expression EOF
            {
            pushFollow(FOLLOW_expression_in_expressionOnly2238);
            nExp=expression();

            state._fsp--;

            match(input,EOF,FOLLOW_EOF_in_expressionOnly2240); 
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
    // USE.g:542:1: expression returns [ASTExpression n] : ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression ;
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
            // USE.g:548:1: ( ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression )
            // USE.g:549:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression
            {
             tok = input.LT(1); /* remember start of expression */ 
            // USE.g:550:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==85) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // USE.g:551:7: 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in'
            	    {
            	    match(input,85,FOLLOW_85_in_expression2288); 
            	    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_expression2292); 
            	    // USE.g:551:24: ( COLON t= type )?
            	    int alt51=2;
            	    int LA51_0 = input.LA(1);

            	    if ( (LA51_0==COLON) ) {
            	        alt51=1;
            	    }
            	    switch (alt51) {
            	        case 1 :
            	            // USE.g:551:26: COLON t= type
            	            {
            	            match(input,COLON,FOLLOW_COLON_in_expression2296); 
            	            pushFollow(FOLLOW_type_in_expression2300);
            	            t=type();

            	            state._fsp--;


            	            }
            	            break;

            	    }

            	    match(input,EQUAL,FOLLOW_EQUAL_in_expression2305); 
            	    pushFollow(FOLLOW_expression_in_expression2309);
            	    e1=expression();

            	    state._fsp--;

            	    match(input,83,FOLLOW_83_in_expression2311); 
            	     ASTLetExpression nextLet = new ASTLetExpression(name, t, e1);
            	             if ( firstLet == null ) 
            	                 firstLet = nextLet;
            	             if ( prevLet != null ) 
            	                 prevLet.setInExpr(nextLet);
            	             prevLet = nextLet;
            	           

            	    }
            	    break;

            	default :
            	    break loop52;
                }
            } while (true);

            pushFollow(FOLLOW_conditionalImpliesExpression_in_expression2336);
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
    // USE.g:579:1: paramList returns [List paramList] : LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN ;
    public final List paramList() throws RecognitionException {
        List paramList = null;

        ASTVariableDeclaration v = null;


         paramList = new ArrayList(); 
        try {
            // USE.g:581:1: ( LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN )
            // USE.g:582:5: LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN
            {
            match(input,LPAREN,FOLLOW_LPAREN_in_paramList2369); 
            // USE.g:583:5: (v= variableDeclaration ( COMMA v= variableDeclaration )* )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==IDENT) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // USE.g:584:7: v= variableDeclaration ( COMMA v= variableDeclaration )*
                    {
                    pushFollow(FOLLOW_variableDeclaration_in_paramList2386);
                    v=variableDeclaration();

                    state._fsp--;

                     paramList.add(v); 
                    // USE.g:585:7: ( COMMA v= variableDeclaration )*
                    loop53:
                    do {
                        int alt53=2;
                        int LA53_0 = input.LA(1);

                        if ( (LA53_0==COMMA) ) {
                            alt53=1;
                        }


                        switch (alt53) {
                    	case 1 :
                    	    // USE.g:585:9: COMMA v= variableDeclaration
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_paramList2398); 
                    	    pushFollow(FOLLOW_variableDeclaration_in_paramList2402);
                    	    v=variableDeclaration();

                    	    state._fsp--;

                    	     paramList.add(v); 

                    	    }
                    	    break;

                    	default :
                    	    break loop53;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_paramList2422); 

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
    // USE.g:593:1: idList returns [List idList] : id0= IDENT ( COMMA idn= IDENT )* ;
    public final List idList() throws RecognitionException {
        List idList = null;

        Token id0=null;
        Token idn=null;

         idList = new ArrayList(); 
        try {
            // USE.g:595:1: (id0= IDENT ( COMMA idn= IDENT )* )
            // USE.g:596:5: id0= IDENT ( COMMA idn= IDENT )*
            {
            id0=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList2451); 
             idList.add(id0); 
            // USE.g:597:5: ( COMMA idn= IDENT )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==COMMA) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // USE.g:597:7: COMMA idn= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_idList2461); 
            	    idn=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList2465); 
            	     idList.add(idn); 

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
    // USE.g:605:1: variableDeclaration returns [ASTVariableDeclaration n] : name= IDENT COLON t= type ;
    public final ASTVariableDeclaration variableDeclaration() throws RecognitionException {
        ASTVariableDeclaration n = null;

        Token name=null;
        ASTType t = null;


        try {
            // USE.g:606:1: (name= IDENT COLON t= type )
            // USE.g:607:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableDeclaration2496); 
            match(input,COLON,FOLLOW_COLON_in_variableDeclaration2498); 
            pushFollow(FOLLOW_type_in_variableDeclaration2502);
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
    // USE.g:615:1: conditionalImpliesExpression returns [ASTExpression n] : nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* ;
    public final ASTExpression conditionalImpliesExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndOrExp = null;

        ASTExpression n1 = null;


        try {
            // USE.g:616:1: (nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* )
            // USE.g:617:5: nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )*
            {
            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2538);
            nCndOrExp=conditionalOrExpression();

            state._fsp--;

            n = nCndOrExp;
            // USE.g:618:5: (op= 'implies' n1= conditionalOrExpression )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==86) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // USE.g:618:7: op= 'implies' n1= conditionalOrExpression
            	    {
            	    op=(Token)match(input,86,FOLLOW_86_in_conditionalImpliesExpression2551); 
            	    pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2555);
            	    n1=conditionalOrExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

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
    // USE.g:627:1: conditionalOrExpression returns [ASTExpression n] : nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* ;
    public final ASTExpression conditionalOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndXorExp = null;

        ASTExpression n1 = null;


        try {
            // USE.g:628:1: (nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* )
            // USE.g:629:5: nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )*
            {
            pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2600);
            nCndXorExp=conditionalXOrExpression();

            state._fsp--;

            n = nCndXorExp;
            // USE.g:630:5: (op= 'or' n1= conditionalXOrExpression )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==87) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // USE.g:630:7: op= 'or' n1= conditionalXOrExpression
            	    {
            	    op=(Token)match(input,87,FOLLOW_87_in_conditionalOrExpression2613); 
            	    pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2617);
            	    n1=conditionalXOrExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

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
    // USE.g:639:1: conditionalXOrExpression returns [ASTExpression n] : nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* ;
    public final ASTExpression conditionalXOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndAndExp = null;

        ASTExpression n1 = null;


        try {
            // USE.g:640:1: (nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* )
            // USE.g:641:5: nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )*
            {
            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2661);
            nCndAndExp=conditionalAndExpression();

            state._fsp--;

            n = nCndAndExp;
            // USE.g:642:5: (op= 'xor' n1= conditionalAndExpression )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==88) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // USE.g:642:7: op= 'xor' n1= conditionalAndExpression
            	    {
            	    op=(Token)match(input,88,FOLLOW_88_in_conditionalXOrExpression2674); 
            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2678);
            	    n1=conditionalAndExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

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
    // USE.g:651:1: conditionalAndExpression returns [ASTExpression n] : nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* ;
    public final ASTExpression conditionalAndExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nEqExp = null;

        ASTExpression n1 = null;


        try {
            // USE.g:652:1: (nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* )
            // USE.g:653:5: nEqExp= equalityExpression (op= 'and' n1= equalityExpression )*
            {
            pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression2722);
            nEqExp=equalityExpression();

            state._fsp--;

            n = nEqExp;
            // USE.g:654:5: (op= 'and' n1= equalityExpression )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==89) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // USE.g:654:7: op= 'and' n1= equalityExpression
            	    {
            	    op=(Token)match(input,89,FOLLOW_89_in_conditionalAndExpression2735); 
            	    pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression2739);
            	    n1=equalityExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

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
    // USE.g:663:1: equalityExpression returns [ASTExpression n] : nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* ;
    public final ASTExpression equalityExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nRelExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // USE.g:665:1: (nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* )
            // USE.g:666:5: nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            {
            pushFollow(FOLLOW_relationalExpression_in_equalityExpression2787);
            nRelExp=relationalExpression();

            state._fsp--;

            n = nRelExp;
            // USE.g:667:5: ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==EQUAL||LA60_0==NOT_EQUAL) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // USE.g:667:7: ( EQUAL | NOT_EQUAL ) n1= relationalExpression
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

            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression2816);
            	    n1=relationalExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

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
    // USE.g:677:1: relationalExpression returns [ASTExpression n] : nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* ;
    public final ASTExpression relationalExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nAddiExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // USE.g:679:1: (nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* )
            // USE.g:680:5: nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            {
            pushFollow(FOLLOW_additiveExpression_in_relationalExpression2865);
            nAddiExp=additiveExpression();

            state._fsp--;

            n = nAddiExp;
            // USE.g:681:5: ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==LESS||(LA61_0>=GREATER && LA61_0<=GREATER_EQUAL)) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // USE.g:681:7: ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression
            	    {
            	     op = input.LT(1); 
            	    if ( input.LA(1)==LESS||(input.LA(1)>=GREATER && input.LA(1)<=GREATER_EQUAL) ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_additiveExpression_in_relationalExpression2901);
            	    n1=additiveExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

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
    // USE.g:691:1: additiveExpression returns [ASTExpression n] : nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* ;
    public final ASTExpression additiveExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nMulExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // USE.g:693:1: (nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* )
            // USE.g:694:5: nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            {
            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression2951);
            nMulExp=multiplicativeExpression();

            state._fsp--;

            n = nMulExp;
            // USE.g:695:5: ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            loop62:
            do {
                int alt62=2;
                int LA62_0 = input.LA(1);

                if ( ((LA62_0>=PLUS && LA62_0<=MINUS)) ) {
                    alt62=1;
                }


                switch (alt62) {
            	case 1 :
            	    // USE.g:695:7: ( PLUS | MINUS ) n1= multiplicativeExpression
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

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression2979);
            	    n1=multiplicativeExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

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
    // USE.g:706:1: multiplicativeExpression returns [ASTExpression n] : nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* ;
    public final ASTExpression multiplicativeExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // USE.g:708:1: (nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* )
            // USE.g:709:5: nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            {
            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3029);
            nUnExp=unaryExpression();

            state._fsp--;

             n = nUnExp;
            // USE.g:710:5: ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( (LA63_0==STAR||LA63_0==SLASH||LA63_0==90) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // USE.g:710:7: ( STAR | SLASH | 'div' ) n1= unaryExpression
            	    {
            	     op = input.LT(1); 
            	    if ( input.LA(1)==STAR||input.LA(1)==SLASH||input.LA(1)==90 ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3061);
            	    n1=unaryExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

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
    // USE.g:722:1: unaryExpression returns [ASTExpression n] : ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression );
    public final ASTExpression unaryExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression nPosExp = null;


         Token op = null; 
        try {
            // USE.g:724:1: ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression )
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( ((LA64_0>=PLUS && LA64_0<=MINUS)||LA64_0==91) ) {
                alt64=1;
            }
            else if ( (LA64_0==IDENT||LA64_0==INT||LA64_0==LPAREN||(LA64_0>=REAL && LA64_0<=HASH)||LA64_0==75||(LA64_0>=93 && LA64_0<=106)) ) {
                alt64=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 64, 0, input);

                throw nvae;
            }
            switch (alt64) {
                case 1 :
                    // USE.g:725:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    {
                    // USE.g:725:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    // USE.g:725:9: ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression
                    {
                     op = input.LT(1); 
                    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS)||input.LA(1)==91 ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression3147);
                    nUnExp=unaryExpression();

                    state._fsp--;

                     n = new ASTUnaryExpression(op, nUnExp); 

                    }


                    }
                    break;
                case 2 :
                    // USE.g:729:7: nPosExp= postfixExpression
                    {
                    pushFollow(FOLLOW_postfixExpression_in_unaryExpression3167);
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
    // USE.g:737:1: postfixExpression returns [ASTExpression n] : nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* ;
    public final ASTExpression postfixExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nPrimExp = null;

        ASTExpression nPc = null;


         boolean arrow = false; 
        try {
            // USE.g:739:1: (nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* )
            // USE.g:740:5: nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            {
            pushFollow(FOLLOW_primaryExpression_in_postfixExpression3200);
            nPrimExp=primaryExpression();

            state._fsp--;

             n = nPrimExp; 
            // USE.g:741:5: ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( ((LA66_0>=ARROW && LA66_0<=DOT)) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // USE.g:742:6: ( ARROW | DOT ) nPc= propertyCall[$n, arrow]
            	    {
            	    // USE.g:742:6: ( ARROW | DOT )
            	    int alt65=2;
            	    int LA65_0 = input.LA(1);

            	    if ( (LA65_0==ARROW) ) {
            	        alt65=1;
            	    }
            	    else if ( (LA65_0==DOT) ) {
            	        alt65=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 65, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt65) {
            	        case 1 :
            	            // USE.g:742:8: ARROW
            	            {
            	            match(input,ARROW,FOLLOW_ARROW_in_postfixExpression3218); 
            	             arrow = true; 

            	            }
            	            break;
            	        case 2 :
            	            // USE.g:742:34: DOT
            	            {
            	            match(input,DOT,FOLLOW_DOT_in_postfixExpression3224); 
            	             arrow = false; 

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_propertyCall_in_postfixExpression3235);
            	    nPc=propertyCall(n, arrow);

            	    state._fsp--;

            	     n = nPc; 

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
    // USE.g:758:1: primaryExpression returns [ASTExpression n] : (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? );
    public final ASTExpression primaryExpression() throws RecognitionException {
        ASTExpression n = null;

        Token id1=null;
        ASTExpression nLit = null;

        ASTExpression nPc = null;

        ASTExpression nExp = null;

        ASTExpression nIfExp = null;


        try {
            // USE.g:759:1: (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? )
            int alt69=5;
            switch ( input.LA(1) ) {
            case INT:
            case REAL:
            case STRING:
            case HASH:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
                {
                alt69=1;
                }
                break;
            case IDENT:
                {
                int LA69_2 = input.LA(2);

                if ( (LA69_2==EOF||LA69_2==IDENT||(LA69_2>=RBRACE && LA69_2<=LESS)||(LA69_2>=EQUAL && LA69_2<=LBRACK)||(LA69_2>=COMMA && LA69_2<=DOTDOT)||LA69_2==STAR||(LA69_2>=COLON_EQUAL && LA69_2<=ARROW)||(LA69_2>=AT && LA69_2<=BAR)||LA69_2==44||(LA69_2>=46 && LA69_2<=47)||(LA69_2>=50 && LA69_2<=52)||(LA69_2>=54 && LA69_2<=57)||(LA69_2>=60 && LA69_2<=68)||LA69_2==70||LA69_2==72||(LA69_2>=74 && LA69_2<=84)||(LA69_2>=86 && LA69_2<=90)) ) {
                    alt69=2;
                }
                else if ( (LA69_2==DOT) ) {
                    int LA69_6 = input.LA(3);

                    if ( (LA69_6==92) ) {
                        alt69=5;
                    }
                    else if ( (LA69_6==IDENT||(LA69_6>=93 && LA69_6<=96)) ) {
                        alt69=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 69, 6, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 69, 2, input);

                    throw nvae;
                }
                }
                break;
            case 93:
            case 94:
            case 95:
            case 96:
                {
                alt69=2;
                }
                break;
            case LPAREN:
                {
                alt69=3;
                }
                break;
            case 75:
                {
                alt69=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 69, 0, input);

                throw nvae;
            }

            switch (alt69) {
                case 1 :
                    // USE.g:760:7: nLit= literal
                    {
                    pushFollow(FOLLOW_literal_in_primaryExpression3275);
                    nLit=literal();

                    state._fsp--;

                     n = nLit; 

                    }
                    break;
                case 2 :
                    // USE.g:761:7: nPc= propertyCall[null, false]
                    {
                    pushFollow(FOLLOW_propertyCall_in_primaryExpression3287);
                    nPc=propertyCall(null, false);

                    state._fsp--;

                     n = nPc; 

                    }
                    break;
                case 3 :
                    // USE.g:762:7: LPAREN nExp= expression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression3298); 
                    pushFollow(FOLLOW_expression_in_primaryExpression3302);
                    nExp=expression();

                    state._fsp--;

                    match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression3304); 
                     n = nExp; 

                    }
                    break;
                case 4 :
                    // USE.g:763:7: nIfExp= ifExpression
                    {
                    pushFollow(FOLLOW_ifExpression_in_primaryExpression3316);
                    nIfExp=ifExpression();

                    state._fsp--;

                     n = nIfExp; 

                    }
                    break;
                case 5 :
                    // USE.g:765:7: id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )?
                    {
                    id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_primaryExpression3333); 
                    match(input,DOT,FOLLOW_DOT_in_primaryExpression3335); 
                    match(input,92,FOLLOW_92_in_primaryExpression3337); 
                    // USE.g:765:36: ( LPAREN RPAREN )?
                    int alt67=2;
                    int LA67_0 = input.LA(1);

                    if ( (LA67_0==LPAREN) ) {
                        alt67=1;
                    }
                    switch (alt67) {
                        case 1 :
                            // USE.g:765:38: LPAREN RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression3341); 
                            match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression3343); 

                            }
                            break;

                    }

                     n = new ASTAllInstancesExpression(id1); 
                    // USE.g:767:7: ( AT 'pre' )?
                    int alt68=2;
                    int LA68_0 = input.LA(1);

                    if ( (LA68_0==AT) ) {
                        alt68=1;
                    }
                    switch (alt68) {
                        case 1 :
                            // USE.g:767:9: AT 'pre'
                            {
                            match(input,AT,FOLLOW_AT_in_primaryExpression3364); 
                            match(input,63,FOLLOW_63_in_primaryExpression3366); 
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
    // USE.g:780:1: propertyCall[ASTExpression source, boolean followsArrow] returns [ASTExpression n] : ({...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] );
    public final ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExpQuery = null;

        ASTExpression nExpIterate = null;

        ASTOperationExpression nExpOperation = null;

        ASTTypeArgExpression nExpType = null;


        try {
            // USE.g:781:1: ({...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] )
            int alt70=4;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA70_1 = input.LA(2);

                if ( (( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                    alt70=1;
                }
                else if ( (true) ) {
                    alt70=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 70, 1, input);

                    throw nvae;
                }
                }
                break;
            case 93:
                {
                alt70=2;
                }
                break;
            case 94:
            case 95:
            case 96:
                {
                alt70=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 70, 0, input);

                throw nvae;
            }

            switch (alt70) {
                case 1 :
                    // USE.g:785:7: {...}?nExpQuery= queryExpression[source]
                    {
                    if ( !(( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                        throw new FailedPredicateException(input, "propertyCall", " org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) ");
                    }
                    pushFollow(FOLLOW_queryExpression_in_propertyCall3432);
                    nExpQuery=queryExpression(source);

                    state._fsp--;

                     n = nExpQuery; 

                    }
                    break;
                case 2 :
                    // USE.g:787:7: nExpIterate= iterateExpression[source]
                    {
                    pushFollow(FOLLOW_iterateExpression_in_propertyCall3445);
                    nExpIterate=iterateExpression(source);

                    state._fsp--;

                     n = nExpIterate; 

                    }
                    break;
                case 3 :
                    // USE.g:788:7: nExpOperation= operationExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_operationExpression_in_propertyCall3458);
                    nExpOperation=operationExpression(source, followsArrow);

                    state._fsp--;

                     n = nExpOperation; 

                    }
                    break;
                case 4 :
                    // USE.g:789:7: nExpType= typeExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_typeExpression_in_propertyCall3471);
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
    // USE.g:798:1: queryExpression[ASTExpression range] returns [ASTExpression n] : op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN ;
    public final ASTExpression queryExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTElemVarsDeclaration decls = null;

        ASTExpression nExp = null;


        ASTElemVarsDeclaration decl = new ASTElemVarsDeclaration(); 
        try {
            // USE.g:799:69: (op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN )
            // USE.g:800:5: op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN
            {
            op=(Token)match(input,IDENT,FOLLOW_IDENT_in_queryExpression3506); 
            match(input,LPAREN,FOLLOW_LPAREN_in_queryExpression3513); 
            // USE.g:802:5: (decls= elemVarsDeclaration BAR )?
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
                    // USE.g:802:7: decls= elemVarsDeclaration BAR
                    {
                    pushFollow(FOLLOW_elemVarsDeclaration_in_queryExpression3524);
                    decls=elemVarsDeclaration();

                    state._fsp--;

                    decl = decls;
                    match(input,BAR,FOLLOW_BAR_in_queryExpression3528); 

                    }
                    break;

            }

            pushFollow(FOLLOW_expression_in_queryExpression3539);
            nExp=expression();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_queryExpression3545); 
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
    // USE.g:816:1: iterateExpression[ASTExpression range] returns [ASTExpression n] : i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN ;
    public final ASTExpression iterateExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTElemVarsDeclaration decls = null;

        ASTVariableInitialization init = null;

        ASTExpression nExp = null;


        try {
            // USE.g:816:65: (i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN )
            // USE.g:817:5: i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN
            {
            i=(Token)match(input,93,FOLLOW_93_in_iterateExpression3577); 
            match(input,LPAREN,FOLLOW_LPAREN_in_iterateExpression3583); 
            pushFollow(FOLLOW_elemVarsDeclaration_in_iterateExpression3591);
            decls=elemVarsDeclaration();

            state._fsp--;

            match(input,SEMI,FOLLOW_SEMI_in_iterateExpression3593); 
            pushFollow(FOLLOW_variableInitialization_in_iterateExpression3601);
            init=variableInitialization();

            state._fsp--;

            match(input,BAR,FOLLOW_BAR_in_iterateExpression3603); 
            pushFollow(FOLLOW_expression_in_iterateExpression3611);
            nExp=expression();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_iterateExpression3617); 
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
    // USE.g:838:1: operationExpression[ASTExpression source, boolean followsArrow] returns [ASTOperationExpression n] : name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? ;
    public final ASTOperationExpression operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTOperationExpression n = null;

        Token name=null;
        Token rolename=null;
        ASTExpression e = null;


        try {
            // USE.g:840:1: (name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? )
            // USE.g:841:5: name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression3661); 
             n = new ASTOperationExpression(name, source, followsArrow); 
            // USE.g:844:5: ( LBRACK rolename= IDENT RBRACK )?
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==LBRACK) ) {
                alt72=1;
            }
            switch (alt72) {
                case 1 :
                    // USE.g:844:7: LBRACK rolename= IDENT RBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_operationExpression3677); 
                    rolename=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression3681); 
                    match(input,RBRACK,FOLLOW_RBRACK_in_operationExpression3683); 
                     n.setExplicitRolename(rolename); 

                    }
                    break;

            }

            // USE.g:846:5: ( AT 'pre' )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==AT) ) {
                alt73=1;
            }
            switch (alt73) {
                case 1 :
                    // USE.g:846:7: AT 'pre'
                    {
                    match(input,AT,FOLLOW_AT_in_operationExpression3696); 
                    match(input,63,FOLLOW_63_in_operationExpression3698); 
                     n.setIsPre(); 

                    }
                    break;

            }

            // USE.g:847:5: ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==LPAREN) ) {
                alt76=1;
            }
            switch (alt76) {
                case 1 :
                    // USE.g:848:7: LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_operationExpression3719); 
                     n.hasParentheses(); 
                    // USE.g:849:7: (e= expression ( COMMA e= expression )* )?
                    int alt75=2;
                    int LA75_0 = input.LA(1);

                    if ( (LA75_0==IDENT||LA75_0==INT||LA75_0==LPAREN||(LA75_0>=PLUS && LA75_0<=MINUS)||(LA75_0>=REAL && LA75_0<=HASH)||LA75_0==75||LA75_0==85||LA75_0==91||(LA75_0>=93 && LA75_0<=106)) ) {
                        alt75=1;
                    }
                    switch (alt75) {
                        case 1 :
                            // USE.g:850:7: e= expression ( COMMA e= expression )*
                            {
                            pushFollow(FOLLOW_expression_in_operationExpression3740);
                            e=expression();

                            state._fsp--;

                             n.addArg(e); 
                            // USE.g:851:7: ( COMMA e= expression )*
                            loop74:
                            do {
                                int alt74=2;
                                int LA74_0 = input.LA(1);

                                if ( (LA74_0==COMMA) ) {
                                    alt74=1;
                                }


                                switch (alt74) {
                            	case 1 :
                            	    // USE.g:851:9: COMMA e= expression
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_operationExpression3752); 
                            	    pushFollow(FOLLOW_expression_in_operationExpression3756);
                            	    e=expression();

                            	    state._fsp--;

                            	     n.addArg(e); 

                            	    }
                            	    break;

                            	default :
                            	    break loop74;
                                }
                            } while (true);


                            }
                            break;

                    }

                    match(input,RPAREN,FOLLOW_RPAREN_in_operationExpression3776); 

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
    // USE.g:863:1: typeExpression[ASTExpression source, boolean followsArrow] returns [ASTTypeArgExpression n] : ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN ;
    public final ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTTypeArgExpression n = null;

        ASTType t = null;


         Token opToken = null; 
        try {
            // USE.g:866:1: ( ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN )
            // USE.g:867:2: ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN
            {
             opToken = input.LT(1); 
            if ( (input.LA(1)>=94 && input.LA(1)<=96) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_typeExpression3835); 
            pushFollow(FOLLOW_type_in_typeExpression3839);
            t=type();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_typeExpression3841); 
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
    // USE.g:878:1: elemVarsDeclaration returns [ASTElemVarsDeclaration n] : idListRes= idList ( COLON t= type )? ;
    public final ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException {
        ASTElemVarsDeclaration n = null;

        List idListRes = null;

        ASTType t = null;


         List idList; 
        try {
            // USE.g:880:1: (idListRes= idList ( COLON t= type )? )
            // USE.g:881:5: idListRes= idList ( COLON t= type )?
            {
            pushFollow(FOLLOW_idList_in_elemVarsDeclaration3880);
            idListRes=idList();

            state._fsp--;

            // USE.g:882:5: ( COLON t= type )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==COLON) ) {
                alt77=1;
            }
            switch (alt77) {
                case 1 :
                    // USE.g:882:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_elemVarsDeclaration3888); 
                    pushFollow(FOLLOW_type_in_elemVarsDeclaration3892);
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
    // USE.g:891:1: variableInitialization returns [ASTVariableInitialization n] : name= IDENT COLON t= type EQUAL e= expression ;
    public final ASTVariableInitialization variableInitialization() throws RecognitionException {
        ASTVariableInitialization n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // USE.g:892:1: (name= IDENT COLON t= type EQUAL e= expression )
            // USE.g:893:5: name= IDENT COLON t= type EQUAL e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableInitialization3927); 
            match(input,COLON,FOLLOW_COLON_in_variableInitialization3929); 
            pushFollow(FOLLOW_type_in_variableInitialization3933);
            t=type();

            state._fsp--;

            match(input,EQUAL,FOLLOW_EQUAL_in_variableInitialization3935); 
            pushFollow(FOLLOW_expression_in_variableInitialization3939);
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
    // USE.g:902:1: ifExpression returns [ASTExpression n] : i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' ;
    public final ASTExpression ifExpression() throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTExpression cond = null;

        ASTExpression t = null;

        ASTExpression e = null;


        try {
            // USE.g:903:1: (i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' )
            // USE.g:904:5: i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif'
            {
            i=(Token)match(input,75,FOLLOW_75_in_ifExpression3971); 
            pushFollow(FOLLOW_expression_in_ifExpression3975);
            cond=expression();

            state._fsp--;

            match(input,76,FOLLOW_76_in_ifExpression3977); 
            pushFollow(FOLLOW_expression_in_ifExpression3981);
            t=expression();

            state._fsp--;

            match(input,77,FOLLOW_77_in_ifExpression3983); 
            pushFollow(FOLLOW_expression_in_ifExpression3987);
            e=expression();

            state._fsp--;

            match(input,78,FOLLOW_78_in_ifExpression3989); 
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
    // USE.g:922:1: literal returns [ASTExpression n] : (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral );
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


        try {
            // USE.g:923:1: (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral )
            int alt78=10;
            switch ( input.LA(1) ) {
            case 97:
                {
                alt78=1;
                }
                break;
            case 98:
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
            case 99:
            case 100:
            case 101:
                {
                alt78=7;
                }
                break;
            case 102:
                {
                alt78=8;
                }
                break;
            case 103:
            case 104:
            case 105:
                {
                alt78=9;
                }
                break;
            case 106:
                {
                alt78=10;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 78, 0, input);

                throw nvae;
            }

            switch (alt78) {
                case 1 :
                    // USE.g:924:7: t= 'true'
                    {
                    t=(Token)match(input,97,FOLLOW_97_in_literal4028); 
                     n = new ASTBooleanLiteral(true); 

                    }
                    break;
                case 2 :
                    // USE.g:925:7: f= 'false'
                    {
                    f=(Token)match(input,98,FOLLOW_98_in_literal4042); 
                     n = new ASTBooleanLiteral(false); 

                    }
                    break;
                case 3 :
                    // USE.g:926:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_literal4055); 
                     n = new ASTIntegerLiteral(i); 

                    }
                    break;
                case 4 :
                    // USE.g:927:7: r= REAL
                    {
                    r=(Token)match(input,REAL,FOLLOW_REAL_in_literal4070); 
                     n = new ASTRealLiteral(r); 

                    }
                    break;
                case 5 :
                    // USE.g:928:7: s= STRING
                    {
                    s=(Token)match(input,STRING,FOLLOW_STRING_in_literal4084); 
                     n = new ASTStringLiteral(s); 

                    }
                    break;
                case 6 :
                    // USE.g:929:7: HASH enumLit= IDENT
                    {
                    match(input,HASH,FOLLOW_HASH_in_literal4094); 
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal4098); 
                     n = new ASTEnumLiteral(enumLit); 

                    }
                    break;
                case 7 :
                    // USE.g:930:7: nColIt= collectionLiteral
                    {
                    pushFollow(FOLLOW_collectionLiteral_in_literal4110);
                    nColIt=collectionLiteral();

                    state._fsp--;

                     n = nColIt; 

                    }
                    break;
                case 8 :
                    // USE.g:931:7: nEColIt= emptyCollectionLiteral
                    {
                    pushFollow(FOLLOW_emptyCollectionLiteral_in_literal4122);
                    nEColIt=emptyCollectionLiteral();

                    state._fsp--;

                     n = nEColIt; 

                    }
                    break;
                case 9 :
                    // USE.g:932:7: nUndLit= undefinedLiteral
                    {
                    pushFollow(FOLLOW_undefinedLiteral_in_literal4134);
                    nUndLit=undefinedLiteral();

                    state._fsp--;

                    n = nUndLit; 

                    }
                    break;
                case 10 :
                    // USE.g:933:7: nTupleLit= tupleLiteral
                    {
                    pushFollow(FOLLOW_tupleLiteral_in_literal4146);
                    nTupleLit=tupleLiteral();

                    state._fsp--;

                    n = nTupleLit; 

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
    // USE.g:941:1: collectionLiteral returns [ASTCollectionLiteral n] : ( 'Set' | 'Sequence' | 'Bag' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE ;
    public final ASTCollectionLiteral collectionLiteral() throws RecognitionException {
        ASTCollectionLiteral n = null;

        ASTCollectionItem ci = null;


         Token op = null; 
        try {
            // USE.g:943:1: ( ( 'Set' | 'Sequence' | 'Bag' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE )
            // USE.g:944:5: ( 'Set' | 'Sequence' | 'Bag' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE
            {
             op = input.LT(1); 
            if ( (input.LA(1)>=99 && input.LA(1)<=101) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

             n = new ASTCollectionLiteral(op); 
            match(input,LBRACE,FOLLOW_LBRACE_in_collectionLiteral4209); 
            // USE.g:948:5: (ci= collectionItem ( COMMA ci= collectionItem )* )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==IDENT||LA80_0==INT||LA80_0==LPAREN||(LA80_0>=PLUS && LA80_0<=MINUS)||(LA80_0>=REAL && LA80_0<=HASH)||LA80_0==75||LA80_0==85||LA80_0==91||(LA80_0>=93 && LA80_0<=106)) ) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // USE.g:949:7: ci= collectionItem ( COMMA ci= collectionItem )*
                    {
                    pushFollow(FOLLOW_collectionItem_in_collectionLiteral4226);
                    ci=collectionItem();

                    state._fsp--;

                     n.addItem(ci); 
                    // USE.g:950:7: ( COMMA ci= collectionItem )*
                    loop79:
                    do {
                        int alt79=2;
                        int LA79_0 = input.LA(1);

                        if ( (LA79_0==COMMA) ) {
                            alt79=1;
                        }


                        switch (alt79) {
                    	case 1 :
                    	    // USE.g:950:9: COMMA ci= collectionItem
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_collectionLiteral4239); 
                    	    pushFollow(FOLLOW_collectionItem_in_collectionLiteral4243);
                    	    ci=collectionItem();

                    	    state._fsp--;

                    	     n.addItem(ci); 

                    	    }
                    	    break;

                    	default :
                    	    break loop79;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RBRACE,FOLLOW_RBRACE_in_collectionLiteral4262); 

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
    // USE.g:959:1: collectionItem returns [ASTCollectionItem n] : e= expression ( DOTDOT e= expression )? ;
    public final ASTCollectionItem collectionItem() throws RecognitionException {
        ASTCollectionItem n = null;

        ASTExpression e = null;


         n = new ASTCollectionItem(); 
        try {
            // USE.g:961:1: (e= expression ( DOTDOT e= expression )? )
            // USE.g:962:5: e= expression ( DOTDOT e= expression )?
            {
            pushFollow(FOLLOW_expression_in_collectionItem4291);
            e=expression();

            state._fsp--;

             n.setFirst(e); 
            // USE.g:963:5: ( DOTDOT e= expression )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==DOTDOT) ) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // USE.g:963:7: DOTDOT e= expression
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_collectionItem4302); 
                    pushFollow(FOLLOW_expression_in_collectionItem4306);
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
    // USE.g:973:1: emptyCollectionLiteral returns [ASTEmptyCollectionLiteral n] : 'oclEmpty' LPAREN t= collectionType RPAREN ;
    public final ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException {
        ASTEmptyCollectionLiteral n = null;

        ASTCollectionType t = null;


        try {
            // USE.g:974:1: ( 'oclEmpty' LPAREN t= collectionType RPAREN )
            // USE.g:975:5: 'oclEmpty' LPAREN t= collectionType RPAREN
            {
            match(input,102,FOLLOW_102_in_emptyCollectionLiteral4335); 
            match(input,LPAREN,FOLLOW_LPAREN_in_emptyCollectionLiteral4337); 
            pushFollow(FOLLOW_collectionType_in_emptyCollectionLiteral4341);
            t=collectionType();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_emptyCollectionLiteral4343); 
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
    // USE.g:986:1: undefinedLiteral returns [ASTUndefinedLiteral n] : ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' );
    public final ASTUndefinedLiteral undefinedLiteral() throws RecognitionException {
        ASTUndefinedLiteral n = null;

        ASTType t = null;


        try {
            // USE.g:987:1: ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' )
            int alt82=3;
            switch ( input.LA(1) ) {
            case 103:
                {
                alt82=1;
                }
                break;
            case 104:
                {
                alt82=2;
                }
                break;
            case 105:
                {
                alt82=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 82, 0, input);

                throw nvae;
            }

            switch (alt82) {
                case 1 :
                    // USE.g:988:5: 'oclUndefined' LPAREN t= type RPAREN
                    {
                    match(input,103,FOLLOW_103_in_undefinedLiteral4373); 
                    match(input,LPAREN,FOLLOW_LPAREN_in_undefinedLiteral4375); 
                    pushFollow(FOLLOW_type_in_undefinedLiteral4379);
                    t=type();

                    state._fsp--;

                    match(input,RPAREN,FOLLOW_RPAREN_in_undefinedLiteral4381); 
                     n = new ASTUndefinedLiteral(t); 

                    }
                    break;
                case 2 :
                    // USE.g:991:5: 'Undefined'
                    {
                    match(input,104,FOLLOW_104_in_undefinedLiteral4395); 
                     n = new ASTUndefinedLiteral(); 

                    }
                    break;
                case 3 :
                    // USE.g:994:5: 'null'
                    {
                    match(input,105,FOLLOW_105_in_undefinedLiteral4409); 
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
    // USE.g:1003:1: tupleLiteral returns [ASTTupleLiteral n] : 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE ;
    public final ASTTupleLiteral tupleLiteral() throws RecognitionException {
        ASTTupleLiteral n = null;

        ASTTupleItem ti = null;


         List tiList = new ArrayList(); 
        try {
            // USE.g:1005:1: ( 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE )
            // USE.g:1006:5: 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE
            {
            match(input,106,FOLLOW_106_in_tupleLiteral4443); 
            match(input,LBRACE,FOLLOW_LBRACE_in_tupleLiteral4449); 
            pushFollow(FOLLOW_tupleItem_in_tupleLiteral4457);
            ti=tupleItem();

            state._fsp--;

             tiList.add(ti); 
            // USE.g:1009:5: ( COMMA ti= tupleItem )*
            loop83:
            do {
                int alt83=2;
                int LA83_0 = input.LA(1);

                if ( (LA83_0==COMMA) ) {
                    alt83=1;
                }


                switch (alt83) {
            	case 1 :
            	    // USE.g:1009:7: COMMA ti= tupleItem
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleLiteral4468); 
            	    pushFollow(FOLLOW_tupleItem_in_tupleLiteral4472);
            	    ti=tupleItem();

            	    state._fsp--;

            	     tiList.add(ti); 

            	    }
            	    break;

            	default :
            	    break loop83;
                }
            } while (true);

            match(input,RBRACE,FOLLOW_RBRACE_in_tupleLiteral4483); 
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
    // USE.g:1017:1: tupleItem returns [ASTTupleItem n] : name= IDENT ( COLON | EQUAL ) e= expression ;
    public final ASTTupleItem tupleItem() throws RecognitionException {
        ASTTupleItem n = null;

        Token name=null;
        ASTExpression e = null;


        try {
            // USE.g:1018:1: (name= IDENT ( COLON | EQUAL ) e= expression )
            // USE.g:1019:5: name= IDENT ( COLON | EQUAL ) e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tupleItem4514); 
            if ( (input.LA(1)>=COLON && input.LA(1)<=EQUAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            pushFollow(FOLLOW_expression_in_tupleItem4524);
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


    // $ANTLR start "type"
    // USE.g:1030:1: type returns [ASTType n] : (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) ;
    public final ASTType type() throws RecognitionException {
        ASTType n = null;

        ASTSimpleType nTSimple = null;

        ASTCollectionType nTCollection = null;

        ASTTupleType nTTuple = null;


         Token tok = null; 
        try {
            // USE.g:1032:1: ( (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) )
            // USE.g:1033:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            {
             tok = input.LT(1); /* remember start of type */ 
            // USE.g:1034:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            int alt84=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt84=1;
                }
                break;
            case 99:
            case 100:
            case 101:
            case 107:
                {
                alt84=2;
                }
                break;
            case 106:
                {
                alt84=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 84, 0, input);

                throw nvae;
            }

            switch (alt84) {
                case 1 :
                    // USE.g:1035:7: nTSimple= simpleType
                    {
                    pushFollow(FOLLOW_simpleType_in_type4577);
                    nTSimple=simpleType();

                    state._fsp--;

                     n = nTSimple; n.setStartToken(tok); 

                    }
                    break;
                case 2 :
                    // USE.g:1036:7: nTCollection= collectionType
                    {
                    pushFollow(FOLLOW_collectionType_in_type4589);
                    nTCollection=collectionType();

                    state._fsp--;

                     n = nTCollection; n.setStartToken(tok); 

                    }
                    break;
                case 3 :
                    // USE.g:1037:7: nTTuple= tupleType
                    {
                    pushFollow(FOLLOW_tupleType_in_type4601);
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
    // USE.g:1042:1: typeOnly returns [ASTType n] : nT= type EOF ;
    public final ASTType typeOnly() throws RecognitionException {
        ASTType n = null;

        ASTType nT = null;


        try {
            // USE.g:1043:1: (nT= type EOF )
            // USE.g:1044:5: nT= type EOF
            {
            pushFollow(FOLLOW_type_in_typeOnly4633);
            nT=type();

            state._fsp--;

            match(input,EOF,FOLLOW_EOF_in_typeOnly4635); 
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
    // USE.g:1054:1: simpleType returns [ASTSimpleType n] : name= IDENT ;
    public final ASTSimpleType simpleType() throws RecognitionException {
        ASTSimpleType n = null;

        Token name=null;

        try {
            // USE.g:1055:1: (name= IDENT )
            // USE.g:1056:5: name= IDENT
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_simpleType4663); 
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
    // USE.g:1064:1: collectionType returns [ASTCollectionType n] : ( 'Collection' | 'Set' | 'Sequence' | 'Bag' ) LPAREN elemType= type RPAREN ;
    public final ASTCollectionType collectionType() throws RecognitionException {
        ASTCollectionType n = null;

        ASTType elemType = null;


         Token op = null; 
        try {
            // USE.g:1066:1: ( ( 'Collection' | 'Set' | 'Sequence' | 'Bag' ) LPAREN elemType= type RPAREN )
            // USE.g:1067:5: ( 'Collection' | 'Set' | 'Sequence' | 'Bag' ) LPAREN elemType= type RPAREN
            {
             op = input.LT(1); 
            if ( (input.LA(1)>=99 && input.LA(1)<=101)||input.LA(1)==107 ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_collectionType4724); 
            pushFollow(FOLLOW_type_in_collectionType4728);
            elemType=type();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_collectionType4730); 
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
    // USE.g:1077:1: tupleType returns [ASTTupleType n] : 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN ;
    public final ASTTupleType tupleType() throws RecognitionException {
        ASTTupleType n = null;

        ASTTuplePart tp = null;


         List tpList = new ArrayList(); 
        try {
            // USE.g:1079:1: ( 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN )
            // USE.g:1080:5: 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN
            {
            match(input,106,FOLLOW_106_in_tupleType4764); 
            match(input,LPAREN,FOLLOW_LPAREN_in_tupleType4766); 
            pushFollow(FOLLOW_tuplePart_in_tupleType4775);
            tp=tuplePart();

            state._fsp--;

             tpList.add(tp); 
            // USE.g:1082:5: ( COMMA tp= tuplePart )*
            loop85:
            do {
                int alt85=2;
                int LA85_0 = input.LA(1);

                if ( (LA85_0==COMMA) ) {
                    alt85=1;
                }


                switch (alt85) {
            	case 1 :
            	    // USE.g:1082:7: COMMA tp= tuplePart
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleType4786); 
            	    pushFollow(FOLLOW_tuplePart_in_tupleType4790);
            	    tp=tuplePart();

            	    state._fsp--;

            	     tpList.add(tp); 

            	    }
            	    break;

            	default :
            	    break loop85;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_tupleType4802); 
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
    // USE.g:1091:1: tuplePart returns [ASTTuplePart n] : name= IDENT COLON t= type ;
    public final ASTTuplePart tuplePart() throws RecognitionException {
        ASTTuplePart n = null;

        Token name=null;
        ASTType t = null;


        try {
            // USE.g:1092:1: (name= IDENT COLON t= type )
            // USE.g:1093:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tuplePart4834); 
            match(input,COLON,FOLLOW_COLON_in_tuplePart4836); 
            pushFollow(FOLLOW_type_in_tuplePart4840);
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


 

    public static final BitSet FOLLOW_43_in_model69 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_model73 = new BitSet(new long[]{0x02D8F00000000000L});
    public static final BitSet FOLLOW_enumTypeDefinition_in_model85 = new BitSet(new long[]{0x02D8F00000000000L});
    public static final BitSet FOLLOW_generalClassDefinition_in_model102 = new BitSet(new long[]{0x02D8D00000000000L});
    public static final BitSet FOLLOW_associationDefinition_in_model119 = new BitSet(new long[]{0x02D8D00000000000L});
    public static final BitSet FOLLOW_44_in_model135 = new BitSet(new long[]{0x12D8D00000000000L});
    public static final BitSet FOLLOW_invariant_in_model153 = new BitSet(new long[]{0x12D8D00000000000L});
    public static final BitSet FOLLOW_prePost_in_model174 = new BitSet(new long[]{0x12D8D00000000000L});
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
    public static final BitSet FOLLOW_44_in_classDefinition473 = new BitSet(new long[]{0x6004000000000000L});
    public static final BitSet FOLLOW_invariantClause_in_classDefinition493 = new BitSet(new long[]{0x6004000000000000L});
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
    public static final BitSet FOLLOW_44_in_associationClassDefinition702 = new BitSet(new long[]{0x60C4000000000000L});
    public static final BitSet FOLLOW_invariantClause_in_associationClassDefinition722 = new BitSet(new long[]{0x60C4000000000000L});
    public static final BitSet FOLLOW_set_in_associationClassDefinition756 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_associationClassDefinition785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_attributeDefinition814 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_attributeDefinition816 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_attributeDefinition820 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_attributeDefinition824 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationDefinition862 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_paramList_in_operationDefinition870 = new BitSet(new long[]{0x8100000000000682L,0x0000000000000001L});
    public static final BitSet FOLLOW_COLON_in_operationDefinition878 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_operationDefinition882 = new BitSet(new long[]{0x8100000000000482L,0x0000000000000001L});
    public static final BitSet FOLLOW_EQUAL_in_operationDefinition892 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_operationDefinition896 = new BitSet(new long[]{0x8100000000000082L,0x0000000000000001L});
    public static final BitSet FOLLOW_56_in_operationDefinition904 = new BitSet(new long[]{0x0004000000000000L,0x0000000000148D5EL});
    public static final BitSet FOLLOW_alActionList_in_operationDefinition908 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_operationDefinition910 = new BitSet(new long[]{0x8000000000000082L,0x0000000000000001L});
    public static final BitSet FOLLOW_prePostClause_in_operationDefinition929 = new BitSet(new long[]{0x8000000000000082L,0x0000000000000001L});
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
    public static final BitSet FOLLOW_RBRACK_in_associationEnd1076 = new BitSet(new long[]{0x0C00000000000082L});
    public static final BitSet FOLLOW_58_in_associationEnd1092 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd1096 = new BitSet(new long[]{0x0800000000000082L});
    public static final BitSet FOLLOW_59_in_associationEnd1109 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_associationEnd1122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity1157 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_COMMA_in_multiplicity1167 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity1171 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange1200 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_DOTDOT_in_multiplicityRange1210 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange1214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_multiplicitySpec1248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_multiplicitySpec1258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_invariant1299 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_invariant1309 = new BitSet(new long[]{0x0000000000002200L});
    public static final BitSet FOLLOW_COMMA_in_invariant1322 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_invariant1326 = new BitSet(new long[]{0x0000000000002200L});
    public static final BitSet FOLLOW_COLON_in_invariant1334 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_simpleType_in_invariant1346 = new BitSet(new long[]{0x6000000000000002L});
    public static final BitSet FOLLOW_invariantClause_in_invariant1358 = new BitSet(new long[]{0x6000000000000002L});
    public static final BitSet FOLLOW_61_in_invariantClause1389 = new BitSet(new long[]{0x0000000000000210L});
    public static final BitSet FOLLOW_IDENT_in_invariantClause1395 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_invariantClause1400 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_invariantClause1404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_62_in_invariantClause1414 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_61_in_invariantClause1416 = new BitSet(new long[]{0x0000000000000210L});
    public static final BitSet FOLLOW_IDENT_in_invariantClause1422 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_invariantClause1427 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_invariantClause1431 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_prePost1457 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_prePost1461 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_COLON_COLON_in_prePost1463 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_prePost1467 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_paramList_in_prePost1471 = new BitSet(new long[]{0x8000000000000200L,0x0000000000000001L});
    public static final BitSet FOLLOW_COLON_in_prePost1475 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_prePost1479 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_prePostClause_in_prePost1498 = new BitSet(new long[]{0x8000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_set_in_prePostClause1537 = new BitSet(new long[]{0x0000000000000210L});
    public static final BitSet FOLLOW_IDENT_in_prePostClause1552 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_prePostClause1557 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_prePostClause1561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alAction_in_alActionList1594 = new BitSet(new long[]{0x0000000000000002L,0x0000000000148D5EL});
    public static final BitSet FOLLOW_alCreateVar_in_alAction1620 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alDelete_in_alAction1632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alSet_in_alAction1644 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alSetCreate_in_alAction1656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alInsert_in_alAction1668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alDestroy_in_alAction1680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alIf_in_alAction1692 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alWhile_in_alAction1704 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alFor_in_alAction1716 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alExec_in_alAction1728 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_alCreateVar1747 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alCreateVar1755 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_alCreateVar1757 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_alCreateVar1761 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_67_in_alSet1783 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_alSet1787 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_alSet1789 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_alSet1793 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_68_in_alSetCreate1817 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_alSetCreate1821 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_alSetCreate1823 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alSetCreate1829 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alSetCreate1833 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_alSetCreate1842 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_alSetCreate1846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_70_in_alInsert1877 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_alInsert1879 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_alInsert1888 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_COMMA_in_alInsert1892 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_alInsert1900 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_COMMA_in_alInsert1906 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_alInsert1910 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_RPAREN_in_alInsert1922 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_alInsert1924 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alInsert1928 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_alDelete1960 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_alDelete1962 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_alDelete1970 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_COMMA_in_alDelete1974 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_alDelete1982 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_COMMA_in_alDelete1988 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_alDelete1992 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_RPAREN_in_alDelete2003 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_73_in_alDelete2005 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alDelete2009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_alDestroy2038 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_alDestroy2042 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_alIf2066 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_alIf2070 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_alIf2074 = new BitSet(new long[]{0x0000000000000000L,0x000000000014ED5EL});
    public static final BitSet FOLLOW_alActionList_in_alIf2078 = new BitSet(new long[]{0x0000000000000000L,0x0000000000006000L});
    public static final BitSet FOLLOW_77_in_alIf2082 = new BitSet(new long[]{0x0000000000000000L,0x000000000014CD5EL});
    public static final BitSet FOLLOW_alActionList_in_alIf2086 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_alIf2091 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_alWhile2110 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_alWhile2114 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_alWhile2118 = new BitSet(new long[]{0x0000000000000000L,0x0000000000168D5EL});
    public static final BitSet FOLLOW_alActionList_in_alWhile2124 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_alWhile2127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_82_in_alFor2146 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alFor2150 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_alFor2152 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_alFor2156 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_alFor2158 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_alFor2162 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_alFor2166 = new BitSet(new long[]{0x0000000000000010L,0x0000000000148D5EL});
    public static final BitSet FOLLOW_alActionList_in_alFor2172 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alFor2179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_alExec2199 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_alExec2203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_expressionOnly2238 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_expressionOnly2240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_85_in_expression2288 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_expression2292 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COLON_in_expression2296 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_expression2300 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_expression2305 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_expression2309 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_83_in_expression2311 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_conditionalImpliesExpression_in_expression2336 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_paramList2369 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList2386 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_COMMA_in_paramList2398 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList2402 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_RPAREN_in_paramList2422 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_idList2451 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_COMMA_in_idList2461 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_idList2465 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_IDENT_in_variableDeclaration2496 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_variableDeclaration2498 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_variableDeclaration2502 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2538 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_conditionalImpliesExpression2551 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2555 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2600 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_conditionalOrExpression2613 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2617 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2661 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_88_in_conditionalXOrExpression2674 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2678 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression2722 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_89_in_conditionalAndExpression2735 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression2739 = new BitSet(new long[]{0x0000000000000002L,0x0000000002000000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression2787 = new BitSet(new long[]{0x0000000000200402L});
    public static final BitSet FOLLOW_set_in_equalityExpression2806 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression2816 = new BitSet(new long[]{0x0000000000200402L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression2865 = new BitSet(new long[]{0x0000000001C00102L});
    public static final BitSet FOLLOW_set_in_relationalExpression2883 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression2901 = new BitSet(new long[]{0x0000000001C00102L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression2951 = new BitSet(new long[]{0x0000000006000002L});
    public static final BitSet FOLLOW_set_in_additiveExpression2969 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression2979 = new BitSet(new long[]{0x0000000006000002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3029 = new BitSet(new long[]{0x0000000008010002L,0x0000000004000000L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression3047 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3061 = new BitSet(new long[]{0x0000000008010002L,0x0000000004000000L});
    public static final BitSet FOLLOW_set_in_unaryExpression3123 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression3147 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpression_in_unaryExpression3167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_postfixExpression3200 = new BitSet(new long[]{0x0000000030000002L});
    public static final BitSet FOLLOW_ARROW_in_postfixExpression3218 = new BitSet(new long[]{0x0000000000000010L,0x00000001E0000000L});
    public static final BitSet FOLLOW_DOT_in_postfixExpression3224 = new BitSet(new long[]{0x0000000000000010L,0x00000001E0000000L});
    public static final BitSet FOLLOW_propertyCall_in_postfixExpression3235 = new BitSet(new long[]{0x0000000030000002L});
    public static final BitSet FOLLOW_literal_in_primaryExpression3275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyCall_in_primaryExpression3287 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression3298 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_primaryExpression3302 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression3304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifExpression_in_primaryExpression3316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_primaryExpression3333 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_DOT_in_primaryExpression3335 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_primaryExpression3337 = new BitSet(new long[]{0x0000000040080002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression3341 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression3343 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_AT_in_primaryExpression3364 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_63_in_primaryExpression3366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_queryExpression_in_propertyCall3432 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterateExpression_in_propertyCall3445 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationExpression_in_propertyCall3458 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeExpression_in_propertyCall3471 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_queryExpression3506 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_queryExpression3513 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_queryExpression3524 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_BAR_in_queryExpression3528 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_queryExpression3539 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_queryExpression3545 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_iterateExpression3577 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_iterateExpression3583 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_iterateExpression3591 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_in_iterateExpression3593 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableInitialization_in_iterateExpression3601 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_BAR_in_iterateExpression3603 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_iterateExpression3611 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_iterateExpression3617 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression3661 = new BitSet(new long[]{0x0000000040080802L});
    public static final BitSet FOLLOW_LBRACK_in_operationExpression3677 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression3681 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RBRACK_in_operationExpression3683 = new BitSet(new long[]{0x0000000040080002L});
    public static final BitSet FOLLOW_AT_in_operationExpression3696 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_63_in_operationExpression3698 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_LPAREN_in_operationExpression3719 = new BitSet(new long[]{0x0000000706188010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_operationExpression3740 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_COMMA_in_operationExpression3752 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_operationExpression3756 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_RPAREN_in_operationExpression3776 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_typeExpression3819 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_typeExpression3835 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_typeExpression3839 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_typeExpression3841 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_elemVarsDeclaration3880 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_COLON_in_elemVarsDeclaration3888 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_elemVarsDeclaration3892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableInitialization3927 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_variableInitialization3929 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_variableInitialization3933 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_variableInitialization3935 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_variableInitialization3939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_ifExpression3971 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_ifExpression3975 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_ifExpression3977 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_ifExpression3981 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_ifExpression3983 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_ifExpression3987 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_78_in_ifExpression3989 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_97_in_literal4028 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_literal4042 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_literal4055 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REAL_in_literal4070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal4084 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_literal4094 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_literal4098 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionLiteral_in_literal4110 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyCollectionLiteral_in_literal4122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedLiteral_in_literal4134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleLiteral_in_literal4146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionLiteral4184 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LBRACE_in_collectionLiteral4209 = new BitSet(new long[]{0x0000000706088050L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral4226 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_COMMA_in_collectionLiteral4239 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral4243 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_RBRACE_in_collectionLiteral4262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_collectionItem4291 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_DOTDOT_in_collectionItem4302 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_collectionItem4306 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_102_in_emptyCollectionLiteral4335 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_emptyCollectionLiteral4337 = new BitSet(new long[]{0x0000000000000000L,0x0000083800000000L});
    public static final BitSet FOLLOW_collectionType_in_emptyCollectionLiteral4341 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_emptyCollectionLiteral4343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_undefinedLiteral4373 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_undefinedLiteral4375 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_undefinedLiteral4379 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_undefinedLiteral4381 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_104_in_undefinedLiteral4395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_105_in_undefinedLiteral4409 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_106_in_tupleLiteral4443 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LBRACE_in_tupleLiteral4449 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral4457 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_COMMA_in_tupleLiteral4468 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral4472 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_RBRACE_in_tupleLiteral4483 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tupleItem4514 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_set_in_tupleItem4516 = new BitSet(new long[]{0x0000000706088010L,0x000007FFE8200800L});
    public static final BitSet FOLLOW_expression_in_tupleItem4524 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleType_in_type4577 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_type4589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleType_in_type4601 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeOnly4633 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_typeOnly4635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_simpleType4663 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionType4701 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_collectionType4724 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_collectionType4728 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_collectionType4730 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_106_in_tupleType4764 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_tupleType4766 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType4775 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_COMMA_in_tupleType4786 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType4790 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_RPAREN_in_tupleType4802 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tuplePart4834 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_tuplePart4836 = new BitSet(new long[]{0x0000000000000010L,0x00000C3800000000L});
    public static final BitSet FOLLOW_type_in_tuplePart4840 = new BitSet(new long[]{0x0000000000000002L});

}