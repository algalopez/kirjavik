package com.algalopez.kirjavik.havn_app.book_review.application.delete_book_review;

import lombok.Builder;

@Builder
public record DeleteBookReviewCommand(Long id) {}
