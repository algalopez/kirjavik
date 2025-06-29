package com.algalopez.kirjavik.havn_app.book_item.domain.exception;

public class OptimisticConcurrencyException extends RuntimeException {

  public OptimisticConcurrencyException(String message) {
    super(message);
  }
}
