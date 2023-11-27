package org.tzi.use.uml.sys.testsuite;

import org.antlr.runtime.Token;
import org.tzi.use.output.UserOutput;
import org.tzi.use.output.VoidUserOutput;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.soil.ast.ASTStatement;
import org.tzi.use.parser.testsuite.ASTTestCase;
import org.tzi.use.parser.testsuite.ASTTestCase.TestResult;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.sys.MOperationCall;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.ppcHandling.PPCHandler;
import org.tzi.use.uml.sys.ppcHandling.PostConditionCheckFailedException;
import org.tzi.use.uml.sys.ppcHandling.PreConditionCheckFailedException;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.soil.exceptions.CompilationFailedException;

import java.util.List;

public class MTestSuite {
	private final Token name;
	private final MModel model;
	
	private final List<ASTStatement> setupStatements;
	private final List<ASTTestCase> testCases;
	
	private UserOutput output = null;
	
	public MTestSuite(Token name, MModel model, List<ASTStatement> setup, List<ASTTestCase> testCases, UserOutput output) {
		this.name = name;
		this.setupStatements = setup;
		this.testCases = testCases;
		this.model = model;
		this.output = output;
	}
	
	public String getName() {
		return name.getText();
	}
	
	public void run() {
		MSystem system;
		int testNr = 1;
		int failedTests = 0;
		
		for (ASTTestCase test : testCases) {
			// execute the setup statements
			try {
				system = setUp();
			} catch (Exception e) {
				this.output.printlnError("Error during test setup:");
				this.output.printlnError(e.getMessage());
				return;
			}
			
			report("Executing test " + testNr + "/" + testCases.size() + " `" + test.getName().getText() + "'");
			
			try {
				TestResult result = test.execute(system); 
				
				if (result == TestResult.OK) {
					reportln("... success");
				} else if (result == TestResult.FAILURE) {
					reportln("... failure");
					reportln("  " + test.getFailureDetails());
					failedTests++;
				} else if (result == TestResult.ERROR) {
					reportln("... error");
					reportln("  " + test.getFailureDetails());
					return;
				}
			} catch (Exception e) {
				this.output.printlnError("... error");
				this.output.printlnError("  " + e.getMessage());
				return;
			}
			
			testNr++;
		}
		
		if (failedTests > 0) {
			reportln("### " + failedTests + " FAILURE" + (failedTests > 1 ? "S" : "") + " ###");
		} else {
			reportln("### OK ###");
		}
	}
	
	private MSystem setUp() throws MSystemException, CompilationFailedException {
		
		MSystem system = new MSystem(model);
		system.setRunningTestSuite(true);
		system.registerPPCHandlerOverride(new PPCHandler() {
			
			@Override
			public void handlePreConditions(MSystem system, MOperationCall operationCall)
					throws PreConditionCheckFailedException {
				// we ignore the messages in tests				
			}
			
			@Override
			public void handlePostConditions(MSystem system,
					MOperationCall operationCall)
					throws PostConditionCheckFailedException {
				// we ignore the messages in tests
				
			}

			@Override
			public void handleTransitionsPre(MSystem system,
					MOperationCall operationCall)
					throws PreConditionCheckFailedException {
				// we ignore the messages in tests
				
			}

			@Override
			public void handleTransitionsPost(MSystem system,
					MOperationCall operationCall)
					throws PostConditionCheckFailedException {
				// we ignore the messages in tests
				
			}
		});

		//TODO: Check if  output really should be suppressed?
		Context ctx = new Context(name.getText(), VoidUserOutput.getInstance(), system.varBindings(), null);

		ctx.setModel(model);
		ctx.setSystemState(system.state());
		
		for (ASTStatement cmd : this.setupStatements) {
			MStatement c = cmd.generateStatement(ctx, system.getVariableEnvironment().constructSymbolTable());

			if (c == null) {
				return null;
			}
			
			system.execute(ctx.getUserOutput(), c);
		}
				
		return system;
	}
	
	public String getStats() {
		return "Test suite `" + this.getName() + "' with " + testCases.size() + " test cases";
	}
	
	private void reportln(String s) {
		if (output != null) {
			output.println(s);
		}
	}
	
	private void report(String s) {
		if (output != null) {
			output.print(s);
		}
	}
}
