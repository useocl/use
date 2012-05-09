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

package org.tzi.use;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tzi.use.parser.shell.ShellCommandCompiler;
import org.tzi.use.uml.mm.MAssociation;
import org.tzi.use.uml.mm.MAssociationClass;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.ocl.expr.Expression;
import org.tzi.use.uml.ocl.expr.ExpressionWithValue;
import org.tzi.use.uml.ocl.value.Value;
import org.tzi.use.uml.sys.MObject;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.uml.sys.MSystemException;
import org.tzi.use.uml.sys.soil.MAttributeAssignmentStatement;
import org.tzi.use.uml.sys.soil.MLinkDeletionStatement;
import org.tzi.use.uml.sys.soil.MLinkInsertionStatement;
import org.tzi.use.uml.sys.soil.MNewLinkObjectStatement;
import org.tzi.use.uml.sys.soil.MNewObjectStatement;
import org.tzi.use.uml.sys.soil.MObjectDestructionStatement;
import org.tzi.use.uml.sys.soil.MRValue;
import org.tzi.use.uml.sys.soil.MRValueExpression;
import org.tzi.use.uml.sys.soil.MSequenceStatement;
import org.tzi.use.uml.sys.soil.MStatement;
import org.tzi.use.util.NullPrintWriter;

/**
 * TODO
 * @author Daniel Gent
 *
 */
public class SystemManipulator {
	/** TODO */
	private MSystem fSystem;
	
	
	/**
	 * TODO
	 * @param system
	 */
	public SystemManipulator(MSystem system) {
		fSystem = system;
	}
	
	
	/**
	 * TODO
	 * @param objectClass
	 * @param objectNames
	 * @throws MSystemException
	 */
	public void createObjects(
    		MClass objectClass, 
    		String... objectNames) throws MSystemException {
    	
    	if (objectNames.length == 0) {
    		return;
    	}
    	
    	MSequenceStatement seq = new MSequenceStatement();
    	for (String objectName : objectNames) {
    		seq.appendStatement(
    				new MNewObjectStatement(
    						objectClass, 
    						objectName));
    	}
    	
    	evaluateStatement(seq.simplify());
    }
    
	
	/**
	 * TODO
	 * @param className
	 * @param objectNames
	 * @throws MSystemException
	 */
	public void createObjects(
			String className,
			String... objectNames) throws MSystemException {
		
		createObjects(fSystem.model().getClass(className), objectNames);
	}
	
	
	/**
	 * TODO
	 * @param associationClass
	 * @param objectName
	 * @param participants
	 * @throws MSystemException
	 */
	public void createLinkObject(
			MAssociationClass associationClass, 
			String objectName, 
			MObject... participants) throws MSystemException {
		
		List<MRValue> newParticipants = 
			new ArrayList<MRValue>(participants.length);
		
		for (MObject participant : participants) {
			newParticipants.add(new MRValueExpression(participant));
		}
		
		evaluateStatement(
				new MNewLinkObjectStatement(
						associationClass, 
						newParticipants, 
						Collections.<List<MRValue>>emptyList(),
						objectName));
	}
	
	
	/**
	 * TODO
	 * @param associationClass
	 * @param objectName
	 * @param participants
	 * @throws MSystemException
	 */
	public void createLinkObject(
			MAssociationClass associationClass, 
			String objectName, 
			String ... participants) throws MSystemException {
		
		List<MRValue> newParticipants = 
			new ArrayList<MRValue>(participants.length);
		
		for (String participant : participants) {
			newParticipants.add(
					new MRValueExpression(
							fSystem.state().objectByName(participant)));
		}
		
		evaluateStatement(
				new MNewLinkObjectStatement(
						associationClass, 
						newParticipants, 
						Collections.<List<MRValue>>emptyList(),
						objectName));
	}
	
	
	/**
	 * TODO
	 * @param associationClassName
	 * @param objectName
	 * @param participants
	 * @throws MSystemException
	 */
	public void createLinkObject(
			String associationClassName, 
			String objectName, 
			String... participants) throws MSystemException {
		
		createLinkObject(
				fSystem.model().getAssociationClass(associationClassName), 
				objectName, 
				participants);
	}
	
	/**
	 * TODO
	 * @param expressions
	 * @throws MSystemException
	 */
    public void destroyObjects(
    		Expression... expressions) throws MSystemException {
    	
    	if (expressions.length == 0) {
    		return;
    	}
    	
    	MSequenceStatement seq = new MSequenceStatement();
    	for (Expression expression : expressions) {
    		seq.appendStatement(new MObjectDestructionStatement(expression));
    	}
    	
    	evaluateStatement(seq.simplify());
    }
    
    
    /**
     * TODO
     * @param names
     * @throws MSystemException 
     */
    public void destroyObjects(
    		String... names) throws MSystemException {
    	
    	if (names.length == 0) {
    		return;
    	}
    	
    	Expression[] expressions = new Expression[names.length];
    	for (int i = 0; i < names.length; ++i) {
    		expressions[i] = 
    			new ExpressionWithValue(
    					fSystem.state().objectByName(names[i]).value());
    	}
    	
    	destroyObjects(expressions);
    }
    
    
    /**
     * TODO
     * @param association
     * @param participants
     * @throws MSystemException
     */
    public void insertLink(
    		MAssociation association, 
    		MObject[] participants,
    		List<List<Value>> qualifierValues) throws MSystemException {
    	  	
    	evaluateStatement(
    			new MLinkInsertionStatement(
    					association, 
    					participants,
    					qualifierValues));
    }
         
    
    /**
     * TODO
     * @param association
     * @param participants
     * @throws MSystemException
     */
    public void insertLink(
    		MAssociation association,
    		String... participants) throws MSystemException {
    	
    	if (participants.length == 0) {
    		return;
    	}
    	
    	List<MObject> objects = new ArrayList<MObject>(participants.length);
    	for (String participant : participants) {
    		objects.add(fSystem.state().objectByName(participant));
    	}
    	
    	insertLink(association, objects.toArray(new MObject[objects.size()]), Collections.<List<Value>>emptyList());
    }
    
    
    /**
     * TODO
     * @param associationName
     * @param participants
     * @throws MSystemException
     */
    public void insertLink(
    		String associationName, 
    		String... participants) throws MSystemException {
    	
    	insertLink(
    			fSystem.model().getAssociation(associationName), 
    			participants);
    }
    
    
    /**
     * TODO
     * @param association
     * @param participants
     * @throws MSystemException
     */
    public void deleteLink(
    		MAssociation association, 
    		MObject... participants) throws MSystemException {
    	
    	evaluateStatement(
    			new MLinkDeletionStatement(
    					association, 
    					participants, Collections.<List<MRValue>>emptyList()));
    	
    }
    
    
    /**
     * TODO
     * @param association
     * @param participants
     * @throws MSystemException
     */
    public void deleteLink(
    		MAssociation association,
    		String... participants) throws MSystemException {
    	
    	if (participants.length == 0) {
    		return;
    	}
    	
    	List<MObject> objects = new ArrayList<MObject>(participants.length);
    	for (String participant : participants) {
    		objects.add(fSystem.state().objectByName(participant));
    	}
    	
    	deleteLink(association, objects.toArray(new MObject[0]));
    }
    
    
    /**
     * TODO
     * @param associationName
     * @param participants
     * @throws MSystemException
     */
    public void deleteLink(
    		String associationName,
    		String... participants) throws MSystemException {
    	
    	deleteLink(
    			fSystem.model().getAssociation(associationName), 
    			participants);
    }
    
    
    /**
     * TODO
     * @param objectName
     * @param attributeName
     * @param value
     * @throws MSystemException
     */
    public void setAttribute(
    		String objectName, 
    		String attributeName, 
    		Expression value) throws MSystemException {
    	
    	MObject object = fSystem.state().objectByName(objectName);
    	MAttribute attribute = object.cls().attribute(attributeName, true);
    	
    	evaluateStatement(
    			new MAttributeAssignmentStatement(
    					object, 
    					attribute, 
    					value));
    }
    
    
    /**
     * TODO
     * @param statement
     * @throws MSystemException
     */
    public void evaluateStatement(
    		MStatement statement) throws MSystemException {
    	
    	fSystem.execute(statement);
    }
    
    
    /**
     * TODO
     * @param statement
     * @throws MSystemException
     */
    public void evaluateStatement(
    		String statement) throws MSystemException {
    	
    	fSystem.execute(generateStatement(statement));
    }
    
    
    /**
	 * TODO
	 * @param input
	 * @return
	 */
	private MStatement generateStatement(String input) {
		
		return ShellCommandCompiler.compileShellCommand(
				fSystem.model(), 
				fSystem.state(), 
				fSystem.getVariableEnvironment(), 
				input, 
				"<input>", 
				NullPrintWriter.getInstance(), 
				false);
	}
}
