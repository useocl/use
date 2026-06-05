/*
 * USE - UML based specification environment
 * Aggregator for the integration test slice — tests that legitimately
 * cross production slice boundaries (e.g., exercising the public API
 * against the uml runtime via the parser).
 */
package org.tzi.use.integration;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
    private AllTests() {}

    public static Test suite() {
        final TestSuite test = new TestSuite("All integration tests");
        // uml.mm
        test.addTestSuite(org.tzi.use.integration.uml.mm.MAssociationClassTest.class);
        // uml.mm.types
        test.addTestSuite(org.tzi.use.integration.uml.mm.types.TypeTest.class);
        // uml.mm.statemachines
        test.addTestSuite(org.tzi.use.integration.uml.mm.statemachines.TestSignals.class);
        test.addTestSuite(org.tzi.use.integration.uml.mm.statemachines.TestProtocolStateMachine.class);
        // uml.sys
        test.addTestSuite(org.tzi.use.integration.uml.sys.DeletionTest.class);
        test.addTestSuite(org.tzi.use.integration.uml.sys.LinkTest.class);
        test.addTestSuite(org.tzi.use.integration.uml.sys.MCmdDestroyObjectsTest.class);
        test.addTestSuite(org.tzi.use.integration.uml.sys.MSystemStateTest.class);
        test.addTestSuite(org.tzi.use.integration.uml.sys.ModelCreationTest.class);
        // uml.sys.expr
        test.addTest(org.tzi.use.integration.uml.sys.expr.AllTests.suite());
        // uml.sys.soil
        test.addTestSuite(org.tzi.use.integration.uml.sys.soil.StatementEffectTest.class);
        // parser.shell
        test.addTestSuite(org.tzi.use.integration.parser.shell.ASTConstructionTest.class);
        test.addTestSuite(org.tzi.use.integration.parser.shell.StatementGenerationTest.class);
        // parser.soil
        test.addTestSuite(org.tzi.use.integration.parser.soil.ASTConstructionTest.class);
        test.addTestSuite(org.tzi.use.integration.parser.soil.StatementGenerationTest.class);
        // utilcore
        test.addTest(org.tzi.use.integration.utilcore.AllTests.suite());
        return test;
    }
}
