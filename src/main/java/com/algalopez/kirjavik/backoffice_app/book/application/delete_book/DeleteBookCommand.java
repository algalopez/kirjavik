package com.algalopez.kirjavik.backoffice_app.book.application.delete_book;

import lombok.Builder;

@Builder
public record DeleteBookCommand(String id) {}
