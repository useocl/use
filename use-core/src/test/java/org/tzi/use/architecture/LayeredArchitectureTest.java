package org.tzi.use.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import com.tngtech.archunit.library.Architectures;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class LayeredArchitectureTest {

    private static final String USE_ROOT = "C\\Users\\alici\\Uni\\BA\\use";
    private static final String USE_CORE = USE_ROOT + "\\use-core";
    private static final String USE_GUI = USE_ROOT + "\\use-gui";

    private final JavaClasses classes = new ClassFileImporter().importPaths(USE_CORE, USE_GUI);

    // funktioniert nicht, weil definedBy ein package erwartet und keinen Dateipfad
            @Test
            public void verify_layered_architecture() {
                Architectures.LayeredArchitecture architecture = layeredArchitecture()
                        .consideringAllDependencies()
                        .layer("Core").definedBy()
                        .layer("GUI").definedBy(USE_GUI)
                        .whereLayer("Core").mayOnlyBeAccessedByLayers("GUI")
                        .whereLayer("GUI").mayNotBeAccessedByAnyLayer();
        
                architecture.check(classes);
            }
}
