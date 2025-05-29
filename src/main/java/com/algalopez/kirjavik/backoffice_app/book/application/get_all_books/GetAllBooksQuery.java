package com.algalopez.kirjavik.backoffice_app.book.application.get_all_books;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetAllBooksQuery {
  private List<FilterDto> filters;

  @Builder
  @Data
  public static class FilterDto {
    private String field;
    private String operator;
    private String value;
  }
}
