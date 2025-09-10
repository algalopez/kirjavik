package com.algalopez.kirjavik.havn_app.book_projection.application.create_book_projection;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.havn_app.book_projection.domain.model.BookProjection;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CreateBookProjectionMapperTest {

  private final CreateBookProjectionMapper mapper =
      Mappers.getMapper(CreateBookProjectionMapper.class);

  @Test
  void mapToDomain() {
    var command =
        new CreateBookProjectionCommand(
            "01970853-2532-73e2-b36d-2ee42dc19a26", "isbn", "title", "author", 400, 2000);

    BookProjection actualBook = mapper.mapToDomain(command);

    assertThat(actualBook)
        .isEqualTo(
            BookProjection.builder()
                .id(UUID.fromString("01970853-2532-73e2-b36d-2ee42dc19a26"))
                .isbn("isbn")
                .title("title")
                .author("author")
                .pageCount(400)
                .year(2000)
                .build());
  }
}
