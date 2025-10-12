package com.algalopez.kirjavik.havn_app.book_projection.api;

import com.algalopez.kirjavik.shared.domain.model.DomainEvent;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString(callSuper = true)
@Getter
@SuperBuilder
public class BookDeletedEvent extends DomainEvent {

  public static final String EVENT_TYPE = "BookDeleted";
  public static final String AGGREGATE_TYPE = "Book";

  private final String id;

  @JsonCreator
  public BookDeletedEvent(
      @JsonProperty("id") String id,
      @JsonProperty("eventId") String eventId,
      @JsonProperty("eventType") String eventType,
      @JsonProperty("aggregateId") String aggregateId,
      @JsonProperty("aggregateType") String aggregateType,
      @JsonProperty("dateTime") String dateTime) {
    super(eventId, eventType, aggregateId, aggregateType, dateTime);
    this.id = id;
  }
}
