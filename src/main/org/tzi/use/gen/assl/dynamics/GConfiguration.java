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

import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.ocl.value.VarBindings;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.Evaluator;

import java.util.Random;

public class GConfiguration {
    private MSystemState fSystemState;
    private VarBindings fVarBindings;
    private Evaluator fEvaluator;
    private Random fRandom;
    private boolean isCheckingStructure;
    private boolean useTryCuts;
    private boolean useMinCombinations;
    
    public GConfiguration( MSystemState state,
                           VarBindings varBindings,
                           long randomNr,
                           boolean isCheckingStructure,
                           boolean useTryCuts,
                           boolean useMinCombinations) {
        fSystemState = state;
        fVarBindings = varBindings;
        fEvaluator = new Evaluator();
        fRandom = new Random( randomNr );
        this.isCheckingStructure = isCheckingStructure;
        this.useTryCuts = useTryCuts;
        this.useMinCombinations = useMinCombinations;
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

    public boolean isCheckingStructure() {
    	return isCheckingStructure;
    }
    
    public boolean usesTryCuts() {
    	return useTryCuts;
    }
    
    public Value evalExpression( Expression expr ) {
        return fEvaluator.eval( expr,
                                fSystemState,
                                fVarBindings );
    }

	/**
	 * @return
	 */
	public boolean useMinCombinations() {
		return useMinCombinations;
	}
}
