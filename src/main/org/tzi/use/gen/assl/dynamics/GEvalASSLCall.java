package org.tzi.use.gen.assl.dynamics;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import org.tzi.use.gen.assl.statics.GInstrASSLCall;
import org.tzi.use.gen.assl.statics.GOCLExpression;
import org.tzi.use.gen.assl.statics.GProcedure;
import org.tzi.use.gen.assl.statics.GValueInstruction;
import org.tzi.use.gen.tool.GProcedureCall;
import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.util.NullPrintWriter;

public class GEvalASSLCall extends GEvalInstruction implements IGCaller {

	GInstrASSLCall fInstr;
	IGCaller fCaller;
	int varNumber;
	
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
		
		varNumber = fInstr.param().size();
		// adding parameter to callString and varBindings stack
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
		// create callString from procedure name and parameterString
		String callStr = fInstr.procName()+"("+paramStr+")";

		//conf.systemState().system().generator().startProcedure(callStr);
		// prepare procedure call with the calculated callStr
		GProcedure proc = conf.systemState().system().generator().getProcedure(callStr);
		GProcedureCall call = conf.systemState().system().generator().getCall(callStr);
		
		// add parameter variables and local variables to varBindings
		// first: add parameter-variable declarations to varBindings stack
        Iterator<VarDecl> declIt;
        declIt = proc.parameterDecls().iterator();
        List<Value> paramValues = call.evaluateParams(conf.systemState());
        varNumber = paramValues.size(); //new
        Iterator<Value> valuesIt = paramValues.iterator();
        while (declIt.hasNext()) {
            String varName = ((VarDecl) declIt.next()).name();
            Value value = valuesIt.next();
            //varBindings.push(varName, value);
            conf.varBindings().push(varName, value); 
            collector.detailPrintWriter().println( varName + ":=" + value );
        }
        declIt = proc.localDecls().iterator();
        varNumber += proc.localDecls().size(); //new
        // add local variable-declarations to varBindings stack
        while (declIt.hasNext()) {
            VarDecl localDecl = (VarDecl) declIt.next();
            //Value value = new UndefinedValue(localDecl.type());
            Value value = UndefinedValue.instance;
            //varBindings.push(localDecl.name(), value);
            conf.varBindings().push(localDecl.name(), value); 
            collector.detailPrintWriter().println(localDecl.name() + ":=" + value);
        }
		
		// call procedure
		proc.instructionList().createEvalInstr().eval( conf, this, collector ); 

	}

	public String toString() {
		return "GEvalASSLCall";
	}

	public void feedback(GConfiguration conf, Value value,
			IGCollector collector) throws GEvaluationException {
		// take variables from binding stack
		for(int i=0; i<varNumber; i++)
			conf.varBindings().pop();
		
		fCaller.feedback(conf, null, collector);
		
	}

}
