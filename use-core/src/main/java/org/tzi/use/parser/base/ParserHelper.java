package org.tzi.use.parser.base;

import java.util.HashMap;

import org.antlr.runtime.Token;

public class ParserHelper {
	final static String Q_COLLECT  = "collect";
    final static String Q_COLLECTNESTED  = "collectNested";
    final static String Q_SELECT   = "select";
    final static String Q_REJECT   = "reject";
    final static String Q_FORALL   = "forAll";
    final static String Q_EXISTS   = "exists";
    final static String Q_ISUNIQUE = "isUnique";
    final static String Q_SORTEDBY = "sortedBy";
    final static String Q_ANY      = "any";
    final static String Q_ONE      = "one";
    final static String Q_CLOSURE  = "closure";

    public final static int Q_COLLECT_ID  = 1;
    public final static int Q_SELECT_ID   = 2;
    public final static int Q_REJECT_ID   = 3;
    public final static int Q_FORALL_ID   = 4;
    public final static int Q_EXISTS_ID   = 5;
    public final static int Q_ISUNIQUE_ID = 6;
    public final static int Q_SORTEDBY_ID = 7;
    public final static int Q_ANY_ID      = 8;
    public final static int Q_ONE_ID      = 9;
    public final static int Q_COLLECTNESTED_ID  = 10;
    public final static int Q_CLOSURE_ID  = 11;
    
    public final static HashMap<String, Integer> queryIdentMap = new HashMap<String, Integer>();

    static {
        queryIdentMap.put(Q_COLLECT,		Integer.valueOf(Q_COLLECT_ID));
        queryIdentMap.put(Q_SELECT,			Integer.valueOf(Q_SELECT_ID));
        queryIdentMap.put(Q_REJECT,			Integer.valueOf(Q_REJECT_ID));
        queryIdentMap.put(Q_FORALL,			Integer.valueOf(Q_FORALL_ID));
        queryIdentMap.put(Q_EXISTS,			Integer.valueOf(Q_EXISTS_ID));
        queryIdentMap.put(Q_ISUNIQUE,		Integer.valueOf(Q_ISUNIQUE_ID));
        queryIdentMap.put(Q_SORTEDBY,		Integer.valueOf(Q_SORTEDBY_ID));
        queryIdentMap.put(Q_ANY,			Integer.valueOf(Q_ANY_ID));
        queryIdentMap.put(Q_ONE,			Integer.valueOf(Q_ONE_ID));
        queryIdentMap.put(Q_COLLECTNESTED,	Integer.valueOf(Q_COLLECTNESTED_ID));
        queryIdentMap.put(Q_CLOSURE,		Integer.valueOf(Q_CLOSURE_ID));
    }
    
    public static boolean isQueryIdent(Token t) {
        return queryIdentMap.containsKey(t.getText());
    }
    
    /*
     * Keeps track of user friendly token names.
     * Because each language has an own lexer the token names
     * are mapped from string to string and not from integer to string
     */
    public final static HashMap<String, String> tokenParaphrases = new HashMap<String, String>();
    
    static {
    	tokenParaphrases.put("ARROW", "->");
    	tokenParaphrases.put("RBRACE", "}");
    	tokenParaphrases.put("AT", "@");
    	tokenParaphrases.put("BAR", "|");
    	tokenParaphrases.put("COLON", ":");
    	tokenParaphrases.put("COLON_COLON", "::");
    	tokenParaphrases.put("COLON_EQUAL", ":=");
    	tokenParaphrases.put("COMMA", ",");
    	tokenParaphrases.put("DOT", ".");
    	tokenParaphrases.put("DOTDOT", "..");
    	tokenParaphrases.put("EQUAL", "=");
    	tokenParaphrases.put("GREATER", ">");
    	tokenParaphrases.put("GREATER_EQUAL", ">=");
    	tokenParaphrases.put("HASH", "#");
    	tokenParaphrases.put("LBRACE", "{");
    	tokenParaphrases.put("LBRACK", "[");
    	tokenParaphrases.put("LESS", "<");
    	tokenParaphrases.put("LESS_EQUAL", "<=");
    	tokenParaphrases.put("LPAREN", "(");
    	tokenParaphrases.put("MINUS", "-");
    	tokenParaphrases.put("NOT_EQUAL", "<>");
    	tokenParaphrases.put("PLUS", "+");
    	tokenParaphrases.put("RBRACE", "}");
    	tokenParaphrases.put("RPAREN", ")");
    	tokenParaphrases.put("SEMI", ";");
    	tokenParaphrases.put("SLASH", "/");
    	tokenParaphrases.put("STAR", "*");
    	tokenParaphrases.put("IDENT", "an identifier");
    }
    
    public static String getTokenParaphrase(String internalTokenname)
    {
    	if (tokenParaphrases.containsKey(internalTokenname))
    		return tokenParaphrases.get(internalTokenname);
    	else
    		return internalTokenname;
    }
}
