package com.algalopez.kirjavik.backoffice_app.user.domain.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
    super(message);
  }
}
