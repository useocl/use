package org.tzi.use.uml.ocl.type;

import java.util.HashSet;
import java.util.Set;

import org.tzi.use.uml.api.IType;
import org.tzi.use.uml.mm.MClassifier;

/**
 * Adapter that exposes an mm.MClassifier as an OCL low-level Type.
 */
public class ClassifierType extends TypeImpl {
    private final MClassifier classifier;

    public ClassifierType(MClassifier classifier) {
        this.classifier = classifier;
    }

    public MClassifier classifier() {
        return classifier;
    }

    @Override
    public StringBuilder toString(StringBuilder sb) {
        sb.append(classifier.name());
        return sb;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof ClassifierType) {
            return classifier.equals(((ClassifierType) obj).classifier);
        }
        if (obj instanceof MClassifier) {
            return classifier.equals(obj);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return classifier.hashCode();
    }

    @Override
    public boolean conformsTo(IType other) {
        if (other == null) {
            return false;
        }

        // Direkte Gleichheit oder Vergleich mit nacktem MClassifier
        if (other.equals(this) || (other instanceof MClassifier && classifier.equals(other))) {
            return true;
        }

        // Klassifikator-spezifische Konformität über UML-Hierarchie
        if (other.isKindOfClassifier(IType.VoidHandling.EXCLUDE_VOID)) {
            // Versuche, aus "other" einen MClassifier zu extrahieren (z.B. aus ClassifierType)
            MClassifier otherClf = TypeAdapters.asMClassifier(other);
            if (otherClf != null) {
                // Nutze die bestehende mm-Logik für Konformität (Untertyp von other?)
                return classifier.conformsTo(otherClf);
            }
            // Anderer Klassifikatortyp, den wir nicht mappen können -> nicht kompatibel
            return false;
        }

        // Alles ist konform zu OclAny
        if (other.isTypeOfOclAny()) {
            return true;
        }

        return false;
    }

    @Override
    public Type getLeastCommonSupertype(Type other) {
        if (other == null) {
            return this;
        }

        // Spezialfall: Beteiligung von OclVoid (null)
        // Entspricht der Logik in TypeImpl.getLeastCommonSupertype:
        // Ist eine Seite OclVoid, dann ist der andere Typ der LCS.
        if (this.isTypeOfVoidType()) {
            return other;
        }
        if (other.isTypeOfVoidType()) {
            return this;
        }

        // Spezieller Fall: beide Seiten sind ClassifierType -> gemeinsamen UML-Klassifikator bestimmen
        if (other instanceof ClassifierType) {
            MClassifier otherClf = ((ClassifierType) other).classifier();

            // Nutze die mm-Logik direkt auf den beiden MClassifier-Instanzen
            IType lcs = classifier.getLeastCommonSupertype(otherClf);

            if (lcs instanceof MClassifier) {
                // Gemeinsamer Modellklassifikator -> wieder als ClassifierType verpacken
                return TypeFactory.mkClassifierType((MClassifier) lcs);
            }
            if (lcs instanceof Type) {
                // Falls die mm-Logik bereits einen OCL-Typ liefert, diesen direkt verwenden
                return (Type) lcs;
            }

            // Kein sinnvoller gemeinsamer Modellklassifikator -> OclAny
            return TypeFactory.mkOclAny();
        }

        // Fallback: bisheriges Verhalten für Nicht-ClassifierType-Kombinationen beibehalten
        IType res = classifier.getLeastCommonSupertype((IType) other);
        if (res instanceof Type) {
            return (Type) res;
        }
        return TypeFactory.mkOclAny();
    }

    @Override
    public Set<Type> allSupertypes() {
        Set<Type> res = new HashSet<>();
        res.add(TypeFactory.mkOclAny());
        res.add(this);
        for (MClassifier p : classifier.allParents()) {
            res.add(TypeFactory.mkClassifierType(p));
        }
        return res;
    }

    @Override
    public boolean isTypeOfClassifier() {
        return true;
    }

    @Override
    public boolean isKindOfClassifier(IType.VoidHandling h) {
        return true;
    }

    @Override
    public boolean isTypeOfClass() {
        return classifier.isKindOfClass(IType.VoidHandling.EXCLUDE_VOID) || classifier.isTypeOfClass();
    }

    @Override
    public boolean isTypeOfDataType() {
        return classifier.isTypeOfDataType();
    }

    @Override
    public boolean isTypeOfOclAny() {
        return false;
    }
}
