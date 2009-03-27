// $ANTLR 3.1b1 GUSEBase.g 2009-03-27 14:16:42
 
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

package org.tzi.use.parser.generator; 

// ------------------------------------
//  USE parser
// ------------------------------------

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.PrintWriter;
import org.tzi.use.parser.ParseErrorHandler;

import  org.tzi.use.parser.ocl.*;import org.tzi.use.parser.use.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class GGenerator_GUSEBase extends Parser {
    public static final int LITERAL_oclAsType=4;
    public static final int STAR=20;
    public static final int EOF=-1;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__91=91;
    public static final int RPAREN=9;
    public static final int T__92=92;
    public static final int GREATER=15;
    public static final int T__90=90;
    public static final int NOT_EQUAL=13;
    public static final int LITERAL_oclIsTypeOf=6;
    public static final int LESS=14;
    public static final int T__99=99;
    public static final int T__98=98;
    public static final int T__97=97;
    public static final int T__96=96;
    public static final int T__95=95;
    public static final int RBRACK=28;
    public static final int T__80=80;
    public static final int T__81=81;
    public static final int RBRACE=34;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int LITERAL_oclIsKindOf=5;
    public static final int INT=29;
    public static final int T__85=85;
    public static final int T__84=84;
    public static final int T__87=87;
    public static final int T__86=86;
    public static final int T__89=89;
    public static final int T__88=88;
    public static final int REAL=30;
    public static final int WS=39;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int T__70=70;
    public static final int SL_COMMENT=40;
    public static final int LESS_EQUAL=16;
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
    public static final int LBRACK=27;
    public static final int T__65=65;
    public static final int T__62=62;
    public static final int T__63=63;
    public static final int ESC=43;
    public static final int LBRACE=33;
    public static final int DOTDOT=35;
    public static final int T__61=61;
    public static final int T__60=60;
    public static final int LPAREN=7;
    public static final int T__55=55;
    public static final int AT=24;
    public static final int ML_COMMENT=41;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int COLON_EQUAL=37;
    public static final int T__51=51;
    public static final int SLASH=21;
    public static final int T__52=52;
    public static final int T__53=53;
    public static final int T__54=54;
    public static final int COMMA=8;
    public static final int T__59=59;
    public static final int T__103=103;
    public static final int T__104=104;
    public static final int EQUAL=12;
    public static final int IDENT=10;
    public static final int PLUS=18;
    public static final int RANGE_OR_INT=42;
    public static final int DOT=23;
    public static final int T__50=50;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int HASH=32;
    public static final int HEX_DIGIT=44;
    public static final int COLON_COLON=36;
    public static final int T__102=102;
    public static final int T__101=101;
    public static final int T__100=100;
    public static final int MINUS=19;
    public static final int SEMI=26;
    public static final int COLON=11;
    public static final int NEWLINE=38;
    public static final int VOCAB=45;
    public static final int ARROW=22;
    public static final int GREATER_EQUAL=17;
    public static final int BAR=25;
    public static final int STRING=31;

    // delegates
    public GGenerator_GUSEBase_GOCLBase gGOCLBase;
    // delegators
    public GGeneratorParser gGGenerator;


        public GGenerator_GUSEBase(TokenStream input, GGeneratorParser gGGenerator) {
            this(input, new RecognizerSharedState(), gGGenerator);
        }
        public GGenerator_GUSEBase(TokenStream input, RecognizerSharedState state, GGeneratorParser gGGenerator) {
            super(input, state);
            this.gGGenerator = gGGenerator;
            gGOCLBase = new GGenerator_GUSEBase_GOCLBase(input, state, gGGenerator, this);gGGenerator.gGOCLBase = gGOCLBase;
        }
        

    public String[] getTokenNames() { return GGeneratorParser.tokenNames; }
    public String getGrammarFileName() { return "GUSEBase.g"; }

    
    	public void init(ParseErrorHandler handler) {
    		fParseErrorHandler = handler;
    		
    		this.gGOCLBase.init(handler);
    	}
    
    	/* Overridden methods. */
    	private ParseErrorHandler fParseErrorHandler;
        
    	public void emitErrorMessage(String msg) {
           	fParseErrorHandler.reportError(msg);
    	}
    	
    	/** Parser warning-reporting function */
        public void reportWarning(String s) {
            if (getSourceName() == null) {
                System.err.println("warning: " + s);
            }
            else {
                System.err.println(getSourceName() + ": warning: " + s);
            }
        }



    // $ANTLR start enumTypeDefinition
    // GUSEBase.g:90:1: enumTypeDefinition returns [ASTEnumTypeDefinition n] : 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )? ;
    public final ASTEnumTypeDefinition enumTypeDefinition() throws RecognitionException {
        ASTEnumTypeDefinition n = null;

        Token name=null;
        List idListRes = null;


        try {
            // GUSEBase.g:91:1: ( 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )? )
            // GUSEBase.g:92:5: 'enum' name= IDENT LBRACE idListRes= idList RBRACE ( SEMI )?
            {
            match(input,70,FOLLOW_70_in_enumTypeDefinition47); 
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_enumTypeDefinition51); 
            match(input,LBRACE,FOLLOW_LBRACE_in_enumTypeDefinition53); 
            pushFollow(FOLLOW_idList_in_enumTypeDefinition57);
            idListRes=gGGenerator.idList();

            state._fsp--;

            match(input,RBRACE,FOLLOW_RBRACE_in_enumTypeDefinition59); 
            // GUSEBase.g:92:54: ( SEMI )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==SEMI) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // GUSEBase.g:92:56: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_enumTypeDefinition63); 

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
    // $ANTLR end enumTypeDefinition


    // $ANTLR start generalClassDefinition
    // GUSEBase.g:103:1: generalClassDefinition[ASTModel n] : ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) ) ;
    public final void generalClassDefinition(ASTModel n) throws RecognitionException {
        ASTClass c = null;

        ASTAssociationClass ac = null;


         
          boolean isAbstract = false;

        try {
            // GUSEBase.g:107:1: ( ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) ) )
            // GUSEBase.g:108:5: ( 'abstract' )? ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) )
            {
            // GUSEBase.g:108:5: ( 'abstract' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==71) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // GUSEBase.g:108:7: 'abstract'
                    {
                    match(input,71,FOLLOW_71_in_generalClassDefinition104); 
                     isAbstract = true; 

                    }
                    break;

            }

            // GUSEBase.g:109:5: ( (c= classDefinition[isAbstract] ) | (ac= associationClassDefinition[isAbstract] ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==72) ) {
                alt3=1;
            }
            else if ( ((LA3_0>=77 && LA3_0<=78)) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // GUSEBase.g:109:7: (c= classDefinition[isAbstract] )
                    {
                    // GUSEBase.g:109:7: (c= classDefinition[isAbstract] )
                    // GUSEBase.g:109:9: c= classDefinition[isAbstract]
                    {
                    pushFollow(FOLLOW_classDefinition_in_generalClassDefinition122);
                    c=classDefinition(isAbstract);

                    state._fsp--;

                     n.addClass(c); 

                    }


                    }
                    break;
                case 2 :
                    // GUSEBase.g:110:7: (ac= associationClassDefinition[isAbstract] )
                    {
                    // GUSEBase.g:110:7: (ac= associationClassDefinition[isAbstract] )
                    // GUSEBase.g:110:9: ac= associationClassDefinition[isAbstract]
                    {
                    pushFollow(FOLLOW_associationClassDefinition_in_generalClassDefinition142);
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
    // $ANTLR end generalClassDefinition


    // $ANTLR start classDefinition
    // GUSEBase.g:128:1: classDefinition[boolean isAbstract] returns [ASTClass n] : 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end' ;
    public final ASTClass classDefinition(boolean isAbstract) throws RecognitionException {
        ASTClass n = null;

        Token name=null;
        List idListRes = null;

        ASTAttribute a = null;

        ASTOperation op = null;

        ASTInvariantClause inv = null;


         List idList; 
        try {
            // GUSEBase.g:130:1: ( 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end' )
            // GUSEBase.g:131:5: 'class' name= IDENT ( LESS idListRes= idList )? ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? 'end'
            {
            match(input,72,FOLLOW_72_in_classDefinition182); 
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_classDefinition186); 
             n = new ASTClass(name, isAbstract); 
            // GUSEBase.g:132:5: ( LESS idListRes= idList )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==LESS) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // GUSEBase.g:132:7: LESS idListRes= idList
                    {
                    match(input,LESS,FOLLOW_LESS_in_classDefinition196); 
                    pushFollow(FOLLOW_idList_in_classDefinition200);
                    idListRes=gGGenerator.idList();

                    state._fsp--;

                     n.addSuperClasses(idListRes); 

                    }
                    break;

            }

            // GUSEBase.g:133:5: ( 'attributes' (a= attributeDefinition )* )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==73) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // GUSEBase.g:133:7: 'attributes' (a= attributeDefinition )*
                    {
                    match(input,73,FOLLOW_73_in_classDefinition213); 
                    // GUSEBase.g:134:7: (a= attributeDefinition )*
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( (LA5_0==IDENT) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // GUSEBase.g:134:9: a= attributeDefinition
                    	    {
                    	    pushFollow(FOLLOW_attributeDefinition_in_classDefinition226);
                    	    a=attributeDefinition();

                    	    state._fsp--;

                    	     n.addAttribute(a); 

                    	    }
                    	    break;

                    	default :
                    	    break loop5;
                        }
                    } while (true);


                    }
                    break;

            }

            // GUSEBase.g:136:5: ( 'operations' (op= operationDefinition )* )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==74) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // GUSEBase.g:136:7: 'operations' (op= operationDefinition )*
                    {
                    match(input,74,FOLLOW_74_in_classDefinition247); 
                    // GUSEBase.g:137:7: (op= operationDefinition )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==IDENT) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // GUSEBase.g:137:9: op= operationDefinition
                    	    {
                    	    pushFollow(FOLLOW_operationDefinition_in_classDefinition260);
                    	    op=operationDefinition();

                    	    state._fsp--;

                    	     n.addOperation(op); 

                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);


                    }
                    break;

            }

            // GUSEBase.g:139:5: ( 'constraints' (inv= invariantClause )* )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==75) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // GUSEBase.g:139:7: 'constraints' (inv= invariantClause )*
                    {
                    match(input,75,FOLLOW_75_in_classDefinition281); 
                    // GUSEBase.g:140:7: (inv= invariantClause )*
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( (LA9_0==87) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // GUSEBase.g:141:9: inv= invariantClause
                    	    {
                    	    pushFollow(FOLLOW_invariantClause_in_classDefinition301);
                    	    inv=invariantClause();

                    	    state._fsp--;

                    	     n.addInvariantClause(inv); 

                    	    }
                    	    break;

                    	default :
                    	    break loop9;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,76,FOLLOW_76_in_classDefinition325); 

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
    // $ANTLR end classDefinition


    // $ANTLR start associationClassDefinition
    // GUSEBase.g:163:1: associationClassDefinition[boolean isAbstract] returns [ASTAssociationClass n] : classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end' ;
    public final ASTAssociationClass associationClassDefinition(boolean isAbstract) throws RecognitionException {
        ASTAssociationClass n = null;

        Token classKW=null;
        Token name=null;
        List idListRes = null;

        ASTAssociationEnd ae = null;

        ASTAttribute a = null;

        ASTOperation op = null;

        ASTInvariantClause inv = null;


        List idList; 
        try {
            // GUSEBase.g:165:1: (classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end' )
            // GUSEBase.g:166:5: classKW= ( 'associationClass' | 'associationclass' ) name= IDENT ( LESS idListRes= idList )? 'between' ae= associationEnd (ae= associationEnd )+ ( 'attributes' (a= attributeDefinition )* )? ( 'operations' (op= operationDefinition )* )? ( 'constraints' (inv= invariantClause )* )? ( ( 'aggregation' | 'composition' ) )? 'end'
            {
            classKW=(Token)input.LT(1);
            if ( (input.LA(1)>=77 && input.LA(1)<=78) ) {
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
                
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationClassDefinition385); 
             n = new ASTAssociationClass(name, isAbstract); 
            // GUSEBase.g:175:5: ( LESS idListRes= idList )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==LESS) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // GUSEBase.g:175:7: LESS idListRes= idList
                    {
                    match(input,LESS,FOLLOW_LESS_in_associationClassDefinition395); 
                    pushFollow(FOLLOW_idList_in_associationClassDefinition399);
                    idListRes=gGGenerator.idList();

                    state._fsp--;

                     n.addSuperClasses(idListRes); 

                    }
                    break;

            }

            match(input,79,FOLLOW_79_in_associationClassDefinition410); 
            pushFollow(FOLLOW_associationEnd_in_associationClassDefinition418);
            ae=associationEnd();

            state._fsp--;

             n.addEnd(ae); 
            // GUSEBase.g:178:5: (ae= associationEnd )+
            int cnt12=0;
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==IDENT) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // GUSEBase.g:178:7: ae= associationEnd
            	    {
            	    pushFollow(FOLLOW_associationEnd_in_associationClassDefinition430);
            	    ae=associationEnd();

            	    state._fsp--;

            	     n.addEnd(ae); 

            	    }
            	    break;

            	default :
            	    if ( cnt12 >= 1 ) break loop12;
                        EarlyExitException eee =
                            new EarlyExitException(12, input);
                        throw eee;
                }
                cnt12++;
            } while (true);

            // GUSEBase.g:179:5: ( 'attributes' (a= attributeDefinition )* )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==73) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // GUSEBase.g:179:7: 'attributes' (a= attributeDefinition )*
                    {
                    match(input,73,FOLLOW_73_in_associationClassDefinition443); 
                    // GUSEBase.g:180:7: (a= attributeDefinition )*
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0==IDENT) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // GUSEBase.g:180:9: a= attributeDefinition
                    	    {
                    	    pushFollow(FOLLOW_attributeDefinition_in_associationClassDefinition456);
                    	    a=attributeDefinition();

                    	    state._fsp--;

                    	     n.addAttribute(a); 

                    	    }
                    	    break;

                    	default :
                    	    break loop13;
                        }
                    } while (true);


                    }
                    break;

            }

            // GUSEBase.g:182:5: ( 'operations' (op= operationDefinition )* )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==74) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // GUSEBase.g:182:7: 'operations' (op= operationDefinition )*
                    {
                    match(input,74,FOLLOW_74_in_associationClassDefinition477); 
                    // GUSEBase.g:183:7: (op= operationDefinition )*
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( (LA15_0==IDENT) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // GUSEBase.g:183:9: op= operationDefinition
                    	    {
                    	    pushFollow(FOLLOW_operationDefinition_in_associationClassDefinition490);
                    	    op=operationDefinition();

                    	    state._fsp--;

                    	     n.addOperation(op); 

                    	    }
                    	    break;

                    	default :
                    	    break loop15;
                        }
                    } while (true);


                    }
                    break;

            }

            // GUSEBase.g:185:5: ( 'constraints' (inv= invariantClause )* )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==75) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // GUSEBase.g:185:7: 'constraints' (inv= invariantClause )*
                    {
                    match(input,75,FOLLOW_75_in_associationClassDefinition511); 
                    // GUSEBase.g:186:7: (inv= invariantClause )*
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( (LA17_0==87) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // GUSEBase.g:187:9: inv= invariantClause
                    	    {
                    	    pushFollow(FOLLOW_invariantClause_in_associationClassDefinition531);
                    	    inv=invariantClause();

                    	    state._fsp--;

                    	     n.addInvariantClause(inv); 

                    	    }
                    	    break;

                    	default :
                    	    break loop17;
                        }
                    } while (true);


                    }
                    break;

            }

            // GUSEBase.g:190:5: ( ( 'aggregation' | 'composition' ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( ((LA19_0>=80 && LA19_0<=81)) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // GUSEBase.g:190:7: ( 'aggregation' | 'composition' )
                    {
                     Token t = input.LT(1); 
                    if ( (input.LA(1)>=80 && input.LA(1)<=81) ) {
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

            match(input,76,FOLLOW_76_in_associationClassDefinition594); 

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
    // $ANTLR end associationClassDefinition


    // $ANTLR start attributeDefinition
    // GUSEBase.g:208:1: attributeDefinition returns [ASTAttribute n] : name= IDENT COLON t= type ( SEMI )? ;
    public final ASTAttribute attributeDefinition() throws RecognitionException {
        ASTAttribute n = null;

        Token name=null;
        ASTType t = null;


        try {
            // GUSEBase.g:209:1: (name= IDENT COLON t= type ( SEMI )? )
            // GUSEBase.g:210:5: name= IDENT COLON t= type ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_attributeDefinition626); 
            match(input,COLON,FOLLOW_COLON_in_attributeDefinition628); 
            pushFollow(FOLLOW_type_in_attributeDefinition632);
            t=gGGenerator.type();

            state._fsp--;

            // GUSEBase.g:210:29: ( SEMI )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==SEMI) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // GUSEBase.g:210:31: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_attributeDefinition636); 

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
    // $ANTLR end attributeDefinition


    // $ANTLR start operationDefinition
    // GUSEBase.g:220:1: operationDefinition returns [ASTOperation n] : name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression )? ( 'begin' al= alActionList 'end' )? (ppc= prePostClause )* ( SEMI )? ;
    public final ASTOperation operationDefinition() throws RecognitionException {
        ASTOperation n = null;

        Token name=null;
        List pl = null;

        ASTType t = null;

        ASTExpression e = null;

        ASTALActionList al = null;

        ASTPrePostClause ppc = null;


        try {
            // GUSEBase.g:221:1: (name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression )? ( 'begin' al= alActionList 'end' )? (ppc= prePostClause )* ( SEMI )? )
            // GUSEBase.g:222:5: name= IDENT pl= paramList ( COLON t= type )? ( EQUAL e= expression )? ( 'begin' al= alActionList 'end' )? (ppc= prePostClause )* ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_operationDefinition675); 
            pushFollow(FOLLOW_paramList_in_operationDefinition683);
            pl=gGGenerator.paramList();

            state._fsp--;

            // GUSEBase.g:224:5: ( COLON t= type )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==COLON) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // GUSEBase.g:224:7: COLON t= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_operationDefinition691); 
                    pushFollow(FOLLOW_type_in_operationDefinition695);
                    t=gGGenerator.type();

                    state._fsp--;


                    }
                    break;

            }

            // GUSEBase.g:225:5: ( EQUAL e= expression )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==EQUAL) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // GUSEBase.g:225:6: EQUAL e= expression
                    {
                    match(input,EQUAL,FOLLOW_EQUAL_in_operationDefinition705); 
                    pushFollow(FOLLOW_expression_in_operationDefinition709);
                    e=gGGenerator.expression();

                    state._fsp--;


                    }
                    break;

            }

            // GUSEBase.g:226:2: ( 'begin' al= alActionList 'end' )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==82) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // GUSEBase.g:226:3: 'begin' al= alActionList 'end'
                    {
                    match(input,82,FOLLOW_82_in_operationDefinition717); 
                    pushFollow(FOLLOW_alActionList_in_operationDefinition721);
                    al=alActionList();

                    state._fsp--;

                    match(input,76,FOLLOW_76_in_operationDefinition723); 

                    }
                    break;

            }

             n = new ASTOperation(name, pl, t, e, al); 
            // GUSEBase.g:228:5: (ppc= prePostClause )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==55||LA24_0==88) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // GUSEBase.g:228:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_operationDefinition742);
            	    ppc=prePostClause();

            	    state._fsp--;

            	     n.addPrePostClause(ppc); 

            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);

            // GUSEBase.g:229:5: ( SEMI )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==SEMI) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // GUSEBase.g:229:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_operationDefinition755); 

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
    // $ANTLR end operationDefinition


    // $ANTLR start associationDefinition
    // GUSEBase.g:240:1: associationDefinition returns [ASTAssociation n] : ( 'association' | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end' ;
    public final ASTAssociation associationDefinition() throws RecognitionException {
        ASTAssociation n = null;

        Token name=null;
        ASTAssociationEnd ae = null;


        try {
            // GUSEBase.g:241:1: ( ( 'association' | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end' )
            // GUSEBase.g:242:5: ( 'association' | 'aggregation' | 'composition' ) name= IDENT 'between' ae= associationEnd (ae= associationEnd )+ 'end'
            {
             Token t = input.LT(1); 
            if ( (input.LA(1)>=80 && input.LA(1)<=81)||input.LA(1)==83 ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationDefinition813); 
             n = new ASTAssociation(t, name); 
            match(input,79,FOLLOW_79_in_associationDefinition821); 
            pushFollow(FOLLOW_associationEnd_in_associationDefinition829);
            ae=associationEnd();

            state._fsp--;

             n.addEnd(ae); 
            // GUSEBase.g:248:5: (ae= associationEnd )+
            int cnt26=0;
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==IDENT) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // GUSEBase.g:248:7: ae= associationEnd
            	    {
            	    pushFollow(FOLLOW_associationEnd_in_associationDefinition841);
            	    ae=associationEnd();

            	    state._fsp--;

            	     n.addEnd(ae); 

            	    }
            	    break;

            	default :
            	    if ( cnt26 >= 1 ) break loop26;
                        EarlyExitException eee =
                            new EarlyExitException(26, input);
                        throw eee;
                }
                cnt26++;
            } while (true);

            match(input,76,FOLLOW_76_in_associationDefinition852); 

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
    // $ANTLR end associationDefinition


    // $ANTLR start associationEnd
    // GUSEBase.g:257:1: associationEnd returns [ASTAssociationEnd n] : name= IDENT LBRACK m= multiplicity RBRACK ( 'role' rn= IDENT )? ( 'ordered' )? ( SEMI )? ;
    public final ASTAssociationEnd associationEnd() throws RecognitionException {
        ASTAssociationEnd n = null;

        Token name=null;
        Token rn=null;
        ASTMultiplicity m = null;


        try {
            // GUSEBase.g:258:1: (name= IDENT LBRACK m= multiplicity RBRACK ( 'role' rn= IDENT )? ( 'ordered' )? ( SEMI )? )
            // GUSEBase.g:259:5: name= IDENT LBRACK m= multiplicity RBRACK ( 'role' rn= IDENT )? ( 'ordered' )? ( SEMI )?
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd878); 
            match(input,LBRACK,FOLLOW_LBRACK_in_associationEnd880); 
            pushFollow(FOLLOW_multiplicity_in_associationEnd884);
            m=multiplicity();

            state._fsp--;

            match(input,RBRACK,FOLLOW_RBRACK_in_associationEnd886); 
             n = new ASTAssociationEnd(name, m); 
            // GUSEBase.g:261:5: ( 'role' rn= IDENT )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==84) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // GUSEBase.g:261:7: 'role' rn= IDENT
                    {
                    match(input,84,FOLLOW_84_in_associationEnd902); 
                    rn=(Token)match(input,IDENT,FOLLOW_IDENT_in_associationEnd906); 
                     n.setRolename(rn); 

                    }
                    break;

            }

            // GUSEBase.g:262:5: ( 'ordered' )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==85) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // GUSEBase.g:262:7: 'ordered'
                    {
                    match(input,85,FOLLOW_85_in_associationEnd919); 
                     n.setOrdered(); 

                    }
                    break;

            }

            // GUSEBase.g:263:5: ( SEMI )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==SEMI) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // GUSEBase.g:263:7: SEMI
                    {
                    match(input,SEMI,FOLLOW_SEMI_in_associationEnd932); 

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
    // $ANTLR end associationEnd


    // $ANTLR start multiplicity
    // GUSEBase.g:277:1: multiplicity returns [ASTMultiplicity n] : mr= multiplicityRange ( COMMA mr= multiplicityRange )* ;
    public final ASTMultiplicity multiplicity() throws RecognitionException {
        ASTMultiplicity n = null;

        ASTMultiplicityRange mr = null;


        try {
            // GUSEBase.g:278:1: (mr= multiplicityRange ( COMMA mr= multiplicityRange )* )
            // GUSEBase.g:279:5: mr= multiplicityRange ( COMMA mr= multiplicityRange )*
            {
             
            	Token t = input.LT(1); // remember start position of expression
            	n = new ASTMultiplicity(t);
                
            pushFollow(FOLLOW_multiplicityRange_in_multiplicity967);
            mr=multiplicityRange();

            state._fsp--;

             n.addRange(mr); 
            // GUSEBase.g:284:5: ( COMMA mr= multiplicityRange )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==COMMA) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // GUSEBase.g:284:7: COMMA mr= multiplicityRange
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_multiplicity977); 
            	    pushFollow(FOLLOW_multiplicityRange_in_multiplicity981);
            	    mr=multiplicityRange();

            	    state._fsp--;

            	     n.addRange(mr); 

            	    }
            	    break;

            	default :
            	    break loop30;
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
    // $ANTLR end multiplicity


    // $ANTLR start multiplicityRange
    // GUSEBase.g:287:1: multiplicityRange returns [ASTMultiplicityRange n] : ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? ;
    public final ASTMultiplicityRange multiplicityRange() throws RecognitionException {
        ASTMultiplicityRange n = null;

        int ms1 = 0;

        int ms2 = 0;


        try {
            // GUSEBase.g:288:1: (ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )? )
            // GUSEBase.g:289:5: ms1= multiplicitySpec ( DOTDOT ms2= multiplicitySpec )?
            {
            pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange1010);
            ms1=multiplicitySpec();

            state._fsp--;

             n = new ASTMultiplicityRange(ms1); 
            // GUSEBase.g:290:5: ( DOTDOT ms2= multiplicitySpec )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==DOTDOT) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // GUSEBase.g:290:7: DOTDOT ms2= multiplicitySpec
                    {
                    match(input,DOTDOT,FOLLOW_DOTDOT_in_multiplicityRange1020); 
                    pushFollow(FOLLOW_multiplicitySpec_in_multiplicityRange1024);
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
    // $ANTLR end multiplicityRange


    // $ANTLR start multiplicitySpec
    // GUSEBase.g:293:1: multiplicitySpec returns [int m] : (i= INT | STAR );
    public final int multiplicitySpec() throws RecognitionException {
        int m = 0;

        Token i=null;

         m = -1; 
        try {
            // GUSEBase.g:295:1: (i= INT | STAR )
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==INT) ) {
                alt32=1;
            }
            else if ( (LA32_0==STAR) ) {
                alt32=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;
            }
            switch (alt32) {
                case 1 :
                    // GUSEBase.g:296:7: i= INT
                    {
                    i=(Token)match(input,INT,FOLLOW_INT_in_multiplicitySpec1058); 
                     m = Integer.parseInt((i!=null?i.getText():null)); 

                    }
                    break;
                case 2 :
                    // GUSEBase.g:297:7: STAR
                    {
                    match(input,STAR,FOLLOW_STAR_in_multiplicitySpec1068); 
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
    // $ANTLR end multiplicitySpec


    // $ANTLR start invariant
    // GUSEBase.g:318:1: invariant returns [ASTConstraintDefinition n] : 'context' (v= IDENT COLON )? t= simpleType (inv= invariantClause )* ;
    public final ASTConstraintDefinition invariant() throws RecognitionException {
        ASTConstraintDefinition n = null;

        Token v=null;
        ASTSimpleType t = null;

        ASTInvariantClause inv = null;


        try {
            // GUSEBase.g:319:1: ( 'context' (v= IDENT COLON )? t= simpleType (inv= invariantClause )* )
            // GUSEBase.g:320:5: 'context' (v= IDENT COLON )? t= simpleType (inv= invariantClause )*
            {
             n = new ASTConstraintDefinition(); 
            match(input,86,FOLLOW_86_in_invariant1109); 
            // GUSEBase.g:322:5: (v= IDENT COLON )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==IDENT) ) {
                int LA33_1 = input.LA(2);

                if ( (LA33_1==COLON) ) {
                    alt33=1;
                }
            }
            switch (alt33) {
                case 1 :
                    // GUSEBase.g:322:7: v= IDENT COLON
                    {
                    v=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariant1119); 
                    match(input,COLON,FOLLOW_COLON_in_invariant1121); 
                     n.setVarName(v); 

                    }
                    break;

            }

            pushFollow(FOLLOW_simpleType_in_invariant1136);
            t=gGGenerator.simpleType();

            state._fsp--;

             n.setType(t); 
            // GUSEBase.g:325:5: (inv= invariantClause )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==87) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // GUSEBase.g:325:7: inv= invariantClause
            	    {
            	    pushFollow(FOLLOW_invariantClause_in_invariant1148);
            	    inv=invariantClause();

            	    state._fsp--;

            	     n.addInvariantClause(inv); 

            	    }
            	    break;

            	default :
            	    break loop34;
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
    // $ANTLR end invariant


    // $ANTLR start invariantClause
    // GUSEBase.g:333:1: invariantClause returns [ASTInvariantClause n] : 'inv' (name= IDENT )? COLON e= expression ;
    public final ASTInvariantClause invariantClause() throws RecognitionException {
        ASTInvariantClause n = null;

        Token name=null;
        ASTExpression e = null;


        try {
            // GUSEBase.g:334:1: ( 'inv' (name= IDENT )? COLON e= expression )
            // GUSEBase.g:335:5: 'inv' (name= IDENT )? COLON e= expression
            {
            match(input,87,FOLLOW_87_in_invariantClause1178); 
            // GUSEBase.g:335:11: (name= IDENT )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==IDENT) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // GUSEBase.g:335:13: name= IDENT
                    {
                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantClause1184); 

                    }
                    break;

            }

            match(input,COLON,FOLLOW_COLON_in_invariantClause1189); 
            pushFollow(FOLLOW_expression_in_invariantClause1193);
            e=gGGenerator.expression();

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
    // $ANTLR end invariantClause


    // $ANTLR start prePost
    // GUSEBase.g:347:1: prePost returns [ASTPrePost n] : 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ ;
    public final ASTPrePost prePost() throws RecognitionException {
        ASTPrePost n = null;

        Token classname=null;
        Token opname=null;
        List pl = null;

        ASTType rt = null;

        ASTPrePostClause ppc = null;


        try {
            // GUSEBase.g:348:1: ( 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+ )
            // GUSEBase.g:349:5: 'context' classname= IDENT COLON_COLON opname= IDENT pl= paramList ( COLON rt= type )? (ppc= prePostClause )+
            {
            match(input,86,FOLLOW_86_in_prePost1223); 
            classname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost1227); 
            match(input,COLON_COLON,FOLLOW_COLON_COLON_in_prePost1229); 
            opname=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePost1233); 
            pushFollow(FOLLOW_paramList_in_prePost1237);
            pl=gGGenerator.paramList();

            state._fsp--;

            // GUSEBase.g:349:69: ( COLON rt= type )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==COLON) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // GUSEBase.g:349:71: COLON rt= type
                    {
                    match(input,COLON,FOLLOW_COLON_in_prePost1241); 
                    pushFollow(FOLLOW_type_in_prePost1245);
                    rt=gGGenerator.type();

                    state._fsp--;


                    }
                    break;

            }

             n = new ASTPrePost(classname, opname, pl, rt); 
            // GUSEBase.g:351:5: (ppc= prePostClause )+
            int cnt37=0;
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==55||LA37_0==88) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // GUSEBase.g:351:7: ppc= prePostClause
            	    {
            	    pushFollow(FOLLOW_prePostClause_in_prePost1264);
            	    ppc=prePostClause();

            	    state._fsp--;

            	     n.addPrePostClause(ppc); 

            	    }
            	    break;

            	default :
            	    if ( cnt37 >= 1 ) break loop37;
                        EarlyExitException eee =
                            new EarlyExitException(37, input);
                        throw eee;
                }
                cnt37++;
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
    // $ANTLR end prePost


    // $ANTLR start prePostClause
    // GUSEBase.g:358:1: prePostClause returns [ASTPrePostClause n] : ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression ;
    public final ASTPrePostClause prePostClause() throws RecognitionException {
        ASTPrePostClause n = null;

        Token name=null;
        ASTExpression e = null;


        try {
            // GUSEBase.g:359:1: ( ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression )
            // GUSEBase.g:360:5: ( 'pre' | 'post' ) (name= IDENT )? COLON e= expression
            {
             Token t = input.LT(1); 
            if ( input.LA(1)==55||input.LA(1)==88 ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // GUSEBase.g:361:25: (name= IDENT )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==IDENT) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // GUSEBase.g:361:27: name= IDENT
                    {
                    name=(Token)match(input,IDENT,FOLLOW_IDENT_in_prePostClause1313); 

                    }
                    break;

            }

            match(input,COLON,FOLLOW_COLON_in_prePostClause1318); 
            pushFollow(FOLLOW_expression_in_prePostClause1322);
            e=gGGenerator.expression();

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
    // $ANTLR end prePostClause


    // $ANTLR start alActionList
    // GUSEBase.g:365:1: alActionList returns [ASTALActionList al] : (action= alAction )* ;
    public final ASTALActionList alActionList() throws RecognitionException {
        ASTALActionList al = null;

        ASTALAction action = null;


        
        	al = new ASTALActionList();

        try {
            // GUSEBase.g:369:1: ( (action= alAction )* )
            // GUSEBase.g:370:2: (action= alAction )*
            {
            // GUSEBase.g:370:2: (action= alAction )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==57||(LA39_0>=89 && LA39_0<=92)||LA39_0==94||LA39_0==96||(LA39_0>=98 && LA39_0<=99)||(LA39_0>=102 && LA39_0<=103)) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // GUSEBase.g:370:4: action= alAction
            	    {
            	    pushFollow(FOLLOW_alAction_in_alActionList1355);
            	    action=alAction();

            	    state._fsp--;

            	    al.add(action);

            	    }
            	    break;

            	default :
            	    break loop39;
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
    // $ANTLR end alActionList


    // $ANTLR start alAction
    // GUSEBase.g:373:1: alAction returns [ASTALAction action] : (aCV= alCreateVar | aDl= alDelete | aSe= alSet | aSC= alSetCreate | aIn= alInsert | aDe= alDestroy | aIf= alIf | aWh= alWhile | aFo= alFor | aEx= alExec );
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
            // GUSEBase.g:374:1: (aCV= alCreateVar | aDl= alDelete | aSe= alSet | aSC= alSetCreate | aIn= alInsert | aDe= alDestroy | aIf= alIf | aWh= alWhile | aFo= alFor | aEx= alExec )
            int alt40=10;
            switch ( input.LA(1) ) {
            case 89:
            case 90:
                {
                alt40=1;
                }
                break;
            case 96:
                {
                alt40=2;
                }
                break;
            case 91:
                {
                alt40=3;
                }
                break;
            case 92:
                {
                alt40=4;
                }
                break;
            case 94:
                {
                alt40=5;
                }
                break;
            case 98:
                {
                alt40=6;
                }
                break;
            case 57:
                {
                alt40=7;
                }
                break;
            case 99:
                {
                alt40=8;
                }
                break;
            case 102:
                {
                alt40=9;
                }
                break;
            case 103:
                {
                alt40=10;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 40, 0, input);

                throw nvae;
            }

            switch (alt40) {
                case 1 :
                    // GUSEBase.g:375:5: aCV= alCreateVar
                    {
                    pushFollow(FOLLOW_alCreateVar_in_alAction1381);
                    aCV=alCreateVar();

                    state._fsp--;

                     action = aCV; 

                    }
                    break;
                case 2 :
                    // GUSEBase.g:376:5: aDl= alDelete
                    {
                    pushFollow(FOLLOW_alDelete_in_alAction1393);
                    aDl=alDelete();

                    state._fsp--;

                     action = aDl; 

                    }
                    break;
                case 3 :
                    // GUSEBase.g:377:5: aSe= alSet
                    {
                    pushFollow(FOLLOW_alSet_in_alAction1405);
                    aSe=alSet();

                    state._fsp--;

                     action = aSe; 

                    }
                    break;
                case 4 :
                    // GUSEBase.g:378:5: aSC= alSetCreate
                    {
                    pushFollow(FOLLOW_alSetCreate_in_alAction1417);
                    aSC=alSetCreate();

                    state._fsp--;

                     action = aSC; 

                    }
                    break;
                case 5 :
                    // GUSEBase.g:379:5: aIn= alInsert
                    {
                    pushFollow(FOLLOW_alInsert_in_alAction1429);
                    aIn=alInsert();

                    state._fsp--;

                     action = aIn; 

                    }
                    break;
                case 6 :
                    // GUSEBase.g:380:5: aDe= alDestroy
                    {
                    pushFollow(FOLLOW_alDestroy_in_alAction1441);
                    aDe=alDestroy();

                    state._fsp--;

                     action = aDe; 

                    }
                    break;
                case 7 :
                    // GUSEBase.g:381:5: aIf= alIf
                    {
                    pushFollow(FOLLOW_alIf_in_alAction1453);
                    aIf=alIf();

                    state._fsp--;

                     action = aIf; 

                    }
                    break;
                case 8 :
                    // GUSEBase.g:382:5: aWh= alWhile
                    {
                    pushFollow(FOLLOW_alWhile_in_alAction1465);
                    aWh=alWhile();

                    state._fsp--;

                     action = aWh; 

                    }
                    break;
                case 9 :
                    // GUSEBase.g:383:5: aFo= alFor
                    {
                    pushFollow(FOLLOW_alFor_in_alAction1477);
                    aFo=alFor();

                    state._fsp--;

                     action = aFo; 

                    }
                    break;
                case 10 :
                    // GUSEBase.g:384:5: aEx= alExec
                    {
                    pushFollow(FOLLOW_alExec_in_alAction1489);
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
    // $ANTLR end alAction


    // $ANTLR start alCreateVar
    // GUSEBase.g:389:1: alCreateVar returns [ASTALCreateVar var] : ( 'var' | 'declare' ) name= IDENT COLON t= type ;
    public final ASTALCreateVar alCreateVar() throws RecognitionException {
        ASTALCreateVar var = null;

        Token name=null;
        ASTType t = null;


        try {
            // GUSEBase.g:390:1: ( ( 'var' | 'declare' ) name= IDENT COLON t= type )
            // GUSEBase.g:391:2: ( 'var' | 'declare' ) name= IDENT COLON t= type
            {
            if ( (input.LA(1)>=89 && input.LA(1)<=90) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_alCreateVar1516); 
            match(input,COLON,FOLLOW_COLON_in_alCreateVar1518); 
            pushFollow(FOLLOW_type_in_alCreateVar1522);
            t=gGGenerator.type();

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
    // $ANTLR end alCreateVar


    // $ANTLR start alSet
    // GUSEBase.g:395:1: alSet returns [ASTALSet set] : 'set' lval= expression COLON_EQUAL rval= expression ;
    public final ASTALSet alSet() throws RecognitionException {
        ASTALSet set = null;

        ASTExpression lval = null;

        ASTExpression rval = null;


        try {
            // GUSEBase.g:396:1: ( 'set' lval= expression COLON_EQUAL rval= expression )
            // GUSEBase.g:398:5: 'set' lval= expression COLON_EQUAL rval= expression
            {
            match(input,91,FOLLOW_91_in_alSet1544); 
            pushFollow(FOLLOW_expression_in_alSet1548);
            lval=gGGenerator.expression();

            state._fsp--;

            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_alSet1550); 
            pushFollow(FOLLOW_expression_in_alSet1554);
            rval=gGGenerator.expression();

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
    // $ANTLR end alSet


    // $ANTLR start alSetCreate
    // GUSEBase.g:402:1: alSetCreate returns [ASTALSetCreate setcreate] : 'create' lval= expression COLON_EQUAL {...}?new_= IDENT cls= IDENT ( 'namehint' nameExpr= expression )? ;
    public final ASTALSetCreate alSetCreate() throws RecognitionException {
        ASTALSetCreate setcreate = null;

        Token new_=null;
        Token cls=null;
        ASTExpression lval = null;

        ASTExpression nameExpr = null;


        try {
            // GUSEBase.g:403:1: ( 'create' lval= expression COLON_EQUAL {...}?new_= IDENT cls= IDENT ( 'namehint' nameExpr= expression )? )
            // GUSEBase.g:404:5: 'create' lval= expression COLON_EQUAL {...}?new_= IDENT cls= IDENT ( 'namehint' nameExpr= expression )?
            {
            match(input,92,FOLLOW_92_in_alSetCreate1578); 
            pushFollow(FOLLOW_expression_in_alSetCreate1582);
            lval=gGGenerator.expression();

            state._fsp--;

            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_alSetCreate1584); 
            if ( !( input.LT(1).getText().equals("new") ) ) {
                throw new FailedPredicateException(input, "alSetCreate", " input.LT(1).getText().equals(\"new\") ");
            }
            new_=(Token)match(input,IDENT,FOLLOW_IDENT_in_alSetCreate1590); 
            cls=(Token)match(input,IDENT,FOLLOW_IDENT_in_alSetCreate1594); 
            // GUSEBase.g:405:5: ( 'namehint' nameExpr= expression )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==93) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // GUSEBase.g:405:7: 'namehint' nameExpr= expression
                    {
                    match(input,93,FOLLOW_93_in_alSetCreate1603); 
                    pushFollow(FOLLOW_expression_in_alSetCreate1607);
                    nameExpr=gGGenerator.expression();

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
    // $ANTLR end alSetCreate


    // $ANTLR start alInsert
    // GUSEBase.g:410:1: alInsert returns [ASTALInsert insert] : 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT ;
    public final ASTALInsert alInsert() throws RecognitionException {
        ASTALInsert insert = null;

        Token id=null;
        ASTExpression e = null;


        List exprList = new ArrayList(); 
        try {
            // GUSEBase.g:412:1: ( 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT )
            // GUSEBase.g:413:5: 'insert' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'into' id= IDENT
            {
            match(input,94,FOLLOW_94_in_alInsert1638); 
            match(input,LPAREN,FOLLOW_LPAREN_in_alInsert1640); 
            pushFollow(FOLLOW_expression_in_alInsert1649);
            e=gGGenerator.expression();

            state._fsp--;

             exprList.add(e); 
            match(input,COMMA,FOLLOW_COMMA_in_alInsert1653); 
            pushFollow(FOLLOW_expression_in_alInsert1661);
            e=gGGenerator.expression();

            state._fsp--;

             exprList.add(e); 
            // GUSEBase.g:415:42: ( COMMA e= expression )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==COMMA) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // GUSEBase.g:415:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_alInsert1667); 
            	    pushFollow(FOLLOW_expression_in_alInsert1671);
            	    e=gGGenerator.expression();

            	    state._fsp--;

            	     exprList.add(e); 

            	    }
            	    break;

            	default :
            	    break loop42;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_alInsert1683); 
            match(input,95,FOLLOW_95_in_alInsert1685); 
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_alInsert1689); 
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
    // $ANTLR end alInsert


    // $ANTLR start alDelete
    // GUSEBase.g:421:1: alDelete returns [ASTALDelete n] : 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT ;
    public final ASTALDelete alDelete() throws RecognitionException {
        ASTALDelete n = null;

        Token id=null;
        ASTExpression e = null;


         List exprList = new ArrayList(); 
        try {
            // GUSEBase.g:423:1: ( 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT )
            // GUSEBase.g:424:5: 'delete' LPAREN e= expression COMMA e= expression ( COMMA e= expression )* RPAREN 'from' id= IDENT
            {
            match(input,96,FOLLOW_96_in_alDelete1721); 
            match(input,LPAREN,FOLLOW_LPAREN_in_alDelete1723); 
            pushFollow(FOLLOW_expression_in_alDelete1731);
            e=gGGenerator.expression();

            state._fsp--;

             exprList.add(e); 
            match(input,COMMA,FOLLOW_COMMA_in_alDelete1735); 
            pushFollow(FOLLOW_expression_in_alDelete1743);
            e=gGGenerator.expression();

            state._fsp--;

             exprList.add(e); 
            // GUSEBase.g:426:42: ( COMMA e= expression )*
            loop43:
            do {
                int alt43=2;
                int LA43_0 = input.LA(1);

                if ( (LA43_0==COMMA) ) {
                    alt43=1;
                }


                switch (alt43) {
            	case 1 :
            	    // GUSEBase.g:426:44: COMMA e= expression
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_alDelete1749); 
            	    pushFollow(FOLLOW_expression_in_alDelete1753);
            	    e=gGGenerator.expression();

            	    state._fsp--;

            	     exprList.add(e); 

            	    }
            	    break;

            	default :
            	    break loop43;
                }
            } while (true);

            match(input,RPAREN,FOLLOW_RPAREN_in_alDelete1764); 
            match(input,97,FOLLOW_97_in_alDelete1766); 
            id=(Token)match(input,IDENT,FOLLOW_IDENT_in_alDelete1770); 
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
    // $ANTLR end alDelete


    // $ANTLR start alDestroy
    // GUSEBase.g:432:1: alDestroy returns [ASTALDestroy n] : 'destroy' e= expression ;
    public final ASTALDestroy alDestroy() throws RecognitionException {
        ASTALDestroy n = null;

        ASTExpression e = null;


        try {
            // GUSEBase.g:433:1: ( 'destroy' e= expression )
            // GUSEBase.g:434:6: 'destroy' e= expression
            {
            match(input,98,FOLLOW_98_in_alDestroy1799); 
            pushFollow(FOLLOW_expression_in_alDestroy1803);
            e=gGGenerator.expression();

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
    // $ANTLR end alDestroy


    // $ANTLR start alIf
    // GUSEBase.g:438:1: alIf returns [ASTALIf i] : 'if' ifexpr= expression 'then' thenlist= alActionList ( 'else' elselist= alActionList )? 'endif' ;
    public final ASTALIf alIf() throws RecognitionException {
        ASTALIf i = null;

        ASTExpression ifexpr = null;

        ASTALActionList thenlist = null;

        ASTALActionList elselist = null;


        try {
            // GUSEBase.g:439:1: ( 'if' ifexpr= expression 'then' thenlist= alActionList ( 'else' elselist= alActionList )? 'endif' )
            // GUSEBase.g:440:2: 'if' ifexpr= expression 'then' thenlist= alActionList ( 'else' elselist= alActionList )? 'endif'
            {
            match(input,57,FOLLOW_57_in_alIf1827); 
            pushFollow(FOLLOW_expression_in_alIf1831);
            ifexpr=gGGenerator.expression();

            state._fsp--;

            match(input,58,FOLLOW_58_in_alIf1835); 
            pushFollow(FOLLOW_alActionList_in_alIf1839);
            thenlist=alActionList();

            state._fsp--;

            // GUSEBase.g:442:2: ( 'else' elselist= alActionList )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==59) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // GUSEBase.g:442:3: 'else' elselist= alActionList
                    {
                    match(input,59,FOLLOW_59_in_alIf1843); 
                    pushFollow(FOLLOW_alActionList_in_alIf1847);
                    elselist=alActionList();

                    state._fsp--;


                    }
                    break;

            }

            match(input,60,FOLLOW_60_in_alIf1852); 
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
    // $ANTLR end alIf


    // $ANTLR start alWhile
    // GUSEBase.g:447:1: alWhile returns [ASTALWhile w] : 'while' expr= expression 'do' body= alActionList 'wend' ;
    public final ASTALWhile alWhile() throws RecognitionException {
        ASTALWhile w = null;

        ASTExpression expr = null;

        ASTALActionList body = null;


        try {
            // GUSEBase.g:448:1: ( 'while' expr= expression 'do' body= alActionList 'wend' )
            // GUSEBase.g:449:2: 'while' expr= expression 'do' body= alActionList 'wend'
            {
            match(input,99,FOLLOW_99_in_alWhile1871); 
            pushFollow(FOLLOW_expression_in_alWhile1875);
            expr=gGGenerator.expression();

            state._fsp--;

            match(input,100,FOLLOW_100_in_alWhile1879); 
            pushFollow(FOLLOW_alActionList_in_alWhile1885);
            body=alActionList();

            state._fsp--;

            match(input,101,FOLLOW_101_in_alWhile1888); 
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
    // $ANTLR end alWhile


    // $ANTLR start alFor
    // GUSEBase.g:457:1: alFor returns [ASTALFor f] : 'for' var= IDENT COLON t= type 'in' expr= expression 'do' body= alActionList {...}?next= IDENT ;
    public final ASTALFor alFor() throws RecognitionException {
        ASTALFor f = null;

        Token var=null;
        Token next=null;
        ASTType t = null;

        ASTExpression expr = null;

        ASTALActionList body = null;


        try {
            // GUSEBase.g:458:1: ( 'for' var= IDENT COLON t= type 'in' expr= expression 'do' body= alActionList {...}?next= IDENT )
            // GUSEBase.g:459:2: 'for' var= IDENT COLON t= type 'in' expr= expression 'do' body= alActionList {...}?next= IDENT
            {
            match(input,102,FOLLOW_102_in_alFor1907); 
            var=(Token)match(input,IDENT,FOLLOW_IDENT_in_alFor1911); 
            match(input,COLON,FOLLOW_COLON_in_alFor1913); 
            pushFollow(FOLLOW_type_in_alFor1917);
            t=gGGenerator.type();

            state._fsp--;

            match(input,47,FOLLOW_47_in_alFor1919); 
            pushFollow(FOLLOW_expression_in_alFor1923);
            expr=gGGenerator.expression();

            state._fsp--;

            match(input,100,FOLLOW_100_in_alFor1927); 
            pushFollow(FOLLOW_alActionList_in_alFor1933);
            body=alActionList();

            state._fsp--;

            if ( !( input.LT(1).getText().equals("next") ) ) {
                throw new FailedPredicateException(input, "alFor", " input.LT(1).getText().equals(\"next\") ");
            }
            next=(Token)match(input,IDENT,FOLLOW_IDENT_in_alFor1940); 
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
    // $ANTLR end alFor


    // $ANTLR start alExec
    // GUSEBase.g:466:1: alExec returns [ASTALExecute c] : 'execute' op= expression ;
    public final ASTALExecute alExec() throws RecognitionException {
        ASTALExecute c = null;

        ASTExpression op = null;


        try {
            // GUSEBase.g:467:1: ( 'execute' op= expression )
            // GUSEBase.g:468:5: 'execute' op= expression
            {
            match(input,103,FOLLOW_103_in_alExec1960); 
            pushFollow(FOLLOW_expression_in_alExec1964);
            op=gGGenerator.expression();

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
    // $ANTLR end alExec

    // Delegated rules


 

    public static final BitSet FOLLOW_70_in_enumTypeDefinition47 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_enumTypeDefinition51 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_LBRACE_in_enumTypeDefinition53 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_idList_in_enumTypeDefinition57 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_RBRACE_in_enumTypeDefinition59 = new BitSet(new long[]{0x0000000004000002L});
    public static final BitSet FOLLOW_SEMI_in_enumTypeDefinition63 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_71_in_generalClassDefinition104 = new BitSet(new long[]{0x0000000000000000L,0x0000000000006100L});
    public static final BitSet FOLLOW_classDefinition_in_generalClassDefinition122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_associationClassDefinition_in_generalClassDefinition142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_72_in_classDefinition182 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_classDefinition186 = new BitSet(new long[]{0x0000000000004000L,0x0000000000001E00L});
    public static final BitSet FOLLOW_LESS_in_classDefinition196 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_idList_in_classDefinition200 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001E00L});
    public static final BitSet FOLLOW_73_in_classDefinition213 = new BitSet(new long[]{0x0000000000000400L,0x0000000000001C00L});
    public static final BitSet FOLLOW_attributeDefinition_in_classDefinition226 = new BitSet(new long[]{0x0000000000000400L,0x0000000000001C00L});
    public static final BitSet FOLLOW_74_in_classDefinition247 = new BitSet(new long[]{0x0000000000000400L,0x0000000000001800L});
    public static final BitSet FOLLOW_operationDefinition_in_classDefinition260 = new BitSet(new long[]{0x0000000000000400L,0x0000000000001800L});
    public static final BitSet FOLLOW_75_in_classDefinition281 = new BitSet(new long[]{0x0000000000000000L,0x0000000000801000L});
    public static final BitSet FOLLOW_invariantClause_in_classDefinition301 = new BitSet(new long[]{0x0000000000000000L,0x0000000000801000L});
    public static final BitSet FOLLOW_76_in_classDefinition325 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_associationClassDefinition359 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_associationClassDefinition385 = new BitSet(new long[]{0x0000000000004000L,0x0000000000008000L});
    public static final BitSet FOLLOW_LESS_in_associationClassDefinition395 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_idList_in_associationClassDefinition399 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_associationClassDefinition410 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_associationEnd_in_associationClassDefinition418 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_associationEnd_in_associationClassDefinition430 = new BitSet(new long[]{0x0000000000000400L,0x0000000000031E00L});
    public static final BitSet FOLLOW_73_in_associationClassDefinition443 = new BitSet(new long[]{0x0000000000000400L,0x0000000000031C00L});
    public static final BitSet FOLLOW_attributeDefinition_in_associationClassDefinition456 = new BitSet(new long[]{0x0000000000000400L,0x0000000000031C00L});
    public static final BitSet FOLLOW_74_in_associationClassDefinition477 = new BitSet(new long[]{0x0000000000000400L,0x0000000000031800L});
    public static final BitSet FOLLOW_operationDefinition_in_associationClassDefinition490 = new BitSet(new long[]{0x0000000000000400L,0x0000000000031800L});
    public static final BitSet FOLLOW_75_in_associationClassDefinition511 = new BitSet(new long[]{0x0000000000000000L,0x0000000000831000L});
    public static final BitSet FOLLOW_invariantClause_in_associationClassDefinition531 = new BitSet(new long[]{0x0000000000000000L,0x0000000000831000L});
    public static final BitSet FOLLOW_set_in_associationClassDefinition565 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_associationClassDefinition594 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_attributeDefinition626 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_attributeDefinition628 = new BitSet(new long[]{0x8000000000000400L,0x0000000000000033L});
    public static final BitSet FOLLOW_type_in_attributeDefinition632 = new BitSet(new long[]{0x0000000004000002L});
    public static final BitSet FOLLOW_SEMI_in_attributeDefinition636 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_operationDefinition675 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_paramList_in_operationDefinition683 = new BitSet(new long[]{0x0080000004001802L,0x0000000001040000L});
    public static final BitSet FOLLOW_COLON_in_operationDefinition691 = new BitSet(new long[]{0x8000000000000400L,0x0000000000000033L});
    public static final BitSet FOLLOW_type_in_operationDefinition695 = new BitSet(new long[]{0x0080000004001002L,0x0000000001040000L});
    public static final BitSet FOLLOW_EQUAL_in_operationDefinition705 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_operationDefinition709 = new BitSet(new long[]{0x0080000004000002L,0x0000000001040000L});
    public static final BitSet FOLLOW_82_in_operationDefinition717 = new BitSet(new long[]{0x0200000000000000L,0x000000CD5E001000L});
    public static final BitSet FOLLOW_alActionList_in_operationDefinition721 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_operationDefinition723 = new BitSet(new long[]{0x0080000004000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_prePostClause_in_operationDefinition742 = new BitSet(new long[]{0x0080000004000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_SEMI_in_operationDefinition755 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_associationDefinition788 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_associationDefinition813 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_79_in_associationDefinition821 = new BitSet(new long[]{0x0000000000000400L,0x0000000000031E00L});
    public static final BitSet FOLLOW_associationEnd_in_associationDefinition829 = new BitSet(new long[]{0x0000000000000400L,0x0000000000031E00L});
    public static final BitSet FOLLOW_associationEnd_in_associationDefinition841 = new BitSet(new long[]{0x0000000000000400L,0x0000000000031E00L});
    public static final BitSet FOLLOW_76_in_associationDefinition852 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd878 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_LBRACK_in_associationEnd880 = new BitSet(new long[]{0x0000000020100000L});
    public static final BitSet FOLLOW_multiplicity_in_associationEnd884 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_RBRACK_in_associationEnd886 = new BitSet(new long[]{0x0000000004000002L,0x0000000000300000L});
    public static final BitSet FOLLOW_84_in_associationEnd902 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_associationEnd906 = new BitSet(new long[]{0x0000000004000002L,0x0000000000200000L});
    public static final BitSet FOLLOW_85_in_associationEnd919 = new BitSet(new long[]{0x0000000004000002L});
    public static final BitSet FOLLOW_SEMI_in_associationEnd932 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity967 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_multiplicity977 = new BitSet(new long[]{0x0000000020100000L});
    public static final BitSet FOLLOW_multiplicityRange_in_multiplicity981 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange1010 = new BitSet(new long[]{0x0000000800000002L});
    public static final BitSet FOLLOW_DOTDOT_in_multiplicityRange1020 = new BitSet(new long[]{0x0000000020100000L});
    public static final BitSet FOLLOW_multiplicitySpec_in_multiplicityRange1024 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_multiplicitySpec1058 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_multiplicitySpec1068 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_86_in_invariant1109 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_invariant1119 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_invariant1121 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_simpleType_in_invariant1136 = new BitSet(new long[]{0x0000000000000002L,0x0000000000831000L});
    public static final BitSet FOLLOW_invariantClause_in_invariant1148 = new BitSet(new long[]{0x0000000000000002L,0x0000000000831000L});
    public static final BitSet FOLLOW_87_in_invariantClause1178 = new BitSet(new long[]{0x0000000000000C00L});
    public static final BitSet FOLLOW_IDENT_in_invariantClause1184 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_invariantClause1189 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_invariantClause1193 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_86_in_prePost1223 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_prePost1227 = new BitSet(new long[]{0x0000001000000000L});
    public static final BitSet FOLLOW_COLON_COLON_in_prePost1229 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_prePost1233 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_paramList_in_prePost1237 = new BitSet(new long[]{0x0080000004000800L,0x0000000001000000L});
    public static final BitSet FOLLOW_COLON_in_prePost1241 = new BitSet(new long[]{0x8000000000000400L,0x0000000000000033L});
    public static final BitSet FOLLOW_type_in_prePost1245 = new BitSet(new long[]{0x0080000004000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_prePostClause_in_prePost1264 = new BitSet(new long[]{0x0080000004000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_set_in_prePostClause1298 = new BitSet(new long[]{0x0000000000000C00L});
    public static final BitSet FOLLOW_IDENT_in_prePostClause1313 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_prePostClause1318 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_prePostClause1322 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alAction_in_alActionList1355 = new BitSet(new long[]{0x0200000000000002L,0x000000CD5E000000L});
    public static final BitSet FOLLOW_alCreateVar_in_alAction1381 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alDelete_in_alAction1393 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alSet_in_alAction1405 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alSetCreate_in_alAction1417 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alInsert_in_alAction1429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alDestroy_in_alAction1441 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alIf_in_alAction1453 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alWhile_in_alAction1465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alFor_in_alAction1477 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_alExec_in_alAction1489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_alCreateVar1508 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_alCreateVar1516 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_alCreateVar1518 = new BitSet(new long[]{0x8000000000000400L,0x0000000000000033L});
    public static final BitSet FOLLOW_type_in_alCreateVar1522 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_91_in_alSet1544 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_alSet1548 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_alSet1550 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_alSet1554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_92_in_alSetCreate1578 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_alSetCreate1582 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_alSetCreate1584 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_alSetCreate1590 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_alSetCreate1594 = new BitSet(new long[]{0x0000000000000002L,0x0000000020000000L});
    public static final BitSet FOLLOW_93_in_alSetCreate1603 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_alSetCreate1607 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_94_in_alInsert1638 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_alInsert1640 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_alInsert1649 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_alInsert1653 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_alInsert1661 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_COMMA_in_alInsert1667 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_alInsert1671 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_RPAREN_in_alInsert1683 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_95_in_alInsert1685 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_alInsert1689 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_alDelete1721 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_alDelete1723 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_alDelete1731 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_COMMA_in_alDelete1735 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_alDelete1743 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_COMMA_in_alDelete1749 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_alDelete1753 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_RPAREN_in_alDelete1764 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_97_in_alDelete1766 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_alDelete1770 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_98_in_alDestroy1799 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_alDestroy1803 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_alIf1827 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_alIf1831 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_alIf1835 = new BitSet(new long[]{0x1A00000000000000L,0x000000CD5E000000L});
    public static final BitSet FOLLOW_alActionList_in_alIf1839 = new BitSet(new long[]{0x1800000000000000L});
    public static final BitSet FOLLOW_59_in_alIf1843 = new BitSet(new long[]{0x1200000000000000L,0x000000CD5E000000L});
    public static final BitSet FOLLOW_alActionList_in_alIf1847 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_60_in_alIf1852 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_99_in_alWhile1871 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_alWhile1875 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_alWhile1879 = new BitSet(new long[]{0x0200000000000000L,0x000000ED5E000000L});
    public static final BitSet FOLLOW_alActionList_in_alWhile1885 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_alWhile1888 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_102_in_alFor1907 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_alFor1911 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_COLON_in_alFor1913 = new BitSet(new long[]{0x8000000000000400L,0x0000000000000033L});
    public static final BitSet FOLLOW_type_in_alFor1917 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_alFor1919 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_alFor1923 = new BitSet(new long[]{0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_100_in_alFor1927 = new BitSet(new long[]{0x0200000000000400L,0x000000CD5E000000L});
    public static final BitSet FOLLOW_alActionList_in_alFor1933 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_alFor1940 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_alExec1960 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_alExec1964 = new BitSet(new long[]{0x0000000000000002L});

}