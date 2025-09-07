package com.algalopez.kirjavik.backoffice_app.book.application.update_book;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.algalopez.kirjavik.backoffice_app.book.domain.event.BookUpdated;
import com.algalopez.kirjavik.backoffice_app.book.domain.exception.BookNotFoundException;
import com.algalopez.kirjavik.backoffice_app.book.domain.model.Book;
import com.algalopez.kirjavik.backoffice_app.book.domain.model.BookMother;
import com.algalopez.kirjavik.backoffice_app.book.domain.port.BookRepositoryPort;
import com.algalopez.kirjavik.backoffice_app.shared.domain.port.EventBusPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class UpdateBookActorTest {

  private BookRepositoryPort bookRepositoryPort;
  private EventBusPort eventBusPort;
  private UpdateBookActor updateBookActor;

  @BeforeEach
  void setUp() {
    DomainMetadataService domainMetadataService = Mockito.mock(DomainMetadataService.class);
    eventBusPort = Mockito.mock(EventBusPort.class);
    UpdateBookEventPublisher updateBookEventPublisher =
        new UpdateBookEventPublisher(domainMetadataService, eventBusPort);
    UpdateBookMapper updateBookMapper = Mappers.getMapper(UpdateBookMapper.class);
    bookRepositoryPort = Mockito.mock(BookRepositoryPort.class);
    updateBookActor =
        new UpdateBookActor(updateBookMapper, bookRepositoryPort, updateBookEventPublisher);

    Mockito.when(domainMetadataService.generateEventId()).thenReturn("event-id");
    Mockito.when(domainMetadataService.generateEventDateTime()).thenReturn("event-date-time");
  }

  @Test
  void command() {
    UpdateBookCommand updateBookCommand = buildUpdateBookCommand();
    Mockito.when(bookRepositoryPort.findBookById(Mockito.any(UUID.class)))
        .thenReturn(new BookMother().build());

    updateBookActor.command(updateBookCommand);

    Mockito.verify(bookRepositoryPort)
        .updateBook(
            Book.builder()
                .id(UUID.fromString(updateBookCommand.id()))
                .isbn(updateBookCommand.isbn())
                .title(updateBookCommand.title())
                .author(updateBookCommand.author())
                .pageCount(updateBookCommand.pageCount())
                .year(updateBookCommand.year())
                .build());
    Mockito.verify(eventBusPort).publish(Mockito.any(BookUpdated.class));
  }

  @Test
  void command_whenBookDoesNotExist() {
    UpdateBookCommand updateBookCommand = buildUpdateBookCommand();

    assertThatExceptionOfType(BookNotFoundException.class)
        .isThrownBy(() -> updateBookActor.command(updateBookCommand));

    Mockito.verify(bookRepositoryPort, Mockito.never()).updateBook(Mockito.any(Book.class));
    Mockito.verifyNoInteractions(eventBusPort);
  }

  private static UpdateBookCommand buildUpdateBookCommand() {
    return UpdateBookCommand.builder()
        .id(UUID.randomUUID().toString())
        .isbn("123")
        .title("title")
        .author("author")
        .pageCount(1)
        .year(1)
        .build();
  }
}
