/*
 * This is source code of the Snapshot Generator, an extension for USE
 * to generate (valid) system states of UML models.
 * Copyright (C) 2001 Joern Bohling, University of Bremen
 *
 * About USE:
 *   USE - UML based specification environment
 *   Copyright (C) 1999,2000,2001 Mark Richters, University of Bremen
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


package org.tzi.use.gen.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.tzi.use.graph.DirectedGraph;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassInvariant;
import org.tzi.use.uml.mm.MGeneralization;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.ocl.type.EnumType;

/**
 * Decorates a <code>uml.mm.MModel</code>: Class invariants
 * can be negated or deactivated. Class invariants can be added.
 * @author  Joern Bohling
 */
public class GModel extends MModel {
    private MModel fModel;
    private Map<String, GFlaggedInvariant> fFlaggedInvariants;
    
    public GModel( MModel model ) {
        super( model.name() + "'" );
        fModel = model;
        fFlaggedInvariants = new TreeMap<String, GFlaggedInvariant>();
        
        for (MClassInvariant inv : fModel.classInvariants()) {
            String name = inv.cls().name() + "::" + inv.name();
            fFlaggedInvariants.put( name, new GFlaggedInvariant(inv) );
        }
    }
    
    public GFlaggedInvariant getFlaggedInvariant( String name ) {
        return (GFlaggedInvariant) fFlaggedInvariants.get(name);
    }

    public Collection<MClassInvariant> loadedClassInvariants() {
        Collection<MClassInvariant> result = new ArrayList<MClassInvariant>(classInvariants());
        result.removeAll(fModel.classInvariants());
        
        return result;
    }

    public MClassInvariant removeClassInvariant( String name ) {
        // only loaded invariants can be removed.
        GFlaggedInvariant added = null;
        MClassInvariant inv = null;
        if (fModel.getClassInvariant(name) == null)
            added = getFlaggedInvariant(name);
        if (added != null) {
            inv = added.classInvariant();
            fFlaggedInvariants.remove(name);
        }
        return inv;
    }

    public Collection<GFlaggedInvariant> flaggedInvariants() {
        return fFlaggedInvariants.values();
    }


    // overriding operations
    public void addClassInvariant(MClassInvariant inv)
        throws MInvalidModelException {
        String name = inv.cls().name() + "::" + inv.name();
        if (getFlaggedInvariant(name) != null )
            throw new MInvalidModelException(
                                             "Model already contains an invariant `" + name + "'.");
        else
            fFlaggedInvariants.put(name, new GFlaggedInvariant(inv));
    }

    public Collection<MClassInvariant> classInvariants() {
        List<MClassInvariant> invariants = new ArrayList<MClassInvariant>();
        
        for (GFlaggedInvariant inv : flaggedInvariants()) {
            invariants.add(inv.classInvariant());
        }
        
        return invariants;
    }

    public MClassInvariant getClassInvariant(String name) {
        return ((GFlaggedInvariant) fFlaggedInvariants.get(name)).classInvariant();
    }

    // just delegate every other method
    public void addClass(MClass cls) throws MInvalidModelException {
        fModel.addClass(cls);
    }
    
    public MClass getClass(String name) {
        return fModel.getClass(name);
    }

    public Collection<MClass> classes() {
        return fModel.classes();
    }

    public void addAssociation(MAssociation assoc) 
        throws MInvalidModelException
    {
        fModel.addAssociation(assoc);
    }
    
    public Collection<MAssociation> associations() {
        return fModel.associations();
    }

    public MAssociation getAssociation(String name) {
        return fModel.getAssociation(name);
    }

    public Set<MAssociation> getAssociationsBetweenClasses(Set<MClass> classes) {
        return fModel.getAssociationsBetweenClasses(classes);
    }

    public Set<MAssociation> getAllAssociationsBetweenClasses(Set<MClass> classes) {
        return fModel.getAllAssociationsBetweenClasses(classes);
    }

    public void addGeneralization(MGeneralization gen) 
        throws MInvalidModelException 
    {
        fModel.addGeneralization(gen);
    }

    public DirectedGraph<MClass, MGeneralization> generalizationGraph() {
        return fModel.generalizationGraph();
    }

    public void addEnumType(EnumType e) throws MInvalidModelException {
        fModel.addEnumType(e);
    }
    
    public EnumType enumType(String name) {
        return fModel.enumType(name);
    }
    
    public EnumType enumTypeForLiteral(String literal) {
        return fModel.enumTypeForLiteral(literal);
    }

    public Set<EnumType> enumTypes() {
        return fModel.enumTypes();
    }

    public Set<MClassInvariant> classInvariants(MClass cls) {
        return fModel.classInvariants(cls);
    }

    public Set<MClassInvariant> allClassInvariants(MClass cls) {
        return fModel.allClassInvariants(cls);
    }

    public String getStats() {
        return fModel.getStats();
    }

    public void processWithVisitor(MMVisitor v) {
        fModel.processWithVisitor(v);
    }
}