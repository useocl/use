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

import java.util.ArrayList;
import java.util.List;

import org.antlr.runtime.Token;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.parser.Symtable;
import org.tzi.use.parser.use.ASTAssociation.AssociationEndConstraintsGenerator;
import org.tzi.use.uml.mm.MAggregationKind;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MGeneralization;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MOperation;


/**
 * @version     $ProjectVersion: 0.393 $
 * @author <a href="mailto:hanna@tzi.de>Hanna Bauerdick</a>
 * @author <a href="mailto:gutsche@tzi.de>Fabian Gutsche</a>
 */
public class ASTAssociationClass extends ASTClass {

    private Token fKind = null;
    private MAssociationClass fAssocClass;  
    private List<ASTAssociationEnd> fAssociationEnds;

    public ASTAssociationClass( Token name, boolean isAbstract ) {
        super( name, isAbstract );
        fAssociationEnds = new ArrayList<ASTAssociationEnd>();
    }

    public void addEnd( ASTAssociationEnd ae ) {
        fAssociationEnds.add( ae );
    }

    public void setKind( Token kind ) {
        fKind = kind;
    }

    public MAssociationClass genEmptyAssocClass( Context ctx )
            throws SemanticException {
        fAssocClass = ctx.modelFactory()
            .createAssociationClass( fName.getText(), fIsAbstract );
        // sets the line position of the USE-Model in this association class
        fAssocClass.setPositionInModel( fName.getLine() );
        fAssocClass.setModel( ctx.model() );
        
        // makes sure we have a unique class name
        ctx.typeTable().add( fName, fAssocClass );
        fClass = fAssocClass;
        
        return fAssocClass;
    }

    public void genAttributesOperationSignaturesAndGenSpec( Context ctx ) {
        ctx.setCurrentClass( fAssocClass );
        if ( fSuperClasses != null ) {
            for(Token id : fSuperClasses) {
                // lookup parent by name
                MClass parent = ctx.model().getClass( id.getText() );
                if ( parent == null )
                    ctx.reportError( id, "Undefined class `" + id.getText() + "'." );
                else {
                    try {
                        MGeneralization gen =
                            ctx.modelFactory()
                            .createGeneralization( fAssocClass, parent );
                        ctx.model().addGeneralization( gen );
                    } catch ( MInvalidModelException ex ) {
                        ctx.reportError( fName, ex );
                    }
                }
            }
        }
        
        // add attributes
        for (ASTAttribute a : fAttributes) {
            try {
                MAttribute attr = a.gen( ctx );
                if (this.fAssocClass.isDerived() && !attr.isDerived()) {
					throw new SemanticException(a.nameToken(),
							"A derived association class can only define derived attributes.");
                }
                fAssocClass.addAttribute( attr );
            } catch ( SemanticException ex ) {
                ctx.reportError( ex );
            } catch ( MInvalidModelException ex ) {
                ctx.reportError( fName, ex );
            }
        }

        // add operation signatures, expressions have to be generated
        // later when all class interfaces are known
        for (ASTOperation astOp : fOperations) {
            try {
                MOperation op = astOp.genSignature( ctx );
                fAssocClass.addOperation( op );
            } catch ( SemanticException ex ) {
                ctx.reportError( ex );
            } catch ( MInvalidModelException ex ) {
                ctx.reportError( fName, ex );
            }
        }

        ctx.setCurrentClass( null );
    }

    public MAssociationClass genAssociation( Context ctx )
            throws SemanticException {

        int kind = MAggregationKind.NONE;
        if ( fKind != null ) {
            String kindname = fKind.getText();
            
            if ( kindname.equals( "aggregation" ) )
                kind = MAggregationKind.AGGREGATION;
            else if ( kindname.equals( "composition" ) )
                kind = MAggregationKind.COMPOSITION;
        }
        
        try {
            for (ASTAssociationEnd ae : fAssociationEnds) {
                // kind of association determines kind of first
                // association end
                MAssociationEnd aend = ae.gen( ctx, kind );
                fAssocClass.addAssociationEnd( aend );

                // further ends are plain ends
                kind = MAggregationKind.NONE;
            }
        } catch ( MInvalidModelException ex ) {
            throw new SemanticException( fName,
                                         "In " 
                                         + MAggregationKind.name( fAssocClass.aggregationKind() )
                                         + " `" +
                                         fAssocClass.name() + "': " +
                                         ex.getMessage() );
        }
       
        return fAssocClass;
    }

    public void genOperationBodiesAndDerivedAttributes(Context ctx) {
    	ctx.setCurrentClass( fAssocClass );

        // enter pseudo-variable "self" into scope of expressions
        ctx.exprContext().push( "self", fAssocClass );
        Symtable vars = ctx.varTable();
        vars.enterScope();
        try {
            vars.add( "self", fAssocClass, null );
        } catch ( SemanticException ex ) {
            // fatal error?
            throw new Error(ex);
        }


        // generate operation bodies
        for (ASTOperation astOp : fOperations) {
            try {
                astOp.genFinal( ctx );
            } catch ( SemanticException ex ) {
                ctx.reportError( ex );
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
        ctx.setCurrentClass( null );
    }
    
    public void genConstraints( Context ctx ) {
        ctx.setCurrentClass( fAssocClass );

        // enter pseudo-variable "self" into scope of expressions
        ctx.exprContext().push( "self", fAssocClass );
        Symtable vars = ctx.varTable();
        vars.enterScope();
        try {
            vars.add( "self", fAssocClass, null );
        } catch ( SemanticException ex ) {
            // fatal error?
            throw new Error(ex);
        }

        // add class invariants
        for (ASTInvariantClause astInv : fInvariantClauses) {
            astInv.gen( ctx, null, fAssocClass );
        }

        vars.exitScope();
        ctx.exprContext().pop();
        ctx.setCurrentClass( null );
    }

	/**
	 * @param ctx
	 * @throws MInvalidModelException 
	 */
	public void genAssociationFinal(Context ctx) throws MInvalidModelException {
		if (fAssociationEnds.isEmpty()) {
			MAssociationClass parent = fAssocClass.parents().iterator().next();
			for (MAssociationEnd end : parent.associationEnds()) {
				fAssocClass.addAssociationEnd(ctx.modelFactory().createAssociationEnd(
						end.cls(), end.nameAsRolename(), end.multiplicity(), end.aggregationKind()
						, end.isOrdered(), end.getQualifiers()));
			}
		}
	}

	/**
	 * @param ctx
	 * @param model
	 */
	public void genEndConstraints(Context ctx) throws SemanticException {
		AssociationEndConstraintsGenerator gen = new AssociationEndConstraintsGenerator(
				ctx, fName.getText(), fAssociationEnds);
    	
		gen.generate();
	}
}
