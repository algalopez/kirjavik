package com.algalopez.kirjavik.havn_app.book_projection.api;

import com.algalopez.kirjavik.shared.domain.model.DomainEvent;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@ToString(callSuper = true)
@Getter
@SuperBuilder
public class BookCreatedEvent extends DomainEvent {

  public static final String EVENT_TYPE = "BookCreated";
  public static final String AGGREGATE_TYPE = "Book";

  private final String id;
  private final String isbn;
  private final String title;
  private final String author;
  private final Integer pageCount;
  private final Integer year;

  @JsonCreator
  public BookCreatedEvent(
      @JsonProperty("id") String id,
      @JsonProperty("isbn") String isbn,
      @JsonProperty("title") String title,
      @JsonProperty("author") String author,
      @JsonProperty("pageCount") Integer pageCount,
      @JsonProperty("year") Integer year,
      @JsonProperty("eventId") String eventId,
      @JsonProperty("eventType") String eventType,
      @JsonProperty("aggregateId") String aggregateId,
      @JsonProperty("aggregateType") String aggregateType,
      @JsonProperty("dateTime") String dateTime) {
    super(eventId, eventType, aggregateId, aggregateType, dateTime);
    this.id = id;
    this.isbn = isbn;
    this.title = title;
    this.author = author;
    this.pageCount = pageCount;
    this.year = year;
  }
}
