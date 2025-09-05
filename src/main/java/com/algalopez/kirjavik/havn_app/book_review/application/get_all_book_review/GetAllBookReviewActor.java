package com.algalopez.kirjavik.havn_app.book_review.application.get_all_book_review;

import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReviewCriteria;
import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReviewView;
import com.algalopez.kirjavik.havn_app.book_review.domain.port.BookReviewViewRepositoryPort;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationScoped
public class GetAllBookReviewActor {

  private final BookReviewViewRepositoryPort bookReviewViewRepository;
  private final GetAllBookReviewMapper mapper;

  public GetAllBookReviewResponse query(GetAllBookReviewQuery request) {
    BookReviewCriteria criteria =
        BookReviewCriteria.builder()
            .bookId(UUID.fromString(request.bookId()))
            .userId(UUID.fromString(request.userId()))
            .build();

    List<BookReviewView> bookReviews = bookReviewViewRepository.findAllReviewsByFilters(criteria);

    return GetAllBookReviewResponse.builder()
        .bookReviews(bookReviews.stream().map(mapper::toResponse).toList())
        .build();
  }
}
