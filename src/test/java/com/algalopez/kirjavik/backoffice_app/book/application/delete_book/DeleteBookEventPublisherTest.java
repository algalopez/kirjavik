package com.algalopez.kirjavik.backoffice_app.book.application.delete_book;

import java.time.LocalDateTime;
import java.util.UUID;
import com.algalopez.kirjavik.backoffice_app.book.domain.event.BookDeleted;
import com.algalopez.kirjavik.backoffice_app.shared.domain.port.EventBusPort;
import com.algalopez.kirjavik.shared.domain.port.DateTimeProviderPort;
import com.algalopez.kirjavik.shared.domain.port.UuidProviderPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeleteBookEventPublisherTest {

  private UuidProviderPort uuidProvider;
  private DateTimeProviderPort dateTimeProvider;
  private EventBusPort eventBusPort;
  private DeleteBookEventPublisher deleteBookEventPublisher;

  @BeforeEach
  void setUp() {
    dateTimeProvider = Mockito.mock(DateTimeProviderPort.class);
    uuidProvider = Mockito.mock(UuidProviderPort.class);
    DomainMetadataService domainMetadataService =
        new DomainMetadataService(uuidProvider, dateTimeProvider);
    eventBusPort = Mockito.mock(EventBusPort.class);
    deleteBookEventPublisher = new DeleteBookEventPublisher(domainMetadataService, eventBusPort);
  }

  @Test
  void publishBookDeletedEvent() {
    UUID id = UUID.fromString("0197086d-f7a3-7b30-91e4-5370d69d4c7f");
    Mockito.when(dateTimeProvider.getDateTime()).thenReturn(LocalDateTime.of(2023, 1, 2, 3, 4, 5));
    Mockito.when(uuidProvider.getUuid())
        .thenReturn(UUID.fromString("01970869-0fa6-7395-bb9d-d2a6d35e9c14"));

    deleteBookEventPublisher.publishBookDeletedEvent(id);

    Mockito.verify(eventBusPort)
        .publish(
            BookDeleted.builder()
                .eventId("01970869-0fa6-7395-bb9d-d2a6d35e9c14")
                .eventType(BookDeleted.EVENT_TYPE)
                .aggregateId(id.toString())
                .aggregateType(BookDeleted.AGGREGATE_TYPE)
                .dateTime("2023-01-02T03:04:05")
                .id(id)
                .build());
  }
}
