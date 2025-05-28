package com.algalopez.kirjavik.backoffice_app.book.application.get_book;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookView;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class GetBookMapperTest {

  private final GetBookMapper mapper = Mappers.getMapper(GetBookMapper.class);

  @Test
  void mapToResponse() {
    BookView book =
        BookView.builder()
            .id(UUID.fromString("01970861-aea1-7e57-ae93-478eb6b46458"))
            .isbn("isbn")
            .title("title")
            .author("author")
            .pageCount(400)
            .year(2000)
            .build();

    GetBookResponse actualResponse = mapper.mapToResponse(book);

    assertThat(actualResponse)
        .isEqualTo(
            GetBookResponse.builder()
                .id("01970861-aea1-7e57-ae93-478eb6b46458")
                .isbn("isbn")
                .title("title")
                .author("author")
                .pageCount(400)
                .year(2000)
                .build());
  }
}
