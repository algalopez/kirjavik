package com.algalopez.kirjavik.havn_app.book_review.application.update_book_review;

import com.algalopez.kirjavik.havn_app.book_review.domain.event.BookReviewUpdated;
import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReview;
import com.algalopez.kirjavik.shared.application.EventBusPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class UpdateBookReviewEventPublisher {

  private final DomainMetadataService domainMetadataService;
  private final EventBusPort eventBusPort;

  public void publishBookReviewUpdatedEvent(BookReview bookReview) {
    BookReviewUpdated bookReviewUpdated =
        BookReviewUpdated.builder()
            .eventId(domainMetadataService.generateEventId())
            .eventType(BookReviewUpdated.EVENT_TYPE)
            .aggregateId(bookReview.getId().toString())
            .aggregateType(BookReviewUpdated.AGGREGATE_TYPE)
            .dateTime(domainMetadataService.generateEventDateTime())
            .id(bookReview.getId())
            .bookId(bookReview.getBookId())
            .userId(bookReview.getUserId())
            .score(bookReview.getScore())
            .description(bookReview.getDescription())
            .build();

    eventBusPort.publish(bookReviewUpdated);
  }
}
