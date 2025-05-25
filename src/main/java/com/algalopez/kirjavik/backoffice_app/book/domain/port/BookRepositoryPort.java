package com.algalopez.kirjavik.backoffice_app.book.domain.port;

import java.util.UUID;
import com.algalopez.kirjavik.backoffice_app.book.domain.model.Book;

public interface BookRepositoryPort {

  Book findBookById(UUID id);

  void createBook(Book book);

  void updateBook(Book book);

  void deleteBook(UUID id);
}
