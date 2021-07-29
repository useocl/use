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

package org.tzi.use.parser.use;

import java.util.HashMap;
import java.util.Map;

import org.antlr.runtime.Token;
import org.tzi.use.parser.AST;
import org.tzi.use.uml.mm.MElementAnnotation;

/**
 * AST node representing an annotation
 * @author Lars Hamann
 *
 */
public class ASTAnnotation extends AST {
	
	private Token name;
	
	private Map<Token, Token> values;
	
	public ASTAnnotation(Token name) {
		this.name = name;
	}

	public void setValues(Map<Token, Token> values) {
		this.values = values;
	}
	
	public MElementAnnotation gen() {
		if (values != null && !values.isEmpty()) {
			Map<String, String> annotationValues = new HashMap<String, String>(); 
			String annoName, annoValue;
			
			for (Map.Entry<Token, Token> value : values.entrySet()) {
				annoName = value.getKey().getText();
				annoValue = value.getValue().getText();
				annoValue = annoValue.substring(1, annoValue.length() - 1);
				annotationValues.put(annoName, annoValue);
			}
			
			return new MElementAnnotation(name.getText(), annotationValues);
		} else {
			return new MElementAnnotation(name.getText());
		}
	}
}
