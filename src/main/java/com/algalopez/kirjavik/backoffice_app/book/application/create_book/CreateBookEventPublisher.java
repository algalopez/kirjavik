package com.algalopez.kirjavik.backoffice_app.book.application.create_book;

import com.algalopez.kirjavik.backoffice_app.book.domain.event.BookCreated;
import com.algalopez.kirjavik.backoffice_app.book.domain.model.Book;
import com.algalopez.kirjavik.shared.application.EventBusPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

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
            .author(book.getAuthor())
            .pageCount(book.getPageCount())
            .year(book.getYear())
            .build();

    eventBusPort.publish(bookCreated);
  }
}
