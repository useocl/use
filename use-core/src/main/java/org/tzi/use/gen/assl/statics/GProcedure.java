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

package org.tzi.use.gen.assl.statics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tzi.use.analysis.coverage.BasicCoverageData;
import org.tzi.use.analysis.coverage.BasicExpressionCoverageCalulator;
import org.tzi.use.gen.assl.dynamics.IGCollector;
import org.tzi.use.gen.tool.GSignature;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.util.StringUtil;

/**
 * @see org.tzi.use.gen.assl.statics
 * @author  Joern Bohling
 */
public class GProcedure {
    private String fName;
    private List<VarDecl> fParameterDecls;
    private List<VarDecl> fLocalDecls;
    private GInstructionList fInstructionList;
    private GSignature signature = null;
    
    public GProcedure(String name) {
        fName = name;
        fParameterDecls = new ArrayList<VarDecl>();
        fLocalDecls = new ArrayList<VarDecl>();
        fInstructionList = new GInstructionList();
    }

    public String name() {
        return fName;
    }

    public void addParameterDecl( VarDecl parameterDecl ) {
        fParameterDecls.add(parameterDecl);
    }
    
    public void addLocalDecl( VarDecl localDecl ) {
        fLocalDecls.add(localDecl);
    }

    public void addInstruction( GInstruction instruction ) {
        fInstructionList.add(instruction);
    }

    public List<VarDecl> parameterDecls() {
        return fParameterDecls;
    }
    
    public List<VarDecl> localDecls() {
        return fLocalDecls;
    }
    
    public GInstructionList instructionList() {
        return fInstructionList;
    }

    private List<Type> getParameterTypes() {
        ArrayList<Type> types = new ArrayList<Type>();

        for (VarDecl decl : parameterDecls()) {
            types.add( decl.type() );
        }
        
        return types;
    }

    public GSignature getSignature() {
        if (signature == null) {
        	signature = new GSignature(fName, getParameterTypes());
        }
        
        return signature;
    }

    private String signatureString() {
        return getSignature().toString();
    }
    
    public String toString() {
        return signatureString();
    }

	/**
	 * @param fGModel
	 */
	public void calculateBarriers(IGCollector collector, MModel model) {
		
		// Calculate coverage of all invariants
		Map<MClassInvariant, BasicCoverageData> invCoverage = new HashMap<MClassInvariant, BasicCoverageData>();
		BasicExpressionCoverageCalulator invCalc = new BasicExpressionCoverageCalulator(true);
		
		for (MClassInvariant inv : model.classInvariants()) {
			if (inv.isActive()) {
				invCoverage.put(inv, invCalc.calcualteCoverage(inv.flaggedExpression()));
			}
		}
		
		BasicInstructionCoverageCalulator instrCalc = new BasicInstructionCoverageCalulator();
		List<GInstruction> instrList = fInstructionList.instructions();
		Set<MClassInvariant> blockedInvs = new HashSet<>();
		
		for (int index = 0; index < instrList.size(); ++index) {
			// Add user defined barrier to statistics
			if (instrList.get(index).getClass().equals(GInstrBarrier.class) ) {
				collector.addBarrier((GInstrBarrier)instrList.get(index));
			}
			
			BasicCoverageData instrCoverage = instrCalc
					.calcualteCoverage(instrList.subList(index,
							instrList.size()));
			blockedInvs.clear();
			
			for (Map.Entry<MClassInvariant, BasicCoverageData> invData : invCoverage.entrySet()) {
				MClassInvariant inv = invData.getKey();
				if (instrCoverage.disjoint(invData.getValue())) {
					GInstrCalculatedBarrier bInstr = new GInstrCalculatedBarrier(inv);
					blockedInvs.add(inv);
					collector.addBarrier(bInstr);
					instrList.add(index, bInstr);
					inv.setCheckedByBarrier(true);
					++index;
					
					if (collector.doBasicPrinting()) {
						collector.basicPrintWriter().println(
								"Added Barrier for invariant "
										+ StringUtil.inQuotes(inv)
										+ " before statement "
										+ StringUtil.inQuotes(fInstructionList.instructions().get(index)));
					}
				}
			}
			
			for (MClassInvariant inv : blockedInvs) {
				invCoverage.remove(inv);
			}
		}
	}
}



