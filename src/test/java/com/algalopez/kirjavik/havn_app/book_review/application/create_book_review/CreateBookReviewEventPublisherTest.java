package com.algalopez.kirjavik.havn_app.book_review.application.create_book_review;

import com.algalopez.kirjavik.havn_app.book_review.domain.event.BookReviewCreated;
import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReview;
import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReviewMother;
import com.algalopez.kirjavik.havn_app.shared.domain.port.EventBusPort;
import com.algalopez.kirjavik.shared.domain.port.DateTimeProviderPort;
import com.algalopez.kirjavik.shared.domain.port.UuidProviderPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateBookReviewEventPublisherTest {

  private UuidProviderPort uuidProvider;
  private DateTimeProviderPort dateTimeProvider;
  private EventBusPort eventBusPort;
  private CreateBookReviewEventPublisher createBookReviewEventPublisher;

  @BeforeEach
  void setUp() {
    dateTimeProvider = Mockito.mock(DateTimeProviderPort.class);
    uuidProvider = Mockito.mock(UuidProviderPort.class);
    DomainMetadataService domainMetadataService =
        new DomainMetadataService(uuidProvider, dateTimeProvider);
    eventBusPort = Mockito.mock(EventBusPort.class);
    createBookReviewEventPublisher =
        new CreateBookReviewEventPublisher(domainMetadataService, eventBusPort);
  }

  @Test
  void publishBookReviewCreatedEvent() {
    BookReview bookReview = new BookReviewMother().build();
    Mockito.when(dateTimeProvider.getDateTime()).thenReturn(LocalDateTime.of(2023, 1, 2, 3, 4, 5));
    Mockito.when(uuidProvider.getUuid())
        .thenReturn(UUID.fromString("412f851f-e749-4ac1-ae6e-3d568468ff94"));

    createBookReviewEventPublisher.publishBookReviewCreatedEvent(bookReview);

    Mockito.verify(eventBusPort)
        .publish(
            BookReviewCreated.builder()
                .eventId("412f851f-e749-4ac1-ae6e-3d568468ff94")
                .eventType(BookReviewCreated.EVENT_TYPE)
                .aggregateId(bookReview.getId().toString())
                .aggregateType(BookReviewCreated.AGGREGATE_TYPE)
                .dateTime("2023-01-02T03:04:05")
                .id(bookReview.getId())
                .bookId(bookReview.getBookId())
                .userId(bookReview.getUserId())
                .rating(bookReview.getRating())
                .comment(bookReview.getComment())
                .build());
  }
}
