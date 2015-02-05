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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.type.CollectionType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.util.StringUtil;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTElemVarsDeclaration extends AST {
    private List<Token> fIdList;
    private List<ASTType> fTypeList;

    public ASTElemVarsDeclaration() {
        fIdList = new ArrayList<Token>();
        fTypeList = new ArrayList<ASTType>();
    }

    /**
     * Adds a declaration to the list of element var declarations.
	 * @param varname The token representing the varname
	 * @param type The AST node of the type of the variable. Can be <code>null</code>.
	 */
	public void addDeclaration(Token varname, ASTType type) {
		this.fIdList.add(varname);
		this.fTypeList.add(type);
	}
	
    /**
     * Returns <tt>true</tt> if this list contains no declarations.
     */
    public boolean isEmpty() {
        return fIdList.isEmpty();
    }

    public VarDeclList gen(Context ctx, Expression range) throws SemanticException 
    {
        // variable type may be omitted in query expressions
        Type sourceElementType;
        
        if (range.type().isTypeOfVoidType()) {
        	sourceElementType = TypeFactory.mkVoidType();
        } else {
        	sourceElementType = ((CollectionType) range.type()).elemType();
        }

        // build list of VarDecls
        VarDeclList varDeclList = new VarDeclList(false);
        Type thisType;
        
        for (int index = 0; index < fIdList.size(); ++index) { 
        	Token id = fIdList.get(index);
        	ASTType type = fTypeList.get(index);
        	
		    if (varDeclList.containsName(id.getText())) {
		      throw new SemanticException(id, "double declared variable");
		    }
		    
		    if (type != null) {
		    	thisType = type.gen(ctx);
		    	if (!sourceElementType.conformsTo(thisType))
					throw new SemanticException(id, "Invalid type "
							+ StringUtil.inQuotes(thisType)
							+ " for source element type "
							+ StringUtil.inQuotes(sourceElementType)
							+ " specified.");
		    } else {
		    	thisType = sourceElementType;
		    }
		    
            VarDecl decl = new VarDecl(id, thisType);
            varDeclList.add(decl);
        }
        return varDeclList;
    }

    public Set<String> getVarNames() {
    	HashSet<String> result = new HashSet<String>();
    	Iterator<Token> it = fIdList.iterator();
    	while (it.hasNext()) {
    		result.add(it.next().getText());    		
    	}
    	return result;
    }
    
    public List<Token> getVarTokens() {
    	return this.fIdList;
    }
    
    public List<ASTType> getVarTypes() {
    	return this.fTypeList;
    }
    
    public int getNumVars() {
    	return this.fIdList.size();
    }
}
