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

// $Id$

package org.tzi.use.parser.ocl;

import java.util.Set;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.uml.ocl.expr.ExpConstString;
import org.tzi.use.uml.ocl.expr.Expression;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTStringLiteral extends ASTExpression {
    private String fValue;

    public ASTStringLiteral(Token token) {
        String st = token.getText(); 
        
        if (st.startsWith("'") && st.endsWith("'")) {
        	// strip quotes
        	fValue = st.substring(1, st.length() - 1);
        } else {
        	fValue = st;
        }
        
        // Read Escape Characters
        convertString();
    }
    
    public ASTStringLiteral(String value) {
    	fValue = value;
    }

    public Expression gen(Context ctx) {
        return new ExpConstString(fValue); 
    }

    public String toString() {
        return fValue;
    }
    
    /**
     * Converts EscapeCharcters in a String to the corresponding Java-Esc-Characters
     * See: http://forum.java.sun.com/thread.jspa?threadID=733734&messageID=4219038
     * @param str
     * @return
     */
    private void convertString() {
        char[] strArr = fValue.toCharArray();
        boolean escape = false;
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < strArr.length; ++i) {
            if (escape) {
                if (strArr[i] == 'b') {
                    buf.append('\b');
                } else if (strArr[i] == 't') {
                    buf.append('\t');
                } else if (strArr[i] == 'n') {
                    buf.append('\n');
                } else if (strArr[i] == 'r') {
                    buf.append('\r');
                } else if (strArr[i] == 'f') {
                    buf.append('\f');
                } else if (strArr[i] == 'u') {    				
                    // Unicode escape
                    int utf = Integer.parseInt(fValue.substring(i + 1, i + 5), 16);
                    buf.append((char)utf);
                    i += 4;
                } else if (Character.isDigit(strArr[i])) {
                    // Octal escape
                    int j = 0;
                    for (j = 1; (j < 3) && (i + j < strArr.length); ++j) {
                        if (!Character.isDigit(strArr[i+j]))
                            break;
                    }
                    int octal = Integer.parseInt(fValue.substring(i, i + j), 8);
                    buf.append((char)octal);
                    i += j-1;
                } else {
                    buf.append(strArr[i]);
                }   			
                escape = false;
            } else if (strArr[i] == '\\') {
                escape = true;
            } else {
                buf.append(strArr[i]);
            }    		
        }
        fValue = buf.toString();
    }

	@Override
	public void getFreeVariables(Set<String> freeVars) { }
}
