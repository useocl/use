package org.tzi.use.parser.testsuite;

import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.soil.ast.ASTStatement;
import org.tzi.use.uml.sys.testsuite.MTestSuite;

import java.util.List;

public class ASTTestSuite extends AST {
	
	private Token name;
	private Token modelFile;
	private List<ASTTestCase> testCases;
	private List<ASTStatement> setupStatements;
	
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

	public List<ASTStatement> getSetupStatements() {
		return setupStatements;
	}

	public void setSetupStatements(List<ASTStatement> setupStatements) {
		this.setupStatements = setupStatements;
	}

	public Token getName() {
		return name;
	}

	public ASTTestSuite(Token name) {
		this.name = name;
	}
	
	public MTestSuite gen(Context ctx) throws SemanticException {
		return new MTestSuite(name, ctx.model(), setupStatements, testCases, ctx.getUserOutput());
	}
}
