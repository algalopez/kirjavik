package com.algalopez.kirjavik.backoffice_app.book.domain.model;

import java.util.List;
import java.util.UUID;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.datafaker.Faker;
import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookView;

@Accessors(fluent = true)
@Setter
public class BookViewMother {
  private UUID id;
  private String isbn;
  private String title;
  private List<String> authors;
  private Integer pageCount;
  private Integer year;

  public BookViewMother() {
    var faker = new Faker();
    id = UUID.fromString(faker.internet().uuidv7());
    isbn = faker.code().isbn13();
    title = faker.book().title();
    authors = List.of(faker.book().author());
    pageCount = faker.number().numberBetween(1, 1000);
    year = faker.number().numberBetween(-5000, 5000);
  }

  public BookView build() {
    return BookView.builder()
        .id(id)
        .isbn(isbn)
        .title(title)
        .authors(authors)
        .pageCount(pageCount)
        .year(year)
        .build();
  }
}
