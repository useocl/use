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

import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.ocl.type.TupleType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTTupleType extends ASTType {
    private List<ASTTuplePart> fParts;    // (ASTTuplePart)

    public ASTTupleType(List<ASTTuplePart> parts) {
        fParts = parts;
    }

    public Type gen(Context ctx) throws SemanticException {
        Type res = null;
        TupleType.Part[] parts = new TupleType.Part[fParts.size()];
        int i = 0;
        
        for (ASTTuplePart tp : fParts) {
            Type t = tp.type().gen(ctx);
            parts[i++] = new TupleType.Part(i, tp.name().getText(), t);
        }
        res = TypeFactory.mkTuple(parts);
        return res;
    }
}

