package org.tzi.use.parser.generator;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.antlr.runtime.Token;
import org.tzi.use.gen.assl.statics.GInstrOpEnter;
import org.tzi.use.gen.assl.statics.GInstruction;
import org.tzi.use.gen.assl.statics.GValueInstruction;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;

public class ASTGOpEnter extends ASTGInstruction {

	ASTGocl fObjname;
	String fOpname;
	List<ASTGocl> fParameter;
	
	public ASTGOpEnter (ASTGocl oid, Token opname) {
		fObjname = oid;
		fOpname = opname.getText();
		fParameter = new ArrayList<ASTGocl>();
	}
	
	public void addParameter(ASTGocl param) {
		fParameter.add(param);
	}
	
	@Override
	public GInstruction gen(Context ctx) throws SemanticException {

        GValueInstruction oid = (GValueInstruction) fObjname.gen(ctx);
		
        ArrayList<GValueInstruction> params = new ArrayList<GValueInstruction>();
        
        Iterator<ASTGocl> it = fParameter.iterator();
        while (it.hasNext() ) {
        	ASTGocl param = it.next();
            GValueInstruction instr = (GValueInstruction)param.gen(ctx);
            params.add( instr );
        }
        GInstruction instr = new GInstrOpEnter(oid, fOpname, params);
        
		return instr;
	}

}
