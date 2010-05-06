package org.tzi.use.parser.testsuite;

import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.cmd.ASTCmd;
import org.tzi.use.uml.sys.testsuite.MTestSuite;

public class ASTTestSuite extends AST {
	
	private Token name;
	private Token modelFile;
	private List<ASTTestCase> testCases;
	private List<ASTCmd> setupStatements;
	
	public Token getModelFile() {
		return modelFile;
	}

	public void setModelFile(Token modelFile) {
		this.modelFile = modelFile;
	}

	public List<ASTTestCase> getTestCases() {
		return testCases;
	}

	public void setTestCases(List<ASTTestCase> testCases) {
		this.testCases = testCases;
	}

	public List<ASTCmd> getSetupStatements() {
		return setupStatements;
	}

	public void setSetupStatements(List<ASTCmd> setupStatements) {
		this.setupStatements = setupStatements;
	}

	public Token getName() {
		return name;
	}

	public ASTTestSuite(Token name) {
		this.name = name;
	}
	
	public MTestSuite gen(Context ctx) throws SemanticException {
		if (!ctx.model().name().equals(this.getName().getText())) {
			throw new SemanticException(this.getModelFile(),
					"The test suite is for the model `"
							+ this.getModelFile().getText()
							+ "' but the loaded model is `"
							+ ctx.model().name() + "'");
		}
			
		MTestSuite result = new MTestSuite(name, ctx.model(), setupStatements, testCases);
				
		return result;
	}
}
