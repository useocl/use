// $ANTLR 2.7.4: "expandeduse.g" -> "GUSEParser.java"$
 
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

import org.tzi.use.parser.ocl.*;

public class GUSEParser extends antlr.LLkParser       implements GUSETokenTypes
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

protected GUSEParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public GUSEParser(TokenBuffer tokenBuf) {
  this(tokenBuf,5);
}

protected GUSEParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public GUSEParser(TokenStream lexer) {
  this(lexer,5);
}

public GUSEParser(ParserSharedInputState state) {
  super(state,5);
  tokenNames = _tokenNames;
}

	public final ASTModel  model() throws RecognitionException, TokenStreamException {
		ASTModel n;
		
		Token  name = null;
		
		ASTEnumTypeDefinition e = null;
		ASTAssociation a = null;
		ASTConstraintDefinition cons = null;
		ASTPrePost ppc = null;
		n = null;
		
		
		try {      // for error handling
			match(LITERAL_model);
			name = LT(1);
			match(IDENT);
			n = new ASTModel((MyToken) name);
			{
			_loop3:
			do {
				if ((LA(1)==LITERAL_enum)) {
					e=enumTypeDefinition();
					n.addEnumTypeDef(e);
				}
				else {
					break _loop3;
				}
				
			} while (true);
			}
			{
			_loop10:
			do {
				switch ( LA(1)) {
				case LITERAL_abstract:
				case LITERAL_class:
				case LITERAL_associationClass:
				case LITERAL_associationclass:
				{
					{
					generalClassDefinition(n);
					}
					break;
				}
				case LITERAL_aggregation:
				case LITERAL_composition:
				case LITERAL_association:
				{
					{
					a=associationDefinition();
					n.addAssociation(a);
					}
					break;
				}
				case LITERAL_constraints:
				{
					{
					match(LITERAL_constraints);
					{
					_loop9:
					do {
						if ((LA(1)==LITERAL_context) && (LA(2)==IDENT) && (_tokenSet_0.member(LA(3)))) {
							cons=invariant();
							n.addConstraint(cons);
						}
						else if ((LA(1)==LITERAL_context) && (LA(2)==IDENT) && (LA(3)==COLON_COLON)) {
							ppc=prePost();
							n.addPrePost(ppc);
						}
						else {
							break _loop9;
						}
						
					} while (true);
					}
					}
					break;
				}
				default:
				{
					break _loop10;
				}
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
		return n;
	}
	
	public final ASTEnumTypeDefinition  enumTypeDefinition() throws RecognitionException, TokenStreamException {
		ASTEnumTypeDefinition n;
		
		Token  name = null;
		List idList; n = null;
		
		try {      // for error handling
			match(LITERAL_enum);
			name = LT(1);
			match(IDENT);
			match(LBRACE);
			idList=idList();
			match(RBRACE);
			{
			switch ( LA(1)) {
			case SEMI:
			{
				match(SEMI);
				break;
			}
			case EOF:
			case LITERAL_constraints:
			case LITERAL_enum:
			case LITERAL_abstract:
			case LITERAL_class:
			case LITERAL_associationClass:
			case LITERAL_associationclass:
			case LITERAL_aggregation:
			case LITERAL_composition:
			case LITERAL_association:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			n = new ASTEnumTypeDefinition((MyToken) name, idList);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_2);
		}
		return n;
	}
	
	public final void generalClassDefinition(
		ASTModel n
	) throws RecognitionException, TokenStreamException {
		
		boolean isAbstract = false;
		ASTClass c = null;
		ASTAssociationClass ac = null;
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_abstract:
			{
				match(LITERAL_abstract);
				isAbstract = true;
				break;
			}
			case LITERAL_class:
			case LITERAL_associationClass:
			case LITERAL_associationclass:
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
			case LITERAL_class:
			{
				{
				c=classDefinition(isAbstract);
				n.addClass(c);
				}
				break;
			}
			case LITERAL_associationClass:
			case LITERAL_associationclass:
			{
				{
				ac=associationClassDefinition(isAbstract);
				n.addAssociationClass(ac);
				}
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
			consumeUntil(_tokenSet_3);
		}
	}
	
	public final ASTAssociation  associationDefinition() throws RecognitionException, TokenStreamException {
		ASTAssociation n;
		
		Token  name = null;
		ASTAssociationEnd ae; n = null;
		
		try {      // for error handling
			MyToken t = (MyToken) LT(1);
			{
			switch ( LA(1)) {
			case LITERAL_association:
			{
				match(LITERAL_association);
				break;
			}
			case LITERAL_aggregation:
			{
				match(LITERAL_aggregation);
				break;
			}
			case LITERAL_composition:
			{
				match(LITERAL_composition);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			name = LT(1);
			match(IDENT);
			n = new ASTAssociation(t, (MyToken) name);
			match(LITERAL_between);
			ae=associationEnd();
			n.addEnd(ae);
			{
			int _cnt57=0;
			_loop57:
			do {
				if ((LA(1)==IDENT)) {
					ae=associationEnd();
					n.addEnd(ae);
				}
				else {
					if ( _cnt57>=1 ) { break _loop57; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt57++;
			} while (true);
			}
			match(LITERAL_end);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_3);
		}
		return n;
	}
	
	public final ASTConstraintDefinition  invariant() throws RecognitionException, TokenStreamException {
		ASTConstraintDefinition n;
		
		Token  v = null;
		n = null; ASTType t = null; ASTInvariantClause inv = null;
		
		try {      // for error handling
			n = new ASTConstraintDefinition();
			match(LITERAL_context);
			{
			if ((LA(1)==IDENT) && (LA(2)==COLON)) {
				v = LT(1);
				match(IDENT);
				match(COLON);
				n.setVarName((MyToken) v);
			}
			else if ((LA(1)==IDENT) && (_tokenSet_4.member(LA(2)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			t=simpleType();
			n.setType(t);
			{
			_loop71:
			do {
				if ((LA(1)==LITERAL_inv)) {
					inv=invariantClause();
					n.addInvariantClause(inv);
				}
				else {
					break _loop71;
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
	
	public final ASTPrePost  prePost() throws RecognitionException, TokenStreamException {
		ASTPrePost n;
		
		Token  classname = null;
		Token  opname = null;
		n = null; List pl = null; ASTType rt = null; ASTPrePostClause ppc = null;
		
		try {      // for error handling
			match(LITERAL_context);
			classname = LT(1);
			match(IDENT);
			match(COLON_COLON);
			opname = LT(1);
			match(IDENT);
			pl=paramList();
			{
			switch ( LA(1)) {
			case COLON:
			{
				match(COLON);
				rt=type();
				break;
			}
			case LITERAL_pre:
			case LITERAL_post:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			n = new ASTPrePost((MyToken) classname, (MyToken) opname, pl, rt);
			{
			int _cnt77=0;
			_loop77:
			do {
				if ((LA(1)==LITERAL_pre||LA(1)==LITERAL_post)) {
					ppc=prePostClause();
					n.addPrePostClause(ppc);
				}
				else {
					if ( _cnt77>=1 ) { break _loop77; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt77++;
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
			_loop108:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					idn = LT(1);
					match(IDENT);
					idList.add((MyToken) idn);
				}
				else {
					break _loop108;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_6);
		}
		return idList;
	}
	
	public final ASTClass  classDefinition(
		boolean isAbstract
	) throws RecognitionException, TokenStreamException {
		ASTClass n;
		
		Token  name = null;
		List idList; n = null;
		
		try {      // for error handling
			match(LITERAL_class);
			name = LT(1);
			match(IDENT);
			n = new ASTClass((MyToken) name, isAbstract);
			{
			switch ( LA(1)) {
			case LESS:
			{
				match(LESS);
				idList=idList();
				n.addSuperClasses(idList);
				break;
			}
			case LITERAL_constraints:
			case LITERAL_attributes:
			case LITERAL_operations:
			case LITERAL_end:
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
			case LITERAL_attributes:
			{
				match(LITERAL_attributes);
				ASTAttribute a;
				{
				_loop22:
				do {
					if ((LA(1)==IDENT)) {
						a=attributeDefinition();
						n.addAttribute(a);
					}
					else {
						break _loop22;
					}
					
				} while (true);
				}
				break;
			}
			case LITERAL_constraints:
			case LITERAL_operations:
			case LITERAL_end:
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
			case LITERAL_operations:
			{
				match(LITERAL_operations);
				ASTOperation op;
				{
				_loop25:
				do {
					if ((LA(1)==IDENT)) {
						op=operationDefinition();
						n.addOperation(op);
					}
					else {
						break _loop25;
					}
					
				} while (true);
				}
				break;
			}
			case LITERAL_constraints:
			case LITERAL_end:
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
			case LITERAL_constraints:
			{
				match(LITERAL_constraints);
				{
				_loop28:
				do {
					if ((LA(1)==LITERAL_inv)) {
						ASTInvariantClause inv;
						inv=invariantClause();
						n.addInvariantClause(inv);
					}
					else {
						break _loop28;
					}
					
				} while (true);
				}
				break;
			}
			case LITERAL_end:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(LITERAL_end);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_3);
		}
		return n;
	}
	
	public final ASTAssociationClass  associationClassDefinition(
		boolean isAbstract
	) throws RecognitionException, TokenStreamException {
		ASTAssociationClass n;
		
		Token  name = null;
		List idList; n = null; ASTAssociationEnd ae;
		
		try {      // for error handling
			MyToken t1 = (MyToken) LT(1);
			{
			switch ( LA(1)) {
			case LITERAL_associationClass:
			{
				match(LITERAL_associationClass);
				break;
			}
			case LITERAL_associationclass:
			{
				match(LITERAL_associationclass);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			
			if (t1.getText().equals("associationClass")) {
			reportWarning("the 'associationClass' keyword is deprecated and will " +
			"not be supported in the future, use 'associationclass' instead");
			}  
			
			name = LT(1);
			match(IDENT);
			n = new ASTAssociationClass((MyToken) name, isAbstract);
			{
			switch ( LA(1)) {
			case LESS:
			{
				match(LESS);
				idList=idList();
				n.addSuperClasses(idList);
				break;
			}
			case LITERAL_between:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(LITERAL_between);
			ae=associationEnd();
			n.addEnd(ae);
			{
			int _cnt33=0;
			_loop33:
			do {
				if ((LA(1)==IDENT)) {
					ae=associationEnd();
					n.addEnd(ae);
				}
				else {
					if ( _cnt33>=1 ) { break _loop33; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt33++;
			} while (true);
			}
			{
			switch ( LA(1)) {
			case LITERAL_attributes:
			{
				match(LITERAL_attributes);
				ASTAttribute a;
				{
				_loop36:
				do {
					if ((LA(1)==IDENT)) {
						a=attributeDefinition();
						n.addAttribute(a);
					}
					else {
						break _loop36;
					}
					
				} while (true);
				}
				break;
			}
			case LITERAL_constraints:
			case LITERAL_operations:
			case LITERAL_end:
			case LITERAL_aggregation:
			case LITERAL_composition:
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
			case LITERAL_operations:
			{
				match(LITERAL_operations);
				ASTOperation op;
				{
				_loop39:
				do {
					if ((LA(1)==IDENT)) {
						op=operationDefinition();
						n.addOperation(op);
					}
					else {
						break _loop39;
					}
					
				} while (true);
				}
				break;
			}
			case LITERAL_constraints:
			case LITERAL_end:
			case LITERAL_aggregation:
			case LITERAL_composition:
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
			case LITERAL_constraints:
			{
				match(LITERAL_constraints);
				{
				_loop42:
				do {
					if ((LA(1)==LITERAL_inv)) {
						ASTInvariantClause inv;
						inv=invariantClause();
						n.addInvariantClause(inv);
					}
					else {
						break _loop42;
					}
					
				} while (true);
				}
				break;
			}
			case LITERAL_end:
			case LITERAL_aggregation:
			case LITERAL_composition:
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
			case LITERAL_aggregation:
			case LITERAL_composition:
			{
				MyToken t = (MyToken) LT(1);
				{
				switch ( LA(1)) {
				case LITERAL_aggregation:
				{
					match(LITERAL_aggregation);
					break;
				}
				case LITERAL_composition:
				{
					match(LITERAL_composition);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				n.setKind(t);
				break;
			}
			case LITERAL_end:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(LITERAL_end);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_3);
		}
		return n;
	}
	
	public final ASTAttribute  attributeDefinition() throws RecognitionException, TokenStreamException {
		ASTAttribute n;
		
		Token  name = null;
		ASTType t; n = null;
		
		try {      // for error handling
			name = LT(1);
			match(IDENT);
			match(COLON);
			t=type();
			{
			switch ( LA(1)) {
			case SEMI:
			{
				match(SEMI);
				break;
			}
			case IDENT:
			case LITERAL_constraints:
			case LITERAL_operations:
			case LITERAL_end:
			case LITERAL_aggregation:
			case LITERAL_composition:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			n = new ASTAttribute((MyToken) name, t);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_7);
		}
		return n;
	}
	
	public final ASTOperation  operationDefinition() throws RecognitionException, TokenStreamException {
		ASTOperation n;
		
		Token  name = null;
		List pl; ASTType t = null; ASTExpression e = null; 
		ASTPrePostClause ppc = null; n = null; 
		ASTALActionList al = null;
		
		
		try {      // for error handling
			name = LT(1);
			match(IDENT);
			pl=paramList();
			{
			switch ( LA(1)) {
			case COLON:
			{
				match(COLON);
				t=type();
				break;
			}
			case IDENT:
			case EQUAL:
			case LITERAL_pre:
			case SEMI:
			case LITERAL_constraints:
			case LITERAL_end:
			case LITERAL_aggregation:
			case LITERAL_composition:
			case LITERAL_begin:
			case LITERAL_post:
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
			case EQUAL:
			{
				match(EQUAL);
				e=expression();
				break;
			}
			case IDENT:
			case LITERAL_pre:
			case SEMI:
			case LITERAL_constraints:
			case LITERAL_end:
			case LITERAL_aggregation:
			case LITERAL_composition:
			case LITERAL_begin:
			case LITERAL_post:
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
			case LITERAL_begin:
			{
				match(LITERAL_begin);
				al=alActionList();
				match(LITERAL_end);
				break;
			}
			case IDENT:
			case LITERAL_pre:
			case SEMI:
			case LITERAL_constraints:
			case LITERAL_end:
			case LITERAL_aggregation:
			case LITERAL_composition:
			case LITERAL_post:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			n = new ASTOperation((MyToken) name, pl, t, e,al);
			{
			_loop52:
			do {
				if ((LA(1)==LITERAL_pre||LA(1)==LITERAL_post)) {
					ppc=prePostClause();
					n.addPrePostClause(ppc);
				}
				else {
					break _loop52;
				}
				
			} while (true);
			}
			{
			switch ( LA(1)) {
			case SEMI:
			{
				match(SEMI);
				break;
			}
			case IDENT:
			case LITERAL_constraints:
			case LITERAL_end:
			case LITERAL_aggregation:
			case LITERAL_composition:
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
			consumeUntil(_tokenSet_8);
		}
		return n;
	}
	
	public final ASTInvariantClause  invariantClause() throws RecognitionException, TokenStreamException {
		ASTInvariantClause n;
		
		Token  name = null;
		ASTExpression e; n = null;
		
		try {      // for error handling
			match(LITERAL_inv);
			{
			switch ( LA(1)) {
			case IDENT:
			{
				name = LT(1);
				match(IDENT);
				break;
			}
			case COLON:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(COLON);
			e=expression();
			n = new ASTInvariantClause((MyToken) name, e);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_9);
		}
		return n;
	}
	
	public final ASTAssociationEnd  associationEnd() throws RecognitionException, TokenStreamException {
		ASTAssociationEnd n;
		
		Token  name = null;
		Token  rn = null;
		ASTMultiplicity m; n = null;
		
		try {      // for error handling
			name = LT(1);
			match(IDENT);
			match(LBRACK);
			m=multiplicity();
			match(RBRACK);
			n = new ASTAssociationEnd((MyToken) name, m);
			{
			switch ( LA(1)) {
			case LITERAL_role:
			{
				match(LITERAL_role);
				rn = LT(1);
				match(IDENT);
				n.setRolename((MyToken) rn);
				break;
			}
			case IDENT:
			case SEMI:
			case LITERAL_constraints:
			case LITERAL_attributes:
			case LITERAL_operations:
			case LITERAL_end:
			case LITERAL_aggregation:
			case LITERAL_composition:
			case LITERAL_ordered:
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
			case LITERAL_ordered:
			{
				match(LITERAL_ordered);
				n.setOrdered();
				break;
			}
			case IDENT:
			case SEMI:
			case LITERAL_constraints:
			case LITERAL_attributes:
			case LITERAL_operations:
			case LITERAL_end:
			case LITERAL_aggregation:
			case LITERAL_composition:
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
			case SEMI:
			{
				match(SEMI);
				break;
			}
			case IDENT:
			case LITERAL_constraints:
			case LITERAL_attributes:
			case LITERAL_operations:
			case LITERAL_end:
			case LITERAL_aggregation:
			case LITERAL_composition:
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
			consumeUntil(_tokenSet_10);
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
			consumeUntil(_tokenSet_11);
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
				_loop105:
				do {
					if ((LA(1)==COMMA)) {
						match(COMMA);
						v=variableDeclaration();
						paramList.add(v);
					}
					else {
						break _loop105;
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
			consumeUntil(_tokenSet_12);
		}
		return paramList;
	}
	
	public final ASTExpression  expression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		Token  name = null;
		ASTLetExpression prevLet = null, firstLet = null; ASTType t = null; 
		ASTExpression e1, e2; n = null; 
		
		
		try {      // for error handling
			MyToken tok = (MyToken) LT(1); /* remember start of expression */
			{
			_loop114:
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
					break _loop114;
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
			consumeUntil(_tokenSet_13);
		}
		return n;
	}
	
	public final ASTALActionList  alActionList() throws RecognitionException, TokenStreamException {
		ASTALActionList al;
		
		
			al = null;
			ASTALAction action = null;
			al = new ASTALActionList();
		
		
		try {      // for error handling
			{
			_loop83:
			do {
				if ((_tokenSet_14.member(LA(1)))) {
					action=alAction();
					al.add(action);
				}
				else {
					break _loop83;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_15);
		}
		return al;
	}
	
	public final ASTPrePostClause  prePostClause() throws RecognitionException, TokenStreamException {
		ASTPrePostClause n;
		
		Token  name = null;
		ASTExpression e; n = null;
		
		try {      // for error handling
			MyToken t = (MyToken) LT(1);
			{
			switch ( LA(1)) {
			case LITERAL_pre:
			{
				match(LITERAL_pre);
				break;
			}
			case LITERAL_post:
			{
				match(LITERAL_post);
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
			case IDENT:
			{
				name = LT(1);
				match(IDENT);
				break;
			}
			case COLON:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(COLON);
			e=expression();
			n = new ASTPrePostClause(t, (MyToken) name, e);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_16);
		}
		return n;
	}
	
	public final ASTMultiplicity  multiplicity() throws RecognitionException, TokenStreamException {
		ASTMultiplicity n;
		
		ASTMultiplicityRange mr; n = null;
		
		try {      // for error handling
			
				MyToken t = (MyToken) LT(1); // remember start position of expression
				n = new ASTMultiplicity(t); 
			
			mr=multiplicityRange();
			n.addRange(mr);
			{
			_loop64:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					mr=multiplicityRange();
					n.addRange(mr);
				}
				else {
					break _loop64;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_17);
		}
		return n;
	}
	
	public final ASTMultiplicityRange  multiplicityRange() throws RecognitionException, TokenStreamException {
		ASTMultiplicityRange n;
		
		int ms1, ms2; n = null;
		
		try {      // for error handling
			ms1=multiplicitySpec();
			n = new ASTMultiplicityRange(ms1);
			{
			switch ( LA(1)) {
			case DOTDOT:
			{
				match(DOTDOT);
				ms2=multiplicitySpec();
				n.setHigh(ms2);
				break;
			}
			case COMMA:
			case RBRACK:
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
			consumeUntil(_tokenSet_18);
		}
		return n;
	}
	
	public final int  multiplicitySpec() throws RecognitionException, TokenStreamException {
		int m;
		
		Token  i = null;
		m = -1;
		
		try {      // for error handling
			switch ( LA(1)) {
			case INT:
			{
				i = LT(1);
				match(INT);
				m = Integer.parseInt(i.getText());
				break;
			}
			case STAR:
			{
				match(STAR);
				m = -1;
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
			consumeUntil(_tokenSet_19);
		}
		return m;
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
			consumeUntil(_tokenSet_20);
		}
		return n;
	}
	
	public final ASTALAction  alAction() throws RecognitionException, TokenStreamException {
		ASTALAction action;
		
		
			action = null;
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_var:
			case LITERAL_declare:
			{
				action=alCreateVar();
				break;
			}
			case LITERAL_delete:
			{
				action=alDelete();
				break;
			}
			case LITERAL_set:
			{
				action=alSet();
				break;
			}
			case LITERAL_create:
			{
				action=alSetCreate();
				break;
			}
			case LITERAL_insert:
			{
				action=alInsert();
				break;
			}
			case LITERAL_destroy:
			{
				action=alDestroy();
				break;
			}
			case LITERAL_if:
			{
				action=alIf();
				break;
			}
			case LITERAL_while:
			{
				action=alWhile();
				break;
			}
			case LITERAL_for:
			{
				action=alFor();
				break;
			}
			case LITERAL_execute:
			{
				action=alExec();
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
			consumeUntil(_tokenSet_21);
		}
		return action;
	}
	
	public final ASTALCreateVar  alCreateVar() throws RecognitionException, TokenStreamException {
		ASTALCreateVar var;
		
		Token  name = null;
		
			var = null;
			ASTType type = null;
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case LITERAL_var:
			{
				match(LITERAL_var);
				break;
			}
			case LITERAL_declare:
			{
				match(LITERAL_declare);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			name = LT(1);
			match(IDENT);
			match(COLON);
			type=type();
			var = new ASTALCreateVar(name,type);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_21);
		}
		return var;
	}
	
	public final ASTALDelete  alDelete() throws RecognitionException, TokenStreamException {
		ASTALDelete n;
		
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
			_loop95:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					e=expression();
					exprList.add(e);
				}
				else {
					break _loop95;
				}
				
			} while (true);
			}
			match(RPAREN);
			match(LITERAL_from);
			id = LT(1);
			match(IDENT);
			n = new ASTALDelete(exprList, (MyToken) id);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_21);
		}
		return n;
	}
	
	public final ASTALSet  alSet() throws RecognitionException, TokenStreamException {
		ASTALSet set;
		
		
		set = null;
		ASTExpression lval = null;
		ASTExpression rval = null;
		
		
		try {      // for error handling
			match(LITERAL_set);
			lval=expression();
			match(COLON_EQUAL);
			rval=expression();
			set = new ASTALSet(lval, rval);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_21);
		}
		return set;
	}
	
	public final ASTALSetCreate  alSetCreate() throws RecognitionException, TokenStreamException {
		ASTALSetCreate setcreate;
		
		Token  new_ = null;
		Token  cls = null;
		
		setcreate = null;
		ASTExpression lval = null;
		ASTExpression nameExpr = null;
		
		
		try {      // for error handling
			match(LITERAL_create);
			lval=expression();
			match(COLON_EQUAL);
			if (!( LT(1).getText().equals("new") ))
			  throw new SemanticException(" LT(1).getText().equals(\"new\") ");
			new_ = LT(1);
			match(IDENT);
			cls = LT(1);
			match(IDENT);
			{
			switch ( LA(1)) {
			case LITERAL_namehint:
			{
				match(LITERAL_namehint);
				nameExpr=expression();
				break;
			}
			case IDENT:
			case LITERAL_if:
			case LITERAL_else:
			case LITERAL_endif:
			case LITERAL_end:
			case LITERAL_var:
			case LITERAL_declare:
			case LITERAL_set:
			case LITERAL_create:
			case LITERAL_insert:
			case LITERAL_delete:
			case LITERAL_destroy:
			case LITERAL_while:
			case LITERAL_wend:
			case LITERAL_for:
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
			setcreate = new ASTALSetCreate(lval, (MyToken)cls, nameExpr);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_21);
		}
		return setcreate;
	}
	
	public final ASTALInsert  alInsert() throws RecognitionException, TokenStreamException {
		ASTALInsert insert;
		
		Token  id = null;
		ASTExpression e; List exprList = new ArrayList(); insert = null;
		
		try {      // for error handling
			match(LITERAL_insert);
			match(LPAREN);
			e=expression();
			exprList.add(e);
			match(COMMA);
			e=expression();
			exprList.add(e);
			{
			_loop92:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					e=expression();
					exprList.add(e);
				}
				else {
					break _loop92;
				}
				
			} while (true);
			}
			match(RPAREN);
			match(LITERAL_into);
			id = LT(1);
			match(IDENT);
			insert = new ASTALInsert(exprList, (MyToken) id);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_21);
		}
		return insert;
	}
	
	public final ASTALDestroy  alDestroy() throws RecognitionException, TokenStreamException {
		ASTALDestroy n;
		
		ASTExpression e = null;  n = null;
		
		try {      // for error handling
			match(LITERAL_destroy);
			e=expression();
			n = new ASTALDestroy(e);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_21);
		}
		return n;
	}
	
	public final ASTALIf  alIf() throws RecognitionException, TokenStreamException {
		ASTALIf i;
		
		
			i = null;
			ASTExpression ifexpr;
			ASTALActionList thenlist;
			ASTALActionList elselist=null;
		
		
		try {      // for error handling
			match(LITERAL_if);
			ifexpr=expression();
			match(LITERAL_then);
			thenlist=alActionList();
			{
			switch ( LA(1)) {
			case LITERAL_else:
			{
				match(LITERAL_else);
				elselist=alActionList();
				break;
			}
			case LITERAL_endif:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(LITERAL_endif);
			i = new ASTALIf(ifexpr,thenlist,elselist);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_21);
		}
		return i;
	}
	
	public final ASTALWhile  alWhile() throws RecognitionException, TokenStreamException {
		ASTALWhile w;
		
		
			w = null;
			ASTExpression expr;
			ASTALActionList body;
		
		
		try {      // for error handling
			match(LITERAL_while);
			expr=expression();
			match(LITERAL_do);
			body=alActionList();
			match(LITERAL_wend);
			w = new ASTALWhile(expr,body);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_21);
		}
		return w;
	}
	
	public final ASTALFor  alFor() throws RecognitionException, TokenStreamException {
		ASTALFor f;
		
		Token  var = null;
		Token  next = null;
		
			f = null;
			ASTExpression expr;
			ASTALActionList body;
			ASTType type;
		
		
		try {      // for error handling
			match(LITERAL_for);
			var = LT(1);
			match(IDENT);
			match(COLON);
			type=type();
			match(LITERAL_in);
			expr=expression();
			match(LITERAL_do);
			body=alActionList();
			if (!( LT(1).getText().equals("next") ))
			  throw new SemanticException(" LT(1).getText().equals(\"next\") ");
			next = LT(1);
			match(IDENT);
			f = new ASTALFor(var,type,expr,body);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_21);
		}
		return f;
	}
	
	public final ASTALExecute  alExec() throws RecognitionException, TokenStreamException {
		ASTALExecute c;
		
		
		ASTExpression op;
		c=null;
		
		
		try {      // for error handling
			match(LITERAL_execute);
			op=expression();
			c = new ASTALExecute(op);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_21);
		}
		return c;
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
			consumeUntil(_tokenSet_22);
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
			_loop117:
			do {
				if ((LA(1)==LITERAL_implies)) {
					op = LT(1);
					match(LITERAL_implies);
					n1=conditionalOrExpression();
					n = new ASTBinaryExpression((MyToken) op, n, n1);
				}
				else {
					break _loop117;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_13);
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
			_loop120:
			do {
				if ((LA(1)==LITERAL_or)) {
					op = LT(1);
					match(LITERAL_or);
					n1=conditionalXOrExpression();
					n = new ASTBinaryExpression((MyToken) op, n, n1);
				}
				else {
					break _loop120;
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
	
	public final ASTExpression  conditionalXOrExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		Token  op = null;
		ASTExpression n1; n = null;
		
		try {      // for error handling
			n=conditionalAndExpression();
			{
			_loop123:
			do {
				if ((LA(1)==LITERAL_xor)) {
					op = LT(1);
					match(LITERAL_xor);
					n1=conditionalAndExpression();
					n = new ASTBinaryExpression((MyToken) op, n, n1);
				}
				else {
					break _loop123;
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
	
	public final ASTExpression  conditionalAndExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		Token  op = null;
		ASTExpression n1; n = null;
		
		try {      // for error handling
			n=equalityExpression();
			{
			_loop126:
			do {
				if ((LA(1)==LITERAL_and)) {
					op = LT(1);
					match(LITERAL_and);
					n1=equalityExpression();
					n = new ASTBinaryExpression((MyToken) op, n, n1);
				}
				else {
					break _loop126;
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
	
	public final ASTExpression  equalityExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		ASTExpression n1; n = null;
		
		try {      // for error handling
			n=relationalExpression();
			{
			_loop130:
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
					break _loop130;
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
	
	public final ASTExpression  relationalExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		ASTExpression n1; n = null;
		
		try {      // for error handling
			n=additiveExpression();
			{
			_loop134:
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
					break _loop134;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_27);
		}
		return n;
	}
	
	public final ASTExpression  additiveExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		ASTExpression n1; n = null;
		
		try {      // for error handling
			n=multiplicativeExpression();
			{
			_loop138:
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
					break _loop138;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_28);
		}
		return n;
	}
	
	public final ASTExpression  multiplicativeExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		ASTExpression n1; n = null;
		
		try {      // for error handling
			n=unaryExpression();
			{
			_loop142:
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
					break _loop142;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_29);
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
			consumeUntil(_tokenSet_30);
		}
		return n;
	}
	
	public final ASTExpression  postfixExpression() throws RecognitionException, TokenStreamException {
		ASTExpression n;
		
		boolean arrow; n = null;
		
		try {      // for error handling
			n=primaryExpression();
			{
			_loop149:
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
					break _loop149;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_30);
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
				if ((_tokenSet_31.member(LA(1))) && (_tokenSet_32.member(LA(2))) && (_tokenSet_33.member(LA(3)))) {
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
					case LITERAL_pre:
					case BAR:
					case SEMI:
					case LITERAL_if:
					case LITERAL_then:
					case LITERAL_else:
					case LITERAL_endif:
					case RBRACE:
					case DOTDOT:
					case LITERAL_constraints:
					case LITERAL_abstract:
					case LITERAL_class:
					case LITERAL_end:
					case LITERAL_associationClass:
					case LITERAL_associationclass:
					case LITERAL_aggregation:
					case LITERAL_composition:
					case LITERAL_begin:
					case LITERAL_association:
					case LITERAL_context:
					case LITERAL_inv:
					case LITERAL_post:
					case LITERAL_var:
					case LITERAL_declare:
					case LITERAL_set:
					case COLON_EQUAL:
					case LITERAL_create:
					case LITERAL_insert:
					case LITERAL_delete:
					case LITERAL_destroy:
					case LITERAL_while:
					case LITERAL_do:
					case LITERAL_wend:
					case LITERAL_for:
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
					case LITERAL_pre:
					case BAR:
					case SEMI:
					case LITERAL_if:
					case LITERAL_then:
					case LITERAL_else:
					case LITERAL_endif:
					case RBRACE:
					case DOTDOT:
					case LITERAL_constraints:
					case LITERAL_abstract:
					case LITERAL_class:
					case LITERAL_end:
					case LITERAL_associationClass:
					case LITERAL_associationclass:
					case LITERAL_aggregation:
					case LITERAL_composition:
					case LITERAL_begin:
					case LITERAL_association:
					case LITERAL_context:
					case LITERAL_inv:
					case LITERAL_post:
					case LITERAL_var:
					case LITERAL_declare:
					case LITERAL_set:
					case COLON_EQUAL:
					case LITERAL_create:
					case LITERAL_insert:
					case LITERAL_delete:
					case LITERAL_destroy:
					case LITERAL_while:
					case LITERAL_do:
					case LITERAL_wend:
					case LITERAL_for:
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
			consumeUntil(_tokenSet_34);
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
				if (((LA(1)==IDENT) && (LA(2)==LPAREN) && (_tokenSet_35.member(LA(3))) && (_tokenSet_36.member(LA(4))) && (_tokenSet_37.member(LA(5))))&&( isQueryIdent(LT(1)) )) {
					n=queryExpression(source);
				}
				else if ((LA(1)==IDENT) && (_tokenSet_32.member(LA(2))) && (_tokenSet_38.member(LA(3))) && (_tokenSet_39.member(LA(4))) && (_tokenSet_40.member(LA(5)))) {
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
			consumeUntil(_tokenSet_34);
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
			consumeUntil(_tokenSet_34);
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
			consumeUntil(_tokenSet_34);
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
			else if ((_tokenSet_35.member(LA(1))) && (_tokenSet_41.member(LA(2)))) {
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
			consumeUntil(_tokenSet_34);
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
			consumeUntil(_tokenSet_34);
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
			case LITERAL_pre:
			case BAR:
			case SEMI:
			case LITERAL_if:
			case LITERAL_then:
			case LITERAL_else:
			case LITERAL_endif:
			case RBRACE:
			case DOTDOT:
			case LITERAL_constraints:
			case LITERAL_abstract:
			case LITERAL_class:
			case LITERAL_end:
			case LITERAL_associationClass:
			case LITERAL_associationclass:
			case LITERAL_aggregation:
			case LITERAL_composition:
			case LITERAL_begin:
			case LITERAL_association:
			case LITERAL_context:
			case LITERAL_inv:
			case LITERAL_post:
			case LITERAL_var:
			case LITERAL_declare:
			case LITERAL_set:
			case COLON_EQUAL:
			case LITERAL_create:
			case LITERAL_insert:
			case LITERAL_delete:
			case LITERAL_destroy:
			case LITERAL_while:
			case LITERAL_do:
			case LITERAL_wend:
			case LITERAL_for:
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
			case LITERAL_pre:
			case BAR:
			case SEMI:
			case LITERAL_if:
			case LITERAL_then:
			case LITERAL_else:
			case LITERAL_endif:
			case RBRACE:
			case DOTDOT:
			case LITERAL_constraints:
			case LITERAL_abstract:
			case LITERAL_class:
			case LITERAL_end:
			case LITERAL_associationClass:
			case LITERAL_associationclass:
			case LITERAL_aggregation:
			case LITERAL_composition:
			case LITERAL_begin:
			case LITERAL_association:
			case LITERAL_context:
			case LITERAL_inv:
			case LITERAL_post:
			case LITERAL_var:
			case LITERAL_declare:
			case LITERAL_set:
			case COLON_EQUAL:
			case LITERAL_create:
			case LITERAL_insert:
			case LITERAL_delete:
			case LITERAL_destroy:
			case LITERAL_while:
			case LITERAL_do:
			case LITERAL_wend:
			case LITERAL_for:
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
					_loop163:
					do {
						if ((LA(1)==COMMA)) {
							match(COMMA);
							e=expression();
							n.addArg(e);
						}
						else {
							break _loop163;
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
			case LITERAL_pre:
			case BAR:
			case SEMI:
			case LITERAL_if:
			case LITERAL_then:
			case LITERAL_else:
			case LITERAL_endif:
			case RBRACE:
			case DOTDOT:
			case LITERAL_constraints:
			case LITERAL_abstract:
			case LITERAL_class:
			case LITERAL_end:
			case LITERAL_associationClass:
			case LITERAL_associationclass:
			case LITERAL_aggregation:
			case LITERAL_composition:
			case LITERAL_begin:
			case LITERAL_association:
			case LITERAL_context:
			case LITERAL_inv:
			case LITERAL_post:
			case LITERAL_var:
			case LITERAL_declare:
			case LITERAL_set:
			case COLON_EQUAL:
			case LITERAL_create:
			case LITERAL_insert:
			case LITERAL_delete:
			case LITERAL_destroy:
			case LITERAL_while:
			case LITERAL_do:
			case LITERAL_wend:
			case LITERAL_for:
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
			consumeUntil(_tokenSet_34);
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
			consumeUntil(_tokenSet_34);
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
			consumeUntil(_tokenSet_42);
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
			consumeUntil(_tokenSet_43);
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
			_loop174:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					ci=collectionItem();
					n.addItem(ci);
				}
				else {
					break _loop174;
				}
				
			} while (true);
			}
			match(RBRACE);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_34);
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
			consumeUntil(_tokenSet_34);
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
			consumeUntil(_tokenSet_34);
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
			_loop181:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					ti=tupleItem();
					tiList.add(ti);
				}
				else {
					break _loop181;
				}
				
			} while (true);
			}
			match(RBRACE);
			n = new ASTTupleLiteral(tiList);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_34);
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
			consumeUntil(_tokenSet_44);
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
			consumeUntil(_tokenSet_11);
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
			consumeUntil(_tokenSet_44);
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
			_loop192:
			do {
				if ((LA(1)==COMMA)) {
					match(COMMA);
					tp=tuplePart();
					tpList.add(tp);
				}
				else {
					break _loop192;
				}
				
			} while (true);
			}
			match(RPAREN);
			n = new ASTTupleType(tpList);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			consume();
			consumeUntil(_tokenSet_11);
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
			consumeUntil(_tokenSet_22);
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
		"\"model\"",
		"\"constraints\"",
		"\"enum\"",
		"\"abstract\"",
		"\"class\"",
		"\"attributes\"",
		"\"operations\"",
		"\"end\"",
		"\"associationClass\"",
		"\"associationclass\"",
		"\"between\"",
		"\"aggregation\"",
		"\"composition\"",
		"\"begin\"",
		"\"association\"",
		"\"role\"",
		"\"ordered\"",
		"\"context\"",
		"\"inv\"",
		"'::'",
		"\"post\"",
		"\"var\"",
		"\"declare\"",
		"\"set\"",
		"':='",
		"\"create\"",
		"\"namehint\"",
		"\"insert\"",
		"\"into\"",
		"\"delete\"",
		"\"from\"",
		"\"destroy\"",
		"\"while\"",
		"\"do\"",
		"\"wend\"",
		"\"for\"",
		"\"execute\""
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { -6917529027641081598L, 26033L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { -2305843009213693950L, 1457L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { -6917529027641081854L, 1457L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { -6917529027641081854L, 26033L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { -6917529027641081854L, 9649L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { 2323857429198012672L, 78L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 2305843009213694080L, 396L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = { 2305843009213694080L, 392L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = { -6917529027641081854L, 26041L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = { 2305843009213694080L, 398L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	private static final long[] mk_tokenSet_11() {
		long[] data = { 2305857326487178466L, 7963870092L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
	private static final long[] mk_tokenSet_12() {
		long[] data = { 2305843028541048192L, 66440L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
	private static final long[] mk_tokenSet_13() {
		long[] data = { -6863469315815896862L, 8501815225L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
	private static final long[] mk_tokenSet_14() {
		long[] data = { 1099511627776L, 6890061824L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
	private static final long[] mk_tokenSet_15() {
		long[] data = { 13194139533440L, 1073741832L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
	private static final long[] mk_tokenSet_16() {
		long[] data = { -6917529008313728894L, 75193L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
	private static final long[] mk_tokenSet_17() {
		long[] data = { 68719476736L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
	private static final long[] mk_tokenSet_18() {
		long[] data = { 68719476768L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
	private static final long[] mk_tokenSet_19() {
		long[] data = { 36028865738440736L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
	private static final long[] mk_tokenSet_20() {
		long[] data = { -6917514710367597342L, 7963895741L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
	private static final long[] mk_tokenSet_21() {
		long[] data = { 14293651161216L, 7963803656L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
	private static final long[] mk_tokenSet_22() {
		long[] data = { 96L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
	private static final long[] mk_tokenSet_23() {
		long[] data = { -6863469315815892766L, 8501815225L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
	private static final long[] mk_tokenSet_24() {
		long[] data = { -6863469315815884574L, 8501815225L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
	private static final long[] mk_tokenSet_25() {
		long[] data = { -6863469315815868190L, 8501815225L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
	private static final long[] mk_tokenSet_26() {
		long[] data = { -6863469315815835422L, 8501815225L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_26 = new BitSet(mk_tokenSet_26());
	private static final long[] mk_tokenSet_27() {
		long[] data = { -6863469315815768862L, 8501815225L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_27 = new BitSet(mk_tokenSet_27());
	private static final long[] mk_tokenSet_28() {
		long[] data = { -6863469315813802782L, 8501815225L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_28 = new BitSet(mk_tokenSet_28());
	private static final long[] mk_tokenSet_29() {
		long[] data = { -6863469315807511326L, 8501815225L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_29 = new BitSet(mk_tokenSet_29());
	private static final long[] mk_tokenSet_30() {
		long[] data = { -6863469315748791070L, 8501815225L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_30 = new BitSet(mk_tokenSet_30());
	private static final long[] mk_tokenSet_31() {
		long[] data = { 970662609024L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_31 = new BitSet(mk_tokenSet_31());
	private static final long[] mk_tokenSet_32() {
		long[] data = { -6863469279912657678L, 8501815225L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_32 = new BitSet(mk_tokenSet_32());
	private static final long[] mk_tokenSet_33() {
		long[] data = { -5773614826978803726L, 8585701305L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_33 = new BitSet(mk_tokenSet_33());
	private static final long[] mk_tokenSet_34() {
		long[] data = { -6863469315346137886L, 8501815225L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_34 = new BitSet(mk_tokenSet_34());
	private static final long[] mk_tokenSet_35() {
		long[] data = { 513394835581829776L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_35 = new BitSet(mk_tokenSet_35());
	private static final long[] mk_tokenSet_36() {
		long[] data = { 522402075028486128L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_36 = new BitSet(mk_tokenSet_36());
	private static final long[] mk_tokenSet_37() {
		long[] data = { -5764607591753711630L, 8501815225L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_37 = new BitSet(mk_tokenSet_37());
	private static final long[] mk_tokenSet_38() {
		long[] data = { -6350075579282227214L, 8585701305L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_38 = new BitSet(mk_tokenSet_38());
	private static final long[] mk_tokenSet_39() {
		long[] data = { -6341068275874529294L, 8585734143L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_39 = new BitSet(mk_tokenSet_39());
	private static final long[] mk_tokenSet_40() {
		long[] data = { -5764607591753711630L, 8589928447L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_40 = new BitSet(mk_tokenSet_40());
	private static final long[] mk_tokenSet_41() {
		long[] data = { 522402070733518544L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_41 = new BitSet(mk_tokenSet_41());
	private static final long[] mk_tokenSet_42() {
		long[] data = { 21474836480L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_42 = new BitSet(mk_tokenSet_42());
	private static final long[] mk_tokenSet_43() {
		long[] data = { 4294967296L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_43 = new BitSet(mk_tokenSet_43());
	private static final long[] mk_tokenSet_44() {
		long[] data = { 18014398509482016L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_44 = new BitSet(mk_tokenSet_44());
	
	}
