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

import static org.tzi.use.util.StringUtil.inQuotes;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.tzi.use.gen.assl.statics.GInstrCreateN_C_Integer;
import org.tzi.use.gen.assl.statics.GValueInstruction;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.SequenceValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.uml.sys.soil.MNewObjectStatement;
import org.tzi.use.uml.sys.soil.MSequenceStatement;
import org.tzi.use.uml.sys.soil.MStatement;

public class GEvalInstrCreateN_C_Integer extends GEvalInstruction
    implements IGCaller {
    private GInstrCreateN_C_Integer fInstr;
    private IGCaller fCaller;

    public GEvalInstrCreateN_C_Integer(GInstrCreateN_C_Integer instr ) {
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
        fInstr.integerInstr().createEvalInstr().eval(conf,this,collector);
    }

    public void feedback(
    		GConfiguration conf, 
    		Value value, 
    		IGCollector collector ) throws GEvaluationException {
    	
    	if (value.isUndefined()) {
    		GValueInstruction culprit = fInstr.integerInstr();
    		collector.invalid(buildCantExecuteMessage(fInstr, culprit));
    		
    		return;
        }
    	
        int count = ((IntegerValue)value).value();
        
        if (count < 0) {
        	collector.invalid(
        			"Can't execute " + 
        			inQuotes(fInstr) +
        			", because " + 
        			inQuotes(fInstr.integerInstr()) +
        			" has been evaluated to a negative integer.");
        	
        	return;
        }
        
        MSystemState state = conf.systemState();
        MSystem system = state.system();
        PrintWriter basicOutput = collector.basicPrintWriter();
        PrintWriter detailOutput = collector.detailPrintWriter();
              
        MClass objectClass = fInstr.cls();
        
        MSequenceStatement statement = new MSequenceStatement();
        List<String> objectNames = new ArrayList<String>();
        for (int i = 0; i < count; ++i) {
        	String objectName = 
        		state.uniqueObjectNameForClass(objectClass.name());
        
        	objectNames.add(objectName);
        	
        	statement.prependStatement(
        			new MNewObjectStatement(
        					objectClass, 
        					objectName));
        }
        MStatement inverseStatement;
        
        if (collector.doBasicPrinting())
        	basicOutput.println(statement.getShellCommand());
        
        try {
        	StatementEvaluationResult evaluationResult = 
        		system.execute(statement, true, false, false);
        	
        	inverseStatement = evaluationResult.getInverseStatement();
        	
		} catch (MSystemException e) {
			throw new GEvaluationException(e);
		}
        
        List<Value> objectValues = new ArrayList<Value>();
        for (String objectName : objectNames) {
        	MObject object = state.objectByName(objectName);
        	objectValues.add(new ObjectValue(objectClass, object));
        }
        
        Value objects = new SequenceValue(objectClass, objectValues);
        
        if (collector.doDetailPrinting())
        	detailOutput.println(inQuotes(fInstr) + " == " + objects);
        
        fCaller.feedback(conf, objects, collector);
        if (collector.expectSubsequentReporting()) {
        	for (MStatement s : statement.getStatements()) {
        		if (!s.isEmptyStatement()) {
        			collector.subsequentlyPrependStatement(s);
        		}
        	}
        }
            
        if (collector.doBasicPrinting())
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
        return "GEvalInstrCreateN_C_Integer";
    }
}
