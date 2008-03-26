// $ANTLR 2.7.4: "ocl.g" -> "GOCLParser.java"$
 
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

package org.tzi.use.parser.ocl; 

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
import java.io.PrintWriter;
import org.tzi.use.parser.MyToken;
import org.tzi.use.parser.ParseErrorHandler;

public class GOCLParser extends antlr.LLkParser       implements GOCLTokenTypes
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

protected GOCLParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public GOCLParser(TokenBuffer tokenBuf) {
  this(tokenBuf,5);
}

protected GOCLParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public GOCLParser(TokenStream lexer) {
  this(lexer,5);
}

public GOCLParser(ParserSharedInputState state) {
  super(state,5);
  tokenNames = _tokenNames;
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
				_loop4:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						v=variableDeclaration();
						paramList.add(v);
					}
					else {
						break _loop4;
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
			consumeUntil(_tokenSet_0);
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
			consumeUntil(_tokenSet_1);
		}
		return n;
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
			_loop7:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					idn = LT(1);
					match(IDENT);
					idList.add((MyToken) idn);
				}
				else {
					break _loop7;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_2);
		}
		return idList;
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
			consumeUntil(_tokenSet_3);
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
			consumeUntil(_tokenSet_0);
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
			_loop13:
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
					break _loop13;
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
			consumeUntil(_tokenSet_4);
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
			_loop16:
			do {
				if ((LA(1)==LITERAL_implies)) {
					op = LT(1);
					match(LITERAL_implies);
					n1=conditionalOrExpression();
					n = new ASTBinaryExpression((MyToken) op, n, n1);
				}
				else {
					break _loop16;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_4);
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
			_loop19:
			do {
				if ((LA(1)==LITERAL_or)) {
					op = LT(1);
					match(LITERAL_or);
					n1=conditionalXOrExpression();
					n = new ASTBinaryExpression((MyToken) op, n, n1);
				}
				else {
					break _loop19;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_5);
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
			_loop22:
			do {
				if ((LA(1)==LITERAL_xor)) {
					op = LT(1);
					match(LITERAL_xor);
					n1=conditionalAndExpression();
					n = new ASTBinaryExpression((MyToken) op, n, n1);
				}
				else {
					break _loop22;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_6);
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
			_loop25:
			do {
				if ((LA(1)==LITERAL_and)) {
					op = LT(1);
					match(LITERAL_and);
					n1=equalityExpression();
					n = new ASTBinaryExpression((MyToken) op, n, n1);
				}
				else {
					break _loop25;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_7);
		}
		return n;
	}
	
	public final ASTExpression  equalityExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		ASTExpression n1; n = null;
		
		try {      // for error handling
			n=relationalExpression();
			{
			_loop29:
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
					break _loop29;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_8);
		}
		return n;
	}
	
	public final ASTExpression  relationalExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		ASTExpression n1; n = null;
		
		try {      // for error handling
			n=additiveExpression();
			{
			_loop33:
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
					break _loop33;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_9);
		}
		return n;
	}
	
	public final ASTExpression  additiveExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		ASTExpression n1; n = null;
		
		try {      // for error handling
			n=multiplicativeExpression();
			{
			_loop37:
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
					break _loop37;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_10);
		}
		return n;
	}
	
	public final ASTExpression  multiplicativeExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		ASTExpression n1; n = null;
		
		try {      // for error handling
			n=unaryExpression();
			{
			_loop41:
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
					break _loop41;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_11);
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
			consumeUntil(_tokenSet_12);
		}
		return n;
	}
	
	public final ASTExpression  postfixExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		boolean arrow; n = null;
		
		try {      // for error handling
			n=primaryExpression();
			{
			_loop48:
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
					break _loop48;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_12);
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
				if ((_tokenSet_13.member(LA(1))) && (_tokenSet_14.member(LA(2))) && (_tokenSet_15.member(LA(3)))) {
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
					case LITERAL_then:
					case LITERAL_else:
					case LITERAL_endif:
					case RBRACE:
					case DOTDOT:
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
					case LITERAL_then:
					case LITERAL_else:
					case LITERAL_endif:
					case RBRACE:
					case DOTDOT:
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
			consumeUntil(_tokenSet_16);
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
				if (((LA(1)==IDENT) && (LA(2)==LPAREN) && (_tokenSet_17.member(LA(3))) && (_tokenSet_18.member(LA(4))) && (_tokenSet_19.member(LA(5))))&&( isQueryIdent(LT(1)) )) {
					n=queryExpression(source);
				}
				else if ((LA(1)==IDENT) && (_tokenSet_14.member(LA(2))) && (_tokenSet_20.member(LA(3))) && (_tokenSet_21.member(LA(4))) && (_tokenSet_19.member(LA(5)))) {
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
			consumeUntil(_tokenSet_16);
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
			consumeUntil(_tokenSet_16);
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
			consumeUntil(_tokenSet_16);
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
			else if ((_tokenSet_17.member(LA(1))) && (_tokenSet_22.member(LA(2)))) {
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
			consumeUntil(_tokenSet_16);
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
			consumeUntil(_tokenSet_16);
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
			case LITERAL_then:
			case LITERAL_else:
			case LITERAL_endif:
			case RBRACE:
			case DOTDOT:
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
			case LITERAL_then:
			case LITERAL_else:
			case LITERAL_endif:
			case RBRACE:
			case DOTDOT:
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
					_loop62:
					do {
						if ((LA(1)==COMMA)) {
							match(COMMA);
							e=expression();
							n.addArg(e);
						}
						else {
							break _loop62;
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
			case LITERAL_then:
			case LITERAL_else:
			case LITERAL_endif:
			case RBRACE:
			case DOTDOT:
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
			consumeUntil(_tokenSet_16);
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
			consumeUntil(_tokenSet_16);
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
			consumeUntil(_tokenSet_23);
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
			consumeUntil(_tokenSet_24);
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
			_loop73:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					ci=collectionItem();
					n.addItem(ci);
				}
				else {
					break _loop73;
				}
				
			} while (true);
			}
			match(RBRACE);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_16);
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
			consumeUntil(_tokenSet_16);
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
			consumeUntil(_tokenSet_16);
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
			_loop80:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					ti=tupleItem();
					tiList.add(ti);
				}
				else {
					break _loop80;
				}
				
			} while (true);
			}
			match(RBRACE);
			n = new ASTTupleLiteral(tiList);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_16);
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
			consumeUntil(_tokenSet_25);
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
			n = new ASTCollectionType(op, elemType);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_3);
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
			consumeUntil(_tokenSet_25);
		}
		return n;
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
			consumeUntil(_tokenSet_3);
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
			_loop91:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					tp=tuplePart();
					tpList.add(tp);
				}
				else {
					break _loop91;
				}
				
			} while (true);
			}
			match(RPAREN);
			n = new ASTTupleType(tpList);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_3);
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
			consumeUntil(_tokenSet_0);
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
			consumeUntil(_tokenSet_1);
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
		"\"Collection\""
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 96L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 21474836736L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 21474837602L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 54058592986204258L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 54058592986208354L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { 54058592986216546L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 54058592986232930L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = { 54058592986265698L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = { 54058592986332258L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = { 54058592988298338L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = { 54058592994589794L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = { 54058593053310050L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	private static final long[] mk_tokenSet_13() {
		long[] data = { 970662609024L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
	private static final long[] mk_tokenSet_14() {
		long[] data = { 54058628889443442L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
	private static final long[] mk_tokenSet_15() {
		long[] data = { 1143914183482408690L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
	private static final long[] mk_tokenSet_16() {
		long[] data = { 54058593455963234L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
	private static final long[] mk_tokenSet_17() {
		long[] data = { 513394835581829776L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
	private static final long[] mk_tokenSet_18() {
		long[] data = { 522402075028486128L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
	private static final long[] mk_tokenSet_19() {
		long[] data = { 1152921418707501042L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
	private static final long[] mk_tokenSet_20() {
		long[] data = { 567453431178985202L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
	private static final long[] mk_tokenSet_21() {
		long[] data = { 576460732439199730L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
	private static final long[] mk_tokenSet_22() {
		long[] data = { 522402070733518544L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
	private static final long[] mk_tokenSet_23() {
		long[] data = { 21474836480L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
	private static final long[] mk_tokenSet_24() {
		long[] data = { 4294967296L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
	private static final long[] mk_tokenSet_25() {
		long[] data = { 18014398509482016L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
	
	}
