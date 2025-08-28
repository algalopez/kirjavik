package com.algalopez.kirjavik.havn_app.book_review.application.create_book_review;

import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReview;
import com.algalopez.kirjavik.havn_app.book_review.domain.port.BookReviewRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class CreateBookReviewActor {

  private final CreateBookReviewMapper createBookReviewMapper;
  private final BookReviewRepositoryPort bookReviewPort;
  private final CreateBookReviewEventPublisher createBookReviewEventPublisher;

  public void command(CreateBookReviewCommand command) {
    BookReview bookReview = createBookReviewMapper.mapToDomain(command);
    bookReviewPort.createBookReview(bookReview);
    createBookReviewEventPublisher.publishBookReviewCreatedEvent(bookReview);
  }
}
