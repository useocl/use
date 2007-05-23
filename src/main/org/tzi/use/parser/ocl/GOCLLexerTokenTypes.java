// $ANTLR 2.7.4: "ocl.g" -> "GOCLLexer.java"$
 
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

public interface GOCLLexerTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int LPAREN = 4;
	int COMMA = 5;
	int RPAREN = 6;
	int IDENT = 7;
	int COLON = 8;
	int LITERAL_let = 9;
	int EQUAL = 10;
	int LITERAL_in = 11;
	int LITERAL_implies = 12;
	int LITERAL_or = 13;
	int LITERAL_xor = 14;
	int LITERAL_and = 15;
	int NOT_EQUAL = 16;
	int LESS = 17;
	int GREATER = 18;
	int LESS_EQUAL = 19;
	int GREATER_EQUAL = 20;
	int PLUS = 21;
	int MINUS = 22;
	int STAR = 23;
	int SLASH = 24;
	int LITERAL_div = 25;
	int LITERAL_not = 26;
	int ARROW = 27;
	int DOT = 28;
	int LITERAL_allInstances = 29;
	int AT = 30;
	int LITERAL_pre = 31;
	int BAR = 32;
	int LITERAL_iterate = 33;
	int SEMI = 34;
	int LBRACK = 35;
	int RBRACK = 36;
	int LITERAL_oclAsType = 37;
	int LITERAL_oclIsKindOf = 38;
	int LITERAL_oclIsTypeOf = 39;
	int LITERAL_if = 40;
	int LITERAL_then = 41;
	int LITERAL_else = 42;
	int LITERAL_endif = 43;
	int LITERAL_true = 44;
	int LITERAL_false = 45;
	int INT = 46;
	int REAL = 47;
	int STRING = 48;
	int HASH = 49;
	int LITERAL_Set = 50;
	int LITERAL_Sequence = 51;
	int LITERAL_Bag = 52;
	int LBRACE = 53;
	int RBRACE = 54;
	int DOTDOT = 55;
	int LITERAL_oclEmpty = 56;
	int LITERAL_oclUndefined = 57;
	int LITERAL_Tuple = 58;
	int LITERAL_Collection = 59;
	int WS = 60;
	int SL_COMMENT = 61;
	int ML_COMMENT = 62;
	int COLON_COLON = 63;
	int COLON_EQUAL = 64;
	int RANGE_OR_INT = 65;
	int ESC = 66;
	int HEX_DIGIT = 67;
	int VOCAB = 68;
}
