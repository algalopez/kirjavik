package com.algalopez.kirjavik.havn_app.user_projection.infrastructure;

import com.algalopez.kirjavik.havn_app.user_projection.domain.model.UserProjection;
import com.algalopez.kirjavik.havn_app.user_projection.domain.port.UserProjectionRepositoryPort;
import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.hibernate.query.NativeQuery;

@ApplicationScoped
public class UserProjectionRepositoryAdapter implements UserProjectionRepositoryPort {

  private static final String ID = "id";
  private static final String NAME = "name";
  private static final String EMAIL = "email";

  private final EntityManager entityManager;

  public UserProjectionRepositoryAdapter(@PersistenceUnit("havn") EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public UserProjection findUserProjectionById(UUID id) {
    String sqlString = "SELECT id, name, email FROM user_projection WHERE id = :id";

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
    return tuple == null ? null : mapToUserProjection(tuple);
  }

  @Transactional
  @Override
  public void createUserProjection(UserProjection userProjection) {
    String sqlString =
        "INSERT INTO user_projection (`id`, `name`, `email`) VALUES (:id, :name, :email)";
    entityManager
        .createNativeQuery(sqlString)
        .setParameter(ID, userProjection.getId().toString())
        .setParameter(NAME, userProjection.getName())
        .setParameter(EMAIL, userProjection.getEmail())
        .executeUpdate();
  }

  @Transactional
  @Override
  public void updateUserProjection(UserProjection userProjection) {
    String sqlString =
        "UPDATE user_projection SET `name` = :name, `email` = :email WHERE `id` = :id";
    entityManager
        .createNativeQuery(sqlString)
        .setParameter(ID, userProjection.getId().toString())
        .setParameter(NAME, userProjection.getName())
        .setParameter(EMAIL, userProjection.getEmail())
        .executeUpdate();
  }

  @Transactional
  @Override
  public void deleteUserProjection(UUID id) {
    String sqlString = "DELETE FROM user_projection WHERE id = :id";
    entityManager.createNativeQuery(sqlString).setParameter(ID, id.toString()).executeUpdate();
  }

  private UserProjection mapToUserProjection(Tuple tuple) {
    UUID id = UUID.fromString(tuple.get(ID, String.class));
    String name = tuple.get(NAME, String.class);
    String email = tuple.get(EMAIL, String.class);
    return UserProjection.builder().id(id).name(name).email(email).build();
  }
}
