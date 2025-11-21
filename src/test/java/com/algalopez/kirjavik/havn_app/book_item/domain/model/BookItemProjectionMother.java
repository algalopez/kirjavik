package com.algalopez.kirjavik.havn_app.book_item.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.datafaker.Faker;

@Accessors(fluent = true)
@Setter
public class BookItemProjectionMother {
  private UUID id;
  private UUID bookId;
  private UUID userId;
  private BookItemStatus status;
  private Integer reviewCount;
  private BigDecimal reviewScore;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public BookItemProjectionMother() {
    var faker = new Faker();
    id = UUID.fromString(faker.internet().uuidv7());
    bookId = UUID.fromString(faker.internet().uuidv7());
    userId = UUID.fromString(faker.internet().uuidv7());
    status = faker.options().option(BookItemStatus.class);
    reviewCount = faker.number().numberBetween(0, 100);
    reviewScore = BigDecimal.valueOf(faker.number().randomDouble(1, 0, 10));
    createdAt =
        faker.timeAndDate().past(10L, TimeUnit.DAYS).atOffset(ZoneOffset.UTC).toLocalDateTime();
    updatedAt =
        faker.timeAndDate().past(10L, TimeUnit.DAYS).atOffset(ZoneOffset.UTC).toLocalDateTime();
  }

  public BookItemProjection build() {
    return BookItemProjection.builder()
        .id(id)
        .bookId(bookId)
        .userId(userId)
        .status(status)
        .reviewCount(reviewCount)
        .reviewScore(reviewScore)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();
  }
}
