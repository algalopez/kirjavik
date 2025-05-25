package com.algalopez.kirjavik.backoffice_app.book.application.get_book;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetBookQuery {
  private String id;
}
