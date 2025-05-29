package com.algalopez.kirjavik.backoffice_app.user.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.backoffice_app.user.domain.view.UserView;
import com.algalopez.kirjavik.backoffice_app.user.domain.view.UserViewMother;
import io.quarkus.hibernate.orm.PersistenceUnit;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
class UserViewRepositoryAdapterIntegrationTest {

  @Inject
  @PersistenceUnit("backoffice")
  EntityManager entityManager;

  @Inject UserViewRepositoryAdapter userViewRepositoryAdapter;

  @Transactional
  @AfterEach
  void cleanUp() {
    entityManager.createNativeQuery("TRUNCATE TABLE user").executeUpdate();
  }

  @Test
  void findById() {
    UserView user = new UserViewMother().build();
    givenAnExistingUser(user);

    UserView actualUser = userViewRepositoryAdapter.findById(user.id());

    assertThat(actualUser).isEqualTo(user);
  }

  @Test
  void findById_whenUserNotFound() {
    UUID id = UUID.randomUUID();

    UserView actualUser = userViewRepositoryAdapter.findById(id);

    assertThat(actualUser).isNull();
  }

  @Transactional
  void givenAnExistingUser(UserView user) {
    entityManager
        .createNativeQuery("INSERT INTO user (`id`, `name`, `email`) VALUES (:id, :name, :email)")
        .setParameter("id", user.id().toString())
        .setParameter("name", user.name())
        .setParameter("email", user.email())
        .executeUpdate();
  }
}
