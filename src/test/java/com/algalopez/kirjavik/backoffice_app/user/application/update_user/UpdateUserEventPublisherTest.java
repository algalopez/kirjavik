package com.algalopez.kirjavik.backoffice_app.user.application.update_user;

import com.algalopez.kirjavik.backoffice_app.user.domain.event.UserUpdated;
import com.algalopez.kirjavik.backoffice_app.user.domain.model.User;
import com.algalopez.kirjavik.backoffice_app.user.domain.model.UserMother;
import com.algalopez.kirjavik.shared.application.EventBusPort;
import com.algalopez.kirjavik.shared.domain.port.DateTimeProviderPort;
import com.algalopez.kirjavik.shared.domain.port.UuidProviderPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UpdateUserEventPublisherTest {

  private UuidProviderPort uuidProvider;
  private DateTimeProviderPort dateTimeProvider;
  private EventBusPort eventBusPort;
  private UpdateUserEventPublisher updateUserEventPublisher;

  @BeforeEach
  void setUp() {
    dateTimeProvider = Mockito.mock(DateTimeProviderPort.class);
    uuidProvider = Mockito.mock(UuidProviderPort.class);
    DomainMetadataService domainMetadataService =
        new DomainMetadataService(uuidProvider, dateTimeProvider);
    eventBusPort = Mockito.mock(EventBusPort.class);
    updateUserEventPublisher = new UpdateUserEventPublisher(domainMetadataService, eventBusPort);
  }

  @Test
  void publishUserUpdatedEvent() {
    User user = new UserMother().build();
    Mockito.when(dateTimeProvider.getDateTime()).thenReturn(LocalDateTime.of(2023, 1, 2, 3, 4, 5));
    Mockito.when(uuidProvider.getUuid())
        .thenReturn(UUID.fromString("01970869-0fa6-7395-bb9d-d2a6d35e9c14"));

    updateUserEventPublisher.publishUserUpdatedEvent(user);

    Mockito.verify(eventBusPort)
        .publish(
            UserUpdated.builder()
                .eventId("01970869-0fa6-7395-bb9d-d2a6d35e9c14")
                .eventType(UserUpdated.EVENT_TYPE)
                .aggregateId(user.getId().toString())
                .aggregateType(UserUpdated.AGGREGATE_TYPE)
                .dateTime("2023-01-02T03:04:05")
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build());
  }
}
