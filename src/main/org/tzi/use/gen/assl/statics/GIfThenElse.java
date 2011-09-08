package org.tzi.use.gen.assl.statics;

import org.tzi.use.gen.assl.dynamics.GEvalIfThenElse;
import org.tzi.use.gen.assl.dynamics.GEvalInstruction;

/**
 * @see org.tzi.use.gen.assl.statics
 * @author  Hanna Bauerdick
 */
public class GIfThenElse implements GInstruction {
    private GInstructionList fThenInstructionList;
    private GInstructionList fElseInstructionList;
    private GValueInstruction fConditionInstr;

    public GIfThenElse() {
        fThenInstructionList = new GInstructionList();
        fElseInstructionList = new GInstructionList();
        fConditionInstr = null;
    }

    public void setConditionInstr( GValueInstruction conditionInstr ) {
        fConditionInstr = conditionInstr;
    }  

    public void addThenInstruction( GInstruction instr ) {
        fThenInstructionList.add( instr );
    }  

    public void addElseInstruction( GInstruction instr ) {
        fElseInstructionList.add( instr );
    }  

    public GValueInstruction conditionInstr() {
        return fConditionInstr;
    }

    public GInstructionList thenInstructionList() {
        return fThenInstructionList;
    }   

    public GInstructionList elseInstructionList() {
        return fElseInstructionList;
    }   

    public String toString() {
        return new StringBuilder("if ").append(fConditionInstr).append(" then begin ... end else begin ... end").toString();
    }
    
    public void processWithVisitor(InstructionVisitor v) {
    	v.visitIfThenElse(this);
    }

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.GInstruction#createEvalInstr()
	 */
	@Override
	public GEvalInstruction createEvalInstr() {
		return new GEvalIfThenElse( this );
	}
}
