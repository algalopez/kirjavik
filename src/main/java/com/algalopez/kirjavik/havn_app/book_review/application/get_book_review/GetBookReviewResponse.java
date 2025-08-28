package com.algalopez.kirjavik.havn_app.book_review.application.get_book_review;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record GetBookReviewResponse(BookReview bookReview) {
  @Builder
  public record BookReview(
      Long id, String bookId, String userId, BigDecimal score, String description) {}
}
