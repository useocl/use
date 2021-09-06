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
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTCollectionType extends ASTType {
    private Token fName;
    private ASTType fElement;

    /**
     * Constructs a nested type. The type constructor is given as
     * <code>name</code>. 
     */
    public ASTCollectionType(Token name, ASTType elem) {
        fName = name;
        fElement = elem;
    }

    public Type gen(Context ctx) throws SemanticException {
        Type res;
        String name = fName.getText();
        // construct complex type recursively
        Type elemType = fElement.gen(ctx);
        if (name.equals("Set") )
            res = TypeFactory.mkSet(elemType);
        else if (name.equals("Sequence") )
            res = TypeFactory.mkSequence(elemType);
        else if (name.equals("Bag") )
            res = TypeFactory.mkBag(elemType);
        else if (name.equals("Collection") )
            res = TypeFactory.mkCollection(elemType);
        else if (name.equals("OrderedSet"))
        	res = TypeFactory.mkOrderedSet(elemType);
        else throw new SemanticException(fName,
                                         "Expected collection type, found `" + name + "'.");
        return res;
    }

    public String toString() {
        return "(" + fName.toString() + " " + fElement.toString() + ")";
    }
}
