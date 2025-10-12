package com.algalopez.kirjavik.havn_app.book_projection.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;

import com.algalopez.kirjavik.havn_app.book_projection.application.create_book_projection.CreateBookProjectionActor;
import com.algalopez.kirjavik.havn_app.book_projection.application.create_book_projection.CreateBookProjectionCommand;
import com.algalopez.kirjavik.havn_app.book_projection.application.delete_book_projection.DeleteBookProjectionActor;
import com.algalopez.kirjavik.havn_app.book_projection.application.delete_book_projection.DeleteBookProjectionCommand;
import com.algalopez.kirjavik.havn_app.book_projection.application.update_book_projection.UpdateBookProjectionActor;
import com.algalopez.kirjavik.havn_app.book_projection.application.update_book_projection.UpdateBookProjectionCommand;
import com.algalopez.kirjavik.shared.infrastructure.RabbitMqTestClient;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class BookProjectionEventListenerApiTest {

  private static final String EXCHANGE_NAME = "kirjavik.backoffice.domain-events";

  @InjectMock CreateBookProjectionActor createBookProjectionActor;
  @InjectMock UpdateBookProjectionActor updateBookProjectionActor;
  @InjectMock DeleteBookProjectionActor deleteBookProjectionActor;
  @Inject RabbitMqTestClient rabbitMqTestClient;

  @Test
  void onBookEvent_whenBookCreated() {
    String eventJson =
        """
        {
          "eventId": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
          "eventType": "BookCreated",
          "aggregateId": "e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a",
          "aggregateType": "Book",
          "id": "e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a",
          "isbn": "9780340839930",
          "title": "Dune",
          "author": "Frank Herbert",
          "pageCount": 412,
          "year": 1965
        }
        """;

    rabbitMqTestClient.publishMessage(
        EXCHANGE_NAME,
        "kirjavik.backoffice.book.BookCreated",
        eventJson.getBytes(StandardCharsets.UTF_8));

    Mockito.verify(createBookProjectionActor, Mockito.timeout(5000))
        .command(
            CreateBookProjectionCommand.builder()
                .id("e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a")
                .isbn("9780340839930")
                .title("Dune")
                .author("Frank Herbert")
                .pageCount(412)
                .year(1965)
                .build());
  }

  @Test
  void onBookEvent_whenBookUpdated() {
    String eventJson =
        """
        {
          "eventId": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
          "eventType": "BookUpdated",
          "aggregateId": "e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a",
          "aggregateType": "Book",
          "id": "e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a",
          "isbn": "9780340839930",
          "title": "Dune",
          "author": "Frank Herbert",
          "pageCount": 412,
          "year": 1965
        }
        """;

    rabbitMqTestClient.publishMessage(
        EXCHANGE_NAME,
        "kirjavik.backoffice.book.BookUpdated",
        eventJson.getBytes(StandardCharsets.UTF_8));

    Mockito.verify(updateBookProjectionActor, Mockito.timeout(5000))
        .command(
            UpdateBookProjectionCommand.builder()
                .id("e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a")
                .isbn("9780340839930")
                .title("Dune")
                .author("Frank Herbert")
                .pageCount(412)
                .year(1965)
                .build());
  }

  @Test
  void onBookEvent_whenBookDeleted() {
    String eventJson =
        """
        {
          "eventId": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
          "eventType": "BookDeleted",
          "aggregateId": "e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a",
          "aggregateType": "Book",
          "id": "e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a"
        }
        """;

    rabbitMqTestClient.publishMessage(
        EXCHANGE_NAME,
        "kirjavik.backoffice.book.BookDeleted",
        eventJson.getBytes(StandardCharsets.UTF_8));

    Mockito.verify(deleteBookProjectionActor, Mockito.timeout(5000))
        .command(
            DeleteBookProjectionCommand.builder()
                .id("e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a")
                .build());
  }

  @Test
  void onBookEvent_whenInvalidEventType() {
    String eventJson =
        """
        {
          "eventId": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
          "eventType": "Invalid",
          "aggregateId": "e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a",
          "aggregateType": "Book",
          "id": "e8b5a51d-9b16-4c88-9f12-1b2c3d4e5f6a"
        }
        """;

    assertThatNoException()
        .isThrownBy(
            () ->
                rabbitMqTestClient.publishMessage(
                    EXCHANGE_NAME,
                    "kirjavik.backoffice.book.BookDeleted",
                    eventJson.getBytes(StandardCharsets.UTF_8)));
  }
}
