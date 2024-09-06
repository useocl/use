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

import org.tzi.use.uml.mm.statemachines.MProtocolStateMachine;
import org.tzi.use.uml.sys.MOperationCall;

/**
 * Instances of the type MClass represent classes in a model.
 *
 * @author <a href="mailto:hanna@tzi.de">Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de">Fabian Gutsche</a>
 */
public interface MClass extends MClassifier {
	
    /**
     * Returns the name of this class with lowercase first letter.
     */
    String nameAsRolename();

    /**
     * Returns the set of all direct parent classes (without this class).
     * <p>
     * Sets the return type to Set&lt;MClass&gt;
     * @return Set&lt;MClass&gt;
     */
    @Override
    Set<? extends MClass> parents();

    /**
     * Returns the set of all parent classes (without this class). 
     * This is the transitive closure of the generalization relation.
     * <p>
     * Sets the return type to Set&lt;MClass&gt;
     * @return Set&lt;MClass&gt;
     */
    @Override
    Set<? extends MClass> allParents();
    
    /**
     * Returns the set of all child classes (without this class). This
     * is the transitive closure of the generalization relation.
     *
     * @return Set(MClass)
     */
    @Override
    Set<? extends MClass> allChildren();

    /**
     * Returns the set of all direct child classes (without this
     * class).
     *
     * @return Set(MClass) 
     */
    @Override
    Set<? extends MClass> children();
        
    @Override
    Iterable<? extends MClass> generalizationHierachie(boolean includeThis);
    
    @Override
    Iterable<? extends MClass> specializationHierachie(boolean includeThis);

    /**
     * Adds an attribute. The attribute must have a unique name within
     * the class and must not conflict with any attribute with the
     * same name defined in a superclass or subclass or with a
     * rolename of an association this class participates in.
     *
     * @exception MInvalidModelException the class already contains an
     *            attribute with the same name or a name clash
     *            occurred.
     */
    void addAttribute(MAttribute attr) throws MInvalidModelException;

    /**
     * Returns the set of all attributes (including inherited ones)
     * defined for this class.
     *
     * @return List(MAttribute)
     */
    List<MAttribute> allAttributes();

    /**
     * Adds an operation. The operation name must be unique among all
     * operations of this class. Operations in superclasses may be
     * redefined if the signature matches.
     *
     * @exception MInvalidModelException the operation is not unique
     *            or is not a valid redefinition.
     */
    void addOperation(MOperation op) throws MInvalidModelException;

    /**
     * Returns the set of all operations (including inherited ones)
     * defined for this class.
     *
     * @return List(MOperation)
     */
    List<MOperation> allOperations();

    /**
     * Returns the set of associations this class directly
     * participates in.
     *
     * @return Set(MAssociation).
     */
    Set<MAssociation> associations();

    /**
     * Returns the set of associations this class and all of its
     * parents participate in.
     *
     * @return Set(MAssociation).
     */
    Set<MAssociation> allAssociations();

    /**
     * Sets the position in the defined USE-Model.
     */
    void setPositionInModel(int position);
    
    /**
     * Process this element with visitor.
     */
    void processWithVisitor(MMVisitor v);

    /**
     * Searches for the association ends connected to this class or 
     * its parents with the given name.
     * @param name The name of association ends to look for.
     * @return A list of association ends in the order from the most specific class to the more general classes.
     */
    List<MAssociationEnd> getAssociationEnd(String name);
	
	/**
     * Registers all association ends as navigable from this
     * class. This should be called by a MModel implementation when an
     * association is added to the model.
     *
     * @param associationEnds List(MAssociationEnd)
     * @see MModel#addAssociation
     */
    void registerNavigableEnds( List<MNavigableElement> associationEnds );
	
	/**
	 * Adds a new PSM to the class
	 * @param psm
	 */
    void addOwnedProtocolStateMachine(MProtocolStateMachine psm);
	
	/**
	 * Returns all owned PSM or an empty list if no PSM is defined.
	 * @return
	 */
    Set<MProtocolStateMachine> getOwnedProtocolStateMachines();

	/**
	 * @param operationCall
	 * @return
	 */
    boolean hasStateMachineWhichHandles(MOperationCall operationCall);

	/**
	 * @return
	 */
    Set<MProtocolStateMachine> getAllOwnedProtocolStateMachines();
}
