package com.algalopez.kirjavik.backoffice_app.book.application.get_all_books;

import static org.assertj.core.api.Assertions.assertThat;

import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookView;
import com.algalopez.kirjavik.backoffice_app.book.domain.view.BookViewSpec;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

class GetAllBooksMapperTest {

  private final GetAllBooksMapper mapper = Mappers.getMapper(GetAllBooksMapper.class);

  @Test
  void mapToSpec() {
    GetAllBooksQuery request =
        GetAllBooksQuery.builder()
            .filters(
                List.of(
                    GetAllBooksQuery.FilterDto.builder()
                        .field("field")
                        .operator("EQUALS")
                        .value("value")
                        .build()))
            .build();

    BookViewSpec actualSpec = mapper.mapToSpec(request);

    assertThat(actualSpec)
        .isEqualTo(
            BookViewSpec.builder()
                .filters(
                    List.of(
                        BookViewSpec.Filter.builder()
                            .field("field")
                            .operator(BookViewSpec.Operator.EQUALS)
                            .value("value")
                            .build()))
                .build());
  }

  @MethodSource("mapToSpec_shouldMapAllOperators_source")
  @ParameterizedTest
  void mapToSpec_shouldMapAllOperators(
          GetAllBooksQuery request, BookViewSpec.Operator expectedOperator) {
    BookViewSpec actualSpec = mapper.mapToSpec(request);

    assertThat(actualSpec.filters().getFirst().operator()).isEqualTo(expectedOperator);
  }

  private static Stream<Arguments> mapToSpec_shouldMapAllOperators_source() {
    return Stream.of(
        Arguments.of(buildGetAllBooksRequest("EQUALS"), BookViewSpec.Operator.EQUALS),
        Arguments.of(buildGetAllBooksRequest("STARTS_WITH"), BookViewSpec.Operator.STARTS_WITH),
        Arguments.of(buildGetAllBooksRequest("GREATER_THAN"), BookViewSpec.Operator.GREATER_THAN));
  }

  @Test
  void mapToResponse() {
    BookView book =
        BookView.builder()
            .id(UUID.fromString("01970859-0cd4-7a46-9917-3c7c82daf5b4"))
            .isbn("isbn")
            .title("title")
            .author("author")
            .pageCount(400)
            .year(2000)
            .build();

    GetAllBooksResponse.BookDto actualBook = mapper.mapToResponse(book);

    assertThat(actualBook)
        .isEqualTo(
            GetAllBooksResponse.BookDto.builder()
                .id("01970859-0cd4-7a46-9917-3c7c82daf5b4")
                .isbn("isbn")
                .title("title")
                .author("author")
                .pageCount(400)
                .year(2000)
                .build());
  }

  private static GetAllBooksQuery buildGetAllBooksRequest(String operator) {
    return GetAllBooksQuery.builder()
        .filters(
            List.of(
                GetAllBooksQuery.FilterDto.builder()
                    .field("field")
                    .operator(operator)
                    .value("value")
                    .build()))
        .build();
  }
}
