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
import org.tzi.use.autocompletion.parserResultTypes.ocl.*;
import org.tzi.use.autocompletion.parserResultTypes.useCommands.*;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.ocl.expr.ExpInvalidException;
import org.tzi.use.uml.sys.MSystemException;

/**
 * Test auto completion parser.
 *
 * @author Till Aul
 * @see AutoCompletionParser
 */

public class AutoCompletionParserTest extends TestCase {
    public void testParserCaseObjectsIntegers() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();

        ParserResult expectedResult = new ParserResult();
        expectedResult.foundTypes.add("object");
        expectedResult.foundValues.put("objectName", "obj1");
        expectedResult.result = new ResultTypeOCLObjects("obj1", null);

        String input = "obj1.";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        input = "obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        input = "null = obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());


        expectedResult.foundTypes.add(0, "Integer");
        expectedResult.foundValues.put("operationType", "Integer");
        expectedResult.result = new ResultTypeOCLObjects("obj1", "Integer");
        //whitespaces
        input = "5 = obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "5=obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "5= obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "5 =obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        //operations
        //< and >
        input = "3 > obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "3 >= obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "3 < obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "3 <= obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseObjectsBooleans() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();

        ParserResult expectedResult = new ParserResult();
        expectedResult.foundTypes.add("Boolean");
        expectedResult.foundTypes.add("object");
        expectedResult.foundValues.put("objectName", "obj1");
        expectedResult.foundValues.put("operationType", "Boolean");
        expectedResult.result = new ResultTypeOCLObjects("obj1", "Boolean");

        //operations
        //< and >
        String input = "true > obj1.";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "false >= obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "true < obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "false <= obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseObjectsReals() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();

        ParserResult expectedResult = new ParserResult();
        expectedResult.foundTypes.add("Real");
        expectedResult.foundTypes.add("object");
        expectedResult.foundValues.put("objectName", "obj1");
        expectedResult.foundValues.put("operationType", "Real");
        expectedResult.result = new ResultTypeOCLObjects("obj1", "Real");


        //whitespaces
        String input = "5.2 = obj1.";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "5.2=obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "5.2= obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "5.2 =obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        //operations
        //< and >
        input = "5.2 > obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "5.2 >= obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "5.2 < obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "5.2 <= obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseObjectsStrings() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();

        ParserResult expectedResult = new ParserResult();
        expectedResult.foundTypes.add("String");
        expectedResult.foundTypes.add("object");
        expectedResult.foundValues.put("objectName", "obj1");
        expectedResult.foundValues.put("operationType", "String");
        expectedResult.result = new ResultTypeOCLObjects("obj1", "String");

        //whi
        String input = "'5' = obj1.";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "'5'=obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "'5'= obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "'5' =obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        //operations
        //< and >
        input = "'3' > obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "'3' >= obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "'3' < obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "'3' <= obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseObjectPrefix() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();
        ParserResult expectedResult = new ParserResult();

        expectedResult.foundTypes.add("objectName");
        expectedResult.foundValues.put("objectPrefix", "obj");
        expectedResult.foundTypes.add(0, "String");
        expectedResult.foundValues.put("operationType", "String");
        expectedResult.result = new ResultTypeOCLObjectPrefix("obj");

        String input = "'5' = obj";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());


        expectedResult.foundValues.remove("operationType");
        expectedResult.foundTypes.remove(0);
        expectedResult.result = new ResultTypeOCLObjectPrefix("obj");

        input = "obj";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        expectedResult.foundTypes.add(0, "Boolean");
        expectedResult.foundValues.put("operationType", "Boolean");
        expectedResult.result = new ResultTypeOCLObjectPrefix("obj");
        input = "false = obj";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseAttributePrefix() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();
        ParserResult expectedResult = new ParserResult();

        expectedResult.foundTypes.add("object");
        expectedResult.foundTypes.add("objectName");
        expectedResult.foundValues.put("objectPrefix", "attr");
        expectedResult.foundValues.put("objectName", "obj");
        expectedResult.result = new ResultTypeOCLAttributePrefix("obj", null, "attr");

        String input = "obj.attr";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());


        expectedResult.foundTypes.add(0, "String");
        expectedResult.foundValues.put("operationType", "String");
        expectedResult.result = new ResultTypeOCLAttributePrefix("obj", "String", "attr");

        input = "'5' = obj.attr";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());


        expectedResult.foundTypes.set(0, "Boolean");
        expectedResult.foundValues.put("operationType", "Boolean");
        expectedResult.result = new ResultTypeOCLAttributePrefix("obj", "Boolean", "attr");

        input = "false = obj.attr";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseCollectionSet() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();
        ParserResult expectedResult = new ParserResult();

        expectedResult.foundTypes.add("set");
        expectedResult.foundValues.put("elemType", "Integer");
        expectedResult.result = new ResultTypeOCLCollectionsDefault(null, "set");

        String input = "Set{1,2,3}->";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());


        expectedResult.foundTypes.add(0, "String");
        expectedResult.foundValues.put("operationType", "String");
        //whitespaces
        input = "'5' = Set{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "'5'=Set{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "'5'= Set{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "'5' =Set{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());


        expectedResult.foundTypes.set(0, "Integer");
        expectedResult.foundValues.put("operationType", "Integer");
        //operations
        //< and >
        input = "3 > Set{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "3 >= Set{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "3 < Set{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "3 <= Set{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseCollectionBag() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();

        ParserResult expectedResult = new ParserResult();
        expectedResult.foundTypes.add("bag");
        expectedResult.foundValues.put("elemType", "Integer");
        expectedResult.result = new ResultTypeOCLCollectionsDefault(null, "bag");

        String input = "Bag{1,2,3}->";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        expectedResult.foundTypes.add(0, "String");
        expectedResult.foundValues.put("operationType", "String");
        //whitespaces
        input = "'5' = Bag{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "'5'=Bag{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "'5'= Bag{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "'5' =Bag{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        expectedResult.foundTypes.set(0, "Integer");
        expectedResult.foundValues.put("operationType", "Integer");
        //operations
        //< and >
        input = "3 > Bag{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "3 >= Bag{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "3 < Bag{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "3 <= Bag{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseCollectionSequence() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();

        ParserResult expectedResult = new ParserResult();
        expectedResult.foundTypes.add("sequence");
        expectedResult.foundValues.put("elemType", "Integer");
        expectedResult.result = new ResultTypeOCLCollectionsDefault(null, "sequence");

        String input = "Sequence{1,2,3}->";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        expectedResult.foundTypes.add(0, "String");
        expectedResult.foundValues.put("operationType", "String");
        //whitespaces
        input = "'5' = Sequence{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "'5'=Sequence{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "'5'= Sequence{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "'5' =Sequence{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        expectedResult.foundTypes.set(0, "Integer");
        expectedResult.foundValues.put("operationType", "Integer");
        //operations
        //< and >
        input = "3 > Sequence{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "3 >= Sequence{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "3 < Sequence{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "3 <= Sequence{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseCollectionOrderedSet() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();

        ParserResult expectedResult = new ParserResult();
        expectedResult.foundTypes.add("orderedSet");
        expectedResult.foundValues.put("elemType", "Integer");
        expectedResult.result = new ResultTypeOCLCollectionsDefault(null, "orderedSet");

        String input = "OrderedSet{1,2,3}->";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        expectedResult.foundTypes.add(0, "String");
        expectedResult.foundValues.put("operationType", "String");
        //whitespaces
        input = "'5' = OrderedSet{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "'5'=OrderedSet{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "'5'= OrderedSet{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "'5' =OrderedSet{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        expectedResult.foundTypes.set(0, "Integer");
        expectedResult.foundValues.put("operationType", "Integer");
        //operations
        //< and >
        input = "3 > OrderedSet{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "3 >= OrderedSet{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "3 < OrderedSet{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "3 <= OrderedSet{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseCollectionNegativeTest() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();

        ParserResult expectedResult = new ParserResult();
        String input = "OrderedSet{1,2,3}.";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "Bag{1,2,3}.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "Set{1,2,3}.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "Sequence{1,2,3}.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseObjectNegativeTest() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();
        ParserResult expectedResult = new ParserResult();

        String input = "obj1->";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseUseCommandsOCLExpression() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();

        ParserResult expectedResult = new ParserResult();
        expectedResult.foundTypes.add("orderedSet");
        expectedResult.foundValues.put("elemType", "Integer");
        expectedResult.result = new ResultTypeOCLCollectionsDefault(null, "orderedSet");

        String input = "? OrderedSet{1,2,3} ->";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "?OrderedSet{1,2,3} ->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "?? OrderedSet{1,2,3} ->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "??OrderedSet{1,2,3} ->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        expectedResult.foundTypes.add(0, "Integer");
        expectedResult.foundValues.put("operationType", "Integer");
        input = "3 > OrderedSet{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        expectedResult.clearAll();
        expectedResult.foundTypes.add("bag");
        expectedResult.foundValues.put("elemType", "Integer");
        input = "? Bag{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        expectedResult.foundTypes.set(0, "set");
        input = "? Set{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        expectedResult.foundTypes.set(0, "sequence");
        input = "? Sequence{1,2,3}->";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        expectedResult.foundValues.clear();
        expectedResult.foundTypes.add("object");
        expectedResult.foundValues.put("objectName", "obj1");
        expectedResult.foundTypes.set(0, "Integer");
        expectedResult.foundValues.put("operationType", "Integer");
        expectedResult.result = new ResultTypeOCLObjects("obj1", "Integer");

        input = "5 = obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        expectedResult.foundTypes.set(0, "String");
        expectedResult.foundValues.put("operationType", "String");
        expectedResult.result = new ResultTypeOCLObjects("obj1", "String");
        input = "'5' = obj1.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseUseCommandsCreateDestroy() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();

        ParserResult expectedResult = new ParserResult();
        expectedResult.foundTypes.add("USE_create");
        expectedResult.result = new ResultTypeUseCreateDefault();

        String input = "!create greenApple : ";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "!create greenApple, redApple: ";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "!create greenApple, redApple, yellowApple : ";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        //whitespaces
        input = "! create greenApple,redApple,yellowApple : ";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "!create greenApple,redApple,yellowApple : ";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "!create greenApple,redApple,yellowApple: ";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "!create greenApple,redApple,yellowApple :";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "!create greenApple,redApple,yellowApple:";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        expectedResult.foundValues.put("subtype", "association");
        expectedResult.foundValues.put("associationName", "FruitSalad");
        expectedResult.result = new ResultTypeUseCreateAssociation(null, "FruitSalad");
        input = "!create smallSalad : FruitSalad between (";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        //whitespaces
        input = "! create smallSalad: FruitSalad between (";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "!create smallSalad: FruitSalad between (";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "!create smallSalad :FruitSalad between (";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
        input = "!create smallSalad:FruitSalad between (";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());


        expectedResult.foundTypes.set(0, "USE_destroy");
        expectedResult.foundValues.remove("subtype");
        expectedResult.foundValues.remove("associationName");
        expectedResult.result = new ResultTypeUseDestroy(null);

        input = "!destroy ";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseUseCommandsInsertDelete() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();

        ParserResult expectedResult = new ParserResult();
        expectedResult.foundTypes.add("USE_insert");
        expectedResult.foundValues.put("subtype", "insertPartObjects");
        expectedResult.foundValues.put("objectNames", null);
        expectedResult.result = new ResultTypeUseInsertObjects(null);

        String input = "!insert (";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());


        expectedResult.foundValues.put("subtype", "insertPartAssociation");
        expectedResult.foundValues.remove("objectNames");
        expectedResult.result = new ResultTypeUseInsertAssociation();

        input = "!insert (redApple, yellowApple, bigLemon) into ";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());


        expectedResult.foundTypes.set(0, "USE_delete");
        expectedResult.foundValues.put("subtype", "deletePartAssociation");
        expectedResult.result = new ResultTypeUseDeleteAssociation();

        input = "!delete (redApple, yellowApple, bigLemon) from ";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseUseCommandsSet() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();

        ParserResult expectedResult = new ParserResult();
        expectedResult.foundTypes.add("USE_set");
        expectedResult.foundValues.put("subtype", "setPartObject");
        expectedResult.result = new ResultTypeUseSetObject();

        String input = "!set ";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());


        expectedResult.foundValues.put("subtype", "setPartAttr");
        expectedResult.foundValues.put("objectName", "redApple");
        expectedResult.result = new ResultTypeUseSetAttr("redApple");

        input = "!set redApple.";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseUseCommandsOpEnter() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();

        ParserResult expectedResult = new ParserResult();
        expectedResult.foundTypes.add("USE_openter");
        expectedResult.foundValues.put("subtype", "openterPartObject");
        expectedResult.result = new ResultTypeUseOpenterObject(null);

        String input = "!openter ";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        ParserResult r = parser.getResult();

        assertEquals(expectedResult, parser.getResult());

        expectedResult.foundValues.put("subtype", "openterPartOperation");
        expectedResult.foundValues.put("objectName", "banana");
        expectedResult.foundValues.put("operationPrefix", null);
        expectedResult.result = new ResultTypeUseOpenterOperation("banana", null);

        input = "!openter banana ";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseUseCommandsCheck() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();

        ParserResult expectedResult = new ParserResult();
        expectedResult.foundTypes.add("USE_check");
        expectedResult.result = new ResultTypeUseCheck();
        String input = "check ";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseUseCommandsStep() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();

        ParserResult expectedResult = new ParserResult();
        expectedResult.foundTypes.add("USE_step");
        expectedResult.result = new ResultTypeUseStep();
        String input = "step ";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseUseCommandsOpen() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();

        ParserResult expectedResult = new ParserResult();
        expectedResult.foundTypes.add("USE_open");
        expectedResult.foundValues.put("path", "../");
        expectedResult.result = new ResultTypeUseOpen("../");
        String input = "!open ../";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        expectedResult.foundValues.put("path", "../examples/Documentation/Demo/");
        expectedResult.result = new ResultTypeUseOpen("../examples/Documentation/Demo/");
        input = "!open ../examples/Documentation/Demo/";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseUseCommandsInfo() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();

        ParserResult expectedResult = new ParserResult();
        expectedResult.foundTypes.add("USE_info");
        expectedResult.result = new ResultTypeUseInfoDefault();
        String input = "info ";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());

        expectedResult.foundValues.put("subtype", "infoClass");
        expectedResult.result = new ResultTypeUseInfoClass();
        input = "info class ";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseIterationExpressions() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();

        ParserResult expectedResult = new ParserResult();

        expectedResult.foundTypes.add("set");
        expectedResult.foundTypes.add("iterationExpression");
        expectedResult.foundValues.put("elemType", "Integer");
        expectedResult.foundValues.put("subtype", "endsWithPipeAndContainsColon");
        expectedResult.foundValues.put("identifier", "e1");
        expectedResult.foundValues.put("className", "class1");
        expectedResult.foundValues.put("operationName", "forAll");
        expectedResult.result = new ResultTypeOCLCollectionsEndsWithPipeAndContainsColon("class1");

        String input = "Set{1,2,3}->forAll(e1: class1 | ";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());


        expectedResult.clearAll();
        expectedResult.foundTypes.add("set");
        expectedResult.foundTypes.add("objectName");
        expectedResult.foundValues.put("elemType", "Integer");
        expectedResult.foundValues.put("objectPrefix", "obj");
        expectedResult.foundValues.put("operationName", "forAll");
        expectedResult.result = new ResultTypeOCLObjectPrefix("obj");

        input = "Set{1,2,3}->forAll(obj)";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());


        expectedResult.clearAll();
        expectedResult.foundTypes.add("set");
        expectedResult.foundTypes.add("object");
        expectedResult.foundValues.put("elemType", "Integer");
        expectedResult.foundValues.put("objectName", "obj1");
        expectedResult.foundValues.put("operationName", "forAll");
        expectedResult.result = new ResultTypeOCLObjects("obj1", null);

        input = "Set{1,2,3}->forAll(obj1.)";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());


        expectedResult.clearAll();
        expectedResult.foundTypes.add("set");
        expectedResult.foundTypes.add("iterationExpression");
        expectedResult.foundValues.put("elemType", "Integer");
        expectedResult.foundValues.put("identifier", "e1");
        expectedResult.foundValues.put("subtype", "containsColon");
        expectedResult.foundValues.put("operationName", "forAll");
        expectedResult.result = new ResultTypeOCLCollectionsContainsColon(null, "Integer");

        input = "Set{1,2,3}->forAll(e1: ";
        parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }

    public void testParserCaseIterationExpressionsNegativeTests() throws MSystemException, ExpInvalidException, MInvalidModelException {
        TestSystem testSystem = new TestSystem();

        ParserResult expectedResult = new ParserResult();
        String input = "Set{1,2,3}->select(e1, )";
        AutoCompletionParser parser = new AutoCompletionParser(testSystem.getModel(), input);
        assertEquals(expectedResult, parser.getResult());
    }
}