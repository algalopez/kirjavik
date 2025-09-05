package com.algalopez.kirjavik.havn_app.book_review.domain.port;

import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReviewCriteria;
import com.algalopez.kirjavik.havn_app.book_review.domain.model.BookReviewView;
import java.util.List;
import java.util.UUID;

public interface BookReviewViewRepositoryPort {

  BookReviewView findBookReviewByBookIdAndUserId(UUID bookId, UUID userId);

  List<BookReviewView> findAllReviewsByFilters(BookReviewCriteria bookReviewCriteria);
}
