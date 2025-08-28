package com.algalopez.kirjavik.havn_app.book_review.application.get_all_book_review;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;

@Builder
public record GetAllBookReviewResponse(List<BookReview> bookReviews) {

  @Builder
  public record BookReview(
      Long id, String bookId, String userId, BigDecimal score, String description) {}
}
