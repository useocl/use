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

package org.tzi.use.autocompletion;

import junit.framework.TestCase;
import org.tzi.use.TestSystem;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.sys.MSystemException;

import java.util.*;


/**
 * Test auto completion suggestions.
 *
 * @author Till Aul
 * @see AutoCompletion
 */

public class AutoCompletionSuggestionTest extends TestCase {

    public void testSuggestionsCaseObjectNameStarted() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();
        AutoCompletion testee = testSystem.getAutoCompletion();

        Set<String> expectedResult = Set.of("j1", "j2", "j3", "j4", "j5", "j6", "j7", "j8");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("ob", true).suggestions));
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("true = ob", true).suggestions));
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("'5' = ob", true).suggestions));

        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("ob", false).suggestions));
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("true = ob", false).suggestions));
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("'5' = ob", false).suggestions));
    }

    public void testSuggestionsCaseAttributeNameStarted() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();
        AutoCompletion testee = testSystem.getAutoCompletion();

        Set<String> expectedResult = Set.of("tr_isMarried", "tr_age", "tr_size", "tr_name");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("obj1.at", false).suggestions));

        List<String> expectedResultList = List.of("tr_age", "tr_size", "tr_isMarried", "tr_name");
        assertEquals(expectedResultList, new LinkedList<>(testee.getSuggestions("5 = obj1.at", false).suggestions));
        assertEquals(expectedResultList, new LinkedList<>(testee.getSuggestions("5 = obj2.at", false).suggestions));

        expectedResult = Set.of("tr_hasOwner", "tr_isHappy", "tr_size");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("obj5.at", false).suggestions));

        expectedResultList = List.of("tr_size", "tr_hasOwner", "tr_isHappy");
        assertEquals(expectedResultList, new LinkedList<>(testee.getSuggestions("3.5 = obj5.at", false).suggestions));
    }

    public void testSuggestionsCaseOperationNameStarted() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();
        AutoCompletion testee = testSystem.getAutoCompletion();

        Set<String> expectedResult = Set.of("cludes(T2 element)", "cludesAll(Collection(T2) other)", "cluding(T2 element)", "tersection(Set(T2) other)", "tersection(Bag(T2) other)");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("Set{1,2,3}->in", true).suggestions));
    }

    public void testSuggestionsCaseObjects() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();
        AutoCompletion testee = testSystem.getAutoCompletion();


        Set<String> expectedResult = Set.of("attr_isMarried", "attr_age", "attr_size", "attr_name", "addToAge(Integer num)", "addToSize(Real num)", "compareToIsMarried(Boolean bool)");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("obj1.", true).suggestions));

        //expectedResult = Set.of("attr_age", "attr_size", "op1(Integer p1)", "op2(Real p1)");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("5 = obj1.", true).suggestions));

        //expectedResult = Set.of("attr_name");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("'5' = obj1.", true).suggestions));

        expectedResult = Set.of("attr_hasOwner", "attr_isHappy", "attr_size");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("obj5.", true).suggestions));

        //expectedResult = Set.of("attr_age", "attr_size", "op1(Integer p1)", "op2(Real p1)");
        expectedResult = Set.of("attr_isMarried", "attr_age", "attr_size", "attr_name", "addToAge(Integer num)", "addToSize(Real num)", "compareToIsMarried(Boolean bool)");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("6.2 = obj1.", true).suggestions));

        //expectedResult = Set.of("attr_isMarried", "operation3(Boolean p1)");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("false = obj1.", true).suggestions));
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("true = obj1.", true).suggestions));
    }

    public void testSuggestionsCaseCreateAndDestroy() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();
        AutoCompletion testee = testSystem.getAutoCompletion();

        Set<String> expectedResult = Set.of("Human", "Dog", "ownership_assoc", "ownership_assocclass");

        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!create obj3 :", false).suggestions));
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!create obj3:", false).suggestions));
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!create obj3 : ", false).suggestions));

        expectedResult = Set.of("obj1", "obj2", "obj3", "obj4", "obj5", "obj6", "obj7", "obj8");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!create obj3 : ownership_assoc between (", false).suggestions));
        expectedResult = Set.of("obj1", "obj2", "obj3", "obj4", "obj5", "obj6", "obj7", "obj8");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!create obj3 : ownership_assoc between(", false).suggestions));

        expectedResult = Set.of("obj2", "obj3", "obj4", "obj5", "obj6", "obj7", "obj8");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!create obj3 : ownership_assoc between (obj1,", false).suggestions));
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!create obj3 : ownership_assoc between (obj1, ", false).suggestions));

        expectedResult = Set.of("obj1", "obj2", "obj3", "obj4", "obj5", "obj6", "obj7", "obj8");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!destroy ", false).suggestions));

        expectedResult = Set.of("obj2", "obj3", "obj4", "obj5", "obj6", "obj7", "obj8");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!destroy obj1,", false).suggestions));
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!destroy obj1, ", false).suggestions));
    }

    public void testSuggestionsCaseInsertAndDelete() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();
        AutoCompletion testee = testSystem.getAutoCompletion();

        Set<String> expectedResult = Set.of("obj1", "obj2", "obj3", "obj4", "obj5", "obj6", "obj7", "obj8");

        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!insert(", false).suggestions));
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!insert (", false).suggestions));
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!insert( ", false).suggestions));
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!insert ( ", false).suggestions));

        expectedResult = Set.of("obj2", "obj3", "obj4", "obj5", "obj6", "obj7", "obj8");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!insert(obj1,", false).suggestions));
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!insert(obj1, ", false).suggestions));

        expectedResult = Set.of("ownership_assoc", "ownership_assocclass");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!insert(obj1, obj2) into ", false).suggestions));
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!insert(obj1,obj2) into ", false).suggestions));

        expectedResult = Set.of("obj1", "obj2", "obj3", "obj4", "obj5", "obj6", "obj7", "obj8");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!delete(", false).suggestions));
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!delete (", false).suggestions));
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!delete( ", false).suggestions));
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!delete ( ", false).suggestions));

        expectedResult = Set.of("obj2", "obj3", "obj4", "obj5", "obj6", "obj7", "obj8");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!delete(obj1,", false).suggestions));
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!delete(obj1, ", false).suggestions));

        expectedResult = Set.of("ownership_assoc", "ownership_assocclass");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!delete(obj1, obj2) from ", false).suggestions));
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!delete(obj1,obj2) from ", false).suggestions));
    }

    public void testSuggestionsCaseSet() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();
        AutoCompletion testee = testSystem.getAutoCompletion();

        Set<String> expectedResult = Set.of("obj1", "obj2", "obj3", "obj4", "obj5", "obj6", "obj7", "obj8");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!set ", false).suggestions));


        expectedResult = Set.of("attr_isMarried", "attr_age", "attr_size", "attr_name");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!set obj1.", false).suggestions));

        expectedResult = Set.of("attr_hasOwner", "attr_isHappy", "attr_size");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!set obj5.", false).suggestions));
    }

    public void testSuggestionsCaseOpenterAndOpexit() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();
        AutoCompletion testee = testSystem.getAutoCompletion();

        Set<String> expectedResult = Set.of("obj1", "obj2", "obj3", "obj4", "obj5", "obj6", "obj7", "obj8");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!openter ", false).suggestions));

        expectedResult = Set.of("1", "2", "3", "4", "5", "6", "7", "8");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!openter obj", false).suggestions));

        expectedResult = Set.of("addToAge", "addToSize", "compareToIsMarried");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!openter obj1 ", false).suggestions));

        expectedResult = Set.of("ToAge", "ToSize");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!openter obj1 add", false).suggestions));

        expectedResult = Set.of("ize");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!openter obj1 addToS", false).suggestions));
    }

    public void testSuggestionsCaseCheck() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();
        AutoCompletion testee = testSystem.getAutoCompletion();

        Set<String> expectedResult = Set.of("-v", "-d", "-a");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("check ", false).suggestions));
    }

    public void testSuggestionsCaseStep() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();
        AutoCompletion testee = testSystem.getAutoCompletion();

        Set<String> expectedResult = Set.of("on");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("step ", false).suggestions));
    }

    public void testSuggestionsCaseOpen() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();
        AutoCompletion testee = testSystem.getAutoCompletion();

        String path = "../use-core/src/main/resources/examples/Documentation/Demo";

        Set<String> expectedResult = Set.of("Classdiagram", "SplitCommands", "Demo.use");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!open " + path, false).suggestions));
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("!open " + path, false).suggestions));
    }

    public void testSuggestionsCaseInfo() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();
        AutoCompletion testee = testSystem.getAutoCompletion();

        Set<String> expectedResult = Set.of("class", "model", "state", "opstack", "prog", "vars");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("info ", false).suggestions));

        expectedResult = Set.of("Human", "Dog", "ownership_assocclass");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("info class ", false).suggestions));
    }

    public void testSuggestionCaseIterationExpressions() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();
        AutoCompletion testee = testSystem.getAutoCompletion();

        Set<String> expectedResult = Set.of("1", "2", "3", "4", "5", "6", "7", "8");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("Set{1,2,3}->forAll(obj)", true).suggestions));

        expectedResult = Set.of("attr_isMarried", "attr_age", "attr_size", "attr_name", "addToAge(Integer num)", "addToSize(Real num)", "compareToIsMarried(Boolean bool)");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("Set{1,2,3}->forAll(obj1.)", true).suggestions));

        expectedResult = Set.of("isMarried", "age", "size", "name");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("Set{1,2,3}->forAll(obj2.attr_)", true).suggestions));

        expectedResult = Set.of("Integer");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("Set{1,2,3}->forAll(e1: )", true).suggestions));

        expectedResult = Set.of("eger");
        assertEquals(expectedResult, new HashSet<>(testee.getSuggestions("Set{1,2,3}->forAll(obj: Int)", true).suggestions));
    }
}