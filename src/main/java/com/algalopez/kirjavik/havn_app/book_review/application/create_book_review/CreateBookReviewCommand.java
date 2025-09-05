package com.algalopez.kirjavik.havn_app.book_review.application.create_book_review;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record CreateBookReviewCommand(
        String bookId, String userId, BigDecimal rating, String comment) {}
