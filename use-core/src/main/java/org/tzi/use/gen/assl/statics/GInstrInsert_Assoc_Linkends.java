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

import org.tzi.use.gen.assl.dynamics.GEvalInstrInsert_Assoc_Linkends;
import org.tzi.use.gen.assl.dynamics.GEvalInstruction;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.util.StringUtil;
import java.util.List;

/**
 * @see org.tzi.use.gen.assl.statics
 * @author  Joern Bohling
 */
public class GInstrInsert_Assoc_Linkends implements GInstruction {

    // NO RESULT VALUE -> not a subclass of GValueInstruction
    // Insert( n-ary-Association, end1, end2, ..., endn )
    // endx must be type of the xth association-end.

    private MAssociation fAssociation;
    private List<GValueInstruction> fLinkends;

    public GInstrInsert_Assoc_Linkends( MAssociation assoc, List<GValueInstruction> ends ) {
        fAssociation = assoc;
        fLinkends = ends;
    }

    public MAssociation association() {
        return fAssociation;
    }

    public List<GValueInstruction> linkEnds() {
        return fLinkends;
    }

    public String toString() {
        return new StringBuilder("Insert(").append(fAssociation).append(",").append(StringUtil.fmtSeq(fLinkends.iterator(), ",")).append(")").toString();
    }
    
    public void processWithVisitor(InstructionVisitor v) {
    	v.visitInstrInsert_Assoc_Linkends(this);
    }

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.GInstruction#createEvalInstr()
	 */
	@Override
	public GEvalInstruction createEvalInstr() {
		return new GEvalInstrInsert_Assoc_Linkends( this );
	}
}
