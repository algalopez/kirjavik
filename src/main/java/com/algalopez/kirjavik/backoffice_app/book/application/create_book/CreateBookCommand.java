package com.algalopez.kirjavik.backoffice_app.book.application.create_book;

import lombok.Builder;

@Builder
public record CreateBookCommand(
    String id, String isbn, String title, String author, Integer pageCount, Integer year) {}
