package com.algalopez.kirjavik.havn_app.book_review.application.update_book_review;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReview;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UpdateBookReviewMapperTest {

  private final UpdateBookReviewMapper mapper = Mappers.getMapper(UpdateBookReviewMapper.class);

  @Test
  void mapToDomain() {
    var command =
        UpdateBookReviewCommand.builder()
            .id(1L)
            .bookId("42742763-5c42-4926-b0fe-c5aa229d1138")
            .userId("5f9380eb-4d5f-4e9a-9110-f7c5acc85dbb")
            .score(BigDecimal.valueOf(8.3))
            .description("description")
            .build();

    BookReview actualBookReview = mapper.mapToDomain(command);

    assertThat(actualBookReview)
        .isEqualTo(
            BookReview.builder()
                .id(1L)
                .bookId(UUID.fromString("42742763-5c42-4926-b0fe-c5aa229d1138"))
                .userId(UUID.fromString("5f9380eb-4d5f-4e9a-9110-f7c5acc85dbb"))
                .score(BigDecimal.valueOf(8.3))
                .description("description")
                .build());
  }
}
