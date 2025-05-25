package com.algalopez.kirjavik.backoffice_app.book.application.get_book;

import java.util.List;
import lombok.Builder;

@Builder
public record GetBookResponse(
    String id, String isbn, String title, List<String> authors, Integer pageCount, Integer year) {}
