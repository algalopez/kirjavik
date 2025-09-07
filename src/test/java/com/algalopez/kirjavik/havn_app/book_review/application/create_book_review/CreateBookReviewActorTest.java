package com.algalopez.kirjavik.havn_app.book_review.application.create_book_review;

import com.algalopez.kirjavik.havn_app.book_review.domain.event.BookReviewCreated;
import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReview;
import com.algalopez.kirjavik.havn_app.book_review.domain.port.BookReviewRepositoryPort;
import com.algalopez.kirjavik.havn_app.shared.domain.port.EventBusPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class CreateBookReviewActorTest {

  private EventBusPort eventBusPort;
  private BookReviewRepositoryPort bookReviewRepositoryPort;
  private CreateBookReviewActor createBookReviewActor;

  @BeforeEach
  void setUp() {
    DomainMetadataService domainMetadataService = Mockito.mock(DomainMetadataService.class);
    eventBusPort = Mockito.mock(EventBusPort.class);
    CreateBookReviewEventPublisher createBookReviewEventPublisher =
        new CreateBookReviewEventPublisher(domainMetadataService, eventBusPort);

    bookReviewRepositoryPort = Mockito.mock(BookReviewRepositoryPort.class);

    CreateBookReviewMapper createBookReviewMapper = Mappers.getMapper(CreateBookReviewMapper.class);

    createBookReviewActor =
        new CreateBookReviewActor(
            createBookReviewMapper, bookReviewRepositoryPort, createBookReviewEventPublisher);
  }

  @Test
  void command() {
    Mockito.doAnswer(
            i -> {
              BookReview bookReview = i.getArgument(0);
              bookReview.setId(1L);
              return null;
            })
        .when(bookReviewRepositoryPort)
        .createBookReview(Mockito.any(BookReview.class));
    CreateBookReviewCommand command = buildCreateBookReviewCommand();

    createBookReviewActor.command(command);

    Mockito.verify(bookReviewRepositoryPort).createBookReview(Mockito.any(BookReview.class));
    Mockito.verify(eventBusPort).publish(Mockito.any(BookReviewCreated.class));
  }

  private static CreateBookReviewCommand buildCreateBookReviewCommand() {
    return CreateBookReviewCommand.builder()
        .bookId(UUID.randomUUID().toString())
        .userId(UUID.randomUUID().toString())
        .rating(BigDecimal.valueOf(5.1))
        .comment("Great book!")
        .build();
  }
}
