package com.algalopez.kirjavik.backoffice_app.user.application.delete_user;

import com.algalopez.kirjavik.backoffice_app.user.domain.event.UserDeleted;
import com.algalopez.kirjavik.backoffice_app.user.domain.port.UserRepositoryPort;
import com.algalopez.kirjavik.backoffice_app.shared.domain.port.EventBusPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeleteUserActorTest {
  private EventBusPort eventBusPort;
  private UserRepositoryPort userRepositoryPort;
  private DeleteUserActor deleteUserActor;

  @BeforeEach
  void setUp() {
    DomainMetadataService domainMetadataService = Mockito.mock(DomainMetadataService.class);
    eventBusPort = Mockito.mock(EventBusPort.class);
    DeleteUserEventPublisher deleteUserEventPublisher =
        new DeleteUserEventPublisher(domainMetadataService, eventBusPort);
    userRepositoryPort = Mockito.mock(UserRepositoryPort.class);
    deleteUserActor = new DeleteUserActor(userRepositoryPort, deleteUserEventPublisher);

    Mockito.when(domainMetadataService.generateEventId()).thenReturn("event-id");
    Mockito.when(domainMetadataService.generateEventDateTime()).thenReturn("event-date-time");
  }

  @Test
  void command() {
    UUID id = UUID.randomUUID();
    DeleteUserCommand deleteUserCommand = DeleteUserCommand.builder().id(id.toString()).build();

    deleteUserActor.command(deleteUserCommand);

    Mockito.verify(userRepositoryPort).deleteUser(id);
    Mockito.verify(eventBusPort).publish(Mockito.any(UserDeleted.class));
  }
}
