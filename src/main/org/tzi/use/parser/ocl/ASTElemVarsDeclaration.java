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

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.Symtable;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTElemVarsDeclaration extends AST {
    private List<Token> fIdList;
    private ASTType fType;  // optional: may be null

    public ASTElemVarsDeclaration(List<Token> idList, ASTType type) {
        fIdList = idList;
        fType = type;
    }

    public ASTElemVarsDeclaration() {
        fIdList = new ArrayList<Token>();
        fType = null;
    }

    /**
     * Returns <tt>true</tt> if this list contains no declarations.
     */
    public boolean isEmpty() {
        return fIdList.isEmpty();
    }

    public VarDeclList gen(Context ctx, Expression range) 
        throws SemanticException 
    {
        // variable type may be omitted in query expressions
        Type type;
        if (fType == null ) {
            // infer type from range expression, cast cannot fail
            // since the type has been checked by caller.
            CollectionType ctype = (CollectionType) range.type();
            type = ctype.elemType();
        } else {
            type = fType.gen(ctx);
        }

        // build list of VarDecls, all vars have the same type
        VarDeclList varDeclList = new VarDeclList(true);
        
        for (Token id : fIdList) {
        	
		    if (varDeclList.containsName(id.getText())) {
		      throw new SemanticException(id, "double declared variable");
		    }
		    
            VarDecl decl = new VarDecl(id.getText(), type);
            varDeclList.add(decl);
        }
        return varDeclList;
    }

    void addVariablesToSymtable(Symtable vars, Type type) 
        throws SemanticException 
    {
        for (Token id : fIdList) {
            vars.add(id, type);
        }
    }
}
