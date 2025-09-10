package com.algalopez.kirjavik.backoffice_app.book.application.get_book;

import lombok.Builder;

@Builder
public record GetBookQuery(String id) {}
