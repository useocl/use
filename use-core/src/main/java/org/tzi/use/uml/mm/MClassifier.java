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

package org.tzi.use.uml.mm;

import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.sys.MOperationCall;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface representing a classifier of
 * the UML meta model.
 *
 * @author Lars Hamann
 *
 */
public interface MClassifier extends Type, MModelElement, MNamedElement, UseFileLocatable {
	
	/**
     * Returns the model owning this classifier.
     */
    public MModel model();

    /**
     * Sets the model owning this classifier. This method must be called by
     * {@link MModel#addClass(MClass)}.
     *
     * @see MModel#addClass(MClass)
     */
    public void setModel( MModel model );
    
	/**
	 * If true, the Classifier does not provide a complete declaration and can typically not be instantiated.
	 * An abstract classifier is intended to be used by other classifiers (e.g., as the target of general 
	 * metarelationships or generalization relationships). Default value is false. [UML 2.3, p. 53]
	 * @return
	 */
	boolean isAbstract();

    boolean isQualifiedAccess();

    void setQualifiedAccess(boolean qualifiedAccess);

    /**
     * Returns the set of all direct parent classes (without this
     * class).
     *
     * @return Set(MClassifier)
     */
    Set<? extends MClassifier> parents();

    /**
     * Returns the set of all parent classifiers (without this
     * classifier). This is the transitive closure of the generalization
     * relation.
     *
     * @return Set&lt;MClassifier&gt;
     */
    Set<? extends MClassifier> allParents();

    /**
     * Returns an iterable over the generalization hierarchy.
     * The iteration starts at this class and goes up.
     * @param includeThis If <code>true</code>, the first element of the iteration is this class.
     * @return An iterable over the generalization hierarchy.
     */
    Iterable<? extends MClassifier> generalizationHierachie(boolean includeThis);
    
    /**
     * Returns an iterable over the generalization hierarchy.
     * The iteration starts at this class and goes down.
     * @param includeThis If <code>true</code>, the first element of the iteration is this class.
     * @return An iterable over the generalization hierarchy.
     */
    Iterable<? extends MClassifier> specializationHierachie(boolean includeThis);
    
    /**
     * Returns the set of all child classifier (without this classifier). This
     * is the transitive closure of the generalization relation.
     *
     * @return Set(MClassifier)
     */
    Set<? extends MClassifier> allChildren();

    /**
     * Returns the set of all direct child classifier (without this
     * classifier).
     *
     * @return Set(MClassifier) 
     */
    Set<? extends MClassifier> children();

    /**
     * Returns true if this classifier is a child of
     * <code>otherClassifier</code> or equal to it.
     */
    boolean isSubClassifierOf(MClassifier otherClassifier);

    /**
     * Returns true if this classifier is a child of
     * <code>otherClassifier</code>.
     * If <code>excludeThis</code> is <code>true</code>,
     * it is not checked if <code>otherClassifier</code> equals this classifier, i.e.,
     * it is checked, if it is a "real" sub classifier.
     */
    boolean isSubClassifierOf(MClassifier otherClass, boolean excludeThis);

    /**
     * Returns the specified attribute. Attributes are also looked up
     * in parents if <code>searchInherited</code> is <code>true</code>.
     *
     * @return <code>null</code> if attribute does not exist.
     */
    MAttribute attribute( String name, boolean searchInherited );

    /**
     * Returns the set of attributes defined for this class/data type. Inherited
     * attributes are not included.
     *
     * @return List(MAttribute)
     */
    List<MAttribute> attributes();

    /**
     * Returns the set of all attributes (including inherited ones)
     * defined for this class.
     *
     * @return List(MAttribute)
     */
    List<MAttribute> allAttributes();

    /**
     * Gets an operation by name. Operations are also looked up in
     * super classifiers if <code>searchInherited</code> is true. This
     * method walks up the generalization hierarchy and selects the
     * first matching operation. Thus, if an operation is redefined,
     * this method returns the most specific one.
     *
     * @return null if operation does not exist.
     */
    MOperation operation(String name, boolean searchInherited);

    /**
     * Returns all operations defined for this class/data type. Inherited
     * operations are not included.
     */
    List<MOperation> operations();

    /**
     * Returns the association end that can be reached by the
     * OCL expression <code>self.rolename</code>.
     *
     * @return null if no such association end exists.
     */
    MNavigableElement navigableEnd( String rolename );

    /**
     * Returns a map of all association ends that can be reached from
     * this class by navigation.
     *
     * @return Map(String, MAssociationEnd)
     */
    Map<String, ? extends MNavigableElement> navigableEnds();

    boolean hasStateMachineWhichHandles(MOperationCall operationCall);
}
