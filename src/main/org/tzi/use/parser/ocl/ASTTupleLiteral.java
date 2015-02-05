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

import java.util.List;
import java.util.Set;

import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.ocl.expr.ExpTupleLiteral;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTTupleLiteral extends ASTExpression {
    private List<ASTTupleItem> fItems;

    public ASTTupleLiteral(List<ASTTupleItem> items) {
        fItems = items;
    }

    public Expression gen(Context ctx) throws SemanticException {
        ExpTupleLiteral.Part[] parts = new ExpTupleLiteral.Part[fItems.size()];
        int i = 0;
        
        for (ASTTupleItem ti : fItems) {
            Expression e = ti.expression().gen(ctx);
            
            // If a type was given, check conformance
            if (ti.getType() != null) {
            	Type itemType = ti.getType().gen(ctx);
            	if (!e.type().conformsTo(itemType)) {
            		throw new SemanticException(ti.name(), "Tuple part expression does not match the given part type");
            	}
            	parts[i++] = new ExpTupleLiteral.Part(ti.name().getText(), e, itemType);
            }
            else {
            	parts[i++] = new ExpTupleLiteral.Part(ti.name().getText(), e);
            }
        }
        
        return new ExpTupleLiteral(parts);
    }

	@Override
	public void getFreeVariables(Set<String> freeVars) {
		for (ASTTupleItem item : fItems) {
			item.getFreeVariables(freeVars);
		}
	}
}
