package org.tzi.use.parser.testsuite;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.ParseErrorHandler;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.sys.testsuite.MTestSuite;

public class TestSuiteCompiler {
	// utility class
    private TestSuiteCompiler() {}
    

    /**
     * Compiles a test suite specification.
     *
     * @param  in the source to be compiled
     * @param  inName name of the source stream
     * @param  err output stream for error messages
     * @return MTestSuite null if there were any errors
     */
    public static MTestSuite compileTestSuite(String in, 
                                              String inName,
                                              PrintWriter err,
                                              MModel model) {
    	
    	InputStream inStream = new ByteArrayInputStream(in.getBytes());
    	return TestSuiteCompiler.compileTestSuite(inStream, inName, err, model);
    }
    /**
     * Compiles a test suite.
     *
     * @param  in the source to be compiled
     * @param  inName name of the source stream
     * @param  err output stream for error messages
     * @return MTestSuite null if there were any errors
     */
    public static MTestSuite compileTestSuite(InputStream in, 
                                              String inName,
                                              PrintWriter err,
                                              MModel model) {
        MTestSuite testSuite = null;
        ParseErrorHandler errHandler = new ParseErrorHandler(inName, err);
        
        ANTLRInputStream aInput;
		try {
			aInput = new ANTLRInputStream(in);
			aInput.name = inName;
		} catch (IOException e1) {
			err.println(e1.getMessage());
			return testSuite;
		}
		
        TestSuiteLexer lexer = new TestSuiteLexer(aInput);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        TestSuiteParser parser = new TestSuiteParser(tokenStream);
        
        lexer.init(errHandler);
        parser.init(errHandler);
        
        try {
            ASTTestSuite astTestSuite = parser.testSuite();
            if (errHandler.errorCount() == 0 ) {
                Context ctx = new Context(inName, err, null, null);
                ctx.setModel(model);
                testSuite = astTestSuite.gen(ctx);
            }
        } catch (RecognitionException e) {
            err.println(parser.getSourceName() +":" + 
                        e.line + ":" +
                        e.charPositionInLine + ": " + 
                        e.getMessage());
        } catch (SemanticException e) {
			err.println(e.getMessage());
		}
        
        err.flush();
        return testSuite;
    }
}
