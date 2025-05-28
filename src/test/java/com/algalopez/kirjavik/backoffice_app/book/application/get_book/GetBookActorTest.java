package com.algalopez.kirjavik.backoffice_app.book.application.get_book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.algalopez.kirjavik.backoffice_app.book.domain.exception.BookNotFoundException;
import com.algalopez.kirjavik.backoffice_app.book.domain.port.BookViewRepositoryPort;
import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookView;
import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookViewMother;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class GetBookActorTest {

  private BookViewRepositoryPort bookViewRepository;
  private GetBookActor getBookActor;

  @BeforeEach
  void setUp() {
    GetBookMapper getBookMapper = Mappers.getMapper(GetBookMapper.class);
    bookViewRepository = Mockito.mock(BookViewRepositoryPort.class);
    getBookActor = new GetBookActor(getBookMapper, bookViewRepository);
  }

  @Test
  void query() {
    BookView book = new BookViewMother().build();
    GetBookQuery query = new GetBookQuery(book.id().toString());
    Mockito.when(bookViewRepository.findById(Mockito.any(UUID.class))).thenReturn(book);

    GetBookResponse actualResponse = getBookActor.query(query);

    assertThat(actualResponse)
        .isEqualTo(
            GetBookResponse.builder()
                .id(book.id().toString())
                .isbn(book.isbn())
                .title(book.title())
                .author(book.author())
                .pageCount(book.pageCount())
                .year(book.year())
                .build());
  }

  @Test
  void query_whenBookNotFound() {
    BookView book = new BookViewMother().build();
    GetBookQuery query = new GetBookQuery(book.id().toString());
    Mockito.when(bookViewRepository.findById(Mockito.any(UUID.class))).thenReturn(null);

    assertThatExceptionOfType(BookNotFoundException.class)
        .isThrownBy(() -> getBookActor.query(query));
  }
}
