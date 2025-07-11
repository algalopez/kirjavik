package com.algalopez.kirjavik.havn_app.book_item.application.return_book_item;

import lombok.Builder;

@Builder
public record ReturnBookItemCommand(String id, String bookId, String userId) {}
