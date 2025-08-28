package com.algalopez.kirjavik.havn_app.book_review.application.delete_book_review;

import com.algalopez.kirjavik.havn_app.book_review.domain.port.BookReviewRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class DeleteBookReviewActor {

  private final BookReviewRepositoryPort bookReviewRepository;
  private final DeleteBookReviewEventPublisher deleteBookReviewEventPublisher;

  public void command(DeleteBookReviewCommand command) {
    Long id = command.id();
    bookReviewRepository.deleteBookReview(id);
    deleteBookReviewEventPublisher.publishBookReviewDeletedEvent(id);
  }
}
