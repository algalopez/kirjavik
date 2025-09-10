package com.algalopez.kirjavik.havn_app.book_projection.application.delete_book_projection;

import com.algalopez.kirjavik.havn_app.book_projection.domain.port.BookProjectionRepositoryPort;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DeleteBookProjectionActorTest {

  private BookProjectionRepositoryPort bookProjectionRepository;
  private DeleteBookProjectionActor deleteBookProjectionActor;

  @BeforeEach
  void setUp() {
    bookProjectionRepository = Mockito.mock(BookProjectionRepositoryPort.class);
    deleteBookProjectionActor = new DeleteBookProjectionActor(bookProjectionRepository);
  }

  @Test
  void command() {
    UUID id = UUID.randomUUID();
    DeleteBookProjectionCommand deleteBookProjectionCommand =
        DeleteBookProjectionCommand.builder().id(id.toString()).build();

    deleteBookProjectionActor.command(deleteBookProjectionCommand);

    Mockito.verify(bookProjectionRepository).deleteBookProjection(id);
  }
}
