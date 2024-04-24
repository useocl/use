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

import java.util.List;
import java.util.Set;

/**
 * Instances of the type MDataType represent data types in a model.
 *
 * @author Stefan Schoon
 */
public interface MDataType extends MClassifier {
    /**
     * Returns the name of this data type with lowercase first letter.
     */
    String nameAsRolename();

    /**
     * Returns the set of all direct parent data types (without this data type).
     * <p>
     * Sets the return type to Set&lt;MDataType&gt;
     * @return Set&lt;MDataType&gt;
     */
    @Override
    Set<? extends MDataType> parents();
    
    /**
     * Returns the set of all parent data types (without this data type).
     * This is the transitive closure of the generalization relation.
     * <p>
     * Sets the return type to Set&lt;MDataType&gt;
     * @return Set&lt;MDataType&gt;
     */
    @Override
    Set<? extends MDataType> allParents();

    /**
     * Returns the set of all child data types (without this data type). This
     * is the transitive closure of the generalization relation.
     *
     * @return Set(MDataType)
     */
    @Override
    Set<? extends MDataType> allChildren();

    /**
     * Returns the set of all direct child data types (without this
     * data type).
     *
     * @return Set(MDataType)
     */
    @Override
    Set<? extends MDataType> children();

    @Override
    Iterable<? extends MDataType> generalizationHierachie(boolean includeThis);

    @Override
    Iterable<? extends MDataType> specializationHierachie(boolean includeThis);

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
    void addAttribute(MAttribute attr) throws MInvalidModelException;

    void addSuperAttribute(MAttribute attr, MClassifier owner);

    /**
     * Returns the set of all attributes (including inherited ones)
     * defined for this data type.
     *
     * @return List(MAttribute)
     */
    List<MAttribute> allAttributes();

    List<MAttribute> superAttributes();

    /**
     * Adds an operation. The operation name must be unique among all
     * operations of this data type. Operations in super data types may be
     * redefined if the signature matches.
     *
     * @exception MInvalidModelException the operation is not unique
     *            or is not a valid redefinition.
     */
    void addOperation(MOperation op) throws MInvalidModelException;

    /**
     * Returns the set of all operations (including inherited ones)
     * defined for this data type.
     *
     * @return List(MOperation)
     */
    List<MOperation> allOperations();

    /**
     * Sets the position in the defined USE-Model.
     */
    void setPositionInModel(int position);

    /**
     * Process this element with visitor.
     */
    void processWithVisitor(MMVisitor v);
}
