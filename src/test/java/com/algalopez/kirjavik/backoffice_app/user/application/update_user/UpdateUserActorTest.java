package com.algalopez.kirjavik.backoffice_app.user.application.update_user;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.algalopez.kirjavik.backoffice_app.user.domain.event.UserUpdated;
import com.algalopez.kirjavik.backoffice_app.user.domain.exception.UserNotFoundException;
import com.algalopez.kirjavik.backoffice_app.user.domain.model.User;
import com.algalopez.kirjavik.backoffice_app.user.domain.model.UserMother;
import com.algalopez.kirjavik.backoffice_app.user.domain.port.UserRepositoryPort;
import com.algalopez.kirjavik.backoffice_app.shared.domain.port.EventBusPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class UpdateUserActorTest {

  private UserRepositoryPort userRepositoryPort;
  private EventBusPort eventBusPort;
  private UpdateUserActor updateUserActor;

  @BeforeEach
  void setUp() {
    DomainMetadataService domainMetadataService = Mockito.mock(DomainMetadataService.class);
    eventBusPort = Mockito.mock(EventBusPort.class);
    UpdateUserEventPublisher updateUserEventPublisher =
        new UpdateUserEventPublisher(domainMetadataService, eventBusPort);
    UpdateUserMapper updateUserMapper = Mappers.getMapper(UpdateUserMapper.class);
    userRepositoryPort = Mockito.mock(UserRepositoryPort.class);
    updateUserActor =
        new UpdateUserActor(updateUserMapper, userRepositoryPort, updateUserEventPublisher);

    Mockito.when(domainMetadataService.generateEventId()).thenReturn("event-id");
    Mockito.when(domainMetadataService.generateEventDateTime()).thenReturn("event-date-time");
  }

  @Test
  void command() {
    UpdateUserCommand updateUserCommand = buildUpdateUserCommand();
    Mockito.when(userRepositoryPort.findUserById(Mockito.any(UUID.class)))
        .thenReturn(new UserMother().build());

    updateUserActor.command(updateUserCommand);

    Mockito.verify(userRepositoryPort)
        .updateUser(
            User.builder()
                .id(UUID.fromString(updateUserCommand.id()))
                .name(updateUserCommand.name())
                .email(updateUserCommand.email())
                .build());
    Mockito.verify(eventBusPort).publish(Mockito.any(UserUpdated.class));
  }

  @Test
  void command_whenUserDoesNotExist() {
    UpdateUserCommand updateUserCommand = buildUpdateUserCommand();

    assertThatExceptionOfType(UserNotFoundException.class)
        .isThrownBy(() -> updateUserActor.command(updateUserCommand));

    Mockito.verify(userRepositoryPort, Mockito.never()).updateUser(Mockito.any(User.class));
    Mockito.verifyNoInteractions(eventBusPort);
  }

  private static UpdateUserCommand buildUpdateUserCommand() {
    return UpdateUserCommand.builder()
        .id(UUID.randomUUID().toString())
        .name("name")
        .email("email")
        .build();
  }
}
