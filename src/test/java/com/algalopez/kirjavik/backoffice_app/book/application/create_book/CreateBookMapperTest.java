package com.algalopez.kirjavik.backoffice_app.book.application.create_book;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import com.algalopez.kirjavik.backoffice_app.book.domain.model.Book;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CreateBookMapperTest {

  private final CreateBookMapper mapper = Mappers.getMapper(CreateBookMapper.class);

  @Test
  void mapToDomain() {
    var command =
        new CreateBookCommand(
            "01970853-2532-73e2-b36d-2ee42dc19a26", "isbn", "title", List.of("author"), 400, 2000);

    Book actualBook = mapper.mapToDomain(command);

    assertThat(actualBook)
        .isEqualTo(
            Book.builder()
                .id(UUID.fromString("01970853-2532-73e2-b36d-2ee42dc19a26"))
                .isbn("isbn")
                .title("title")
                .authors(List.of("author"))
                .pageCount(400)
                .year(2000)
                .build());
  }
}
