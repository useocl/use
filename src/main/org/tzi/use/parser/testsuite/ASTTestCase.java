package org.tzi.use.parser.testsuite;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.cmd.ASTCmd;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.testsuite.MAssert;

public class ASTTestCase extends AST {
	private Token name;
	private List<AST> statements = new ArrayList<AST>();
	
	public ASTTestCase(Token name) {
		this.name = name;
	}
	
	public Token getName() {
		return name;
	}
	
	public void addStatement(ASTCmd cmd) {
		this.statements.add(cmd);
	}
	
	public void addStatement(ASTAssert ass) {
		this.statements.add(ass);
	}

	public boolean execute(MSystem system) throws SemanticException, MSystemException {
		Context ctx = new Context(name.getText(), null, system.topLevelBindings(), null);
		ctx.setModel(system.model());
		ctx.setSystemState(system.state());
		boolean result = true;
		
		for (AST cmd : statements) {
			// TODO: Generic Interface!
			if (cmd instanceof ASTAssert) {
				EvalContext eCtx = new EvalContext(system.state(), system.state(), system.varBindings(), null);
				ASTAssert ass = (ASTAssert)cmd;
				ctx.setIsAssertExpression(true);
				
				MAssert mAss = ass.gen(ctx);
				if (!mAss.eval(eCtx)) {
					reportAssertionError(mAss);
					return false;
				}
			
				ctx.setIsAssertExpression(false);
			} else {
				ASTCmd c = (ASTCmd)cmd;
				MCmd mCmd;
				mCmd = c.gen(ctx);

				system.executeCmd(mCmd);
			}
		}
		
		return result;
	}
	
	private String failureDetails;
	
	private void reportAssertionError(MAssert ass) {
		failureDetails = "Line " + ass.getLine() +  ": Assertion `" + ass.getExpressionString() + "' failed.";
	}
	
	public String getFailureDetails() {
		return failureDetails;
	}
}
