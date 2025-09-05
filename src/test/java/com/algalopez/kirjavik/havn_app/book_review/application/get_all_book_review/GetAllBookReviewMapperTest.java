package com.algalopez.kirjavik.havn_app.book_review.application.get_all_book_review;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReviewView;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class GetAllBookReviewMapperTest {

  private final GetAllBookReviewMapper mapper = Mappers.getMapper(GetAllBookReviewMapper.class);

  @Test
  void toResponse() {
    BookReviewView bookReviewView =
        BookReviewView.builder()
            .id(123L)
            .bookId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
            .bookTitle("Sample Book")
            .userId(UUID.fromString("00000000-0000-0000-0000-000000000002"))
            .rating(BigDecimal.valueOf(5.2))
            .comment("Great book!")
            .build();

    GetAllBookReviewResponse.BookReview actualResponse = mapper.toResponse(bookReviewView);

    assertThat(actualResponse)
        .isEqualTo(
            GetAllBookReviewResponse.BookReview.builder()
                .id(123L)
                .bookId("00000000-0000-0000-0000-000000000001")
                .bookTitle("Sample Book")
                .userId("00000000-0000-0000-0000-000000000002")
                .rating(BigDecimal.valueOf(5.2))
                .comment("Great book!")
                .build());
  }
}
