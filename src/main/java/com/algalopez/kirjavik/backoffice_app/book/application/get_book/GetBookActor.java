package com.algalopez.kirjavik.backoffice_app.book.application.get_book;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import com.algalopez.kirjavik.backoffice_app.book.domain.exception.BookNotFoundException;
import com.algalopez.kirjavik.backoffice_app.book.domain.port.BookViewRepositoryPort;
import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookView;

@RequiredArgsConstructor
@ApplicationScoped
public class GetBookActor {

  private final GetBookMapper getBookMapper;
  private final BookViewRepositoryPort bookViewRepository;

  public GetBookResponse query(GetBookQuery query) {
    UUID id = UUID.fromString(query.id());
    BookView book = bookViewRepository.findById(id);
    if (book == null) {
      throw new BookNotFoundException("Book " + id + " does not exist");
    }

    return getBookMapper.mapToResponse(book);
  }
}
