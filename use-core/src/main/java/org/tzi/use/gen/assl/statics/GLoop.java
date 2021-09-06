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

import org.tzi.use.gen.assl.dynamics.GEvalInstruction;
import org.tzi.use.gen.assl.dynamics.GEvalLoop;
import org.tzi.use.uml.ocl.expr.VarDecl;

/**
 * @see org.tzi.use.gen.assl.statics
 * @author  Joern Bohling
 */
public class GLoop implements GInstruction {
    private GInstructionList fInstructionList;
    private GValueInstruction fSequenceInstr;
    private VarDecl fDecl;

    public GLoop() {
        fInstructionList = new GInstructionList();
        fSequenceInstr = null;
        fDecl = null;
    }

    public void setDecl( VarDecl decl ) {
        fDecl = decl;
    }
    
    public void setSequenceInstr( GValueInstruction sequenceInstr ) {
        fSequenceInstr = sequenceInstr;
    }  

    public void addInstruction( GInstruction instr ) {
        fInstructionList.add( instr );
    }  

    public VarDecl decl() {
        return fDecl;
    }
    
    public GValueInstruction sequenceInstr() {
        return fSequenceInstr;
    }

    public GInstructionList instructionList() {
        return fInstructionList;
    }   

    public String toString() {
        return new StringBuilder("for ").append(fDecl).append(" in ").append(fSequenceInstr).append(" ...").toString();
    }
    
    public void processWithVisitor(InstructionVisitor v) {
    	v.visitLoop(this);
    }

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.GInstruction#createEvalInstr()
	 */
	@Override
	public GEvalInstruction createEvalInstr() {
		return new GEvalLoop( this );
	}
}
