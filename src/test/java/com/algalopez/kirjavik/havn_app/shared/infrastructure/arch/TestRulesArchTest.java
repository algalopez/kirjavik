package com.algalopez.kirjavik.havn_app.shared.infrastructure.arch;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class TestRulesArchTest {

  private static final String UNIT_TEST = "Test";
  private static final String INTEGRATION_TEST = "IntegrationTest";
  private static final String API_TEST = "ApiTest";

  private static final JavaClasses PRODUCTION_CLASSES =
      new ClassFileImporter().importPackages("com.algalopez.kirjavik.havn_app");

  @Test
  void ControllerClasses_shouldHaveApiTests() {
    ArchRule archRule =
        classes()
            .that()
            .haveSimpleNameEndingWith("Controller")
            .should(haveCorrespondingTest(API_TEST));

    archRule.allowEmptyShould(true).check(PRODUCTION_CLASSES);
  }

  @Test
  void useCaseClasses_shouldHaveUnitAndIntegrationTests() {
    ArchRule archRule =
        classes()
            .that()
            .haveSimpleNameEndingWith("Actor")
            .should(haveCorrespondingTest(UNIT_TEST))
            .andShould(haveCorrespondingTest(INTEGRATION_TEST));

    archRule.allowEmptyShould(true).check(PRODUCTION_CLASSES);
  }

  private static ArchCondition<JavaClass> haveCorrespondingTest(String expectedTestSuffix) {
    return new ArchCondition<>("have a corresponding test class") {
      @Override
      public void check(JavaClass item, ConditionEvents events) {
        String controllerClassName = item.getSimpleName();
        String testClassName = controllerClassName + expectedTestSuffix;

        Optional<JavaClass> testClass =
            PRODUCTION_CLASSES.stream()
                .filter(clazz -> clazz.getSimpleName().equals(testClassName))
                .findFirst();

        if (testClass.isEmpty()) {
          String message =
              String.format(
                  "Controller %s does not have a corresponding test class %s",
                  controllerClassName, testClassName);
          events.add(SimpleConditionEvent.violated(item, message));
        }
      }
    };
  }
}
