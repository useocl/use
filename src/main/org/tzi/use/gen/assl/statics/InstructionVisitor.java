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

package org.tzi.use.gen.assl.statics;

/**
 * Visitor interface for generator instructions
 * @author Lars Hamann
 *
 */
public interface InstructionVisitor {
	
	/**
	 * @param gAttributeAssignment
	 */
	void visitAttributeAssignment(GAttributeAssignment gAttributeAssignment);

	/**
	 * @param gIfThenElse
	 */
	void visitIfThenElse(GIfThenElse gIfThenElse);

	/**
	 * @param gInstrAny_Seq
	 */
	void visitInstrAny_Seq(GInstrAny_Seq gInstrAny_Seq);

	/**
	 * @param gInstrCreate_C
	 */
	void visitInstrCreate_C(GInstrCreate_C gInstrCreate_C);

	/**
	 * @param gInstrCreateN_C_Integer
	 */
	void visitInstrCreateN_C_Integer(
			GInstrCreateN_C_Integer gInstrCreateN_C_Integer);

	/**
	 * @param gInstrDelete_Assoc_Linkends
	 */
	void visitInstrDelete_Assoc_Linkends(
			GInstrDelete_Assoc_Linkends gInstrDelete_Assoc_Linkends);

	/**
	 * @param gInstrDelete_Object
	 */
	void visitInstrDelete_Object(GInstrDelete_Object gInstrDelete_Object);

	/**
	 * @param gInstrInsert_Assoc_Linkends
	 */
	void visitInstrInsert_Assoc_Linkends(
			GInstrInsert_Assoc_Linkends gInstrInsert_Assoc_Linkends);

	/**
	 * @param gInstrSub_Seq_Integer
	 */
	void visitInstrSub_Seq_Integer(GInstrSub_Seq_Integer gInstrSub_Seq_Integer);

	/**
	 * @param gInstrSub_Seq
	 */
	void visitInstrSub_Seq(GInstrSub_Seq gInstrSub_Seq);

	/**
	 * @param gInstrTry_Assoc_LinkendSeqs
	 */
	void visitInstrTry_Assoc_LinkendSeqs(
			GInstrTry_Assoc_LinkendSeqs gInstrTry_Assoc_LinkendSeqs);

	/**
	 * @param gInstrTry_Seq
	 */
	void visitInstrTry_Seq(GInstrTry_Seq gInstrTry_Seq);

	/**
	 * @param gLoop
	 */
	void visitLoop(GLoop gLoop);

	/**
	 * @param goclExpression
	 */
	void visitOCLExpression(GOCLExpression goclExpression);

	/**
	 * @param gVariableAssignment
	 */
	void visitVariableAssignment(GVariableAssignment gVariableAssignment);

	/**
	 * @param gInstrASSLCall
	 */
	void visitInstrASSLCall(GInstrASSLCall gInstrASSLCall);

	/**
	 * @param gInstrOpEnter
	 */
	void visitInstrOpEnter(GInstrOpEnter gInstrOpEnter);

	/**
	 * @param gInstrOpExit
	 */
	void visitInstrOpExit(GInstrOpExit gInstrOpExit);

	/**
	 * @param gInstrCreate_AC
	 */
	void visitInstrCreate_AC(GInstrCreate_AC gInstrCreate_AC);

	/**
	 * @param gInstructionList
	 */
	void visitInstrucionList(GInstructionList gInstructionList);

	/**
	 * @param gInstrTry_AssocClass_LinkendSeqs
	 */
	void visitInstrTry_AssocClass_LinkendSeqs(
			GInstrTry_AssocClass_LinkendSeqs gInstrTry_AssocClass_LinkendSeqs);

	/**
	 * @param gInstrBarrier
	 */
	void visitBarrier(GInstrBarrier gInstrBarrier);

	/**
	 * @param gInstrTry_Attribute
	 */
	void visitInstrTry_Attribute(GInstrTry_Attribute gInstrTry_Attribute);
}
