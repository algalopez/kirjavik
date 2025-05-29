package com.algalopez.kirjavik.backoffice_app.user.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.backoffice_app.user.domain.model.User;
import com.algalopez.kirjavik.backoffice_app.user.domain.model.UserMother;
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
class UserRepositoryAdapterIntegrationTest {

  @Inject
  @PersistenceUnit("backoffice")
  EntityManager entityManager;

  @Inject UserRepositoryAdapter userRepositoryAdapter;

  @Transactional
  @AfterEach
  void cleanUp() {
    entityManager.createNativeQuery("TRUNCATE TABLE user").executeUpdate();
  }

  @Test
  void findUserById() {
    User expectedUser = new UserMother().build();
    givenAnExistingUser(expectedUser);

    User actualUser = userRepositoryAdapter.findUserById(expectedUser.getId());

    assertThat(actualUser).isEqualTo(expectedUser);
  }

  @Test
  void findUserById_whenUserDoesNotExist() {
    UUID id = UUID.randomUUID();

    User actualUser = userRepositoryAdapter.findUserById(id);

    assertThat(actualUser).isNull();
  }

  @Test
  void createUser() {
    User expectedUser = new UserMother().build();

    userRepositoryAdapter.createUser(expectedUser);

    List<Tuple> actualUsers = findAllUsers();

    assertThat(actualUsers).hasSize(1);
    UserTupleAssert.assertThat(actualUsers.getFirst()).isEqualToUser(expectedUser);
  }

  @Test
  void updateUser() {
    User originalUser = new UserMother().build();
    User expectedUser = new UserMother().id(originalUser.getId()).build();
    givenAnExistingUser(originalUser);

    userRepositoryAdapter.updateUser(expectedUser);

    List<Tuple> actualUsers = findAllUsers();
    assertThat(actualUsers).hasSize(1);
    UserTupleAssert.assertThat(actualUsers.getFirst()).isEqualToUser(expectedUser);
  }

  @Test
  void deleteUser() {
    User originalUser = new UserMother().build();
    givenAnExistingUser(originalUser);

    userRepositoryAdapter.deleteUser(originalUser.getId());

    List<Tuple> actualUsers = findAllUsers();
    assertThat(actualUsers).isEmpty();
  }

  @SneakyThrows
  @Transactional
  void givenAnExistingUser(User user) {
    entityManager
        .createNativeQuery("INSERT INTO user (`id`, `name`, `email`) VALUES (:id, :name, :email)")
        .setParameter("id", user.getId().toString())
        .setParameter("name", user.getName())
        .setParameter("email", user.getEmail())
        .executeUpdate();
  }

  private List<Tuple> findAllUsers() {
    String sqlString = "SELECT id, name, email FROM user";
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

  public static class UserTupleAssert extends AbstractAssert<UserTupleAssert, Tuple> {

    public UserTupleAssert(Tuple actual) {
      super(actual, UserTupleAssert.class);
    }

    public static UserTupleAssert assertThat(Tuple actual) {
      return new UserTupleAssert(actual);
    }

    @SneakyThrows
    public void isEqualToUser(User expected) {
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
