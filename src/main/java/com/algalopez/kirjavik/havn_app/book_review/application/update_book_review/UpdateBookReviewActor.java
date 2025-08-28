package com.algalopez.kirjavik.havn_app.book_review.application.update_book_review;

import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReview;
import com.algalopez.kirjavik.havn_app.book_review.domain.port.BookReviewRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class UpdateBookReviewActor {

  private final UpdateBookReviewMapper updateBookReviewMapper;
  private final BookReviewRepositoryPort bookReviewPort;
  private final UpdateBookReviewEventPublisher updateBookReviewEventPublisher;

  public void command(UpdateBookReviewCommand command) {
    BookReview bookReview = updateBookReviewMapper.mapToDomain(command);
    bookReviewPort.updateBookReview(bookReview);
    updateBookReviewEventPublisher.publishBookReviewUpdatedEvent(bookReview);
  }
}
