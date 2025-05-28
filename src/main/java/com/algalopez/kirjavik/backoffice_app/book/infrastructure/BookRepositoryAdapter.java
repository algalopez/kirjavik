package com.algalopez.kirjavik.backoffice_app.book.infrastructure;

import com.algalopez.kirjavik.backoffice_app.book.domain.model.Book;
import com.algalopez.kirjavik.backoffice_app.book.domain.port.BookRepositoryPort;
import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.NativeQuery;

@Slf4j
@ApplicationScoped
public class BookRepositoryAdapter implements BookRepositoryPort {

  private static final String ID = "id";
  private static final String ISBN = "isbn";
  private static final String TITLE = "title";
  private static final String AUTHOR = "author";
  private static final String PAGE_COUNT = "pageCount";
  private static final String YEAR = "year";

  private final EntityManager entityManager;

  public BookRepositoryAdapter(@PersistenceUnit("backoffice") EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Book findBookById(UUID id) {
    String sqlString =
        "SELECT id, isbn, title, author, page_count AS pageCount, year FROM book WHERE id = :id";
    @SuppressWarnings("unchecked")
    NativeQuery<Tuple> query =
        entityManager
            .createNativeQuery(sqlString, Tuple.class)
            .setParameter(ID, id.toString())
            .unwrap(NativeQuery.class)
            .addScalar(ID, String.class)
            .addScalar(ISBN, String.class)
            .addScalar(TITLE, String.class)
            .addScalar(AUTHOR, String.class)
            .addScalar(PAGE_COUNT, Integer.class)
            .addScalar(YEAR, Integer.class);
    Tuple tuple = query.uniqueResult();
    return tuple == null ? null : mapToBook(tuple);
  }

  @Transactional
  @Override
  public void createBook(Book book) {
    String sqlString =
        "INSERT INTO book (`id`, `isbn`, `title`, `author`, `page_count`, `year`) VALUES (:id, :isbn, :title, :author, :pageCount, :year)";
    entityManager
        .createNativeQuery(sqlString)
        .setParameter(ID, book.getId().toString())
        .setParameter(ISBN, book.getIsbn())
        .setParameter(TITLE, book.getTitle())
        .setParameter(AUTHOR, book.getAuthor())
        .setParameter(PAGE_COUNT, book.getPageCount())
        .setParameter(YEAR, book.getYear())
        .executeUpdate();
  }

  @Transactional
  @Override
  public void updateBook(Book book) {
    String sqlString =
        "UPDATE book SET `isbn` = :isbn, `title` = :title, `author` = :author, `page_count` = :pageCount, `year` = :year WHERE `id` = :id";
    entityManager
        .createNativeQuery(sqlString)
        .setParameter(ID, book.getId().toString())
        .setParameter(ISBN, book.getIsbn())
        .setParameter(TITLE, book.getTitle())
        .setParameter(AUTHOR, book.getAuthor())
        .setParameter(PAGE_COUNT, book.getPageCount())
        .setParameter(YEAR, book.getYear())
        .executeUpdate();
  }

  @Transactional
  @Override
  public void deleteBook(UUID id) {
    String sqlString = "DELETE FROM book WHERE id = :id";
    entityManager.createNativeQuery(sqlString).setParameter("id", id.toString()).executeUpdate();
  }

  private Book mapToBook(Tuple tuple) {
    String id = tuple.get(ID, String.class);
    String isbn = tuple.get(ISBN, String.class);
    String title = tuple.get(TITLE, String.class);
    String author = tuple.get(AUTHOR, String.class);
    Integer pageCount = tuple.get(PAGE_COUNT, Integer.class);
    Integer year = tuple.get(YEAR, Integer.class);
    return Book.builder()
        .id(UUID.fromString(id))
        .isbn(isbn)
        .title(title)
        .author(author)
        .pageCount(pageCount)
        .year(year)
        .build();
  }
}
