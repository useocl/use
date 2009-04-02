package org.tzi.use.parser.base;

import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;
import org.tzi.use.parser.ParseErrorHandler;

public class BaseParser extends Parser {

	public BaseParser(TokenStream input) {
		super(input);
		SetTokennames();
	}

	public BaseParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
		SetTokennames();
	}
	
	private void SetTokennames() {
    	/* Dirty Hack for user friendly error messages
    	 * To get a use friendly error message the internal token names
    	 * must be translated.
    	 * During error message generation ANTLR calls getTokenErrorDisplay, but
    	 * this is only called for the invalid token.
    	 * The expected token name is just returned from the array.
    	 * Therefore the internal names are replaced by the translation given
    	 * in ParserHelper.
    	 */
		String[] names = getTokenNames();
        for(int i = 0; i < names.length; i++)
        {
        	names[i] = org.tzi.use.parser.base.ParserHelper.getTokenParaphrase(names[i]);
        }
    }
    
    public void init(ParseErrorHandler handler) {
        fParseErrorHandler = handler;
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
}
