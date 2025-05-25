package com.algalopez.kirjavik.shared.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import io.quarkus.hibernate.orm.PersistenceUnit;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

@QuarkusTest
class QuarkusContextIntegrationTest {

  @Inject
  @PersistenceUnit("backoffice")
  EntityManager entityManager;

  @Test
  public void contextLoads() {
    // Tests that the context loads
    assertThat(entityManager).isNotNull();
  }
}
