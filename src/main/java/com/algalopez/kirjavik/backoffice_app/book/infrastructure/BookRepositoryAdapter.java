package com.algalopez.kirjavik.backoffice_app.book.infrastructure;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import com.algalopez.kirjavik.backoffice_app.book.domain.model.Book;
import com.algalopez.kirjavik.backoffice_app.book.domain.port.BookRepositoryPort;

@Slf4j
@ApplicationScoped
public class BookRepositoryAdapter implements BookRepositoryPort {
  @Override
  public Book findBookById(UUID id) {
    return null;
  }

  @Override
  public void createBook(Book book) {
    log.info("Creating book: {}", book);
  }

  @Override
  public void updateBook(Book book) {
    log.info("Updating book: {}", book);
  }

  @Override
  public void deleteBook(UUID id) {
    log.info("Deleting book: {}", id);
  }
}
