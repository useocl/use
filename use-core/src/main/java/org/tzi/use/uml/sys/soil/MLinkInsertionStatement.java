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

package org.tzi.use.uml.sys.soil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;

/**
 * This statement inserts a new link into an association.
 * 
 * @author Daniel Gent
 */
public class MLinkInsertionStatement extends MStatement {
	/**
	 * The association the link is created for.
	 */
	private MAssociation fAssociation;
	/**
     * List of the objects that participate in the link in the same order as
     * association ends.
	 */
	private List<MRValue> fParticipants;
	/**
	 * List of the qualifier values for the association ends. 
	 */
	private List<List<MRValue>> qualifiers;
	/**
     * When creating an association class instance, this is the name of the
     * instance.
	 */
	private String fLinkObjectName;
	
	/**
     * Creates a new link insertion statement between the objects provided by
     * the list participants.
	 * 
     * @param association
     *            The <code>MAssociation</code> to create the link for.
     * @param participants
     *            The <code>MObject</code>s participating in the link as
     *            <code>MRValue</code>s.
     * @param qualifiers
     *            The qualifier values for the different association ends
	 */
    public MLinkInsertionStatement(MAssociation association, List<MRValue> participants,
			List<List<MRValue>> qualifiers) {
		
		this.fAssociation = association;
		this.fParticipants = participants;
		this.qualifiers = qualifiers;
	}
	
	/**
     * Creates a new link insertion statement between the objects provided by
     * the list participants.
     * 
     * @param association
     *            The <code>MAssociation</code> to create the link for.
     * @param participants
     *            The <code>MObject</code>s participating in the link.
	 * @param qualifiers
	 */
    public MLinkInsertionStatement(MAssociation association, MObject[] participants,
			List<List<Value>> qualifiers) {
		
		fAssociation = association;
		
		fParticipants = new ArrayList<MRValue>(participants.length);
		for (MObject participant : participants) {
			fParticipants.add(new MRValueExpression(participant));
		}
		
		this.qualifiers = new ArrayList<List<MRValue>>();
		for (List<Value> endQualifiers : qualifiers) {
			List<MRValue> endQualifierValues;
			
			if (endQualifiers == null || endQualifiers.isEmpty() ) {
				endQualifierValues = Collections.emptyList();
			} else {
				endQualifierValues = new ArrayList<MRValue>();
				for (Value v : endQualifiers) {
					endQualifierValues.add(new MRValueExpression(v));
				}
			}
			
			this.qualifiers.add(endQualifierValues);
		}
	}
    
	/**
	 * Gets the <code>MAssociation</code> to create the link for.
     * 
	 * @return The <code>MAssociation</code> to create the link for.
	 */
	public MAssociation getAssociation() {
		return fAssociation;
	}
		
	/**
     * Gets the <code>MObject</code>s participating in the link as
     * <code>MRValue</code>s.
     * 
     * @return The <code>MObject</code>s participating in the link as
     *         <code>MRValue</code>s.
	 */
	public List<MRValue> getParticipants() {
		return fParticipants;
	}
	
	/**
	 * @return the qualifiers
	 */
	public List<List<MRValue>> getQualifiers() {
		return qualifiers;
	}

	/**
	 * @return the fLinkObjectName
	 */
	public String getLinkObjectName() {
		return fLinkObjectName;
	}
	
	@Override
    public Value execute(SoilEvaluationContext context, StatementEvaluationResult result)
            throws EvaluationFailedException {
		
        List<MObject> vresult = new ArrayList<MObject>(fParticipants.size());

        for (MRValue rValue : fParticipants) {
            vresult.add(EvalUtil.evaluateObjectRValue(context, result, rValue));
        }
        List<MObject> participants = vresult;
		List<List<Value>> qualifierValues = new ArrayList<List<Value>>();
		List<Value> empty = Collections.emptyList();
		
		if (qualifiers != null && !qualifiers.isEmpty()) {
			for (List<MRValue> values : qualifiers) {
				if (values == null) {
					qualifierValues.add(empty);
				} else {
					List<Value> thisQualifierValues = new ArrayList<Value>();
					for (MRValue v : values) {
                        thisQualifierValues.add(EvalUtil.evaluateRValue(context, result, v, false));
					}
					qualifierValues.add(thisQualifierValues);
				}
			}
		}
		
		// we want to make sure that if this creates an association class
		// instance, it always gets the same name, to enable redo
		if (fAssociation instanceof MAssociationClass) {
            MAssociationClass associationClass = (MAssociationClass) fAssociation;
			
            if ((fLinkObjectName == null) || context.getState().hasObjectWithName(fLinkObjectName)) {
				
                fLinkObjectName = context.getSystem().uniqueObjectNameForClass(
                        associationClass.name());
			}
			
            try {
                context.getSystem().createLinkObject(result, associationClass, fLinkObjectName,
                        participants, qualifierValues);
            } catch (MSystemException e) {
                throw new EvaluationFailedException(e.getMessage());
            }
			
		} else {
            try {
                context.getSystem().createLink(result, fAssociation, participants, qualifierValues);
            } catch (MSystemException e) {
                throw new EvaluationFailedException(e.getMessage());
            }
		}
		
		return null;
    }
	
	@Override
	protected String shellCommand() {
		
		StringBuilder result = new StringBuilder();
		result.append("insert (");
		
		StringUtil.fmtSeqWithSubSeq(result, fParticipants, ",", qualifiers, ",", "{", "}");
		
		result.append(") into ");
		result.append(fAssociation.name());
			
		return result.toString();
	}
	
	@Override
	public String toString() {
		return shellCommand();
	}

	@Override
	public void processWithVisitor(MStatementVisitor v) throws Exception {
		v.visit(this);
	}
}
