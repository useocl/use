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

package org.tzi.use.uml.mm;

import java.util.List;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;

/** 
 * An association connects two or more classes.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public interface MAssociation extends MClassifier {
    
	/** 
     * Adds an association end.
     *
     * @exception MInvalidModel trying to add another composition
     *            or aggregation end.
     */
    void addAssociationEnd(@NonNull MAssociationEnd aend) throws MInvalidModelException;

    /**
     * Returns the list of association ends.
     *
     * @return List(MAssociationEnd)
     */
    List<MAssociationEnd> associationEnds();
    
    
    /**
     * Returns the list of all role names.
     * @return
     */
    List<String> roleNames();

    /**
     * Returns the list of reachable navigation ends from
     * this association.
     *
     * @return List(MAssociationEnd)
     */
    List<MNavigableElement> reachableEnds();
    
    /**
     * Returns the set of association ends attached to <code>cls</code>.
     *
     * @return Set(MAssociationEnd)
     */
    Set<MAssociationEnd> associationEndsAt(MClass cls);

    /**
     * Returns the set of classes participating in this association.
     *
     * @return Set(MClass).
     */
    Set<MClass> associatedClasses();

    /**
     * All associations, which are subsetted by this
     * @return
     */
    Set<MAssociation> getSubsets();
    
    /**
     * Adds an association that is subsetted by this association
     * @param asso
     */
    void addSubsets(@NonNull MAssociation asso);
    
    /**
     * Returns the transitive closure of all association, this
     * association subsets.
     * @return
     */
    Set<MAssociation> getSubsetsClosure();
    
    /**
     * Adds an association that this association is subsetted by
     * @param asso
     */
    void addSubsettedBy(@NonNull MAssociation asso);
    
    /**
     * All associations this association is subsetted by
     * @return
     */
    Set<MAssociation> getSubsettedBy();
    
    /**
     * Returns the transitive closure of all association, this
     * association is subsetted by.
     * @return
     */
    Set<MAssociation> getSubsettedByClosure();
    
    /**
     * Returns kind of association. This operation returns aggregate
     * or composition if one of the association ends is aggregate or
     * composition.
     */
    int aggregationKind();

    /** 
     * Returns a list of association ends which can be reached by
     * navigation from the given class. Examples: 
     *
     * <ul> 
     * <li>For an association R(A,B), R.navigableEndsFrom(A)
     * results in (B).
     *
     * <li>For an association R(A,A), R.navigableEndsFrom(A) results
     * in (A,A).
     *
     * <li>For an association R(A,B,C), R.navigableEndsFrom(A) results
     * in (B,C).
     *
     * <li>For an association R(A,A,B), R.navigableEndsFrom(A) results
     * in (A,A,B).
     * </ul>
     *
     * This operation does not consider associations in which parents
     * of <code>cls</code> take part.
     *
     * @return List(MAssociationEnd)
     * @exception IllegalArgumentException cls is not part of this association.  
     */
    List<MNavigableElement> navigableEndsFrom(MClass cls);

    /**
     * Sets the position in the defined USE-Model.
     */
    public void setPositionInModel(int position);

    /**
     * Sets the model owning this class. This method must be called by
     * {@link MModel#addAssociation(MAssociation)}.
     *
     * @see MModel#addAssociation(MAssociation)
     */
    public void setModel( MModel model );
    
    /** 
     * Returns true if a link consisting of objects of the given classes
     * can be assigned to this association.
     */
    boolean isAssignableFrom(MClass[] classes);
    
    boolean isUnion();
    
    void setUnion(boolean newValue);

	MAssociationEnd getAssociationEnd(MClass endCls, String rolename);

	void addRedefinedBy(@NonNull MAssociation association);

	/**
	 * The set of child associations this association is directly redefined by. 
	 * @return An unmodifiable set of associations this association is directly redefined by.
	 */
	Set<MAssociation> getRedefinedBy();
	
	/**
	 * A set of child associations this association is directly or indirectly redefined by. 
	 * @return An unmodifiable set of associations this association is directly or indirectly redefined by.
	 */
	Set<MAssociation> getRedefinedByClosure();
	
	/**
	 * Returns the transitive closure of specified redefines relations.
	 * In contrast to {@link #getRedefinedByClosure()} it does not return
	 * implicitly present relations.
	 * @return
	 */
	Set<MAssociation> getSpecifiedRedefinedByClosure();
	
	/**
	 * Forces the closure cache to be recalculated.
	 */
	void calculateRedefinedByClosure();
	
	
	void addRedefines(@NonNull MAssociation parentAssociation);

	/**
	 * Access to the set of associations this association directly redefines.
	 * @return The unmodifiable set of associations this association redefines. 
	 */
	Set<MAssociation> getRedefines();
	
	/**
	 * Access to the set of all associations this association directly or indirectly redefines.
	 * @return The unmodifiable set of associations this association directly or indirectly redefines. 
	 */
	Set<MAssociation> getRedefinesClosure();
	
	@Override
    public Set<? extends MAssociation> parents();
        
    @Override
    public Set<? extends MAssociation> allParents();
    
    @Override
    public Set<? extends MAssociation> allChildren();

    @Override
    public Set<? extends MAssociation> children();
    
    @Override
    public Iterable<? extends MAssociation> generalizationHierachie(boolean includeThis);
    
    @Override
    public Iterable<? extends MAssociation> specializationHierachie(boolean includeThis);
    
	/**
	 * True, if links can be inserted into this association otherwise
	 * false, e.g., an association with a derived union at at least
	 * one association end is read only.
	 * 
	 * @return
	 */
	boolean isReadOnly();

	/**
	 * True if any of the end of this association has defined qualifiers.
	 * @return A <code>Boolean</code> value indicating, if this association has end with qualified values.
	 */
	boolean hasQualifiedEnds();

	/**
	 * Returns the first association end that is a possible source of a navigation, e.g.,
	 * if <code>srcClass</code> is a subtype of the navigable end and if provided 
	 * the name of the end matches the given explicit role name. 
	 * @param srcClass The source class of the navigation
	 * @param dst The destination navigable end
	 * @param explicitRolename Optional an explicit rolename. May be <code>null</code>.
	 * @return The matching source end or <code>null</code>.
	 */
	MNavigableElement getSourceEnd(MClassifier srcClass, MNavigableElement dst, String explicitRolename);

	/**
	 * @return
	 */
	boolean isOrdered();

	/**
	 * Returns <code>true</code> if an association end
	 * is defined as derived.
	 * @return
	 */
	boolean isDerived();

	/**
	 * Returns <code>true</code> if an association end
	 * is redefines another.
	 * @return
	 */
	boolean isRedefining();

	/**
	 * Because the ordering of association ends is important for validating
	 * relations between associations (subsets, redefines, etc.) but the order of 
	 * association ends can be changed in related associations this operation
	 * can be used to retrieve an ordered list of association ends which sorts
	 * the related ends in the same order as specified in the parent association
	 * <code>parent</code>. 
	 * @param parentAssociation The <code>MAssociation</code> which defines the order
	 * @return A sorted <code>List</code> of the owned association ends.
	 */
	//List<MAssociationEnd> getParentAlignedEnds(MAssociation parentAssociation);
}
