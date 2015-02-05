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
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.use.ASTAnnotatable;
import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.uml.ocl.type.TypeFactory;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTEnumTypeDefinition extends ASTAnnotatable {
    private Token fName;
    private List<Token> fIdList;

    public ASTEnumTypeDefinition(Token name, List<Token> idList) {
        fName = name;
        fIdList = idList;
    }

    public EnumType gen(Context ctx) throws SemanticException {
        EnumType res;
        String name = fName.getText();

        // map token list to string list
        List<String> literalList = new ArrayList<String>();
        
        for (Token tok : fIdList) {
            literalList.add(tok.getText());
        }
    
        try {
            res = TypeFactory.mkEnum(name, literalList);
            // makes sure we have a unique type name
            ctx.typeTable().add(fName, res);
        } catch (IllegalArgumentException ex) {
            throw new SemanticException(fName, "Error in enumeration type: " +
                                        ex.getMessage() + ".");
        }
        
        res.setPositionInModel(fName.getLine());
        this.genAnnotations(res);
        
        return res;
    }
}
