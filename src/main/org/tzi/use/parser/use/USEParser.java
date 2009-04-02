// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 USE.g 2009-04-02 13:53:40
 
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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "IDENT", "LBRACE", "RBRACE", "SEMI", "LESS", "COLON", "EQUAL", "LBRACK", "RBRACK", "COMMA", "DOTDOT", "INT", "STAR", "COLON_COLON", "COLON_EQUAL", "LPAREN", "RPAREN", "NOT_EQUAL", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "PLUS", "MINUS", "SLASH", "ARROW", "DOT", "AT", "BAR", "REAL", "STRING", "HASH", "NEWLINE", "WS", "SL_COMMENT", "ML_COMMENT", "RANGE_OR_INT", "ESC", "HEX_DIGIT", "VOCAB", "'model'", "'constraints'", "'enum'", "'abstract'", "'class'", "'attributes'", "'operations'", "'end'", "'associationClass'", "'associationclass'", "'between'", "'aggregation'", "'composition'", "'begin'", "'association'", "'role'", "'ordered'", "'context'", "'inv'", "'pre'", "'post'", "'var'", "'declare'", "'set'", "'create'", "'namehint'", "'insert'", "'into'", "'delete'", "'from'", "'destroy'", "'if'", "'then'", "'else'", "'endif'", "'while'", "'do'", "'wend'", "'for'", "'in'", "'execute'", "'let'", "'implies'", "'or'", "'xor'", "'and'", "'div'", "'not'", "'allInstances'", "'iterate'", "'oclAsType'", "'oclIsKindOf'", "'oclIsTypeOf'", "'true'", "'false'", "'Set'", "'Sequence'", "'Bag'", "'oclEmpty'", "'oclUndefined'", "'Undefined'", "'null'", "'Tuple'", "'Collection'"
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
    public static final int T__51=51;
    public static final int COLON_EQUAL=18;
    public static final int SLASH=27;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
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
            	                else if ( (LA2_3==EOF||LA2_3==COLON||LA2_3==44||(LA2_3>=46 && LA2_3<=47)||(LA2_3>=51 && LA2_3<=52)||(LA2_3>=54 && LA2_3<=55)||LA2_3==57||(LA2_3>=60 && LA2_3<=61)) ) {
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

                        if ( (LA12_0==61) ) {
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

                        if ( (LA20_0==61) ) {
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

                if ( ((LA27_0>=62 && LA27_0<=63)) ) {
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
    // USE.g:351:1: invariant returns [ASTConstraintDefinition n] : 'context' (v= IDENT COLON )? t= simpleType (inv= invariantClause )* ;
    public final ASTConstraintDefinition invariant() throws RecognitionException {
        ASTConstraintDefinition n = null;

        Token v=null;
        ASTSimpleType t = null;

        ASTInvariantClause inv = null;


        try {
            // USE.g:352:1: ( 'context' (v= IDENT COLON )? t= simpleType (inv= invariantClause )* )
            // USE.g:353:5: 'context' (v= IDENT COLON )? t= simpleType (inv= invariantClause )*
            {
             n = new ASTConstraintDefinition(); 
            match(input,60,FOLLOW_60_in_invariant1299); 
            // USE.g:355:5: (v= IDENT COLON )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==IDENT) ) {
                int LA36_1 = input.LA(2);

                if ( (LA36_1==COLON) ) {
                    alt36=1;
                }
            }
            switch (alt36) {
                case 1 :
                    // USE.g:355:7: v= IDENT COLON
                    {
                    v=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariant1309); 
                    match(input,COLON,FOLLOW_COLON_in_invariant1311); 
                     n.setVarName(v); 

                    }
                    break;

            }

            pushFollow(FOLLOW_simpleType_in_invariant1326);
            t=simpleType();

            state._fsp--;

             n.setType(t); 
            // USE.g:358:5: (inv= invariantClause )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==61) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // USE.g:358:7: inv= invariantClause
            	    {
            	    pushFollow(FOLLOW_invariantClause_in_invariant1338);
            	    inv=invariantClause();

            	    state._fsp--;

            	     n.addInvariantClause(inv); 

            	    }
            	    break;

            	default :
            	    break loop37;
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
    // USE.g:366:1: invariantClause returns [ASTInvariantClause n] : 'inv' (name= IDENT )? COLON e= expression ;
    public final ASTInvariantClause invariantClause() throws RecognitionException {
        ASTInvariantClause n = null;

        Token name=null;
        ASTExpression e = null;


        try {
            // USE.g:367:1: ( 'inv' (name= IDENT )? COLON e= expression )
            // USE.g:368:5: 'inv' (name= IDENT )? COLON e= expression
            {
            match(input,61,FOLLOW_61_in_invariantClause1368); 
            // USE.g:368:11: (name= IDENT )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==IDENT) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // USE.g:368:13: name= IDENT
                    {
                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantClause1374); 

                    }
                    break;

            }

            match(input,COLON,FOLLOW_COLON_in_invariantClause1379); 
            pushFollow(FOLLOW_expression_in_invariantClause1383);
            e=expression();

            state._fsp--;

             n = new ASTInvariantClause(name, e); 

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
    // USE.g:380:1: prePost returns [ASTPrePost n] : 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ ;
    public final ASTPrePost prePost() throws RecognitionException {
        ASTPrePost n = null;

        Token classname=null;
        Token opname=null;
        List pl = null;

        ASTType rt = null;

        ASTPrePostClause ppc = null;


        try {
            // USE.g:381:1: ( 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ )
            // USE.g:382:5: 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+
            {
            match(input,60,FOLLOW_60_in_prePost1413); 
            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost1417); 
            match(input,COLON_COLON,FOLLOW_COLON_COLON_in_prePost1419); 
            opname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost1423); 
            pushFollow(FOLLOW_paramList_in_prePost1427);
            pl=paramList();

            state._fsp--;

            // USE.g:382:69: ( COLON rt= type )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==COLON) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // USE.g:382:71: COLON rt= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_prePost1431); 
                    pushFollow(FOLLOW_type_in_prePost1435);
                    rt=type();

                    state._fsp--;


                    }
                    break;

            }

             n = new ASTPrePost(classname, opname, pl, rt); 
            // USE.g:384:5: (ppc= prePostClause )+
            int cnt40=0;
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( ((LA40_0>=62 && LA40_0<=63)) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // USE.g:384:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_prePost1454);
            	    ppc=prePostClause();

            	    state._fsp--;

            	     n.addPrePostClause(ppc); 

            	    }
            	    break;

            	default :
            	    if ( cnt40 >= 1 ) break loop40;
                        EarlyExitException eee =
                            new EarlyExitException(40, input);
                        throw eee;
                }
                cnt40++;
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
    // USE.g:391:1: prePostClause returns [ASTPrePostClause n] : ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression ;
    public final ASTPrePostClause prePostClause() throws RecognitionException {
        ASTPrePostClause n = null;

        Token name=null;
        ASTExpression e = null;


         Token t = null; 
        try {
            // USE.g:393:1: ( ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression )
            // USE.g:394:5: ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression
            {
             t = input.LT(1); 
            if ( (input.LA(1)>=62 && input.LA(1)<=63) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // USE.g:395:25: (name= IDENT )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==IDENT) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // USE.g:395:27: name= IDENT
                    {
                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePostClause1508); 

                    }
                    break;

            }

            match(input,COLON,FOLLOW_COLON_in_prePostClause1513); 
            pushFollow(FOLLOW_expression_in_prePostClause1517);
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
    // USE.g:399:1: alActionList returns [ASTALActionList al] : (action= alAction )* ;
    public final ASTALActionList alActionList() throws RecognitionException {
        ASTALActionList al = null;

        ASTALAction action = null;



        	al = new ASTALActionList();

        try {
            // USE.g:403:1: ( (action= alAction )* )
            // USE.g:404:2: (action= alAction )*
            {
            // USE.g:404:2: (action= alAction )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( ((LA42_0>=64 && LA42_0<=67)||LA42_0==69||LA42_0==71||(LA42_0>=73 && LA42_0<=74)||LA42_0==78||LA42_0==81||LA42_0==83) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // USE.g:404:4: action= alAction
            	    {
            	    pushFollow(FOLLOW_alAction_in_alActionList1550);
            	    action=alAction();

            	    state._fsp--;

            	    al.add(action);

            	    }
            	    break;

            	default :
            	    break loop42;
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
    // USE.g:407:1: alAction returns [ASTALAction action] : (aCV= alCreateVar | aDl= alDelete | aSe= alSet | aSC= alSetCreate | aIn= alInsert | aDe= alDestroy | aIf= alIf | aWh= alWhile | aFo= alFor | aEx= alExec );
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
            // USE.g:408:1: (aCV= alCreateVar | aDl= alDelete | aSe= alSet | aSC= alSetCreate | aIn= alInsert | aDe= alDestroy | aIf= alIf | aWh= alWhile | aFo= alFor | aEx= alExec )
            int alt43=10;
            switch ( input.LA(1) ) {
            case 64:
            case 65:
                {
                alt43=1;
                }
                break;
            case 71:
                {
                alt43=2;
                }
                break;
            case 66:
                {
                alt43=3;
                }
                break;
            case 67:
                {
                alt43=4;
                }
                break;
            case 69:
                {
                alt43=5;
                }
                break;
            case 73:
                {
                alt43=6;
                }
                break;
            case 74:
                {
                alt43=7;
                }
                break;
            case 78:
                {
                alt43=8;
                }
                break;
            case 81:
                {
                alt43=9;
                }
                break;
            case 83:
                {
                alt43=10;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 43, 0, input);

                throw nvae;
            }

            switch (alt43) {
                case 1 :
                    // USE.g:409:5: aCV= alCreateVar
                    {
                    pushFollow(FOLLOW_alCreateVar_in_alAction1576);
                    aCV=alCreateVar();

                    state._fsp--;

                     action = aCV; 

                    }
                    break;
                case 2 :
                    // USE.g:410:5: aDl= alDelete
                    {
                    pushFollow(FOLLOW_alDelete_in_alAction1588);
                    aDl=alDelete();

                    state._fsp--;

                     action = aDl; 

                    }
                    break;
                case 3 :
                    // USE.g:411:5: aSe= alSet
                    {
                    pushFollow(FOLLOW_alSet_in_alAction1600);
                    aSe=alSet();

                    state._fsp--;

                     action = aSe; 

                    }
                    break;
                case 4 :
                    // USE.g:412:5: aSC= alSetCreate
                    {
                    pushFollow(FOLLOW_alSetCreate_in_alAction1612);
                    aSC=alSetCreate();

                    state._fsp--;

                     action = aSC; 

                    }
                    break;
                case 5 :
                    // USE.g:413:5: aIn= alInsert
                    {
                    pushFollow(FOLLOW_alInsert_in_alAction1624);
                    aIn=alInsert();

                    state._fsp--;

                     action = aIn; 

                    }
                    break;
                case 6 :
                    // USE.g:414:5: aDe= alDestroy
                    {
                    pushFollow(FOLLOW_alDestroy_in_alAction1636);
                    aDe=alDestroy();

                    state._fsp--;

                     action = aDe; 

                    }
                    break;
                case 7 :
                    // USE.g:415:5: aIf= alIf
                    {
                    pushFollow(FOLLOW_alIf_in_alAction1648);
                    aIf=alIf();

                    state._fsp--;

                     action = aIf; 

                    }
                    break;
                case 8 :
                    // USE.g:416:5: aWh= alWhile
                    {
                    pushFollow(FOLLOW_alWhile_in_alAction1660);
                    aWh=alWhile();

                    state._fsp--;

                     action = aWh; 

                    }
                    break;
                case 9 :
                    // USE.g:417:5: aFo= alFor
                    {
                    pushFollow(FOLLOW_alFor_in_alAction1672);
                    aFo=alFor();

                    state._fsp--;

                     action = aFo; 

                    }
                    break;
                case 10 :
                    // USE.g:418:5: aEx= alExec
                    {
                    pushFollow(FOLLOW_alExec_in_alAction1684);
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
    // USE.g:423:1: alCreateVar returns [ASTALCreateVar var] : ( 'var' | 'declare' ) name= IDENT COLON t= type ;
    public final ASTALCreateVar alCreateVar() throws RecognitionException {
        ASTALCreateVar var = null;

        Token name=null;
        ASTType t = null;


        try {
            // USE.g:424:1: ( ( 'var' | 'declare' ) name= IDENT COLON t= type )
            // USE.g:425:2: ( 'var' | 'declare' ) name= IDENT COLON t= type
            {
            if ( (input.LA(1)>=64 && input.LA(1)<=65) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_alCreateVar1711); 
            match(input,COLON,FOLLOW_COLON_in_alCreateVar1713); 
            pushFollow(FOLLOW_type_in_alCreateVar1717);
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
    // USE.g:429:1: alSet returns [ASTALSet set] : 'set' lval= expression COLON_EQUAL rval= expression ;
    public final ASTALSet alSet() throws RecognitionException {
        ASTALSet set = null;

        ASTExpression lval = null;

        ASTExpression rval = null;


        try {
            // USE.g:430:1: ( 'set' lval= expression COLON_EQUAL rval= expression )
            // USE.g:432:5: 'set' lval= expression COLON_EQUAL rval= expression
            {
            match(input,66,FOLLOW_66_in_alSet1739); 
            pushFollow(FOLLOW_expression_in_alSet1743);
            lval=expression();

            state._fsp--;

            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_alSet1745); 
            pushFollow(FOLLOW_expression_in_alSet1749);
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
    // USE.g:436:1: alSetCreate returns [ASTALSetCreate setcreate] : 'create' lval= expression COLON_EQUAL {...}?new_= IDENT cls= IDENT ( 'namehint' nameExpr= expression )? ;
    public final ASTALSetCreate alSetCreate() throws RecognitionException {
        ASTALSetCreate setcreate = null;

        Token new_=null;
        Token cls=null;
        ASTExpression lval = null;

        ASTExpression nameExpr = null;


        try {
            // USE.g:437:1: ( 'create' lval= expression COLON_EQUAL {...}?new_= IDENT cls= IDENT ( 'namehint' nameExpr= expression )? )
            // USE.g:438:5: 'create' lval= expression COLON_EQUAL {...}?new_= IDENT cls= IDENT ( 'namehint' nameExpr= expression )?
            {
            match(input,67,FOLLOW_67_in_alSetCreate1773); 
            pushFollow(FOLLOW_expression_in_alSetCreate1777);
            lval=expression();

            state._fsp--;

            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_alSetCreate1779); 
            if ( !(( input.LT(1).getText().equals("new") )) ) {
                throw new FailedPredicateException(input, "alSetCreate", " input.LT(1).getText().equals(\"new\") ");
            }
            new_=(Token)match(input,IDENT,FOLLOW_IDENT_in_alSetCreate1785); 
            cls=(Token)match(input,IDENT,FOLLOW_IDENT_in_alSetCreate1789); 
            // USE.g:439:5: ( 'namehint' nameExpr= expression )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==68) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // USE.g:439:7: 'namehint' nameExpr= expression
                    {
                    match(input,68,FOLLOW_68_in_alSetCreate1798); 
                    pushFollow(FOLLOW_expression_in_alSetCreate1802);
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
    // USE.g:444:1: alInsert returns [ASTALInsert insert] : 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT ;
    public final ASTALInsert alInsert() throws RecognitionException {
        ASTALInsert insert = null;

        Token id=null;
        ASTExpression e = null;


        List exprList = new ArrayList(); 
        try {
            // USE.g:446:1: ( 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT )
            // USE.g:447:5: 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT
            {
            match(input,69,FOLLOW_69_in_alInsert1833); 
            match(input,LPAREN,FOLLOW_LPAREN_in_alInsert1835); 
            pushFollow(FOLLOW_expression_in_alInsert1844);
            e=expression();

            state._fsp--;

             exprList.add(e); 
            match(input,COMMA,FOLLOW_COMMA_in_alInsert1848); 
            pushFollow(FOLLOW_expression_in_alInsert1856);
            e=expression();

            state._fsp--;

             exprList.add(e); 
            // USE.g:449:42: ( COMMA e= expression )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==COMMA) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // USE.g:449:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_alInsert1862); 
            	    pushFollow(FOLLOW_expression_in_alInsert1866);
            	    e=expression();

            	    state._fsp--;

            	     exprList.add(e); 

            	    }
            	    break;

            	default :
            	    break loop45;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_alInsert1878); 
            match(input,70,FOLLOW_70_in_alInsert1880); 
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_alInsert1884); 
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
    // USE.g:455:1: alDelete returns [ASTALDelete n] : 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT ;
    public final ASTALDelete alDelete() throws RecognitionException {
        ASTALDelete n = null;

        Token id=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // USE.g:457:1: ( 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT )
            // USE.g:458:5: 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT
            {
            match(input,71,FOLLOW_71_in_alDelete1916); 
            match(input,LPAREN,FOLLOW_LPAREN_in_alDelete1918); 
            pushFollow(FOLLOW_expression_in_alDelete1926);
            e=expression();

            state._fsp--;

             exprList.add(e); 
            match(input,COMMA,FOLLOW_COMMA_in_alDelete1930); 
            pushFollow(FOLLOW_expression_in_alDelete1938);
            e=expression();

            state._fsp--;

             exprList.add(e); 
            // USE.g:460:42: ( COMMA e= expression )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==COMMA) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // USE.g:460:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_alDelete1944); 
            	    pushFollow(FOLLOW_expression_in_alDelete1948);
            	    e=expression();

            	    state._fsp--;

            	     exprList.add(e); 

            	    }
            	    break;

            	default :
            	    break loop46;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_alDelete1959); 
            match(input,72,FOLLOW_72_in_alDelete1961); 
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_alDelete1965); 
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
    // USE.g:466:1: alDestroy returns [ASTALDestroy n] : 'destroy' e= expression ;
    public final ASTALDestroy alDestroy() throws RecognitionException {
        ASTALDestroy n = null;

        ASTExpression e = null;


        try {
            // USE.g:467:1: ( 'destroy' e= expression )
            // USE.g:468:6: 'destroy' e= expression
            {
            match(input,73,FOLLOW_73_in_alDestroy1994); 
            pushFollow(FOLLOW_expression_in_alDestroy1998);
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
    // USE.g:472:1: alIf returns [ASTALIf i] : 'if' ifexpr= expression 'then' thenlist= alActionList ( 'else' elselist= alActionList )? 'endif' ;
    public final ASTALIf alIf() throws RecognitionException {
        ASTALIf i = null;

        ASTExpression ifexpr = null;

        ASTALActionList thenlist = null;

        ASTALActionList elselist = null;


        try {
            // USE.g:473:1: ( 'if' ifexpr= expression 'then' thenlist= alActionList ( 'else' elselist= alActionList )? 'endif' )
            // USE.g:474:2: 'if' ifexpr= expression 'then' thenlist= alActionList ( 'else' elselist= alActionList )? 'endif'
            {
            match(input,74,FOLLOW_74_in_alIf2022); 
            pushFollow(FOLLOW_expression_in_alIf2026);
            ifexpr=expression();

            state._fsp--;

            match(input,75,FOLLOW_75_in_alIf2030); 
            pushFollow(FOLLOW_alActionList_in_alIf2034);
            thenlist=alActionList();

            state._fsp--;

            // USE.g:476:2: ( 'else' elselist= alActionList )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==76) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // USE.g:476:3: 'else' elselist= alActionList
                    {
                    match(input,76,FOLLOW_76_in_alIf2038); 
                    pushFollow(FOLLOW_alActionList_in_alIf2042);
                    elselist=alActionList();

                    state._fsp--;


                    }
                    break;

            }

            match(input,77,FOLLOW_77_in_alIf2047); 
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
    // USE.g:481:1: alWhile returns [ASTALWhile w] : 'while' expr= expression 'do' body= alActionList 'wend' ;
    public final ASTALWhile alWhile() throws RecognitionException {
        ASTALWhile w = null;

        ASTExpression expr = null;

        ASTALActionList body = null;


        try {
            // USE.g:482:1: ( 'while' expr= expression 'do' body= alActionList 'wend' )
            // USE.g:483:2: 'while' expr= expression 'do' body= alActionList 'wend'
            {
            match(input,78,FOLLOW_78_in_alWhile2066); 
            pushFollow(FOLLOW_expression_in_alWhile2070);
            expr=expression();

            state._fsp--;

            match(input,79,FOLLOW_79_in_alWhile2074); 
            pushFollow(FOLLOW_alActionList_in_alWhile2080);
            body=alActionList();

            state._fsp--;

            match(input,80,FOLLOW_80_in_alWhile2083); 
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
    // USE.g:491:1: alFor returns [ASTALFor f] : 'for' var= IDENT COLON t= type 'in' expr= expression 'do' body= alActionList {...}?next= IDENT ;
    public final ASTALFor alFor() throws RecognitionException {
        ASTALFor f = null;

        Token var=null;
        Token next=null;
        ASTType t = null;

        ASTExpression expr = null;

        ASTALActionList body = null;


        try {
            // USE.g:492:1: ( 'for' var= IDENT COLON t= type 'in' expr= expression 'do' body= alActionList {...}?next= IDENT )
            // USE.g:493:2: 'for' var= IDENT COLON t= type 'in' expr= expression 'do' body= alActionList {...}?next= IDENT
            {
            match(input,81,FOLLOW_81_in_alFor2102); 
            var=(Token)match(input,IDENT,FOLLOW_IDENT_in_alFor2106); 
            match(input,COLON,FOLLOW_COLON_in_alFor2108); 
            pushFollow(FOLLOW_type_in_alFor2112);
            t=type();

            state._fsp--;

            match(input,82,FOLLOW_82_in_alFor2114); 
            pushFollow(FOLLOW_expression_in_alFor2118);
            expr=expression();

            state._fsp--;

            match(input,79,FOLLOW_79_in_alFor2122); 
            pushFollow(FOLLOW_alActionList_in_alFor2128);
            body=alActionList();

            state._fsp--;

            if ( !(( input.LT(1).getText().equals("next") )) ) {
                throw new FailedPredicateException(input, "alFor", " input.LT(1).getText().equals(\"next\") ");
            }
            next=(Token)match(input,IDENT,FOLLOW_IDENT_in_alFor2135); 
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
    // USE.g:500:1: alExec returns [ASTALExecute c] : 'execute' op= expression ;
    public final ASTALExecute alExec() throws RecognitionException {
        ASTALExecute c = null;

        ASTExpression op = null;


        try {
            // USE.g:501:1: ( 'execute' op= expression )
            // USE.g:502:5: 'execute' op= expression
            {
            match(input,83,FOLLOW_83_in_alExec2155); 
            pushFollow(FOLLOW_expression_in_alExec2159);
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
    // USE.g:534:1: expressionOnly returns [ASTExpression n] : nExp= expression EOF ;
    public final ASTExpression expressionOnly() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExp = null;


        try {
            // USE.g:535:1: (nExp= expression EOF )
            // USE.g:536:5: nExp= expression EOF
            {
            pushFollow(FOLLOW_expression_in_expressionOnly2194);
            nExp=expression();

            state._fsp--;

            match(input,EOF,FOLLOW_EOF_in_expressionOnly2196); 
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
    // USE.g:543:1: expression returns [ASTExpression n] : ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression ;
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
            // USE.g:549:1: ( ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression )
            // USE.g:550:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression
            {
             tok = input.LT(1); /* remember start of expression */ 
            // USE.g:551:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==84) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // USE.g:552:7: 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in'
            	    {
            	    match(input,84,FOLLOW_84_in_expression2244); 
            	    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_expression2248); 
            	    // USE.g:552:24: ( COLON t= type )?
            	    int alt48=2;
            	    int LA48_0 = input.LA(1);

            	    if ( (LA48_0==COLON) ) {
            	        alt48=1;
            	    }
            	    switch (alt48) {
            	        case 1 :
            	            // USE.g:552:26: COLON t= type
            	            {
            	            match(input,COLON,FOLLOW_COLON_in_expression2252); 
            	            pushFollow(FOLLOW_type_in_expression2256);
            	            t=type();

            	            state._fsp--;


            	            }
            	            break;

            	    }

            	    match(input,EQUAL,FOLLOW_EQUAL_in_expression2261); 
            	    pushFollow(FOLLOW_expression_in_expression2265);
            	    e1=expression();

            	    state._fsp--;

            	    match(input,82,FOLLOW_82_in_expression2267); 
            	     ASTLetExpression nextLet = new ASTLetExpression(name, t, e1);
            	             if ( firstLet == null ) 
            	                 firstLet = nextLet;
            	             if ( prevLet != null ) 
            	                 prevLet.setInExpr(nextLet);
            	             prevLet = nextLet;
            	           

            	    }
            	    break;

            	default :
            	    break loop49;
                }
            } while (true);

            pushFollow(FOLLOW_conditionalImpliesExpression_in_expression2292);
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
    // USE.g:580:1: paramList returns [List paramList] : LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN ;
    public final List paramList() throws RecognitionException {
        List paramList = null;

        ASTVariableDeclaration v = null;


         paramList = new ArrayList(); 
        try {
            // USE.g:582:1: ( LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN )
            // USE.g:583:5: LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN
            {
            match(input,LPAREN,FOLLOW_LPAREN_in_paramList2325); 
            // USE.g:584:5: (v= variableDeclaration ( COMMA v= variableDeclaration )* )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==IDENT) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // USE.g:585:7: v= variableDeclaration ( COMMA v= variableDeclaration )*
                    {
                    pushFollow(FOLLOW_variableDeclaration_in_paramList2342);
                    v=variableDeclaration();

                    state._fsp--;

                     paramList.add(v); 
                    // USE.g:586:7: ( COMMA v= variableDeclaration )*
                    loop50:
                    do {
                        int alt50=2;
                        int LA50_0 = input.LA(1);

                        if ( (LA50_0==COMMA) ) {
                            alt50=1;
                        }


                        switch (alt50) {
                    	case 1 :
                    	    // USE.g:586:9: COMMA v= variableDeclaration
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_paramList2354); 
                    	    pushFollow(FOLLOW_variableDeclaration_in_paramList2358);
                    	    v=variableDeclaration();

                    	    state._fsp--;

                    	     paramList.add(v); 

                    	    }
                    	    break;

                    	default :
                    	    break loop50;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_paramList2378); 

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
    // USE.g:594:1: idList returns [List idList] : id0= IDENT ( COMMA idn= IDENT )* ;
    public final List idList() throws RecognitionException {
        List idList = null;

        Token id0=null;
        Token idn=null;

         idList = new ArrayList(); 
        try {
            // USE.g:596:1: (id0= IDENT ( COMMA idn= IDENT )* )
            // USE.g:597:5: id0= IDENT ( COMMA idn= IDENT )*
            {
            id0=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList2407); 
             idList.add(id0); 
            // USE.g:598:5: ( COMMA idn= IDENT )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==COMMA) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // USE.g:598:7: COMMA idn= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_idList2417); 
            	    idn=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList2421); 
            	     idList.add(idn); 

            	    }
            	    break;

            	default :
            	    break loop52;
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
    // USE.g:606:1: variableDeclaration returns [ASTVariableDeclaration n] : name= IDENT COLON t= type ;
    public final ASTVariableDeclaration variableDeclaration() throws RecognitionException {
        ASTVariableDeclaration n = null;

        Token name=null;
        ASTType t = null;


        try {
            // USE.g:607:1: (name= IDENT COLON t= type )
            // USE.g:608:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableDeclaration2452); 
            match(input,COLON,FOLLOW_COLON_in_variableDeclaration2454); 
            pushFollow(FOLLOW_type_in_variableDeclaration2458);
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
    // USE.g:616:1: conditionalImpliesExpression returns [ASTExpression n] : nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* ;
    public final ASTExpression conditionalImpliesExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndOrExp = null;

        ASTExpression n1 = null;


        try {
            // USE.g:617:1: (nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* )
            // USE.g:618:5: nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )*
            {
            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2494);
            nCndOrExp=conditionalOrExpression();

            state._fsp--;

            n = nCndOrExp;
            // USE.g:619:5: (op= 'implies' n1= conditionalOrExpression )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==85) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // USE.g:619:7: op= 'implies' n1= conditionalOrExpression
            	    {
            	    op=(Token)match(input,85,FOLLOW_85_in_conditionalImpliesExpression2507); 
            	    pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2511);
            	    n1=conditionalOrExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

            	    }
            	    break;

            	default :
            	    break loop53;
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
    // USE.g:628:1: conditionalOrExpression returns [ASTExpression n] : nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* ;
    public final ASTExpression conditionalOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndXorExp = null;

        ASTExpression n1 = null;


        try {
            // USE.g:629:1: (nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* )
            // USE.g:630:5: nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )*
            {
            pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2556);
            nCndXorExp=conditionalXOrExpression();

            state._fsp--;

            n = nCndXorExp;
            // USE.g:631:5: (op= 'or' n1= conditionalXOrExpression )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==86) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // USE.g:631:7: op= 'or' n1= conditionalXOrExpression
            	    {
            	    op=(Token)match(input,86,FOLLOW_86_in_conditionalOrExpression2569); 
            	    pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2573);
            	    n1=conditionalXOrExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

            	    }
            	    break;

            	default :
            	    break loop54;
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
    // USE.g:640:1: conditionalXOrExpression returns [ASTExpression n] : nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* ;
    public final ASTExpression conditionalXOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndAndExp = null;

        ASTExpression n1 = null;


        try {
            // USE.g:641:1: (nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* )
            // USE.g:642:5: nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )*
            {
            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2617);
            nCndAndExp=conditionalAndExpression();

            state._fsp--;

            n = nCndAndExp;
            // USE.g:643:5: (op= 'xor' n1= conditionalAndExpression )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==87) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // USE.g:643:7: op= 'xor' n1= conditionalAndExpression
            	    {
            	    op=(Token)match(input,87,FOLLOW_87_in_conditionalXOrExpression2630); 
            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2634);
            	    n1=conditionalAndExpression();

            	    state._fsp--;

            	     n = new ASTBinaryExpression(op, n, n1); 

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
        return n;
    }
    // $ANTLR end "conditionalXOrExpression"


    // $ANTLR start "conditionalAndExpression"
    // USE.g:652:1: conditionalAndExpression returns [ASTExpression n] : nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* ;
    public final ASTExpression conditionalAndExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nEqExp = null;

        ASTExpression n1 = null;


        try {
            // USE.g:653:1: (nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* )
            // USE.g:654:5: nEqExp= equalityExpression (op= 'and' n1= equalityExpression )*
            {
            pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression2678);
            nEqExp=equalityExpression();

            state._fsp--;

            n = nEqExp;
            // USE.g:655:5: (op= 'and' n1= equalityExpression )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==88) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // USE.g:655:7: op= 'and' n1= equalityExpression
            	    {
            	    op=(Token)match(input,88,FOLLOW_88_in_conditionalAndExpression2691); 
            	    pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression2695);
            	    n1=equalityExpression();

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
    // $ANTLR end "conditionalAndExpression"


    // $ANTLR start "equalityExpression"
    // USE.g:664:1: equalityExpression returns [ASTExpression n] : nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* ;
    public final ASTExpression equalityExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nRelExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // USE.g:666:1: (nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* )
            // USE.g:667:5: nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            {
            pushFollow(FOLLOW_relationalExpression_in_equalityExpression2743);
            nRelExp=relationalExpression();

            state._fsp--;

            n = nRelExp;
            // USE.g:668:5: ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==EQUAL||LA57_0==NOT_EQUAL) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // USE.g:668:7: ( EQUAL | NOT_EQUAL ) n1= relationalExpression
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

            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression2772);
            	    n1=relationalExpression();

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
    // $ANTLR end "equalityExpression"


    // $ANTLR start "relationalExpression"
    // USE.g:678:1: relationalExpression returns [ASTExpression n] : nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* ;
    public final ASTExpression relationalExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nAddiExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // USE.g:680:1: (nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* )
            // USE.g:681:5: nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            {
            pushFollow(FOLLOW_additiveExpression_in_relationalExpression2821);
            nAddiExp=additiveExpression();

            state._fsp--;

            n = nAddiExp;
            // USE.g:682:5: ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==LESS||(LA58_0>=GREATER && LA58_0<=GREATER_EQUAL)) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // USE.g:682:7: ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression
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

            	    pushFollow(FOLLOW_additiveExpression_in_relationalExpression2857);
            	    n1=additiveExpression();

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
    // $ANTLR end "relationalExpression"


    // $ANTLR start "additiveExpression"
    // USE.g:692:1: additiveExpression returns [ASTExpression n] : nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* ;
    public final ASTExpression additiveExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nMulExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // USE.g:694:1: (nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* )
            // USE.g:695:5: nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            {
            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression2907);
            nMulExp=multiplicativeExpression();

            state._fsp--;

            n = nMulExp;
            // USE.g:696:5: ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( ((LA59_0>=PLUS && LA59_0<=MINUS)) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // USE.g:696:7: ( PLUS | MINUS ) n1= multiplicativeExpression
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

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression2935);
            	    n1=multiplicativeExpression();

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
    // $ANTLR end "additiveExpression"


    // $ANTLR start "multiplicativeExpression"
    // USE.g:707:1: multiplicativeExpression returns [ASTExpression n] : nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* ;
    public final ASTExpression multiplicativeExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // USE.g:709:1: (nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* )
            // USE.g:710:5: nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            {
            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression2985);
            nUnExp=unaryExpression();

            state._fsp--;

             n = nUnExp;
            // USE.g:711:5: ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==STAR||LA60_0==SLASH||LA60_0==89) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // USE.g:711:7: ( STAR | SLASH | 'div' ) n1= unaryExpression
            	    {
            	     op = input.LT(1); 
            	    if ( input.LA(1)==STAR||input.LA(1)==SLASH||input.LA(1)==89 ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression3017);
            	    n1=unaryExpression();

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
    // $ANTLR end "multiplicativeExpression"


    // $ANTLR start "unaryExpression"
    // USE.g:723:1: unaryExpression returns [ASTExpression n] : ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression );
    public final ASTExpression unaryExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression nPosExp = null;


         Token op = null; 
        try {
            // USE.g:725:1: ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression )
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( ((LA61_0>=PLUS && LA61_0<=MINUS)||LA61_0==90) ) {
                alt61=1;
            }
            else if ( (LA61_0==IDENT||LA61_0==INT||LA61_0==LPAREN||(LA61_0>=REAL && LA61_0<=HASH)||LA61_0==74||(LA61_0>=92 && LA61_0<=105)) ) {
                alt61=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 61, 0, input);

                throw nvae;
            }
            switch (alt61) {
                case 1 :
                    // USE.g:726:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    {
                    // USE.g:726:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    // USE.g:726:9: ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression
                    {
                     op = input.LT(1); 
                    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS)||input.LA(1)==90 ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression3103);
                    nUnExp=unaryExpression();

                    state._fsp--;

                     n = new ASTUnaryExpression(op, nUnExp); 

                    }


                    }
                    break;
                case 2 :
                    // USE.g:730:7: nPosExp= postfixExpression
                    {
                    pushFollow(FOLLOW_postfixExpression_in_unaryExpression3123);
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
    // USE.g:738:1: postfixExpression returns [ASTExpression n] : nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* ;
    public final ASTExpression postfixExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nPrimExp = null;

        ASTExpression nPc = null;


         boolean arrow = false; 
        try {
            // USE.g:740:1: (nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* )
            // USE.g:741:5: nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            {
            pushFollow(FOLLOW_primaryExpression_in_postfixExpression3156);
            nPrimExp=primaryExpression();

            state._fsp--;

             n = nPrimExp; 
            // USE.g:742:5: ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( ((LA63_0>=ARROW && LA63_0<=DOT)) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // USE.g:743:6: ( ARROW | DOT ) nPc= propertyCall[$n, arrow]
            	    {
            	    // USE.g:743:6: ( ARROW | DOT )
            	    int alt62=2;
            	    int LA62_0 = input.LA(1);

            	    if ( (LA62_0==ARROW) ) {
            	        alt62=1;
            	    }
            	    else if ( (LA62_0==DOT) ) {
            	        alt62=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 62, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt62) {
            	        case 1 :
            	            // USE.g:743:8: ARROW
            	            {
            	            match(input,ARROW,FOLLOW_ARROW_in_postfixExpression3174); 
            	             arrow = true; 

            	            }
            	            break;
            	        case 2 :
            	            // USE.g:743:34: DOT
            	            {
            	            match(input,DOT,FOLLOW_DOT_in_postfixExpression3180); 
            	             arrow = false; 

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_propertyCall_in_postfixExpression3191);
            	    nPc=propertyCall(n, arrow);

            	    state._fsp--;

            	     n = nPc; 

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
    // $ANTLR end "postfixExpression"


    // $ANTLR start "primaryExpression"
    // USE.g:759:1: primaryExpression returns [ASTExpression n] : (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? );
    public final ASTExpression primaryExpression() throws RecognitionException {
        ASTExpression n = null;

        Token id1=null;
        ASTExpression nLit = null;

        ASTExpression nPc = null;

        ASTExpression nExp = null;

        ASTExpression nIfExp = null;


        try {
            // USE.g:760:1: (nLit= literal | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? )
            int alt66=5;
            switch ( input.LA(1) ) {
            case INT:
            case REAL:
            case STRING:
            case HASH:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
                {
                alt66=1;
                }
                break;
            case IDENT:
                {
                int LA66_2 = input.LA(2);

                if ( (LA66_2==EOF||LA66_2==IDENT||(LA66_2>=RBRACE && LA66_2<=LESS)||(LA66_2>=EQUAL && LA66_2<=LBRACK)||(LA66_2>=COMMA && LA66_2<=DOTDOT)||LA66_2==STAR||(LA66_2>=COLON_EQUAL && LA66_2<=ARROW)||(LA66_2>=AT && LA66_2<=BAR)||LA66_2==44||(LA66_2>=46 && LA66_2<=47)||(LA66_2>=50 && LA66_2<=52)||(LA66_2>=54 && LA66_2<=57)||(LA66_2>=60 && LA66_2<=67)||LA66_2==69||LA66_2==71||(LA66_2>=73 && LA66_2<=83)||(LA66_2>=85 && LA66_2<=89)) ) {
                    alt66=2;
                }
                else if ( (LA66_2==DOT) ) {
                    int LA66_6 = input.LA(3);

                    if ( (LA66_6==91) ) {
                        alt66=5;
                    }
                    else if ( (LA66_6==IDENT||(LA66_6>=92 && LA66_6<=95)) ) {
                        alt66=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 66, 6, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 66, 2, input);

                    throw nvae;
                }
                }
                break;
            case 92:
            case 93:
            case 94:
            case 95:
                {
                alt66=2;
                }
                break;
            case LPAREN:
                {
                alt66=3;
                }
                break;
            case 74:
                {
                alt66=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 66, 0, input);

                throw nvae;
            }

            switch (alt66) {
                case 1 :
                    // USE.g:761:7: nLit= literal
                    {
                    pushFollow(FOLLOW_literal_in_primaryExpression3231);
                    nLit=literal();

                    state._fsp--;

                     n = nLit; 

                    }
                    break;
                case 2 :
                    // USE.g:762:7: nPc= propertyCall[null, false]
                    {
                    pushFollow(FOLLOW_propertyCall_in_primaryExpression3243);
                    nPc=propertyCall(null, false);

                    state._fsp--;

                     n = nPc; 

                    }
                    break;
                case 3 :
                    // USE.g:763:7: LPAREN nExp= expression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression3254); 
                    pushFollow(FOLLOW_expression_in_primaryExpression3258);
                    nExp=expression();

                    state._fsp--;

                    match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression3260); 
                     n = nExp; 

                    }
                    break;
                case 4 :
                    // USE.g:764:7: nIfExp= ifExpression
                    {
                    pushFollow(FOLLOW_ifExpression_in_primaryExpression3272);
                    nIfExp=ifExpression();

                    state._fsp--;

                     n = nIfExp; 

                    }
                    break;
                case 5 :
                    // USE.g:766:7: id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )?
                    {
                    id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_primaryExpression3289); 
                    match(input,DOT,FOLLOW_DOT_in_primaryExpression3291); 
                    match(input,91,FOLLOW_91_in_primaryExpression3293); 
                    // USE.g:766:36: ( LPAREN RPAREN )?
                    int alt64=2;
                    int LA64_0 = input.LA(1);

                    if ( (LA64_0==LPAREN) ) {
                        alt64=1;
                    }
                    switch (alt64) {
                        case 1 :
                            // USE.g:766:38: LPAREN RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression3297); 
                            match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression3299); 

                            }
                            break;

                    }

                     n = new ASTAllInstancesExpression(id1); 
                    // USE.g:768:7: ( AT 'pre' )?
                    int alt65=2;
                    int LA65_0 = input.LA(1);

                    if ( (LA65_0==AT) ) {
                        alt65=1;
                    }
                    switch (alt65) {
                        case 1 :
                            // USE.g:768:9: AT 'pre'
                            {
                            match(input,AT,FOLLOW_AT_in_primaryExpression3320); 
                            match(input,62,FOLLOW_62_in_primaryExpression3322); 
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
    // USE.g:781:1: propertyCall[ASTExpression source, boolean followsArrow] returns [ASTExpression n] : ({...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] );
    public final ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExpQuery = null;

        ASTExpression nExpIterate = null;

        ASTOperationExpression nExpOperation = null;

        ASTTypeArgExpression nExpType = null;


        try {
            // USE.g:782:1: ({...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] )
            int alt67=4;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA67_1 = input.LA(2);

                if ( (( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                    alt67=1;
                }
                else if ( (true) ) {
                    alt67=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 67, 1, input);

                    throw nvae;
                }
                }
                break;
            case 92:
                {
                alt67=2;
                }
                break;
            case 93:
            case 94:
            case 95:
                {
                alt67=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 67, 0, input);

                throw nvae;
            }

            switch (alt67) {
                case 1 :
                    // USE.g:786:7: {...}?nExpQuery= queryExpression[source]
                    {
                    if ( !(( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                        throw new FailedPredicateException(input, "propertyCall", " org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) ");
                    }
                    pushFollow(FOLLOW_queryExpression_in_propertyCall3388);
                    nExpQuery=queryExpression(source);

                    state._fsp--;

                     n = nExpQuery; 

                    }
                    break;
                case 2 :
                    // USE.g:788:7: nExpIterate= iterateExpression[source]
                    {
                    pushFollow(FOLLOW_iterateExpression_in_propertyCall3401);
                    nExpIterate=iterateExpression(source);

                    state._fsp--;

                     n = nExpIterate; 

                    }
                    break;
                case 3 :
                    // USE.g:789:7: nExpOperation= operationExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_operationExpression_in_propertyCall3414);
                    nExpOperation=operationExpression(source, followsArrow);

                    state._fsp--;

                     n = nExpOperation; 

                    }
                    break;
                case 4 :
                    // USE.g:790:7: nExpType= typeExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_typeExpression_in_propertyCall3427);
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
    // USE.g:799:1: queryExpression[ASTExpression range] returns [ASTExpression n] : op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN ;
    public final ASTExpression queryExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTElemVarsDeclaration decls = null;

        ASTExpression nExp = null;


        ASTElemVarsDeclaration decl = new ASTElemVarsDeclaration(); 
        try {
            // USE.g:800:69: (op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN )
            // USE.g:801:5: op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN
            {
            op=(Token)match(input,IDENT,FOLLOW_IDENT_in_queryExpression3462); 
            match(input,LPAREN,FOLLOW_LPAREN_in_queryExpression3469); 
            // USE.g:803:5: (decls= elemVarsDeclaration BAR )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==IDENT) ) {
                int LA68_1 = input.LA(2);

                if ( (LA68_1==COLON||LA68_1==COMMA||LA68_1==BAR) ) {
                    alt68=1;
                }
            }
            switch (alt68) {
                case 1 :
                    // USE.g:803:7: decls= elemVarsDeclaration BAR
                    {
                    pushFollow(FOLLOW_elemVarsDeclaration_in_queryExpression3480);
                    decls=elemVarsDeclaration();

                    state._fsp--;

                    decl = decls;
                    match(input,BAR,FOLLOW_BAR_in_queryExpression3484); 

                    }
                    break;

            }

            pushFollow(FOLLOW_expression_in_queryExpression3495);
            nExp=expression();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_queryExpression3501); 
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
    // USE.g:817:1: iterateExpression[ASTExpression range] returns [ASTExpression n] : i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN ;
    public final ASTExpression iterateExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTElemVarsDeclaration decls = null;

        ASTVariableInitialization init = null;

        ASTExpression nExp = null;


        try {
            // USE.g:817:65: (i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN )
            // USE.g:818:5: i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN
            {
            i=(Token)match(input,92,FOLLOW_92_in_iterateExpression3533); 
            match(input,LPAREN,FOLLOW_LPAREN_in_iterateExpression3539); 
            pushFollow(FOLLOW_elemVarsDeclaration_in_iterateExpression3547);
            decls=elemVarsDeclaration();

            state._fsp--;

            match(input,SEMI,FOLLOW_SEMI_in_iterateExpression3549); 
            pushFollow(FOLLOW_variableInitialization_in_iterateExpression3557);
            init=variableInitialization();

            state._fsp--;

            match(input,BAR,FOLLOW_BAR_in_iterateExpression3559); 
            pushFollow(FOLLOW_expression_in_iterateExpression3567);
            nExp=expression();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_iterateExpression3573); 
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
    // USE.g:839:1: operationExpression[ASTExpression source, boolean followsArrow] returns [ASTOperationExpression n] : name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? ;
    public final ASTOperationExpression operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTOperationExpression n = null;

        Token name=null;
        Token rolename=null;
        ASTExpression e = null;


        try {
            // USE.g:841:1: (name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? )
            // USE.g:842:5: name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression3617); 
             n = new ASTOperationExpression(name, source, followsArrow); 
            // USE.g:845:5: ( LBRACK rolename= IDENT RBRACK )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==LBRACK) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // USE.g:845:7: LBRACK rolename= IDENT RBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_operationExpression3633); 
                    rolename=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression3637); 
                    match(input,RBRACK,FOLLOW_RBRACK_in_operationExpression3639); 
                     n.setExplicitRolename(rolename); 

                    }
                    break;

            }

            // USE.g:847:5: ( AT 'pre' )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==AT) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // USE.g:847:7: AT 'pre'
                    {
                    match(input,AT,FOLLOW_AT_in_operationExpression3652); 
                    match(input,62,FOLLOW_62_in_operationExpression3654); 
                     n.setIsPre(); 

                    }
                    break;

            }

            // USE.g:848:5: ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==LPAREN) ) {
                alt73=1;
            }
            switch (alt73) {
                case 1 :
                    // USE.g:849:7: LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_operationExpression3675); 
                     n.hasParentheses(); 
                    // USE.g:850:7: (e= expression ( COMMA e= expression )* )?
                    int alt72=2;
                    int LA72_0 = input.LA(1);

                    if ( (LA72_0==IDENT||LA72_0==INT||LA72_0==LPAREN||(LA72_0>=PLUS && LA72_0<=MINUS)||(LA72_0>=REAL && LA72_0<=HASH)||LA72_0==74||LA72_0==84||LA72_0==90||(LA72_0>=92 && LA72_0<=105)) ) {
                        alt72=1;
                    }
                    switch (alt72) {
                        case 1 :
                            // USE.g:851:7: e= expression ( COMMA e= expression )*
                            {
                            pushFollow(FOLLOW_expression_in_operationExpression3696);
                            e=expression();

                            state._fsp--;

                             n.addArg(e); 
                            // USE.g:852:7: ( COMMA e= expression )*
                            loop71:
                            do {
                                int alt71=2;
                                int LA71_0 = input.LA(1);

                                if ( (LA71_0==COMMA) ) {
                                    alt71=1;
                                }


                                switch (alt71) {
                            	case 1 :
                            	    // USE.g:852:9: COMMA e= expression
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_operationExpression3708); 
                            	    pushFollow(FOLLOW_expression_in_operationExpression3712);
                            	    e=expression();

                            	    state._fsp--;

                            	     n.addArg(e); 

                            	    }
                            	    break;

                            	default :
                            	    break loop71;
                                }
                            } while (true);


                            }
                            break;

                    }

                    match(input,RPAREN,FOLLOW_RPAREN_in_operationExpression3732); 

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
    // USE.g:864:1: typeExpression[ASTExpression source, boolean followsArrow] returns [ASTTypeArgExpression n] : ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN ;
    public final ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTTypeArgExpression n = null;

        ASTType t = null;


         Token opToken = null; 
        try {
            // USE.g:867:1: ( ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN )
            // USE.g:868:2: ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN
            {
             opToken = input.LT(1); 
            if ( (input.LA(1)>=93 && input.LA(1)<=95) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_typeExpression3791); 
            pushFollow(FOLLOW_type_in_typeExpression3795);
            t=type();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_typeExpression3797); 
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
    // USE.g:879:1: elemVarsDeclaration returns [ASTElemVarsDeclaration n] : idListRes= idList ( COLON t= type )? ;
    public final ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException {
        ASTElemVarsDeclaration n = null;

        List idListRes = null;

        ASTType t = null;


         List idList; 
        try {
            // USE.g:881:1: (idListRes= idList ( COLON t= type )? )
            // USE.g:882:5: idListRes= idList ( COLON t= type )?
            {
            pushFollow(FOLLOW_idList_in_elemVarsDeclaration3836);
            idListRes=idList();

            state._fsp--;

            // USE.g:883:5: ( COLON t= type )?
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==COLON) ) {
                alt74=1;
            }
            switch (alt74) {
                case 1 :
                    // USE.g:883:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_elemVarsDeclaration3844); 
                    pushFollow(FOLLOW_type_in_elemVarsDeclaration3848);
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
    // USE.g:892:1: variableInitialization returns [ASTVariableInitialization n] : name= IDENT COLON t= type EQUAL e= expression ;
    public final ASTVariableInitialization variableInitialization() throws RecognitionException {
        ASTVariableInitialization n = null;

        Token name=null;
        ASTType t = null;

        ASTExpression e = null;


        try {
            // USE.g:893:1: (name= IDENT COLON t= type EQUAL e= expression )
            // USE.g:894:5: name= IDENT COLON t= type EQUAL e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableInitialization3883); 
            match(input,COLON,FOLLOW_COLON_in_variableInitialization3885); 
            pushFollow(FOLLOW_type_in_variableInitialization3889);
            t=type();

            state._fsp--;

            match(input,EQUAL,FOLLOW_EQUAL_in_variableInitialization3891); 
            pushFollow(FOLLOW_expression_in_variableInitialization3895);
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
    // USE.g:903:1: ifExpression returns [ASTExpression n] : i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' ;
    public final ASTExpression ifExpression() throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTExpression cond = null;

        ASTExpression t = null;

        ASTExpression e = null;


        try {
            // USE.g:904:1: (i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' )
            // USE.g:905:5: i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif'
            {
            i=(Token)match(input,74,FOLLOW_74_in_ifExpression3927); 
            pushFollow(FOLLOW_expression_in_ifExpression3931);
            cond=expression();

            state._fsp--;

            match(input,75,FOLLOW_75_in_ifExpression3933); 
            pushFollow(FOLLOW_expression_in_ifExpression3937);
            t=expression();

            state._fsp--;

            match(input,76,FOLLOW_76_in_ifExpression3939); 
            pushFollow(FOLLOW_expression_in_ifExpression3943);
            e=expression();

            state._fsp--;

            match(input,77,FOLLOW_77_in_ifExpression3945); 
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
    // USE.g:923:1: literal returns [ASTExpression n] : (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral );
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
            // USE.g:924:1: (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral )
            int alt75=10;
            switch ( input.LA(1) ) {
            case 96:
                {
                alt75=1;
                }
                break;
            case 97:
                {
                alt75=2;
                }
                break;
            case INT:
                {
                alt75=3;
                }
                break;
            case REAL:
                {
                alt75=4;
                }
                break;
            case STRING:
                {
                alt75=5;
                }
                break;
            case HASH:
                {
                alt75=6;
                }
                break;
            case 98:
            case 99:
            case 100:
                {
                alt75=7;
                }
                break;
            case 101:
                {
                alt75=8;
                }
                break;
            case 102:
            case 103:
            case 104:
                {
                alt75=9;
                }
                break;
            case 105:
                {
                alt75=10;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 75, 0, input);

                throw nvae;
            }

            switch (alt75) {
                case 1 :
                    // USE.g:925:7: t= 'true'
                    {
                    t=(Token)match(input,96,FOLLOW_96_in_literal3984); 
                     n = new ASTBooleanLiteral(true); 

                    }
                    break;
                case 2 :
                    // USE.g:926:7: f= 'false'
                    {
                    f=(Token)match(input,97,FOLLOW_97_in_literal3998); 
                     n = new ASTBooleanLiteral(false); 

                    }
                    break;
                case 3 :
                    // USE.g:927:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_literal4011); 
                     n = new ASTIntegerLiteral(i); 

                    }
                    break;
                case 4 :
                    // USE.g:928:7: r= REAL
                    {
                    r=(Token)match(input,REAL,FOLLOW_REAL_in_literal4026); 
                     n = new ASTRealLiteral(r); 

                    }
                    break;
                case 5 :
                    // USE.g:929:7: s= STRING
                    {
                    s=(Token)match(input,STRING,FOLLOW_STRING_in_literal4040); 
                     n = new ASTStringLiteral(s); 

                    }
                    break;
                case 6 :
                    // USE.g:930:7: HASH enumLit= IDENT
                    {
                    match(input,HASH,FOLLOW_HASH_in_literal4050); 
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal4054); 
                     n = new ASTEnumLiteral(enumLit); 

                    }
                    break;
                case 7 :
                    // USE.g:931:7: nColIt= collectionLiteral
                    {
                    pushFollow(FOLLOW_collectionLiteral_in_literal4066);
                    nColIt=collectionLiteral();

                    state._fsp--;

                     n = nColIt; 

                    }
                    break;
                case 8 :
                    // USE.g:932:7: nEColIt= emptyCollectionLiteral
                    {
                    pushFollow(FOLLOW_emptyCollectionLiteral_in_literal4078);
                    nEColIt=emptyCollectionLiteral();

                    state._fsp--;

                     n = nEColIt; 

                    }
                    break;
                case 9 :
                    // USE.g:933:7: nUndLit= undefinedLiteral
                    {
                    pushFollow(FOLLOW_undefinedLiteral_in_literal4090);
                    nUndLit=undefinedLiteral();

                    state._fsp--;

                    n = nUndLit; 

                    }
                    break;
                case 10 :
                    // USE.g:934:7: nTupleLit= tupleLiteral
                    {
                    pushFollow(FOLLOW_tupleLiteral_in_literal4102);
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
    // USE.g:942:1: collectionLiteral returns [ASTCollectionLiteral n] : ( 'Set' | 'Sequence' | 'Bag' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE ;
    public final ASTCollectionLiteral collectionLiteral() throws RecognitionException {
        ASTCollectionLiteral n = null;

        ASTCollectionItem ci = null;


         Token op = null; 
        try {
            // USE.g:944:1: ( ( 'Set' | 'Sequence' | 'Bag' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE )
            // USE.g:945:5: ( 'Set' | 'Sequence' | 'Bag' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE
            {
             op = input.LT(1); 
            if ( (input.LA(1)>=98 && input.LA(1)<=100) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

             n = new ASTCollectionLiteral(op); 
            match(input,LBRACE,FOLLOW_LBRACE_in_collectionLiteral4165); 
            // USE.g:949:5: (ci= collectionItem ( COMMA ci= collectionItem )* )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==IDENT||LA77_0==INT||LA77_0==LPAREN||(LA77_0>=PLUS && LA77_0<=MINUS)||(LA77_0>=REAL && LA77_0<=HASH)||LA77_0==74||LA77_0==84||LA77_0==90||(LA77_0>=92 && LA77_0<=105)) ) {
                alt77=1;
            }
            switch (alt77) {
                case 1 :
                    // USE.g:950:7: ci= collectionItem ( COMMA ci= collectionItem )*
                    {
                    pushFollow(FOLLOW_collectionItem_in_collectionLiteral4182);
                    ci=collectionItem();

                    state._fsp--;

                     n.addItem(ci); 
                    // USE.g:951:7: ( COMMA ci= collectionItem )*
                    loop76:
                    do {
                        int alt76=2;
                        int LA76_0 = input.LA(1);

                        if ( (LA76_0==COMMA) ) {
                            alt76=1;
                        }


                        switch (alt76) {
                    	case 1 :
                    	    // USE.g:951:9: COMMA ci= collectionItem
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_collectionLiteral4195); 
                    	    pushFollow(FOLLOW_collectionItem_in_collectionLiteral4199);
                    	    ci=collectionItem();

                    	    state._fsp--;

                    	     n.addItem(ci); 

                    	    }
                    	    break;

                    	default :
                    	    break loop76;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RBRACE,FOLLOW_RBRACE_in_collectionLiteral4218); 

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
    // USE.g:960:1: collectionItem returns [ASTCollectionItem n] : e= expression ( DOTDOT e= expression )? ;
    public final ASTCollectionItem collectionItem() throws RecognitionException {
        ASTCollectionItem n = null;

        ASTExpression e = null;


         n = new ASTCollectionItem(); 
        try {
            // USE.g:962:1: (e= expression ( DOTDOT e= expression )? )
            // USE.g:963:5: e= expression ( DOTDOT e= expression )?
            {
            pushFollow(FOLLOW_expression_in_collectionItem4247);
            e=expression();

            state._fsp--;

             n.setFirst(e); 
            // USE.g:964:5: ( DOTDOT e= expression )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==DOTDOT) ) {
                alt78=1;
            }
            switch (alt78) {
                case 1 :
                    // USE.g:964:7: DOTDOT e= expression
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_collectionItem4258); 
                    pushFollow(FOLLOW_expression_in_collectionItem4262);
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
    // USE.g:974:1: emptyCollectionLiteral returns [ASTEmptyCollectionLiteral n] : 'oclEmpty' LPAREN t= collectionType RPAREN ;
    public final ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException {
        ASTEmptyCollectionLiteral n = null;

        ASTCollectionType t = null;


        try {
            // USE.g:975:1: ( 'oclEmpty' LPAREN t= collectionType RPAREN )
            // USE.g:976:5: 'oclEmpty' LPAREN t= collectionType RPAREN
            {
            match(input,101,FOLLOW_101_in_emptyCollectionLiteral4291); 
            match(input,LPAREN,FOLLOW_LPAREN_in_emptyCollectionLiteral4293); 
            pushFollow(FOLLOW_collectionType_in_emptyCollectionLiteral4297);
            t=collectionType();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_emptyCollectionLiteral4299); 
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
    // USE.g:987:1: undefinedLiteral returns [ASTUndefinedLiteral n] : ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' );
    public final ASTUndefinedLiteral undefinedLiteral() throws RecognitionException {
        ASTUndefinedLiteral n = null;

        ASTType t = null;


        try {
            // USE.g:988:1: ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' )
            int alt79=3;
            switch ( input.LA(1) ) {
            case 102:
                {
                alt79=1;
                }
                break;
            case 103:
                {
                alt79=2;
                }
                break;
            case 104:
                {
                alt79=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 79, 0, input);

                throw nvae;
            }

            switch (alt79) {
                case 1 :
                    // USE.g:989:5: 'oclUndefined' LPAREN t= type RPAREN
                    {
                    match(input,102,FOLLOW_102_in_undefinedLiteral4329); 
                    match(input,LPAREN,FOLLOW_LPAREN_in_undefinedLiteral4331); 
                    pushFollow(FOLLOW_type_in_undefinedLiteral4335);
                    t=type();

                    state._fsp--;

                    match(input,RPAREN,FOLLOW_RPAREN_in_undefinedLiteral4337); 
                     n = new ASTUndefinedLiteral(t); 

                    }
                    break;
                case 2 :
                    // USE.g:992:5: 'Undefined'
                    {
                    match(input,103,FOLLOW_103_in_undefinedLiteral4351); 
                     n = new ASTUndefinedLiteral(); 

                    }
                    break;
                case 3 :
                    // USE.g:995:5: 'null'
                    {
                    match(input,104,FOLLOW_104_in_undefinedLiteral4365); 
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
    // USE.g:1004:1: tupleLiteral returns [ASTTupleLiteral n] : 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE ;
    public final ASTTupleLiteral tupleLiteral() throws RecognitionException {
        ASTTupleLiteral n = null;

        ASTTupleItem ti = null;


         List tiList = new ArrayList(); 
        try {
            // USE.g:1006:1: ( 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE )
            // USE.g:1007:5: 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE
            {
            match(input,105,FOLLOW_105_in_tupleLiteral4399); 
            match(input,LBRACE,FOLLOW_LBRACE_in_tupleLiteral4405); 
            pushFollow(FOLLOW_tupleItem_in_tupleLiteral4413);
            ti=tupleItem();

            state._fsp--;

             tiList.add(ti); 
            // USE.g:1010:5: ( COMMA ti= tupleItem )*
            loop80:
            do {
                int alt80=2;
                int LA80_0 = input.LA(1);

                if ( (LA80_0==COMMA) ) {
                    alt80=1;
                }


                switch (alt80) {
            	case 1 :
            	    // USE.g:1010:7: COMMA ti= tupleItem
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleLiteral4424); 
            	    pushFollow(FOLLOW_tupleItem_in_tupleLiteral4428);
            	    ti=tupleItem();

            	    state._fsp--;

            	     tiList.add(ti); 

            	    }
            	    break;

            	default :
            	    break loop80;
                }
            } while (true);

            match(input,RBRACE,FOLLOW_RBRACE_in_tupleLiteral4439); 
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
    // USE.g:1018:1: tupleItem returns [ASTTupleItem n] : name= IDENT ( COLON | EQUAL ) e= expression ;
    public final ASTTupleItem tupleItem() throws RecognitionException {
        ASTTupleItem n = null;

        Token name=null;
        ASTExpression e = null;


        try {
            // USE.g:1019:1: (name= IDENT ( COLON | EQUAL ) e= expression )
            // USE.g:1020:5: name= IDENT ( COLON | EQUAL ) e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tupleItem4470); 
            if ( (input.LA(1)>=COLON && input.LA(1)<=EQUAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            pushFollow(FOLLOW_expression_in_tupleItem4480);
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
    // USE.g:1031:1: type returns [ASTType n] : (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) ;
    public final ASTType type() throws RecognitionException {
        ASTType n = null;

        ASTSimpleType nTSimple = null;

        ASTCollectionType nTCollection = null;

        ASTTupleType nTTuple = null;


         Token tok = null; 
        try {
            // USE.g:1033:1: ( (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) )
            // USE.g:1034:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            {
             tok = input.LT(1); /* remember start of type */ 
            // USE.g:1035:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            int alt81=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt81=1;
                }
                break;
            case 98:
            case 99:
            case 100:
            case 106:
                {
                alt81=2;
                }
                break;
            case 105:
                {
                alt81=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 81, 0, input);

                throw nvae;
            }

            switch (alt81) {
                case 1 :
                    // USE.g:1036:7: nTSimple= simpleType
                    {
                    pushFollow(FOLLOW_simpleType_in_type4533);
                    nTSimple=simpleType();

                    state._fsp--;

                     n = nTSimple; n.setStartToken(tok); 

                    }
                    break;
                case 2 :
                    // USE.g:1037:7: nTCollection= collectionType
                    {
                    pushFollow(FOLLOW_collectionType_in_type4545);
                    nTCollection=collectionType();

                    state._fsp--;

                     n = nTCollection; n.setStartToken(tok); 

                    }
                    break;
                case 3 :
                    // USE.g:1038:7: nTTuple= tupleType
                    {
                    pushFollow(FOLLOW_tupleType_in_type4557);
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
    // USE.g:1043:1: typeOnly returns [ASTType n] : nT= type EOF ;
    public final ASTType typeOnly() throws RecognitionException {
        ASTType n = null;

        ASTType nT = null;


        try {
            // USE.g:1044:1: (nT= type EOF )
            // USE.g:1045:5: nT= type EOF
            {
            pushFollow(FOLLOW_type_in_typeOnly4589);
            nT=type();

            state._fsp--;

            match(input,EOF,FOLLOW_EOF_in_typeOnly4591); 
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
    // USE.g:1055:1: simpleType returns [ASTSimpleType n] : name= IDENT ;
    public final ASTSimpleType simpleType() throws RecognitionException {
        ASTSimpleType n = null;

        Token name=null;

        try {
            // USE.g:1056:1: (name= IDENT )
            // USE.g:1057:5: name= IDENT
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_simpleType4619); 
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
    // USE.g:1065:1: collectionType returns [ASTCollectionType n] : ( 'Collection' | 'Set' | 'Sequence' | 'Bag' ) LPAREN elemType= type RPAREN ;
    public final ASTCollectionType collectionType() throws RecognitionException {
        ASTCollectionType n = null;

        ASTType elemType = null;


         Token op = null; 
        try {
            // USE.g:1067:1: ( ( 'Collection' | 'Set' | 'Sequence' | 'Bag' ) LPAREN elemType= type RPAREN )
            // USE.g:1068:5: ( 'Collection' | 'Set' | 'Sequence' | 'Bag' ) LPAREN elemType= type RPAREN
            {
             op = input.LT(1); 
            if ( (input.LA(1)>=98 && input.LA(1)<=100)||input.LA(1)==106 ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_collectionType4680); 
            pushFollow(FOLLOW_type_in_collectionType4684);
            elemType=type();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_collectionType4686); 
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
    // USE.g:1078:1: tupleType returns [ASTTupleType n] : 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN ;
    public final ASTTupleType tupleType() throws RecognitionException {
        ASTTupleType n = null;

        ASTTuplePart tp = null;


         List tpList = new ArrayList(); 
        try {
            // USE.g:1080:1: ( 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN )
            // USE.g:1081:5: 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN
            {
            match(input,105,FOLLOW_105_in_tupleType4720); 
            match(input,LPAREN,FOLLOW_LPAREN_in_tupleType4722); 
            pushFollow(FOLLOW_tuplePart_in_tupleType4731);
            tp=tuplePart();

            state._fsp--;

             tpList.add(tp); 
            // USE.g:1083:5: ( COMMA tp= tuplePart )*
            loop82:
            do {
                int alt82=2;
                int LA82_0 = input.LA(1);

                if ( (LA82_0==COMMA) ) {
                    alt82=1;
                }


                switch (alt82) {
            	case 1 :
            	    // USE.g:1083:7: COMMA tp= tuplePart
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleType4742); 
            	    pushFollow(FOLLOW_tuplePart_in_tupleType4746);
            	    tp=tuplePart();

            	    state._fsp--;

            	     tpList.add(tp); 

            	    }
            	    break;

            	default :
            	    break loop82;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_tupleType4758); 
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
    // USE.g:1092:1: tuplePart returns [ASTTuplePart n] : name= IDENT COLON t= type ;
    public final ASTTuplePart tuplePart() throws RecognitionException {
        ASTTuplePart n = null;

        Token name=null;
        ASTType t = null;


        try {
            // USE.g:1093:1: (name= IDENT COLON t= type )
            // USE.g:1094:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tuplePart4790); 
            match(input,COLON,FOLLOW_COLON_in_tuplePart4792); 
            pushFollow(FOLLOW_type_in_tuplePart4796);
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
    public static final BitSet FOLLOW_44_in_classDefinition473 = new BitSet(new long[]{0x2004000000000000L});
    public static final BitSet FOLLOW_invariantClause_in_classDefinition493 = new BitSet(new long[]{0x2004000000000000L});
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
    public static final BitSet FOLLOW_44_in_associationClassDefinition702 = new BitSet(new long[]{0x20C4000000000000L});
    public static final BitSet FOLLOW_invariantClause_in_associationClassDefinition722 = new BitSet(new long[]{0x20C4000000000000L});
    public static final BitSet FOLLOW_set_in_associationClassDefinition756 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_associationClassDefinition785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_attributeDefinition814 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_attributeDefinition816 = new BitSet(new long[]{0x0000000000000010L,0x0000061C00000000L});
    public static final BitSet FOLLOW_type_in_attributeDefinition820 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_attributeDefinition824 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationDefinition862 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_paramList_in_operationDefinition870 = new BitSet(new long[]{0xC100000000000682L});
    public static final BitSet FOLLOW_COLON_in_operationDefinition878 = new BitSet(new long[]{0x0000000000000010L,0x0000061C00000000L});
    public static final BitSet FOLLOW_type_in_operationDefinition882 = new BitSet(new long[]{0xC100000000000482L});
    public static final BitSet FOLLOW_EQUAL_in_operationDefinition892 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_operationDefinition896 = new BitSet(new long[]{0xC100000000000082L});
    public static final BitSet FOLLOW_56_in_operationDefinition904 = new BitSet(new long[]{0x0004000000000000L,0x00000000000A46AFL});
    public static final BitSet FOLLOW_alActionList_in_operationDefinition908 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_50_in_operationDefinition910 = new BitSet(new long[]{0xC000000000000082L});
    public static final BitSet FOLLOW_prePostClause_in_operationDefinition929 = new BitSet(new long[]{0xC000000000000082L});
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
    public static final BitSet FOLLOW_IDENT_in_invariant1309 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_invariant1311 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_simpleType_in_invariant1326 = new BitSet(new long[]{0x2000000000000002L});
    public static final BitSet FOLLOW_invariantClause_in_invariant1338 = new BitSet(new long[]{0x2000000000000002L});
    public static final BitSet FOLLOW_61_in_invariantClause1368 = new BitSet(new long[]{0x0000000000000210L});
    public static final BitSet FOLLOW_IDENT_in_invariantClause1374 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_invariantClause1379 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_invariantClause1383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_60_in_prePost1413 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_prePost1417 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_COLON_COLON_in_prePost1419 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_prePost1423 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_paramList_in_prePost1427 = new BitSet(new long[]{0xC000000000000200L});
    public static final BitSet FOLLOW_COLON_in_prePost1431 = new BitSet(new long[]{0x0000000000000010L,0x0000061C00000000L});
    public static final BitSet FOLLOW_type_in_prePost1435 = new BitSet(new long[]{0xC000000000000000L});
    public static final BitSet FOLLOW_prePostClause_in_prePost1454 = new BitSet(new long[]{0xC000000000000002L});
    public static final BitSet FOLLOW_set_in_prePostClause1493 = new BitSet(new long[]{0x0000000000000210L});
    public static final BitSet FOLLOW_IDENT_in_prePostClause1508 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_prePostClause1513 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_prePostClause1517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alAction_in_alActionList1550 = new BitSet(new long[]{0x0000000000000002L,0x00000000000A46AFL});
    public static final BitSet FOLLOW_alCreateVar_in_alAction1576 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alDelete_in_alAction1588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alSet_in_alAction1600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alSetCreate_in_alAction1612 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alInsert_in_alAction1624 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alDestroy_in_alAction1636 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alIf_in_alAction1648 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alWhile_in_alAction1660 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alFor_in_alAction1672 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alExec_in_alAction1684 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_alCreateVar1703 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alCreateVar1711 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_alCreateVar1713 = new BitSet(new long[]{0x0000000000000010L,0x0000061C00000000L});
    public static final BitSet FOLLOW_type_in_alCreateVar1717 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_alSet1739 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_alSet1743 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_alSet1745 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_alSet1749 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_67_in_alSetCreate1773 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_alSetCreate1777 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_alSetCreate1779 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alSetCreate1785 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alSetCreate1789 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_alSetCreate1798 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_alSetCreate1802 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_69_in_alInsert1833 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_alInsert1835 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_alInsert1844 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_COMMA_in_alInsert1848 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_alInsert1856 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_COMMA_in_alInsert1862 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_alInsert1866 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_RPAREN_in_alInsert1878 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_alInsert1880 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alInsert1884 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_alDelete1916 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_alDelete1918 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_alDelete1926 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_COMMA_in_alDelete1930 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_alDelete1938 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_COMMA_in_alDelete1944 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_alDelete1948 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_RPAREN_in_alDelete1959 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_72_in_alDelete1961 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alDelete1965 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_73_in_alDestroy1994 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_alDestroy1998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_alIf2022 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_alIf2026 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_alIf2030 = new BitSet(new long[]{0x0000000000000000L,0x00000000000A76AFL});
    public static final BitSet FOLLOW_alActionList_in_alIf2034 = new BitSet(new long[]{0x0000000000000000L,0x0000000000003000L});
    public static final BitSet FOLLOW_76_in_alIf2038 = new BitSet(new long[]{0x0000000000000000L,0x00000000000A66AFL});
    public static final BitSet FOLLOW_alActionList_in_alIf2042 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_alIf2047 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_78_in_alWhile2066 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_alWhile2070 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_alWhile2074 = new BitSet(new long[]{0x0000000000000000L,0x00000000000B46AFL});
    public static final BitSet FOLLOW_alActionList_in_alWhile2080 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_alWhile2083 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_81_in_alFor2102 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alFor2106 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_alFor2108 = new BitSet(new long[]{0x0000000000000010L,0x0000061C00000000L});
    public static final BitSet FOLLOW_type_in_alFor2112 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_alFor2114 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_alFor2118 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_alFor2122 = new BitSet(new long[]{0x0000000000000010L,0x00000000000A46AFL});
    public static final BitSet FOLLOW_alActionList_in_alFor2128 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_alFor2135 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_alExec2155 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_alExec2159 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_expressionOnly2194 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_expressionOnly2196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_expression2244 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_expression2248 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COLON_in_expression2252 = new BitSet(new long[]{0x0000000000000010L,0x0000061C00000000L});
    public static final BitSet FOLLOW_type_in_expression2256 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_expression2261 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_expression2265 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_expression2267 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_conditionalImpliesExpression_in_expression2292 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_paramList2325 = new BitSet(new long[]{0x0000000000100010L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList2342 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_COMMA_in_paramList2354 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList2358 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_RPAREN_in_paramList2378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_idList2407 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_COMMA_in_idList2417 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_idList2421 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_IDENT_in_variableDeclaration2452 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_variableDeclaration2454 = new BitSet(new long[]{0x0000000000000010L,0x0000061C00000000L});
    public static final BitSet FOLLOW_type_in_variableDeclaration2458 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2494 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_conditionalImpliesExpression2507 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2511 = new BitSet(new long[]{0x0000000000000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2556 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_86_in_conditionalOrExpression2569 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2573 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2617 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_87_in_conditionalXOrExpression2630 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2634 = new BitSet(new long[]{0x0000000000000002L,0x0000000000800000L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression2678 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_88_in_conditionalAndExpression2691 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression2695 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression2743 = new BitSet(new long[]{0x0000000000200402L});
    public static final BitSet FOLLOW_set_in_equalityExpression2762 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression2772 = new BitSet(new long[]{0x0000000000200402L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression2821 = new BitSet(new long[]{0x0000000001C00102L});
    public static final BitSet FOLLOW_set_in_relationalExpression2839 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression2857 = new BitSet(new long[]{0x0000000001C00102L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression2907 = new BitSet(new long[]{0x0000000006000002L});
    public static final BitSet FOLLOW_set_in_additiveExpression2925 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression2935 = new BitSet(new long[]{0x0000000006000002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression2985 = new BitSet(new long[]{0x0000000008010002L,0x0000000002000000L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression3003 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression3017 = new BitSet(new long[]{0x0000000008010002L,0x0000000002000000L});
    public static final BitSet FOLLOW_set_in_unaryExpression3079 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression3103 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpression_in_unaryExpression3123 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_postfixExpression3156 = new BitSet(new long[]{0x0000000030000002L});
    public static final BitSet FOLLOW_ARROW_in_postfixExpression3174 = new BitSet(new long[]{0x0000000000000010L,0x00000000F0000000L});
    public static final BitSet FOLLOW_DOT_in_postfixExpression3180 = new BitSet(new long[]{0x0000000000000010L,0x00000000F0000000L});
    public static final BitSet FOLLOW_propertyCall_in_postfixExpression3191 = new BitSet(new long[]{0x0000000030000002L});
    public static final BitSet FOLLOW_literal_in_primaryExpression3231 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyCall_in_primaryExpression3243 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression3254 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_primaryExpression3258 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression3260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifExpression_in_primaryExpression3272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_primaryExpression3289 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_DOT_in_primaryExpression3291 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_91_in_primaryExpression3293 = new BitSet(new long[]{0x0000000040080002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression3297 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression3299 = new BitSet(new long[]{0x0000000040000002L});
    public static final BitSet FOLLOW_AT_in_primaryExpression3320 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_62_in_primaryExpression3322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_queryExpression_in_propertyCall3388 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterateExpression_in_propertyCall3401 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationExpression_in_propertyCall3414 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeExpression_in_propertyCall3427 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_queryExpression3462 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_queryExpression3469 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_queryExpression3480 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_BAR_in_queryExpression3484 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_queryExpression3495 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_queryExpression3501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_iterateExpression3533 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_iterateExpression3539 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_iterateExpression3547 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_in_iterateExpression3549 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableInitialization_in_iterateExpression3557 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_BAR_in_iterateExpression3559 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_iterateExpression3567 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_iterateExpression3573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression3617 = new BitSet(new long[]{0x0000000040080802L});
    public static final BitSet FOLLOW_LBRACK_in_operationExpression3633 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression3637 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RBRACK_in_operationExpression3639 = new BitSet(new long[]{0x0000000040080002L});
    public static final BitSet FOLLOW_AT_in_operationExpression3652 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_62_in_operationExpression3654 = new BitSet(new long[]{0x0000000000080002L});
    public static final BitSet FOLLOW_LPAREN_in_operationExpression3675 = new BitSet(new long[]{0x0000000706188010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_operationExpression3696 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_COMMA_in_operationExpression3708 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_operationExpression3712 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_RPAREN_in_operationExpression3732 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_typeExpression3775 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_typeExpression3791 = new BitSet(new long[]{0x0000000000000010L,0x0000061C00000000L});
    public static final BitSet FOLLOW_type_in_typeExpression3795 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_typeExpression3797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_elemVarsDeclaration3836 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_COLON_in_elemVarsDeclaration3844 = new BitSet(new long[]{0x0000000000000010L,0x0000061C00000000L});
    public static final BitSet FOLLOW_type_in_elemVarsDeclaration3848 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableInitialization3883 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_variableInitialization3885 = new BitSet(new long[]{0x0000000000000010L,0x0000061C00000000L});
    public static final BitSet FOLLOW_type_in_variableInitialization3889 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_variableInitialization3891 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_variableInitialization3895 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_74_in_ifExpression3927 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_ifExpression3931 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_75_in_ifExpression3933 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_ifExpression3937 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_ifExpression3939 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_ifExpression3943 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_77_in_ifExpression3945 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_literal3984 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_97_in_literal3998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_literal4011 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REAL_in_literal4026 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal4040 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_literal4050 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_literal4054 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionLiteral_in_literal4066 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyCollectionLiteral_in_literal4078 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedLiteral_in_literal4090 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleLiteral_in_literal4102 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionLiteral4140 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LBRACE_in_collectionLiteral4165 = new BitSet(new long[]{0x0000000706088050L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral4182 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_COMMA_in_collectionLiteral4195 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral4199 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_RBRACE_in_collectionLiteral4218 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_collectionItem4247 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_DOTDOT_in_collectionItem4258 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_collectionItem4262 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_101_in_emptyCollectionLiteral4291 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_emptyCollectionLiteral4293 = new BitSet(new long[]{0x0000000000000000L,0x0000041C00000000L});
    public static final BitSet FOLLOW_collectionType_in_emptyCollectionLiteral4297 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_emptyCollectionLiteral4299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_102_in_undefinedLiteral4329 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_undefinedLiteral4331 = new BitSet(new long[]{0x0000000000000010L,0x0000061C00000000L});
    public static final BitSet FOLLOW_type_in_undefinedLiteral4335 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_undefinedLiteral4337 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_undefinedLiteral4351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_104_in_undefinedLiteral4365 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_105_in_tupleLiteral4399 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LBRACE_in_tupleLiteral4405 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral4413 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_COMMA_in_tupleLiteral4424 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral4428 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_RBRACE_in_tupleLiteral4439 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tupleItem4470 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_set_in_tupleItem4472 = new BitSet(new long[]{0x0000000706088010L,0x000003FFF4100400L});
    public static final BitSet FOLLOW_expression_in_tupleItem4480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleType_in_type4533 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_type4545 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleType_in_type4557 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeOnly4589 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_typeOnly4591 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_simpleType4619 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionType4657 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_collectionType4680 = new BitSet(new long[]{0x0000000000000010L,0x0000061C00000000L});
    public static final BitSet FOLLOW_type_in_collectionType4684 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_RPAREN_in_collectionType4686 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_105_in_tupleType4720 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_LPAREN_in_tupleType4722 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType4731 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_COMMA_in_tupleType4742 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType4746 = new BitSet(new long[]{0x0000000000102000L});
    public static final BitSet FOLLOW_RPAREN_in_tupleType4758 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tuplePart4790 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_tuplePart4792 = new BitSet(new long[]{0x0000000000000010L,0x0000061C00000000L});
    public static final BitSet FOLLOW_type_in_tuplePart4796 = new BitSet(new long[]{0x0000000000000002L});

}