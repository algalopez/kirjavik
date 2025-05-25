package com.algalopez.kirjavik.backoffice_app.book.application.delete_book;

import java.util.UUID;
import com.algalopez.kirjavik.backoffice_app.book.domain.event.BookDeleted;
import com.algalopez.kirjavik.backoffice_app.book.domain.port.BookRepositoryPort;
import com.algalopez.kirjavik.shared.application.EventBusPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeleteBookActorTest {
  private EventBusPort eventBusPort;
  private BookRepositoryPort bookRepositoryPort;
  private DeleteBookActor deleteBookActor;

  @BeforeEach
  void setUp() {
    DomainMetadataService domainMetadataService = Mockito.mock(DomainMetadataService.class);
    eventBusPort = Mockito.mock(EventBusPort.class);
    DeleteBookEventPublisher deleteBookEventPublisher =
        new DeleteBookEventPublisher(domainMetadataService, eventBusPort);
    bookRepositoryPort = Mockito.mock(BookRepositoryPort.class);
    deleteBookActor = new DeleteBookActor(bookRepositoryPort, deleteBookEventPublisher);

    Mockito.when(domainMetadataService.generateEventId()).thenReturn("event-id");
    Mockito.when(domainMetadataService.generateEventDateTime()).thenReturn("event-date-time");
  }

  @Test
  void command() {
    UUID id = UUID.randomUUID();
    DeleteBookCommand deleteBookCommand = DeleteBookCommand.builder().id(id.toString()).build();

    deleteBookActor.command(deleteBookCommand);

    Mockito.verify(bookRepositoryPort).deleteBook(id);
    Mockito.verify(eventBusPort).publish(Mockito.any(BookDeleted.class));
  }
}
