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

package org.tzi.use.uml.mm;

import java.util.List;

import org.tzi.use.uml.api.IExpression;
import org.tzi.use.uml.api.IInvariantExpressionFactory;
import org.tzi.use.uml.api.InvariantExpressionException;

/**
 * Model factory used by generator to add loaded class invariants.
 * 
 * @author Frank Hilken
 */
public class GeneratorModelFactory extends ModelFactory {

	public MClassInvariant createClassInvariant(String name, List<String> vars,
				MClassifier cf, IExpression inv, boolean isExistential)
				throws InvariantExpressionException {
		IInvariantExpressionFactory factory = org.tzi.use.uml.api.InvariantExpressionFactoryProvider.get();
		return new MClassInvariant(name, vars, cf, inv, isExistential, true, false, factory);
	}

}
