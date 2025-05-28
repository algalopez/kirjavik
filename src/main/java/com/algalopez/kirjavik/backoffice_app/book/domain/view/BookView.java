package com.algalopez.kirjavik.backoffice_app.book.domain.view;

import java.util.UUID;
import lombok.Builder;

@Builder
public record BookView(
    UUID id, String isbn, String title, String author, Integer pageCount, Integer year) {}
