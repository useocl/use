package org.tzi.use.uml.sys.testsuite;

import java.io.PrintWriter;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.cmd.ASTCmd;
import org.tzi.use.parser.testsuite.ASTTestCase;
import org.tzi.use.parser.testsuite.ASTTestCase.TestResult;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.sys.MCmd;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.util.NullWriter;

public class MTestSuite {
	private Token name;
	private MModel model;
	
	private List<ASTCmd> setupStatements;
	private List<ASTTestCase> testCases;
	
	private PrintWriter output = null;
	
	public MTestSuite(Token name, MModel model, List<ASTCmd> setup, List<ASTTestCase> testCases) {
		this.name = name;
		this.setupStatements = setup;
		this.testCases = testCases;
		this.model = model;
	}
	
	public String getName() {
		return name.getText();
	}
	
	public void run(PrintWriter output) {
		this.output = output;
		this.run();
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
				System.err.println("Error during test setup:");
				System.err.println(e.getMessage());
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
				System.err.println("... error");
				System.err.println("  " + e.getMessage());
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
	
	private MSystem setUp() throws MSystemException, SemanticException {
		MSystem system = new MSystem(model);
		Context ctx = new Context(name.getText(), output, system.varBindings(), null);
		ctx.setOut(new PrintWriter(new NullWriter()));
		ctx.setModel(model);
		ctx.setSystemState(system.state());
		
		for (ASTCmd cmd : this.setupStatements) {
			MCmd c = cmd.gen(ctx);
			if (c == null)
				return null;
			
			system.executeCmd(c);
		}
				
		return system;
	}
	
	public String getStats() {
		return "Test suite `" + this.getName() + "' with " + testCases.size() + " test cases";
	}
	
	private void reportln(String s) {
		if (output != null) {
			output.println(s);
			output.flush();
		}
	}
	
	private void report(String s) {
		if (output != null) {
			output.print(s);
			output.flush();
		}
	}
}
