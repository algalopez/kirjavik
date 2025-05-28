package com.algalopez.kirjavik.backoffice_app.book.application.update_book;

import com.algalopez.kirjavik.backoffice_app.book.domain.event.BookUpdated;
import com.algalopez.kirjavik.backoffice_app.book.domain.model.Book;
import com.algalopez.kirjavik.shared.application.EventBusPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class UpdateBookEventPublisher {

  private final DomainMetadataService domainMetadataService;
  private final EventBusPort eventBusPort;

  public void publishBookUpdatedEvent(Book book) {
    BookUpdated bookUpdated =
        BookUpdated.builder()
            .eventId(domainMetadataService.generateEventId())
            .eventType(BookUpdated.EVENT_TYPE)
            .aggregateId(book.getId().toString())
            .aggregateType(BookUpdated.AGGREGATE_TYPE)
            .dateTime(domainMetadataService.generateEventDateTime())
            .id(book.getId())
            .isbn(book.getIsbn())
            .title(book.getTitle())
            .author(book.getAuthor())
            .pageCount(book.getPageCount())
            .year(book.getYear())
            .build();

    eventBusPort.publish(bookUpdated);
  }
}
