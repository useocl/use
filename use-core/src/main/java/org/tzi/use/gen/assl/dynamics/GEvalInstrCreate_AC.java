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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.tzi.use.gen.assl.statics.GInstrCreate_AC;
import org.tzi.use.gen.assl.statics.GInstruction;
import org.tzi.use.gen.assl.statics.GValueInstruction;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.uml.sys.soil.MNewLinkObjectStatement;
import org.tzi.use.uml.sys.soil.MRValue;
import org.tzi.use.uml.sys.soil.MRValueExpression;
import org.tzi.use.uml.sys.soil.MStatement;

public class GEvalInstrCreate_AC extends GEvalInstruction implements IGCaller {
    private GInstrCreate_AC fInstr;
    private IGCaller fCaller;
    private List<MRValue> fObjectList;
    private ListIterator<GValueInstruction> fIterator;
    private GInstruction fLastEvaluatedInstruction;
    
    public GEvalInstrCreate_AC(GInstrCreate_AC instr ) {
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
        
        fIterator = fInstr.getLinkedObjects().listIterator();
        fObjectList = new LinkedList<MRValue>();
    
        // fIterator has a next element, because an association has at least
        // two link ends.
        fLastEvaluatedInstruction = fIterator.next();
        fLastEvaluatedInstruction.createEvalInstr().eval(conf, this, collector);
        fIterator.previous();
    }

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.dynamics.IGCaller#feedback(org.tzi.use.gen.assl.dynamics.GConfiguration, org.tzi.use.uml.ocl.value.Value, org.tzi.use.gen.assl.dynamics.IGCollector)
	 */
	@Override
	public void feedback(GConfiguration conf, Value value, IGCollector collector) throws GEvaluationException {
		
		if (value.isUndefined() || !value.isObject()) {
            collector.invalid( buildCantExecuteMessage(fInstr, (GValueInstruction)fLastEvaluatedInstruction));
            return;
        }

        ObjectValue objVal = (ObjectValue)value;
        fObjectList.add( new MRValueExpression(objVal.value()) );
    
        if (fIterator.hasNext()) {
            fLastEvaluatedInstruction = fIterator.next();
            fLastEvaluatedInstruction.createEvalInstr().eval(conf, this, collector);
            fIterator.previous();
        }
        else
            createLinkObject(conf, collector);
		
	}
	
	private void createLinkObject(GConfiguration conf, IGCollector collector) throws GEvaluationException {
		MSystemState state = conf.systemState();
    	MSystem system = state.system();

    	PrintWriter basicOutput = collector.basicPrintWriter();
    	PrintWriter detailOutput = collector.detailPrintWriter();
    			
    	MAssociationClass objectClass = fInstr.getAssociationClass();
    	String objectName = state.uniqueObjectNameForClass(objectClass.name());
    	
    	MStatement statement = new MNewLinkObjectStatement(objectClass, fObjectList, Collections.<List<MRValue>>emptyList(), objectName);
    	
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
		
		ObjectValue objectValue = new ObjectValue(objectClass, state.objectByName(objectName));
		
		if (collector.doDetailPrinting())
			detailOutput.println(inQuotes(fInstr) + " == " + objectValue);
		
		fCaller.feedback(conf, objectValue, collector);
            
		if (collector.expectSubsequentReporting()) {
			collector.subsequentlyPrependStatement(statement);
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
}
