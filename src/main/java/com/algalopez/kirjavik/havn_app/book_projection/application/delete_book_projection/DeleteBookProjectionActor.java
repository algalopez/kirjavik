package com.algalopez.kirjavik.havn_app.book_projection.application.delete_book_projection;

import com.algalopez.kirjavik.havn_app.book_projection.domain.port.BookProjectionRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class DeleteBookProjectionActor {
  private final BookProjectionRepositoryPort bookProjectionRepository;

  public void command(DeleteBookProjectionCommand command) {
    UUID id = UUID.fromString(command.id());
    bookProjectionRepository.deleteBookProjection(id);
  }
}
