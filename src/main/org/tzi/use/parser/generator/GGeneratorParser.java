// $ANTLR 3.1b1 GGenerator.g 2009-03-06 10:35:37
 
package org.tzi.use.parser.generator;

import org.tzi.use.parser.*;
import org.tzi.use.parser.ocl.*;
import org.tzi.use.parser.use.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class GGeneratorParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LITERAL_oclAsType", "LITERAL_oclIsKindOf", "LITERAL_oclIsTypeOf", "LPAREN", "COMMA", "RPAREN", "IDENT", "COLON", "EQUAL", "NOT_EQUAL", "LESS", "GREATER", "LESS_EQUAL", "GREATER_EQUAL", "PLUS", "MINUS", "STAR", "SLASH", "ARROW", "DOT", "AT", "BAR", "SEMI", "LBRACK", "RBRACK", "INT", "REAL", "STRING", "HASH", "LBRACE", "RBRACE", "DOTDOT", "COLON_COLON", "COLON_EQUAL", "NEWLINE", "WS", "SL_COMMENT", "ML_COMMENT", "RANGE_OR_INT", "ESC", "HEX_DIGIT", "VOCAB", "'let'", "'in'", "'implies'", "'or'", "'xor'", "'and'", "'div'", "'not'", "'allInstances'", "'pre'", "'iterate'", "'if'", "'then'", "'else'", "'endif'", "'true'", "'false'", "'Set'", "'Sequence'", "'Bag'", "'oclEmpty'", "'oclUndefined'", "'Tuple'", "'Collection'", "'enum'", "'abstract'", "'class'", "'attributes'", "'operations'", "'constraints'", "'end'", "'associationClass'", "'associationclass'", "'between'", "'aggregation'", "'composition'", "'begin'", "'association'", "'role'", "'ordered'", "'context'", "'inv'", "'post'", "'var'", "'declare'", "'set'", "'create'", "'namehint'", "'insert'", "'into'", "'delete'", "'from'", "'destroy'", "'while'", "'do'", "'wend'", "'for'", "'execute'", "'procedure'"
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
    public GGenerator_GUSEBase_GOCLBase gGOCLBase;
    public GGenerator_GUSEBase gGUSEBase;
    // delegators


        public GGeneratorParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public GGeneratorParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            gGUSEBase = new GGenerator_GUSEBase(input, state, this);
        }
        

    public String[] getTokenNames() { return GGeneratorParser.tokenNames; }
    public String getGrammarFileName() { return "GGenerator.g"; }


    	private ParseErrorHandler fParseErrorHandler;
        
        public void init(ParseErrorHandler handler) {
            fParseErrorHandler = handler;
    		this.gGUSEBase.init(handler);
        }
        
        /* Overridden methods. */
        public void emitErrorMessage(String msg) {
           	fParseErrorHandler.reportError(msg);
    	}
     


    // $ANTLR start invariantListOnly
    // GGenerator.g:62:1: invariantListOnly returns [List invariantList] : (def= invariant )* EOF ;
    public final List invariantListOnly() throws RecognitionException {
        List invariantList = null;

        ASTConstraintDefinition def = null;


         invariantList = new ArrayList(); 
        try {
            // GGenerator.g:64:1: ( (def= invariant )* EOF )
            // GGenerator.g:65:5: (def= invariant )* EOF
            {
            // GGenerator.g:65:5: (def= invariant )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==86) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // GGenerator.g:65:7: def= invariant
            	    {
            	    pushFollow(FOLLOW_invariant_in_invariantListOnly82);
            	    def=invariant();

            	    state._fsp--;

            	     invariantList.add(def); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            match(input,EOF,FOLLOW_EOF_in_invariantListOnly93); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return invariantList;
    }
    // $ANTLR end invariantListOnly


    // $ANTLR start procedureListOnly
    // GGenerator.g:114:1: procedureListOnly returns [List procedureList] : (proc= procedure )* EOF ;
    public final List procedureListOnly() throws RecognitionException {
        List procedureList = null;

        ASTGProcedure proc = null;


         procedureList = new ArrayList(); 
        try {
            // GGenerator.g:116:1: ( (proc= procedure )* EOF )
            // GGenerator.g:117:5: (proc= procedure )* EOF
            {
            // GGenerator.g:117:5: (proc= procedure )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==104) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // GGenerator.g:118:7: proc= procedure
            	    {
            	    pushFollow(FOLLOW_procedure_in_procedureListOnly138);
            	    proc=procedure();

            	    state._fsp--;

            	     procedureList.add(proc); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            match(input,EOF,FOLLOW_EOF_in_procedureListOnly153); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return procedureList;
    }
    // $ANTLR end procedureListOnly


    // $ANTLR start procedure
    // GGenerator.g:129:1: procedure returns [ASTGProcedure proc] : 'procedure' name= IDENT LPAREN parameterDecls= variableDeclarationList RPAREN ( 'var' localDecls= variableDeclarationList SEMI )? 'begin' instructions= instructionList 'end' SEMI ;
    public final ASTGProcedure procedure() throws RecognitionException {
        ASTGProcedure proc = null;

        Token name=null;
        List parameterDecls = null;

        List localDecls = null;

        List instructions = null;


         localDecls = new ArrayList(); 
        try {
            // GGenerator.g:131:1: ( 'procedure' name= IDENT LPAREN parameterDecls= variableDeclarationList RPAREN ( 'var' localDecls= variableDeclarationList SEMI )? 'begin' instructions= instructionList 'end' SEMI )
            // GGenerator.g:132:5: 'procedure' name= IDENT LPAREN parameterDecls= variableDeclarationList RPAREN ( 'var' localDecls= variableDeclarationList SEMI )? 'begin' instructions= instructionList 'end' SEMI
            {
            match(input,104,FOLLOW_104_in_procedure181); 
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_procedure185); 
            match(input,LPAREN,FOLLOW_LPAREN_in_procedure187); 
            pushFollow(FOLLOW_variableDeclarationList_in_procedure191);
            parameterDecls=variableDeclarationList();

            state._fsp--;

            match(input,RPAREN,FOLLOW_RPAREN_in_procedure193); 
            // GGenerator.g:133:5: ( 'var' localDecls= variableDeclarationList SEMI )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==89) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // GGenerator.g:133:7: 'var' localDecls= variableDeclarationList SEMI
                    {
                    match(input,89,FOLLOW_89_in_procedure201); 
                    pushFollow(FOLLOW_variableDeclarationList_in_procedure205);
                    localDecls=variableDeclarationList();

                    state._fsp--;

                    match(input,SEMI,FOLLOW_SEMI_in_procedure207); 

                    }
                    break;

            }

            match(input,82,FOLLOW_82_in_procedure216); 
            pushFollow(FOLLOW_instructionList_in_procedure220);
            instructions=instructionList();

            state._fsp--;

            match(input,76,FOLLOW_76_in_procedure222); 
            match(input,SEMI,FOLLOW_SEMI_in_procedure224); 
             proc = new ASTGProcedure(name, parameterDecls, localDecls, instructions ); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return proc;
    }
    // $ANTLR end procedure


    // $ANTLR start variableDeclarationList
    // GGenerator.g:142:1: variableDeclarationList returns [List varDecls] : (decl= variableDeclaration ( COMMA decl= variableDeclaration )* )? ;
    public final List variableDeclarationList() throws RecognitionException {
        List varDecls = null;

        ASTVariableDeclaration decl = null;


         varDecls = new ArrayList(); 
        try {
            // GGenerator.g:144:1: ( (decl= variableDeclaration ( COMMA decl= variableDeclaration )* )? )
            // GGenerator.g:145:5: (decl= variableDeclaration ( COMMA decl= variableDeclaration )* )?
            {
            // GGenerator.g:145:5: (decl= variableDeclaration ( COMMA decl= variableDeclaration )* )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==IDENT) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // GGenerator.g:145:7: decl= variableDeclaration ( COMMA decl= variableDeclaration )*
                    {
                    pushFollow(FOLLOW_variableDeclaration_in_variableDeclarationList262);
                    decl=variableDeclaration();

                    state._fsp--;

                    varDecls.add(decl);
                    // GGenerator.g:146:7: ( COMMA decl= variableDeclaration )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( (LA4_0==COMMA) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // GGenerator.g:146:8: COMMA decl= variableDeclaration
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_variableDeclarationList273); 
                    	    pushFollow(FOLLOW_variableDeclaration_in_variableDeclarationList277);
                    	    decl=variableDeclaration();

                    	    state._fsp--;

                    	    varDecls.add(decl);

                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);


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
        return varDecls;
    }
    // $ANTLR end variableDeclarationList


    // $ANTLR start instructionList
    // GGenerator.g:154:1: instructionList returns [List instructions] : (instr= instruction SEMI )* ;
    public final List instructionList() throws RecognitionException {
        List instructions = null;

        ASTGInstruction instr = null;


         instructions = new ArrayList(); 
        try {
            // GGenerator.g:156:1: ( (instr= instruction SEMI )* )
            // GGenerator.g:157:5: (instr= instruction SEMI )*
            {
            // GGenerator.g:157:5: (instr= instruction SEMI )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==IDENT||LA6_0==LBRACK||LA6_0==57||LA6_0==102) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // GGenerator.g:157:7: instr= instruction SEMI
            	    {
            	    pushFollow(FOLLOW_instruction_in_instructionList321);
            	    instr=instruction();

            	    state._fsp--;

            	    match(input,SEMI,FOLLOW_SEMI_in_instructionList323); 
            	    instructions.add(instr);

            	    }
            	    break;

            	default :
            	    break loop6;
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
        return instructions;
    }
    // $ANTLR end instructionList


    // $ANTLR start instruction
    // GGenerator.g:168:1: instruction returns [ASTGInstruction instr] : (instrVA= variableAssignment | instrAA= attributeAssignment | instrLO= loop | instrAI= atomicInstruction | instrIT= ifThenElse );
    public final ASTGInstruction instruction() throws RecognitionException {
        ASTGInstruction instr = null;

        ASTGVariableAssignment instrVA = null;

        ASTGAttributeAssignment instrAA = null;

        ASTGLoop instrLO = null;

        ASTGAtomicInstruction instrAI = null;

        ASTGIfThenElse instrIT = null;


        try {
            // GGenerator.g:169:1: (instrVA= variableAssignment | instrAA= attributeAssignment | instrLO= loop | instrAI= atomicInstruction | instrIT= ifThenElse )
            int alt7=5;
            switch ( input.LA(1) ) {
            case IDENT:
                {
                int LA7_1 = input.LA(2);

                if ( (LA7_1==COLON_EQUAL) ) {
                    alt7=1;
                }
                else if ( (LA7_1==LPAREN) ) {
                    alt7=4;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 1, input);

                    throw nvae;
                }
                }
                break;
            case LBRACK:
                {
                alt7=2;
                }
                break;
            case 102:
                {
                alt7=3;
                }
                break;
            case 57:
                {
                alt7=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // GGenerator.g:170:7: instrVA= variableAssignment
                    {
                    pushFollow(FOLLOW_variableAssignment_in_instruction358);
                    instrVA=variableAssignment();

                    state._fsp--;

                    instr = instrVA;

                    }
                    break;
                case 2 :
                    // GGenerator.g:171:7: instrAA= attributeAssignment
                    {
                    pushFollow(FOLLOW_attributeAssignment_in_instruction373);
                    instrAA=attributeAssignment();

                    state._fsp--;

                    instr = instrAA;

                    }
                    break;
                case 3 :
                    // GGenerator.g:172:7: instrLO= loop
                    {
                    pushFollow(FOLLOW_loop_in_instruction387);
                    instrLO=loop();

                    state._fsp--;

                    instr = instrLO;

                    }
                    break;
                case 4 :
                    // GGenerator.g:173:7: instrAI= atomicInstruction
                    {
                    pushFollow(FOLLOW_atomicInstruction_in_instruction404);
                    instrAI=atomicInstruction();

                    state._fsp--;

                    instr = instrAI;

                    }
                    break;
                case 5 :
                    // GGenerator.g:174:7: instrIT= ifThenElse
                    {
                    pushFollow(FOLLOW_ifThenElse_in_instruction418);
                    instrIT=ifThenElse();

                    state._fsp--;

                    instr = instrIT;

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
        return instr;
    }
    // $ANTLR end instruction


    // $ANTLR start variableAssignment
    // GGenerator.g:181:1: variableAssignment returns [ASTGVariableAssignment assignment] : target= IDENT COLON_EQUAL source= valueInstruction ;
    public final ASTGVariableAssignment variableAssignment() throws RecognitionException {
        ASTGVariableAssignment assignment = null;

        Token target=null;
        ASTGValueInstruction source = null;


        try {
            // GGenerator.g:182:1: (target= IDENT COLON_EQUAL source= valueInstruction )
            // GGenerator.g:183:5: target= IDENT COLON_EQUAL source= valueInstruction
            {
            target=(Token)match(input,IDENT,FOLLOW_IDENT_in_variableAssignment448); 
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_variableAssignment450); 
            pushFollow(FOLLOW_valueInstruction_in_variableAssignment454);
            source=valueInstruction();

            state._fsp--;

             assignment = new ASTGVariableAssignment( target, source ); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return assignment;
    }
    // $ANTLR end variableAssignment


    // $ANTLR start attributeAssignment
    // GGenerator.g:191:1: attributeAssignment returns [ASTGAttributeAssignment assignment] : targetObject= oclExpression DOT attributeName= IDENT COLON_EQUAL source= valueInstruction ;
    public final ASTGAttributeAssignment attributeAssignment() throws RecognitionException {
        ASTGAttributeAssignment assignment = null;

        Token attributeName=null;
        ASTGocl targetObject = null;

        ASTGValueInstruction source = null;


        try {
            // GGenerator.g:192:1: (targetObject= oclExpression DOT attributeName= IDENT COLON_EQUAL source= valueInstruction )
            // GGenerator.g:193:5: targetObject= oclExpression DOT attributeName= IDENT COLON_EQUAL source= valueInstruction
            {
            pushFollow(FOLLOW_oclExpression_in_attributeAssignment486);
            targetObject=oclExpression();

            state._fsp--;

            match(input,DOT,FOLLOW_DOT_in_attributeAssignment488); 
            attributeName=(Token)match(input,IDENT,FOLLOW_IDENT_in_attributeAssignment492); 
            match(input,COLON_EQUAL,FOLLOW_COLON_EQUAL_in_attributeAssignment500); 
            pushFollow(FOLLOW_valueInstruction_in_attributeAssignment504);
            source=valueInstruction();

            state._fsp--;

             assignment = new ASTGAttributeAssignment(
            			 targetObject, attributeName, source ); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return assignment;
    }
    // $ANTLR end attributeAssignment


    // $ANTLR start loop
    // GGenerator.g:203:1: loop returns [ASTGLoop loop] : t= 'for' decl= variableDeclaration 'in' sequence= oclExpression 'begin' instructions= instructionList 'end' ;
    public final ASTGLoop loop() throws RecognitionException {
        ASTGLoop loop = null;

        Token t=null;
        ASTVariableDeclaration decl = null;

        ASTGocl sequence = null;

        List instructions = null;


        try {
            // GGenerator.g:204:1: (t= 'for' decl= variableDeclaration 'in' sequence= oclExpression 'begin' instructions= instructionList 'end' )
            // GGenerator.g:205:5: t= 'for' decl= variableDeclaration 'in' sequence= oclExpression 'begin' instructions= instructionList 'end'
            {
            t=(Token)match(input,102,FOLLOW_102_in_loop536); 
            pushFollow(FOLLOW_variableDeclaration_in_loop540);
            decl=variableDeclaration();

            state._fsp--;

            match(input,47,FOLLOW_47_in_loop542); 
            pushFollow(FOLLOW_oclExpression_in_loop546);
            sequence=oclExpression();

            state._fsp--;

            match(input,82,FOLLOW_82_in_loop548); 
            pushFollow(FOLLOW_instructionList_in_loop559);
            instructions=instructionList();

            state._fsp--;

            match(input,76,FOLLOW_76_in_loop561); 
             loop = new ASTGLoop( decl, sequence, instructions, t ); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return loop;
    }
    // $ANTLR end loop


    // $ANTLR start ifThenElse
    // GGenerator.g:216:1: ifThenElse returns [ASTGIfThenElse ifThenElse] : token= 'if' sequence= oclExpression 'then' 'begin' thenInstructions= instructionList 'end' ( 'else' 'begin' elseInstructions= instructionList 'end' )? ;
    public final ASTGIfThenElse ifThenElse() throws RecognitionException {
        ASTGIfThenElse ifThenElse = null;

        Token token=null;
        ASTGocl sequence = null;

        List thenInstructions = null;

        List elseInstructions = null;


         List elseInstructionsList = new ArrayList(); 
        try {
            // GGenerator.g:218:1: (token= 'if' sequence= oclExpression 'then' 'begin' thenInstructions= instructionList 'end' ( 'else' 'begin' elseInstructions= instructionList 'end' )? )
            // GGenerator.g:219:5: token= 'if' sequence= oclExpression 'then' 'begin' thenInstructions= instructionList 'end' ( 'else' 'begin' elseInstructions= instructionList 'end' )?
            {
            token=(Token)match(input,57,FOLLOW_57_in_ifThenElse597); 
            pushFollow(FOLLOW_oclExpression_in_ifThenElse601);
            sequence=oclExpression();

            state._fsp--;

            match(input,58,FOLLOW_58_in_ifThenElse612); 
            match(input,82,FOLLOW_82_in_ifThenElse614); 
            pushFollow(FOLLOW_instructionList_in_ifThenElse618);
            thenInstructions=instructionList();

            state._fsp--;

            match(input,76,FOLLOW_76_in_ifThenElse620); 
            // GGenerator.g:221:9: ( 'else' 'begin' elseInstructions= instructionList 'end' )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==59) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // GGenerator.g:221:10: 'else' 'begin' elseInstructions= instructionList 'end'
                    {
                    match(input,59,FOLLOW_59_in_ifThenElse631); 
                    match(input,82,FOLLOW_82_in_ifThenElse633); 
                    pushFollow(FOLLOW_instructionList_in_ifThenElse637);
                    elseInstructions=instructionList();

                    state._fsp--;

                    match(input,76,FOLLOW_76_in_ifThenElse639); 
                     elseInstructionsList=elseInstructions; 

                    }
                    break;

            }

             ifThenElse = new ASTGIfThenElse( sequence, thenInstructions,
                            elseInstructionsList, token ); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ifThenElse;
    }
    // $ANTLR end ifThenElse


    // $ANTLR start valueInstruction
    // GGenerator.g:230:1: valueInstruction returns [ASTGValueInstruction valueinstr] : (atmoicInstr= atomicInstruction | oclExpr= oclExpression );
    public final ASTGValueInstruction valueInstruction() throws RecognitionException {
        ASTGValueInstruction valueinstr = null;

        ASTGAtomicInstruction atmoicInstr = null;

        ASTGocl oclExpr = null;


        try {
            // GGenerator.g:231:1: (atmoicInstr= atomicInstruction | oclExpr= oclExpression )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==IDENT) ) {
                alt9=1;
            }
            else if ( (LA9_0==LBRACK) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // GGenerator.g:232:7: atmoicInstr= atomicInstruction
                    {
                    pushFollow(FOLLOW_atomicInstruction_in_valueInstruction679);
                    atmoicInstr=atomicInstruction();

                    state._fsp--;

                    valueinstr = atmoicInstr; 

                    }
                    break;
                case 2 :
                    // GGenerator.g:233:7: oclExpr= oclExpression
                    {
                    pushFollow(FOLLOW_oclExpression_in_valueInstruction693);
                    oclExpr=oclExpression();

                    state._fsp--;

                    valueinstr = oclExpr; 

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
        return valueinstr;
    }
    // $ANTLR end valueInstruction


    // $ANTLR start atomicInstruction
    // GGenerator.g:241:1: atomicInstruction returns [ASTGAtomicInstruction instr] : name= IDENT LPAREN (parameter= instructionParameter ( COMMA parameter= instructionParameter )* )? RPAREN ;
    public final ASTGAtomicInstruction atomicInstruction() throws RecognitionException {
        ASTGAtomicInstruction instr = null;

        Token name=null;
        Object parameter = null;


        try {
            // GGenerator.g:242:1: (name= IDENT LPAREN (parameter= instructionParameter ( COMMA parameter= instructionParameter )* )? RPAREN )
            // GGenerator.g:243:5: name= IDENT LPAREN (parameter= instructionParameter ( COMMA parameter= instructionParameter )* )? RPAREN
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_atomicInstruction723); 
             instr = new ASTGAtomicInstruction(name); 
            match(input,LPAREN,FOLLOW_LPAREN_in_atomicInstruction727); 
            // GGenerator.g:244:9: (parameter= instructionParameter ( COMMA parameter= instructionParameter )* )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==IDENT||LA11_0==LBRACK) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // GGenerator.g:244:11: parameter= instructionParameter ( COMMA parameter= instructionParameter )*
                    {
                    pushFollow(FOLLOW_instructionParameter_in_atomicInstruction741);
                    parameter=instructionParameter();

                    state._fsp--;

                     instr.addParameter(parameter); 
                    // GGenerator.g:245:13: ( COMMA parameter= instructionParameter )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0==COMMA) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // GGenerator.g:245:15: COMMA parameter= instructionParameter
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_atomicInstruction759); 
                    	    pushFollow(FOLLOW_instructionParameter_in_atomicInstruction763);
                    	    parameter=instructionParameter();

                    	    state._fsp--;

                    	     instr.addParameter(parameter); 

                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_atomicInstruction811); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return instr;
    }
    // $ANTLR end atomicInstruction


    // $ANTLR start instructionParameter
    // GGenerator.g:256:1: instructionParameter returns [Object parameter] : (parameterOcl= oclExpression | parameterIdent= instrParameterIdent );
    public final Object instructionParameter() throws RecognitionException {
        Object parameter = null;

        ASTGocl parameterOcl = null;

        Token parameterIdent = null;


        try {
            // GGenerator.g:257:1: (parameterOcl= oclExpression | parameterIdent= instrParameterIdent )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==LBRACK) ) {
                alt12=1;
            }
            else if ( (LA12_0==IDENT) ) {
                alt12=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // GGenerator.g:258:7: parameterOcl= oclExpression
                    {
                    pushFollow(FOLLOW_oclExpression_in_instructionParameter839);
                    parameterOcl=oclExpression();

                    state._fsp--;

                    parameter = parameterOcl; 

                    }
                    break;
                case 2 :
                    // GGenerator.g:259:7: parameterIdent= instrParameterIdent
                    {
                    pushFollow(FOLLOW_instrParameterIdent_in_instructionParameter851);
                    parameterIdent=instrParameterIdent();

                    state._fsp--;

                    parameter = parameterIdent; 

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
        return parameter;
    }
    // $ANTLR end instructionParameter


    // $ANTLR start instrParameterIdent
    // GGenerator.g:266:1: instrParameterIdent returns [Token t] : i= IDENT ;
    public final Token instrParameterIdent() throws RecognitionException {
        Token t = null;

        Token i=null;

        try {
            // GGenerator.g:267:1: (i= IDENT )
            // GGenerator.g:268:5: i= IDENT
            {
            i=(Token)match(input,IDENT,FOLLOW_IDENT_in_instrParameterIdent879); 
             t = i; 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return t;
    }
    // $ANTLR end instrParameterIdent


    // $ANTLR start oclExpression
    // GGenerator.g:275:1: oclExpression returns [ASTGocl encapOcl] : i= LBRACK ocl= expression RBRACK ;
    public final ASTGocl oclExpression() throws RecognitionException {
        ASTGocl encapOcl = null;

        Token i=null;
        ASTExpression ocl = null;


        try {
            // GGenerator.g:276:1: (i= LBRACK ocl= expression RBRACK )
            // GGenerator.g:277:5: i= LBRACK ocl= expression RBRACK
            {
            i=(Token)match(input,LBRACK,FOLLOW_LBRACK_in_oclExpression907); 
            pushFollow(FOLLOW_expression_in_oclExpression911);
            ocl=expression();

            state._fsp--;

            match(input,RBRACK,FOLLOW_RBRACK_in_oclExpression913); 
             encapOcl = new ASTGocl(ocl, i); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return encapOcl;
    }
    // $ANTLR end oclExpression


    // $ANTLR start procedureCallOnly
    // GGenerator.g:290:1: procedureCallOnly returns [ASTGProcedureCall call] : name= IDENT LPAREN (ocl= expression ( COMMA ocl= expression )* )? RPAREN EOF ;
    public final ASTGProcedureCall procedureCallOnly() throws RecognitionException {
        ASTGProcedureCall call = null;

        Token name=null;
        ASTExpression ocl = null;


        try {
            // GGenerator.g:291:1: (name= IDENT LPAREN (ocl= expression ( COMMA ocl= expression )* )? RPAREN EOF )
            // GGenerator.g:292:5: name= IDENT LPAREN (ocl= expression ( COMMA ocl= expression )* )? RPAREN EOF
            {
            name=(Token)match(input,IDENT,FOLLOW_IDENT_in_procedureCallOnly948); 
            call = new ASTGProcedureCall(name);
            match(input,LPAREN,FOLLOW_LPAREN_in_procedureCallOnly956); 
            // GGenerator.g:293:12: (ocl= expression ( COMMA ocl= expression )* )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( ((LA14_0>=LITERAL_oclAsType && LA14_0<=LPAREN)||LA14_0==IDENT||(LA14_0>=PLUS && LA14_0<=MINUS)||(LA14_0>=INT && LA14_0<=HASH)||LA14_0==46||LA14_0==53||(LA14_0>=56 && LA14_0<=57)||(LA14_0>=61 && LA14_0<=68)) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // GGenerator.g:294:5: ocl= expression ( COMMA ocl= expression )*
                    {
                    pushFollow(FOLLOW_expression_in_procedureCallOnly967);
                    ocl=expression();

                    state._fsp--;

                    call.addParameter(ocl);
                    // GGenerator.g:295:5: ( COMMA ocl= expression )*
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( (LA13_0==COMMA) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // GGenerator.g:295:7: COMMA ocl= expression
                    	    {
                    	    match(input,COMMA,FOLLOW_COMMA_in_procedureCallOnly977); 
                    	    pushFollow(FOLLOW_expression_in_procedureCallOnly981);
                    	    ocl=expression();

                    	    state._fsp--;

                    	    call.addParameter(ocl);

                    	    }
                    	    break;

                    	default :
                    	    break loop13;
                        }
                    } while (true);


                    }
                    break;

            }

            match(input,RPAREN,FOLLOW_RPAREN_in_procedureCallOnly995); 
            match(input,EOF,FOLLOW_EOF_in_procedureCallOnly1001); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return call;
    }
    // $ANTLR end procedureCallOnly

    // Delegated rules
    public ASTExpression conditionalAndExpression() throws RecognitionException { return gGOCLBase.conditionalAndExpression(); }
    public ASTTupleLiteral tupleLiteral() throws RecognitionException { return gGOCLBase.tupleLiteral(); }
    public ASTExpression equalityExpression() throws RecognitionException { return gGOCLBase.equalityExpression(); }
    public ASTMultiplicity multiplicity() throws RecognitionException { return gGUSEBase.multiplicity(); }
    public ASTExpression literal() throws RecognitionException { return gGOCLBase.literal(); }
    public ASTVariableDeclaration variableDeclaration() throws RecognitionException { return gGOCLBase.variableDeclaration(); }
    public ASTALExecute alExec() throws RecognitionException { return gGUSEBase.alExec(); }
    public int multiplicitySpec() throws RecognitionException { return gGUSEBase.multiplicitySpec(); }
    public void generalClassDefinition(ASTModel n) throws RecognitionException { gGUSEBase.generalClassDefinition(n); }
    public ASTTypeArgExpression typeExpression(ASTExpression source, boolean followsArrow) throws RecognitionException { return gGOCLBase.typeExpression(source, followsArrow); }
    public ASTOperationExpression operationExpression(ASTExpression source, boolean followsArrow) throws RecognitionException { return gGOCLBase.operationExpression(source, followsArrow); }
    public ASTType typeOnly() throws RecognitionException { return gGOCLBase.typeOnly(); }
    public ASTALDelete alDelete() throws RecognitionException { return gGUSEBase.alDelete(); }
    public ASTALSet alSet() throws RecognitionException { return gGUSEBase.alSet(); }
    public ASTTupleType tupleType() throws RecognitionException { return gGOCLBase.tupleType(); }
    public ASTUndefinedLiteral undefinedLiteral() throws RecognitionException { return gGOCLBase.undefinedLiteral(); }
    public ASTEnumTypeDefinition enumTypeDefinition() throws RecognitionException { return gGUSEBase.enumTypeDefinition(); }
    public ASTExpression expression() throws RecognitionException { return gGOCLBase.expression(); }
    public ASTTuplePart tuplePart() throws RecognitionException { return gGOCLBase.tuplePart(); }
    public ASTExpression postfixExpression() throws RecognitionException { return gGOCLBase.postfixExpression(); }
    public ASTALCreateVar alCreateVar() throws RecognitionException { return gGUSEBase.alCreateVar(); }
    public ASTPrePost prePost() throws RecognitionException { return gGUSEBase.prePost(); }
    public ASTAssociationEnd associationEnd() throws RecognitionException { return gGUSEBase.associationEnd(); }
    public ASTExpression conditionalImpliesExpression() throws RecognitionException { return gGOCLBase.conditionalImpliesExpression(); }
    public ASTPrePostClause prePostClause() throws RecognitionException { return gGUSEBase.prePostClause(); }
    public ASTCollectionType collectionType() throws RecognitionException { return gGOCLBase.collectionType(); }
    public ASTClass classDefinition(boolean isAbstract) throws RecognitionException { return gGUSEBase.classDefinition(isAbstract); }
    public ASTAttribute attributeDefinition() throws RecognitionException { return gGUSEBase.attributeDefinition(); }
    public ASTElemVarsDeclaration elemVarsDeclaration() throws RecognitionException { return gGOCLBase.elemVarsDeclaration(); }
    public ASTExpression unaryExpression() throws RecognitionException { return gGOCLBase.unaryExpression(); }
    public ASTExpression conditionalXOrExpression() throws RecognitionException { return gGOCLBase.conditionalXOrExpression(); }
    public ASTALFor alFor() throws RecognitionException { return gGUSEBase.alFor(); }
    public List idList() throws RecognitionException { return gGOCLBase.idList(); }
    public ASTMultiplicityRange multiplicityRange() throws RecognitionException { return gGUSEBase.multiplicityRange(); }
    public ASTVariableInitialization variableInitialization() throws RecognitionException { return gGOCLBase.variableInitialization(); }
    public ASTTupleItem tupleItem() throws RecognitionException { return gGOCLBase.tupleItem(); }
    public ASTAssociationClass associationClassDefinition(boolean isAbstract) throws RecognitionException { return gGUSEBase.associationClassDefinition(isAbstract); }
    public ASTExpression propertyCall(ASTExpression source, boolean followsArrow) throws RecognitionException { return gGOCLBase.propertyCall(source, followsArrow); }
    public ASTConstraintDefinition invariant() throws RecognitionException { return gGUSEBase.invariant(); }
    public ASTALActionList alActionList() throws RecognitionException { return gGUSEBase.alActionList(); }
    public ASTExpression multiplicativeExpression() throws RecognitionException { return gGOCLBase.multiplicativeExpression(); }
    public ASTExpression additiveExpression() throws RecognitionException { return gGOCLBase.additiveExpression(); }
    public ASTEmptyCollectionLiteral emptyCollectionLiteral() throws RecognitionException { return gGOCLBase.emptyCollectionLiteral(); }
    public ASTALWhile alWhile() throws RecognitionException { return gGUSEBase.alWhile(); }
    public ASTALInsert alInsert() throws RecognitionException { return gGUSEBase.alInsert(); }
    public ASTExpression conditionalOrExpression() throws RecognitionException { return gGOCLBase.conditionalOrExpression(); }
    public ASTExpression primaryExpression() throws RecognitionException { return gGOCLBase.primaryExpression(); }
    public ASTInvariantClause invariantClause() throws RecognitionException { return gGUSEBase.invariantClause(); }
    public ASTCollectionItem collectionItem() throws RecognitionException { return gGOCLBase.collectionItem(); }
    public ASTALDestroy alDestroy() throws RecognitionException { return gGUSEBase.alDestroy(); }
    public ASTAssociation associationDefinition() throws RecognitionException { return gGUSEBase.associationDefinition(); }
    public ASTExpression relationalExpression() throws RecognitionException { return gGOCLBase.relationalExpression(); }
    public ASTExpression queryExpression(ASTExpression range) throws RecognitionException { return gGOCLBase.queryExpression(range); }
    public List paramList() throws RecognitionException { return gGOCLBase.paramList(); }
    public ASTSimpleType simpleType() throws RecognitionException { return gGOCLBase.simpleType(); }
    public ASTCollectionLiteral collectionLiteral() throws RecognitionException { return gGOCLBase.collectionLiteral(); }
    public ASTType type() throws RecognitionException { return gGOCLBase.type(); }
    public ASTALSetCreate alSetCreate() throws RecognitionException { return gGUSEBase.alSetCreate(); }
    public ASTExpression ifExpression() throws RecognitionException { return gGOCLBase.ifExpression(); }
    public ASTOperation operationDefinition() throws RecognitionException { return gGUSEBase.operationDefinition(); }
    public ASTALIf alIf() throws RecognitionException { return gGUSEBase.alIf(); }
    public ASTExpression iterateExpression(ASTExpression range) throws RecognitionException { return gGOCLBase.iterateExpression(range); }
    public ASTALAction alAction() throws RecognitionException { return gGUSEBase.alAction(); }


 

    public static final BitSet FOLLOW_invariant_in_invariantListOnly82 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_EOF_in_invariantListOnly93 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_procedure_in_procedureListOnly138 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_EOF_in_procedureListOnly153 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_104_in_procedure181 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_procedure185 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_procedure187 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_variableDeclarationList_in_procedure191 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_RPAREN_in_procedure193 = new BitSet(new long[]{0x0000000000000000L,0x0000000002040000L});
    public static final BitSet FOLLOW_89_in_procedure201 = new BitSet(new long[]{0x0000000004000400L});
    public static final BitSet FOLLOW_variableDeclarationList_in_procedure205 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_SEMI_in_procedure207 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_procedure216 = new BitSet(new long[]{0x0200000008000400L,0x0000004000001000L});
    public static final BitSet FOLLOW_instructionList_in_procedure220 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_procedure222 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_SEMI_in_procedure224 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableDeclaration_in_variableDeclarationList262 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_COMMA_in_variableDeclarationList273 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_variableDeclaration_in_variableDeclarationList277 = new BitSet(new long[]{0x0000000000000102L});
    public static final BitSet FOLLOW_instruction_in_instructionList321 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_SEMI_in_instructionList323 = new BitSet(new long[]{0x0200000008000402L,0x0000004000000000L});
    public static final BitSet FOLLOW_variableAssignment_in_instruction358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attributeAssignment_in_instruction373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_loop_in_instruction387 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicInstruction_in_instruction404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifThenElse_in_instruction418 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_variableAssignment448 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_variableAssignment450 = new BitSet(new long[]{0x0000000008000400L});
    public static final BitSet FOLLOW_valueInstruction_in_variableAssignment454 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oclExpression_in_attributeAssignment486 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_DOT_in_attributeAssignment488 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_IDENT_in_attributeAssignment492 = new BitSet(new long[]{0x0000002000000000L});
    public static final BitSet FOLLOW_COLON_EQUAL_in_attributeAssignment500 = new BitSet(new long[]{0x0000000008000400L});
    public static final BitSet FOLLOW_valueInstruction_in_attributeAssignment504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_102_in_loop536 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_variableDeclaration_in_loop540 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_47_in_loop542 = new BitSet(new long[]{0x0000000008000400L});
    public static final BitSet FOLLOW_oclExpression_in_loop546 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_loop548 = new BitSet(new long[]{0x0200000008000400L,0x0000004000001000L});
    public static final BitSet FOLLOW_instructionList_in_loop559 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_loop561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_57_in_ifThenElse597 = new BitSet(new long[]{0x0000000008000400L});
    public static final BitSet FOLLOW_oclExpression_in_ifThenElse601 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_58_in_ifThenElse612 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_ifThenElse614 = new BitSet(new long[]{0x0200000008000400L,0x0000004000001000L});
    public static final BitSet FOLLOW_instructionList_in_ifThenElse618 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_ifThenElse620 = new BitSet(new long[]{0x0800000000000002L});
    public static final BitSet FOLLOW_59_in_ifThenElse631 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_82_in_ifThenElse633 = new BitSet(new long[]{0x0200000008000400L,0x0000004000001000L});
    public static final BitSet FOLLOW_instructionList_in_ifThenElse637 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_76_in_ifThenElse639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicInstruction_in_valueInstruction679 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oclExpression_in_valueInstruction693 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_atomicInstruction723 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_atomicInstruction727 = new BitSet(new long[]{0x0000000008000600L});
    public static final BitSet FOLLOW_instructionParameter_in_atomicInstruction741 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_COMMA_in_atomicInstruction759 = new BitSet(new long[]{0x0000000008000400L});
    public static final BitSet FOLLOW_instructionParameter_in_atomicInstruction763 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_RPAREN_in_atomicInstruction811 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oclExpression_in_instructionParameter839 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_instrParameterIdent_in_instructionParameter851 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_instrParameterIdent879 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACK_in_oclExpression907 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_oclExpression911 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_RBRACK_in_oclExpression913 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENT_in_procedureCallOnly948 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_LPAREN_in_procedureCallOnly956 = new BitSet(new long[]{0xE3204001E00C06F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_procedureCallOnly967 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_COMMA_in_procedureCallOnly977 = new BitSet(new long[]{0xE3204001E00C04F0L,0x000000000000001FL});
    public static final BitSet FOLLOW_expression_in_procedureCallOnly981 = new BitSet(new long[]{0x0000000000000300L});
    public static final BitSet FOLLOW_RPAREN_in_procedureCallOnly995 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_EOF_in_procedureCallOnly1001 = new BitSet(new long[]{0x0000000000000002L});

}