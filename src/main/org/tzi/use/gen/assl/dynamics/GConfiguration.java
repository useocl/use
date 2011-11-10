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

/**
 * March 22th 2001 
 * @author  Joern Bohling
 */

package org.tzi.use.gen.assl.dynamics;

import java.util.Random;

import org.tzi.use.gen.tool.GGeneratorArguments;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.sys.MSystemState;

public class GConfiguration {
    private final MSystemState fSystemState;
    private final VarBindings fVarBindings;
    private final Evaluator fEvaluator;
    private final GGeneratorArguments fConfig;
    private final Random fRandom;
    
    public GConfiguration( MSystemState state,
                           VarBindings varBindings,
                           GGeneratorArguments cfg) {
        fSystemState = state;
        fVarBindings = varBindings;
        fEvaluator = new Evaluator();
        fConfig = cfg;
        fRandom = new Random( fConfig.getRandomNr().longValue() );
    }

    public MSystemState systemState() {
        return fSystemState;
    }
    
    public VarBindings varBindings() {
        return fVarBindings;
    }

    public Random random() {
        return fRandom;
    }

    public GGeneratorArguments getArguments() {
    	return fConfig;
    }
    
    public Value evalExpression( Expression expr ) {
        return fEvaluator.eval( expr,
                                fSystemState,
                                fVarBindings );
    }
}
