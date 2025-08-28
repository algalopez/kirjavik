package com.algalopez.kirjavik.havn_app.book_review.domain.event;

import com.algalopez.kirjavik.shared.domain.model.DomainEvent;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString(callSuper = true)
@Getter
@SuperBuilder
public class BookReviewCreated extends DomainEvent {
  public static final String EVENT_TYPE = "BookReviewCreated";
  public static final String AGGREGATE_TYPE = "BookReview";

  private final Long id;
  private final UUID bookId;
  private final UUID userId;
  private final BigDecimal score;
  private final String description;
}
