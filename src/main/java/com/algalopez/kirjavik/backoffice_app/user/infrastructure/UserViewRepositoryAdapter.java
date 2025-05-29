package com.algalopez.kirjavik.backoffice_app.user.infrastructure;

import com.algalopez.kirjavik.backoffice_app.user.domain.port.UserViewRepositoryPort;
import com.algalopez.kirjavik.backoffice_app.user.domain.view.UserView;
import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import java.util.UUID;
import org.hibernate.query.NativeQuery;

@ApplicationScoped
public class UserViewRepositoryAdapter implements UserViewRepositoryPort {

  private static final String ID = "id";
  private static final String NAME = "name";
  private static final String EMAIL = "email";

  private final EntityManager entityManager;

  public UserViewRepositoryAdapter(@PersistenceUnit("backoffice") EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public UserView findById(UUID id) {
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
    return (tuple == null) ? null : mapToView(tuple);
  }

  private static UserView mapToView(Tuple tuple) {
    UUID id = UUID.fromString(tuple.get(ID, String.class));
    String name = tuple.get(NAME, String.class);
    String email = tuple.get(EMAIL, String.class);
    return UserView.builder().id(id).name(name).email(email).build();
  }
}
