package com.algalopez.kirjavik.backoffice_app.book.application.create_book;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.backoffice_app.book.domain.model.Book;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CreateBookMapperTest {

  private final CreateBookMapper mapper = Mappers.getMapper(CreateBookMapper.class);

  @Test
  void mapToDomain() {
    var command =
        new CreateBookCommand(
            "01970853-2532-73e2-b36d-2ee42dc19a26", "isbn", "title", "author", 400, 2000);

    Book actualBook = mapper.mapToDomain(command);

    assertThat(actualBook)
        .isEqualTo(
            Book.builder()
                .id(UUID.fromString("01970853-2532-73e2-b36d-2ee42dc19a26"))
                .isbn("isbn")
                .title("title")
                .author("author")
                .pageCount(400)
                .year(2000)
                .build());
  }
}
