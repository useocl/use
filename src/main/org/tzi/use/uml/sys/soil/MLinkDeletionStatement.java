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
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MLink;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.StatementEvaluationResult;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.EvaluationFailedException;


/**
 * "Compiled" version of a link deletion statement.
 * @author Daniel Gent
 * @author Lars Hamann
 */
public class MLinkDeletionStatement extends MStatement {
	/**
	 * The association to delete the link from. 
	 */
	private MAssociation fAssociation;
	
	/**
	 * The List of objects that build the link which is deleted
	 */
	private List<MRValue> fParticipants;
	
	/**
	 * The qualifier values of the associations ends.
	 */
	private List<List<MRValue>> qualifier;
	
	/**
	 * Constructs a new link deletion statement.
	 * @param association The association to delete the link from.
	 * @param participants The List of objects that build the link which is deleted.
	 * @param qualifiers The qualifier values of the associations ends.
	 */
	public MLinkDeletionStatement(
			MAssociation association, 
			List<MRValue> participants,
			List<List<MRValue>> qualifiers) {
		
		this.fAssociation = association;
		this.fParticipants = participants;
		this.qualifier = qualifiers;
	}
	
	/**
	 * Constructs a new link deletion statement.
	 * @param association The association to delete the link from.
	 * @param participants The objects that build the link which is deleted.
	 * @param qualifiers The qualifier values of the associations ends.
	 */
	public MLinkDeletionStatement(
			MAssociation association,
			MObject[] participants,
			List<List<MRValue>> qualifiers) {
		
		fAssociation = association;
		
		fParticipants = new ArrayList<MRValue>(participants.length);
		for (MObject participant : participants) {
			fParticipants.add(new MRValueExpression(participant));
		}
		this.qualifier = qualifiers;
	}
	
	public MLinkDeletionStatement(MLink link) {
		fAssociation = link.association();
		fParticipants = new ArrayList<MRValue>(link.linkedObjectsAsArray().length);
		for (MObject participant : link.linkedObjects()) {
			fParticipants.add(new MRValueExpression(participant));
		}
		
		
		this.qualifier = new ArrayList<List<MRValue>>();
		for (List<Value> endQualifiers : link.getQualifier()) {
			List<MRValue> endQualifierValues;
			
			if (endQualifiers == null || endQualifiers.isEmpty() ) {
				endQualifierValues = Collections.emptyList();
			} else {
				endQualifierValues = new ArrayList<MRValue>();
				for (Value v : endQualifiers) {
					endQualifierValues.add(new MRValueExpression(v));
				}
			}
			
			this.qualifier.add(endQualifierValues);
		}
		
	}
	/**
	 * @return the fAssociation
	 */
	public MAssociation getAssociation() {
		return fAssociation;
	}

	/**
	 * @return the fParticipants
	 */
	public List<MRValue> getParticipants() {
		return fParticipants;
	}

	/**
	 * @return the qualifier
	 */
	public List<List<MRValue>> getQualifiers() {
		return qualifier;
	}

	@Override
    public Value execute(SoilEvaluationContext context,
			StatementEvaluationResult result) throws EvaluationFailedException {
		
		List<MObject> vresult = new ArrayList<MObject>(fParticipants.size());

		for (MRValue rValue : fParticipants) {
			vresult.add(EvalUtil.evaluateObjectRValue(context, result, rValue));
		}
		List<MObject> participants = vresult;
		List<List<Value>> qualifierValues;

		if (this.qualifier == null || this.qualifier.isEmpty()) {
			qualifierValues = Collections.emptyList();
		} else {
			qualifierValues = new ArrayList<List<Value>>();
			for (List<MRValue> endRValues : qualifier) {
				List<Value> endQualifierValues;
				if (endRValues == null || endRValues.isEmpty()) {
					endQualifierValues = Collections.emptyList();
				} else {
					endQualifierValues = new ArrayList<Value>();
					for (MRValue endRValue : endRValues) {
						endQualifierValues.add(EvalUtil.evaluateRValue(context,
								result, endRValue, false));
					}
				}
				qualifierValues.add(endQualifierValues);
			}
		}
		
		try {
            context.getSystem().deleteLink(result, fAssociation, participants, qualifierValues);
        } catch (MSystemException e) {
            throw new EvaluationFailedException(e.getMessage());
        }
		
		return null;
	}
	
	
	@Override
	protected String shellCommand() {
		
		StringBuilder result = new StringBuilder();
		result.append("delete (");
		StringUtil.fmtSeq(result, fParticipants, ",", new StringUtil.IElementFormatter<MRValue>() {
			int index = 0;
			
			@Override
			public String format(MRValue element) {
				String qualifierValues = "";
				
				if (!qualifier.isEmpty() && qualifier.get(index) != null && qualifier.get(index).size() > 0) {
					qualifierValues = "{";
					qualifierValues += StringUtil.fmtSeq(qualifier.get(index), ",");
					qualifierValues += "}";
				}
				++index;
				return element.toString() + qualifierValues;
			}
		});
		
		result.append(") from ");
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
