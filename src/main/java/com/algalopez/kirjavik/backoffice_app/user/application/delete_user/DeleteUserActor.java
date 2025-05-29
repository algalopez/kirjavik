package com.algalopez.kirjavik.backoffice_app.user.application.delete_user;

import com.algalopez.kirjavik.backoffice_app.user.domain.port.UserRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class DeleteUserActor {

  private final UserRepositoryPort userRepositoryPort;
  private final DeleteUserEventPublisher deleteUserEventPublisher;

  public void command(DeleteUserCommand command) {
    UUID id = UUID.fromString(command.id());
    userRepositoryPort.deleteUser(id);
    deleteUserEventPublisher.publishUserDeletedEvent(id);
  }
}
