package com.algalopez.kirjavik.havn_app.book_review.application.get_book_review;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record GetBookReviewResponse(
    Long id,
    String bookId,
    String bookTitle,
    String userId,
    BigDecimal rating,
    String comment) {}
