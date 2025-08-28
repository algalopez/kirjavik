package com.algalopez.kirjavik.havn_app.book_review.domain.port;

import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReview;

public interface BookReviewRepositoryPort {

  void createBookReview(BookReview bookReview);

  void updateBookReview(BookReview bookReview);

  void deleteBookReview(Long id);
}
