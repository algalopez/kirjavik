package com.algalopez.kirjavik.havn_app.book_review.application.delete_book_review;

import com.algalopez.kirjavik.havn_app.book_review.domain.event.BookReviewDeleted;
import com.algalopez.kirjavik.havn_app.shared.domain.port.EventBusPort;
import com.algalopez.kirjavik.shared.domain.port.DateTimeProviderPort;
import com.algalopez.kirjavik.shared.domain.port.UuidProviderPort;
import com.algalopez.kirjavik.shared.domain.service.DomainMetadataService;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeleteBookReviewEventPublisherTest {

  private UuidProviderPort uuidProvider;
  private DateTimeProviderPort dateTimeProvider;
  private EventBusPort eventBusPort;
  private DeleteBookReviewEventPublisher deleteBookReviewEventPublisher;

  @BeforeEach
  void setUp() {
    dateTimeProvider = Mockito.mock(DateTimeProviderPort.class);
    uuidProvider = Mockito.mock(UuidProviderPort.class);
    DomainMetadataService domainMetadataService =
        new DomainMetadataService(uuidProvider, dateTimeProvider);
    eventBusPort = Mockito.mock(EventBusPort.class);
    deleteBookReviewEventPublisher =
        new DeleteBookReviewEventPublisher(domainMetadataService, eventBusPort);
  }

  @Test
  void publishBookReviewDeletedEvent() {
    Long id = 234L;
    Mockito.when(dateTimeProvider.getDateTime()).thenReturn(LocalDateTime.of(2023, 1, 2, 3, 4, 5));
    Mockito.when(uuidProvider.getUuid())
        .thenReturn(UUID.fromString("412f851f-e749-4ac1-ae6e-3d568468ff94"));

    deleteBookReviewEventPublisher.publishBookReviewDeletedEvent(id);

    Mockito.verify(eventBusPort)
        .publish(
            BookReviewDeleted.builder()
                .eventId("412f851f-e749-4ac1-ae6e-3d568468ff94")
                .eventType(BookReviewDeleted.EVENT_TYPE)
                .aggregateId(id.toString())
                .aggregateType(BookReviewDeleted.AGGREGATE_TYPE)
                .dateTime("2023-01-02T03:04:05")
                .id(id)
                .build());
  }
}
