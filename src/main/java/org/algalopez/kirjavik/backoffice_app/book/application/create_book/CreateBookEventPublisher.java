package org.algalopez.kirjavik.backoffice_app.book.application.create_book;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.algalopez.kirjavik.backoffice_app.book.domain.event.BookCreated;
import org.algalopez.kirjavik.backoffice_app.book.domain.model.Book;
import org.algalopez.kirjavik.shared.application.EventBusPort;
import org.algalopez.kirjavik.shared.domain.service.DomainMetadataService;

@RequiredArgsConstructor
@ApplicationScoped
public class CreateBookEventPublisher {

  private final DomainMetadataService domainMetadataService;
  private final EventBusPort eventBusPort;

  public void publishBookCreatedEvent(Book book) {
    BookCreated bookCreated =
        BookCreated.builder()
            .eventId(domainMetadataService.generateEventId())
            .eventType(BookCreated.EVENT_TYPE)
            .aggregateId(book.getId().toString())
            .aggregateType(BookCreated.AGGREGATE_TYPE)
            .dateTime(domainMetadataService.generateEventDateTime())
            .id(book.getId())
            .isbn(book.getIsbn())
            .title(book.getTitle())
            .authors(book.getAuthors())
            .pageCount(book.getPageCount())
            .year(book.getYear())
            .build();

    eventBusPort.publish(bookCreated);
  }
}
