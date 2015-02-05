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

package org.tzi.use.parser.use.statemachines;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.use.ASTAnnotatable;
import org.tzi.use.parser.use.ASTAttribute;
import org.tzi.use.parser.use.ASTInvariantClause;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MGeneralization;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.commonbehavior.communications.MSignal;
import org.tzi.use.uml.ocl.type.TypeFactory;

/**
 * AST node for signal definitions
 * @author Lars Hamann
 *
 */
public class ASTSignal extends ASTAnnotatable {
	
	private final Token name;
	
	private final boolean isAbstract;
	
	private final List<ASTAttribute> attributes = new ArrayList<>();
	
	private final List<ASTInvariantClause> invariants = new ArrayList<>();
	
	private List<Token> generals;
	
	private MSignal signal;
	
	public ASTSignal(Token name, boolean isAbstract) {
		this.name = name;
		this.isAbstract = isAbstract;
	}

	/**
	 * @return the name
	 */
	public Token getName() {
		return name;
	}

	/**
	 * @return the isAbstract
	 */
	public boolean isAbstract() {
		return isAbstract;
	}

	/**
	 * @param idListRes
	 */
	public void addGenerals(List<Token> generals) {
		this.generals = generals;
	}
	
	public void addAttribute(ASTAttribute a) {
        attributes.add(a);
    }

    public void addInvariantClause(ASTInvariantClause inv) {
        invariants.add(inv);
    }

	public MSignal genEmptySignal(Context ctx) throws SemanticException {
		
		signal = ctx.modelFactory().createSignal(name.getText(), isAbstract);

        signal.setPositionInModel( name.getLine() );
        
        this.genAnnotations(signal);
        
        // makes sure we have a unique class name
        ctx.typeTable().add(name, TypeFactory.mkMessageType(signal));
        
        return signal;
	}

	/**
	 * @param ctx
	 */
	public void genAttributesAndGenSpec(Context ctx) {
		
        if (this.generals != null ) {
            for(Token id : this.generals) {
                // lookup parent by name
                MSignal parent = ctx.model().getSignal(id.getText());
                
                if (parent == null ) {
                    ctx.reportError(id, "Undefined signal `" + id.getText() + "'.");
                } else {
                    try {
                        checkForInheritanceConflicts(parent);
                        MGeneralization gen = 
                            ctx.modelFactory().createGeneralization(signal, parent);
                        ctx.model().addGeneralization(gen);
                    } catch (SemanticException ex) {
                        ctx.reportError(ex);
                    } catch (MInvalidModelException ex) {
                        ctx.reportError(name, ex);
                    }
                }
            }
        }

        // add attributes
        for (ASTAttribute a : attributes ) {
            try {
                MAttribute attr = a.gen(ctx);
                signal.addAttribute(attr);
            } catch (SemanticException ex) {
                ctx.reportError(ex);
            } catch (MInvalidModelException ex) {
                ctx.reportError(name, ex);
            }
        }
		
	}
	
	private void checkForInheritanceConflicts(MSignal parent) throws SemanticException {
        //check for inheritance conflicts
        for(MSignal otherParent : signal.parents()) {
            // check attributes
            for(MAttribute otherParentAttribute : otherParent.getAllAttributes()) {
            	for(MAttribute parentAttribute : parent.getAllAttributes()) {
                    if (parentAttribute.name().equals(otherParentAttribute.name()) && !parentAttribute.type().equals(otherParentAttribute.type())) {
                        throw new SemanticException(name, "Inheritance conflict: attribute " + parentAttribute.name() +
                                " occurs with different types in the base classes of " + 
                                signal.name());
                    }
                }
            }
        }
    }
}
