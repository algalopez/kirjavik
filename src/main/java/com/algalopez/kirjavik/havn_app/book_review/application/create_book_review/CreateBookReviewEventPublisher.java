package com.algalopez.kirjavik.havn_app.book_review.application.create_book_review;

import com.algalopez.kirjavik.havn_app.book_review.domain.event.BookReviewCreated;
import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReview;
import com.algalopez.kirjavik.shared.application.EventBusPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class CreateBookReviewEventPublisher {

  private final DomainMetadataService domainMetadataService;
  private final EventBusPort eventBusPort;

  public void publishBookReviewCreatedEvent(BookReview bookReview) {
    BookReviewCreated bookReviewCreated =
        BookReviewCreated.builder()
            .eventId(domainMetadataService.generateEventId())
            .eventType(BookReviewCreated.EVENT_TYPE)
            .aggregateId(bookReview.getId().toString())
            .aggregateType(BookReviewCreated.AGGREGATE_TYPE)
            .dateTime(domainMetadataService.generateEventDateTime())
            .id(bookReview.getId())
            .bookId(bookReview.getBookId())
            .userId(bookReview.getUserId())
            .rating(bookReview.getRating())
            .comment(bookReview.getComment())
            .build();

    eventBusPort.publish(bookReviewCreated);
  }
}
