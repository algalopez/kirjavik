package com.algalopez.kirjavik.havn_app.book_item.domain.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString(callSuper = true)
@Getter
@SuperBuilder
public final class BookItemAdded extends BookItemDomainEvent {
  public static final String EVENT_TYPE = "BookItemAdded";
  public static final String AGGREGATE_TYPE = "BookItem";

  private final String id;
  private final String bookId;
  private final String userId;
}
