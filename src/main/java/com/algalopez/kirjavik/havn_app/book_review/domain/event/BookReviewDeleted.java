package com.algalopez.kirjavik.havn_app.book_review.domain.event;

import com.algalopez.kirjavik.shared.domain.model.DomainEvent;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString(callSuper = true)
@Getter
@SuperBuilder
public class BookReviewDeleted extends DomainEvent {

  public static final String EVENT_TYPE = "BookReviewDeleted";
  public static final String AGGREGATE_TYPE = "BookReview";

  private final Long id;
}
