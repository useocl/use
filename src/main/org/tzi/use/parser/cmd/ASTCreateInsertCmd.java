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

package org.tzi.use.parser.cmd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MCmdCreateInsertObjects;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */
public class ASTCreateInsertCmd extends ASTCmd {
    private Token fNameCreate;
    private List fIdListInsert; // (MyToken)
    private Token fAssocClassName;

    public ASTCreateInsertCmd( Token nameCreate, Token assocClassName, 
                               List idListInsert ) {
        fNameCreate = nameCreate;
        fAssocClassName = assocClassName;
        fIdListInsert = idListInsert;
    }

    public MCmd gen( Context ctx ) {
        try {
            MAssociationClass assocClass =
                    ctx.model().getAssociationClass( fAssocClassName.getText() );
            if ( assocClass == null ) {
                throw new SemanticException( fAssocClassName, "association class `"
                                                              + fAssocClassName.getText() + "' does not exist." );
            }
            if ( assocClass.associationEnds().size() != fIdListInsert.size() ) {
                throw new SemanticException( fAssocClassName, "association class `"
                                                              + fAssocClassName.getText() + "' requires "
                                                              + assocClass.associationEnds().size()
                                                              + " objects, found " + fIdListInsert.size() + "." );
            }

            // map token list to string list

            List nameListInsert = new ArrayList();
            Iterator it = fIdListInsert.iterator();
            while ( it.hasNext() ) {
            	Token tok = ( Token ) it.next();
                nameListInsert.add( tok.getText() );
            }
            return new MCmdCreateInsertObjects( ctx.systemState(),
                                                fNameCreate.getText(),
                                                assocClass, nameListInsert );
        } catch ( SemanticException ex ) {
            ctx.reportError( ex );
        }
        return null;
    }

    public String toString() {
        return "FIXME";
    }
}
