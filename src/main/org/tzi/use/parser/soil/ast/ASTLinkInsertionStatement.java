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

package org.tzi.use.parser.soil.ast;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.sys.soil.MLinkInsertionStatement;
import org.tzi.use.uml.sys.soil.MRValue;
import org.tzi.use.util.soil.exceptions.compilation.CompilationFailedException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class ASTLinkInsertionStatement extends ASTStatement {
	/**
	 * The name of the association to insert a link into
	 */
	private String fAssociationName;
	
	/**
	 * The ASTRValues of the participating objects
	 */
	private List<ASTRValue> fParticipants;
		
	/**
	 * The List of the provided qualifiers
	 */
	private List<List<ASTRValue>> qualifierValues;
	
	/**
	 * TODO
	 * @param associationName
	 * @param participants
	 */
	public ASTLinkInsertionStatement(
			String associationName,
			List<ASTRValue> participants,
			List<List<ASTRValue>> qualifierValues ) {
		
		this.fAssociationName = associationName;
		this.fParticipants = participants;
		this.qualifierValues = qualifierValues;
	}

	/**
	 * TODO
	 * @return
	 */
	public String getAssociationName() {
		return fAssociationName;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public List<ASTRValue> getParticipants() {
		return fParticipants;
	}

	
	@Override
	protected MLinkInsertionStatement generateStatement() throws CompilationFailedException {
		
		// generate association
		MAssociation association = 
			generateAssociation(fAssociationName);
				
		// generate participants
		List<MRValue> participants = 
			generateAssociationParticipants(
					association, 
					fParticipants);
		
		List<List<MRValue>> qualifierRValues;
		if (this.qualifierValues == null || this.qualifierValues.isEmpty()) {
			qualifierRValues = Collections.emptyList();
		} else {
			qualifierRValues = new ArrayList<List<MRValue>>();
			
			for (List<ASTRValue> endQualifierValues : this.qualifierValues ) {
				List<MRValue> endQualifierRValues;
				
				if (endQualifierValues == null || endQualifierValues.isEmpty()) {
					endQualifierRValues = Collections.emptyList();
				} else {
					endQualifierRValues = new ArrayList<MRValue>();
					
					for (ASTRValue value : endQualifierValues) {
						endQualifierRValues.add(this.generateRValue(value));
					}
				}
				qualifierRValues.add(endQualifierRValues);
			}
		}
		
		return new MLinkInsertionStatement(association, participants, qualifierRValues);
	}

	
	@Override
	protected void printTree(StringBuilder prelude, PrintWriter target) {
		target.println(prelude + "[LINK INSERTION]");

	}

	
	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		sB.append("insert ");
		sB.append("(");
		for (ASTRValue participant : fParticipants) {
			sB.append(participant);
			sB.append(",");
		}
		sB.delete(sB.length() - 1, sB.length());
		sB.append(") ");
		sB.append("into ");
		sB.append(fAssociationName);
		
		return sB.toString();
	}
}
