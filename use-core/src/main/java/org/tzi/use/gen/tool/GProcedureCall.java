/*
 * This is source code of the Snapshot Generator, an extension for USE
 * to generate (valid) system states of UML models.
 * Copyright (C) 2001 Joern Bohling, University of Bremen
 *
 * About USE:
 *   USE - UML based specification environment
 *   Copyright (C) 1999,2000,2001 Mark Richters, University of Bremen
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


package org.tzi.use.gen.tool;

import java.util.ArrayList;
import java.util.List;

import org.tzi.use.gen.assl.statics.GProcedure;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MSystemState;



/**
 * Represents the call of a procedure.
 * @author  Joern Bohling
 */
public class GProcedureCall {
    private GProcedure fProcedure;
    private List<Expression> fParameter;    
    private GSignature signature = null;
    
    public GProcedureCall (GProcedure procedure, List<Expression> params) {
        fProcedure = procedure;
        fParameter = params;
    }

    private List<Type> getParameterTypes() {       
        ArrayList<Type> types = new ArrayList<Type>();
        for (Expression exp : fParameter) {
            types.add( exp.type() );
        }
        
        return types;
    }

    public GSignature getSignature() {
        if (signature == null)
        {
        	signature = new GSignature(fProcedure.name(), getParameterTypes());
        }
        
        return signature;
    }

    public String signatureString() {
        return getSignature().toString();
    }

    public GProcedure getProcedure() {
    	return fProcedure;
    }
    
    public List<Value> evaluateParams(MSystemState state) {
        List<Value> values = new ArrayList<Value>();
        Evaluator evaluator = new Evaluator();

        for (Expression exp : fParameter) {
            values.add( evaluator.eval( exp,
                                        state,
                                        state.system().varBindings()) );
        }
        
        return values;
    }
}
