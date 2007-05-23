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

import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.ocl.expr.ExpAttrOp;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.StringValue;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MObjectState;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.util.Log;

/**
 * @author green
 */
public class ALSetCreate extends ALAction {

    private ExpVariable fVariable;
    private ExpAttrOp fAttrOp;
    private MClass fCls;
    private Expression fNameExpr;

    public ALSetCreate(ExpVariable v, MClass cls, Expression nameExpr) {
        fVariable = v;
        fCls = cls;
        fNameExpr = nameExpr;
    }

    public ALSetCreate(ExpAttrOp op, MClass cls, Expression nameExpr) {
        fAttrOp = op;
        fCls = cls;
        fNameExpr = nameExpr;
    }

    public void exec(EvalContext ctx) throws MSystemException {
        VarBindings varBindings = ctx.postState().system().varBindings();

        String objname=null;
        if (fNameExpr!=null) {
            try {
                objname=((StringValue)fNameExpr.eval(ctx)).value();
            } catch (ClassCastException e) {
                // ignored
            }
            if (varBindings.getValue(objname)!=null) {
                // already in use
                objname=null;
            }
        }

        if (objname==null)
            objname = ctx.postState().system().uniqueObjectNameForClass(fCls.name());

        MObject obj = ctx.postState().createObject(fCls, objname);
        Log.verbose("CREATE " + objname + " : " + fCls.name());
        ObjectValue val = new ObjectValue(obj.type(), obj);
        varBindings.push(objname, new ObjectValue(obj.type(), obj));
        if (fVariable != null) {
            ctx.varBindings().setValue(fVariable.getVarname(), val);
        } else if (fAttrOp != null) {
            ObjectValue ov = (ObjectValue)fAttrOp.objExp().eval(ctx);
            MObjectState os = ov.value().state(ctx.postState()); 
            os.setAttributeValue(fAttrOp.attr(), val);
        }
    }

    public String toString() {
        if (fVariable != null) {
            return "create " + fVariable.getVarname() + " := new " + fCls.name();
        } else {
            return "create " + fAttrOp.toString() + " := new " + fCls.name();
        }
    }

    public void processWithVisitor(MMVisitor v) {
        v.visitALSetCreate(this);
    }

}
