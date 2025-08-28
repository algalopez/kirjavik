package com.algalopez.kirjavik.havn_app.book_review.domain.model;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookReview {
  private Long id;
  private UUID bookId;
  private UUID userId;
  private BigDecimal score;
  private String description;
}
