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

package org.tzi.use.uml.ocl.type;

import java.util.*;

import org.tzi.use.uml.api.IElementAnnotation;
import org.tzi.use.uml.api.IType;
import org.tzi.use.uml.api.IModel;
import org.tzi.use.uml.mm.MClassifier;
import org.tzi.use.uml.mm.MClassifierImpl;
import org.tzi.use.uml.mm.MMVisitor;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MElementAnnotation;

/**
 * An enumeration type.
 *
 * @author  Mark Richters
 */
public final class EnumType extends MClassifierImpl implements Type, org.tzi.use.uml.api.IEnumType {
    /**
     * list of enumeration literals
     */
    private final List<String> fLiterals;

    /**
     * for fast access
     */
    private final Set<String> fLiteralSet;

    /**
     * optional annotations attached to this enum (key -> MElementAnnotation)
     */
    private final Map<String, MElementAnnotation> fAnnotations = new LinkedHashMap<>();

    /**
     * Constructs an enumeration type with name and list of literals
     * (String objects). The list of literals is checked for
     * duplicates.
     */
    EnumType(MModel model, String name, List<String> literals) {
        super(name, false);
        setModel(model);
        fLiterals = new ArrayList<>(literals);
        fLiteralSet = new HashSet<>(fLiterals.size());

        for (String lit : fLiterals) {
            if (!fLiteralSet.add(lit))
                throw new IllegalArgumentException("duplicate literal `" + lit + "'");
        }
    }
    
    @Override
    public boolean isTypeOfEnum() {
        return true;
    }
    
    @Override
    public boolean isKindOfEnum(VoidHandling h) {
        return true;
    }
    
    @Override
    public boolean isTypeOfClassifier() {
        return false;
    }

    /**
     * Returns an iterator over the literals.
     */
    public Iterator<String> literals() {
        return fLiterals.iterator();
    }

    /**
     * Returns an unmodifiable list of literals for the enumeration
     *
     * @return an unmodifiable list containing the enumeration's literals
     */
    public List<String> getLiterals() {
        return Collections.unmodifiableList(fLiterals);
    }
    
    /** 
     * Returns true if this enumeration type contains the given literal.
     */
    public boolean contains(String lit) {
        return fLiteralSet.contains(lit);
    }

    /** 
     * Returns true if this type is a subtype of <code>t</code>. 
     */
    @Override
    public boolean conformsTo(IType type) {
        return equals(type) || type.isTypeOfOclAny();
    }

    /** 
     * Returns the set of all supertypes (including this type).
     */
    @Override
    public Set<Type> allSupertypes() {
        Set<Type> res = new HashSet<>(2);
        res.add(TypeFactory.mkOclAny());
        // EnumType implements Type (ocl-level), so 'this' is a valid Type
        res.add(this);
        return res;
    }

    // --- Minimal ocl.Type implementations so EnumType can be used where a Type is required ---
    @Override
    public String shortName() {
        return name();
    }

    @Override
    public String qualifiedName() {
        return model() != null ? model().name() + "#" + name() : name();
    }

    @Override
    public Type getLeastCommonSupertype(Type other) {
        // delegate to IType implementation inherited from MClassifierImpl
        IType res = getLeastCommonSupertype((IType) other);
        if (res instanceof Type) {
            return (Type) res;
        }
        return TypeFactory.mkOclAny();
    }

    public Set<? extends MClassifier> parents() {
        return Collections.emptySet();
    }

    @Override
    public Set<? extends MClassifier> allParents() {
        return Collections.emptySet();
    }

    @Override
    public Set<? extends MClassifier> allChildren() {
        return Collections.emptySet();
    }

    @Override
    public Set<? extends MClassifier> children() {
        return Collections.emptySet();
    }

    @Override
    public Iterable<? extends MClassifier> generalizationHierachie(boolean includeThis) {
        // We don't support generalization of enumerations, yet
        return () -> Collections.emptyIterator();
    }

    @Override
    public Iterable<? extends MClassifier> specializationHierachie(boolean includeThis) {
        // We don't support generalization of enumerations, yet
        return () -> Collections.emptyIterator();
    }

    @Override
    public void processWithVisitor(MMVisitor v) {
        v.visitEnum(this);
    }

    @Override
    public void setModel(IModel model) {
        if (model instanceof MModel) {
            super.setModel((MModel) model);
        } else {
            // if model is some other IModel impl, we cannot set internal MModel reference
            // keep silent to avoid leaking mm into api
        }
    }

    /**
     * Add an annotation coming from API-level (IElementAnnotation). We store mm.MElementAnnotation internally.
     */
    @Override
    public void addAnnotation(IElementAnnotation an) {
        if (an == null) {
            return;
        }
        if (an instanceof MElementAnnotation mea) {
            fAnnotations.put(mea.getName(), mea);
        } else {
            // convert API annotation into mm representation
            MElementAnnotation mea = new MElementAnnotation(an.getName(), an.getValues() == null ? Collections.emptyMap() : an.getValues());
            fAnnotations.put(mea.getName(), mea);
        }
    }

    /**
     * Expose annotations through the API-level map (String -> IElementAnnotation)
     */
    @Override
    public Map<String, org.tzi.use.uml.api.IElementAnnotation> annotations() {
        if (fAnnotations.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, org.tzi.use.uml.api.IElementAnnotation> res = new LinkedHashMap<>();
        for (Map.Entry<String, MElementAnnotation> e : fAnnotations.entrySet()) {
            res.put(e.getKey(), e.getValue());
        }
        return Collections.unmodifiableMap(res);
    }

}
