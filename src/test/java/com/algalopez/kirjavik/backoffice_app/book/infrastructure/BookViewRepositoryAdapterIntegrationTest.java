package com.algalopez.kirjavik.backoffice_app.book.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookView;
import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookViewMother;
import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookViewSpec;
import io.quarkus.hibernate.orm.PersistenceUnit;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
class BookViewRepositoryAdapterIntegrationTest {

  @Inject
  @PersistenceUnit("backoffice")
  EntityManager entityManager;

  @Inject BookViewRepositoryAdapter bookViewRepositoryAdapter;

  @Transactional
  @AfterEach
  void cleanUp() {
    entityManager.createNativeQuery("TRUNCATE TABLE book").executeUpdate();
  }

  @Test
  void findAllByFilter() {
    BookView bookView = new BookViewMother().build();
    givenAnExistingBook(bookView);
    BookViewSpec.Filter filter =
        BookViewSpec.Filter.builder()
            .field("title")
            .operator(BookViewSpec.Operator.EQUALS)
            .value(bookView.title())
            .build();
    BookViewSpec bookViewSpec = BookViewSpec.builder().filters(List.of(filter)).build();

    List<BookView> actualBooks = bookViewRepositoryAdapter.findAllByFilter(bookViewSpec);

    assertThat(actualBooks).containsExactlyInAnyOrder(bookView);
  }

  @Test
  void findAllByFilter_whenMultipleFilters() {
    BookView bookView = new BookViewMother().build();
    givenAnExistingBook(bookView);
    BookViewSpec.Filter filter1 =
        BookViewSpec.Filter.builder()
            .field("title")
            .operator(BookViewSpec.Operator.EQUALS)
            .value(bookView.title())
            .build();
    BookViewSpec.Filter filter2 =
        BookViewSpec.Filter.builder()
            .field("year")
            .operator(BookViewSpec.Operator.GREATER_THAN)
            .value(String.valueOf(bookView.year() - 1))
            .build();
    BookViewSpec bookViewSpec = BookViewSpec.builder().filters(List.of(filter1, filter2)).build();

    List<BookView> actualBooks = bookViewRepositoryAdapter.findAllByFilter(bookViewSpec);

    assertThat(actualBooks).containsExactlyInAnyOrder(bookView);
  }

  @Test
  void findAllByFilter_whenNoMatches() {
    BookView bookView = new BookViewMother().build();
    givenAnExistingBook(bookView);
    BookViewSpec.Filter filter1 =
        BookViewSpec.Filter.builder()
            .field("title")
            .operator(BookViewSpec.Operator.STARTS_WITH)
            .value("aa" + bookView.title())
            .build();
    BookViewSpec bookViewSpec = BookViewSpec.builder().filters(List.of(filter1)).build();

    List<BookView> actualBooks = bookViewRepositoryAdapter.findAllByFilter(bookViewSpec);

    assertThat(actualBooks).isEmpty();
  }

  @Test
  void findById() {
    BookView bookView = new BookViewMother().build();
    givenAnExistingBook(bookView);

    BookView actualBook = bookViewRepositoryAdapter.findById(bookView.id());

    assertThat(actualBook).isEqualTo(bookView);
  }

  @Test
  void findById_whenNoBook() {
    UUID id = UUID.randomUUID();

    BookView actualBook = bookViewRepositoryAdapter.findById(id);

    assertThat(actualBook).isNull();
  }

  @Transactional
  void givenAnExistingBook(BookView book) {
    entityManager
        .createNativeQuery(
            "INSERT INTO book (`id`, `isbn`, `title`, `author`, `page_count`, `year`) VALUES (:id, :isbn, :title, :author, :pageCount, :year)")
        .setParameter("id", book.id().toString())
        .setParameter("isbn", book.isbn())
        .setParameter("title", book.title())
        .setParameter("author", book.author())
        .setParameter("pageCount", book.pageCount())
        .setParameter("year", book.year())
        .executeUpdate();
  }
}
