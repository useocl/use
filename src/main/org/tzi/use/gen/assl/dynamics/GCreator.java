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

import org.tzi.use.gen.assl.statics.*;

class GCreator {

    // utility class
    private GCreator() {}
    

    public static GEvalInstructionList createFor(GInstructionList instrlist) {
        return new GEvalInstructionList(instrlist);
    }

    public static GEvalInstruction createFor(GInstruction instr)
        throws GEvaluationException {
        if (instr instanceof GInstrTry_Seq)
            return new GEvalInstrTry_Seq( (GInstrTry_Seq) instr );
        else if (instr instanceof GOCLExpression)
            return new GEvalOCLExpression( (GOCLExpression) instr );
        else if (instr instanceof GVariableAssignment)
            return new GEvalVariableAssignment( (GVariableAssignment) instr );
        else if (instr instanceof GLoop)
            return new GEvalLoop( (GLoop) instr );
        else if (instr instanceof GIfThenElse)
            return new GEvalIfThenElse( (GIfThenElse) instr );
        else if (instr instanceof GInstrCreate_C)
            return new GEvalInstrCreate_C( (GInstrCreate_C) instr );
        else if (instr instanceof GInstrCreateN_C_Integer)
            return new GEvalInstrCreateN_C_Integer( (GInstrCreateN_C_Integer) instr );
        else if (instr instanceof GInstrInsert_Assoc_Linkends)
            return new GEvalInstrInsert_Assoc_Linkends( (GInstrInsert_Assoc_Linkends) instr );
        else if (instr instanceof GInstrDelete_Assoc_Linkends)
            return new GEvalInstrDelete_Assoc_Linkends( (GInstrDelete_Assoc_Linkends) instr );
        else if (instr instanceof GInstrAny_Seq)
            return new GEvalInstrAny_Seq( (GInstrAny_Seq) instr );
        else if (instr instanceof GInstrSub_Seq)
            return new GEvalInstrSub_Seq( (GInstrSub_Seq) instr );
        else if (instr instanceof GInstrSub_Seq_Integer)
            return new GEvalInstrSub_Seq_Integer( (GInstrSub_Seq_Integer) instr );
        else if (instr instanceof GAttributeAssignment)
            return new GEvalAttributeAssignment( (GAttributeAssignment) instr );
        else if (instr instanceof GInstrTry_Assoc_LinkendSeqs)
            return new GEvalInstrTry_Assoc_LinkendSeqs( (GInstrTry_Assoc_LinkendSeqs) instr );
        else if (instr instanceof GInstrDelete_Object)
            return new GEvalInstrDelete_Object( (GInstrDelete_Object) instr );
        else if (instr == null)
            return null;
        else
            throw new GEvaluationException("The execution of the instruction `"
                                           + instr + "' is not implemented.");
    }
}
