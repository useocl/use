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
package org.tzi.use.uml.al;

import java.util.ArrayList;
import java.util.List;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MLinkObject;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.util.Log;

/**
 * Inserts a link.
 * @author green
 */
public class ALInsert extends ALLinkAction {

    public ALInsert(List exprs, MAssociation assoc) {
        super(exprs, assoc);
    }

    public void exec(EvalContext ctx) throws MSystemException {
        ArrayList link = getLinkElements(ctx);
        insertLink(ctx, link);
    }
    
    protected void insertLink(EvalContext ctx, ArrayList link) throws MSystemException {
        MLink newLink = ctx.postState().createLink(getAssociation(), link);
        Log.verbose("INSERT (" + link + ") INTO " + getAssociation().name());
        if ( newLink instanceof MLinkObject ) {
            MLinkObject obj = (MLinkObject) newLink;
            // Object name has to be pushed to varbindings. topLevelBindings is an 
            // independent List, where changes have no effects. 
            ctx.postState().system().varBindings().push( obj.name(), new ObjectValue( obj.type(), obj ) );
        }
    }

    public String toString() {
        return "insert " + getTupleForToString() + " into " + getAssociation().name();
    }

    public void processWithVisitor(MMVisitor v) {
        v.visitALInsert(this);
    }

}
