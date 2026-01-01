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

import org.tzi.use.uml.api.IEnumType;
import org.tzi.use.uml.mm.commonbehavior.communications.MSignal;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents an imported model, encapsulating the actual model object that is being
 * imported into another model. The access to model elements is determined either by the value
 * of the wildcard attribute or the explicitly declared symbols.
 * @author Matthias Marschalk
 */
public class MImportedModel extends MModelElementImpl {
    private final MModel model;
    private final Boolean isWildcard;
    private final Set<String> elementIdentifiers;

    public MImportedModel(MModel model, Boolean isWildcard, Set<String> elementIdentifiers) {
        super(model.name());
        this.model = model;
        this.isWildcard = isWildcard;
        this.elementIdentifiers = elementIdentifiers;
    }

    /**
     * Returns a collection of classes from the model that match the import criteria.
     *
     * @return collection of MClass objects based on filter criteria.
     */
    public Collection<MClass> getClasses() {
        return model.classes().stream().filter(c -> isElementImported(c.name()) &&
                isImportableIfAssociationClass(c)).toList();
    }

    /**
     * Returns the specified class, provided it passes the import criteria.
     *
     * @return MClass with the specified name if found, otherwise null
     */
    public MClass getClass(String name) {
        // Check if specified name contains model qualifier
        if (name.contains("#")) {
            return resolveQualifiedClass(name);
        }
        if (isWildcard || elementIdentifiers.contains(name)) {
            return model.classes().stream().filter(c -> c.name().equals(name) &&
                    isImportableIfAssociationClass(c)).findFirst().orElse(null);
        }
        return null;
    }

    /**
     * Resolves and returns the class corresponding to the given qualified class name
     * by searching in this model if the specified model name matches and otherwise iterating
     * through this model's imports.
     *
     * @param name The qualified name of the class to resolve, in the format "modelName::className".
     * @return resolved MClass object if found, otherwise null.
     */
    public MClass resolveQualifiedClass(String name) {
        MClass res = null;
        if (isElementImported(name)) {
             res = model.classes().stream()
                    .filter(c -> c.qualifiedName().equals(name))
                    .findFirst().orElse(null);
            if (res != null) {
                return res;
            }
        }

        for (MImportedModel importedModel : model.getImportedModels()) {
            res = importedModel.resolveQualifiedClass(name);
            if (res != null) {
                return res;
            }
        }
        return null;
    }

    /**
     * Returns a collection of data types from the model that match the import criteria.
     *
     * @return collection of MDataType objects based on filter criteria.
     */
    public Collection<MDataType> getDataTypes() {
        return model.dataTypes().stream().filter(c -> isElementImported(c.name())).toList();
    }

    /**
     * Returns the specified data type, provided it passes the import criteria.
     *
     * @return MDatatype with the specified name if found, otherwise null
     */
    public MDataType getDataType(String name) {
        if (name.contains("#")) {
            return resolveQualifiedDatatype(name);
        }
        if (isWildcard || elementIdentifiers.contains(name)) {
            return model.dataTypes().stream().filter(c -> c.name().equals(name)).findFirst().orElse(null);
        }
        return null;
    }

    /**
     * Resolves and returns the data type corresponding to the given qualified data type name
     * by searching in this model if the specified model name matches and otherwise iterating
     * through this model's imports.
     *
     * @param name The qualified name of the data type to resolve, in the format "modelName::datatypeName".
     * @return resolved MDataType object if found, otherwise null.
     */
    public MDataType resolveQualifiedDatatype(String name) {
        MDataType res = null;
        if (isElementImported(name)) {
            res = model.dataTypes().stream()
                    .filter(c -> c.qualifiedName().equals(name))
                    .findFirst().orElse(null);
            if (res != null) {
                return res;
            }
        }

        for (MImportedModel importedModel : model.getImportedModels()) {
            res = importedModel.resolveQualifiedDatatype(name);
            if (res != null) {
                return res;
            }
        }
        return null;
    }

    /**
     * Returns a collection of classifiers from the model that match the import criteria.
     *
     * @return collection of MClassifier objects based on filter criteria.
     */
    public Collection<MClassifier> getClassifiers() {
        return model.classifiers().stream().filter(c -> isElementImported(c.name())).toList();
    }

    /**
     * Returns a collection of enum types from the model that match the import criteria.
     *
     * @return collection of EnumType objects based on filter criteria.
     */
    public Collection<IEnumType> getEnumTypes() {
        return model.enumTypes().stream()
                .filter(c -> isElementImported(c.name()))
                .map(c -> (IEnumType) c)
                .collect(Collectors.toList());
    }

    /**
     * Returns the specified enum type, provided it passes the import criteria.
     *
     * @return EnumType with the specified name if found, otherwise null
     */
    public IEnumType getEnumType(String name) {
        if (name.contains("#")) {
            return resolveQualifiedEnumType(name);
        }
        if (isWildcard || elementIdentifiers.contains(name)) {
            return model.enumTypes().stream().filter(c -> c.name().equals(name)).findFirst().orElse(null);
        }
        return null;
    }
    /**
     * Resolves and returns the data type corresponding to the given qualified data type name
     * by searching in this model if the specified model name matches and otherwise iterating
     * through this model's imports.
     *
     * @param name The qualified name of the data type to resolve, in the format "modelName::datatypeName".
     * @return resolved MDataType object if found, otherwise null.
     */
    public IEnumType resolveQualifiedEnumType(String name) {
        IEnumType res = model.enumTypes().stream()
                .filter(c -> c.qualifiedName().equals(name)).findFirst().orElse(null);
        if (res != null) {
            return res;
        }

        for (MImportedModel importedModel : model.getImportedModels()) {
            res = importedModel.resolveQualifiedEnumType(name);
            if (res != null) {
                return res;
            }
        }
        return null;
    }

    /**
     * Returns a collection of associations from the model that match the import criteria.
     * The classes present in the association must be local to the model and part of the
     * imported symbols (or imported by wildcard).
     *
     * @return a collection of MAssociation objects filtered based on the import criteria
     */
    public Collection<MAssociation> getAssociations() {
        return model.associations().stream()
                .filter(c -> isElementImported(c.name()) &&
                        c.associationEnds().stream()
                                .allMatch(ae -> getClass(ae.cls().name()) != null
                                && isElementImported(ae.cls().name())))
                .toList();
    }

    /**
     * Retrieves the association with the specified name that matches the import criteria.
     * The classes present in the association must be local to the model and part of the
     * imported symbols (or imported by wildcard).
     *
     * @return the MAssociation object with the specified name if it meets the criteria, otherwise null
     */
    public MAssociation getAssociation(String name) {
        if (isWildcard || elementIdentifiers.contains(name)) {
            return model.associations().stream()
                    .filter(c -> c.name().equals(name)
                            && c.associationEnds().stream()
                            .allMatch(ae -> getClass(ae.cls().name()) != null
                                    && isElementImported(ae.cls().name())))
                    .findFirst().orElse(null);
        }
        return null;
    }

    /**
     * Returns an MAssociationClass with the specified name if it matches the import criteria.
     * If wildcard import is enabled, all association ends of the association class must have classes local to the model.
     * If wildcard import is not enabled, both the association and its related classes must be explicitly imported.
     *
     * @return MAssociationClass matching the specified name and meeting
     *         the import criteria, null if no such association class found
     */
    public MAssociationClass getAssociationClass(String name) {
        MClass cls = getClass(name);
        if (cls instanceof MAssociationClass associationClass) {
            if (associationClass.associationEnds().stream().allMatch(ae -> getClass(ae.cls().name()) != null
                            && isElementImported(ae.cls().name()))) {
                return associationClass;
            }
        }
        return null;
    }

    /**
     * Returns a collection of class invariants that match the import criteria.
     * Filters the class invariants based on whether they belong to classes explicitly imported
     * by name or included via a wildcard import.
     *
     * @return collection of MClassInvariant objects filtered based on the import criteria.
     */
    public Collection<MClassInvariant> getClassInvariants() {
        return model.classInvariants().stream().filter(c -> isElementImported(c.cls().name())).toList();
    }

    /**
     * Returns all class invariants that belong to the specified set of classes.
     *
     * @return a collection of MClassInvariant objects associated with the specified classes
     */
    public Collection<MClassInvariant> getAllClassInvariants(Set<MClass> classes) {
        return getClassInvariants().stream()
                .filter(inv -> classes.contains(inv.cls())).collect(Collectors.toSet());
    }

    /**
     * Retrieves the class invariant with the specified qualified name (cls::inv) that matches the import criteria.
     * Filters the class invariants based on whether the classes they belong to are explicitly included
     * via imported symbols or a wildcard import.
     *
     * @return MClassInvariant with the specified name if it meets the criteria, otherwise null.
     */
    public MClassInvariant getClassInvariant(String name) {
        return model.classInvariants().stream().filter(c ->
                (isElementImported(c.cls().name()))
                && c.qualifiedName().equals(name)).findFirst().orElse(null);
    }

    /**
     * Returns a collection of pre- and postconditions that match the import criteria.
     * Filters based on wildcard operator or whether the class of the pre-/postcondition is included
     * in the imported symbols.
     *
     * @return a collection of MPrePostCondition objects filtered by the import criteria.
     */
    public Collection<MPrePostCondition> getPrePostConditions() {
        return model.prePostConditions().stream()
                .filter(c -> isElementImported(c.cls().name())).toList();
    }

    /**
     * Returns a collection of signals from the model that match the import criteria.
     *
     * @return collection of MSignal objects based on filter criteria.
     */
    public Collection<MSignal> getSignals() {
        return model.getSignals().stream().filter(c -> isElementImported(c.name())).toList();
    }

    /**
     * Returns the specified signal, provided it passes the import criteria.
     *
     * @return MSignal with the specified name if found, otherwise null
     */
    public MSignal getSignal(String name) {
        if (isWildcard || elementIdentifiers.contains(name)) {
            return model.getSignals().stream().filter(c -> c.name().equals(name))
                    .findFirst().orElse(null);
        }
        return null;
    }

    /**
     * Helper method to determine if element is imported, i.e. either the wildcard symbol is used or the element
     * is imported explicitly (Unqualified/Qualified). This method should only be used when the distinction
     * between qualified and unqualified elements does not matter. When retrieving classes, cls.name() should
     * be checked for un-/qualified import, or when name argument is qualified anyway.
     */
    private boolean isElementImported(String name) {
        if (isWildcard) {
            return true;
        }

        String[] parts = name.split("#");
        if (parts.length == 2) {
            if (!parts[0].equals(model.name())) {
                return false;
            }
            return elementIdentifiers.contains(name) || elementIdentifiers.contains(parts[1]);
        }

        return elementIdentifiers.contains(parts[0]) || elementIdentifiers.contains(model.name() + "#" + parts[0]);
    }

    private boolean isImportableIfAssociationClass(MClass cls) {
        if (cls instanceof MAssociationClass associationClass) {
            return associationClass.associationEnds().stream()
                    .allMatch(ae -> getClass(ae.cls().name()) != null
                    && isElementImported(ae.cls().name()));
        }
        return true;
    }

    public MModel getModel() {
        return model;
    }

    @Override
    public void processWithVisitor(MMVisitor v) {
        v.visitModel(model);
    }
}
