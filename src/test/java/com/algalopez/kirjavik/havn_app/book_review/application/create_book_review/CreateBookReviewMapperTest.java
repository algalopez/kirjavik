package com.algalopez.kirjavik.havn_app.book_review.application.create_book_review;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReview;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CreateBookReviewMapperTest {

  private final CreateBookReviewMapper mapper = Mappers.getMapper(CreateBookReviewMapper.class);

  @Test
  void mapToDomain() {
    var command =
        CreateBookReviewCommand.builder()
            .bookId("baf7bbd0-4824-4ff4-ab6e-272ae23e9a34")
            .userId("f3f340b8-5ee6-4aec-a6a2-9d0b27eb255c")
            .rating(BigDecimal.valueOf(3.5))
            .comment("comment")
            .build();

    BookReview bookReview = mapper.mapToDomain(command);

    assertThat(bookReview)
        .isEqualTo(
            BookReview.builder()
                .id(null)
                .bookId(UUID.fromString("baf7bbd0-4824-4ff4-ab6e-272ae23e9a34"))
                .userId(UUID.fromString("f3f340b8-5ee6-4aec-a6a2-9d0b27eb255c"))
                .rating(BigDecimal.valueOf(3.5))
                .comment("comment")
                .build());
  }
}
