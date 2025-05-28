package com.algalopez.kirjavik.backoffice_app.book.application.update_book;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.backoffice_app.book.domain.model.Book;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UpdateBookMapperTest {

  private final UpdateBookMapper mapper = Mappers.getMapper(UpdateBookMapper.class);

  @Test
  void mapToDomain() {
    UpdateBookCommand command =
        new UpdateBookCommand(
            "01970864-a920-7b34-9be2-1f1703a6396a", "isbn", "title", "author", 400, 2000);

    Book actualBook = mapper.mapToDomain(command);

    assertThat(actualBook)
        .isEqualTo(
            Book.builder()
                .id(UUID.fromString("01970864-a920-7b34-9be2-1f1703a6396a"))
                .isbn("isbn")
                .title("title")
                .author("author")
                .pageCount(400)
                .year(2000)
                .build());
  }
}
