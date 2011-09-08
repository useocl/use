package org.tzi.use.gen.assl.dynamics;

import java.io.PrintWriter;
import java.util.Iterator;

import org.tzi.use.gen.assl.statics.GInstrASSLCall;
import org.tzi.use.gen.assl.statics.GOCLExpression;
import org.tzi.use.gen.assl.statics.GProcedure;
import org.tzi.use.gen.assl.statics.GValueInstruction;
import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.NullPrintWriter;

public class GEvalASSLCall extends GEvalInstruction implements IGCaller {

	GInstrASSLCall fInstr;
	IGCaller fCaller;
	
	public GEvalASSLCall(GInstrASSLCall instr) {
		fInstr = instr;
	}

	@Override
	public void eval(GConfiguration conf, IGCaller caller, IGCollector collector)
			throws GEvaluationException {
		
		fCaller = caller;
		// create call-string for calling the procedure
		Iterator<GValueInstruction> paramIter = fInstr.param().iterator();
		String help="";
		String paramStr = "";
		PrintWriter out = NullPrintWriter.getInstance();
		
		// adding parameter to call string
		while (paramIter.hasNext()){
			GOCLExpression expr = (GOCLExpression) paramIter.next();
			help = expr.expression().toString();
		        
			Expression expr2 = OCLCompiler.compileExpression(
					conf.systemState().system().model(), help, "Error", out, conf.varBindings());
				
            Value val = new Evaluator().eval(expr2, conf.systemState(), conf.varBindings()); 
            if (val.isObject())
            	paramStr += val.toString().substring(1) +", ";
            else
            	paramStr += val.toString() +", ";
		}
		
		if (!paramStr.isEmpty() && paramStr.charAt(paramStr.length()-2)==','){
			paramStr=paramStr.substring(0, paramStr.length()-2);
		}
		// create call-string from procedure name and parameter-string
		String callStr = fInstr.procName()+"("+paramStr+")";

		// prepare procedure call with the calculated callStr
		GProcedure proc = conf.systemState().system().generator().getProcedure(callStr);
		// call procedure
		proc.instructionList().createEvalInstr().eval( conf, this, collector ); 

	}

	public String toString() {
		return "GEvalASSLCall";
	}

	public void feedback(GConfiguration conf, Value value,
			IGCollector collector) throws GEvaluationException {
		
		fCaller.feedback(conf, null, collector);
		
	}

}
