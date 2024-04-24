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

import org.tzi.use.util.collections.CollectionUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * MDataType instances represent data types in a model.
 *
 * @author Stefan Schoon
 */
public class MDataTypeImpl extends MClassifierImpl implements MDataType {

    /**
     * All inherited attributes of this data type.
     */
    private Map<String, MAttribute> fSuperAttributes;

    MDataTypeImpl(String name, boolean isAbstract) {
        super(name, isAbstract);
        fSuperAttributes = new TreeMap<String, MAttribute>();
    }

    /**
     * Returns the name of this data type with lowercase first letter.
     */
    public String nameAsRolename() {
        return Character.toLowerCase(name().charAt(0)) + name().substring(1);
    }

    public void addSuperAttribute(MAttribute attr, MClassifier owner) {
        // add super attribute
        fSuperAttributes.put(attr.name(), attr);
        attr.setOwner(owner);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" }) // Data type only inherit from other data types
    public Iterable<MDataType> generalizationHierachie(final boolean includeThis) {
        return (Iterable) super.generalizationHierachie(includeThis);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" }) // Data type only inherit from other data types
    public Iterable<MDataType> specializationHierachie(final boolean includeThis) {
        return (Iterable) super.specializationHierachie(includeThis);
    }

    /**
     * Adds an attribute. The attribute must have a unique name within
     * the data type and must not conflict with any attribute with the
     * same name defined in a super data type or sub data type or with a
     * rolename of an association this data type participates in.
     *
     * @exception MInvalidModelException the data type already contains an
     *            attribute with the same name or a name clash
     *            occurred.
     */
    @Override
    public void addAttribute(MAttribute attr) throws MInvalidModelException {
        // check if there already exists an attribute with the
        // specified name in this data type
        String attrName = attr.name();
        if (fAttributes.containsKey(attrName))
            throw new MInvalidModelException("Data type `" + name() + "' already contains an attribute named `"
                    + attrName + "'.");

        // check if attribute of same name is already defined in a
        // super data type
        if (attribute(attrName, true) != null)
            throw new MInvalidModelException("Attribute with name `" + attrName +
                    "' already defined in a super data type.");

        // check if attribute of same name is defined in a sub data type
        for (MDataType dtp : allChildren()) {
            if (dtp.attribute(attrName, false) != null)
                throw new MInvalidModelException("Attribute `" + attrName +
                        "' conflicts with existing attribute in data type `" + dtp.name() + "'.");
        }

        // add attribute
        fAttributes.put(attr.name(), attr);
        attr.setOwner(this);
    }

    /**
     * Returns the set of all attributes (including inherited ones)
     * defined for this data type.
     *
     * @return List(MAttribute)
     */
    public List<MAttribute> allAttributes() {
        // start with local attributes
        List<MAttribute> result = new ArrayList<>(attributes());

        // add attributes from all super data types
        for (MDataType dtp : allParents() ) {
            result.addAll(dtp.attributes());
        }

        return result;
    }

    /**
     * Returns the set of attributes defined for this data type. Inherited
     * attributes are not included.
     *
     * @return List(MAttribute)
     */
    public List<MAttribute> attributes() {
        return new ArrayList<MAttribute>(fAttributes.values());
    }

    public List<MAttribute> superAttributes() {
        return new ArrayList<MAttribute>(fSuperAttributes.values());
    }

    /**
     * Adds an operation. The operation name must be unique among all
     * operations of this data type. Operations in super data types may be
     * redefined if the signature matches.
     *
     * @exception MInvalidModelException the operation is not unique
     *            or is not a valid redefinition.
     */
    @Override
    public void addOperation(MOperation op) throws MInvalidModelException {
        String opname = op.name();
        // check if there already exists an operation with the same
        // name in this data type
        if (fOperations.containsKey(opname))
            throw new MInvalidModelException("Data type `" + name() +
                    "' already contains an operation named `" + opname + "'.");

        // check if operation with same signature is already defined
        // in a super data type. A redefinition is only allowed with same
        // number and types of arguments.
        MOperation superOp = operation(opname, true);
        if (superOp != null) {
            if (!op.isValidOverrideOf(superOp) )
                throw new MInvalidModelException("Redefinition of operation `" + superOp +
                        "' requires same number and type of arguments.");
        }

        // check if operation with same signature is already defined
        // in a sub data type. A redefinition is only allowed with same
        // number and types of arguments.
        for (MDataType dtp : allChildren()) {
            MOperation subOp = dtp.operation(opname, false);
            if (subOp != null)
                if (!subOp.isValidOverrideOf(op) )
                    throw new MInvalidModelException("Operation `" + op +
                            "' does not match its redefinition in data type `" + dtp + "'.");
        }

        // Check if parent attributes are missing in constructor parameters.
        if (op.isConstructor()) {
            List<String> allAttributes = allAttributes().stream()
                    .map(MModelElementImpl::name).collect(Collectors.toList());
            List<String> paramNames = op.paramNames();
            if (!CollectionUtil.equalLists(allAttributes, paramNames, Comparator.comparing(String::toString))) {
                throw new MInvalidModelException("Constructor `" + opname + "' is missing parent attribute(s).");
            }
        }

        fOperations.put(op.name(), op);
        fVTableOperations.put(op.name(), op);

        op.setClassifier(this);
    }

    /**
     * Returns the set of all operations (including inherited ones)
     * defined for this data type.
     *
     * @return List(MOperation)
     */
    public List<MOperation> allOperations() {
        // start with local operations
        List<MOperation> result = new ArrayList<>(operations());

        // add operations from all super data types
        for (MDataType dtp : allParents()) {
            result.addAll(dtp.operations());
        }

        return result;
    }

    @Override
    public boolean isTypeOfClassifier() {
        return false;
    }

    @Override
    public boolean isKindOfDataType(VoidHandling h) {
        return true;
    }

    @Override
    public boolean isTypeOfDataType() {
        return true;
    }

    /**
     * Returns the set of all direct parent data types (without this
     * data type).
     *
     * @return Set(MDataType)
     */
    @Override
    public Set<MDataType> parents() {
        return model.generalizationGraph().targetNodeSet(MDataType.class, this);
    }

    /**
     * Returns the set of all parent data types (without this
     * data type). This is the transitive closure of the generalization
     * relation.
     * The set is unmodifiable!
     *
     * @return Set(MDataType) An unmodifiable set of all parent data types
     */
    public Set<MDataType> allParents() {
        return Collections.unmodifiableSet(model.generalizationGraph().targetNodeClosureSet(MDataType.class, this));
    }

    /**
     * Returns the set of all child data types (without this data type). This
     * is the transitive closure of the generalization relation.
     * The set is unmodifiable!
     *
     * @return Set(MDataType) An unmodifiable set of all child data types
     */
    public Set<MDataType> allChildren() {
        return Collections.unmodifiableSet(model.generalizationGraph().sourceNodeClosureSet(MDataType.class, this));
    }

    /**
     * Returns the set of all direct child data types (without this
     * data type).
     *
     * @return Set(MDataType)
     */
    public Set<MDataType> children() {
        return model.generalizationGraph().sourceNodeSet(MDataType.class, this);
    }

    /**
     * Process this element with visitor.
     */
    public void processWithVisitor(MMVisitor v) {
        v.visitDataType(this);
    }
}
