package com.algalopez.kirjavik.havn_app.book_review.application.delete_book_review;

import com.algalopez.kirjavik.havn_app.book_review.domain.event.BookReviewDeleted;
import com.algalopez.kirjavik.havn_app.shared.domain.port.EventBusPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class DeleteBookReviewEventPublisher {

  private final DomainMetadataService domainMetadataService;
  private final EventBusPort eventBusPort;

  public void publishBookReviewDeletedEvent(Long id) {
    BookReviewDeleted bookReviewDeleted =
        BookReviewDeleted.builder()
            .eventId(domainMetadataService.generateEventId())
            .eventType(BookReviewDeleted.EVENT_TYPE)
            .aggregateId(id.toString())
            .aggregateType(BookReviewDeleted.AGGREGATE_TYPE)
            .dateTime(domainMetadataService.generateEventDateTime())
            .id(id)
            .build();

    eventBusPort.publish(bookReviewDeleted);
  }
}
