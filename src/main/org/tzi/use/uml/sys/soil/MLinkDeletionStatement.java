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

// $Id$

package org.tzi.use.uml.sys.soil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.evaluation.EvaluationFailedException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
//FIXME: Qualifier values need to be considered
public class MLinkDeletionStatement extends MStatement {
	/**
	 * The name of the association to delete the link from. 
	 */
	private MAssociation fAssociation;
	
	/**
	 * The List of objects that form the link which is deleted
	 */
	private List<MRValue> fParticipants;
	
	/**
	 * The qualifier values of the associations ends.
	 */
	private List<List<MRValue>> qualifierRValues = Collections.emptyList();
	
	/**
	 * TODO
	 * @param association
	 * @param participants
	 */
	public MLinkDeletionStatement(
			MAssociation association, 
			List<MRValue> participants) {
		
		fAssociation = association;
		fParticipants = participants;
	}
	
	
	/**
	 * TODO
	 * @param association
	 * @param participants
	 */
	public MLinkDeletionStatement(
			MAssociation association, 
			Expression... participants) {
		
		fAssociation = association;
		
		fParticipants = new ArrayList<MRValue>(participants.length);
		for (Expression participant : participants) {
			fParticipants.add(new MRValueExpression(participant));
		}
	}
	
	
	/**
	 * TODO
	 * @param association
	 * @param participants
	 */
	public MLinkDeletionStatement(
			MAssociation association,
			MObject... participants) {
		
		fAssociation = association;
		
		fParticipants = new ArrayList<MRValue>(participants.length);
		for (MObject participant : participants) {
			fParticipants.add(new MRValueExpression(participant));
		}
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public MAssociation getAssociation() {
		return fAssociation;
	}
	
	
	/**
	 * Returns an unmodifiable List of the objects that participate in the link which is going to be deleted.
	 * @return An unmodifiable <code>List</code> of the objects of the link to delete.
	 */
	public List<MRValue> getParticipants() {
		return Collections.unmodifiableList(fParticipants);
	}
	
	
	@Override
	protected void evaluate() throws EvaluationFailedException {
		
		List<MObject> participants = evaluateObjectRValues(fParticipants);
		List<List<Value>> qualifierValues;
		
		if (this.qualifierRValues == null || this.qualifierRValues.isEmpty()) {
			qualifierValues = Collections.emptyList();
		} else {
			qualifierValues = new ArrayList<List<Value>>();
			for (List<MRValue> endRValues : qualifierRValues ) {
				List<Value> endQualifierValues;
				if (endRValues == null || endRValues.isEmpty() ) {
					endQualifierValues = Collections.emptyList();
				} else {
					endQualifierValues = new ArrayList<Value>();
					for (MRValue endRValue : endRValues) {
						endQualifierValues.add(this.evaluateRValue(endRValue));
					}
				}
				qualifierValues.add(endQualifierValues);
			}
		}
		
		deleteLink(fAssociation, participants, qualifierValues);
	}
	
	
	@Override
	protected String shellCommand() {
		
		StringBuilder result = new StringBuilder();
		result.append("delete (");
		StringUtil.fmtSeq(result, fParticipants, ",");
		
		/*
		, new StringUtil.IElementFormatter<MRValue>() {
			int index = 0;
			
			@Override
			public String format(MRValue element) {
				String qualifierValues = "";
				
				if (qualifierRValues.get(index) != null && qualifierRValues.get(index).size() > 0) {
					qualifierValues = "{";
					qualifierValues += StringUtil.fmtSeq(qualifierRValues.get(index), ",");
					qualifierValues += "}";
				}
				++index;
				return element.toString() + qualifierValues;
			}
		});*/
		result.append(") from ");
		result.append(fAssociation);
			
		return result.toString();
	}
	
	
	@Override
	public boolean hasSideEffects() {
		return true;
	}


	@Override
	public String toString() {
		return shellCommand();	
	}
}
