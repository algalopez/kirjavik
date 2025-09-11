package com.algalopez.kirjavik.havn_app.user_projection.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.havn_app.user_projection.domain.model.UserProjection;
import com.algalopez.kirjavik.havn_app.user_projection.domain.model.UserProjectionMother;
import io.quarkus.hibernate.orm.PersistenceUnit;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.SneakyThrows;
import org.assertj.core.api.AbstractAssert;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
class UserProjectionRepositoryAdapterIntegrationTest {

  @Inject
  @PersistenceUnit("havn")
  EntityManager entityManager;

  @Inject UserProjectionRepositoryAdapter userProjectionRepositoryAdapter;

  @Transactional
  @AfterEach
  void cleanUp() {
    entityManager.createNativeQuery("TRUNCATE TABLE user_projection").executeUpdate();
  }

  @Test
  void findUserProjectionById() {
    UserProjection expectedUserProjection = new UserProjectionMother().build();
    givenAnExistingUserProjection(expectedUserProjection);

    UserProjection actualUserProjection =
        userProjectionRepositoryAdapter.findUserProjectionById(expectedUserProjection.getId());

    assertThat(actualUserProjection).isEqualTo(expectedUserProjection);
  }

  @Test
  void findUserProjectionById_whenUserDoesNotExist() {
    UUID id = UUID.randomUUID();

    UserProjection actualUserProjection =
        userProjectionRepositoryAdapter.findUserProjectionById(id);

    assertThat(actualUserProjection).isNull();
  }

  @Test
  void createUserProjection() {
    UserProjection expectedUserProjection = new UserProjectionMother().build();

    userProjectionRepositoryAdapter.createUserProjection(expectedUserProjection);

    List<Tuple> actualUserProjections = findAllUserProjections();

    assertThat(actualUserProjections).hasSize(1);
    UserProjectionTupleAssert.assertThat(actualUserProjections.getFirst())
        .isEqualToUserProjection(expectedUserProjection);
  }

  @Test
  void updateUserProjection() {
    UserProjection originalUserProjection = new UserProjectionMother().build();
    UserProjection expectedUserProjection =
        new UserProjectionMother().id(originalUserProjection.getId()).build();
    givenAnExistingUserProjection(originalUserProjection);

    userProjectionRepositoryAdapter.updateUserProjection(expectedUserProjection);

    List<Tuple> actualUserProjections = findAllUserProjections();
    assertThat(actualUserProjections).hasSize(1);
    UserProjectionTupleAssert.assertThat(actualUserProjections.getFirst())
        .isEqualToUserProjection(expectedUserProjection);
  }

  @Test
  void deleteUserProjection() {
    UserProjection originalUserProjection = new UserProjectionMother().build();
    givenAnExistingUserProjection(originalUserProjection);

    userProjectionRepositoryAdapter.deleteUserProjection(originalUserProjection.getId());

    List<Tuple> actualUserProjections = findAllUserProjections();
    assertThat(actualUserProjections).isEmpty();
  }

  @SneakyThrows
  @Transactional
  void givenAnExistingUserProjection(UserProjection userProjection) {
    entityManager
        .createNativeQuery(
            "INSERT INTO user_projection (`id`, `name`, `email`) VALUES (:id, :name, :email)")
        .setParameter("id", userProjection.getId().toString())
        .setParameter("name", userProjection.getName())
        .setParameter("email", userProjection.getEmail())
        .executeUpdate();
  }

  private List<Tuple> findAllUserProjections() {
    String sqlString = "SELECT id, name, email FROM user_projection";
    @SuppressWarnings("unchecked")
    NativeQuery<Tuple> query =
        entityManager
            .createNativeQuery(sqlString, Tuple.class)
            .unwrap(NativeQuery.class)
            .addScalar("id", String.class)
            .addScalar("name", String.class)
            .addScalar("email", String.class);
    return query.getResultList();
  }

  public static class UserProjectionTupleAssert
      extends AbstractAssert<UserProjectionTupleAssert, Tuple> {

    public UserProjectionTupleAssert(Tuple actual) {
      super(actual, UserProjectionTupleAssert.class);
    }

    public static UserProjectionTupleAssert assertThat(Tuple actual) {
      return new UserProjectionTupleAssert(actual);
    }

    @SneakyThrows
    public void isEqualToUserProjection(UserProjection expected) {
      isNotNull();

      UUID id = UUID.fromString(actual.get("id", String.class));
      String name = actual.get("name", String.class);
      String email = actual.get("email", String.class);

      if (!Objects.equals(expected.getId(), id)) {
        failWithMessage("Expected id to be <%s> but was <%s>", expected.getId(), id);
      }
      if (!Objects.equals(expected.getName(), name)) {
        failWithMessage("Expected name to be <%s> but was <%s>", expected.getName(), name);
      }
      if (!Objects.equals(expected.getEmail(), email)) {
        failWithMessage("Expected email to be <%s> but was <%s>", expected.getEmail(), email);
      }
    }
  }
}
