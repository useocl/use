package org.tzi.use.parser.generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.gen.assl.statics.GInstrASSLCall;
import org.tzi.use.gen.assl.statics.GInstruction;
import org.tzi.use.gen.assl.statics.GValueInstruction;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;

public class ASTGAsslCall extends ASTGInstruction {

	String fName;
    List<ASTGocl> fParameter;
    
    public ASTGAsslCall(Token name) {
        fName = name.getText();
        fParameter = new ArrayList<ASTGocl>();
    }
    
    public void addParameter( ASTGocl parameter ) {
        fParameter.add(parameter);
    }
    
    
	@Override
	public GInstruction gen(Context ctx) throws SemanticException {

        List<GValueInstruction> params = new ArrayList<GValueInstruction>();
 
        Iterator<ASTGocl> it = fParameter.iterator();
        while (it.hasNext() ) {
        	ASTGocl param = it.next();
            GValueInstruction instr = (GValueInstruction)param.gen(ctx);
            params.add( instr );
        }
        
        GInstruction instr = new GInstrASSLCall(fName, params);
        return instr;
    }

}
