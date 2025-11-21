package com.algalopez.kirjavik.havn_app.book_item.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record BookItemProjection(
    UUID id,
    UUID bookId,
    UUID userId,
    BookItemStatus status,
    Integer reviewCount,
    BigDecimal reviewScore,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
