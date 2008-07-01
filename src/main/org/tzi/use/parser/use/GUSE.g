/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
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

/* $ProjectHeader: use 0.393 Wed, 16 May 2007 14:10:28 +0200 opti $ */

grammar GUSE;

/*
options {
    exportVocab = GUSE;
    defaultErrorHandler = true;
    buildAST = false;
    k = 5;
    //codeGenMakeSwitchThreshold = 2;
    //codeGenBitsetTestThreshold = 3;
}*/

import GUSEBase, GOCLLexerRules;

// set package for all generated classes
@header { 
/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
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

package org.tzi.use.parser.use; 

// ------------------------------------
//  USE parser
// ------------------------------------

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.PrintWriter;
import org.tzi.use.parser.ParseErrorHandler;
import org.tzi.use.parser.ocl.*;
}
 
@members {
	public void init(ParseErrorHandler handler) {
		fParseErrorHandler = handler;
		
		this.gGUSEBase.init(handler);
	}

	/* Overridden methods. */
	private ParseErrorHandler fParseErrorHandler;
    
	public void emitErrorMessage(String msg) {
       	fParseErrorHandler.reportError(msg);
	}
	
	/** Parser warning-reporting function */
    public void reportWarning(String s) {
        if (getSourceName() == null) {
            System.err.println("warning: " + s);
        }
        else {
            System.err.println(getSourceName() + ": warning: " + s);
        }
    }
}

@lexer::header {
/*
 * USE - UML based specification environment
 * Copyright (C) 1999-2004 Mark Richters, University of Bremen
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

package org.tzi.use.parser.use; 

import org.tzi.use.parser.ParseErrorHandler;
// ------------------------------------
//  USE parser
// ------------------------------------
}

@lexer::members
{
	public void init(ParseErrorHandler handler) {
		fParseErrorHandler = handler;
	}
	
	public String getFilename() {
        return fParseErrorHandler.getFileName();
    }
    
	/* Overridden methods. */
	private ParseErrorHandler fParseErrorHandler;
    
	public void emitErrorMessage(String msg) {
       	fParseErrorHandler.reportError(msg);
	}
}
// grammar start

/* ------------------------------------
  model ::= 
    "model" id
    { enumTypeDefinition } 
    {   classDefinition 
      | associationDefinition 
      | "constraints" { invariant | prePost }
    } 
*/
model returns [ASTModel n]
:
    'model' modelName=IDENT { $n = new ASTModel($modelName); }
    ( e=enumTypeDefinition { $n.addEnumTypeDef($e.n); } )*
    (   ( generalClassDefinition[$n] )
      | ( a=associationDefinition { $n.addAssociation($a.n); } )
      | ( 'constraints'
          (   cons=invariant { $n.addConstraint($cons.n); } 
            | ppc=prePost { $n.addPrePost($ppc.n); } 
          )*  
        )
    )*
    EOF
    ;