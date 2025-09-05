package com.algalopez.kirjavik.havn_app.book_review.application.get_book_review;

import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReviewView;
import com.algalopez.kirjavik.havn_app.book_review.domain.port.BookReviewViewRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class GetBookReviewActor {

  private final BookReviewViewRepositoryPort bookReviewViewRepository;
  private final GetBookReviewMapper mapper;

  public GetBookReviewResponse query(GetBookReviewQuery request) {
    UUID bookId = UUID.fromString(request.bookId());
    UUID userId = UUID.fromString(request.userId());

    BookReviewView bookReview =
        bookReviewViewRepository.findBookReviewByBookIdAndUserId(bookId, userId);

    return mapper.toResponse(bookReview);
  }
}
