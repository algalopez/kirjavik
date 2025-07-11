package com.algalopez.kirjavik.havn_app.book_item.application.borrow_book_item;

import lombok.Builder;

@Builder
public record BorrowBookItemCommand(String id, String bookId, String userId) {}
