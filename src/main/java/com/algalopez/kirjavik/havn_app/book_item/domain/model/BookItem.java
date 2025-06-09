package com.algalopez.kirjavik.havn_app.book_item.domain.model;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BookItem {
  private UUID id;
  private UUID bookId;
  private UUID userId;
  private BookItemStatus status;
}
