/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2026 University of Bremen
 *
 * Licensed under the GNU General Public License, version 2.
 */

package org.tzi.use.uml.mm.instance;

import java.util.List;
import java.util.Set;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MClassifier;
import org.tzi.use.uml.mm.MNavigableElement;

/**
 * Runtime model state, exposed at the mm level so that {@code mm.expr}
 * and {@code mm.values} can query "the running system" without
 * importing {@code sys.MSystemState} (which would create a
 * {@code mm → sys} back-edge). The concrete implementation lives in
 * the sys layer ({@code sys.MSystemState}).
 *
 * <p>Only the subset of {@code MSystemState}'s surface that the
 * model layer genuinely needs is exposed here. State-mutation
 * methods, derived-value plumbing, and event-bus hooks stay
 * sys-only.
 */
public interface IModelState {

    /** Returns all objects in this state. */
    Set<MObject> allObjects();

    /** Returns the number of objects in this state. */
    int numObjects();

    /** Returns the object with the given name, or {@code null}. */
    MObject objectByName(String name);

    /** Returns all objects whose classifier is {@code cls} or a sub-classifier. */
    Set<MObject> objectsOfClassAndSubClasses(MClassifier cls);

    /** Returns the link set for the given association. */
    MLinkSet linksOfAssociation(MAssociation assoc);

    /**
     * Evaluates a derive expression for a derived association end.
     */
    List<MObject> evaluateDeriveExpression(MObject[] source, MAssociationEnd dst) throws MSystemException;

    /**
     * Returns the navigable object reached from {@code link} along
     * the navigable element {@code dst}.
     */
    MObject getNavigableObject(MLink link, MNavigableElement dst);

    /**
     * Evaluates a derive expression for a derived attribute.
     */
    org.tzi.use.uml.mm.values.Value evaluateDeriveExpression(MInstance source, org.tzi.use.uml.mm.MAttribute attribute);
}
