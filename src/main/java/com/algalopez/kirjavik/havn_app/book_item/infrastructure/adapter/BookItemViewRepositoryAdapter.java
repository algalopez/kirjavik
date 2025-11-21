package com.algalopez.kirjavik.havn_app.book_item.infrastructure.adapter;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemAdded;
import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemBorrowed;
import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemRemoved;
import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemReturned;
import com.algalopez.kirjavik.havn_app.book_item.domain.model.BookItemStatus;
import com.algalopez.kirjavik.havn_app.book_item.domain.port.BookItemViewRepositoryPort;
import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BookItemViewRepositoryAdapter implements BookItemViewRepositoryPort {

  private static final String ID = "id";
  private static final String BOOK_ID = "bookId";
  private static final String USER_ID = "userId";
  private static final String STATUS = "status";
  private static final String REVIEW_COUNT = "reviewCount";
  private static final String REVIEW_SCORE = "reviewScore";
  private static final String CREATED_AT = "createdAt";
  private static final String UPDATED_AT = "updatedAt";

  private final EntityManager entityManager;

  public BookItemViewRepositoryAdapter(@PersistenceUnit("havn") EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public void createBookItemView(BookItemAdded bookItemAdded) {
    String sqlString =
        "INSERT INTO book (`id`, `book_id`, `user_id`, `status`, `review_count`, `review_score`, `created_at`, `updated_at`) VALUES (:id, :bookId, :userId, :status, :reviewCount, :reviewScore, :createdAt, :updatedAt)";
    entityManager
        .createNativeQuery(sqlString)
        .setParameter(ID, bookItemAdded.getId())
        .setParameter(BOOK_ID, bookItemAdded.getBookId())
        .setParameter(USER_ID, bookItemAdded.getUserId())
        .setParameter(STATUS, BookItemStatus.AVAILABLE.getValue())
        .setParameter(REVIEW_COUNT, 0)
        .setParameter(REVIEW_SCORE, 0)
        .setParameter(CREATED_AT, bookItemAdded.getDateTime())
        .setParameter(UPDATED_AT, bookItemAdded.getDateTime())
        .executeUpdate();
  }

  @Transactional
  @Override
  public void updateBookItemViewOnBookBorrowed(BookItemBorrowed bookItemBorrowed) {
    String sqlString =
        "UPDATE book SET `status` = :status, `updated_at` = :updateAt WHERE `id` = :id";

    entityManager
        .createNativeQuery(sqlString)
        .setParameter(STATUS, BookItemStatus.BORROWED.getValue())
        .setParameter(UPDATED_AT, bookItemBorrowed.getDateTime())
        .setParameter(ID, bookItemBorrowed.getId())
        .executeUpdate();
  }

  @Transactional
  @Override
  public void updateBookItemViewOnBookReturned(BookItemReturned bookItemReturned) {
    String sqlString =
        "UPDATE book SET `status` = :status, `updated_at` = :updateAt WHERE `id` = :id";

    entityManager
        .createNativeQuery(sqlString)
        .setParameter(STATUS, BookItemStatus.AVAILABLE.getValue())
        .setParameter(UPDATED_AT, bookItemReturned.getDateTime())
        .setParameter(ID, bookItemReturned.getId())
        .executeUpdate();
  }

  @Transactional
  @Override
  public void updateBookItemViewOnBookRemoved(BookItemRemoved bookItemRemoved) {
    String sqlString =
        "UPDATE book SET `status` = :status, `updated_at` = :updateAt WHERE `id` = :id";

    entityManager
        .createNativeQuery(sqlString)
        .setParameter(STATUS, BookItemStatus.REMOVED.getValue())
        .setParameter(UPDATED_AT, bookItemRemoved.getDateTime())
        .setParameter(ID, bookItemRemoved.getId())
        .executeUpdate();
  }
}
