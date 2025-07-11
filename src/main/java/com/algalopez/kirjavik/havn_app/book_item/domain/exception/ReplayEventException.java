package com.algalopez.kirjavik.havn_app.book_item.domain.exception;

import com.algalopez.kirjavik.havn_app.book_item.domain.event.BookItemDomainEvent;
import com.algalopez.kirjavik.havn_app.book_item.domain.model.BookItem;
import lombok.Getter;

@Getter
public class ReplayEventException extends RuntimeException {

  private final BookItem bookItem;
  private final BookItemDomainEvent event;

  public ReplayEventException(String message, BookItem bookItem, BookItemDomainEvent event) {
    super(message);
    this.bookItem = bookItem;
    this.event = event;
  }
}
