package com.algalopez.kirjavik.havn_app.book_projection.application.create_book_projection;

import com.algalopez.kirjavik.havn_app.book_projection.domain.model.BookProjection;
import com.algalopez.kirjavik.havn_app.book_projection.domain.port.BookProjectionRepositoryPort;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class CreateBookProjectionActorTest {

  private BookProjectionRepositoryPort bookProjectionRepository;
  private CreateBookProjectionActor createBookProjectionActor;

  @BeforeEach
  void setUp() {
    bookProjectionRepository = Mockito.mock(BookProjectionRepositoryPort.class);
    CreateBookProjectionMapper createBookProjectionMapper =
        Mappers.getMapper(CreateBookProjectionMapper.class);
    createBookProjectionActor =
        new CreateBookProjectionActor(createBookProjectionMapper, bookProjectionRepository);
  }

  @Test
  void command() {
    CreateBookProjectionCommand createBookProjectionCommand = buildCreateBookProjectionCommand();

    createBookProjectionActor.command(createBookProjectionCommand);

    Mockito.verify(bookProjectionRepository)
        .createBookProjection(
            BookProjection.builder()
                .id(UUID.fromString(createBookProjectionCommand.id()))
                .isbn(createBookProjectionCommand.isbn())
                .title(createBookProjectionCommand.title())
                .author(createBookProjectionCommand.author())
                .pageCount(createBookProjectionCommand.pageCount())
                .year(createBookProjectionCommand.year())
                .build());
  }

  private static CreateBookProjectionCommand buildCreateBookProjectionCommand() {
    return CreateBookProjectionCommand.builder()
        .id(UUID.randomUUID().toString())
        .isbn("isbn")
        .title("title")
        .author("author")
        .pageCount(1)
        .year(2000)
        .build();
  }
}
