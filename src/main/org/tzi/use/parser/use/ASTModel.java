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
import java.util.Iterator;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.ocl.ASTEnumTypeDefinition;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.ocl.type.EnumType;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTModel extends AST {
    private Token fName;
    private List<ASTEnumTypeDefinition> fEnumTypeDefs;
    private List<ASTClass> fClasses;
    private List<ASTAssociationClass> fAssociationClasses;
    private List<ASTAssociation> fAssociations;
    private List<ASTConstraintDefinition> fConstraints;
    private List<ASTPrePost> fPrePosts;

    public ASTModel(Token name) {
        fName = name;
        fEnumTypeDefs = new ArrayList<ASTEnumTypeDefinition>();
        fClasses = new ArrayList<ASTClass>();
        fAssociationClasses = new ArrayList<ASTAssociationClass>();
        fAssociations = new ArrayList<ASTAssociation>();
        fConstraints = new ArrayList<ASTConstraintDefinition>();
        fPrePosts = new ArrayList<ASTPrePost>();
    }

    public void addEnumTypeDef(ASTEnumTypeDefinition etd) {
        fEnumTypeDefs.add(etd);
    }

    public void addClass(ASTClass cls) {
        fClasses.add(cls);
    }

    public void addAssociationClass( ASTAssociationClass assocCls ) {
        fAssociationClasses.add( assocCls );
    }

    public void addAssociation(ASTAssociation assoc) {
        fAssociations.add(assoc);
    }

    public void addConstraint(ASTConstraintDefinition cons) {
        fConstraints.add(cons);
    }

    public void addPrePost(ASTPrePost ppc) {
        fPrePosts.add(ppc);
    }

    public MModel gen(Context ctx) {
        MModel model = ctx.modelFactory().createModel(fName.getText());
        model.setFilename(ctx.filename());
        ctx.setModel(model);

        // (1a) add user-defined types to model
        for (ASTEnumTypeDefinition e : fEnumTypeDefs) {
            EnumType enm;
            try {
                enm = e.gen(ctx);
                model.addEnumType(enm);
            } catch (SemanticException ex) {
                ctx.reportError(ex);
            } catch (MInvalidModelException ex) {
                ctx.reportError(fName, ex);
            }
        }

        // (1b) add empty classes to model
        Iterator<ASTClass> cIt = fClasses.iterator();
        while(cIt.hasNext()) {
        	ASTClass c = cIt.next();
        	
            try {
                MClass cls = c.genEmptyClass(ctx);
                model.addClass(cls);
            } catch (SemanticException ex) {
                ctx.reportError(ex);
                cIt.remove();
            } catch (MInvalidModelException ex) {
                ctx.reportError(fName, ex);
                cIt.remove();
            }
        }

        // (1c) add empty associationclasses to model
        Iterator<ASTAssociationClass> acIt = fAssociationClasses.iterator();
        while ( acIt.hasNext() ) {
            ASTAssociationClass ac = acIt.next();
            try {
                // The associationclass can just be added as a class so far,
                // because to keep the order of generating a model.
                // The associationclass will be added as an association in step 3b.
                MAssociationClass assocCls = ac.genEmptyAssocClass( ctx );
                model.addClass( assocCls );
            } catch ( SemanticException ex ) {
                ctx.reportError( ex );
                acIt.remove();
            } catch ( MInvalidModelException ex ) {
                ctx.reportError( fName, ex );
                acIt.remove();
            }
        }

        // (2a) add attributes and set generalization
        // relationships. The names of all classes are known at this
        // point
        for (ASTClass c : fClasses) {
            c.genAttributesOperationSignaturesAndGenSpec(ctx);
        }

        // (2b) add attributes and set generalization
        // relationships of the associationclasses.
        // The names of all classes are known at this point
        for (ASTAssociationClass ac : fAssociationClasses) {
            ac.genAttributesOperationSignaturesAndGenSpec( ctx );
        }

        // (3a) add associations. Classes are known and can be
        // referenced by role names.
        for (ASTAssociation a : fAssociations) {
            try {
                a.gen(ctx, model);
            } catch (SemanticException ex) {
                ctx.reportError(ex);
            }
        }

        // (3b) add association classes as associations.
        // Classes are known and can be referenced by role names.
        for (ASTAssociationClass ac : fAssociationClasses) {
            try {
                // The association class is now added as an association.
                // It is added here to keep the order of generating a model.
                // The association class is already added as a class in step 1c.
                MAssociationClass assocClass = ac.genAssociation( ctx );
                model.addAssociation( assocClass );
            } catch ( SemanticException ex ) {
                ctx.reportError( ex );
            } catch ( MInvalidModelException ex ) {
                ctx.reportError( fName, ex );
            }
        }

        // (4a) generate constraints. All class interfaces are known
        // and association features are available for expressions.
        for (ASTClass c : fClasses) {
            c.genConstraintsAndOperationBodies(ctx);
        }

        // (4b) generate constraints of the associationclasses.
        // All class interfaces are known and association features
        // are available for expressions.
        for (ASTAssociationClass ac : fAssociationClasses) {
            ac.genConstraintsAndOperationBodies( ctx );
        }

        // (5a) generate global constraints. All class interfaces are
        // known and association features are available for
        // expressions.
        for (ASTConstraintDefinition c : fConstraints) {
            c.gen(ctx);
        }

        // (5b) generate pre-/postconditions.
        for (ASTPrePost ppc : fPrePosts) {
            try {
                ppc.gen(ctx);
            } catch (SemanticException ex) {
                ctx.reportError(ex);
            }
        }

        return model;
    }

    public String toString() {
        return "(" + fName + ")";
    }
}
