package com.algalopez.kirjavik.backoffice_app.book.infrastructure;

import com.algalopez.kirjavik.backoffice_app.book.domain.port.BookViewRepositoryPort;
import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookView;
import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookViewSpec;
import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.hibernate.query.NativeQuery;

@ApplicationScoped
public class BookViewRepositoryAdapter implements BookViewRepositoryPort {

  private static final String ID = "id";
  private static final String ISBN = "isbn";
  private static final String TITLE = "title";
  private static final String AUTHOR = "author";
  private static final String PAGE_COUNT = "pageCount";
  private static final String YEAR = "year";

  private final EntityManager entityManager;

  public BookViewRepositoryAdapter(@PersistenceUnit("backoffice") EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<BookView> findAllByFilter(BookViewSpec specification) {
    BookViewSqlBuilder whereClause = BookViewSqlBuilder.build(specification.filters());

    String sql =
        """
        SELECT id, isbn, title, author, page_count AS pageCount, year
        FROM book
        """
            + whereClause.sql;

    Query query = entityManager.createNativeQuery(sql, Tuple.class);
    for (Map.Entry<String, Object> entry : whereClause.parameters.entrySet()) {
      query.setParameter(entry.getKey(), entry.getValue());
    }
    query
        .unwrap(NativeQuery.class)
        .addScalar(ID, String.class)
        .addScalar(ISBN, String.class)
        .addScalar(TITLE, String.class)
        .addScalar(AUTHOR, String.class)
        .addScalar(PAGE_COUNT, Integer.class)
        .addScalar(YEAR, Integer.class);

    @SuppressWarnings("unchecked")
    List<Tuple> rows = query.getResultList();

    return rows.stream().map(BookViewRepositoryAdapter::mapToView).toList();
  }

  @Override
  public BookView findById(UUID id) {
    String sql =
        """
            SELECT id, isbn, title, author, page_count AS pageCount, year
            FROM book
            WHERE id = :id""";
    Query query = entityManager.createNativeQuery(sql, Tuple.class);
    query.setParameter(ID, id.toString());
    query
        .unwrap(NativeQuery.class)
        .addScalar(ID, String.class)
        .addScalar(ISBN, String.class)
        .addScalar(TITLE, String.class)
        .addScalar(AUTHOR, String.class)
        .addScalar(PAGE_COUNT, Integer.class)
        .addScalar(YEAR, Integer.class);

    @SuppressWarnings("unchecked")
    Tuple tuple = ((NativeQuery<Tuple>) query).uniqueResult();

    if (tuple == null) {
      return null;
    }
    return mapToView(tuple);
  }

  @AllArgsConstructor
  private static class BookViewSqlBuilder {

    private static final String AND = " AND ";

    public final String sql;
    public final Map<String, Object> parameters;

    public static BookViewSqlBuilder build(List<BookViewSpec.Filter> filters) {
      StringBuilder where = new StringBuilder("WHERE 1=1");
      Map<String, Object> params = new HashMap<>();

      int index = 0;
      for (BookViewSpec.Filter filter : filters) {
        String paramName = "param" + index++;

        switch (filter.operator()) {
          case EQUALS -> {
            where.append(AND).append(filter.field()).append(" = :").append(paramName);
            params.put(paramName, filter.value());
          }
          case GREATER_THAN -> {
            where.append(AND).append(filter.field()).append(" > :").append(paramName);
            params.put(paramName, Integer.parseInt(filter.value()));
          }
          case STARTS_WITH -> {
            where.append(AND).append(filter.field()).append(" LIKE :").append(paramName);
            params.put(paramName, filter.value() + "%");
          }
        }
      }

      return new BookViewSqlBuilder(where.toString(), params);
    }
  }

  private static BookView mapToView(Tuple tuple) {
    String id = tuple.get(ID, String.class);
    String isbn = tuple.get(ISBN, String.class);
    String title = tuple.get(TITLE, String.class);
    String author = tuple.get(AUTHOR, String.class);
    Integer pageCount = tuple.get(PAGE_COUNT, Integer.class);
    Integer year = tuple.get(YEAR, Integer.class);
    return BookView.builder()
        .id(UUID.fromString(id))
        .isbn(isbn)
        .title(title)
        .author(author)
        .pageCount(pageCount)
        .year(year)
        .build();
  }
}
