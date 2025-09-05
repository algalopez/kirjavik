package com.algalopez.kirjavik.havn_app.book_review.infrastructure;

import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReviewCriteria;
import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReviewView;
import com.algalopez.kirjavik.havn_app.book_review.domain.port.BookReviewViewRepositoryPort;
import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.hibernate.query.NativeQuery;

@ApplicationScoped
public class BookReviewViewRepositoryAdapter implements BookReviewViewRepositoryPort {
  private static final String ID = "id";
  private static final String BOOK_ID = "bookId";
  private static final String BOOK_TITLE = "bookTitle";
  private static final String USER_ID = "userId";
  private static final String RATING = "rating";
  private static final String COMMENT = "comment";

  private final EntityManager entityManager;

  public BookReviewViewRepositoryAdapter(@PersistenceUnit("havn") EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public BookReviewView findBookReviewByBookIdAndUserId(UUID bookId, UUID userId) {
    String sqlString =
        """
        SELECT br.id AS id,
               br.book_id AS bookId,
               b.title AS bookTitle,
               br.user_id AS userId,
               br.rating AS rating,
               br.comment AS comment
        FROM book_review br
        JOIN book_projection b ON br.book_id = b.id
        WHERE br.book_id = :bookId AND br.user_id = :userId
        """;
    @SuppressWarnings("unchecked")
    NativeQuery<Tuple> query =
        entityManager
            .createNativeQuery(sqlString, Tuple.class)
            .setParameter(BOOK_ID, bookId.toString())
            .setParameter(USER_ID, userId.toString())
            .unwrap(org.hibernate.query.NativeQuery.class)
            .addScalar(ID, Long.class)
            .addScalar(BOOK_ID, String.class)
            .addScalar(BOOK_TITLE, String.class)
            .addScalar(USER_ID, String.class)
            .addScalar(RATING, BigDecimal.class)
            .addScalar(COMMENT, String.class);

    Tuple tuple = query.uniqueResult();
    return tuple == null ? null : mapToView(tuple);
  }

  @Override
  public List<BookReviewView> findAllReviewsByFilters(BookReviewCriteria bookReviewCriteria) {
    String sqlString =
        """
        SELECT br.id AS id,
               br.book_id AS bookId,
               b.title AS bookTitle,
               br.user_id AS userId,
               br.rating AS rating,
               br.comment AS comment
        FROM book_review br
        JOIN book_projection b ON br.book_id = b.id
        WHERE (br.book_id = :bookId OR :bookId IS NULL) AND (br.user_id = :userId OR :userId IS NULL)
        """;
    @SuppressWarnings("unchecked")
    NativeQuery<Tuple> query =
        entityManager
            .createNativeQuery(sqlString, Tuple.class)
            .setParameter(BOOK_ID, bookReviewCriteria.bookId().toString())
            .setParameter(USER_ID, bookReviewCriteria.userId().toString())
            .unwrap(org.hibernate.query.NativeQuery.class)
            .addScalar(ID, Long.class)
            .addScalar(BOOK_ID, String.class)
            .addScalar(BOOK_TITLE, String.class)
            .addScalar(USER_ID, String.class)
            .addScalar(RATING, BigDecimal.class)
            .addScalar(COMMENT, String.class);

    List<Tuple> tuples = query.getResultList();
    return tuples.stream().map(BookReviewViewRepositoryAdapter::mapToView).toList();
  }

  private static BookReviewView mapToView(Tuple tuple) {
    Long id = tuple.get(ID, Long.class);
    UUID bookId = UUID.fromString(tuple.get(BOOK_ID, String.class));
    String bookTitle = tuple.get(BOOK_TITLE, String.class);
    UUID userId = UUID.fromString(tuple.get(USER_ID, String.class));
    BigDecimal rating = tuple.get(RATING, BigDecimal.class);
    String comment = tuple.get(COMMENT, String.class);
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
