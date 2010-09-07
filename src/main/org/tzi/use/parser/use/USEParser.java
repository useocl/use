// $ANTLR 3.1.3 Mar 18, 2009 10:09:25 USE.g 2010-09-07 16:42:51
 
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
import org.tzi.use.parser.soil.ast.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
@SuppressWarnings("all") public class USEParser extends BaseParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "IDENT", "LBRACE", "RBRACE", "SEMI", "LESS", "COLON", "EQUAL", "LBRACK", "RBRACK", "COMMA", "DOTDOT", "INT", "STAR", "COLON_COLON", "LPAREN", "RPAREN", "NOT_EQUAL", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "PLUS", "MINUS", "SLASH", "ARROW", "DOT", "AT", "BAR", "REAL", "STRING", "HASH", "COLON_EQUAL", "NEWLINE", "WS", "SL_COMMENT", "ML_COMMENT", "SCRIPTBODY", "RANGE_OR_INT", "ESC", "HEX_DIGIT", "VOCAB", "'model'", "'constraints'", "'enum'", "'abstract'", "'class'", "'attributes'", "'operations'", "'end'", "'associationClass'", "'associationclass'", "'between'", "'aggregation'", "'composition'", "'begin'", "'ordered'", "'subsets'", "'redefines'", "'context'", "'inv'", "'existential'", "'pre'", "'post'", "'let'", "'in'", "'implies'", "'or'", "'xor'", "'and'", "'div'", "'not'", "'allInstances'", "'iterate'", "'oclAsType'", "'oclIsKindOf'", "'oclIsTypeOf'", "'if'", "'then'", "'else'", "'endif'", "'true'", "'false'", "'Set'", "'Sequence'", "'Bag'", "'OrderedSet'", "'oclEmpty'", "'oclUndefined'", "'Undefined'", "'null'", "'Tuple'", "'Date'", "'Collection'", "'new'", "'destroy'", "'insert'", "'into'", "'delete'", "'from'", "'for'", "'do'"
    };
    public static final int STAR=16;
    public static final int EOF=-1;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__91=91;
    public static final int RPAREN=19;
    public static final int T__92=92;
    public static final int GREATER=21;
    public static final int T__90=90;
    public static final int NOT_EQUAL=20;
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
    public static final int REAL=31;
    public static final int WS=36;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__70=70;
    public static final int SL_COMMENT=37;
    public static final int LESS_EQUAL=22;
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
    public static final int ESC=41;
    public static final int LBRACE=5;
    public static final int DOTDOT=14;
    public static final int T__61=61;
    public static final int T__60=60;
    public static final int LPAREN=18;
    public static final int T__55=55;
    public static final int AT=29;
    public static final int ML_COMMENT=38;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int COLON_EQUAL=34;
    public static final int T__51=51;
    public static final int SLASH=26;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int COMMA=13;
    public static final int T__59=59;
    public static final int T__103=103;
    public static final int EQUAL=10;
    public static final int IDENT=4;
    public static final int PLUS=24;
    public static final int RANGE_OR_INT=40;
    public static final int DOT=28;
    public static final int SCRIPTBODY=39;
    public static final int T__50=50;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int HASH=33;
    public static final int HEX_DIGIT=42;
    public static final int T__102=102;
    public static final int COLON_COLON=17;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int MINUS=25;
    public static final int SEMI=7;
    public static final int COLON=9;
    public static final int NEWLINE=35;
    public static final int VOCAB=43;
    public static final int ARROW=27;
    public static final int GREATER_EQUAL=23;
    public static final int BAR=30;
    public static final int STRING=32;

    // delegates
    // delegators


        public USEParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public USEParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[98+1];
             
             
        }
        

    public String[] getTokenNames() { return USEParser.tokenNames; }
    public String getGrammarFileName() { return "USE.g"; }



    // $ANTLR start "model"
    // USE.g:117:1: model returns [ASTModel n] : 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF ;
    public final ASTModel model() throws RecognitionException {
        ASTModel n = null;

        Token modelName=null;
        ASTEnumTypeDefinition e = null;

        ASTAssociation a = null;

        ASTConstraintDefinition cons = null;

        ASTPrePost ppc = null;


        try {
            // USE.g:118:1: ( 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF )
            // USE.g:119:5: 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF
            {
            match(input,44,FOLLOW_44_in_model69); if (state.failed) return n;
            modelName=(Token)match(input,IDENT,FOLLOW_IDENT_in_model73); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTModel(modelName); 
            }
            // USE.g:120:5: (e= enumTypeDefinition )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==46) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // USE.g:120:7: e= enumTypeDefinition
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

            // USE.g:121:5: ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )*
            loop3:
            do {
                int alt3=4;
                switch ( input.LA(1) ) {
                case 47:
                case 48:
                case 52:
                case 53:
                    {
                    alt3=1;
                    }
                    break;
                case IDENT:
                case 55:
                case 56:
                    {
                    alt3=2;
                    }
                    break;
                case 45:
                    {
                    alt3=3;
                    }
                    break;

                }

                switch (alt3) {
            	case 1 :
            	    // USE.g:121:9: ( generalClassDefinition[$n] )
            	    {
            	    // USE.g:121:9: ( generalClassDefinition[$n] )
            	    // USE.g:121:11: generalClassDefinition[$n]
            	    {
            	    pushFollow(FOLLOW_generalClassDefinition_in_model102);
            	    generalClassDefinition(n);

            	    state._fsp--;
            	    if (state.failed) return n;

            	    }


            	    }
            	    break;
            	case 2 :
            	    // USE.g:122:9: (a= associationDefinition )
            	    {
            	    // USE.g:122:9: (a= associationDefinition )
            	    // USE.g:122:11: a= associationDefinition
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
            	    // USE.g:123:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    {
            	    // USE.g:123:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    // USE.g:123:11: 'constraints' (cons= invariant | ppc= prePost )*
            	    {
            	    match(input,45,FOLLOW_45_in_model135); if (state.failed) return n;
            	    // USE.g:124:11: (cons= invariant | ppc= prePost )*
            	    loop2:
            	    do {
            	        int alt2=3;
            	        int LA2_0 = input.LA(1);

            	        if ( (LA2_0==61) ) {
            	            int LA2_2 = input.LA(2);

            	            if ( (LA2_2==IDENT) ) {
            	                int LA2_3 = input.LA(3);

            	                if ( (LA2_3==COLON_COLON) ) {
            	                    alt2=2;
            	                }
            	                else if ( (LA2_3==EOF||LA2_3==IDENT||LA2_3==COLON||LA2_3==COMMA||LA2_3==45||(LA2_3>=47 && LA2_3<=48)||(LA2_3>=52 && LA2_3<=53)||(LA2_3>=55 && LA2_3<=56)||(LA2_3>=61 && LA2_3<=63)) ) {
            	                    alt2=1;
            	                }


            	            }


            	        }


            	        switch (alt2) {
            	    	case 1 :
            	    	    // USE.g:124:15: cons= invariant
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
            	    	    // USE.g:125:15: ppc= prePost
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
    // USE.g:136:1: enumTypeDefinition returns [ASTEnumTypeDefinition n] : 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )? ;
    public final ASTEnumTypeDefinition enumTypeDefinition() throws RecognitionException {
        ASTEnumTypeDefinition n = null;

        Token name=null;
        List idListRes = null;


        try {
            // USE.g:137:1: ( 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )? )
            // USE.g:138:5: 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )?
            {
            match(input,46,FOLLOW_46_in_enumTypeDefinition242); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_enumTypeDefinition246); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_enumTypeDefinition248); if (state.failed) return n;
            pushFollow(FOLLOW_idList_in_enumTypeDefinition252);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            match(input,RBRACE,FOLLOW_RBRACE_in_enumTypeDefinition254); if (state.failed) return n;
            // USE.g:138:54: ( SEMI )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==SEMI) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // USE.g:138:56: SEMI
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
    // USE.g:147:1: generalClassDefinition[ASTModel n] : ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) ) ;
    public final void generalClassDefinition(ASTModel n) throws RecognitionException {
        ASTClass c = null;

        ASTAssociationClass ac = null;


         
          boolean isAbstract = false;

        try {
            // USE.g:151:1: ( ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) ) )
            // USE.g:152:5: ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) )
            {
            // USE.g:152:5: ( 'abstract' )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==47) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // USE.g:152:7: 'abstract'
                    {
                    match(input,47,FOLLOW_47_in_generalClassDefinition297); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                       isAbstract = true; 
                    }

                    }
                    break;

            }

            // USE.g:153:5: ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==48) ) {
                alt6=1;
            }
            else if ( ((LA6_0>=52 && LA6_0<=53)) ) {
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
                    // USE.g:153:7: (c= classDefinition[isAbstract] )
                    {
                    // USE.g:153:7: (c= classDefinition[isAbstract] )
                    // USE.g:153:9: c= classDefinition[isAbstract]
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
                    // USE.g:154:7: (ac= associationClassDefinition[isAbstract] )
                    {
                    // USE.g:154:7: (ac= associationClassDefinition[isAbstract] )
                    // USE.g:154:9: ac= associationClassDefinition[isAbstract]
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
    // USE.g:171:1: classDefinition[boolean isAbstract] returns [ASTClass n] : 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end' ;
    public final ASTClass classDefinition(boolean isAbstract) throws RecognitionException {
        ASTClass n = null;

        Token name=null;
        List idListRes = null;

        ASTAttribute a = null;

        ASTOperation op = null;

        ASTInvariantClause inv = null;


         List idList; 
        try {
            // USE.g:173:1: ( 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end' )
            // USE.g:174:5: 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end'
            {
            match(input,48,FOLLOW_48_in_classDefinition374); if (state.failed) return n;
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_classDefinition378); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTClass(name, isAbstract); 
            }
            // USE.g:175:5: ( LESS idListRes= idList )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==LESS) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // USE.g:175:7: LESS idListRes= idList
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

            // USE.g:176:5: ( 'attributes' (a= attributeDefinition )* )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==49) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // USE.g:176:7: 'attributes' (a= attributeDefinition )*
                    {
                    match(input,49,FOLLOW_49_in_classDefinition405); if (state.failed) return n;
                    // USE.g:177:7: (a= attributeDefinition )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0==IDENT) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // USE.g:177:9: a= attributeDefinition
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

            // USE.g:179:5: ( 'operations' (op= operationDefinition )* )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==50) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // USE.g:179:7: 'operations' (op= operationDefinition )*
                    {
                    match(input,50,FOLLOW_50_in_classDefinition439); if (state.failed) return n;
                    // USE.g:180:7: (op= operationDefinition )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0==IDENT) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // USE.g:180:9: op= operationDefinition
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

            // USE.g:182:5: ( 'constraints' (inv= invariantClause )* )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==45) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // USE.g:182:7: 'constraints' (inv= invariantClause )*
                    {
                    match(input,45,FOLLOW_45_in_classDefinition473); if (state.failed) return n;
                    // USE.g:183:7: (inv= invariantClause )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( ((LA12_0>=62 && LA12_0<=63)) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // USE.g:184:9: inv= invariantClause
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

            match(input,51,FOLLOW_51_in_classDefinition517); if (state.failed) return n;

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
    // USE.g:205:1: associationClassDefinition[boolean isAbstract] returns [ASTAssociationClass n] : classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end' ;
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
            // USE.g:207:1: (classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end' )
            // USE.g:208:5: classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end'
            {
            classKW=(Token)input.LT(1);
            if ( (input.LA(1)>=52 && input.LA(1)<=53) ) {
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
            // USE.g:217:5: ( LESS idListRes= idList )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==LESS) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // USE.g:217:7: LESS idListRes= idList
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

            match(input,54,FOLLOW_54_in_associationClassDefinition601); if (state.failed) return n;
            pushFollow(FOLLOW_associationEnd_in_associationClassDefinition609);
            ae=associationEnd();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addEnd(ae); 
            }
            // USE.g:220:5: (ae= associationEnd )+
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
            	    // USE.g:220:7: ae= associationEnd
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

            // USE.g:221:5: ( 'attributes' (a= attributeDefinition )* )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==49) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // USE.g:221:7: 'attributes' (a= attributeDefinition )*
                    {
                    match(input,49,FOLLOW_49_in_associationClassDefinition634); if (state.failed) return n;
                    // USE.g:222:7: (a= attributeDefinition )*
                    loop16:
                    do {
                        int alt16=2;
                        int LA16_0 = input.LA(1);

                        if ( (LA16_0==IDENT) ) {
                            alt16=1;
                        }


                        switch (alt16) {
                    	case 1 :
                    	    // USE.g:222:9: a= attributeDefinition
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

            // USE.g:224:5: ( 'operations' (op= operationDefinition )* )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==50) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // USE.g:224:7: 'operations' (op= operationDefinition )*
                    {
                    match(input,50,FOLLOW_50_in_associationClassDefinition668); if (state.failed) return n;
                    // USE.g:225:7: (op= operationDefinition )*
                    loop18:
                    do {
                        int alt18=2;
                        int LA18_0 = input.LA(1);

                        if ( (LA18_0==IDENT) ) {
                            alt18=1;
                        }


                        switch (alt18) {
                    	case 1 :
                    	    // USE.g:225:9: op= operationDefinition
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

            // USE.g:227:5: ( 'constraints' (inv= invariantClause )* )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==45) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // USE.g:227:7: 'constraints' (inv= invariantClause )*
                    {
                    match(input,45,FOLLOW_45_in_associationClassDefinition702); if (state.failed) return n;
                    // USE.g:228:7: (inv= invariantClause )*
                    loop20:
                    do {
                        int alt20=2;
                        int LA20_0 = input.LA(1);

                        if ( ((LA20_0>=62 && LA20_0<=63)) ) {
                            alt20=1;
                        }


                        switch (alt20) {
                    	case 1 :
                    	    // USE.g:229:9: inv= invariantClause
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

            // USE.g:232:5: ( ( 'aggregation' | 'composition' ) )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( ((LA22_0>=55 && LA22_0<=56)) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // USE.g:232:7: ( 'aggregation' | 'composition' )
                    {
                    if ( state.backtracking==0 ) {
                       t = input.LT(1); 
                    }
                    if ( (input.LA(1)>=55 && input.LA(1)<=56) ) {
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

            match(input,51,FOLLOW_51_in_associationClassDefinition785); if (state.failed) return n;

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
    // USE.g:243:1: attributeDefinition returns [ASTAttribute n] : name= IDENT COLON t= type ( SEMI )? ;
    public final ASTAttribute attributeDefinition() throws RecognitionException {
        ASTAttribute n = null;

        Token name=null;
        ASTType t = null;


        try {
            // USE.g:244:1: (name= IDENT COLON t= type ( SEMI )? )
            // USE.g:245:5: name= IDENT COLON t= type ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_attributeDefinition814); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_attributeDefinition816); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_attributeDefinition820);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            // USE.g:245:29: ( SEMI )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==SEMI) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // USE.g:245:31: SEMI
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
    // USE.g:254:1: operationDefinition returns [ASTOperation n] : name= IDENT pl= paramList ( COLON t= type )? ( ( EQUAL e= expression ) | ( 'begin' s= stat 'end' ) )? (ppc= prePostClause )* ( SEMI )? ;
    public final ASTOperation operationDefinition() throws RecognitionException {
        ASTOperation n = null;

        Token name=null;
        List pl = null;

        ASTType t = null;

        USEParser.expression_return e = null;

        USEParser.stat_return s = null;

        ASTPrePostClause ppc = null;


        try {
            // USE.g:255:1: (name= IDENT pl= paramList ( COLON t= type )? ( ( EQUAL e= expression ) | ( 'begin' s= stat 'end' ) )? (ppc= prePostClause )* ( SEMI )? )
            // USE.g:256:5: name= IDENT pl= paramList ( COLON t= type )? ( ( EQUAL e= expression ) | ( 'begin' s= stat 'end' ) )? (ppc= prePostClause )* ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationDefinition864); if (state.failed) return n;
            pushFollow(FOLLOW_paramList_in_operationDefinition874);
            pl=paramList();

            state._fsp--;
            if (state.failed) return n;
            // USE.g:258:5: ( COLON t= type )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==COLON) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // USE.g:258:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_operationDefinition882); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_operationDefinition888);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTOperation(name, pl, t); 
            }
            // USE.g:260:5: ( ( EQUAL e= expression ) | ( 'begin' s= stat 'end' ) )?
            int alt25=3;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==EQUAL) ) {
                alt25=1;
            }
            else if ( (LA25_0==57) ) {
                alt25=2;
            }
            switch (alt25) {
                case 1 :
                    // USE.g:261:9: ( EQUAL e= expression )
                    {
                    // USE.g:261:9: ( EQUAL e= expression )
                    // USE.g:261:11: EQUAL e= expression
                    {
                    match(input,EQUAL,FOLLOW_EQUAL_in_operationDefinition915); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_operationDefinition921);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setExpression((e!=null?e.n:null)); 
                    }

                    }


                    }
                    break;
                case 2 :
                    // USE.g:262:9: ( 'begin' s= stat 'end' )
                    {
                    // USE.g:262:9: ( 'begin' s= stat 'end' )
                    // USE.g:262:11: 'begin' s= stat 'end'
                    {
                    match(input,57,FOLLOW_57_in_operationDefinition939); if (state.failed) return n;
                    pushFollow(FOLLOW_stat_in_operationDefinition945);
                    s=stat();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,51,FOLLOW_51_in_operationDefinition947); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setStatement((s!=null?s.n:null));  
                    }

                    }


                    }
                    break;

            }

            // USE.g:264:5: (ppc= prePostClause )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( ((LA26_0>=64 && LA26_0<=65)) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // USE.g:264:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_operationDefinition968);
            	    ppc=prePostClause();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addPrePostClause(ppc); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);

            // USE.g:265:5: ( SEMI )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==SEMI) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // USE.g:265:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_operationDefinition981); if (state.failed) return n;

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
    // USE.g:275:1: associationDefinition returns [ASTAssociation n] : ( keyAssociation | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end' ;
    public final ASTAssociation associationDefinition() throws RecognitionException {
        ASTAssociation n = null;

        Token name=null;
        ASTAssociationEnd ae = null;


         Token t = null; 
        try {
            // USE.g:277:1: ( ( keyAssociation | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end' )
            // USE.g:278:5: ( keyAssociation | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end'
            {
            if ( state.backtracking==0 ) {
               t = input.LT(1); 
            }
            // USE.g:279:5: ( keyAssociation | 'aggregation' | 'composition' )
            int alt28=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt28=1;
                }
                break;
            case 55:
                {
                alt28=2;
                }
                break;
            case 56:
                {
                alt28=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }

            switch (alt28) {
                case 1 :
                    // USE.g:279:7: keyAssociation
                    {
                    pushFollow(FOLLOW_keyAssociation_in_associationDefinition1019);
                    keyAssociation();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;
                case 2 :
                    // USE.g:279:24: 'aggregation'
                    {
                    match(input,55,FOLLOW_55_in_associationDefinition1023); if (state.failed) return n;

                    }
                    break;
                case 3 :
                    // USE.g:279:40: 'composition'
                    {
                    match(input,56,FOLLOW_56_in_associationDefinition1027); if (state.failed) return n;

                    }
                    break;

            }

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationDefinition1042); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociation(t, name); 
            }
            match(input,54,FOLLOW_54_in_associationDefinition1050); if (state.failed) return n;
            pushFollow(FOLLOW_associationEnd_in_associationDefinition1058);
            ae=associationEnd();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addEnd(ae); 
            }
            // USE.g:284:5: (ae= associationEnd )+
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
            	    // USE.g:284:7: ae= associationEnd
            	    {
            	    pushFollow(FOLLOW_associationEnd_in_associationDefinition1070);
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

            match(input,51,FOLLOW_51_in_associationDefinition1081); if (state.failed) return n;

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
    // USE.g:293:1: associationEnd returns [ASTAssociationEnd n] : name= IDENT LBRACK m= multiplicity RBRACK ( keyRole rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )? ;
    public final ASTAssociationEnd associationEnd() throws RecognitionException {
        ASTAssociationEnd n = null;

        Token name=null;
        Token rn=null;
        Token sr=null;
        Token rd=null;
        ASTMultiplicity m = null;


        try {
            // USE.g:294:1: (name= IDENT LBRACK m= multiplicity RBRACK ( keyRole rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )? )
            // USE.g:295:5: name= IDENT LBRACK m= multiplicity RBRACK ( keyRole rn= IDENT )? ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )* ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd1107); if (state.failed) return n;
            match(input,LBRACK,FOLLOW_LBRACK_in_associationEnd1109); if (state.failed) return n;
            pushFollow(FOLLOW_multiplicity_in_associationEnd1113);
            m=multiplicity();

            state._fsp--;
            if (state.failed) return n;
            match(input,RBRACK,FOLLOW_RBRACK_in_associationEnd1115); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAssociationEnd(name, m); 
            }
            // USE.g:296:5: ( keyRole rn= IDENT )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==IDENT) ) {
                int LA30_1 = input.LA(2);

                if ( (LA30_1==IDENT) ) {
                    int LA30_3 = input.LA(3);

                    if ( ((input.LT(1).getText().equals("role"))) ) {
                        alt30=1;
                    }
                }
            }
            switch (alt30) {
                case 1 :
                    // USE.g:296:7: keyRole rn= IDENT
                    {
                    pushFollow(FOLLOW_keyRole_in_associationEnd1126);
                    keyRole();

                    state._fsp--;
                    if (state.failed) return n;
                    rn=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd1130); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setRolename(rn); 
                    }

                    }
                    break;

            }

            // USE.g:297:5: ( 'ordered' | 'subsets' sr= IDENT | keyUnion | 'redefines' rd= IDENT )*
            loop31:
            do {
                int alt31=5;
                switch ( input.LA(1) ) {
                case IDENT:
                    {
                    int LA31_2 = input.LA(2);

                    if ( (LA31_2==IDENT||LA31_2==SEMI||LA31_2==45||(LA31_2>=49 && LA31_2<=51)||(LA31_2>=55 && LA31_2<=56)||(LA31_2>=58 && LA31_2<=60)) ) {
                        alt31=3;
                    }


                    }
                    break;
                case 58:
                    {
                    alt31=1;
                    }
                    break;
                case 59:
                    {
                    alt31=2;
                    }
                    break;
                case 60:
                    {
                    alt31=4;
                    }
                    break;

                }

                switch (alt31) {
            	case 1 :
            	    // USE.g:298:9: 'ordered'
            	    {
            	    match(input,58,FOLLOW_58_in_associationEnd1151); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.setOrdered(); 
            	    }

            	    }
            	    break;
            	case 2 :
            	    // USE.g:299:9: 'subsets' sr= IDENT
            	    {
            	    match(input,59,FOLLOW_59_in_associationEnd1163); if (state.failed) return n;
            	    sr=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd1167); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addSubsetsRolename(sr); 
            	    }

            	    }
            	    break;
            	case 3 :
            	    // USE.g:300:9: keyUnion
            	    {
            	    pushFollow(FOLLOW_keyUnion_in_associationEnd1179);
            	    keyUnion();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.setUnion(true); 
            	    }

            	    }
            	    break;
            	case 4 :
            	    // USE.g:301:9: 'redefines' rd= IDENT
            	    {
            	    match(input,60,FOLLOW_60_in_associationEnd1191); if (state.failed) return n;
            	    rd=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd1195); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.addRedefinesRolename(rd); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);

            // USE.g:303:5: ( SEMI )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==SEMI) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // USE.g:303:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_associationEnd1212); if (state.failed) return n;

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
    // USE.g:317:1: multiplicity returns [ASTMultiplicity n] : mr= multiplicityRange ( COMMA mr= multiplicityRange )* ;
    public final ASTMultiplicity multiplicity() throws RecognitionException {
        ASTMultiplicity n = null;

        ASTMultiplicityRange mr = null;


        try {
            // USE.g:318:1: (mr= multiplicityRange ( COMMA mr= multiplicityRange )* )
            // USE.g:319:5: mr= multiplicityRange ( COMMA mr= multiplicityRange )*
            {
            if ( state.backtracking==0 ) {
               
              	Token t = input.LT(1); // remember start position of expression
              	n = new ASTMultiplicity(t);
                  
            }
            pushFollow(FOLLOW_multiplicityRange_in_multiplicity1247);
            mr=multiplicityRange();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.addRange(mr); 
            }
            // USE.g:324:5: ( COMMA mr= multiplicityRange )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==COMMA) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // USE.g:324:7: COMMA mr= multiplicityRange
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_multiplicity1257); if (state.failed) return n;
            	    pushFollow(FOLLOW_multiplicityRange_in_multiplicity1261);
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
    // USE.g:327:1: multiplicityRange returns [ASTMultiplicityRange n] : ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? ;
    public final ASTMultiplicityRange multiplicityRange() throws RecognitionException {
        ASTMultiplicityRange n = null;

        int ms1 = 0;

        int ms2 = 0;


        try {
            // USE.g:328:1: (ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? )
            // USE.g:329:5: ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )?
            {
            pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange1290);
            ms1=multiplicitySpec();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTMultiplicityRange(ms1); 
            }
            // USE.g:330:5: ( DOTDOT ms2= multiplicitySpec )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==DOTDOT) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // USE.g:330:7: DOTDOT ms2= multiplicitySpec
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_multiplicityRange1300); if (state.failed) return n;
                    pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange1304);
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
    // USE.g:333:1: multiplicitySpec returns [int m] : (i= INT | STAR );
    public final int multiplicitySpec() throws RecognitionException {
        int m = 0;

        Token i=null;

         m = -1; 
        try {
            // USE.g:335:1: (i= INT | STAR )
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
                    // USE.g:336:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_multiplicitySpec1338); if (state.failed) return m;
                    if ( state.backtracking==0 ) {
                       m = Integer.parseInt((i!=null?i.getText():null)); 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:337:7: STAR
                    {
                    match(input,STAR,FOLLOW_STAR_in_multiplicitySpec1348); if (state.failed) return m;
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
    // USE.g:358:1: invariant returns [ASTConstraintDefinition n] : 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )* ;
    public final ASTConstraintDefinition invariant() throws RecognitionException {
        ASTConstraintDefinition n = null;

        Token v=null;
        ASTSimpleType t = null;

        ASTInvariantClause inv = null;


        try {
            // USE.g:359:1: ( 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )* )
            // USE.g:360:5: 'context' (v= IDENT ( ',' v= IDENT )* COLON )? t= simpleType (inv= invariantClause )*
            {
            if ( state.backtracking==0 ) {
               n = new ASTConstraintDefinition(); 
            }
            match(input,61,FOLLOW_61_in_invariant1389); if (state.failed) return n;
            // USE.g:362:5: (v= IDENT ( ',' v= IDENT )* COLON )?
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
                    // USE.g:362:7: v= IDENT ( ',' v= IDENT )* COLON
                    {
                    v=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariant1399); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addVarName(v); 
                    }
                    // USE.g:363:8: ( ',' v= IDENT )*
                    loop36:
                    do {
                        int alt36=2;
                        int LA36_0 = input.LA(1);

                        if ( (LA36_0==COMMA) ) {
                            alt36=1;
                        }


                        switch (alt36) {
                    	case 1 :
                    	    // USE.g:363:9: ',' v= IDENT
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_invariant1412); if (state.failed) return n;
                    	    v=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariant1416); if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addVarName(v); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop36;
                        }
                    } while (true);

                    match(input,COLON,FOLLOW_COLON_in_invariant1424); if (state.failed) return n;

                    }
                    break;

            }

            pushFollow(FOLLOW_simpleType_in_invariant1436);
            t=simpleType();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setType(t); 
            }
            // USE.g:365:5: (inv= invariantClause )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( ((LA38_0>=62 && LA38_0<=63)) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // USE.g:365:7: inv= invariantClause
            	    {
            	    pushFollow(FOLLOW_invariantClause_in_invariant1448);
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
    // USE.g:372:1: invariantClause returns [ASTInvariantClause n] : ( 'inv' (name= IDENT )? COLON e= expression | 'existential' 'inv' (name= IDENT )? COLON e= expression );
    public final ASTInvariantClause invariantClause() throws RecognitionException {
        ASTInvariantClause n = null;

        Token name=null;
        USEParser.expression_return e = null;


        try {
            // USE.g:373:1: ( 'inv' (name= IDENT )? COLON e= expression | 'existential' 'inv' (name= IDENT )? COLON e= expression )
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==62) ) {
                alt41=1;
            }
            else if ( (LA41_0==63) ) {
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
                    // USE.g:374:7: 'inv' (name= IDENT )? COLON e= expression
                    {
                    match(input,62,FOLLOW_62_in_invariantClause1479); if (state.failed) return n;
                    // USE.g:374:13: (name= IDENT )?
                    int alt39=2;
                    int LA39_0 = input.LA(1);

                    if ( (LA39_0==IDENT) ) {
                        alt39=1;
                    }
                    switch (alt39) {
                        case 1 :
                            // USE.g:374:15: name= IDENT
                            {
                            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantClause1485); if (state.failed) return n;

                            }
                            break;

                    }

                    match(input,COLON,FOLLOW_COLON_in_invariantClause1490); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_invariantClause1494);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTInvariantClause(name, (e!=null?e.n:null)); 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:375:7: 'existential' 'inv' (name= IDENT )? COLON e= expression
                    {
                    match(input,63,FOLLOW_63_in_invariantClause1504); if (state.failed) return n;
                    match(input,62,FOLLOW_62_in_invariantClause1506); if (state.failed) return n;
                    // USE.g:375:27: (name= IDENT )?
                    int alt40=2;
                    int LA40_0 = input.LA(1);

                    if ( (LA40_0==IDENT) ) {
                        alt40=1;
                    }
                    switch (alt40) {
                        case 1 :
                            // USE.g:375:29: name= IDENT
                            {
                            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantClause1512); if (state.failed) return n;

                            }
                            break;

                    }

                    match(input,COLON,FOLLOW_COLON_in_invariantClause1517); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_invariantClause1521);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTExistentialInvariantClause(name, (e!=null?e.n:null)); 
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
    // USE.g:386:1: prePost returns [ASTPrePost n] : 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ ;
    public final ASTPrePost prePost() throws RecognitionException {
        ASTPrePost n = null;

        Token classname=null;
        Token opname=null;
        List pl = null;

        ASTType rt = null;

        ASTPrePostClause ppc = null;


        try {
            // USE.g:387:1: ( 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ )
            // USE.g:388:5: 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+
            {
            match(input,61,FOLLOW_61_in_prePost1547); if (state.failed) return n;
            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost1551); if (state.failed) return n;
            match(input,COLON_COLON,FOLLOW_COLON_COLON_in_prePost1553); if (state.failed) return n;
            opname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost1557); if (state.failed) return n;
            pushFollow(FOLLOW_paramList_in_prePost1561);
            pl=paramList();

            state._fsp--;
            if (state.failed) return n;
            // USE.g:388:69: ( COLON rt= type )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==COLON) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // USE.g:388:71: COLON rt= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_prePost1565); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_prePost1569);
                    rt=type();

                    state._fsp--;
                    if (state.failed) return n;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               n = new ASTPrePost(classname, opname, pl, rt); 
            }
            // USE.g:390:5: (ppc= prePostClause )+
            int cnt43=0;
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( ((LA43_0>=64 && LA43_0<=65)) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // USE.g:390:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_prePost1588);
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
    // USE.g:397:1: prePostClause returns [ASTPrePostClause n] : ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression ;
    public final ASTPrePostClause prePostClause() throws RecognitionException {
        ASTPrePostClause n = null;

        Token name=null;
        USEParser.expression_return e = null;


         Token t = null; 
        try {
            // USE.g:399:1: ( ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression )
            // USE.g:400:5: ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression
            {
            if ( state.backtracking==0 ) {
               t = input.LT(1); 
            }
            if ( (input.LA(1)>=64 && input.LA(1)<=65) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // USE.g:401:25: (name= IDENT )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==IDENT) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // USE.g:401:27: name= IDENT
                    {
                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePostClause1642); if (state.failed) return n;

                    }
                    break;

            }

            match(input,COLON,FOLLOW_COLON_in_prePostClause1647); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_prePostClause1651);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTPrePostClause(t, name, (e!=null?e.n:null)); 
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


    // $ANTLR start "keyUnion"
    // USE.g:405:1: keyUnion : {...}? IDENT ;
    public final void keyUnion() throws RecognitionException {
        try {
            // USE.g:405:9: ({...}? IDENT )
            // USE.g:406:3: {...}? IDENT
            {
            if ( !((input.LT(1).getText().equals("union"))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "keyUnion", "input.LT(1).getText().equals(\"union\")");
            }
            match(input,IDENT,FOLLOW_IDENT_in_keyUnion1673); if (state.failed) return ;

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


    // $ANTLR start "keyAssociation"
    // USE.g:408:1: keyAssociation : {...}? IDENT ;
    public final void keyAssociation() throws RecognitionException {
        try {
            // USE.g:408:15: ({...}? IDENT )
            // USE.g:409:3: {...}? IDENT
            {
            if ( !((input.LT(1).getText().equals("association"))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "keyAssociation", "input.LT(1).getText().equals(\"association\")");
            }
            match(input,IDENT,FOLLOW_IDENT_in_keyAssociation1687); if (state.failed) return ;

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
    // $ANTLR end "keyAssociation"


    // $ANTLR start "keyRole"
    // USE.g:411:1: keyRole : {...}? IDENT ;
    public final void keyRole() throws RecognitionException {
        try {
            // USE.g:411:8: ({...}? IDENT )
            // USE.g:412:3: {...}? IDENT
            {
            if ( !((input.LT(1).getText().equals("role"))) ) {
                if (state.backtracking>0) {state.failed=true; return ;}
                throw new FailedPredicateException(input, "keyRole", "input.LT(1).getText().equals(\"role\")");
            }
            match(input,IDENT,FOLLOW_IDENT_in_keyRole1701); if (state.failed) return ;

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
    // $ANTLR end "keyRole"


    // $ANTLR start "expressionOnly"
    // USE.g:443:1: expressionOnly returns [ASTExpression n] : nExp= expression EOF ;
    public final ASTExpression expressionOnly() throws RecognitionException {
        ASTExpression n = null;

        USEParser.expression_return nExp = null;


        try {
            // USE.g:444:1: (nExp= expression EOF )
            // USE.g:445:5: nExp= expression EOF
            {
            pushFollow(FOLLOW_expression_in_expressionOnly1734);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_expressionOnly1736); if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = (nExp!=null?nExp.n:null);
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

    public static class expression_return extends ParserRuleReturnScope {
        public ASTExpression n;
    };

    // $ANTLR start "expression"
    // USE.g:452:1: expression returns [ASTExpression n] : ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression ;
    public final USEParser.expression_return expression() throws RecognitionException {
        USEParser.expression_return retval = new USEParser.expression_return();
        retval.start = input.LT(1);

        Token name=null;
        ASTType t = null;

        USEParser.expression_return e1 = null;

        ASTExpression nCndImplies = null;


         
          ASTLetExpression prevLet = null, firstLet = null;
          ASTExpression e2;
          Token tok = null;

        try {
            // USE.g:458:1: ( ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression )
            // USE.g:459:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )* nCndImplies= conditionalImpliesExpression
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of expression */ 
            }
            // USE.g:460:5: ( 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in' )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==66) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // USE.g:461:7: 'let' name= IDENT ( COLON t= type )? EQUAL e1= expression 'in'
            	    {
            	    match(input,66,FOLLOW_66_in_expression1784); if (state.failed) return retval;
            	    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_expression1788); if (state.failed) return retval;
            	    // USE.g:461:24: ( COLON t= type )?
            	    int alt45=2;
            	    int LA45_0 = input.LA(1);

            	    if ( (LA45_0==COLON) ) {
            	        alt45=1;
            	    }
            	    switch (alt45) {
            	        case 1 :
            	            // USE.g:461:26: COLON t= type
            	            {
            	            match(input,COLON,FOLLOW_COLON_in_expression1792); if (state.failed) return retval;
            	            pushFollow(FOLLOW_type_in_expression1796);
            	            t=type();

            	            state._fsp--;
            	            if (state.failed) return retval;

            	            }
            	            break;

            	    }

            	    match(input,EQUAL,FOLLOW_EQUAL_in_expression1801); if (state.failed) return retval;
            	    pushFollow(FOLLOW_expression_in_expression1805);
            	    e1=expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    match(input,67,FOLLOW_67_in_expression1807); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	       ASTLetExpression nextLet = new ASTLetExpression(name, t, (e1!=null?e1.n:null));
            	               if ( firstLet == null ) 
            	                   firstLet = nextLet;
            	               if ( prevLet != null ) 
            	                   prevLet.setInExpr(nextLet);
            	               prevLet = nextLet;
            	             
            	    }

            	    }
            	    break;

            	default :
            	    break loop46;
                }
            } while (true);

            pushFollow(FOLLOW_conditionalImpliesExpression_in_expression1832);
            nCndImplies=conditionalImpliesExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) {
               if ( nCndImplies != null ) {
                  	 retval.n = nCndImplies;
                       retval.n.setStartToken(tok);
                    }
                    
                    if ( prevLet != null ) { 
                       prevLet.setInExpr(retval.n);
                       retval.n = firstLet;
                       retval.n.setStartToken(tok);
                    }
                  
            }

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expression"


    // $ANTLR start "paramList"
    // USE.g:489:1: paramList returns [List paramList] : LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN ;
    public final List paramList() throws RecognitionException {
        List paramList = null;

        ASTVariableDeclaration v = null;


         paramList = new ArrayList(); 
        try {
            // USE.g:491:1: ( LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN )
            // USE.g:492:5: LPAREN (v= variableDeclaration ( COMMA v= variableDeclaration )* )? RPAREN
            {
            match(input,LPAREN,FOLLOW_LPAREN_in_paramList1865); if (state.failed) return paramList;
            // USE.g:493:5: (v= variableDeclaration ( COMMA v= variableDeclaration )* )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==IDENT) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // USE.g:494:7: v= variableDeclaration ( COMMA v= variableDeclaration )*
                    {
                    pushFollow(FOLLOW_variableDeclaration_in_paramList1882);
                    v=variableDeclaration();

                    state._fsp--;
                    if (state.failed) return paramList;
                    if ( state.backtracking==0 ) {
                       paramList.add(v); 
                    }
                    // USE.g:495:7: ( COMMA v= variableDeclaration )*
                    loop47:
                    do {
                        int alt47=2;
                        int LA47_0 = input.LA(1);

                        if ( (LA47_0==COMMA) ) {
                            alt47=1;
                        }


                        switch (alt47) {
                    	case 1 :
                    	    // USE.g:495:9: COMMA v= variableDeclaration
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_paramList1894); if (state.failed) return paramList;
                    	    pushFollow(FOLLOW_variableDeclaration_in_paramList1898);
                    	    v=variableDeclaration();

                    	    state._fsp--;
                    	    if (state.failed) return paramList;
                    	    if ( state.backtracking==0 ) {
                    	       paramList.add(v); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop47;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_paramList1918); if (state.failed) return paramList;

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
    // USE.g:503:1: idList returns [List idList] : id0= IDENT ( COMMA idn= IDENT )* ;
    public final List idList() throws RecognitionException {
        List idList = null;

        Token id0=null;
        Token idn=null;

         idList = new ArrayList(); 
        try {
            // USE.g:505:1: (id0= IDENT ( COMMA idn= IDENT )* )
            // USE.g:506:5: id0= IDENT ( COMMA idn= IDENT )*
            {
            id0=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList1947); if (state.failed) return idList;
            if ( state.backtracking==0 ) {
               idList.add(id0); 
            }
            // USE.g:507:5: ( COMMA idn= IDENT )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==COMMA) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // USE.g:507:7: COMMA idn= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_idList1957); if (state.failed) return idList;
            	    idn=(Token)match(input,IDENT,FOLLOW_IDENT_in_idList1961); if (state.failed) return idList;
            	    if ( state.backtracking==0 ) {
            	       idList.add(idn); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop49;
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
    // USE.g:515:1: variableDeclaration returns [ASTVariableDeclaration n] : name= IDENT COLON t= type ;
    public final ASTVariableDeclaration variableDeclaration() throws RecognitionException {
        ASTVariableDeclaration n = null;

        Token name=null;
        ASTType t = null;


        try {
            // USE.g:516:1: (name= IDENT COLON t= type )
            // USE.g:517:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableDeclaration1992); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableDeclaration1994); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableDeclaration1998);
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
    // USE.g:525:1: conditionalImpliesExpression returns [ASTExpression n] : nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* ;
    public final ASTExpression conditionalImpliesExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndOrExp = null;

        ASTExpression n1 = null;


        try {
            // USE.g:526:1: (nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )* )
            // USE.g:527:5: nCndOrExp= conditionalOrExpression (op= 'implies' n1= conditionalOrExpression )*
            {
            pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2034);
            nCndOrExp=conditionalOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndOrExp;
            }
            // USE.g:528:5: (op= 'implies' n1= conditionalOrExpression )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==68) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // USE.g:528:7: op= 'implies' n1= conditionalOrExpression
            	    {
            	    op=(Token)match(input,68,FOLLOW_68_in_conditionalImpliesExpression2047); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2051);
            	    n1=conditionalOrExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop50;
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
    // USE.g:537:1: conditionalOrExpression returns [ASTExpression n] : nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* ;
    public final ASTExpression conditionalOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndXorExp = null;

        ASTExpression n1 = null;


        try {
            // USE.g:538:1: (nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )* )
            // USE.g:539:5: nCndXorExp= conditionalXOrExpression (op= 'or' n1= conditionalXOrExpression )*
            {
            pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2096);
            nCndXorExp=conditionalXOrExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndXorExp;
            }
            // USE.g:540:5: (op= 'or' n1= conditionalXOrExpression )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==69) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // USE.g:540:7: op= 'or' n1= conditionalXOrExpression
            	    {
            	    op=(Token)match(input,69,FOLLOW_69_in_conditionalOrExpression2109); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2113);
            	    n1=conditionalXOrExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop51;
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
    // USE.g:549:1: conditionalXOrExpression returns [ASTExpression n] : nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* ;
    public final ASTExpression conditionalXOrExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nCndAndExp = null;

        ASTExpression n1 = null;


        try {
            // USE.g:550:1: (nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )* )
            // USE.g:551:5: nCndAndExp= conditionalAndExpression (op= 'xor' n1= conditionalAndExpression )*
            {
            pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2157);
            nCndAndExp=conditionalAndExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nCndAndExp;
            }
            // USE.g:552:5: (op= 'xor' n1= conditionalAndExpression )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==70) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // USE.g:552:7: op= 'xor' n1= conditionalAndExpression
            	    {
            	    op=(Token)match(input,70,FOLLOW_70_in_conditionalXOrExpression2170); if (state.failed) return n;
            	    pushFollow(FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2174);
            	    n1=conditionalAndExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

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
        return n;
    }
    // $ANTLR end "conditionalXOrExpression"


    // $ANTLR start "conditionalAndExpression"
    // USE.g:561:1: conditionalAndExpression returns [ASTExpression n] : nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* ;
    public final ASTExpression conditionalAndExpression() throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTExpression nEqExp = null;

        ASTExpression n1 = null;


        try {
            // USE.g:562:1: (nEqExp= equalityExpression (op= 'and' n1= equalityExpression )* )
            // USE.g:563:5: nEqExp= equalityExpression (op= 'and' n1= equalityExpression )*
            {
            pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression2218);
            nEqExp=equalityExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nEqExp;
            }
            // USE.g:564:5: (op= 'and' n1= equalityExpression )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==71) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // USE.g:564:7: op= 'and' n1= equalityExpression
            	    {
            	    op=(Token)match(input,71,FOLLOW_71_in_conditionalAndExpression2231); if (state.failed) return n;
            	    pushFollow(FOLLOW_equalityExpression_in_conditionalAndExpression2235);
            	    n1=equalityExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

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
    // $ANTLR end "conditionalAndExpression"


    // $ANTLR start "equalityExpression"
    // USE.g:573:1: equalityExpression returns [ASTExpression n] : nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* ;
    public final ASTExpression equalityExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nRelExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // USE.g:575:1: (nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )* )
            // USE.g:576:5: nRelExp= relationalExpression ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            {
            pushFollow(FOLLOW_relationalExpression_in_equalityExpression2283);
            nRelExp=relationalExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nRelExp;
            }
            // USE.g:577:5: ( ( EQUAL | NOT_EQUAL ) n1= relationalExpression )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==EQUAL||LA54_0==NOT_EQUAL) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // USE.g:577:7: ( EQUAL | NOT_EQUAL ) n1= relationalExpression
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

            	    pushFollow(FOLLOW_relationalExpression_in_equalityExpression2312);
            	    n1=relationalExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
            	    }

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
    // $ANTLR end "equalityExpression"


    // $ANTLR start "relationalExpression"
    // USE.g:587:1: relationalExpression returns [ASTExpression n] : nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* ;
    public final ASTExpression relationalExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nAddiExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // USE.g:589:1: (nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )* )
            // USE.g:590:5: nAddiExp= additiveExpression ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            {
            pushFollow(FOLLOW_additiveExpression_in_relationalExpression2361);
            nAddiExp=additiveExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nAddiExp;
            }
            // USE.g:591:5: ( ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==LESS||(LA55_0>=GREATER && LA55_0<=GREATER_EQUAL)) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // USE.g:591:7: ( LESS | GREATER | LESS_EQUAL | GREATER_EQUAL ) n1= additiveExpression
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

            	    pushFollow(FOLLOW_additiveExpression_in_relationalExpression2397);
            	    n1=additiveExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = new ASTBinaryExpression(op, n, n1); 
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
        return n;
    }
    // $ANTLR end "relationalExpression"


    // $ANTLR start "additiveExpression"
    // USE.g:601:1: additiveExpression returns [ASTExpression n] : nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* ;
    public final ASTExpression additiveExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nMulExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // USE.g:603:1: (nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )* )
            // USE.g:604:5: nMulExp= multiplicativeExpression ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            {
            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression2447);
            nMulExp=multiplicativeExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
              n = nMulExp;
            }
            // USE.g:605:5: ( ( PLUS | MINUS ) n1= multiplicativeExpression )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( ((LA56_0>=PLUS && LA56_0<=MINUS)) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // USE.g:605:7: ( PLUS | MINUS ) n1= multiplicativeExpression
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

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression2475);
            	    n1=multiplicativeExpression();

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
    // $ANTLR end "additiveExpression"


    // $ANTLR start "multiplicativeExpression"
    // USE.g:616:1: multiplicativeExpression returns [ASTExpression n] : nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* ;
    public final ASTExpression multiplicativeExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression n1 = null;


         Token op = null; 
        try {
            // USE.g:618:1: (nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )* )
            // USE.g:619:5: nUnExp= unaryExpression ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            {
            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression2525);
            nUnExp=unaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nUnExp;
            }
            // USE.g:620:5: ( ( STAR | SLASH | 'div' ) n1= unaryExpression )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==STAR||LA57_0==SLASH||LA57_0==72) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // USE.g:620:7: ( STAR | SLASH | 'div' ) n1= unaryExpression
            	    {
            	    if ( state.backtracking==0 ) {
            	       op = input.LT(1); 
            	    }
            	    if ( input.LA(1)==STAR||input.LA(1)==SLASH||input.LA(1)==72 ) {
            	        input.consume();
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression2557);
            	    n1=unaryExpression();

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
    // $ANTLR end "multiplicativeExpression"


    // $ANTLR start "unaryExpression"
    // USE.g:632:1: unaryExpression returns [ASTExpression n] : ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression );
    public final ASTExpression unaryExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nUnExp = null;

        ASTExpression nPosExp = null;


         Token op = null; 
        try {
            // USE.g:634:1: ( ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression ) | nPosExp= postfixExpression )
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( ((LA58_0>=PLUS && LA58_0<=MINUS)||LA58_0==73) ) {
                alt58=1;
            }
            else if ( (LA58_0==IDENT||LA58_0==INT||LA58_0==LPAREN||LA58_0==AT||(LA58_0>=REAL && LA58_0<=HASH)||(LA58_0>=75 && LA58_0<=79)||(LA58_0>=83 && LA58_0<=94)) ) {
                alt58=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 58, 0, input);

                throw nvae;
            }
            switch (alt58) {
                case 1 :
                    // USE.g:635:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    {
                    // USE.g:635:7: ( ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression )
                    // USE.g:635:9: ( 'not' | MINUS | PLUS ) nUnExp= unaryExpression
                    {
                    if ( state.backtracking==0 ) {
                       op = input.LT(1); 
                    }
                    if ( (input.LA(1)>=PLUS && input.LA(1)<=MINUS)||input.LA(1)==73 ) {
                        input.consume();
                        state.errorRecovery=false;state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_unaryExpression_in_unaryExpression2643);
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
                    // USE.g:639:7: nPosExp= postfixExpression
                    {
                    pushFollow(FOLLOW_postfixExpression_in_unaryExpression2663);
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
    // USE.g:647:1: postfixExpression returns [ASTExpression n] : nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* ;
    public final ASTExpression postfixExpression() throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nPrimExp = null;

        ASTExpression nPc = null;


         boolean arrow = false; 
        try {
            // USE.g:649:1: (nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )* )
            // USE.g:650:5: nPrimExp= primaryExpression ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            {
            pushFollow(FOLLOW_primaryExpression_in_postfixExpression2696);
            nPrimExp=primaryExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = nPrimExp; 
            }
            // USE.g:651:5: ( ( ARROW | DOT ) nPc= propertyCall[$n, arrow] )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==DOT) ) {
                    int LA60_2 = input.LA(2);

                    if ( (LA60_2==IDENT) ) {
                        int LA60_4 = input.LA(3);

                        if ( (LA60_4==EOF||LA60_4==IDENT||(LA60_4>=RBRACE && LA60_4<=LESS)||(LA60_4>=EQUAL && LA60_4<=LBRACK)||(LA60_4>=COMMA && LA60_4<=DOTDOT)||LA60_4==STAR||(LA60_4>=LPAREN && LA60_4<=BAR)||LA60_4==45||(LA60_4>=47 && LA60_4<=48)||(LA60_4>=51 && LA60_4<=53)||(LA60_4>=55 && LA60_4<=56)||(LA60_4>=61 && LA60_4<=65)||(LA60_4>=67 && LA60_4<=72)||(LA60_4>=80 && LA60_4<=82)||LA60_4==103) ) {
                            alt60=1;
                        }


                    }
                    else if ( ((LA60_2>=75 && LA60_2<=78)) ) {
                        alt60=1;
                    }


                }
                else if ( (LA60_0==ARROW) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // USE.g:652:6: ( ARROW | DOT ) nPc= propertyCall[$n, arrow]
            	    {
            	    // USE.g:652:6: ( ARROW | DOT )
            	    int alt59=2;
            	    int LA59_0 = input.LA(1);

            	    if ( (LA59_0==ARROW) ) {
            	        alt59=1;
            	    }
            	    else if ( (LA59_0==DOT) ) {
            	        alt59=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return n;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 59, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt59) {
            	        case 1 :
            	            // USE.g:652:8: ARROW
            	            {
            	            match(input,ARROW,FOLLOW_ARROW_in_postfixExpression2714); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = true; 
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // USE.g:652:34: DOT
            	            {
            	            match(input,DOT,FOLLOW_DOT_in_postfixExpression2720); if (state.failed) return n;
            	            if ( state.backtracking==0 ) {
            	               arrow = false; 
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_propertyCall_in_postfixExpression2731);
            	    nPc=propertyCall(n, arrow);

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n = nPc; 
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
    // $ANTLR end "postfixExpression"


    // $ANTLR start "primaryExpression"
    // USE.g:668:1: primaryExpression returns [ASTExpression n] : (nLit= literal | nOr= objectReference | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? );
    public final ASTExpression primaryExpression() throws RecognitionException {
        ASTExpression n = null;

        Token id1=null;
        ASTExpression nLit = null;

        ASTExpression nOr = null;

        ASTExpression nPc = null;

        USEParser.expression_return nExp = null;

        ASTExpression nIfExp = null;


        try {
            // USE.g:669:1: (nLit= literal | nOr= objectReference | nPc= propertyCall[null, false] | LPAREN nExp= expression RPAREN | nIfExp= ifExpression | id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )? )
            int alt63=6;
            switch ( input.LA(1) ) {
            case INT:
            case REAL:
            case STRING:
            case HASH:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
                {
                alt63=1;
                }
                break;
            case IDENT:
                {
                switch ( input.LA(2) ) {
                case COLON_COLON:
                    {
                    alt63=1;
                    }
                    break;
                case EOF:
                case IDENT:
                case RBRACE:
                case SEMI:
                case LESS:
                case EQUAL:
                case LBRACK:
                case COMMA:
                case DOTDOT:
                case STAR:
                case LPAREN:
                case RPAREN:
                case NOT_EQUAL:
                case GREATER:
                case LESS_EQUAL:
                case GREATER_EQUAL:
                case PLUS:
                case MINUS:
                case SLASH:
                case ARROW:
                case AT:
                case BAR:
                case 45:
                case 47:
                case 48:
                case 51:
                case 52:
                case 53:
                case 55:
                case 56:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 67:
                case 68:
                case 69:
                case 70:
                case 71:
                case 72:
                case 80:
                case 81:
                case 82:
                case 103:
                    {
                    alt63=3;
                    }
                    break;
                case DOT:
                    {
                    int LA63_7 = input.LA(3);

                    if ( (LA63_7==74) ) {
                        alt63=6;
                    }
                    else if ( (LA63_7==IDENT||(LA63_7>=75 && LA63_7<=78)) ) {
                        alt63=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 63, 7, input);

                        throw nvae;
                    }
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 63, 2, input);

                    throw nvae;
                }

                }
                break;
            case AT:
                {
                alt63=2;
                }
                break;
            case 75:
            case 76:
            case 77:
            case 78:
                {
                alt63=3;
                }
                break;
            case LPAREN:
                {
                alt63=4;
                }
                break;
            case 79:
                {
                alt63=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;
            }

            switch (alt63) {
                case 1 :
                    // USE.g:670:7: nLit= literal
                    {
                    pushFollow(FOLLOW_literal_in_primaryExpression2771);
                    nLit=literal();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nLit; 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:671:7: nOr= objectReference
                    {
                    pushFollow(FOLLOW_objectReference_in_primaryExpression2785);
                    nOr=objectReference();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nOr; 
                    }

                    }
                    break;
                case 3 :
                    // USE.g:672:7: nPc= propertyCall[null, false]
                    {
                    pushFollow(FOLLOW_propertyCall_in_primaryExpression2797);
                    nPc=propertyCall(null, false);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nPc; 
                    }

                    }
                    break;
                case 4 :
                    // USE.g:673:7: LPAREN nExp= expression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression2808); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_primaryExpression2812);
                    nExp=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression2814); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (nExp!=null?nExp.n:null); 
                    }

                    }
                    break;
                case 5 :
                    // USE.g:674:7: nIfExp= ifExpression
                    {
                    pushFollow(FOLLOW_ifExpression_in_primaryExpression2826);
                    nIfExp=ifExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nIfExp; 
                    }

                    }
                    break;
                case 6 :
                    // USE.g:676:7: id1= IDENT DOT 'allInstances' ( LPAREN RPAREN )? ( AT 'pre' )?
                    {
                    id1=(Token)match(input,IDENT,FOLLOW_IDENT_in_primaryExpression2843); if (state.failed) return n;
                    match(input,DOT,FOLLOW_DOT_in_primaryExpression2845); if (state.failed) return n;
                    match(input,74,FOLLOW_74_in_primaryExpression2847); if (state.failed) return n;
                    // USE.g:676:36: ( LPAREN RPAREN )?
                    int alt61=2;
                    int LA61_0 = input.LA(1);

                    if ( (LA61_0==LPAREN) ) {
                        alt61=1;
                    }
                    switch (alt61) {
                        case 1 :
                            // USE.g:676:38: LPAREN RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_primaryExpression2851); if (state.failed) return n;
                            match(input,RPAREN,FOLLOW_RPAREN_in_primaryExpression2853); if (state.failed) return n;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       n = new ASTAllInstancesExpression(id1); 
                    }
                    // USE.g:678:7: ( AT 'pre' )?
                    int alt62=2;
                    int LA62_0 = input.LA(1);

                    if ( (LA62_0==AT) ) {
                        alt62=1;
                    }
                    switch (alt62) {
                        case 1 :
                            // USE.g:678:9: AT 'pre'
                            {
                            match(input,AT,FOLLOW_AT_in_primaryExpression2874); if (state.failed) return n;
                            match(input,64,FOLLOW_64_in_primaryExpression2876); if (state.failed) return n;
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


    // $ANTLR start "objectReference"
    // USE.g:682:1: objectReference returns [ASTExpression n] : AT objectName= IDENT ;
    public final ASTExpression objectReference() throws RecognitionException {
        ASTExpression n = null;

        Token objectName=null;

        try {
            // USE.g:683:1: ( AT objectName= IDENT )
            // USE.g:684:3: AT objectName= IDENT
            {
            match(input,AT,FOLLOW_AT_in_objectReference2903); if (state.failed) return n;
            objectName=(Token)match(input,IDENT,FOLLOW_IDENT_in_objectReference2911); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTObjectReferenceExpression(objectName); 
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
    // $ANTLR end "objectReference"


    // $ANTLR start "propertyCall"
    // USE.g:699:1: propertyCall[ASTExpression source, boolean followsArrow] returns [ASTExpression n] : ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] );
    public final ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTExpression n = null;

        ASTExpression nExpQuery = null;

        ASTExpression nExpIterate = null;

        USEParser.operationExpression_return nExpOperation = null;

        ASTTypeArgExpression nExpType = null;


        try {
            // USE.g:700:1: ({...}?{...}?nExpQuery= queryExpression[source] | nExpIterate= iterateExpression[source] | nExpOperation= operationExpression[source, followsArrow] | nExpType= typeExpression[source, followsArrow] )
            int alt64=4;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA64_1 = input.LA(2);

                if ( ((( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )&&( input.LA(2) == LPAREN ))) ) {
                    alt64=1;
                }
                else if ( (true) ) {
                    alt64=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 64, 1, input);

                    throw nvae;
                }
                }
                break;
            case 75:
                {
                alt64=2;
                }
                break;
            case 76:
            case 77:
            case 78:
                {
                alt64=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 64, 0, input);

                throw nvae;
            }

            switch (alt64) {
                case 1 :
                    // USE.g:704:7: {...}?{...}?nExpQuery= queryExpression[source]
                    {
                    if ( !(( org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " org.tzi.use.parser.base.ParserHelper.isQueryIdent(input.LT(1)) ");
                    }
                    if ( !(( input.LA(2) == LPAREN )) ) {
                        if (state.backtracking>0) {state.failed=true; return n;}
                        throw new FailedPredicateException(input, "propertyCall", " input.LA(2) == LPAREN ");
                    }
                    pushFollow(FOLLOW_queryExpression_in_propertyCall2979);
                    nExpQuery=queryExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpQuery; 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:707:7: nExpIterate= iterateExpression[source]
                    {
                    pushFollow(FOLLOW_iterateExpression_in_propertyCall2992);
                    nExpIterate=iterateExpression(source);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nExpIterate; 
                    }

                    }
                    break;
                case 3 :
                    // USE.g:708:7: nExpOperation= operationExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_operationExpression_in_propertyCall3005);
                    nExpOperation=operationExpression(source, followsArrow);

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (nExpOperation!=null?nExpOperation.n:null); 
                    }

                    }
                    break;
                case 4 :
                    // USE.g:709:7: nExpType= typeExpression[source, followsArrow]
                    {
                    pushFollow(FOLLOW_typeExpression_in_propertyCall3018);
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
    // USE.g:718:1: queryExpression[ASTExpression range] returns [ASTExpression n] : op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN ;
    public final ASTExpression queryExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token op=null;
        ASTElemVarsDeclaration decls = null;

        USEParser.expression_return nExp = null;


        ASTElemVarsDeclaration decl = new ASTElemVarsDeclaration(); 
        try {
            // USE.g:719:69: (op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN )
            // USE.g:720:5: op= IDENT LPAREN (decls= elemVarsDeclaration BAR )? nExp= expression RPAREN
            {
            op=(Token)match(input,IDENT,FOLLOW_IDENT_in_queryExpression3053); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_queryExpression3060); if (state.failed) return n;
            // USE.g:722:5: (decls= elemVarsDeclaration BAR )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==IDENT) ) {
                int LA65_1 = input.LA(2);

                if ( (LA65_1==COLON||LA65_1==COMMA||LA65_1==BAR) ) {
                    alt65=1;
                }
            }
            switch (alt65) {
                case 1 :
                    // USE.g:722:7: decls= elemVarsDeclaration BAR
                    {
                    pushFollow(FOLLOW_elemVarsDeclaration_in_queryExpression3071);
                    decls=elemVarsDeclaration();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      decl = decls;
                    }
                    match(input,BAR,FOLLOW_BAR_in_queryExpression3075); if (state.failed) return n;

                    }
                    break;

            }

            pushFollow(FOLLOW_expression_in_queryExpression3086);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_queryExpression3092); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTQueryExpression(op, range, decl, (nExp!=null?nExp.n:null)); 
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
    // USE.g:736:1: iterateExpression[ASTExpression range] returns [ASTExpression n] : i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN ;
    public final ASTExpression iterateExpression(ASTExpression range) throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        ASTElemVarsDeclaration decls = null;

        ASTVariableInitialization init = null;

        USEParser.expression_return nExp = null;


        try {
            // USE.g:736:65: (i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN )
            // USE.g:737:5: i= 'iterate' LPAREN decls= elemVarsDeclaration SEMI init= variableInitialization BAR nExp= expression RPAREN
            {
            i=(Token)match(input,75,FOLLOW_75_in_iterateExpression3124); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_iterateExpression3130); if (state.failed) return n;
            pushFollow(FOLLOW_elemVarsDeclaration_in_iterateExpression3138);
            decls=elemVarsDeclaration();

            state._fsp--;
            if (state.failed) return n;
            match(input,SEMI,FOLLOW_SEMI_in_iterateExpression3140); if (state.failed) return n;
            pushFollow(FOLLOW_variableInitialization_in_iterateExpression3148);
            init=variableInitialization();

            state._fsp--;
            if (state.failed) return n;
            match(input,BAR,FOLLOW_BAR_in_iterateExpression3150); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_iterateExpression3158);
            nExp=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_iterateExpression3164); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTIterateExpression(i, range, decls, init, (nExp!=null?nExp.n:null)); 
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

    public static class operationExpression_return extends ParserRuleReturnScope {
        public ASTOperationExpression n;
    };

    // $ANTLR start "operationExpression"
    // USE.g:758:1: operationExpression[ASTExpression source, boolean followsArrow] returns [ASTOperationExpression n] : name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? ;
    public final USEParser.operationExpression_return operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        USEParser.operationExpression_return retval = new USEParser.operationExpression_return();
        retval.start = input.LT(1);

        Token name=null;
        Token rolename=null;
        USEParser.expression_return e = null;


        try {
            // USE.g:760:1: (name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )? )
            // USE.g:761:5: name= IDENT ( LBRACK rolename= IDENT RBRACK )? ( AT 'pre' )? ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression3208); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
               retval.n = new ASTOperationExpression(name, source, followsArrow); 
            }
            // USE.g:764:5: ( LBRACK rolename= IDENT RBRACK )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==LBRACK) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // USE.g:764:7: LBRACK rolename= IDENT RBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_operationExpression3224); if (state.failed) return retval;
                    rolename=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationExpression3228); if (state.failed) return retval;
                    match(input,RBRACK,FOLLOW_RBRACK_in_operationExpression3230); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n.setExplicitRolename(rolename); 
                    }

                    }
                    break;

            }

            // USE.g:766:5: ( AT 'pre' )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==AT) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // USE.g:766:7: AT 'pre'
                    {
                    match(input,AT,FOLLOW_AT_in_operationExpression3243); if (state.failed) return retval;
                    match(input,64,FOLLOW_64_in_operationExpression3245); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n.setIsPre(); 
                    }

                    }
                    break;

            }

            // USE.g:767:5: ( LPAREN (e= expression ( COMMA e= expression )* )? RPAREN )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==LPAREN) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // USE.g:768:7: LPAREN (e= expression ( COMMA e= expression )* )? RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_operationExpression3266); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n.hasParentheses(); 
                    }
                    // USE.g:769:7: (e= expression ( COMMA e= expression )* )?
                    int alt69=2;
                    int LA69_0 = input.LA(1);

                    if ( (LA69_0==IDENT||LA69_0==INT||LA69_0==LPAREN||(LA69_0>=PLUS && LA69_0<=MINUS)||LA69_0==AT||(LA69_0>=REAL && LA69_0<=HASH)||LA69_0==66||LA69_0==73||(LA69_0>=75 && LA69_0<=79)||(LA69_0>=83 && LA69_0<=94)) ) {
                        alt69=1;
                    }
                    switch (alt69) {
                        case 1 :
                            // USE.g:770:7: e= expression ( COMMA e= expression )*
                            {
                            pushFollow(FOLLOW_expression_in_operationExpression3287);
                            e=expression();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                               retval.n.addArg((e!=null?e.n:null)); 
                            }
                            // USE.g:771:7: ( COMMA e= expression )*
                            loop68:
                            do {
                                int alt68=2;
                                int LA68_0 = input.LA(1);

                                if ( (LA68_0==COMMA) ) {
                                    alt68=1;
                                }


                                switch (alt68) {
                            	case 1 :
                            	    // USE.g:771:9: COMMA e= expression
                            	    {
                            	    match(input,COMMA,FOLLOW_COMMA_in_operationExpression3299); if (state.failed) return retval;
                            	    pushFollow(FOLLOW_expression_in_operationExpression3303);
                            	    e=expression();

                            	    state._fsp--;
                            	    if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) {
                            	       retval.n.addArg((e!=null?e.n:null)); 
                            	    }

                            	    }
                            	    break;

                            	default :
                            	    break loop68;
                                }
                            } while (true);


                            }
                            break;

                    }

                    match(input,RPAREN,FOLLOW_RPAREN_in_operationExpression3323); if (state.failed) return retval;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
               retval.n.setStartToken(((Token)retval.start)); 
            }

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "operationExpression"


    // $ANTLR start "typeExpression"
    // USE.g:784:1: typeExpression[ASTExpression source, boolean followsArrow] returns [ASTTypeArgExpression n] : ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN ;
    public final ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException {
        ASTTypeArgExpression n = null;

        ASTType t = null;


         Token opToken = null; 
        try {
            // USE.g:787:1: ( ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN )
            // USE.g:788:2: ( 'oclAsType' | 'oclIsKindOf' | 'oclIsTypeOf' ) LPAREN t= type RPAREN
            {
            if ( state.backtracking==0 ) {
               opToken = input.LT(1); 
            }
            if ( (input.LA(1)>=76 && input.LA(1)<=78) ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_typeExpression3388); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_typeExpression3392);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_typeExpression3394); if (state.failed) return n;
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
    // USE.g:799:1: elemVarsDeclaration returns [ASTElemVarsDeclaration n] : idListRes= idList ( COLON t= type )? ;
    public final ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException {
        ASTElemVarsDeclaration n = null;

        List idListRes = null;

        ASTType t = null;


         List idList; 
        try {
            // USE.g:801:1: (idListRes= idList ( COLON t= type )? )
            // USE.g:802:5: idListRes= idList ( COLON t= type )?
            {
            pushFollow(FOLLOW_idList_in_elemVarsDeclaration3433);
            idListRes=idList();

            state._fsp--;
            if (state.failed) return n;
            // USE.g:803:5: ( COLON t= type )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==COLON) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // USE.g:803:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_elemVarsDeclaration3441); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_elemVarsDeclaration3445);
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
    // USE.g:812:1: variableInitialization returns [ASTVariableInitialization n] : name= IDENT COLON t= type EQUAL e= expression ;
    public final ASTVariableInitialization variableInitialization() throws RecognitionException {
        ASTVariableInitialization n = null;

        Token name=null;
        ASTType t = null;

        USEParser.expression_return e = null;


        try {
            // USE.g:813:1: (name= IDENT COLON t= type EQUAL e= expression )
            // USE.g:814:5: name= IDENT COLON t= type EQUAL e= expression
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableInitialization3480); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_variableInitialization3482); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_variableInitialization3486);
            t=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EQUAL,FOLLOW_EQUAL_in_variableInitialization3488); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_variableInitialization3492);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTVariableInitialization(name, t, (e!=null?e.n:null)); 
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
    // USE.g:823:1: ifExpression returns [ASTExpression n] : i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' ;
    public final ASTExpression ifExpression() throws RecognitionException {
        ASTExpression n = null;

        Token i=null;
        USEParser.expression_return cond = null;

        USEParser.expression_return t = null;

        USEParser.expression_return e = null;


        try {
            // USE.g:824:1: (i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif' )
            // USE.g:825:5: i= 'if' cond= expression 'then' t= expression 'else' e= expression 'endif'
            {
            i=(Token)match(input,79,FOLLOW_79_in_ifExpression3524); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression3528);
            cond=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,80,FOLLOW_80_in_ifExpression3530); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression3534);
            t=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,81,FOLLOW_81_in_ifExpression3536); if (state.failed) return n;
            pushFollow(FOLLOW_expression_in_ifExpression3540);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            match(input,82,FOLLOW_82_in_ifExpression3542); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTIfExpression(i, (cond!=null?cond.n:null), (t!=null?t.n:null), (e!=null?e.n:null)); 
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
    // USE.g:845:1: literal returns [ASTExpression n] : (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral );
    public final ASTExpression literal() throws RecognitionException {
        ASTExpression n = null;

        Token t=null;
        Token f=null;
        Token i=null;
        Token r=null;
        Token s=null;
        Token enumLit=null;
        Token enumName=null;
        ASTCollectionLiteral nColIt = null;

        ASTEmptyCollectionLiteral nEColIt = null;

        ASTUndefinedLiteral nUndLit = null;

        ASTTupleLiteral nTupleLit = null;

        ASTDateLiteral nDateLit = null;


        try {
            // USE.g:846:1: (t= 'true' | f= 'false' | i= INT | r= REAL | s= STRING | HASH enumLit= IDENT | enumName= IDENT '::' enumLit= IDENT | nColIt= collectionLiteral | nEColIt= emptyCollectionLiteral | nUndLit= undefinedLiteral | nTupleLit= tupleLiteral | nDateLit= dateLiteral )
            int alt72=12;
            switch ( input.LA(1) ) {
            case 83:
                {
                alt72=1;
                }
                break;
            case 84:
                {
                alt72=2;
                }
                break;
            case INT:
                {
                alt72=3;
                }
                break;
            case REAL:
                {
                alt72=4;
                }
                break;
            case STRING:
                {
                alt72=5;
                }
                break;
            case HASH:
                {
                alt72=6;
                }
                break;
            case IDENT:
                {
                alt72=7;
                }
                break;
            case 85:
            case 86:
            case 87:
            case 88:
                {
                alt72=8;
                }
                break;
            case 89:
                {
                alt72=9;
                }
                break;
            case 90:
            case 91:
            case 92:
                {
                alt72=10;
                }
                break;
            case 93:
                {
                alt72=11;
                }
                break;
            case 94:
                {
                alt72=12;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 72, 0, input);

                throw nvae;
            }

            switch (alt72) {
                case 1 :
                    // USE.g:847:7: t= 'true'
                    {
                    t=(Token)match(input,83,FOLLOW_83_in_literal3581); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(true); 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:848:7: f= 'false'
                    {
                    f=(Token)match(input,84,FOLLOW_84_in_literal3595); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTBooleanLiteral(false); 
                    }

                    }
                    break;
                case 3 :
                    // USE.g:849:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_literal3608); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTIntegerLiteral(i); 
                    }

                    }
                    break;
                case 4 :
                    // USE.g:850:7: r= REAL
                    {
                    r=(Token)match(input,REAL,FOLLOW_REAL_in_literal3623); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTRealLiteral(r); 
                    }

                    }
                    break;
                case 5 :
                    // USE.g:851:7: s= STRING
                    {
                    s=(Token)match(input,STRING,FOLLOW_STRING_in_literal3637); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTStringLiteral(s); 
                    }

                    }
                    break;
                case 6 :
                    // USE.g:852:7: HASH enumLit= IDENT
                    {
                    match(input,HASH,FOLLOW_HASH_in_literal3647); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal3651); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumLit);
                    }

                    }
                    break;
                case 7 :
                    // USE.g:853:7: enumName= IDENT '::' enumLit= IDENT
                    {
                    enumName=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal3663); if (state.failed) return n;
                    match(input,COLON_COLON,FOLLOW_COLON_COLON_in_literal3665); if (state.failed) return n;
                    enumLit=(Token)match(input,IDENT,FOLLOW_IDENT_in_literal3669); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTEnumLiteral(enumName, enumLit); 
                    }

                    }
                    break;
                case 8 :
                    // USE.g:854:7: nColIt= collectionLiteral
                    {
                    pushFollow(FOLLOW_collectionLiteral_in_literal3681);
                    nColIt=collectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nColIt; 
                    }

                    }
                    break;
                case 9 :
                    // USE.g:855:7: nEColIt= emptyCollectionLiteral
                    {
                    pushFollow(FOLLOW_emptyCollectionLiteral_in_literal3693);
                    nEColIt=emptyCollectionLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nEColIt; 
                    }

                    }
                    break;
                case 10 :
                    // USE.g:856:7: nUndLit= undefinedLiteral
                    {
                    pushFollow(FOLLOW_undefinedLiteral_in_literal3705);
                    nUndLit=undefinedLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nUndLit; 
                    }

                    }
                    break;
                case 11 :
                    // USE.g:857:7: nTupleLit= tupleLiteral
                    {
                    pushFollow(FOLLOW_tupleLiteral_in_literal3717);
                    nTupleLit=tupleLiteral();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                      n = nTupleLit; 
                    }

                    }
                    break;
                case 12 :
                    // USE.g:858:7: nDateLit= dateLiteral
                    {
                    pushFollow(FOLLOW_dateLiteral_in_literal3729);
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
    // USE.g:866:1: collectionLiteral returns [ASTCollectionLiteral n] : ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE ;
    public final ASTCollectionLiteral collectionLiteral() throws RecognitionException {
        ASTCollectionLiteral n = null;

        ASTCollectionItem ci = null;


         Token op = null; 
        try {
            // USE.g:868:1: ( ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE )
            // USE.g:869:5: ( 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LBRACE (ci= collectionItem ( COMMA ci= collectionItem )* )? RBRACE
            {
            if ( state.backtracking==0 ) {
               op = input.LT(1); 
            }
            if ( (input.LA(1)>=85 && input.LA(1)<=88) ) {
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
            match(input,LBRACE,FOLLOW_LBRACE_in_collectionLiteral3796); if (state.failed) return n;
            // USE.g:873:5: (ci= collectionItem ( COMMA ci= collectionItem )* )?
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==IDENT||LA74_0==INT||LA74_0==LPAREN||(LA74_0>=PLUS && LA74_0<=MINUS)||LA74_0==AT||(LA74_0>=REAL && LA74_0<=HASH)||LA74_0==66||LA74_0==73||(LA74_0>=75 && LA74_0<=79)||(LA74_0>=83 && LA74_0<=94)) ) {
                alt74=1;
            }
            switch (alt74) {
                case 1 :
                    // USE.g:874:7: ci= collectionItem ( COMMA ci= collectionItem )*
                    {
                    pushFollow(FOLLOW_collectionItem_in_collectionLiteral3813);
                    ci=collectionItem();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.addItem(ci); 
                    }
                    // USE.g:875:7: ( COMMA ci= collectionItem )*
                    loop73:
                    do {
                        int alt73=2;
                        int LA73_0 = input.LA(1);

                        if ( (LA73_0==COMMA) ) {
                            alt73=1;
                        }


                        switch (alt73) {
                    	case 1 :
                    	    // USE.g:875:9: COMMA ci= collectionItem
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_collectionLiteral3826); if (state.failed) return n;
                    	    pushFollow(FOLLOW_collectionItem_in_collectionLiteral3830);
                    	    ci=collectionItem();

                    	    state._fsp--;
                    	    if (state.failed) return n;
                    	    if ( state.backtracking==0 ) {
                    	       n.addItem(ci); 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop73;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RBRACE,FOLLOW_RBRACE_in_collectionLiteral3849); if (state.failed) return n;

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
    // USE.g:884:1: collectionItem returns [ASTCollectionItem n] : e= expression ( DOTDOT e= expression )? ;
    public final ASTCollectionItem collectionItem() throws RecognitionException {
        ASTCollectionItem n = null;

        USEParser.expression_return e = null;


         n = new ASTCollectionItem(); 
        try {
            // USE.g:886:1: (e= expression ( DOTDOT e= expression )? )
            // USE.g:887:5: e= expression ( DOTDOT e= expression )?
            {
            pushFollow(FOLLOW_expression_in_collectionItem3878);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.setFirst((e!=null?e.n:null)); 
            }
            // USE.g:888:5: ( DOTDOT e= expression )?
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==DOTDOT) ) {
                alt75=1;
            }
            switch (alt75) {
                case 1 :
                    // USE.g:888:7: DOTDOT e= expression
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_collectionItem3889); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_collectionItem3893);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n.setSecond((e!=null?e.n:null)); 
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
    // USE.g:898:1: emptyCollectionLiteral returns [ASTEmptyCollectionLiteral n] : 'oclEmpty' LPAREN t= collectionType RPAREN ;
    public final ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException {
        ASTEmptyCollectionLiteral n = null;

        ASTCollectionType t = null;


        try {
            // USE.g:899:1: ( 'oclEmpty' LPAREN t= collectionType RPAREN )
            // USE.g:900:5: 'oclEmpty' LPAREN t= collectionType RPAREN
            {
            match(input,89,FOLLOW_89_in_emptyCollectionLiteral3922); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_emptyCollectionLiteral3924); if (state.failed) return n;
            pushFollow(FOLLOW_collectionType_in_emptyCollectionLiteral3928);
            t=collectionType();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_emptyCollectionLiteral3930); if (state.failed) return n;
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
    // USE.g:911:1: undefinedLiteral returns [ASTUndefinedLiteral n] : ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' );
    public final ASTUndefinedLiteral undefinedLiteral() throws RecognitionException {
        ASTUndefinedLiteral n = null;

        ASTType t = null;


        try {
            // USE.g:912:1: ( 'oclUndefined' LPAREN t= type RPAREN | 'Undefined' | 'null' )
            int alt76=3;
            switch ( input.LA(1) ) {
            case 90:
                {
                alt76=1;
                }
                break;
            case 91:
                {
                alt76=2;
                }
                break;
            case 92:
                {
                alt76=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 76, 0, input);

                throw nvae;
            }

            switch (alt76) {
                case 1 :
                    // USE.g:913:5: 'oclUndefined' LPAREN t= type RPAREN
                    {
                    match(input,90,FOLLOW_90_in_undefinedLiteral3960); if (state.failed) return n;
                    match(input,LPAREN,FOLLOW_LPAREN_in_undefinedLiteral3962); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_undefinedLiteral3966);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_undefinedLiteral3968); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(t); 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:916:5: 'Undefined'
                    {
                    match(input,91,FOLLOW_91_in_undefinedLiteral3982); if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTUndefinedLiteral(); 
                    }

                    }
                    break;
                case 3 :
                    // USE.g:919:5: 'null'
                    {
                    match(input,92,FOLLOW_92_in_undefinedLiteral3996); if (state.failed) return n;
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
    // USE.g:928:1: tupleLiteral returns [ASTTupleLiteral n] : 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE ;
    public final ASTTupleLiteral tupleLiteral() throws RecognitionException {
        ASTTupleLiteral n = null;

        ASTTupleItem ti = null;


         List tiList = new ArrayList(); 
        try {
            // USE.g:930:1: ( 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE )
            // USE.g:931:5: 'Tuple' LBRACE ti= tupleItem ( COMMA ti= tupleItem )* RBRACE
            {
            match(input,93,FOLLOW_93_in_tupleLiteral4030); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_tupleLiteral4036); if (state.failed) return n;
            pushFollow(FOLLOW_tupleItem_in_tupleLiteral4044);
            ti=tupleItem();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tiList.add(ti); 
            }
            // USE.g:934:5: ( COMMA ti= tupleItem )*
            loop77:
            do {
                int alt77=2;
                int LA77_0 = input.LA(1);

                if ( (LA77_0==COMMA) ) {
                    alt77=1;
                }


                switch (alt77) {
            	case 1 :
            	    // USE.g:934:7: COMMA ti= tupleItem
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleLiteral4055); if (state.failed) return n;
            	    pushFollow(FOLLOW_tupleItem_in_tupleLiteral4059);
            	    ti=tupleItem();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tiList.add(ti); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop77;
                }
            } while (true);

            match(input,RBRACE,FOLLOW_RBRACE_in_tupleLiteral4070); if (state.failed) return n;
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
    // USE.g:942:1: tupleItem returns [ASTTupleItem n] : name= IDENT ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) ;
    public final ASTTupleItem tupleItem() throws RecognitionException {
        ASTTupleItem n = null;

        Token name=null;
        ASTType t = null;

        USEParser.expression_return e = null;


        try {
            // USE.g:943:1: (name= IDENT ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression ) )
            // USE.g:944:5: name= IDENT ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tupleItem4101); if (state.failed) return n;
            // USE.g:945:5: ( ( COLON type EQUAL )=> COLON t= type EQUAL e= expression | ( COLON | EQUAL ) e= expression )
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==COLON) ) {
                int LA78_1 = input.LA(2);

                if ( (synpred1_USE()) ) {
                    alt78=1;
                }
                else if ( (true) ) {
                    alt78=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return n;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 78, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA78_0==EQUAL) ) {
                alt78=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 78, 0, input);

                throw nvae;
            }
            switch (alt78) {
                case 1 :
                    // USE.g:948:7: ( COLON type EQUAL )=> COLON t= type EQUAL e= expression
                    {
                    match(input,COLON,FOLLOW_COLON_in_tupleItem4140); if (state.failed) return n;
                    pushFollow(FOLLOW_type_in_tupleItem4144);
                    t=type();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,EQUAL,FOLLOW_EQUAL_in_tupleItem4146); if (state.failed) return n;
                    pushFollow(FOLLOW_expression_in_tupleItem4150);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTTupleItem(name, t, (e!=null?e.n:null)); 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:951:7: ( COLON | EQUAL ) e= expression
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

                    pushFollow(FOLLOW_expression_in_tupleItem4182);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ASTTupleItem(name, (e!=null?e.n:null)); 
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
    // USE.g:960:1: dateLiteral returns [ASTDateLiteral n] : 'Date' LBRACE v= STRING RBRACE ;
    public final ASTDateLiteral dateLiteral() throws RecognitionException {
        ASTDateLiteral n = null;

        Token v=null;

        try {
            // USE.g:961:1: ( 'Date' LBRACE v= STRING RBRACE )
            // USE.g:962:5: 'Date' LBRACE v= STRING RBRACE
            {
            match(input,94,FOLLOW_94_in_dateLiteral4227); if (state.failed) return n;
            match(input,LBRACE,FOLLOW_LBRACE_in_dateLiteral4229); if (state.failed) return n;
            v=(Token)match(input,STRING,FOLLOW_STRING_in_dateLiteral4233); if (state.failed) return n;
            match(input,RBRACE,FOLLOW_RBRACE_in_dateLiteral4235); if (state.failed) return n;
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
    // USE.g:972:1: type returns [ASTType n] : (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) ;
    public final ASTType type() throws RecognitionException {
        ASTType n = null;

        ASTSimpleType nTSimple = null;

        ASTCollectionType nTCollection = null;

        ASTTupleType nTTuple = null;


         Token tok = null; 
        try {
            // USE.g:974:1: ( (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType ) )
            // USE.g:975:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            {
            if ( state.backtracking==0 ) {
               tok = input.LT(1); /* remember start of type */ 
            }
            // USE.g:976:5: (nTSimple= simpleType | nTCollection= collectionType | nTTuple= tupleType )
            int alt79=3;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                alt79=1;
                }
                break;
            case 85:
            case 86:
            case 87:
            case 88:
            case 95:
                {
                alt79=2;
                }
                break;
            case 93:
                {
                alt79=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 79, 0, input);

                throw nvae;
            }

            switch (alt79) {
                case 1 :
                    // USE.g:977:7: nTSimple= simpleType
                    {
                    pushFollow(FOLLOW_simpleType_in_type4285);
                    nTSimple=simpleType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTSimple; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:978:7: nTCollection= collectionType
                    {
                    pushFollow(FOLLOW_collectionType_in_type4297);
                    nTCollection=collectionType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTCollection; if (n != null) n.setStartToken(tok); 
                    }

                    }
                    break;
                case 3 :
                    // USE.g:979:7: nTTuple= tupleType
                    {
                    pushFollow(FOLLOW_tupleType_in_type4309);
                    nTTuple=tupleType();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = nTTuple; if (n != null) n.setStartToken(tok); 
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
    // USE.g:984:1: typeOnly returns [ASTType n] : nT= type EOF ;
    public final ASTType typeOnly() throws RecognitionException {
        ASTType n = null;

        ASTType nT = null;


        try {
            // USE.g:985:1: (nT= type EOF )
            // USE.g:986:5: nT= type EOF
            {
            pushFollow(FOLLOW_type_in_typeOnly4341);
            nT=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_typeOnly4343); if (state.failed) return n;
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
    // USE.g:996:1: simpleType returns [ASTSimpleType n] : name= IDENT ;
    public final ASTSimpleType simpleType() throws RecognitionException {
        ASTSimpleType n = null;

        Token name=null;

        try {
            // USE.g:997:1: (name= IDENT )
            // USE.g:998:5: name= IDENT
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_simpleType4371); if (state.failed) return n;
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
    // USE.g:1006:1: collectionType returns [ASTCollectionType n] : ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN ;
    public final ASTCollectionType collectionType() throws RecognitionException {
        ASTCollectionType n = null;

        ASTType elemType = null;


         Token op = null; 
        try {
            // USE.g:1008:1: ( ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN )
            // USE.g:1009:5: ( 'Collection' | 'Set' | 'Sequence' | 'Bag' | 'OrderedSet' ) LPAREN elemType= type RPAREN
            {
            if ( state.backtracking==0 ) {
               op = input.LT(1); 
            }
            if ( (input.LA(1)>=85 && input.LA(1)<=88)||input.LA(1)==95 ) {
                input.consume();
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,LPAREN,FOLLOW_LPAREN_in_collectionType4436); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_collectionType4440);
            elemType=type();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_collectionType4442); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTCollectionType(op, elemType); if (n != null) n.setStartToken(op);
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
    // USE.g:1019:1: tupleType returns [ASTTupleType n] : 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN ;
    public final ASTTupleType tupleType() throws RecognitionException {
        ASTTupleType n = null;

        ASTTuplePart tp = null;


         List tpList = new ArrayList(); 
        try {
            // USE.g:1021:1: ( 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN )
            // USE.g:1022:5: 'Tuple' LPAREN tp= tuplePart ( COMMA tp= tuplePart )* RPAREN
            {
            match(input,93,FOLLOW_93_in_tupleType4476); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_tupleType4478); if (state.failed) return n;
            pushFollow(FOLLOW_tuplePart_in_tupleType4487);
            tp=tuplePart();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               tpList.add(tp); 
            }
            // USE.g:1024:5: ( COMMA tp= tuplePart )*
            loop80:
            do {
                int alt80=2;
                int LA80_0 = input.LA(1);

                if ( (LA80_0==COMMA) ) {
                    alt80=1;
                }


                switch (alt80) {
            	case 1 :
            	    // USE.g:1024:7: COMMA tp= tuplePart
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_tupleType4498); if (state.failed) return n;
            	    pushFollow(FOLLOW_tuplePart_in_tupleType4502);
            	    tp=tuplePart();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       tpList.add(tp); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop80;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_tupleType4514); if (state.failed) return n;
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
    // USE.g:1033:1: tuplePart returns [ASTTuplePart n] : name= IDENT COLON t= type ;
    public final ASTTuplePart tuplePart() throws RecognitionException {
        ASTTuplePart n = null;

        Token name=null;
        ASTType t = null;


        try {
            // USE.g:1034:1: (name= IDENT COLON t= type )
            // USE.g:1035:5: name= IDENT COLON t= type
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tuplePart4546); if (state.failed) return n;
            match(input,COLON,FOLLOW_COLON_in_tuplePart4548); if (state.failed) return n;
            pushFollow(FOLLOW_type_in_tuplePart4552);
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


    // $ANTLR start "statOnly"
    // USE.g:1074:1: statOnly returns [ASTStatement n] : s= stat EOF ;
    public final ASTStatement statOnly() throws RecognitionException {
        ASTStatement n = null;

        USEParser.stat_return s = null;


        try {
            // USE.g:1075:1: (s= stat EOF )
            // USE.g:1076:3: s= stat EOF
            {
            pushFollow(FOLLOW_stat_in_statOnly4601);
            s=stat();

            state._fsp--;
            if (state.failed) return n;
            match(input,EOF,FOLLOW_EOF_in_statOnly4605); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = (s!=null?s.n:null); 
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
    // $ANTLR end "statOnly"

    public static class stat_return extends ParserRuleReturnScope {
        public ASTStatement n;
    };

    // $ANTLR start "stat"
    // USE.g:1086:1: stat returns [ASTStatement n] : nextStat[seq] ( SEMI nextStat[seq] )* ;
    public final USEParser.stat_return stat() throws RecognitionException {
        USEParser.stat_return retval = new USEParser.stat_return();
        retval.start = input.LT(1);


          ASTSequenceStatement seq = new ASTSequenceStatement();

        try {
            // USE.g:1090:1: ( nextStat[seq] ( SEMI nextStat[seq] )* )
            // USE.g:1091:3: nextStat[seq] ( SEMI nextStat[seq] )*
            {
            pushFollow(FOLLOW_nextStat_in_stat4636);
            nextStat(seq);

            state._fsp--;
            if (state.failed) return retval;
            // USE.g:1092:3: ( SEMI nextStat[seq] )*
            loop81:
            do {
                int alt81=2;
                int LA81_0 = input.LA(1);

                if ( (LA81_0==SEMI) ) {
                    alt81=1;
                }


                switch (alt81) {
            	case 1 :
            	    // USE.g:1093:5: SEMI nextStat[seq]
            	    {
            	    match(input,SEMI,FOLLOW_SEMI_in_stat4647); if (state.failed) return retval;
            	    pushFollow(FOLLOW_nextStat_in_stat4653);
            	    nextStat(seq);

            	    state._fsp--;
            	    if (state.failed) return retval;

            	    }
            	    break;

            	default :
            	    break loop81;
                }
            } while (true);

            if ( state.backtracking==0 ) {
               
                  retval.n = seq.simplify();
                  if ((retval.n != null) && (!retval.n.isEmptyStatement())) {
                    retval.n.setSourcePosition(((Token)retval.start));
                    retval.n.setParsedText(input.toString(retval.start,input.LT(-1)));
                  }
                
            }

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "stat"

    public static class nextStat_return extends ParserRuleReturnScope {
    };

    // $ANTLR start "nextStat"
    // USE.g:1110:1: nextStat[ASTSequenceStatement seq] : s= singleStat ;
    public final USEParser.nextStat_return nextStat(ASTSequenceStatement seq) throws RecognitionException {
        USEParser.nextStat_return retval = new USEParser.nextStat_return();
        retval.start = input.LT(1);

        ASTStatement s = null;


        try {
            // USE.g:1111:1: (s= singleStat )
            // USE.g:1112:3: s= singleStat
            {
            pushFollow(FOLLOW_singleStat_in_nextStat4687);
            s=singleStat();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) {

                  if ((s != null) && (!s.isEmptyStatement())) {
                    seq.addStatement(s, ((Token)retval.start), input.toString(retval.start,input.LT(-1)));
                  }
                
            }

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "nextStat"


    // $ANTLR start "singleStat"
    // USE.g:1124:1: singleStat returns [ASTStatement n] options {backtrack=true; memoize=true; } : (emp= emptyStat | vas= varAssignStat | aas= attAssignStat | lcs= lobjCreateStat | ocs= objCreateStat | ods= objDestroyStat | lis= lnkInsStat | lds= lnkDelStat | ces= condExStat | its= iterStat | ops= callStat );
    public final ASTStatement singleStat() throws RecognitionException {
        ASTStatement n = null;
        int singleStat_StartIndex = input.index();
        ASTEmptyStatement emp = null;

        USEParser.varAssignStat_return vas = null;

        ASTAttributeAssignmentStatement aas = null;

        ASTNewLinkObjectStatement lcs = null;

        USEParser.objCreateStat_return ocs = null;

        USEParser.objDestroyStat_return ods = null;

        ASTLinkInsertionStatement lis = null;

        ASTLinkDeletionStatement lds = null;

        ASTConditionalExecutionStatement ces = null;

        ASTIterationStatement its = null;

        ASTOperationCallStatement ops = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 62) ) { return n; }
            // USE.g:1129:1: (emp= emptyStat | vas= varAssignStat | aas= attAssignStat | lcs= lobjCreateStat | ocs= objCreateStat | ods= objDestroyStat | lis= lnkInsStat | lds= lnkDelStat | ces= condExStat | its= iterStat | ops= callStat )
            int alt82=11;
            alt82 = dfa82.predict(input);
            switch (alt82) {
                case 1 :
                    // USE.g:1130:5: emp= emptyStat
                    {
                    pushFollow(FOLLOW_emptyStat_in_singleStat4742);
                    emp=emptyStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = emp; 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:1132:5: vas= varAssignStat
                    {
                    pushFollow(FOLLOW_varAssignStat_in_singleStat4763);
                    vas=varAssignStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (vas!=null?vas.n:null); 
                    }

                    }
                    break;
                case 3 :
                    // USE.g:1133:5: aas= attAssignStat
                    {
                    pushFollow(FOLLOW_attAssignStat_in_singleStat4777);
                    aas=attAssignStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = aas; 
                    }

                    }
                    break;
                case 4 :
                    // USE.g:1134:5: lcs= lobjCreateStat
                    {
                    pushFollow(FOLLOW_lobjCreateStat_in_singleStat4791);
                    lcs=lobjCreateStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = lcs; 
                    }

                    }
                    break;
                case 5 :
                    // USE.g:1135:5: ocs= objCreateStat
                    {
                    pushFollow(FOLLOW_objCreateStat_in_singleStat4804);
                    ocs=objCreateStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (ocs!=null?ocs.n:null); 
                    }

                    }
                    break;
                case 6 :
                    // USE.g:1136:5: ods= objDestroyStat
                    {
                    pushFollow(FOLLOW_objDestroyStat_in_singleStat4818);
                    ods=objDestroyStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = (ods!=null?ods.n:null); 
                    }

                    }
                    break;
                case 7 :
                    // USE.g:1137:5: lis= lnkInsStat
                    {
                    pushFollow(FOLLOW_lnkInsStat_in_singleStat4831);
                    lis=lnkInsStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = lis; 
                    }

                    }
                    break;
                case 8 :
                    // USE.g:1138:5: lds= lnkDelStat
                    {
                    pushFollow(FOLLOW_lnkDelStat_in_singleStat4848);
                    lds=lnkDelStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = lds; 
                    }

                    }
                    break;
                case 9 :
                    // USE.g:1139:5: ces= condExStat
                    {
                    pushFollow(FOLLOW_condExStat_in_singleStat4865);
                    ces=condExStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = ces; 
                    }

                    }
                    break;
                case 10 :
                    // USE.g:1140:5: its= iterStat
                    {
                    pushFollow(FOLLOW_iterStat_in_singleStat4882);
                    its=iterStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = its; 
                    }

                    }
                    break;
                case 11 :
                    // USE.g:1141:5: ops= callStat
                    {
                    pushFollow(FOLLOW_callStat_in_singleStat4901);
                    ops=callStat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = ops; 
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
            if ( state.backtracking>0 ) { memoize(input, 62, singleStat_StartIndex); }
        }
        return n;
    }
    // $ANTLR end "singleStat"


    // $ANTLR start "emptyStat"
    // USE.g:1149:1: emptyStat returns [ASTEmptyStatement n] : nothing ;
    public final ASTEmptyStatement emptyStat() throws RecognitionException {
        ASTEmptyStatement n = null;

        try {
            // USE.g:1150:1: ( nothing )
            // USE.g:1151:3: nothing
            {
            pushFollow(FOLLOW_nothing_in_emptyStat4932);
            nothing();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTEmptyStatement(); 
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
    // $ANTLR end "emptyStat"

    public static class varAssignStat_return extends ParserRuleReturnScope {
        public ASTStatement n;
    };

    // $ANTLR start "varAssignStat"
    // USE.g:1160:1: varAssignStat returns [ASTStatement n] options {backtrack=true; memoize=true; } : (varName= IDENT COLON_EQUAL rVal= rValue | vNames= identListMin1 COLON_EQUAL 'new' oType= simpleType ( LPAREN oNames= exprList RPAREN )? );
    public final USEParser.varAssignStat_return varAssignStat() throws RecognitionException {
        USEParser.varAssignStat_return retval = new USEParser.varAssignStat_return();
        retval.start = input.LT(1);
        int varAssignStat_StartIndex = input.index();
        Token varName=null;
        USEParser.rValue_return rVal = null;

        List<String> vNames = null;

        ASTSimpleType oType = null;

        List<ASTExpression> oNames = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 64) ) { return retval; }
            // USE.g:1165:1: (varName= IDENT COLON_EQUAL rVal= rValue | vNames= identListMin1 COLON_EQUAL 'new' oType= simpleType ( LPAREN oNames= exprList RPAREN )? )
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==IDENT) ) {
                int LA84_1 = input.LA(2);

                if ( (LA84_1==COLON_EQUAL) ) {
                    int LA84_2 = input.LA(3);

                    if ( (LA84_2==96) ) {
                        int LA84_4 = input.LA(4);

                        if ( (LA84_4==IDENT) ) {
                            int LA84_6 = input.LA(5);

                            if ( (synpred12_USE()) ) {
                                alt84=1;
                            }
                            else if ( (true) ) {
                                alt84=2;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 84, 6, input);

                                throw nvae;
                            }
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 84, 4, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA84_2==IDENT||LA84_2==INT||LA84_2==LPAREN||(LA84_2>=PLUS && LA84_2<=MINUS)||LA84_2==AT||(LA84_2>=REAL && LA84_2<=HASH)||LA84_2==66||LA84_2==73||(LA84_2>=75 && LA84_2<=79)||(LA84_2>=83 && LA84_2<=94)) ) {
                        alt84=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 84, 2, input);

                        throw nvae;
                    }
                }
                else if ( (LA84_1==COMMA) ) {
                    alt84=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 84, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 84, 0, input);

                throw nvae;
            }
            switch (alt84) {
                case 1 :
                    // USE.g:1166:3: varName= IDENT COLON_EQUAL rVal= rValue
                    {
                    varName=(Token)match(input,IDENT,FOLLOW_IDENT_in_varAssignStat4985); if (state.failed) return retval;
                    match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_varAssignStat4989); if (state.failed) return retval;
                    pushFollow(FOLLOW_rValue_in_varAssignStat4997);
                    rVal=rValue();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n = new ASTVariableAssignmentStatement((varName!=null?varName.getText():null), (rVal!=null?rVal.n:null)); 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:1172:3: vNames= identListMin1 COLON_EQUAL 'new' oType= simpleType ( LPAREN oNames= exprList RPAREN )?
                    {
                    pushFollow(FOLLOW_identListMin1_in_varAssignStat5016);
                    vNames=identListMin1();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_varAssignStat5020); if (state.failed) return retval;
                    match(input,96,FOLLOW_96_in_varAssignStat5024); if (state.failed) return retval;
                    pushFollow(FOLLOW_simpleType_in_varAssignStat5032);
                    oType=simpleType();

                    state._fsp--;
                    if (state.failed) return retval;
                    // USE.g:1176:3: ( LPAREN oNames= exprList RPAREN )?
                    int alt83=2;
                    int LA83_0 = input.LA(1);

                    if ( (LA83_0==LPAREN) ) {
                        alt83=1;
                    }
                    switch (alt83) {
                        case 1 :
                            // USE.g:1177:5: LPAREN oNames= exprList RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_varAssignStat5042); if (state.failed) return retval;
                            pushFollow(FOLLOW_exprList_in_varAssignStat5054);
                            oNames=exprList();

                            state._fsp--;
                            if (state.failed) return retval;
                            match(input,RPAREN,FOLLOW_RPAREN_in_varAssignStat5060); if (state.failed) return retval;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {

                          ASTSimpleType objType = oType;
                          List<String> varNames = vNames;
                          int numVars = varNames.size();
                          
                          List<ASTExpression> objNames = null;
                          int numNames = 0;
                          
                          if (oNames != null) {
                            objNames = oNames;
                            numNames = objNames.size();
                          }
                          
                          if (numNames > numVars) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("ignoring superfluous object name");
                            if ((numNames - numVars) > 1) {
                              sb.append("s");
                            }
                            sb.append(": ");
                            for (int i = numVars; i < numNames; ) {
                              sb.append(objNames.get(i).getStringRep());
                              if (++i < numNames) {
                                sb.append(", ");
                              }
                            }
                            sb.append(" in statement `");
                            sb.append(input.toString(retval.start,input.LT(-1))); 
                            sb.append("'");
                            
                            reportWarning(sb.toString());
                          }
                          
                          ASTSequenceStatement seq = new ASTSequenceStatement(numVars);
                          for (int i = 0; i < numVars; ++i) {     
                            seq.addStatement(
                              new ASTVariableAssignmentStatement(
                                varNames.get(i),
                                new ASTRValueNewObject(
                                  objType, 
                                  ((i < numNames) ? objNames.get(i) : null))),
                              ((Token)retval.start),
                              input.toString(retval.start,input.LT(-1)));  
                          }
                             
                          retval.n = seq.simplify();
                        
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 64, varAssignStat_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "varAssignStat"


    // $ANTLR start "attAssignStat"
    // USE.g:1235:1: attAssignStat returns [ASTAttributeAssignmentStatement n] : obj= inSoilExpression DOT attName= IDENT COLON_EQUAL r= rValue ;
    public final ASTAttributeAssignmentStatement attAssignStat() throws RecognitionException {
        ASTAttributeAssignmentStatement n = null;

        Token attName=null;
        ASTExpression obj = null;

        USEParser.rValue_return r = null;


        try {
            // USE.g:1236:1: (obj= inSoilExpression DOT attName= IDENT COLON_EQUAL r= rValue )
            // USE.g:1237:3: obj= inSoilExpression DOT attName= IDENT COLON_EQUAL r= rValue
            {
            pushFollow(FOLLOW_inSoilExpression_in_attAssignStat5095);
            obj=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            match(input,DOT,FOLLOW_DOT_in_attAssignStat5100); if (state.failed) return n;
            attName=(Token)match(input,IDENT,FOLLOW_IDENT_in_attAssignStat5109); if (state.failed) return n;
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_attAssignStat5113); if (state.failed) return n;
            pushFollow(FOLLOW_rValue_in_attAssignStat5121);
            r=rValue();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTAttributeAssignmentStatement(obj, (attName!=null?attName.getText():null), (r!=null?r.n:null)); 
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
    // $ANTLR end "attAssignStat"

    public static class objCreateStat_return extends ParserRuleReturnScope {
        public ASTStatement n;
    };

    // $ANTLR start "objCreateStat"
    // USE.g:1250:1: objCreateStat returns [ASTStatement n] : 'new' objType= simpleType ( LPAREN objNames= exprListMin1 RPAREN )? ;
    public final USEParser.objCreateStat_return objCreateStat() throws RecognitionException {
        USEParser.objCreateStat_return retval = new USEParser.objCreateStat_return();
        retval.start = input.LT(1);

        ASTSimpleType objType = null;

        List<ASTExpression> objNames = null;


        try {
            // USE.g:1251:1: ( 'new' objType= simpleType ( LPAREN objNames= exprListMin1 RPAREN )? )
            // USE.g:1252:3: 'new' objType= simpleType ( LPAREN objNames= exprListMin1 RPAREN )?
            {
            match(input,96,FOLLOW_96_in_objCreateStat5147); if (state.failed) return retval;
            pushFollow(FOLLOW_simpleType_in_objCreateStat5155);
            objType=simpleType();

            state._fsp--;
            if (state.failed) return retval;
            // USE.g:1254:3: ( LPAREN objNames= exprListMin1 RPAREN )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==LPAREN) ) {
                alt85=1;
            }
            switch (alt85) {
                case 1 :
                    // USE.g:1255:5: LPAREN objNames= exprListMin1 RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_objCreateStat5165); if (state.failed) return retval;
                    pushFollow(FOLLOW_exprListMin1_in_objCreateStat5177);
                    objNames=exprListMin1();

                    state._fsp--;
                    if (state.failed) return retval;
                    match(input,RPAREN,FOLLOW_RPAREN_in_objCreateStat5183); if (state.failed) return retval;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                  if (objNames == null) {
                    retval.n = new ASTNewObjectStatement(objType);
                  } else {
                    ASTSequenceStatement seq = new ASTSequenceStatement();
                    
                    for (ASTExpression objName : objNames){    
                      seq.addStatement(
                        new ASTNewObjectStatement(objType, objName),
                        ((Token)retval.start),
                        input.toString(retval.start,input.LT(-1)));
                    }
                    
                    retval.n = seq.simplify();
                  }
                
            }

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "objCreateStat"


    // $ANTLR start "lobjCreateStat"
    // USE.g:1282:1: lobjCreateStat returns [ASTNewLinkObjectStatement n] : 'new' assoc= IDENT ( LPAREN name= inSoilExpression RPAREN )? 'between' LPAREN p= rValListMin2 RPAREN ;
    public final ASTNewLinkObjectStatement lobjCreateStat() throws RecognitionException {
        ASTNewLinkObjectStatement n = null;

        Token assoc=null;
        ASTExpression name = null;

        List<ASTRValue> p = null;


        try {
            // USE.g:1283:1: ( 'new' assoc= IDENT ( LPAREN name= inSoilExpression RPAREN )? 'between' LPAREN p= rValListMin2 RPAREN )
            // USE.g:1284:3: 'new' assoc= IDENT ( LPAREN name= inSoilExpression RPAREN )? 'between' LPAREN p= rValListMin2 RPAREN
            {
            match(input,96,FOLLOW_96_in_lobjCreateStat5214); if (state.failed) return n;
            assoc=(Token)match(input,IDENT,FOLLOW_IDENT_in_lobjCreateStat5222); if (state.failed) return n;
            // USE.g:1286:3: ( LPAREN name= inSoilExpression RPAREN )?
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==LPAREN) ) {
                alt86=1;
            }
            switch (alt86) {
                case 1 :
                    // USE.g:1287:5: LPAREN name= inSoilExpression RPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_lobjCreateStat5232); if (state.failed) return n;
                    pushFollow(FOLLOW_inSoilExpression_in_lobjCreateStat5244);
                    name=inSoilExpression();

                    state._fsp--;
                    if (state.failed) return n;
                    match(input,RPAREN,FOLLOW_RPAREN_in_lobjCreateStat5250); if (state.failed) return n;

                    }
                    break;

            }

            match(input,54,FOLLOW_54_in_lobjCreateStat5259); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_lobjCreateStat5263); if (state.failed) return n;
            pushFollow(FOLLOW_rValListMin2_in_lobjCreateStat5273);
            p=rValListMin2();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_lobjCreateStat5277); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               
                  n = 
                    new ASTNewLinkObjectStatement(
                      (assoc!=null?assoc.getText():null), p, name); 
                
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
    // $ANTLR end "lobjCreateStat"

    public static class objDestroyStat_return extends ParserRuleReturnScope {
        public ASTStatement n;
    };

    // $ANTLR start "objDestroyStat"
    // USE.g:1307:1: objDestroyStat returns [ASTStatement n] : 'destroy' el= exprListMin1 ;
    public final USEParser.objDestroyStat_return objDestroyStat() throws RecognitionException {
        USEParser.objDestroyStat_return retval = new USEParser.objDestroyStat_return();
        retval.start = input.LT(1);

        List<ASTExpression> el = null;


        try {
            // USE.g:1308:1: ( 'destroy' el= exprListMin1 )
            // USE.g:1309:3: 'destroy' el= exprListMin1
            {
            match(input,97,FOLLOW_97_in_objDestroyStat5303); if (state.failed) return retval;
            pushFollow(FOLLOW_exprListMin1_in_objDestroyStat5311);
            el=exprListMin1();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) {

                  ASTSequenceStatement seq = new ASTSequenceStatement();
                  
                  for (ASTExpression expression : el) {
                    seq.addStatement(
                      new ASTObjectDestructionStatement(expression),
                      ((Token)retval.start),
                      input.toString(retval.start,input.LT(-1)));
                  }
                  
                  retval.n = seq.simplify();
                
            }

            }

            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "objDestroyStat"


    // $ANTLR start "lnkInsStat"
    // USE.g:1330:1: lnkInsStat returns [ASTLinkInsertionStatement n] : 'insert' LPAREN p= rValListMin2 RPAREN 'into' ass= IDENT ;
    public final ASTLinkInsertionStatement lnkInsStat() throws RecognitionException {
        ASTLinkInsertionStatement n = null;

        Token ass=null;
        List<ASTRValue> p = null;


        try {
            // USE.g:1331:1: ( 'insert' LPAREN p= rValListMin2 RPAREN 'into' ass= IDENT )
            // USE.g:1332:3: 'insert' LPAREN p= rValListMin2 RPAREN 'into' ass= IDENT
            {
            match(input,98,FOLLOW_98_in_lnkInsStat5337); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_lnkInsStat5341); if (state.failed) return n;
            pushFollow(FOLLOW_rValListMin2_in_lnkInsStat5351);
            p=rValListMin2();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_lnkInsStat5355); if (state.failed) return n;
            match(input,99,FOLLOW_99_in_lnkInsStat5359); if (state.failed) return n;
            ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_lnkInsStat5367); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTLinkInsertionStatement((ass!=null?ass.getText():null), p); 
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
    // $ANTLR end "lnkInsStat"


    // $ANTLR start "lnkDelStat"
    // USE.g:1346:1: lnkDelStat returns [ASTLinkDeletionStatement n] : 'delete' LPAREN p= rValListMin2 RPAREN 'from' ass= IDENT ;
    public final ASTLinkDeletionStatement lnkDelStat() throws RecognitionException {
        ASTLinkDeletionStatement n = null;

        Token ass=null;
        List<ASTRValue> p = null;


        try {
            // USE.g:1347:1: ( 'delete' LPAREN p= rValListMin2 RPAREN 'from' ass= IDENT )
            // USE.g:1348:3: 'delete' LPAREN p= rValListMin2 RPAREN 'from' ass= IDENT
            {
            match(input,100,FOLLOW_100_in_lnkDelStat5391); if (state.failed) return n;
            match(input,LPAREN,FOLLOW_LPAREN_in_lnkDelStat5395); if (state.failed) return n;
            pushFollow(FOLLOW_rValListMin2_in_lnkDelStat5405);
            p=rValListMin2();

            state._fsp--;
            if (state.failed) return n;
            match(input,RPAREN,FOLLOW_RPAREN_in_lnkDelStat5409); if (state.failed) return n;
            match(input,101,FOLLOW_101_in_lnkDelStat5413); if (state.failed) return n;
            ass=(Token)match(input,IDENT,FOLLOW_IDENT_in_lnkDelStat5422); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTLinkDeletionStatement((ass!=null?ass.getText():null), p); 
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
    // $ANTLR end "lnkDelStat"


    // $ANTLR start "condExStat"
    // USE.g:1362:1: condExStat returns [ASTConditionalExecutionStatement n] : 'if' con= inSoilExpression 'then' ts= stat ( 'else' es= stat )? 'end' ;
    public final ASTConditionalExecutionStatement condExStat() throws RecognitionException {
        ASTConditionalExecutionStatement n = null;

        ASTExpression con = null;

        USEParser.stat_return ts = null;

        USEParser.stat_return es = null;



          ASTStatement elseStat = new ASTEmptyStatement();

        try {
            // USE.g:1366:1: ( 'if' con= inSoilExpression 'then' ts= stat ( 'else' es= stat )? 'end' )
            // USE.g:1367:3: 'if' con= inSoilExpression 'then' ts= stat ( 'else' es= stat )? 'end'
            {
            match(input,79,FOLLOW_79_in_condExStat5453); if (state.failed) return n;
            pushFollow(FOLLOW_inSoilExpression_in_condExStat5462);
            con=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            match(input,80,FOLLOW_80_in_condExStat5466); if (state.failed) return n;
            pushFollow(FOLLOW_stat_in_condExStat5475);
            ts=stat();

            state._fsp--;
            if (state.failed) return n;
            // USE.g:1371:3: ( 'else' es= stat )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==81) ) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    // USE.g:1372:5: 'else' es= stat
                    {
                    match(input,81,FOLLOW_81_in_condExStat5486); if (state.failed) return n;
                    pushFollow(FOLLOW_stat_in_condExStat5498);
                    es=stat();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       elseStat = (es!=null?es.n:null); 
                    }

                    }
                    break;

            }

            match(input,51,FOLLOW_51_in_condExStat5509); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTConditionalExecutionStatement(con, (ts!=null?ts.n:null), elseStat); 
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
    // $ANTLR end "condExStat"


    // $ANTLR start "iterStat"
    // USE.g:1383:1: iterStat returns [ASTIterationStatement n] : 'for' var= IDENT 'in' set= inSoilExpression 'do' s= stat 'end' ;
    public final ASTIterationStatement iterStat() throws RecognitionException {
        ASTIterationStatement n = null;

        Token var=null;
        ASTExpression set = null;

        USEParser.stat_return s = null;


        try {
            // USE.g:1384:1: ( 'for' var= IDENT 'in' set= inSoilExpression 'do' s= stat 'end' )
            // USE.g:1385:3: 'for' var= IDENT 'in' set= inSoilExpression 'do' s= stat 'end'
            {
            match(input,102,FOLLOW_102_in_iterStat5534); if (state.failed) return n;
            var=(Token)match(input,IDENT,FOLLOW_IDENT_in_iterStat5542); if (state.failed) return n;
            match(input,67,FOLLOW_67_in_iterStat5546); if (state.failed) return n;
            pushFollow(FOLLOW_inSoilExpression_in_iterStat5554);
            set=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            match(input,103,FOLLOW_103_in_iterStat5558); if (state.failed) return n;
            pushFollow(FOLLOW_stat_in_iterStat5566);
            s=stat();

            state._fsp--;
            if (state.failed) return n;
            match(input,51,FOLLOW_51_in_iterStat5570); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTIterationStatement((var!=null?var.getText():null), set, (s!=null?s.n:null)); 
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
    // $ANTLR end "iterStat"


    // $ANTLR start "callStat"
    // USE.g:1400:1: callStat returns [ASTOperationCallStatement n] : e= inSoilExpression ;
    public final ASTOperationCallStatement callStat() throws RecognitionException {
        ASTOperationCallStatement n = null;

        ASTExpression e = null;


        try {
            // USE.g:1401:1: (e= inSoilExpression )
            // USE.g:1402:3: e= inSoilExpression
            {
            pushFollow(FOLLOW_inSoilExpression_in_callStat5600);
            e=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n = new ASTOperationCallStatement(e); 
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
    // $ANTLR end "callStat"


    // $ANTLR start "nothing"
    // USE.g:1420:1: nothing : ;
    public final void nothing() throws RecognitionException {
        try {
            // USE.g:1421:1: ()
            // USE.g:1422:1: 
            {
            }

        }
        finally {
        }
        return ;
    }
    // $ANTLR end "nothing"

    public static class rValue_return extends ParserRuleReturnScope {
        public ASTRValue n;
    };

    // $ANTLR start "rValue"
    // USE.g:1428:1: rValue returns [ASTRValue n] options {backtrack=true; memoize=true; } : (e= inSoilExpression | loc= lobjCreateStat | 'new' objType= simpleType ( LPAREN objName= inSoilExpression RPAREN )? );
    public final USEParser.rValue_return rValue() throws RecognitionException {
        USEParser.rValue_return retval = new USEParser.rValue_return();
        retval.start = input.LT(1);
        int rValue_StartIndex = input.index();
        ASTExpression e = null;

        ASTNewLinkObjectStatement loc = null;

        ASTSimpleType objType = null;

        ASTExpression objName = null;


        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 75) ) { return retval; }
            // USE.g:1433:1: (e= inSoilExpression | loc= lobjCreateStat | 'new' objType= simpleType ( LPAREN objName= inSoilExpression RPAREN )? )
            int alt89=3;
            alt89 = dfa89.predict(input);
            switch (alt89) {
                case 1 :
                    // USE.g:1434:3: e= inSoilExpression
                    {
                    pushFollow(FOLLOW_inSoilExpression_in_rValue5672);
                    e=inSoilExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       retval.n = new ASTRValueExpressionOrOpCall(e); 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:1437:3: loc= lobjCreateStat
                    {
                    pushFollow(FOLLOW_lobjCreateStat_in_rValue5688);
                    loc=lobjCreateStat();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                       
                          loc.setSourcePosition(((Token)retval.start));
                          loc.setParsedText(input.toString(retval.start,input.LT(-1)));
                          retval.n = new ASTRValueNewLinkObject(loc);
                        
                    }

                    }
                    break;
                case 3 :
                    // USE.g:1444:3: 'new' objType= simpleType ( LPAREN objName= inSoilExpression RPAREN )?
                    {
                    match(input,96,FOLLOW_96_in_rValue5700); if (state.failed) return retval;
                    pushFollow(FOLLOW_simpleType_in_rValue5709);
                    objType=simpleType();

                    state._fsp--;
                    if (state.failed) return retval;
                    // USE.g:1446:3: ( LPAREN objName= inSoilExpression RPAREN )?
                    int alt88=2;
                    int LA88_0 = input.LA(1);

                    if ( (LA88_0==LPAREN) ) {
                        alt88=1;
                    }
                    switch (alt88) {
                        case 1 :
                            // USE.g:1447:5: LPAREN objName= inSoilExpression RPAREN
                            {
                            match(input,LPAREN,FOLLOW_LPAREN_in_rValue5719); if (state.failed) return retval;
                            pushFollow(FOLLOW_inSoilExpression_in_rValue5731);
                            objName=inSoilExpression();

                            state._fsp--;
                            if (state.failed) return retval;
                            match(input,RPAREN,FOLLOW_RPAREN_in_rValue5737); if (state.failed) return retval;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       
                          ASTNewObjectStatement nos = 
                            new ASTNewObjectStatement(objType, objName);
                          
                          nos.setSourcePosition(((Token)retval.start));
                          nos.setParsedText(input.toString(retval.start,input.LT(-1)));
                            
                          retval.n = new ASTRValueNewObject(nos); 
                        
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 75, rValue_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "rValue"


    // $ANTLR start "rValList"
    // USE.g:1466:1: rValList returns [List<ASTRValue> n] : ( nothing | rl= rValListMin1 );
    public final List<ASTRValue> rValList() throws RecognitionException {
        List<ASTRValue> n = null;

        List<ASTRValue> rl = null;


        try {
            // USE.g:1467:1: ( nothing | rl= rValListMin1 )
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==EOF) ) {
                alt90=1;
            }
            else if ( (LA90_0==IDENT||LA90_0==INT||LA90_0==LPAREN||(LA90_0>=PLUS && LA90_0<=MINUS)||LA90_0==AT||(LA90_0>=REAL && LA90_0<=HASH)||LA90_0==66||LA90_0==73||(LA90_0>=75 && LA90_0<=79)||(LA90_0>=83 && LA90_0<=94)||LA90_0==96) ) {
                alt90=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 90, 0, input);

                throw nvae;
            }
            switch (alt90) {
                case 1 :
                    // USE.g:1468:3: nothing
                    {
                    pushFollow(FOLLOW_nothing_in_rValList5765);
                    nothing();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ArrayList<ASTRValue>(); 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:1471:3: rl= rValListMin1
                    {
                    pushFollow(FOLLOW_rValListMin1_in_rValList5792);
                    rl=rValListMin1();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = rl; 
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
    // $ANTLR end "rValList"


    // $ANTLR start "rValListMin1"
    // USE.g:1479:1: rValListMin1 returns [List<ASTRValue> n] : r= rValue ( COMMA r= rValue )* ;
    public final List<ASTRValue> rValListMin1() throws RecognitionException {
        List<ASTRValue> n = null;

        USEParser.rValue_return r = null;



          n = new ArrayList<ASTRValue>();

        try {
            // USE.g:1483:1: (r= rValue ( COMMA r= rValue )* )
            // USE.g:1484:3: r= rValue ( COMMA r= rValue )*
            {
            pushFollow(FOLLOW_rValue_in_rValListMin15825);
            r=rValue();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.add((r!=null?r.n:null)); 
            }
            // USE.g:1486:3: ( COMMA r= rValue )*
            loop91:
            do {
                int alt91=2;
                int LA91_0 = input.LA(1);

                if ( (LA91_0==COMMA) ) {
                    alt91=1;
                }


                switch (alt91) {
            	case 1 :
            	    // USE.g:1487:5: COMMA r= rValue
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_rValListMin15839); if (state.failed) return n;
            	    pushFollow(FOLLOW_rValue_in_rValListMin15849);
            	    r=rValue();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.add((r!=null?r.n:null)); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop91;
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
    // $ANTLR end "rValListMin1"


    // $ANTLR start "rValListMin2"
    // USE.g:1497:1: rValListMin2 returns [List<ASTRValue> n] : r= rValue COMMA r= rValue ( COMMA r= rValue )* ;
    public final List<ASTRValue> rValListMin2() throws RecognitionException {
        List<ASTRValue> n = null;

        USEParser.rValue_return r = null;



          n = new ArrayList<ASTRValue>();

        try {
            // USE.g:1501:1: (r= rValue COMMA r= rValue ( COMMA r= rValue )* )
            // USE.g:1502:3: r= rValue COMMA r= rValue ( COMMA r= rValue )*
            {
            pushFollow(FOLLOW_rValue_in_rValListMin25888);
            r=rValue();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.add((r!=null?r.n:null)); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_rValListMin25896); if (state.failed) return n;
            pushFollow(FOLLOW_rValue_in_rValListMin25904);
            r=rValue();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.add((r!=null?r.n:null)); 
            }
            // USE.g:1507:3: ( COMMA r= rValue )*
            loop92:
            do {
                int alt92=2;
                int LA92_0 = input.LA(1);

                if ( (LA92_0==COMMA) ) {
                    alt92=1;
                }


                switch (alt92) {
            	case 1 :
            	    // USE.g:1508:5: COMMA r= rValue
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_rValListMin25918); if (state.failed) return n;
            	    pushFollow(FOLLOW_rValue_in_rValListMin25928);
            	    r=rValue();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       n.add((r!=null?r.n:null)); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop92;
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
    // $ANTLR end "rValListMin2"


    // $ANTLR start "inSoilExpression"
    // USE.g:1518:1: inSoilExpression returns [ASTExpression n] : e= expression ;
    public final ASTExpression inSoilExpression() throws RecognitionException {
        ASTExpression n = null;

        USEParser.expression_return e = null;


        try {
            // USE.g:1519:1: (e= expression )
            // USE.g:1520:3: e= expression
            {
            pushFollow(FOLLOW_expression_in_inSoilExpression5962);
            e=expression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               if ((e!=null?e.n:null) != null) (e!=null?e.n:null).setStringRep((e!=null?input.toString(e.start,e.stop):null)); 
            }
            if ( state.backtracking==0 ) {
               n = (e!=null?e.n:null); 
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
    // $ANTLR end "inSoilExpression"


    // $ANTLR start "exprList"
    // USE.g:1529:1: exprList returns [List<ASTExpression> n] : ( nothing | el= exprListMin1 );
    public final List<ASTExpression> exprList() throws RecognitionException {
        List<ASTExpression> n = null;

        List<ASTExpression> el = null;


        try {
            // USE.g:1530:1: ( nothing | el= exprListMin1 )
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==RPAREN) ) {
                alt93=1;
            }
            else if ( (LA93_0==IDENT||LA93_0==INT||LA93_0==LPAREN||(LA93_0>=PLUS && LA93_0<=MINUS)||LA93_0==AT||(LA93_0>=REAL && LA93_0<=HASH)||LA93_0==66||LA93_0==73||(LA93_0>=75 && LA93_0<=79)||(LA93_0>=83 && LA93_0<=94)) ) {
                alt93=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 93, 0, input);

                throw nvae;
            }
            switch (alt93) {
                case 1 :
                    // USE.g:1531:3: nothing
                    {
                    pushFollow(FOLLOW_nothing_in_exprList5991);
                    nothing();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ArrayList<ASTExpression>(); 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:1534:3: el= exprListMin1
                    {
                    pushFollow(FOLLOW_exprListMin1_in_exprList6009);
                    el=exprListMin1();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = el; 
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
    // $ANTLR end "exprList"


    // $ANTLR start "exprListMin1"
    // USE.g:1542:1: exprListMin1 returns [List<ASTExpression> n] : e= inSoilExpression ( COMMA e= inSoilExpression )* ;
    public final List<ASTExpression> exprListMin1() throws RecognitionException {
        List<ASTExpression> n = null;

        ASTExpression e = null;



          n = new ArrayList<ASTExpression>();

        try {
            // USE.g:1546:1: (e= inSoilExpression ( COMMA e= inSoilExpression )* )
            // USE.g:1547:3: e= inSoilExpression ( COMMA e= inSoilExpression )*
            {
            pushFollow(FOLLOW_inSoilExpression_in_exprListMin16042);
            e=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               if (e != null) n.add(e); 
            }
            // USE.g:1549:3: ( COMMA e= inSoilExpression )*
            loop94:
            do {
                int alt94=2;
                int LA94_0 = input.LA(1);

                if ( (LA94_0==COMMA) ) {
                    alt94=1;
                }


                switch (alt94) {
            	case 1 :
            	    // USE.g:1550:5: COMMA e= inSoilExpression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_exprListMin16057); if (state.failed) return n;
            	    pushFollow(FOLLOW_inSoilExpression_in_exprListMin16067);
            	    e=inSoilExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       if (e != null) n.add(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop94;
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
    // $ANTLR end "exprListMin1"


    // $ANTLR start "exprListMin2"
    // USE.g:1560:1: exprListMin2 returns [List<ASTExpression> n] : e= inSoilExpression COMMA e= inSoilExpression ( COMMA e= inSoilExpression )* ;
    public final List<ASTExpression> exprListMin2() throws RecognitionException {
        List<ASTExpression> n = null;

        ASTExpression e = null;



          n = new ArrayList<ASTExpression>();

        try {
            // USE.g:1564:1: (e= inSoilExpression COMMA e= inSoilExpression ( COMMA e= inSoilExpression )* )
            // USE.g:1565:3: e= inSoilExpression COMMA e= inSoilExpression ( COMMA e= inSoilExpression )*
            {
            pushFollow(FOLLOW_inSoilExpression_in_exprListMin26107);
            e=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               if (e != null) n.add(e); 
            }
            match(input,COMMA,FOLLOW_COMMA_in_exprListMin26115); if (state.failed) return n;
            pushFollow(FOLLOW_inSoilExpression_in_exprListMin26123);
            e=inSoilExpression();

            state._fsp--;
            if (state.failed) return n;
            if ( state.backtracking==0 ) {
               if (e != null) n.add(e); 
            }
            // USE.g:1570:3: ( COMMA e= inSoilExpression )*
            loop95:
            do {
                int alt95=2;
                int LA95_0 = input.LA(1);

                if ( (LA95_0==COMMA) ) {
                    alt95=1;
                }


                switch (alt95) {
            	case 1 :
            	    // USE.g:1571:5: COMMA e= inSoilExpression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_exprListMin26137); if (state.failed) return n;
            	    pushFollow(FOLLOW_inSoilExpression_in_exprListMin26147);
            	    e=inSoilExpression();

            	    state._fsp--;
            	    if (state.failed) return n;
            	    if ( state.backtracking==0 ) {
            	       if (e != null) n.add(e); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop95;
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
    // $ANTLR end "exprListMin2"


    // $ANTLR start "identList"
    // USE.g:1581:1: identList returns [List<String> n] : ( nothing | il= identListMin1 );
    public final List<String> identList() throws RecognitionException {
        List<String> n = null;

        List<String> il = null;


        try {
            // USE.g:1582:1: ( nothing | il= identListMin1 )
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==EOF||LA96_0==SEMI||LA96_0==RPAREN||LA96_0==51||LA96_0==81) ) {
                alt96=1;
            }
            else if ( (LA96_0==IDENT) ) {
                alt96=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return n;}
                NoViableAltException nvae =
                    new NoViableAltException("", 96, 0, input);

                throw nvae;
            }
            switch (alt96) {
                case 1 :
                    // USE.g:1583:3: nothing
                    {
                    pushFollow(FOLLOW_nothing_in_identList6177);
                    nothing();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = new ArrayList<String>(); 
                    }

                    }
                    break;
                case 2 :
                    // USE.g:1586:3: il= identListMin1
                    {
                    pushFollow(FOLLOW_identListMin1_in_identList6194);
                    il=identListMin1();

                    state._fsp--;
                    if (state.failed) return n;
                    if ( state.backtracking==0 ) {
                       n = il; 
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
    // $ANTLR end "identList"


    // $ANTLR start "identListMin1"
    // USE.g:1594:1: identListMin1 returns [List<String> n] : id= IDENT ( COMMA id= IDENT )* ;
    public final List<String> identListMin1() throws RecognitionException {
        List<String> n = null;

        Token id=null;


          n = new ArrayList<String>();

        try {
            // USE.g:1598:1: (id= IDENT ( COMMA id= IDENT )* )
            // USE.g:1599:3: id= IDENT ( COMMA id= IDENT )*
            {
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_identListMin16228); if (state.failed) return n;
            if ( state.backtracking==0 ) {
               n.add((id!=null?id.getText():null)); 
            }
            // USE.g:1601:3: ( COMMA id= IDENT )*
            loop97:
            do {
                int alt97=2;
                int LA97_0 = input.LA(1);

                if ( (LA97_0==COMMA) ) {
                    alt97=1;
                }


                switch (alt97) {
            	case 1 :
            	    // USE.g:1602:5: COMMA id= IDENT
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_identListMin16242); if (state.failed) return n;
            	    id=(Token)match(input,IDENT,FOLLOW_IDENT_in_identListMin16252); if (state.failed) return n;
            	    if ( state.backtracking==0 ) {

            	          n.add((id!=null?id.getText():null)); 
            	    }

            	    }
            	    break;

            	default :
            	    break loop97;
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
    // $ANTLR end "identListMin1"

    // $ANTLR start synpred1_USE
    public final void synpred1_USE_fragment() throws RecognitionException {   
        // USE.g:948:7: ( COLON type EQUAL )
        // USE.g:948:8: COLON type EQUAL
        {
        match(input,COLON,FOLLOW_COLON_in_synpred1_USE4131); if (state.failed) return ;
        pushFollow(FOLLOW_type_in_synpred1_USE4133);
        type();

        state._fsp--;
        if (state.failed) return ;
        match(input,EQUAL,FOLLOW_EQUAL_in_synpred1_USE4135); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_USE

    // $ANTLR start synpred3_USE
    public final void synpred3_USE_fragment() throws RecognitionException {   
        USEParser.varAssignStat_return vas = null;


        // USE.g:1132:5: (vas= varAssignStat )
        // USE.g:1132:5: vas= varAssignStat
        {
        pushFollow(FOLLOW_varAssignStat_in_synpred3_USE4763);
        vas=varAssignStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_USE

    // $ANTLR start synpred4_USE
    public final void synpred4_USE_fragment() throws RecognitionException {   
        ASTAttributeAssignmentStatement aas = null;


        // USE.g:1133:5: (aas= attAssignStat )
        // USE.g:1133:5: aas= attAssignStat
        {
        pushFollow(FOLLOW_attAssignStat_in_synpred4_USE4777);
        aas=attAssignStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_USE

    // $ANTLR start synpred5_USE
    public final void synpred5_USE_fragment() throws RecognitionException {   
        ASTNewLinkObjectStatement lcs = null;


        // USE.g:1134:5: (lcs= lobjCreateStat )
        // USE.g:1134:5: lcs= lobjCreateStat
        {
        pushFollow(FOLLOW_lobjCreateStat_in_synpred5_USE4791);
        lcs=lobjCreateStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_USE

    // $ANTLR start synpred6_USE
    public final void synpred6_USE_fragment() throws RecognitionException {   
        USEParser.objCreateStat_return ocs = null;


        // USE.g:1135:5: (ocs= objCreateStat )
        // USE.g:1135:5: ocs= objCreateStat
        {
        pushFollow(FOLLOW_objCreateStat_in_synpred6_USE4804);
        ocs=objCreateStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_USE

    // $ANTLR start synpred10_USE
    public final void synpred10_USE_fragment() throws RecognitionException {   
        ASTConditionalExecutionStatement ces = null;


        // USE.g:1139:5: (ces= condExStat )
        // USE.g:1139:5: ces= condExStat
        {
        pushFollow(FOLLOW_condExStat_in_synpred10_USE4865);
        ces=condExStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred10_USE

    // $ANTLR start synpred12_USE
    public final void synpred12_USE_fragment() throws RecognitionException {   
        Token varName=null;
        USEParser.rValue_return rVal = null;


        // USE.g:1166:3: (varName= IDENT COLON_EQUAL rVal= rValue )
        // USE.g:1166:3: varName= IDENT COLON_EQUAL rVal= rValue
        {
        varName=(Token)match(input,IDENT,FOLLOW_IDENT_in_synpred12_USE4985); if (state.failed) return ;
        match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_synpred12_USE4989); if (state.failed) return ;
        pushFollow(FOLLOW_rValue_in_synpred12_USE4997);
        rVal=rValue();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred12_USE

    // $ANTLR start synpred14_USE
    public final void synpred14_USE_fragment() throws RecognitionException {   
        ASTNewLinkObjectStatement loc = null;


        // USE.g:1437:3: (loc= lobjCreateStat )
        // USE.g:1437:3: loc= lobjCreateStat
        {
        pushFollow(FOLLOW_lobjCreateStat_in_synpred14_USE5688);
        loc=lobjCreateStat();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred14_USE

    // Delegated rules

    public final boolean synpred10_USE() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_USE_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_USE() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_USE_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
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
    public final boolean synpred14_USE() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_USE_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred12_USE() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred12_USE_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_USE() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_USE_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_USE() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_USE_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_USE() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_USE_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA82 dfa82 = new DFA82(this);
    protected DFA89 dfa89 = new DFA89(this);
    static final String DFA82_eotS =
        "\45\uffff";
    static final String DFA82_eofS =
        "\1\1\44\uffff";
    static final String DFA82_minS =
        "\1\4\4\uffff\26\0\12\uffff";
    static final String DFA82_maxS =
        "\1\146\4\uffff\26\0\12\uffff";
    static final String DFA82_acceptS =
        "\1\uffff\1\1\31\uffff\1\6\1\7\1\10\1\12\1\2\1\3\1\13\1\11\1\4\1"+
        "\5";
    static final String DFA82_specialS =
        "\5\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14"+
        "\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\12\uffff}>";
    static final String[] DFA82_transitionS = {
            "\1\5\2\uffff\1\1\7\uffff\1\12\2\uffff\1\30\5\uffff\2\7\3\uffff"+
            "\1\25\1\uffff\1\13\1\14\1\15\21\uffff\1\1\16\uffff\1\6\6\uffff"+
            "\1\7\1\uffff\1\26\3\27\1\31\1\uffff\1\1\1\uffff\1\10\1\11\4"+
            "\16\1\17\1\20\1\21\1\22\1\23\1\24\1\uffff\1\32\1\33\1\34\1\uffff"+
            "\1\35\1\uffff\1\36",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
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

    static final short[] DFA82_eot = DFA.unpackEncodedString(DFA82_eotS);
    static final short[] DFA82_eof = DFA.unpackEncodedString(DFA82_eofS);
    static final char[] DFA82_min = DFA.unpackEncodedStringToUnsignedChars(DFA82_minS);
    static final char[] DFA82_max = DFA.unpackEncodedStringToUnsignedChars(DFA82_maxS);
    static final short[] DFA82_accept = DFA.unpackEncodedString(DFA82_acceptS);
    static final short[] DFA82_special = DFA.unpackEncodedString(DFA82_specialS);
    static final short[][] DFA82_transition;

    static {
        int numStates = DFA82_transitionS.length;
        DFA82_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA82_transition[i] = DFA.unpackEncodedString(DFA82_transitionS[i]);
        }
    }

    class DFA82 extends DFA {

        public DFA82(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 82;
            this.eot = DFA82_eot;
            this.eof = DFA82_eof;
            this.min = DFA82_min;
            this.max = DFA82_max;
            this.accept = DFA82_accept;
            this.special = DFA82_special;
            this.transition = DFA82_transition;
        }
        public String getDescription() {
            return "1124:1: singleStat returns [ASTStatement n] options {backtrack=true; memoize=true; } : (emp= emptyStat | vas= varAssignStat | aas= attAssignStat | lcs= lobjCreateStat | ocs= objCreateStat | ods= objDestroyStat | lis= lnkInsStat | lds= lnkDelStat | ces= condExStat | its= iterStat | ops= callStat );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA82_5 = input.LA(1);

                         
                        int index82_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred3_USE()) ) {s = 31;}

                        else if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_5);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA82_6 = input.LA(1);

                         
                        int index82_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_6);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA82_7 = input.LA(1);

                         
                        int index82_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_7);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA82_8 = input.LA(1);

                         
                        int index82_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_8);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA82_9 = input.LA(1);

                         
                        int index82_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_9);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA82_10 = input.LA(1);

                         
                        int index82_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_10);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA82_11 = input.LA(1);

                         
                        int index82_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_11);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA82_12 = input.LA(1);

                         
                        int index82_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_12);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA82_13 = input.LA(1);

                         
                        int index82_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_13);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA82_14 = input.LA(1);

                         
                        int index82_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_14);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA82_15 = input.LA(1);

                         
                        int index82_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_15);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA82_16 = input.LA(1);

                         
                        int index82_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_16);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA82_17 = input.LA(1);

                         
                        int index82_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_17);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA82_18 = input.LA(1);

                         
                        int index82_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_18);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA82_19 = input.LA(1);

                         
                        int index82_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_19);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA82_20 = input.LA(1);

                         
                        int index82_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_20);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA82_21 = input.LA(1);

                         
                        int index82_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_21);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA82_22 = input.LA(1);

                         
                        int index82_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_22);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA82_23 = input.LA(1);

                         
                        int index82_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_23);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA82_24 = input.LA(1);

                         
                        int index82_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_24);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA82_25 = input.LA(1);

                         
                        int index82_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_USE()) ) {s = 32;}

                        else if ( (synpred10_USE()) ) {s = 34;}

                        else if ( (true) ) {s = 33;}

                         
                        input.seek(index82_25);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA82_26 = input.LA(1);

                         
                        int index82_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_USE()) ) {s = 35;}

                        else if ( (synpred6_USE()) ) {s = 36;}

                         
                        input.seek(index82_26);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 82, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA89_eotS =
        "\31\uffff";
    static final String DFA89_eofS =
        "\31\uffff";
    static final String DFA89_minS =
        "\1\4\25\uffff\1\0\2\uffff";
    static final String DFA89_maxS =
        "\1\140\25\uffff\1\0\2\uffff";
    static final String DFA89_acceptS =
        "\1\uffff\1\1\25\uffff\1\2\1\3";
    static final String DFA89_specialS =
        "\26\uffff\1\0\2\uffff}>";
    static final String[] DFA89_transitionS = {
            "\1\1\12\uffff\1\1\2\uffff\1\1\5\uffff\2\1\3\uffff\1\1\1\uffff"+
            "\3\1\40\uffff\1\1\6\uffff\1\1\1\uffff\5\1\3\uffff\14\1\1\uffff"+
            "\1\26",
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
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA89_eot = DFA.unpackEncodedString(DFA89_eotS);
    static final short[] DFA89_eof = DFA.unpackEncodedString(DFA89_eofS);
    static final char[] DFA89_min = DFA.unpackEncodedStringToUnsignedChars(DFA89_minS);
    static final char[] DFA89_max = DFA.unpackEncodedStringToUnsignedChars(DFA89_maxS);
    static final short[] DFA89_accept = DFA.unpackEncodedString(DFA89_acceptS);
    static final short[] DFA89_special = DFA.unpackEncodedString(DFA89_specialS);
    static final short[][] DFA89_transition;

    static {
        int numStates = DFA89_transitionS.length;
        DFA89_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA89_transition[i] = DFA.unpackEncodedString(DFA89_transitionS[i]);
        }
    }

    class DFA89 extends DFA {

        public DFA89(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 89;
            this.eot = DFA89_eot;
            this.eof = DFA89_eof;
            this.min = DFA89_min;
            this.max = DFA89_max;
            this.accept = DFA89_accept;
            this.special = DFA89_special;
            this.transition = DFA89_transition;
        }
        public String getDescription() {
            return "1428:1: rValue returns [ASTRValue n] options {backtrack=true; memoize=true; } : (e= inSoilExpression | loc= lobjCreateStat | 'new' objType= simpleType ( LPAREN objName= inSoilExpression RPAREN )? );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA89_22 = input.LA(1);

                         
                        int index89_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred14_USE()) ) {s = 23;}

                        else if ( (true) ) {s = 24;}

                         
                        input.seek(index89_22);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 89, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_44_in_model69 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_model73 = new BitSet(new long[]{0x01B1E00000000010L});
    public static final BitSet FOLLOW_enumTypeDefinition_in_model85 = new BitSet(new long[]{0x01B1E00000000010L});
    public static final BitSet FOLLOW_generalClassDefinition_in_model102 = new BitSet(new long[]{0x01B1A00000000010L});
    public static final BitSet FOLLOW_associationDefinition_in_model119 = new BitSet(new long[]{0x01B1A00000000010L});
    public static final BitSet FOLLOW_45_in_model135 = new BitSet(new long[]{0x21B1A00000000010L});
    public static final BitSet FOLLOW_invariant_in_model153 = new BitSet(new long[]{0x21B1A00000000010L});
    public static final BitSet FOLLOW_prePost_in_model174 = new BitSet(new long[]{0x21B1A00000000010L});
    public static final BitSet FOLLOW_EOF_in_model215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_46_in_enumTypeDefinition242 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_enumTypeDefinition246 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LBRACE_in_enumTypeDefinition248 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_enumTypeDefinition252 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RBRACE_in_enumTypeDefinition254 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_enumTypeDefinition258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_47_in_generalClassDefinition297 = new BitSet(new long[]{0x0031800000000000L});
    public static final BitSet FOLLOW_classDefinition_in_generalClassDefinition315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_associationClassDefinition_in_generalClassDefinition335 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_48_in_classDefinition374 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_classDefinition378 = new BitSet(new long[]{0x000E200000000100L});
    public static final BitSet FOLLOW_LESS_in_classDefinition388 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_classDefinition392 = new BitSet(new long[]{0x000E200000000000L});
    public static final BitSet FOLLOW_49_in_classDefinition405 = new BitSet(new long[]{0x000C200000000010L});
    public static final BitSet FOLLOW_attributeDefinition_in_classDefinition418 = new BitSet(new long[]{0x000C200000000010L});
    public static final BitSet FOLLOW_50_in_classDefinition439 = new BitSet(new long[]{0x0008200000000010L});
    public static final BitSet FOLLOW_operationDefinition_in_classDefinition452 = new BitSet(new long[]{0x0008200000000010L});
    public static final BitSet FOLLOW_45_in_classDefinition473 = new BitSet(new long[]{0xC008000000000000L});
    public static final BitSet FOLLOW_invariantClause_in_classDefinition493 = new BitSet(new long[]{0xC008000000000000L});
    public static final BitSet FOLLOW_51_in_classDefinition517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_associationClassDefinition550 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationClassDefinition576 = new BitSet(new long[]{0x0040000000000100L});
    public static final BitSet FOLLOW_LESS_in_associationClassDefinition586 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_idList_in_associationClassDefinition590 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_associationClassDefinition601 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_associationEnd_in_associationClassDefinition609 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_associationEnd_in_associationClassDefinition621 = new BitSet(new long[]{0x018E200000000010L});
    public static final BitSet FOLLOW_49_in_associationClassDefinition634 = new BitSet(new long[]{0x018C200000000010L});
    public static final BitSet FOLLOW_attributeDefinition_in_associationClassDefinition647 = new BitSet(new long[]{0x018C200000000010L});
    public static final BitSet FOLLOW_50_in_associationClassDefinition668 = new BitSet(new long[]{0x0188200000000010L});
    public static final BitSet FOLLOW_operationDefinition_in_associationClassDefinition681 = new BitSet(new long[]{0x0188200000000010L});
    public static final BitSet FOLLOW_45_in_associationClassDefinition702 = new BitSet(new long[]{0xC188000000000000L});
    public static final BitSet FOLLOW_invariantClause_in_associationClassDefinition722 = new BitSet(new long[]{0xC188000000000000L});
    public static final BitSet FOLLOW_set_in_associationClassDefinition756 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_associationClassDefinition785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_attributeDefinition814 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_attributeDefinition816 = new BitSet(new long[]{0x0000000000000010L,0x00000000A1E00000L});
    public static final BitSet FOLLOW_type_in_attributeDefinition820 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_attributeDefinition824 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationDefinition864 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_paramList_in_operationDefinition874 = new BitSet(new long[]{0x0200000000000682L,0x0000000000000003L});
    public static final BitSet FOLLOW_COLON_in_operationDefinition882 = new BitSet(new long[]{0x0000000000000010L,0x00000000A1E00000L});
    public static final BitSet FOLLOW_type_in_operationDefinition888 = new BitSet(new long[]{0x0200000000000482L,0x0000000000000003L});
    public static final BitSet FOLLOW_EQUAL_in_operationDefinition915 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_expression_in_operationDefinition921 = new BitSet(new long[]{0x0000000000000082L,0x0000000000000003L});
    public static final BitSet FOLLOW_57_in_operationDefinition939 = new BitSet(new long[]{0x00000003A3048010L,0x000000577FF8FA04L});
    public static final BitSet FOLLOW_stat_in_operationDefinition945 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_operationDefinition947 = new BitSet(new long[]{0x0000000000000082L,0x0000000000000003L});
    public static final BitSet FOLLOW_prePostClause_in_operationDefinition968 = new BitSet(new long[]{0x0000000000000082L,0x0000000000000003L});
    public static final BitSet FOLLOW_SEMI_in_operationDefinition981 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_keyAssociation_in_associationDefinition1019 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_55_in_associationDefinition1023 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_56_in_associationDefinition1027 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationDefinition1042 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_associationDefinition1050 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_associationEnd_in_associationDefinition1058 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_associationEnd_in_associationDefinition1070 = new BitSet(new long[]{0x0008000000000010L});
    public static final BitSet FOLLOW_51_in_associationDefinition1081 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd1107 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_LBRACK_in_associationEnd1109 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_multiplicity_in_associationEnd1113 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RBRACK_in_associationEnd1115 = new BitSet(new long[]{0x1C00000000000092L});
    public static final BitSet FOLLOW_keyRole_in_associationEnd1126 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd1130 = new BitSet(new long[]{0x1C00000000000092L});
    public static final BitSet FOLLOW_58_in_associationEnd1151 = new BitSet(new long[]{0x1C00000000000092L});
    public static final BitSet FOLLOW_59_in_associationEnd1163 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd1167 = new BitSet(new long[]{0x1C00000000000092L});
    public static final BitSet FOLLOW_keyUnion_in_associationEnd1179 = new BitSet(new long[]{0x1C00000000000092L});
    public static final BitSet FOLLOW_60_in_associationEnd1191 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd1195 = new BitSet(new long[]{0x1C00000000000092L});
    public static final BitSet FOLLOW_SEMI_in_associationEnd1212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity1247 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_COMMA_in_multiplicity1257 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity1261 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange1290 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_DOTDOT_in_multiplicityRange1300 = new BitSet(new long[]{0x0000000000018000L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange1304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_multiplicitySpec1338 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_multiplicitySpec1348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_invariant1389 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_invariant1399 = new BitSet(new long[]{0x0000000000002200L});
    public static final BitSet FOLLOW_COMMA_in_invariant1412 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_invariant1416 = new BitSet(new long[]{0x0000000000002200L});
    public static final BitSet FOLLOW_COLON_in_invariant1424 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_simpleType_in_invariant1436 = new BitSet(new long[]{0xC000000000000002L});
    public static final BitSet FOLLOW_invariantClause_in_invariant1448 = new BitSet(new long[]{0xC000000000000002L});
    public static final BitSet FOLLOW_62_in_invariantClause1479 = new BitSet(new long[]{0x0000000000000210L});
    public static final BitSet FOLLOW_IDENT_in_invariantClause1485 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_invariantClause1490 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_expression_in_invariantClause1494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_63_in_invariantClause1504 = new BitSet(new long[]{0x4000000000000000L});
    public static final BitSet FOLLOW_62_in_invariantClause1506 = new BitSet(new long[]{0x0000000000000210L});
    public static final BitSet FOLLOW_IDENT_in_invariantClause1512 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_invariantClause1517 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_expression_in_invariantClause1521 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_61_in_prePost1547 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_prePost1551 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_COLON_COLON_in_prePost1553 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_prePost1557 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_paramList_in_prePost1561 = new BitSet(new long[]{0x0000000000000200L,0x0000000000000003L});
    public static final BitSet FOLLOW_COLON_in_prePost1565 = new BitSet(new long[]{0x0000000000000010L,0x00000000A1E00000L});
    public static final BitSet FOLLOW_type_in_prePost1569 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_prePostClause_in_prePost1588 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000003L});
    public static final BitSet FOLLOW_set_in_prePostClause1627 = new BitSet(new long[]{0x0000000000000210L});
    public static final BitSet FOLLOW_IDENT_in_prePostClause1642 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_prePostClause1647 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_expression_in_prePostClause1651 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_keyUnion1673 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_keyAssociation1687 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_keyRole1701 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_expressionOnly1734 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_expressionOnly1736 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_66_in_expression1784 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_expression1788 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COLON_in_expression1792 = new BitSet(new long[]{0x0000000000000010L,0x00000000A1E00000L});
    public static final BitSet FOLLOW_type_in_expression1796 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_expression1801 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_expression_in_expression1805 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_expression1807 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_conditionalImpliesExpression_in_expression1832 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_paramList1865 = new BitSet(new long[]{0x0000000000080010L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList1882 = new BitSet(new long[]{0x0000000000082000L});
    public static final BitSet FOLLOW_COMMA_in_paramList1894 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableDeclaration_in_paramList1898 = new BitSet(new long[]{0x0000000000082000L});
    public static final BitSet FOLLOW_RPAREN_in_paramList1918 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_idList1947 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_COMMA_in_idList1957 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_idList1961 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_IDENT_in_variableDeclaration1992 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_variableDeclaration1994 = new BitSet(new long[]{0x0000000000000010L,0x00000000A1E00000L});
    public static final BitSet FOLLOW_type_in_variableDeclaration1998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2034 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_68_in_conditionalImpliesExpression2047 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_conditionalOrExpression_in_conditionalImpliesExpression2051 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000010L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2096 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_69_in_conditionalOrExpression2109 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_conditionalXOrExpression_in_conditionalOrExpression2113 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000020L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2157 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_70_in_conditionalXOrExpression2170 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_conditionalAndExpression_in_conditionalXOrExpression2174 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000040L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression2218 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_71_in_conditionalAndExpression2231 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_equalityExpression_in_conditionalAndExpression2235 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000080L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression2283 = new BitSet(new long[]{0x0000000000100402L});
    public static final BitSet FOLLOW_set_in_equalityExpression2302 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_relationalExpression_in_equalityExpression2312 = new BitSet(new long[]{0x0000000000100402L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression2361 = new BitSet(new long[]{0x0000000000E00102L});
    public static final BitSet FOLLOW_set_in_relationalExpression2379 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression2397 = new BitSet(new long[]{0x0000000000E00102L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression2447 = new BitSet(new long[]{0x0000000003000002L});
    public static final BitSet FOLLOW_set_in_additiveExpression2465 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression2475 = new BitSet(new long[]{0x0000000003000002L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression2525 = new BitSet(new long[]{0x0000000004010002L,0x0000000000000100L});
    public static final BitSet FOLLOW_set_in_multiplicativeExpression2543 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression2557 = new BitSet(new long[]{0x0000000004010002L,0x0000000000000100L});
    public static final BitSet FOLLOW_set_in_unaryExpression2619 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_unaryExpression_in_unaryExpression2643 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpression_in_unaryExpression2663 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_primaryExpression_in_postfixExpression2696 = new BitSet(new long[]{0x0000000018000002L});
    public static final BitSet FOLLOW_ARROW_in_postfixExpression2714 = new BitSet(new long[]{0x0000000000000010L,0x0000000000007800L});
    public static final BitSet FOLLOW_DOT_in_postfixExpression2720 = new BitSet(new long[]{0x0000000000000010L,0x0000000000007800L});
    public static final BitSet FOLLOW_propertyCall_in_postfixExpression2731 = new BitSet(new long[]{0x0000000018000002L});
    public static final BitSet FOLLOW_literal_in_primaryExpression2771 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objectReference_in_primaryExpression2785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_propertyCall_in_primaryExpression2797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression2808 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_expression_in_primaryExpression2812 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression2814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifExpression_in_primaryExpression2826 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_primaryExpression2843 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_DOT_in_primaryExpression2845 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_74_in_primaryExpression2847 = new BitSet(new long[]{0x0000000020040002L});
    public static final BitSet FOLLOW_LPAREN_in_primaryExpression2851 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_RPAREN_in_primaryExpression2853 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_AT_in_primaryExpression2874 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_primaryExpression2876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_in_objectReference2903 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_objectReference2911 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_queryExpression_in_propertyCall2979 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterateExpression_in_propertyCall2992 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationExpression_in_propertyCall3005 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_typeExpression_in_propertyCall3018 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_queryExpression3053 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_LPAREN_in_queryExpression3060 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_queryExpression3071 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_BAR_in_queryExpression3075 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_expression_in_queryExpression3086 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_RPAREN_in_queryExpression3092 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_iterateExpression3124 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_LPAREN_in_iterateExpression3130 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_elemVarsDeclaration_in_iterateExpression3138 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_SEMI_in_iterateExpression3140 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_variableInitialization_in_iterateExpression3148 = new BitSet(new long[]{0x0000000040000000L});
    public static final BitSet FOLLOW_BAR_in_iterateExpression3150 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_expression_in_iterateExpression3158 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_RPAREN_in_iterateExpression3164 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression3208 = new BitSet(new long[]{0x0000000020040802L});
    public static final BitSet FOLLOW_LBRACK_in_operationExpression3224 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_operationExpression3228 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_RBRACK_in_operationExpression3230 = new BitSet(new long[]{0x0000000020040002L});
    public static final BitSet FOLLOW_AT_in_operationExpression3243 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_64_in_operationExpression3245 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_LPAREN_in_operationExpression3266 = new BitSet(new long[]{0x00000003A30C8010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_expression_in_operationExpression3287 = new BitSet(new long[]{0x0000000000082000L});
    public static final BitSet FOLLOW_COMMA_in_operationExpression3299 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_expression_in_operationExpression3303 = new BitSet(new long[]{0x0000000000082000L});
    public static final BitSet FOLLOW_RPAREN_in_operationExpression3323 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_typeExpression3372 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_LPAREN_in_typeExpression3388 = new BitSet(new long[]{0x0000000000000010L,0x00000000A1E00000L});
    public static final BitSet FOLLOW_type_in_typeExpression3392 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_RPAREN_in_typeExpression3394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_idList_in_elemVarsDeclaration3433 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_COLON_in_elemVarsDeclaration3441 = new BitSet(new long[]{0x0000000000000010L,0x00000000A1E00000L});
    public static final BitSet FOLLOW_type_in_elemVarsDeclaration3445 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableInitialization3480 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_variableInitialization3482 = new BitSet(new long[]{0x0000000000000010L,0x00000000A1E00000L});
    public static final BitSet FOLLOW_type_in_variableInitialization3486 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_variableInitialization3488 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_expression_in_variableInitialization3492 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_ifExpression3524 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_expression_in_ifExpression3528 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_ifExpression3530 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_expression_in_ifExpression3534 = new BitSet(new long[]{0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_ifExpression3536 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_expression_in_ifExpression3540 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_ifExpression3542 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_83_in_literal3581 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_84_in_literal3595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_literal3608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_REAL_in_literal3623 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_literal3637 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_HASH_in_literal3647 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_literal3651 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_literal3663 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_COLON_COLON_in_literal3665 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_literal3669 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionLiteral_in_literal3681 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyCollectionLiteral_in_literal3693 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_undefinedLiteral_in_literal3705 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleLiteral_in_literal3717 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dateLiteral_in_literal3729 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionLiteral3767 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LBRACE_in_collectionLiteral3796 = new BitSet(new long[]{0x00000003A3048050L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral3813 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_COMMA_in_collectionLiteral3826 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_collectionItem_in_collectionLiteral3830 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_RBRACE_in_collectionLiteral3849 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_collectionItem3878 = new BitSet(new long[]{0x0000000000004002L});
    public static final BitSet FOLLOW_DOTDOT_in_collectionItem3889 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_expression_in_collectionItem3893 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_89_in_emptyCollectionLiteral3922 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_LPAREN_in_emptyCollectionLiteral3924 = new BitSet(new long[]{0x0000000000000000L,0x0000000081E00000L});
    public static final BitSet FOLLOW_collectionType_in_emptyCollectionLiteral3928 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_RPAREN_in_emptyCollectionLiteral3930 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_90_in_undefinedLiteral3960 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_LPAREN_in_undefinedLiteral3962 = new BitSet(new long[]{0x0000000000000010L,0x00000000A1E00000L});
    public static final BitSet FOLLOW_type_in_undefinedLiteral3966 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_RPAREN_in_undefinedLiteral3968 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_91_in_undefinedLiteral3982 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_undefinedLiteral3996 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_tupleLiteral4030 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LBRACE_in_tupleLiteral4036 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral4044 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_COMMA_in_tupleLiteral4055 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tupleItem_in_tupleLiteral4059 = new BitSet(new long[]{0x0000000000002040L});
    public static final BitSet FOLLOW_RBRACE_in_tupleLiteral4070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tupleItem4101 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_COLON_in_tupleItem4140 = new BitSet(new long[]{0x0000000000000010L,0x00000000A1E00000L});
    public static final BitSet FOLLOW_type_in_tupleItem4144 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_tupleItem4146 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_expression_in_tupleItem4150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_tupleItem4172 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_expression_in_tupleItem4182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_dateLiteral4227 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_LBRACE_in_dateLiteral4229 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_STRING_in_dateLiteral4233 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_RBRACE_in_dateLiteral4235 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simpleType_in_type4285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_type4297 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_tupleType_in_type4309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_type_in_typeOnly4341 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_typeOnly4343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_simpleType4371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_collectionType4409 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_LPAREN_in_collectionType4436 = new BitSet(new long[]{0x0000000000000010L,0x00000000A1E00000L});
    public static final BitSet FOLLOW_type_in_collectionType4440 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_RPAREN_in_collectionType4442 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_tupleType4476 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_LPAREN_in_tupleType4478 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType4487 = new BitSet(new long[]{0x0000000000082000L});
    public static final BitSet FOLLOW_COMMA_in_tupleType4498 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_tuplePart_in_tupleType4502 = new BitSet(new long[]{0x0000000000082000L});
    public static final BitSet FOLLOW_RPAREN_in_tupleType4514 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_tuplePart4546 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_COLON_in_tuplePart4548 = new BitSet(new long[]{0x0000000000000010L,0x00000000A1E00000L});
    public static final BitSet FOLLOW_type_in_tuplePart4552 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stat_in_statOnly4601 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_statOnly4605 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nextStat_in_stat4636 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_stat4647 = new BitSet(new long[]{0x00000003A3048010L,0x000000577FF8FA04L});
    public static final BitSet FOLLOW_nextStat_in_stat4653 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_singleStat_in_nextStat4687 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_emptyStat_in_singleStat4742 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varAssignStat_in_singleStat4763 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attAssignStat_in_singleStat4777 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lobjCreateStat_in_singleStat4791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objCreateStat_in_singleStat4804 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objDestroyStat_in_singleStat4818 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lnkInsStat_in_singleStat4831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lnkDelStat_in_singleStat4848 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_condExStat_in_singleStat4865 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_iterStat_in_singleStat4882 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_callStat_in_singleStat4901 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nothing_in_emptyStat4932 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_varAssignStat4985 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_varAssignStat4989 = new BitSet(new long[]{0x00000003A3048010L,0x000000017FF8FA04L});
    public static final BitSet FOLLOW_rValue_in_varAssignStat4997 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identListMin1_in_varAssignStat5016 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_varAssignStat5020 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_96_in_varAssignStat5024 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_simpleType_in_varAssignStat5032 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_LPAREN_in_varAssignStat5042 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_exprList_in_varAssignStat5054 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_RPAREN_in_varAssignStat5060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_attAssignStat5095 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_DOT_in_attAssignStat5100 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_attAssignStat5109 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_attAssignStat5113 = new BitSet(new long[]{0x00000003A3048010L,0x000000017FF8FA04L});
    public static final BitSet FOLLOW_rValue_in_attAssignStat5121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_objCreateStat5147 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_simpleType_in_objCreateStat5155 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_LPAREN_in_objCreateStat5165 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_exprListMin1_in_objCreateStat5177 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_RPAREN_in_objCreateStat5183 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_lobjCreateStat5214 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_lobjCreateStat5222 = new BitSet(new long[]{0x0040000000040000L});
    public static final BitSet FOLLOW_LPAREN_in_lobjCreateStat5232 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_inSoilExpression_in_lobjCreateStat5244 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_RPAREN_in_lobjCreateStat5250 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_54_in_lobjCreateStat5259 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_LPAREN_in_lobjCreateStat5263 = new BitSet(new long[]{0x00000003A3048010L,0x000000017FF8FA04L});
    public static final BitSet FOLLOW_rValListMin2_in_lobjCreateStat5273 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_RPAREN_in_lobjCreateStat5277 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_97_in_objDestroyStat5303 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_exprListMin1_in_objDestroyStat5311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_lnkInsStat5337 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_LPAREN_in_lnkInsStat5341 = new BitSet(new long[]{0x00000003A3048010L,0x000000017FF8FA04L});
    public static final BitSet FOLLOW_rValListMin2_in_lnkInsStat5351 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_RPAREN_in_lnkInsStat5355 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_99_in_lnkInsStat5359 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_lnkInsStat5367 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_100_in_lnkDelStat5391 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_LPAREN_in_lnkDelStat5395 = new BitSet(new long[]{0x00000003A3048010L,0x000000017FF8FA04L});
    public static final BitSet FOLLOW_rValListMin2_in_lnkDelStat5405 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_RPAREN_in_lnkDelStat5409 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_lnkDelStat5413 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_lnkDelStat5422 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_79_in_condExStat5453 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_inSoilExpression_in_condExStat5462 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_80_in_condExStat5466 = new BitSet(new long[]{0x00000003A3048010L,0x000000577FF8FA04L});
    public static final BitSet FOLLOW_stat_in_condExStat5475 = new BitSet(new long[]{0x0008000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_81_in_condExStat5486 = new BitSet(new long[]{0x00000003A3048010L,0x000000577FF8FA04L});
    public static final BitSet FOLLOW_stat_in_condExStat5498 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_condExStat5509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_102_in_iterStat5534 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_iterStat5542 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_67_in_iterStat5546 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_inSoilExpression_in_iterStat5554 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_103_in_iterStat5558 = new BitSet(new long[]{0x00000003A3048010L,0x000000577FF8FA04L});
    public static final BitSet FOLLOW_stat_in_iterStat5566 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_51_in_iterStat5570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_callStat5600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_rValue5672 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lobjCreateStat_in_rValue5688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_rValue5700 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_simpleType_in_rValue5709 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_LPAREN_in_rValue5719 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_inSoilExpression_in_rValue5731 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_RPAREN_in_rValue5737 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nothing_in_rValList5765 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rValListMin1_in_rValList5792 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rValue_in_rValListMin15825 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_COMMA_in_rValListMin15839 = new BitSet(new long[]{0x00000003A3048010L,0x000000017FF8FA04L});
    public static final BitSet FOLLOW_rValue_in_rValListMin15849 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_rValue_in_rValListMin25888 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_COMMA_in_rValListMin25896 = new BitSet(new long[]{0x00000003A3048010L,0x000000017FF8FA04L});
    public static final BitSet FOLLOW_rValue_in_rValListMin25904 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_COMMA_in_rValListMin25918 = new BitSet(new long[]{0x00000003A3048010L,0x000000017FF8FA04L});
    public static final BitSet FOLLOW_rValue_in_rValListMin25928 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_expression_in_inSoilExpression5962 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nothing_in_exprList5991 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exprListMin1_in_exprList6009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin16042 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_COMMA_in_exprListMin16057 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin16067 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin26107 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_COMMA_in_exprListMin26115 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin26123 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_COMMA_in_exprListMin26137 = new BitSet(new long[]{0x00000003A3048010L,0x000000007FF8FA04L});
    public static final BitSet FOLLOW_inSoilExpression_in_exprListMin26147 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_nothing_in_identList6177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_identListMin1_in_identList6194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_identListMin16228 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_COMMA_in_identListMin16242 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_identListMin16252 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_COLON_in_synpred1_USE4131 = new BitSet(new long[]{0x0000000000000010L,0x00000000A1E00000L});
    public static final BitSet FOLLOW_type_in_synpred1_USE4133 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_EQUAL_in_synpred1_USE4135 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_varAssignStat_in_synpred3_USE4763 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attAssignStat_in_synpred4_USE4777 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lobjCreateStat_in_synpred5_USE4791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_objCreateStat_in_synpred6_USE4804 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_condExStat_in_synpred10_USE4865 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_synpred12_USE4985 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_synpred12_USE4989 = new BitSet(new long[]{0x00000003A3048010L,0x000000017FF8FA04L});
    public static final BitSet FOLLOW_rValue_in_synpred12_USE4997 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lobjCreateStat_in_synpred14_USE5688 = new BitSet(new long[]{0x0000000000000002L});

}