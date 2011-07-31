package org.tzi.use.parser.generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.gen.assl.statics.GInstrASSLCall;
import org.tzi.use.gen.assl.statics.GInstruction;
import org.tzi.use.gen.assl.statics.GInstructionCreator;
import org.tzi.use.gen.assl.statics.GValueInstruction;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.util.StringUtil;

public class ASTGAsslCall extends ASTGInstruction {

	String fName;
    List fParameter;  // ASTGInstructionParameterInterface;
    
    public ASTGAsslCall(Token name) {
        fName = name.getText();
        fParameter = new ArrayList();
    }
    
    public void addParameter( Object parameter ) {
        fParameter.add(parameter);
    }
    
    
	@Override
	public GInstruction gen(Context ctx) throws SemanticException {

        List params = new ArrayList();
        List errParams = new ArrayList();
 
        Iterator it = fParameter.iterator();
        while (it.hasNext() ) {
            Object param = it.next();
            GValueInstruction instr =
            	(GValueInstruction) ((ASTGocl) param).gen(ctx);
            params.add( instr );
            errParams.add( instr.type().toString() );
        }
        
        GInstruction instr = new GInstrASSLCall(fName, params);

        
        return (GInstruction) instr;
    }

}
