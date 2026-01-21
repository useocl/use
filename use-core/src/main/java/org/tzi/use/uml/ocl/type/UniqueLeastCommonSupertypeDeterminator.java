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

package org.tzi.use.uml.ocl.type;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UniqueLeastCommonSupertypeDeterminator {

    public Type calculateFor(Set<Type> types) {
        if (types.isEmpty()) {
            return TypeFactory.mkVoidType();
        }
        if (types.size() == 1) {
            return types.iterator().next();
        }

        // Void-Typen aussondern; für die Strukturbestimmung verwenden wir nur nonVoidTypes
        Set<Type> nonVoidTypes = new HashSet<Type>();
        for (Type t : types) {
            if (!(t instanceof VoidType)) {
                nonVoidTypes.add(t);
            }
        }

        if (nonVoidTypes.isEmpty()) {
            // alle Typen waren Void
            return TypeFactory.mkVoidType();
        }
        if (nonVoidTypes.size() == 1) {
            return nonVoidTypes.iterator().next();
        }

        // Spezialfall: alle nicht-void Typen sind Tupel mit identischen Parts
        boolean allTuples = true;
        for (Type t : nonVoidTypes) {
            if (!(t instanceof TupleType)) {
                allTuples = false;
                break;
            }
        }
        if (allTuples) {
            TupleType first = (TupleType) nonVoidTypes.iterator().next();
            for (Type t : nonVoidTypes) {
                TupleType tt = (TupleType) t;
                if (tt.getParts().size() != first.getParts().size() ||
                    !tt.getParts().keySet().equals(first.getParts().keySet())) {
                    allTuples = false;
                    break;
                }
            }
            if (allTuples) {
                TupleType.Part[] lcsParts = new TupleType.Part[first.getParts().size()];
                int idx = 0;
                for (TupleType.Part p : first.getParts().values()) {
                    Set<Type> partTypes = new HashSet<Type>();
                    for (Type t : nonVoidTypes) {
                        TupleType tt = (TupleType) t;
                        partTypes.add(tt.getParts().get(p.name()).type());
                    }
                    boolean allPartCollections = true;
                    Class<?> collClass = null;
                    for (Type pt : partTypes) {
                        if (!(pt instanceof CollectionType)) {
                            allPartCollections = false;
                            break;
                        }
                        Class<?> c = pt.getClass();
                        if (collClass == null) {
                            collClass = c;
                        } else if (!collClass.equals(c)) {
                            allPartCollections = false;
                            break;
                        }
                    }
                    Type partLcs;
                    if (allPartCollections && collClass != null) {
                        Type any = partTypes.iterator().next();
                        for (Type pt : partTypes) {
                            if (pt != any) {
                                any = any.getLeastCommonSupertype(pt);
                            }
                        }
                        partLcs = any;
                    } else {
                        partLcs = calculateFor(partTypes);
                    }
                    if (partLcs == null) {
                        partLcs = TypeFactory.mkOclAny();
                    }
                    lcsParts[idx++] = new TupleType.Part(idx - 1, p.name(), partLcs);
                }
                return TypeFactory.mkTuple(lcsParts);
            }
        }

        boolean allCollections = true;
        for (Type t : nonVoidTypes) {
            if (!(t instanceof CollectionType)) {
                allCollections = false;
                break;
            }
        }

        if (allCollections) {
            Set<Class<?>> collectionClasses = new HashSet<Class<?>>();
            for (Type t : nonVoidTypes) {
                collectionClasses.add(t.getClass());
            }
            boolean sameSpecialization = collectionClasses.size() == 1;

            Set<Type> innerElementTypes = new HashSet<Type>();
            for (Type t : nonVoidTypes) {
                CollectionType ct = (CollectionType) t;
                innerElementTypes.add(ct.elemType());
            }

            // Element-LCS ohne künstliche Void-Beimischung bestimmen
            Type elemLcs = calculateFor(innerElementTypes);
            if (elemLcs == null) {
                elemLcs = TypeFactory.mkOclAny();
            }

            if (sameSpecialization) {
                Class<?> collClass = collectionClasses.iterator().next();
                if (SetType.class.equals(collClass)) {
                    return TypeFactory.mkSet(elemLcs);
                }
                if (BagType.class.equals(collClass)) {
                    return TypeFactory.mkBag(elemLcs);
                }
                if (SequenceType.class.equals(collClass)) {
                    return TypeFactory.mkSequence(elemLcs);
                }
                if (OrderedSetType.class.equals(collClass)) {
                    return TypeFactory.mkOrderedSet(elemLcs);
                }
                return TypeFactory.mkCollection(elemLcs);
            } else {
                return TypeFactory.mkCollection(elemLcs);
            }
        }

        // Generischer LCS-Algorithmus: nur über nicht-void Typen
        Set<Type> allSuperTypes = new HashSet<Type>();
        for (Type t : nonVoidTypes) {
            allSuperTypes.addAll((Collection<? extends Type>) t.allSupertypes());
        }

        Set<Type> allCommonSuperTypes = new HashSet<Type>();
        for (Type t : allSuperTypes) {
            if (typeIsSupertypeOfAll(t, nonVoidTypes))
                allCommonSuperTypes.add(t);
        }

        Type result = null;
        for (Type t : allCommonSuperTypes) {
            if (typeIsComparableToAll(t, allCommonSuperTypes)) {
                if (result == null) {
                    result = t;
                }
                else if (t.conformsTo(result)) {
                    result = t;
                }
            }
        }

        if (result == null) {
            // Fallback: kein eindeutiger LCS -> OclAny
            return TypeFactory.mkOclAny();
        }
        return result;
    }

	/**
     * Determines whether t is either sub-type or super-type of each element in allSuperTypes.
	 * @param t
	 * @param allSuperTypes
	 * @return
	 */
	private boolean typeIsComparableToAll(Type t, Set<Type> allSuperTypes) {
		for (Type t1 : allSuperTypes) {
			if (! (t1.conformsTo(t) || t.conformsTo(t1)))  
				return false;
		}
		return true;
	}

	/**
	 * Determines whether t is a super-type of each element in types.
	 * @param t
	 * @param types
	 * @return
	 */
	private boolean typeIsSupertypeOfAll(Type t, Set<Type> types) {
		for (Type t1 : types) {
			if (! t1.conformsTo(t)) {
				return false;
			}
		}
		return true;
	}
}
