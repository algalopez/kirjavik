package com.algalopez.kirjavik.havn_app.book_projection.application.create_book_projection;

import com.algalopez.kirjavik.havn_app.book_projection.domain.model.BookProjection;
import com.algalopez.kirjavik.havn_app.book_projection.domain.port.BookProjectionRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class CreateBookProjectionActor {

  private final CreateBookProjectionMapper createBookMapper;
  private final BookProjectionRepositoryPort bookProjectionRepository;

  public void command(CreateBookProjectionCommand command) {
    BookProjection book = createBookMapper.mapToDomain(command);
    bookProjectionRepository.createBookProjection(book);
  }
}
