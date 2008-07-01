lexer grammar GOCLLexerRules;

@lexer::header
{
package @package; 

// ------------------------------------
// OCL lexer
// ------------------------------------

import java.io.PrintWriter;
import org.tzi.use.parser.ParseErrorHandler;
}

/* ToDo: Check usage in v3
options {
    importVocab=GOCL;

	// MyLexer.reportError depends on defaultErrorHandler == true for
	// providing correct column number information
    defaultErrorHandler = true;	

    // don't automatically test all tokens for literals
    testLiterals = false;    

    k = 2;
}
*/

@lexer::members {
    private PrintWriter fErr;
    private ParseErrorHandler fParseErrorHandler;

    Stack paraphrase = new Stack();
    
    public String getFilename() {
        return fParseErrorHandler.getFileName();
    }
    
    public void emitErrorMessage(String msg) {
       	fParseErrorHandler.reportError(msg);
	}
 
    public void init(ParseErrorHandler handler) {
        fParseErrorHandler = handler;
    }
}

// Whitespace -- ignored
WS:
    ( ' '
    | '\t'
    | '\f'
    | NEWLINE
    )
    { $channel=HIDDEN; }
    ;

// Single-line comments
SL_COMMENT:
    ('//' | '--')
    (~('\n'|'\r'))* NEWLINE
    { $channel=HIDDEN; }
    ;

// multiple-line comments
ML_COMMENT:
    '/*' ( options {greedy=false;} : . )* '*/' { $channel=HIDDEN; };

fragment
NEWLINE	:	
    '\r\n' | '\r' | '\n';
    
// Use paraphrases for nice error messages
//EOF 		options { paraphrase = "\"end of file\""; } : EOF;
ARROW 		@init { paraphrase.push("->"); } @after { paraphrase.pop(); }      : '->';
AT     		@init { paraphrase.push("@");  } @after { paraphrase.pop(); }      : '@';
BAR 		@init { paraphrase.push("|");  } @after { paraphrase.pop(); }      : '|';
COLON 		@init { paraphrase.push(":");  } @after { paraphrase.pop(); }      : ':';
COLON_COLON	@init { paraphrase.push("::"); } @after { paraphrase.pop(); }      : '::';
COLON_EQUAL	@init { paraphrase.push(":="); } @after { paraphrase.pop(); }      : ':=';
COMMA 		@init { paraphrase.push(",");  } @after { paraphrase.pop(); }      : ',';
DOT 		@init { paraphrase.push(".");  } @after { paraphrase.pop(); }      : '.';
DOTDOT 		@init { paraphrase.push(".."); } @after { paraphrase.pop(); }      : '..';
EQUAL 		@init { paraphrase.push("=");  } @after { paraphrase.pop(); }      : '=';
GREATER 	@init { paraphrase.push(">");  } @after { paraphrase.pop(); }      : '>';
GREATER_EQUAL 	@init { paraphrase.push(">="); } @after { paraphrase.pop(); }      : '>=';
HASH 		@init { paraphrase.push("#");  } @after { paraphrase.pop(); }      : '#';
LBRACE 		@init { paraphrase.push("{");  } @after { paraphrase.pop(); }      : '{';
LBRACK 		@init { paraphrase.push("[");  } @after { paraphrase.pop(); }      : '[';
LESS 		@init { paraphrase.push("<");  } @after { paraphrase.pop(); }      : '<';
LESS_EQUAL 	@init { paraphrase.push("<="); } @after { paraphrase.pop(); }      : '<=';
LPAREN 		@init { paraphrase.push("(");  } @after { paraphrase.pop(); }      : '(';
MINUS 		@init { paraphrase.push("-");  } @after { paraphrase.pop(); }      : '-';
NOT_EQUAL 	@init { paraphrase.push("<>"); } @after { paraphrase.pop(); }      : '<>';
PLUS 		@init { paraphrase.push("+");  } @after { paraphrase.pop(); }      : '+';
RBRACE 		@init { paraphrase.push("}");  } @after { paraphrase.pop(); }      : '}';
RBRACK 		@init { paraphrase.push("]");  } @after { paraphrase.pop(); }      : ']';
RPAREN		@init { paraphrase.push(")");  } @after { paraphrase.pop(); }      : ')';
SEMI		@init { paraphrase.push(";");  } @after { paraphrase.pop(); }      : ';';
SLASH 		@init { paraphrase.push("/");  } @after { paraphrase.pop(); }      : '/';
STAR 		@init { paraphrase.push("*");  } @after { paraphrase.pop(); }      : '*';

fragment
INT:
    ('0'..'9')+
    ;

fragment
REAL:
    INT ( '.' INT | ( ('e'|'E') ('+'|'-')? INT )?)
    ;

RANGE_OR_INT:
      ( INT '..' )    => INT    { $type=INT; }
    | ( INT '.' INT)  => REAL   { $type=REAL; }
    | ( INT ('e'|'E'))  => REAL { $type=REAL; }
    |   INT                     { $type=INT; }
    ;

// String literals

STRING:	
    '\'' ( ~('\''|'\\') | ESC)* '\'';

// escape sequence -- note that this is protected; it can only be called
//   from another lexer rule -- it will not ever directly return a token to
//   the parser
// There are various ambiguities hushed in this rule.  The optional
// '0'...'7' digit matches should be matched here rather than letting
// them go back to STRING_LITERAL to be matched.  ANTLR does the
// right thing by matching immediately; hence, it's ok to shut off
// the FOLLOW ambig warnings.
fragment
ESC
:
    '\\'
     ( 'n'
     | 'r'
     | 't'
     | 'b'
     | 'f'
     | '"'
     | '\''
     | '\\'
     | 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
     | '0'..'3' ('0'..'7' ('0'..'7')? )?  | '4'..'7' ('0'..'7')?
     )
     ;

// hexadecimal digit (again, note it's protected!)
fragment
HEX_DIGIT:
    ( '0'..'9' | 'A'..'F' | 'a'..'f' );


// An identifier.  Note that testLiterals is set to true!  This means
// that after we match the rule, we look in the literals table to see
// if it's a literal or really an identifer.

IDENT
    /*options { 
        testLiterals = true; 
	paraphrase = "an identifier";
    }*/ :
    ('$' | 'a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*
    ;

// A dummy rule to force vocabulary to be all characters (except
// special ones that ANTLR uses internally (0 to 2)

fragment
VOCAB:	
    '\U0003'..'\U0377'
    ;
