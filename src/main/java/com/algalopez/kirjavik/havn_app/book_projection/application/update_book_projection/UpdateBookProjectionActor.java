package com.algalopez.kirjavik.havn_app.book_projection.application.update_book_projection;

import com.algalopez.kirjavik.havn_app.book_projection.domain.exception.BookProjectionNotFoundException;
import com.algalopez.kirjavik.havn_app.book_projection.domain.model.BookProjection;
import com.algalopez.kirjavik.havn_app.book_projection.domain.port.BookProjectionRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class UpdateBookProjectionActor {

  private final UpdateBookProjectionMapper updateBookProjectionMapper;
  private final BookProjectionRepositoryPort bookProjectionRepository;

  public void command(UpdateBookProjectionCommand command) {
    UUID id = UUID.fromString(command.id());
    ensureBookProjectionExists(id);
    BookProjection updatedBookProjection = updateBookProjectionMapper.mapToDomain(command);
    bookProjectionRepository.updateBookProjection(updatedBookProjection);
  }

  private void ensureBookProjectionExists(UUID id) {
    BookProjection bookProjection = bookProjectionRepository.findBookProjectionById(id);
    if (bookProjection == null) {
      throw new BookProjectionNotFoundException("Book projection" + id + " does not exist");
    }
  }
}
