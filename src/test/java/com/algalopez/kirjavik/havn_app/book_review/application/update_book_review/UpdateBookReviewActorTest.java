package com.algalopez.kirjavik.havn_app.book_review.application.update_book_review;

import com.algalopez.kirjavik.havn_app.book_review.domain.event.BookReviewUpdated;
import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReview;
import com.algalopez.kirjavik.havn_app.book_review.domain.port.BookReviewRepositoryPort;
import com.algalopez.kirjavik.shared.application.EventBusPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class UpdateBookReviewActorTest {

  private EventBusPort eventBusPort;
  private BookReviewRepositoryPort bookReviewRepositoryPort;
  private UpdateBookReviewActor updateBookReviewActor;

  @BeforeEach
  void setUp() {
    DomainMetadataService domainMetadataService = Mockito.mock(DomainMetadataService.class);
    eventBusPort = Mockito.mock(EventBusPort.class);
    UpdateBookReviewEventPublisher updateBookReviewEventPublisher =
        new UpdateBookReviewEventPublisher(domainMetadataService, eventBusPort);

    bookReviewRepositoryPort = Mockito.mock(BookReviewRepositoryPort.class);

    UpdateBookReviewMapper updateBookReviewMapper = Mappers.getMapper(UpdateBookReviewMapper.class);

    updateBookReviewActor =
        new UpdateBookReviewActor(
            updateBookReviewMapper, bookReviewRepositoryPort, updateBookReviewEventPublisher);
  }

  @Test
  void command() {
    Mockito.doNothing()
        .when(bookReviewRepositoryPort)
        .updateBookReview(Mockito.any(BookReview.class));
    UpdateBookReviewCommand command = buildUpdateBookReviewCommand();

    updateBookReviewActor.command(command);

    Mockito.verify(bookReviewRepositoryPort).updateBookReview(Mockito.any(BookReview.class));
    Mockito.verify(eventBusPort).publish(Mockito.any(BookReviewUpdated.class));
  }

  private static UpdateBookReviewCommand buildUpdateBookReviewCommand() {
    return UpdateBookReviewCommand.builder()
        .id(1L)
        .bookId(UUID.randomUUID().toString())
        .userId(UUID.randomUUID().toString())
        .rating(BigDecimal.valueOf(5.1))
        .comment("Great book!")
        .build();
  }
}
