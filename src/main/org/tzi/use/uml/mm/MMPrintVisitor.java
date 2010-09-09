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

package org.tzi.use.uml.mm;

import java.io.PrintWriter;
import java.util.Set;

import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.StringUtil;

/**
 * Visitor for dumping a string representation of model elements on an
 * output stream. The output respects the concrete syntax rules for a
 * model specification and can be directly fed back into the
 * specification parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public class MMPrintVisitor implements MMVisitor {
    protected PrintWriter fOut;
    private int fIndent;    // number of columns to indent output
    private int fIndentStep = 2;

    public MMPrintVisitor(PrintWriter out) {
        fOut = out;
        fIndent = 0;
    }

    /**
     * Can be overriden by subclasses to achieve a different output style.
     */
    protected String keyword(String s) {
        return s;
    }

    /**
     * Can be overriden by subclasses to achieve a different output style.
     */
    protected String id(String s) {
        return s;
    }

    /**
     * Can be overriden by subclasses to achieve a different output style.
     */
    protected String other(String s) {
        return s;
    }

    /**
     * Can be overriden by subclasses to achieve a different output style.
     */
    protected void println(String s) {
        fOut.println(s);
    }

    /**
     * Can be overriden by subclasses to achieve a different output style.
     */
    protected void print(String s) {
        fOut.print(s);
    }

    /**
     * Can be overriden by subclasses to achieve a different output style.
     */
    protected void println() {
        fOut.println();
    }

    /**
     * Can be overriden by subclasses to achieve a different output style.
     */
    protected String ws() {
        return " ";
    }

    /**
     * Can be overriden by subclasses to achieve a different output style.
     */
    protected void indent() {
        for (int i = 0; i < fIndent; i++)
            fOut.print(ws());
    }

    public void visitAssociation(MAssociation e) {
        indent();
        println(keyword(MAggregationKind.name(e.aggregationKind())) + ws() + 
                id(e.name()) + ws() + keyword("between"));

        incIndent();
        
        visitAssociationEnds( e );
        
        println(keyword("end"));
    }

    private void visitAssociationEnds( MAssociation e ) {
        // visit association ends
        for (MAssociationEnd assocEnd : e.associationEnds()) {
            assocEnd.processWithVisitor(this);
        }
        
        decIndent();
        indent();
    }
    
    public void visitAssociationClass( MAssociationClass e ) {
    	indent();
        if ( e.isAbstract() ) {
            print( keyword( "abstract" ) );
            print( ws() );
        }
        
        print( keyword( "associationclass" ) );
        print( ws() );
        print( id( e.name() ) );

        Set<MClass> parents = e.parents();
        if ( !parents.isEmpty() ) {
            fOut.print( ws() + other( "<" ) + ws() +
                        other( StringUtil.fmtSeq( parents.iterator(), "," ) ) );
        }

        // visit aggregation kind
        if ( e.aggregationKind() == MAggregationKind.NONE ) {
            // normale associationclass
            indent();
            println( ws() + keyword( "between" ) );
        } else {
            // aggregations or composition
            indent();
            println( ws() + keyword( MAggregationKind.name( e.aggregationKind() ) ) + ws() +
                     keyword( "between" ) );
        }
        incIndent();

        visitAssociationEnds( e );
        visitAttributesAndOperations( e );
        
        indent();
        println( keyword( "end" ) );
    }

    public void visitAssociationEnd(MAssociationEnd e) {
        indent();
        StringBuilder result = new StringBuilder();
        
        result.append(id(e.cls().name()));
        result.append(other("[" + e.multiplicity() + "]"));
        result.append(ws());
        result.append(keyword("role"));
        result.append(ws());
        result.append(id(e.name()));
        
        if (e.getSubsettedEnds().size() > 0) {
        	for (MAssociationEnd end : e.getSubsettedEnds()) {
        		result.append(ws());
        		result.append(keyword("subsets"));
        		result.append(ws());
        		result.append(end.nameAsRolename());
        	}
        }
        
        if (e.isUnion()) {
        	result.append(ws());
        	result.append(keyword("union"));
        }
        
        if (e.isOrdered()) {
        	result.append(ws());
        	result.append(keyword("ordered"));
        }
                
        println(result.toString());
    }

    public void visitAttribute(MAttribute e) {
        indent();
        println(id(e.name()) + ws() + other(":") + ws() +
                other(e.type().toString()));
    }

    private void visitAttributesAndOperations( MClass e ) {
        // visit attributes
        if (e.attributes().size() > 0 ) {
            indent();
            println(keyword("attributes"));
            incIndent();
            
            for (MAttribute attr : e.attributes()) {
                attr.processWithVisitor(this);
            }
            decIndent();
        }

        // visit operations
        if (e.operations().size() > 0 ) {
            indent();
            println(keyword("operations"));
            incIndent();
            
            for (MOperation op : e.operations()) {
                op.processWithVisitor(this);
            }
            
            decIndent();
        }
    }
    
    public void visitClass(MClass e) {
        indent();
        if (e.isAbstract() )
            print(keyword("abstract") + ws());
        print(keyword("class") + ws() + id(e.name()));

        Set<MClass> parents = e.parents();
        if (! parents.isEmpty() ) {
            fOut.print(ws() + other("<") + ws() + 
                       other(StringUtil.fmtSeq(parents.iterator(), ",")));
        }
        println();
        
        visitAttributesAndOperations( e );
        
        indent();
        println(keyword("end")); 
    }

    public void visitClassInvariant(MClassInvariant e) {
    	StringBuilder line = new StringBuilder();
    	line.append(keyword("context"));
    	line.append(ws());
    	
    	if (e.hasVar()) {
    		line.append(id(e.var()));
    		line.append(ws());
    		line.append(other(":"));
    		line.append(ws());
    	}
    	
    	line.append(other(e.cls().name()));
    	line.append(ws());
    	
    	if (e.isExistential()) {
    		line.append(keyword("existential"));
    		line.append(ws());
    	}
    	
    	line.append(keyword("inv"));
    	line.append(ws());
    	line.append(id(e.name()));
    	line.append(other(":"));
        
    	println(line.toString());
        
        incIndent();
        indent();
        println(other(e.bodyExpression().toString()));
        decIndent();
    }

    public void visitGeneralization(MGeneralization e) {
    }

    public void visitModel(MModel e) {
        indent(); 
        println(keyword("model") + ws() + id(e.name()));
        println();
    
        // print user-defined data types
        for (EnumType t : e.enumTypes()) {
            indent();
            println(keyword("enum") + ws() + other(t.toString()) + ws() + 
                    other("{") + ws() +
                    other(StringUtil.fmtSeq(t.literals(), ", ")) + ws() +
                    other("};"));
        }
        println();

        // visit classes
        for (MClass cls : e.classes()) {
            cls.processWithVisitor(this);
            println();
        }

        // visit associations
        for (MAssociation assoc : e.associations()) {
            assoc.processWithVisitor(this);
            println();
        }

        // visit constraints
        indent(); 
        println(keyword("constraints"));

        // invariants
        for (MClassInvariant inv : e.classInvariants()) {
            inv.processWithVisitor(this);
            println();
        }

        // pre-/postconditions
        for (MPrePostCondition ppc : e.prePostConditions()) {
            ppc.processWithVisitor(this);
            println();
        }
    }

    public void visitOperation(MOperation e) {
        indent(); 
        print(id(e.name()) + 
              other("(" + e.paramList() + ")"));
        
        if (e.hasResultType() ) {
            print(ws() + other(":") + ws() + other(e.resultType().toString()));
        }
        
        if (e.hasExpression() ) {
        	println(ws() + other("=") + ws());
            incIndent();
            indent(); 
            print(other(e.expression().toString()));
            decIndent();
            println();
        } else if (e.hasStatement()) {
        	println();
        	incIndent();
        	indent();
        	println(keyword("begin"));
        	incIndent();
        	println(getStatementVisitorString(e.getStatement()));
            decIndent();
            indent();
            println(keyword("end"));
            decIndent();
        }
    }
    
    protected String getStatementVisitorString(MStatement statement) {
    	return statement.toVisitorString(fIndent, fIndentStep);
    }
    
    public void visitPrePostCondition(MPrePostCondition e) {
        println(keyword("context") + ws() +
                other(e.cls().name()) + other("::") +
                other(e.operation().signature()));
        incIndent();
        indent();
        println(keyword(e.isPre() ? "pre" : "post") + 
                ws() + id(e.name()) + other(":") + ws() +
                other(e.expression().toString()));
        decIndent();
    }

    private void incIndent() {
        fIndent += fIndentStep;
    }

    private void decIndent() {
        if (fIndent < fIndentStep )
            throw new RuntimeException("unbalanced indentation");
        fIndent -= fIndentStep;
    }
}
