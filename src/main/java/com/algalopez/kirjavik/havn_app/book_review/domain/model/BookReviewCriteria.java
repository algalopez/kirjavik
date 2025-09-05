package com.algalopez.kirjavik.havn_app.book_review.domain.model;

import java.util.UUID;
import lombok.Builder;

@Builder
public record BookReviewCriteria(UUID bookId, UUID userId) {}
