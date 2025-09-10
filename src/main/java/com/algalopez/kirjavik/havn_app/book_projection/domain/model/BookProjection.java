package com.algalopez.kirjavik.havn_app.book_projection.domain.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookProjection {
  private UUID id;
  private String isbn;
  private String title;
  private String author;
  private Integer pageCount;
  private Integer year;
}
