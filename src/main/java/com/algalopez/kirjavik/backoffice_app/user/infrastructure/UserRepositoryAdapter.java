package com.algalopez.kirjavik.backoffice_app.user.infrastructure;

import com.algalopez.kirjavik.backoffice_app.user.domain.model.User;
import com.algalopez.kirjavik.backoffice_app.user.domain.port.UserRepositoryPort;
import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import java.util.UUID;

import jakarta.transaction.Transactional;
import org.hibernate.query.NativeQuery;

@ApplicationScoped
public class UserRepositoryAdapter implements UserRepositoryPort {

  private static final String ID = "id";
  private static final String NAME = "name";
  private static final String EMAIL = "email";

  private final EntityManager entityManager;

  public UserRepositoryAdapter(@PersistenceUnit("backoffice") EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public User findUserById(UUID id) {
    String sqlString = "SELECT id, name, email FROM user WHERE id = :id";

    @SuppressWarnings("unchecked")
    NativeQuery<Tuple> query =
        entityManager
            .createNativeQuery(sqlString, Tuple.class)
            .setParameter(ID, id.toString())
            .unwrap(NativeQuery.class)
            .addScalar(ID, String.class)
            .addScalar(NAME, String.class)
            .addScalar(EMAIL, String.class);

    Tuple tuple = query.uniqueResult();
    return tuple == null ? null : mapToUser(tuple);
  }

  @Transactional
  @Override
  public void createUser(User user) {
    String sqlString = "INSERT INTO user (`id`, `name`, `email`) VALUES (:id, :name, :email)";
    entityManager
        .createNativeQuery(sqlString)
        .setParameter(ID, user.getId().toString())
        .setParameter(NAME, user.getName())
        .setParameter(EMAIL, user.getEmail())
        .executeUpdate();
  }

  @Transactional
  @Override
  public void updateUser(User user) {
    String sqlString = "UPDATE user SET `name` = :name, `email` = :email WHERE `id` = :id";
    entityManager
        .createNativeQuery(sqlString)
        .setParameter(ID, user.getId().toString())
        .setParameter(NAME, user.getName())
        .setParameter(EMAIL, user.getEmail())
        .executeUpdate();
  }

  @Transactional
  @Override
  public void deleteUser(UUID id) {
    String sqlString = "DELETE FROM user WHERE id = :id";
    entityManager.createNativeQuery(sqlString).setParameter(ID, id.toString()).executeUpdate();
  }

  private User mapToUser(Tuple tuple) {
    UUID id = UUID.fromString(tuple.get(ID, String.class));
    String name = tuple.get(NAME, String.class);
    String email = tuple.get(EMAIL, String.class);
    return User.builder().id(id).name(name).email(email).build();
  }
}
