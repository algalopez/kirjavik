package com.algalopez.kirjavik.backoffice_app.user.domain.port;

import com.algalopez.kirjavik.backoffice_app.user.domain.model.User;
import java.util.UUID;

public interface UserRepositoryPort {
  User findUserById(UUID id);

  void createUser(User user);

  void updateUser(User user);

  void deleteUser(UUID id);
}
