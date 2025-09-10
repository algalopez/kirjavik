package com.algalopez.kirjavik.backoffice_app.book.application.get_all_books;

import com.algalopez.kirjavik.backoffice_app.book.domain.port.BookViewRepositoryPort;
import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookView;
import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookViewSpec;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class GetAllBooksActor {

  private final BookViewRepositoryPort bookViewRepository;
  private final GetAllBooksMapper getAllBooksMapper;

  public GetAllBooksResponse query(GetAllBooksQuery request) {
    ensureValidRequest(request);
    BookViewSpec bookViewSpec = getAllBooksMapper.mapToSpec(request);
    List<BookView> books = bookViewRepository.findAllByFilter(bookViewSpec);

    return GetAllBooksResponse.builder()
        .books(books.stream().map(getAllBooksMapper::mapToResponse).toList())
        .build();
  }

  private static void ensureValidRequest(GetAllBooksQuery request) {
    request.filters().forEach(GetAllBooksActor::ensureValidFilter);
  }

  private static void ensureValidFilter(GetAllBooksQuery.FilterDto filter) {
    List<String> validFields = List.of("id", "isbn", "title", "author", "pageCount", "year");
    if (!validFields.contains(filter.field())) {
      throw new IllegalArgumentException("Invalid field: " + filter.field());
    }
    List<String> validOperators = List.of("EQUALS", "STARTS_WITH", "GREATER_THAN");
    if (!validOperators.contains(filter.operator())) {
      throw new IllegalArgumentException("Invalid operator: " + filter.operator());
    }
    if (!filter.value().chars().allMatch(Character::isLetterOrDigit)) {
      throw new IllegalArgumentException("Invalid value: " + filter.value());
    }
  }
}
