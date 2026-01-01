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

package org.tzi.use.parser.ocl;

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

import java.util.*;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
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
     * Returns <code>true</code> if this list contains no declarations.
     */
    public boolean isEmpty() {
        return fIdList.isEmpty();
    }

    public VarDeclList gen(Context ctx, Expression range) throws SemanticException 
    {
        // helper: structural equality for types (recursive for collections)
        class TypeUtil {
            boolean structuralEqual(Type a, Type b) {
                if (a == null || b == null) return a == b;
                try {
                    if (a.getClass().equals(b.getClass())) {
                        // if both are collection types, compare element types structurally
                        if (a instanceof org.tzi.use.uml.ocl.type.CollectionType && b instanceof org.tzi.use.uml.ocl.type.CollectionType) {
                            org.tzi.use.uml.ocl.type.CollectionType ca = (org.tzi.use.uml.ocl.type.CollectionType) a;
                            org.tzi.use.uml.ocl.type.CollectionType cb = (org.tzi.use.uml.ocl.type.CollectionType) b;
                            return structuralEqual(ca.elemType(), cb.elemType());
                        }
                        // same runtime class and not collection -> rely on qualifiedName
                        return a.qualifiedName().equals(b.qualifiedName());
                    } else {
                        // different classes: try structural check for collection vs collection supertype
                        if (a instanceof org.tzi.use.uml.ocl.type.CollectionType && b instanceof org.tzi.use.uml.ocl.type.CollectionType) {
                            org.tzi.use.uml.ocl.type.CollectionType ca = (org.tzi.use.uml.ocl.type.CollectionType) a;
                            org.tzi.use.uml.ocl.type.CollectionType cb = (org.tzi.use.uml.ocl.type.CollectionType) b;
                            return structuralEqual(ca.elemType(), cb.elemType());
                        }
                        return a.qualifiedName().equals(b.qualifiedName());
                    }
                } catch (Throwable t) {
                    return a.qualifiedName().equals(b.qualifiedName());
                }
            }
        }
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

                boolean ok = false;
                try {
                    ok = sourceElementType.conformsTo(thisType)
                         || thisType.conformsTo(sourceElementType)
                         || sourceElementType.equals(thisType)
                         || sourceElementType.qualifiedName().equals(thisType.qualifiedName());
                } catch (Throwable t) {
                    // ignore and rely on name equality check below
                }

                // structural equality fallback
                if (!ok) {
                    TypeUtil tu = new TypeUtil();
                    if (tu.structuralEqual(sourceElementType, thisType)) {
                        ok = true;
                    }
                }

                if (!ok) {
                    // Diagnostic: print detailed info to help debugging
                    try {
                        StringBuilder dbg = new StringBuilder();
                        dbg.append("ASTElemVarsDeclaration: sourceElementType.class=").append(sourceElementType.getClass().getName())
                           .append(", qualifiedName=").append(sourceElementType.qualifiedName()).append("\n");
                        dbg.append("ASTElemVarsDeclaration: thisType.class=").append(thisType.getClass().getName())
                           .append(", qualifiedName=").append(thisType.qualifiedName()).append("\n");
                        dbg.append("ASTElemVarsDeclaration: conforms(sourceElementType -> thisType) = ")
                           .append(sourceElementType.conformsTo(thisType)).append("\n");
                        dbg.append("ASTElemVarsDeclaration: conforms(thisType -> sourceElementType) = ")
                           .append(thisType.conformsTo(sourceElementType)).append("\n");
                        System.err.println(dbg.toString());
                    } catch (Throwable t) {
                        // ignore
                    }

                    throw new SemanticException(id, "Invalid type "
                                    + StringUtil.inQuotes(thisType)
                                    + " for source element type "
                                    + StringUtil.inQuotes(sourceElementType)
                                    + " specified.");
                }
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
