package org.algalopez.kirjavik.backoffice_app.book.application.create_book;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.algalopez.kirjavik.backoffice_app.book.domain.model.Book;
import org.algalopez.kirjavik.backoffice_app.book.domain.port.BookRepositoryPort;

@RequiredArgsConstructor
@ApplicationScoped
public class CreateBookActor {

  private final CreateBookMapper createBookMapper;
  private final BookRepositoryPort bookRepositoryPort;
  private final CreateBookEventPublisher createBookEventPublisher;

  public void command(CreateBookCommand command) {
    Book book = createBookMapper.mapToDomain(command);
    bookRepositoryPort.createBook(book);

    createBookEventPublisher.publishBookCreatedEvent(book);
  }
}
