package com.algalopez.kirjavik.backoffice_app.book.domain.view;

import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record BookView(
    UUID id, String isbn, String title, List<String> authors, Integer pageCount, Integer year) {}
