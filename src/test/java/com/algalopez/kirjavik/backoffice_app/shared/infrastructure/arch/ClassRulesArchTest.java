package com.algalopez.kirjavik.backoffice_app.shared.infrastructure.arch;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

class ClassRulesArchTest {

  private static final JavaClasses PRODUCTION_CLASSES =
      new ClassFileImporter()
          .withImportOption(new ImportOption.DoNotIncludeTests())
          .importPackages("com.algalopez.kirjavik.backoffice_app");

  @Test
  void adapterClasses_shouldImplementPorts() {
    ArchRule archRule =
        classes()
            .that()
            .resideInAnyPackage("..kirjavik.backoffice_app.*.infrastructure.adapter")
            .should()
            .beTopLevelClasses()
            .andShould()
            .implement(resideInAPackage("..kirjavik.backoffice_app.*.domain.port"));

    archRule.allowEmptyShould(true).check(PRODUCTION_CLASSES);
  }

  @Test
  void dtoClasses_shouldBeRecords() {
    ArchRule archRule =
        classes()
            .that()
            .haveSimpleNameEndingWith("Response")
            .or()
            .haveSimpleNameEndingWith("Request")
            .or()
            .haveSimpleNameEndingWith("Command")
            .or()
            .haveSimpleNameEndingWith("Query")
            .should()
            .beRecords();

    archRule.allowEmptyShould(true).check(PRODUCTION_CLASSES);
  }

  @Test
  void serviceClasses_shouldBeFinal() {
    ArchRule archRule =
        classes()
            .that()
            .resideInAnyPackage("..kirjavik.backoffice_app.*.domain.service")
            .should()
            .haveModifier(JavaModifier.FINAL);

    archRule.allowEmptyShould(true).check(PRODUCTION_CLASSES);
  }

  @Test
  void portClasses_shouldBeInterfaces() {
    ArchRule archRule =
        classes()
            .that()
            .resideInAnyPackage("..kirjavik.backoffice_app.*.domain.port")
            .should()
            .beInterfaces();

    archRule.allowEmptyShould(true).check(PRODUCTION_CLASSES);
  }
}
