/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

// $Id$

package org.tzi.use.uml.ocl.expr;
import junit.framework.TestCase;

import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.BagValue;
import org.tzi.use.uml.ocl.value.BooleanValue;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.RealValue;
import org.tzi.use.uml.ocl.value.SequenceValue;
import org.tzi.use.uml.ocl.value.SetValue;
import org.tzi.use.uml.ocl.value.UndefinedValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemState;

/**
 * Test ExpStdOp class.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */

public class ExpStdOpTest extends TestCase {
    private MSystemState state;
    private Evaluator e;

    protected void setUp() throws Exception {
        state = new MSystem(new ModelFactory().createModel("Test")).state();
        e = new Evaluator();
    }

    public void testIfExpression() throws ExpInvalidException {
        ExpIf exp =
            new ExpIf(
                      new ExpConstBoolean(true),
                      new ExpConstInteger(2),
                      new ExpConstInteger(3));
        assertEquals(exp.toString(), new IntegerValue(2), e.eval(exp, state));
        exp =
            new ExpIf(
                      new ExpConstBoolean(false),
                      new ExpConstInteger(2),
                      new ExpConstInteger(3));
        assertEquals(exp.toString(), new IntegerValue(3), e.eval(exp, state));
    }

    public void testBooleanOperations() throws ExpInvalidException {
        Expression[] args;
        ExpStdOp op;
        args = new Expression[] { new ExpConstBoolean(true)};
        op = ExpStdOp.create("not", args);
        assertEquals(op.toString(), BooleanValue.FALSE, e.eval(op, state));

        args = new Expression[] { new ExpConstBoolean(true), new ExpConstBoolean(true)};
        op = ExpStdOp.create("=", args);
        assertEquals(op.toString(), BooleanValue.TRUE, e.eval(op, state));

        args =
            new Expression[] { new ExpConstBoolean(false), new ExpConstBoolean(false)};
        op = ExpStdOp.create("=", args);
        assertEquals(op.toString(), BooleanValue.TRUE, e.eval(op, state));

        args = new Expression[] { new ExpConstBoolean(true), new ExpConstBoolean(false)};
        op = ExpStdOp.create("<>", args);
        assertEquals(op.toString(), BooleanValue.TRUE, e.eval(op, state));
    }

    public void testNumberOps() throws ExpInvalidException {
        Expression[] args;
        ExpStdOp op;
        args = new Expression[] { new ExpConstInteger(2), new ExpConstInteger(3)};
        op = ExpStdOp.create("=", args);
        assertEquals(op.toString(), BooleanValue.FALSE, e.eval(op, state));

        args = new Expression[] { new ExpConstInteger(2), new ExpConstInteger(2)};
        op = ExpStdOp.create("=", args);
        assertEquals(op.toString(), BooleanValue.TRUE, e.eval(op, state));

        args = new Expression[] { new ExpConstReal(1.2), new ExpConstReal(1.2)};
        op = ExpStdOp.create("=", args);
        assertEquals(op.toString(), BooleanValue.TRUE, e.eval(op, state));

        args = new Expression[] { new ExpConstReal(2.0), new ExpConstInteger(2)};
        op = ExpStdOp.create("=", args);
        assertEquals(op.toString(), BooleanValue.TRUE, e.eval(op, state));

        args = new Expression[] { new ExpConstInteger(2), new ExpConstReal(2.0)};
        op = ExpStdOp.create("=", args);
        assertEquals(op.toString(), BooleanValue.TRUE, e.eval(op, state));

        args = new Expression[] { new ExpConstInteger(2), new ExpConstInteger(3)};
        op = ExpStdOp.create("+", args);
        assertEquals(op.toString(), new IntegerValue(5), e.eval(op, state));

        args = new Expression[] { new ExpConstReal(2.6), new ExpConstInteger(3)};
        op = ExpStdOp.create("+", args);
        assertEquals(op.toString(), new RealValue(5.6), e.eval(op, state));

        args = new Expression[] { new ExpConstInteger(3), new ExpConstReal(2.6)};
        op = ExpStdOp.create("+", args);
        assertEquals(op.toString(), new RealValue(5.6), e.eval(op, state));

        args = new Expression[] { new ExpConstInteger(6), new ExpConstInteger(3)};
        op = ExpStdOp.create("/", args);
        assertEquals(op.toString(), new RealValue(2.0), e.eval(op, state));

        args = new Expression[] { new ExpConstInteger(7), new ExpConstInteger(2)};
        op = ExpStdOp.create("/", args);
        assertEquals(op.toString(), new RealValue(3.5), e.eval(op, state));

        args = new Expression[] { new ExpConstInteger(7), new ExpConstInteger(2)};
        op = ExpStdOp.create("div", args);
        assertEquals(op.toString(), new IntegerValue(3), e.eval(op, state));

        args = new Expression[] { new ExpConstInteger(7), new ExpConstInteger(0)};
        op = ExpStdOp.create("div", args);
        assertEquals(
                     op.toString(),
                     new UndefinedValue(TypeFactory.mkInteger()),
                     e.eval(op, state));

        args = new Expression[] { new ExpConstInteger(7), new ExpConstInteger(0)};
        op = ExpStdOp.create("/", args);
        assertEquals(
                     op.toString(),
                     new UndefinedValue(TypeFactory.mkReal()),
                     e.eval(op, state));

        args = new Expression[] { new ExpConstInteger(3)};
        op = ExpStdOp.create("abs", args);
        assertEquals(op.toString(), new IntegerValue(3), e.eval(op, state));

        args = new Expression[] { new ExpConstInteger(-3)};
        op = ExpStdOp.create("abs", args);
        assertEquals(op.toString(), new IntegerValue(3), e.eval(op, state));
    }

    public void testSetConstruction() throws ExpInvalidException {
        Expression[] args;
        ExpStdOp op;
        Value values[];
        args = new Expression[] { new ExpConstInteger(2), new ExpConstInteger(3)};
        op = ExpStdOp.create("mkSet", args);
        values = new Value[] { new IntegerValue(2), new IntegerValue(3)};
        assertEquals(
                     op.toString(),
                     new SetValue(TypeFactory.mkInteger(), values),
                     e.eval(op, state));

        args = new Expression[] { new ExpConstInteger(1), new ExpConstInteger(10)};
        op = ExpStdOp.create("mkSetRange", args);
        values = new Value[10];
        for (int i = 1; i <= 10; i++)
            values[i - 1] = new IntegerValue(i);
        assertEquals(
                     op.toString(),
                     new SetValue(TypeFactory.mkInteger(), values),
                     e.eval(op, state));
    }

    public void testSequenceConstruction() throws ExpInvalidException {
        Expression[] args;
        ExpStdOp op;
        Value values[];
        args = new Expression[] { new ExpConstInteger(2), new ExpConstInteger(3)};
        op = ExpStdOp.create("mkSequence", args);
        values = new Value[] { new IntegerValue(2), new IntegerValue(3)};
        assertEquals(
                     op.toString(),
                     new SequenceValue(TypeFactory.mkInteger(), values),
                     e.eval(op, state));

        args = new Expression[] { new ExpConstInteger(1), new ExpConstInteger(10)};
        op = ExpStdOp.create("mkSequenceRange", args);
        values = new Value[10];
        for (int i = 1; i <= 10; i++)
            values[i - 1] = new IntegerValue(i);
        assertEquals(
                     op.toString(),
                     new SequenceValue(TypeFactory.mkInteger(), values),
                     e.eval(op, state));
    }

    public void testBagConstruction() throws ExpInvalidException {
        Expression[] args;
        ExpStdOp op;
        Value values[];
        args = new Expression[] { new ExpConstInteger(2), new ExpConstInteger(3)};
        op = ExpStdOp.create("mkBag", args);
        values = new Value[] { new IntegerValue(2), new IntegerValue(3)};
        assertEquals(
                     op.toString(),
                     new BagValue(TypeFactory.mkInteger(), values),
                     e.eval(op, state));

        args = new Expression[] { new ExpConstInteger(1), new ExpConstInteger(10)};
        op = ExpStdOp.create("mkBagRange", args);
        values = new Value[10];
        for (int i = 1; i <= 10; i++)
            values[i - 1] = new IntegerValue(i);
        assertEquals(
                     op.toString(),
                     new BagValue(TypeFactory.mkInteger(), values),
                     e.eval(op, state));
    }

    public void testCollectionOperations() throws ExpInvalidException {
        Expression[] args;
        Expression[] args1;
        Expression[] args2;
        ExpStdOp op;
        ExpStdOp op1;
        ExpStdOp op2;
        args1 = new Expression[] { new ExpConstInteger(2), new ExpConstInteger(3)};
        op1 = ExpStdOp.create("mkSet", args1);
        args = new Expression[] { op1 };

        op = ExpStdOp.create("size", args);
        assertEquals(op.toString(), new IntegerValue(2), e.eval(op, state));

        op = ExpStdOp.create("isEmpty", args);
        assertEquals(op.toString(), BooleanValue.FALSE, e.eval(op, state));

        op = ExpStdOp.create("notEmpty", args);
        assertEquals(op.toString(), BooleanValue.TRUE, e.eval(op, state));

        args = new Expression[] { op1, new ExpConstInteger(3)};
        op = ExpStdOp.create("includes", args);
        assertEquals(op.toString(), BooleanValue.TRUE, e.eval(op, state));

        args = new Expression[] { op1, new ExpConstInteger(5)};
        op = ExpStdOp.create("includes", args);
        assertEquals(op.toString(), BooleanValue.FALSE, e.eval(op, state));

        args = new Expression[] { op1, new ExpConstInteger(3)};
        op = ExpStdOp.create("count", args);
        assertEquals(op.toString(), new IntegerValue(1), e.eval(op, state));

        args = new Expression[] { op1, new ExpConstInteger(5)};
        op = ExpStdOp.create("count", args);
        assertEquals(op.toString(), new IntegerValue(0), e.eval(op, state));

        args2 = new Expression[] { new ExpConstInteger(2), new ExpConstInteger(3)};
        op2 = ExpStdOp.create("mkSet", args2);
        args = new Expression[] { op1, op2 };
        op = ExpStdOp.create("includesAll", args);
        assertEquals(op.toString(), BooleanValue.TRUE, e.eval(op, state));

        args2 = new Expression[] { new ExpConstInteger(2)};
        op2 = ExpStdOp.create("mkSet", args2);
        args = new Expression[] { op1, op2 };
        op = ExpStdOp.create("includesAll", args);
        assertEquals(op.toString(), BooleanValue.TRUE, e.eval(op, state));

        args2 = new Expression[] { new ExpConstInteger(2), new ExpConstInteger(5)};
        op2 = ExpStdOp.create("mkSet", args2);
        args = new Expression[] { op1, op2 };
        op = ExpStdOp.create("includesAll", args);
        assertEquals(op.toString(), BooleanValue.FALSE, e.eval(op, state));
    }

    public void testSetOperations() throws ExpInvalidException {
        Expression[] args;
        Expression[] args1;
        Expression[] args2;
        ExpStdOp op;
        ExpStdOp op1;
        ExpStdOp op2;
        Value values[];
        args1 = new Expression[] { new ExpConstInteger(2), new ExpConstInteger(3)};
        op1 = ExpStdOp.create("mkSet", args1);
        args = new Expression[] { op1 };

        args2 = new Expression[] { new ExpConstInteger(2), new ExpConstInteger(5)};
        op2 = ExpStdOp.create("mkSet", args2);
        args = new Expression[] { op1, op2 };
        op = ExpStdOp.create("union", args);
        values =
            new Value[] { new IntegerValue(2), new IntegerValue(3), new IntegerValue(5)};
        assertEquals(
                     op.toString(),
                     new SetValue(TypeFactory.mkInteger(), values),
                     e.eval(op, state));

        args2 = new Expression[] { new ExpConstInteger(2), new ExpConstInteger(5)};
        op2 = ExpStdOp.create("mkSet", args2);
        args = new Expression[] { op1, op2 };
        op = ExpStdOp.create("intersection", args);
        values = new Value[] { new IntegerValue(2)};
        assertEquals(
                     op.toString(),
                     new SetValue(TypeFactory.mkInteger(), values),
                     e.eval(op, state));

        args2 = new Expression[] { new ExpConstInteger(4), new ExpConstInteger(5)};
        op2 = ExpStdOp.create("mkSet", args2);
        args = new Expression[] { op1, op2 };
        op = ExpStdOp.create("intersection", args);
        assertEquals(
                     op.toString(),
                     new SetValue(TypeFactory.mkInteger()),
                     e.eval(op, state));

        args2 = new Expression[] { new ExpConstInteger(2), new ExpConstInteger(5)};
        op2 = ExpStdOp.create("mkSet", args2);
        args = new Expression[] { op1, op2 };
        op = ExpStdOp.create("-", args);
        values = new Value[] { new IntegerValue(3)};
        assertEquals(
                     op.toString(),
                     new SetValue(TypeFactory.mkInteger(), values),
                     e.eval(op, state));

        args = new Expression[] { op1, new ExpConstInteger(8)};
        op = ExpStdOp.create("including", args);
        values =
            new Value[] { new IntegerValue(2), new IntegerValue(3), new IntegerValue(8)};
        assertEquals(
                     op.toString(),
                     new SetValue(TypeFactory.mkInteger(), values),
                     e.eval(op, state));

        args = new Expression[] { op1, new ExpConstInteger(3)};
        op = ExpStdOp.create("including", args);
        values = new Value[] { new IntegerValue(2), new IntegerValue(3)};
        assertEquals(
                     op.toString(),
                     new SetValue(TypeFactory.mkInteger(), values),
                     e.eval(op, state));

        args = new Expression[] { op1, new ExpConstInteger(8)};
        op = ExpStdOp.create("excluding", args);
        values = new Value[] { new IntegerValue(2), new IntegerValue(3)};
        assertEquals(
                     op.toString(),
                     new SetValue(TypeFactory.mkInteger(), values),
                     e.eval(op, state));

        args = new Expression[] { op1, new ExpConstInteger(3)};
        op = ExpStdOp.create("excluding", args);
        values = new Value[] { new IntegerValue(2)};
        assertEquals(
                     op.toString(),
                     new SetValue(TypeFactory.mkInteger(), values),
                     e.eval(op, state));

        args2 = new Expression[] { new ExpConstInteger(2), new ExpConstInteger(5)};
        op2 = ExpStdOp.create("mkSet", args2);
        args = new Expression[] { op1, op2 };
        op = ExpStdOp.create("symmetricDifference", args);
        values = new Value[] { new IntegerValue(3), new IntegerValue(5)};
        assertEquals(
                     op.toString(),
                     new SetValue(TypeFactory.mkInteger(), values),
                     e.eval(op, state));
    }

    public void testBagOperations() throws ExpInvalidException {
        Expression[] args;
        Expression[] args1;
        ExpStdOp op;
        ExpStdOp op1;
        Value values[];
        args1 =
            new Expression[] {
                new ExpConstInteger(2),
                new ExpConstInteger(3),
                new ExpConstInteger(2)};
        op1 = ExpStdOp.create("mkBag", args1);
        args = new Expression[] { op1 };
        op = ExpStdOp.create("asSet", args);
        values = new Value[] { new IntegerValue(2), new IntegerValue(3)};
        assertEquals(
                     op.toString(),
                     new SetValue(TypeFactory.mkInteger(), values),
                     e.eval(op, state));
    }

    public void testSequenceOperations() throws ExpInvalidException {
        Expression[] args;
        Expression[] args1;
        ExpStdOp op;
        ExpStdOp op1;

        args1 = new Expression[] { new ExpConstInteger(2), new ExpConstInteger(3)};
        op1 = ExpStdOp.create("mkSequence", args1);
        args = new Expression[] { op1, new ExpConstInteger(1)};
        op = ExpStdOp.create("at", args);
        assertEquals(op.toString(), e.eval(op, state), new IntegerValue(2));

        args = new Expression[] { op1, new ExpConstInteger(2)};
        op = ExpStdOp.create("at", args);
        assertEquals(op.toString(), e.eval(op, state), new IntegerValue(3));

        args = new Expression[] { op1, new ExpConstInteger(0)};
        op = ExpStdOp.create("at", args);
        assertEquals(
                     op.toString(),
                     e.eval(op, state),
                     new UndefinedValue(TypeFactory.mkInteger()));

        args = new Expression[] { op1, new ExpConstInteger(3)};
        op = ExpStdOp.create("at", args);
        assertEquals(
                     op.toString(),
                     e.eval(op, state),
                     new UndefinedValue(TypeFactory.mkInteger()));
    }

    public void testLargeSetsAndSequences() throws ExpInvalidException {
        Expression[] args;
        Expression[] args1;
        ExpStdOp op;
        ExpStdOp op1;

        final int SET_SIZE = 5000;
        args1 = new Expression[SET_SIZE];
        for (int i = 0; i < SET_SIZE; i++)
            args1[i] = new ExpConstInteger(i);
        op1 = ExpStdOp.create("mkSet", args1);
        args = new Expression[] { op1 };
        op = ExpStdOp.create("size", args);
        assertEquals(
                     "mkSet(0.." + SET_SIZE + ").size",
                     new IntegerValue(SET_SIZE),
                     e.eval(op, state));

        args1 = new Expression[SET_SIZE];
        for (int i = 0; i < SET_SIZE; i++)
            args1[i] = new ExpConstInteger(i);
        op1 = ExpStdOp.create("mkSequence", args1);
        args = new Expression[] { op1 };
        op = ExpStdOp.create("size", args);
        assertEquals(
                     "mkSequence(0.." + SET_SIZE + ").size",
                     new IntegerValue(SET_SIZE),
                     e.eval(op, state));
    }
}
