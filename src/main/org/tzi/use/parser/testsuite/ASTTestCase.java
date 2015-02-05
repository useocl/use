package org.tzi.use.parser.testsuite;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.soil.ast.ASTStatement;
import org.tzi.use.uml.ocl.expr.EvalContext;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.soil.MExitOperationStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.uml.sys.testsuite.MAssert;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.CompilationFailedException;

public class ASTTestCase extends AST {
	public enum TestResult {
		OK,
		FAILURE,
		ERROR
	}
	
	private Token name;
	private List<AST> statements = new ArrayList<AST>();
	
	public ASTTestCase(Token name) {
		this.name = name;
	}
	
	public Token getName() {
		return name;
	}
	
	public void addStatement(AST cmd) {
		this.statements.add(cmd);
	}
	
	public void addStatement(ASTAssert ass) {
		this.statements.add(ass);
	}

	public TestResult execute(MSystem system) throws SemanticException, MSystemException {
		
		MSystemState preState = system.state();
				
		for (AST cmd : statements) {
			Context ctx = new Context(name.getText(), null, system.varBindings(), null);
			ctx.setModel(system.model());
			ctx.setIsInsideTestCase(true);
			
			StringWriter errors = new StringWriter();
			ctx.setOut(new PrintWriter(errors));
			ctx.setSystemState(system.state());
			
			// TODO: Generic Interface!
			if (cmd instanceof ASTAssert) {
				EvalContext eCtx = new EvalContext(preState, system.state(), system.varBindings(), null, "");
				ASTAssert ass = (ASTAssert)cmd;
				ctx.setIsAssertExpression(true);
				
				MAssert mAss = ass.gen(ctx);
				if (!mAss.eval(eCtx)) {
					reportAssertionError(mAss, ctx);
					return TestResult.FAILURE;
				}
			} else if (cmd instanceof ASTVariation) {
				ASTVariation aVar = (ASTVariation)cmd;
				aVar.gen(ctx).doExecute();
			} else {
				ASTStatement astCmd = (ASTStatement)cmd;
				MStatement mCmd;
				
				try {
					mCmd = astCmd.generateStatement(ctx, system.getVariableEnvironment().constructSymbolTable());
				} catch (CompilationFailedException e) {
					failureDetails = "Line " + astCmd.getSourcePosition().line() + ": " + astCmd.toString() + " command failed: " + e.getMessage();
					return TestResult.ERROR;
				}
				
				if (mCmd == null) {
					failureDetails = "Line " + astCmd.getSourcePosition().line() + ": " + astCmd.toString() + " command failed!";
					return TestResult.ERROR;
				}
				
				system.execute(mCmd);
				
				if (mCmd instanceof MExitOperationStatement) {
					// We keep track of the last pre state to allow asserts after
					// an operation call
					MExitOperationStatement opExit = (MExitOperationStatement)mCmd;
					preState = opExit.getOperationCall().getPreState();
				}
			}
		}
		
		return TestResult.OK;
	}
	
	private String failureDetails;
	
	private void reportAssertionError(MAssert ass, Context ctx) {
		StringBuilder details = new StringBuilder();
		
		details.append("Line ");
		details.append(ass.getPosition().line());
		details.append(": Assertion `");
		if (ass.getMessage() == null) {
			details.append(ass.getExpressionString());
		} else {
			details.append(ass.getMessage());
		}
		details.append("' failed.");
		
		details.append(StringUtil.NEWLINE);
		details.append("Commands to reproduce state:");
		details.append(StringUtil.NEWLINE);
		
		StringWriter sw = new StringWriter();
		PrintWriter out = new PrintWriter(sw);
		
		ctx.systemState().system().writeSoilStatements(out);
				
		details.append(sw.toString());
		this.failureDetails = details.toString();
	}
	
	public String getFailureDetails() {
		return failureDetails;
	}
}
