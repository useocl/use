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
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.util.Log;

/**
 * Creates a new object and assigns it to a variable. 
 * @author green
 */
public class ALCreateObject extends ALAction {

    private String fVar;
    private MClass fCls;
    
    public ALCreateObject(String var, MClass cls) {
        fVar = var;
        fCls = cls;
    }
    
    public void exec(EvalContext ctx) {
        try {
            MObject obj = ctx.postState().createObject(fCls,null);
            ObjectType type = TypeFactory.mkObjectType(fCls);
            ObjectValue val =  new ObjectValue(type, obj);
            Log.verbose("CREATE " + fCls.name());
            ctx.postState().system().varBindings().push(obj.name(),val);
            ctx.varBindings().setValue(fVar,val);
        } catch (MSystemException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        return "create " + fCls.name();
    }

    public void processWithVisitor(MMVisitor v) {
        v.visitALCreateObject(this);
    }

}
