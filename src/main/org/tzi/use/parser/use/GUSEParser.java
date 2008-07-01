// $ANTLR 3.1b1 GUSE.g 2008-07-01 13:26:34
 
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

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.PrintWriter;
import org.tzi.use.parser.ParseErrorHandler;
import org.tzi.use.parser.ocl.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class GUSEParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LITERAL_oclAsType", "LITERAL_oclIsKindOf", "LITERAL_oclIsTypeOf", "LPAREN", "COMMA", "RPAREN", "IDENT", "COLON", "EQUAL", "NOT_EQUAL", "LESS", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "PLUS", "MINUS", "STAR", "SLASH", "ARROW", "DOT", "AT", "BAR", "SEMI", "LBRACK", "RBRACK", "INT", "REAL", "STRING", "HASH", "LBRACE", "RBRACE", "DOTDOT", "COLON_COLON", "COLON_EQUAL", "NEWLINE", "WS", "SL_COMMENT", "ML_COMMENT", "RANGE_OR_INT", "ESC", "HEX_DIGIT", "VOCAB", "'let'", "'in'", "'implies'", "'or'", "'xor'", "'and'", "'div'", "'not'", "'allInstances'", "'pre'", "'iterate'", "'if'", "'then'", "'else'", "'endif'", "'true'", "'false'", "'Set'", "'Sequence'", "'Bag'", "'oclEmpty'", "'oclUndefined'", "'Tuple'", "'Collection'", "'enum'", "'abstract'", "'class'", "'attributes'", "'operations'", "'constraints'", "'end'", "'associationClass'", "'associationclass'", "'between'", "'aggregation'", "'composition'", "'begin'", "'association'", "'role'", "'ordered'", "'context'", "'inv'", "'post'", "'var'", "'declare'", "'set'", "'create'", "'namehint'", "'insert'", "'into'", "'delete'", "'from'", "'destroy'", "'while'", "'do'", "'wend'", "'for'", "'execute'", "'model'"
    };
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
    public GUSE_GUSEBase_GOCLBase gGOCLBase;
    public GUSE_GUSEBase gGUSEBase;
    // delegators


        public GUSEParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public GUSEParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            gGUSEBase = new GUSE_GUSEBase(input, state, this);
        }
        

    public String[] getTokenNames() { return GUSEParser.tokenNames; }
    public String getGrammarFileName() { return "GUSE.g"; }

    
    	public void init(ParseErrorHandler handler) {
    		fParseErrorHandler = handler;
    		
    		this.gGUSEBase.init(handler);
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



    // $ANTLR start model
    // GUSE.g:152:1: model returns [ASTModel n] : 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF ;
    public final ASTModel model() throws RecognitionException {
        ASTModel n = null;

        Token modelName=null;
        ASTEnumTypeDefinition e = null;

        ASTAssociation a = null;

        ASTConstraintDefinition cons = null;

        ASTPrePost ppc = null;


        try {
            // GUSE.g:153:1: ( 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF )
            // GUSE.g:154:5: 'model' modelName= IDENT (e= enumTypeDefinition )* ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )* EOF
            {
            match(input,104,FOLLOW_104_in_model71); 
            modelName=(Token)match(input,IDENT,FOLLOW_IDENT_in_model75); 
             n = new ASTModel(modelName); 
            // GUSE.g:155:5: (e= enumTypeDefinition )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==70) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // GUSE.g:155:7: e= enumTypeDefinition
            	    {
            	    pushFollow(FOLLOW_enumTypeDefinition_in_model87);
            	    e=enumTypeDefinition();

            	    state._fsp--;

            	     n.addEnumTypeDef(e); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // GUSE.g:156:5: ( ( generalClassDefinition[$n] ) | (a= associationDefinition ) | ( 'constraints' (cons= invariant | ppc= prePost )* ) )*
            loop3:
            do {
                int alt3=4;
                switch ( input.LA(1) ) {
                case 71:
                case 72:
                case 77:
                case 78:
                    {
                    alt3=1;
                    }
                    break;
                case 80:
                case 81:
                case 83:
                    {
                    alt3=2;
                    }
                    break;
                case 75:
                    {
                    alt3=3;
                    }
                    break;

                }

                switch (alt3) {
            	case 1 :
            	    // GUSE.g:156:9: ( generalClassDefinition[$n] )
            	    {
            	    // GUSE.g:156:9: ( generalClassDefinition[$n] )
            	    // GUSE.g:156:11: generalClassDefinition[$n]
            	    {
            	    pushFollow(FOLLOW_generalClassDefinition_in_model104);
            	    generalClassDefinition(n);

            	    state._fsp--;


            	    }


            	    }
            	    break;
            	case 2 :
            	    // GUSE.g:157:9: (a= associationDefinition )
            	    {
            	    // GUSE.g:157:9: (a= associationDefinition )
            	    // GUSE.g:157:11: a= associationDefinition
            	    {
            	    pushFollow(FOLLOW_associationDefinition_in_model121);
            	    a=associationDefinition();

            	    state._fsp--;

            	     n.addAssociation(a); 

            	    }


            	    }
            	    break;
            	case 3 :
            	    // GUSE.g:158:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    {
            	    // GUSE.g:158:9: ( 'constraints' (cons= invariant | ppc= prePost )* )
            	    // GUSE.g:158:11: 'constraints' (cons= invariant | ppc= prePost )*
            	    {
            	    match(input,75,FOLLOW_75_in_model137); 
            	    // GUSE.g:159:11: (cons= invariant | ppc= prePost )*
            	    loop2:
            	    do {
            	        int alt2=3;
            	        int LA2_0 = input.LA(1);

            	        if ( (LA2_0==86) ) {
            	            int LA2_2 = input.LA(2);

            	            if ( (LA2_2==IDENT) ) {
            	                int LA2_3 = input.LA(3);

            	                if ( (LA2_3==COLON_COLON) ) {
            	                    alt2=2;
            	                }
            	                else if ( (LA2_3==EOF||LA2_3==COLON||(LA2_3>=71 && LA2_3<=72)||LA2_3==75||(LA2_3>=77 && LA2_3<=78)||(LA2_3>=80 && LA2_3<=81)||LA2_3==83||(LA2_3>=86 && LA2_3<=87)) ) {
            	                    alt2=1;
            	                }


            	            }


            	        }


            	        switch (alt2) {
            	    	case 1 :
            	    	    // GUSE.g:159:15: cons= invariant
            	    	    {
            	    	    pushFollow(FOLLOW_invariant_in_model155);
            	    	    cons=invariant();

            	    	    state._fsp--;

            	    	     n.addConstraint(cons); 

            	    	    }
            	    	    break;
            	    	case 2 :
            	    	    // GUSE.g:160:15: ppc= prePost
            	    	    {
            	    	    pushFollow(FOLLOW_prePost_in_model176);
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

            match(input,EOF,FOLLOW_EOF_in_model217); 

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
    // $ANTLR end model

    // Delegated rules
    public ASTMultiplicityRange multiplicityRange() throws RecognitionException { return gGUSEBase.multiplicityRange(); }
    public ASTALWhile alWhile() throws RecognitionException { return gGUSEBase.alWhile(); }
    public ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException { return gGOCLBase.emptyCollectionLiteral(); }
    public ASTTupleType tupleType() throws RecognitionException { return gGOCLBase.tupleType(); }
    public ASTExpression ifExpression() throws RecognitionException { return gGOCLBase.ifExpression(); }
    public ASTExpression iterateExpression(ASTExpression range) throws RecognitionException { return gGOCLBase.iterateExpression(range); }
    public ASTExpression relationalExpression() throws RecognitionException { return gGOCLBase.relationalExpression(); }
    public ASTALDelete alDelete() throws RecognitionException { return gGUSEBase.alDelete(); }
    public ASTExpression queryExpression(ASTExpression range) throws RecognitionException { return gGOCLBase.queryExpression(range); }
    public ASTAssociationEnd associationEnd() throws RecognitionException { return gGUSEBase.associationEnd(); }
    public ASTExpression unaryExpression() throws RecognitionException { return gGOCLBase.unaryExpression(); }
    public ASTEnumTypeDefinition enumTypeDefinition() throws RecognitionException { return gGUSEBase.enumTypeDefinition(); }
    public ASTALActionList alActionList() throws RecognitionException { return gGUSEBase.alActionList(); }
    public ASTTupleLiteral tupleLiteral() throws RecognitionException { return gGOCLBase.tupleLiteral(); }
    public List idList() throws RecognitionException { return gGOCLBase.idList(); }
    public ASTMultiplicity multiplicity() throws RecognitionException { return gGUSEBase.multiplicity(); }
    public ASTExpression primaryExpression() throws RecognitionException { return gGOCLBase.primaryExpression(); }
    public ASTInvariantClause invariantClause() throws RecognitionException { return gGUSEBase.invariantClause(); }
    public ASTALFor alFor() throws RecognitionException { return gGUSEBase.alFor(); }
    public ASTAssociation associationDefinition() throws RecognitionException { return gGUSEBase.associationDefinition(); }
    public ASTExpression conditionalXOrExpression() throws RecognitionException { return gGOCLBase.conditionalXOrExpression(); }
    public ASTExpression conditionalAndExpression() throws RecognitionException { return gGOCLBase.conditionalAndExpression(); }
    public ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException { return gGOCLBase.elemVarsDeclaration(); }
    public ASTAssociationClass associationClassDefinition(boolean isAbstract) throws RecognitionException { return gGUSEBase.associationClassDefinition(isAbstract); }
    public int multiplicitySpec() throws RecognitionException { return gGUSEBase.multiplicitySpec(); }
    public ASTExpression expression() throws RecognitionException { return gGOCLBase.expression(); }
    public ASTConstraintDefinition invariant() throws RecognitionException { return gGUSEBase.invariant(); }
    public ASTALCreateVar alCreateVar() throws RecognitionException { return gGUSEBase.alCreateVar(); }
    public ASTExpression conditionalOrExpression() throws RecognitionException { return gGOCLBase.conditionalOrExpression(); }
    public ASTALIf alIf() throws RecognitionException { return gGUSEBase.alIf(); }
    public ASTExpression equalityExpression() throws RecognitionException { return gGOCLBase.equalityExpression(); }
    public ASTExpression literal() throws RecognitionException { return gGOCLBase.literal(); }
    public ASTALInsert alInsert() throws RecognitionException { return gGUSEBase.alInsert(); }
    public ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException { return gGOCLBase.propertyCall(source, followsArrow); }
    public ASTUndefinedLiteral undefinedLiteral() throws RecognitionException { return gGOCLBase.undefinedLiteral(); }
    public ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException { return gGOCLBase.typeExpression(source, followsArrow); }
    public List paramList() throws RecognitionException { return gGOCLBase.paramList(); }
    public ASTCollectionType collectionType() throws RecognitionException { return gGOCLBase.collectionType(); }
    public ASTClass classDefinition(boolean isAbstract) throws RecognitionException { return gGUSEBase.classDefinition(isAbstract); }
    public ASTVariableInitialization variableInitialization() throws RecognitionException { return gGOCLBase.variableInitialization(); }
    public ASTType typeOnly() throws RecognitionException { return gGOCLBase.typeOnly(); }
    public ASTALDestroy alDestroy() throws RecognitionException { return gGUSEBase.alDestroy(); }
    public ASTTupleItem tupleItem() throws RecognitionException { return gGOCLBase.tupleItem(); }
    public ASTPrePostClause prePostClause() throws RecognitionException { return gGUSEBase.prePostClause(); }
    public ASTVariableDeclaration variableDeclaration() throws RecognitionException { return gGOCLBase.variableDeclaration(); }
    public ASTALAction alAction() throws RecognitionException { return gGUSEBase.alAction(); }
    public ASTALSet alSet() throws RecognitionException { return gGUSEBase.alSet(); }
    public ASTExpression additiveExpression() throws RecognitionException { return gGOCLBase.additiveExpression(); }
    public ASTPrePost prePost() throws RecognitionException { return gGUSEBase.prePost(); }
    public ASTCollectionLiteral collectionLiteral() throws RecognitionException { return gGOCLBase.collectionLiteral(); }
    public ASTExpression multiplicativeExpression() throws RecognitionException { return gGOCLBase.multiplicativeExpression(); }
    public ASTExpression conditionalImpliesExpression() throws RecognitionException { return gGOCLBase.conditionalImpliesExpression(); }
    public ASTType type() throws RecognitionException { return gGOCLBase.type(); }
    public ASTSimpleType simpleType() throws RecognitionException { return gGOCLBase.simpleType(); }
    public ASTAttribute attributeDefinition() throws RecognitionException { return gGUSEBase.attributeDefinition(); }
    public ASTOperationExpression operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException { return gGOCLBase.operationExpression(source, followsArrow); }
    public ASTCollectionItem collectionItem() throws RecognitionException { return gGOCLBase.collectionItem(); }
    public void generalClassDefinition(ASTModel n) throws RecognitionException { gGUSEBase.generalClassDefinition(n); }
    public ASTExpression postfixExpression() throws RecognitionException { return gGOCLBase.postfixExpression(); }
    public ASTOperation operationDefinition() throws RecognitionException { return gGUSEBase.operationDefinition(); }
    public ASTTuplePart tuplePart() throws RecognitionException { return gGOCLBase.tuplePart(); }
    public ASTALExecute alExec() throws RecognitionException { return gGUSEBase.alExec(); }
    public ASTALSetCreate alSetCreate() throws RecognitionException { return gGUSEBase.alSetCreate(); }


 

    public static final BitSet FOLLOW_104_in_model71 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_model75 = new BitSet(new long[]{0x0000000000000000L,0x00000000000B69C0L});
    public static final BitSet FOLLOW_enumTypeDefinition_in_model87 = new BitSet(new long[]{0x0000000000000000L,0x00000000000B69C0L});
    public static final BitSet FOLLOW_generalClassDefinition_in_model104 = new BitSet(new long[]{0x0000000000000000L,0x00000000000B6980L});
    public static final BitSet FOLLOW_associationDefinition_in_model121 = new BitSet(new long[]{0x0000000000000000L,0x00000000000B6980L});
    public static final BitSet FOLLOW_75_in_model137 = new BitSet(new long[]{0x0000000000000000L,0x00000000004B6980L});
    public static final BitSet FOLLOW_invariant_in_model155 = new BitSet(new long[]{0x0000000000000000L,0x00000000004B6980L});
    public static final BitSet FOLLOW_prePost_in_model176 = new BitSet(new long[]{0x0000000000000000L,0x00000000004B6980L});
    public static final BitSet FOLLOW_EOF_in_model217 = new BitSet(new long[]{0x0000000000000002L});

}