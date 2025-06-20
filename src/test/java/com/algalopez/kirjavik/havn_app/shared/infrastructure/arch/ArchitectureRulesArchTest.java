package com.algalopez.kirjavik.havn_app.shared.infrastructure.arch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

class ArchitectureRulesArchTest {

  private static final JavaClasses CLASSES =
      new ClassFileImporter().importPackages("com.algalopez.kirjavik.havn_app");

  @Test
  void architecture_shouldFollowDependencyRule() {
    Architectures.LayeredArchitecture layeredArchitecture =
        Architectures.layeredArchitecture()
            .consideringAllDependencies()
            .layer("Api")
            .definedBy("..kirjavik.havn_app.*.api..", "..kirjavik.shared.api..")
            .layer("Application")
            .definedBy("..kirjavik.havn_app.*.application..", "..kirjavik.shared.application..")
            .layer("Domain")
            .definedBy("..kirjavik.havn_app.*.domain..", "..kirjavik.shared.domain..")
            .layer("Infrastructure")
            .definedBy(
                "..kirjavik.havn_app.*.infrastructure..", "..kirjavik.shared.infrastructure..");

    layeredArchitecture
        .whereLayer("Api")
        .mayOnlyBeAccessedByLayers("Api", "Infrastructure")
        .whereLayer("Application")
        .mayOnlyBeAccessedByLayers("Api", "Application", "Infrastructure")
        .whereLayer("Domain")
        .mayOnlyBeAccessedByLayers("Api", "Application", "Domain", "Infrastructure")
        .whereLayer("Infrastructure")
        .mayOnlyBeAccessedByLayers("Infrastructure");

    layeredArchitecture.allowEmptyShould(true).check(CLASSES);
  }

  @Test
  void architecture_shouldFollowHexagonalStructure() {
    ArchRule archRule =
        ArchRuleDefinition.noClasses()
            .should()
            .resideOutsideOfPackages(
                "..kirjavik.havn_app.*.api..",
                "..kirjavik.havn_app.*.application..",
                "..kirjavik.havn_app.*.domain..",
                "..kirjavik.havn_app.*.infrastructure..");

    archRule.check(CLASSES);
  }
}
