package com.algalopez.kirjavik.backoffice_app.book.application.get_all_books;

import static org.assertj.core.api.Assertions.*;

import com.algalopez.kirjavik.backoffice_app.book.domain.port.BookViewRepositoryPort;
import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookView;
import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookViewMother;
import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookViewSpec;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

class GetAllBooksActorTest {

  private BookViewRepositoryPort bookViewRepository;
  private GetAllBooksActor getAllBooksActor;

  @BeforeEach
  void setUp() {
    GetAllBooksMapper getAllBooksMapper = Mappers.getMapper(GetAllBooksMapper.class);
    bookViewRepository = Mockito.mock(BookViewRepositoryPort.class);
    getAllBooksActor = new GetAllBooksActor(bookViewRepository, getAllBooksMapper);
  }

  @Test
  void query() {
    BookView book = new BookViewMother().build();
    GetAllBooksRequest request =
        GetAllBooksRequest.builder()
            .filters(
                List.of(
                    GetAllBooksRequest.FilterDto.builder()
                        .field("id")
                        .operator("EQUALS")
                        .value("1")
                        .build()))
            .build();
    Mockito.when(bookViewRepository.findAllByFilter(Mockito.any(BookViewSpec.class)))
        .thenReturn(List.of(book));

    GetAllBooksResponse actualResponse = getAllBooksActor.query(request);

    assertThat(actualResponse)
        .isEqualTo(
            GetAllBooksResponse.builder()
                .books(
                    List.of(
                        GetAllBooksResponse.BookDto.builder()
                            .id(book.id().toString())
                            .isbn(book.isbn())
                            .title(book.title())
                            .author(book.author())
                            .pageCount(book.pageCount())
                            .year(book.year())
                            .build()))
                .build());
  }

  @MethodSource("query_whenValidRequest_source")
  @ParameterizedTest
  void query_whenValidRequest(String field, String operator, String value) {
    GetAllBooksRequest request =
        GetAllBooksRequest.builder()
            .filters(
                List.of(
                    GetAllBooksRequest.FilterDto.builder()
                        .field(field)
                        .operator(operator)
                        .value(value)
                        .build()))
            .build();
    Mockito.when(bookViewRepository.findAllByFilter(Mockito.any(BookViewSpec.class)))
        .thenReturn(List.of());

    assertThatNoException().isThrownBy(() -> getAllBooksActor.query(request));
  }

  private static Stream<Arguments> query_whenValidRequest_source() {
    return Stream.of(
        Arguments.of("id", "EQUALS", "1"),
        Arguments.of("isbn", "EQUALS", "1"),
        Arguments.of("title", "EQUALS", "1"),
        Arguments.of("author", "EQUALS", "1"),
        Arguments.of("pageCount", "EQUALS", "1"),
        Arguments.of("year", "EQUALS", "1"),
        Arguments.of("id", "STARTS_WITH", "1"),
        Arguments.of("id", "GREATER_THAN", "1"),
        Arguments.of("id", "EQUALS", "a"),
        Arguments.of("id", "EQUALS", "1a"));
  }

  @MethodSource("query_whenInvalidRequest_source")
  @ParameterizedTest
  void query_whenInvalidRequest(String field, String operator, String value) {
    GetAllBooksRequest request =
        GetAllBooksRequest.builder()
            .filters(
                List.of(
                    GetAllBooksRequest.FilterDto.builder()
                        .field(field)
                        .operator(operator)
                        .value(value)
                        .build()))
            .build();
    Mockito.when(bookViewRepository.findAllByFilter(Mockito.any(BookViewSpec.class)))
        .thenReturn(List.of());

    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> getAllBooksActor.query(request));
  }

  private static Stream<Arguments> query_whenInvalidRequest_source() {
    return Stream.of(
        Arguments.of("invalid", "EQUALS", "1"),
        Arguments.of("id", "invalid", "1"),
        Arguments.of("id", "EQUALS", "aa="));
  }
}
