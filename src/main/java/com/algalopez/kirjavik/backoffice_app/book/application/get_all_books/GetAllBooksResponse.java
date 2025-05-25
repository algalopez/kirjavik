package com.algalopez.kirjavik.backoffice_app.book.application.get_all_books;

import java.util.List;
import lombok.Builder;

@Builder
public record GetAllBooksResponse(List<BookDto> books) {

  @Builder
  public record BookDto(
      String id,
      String isbn,
      String title,
      List<String> authors,
      Integer pageCount,
      Integer year) {}
}
