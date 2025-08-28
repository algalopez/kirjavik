package com.algalopez.kirjavik.havn_app.book_review.application.update_book_review;

import java.math.BigDecimal;
import lombok.Builder;

@Builder(toBuilder = true)
public record UpdateBookReviewCommand(
    Long id, String bookId, String userId, BigDecimal score, String description) {}
