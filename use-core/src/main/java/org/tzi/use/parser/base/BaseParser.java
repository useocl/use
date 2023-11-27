package org.tzi.use.parser.base;

import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;
import org.tzi.use.output.UserOutput;
import org.tzi.use.parser.ParseErrorHandler;

public class BaseParser extends Parser {
	
	private UserOutput output;

	public BaseParser(TokenStream input) {
		super(input);
		setTokennames();
	}

	public BaseParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
		setTokennames();
	}
	
	private void setTokennames() {
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
        this.output = handler.getOutput();
    }

    /* Overridden methods. */
	private ParseErrorHandler fParseErrorHandler;
    
    @Override
	public String getErrorHeader(RecognitionException e) {
		return "line " + e.line + ":" + e.charPositionInLine;
	}

	public void emitErrorMessage(String msg) {
       	fParseErrorHandler.reportError(msg);
	}
    
    /** Parser warning-reporting function */
    public void reportWarning(String s) {
        
    	this.output.printlnWarn(
    			(getSourceName() == null ? "" : getSourceName() + ": ") +
    			"warning: " +
    			s);
    }
}
