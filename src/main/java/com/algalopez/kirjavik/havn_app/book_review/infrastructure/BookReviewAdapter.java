package com.algalopez.kirjavik.havn_app.book_review.infrastructure;

import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReview;
import com.algalopez.kirjavik.havn_app.book_review.domain.port.BookReviewRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BookReviewAdapter implements BookReviewRepositoryPort {

  @Override
  public void createBookReview(BookReview bookReview) {}

  @Override
  public void updateBookReview(BookReview bookReview) {}

  @Override
  public void deleteBookReview(Long id) {}
}
