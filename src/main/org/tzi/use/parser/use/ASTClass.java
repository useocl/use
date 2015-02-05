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

package org.tzi.use.parser.use;

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.Symtable;
import org.tzi.use.parser.use.statemachines.ASTStateMachine;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassifier;
import org.tzi.use.uml.mm.MGeneralization;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.mm.statemachines.MProtocolStateMachine;
import org.tzi.use.uml.mm.statemachines.MStateMachine;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTClass extends ASTAnnotatable {
    protected Token fName;
    protected boolean fIsAbstract;
    protected List<Token> fSuperClasses;
    protected List<ASTAttribute> fAttributes;
    protected List<ASTOperation> fOperations;
    protected List<ASTConstraintDefinition> fConstraints;
    
    /**
     * The class is constructed in several passes, see genXXX methods below
     */
    protected MClass fClass;
    
    protected ArrayList<ASTInvariantClause> fInvariantClauses;

    protected List<ASTStateMachine> stateMachines;
    
    public ASTClass(Token name, boolean isAbstract) {
        fName = name;
        fIsAbstract = isAbstract;
        fAttributes = new ArrayList<ASTAttribute>();
        fOperations = new ArrayList<ASTOperation>();
        fConstraints = new ArrayList<ASTConstraintDefinition>();
        fInvariantClauses = new ArrayList<ASTInvariantClause>();
        stateMachines = new ArrayList<ASTStateMachine>();
    }

    public void addAttribute(ASTAttribute a) {
        fAttributes.add(a);
    }

    public void addOperation(ASTOperation op) {
        fOperations.add(op);
    }

    public void addSuperClasses(List<Token> idList) {
        fSuperClasses = idList;
    }

    public void addInvariantClause(ASTInvariantClause inv) {
        fInvariantClauses.add(inv);
    }

    /**
     * Creates an empty but named classes.
     */
    public MClass genEmptyClass(Context ctx) 
        throws SemanticException
    {
        fClass = ctx.modelFactory().createClass(fName.getText(), fIsAbstract);
        // sets the line position of the USE-Model in this class
        fClass.setPositionInModel( fName.getLine() );
        this.genAnnotations(fClass);
        // makes sure we have a unique class name
        ctx.typeTable().add(fName, fClass);
        return fClass;
    }

    /**
     * Sets superclass relationship and adds attributes to the class.
     */
    public void genAttributesOperationSignaturesAndGenSpec(Context ctx) {
        ctx.setCurrentClass(fClass);
        if (fSuperClasses != null ) {
        	
            for(Token id : fSuperClasses) {
                // lookup parent by name
                MClass parent = ctx.model().getClass(id.getText());
                
                if (parent == null )
                    ctx.reportError(id, "Undefined class `" + id.getText() + "'.");
                else {
                    try {
                        checkForInheritanceConflicts(parent);
                        MGeneralization gen = 
                            ctx.modelFactory().createGeneralization(fClass, parent);
                        ctx.model().addGeneralization(gen);
                    } catch (SemanticException ex) {
                        ctx.reportError(ex);
                    } catch (MInvalidModelException ex) {
                        ctx.reportError(fName, ex);
                    }
                }
            }
        }

        // add attributes
        for (ASTAttribute a : fAttributes ) {
            try {
                MAttribute attr = a.gen(ctx);
                fClass.addAttribute(attr);
            } catch (SemanticException ex) {
                ctx.reportError(ex);
            } catch (MInvalidModelException ex) {
                ctx.reportError(fName, ex);
            }
        }

        // add operation signatures, expressions have to be generated
        // later when all class interfaces are known
        for (ASTOperation astOp : fOperations ) {
            try {
                MOperation op = astOp.genSignature(ctx);
                fClass.addOperation(op);
            } catch (SemanticException ex) {
                ctx.reportError(ex);
                astOp.setSignatureGenFailed();
            } catch (MInvalidModelException ex) {
                ctx.reportError(fName, ex);
                astOp.setSignatureGenFailed();
            }
        }

        ctx.setCurrentClass(null);
    }


    private void checkForInheritanceConflicts(MClass parent) throws SemanticException {
        //check for inheritance conflicts
        for(MClassifier otherParentC : fClass.parents()) {
        	MClass otherParent = (MClass)otherParentC;
            // check attributes
            for(MAttribute otherParentAttribute : otherParent.allAttributes()) {
                
            	for(MAttribute parentAttribute : parent.allAttributes()) {
                    if (parentAttribute.name().equals(otherParentAttribute.name()) && !parentAttribute.type().equals(otherParentAttribute.type())) {
                        throw new SemanticException(fName,"Inheritance conflict: attribute " + parentAttribute.name() +
                                " occurs with different types in the base classes of " + 
                                fClass.name());
                    }
                }
            }
            
            // check operations
            for(MOperation otherParentOperation : otherParent.allOperations()) {
                for(MOperation parentOperation : parent.allOperations()) {
                    if (parentOperation.name().equals(otherParentOperation.name()) && 
                    	!parentOperation.signature().equals(otherParentOperation.signature()) &&
                    	// the operations could be overloaded
                    	!(parentOperation.cls().isSubClassOf(otherParentOperation.cls()) || otherParentOperation.cls().isSubClassOf(parentOperation.cls()))) {
                        throw new SemanticException(fName,"Inheritance conflict: operation " + parentOperation.name() +
                                " occurs with different signatures in the base classes of " + 
                                fClass.name());
                    }
                }
            }

        }
    }
    
    public void genOperationBodiesAndDerivedAttributes(Context ctx) {
    	ctx.setCurrentClass(fClass);

        // enter pseudo-variable "self" into scope of expressions
        ctx.exprContext().push("self", fClass);
        Symtable vars = ctx.varTable();
        vars.enterScope();
        try {
            vars.add("self", fClass, null);
        } catch (SemanticException ex) { 
            // fatal error?
            throw new Error(ex);
        }

        // generate operation bodies
        for (ASTOperation astOp : fOperations) {
            try {
                astOp.genFinal(ctx);
            } catch (SemanticException ex) {
                ctx.reportError(ex);
            }
        }

        // generate derived attributes
        for (ASTAttribute astAttribute : fAttributes) {
            try {
            	astAttribute.genDerived(ctx);
            	astAttribute.genInit(ctx);
            } catch (SemanticException ex) {
                ctx.reportError(ex);
            }
        }
        
        vars.exitScope(); 
        ctx.exprContext().pop();
        ctx.setCurrentClass(null);
    }
    
    public void genStateMachinesAndStates(Context ctx) {
    	ctx.setCurrentClass(fClass);
    	
    	for (ASTStateMachine sm : this.stateMachines) {
    		MStateMachine mSm;
			try {
				mSm = sm.gen(ctx);
				if (mSm instanceof MProtocolStateMachine)
	    			fClass.addOwnedProtocolStateMachine((MProtocolStateMachine)mSm);
				
			} catch (SemanticException ex) {
				ctx.reportError(ex);
			}
    	}
    	
    	ctx.setCurrentClass(null);
    }
    

    /**
	 * @param ctx
	 */
	public void genStateMachineTransitions(Context ctx) {
		ctx.setCurrentClass(fClass);

        // enter pseudo-variable "self" into scope of expressions
        ctx.exprContext().push("self", fClass);
        Symtable vars = ctx.varTable();
        vars.enterScope();
        
    	for (ASTStateMachine sm : this.stateMachines) {
			try {
				sm.genTransitionsAndStateInvariants(ctx);
			} catch (SemanticException ex) {
				ctx.reportError(ex);
			}
    	}
    	
    	vars.exitScope(); 
        ctx.exprContext().pop();
        ctx.setCurrentClass(null);
	}
	
    /**
     * Adds constraints to the class.
     */
    public void genConstraints(Context ctx) {
        ctx.setCurrentClass(fClass);

        // enter pseudo-variable "self" into scope of expressions
        ctx.exprContext().push("self", fClass);
        Symtable vars = ctx.varTable();
        vars.enterScope();
        try {
            vars.add("self", fClass, null);
        } catch (SemanticException ex) { 
            // fatal error?
            throw new Error(ex);
        }

        // add class invariants
        for (ASTInvariantClause astInv : fInvariantClauses) {
            astInv.gen(ctx, null, fClass);
        }

        vars.exitScope(); 
        ctx.exprContext().pop();
        ctx.setCurrentClass(null);
    }

    public String toString() {
        return "(" + fName + ")";
    }

    public void setClass(MClass cls) {
      fClass = cls;
    }

	/**
	 * @param sm
	 */
	public void addStateMachine(ASTStateMachine sm) {
		this.stateMachines.add(sm);
	}
}
