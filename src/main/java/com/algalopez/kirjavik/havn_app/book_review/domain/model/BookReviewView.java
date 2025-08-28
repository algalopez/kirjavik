package com.algalopez.kirjavik.havn_app.book_review.domain.model;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

@Builder
public record BookReviewView(
    Long id, UUID bookId, UUID userId, BigDecimal score, String description) {}
