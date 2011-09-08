package org.tzi.use.gen.assl.statics;

import java.util.List;

import org.tzi.use.gen.assl.dynamics.GEvalInstruction;
import org.tzi.use.gen.assl.dynamics.GEvalOpEnter;

public class GInstrOpEnter implements GInstruction {

	GValueInstruction fObjname;
	String fOpname;
	List<GValueInstruction> fParameter;
	
	public GInstrOpEnter(GValueInstruction oid, String opname, List<GValueInstruction> params) {
		fObjname = oid;
		fOpname = opname;
		fParameter = params;
	}

	public GValueInstruction objname() {
		return fObjname;
	}
	
	public String opname() {
		return fOpname;
	}
	
	public List<GValueInstruction> parameter() {
		return fParameter;
	}
	
	@Override
	public String toString() {
		return "OpEnter " + fObjname.toString() + " " + fOpname + "("
				+ fParameter + ")";
	}

	public void processWithVisitor(InstructionVisitor v) {
    	v.visitInstrOpEnter(this);
    }

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.GInstruction#createEvalInstr()
	 */
	@Override
	public GEvalInstruction createEvalInstr() {
		return new GEvalOpEnter(this);
	}
}
