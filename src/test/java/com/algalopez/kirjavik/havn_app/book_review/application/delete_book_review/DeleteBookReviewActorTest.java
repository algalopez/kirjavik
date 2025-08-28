package com.algalopez.kirjavik.havn_app.book_review.application.delete_book_review;

import com.algalopez.kirjavik.havn_app.book_review.domain.event.BookReviewDeleted;
import com.algalopez.kirjavik.havn_app.book_review.domain.port.BookReviewRepositoryPort;
import com.algalopez.kirjavik.shared.application.EventBusPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeleteBookReviewActorTest {

  private EventBusPort eventBusPort;
  private BookReviewRepositoryPort bookReviewRepositoryPort;
  private DeleteBookReviewActor deleteBookReviewActor;

  @BeforeEach
  void setUp() {
    DomainMetadataService domainMetadataService = Mockito.mock(DomainMetadataService.class);
    eventBusPort = Mockito.mock(EventBusPort.class);
    DeleteBookReviewEventPublisher deleteBookReviewEventPublisher =
        new DeleteBookReviewEventPublisher(domainMetadataService, eventBusPort);
    bookReviewRepositoryPort = Mockito.mock(BookReviewRepositoryPort.class);
    deleteBookReviewActor =
        new DeleteBookReviewActor(bookReviewRepositoryPort, deleteBookReviewEventPublisher);

    Mockito.when(domainMetadataService.generateEventId()).thenReturn("event-id");
    Mockito.when(domainMetadataService.generateEventDateTime()).thenReturn("event-date-time");
  }

  @Test
  void command() {
    Long id = 123L;
    var deleteBookReviewCommand = DeleteBookReviewCommand.builder().id(id).build();

    deleteBookReviewActor.command(deleteBookReviewCommand);

    Mockito.verify(bookReviewRepositoryPort).deleteBookReview(id);
    Mockito.verify(eventBusPort).publish(Mockito.any(BookReviewDeleted.class));
  }
}
