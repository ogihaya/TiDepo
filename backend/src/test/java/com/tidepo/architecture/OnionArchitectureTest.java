package com.tidepo.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

/**
 * オニオンアーキテクチャの依存方向を検証する。
 *
 * <p>依存は内向き（domain &lt;- application &lt;- infrastructure/presentation &lt;- config）。
 * 各層が「どの層から参照されてよいか」を定めることで、内側の層が外側へ依存していないことを担保する。
 * 例: domain が infrastructure を参照すると「infrastructure が domain から参照される」ことになり違反となる。
 */
@AnalyzeClasses(packages = "com.tidepo", importOptions = ImportOption.DoNotIncludeTests.class)
class OnionArchitectureTest {

    @ArchTest
    static final ArchRule onion_dependencies_point_inward = Architectures.layeredArchitecture()
            .consideringOnlyDependenciesInLayers()
            // 雛形段階では各層が空（package-info のみ）のため空層を許容する。
            // 実クラスが入った時点で依存方向ルールは有効に働く。
            .withOptionalLayers(true)
            .layer("Domain")
            .definedBy("com.tidepo.domain..")
            .layer("Application")
            .definedBy("com.tidepo.application..")
            .layer("Infrastructure")
            .definedBy("com.tidepo.infrastructure..")
            .layer("Presentation")
            .definedBy("com.tidepo.presentation..")
            .layer("Config")
            .definedBy("com.tidepo.config..")
            .whereLayer("Presentation")
            .mayOnlyBeAccessedByLayers("Config")
            .whereLayer("Infrastructure")
            .mayOnlyBeAccessedByLayers("Config")
            .whereLayer("Application")
            .mayOnlyBeAccessedByLayers("Presentation", "Infrastructure", "Config")
            .whereLayer("Domain")
            .mayOnlyBeAccessedByLayers("Application", "Infrastructure", "Presentation", "Config");
}
