package com.algalopez.kirjavik.havn_app.book_review.domain.model;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.datafaker.Faker;

@Accessors(fluent = true)
@Setter
public class BookReviewViewMother {
  private Long id;
  private UUID bookId;
  private String bookTitle;
  private UUID userId;
  private BigDecimal rating;
  private String comment;

  public BookReviewViewMother() {
    var faker = new Faker();
    this.id = faker.number().numberBetween(1L, 10_000_000L);
    this.bookId = UUID.fromString(faker.internet().uuidv7());
    this.bookTitle = faker.book().title();
    this.userId = UUID.fromString(faker.internet().uuidv7());
    this.rating = BigDecimal.valueOf(faker.number().numberBetween(0, 100) / 10.0);
    this.comment = faker.lorem().sentence();
  }

  public BookReviewView build() {
    return BookReviewView.builder()
        .id(id)
        .bookId(bookId)
        .bookTitle(bookTitle)
        .userId(userId)
        .rating(rating)
        .comment(comment)
        .build();
  }
}
