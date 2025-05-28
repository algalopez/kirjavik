package com.algalopez.kirjavik.backoffice_app.book.application.get_book;

import lombok.Builder;

@Builder
public record GetBookResponse(
    String id, String isbn, String title, String author, Integer pageCount, Integer year) {}
