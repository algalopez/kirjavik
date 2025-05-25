package com.algalopez.kirjavik.backoffice_app.book.application.delete_book;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import com.algalopez.kirjavik.backoffice_app.book.domain.event.BookDeleted;
import com.algalopez.kirjavik.shared.application.EventBusPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;

@RequiredArgsConstructor
@ApplicationScoped
public class DeleteBookEventPublisher {

  private final DomainMetadataService domainMetadataService;
  private final EventBusPort eventBusPort;

  public void publishBookDeletedEvent(UUID bookId) {
    BookDeleted bookDeleted =
        BookDeleted.builder()
            .eventId(domainMetadataService.generateEventId())
            .eventType(BookDeleted.EVENT_TYPE)
            .aggregateId(bookId.toString())
            .aggregateType(BookDeleted.AGGREGATE_TYPE)
            .dateTime(domainMetadataService.generateEventDateTime())
            .build();

    eventBusPort.publish(bookDeleted);
  }
}
