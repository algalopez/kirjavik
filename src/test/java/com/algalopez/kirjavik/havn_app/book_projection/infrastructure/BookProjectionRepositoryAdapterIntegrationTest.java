package com.algalopez.kirjavik.havn_app.book_projection.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.havn_app.book_projection.domain.model.BookProjection;
import com.algalopez.kirjavik.havn_app.book_projection.domain.model.BookProjectionMother;
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
class BookProjectionRepositoryAdapterIntegrationTest {

  @Inject
  @PersistenceUnit("havn")
  EntityManager entityManager;

  @Inject BookProjectionRepositoryAdapter bookProjectionRepository;

  @Transactional
  @AfterEach
  void cleanUp() {
    entityManager.createNativeQuery("TRUNCATE TABLE book_projection").executeUpdate();
  }

  @Test
  void findBookProjectionById() {
    BookProjection expectedBook = new BookProjectionMother().build();
    givenAnExistingBookProjection(expectedBook);

    BookProjection actualBook =
        bookProjectionRepository.findBookProjectionById(expectedBook.getId());

    assertThat(actualBook).isEqualTo(expectedBook);
  }

  @Test
  void findBookProjectionById_whenBookDoesNotExist() {
    UUID id = UUID.randomUUID();

    BookProjection actualBook = bookProjectionRepository.findBookProjectionById(id);

    assertThat(actualBook).isNull();
  }

  @Test
  void createBookProjection() {
    BookProjection expectedBook = new BookProjectionMother().build();

    bookProjectionRepository.createBookProjection(expectedBook);

    List<Tuple> actualBooks = findAllBookProjections();

    assertThat(actualBooks).hasSize(1);
    BookProjectionTupleAssert.assertThat(actualBooks.getFirst()).isEqualToBook(expectedBook);
  }

  @Test
  void updateBookProjection() {
    BookProjection originalBook = new BookProjectionMother().build();
    BookProjection expectedBook = new BookProjectionMother().id(originalBook.getId()).build();
    givenAnExistingBookProjection(originalBook);

    bookProjectionRepository.updateBookProjection(expectedBook);

    List<Tuple> actualBooks = findAllBookProjections();
    assertThat(actualBooks).hasSize(1);
    BookProjectionTupleAssert.assertThat(actualBooks.getFirst()).isEqualToBook(expectedBook);
  }

  @Test
  void deleteBookProjection() {
    BookProjection originalBook = new BookProjectionMother().build();
    givenAnExistingBookProjection(originalBook);

    bookProjectionRepository.deleteBookProjection(originalBook.getId());

    List<Tuple> actualBooks = findAllBookProjections();
    assertThat(actualBooks).isEmpty();
  }

  @SneakyThrows
  @Transactional
  void givenAnExistingBookProjection(BookProjection book) {
    entityManager
        .createNativeQuery(
            "INSERT INTO book_projection (`id`, `isbn`, `title`, `author`, `page_count`, `year`) VALUES (:id, :isbn, :title, :author, :pageCount, :year)")
        .setParameter("id", book.getId().toString())
        .setParameter("isbn", book.getIsbn())
        .setParameter("title", book.getTitle())
        .setParameter("author", book.getAuthor())
        .setParameter("pageCount", book.getPageCount())
        .setParameter("year", book.getYear())
        .executeUpdate();
  }

  private List<Tuple> findAllBookProjections() {
    String sqlString =
        "SELECT id, isbn, title, author, page_count AS pageCount, year FROM book_projection";
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

  public static class BookProjectionTupleAssert
      extends AbstractAssert<BookProjectionTupleAssert, Tuple> {

    public BookProjectionTupleAssert(Tuple actual) {
      super(actual, BookProjectionTupleAssert.class);
    }

    public static BookProjectionTupleAssert assertThat(Tuple actual) {
      return new BookProjectionTupleAssert(actual);
    }

    @SneakyThrows
    public void isEqualToBook(BookProjection expected) {
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
