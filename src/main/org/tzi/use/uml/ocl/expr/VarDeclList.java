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

package org.tzi.use.uml.ocl.expr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.Symtable;
import org.tzi.use.util.StringUtil;

/** 
 * A list of variable declarations.
 *
 * @author      Mark Richters 
 */
public class VarDeclList implements Iterable<VarDecl> {
    private List<VarDecl> fVarDecls;

    /**
     * An additional constraint specifying that all declarations must
     * have identical type.  
     */
    private boolean fAllHaveSameType = false;

    public VarDeclList(boolean allHaveSameType) {
        fVarDecls = new ArrayList<VarDecl>();
        fAllHaveSameType = allHaveSameType;
    }

    public VarDeclList(VarDecl varDecl) {
        fVarDecls = new ArrayList<VarDecl>(1);
        if (varDecl == null )
            throw new NullPointerException();
        fVarDecls.add(varDecl);
    }

    /**
     * Adds a variable declaration to the list. The following
     * constraints are enforced: (1) The declaration to be added must
     * have a variable name which is distinct from all currently
     * existing declarations in the list. (2) The new declaration must
     * have the same type as all currently existing declarations if
     * the list has been constructed with the allHaveSameType flag. 
     *
     * @exception IllegalArgumentException one of the constraints
     *            above failed.
     */
    public void add(VarDecl varDecl) {
        if (fVarDecls.contains(varDecl) )
            throw new IllegalArgumentException(varDecl.toString());

        if (fAllHaveSameType && ! fVarDecls.isEmpty() )
            if (! varDecl(0).type().equals(varDecl.type()) )
                throw new IllegalArgumentException("inconsistent type");
        
        fVarDecls.add(varDecl);
    }

    /**
     * Returns the nth variable declaration.
     */
    public VarDecl varDecl(int n) {
        return fVarDecls.get(n);
    }

    /**
     * Returns the number of variable declarations.
     */
    public int size() {
        return fVarDecls.size();
    }

    /**
     * Returns {@code true} if this list contains no declarations.
     */
    public boolean isEmpty() {
        return fVarDecls.isEmpty();
    }
    
    /**
	 * Returns {@code true} if this lists elements all have to be the same
	 * type.
	 */
	public boolean allHaveSameType() {
		return fAllHaveSameType;
	}
    
    /**
     * Returns {@code true} if this list contains a declaration for
     * {@code varName}.
     */
    public boolean containsName(String varName) {
        for (VarDecl decl : fVarDecls) {
            if (decl.name().equals(varName) )
                return true;
        }
        
        return false;
    }

    public int hashCode() {
        return 999;
    }

    public boolean equals(Object obj) {
        if (obj == this )
            return true;
        else if (obj instanceof VarDeclList )
            return ((VarDeclList) obj).fVarDecls.equals(fVarDecls);
        return false;
    }

    @Override
    public String toString() {
    	StringBuilder res = new StringBuilder();
    	this.toString(res);
    	return res.toString();
    }
    
    public void toString(StringBuilder sb) {
        if (fAllHaveSameType ) {
            Iterator<VarDecl> it = fVarDecls.iterator();
            
            while (it.hasNext() ) {
                VarDecl decl = it.next();
                sb.append(decl.name());
                
                if (it.hasNext() ) {
                    sb.append(", ");
                } else {
                    sb.append(" : ");
                    decl.type().toString(sb);
                }
            }
        } else {
            StringUtil.fmtSeq(sb, fVarDecls.iterator(), ", ");
        }
    }

	/**
	 * @param vars
	 * @throws SemanticException 
	 */
	public void addVariablesToSymtable(Symtable vars) throws SemanticException {
		for (VarDecl var : fVarDecls) {
			vars.add(var.name(), var.type(), var.getSourcePosition());
		}
	}
	
	@Override
	public Iterator<VarDecl> iterator() {
		return fVarDecls.iterator();
	}
	

	/**
	 * @param generateHTMLExpressionVisitor
	 */
	public void processWithVisitor(
			ExpressionVisitor v) {
		v.visitVarDeclList(this);		
	}
}

