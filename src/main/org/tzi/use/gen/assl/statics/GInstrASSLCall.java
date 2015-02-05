package org.tzi.use.gen.assl.statics;

import java.util.List;

import org.tzi.use.gen.assl.dynamics.GEvalASSLCall;
import org.tzi.use.gen.assl.dynamics.GEvalInstruction;
import org.tzi.use.util.StringUtil;

public class GInstrASSLCall implements GInstruction {

	GProcedure fProcedure;
	
	List<GOCLExpression> fArguments;

	public GInstrASSLCall(GProcedure procedure, List<GOCLExpression> param) {
		fProcedure = procedure;
		fArguments = param;
	}
	
	public GProcedure getProcedure() {
		return fProcedure;
	}
	
	public List<GOCLExpression> getArguments() {
		return fArguments;
	}
	
	@Override
	public String toString() {
		return "ASSLCall "+ fProcedure.name() + "("+ StringUtil.fmtSeq(fArguments, ",") + ")";
	}

	public void processWithVisitor(InstructionVisitor v) {
    	v.visitInstrASSLCall(this);
    }

	@Override
	public GEvalInstruction createEvalInstr() {
		return new GEvalASSLCall(this);
	}
}
