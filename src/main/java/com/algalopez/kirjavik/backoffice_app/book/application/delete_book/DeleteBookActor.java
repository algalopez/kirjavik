package com.algalopez.kirjavik.backoffice_app.book.application.delete_book;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import com.algalopez.kirjavik.backoffice_app.book.domain.port.BookRepositoryPort;

@RequiredArgsConstructor
@ApplicationScoped
public class DeleteBookActor {

  private final BookRepositoryPort bookRepositoryPort;
  private final DeleteBookEventPublisher deleteBookEventPublisher;

  public void command(DeleteBookCommand command) {
    UUID id = UUID.fromString(command.id());
    bookRepositoryPort.deleteBook(id);
    deleteBookEventPublisher.publishBookDeletedEvent(id);
  }
}
