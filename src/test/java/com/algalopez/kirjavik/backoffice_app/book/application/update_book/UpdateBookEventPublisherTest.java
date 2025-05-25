package com.algalopez.kirjavik.backoffice_app.book.application.update_book;

import java.time.LocalDateTime;
import java.util.UUID;
import com.algalopez.kirjavik.backoffice_app.book.domain.event.BookUpdated;
import com.algalopez.kirjavik.backoffice_app.book.domain.model.Book;
import com.algalopez.kirjavik.backoffice_app.book.domain.model.BookMother;
import com.algalopez.kirjavik.shared.application.EventBusPort;
import com.algalopez.kirjavik.shared.domain.port.DateTimeProviderPort;
import com.algalopez.kirjavik.shared.domain.port.UuidProviderPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UpdateBookEventPublisherTest {

  private UuidProviderPort uuidProvider;
  private DateTimeProviderPort dateTimeProvider;
  private EventBusPort eventBusPort;
  private UpdateBookEventPublisher updateBookEventPublisher;

  @BeforeEach
  void setUp() {
    dateTimeProvider = Mockito.mock(DateTimeProviderPort.class);
    uuidProvider = Mockito.mock(UuidProviderPort.class);
    DomainMetadataService domainMetadataService =
        new DomainMetadataService(uuidProvider, dateTimeProvider);
    eventBusPort = Mockito.mock(EventBusPort.class);
    updateBookEventPublisher = new UpdateBookEventPublisher(domainMetadataService, eventBusPort);
  }

  @Test
  void publishBookUpdatedEvent() {
    Book book = new BookMother().build();
    Mockito.when(dateTimeProvider.getDateTime()).thenReturn(LocalDateTime.of(2023, 1, 2, 3, 4, 5));
    Mockito.when(uuidProvider.getUuid())
        .thenReturn(UUID.fromString("01970869-0fa6-7395-bb9d-d2a6d35e9c14"));

    updateBookEventPublisher.publishBookUpdatedEvent(book);

    Mockito.verify(eventBusPort)
        .publish(
            BookUpdated.builder()
                .eventId("01970869-0fa6-7395-bb9d-d2a6d35e9c14")
                .eventType(BookUpdated.EVENT_TYPE)
                .aggregateId(book.getId().toString())
                .aggregateType(BookUpdated.AGGREGATE_TYPE)
                .dateTime("2023-01-02T03:04:05")
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .authors(book.getAuthors())
                .pageCount(book.getPageCount())
                .year(book.getYear())
                .build());
  }
}
