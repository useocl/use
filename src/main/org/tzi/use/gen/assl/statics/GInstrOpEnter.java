package org.tzi.use.gen.assl.statics;

import java.util.List;

public class GInstrOpEnter extends GInstruction {

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
}
