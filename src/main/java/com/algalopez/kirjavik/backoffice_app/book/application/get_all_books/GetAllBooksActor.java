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

  public GetAllBooksResponse query(GetAllBooksRequest request) {
    BookViewSpec bookViewSpec = getAllBooksMapper.mapToSpec(request);
    List<BookView> books = bookViewRepository.findAllByFilter(bookViewSpec);

    return GetAllBooksResponse.builder()
        .books(books.stream().map(getAllBooksMapper::mapToResponse).toList())
        .build();
  }
}
