package com.algalopez.kirjavik.backoffice_app.book.domain.exception;

public class BookNotFoundException extends RuntimeException {

  public BookNotFoundException(String message) {
    super(message);
  }
}
