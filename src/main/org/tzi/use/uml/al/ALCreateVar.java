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
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.UndefinedValue;

/**
 * Declares a variable in the current scope.
 * @author green
 */
public class ALCreateVar extends ALAction {
    
    private String fVar;
    private Type fType;
    
    public ALCreateVar(String var, Type type) {
        fVar = var;
        fType = type;
    }
    
    public void exec(EvalContext ctx) {
        ctx.pushVarBinding(fVar,new UndefinedValue(fType));
    }
    
    public String toString() {
        return "declare " + fVar + " : " + fType;
    }

    public void processWithVisitor(MMVisitor v) {
        v.visitALCreateVar(this);
    }

}
