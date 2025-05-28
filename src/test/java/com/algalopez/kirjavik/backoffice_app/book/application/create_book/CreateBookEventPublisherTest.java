package com.algalopez.kirjavik.backoffice_app.book.application.create_book;

import com.algalopez.kirjavik.backoffice_app.book.domain.event.BookCreated;
import com.algalopez.kirjavik.backoffice_app.book.domain.model.Book;
import com.algalopez.kirjavik.backoffice_app.book.domain.model.BookMother;
import com.algalopez.kirjavik.shared.application.EventBusPort;
import com.algalopez.kirjavik.shared.domain.port.DateTimeProviderPort;
import com.algalopez.kirjavik.shared.domain.port.UuidProviderPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateBookEventPublisherTest {

  private UuidProviderPort uuidProvider;
  private DateTimeProviderPort dateTimeProvider;
  private EventBusPort eventBusPort;
  private CreateBookEventPublisher createBookEventPublisher;

  @BeforeEach
  void setUp() {
    dateTimeProvider = Mockito.mock(DateTimeProviderPort.class);
    uuidProvider = Mockito.mock(UuidProviderPort.class);
    DomainMetadataService domainMetadataService =
        new DomainMetadataService(uuidProvider, dateTimeProvider);
    eventBusPort = Mockito.mock(EventBusPort.class);
    createBookEventPublisher = new CreateBookEventPublisher(domainMetadataService, eventBusPort);
  }

  @Test
  void publishBookCreatedEvent() {
    Book book = new BookMother().build();
    Mockito.when(dateTimeProvider.getDateTime()).thenReturn(LocalDateTime.of(2023, 1, 2, 3, 4, 5));
    Mockito.when(uuidProvider.getUuid())
        .thenReturn(UUID.fromString("01970869-0fa6-7395-bb9d-d2a6d35e9c14"));

    createBookEventPublisher.publishBookCreatedEvent(book);

    Mockito.verify(eventBusPort)
        .publish(
            BookCreated.builder()
                .eventId("01970869-0fa6-7395-bb9d-d2a6d35e9c14")
                .eventType(BookCreated.EVENT_TYPE)
                .aggregateId(book.getId().toString())
                .aggregateType(BookCreated.AGGREGATE_TYPE)
                .dateTime("2023-01-02T03:04:05")
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .pageCount(book.getPageCount())
                .year(book.getYear())
                .build());
  }
}
