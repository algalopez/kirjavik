package com.algalopez.kirjavik.backoffice_app.book.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.backoffice_app.book.domain.model.Book;
import com.algalopez.kirjavik.backoffice_app.book.domain.model.BookMother;
import io.quarkus.hibernate.orm.PersistenceUnit;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.SneakyThrows;
import org.assertj.core.api.AbstractAssert;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
class BookRepositoryAdapterIntegrationTest {

  @Inject
  @PersistenceUnit("backoffice")
  EntityManager entityManager;

  @Inject BookRepositoryAdapter bookRepositoryAdapter;

  @Transactional
  @AfterEach
  void cleanUp() {
    entityManager.createNativeQuery("TRUNCATE TABLE book").executeUpdate();
  }

  @Test
  void findBookById() {
    Book expectedBook = new BookMother().build();
    givenAnExistingBook(expectedBook);

    Book actualBook = bookRepositoryAdapter.findBookById(expectedBook.getId());

    assertThat(actualBook).isEqualTo(expectedBook);
  }

  @Test
  void findBookById_whenBookDoesNotExist() {
    UUID id = UUID.randomUUID();

    Book actualBook = bookRepositoryAdapter.findBookById(id);

    assertThat(actualBook).isNull();
  }

  @Test
  void createBook() {
    Book expectedBook = new BookMother().build();

    bookRepositoryAdapter.createBook(expectedBook);

    List<Tuple> actualBooks = findAllBooks();

    assertThat(actualBooks).hasSize(1);
    BookTupleAssert.assertThat(actualBooks.getFirst()).isEqualToBook(expectedBook);
  }

  @Test
  void updateBook() {
    Book originalBook = new BookMother().build();
    Book expectedBook = new BookMother().id(originalBook.getId()).build();
    givenAnExistingBook(originalBook);

    bookRepositoryAdapter.updateBook(expectedBook);

    List<Tuple> actualBooks = findAllBooks();
    assertThat(actualBooks).hasSize(1);
    BookTupleAssert.assertThat(actualBooks.getFirst()).isEqualToBook(expectedBook);
  }

  @Test
  void deleteBook() {
    Book originalBook = new BookMother().build();
    givenAnExistingBook(originalBook);

    bookRepositoryAdapter.deleteBook(originalBook.getId());

    List<Tuple> actualBooks = findAllBooks();
    assertThat(actualBooks).isEmpty();
  }

  @SneakyThrows
  @Transactional
  void givenAnExistingBook(Book book) {
    entityManager
        .createNativeQuery(
            "INSERT INTO book (`id`, `isbn`, `title`, `author`, `page_count`, `year`) VALUES (:id, :isbn, :title, :author, :pageCount, :year)")
        .setParameter("id", book.getId().toString())
        .setParameter("isbn", book.getIsbn())
        .setParameter("title", book.getTitle())
        .setParameter("author", book.getAuthor())
        .setParameter("pageCount", book.getPageCount())
        .setParameter("year", book.getYear())
        .executeUpdate();
  }

  private List<Tuple> findAllBooks() {
    String sqlString = "SELECT id, isbn, title, author, page_count AS pageCount, year FROM book";
    @SuppressWarnings("unchecked")
    NativeQuery<Tuple> query =
        entityManager
            .createNativeQuery(sqlString, Tuple.class)
            .unwrap(NativeQuery.class)
            .addScalar("id", String.class)
            .addScalar("isbn", String.class)
            .addScalar("title", String.class)
            .addScalar("author", String.class)
            .addScalar("pageCount", Integer.class)
            .addScalar("year", Integer.class);
    return query.getResultList();
  }

  public static class BookTupleAssert extends AbstractAssert<BookTupleAssert, Tuple> {

    public BookTupleAssert(Tuple actual) {
      super(actual, BookTupleAssert.class);
    }

    public static BookTupleAssert assertThat(Tuple actual) {
      return new BookTupleAssert(actual);
    }

    @SneakyThrows
    public void isEqualToBook(Book expected) {
      isNotNull();

      UUID id = UUID.fromString(actual.get("id", String.class));
      String isbn = actual.get("isbn", String.class);
      String title = actual.get("title", String.class);
      String author = actual.get("author", String.class);
      Integer pageCount = actual.get("pageCount", Integer.class);
      Integer year = actual.get("year", Integer.class);

      if (!Objects.equals(expected.getId(), id)) {
        failWithMessage("Expected id to be <%s> but was <%s>", expected.getId(), id);
      }
      if (!Objects.equals(expected.getIsbn(), isbn)) {
        failWithMessage("Expected isbn to be <%s> but was <%s>", expected.getIsbn(), isbn);
      }
      if (!Objects.equals(expected.getTitle(), title)) {
        failWithMessage("Expected title to be <%s> but was <%s>", expected.getTitle(), title);
      }
      if (!Objects.equals(expected.getAuthor(), author)) {
        failWithMessage("Expected authors to be <%s> but was <%s>", expected.getAuthor(), author);
      }
      if (!Objects.equals(expected.getPageCount(), pageCount)) {
        failWithMessage(
            "Expected pageCount to be <%s> but was <%s>", expected.getPageCount(), pageCount);
      }
      if (!Objects.equals(expected.getYear(), year)) {
        failWithMessage("Expected year to be <%s> but was <%s>", expected.getYear(), year);
      }
    }
  }
}
