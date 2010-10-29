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
import org.tzi.use.parser.AST;
import org.tzi.use.parser.Context;
import org.tzi.use.parser.SemanticException;
import org.tzi.use.uml.mm.MAggregationKind;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.util.PermutationGenerator;
import org.tzi.use.util.StringUtil;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTAssociation extends AST {
    private Token fKind;
    private Token fName;
    private List<ASTAssociationEnd> fAssociationEnds;

    public ASTAssociation(Token kind, Token name) {
        fKind = kind;
        fName = name;
        fAssociationEnds = new ArrayList<ASTAssociationEnd>();
    }

    public void addEnd(ASTAssociationEnd ae) {
        fAssociationEnds.add(ae);
    }

    public MAssociation gen(Context ctx, MModel model) 
        throws SemanticException 
    {
    	checkDerive();
    	
        MAssociation assoc = ctx.modelFactory().createAssociation(fName.getText());
        
        // sets the line position of the USE-Model in this association
        assoc.setPositionInModel( fName.getLine() );
        String kindname = fKind.getText();
        int kind = MAggregationKind.NONE;
        
        if (kindname.equals("aggregation") )
            kind = MAggregationKind.AGGREGATION;
        else if (kindname.equals("composition") )
            kind = MAggregationKind.COMPOSITION;

        try {
            for (ASTAssociationEnd ae : fAssociationEnds) {
            	// kind of association determines kind of first
                // association end
                MAssociationEnd aend = ae.gen(ctx, kind);
                assoc.addAssociationEnd(aend);

                // further ends are plain ends
                kind = MAggregationKind.NONE;
                
                if (aend.isUnion())
                	assoc.setUnion(true);
            }
            model.addAssociation(assoc);
        } catch (MInvalidModelException ex) {
            throw new SemanticException(fName,
                                        "In " + MAggregationKind.name(assoc.aggregationKind()) + " `" +
                                        assoc.name() + "': " + 
                                        ex.getMessage());
        }
        return assoc;
    }

    // Checks and generates additional constraints on the association, e. g. subsets
    public void genConstraints(Context ctx, MModel model) throws SemanticException {
    	
    	MAssociation association = model.getAssociation(this.fName.getText());

    	// if the association could not be generated in the first step
    	if (association == null) return;
    	
    	// Check subsetting on every association end    	
		for (ASTAssociationEnd aEnd : fAssociationEnds) {
			genSubsetsConstraints(ctx, model, association, aEnd);
			genRedefinesConstraints(ctx, model, association, aEnd);
			aEnd.genDerived(ctx);
		}
    }

    private void checkDerive() throws SemanticException {
    	int derived = 0;
    	for (ASTAssociationEnd aend : fAssociationEnds) {
    		if (aend.isDerived()) derived++;
    	}
    	
    	// We only allow redefine on one end of a binary association
		if (derived > 0 && fAssociationEnds.size() > 2) {
			throw new SemanticException(fName, "A derive expressions on an association end is only allowed on binary associations.");
		}
		
		// We know it is binary
		if ( derived > 1 ) {
			throw new SemanticException(fName, "Only one association end can be derived. One direction is always calculated by USE.");
		}
    }
    
	private void genSubsetsConstraints(Context ctx, MModel model,
			MAssociation association, ASTAssociationEnd aEnd)
			throws SemanticException {
		// TODO: Subset of AssociationClass ?
		
		if (aEnd.getSubsetsRolenames().size() > 0) {
			// Get the MAssociationEnd from the name
			MClass cls = model.getClass(aEnd.getClassName());
			MAssociationEnd nSubsettingEnd = association.getAssociationEnd(cls, aEnd.getRolename(ctx));
		
			// Find parent association end to subset
			for (Token subsetsRolenameToken : aEnd.getSubsetsRolenames()) {
				String subsetsRolename = subsetsRolenameToken.getText();
				List<MAssociationEnd> possibleSubsettedEnds = cls.getAssociationEnd(subsetsRolename);

				if (possibleSubsettedEnds.size() == 0) {
					throw new SemanticException(subsetsRolenameToken, "No association end with name '" + subsetsRolename + "' to subset.");
				}

				boolean valid = false;
			
				// Find a compatible association
				// Duplicate role names are possible 
				for (MAssociationEnd possibleSubsettedEnd : possibleSubsettedEnds) {
					MAssociation parentAssociation = possibleSubsettedEnd.association();
											
					if (isSubsettingValid(association, parentAssociation)) {
						assert(valid == false);
						// Set subset information on this AssociationEnd
						
						// More readable
						MAssociationEnd subsettedEnd = possibleSubsettedEnd;
		    			nSubsettingEnd.addSubsettedEnd(subsettedEnd);
		    		    subsettedEnd.addSubsettingEnd(nSubsettingEnd);
		    		    
		    			parentAssociation.addSubsettedBy(association);
		    			association.addSubsets(parentAssociation);
		    			
		    			valid = true;
					}
				}
				
				if (!valid)
					throw new SemanticException(subsetsRolenameToken, 
							"Constraint {subsets " + subsetsRolename + 
							"} on association end '" + nSubsettingEnd.nameAsRolename() + 
							":" + association.name() + "' is invalid." +
							StringUtil.NEWLINE +
							"Either the parent association " +
							"has another number of association ends or " +
							StringUtil.NEWLINE +
							"the types connected by the subsetting association do not conform " +
							"to the types connected by the subsetted association.");   
			}
		}
	}

	private void genRedefinesConstraints(Context ctx, MModel model,
			MAssociation association, ASTAssociationEnd aEnd)
			throws SemanticException {
		// TODO: Redefines on AssociationClass ?
		
		if (aEnd.getRedefinesRolenames().size() > 0) {
			// Get the MAssociationEnd from the name
			MClass cls = model.getClass(aEnd.getClassName());
			MAssociationEnd redefiningEnd = association.getAssociationEnd(cls, aEnd.getRolename(ctx));
		
			// Find parent association end to subset
			for (Token redefinesRolenameToken : aEnd.getRedefinesRolenames()) {
				String redefinesRolename = redefinesRolenameToken.getText();
				List<MAssociationEnd> possibleRedefinedEnds = cls.getAssociationEnd(redefinesRolename);

				if (possibleRedefinedEnds.size() == 0) {
					throw new SemanticException(redefinesRolenameToken, "No association end with name '" + redefinesRolename + "' to redefine.");
				}

				boolean valid = false;
			
				// Find a compatible association
				// Duplicate role names are possible 
				for (MAssociationEnd possibleRedefinedEnd : possibleRedefinedEnds) {
					MAssociation parentAssociation = possibleRedefinedEnd.association();
											
					if (isSubsettingValid(association, parentAssociation)) {
						assert(valid == false);
						// Set redefined information on this AssociationEnd
						
						// More readable
						MAssociationEnd redefinedEnd = possibleRedefinedEnd;
		    			redefiningEnd.addRedefinedEnd(redefinedEnd);
		    		    redefinedEnd.addRedefiningEnd(redefiningEnd);
		    		    
		    			parentAssociation.addRedefinedBy(association);
		    			association.addRedefines(parentAssociation);
		    			
		    			valid = true;
					}
				}
				
				if (!valid)
					throw new SemanticException(redefinesRolenameToken, 
							"Constraint {redefines " + redefinesRolename + 
							"} on association end '" + redefiningEnd.nameAsRolename() + 
							":" + association.name() + "' is invalid." +
							StringUtil.NEWLINE +
							"Either the parent association " +
							"has another number of association ends or " +
							StringUtil.NEWLINE +
							"the types connected by the redefining association do not conform " +
							"to the types connected by the redefined association.");   
			}
		}
	}
	
	private boolean isSubsettingValid(MAssociation association, MAssociation parentAssociation) {
		
		if (association.reachableEnds().size() != parentAssociation.reachableEnds().size())
			return false;

		// Check every permutation of ends of first association to ends of parent association
		PermutationGenerator<MNavigableElement> gen = new PermutationGenerator<MNavigableElement>(association.reachableEnds());
		
		boolean valid;
		int index;
		MNavigableElement parentEnd;
		
		while (gen.hasMore()) {
			index = 0;
			valid = true;
			
			for (MNavigableElement end : gen.getNextList()) {
				parentEnd = parentAssociation.reachableEnds().get(index);
				
				if (!end.cls().isSubClassOf(parentEnd.cls())) {
					valid = false;
					break;
				}
				
				index++;
			}
			
			// all combinations fit, so subsetting is allowed
			// and we can exit
			if (valid)
				return true;
		}
		
		return false;
	}
    
    public String toString() {
        return "(" + fName + ", " + fKind.getText() + ")";
    }
}
