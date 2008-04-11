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

package org.tzi.use.parser.use;

import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.MyToken;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTType;
import org.tzi.use.uml.mm.MAttribute;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTAttribute extends AST {
    private MyToken fName;
    private ASTType fType;

    public ASTAttribute(MyToken name, ASTType type) {
        fName = name;
        fType = type;
    }

    public MAttribute gen(Context ctx) throws SemanticException {
        MAttribute attr = ctx.modelFactory().createAttribute(fName.getText(), fType.gen(ctx));
        // sets the line position of the USE-Model in this attribute
        attr.setPositionInModel( fName.getLine() );
        return attr;
    }

    public MyToken nameToken() {
        return fName;
    }

    public String toString() {
        return "(" + fName + " " + fType + ")";
    }
}
