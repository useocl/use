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

import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.util.Log;

/**
 * Deletes a link.
 * @author green
 */
public class ALDestroyObject extends ALAction {

    Expression fExpression;

    public ALDestroyObject(Expression expr) {
        fExpression = expr;
    }

    public void exec(EvalContext ctx) throws MSystemException {
        ObjectValue v = (ObjectValue)fExpression.eval(ctx);
        Log.verbose("DESTROY " + v.value());
        ctx.postState().deleteObject(v.value());
        ctx.postState().system().varBindings().remove(v.value().name());
    }

    public String toString() {
        return "destroy " + fExpression;
    }

    public void processWithVisitor(MMVisitor v) {
        v.visitALDestroyObject(this);
    }
    
}
