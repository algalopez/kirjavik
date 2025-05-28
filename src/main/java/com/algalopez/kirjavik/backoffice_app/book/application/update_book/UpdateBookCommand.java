package com.algalopez.kirjavik.backoffice_app.book.application.update_book;

import lombok.Builder;

@Builder
public record UpdateBookCommand(
    String id, String isbn, String title, String author, Integer pageCount, Integer year) {}
