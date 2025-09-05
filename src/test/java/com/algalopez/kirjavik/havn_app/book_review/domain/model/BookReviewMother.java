package com.algalopez.kirjavik.havn_app.book_review.domain.model;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.datafaker.Faker;

@Accessors(fluent = true)
@Setter
public class BookReviewMother {
  private Long id;
  private UUID bookId;
  private UUID userId;
  private BigDecimal rating;
  private String comment;

  public BookReviewMother() {
    var faker = new Faker();
    this.id = faker.number().numberBetween(1L, 10_000_000L);
    this.bookId = UUID.fromString(faker.internet().uuidv7());
    this.userId = UUID.fromString(faker.internet().uuidv7());
    this.rating = BigDecimal.valueOf(faker.number().numberBetween(0, 100) / 10.0);
    this.comment = faker.lorem().sentence();
  }

  public BookReview build() {
    return BookReview.builder()
        .id(id)
        .bookId(bookId)
        .userId(userId)
        .rating(rating)
        .comment(comment)
        .build();
  }
}
