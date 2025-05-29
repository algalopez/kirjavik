package com.algalopez.kirjavik.backoffice_app.user.infrastructure;

import com.algalopez.kirjavik.backoffice_app.user.domain.model.User;
import com.algalopez.kirjavik.backoffice_app.user.domain.port.UserRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class UserRepositoryAdapter implements UserRepositoryPort {

  @Override
  public User findUserById(UUID id) {
    return null;
  }

  @Override
  public void createUser(User user) {}

  @Override
  public void updateUser(User user) {}

  @Override
  public void deleteUser(UUID id) {}
}
