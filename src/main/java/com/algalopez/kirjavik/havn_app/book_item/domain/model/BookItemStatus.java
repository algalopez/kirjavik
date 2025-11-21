package com.algalopez.kirjavik.havn_app.book_item.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookItemStatus {
  AVAILABLE(1),
  BORROWED(2),
  REMOVED(3);
  
  private final int value;
}
