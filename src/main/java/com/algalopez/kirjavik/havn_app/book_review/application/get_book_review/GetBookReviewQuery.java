package com.algalopez.kirjavik.havn_app.book_review.application.get_book_review;

import lombok.Builder;

@Builder
public record GetBookReviewQuery(String bookId, String userId) {}
