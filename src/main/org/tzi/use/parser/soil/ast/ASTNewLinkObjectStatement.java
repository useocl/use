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
import java.util.List;

import org.tzi.use.parser.ocl.ASTExpression;
import org.tzi.use.parser.ocl.ASTSimpleType;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.sys.soil.MNewLinkObjectStatement;
import org.tzi.use.uml.sys.soil.MRValue;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.StringUtil;
import org.tzi.use.util.soil.exceptions.compilation.CompilationFailedException;


/**
 * TODO
 * @author Daniel Gent
 *
 */
public class ASTNewLinkObjectStatement extends ASTStatement {
	/** TODO */
	private ASTSimpleType fAssociationClassName;
	/** TODO */
	private List<ASTRValue> fParticipants;
	/** TODO */
	private ASTExpression fLinkObjectName;
	
	
	
	/**
	 * TODO
	 * @param associationName
	 * @param objectName
	 * @param participants
	 */
	public ASTNewLinkObjectStatement(
			ASTSimpleType associationClassName, 
			List<ASTRValue> participants,
			ASTExpression linkObjectName) {
		
		fAssociationClassName = associationClassName;
		fParticipants = participants;
		fLinkObjectName = linkObjectName;
	}
	
	
	/**
	 * TODO
	 * @param associationClassName
	 * @param participants
	 */
	public ASTNewLinkObjectStatement(
			ASTSimpleType associationClassName, 
			List<ASTRValue> participants) {
		
		this(associationClassName, participants, null);
	}
	
	

	
	
	/**
	 * TODO
	 * @return
	 */
	public ASTExpression getLinkObjectName() {
		return fLinkObjectName;
	}
	
	
	/**
	 * TODO
	 * @return
	 */
	public List<ASTRValue> getParticipants() {
		return fParticipants;
	}


	@Override
	protected MStatement generateStatement() throws CompilationFailedException {
		
		MAssociationClass associationClass = 
			generateAssociationClass(fAssociationClassName.toString());
		
		List<MRValue> participants = 
			generateAssociationParticipants(
					associationClass, 
					fParticipants);
		
		return new MNewLinkObjectStatement(
				associationClass, 
				participants, 
				(fLinkObjectName == null ? 
						null : generateStringExpression(fLinkObjectName)));
	}


	@Override
	protected void printTree(StringBuilder prelude, PrintWriter target) {
		target.println(prelude + "[LINK OBJECT CREATION]");
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(fAssociationClassName);
		if (fLinkObjectName != null) {
			sb.append(" ");
			sb.append(fLinkObjectName);
		}
		sb.append(" between (");
		StringUtil.fmtSeq(sb, fParticipants, ", ");
		sb.append(")");
		
		return sb.toString();
	}
}
