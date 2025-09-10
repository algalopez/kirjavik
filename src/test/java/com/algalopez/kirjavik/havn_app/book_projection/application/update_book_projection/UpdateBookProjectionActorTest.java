package com.algalopez.kirjavik.havn_app.book_projection.application.update_book_projection;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.algalopez.kirjavik.havn_app.book_projection.domain.exception.BookProjectionNotFoundException;
import com.algalopez.kirjavik.havn_app.book_projection.domain.model.BookProjection;
import com.algalopez.kirjavik.havn_app.book_projection.domain.model.BookProjectionMother;
import com.algalopez.kirjavik.havn_app.book_projection.domain.port.BookProjectionRepositoryPort;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class UpdateBookProjectionActorTest {

  private BookProjectionRepositoryPort bookProjectionRepository;
  private UpdateBookProjectionActor updateBookProjectionActor;

  @BeforeEach
  void setUp() {
    UpdateBookProjectionMapper updateBookProjectionMapper =
        Mappers.getMapper(UpdateBookProjectionMapper.class);
    bookProjectionRepository = Mockito.mock(BookProjectionRepositoryPort.class);
    updateBookProjectionActor =
        new UpdateBookProjectionActor(updateBookProjectionMapper, bookProjectionRepository);
  }

  @Test
  void command() {
    UpdateBookProjectionCommand updateBookProjectionCommand = buildUpdateBookProjectionCommand();
    Mockito.when(bookProjectionRepository.findBookProjectionById(Mockito.any(UUID.class)))
        .thenReturn(new BookProjectionMother().build());

    updateBookProjectionActor.command(updateBookProjectionCommand);

    Mockito.verify(bookProjectionRepository)
        .updateBookProjection(
            BookProjection.builder()
                .id(UUID.fromString(updateBookProjectionCommand.id()))
                .isbn(updateBookProjectionCommand.isbn())
                .title(updateBookProjectionCommand.title())
                .author(updateBookProjectionCommand.author())
                .pageCount(updateBookProjectionCommand.pageCount())
                .year(updateBookProjectionCommand.year())
                .build());
  }

  @Test
  void command_whenBookProjectionDoesNotExist() {
    UpdateBookProjectionCommand updateBookProjectionCommand = buildUpdateBookProjectionCommand();

    assertThatExceptionOfType(BookProjectionNotFoundException.class)
        .isThrownBy(() -> updateBookProjectionActor.command(updateBookProjectionCommand));

    Mockito.verify(bookProjectionRepository, Mockito.never())
        .updateBookProjection(Mockito.any(BookProjection.class));
  }

  private static UpdateBookProjectionCommand buildUpdateBookProjectionCommand() {
    return UpdateBookProjectionCommand.builder()
        .id(UUID.randomUUID().toString())
        .isbn("123")
        .title("title")
        .author("author")
        .pageCount(1)
        .year(1)
        .build();
  }
}
