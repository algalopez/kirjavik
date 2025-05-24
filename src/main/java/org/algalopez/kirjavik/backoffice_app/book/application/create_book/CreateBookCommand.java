package org.algalopez.kirjavik.backoffice_app.book.application.create_book;

import java.util.List;
import lombok.Builder;

@Builder
public record CreateBookCommand(
    String id, String isbn, String title, List<String> authors, Integer pageCount, Integer year) {}
