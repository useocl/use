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

import java.util.List;

import org.tzi.use.analysis.coverage.BasicCoverageData;
import org.tzi.use.uml.mm.MClass;
import org.tzi.use.uml.mm.MClassifier;

/**
 * Calculates the coverage informations about a 
 * ASSL instruction list.
 * @author Lars Hamann
 *
 */
public class BasicInstructionCoverageCalulator implements InstructionVisitor {
	BasicCoverageData coverage;
	
	/**
	 * @param subList
	 */
	public BasicInstructionCoverageCalulator() {}

	/**
	 * @return
	 */
	public BasicCoverageData calcualteCoverage(List<GInstruction> instrList) {
		this.coverage = new BasicCoverageData();
		for (GInstruction instr : instrList) {
			instr.processWithVisitor(this);
		}
		return coverage;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitAttributeAssignment(org.tzi.use.gen.assl.statics.GAttributeAssignment)
	 */
	@Override
	public void visitAttributeAssignment(
			GAttributeAssignment gAttributeAssignment) {
		gAttributeAssignment.sourceInstr().processWithVisitor(this);
		this.coverage.getCoveredAttributes().add(gAttributeAssignment.targetAttribute());		
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitIfThenElse(org.tzi.use.gen.assl.statics.GIfThenElse)
	 */
	@Override
	public void visitIfThenElse(GIfThenElse gIfThenElse) {
		gIfThenElse.thenInstructionList().processWithVisitor(this);
		gIfThenElse.elseInstructionList().processWithVisitor(this);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitInstrAny_Seq(org.tzi.use.gen.assl.statics.GInstrAny_Seq)
	 */
	@Override
	public void visitInstrAny_Seq(GInstrAny_Seq gInstrAny_Seq) {
		gInstrAny_Seq.sequenceInstr().processWithVisitor(this);		
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitInstrCreate_C(org.tzi.use.gen.assl.statics.GInstrCreate_C)
	 */
	@Override
	public void visitInstrCreate_C(GInstrCreate_C gInstrCreate_C) {
		addClassCoverage(gInstrCreate_C.cls());
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitInstrCreateN_C_Integer(org.tzi.use.gen.assl.statics.GInstrCreateN_C_Integer)
	 */
	@Override
	public void visitInstrCreateN_C_Integer(
			GInstrCreateN_C_Integer gInstrCreateN_C_Integer) {
		gInstrCreateN_C_Integer.integerInstr().processWithVisitor(this);
		addClassCoverage(gInstrCreateN_C_Integer.cls());
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitInstrDelete_Assoc_Linkends(org.tzi.use.gen.assl.statics.GInstrDelete_Assoc_Linkends)
	 */
	@Override
	public void visitInstrDelete_Assoc_Linkends(
			GInstrDelete_Assoc_Linkends gInstrDelete_Assoc_Linkends) {
		
		for (GInstruction instr : gInstrDelete_Assoc_Linkends.linkEnds()) {
			instr.processWithVisitor(this);
		}
		
		coverage.getCoveredAssociations().add(gInstrDelete_Assoc_Linkends.association());
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitInstrDelete_Object(org.tzi.use.gen.assl.statics.GInstrDelete_Object)
	 */
	@Override
	public void visitInstrDelete_Object(GInstrDelete_Object gInstrDelete_Object) {
		gInstrDelete_Object.objectInstr().processWithVisitor(this);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitInstrInsert_Assoc_Linkends(org.tzi.use.gen.assl.statics.GInstrInsert_Assoc_Linkends)
	 */
	@Override
	public void visitInstrInsert_Assoc_Linkends(
			GInstrInsert_Assoc_Linkends gInstrInsert_Assoc_Linkends) {
		this.coverage.getCoveredAssociations().add(gInstrInsert_Assoc_Linkends.association());
		for (GInstruction instr : gInstrInsert_Assoc_Linkends.linkEnds()) {
			instr.processWithVisitor(this);
		}
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitInstrSub_Seq_Integer(org.tzi.use.gen.assl.statics.GInstrSub_Seq_Integer)
	 */
	@Override
	public void visitInstrSub_Seq_Integer(
			GInstrSub_Seq_Integer gInstrSub_Seq_Integer) {
		gInstrSub_Seq_Integer.sequenceInstr().processWithVisitor(this);
		gInstrSub_Seq_Integer.integerInstr().processWithVisitor(this);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitInstrSub_Seq(org.tzi.use.gen.assl.statics.GInstrSub_Seq)
	 */
	@Override
	public void visitInstrSub_Seq(GInstrSub_Seq gInstrSub_Seq) {
		gInstrSub_Seq.sequenceInstr().processWithVisitor(this);	
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitInstrTry_Assoc_LinkendSeqs(org.tzi.use.gen.assl.statics.GInstrTry_Assoc_LinkendSeqs)
	 */
	@Override
	public void visitInstrTry_Assoc_LinkendSeqs(
			GInstrTry_Assoc_LinkendSeqs gInstrTry_Assoc_LinkendSeqs) {
		for (GInstruction instr : gInstrTry_Assoc_LinkendSeqs.linkendSequences()) {
			instr.processWithVisitor(this);
		}
		this.coverage.getCoveredAssociations().add(gInstrTry_Assoc_LinkendSeqs.association());
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitInstrTry_Seq(org.tzi.use.gen.assl.statics.GInstrTry_Seq)
	 */
	@Override
	public void visitInstrTry_Seq(GInstrTry_Seq gInstrTry_Seq) {
		gInstrTry_Seq.sequenceInstr().processWithVisitor(this);		
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitLoop(org.tzi.use.gen.assl.statics.GLoop)
	 */
	@Override
	public void visitLoop(GLoop gLoop) {
		gLoop.instructionList().processWithVisitor(this);		
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitOCLExpression(org.tzi.use.gen.assl.statics.GOCLExpression)
	 */
	@Override
	public void visitOCLExpression(GOCLExpression goclExpression) {
				
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitVariableAssignment(org.tzi.use.gen.assl.statics.GVariableAssignment)
	 */
	@Override
	public void visitVariableAssignment(GVariableAssignment gVariableAssignment) {
		gVariableAssignment.sourceInstr().processWithVisitor(this);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitInstrASSLCall(org.tzi.use.gen.assl.statics.GInstrASSLCall)
	 */
	@Override
	public void visitInstrASSLCall(GInstrASSLCall gInstrASSLCall) {
				
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitInstrOpEnter(org.tzi.use.gen.assl.statics.GInstrOpEnter)
	 */
	@Override
	public void visitInstrOpEnter(GInstrOpEnter gInstrOpEnter) {
				
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitInstrOpExit(org.tzi.use.gen.assl.statics.GInstrOpExit)
	 */
	@Override
	public void visitInstrOpExit(GInstrOpExit gInstrOpExit) {
				
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitInstrCreate_AC(org.tzi.use.gen.assl.statics.GInstrCreate_AC)
	 */
	@Override
	public void visitInstrCreate_AC(GInstrCreate_AC gInstrCreate_AC) {
		for (GInstruction instr : gInstrCreate_AC.getLinkedObjects()) {
			instr.processWithVisitor(this);
		}
		this.coverage.getCoveredAssociations().add(gInstrCreate_AC.getAssociationClass());
		addClassCoverage(gInstrCreate_AC.getAssociationClass());
	}

	/**
	 * @param gInstrCreate_AC
	 */
	private void addClassCoverage(MClass cls) {
		this.coverage.getCoveredClasses().add(cls);
		for (MClassifier parent : cls.allParents()) {
			this.coverage.getCoveredClasses().add((MClass)parent);
		}
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitInstrucionList(org.tzi.use.gen.assl.statics.GInstructionList)
	 */
	@Override
	public void visitInstrucionList(GInstructionList gInstructionList) {
		for (GInstruction instr : gInstructionList.instructions()) {
			instr.processWithVisitor(this);
		}
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitInstrTry_AssocClass_LinkendSeqs(org.tzi.use.gen.assl.statics.GInstrTry_AssocClass_LinkendSeqs)
	 */
	@Override
	public void visitInstrTry_AssocClass_LinkendSeqs(
			GInstrTry_AssocClass_LinkendSeqs gInstrTry_AssocClass_LinkendSeqs) {
	
		for (GInstruction instr : gInstrTry_AssocClass_LinkendSeqs.linkendSequences()) {
			instr.processWithVisitor(this);
		}
		
		this.coverage.getCoveredAssociations().add(gInstrTry_AssocClass_LinkendSeqs.getAssociationClass());
		addClassCoverage(gInstrTry_AssocClass_LinkendSeqs.getAssociationClass());
		
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitBarrier(org.tzi.use.gen.assl.statics.GInstrBarrier)
	 */
	@Override
	public void visitBarrier(GInstrBarrier gInstrBarrier) { }

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.InstructionVisitor#visitInstrTry_Attribute(org.tzi.use.gen.assl.statics.GInstrTry_Attribute)
	 */
	@Override
	public void visitInstrTry_Attribute(GInstrTry_Attribute gInstrTry_Attribute) {
		this.coverage.getCoveredAttributes().add(gInstrTry_Attribute.getAttribute());		
	}

}
