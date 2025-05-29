package com.algalopez.kirjavik.backoffice_app.user.application.update_user;

import com.algalopez.kirjavik.backoffice_app.user.domain.exception.UserNotFoundException;
import com.algalopez.kirjavik.backoffice_app.user.domain.model.User;
import com.algalopez.kirjavik.backoffice_app.user.domain.port.UserRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class UpdateUserActor {

  private final UpdateUserMapper updateUserMapper;
  private final UserRepositoryPort userRepositoryPort;
  private final UpdateUserEventPublisher updateUserEventPublisher;

  public void command(UpdateUserCommand command) {
    UUID id = UUID.fromString(command.id());
    ensureUserExists(id);
    User updatedUser = updateUserMapper.mapToDomain(command);
    userRepositoryPort.updateUser(updatedUser);
    updateUserEventPublisher.publishUserUpdatedEvent(updatedUser);
  }

  private void ensureUserExists(UUID id) {
    User user = userRepositoryPort.findUserById(id);
    if (user == null) {
      throw new UserNotFoundException("User " + id + " does not exist");
    }
  }
}
