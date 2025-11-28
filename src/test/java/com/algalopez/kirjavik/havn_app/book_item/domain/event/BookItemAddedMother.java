package com.algalopez.kirjavik.havn_app.book_item.domain.event;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.datafaker.Faker;

@Setter
@Accessors(fluent = true)
public class BookItemAddedMother {
  private String id;
  private String bookId;
  private String userId;

  private String eventId;
  private String eventType;
  private String aggregateId;
  private String aggregateType;
  private String dateTime;

  public BookItemAddedMother() {
    var faker = new Faker();
    this.id = faker.internet().uuidv7();
    this.bookId = faker.internet().uuidv7();
    this.userId = faker.internet().uuidv7();
    this.eventId = faker.internet().uuidv7();
    this.eventType = BookItemAdded.EVENT_TYPE;
    this.aggregateId = this.id;
    this.aggregateType = BookItemAdded.AGGREGATE_TYPE;
    this.dateTime =
        faker
            .timeAndDate()
            .past(1, TimeUnit.DAYS)
            .atOffset(ZoneOffset.UTC)
            .toLocalDateTime()
            .truncatedTo(ChronoUnit.SECONDS)
            .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
  }

  public BookItemAdded build() {
    return BookItemAdded.builder()
        .id(id)
        .bookId(bookId)
        .userId(userId)
        .eventId(eventId)
        .eventType(eventType)
        .aggregateId(aggregateId)
        .aggregateType(aggregateType)
        .dateTime(dateTime)
        .build();
  }
}
