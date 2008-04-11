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

package org.tzi.use.parser.cmd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tzi.use.parser.Context;
import org.tzi.use.parser.MyToken;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTType;
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MCmdCreateObjects;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTCreateCmd extends ASTCmd {
    private List fIdList;   // (MyToken)
    private ASTType fType;

    public ASTCreateCmd(List idList, ASTType type) {
        fIdList = idList;
        fType = type;
    }

    public MCmd gen(Context ctx) {
        Type t = null;
        try {
            t = fType.gen(ctx);
            if (! t.isObjectType() ) {
                throw new SemanticException(fType.getStartToken(), 
                                            "Expected object type, found `" + t + "'.");
            } else {
                // map token list to string list
                List nameList = new ArrayList();
                Iterator it = fIdList.iterator();
                while (it.hasNext() ) {
                    MyToken tok = (MyToken) it.next();
                    nameList.add(tok.getText());
                }

                return new MCmdCreateObjects(ctx.systemState(), 
                                                   nameList, (ObjectType) t);
            }
        } catch (SemanticException ex) {
            ctx.reportError(ex);
        }
        return null;
    }

    public String toString() {
        return "FIXME";
    }
}
