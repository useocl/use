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

import org.tzi.use.uml.api.IExpression;
import org.tzi.use.uml.api.IInvariantExpressionFactory;
import org.tzi.use.uml.api.InvariantExpressionException;
import org.tzi.use.uml.mm.commonbehavior.communications.MSignal;
import org.tzi.use.uml.mm.commonbehavior.communications.MSignalImpl;
import org.tzi.use.uml.api.IVarDeclList;
import org.tzi.use.uml.api.IType;

/** 
 * A ModelFactory creates instances of the Metamodel.
 *
 * @author  Mark Richters
 */
public class ModelFactory {

    public MModel createModel(String name) {
        return new MModel(name);
    }

    public MDataType createDataType(String name, boolean isAbstract) {
        return new MDataTypeImpl(name, isAbstract);
    }

    public MClass createClass(String name, boolean isAbstract) {
        return new MClassImpl(name, isAbstract);
    }

    public MAssociationClass createAssociationClass( String name, 
                                                     boolean isAbstract ) {
        return new MAssociationClassImpl( name, isAbstract );
    }

    public MClassInvariant createClassInvariant(String name, List<String> vars, 
                                                MClassifier cf, IExpression inv, boolean isExistential)
        throws InvariantExpressionException
    {
        IInvariantExpressionFactory factory = org.tzi.use.uml.api.InvariantExpressionFactoryProvider.get();
        return new MClassInvariant(name, vars, cf, inv, isExistential, factory);
    }

    public MPrePostCondition createPrePostCondition(String name, 
                                                    MOperation op, 
                                                    boolean isPre, 
                                                    IExpression constraint)
        throws InvariantExpressionException
    {
        IExpression apiConstraint = constraint; // already an IExpression
        return new MPrePostCondition(name, op, isPre, apiConstraint);
    }

    public MAttribute createAttribute(String name, IType t) {
        return new MAttribute(name, t);
    }

    public MOperation createOperation(String name, IVarDeclList varDeclList,
                                      IType resultType, boolean isConstructor) {
        return new MOperation(name, varDeclList, resultType, isConstructor);
    }

    public MAssociation createAssociation(String name) {
        return new MAssociationImpl(name);
    }

    public MGeneralization createGeneralization(MClassifier child, MClassifier parent) {
        return new MGeneralization(child, parent);
    }

    /** 
     * Creates a new association end. 
     *
     * @param cls        the class to be connected.
     * @param rolename   role that the class plays in this association.
     * @param mult       multiplicity of end.
     * @param kind       MAggregationKind
     * @param isOrdered  use as Set or Sequence
     * @param qualifiers List of qualifier declarations
     */
    public MAssociationEnd createAssociationEnd(MClass cls, 
                                                String rolename, 
                                                MMultiplicity mult, 
                                                int kind,
                                                boolean isOrdered,
                                                java.util.List<? extends org.tzi.use.uml.api.IVarDecl> qualifiers) {
        return new MAssociationEnd(cls, rolename, mult, kind, isOrdered, qualifiers);
    }

    public MMultiplicity createMultiplicity() {
        return new MMultiplicity();
    }

	/**
	 * Creates a new signal.
	 * @param name
	 * @return
	 */
	public MSignal createSignal(String name, boolean isAbstract) {	
		return new MSignalImpl(name, isAbstract);
	}
}
