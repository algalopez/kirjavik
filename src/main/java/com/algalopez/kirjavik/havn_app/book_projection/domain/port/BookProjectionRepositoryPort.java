package com.algalopez.kirjavik.havn_app.book_projection.domain.port;

import com.algalopez.kirjavik.havn_app.book_projection.domain.model.BookProjection;
import java.util.UUID;

public interface BookProjectionRepositoryPort {

  BookProjection findBookProjectionById(UUID id);

  void createBookProjection(BookProjection book);

  void updateBookProjection(BookProjection book);

  void deleteBookProjection(UUID id);
}
