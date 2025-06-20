package com.algalopez.kirjavik.havn_app.shared.infrastructure.arch;

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
      new ClassFileImporter().importPackages("com.algalopez.kirjavik.havn_app");

  @Test
  void modules_shouldHaveNoCyclicDependencies() {
    SliceRule sliceRule =
        SlicesRuleDefinition.slices()
            .matching("..kirjavik.havn_app.(*)..")
            .should()
            .beFreeOfCycles();

    sliceRule.check(CLASSES);
  }

  @Test
  void sharedModule_shouldNotAccessOtherModules() {
    DescribedPredicate<JavaClass> sharedModuleAccessAllowed =
        JavaClass.Predicates.resideOutsideOfPackages("..kirjavik..") // sdk classes, libraries, etc
            .or(JavaClass.Predicates.resideInAnyPackage("..kirjavik.havn_app.shared.."))
            .or(JavaClass.Predicates.resideInAnyPackage("..kirjavik.shared.."));

    ArchRule rule =
        ArchRuleDefinition.classes()
            .that()
            .resideInAnyPackage("..kirjavik.havn_app.shared..")
            .should()
            .onlyAccessClassesThat(sharedModuleAccessAllowed);

    rule.check(CLASSES);
  }

  @Test
  void bookItem_shouldNotAccessOtherModules_exceptSharedOrThoughApi() {
    DescribedPredicate<JavaClass> sampleModuleAccessAllowed =
        JavaClass.Predicates.resideOutsideOfPackages("..kirjavik.havn_app..")
            .or(
                JavaClass.Predicates.resideInAnyPackage(
                    "..kirjavik.havn_app.book_item..",
                    "..kirjavik.havn_app.shared..",
                    "..kirjavik.shared..",
                    "..kirjavik.havn_app.*.api.."));

    ArchRule rule =
        ArchRuleDefinition.classes()
            .that()
            .resideInAnyPackage("..kirjavik.havn_app.book_item..")
            .should()
            .onlyAccessClassesThat(sampleModuleAccessAllowed);

    rule.check(CLASSES);
  }
}
