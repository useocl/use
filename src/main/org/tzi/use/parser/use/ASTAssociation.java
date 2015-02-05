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
import org.tzi.use.uml.mm.MAggregationKind;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationEnd;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MInvalidModelException;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.MNavigableElement;
import org.tzi.use.util.StringUtil;

/**
 * Node of the abstract syntax tree constructed by the parser.
 *
 * @version     $ProjectVersion: 0.393 $
 * @author  Mark Richters
 */
public class ASTAssociation extends ASTAnnotatable {
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

    public MAssociation gen(Context ctx, MModel model) throws SemanticException 
    {
    	checkDerive();
    	
        MAssociation assoc = ctx.modelFactory().createAssociation(fName.getText());
        this.genAnnotations(assoc);
        
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
            	if (!ae.getQualifiers().isEmpty() && this.fAssociationEnds.size() > 2) {
            		throw new SemanticException(fName,
                            "Error in " + MAggregationKind.name(assoc.aggregationKind()) + " `" +
                            assoc.name() + "': Only binary associations can be qualified.");
            	}
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
    public void genEndConstraints(Context ctx) throws SemanticException {    	
		AssociationEndConstraintsGenerator gen = new AssociationEndConstraintsGenerator(
				ctx, fName.getText(), fAssociationEnds);
    	
		gen.generate();
    }

    private void checkDerive() throws SemanticException {
    	int derived = 0;
    	for (ASTAssociationEnd aend : fAssociationEnds) {
    		if (aend.isDerived()) derived++;
    	}

		if ( derived > 1 ) {
			throw new SemanticException(fName, "Only one association end can be derived. One direction is always calculated by USE.");
		}
    }
    	
	private enum ValidationContext {
		SUBSETS,
		REDEFINES
	}
	
    public String toString() {
        return "(" + fName + ", " + fKind.getText() + ")";
    }
    
    /**
     * To be able to reuse association end constraints generation for association 
     * classes, the logic is placed in an extra class. 
     * @author Lars Hamann
     *
     */
    public static class AssociationEndConstraintsGenerator {

    	private final MModel model;
    	private final MAssociation association;
    	private final List<ASTAssociationEnd> ends;
    	private final Context ctx;
    	
    	public AssociationEndConstraintsGenerator(Context ctx, String associationName, List<ASTAssociationEnd> ends) {
    		this.model = ctx.model();
    		this.association = this.model.getAssociation(associationName);
    		
    		this.ends = ends;
    		this.ctx = ctx;
    	}
    	
		/**
		 * @param ctx
		 * @param association
		 */
		public void generate() throws SemanticException {
			// if the association could not be generated in the first step
        	if (association == null) return;
        	
			// Check subsetting on every association end    	
			for (ASTAssociationEnd aEnd : ends) {
				genSubsetsConstraints(aEnd);
				genRedefinesConstraints(aEnd);
				aEnd.genDerived(ctx);
			}
		}
    	
		/**
		 * Generates possibly defined redefine constraints.
		 * First possible associations are identified, afterwards the validity of
		 * redefinition is validated.
		 * This includes checking the right association end order.  
		 * 
		 * <h1>Relevant parts of the UML specification:</h1>
		 * <h2>UML 2.4.1 p. 130:</h2>
		 * <p>Feature redefinitions can either be explicit with the use of a {redefines x} property string on the feature or implicit by
		 * having a feature with the same name as another feature in one of the owning classifier's more general classifiers. In both
		 * cases, the redefined feature must conform to the compatibility constraint on the redefinitions (e.g., the type of the feature
		 * must be a subtype of the feature's type in the more general context).</p>
		 *
		 * <h2>UML 2.4.1 p. 133:</h2>
		 * <h3>Semantic Variation Points</h3>
		 * <p>There are various degrees of compatibility between the redefined element and the redefining element, such as name
		 * compatibility (the redefining element has the same name as the redefined element), [...]</p>
		 * 
		 * <h2>UML 2.4.1 p. 38:</h2>
		 * <p>The combination of constraints [1,2] above with the semantics of property subsetting and redefinition specified in section
		 * 7.3.45 in constraints [3,4,5] imply that any association end that subsets or redefines another association end forces the
		 * association of the subsetting or redefining association end to be a specialization of the association of the subsetted or
		 * redefined association end respectively.</p>
		 * 
		 * @param ctx
		 * @param model
		 * @param association
		 * @param aEnd
		 * @throws SemanticException
		 */
		private void genRedefinesConstraints(ASTAssociationEnd aEnd)
				throws SemanticException {			
			if (aEnd.getRedefinesRolenames().size() > 0) {
				// Get the MAssociationEnd from the name
				MClass cls = model.getClass(aEnd.getClassName());
				MAssociationEnd redefiningEnd = association.getAssociationEnd(cls, aEnd.getRolename(ctx));

				// Find parent association end to redefine
				for (Token redefinesRolenameToken : aEnd.getRedefinesRolenames()) {
					boolean success = false;
					StringBuilder error = new StringBuilder();
					StringBuilder validationError;
					
					String redefinesRolename = redefinesRolenameToken.getText();
					List<MAssociationEnd> possibleRedefinedEnds = cls.getAssociationEnd(redefinesRolename);

					if (possibleRedefinedEnds.size() == 0) {
						throw new SemanticException(redefinesRolenameToken, "No association end with name '" + redefinesRolename + "' to redefine.");
					}
					 
					//Find a compatible association
					//Duplicate role names are possible:
					for (MAssociationEnd possibleRedefinedEnd : possibleRedefinedEnds) {
						validationError = new StringBuilder();
						if (possibleRedefinedEnd.equals(redefiningEnd)) continue;
						
						MAssociation parentAssociation = possibleRedefinedEnd.association();
						
						// Possible generation error might lead to null
						if (parentAssociation == null) return;
						
						if (!validateInheritance(association, parentAssociation, ValidationContext.REDEFINES, validationError)) {
							if (error.length() > 0) error.append(StringUtil.NEWLINE);
							//TODO each iteration of the for loop yields the same information in the output
							// (leading possibly multiple to identical lines of error messages)
							error.append("Constraint {redefines ").append(redefinesRolename) 
							     .append("} on association end '").append(redefiningEnd.nameAsRolename()) 
							     .append(":").append(association.name()).append("' is invalid.")
							     .append(StringUtil.NEWLINE)
							     .append(validationError);
							continue;
						}
						
						success = true;
						
						// Set redefined information on this AssociationEnd
						// More readable
						MAssociationEnd redefinedEnd = possibleRedefinedEnd;
		    			redefiningEnd.addRedefinedEnd(redefinedEnd);
		    		    redefinedEnd.addRedefiningEnd(redefiningEnd);
		    		    
		    			parentAssociation.addRedefinedBy(association);
		    			association.addRedefines(parentAssociation);
					}
					
					if (!success) {
						throw new SemanticException(redefinesRolenameToken, error.toString());
					}
				}
			}
		}
		
		private void genSubsetsConstraints(ASTAssociationEnd aEnd) throws SemanticException {
			if (!aEnd.getSubsetsRolenames().isEmpty()) {
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
					
					StringBuilder error = new StringBuilder();
					StringBuilder errorBuffer;
					boolean success = false;
					
					// Find a compatible association
					// Duplicate role names are possible 
					for (MAssociationEnd possibleSubsettedEnd : possibleSubsettedEnds) {
						errorBuffer = new StringBuilder();
						MAssociation parentAssociation = possibleSubsettedEnd.association();
						
						if (!validateInheritance(association, parentAssociation, ValidationContext.SUBSETS, errorBuffer)) {
							if (error.length() > 0) error.append(StringUtil.NEWLINE);
							//TODO each iteration of the for loop yields the same information in the output
							// (leading possibly multiple to identical lines of messages)
							error.append("Constraint {subsets ").append(subsetsRolename) 
								 .append("} on association end '").append(nSubsettingEnd.nameAsRolename()) 
								 .append(":").append(association.name()).append("' is invalid.")
								 .append(StringUtil.NEWLINE)
								 .append(errorBuffer.toString());
							continue;
						}
						
						success = true;
						// Set subset information on this AssociationEnd
						
						// More readable
						MAssociationEnd subsettedEnd = possibleSubsettedEnd;
		    			nSubsettingEnd.addSubsettedEnd(subsettedEnd);
		    		    subsettedEnd.addSubsettingEnd(nSubsettingEnd);
		    		    
		    			parentAssociation.addSubsettedBy(association);
		    			association.addSubsets(parentAssociation);
					}
					
					if (!success) {
						throw new SemanticException(subsetsRolenameToken, error.toString());
					}
				}
			}
		}
		
		/**
		 * Checks, if the given <code>childAssociation</code>  
		 * is a valid child definition w.r.t. the types at the association ends.
		 * The ends are checked index-wise.   
		 * @param association The child association. 
		 * @param parentAssociation The parent association.
		 * @param validationContext The context of the validation (subsets or redefines).
		 * @param errorBuffer A StringBuilder for the error message 
		 * @return <code>true</code>, if it is valid. <code>false</code>, otherwise. 
		 */
		private boolean validateInheritance(MAssociation association, MAssociation parentAssociation, ValidationContext validationContext, StringBuilder errorBuffer) {

			if (association.reachableEnds().size() != parentAssociation.reachableEnds().size()) {
				errorBuffer.append("Child association must have the same number of association ends as its parent.");
				return false;
			}

			// UML 2.4.1: (self.isComposite implies prop.isComposite)
			// self is the redefined property. prop is the redefinee. 
			if (validationContext == ValidationContext.REDEFINES) {
				if (parentAssociation.aggregationKind() == MAggregationKind.COMPOSITION &&
					association.aggregationKind() != MAggregationKind.COMPOSITION) {
					errorBuffer.append("Redefinig association must be a composition, if the redefined association is one.");
					return false;
				}
			}
			
			// We check the subsetting (and redefinition) context by index.
			// This means, each child association must be defined in the same order
			// as the parent.
			MNavigableElement parentEnd;
			MNavigableElement ourEnd;
			
			for (int index = 0; index < parentAssociation.associationEnds().size(); ++index) {
				ourEnd    = association.reachableEnds().get(index);
				parentEnd = parentAssociation.reachableEnds().get(index);
				
				if (!ourEnd.cls().isSubClassOf(parentEnd.cls())) {
					errorBuffer.append("The end type of the association end ");
					errorBuffer.append(StringUtil.inQuotes(ourEnd.toString()));
					errorBuffer.append(" has no valid inheritance relation to the association end ");
					errorBuffer.append(StringUtil.inQuotes(parentEnd.toString()));
					errorBuffer.append(StringUtil.NEWLINE);
					errorBuffer.append("Note, that the ends of a child association must be defined in the same order as the ends from the parent.");
					return false;
				}
				
				if (validationContext == ValidationContext.SUBSETS) {
					// UML 2.4.1, p. 127:
					// A Property cannot be subset by a Property with the same name
					// if (self.subsettedProperty->notEmpty()) then
					//  self.subsettedProperty->forAll(sp | sp.name <> self.name)
					if (ourEnd.nameAsRolename().equals(parentEnd.nameAsRolename())) {
						errorBuffer.append("An association end cannot be subset by an association end with the same name.");
						return false;
					}
				}
				
				// Check multiplicity
				
				// UML 2.4.1, p. 127:
				// A subsetting property may strengthen the type of the subsetted property, and its upper bound may be less.
				// subsettedProperty->forAll(sp | type.conformsTo(sp.type) and
				//   ((upperBound()->notEmpty() and sp.upperBound()->notEmpty()) implies
				//       upperBound()<=sp.upperBound() ))
				// The constraint to validate redefinition also checks the lower bound.
				// 
				if (ourEnd instanceof MAssociationEnd) {
					// The multiplicity of an association class end is defined internally
					MAssociationEnd ourAssocEnd = (MAssociationEnd)ourEnd;
					MAssociationEnd parentAssocEnd = (MAssociationEnd)parentEnd;
					
					if (!parentAssocEnd.multiplicity().includesMultiplicity(ourAssocEnd.multiplicity(), validationContext == ValidationContext.REDEFINES)) {
						errorBuffer.append("A " + (validationContext == ValidationContext.REDEFINES ? " redefining " : "subsetting ") + 
								"association end can only reduce the multiplicity of the parent association end.");
						return false;
					}
				}
			}
			
			return true;
		}
    }
}
