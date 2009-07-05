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
import java.util.Iterator;
import java.util.List;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;

/**
 * @author green
 */
public abstract class ALLinkAction extends ALAction {

    private List<Expression> fExpressions;
    private MAssociation fAssociation;

    public ALLinkAction(List<Expression> exprList, MAssociation assoc) {
        fExpressions = exprList;
        fAssociation = assoc;
    }

    public MAssociation getAssociation() {
        return fAssociation;
    }
    
    /**
     * Evals all expressions and returns the result tuple. 
     */
    protected List<MObject> getLinkElements(EvalContext ctx) {
        List<MObject> link = new ArrayList<MObject>(fExpressions.size());
        
        for (Expression exp : fExpressions) {
            Value v = exp.eval(ctx);
            if (v.isDefined() && (v instanceof ObjectValue) ) {
                ObjectValue oval = (ObjectValue) v;
                MObject obj = oval.value();
                MObjectState objState = obj.state(ctx.postState());
                if (objState == null ) {
                    throw new RuntimeException("Object `" + obj.name() + 
                                                     "' does not exist anymore.");
                }
                link.add(obj);
            }
        }
        return link;
    }

    protected String getTupleForToString() {
        String res = "(";
        for (Iterator<Expression> it=fExpressions.iterator(); it.hasNext();) {
            res += it.next().toString();
            if (it.hasNext()) res += ", ";
        }
        return res + ")";
    }
}
