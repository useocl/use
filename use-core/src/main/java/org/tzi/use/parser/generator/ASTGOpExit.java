package org.tzi.use.parser.generator;

import org.tzi.use.gen.assl.statics.GInstrOpExit;
import org.tzi.use.gen.assl.statics.GInstruction;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;

public class ASTGOpExit extends ASTGInstruction {

	@Override
	public GInstruction gen(Context ctx) throws SemanticException {
		GInstruction instr = new GInstrOpExit();
		return instr;
	}

}
