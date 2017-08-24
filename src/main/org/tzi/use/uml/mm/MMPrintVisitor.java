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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tzi.use.uml.mm.commonbehavior.communications.MSignal;
import org.tzi.use.uml.ocl.expr.ExpressionPrintVisitor;
import org.tzi.use.uml.ocl.expr.ExpressionVisitor;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.uml.sorting.UseFileOrderComparator;
import org.tzi.use.util.uml.sorting.UseModelElementFileOrderComparator;

/**
 * Visitor for dumping a string representation of model elements on an
 * output stream. The output respects the concrete syntax rules for a
 * model specification and can be directly fed back into the
 * specification parser.
 *
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
            print(ws());
    }

    @Override
	public void visitAssociation(MAssociation e) {
        visitAnnotations(e);
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
    
    @Override
	public void visitAssociationClass( MAssociationClass e ) {
    	visitAnnotations(e);
    	indent();
        if ( e.isAbstract() ) {
            print( keyword( "abstract" ) );
            print( ws() );
        }
        
        print( keyword( "associationclass" ) );
        print( ws() );
        print( id( e.name() ) );

        Set<MAssociationClass> parents = e.parents();
        if ( !parents.isEmpty() ) {
            print( ws() + other( "<" ) + ws() +
                        other( StringUtil.fmtSeq( parents.iterator(), "," ) ) );
        }

        // check parents for definition of roles
        boolean doAssociationEnds = true;
        List<MAssociationEnd> associationEnds = e.associationEnds();
        for(MAssociationClass parent : parents){
        	List<MAssociationEnd> parentAssociationEnds = parent.associationEnds();
        	boolean allEndsTheSame = true;
        	for(int i = 0; i < associationEnds.size(); i++){
        		MAssociationEnd associationEnd = associationEnds.get(i);
        		MAssociationEnd parentAssociationEnd = parentAssociationEnds.get(i);
        		
				if (!associationEnd.name().equals(parentAssociationEnd.name()) && associationEnd.getRedefinedEnds().contains(parentAssociationEnd)) {
        			allEndsTheSame = false;
        			break;
        		}
        	}
        	if(allEndsTheSame){
        		doAssociationEnds = false;
        	}
        }
        if(parents.isEmpty() || doAssociationEnds){
        	// visit aggregation kind
        	if ( e.aggregationKind() == MAggregationKind.NONE ) {
        		// normal association class
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
        } else {
        	println();
        }
        visitAttributesAndOperations( e );
        
        indent();
        println( keyword( "end" ) );
    }

    @Override
	public void visitAssociationEnd(MAssociationEnd e) {
        visitAnnotations(e);
        StringBuilder result = new StringBuilder();
        
        indent();
        result.append(id(e.cls().name()));
        result.append(other("[" + e.multiplicity() + "]"));
        
        if(!e.cls().nameAsRolename().equals(e.nameAsRolename())){
        	result.append(ws());
        	result.append(keyword("role"));
        	result.append(ws());
        	result.append(id(e.name()));
        }
        
        if (e.hasQualifiers()) {
        	result.append(ws());
        	result.append(keyword("qualifier"));
        	result.append(ws());
        	result.append('(');
        	
        	boolean first = true;
        	for (VarDecl q : e.getQualifiers()) {
        		if (!first){
        			result.append(',');
					result.append(ws());
        		}
        		result.append(q.toString());
        		first = false;
        	}
        	result.append(')');
        }
        
        if (e.getSubsettedEnds().size() > 0) {
        	for (MAssociationEnd end : e.getSubsettedEnds()) {
        		result.append(ws());
        		result.append(keyword("subsets"));
        		result.append(ws());
        		result.append(end.nameAsRolename());
        	}
        }
        
        if (e.getRedefinedEnds().size() > 0) {
        	for (MAssociationEnd end : e.getRedefinedEnds()) {
        		result.append(ws());
        		result.append(keyword("redefines"));
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
        
        if (e.isDerived()) {
        	result.append(ws());
        	result.append(keyword("derived"));
        	result.append(ws());
        	result.append(other("="));
        	result.append(ws());
        	print(result.toString());
        	ExpressionVisitor visitor = createExpressionVisitor();
        	e.getDeriveExpression().processWithVisitor(visitor);
        	result = new StringBuilder();
        }
        
        println(result.toString());
    }

    @Override
	public void visitAttribute(MAttribute e) {
        visitAnnotations(e);
        
        indent();
        print(id(e.name()) + ws() + other(":") + ws() +
                other(e.type().toString()));
        
        if(e.getInitExpression().isPresent()){
        	print(ws() + keyword("init") + ws() + other(":") + ws());
        	ExpressionVisitor v = createExpressionVisitor();
        	e.getInitExpression().get().processWithVisitor(v);
        }
        else if(e.isDerived()){
        	print(ws() + keyword("derived") + ws() + other(":") + ws());
        	ExpressionVisitor v = createExpressionVisitor();
        	e.getDeriveExpression().processWithVisitor(v);
        }
        println();
    }

    private void visitAttributesAndOperations( MClass e ) {
        // visit attributes
        if (e.attributes().size() > 0 ) {
            indent();
            println(keyword("attributes"));
            incIndent();
            
            MAttribute[] attributes = e.attributes().toArray(new MAttribute[0]);
            Arrays.sort(attributes, new UseFileOrderComparator());
            
        	for (MAttribute attr : attributes) {
				attr.processWithVisitor(this);
			}
            
            decIndent();
        }

        // visit operations
        if (e.operations().size() > 0 ) {
            indent();
            println(keyword("operations"));
            incIndent();
            
            MOperation[] operations = e.operations().toArray(new MOperation[0]);
            Arrays.sort(operations, new UseFileOrderComparator());
            
            for (MOperation op : operations) {
                op.processWithVisitor(this);
            }
            
            decIndent();
        }
    }
    
    @Override
	public void visitClass(MClass e) {
        visitAnnotations(e);
        indent();
        if (e.isAbstract() )
            print(keyword("abstract") + ws());
        print(keyword("class") + ws() + id(e.name()));

        Set<? extends MClassifier> parents = e.parents();
        if (! parents.isEmpty() ) {
            print(ws() + other("<") + ws() + 
                       other(StringUtil.fmtSeq(parents.iterator(), ",")));
        }
        println();
        
        visitAttributesAndOperations( e );
        
        indent();
        println(keyword("end")); 
    }

    @Override
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
    	
    	if(e.isAnnotated()){
    		println(line.toString());
    		incIndent();
    		visitAnnotations(e);
    		line = new StringBuilder();
    		indent();
    	} else {
    		line.append(ws());
    	}
    	
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
        ExpressionVisitor visitor = createExpressionVisitor();
        e.bodyExpression().processWithVisitor(visitor);
        println();
        fOut.flush();
        decIndent();
    }

	protected ExpressionVisitor createExpressionVisitor() {
		return new ExpressionPrintVisitor(fOut);
	}

	@Override
	public void visitGeneralization(MGeneralization e) {
    }

    @Override
	public void visitModel(MModel e) {
        visitAnnotations(e);
        indent();
        println(keyword("model") + ws() + id(e.name()));
        println();
    
        // print user-defined data types
        EnumType[] enumTypes = e.enumTypes().toArray(new EnumType[0]);
        Arrays.sort(enumTypes, new UseFileOrderComparator());
        
        for (EnumType t : enumTypes) {
        	visitEnum(t);
        }
        println();

        // visit classes and associations together to maintain USE file order and easy handling of association classes
        Set<MModelElement> classesAndAssocs = new HashSet<MModelElement>();
        classesAndAssocs.addAll(e.classes());
        classesAndAssocs.addAll(e.associations());
        
        List<MModelElement> sortedClassesAndAssocs = new ArrayList<MModelElement>(classesAndAssocs);
        Collections.sort(sortedClassesAndAssocs, new UseModelElementFileOrderComparator());
        
        for(MModelElement element : sortedClassesAndAssocs){
        	element.processWithVisitor(this);
        	println();
        }

        // visit constraints
        indent(); 
        println(keyword("constraints"));

        // invariants
        MClassInvariant[] classInvariants = e.classInvariants().toArray(new MClassInvariant[0]);
        Arrays.sort(classInvariants, new UseFileOrderComparator());
        
        for (MClassInvariant inv : classInvariants) {
            inv.processWithVisitor(this);
            println();
        }

        // pre-/postconditions
        MPrePostCondition[] prePostConditions = e.prePostConditions().toArray(new MPrePostCondition[0]);
        Arrays.sort(prePostConditions, new UseFileOrderComparator());
        
        for (MPrePostCondition ppc : prePostConditions) {
            ppc.processWithVisitor(this);
            println();
        }
    }

    @Override
	public void visitOperation(MOperation e) {
        visitAnnotations(e);
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
            ExpressionVisitor visitor = createExpressionVisitor();
            e.expression().processWithVisitor(visitor);
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
        } else {
        	println();
        }
    }
    
    protected String getStatementVisitorString(MStatement statement) {
    	return statement.toConcreteSyntax(fIndent, fIndentStep);
    }
    
    @Override
	public void visitPrePostCondition(MPrePostCondition e) {
        println(keyword("context") + ws() +
                other(e.cls().name()) + other("::") +
                other(e.operation().signature()));
        incIndent();
        visitAnnotations(e);
        indent();
        print(keyword(e.isPre() ? "pre" : "post") + 
              ws() + id(e.name()) + other(":") + ws());
        
        ExpressionVisitor visitor = createExpressionVisitor();
        e.expression().processWithVisitor(visitor);
        println("");
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

	@Override
	public void visitAnnotation(MElementAnnotation a) {
		indent();
		print(keyword("@" + a.getName()));
		print("(");
		
		boolean first = true;
		
		for (Map.Entry<String, String> e : a.getValues().entrySet()) {
			if (!first)
				print(", ");
			
			print(id(e.getKey()));
			print("=\"");
			print(e.getValue());
			print("\"");
			
			first = false;
		}
		
		println(")");
	}
	
	private void visitAnnotations(Annotatable e) {
		for (MElementAnnotation a : e.getAllAnnotations().values()) {
			a.processWithVisitor(this);
		}
	}

	@Override
	public void visitSignal(MSignal s) {
		print(keyword("signal"));
		println(s.name());
		incIndent();
		indent();
		print(keyword("attributes"));
		incIndent();
		indent();
		for (MAttribute attr : s.getAttributes()) {
			attr.processWithVisitor(this);
		}
		decIndent();
		decIndent();
		println(keyword("end"));
	}

	@Override
	public void visitEnum(EnumType enumType) {
		visitAnnotations(enumType);
		indent();
		println(keyword("enum") + ws() + other(enumType.name()) + ws() + other("{"));
		
		incIndent();
		indent();
		println(other(StringUtil.fmtSeq(enumType.literals(), ", ")));
		
		decIndent();
		indent();
		println(ws() + other("};"));
	}
}
