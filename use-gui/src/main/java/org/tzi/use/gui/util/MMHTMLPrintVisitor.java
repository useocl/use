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

package org.tzi.use.gui.util;

import java.io.PrintWriter;
import java.util.List;

import org.tzi.use.gui.main.ModelBrowserSorting;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MMPrintVisitor;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.ExpressionVisitor;
import org.tzi.use.uml.ocl.expr.GenerateHTMLExpressionVisitor;
import org.tzi.use.uml.sys.soil.MStatement;

/**
 * Visitor for dumping a HTML representation of model elements on an output
 * stream.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public class MMHTMLPrintVisitor extends MMPrintVisitor {

    public MMHTMLPrintVisitor(PrintWriter out) {
        super(out);
    }

    @Override
    protected ExpressionVisitor createExpressionVisitor() {
    	return new GenerateHTMLExpressionVisitor(fOut);
    }
    
    /**
     * Can be overriden by subclasses to achieve a different output style.
     */
    protected String keyword(String s) {
        return "<strong>" + s + "</strong>";
    }

    /**
     * Can be overriden by subclasses to achieve a different output style.
     */
    protected String other(String s) {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch ( c ) {
			case '<':
				b.append("&lt;");
				break;
			case '>':
				b.append("&gt;");
				break;
            default:
                b.append(c); 
            }
        }
        return b.toString();
    }

    /**
     * Can be overriden by subclasses to achieve a different output style.
     */
    protected void println(String s) {
        super.print(s);
        println();
    }

    /**
     * Can be overriden by subclasses to achieve a different output style.
     */
    protected void println() {
        print("<br>");
        super.println();
    }

    /**
     * Can be overriden by subclasses to achieve a different output style.
     */
    protected String ws() {
        return "&nbsp;";
    }
    
    public List<MAttribute> getAttributesForClass( MClass c ) {
		return ModelBrowserSorting.getInstance().sortAttributes(c.attributes());
    }

    public List<MOperation> getOperationsForClass( MClass c ) {
		return ModelBrowserSorting.getInstance().sortOperations(c.operations());
    }
    
    @Override
    protected String getStatementVisitorString(MStatement statement) {
    	String visitorString = super.getStatementVisitorString(statement);
    	
    	String result = 
    		visitorString.
    		replaceAll("\n", "<br>").
    		replaceAll("\\s", "&nbsp;");
    	
    	return result; 
    }
}
