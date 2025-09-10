package com.algalopez.kirjavik.havn_app.book_projection.application.create_book_projection;

import lombok.Builder;

@Builder
public record CreateBookProjectionCommand(
    String id, String isbn, String title, String author, Integer pageCount, Integer year) {}
