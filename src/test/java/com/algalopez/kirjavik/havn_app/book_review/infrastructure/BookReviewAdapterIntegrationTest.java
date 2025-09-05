package com.algalopez.kirjavik.havn_app.book_review.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReview;
import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReviewMother;
import io.quarkus.hibernate.orm.PersistenceUnit;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.SneakyThrows;
import org.assertj.core.api.AbstractAssert;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
class BookReviewAdapterIntegrationTest {

  @Inject
  @PersistenceUnit("havn")
  EntityManager entityManager;

  @Inject BookReviewRepositoryAdapter bookReviewRepositoryAdapter;

  @Transactional
  @AfterEach
  void cleanUp() {
    entityManager.createNativeQuery("TRUNCATE TABLE book_review").executeUpdate();
  }

  @Test
  void findBookReviewByBookIdAndUserId() {
    BookReview expectedBookReview = new BookReviewMother().build();
    givenAnExistingBookReview(expectedBookReview);

    BookReview actualBookReview =
        bookReviewRepositoryAdapter.findBookReviewByBookIdAndUserId(
            expectedBookReview.getBookId(), expectedBookReview.getUserId());

    assertThat(actualBookReview).isEqualTo(expectedBookReview);
  }

  @Test
  void createBookReview() {
    BookReview expectedBookReview = new BookReviewMother().id(null).build();

    bookReviewRepositoryAdapter.createBookReview(expectedBookReview);

    List<Tuple> actualBookReviews = findAllBookReviews();
    assertThat(actualBookReviews).hasSize(1);
    BookReviewTupleAssert.assertThat(actualBookReviews.getFirst())
        .isEqualToBookReview(expectedBookReview);
  }

  @Test
  void updateBookReview() {
    BookReview originalBookReview = new BookReviewMother().build();
    BookReview expectedBookReview = new BookReviewMother().id(originalBookReview.getId()).build();
    givenAnExistingBookReview(originalBookReview);

    bookReviewRepositoryAdapter.updateBookReview(expectedBookReview);

    List<Tuple> actualBookReviews = findAllBookReviews();
    assertThat(actualBookReviews).hasSize(1);
    BookReviewTupleAssert.assertThat(actualBookReviews.getFirst());
  }

  @Test
  void deleteBookReview() {
    BookReview originalBookReview = new BookReviewMother().build();
    givenAnExistingBookReview(originalBookReview);

    bookReviewRepositoryAdapter.deleteBookReview(originalBookReview.getId());

    List<Tuple> actualBookReviews = findAllBookReviews();
    assertThat(actualBookReviews).isEmpty();
  }

  @SneakyThrows
  @Transactional
  void givenAnExistingBookReview(BookReview bookReview) {
    entityManager
        .createNativeQuery(
            "INSERT INTO book_review (`id`, `book_id`, `user_id`, `rating`, `comment`) VALUES (:id, :bookId, :userId, :rating, :comment)")
        .setParameter("id", bookReview.getId())
        .setParameter("bookId", bookReview.getBookId().toString())
        .setParameter("userId", bookReview.getUserId().toString())
        .setParameter("rating", bookReview.getRating())
        .setParameter("comment", bookReview.getComment())
        .executeUpdate();
  }

  private List<Tuple> findAllBookReviews() {
    String sqlString = "SELECT id, book_id, user_id, rating, comment FROM book_review";
    @SuppressWarnings("unchecked")
    NativeQuery<Tuple> query =
        entityManager
            .createNativeQuery(sqlString, Tuple.class)
            .unwrap(NativeQuery.class)
            .addScalar("id", Long.class)
            .addScalar("book_id", String.class)
            .addScalar("user_id", String.class)
            .addScalar("rating", BigDecimal.class)
            .addScalar("comment", String.class);
    return query.getResultList();
  }

  public static class BookReviewTupleAssert extends AbstractAssert<BookReviewTupleAssert, Tuple> {

    public BookReviewTupleAssert(Tuple actual) {
      super(actual, BookReviewTupleAssert.class);
    }

    public static BookReviewTupleAssert assertThat(Tuple actual) {
      return new BookReviewTupleAssert(actual);
    }

    @SneakyThrows
    public void isEqualToBookReview(BookReview expected) {
      isNotNull();

      Long id = actual.get("id", Long.class);
      UUID bookId = UUID.fromString(actual.get("book_id", String.class));
      UUID userId = UUID.fromString(actual.get("user_id", String.class));
      BigDecimal rating = actual.get("rating", BigDecimal.class);
      String comment = actual.get("comment", String.class);

      if (!Objects.equals(expected.getId(), id)) {
        failWithMessage("Expected id to be <%s> but was <%s>", expected.getId(), id);
      }
      if (!Objects.equals(expected.getBookId(), bookId)) {
        failWithMessage("Expected bookId to be <%s> but was <%s>", expected.getBookId(), bookId);
      }
      if (!Objects.equals(expected.getUserId(), userId)) {
        failWithMessage("Expected userId to be <%s> but was <%s>", expected.getUserId(), userId);
      }
      if (!Objects.equals(expected.getRating(), rating)) {
        failWithMessage("Expected rating to be <%s> but was <%s>", expected.getRating(), rating);
      }
      if (!Objects.equals(expected.getComment(), comment)) {
        failWithMessage(
            "Expected comment to be <%s> but was <%s>", expected.getComment(), comment);
      }
    }
  }
}
