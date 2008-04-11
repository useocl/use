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

$Id$

package org.tzi.use.uml.ocl.expr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tzi.use.util.StringUtil;

/** 
 * A list of variable declarations.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author      Mark Richters 
 */
public class VarDeclList {
    private List fVarDecls;

    /**
     * An additional constraint specifying that all declarations must
     * have identical type.  
     */
    private boolean fAllHaveSameType = false;

    public VarDeclList(boolean allHaveSameType) {
        fVarDecls = new ArrayList();
        fAllHaveSameType = allHaveSameType;
    }

    public VarDeclList(VarDecl varDecl) {
        fVarDecls = new ArrayList(1);
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
        return (VarDecl) fVarDecls.get(n);
    }

    /**
     * Returns the number of variable declarations.
     */
    public int size() {
        return fVarDecls.size();
    }

    /**
     * Returns <tt>true</tt> if this list contains no declarations.
     */
    public boolean isEmpty() {
        return fVarDecls.isEmpty();
    }

    /**
     * Returns <tt>true</tt> if this list contains a declaration for
     * <code>varName</code>.
     */
    public boolean containsName(String varName) {
        Iterator it = fVarDecls.iterator();
        while (it.hasNext() ) {
            VarDecl decl = (VarDecl) it.next();
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

    public String toString() {
        String res = "";
        if (fAllHaveSameType ) {
            Iterator it = fVarDecls.iterator();
            while (it.hasNext() ) {
                VarDecl decl = (VarDecl) it.next();
                res += decl.name();
                if (it.hasNext() )
                    res += ", ";
                else
                    res += " : " + decl.type();
            }
        } else
            res = StringUtil.fmtSeq(fVarDecls.iterator(), ", ");
        return res;
    }
}

