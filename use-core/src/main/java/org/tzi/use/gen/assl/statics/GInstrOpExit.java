package org.tzi.use.gen.assl.statics;

import org.tzi.use.gen.assl.dynamics.GEvalInstruction;
import org.tzi.use.gen.assl.dynamics.GEvalOpExit;

public class GInstrOpExit implements GInstruction {

	@Override
	public String toString() {
		return "OpExit";
	}

	public void processWithVisitor(InstructionVisitor v) {
    	v.visitInstrOpExit(this);
    }

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.GInstruction#createEvalInstr()
	 */
	@Override
	public GEvalInstruction createEvalInstr() {
		return new GEvalOpExit(this);
	}
}
