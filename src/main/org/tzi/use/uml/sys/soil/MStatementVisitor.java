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

/**
 * Visitor interface for SOIL statements.
 * @author Frank Hilken
 */
public interface MStatementVisitor {
	
	void visit(MAttributeAssignmentStatement s) throws Exception;
	void visit(MBlockStatement s) throws Exception;
	void visit(MConditionalExecutionStatement s) throws Exception;
	void visit(MEmptyStatement s) throws Exception;
	void visit(MEnterOperationStatement s) throws Exception;
	void visit(MExitOperationStatement s) throws Exception;
	void visit(MIterationStatement s) throws Exception;
	void visit(MLinkDeletionStatement s) throws Exception;
	void visit(MLinkInsertionStatement s) throws Exception;
	void visit(MNewLinkObjectStatement s) throws Exception;
	void visit(MNewObjectStatement s) throws Exception;
	void visit(MObjectDestructionStatement s) throws Exception;
	void visit(MObjectRestorationStatement s) throws Exception;
	void visit(MOperationCallInverseStatement s) throws Exception;
	void visit(MLibraryOperationCallStatement s) throws Exception;
	void visit(MObjectOperationCallStatement s) throws Exception;
	void visit(MSequenceStatement s) throws Exception;
	void visit(MVariableAssignmentStatement s) throws Exception;
	void visit(MVariableDestructionStatement s) throws Exception;
	void visit(MWhileStatement s) throws Exception;
	
}
