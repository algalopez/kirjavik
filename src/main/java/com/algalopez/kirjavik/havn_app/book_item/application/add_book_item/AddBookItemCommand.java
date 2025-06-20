package com.algalopez.kirjavik.havn_app.book_item.application.add_book_item;

import lombok.Builder;

@Builder
public record AddBookItemCommand(String id, String bookId, String userId) {}
