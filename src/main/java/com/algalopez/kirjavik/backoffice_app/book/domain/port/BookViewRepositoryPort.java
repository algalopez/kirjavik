package com.algalopez.kirjavik.backoffice_app.book.domain.port;

import java.util.List;
import java.util.UUID;
import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookView;
import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookViewSpec;

public interface BookViewRepositoryPort {

  List<BookView> findAllByFilter(BookViewSpec specification);

  BookView findById(UUID id);
}
