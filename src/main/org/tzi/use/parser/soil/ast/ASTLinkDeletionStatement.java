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
import java.util.Arrays;
import java.util.List;

import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.sys.soil.MLinkDeletionStatement;
import org.tzi.use.uml.sys.soil.MRValue;
import org.tzi.use.util.soil.exceptions.compilation.CompilationFailedException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class ASTLinkDeletionStatement extends ASTStatement {
	/** TODO */
	private String fAssociationName;
	/** TODO */
	private List<ASTRValue> fParticipants;
	
	
	/**
	 * TODO
	 * @param associationName
	 * @param participants
	 */
	public ASTLinkDeletionStatement(
			String associationName,
			List<ASTRValue> participants) {
		
		fAssociationName = associationName;
		fParticipants = participants;
	}
	
	
	/**
	 * TODO
	 * @param associationName
	 * @param participants
	 */
	public ASTLinkDeletionStatement(
			String associationName,
			ASTRValue... participants) {
		
		this(associationName, Arrays.asList(participants));
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
	protected MLinkDeletionStatement generateStatement() throws CompilationFailedException {
		
		// generate association
		MAssociation association = 
			generateAssociation(fAssociationName);
				
		// generate participants
		List<MRValue> participants = 
			generateAssociationParticipants(
					association, 
					fParticipants);
		
		return new MLinkDeletionStatement(association, participants);
	}


	@Override
	protected void printTree(StringBuilder prelude, PrintWriter target) {
		target.println(prelude + "[LINK DELETION]");
	}


	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		sB.append("delete ");
		sB.append("(");
		for (ASTRValue participant : fParticipants) {
			sB.append(participant);
			sB.append(",");
		}
		sB.delete(sB.length() - 1, sB.length());
		sB.append(") ");
		sB.append("from ");
		sB.append(fAssociationName);
		
		return sB.toString();
	}
}
