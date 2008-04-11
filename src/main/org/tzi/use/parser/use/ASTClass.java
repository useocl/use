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

$Id$

package org.tzi.use.parser.use;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.MyToken;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.Symtable;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MGeneralization;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MOperation;
import org.tzi.use.uml.ocl.type.ObjectType;
import org.tzi.use.uml.ocl.type.TypeFactory;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTClass extends AST {
    protected MyToken fName;
    protected boolean fIsAbstract;
    protected List fSuperClasses; // (MyToken) optional: may be null
    protected List fAttributes;   // (ASTAttribute)
    protected List fOperations;   // (ASTOperation)
    protected List fConstraints;  // (ASTConstraint)
    private MClass fClass;  // the class is constructed in several passes, see genXXX methods below
    protected ArrayList fInvariantClauses; // (ASTInvariantClause)

    public ASTClass(MyToken name, boolean isAbstract) {
        fName = name;
        fIsAbstract = isAbstract;
        fAttributes = new ArrayList();
        fOperations = new ArrayList();
        fConstraints = new ArrayList();
        fInvariantClauses = new ArrayList();
    }

    public void addAttribute(ASTAttribute a) {
        fAttributes.add(a);
    }

    public void addOperation(ASTOperation op) {
        fOperations.add(op);
    }

    //      public void addConstraint(ASTConstraint c) {
    //      fConstraints.add(c);
    //      }

    public void addSuperClasses(List idList) {
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
        // makes sure we have a unique class name
        ctx.typeTable().add(fName, TypeFactory.mkObjectType(fClass));
        return fClass;
    }

    /**
     * Sets superclass relationship and adds attributes to the class.
     */
    public void genAttributesOperationSignaturesAndGenSpec(Context ctx) {
        ctx.setCurrentClass(fClass);
        if (fSuperClasses != null ) {
            Iterator it = fSuperClasses.iterator();
            while (it.hasNext() ) {
                MyToken id = (MyToken) it.next();

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
        Iterator it = fAttributes.iterator();
        while (it.hasNext() ) {
            ASTAttribute a = (ASTAttribute) it.next();
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
        it = fOperations.iterator();
        while (it.hasNext() ) {
            ASTOperation astOp = (ASTOperation) it.next();
            try {
                MOperation op = astOp.genSignature(ctx);
                fClass.addOperation(op);
            } catch (SemanticException ex) {
                ctx.reportError(ex);
            } catch (MInvalidModelException ex) {
                ctx.reportError(fName, ex);
            }
        }

        ctx.setCurrentClass(null);
    }


    private void checkForInheritanceConflicts(MClass parent) throws SemanticException {
        //check for inheritance conflicts
        for(Iterator itx=fClass.parents().iterator();itx.hasNext();) {
            MClass op = (MClass)itx.next();
            // check attributes
            for(Iterator it1=op.allAttributes().iterator();it1.hasNext();) {
                MAttribute opa = (MAttribute)it1.next();
                for(Iterator it2=parent.allAttributes().iterator();it2.hasNext();) {
                    MAttribute pa = (MAttribute)it2.next();
                    if (pa.name().equals(opa.name()) && !pa.type().equals(opa.type())) {
                        throw new SemanticException(fName,"Inheritance conflict: attribute " + pa.name() +
                                " occurs with different types in the base classes of " + 
                                fClass.name());
                    }
                }
            }
            // check operations
            for(Iterator it3=op.allOperations().iterator();it3.hasNext();) {
                MOperation opo = (MOperation)it3.next();
                for(Iterator it4=parent.allOperations().iterator();it4.hasNext();) {
                    MOperation po = (MOperation)it4.next();
                    if (po.name().equals(opo.name()) && !po.signature().equals(opo.signature())) {
                        throw new SemanticException(fName,"Inheritance conflict: operation " + po.name() +
                                " occurs with different signatures in the base classes of " + 
                                fClass.name());
                    }
                }
            }

        }
    }

    /**
     * Adds constraints to the class.
     */
    public void genConstraintsAndOperationBodies(Context ctx) {
        ctx.setCurrentClass(fClass);

        // enter pseudo-variable "self" into scope of expressions
        ObjectType ot = TypeFactory.mkObjectType(fClass);
        ctx.exprContext().push("self", ot);
        Symtable vars = ctx.varTable();
        vars.enterScope();
        try {
            vars.add("self", ot, null);
        } catch (SemanticException ex) { 
            // fatal error?
            throw new Error(ex);
        }


        // generate operation bodies
        Iterator it = fOperations.iterator();
        while (it.hasNext() ) {
            ASTOperation astOp = (ASTOperation) it.next();
            try {
                astOp.genFinal(ctx);
            } catch (SemanticException ex) {
                ctx.reportError(ex);
            }
        }

        // add class invariants
        it = fInvariantClauses.iterator();
        while (it.hasNext() ) {
            ASTInvariantClause astInv = (ASTInvariantClause) it.next();
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
}
