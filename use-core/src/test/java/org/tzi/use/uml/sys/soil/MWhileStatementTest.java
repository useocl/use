/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 */

package org.tzi.use.uml.sys.soil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import junit.framework.TestCase;
import org.tzi.use.TestSystem;
import org.tzi.use.uml.ocl.expr.ExpConstBoolean;
import org.tzi.use.uml.ocl.expr.ExpConstInteger;
import org.tzi.use.uml.ocl.expr.ExpStdOp;
import org.tzi.use.uml.ocl.expr.ExpVariable;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;

public class MWhileStatementTest extends TestCase {

    private TestSystem testSystem;

    @Override
    public void setUp() throws Exception {
        testSystem = new TestSystem();
    }

    public void testInterruptCancelsNestedLoopAndRollsBackChanges() throws Exception {
        CountDownLatch bodyStarted = new CountDownLatch(1);
        AtomicBoolean stopWorker = new AtomicBoolean();
        MStatement assignment = new MVariableAssignmentStatement("cancelledLoopValue", IntegerValue.valueOf(1));
        MStatement body = new MStatement() {
            @Override
            public org.tzi.use.uml.ocl.value.Value execute(SoilEvaluationContext context, StatementEvaluationResult result)
                    throws EvaluationFailedException {
                if (stopWorker.get()) {
                    throw new EvaluationFailedException("Test worker cleanup");
                }
                bodyStarted.countDown();
                return assignment.execute(context, result);
            }

            @Override
            protected String shellCommand() {
                return assignment.toString();
            }

            @Override
            public String toString() {
                return shellCommand();
            }

            @Override
            public void processWithVisitor(MStatementVisitor visitor) throws Exception {
                assignment.processWithVisitor(visitor);
            }
        };
        MWhileStatement innerLoop = new MWhileStatement(new ExpConstBoolean(true), body);
        MWhileStatement outerLoop = new MWhileStatement(new ExpConstBoolean(true), innerLoop);
        AtomicReference<MSystemException> failure = new AtomicReference<>();
        AtomicBoolean interruptPreserved = new AtomicBoolean();
        Thread worker = new Thread(() -> {
            try {
                testSystem.getSystem().execute(outerLoop);
            } catch (MSystemException e) {
                failure.set(e);
                interruptPreserved.set(Thread.currentThread().isInterrupted());
            }
        });
        worker.setDaemon(true);
        worker.start();

        try {
            assertTrue("Loop body did not start", bodyStarted.await(5, TimeUnit.SECONDS));
            worker.interrupt();
            worker.join(TimeUnit.SECONDS.toMillis(5));

            assertFalse("Interrupted loop did not finish", worker.isAlive());
            assertNotNull("Cancellation should be reported as a system failure", failure.get());
            assertTrue("Failure should identify interruption", failure.get().getMessage().toLowerCase().contains("interrupt"));
            assertTrue("Interrupt status should be preserved", interruptPreserved.get());
            assertNull("Changes made before cancellation should be rolled back",
                    testSystem.getVarEnv().lookUp("cancelledLoopValue"));
        } finally {
            stopWorker.set(true);
            if (worker.isAlive()) {
                worker.interrupt();
                worker.join(TimeUnit.SECONDS.toMillis(5));
            }
        }
    }

    public void testPreInterruptedThreadCancelsEvenWhenConditionIsFalse() {
        Thread.currentThread().interrupt();
        try {
            try {
                testSystem.getSystem().execute(new MWhileStatement(new ExpConstBoolean(false), MEmptyStatement.getInstance()));
                fail("Expected a pre-interrupted execution to be cancelled");
            } catch (MSystemException e) {
                assertTrue(e.getMessage().toLowerCase().contains("interrupt"));
            }
            assertTrue("Interrupt status should be preserved", Thread.currentThread().isInterrupted());
        } finally {
            assertTrue("Interrupt status should remain set until cleared", Thread.interrupted());
        }
    }

    public void testFalseConditionExecutesNormally() throws MSystemException {
        testSystem.getSystem().execute(new MWhileStatement(new ExpConstBoolean(false), MEmptyStatement.getInstance()));
    }

    public void testTerminatingLoopExecutesNormally() throws Exception {
        testSystem.getVarEnv().assign("loopCounter", IntegerValue.valueOf(0));
        Expression condition = ExpStdOp.create("<", new Expression[]{
                new ExpVariable("loopCounter", TypeFactory.mkInteger()), new ExpConstInteger(3)
        });
        Expression nextValue = ExpStdOp.create("+", new Expression[]{
                new ExpVariable("loopCounter", TypeFactory.mkInteger()), new ExpConstInteger(1)
        });

        testSystem.getSystem().execute(new MWhileStatement(condition,
                new MVariableAssignmentStatement("loopCounter", new MRValueExpression(nextValue))));

        assertEquals(IntegerValue.valueOf(3), testSystem.getVarEnv().lookUp("loopCounter"));
    }

    private MWhileStatement countingLoop(String variable, int iterations) throws Exception {
        Expression condition = ExpStdOp.create("<", new Expression[]{
                new ExpVariable(variable, TypeFactory.mkInteger()), new ExpConstInteger(iterations)
        });
        Expression nextValue = ExpStdOp.create("+", new Expression[]{
                new ExpVariable(variable, TypeFactory.mkInteger()), new ExpConstInteger(1)
        });
        return new MWhileStatement(condition,
                new MVariableAssignmentStatement(variable, new MRValueExpression(nextValue)));
    }

    public void testUndoRecordGrowsWithEveryIterationByDefault() throws Exception {
        testSystem.getVarEnv().assign("loopCounter", IntegerValue.valueOf(0));

        StatementEvaluationResult result = testSystem.getSystem().execute(countingLoop("loopCounter", 1000));

        assertEquals(1000, result.getInverseStatement().getStatements().size());
        assertEquals(1, testSystem.getSystem().numEvaluatedStatements());
    }

    public void testLoopWithUndoRecordingDisabledKeepsNoUndoRecord() throws Exception {
        testSystem.getSystem().setUndoRecordingEnabled(false);
        testSystem.getVarEnv().assign("loopCounter", IntegerValue.valueOf(0));

        StatementEvaluationResult result = testSystem.getSystem().execute(countingLoop("loopCounter", 100000));

        assertEquals(IntegerValue.valueOf(100000), testSystem.getVarEnv().lookUp("loopCounter"));
        assertTrue("No inverse statements should be recorded", result.getInverseStatement().getStatements().isEmpty());
        assertTrue("No events should be recorded", result.getEvents().isEmpty());
        assertEquals("Result should not be stored for undo", 0, testSystem.getSystem().numEvaluatedStatements());
    }

    public void testDisablingUndoRecordingClearsUndoStack() throws Exception {
        testSystem.getVarEnv().assign("loopCounter", IntegerValue.valueOf(0));
        testSystem.getSystem().execute(countingLoop("loopCounter", 3));
        assertEquals(1, testSystem.getSystem().numEvaluatedStatements());

        testSystem.getSystem().setUndoRecordingEnabled(false);

        assertFalse(testSystem.getSystem().isUndoRecordingEnabled());
        assertEquals(0, testSystem.getSystem().numEvaluatedStatements());
        try {
            testSystem.getSystem().undoLastStatement();
            fail("Expected nothing to undo");
        } catch (MSystemException expected) {
            // nothing to undo
        }
    }

    public void testReenablingUndoRecordingRecordsAgain() throws Exception {
        testSystem.getSystem().setUndoRecordingEnabled(false);
        testSystem.getSystem().setUndoRecordingEnabled(true);
        testSystem.getVarEnv().assign("loopCounter", IntegerValue.valueOf(0));

        testSystem.getSystem().execute(countingLoop("loopCounter", 3));
        testSystem.getSystem().undoLastStatement();

        assertEquals(IntegerValue.valueOf(0), testSystem.getVarEnv().lookUp("loopCounter"));
    }

    public void testCancelledLoopIsNotRolledBackWhenUndoRecordingIsDisabled() {
        testSystem.getSystem().setUndoRecordingEnabled(false);
        MStatement statement = new MSequenceStatement(
                new MVariableAssignmentStatement("keptValue", IntegerValue.valueOf(1)),
                new MWhileStatement(new ExpConstBoolean(true), MEmptyStatement.getInstance()));

        Thread.currentThread().interrupt();
        try {
            try {
                testSystem.getSystem().execute(statement);
                fail("Expected the interrupted loop to be cancelled");
            } catch (MSystemException e) {
                assertTrue(e.getMessage().toLowerCase().contains("interrupt"));
            }
        } finally {
            Thread.interrupted();
        }

        assertEquals("Without undo information, changes before cancellation are kept",
                IntegerValue.valueOf(1), testSystem.getVarEnv().lookUp("keptValue"));
    }
}
