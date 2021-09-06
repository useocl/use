package org.tzi.use.gen.assl.dynamics;

import org.tzi.use.gen.assl.statics.GInstrOpExit;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.uml.sys.soil.MExitOperationStatement;
import org.tzi.use.uml.sys.soil.MStatement;

public class GEvalOpExit extends GEvalInstruction {

	public GEvalOpExit( GInstrOpExit instr ) {
		// no
	}
	
	@Override
	public void eval(GConfiguration conf, IGCaller caller, IGCollector collector)
			throws GEvaluationException {
		// a precondition violation occurred before. This command is skipped. Continue with ASSL evaluation. 
		if (collector.getPrePostViolation()) 
			caller.feedback(conf, null, collector);
		else {
			// no precondition violation occurred before. Generate OpExit statement. 
			MStatement statement = new MExitOperationStatement(null);
			StatementEvaluationResult evaluationResult = null;
			MStatement inverseStatement = null;
			try {
				// execute OpExit statement
				evaluationResult = conf.systemState().system().execute(statement, false, false, false);
				inverseStatement = evaluationResult.getInverseStatement();
			} catch (MSystemException e) {
				//e.printStackTrace();
				collector.setPrePostViolation();
			} 

			caller.feedback(conf, null, collector);
			if (collector.expectSubsequentReporting()) {
				collector.subsequentlyPrependStatement( statement );
			}
			try {
				if (evaluationResult!=null)
					conf.systemState().system().execute(inverseStatement, true, false, false);
			} catch (MSystemException e) {
				e.printStackTrace();
			}
		}
	}

}
