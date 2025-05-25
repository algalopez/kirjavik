package com.algalopez.kirjavik.backoffice_app.book.domain.model;

import java.util.List;
import java.util.UUID;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.datafaker.Faker;

@Accessors(fluent = true)
@Setter
public class BookMother {
  private UUID id;
  private String isbn;
  private String title;
  private List<String> authors;
  private Integer pageCount;
  private Integer year;

  public BookMother() {
    var faker = new Faker();
    id = UUID.fromString(faker.internet().uuidv7());
    isbn = faker.code().isbn13();
    title = faker.book().title();
    authors = List.of(faker.book().author());
    pageCount = faker.number().numberBetween(1, 1000);
    year = faker.number().numberBetween(-5000, 5000);
  }

  public Book build() {
    return Book.builder()
        .id(id)
        .isbn(isbn)
        .title(title)
        .authors(authors)
        .pageCount(pageCount)
        .year(year)
        .build();
  }
}
