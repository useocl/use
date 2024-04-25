/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2012 Mark Richters, University of Bremen
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
package org.tzi.use.gui.views.diagrams.classdiagram;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.tzi.use.parser.ocl.OCLCompiler;
import org.tzi.use.uml.mm.*;
import org.tzi.use.uml.ocl.expr.VarDecl;
import org.tzi.use.uml.ocl.expr.VarDeclList;
import org.tzi.use.uml.ocl.type.EnumType;
import org.tzi.use.uml.ocl.type.Type;
import org.tzi.use.uml.ocl.type.TypeFactory;
import org.tzi.use.uml.sys.MSystem;
import org.tzi.use.util.NullPrintWriter;

/**
 * @author Lars Hamann
 */
public class ModelExporter {
	
	public ModelExporter() {
		
	}
	
	public void export(Path exportFile, MSystem system, Set<MClass> classes, Set<MDataType> dataTypes, Set<EnumType> enums, Set<MAssociation> associations) throws IOException {
		ModelFactory f = new ModelFactory();
		MModel sourceModel = system.model();
		MModel targetModel = f.createModel(sourceModel.name());
		
		copyAnnotations(sourceModel, targetModel);
		
		// Create "skeletons" for class
		for (MClass sourceClass : classes) {
			try {
				MClass targetClass;
				
				if (sourceClass instanceof MAssociationClass)
					targetClass = f.createAssociationClass(sourceClass.name(), sourceClass.isAbstract());
				else
					targetClass = f.createClass(sourceClass.name(), sourceClass.isAbstract());
				
				targetModel.addClass(targetClass);
				copyAnnotations(sourceClass, targetClass);
			} catch (MInvalidModelException e) { /* Cannot happen */ }
		}
		
		// Create "skeletons" for data types
		for (MDataType sourceDataType : dataTypes) {
			try {
				MDataType targetDataType;
				targetDataType = f.createDataType(sourceDataType.name(), sourceDataType.isAbstract());
				targetModel.addDataType(targetDataType);
				copyAnnotations(sourceDataType, targetDataType);
			} catch (MInvalidModelException e) { /* Cannot happen */ }
		}

		// Create enumerations
		for (EnumType sourceEnum : enums) {
			try {
				EnumType targetEnum = TypeFactory.mkEnum(sourceEnum.name(), sourceEnum.getLiterals());
				targetModel.addEnumType(targetEnum);
				copyAnnotations(sourceEnum, targetEnum);
			} catch (MInvalidModelException e) { /* Cannot happen */ }
		}
		
		// Create inheritance and attributes
		for (MClass targetClass : targetModel.classes()) {
			MClass sourceClass = sourceModel.getClass(targetClass.name());
			
			// Inheritance
			for (MClass sourceParentClassifier : sourceClass.parents()) {
                MClass targetParentClass = findMostSpecificExportedType(sourceParentClassifier, targetModel);
				
				// Could be hidden!
				if (targetParentClass != null) {
					try {
						targetModel.addGeneralization(f.createGeneralization(targetClass, targetParentClass));
					} catch (MInvalidModelException e) { /* Cannot happen */ }
				}
			}
			
			// Attributes
			for (MAttribute sourceAttribute : sourceClass.attributes()) {
				Type attType = OCLCompiler.compileType(
						targetModel, sourceAttribute.type().toString(),
						"Export", NullPrintWriter.getInstance());
				
				// if type is not exported, don't export the attribute
				if (attType != null) {
					MAttribute targetAttribute = f.createAttribute(sourceAttribute.name(), attType);
					try {
						targetClass.addAttribute(targetAttribute);
					} catch (MInvalidModelException e) { /* Cannot happen */ }
					copyAnnotations(sourceAttribute, targetAttribute);
				}
			}
			
			// Operations
			for (MOperation sourceOperation : sourceClass.operations()) {
				boolean hasErrors = false;
				Type resultType = null;
				
				if (sourceOperation.hasResultType()) {
					resultType = OCLCompiler.compileType(
							targetModel, sourceOperation.resultType().toString(),
							"Export", NullPrintWriter.getInstance());
					
					// Result Type is not exported
					if (resultType == null)
						continue;
				}
				
				VarDeclList targetArgs = new VarDeclList(false);
									
				// Build arguments
				for (VarDecl arg : sourceOperation.paramList()) {
					VarDecl v = cloneVarDecl(targetModel, arg);
					if (v == null) {
						hasErrors = true;
						break;
					}

					targetArgs.add(v);
				}
				
				// If arg type is not present, continue to next operation
				if (hasErrors)
					continue;
				
				MOperation targetOperation = f.createOperation(sourceOperation.name(), targetArgs, resultType,
						sourceOperation.isConstructor());
									
				try {
					targetClass.addOperation(targetOperation);
				} catch (MInvalidModelException e) { /* Cannot happen */ }
				
				copyAnnotations(sourceOperation, targetOperation);
			}
		}
		
		for (MAssociation sourceAssoc : associations) {
			MAssociation targetAssoc;
			if (sourceAssoc instanceof MAssociationClass) {
				targetAssoc = (MAssociation)targetModel.getClass(sourceAssoc.name());
			} else {
				targetAssoc = f.createAssociation(sourceAssoc.name());
				copyAnnotations(sourceAssoc, targetAssoc);
			}
			
			for (MAssociationEnd sourceEnd : sourceAssoc.associationEnds()) {
				List<VarDecl> targetQualifiers = new ArrayList<VarDecl>();
				boolean hasErrors = false;
				
				for (VarDecl sourceQualifier : sourceEnd.getQualifiers()) {
					VarDecl targetQualifier = cloneVarDecl(targetModel, sourceQualifier);
					if (targetQualifier == null) {
						hasErrors = true;
						break;
					}
					
					targetQualifiers.add(targetQualifier);
				}
				
				if (hasErrors)
					continue;
				
				MAssociationEnd targetEnd = 
						f.createAssociationEnd(
								targetModel.getClass(sourceEnd.cls().name()),
								sourceEnd.name(),
								sourceEnd.multiplicity(),
								sourceEnd.aggregationKind(),
								sourceEnd.isOrdered(),
								targetQualifiers);

				copyAnnotations(sourceEnd, targetEnd);
				try {
					targetAssoc.addAssociationEnd(targetEnd);
				} catch (MInvalidModelException e) {
					
				}
			}
			
			try {
				targetModel.addAssociation(targetAssoc);
			} catch (MInvalidModelException e) {

			}
		}

		// Write result
		try (OutputStream out = Files.newOutputStream(exportFile)) {
			MMVisitor v = new MMPrintVisitor(new PrintWriter(out, true));
			targetModel.processWithVisitor(v);
		}
	}
	
	/**
	 * @param sourceParentClass
	 * @param targetModel
	 * @return
	 */
	private MClass findMostSpecificExportedType(MClass sourceParentClass, MModel targetModel) {
		MClass parent = targetModel.getClass(sourceParentClass.name());
		
		if (parent != null)
			return parent;
		
		for (MClass otherParent : sourceParentClass.parents()) {
			parent = findMostSpecificExportedType(otherParent, targetModel); 
			if (parent != null)
				return parent;
		}
		
		return null;
	}

	private VarDecl cloneVarDecl(MModel targetModel, VarDecl v) {
		Type argType = OCLCompiler.compileType(
				targetModel, v.type().toString(),
				"Export", NullPrintWriter.getInstance());
		
		if (argType == null) {
			return null;
		}
		
		return new VarDecl(v.name(), argType);
	}
	
	/**
	 * @param source
	 * @param target
	 */
	private void copyAnnotations(Annotatable source, Annotatable target) {
		for (MElementAnnotation an : source.getAllAnnotations().values()) {
			target.addAnnotation(an);
		}
	}
}
