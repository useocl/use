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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.tzi.use.gen.assl.statics.GInstrDelete_Assoc_Linkends;
import org.tzi.use.gen.assl.statics.GValueInstruction;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.ocl.value.ObjectValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.uml.sys.soil.MLinkDeletionStatement;
import org.tzi.use.uml.sys.soil.MRValue;
import org.tzi.use.uml.sys.soil.MRValueExpression;
import org.tzi.use.uml.sys.soil.MStatement;

public class GEvalInstrDelete_Assoc_Linkends extends GEvalInstruction
    implements IGCaller {
    private GInstrDelete_Assoc_Linkends fInstr;
    private IGCaller fCaller;
    private ListIterator<GValueInstruction> fIterator;
    private List<String> fObjectNames;

    public GEvalInstrDelete_Assoc_Linkends(GInstrDelete_Assoc_Linkends instr ) {
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
        fIterator = fInstr.linkEnds().listIterator();
        fObjectNames = new ArrayList<String>();
    
        // fIterator has a next element, because an association has at least
        // two linkends.
        fIterator.next().createEvalInstr().eval(conf,this,collector);
        fIterator.previous();
    }

    public void feedback(GConfiguration conf,
                         Value value,
                         IGCollector collector ) throws GEvaluationException {
        if (value.isUndefined()) {
            collector.invalid( buildCantExecuteMessage(fInstr,
                                                       fInstr.linkEnds().get(fIterator.previousIndex())) );
            return;
        }

        fObjectNames.add( ((ObjectValue) value).value().name() );
        if (fIterator.hasNext()) {
            fIterator.next().createEvalInstr().eval(conf,this,collector);
            fIterator.previous();
        }
        else
            // every parameter is evaluated, so fObjectNames is complete now
            createLink(conf, collector);
    }
    
    private void createLink(
    		GConfiguration conf, 
    		IGCollector collector) throws GEvaluationException {
        
        MSystemState state = conf.systemState();
        MSystem system = state.system();
        PrintWriter basicOutput = collector.basicPrintWriter();
        
        MAssociation association = fInstr.association();
        List<MRValue> participants = 
        	new ArrayList<MRValue>(fObjectNames.size());
        
        for (String objectName : fObjectNames) {
        	MObject object = state.objectByName(objectName);
        	participants.add(
        			new MRValueExpression(object));      			
        }
        
        //FIXME: Qualifier in Generator
        MStatement statement = 
        	new MLinkDeletionStatement(
        			association, 
        			participants, Collections.<List<MRValue>>emptyList());
        
        MStatement inverseStatement;

        basicOutput.println(statement.getShellCommand());
        try {
        	StatementEvaluationResult evaluationResult = 
        		system.execute(statement, true, false, false);
        	
        	inverseStatement = evaluationResult.getInverseStatement();
		} catch (MSystemException e) {
			collector.invalid(e);
			return;
		}
				
		fCaller.feedback(conf, null, collector);
        if (collector.expectSubsequentReporting()) {
        	collector.subsequentlyPrependStatement(statement);
        }
         
        basicOutput.println("undo: " + statement.getShellCommand());
        try {
        	system.execute(inverseStatement, true, false, false);
		} catch (MSystemException e) {
			collector.invalid(e);
		}
    }

    public String toString() {
        return "GEvalInstrDelete_Assoc_Linkends";
    }
}
