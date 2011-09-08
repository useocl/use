package org.tzi.use.gen.assl.statics;

import java.util.List;

import org.tzi.use.gen.assl.dynamics.GEvalASSLCall;
import org.tzi.use.gen.assl.dynamics.GEvalInstruction;

public class GInstrASSLCall implements GInstruction {

	String procname;
	List<GValueInstruction> fParam;

	public GInstrASSLCall(String id, List<GValueInstruction> param) {
		procname = id;
		fParam=param;
	}
	
	public String procName() {
		return procname;
	}
	
	public List<GValueInstruction> param() {
		return fParam;
	}
	
	@Override
	public String toString() {
		return "ASSLCall "+ procname+ "("+ fParam + ")";
	}

	public void processWithVisitor(InstructionVisitor v) {
    	v.visitInstrASSLCall(this);
    }

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.GInstruction#createEvalInstr()
	 */
	@Override
	public GEvalInstruction createEvalInstr() {
		return new GEvalASSLCall(this);
	}
}
