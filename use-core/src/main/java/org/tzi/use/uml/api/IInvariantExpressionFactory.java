package org.tzi.use.uml.api;

import org.tzi.use.uml.mm.MClassifier;

/**
 * Factory-Schnittstelle zum Erzeugen von Expressions, die speziell
 * für Klasseninvarianten benötigt werden. Implementierungen liegen im
 * OCL- oder sys-Layer; mm kennt nur dieses Interface und übergibt die
 * bereits vorbereitete Invariante.
 */
public interface IInvariantExpressionFactory {

    /**
     * Erzeugt die global auswertbare Invarianten-Expression aus dem
     * Invariantenkörper.
     */
    IExpression buildExpandedInvariant(IExpression body, boolean existential, MClassifier classifier, IVarDeclList vars);

    /**
     * Erzeugt eine Expression, die alle verletzenden Instanzen liefert
     * (z. B. C.allInstances()->reject(v1 | &lt;inv&gt;)).
     */
    IExpression buildViolatingInstancesExpr(IExpression expandedBody, boolean negated);

    /**
     * Erzeugt eine Expression, die alle erfüllenden Instanzen liefert
     * (z. B. C.allInstances()->select(v1 | &lt;inv&gt;)).
     */
    IExpression buildSatisfyingInstancesExpr(IExpression expandedBody, boolean negated);
}
