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
	List fParameter;
	
	public ASTGOpEnter (ASTGocl oid, Token opname) {
		fObjname = oid;
		fOpname = opname.getText();
		fParameter = new ArrayList();
	}
	
	public void addParameter(Object param) {
		fParameter.add(param);
	}
	
	@Override
	public GInstruction gen(Context ctx) throws SemanticException {
		// TODO Auto-generated method stub


        GValueInstruction oid =
        	(GValueInstruction) ((ASTGocl) fObjname).gen(ctx);
		
        ArrayList params = new ArrayList();
        ArrayList errParams = new ArrayList();
        
        Iterator it = fParameter.iterator();
        while (it.hasNext() ) {
            Object param = it.next();
            GValueInstruction instr =
            	(GValueInstruction) ((ASTGocl) param).gen(ctx);
            params.add( instr );
            errParams.add( instr.type().toString() );
        }
        GInstruction instr = new GInstrOpEnter(oid, fOpname, params);
        
		return instr;
	}

}
