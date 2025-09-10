package com.algalopez.kirjavik.havn_app.book_projection.api;

import com.algalopez.kirjavik.havn_app.book_projection.application.create_book_projection.CreateBookProjectionActor;
import com.algalopez.kirjavik.havn_app.book_projection.application.delete_book_projection.DeleteBookProjectionActor;
import com.algalopez.kirjavik.havn_app.book_projection.application.update_book_projection.UpdateBookProjectionActor;
import com.algalopez.kirjavik.shared.domain.model.DomainEvent;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@Slf4j
@ApplicationScoped
public class BookProjectionEventListener {

  @Inject CreateBookProjectionActor createBookProjectionActor;
  @Inject UpdateBookProjectionActor updateBookProjectionActor;
  @Inject DeleteBookProjectionActor deleteBookProjectionActor;

  @Incoming("backoffice-book-events")
  @Blocking
  public void onBookEvent(DomainEvent event) {
    log.info("Received book event: {}", event);

    switch (event.getEventType()) {
      //      case "BookCreated" -> createBookProjectionActor.handleBookCreated(event);
      //      case "BookUpdated" -> updateBookProjectionActor.handleBookUpdated(event);
      //      case "BookDeleted" -> deleteBookProjectionActor.handleBookDeleted(event);
      default -> log.warn("Unhandled event type: {}", event.getEventType());
    }
  }
}
