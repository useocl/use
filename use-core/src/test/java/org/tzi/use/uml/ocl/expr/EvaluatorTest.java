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
import java.util.ArrayList;

import junit.framework.TestCase;

import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.util.collections.Queue;

/**
 * Test Evaluator class.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */

public class EvaluatorTest extends TestCase {

    public void test1() throws ExpInvalidException, InterruptedException {
        MSystemState systemState =
            new MSystem(new ModelFactory().createModel("Test")).state();

        // env.printHeader("testing evaluator...");
        int numExpr = 100; // create expressions
        int factor = 10; // higher values cause longer eval times per expression
        int numThreads = 4;

        ArrayList<Expression> expList = new ArrayList<Expression>(numExpr);
        for (int i = 0; i < numExpr; i++) {
            // Sequence { 1..i * factor }->iterate(e; acc : Integer= 0 | acc + e);
            Expression arg0 = new ExpConstInteger(1);
            Expression arg1 = new ExpConstInteger((numExpr - i) * factor);
            Expression args = new ExpRange(arg0, arg1);
            
            Expression rangeExp = new ExpSequenceLiteral(new Expression[]{args});

            // create iterate
            Expression[] args2 =
                new Expression[] {
                    new ExpVariable("acc", TypeFactory.mkInteger()),
                    new ExpVariable("e", TypeFactory.mkInteger())};
            Expression queryExp = ExpStdOp.create("+", args2);

            Expression exp =
                new ExpIterate(
                               new VarDeclList(new VarDecl("e", TypeFactory.mkInteger())),
                               new VarInitializer(
                                                  "acc",
                                                  TypeFactory.mkInteger(),
                                                  new ExpConstInteger(0)),
                               rangeExp,
                               queryExp);

            expList.add(exp);
        }

        Evaluator evaluator = new Evaluator();
        Queue q = evaluator.evalList(numThreads, expList, systemState);
        for (int i = 0; i < numExpr; i++) {
            Value v = (Value) q.get();
            int s1 = (numExpr - i) * factor;
            int s2 = s1 * (s1 + 1) / 2;
            assertEquals("Sum 1.." + s1, IntegerValue.valueOf(s2), v);
        }

        // env.printHeader("Time: " + (System.currentTimeMillis() - t0) + " ms");

        //env.printHeader("testing done...");
    }
}
