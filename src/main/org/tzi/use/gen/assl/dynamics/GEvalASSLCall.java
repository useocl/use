package org.tzi.use.gen.assl.dynamics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tzi.use.gen.assl.statics.GInstrASSLCall;
import org.tzi.use.gen.assl.statics.GOCLExpression;
import org.tzi.use.gen.assl.statics.GProcedure;
import org.tzi.use.uml.ocl.expr.Evaluator;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;

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
		Iterator<GOCLExpression> paramIter = fInstr.getArguments().iterator();
		varNumber = fInstr.getArguments().size();
		Evaluator eval = new Evaluator();
		List<Value> paramValues = new ArrayList<Value>();
		
		// adding parameter to callString and varBindings stack
		while (paramIter.hasNext()){
			GOCLExpression expr = paramIter.next();
            Value val = eval.eval(expr.expression(), conf.systemState(), conf.varBindings());
            paramValues.add(val);
		}

		GProcedure proc = fInstr.getProcedure();		
				
		// add parameter variables and local variables to varBindings
		// first: add parameter-variable declarations to varBindings stack
        Iterator<VarDecl> declIt;
        declIt = proc.parameterDecls().iterator();
        varNumber = paramValues.size();
        
        Iterator<Value> valuesIt = paramValues.iterator();
        while (declIt.hasNext()) {
            String varName = declIt.next().name();
            Value value = valuesIt.next();
            conf.varBindings().push(varName, value); 
            collector.detailPrintWriter().println( varName + ":=" + value );
        }
        
        declIt = proc.localDecls().iterator();
        varNumber += proc.localDecls().size(); //new
        // add local variable-declarations to varBindings stack
        while (declIt.hasNext()) {
            VarDecl localDecl = declIt.next();
            Value value = UndefinedValue.instance;
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
