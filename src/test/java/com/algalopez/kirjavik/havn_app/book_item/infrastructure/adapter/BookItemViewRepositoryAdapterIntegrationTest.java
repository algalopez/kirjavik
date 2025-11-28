package com.algalopez.kirjavik.havn_app.book_item.infrastructure.adapter;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.*;
import com.algalopez.kirjavik.havn_app.book_item.domain.model.BookItemProjection;
import com.algalopez.kirjavik.havn_app.book_item.domain.model.BookItemProjectionMother;
import com.algalopez.kirjavik.havn_app.book_item.domain.model.BookItemStatus;
import io.quarkus.hibernate.orm.PersistenceUnit;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.SneakyThrows;
import org.assertj.core.api.AbstractAssert;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
class BookItemViewRepositoryAdapterIntegrationTest {

  @Inject
  @PersistenceUnit("havn")
  EntityManager entityManager;

  @Inject BookItemViewRepositoryAdapter bookItemViewRepositoryAdapter;

  @Transactional
  @AfterEach
  void tearDown() {
    entityManager.createNativeQuery("TRUNCATE TABLE book_item_projection").executeUpdate();
  }

  @Test
  void createBookItemView() {
    BookItemAdded bookItemAdded = new BookItemAddedMother().build();

    bookItemViewRepositoryAdapter.createBookItemView(bookItemAdded);

    List<Tuple> actualBookItems = findAllBookItems();
    BookItemTupleAssert.assertThat(actualBookItems.getFirst())
        .isEqualToBookItem(
            BookItemProjection.builder()
                .id(UUID.fromString(bookItemAdded.getId()))
                .bookId(UUID.fromString(bookItemAdded.getBookId()))
                .userId(UUID.fromString(bookItemAdded.getUserId()))
                .status(BookItemStatus.AVAILABLE)
                .reviewCount(0)
                .reviewScore(new BigDecimal("0.00"))
                .createdAt(
                    LocalDateTime.parse(
                        bookItemAdded.getDateTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .updatedAt(
                    LocalDateTime.parse(
                        bookItemAdded.getDateTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build());
  }

  @Test
  void updateBookItemViewOnBookBorrowed() {
    BookItemProjection bookItemProjection = new BookItemProjectionMother().build();
    givenAnExistingBookItem(bookItemProjection);
    BookItemBorrowed bookItemBorrowed =
        new BookItemBorrowedMother()
            .id(bookItemProjection.id().toString())
            .bookId(bookItemProjection.bookId().toString())
            .build();

    bookItemViewRepositoryAdapter.updateBookItemViewOnBookBorrowed(bookItemBorrowed);

    List<Tuple> actualBookItems = findAllBookItems();
    BookItemTupleAssert.assertThat(actualBookItems.getFirst())
        .isEqualToBookItem(
            BookItemProjection.builder()
                .id(bookItemProjection.id())
                .bookId(bookItemProjection.bookId())
                .userId(bookItemProjection.userId())
                .status(BookItemStatus.BORROWED)
                .reviewCount(bookItemProjection.reviewCount())
                .reviewScore(bookItemProjection.reviewScore())
                .createdAt(bookItemProjection.createdAt())
                .updatedAt(
                    LocalDateTime.parse(
                        bookItemBorrowed.getDateTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build());
  }

  @Test
  void updateBookItemViewOnBookReturned() {
    BookItemProjection bookItemProjection = new BookItemProjectionMother().build();
    givenAnExistingBookItem(bookItemProjection);
    BookItemReturned bookItemReturned =
        new BookItemReturnedMother()
            .id(bookItemProjection.id().toString())
            .bookId(bookItemProjection.bookId().toString())
            .build();

    bookItemViewRepositoryAdapter.updateBookItemViewOnBookReturned(bookItemReturned);

    List<Tuple> actualBookItems = findAllBookItems();
    BookItemTupleAssert.assertThat(actualBookItems.getFirst())
        .isEqualToBookItem(
            BookItemProjection.builder()
                .id(bookItemProjection.id())
                .bookId(bookItemProjection.bookId())
                .userId(bookItemProjection.userId())
                .status(BookItemStatus.AVAILABLE)
                .reviewCount(bookItemProjection.reviewCount())
                .reviewScore(bookItemProjection.reviewScore())
                .createdAt(bookItemProjection.createdAt())
                .updatedAt(
                    LocalDateTime.parse(
                        bookItemReturned.getDateTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build());
  }

  @Test
  void updateBookItemViewOnBookRemoved() {
    BookItemProjection bookItemProjection = new BookItemProjectionMother().build();
    givenAnExistingBookItem(bookItemProjection);
    BookItemRemoved bookItemRemoved =
        new BookItemRemovedMother()
            .id(bookItemProjection.id().toString())
            .bookId(bookItemProjection.bookId().toString())
            .build();

    bookItemViewRepositoryAdapter.updateBookItemViewOnBookRemoved(bookItemRemoved);

    List<Tuple> actualBookItems = findAllBookItems();
    BookItemTupleAssert.assertThat(actualBookItems.getFirst())
        .isEqualToBookItem(
            BookItemProjection.builder()
                .id(bookItemProjection.id())
                .bookId(bookItemProjection.bookId())
                .userId(bookItemProjection.userId())
                .status(BookItemStatus.REMOVED)
                .reviewCount(bookItemProjection.reviewCount())
                .reviewScore(bookItemProjection.reviewScore())
                .createdAt(bookItemProjection.createdAt())
                .updatedAt(
                    LocalDateTime.parse(
                        bookItemRemoved.getDateTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build());
  }

  @SneakyThrows
  @Transactional
  void givenAnExistingBookItem(BookItemProjection bookItem) {
    entityManager
        .createNativeQuery(
            "INSERT INTO book_item_projection (`id`, `book_id`, `user_id`, `status`, `review_count`, `review_score`, `created_at`, `updated_at`) VALUES (:id, :bookId, :userId, :status, :reviewCount, :reviewScore, :createdAt, :updatedAt)")
        .setParameter("id", bookItem.id().toString())
        .setParameter("bookId", bookItem.bookId().toString())
        .setParameter("userId", bookItem.userId().toString())
        .setParameter("status", bookItem.status().getValue())
        .setParameter("reviewCount", bookItem.reviewCount())
        .setParameter("reviewScore", bookItem.reviewScore())
        .setParameter("createdAt", bookItem.createdAt())
        .setParameter("updatedAt", bookItem.updatedAt())
        .executeUpdate();
  }

  private List<Tuple> findAllBookItems() {
    String sqlString =
        """
        SELECT id, book_id AS bookId, user_id AS userId, status, review_count AS reviewCount, review_score AS reviewScore, created_at AS createdAt, updated_at AS updatedAt
        FROM book_item_projection""";
    @SuppressWarnings("unchecked")
    NativeQuery<Tuple> query =
        entityManager
            .createNativeQuery(sqlString, Tuple.class)
            .unwrap(NativeQuery.class)
            .addScalar("id", String.class)
            .addScalar("bookId", String.class)
            .addScalar("userId", String.class)
            .addScalar("status", Integer.class)
            .addScalar("reviewCount", Integer.class)
            .addScalar("reviewScore", BigDecimal.class)
            .addScalar("createdAt", LocalDateTime.class)
            .addScalar("updatedAt", LocalDateTime.class);
    return query.getResultList();
  }

  public static class BookItemTupleAssert extends AbstractAssert<BookItemTupleAssert, Tuple> {

    public BookItemTupleAssert(Tuple actual) {
      super(actual, BookItemTupleAssert.class);
    }

    public static BookItemTupleAssert assertThat(Tuple actual) {
      return new BookItemTupleAssert(actual);
    }

    @SneakyThrows
    public void isEqualToBookItem(BookItemProjection expected) {
      isNotNull();

      UUID id = UUID.fromString(actual.get("id", String.class));
      UUID bookId = UUID.fromString(actual.get("bookId", String.class));
      UUID userId = UUID.fromString(actual.get("userId", String.class));
      Integer status = actual.get("status", Integer.class);
      Integer reviewCount = actual.get("reviewCount", Integer.class);
      BigDecimal reviewScore = actual.get("reviewScore", BigDecimal.class);
      LocalDateTime createdAt = actual.get("createdAt", LocalDateTime.class);
      LocalDateTime updatedAt = actual.get("updatedAt", LocalDateTime.class);

      if (!Objects.equals(expected.id(), id)) {
        failWithMessage("Expected id to be <%s> but was <%s>", expected.id(), id);
      }
      if (!Objects.equals(expected.bookId(), bookId)) {
        failWithMessage("Expected bookId to be <%s> but was <%s>", expected.bookId(), bookId);
      }
      if (!Objects.equals(expected.userId(), userId)) {
        failWithMessage("Expected userId to be <%s> but was <%s>", expected.userId(), userId);
      }
      if (!Objects.equals(expected.status().getValue(), status)) {
        failWithMessage(
            "Expected status to be <%s> but was <%s>", expected.status().getValue(), status);
      }
      if (!Objects.equals(expected.reviewCount(), reviewCount)) {
        failWithMessage(
            "Expected reviewCount to be <%s> but was <%s>", expected.reviewCount(), reviewCount);
      }
      if (!Objects.equals(expected.reviewScore(), reviewScore)) {
        failWithMessage(
            "Expected reviewScore to be <%s> but was <%s>", expected.reviewScore(), reviewScore);
      }
      if (!Objects.equals(expected.createdAt(), createdAt)) {
        failWithMessage(
            "Expected createdAt to be <%s> but was <%s>", expected.createdAt(), createdAt);
      }
      if (!Objects.equals(expected.updatedAt(), updatedAt)) {
        failWithMessage(
            "Expected updatedAt to be <%s> but was <%s>", expected.updatedAt(), updatedAt);
      }
    }
  }
}
