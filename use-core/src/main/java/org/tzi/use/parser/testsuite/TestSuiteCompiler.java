package org.tzi.use.parser.testsuite;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.tzi.use.output.UserOutput;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.ParseErrorHandler;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.sys.testsuite.MTestSuite;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestSuiteCompiler {
	// utility class
    private TestSuiteCompiler() {}
    

    /**
     * Compiles a test suite specification.
     *
     * @param  in the source to be compiled
     * @param  inName name of the source stream
     * @param  output user output for error messages and warnings
     * @return MTestSuite null if there were any errors
     */
    public static MTestSuite compileTestSuite(String in, 
                                              String inName,
                                              UserOutput output,
                                              MModel model) {
    	
    	InputStream inStream = new ByteArrayInputStream(in.getBytes());
    	return TestSuiteCompiler.compileTestSuite(inStream, inName, output, model);
    }
    /**
     * Compiles a test suite.
     *
     * @param  in the source to be compiled
     * @param  inName name of the source stream
     * @param  output user output for error messages and warnings
     * @return MTestSuite null if there were any errors
     */
    public static MTestSuite compileTestSuite(InputStream in, 
                                              String inName,
                                              UserOutput output,
                                              MModel model) {
        MTestSuite testSuite = null;
        ParseErrorHandler errHandler = new ParseErrorHandler(inName, output);
        
        ANTLRInputStream aInput;
		try {
			aInput = new ANTLRInputStream(in);
			aInput.name = inName;
		} catch (IOException e1) {
			output.printlnError(e1.getMessage());
			return null;
		}
		
        TestSuiteLexer lexer = new TestSuiteLexer(aInput);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        TestSuiteParser parser = new TestSuiteParser(tokenStream);
        
        lexer.init(errHandler);
        parser.init(errHandler);
        
        try {
            ASTTestSuite astTestSuite = parser.testSuite();
            if (errHandler.errorCount() == 0 ) {
                Context ctx = new Context(inName, output, null, null);
                ctx.setModel(model);
                testSuite = astTestSuite.gen(ctx);
            }
        } catch (RecognitionException e) {
            output.printlnError(parser.getSourceName() +":" +
                        e.line + ":" +
                        e.charPositionInLine + ": " + 
                        e.getMessage());
        } catch (SemanticException e) {
			output.printlnError(e.getMessage());
		}

        return testSuite;
    }
}
