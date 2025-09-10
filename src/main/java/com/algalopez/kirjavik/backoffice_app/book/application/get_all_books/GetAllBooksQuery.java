package com.algalopez.kirjavik.backoffice_app.book.application.get_all_books;

import java.util.List;
import lombok.Builder;

@Builder
public record GetAllBooksQuery(List<FilterDto> filters) {

  @Builder
  public record FilterDto(String field, String operator, String value) {}
}
