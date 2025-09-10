package com.algalopez.kirjavik.backoffice_app.shared.infrastructure.arch;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.dependencies.SliceRule;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.Test;

class ModuleDependencyRulesArchTest {

  private static final JavaClasses CLASSES =
      new ClassFileImporter().importPackages("com.algalopez.kirjavik.backoffice_app");

  @Test
  void modules_shouldHaveNoCyclicDependencies() {
    SliceRule sliceRule =
        SlicesRuleDefinition.slices()
            .matching("..kirjavik.backoffice_app.(*)..")
            .should()
            .beFreeOfCycles();

    sliceRule.check(CLASSES);
  }

  @Test
  void sharedModule_shouldNotAccessOtherModules() {
    DescribedPredicate<JavaClass> sharedModuleAccessAllowed =
        JavaClass.Predicates.resideOutsideOfPackages("..kirjavik..") // sdk classes, libraries, etc
            .or(JavaClass.Predicates.resideInAnyPackage("..kirjavik.backoffice_app.shared.."))
            .or(JavaClass.Predicates.resideInAnyPackage("..kirjavik.shared.."));

    ArchRule rule =
        ArchRuleDefinition.classes()
            .that()
            .resideInAnyPackage("..kirjavik.backoffice_app.shared..")
            .should()
            .onlyAccessClassesThat(sharedModuleAccessAllowed);

    rule.check(CLASSES);
  }

  @Test
  void book_shouldNotAccessOtherModules_exceptSharedOrThoughApi() {
    DescribedPredicate<JavaClass> sampleModuleAccessAllowed =
        JavaClass.Predicates.resideOutsideOfPackages("..kirjavik.backoffice_app..")
            .or(
                JavaClass.Predicates.resideInAnyPackage(
                    "..kirjavik.backoffice_app.book..",
                    "..kirjavik.backoffice_app.shared..",
                    "..kirjavik.shared..",
                    "..kirjavik.backoffice_app.*.api.."));

    ArchRule rule =
        ArchRuleDefinition.classes()
            .that()
            .resideInAnyPackage("..kirjavik.backoffice_app.book..")
            .should()
            .onlyAccessClassesThat(sampleModuleAccessAllowed);

    rule.check(CLASSES);
  }

  @Test
  void user_shouldNotAccessOtherModules_exceptSharedOrThoughApi() {
    DescribedPredicate<JavaClass> sampleModuleAccessAllowed =
        JavaClass.Predicates.resideOutsideOfPackages("..kirjavik.backoffice_app..")
            .or(
                JavaClass.Predicates.resideInAnyPackage(
                    "..kirjavik.backoffice_app.user..",
                    "..kirjavik.backoffice_app.shared..",
                    "..kirjavik.shared..",
                    "..kirjavik.backoffice_app.*.api.."));

    ArchRule rule =
        ArchRuleDefinition.classes()
            .that()
            .resideInAnyPackage("..kirjavik.backoffice_app.user..")
            .should()
            .onlyAccessClassesThat(sampleModuleAccessAllowed);

    rule.check(CLASSES);
  }
}
