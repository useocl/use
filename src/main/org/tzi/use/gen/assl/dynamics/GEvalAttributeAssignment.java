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

import java.io.PrintWriter;

import org.tzi.use.gen.assl.statics.GAttributeAssignment;
import org.tzi.use.gen.assl.statics.GValueInstruction;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.uml.sys.soil.MAttributeAssignmentStatement;
import org.tzi.use.uml.sys.soil.MStatement;

public class GEvalAttributeAssignment extends GEvalInstruction
    implements IGCaller {
    private GAttributeAssignment fInstr;
    private IGCaller fCaller;
    private String fObjectName;

    public GEvalAttributeAssignment(GAttributeAssignment instr) {
        fInstr = instr;
    }

    public void eval(GConfiguration conf,
                     IGCaller caller,
                     IGCollector collector) throws GEvaluationException {

		if (collector.doDetailPrinting())
			collector.detailPrintWriter().println(
					new StringBuilder("evaluating `").append(fInstr)
							.append("'").toString());
        
		fCaller = caller;
        fObjectName = null;
        
        fInstr.targetObjectInstr().createEvalInstr().eval( conf, this, collector );
    }

    public void feedback(
    		GConfiguration conf, 
    		Value value, 
    		IGCollector collector ) throws GEvaluationException {
        
    	if (fObjectName == null) {
    		if (value.isUndefined()) {   			
    			GValueInstruction culprit = fInstr.targetObjectInstr();
    			collector.invalid(buildCantExecuteMessage(fInstr, culprit));
    		} else {
    			fObjectName = ((ObjectValue)value).value().name();
                GEvalInstruction instruction = fInstr.sourceInstr().createEvalInstr();
                instruction.eval(conf, this, collector);
    		}
    		
    		return;
    	}
    	
    	MSystemState state = conf.systemState();
    	MSystem system = state.system();
    	
    	boolean doBasicOutput = collector.doBasicPrinting() || collector.doDetailPrinting();
    	PrintWriter basicOutput = collector.basicPrintWriter();
    	
    	MObject object = state.objectByName(fObjectName);
    	
    	Expression objectExpression = 
    		new ExpressionWithValue(object.value());
    		//new ExpVariable(fObjectName, object.type());
    	MAttribute attribute = fInstr.targetAttribute();
    	Expression valueExpression = new ExpressionWithValue(value);
    	
    	MStatement statement = 
    		new MAttributeAssignmentStatement(
    				objectExpression, 
    				attribute, 
    				valueExpression);
    	
    	MStatement inverseStatement;
    	
    	if (doBasicOutput)
    		basicOutput.println(statement.getShellCommand());
    	
    	try {
    		StatementEvaluationResult evaluationResult = 
    			system.execute(statement, true, false, false);
    		inverseStatement = evaluationResult.getInverseStatement();
    		
		} catch (MSystemException e) {
			throw new GEvaluationException(e);
		}
		
		fCaller.feedback(conf, null, collector);
		if (collector.expectSubsequentReporting()) {
			collector.subsequentlyPrependStatement(statement);
		}
        
		if (doBasicOutput)
			basicOutput.println("undo: " + statement.getShellCommand());
		
		try {
			system.execute(inverseStatement, true, false, false);
		} catch (MSystemException e) {
			throw new GEvaluationException(e);
		}
		
		system.getUniqueNameGenerator().popState();
		system.getUniqueNameGenerator().popState();
    }

    public String toString() {
        return "GEvalAttributeAssignment";
    }
}
