package com.algalopez.kirjavik.havn_app.book_review.application.get_all_book_review;

import lombok.Builder;

@Builder
public record GetAllBookReviewQuery(String bookId, String userId) {}
