package com.algalopez.kirjavik.havn_app.book_projection.application.update_book_projection;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.havn_app.book_projection.domain.model.BookProjection;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UpdateBookProjectionMapperTest {

  private final UpdateBookProjectionMapper mapper =
      Mappers.getMapper(UpdateBookProjectionMapper.class);

  @Test
  void mapToDomain() {
    UpdateBookProjectionCommand command =
        new UpdateBookProjectionCommand(
            "01970864-a920-7b34-9be2-1f1703a6396a", "isbn", "title", "author", 400, 2000);

    BookProjection actualBook = mapper.mapToDomain(command);

    assertThat(actualBook)
        .isEqualTo(
            BookProjection.builder()
                .id(UUID.fromString("01970864-a920-7b34-9be2-1f1703a6396a"))
                .isbn("isbn")
                .title("title")
                .author("author")
                .pageCount(400)
                .year(2000)
                .build());
  }
}
