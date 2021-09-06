package org.tzi.use.parser.generator;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.gen.assl.statics.GInstrASSLCall;
import org.tzi.use.gen.assl.statics.GInstruction;
import org.tzi.use.gen.assl.statics.GOCLExpression;
import org.tzi.use.gen.assl.statics.GProcedure;
import org.tzi.use.gen.tool.GSignature;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.ocl.type.Type;

public class ASTGAsslCall extends ASTGInstruction {

	Token fName;
	
    List<ASTGocl> fParameter;
    
    public ASTGAsslCall(Token name) {
        fName = name;
        fParameter = new ArrayList<ASTGocl>();
    }
    
    public void addParameter( ASTGocl parameter ) {
        fParameter.add(parameter);
    }
    
	@Override
	public GInstruction gen(Context ctx) throws SemanticException {
		List<GOCLExpression> params = new ArrayList<GOCLExpression>();
		List<Type> paramTypes = new ArrayList<Type>();
		
        for (ASTGocl param : fParameter) {
        	GOCLExpression instr = (GOCLExpression)param.gen(ctx);
            params.add( instr );
            paramTypes.add(instr.type());
        }
        
        List<GProcedure> procedures = ctx.getProcedures();
        GSignature sig = new GSignature(fName.getText(), paramTypes);
        GProcedure toCall = sig.findMatching(procedures);
        
        if (toCall == null) {
        	ctx.reportError(fName, "Unknown procedure " + sig.toString());
        	return null;
        }
        
        GInstruction instr = new GInstrASSLCall(toCall, params);
        return instr;
    }

}
