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
import java.util.List;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.evaluation.EvaluationFailedException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class MLinkDeletionStatement extends MStatement {
	/** TODO */
	private MAssociation fAssociation;
	/** TODO */
	private List<MRValue> fParticipants;
	
	
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
	 * TODO
	 * @return
	 */
	public List<MRValue> getParticipants() {
		return fParticipants;
	}
	
	
	@Override
	protected void evaluate() throws EvaluationFailedException {
		
		List<MObject> participants = evaluateObjectRValues(fParticipants);
		
		deleteLink(fAssociation, participants);
	}
	
	
	@Override
	protected String shellCommand() {
		
		StringBuilder result = new StringBuilder();
		result.append("delete (");
		StringUtil.addToStringBuilder(result, ",", fParticipants);
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
