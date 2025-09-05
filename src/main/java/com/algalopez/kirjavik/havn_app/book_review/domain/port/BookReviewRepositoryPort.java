package com.algalopez.kirjavik.havn_app.book_review.domain.port;

import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReview;
import java.util.UUID;

public interface BookReviewRepositoryPort {

  BookReview findBookReviewByBookIdAndUserId(UUID bookId, UUID userId);

  void createBookReview(BookReview bookReview);

  void updateBookReview(BookReview bookReview);

  void deleteBookReview(Long id);
}
