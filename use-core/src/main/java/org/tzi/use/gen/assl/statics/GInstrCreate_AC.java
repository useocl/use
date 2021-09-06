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

import java.util.List;

import org.tzi.use.gen.assl.dynamics.GEvalInstrCreate_AC;
import org.tzi.use.gen.assl.dynamics.GEvalInstruction;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.util.StringUtil;

/**
 * Instruction for creating association class instances
 * @see org.tzi.use.gen.assl.statics
 * @author  Lars Hamann
 */
public class GInstrCreate_AC implements GValueInstruction {

    // Create( AssociationClass C, Exp... ): C

    private MAssociationClass associationClass;
    
    private List<GValueInstruction> linkedObjects;
    
    public GInstrCreate_AC( MAssociationClass cls, List<GValueInstruction> linkedObjects ) {
        this.associationClass = cls;
        this.linkedObjects = linkedObjects;
    }

    public MAssociationClass getAssociationClass() {
        return associationClass;
    }

    public List<GValueInstruction> getLinkedObjects() {
        return linkedObjects;
    }
    
    @Override
    public Type type() {
        return associationClass;
    }
    
    public String toString() {
        StringBuilder res = new StringBuilder("Create(");
        res.append(associationClass).append(", [");
        StringUtil.fmtSeq(res, this.linkedObjects, "], [");
        res.append("])");
        return res.toString();
    }
    
    public void processWithVisitor(InstructionVisitor v) {
    	v.visitInstrCreate_AC(this);
    }

	@Override
	public GEvalInstruction createEvalInstr() {
		return new GEvalInstrCreate_AC(this);
	}
}
