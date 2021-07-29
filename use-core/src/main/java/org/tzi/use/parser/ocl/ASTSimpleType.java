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

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @author Mark Richters
 * @author Lars Hamann
 */
public class ASTSimpleType extends ASTType {
    private Token fName;

    public ASTSimpleType(Token name) {
        fName = name;
    }

    public Type gen(Context ctx) throws SemanticException {
        String name = fName.getText();
        Type res = TypeFactory.mkSimpleType(name);

        if (res == null) { 
            // check for enumeration type
            //TODO: Add enums to MModel.getClassifier()
        	res = ctx.model().enumType(name);
            
            if (res == null ) {
                // check for object type
                res = ctx.model().getClassifier(name);
                
                if (res == null )
                    throw new SemanticException(fName,
                                                "Expected type name, found `" + name + "'.");
            }
        }
        
        return res;
    }

    public String toString() {
        return fName.getText();
    }
}
