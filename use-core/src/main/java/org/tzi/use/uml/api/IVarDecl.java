package org.tzi.use.uml.api;

import org.tzi.use.parser.SrcPos;
import org.tzi.use.uml.api.IType;

/**
 * API-Interface für eine Variable-Declaration (nur das benötigtem Verhalten
 * aus dem mm-Layer). Implementiert von ocl.VarDecl.
 *
 * WICHTIG: Schnittstelle darf keine OCL-Typen in Signaturen enthalten.
 */
public interface IVarDecl {
    /** Liefert den Namen der deklarierten Variable. */
    String name();

    /** Liefert die Quellposition falls vorhanden (kann null sein). */
    SrcPos getSourcePosition();

   /** Returns the variable's type as API abstraction (no OCL types in signature). */
   IType type();
}
