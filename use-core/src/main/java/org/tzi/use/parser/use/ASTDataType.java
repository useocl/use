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

package org.tzi.use.parser.use;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.Symtable;
import org.tzi.use.uml.mm.*;
import org.tzi.use.util.StringUtil;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @author Stefan Schoon
 */
public class ASTDataType extends ASTClassifier {

    /**
     * The data type is constructed in several passes, see genXXX methods below.
     */
    protected MDataType fDataType;

    public ASTDataType(Token name, boolean isAbstract) {
        super(name, isAbstract);
    }

    /**
     * Creates an empty but named data type.
     */
    public MDataType genEmptyDataType(Context ctx) throws SemanticException {
        fDataType = ctx.modelFactory().createDataType(fName.getText(), fIsAbstract);
        // sets the line position of the USE-Model in this data type
        fDataType.setPositionInModel(fName.getLine());
        this.genAnnotations(fDataType);
        // makes sure we have a unique data type name
        ctx.typeTable().add(fName, fDataType);
        return fDataType;
    }

    /**
     * Sets super data type relationship and adds attributes to the data type.
     */
    public void genAttributesOperationSignaturesAndGenSpec(Context ctx) {
        ctx.setCurrentClassifier(fDataType);
        if (fSuperClassifiers != null ) {
            for (Token id : fSuperClassifiers) {
                // lookup parent by name
                MDataType parent = ctx.model().getDataType(id.getText());
                if (parent == null) {
                    ctx.reportError(id, "Undefined data type " + StringUtil.inQuotes(id.getText()) + ".");
                } else {
                    try {
                        checkForInheritanceConflicts(parent);
                        MGeneralization gen = ctx.modelFactory().createGeneralization(fDataType, parent);
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
        for (ASTAttribute a : fAttributes) {
            try {
                MAttribute attr = a.gen(ctx);
                fDataType.addAttribute(attr);
            } catch (SemanticException ex) {
                ctx.reportError(ex);
            } catch (MInvalidModelException ex) {
                ctx.reportError(fName, ex);
            }
        }

        // add super attributes
        for (ASTAttribute a : fSuperAttributes) {
            try {
                MAttribute attr = a.gen(ctx);
                fDataType.addSuperAttribute(attr, fDataType.attribute(attr.name(), true).owner());
            } catch (SemanticException ex) {
                ctx.reportError(ex);
            }
        }

        // add operation signatures, expressions have to be generated
        // later when all class and data type interfaces are known
        for (ASTOperation astOp : fOperations) {
            try {
                MOperation op = astOp.genSignature(ctx);
                fDataType.addOperation(op);
            } catch (SemanticException ex) {
                ctx.reportError(ex);
                astOp.setSignatureGenFailed();
            } catch (MInvalidModelException ex) {
                ctx.reportError(fName, ex);
                astOp.setSignatureGenFailed();
            }
        }

        ctx.setCurrentClassifier(null);
    }

    private void checkForInheritanceConflicts(MDataType parent) throws SemanticException {
        // check for inheritance conflicts
        for (MDataType otherParentC : fDataType.parents()) {
            // check attributes
            for (MAttribute otherParentAttribute : otherParentC.allAttributes()) {
                for (MAttribute parentAttribute : parent.allAttributes()) {
                    if (parentAttribute.name().equals(otherParentAttribute.name()) &&
                            !parentAttribute.type().equals(otherParentAttribute.type())) {
                        throw new SemanticException(fName, "Inheritance conflict: attribute " + parentAttribute.name() +
                                " occurs with different types in the base data types of " + fDataType.name());
                    }
                }
            }

            // check operations
            for (MOperation otherParentOperation : otherParentC.allOperations()) {
                for (MOperation parentOperation : parent.allOperations()) {
                    if (parentOperation.name().equals(otherParentOperation.name()) &&
                            !parentOperation.signature().equals(otherParentOperation.signature()) &&
                            // the operations could be overloaded
                            !(parentOperation.cls().isSubClassifierOf(otherParentOperation.cls()) ||
                                    otherParentOperation.cls().isSubClassifierOf(parentOperation.cls()))) {
                        throw new SemanticException(fName, "Inheritance conflict: operation " + parentOperation.name() +
                                " occurs with different signatures in the base data types of " + fDataType.name());
                    }
                }
            }
        }
    }

    public void genOperationBodiesAndDerivedAttributes(Context ctx) {
        ctx.setCurrentClassifier(fDataType);

        // enter pseudo-variable "self" into scope of expressions
        ctx.exprContext().push("self", fDataType);
        Symtable vars = ctx.varTable();
        vars.enterScope();
        try {
            vars.add("self", fDataType, null);
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
        ctx.setCurrentClassifier(null);
    }

    /**
     * Adds constraints to the data type.
     */
    public void genConstraints(Context ctx) {
        ctx.setCurrentClassifier(fDataType);

        // enter pseudo-variable "self" into scope of expressions
        ctx.exprContext().push("self", fDataType);
        Symtable vars = ctx.varTable();
        vars.enterScope();
        try {
            vars.add("self", fDataType, null);
        } catch (SemanticException ex) {
            // fatal error?
            throw new Error(ex);
        }

        // add data type invariants
        for (ASTInvariantClause astInv : fInvariantClauses) {
            astInv.gen(ctx, null, fDataType);
        }

        vars.exitScope();
        ctx.exprContext().pop();
        ctx.setCurrentClassifier(null);
    }

    public void setDataType(MDataType dtp) {
        fDataType = dtp;
    }
}
