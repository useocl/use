/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2010 Mark Richters, University of Bremen
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

package org.tzi.use;

import org.tzi.use.uml.mm.*;
import org.tzi.use.uml.ocl.expr.*;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.ocl.value.IntegerValue;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.MSystemState;
import org.tzi.use.uml.sys.soil.MVariableAssignmentStatement;
import org.tzi.use.autocompletion.AutoCompletion;
import org.tzi.use.util.soil.VariableEnvironment;

import java.util.Collections;
import java.util.List;

/**
 * Helper to setup a test system.
 *
 * @author Daniel Gent
 */
public class TestSystem {

    private MSystem fSystem;

    private AutoCompletion autoCompletion;

    public TestSystem() throws MSystemException, MInvalidModelException, ExpInvalidException {
        init();
    }

    public AutoCompletion getAutoCompletion() {
        return autoCompletion;
    }

    public MSystem getSystem() {
        return fSystem;
    }

    public MModel getModel() {
        return fSystem.model();
    }

    public MSystemState getState() {
        return fSystem.state();
    }

    public VariableEnvironment getVarEnv() {
        return fSystem.getVariableEnvironment();
    }


    private void init() throws MSystemException, MInvalidModelException, ExpInvalidException {
        ModelFactory factory = new ModelFactory();
        MModel model = factory.createModel("testModel");

        MClass c1 = factory.createClass("class1", false);
        model.addClass(c1);
        c1.addAttribute(factory.createAttribute("attribute1_1", TypeFactory.mkBoolean()));
        c1.addAttribute(factory.createAttribute("attribute1_2", TypeFactory.mkInteger()));
        c1.addAttribute(factory.createAttribute("attribute1_3", TypeFactory.mkReal()));
        c1.addAttribute(factory.createAttribute("attribute1_4", TypeFactory.mkString()));
        MOperation op1 = new MOperation("op1", new VarDeclList(new VarDecl("p1", TypeFactory.mkInteger())), TypeFactory.mkInteger());
        op1.setStatement(new MVariableAssignmentStatement("result", IntegerValue.valueOf(42)));
        c1.addOperation(op1);
        MOperation op2 = new MOperation("op2", new VarDeclList(new VarDecl("p1", TypeFactory.mkReal())), TypeFactory.mkInteger());
        op2.setStatement(new MVariableAssignmentStatement("result", IntegerValue.valueOf(40)));
        c1.addOperation(op2);
        MOperation op3 = new MOperation("operation3", new VarDeclList(new VarDecl("p1", TypeFactory.mkBoolean())), TypeFactory.mkBoolean());
        op3.setStatement(new MVariableAssignmentStatement("result", IntegerValue.valueOf(42)));
        c1.addOperation(op3);


        MClass c2 = factory.createClass("class2", false);
        model.addClass(c2);
        c2.addAttribute(factory.createAttribute("attribute2_1", TypeFactory.mkBoolean()));
        c2.addAttribute(factory.createAttribute("attribute2_2", TypeFactory.mkBoolean()));
        c2.addAttribute(factory.createAttribute("attribute2_3", TypeFactory.mkReal()));

        List<VarDecl> emptyQualifiers = Collections.emptyList();
        MAssociation a1 = factory.createAssociation("A1");
        a1.addAssociationEnd(
                factory.createAssociationEnd(
                        c1,
                        "E1",
                        new MMultiplicity(0, 1),
                        MAggregationKind.NONE,
                        false, emptyQualifiers));

        a1.addAssociationEnd(
                factory.createAssociationEnd(
                        c2,
                        "E2",
                        new MMultiplicity(0, 1),
                        MAggregationKind.NONE,
                        false, emptyQualifiers));

        model.addAssociation(a1);

        MAssociationClass ac1 = factory.createAssociationClass("AC1", false);
        ac1.addAssociationEnd(
                factory.createAssociationEnd(
                        c1,
                        "role1",
                        new MMultiplicity(0, 1),
                        MAggregationKind.NONE,
                        false, emptyQualifiers));

        ac1.addAssociationEnd(
                factory.createAssociationEnd(
                        c2,
                        "role2",
                        new MMultiplicity(0, 1),
                        MAggregationKind.NONE,
                        false, emptyQualifiers));

        model.addClass(ac1);
        model.addAssociation(ac1);

        fSystem = new MSystem(model);

        autoCompletion = new AutoCompletion(model, fSystem.state());
        initObjectsAndLinks(autoCompletion, c1, c2);
    }

    private void initObjectsAndLinks(AutoCompletion autoCompletion, MClass c1, MClass c2) throws MSystemException {
        MSystemState state = fSystem.state();
        VariableEnvironment varEnv = fSystem.getVariableEnvironment();

        MClass C1 = getModel().getClass("class1");
        MClass C2 = getModel().getClass("class2");

        varEnv.assign("v", IntegerValue.valueOf(42));

        state.createObject(c1, "obj1");
        state.createObject(c1, "obj2");
        state.createObject(c1, "obj3");
        state.createObject(c1, "obj4");
        state.createObject(c2, "obj5");
        state.createObject(c2, "obj6");
        state.createObject(c2, "obj7");
        state.createObject(c2, "obj8");
    }
}
