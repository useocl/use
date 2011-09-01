package org.tzi.use.gen.assl.statics;

public class GInstrOpExit extends GInstruction {

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "OpExit";
	}

	public void processWithVisitor(InstructionVisitor v) {
    	v.visitInstrOpExit(this);
    }
}
