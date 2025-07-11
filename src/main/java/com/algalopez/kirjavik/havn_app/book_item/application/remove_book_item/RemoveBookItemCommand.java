package com.algalopez.kirjavik.havn_app.book_item.application.remove_book_item;

import lombok.Builder;

@Builder
public record RemoveBookItemCommand(String id, String bookId, String userId) {}
