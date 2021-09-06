package org.tzi.use.gen.assl.dynamics;

import java.util.Iterator;

import org.tzi.use.gen.assl.statics.GInstrOpEnter;
import org.tzi.use.gen.assl.statics.GOCLExpression;
import org.tzi.use.gen.assl.statics.GValueInstruction;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.uml.sys.soil.MEnterOperationStatement;
import org.tzi.use.uml.sys.soil.MStatement;

public class GEvalOpEnter extends GEvalInstruction  implements IGCaller {

	GInstrOpEnter fInstr;
	IGCaller fCaller;
	
	public GEvalOpEnter (GInstrOpEnter instr) {
		fInstr = instr;
	}
	
	@Override
	public void eval(GConfiguration conf, IGCaller caller, IGCollector collector)
			throws GEvaluationException {

		collector.detailPrintWriter().println("evaluating `" + fInstr + "'");
		fCaller = caller;
		fInstr.objname().createEvalInstr().eval(conf, this, collector);
	}

	public void feedback( GConfiguration conf,
            Value value,
            IGCollector collector ) throws GEvaluationException {
		// if a pre- or postcondition violation occured before skip this command and continue with ASSL evaluation
		if (collector.getPrePostViolation()) 
			fCaller.feedback(conf, value, collector);
		else {
			// no condition violation occurred before. Generate and execute this OpEnter ASSL command
			collector.detailPrintWriter().println("evaluating `" + fInstr + "'");

			Expression[] argExprs;
			Expression expr = new ExpressionWithValue( value );

			Type t = expr.type();
			if (!t.isTypeOfClass() ) throw new GEvaluationException();
			
			MClass cls = (MClass)t;
			MOperation op = cls.operation(fInstr.opname(), true);

			// get Parameter and transform them into expressions
			Iterator<GValueInstruction> paramIter = fInstr.parameter().iterator();
			argExprs = new Expression[fInstr.parameter().size()];
			int i=0;
			while (paramIter.hasNext()){
				GOCLExpression goexpr = (GOCLExpression) paramIter.next();
				Value v2 = conf.evalExpression(goexpr.expression());
				argExprs[i] = new ExpressionWithValue(v2);
				i++;
			}
			
			// generate statement for openter command 
			MEnterOperationStatement stmt = new MEnterOperationStatement(expr, op, argExprs);
			MStatement inverseStatement = null;
			StatementEvaluationResult evaluationResult = null;
			try {
				// execute openter command
				evaluationResult = conf.systemState().system().execute(stmt, false, false, false);
				inverseStatement = evaluationResult.getInverseStatement();
			} catch (MSystemException e) {
				// Precondition violated
				//e.printStackTrace();
			}
			// if a precondition was violated an exception is thrown and no evaluation result is generated. 
			// The violation is marked in the collector. 
			if ( evaluationResult == null )
				collector.setPrePostViolation();

			fCaller.feedback(conf, value, collector);

			// if all preconditions hold the opexit command is added to the ASSL command list 
			if (evaluationResult!=null && collector.expectSubsequentReporting()) {
				collector.subsequentlyPrependStatement( stmt );
			}

			try {
				if (!collector.getPrePostViolation())
					conf.systemState().system().execute(inverseStatement, true, false, false);
			} catch (MSystemException e) {
				e.printStackTrace();
			}
		}


	}

	public String toString() {
		return "GEvalASSLCall";
	}
	
}
