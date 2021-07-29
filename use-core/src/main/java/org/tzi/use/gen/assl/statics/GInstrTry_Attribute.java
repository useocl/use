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

import org.tzi.use.gen.assl.dynamics.GEvalInstrTry_Attribute;
import org.tzi.use.gen.assl.dynamics.GEvalInstruction;
import org.tzi.use.uml.mm.MAttribute;

/**
 * Static part for an attribute try instruction.
 * @author Lars Hamann
 *
 */
public class GInstrTry_Attribute extends GInstrTry implements GInstruction {

	private MAttribute attribute;
    
	private GOCLExpression objects;
	
	private GOCLExpression values;
	
    
	/**
	 * @param range
	 * @param attribute2
	 * @param values2
	 */
	public GInstrTry_Attribute(GOCLExpression range, MAttribute attribute,
			GOCLExpression values) {
		this.attribute = attribute;
		this.objects = range;
		this.values = values;
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.GInstruction#processWithVisitor(org.tzi.use.gen.assl.statics.InstructionVisitor)
	 */
	@Override
	public void processWithVisitor(InstructionVisitor v) {
		v.visitInstrTry_Attribute(this);
	}

	/* (non-Javadoc)
	 * @see org.tzi.use.gen.assl.statics.GInstruction#createEvalInstr()
	 */
	@Override
	public GEvalInstruction createEvalInstr() {
		++createdEvalTries;
		return new GEvalInstrTry_Attribute(this, firstTry && createdEvalTries == 1);
	}

	/**
	 * @return the attribute
	 */
	public MAttribute getAttribute() {
		return attribute;
	}

	/**
	 * @return the objects
	 */
	public GOCLExpression getObjects() {
		return objects;
	}

	/**
	 * @return the values
	 */
	public GOCLExpression getValues() {
		return values;
	}
	
	@Override
	public String toString() {
		return "Try([" + objects.toString() + "], " + attribute.name() + ", [" + values.toString() + "])";
	}
}
