package org.tzi.use.gen.assl.statics;

import java.util.List;

public class GInstrASSLCall extends GInstruction {

	String procname;
	List fParam;

	public GInstrASSLCall(String id, List param) {
		procname = id;
		fParam=param;
	}
	
	public String procName() {
		return procname;
	}
	
	public List param() {
		return fParam;
	}
	
	@Override
	public String toString() {
		return "ASSLCall "+ procname+ "("+ fParam + ")";
	}

	public void processWithVisitor(InstructionVisitor v) {
    	v.visitInstrASSLCall(this);
    }
}
