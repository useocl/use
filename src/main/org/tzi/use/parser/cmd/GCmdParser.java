// $ANTLR 2.7.4: "expandedcmd.g" -> "GCmdParser.java"$
 
package org.tzi.use.parser.cmd;

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import org.tzi.use.parser.ParseErrorHandler;
import org.tzi.use.parser.MyToken;

import org.tzi.use.parser.ocl.*;
import org.tzi.use.parser.use.*;

public class GCmdParser extends antlr.LLkParser       implements GCmdTokenTypes
 {
  
    final static String Q_COLLECT  = "collect";
    final static String Q_SELECT   = "select";
    final static String Q_REJECT   = "reject";
    final static String Q_FORALL   = "forAll";
    final static String Q_EXISTS   = "exists";
    final static String Q_ISUNIQUE = "isUnique";
    final static String Q_SORTEDBY = "sortedBy";
    final static String Q_ANY      = "any";
    final static String Q_ONE      = "one";

    final static int Q_COLLECT_ID  = 1;
    final static int Q_SELECT_ID   = 2;
    final static int Q_REJECT_ID   = 3;
    final static int Q_FORALL_ID   = 4;
    final static int Q_EXISTS_ID   = 5;
    final static int Q_ISUNIQUE_ID = 6;
    final static int Q_SORTEDBY_ID = 7;
    final static int Q_ANY_ID      = 8;
    final static int Q_ONE_ID      = 9;

    final static HashMap queryIdentMap = new HashMap();

    static {
        queryIdentMap.put(Q_COLLECT,  new Integer(Q_COLLECT_ID));
        queryIdentMap.put(Q_SELECT,   new Integer(Q_SELECT_ID));
        queryIdentMap.put(Q_REJECT,   new Integer(Q_REJECT_ID));
        queryIdentMap.put(Q_FORALL,   new Integer(Q_FORALL_ID));
        queryIdentMap.put(Q_EXISTS,   new Integer(Q_EXISTS_ID));
        queryIdentMap.put(Q_ISUNIQUE, new Integer(Q_ISUNIQUE_ID));
        queryIdentMap.put(Q_SORTEDBY, new Integer(Q_SORTEDBY_ID));
        queryIdentMap.put(Q_ANY,      new Integer(Q_ANY_ID));
        queryIdentMap.put(Q_ONE,      new Integer(Q_ONE_ID));
    }

    protected boolean isQueryIdent(Token t) {
        return queryIdentMap.containsKey(t.getText());
    }
    
    private int fNest = 0;
    
    public void traceIn(String rname) throws TokenStreamException {
        for (int i = 0; i < fNest; i++)
            System.out.print(" ");
        super.traceIn(rname);
        fNest++;
    }

    public void traceOut(String rname) throws TokenStreamException {
        fNest--;
        for (int i = 0; i < fNest; i++)
            System.out.print(" ");
        super.traceOut(rname);
    }
    
    public void init(ParseErrorHandler handler) {
        fParseErrorHandler = handler;
    }

    /* Overridden methods. */
	private ParseErrorHandler fParseErrorHandler;
    
    public void reportError(RecognitionException ex) {
        fParseErrorHandler.reportError(
	        ex.getLine() + ":" +ex.getColumn() + ": " + ex.getMessage());
    }

protected GCmdParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public GCmdParser(TokenBuffer tokenBuf) {
  this(tokenBuf,5);
}

protected GCmdParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public GCmdParser(TokenStream lexer) {
  this(lexer,5);
}

public GCmdParser(ParserSharedInputState state) {
  super(state,5);
  tokenNames = _tokenNames;
}

	public final ASTCmdList  cmdList() throws RecognitionException, TokenStreamException {
		ASTCmdList cmdList;
		
		cmdList = new ASTCmdList(); ASTCmd c;
		
		try {      // for error handling
			c=cmd();
			cmdList.add(c);
			{
			_loop3:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					c=cmd();
					cmdList.add(c);
				}
				else {
					break _loop3;
				}
				
			} while (true);
			}
			match(Token.EOF_TYPE);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_1);
		}
		return cmdList;
	}
	
	public final ASTCmd  cmd() throws RecognitionException, TokenStreamException {
		ASTCmd n;
		
		n = null;
		
		try {      // for error handling
			n=cmdStmt();
			{
			switch ( LA(1)) {
			case SEMI:
			{
				match(SEMI);
				break;
			}
			case EOF:
			case LITERAL_let:
			case LITERAL_create:
			case LITERAL_assign:
			case LITERAL_destroy:
			case LITERAL_insert:
			case LITERAL_delete:
			case LITERAL_set:
			case LITERAL_openter:
			case LITERAL_opexit:
			case LITERAL_execute:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_2);
		}
		return n;
	}
	
	public final ASTCmd  cmdStmt() throws RecognitionException, TokenStreamException {
		ASTCmd n;
		
		n = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_assign:
			{
				n=createAssignCmd();
				break;
			}
			case LITERAL_destroy:
			{
				n=destroyCmd();
				break;
			}
			case LITERAL_insert:
			{
				n=insertCmd();
				break;
			}
			case LITERAL_delete:
			{
				n=deleteCmd();
				break;
			}
			case LITERAL_set:
			{
				n=setCmd();
				break;
			}
			case LITERAL_openter:
			{
				n=opEnterCmd();
				break;
			}
			case LITERAL_opexit:
			{
				n=opExitCmd();
				break;
			}
			case LITERAL_let:
			{
				n=letCmd();
				break;
			}
			case LITERAL_execute:
			{
				n=executeCmd();
				break;
			}
			default:
				if ((LA(1)==LITERAL_create) && (LA(2)==IDENT) && (LA(3)==COMMA||LA(3)==COLON) && (LA(4)==IDENT) && (_tokenSet_3.member(LA(5)))) {
					n=createCmd();
				}
				else if ((LA(1)==LITERAL_create) && (LA(2)==IDENT) && (LA(3)==COLON) && (LA(4)==IDENT) && (LA(5)==LITERAL_between)) {
					n=createInsertCmd();
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_4);
		}
		return n;
	}
	
	public final ASTCreateCmd  createCmd() throws RecognitionException, TokenStreamException {
		ASTCreateCmd n;
		
		List idList; ASTType t; n = null;
		
		try {      // for error handling
			match(LITERAL_create);
			idList=idList();
			match(COLON);
			t=simpleType();
			n = new ASTCreateCmd(idList, t);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_4);
		}
		return n;
	}
	
	public final ASTCreateAssignCmd  createAssignCmd() throws RecognitionException, TokenStreamException {
		ASTCreateAssignCmd n;
		
		List idList; ASTType t; n = null;
		
		try {      // for error handling
			match(LITERAL_assign);
			idList=idList();
			match(COLON_EQUAL);
			match(LITERAL_create);
			t=simpleType();
			n = new ASTCreateAssignCmd(idList, t);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_4);
		}
		return n;
	}
	
	public final ASTCreateInsertCmd  createInsertCmd() throws RecognitionException, TokenStreamException {
		ASTCreateInsertCmd n;
		
		Token  id = null;
		Token  idAssoc = null;
		List idListInsert; ASTType t; n = null;
		
		try {      // for error handling
			match(LITERAL_create);
			id = LT(1);
			match(IDENT);
			match(COLON);
			idAssoc = LT(1);
			match(IDENT);
			match(LITERAL_between);
			match(LPAREN);
			idListInsert=idList();
			match(RPAREN);
			n = new ASTCreateInsertCmd( (MyToken) id, (MyToken) idAssoc, idListInsert);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_4);
		}
		return n;
	}
	
	public final ASTDestroyCmd  destroyCmd() throws RecognitionException, TokenStreamException {
		ASTDestroyCmd n;
		
		ASTExpression e = null; List exprList = new ArrayList(); n = null;
		
		try {      // for error handling
			match(LITERAL_destroy);
			e=expression();
			exprList.add(e);
			{
			_loop12:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					e=expression();
					exprList.add(e);
				}
				else {
					break _loop12;
				}
				
			} while (true);
			}
			n = new ASTDestroyCmd(exprList);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_4);
		}
		return n;
	}
	
	public final ASTInsertCmd  insertCmd() throws RecognitionException, TokenStreamException {
		ASTInsertCmd n;
		
		Token  id = null;
		ASTExpression e; List exprList = new ArrayList(); n = null;
		
		try {      // for error handling
			match(LITERAL_insert);
			match(LPAREN);
			e=expression();
			exprList.add(e);
			match(COMMA);
			e=expression();
			exprList.add(e);
			{
			_loop15:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					e=expression();
					exprList.add(e);
				}
				else {
					break _loop15;
				}
				
			} while (true);
			}
			match(RPAREN);
			match(LITERAL_into);
			id = LT(1);
			match(IDENT);
			n = new ASTInsertCmd(exprList, (MyToken) id);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_4);
		}
		return n;
	}
	
	public final ASTDeleteCmd  deleteCmd() throws RecognitionException, TokenStreamException {
		ASTDeleteCmd n;
		
		Token  id = null;
		ASTExpression e; List exprList = new ArrayList(); n = null;
		
		try {      // for error handling
			match(LITERAL_delete);
			match(LPAREN);
			e=expression();
			exprList.add(e);
			match(COMMA);
			e=expression();
			exprList.add(e);
			{
			_loop18:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					e=expression();
					exprList.add(e);
				}
				else {
					break _loop18;
				}
				
			} while (true);
			}
			match(RPAREN);
			match(LITERAL_from);
			id = LT(1);
			match(IDENT);
			n = new ASTDeleteCmd(exprList, (MyToken) id);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_4);
		}
		return n;
	}
	
	public final ASTSetCmd  setCmd() throws RecognitionException, TokenStreamException {
		ASTSetCmd n;
		
		ASTExpression e1, e2; n = null;
		
		try {      // for error handling
			match(LITERAL_set);
			e1=expression();
			match(COLON_EQUAL);
			e2=expression();
			n = new ASTSetCmd(e1, e2);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_4);
		}
		return n;
	}
	
	public final ASTOpEnterCmd  opEnterCmd() throws RecognitionException, TokenStreamException {
		ASTOpEnterCmd n;
		
		Token  id = null;
		ASTExpression e; n = null;
		
		try {      // for error handling
			match(LITERAL_openter);
			e=expression();
			id = LT(1);
			match(IDENT);
			n = new ASTOpEnterCmd(e, (MyToken) id);
			match(LPAREN);
			{
			switch ( LA(1)) {
			case LPAREN:
			case IDENT:
			case LITERAL_let:
			case PLUS:
			case MINUS:
			case LITERAL_not:
			case LITERAL_iterate:
			case LITERAL_oclAsType:
			case LITERAL_oclIsKindOf:
			case LITERAL_oclIsTypeOf:
			case LITERAL_if:
			case LITERAL_true:
			case LITERAL_false:
			case INT:
			case REAL:
			case STRING:
			case HASH:
			case LITERAL_Set:
			case LITERAL_Sequence:
			case LITERAL_Bag:
			case LITERAL_oclEmpty:
			case LITERAL_oclUndefined:
			case LITERAL_Tuple:
			{
				e=expression();
				n.addArg(e);
				{
				_loop23:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						e=expression();
						n.addArg(e);
					}
					else {
						break _loop23;
					}
					
				} while (true);
				}
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RPAREN);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_4);
		}
		return n;
	}
	
	public final ASTOpExitCmd  opExitCmd() throws RecognitionException, TokenStreamException {
		ASTOpExitCmd n;
		
		ASTExpression e = null; n = null;
		
		try {      // for error handling
			match(LITERAL_opexit);
			{
			if ((_tokenSet_5.member(LA(1))) && (_tokenSet_6.member(LA(2))) && (_tokenSet_7.member(LA(3))) && (_tokenSet_8.member(LA(4))) && (_tokenSet_9.member(LA(5)))) {
				e=expression();
			}
			else if ((_tokenSet_4.member(LA(1))) && (_tokenSet_10.member(LA(2))) && (_tokenSet_11.member(LA(3))) && (_tokenSet_12.member(LA(4))) && (_tokenSet_13.member(LA(5)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			n = new ASTOpExitCmd(e);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_4);
		}
		return n;
	}
	
	public final ASTLetCmd  letCmd() throws RecognitionException, TokenStreamException {
		ASTLetCmd n;
		
		Token  name = null;
		ASTExpression e = null; ASTType t = null; n = null;
		
		try {      // for error handling
			match(LITERAL_let);
			name = LT(1);
			match(IDENT);
			{
			switch ( LA(1)) {
			case COLON:
			{
				match(COLON);
				t=type();
				break;
			}
			case EQUAL:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(EQUAL);
			e=expression();
			n = new ASTLetCmd((MyToken) name, t, e);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_4);
		}
		return n;
	}
	
	public final ASTExecuteCmd  executeCmd() throws RecognitionException, TokenStreamException {
		ASTExecuteCmd ec;
		
		
			ec = null;
			ASTExpression e = null; 
		
		
		try {      // for error handling
			match(LITERAL_execute);
			e=expression();
			ec = new ASTExecuteCmd(e);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_4);
		}
		return ec;
	}
	
	public final List  idList() throws RecognitionException, TokenStreamException {
		List idList;
		
		Token  id0 = null;
		Token  idn = null;
		idList = new ArrayList();
		
		try {      // for error handling
			id0 = LT(1);
			match(IDENT);
			idList.add((MyToken) id0);
			{
			_loop35:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					idn = LT(1);
					match(IDENT);
					idList.add((MyToken) idn);
				}
				else {
					break _loop35;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_14);
		}
		return idList;
	}
	
	public final ASTSimpleType  simpleType() throws RecognitionException, TokenStreamException {
		ASTSimpleType n;
		
		Token  name = null;
		n = null;
		
		try {      // for error handling
			name = LT(1);
			match(IDENT);
			n = new ASTSimpleType((MyToken) name);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_15);
		}
		return n;
	}
	
	public final ASTExpression  expression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		Token  name = null;
		ASTLetExpression prevLet = null, firstLet = null; ASTType t = null; 
		ASTExpression e1, e2; n = null; 
		
		
		try {      // for error handling
			MyToken tok = (MyToken) LT(1); /* remember start of expression */
			{
			_loop41:
			do {
				if ((LA(1)==LITERAL_let)) {
					match(LITERAL_let);
					name = LT(1);
					match(IDENT);
					{
					switch ( LA(1)) {
					case COLON:
					{
						match(COLON);
						t=type();
						break;
					}
					case EQUAL:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					match(EQUAL);
					e1=expression();
					match(LITERAL_in);
					ASTLetExpression nextLet = new ASTLetExpression((MyToken) name, t, e1);
					if ( firstLet == null ) 
					firstLet = nextLet;
					if ( prevLet != null ) 
					prevLet.setInExpr(nextLet);
					prevLet = nextLet;
					
				}
				else {
					break _loop41;
				}
				
			} while (true);
			}
			n=conditionalImpliesExpression();
			if ( n != null ) 
			n.setStartToken(tok);
			if ( prevLet != null ) { 
			prevLet.setInExpr(n);
			n = firstLet;
			n.setStartToken(tok);
			}
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_16);
		}
		return n;
	}
	
	public final ASTType  type() throws RecognitionException, TokenStreamException {
		ASTType n;
		
		n = null;
		
		try {      // for error handling
			MyToken tok = (MyToken) LT(1); /* remember start of type */
			{
			switch ( LA(1)) {
			case IDENT:
			{
				n=simpleType();
				break;
			}
			case LITERAL_Set:
			case LITERAL_Sequence:
			case LITERAL_Bag:
			case LITERAL_Collection:
			{
				n=collectionType();
				break;
			}
			case LITERAL_Tuple:
			{
				n=tupleType();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			n.setStartToken(tok);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_17);
		}
		return n;
	}
	
	public final List  paramList() throws RecognitionException, TokenStreamException {
		List paramList;
		
		ASTVariableDeclaration v; paramList = new ArrayList();
		
		try {      // for error handling
			match(LPAREN);
			{
			switch ( LA(1)) {
			case IDENT:
			{
				v=variableDeclaration();
				paramList.add(v);
				{
				_loop32:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						v=variableDeclaration();
						paramList.add(v);
					}
					else {
						break _loop32;
					}
					
				} while (true);
				}
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(RPAREN);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_1);
		}
		return paramList;
	}
	
	public final ASTVariableDeclaration  variableDeclaration() throws RecognitionException, TokenStreamException {
		ASTVariableDeclaration n;
		
		Token  name = null;
		ASTType t; n = null;
		
		try {      // for error handling
			name = LT(1);
			match(IDENT);
			match(COLON);
			t=type();
			n = new ASTVariableDeclaration((MyToken) name, t);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_18);
		}
		return n;
	}
	
	public final ASTExpression  expressionOnly() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		n = null;
		
		try {      // for error handling
			n=expression();
			match(Token.EOF_TYPE);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_1);
		}
		return n;
	}
	
	public final ASTExpression  conditionalImpliesExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		Token  op = null;
		ASTExpression n1; n = null;
		
		try {      // for error handling
			n=conditionalOrExpression();
			{
			_loop44:
			do {
				if ((LA(1)==LITERAL_implies)) {
					op = LT(1);
					match(LITERAL_implies);
					n1=conditionalOrExpression();
					n = new ASTBinaryExpression((MyToken) op, n, n1);
				}
				else {
					break _loop44;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_16);
		}
		return n;
	}
	
	public final ASTExpression  conditionalOrExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		Token  op = null;
		ASTExpression n1; n = null;
		
		try {      // for error handling
			n=conditionalXOrExpression();
			{
			_loop47:
			do {
				if ((LA(1)==LITERAL_or)) {
					op = LT(1);
					match(LITERAL_or);
					n1=conditionalXOrExpression();
					n = new ASTBinaryExpression((MyToken) op, n, n1);
				}
				else {
					break _loop47;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_19);
		}
		return n;
	}
	
	public final ASTExpression  conditionalXOrExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		Token  op = null;
		ASTExpression n1; n = null;
		
		try {      // for error handling
			n=conditionalAndExpression();
			{
			_loop50:
			do {
				if ((LA(1)==LITERAL_xor)) {
					op = LT(1);
					match(LITERAL_xor);
					n1=conditionalAndExpression();
					n = new ASTBinaryExpression((MyToken) op, n, n1);
				}
				else {
					break _loop50;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_20);
		}
		return n;
	}
	
	public final ASTExpression  conditionalAndExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		Token  op = null;
		ASTExpression n1; n = null;
		
		try {      // for error handling
			n=equalityExpression();
			{
			_loop53:
			do {
				if ((LA(1)==LITERAL_and)) {
					op = LT(1);
					match(LITERAL_and);
					n1=equalityExpression();
					n = new ASTBinaryExpression((MyToken) op, n, n1);
				}
				else {
					break _loop53;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_21);
		}
		return n;
	}
	
	public final ASTExpression  equalityExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		ASTExpression n1; n = null;
		
		try {      // for error handling
			n=relationalExpression();
			{
			_loop57:
			do {
				if ((LA(1)==EQUAL||LA(1)==NOT_EQUAL)) {
					MyToken op = (MyToken) LT(1);
					{
					switch ( LA(1)) {
					case EQUAL:
					{
						match(EQUAL);
						break;
					}
					case NOT_EQUAL:
					{
						match(NOT_EQUAL);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					n1=relationalExpression();
					n = new ASTBinaryExpression(op, n, n1);
				}
				else {
					break _loop57;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_22);
		}
		return n;
	}
	
	public final ASTExpression  relationalExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		ASTExpression n1; n = null;
		
		try {      // for error handling
			n=additiveExpression();
			{
			_loop61:
			do {
				if (((LA(1) >= LESS && LA(1) <= GREATER_EQUAL))) {
					MyToken op = (MyToken) LT(1);
					{
					switch ( LA(1)) {
					case LESS:
					{
						match(LESS);
						break;
					}
					case GREATER:
					{
						match(GREATER);
						break;
					}
					case LESS_EQUAL:
					{
						match(LESS_EQUAL);
						break;
					}
					case GREATER_EQUAL:
					{
						match(GREATER_EQUAL);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					n1=additiveExpression();
					n = new ASTBinaryExpression(op, n, n1);
				}
				else {
					break _loop61;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_23);
		}
		return n;
	}
	
	public final ASTExpression  additiveExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		ASTExpression n1; n = null;
		
		try {      // for error handling
			n=multiplicativeExpression();
			{
			_loop65:
			do {
				if ((LA(1)==PLUS||LA(1)==MINUS)) {
					MyToken op = (MyToken) LT(1);
					{
					switch ( LA(1)) {
					case PLUS:
					{
						match(PLUS);
						break;
					}
					case MINUS:
					{
						match(MINUS);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					n1=multiplicativeExpression();
					n = new ASTBinaryExpression(op, n, n1);
				}
				else {
					break _loop65;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_24);
		}
		return n;
	}
	
	public final ASTExpression  multiplicativeExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		ASTExpression n1; n = null;
		
		try {      // for error handling
			n=unaryExpression();
			{
			_loop69:
			do {
				if (((LA(1) >= STAR && LA(1) <= LITERAL_div))) {
					MyToken op = (MyToken) LT(1);
					{
					switch ( LA(1)) {
					case STAR:
					{
						match(STAR);
						break;
					}
					case SLASH:
					{
						match(SLASH);
						break;
					}
					case LITERAL_div:
					{
						match(LITERAL_div);
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					n1=unaryExpression();
					n = new ASTBinaryExpression(op, n, n1);
				}
				else {
					break _loop69;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_25);
		}
		return n;
	}
	
	public final ASTExpression  unaryExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		n = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case PLUS:
			case MINUS:
			case LITERAL_not:
			{
				{
				MyToken op = (MyToken) LT(1);
				{
				switch ( LA(1)) {
				case LITERAL_not:
				{
					match(LITERAL_not);
					break;
				}
				case MINUS:
				{
					match(MINUS);
					break;
				}
				case PLUS:
				{
					match(PLUS);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				n=unaryExpression();
				n = new ASTUnaryExpression((MyToken) op, n);
				}
				break;
			}
			case LPAREN:
			case IDENT:
			case LITERAL_iterate:
			case LITERAL_oclAsType:
			case LITERAL_oclIsKindOf:
			case LITERAL_oclIsTypeOf:
			case LITERAL_if:
			case LITERAL_true:
			case LITERAL_false:
			case INT:
			case REAL:
			case STRING:
			case HASH:
			case LITERAL_Set:
			case LITERAL_Sequence:
			case LITERAL_Bag:
			case LITERAL_oclEmpty:
			case LITERAL_oclUndefined:
			case LITERAL_Tuple:
			{
				n=postfixExpression();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_26);
		}
		return n;
	}
	
	public final ASTExpression  postfixExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		boolean arrow; n = null;
		
		try {      // for error handling
			n=primaryExpression();
			{
			_loop76:
			do {
				if ((LA(1)==ARROW||LA(1)==DOT)) {
					{
					switch ( LA(1)) {
					case ARROW:
					{
						match(ARROW);
						arrow = true;
						break;
					}
					case DOT:
					{
						match(DOT);
						arrow = false;
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					n=propertyCall(n, arrow);
				}
				else {
					break _loop76;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_26);
		}
		return n;
	}
	
	public final ASTExpression  primaryExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		Token  id1 = null;
		n = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_true:
			case LITERAL_false:
			case INT:
			case REAL:
			case STRING:
			case HASH:
			case LITERAL_Set:
			case LITERAL_Sequence:
			case LITERAL_Bag:
			case LITERAL_oclEmpty:
			case LITERAL_oclUndefined:
			case LITERAL_Tuple:
			{
				n=literal();
				break;
			}
			case LPAREN:
			{
				match(LPAREN);
				n=expression();
				match(RPAREN);
				break;
			}
			case LITERAL_if:
			{
				n=ifExpression();
				break;
			}
			default:
				if ((_tokenSet_27.member(LA(1))) && (_tokenSet_28.member(LA(2))) && (_tokenSet_29.member(LA(3)))) {
					n=propertyCall(null, false);
				}
				else if ((LA(1)==IDENT) && (LA(2)==DOT) && (LA(3)==LITERAL_allInstances)) {
					id1 = LT(1);
					match(IDENT);
					match(DOT);
					match(LITERAL_allInstances);
					{
					switch ( LA(1)) {
					case LPAREN:
					{
						match(LPAREN);
						match(RPAREN);
						break;
					}
					case EOF:
					case COMMA:
					case RPAREN:
					case IDENT:
					case LITERAL_let:
					case EQUAL:
					case LITERAL_in:
					case LITERAL_implies:
					case LITERAL_or:
					case LITERAL_xor:
					case LITERAL_and:
					case NOT_EQUAL:
					case LESS:
					case GREATER:
					case LESS_EQUAL:
					case GREATER_EQUAL:
					case PLUS:
					case MINUS:
					case STAR:
					case SLASH:
					case LITERAL_div:
					case ARROW:
					case DOT:
					case AT:
					case BAR:
					case SEMI:
					case LITERAL_then:
					case LITERAL_else:
					case LITERAL_endif:
					case RBRACE:
					case DOTDOT:
					case LITERAL_create:
					case LITERAL_assign:
					case COLON_EQUAL:
					case LITERAL_destroy:
					case LITERAL_insert:
					case LITERAL_delete:
					case LITERAL_set:
					case LITERAL_openter:
					case LITERAL_opexit:
					case LITERAL_execute:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					n = new ASTAllInstancesExpression((MyToken) id1);
					{
					switch ( LA(1)) {
					case AT:
					{
						match(AT);
						match(LITERAL_pre);
						n.setIsPre();
						break;
					}
					case EOF:
					case COMMA:
					case RPAREN:
					case IDENT:
					case LITERAL_let:
					case EQUAL:
					case LITERAL_in:
					case LITERAL_implies:
					case LITERAL_or:
					case LITERAL_xor:
					case LITERAL_and:
					case NOT_EQUAL:
					case LESS:
					case GREATER:
					case LESS_EQUAL:
					case GREATER_EQUAL:
					case PLUS:
					case MINUS:
					case STAR:
					case SLASH:
					case LITERAL_div:
					case ARROW:
					case DOT:
					case BAR:
					case SEMI:
					case LITERAL_then:
					case LITERAL_else:
					case LITERAL_endif:
					case RBRACE:
					case DOTDOT:
					case LITERAL_create:
					case LITERAL_assign:
					case COLON_EQUAL:
					case LITERAL_destroy:
					case LITERAL_insert:
					case LITERAL_delete:
					case LITERAL_set:
					case LITERAL_openter:
					case LITERAL_opexit:
					case LITERAL_execute:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_30);
		}
		return n;
	}
	
	public final ASTExpression  propertyCall(
		ASTExpression source, boolean followsArrow
	) throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		n = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_iterate:
			{
				n=iterateExpression(source);
				break;
			}
			case LITERAL_oclAsType:
			case LITERAL_oclIsKindOf:
			case LITERAL_oclIsTypeOf:
			{
				n=typeExpression(source, followsArrow);
				break;
			}
			default:
				if (((LA(1)==IDENT) && (LA(2)==LPAREN) && (_tokenSet_5.member(LA(3))) && (_tokenSet_31.member(LA(4))) && (_tokenSet_32.member(LA(5))))&&( isQueryIdent(LT(1)) )) {
					n=queryExpression(source);
				}
				else if ((LA(1)==IDENT) && (_tokenSet_28.member(LA(2))) && (_tokenSet_33.member(LA(3))) && (_tokenSet_34.member(LA(4))) && (_tokenSet_35.member(LA(5)))) {
					n=operationExpression(source, followsArrow);
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_30);
		}
		return n;
	}
	
	public final ASTExpression  literal() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		Token  t = null;
		Token  f = null;
		Token  i = null;
		Token  r = null;
		Token  s = null;
		Token  enumLit = null;
		n = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_true:
			{
				t = LT(1);
				match(LITERAL_true);
				n = new ASTBooleanLiteral(true);
				break;
			}
			case LITERAL_false:
			{
				f = LT(1);
				match(LITERAL_false);
				n = new ASTBooleanLiteral(false);
				break;
			}
			case INT:
			{
				i = LT(1);
				match(INT);
				n = new ASTIntegerLiteral((MyToken) i);
				break;
			}
			case REAL:
			{
				r = LT(1);
				match(REAL);
				n = new ASTRealLiteral((MyToken) r);
				break;
			}
			case STRING:
			{
				s = LT(1);
				match(STRING);
				n = new ASTStringLiteral((MyToken) s);
				break;
			}
			case HASH:
			{
				match(HASH);
				enumLit = LT(1);
				match(IDENT);
				n = new ASTEnumLiteral((MyToken) enumLit);
				break;
			}
			case LITERAL_Set:
			case LITERAL_Sequence:
			case LITERAL_Bag:
			{
				n=collectionLiteral();
				break;
			}
			case LITERAL_oclEmpty:
			{
				n=emptyCollectionLiteral();
				break;
			}
			case LITERAL_oclUndefined:
			{
				n=undefinedLiteral();
				break;
			}
			case LITERAL_Tuple:
			{
				n=tupleLiteral();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_30);
		}
		return n;
	}
	
	public final ASTExpression  ifExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		Token  i = null;
		ASTExpression cond, t, e; n = null;
		
		try {      // for error handling
			i = LT(1);
			match(LITERAL_if);
			cond=expression();
			match(LITERAL_then);
			t=expression();
			match(LITERAL_else);
			e=expression();
			match(LITERAL_endif);
			n = new ASTIfExpression((MyToken) i, cond, t, e);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_30);
		}
		return n;
	}
	
	public final ASTExpression  queryExpression(
		ASTExpression range
	) throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		Token  op = null;
		
		ASTElemVarsDeclaration decls = new ASTElemVarsDeclaration(); 
		n = null; 
		
		
		try {      // for error handling
			op = LT(1);
			match(IDENT);
			match(LPAREN);
			{
			if ((LA(1)==IDENT) && (LA(2)==COMMA||LA(2)==COLON||LA(2)==BAR)) {
				decls=elemVarsDeclaration();
				match(BAR);
			}
			else if ((_tokenSet_5.member(LA(1))) && (_tokenSet_36.member(LA(2)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			n=expression();
			match(RPAREN);
			n = new ASTQueryExpression((MyToken) op, range, decls, n);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_30);
		}
		return n;
	}
	
	public final ASTExpression  iterateExpression(
		ASTExpression range
	) throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		Token  i = null;
		
		ASTElemVarsDeclaration decls = null; 
		ASTVariableInitialization init = null; 
		n = null;
		
		
		try {      // for error handling
			i = LT(1);
			match(LITERAL_iterate);
			match(LPAREN);
			decls=elemVarsDeclaration();
			match(SEMI);
			init=variableInitialization();
			match(BAR);
			n=expression();
			match(RPAREN);
			n = new ASTIterateExpression((MyToken) i, range, decls, init, n);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_30);
		}
		return n;
	}
	
	public final ASTOperationExpression  operationExpression(
		ASTExpression source, boolean followsArrow
	) throws RecognitionException, TokenStreamException {
		ASTOperationExpression n;
		
		Token  name = null;
		Token  rolename = null;
		ASTExpression e; n = null;
		
		try {      // for error handling
			name = LT(1);
			match(IDENT);
			n = new ASTOperationExpression((MyToken) name, source, followsArrow);
			{
			switch ( LA(1)) {
			case LBRACK:
			{
				match(LBRACK);
				rolename = LT(1);
				match(IDENT);
				match(RBRACK);
				n.setExplicitRolename((MyToken) rolename);
				break;
			}
			case EOF:
			case LPAREN:
			case COMMA:
			case RPAREN:
			case IDENT:
			case LITERAL_let:
			case EQUAL:
			case LITERAL_in:
			case LITERAL_implies:
			case LITERAL_or:
			case LITERAL_xor:
			case LITERAL_and:
			case NOT_EQUAL:
			case LESS:
			case GREATER:
			case LESS_EQUAL:
			case GREATER_EQUAL:
			case PLUS:
			case MINUS:
			case STAR:
			case SLASH:
			case LITERAL_div:
			case ARROW:
			case DOT:
			case AT:
			case BAR:
			case SEMI:
			case LITERAL_then:
			case LITERAL_else:
			case LITERAL_endif:
			case RBRACE:
			case DOTDOT:
			case LITERAL_create:
			case LITERAL_assign:
			case COLON_EQUAL:
			case LITERAL_destroy:
			case LITERAL_insert:
			case LITERAL_delete:
			case LITERAL_set:
			case LITERAL_openter:
			case LITERAL_opexit:
			case LITERAL_execute:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case AT:
			{
				match(AT);
				match(LITERAL_pre);
				n.setIsPre();
				break;
			}
			case EOF:
			case LPAREN:
			case COMMA:
			case RPAREN:
			case IDENT:
			case LITERAL_let:
			case EQUAL:
			case LITERAL_in:
			case LITERAL_implies:
			case LITERAL_or:
			case LITERAL_xor:
			case LITERAL_and:
			case NOT_EQUAL:
			case LESS:
			case GREATER:
			case LESS_EQUAL:
			case GREATER_EQUAL:
			case PLUS:
			case MINUS:
			case STAR:
			case SLASH:
			case LITERAL_div:
			case ARROW:
			case DOT:
			case BAR:
			case SEMI:
			case LITERAL_then:
			case LITERAL_else:
			case LITERAL_endif:
			case RBRACE:
			case DOTDOT:
			case LITERAL_create:
			case LITERAL_assign:
			case COLON_EQUAL:
			case LITERAL_destroy:
			case LITERAL_insert:
			case LITERAL_delete:
			case LITERAL_set:
			case LITERAL_openter:
			case LITERAL_opexit:
			case LITERAL_execute:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			{
			switch ( LA(1)) {
			case LPAREN:
			{
				match(LPAREN);
				n.hasParentheses();
				{
				switch ( LA(1)) {
				case LPAREN:
				case IDENT:
				case LITERAL_let:
				case PLUS:
				case MINUS:
				case LITERAL_not:
				case LITERAL_iterate:
				case LITERAL_oclAsType:
				case LITERAL_oclIsKindOf:
				case LITERAL_oclIsTypeOf:
				case LITERAL_if:
				case LITERAL_true:
				case LITERAL_false:
				case INT:
				case REAL:
				case STRING:
				case HASH:
				case LITERAL_Set:
				case LITERAL_Sequence:
				case LITERAL_Bag:
				case LITERAL_oclEmpty:
				case LITERAL_oclUndefined:
				case LITERAL_Tuple:
				{
					e=expression();
					n.addArg(e);
					{
					_loop90:
					do {
						if ((LA(1)==COMMA)) {
							match(COMMA);
							e=expression();
							n.addArg(e);
						}
						else {
							break _loop90;
						}
						
					} while (true);
					}
					break;
				}
				case RPAREN:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				match(RPAREN);
				break;
			}
			case EOF:
			case COMMA:
			case RPAREN:
			case IDENT:
			case LITERAL_let:
			case EQUAL:
			case LITERAL_in:
			case LITERAL_implies:
			case LITERAL_or:
			case LITERAL_xor:
			case LITERAL_and:
			case NOT_EQUAL:
			case LESS:
			case GREATER:
			case LESS_EQUAL:
			case GREATER_EQUAL:
			case PLUS:
			case MINUS:
			case STAR:
			case SLASH:
			case LITERAL_div:
			case ARROW:
			case DOT:
			case BAR:
			case SEMI:
			case LITERAL_then:
			case LITERAL_else:
			case LITERAL_endif:
			case RBRACE:
			case DOTDOT:
			case LITERAL_create:
			case LITERAL_assign:
			case COLON_EQUAL:
			case LITERAL_destroy:
			case LITERAL_insert:
			case LITERAL_delete:
			case LITERAL_set:
			case LITERAL_openter:
			case LITERAL_opexit:
			case LITERAL_execute:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_30);
		}
		return n;
	}
	
	public final ASTTypeArgExpression  typeExpression(
		ASTExpression source, boolean followsArrow
	) throws RecognitionException, TokenStreamException {
		ASTTypeArgExpression n;
		
		ASTType t = null; n = null;
		
		try {      // for error handling
			MyToken opToken = (MyToken) LT(1);
			{
			switch ( LA(1)) {
			case LITERAL_oclAsType:
			{
				match(LITERAL_oclAsType);
				break;
			}
			case LITERAL_oclIsKindOf:
			{
				match(LITERAL_oclIsKindOf);
				break;
			}
			case LITERAL_oclIsTypeOf:
			{
				match(LITERAL_oclIsTypeOf);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(LPAREN);
			t=type();
			match(RPAREN);
			n = new ASTTypeArgExpression(opToken, source, t, followsArrow);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_30);
		}
		return n;
	}
	
	public final ASTElemVarsDeclaration  elemVarsDeclaration() throws RecognitionException, TokenStreamException {
		ASTElemVarsDeclaration n;
		
		List idList; ASTType t = null; n = null;
		
		try {      // for error handling
			idList=idList();
			{
			switch ( LA(1)) {
			case COLON:
			{
				match(COLON);
				t=type();
				break;
			}
			case BAR:
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			n = new ASTElemVarsDeclaration(idList, t);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_37);
		}
		return n;
	}
	
	public final ASTVariableInitialization  variableInitialization() throws RecognitionException, TokenStreamException {
		ASTVariableInitialization n;
		
		Token  name = null;
		ASTType t; ASTExpression e; n = null;
		
		try {      // for error handling
			name = LT(1);
			match(IDENT);
			match(COLON);
			t=type();
			match(EQUAL);
			e=expression();
			n = new ASTVariableInitialization((MyToken) name, t, e);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_38);
		}
		return n;
	}
	
	public final ASTCollectionLiteral  collectionLiteral() throws RecognitionException, TokenStreamException {
		ASTCollectionLiteral n;
		
		ASTCollectionItem ci; n = null;
		
		try {      // for error handling
			MyToken op = (MyToken) LT(1);
			{
			switch ( LA(1)) {
			case LITERAL_Set:
			{
				match(LITERAL_Set);
				break;
			}
			case LITERAL_Sequence:
			{
				match(LITERAL_Sequence);
				break;
			}
			case LITERAL_Bag:
			{
				match(LITERAL_Bag);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			n = new ASTCollectionLiteral(op);
			match(LBRACE);
			ci=collectionItem();
			n.addItem(ci);
			{
			_loop101:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					ci=collectionItem();
					n.addItem(ci);
				}
				else {
					break _loop101;
				}
				
			} while (true);
			}
			match(RBRACE);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_30);
		}
		return n;
	}
	
	public final ASTEmptyCollectionLiteral  emptyCollectionLiteral() throws RecognitionException, TokenStreamException {
		ASTEmptyCollectionLiteral n;
		
		ASTType t = null; n = null;
		
		try {      // for error handling
			match(LITERAL_oclEmpty);
			match(LPAREN);
			t=collectionType();
			match(RPAREN);
			n = new ASTEmptyCollectionLiteral(t);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_30);
		}
		return n;
	}
	
	public final ASTUndefinedLiteral  undefinedLiteral() throws RecognitionException, TokenStreamException {
		ASTUndefinedLiteral n;
		
		ASTType t = null; n = null;
		
		try {      // for error handling
			match(LITERAL_oclUndefined);
			match(LPAREN);
			t=type();
			match(RPAREN);
			n = new ASTUndefinedLiteral(t);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_30);
		}
		return n;
	}
	
	public final ASTTupleLiteral  tupleLiteral() throws RecognitionException, TokenStreamException {
		ASTTupleLiteral n;
		
		ASTTupleItem ti; n = null; List tiList = new ArrayList();
		
		try {      // for error handling
			match(LITERAL_Tuple);
			match(LBRACE);
			ti=tupleItem();
			tiList.add(ti);
			{
			_loop108:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					ti=tupleItem();
					tiList.add(ti);
				}
				else {
					break _loop108;
				}
				
			} while (true);
			}
			match(RBRACE);
			n = new ASTTupleLiteral(tiList);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_30);
		}
		return n;
	}
	
	public final ASTCollectionItem  collectionItem() throws RecognitionException, TokenStreamException {
		ASTCollectionItem n;
		
		ASTExpression e; n = new ASTCollectionItem();
		
		try {      // for error handling
			e=expression();
			n.setFirst(e);
			{
			switch ( LA(1)) {
			case DOTDOT:
			{
				match(DOTDOT);
				e=expression();
				n.setSecond(e);
				break;
			}
			case COMMA:
			case RBRACE:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_39);
		}
		return n;
	}
	
	public final ASTCollectionType  collectionType() throws RecognitionException, TokenStreamException {
		ASTCollectionType n;
		
		ASTType elemType = null; n = null;
		
		try {      // for error handling
			MyToken op = (MyToken) LT(1);
			{
			switch ( LA(1)) {
			case LITERAL_Collection:
			{
				match(LITERAL_Collection);
				break;
			}
			case LITERAL_Set:
			{
				match(LITERAL_Set);
				break;
			}
			case LITERAL_Sequence:
			{
				match(LITERAL_Sequence);
				break;
			}
			case LITERAL_Bag:
			{
				match(LITERAL_Bag);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(LPAREN);
			elemType=type();
			match(RPAREN);
			n = new ASTCollectionType(op, elemType); n.setStartToken(op);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_17);
		}
		return n;
	}
	
	public final ASTTupleItem  tupleItem() throws RecognitionException, TokenStreamException {
		ASTTupleItem n;
		
		Token  name = null;
		ASTExpression e; n = null;
		
		try {      // for error handling
			name = LT(1);
			match(IDENT);
			{
			switch ( LA(1)) {
			case COLON:
			{
				match(COLON);
				break;
			}
			case EQUAL:
			{
				match(EQUAL);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			e=expression();
			n = new ASTTupleItem((MyToken) name, e);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_39);
		}
		return n;
	}
	
	public final ASTTupleType  tupleType() throws RecognitionException, TokenStreamException {
		ASTTupleType n;
		
		ASTTuplePart tp; n = null; List tpList = new ArrayList();
		
		try {      // for error handling
			match(LITERAL_Tuple);
			match(LPAREN);
			tp=tuplePart();
			tpList.add(tp);
			{
			_loop119:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					tp=tuplePart();
					tpList.add(tp);
				}
				else {
					break _loop119;
				}
				
			} while (true);
			}
			match(RPAREN);
			n = new ASTTupleType(tpList);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_17);
		}
		return n;
	}
	
	public final ASTType  typeOnly() throws RecognitionException, TokenStreamException {
		ASTType n;
		
		n = null;
		
		try {      // for error handling
			n=type();
			match(Token.EOF_TYPE);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_1);
		}
		return n;
	}
	
	public final ASTTuplePart  tuplePart() throws RecognitionException, TokenStreamException {
		ASTTuplePart n;
		
		Token  name = null;
		ASTType t; n = null;
		
		try {      // for error handling
			name = LT(1);
			match(IDENT);
			match(COLON);
			t=type();
			n = new ASTTuplePart((MyToken) name, t);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_18);
		}
		return n;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"'('",
		"','",
		"')'",
		"an identifier",
		"':'",
		"\"let\"",
		"'='",
		"\"in\"",
		"\"implies\"",
		"\"or\"",
		"\"xor\"",
		"\"and\"",
		"'<>'",
		"'<'",
		"'>'",
		"'<='",
		"'>='",
		"'+'",
		"'-'",
		"'*'",
		"'/'",
		"\"div\"",
		"\"not\"",
		"'->'",
		"'.'",
		"\"allInstances\"",
		"'@'",
		"\"pre\"",
		"'|'",
		"\"iterate\"",
		"';'",
		"'['",
		"']'",
		"\"oclAsType\"",
		"\"oclIsKindOf\"",
		"\"oclIsTypeOf\"",
		"\"if\"",
		"\"then\"",
		"\"else\"",
		"\"endif\"",
		"\"true\"",
		"\"false\"",
		"INT",
		"REAL",
		"STRING",
		"'#'",
		"\"Set\"",
		"\"Sequence\"",
		"\"Bag\"",
		"'{'",
		"'}'",
		"'..'",
		"\"oclEmpty\"",
		"\"oclUndefined\"",
		"\"Tuple\"",
		"\"Collection\"",
		"\"create\"",
		"\"assign\"",
		"':='",
		"\"between\"",
		"\"destroy\"",
		"\"insert\"",
		"\"into\"",
		"\"delete\"",
		"\"from\"",
		"\"set\"",
		"\"openter\"",
		"\"opexit\"",
		"\"execute\""
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 3458764513820541440L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 3458764513820541442L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 3458764531000410914L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 3458764531000410626L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 513394835581829776L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { 3981166601733928594L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 4557629555744962514L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = { 9223358842715240434L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = { 9223363240761753586L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = { 3972159366582239890L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = { 8592852620161316786L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = { 9169315574172350450L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	private static final long[] mk_tokenSet_13() {
		long[] data = { -13194139535374L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
	private static final long[] mk_tokenSet_14() {
		long[] data = { 4611686039902224704L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
	private static final long[] mk_tokenSet_15() {
		long[] data = { 3458764535295379042L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
	private static final long[] mk_tokenSet_16() {
		long[] data = { 8124509142414002914L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
	private static final long[] mk_tokenSet_17() {
		long[] data = { 21474837602L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
	private static final long[] mk_tokenSet_18() {
		long[] data = { 96L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
	private static final long[] mk_tokenSet_19() {
		long[] data = { 8124509142414007010L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
	private static final long[] mk_tokenSet_20() {
		long[] data = { 8124509142414015202L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
	private static final long[] mk_tokenSet_21() {
		long[] data = { 8124509142414031586L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
	private static final long[] mk_tokenSet_22() {
		long[] data = { 8124509142414064354L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
	private static final long[] mk_tokenSet_23() {
		long[] data = { 8124509142414130914L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
	private static final long[] mk_tokenSet_24() {
		long[] data = { 8124509142416096994L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
	private static final long[] mk_tokenSet_25() {
		long[] data = { 8124509142422388450L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
	private static final long[] mk_tokenSet_26() {
		long[] data = { 8124509142481108706L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_26 = new BitSet(mk_tokenSet_26());
	private static final long[] mk_tokenSet_27() {
		long[] data = { 970662609024L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_27 = new BitSet(mk_tokenSet_27());
	private static final long[] mk_tokenSet_28() {
		long[] data = { 8124509178317242098L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_28 = new BitSet(mk_tokenSet_28());
	private static final long[] mk_tokenSet_29() {
		long[] data = { 9214364732910206706L, 511L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_29 = new BitSet(mk_tokenSet_29());
	private static final long[] mk_tokenSet_30() {
		long[] data = { 8124509142883761890L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_30 = new BitSet(mk_tokenSet_30());
	private static final long[] mk_tokenSet_31() {
		long[] data = { 522402075028486128L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_31 = new BitSet(mk_tokenSet_31());
	private static final long[] mk_tokenSet_32() {
		long[] data = { 9223371968135299058L, 491L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_32 = new BitSet(mk_tokenSet_32());
	private static final long[] mk_tokenSet_33() {
		long[] data = { 8637903980606783218L, 511L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_33 = new BitSet(mk_tokenSet_33());
	private static final long[] mk_tokenSet_34() {
		long[] data = { 8646911281866997746L, 511L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_34 = new BitSet(mk_tokenSet_34());
	private static final long[] mk_tokenSet_35() {
		long[] data = { 9223371968135299058L, 511L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_35 = new BitSet(mk_tokenSet_35());
	private static final long[] mk_tokenSet_36() {
		long[] data = { 522402070733518544L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_36 = new BitSet(mk_tokenSet_36());
	private static final long[] mk_tokenSet_37() {
		long[] data = { 21474836480L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_37 = new BitSet(mk_tokenSet_37());
	private static final long[] mk_tokenSet_38() {
		long[] data = { 4294967296L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_38 = new BitSet(mk_tokenSet_38());
	private static final long[] mk_tokenSet_39() {
		long[] data = { 18014398509482016L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_39 = new BitSet(mk_tokenSet_39());
	
	}
