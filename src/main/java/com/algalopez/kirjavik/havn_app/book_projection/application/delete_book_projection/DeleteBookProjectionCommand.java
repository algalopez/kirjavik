package com.algalopez.kirjavik.havn_app.book_projection.application.delete_book_projection;

import lombok.Builder;

@Builder
public record DeleteBookProjectionCommand(String id) {}
