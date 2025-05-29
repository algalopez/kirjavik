package com.algalopez.kirjavik.backoffice_app.user.application.create_user;

import com.algalopez.kirjavik.backoffice_app.user.domain.event.UserCreated;
import com.algalopez.kirjavik.backoffice_app.user.domain.model.User;
import com.algalopez.kirjavik.backoffice_app.user.domain.port.UserRepositoryPort;
import com.algalopez.kirjavik.shared.application.EventBusPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class CreateUserActorTest {

  private EventBusPort eventBusPort;
  private UserRepositoryPort userRepositoryPort;
  private CreateUserActor createUserActor;

  @BeforeEach
  void setUp() {
    DomainMetadataService domainMetadataService = Mockito.mock(DomainMetadataService.class);
    eventBusPort = Mockito.mock(EventBusPort.class);
    CreateUserEventPublisher createUserEventPublisher =
        new CreateUserEventPublisher(domainMetadataService, eventBusPort);
    userRepositoryPort = Mockito.mock(UserRepositoryPort.class);
    CreateUserMapper createUserMapper = Mappers.getMapper(CreateUserMapper.class);
    createUserActor =
        new CreateUserActor(createUserMapper, userRepositoryPort, createUserEventPublisher);

    Mockito.when(domainMetadataService.generateEventId()).thenReturn("event-id");
    Mockito.when(domainMetadataService.generateEventDateTime()).thenReturn("event-date-time");
  }

  @Test
  void command() {
    CreateUserCommand createUserCommand = buildCreateUserCommand();

    createUserActor.command(createUserCommand);

    Mockito.verify(userRepositoryPort)
        .createUser(
            User.builder()
                .id(UUID.fromString(createUserCommand.id()))
                .name(createUserCommand.name())
                .email(createUserCommand.email())
                .build());
    Mockito.verify(eventBusPort).publish(Mockito.any(UserCreated.class));
  }

  private static CreateUserCommand buildCreateUserCommand() {
    return CreateUserCommand.builder()
        .id(UUID.randomUUID().toString())
        .name("name")
        .email("email")
        .build();
  }
}
