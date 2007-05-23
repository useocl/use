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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

package org.tzi.use.parser.use;

import java.util.ArrayList;
import java.util.Iterator;

import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.MyToken;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTType;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.uml.ocl.type.Type;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTConstraintDefinition extends AST {
    private MyToken fVarName;   // optional
    private ASTType fType;
    private ArrayList fInvariantClauses; // (ASTInvariantClause)

    public ASTConstraintDefinition() {
        fInvariantClauses = new ArrayList();
    }

    public void addInvariantClause(ASTInvariantClause inv) {
        fInvariantClauses.add(inv);
    }

    public void setVarName(MyToken tok) {
        fVarName = tok;
    }

    public void setType(ASTType t) {
        fType = t;
    }

    public void gen(Context ctx) {
        try {
            Type t = fType.gen(ctx);
            if (! t.isObjectType() )
                throw new SemanticException(fType.getStartToken(), 
                                            "Expected an object type, found `" +
                                            t + "'");
            MClass cls = ((ObjectType) t).cls();
            ctx.setCurrentClass(cls);
            Iterator it = fInvariantClauses.iterator();
            while (it.hasNext() ) {
                ASTInvariantClause astInv = (ASTInvariantClause) it.next();
                astInv.gen(ctx, fVarName, cls);
            }
        } catch (SemanticException ex) {
            ctx.reportError(ex);
        } finally {
            ctx.setCurrentClass(null);
        }
    }
}
