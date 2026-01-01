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
package org.tzi.use.uml.mm;

import org.junit.Test;
import org.tzi.use.api.UseApiException;
import org.tzi.use.api.UseModelApi;
import org.tzi.use.uml.api.IEnumType;
import org.tzi.use.uml.mm.commonbehavior.communications.MSignal;
import org.tzi.use.uml.ocl.type.EnumType;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The Test class for {@code MImportedModel} tests if imported elements are retrieved
 * correctly by the root model based on the filter rules (wildcard or symbol table).
 * @author Matthias Marschalk
 */
public class MImportedModelTest {

    // Classes
    @Test
    public void testGetClassesWithWildcard() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClasses();
        MImportedModel importedModel = new MImportedModel(model, true, null);
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MClass> classes = rootModel.getClassesIncludingImports();
            assertEquals(3, classes.size());
            assertTrue(classes.contains(rootModel.getClass("RootClass")));
            assertTrue(classes.contains(model.getClass("Person")));
            assertTrue(classes.contains(model.getClass("Company")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetClassesWithSymbols() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClasses();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Person"));
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MClass> classes = rootModel.getClassesIncludingImports();
            assertEquals(2, classes.size());
            assertTrue(classes.contains(rootModel.getClass("RootClass")));
            assertTrue(classes.contains(model.getClass("Person")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetClassesWithImportableAssociationClass() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassAndAssocClass();
        MImportedModel importedModel = new MImportedModel(model, true, null);
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MClass> classes = rootModel.getClassesIncludingImports();
            assertEquals(4, classes.size());
            assertTrue(classes.contains(rootModel.getClass("RootClass")));
            assertTrue(classes.contains(model.getClass("Person")));
            assertTrue(classes.contains(model.getClass("Company")));
            assertTrue(classes.contains(model.getClass("Job")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetClassesWithNotImportableAssociationClass() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassAndAssocClass();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Person", "Job"));
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MClass> classes = rootModel.getClassesIncludingImports();
            assertEquals(2, classes.size());
            assertTrue(classes.contains(rootModel.getClass("RootClass")));
            assertTrue(classes.contains(model.getClass("Person")));
            assertFalse(classes.contains(model.getClass("Job")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetClassesWithQualifiedClass() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassAndAssocClass();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("PersonCompany#Person", "Job"));
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MClass> classes = rootModel.getClassesIncludingImports();
            assertEquals(2, classes.size());
            assertTrue(classes.contains(rootModel.getClass("RootClass")));
            assertTrue(classes.contains(rootModel.getClass("PersonCompany#Person")));
            assertFalse(classes.contains(model.getClass("Job")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetClassWithWildcard() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClasses();
        MImportedModel importedModel = new MImportedModel(model, true, null);
        try {
            rootModel.addImportedModel(importedModel);
            assertEquals(model.getClass("Person"), rootModel.getClass("Person"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetClassWithSymbols() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClasses();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Company"));
        try {
            rootModel.addImportedModel(importedModel);
            assertEquals(model.getClass("Company"), rootModel.getClass("Company"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetClassNotImported() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClasses();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Company"));
        try {
            rootModel.addImportedModel(importedModel);
            assertNull(rootModel.getClass("Person"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetClassWithImportableAssociationClass() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassAndAssocClass();
        MImportedModel importedModel = new MImportedModel(model, true, null);
        try {
            rootModel.addImportedModel(importedModel);
            assertEquals(model.getClass("Job"), rootModel.getClass("Job"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetClassWithNotImportableAssociationClass() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassAndAssocClass();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Person", "Job"));
        try {
            rootModel.addImportedModel(importedModel);
            assertNotNull(model.getClass("Job"));
            assertNull(rootModel.getClass("Job"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetClassWithQualifiedClass() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassAndAssocClass();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("PersonCompany#Person", "Job"));
        try {
            rootModel.addImportedModel(importedModel);
            assertEquals(rootModel.getClass("PersonCompany#Person"), model.getClass("Person"));
            assertNull(rootModel.getClass("Person"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testResolveQualifiedClass() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClasses();
        MModel transitiveModel = createTransitiveImportedModel();
        MImportedModel transitiveImportedModel = new MImportedModel(transitiveModel, true, null);
        try {
            model.addImportedModel(transitiveImportedModel);
            MImportedModel importedModel = new MImportedModel(model, false, Set.of("Company"));
            rootModel.addImportedModel(importedModel);
            assertEquals(transitiveModel.getClass("TransitiveClass"),
                    rootModel.getClass("TransitiveModel#TransitiveClass"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    // DataTypes
    @Test
    public void testGetDataTypesWithWildcard() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithDataType();
        MImportedModel importedModel = new MImportedModel(model, true, null);
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MDataType> dataTypes = rootModel.getDataTypesIncludingImports();
            assertEquals(3, dataTypes.size());
            assertTrue(dataTypes.contains(rootModel.getDataType("RootDataType")));
            assertTrue(dataTypes.contains(model.getDataType("Date")));
            assertTrue(dataTypes.contains(model.getDataType("Time")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetDataTypesWithSymbols() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithDataType();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Date"));
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MDataType> dataTypes = rootModel.getDataTypesIncludingImports();
            assertEquals(2, dataTypes.size());
            assertTrue(dataTypes.contains(rootModel.getDataType("RootDataType")));
            assertTrue(dataTypes.contains(model.getDataType("Date")));
            assertFalse(dataTypes.contains(model.getDataType("Time")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetDataTypesWithQualifiedDataType() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithDataType();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Date", "Dates#Time"));
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MDataType> dataTypes = rootModel.getDataTypesIncludingImports();
            assertEquals(3, dataTypes.size());
            assertTrue(dataTypes.contains(rootModel.getDataType("RootDataType")));
            assertTrue(dataTypes.contains(model.getDataType("Date")));
            assertTrue(dataTypes.contains(rootModel.getDataType("Dates#Time")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetDataTypeWithWildcard() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithDataType();
        MImportedModel importedModel = new MImportedModel(model, true, null);
        try {
            rootModel.addImportedModel(importedModel);
            assertEquals(model.getDataType("Date"), rootModel.getDataType("Date"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetDataTypeWithSymbols() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithDataType();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Date"));
        try {
            rootModel.addImportedModel(importedModel);
            assertEquals(model.getDataType("Date"), rootModel.getDataType("Date"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetDataTypeNotImported() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithDataType();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Time"));
        try {
            rootModel.addImportedModel(importedModel);
            assertNull(rootModel.getDataType("Date"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testResolveQualifiedDataType() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClasses();
        MModel transitiveModel = createTransitiveImportedModel();
        MImportedModel transitiveImportedModel = new MImportedModel(transitiveModel, true, null);
        try {
            model.addImportedModel(transitiveImportedModel);
            MImportedModel importedModel = new MImportedModel(model, false, Set.of("Company"));
            rootModel.addImportedModel(importedModel);
            assertEquals(transitiveModel.getDataType("TransitiveDataType"),
                    rootModel.getDataType("TransitiveModel#TransitiveDataType"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    // Classifiers
    @Test
    public void testGetClassifiersWithWildcard() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassesAndDataType();
        MImportedModel importedModel = new MImportedModel(model, true, null);
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MClassifier> classifiers = rootModel.getClassifiersIncludingImports();
            assertEquals(5, classifiers.size());
            assertTrue(classifiers.contains(rootModel.getClass("RootClass")));
            assertTrue(classifiers.contains(rootModel.getDataType("RootDataType")));
            assertTrue(classifiers.contains(model.getClass("Person")));
            assertTrue(classifiers.contains(model.getClass("Company")));
            assertTrue(classifiers.contains(model.getDataType("Date")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetClassifiersWithSymbols() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassesAndDataType();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Person", "Date"));
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MClassifier> classifiers = rootModel.getClassifiersIncludingImports();
            assertEquals(4, classifiers.size());
            assertTrue(classifiers.contains(rootModel.getClass("RootClass")));
            assertTrue(classifiers.contains(rootModel.getDataType("RootDataType")));
            assertTrue(classifiers.contains(model.getClass("Person")));
            assertTrue(classifiers.contains(model.getDataType("Date")));
            assertFalse(classifiers.contains(model.getClass("Company")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetClassifierWithWildcard() {
        // Tests getClassifier on root model, since the method calls getClass, getDataType, etc. which also check
        // imported models
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassesAndDataType();
        MImportedModel importedModel = new MImportedModel(model, true, null);
        try {
            rootModel.addImportedModel(importedModel);
            assertEquals(model.getClassifier("Person"), rootModel.getClass("Person"));
            assertEquals(model.getClassifier("Date"), rootModel.getDataType("Date"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetClassifierWithSymbols() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassesAndDataType();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Person", "Date"));
        try {
            rootModel.addImportedModel(importedModel);
            assertEquals(model.getClass("Person"), rootModel.getClass("Person"));
            assertEquals(model.getClassifier("Date"), rootModel.getDataType("Date"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetClassifierNotImported() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassesAndDataType();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Person", "Date"));
        try {
            rootModel.addImportedModel(importedModel);
            assertNull(rootModel.getClassifier("Company"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetClassifierQualified() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassesAndDataType();
        MModel transitiveModel = createTransitiveImportedModel();
        MImportedModel transitiveImportedModel = new MImportedModel(transitiveModel, true, null);
        try {
            model.addImportedModel(transitiveImportedModel);
            MImportedModel importedModel = new MImportedModel(model, false, Set.of("Company"));
            rootModel.addImportedModel(importedModel);
            assertEquals(transitiveModel.getClassifier("TransitiveModel#TransitiveClass"),
                    rootModel.getClassifier("TransitiveClass")
                    );
            assertEquals(transitiveModel.getClassifier("TransitiveModel#TransitiveDataType"),
                    rootModel.getClassifier("TransitiveDataType")
                    );
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    // EnumTypes
    @Test
    public void testGetEnumTypesWithWildcard() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithEnum();
        MImportedModel importedModel = new MImportedModel(model, true, null);
        try {
            rootModel.addImportedModel(importedModel);
            Set<IEnumType> enums = rootModel.getEnumTypesIncludingImports();
            assertEquals(3, enums.size());
            assertTrue(enums.contains(rootModel.enumType("RootEnum")));
            assertTrue(enums.contains(rootModel.enumType("colors")));
            assertTrue(enums.contains(rootModel.enumType("colors2")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetEnumTypesWithSymbols() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithEnum();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("colors"));
        try {
            rootModel.addImportedModel(importedModel);
            Set<IEnumType> enums = rootModel.getEnumTypesIncludingImports();
            assertEquals(2, enums.size());
            assertTrue(enums.contains(rootModel.enumType("RootEnum")));
            assertTrue(enums.contains(rootModel.enumType("colors")));
            assertFalse(enums.contains(rootModel.enumType("colors2")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetEnumTypesWithQualifiedEnumType() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithEnum();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Color#colors"));
        try {
            rootModel.addImportedModel(importedModel);
            Set<IEnumType> enums = rootModel.getEnumTypesIncludingImports();
            assertEquals(2, enums.size());
            assertTrue(enums.contains(rootModel.enumType("RootEnum")));
            assertTrue(enums.contains(rootModel.enumType("Color#colors")));
            assertFalse(enums.contains(rootModel.enumType("colors2")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testResolveQualifiedEnumType() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClasses();
        MModel transitiveModel = createTransitiveImportedModel();
        MImportedModel transitiveImportedModel = new MImportedModel(transitiveModel, true, null);
        try {
            model.addImportedModel(transitiveImportedModel);
            MImportedModel importedModel = new MImportedModel(model, false, Set.of("TransitiveEnum"));
            rootModel.addImportedModel(importedModel);
            assertEquals(transitiveModel.getDataType("TransitiveEnum"),
                    rootModel.getDataType("TransitiveModel#TransitiveEnum"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    // Associations
    @Test
    public void testGetAssociationsWithWildcard() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassAndAssocs();
        MImportedModel importedModel = new MImportedModel(model, true, null);
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MAssociation> associations = rootModel.getAssociationsIncludingImports();
            assertEquals(3, associations.size());
            assertTrue(associations.contains(rootModel.getAssociation("RootAssociation")));
            assertTrue(associations.contains(model.getAssociation("Job")));
            assertTrue(associations.contains(model.getAssociation("isBoss")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetAssociationsWithSymbols() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassAndAssocs();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Job", "Person", "Company"));
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MAssociation> associations = rootModel.getAssociationsIncludingImports();
            assertEquals(2, associations.size());
            assertTrue(associations.contains(rootModel.getAssociation("RootAssociation")));
            assertTrue(associations.contains(model.getAssociation("Job")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetAssociationsNotImported() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassAndAssocs();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Job", "Person", "Company"));
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MAssociation> associations = rootModel.getAssociationsIncludingImports();
            assertEquals(2, associations.size());
            assertFalse(associations.contains(model.getAssociation("isBoss")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetAssociationsEndsNotImported() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassAndAssocs();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Job", "Person"));
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MAssociation> associations = rootModel.getAssociationsIncludingImports();
            assertEquals(1, associations.size());
            assertFalse(associations.contains(model.getAssociation("Job")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetAssociationsEndNotLocalToModel() {
        MModel rootModel = createRootModel();
        MModel model = createModelWithImportedAssociationEnd();
        MImportedModel importedModel = new MImportedModel(model, true, null);
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MAssociation> associations = rootModel.getAssociationsIncludingImports();
            assertEquals(3, associations.size());
            assertNotNull(model.getAssociation("TransitiveEnds"));
            assertNull(rootModel.getAssociation("TransitiveEnds"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetAssociationWithWildcard() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassAndAssocs();
        MImportedModel importedModel = new MImportedModel(model, true, null);
        try {
            rootModel.addImportedModel(importedModel);
            assertEquals(model.getAssociation("Job"), rootModel.getAssociation("Job"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetAssociationWithSymbols() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassAndAssocs();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Job", "Person", "Company"));
        try {
            rootModel.addImportedModel(importedModel);
            assertEquals(model.getAssociation("Job"), rootModel.getAssociation("Job"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetAssociationNotImported() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassAndAssocs();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Job", "Person", "Company"));
        try {
            rootModel.addImportedModel(importedModel);
            assertNotNull(model.getAssociation("isBoss"));
            assertNull(rootModel.getAssociation("isBoss"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetAssociationEndsNotImported() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassAndAssocs();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Job", "Person"));
        try {
            rootModel.addImportedModel(importedModel);
            assertNotNull(model.getAssociation("Job"));
            assertNull(rootModel.getAssociation("Job"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetAssociationEndNotLocalToModel() {
        MModel rootModel = createRootModel();
        MModel model = createModelWithImportedAssociationEnd();
        MImportedModel importedModel = new MImportedModel(model, true, null);
        try {
            rootModel.addImportedModel(importedModel);
            assertNotNull(model.getAssociation("TransitiveEnds"));
            assertNull(rootModel.getAssociation("TransitiveEnds"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    // AssociationClass
    @Test
    public void testGetAssociationClassWithWildcard() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassAndAssocClass();
        MImportedModel importedModel = new MImportedModel(model, true, null);
        try {
            rootModel.addImportedModel(importedModel);
            assertEquals(model.getAssociationClass("Job"), rootModel.getAssociationClass("Job"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetClassInvariantsWithWildcard() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithInvariant();
        MImportedModel importedModel = new MImportedModel(model, true, null);
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MClassInvariant> invariants = rootModel.getClassInvariantsIncludingImports();
            assertEquals(2, invariants.size());
            assertTrue(invariants.contains(rootModel.getClassInvariant("RootClass::RootInv")));
            assertTrue(invariants.contains(model.getClassInvariant("Person::testInv")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetClassInvariantsWithSymbols() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithInvariant();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Person"));
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MClassInvariant> invariants = rootModel.getClassInvariantsIncludingImports();
            assertEquals(2, invariants.size());
            assertTrue(invariants.contains(rootModel.getClassInvariant("RootClass::RootInv")));
            assertTrue(invariants.contains(model.getClassInvariant("Person::testInv")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetClassInvariantsNotImported() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithInvariant();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of());
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MClassInvariant> invariants = rootModel.getClassInvariantsIncludingImports();
            assertEquals(1, invariants.size());
            assertFalse(invariants.contains(model.getClassInvariant("Person::testInv")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetAllClassInvariants() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithInvariant();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Person"));
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MClassInvariant> invariants = rootModel.allClassInvariants(model.getClass("Person"));
            assertEquals(1, invariants.size());
            assertTrue(invariants.contains(model.getClassInvariant("Person::testInv")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetClassInvariantWithWildcard() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithInvariant();
        MImportedModel importedModel = new MImportedModel(model, true, null);
        try {
            rootModel.addImportedModel(importedModel);
            assertEquals(model.getClassInvariant("Person::testInv"), rootModel.getClassInvariant("Person::testInv"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetClassInvariantWithSymbols() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithInvariant();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Person"));
        try {
            rootModel.addImportedModel(importedModel);
            assertEquals(model.getClassInvariant("Person::testInv"), rootModel.getClassInvariant("Person::testInv"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void getClassInvariantNotImported() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithInvariant();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of());
        try {
            rootModel.addImportedModel(importedModel);
            assertNull(rootModel.getClassInvariant("Person::testInv"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    // Pre-/Postconditions
    @Test
    public void testGetPrePostConditionsWithWildcard() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassAndPrePostCondition();
        MImportedModel importedModel = new MImportedModel(model, true, null);
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MPrePostCondition> prePostConditions = importedModel.getPrePostConditions();
            assertEquals(2, prePostConditions.size());
            assertTrue(prePostConditions.containsAll(model.prePostConditions()));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetPrePostConditionsWithSymbols() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithClassAndPrePostCondition();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Person"));
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MPrePostCondition> prePostConditions = importedModel.getPrePostConditions();
            assertEquals(1, prePostConditions.size());
            MPrePostCondition numEmployeesIsPositive = model.prePostConditions().stream()
                    .filter(p -> p.name().equals("ageIsPositive"))
                    .findFirst().orElse(null);
            assertTrue(prePostConditions.contains(numEmployeesIsPositive));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    // Signals
    @Test
    public void testGetSignalsWithWildcard() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithSignal();
        MImportedModel importedModel = new MImportedModel(model, true, null);
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MSignal> signals = rootModel.getSignalsIncludingImports();
            assertEquals(2, signals.size());
            assertTrue(signals.contains(rootModel.getSignal("RootSignal")));
            assertTrue(signals.contains(model.getSignal("Alarm")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetSignalsWithSymbols() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithSignal();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Alarm"));
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MSignal> signals = rootModel.getSignalsIncludingImports();
            assertEquals(2, signals.size());
            assertTrue(signals.contains(rootModel.getSignal("RootSignal")));
            assertTrue(signals.contains(model.getSignal("Alarm")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetSignalsNotImported() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithSignal();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of());
        try {
            rootModel.addImportedModel(importedModel);
            Collection<MSignal> signals = rootModel.getSignalsIncludingImports();
            assertEquals(1, signals.size());
            assertFalse(signals.contains(model.getSignal("RootSignal")));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetSignalWithWildcard() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithSignal();
        MImportedModel importedModel = new MImportedModel(model, true, null);
        try {
            rootModel.addImportedModel(importedModel);
            assertEquals(model.getSignal("Alarm"), rootModel.getSignal("Alarm"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetSignalWithSymbols() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithSignal();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of("Alarm"));
        try {
            rootModel.addImportedModel(importedModel);
            assertEquals(model.getSignal("Alarm"), rootModel.getSignal("Alarm"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    @Test
    public void testGetSignalNotImported() {
        MModel rootModel = createRootModel();
        MModel model = TestModelUtil.getInstance().createModelWithSignal();
        MImportedModel importedModel = new MImportedModel(model, false, Set.of());
        try {
            rootModel.addImportedModel(importedModel);
            assertNotNull(model.getSignal("Alarm"));
            assertNull(rootModel.getSignal("Alarm"));
        } catch (MInvalidModelException e) {
            throw new Error(e);
        }
    }

    private MModel createRootModel() {
        try {
            UseModelApi api = new UseModelApi("RootModel");

            api.createClass("RootClass", false);
            api.createDataType("RootDataType", false);
            api.createEnumeration("RootEnum", "a", "b", "c");
            api.createAssociation("RootAssociation",
                    "RootClass", "r1", "0..1", MAggregationKind.NONE,
                    "RootClass", "r2", "0..1", MAggregationKind.NONE);
            api.createSignal("RootSignal", false);
            api.createInvariant("RootInv","RootClass", "true", false);

            return api.getModel();
        } catch (UseApiException e) {
            throw new Error(e);
        }
    }

    private MModel createTransitiveImportedModel() {
        try {
            UseModelApi api = new UseModelApi("TransitiveModel");

            api.createClass("TransitiveClass", false);
            api.createDataType("TransitiveDataType", false);
            api.createEnumeration("TransitiveEnum", "a", "b", "c");
            api.createAssociation("TransitiveAssociation",
                    "TransitiveClass", "r1", "0..1", MAggregationKind.NONE,
                    "TransitiveClass", "r2", "0..1", MAggregationKind.NONE);
            api.createSignal("TransitiveSignal", false);

            return api.getModel();
        } catch (UseApiException e) {
            throw new Error(e);
        }
    }

    private MModel createModelWithImportedAssociationEnd() {
        MModel model = TestModelUtil.getInstance().createModelWithClassAndAssocs();
        MModel transitiveModel = createTransitiveImportedModel();
        try {
            model.addImportedModel(new MImportedModel(transitiveModel, true, null));
            MAssociation association = new MAssociationImpl("TransitiveEnds");
            association.addAssociationEnd(new MAssociationEnd(model.getClass("Person"),
                    "a1", new MMultiplicity(0, 1), MAggregationKind.NONE, false, null));
            association.addAssociationEnd(new MAssociationEnd(model.getClass("TransitiveClass"),
                    "a2", new MMultiplicity(0, 1), MAggregationKind.NONE, false, null));
            model.addAssociation(association);
        } catch (MInvalidModelException e) {
            throw new RuntimeException(e);
        }
        return model;
    }
}
