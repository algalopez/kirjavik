package com.algalopez.kirjavik.backoffice_app.user.application.create_user;

import com.algalopez.kirjavik.backoffice_app.user.domain.model.User;
import com.algalopez.kirjavik.backoffice_app.user.domain.port.UserRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class CreateUserActor {

  private final CreateUserMapper createUserMapper;
  private final UserRepositoryPort userRepositoryPort;
  private final CreateUserEventPublisher createUserEventPublisher;

  public void command(CreateUserCommand command) {
    User user = createUserMapper.mapToDomain(command);
    userRepositoryPort.createUser(user);

    createUserEventPublisher.publishUserCreatedEvent(user);
  }
}
