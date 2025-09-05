package com.algalopez.kirjavik.havn_app.book_review.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReviewCriteria;
import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReviewView;
import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReviewViewMother;
import io.quarkus.hibernate.orm.PersistenceUnit;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
class BookReviewViewRepositoryAdapterIntegrationTest {

  @Inject
  @PersistenceUnit("havn")
  EntityManager entityManager;

  @Inject BookReviewViewRepositoryAdapter bookReviewViewRepository;

  @Transactional
  @AfterEach
  void cleanUp() {
    entityManager.createNativeQuery("TRUNCATE TABLE book_review").executeUpdate();
  }

  @Test
  void findBookReviewByBookIdAndUserId() {
    BookReviewView bookReviewView = new BookReviewViewMother().build();
    givenAnExistingBookReview(bookReviewView);
    givenAnExistingBookProjection(bookReviewView);

    BookReviewView actualBookReview =
        bookReviewViewRepository.findBookReviewByBookIdAndUserId(
            bookReviewView.bookId(), bookReviewView.userId());

    assertThat(actualBookReview).isEqualTo(bookReviewView);
  }

  @Test
  void findAllReviewsByFilters() {
    BookReviewView bookReviewView = new BookReviewViewMother().build();
    givenAnExistingBookReview(bookReviewView);
    givenAnExistingBookProjection(bookReviewView);
    BookReviewCriteria criteria =
        BookReviewCriteria.builder()
            .bookId(bookReviewView.bookId())
            .userId(bookReviewView.userId())
            .build();

    List<BookReviewView> actualBookReview =
        bookReviewViewRepository.findAllReviewsByFilters(criteria);

    assertThat(actualBookReview).isEqualTo(List.of(bookReviewView));
  }

  @SneakyThrows
  @Transactional
  void givenAnExistingBookReview(BookReviewView bookReview) {
    entityManager
        .createNativeQuery(
            "INSERT INTO book_review (`id`, `book_id`, `user_id`, `rating`, `comment`) VALUES (:id, :bookId, :userId, :rating, :comment)")
        .setParameter("id", bookReview.id())
        .setParameter("bookId", bookReview.bookId().toString())
        .setParameter("userId", bookReview.userId().toString())
        .setParameter("rating", bookReview.rating())
        .setParameter("comment", bookReview.comment())
        .executeUpdate();
  }

  @SneakyThrows
  @Transactional
  void givenAnExistingBookProjection(BookReviewView bookReview) {
    entityManager
        .createNativeQuery(
            "INSERT INTO book_projection (`id`, `isbn`, `title`, `author`, `page_count`) VALUES (:id, :isbn, :title, :author, :pageCount)")
        .setParameter("id", bookReview.bookId().toString())
        .setParameter("isbn", "978-3-16-148410-0")
        .setParameter("title", bookReview.bookTitle())
        .setParameter("author", "author")
        .setParameter("pageCount", 100)
        .executeUpdate();
  }
}
