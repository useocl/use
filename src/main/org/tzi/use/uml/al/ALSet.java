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
import org.tzi.use.uml.ocl.expr.ExpAttrOp;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObjectState;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.util.Log;

/**
 * @author green
 */
public class ALSet extends ALAction {

    private ExpVariable fVariable;
    private Expression fValue;
    private ExpAttrOp fAttrOp;

    public ALSet(ExpVariable v, Expression value) {
        fVariable = v;
        fValue = value;
    }

    public ALSet(ExpAttrOp op, Expression value) {
        fAttrOp = op;
        fValue = value;
    }

    public void exec(EvalContext ctx) throws MSystemException {
        Value value = fValue.eval(ctx);
        assert value != null;
        if (fVariable != null) {
            ctx.varBindings().setValue(fVariable.getVarname(), value);
            Log.verbose("SET " + fVariable.getVarname() + " := " + value);
        } else if (fAttrOp != null) {
            ObjectValue ov = (ObjectValue)fAttrOp.objExp().eval(ctx);
            MObjectState os = ov.value().state(ctx.postState()); 
            os.setAttributeValue(fAttrOp.attr(), value);
            Log.verbose("SET " + ov.value() + "." + fAttrOp.attr().name() + " := " + value);
        }
    }
    
    
    public String toString() {
        if (fVariable != null) {
            return "set " + fVariable.getVarname() + " := " + fValue.toString();
        } else {
            return "set " + fAttrOp.toString() + " := " + fValue.toString();
        }
    }

    public void processWithVisitor(MMVisitor v) {
        v.visitALSet(this);
    }
}
