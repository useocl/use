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

package org.tzi.use.parser.use;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.mm.MAggregationKind;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MModel;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTAssociation extends AST {
    private Token fKind;
    private Token fName;
    private List fAssociationEnds; // (ASTAssociationEnd)

    public ASTAssociation(Token kind, Token name) {
        fKind = kind;
        fName = name;
        fAssociationEnds = new ArrayList();
    }

    public void addEnd(ASTAssociationEnd ae) {
        fAssociationEnds.add(ae);
    }

    public MAssociation gen(Context ctx, MModel model) 
        throws SemanticException 
    {
        MAssociation assoc = ctx.modelFactory().createAssociation(fName.getText());
        // sets the line position of the USE-Model in this association
        assoc.setPositionInModel( fName.getLine() );
        String kindname = fKind.getText();
        int kind = MAggregationKind.NONE;
        if (kindname.equals("association") ) {
        }
        else if (kindname.equals("aggregation") )
            kind = MAggregationKind.AGGREGATION;
        else if (kindname.equals("composition") )
            kind = MAggregationKind.COMPOSITION;

        Iterator it = fAssociationEnds.iterator();
        try {
            while (it.hasNext() ) {
                ASTAssociationEnd ae = (ASTAssociationEnd) it.next();

                // kind of association determines kind of first
                // association end
                MAssociationEnd aend = ae.gen(ctx, kind);
                assoc.addAssociationEnd(aend);

                // further ends are plain ends
                kind = MAggregationKind.NONE;
            }
            model.addAssociation(assoc);
        } catch (MInvalidModelException ex) {
            throw new SemanticException(fName,
                                        "In " + MAggregationKind.name(assoc.aggregationKind()) + " `" +
                                        assoc.name() + "': " + 
                                        ex.getMessage());
        }
        return assoc;
    }

    public String toString() {
        return "FIXME";
    }
}
