package com.algalopez.kirjavik.backoffice_app.book.application.create_book;

import java.util.List;
import java.util.UUID;
import com.algalopez.kirjavik.backoffice_app.book.domain.event.BookCreated;
import com.algalopez.kirjavik.backoffice_app.book.domain.model.Book;
import com.algalopez.kirjavik.backoffice_app.book.domain.port.BookRepositoryPort;
import com.algalopez.kirjavik.shared.application.EventBusPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class CreateBookActorTest {

  private EventBusPort eventBusPort;
  private BookRepositoryPort bookRepositoryPort;
  private CreateBookActor createBookActor;

  @BeforeEach
  void setUp() {
    DomainMetadataService domainMetadataService = Mockito.mock(DomainMetadataService.class);
    eventBusPort = Mockito.mock(EventBusPort.class);
    CreateBookEventPublisher createBookEventPublisher =
        new CreateBookEventPublisher(domainMetadataService, eventBusPort);
    bookRepositoryPort = Mockito.mock(BookRepositoryPort.class);
    CreateBookMapper createBookMapper = Mappers.getMapper(CreateBookMapper.class);
    createBookActor =
        new CreateBookActor(createBookMapper, bookRepositoryPort, createBookEventPublisher);

    Mockito.when(domainMetadataService.generateEventId()).thenReturn("event-id");
    Mockito.when(domainMetadataService.generateEventDateTime()).thenReturn("event-date-time");
  }

  @Test
  void command() {
    CreateBookCommand createBookCommand = buildCreateBookCommand();

    createBookActor.command(createBookCommand);

    Mockito.verify(bookRepositoryPort)
        .createBook(
            Book.builder()
                .id(UUID.fromString(createBookCommand.id()))
                .isbn(createBookCommand.isbn())
                .title(createBookCommand.title())
                .authors(createBookCommand.authors())
                .pageCount(createBookCommand.pageCount())
                .year(createBookCommand.year())
                .build());
    Mockito.verify(eventBusPort).publish(Mockito.any(BookCreated.class));
  }

  private static CreateBookCommand buildCreateBookCommand() {
    return CreateBookCommand.builder()
        .id(UUID.randomUUID().toString())
        .isbn("isbn")
        .title("title")
        .authors(List.of("author"))
        .pageCount(1)
        .year(2000)
        .build();
  }
}
