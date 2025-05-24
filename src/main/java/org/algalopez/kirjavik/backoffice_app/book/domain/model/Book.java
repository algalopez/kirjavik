package org.algalopez.kirjavik.backoffice_app.book.domain.model;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Book {
  private UUID id;
  private String isbn;
  private String title;
  private List<String> authors;
  private Integer pageCount;
  private Integer year;
}
