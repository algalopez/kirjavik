package com.algalopez.kirjavik.havn_app.book_review.infrastructure;

import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReview;
import com.algalopez.kirjavik.havn_app.book_review.domain.port.BookReviewRepositoryPort;
import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.UUID;
import org.hibernate.query.NativeQuery;

@ApplicationScoped
public class BookReviewRepositoryAdapter implements BookReviewRepositoryPort {

  private static final String ID = "id";
  private static final String BOOK_ID = "bookId";
  private static final String USER_ID = "userId";
  private static final String RATING = "rating";
  private static final String COMMENT = "comment";

  private final EntityManager entityManager;

  public BookReviewRepositoryAdapter(@PersistenceUnit("havn") EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public BookReview findBookReviewByBookIdAndUserId(UUID bookId, UUID userId) {
    String sqlString =
        """
        SELECT id, book_id AS bookId, user_id AS userId, rating, comment
        FROM book_review
        WHERE book_id = :bookId AND user_id = :userId""";

    @SuppressWarnings("unchecked")
    NativeQuery<Tuple> query =
        entityManager
            .createNativeQuery(sqlString, Tuple.class)
            .setParameter(BOOK_ID, bookId.toString())
            .setParameter(USER_ID, userId.toString())
            .unwrap(NativeQuery.class)
            .addScalar(ID, Long.class)
            .addScalar(BOOK_ID, String.class)
            .addScalar(USER_ID, String.class)
            .addScalar(RATING, BigDecimal.class)
            .addScalar(COMMENT, String.class);

    Tuple tuple = query.uniqueResult();
    return tuple == null ? null : mapToBookReview(tuple);
  }

  @Transactional
  @Override
  public void createBookReview(BookReview bookReview) {
    String sqlString =
        """
        INSERT INTO book_review (book_id, user_id, rating, comment)
        VALUES (:bookId, :userId, :rating, :comment)
        RETURNING id""";
    @SuppressWarnings("unchecked")
    NativeQuery<Long> query =
        entityManager
            .createNativeQuery(sqlString, Long.class)
            .setParameter(BOOK_ID, bookReview.getBookId().toString())
            .setParameter(USER_ID, bookReview.getUserId().toString())
            .setParameter(RATING, bookReview.getRating())
            .setParameter(COMMENT, bookReview.getComment())
            .unwrap(NativeQuery.class)
            .addScalar(ID, Long.class);

    Long insertedId = query.getSingleResult();
    bookReview.setId(insertedId);
  }

  @Transactional
  @Override
  public void updateBookReview(BookReview bookReview) {
    String sqlString =
        """
        UPDATE book_review
        SET rating = :rating, comment = :comment
        WHERE id = :id""";
    entityManager
        .createNativeQuery(sqlString)
        .setParameter(RATING, bookReview.getRating())
        .setParameter(COMMENT, bookReview.getComment())
        .setParameter(ID, bookReview.getId())
        .executeUpdate();
  }

  @Transactional
  @Override
  public void deleteBookReview(Long id) {
    String sqlString = "DELETE FROM book_review WHERE id = :id";
    entityManager.createNativeQuery(sqlString).setParameter(ID, id).executeUpdate();
  }

  private static BookReview mapToBookReview(Tuple tuple) {
    Long id = tuple.get(ID, Long.class);
    UUID bookId = UUID.fromString(tuple.get(BOOK_ID, String.class));
    UUID userId = UUID.fromString(tuple.get(USER_ID, String.class));
    BigDecimal rating = tuple.get(RATING, BigDecimal.class);
    String comment = tuple.get(COMMENT, String.class);
    return BookReview.builder()
        .id(id)
        .bookId(bookId)
        .userId(userId)
        .rating(rating)
        .comment(comment)
        .build();
  }
}
