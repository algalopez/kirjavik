package org.algalopez.kirjavik.backoffice_app.book.application.update_book;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.algalopez.kirjavik.backoffice_app.book.domain.exception.BookNotFoundException;
import org.algalopez.kirjavik.backoffice_app.book.domain.model.Book;
import org.algalopez.kirjavik.backoffice_app.book.domain.port.BookRepositoryPort;

@RequiredArgsConstructor
@ApplicationScoped
public class UpdateBookActor {

  private final UpdateBookMapper updateBookMapper;
  private final BookRepositoryPort bookRepositoryPort;
  private final UpdateBookEventPublisher updateBookEventPublisher;

  public void command(UpdateBookCommand command) {
    UUID id = UUID.fromString(command.id());
    ensureBookExists(id);
    Book updatedBook = updateBookMapper.mapToDomain(command);
    bookRepositoryPort.updateBook(updatedBook);
    updateBookEventPublisher.publishBookUpdatedEvent(updatedBook);
  }

  private void ensureBookExists(UUID id) {
    Book book = bookRepositoryPort.findBookById(id);
    if (book == null) {
      throw new BookNotFoundException("Book " + id + " does not exist.");
    }
  }
}
