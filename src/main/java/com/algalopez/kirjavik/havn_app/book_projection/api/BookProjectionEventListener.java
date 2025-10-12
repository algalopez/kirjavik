package com.algalopez.kirjavik.havn_app.book_projection.api;

import com.algalopez.kirjavik.havn_app.book_projection.application.create_book_projection.CreateBookProjectionActor;
import com.algalopez.kirjavik.havn_app.book_projection.application.create_book_projection.CreateBookProjectionCommand;
import com.algalopez.kirjavik.havn_app.book_projection.application.delete_book_projection.DeleteBookProjectionActor;
import com.algalopez.kirjavik.havn_app.book_projection.application.delete_book_projection.DeleteBookProjectionCommand;
import com.algalopez.kirjavik.havn_app.book_projection.application.update_book_projection.UpdateBookProjectionActor;
import com.algalopez.kirjavik.havn_app.book_projection.application.update_book_projection.UpdateBookProjectionCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@Slf4j
@RequiredArgsConstructor
@ApplicationScoped
public class BookProjectionEventListener {

  private static final String INVALID_MESSAGE = "Invalid";

  private final ObjectMapper objectMapper;
  private final CreateBookProjectionActor createBookProjectionActor;
  private final UpdateBookProjectionActor updateBookProjectionActor;
  private final DeleteBookProjectionActor deleteBookProjectionActor;

  @Incoming("havn-book-domain-events")
  @Blocking
  public void onBookEvent(JsonObject messageJson) throws JsonProcessingException {
    log.info("Received book event: {}", messageJson);
    String eventType = messageJson.getString("eventType");

    switch (eventType) {
      case BookCreatedEvent.EVENT_TYPE -> {
        BookCreatedEvent event =
            objectMapper.readValue(messageJson.encode(), BookCreatedEvent.class);
        handleBookCreated(event);
      }
      case BookUpdatedEvent.EVENT_TYPE -> {
        BookUpdatedEvent event =
            objectMapper.readValue(messageJson.encode(), BookUpdatedEvent.class);
        handleBookUpdated(event);
      }
      case BookDeletedEvent.EVENT_TYPE -> {
        BookDeletedEvent event =
            objectMapper.readValue(messageJson.encode(), BookDeletedEvent.class);
        handleBookDeleted(event);
      }

      default -> log.warn("Unhandled event type: {}. event: {}", eventType, messageJson);
    }
  }

  private void handleBookCreated(BookCreatedEvent bookCreatedEvent) {
    CreateBookProjectionCommand command =
        CreateBookProjectionCommand.builder()
            .id(bookCreatedEvent.getId())
            .isbn(bookCreatedEvent.getIsbn())
            .title(bookCreatedEvent.getTitle())
            .author(bookCreatedEvent.getAuthor())
            .pageCount(bookCreatedEvent.getPageCount())
            .year(bookCreatedEvent.getYear())
            .build();
    createBookProjectionActor.command(command);
  }

  private void handleBookUpdated(BookUpdatedEvent bookUpdatedEvent) {
    UpdateBookProjectionCommand command =
        UpdateBookProjectionCommand.builder()
            .id(bookUpdatedEvent.getId())
            .isbn(bookUpdatedEvent.getIsbn())
            .title(bookUpdatedEvent.getTitle())
            .author(bookUpdatedEvent.getAuthor())
            .pageCount(bookUpdatedEvent.getPageCount())
            .year(bookUpdatedEvent.getYear())
            .build();
    updateBookProjectionActor.command(command);
  }

  private void handleBookDeleted(BookDeletedEvent bookDeletedEvent) {
    DeleteBookProjectionCommand command =
        DeleteBookProjectionCommand.builder().id(bookDeletedEvent.getId()).build();
    deleteBookProjectionActor.command(command);
  }
}
