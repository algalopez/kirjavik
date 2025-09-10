package com.algalopez.kirjavik.havn_app.book_projection.application.update_book_projection;

import lombok.Builder;

@Builder
public record UpdateBookProjectionCommand(
    String id, String isbn, String title, String author, Integer pageCount, Integer year) {}
